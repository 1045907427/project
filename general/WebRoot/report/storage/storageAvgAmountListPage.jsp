<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>库存平均金额报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>
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
<body>
<table id="report-datagrid-avgAmount"></table>
<div id="report-toolbar-avgAmount" style="padding: 0px">
    <div class="buttonBG">
        <security:authorize url="/report/storage/avgAmountExport.do">
            <a href="javaScript:void(0);" id="report-buttons-avgAmountPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
        </security:authorize>
    </div>
    <form action="" id="report-query-form-avgAmount" method="post">
        <table>

            <tr>
                <td>日期:</td>
                <td><input type="text" name="businessdate1" value="${firstDay }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                <td>品牌部门:</td>
                <td><input type="text" id="report-query-branddept" name="branddept"/></td>
                <td>供应商:</td>
                <td><input type="text" id="report-query-supplierid" style="width: 220px;" name="supplierid"/></td>
            </tr>
            <tr>
                <td>商品:</td>
                <td><input type="text" id="report-query-goodsid" name="goodsid" style="width:225px;"/></td>
                <td>品牌名称:</td>
                <td><input type="text" id="report-query-brandid" name="brandid"/></td>
            </tr>
            <tr>
                <td>小计列：</td>
                <td>
                    <input type="checkbox" class="groupcols checkbox1" value="goodsid" id="goodsid"/>
                    <label class="divtext" for="goodsid">商品</label>
                    <input type="checkbox" class="groupcols checkbox1" value="brandid" id="brandid"/>
                    <label class="divtext" for="brandid">品牌</label>
                    <input type="checkbox" class="groupcols checkbox1" value="branddept" id="branddept"/>
                    <label class="divtext" for="branddept">品牌部门</label>
                    <input type="checkbox" class="groupcols checkbox1" value="supplierid" id="supplierid"/>
                    <label class="divtext" for="supplierid">供应商</label>
                    <input id="report-query-groupcols" type="hidden" name="groupcols"/>
                </td>
                <td colspan="2"></td>
                <td style="padding-left: 60px;" colspan="4">
                    <a href="javaScript:void(0);" id="report-queay-avgAmount" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-avgAmount" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#report-query-form-avgAmount").serializeJSON();
    var checkListJson = $("#report-datagrid-avgAmount").createGridColumnLoad({
        commonCol : [[
            {field:'goodsid',title:'商品编码',width:60},
            {field:'goodsname',title:'商品名称',width:200},
            {field:'barcode',title:'条形码',sortable:true,width:90},
            {field:'existingnum',title:'数量',width:90,
                formatter:function(value,rowData,rowIndex){
                    return formatterBigNumNoLen(value);
                }
            },
            {field:'auxnumdetail',title:'箱数',width:90},
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
            {field:'storageamount',title:'实际库存平均金额',resizable:true,sortable:true,align:'right',
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            },
            {field:'checkstorageamount',title:'库存平均金额',resizable:true,sortable:true,align:'right',
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            }
        ]]
    });
    $(function(){
        $(".groupcols").click(function(){
            var cols = "";
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
        $("#report-datagrid-avgAmount").datagrid({
            authority:checkListJson,
            frozenColumns: checkListJson.frozen,
            columns:checkListJson.common,
            method:'post',
            title:'',
            fit:true,
            rownumbers:true,
            pagination:true,
            idField:'goodsid',
            showFooter: true,
            pageSize:100,
            singleSelect:true,
            toolbar:'#report-toolbar-avgAmount'
        }).datagrid("columnMoving");
        $("#report-query-goodsid").goodsWidget();
        $("#report-query-brandid").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            width:120,
            singleSelect:true
        });
        $("#report-query-branddept").widget({
            referwid:'RL_T_BASE_DEPARTMENT_BUYER',
            width:120,
            singleSelect:true
        });
        $("#report-query-supplierid").supplierWidget();
        //回车事件
        controlQueryAndResetByKey("report-queay-avgAmount","report-reload-avgAmount");

        //查询
        $("#report-queay-avgAmount").click(function(){
            setColumn();
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#report-query-form-avgAmount").serializeJSON();
            $("#report-datagrid-avgAmount").datagrid({
                url: 'report/storage/showStorageAvgAmountData.do',
                pageNumber:1,
                queryParams:queryJSON
            });
        });
        //重置
        $("#report-reload-avgAmount").click(function(){
            $("#report-query-goodsid").goodsWidget("clear");
            $("#report-query-brandid").widget("clear");
            $("#report-query-supplierid").supplierWidget("clear");
            $("#report-query-form-avgAmount").form("reset");
            var queryJSON = $("#report-query-form-avgAmount").serializeJSON();
            $("#report-datagrid-avgAmount").datagrid('loadData',{total:0,rows:[]});
        });

        $("#report-buttons-avgAmountPage").Excel('export',{
            queryForm: "#report-query-form-avgAmount", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            type:'exportUserdefined',
            name:'库存平均金额统计表',
            url:'report/storage/exportStorageAvgAmountData.do'
        });
    });
    var $datagrid = $("#report-datagrid-avgAmount");
    function setColumn(){
        var cols = $("#report-query-groupcols").val();
        if(cols!=""){
            $datagrid.datagrid('hideColumn', "goodsid");
            $datagrid.datagrid('hideColumn', "barcode");
            $datagrid.datagrid('hideColumn', "goodsname");
            $datagrid.datagrid('hideColumn', "brandid");
            $datagrid.datagrid('hideColumn', "branddept");
            $datagrid.datagrid('hideColumn', "supplierid");
            $datagrid.datagrid('hideColumn', "suppliername");
        }
        else{
            $datagrid.datagrid('showColumn', "goodsid");
            $datagrid.datagrid('hideColumn', "barcode");
            $datagrid.datagrid('showColumn', "goodsname");
            $datagrid.datagrid('showColumn', "brandid");
            $datagrid.datagrid('showColumn', "branddept");
            $datagrid.datagrid('showColumn', "supplierid");
            $datagrid.datagrid('hideColumn', "suppliername");
        }
        var colarr = cols.split(",");
        for(var i=0;i<colarr.length;i++){
            var col = colarr[i];
            if(col=="goodsid"){
                $datagrid.datagrid('showColumn', "goodsid");
                $datagrid.datagrid('showColumn', "barcode");
                $datagrid.datagrid('showColumn', "goodsname");
                $datagrid.datagrid('showColumn', "brandid");
                $datagrid.datagrid('showColumn', "branddept");
                $datagrid.datagrid('showColumn', "supplierid");
                $datagrid.datagrid('showColumn', "suppliername");
                $datagrid.datagrid('showColumn', "existingnum");
                $datagrid.datagrid('showColumn', "auxnumdetail");
            }else if(col=="brandid"){
                $datagrid.datagrid('showColumn', "brandid");
                $datagrid.datagrid('showColumn', "branddept");
                $datagrid.datagrid('showColumn', "supplierid");
                $datagrid.datagrid('showColumn', "suppliername");
                $datagrid.datagrid('hideColumn', "existingnum");
                $datagrid.datagrid('hideColumn', "auxnumdetail");
            }else if(col=="branddept"){
                $datagrid.datagrid('showColumn', "branddept");
                $datagrid.datagrid('hideColumn', "existingnum");
                $datagrid.datagrid('hideColumn', "auxnumdetail");
            }else if(col=="supplierid"){
                $datagrid.datagrid('showColumn', "branddept");
                $datagrid.datagrid('showColumn', "supplierid");
                $datagrid.datagrid('showColumn', "suppliername");
                $datagrid.datagrid('hideColumn', "existingnum");
                $datagrid.datagrid('hideColumn', "auxnumdetail");
            }
        }
    }
</script>
</body>
</html>
