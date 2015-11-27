package com.pg.web.admin.webpage.screen.export;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.buffered.BufferedRequestContext;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.google.common.collect.Lists;
import com.pg.biz.manager.WarehouseManager;
import com.pg.dal.model.WarehouseDO;
import com.pg.dal.query.WarehouseQueryCondition;
import com.victor.framework.dal.basic.Paging;


public class Mywarehouse {
	
	@Autowired
	private BufferedRequestContext buffered;
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private WarehouseManager warehouseManager;
	
	public void execute(@Params WarehouseQueryCondition queryCondition,
						Context context) throws Exception{
		queryCondition.setSystem(true);
		buffered.setBuffering(false);
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter(); 

		List<NameValuePair> pairs = Lists.newArrayList();
		pairs.add(new NameValuePair("","3%"));
		pairs.add(new NameValuePair("名称","12%"));
		pairs.add(new NameValuePair("省","5%"));
		pairs.add(new NameValuePair("市","5%"));
		pairs.add(new NameValuePair("区","5%"));
		pairs.add(new NameValuePair("地址","15%"));
		pairs.add(new NameValuePair("联系人","15%"));
		pairs.add(new NameValuePair("身份证","15%"));
		pairs.add(new NameValuePair("备注","25%"));
		printTableHead(out,pairs);
		
		int count = 0;
		
		Paging<WarehouseDO> pageList = Paging.emptyPage();
		queryCondition.setPage(1);
		pageList = warehouseManager.getPage(queryCondition);
		for(WarehouseDO warehouseDO : pageList.getData()){
			count++;
			printTableRow(out,count,warehouseDO);
		}
		
		for(int page=2;page<=pageList.getTotalPage();page++){
			queryCondition.setPage(page);
			pageList = warehouseManager.getPage(queryCondition);
			for(WarehouseDO warehouseDO : pageList.getData()){
				count++;
				printTableRow(out,count,warehouseDO);
			}
		}
		
		printTableFoot(out,count);
	}
	
	private void printTableHead(PrintWriter out,List<NameValuePair> pairs) throws Exception{
		out.write("<div><a href='#' onclick='javascript:history.go(-2)'>返回</a>&nbsp;<a href='#' onclick='javascript: window.print();'>打印</a></div>");
		out.write("<table>");
		out.write("<tr class='head'>");
		for(NameValuePair head : pairs){
			out.write("<td style='width:"+head.getValue()+"'>"+head.getName()+"</td>");
		}
		out.write("</tr>");
	}
	
	private void printTableRow(PrintWriter out, int count, WarehouseDO warehouseDO) throws Exception{
		if(count % 2 == 0){
			out.write("<tr class='row'>");
		} else {
			out.write("<tr class='row odd'>");
		}
		
		out.write("<td style='text-align:center'>"+count+"</td>");
		out.write("<td>"+warehouseDO.getName()+"</td>");
		out.write("<td>"+warehouseManager.getProvince(warehouseDO)+"</td>");
		out.write("<td>"+warehouseManager.getCity(warehouseDO)+"</td>");
		out.write("<td>"+warehouseManager.getTown(warehouseDO)+"</td>");
		out.write("<td>"+warehouseDO.getAddress()+"</td>");
		out.write("<td>"+getContact(warehouseDO)+"</td>");
		out.write("<td>"+warehouseDO.getKeeperIdCard()+"</td>");
		out.write("<td>"+warehouseDO.getComment()+"</td>");
		out.write("</tr>");
	}
	
	private void printTableFoot(PrintWriter out,int count) throws Exception{
		out.write("</table><div>共"+count+"条数据</div>");
	}
	
	private String getContact(WarehouseDO warehouseDO){
		return warehouseDO.getKeeper() + "("+warehouseDO.getMobile()+" "+warehouseDO.getPhone()+")";
	}
}
