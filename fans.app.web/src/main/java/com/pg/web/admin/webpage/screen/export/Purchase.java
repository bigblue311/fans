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
import com.pg.biz.manager.TransactionManager;
import com.pg.biz.model.PurchaseVO;
import com.pg.dal.model.CustomerDO;
import com.pg.dal.model.OrderDO;
import com.pg.dal.model.PurchaseDO;
import com.pg.dal.model.PurchaseItemDO;
import com.pg.dal.query.PurchaseQueryCondition;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.Paging;

public class Purchase {
	
	@Autowired
	private BufferedRequestContext buffered;
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private CustomerManager customerManager;
	
	public void execute(@Params PurchaseQueryCondition queryCondition,
						Context context) throws Exception {
		buffered.setBuffering(false);
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter(); 
		
		List<NameValuePair> pairs = Lists.newArrayList();
		pairs.add(new NameValuePair("","3%"));
		pairs.add(new NameValuePair("创建时间","10%"));
		pairs.add(new NameValuePair("名称","5%"));
		pairs.add(new NameValuePair("标题","10%"));
		pairs.add(new NameValuePair("客户","12%"));
		pairs.add(new NameValuePair("运费","5%"));
		pairs.add(new NameValuePair("总价","5%"));
		pairs.add(new NameValuePair("发货地址","12%"));
		pairs.add(new NameValuePair("收货地址","13%"));
		pairs.add(new NameValuePair("收货人","10%"));
		pairs.add(new NameValuePair("备注","15%"));
		printTableHead(out,pairs);
		
		int count = 0;
		
		Paging<PurchaseVO> pageList = Paging.emptyPage();
		queryCondition.setPage(1);
		pageList = transactionManager.getPurchaseVOPage(queryCondition);
		for(PurchaseVO purchaseVO : pageList.getData()){
			count++;
			printTableRow(out,count,purchaseVO);
		}
		
		for(int page=2;page<=pageList.getTotalPage();page++){
			queryCondition.setPage(page);
			pageList = transactionManager.getPurchaseVOPage(queryCondition);
			for(PurchaseVO purchaseVO : pageList.getData()){
				count++;
				printTableRow(out,count,purchaseVO);
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
	
	private void printTableRow(PrintWriter out, int count, PurchaseVO purchaseVO) throws Exception{
		if(count % 2 == 0){
			out.write("<tr class='row'>");
		} else {
			out.write("<tr class='row odd'>");
		}
		
		out.write("<td style='text-align:center'>"+count+"</td>");
		out.write("<td>"+DateTools.DateToString(purchaseVO.getGmtCreate())+"</td>");
		out.write("<td>"+purchaseVO.getName()+"</td>");
		out.write("<td>"+purchaseVO.getTitle()+"</td>");
		out.write("<td>"+getCustomer(purchaseVO)+"</td>");
		out.write("<td>"+getTransport(purchaseVO)+"</td>");
		out.write("<td>"+getTotal(purchaseVO)+"</td>");
		out.write("<td>"+purchaseVO.getAddressFrom()+"</td>");
		out.write("<td>"+purchaseVO.getAddressTo()+"</td>");
		out.write("<td>"+getContact(purchaseVO)+"</td>");
		out.write("<td>"+purchaseVO.getComment()+"</td>");
		out.write("</tr>");
	}
	
	private void printTableFoot(PrintWriter out,int count) throws Exception{
		out.write("</table><div>共"+count+"条数据</div>");
	}
	
	private String getContact(PurchaseDO purchaseDO){
		return purchaseDO.getKeeper() + "("+purchaseDO.getMobile()+")";
	}
	
	private String getTransport(PurchaseDO purchaseDO){
		return purchaseDO.getTransportFee() + "("+purchaseDO.getTransportCode()+")";
	}
	
	private String getCustomer(PurchaseDO purchaseDO){
		if(purchaseDO == null) {
			return "";
		}
		OrderDO orderDO = transactionManager.getOrderDOById(purchaseDO.getOrderId());
		if(orderDO == null){
			return "";
		}
		CustomerDO customerDO = customerManager.getById(orderDO.getCustomerId());
		if(customerDO == null) {
			return "";
		}
		return customerDO.getName() + "("+customerDO.getMobile()+")";
	}
	
	private String getTotal(PurchaseVO purchaseVO){
		if(purchaseVO == null){
			return "";
		}
		List<PurchaseItemDO> itemList = purchaseVO.getItemList();
		Double total = 0d;
		for(PurchaseItemDO item : itemList){
			Double price = item.getPrice();
			Integer quantity = item.getQuantity();
			if(price == null || quantity == null){
				continue;
			}
			total+=(price*quantity);
		}
		
		return total+"";
	}
}
