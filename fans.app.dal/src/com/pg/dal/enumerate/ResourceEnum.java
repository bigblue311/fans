package com.pg.dal.enumerate;

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
	系统通知(6l,"/admin/news","系统通知","系统通知配置",SubMenuEnum.系统通知,true),
	
	品牌管理(7l,"/admin/brand","品牌管理","管理所拥有的品牌,例如飘柔,海飞丝等",SubMenuEnum.品牌管理,true),
	品类管理(8l,"/admin/category","品类管理","管理所拥有的品类,例如洗发水,香皂,沐浴露等",SubMenuEnum.品类管理,true),
	产品管理(9l,"/admin/product","产品管理","管理所拥有的产品",SubMenuEnum.产品管理,true),
	产品打包(10l,"/admin/package","产品打包","将多个产品定义为一个产品包",SubMenuEnum.产品打包,true),
	商品包关系(11l,"/admin/prodpack","商品包关系","产品与产品包的包含关系",SubMenuEnum.产品打包,true),
	
	客户查询(12l,"/admin/customer","客户查询","查看目前拥有的客户",SubMenuEnum.客户查询,true),
	我的客户(13l,"/admin/mycustomer","我的仓库","查看目前坐席拥有的客户",SubMenuEnum.我的客户,true),
	
	我的仓库(14l,"/admin/mywarehouse","我的仓库","管理我所拥有的发货仓库",SubMenuEnum.我的仓库,true),
	客户仓库(15l,"/admin/warehouse","客户仓库","管理客户的发货仓库/地址",SubMenuEnum.客户仓库,true),
	
	商品发布(16l,"/admin/publish","商品发布","将事先定义好的商品或者商品包发布到上架",SubMenuEnum.商品发布,true),
	交易管理(17l,"/admin/purchase","交易管理","查询订单商品列表",SubMenuEnum.交易管理,true),
	订单管理(18l,"/admin/order","订单管理","查询订单列表",SubMenuEnum.订单管理,true),
	订单详情(19l,"/admin/orderdetail","订单详情","查看/修改订单详情",SubMenuEnum.订单管理,true),
	
	产品导出(20l,"/admin/export/product","产品导出","导出查询结果",SubMenuEnum.产品管理,true),
	产品包导出(21l,"/admin/export/package","产品包导出","导出查询结果",SubMenuEnum.产品管理,true),
	客户信息导出(22l,"/admin/export/customer","客户信息导出","导出目前拥有的客户",SubMenuEnum.客户查询,true),
	我的仓库导出(23l,"/admin/export/mywarehouse","我的仓库导出","导出我所拥有的发货仓库",SubMenuEnum.我的仓库,true),
	客户仓库导出(24l,"/admin/export/warehouse","客户仓库导出","导出客户的发货仓库/地址信息",SubMenuEnum.客户仓库,true),
	商品发布导出(25l,"/admin/export/publish","商品发布导出","导出将事先定义好的商品或者商品包发布信息",SubMenuEnum.商品发布,true),
	交易记录导出(26l,"/admin/export/purchase","交易记录导出","导出订单商品列表",SubMenuEnum.交易管理,true),
	订单记录导出(27l,"/admin/export/order","订单记录导出","导出订单列表",SubMenuEnum.订单管理,true),
	订单详情导出(28l,"/admin/export/orderdetail","订单详情导出","导出订单详情",SubMenuEnum.订单管理,true),
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
