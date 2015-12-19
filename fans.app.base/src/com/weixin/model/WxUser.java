package com.weixin.model;

import com.victor.framework.common.tools.JsonTools;

/**
 * 微信用户信息(UnionId机制)
 * 
 * @link http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html
 * @author felix
 * @since 2015-02-10
 */
public final class WxUser {

    /**
     * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息
     */
    private Integer subscribe;
    /**
     * 用户的标识，对当前公众号唯一
     */
    private String openId;
    
    /**
     * 用户AccessToken
     */
    private String accessToken;
    /**
     * 用户的昵称
     */
    private String nickName;
    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    private Integer sex;
    /**
     * 用户所在城市
     */
    private String city;
    /**
     * 用户所在国家
     */
    private String country;
    /**
     * 用户所在省份
     */
    private String province;
    /**
     * 用户的语言，简体中文为zh_CN
     */
    private String language;
    /**
     * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。
     * 若用户更换头像，原有头像URL将失效。
     */
    private String headImgUrl;
    /**
     * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     */
    private Long subscribeTime;
    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     * 
     * @see https 
     *      ://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource
     *      /res_list&verify=1&lang=zh_CN
     */
    private String unionId;

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
    
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Long getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Long subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public static WxUser fromJson(String json) {
        return JsonTools.fromJson(json, WxUser.class);
    }

    @Override
    public String toString() {
        return "WxUser[" + "subscribe=" + subscribe + ", openId=" + openId + ", nickName=" + nickName + ", sex=" + sex
                + ", language=" + language + ", city=" + city + ", province=" + province + ", country=" + country
                + ", headImgUrl=" + headImgUrl + ", subscribeTime=" + subscribeTime + ", unionId=" + unionId + ']';
    }
}
