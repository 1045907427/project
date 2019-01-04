<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>库存周转天数区间报表</title>
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
    	<table id="report-datagrid-revolutionDays"></table>
    	<div id="report-toolbar-revolutionDays" style="padding:0px;height:auto">
            <div class="buttonBG">
                <security:authorize url="/report/storage/revolutionDaysBySectionExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-revolutionDaysPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-revolutionDays" method="post">
    		<table class="querytable">

                <tr>
                <tr>
                    <td>商&nbsp;品:</td>
                    <td colspan="2"><input type="text" id="report-query-goodsid" name="goodsid" style="width: 220px;"/></td>
                    <td>品牌名称:</td>
                    <td><input type="text" id="report-query-brandid" name="brandid" style="width:170px;"/></td>
                    <td>品牌部门:</td>
                    <td>
                        <input type="text" id="report-query-brandept" name="brandept" style="width: 125px;"/>
                    </td>
                </tr>
                <tr>
                    <td>日&nbsp;期:</td>
                    <td colspan="2">
                        <input type="text" name="begindate" style="width:100px;" class="Wdate" readonly value="${preMonthLastDay}" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${preMonthLastDay }',isShowClear:false})" />
                        到<input type="text" name="enddate" class="Wdate" style="width:100px;" readonly value="${preMonthLastDay}" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${preMonthLastDay }',isShowClear:false})" />
                    </td>
                    <td>供 应 商:</td>
                    <td>
                        <input type="text" id="report-query-supplierid" style="width: 170px;" name="supplierid"/>
                    </td>
                    <td>周转天数:</td>
                    <td>
                        <div style="float:left"><input type="text" class="easyui-numberbox" name="days1" style="width:50px;"/></div>
                        <div style="float:left;width:25px;text-align: center">--</div>
                        <div style="float:left"><input type="text" class="easyui-numberbox" name="days2" style="width:50px;"/></div>
                    </td>
                </tr>
    			<tr>
                    <td>小计列:</td>
                    <td colspan="4">
                        <div>
                            <input type="checkbox" class="groupcols checkbox1" value="goodsid" id="goodsid"/>
                            <label class="divtext" for="goodsid">商品</label>
                            <input type="checkbox" class="groupcols checkbox1" value="brandid" id="brandid"/>
                            <label class="divtext" for="brandid">品牌</label>
                            <input type="checkbox" class="groupcols checkbox1" value="brandept" id="brandept"/>
                            <label class="divtext" for="brandept">品牌部门</label>
                            <input type="checkbox" class="groupcols checkbox1" value="supplierid" id="supplierid"/>
                            <label class="divtext" for="supplierid">供应商</label>
                            <input id="report-query-groupcols" type="hidden" name="groupcols"/>
                        </div>
                    </td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="report-queay-revolutionDays" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="report-reload-revolutionDays" class="button-qr">重置</a>
                    </td>
    			</tr>
    		</table>
                <div id="report-dialog-salesAmountDetail"></div>
    	</form>
    	</div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-revolutionDays").serializeJSON();
    		var checkListJson = $("#report-datagrid-revolutionDays").createGridColumnLoad({
				commonCol : [[
                             //{field:'businessdate',title:'业务日期',width:80},
							 {field:'goodsid',title:'商品编码',width:60},
							 {field:'goodsname',title:'商品名称',width:180},
							 {field:'barcode',title:'条形码',sortable:true,width:90},
							 {field:'brandid',title:'品牌名称',width:60,sortable:true,
							 	formatter:function(value,rowData,rowIndex){
					        		return rowData.brandname;
					        	}
							 },
							 {field:'brandept',title:'品牌部门',width:70,sortable:true,
							 	formatter:function(value,rowData,rowIndex){
					        		return rowData.branddeptname;
					        	}
							 },
							 {field:'supplierid',title:'供应商编号',width:70,sortable:true},
							 {field:'suppliername',title:'供应商名称',width:180},
							 {field:'storageunitnum',title:'库存平均数量',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							 },
							 {field:'storageamount',title:'库存平均金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							 },
							 {field:'salesamount',title:'销售金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
                                    if(value == undefined){
                                        return "";
                                    }
                                    if("合计"== rowData.goodsname || "合计"==rowData.suppliername || "合计"== rowData.branddeptname || "合计"==rowData.brandname ){
                                        return formatterMoney(value);
                                    }
                                    var cols = $("#report-query-groupcols").val();
                                    var column = "";
                                    if(cols == ""){
                                        column = "goodsid";
                                    }else{
                                        var colarr = cols.split(",");
                                        column = colarr[0];
                                    }
                                    if("goodsid" == column){
                                        return '<a href="javascript:showSalesamountDetail(\''+rowData.goodsid+'\')">'+formatterMoney(value)+'</a>';
                                    }else if("brandid" == column){
                                        return '<a href="javascript:showSalesamountDetail(\''+rowData.brandid+'\')">'+formatterMoney(value)+'</a>';
                                    }else if("brandept" == column){
                                        return '<a href="javascript:showSalesamountDetail(\''+rowData.brandept+'\')">'+formatterMoney(value)+'</a>';
                                    }else if("supplierid" == column){
                                        return '<a href="javascript:showSalesamountDetail(\''+rowData.supplierid+'\')">'+formatterMoney(value)+'</a>';
                                    }else{
                                        return '<a href="javascript:showSalesamountDetail(\''+rowData.goodsid+'\')">'+formatterMoney(value)+'</a>';
                                    }
					        	}
							 },
							 {field:'days',title:'周转天数',width:60,align:'right',sortable:true,
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
    			$("#report-datagrid-revolutionDays").datagrid({ 
					authority:checkListJson,
		 			frozenColumns: checkListJson.frozen,
					columns:checkListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		pageSize:100,
		  	 		singleSelect:true,
					toolbar:'#report-toolbar-revolutionDays'
				}).datagrid("columnMoving");
				$("#report-query-goodsid").goodsWidget();
				$("#report-query-brandid").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
		    		width:170,
					singleSelect:true
				});
				$("#report-query-brandept").widget({
					referwid:'RL_T_BASE_DEPARTMENT_BUYER',
		    		width:125,
					singleSelect:true
				});
				$("#report-query-supplierid").supplierWidget();
				//回车事件
				controlQueryAndResetByKey("report-queay-revolutionDays","report-reload-revolutionDays");
				
				//查询
				$("#report-queay-revolutionDays").click(function(){
					setColumn();
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-revolutionDays").serializeJSON();
		      		$("#report-datagrid-revolutionDays").datagrid({
		      			url: 'report/storage/showStorageRevolutionDaysBySectionData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-revolutionDays").click(function(){
					$("#report-query-goodsid").goodsWidget("clear");
					$("#report-query-brandid").widget("clear");
					$("#report-query-supplierid").supplierWidget("clear");
					$("#report-query-form-revolutionDays").form("reset");
					var queryJSON = $("#report-query-form-revolutionDays").serializeJSON();
		       		$("#report-datagrid-revolutionDays").datagrid('loadData',{total:0,rows:[]});
				});
                //全局导出
                $("#report-buttons-revolutionDaysPage").click(function(){
                    //封装查询条件
                    var objecr  = $("#report-datagrid-revolutionDays").datagrid("options");
                    var queryParam = objecr.queryParams;
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryParam["sort"] = objecr.sortName;
                        queryParam["order"] = objecr.sortOrder;
                    }
                    var queryParam = JSON.stringify(queryParam);
                    var url = "report/storage/exportRevolutionDaysBySection.do";
                    exportByAnalyse(queryParam,"库存周转天数区间统计表","report-datagrid-revolutionDays",url);
                });

    		});

            var $datagrid = $("#report-datagrid-revolutionDays");
            function setColumn(){
                var cols = $("#report-query-groupcols").val();
                if(cols!=""){
                    $datagrid.datagrid('hideColumn', "goodsid");
                    $datagrid.datagrid('hideColumn', "barcode");
                    $datagrid.datagrid('hideColumn', "goodsname");
                    $datagrid.datagrid('hideColumn', "brandid");
                    $datagrid.datagrid('hideColumn', "brandept");
                    $datagrid.datagrid('hideColumn', "supplierid");
                    $datagrid.datagrid('hideColumn', "suppliername");
                }else{
                    $datagrid.datagrid('showColumn', "goodsid");
                    $datagrid.datagrid('showColumn', "barcode");
                    $datagrid.datagrid('showColumn', "goodsname");
                    $datagrid.datagrid('showColumn', "brandid");
                    $datagrid.datagrid('showColumn', "brandept");
                    $datagrid.datagrid('showColumn', "supplierid");
                    $datagrid.datagrid('showColumn', "suppliername");
                }
                var colarr = cols.split(",");
                for(var i=0;i<colarr.length;i++){
                    var col = colarr[i];
                    if(col=="goodsid"){
                        $datagrid.datagrid('showColumn', "goodsid");
                        $datagrid.datagrid('showColumn', "barcode");
                        $datagrid.datagrid('showColumn', "goodsname");
                        $datagrid.datagrid('showColumn', "brandid");
                        $datagrid.datagrid('showColumn', "brandept");
                        $datagrid.datagrid('showColumn', "supplierid");
                        $datagrid.datagrid('showColumn', "suppliername");
                    }else if(col=="brandid"){
                        $datagrid.datagrid('showColumn', "brandid");
                        $datagrid.datagrid('showColumn', "brandept");
                        $datagrid.datagrid('showColumn', "supplierid");
                        $datagrid.datagrid('showColumn', "suppliername");
                    }else if(col=="brandept"){
                        $datagrid.datagrid('showColumn', "brandept");
                    }else if(col=="supplierid"){
                        $datagrid.datagrid('showColumn', "supplierid");
                        $datagrid.datagrid('showColumn', "suppliername");
                    }
                }
            }

            function showSalesamountDetail(value){
                var cols = $("#report-query-groupcols").val();
                var column = "";
                if(cols == ""){
                    column = "goodsid";
                }else{
                    var colarr = cols.split(",");
                    column = colarr[0];
                }
                var begindate = $("input[name=begindate]").val();
                var enddate = $("input[name=enddate]").val();
                $("#report-dialog-salesAmountDetail").dialog({
                    title:'库存周转天数报表销售流水明细',
                    width:800,
                    height:450,
                    closed:false,
                    modal:true,
                    cache:false,
                    maximizable:true,
                    resizable:true,
                    href:'report/storage/showSalesamountDetail.do?begindate='+begindate+"&enddate="+enddate+"&col="+column+"&value="+value ,
                });

            }


    	</script>
  </body>
</html>
