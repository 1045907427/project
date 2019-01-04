<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>客户应收款冲差添加页面</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  	<div data-options="region:'center',border:false">
   	<form action="account/receivable/addCustomerPushBanlance.do" method="post" id="account-form-customerPushBanlance-add">
   		<input id="account-customerPushBanlance-status" type="hidden" name="customerPushBalance.status">
   		<table  border="0" style="width: 380px;">
   			<tr>
   				<td style="text-align: right;width: 100px;">业务日期:</td>
   				<td style="text-align: left;">
   					<input type="text"  onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style="width: 200px;" value="${date }" name="customerPushBalance.businessdate" />
   				</td>
   			</tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">客户名称:</td>
   				<td style="text-align: left;">
   					<input type="text" id="account-customerPushBanlance-customerid" name="customerPushBalance.customerid" style="width: 200px;"/>
   				</td>
   			</tr>
			<tr>
				<td style="text-align: right;width: 100px;">销售部门:</td>
				<td style="text-align: left;">
					<input type="text" id="account-customerPushBanlance-deptid" name="customerPushBalance.salesdept" style="width: 200px;"/>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;width: 100px;">客户业务员:</td>
				<td style="text-align: left;">
					<input type="text" id="account-customerPushBanlance-salesuser" name="customerPushBalance.salesuser" style="width: 200px;"/>
				</td>
			</tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">冲差类型:</td>
   				<td style="text-align: left;">
   					<select id="account-customerPushBanlance-pushtype" name="customerPushBalance.pushtype" style="width: 200px;">
						<c:forEach items="${pushtypeList }" var="list">
							<option value="${list.code }">${list.codename }</option>
						</c:forEach>
   					</select>
   				</td>
   			</tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">费用科目:</td>
   				<td style="text-align: left;">
   					<input id="account-customerPushBanlance-subject" name="customerPushBalance.subject" style="width: 200px;"/>
   				</td>
   			</tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">商品品牌:</td>
   				<td style="text-align: left;">
   					<input id="account-customerPushBanlance-brand" name="customerPushBalance.brandid" style="width: 200px;"/>
   				</td>
   			</tr>
            <tr>
                <td style="text-align: right;width: 100px;">默认税种:</td>
                <td style="text-align: left;">
                    <input type="text" id="account-customerPushBanlance-defaulttaxtype" readonly="readonly" name="customerPushBalance.defaulttaxtype" style="width: 200px;"/>
                </td>
            </tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">冲差金额:</td>
   				<td style="text-align: left;">
   					<input type="text" id="account-customerPushBanlance-amount" name="customerPushBalance.amount" style="width: 200px;"/>
   				</td>
   			</tr>
            <tr>
                <td style="text-align: right;width: 100px;">冲差未税金额:</td>
                <td style="text-align: left;">
                    <input type="text" id="account-customerPushBanlance-notaxamount" name="customerPushBalance.notaxamount" readonly="readonly" class="easyui-numberbox no_input" data-options="precision:2" style="width: 200px;"/>
                </td>
            </tr>
            <tr>
                <td style="text-align: right;width: 100px;">冲差税额:</td>
                <td style="text-align: left;">
                    <input type="text" id="account-customerPushBanlance-tax" name="customerPushBalance.tax" readonly="readonly" class="easyui-numberbox no_input" data-options="precision:2" style="width: 200px;"/>
                </td>
            </tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">备注:</td>
   				<td style="text-align: left;">
   					<input type="text" name="customerPushBalance.remark" style="width: 200px;"/>
   				</td>
   			</tr>
   		</table>
    </form>
    </div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="height:30px;text-align:right;">
  				<security:authorize url="/account/receivable/customerPushBanlanceHoldSave.do">
  					<input type="button" value="暂 存" name="savegoon" id="account-collectionOrder-addbutton-hold" />
	  				<input type="button" value="继续添加" name="savegoon" id="account-customerPushBanlance-addGoButton-hold" />
				</security:authorize>
				<security:authorize url="/account/receivable/customerPushBanlanceSave.do">
					<input type="button" value="保 存" name="savegoon" id="account-collectionOrder-addbutton" />
	  				<input type="button" value="继续添加" name="savegoon" id="account-customerPushBanlance-addGoButton" />
				</security:authorize>
	  			<input id="account-customerPushBanlance-addGoButton-type" type="hidden" value="2">
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
    			name:'t_account_customer_push_balance',
	    		width:200,
				col:'customerid',
				singleSelect:true,
				required:true,
				isopen:true,
				onSelect:function(data){
					$("#account-customerPushBanlance-deptid").widget('clear');
					$("#account-customerPushBanlance-salesuser").widget('clear');
					$("#account-customerPushBanlance-deptid").widget('setValue',data.salesdeptid);
					$("#account-customerPushBanlance-salesuser").widget('setValue',data.salesuserid);
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
                    $("#account-customerPushBanlance-addGoButton").click();
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
    			document.getElementById("account-collectionOrder-addbutton-hold").style.display = "none";
    			document.getElementById("account-customerPushBanlance-addGoButton-hold").style.display = "none";
    			$("#account-btn-permission-hold").val("0");
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
			    		$.messager.alert("提醒","提交成功,生成冲差单："+json.id);
			    		$("#account-datagrid-customerPushBanlancePage").datagrid("reload");
			    		var type = $("#account-customerPushBanlance-addGoButton-type").val();
			    		if(type=="1"){
			    			$('#account-panel-customerPushBanlance-addpage').dialog("close");
			    		}else{
			    			$("#account-customerPushBanlance-brand").widget("clear");
			    			$("#account-customerPushBanlance-amount").numberbox("setValue",0);	
			    			$("#account-customerPushBanlance-customerid").select();
			    			$("#account-customerPushBanlance-customerid").focus();
			    		}
			    	}else{
			    		$.messager.alert("提醒","提交失败");
			    	}
			    }  
			}); 
			
			//暂存
			$("#account-collectionOrder-addbutton-hold").click(function(){
				$("#account-customerPushBanlance-status").val("1");
				addCustomerPushBanlance("1");
			});
			$("#account-customerPushBanlance-addGoButton-hold").click(function(){
				$("#account-customerPushBanlance-status").val("1");
				addCustomerPushBanlance("2");
			});
			
			//保存
			$("#account-collectionOrder-addbutton").click(function(){
				$("#account-customerPushBanlance-status").val("2");
				addCustomerPushBanlance("1");
			});
			$("#account-customerPushBanlance-addGoButton").click(function(){
				$("#account-customerPushBanlance-status").val("2");
				addCustomerPushBanlance("2");
			});
			$("#account-customerPushBanlance-amount").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					var holdbtnkey = $("#account-btn-permission-hold").val();
    				var savebtnkey = $("#account-btn-permission").val();
					if(undefined != holdbtnkey && "1" == holdbtnkey){
						$("#account-customerPushBanlance-addGoButton-hold").focus();
					}else if(undefined != savebtnkey && "1" == savebtnkey){
						$("#account-customerPushBanlance-addGoButton").focus();
					}
				}
			});
    	});
    	function addCustomerPushBanlance(type){
    		$("#account-customerPushBanlance-addGoButton-type").val(type);
			$("#account-form-customerPushBanlance-add").submit();
    	}
    </script>
  </body>
</html>
