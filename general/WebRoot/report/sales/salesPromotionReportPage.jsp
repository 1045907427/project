<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <title>促销活动统计报表</title>
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
<table id="report-datagrid-salesPromotionReport"></table>
<div id="report-toolbar-salesPromotionReport" class="buttonBG">
  <a href="javaScript:void(0);" id="report-advancedQuery-salesPromotionReportPage" class="easyui-linkbutton" iconCls="button-commonquery" plain="true" title="查询">查询</a>
  <security:authorize url="/report/sales/salesPromotionReportExport.do">
    <a href="javaScript:void(0);" id="report-export-salesPromotionReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
  </security:authorize>
  <security:authorize url="/report/sales/salesPromotionReportPrint.do">
    <a href="javaScript:void(0);" id="report-print-salesPromotionReportPage" class="easyui-linkbutton" iconCls="button-print" plain="true" title="打印">打印</a>
  </security:authorize>
</div>
<div style="display:none">
  <div id="report-dialog-promotion-advancedQueryDialog" >
    <form action="" id="report-query-form-salesPromotionReport" method="post">
      <table cellpadding="2" style="margin-left:5px;margin-top: 5px;">
        <tr>
          <td>业务日期:</td>
          <td><input type="text" name="businessdate1" value="${today }" style="width:87px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:87px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
          <td>客&nbsp;&nbsp;户:</td>
          <td><input type="text" name="customerid" id="report-customernamemore-advancedQuery"/></td>
        </tr>
        <tr>
          <td>总&nbsp;&nbsp;店:</td>
          <td><input type="text" name="pcustomerid" id="report-pcustomernamemore-advancedQuery"/></td>
          <td>品&nbsp;&nbsp;牌:</td>
          <td><input type="text" name="brandid" id="report-brandid-advancedQuery"/></td>
        </tr>
        <tr>
          <td>销售区域:</td>
          <td><input type="text" name="salesarea" id="report-salesarea-advancedQuery"/></td>
          <td>单据编号:</td>
          <td><input type="text" name="billid" style="width: 200px;"/></td>
        </tr>
        <tr>
          <td>商&nbsp;&nbsp;品:</td>
          <td><input type="text" name="goodsid" id="report-goodsid-advancedQuery"/></td>
          <td>商品分类:</td>
          <td><input type="text" name="goodssort" id="report-goodssort-advancedQuery"/></td>
        </tr>
        <tr>
          <td>客户分类:</td>
          <td><input type="text" name="customersort" id="report-customersort-advancedQuery"/></td>
          <td>客户业务员:</td>
          <td><input type="text" name="salesuser" id="report-salesuser-advancedQuery"/></td>
        </tr>
        <tr>
          <td>供 应 商:</td>
          <td><input type="text" name="supplierid" id="report-supplierid-advancedQuery"/></td>
          <td>促销类型:</td>
          <td><select name="ptype" style="width: 200px;">
              <option></option>
              <option value="0">正常商品</option>
              <option value="1">买赠</option>
              <option value="2">捆绑</option>
              <option value="3">满赠</option>
              <option value="4">特价</option>
          </select></td>
        </tr>
        <tr>
          <td>销售部门:</td>
          <td><input type="text" name="salesdept" id="report-salesdept-advancedQuery"/></td>
          <td>仓库:</td>
          <td><input type="text" name="storageid" id="report-storageid-advancedQuery"/></td>
        </tr>
        <tr>
          <td>小计列：</td>
          <td colspan="3">
            <div style="margin-top:2px">
              <div style="line-height: 25px;margin-top: 2px;">
                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="customerid"/>客户</label>
                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="pcustomerid"/>总店</label>
                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="customersort"/>客户分类</label>
                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesarea"/>销售区域</label>
                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesuser"/>客户业务员</label>
                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesdept"/>销售部门</label>
                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="storageid"/>仓库</label>
                <br/>
                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="goodsid"/>商品</label>
                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="brandid"/>品牌</label>
                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="goodssort"/>商品分类</label>
                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="supplierid"/>供应商</label>
                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="promotionid"/>促销单据</label>
                <input id="report-query-groupcols" type="hidden" name="groupcols"/>
              </div>
            </div>
          </td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript">
  var PR_footerobject  = null;
  var initQueryJSON = $("#report-query-form-salesPromotionReport").serializeJSON();
  $(function(){
      $("#report-print-salesPromotionReportPage").click(function () {
          var msg = "";
          printByAnalyse("促销活动统计表", "report-datagrid-salesPromotionReport", "report/sales/printSalesPromotionReportData.do", msg);
      })
    $("#report-export-salesPromotionReportPage").click(function(){
      //封装查询条件
      var objecr  = $("#report-datagrid-salesPromotionReport").datagrid("options");
      var queryParam = JSON.stringify(objecr.queryParams);
      var url = "report/sales/exportSalesPromotionReportData.do";
      exportByAnalyse(queryParam,"促销活动统计表","report-datagrid-salesPromotionReport",url);
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
    var tableColumnListJson = $("#report-datagrid-salesPromotionReport").createGridColumnLoad({
      frozenCol : [[
        {field:'idok',checkbox:true,isShow:true}
      ]],
      commonCol : [[
        {field:'customerid',title:'客户编号',sortable:true,width:60},
        {field:'customername',title:'客户名称',width:210},
        {field:'pcustomerid',title:'总店编码',sortable:true,width:60,hidden:true},
        {field:'pcustomername',title:'总店名称',width:60,hidden:true},
        {field:'salesuser',title:'客户业务员',sortable:true,width:70,hidden:true,
          formatter:function(value,rowData,rowIndex){
            return rowData.salesusername;
          }
        },
        {field:'customersort',title:'客户分类',sortable:true,width:60,hidden:true,
          formatter:function(value,rowData,rowIndex){
            return rowData.customersortname;
          }
        },
        {field:'salesarea',title:'销售区域',sortable:true,width:60,hidden:true,
          formatter:function(value,
                             rowData,rowIndex){
            return rowData.salesareaname;
          }
        },
        {field:'salesdept',title:'销售部门',sortable:true,width:80,hidden:true,
          formatter:function(value,rowData,rowIndex){
            return rowData.salesdeptname;
          }
        },
        {field:'goodsid',title:'商品编码',sortable:true,width:60,hidden:true},
        {field:'goodsname',title:'商品名称',width:250,hidden:true},
        {field:'spell',title:'助记符',width:80,hidden:true},
        {field:'goodssortname',title:'商品分类',width:60,hidden:true},
        {field:'barcode',title:'条形码',width:90,hidden:true},
        {field:'boxnum',title:'箱装量',width:60,hidden:true,align:'right',
          formatter:function(value,rowData,rowIndex){
            return formatterBigNumNoLen(value);
          }
        },
        {field:'brandid',title:'品牌名称',sortable:true,width:60,hidden:true,
          formatter:function(value,rowData,rowIndex){
            return rowData.brandname;
          }
        },
        {field:'ptype',title:'促销类型',sortable:true,width:60,
          formatter:function(value,rowData,rowIndex){
            return rowData.ptypename;
          }
        },
        {field:'supplierid',title:'供应商名称',sortable:true,width:200,hidden:true,
          formatter:function(value,rowData,rowIndex){
            return rowData.suppliername;
          }
        },
        {field:'storageid',title:'仓库名称',sortable:true,width:60,hidden:true,
          formatter:function(value,rowData,rowIndex){
            return rowData.storagename;
          }
        },
        {field:'saleoutid',title:'发货单编号',sortable:true,width:140,hidden:true},
        {field:'promotionid',title:'促销编号',sortable:true,width:140,hidden:true},
        {field:'unitname',title:'主单位',width:45},
        {field:'salesnum',title:'销售数量',width:80,align:'right',
          formatter:function(value,rowData,rowIndex){
            return formatterBigNumNoLen(value);
          }
        },
        {field:'salesamount',title:'销售金额',resizable:true,sortable:true,align:'right',
          formatter:function(value,rowData,rowIndex){
            return formatterMoney(value);
          }
        },
        {field:'promonum',title:'促销数量',width:80,align:'right',
          formatter:function(value,rowData,rowIndex){
            return formatterBigNumNoLen(value);
          }
        },
        {field:'promoamount',title:'促销金额',resizable:true,sortable:true,align:'right',
          formatter:function(value,rowData,rowIndex){
            return formatterMoney(value);
          }
        }
      ]]
    });
    //品牌
    $("#report-brandid-advancedQuery").widget({
      referwid:'RL_T_BASE_GOODS_BRAND',
      singleSelect:false,
      width:200,
      onlyLeafCheck:true
    });

    //供应商
    $("#report-supplierid-advancedQuery").widget({
      referwid:'RL_T_BASE_BUY_SUPPLIER',
      singleSelect:false,
      width:200,
      onlyLeafCheck:true
    });
    $("#report-salesdept-advancedQuery").widget({
      name:'t_sales_order',
      col:'salesdept',
      width:200,
      singleSelect:false,
      onlyLeafCheck:true
    });
    //仓库
    $("#report-storageid-advancedQuery").widget({
      referwid:'RL_T_BASE_STORAGE_INFO',
      singleSelect:false,
      width:200
    });

    $("#report-customernamemore-advancedQuery").widget({
      referwid:'RL_T_BASE_SALES_CUSTOMER',
      singleSelect:false,
      width:200,
      onlyLeafCheck:true
    });
    $("#report-pcustomernamemore-advancedQuery").widget({
      referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
      singleSelect:false,
      width:200,
      onlyLeafCheck:true
    });
    $("#report-goodsid-advancedQuery").widget({
      referwid:'RL_T_BASE_GOODS_INFO',
      singleSelect:false,
      width:200,
      onlyLeafCheck:true
    });
    $("#report-goodssort-advancedQuery").widget({
      referwid:'RL_T_BASE_GOODS_WARESCLASS',
      singleSelect:false,
      width:200,
      onlyLeafCheck:false
    });
    $("#report-customersort-advancedQuery").widget({
      referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
      singleSelect:false,
      width:200,
      onlyLeafCheck:false
    });
    $("#report-salesuser-advancedQuery").widget({
      referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
      singleSelect:false,
      width:200,
      onlyLeafCheck:false
    });
    $("#report-salesarea-advancedQuery").widget({
      referwid:'RT_T_BASE_SALES_AREA',
      singleSelect:false,
      width:200,
      onlyLeafCheck:false
    });

    $("#report-datagrid-salesPromotionReport").datagrid({
      authority:tableColumnListJson,
      frozenColumns: tableColumnListJson.frozen,
      columns:tableColumnListJson.common,
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
      toolbar:'#report-toolbar-salesPromotionReport',
      onLoadSuccess:function(){
        var footerrows = $(this).datagrid('getFooterRows');
        if(null!=footerrows && footerrows.length>0){
          PR_footerobject = footerrows[0];
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

    //高级查询
    $("#report-advancedQuery-salesPromotionReportPage").click(function(){
      $("#report-dialog-promotion-advancedQueryDialog").dialog({
        maximizable:false,
        resizable:true,
        title: '促销活动统计表查询',
        top:30,
        width: 565,
        height: 400,
        closed: false,
        cache: false,
        modal: true,
        buttons:[
          {
            text:'确定',
            handler:function(){
              setColumn();
              //把form表单的name序列化成JSON对象
              var queryJSON = $("#report-query-form-salesPromotionReport").serializeJSON();
              $("#report-datagrid-salesPromotionReport").datagrid({
                url:'report/sales/getSalesPromotionReportData.do',
                pageNumber:1,
                queryParams:queryJSON
              }).datagrid("columnMoving");

              $("#report-dialog-promotion-advancedQueryDialog").dialog('close');
            }
          },
          {
            text:'重置',
            handler:function(){
              $("#report-query-form-salesPromotionReport").form("reset");
              $(".groupcols").each(function(){
                if($(this).attr("checked")){
                  $(this)[0].checked = false;
                }
              });
              $("#report-query-groupcols").val("");
              $("#report-customernamemore-advancedQuery").widget("clear");
              $("#report-pcustomernamemore-advancedQuery").widget("clear");
              $("#report-customersort-advancedQuery").widget("clear");
              $("#report-brandid-advancedQuery").widget("clear");
              $("#report-salesarea-advancedQuery").widget("clear");
              $("#report-salesuser-advancedQuery").widget("clear");
              $("#report-goodsid-advancedQuery").widget("clear");
              $("#report-goodssort-advancedQuery").widget("clear");
              $("#report-supplierid-advancedQuery").widget("clear");
              $("#report-datagrid-salesPromotionReport").datagrid("loadData",[]);
              $("#report-salesdept-advancedQuery").widget("clear");
              $("#report-storageid-advancedQuery").widget('clear');
              setColumn();
            }
          }
        ],
        onClose:function(){
        }
      });
    });
  });
  function setColumn(){
    var cols = $("#report-query-groupcols").val();
    if(cols!=""){
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "customerid");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "customername");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "pcustomerid");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "pcustomername");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "salesuser");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "customersort");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "salesarea");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "salesdept");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "goodsid");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "goodsname");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "spell");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "goodssortname");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "barcode");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "boxnum");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "brandid");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "supplierid");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "storageid");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "saleoutid");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "promotionid");
    }
    else{
      $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "customerid");
      $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "customername");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "pcustomerid");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "pcustomername");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "salesuser");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "salesarea");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "salesdept");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "goodsid");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "goodsname");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "spell");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "goodssortname");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "barcode");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "boxnum");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "brandid");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "supplierid");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "storageid");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "saleoutid");
      $("#report-datagrid-salesPromotionReport").datagrid('hideColumn', "promotionid");
    }
    var colarr = cols.split(",");
    for(var i=0;i<colarr.length;i++){
      var col = colarr[i];
      if(col=='customerid'){
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "customerid");
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "customername");
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "pcustomerid");
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "pcustomername");
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "salesuser");
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "customersort");
      }else if(col=="pcustomerid"){
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "pcustomerid");
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "pcustomername");
      }else if(col=="salesuser"){
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "salesuser");
        //$("#report-datagrid-salesPromotionReport").datagrid('showColumn', "salesarea");
        //$("#report-datagrid-salesPromotionReport").datagrid('showColumn', "salesdept");
      }else if(col=="salesarea"){
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "salesarea");
      }else if(col=="salesdept"){
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "salesdept");
      }else if(col=="goodsid"){
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "goodsid");
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "goodsname");
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "spell");
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "goodssortname");
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "barcode");
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "boxnum");
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "brandid");
      }else if(col=="goodssort"){
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "goodssortname");
      }else if(col=="brandid"){
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "brandid");
      }else if(col=="customersort"){
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "customersort");
      }else if(col=="supplierid"){
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "supplierid");
      }else if(col=="storageid"){
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "storageid");
      }else if(col=="promotionid"){
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "saleoutid");
        $("#report-datagrid-salesPromotionReport").datagrid('showColumn', "promotionid");
      }
    }
  }

  function baseSales_retColsname(){
    var colname = "";
    var cols = $("#report-query-groupcols").val();
    if(cols == ""){
      cols = "customerid";
    }
    var colarr = cols.split(",");
    if(colarr[0]=="pcustomerid"){
      colname = "pcustomername";
    }else if(colarr[0]=='customerid'){
      colname = "customername";
    }else if(colarr[0]=="salesuser"){
      colname = "salesusername";
    }else if(colarr[0]=="salesarea"){
      colname = "salesareaname";
    }else if(colarr[0]=="salesdept"){
      colname = "salesdeptname";
    }else if(colarr[0]=="goodsid"){
      colname = "goodsname";
    }else if(colarr[0]=="goodssort"){
      colname = "goodssortname";
    }else if(colarr[0]=="brandid"){
      colname = "brandname";
    }else if(colarr[0]=="customersort"){
      colname = "customersortname";
    }else if(colarr[0]=="supplierid"){
      colname = "suppliername";
    }else if(colarr[0]=="storageid"){
      colname = "storagename";
    }else if(colarr[0]=="promotionid"){
      colname = "promotionid";
    }
    return colname;
  }

  function countTotalAmount(){
    var rows =  $("#report-datagrid-salesPromotionReport").datagrid('getChecked');
    var salesnum = 0;
    var salesamount = 0;
    var promonum = 0;
    var promoamount = 0;
    for(var i=0;i<rows.length;i++){
      salesnum = Number(salesnum)+Number(rows[i].salesnum == undefined ? 0 : rows[i].salesnum);
      salesamount = Number(salesamount)+Number(rows[i].salesamount == undefined ? 0 : rows[i].salesamount);
      promonum = Number(promonum)+Number(rows[i].promonum == undefined ? 0 : rows[i].promonum);
      promoamount = Number(promoamount)+Number(rows[i].promoamount == undefined ? 0 : rows[i].promoamount);
    }
    var obj = {salesnum:Number(salesnum).toFixed(general_bill_decimallen),salesamount:Number(salesamount).toFixed(2),promonum:Number(promonum).toFixed(general_bill_decimallen),promoamount:Number(promoamount).toFixed(2)};
    var col = baseSales_retColsname();
    if(col != ""){
      obj[col] = '选中合计';
    }else{
      obj['customername'] = '选中合计';
    }
    var foot=[];
    foot.push(obj);
    if(null!=PR_footerobject){
      foot.push(PR_footerobject);
    }
    $("#report-datagrid-salesPromotionReport").datagrid("reloadFooter",foot);
  }
</script>
</body>
</html>
