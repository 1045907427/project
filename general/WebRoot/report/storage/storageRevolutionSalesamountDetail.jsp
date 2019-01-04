<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>库存周转天数报表销售流水明细</title>
</head>
<body>
<table id="report-table-SalesamountDetail"></table>
<input type="hidden" id="report-checkboxcol-SalesamountDetail" value="${col}" />
<input type="hidden" id="report-checkboxvalue-SalesamountDetail" value="${value}" />
<script type="text/javascript">
    var checkListJson = $("#report-table-SalesamountDetail").createGridColumnLoad({
        commonCol: [[
            {field:'businessdate',title:'业务日期',width:80},
            {field:'customerid',title:'客户编码',width:60},
            {field:'customername',title:'客户名称',width:100},
            {field:'salesuser',title:'客户业务员',width:70,
                formatter:function(value,rowData,rowIndex){
                    return rowData.salesusername;
                }
            },
            {field:'salesareaname',title:'销售区域',width:100,hidden:true},
            {field:'billtype',title:'单据类型',width:60,
                formatter:function(value,rowData,rowIndex){
                    if(value=='0'){
                        return "销售出库单";
                    }else if(value=='1'){
                        return "退货入库单";
                    }else if(value=='2'){
                        return "应收款冲差单";
                    }
                }
            },
            {field:'storageid',title:'库存仓库',width:80,
                formatter:function(value,rowData,rowIndex){
                    return rowData.storagename;
                }
            },
            {field:'id',title:'单据编号',sortable:true,width:130},
            {field:'goodsid',title:'商品编码',width:60},
            {field:'spell',title:'助记符',width:60},
            {field:'goodsname',title:'商品名称',width:100},
            {field:'barcode',title:'条形码',sortable:true,width:90},
            {field:'brandid',title:'品牌名称',width:80,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.brandname;
                }
            },
            {field:'branddept',title:'品牌部门',width:80,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.branddeptname;
                }
            },
            {field:'supplierid',title:'供应商编号',width:80,sortable:true},
            {field:'suppliername',title:'供应商名称',width:200},
            {field:'unitname',title:'单位',width:40},
            {field:'unitnum',title:'数量',width:60,align:'right',
                formatter:function(value,rowData,rowIndex){
                    if(value!=null && value!=0){
                        return formatterNum(value);
                    }
                }
            },
            {field:'salesamount',title:'销售金额',width :80,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            }
        ]]
    });
    $(function(){
        var col = $("#report-checkboxcol-SalesamountDetail").val();console.log(col);
        var value = $("#report-checkboxvalue-SalesamountDetail").val();
        $("#report-table-SalesamountDetail").datagrid({
            authority:checkListJson,
            columns:checkListJson.common,
            fit:true,
            method:'post',
            rownumbers:true,
            pagination:true,
            idField:'id',
            singleSelect:false,
            url:'report/storage/salesAmountDetailData.do?begindate=${begindate}&enddate=${enddate}&col='+col+'&value='+value
        });
    });
</script>

</body>
</html>
