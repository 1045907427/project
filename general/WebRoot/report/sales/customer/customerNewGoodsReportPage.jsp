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
    	<table id="report-datagrid-customerNewGoods"></table>
    	<div id="report-toolbar-customerNewGoods" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/sales/exportCustomerNewGoodsReportData.do">
                    <a href="javaScript:void(0);" id="report-buttons-customerNewGoodsPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                </security:authorize>
                <div id="dialog-autoexport"></div>
            </div>
    		<form action="" id="report-query-form-customerNewGoods" method="post">
	    		<table class="querytable">

	    			<tr>
	    				<td>客户名称：</td>
	    				<td><input type="text" id="report-query-customerid" name="customerid" style="width: 180px;"/></td>
	    				<td>总店名称：</td>
	    				<td><input type="text" id="report-query-pcustomerid" name="pcustomerid" style="width: 130px;"/></td>
	    				<td>所属区域：</td>
	    				<td><input id="report-query-salesarea" type="text" name="salesarea" style="width: 205px;"/></td>
	    			</tr>
	    			<tr>
	    				<td>客户业务员：</td>
	    				<td><input id="report-query-salesuser" type="text" name="salesuser" style="width: 130px;"/></td>
	    				<td>品牌名称：</td>
	    				<td>
	    					<input type="text" id="report-query-brandid" name="brandid" style="width: 130px;"/>
	    				</td>
	    				<td colspan="2"></td>
	    			</tr>
	    			<tr>
	    				<td>商品名称：</td>
	    				<td colspan="4">
	    					<input type="text" id="report-query-goodsid" name="goodsid" style="width: 425px;"/>
	    				</td>
	    				<td>
	    					<a href="javaScript:void(0);" id="report-queay-customerNewGoods" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="report-reload-customerNewGoods" class="button-qr">重置</a>

	    				</td>
	    			</tr>
	    		</table>
	    	</form>
    	</div>
    	<div id="report-dialog-customerNewGoodsDetail"></div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-customerNewGoods").serializeJSON();
            var total = "";
    		$(function(){
                $("#report-buttons-customerNewGoodsPage").click(function(){
                    if(total == 0){
                        $.messager.alert("提醒","请先查询后再做导出操作！");
                    }else if(total > 10000){
                        $.messager.alert("提醒","导出记录过多，不允许导出！");
                    }
                    else{
                        var queryJSON = $("#report-query-form-customerNewGoods").serializeJSON();
                        //获取排序规则
                        var objecr  = $("#report-datagrid-customerNewGoods").datagrid("options");
                        if(null != objecr.sortName && null != objecr.sortOrder ){
                            queryJSON["sort"] = objecr.sortName;
                            queryJSON["order"] = objecr.sortOrder;
                        }
                        var queryParam = JSON.stringify(queryJSON);
                        var url = "report/sales/exportCustomerNewGoodsReportData.do";
                        exportByAnalyse(queryParam,"客户新品销售报表","report-datagrid-customerNewGoods",url);
                    }

                });

                var tableColumnListJson = $("#report-datagrid-customerNewGoods").createGridColumnLoad({
					frozenCol : [[
									{field:'idok',checkbox:true,isShow:true}
				    			]],
					commonCol : [[
								  {field:'businessdate',title:'业务日期',sortable:true,width:80},
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
								  {field:'taxprice',title:'单价',resizable:true,align:'right',sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
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
    			$("#report-datagrid-customerNewGoods").datagrid({ 
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
					toolbar:'#report-toolbar-customerNewGoods',
		  	 		onLoadSuccess:function(data){
						var footerrows = $(this).datagrid('getFooterRows');
                        total = data.total ;
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
				}).datagrid('loadData',{total:0,rows:[]}).datagrid("columnMoving");
				$("#report-query-customerid").customerWidget({});
				$("#report-query-pcustomerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
					width:130,
					singleSelect:true
				});
				$("#report-query-salesarea").widget({
					referwid:'RT_T_BASE_SALES_AREA',
					width:205,
					onlyLeafCheck:false,
					singleSelect:true
				});
				$("#report-query-goodsid").goodsWidget({
					width:400
				});
				$("#report-query-brandid").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
					width:130,
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
				controlQueryAndResetByKey("report-queay-customerNewGoods","report-reload-customerNewGoods");
				
				//查询
				$("#report-queay-customerNewGoods").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-customerNewGoods").serializeJSON();
		      		$("#report-datagrid-customerNewGoods").datagrid({
		      			url: 'report/sales/showCustomerNewGoodsReportData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-customerNewGoods").click(function(){
					$("#report-query-customerid").customerWidget("clear");
					$("#report-query-pcustomerid").widget("clear");
					$("#report-query-salesarea").widget("clear");
					$("#report-query-goodsid").goodsWidget("clear");
					$("#report-query-brandid").widget("clear");
					$("#report-query-salesuser").widget("clear");
					$("#report-query-form-customerNewGoods").form("reset");
					var queryJSON = $("#report-query-form-customerNewGoods").serializeJSON();
		       		$("#report-datagrid-customerNewGoods").datagrid('loadData',{total:0,rows:[]});
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
