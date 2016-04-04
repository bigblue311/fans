package com.fans.web.pipeline;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.Valve;
import com.fans.biz.manager.UserManager;
import com.fans.biz.manager.WeixinManager;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.model.QrcodeDO;
import com.fans.dal.model.QrcodeScanDO;
import com.fans.dal.model.SkvUserDO;
import com.fans.dal.model.UserDO;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.tools.StringTools;

public class WeixinOpenIdValve extends RequestSessionBase implements Valve{

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private WeixinManager weixinManager;
	
	@Autowired
    private SystemConfigCache systemConfigCache;
	
	@Autowired
    private UserManager userManager;
	
	@Override
	public void invoke(PipelineContext pipelineContext) throws Exception {
		try {
		    String openId = super.getOpenId(request);
		    
		    String _goWetuan = request.getParameter("_goWetuan");
            if(StringTools.isNotEmpty(_goWetuan)){
                response.sendRedirect(getWetuanUrl(_goWetuan,openId));
                return;
            }
            
            String _setOpenId = request.getParameter("_setOpenId");
		    if(StringTools.isNotEmpty(_setOpenId)){
	            if(StringTools.isNotEmpty(openId)){
	                super.setOpenId(response, openId);
	                handWZUser(openId);
	                return;
	            } else {
	                String domain = super.getDomain(request);
	                response.sendRedirect(weixinManager.getOauth2Url(domain));
	                return;
	            }
		    }
		} finally {
			pipelineContext.invokeNext();
		}
	}
	
	private String getWetuanUrl(String _goWetuan, String openId){
	    String uri = request.getRequestURI();
        String wetuanHost = systemConfigCache.getCacheString(SystemConfigKeyEnum.WETUAN_HOST.getCode(), "wetuan.com");
        if(_goWetuan.equals("1")){
            if(StringTools.isNotEmpty(openId)){
                uri = "http://"+wetuanHost+"/index.htm?openId="+openId;
            } else {
                uri = "http://"+wetuanHost+"/index.htm";
            }
        }
        if(_goWetuan.equals("2")){
            if(StringTools.isNotEmpty(openId)){
                String upId = getUpId(openId);
                uri = "http://"+wetuanHost+"/user/login.htm?upId="+upId+"&openId="+openId;
            } else {
                uri = "http://"+wetuanHost+"/user/login.htm";
            }
        }
        if(_goWetuan.equals("3")){
            if(StringTools.isNotEmpty(openId)){
                String upId = getUpId(openId);
                uri = "http://"+wetuanHost+"/user/toRegist.htm?upId="+upId+"&openId="+openId;
            } else {
                uri = "http://"+wetuanHost+"/user/toRegist.htm";
            }
        }
        return uri;
	}
	
	private void handWZUser(String openId){
	    UserDO userDO = userManager.getByOpenId(openId);
        
        Long skvId = super.getSkvId(request);
        SkvUserDO skvUserDO = userManager.getSkvUserByOpenId(openId);
        if(skvUserDO!=null){
            skvId = skvUserDO.getId();
        }
        
        if(userDO!=null){
            updateQrcodeScan(userDO);
            if(userDO.getSkvId() == null && skvId != null){
                userDO.setSkvId(skvId);
                userManager.update(userDO);
            }
        }
        
        if(skvUserDO != null){
            boolean toUpdate = false;
            if(StringTools.isEmpty(skvUserDO.getUserName())){
                skvUserDO.setUserName(userDO.getNickName());
                toUpdate = true;
            }
            if(StringTools.isEmpty(skvUserDO.getUserImage())){
                skvUserDO.setUserImage(userDO.getHeadImg());
                toUpdate = true;
            }
            if(StringTools.isEmpty(skvUserDO.getOpenId())){
                skvUserDO.setOpenId(openId);
                toUpdate = true;
            }
            if(toUpdate){
                userManager.updateSkvUser(skvUserDO);
            }
        }
	}
	
	private void updateQrcodeScan(UserDO userDO){
        if(userDO != null && userDO.getSkvId() != null && StringTools.isNotEmpty(userDO.getOpenId())){
            weixinManager.updateSkvId(userDO.getOpenId(), userDO.getSkvId());
        }
    }
	
	private String getUpId(String openId){
        if(StringTools.isEmpty(openId)){
            return "";
        }
        QrcodeScanDO qrcodeScanDO = weixinManager.getSkvScanByOpenId(openId);
        if(qrcodeScanDO!=null){
            QrcodeDO qrcodeDO = weixinManager.getQrcodeById(qrcodeScanDO.getQrcodeId());
            if(qrcodeDO!=null && qrcodeDO.getSkvId() != null){
                return qrcodeDO.getSkvId().toString();
            }
        }
        return "";
    }
}
