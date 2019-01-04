<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户应收款冲差添加页面</title>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
	  		<form action="account/receivable/editCustomerPushBanlanceRemark.do" method="post" id="account-form-customerPushBanlance-add">
		   		<input type="hidden" name="customerPushBalance.id" value="${customerPushBalance.id}"/>
		   		<table  border="0" style="width: 380px;">
		   			<tr>
		   				<td style="text-align: right;width: 100px;">业务日期:</td>
		   				<td style="text-align: left;">
		   					<input type="text"  style="width: 200px;" value="${customerPushBalance.businessdate }" name="customerPushBalance.businessdate" readonly="readonly"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td style="text-align: right;width: 100px;">客户名称:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="account-customerPushBanlance-customerid" name="customerPushBalance.customerid" style="width: 200px;" value="${customerPushBalance.customerid }" text="<c:out value="${customerPushBalance.customername }"></c:out>" readonly="readonly"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td style="text-align: right;width: 100px;">销售部门:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="account-customerPushBanlance-deptid" name="customerPushBalance.salesdept" style="width: 200px;" value="${customerPushBalance.salesdept }" readonly="readonly"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td style="text-align: right;width: 100px;">客户业务员:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="account-customerPushBanlance-salesuser" name="customerPushBalance.salesuser" style="width: 200px;" value="${customerPushBalance.salesuser }" readonly="readonly"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td style="text-align: right;width: 100px;">冲差类型:</td>
		   				<td style="text-align: left;">
		   					<select id="account-customerPushBanlance-pushtype" name="customerPushBalance.pushtype" style="width: 200px;" disabled="disabled">
								<c:forEach items="${pushtypeList }" var="list">
									<c:choose>
										<c:when test="${list.code == customerPushBalance.pushtype}">
											<option value="${list.code }" selected="selected">${list.codename }</option>
										</c:when>
										<c:otherwise>
											<option value="${list.code }">${list.codename }</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
		   					</select>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td style="text-align: right;width: 100px;">费用科目:</td>
		   				<td style="text-align: left;">
		   					<input id="account-customerPushBanlance-subject" name="customerPushBalance.subject" style="width: 200px;" value="${customerPushBalance.subject }" readonly="readonly"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td style="text-align: right;width: 100px;">商品品牌:</td>
		   				<td style="text-align: left;">
		   					<input id="account-customerPushBanlance-brand" name="customerPushBalance.brandid" style="width: 200px;" value="${customerPushBalance.brandid }" readonly="readonly"/>
		   				</td>
		   			</tr>
                    <tr>
                        <td style="text-align: right;width: 100px;">默认税种:</td>
                        <td style="text-align: left;">
                            <input type="text" id="account-customerPushBanlance-defaulttaxtype" name="customerPushBalance.defaulttaxtype" value="${customerPushBalance.defaulttaxtype}" readonly="readonly" style="width: 200px;"/>
                        </td>
                    </tr>
		   			<tr>
		   				<td style="text-align: right;width: 100px;">冲差金额:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="account-customerPushBanlance-amount" name="customerPushBalance.amount" value="${customerPushBalance.amount }" style="width: 200px;" readonly="readonly"/>
		   				</td>
		   			</tr>
                    <tr>
                        <td style="text-align: right;width: 100px;">冲差未税金额:</td>
                        <td style="text-align: left;">
                            <input type="text" id="account-customerPushBanlance-notaxamount" name="customerPushBalance.notaxamount" value="${customerPushBalance.notaxamount}" readonly="readonly" class="easyui-numberbox no_input" data-options="precision:2" style="width: 200px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: right;width: 100px;">冲差税额:</td>
                        <td style="text-align: left;">
                            <input type="text" id="account-customerPushBanlance-tax" name="customerPushBalance.tax" value="${customerPushBalance.tax}" readonly="readonly" class="easyui-numberbox no_input" data-options="precision:2" style="width: 200px;"/>
                        </td>
                    </tr>
		   			<tr>
		   				<td style="text-align: right;width: 100px;">备注:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="customerPushBalance-remark" name="customerPushBalance.remark" value="<c:out value="${customerPushBalance.remark }"></c:out>" style="width: 200px;"/>
		   				</td>
		   			</tr>
		   		</table>
		    </form>
  		</div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="height:30px;text-align:right;">
  				<security:authorize url="/account/receivable/customerPushBanlanceHoldSave.do">
  					<input type="button" value="暂存" name="savegoon" id="account-customerPushBanlance-addButton-hold" />
				</security:authorize>
				<security:authorize url="/account/receivable/customerPushBanlanceSave.do">
					<input type="button" value="保存" name="savegoon" id="account-customerPushBanlance-addButton" />
				</security:authorize>
  			</div>
  		</div>
  	</div>
    <script type="text/javascript">
    	$(function(){
    		$("#account-customerPushBanlance-customerid").customerWidget({
	    		width:200,
				singleSelect:true,
				required:true
    		});
			$("#account-customerPushBanlance-deptid").widget({
				width:200,
				referwid:'RL_T_BASE_DEPARTMENT_SELLER',
				singleSelect:true
			});
			$("#account-customerPushBanlance-salesuser").widget({
				referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
				width:200,
				singleSelect:true
			});
    		$("#account-customerPushBanlance-amount").numberbox({
    			precision:2,
				required:true
    		});
    		$("#account-customerPushBanlance-subject").widget({
    			name:'t_account_customer_push_balance',
	    		width:200,
				col:'subject',
				singleSelect:true
    		});
    		$("#account-customerPushBanlance-brand").widget({
    			name:'t_account_customer_push_balance',
	    		width:200,
				col:'brandid',
				singleSelect:true,
				required:true
    		});

            //默认税种
            $("#account-customerPushBanlance-defaulttaxtype").widget({
                width:200,
                referwid:'RL_T_BASE_FINANCE_TAXTYPE',
                singleSelect:true,
                required:true
            });

            $("#customerPushBalance-remark").bind('keydown',function(e){
				if(e.keyCode==13){
					$("#account-customerPushBanlance-addButton").click();
				}
            });

    		//按钮权限
    		var holdbtn = $("#account-btn-permission-hold").val();
    		var savebtn = $("#account-btn-permission").val();
    		if(undefined != holdbtn && undefined != savebtn && "" != holdbtn && "" != savebtn){
    			document.getElementById("account-customerPushBanlance-addButton-hold").style.display = "none";
    		}
    		
    		$("#account-form-customerPushBanlance-add").form({
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
			    		$.messager.alert("提醒","提交成功");
			    		$("#account-datagrid-customerPushBanlancePage").datagrid("reload");
			    		$('#account-panel-customerPushBanlance-addpage').dialog("close");
			    	}else{
			    		$.messager.alert("提醒","提交失败");
			    	}
			    }  
			});
			
			$("#account-customerPushBanlance-addButton-hold").click(function(){
				if("${customerPushBalance.status}" == "2"){
					$.messager.alert("提醒","无权限操作!");
					return false;
				}
				$.messager.confirm("提醒","是否暂存该客户应收款冲差数据？",function(r){
					if(r){
						$("#account-form-customerPushBanlance-add").submit();
	 				}
				});
			});
			$("#account-customerPushBanlance-addButton").click(function(){
				$.messager.confirm("提醒","是否保存该客户应收款冲差数据？",function(r){
					if(r){
						$("#account-form-customerPushBanlance-add").submit();
	 				}
				});
			}); 
    	});
    </script>
  </body>
</html>
