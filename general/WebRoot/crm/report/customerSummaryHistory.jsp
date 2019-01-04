<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head></head>
<body>
<table id="crm-table-customerSummaryHistory"></table>
<script type="text/javascript">
    var historyListJson = $("#crm-table-customerSummaryHistory").createGridColumnLoad({
        commonCol: [[
            {field:'businessdate', title:'业务日期',width:90,align:'left'},
            {field:'unitnum', title:'数量',width:60,align:'right',
                formatter:function(value,row,index){
                    return formatterBigNum(value);
                }
            },
            {field:'totalbox', title:'箱数',width:60,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value,2);
                }
            },
            {field:'unitname', title:'单位',width:60,align:'right'},
            {field:'auxnumdetail', title:'辅数量',width:60,align:'right'},
            {field:'addtime', title:'添加时间',width:90,align:'right'}
        ]]
    });

    $(function(){
        $("#crm-table-customerSummaryHistory").datagrid({ //销售商品明细信息编辑
            authority:historyListJson,
            columns: historyListJson.common,
            border: true,
            fit: true,
            rownumbers: true,
            striped:true,
            singleSelect: true,
            data: JSON.parse('${listjson}')
        }).datagrid('columnMoving');

    });


</script>
</body>
</html>
