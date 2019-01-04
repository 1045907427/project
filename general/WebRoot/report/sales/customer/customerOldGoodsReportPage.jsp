<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户新品销售情况表</title>
    <%@include file="/include.jsp" %>  
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>   
  </head>
  
  <body>
    	<table id="report-datagrid-customerOldGoods"></table>
    	<div id="report-toolbar-customerOldGoods" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/sales/exportCustomerOldGoodsReportData.do">
                    <a href="javaScript:void(0);" id="report-buttons-customerOldGoodsPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                </security:authorize>
                <div id="dialog-autoexport"></div>
            </div>
    		<form action="" id="report-query-form-customerOldGoods" method="post">
	    		<table class="querytable">

	    			<tr>
	    				<td>客户名称：</td>
	    				<td><input type="text" id="report-query-customerid" name="customerid" style="width: 225px;"/></td>
	    				<td>总店名称：</td>
	    				<td><input type="text" id="report-query-pcustomerid" name="pcustomerid" style="width: 180px;"/></td>
	    				<td>所属区域：</td>
	    				<td><input id="report-query-salesarea" type="text" name="salesarea" style="width: 205px;"/></td>
	    			</tr>
	    			<tr>
	    				<td>业务日期:</td>
	    				<td><input type="text" name="businessdate1" value="${day1 }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
	    				<td>客户业务员：</td>
	    				<td><input id="report-query-salesuser" type="text" name="salesuser" style="width: 180px;"/></td>
	    			</tr>
	    			<tr>
	    				<td>商品名称：</td>
	    				<td>
	    					<input type="text" id="report-query-goodsid" name="goodsid" style="width: 225px;"/>
	    				</td>
	    				<td>品牌名称：</td>
	    				<td>
	    					<input type="text" id="report-query-brandid" name="brandid" />
	    				</td>
                        <td></td>
	    				<td>
	    					<a href="javaScript:void(0);" id="report-queay-customerOldGoods" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="report-reload-customerOldGoods" class="button-qr">重置</a>

	    				</td>
	    			</tr>
	    		</table>
	    	</form>
    	</div>
    	<div id="report-dialog-customerOldGoodsDetail"></div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-customerOldGoods").serializeJSON();
    		$(function(){
                $("#report-buttons-customerOldGoodsPage").click(function(){
                    var queryJSON = $("#report-query-form-customerOldGoods").serializeJSON();
                    //获取排序规则
                    var objecr  = $("#report-datagrid-customerOldGoods").datagrid("options");
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryJSON["sort"] = objecr.sortName;
                        queryJSON["order"] = objecr.sortOrder;
                    }
                    var queryParam = JSON.stringify(queryJSON);
                    var url = "report/sales/exportCustomerOldGoodsReportData.do";
                    exportByAnalyse(queryParam,"客户老品销售报表","report-datagrid-customerOldGoods",url);
                });

    			var tableColumnListJson = $("#report-datagrid-customerOldGoods").createGridColumnLoad({
					frozenCol : [[
									{field:'idok',checkbox:true,isShow:true}
				    			]],
					commonCol : [[
								  {field:'customerid',title:'客户编号',sortable:true,width:60},
								  {field:'customername',title:'客户名称',width:210},
								  {field:'pcustomerid',title:'总店编码',sortable:true,width:60,hidden:true},
								  {field:'pcustomername',title:'总店名称',width:60},
								  {field:'salesuser',title:'客户业务员',width:70,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.salesusername;
						        	}
								  },
								  {field:'salesarea',title:'所属区域',sortable:true,width:80,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.salesareaname;
						        	}
								  },
								  {field:'goodsid',title:'商品编码',sortable:true,width:70},
								  {field:'goodsname',title:'商品名称',sortable:true,width:150},
								  {field:'brandid',title:'品牌名称',sortable:true,width:80,
									formatter:function(value,rowData,rowIndex){
						        		return rowData.brandname;
						        	}
								  },
								  {field:'unitnum',title:'数量',width:50,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'auxnumdetail',title:'辅数量',width:70,sortable:true,align:'right'},
								  {field:'taxamount',title:'销售金额',resizable:true,align:'right',sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  }
							 ]]
				});
    			$("#report-datagrid-customerOldGoods").datagrid({ 
			 		authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
		  	 		pageSize:100,
		  	 		sortName:'customerid',
					sortOrder:'asc',
					toolbar:'#report-toolbar-customerOldGoods',
		  	 		onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SR_footerobject = footerrows[0];
							reportCountTotalAmount('customername',this);
						}
			 		},
					onCheckAll:function(){
						reportCountTotalAmount('customername',this);
					},
					onUncheckAll:function(){
						reportCountTotalAmount('customername',this);
					},
					onCheck:function(){
						reportCountTotalAmount('customername',this);
					},
					onUncheck:function(){
						reportCountTotalAmount('customername',this);
					}
				}).datagrid("columnMoving");
				$("#report-query-customerid").customerWidget({});
				$("#report-query-pcustomerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
					width:180,
					singleSelect:true
				});
				$("#report-query-salesarea").widget({
					referwid:'RT_T_BASE_SALES_AREA',
					width:205,
					onlyLeafCheck:false,
					singleSelect:true
				});
				$("#report-query-goodsid").goodsWidget({});
				$("#report-query-brandid").widget({
                    width:180,
					referwid:'RL_T_BASE_GOODS_BRAND',
					onlyLeafCheck:false,
					singleSelect:true
				});
				$("#report-query-salesuser").widget({
					referwid:'RL_T_BASE_PERSONNEL_SELLER',
					width:180,
					onlyLeafCheck:false,
					singleSelect:true
				});
				//回车事件
				controlQueryAndResetByKey("report-queay-customerOldGoods","report-reload-customerOldGoods");
				
				//查询
				$("#report-queay-customerOldGoods").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-customerOldGoods").serializeJSON();
		      		$("#report-datagrid-customerOldGoods").datagrid({
		      			url: 'report/sales/showCustomerOldGoodsReportData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-customerOldGoods").click(function(){
					$("#report-query-customerid").customerWidget("clear");
					$("#report-query-pcustomerid").widget("clear");
					$("#report-query-salesarea").widget("clear");
					$("#report-query-goodsid").goodsWidget("clear");
					$("#report-query-brandid").widget("clear");
					$("#report-query-salesuser").widget("clear");
					$("#report-query-form-customerOldGoods").form("reset");
					var queryJSON = $("#report-query-form-customerOldGoods").serializeJSON();
		       		$("#report-datagrid-customerOldGoods").datagrid('loadData',{total:0,rows:[]});
				});

    		});
    		 function reportCountTotalAmount(col,datagrid){
			 	var rows =  $("#"+datagrid.id+"").datagrid('getChecked');
				var unitnum = 0;
				var taxamount = 0;
				for(var i=0;i<rows.length;i++){
					unitnum = Number(unitnum)+Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
					taxamount = Number(taxamount)+Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
				}
				var obj = {unitnum:unitnum,taxamount:taxamount};
		    	if(col != ""){
					obj[col] = '选中合计';
				}else{
					obj['goodsname'] = '选中合计';
				}
				var foot=[];
				foot.push(obj);
				if(null!=SR_footerobject){
		    		foot.push(SR_footerobject);
				}
				$("#"+datagrid.id+"").datagrid("reloadFooter",foot);
  			 }
    	</script>
  </body>
</html>
