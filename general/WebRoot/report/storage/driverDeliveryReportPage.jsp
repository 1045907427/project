<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>配送员配送情况表</title>
    <%@include file="/include.jsp" %>
    <style>
        .checkbox1{
            float:left;
            height: 22px;
            line-height: 22px;
        }
        .divtext{
            height:22px;
            line-height:22px;
            float:left;
            display: block;
        }
    </style>
</head>
<body>
<table id="report-datagrid-driverDeliveryReportPage"></table>
<div id="report-toolbar-driverDeliveryReportPage" style="height:auto;padding:0px">
    <div class="buttonBG">
        <security:authorize url="/report/sales/driverDeliveryReportPageExport.do">
            <a href="javaScript:void(0);" id="report-export-driverDeliveryReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
        </security:authorize>
    </div>
    <form id="report-query-form-driverDeliveryReportPage" method="post">
        <table class="querytable">
            <tr>
                <td>业务日期：</td>
                <td class="tdinput"><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${firstday}" />
                    到<input type="text" name="businessdate2" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today}" /></td>
                <td>销售部门:</td>
                <td><input type="text" name="salesdept" id="report-salesdept-advancedQuery"/></td>
                <td>商&nbsp;&nbsp;品:</td>
                <td><input type="text" name="goodsid" id="report-goodsid-advancedQuery"/></td>
                <td>品&nbsp;&nbsp;牌:</td>
                <td><input type="text" name="brandid" id="report-brandid-advancedQuery"/></td>
            </tr>
            <tr>
                <td>验收日期:</td>
                <td class="tdinput"><input type="text" name="checkdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})"  />
                    到<input type="text" name="checkdate2" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
                <td>客户业务员:</td>
                <td><input type="text" name="salesuser" id="report-salesuser-advancedQuery"/></td>
                <td>客&nbsp;&nbsp;户:</td>
                <td><input type="text" name="customerid" id="report-customernamemore-advancedQuery"/></td>
            </tr>
            <tr>
                <td>小计列：</td>
                <td colspan="3">
                    <div style="margin-top:2px">
                        <div style="line-height: 25px;margin-top: 2px;">
                            <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="customerid"/>客户</label>
                            <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="goodsid" />商品</label>
                            <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="brandid"/>品牌</label>
                            <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesdept"/>销售部门</label>
                            <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesuser"/>客户业务员</label>
                            <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="driverid"/>配送员</label>
                            <input id="report-query-groupcols" type="hidden" name="groupcols"/>
                        </div>
                    </div>

                </td>
                <td>配送员:</td>
                <td><input type="text" name="driverid" id="report-driver-advancedQuery"/> </td>
                <td colspan="2" class="tdbutton" style="padding-left: 15px">
                    <a href="javascript:void(0);" id="sales-queay-driverDeliveryReportPage" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="sales-resetQueay-driverDeliveryReportPage" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#report-query-form-driverDeliveryReportPage").serializeJSON();
    var SR_footerobject  = null;
    $(function() {

        $("#report-export-driverDeliveryReportPage").click(function(){
            //封装查询条件
            var objecr  = $("#report-datagrid-driverDeliveryReportPage").datagrid("options");
            var queryParam = objecr.queryParams;
            if(null != objecr.sortName && null != objecr.sortOrder ){
                queryParam["sort"] = objecr.sortName;
                queryParam["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryParam);
            var url = "report/storage/exportDriverDeliveryReportData.do";
            exportByAnalyse(queryParam,"配送员配送情况表","report-datagrid-driverDeliveryReportPage",url);
        });

        $(".groupcols").click(function(){
            var cols = "";
            $("#report-query-groupcols").val(cols);
            $(".groupcols").each(function(){
                if($(this).attr("checked")){
                    if(cols==""){
                        cols = $(this).val();
                    }else{
                        cols += ","+$(this).val();
                    }
                    $("#report-query-groupcols").val(cols);
                }
            });
        });
        var tableColumnListJson = $("#report-datagrid-driverDeliveryReportPage").createGridColumnLoad({
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'businessdate', title: '业务日期', hidden: true, width: 70},
                {field: 'customerid', title: '客户编号', sortable: true, width: 60},
                {field: 'customername', title: '客户名称', width: 150},
                {field: 'salesuser', title: '客户业务员', sortable: true, width: 70,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.salesusername;
                    }
                },
                {field: 'salesdept', title: '销售部门', sortable: true, width: 80,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.salesdeptname;
                    }
                },
                {field: 'checkdate', title: '验收日期', hidden: true, width: 70},
                {field: 'driverid', title: '配送员', width: 60, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.drivername;
                    }
                },
                {field: 'goodsid', title: '商品编码', sortable: true, width: 60},
                {field: 'goodsname', title: '商品名称', width: 180},
                {field: 'barcode', title: '条形码', width: 90},
                {field: 'brandid', title: '商品品牌', sortable: true, width: 60,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.brandname;
                    }
                },
                {field: 'volume', title: '商品体积', width: 60, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        if( undefined != value ){
                            return Number(value).toFixed(3);
                        }else{
                            return '';
                        }
                    }
                },
                {field: 'weight', title: '商品重量', width: 60, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        if( undefined != value ){
                            return Number(value).toFixed(2);
                        }else{
                            return '';
                        }
                    }
                },
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
                {field: 'salesamount', title: '发货金额', width: 60, align: 'right',
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
            width: 153,
            onlyLeafCheck: true
        });
        //销售部门
        $("#report-salesdept-advancedQuery").widget({
            referwid:'RL_T_BASE_DEPARTMENT_SELLER',
            width:150,
            singleSelect:false,
            onlyLeafCheck:true
        });
        //客户
        $("#report-customernamemore-advancedQuery").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER',
            singleSelect:false,
            width:150,
            onlyLeafCheck:true
        });
        //商品
        $("#report-goodsid-advancedQuery").widget({
            referwid: 'RL_T_BASE_GOODS_INFO',
            singleSelect: false,
            width: 150,
            onlyLeafCheck: true
        });
        //客户业务员
        $("#report-salesuser-advancedQuery").widget({
            referwid: 'RL_T_BASE_PERSONNEL_CLIENTSELLER',
            singleSelect: false,
            width: 150,
            onlyLeafCheck: false
        });
        //司机
        $("#report-driver-advancedQuery").widget({
            referwid: 'RL_T_BASE_PERSONNEL_LOGISTICS',
            singleSelect: false,
            width: 150,
            onlyLeafCheck: false
        });

        $("#report-datagrid-driverDeliveryReportPage").datagrid({
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
            toolbar: '#report-toolbar-driverDeliveryReportPage',
            onLoadSuccess:function(){
                var footerrows = $(this).datagrid('getFooterRows');
                if(null!=footerrows && footerrows.length>0){
                    SR_footerobject = footerrows[0];
                }
            },
            onCheckAll:function(){
                countTotalAmount(setColumn());
            },
            onUncheckAll:function(){
                countTotalAmount(setColumn());
            },
            onCheck:function(){
                countTotalAmount(setColumn());
            },
            onUncheck:function(){
                countTotalAmount(setColumn());
            }
        });

        //查询
        $("#sales-queay-driverDeliveryReportPage").click(function(){
            setColumn();
            var queryJSON = $("#report-query-form-driverDeliveryReportPage").serializeJSON();
            $("#report-datagrid-driverDeliveryReportPage").datagrid({
                url: 'report/storage/getDriverDeliveryReportData.do',
                pageNumber:1,
                queryParams:queryJSON
            });
        });
        //重置
        $("#sales-resetQueay-driverDeliveryReportPage").click(function(){
            $("#report-salesdept-advancedQuery").widget("clear");
            $("#report-customernamemore-advancedQuery").widget("clear");
            $("#report-goodsid-advancedQuery").widget("clear");
            $("#report-brandid-advancedQuery").widget("clear");
            $("#report-salesuser-advancedQuery").widget("clear");
            $("#report-query-form-driverDeliveryReportPage")[0].reset();
            $("#report-query-groupcols").val("");
            $("#report-datagrid-driverDeliveryReportPage").datagrid('loadData',{total:0,rows:[]});
        });
    });

    var $datagrid = $("#report-datagrid-driverDeliveryReportPage");
    function setColumn(){
        var colume = "goodsid";
        var cols = $("#report-query-groupcols").val();
//        if(cols==""){
        $datagrid.datagrid('hideColumn', "customerid");
        $datagrid.datagrid('hideColumn', "customername");
        $datagrid.datagrid('hideColumn', "salesuser");
        $datagrid.datagrid('hideColumn', "salesdept");
//            $datagrid.datagrid('hideColumn', "goodsid");
//            $datagrid.datagrid('hideColumn', "goodsname");
//            $datagrid.datagrid('hideColumn', "barcode");
        $datagrid.datagrid('hideColumn', "brandid");
        $datagrid.datagrid('hideColumn', "driverid");
//        }
//        else{
//            $datagrid.datagrid('showColumn', "customerid");
//            $datagrid.datagrid('showColumn', "customername");
//            $datagrid.datagrid('showColumn', "salesuser");
//            $datagrid.datagrid('showColumn', "salesdept");
//            $datagrid.datagrid('showColumn', "goodsid");
//            $datagrid.datagrid('showColumn', "goodsname");
//            $datagrid.datagrid('showColumn', "barcode");
//            $datagrid.datagrid('showColumn', "brandid");
//            $datagrid.datagrid('showColumn', "driverid");
        //       }
        var colarr = cols.split(",");
        for(var i=0;i<colarr.length;i++){
            var col = colarr[i];
            if(col == ""){
                col = colume;
            }
            if(col=='customerid'){
                $datagrid.datagrid('showColumn', "customerid");
                $datagrid.datagrid('showColumn', "customername");
            }else if(col=="salesdept"){
                $datagrid.datagrid('showColumn', "salesdept");
            }else if(col=="salesuser"){
                $datagrid.datagrid('showColumn', "salesuser");
            }else if(col=="goodsid"){
                $datagrid.datagrid('showColumn', "goodsid");
                $datagrid.datagrid('showColumn', "goodsname");
                $datagrid.datagrid('showColumn', "barcode");
                $datagrid.datagrid('showColumn', "brandid");
            }else if(col=="brandid"){
                $datagrid.datagrid('showColumn', "brandid");
            }else if(col=="driverid"){
                $datagrid.datagrid('showColumn', "driverid");
            }
        }
        return colume;
    }
    function countTotalAmount(col){
        var rows =  $datagrid.datagrid('getChecked');
        if(null==rows || rows.length==0){
            var foot=[];
            if(null!=SR_footerobject){
                foot.push(SR_footerobject);
            }
            $datagrid.datagrid("reloadFooter",foot);
            return false;
        }
        var salesamount = 0;
        var auxnum = 0;
        var unitnum =0;
        var weight = 0;
        var volume = 0;
        var totalbox = 0 ;
        var discountamount = 0;
        for(var i=0;i<rows.length;i++){
            salesamount = Number(salesamount)+Number(rows[i].salesamount == undefined ? 0 : rows[i].salesamount);
            auxnum = Number(auxnum)+Number(rows[i].auxnum == undefined ? 0 : rows[i].auxnum);
            unitnum = Number(unitnum)+Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
            weight = Number(weight)+Number(rows[i].weight == undefined ? 0 : rows[i].weight);
            volume = Number(volume)+Number(rows[i].volume == undefined ? 0 : rows[i].volume);
            totalbox = Number(totalbox)+Number(rows[i].totalbox == undefined ? 0 :rows[i].totalbox);
            discountamount = Number(discountamount)+Number(rows[i].discountamount == undefined ? 0 : rows[i].discountamount);
        }

        var foot=[{customername:"选中合计",salesamount:salesamount,discountamount:discountamount,auxnum:auxnum,unitnum:unitnum,volume:volume,weight:weight,totalbox:totalbox}];
        foot[col]  = '选中合计';
        if(null!=SR_footerobject){
            foot.push(SR_footerobject);
        }
        $datagrid.datagrid("reloadFooter",foot);

    }

</script>

</body>
</html>
