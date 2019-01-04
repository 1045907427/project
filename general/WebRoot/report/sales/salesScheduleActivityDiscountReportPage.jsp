<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>档期活动折让统计表</title>
  <%@include file="/include.jsp" %>
  <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
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
</head>
<body>
<table id="report-datagrid-salesScheduleADReport"></table>
<div id="report-toolbar-salesScheduleADReport" style="padding: 0px;">
  <div class="buttonBG">
    <security:authorize url="/report/sales/salesScheduleADReportExport.do">
      <a href="javaScript:void(0);" id="report-export-salesScheduleADReport" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
    </security:authorize>
    <security:authorize url="/report/sales/salesScheduleADReportPrint.do">
      <a href="javaScript:void(0);" id="report-print-salesScheduleADReport" class="easyui-linkbutton" iconCls="button-print" plain="true" title="打印">打印</a>
    </security:authorize>
  </div>
  <form action="" id="report-query-form-salesScheduleADReport" method="post">
    <table class="querytable">
      <tr>
        <td>生效日期-截止日期：</td>
        <td><input id="report-query-begindate" type="text" name="begindate" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
            - <input id="report-query-enddate" type="text" name="enddate" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
        </td>
        <td>档期：</td>
        <td><input type="text" id="report-query-schedule" name="schedule" class="len150"></td>
        <td>商品品牌：</td>
        <td><input type="text" id="report-query-brandid" name="brandid"/></td>
      </tr>
      <tr>
        <td>客户：</td>
        <td><input type="text" id="report-query-customerid" name="customerid" /></td>
        <td>渠道：</td>
        <td><input type="text" id="report-query-customersort" name="customersort"/></td>
        <td colspan="2">
          <a href="javaScript:void(0);" id="report-query-salesScheduleADReport" class="button-qr">查询</a>
          <a href="javaScript:void(0);" id="report-reload-salesScheduleADReport" class="button-qr">重置</a>
        </td>
      </tr>
      <tr>
        <td>商品：</td>
        <td><input type="text" id="report-query-goodsid" name="goodsid" /></td>
        <td>小计列：</td>
        <td colspan="3">
          <div style="margin-top:2px">
            <div style="line-height: 25px;margin-top: 2px;">
              <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="schedule"/>档期</label>
              <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="customersort"/>渠道</label>
              <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="customerid"/>客户</label>
              <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="brandid"/>品牌</label>
              <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="goodsid"/>商品</label>
              <input id="report-query-groupcols" type="hidden" name="groupcols"/>
            </div>
          </div>
        </td>
      </tr>
    </table>
  </form>
