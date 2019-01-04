<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>分销售部门品牌销售回笼考核报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>

<body>
<table id="report-datagrid-salesdeptBrandWithdrawnAssess"></table>
<div id="report-toolbar-salesdeptBrandWithdrawnAssess" style="padding: 0px">
    <div class="buttonBG">
        <security:authorize url="/report/sales/salesdeptBrandWithdrawnAssessReportImport.do">
            <a href="javaScript:void(0);" id="report-import-salesdeptBrandWithdrawnAssess" class="easyui-linkbutton" iconCls="button-import" plain="true" title="导入">导入</a>
        </security:authorize>
        <security:authorize url="/report/sales/salesdeptBrandWithdrawnAssessReportExport.do">
            <a href="javaScript:void(0);" id="report-export-salesdeptBrandWithdrawnAssess" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
        </security:authorize>
    </div>
    <form action="" id="report-query-form-salesdeptBrandWithdrawnAssess" method="post">
        <table class="querytable">
            <tr>
                <td>日期:</td>
                <td><input type="text" id="report-businessdate1" name="businessdate1" value="${today }" style="width:80px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM',/*maxDate:'${today }'*/})" /></td>
                <td>销售部门:</td>
                <td><input type="text" name="salesdept" id="report-salesdept-salesdeptBrandWithdrawnAssess"/></td>
                <td>品牌:</td>
                <td><input type="text" name="brandid" id="report-brandid-salesdeptBrandWithdrawnAssess"/></td>
                <td>
                    <a href="javaScript:void(0);" id="report-queay-salesdeptBrandWithdrawnAssess" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-salesdeptBrandWithdrawnAssess" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    var footerobject  = null;
    var businessdate = $("#report-businessdate1").val();

    //结束行编辑
    var $salesdeptBrandSWADatagrid = $('#report-datagrid-salesdeptBrandWithdrawnAssess');
    var editIndex = undefined;
    var editfiled = null;
    function endEditing(){
        if (editIndex == undefined){
            return true
        }else{return false;}
    }

    //根据录入字段值计算对应换算数据（销售完成率、销售毛利完成业绩超率、回笼完成率、回笼毛利完成业绩超率）
    function computeDataCaseField(){
        var row = $salesdeptBrandSWADatagrid.datagrid('getRows')[editIndex];
        var initsaletargetamount = Number(row.initsaletargetamount == undefined ? 0 : row.initsaletargetamount);
        var initmarginratetarget = Number(row.initmarginratetarget == undefined ? 0 : row.initmarginratetarget);
        var initwithdrawntargetamount = Number(row.initwithdrawntargetamount == undefined ? 0 : row.initwithdrawntargetamount);
        var initwriteoffratetarget = Number(row.initwriteoffratetarget == undefined ? 0 : row.initwriteoffratetarget);
        var saletargetamount = undefined != row.saletargetamount ? row.saletargetamount : 0;
        var saleamount = (null != row.saleamount && "" != row.saleamount) ? row.saleamount : 0;
        var salemarginamount = (null != row.salemarginamount && "" != row.salemarginamount) ? row.salemarginamount : 0;
        var withdrawnamount = (null != row.withdrawnamount && "" != row.withdrawnamount) ? row.withdrawnamount : 0;
        var writeoffmarginamount = (null != row.writeoffmarginamount && "" != row.writeoffmarginamount) ? row.writeoffmarginamount : 0;
        var marginratetarget = undefined != row.marginratetarget ? row.marginratetarget : 0;
        var withdrawntargetamount = undefined != row.withdrawntargetamount ? row.withdrawntargetamount : 0;
        var writeoffratetarget = undefined != row.writeoffratetarget ? row.writeoffratetarget : 0;
        if(Number(saletargetamount).toFixed(2) ==  Number(initsaletargetamount).toFixed(2)
                && Number(marginratetarget).toFixed(2) ==  Number(initmarginratetarget).toFixed(2)
                && Number(withdrawntargetamount).toFixed(2) ==  Number(initwithdrawntargetamount).toFixed(2)
                && Number(writeoffratetarget).toFixed(2) ==  Number(initwriteoffratetarget).toFixed(2)
        ){
            return false;
        }
        row.saletargetamount = saletargetamount;
        row.marginratetarget = marginratetarget;
        row.withdrawntargetamount = withdrawntargetamount;
        row.writeoffratetarget = writeoffratetarget;

        //销售完成率=销售金额/本期销售目标
        if(Number(saletargetamount) != Number(0)){
            if(Number(saleamount)>= Number(0) && Number(saletargetamount)>Number(0)){
                var saledonerate = saleamount/saletargetamount*Number(100);
                row.saledonerate = saledonerate;
            }else if(saleamount >= saletargetamount){
                row.saledonerate = 100;
            }else{
                var saledonerate = (saleamount-saletargetamount)/Math.abs(saletargetamount)*Number(100);
                row.saledonerate = saledonerate ;
            }

        }else {
            row.saledonerate = "";
        }
        //本期目标毛利=本期销售目标*本期毛利率目标
        var salemargintargetamount = saletargetamount*(marginratetarget/Number(100));
        //销售业绩超率=销售毛利额/本期目标毛利
        if(Number(salemargintargetamount) >Number(0) &&  Number(salemarginamount) >= Number(0)){
            var salemargindonesurpassrate = salemarginamount / salemargintargetamount * Number(100);
            row.salemargindonesurpassrate = salemargindonesurpassrate;

        }else if(Number(salemargintargetamount) != Number(0)){
            if(Number(salemargintargetamount) <= Number(salemarginamount)){
                row.salemargindonesurpassrate = 100;
            }else {
                var mindata = Number(salemarginamount) - Number(salemargintargetamount) ;
                var salemargindonesurpassrate = mindata / Math.abs(salemargintargetamount) * Number(100);
                row.salemargindonesurpassrate = salemargindonesurpassrate;
            }
        }else{
            row.salemargindonesurpassrate = "";
        }

        //回笼完成率=回笼金额/回笼目标
        if(Number(withdrawntargetamount) > Number(0) && Number(withdrawnamount) >= Number(0)){
            var withdrawndonerate = withdrawnamount/withdrawntargetamount*Number(100);
            row.withdrawndonerate = withdrawndonerate ;
        }else if(Number(withdrawntargetamount) != Number(0)){

            if(Number(withdrawnamount)<Number(withdrawntargetamount)){
                var withdrawndonerate = (withdrawnamount - withdrawntargetamount)/Math.abs(withdrawntargetamount)*Number(100);
                row.withdrawndonerate = withdrawndonerate;
            }else {
                row.withdrawndonerate = 100;
            }

        }else{
            row.withdrawndonerate = "";
        }
        //回笼目标毛利=回笼目标*回笼毛利率目标
        var withdrawnmargintargetamount = withdrawntargetamount*(writeoffratetarget/Number(100));
        //回笼业绩超率=回笼毛利额/回笼目标毛利
        if(Number(withdrawnmargintargetamount) > Number(0) &&
                Number(writeoffmarginamount)>= Number(0)){

            var withdrawnmargindonesurpassrate = writeoffmarginamount/withdrawnmargintargetamount*Number(100);
            row.withdrawnmargindonesurpassrate = withdrawnmargindonesurpassrate;

        }else if(Number(withdrawnmargintargetamount) != Number(0)){

            if(Number(withdrawnmargintargetamount) > Number(writeoffmarginamount)){
                var withdrawnmargindonesurpassrate = (writeoffmarginamount-withdrawnmargintargetamount)/Math.abs(withdrawnmargintargetamount)*Number(100);
                row.withdrawnmargindonesurpassrate = withdrawnmargindonesurpassrate;
            }else{
                row.withdrawnmargindonesurpassrate = 100;
            }
        }else{
            row.withdrawnmargindonesurpassrate = "" ;
        }

        $salesdeptBrandSWADatagrid.datagrid('updateRow',{index:editIndex, row:row});
        var query = $("#report-query-form-salesdeptBrandWithdrawnAssess").serializeJSON();
        query["rowstr"] = JSON.stringify(row);
        query["billtype"] = "SalesWithdrawnSalesdeptBrand";
        query["businessdate"] = businessdate;
        $.ajax({
            url:'report/target/addSalesWithdrawnAssess.do',
            data:query,
            dataType:'json',
            type:'post',
            success:function(json){
                if(json.flag && json.reportTarget != undefined){
                    salesWithdrawnAssessCountTotalAmountCaseEndEditing(json.reportTarget,$salesdeptBrandSWADatagrid);
                }
            }
        });

    }
    $(function(){
        var tableColumnListJson = $("#report-datagrid-salesdeptBrandWithdrawnAssess").createGridColumnLoad({
            frozenCol : [[
                {field:'idok',checkbox:true,isShow:true}
            ]],
            commonCol : [[
                {field:'salesdept',title:'销售部门',width:80,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.salesdeptname;
                    }
                },
                {field:'brandid',title:'品牌',width:70,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.brandname;
                    }
                },
                {field:'withdrawntargetamount',title:'回笼目标',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(value!=null && value!=0){
                            return formatterMoney(value);
                        }else{
                            return "";
                        }
                    },
                    editor:{
                        type:'numberbox',
                        options:{
                            precision:2
                            //required:true,
                            //min:0
                        }
                    }
                },
                {field:'withdrawnamount',title:'回笼金额',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'withdrawndonerate',title:'回笼完成率',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(value!=null && value!=0){
                            return formatterMoney(value)+"%";
                        }else{
                            return "";
                        }
                    }
                },
                {field:'writeoffmarginamount',title:'回笼毛利额',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'writeoffratetarget',title:'回笼毛利率目标',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(null != value && "" != value){
                            return formatterMoney(value)+"%";
                        }
                    },
                    editor:{
                        type:'numberbox',
                        options:{
                            precision:2,
                            //required:true,
                            min:-100,
                            max:100
                        }
                    }
                },
                {field:'writeoffrate',title:'回笼毛利率',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(null != value && "" != value){
                            return formatterMoney(value)+"%";
                        }
                    }
                },
                {field:'withdrawnmargindonesurpassrate',title:'回笼毛利完成率',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(value!=null && value!=0){
                            return formatterMoney(value)+"%";
                        }else{
                            return "";
                        }
                    }
                },
                {field:'saletargetamount',title:'销售目标',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    },
                    editor:{
                        type:'numberbox',
                        options:{
                            precision:2
                        }
                    }
                },
                {field:'saleamount',title:'销售金额',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'saledonerate',title:'销售完成率',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(value!=null && value!=0){
                            return formatterMoney(value)+"%";
                        }else{
                            return "";
                        }
                    }
                },
                {field:'salemarginamount',title:'销售毛利额',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'marginratetarget',title:'毛利率目标',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(value!=null && value!=0){
                            return formatterMoney(value)+"%";
                        }else{
                            return "";
                        }
                    },
                    editor:{
                        type:'numberbox',
                        options:{
                            precision:2,
                            //required:true,
                            min:-100,
                            max:100
                        }
                    }
                },
                {field:'realrate',title:'实际毛利率',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(value!=null && value!=0){
                            return formatterMoney(value)+"%";
                        }else{
                            return "";
                        }
                    }
                },
                {field:'salemargindonesurpassrate',title:'毛利完成率',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(value!=null && value!=0){
                            return formatterMoney(value)+"%";
                        }else{
                            return "";
                        }
                    }
                }
            ]]
        });

        $("#report-datagrid-salesdeptBrandWithdrawnAssess").datagrid({
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
            toolbar:'#report-toolbar-salesdeptBrandWithdrawnAssess',
            onDblClickRow:function(rowIndex, rowData){
                <security:authorize url="/report/sales/addSaleWithdrawnTargetBySalesdeptBrand.do">
                if(undefined != rowData && undefined != rowData.salesuser){
                    if(endEditing()){
                        editIndex = rowIndex;
                        $(this).datagrid('beginEdit', rowIndex);
                        var ed = $(this).datagrid('getEditor', {index:editIndex,field:'withdrawntargetamount'});
                        if(ed != null){
                            getNumberBoxObject(ed.target).focus();
                            getNumberBoxObject(ed.target).select();
                            editfiled = "withdrawntargetamount";
                        }
                        $(this).datagrid('selectRow', rowIndex);
                    }
                }
                </security:authorize>
            },
            onClickRow:function(rowIndex, rowData){
                if(!endEditing()){
                    if($(this).datagrid('validateRow', editIndex)){
                        $(this).datagrid('endEdit', editIndex);
                        //editIndex = rowIndex ;
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
                    salesWithdrawnAssessCountTotalAmount("salesdeptname",this);
                }
            },
            onCheckAll:function(){
                salesWithdrawnAssessCountTotalAmount("salesdeptname",this);
            },
            onUncheckAll:function(){
                salesWithdrawnAssessCountTotalAmount("salesdeptname",this);
            },
            onCheck:function(){
                salesWithdrawnAssessCountTotalAmount("salesdeptname",this);
            },
            onUncheck:function(){
                salesWithdrawnAssessCountTotalAmount("salesdeptname",this);
            }
        }).datagrid("columnMoving");

        $("#report-brandid-salesdeptBrandWithdrawnAssess").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            width:130,
            singleSelect:false
        });
        //销售部门
        $("#report-salesdept-salesdeptBrandWithdrawnAssess").widget({
            referwid:'RL_T_BASE_DEPARTMENT_SELLER',
            width:120,
            singleSelect:false,
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
                if("withdrawntargetamount" == editfiled){
                    var ed = $salesdeptBrandSWADatagrid.datagrid('getEditor', {index:editIndex,field:'writeoffratetarget'});
                    if(ed != null){
                        getNumberBoxObject(ed.target).focus();
                        getNumberBoxObject(ed.target).select();
                        editfiled = "writeoffratetarget";
                    }
                }else if("writeoffratetarget" == editfiled){
                    var ed = $salesdeptBrandSWADatagrid.datagrid('getEditor', {index:editIndex,field:'saletargetamount'});
                    if(ed != null){
                        getNumberBoxObject(ed.target).focus();
                        getNumberBoxObject(ed.target).select();
                        editfiled = "saletargetamount";
                    }
                }else if("saletargetamount" == editfiled){
                    var ed = $salesdeptBrandSWADatagrid.datagrid('getEditor', {index:editIndex,field:'marginratetarget'});
                    if(ed != null){
                        getNumberBoxObject(ed.target).focus();
                        getNumberBoxObject(ed.target).select();
                        editfiled = "marginratetarget";
                    }
                }else if("marginratetarget" == editfiled){
                    if($salesdeptBrandSWADatagrid.datagrid('validateRow', editIndex)){
                        var index = editIndex;
                        $salesdeptBrandSWADatagrid.datagrid('endEdit', editIndex);
                        computeDataCaseField();
                        editIndex = undefined;
                        editfiled = null;
                        $salesdeptBrandSWADatagrid.datagrid('clearSelections');

                        var row = $salesdeptBrandSWADatagrid.datagrid('getRows')[index+1];
                        if(undefined != row && endEditing()){
                            editIndex = index+1;
                            $salesdeptBrandSWADatagrid.datagrid('beginEdit', editIndex);
                            var ed = $salesdeptBrandSWADatagrid.datagrid('getEditor', {index:editIndex,field:'withdrawntargetamount'});
                            if(ed != null){
                                getNumberBoxObject(ed.target).focus();
                                getNumberBoxObject(ed.target).select();
                                editfiled = "withdrawntargetamount";
                            }
                            $salesdeptBrandSWADatagrid.datagrid('selectRow', editIndex);
                        }
                    }
                }
            }
        });

        //查询
        $("#report-queay-salesdeptBrandWithdrawnAssess").click(function(){
            var queryJSON = $("#report-query-form-salesdeptBrandWithdrawnAssess").serializeJSON();
            editIndex = undefined;
            businessdate = $("#report-businessdate1").val();
            $("#report-datagrid-salesdeptBrandWithdrawnAssess").datagrid({
                //salesdeptbrand表示所有销售部门对应所有品牌（一个销售部门对应所有品牌）的组合记录
                url: 'report/sales/getSalesWithdrawnAssessData.do?groupcols=salesdept,brandid&salesdeptbrand=1',
                pageNumber:1,
                queryParams:queryJSON
            });
        });
        //重置
        $("#report-reload-salesdeptBrandWithdrawnAssess").click(function(){
            $("#report-customeruser-salesdeptBrandWithdrawnAssess").widget("clear");
            $("#report-query-form-salesdeptBrandWithdrawnAssess")[0].reset();
            $("#report-datagrid-salesdeptBrandWithdrawnAssess").datagrid('loadData',{total:0,rows:[]});
            $("#report-brandid-salesdeptBrandWithdrawnAssess").widget("clear");
            $("#report-salesdept-salesdeptBrandWithdrawnAssess").widget("clear");
        });

        //导入
        $("#report-import-salesdeptBrandWithdrawnAssess").Excel('import',{
            url:'report/target/importSalesuserBrandSWAData.do?billtype=SalesWithdrawnSalesdeptBrand',
            type:'importUserdefined',
            importparam:'分销售部门品牌销售回笼考核表导入。销售部门、品牌、日期必填',
            onClose: function(){ //导入成功后窗口关闭时操作，
                $salesdeptBrandSWADatagrid.datagrid('reload');	//更新列表
            }
        });
        //导出
        $("#report-export-salesdeptBrandWithdrawnAssess").Excel('export',{
            queryForm: "#report-query-form-salesdeptBrandWithdrawnAssess", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            type:'exportUserdefined',
            name:'分销售部门品牌销售回笼考核报表',
            url:'report/sales/exportSalesWithdrawnAssessData.do?groupcols=salesdept,brandid&salesdeptbrand=1'
        });
    });
</script>
</body>
</html>
