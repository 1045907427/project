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
		   	<form action="" method="post" id="sales-form-dispatchBillDetailBrandAddPage">
		   		<table  border="0">
		   			<tr>
		   				<td width="120">品牌名称:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="sales-dispatchBill-brandname" style="width: 180px;" class="no_input" readonly="readonly"/>
		   					<input type="hidden" id="sales-dispatchBill-brandid" name="brandid"/>
		   				</td>
		   			</tr>
					<tr>
						<td>摊分方式:</td>
						<td style="text-align: left;">
							<select id="sales-dispatchBill-repartitionType" style="width: 180px;" name="repartitiontype">
								<option></option>
								<option value="0">金额</option>
								<option value="1">数量</option>
								<option value="2">箱数</option>
							</select>
						</td>
					</tr>
		   			<tr>
		   				<td>折扣金额:</td>
		   				<td>
		   					<input type="text" id="sales-dispatchBill-taxamount" name="taxamount" style="width: 180px;"/>
		   					<input type="hidden" name="isdiscount" value="2"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td width="120">折扣无税额:</td>
		   				<td colspan="3" style="text-align: left;">
		   					<input type="text" id="sales-dispatchBill-notaxamount" name="notaxamount" class="no_input" style="width: 180px;" readonly="readonly"/>
		   					<input type="hidden" id="sales-dispatchBill-tax" name="tax" class="no_input" readonly="readonly"/>
		   				</td>
		   			</tr>
                    <tr>
                        <td width="120">指定仓库:</td>
                        <td style="text-align: left;">
                            <input id="sales-dispatchBill-storageid" name="storageid"/>
                            <input type="hidden" id="sales-dispatchBill-storagename" name="storagename"/>
                        </td>
                    </tr>
		   			<tr>
		   				<td>备注:</td>
		   				<td colspan="3" style="text-align: left;">
		   					<input type="text" id="sales-dispatchBill-brandremark" name="remark" style="width: 180px;" maxlength="200" value="品牌折扣"/>
		   				</td>
		   			</tr>
		   		</table>
		   		
		    </form>
		    </div>
	  		<div data-options="region:'south',border:false">
	  			<div  class="buttonDetailBG" style="text-align:right;">
		  			<input type="button" value="确 定" name="savegoon" id="sales-savegoon-billDetailAddBrandDiscountPage" />
		  			<!-- <input type="button" value="确定" name="savenogo" /> -->
	  			</div>
	  		</div>
	  	</div>
   <script type="text/javascript">
   		var all = false;
   		<security:authorize url="/sales/dispatchBillAddBrandDiscountPage2.do">
   			all = true;
   		</security:authorize>
   		$(function(){
            $("#sales-dispatchBill-storageid").widget({
                name:'t_sales_dispatchbill_detail',
                col:'storageid',
                width:180,
                onSelect: function(data){
                    $("#sales-dispatchBill-storagename").val(data.name);
                },
                onClear: function(){
                    $("#sales-dispatchBill-storagename").val("");
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
						var brandid = $("#sales-dispatchBill-brandid").val();
						var taxamount = $("#sales-dispatchBill-taxamount").numberbox("getValue");
						$.ajax({   
				            url :'sales/computeDispatchBillDiscountTax.do',
				            type:'post',
				            data:{brandid:brandid,taxamount:taxamount},
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
			$("#sales-savegoon-billDetailAddBrandDiscountPage").click(function(){
				editSaveDetailBrandDiscount();
			});
			getNumberBox("sales-dispatchBill-taxamount").die("keydown").bind("keydown",function(event){
				if(event.keyCode==13){
					$("#sales-dispatchBill-brandremark").focus();
					$("#sales-dispatchBill-brandremark").select();
				}
			});
			$("#sales-dispatchBill-brandremark").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#sales-savegoon-billDetailAddBrandDiscountPage").focus();
				}
			});
			$("#sales-savegoon-billDetailAddBrandDiscountPage").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					addSaveDetailBrandDiscount();
				}
			});
		});
		//加载数据
		var object = $("#sales-datagrid-dispatchBillAddPage").datagrid("getSelected");
		$("#sales-form-dispatchBillDetailBrandAddPage").form("load",object);
		$("#sales-dispatchBill-brandname").val(object.goodsInfo.name);
		$("#sales-dispatchBill-goodsmodel").val(object.goodsInfo.model);
		$("#sales-dispatchBill-repartitionType").val(object.repartitiontype);
        $("#sales-dispatchBill-storageid").widget("setValue",object.storageid);
   </script>
  </body>
</html>
