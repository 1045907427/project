<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title></title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form id="adjustmentsVouchPage-form-addacountvourch" action="erpconnect/addAdjustmentsVouch.do" method="post">
            <table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
                <tr>
                    <td style="text-align: right;">账套名称：</td>
                    <td>
                        <select id="adjustmentsVouchPage-databaseid" onchange="loadSubject()"  style="width: 230px;" name="dbid">
                            <c:forEach items="${dbList }" var="list">
                                <option value="${list.id }">${list.dbasename }</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;">凭证类别：</td>
                    <td>
                        <select id="adjustmentsVouchPage-sign"  style="width: 230px;" name="sign">
                            <%--<c:forEach items="${signList }" var="list">--%>
                                <%--<option value="${list.id }">${list.codesign }</option>--%>
                            <%--</c:forEach>--%>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;">是否含税：</td>
                    <td>
                        <select name="isTax" style="width: 230px;">
                            <option value="0" selected>否</option>
                            <option value="1">是</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;">借方会计科目：</td>
                    <td>
                        <input type="text" id="adjustmentsVouchPage-dcode" name="debitCode" style="width: 230px;"/>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;">贷方会计科目：</td>
                    <td>
                        <%--<c:if test="${isUserRelationSubject=='0'}">--%>
                        <input type="text" id="adjustmentsVouchPage-ccode" name="creditCode" style="width: 230px;" />
                        <%--</c:if>--%>
                        <%--<c:if test="${isUserRelationSubject=='1'}">--%>
                        <%--<input type="text" id="adjustmentsVouchPage-ccode" readonly="readonly" value="" style="width: 230px;" />--%>
                        <%--</c:if>--%>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;">操作日期：</td>
                    <td>
                        <input type="text" id="adjustmentsVouchPage-date" name="date" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" style="width: 230px;" value="${today}"/>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="adjustments-ids" name="ids"/>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align:right;">
            <input type="button" value="确 定" name="savenogo" id="adjustmentsVouchPage-button-addSave" />
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){

        $("#adjustmentsVouchPage-button-addSave").click(function(){
            var operatedate = $("#adjustmentsVouchPage-date").val();
            var todayyear = '${today}'.substring(0,4);
            var operateyear = operatedate.substring(0,4);
            if(todayyear != operateyear){
                $.messager.confirm("提醒","操作日期与当前年份不符，是否仍要生成凭证？",function (r) {
                    if(r){
                        $("#adjustmentsVouchPage-form-addacountvourch").submit();
                    }
                });
            }else{
                $("#adjustmentsVouchPage-form-addacountvourch").submit();
            }
        });
        $("#adjustmentsVouchPage-form-addacountvourch").form({
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
                    $("#adjustments-account-dialog").dialog("close");
                } else {
                    $.messager.alert("提醒", "生成失败!" + json.msg);
                }


            }
        });
        loadSubject();
    });
    function loadSubject(){
        var dbid=$("#adjustmentsVouchPage-databaseid").val();
        $.ajax({
            url :"thirdDb/getThirdDbParam.do",
            data:{
                dbid:dbid
            },
            type:'post',
            dataType:'json',
            async: false,
            success:function(json){

                $("#adjustmentsVouchPage-dcode").widget({
                    referwid:'RT_T_ERP_ACCOUNTCODE',
                    required:true,
                    onlyLeafCheck:true,
                    width:230,
                    singleSelect:true,
                    param:[{field:'dbid',op:'equal',value:dbid}]
                });
                $("#adjustmentsVouchPage-ccode").widget({
                    referwid:'RT_T_ERP_ACCOUNTCODE',
                    required:true,
                    width:230,
                    onlyLeafCheck:true,
                    singleSelect:true,
                    param:[{field:'dbid',op:'equal',value:dbid},{field:'isleaf',op:'equal',value:'1'}]
                });

                var debitcode="",creditcode="";
                debitcode=json.adjustmentsDcode;
                creditcode=json.adjustmentsCcode;
                if(debitcode==undefined){
                    $("#adjustmentsVouchPage-dcode").widget("clear");
                }else{
                    $("#adjustmentsVouchPage-dcode").widget("setValue",debitcode);
                }
                if(creditcode==undefined){
                    $("#adjustmentsVouchPage-ccode").widget("clear");
                }else{
                    $("#adjustmentsVouchPage-ccode").widget("setValue",creditcode);
                }

                var signList=json.signList;
                $("#adjustmentsVouchPage-sign").empty();
                for(var i=0;i<signList.length;i++){
                    var tdstr = '<option value="'+signList[i].id+'">'+signList[i].codesign+'</option>';
                    $(tdstr).appendTo("#adjustmentsVouchPage-sign");
                }
            }
        });
    }
</script>
</body>
</html>
