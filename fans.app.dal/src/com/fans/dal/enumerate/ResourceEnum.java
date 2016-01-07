package com.fans.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum ResourceEnum {
	
	帮助(-4l,"/admin/help","帮助","帮助页面",null,false),
	没有权限(-3l,"/admin/nopermision","没有权限","没有权限的提示页面",null,false),
	根目录(-2l,"/admin","根目录","默认页面",null,false),
	默认页(-1l,"/admin/","默认页","默认页面",null,false),
	登录(0l,"/admin/login","登录页面","登录页面",null,false),
	欢迎(1l,"/admin/welcome","欢迎页面","默认页面",null,true),
	
	后台账号(2l,"/admin/employee","后台账号","管理后台用户,添加后台用户,为用户授权,删除用户",SubMenuEnum.后台账号,true),
	角色权限(3l,"/admin/role","角色权限","查看所有后台用户的角色",SubMenuEnum.角色权限,true),
	资源权限(4l,"/admin/resource","资源权限","为一个角色添加/删除页面访问权限",SubMenuEnum.角色权限,true),
	系统配置(5l,"/admin/system","系统配置","系统参数配置",SubMenuEnum.系统配置,true),
	;
	
	private ResourceEnum(Long code,String resource,String name,String desc, SubMenuEnum subMenu, Boolean loginRequired){
		this.code = code;
		this.resource = resource;
		this.name = name;
		this.desc = desc;
		this.subMenu = subMenu;
		this.loginRequired = loginRequired;
	}
	
	private Long code;
	private String resource;
	private String name;
	private String desc;
	private SubMenuEnum subMenu;
	private Boolean loginRequired;
	
	public Long getCode() {
		return code;
	}
	public String getUri(){
		return resource+".htm";
	}
	public String getResource() {
		return resource;
	}
	public String getName() {
		return name;
	}
	public String getDesc() {
		return desc;
	}
	public SubMenuEnum getSubMenu() {
		return subMenu;
	}
	public Boolean getLoginRequired() {
		return loginRequired;
	}
	
	public static List<ResourceEnum> getAll(){
		return Lists.newArrayList(ResourceEnum.values());
	}
	
	public static List<ResourceEnum> loginRequired(){
		List<ResourceEnum> list = Lists.newArrayList();
		for(ResourceEnum res : getAll()){
			if(res.loginRequired){
				list.add(res);
			}
		}
		return list;
	}
	
	public static ResourceEnum getByCode(Long code){
		if(code == null){
			return null;
		}
		for(ResourceEnum res : getAll()){
			if(res.code.longValue() == code.longValue()){
				return res;
			}
		}
		return null;
	}
	
	public static ResourceEnum getByResource(String resource){
		for(ResourceEnum res : getAll()){
			if(res.resource.equals(resource)){
				return res;
			}
		}
		return null;
	}
	
	public static boolean loginRequired(String resource){
		ResourceEnum resourceEnum = getByResource(resource);
		if(resourceEnum != null){
			return resourceEnum.loginRequired;
		} else {
			if(resource.startsWith("/admin")){
				return true;
			} else {
				return false;
			}
		}
	}
}
