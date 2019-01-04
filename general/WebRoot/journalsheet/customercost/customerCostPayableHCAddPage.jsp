<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户应付费用新增页面</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center">
    			<form action="" method="post" id="customerCostPayable-form-add" style="padding: 10px;">
                <table cellpadding="2" cellspacing="2" border="0">
                    <tr>
                        <td class="len80 right">业务日期:</td>
                        <td class="left" style="width:130px;"><input id="customerCostPayable-date-businesstime" type="text" name="customerCostPayable.businessdate" class="easyui-validatebox Wdate" required="true" value="${customerCostPayable.businessdate }" style="width:120px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'2000-01-01'})"/></td>
						<td class="right" style="width:95px;">关联代垫:</td>
						<td align="left">
							<input type="text" class="readonly" readonly="readonly" id="customerCostPayable-text-hcreferid" name="customerCostPayable.hcreferid" value="${customerCostPayable.id }" style="width:130px; font-family: sans-serif;" />
						</td>
                    </tr>
                    <tr>
                        <td class="len80 right">OA编号:</td>
                        <td class="left" style="width:130px;"><input id="customerCostPayable-text-oaid" type="text" value="${customerCostPayable.oaid }" style="width:120px" class="readonly" readonly="radonly"/></td>
						<td class="right" style="width:95px;">费用类别:</td>
						<td align="left"><input id="customerCostPayable-widget-expensesort" type="text" value="${customerCostPayable.expensesort}" style="width: 130px" class="readonly" readonly="radonly"/></td>
                    </tr>
                    <tr>
                        <td class="right">客户名称:</td>
                        <td class="left" colspan="3"><input id="customerCostPayable-widget-customerid" type="text" style="width: 365px;" value="${customerCostPayable.customerid }" text="${customerCostPayable.customername }" class="readonly" readonly="readonly"/></td>
	    			</tr>
                    <tr>
                        <td class="right">供应商:</td>
                        <td colspan="3">
                        	<input id="customerCostPayable-widget-supplierid" type="text" value="${customerCostPayable.supplierid }" text="${customerCostPayable.suppliername }"style="width:365px;" class="readonly" readonly="readonly" />
                        	<input id="customerCostPayable-hidden-deptid" type="hidden"  value="${customerCostPayable.deptid }"/>
                        </td>
                    </tr>
					<tr>
						<td class="right">单据类型:</td>
						<td class="left">
							<select style="width:130px;" class="readonly" readonly="radonly" disabled="disabled">
								<option value="2" selected="selected">贷(客户应付费用)</option>
								<option value="1">借(客户支付费用)</option>
							</select>
						</td>	    					    					
						<td class="right">费用金额:</td>
						<td class="left"><input id="customerCostPayable-number-amount" type="text" style="width: 130px;" value="${customerCostPayable.amount }" class="easyui-numberbox readonly" data-options="precision:2" readonly="readonly"/></td>
                    </tr>
                    <tr>
                        <td class="right">银行:</td>
                        <td class="left"><input id="customerCostPayable-widget-bankid" type="text" style="width: 130px;" name="customerCostPayable.bankid" readonly="readonly" value="${customerCostPayable.bankid }"/></td>
						<td class="right">费用金额(红冲):</td>
						<td class="left"><input id="customerCostPayable-number-hcamount" type="text" style="width: 130px;" value="${customerCostPayable.amount }" disabled="disabled"/></td>
                    </tr>
                    <tr>
                        <td class="right" >备注:</td>
                        <td align="left" colspan="3">
                            <textarea id="customerCostPayable-text-remark" name="customerCostPayable.remark" style="width: 360px; height: 50px;">关联客户应付费用编号：${customerCostPayable.id }</textarea>
                        </td>
                    </tr>
                </table>          
            </form>
        </div>
    </div>
    <div data-options="region:'south'" style="height: 38px;border: 0px;">
		<div class="buttonDetailBG" style="text-align:right;">
			<input type="button" name="savegoon" id="customerCostPayable-save-saveMenu" value="确定" title="客户应付费用红冲"/>
		</div>
    </div>
