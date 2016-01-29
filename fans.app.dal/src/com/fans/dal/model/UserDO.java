package com.fans.dal.model;

import java.io.Serializable;
import java.util.Date;

import com.fans.dal.query.UserQueryCondition;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.EntityDO;

public class UserDO extends EntityDO implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 9107233004949997492L;
    private Long skvId;                 //SKV ID
    private String openId;              //微信OPEN ID
    private String nickName;            //微信昵称
    private String phone;               //手机号码
    private String headImg;             //头像
    private Integer gender;             //性别
    private String qrcode;              //二维码
    private String groupQrcode;         //群二维码
    private String province;            //省
    private String city;                //市
    private String description;         //描述
    private String groupDescription;    //群描述
    private String weixinId;            //微信ID
    private Integer coins;				//金币
    private Date gmtRefresh;            //置顶刷新时间
    private Date gmtVipExpire;          //vip过期时间
    private Long extId;                 //外部ID
    private String extCity;             //外部城市
    private String extSource;           //外部来源
    private String domain;              //外部域名
    private Integer clickCount;         //点击次数
    
    public Long getSkvId() {
        return skvId;
    }
    public void setSkvId(Long skvId) {
        this.skvId = skvId;
    }
    public String getOpenId() {
        return openId;
    }
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getHeadImg() {
        return headImg;
    }
    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
    public Integer getGender() {
        return gender;
    }
    public void setGender(Integer gender) {
        this.gender = gender;
    }
    public String getQrcode() {
        return qrcode;
    }
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
    public String getGroupQrcode() {
        return groupQrcode;
    }
    public void setGroupQrcode(String groupQrcode) {
        this.groupQrcode = groupQrcode;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getGroupDescription() {
        return groupDescription;
    }
    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }
    public String getWeixinId() {
        return weixinId;
    }
    public void setWeixinId(String weixinId) {
        this.weixinId = weixinId;
    }
    public Date getGmtRefresh() {
        return gmtRefresh;
    }
    public void setGmtRefresh(Date gmtRefresh) {
        this.gmtRefresh = gmtRefresh;
    }
	public Date getGmtVipExpire() {
        return gmtVipExpire;
    }
    public void setGmtVipExpire(Date gmtVipExpire) {
        this.gmtVipExpire = gmtVipExpire;
    }
    public Integer getCoins() {
		return coins;
	}
	public void setCoins(Integer coins) {
		this.coins = coins;
	}
	public Long getExtId() {
        return extId;
    }
    public void setExtId(Long extId) {
        this.extId = extId;
    }
    public String getExtCity() {
        return extCity;
    }
    public void setExtCity(String extCity) {
        this.extCity = extCity;
    }
    public String getExtSource() {
        return extSource;
    }
    public void setExtSource(String extSource) {
        this.extSource = extSource;
    }
    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }
    public Integer getClickCount() {
        return clickCount;
    }
    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }
    public boolean isValid(){
        if(StringTools.isEmpty(headImg)){
            return false;
        }
        if(StringTools.isEmpty(qrcode)){
            return false;
        }
        return true;
    }
    
    public boolean isVip(){
        if(gmtVipExpire == null){
            return false;
        }
        return DateTools.today().before(gmtVipExpire);
    }
    
    public UserQueryCondition toQueryCondition(){
        UserQueryCondition queryCondition = new UserQueryCondition();
        queryCondition.setQueryMap(this.toMap());
        return queryCondition;
    }
}
