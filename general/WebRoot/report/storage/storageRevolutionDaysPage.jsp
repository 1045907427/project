<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>库存周转天数报表报表</title>
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
                <security:authorize url="/report/storage/revolutionDaysExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-revolutionDaysPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-revolutionDays" method="post">
    		<table class="querytable">

                <tr>
                <tr>
                    <td>商品:</td>
                    <td colspan="2"><input type="text" id="report-query-goodsid" name="goodsid" style="width: 180px;"/></td>
                    <td>品牌名称:</td>
                    <td><input type="text" id="report-query-brandid" name="brandid" style="width:170px;"/></td>
                    <td>品牌部门:</td>
                    <td>
                        <input type="text" id="report-query-brandept" name="brandept" style="width: 125px;"/>
                    </td>
                </tr>
                <tr>
                    <td>年度:</td>
                    <td colspan="2">
                        <span>
                            <input type="text" class="easyui-numberspinner" style="width:80px;" required="required" data-options="min:2010,max:2050,editable:false" name="year" value="${year }"/>
                            月 份:<input type="text" class="easyui-numberspinner" style="width:55px;" required="required" data-options="min:1,max:12,editable:false" name="mon" value="${mon }"/>
                        </span>
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
                    <td>小计列：</td>
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
    	</form>
    	</div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-revolutionDays").serializeJSON();
    		var checkListJson = $("#report-datagrid-revolutionDays").createGridColumnLoad({
				commonCol : [[
							{field:'year',title:'年度',width:40},
							{field:'mon',title:'月份',width:40},
							 {field:'goodsid',title:'商品编码',width:60},
							 {field:'goodsname',title:'商品名称',width:200},
							 {field:'barcode',title:'条形码',sortable:true,width:90},
							 {field:'brandid',title:'品牌名称',width:80,sortable:true,
							 	formatter:function(value,rowData,rowIndex){
					        		return rowData.brandname;
					        	}
							 },
							 {field:'brandept',title:'品牌部门',width:80,sortable:true,
							 	formatter:function(value,rowData,rowIndex){
					        		return rowData.branddeptname;
					        	}
							 },
							 {field:'supplierid',title:'供应商编号',width:80,sortable:true},
							 {field:'suppliername',title:'供应商名称',width:200},
							 {field:'storageunitnum',title:'库存平均数量',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterBigNumNoLen(value);
					        	}
							 },
							 {field:'storageamount',title:'库存平均金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							 },
							 {field:'salesamount',title:'销售金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							 },
							 {field:'days',title:'周转天数',width:80,align:'right',sortable:true,
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
		      			url: 'report/storage/showStorageRevolutionDaysData.do',
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
				
				$("#report-buttons-revolutionDaysPage").Excel('export',{
					queryForm: "#report-query-form-revolutionDays", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'库存周转天数统计表',
			 		url:'report/storage/exportRevolutionDaysData.do'
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
				}
				else{
					$datagrid.datagrid('showColumn', "goodsid");
					$datagrid.datagrid('hideColumn', "barcode");
					$datagrid.datagrid('showColumn', "goodsname");
					$datagrid.datagrid('showColumn', "brandid");
					$datagrid.datagrid('showColumn', "brandept");
					$datagrid.datagrid('showColumn', "supplierid");
					$datagrid.datagrid('hideColumn', "suppliername");
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
    	</script>
  </body>
</html>
