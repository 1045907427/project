<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <title>应收款余额明细报表</title>
</head>
<body>
<form action="" id="report-query-form-receiptAmountDetail" method="post">
  <input type="hidden" name="customerid" value="${customerid }"/>
  <input type="hidden" name="businessdate1" value="${businessdate1 }"/>
  <input type="hidden" name="businessdate2" value="${businessdate2 }"/>
  <input type="hidden" name="salesuser" value="${param.salesuser }"/>
  <input type="hidden" name="pcustomerid" value="${param.pcustomerid }"/>
</form>
<table id="report-datagrid-receiptAmountDetail"></table>
<div id="report-toolbar-receiptAmountDetail" class="buttonBG">
  <security:authorize url="/report/finance/receiptAmountReportExport.do">
    <a href="javaScript:void(0);" id="report-buttons-receiptAmountDetail" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
  </security:authorize>
</div>
<script type="text/javascript">
  var initQueryJSON = $("#report-query-form-receiptAmountDetail").serializeJSON();
  $(function(){
    $("#report-datagrid-receiptAmountDetail").datagrid({
      columns:[[
        {field:'businessdate',title:'业务日期',width:80},
        {field:'billtype',title:'单据类型',width:90,
          formatter:function(value,rowData,rowIndex){
            return rowData.billtypename;
          }
        },
        {field:'id',title:'单据编号',width:130},
        {field:'initunwithdrawnamount',title:'期初应收',resizable:true,sortable:true,align:'right',
          formatter:function(value,rowData,rowIndex){
            return formatterMoney(value);
          }
        },
        {field:'salesamount',title:'本期销售',resizable:true,sortable:true,align:'right',
          formatter:function(value,rowData,rowIndex){
            return formatterMoney(value);
          }
        },
        {field:'withdrawnamount',title:'本期回笼',resizable:true,sortable:true,align:'right',
          formatter:function(value,rowData,rowIndex){
            return formatterMoney(value);
          }
        },
        {field:'balanceamount',title:'期末应收余额',resizable:true,align:'right',sortable:true,
          formatter:function(value,rowData,rowIndex){
            return formatterMoney(value);
          }
        },
        {field:'remark',title:'备注',align:'right',width:150}
      ]],
      method:'post',
      fit:true,
      rownumbers:true,
      singleSelect:true,
      pageSize:500,
      pagination:true,
      showFooter: true,
      url:'report/finance/getReceiptAmountDetailData.do',
      queryParams:initQueryJSON,
      toolbar:"#report-toolbar-receiptAmountDetail"
    });

    $("#report-buttons-receiptAmountDetail").Excel('export',{
      queryForm: "#report-query-form-receiptAmountDetail",
      type:'exportUserdefined',
      name:'客户[${customername}]应收余额明细统计报表',
      url:'report/finance/exportReceiptAmountDetailData.do'
    });
  });
</script>
</body>
</html>
