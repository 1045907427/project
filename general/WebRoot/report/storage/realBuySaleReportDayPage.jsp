<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>每日进销存汇总统计报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
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
<table id="report-datagrid-buySaleReport"></table>
<div id="report-toolbar-buySaleReport" style="padding:0px;height:auto">
    <div class="buttonBG">
        <security:authorize url="/report/storage/realBuySaleReportDayExport.do">
            <a href="javaScript:void(0);" id="report-buttons-buySaleReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
        </security:authorize>
    </div>
    <form action="" id="report-query-form-buySaleReport" method="post">
        <table class="querytable">
            <tr>
                <td>业务日期:</td>
                <td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${yestoday }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${yestoday }'})" />
                    到 <input id="report-query-businessdate2" type="text" name="businessdate2" value="${yestoday }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${yestoday }'})" />
                </td>
                <td>品牌:</td>
                <td><input type="text" id="report-query-brandid" name="brandid" style="width: 180px;"/></td>
                <td>仓库:</td>
                <td><input type="text" id="report-query-storageid" name="storageid"/></td>
            </tr>
            <tr>
                <td>商品:</td>
                <td><input type="text" id="report-query-goodsid" name="goodsid" style="width: 225px;"/></td>
                <td>供应商:</td>
                <td><input type="text" id="report-query-supplierid" name="supplierid"/></td>
                <td>品牌部门:</td>
                <td><input type="text" id="report-query-branddept" name="deptid"/></td>
            </tr>
            <tr>
                <td>小计列:</td>
                <td colspan="3">
                    <div style="margin-top:3px">
                        <input type="checkbox" class="groupcols checkbox1" value="goodsid" id="goodsid" />
                        <label for="goodsid" class="divtext"> 商品</label>
                        <input type="checkbox" class="groupcols checkbox1" value="storageid" id="storageid"/>
                        <label for="storageid" class="divtext"> 仓库</label>
                        <input type="checkbox" class="groupcols checkbox1" value="deptid" id="deptid"/>
                        <label for="deptid" class="divtext"> 品牌部门</label>
                        <input type="checkbox" class="groupcols checkbox1" value="brandid" id="brandid"/>
                        <label for="brandid" class="divtext"> 品牌</label>
                        <input type="checkbox" class="groupcols checkbox1" value="supplierid" id="supplierid"/>
                        <label for="supplierid" class="divtext"> 供应商</label>
                        <input id="report-query-groupcols" type="hidden" name="groupcols"/>
                    </div>
                </td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="report-queay-buySaleReport" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-buySaleReport" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    var page_cols = "";
    var initQueryJSON = $("#report-query-form-buySaleReport").serializeJSON();
    $(function(){
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

        $("#report-datagrid-buySaleReport").datagrid({
            columns:[[
                {field:'goodsid',title:'商品编码',width:70,rowspan:2},
                {field:'goodsname',title:'商品名称',width:130,rowspan:2},
                {field:'barcode',title:'条形码',sortable:true,width:90,rowspan:2},
                {field:'brandid',title:'品牌名称',width:60,sortable:true,rowspan:2,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.brandname;
                    }
                },
                {field:'deptid',title:'品牌部门',width:70,sortable:true,rowspan:2,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.deptname;
                    }
                },
                {field:'storageid',title:'仓库名称',width:90,sortable:true,rowspan:2,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.storagename;
                    }
                },
                {field:'supplierid',title:'供应商编码',width:70,sortable:true,rowspan:2},
                {field:'suppliername',title:'供应商名称',width:200,rowspan:2},
                {field:'unitname',title:'单位',width:40,rowspan:2},
                {field:'price',title:'成本价',width:50,align:'right',rowspan:2,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {title:'期初',align:'center',colspan:4},
                {title:'采购',align:'center',colspan:8},
                {title:'销售',align:'center',colspan:10},
                {title:'调拨',align:'center',colspan:8},
                {title:'损益',align:'center',colspan:6},
                {title:'期末',align:'center',colspan:4}
            ],[
                {field:'initnum',title:'数量',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'auxinitnumdetail',title:'箱数',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(page_cols.indexOf("goodsid") != -1){
                            return value;
                        }else{
                            return rowData.inittotalbox;
                        }
                    }
                },
                {field:'initamount',title:'含税金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'initnotaxamount',title:'未税金额',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'buyinnum',title:'进货数量',width:80,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'auxbuyinnumdetail',title:'进货箱数',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(page_cols.indexOf("goodsid") != -1){
                            return value;
                        }else{
                            return rowData.buyintotalbox;
                        }
                    }
                },
                {field:'buyinamount',title:'进货金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'buyinnotaxamount',title:'进货未税金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'buyoutnum',title:'退货数量',width:80,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'auxbuyoutnumdetail',title:'退货箱数',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(page_cols.indexOf("goodsid") != -1){
                            return value;
                        }else{
                            return rowData.buyouttotalbox;
                        }
                    }
                },
                {field:'buyoutamount',title:'退货金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'buyoutnotaxamount',title:'退货未税金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'saleoutnum',title:'出库数量',width:80,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'auxsaleoutnumdetail',title:'出库箱数',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(page_cols.indexOf("goodsid") != -1){
                            return value;
                        }else{
                            return rowData.saleouttotalbox;
                        }
                    }
                },
                {field:'saleoutamount',title:'出库金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'saleoutnotaxamount',title:'出库未税金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'saleinnum',title:'退货数量',width:80,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'auxsaleinnumdetail',title:'退货箱数',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(page_cols.indexOf("goodsid") != -1){
                            return value;
                        }else{
                            return rowData.saleintotalbox;
                        }
                    }
                },
                {field:'saleinamount',title:'退货金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'saleinnotaxamount',title:'退货未税金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'costoutamount',title:'合计成本金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'costnotaxoutamount',title:'合计成本未税金额',width:100,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'allocateinnum',title:'入库数量',width:80,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'auxallocateinnumdetail',title:'入库箱数',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(page_cols.indexOf("goodsid") != -1){
                            return value;
                        }else{
                            return rowData.allocateintotalbox;
                        }
                    }
                },
                {field:'allocateinamount',title:'入库金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'allocateinnotaxamount',title:'入库未税金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'allocateoutnum',title:'出库数量',width:80,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'auxallocateoutnumdetail',title:'出库箱数',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(page_cols.indexOf("goodsid") != -1){
                            return value;
                        }else{
                            return rowData.allocateouttotalbox;
                        }
                    }
                },
                {field:'allocateoutamount',title:'出库金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'allocateoutnotaxamount',title:'出库未税金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'lossnum',title:'数量',width:80,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'auxlossnumdetail',title:'箱数',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(page_cols.indexOf("goodsid") != -1){
                            return value;
                        }else{
                            return rowData.losstotalbox;
                        }
                    }
                },
                {field:'lossamount',title:'含税金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'lossnotaxamount',title:'未税金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'costlossamount',title:'成本含税金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'costnotaxlossamount',title:'成本未税金额',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'endnum',title:'数量',width:80,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'auxendnumdetail',title:'箱数',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(page_cols.indexOf("goodsid") != -1){
                            return value;
                        }else{
                            return rowData.endtotalbox;
                        }
                    }
                },
                {field:'endamount',title:'含税金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'endnotaxamount',title:'未税金额',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                }
            ]],
            method:'post',
            title:'',
            fit:true,
            rownumbers:true,
            pagination:true,
            showFooter: true,
            pageSize:100,
            singleSelect:true,
            checkOnSelect:true,
            selectOnCheck:true,
            toolbar:'#report-toolbar-buySaleReport'
        }).datagrid("columnMoving");
        $("#report-query-goodsid").goodsWidget({
            isHiddenUsenum:true
        });
        //品牌
        $("#report-query-brandid").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            width:180,
            singleSelect:false
        });
        //仓库
        $("#report-query-storageid").widget({
            referwid:'RL_T_BASE_STORAGE_INFO',
            width:150,
            singleSelect:false
        });
        //品牌部门
        $("#report-query-branddept").widget({
            referwid:'RL_T_BASE_DEPARTMENT_BUYER',
            width:150,
            singleSelect:true
        });
        //供应商
        $("#report-query-supplierid").widget({
            referwid:'RL_T_BASE_BUY_SUPPLIER',
            width:180,
            singleSelect:false
        });

        //回车事件
        controlQueryAndResetByKey("report-queay-buySaleReport","report-reload-buySaleReport");

        //查询
        $("#report-queay-buySaleReport").click(function(){
            setColumn();
            var queryJSON = $("#report-query-form-buySaleReport").serializeJSON();
            $("#report-datagrid-buySaleReport").datagrid({
                url: 'report/storage/showRealBuySaleReportDayData.do',
                pageNumber:1,
                queryParams:queryJSON
            });
        });
        //重置
        $("#report-reload-buySaleReport").click(function(){
            $(".groupcols").each(function(){
                if($(this).attr("checked")){
                    $(this)[0].checked = false;
                }
            });
            $("#report-query-groupcols").val("");
            $("#report-query-goodsid").goodsWidget("clear");
            $("#report-query-brandid").widget("clear");
            $("#report-query-storageid").widget("clear");
            $("#report-query-branddept").widget('clear');
            $("#report-query-supplierid").widget('clear');
            $("#report-query-form-buySaleReport").form("reset");
            var queryJSON = $("#report-query-form-buySaleReport").serializeJSON();
            $("#report-datagrid-buySaleReport").datagrid('loadData',{total:0,rows:[],footer:[]});
            setColumn();
        });

        $("#report-buttons-buySaleReportPage").Excel('export',{
            queryForm: "#report-query-form-buySaleReport", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            type:'exportUserdefined',
            name:'实际每日进销存汇总统计报表',
            url:'report/storage/exportBuySaleReportData.do?type=real'
        });
    });

    function setColumn(){
        var cols = $("#report-query-groupcols").val();
        if("" == cols){
            page_cols = "goodsid";
        }else{
            page_cols = cols;
        }
        if(cols != ""){
            $("#report-datagrid-buySaleReport").datagrid('hideColumn','goodsid');
            $("#report-datagrid-buySaleReport").datagrid('hideColumn','goodsname');
            $("#report-datagrid-buySaleReport").datagrid('hideColumn','barcode');
            $("#report-datagrid-buySaleReport").datagrid('hideColumn','brandid');
            $("#report-datagrid-buySaleReport").datagrid('hideColumn','deptid');
            $("#report-datagrid-buySaleReport").datagrid('hideColumn','storageid');
            $("#report-datagrid-buySaleReport").datagrid('hideColumn','supplierid');
            $("#report-datagrid-buySaleReport").datagrid('hideColumn','suppliername');
            $("#report-datagrid-buySaleReport").datagrid('hideColumn','unitname');
            $("#report-datagrid-buySaleReport").datagrid('hideColumn','price');

            var colarr = cols.split(",");
            for(var i=0;i<colarr.length;i++){
                var col = colarr[i];
                if(col=='goodsid' || col == ""){
                    $("#report-datagrid-buySaleReport").datagrid('showColumn','goodsid');
                    $("#report-datagrid-buySaleReport").datagrid('showColumn','goodsname');
                    $("#report-datagrid-buySaleReport").datagrid('showColumn','barcode');
                    $("#report-datagrid-buySaleReport").datagrid('showColumn','brandid');
                    $("#report-datagrid-buySaleReport").datagrid('showColumn','unitname');
                    $("#report-datagrid-buySaleReport").datagrid('showColumn','price');
                }else if(col=='storageid'){
                    $("#report-datagrid-buySaleReport").datagrid('showColumn','storageid');
                }else if(col=='deptid'){
                    $("#report-datagrid-buySaleReport").datagrid('showColumn','deptid');
                }else if(col=='brandid'){
                    $("#report-datagrid-buySaleReport").datagrid('showColumn','brandid');
                }else if(col=='supplierid'){
                    $("#report-datagrid-buySaleReport").datagrid('showColumn','supplierid');
                    $("#report-datagrid-buySaleReport").datagrid('showColumn','suppliername');
                }
            }
        }else{
            $("#report-datagrid-buySaleReport").datagrid('showColumn','goodsid');
            $("#report-datagrid-buySaleReport").datagrid('showColumn','goodsname');
            $("#report-datagrid-buySaleReport").datagrid('showColumn','barcode');
            $("#report-datagrid-buySaleReport").datagrid('showColumn','brandid');
            $("#report-datagrid-buySaleReport").datagrid('hideColumn','deptid');
            $("#report-datagrid-buySaleReport").datagrid('hideColumn','storageid');
            $("#report-datagrid-buySaleReport").datagrid('hideColumn','supplierid');
            $("#report-datagrid-buySaleReport").datagrid('hideColumn','suppliername');
            $("#report-datagrid-buySaleReport").datagrid('showColumn','unitname');
            $("#report-datagrid-buySaleReport").datagrid('showColumn','price');
        }
    }

</script>
</body>
</html>
