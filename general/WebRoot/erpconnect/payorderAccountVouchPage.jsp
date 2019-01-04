<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>货款支付凭证</title>

</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form id="payorder-form-addacountvourch" action="erpconnect/addPayorderAccountVouch.do" method="post">
            <table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
                <tr>
                    <td style="text-align: right;">账套名称：</td>
                    <td>
                        <select id="sales-customer-databaseid" onchange="loadSubject()"  style="width: 230px;" name="dbid">
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
                </tr>
                <tr>
                    <td style="text-align: right;">借方会计科目：</td>
                    <td>
                        <input type="text" id="payorder-paymentDcode" name="paymentDcode" style="width: 230px;"/>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;">贷方会计科目：</td>
                    <td>
                        <%--<c:if test="${isUserRelationSubject=='0'}">--%>
                            <input type="text" id="payorder-paymentCcode" name="paymentCcode" style="width: 230px;"/>
                        <%--</c:if>--%>
                        <%--<c:if test="${isUserRelationSubject=='1'}">--%>
                            <%--<input type="text" id="payorder-paymentCcode" readonly="readonly" style="width: 230px;" value=""/>--%>
                        <%--</c:if>--%>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;">操作日期：</td>
                    <td>
                        <input type="text" id="payorder-date" name="date" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" style="width: 230px;" value="${today}"/>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="payorder-ids" name="ids"/>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align:right;">
            <input type="button" value="确 定" name="savenogo" id="payorder-button-addSave" />
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        var dbid=$("#sales-customer-databaseid").val();
        $("#payorder-paymentDcode").widget({
            referwid:'RT_T_ERP_ACCOUNTCODE',
           // param:[{field:'bproperty',op:'like',value:'0'}],
            width:230,
            required:true,
            onlyLeafCheck:true,
            singleSelect:true,
            param:[{field:'dbid',op:'equal',value:dbid},{field:'isleaf',op:'equal',value:'1'}]
        });
        $("#payorder-paymentCcode").widget({
            referwid:'RT_T_ERP_ACCOUNTCODE',
            //param:[{field:'bproperty',op:'like',value:'1'}],
            width:230,
            <c:if test="${isUserRelationSubject=='0'}">
            required:true,
            </c:if>
            onlyLeafCheck:true,
            singleSelect:true,
            param:[{field:'dbid',op:'equal',value:dbid},{field:'isleaf',op:'equal',value:'1'}]
        });
        $("#payorder-button-addSave").click(function(){
            var operatedate = $("#payorder-date").val();
            var todayyear = '${today}'.substring(0,4);
            var operateyear = operatedate.substring(0,4);
            if(todayyear != operateyear){
                $.messager.confirm("提醒","操作日期与当前年份不符，是否仍要生成凭证？",function (r) {
                    if(r){
                        $("#payorder-form-addacountvourch").submit();
                    }
                });
            }else{
                $("#payorder-form-addacountvourch").submit();
            }
//            $("#payorder-form-addacountvourch").submit();
        });
        $("#payorder-form-addacountvourch").form({
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
                    $("#Payorder-account-dialog-content").dialog("close");
                } else {
                    $.messager.alert("提醒", "生成失败!" + json.msg);
                }


            }
        });
        loadSubject();
    });

    function loadSubject(){
        var dbid=$("#sales-customer-databaseid").val();
        $.ajax({
            url :"thirdDb/getThirdDbParam.do",
            data:{
                dbid:dbid
            },
            type:'post',
            dataType:'json',
            async: false,
            success:function(json){
                var isUserRelationSubject=json.isUserRelationSubject;
                var isrequire=false;
                if(isUserRelationSubject==0 || isUserRelationSubject==undefined){
                    isrequire=true;
                    $("#payorder-paymentCcode").removeAttr('readonly');
                }else{
                    $("#payorder-paymentCcode").attr('readonly','readonly');
                }

                $("#payorder-paymentDcode").widget({
                    referwid:'RT_T_ERP_ACCOUNTCODE',
                    width:230,
                    singleSelect:true,
                    required:true,
                    param:[{field:'dbid',op:'equal',value:dbid},{field:'isleaf',op:'equal',value:'1'}]
                });
                $("#payorder-paymentCcode").widget({
                    referwid:'RT_T_ERP_ACCOUNTCODE',
//                param:[{field:'bproperty',op:'like',value:'0'}],
                    width:230,
                    required:isrequire,
                    onlyLeafCheck:true,
                    singleSelect:true,
                    param:[{field:'dbid',op:'equal',value:dbid},{field:'isleaf',op:'equal',value:'1'}]
                });



                var debitcode="",creditcode="";
                debitcode=json.paymentDcode;
                creditcode=json.paymentCcode;
                if(isUserRelationSubject=='1'){
                    creditcode="";
                }
                if(debitcode==undefined){
                    $("#payorder-paymentDcode").widget("clear");
                }else{
                    $("#payorder-paymentDcode").widget("setValue",debitcode);
                }
                if(creditcode==undefined){
                    $("#payorder-paymentCcode").widget("clear");
                }else{
                    $("#payorder-paymentCcode").widget("setValue",creditcode);
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
