<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
  	<div style="padding-left: 10px;">
  	<table  border="0">
		<tr>
			<td width="80">客户名称:</td>
			<td style="text-align: left;">
				<select id="select-customerid-receiptAndReject" name="customerid" style="width: 150px;">
					<c:forEach items="${customerMap}" var="entry">   
					<option value="${entry.key}" <c:if test="${entry.key==customerid}">selected="selected"</c:if>>${entry.value}</option> 
					</c:forEach> 
				</select>
			</td>
			<td width="80">客户余额:</td>
			<td>
				<input type="text" id="select-amount-customer" style="width: 100px;" class="no_input" value=""/>
			</td>
 		</tr>
 		<tr>
 			<td width="80">总 金 额:</td>
			<td style="text-align: left;">
				<input type="text" id="select-amount-all" style="width: 150px;" class="no_input" readonly="readonly" value="${totalamount }"/>
			</td>
			<td width="80">选中金额:</td>
			<td style="text-align: left;">
				<input type="text" id="select-amount" style="width: 100px;" class="no_input" readonly="readonly" value="${totalamount }"/>
			</td>
 		</tr>
	</table>
	</div>
	<div id="receiptAndreject-detail-div" style="overflow: auto;padding-left: 10px;">
	    <table class="tableinvoice">
	    	<tr>
	        	<td width="20"></td>
	            <td width="20" align="center"><input type="checkbox" id="allCheckbox" checked="checked"/></td>
	            <td width="130">单据编号</td>
	            <td width="85">单据类型</td>
	            <td width="50">商品编码</td>
	            <td width="180">商品名称</td>
	            <td width="85">条形码</td>
                <td width="50">商品箱价</td>
	            <td width="40" align="right">箱装量</td>
	            <td width="30">单位</td>
	            <td width="40" align="right">数量</td>
	            <td width="30" align="right">单价</td>
	            <td width="40" align="right">金额</td>
	            <td width="60">税种</td>
	            <td width="50" align="right">辅数量</td>
	        </tr>
	    	<c:forEach var="list" items="${list}" varStatus="status">
	    	<tr id="tr-${list.billid}-${list.id}" class="detailtr select" value="${list.billid}-${list.id}">
	    		<td width="20" class="trclick" value="${list.billid}-${list.id}">${status.index+1}</td>
	            <td  width="20">
	            	<input type="checkbox" id="checkbox-${list.billid}-${list.id}" class="salesinvoice-check" name="ids" value="${list.billid}-${list.id}" checked="checked"/>
	            	<input type="hidden" id="billtype-${list.billid}-${list.id}" value="${list.billtype}">
	            	<input type="hidden" id="detailid-${list.billid}-${list.id}" value="${list.id}">
	            	<input type="hidden" id="billid-${list.billid}-${list.id}" value="${list.billid}">
	            	<input type="hidden" id="taxamount-${list.billid}-${list.id}" value="${list.taxamount}">
	            	<input type="hidden" id="isdiscount-${list.billid}-${list.id}" value="${list.isdiscount}">
	            	<input type="hidden" id="taxtype-${list.billid}-${list.id}" value="${list.taxtype}" line="${status.index+1}">
	            </td>
	            <td width="130" class="trclick" value="${list.billid}-${list.id}">${list.billid}</td>
	            <td width="85" class="trclick" value="${list.billid}-${list.id}">
	            <c:if test="${list.billtype=='1'}">销售发货回单</c:if>
	    		<c:if test="${list.billtype=='2'}">销售退货通知单</c:if>
	    		<c:if test="${list.billtype=='3'}">冲差单</c:if>
	    		<c:if test="${list.billtype=='4'}">应收款期初</c:if>
	    		</td>
	            <td width="50" class="trclick" value="${list.billid}-${list.id}">${list.goodsid}</td>
	            <td width="180" class="trclick" value="${list.billid}-${list.id}">${list.goodsInfo.name}</td>
	            <td width="85" class="trclick" value="${list.billid}-${list.id}">${list.goodsInfo.barcode}</td>
                <td width="50" class="trclick" value=""  align="right">${list.boxprice}</td>
	            <td width="40" class="trclick" value="${list.billid}-${list.id}" align="right"><c:if test="${list.goodsInfo.boxnum!=null && list.goodsInfo.boxnum >0}"><fmt:formatNumber value="${list.goodsInfo.boxnum}" pattern="${pattern}"/></c:if></td>
	            <td width="30" class="trclick" value="${list.billid}-${list.id}">${list.unitname}</td>
	            <td width="40" class="trclick" value="${list.billid}-${list.id}" align="right"><c:if test="${list.unitnum !=null }"><fmt:formatNumber value="${list.unitnum}" pattern="${pattern}"/></c:if></td>
	            <td width="30" class="trclick" value="${list.billid}-${list.id}" align="right"><c:if test="${list.taxprice !=null}"><fmt:formatNumber value="${list.taxprice}" type="currency" pattern="0.00"/></c:if></td>
	            <td width="40" class="trclick" value="${list.billid}-${list.id}" align="right"><fmt:formatNumber value="${list.taxamount}" type="currency" pattern="0.00"/></td>
	            <td width="60" class="trclick" value="${list.billid}-${list.id}"><c:if test="${!empty(list.taxtype)}">${list.taxtypename}</c:if></td>
	            <td width="50" class="trclick" value="${list.billid}-${list.id}" align="right">${list.auxnumdetail}</td>
	        </tr>
	    	</c:forEach>
		</table>
	</div>
	<div id="receiptAndreject-salesinvoice-old-list"></div>
	<script type="text/javascript">
		$(function(){
			$("#allCheckbox").click(function(){
				if($(this).attr("checked")){
					$(".salesinvoice-check").attr("checked",true);
					$("#select-amount").val($("#select-amount-all").val());
					$(".detailtr").addClass("select");
				}else{
					$(".salesinvoice-check").attr("checked",false);
					$(".detailtr").removeClass("select");
					$("#select-amount").val("0");
				}
			});
			$(".salesinvoice-check").click(function(){
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
			$("#select-customerid-receiptAndReject").change(function(){
				var customerid = $(this).val();
				$.ajax({   
		            url :'account/receivable/getCustomerCapital.do?id='+customerid,
		            type:'post',
		            dataType:'json',
		            success:function(json){
		            	$("#select-amount-customer").val(formatterMoney(json.camount));
		            }
		        });
			});
		});
		
		function countTaxamount(){
			var totaltaxamount = $("#select-amount-all").val();
			$("#select-amount").val(totaltaxamount);
			$(".salesinvoice-check").each(function () {
                if(!$(this).attr("checked")){
                	var id = $(this).val();
                	var taxamount = $("#taxamount-"+id).val();
                	totaltaxamount = Number(totaltaxamount) - Number(taxamount);
                	$("#select-amount").val(formatterMoney(totaltaxamount));
                }
            });
		}
		//生成申请单
		function addSalesInvoiceByRefer(){
			var rows = [];
			$(".salesinvoice-check").each(function () {
                if($(this).attr("checked")){
                	rows.push($(this).val());
                }
            });
			if(rows==null || rows.length==0){
				$.messager.alert("提醒","请选择数据");
				return false;
			}
			var ids = [];
			for(var i=0;i<rows.length;i++){
				var val = rows[i];
				var billtype = $("#billtype-"+val).val();
				var billid = $("#billid-"+val).val();
				var detailid = $("#detailid-"+val).val();
				var isdiscount = $("#isdiscount-"+val).val();
				var object = {billtype:billtype,billid:billid,detailid:detailid,isdiscount:isdiscount}
				ids.push(object);
			}
			var customerid = $("#select-customerid-receiptAndReject").val();
			$.messager.confirm("提醒","是否确定销售核销？",function(r){
				if(r){
					loading("提交中..");
					$.ajax({   
			            url :'account/receivable/addSalesInvoiceByRefer.do?iswriteoff=0',
			            type:'post',
			            dataType:'json',
			            data:{ids:JSON.stringify(ids),customerid:customerid},
			            success:function(json){
			            	loaded();
			            	if(json.flag){
			            		$.messager.alert("提醒","生成成功");

			            		var title = parent.getNowTabTitle();
                                if (top.$('#tt').tabs('exists',title)){
                                    var queryJSON = tabsWindow(title).$("#account-form-query-salesInvoiceSouceBill").serializeJSON();
                                    tabsWindow(title).$("#account-orderDatagrid-dispatchBillSourcePage").datagrid('load',queryJSON);
                                    tabsWindow(title).$("#account-orderDatagrid-dispatchBillSourcePage").datagrid('clearSelections');
                                    tabsWindow(title).$("#account-orderDatagrid-dispatchBillSourcePage").datagrid('clearChecked');
                                }
                                $("#account-panel-salesinvoiceDetailPage1").dialog('close');
			            		top.addOrUpdateTab('account/receivable/showSalesInvoiceEditPage.do?id='+ json.id, "销售核销查看");
							}else{
								$.messager.alert("提醒","生成失败<br/>"+json.msg);
							}
			            },
			            error:function(){
			            	loaded();
			            	$.messager.alert("错误","销售核销出错");
			            }
			        });
	        	}
	        });
		}
		//申请核销
		function addSalesInvoiceWithWriteOff(){
			var rows = [];
			$(".salesinvoice-check").each(function () {
                if($(this).attr("checked")){
                	rows.push($(this).val());
                }
            });
			if(rows==null || rows.length==0){
				$.messager.alert("提醒","请选择数据");
				return false;
			}
			var ids = [];
			for(var i=0;i<rows.length;i++){
				var val = rows[i];
				var billtype = $("#billtype-"+val).val();
				var billid = $("#billid-"+val).val();
				var detailid = $("#detailid-"+val).val();
				var isdiscount = $("#isdiscount-"+val).val();
				var object = {billtype:billtype,billid:billid,detailid:detailid,isdiscount:isdiscount}
				ids.push(object);
			}
			var customerid = $("#select-customerid-receiptAndReject").val();
			$.messager.confirm("提醒","是否确定申请核销？",function(r){
				if(r){
					loading("提交中..");
					$.ajax({   
			            url :'account/receivable/addSalesInvoiceByRefer.do?iswriteoff=1',
			            type:'post',
			            dataType:'json',
			            data:{ids:JSON.stringify(ids),customerid:customerid},
			            success:function(json){
			            	loaded();
			            	if(json.flag){
			            		$.messager.alert("提醒","申请核销成功");
			            		$("#account-panel-salesinvoiceDetailPage1").dialog('close');
			            		var invoiceid = json.id;
								if(invoiceid!=""){
									$('<div id="account-dialog-writeoff-info-content"></div>').appendTo('#account-panel-salesinvoiceDetailPage');
									$("#account-dialog-writeoff-info-content").dialog({
										href:"account/receivable/showWriteoffPage.do?customerid="+customerid+"&invoiceid="+invoiceid,
										title:"核销",
									    fit:true,
										modal:true,
										cache:false,
										maximizable:true,
										resizable:true,
									    cache: false,  
									    modal: true,
									    buttons:[{
												text:'确定',
												handler:function(){
													writeOffSubmit();
												}
											}],
										onClose:function(){
									    	$('#account-dialog-writeoff-info-content').dialog("destroy");
									    }
									});
								}
							}else{
								$.messager.alert("提醒","申请核销失败<br/>"+json.msg);
							}
			            },
			            error:function(){
			            	loaded();
			            	$.messager.alert("错误","销售核销出错");
			            }
			        });
	        	}
	        });
		}
		//追加到销售发票
		function showCustomerSalesInvoiceList(customerid){
			$('<div id="receiptAndreject-salesinvoice-old-list-content"></div>').appendTo('#receiptAndreject-salesinvoice-old-list');
			$("#receiptAndreject-salesinvoice-old-list-content").dialog({
				title:"客户:"+customerid+"，追加申请列表",
				width:600,
				height:300,
				closed:false,
				modal:true,
				cache:false,
				maximizable:true,
				resizable:true,
				href: 'account/receivable/showSalesInvoiceListPageByCustomer.do?customerid='+customerid,
			    buttons:[
			    		{
							text:'确认',
							handler:function(){
								var customerid = $("#select-customerid-receiptAndReject").val();
								var rowdata = $("#account-dialog-datagrid-salesInvoiceList-customer").datagrid("getSelected");
								if(null!=rowdata){
									$.messager.confirm("提醒","是否追加到该单据中？",function(r){
										if(r){
											addToSalesInvoiceByCustomer(customerid,rowdata.id);
										}
									});
								}else{
									$.messager.alert("错误","请先选择一条单据！");
								}
							}
						}
				],
				onClose:function(){
			    	$('#receiptAndreject-salesinvoice-old-list-content').dialog("destroy");
			    }
			});
		}
		//执行追加到其他销售发票中
		function addToSalesInvoiceByCustomer(customerid,salesinvoiceid){
			var rows = [];
			$(".salesinvoice-check").each(function () {
                if($(this).attr("checked")){
                	rows.push($(this).val());
                }
            });
			if(rows==null || rows.length==0){
				$.messager.alert("提醒","请选择数据");
				return false;
			}
			var ids = [];
			for(var i=0;i<rows.length;i++){
				var val = rows[i];
				var billtype = $("#billtype-"+val).val();
				var billid = $("#billid-"+val).val();
				var detailid = $("#detailid-"+val).val();
				var isdiscount = $("#isdiscount-"+val).val();
				var object = {billtype:billtype,billid:billid,detailid:detailid,isdiscount:isdiscount}
				ids.push(object);
			}
			var customerid = $("#select-customerid-receiptAndReject").val();
			loading("提交中..");
			$.ajax({   
	            url :'account/receivable/addToSalesInvoiceByCustomer.do',
	            type:'post',
	            dataType:'json',
	            data:{billid:salesinvoiceid,customerid:customerid,ids:JSON.stringify(ids)},
	            success:function(json){
	            	loaded();
	            	if(json.flag){
	            		$.messager.alert("提醒","追加成功");
	            		$("#receiptAndreject-salesinvoice-old-list-content").dialog("close");
	            		$("#account-panel-salesinvoiceDetailPage1").dialog("close");
	            		$("#account-orderDatagrid-dispatchBillSourcePage").datagrid("reload");
                        $("#account-orderDatagrid-dispatchBillSourcePage").datagrid("clearChecked");
					}else{
						$.messager.alert("提醒","追加失败<br/>"+json.msg);
					}
	            },
	            error:function(){
	            	loaded();
	            	$.messager.alert("错误","追加出错");
	            }
	        });
		}
		$("#receiptAndreject-detail-div").height($("body").height()-140);
		$.ajax({   
            url :'account/receivable/getCustomerCapital.do?id=${customerid}',
            type:'post',
            dataType:'json',
            success:function(json){
            	$("#select-amount-customer").val(formatterMoney(json.camount));
            }
        });
	</script>
  </body>
</html>
