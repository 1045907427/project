<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>发货单折扣修改</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
		   	<form action="" method="post" id="sales-form-dispatchBillDetailAddPage">
		   		<table  border="0" class="box_table">
		   			<tr>
		   				<td width="120">商品名称:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="sales-dispatchBill-goodsname" width="180"/>
		   					<input type="hidden" id="sales-dispatchBill-goodsid" name="goodsid"/>
		   				</td>
		   				<td>折扣金额:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="sales-dispatchBill-taxamount" name="taxamount"/>
		   					<input type="hidden" name="isdiscount" value="1"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td width="120">商品品牌:</td>
		   				<td>
		   					<input type="text" id="sales-dispatchBill-goodsbrandName" class="no_input" readonly="readonly"/>
		   				</td>
		   				<td width="120">规格型号:</td>
		   				<td>
		   					<input type="text" id="sales-dispatchBill-goodsmodel" class="no_input" readonly="readonly"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td width="120">折扣无税额:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="sales-dispatchBill-notaxamount" name="notaxamount" class="no_input" readonly="readonly"/>
		   					<input type="hidden" id="sales-dispatchBill-tax" name="tax" class="no_input" readonly="readonly"/>
                        <td width="120">指定仓库:</td>
                        <td style="text-align: left;">
                            <input id="sales-storageid-billDetailDiscountAddPage"  name="storageid" /></td>
                        <input id="sales-storagename-billDetailDiscountAddPage" name="storagename" type="hidden" />
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>备注:</td>
		   				<td colspan="3" style="text-align: left;">
		   					<input type="text" id="sales-dispatchBill-remark" name="remark" style="width: 488px;" maxlength="200" value="商品折扣"/>
		   				</td>
		   			</tr>
		   		</table>
		   		
		    </form>
		    </div>
	  		<div data-options="region:'south',border:false">
	  			<div  class="buttonDetailBG" style="height:30px;text-align:right;">
		  			<input type="button" value="确 定" name="savegoon" id="sales-savegoon-billDetailAddDiscountPage" />
		  			<!-- <input type="button" value="确定" name="savenogo" /> -->
	  			</div>
	  		</div>
	  	</div>
   <script type="text/javascript">
   		var all = false;
   		<security:authorize url="/sales/dispatchBillAddDiscountPage2.do">
   			all = true;
   		</security:authorize>
   		$(function(){
            $("#sales-storageid-billDetailDiscountAddPage").widget({
                name:'t_sales_dispatchbill_detail',
                col:'storageid',
                width:165,
                onSelect: function(data){
                    $("#sales-storagename-billDetailDiscountAddPage").val(data.name);
                },
                onClear: function(){
                    $("#sales-storagename-billDetailDiscountAddPage").val("");
                }
            });
			$("#sales-dispatchBill-taxamount").numberbox({
				precision:2,
				required:true,
				onChange:function(newValue,oldValue){
					if(!all){
						if(Number(newValue)<0){
							$("#sales-dispatchBill-taxamount").numberbox("setValue",-Number(newValue));
						}
					}
					if(oldValue!=null && oldValue!=""){
						var goodsid = $("#sales-dispatchBill-goodsid").val();
						var taxamount = $("#sales-dispatchBill-taxamount").numberbox("getValue");
						$.ajax({   
				            url :'sales/computeDispatchBillDiscountTax.do',
				            type:'post',
				            data:{goodsid:goodsid,taxamount:taxamount},
				            dataType:'json',
				            async:false,
				            success:function(json){
				            	$("#sales-dispatchBill-notaxamount").numberbox("setValue",json.notaxamount);
				            	$("#sales-dispatchBill-tax").val(json.tax);
				            }
				        });
					}
				}
			});
			$("#sales-dispatchBill-notaxamount").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#sales-savegoon-billDetailAddDiscountPage").click(function(){
				editSaveDetailDiscount();
			});
			getNumberBox("sales-dispatchBill-taxamount").die("keydown").bind("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#sales-dispatchBill-remark").focus();
					$("#sales-dispatchBill-remark").select();
				}
			});
			$("#sales-dispatchBill-remark").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#sales-savegoon-billDetailAddDiscountPage").focus();
				}
			});
			$("#sales-savegoon-billDetailAddDiscountPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			editSaveDetailDiscount();
				}
			});
		});
		//加载数据
		var object = $("#sales-datagrid-dispatchBillAddPage").datagrid("getSelected");
		$("#sales-form-dispatchBillDetailAddPage").form("load",object);
		$("#sales-dispatchBill-goodsname").val(object.goodsInfo.name);
		$("#sales-dispatchBill-goodsbrandName").val(object.goodsInfo.brandName);
		$("#sales-dispatchBill-goodsmodel").val(object.goodsInfo.model);
        $("#sales-storageid-billDetailDiscountAddPage").widget('setValue',object.storageid);
   </script>
  </body>
</html>
