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
   	<form  method="post" id="account-form-customerPushBanlance-add">
   		<input id="account-customerPushBanlance-defaulttaxtypename" type="hidden" name="defaulttaxtypename">
		<input id="account-customerPushBanlance-subjectname" type="hidden" name="subjectname">
		<input id="account-customerPushBanlance-brandname" type="hidden" name="brandname">
		<input id="account-customerPushBanlance-pushtypename" type="hidden" name="pushtypename">
   		<table  border="0" style="width: 380px;">
   			<tr>
   				<td style="text-align: right;width: 100px;">冲差类型:</td>
   				<td style="text-align: left;">
   					<select id="account-customerPushBanlance-pushtype" name="pushtype" style="width: 200px;">
						<c:forEach items="${pushtypeList }" var="list">
							<option value="${list.code }">${list.codename }</option>
						</c:forEach>
   					</select>
   				</td>
   			</tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">费用科目:</td>
   				<td style="text-align: left;">
   					<input id="account-customerPushBanlance-subject" name="subject" style="width: 200px;"/>
   				</td>
   			</tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">商品品牌:</td>
   				<td style="text-align: left;">
   					<input id="account-customerPushBanlance-brand" name="brandid" style="width: 200px;"/>
   				</td>
   			</tr>
            <tr>
                <td style="text-align: right;width: 100px;">默认税种:</td>
                <td style="text-align: left;">
                    <input type="text" id="account-customerPushBanlance-defaulttaxtype" readonly="readonly" name="defaulttaxtype" style="width: 200px;"/>
                </td>
            </tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">冲差金额:</td>
   				<td style="text-align: left;">
   					<input type="text" id="account-customerPushBanlance-amount" name="amount" style="width: 200px;"/>
   				</td>
   			</tr>
            <tr>
                <td style="text-align: right;width: 100px;">冲差未税金额:</td>
                <td style="text-align: left;">
                    <input type="text" id="account-customerPushBanlance-notaxamount" name="notaxamount" readonly="readonly" class="easyui-numberbox no_input" data-options="precision:2" style="width: 200px;"/>
                </td>
            </tr>
            <tr>
                <td style="text-align: right;width: 100px;">冲差税额:</td>
                <td style="text-align: left;">
                    <input type="text" id="account-customerPushBanlance-tax" name="tax" readonly="readonly" class="easyui-numberbox no_input" data-options="precision:2" style="width: 200px;"/>
                </td>
            </tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">备注:</td>
   				<td style="text-align: left;">
   					<input type="text" name="remark" style="width: 200px;"/>
   				</td>
   			</tr>
   		</table>
    </form>
    </div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="height:30px;text-align:right;">
				<security:authorize url="/account/receivable/customerPushBanlanceSave.do">
					<input type="button" value="保 存" name="savegoon" id="account-customerPushBanlance-addbutton" />
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
				singleSelect:true,
				onSelect:function(data){
					$("#account-customerPushBanlance-subjectname").val(data.name);
				}
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
					$("#account-customerPushBanlance-brandname").val(data.name);
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
					$("#account-customerPushBanlance-defaulttaxtypename").val(data.name);
				}
            });

            getNumberBox("account-customerPushBanlance-amount").bind('keydown',function(e){
                if(e.keyCode==13){
                    $("#account-customerPushBanlance-addGoButton").click();
                }
            });

    		//按钮权限
    		var holdbtn = $("#account-btn-permission-hold").val();
    		var savebtn = $("#account-btn-permission").val();
    		if(undefined != holdbtn && undefined != savebtn && "" != holdbtn && "" != savebtn){
    			$("#account-btn-permission-hold").val("0");
    		}

			//保存
			$("#account-customerPushBanlance-addbutton").click(function(){
				addCustomerPushBanlace(false);
			});
			$("#account-customerPushBanlance-addGoButton").click(function(){
				$("#account-customerPushBanlance-status").val("2");
				addCustomerPushBanlace(true);
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

    </script>
  </body>
</html>
