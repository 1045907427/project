<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商品科目新增</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:true">
    <div data-options="region:'north',split:false,border:false" style="height: 50px;overflow: hidden;">
        <div id="ledger-queryDiv-thirdBrandSubjectAddPage" style="padding-bottom: 15px;">
            <form id="ledger-form-thirdBrandSubjectAddPage">
                <input type="hidden" name="dbid" value="${param.dbid}"/>
                <input type="hidden" name="pid" value="${param.pid}"/>
                <table>
                    <tr>
                        <td>品牌：</td>
                        <td><input type="text" id="thirdBrandSubjectAddPage-widget-brandid" name="brandid" autocomplete="off"/></td>
                        <td>科目：</td>
                        <td><input type="text" id="thirdBrandSubjectAddPage-widget-subjectid" name="subjectid" autocomplete="off"/></td>
                        <td colspan="2" class="tdbutton">
                            <a href="javascript:void(0);" id="ledger-query-thirdBrandSubjectAddPage" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="ledger-reset-thirdBrandSubjectAddPage" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div data-options="region:'center',border:false" >
        <table id="ledger-datagrid-thirdBrandSubjectAddPage"></table>
    </div>
</div>
<script type="text/javascript">

    $(function () {

        $("#thirdBrandSubjectAddPage-widget-subjectid").widget({
            referwid:'RT_T_ERP_ACCOUNTCODE',
            onlyLeafCheck:true,
            singleSelect:true,
            width:120,
            param : [{field: 'id', op: 'startwith', value: '${param.subjectid}'},{field:'isleaf',op:'equal',value:'1'},
                {field:'dbid',op:'equal',value:'${param.dbid}'}],
            required:true
        })
        

        $("#thirdBrandSubjectAddPage-widget-brandid").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            singleSelect:false,
            width:120
        })
        

        $("#ledger-datagrid-thirdBrandSubjectAddPage").datagrid({
            frozenColumns: [[{field: 'ck', checkbox: true}]],
            columns:  [[
                {field: 'id', title: '品牌编码', sortable: false, width: 110},
                {field: 'name', title: '品牌名称', sortable: false, width: 200}
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
            toolbar: 'ledger-queryDiv-thirdBrandSubjectAddPage',
            url: 'thirdDb/getThirdBrandList.do',
            queryParams: $("#ledger-form-thirdBrandSubjectAddPage").serializeJSON()
        }).datagrid("columnMoving");

        // 查询
        $('#ledger-query-thirdBrandSubjectAddPage').click(function (e) {
            var param = $("#ledger-form-thirdBrandSubjectAddPage").serializeJSON();
            $("#ledger-datagrid-thirdBrandSubjectAddPage").datagrid('load', param);
        });

        // 重置
        $('#ledger-reset-thirdBrandSubjectAddPage').off('click').on('click', function (e) {


            $("#ledger-form-thirdBrandSubjectAddPage")[0].reset();
            $("#thirdBrandSubjectAddPage-widget-subjectid").widget('clear');
            $("#thirdBrandSubjectAddPage-widget-brandid").widget('clear');

            var param = $("#ledger-form-thirdBrandSubjectAddPage").serializeJSON();
            $("#ledger-datagrid-thirdBrandSubjectAddPage").datagrid('load', param);
        });

    });

</script>
</body>
</html>
