package com.fans.web.webpage;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.fans.biz.manager.PriceManager;
import com.fans.dal.enumerate.SearchTypeEnum;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.fans.web.constant.CookieKey;
import com.google.common.net.InetAddresses;
import com.victor.framework.common.tools.JsonTools;
import com.victor.framework.common.tools.StringTools;

public abstract class RequestSessionBase extends CookieBase{
    
	@Autowired
    private PriceManager priceManager;
	
	public void loadPriceSet(Context context){
		context.put("topupSet", priceManager.getTopupSet());
		context.put("vipSet", priceManager.getVipSet());
		context.put("rocketSet", priceManager.getZhuangBSet());
		context.put("skvPriceMsg", priceManager.getSkvPriceMsg());
	}
	
	/**
     * 获取当前域名
     * @param request
     * @return
     */
    public String getDomain(HttpServletRequest request){
        String __domain = request.getParameter("__domain");
        String domain = "";
        try{
            URL urlObj = new URL(request.getRequestURL().toString());
            domain = urlObj.getHost();
        }catch(Exception e){
            e.printStackTrace();
        }

        if(StringTools.isNotEmpty(__domain)){
            domain = __domain;
        } else {
            if(StringTools.isEmpty(domain) || "localhost".equals(domain) || isValidIP(domain)){
                domain = "wz.wetuan.com";
            }
        }
        return domain;
    }
    
    private boolean isValidIP(String ip){
        if(StringTools.isEmpty(ip)){
            return false;
        }
        return InetAddresses.isInetAddress(ip);
    } 
	
	public Long getSkvId(HttpServletRequest request) {
	    String skvId = super.getCookie(request, CookieKey.SKV_ID);
	    if(StringTools.isNotEmpty(skvId)){
	        String trimed = trim0(skvId);
	        return Long.parseLong(trimed);
	    } else {
	        return null;
	    }
	}
	
	private String trim0(String bigInt){
	    boolean zeroFind = false;
	    if(StringTools.isEmpty(bigInt)){
	        return null;
	    }
	    char[] chars = bigInt.toCharArray();
	    String result = "";
	    for(char c : chars){
	        String each = c+"";
	        if(!zeroFind && each.equals("0")){
	            continue;
	        } else {
	            result += c;
	            zeroFind = true;
	        }
	    }
	    return result;
	}
	
    public String getOpenId(HttpServletRequest request) {
        return super.getCookie(request, CookieKey.OPEN_ID);
    }
    
    public void setOpenId(HttpServletResponse response, String openId) {
        super.setCookie(response, CookieKey.OPEN_ID, openId);
    }
    
    public Integer getWxVersion(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger")+15);
        String agentStr = new String(new char[]{agent});
        try{
            if(StringTools.isNotEmpty(agentStr)){
                return Integer.parseInt(agentStr);
            } else {
                return null;
            }
        } catch(Exception ex) {
            return null;
        }
    }
    
    public Boolean isSearchGroup(HttpServletRequest request){
        String searchType = getSearchType(request);
        return SearchTypeEnum.群二维码.getCode().equals(searchType);
    }
    
    public String getSearchType(HttpServletRequest request) {
        return super.getCookie(request, CookieKey.SEARCH_TYPE);
    }
    
    public void setSearchType(HttpServletResponse response, String searchType) {
        super.setCookie(response, CookieKey.SEARCH_TYPE, searchType);
    }
    
    public UserQueryCondition getQuery(HttpServletRequest request) {
        UserQueryCondition queryCondition = new UserQueryCondition();
        String query = super.getCookie(request, CookieKey.QUERY);
        if(StringTools.isNotEmpty(query)){
            try {
                UserDO userDO = JsonTools.fromJson(query, UserDO.class);
                if(userDO!=null){
                    queryCondition = userDO.toQueryCondition();
                }
            } catch (Exception e) {
                //do nothing
            }
        }
        if(isSearchGroup(request)){
            queryCondition.searchGroup();
        } else {
            queryCondition.searchPerson();
        }
        queryCondition.valid().setPageSize(UserQueryCondition.DEFAULT_PAGE_SIZE).setPage(1);
        return queryCondition;
    }
    
    public void setQuery(HttpServletResponse response, UserDO userDO) {
        if(userDO == null){
            userDO = new UserDO();
        }
        super.setCookie(response, CookieKey.QUERY, JsonTools.toJson(userDO));
    }
}
