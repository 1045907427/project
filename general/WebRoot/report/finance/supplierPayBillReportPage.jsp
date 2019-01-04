<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>供应商对应付账报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>
<body>
    <table id="report-datagrid-supplierPayBill"></table>
    <div id="report-toolbar-supplierPayBill" style="padding: 0px">
        <div class="buttonBG">
            <security:authorize url="/report/finance/supplierPayBillExport.do">
                <a href="javaScript:void(0);" id="report-buttons-supplierPayBill" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
            </security:authorize>
        </div>
        <form action="" id="report-query-form-supplierPayBill" method="post">
            <table>
                <tr>
                    <td>业务日期:</td>
                    <td style="padding-right: 30px"><input type="text" name="businessdate1" id="report-query-businessdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" value="${today}"/> 到 <input type="text" name="businessdate2" id="report-query-businessdate2"  class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" value="${today}"/></td>
                    <td>采购部门:</td>
                    <td><input type="text" id="report-query-buydeptid" name="buydeptid"/></td>
                </tr>
                <tr>
                    <td>供 应 商:</td>
                    <td><input type="text" id="report-query-supplierid" name="supplierid"/></td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="report-queay-supplierPayBill" class="button-qr" plain="true" title="[Alt+Q]查询">查询</a>
                        <a href="javaScript:void(0);" id="report-reload-supplierPayBill" class="button-qr" iconCls="icon-reload" plain="true" title="[Alt+R]重置">重置</a>

                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div id="report-dialog-supplierPayBillDetail"></div>
    <script type="text/javascript">
        $(function(){
            $("#report-datagrid-supplierPayBill").datagrid({
                columns:[[
                    {field:'supplierid',title:'供应商编号',width:80},
                    {field:'suppliername',title:'供应商名称',width:200},
                    {field:'buydeptid',title:'采购部门',width:80,
                        formatter:function(value,rowData,rowIndex){
                            return rowData.buydeptname;
                        }
                    },
                    {field:'initbuyamount',title:'期初金额',resizable:true,sortable:true,align:'right',
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    },
                    {field:'payableamount',title:'采购金额',resizable:true,sortable:true,align:'right',
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    },
                    {field:'payamount',title:'付款金额',resizable:true,sortable:true,align:'right',
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    },
                    {field:'pushbalanceamount',title:'冲差金额',resizable:true,align:'right',sortable:true,
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    },
                    {field:'balanceamount',title:'余额',resizable:true,align:'right',sortable:true,
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
                singleSelect:true,
                pageSize:100,
                toolbar:'#report-toolbar-supplierPayBill',
                onDblClickRow:function(rowIndex, rowData){
                    var businessdate1 = $("#report-query-businessdate1").val();
                    var businessdate2 = $("#report-query-businessdate2").val();
                    $("#report-dialog-supplierPayBillDetail").dialog({
                        title:'按供应商:['+rowData.suppliername+']对应付账明细',
                        width:800,
                        height:400,
                        closed:true,
                        modal:true,
                        maximizable:true,
                        cache:false,
                        resizable:true,
                        maximized:true,
                        href: 'report/finance/showSupplierPayBillDetailReportPage.do',
                        queryParams:{supplierid:rowData.supplierid,suppliername:rowData.suppliername,businessdate1:businessdate1,businessdate2:businessdate2}
                    });
                    $("#report-dialog-supplierPayBillDetail").dialog("open");
                }
            });

            $("#report-query-supplierid").widget({
                referwid:'RL_T_BASE_BUY_SUPPLIER',
                width:225,
                singleSelect:true
            });
            $("#report-query-buydeptid").widget({
                referwid:'RL_T_BASE_DEPARTMENT_BUYER',
                width:145,
                singleSelect:true
            });

            //查询
            $("#report-queay-supplierPayBill").click(function(){
                var queryJSON = $("#report-query-form-supplierPayBill").serializeJSON();
                $("#report-datagrid-supplierPayBill").datagrid({
                    url: 'report/finance/getSupplierPayBillData.do',
                    queryParams:queryJSON,
                    pageNumber:1
                });
            });
            //重置
            $("#report-reload-supplierPayBill").click(function(){
                $("#report-query-supplierid").widget("clear");
                $("#report-query-buydeptid").widget("clear");
                $("#report-query-form-supplierPayBill")[0].reset();
                $("#report-datagrid-supplierPayBill").datagrid('loadData',{total:0,rows:[]});
            });

            $("#report-buttons-supplierPayBill").Excel('export',{
                queryForm: "#report-query-form-supplierPayBill",
                type:'exportUserdefined',
                name:'分供应商对应付账统计报表',
                url:'report/finance/exportSupplierPayBillData.do'
            });
        });
    </script>
</body>
</html>
