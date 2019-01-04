<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
		   	<form action="" method="post" id="sales-form-dispatchBillDetailBrandAddPage">
		   		<table  border="0" >
		   			<tr>
		   				<td width="120">选择品牌:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="sales-dispatchBill-brandid" width="180" name="brandid"/>
		   				</td>
		   				
		   			</tr>
					<tr>
						<td>摊分方式:</td>
						<td style="text-align: left;">
							<select id="sales-dispatchBill-repartitionType" style="width: 180px;" name="repartitiontype">
								<option></option>
								<option value="0" <c:if test="${repartitionType == '0'}">selected="selected"</c:if>>金额</option>
								<option value="1" <c:if test="${repartitionType == '1'}">selected="selected"</c:if>>数量</option>
								<option value="2" <c:if test="${repartitionType == '2'}">selected="selected"</c:if>>箱数</option>
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
		   				<td style="text-align: left;">
		   					<input type="text" id="sales-dispatchBill-notaxamount" name="notaxamount" class="no_input" style="width: 180px;" readonly="readonly"/>
		   					<input type="hidden" id="sales-dispatchBill-tax" name="tax" class="no_input" readonly="readonly"/>
		   					<input type="hidden" id="sales-dispatchBill-taxtype" name="taxtype">
		   					<input type="hidden" id="sales-dispatchBill-taxtypename" name="taxtypename">
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
		   				<td style="text-align: left;">
		   					<input type="text" id="sales-dispatchBill-brandremark" name="remark" style="width: 180px;" maxlength="200" value="品牌折扣"/>
		   				</td>
		   			</tr>
		   		</table>
		    </form>
		    </div>
	  		<div data-options="region:'south',border:false">
	  			<div class="buttonDetailBG" style="height:30px;text-align:right;">
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
   		var detailrows = $("#sales-datagrid-dispatchBillAddPage").datagrid('getRows');
   		var brandid = "";
   		for(var i=0; i<detailrows.length; i++){
   			var rowJson = detailrows[i];
   			if(rowJson.goodsInfo != undefined && (rowJson.isdiscount==null ||rowJson.isdiscount=='0')){
   				var brand= rowJson.goodsInfo.brand;
   				if(brand!=null && brand!=""){
	   				if(brandid.indexOf(brand)==-1){
	   					if(brandid==""){
	   						brandid = brand;
	   					}else{
		   					brandid += ","+brand;
		   				}
	   				}
   				}
   			}
   		}
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
   			$("#sales-dispatchBill-brandid").widget({
   				referwid:'RL_T_BASE_GOODS_BRAND',
    			param:[{field:'id',op:'in',value:brandid}],
    			width:180,
    			onSelect:function(data){
    				$("#sales-dispatchBill-goodsbrandName").val(data.brandName);
    				$("#sales-dispatchBill-goodsmodel").val(data.model);
					getNumberBox("sales-dispatchBill-taxamount").focus();
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
					var brandid = $("#sales-dispatchBill-brandid").widget("getValue");
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
			            	$("#sales-dispatchBill-taxtype").val(json.taxtype);
			            	$("#sales-dispatchBill-taxtypename").val(json.taxtypename);
			            }
			        });
				}
			});
			$("#sales-dispatchBill-notaxamount").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#sales-savegoon-billDetailAddBrandDiscountPage").click(function(){
				addSaveDetailBrandDiscount();
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
   </script>
  </body>
</html>
