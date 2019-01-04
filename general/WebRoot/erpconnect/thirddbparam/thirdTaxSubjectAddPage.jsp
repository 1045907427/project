<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>税种科目新增</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:true">
    <div data-options="region:'north',split:false,border:false" style="height: 50px;overflow: hidden;">
        <div id="ledger-queryDiv-thirdGoodsSubjectAddPage" style="padding-bottom: 15px;">
            <form id="ledger-form-thirdGoodsSubjectAddPage">
                <input type="hidden" name="dbid" value="${param.dbid}"/>
                <input type="hidden" name="pid" value="${param.pid}"/>
                <table>
                    <tr>
                        <td>税种：</td>
                        <td><input type="text" id="thirdGoodsSubjectAddPage-widget-taxtype" name="id" autocomplete="off"/></td>
                        <td>科目：</td>
                        <td><input type="text" id="thirdGoodsSubjectAddPage-widget-subjectid" name="subjectid" autocomplete="off"/></td>
                        <td colspan="2" class="tdbutton">
                            <a href="javascript:void(0);" id="ledger-query-thirdGoodsSubjectAddPage" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="ledger-reset-thirdGoodsSubjectAddPage" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div data-options="region:'center',border:false" >
        <table id="ledger-datagrid-thirdGoodsSubjectAddPage"></table>
    </div>
</div>
<script type="text/javascript">

    $(function () {

        $("#thirdGoodsSubjectAddPage-widget-subjectid").widget({
            referwid:'RT_T_ERP_ACCOUNTCODE',
            onlyLeafCheck:true,
            singleSelect:true,
            width:120,
            param : [{field: 'id', op: 'startwith', value: '${param.subjectid}'},{field:'isleaf',op:'equal',value:'1'},
                {field:'dbid',op:'equal',value:'${param.dbid}'}],
            required:true
        })

        $("#thirdGoodsSubjectAddPage-widget-taxtype").widget({
            referwid:'RL_T_BASE_FINANCE_TAXTYPE',
            singleSelect:false,
            width:120
        })

        $("#ledger-datagrid-thirdGoodsSubjectAddPage").datagrid({
            frozenColumns: [[{field: 'ck', checkbox: true}]],
            columns:  [[
                {field: 'id', title: '税种编码', sortable: false, width: 110},
                {field: 'name', title: '税种名称', sortable: false, width: 200}
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
            toolbar: 'ledger-queryDiv-thirdGoodsSubjectAddPage',
            url: 'basefiles/finance/getTaxTypeList.do',
            queryParams: $("#ledger-form-thirdGoodsSubjectAddPage").serializeJSON()
        }).datagrid("columnMoving");

        // 查询
        $('#ledger-query-thirdGoodsSubjectAddPage').click(function (e) {
            var param = $("#ledger-form-thirdGoodsSubjectAddPage").serializeJSON();
            $("#ledger-datagrid-thirdGoodsSubjectAddPage").datagrid('load', param);
        });

        // 重置
        $('#ledger-reset-thirdGoodsSubjectAddPage').off('click').on('click', function (e) {


            $("#ledger-form-thirdGoodsSubjectAddPage")[0].reset();
            $("#thirdGoodsSubjectAddPage-widget-subjectid").widget('clear');
            $("#thirdGoodsSubjectAddPage-widget-taxtype").widget('clear');

            var param = $("#ledger-form-thirdGoodsSubjectAddPage").serializeJSON();
            $("#ledger-datagrid-thirdGoodsSubjectAddPage").datagrid('load', param);
        });

    });

</script>
</body>
</html>
