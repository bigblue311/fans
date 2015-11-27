package com.victor.framework.common.tools;

import com.victor.framework.common.shared.Split;

public class UriTools {
	
	public static String getResource(String uri){
		if(StringTools.isEmpty(uri)){
			return "";
		}
		if(uri.contains(".")){
			return uri.split(Split.点)[0];
		} else {
			return uri;
		}
	}
	
	public static String getExtension(String uri){
		if(StringTools.isEmpty(uri)){
			return "";
		}
		if(uri.contains(".")){
			String[] strs = uri.split(Split.点);
			if(strs.length>1){
				String extension = strs[1];
				if(StringTools.isNotEmpty(extension)){
					return extension.toLowerCase();
				}
			}
		}
		return "";
	}
	
}
