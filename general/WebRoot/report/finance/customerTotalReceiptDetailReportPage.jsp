<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>客户总应收款明细</title>
</head>
<body>
<form action="" id="report-query-form-customerTotalReceiptDetail" method="post">
  <input type="hidden" name="customerid" value="${customerid}"/>
  <input type="hidden" name="businessdate1" value="${businessdate1}"/>
  <input type="hidden" name="businessdate2" value="${businessdate2}"/>
</form>
<table id="report-datagrid-customerTotalReceiptDetail"></table>
<div id="report-toolbar-customerTotalReceiptDetail" class="buttonBG">
  <security:authorize url="/report/finance/customerTotalReceiptExport.do">
    <a href="javaScript:void(0);" id="report-buttons-customerTotalReceiptDetail" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
  </security:authorize>
</div>
<script type="text/javascript">
  var initQueryJSON = $("#report-query-form-customerTotalReceiptDetail").serializeJSON();
  $(function(){
    $("#report-datagrid-customerTotalReceiptDetail").datagrid({
      columns:[[
        {field:'businessdate',title:'业务日期',width:80},
        {field:'billtype',title:'单据类型',width:90,
          formatter:function(value,rowData,rowIndex){
            return rowData.billtypename;
          }
        },
        {field:'id',title:'单据编号',width:130},
        {field:'orderid',title:'订单编号',width:130},
        {field:'inittotalreceiptamount',title:'期初总应收额',align:'right',resizable:true,sortable:true,
          formatter:function(value,rowData,rowIndex){
            return formatterMoney(value);
          }
        },
        {field:'saleamount',title:'销售金额',align:'right',resizable:true,sortable:true,
          formatter:function(value,rowData,rowIndex){
            return formatterMoney(value);
          }
        },
        {field:'tailamount',title:'尾差金额',align:'right',resizable:true,sortable:true,
          formatter:function(value,rowData,rowIndex){
            return formatterMoney(value);
          }
        },
        {field:'receiptamount',title:'收款金额',align:'right',resizable:true,sortable:true,
          formatter:function(value,rowData,rowIndex){
            return formatterMoney(value);
          }
        },
        {field:'totalreceiptamount',title:'期末总应收额',align:'right',resizable:true,isShow:true,sortable:true,
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
      pageSize:100,
      pagination:true,
      showFooter: true,
      url:'report/finance/getCustomerTotalReceiptReportDetailList.do',
      queryParams:initQueryJSON,
      toolbar:"#report-toolbar-customerTotalReceiptDetail"
    });

    $("#report-buttons-customerTotalReceiptDetail").Excel('export',{
      queryForm: "#report-query-form-customerTotalReceiptDetail",
      type:'exportUserdefined',
      name:'客户[${customername}]应收款明细报表',
      url:'report/finance/exportCustomerTotalReceiptDetailData.do'
    });
  });
</script>
</body>
</html>
