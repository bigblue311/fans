package com.fans.web.webpage;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class CookieBase {
    public void setCookie(HttpServletResponse response,String name,String value){
        Cookie c = new Cookie(name, value);
        c.setDomain(".wetuan.com");
        c.setMaxAge(86400);
        c.setPath("/");
        response.addCookie(c);
    }
   
    public String getCookie(HttpServletRequest request, String key){
       Cookie[] cookies = request.getCookies();
       if(cookies == null) {
           return null;
       }
       for(Cookie cookie : cookies){
           if(cookie.getName().equals(key)){
               return cookie.getValue();
           }
       }
       return null;
    }
}
