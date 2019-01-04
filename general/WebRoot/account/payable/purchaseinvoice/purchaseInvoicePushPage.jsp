<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户应收款冲差添加页面</title>
  </head>
  
  <body>
   	<form action="account/payable/addOrUpdatePurchaseInvoicePush.do" method="post" id="account-form-purchaseInvoicePush">
   		<table  border="0" style="width: 380px;">
   			<tr>
   				<td style="text-align: right;width: 100px;">采购发票编号:</td>
   				<td style="text-align: left;">
   					<input type="text"  name="purchaseInvoicePush.invoiceid" style="width: 200px;" value="${id}" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">业务日期:</td>
   				<td style="text-align: left;">
   					<input type="text"  onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style="width: 200px;" value="${date }" name="purchaseInvoicePush.businessdate" />
   				</td>
   			</tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">供应商名称:</td>
   				<td style="text-align: left;">
   					<input type="text" style="width: 200px;" value="${purchaseInvoice.suppliername}" readonly="readonly"/>
   					<input type="hidden"  name="purchaseInvoicePush.supplierid" value="${purchaseInvoice.supplierid}"/>
   				</td>
   			</tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">冲差类型:</td>
   				<td style="text-align: left;">
   					<input id="account-purchaseInvoicePush-pushtype" name="purchaseInvoicePush.pushtype" style="width: 200px;" <c:if test="${purchaseInvoicePush!=null}">value="${purchaseInvoicePush.pushtype}"</c:if>/>
   				</td>
   			</tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">商品品牌:</td>
   				<td style="text-align: left;">
   					<input id="account-purchaseInvoicePush-brand" name="purchaseInvoicePush.brand" style="width: 200px;" />
   				</td>
   			</tr>
            <c:if test="${purchaseInvoicePush==null}">
   			<tr>
   				<td style="text-align: right;width: 100px;">初始金额:</td>
   				<td style="text-align: left;">
   					<input id="account-purchaseInvoicePush-old-taxamount" type="text"  style="width: 200px;" value="${purchaseInvoice.taxamount}" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">发票金额:</td>
   				<td style="text-align: left;">
   					<input type="text" id="account-purchaseInvoicePush-taxamount" style="width: 200px;" value="${purchaseInvoice.invoiceamount}"/>
   				</td>
   			</tr>
            </c:if>
   			<tr>
   				<td style="text-align: right;width: 100px;">冲差金额:</td>
   				<td style="text-align: left;">
   					<input type="text" id="account-purchaseInvoicePush-amount" name="purchaseInvoicePush.amount" style="width: 200px;" class="easyui-validatebox easyui-numberbox" data-options="precision:2,required:true,validType:'intOrFloat'" />
   				</td>
   			</tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">备注:</td>
   				<td style="text-align: left;">
   					<input type="text" name="purchaseInvoicePush.remark" style="width: 200px;" id="account-purchaseInvoicePush-remark"  />
   				</td>
   			</tr>
   		</table>
    </form>
    <script type="text/javascript">
    	$(function(){
    		$("#account-purchaseInvoicePush-old-taxamount").numberbox({
				precision:2,
				required:true,
				validType:'intOrFloat'
			});
			$("#account-purchaseInvoicePush-taxamount").numberbox({
				precision:2,
				required:true,
				validType:'intOrFloat',
                onChange:function(newValue,oldValue){
                    var invoiceamount = newValue;
                    var oldamount = $("#account-purchaseInvoicePush-old-taxamount").val();
                    var pushamount = Number(invoiceamount) - Number(oldamount);
                    $("#account-purchaseInvoicePush-amount").numberbox("setValue",formatterMoney(pushamount));
                }
			});
            $("#account-purchaseInvoicePush-amount").numberbox({
                precision:2,
                required:true,
                validType:'intOrFloat',
                onChange:function(newValue,oldValue){
                    var pushamount = newValue;
                    if(newValue == 0){
                        $.messager.alert("提醒","冲差金额不能为0");
                        $('#account-purchaseInvoicePush-amount').numberbox("getValue");
                        return false;
                    }
                    var oldamount = $("#account-purchaseInvoicePush-old-taxamount").val();
                    var invoiceamount = Number(oldamount)+Number(pushamount);
                    $("#account-purchaseInvoicePush-taxamount").numberbox("setValue",formatterMoney(invoiceamount));
                },
                formatter: function(value){
                    if(value == 0){
                        return "";
                    }else{
                        return value;
                    }
                }
            });


    		$("#account-purchaseInvoicePush-pushtype").widget({
    			name:'t_account_purchase_invoice_push',
	    		width:200,
				col:'pushtype',
				singleSelect:true,
                param:[{field:'pushtypename',op:'equal',value:'${pushtypename}'}]
    		});

    		showBrandWidget($("#account-purchaseInvoicePush-pushtype").val());
    		
    		$("#account-purchaseInvoicePush-pushtype").change(function(){
    			var val = $(this).val();
    			showBrandWidget(val);
    		});
    		$("#account-form-purchaseInvoicePush").form({  
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
			    		$.messager.alert("提醒","冲差成功");
                        if($('#account-dialog-purchaseInvoicePushPage').size() > 0){
                            $('#account-dialog-purchaseInvoicePushPage').dialog("close");
                        }
                        if($('#account-dialog-push-purchaseInvoicePage').size() > 0){
                            $('#account-dialog-push-purchaseInvoicePage').dialog('close');
                        }
                        if($("#account-datagrid-purchaseInvoicePage").size() > 0){
                            $("#account-datagrid-purchaseInvoicePage").datagrid("reload");
                        }
                        if($("#account-panel-purchaseInvoicePage").size() > 0){
                            $("#account-panel-purchaseInvoicePage").panel({
                                href:"account/payable/showPurchaseInvoiceEditPage.do?id=${id}",
                                title:'',
                                cache:false,
                                maximized:true,
                                border:false
                            });
                        }
			    	}else{
			    		$.messager.alert("提醒","保存失败");
			    	}
			    }  
			}); 
    	});
    	function showBrandWidget(pushtype){
            var remark = $("#account-purchaseInvoicePush-remark").val();
            if(remark != ""){
                remark = remark.replace("代垫费用","");
                remark = remark.replace("价格冲差","");
                remark = remark.replace("货物冲差","");
            }
    		if(typeof(pushtype)=="undefined"){
    			pushtype="";
    		}
    		var required=false;
    		if(pushtype==1){
    			required=true;
                $("#account-purchaseInvoicePush-remark").val("代垫费用 "+remark);
    		}else if(pushtype==2){
                $("#account-purchaseInvoicePush-remark").val("价格冲差 "+remark);
            }else if(pushtype==3){
                $("#account-purchaseInvoicePush-remark").val("货物冲差 "+remark);
            }
    		$("#account-purchaseInvoicePush-brand").widget({
    			name:'t_account_purchase_invoice_push',
	    		width:200,
				col:'brand',
				singleSelect:true,
				required:required
    		});
            $("#account-purchaseInvoicePush-brand").widget("clear");
    	}
    </script>
  </body>
</html>
