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
        <form id="storageOtherIn-form-addacountvourch" action="erpconnect/addStorageOtherEnterVouch.do" method="post">
            <table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
                <tr>
                    <td style="text-align: right;">账套名称：</td>
                    <td>
                        <select id="storageOtherIn-databaseid" onchange="loadSubject()"  style="width: 230px;" name="dbid">
                            <c:forEach items="${dbList }" var="list">
                                <option value="${list.id }">${list.dbasename }</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;">凭证类别：</td>
                    <td>
                        <select id="storageOtherIn-sign"  style="width: 230px;" name="sign">
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
                        <input type="text" id="storageOtherIn-dcode" name="debitCode" style="width: 230px;"/>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;">贷方会计科目：</td>
                    <td>
                        <%--<c:if test="${isUserRelationSubject=='0'}">--%>
                            <input type="text" id="storageOtherIn-ccode" name="creditCode" style="width: 230px;" />
                        <%--</c:if>--%>
                        <%--<c:if test="${isUserRelationSubject=='1'}">--%>
                            <%--<input type="text" id="storageOtherIn-ccode" readonly="readonly" value="" style="width: 230px;" />--%>
                        <%--</c:if>--%>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;">操作日期：</td>
                    <td>
                        <input type="text" id="storageOtherIn-date" name="date" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" style="width: 230px;" value="${today}"/>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="storageOtherIn-ids" name="ids"/>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align:right;">
            <input type="button" value="确 定" name="savenogo" id="storageOtherIn-button-addSave" />
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){

        $("#storageOtherIn-button-addSave").click(function(){
            var operatedate = $("#storageOtherIn-date").val();
            var todayyear = '${today}'.substring(0,4);
            var operateyear = operatedate.substring(0,4);
            if(todayyear != operateyear){
                $.messager.confirm("提醒","操作日期与当前年份不符，是否仍要生成凭证？",function (r) {
                    if(r){
                        $("#storageOtherIn-form-addacountvourch").submit();
                    }
                });
            }else{
                $("#storageOtherIn-form-addacountvourch").submit();
            }
        });
        $("#storageOtherIn-form-addacountvourch").form({
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
                    $("#storageOtherEnter-account-dialog").dialog("close");
                } else {
                    $.messager.alert("提醒", "生成失败!" + json.msg);
                }


            }
        });
        loadSubject();
    });
    function loadSubject(){
        var dbid=$("#storageOtherIn-databaseid").val();
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
                    $("#storageOtherIn-ccode").removeAttr('readonly','readonly');
                }else{
                    $("#storageOtherIn-ccode").attr('readonly','readonly');
                }

                $("#storageOtherIn-dcode").widget({
                    referwid:'RT_T_ERP_ACCOUNTCODE',
                    // param:[{field:'bproperty',op:'like',value:'1'}],
                    required:true,
                    onlyLeafCheck:true,
                    width:230,
                    singleSelect:true,
                    param:[{field:'dbid',op:'equal',value:dbid}]
                });
                $("#storageOtherIn-ccode").widget({
                    referwid:'RT_T_ERP_ACCOUNTCODE',
                    required:isrequire,
                    width:230,
                    onlyLeafCheck:true,
                    singleSelect:true,
                    param:[{field:'dbid',op:'equal',value:dbid},{field:'isleaf',op:'equal',value:'1'}]
                });

                var debitcode="",creditcode="";
                debitcode=json.storageotherinDcode;
                creditcode=json.storageotherinCcode;
                if(isUserRelationSubject=='1'){
                    creditcode="";
                }
                if(debitcode==undefined){
                    $("#storageOtherIn-dcode").widget("clear");
                }else{
                    $("#storageOtherIn-dcode").widget("setValue",debitcode);
                }
                if(creditcode==undefined){
                    $("#storageOtherIn-ccode").widget("clear");
                }else{
                    $("#storageOtherIn-ccode").widget("setValue",creditcode);
                }

                var signList=json.signList;
                $("#storageOtherIn-sign").empty();
                for(var i=0;i<signList.length;i++){
                    var tdstr = '<option value="'+signList[i].id+'">'+signList[i].codesign+'</option>';
                    $(tdstr).appendTo("#storageOtherIn-sign");
                }
            }
        });
    }
</script>
</body>
</html>
