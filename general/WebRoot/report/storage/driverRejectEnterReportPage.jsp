<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>司机退货原始明细表</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<table id="report-datagrid-driverRejectEnterReportPage"></table>
<div id="report-toolbar-driverRejectEnterReportPage" style="height:auto;padding:0px">
    <div class="buttonBG">
        <security:authorize url="/report/sales/driverRejectEnterReportPageExport.do">
            <a href="javaScript:void(0);" id="report-export-driverRejectEnterReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
        </security:authorize>
    </div>
    <form id="report-query-form-driverRejectEnterReportPage" method="post">
        <table class="querytable">
            <tr>
                <td>业务日期：</td>
                <td class="tdinput"><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${firstday}" />
                    到<input type="text" name="businessdate2" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today}" /></td>
                <td>销售部门:</td>
                <td><input type="text" name="salesdept" id="report-salesdept-advancedQuery"/></td>
                <td>商&nbsp;&nbsp;品:</td>
                <td><input type="text" name="goodsid" id="report-goodsid-advancedQuery"/></td>
            </tr>
            <tr>
                <td>客户业务员:</td>
                <td><input type="text" name="salesuser" id="report-salesuser-advancedQuery" /></td>
                <td>客&nbsp;&nbsp;户:</td>
                <td><input type="text" name="customerid" id="report-customernamemore-advancedQuery"/></td>
                <td>司&nbsp;&nbsp;机:</td>
                <td><input type="text" name="driverid" id="report-driver-advancedQuery"/> </td>
            </tr>
            <tr>
                <td>小计列：</td>
                <td colspan="3">
                    <div style="margin-top:2px">
                        <div style="line-height: 25px;margin-top: 2px;">
                            <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="goodsid"/>商品</label>
                            <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="customerid"/>客户</label>
                            <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="brandid"/>品牌</label>
                            <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesdept"/>销售部门</label>
                            <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesuser"/>客户业务员</label>
                            <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="driverid"/>司机</label>
                            <input id="report-query-groupcols" type="hidden" name="groupcols"/>
                        </div>
                    </div>
                </td>
                <td>品&nbsp;&nbsp;牌:</td>
                <td><input type="text" name="brandid" id="report-brandid-advancedQuery"/></td>
                <td colspan="2" class="tdbutton" style="padding-left: 15px">
                    <a href="javascript:void(0);" id="sales-queay-driverRejectEnterReportPage" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="sales-resetQueay-driverRejectEnterReportPage" class="button-qr">重置</a>
                </td>

            </tr>

        </table>
    </form>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#report-query-form-driverRejectEnterReportPage").serializeJSON();
    var SR_footerobject  = null;
    var $datagrid = $("#report-datagrid-driverRejectEnterReportPage");
    $(function(){
        $("#report-export-driverRejectEnterReportPage").click(function(){
            //封装查询条件
            var objecr  = $("#report-datagrid-driverRejectEnterReportPage").datagrid("options");
            var queryParam = objecr.queryParams;
            if(null != objecr.sortName && null != objecr.sortOrder ){
                queryParam["sort"] = objecr.sortName;
                queryParam["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryParam);
            var url = "report/storage/exportDriverRejectEnterReportPage.do";
            exportByAnalyse(queryParam,"司机退货原始明细表","report-datagrid-driverRejectEnterReportPage",url);
        });
        //小计列
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

        var tableColumnListJson = $("#report-datagrid-driverRejectEnterReportPage").createGridColumnLoad({
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'sourceid', title: '退货单编号', hidden: true, width: 150},
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
                {field: 'driverid', title: '司机', width: 60, align: 'right',
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
                {field: 'unitnum', title: '退货数量', width: 60, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {field: 'totalbox', title: '退货件数', width: 60, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {field: 'taxamount', title: '退货金额', width: 60, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                }

            ]]
        });

        $("#report-datagrid-driverRejectEnterReportPage").datagrid({
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
            url: 'report/storage/showDriverRejectEnterData.do',
            queryParams:initQueryJSON,
            toolbar: '#report-toolbar-driverRejectEnterReportPage',
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
            width: 218,
            onlyLeafCheck: false
        });
        //司机
        $("#report-driver-advancedQuery").widget({
            referwid: 'RL_T_BASE_PERSONNEL_LOGISTICS',
            singleSelect: false,
            width: 150,
            onlyLeafCheck: false
        });
        //品牌
        $("#report-brandid-advancedQuery").widget({
            referwid: 'RL_T_BASE_GOODS_BRAND',
            singleSelect: false,
            width: 150,
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
        //查询
        $("#sales-queay-driverRejectEnterReportPage").click(function(){
            //setColumn();
            var queryJSON = $("#report-query-form-driverRejectEnterReportPage").serializeJSON();
            $("#report-datagrid-driverRejectEnterReportPage").datagrid('load',queryJSON);
        });
        //重置
        $("#sales-resetQueay-driverRejectEnterReportPage").click(function(){
            $("#report-salesdept-advancedQuery").widget("clear");
            $("#report-customernamemore-advancedQuery").widget("clear");
            $("#report-goodsid-advancedQuery").widget("clear");
            $("#report-brandid-advancedQuery").widget("clear");
            $("#report-salesuser-advancedQuery").widget("clear");
            $("#report-query-form-driverRejectEnterReportPage")[0].reset();
            $("#report-query-groupcols").val("");
            $("#report-datagrid-driverRejectEnterReportPage").datagrid('loadData',{total:0,rows:[]});
        });
        function setColumn(){
            var colume = "customerid";
            var cols = $("#report-query-groupcols").val();
            if(cols!=""){
                $datagrid.datagrid('hideColumn', "customerid");
                $datagrid.datagrid('hideColumn', "customername");
                $datagrid.datagrid('hideColumn', "salesuser");
                $datagrid.datagrid('hideColumn', "salesdept");
                $datagrid.datagrid('hideColumn', "goodsid");
                $datagrid.datagrid('hideColumn', "goodsname");
                $datagrid.datagrid('hideColumn', "barcode");
                $datagrid.datagrid('hideColumn', "brandid");
                $datagrid.datagrid('hideColumn', "driverid");
            }
            else{
                $datagrid.datagrid('showColumn', "customerid");
                $datagrid.datagrid('showColumn', "customername");
                $datagrid.datagrid('showColumn', "salesuser");
                $datagrid.datagrid('showColumn', "salesdept");
                $datagrid.datagrid('showColumn', "goodsid");
                $datagrid.datagrid('showColumn', "goodsname");
                $datagrid.datagrid('showColumn', "barcode");
                $datagrid.datagrid('showColumn', "brandid");
                $datagrid.datagrid('showColumn', "driverid");
            }
            var colarr = cols.split(",");
            for(var i=0;i<colarr.length;i++){
                var col = colarr[i];
                colume = col;
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

    });

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
        var taxamount = 0;
        var unitnum =0;
        var weight = 0;
        var volume = 0;
        var totalbox = 0 ;
        for(var i=0;i<rows.length;i++){
            taxamount = Number(taxamount)+Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
            unitnum = Number(unitnum)+Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
            weight = Number(weight)+Number(rows[i].weight == undefined ? 0 : rows[i].weight);
            volume = Number(volume)+Number(rows[i].volume == undefined ? 0 : rows[i].volume);
            totalbox = Number(totalbox)+Number(rows[i].totalbox == undefined ? 0 :rows[i].totalbox);
        }
        var cols = $("#report-query-groupcols").val();
        var col = "cutomerid";
        if(cols != ""){
            var colarr = cols.split(",");
            for(var i=0;i<colarr.length;i++){
                col = colarr[0] ;
            }
        }
        if(col == "brandid"){
            var foot=[{brandname:'选中合计',taxamount:taxamount,unitnum:unitnum,volume:volume,weight:weight,totalbox:totalbox}];
        }else if(col == "salesuser"){
            var foot=[{salesusername:'选中合计',taxamount:taxamount,unitnum:unitnum,volume:volume,weight:weight,totalbox:totalbox}];
        }else if(col == "salesdept"){
            var foot=[{salesdeptname:'选中合计',taxamount:taxamount,unitnum:unitnum,volume:volume,weight:weight,totalbox:totalbox}];
        }else if(col == "driverid"){
            var foot=[{drivername:'选中合计',taxamount:taxamount,unitnum:unitnum,volume:volume,weight:weight,totalbox:totalbox}];
        }else if(col == "goodsid"){
            var foot=[{goodsid:'选中合计',taxamount:taxamount,unitnum:unitnum,volume:volume,weight:weight,totalbox:totalbox}];
        }else{
            var foot=[{customerid:'选中合计',taxamount:taxamount,unitnum:unitnum,volume:volume,weight:weight,totalbox:totalbox}];
        }

        if(null!=SR_footerobject){
            foot.push(SR_footerobject);
        }
        $datagrid.datagrid("reloadFooter",foot);

    }
    
    
</script>

</body>
</html>
