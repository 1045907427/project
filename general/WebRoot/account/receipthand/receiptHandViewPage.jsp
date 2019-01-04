<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>应收交接单查看页面</title>
  </head>
  
  <body>
  	<div id="account-layout-receipthanddetail" class="easyui-layout" data-options="fit:true,border:false">
  		<div id="account-layout-receipthanddetail-north" data-options="region:'north',border:false" style="height:140px">
  			<form id="receiptHand-form-head" action="" method="post" style="padding: 3px;">
		    	<table cellpadding="3" cellspacing="3" border="0">
		    		<tr>
		   				<td>编号：</td>
		   				<td><input id="receipthand-id-baseinfo" type="text" name="receiptHand.id" value="${receiptHand.id }" class="no_input" readonly="readonly" style="width: 160px;"/></td>
		   				<td>业务日期：</td>
		   				<td><input id="receipthand-businessdate-baseinfo" type="text" name="receiptHand.businessdate" value="${receiptHand.businessdate }" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style="width: 160px;" disabled="disabled"/></td>
		   				<td>状态：</td>
		   				<td>
		   					<select  style="width: 160px;" disabled="disabled" class="no_input">
								<c:forEach items="${statusList}" var="list">
		   							<c:choose>
		   								<c:when test="${list.code == receiptHand.status}">
		   									<option value="${list.code }" selected="selected">${list.codename }</option>
		   								</c:when>
		   								<c:otherwise>
		   									<option value="${list.code }">${list.codename }</option>
		   								</c:otherwise>
		   							</c:choose>
		   						</c:forEach>
							</select>
							<input type="hidden" name="receiptHand.status" value="${receiptHand.status}"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>领单人：</td>
		   				<td><input id="receipthand-handuserid-baseinfo" type="text" name="receiptHand.handuserid" value="${receiptHand.handuserid }" readonly="readonly"/></td>
		   				<td>领单人部门：</td>
		   				<td><input id="receipthand-handdeptid-baseinfo" type="text" name="receiptHand.handdeptid" value="${receiptHand.handdeptid }" readonly="readonly"/></td>
		   				<td>客户家数：</td>
		   				<td>
		   					<table>
		   						<tr>
		   							<td><input id="receipthand-cnums-baseinfo" type="text" name="receiptHand.cnums" value="${receiptHand.cnums }" class="easyui-numberbox" data-options="min:0,precision:0" style="width: 40px;" readonly="readonly"/></td>
		   							<td>打印次数：</td>
		   							<td><input id="receipthand-printtimes-baseinfo" type="text" name="receiptHand.printtimes" value="${receiptHand.printtimes }" class="easyui-numberbox no_input" data-options="min:0,precision:0" style="width: 40px;" readonly="readonly"/></td>
		   						</tr>
		   					</table>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>发货金额：</td>
		   				<td><input id="receipthand-totalamount-baseinfo" type="text" name="receiptHand.totalamount" value="${receiptHand.totalamount }" class="easyui-numberbox" data-options="precision:2" style="width: 160px;" readonly="readonly"/></td>
		   				<td>收款金额：</td>
		   				<td><input id="receipthand-collectamount-baseinfo" type="text" name="receiptHand.collectamount" value="${receiptHand.collectamount }" class="easyui-numberbox" data-options="precision:2" style="width: 160px;" readonly="readonly"/></td>
		   				<td>备注：</td>
		   				<td><input type="text" name="receiptHand.remark" value="${receiptHand.remark }" style="width: 160px;" readonly="readonly"/></td>
		   			</tr>
		    	</table>
		    </form>
		    <ul class="tags" style="min-width: 400px">
				<security:authorize url="/account/receipthand/customerDetailTab.do">
					<li id="firstli" class="selectTag">
						<a href="javascript:void(0)">客户明细</a>
					</li>
				</security:authorize>
				<security:authorize url="/account/receipthand/billDetailTab.do">
					<li>
						<a href="javascript:void(0)">单据明细</a>
					</li>
				</security:authorize>
			</ul>
  		</div>
  		<div id="account-layout-receipthanddetail-center" data-options="region:'center',border:false">
  			<div class="tagsDiv" style="min-width: 1024px">
				<div class="tagsDiv_item">
					<table id="receipthand-table-customer"></table>
				</div>
				<div class="tagsDiv_item">
					<table id="receipthand-table-bill"></table>
				</div>
			</div>
  		</div>
  	</div>
	<script type="text/javascript">
		var $dgCustomerList = $("#receipthand-table-customer");
		var $dgBillList = $("#receipthand-table-bill");
		
		$(function(){
			$("#account-buttons-receipthand").buttonWidget("setDataID",{id:"${receiptHand.id}",state:"${receiptHand.status}",type:"veiw"});
            if("${receiptHand.status}" != "3"){
                $("#account-buttons-receipthand").buttonWidget("disableMenu","menuButton");
            }else{
                $("#account-buttons-receipthand").buttonWidget("enableMenu","menuButton");
            }
			
			//领单人
			$("#receipthand-handuserid-baseinfo").widget({
                name:'t_account_receipt',
                col:'handuserid',
    			width:160,
    			singleSelect:true
			});
			//领单人所属部门
			$("#receipthand-handdeptid-baseinfo").widget({
                name:'t_account_receipt',
                col:'handdeptid',
    			width:160,
    			singleSelect:true,
    			onlyLeafCheck:true
			});
			
			$(".tags").find("li").click(function(){
				var height = $("#account-layout-receipthanddetail-center").height()-5;
				var id = $("#receipthand-id-baseinfo").val();
				var index = $(this).index();
				$(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
				$(".tagsDiv .tagsDiv_item").hide().eq(index).show();
				if(index == 0){
					if(!$dgCustomerList.hasClass("create-datagrid")){
						$dgCustomerList.datagrid({
							authority:customerTableColJson,
			    			columns: customerTableColJson.common,
			    			frozenColumns: customerTableColJson.frozen,
			    			height:height,
			    			idField:'id',
			    			url:'account/receipthand/getReceiptHandCustomerListByBillid.do?id=${receiptHand.id }',
				   			method:'post',
				   			rownumbers:true,
					  		border:false,
					  		showFooter:true,
					  		singleSelect:true,
					  		rowStyler: function(index,row){
								if (row.isedit == "1" || row.isedit == "2"){
									return 'background-color:rgb(190, 250, 241);';
								}
							}
						});
						$dgCustomerList.addClass("create-datagrid");
					}
				}else if(index == 1){
					if(!$dgBillList.hasClass("create-datagrid")){
						$dgBillList.datagrid({
							authority:billTableColJson,
			    			columns: billTableColJson.common,
			    			frozenColumns: billTableColJson.frozen,
			    			height:height,
			    			url:'account/receipthand/getReceiptHandBillListByBillid.do?id=${receiptHand.id }',
				   			method:'post',
				   			rownumbers:true,
					  		border:false,
					  		showFooter:true,
					  		singleSelect:true
						});
						$dgBillList.addClass("create-datagrid");
					}
				}
			});
			setTimeout(function(){
				$("#firstli").click();
			},50);
		});
	</script>
  </body>
</html>
