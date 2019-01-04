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
                        <td class="len70 right">业务日期:</td>
                        <td class="len150 left"><input id="customerCostPayable-date-businesstime" type="text" name="customerCostPayable.businessdate" class="easyui-validatebox Wdate" required="true" value="${busdate }" style="width:120px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'2000-01-01'})"/></td>
						<td colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td class="len70 right">OA编号:</td>
                        <td class="len150 left"><input id="customerCostPayable-text-oaid" type="text" name="customerCostPayable.oaid" style="width:120px"/></td>
						<td class="len70 left">费用类别:</td>
						<td align="left"><input id="customerCostPayable-widget-expensesort" type="text" name="customerCostPayable.expensesort" style="width: 130px"/></td>
                    </tr>
                    <tr>
                        <td class="right">客户名称:</td>
                        <td class="left" colspan="3"><input id="customerCostPayable-widget-customerid" type="text" style="width: 345px;" name="customerCostPayable.customerid"/></td>
	    			</tr>
                    <tr>
                        <td class="right">供应商:</td>
                        <td colspan="3">
                        	<input id="customerCostPayable-widget-supplierid" type="text" name="customerCostPayable.supplierid"  style="width:345px;" />
                        	<input id="customerCostPayable-hidden-deptid" type="hidden" name="customerCostPayable.deptid"/>
                        </td>
                    </tr>
					<tr>
						<td class="right">单据类型:</td>
						<td class="left">
							<select style="width:120px;" name="customerCostPayable.billtype">
								<option value="2" selected="selected">贷</option>
								<option value="1">借</option>
							</select>
						</td>
						<td class="left">费用金额:</td>
						<td class="left"><input id="customerCostPayable-number-amount" type="text" style="width: 130px;" name="customerCostPayable.amount"  value="0"/></td>
                    </tr>
                    <tr>
                        <td class="right" >支付类型:</td>
                        <td align="left">
                            <select style="width:120px;" name="customerCostPayable.paytype" id="customerCostPayable-select-paytype" class="easyui-validatebox" required="true">
                            	<option value="">请选择</option>
								<option value="1">支付</option>
								<option value="2">冲差</option>
							</select>
                        </td>
                        <td class="left">银　　行:</td>
                        <td class="left"><input id="customerCostPayable-widget-bankid" type="text" style="width: 130px;" name="customerCostPayable.bankid" /></td>
                    </tr>
                    <tr>
                        <td class="right" >备注:</td>
                        <td align="left" colspan="3">
                            <textarea id="customerCostPayable-text-remark" name="customerCostPayable.remark" style="width: 340px; height: 50px; resize: none;"></textarea>
                        </td>
                    </tr>
                </table>          
            </form>
        </div>
    </div>
    <div data-options="region:'south'" style="height: 38px; border: 0px;">
		<div class="buttonDetailBG" style="text-align:right;">
			<input type="button" name="savegoon" id="customerCostPayable-save-saveMenu" value="确定" title="添加客户应付费用"/>
			<input type="button" name="savegoon" id="customerCostPayable-saveAgain-saveMenu" value="确定并且继续添加 " title="继续添加客户应付费用"/>
        </div>
    </div>
</div>
<script type="text/javascript">

    	$(function(){

    	    $("#customerCostPayable-number-amount").numberbox({
    	        precision:2
    	    });
    	    
    	    var $amountTexbox=getNumberBox("customerCostPayable-number-amount");

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
        		
        		if(!$("#customerCostPayable-form-add").form('validate')){
           			return false;
           		}

        		var amount=$("#customerCostPayable-number-amount").numberbox('getValue');

        		if(amount=="" || amount==0){
            		$.messager.alert("提醒","请填写费用金额!");
            		return false;
           		}

        		$.messager.confirm("提醒","是否新增客户应付费用?",function(r){
					if(r){
		           		var ret = customerCostPayable_AjaxConn($("#customerCostPayable-form-add").serializeJSON(),'journalsheet/customercost/addCustomerCostPayable.do','提交中...');
		            	var retJson = $.parseJSON(ret);
                        $('#customerCostPayable-select-paytype').trigger('change');
		            	if(retJson.flag){
		            		$.messager.alert("提醒","新增成功!");
		            		//$("#customerCostPayable-detail-table").datagrid('reload');
		            		$("#customerCostPayable-detail-query-btn-List").trigger('click');
		            		closeCustomerCostPayableAEVDailog();
		            	}
		            	else{
		                	if(retJson.msg){
		            			$.messager.alert("提醒","新增失败!"+retJson.msg);
		                	}else{
		            			$.messager.alert("提醒","新增失败!");
		                	}
		            	}
					}
        		});
        	});

        	//继续添加客户应付费用
        	$("#customerCostPayable-saveAgain-saveMenu").click(function(){
        		$("#customerCostPayable-text-remark").blur();

        		
        		if(!$("#customerCostPayable-form-add").form('validate')){
           			return false;
           		}
        		var amount=$("#customerCostPayable-number-amount").numberbox('getValue');

        		if(amount=="" || amount==0){
            		$.messager.alert("提醒","请填写费用金额!");
            		return false;
           		}

        		var paytype=$("#customerCostPayable-select-paytype").val()||"";
           		var ret = customerCostPayable_AjaxConn($("#customerCostPayable-form-add").serializeJSON(),'journalsheet/customercost/addCustomerCostPayable.do','提交中..');
            	var retJson = $.parseJSON(ret);
            	if(retJson.flag){
            		$.messager.alert("提醒","新增成功!");
//                    setTimeout(function () {
//                        $('#customerCostPayable-select-paytype').trigger('change');
//                    }, 150);

                    $("#customerCostPayable-detail-query-btn-List").trigger('click');

            		closeCustomerCostPayableAEVDailog();
            		var onLoadFunc=function(){
            			$("#customerCostPayable-select-paytype").val(paytype);
                        $('#customerCostPayable-select-paytype').trigger('change');
            		};
					customerCostPayableAEVDailog('客户应付费用【新增】', 'journalsheet/customercost/showCustomerCostPayableAddPage.do',onLoadFunc);
            	}
            	else{
                	if(retJson.msg){
            			$.messager.alert("提醒","新增失败!"+retJson.msg);
                	}else{
            			$.messager.alert("提醒","新增失败!");
                	}
            	}
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
		  			$amountTexbox.focus();
		  			$amountTexbox.select();
		  		},
		  		onClear:function(){
	  				$("#customerCostPayable-hidden-deptid").val("");		  			
		  		}
			});

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
			$amountTexbox.keydown(function(event){
				//enter
				if(event.keyCode==13){
					$(this).blur();
   	   				$("#customerCostPayable-saveAgain-saveMenu").focus();
				}
   			});

            // 支付类型
			$('#customerCostPayable-select-paytype').off('change').on('change', function (e) {

			    var paytype = $(this).val();
			    if(paytype == '2') {
                    $("#customerCostPayable-widget-bankid").widget('clear');
                    $("#customerCostPayable-widget-bankid").widget('readonly', true);
                } else if(paytype == '1') {

                    $("#customerCostPayable-widget-bankid").widget('clear');
                    $("#customerCostPayable-widget-bankid").widget('readonly', false);
                }
            })

    	});
</script>
</body>
</html>
