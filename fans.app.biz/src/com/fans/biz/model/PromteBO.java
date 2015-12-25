package com.fans.biz.model;

import com.victor.framework.common.shared.Split;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.StringTools;

public class PromteBO {
	private Integer valve=0;
	private String expression="";
	private String discount="0";
	private Double precentage = 0.0d;
	private String text="";
	
	public PromteBO(String promte){
		if(StringTools.isEmpty(promte)){
			return;
		}
		String[] split = promte.split(Split.冒号);
		if(CollectionTools.isEmpty(split)){
			return;
		}
		expression = split[0];
		try {
			valve = Integer.parseInt(split[1]);
		} catch (NumberFormatException e) {
			expression = "";
			return;
		}
		discount = split[2];
		if(StringTools.isNotEmpty(discount)){
			if(discount.startsWith("+")){
				try {
					precentage = Double.parseDouble(discount.replace("+", "").replace("%", ""));
					precentage = precentage / 100.0d;
				} catch (NumberFormatException e) {
					expression = "";
					return;
				}
				text = discount.replace("+", "送");
			}
			if(discount.startsWith("-") && discount.endsWith("%")){
				try {
					precentage = Double.parseDouble(discount.replace("-", "").replace("%", ""));
					precentage = precentage / 100.0d;
				} catch (NumberFormatException e) {
					expression = "";
					return;
				}
				Double discountText = (1.0d-precentage)*10.0d;
				text = discountText+"折";
			}
		}
	}
	
	public boolean match(Integer cash){
		if(StringTools.isEmpty(expression)){
			return false;
		}
		if(expression.equals(">")){
			return cash > valve;
		}
		if(expression.equals(">=")){
			return cash >= valve;
		}
		if(expression.equals("=")){
			return cash == valve;
		}
		if(expression.equals("<")){
			return cash < valve;
		}
		if(expression.equals("<=")){
			return cash <= valve;
		}
		return false;
	}
	
	public Integer afterDiscount(Integer cost){
		if(discount.startsWith("+")){
			Double afterPrecentage = 1 + precentage;
			Double afterDiscount = cost * afterPrecentage;
			return afterDiscount.intValue();
		}
		if(discount.startsWith("-")){
			Double afterPrecentage = 1 - precentage;
			Double afterDiscount = cost * afterPrecentage;
			return afterDiscount.intValue();
		}
		return cost;
	}
	
	public String getText() {
		return text;
	}
}
