<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>数据源参数选择</title>
</head>
<body>
<div id="ledger-table-dbsourceSeqPage"></div>
<script type="text/javascript">
    $("#ledger-table-dbsourceSeqPage").datagrid({
        columns:[[
            {field: 'sequence', title: '数据源序号', width: 70, align: ' left', sortable: true},
            {field: 'db_type', title: '数据库类型', width: 70, align: ' left', sortable: true},
            {field: 'db_scheme', title: '数据库账套', width: 70, align: ' left', sortable: true},
            {field: 'sys_host', title: '用友地址', width: 70, align: ' left', sortable: true}
        ]],
        fit: true,
        method: 'post',
        singleSelect:false,
        checkOnSelect:true,
        selectOnCheck:true,
        data: JSON.parse('${dbJson}'),
        onSelect: function(index,row){
            $(this).datagrid('beginEdit', index);
            var ed = $(this).datagrid('getEditor', {index:index,field:field});
            $(ed.target).focus();
        }

    });


</script>
</body>
</html>
