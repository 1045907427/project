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
		   	<form action="sales/addSaveReceiptDetailBrandDiscount.do" method="post" id="sales-form-receiptDetailBrandAddPage">
		   		<table  border="0" >
		   			<tr>
		   				<td width="120">选择品牌:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="sales-receipt-brandid" width="180" name="brandid"/>
                            <input type="hidden" name="receiptid" value="${receiptid}"/>
		   				</td>
		   			</tr>
                    <tr>
                        <td>摊分方式:</td>
                        <td style="text-align: left;">
                            <select id="sales-receipt-repartitionType" style="width: 180px;" name="repartitiontype">
                                <option></option>
                                <option value="0" <c:if test="${repartitionType == '0'}">selected="selected"</c:if>>金额</option>
                                <option value="1" <c:if test="${repartitionType == '1'}">selected="selected"</c:if>>数量</option>
                                <option value="2" <c:if test="${repartitionType == '2'}">selected="selected"</c:if>>箱数</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>品牌总额：</td>
                        <td> <input type="text" id="sales-receipt-amount" name="amount" readonly  class="easyui-numberbox no_input" data-options="precision:2,required: true"  style="width: 180px;"/></td>
                    </tr>
                    <tr>
                        <td>折扣比率:</td>
                        <td>
                            <input type="text" id="sales-receipt-discount" name="discount" style="width: 180px;"/>折
                        </td>
                    </tr>
		   			<tr>
		   				<td>折扣金额:</td>
		   				<td>
		   					<input type="text" id="sales-receipt-taxamount" name="taxamount" style="width: 180px;"/>
		   					<input type="hidden" name="isdiscount" value="2"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td width="120">折扣无税额:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="sales-receipt-notaxamount" name="notaxamount" class="no_input" style="width: 180px;" readonly="readonly"/>
		   					<input type="hidden" id="sales-receipt-tax" name="tax" class="no_input" readonly="readonly"/>
		   					<input type="hidden" id="sales-receipt-taxtype" name="taxtype">
		   					<input type="hidden" id="sales-receipt-taxtypename" name="taxtypename">
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
   		<security:authorize url="/sales/receiptBrandDiscountAddPage2.do">
   			all = true;
   		</security:authorize>
   		var detailrows =  $("#sales-datagrid-receiptAddPage").datagrid('getRows');
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
   			$("#sales-receipt-brandid").widget({
   				referwid:'RL_T_BASE_GOODS_BRAND',
    			param:[{field:'id',op:'in',value:brandid}],
    			width:180,
    			onSelect:function(data){
                    var brandAmount = 0;
                    for(var i=0; i<detailrows.length; i++){
                        var rowJson = detailrows[i];
                        if(rowJson.goodsid != undefined && rowJson.brandid == data.id && (rowJson.isdiscount==null ||rowJson.isdiscount=="" ||rowJson.isdiscount=='0')){
                            if(brandAmount == ""){
                                brandAmount = rowJson.taxamount;
                            }else{
                                brandAmount = Number(rowJson.taxamount)+Number(brandAmount);
                            }
                        }
                    }
                    $("#sales-receipt-amount").numberbox("setValue",brandAmount);
//    				$("#sales-receipt-goodsbrandName").val(data.brandName);
//    				$("#sales-receipt-goodsmodel").val(data.model);
                    getNumberBox("sales-receipt-discount").focus();
    			}
   			});

            //折扣比率
            $("#sales-receipt-discount").numberbox({
                precision:2,
                required:true,
                min:0,
                max:10,
                onChange:function(newValue,oldValue){
                    if(newValue!=oldValue && changeFlag){
                        var amount = $("#sales-receipt-amount").numberbox("getValue");
                        var proAmount = Number(amount)*Number(newValue)/Number(10);
                        var taxamount = amount - proAmount ;
                        changeFlag = false;
                        if(all){
                            $("#sales-receipt-taxamount").numberbox("setValue",-taxamount);
                            taxamount = - taxamount ;
                        }else{
                            $("#sales-receipt-taxamount").numberbox("setValue",taxamount);
                        }
                        $("#goodsDiscountRemark").val("品牌折扣："+parseFloat(newValue)+"折");

                        var brandid = $("#sales-receipt-brandid").widget("getValue");
                        computeDiscount(brandid,"",taxamount);
                    }else{
                        changeFlag = true;
                    }

                }
            });
            //折扣金额
			$("#sales-receipt-taxamount").numberbox({
				precision:2,
				required:true,
				onChange:function(newValue,oldValue){

                    if(newValue!=oldValue && changeFlag){

                        if(!all){
                            if(Number(newValue)<0){
                                $("#sales-receipt-taxamount").numberbox("setValue",-Number(newValue));
                            }
                        }
                        var amount = $("#sales-receipt-amount").val();
                        var taxamount = $("#sales-receipt-taxamount").numberbox("getValue");

                        computeDiscount(brandid,"",taxamount);

                        //如果折扣金额为负 计算转换成正数
                        if(taxamount < 0){
                            taxamount = -taxamount;
                        }
                        var discount = Number(taxamount)/Number(amount)*Number(10);

                        changeFlag = false;
                        $("#sales-receipt-discount").numberbox("setValue",10-discount);
                        changeFlag = true ;

                        var count = $("#sales-receipt-discount").numberbox("getValue");
                        $("#goodsDiscountRemark").val("品牌折扣："+parseFloat(count)+"折");

                    }else{
                        changeFlag = true;
                    }
				}
			});

			$("#sales-receipt-notaxamount").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#sales-savegoon-billDetailAddBrandDiscountPage").click(function(){
                var flag = $("#sales-form-receiptDetailBrandAddPage").form('validate');
                if(flag==false){
                    return false;
                }
                $("#sales-form-receiptDetailBrandAddPage").submit();
			});

            $("#sales-form-receiptDetailBrandAddPage").form({
                onSubmit: function(){
                    loading("提交中..");
                },
                success:function(data){
                    loaded();
                    var detailrows =  $("#sales-datagrid-receiptAddPage").datagrid('getRows');
                    var rows = $.parseJSON(data);
//                    $wareList.datagrid("loadData",rows);
                    for(var i=0;i<rows.length;i++){
                        var index = $wareList.datagrid("getRowIndex",rows[i].id);
                        $wareList.datagrid("updateRow",{index:index,row:rows[i]});
                    }
                    $("#sales-datagrid-receiptAddPage-content").dialog("close");
                }
            });

            //快捷键
            getNumberBox("sales-receipt-discount").bind("keydown", function(event){
                if(event.keyCode==13){
                    getNumberBox("sales-receipt-taxamount").focus();
                    getNumberBox("sales-receipt-taxamount").select();
                }
            });
            getNumberBox("sales-receipt-taxamount").bind("keydown",function(event){
				//enter
				if(event.keyCode==13){
                    getNumberBox("sales-receipt-taxamount").blur();
                    var discount = $("#sales-receipt-discount").numberbox("getValue");
                    if(discount == "" ){
                        alert("请填写折扣比率或折扣金额");
                        getNumberBox("sales-receipt-discount").focus();
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
