<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>回单折扣页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form action="sales/addSaveBillDetailReceiptDiscount.do" method="post" id="sales-form-receiptDetailBrandAddPage">
            <table  border="0" >
                <tr>
                    <td>总金额:</td>
                    <td>
                        <input type="text" id="sales-receipt-totalamount" style="width: 180px;" name="totalamount" readonly="readonly" class="easyui-numberbox no_input" data-options="precision:2" />
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
                    <td>折扣比率:</td>
                    <td>
                        <input type="text" id="sales-receipt-discount" name="discount" style="width: 180px;"/>折
                    </td>
                </tr>
                <tr>
                    <td>折扣金额:</td>
                    <td>
                        <input type="text" id="sales-receipt-distaxamount" name="taxamount" style="width: 180px;"/>
                    </td>
                </tr>
                <tr>
                    <td>备注:</td>
                    <td style="text-align: left;">
                        <input type="text" name="remark" id="goodsDiscountRemark" style="width: 180px;"  value="回单折扣"/>
                    </td>
                </tr>
            </table>
        </form>
        </div>
        <div data-options="region:'south',border:false">
            <div class="buttonDetailBG" style="height:30px;text-align:center;">
                <input type="button" value="确定" id="sales-save-billDetailReceiptDiscountAddPage" />
            </div>
        </div>
    </div>
<script type="text/javascript">
    var all = false;
    var changeFlag = true;
    <security:authorize url="/sales/discountReceiptPage2.do">
    all = true;
    </security:authorize>

    var detailrows =  $("#sales-datagrid-receiptAddPage").datagrid('getRows');
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

    $("#sales-receipt-totalamount").val(brandAmount);

    $(function(){
        //折扣比率
        $("#sales-receipt-discount").numberbox({
            precision:2,
            required:true,
            min:0,
            max:10,
            onChange:function(newValue,oldValue){
                if(newValue!=oldValue && changeFlag){
                    //总金额
                    var amount = $("#sales-receipt-totalamount").numberbox("getValue");
                    var proAmount = Number(amount)*Number(newValue)/Number(10);
                    var distaxamount = amount - proAmount ;
                    changeFlag = false;
                    //负折扣生成负的金额
                    if(all){
                        $("#sales-receipt-distaxamount").numberbox("setValue",-distaxamount);
                    }else{
                        $("#sales-receipt-distaxamount").numberbox("setValue",distaxamount);
                    }

                    $("#goodsDiscountRemark").val("回单折扣："+parseFloat(newValue)+"折");

                }else{
                    changeFlag = true ;
                }

            }
        });

        //折扣金额
        $("#sales-receipt-distaxamount").numberbox({
            precision:2,
            required:true,
            onChange:function(newValue,oldValue){
                if(!all && newValue < 0){
                    $("#sales-receipt-distaxamount").numberbox("setValue",-newValue);
                    newValue = - newValue ;
                }else if(all && newValue < 0){
                    newValue = - newValue ;
                }

                if(newValue!=oldValue && changeFlag){
                    //总金额
                    var amount = $("#sales-receipt-totalamount").numberbox("getValue");
                    var discount = Number(newValue)/Number(amount)*Number(10);
                    changeFlag = false;
                    $("#sales-receipt-discount").numberbox("setValue",10-discount);
                    var count =  $("#sales-receipt-discount").numberbox("getValue");
                    $("#goodsDiscountRemark").val("回单折扣："+parseFloat(count)+"折");

                }else{
                    changeFlag = true;
                }
            }
        });

        $("#sales-form-receiptDetailBrandAddPage").form({
            onSubmit: function(){
                loading("提交中..");
            },
            success:function(data){
                loaded();
                var rows = $.parseJSON(data);
//                    $wareList.datagrid("loadData",rows);
                for(var i=0;i<rows.length;i++){
                    var index = $wareList.datagrid("getRowIndex",rows[i].id);
                    $wareList.datagrid("updateRow",{index:index,row:rows[i]});
                }
                $("#sales-datagrid-receiptAddPage-content").dialog("close");
            }
        });

        $("#sales-save-billDetailReceiptDiscountAddPage").click(function(){
            var flag = $("#sales-form-receiptDetailBrandAddPage").form('validate');
            if(flag==false){
                return false;
            }
            $("#sales-form-receiptDetailBrandAddPage").submit();
        });
    });

    getNumberBox("sales-receipt-discount").bind("keydown", function(event){
        if(event.keyCode==13){
            getNumberBox("sales-receipt-distaxamount").focus();
            getNumberBox("sales-receipt-distaxamount").select();
        }
    });
    getNumberBox("sales-receipt-distaxamount").bind("keydown", function(event){
        if(event.keyCode==13){
            getNumberBox("sales-receipt-distaxamount").blur();
            var discount = $("#sales-receipt-discount").numberbox("getValue");
            if(discount == "" ){
                alert("请填写折扣比率或折扣金额");
                getNumberBox("sales-receipt-discount").focus();
            }else{
                $("#goodsDiscountRemark").focus();
                $("#goodsDiscountRemark").select();
            }
        }
    });
    $("#goodsDiscountRemark").die("keydown").live("keydown", function(event){
        if(event.keyCode==13){
            $("#sales-save-billDetailReceiptDiscountAddPage").focus();
        }
    });

</script>

</body>
</html>
