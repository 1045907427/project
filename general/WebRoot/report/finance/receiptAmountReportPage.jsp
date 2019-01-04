<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <title>应收款余额报表</title>
  <%@include file="/include.jsp" %>
  <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>
<body>
<table id="report-datagrid-receiptAmountReport"></table>
<div id="report-toolbar-receiptAmountReport" style="padding: 0px">
  <div class="buttonBG">
    <security:authorize url="/report/finance/receiptAmountReportExport.do">
      <a href="javaScript:void(0);" id="report-buttons-receiptAmountReport" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
    </security:authorize>
  </div>
  <form action="" id="report-query-form-receiptAmountReport" method="post">
    <table>
      <tr>
        <td>业务日期:</td>
        <td style="padding-right: 30px"><input type="text" name="businessdate1" id="report-query-businessdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" value="${today}"/> 到 <input type="text" name="businessdate2" id="report-query-businessdate2"  class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" value="${today}"/></td>
        <td>销售区域:</td>
        <td><input type="text" id="report-query-salesarea" name="salesarea"/></td>
        <td>销售部门:</td>
        <td><input type="text" id="report-query-salesdept" name="salesdept"/></td>
      </tr>
      <tr>
        <td>客户:</td>
        <td><input type="text" id="report-query-customerid" name="customerid" style="width: 220px;"/></td>
        <td>客户业务员:</td>
        <td><input type="text" id="report-query-salesuser" name="salesuser"/></td>
        <td>总店:</td>
        <td><input type="text" id="report-query-pcustomerid" name="pcustomerid" style="width: 145px;"/></td>
      </tr>
      <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td colspan="2">
          <a href="javaScript:void(0);" id="report-query-receiptAmountReport" class="button-qr" plain="true" title="查询">查询</a>
          <a href="javaScript:void(0);" id="report-reload-receiptAmountReport" class="button-qr" plain="true" title="重置">重置</a>
        </td>
      </tr>
    </table>
  </form>
</div>
<div id="report-dialog-receiptAmountDetail"></div>
<script type="text/javascript">
  $(function(){
    $("#report-datagrid-receiptAmountReport").datagrid({
      columns:[[
        {field:'customerid',title:'客户编号',width:80},
        {field:'customername',title:'客户名称',width:200},
        {field:'salesdept',title:'销售部门',width:80,hidden:true,
          formatter:function(value,rowData,rowIndex){
            return rowData.salesdeptname;
          }
        },
        {field:'salesarea',title:'销售区域',width:80,hidden:true,
          formatter:function(value,rowData,rowIndex){
            return rowData.salesareaname;
          }
        },
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
        {field:'customeramount',title:'客户余额',resizable:true,align:'right',sortable:true,
          formatter:function(value,rowData,rowIndex){
            return formatterMoney(value);
          }
        },
        {field:'surplusamount',title:'结余金额',align:'right',resizable:true,
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
      toolbar:'#report-toolbar-receiptAmountReport',
      onDblClickRow:function(rowIndex, rowData){
        var customerid = rowData.customerid;
        var customername = rowData.customername;
        var businessdate1 = $("#report-query-businessdate1").val();
        var businessdate2 = $("#report-query-businessdate2").val();
          var salesuser = $("#report-datagrid-receiptAmountReport").datagrid('options').queryParams.salesuser;
          var pcustomerid = $("#report-datagrid-receiptAmountReport").datagrid('options').queryParams.pcustomerid;
        var url = 'report/finance/showReceiptAmountDetailReportPage.do?customerid='+customerid+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2+'&customername='+customername;
        $('<div id="report-dialog-receiptAmountDetail1"></div>').appendTo('#report-dialog-receiptAmountDetail');
        $("#report-dialog-receiptAmountDetail1").dialog({
          title:'按客户:['+rowData.customername+']应收余额明细',
          width:800,
          height:400,
          closed:true,
          modal:true,
          maximizable:true,
          cache:false,
          resizable:true,
          maximized:true,
          href: url,
            queryParams : {
                salesuser: salesuser,
                pcustomerid: pcustomerid
            },
          onClose:function(){
            $('#report-dialog-receiptAmountDetail1').dialog("destroy");
          }
        });
        $("#report-dialog-receiptAmountDetail1").dialog("open");
      }
    });

    $("#report-query-customerid").widget({
      referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
      width:220,
      singleSelect:true
    });
    $("#report-query-salesarea").widget({
      referwid:'RT_T_BASE_SALES_AREA',
      width:145,
      singleSelect:false
    });
    $("#report-query-salesdept").widget({
      referwid:'RL_T_BASE_DEPARTMENT_SELLER',
      width:145,
      singleSelect:false
    });
      $("#report-query-salesuser").widget({
          referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
          width:145,
          singleSelect:false
      });
      $("#report-query-pcustomerid").widget({
          referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
          width:145,
          singleSelect:false
      });
    //查询
    $("#report-query-receiptAmountReport").click(function(){
      var queryJSON = $("#report-query-form-receiptAmountReport").serializeJSON();
      $("#report-datagrid-receiptAmountReport").datagrid({
        url: 'report/finance/getReceiptAmountReportData.do?groupcols=customerid',
        queryParams:queryJSON,
        pageNumber:1
      });
    });
    //重置
    $("#report-reload-receiptAmountReport").click(function(){
      $("#report-query-customerid").widget("clear");
      $("#report-query-salesarea").widget("clear");
      $("#report-query-salesdept").widget("clear");
      $("#report-query-form-receiptAmountReport")[0].reset();
      $("#report-datagrid-receiptAmountReport").datagrid('loadData',{total:0,rows:[]});
    });

    $("#report-buttons-receiptAmountReport").Excel('export',{
      queryForm: "#report-query-form-receiptAmountReport",
      type:'exportUserdefined',
      name:'应收款余额报表',
      url:'report/finance/exportReceiptAmountReportData.do?groupcols=customerid'
    });
  });
</script>
</body>
</html>
