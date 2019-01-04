<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>客户总应收款报表表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>
<body>
<table id="report-datagrid-customerTotalReceipt"></table>
<div id="report-toolbar-customerTotalReceipt" style="padding: 0px">
  <div class="buttonBG">
    <security:authorize url="/report/finance/customerTotalReceiptExport.do">
      <a href="javaScript:void(0);" id="report-buttons-customerTotalReceiptPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
    </security:authorize>
  </div>
  <form action="" id="report-query-form-customerTotalReceipt" method="post">
    <table class="querytable">
      <tr>
        <td>业务日期:</td>
        <td><input type="text" id="report-query-businessdate1" name="businessdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
        <td>客户业务员:</td>
        <td><input type="text" id="report-query-salesuser" name="salesuser"/></td>
        <td>销售部门:</td>
        <td><input type="text" id="report-query-salesdept" name="salesdept"/></td>
      </tr>
      <tr>
        <td>客户名称:</td>
        <td><input type="text" id="report-query-customerid" name="customerid"/></td>
        <td>总店名称:</td>
        <td><input type="text" id="report-query-pcustomerid" name="pcustomerid"/></td>
        <td>客户分类:</td>
        <td><input type="text" id="report-query-customersort" name="customersort"/></td>
      </tr>
      <tr>
        <td colspan="4"></td>
        <td colspan="2">
          <a href="javaScript:void(0);" id="report-queay-customerTotalReceipt" class="button-qr">查询</a>
          <a href="javaScript:void(0);" id="report-reload-customerTotalReceipt" class="button-qr">重置</a>
        </td>
      </tr>
    </table>
  </form>
</div>
<div id="report-customerTotalReceipt-detail-dialog"></div>
<script type="text/javascript">
  var initQueryJSON = $("#report-query-form-customerTotalReceipt").serializeJSON();
  $(function(){
    $("#report-datagrid-customerTotalReceipt").datagrid({
      columns:[[
        {field:'customerid',title:'客户编码',width:60},
        {field:'customername',title:'客户名称',width:210},
        {field:'pcustomerid',title:'总店名称',width:60,sortable:true,hidden:true,
          formatter:function(value,rowData,rowIndex){
            if(rowData.customerid!=rowData.pcustomerid){
              return rowData.pcustomername;
            }else{
              return "";
            }
          }
        },
        {field:'salesdept',title:'销售部门',sortable:true,width:80,
          formatter:function(value,rowData,rowIndex){
            return rowData.salesdeptname;
          }
        },
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
      toolbar:'#report-toolbar-customerTotalReceipt',
      onDblClickRow:function(rowIndex, rowData){
        var customerid = rowData.customerid;
        var customername = rowData.customername;
        var businessdate1 = $("#report-query-businessdate1").val();
        var businessdate2 = $("#report-query-businessdate2").val();
        var url = 'report/finance/showCustomerTotalReceiptDetailReportPage.do?customerid='+customerid+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2+'&customername='+customername;
        $("#report-customerTotalReceipt-detail-dialog").dialog({
          title:'按客户:['+rowData.customername+']总应收款明细',
          width:800,
          height:400,
          closed:true,
          modal:true,
          maximizable:true,
          cache:false,
          resizable:true,
          maximized:true,
          href: url
        });
        $("#report-customerTotalReceipt-detail-dialog").dialog("open");
      }
    });
    $("#report-query-customerid").widget({
      referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
      width:225,
      singleSelect:true
    });
    $("#report-query-salesuser").widget({
      referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
      width:150,
      singleSelect:true
    });
    $("#report-query-salesdept").widget({
      referwid:'RL_T_BASE_DEPARTMENT_SELLER',
      width:150,
      singleSelect:true
    });
    $("#report-query-pcustomerid").widget({
      referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
      width:150,
      singleSelect:true
    });
    $("#report-query-customersort").widget({
      referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
      width:150,
      singleSelect:false,
      onlyLeafCheck:true
    });

    //查询
    $("#report-queay-customerTotalReceipt").click(function(){
      var queryJSON = $("#report-query-form-customerTotalReceipt").serializeJSON();
      $("#report-datagrid-customerTotalReceipt").datagrid({
        url: 'report/finance/getCustomerTotalReceiptReportList.do',
        pageNumber:1,
        queryParams:queryJSON
      });
    });
    //重置
    $("#report-reload-customerTotalReceipt").click(function(){
      $("#report-query-customerid").widget("clear");
      $("#report-query-salesuser").widget("clear");
      $("#report-query-salesdept").widget('clear');
      $("#report-query-pcustomerid").widget('clear');
      $("#report-query-customersort").widget('clear');
      $("#report-query-form-customerTotalReceipt").form("reset");
      var queryJSON = $("#report-query-form-customerTotalReceipt").serializeJSON();
      $("#report-datagrid-customerTotalReceipt").datagrid('loadData',{total:0,rows:[]});
    });

    $("#report-buttons-customerTotalReceiptPage").Excel('export',{
      queryForm: "#report-query-form-customerTotalReceipt", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
      type:'exportUserdefined',
      name:'客户总应收款报表',
      url:'report/finance/exportCustomerTotalReceiptReportList.do'
    });
  });

</script>
</body>
</html>
