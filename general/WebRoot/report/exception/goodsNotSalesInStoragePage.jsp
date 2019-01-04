<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title></title>
</head>
<body>
<table id="report-datagrid-goodsNotSalesInStoragePage"></table>

<script type="text/javascript">

        $("#report-datagrid-goodsNotSalesInStoragePage").datagrid({
            columns:[[
                {field:'field01',title:'仓库编码',width:80,sortable:true},
                {field:'name',title:'仓库名称',width:80,sortable:true},
                {field:'field02',title:'商品现存量',width:80,sortable:true,
                    formatter:function(value,row,index){
                        return formatterNum(value);
                    },
                }
            ]],
            striped:true,
            border: true,
            fit: true,
            data: JSON.parse('${detaillist}')
        }).datagrid("columnMoving");

</script>
</body>
</html>
