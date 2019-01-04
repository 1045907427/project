<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>进货折扣页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form action="purchase/arrivalorder/addArrivalOrderDiscount.do" method="post" id="purchase-form-receiptDetailBrandAddPage">
            <table  border="0" >
                <tr>
                    <td>总金额:</td>
                    <td>
                        <input type="text" id="purchase-arrivalorder-totalamount" style="width: 180px;" name="totalamount" readonly="readonly" class="easyui-numberbox no_input" data-options="precision:2" />
                        <input type="hidden" name="arrivalorderid" value="${arrivalorderid}"/>
                    </td>
                </tr>
                <tr>
                    <td>摊分方式:</td>
                    <td style="text-align: left;">
                        <select id="purchase-arrivalorder-repartitionType" style="width: 180px;" name="repartitiontype">
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
                        <input type="text" id="purchase-arrivalorder-discount" name="discount" style="width: 180px;"/>折
                    </td>
                </tr>
                <tr>
                    <td>折扣金额:</td>
                    <td>
                        <input type="text" id="purchase-arrivalorder-distaxamount" name="taxamount" style="width: 180px;"/>
                    </td>
                </tr>
                <tr>
                    <td>备注:</td>
                    <td style="text-align: left;">
                        <input type="text" name="remark" id="goodsDiscountRemark" style="width: 180px;"  value="进货折扣"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align:center;">
            <input type="button" value="确定" id="purchase-save-billDetailReceiptDiscountAddPage" />
        </div>
    </div>
</div>
<script type="text/javascript">
    var changeFlag = true;
    var $wareList = $("#purchase-arrivalOrderAddPage-arrivalOrdertable");
    var detailrows =  $wareList.datagrid('getRows');
    var brandAmount = "";
    for(var i=0; i<detailrows.length; i++){
        var rowJson = detailrows[i];
        if(!isObjectEmpty(rowJson) && rowJson.goodsInfo != undefined){
            if(brandAmount == ""){
                brandAmount = rowJson.taxamount;
            }else{
                brandAmount = Number(rowJson.taxamount)+Number(brandAmount);
            }
        }
    }
    $("#purchase-arrivalorder-totalamount").val(brandAmount);

    $(function(){
        //折扣比率
        $("#purchase-arrivalorder-discount").numberbox({
            precision:2,
            required:true,
            min:0,
            max:10,
            onChange:function(newValue,oldValue){
                if(newValue!=oldValue && changeFlag){
                    //总金额
                    var amount = $("#purchase-arrivalorder-totalamount").numberbox("getValue");
                    var proAmount = Number(amount)*Number(newValue)/Number(10);
                    var distaxamount = amount - proAmount ;
                    changeFlag = false;
                    $("#purchase-arrivalorder-distaxamount").numberbox("setValue",-distaxamount);

                    $("#goodsDiscountRemark").val("进货折扣："+parseFloat(newValue)+"折");

                }else{
                    changeFlag = true ;
                }

            }
        });

        //折扣金额
        $("#purchase-arrivalorder-distaxamount").numberbox({
            precision:2,
            required:true,
            onChange:function(newValue,oldValue){
                if(newValue!=oldValue && changeFlag){
                    //总金额
                    var amount = $("#purchase-arrivalorder-totalamount").numberbox("getValue");
                    var discount = Number(newValue)/Number(amount)*Number(10);
                    changeFlag = false;
                    $("#purchase-arrivalorder-discount").numberbox("setValue",10-discount);
                    var count =  $("#purchase-arrivalorder-discount").numberbox("getValue");
                    $("#goodsDiscountRemark").val("进货折扣："+parseFloat(count)+"折");

                }else{
                    changeFlag = true;
                }

            }
        });

        $("#purchase-form-receiptDetailBrandAddPage").form({
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
                $("#purchase-arrivalOrderAddPage-dialog-DetailOper-content").dialog("close");
            }
        });

        $("#purchase-save-billDetailReceiptDiscountAddPage").click(function(){
            var flag = $("#purchase-form-receiptDetailBrandAddPage").form('validate');
            if(flag==false){
                return false;
            }
            var discountRemark =  $("#goodsDiscountRemark").val();
            var billRemark = $("#purchase-arrivalOrderAddPage-remark").val();
            if(billRemark != undefined){
                billRemark = billRemark + discountRemark ;
            }else{
                billRemark = discountRemark ;
            }
            $("#purchase-arrivalOrderAddPage-remark").val(billRemark);
            $("#purchase-form-receiptDetailBrandAddPage").submit();
        });
    });

    getNumberBox("purchase-arrivalorder-discount").bind("keydown", function(event){
        if(event.keyCode==13){
            getNumberBox("purchase-arrivalorder-distaxamount").focus();
            getNumberBox("purchase-arrivalorder-distaxamount").select();
        }
    });
    getNumberBox("purchase-arrivalorder-distaxamount").bind("keydown", function(event){
        if(event.keyCode==13){
            getNumberBox("purchase-arrivalorder-distaxamount").blur();
            var discount = $("#purchase-arrivalorder-discount").numberbox("getValue");
            if(discount == "" ){
                alert("请填写折扣比率或折扣金额");
                getNumberBox("purchase-arrivalorder-discount").focus();
            }else{
                $("#goodsDiscountRemark").focus();
                $("#goodsDiscountRemark").select();
            }
        }
    });
    $("#goodsDiscountRemark").die("keydown").live("keydown", function(event){
        if(event.keyCode==13){
            $("#purchase-save-billDetailReceiptDiscountAddPage").focus();
        }
    });

</script>

</body>
</html>
