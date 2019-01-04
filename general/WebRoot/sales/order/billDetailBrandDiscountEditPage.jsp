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
        <form action="" method="post" id="sales-form-orderDetailBrandAddPage">
            <table  border="0">
                <tr>
                    <td width="120">品牌名称:</td>
                    <td style="text-align: left;">
                        <input type="text" id="sales-order-goodsname" style="width: 180px;" class="no_input" readonly="readonly"/>
                        <input type="hidden" id="sales-order-goodsid" name="brandid"/>
                    </td>
                </tr>
                <tr>
                    <td>摊分方式:</td>
                    <td style="text-align: left;">
                        <select id="sales-order-repartitionType" style="width: 180px;" name="repartitiontype">
                            <option></option>
                            <option value="0">金额</option>
                            <option value="1">数量</option>
                            <option value="2">箱数</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>品牌总额：</td>
                    <td> <input type="text" id="sales-order-amount" name="amount" readonly="readonly"
                                class="easyui-numberbox no_input" data-options="precision:2,required: true" style="width: 180px;"/></td>
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
                    <td colspan="3" style="text-align: left;">
                        <input type="text" id="sales-order-notaxamount" name="notaxamount" class="no_input" style="width: 180px;" readonly="readonly"/>
                        <input type="hidden" id="sales-order-tax" name="tax" class="no_input" readonly="readonly"/>
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
                    <td colspan="3" style="text-align: left;">
                        <input type="text" name="remark" id="goodsDiscountRemark" style="width: 185px;" maxlength="200" value="品牌折扣"/>
                    </td>
                </tr>
            </table>

        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align:right;">
            <input type="button" value="确定" name="savegoon" id="sales-savegoon-billDetailAddBrandDiscountPage" />
        </div>
    </div>
</div>
<script type="text/javascript">
    var all = false;
    <security:authorize url="/sales/orderBrandDiscountAddPage2.do">
    all = true;
    </security:authorize>

    //加载数据
    var object = $("#sales-datagrid-orderAddPage").datagrid("getSelected");
    //$("#sales-form-orderDetailBrandAddPage").form("load",object);

    //订单折扣生成的品牌折扣
    if(object.goodsInfo == undefined){
        var goodsname = object.goodsname.replace("折扣(","");
        goodsname = goodsname.replace(")","");
        $("#sales-order-goodsname").val(goodsname);

    }else{
        $("#sales-order-goodsname").val(object.goodsInfo.name);
    }
    var brandAmount = "";
    var brandid = "";
    //获取品牌总额
    if(object.amount == undefined ){
        var detailrows =  $("#sales-datagrid-orderAddPage").datagrid('getRows');
        for(var i=0; i<detailrows.length; i++){
            var rowJson = detailrows[i];
            if(object.goodsInfo != undefined){
                brandid = object.goodsInfo.brand;
            }else{
                brandid = object.brandid;
            }
            if(rowJson.goodsInfo != undefined && rowJson.goodsInfo.brand == brandid
                    && (rowJson.isdiscount==null || rowJson.isdiscount=="" ||rowJson.isdiscount=='0')){
                if(brandAmount == ""){
                    brandAmount = rowJson.taxamount;
                }else{
                    brandAmount = Number(rowJson.taxamount)+Number(brandAmount);
                }
            }
        }
        $("#sales-order-taxamount").val(object.taxamount);
        //折扣金额为负时计算时取正值
        var distaxamount = object.taxamount;
        if(distaxamount <0){
            distaxamount = -distaxamount;
        }
        var discount = Number(1)- Number(distaxamount) / Number(brandAmount);
        discount = discount.toFixed(2)*Number(10);
        $("#sales-order-amount").val(brandAmount);
        $("#sales-order-discount").val(discount);
    }else{
        $("#sales-order-amount").val(object.amount);
        $("#sales-order-discount").val(object.discount);
        $("#sales-order-taxamount").val(object.taxamount);
    }

    $("#goodsDiscountRemark").val(object.remark);
    $("#sales-order-notaxamount").val(object.notaxamount);
    $("#sales-order-repartitionType").val(object.repartitiontype);
    $("#sales-order-storageid").widget('setValue',object.storageid);
    $("#sales-order-storagename").val(object.storagename);
    $("#sales-order-goodsid").val(object.brandid);
    var changeFlag = true;

    $(function(){
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

                    var amount = $("#sales-order-amount").val();
                    var proAmount = Number(amount)*Number(newValue)/Number(10);
                    var taxamount = amount - proAmount ;
                    changeFlag = false;
                    if(all){
                        $("#sales-order-taxamount").numberbox("setValue",-taxamount);
                    }else{
                        $("#sales-order-taxamount").numberbox("setValue",taxamount);
                    }
                    $("#goodsDiscountRemark").val("品牌折扣："+parseFloat(newValue)+"折");

                    computeDiscount(object.brandid,"",taxamount);
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
                    computeDiscount(object.brandid,"",taxamount);

                    //如果折扣金额为负 计算转换成正数
                    if(taxamount < 0){
                        taxamount = -taxamount;
                    }
                    var discount = Number(taxamount)/Number(amount)*Number(10);
                    changeFlag = false;
                    $("#sales-order-discount").numberbox("setValue",10-discount);
                    changeFlag = true;

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
            editSaveDetailBrandDiscount();
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
                editSaveDetailBrandDiscount();
            }
        });
    });


</script>
</body>
</html>
