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
import com.pg.biz.manager.CustomerManager;
import com.pg.biz.manager.EmployeeManager;
import com.pg.dal.model.CustomerDO;
import com.pg.dal.model.EmployeeDO;
import com.pg.dal.query.CustomerQueryCondition;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.Paging;

public class Customer {
	
	@Autowired
	private BufferedRequestContext buffered;
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private EmployeeManager employeeManager;
	
	public void execute(@Params CustomerQueryCondition queryCondition,
						Context context) throws Exception{
		buffered.setBuffering(false);
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter(); 

		List<NameValuePair> pairs = Lists.newArrayList();
		pairs.add(new NameValuePair("","3%"));
		pairs.add(new NameValuePair("注册时间","15%"));
		pairs.add(new NameValuePair("称呼","15%"));
		pairs.add(new NameValuePair("身份证号","15%"));
		pairs.add(new NameValuePair("电话号码","15%"));
		pairs.add(new NameValuePair("归属坐席","15%"));
		pairs.add(new NameValuePair("推荐人手机","22%"));
		printTableHead(out,pairs);
		
		int count = 0;
		
		Paging<CustomerDO> pageList = Paging.emptyPage();
		queryCondition.setPage(1);
		pageList = customerManager.getPage(queryCondition);
		for(CustomerDO customerDO : pageList.getData()){
			count++;
			printTableRow(out,count,customerDO);
		}
		
		for(int page=2;page<=pageList.getTotalPage();page++){
			queryCondition.setPage(page);
			pageList = customerManager.getPage(queryCondition);
			for(CustomerDO customerDO : pageList.getData()){
				count++;
				printTableRow(out,count,customerDO);
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
	
	private void printTableRow(PrintWriter out, int count, CustomerDO customerDO) throws Exception{
		if(count % 2 == 0){
			out.write("<tr class='row'>");
		} else {
			out.write("<tr class='row odd'>");
		}
		
		out.write("<td style='text-align:center'>"+count+"</td>");
		out.write("<td>"+DateTools.DateToString(customerDO.getGmtCreate())+"</td>");
		out.write("<td>"+customerDO.getName()+"</td>");
		out.write("<td>"+customerDO.getIdCard()+"</td>");
		out.write("<td>"+customerDO.getMobile()+"</td>");
		out.write("<td>"+getEmployeeName(customerDO.getEmployeeId())+"</td>");
		out.write("<td>"+customerDO.getRecommender()+"</td>");
		out.write("</tr>");
	}
	
	private void printTableFoot(PrintWriter out,int count) throws Exception{
		out.write("</table><div>共"+count+"条数据</div>");
	}
	
	private String getEmployeeName(Long employeeId){
		if(employeeId == null){
			return "";
		}
		EmployeeDO employeeDO = employeeManager.getById(employeeId);
		if(employeeDO == null){
			return "";
		} else {
			return employeeDO.getName();
		}
	}
}
