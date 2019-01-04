<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                    <td>总金额:</td>
                    <td>
                        <input type="text" id="sales-order-totalamount" style="width: 180px;" readonly
                               class="easyui-numberbox no_input" data-options="precision:2" />
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
                    <td>折扣比率:</td>
                    <td>
                        <input type="text" id="sales-order-discount" style="width: 180px;"/>折
                    </td>
                </tr>
                <tr>
                    <td>折扣金额:</td>
                    <td>
                        <input type="text" id="sales-order-distaxamount" style="width: 180px;"/>
                    </td>
                </tr>
                <tr>
                    <td>备注:</td>
                    <td style="text-align: left;">
                        <input type="text" name="remark" id="goodsDiscountRemark" style="width: 180px;"  value="订单折扣"/>
                    </td>
                </tr>
            </table>
        </form>
        </div>
        <div data-options="region:'south',border:false">
            <div class="buttonDetailBG" style="height:30px;text-align:center;">
                <input type="button" value="确定" id="sales-save-billDetailOrderDiscountAddPage" />
            </div>
        </div>
    </div>
<script type="text/javascript">
    var all = false;
    var changeFlag = true;
    <security:authorize url="/sales/discountOrderPage2.do">
    all = true;
    </security:authorize>

    var detailrows =  $("#sales-datagrid-orderAddPage").datagrid('getRows');
    var brandAmount = "";
    for(var i=0; i<detailrows.length; i++){
        var rowJson = detailrows[i];
        if(!isObjectEmpty(rowJson) && rowJson.goodsInfo != undefined && (rowJson.isdiscount==null ||rowJson.isdiscount=='0')){
            if(brandAmount == ""){
                brandAmount = rowJson.taxamount;
            }else{
                brandAmount = Number(rowJson.taxamount)+Number(brandAmount);
            }
        }
    }

    $("#sales-order-totalamount").val(brandAmount);

    $(function(){
        //折扣比率
        $("#sales-order-discount").numberbox({
            precision:2,
            required:true,
            min:0,
            max:10,
            onChange:function(newValue,oldValue){
                if(newValue!=oldValue && changeFlag){
                    //总金额
                    var amount = $("#sales-order-totalamount").numberbox("getValue");
                    var proAmount = Number(amount)*Number(newValue)/Number(10);
                    var distaxamount = amount - proAmount;
                    changeFlag = false;
                    //负折扣生成负的金额
                    if(all){
                        $("#sales-order-distaxamount").numberbox("setValue",-distaxamount);
                    }else{
                        $("#sales-order-distaxamount").numberbox("setValue",distaxamount);
                    }

                    $("#goodsDiscountRemark").val("订单折扣："+parseFloat(newValue)+"折");

                }else{
                    changeFlag = true ;
                }

            }
        });

        //折扣金额
        $("#sales-order-distaxamount").numberbox({
            precision:2,
            required:true,
            onChange:function(newValue,oldValue){
                if(!all && newValue < 0){
                    $("#sales-order-distaxamount").numberbox("setValue",-newValue);
                    newValue = - newValue ;
                }else if(all && newValue < 0){
                    newValue = - newValue ;
                }

                if(newValue!=oldValue && changeFlag){
                    //总金额
                    var amount = $("#sales-order-totalamount").numberbox("getValue");
                    var discount = Number(newValue)/Number(amount)*Number(10);
                    changeFlag = false;
                    $("#sales-order-discount").numberbox("setValue",10-discount);
                    var count =  $("#sales-order-discount").numberbox("getValue");
                    $("#goodsDiscountRemark").val("订单折扣："+parseFloat(count)+"折");

                }else{
                    changeFlag = true;
                }
            }
        });

        $("#sales-save-billDetailOrderDiscountAddPage").click(function(){
            var brandAmountMap = {};
            var goodsid = {} ;
            var countAmount = "";
            var countNum = 0,countTotalbox = 0;
            var rowIndex = "";
            var index = 0;
            var repartitiontype = $("#sales-order-repartitionType").val();
            $wareList.datagrid('loadData', []);
            for(var i=0; i<detailrows.length; i++){
                var rowJson = detailrows[i];
                if(!isObjectEmpty(rowJson)){
                    if(rowJson.isdiscount == '0'){
                        $wareList.datagrid('insertRow', {index:index, row:rowJson});
                        index++;
                    }
                }
            }

            for(var i=0; i<detailrows.length; i++){
                var rowJson = detailrows[i];
                if(rowJson.goodsid != undefined ){
                    var brand= rowJson.goodsInfo.brand;
                    var brandname = rowJson.goodsInfo.brandName;
                    var brand = brand + "&" + brandname ;

                    if(brand!=null && brand!="" && (rowJson.isdiscount == '0' || rowJson.isdiscount == undefined )){
                        countAmount = Number(countAmount) + Number(rowJson.taxamount);
                        countNum = Number(countNum) + Number(rowJson.unitnum);
                        countTotalbox = Number(countTotalbox) + Number(rowJson.totalbox);

                        if(repartitiontype == "0"){//金额
                            if(i==0){//这里判断map为空有问题，改为判断是否是第一个商品
                                brandAmountMap[brand] = rowJson.taxamount;
                                goodsid[brand] = rowJson.goodsid ;
                            }else{
                                if(brand in brandAmountMap){
                                    brandAmountMap[brand] = Number(brandAmountMap[brand]) + Number(rowJson.taxamount);
                                }else{
                                    brandAmountMap[brand] = rowJson.taxamount;
                                    goodsid[brand] = rowJson.goodsid ;
                                }
                            }
                        }else if(repartitiontype == "1"){//数量
                            if(i==0){//这里判断map为空有问题，改为判断是否是第一个商品
                                brandAmountMap[brand] = rowJson.unitnum;
                                goodsid[brand] = rowJson.goodsid ;
                            }else{
                                if(brand in brandAmountMap){
                                    brandAmountMap[brand] = Number(brandAmountMap[brand]) + Number(rowJson.unitnum);
                                }else{
                                    brandAmountMap[brand] = rowJson.unitnum;
                                    goodsid[brand] = rowJson.goodsid ;
                                }
                            }
                        }else if(repartitiontype == "2"){//箱数
                            if(i==0){//这里判断map为空有问题，改为判断是否是第一个商品
                                brandAmountMap[brand] = rowJson.totalbox;
                                goodsid[brand] = rowJson.goodsid ;
                            }else{
                                if(brand in brandAmountMap){
                                    brandAmountMap[brand] = Number(brandAmountMap[brand]) + Number(rowJson.totalbox);
                                }else{
                                    brandAmountMap[brand] = rowJson.totalbox;
                                    goodsid[brand] = rowJson.goodsid ;
                                }
                            }
                        }
                    }
                }else{
                    rowIndex = index;
                    break;
                }
            }
            for(var key in brandAmountMap){
                if(rowIndex == detailrows.length - 1){
                    $wareList.datagrid('appendRow',{}); //如果是最后一行则添加一新行
                }
                //折扣金额
                var distaxamount = $("#sales-order-distaxamount").numberbox("getValue");
                var brandRate = Number(0);
                if(repartitiontype == "0"){//金额
                    brandRate = Number(brandAmountMap[key])/Number(countAmount);
                }else if(repartitiontype == "1"){//数量
                    brandRate = Number(brandAmountMap[key])/Number(countNum);
                }else if(repartitiontype == "2"){//箱数
                    brandRate = Number(brandAmountMap[key])/Number(countTotalbox);
                }
                var brandFee = Number(brandRate)*Number(distaxamount);
                var brandname = key.split("&")[1];
                var brand = key.split("&")[0];
                $.ajax({
                    url :'sales/computeDispatchBillDiscountTax.do',
                    type:'post',
                    data:{brandid:brand,taxamount:brandFee},
                    dataType:'json',
                    async:false,
                    success:function(json){

                        var remark =  $("#goodsDiscountRemark").val();
                        var row = {goodsname:'折扣('+brandname+')',taxamount:brandFee,isdiscount:'2',remark:remark,discount:distaxamount,
                            notaxamount:json.notaxamount,tax:json.tax,taxtype:json.taxtype,taxtypename:json.taxtypename,brandid:brand,repartitiontype:repartitiontype};
                        $wareList.datagrid('updateRow',{index:rowIndex, row:row}); //将数据更新到列表中
                    }
                });
                rowIndex++ ;
            }
            $("#sales-dialog-orderAddPage-content").dialog("close");
            countTotal(leftAmount,receivableAmount);

        });

    });

    getNumberBox("sales-order-discount").bind("keydown", function(event){
        if(event.keyCode==13){
            getNumberBox("sales-order-distaxamount").focus();
            getNumberBox("sales-order-distaxamount").select();
        }
    });
    getNumberBox("sales-order-distaxamount").bind("keydown", function(event){
        if(event.keyCode==13){
            getNumberBox("sales-order-distaxamount").blur();
            var discount = $("#sales-order-discount").numberbox("getValue");
            if(discount == "" ){
                alert("请填写折扣比率或折扣金额");
                getNumberBox("sales-order-discount").focus();
            }else{
                $("#goodsDiscountRemark").focus();
                $("#goodsDiscountRemark").select();
            }
        }
    });
    $("#goodsDiscountRemark").die("keydown").live("keydown", function(event){
        if(event.keyCode==13){
            $("#sales-save-billDetailOrderDiscountAddPage").focus();
        }
    });

</script>

</body>
</html>
