<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>普通采购费用分摊表</title>
</head>
<body>

<table id="purchase-datagrid-arrivalOrderShareLogPage"></table>


<script type="text/javascript">

    $(function(){

        $("#purchase-datagrid-arrivalOrderShareLogPage").datagrid({
            columns:[[
                {field:'goodsid',title:'分摊商品',width:250,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.goodsname;
                    }
                },
                {field:'taxamount',title:'费用金额',resizable:true,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'remark',title:'备注',resizable:true,sortable:true,align:'right'}

            ]],
            border: true,
            rownumbers: true,
            showFooter: true,
            pagination: true,
            striped:true,
            fit:true,
            singleSelect: true,
            url:'purchase/arrivalorder/getPurchaseShareLogDate.do?id=${param.id}',
        });


    });


</script>
</body>
</html>
