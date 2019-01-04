<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title></title>
</head>
<body>
<div id="ledger-layout-detail" class="easyui-layout" data-options="fit:true,border:false">
    <form id="ledger-form-thirdDbAddPage" method="post" action="erpconnect/addErpConnection.do">
        <div id="ledger-layout-detail-north" data-options="region:'north',border:false" style="height:500px">
            <table cellpadding="5px" cellspacing="5px" style="padding-top: 5px;padding-left: 5px;">
                <tr>
                    <td>接入系统:</td>
                    <td>
                        <select name="erpDb.relatesystem" style="width: 150px;" id="ledger-relatesystem-thirdDbParamAddPage" >
                            <option VALUE="T3">用友T3</option>
                            <option VALUE="T6">用友T6</option>
                            <option VALUE="U8">用友U8</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>接口方式:</td>
                    <td>
                        <select name="erpDb.interfacetype" style="width: 150px" onchange="changeConnectType()" id="ledger-interfacetype-thirdDbParamAddPage">
                            <option VALUE="1" selected>openAPI</option>
                            <option VALUE="0" selected>数据库直连</option>

                        </select>
                    </td>
                </tr>
                <tr class="dbconnect">
                    <td>数据库IP:</td>
                    <td>
                        <input type="text" class="easyui-validatebox len150"  name="erpDb.dbIP"/>
                    </td>
                </tr>
                <tr>
                    <td>数据库账套名:</td>
                    <td>
                        <input type="text" class="easyui-validatebox len150" required="required" class="len150"  name="erpDb.dbasename"/>
                        <input type="hidden"  name="erpDb.dbversion" value="SQLSERVER2000" />
                    </td>
                </tr>
                <tr class="dbconnect">
                    <td>数据库名:</td>
                    <td>
                        <input type="text" class="len150" class="len150"  name="erpDb.dbname"/>
                    </td>
                </tr>
                <tr class="dbconnect">
                    <td>用户名:</td>
                    <td>
                        <input type="text" class="len150"  name="erpDb.dbusername"/>
                    </td>
                </tr>
                <tr class="dbconnect">
                    <td>密码:</td>
                    <td>
                        <input type="password" class="len150"  name="erpDb.dbpassword"/>
                    </td>
                </tr>
                <tr class="dbconnect">
                    <td>默认数据库:</td>
                    <td>
                        <select name="erpDb.isdefault" style="width: 150px;" >
                            <option VALUE="0">是</option>
                            <option VALUE="1">否</option>
                        </select>
                    </td>
                </tr>
                <tr class="dbconnect">
                    <td>对应部门:</td>
                    <td>
                        <select id="erp-deptid-connectionAddPage" class="len150" name="erpDb.deptid">
                            <option value=""></option>
                            <c:forEach items="${deptList}" var="list">
                                <c:choose>
                                    <c:when test="${list.id == plannedOrder.buydeptid}">
                                        <option value="${list.id }" selected="selected">${list.name }</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${list.id }">${list.name }</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr class="openapiconnect">
                    <td>用友调用方:</td>
                    <td>
                        <input type="from_account" class="len150"  name="erpDb.from_account"/>
                    </td>
                </tr>
                <tr class="openapiconnect">
                    <td>用友提供方:</td>
                    <td>
                        <input type="to_account" class="len150"  name="erpDb.to_account"/>
                    </td>
                </tr>
                <tr class="openapiconnect">
                    <td>用友应用key	:</td>
                    <td>
                        <input type="app_key" class="len150"  name="erpDb.app_key"/>
                    </td>
                </tr>
                <tr class="openapiconnect">
                    <td>用友应用密码:</td>
                    <td>
                        <input type="app_secret" class="len150"  name="erpDb.app_secret"/>
                    </td>
                </tr>
                <tr class="openapiconnect">
                    <td>数据源序号:</td>
                    <td>
                        <input type="dbsourceseq" class="len150"  name="erpDb.dbsourceseq"/>
                    </td>
                </tr>
            </table>
        </div>

    </form>
</div>
<div id="ledger-dialog-AccountSetAddPage"></div>
<script type="text/javascript">
//    //表单数据验证
//    $.extend($.fn.validatebox.defaults.rules, {
//        validName:{
//            validator: function(value){
//                var flag = true;
//                $.ajax({
//                    url: 'ledger/isUsedLedgerName.do?name=' + value,
//                    type: 'post',
//                    dataType: 'json',
//                    async: false,
//                    success: function (json) {
//                        flag = json.flag;
//                    }
//                });
//                return flag;
//            },
//            message:'账套名重复，请重新输入!'
//        }
//    });

    $(function () {
        $("#ledger-form-thirdDbAddPage").form({
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
                if(json.flag){
                    $.messager.alert("提醒","新增成功");
                }else{
                    $.messager.alert("提醒","新增失败");
                }
                $("#ledger-VoucherSet-ledgerCommonSetPage").datagrid("reload");
                $("#ledger-addAccountSet-dialog").dialog("close");
            }
        });

        changeConnectType();

        //切换对接系统
        $("#ledger-relatesystem-thirdDbParamAddPage").change(function () {
            var val = $(this).val();
            if("U8" == val){
                $("#ledger-interfacetype-thirdDbParamAddPage").val("1");
            }else{
                $("#ledger-interfacetype-thirdDbParamAddPage").val("0");
            }
            changeConnectType();
        });


    });

    function changeConnectType(){
        var type=$("#ledger-interfacetype-thirdDbParamAddPage").val();
        if('0'==type){
            $(".dbconnect").show();
            $(".openapiconnect").hide();
        }else if("1"==type){
            $("#ledger-relatesystem-thirdDbParamAddPage").val("U8");
            $(".dbconnect").hide();
            $(".openapiconnect").show();
        }
    }

</script>
</body>
</html>
