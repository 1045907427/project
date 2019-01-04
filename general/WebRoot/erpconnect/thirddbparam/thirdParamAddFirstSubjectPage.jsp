<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>一级科目新增</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:true">
    <div data-options="region:'north',split:false,border:false" style="height: 70px;overflow: hidden;">
        <div id="ledger-queryDiv-thirdParamAddFirstSubjectPage" style="padding-bottom: 15px;">
            <form id="ledger-form-thirdParamAddFirstSubjectPage">
                <input type="hidden" name="dbid" value="${param.dbid}"/>
                <input type="hidden" name="basetype" value="${param.basetype}"/>
                <table>
                    <tr>
                        <td>科目编码：</td>
                        <td><input type="text" name="id" class="len130" autocomplete="off"/></td>
                        <td>科目名称：</td>
                        <td><input type="text" name="name" class="len150" autocomplete="off"/></td>
                    </tr>
                    <tr>
                        <td colspan="2" class="tdbutton">
                            <a href="javascript:void(0);" id="ledger-query-thirdParamAddFirstSubjectPage" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="ledger-reset-thirdParamAddFirstSubjectPage" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div data-options="region:'center',border:false" >
        <table id="ledger-datagrid-thirdParamAddFirstSubjectPage"></table>
    </div>
</div>
<script type="text/javascript">

    $(function () {

        $("#ledger-datagrid-thirdParamAddFirstSubjectPage").datagrid({
            frozenColumns: [[{field: 'ck', checkbox: true}]],
            columns:  [[
                {field: 'id', title: '科目编码', sortable: false, width: 110},
                {field: 'name', title: '科目名称', sortable: false, width: 200}
            ]],
            fit: true,
            border: false,
            rownumbers: true,
            pagination: true,
            pageSize: 100,
            idField: 'id',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            toolbar: 'ledger-queryDiv-thirdParamAddFirstSubjectPage',
            url: 'thirdDb/getFirstBaseSubjectData.do',
            queryParams: $("#ledger-form-thirdParamAddFirstSubjectPage").serializeJSON()
        }).datagrid("columnMoving");

        // 查询
        $('#ledger-query-thirdParamAddFirstSubjectPage').click(function (e) {
            var param = $("#ledger-form-thirdParamAddFirstSubjectPage").serializeJSON();
            $("#ledger-datagrid-thirdParamAddFirstSubjectPage").datagrid('load', param);
        });

        // 重置
        $('#ledger-reset-thirdParamAddFirstSubjectPage').off('click').on('click', function (e) {

            $("#ledger-form-thirdParamAddFirstSubjectPage")[0].reset();

            var param = $("#ledger-form-thirdParamAddFirstSubjectPage").serializeJSON();
            $("#ledger-datagrid-thirdParamAddFirstSubjectPage").datagrid('load', param);
        });

    });

</script>
</body>
</html>
