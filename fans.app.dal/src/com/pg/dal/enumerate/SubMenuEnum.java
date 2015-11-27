package com.pg.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum SubMenuEnum {
	
	后台账号("subMenuEmployee","后台账号","/admin/employee",TopMenuEnum.系统管理),
	角色权限("subMenuResource","角色权限","/admin/role",TopMenuEnum.系统管理),
	系统配置("subMenuSystem","系统配置","/admin/system",TopMenuEnum.系统管理),
	系统通知("subMenuNews","系统通知","/admin/news",TopMenuEnum.系统管理),
	
	品牌管理("subMenuBrand","品牌管理","/admin/brand",TopMenuEnum.产品管理),
	品类管理("subMenuCategory","品类管理","/admin/category",TopMenuEnum.产品管理),
	产品管理("subMenuProduct","产品管理","/admin/product",TopMenuEnum.产品管理),
	产品打包("subMenuPackage","产品打包","/admin/package",TopMenuEnum.产品管理),
	商品发布("subMenuPublish","商品发布","/admin/publish",TopMenuEnum.产品管理),
	
	客户查询("subMenuCustomer","客户查询","/admin/customer",TopMenuEnum.客户管理),
	我的客户("subMenuMyCustomer","我的客户","/admin/mycustomer",TopMenuEnum.客户管理),
	
	我的仓库("subMenuMyWarehouse","我的仓库","/admin/mywarehouse",TopMenuEnum.物流管理),
	客户仓库("subMenuCuWarehouse","客户仓库","/admin/warehouse",TopMenuEnum.物流管理),
	
	交易管理("subMenuPurchase","交易管理","/admin/purchase",TopMenuEnum.交易管理),
	订单管理("subMenuOrder","订单管理","/admin/order",TopMenuEnum.交易管理);
	
	private SubMenuEnum(String code,String desc, String resource,TopMenuEnum topMenu){
		this.code = code;
		this.desc = desc;
		this.resource = resource;
		this.topMenu = topMenu;
	}
	
	private String code;
	private String desc;
	private String resource;
	private TopMenuEnum topMenu;
	
	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public String getResource() {
		return resource;
	}

	public String getUri() {
		return resource+".htm";
	}

	public TopMenuEnum getTopMenu(){
		return topMenu;
	}
	
	public static List<SubMenuEnum> getAll(){
		return Lists.newArrayList(SubMenuEnum.values());
	}
	
	public static List<SubMenuEnum> getByTopMenu(String topMenuCode){
		List<SubMenuEnum> list = Lists.newLinkedList();
		for(SubMenuEnum menu : getAll()){
			if(menu.topMenu.getCode().equals(topMenuCode)){
				list.add(menu);
			}
		}
		return list;
	}
	
	public static SubMenuEnum getByCode(String code){
		for(SubMenuEnum menu : getAll()){
			if(menu.code.equals(code)){
				return menu;
			}
		}
		return null;
	}
	
	public static SubMenuEnum getByDesc(String desc){
		for(SubMenuEnum menu : getAll()){
			if(menu.desc.equals(desc)){
				return menu;
			}
		}
		return null;
	}
	
	public static SubMenuEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
