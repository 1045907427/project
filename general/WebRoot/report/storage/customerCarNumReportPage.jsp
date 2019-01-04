<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>客户装车次数报表</title>
    <%@include file="/include.jsp"%>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>
<body>
    <table id="report-datagrid-customercarnum"></table>
    <div id="report-toolbar-customercarnum" style="padding: 0px">
        <div class="buttonBG">
            <security:authorize url="/report/storage/customercarnumReportExport.do">
                <a href="javaScript:void(0);" id="report-export-customercarnum" class="easyui-linkbutton" iconCls="button-export" plain="true">全局导出</a>
            </security:authorize>
        </div>
        <form action="" id="report-query-form-customercarnum" method="post">
            <table>
                <tr>
                    <td>线路名称：</td>
                    <td><input id="report-lineid-customercarnum" type="text" name="lineid"/></td>
                    <td>客户名称：</td>
                    <td><input id="report-customerid-customercarnum" type="text" name="customerid"/></td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="report-queay-customercarnum" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="report-reload-customercarnum" class="button-qr">重置</a>

                    </td>
                </tr>
            </table>
        </form>
    </div>
    <script type="text/javascript">
        $(function(){

            $("#report-export-customercarnum").click(function(){
                //封装查询条件
                var objecr  = $("#report-datagrid-customercarnum").datagrid("options");
                var queryParam = objecr.queryParams;
                if(null != objecr.sortName && null != objecr.sortOrder ){
                    queryParam["sort"] = objecr.sortName;
                    queryParam["order"] = objecr.sortOrder;
                }
                var queryParam = JSON.stringify(queryParam);
                var url = "report/storage/exportCustomerCarNumReportData.do";
                exportByAnalyse(queryParam,"客户装车次数统计报表","report-datagrid-customercarnum",url);
            });

            var tableColumnJson = $("#report-datagrid-customercarnum").createGridColumnLoad({
                frozenCol : [[
                    {field:'idok',checkbox:true,isShow:true}
                ]],
                commonCol : [[
                    {field:'customerid',title:'客户编号',sortable:true,width:60},
                    {field:'customername',title:'客户名称',width:210},
                    {field:'pcustomerid',title:'总店编码',sortable:true,width:60,hidden:true},
                    {field:'pcustomername',title:'总店名称',width:60,hidden:true},
                    {field:'salesarea',title:'所属区域',sortable:true,width:60,
                        formatter:function(value,rowData,rowIndex){
                            return rowData.salesareaname;
                        }
                    },
                    {field:'salesdept',title:'所属部门',sortable:true,width:70,hidden:true,
                        formatter:function(value,rowData,rowIndex){
                            return rowData.salesdeptname;
                        }
                    },
                    {field:'lineid',title:'所属线路',sortable:true,width:70,
                        formatter:function(value,rowData,rowIndex){
                            return rowData.linename;
                        }
                    },
                    {field:'carnum',title:'装车次数',sortable:true,width:60}
                ]]
            });
            $("#report-datagrid-customercarnum").datagrid({
                authority:tableColumnJson,
                frozenColumns: tableColumnJson.frozen,
                columns:tableColumnJson.common,
                method:'post',
                title:'',
                fit:true,
                rownumbers:true,
                pagination:true,
                showFooter: true,
                singleSelect:false,
                checkOnSelect:true,
                selectOnCheck:true,
                pageSize:100,
                toolbar:'#report-toolbar-customercarnum'
            }).datagrid("columnMoving");

            //线路
            $("#report-lineid-customercarnum").widget({
                referwid:'RL_T_BASE_LOGISTICS_LINE',
                width:120,
                singleSelect:true
            });
            //客户
            $("#report-customerid-customercarnum").widget({
                referwid:'RL_T_BASE_SALES_CUSTOMER',
                width:150,
                singleSelect:true
            });

            //查询
            $("#report-queay-customercarnum").click(function(){
                var queryJSON = $("#report-query-form-customercarnum").serializeJSON();
                $("#report-datagrid-customercarnum").datagrid({
                    url: 'report/storage/getCustomerCarNumReportData.do',
                    pageNumber:1,
                    queryParams:queryJSON
                });
            });
            //重置
            $("#report-reload-customercarnum").click(function(){
                $("#report-lineid-customercarnum").widget("clear");
                $("#report-customerid-customercarnum").widget("clear");
                $("#report-query-form-customercarnum").form("reset");
                $("#report-datagrid-customercarnum").datagrid('loadData',{total:0,rows:[]});
            });
            //导出
//            $("#report-export-customercarnum").Excel('export',{
//                queryForm: "#report-query-form-customercarnum", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
//                type:'exportUserdefined',
//                name:'客户装车次数统计报表',
//                url:'report/storage/exportCustomerCarNumReportData.do'
//            });
        });

    </script>
</body>
</html>
