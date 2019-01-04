<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>编辑交款单明细</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
		  	<form id="account-form-paymentVoucherDetailAddPage" method="post">
		  		<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
		  			<tr>
						<td style="height:10px;" colspan="2">&nbsp;</td>	
					</tr>
					<tr>
						<td style="text-align: right;">客户：</td>
						<td>
							<input type="text" id="account-paymentVoucherDetail-customerid" name="customerid" style="width:250px;" tabindex="1"/>
							<input type="hidden" id="account-paymentVoucherDetail-customername" name="customername"/>
						</td>		
					</tr>
					<tr>
						<td style="text-align: right;">金额：</td>
						<td><input type="text" id="account-paymentVoucherDetail-amount" name="amount" style="width:250px;" tabindex="2"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">备注：</td>
						<td>
							<input id="account-paymentVoucherDetail-remark" name="remark" style="width:250px;height:60px;"/>
						</td>
					</tr>
				</table>
		  	</form>
		  	<input type="hidden" id="account-paymentVoucherDetail-customerid-edit" value="${param.customerid }" />
  		</div>
	  	<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="text-align:right;">
	  			<input type="button" value="确定" name="savenogo" id="account-paymentVoucherDetailAddPage-addSave" />
  			</div>
	  	</div>
  	</div>
  	<script type="text/javascript">

		var selectedCustomerid=getFilterCustomerid();
		
		var theEditCustomerid="${param.customerid }";

		function getFilterCustomerid(){
			var tmpidarr=[];
			var idarrs="";
			var selectDetailRow=[];
			var theCustomerid= $("#account-paymentVoucherDetail-customerid-edit").val() || "";
	  		if($("#account-paymentVoucherAddPage-paymentVouchertable").size()>0){
	  			selectDetailRow=$("#account-paymentVoucherAddPage-paymentVouchertable").datagrid('getRows');
	  		}
	  		for(var i=0; i<selectDetailRow.length; i++){
	   			var rowJson = selectDetailRow[i];
	   			if(rowJson.customerid != undefined && rowJson.customerid!="" && theCustomerid != rowJson.customerid){
  	   				tmpidarr.push(rowJson.customerid);
	   			}
	   		}
	   		if(tmpidarr.length>0){
	   			idarrs=tmpidarr.join(',');
	   		}
	   		return idarrs;
		}
  		
  		function orderDetailAddSaveGoOnDialog(){
  			var $DetailOper=$("#account-paymentVoucherAddPage-dialog-DetailOper-content");
  			if($DetailOper.size()>0){
  				try{
  					$DetailOper.dialog("close");
  				}catch(e){
  					
  				}
  			}
  	  		$('<div id="account-paymentVoucherAddPage-dialog-DetailOper-content"></div>').appendTo("#account-paymentVoucherAddPage-dialog-DetailOper");
  	  		$DetailOper=$("#account-paymentVoucherAddPage-dialog-DetailOper-content");
  			$DetailOper.dialog({
  				title:'交款明细信息新增(按ESC退出)',
  			    width: 380,  
  			    height: 280,
  	            left:($(window).width() - 380) * 0.5+80,
  	            top:($(window).height() - 280)* 0.5-30 ,
  			    closed: true,  
  			    cache: false, 
  			    modal: true,
  			    maximizable:true,
  			    href:"account/paymentvoucher/paymentVoucherDetailAddPage.do?businessdate=${businessdate}",
			    onLoad:function(){
			    	$("#account-paymentVoucherDetail-customerid").focus();
		    	},
			    onClose:function(){
			    	$DetailOper.dialog("destroy");
			    }
  			});
  			$DetailOper.dialog("open");
  		}
		function saveOrderDetail(isGoOn){
	    	$("#account-paymentVoucherDetail-remark").focus();
  			var flag=$("#account-form-paymentVoucherDetailAddPage").form('validate');
  			if(!flag){
	  			return false;
  			}

			
  			var customerid=$("#account-paymentVoucherDetail-customerid").customerWidget('getValue');
  			if(customerid==null || customerid==""){
				$.messager.alert("提醒","抱歉，请选择客户！");
				return false;
  			}
			var theCustomerid= $("#account-paymentVoucherDetail-customerid-edit").val() || "";

//  			if(theCustomerid!=customerid && !checkAfterAddCustomer(customerid)){
//				$.messager.alert("提醒","抱歉，交款单中已经存在该客户收款！");
//				return false;
//			}

			var amount=$("#account-paymentVoucherDetail-amount").val();
			if(amount==null || amount=="" || amount==0){
				$.messager.alert("提醒","抱歉，请填写金额！");
				return false;
			}

			var formdata=$("#account-form-paymentVoucherDetailAddPage").serializeJSON();
			if(formdata){
				$("#account-paymentVoucherAddPage-paymentVouchertable").datagrid('updateRow',{
					index:editRowIndex,
					row:formdata
				});
				footerReCalc();
			}
			if(isGoOn){				
				orderDetailAddSaveGoOnDialog();
			}else{				
				var $detailOperDialog=$("#account-paymentVoucherAddPage-dialog-DetailOper-content");
	  			if($detailOperDialog.size()>0){
	  				try{
	  					$detailOperDialog.dialog("close");
	  				}catch(e){
	  					
	  				}
	  			}	
			}		
		}
  		$(document).ready(function(){  		
	  		$('#account-paymentVoucherDetail-amount').numberbox({    
	  			required:true,
	  			precision:2 
	  		});  
  			
  			$("#account-paymentVoucherDetail-customerid").customerWidget({
    			name:'t_account_paymentvoucher_detail',
    			col:'customerid',
    			singleSelect:true,
        		param:[
                  		{field:'id',op:'notin',value:selectedCustomerid}
                ],
                required:true,
                isall:true,
                isdatasql:false,
    			onSelect : function(data){
    				if(data){
    					var theCustomerid= $("#account-paymentVoucherDetail-customerid-edit").val() || "";
    					
//    		  			if(data.id!=theCustomerid && !checkAfterAddCustomer(data.id)){
//    						$.messager.alert("提醒","抱歉，交款单中已经存在该客户收款！");
//    	    				return false;
//    					}
        				$("#account-paymentVoucherDetail-customername").val(data.name);
        				
        				var nextObj=getNumberBox("account-paymentVoucherDetail-amount");
        				nextObj.focus();
        				nextObj.select();
    				}
    			}
    		});
    		

	  		$("#account-paymentVoucherDetailAddPage-addSaveGoOn").click(function(){
				saveOrderDetail(true);		
	  		});

	  		getNumberBox("account-paymentVoucherDetail-amount").die("keydown").bind("keydown",function(event){
				//enter
				if(event.keyCode==13){
					getNumberBox("account-paymentVoucherDetail-amount").blur();
					$("#account-paymentVoucherDetail-remark").focus();
					$("#account-paymentVoucherDetail-remark").select();
		   			event.stopPropagation();
				}
		    });
	  		$("#account-paymentVoucherDetail-remark").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
                    $("#account-paymentVoucherDetailAddPage-addSave").click();
		   			event.stopPropagation();
				}
		    });

	  		
	  		$("#account-paymentVoucherDetailAddPage-addSave").click(function(){
				saveOrderDetail(false);		
	  		});

	  		$("#account-paymentVoucherDetailAddPage-addSaveGoOn").click(function(){
				saveOrderDetail(true);		
	  		});

	  		$("#account-paymentVoucherDetailAddPage-addSave").die("keydown").live("keydown",function(event){
	  			if(event.keyCode==13){
	  				saveOrderDetail(false);
	  			}
	  		});
	  		
	  		$("#account-paymentVoucherDetailAddPage-addSaveGoOn").die("keydown").live("keydown",function(event){
	  			if(event.keyCode==13){
	  				saveOrderDetail(true);
	  			}
	  		});
  		});
  	</script>
  </body>
</html>
