<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0"/>
    <title>订单追踪</title>
    <style type="text/css">
    	.main{}
    	.item{padding:10px 5px;line-height:20px;}
    </style>
  </head>
  <body style="margin:0;padding:0;">
    <c:if test="${track.status == 0}">
    	未查询到订单信息
    </c:if>
    <c:if test="${track.status == 1}">
    	<div class="main">
    		<c:if test="${track.orderstatus == 3 or track.orderstatus == 4}">
    			<c:if test="${track.dbillstatus == 3 or track.dbillstatus == 4}">
    				<c:if test="${track.outstatus == 3 or track.outstatus == 4}">
    					<c:if test="${track.recstatus == 3 or track.recstatus == 4}">
    						<c:if test="${track.isinvoice == 1}">
    							<c:if test="${track.iswriteoff == 1}">
					    			<div style="padding:10px 5px;line-height:20px;">
					    				<c:out value="${track.writeoffdate }"></c:out> 已核销
					    			</div>
					    		</c:if>
				    			<div style="padding:10px 5px;line-height:20px;">
				    			<c:out value="${track.invoicedate }"></c:out> 已开票
				    			</div>
				    		</c:if>
			    			<div class="item" onclick="showSeceiptList();"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${track.recaudittime }"/> 客户已签收</div>
			    			<div id="receiptView" style="display: none;">回单编号：${track.recid}</div>
			    		</c:if>
		    			<div class="item" onclick="showPsmsgList();"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${track.outaudittime }"/> 已出库，正在送货</div>
		    			<div id="psmsgView" <c:if test="${track.showpsmsg==false }">style="display: none;" </c:if>>配送信息：${track.psmsg}</div>
		    		</c:if>
	    			<div class="item" onclick="showSaleoutList();"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${track.dbillaudittime }"/> 已配货，正在出库</div>
	    			<div id="saleoutView" style="display: none;">发货单编号：${track.outid}</div>
	    		</c:if>
    			<div class="item" onclick="showDbillList();"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${track.orderaudittime }"/> 正在配货</div>
    			<div id="dbillView" style="display: none;">发货通知单编号：${track.dbillid}</div>
    			<div class="item" onclick="showOrderList();"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${track.orderaddtime }"/> 已下单</div>
    			<div id="orderidView" style="display: none;">订单编号：${track.orderid}</div>
    		</c:if>
    		<c:if test="${track.orderstatus == 2}">
    			<div class="item" onclick="showOrderList();"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${track.orderaddtime }"/> 已下单</div>
    			<div id="orderidView" style="display: none;">订单编号：${track.orderid}</div>
    		</c:if>
    	</div>
    </c:if>
  </body>
  <script type="text/javascript">
	function showOrderList(){
		var target = document.getElementById("orderidView");
		if(target.style.display=="block"){
			target.style.display="none";
		}else{
			target.style.display="block";
		}
	}
	function showDbillList(){
		var target = document.getElementById("dbillView");
		if(target.style.display=="block"){
			target.style.display="none";
		}else{
			target.style.display="block";
		}
	}
	function showSaleoutList(){
		var target = document.getElementById("saleoutView");
		if(target.style.display=="block"){
			target.style.display="none";
		}else{
			target.style.display="block";
		}
	}
	function showSeceiptList(){
		var target = document.getElementById("receiptView");
		if(target.style.display=="block"){
			target.style.display="none";
		}else{
			target.style.display="block";
		}
	}
	function showPsmsgList(){
		var target = document.getElementById("psmsgView");
		if(target.style.display=="block"){
			target.style.display="none";
		}else{
			target.style.display="block";
		}
	}
	function callAndroid(name,mobile){  
		window.agent.call(name,mobile);  
	}
  </script>
</html>
