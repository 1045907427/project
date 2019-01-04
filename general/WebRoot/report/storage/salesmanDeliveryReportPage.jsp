<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>业务员发货报表</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<table id="report-datagrid-salesmanDeliveryReportPage"></table>
<div id="report-toolbar-salesmanDeliveryReportPage" style="height:auto;padding:0px">
    <div class="buttonBG">
        <security:authorize url="/report/sales/salesmanDeliveryReportPageExport.do">
            <a href="javaScript:void(0);" id="report-export-salesmanDeliveryReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
        </security:authorize>
    </div>
    <form id="report-query-form-salesmanDeliveryReportPage" method="post">
        <table class="querytable">
            <tr>
                <td>业务日期：</td>
                <td class="tdinput"><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${firstday}" />
                    到<input type="text" name="businessdate2" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today}" /></td>
                <td>人员部门:</td>
                <td><input type="text" name="persondept" id="report-persondept-advancedQuery"/></td>
                <td>品&nbsp;&nbsp;牌:</td>
                <td><input type="text" name="brandid" id="report-brandid-advancedQuery"/></td>
            </tr>
            <tr>
                <td>商&nbsp;&nbsp;品:</td>
                <td><input type="text" name="goodsid" id="report-goodsid-advancedQuery"/></td>
                <td>品牌业务员:</td>
                <td><input type="text" name="branduser" id="report-branduser-advancedQuery"/></td>
                <td>客&nbsp;&nbsp;户:</td>
                <td><input type="text" name="customerid" id="report-customernamemore-advancedQuery"/></td>
                <td colspan="2" class="tdbutton" style="padding-left: 15px">
                    <a href="javascript:void(0);" id="sales-queay-salesmanDeliveryReportPage" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="sales-resetQueay-salesmanDeliveryReportPage" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#report-query-form-salesmanDeliveryReportPage").serializeJSON();
    var SR_footerobject  = null;
    $(function() {

        $("#report-export-salesmanDeliveryReportPage").click(function () {
            //封装查询条件
            var objecr = $("#report-datagrid-salesmanDeliveryReportPage").datagrid("options");
            var queryParam = objecr.queryParams;
            if (null != objecr.sortName && null != objecr.sortOrder) {
                queryParam["sort"] = objecr.sortName;
                queryParam["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryParam);
            var url = "report/storage/exportsalesmanDeliveryReportPage.do";
            exportByAnalyse(queryParam, "业务员发货报表", "report-datagrid-salesmanDeliveryReportPage", url);
        });

        var tableColumnListJson = $("#report-datagrid-salesmanDeliveryReportPage").createGridColumnLoad({
            frozenCol: [[{field: 'idok', checkbox: true, isShow: true} ]],
            commonCol: [[
                {field: 'persondept', title: '人员部门', sortable: true, width: 80,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.persondeptname;
                    }
                },
                {field: 'sourceid', title: '来源单据编号', hidden: true, width: 140},
                {field: 'customerid', title: '客户编码', sortable: true, width: 60},
                {field: 'customername', title: '客户名称', width: 180},
                {field: 'branduser', title: '品牌业务员', sortable: true, width: 70,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.brandusername;
                    }
                },
                {field: 'goodsid', title: '商品编码', sortable: true, width: 60},
                {field: 'goodsname', title: '商品名称', width: 180},
                {field: 'barcode', title: '条形码', width: 90},
                {field: 'brandid', title: '品牌', sortable: true, width: 60,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.brandname;
                    }
                },
                {field: 'model', title: '规格', width: 60, align: 'right'},
                {field: 'unitnum', title: '发货数量', width: 60, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {field: 'totalbox', title: '发货件数', width: 60, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {field: 'discountamount', title: '发货折扣', width: 60, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {field: 'taxamount', title: '发货金额', width: 60, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                }
            ]]
        });
        //品牌
        $("#report-brandid-advancedQuery").widget({
            referwid: 'RL_T_BASE_GOODS_BRAND',
            singleSelect: false,
            width: 150,
            onlyLeafCheck: true
        });
        //销售部门
        $("#report-persondept-advancedQuery").widget({
            referwid: 'RL_T_BASE_DEPARTMENT_SELLER',
            width: 150,
            singleSelect: false,
            onlyLeafCheck: true
        });
        //客户
        $("#report-customernamemore-advancedQuery").widget({
            referwid: 'RL_T_BASE_SALES_CUSTOMER',
            singleSelect: false,
            width: 150,
            onlyLeafCheck: true
        });
        //商品
        $("#report-goodsid-advancedQuery").widget({
            referwid: 'RL_T_BASE_GOODS_INFO',
            singleSelect: false,
            width: 218,
            onlyLeafCheck: true
        });
        //品牌业务员
        $("#report-branduser-advancedQuery").widget({
            referwid: 'RL_T_BASE_PERSONNEL_SALESMAN',
            singleSelect: false,
            width: 150,
            onlyLeafCheck: false
        });

        $("#report-datagrid-salesmanDeliveryReportPage").datagrid({
            authority: tableColumnListJson,
            frozenColumns: tableColumnListJson.frozen,
            columns: tableColumnListJson.common,
            method:'post',
            fit:true,
            rownumbers:true,
            pagination:true,
            showFooter: true,
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            pageSize:100,
            queryParams:initQueryJSON,
            toolbar: '#report-toolbar-salesmanDeliveryReportPage',
            onLoadSuccess:function() {
                var footerrows = $(this).datagrid('getFooterRows');
                if (null != footerrows && footerrows.length > 0) {
                    SR_footerobject = footerrows[0];
                }
            },
            onCheckAll:function(){
                countTotalAmount();
            },
            onUncheckAll:function(){
                countTotalAmount();
            },
            onCheck:function(){
                countTotalAmount();
            },
            onUncheck:function(){
                countTotalAmount();
            }

        });

        //查询
        $("#sales-queay-salesmanDeliveryReportPage").click(function(){
            var queryJSON = $("#report-query-form-salesmanDeliveryReportPage").serializeJSON();
            $("#report-datagrid-salesmanDeliveryReportPage").datagrid({
                url: 'report/storage/showSalesmanDeliveryData.do',
                pageNumber:1,
                queryParams:queryJSON
            });
        });
        //重置
        $("#sales-resetQueay-salesmanDeliveryReportPage").click(function(){
            $("#report-persondept-advancedQuery").widget("clear");
            $("#report-customernamemore-advancedQuery").widget("clear");
            $("#report-goodsid-advancedQuery").widget("clear");
            $("#report-brandid-advancedQuery").widget("clear");
            $("#report-branduser-advancedQuery").widget("clear");
            $("#report-query-form-salesmanDeliveryReportPage")[0].reset();
            $("#report-query-groupcols").val("");
            $("#report-datagrid-salesmanDeliveryReportPage").datagrid('loadData',{total:0,rows:[]});
        });
        //选中合计
        function countTotalAmount(){
            var rows =  $("#report-datagrid-salesmanDeliveryReportPage").datagrid('getChecked');
            var unitnum=0;
            var totalbox =0;
            var discountamount=0;
            var taxamount=0;
            for(var i=0;i<rows.length;i++){
                unitnum = Number(unitnum)+Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
                totalbox = Number(totalbox)+Number(rows[i].totalbox == undefined ? 0 : rows[i].totalbox);
                discountamount = Number(discountamount)+Number(rows[i].discountamount == undefined ? 0 : rows[i].discountamount);
                taxamount = Number(taxamount)+Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
            }
            var foot=[{customername:'选中合计',unitnum:unitnum,totalbox:totalbox,discountamount:discountamount,taxamount:taxamount}];
            if(null!=SR_footerobject){
                foot.push(SR_footerobject);
            }
            $("#report-datagrid-salesmanDeliveryReportPage").datagrid("reloadFooter",foot);
        }



    });

</script>

</body>
</html>
