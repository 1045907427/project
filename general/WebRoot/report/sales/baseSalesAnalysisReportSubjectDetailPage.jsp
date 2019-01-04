<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/27
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
    <table id="sales-datagrid-orderAddPage"></table>
    </div>
</div>

<script type="text/javascript">
    var tableColJson = $("#sales-datagrid-orderAddPage").createGridColumnLoad({
        frozenCol: [[]],
        commonCol: [[
            {field: 'subjectname', title: '费用科目', width: 70, align: ' left', sortable: true},
            {field: 'amount', title: '金额', width: 70, align: ' left', sortable: true},
        ]]
    });
    $(function(){
        $("#sales-datagrid-orderAddPage").datagrid({ //销售商品明细信息编辑
            authority:tableColJson,
            columns: tableColJson.common,
            frozenColumns: tableColJson.frozen,
            border: true,
            fit: true,
            rownumbers: true,
            showFooter: true,
            striped:true,
            singleSelect: false,
            checkOnSelect:true,
            selectOnCheck:true,
            toolbar:'#sales-toolbar-orderAddPage',
            data: JSON.parse('${jsonStr}'),
        }).datagrid('columnMoving');
    })
</script>
</body>
</html>
