<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>分客户业务员品牌目标考核报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>
<body>
<table id="report-datagrid-salesUserTargetReport"></table>
<div id="report-toolbar-salesUserTargetReport" style="padding: 0px">
    <div class="buttonBG">
        <security:authorize url="/report/sales/exportSalesUserTargetReportData.do">
            <a href="javaScript:void(0);" id="report-autoExport-salesUserTargetReport" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
        </security:authorize>
    </div>
    <form action="" method="post" id="report-form-salesUserTargetReport">
        <table class="querytable">
            <tr>
                <td>本期日期:</td>
                <td><input type="text" id="report-bqstartdate-salesUserTargetReport" name="bqstartdate" value="${firstDay }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${monthday }'})" /> 到 <input type="text" id="report-bqenddate-salesUserTargetReport" name="bqenddate" value="${monthday }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${monthday }'})" /></td>
                <td>客户:</td>
                <td><input type="text" name="customeridarr" id="report-query-customeridarr"/></td>
                <td>客户业务员:</td>
                <td><input type="text" name="salesuser" id="report-query-salesuser"/></td>
             </tr>
            <tr>
                <td>前期日期:</td>
                <td><input type="text" name="qqstartdate" value="${prevyearfirstday }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="qqenddate" value="${prevyearcurday }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                <td>品牌:</td>
                <td><input type="text" name="brandidarr" id="report-query-brandidarr"/></td>
                <td colspan="2" style="padding-left: 30px;">
                    <a href="javaScript:void(0);" id="report-query-salesUserTargetReport" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-salesUserTargetReport" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">

    var footerobject  = null;
    var businessdate = $("#report-businessdate1").val();

    //结束行编辑
    var $salesUserDatagrid = $('#report-datagrid-salesUserTargetReport');
    var editIndex = undefined;
    var editfiled = null;
    function endEditing(){
        if (editIndex == undefined){
            return true
        }else{return false;}
    }

    function computeDataCaseField() {
        var row = $salesUserDatagrid.datagrid('getRows')[editIndex];

        var targets = undefined != row.targets ? row.targets : 0;
        var nweektargets = undefined != row.nweektargets ? row.nweektargets : 0;
        row.targets = targets;
        row.nweektargets = nweektargets;
        if(Number(targets) != Number(0)){
            row.salerate = Number(row.saleamount)/targets ;
        }
        $salesUserDatagrid.datagrid('updateRow',{index:editIndex, row:row});

        $.ajax({
            url:'report/target/addSalesUserTarget.do',
            data:{rowstr:JSON.stringify(row),billtype:'SalesUserTargetCustomerBrand'},
            dataType:'json',
            type:'post',
            success:function(json){

            }
        });

    }

    $(function(){
        //导出
        $("#report-autoExport-salesUserTargetReport").click(function(){
            var queryJSON = $("#report-form-salesUserTargetReport").serializeJSON();
            //获取排序规则
            var objecr  = $("#report-datagrid-salesUserTargetReport").datagrid("options");
            if(null != objecr.sortName && null != objecr.sortOrder ){
                queryJSON["sort"] = objecr.sortName;
                queryJSON["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryJSON);
            var url = "report/sales/exportSalesUserTargetReportData.do";
            exportByAnalyse(queryParam,"分客户业务员品牌目标考核报表","report-datagrid-salesUserTargetReport",url);
        });

        var tableColumnListJson = $("#report-datagrid-salesUserTargetReport").createGridColumnLoad({
            frozenCol : [[
                {field:'idok',checkbox:true,isShow:true}
            ]],
            commonCol : [[
                {field:'salesuser',title:'业务员',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return rowData.salesusername;
                    }
                },
                {field:'customerid',title:'客户',width:210,isShow:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.customername;
                    }
                },
                {field:'brand',title:'品牌',width:80,isShow:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.brandname;
                    }
                },
                {field: 'targets', title: '销售目标', width: 70, align: 'right',
                    formatter: function (value, rowData, rowIndex){
                        return formatterMoney(value);
                    },
                    editor:{
                        type:'numberbox',
                        options:{
                            precision:2
                        }
                    }
                },
                {field: 'saleamount', title: '销售实绩', width: 70, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {field: 'salerate', title: '完成占比', width: 70, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        if (value && !isNaN(value)) {
                            return Number(value * 100).toFixed(2) + "%";
                        }
                    }
                },
                {field: 'qqsaleamount', title: '前期销售实绩', width: 80, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {field: 'nweektargets', title: '下月销售目标', width: 80, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    },
                    editor:{
                        type:'numberbox',
                        options:{
                            precision:2
                        }
                    }
                }
            ]]
        });
        $("#report-datagrid-salesUserTargetReport").datagrid({
            authority:tableColumnListJson,
            frozenColumns: tableColumnListJson.frozen,
            columns:tableColumnListJson.common,
            method:'post',
            title:'',
            fit:true,
            //sortName:'salesuser',
            //sortOrder:'asc',
            rownumbers:true,
            pagination:true,
            showFooter: true,
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            pageSize:100,
            toolbar:'#report-toolbar-salesUserTargetReport',
            onDblClickRow:function(rowIndex, rowData){
                if(undefined != rowData && undefined != rowData.salesuser){
                    if(endEditing()){
                        editIndex = rowIndex;
                        $(this).datagrid('beginEdit', rowIndex);
                        var ed = $(this).datagrid('getEditor', {index:editIndex,field:'targets'});
                        if(ed != null){
                            $(ed.target).textbox('textbox').focus();
                            $(ed.target).textbox('textbox').select();
                            editfiled = "targets";
                        }
                        $(this).datagrid('selectRow', rowIndex);
                    }
                }
            },
            onClickRow:function(rowIndex, rowData){
                if(!endEditing()){
                    if($(this).datagrid('validateRow', editIndex)){
                        $(this).datagrid('endEdit', editIndex);
                        computeDataCaseField();
                        editIndex = undefined;
                        $(this).datagrid('clearSelections');
                    }
                }
            },
            onLoadSuccess:function(){
                var footerrows = $(this).datagrid('getFooterRows');
                if(null!=footerrows && footerrows.length>0){
                    footerobject = footerrows[0];
                    totalAmount();
                }
            },
            onCheckAll:function(){
                totalAmount();
            },
            onUncheckAll:function(){
                totalAmount();
            },
            onCheck:function(){
                totalAmount();
            },
            onUncheck:function(){
                totalAmount();
            }
        });
        //客户
        $("#report-query-customeridarr").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER',
            singleSelect:false,
            width:180,
            onlyLeafCheck:true
        });
        $("#report-query-brandidarr").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            width:180,
            singleSelect:false
        });
        $("#report-query-salesuser").widget({
            referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
            singleSelect:true,
            width:150,
            onlyLeafCheck:true
        });
        //编辑状态下获取焦点赋值
        $(".datagrid-editable-input").live("focus",function(){
            $(this).select();
            editfiled = $(this).parents("table:first").parent().parent().attr("field");
        });
        //回车键操作
        $(document).die("keydown").live("keydown",function(event){
            if(event.keyCode==13 && !endEditing()){
                if("targets" == editfiled){
                    var ed = $salesUserDatagrid.datagrid('getEditor', {index:editIndex,field:'nweektargets'});
                    if(ed != null){
                        $(ed.target).textbox('textbox').focus();
                        $(ed.target).textbox('textbox').select();
                        editfiled = "nweektargets";
                    }
                }else if("nweektargets" == editfiled){
                    if($salesUserDatagrid.datagrid('validateRow', editIndex)){
                        var index = editIndex;
                        $salesUserDatagrid.datagrid('endEdit', editIndex);
                        computeDataCaseField();
                        editIndex = undefined;
                        editfiled = null;
                        $salesUserDatagrid.datagrid('clearSelections');

                        var row = $salesUserDatagrid.datagrid('getRows')[index+1];
                        if(undefined != row && endEditing()){
                            editIndex = index+1;
                            $salesUserDatagrid.datagrid('beginEdit', editIndex);
                            var ed = $salesUserDatagrid.datagrid('getEditor', {index:editIndex,field:'targets'});
                            if(ed != null){
                                $(ed.target).textbox('textbox').focus();
                                $(ed.target).textbox('textbox').select();
                                editfiled = "targets";
                            }
                            $salesUserDatagrid.datagrid('selectRow', editIndex);
                        }
                    }
                }

            }
        });

        //查询
        $("#report-query-salesUserTargetReport").click(function(){
            var bqstartdate=$("#report-bqstartdate-salesUserTargetReport").val();
            var bqenddate=$("#report-bqenddate-salesUserTargetReport").val();
            if(null==bqstartdate || "" == bqstartdate){
                $.messager.alert("提醒","抱歉，请填写本期时间");
                return false;
            }
            if(null==bqenddate || "" == bqenddate){
                $.messager.alert("提醒","抱歉，请填写本期结束时间");
                return false;
            }
            var queryJSON = $("#report-form-salesUserTargetReport").serializeJSON();
            $("#report-datagrid-salesUserTargetReport").datagrid({
                url: 'report/sales/showSalesUserTargetReportData.do',
                pageNumber:1,
                queryParams:queryJSON
            });
        });
        //重置
        $("#report-reload-salesUserTargetReport").click(function(){
            $("#report-query-customeridarr").widget('clear');
            $("#report-query-brandidarr").widget('clear');
            $("#report-query-salesuser").widget('clear');
            $("#report-bqstartdate-salesUserTargetReport").val('${firstDay }');
            $("#report-bqenddate-salesUserTargetReport").val('${monthday }');
            $("input[name = qqstartdate]").val('${prevyearfirstday }');
            $("input[name = qqenddate]").val('${prevyearcurday }');
        });

    });
    function totalAmount() {
        var rows = $salesUserDatagrid.datagrid('getChecked');
        var targets = 0;
        var saleamount = 0;
        var qqsaleamount = 0;
        var nweektargets = 0;
        for(var i=0;i<rows.length;i++){
            targets = Number(targets)+Number(rows[i].targets == undefined ? 0 : rows[i].targets);
            saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
            qqsaleamount = Number(qqsaleamount)+Number(rows[i].qqsaleamount == undefined ? 0 : rows[i].qqsaleamount);
            nweektargets = Number(nweektargets)+Number(rows[i].nweektargets == undefined ? 0 : rows[i].nweektargets);
        }
        var foot=[{ customername:'选中合计',targets:targets,saleamount:saleamount,qqsaleamount:qqsaleamount,nweektargets:nweektargets }];
        if(null!=footerobject){
            foot.push(footerobject);
        }
        $salesUserDatagrid.datagrid("reloadFooter",foot);

    }


</script>
</body>
</html>