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
		   	<form action="" method="post" id="sales-form-orderDetailBrandAddPage">
		   		<table  border="0" >
		   			<tr>
		   				<td width="120">选择品牌:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="sales-order-brandid" width="180" name="brandid"/>
		   				</td>
		   			</tr>
                    <tr>
                        <td>摊分方式:</td>
                        <td style="text-align: left;">
                            <select id="sales-order-repartitionType" style="width: 180px;" name="repartitiontype">
                                <option></option>
                                <option value="0" <c:if test="${repartitionType == '0'}">selected="selected"</c:if>>金额</option>
                                <option value="1" <c:if test="${repartitionType == '1'}">selected="selected"</c:if>>数量</option>
                                <option value="2" <c:if test="${repartitionType == '2'}">selected="selected"</c:if>>箱数</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>品牌总额：</td>
                        <td> <input type="text" id="sales-order-amount" name="amount" readonly  class="easyui-numberbox no_input"
                                    data-options="precision:2,required: true"  style="width: 180px;"/></td>
                    </tr>
                    <tr>
                        <td>折扣比率:</td>
                        <td>
                            <input type="text" id="sales-order-discount" name="discount" style="width: 180px;"/>折
                        </td>
                    </tr>
		   			<tr>
		   				<td>折扣金额:</td>
		   				<td>
		   					<input type="text" id="sales-order-taxamount" name="taxamount" style="width: 180px;"/>
		   					<input type="hidden" name="isdiscount" value="2"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td width="120">折扣无税额:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="sales-order-notaxamount" name="notaxamount" class="no_input" style="width: 180px;" readonly="readonly"/>
		   					<input type="hidden" id="sales-order-tax" name="tax" class="no_input" readonly="readonly"/>
		   					<input type="hidden" id="sales-order-taxtype" name="taxtype">
		   					<input type="hidden" id="sales-order-taxtypename" name="taxtypename">
		   				</td>
		   			</tr>
                    <tr>
                        <td width="120">指定仓库:</td>
                        <td style="text-align: left;">
                            <input id="sales-order-storageid" name="storageid"/>
                            <input type="hidden" id="sales-order-storagename" name="storagename"/>
                        </td>
                    </tr>
		   			<tr>
		   				<td>备注:</td>
		   				<td style="text-align: left;">
		   					<input type="text" name="remark" id="goodsDiscountRemark" style="width: 180px;" maxlength="200" value="品牌折扣"/>
		   				</td>
		   			</tr>
		   		</table>
		    </form>
		    </div>
	  		<div data-options="region:'south',border:false">
	  			<div class="buttonDetailBG" style="height:30px;text-align:right;">
		  			<input type="button" value="确定" name="savegoon" id="sales-savegoon-billDetailAddBrandDiscountPage" />
		  			<!-- <input type="button" value="确定" name="savenogo" /> -->
	  			</div>
	  		</div>
	  	</div>
   <script type="text/javascript">
   		var all = false;
        var changeFlag = true;
   		<security:authorize url="/sales/orderBrandDiscountAddPage2.do">
   			all = true;
   		</security:authorize>
   		var detailrows =  $("#sales-datagrid-orderAddPage").datagrid('getRows');
   		var brandid = "";

   		for(var i=0; i<detailrows.length; i++){
   			var rowJson = detailrows[i];
            if(!isObjectEmpty(rowJson)){
                if(rowJson.goodsInfo != undefined && (rowJson.isdiscount==null ||rowJson.isdiscount=="" ||rowJson.isdiscount=='0')){
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
   		}
   		$(function(){
   			$("#sales-order-brandid").widget({
   				referwid:'RL_T_BASE_GOODS_BRAND',
    			param:[{field:'id',op:'in',value:brandid}],
    			width:180,
    			onSelect:function(data){
                    var brandAmount = 0;
                    for(var i=0; i<detailrows.length; i++){
                        var rowJson = detailrows[i];
                        if(rowJson.goodsid != undefined && rowJson.brandid == data.id
                                && (rowJson.isdiscount==null ||rowJson.isdiscount=="" ||rowJson.isdiscount=='0')){
                            if(brandAmount == ""){
                                brandAmount = rowJson.taxamount;
                            }else{
                                brandAmount = Number(rowJson.taxamount)+Number(brandAmount);
                            }
                        }
                    }
                    $("#sales-order-amount").numberbox("setValue",brandAmount);
    				$("#sales-order-goodsbrandName").val(data.brandName);
    				$("#sales-order-goodsmodel").val(data.model);
                    getNumberBox("sales-order-discount").focus();
    			}
   			});

            $("#sales-order-storageid").widget({
                name:'t_sales_dispatchbill_detail',
                col:'storageid',
                width:180,
                onSelect: function(data){
                    $("#sales-order-storagename").val(data.name);
                },
                onClear: function(){
                    $("#sales-order-storagename").val("");
                }
            });

            //折扣比率
            $("#sales-order-discount").numberbox({
                precision:2,
                required:true,
                min:0,
                max:10,
                onChange:function(newValue,oldValue){
                    if(newValue!=oldValue && changeFlag){
                        var amount = $("#sales-order-amount").numberbox("getValue");
                        var proAmount = Number(amount)*Number(newValue)/Number(10);
                        var taxamount = amount - proAmount ;
                        changeFlag = false;
                        if(all){
                            $("#sales-order-taxamount").numberbox("setValue",-taxamount);
                            taxamount = - taxamount ;
                        }else{
                            $("#sales-order-taxamount").numberbox("setValue",taxamount);
                        }
                        $("#goodsDiscountRemark").val("品牌折扣："+parseFloat(newValue)+"折");

                        var brandid = $("#sales-order-brandid").widget("getValue");
                        computeDiscount(brandid,"",taxamount);
                    }else{
                        changeFlag = true;
                    }

                }
            });
            //折扣金额
			$("#sales-order-taxamount").numberbox({
				precision:2,
				required:true,
				onChange:function(newValue,oldValue){

                    if(newValue!=oldValue && changeFlag){

                        if(!all){
                            if(Number(newValue)<0){
                                $("#sales-order-taxamount").numberbox("setValue",-Number(newValue));
                            }
                        }
                        var amount = $("#sales-order-amount").val();
                        var taxamount = $("#sales-order-taxamount").numberbox("getValue");

                        computeDiscount(brandid,"",taxamount);

                        //如果折扣金额为负 计算转换成正数
                        if(taxamount < 0){
                            taxamount = -taxamount;
                        }
                        var discount = Number(taxamount)/Number(amount)*Number(10);

                        changeFlag = false;
                        $("#sales-order-discount").numberbox("setValue",10-discount);
                        changeFlag = true ;

                        var count = $("#sales-order-discount").numberbox("getValue");
                        $("#goodsDiscountRemark").val("品牌折扣："+parseFloat(count)+"折");

                    }else{
                        changeFlag = true;
                    }
				}
			});

			$("#sales-order-notaxamount").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#sales-savegoon-billDetailAddBrandDiscountPage").click(function(){
				addSaveDetailBrandDiscount();
			});

            //快捷键
            getNumberBox("sales-order-discount").bind("keydown", function(event){
                if(event.keyCode==13){
                    getNumberBox("sales-order-taxamount").focus();
                    getNumberBox("sales-order-taxamount").select();
                }
            });
            getNumberBox("sales-order-taxamount").bind("keydown",function(event){
				//enter
				if(event.keyCode==13){
                    getNumberBox("sales-order-taxamount").blur();
                    var discount = $("#sales-order-discount").numberbox("getValue");
                    if(discount == "" ){
                        alert("请填写折扣比率或折扣金额");
                        getNumberBox("sales-order-discount").focus();
                    }else{
                        $("#goodsDiscountRemark").focus();
                    }
				}
			});

            $("#goodsDiscountRemark").die("keydown").live("keydown",function(event){
                if(event.keyCode == 13){
                    $("#sales-savegoon-billDetailAddBrandDiscountPage").focus();
                }
            });

			$("#sales-savegoon-billDetailAddBrandDiscountPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			addSaveDetailBrandDiscount();
				}
			});

		});
   </script>
  </body>
</html>
