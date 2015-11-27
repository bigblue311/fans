package com.pg.web.admin.model.json;

import java.util.List;

public class TopMenuJson {
	private String text;
	private boolean collapsed = true;
	private List<SubMenuJson> items;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isCollapsed() {
		return collapsed;
	}
	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}
	public List<SubMenuJson> getItems() {
		return items;
	}
	public void setItems(List<SubMenuJson> items) {
		this.items = items;
	}
}
