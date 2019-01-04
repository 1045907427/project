<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<title>客户档案批量修改信息页面</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div style="padding-left: 20px;">
    			<form action="basefiles/editCustomerMore.do" method="post" id="sales-form-editMoreCustomer" style="padding: 10px;">
    				<input type="hidden" name="idStr" value="${idStr }"/>
    				<input type="hidden" id="sales-unInvNum-customer" value="${unInvNum }"/>
    				<input type="hidden" name="customer.oldsalesarea" />
			    	<input type="hidden" name="customer.oldsalesdeptid" />
			    	<input type="hidden" name="customer.oldsalesuserid" />
			    	<input type="hidden" name="customer.oldindoorstaff" />
			    	<input type="hidden" name="customer.oldcustomersort"/>
    				<table cellpadding="2" cellspacing="2" border="0">
    					<tr>
    						<td align="left">结算方式:</td>
    						<td align="left"><input id="sales-settletype-customerMoreEdit" type="text" style="width: 120px;" name="customer.settletype" /></td>
    						<td align="left">结算日:</td>
    						<td align="left"><select id="customer-select-settleday" name="customer.settleday" class="easyui-combobox" style="width: 120px;">
    								<option></option>
    								<c:forEach items="${dayList}" var="day">
    								<option value="${day }">${day }</option>
    								</c:forEach>
    						</select></td>
    					</tr>
    					<tr>
                            <td>信用额度:</td>
                            <td><input type="text" class="easyui-numberbox" data-options="min:0,precision:0" style="width: 120px;"  name="customer.credit"/></td>
    						<%--<td>支付方式:</td>--%>
    						<%--<td><input id="sales-paytype-customerMoreEdit" type="text" style="width: 120px;" name="customer.paytype"/></td>--%>
    						<td>默认价格套:</td>
    						<td><select name="customer.pricesort" style="width: 120px;" class="easyui-combobox">
    							<option></option>
    							<c:forEach items="${priceList}" var="list">
    							<option value="${list.code }">${list.codename }</option>
    							</c:forEach>
    							</select></td>
    					</tr>
    					<!-- <tr>
    						<td>是否开票:</td>
    						<td><select name="customer.billing" style="width: 120px;" class="easyui-combobox">
						    		<option></option>
						    		<option value="1">是</option>
						    		<option value="0">否</option>
						    	</select></td>
    						<td>应收依据:</td>
    						<td><select name="customer.billtype" style="width: 120px;" class="easyui-combobox">
						    		<option></option>
						    		<option value="0">发票</option>
    								<option value="1">发货</option>
    								<option value="2">发票+发货</option>
						    	</select></td>
    					</tr> -->
    					<tr>
    						<td>核销方式:</td>
    						<td><select name="customer.canceltype" style="width: 120px;" class="easyui-combobox">
	    								<option></option>
	    								<c:forEach items="${canceltypeList }" var="list">
						    				<c:choose>
						    					<c:when test="${list.code == customer.canceltype }">
						    						<option value="${list.code }" selected="selected">${list.codename }</option>
						    					</c:when>
						    					<c:otherwise>
						    						<option value="${list.code }">${list.codename }</option>
						    					</c:otherwise>
						    				</c:choose>
						    			</c:forEach>
	    						</select></td>
    						<td>默认销售部门:</td>
    						<td><input id="sales-salesdeptid-customerMoreEdit" type="text" style="width: 120px;" name="customer.salesdeptid"/></td>
    					</tr>
    					<tr>
    						<td>默认业务员:</td>
    						<td><input id="sales-salesuserid-customerMoreEdit" type="text" style="width: 120px;" name="customer.salesuserid"/></td>
    						<td>默认理货员:</td>
    						<td><input id="sales-tallyuserid-customerMoreEdit" type="text" style="width: 120px;" name="customer.tallyuserid"/></td>
    					</tr>
    					<tr>
    						<td>默认内勤:</td>
    						<td><input id="sales-indoorstaff-customerMoreEdit" type="text" style="width: 120px;" name="customer.indoorstaff"/></td>
    						<td>默认分类:</td>
    						<td><input id="sales-customersort-customerMoreEdit" type="text" style="width: 120px;" name="customer.customersort"/></td>
    					</tr>
    					<tr>
    						<td>所属区域:</td>
    						<td><input id="sales-area-customerMoreEdit" type="text" style="width: 120px;" name="customer.salesarea"/></td>
    						<td>收款人:</td>
				    		<td><input id="sales-payeeid-customerMoreEdit" type="text" style="width: 120px;" name="customer.payeeid"/></td>
    					</tr>
    				</table>
    			</form>
    		</div>
    	</div>
    	<div data-options="region:'south'" style="height: 30px;border: 0px;">
    		<div align="right">
    			<a href="javaScript:void(0);" id="sales-save-saveMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="修改">确定</a>
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
    	$("#sales-save-saveMenu").click(function(){
    		$.messager.confirm("提醒","确认修改?",function(r){
				if(r){
					$("#sales-form-editMoreCustomer").submit();
				}
			});
    	});
    
    	$("#sales-form-editMoreCustomer").form({  
		    onSubmit: function(){  
		    	loading("提交中..");
		    },  
		    success:function(data){
		    	//表单提交完成后 隐藏提交等待页面
		    	loaded();
		    	var json = $.parseJSON(data);
		    	$.messager.alert("提醒",""+json.lockNum+"条数据被其他人操作,暂不能修改;<br/>"+json.unEditNum+"条数据不允许修改;<br/>"+json.sucNum+"条记录修改成功;<br/>"+json.failNum+"条数据修改失败");
		    	$('#sales-dialog-customerEditMore').dialog('close',true);
		        $("#sales-datagrid-customerListPage").datagrid('reload');
		        $("#sales-datagrid-customerListPage").datagrid('clearChecked');
		    }  
		});
    	
    	$(function(){
    		//结算方式
			$("#sales-settletype-customerMoreEdit").widget({
				width:120,
				name:'t_base_sales_customer',
				col:'settletype',
				singleSelect:true,
				onlyLeafCheck:false
			});
			
			//支付方式
//			$("#sales-paytype-customerMoreEdit").widget({
//				width:120,
//				name:'t_base_sales_customer',
//				col:'paytype',
//				singleSelect:true,
//				onlyLeafCheck:true
//			});
			
			//默认销售部门
			$("#sales-salesdeptid-customerMoreEdit").widget({ //销售部门参照窗口
    			width:120,
				name:'t_base_sales_customer',
				col:'salesdeptid',
				singleSelect:true,
				onlyLeafCheck:false,
    			setValueSelect:false,
    			onSelect:function(data){
    				$("#sales-salesuserid-customerMoreEdit").widget({ //客户业务员参照窗口
			    		name:'t_base_sales_customer',
			    		col:'salesuserid',
			    		singleSelect:true,
			    		width:120,
			    		onlyLeafCheck:true,
			    		param:[{field:'deptid',op:'equal',value:data.id}]
			    	});
    			}
    		});
			
			//默认业务员
			$("#sales-salesuserid-customerMoreEdit").widget({
				width:120,
				name:'t_base_sales_customer',
				col:'salesuserid',
				singleSelect:true,
				onlyLeafCheck:false
			});
			
			//默认理货员
			$("#sales-tallyuserid-customerMoreEdit").widget({
				width:120,
				name:'t_base_sales_customer',
				col:'tallyuserid',
				singleSelect:true,
				onlyLeafCheck:false
			});
			
			//默认内勤
			$("#sales-indoorstaff-customerMoreEdit").widget({
				width:120,
				name:'t_base_sales_customer',
				col:'indoorstaff',
				singleSelect:true,
				onlyLeafCheck:false
			});
			
			//默认分类
			$("#sales-customersort-customerMoreEdit").widget({
				width:120,
				name:'t_base_sales_customer',
				col:'customersort',
				singleSelect:true,
				onlyLeafCheck:true
			});
			
			//所属区域
			$("#sales-area-customerMoreEdit").widget({ //销售区域参照窗口
    			name:'t_base_sales_customer',
				col:'salesarea',
    			singleSelect:true,
    			width:120,
    			onlyLeafCheck:true
    		});
			
			//收款人
    		$("#sales-payeeid-customerMoreEdit").widget({ 
    			name:'t_base_sales_customer',
				col:'payeeid',
    			singleSelect:true,
    			width:120,
    			onlyLeafCheck:false
    		});
    	});
    </script>
  </body>
</html>
