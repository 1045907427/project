<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>收款单收款凭证页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form id="collectionAccount-form-addacountvourch" action="erpconnect/addCollectionVouch.do" method="post">
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
                    <td id="collectionAccount-td-dcode">
                        <%--<c:if test="${isUserRelationSubject=='0'}">--%>
                            <input type="text" id="collectionAccount-dcode-1" name="debitCode"  style="width: 230px;"/>
                        <%--</c:if>--%>
                        <%--<c:if test="${isUserRelationSubject=='1'}">--%>
                            <%--<input type="text" id="collectionAccount-dcode-1"  style="width: 230px;" value="" readonly="readonly" />--%>
                        <%--</c:if>--%>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;">贷方会计科目：</td>
                    <td id="collectionAccount-td-ccode">
                        <input type="text" id="collectionAccount-ccode-1" name="creditCode" style="width: 230px;"/>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;">操作日期：</td>
                    <td>
                        <input type="text" id="collectionAccount-date" name="date" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" style="width: 230px;" value="${today}"/>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="collectionAccount-ids" name="ids"/>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align:right;">
            <input type="button" value="确 定" name="savenogo" id="collectionAccount-button-addSave" />
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        $("#collectionAccount-button-addSave").click(function(){
            var operatedate = $("#collectionAccount-date").val();
            var todayyear = '${today}'.substring(0,4);
            var operateyear = operatedate.substring(0,4);
            if(todayyear != operateyear){
                $.messager.confirm("提醒","操作日期与当前年份不符，是否仍要生成凭证？",function (r) {
                    if(r){
                        $("#collectionAccount-form-addacountvourch").submit();
                    }
                });
            }else{
                $("#collectionAccount-form-addacountvourch").submit();
            }
        });
        $("#collectionAccount-form-addacountvourch").form({
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
                    $.messager.alert("提醒", "生成成功!" );
                    $("#collection-account-dialog").dialog("close");
                } else {
                    $.messager.alert("提醒", "生成失败!" + json.msg);
                    $("#collection-account-dialog").dialog("close");
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
                    $("#collectionAccount-dcode-1").removeAttr('readonly','readonly');
                }else{
                    $("#collectionAccount-dcode-1").attr('readonly','readonly');
                }

                $("#collectionAccount-dcode-1").widget({
                    referwid:'RT_T_ERP_ACCOUNTCODE',
                    width:230,
                    singleSelect:true,
                    required:isrequire,
                    param:[{field:'dbid',op:'equal',value:dbid},{field:'isleaf',op:'equal',value:'1'}]
                });
                $("#collectionAccount-ccode-1").widget({
                    referwid:'RT_T_ERP_ACCOUNTCODE',
//                param:[{field:'bproperty',op:'like',value:'0'}],
                    width:230,
                    required:true,
                    onlyLeafCheck:true,
                    singleSelect:true,
                    param:[{field:'dbid',op:'equal',value:dbid},{field:'isleaf',op:'equal',value:'1'}]
                });

                var debitcode="",creditcode="";
                debitcode=json.collectionDcode;
                creditcode=json.collectionCcode;

                if(isUserRelationSubject=='1'){
                    debitcode="";
                }
                if(debitcode==undefined){
                    $("#collectionAccount-dcode-1").widget("clear");
                }else{
                    $("#collectionAccount-dcode-1").widget("setValue",debitcode);
                }
                if(creditcode==undefined){
                    $("#collectionAccount-ccode-1").widget("clear");
                }else{
                    $("#collectionAccount-ccode-1").widget("setValue",creditcode);
                }

                if(isUserRelationSubject=='0'){
                    $("#collectionAccount-dcode-1").widget("setValue",debitcode);
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
