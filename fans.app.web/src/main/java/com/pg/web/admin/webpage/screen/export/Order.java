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
import com.pg.biz.manager.TransactionManager;
import com.pg.dal.enumerate.OrderStatusEnum;
import com.pg.dal.model.OrderDO;
import com.pg.dal.query.OrderQueryCondition;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.Paging;

public class Order {
	
	@Autowired
	private BufferedRequestContext buffered;
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private TransactionManager transactionManager;
	
	public void execute(@Params OrderQueryCondition queryCondition,
						Context context) throws Exception{
		buffered.setBuffering(false);
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter(); 

		List<NameValuePair> pairs = Lists.newArrayList();
		pairs.add(new NameValuePair("","3%"));
		pairs.add(new NameValuePair("创建时间","15%"));
		pairs.add(new NameValuePair("客户","7%"));
		pairs.add(new NameValuePair("电话","10%"));
		pairs.add(new NameValuePair("身份证","15%"));
		pairs.add(new NameValuePair("定金","5%"));
		pairs.add(new NameValuePair("总价","5%"));
		pairs.add(new NameValuePair("运费","5%"));
		pairs.add(new NameValuePair("合计","5%"));
		pairs.add(new NameValuePair("状态","5%"));
		pairs.add(new NameValuePair("备注","25%"));
		printTableHead(out,pairs);
		
		int count = 0;
		
		Paging<OrderDO> pageList = Paging.emptyPage();
		queryCondition.setPage(1);
		pageList = transactionManager.getOrderDOPage(queryCondition);
		for(OrderDO orderDO : pageList.getData()){
			count++;
			printTableRow(out,count,orderDO);
		}
		
		for(int page=2;page<=pageList.getTotalPage();page++){
			queryCondition.setPage(page);
			pageList = transactionManager.getOrderDOPage(queryCondition);
			for(OrderDO orderDO : pageList.getData()){
				count++;
				printTableRow(out,count,orderDO);
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
	
	private void printTableRow(PrintWriter out, int count, OrderDO orderDO) throws Exception{
		if(count % 2 == 0){
			out.write("<tr class='row'>");
		} else {
			out.write("<tr class='row odd'>");
		}
		
		out.write("<td style='text-align:center'>"+count+"</td>");
		out.write("<td>"+DateTools.DateToString(orderDO.getGmtCreate())+"</td>");
		out.write("<td>"+orderDO.getCustomerName()+"</td>");
		out.write("<td>"+orderDO.getCustomerMobile()+"</td>");
		out.write("<td>"+orderDO.getCustomerIdCard()+"</td>");
		out.write("<td>"+orderDO.getDeposit()+"</td>");
		out.write("<td>"+orderDO.getTotalPrice()+"</td>");
		out.write("<td>"+orderDO.getTransportFee()+"</td>");
		out.write("<td>"+getTotal(orderDO)+"</td>");
		out.write("<td>"+OrderStatusEnum.getByCode(orderDO.getStatus())+"</td>");
		out.write("<td>"+orderDO.getComment()+"</td>");
		out.write("</tr>");
	}
	
	private void printTableFoot(PrintWriter out,int count) throws Exception{
		out.write("</table><div>共"+count+"条数据</div>");
	}
	
	private String getTotal(OrderDO orderDO){
		return orderDO.getTotalPrice() + orderDO.getTransportFee() + "";
	}
}
