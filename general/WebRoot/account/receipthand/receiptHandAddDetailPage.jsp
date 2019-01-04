<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>生成交接单来源单据列表页面</title>
  </head>
  
  <body>
    <div style="padding-left: 10px;">
    	<table  border="0">
    		<tr>
				<td width="60">客户家数:</td>
				<td style="text-align: left;">
					<input type="text" id="select-customernum" style="width: 60px;" class="no_input" readonly="readonly" value="${customernum }"/>
				</td>
				<td width="60">总金额:</td>
				<td style="text-align: left;">
					<input type="text" id="select-amount-all" style="width: 100px;" class="no_input" readonly="readonly" value="${totalreceipttaxamount }"/>
				</td>
				<td width="60">选中金额:</td>
				<td style="text-align: left;">
					<input type="text" id="select-amount" style="width: 100px;" class="no_input" readonly="readonly" value="${totalreceipttaxamount }"/>
				</td>
    		</tr>
    	</table>
    </div>
    <div id="addReceiptHand-detail-div" style="overflow: auto;padding-left: 10px;">
    	<table class="tableinvoice">
    		<tr>
    			<td width="20"></td>
	            <td width="20"><input type="checkbox" id="allCheckbox" checked="checked"/></td>
	            <td width="100">编号</td>
	            <td width="80">单据类型</td>
	            <td width="100">订单编号</td>
	            <td width="70">业务日期</td>
	            <td width="50">客户编码</td>
	            <td width="180">客户名称</td>
	            <td width="60">客户业务员</td>
	            <td width="60">收款人</td>
	            <td width="80" align="right">应收金额</td>
    		</tr>
    		<c:forEach var="list" items="${list}" varStatus="status">
    			<tr id="tr-${list.id}" class="detailtr select" value="${list.id}">
		    		<td width="20" class="trclick" value="${list.id}">${status.index+1}</td>
		            <td  width="20">
		            	<input type="checkbox" id="checkbox-${list.id}" class="receipthand-check" name="ids" value="${list.id}" checked="checked"/>
		            	<input type="hidden" id="totalreceipttaxamount-${list.id}" value="${list.totalreceipttaxamount}">
		            	<input type="hidden" id="customerid-${list.id}" value="${list.customerid}">
		            </td>
		            <td width="100" class="trclick" value="id-td-${list.id}">${list.id}</td>
		            <td width="80" class="trclick" value="billtype-td-${list.id}">${list.billtypename}</td>
		            <td width="100" class="trclick" value="saleorderid-td-${list.id}">${list.saleorderid}</td>
		            <td width="70" class="trclick" value="businessdate-td-${list.id}">${list.businessdate}</td>
		            <td width="50" class="trclick" value="customerid-td-${list.id}">${list.customerid}</td>
		            <td width="180" class="trclick" value="customername-td-${list.id}">${list.customername}</td>
		            <td width="60" class="trclick" value="salesusername-td-${list.id}">${list.salesusername}</td>
		            <td width="60" class="trclick" value="payeename-td-${list.id}">${list.payeename}</td>
		            <td width="80" class="trclick" value="totalreceipttaxamount-td-${list.id}" align="right"><fmt:formatNumber value="${list.totalreceipttaxamount}" type="currency" pattern="0.00"/></td>
		        </tr>
    		</c:forEach>
    	</table>
    </div>
    <script type="text/javascript">
    	//加锁
	    function isDoLockData(id,tablename){
	    	var flag = false;
	    	$.ajax({
	            url :'system/lock/isDoLockData.do',
	            type:'post',
	            data:{id:id,tname:tablename},
	            dataType:'json',
	            async: false,
	            success:function(json){
	            	flag = json.flag
	            }
	        });
	        return flag;
	    }
    	$(function(){
    		$("#allCheckbox").click(function(){
				if($(this).attr("checked")){
					$(".receipthand-check").attr("checked",true);
					$("#select-amount").val($("#select-amount-all").val());
					$("#select-customernum").val("${customernum }");
					$(".detailtr").addClass("select");
				}else{
					$(".receipthand-check").attr("checked",false);
					$(".detailtr").removeClass("select");
					$("#select-amount").val("0");
					$("#select-customernum").val("0");
				}
			});
			$(".receipthand-check").click(function(){
				var id = $(this).val();
				if($(this).attr("checked")){
					$("#tr-"+id).addClass("select");
				}else{
					$("#tr-"+id).removeClass("select");
				}
				countTaxamount();
			});
			$(".tableinvoice .detailtr .trclick").click(function(){
				var id = $(this).attr("value");
				if($("#checkbox-"+id).attr("checked")){
					$("#checkbox-"+id).attr("checked",false);
					$("#tr-"+id).removeClass("select");
				}else{
					$("#checkbox-"+id).attr("checked",true);
					$("#tr-"+id).addClass("select");
				}
				countTaxamount();
			});
			
    	});
    	
    	function countTaxamount(){
			var totaltaxamount = $("#select-amount-all").val();
			$("#select-amount").val(totaltaxamount);
			var oldcustomerid = null;
			var customernum = 0;
			$(".receipthand-check").each(function () {
				var id = $(this).val();
                if(!$(this).attr("checked")){
                	var taxamount = $("#totalreceipttaxamount-"+id).val();
                	totaltaxamount = Number(totaltaxamount) - Number(taxamount);
                	$("#select-amount").val(formatterMoney(totaltaxamount));
                }else{
                	var customerid = $("#customerid-"+id).val();
                	if(customerid != oldcustomerid){
                		customernum++;
                		oldcustomerid = customerid;
                	}
                	$("#select-customernum").val(customernum);
                }
            });
		}
		
		//新增交接单
		function addReceiptHandByRefer(){
			$.messager.confirm("提醒","是否生成新的交接单？",function(r){
				if(r){
		    		var ids = null;
		    		$(".receipthand-check").each(function () {
		                if($(this).attr("checked")){
		                	if(ids==null){
								ids = $(this).val();
							}else{
								ids +="," + $(this).val();
							}
		                }
		            });
		    		if(ids==null){
		    			$.messager.alert("提醒","请选择数据");
		    			return false;
		    		}
					loading("生成中..");
					$.ajax({
						url:'account/receipthand/addReceiptHand.do',
						dataType:'json',
						type:'post',
						data:{ids:ids},
						success:function(json){
							loaded();
							if(json.flag){
			            		$.messager.alert("提醒","生成成功");
			            		var flag = isDoLockData(json.id,"t_account_receipt");
				 				if(!flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
				 					return false;
				 				}
			            		top.addOrUpdateTab('account/receipthand/showReceiptHandPage.do?type=edit&id='+ json.id, "交接单查看");
			            		$("#account-panel-receipthandDetailPage").dialog('close');
			            		var queryJSON2 = $("#account-form-receipthand").serializeJSON();
								$("#account-datagrid-receipthand").datagrid({
						       		url: 'account/receipthand/getReceiptListGroupByCustomerForReceiptHand.do',
					      			pageNumber:1,
									queryParams:queryJSON2
						       	}).datagrid("columnMoving");
						       	$("#account-datagrid-receipthand").datagrid('clearChecked');
							}else{
								$.messager.alert("提醒","生成失败<br/>");
							}
						},
						error:function(){
							loaded();
						}
					});
				}
			});
		}
    </script>
  </body>
</html>
