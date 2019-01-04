<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>销售订货单商品折扣修改</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form action="" method="post" id="sales-form-orderGoodsDetailAddPage">
            <table  border="0" class="box_table">
                <tr>
                    <td width="120">商品名称:</td>
                    <td style="text-align: left;">
                        <input type="text" id="sales-order-goodsname" width="180"/>
                        <input type="hidden" id="sales-order-goodsid" name="goodsid"/>
                    </td>
                    <td>商品总额：</td>
                    <td><input type="text" id="sales-order-amount"  width="180"/></td>
                </tr>
                <tr>
                    <td>折扣比率:</td>
                    <td style="text-align: left;">
                        <input type="text" id="sales-order-discount" width="180" />折
                    </td>
                    <td>折扣金额:</td>
                    <td style="text-align: left;">
                        <input type="text" id="sales-order-taxamount" name="taxamount"/>
                        <input type="hidden" name="isdiscount" value="1"/>
                    </td>
                </tr>
                <tr>
                    <td width="120">商品品牌:</td>
                    <td>
                        <input type="text" id="sales-order-goodsbrandName" class="no_input" readonly="readonly"/>
                    </td>
                    <td width="120">规格型号:</td>
                    <td>
                        <input type="text" id="sales-order-goodsmodel" class="no_input" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td width="120">折扣无税额:</td>
                    <td  style="text-align: left;">
                        <input type="text" id="sales-order-notaxamount" name="notaxamount" class="no_input" readonly="readonly"/>
                        <input type="hidden" id="sales-order-tax" name="tax" class="no_input" readonly="readonly"/>
                    </td>
                    <td width="120">指定仓库:</td>
                    <td style="text-align: left;">
                        <input id="sales-order-storageid"  name="storageid" /></td>
                    <input id="sales-order-storagename" name="storagename" type="hidden" />
                    </td>
                </tr>
                <tr>
                    <td>备注:</td>
                    <td colspan="3" style="text-align: left;">
                        <input type="text" name="remark" id="goodsDiscountRemark" style="width: 488px;" value="商品折扣"/>
                    </td>
                </tr>
            </table>

        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="text-align:right;">
            <input type="button" value="确定" name="savegoon" id="sales-savegoon-billDetailAddDiscountPage" />
        </div>
    </div>
</div>
<script type="text/javascript">

    var row = $wareList.datagrid('getSelected');
    var remark = row.remark ;
    var beDiscount = remark.split("：")[1].replace("折","");

    var all = false;
    <security:authorize url="/sales/orderDiscountAddPage2.do">
    all = true;
    </security:authorize>
    var changeFlag = true;
    $(function(){
        $("#sales-order-storageid").widget({
            name:'t_sales_dispatchbill_detail',
            col:'storageid',
            width:165,
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
                    var amount = $("#sales-order-amount").val();
                    var proAmount = Number(amount)*Number(newValue)/Number(10);
                    var taxamountABS = amount - proAmount ;
                    changeFlag = false;

                    if(all){
                        $("#sales-order-taxamount").numberbox("setValue",-taxamountABS);
                        taxamountABS = - taxamountABS;
                    }else{
                        $("#sales-order-taxamount").numberbox("setValue",taxamountABS);
                    }
                    //修改时避免根据折扣率计算的折扣金额和实际金额的偏差
                    if(newValue == beDiscount){
                        $("#sales-order-taxamount").numberbox("setValue",row.taxamount);
                    }

                    $("#goodsDiscountRemark").val("商品折扣："+parseFloat(newValue)+"折");

                    var goodsid = $("#sales-order-goodsid").val();
                    computeDiscount("",goodsid,taxamountABS);
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

                    var discount = Number(taxamount)/Number(amount)*Number(10);
                    discount = 10- Math.abs(discount) ;

                    changeFlag = false;
                    $("#sales-order-discount").numberbox("setValue",discount);
                    changeFlag = true ;
                    var count = $("#sales-order-discount").numberbox("getValue");
                    $("#goodsDiscountRemark").val("商品折扣："+parseFloat(count)+"折");

                    var goodsid = $("#sales-order-goodsid").val();
                    computeDiscount("",goodsid,taxamount);

                }else{
                    changeFlag = true;
                }
            }
        });

        $("#sales-order-notaxamount").numberbox({
            precision:6,
            groupSeparator:','
        });

        $("#sales-savegoon-billDetailAddDiscountPage").click(function(){
            editSaveDetailDiscount();
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
    var object = $("#sales-datagrid-orderAddPage").datagrid("getSelected");
    $("#sales-form-orderGoodsDetailAddPage").form("load",object);
    $("#sales-order-goodsname").val(object.goodsInfo.name);
    $("#sales-order-goodsbrandName").val(object.goodsInfo.brandName);
    $("#sales-order-goodsmodel").val(object.goodsInfo.model);
    $("#sales-order-storageid").widget('setValue',object.storageid);
    //$("#sales-order-storagename").widget('setText',object.storagename);
    $("#sales-order-storagename").val(object.storagename);

    var detailrows = $("#sales-datagrid-orderAddPage").datagrid('getRows');
    var totalamount = Number(0);
    for(var i=0; i<detailrows.length; i++){
        var rowJson = detailrows[i];
        if(!isObjectEmpty(rowJson)){
            if(rowJson.goodsid != undefined && rowJson.goodsid == object.goodsInfo.id && (rowJson.isdiscount==null || rowJson.isdiscount==""||rowJson.isdiscount=='0')){
                totalamount += Number(rowJson.taxamount);
            }
        }
    }
    $("#sales-order-amount").val(totalamount);
    var taxamount = Number(object.taxamount);
    if(taxamount<0){
        taxamount = - taxamount ;
    }
    var proAmount = Number(totalamount) - taxamount;
    var discount = Number(proAmount)/Number(totalamount)*Number(10);
    $("#sales-order-discount").numberbox("setValue",discount.toFixed(2));
    changeFlag = true;
</script>
</body>
</html>
