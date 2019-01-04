<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>付款单</title>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
	  	<div data-options="region:'center',border:false">
	  		<form action="" id="account-form-payorderAdd" method="post">
		   		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
		   			<tr>
		   				<td class="len60 left">编号：</td>
		   				<td><input class="len130 easyui-validatebox" name="payorder.id" value="${payorder.id }" readonly="readonly"/></td>
		   				<td class="len60 left">业务日期：</td>
		   				<td><input type="text" id="account-payorder-businessdate" class="len130" value="${payorder.businessdate }" name="payorder.businessdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
		   				<td class="len60 left">状&nbsp;&nbsp;态：</td>
		   				<td>
		   					<select disabled="disabled" class="len130">
		   						<c:forEach items="${statusList }" var="list">
		   							<c:choose>
		   								<c:when test="${list.code == payorder.status}">
		   									<option value="${list.code }" selected="selected">${list.codename }</option>
		   								</c:when>
		   								<c:otherwise>
		   									<option value="${list.code }">${list.codename }</option>
		   								</c:otherwise>
		   							</c:choose>
		   						</c:forEach>
		   					</select>
		   					<input type="hidden" name="payorder.status" value="${payorder.status }"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>供应商:</td>
		   				<td colspan="3" style="text-align: left;">
		   					<input type="text" id="account-payorder-supplierid" name="payorder.supplierid" style="width: 250px;" value="${payorder.supplierid }" text="<c:out value="${payorder.suppliername }"></c:out>"/>
		   					<span id="account-payorder-supplierid-span" style="margin-left:5px;line-height:25px;">编码：${payorder.supplierid }</span>
		   				</td>
                        <td>付款类型:</td>
                        <td>
                            <input type="text" id="account-payorder-paytype" name="payorder.paytype" class="len130" value="${payorder.paytype}"/>
                        </td>
		   			</tr>
		   			<tr>
		   				<td class="selectBank">银行名称:</td>
		   				<td class="selectBank">
		   					<input id="account-payorder-bank" type="text" name="payorder.bank" value="${payorder.bank }"/>
		   				</td>
		   				<td>是否预付：</td>
		   				<td>
		   					<select name="payorder.prepay" class="len130">
		   						<option value="1" <c:if test="${payorder.prepay=='1'}">selected="selected"</c:if>>是</option>
		   						<option value="0" <c:if test="${payorder.prepay=='0'}">selected="selected"</c:if>>否</option>
		   					</select>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>采购部门：</td>
		   				<td>
		   					<input type="text" id="account-payorder-buydept" name="payorder.buydeptid" class="len130" value="${payorder.buydeptid}"/>
		   				</td>
		   				<td>采购员：</td>
		   				<td style="text-align: left">
		   					<input type="text" id="account-payorder-buyuser" name="payorder.buyuserid" class="len130" value="${payorder.buyuserid}"/>
		   				</td>
		   				<td>付款金额:</td>
		   				<td >
		   					<input type="text" id="account-payorder-amount" name="payorder.amount" class="len130" value="${payorder.amount }"/>
		   				</td>
		   			</tr>
		   			<!-- <tr>
		   				<td>已核销金额:</td>
		   				<td>
		   					<input type="text" class="len130" readonly="readonly"/>
		   				</td>
		   				<td>剩余金额:</td>
		   				<td>
		   					<input type="text" id="account-payorder-remainderamount" name="payorder.remainderamount" class="len130" readonly="readonly" value="${payorder.remainderamount }"/>
		   				</td>
		   			</tr> -->
		   			<tr>
		   				<td>备注：</td>
		   				<td colspan="5" style="text-align: left">
		   					<textarea style="width: 545px;height: 60px;" name="payorder.remark"><c:out value="${payorder.remark }"></c:out></textarea>
		   				</td>
		   			</tr>
		   			<tr>
		   		</table>
		   	</form>
	  	</div>
	  	<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="height:30px;text-align:right;">
  				<input type="button" value="确定" name="savegoon" id="account-payorder-addbutton" />
  			</div>
  		</div>
	</div>
    <script type="text/javascript">
    	$(function(){
    		$("#account-payorder-supplierid").supplierWidget({
    			name:'t_account_purchase_payorder',
	    		width:250,
				col:'supplierid',
				singleSelect:true,
				required:true,
				setValueSelect:false,
				onSelect:function(data){
					$("#account-payorder-supplierid-span").html("编码:"+data.id);
					$("#account-payorder-buyuser").widget({
		    			name:'t_account_purchase_payorder',
		    			col:'buyuserid',
		    			singleSelect:true,
		    			width:130,
		    			onlyLeafCheck:true,
		    			async:false,
		    			param:[{field:'deptid',op:'equal',value:data.buydeptid}]
		    		});
		    		$("#account-payorder-buyuser").widget('clear');
		    		$("#account-payorder-buydept").widget('clear');
		  			$("#account-payorder-buydept").widget('setValue', data.buydeptid);
		  			$("#account-payorder-buyuser").widget('setValue', data.buyuserid);
				},
				onClear:function(){
					$("#account-payorder-buydept").widget('clear');
  					$("#account-payorder-buyuser").widget('clear');
				}
    		});
  			$("#account-payorder-buyuser").widget({
    			name:'t_account_purchase_payorder',
    			col:'buyuserid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:true
    		});
    		$("#account-payorder-paytype").widget({
    			name:'t_account_purchase_payorder',
	    		width:130,
				col:'paytype',
				singleSelect:true,
				required:true
    		});
    		$("#account-payorder-buydept").widget({
    			name:'t_account_purchase_payorder',
	    		width:130,
				col:'buydeptid',
				singleSelect:true,
				required:true,
				setValueSelect:false,
				onSelect:function(data){
					$("#account-payorder-buyuser").widget({
		    			name:'t_account_purchase_payorder',
			    		width:130,
						col:'buyuserid',
						singleSelect:true,
						async:false,
						param:[{field:'deptid',op:'equal',value:data.id}]
					});
					$("#account-payorder-buyuser").widget("clear");
				}
    		});
    		$("#account-payorder-bank").widget({
				name:'t_account_purchase_payorder',
	    		width:130,
				col:'bank',
				disable:true,
				singleSelect:true,
				required:true
			});
    		$("#account-payorder-amount").numberbox({
    			precision:2,
				groupSeparator:',',
				required:true,
				onChange:function(newValue,oldValue){
					$("#account-payorder-remainderamount").numberbox("setValue",newValue);
				}
    		});
    		$("#account-payorder-remainderamount").numberbox({
    			precision:2,
				groupSeparator:','
    		});
    		$("#account-form-payorderAdd").form({
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	var json = $.parseJSON(data);
			    	if(json.flag){
			    		$("#account-datagrid-payorderPage").datagrid("reload");
			    		$.messager.alert("提醒","保存成功");
			    		$('#account-panel-payorder-addpage').dialog("close");
			    	}else{
			    		$.messager.alert("提醒","保存失败");
			    	}
			    }  
			});
			
			$("#account-payorder-addbutton").click(function(){
				//$("#account-collectionOrder-addtype").val("save");
				$("#account-form-payorderAdd").attr("action", "account/payable/editPayorder.do");
				$("#account-form-payorderAdd").submit();
			});
    	});
    	//控制按钮状态
    	//$("#account-buttons-payorderPage").buttonWidget("setDataID",{id:'${payorder.id}',state:'${payorder.status}',type:'edit'});
    	$("#account-hidden-billid").val("${payorder.id}");
    	//$("#account-buttons-payorderPage").buttonWidget("disableButton", 'writeoff-button');
    </script>
  </body>
</html>
