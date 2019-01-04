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
	   	<form action="account/receivable/editCustomerPushBanlance.do" method="post" id="account-form-customerPushBanlance-add">
	   		<input id="account-customerPushBanlance-status" type="hidden" name="customerPushBalance.status" value="${customerPushBalance.status }">
	   		<table  border="0" style="width: 380px;">
	   			<tr>
	   				<td style="text-align: right;width: 100px;">业务日期:</td>
	   				<td style="text-align: left;">
	   					<input type="text"  onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style="width: 200px;" value="${customerPushBalance.businessdate }" name="customerPushBalance.businessdate" />
	   				</td>
	   			</tr>
	   			<tr>
	   				<td style="text-align: right;width: 100px;">客户名称:</td>
	   				<td style="text-align: left;">
	   					<input type="hidden" name="customerPushBalance.id" value="${customerPushBalance.id}"/>
	   					<input type="text" id="account-customerPushBanlance-customerid" name="customerPushBalance.customerid" style="width: 200px;" value="${customerPushBalance.customerid }" text="<c:out value="${customerPushBalance.customername }"></c:out>"/>
	   				</td>
	   			</tr>
 		   		<tr>
 	   				<td style="text-align: right;width: 100px;">销售部门:</td>
 	   				<td style="text-align: left;">
 	   					<input type="text" id="account-customerPushBanlance-deptid" name="customerPushBalance.salesdept" style="width: 200px;" value="${customerPushBalance.salesdept }"/>
 	   				</td>
 	   			</tr>
 	   			<tr>
 	   				<td style="text-align: right;width: 100px;">客户业务员:</td>
 	   				<td style="text-align: left;">
 	   					<input type="text" id="account-customerPushBanlance-salesuser" name="customerPushBalance.salesuser" style="width: 200px;" value="${customerPushBalance.salesuser }"/>
 	   				</td>
 	   			</tr>
	   			<tr>
	   				<td style="text-align: right;width: 100px;">冲差类型:</td>
	   				<td>
	   					<select id="account-customerPushBanlance-pushtype" name="customerPushBalance.pushtype" style="width: 200px;" disabled>
							<c:forEach items="${pushtypeList }" var="list">
								<option value="${list.code }" <c:if test="${list.code == customerPushBalance.pushtype}">selected="selected"</c:if>>${list.codename }</option>
							</c:forEach>
	   					</select>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td style="text-align: right;width: 100px;">费用科目:</td>
	   				<td style="text-align: left;">
	   					<input id="account-customerPushBanlance-subject" name="customerPushBalance.subject" style="width: 200px;" value="${customerPushBalance.subject }"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td style="text-align: right;width: 100px;">商品品牌:</td>
	   				<td style="text-align: left;">
	   					<input id="account-customerPushBanlance-brand" name="customerPushBalance.brandid" style="width: 200px;" value="${customerPushBalance.brandid }"/>
	   				</td>
	   			</tr>
                <tr>
                    <td style="text-align: right;width: 100px;">默认税种:</td>
                    <td style="text-align: left;">
                        <input type="text" id="account-customerPushBanlance-defaulttaxtype" readonly="readonly" name="customerPushBalance.defaulttaxtype" value="${customerPushBalance.defaulttaxtype}" style="width: 200px;"/>
                    </td>
                </tr>
	   			<tr>
	   				<td style="text-align: right;width: 100px;">冲差金额:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="account-customerPushBanlance-amount" name="customerPushBalance.amount" value="${customerPushBalance.amount }" style="width: 200px;"/>
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
	   					<input type="text" name="customerPushBalance.remark" value="<c:out value="${customerPushBalance.remark }"></c:out>" style="width: 200px;"/>
	   				</td>
	   			</tr>
	   		</table>
	    </form>
	    </div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="height:30px;text-align:right;">
  				<security:authorize url="/account/receivable/customerPushBanlanceHoldSave.do">
  					<input type="button" value="暂 存" name="savegoon" id="account-customerPushBanlance-addButton-hold" />
				</security:authorize>
				<security:authorize url="/account/receivable/customerPushBanlanceSave.do">
					<input type="button" value="保 存" name="savegoon" id="account-customerPushBanlance-addButton" />
				</security:authorize>
  			</div>
  		</div>
  	</div>
    <script type="text/javascript">
    	var all = false;
   		<security:authorize url="/account/receivable/customerPushNegative.do">
   			all = true;
   		</security:authorize>
    	$(function(){
    		$("#account-customerPushBanlance-customerid").customerWidget({
	    		width:200,
				singleSelect:true,
				required:true,
				isopen:true,
				onSelect:function(data){
					$("#account-customerPushBanlance-deptid").widget('clear');
					$("#account-customerPushBanlance-salesuser").widget('clear');
					$("#account-customerPushBanlance-deptid").widget('setValue',data.salesdeptid);
					$("#account-customerPushBanlance-salesuser").widget('setValue',data.salesuserid);
					$("#account-customerPushBanlance-brand").select();
					$("#account-customerPushBanlance-brand").focus();
				},
				onClear:function(){
					$("#account-customerPushBanlance-deptid").widget('clear');
					$("#account-customerPushBanlance-salesuser").widget('clear');
				}
    		});
    		$("#account-customerPushBanlance-amount").numberbox({
    			precision:2,
				required:true,
				onChange:function(newValue,oldValue){
					if(!all){
						if(Number(newValue)<0){
							$("#account-customerPushBanlance-amount").numberbox("setValue",-Number(newValue));
						}
					}
                    //根据默认税种计算未税金额、税额
                    getPushBanlanceNoTaxAmount();
				}
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
				required:true,
				onSelect:function(data){
					$("#account-customerPushBanlance-defaulttaxtype").widget('setValue',data.defaulttaxtype);
					getNumberBox("account-customerPushBanlance-amount").select();
                    getNumberBox("account-customerPushBanlance-amount").focus();
				}
    		});

            //默认税种
            $("#account-customerPushBanlance-defaulttaxtype").widget({
                width:200,
                referwid:'RL_T_BASE_FINANCE_TAXTYPE',
                singleSelect:true,
                required:true,
				onSelect:function(data){
					//根据默认税种计算未税金额、税额
					getPushBanlanceNoTaxAmount();
				}
            });

            getNumberBox("account-customerPushBanlance-amount").bind('keydown',function(e){
                if(e.keyCode==13){
                    $("#account-customerPushBanlance-addButton").click();
                }
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
			$("#account-customerPushBanlance-amount").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#account-customerPushBanlance-addButton").focus();
				}
			});
			$("#account-customerPushBanlance-addButton-hold").click(function(){
				if("${customerPushBalance.status}" == "2"){
					$.messager.alert("提醒","无权限操作!");
					return false;
				}
				$.messager.confirm("提醒","是否暂存该客户应收款冲差数据？",function(r){
					if(r){
						$("#account-customerPushBanlance-status").val("1");
						$("#account-form-customerPushBanlance-add").submit();
	 				}
				});
			});
			$("#account-customerPushBanlance-addButton").click(function(){
				$.messager.confirm("提醒","是否保存该客户应收款冲差数据？",function(r){
					if(r){
						$("#account-customerPushBanlance-status").val("2");
						$("#account-form-customerPushBanlance-add").submit();
	 				}
				});
			});
    	});
    </script>
  </body>
</html>
