package com.pg.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum ExtensionEnum {
	JSON("json",false),
	HTM("htm",true);
	
	private String  extension;
	private Boolean authRequired;
	
	private ExtensionEnum(String extension,Boolean authRequired){
		this.extension = extension;
		this.authRequired = authRequired;
	}

	public String getExtension() {
		return extension;
	}

	public Boolean getAuthRequired() {
		return authRequired;
	}
	
	public static List<ExtensionEnum> getAll(){
		return Lists.newArrayList(ExtensionEnum.values());
	}
	
	public static ExtensionEnum get(String extension){
		for(ExtensionEnum extensionEnum : getAll()){
			if(extensionEnum.getExtension().equals(extension)){
				return extensionEnum;
			}
		}
		return null;
	}
	
}