</div>
<script type="text/javascript">

    	$(function(){

    		//检验输入值的最大长度
    		$.extend($.fn.validatebox.defaults.rules, {
    			maxLen:{
    	  			validator : function(value,param) { 
    		            return value.length <= param[0]; 
    		        }, 
    		        message : '最多可输入{0}个字符!' 
    	  		}
    		});
        	//添加客户应付费用
        	$("#customerCostPayable-save-saveMenu").click(function(){
        		$("#customerCostPayable-text-remark").blur();

        		var hcreferid= $("#customerCostPayable-text-hcreferid").val()||"";
        		if($.trim(hcreferid)=="" ){    		
        			$.messager.alert("提醒","抱歉，未能找到相关客户应付费用信息!");
        			return false;
        		}

        		var amount=$("#customerCostPayable-number-amount").numberbox('getValue');

        		if(amount=="" || amount==0){
            		$.messager.alert("提醒","请填写费用金额!");
            		return false;
           		}
        		
        		if(!$("#customerCostPayable-form-add").form('validate')){
           			return false;
           		}
        		$.messager.confirm("提醒","是否新增客户应付费用红冲?",function(r){
					if(r){
		           		var ret = customerCostPayable_AjaxConn($("#customerCostPayable-form-add").serializeJSON(),'journalsheet/customercost/addCustomerCostPayableHC.do','提交中...');
		            	var retJson = $.parseJSON(ret);
		            	if(retJson.flag){
		            		$.messager.alert("提醒","新增红冲成功!");
		            		//$("#customerCostPayable-detail-table").datagrid('reload');
		            		$("#customerCostPayable-detail-query-btn-List").trigger('click');
		            		closeCustomerCostPayableAEVDailog();
		            	}
		            	else{
		                	if(retJson.msg){
		            			$.messager.alert("提醒","新增红冲失败!"+retJson.msg);
		                	}else{
		            			$.messager.alert("提醒","新增红冲失败!");
		                	}
		            	}
					}
        		});
        	});
        	
    		//供应商
		  	$("#customerCostPayable-widget-supplierid").supplierWidget({
				name:'t_js_customercost_payable',
				col:'supplierid',
				singleSelect:true,
				onlyLeafCheck:false,
		  		onSelect:function(data){

		  			if(data.buydeptid == undefined){
		  				$("#customerCostPayable-hidden-deptid").val("");
		  			}else{
		  				$("#customerCostPayable-hidden-deptid").val(data.buydeptid);
		  			}
            		$("#customerCostPayable-number-amount").focus();
           		 	$("#customerCostPayable-number-amount").select();
		  		},
		  		onClear:function(){
	  				$("#customerCostPayable-hidden-deptid").val("");		  			
		  		}
			});

            // 银行
            $("#customerCostPayable-widget-bankid").widget({
                width:130,
                referwid: 'RL_T_BASE_FINANCE_BANK',
                singleSelect: true,
                onSelect: function(data){
                    $("#customerCostPayable-text-remark").focus();
                    $("#customerCostPayable-text-remark").select();
                }
            });

			$("#customerCostPayable-widget-supplierid").addClass("readonly");

			//科目编码
			$("#customerCostPayable-widget-expensesort").widget({
		  		width:130,
				name:'t_js_customercost_payable',
				col:'expensesort',
				singleSelect:true,
				onSelect:function(data){
					$("#customerCostPayable-widget-customerid").focus();
					$("#customerCostPayable-widget-customerid").select();
				}
			});

			$("#customerCostPayable-widget-expensesort").addClass("readonly");
			
			//客户名称
			$("#customerCostPayable-widget-customerid").customerWidget({
				name:'t_js_customercost_payable',
				col:'customerid',
				singleSelect:true,
				isall:true,
				required:true,
				onSelect:function(data){
					$(this).select();
		   			$(this).blur();
					$("#customerCostPayable-widget-supplierid").focus();
					$("#customerCostPayable-widget-supplierid").select();
				}
			});

			$("#customerCostPayable-widget-customerid").addClass("readonly");
			
   			$("#customerCostPayable-widget-expensesort").change(function(){
	   				$(this).blur();
	   				$("#customerCostPayable-widget-customerid").focus();
	   				$("#customerCostPayable-widget-customerid").select(); 	   				
			});
			
			$("#customerCostPayable-text-oaid").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$(this).blur();
		   			$("#customerCostPayable-widget-expensesort").focus();
				}
			});
   			$("#customerCostPayable-number-amount").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$(this).blur();
   	   				$("#customerCostPayable-saveAgain-saveMenu").focus();
				}
   			});
   			
   			$("#customerCostPayable-number-hcamount").numberbox({    
   				precision:2,
   				groupSeparator:',',
   				formatter:function(value){
   					if(value!=null){
   						var val= value*(-1);
   						return formatterMoney(val);
   					}
   				}
   			});  

   			

    	});
</script>
</body>
</html>
