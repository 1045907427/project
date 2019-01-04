<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>采购发票凭证</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form id="purchasePushAccount-form-addacountvourch" action="erpconnect/addPurchasePushAccountVouch.do" method="post">
            <table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
                <tr>
                    <td style="text-align: right;">账套名称：</td>
                    <td>
                        <select id="sales-customer-databaseid" onchange="loadSubject()" style="width: 230px;" name="dbid">
                            <c:forEach items="${dbList }" var="list">
                                <option value="${list.id }">${list.dbasename }</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;">凭证类别：</td>
                    <td>
                        <select id="sales-customer-sign"  style="width: 230px;" name="sign">
                            <%--<c:forEach items="${signList }" var="list">--%>
                                <%--<option value="${list.id }">${list.codesign }</option>--%>
                            <%--</c:forEach>--%>
                        </select>
                    </td>
                    <input type="hidden" name="versionParam" value="${versionParam}" />
                </tr>
                <%--<c:if test="${versionParam == 0}">--%>
                    <tr class="versionParam0">
                        <td style="text-align: right;">借方会计科目：</td>
                        <td>
                            <input type="text" id="purchasePushAccount-paymentPushDcode" name="paymentPushDcode" style="width: 230px;"/>
                        </td>
                    </tr>
                    <tr class="versionParam0">
                        <td style="text-align: right;">贷方会计科目：</td>
                        <td>
                            <input type="text" id="purchasePushAccount-paymentTotalCcode" name="paymentTotalCcode" style="width: 230px;" />
                        </td>
                    </tr>
                    <tr class="versionParam0">
                        <td></td>
                        <td><input type="text" id="purchasePushAccount-paymentPushCcode" name="paymentPushCcode" style="width: 230px;"/></td>
                    </tr>
                <%--</c:if>--%>
                <%--<c:if test="${versionParam == 1}">--%>
                    <tr class="versionParam1">
                        <td style="text-align: right;">借方会计科目：</td>
                        <td>
                            <input type="text" id="purchasePushAccount-pushNotaxamountDcode" name="pushNotaxamountDcode" style="width: 230px;"/>
                        </td>
                    </tr>
                    <tr class="versionParam1">
                        <td></td>
                        <td><input type="text" id="purchasePushAccount-pushAdjustDcode" name="pushAdjustDcode" style="width: 230px;"/></td>
                    </tr>
                    <tr class="versionParam1">
                        <td></td>
                        <td><input type="text" id="purchasePushAccount-pushTaxDcode" name="pushTaxDcode" style="width: 230px;"/></td>
                    </tr>
                    <tr class="versionParam1">
                        <td style="text-align: right;">贷方会计科目：</td>
                        <td>
                            <input type="text" id="purchasePushAccount-pushTaxamountCcode" name="pushTaxamountCcode" style="width: 230px;"/>
                        </td>
                    </tr>
                <%--</c:if>--%>

                <tr>
                    <td style="text-align: right;">操作日期：</td>
                    <td>
                        <input type="text" id="purchasePushAccount-date" name="date" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" style="width: 230px;" value="${today}"/>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="purchasePushAccount-ids" name="ids"/>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align:right;">
            <input type="button" value="确 定" name="savenogo" id="purchasePushAccount-button-addSave" />
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        $("#purchasePushAccount-button-addSave").click(function(){
            var operatedate = $("#purchasePushAccount-date").val();
            var todayyear = '${today}'.substring(0,4);
            var operateyear = operatedate.substring(0,4);
            if(todayyear != operateyear){
                $.messager.confirm("提醒","操作日期与当前年份不符，是否仍要生成凭证？",function (r) {
                    if(r){
                        $("#purchasePushAccount-form-addacountvourch").submit();
                    }
                });
            }else{
                $("#purchasePushAccount-form-addacountvourch").submit();
            }
        });
        $("#purchasePushAccount-form-addacountvourch").form({
            onSubmit: function(){
                var flag = $(this).form('validate');
                if(flag==false){
                    return false;
                }
                loading("提交中..");
            },
            success:function(data){
                //表单提交完成后 隐藏提交等待页面
                loaded();
                var json = $.parseJSON(data);
                if (json.flag) {
                    $.messager.alert("提醒", "生成成功!");
                    $("#purchaseInvoicePush-account-dialog").dialog("close");
                } else {
                    $.messager.alert("提醒", "生成失败!" + json.msg);
                    $("#purchaseInvoicePush-account-dialog").dialog("close");
                }


            }
        });
        loadSubject();
    });
    function loadSubject(){
        var dbid=$("#sales-customer-databaseid").val();
        $("#purchasePushAccount-pushNotaxamountDcode").widget({
            referwid:'RT_T_ERP_ACCOUNTCODE',
            // param:[{field:'bproperty',op:'like',value:'1'}],
            width:230,
//            required:true,
            onlyLeafCheck:true,
            singleSelect:true,
            param:[{field:'dbid',op:'equal',value:dbid}]
        });
        $("#purchasePushAccount-pushAdjustDcode").widget({
            referwid:'RT_T_ERP_ACCOUNTCODE',
            //param:[{field:'bproperty',op:'like',value:'1'}],
            width:230,
//            required:true,
            onlyLeafCheck:true,
            singleSelect:true,
            param:[{field:'dbid',op:'equal',value:dbid}]
        });
        $("#purchasePushAccount-pushTaxDcode").widget({
            referwid:'RT_T_ERP_ACCOUNTCODE',
            //param:[{field:'bproperty',op:'like',value:'0'}],
            width:230,
//            required:true,
            onlyLeafCheck:true,
            singleSelect:true,
            param:[{field:'dbid',op:'equal',value:dbid}]
        });
        $("#purchasePushAccount-pushTaxamountCcode").widget({
            referwid:'RT_T_ERP_ACCOUNTCODE',
            //param:[{field:'bproperty',op:'like',value:'0'}],
            width:230,
//            required:true,
            onlyLeafCheck:true,
            singleSelect:true,
            param:[{field:'dbid',op:'equal',value:dbid}]
        });
        $("#purchasePushAccount-paymentPushDcode").widget({
            referwid:'RT_T_ERP_ACCOUNTCODE',
            width:230,
//            required:true,
            onlyLeafCheck:true,
            singleSelect:true,
            param:[{field:'dbid',op:'equal',value:dbid}]
        });
        $("#purchasePushAccount-paymentTotalCcode").widget({
            referwid:'RT_T_ERP_ACCOUNTCODE',
//            required:true,
            onlyLeafCheck:true,
            singleSelect:true,
            width:230,
            param:[{field:'dbid',op:'equal',value:dbid}]
        });
        $("#purchasePushAccount-paymentPushCcode").widget({
            referwid:'RT_T_ERP_ACCOUNTCODE',
//            required:true,
            onlyLeafCheck:true,
            singleSelect:true,
            width:230,
            param:[{field:'dbid',op:'equal',value:dbid}]
        });
        $.ajax({
            url :"thirdDb/getThirdDbParam.do",
            data:{
                dbid:dbid
            },
            type:'post',
            dataType:'json',
            async: false,
            success:function(json){
                var versionParam=json.versionParam;
                if(versionParam=='0'){
                    $(".versionParam0").show();
                    $(".versionParam1").hide();
                    var debitcode="",creditcode1="",creditcode2="";
                    debitcode=json.paymentPushDcode;
                    creditcode1=json.paymentTotalCcode;
                    creditcode2=json.paymentPushCcode;

                    if(debitcode==undefined){
                        $("#purchasePushAccount-paymentPushDcode").widget("clear");
                    }else{
                        $("#purchasePushAccount-paymentPushDcode").widget("setValue",debitcode);
                    }

                    if(creditcode1==undefined){
                        $("#purchasePushAccount-paymentTotalCcode").widget("clear");
                    }else{
                        $("#purchasePushAccount-paymentTotalCcode").widget("setValue",creditcode1);
                    }

                    if(creditcode2==undefined){
                        $("#purchasePushAccount-paymentPushCcode").widget("clear");
                    }else{
                        $("#purchasePushAccount-paymentPushCcode").widget("setValue",creditcode2);
                    }
                }else if(versionParam=='1'){
                    $(".versionParam0").hide();
                    $(".versionParam1").show();
                    var debitcode1=json.pushNotaxamountDcode;
                    var debitcode2=json.pushAdjustDcode;
                    var debitcode3=json.pushTaxDcode;
                    var creditcode=json.pushTaxamountCcode;

                    if(debitcode1==undefined){
                        $("#purchasePushAccount-pushNotaxamountDcode").widget("clear");
                    }else{
                        $("#purchasePushAccount-pushNotaxamountDcode").widget("setValue",debitcode1);
                    }

                    if(debitcode2==undefined){
                        $("#purchasePushAccount-pushAdjustDcode").widget("clear");
                    }else{
                        $("#purchasePushAccount-pushAdjustDcode").widget("setValue",debitcode2);
                    }

                    if(debitcode3==undefined){
                        $("#purchasePushAccount-pushTaxDcode").widget("clear");
                    }else{
                        $("#purchasePushAccount-pushTaxDcode").widget("setValue",debitcode3);
                    }

                    if(creditcode==undefined){
                        $("#purchasePushAccount-pushTaxamountCcode").widget("clear");
                    }else{
                        $("#purchasePushAccount-pushTaxamountCcode").widget("setValue",creditcode);
                    }
                }

                var signList=json.signList;
                $("#sales-customer-sign").empty();
                for(var i=0;i<signList.length;i++){
                    var tdstr = '<option value="'+signList[i].id+'">'+signList[i].codesign+'</option>';
                    $(tdstr).appendTo("#sales-customer-sign");
                }
            }
        });
    }
</script>
</body>
</html>
