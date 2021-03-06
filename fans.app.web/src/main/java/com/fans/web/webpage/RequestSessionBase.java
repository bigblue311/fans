package com.fans.web.webpage;

import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.fans.biz.manager.PriceManager;
import com.fans.biz.manager.UserManager;
import com.fans.dal.cache.SystemConfigCache;
import com.fans.dal.enumerate.SearchTypeEnum;
import com.fans.dal.enumerate.ShoppingLevelEnum;
import com.fans.dal.enumerate.SystemConfigKeyEnum;
import com.fans.dal.model.SkvUserDO;
import com.fans.dal.model.UserDO;
import com.fans.dal.query.UserQueryCondition;
import com.fans.web.constant.CookieKey;
import com.google.common.collect.Lists;
import com.google.common.net.InetAddresses;
import com.victor.framework.common.shared.Split;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.JsonTools;
import com.victor.framework.common.tools.StringTools;

public abstract class RequestSessionBase extends CookieBase{
    
	@Autowired
    private PriceManager priceManager;
	
	@Autowired
    private UserManager userManager;
	
	@Autowired
    private SystemConfigCache systemConfigCache;
	
	public void loadPriceSet(HttpServletRequest request, Context context){
	    UserDO userDO = getUserDO(request);
	    ShoppingLevelEnum level = getLevel(request);
	    
		context.put("topupSet", priceManager.getTopupSet());
		context.put("vipSet", priceManager.getVipSet());
		context.put("rocketSet", priceManager.getZhuangBSet(userDO,level));
		context.put("skvPriceMsg", priceManager.getSkvPriceMsg(userDO,level));
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
    
    public UserDO getUserDO(HttpServletRequest request){
        String openId = getOpenId(request);
        return userManager.getByOpenId(openId);
    }
    
	public Long getSkvId(HttpServletRequest request) {
	    UserDO userDO = getUserDO(request);
        if(userDO != null){
            if(userDO.getSkvId()!=null){
                return userDO.getSkvId();
            }
        }
	    String skvId = request.getParameter(CookieKey.SKV_ID);
	    if(StringTools.isEmpty(skvId)){
	        skvId = super.getCookie(request, CookieKey.SKV_ID);
	    }
	    if(StringTools.isNotEmpty(skvId)){
	        String trimed = trim0(skvId);
	        try {
                return Long.parseLong(trimed);
            } catch (NumberFormatException e) {
                return null;
            }
	    } else {
	        return null;
	    }
	}
	
	public ShoppingLevelEnum getLevel(HttpServletRequest request){
	    Long skvId = getSkvId(request);
	    if(skvId!=null){
            SkvUserDO skvUser = userManager.getSkvUserById(skvId);
            if(skvUser!=null){
                String shoppingLevel = skvUser.getShoppingLevel();
                return ShoppingLevelEnum.getByCode(shoppingLevel);
            }
        }
	    return null;
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
    
    public boolean isWeixinUser(HttpServletRequest request){
        Integer version = getWxVersion(request);
        return version != null;
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
    
    public boolean isAdmin(HttpServletRequest request){
        String openId = super.getCookie(request, CookieKey.OPEN_ID);
        List<String> list = getAdminList();
        if(CollectionTools.isEmpty(list)){
            return false;
        }
        return list.contains(openId);
    }
    
    public boolean isOriAdmin(HttpServletRequest request){
        String oriOpenId = super.getCookie(request, CookieKey.ORI_OPEN_ID);
        List<String> list = getAdminList();
        if(CollectionTools.isEmpty(list)){
            return false;
        }
        return list.contains(oriOpenId);
    }
    
    public List<String> getAdminList(){
        String admins = systemConfigCache.getCacheString(SystemConfigKeyEnum.GREEN_CHANNEL.getCode(), "");
        if(StringTools.isEmpty(admins)){
            return Lists.newArrayList();
        }
        List<String> list = StringTools.split(admins, Split.逗号);
        return list;
    }
}
