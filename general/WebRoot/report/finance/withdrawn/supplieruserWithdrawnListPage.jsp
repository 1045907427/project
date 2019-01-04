<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>分厂家业务员资金回笼报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>

<body>
<table id="report-datagrid-supplieruserWithdrawn"></table>
<div id="report-toolbar-supplieruserWithdrawn" style="padding: 0px">
    <div class="buttonBG">
        <security:authorize url="/report/finance/supplieruserWithdrawnListExport.do">
            <a href="javaScript:void(0);" id="report-export-supplieruserWithdrawn" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
        </security:authorize>
        <div id="dialog-autoexport"></div>
    </div>
    <form action="" id="report-query-form-supplieruserWithdrawn" method="post">
        <table class="querytable">

            <tr>
                <td>业务日期:</td>
                <td class="tdinput"><input id="report-query-businessdate1" type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input id="report-query-businessdate2" type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                <td>厂家业务员:</td>
                <td class="tdinput"><input type="text" id="report-query-supplieruser" name="supplieruser"/></td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="report-queay-supplieruserWithdrawn" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-supplieruserWithdrawn" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="report-dialog-supplieruserWithdrawnDetail"></div>
<script type="text/javascript">
    var SR_footerobject  = null;
    $(function(){

        $("#report-export-supplieruserWithdrawn").click(function(){
            var queryJSON = $("#report-query-form-supplieruserWithdrawn").serializeJSON();
            //获取排序规则
            var objecr  = $("#report-datagrid-supplieruserWithdrawn").datagrid("options");
            if(null != objecr.sortName && null != objecr.sortOrder ){
                queryJSON["sort"] = objecr.sortName;
                queryJSON["order"] = objecr.sortOrder;
            }
            //存入导出时的参数
            queryJSON["groupcols"] = "supplieruser";
            var queryParam = JSON.stringify(queryJSON);
            var url = "report/finance/exportBaseFinanceDrawnData.do";
            exportByAnalyse(queryParam,"分厂家业务员资金回笼情况报表","report-datagrid-supplieruserWithdrawn",url);
        });

        var tableColumnSupplieruserListJson = $("#report-datagrid-supplieruserWithdrawn").createGridColumnLoad({
            frozenCol : [[{field:'idok',checkbox:true,isShow:true}]],
            commonCol : [[
                {field:'supplieruser',title:'厂家业务员',width:70,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.supplierusername;
                    }
                },
                {field:'withdrawnamount',title:'回笼金额',align:'right',resizable:true,isShow:true,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                }
                <c:if test="${map.costwriteoffamount == 'costwriteoffamount'}">
                ,
                {field:'costwriteoffamount',title:'回笼成本',align:'right',resizable:true,isShow:true,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                }
                </c:if>
                <c:if test="${map.writeoffmarginamount == 'writeoffmarginamount'}">
                ,
                {field:'writeoffmarginamount',title:'回笼毛利额',align:'right',resizable:true,isShow:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                }
                </c:if>
                <c:if test="${map.writeoffrate == 'writeoffrate'}">
                ,
                {field:'writeoffrate',title:'回笼毛利率',align:'right',width:80,isShow:true,
                    formatter:function(value,rowData,rowIndex){
                        if(null != value && "" != value){
                            return formatterMoney(value)+"%";
                        }
                    }
                }
                </c:if>
            ]]
        });

        $("#report-datagrid-supplieruserWithdrawn").datagrid({
            authority:tableColumnSupplieruserListJson,
            frozenColumns: tableColumnSupplieruserListJson.frozen,
            columns:tableColumnSupplieruserListJson.common,
            method:'post',
            title:'',
            fit:true,
            sortName:'supplieruser',
            sortOrder:'asc',
            rownumbers:true,
            pagination:true,
            showFooter: true,
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            pageSize:100,
            toolbar:'#report-toolbar-supplieruserWithdrawn',
            onDblClickRow:function(rowIndex, rowData){
                var supplieruser = rowData.supplieruser;
                var supplierusername = rowData.supplierusername;
                var businessdate1 = $("#report-query-businessdate1").val();
                var businessdate2 = $("#report-query-businessdate2").val();
                var url = 'report/finance/showSupplieruserWithdrawnDetailListPage.do?supplieruser='+supplieruser+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2+'&supplierusername='+supplierusername;
                $('<div id="report-dialog-supplieruserWithdrawnDetail1"></div>').appendTo('#report-dialog-supplieruserWithdrawnDetail');
                $("#report-dialog-supplieruserWithdrawnDetail1").dialog({
                    title: '按厂家业务员：['+supplierusername+']分品牌部门统计',
                    width:800,
                    height:400,
                    closed:true,
                    modal:true,
                    maximizable:true,
                    cache:false,
                    resizable:true,
                    maximized:true,
                    href: url,
                    onClose:function(){
                        $('#report-dialog-supplieruserWithdrawnDetail1').dialog("destroy");
                    }
                });
                $("#report-dialog-supplieruserWithdrawnDetail1").dialog("open");
            }
        }).datagrid("columnMoving");

        //厂家业务员
        $("#report-query-supplieruser").widget({
            referwid:'RL_T_BASE_PERSONNEL_SUPPLIER',
            width:130,
            singleSelect:true
        });

        //查询
        $("#report-queay-supplieruserWithdrawn").click(function(){
            var queryJSON = $("#report-query-form-supplieruserWithdrawn").serializeJSON();
            $("#report-datagrid-supplieruserWithdrawn").datagrid({
                url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=supplieruser',
                pageNumber:1,
                queryParams:queryJSON
            });
        });
        //重置
        $("#report-reload-supplieruserWithdrawn").click(function(){
            $("#report-query-supplieruser").widget('clear');
            $("#report-query-form-supplieruserWithdrawn")[0].reset();
            $("#report-datagrid-supplieruserWithdrawn").datagrid('loadData',{total:0,rows:[]});
        });

    });
</script>
</body>
</html>