</div>
<script type="text/javascript">
  var SADR_footerobject  = null;
  var initQueryJSON = $("#report-query-form-salesScheduleADReport").serializeJSON();
  $(function(){
      $("#report-print-salesScheduleADReport").click(function () {
          var msg = "";
          printByAnalyse("档期活动折让统计表", "report-datagrid-salesScheduleADReport", "report/sales/printSalesScheduleActivityDiscountReportData.do", msg);
      })

    $("#report-export-salesScheduleADReport").click(function(){
      var queryJSON = $("#report-query-form-salesScheduleADReport").serializeJSON();
      var queryParam = JSON.stringify(queryJSON);
      var url = "report/sales/exportSalesScheduleActivityDiscountReportData.do";
      exportByAnalyse(queryParam,"档期活动折让统计表","report-datagrid-salesScheduleADReport",url);
    });

    $(".groupcols").click(function(){
      var cols = "";
      $("#report-query-groupcols").val(cols);
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

    var tableColumnJson = $("#report-datagrid-salesScheduleADReport").createGridColumnLoad({
      frozenCol : [[
        {field:'idok',checkbox:true,isShow:true}
      ]],
      commonCol : [[
        {field:'schedule',title:'档期',width:100},
        {field:'begindate',title:'生效日期',width:100},
        {field:'enddate',title:'截止日期',width:100},
        {field:'customersort',title:'渠道',width:60,
          formatter:function(value,rowData,rowIndex){
            return rowData.customersortname;
          }
        },
        {field:'customerid',title:'客户编号',width:60},
        {field:'customername',title:'客户名称',width:200},
        {field:'brandid',title:'商品品牌',width:60,
          formatter:function(value,rowData,rowIndex){
            return rowData.brandname;
          }
        },
        {field:'goodsid',title:'商品编号',width:80},
        {field:'goodsname',title:'商品名称',width:200},
        {field:'barcode',title:'条形码',width:120},
        {field:'oldprice',title:'原价',width:80,align:'right',
          formatter:function(value,rowData,rowIndex){
            return formatterMoney(value);
          }
        },
        {field:'offprice',title:'特价',width:80,align:'right',
          formatter:function(value,rowData,rowIndex){
            return formatterMoney(value);
          }
        },
        {field:'costprice',title:'成本价',width:80,align:'right',
            formatter:function(value,rowData,rowIndex){
                return formatterMoney(value);
            }
        },
        {field:'rate',title:'毛利率',width:80,align:'right',
            formatter:function(value,rowData,rowIndex){
                console.info(value)
                if(value != undefined){
                    return formatterMoney(value)+"%";
                }
            }
        },
        {field:'unitname',title:'单位',width:50},
        {field:'schedulenum',title:'档期销量',width:90,align:'right',
          formatter:function(value,rowData,rowIndex){
            return formatterBigNumNoLen(value);
          }
        },
        {field:'schedulediscountamount',title:'档期折让金额',width:90,align:'right',
          formatter:function(value,rowData,rowIndex){
            return formatterMoney(value);
          }
        }
      ]]
    });

    $("#report-datagrid-salesScheduleADReport").datagrid({
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
      toolbar:'#report-toolbar-salesScheduleADReport',
      onLoadSuccess:function(){
        var footerrows = $(this).datagrid('getFooterRows');
        if(null!=footerrows && footerrows.length>0){
          SADR_footerobject = footerrows[0];
          countTotalAmount();
        }
      },
      onCheckAll:function(){
        countTotalAmount();
      },
      onUncheckAll:function(){
        countTotalAmount();
      },
      onCheck:function(){
        countTotalAmount();
      },
      onUncheck:function(){
        countTotalAmount();
      }
    }).datagrid("columnMoving");

    //渠道
    $("#report-query-customersort").widget({
      referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
      singleSelect:false,
      width:150,
      onlyLeafCheck:false
    });

    //商品品牌
    $("#report-query-brandid").widget({
      referwid:'RL_T_BASE_GOODS_BRAND',
      singleSelect:false,
      width:130,
      onlyLeafCheck:true
    });

    //客户
    $("#report-query-customerid").widget({
      referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
      singleSelect:false,
      width:218,
      onlyLeafCheck:true
    });

    //商品
    $("#report-query-goodsid").widget({
      referwid:'RL_T_BASE_GOODS_INFO',
      singleSelect:false,
      width:218,
      onlyLeafCheck:true
    });

    //查询
    $("#report-query-salesScheduleADReport").click(function(){
      setColumn();
      var queryJSON = $("#report-query-form-salesScheduleADReport").serializeJSON();
      $("#report-datagrid-salesScheduleADReport").datagrid({
        url: 'report/sales/getSalesScheduleActivityDiscountReportData.do',
        pageNumber:1,
        queryParams:queryJSON
      }).datagrid("columnMoving");
    });
    //重置
    $("#report-reload-salesScheduleADReport").click(function(){
      $(".groupcols").each(function(){
        if($(this).attr("checked")){
          $(this)[0].checked = false;
        }
      });
      $("#report-query-groupcols").val("");
      $("#report-query-customersort").widget("clear");
      $("#report-query-brandid").widget("clear");
      $("#report-query-customerid").widget("clear");
      $("#report-query-goodsid").widget("clear");
      $("#report-query-form-salesScheduleADReport")[0].reset();
      var queryJSON = $("#report-query-form-salesScheduleADReport").serializeJSON();
      $("#report-datagrid-salesScheduleADReport").datagrid('loadData',[]);

      setColumn();
    });

    function setColumn(){
      var cols = $("#report-query-groupcols").val();
      if(cols!=""){
        $("#report-datagrid-salesScheduleADReport").datagrid('hideColumn', "schedule");
        $("#report-datagrid-salesScheduleADReport").datagrid('hideColumn', "begindate");
        $("#report-datagrid-salesScheduleADReport").datagrid('hideColumn', "enddate");
        $("#report-datagrid-salesScheduleADReport").datagrid('hideColumn', "customersort");
        $("#report-datagrid-salesScheduleADReport").datagrid('hideColumn', "customerid");
        $("#report-datagrid-salesScheduleADReport").datagrid('hideColumn', "customername");
        $("#report-datagrid-salesScheduleADReport").datagrid('hideColumn', "brandid");
        $("#report-datagrid-salesScheduleADReport").datagrid('hideColumn', "goodsid");
        $("#report-datagrid-salesScheduleADReport").datagrid('hideColumn', "goodsname");
        $("#report-datagrid-salesScheduleADReport").datagrid('hideColumn', "barcode");
        $("#report-datagrid-salesScheduleADReport").datagrid('hideColumn', "oldprice");
        $("#report-datagrid-salesScheduleADReport").datagrid('hideColumn', "offprice");
        $("#report-datagrid-salesScheduleADReport").datagrid('hideColumn', "costprice");
        $("#report-datagrid-salesScheduleADReport").datagrid('hideColumn', "rate");
        $("#report-datagrid-salesScheduleADReport").datagrid('hideColumn', "unitname");
      }else{
        $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "schedule");
        $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "begindate");
        $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "enddate");
        $("#report-datagrid-salesScheduleADReport").datagrid('hideColumn', "customersort");
        $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "customerid");
        $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "customername");
        $("#report-datagrid-salesScheduleADReport").datagrid('hideColumn', "brandid");
        $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "goodsid");
        $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "goodsname");
        $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "barcode");
        $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "oldprice");
        $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "offprice");
        $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "costprice");
        $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "rate");
        $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "unitname");
      }
      var colarr = cols.split(",");
      var customerflag = false;goodsflag = false;
      for(var i=0;i<colarr.length;i++){
        var col = colarr[i];
        if(col=='schedule'){
          $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "schedule");
          $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "begindate");
          $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "enddate");
        }else if(col=="customersort"){
          $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "customersort");
        }else if(col=="customerid"){
          $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "customerid");
          $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "customername");
          customerflag = true;
        }else if(col=="brandid"){
          $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "brandid");
        }else if(col=="goodsid"){
          $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "goodsid");
          $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "goodsname");
          $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "barcode");
          $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "unitname");
          goodsflag = true;
        }
      }
      if(customerflag && goodsflag){
        $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "oldprice");
        $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "offprice");
        $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "costprice");
        $("#report-datagrid-salesScheduleADReport").datagrid('showColumn', "rate");
      }
    }

    function SADR_retColsname(){
      var colname = "";
      var cols = $("#report-query-groupcols").val();
      if(cols == ""){
        cols = "schedule";
      }
      var colarr = cols.split(",");
      if(colarr[0]=="schedule"){
        colname = "schedule";
      }else if(colarr[0]=='customersort'){
        colname = "customersortname";
      }else if(colarr[0]=="customerid"){
        colname = "customername";
      }else if(colarr[0]=="brandid"){
        colname = "brandname";
      }else if(colarr[0]=="goodsid"){
        colname = "goodsname";
      }
      return colname;
    }
    
    function countTotalAmount(){
      var rows =  $("#report-datagrid-salesScheduleADReport").datagrid('getChecked');
      var schedulenum = 0;
      var schedulediscountamount = 0;
      for(var i=0;i<rows.length;i++){
        schedulenum = Number(schedulenum)+Number(rows[i].schedulenum == undefined ? 0 : rows[i].schedulenum);
        schedulediscountamount = Number(schedulediscountamount)+Number(rows[i].schedulediscountamount == undefined ? 0 : rows[i].schedulediscountamount);
      }
      var obj = {schedulenum:schedulenum,schedulediscountamount:schedulediscountamount};
      var col = SADR_retColsname();
      if(col != ""){
        obj[col] = '选中合计';
      }else{
        obj['schedule'] = '选中合计';
      }
      var foot=[];
      foot.push(obj);
      if(null!=SADR_footerobject){
        foot.push(SADR_footerobject);
      }
      $("#report-datagrid-salesScheduleADReport").datagrid("reloadFooter",foot);
    }
  });
</script>
</body>
</html>
