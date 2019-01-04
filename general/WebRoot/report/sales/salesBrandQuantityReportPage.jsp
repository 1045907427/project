<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售箱数统计报表</title>
    <%@include file="/include.jsp" %>  
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>   
  </head>
  
  <body>
    	<table id="report-datagrid-salesBrandQuantity"></table>
    	<div id="report-toolbar-salesBrandQuantity" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/sales/salesQuantityReportExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-salesBrandQuantityPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-salesBrandQuantity" method="post">
	    		<table class="querytable">
	    			<tr>
	    				<td>商品品牌:</td>
	    				<td><input type="text" id="report-query-brandid" name="brandid"/></td>
	    				<td>
	    					<a href="javaScript:void(0);" id="report-query-salesBrandQuantity" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="report-reload-salesBrandQuantity" class="button-qr">重置</a>

	    				</td>
	    			</tr>
	    		</table>
	    		<input type="hidden" name="businessdate1" value="${businessdate1 }"/>
	    		<input type="hidden" name="businessdate2" value="${businessdate2 }" />
	    		<input type="hidden" name="storageid" value="${storageid}" />
	    	</form>
    	</div>
    	<div id="report-dialog-salesBrandQuantityDetail"></div>
    	<script type="text/javascript">
			var SRBrand_footerobject  = null;
    		var initBrandQueryJSON = $("#report-query-form-salesBrandQuantity").serializeJSON();
    		$(function(){
    			var exporttitle="按仓库：[${storagename}],分品牌销售数量统计表";
    			var brandTableColumnListJson = $("#report-datagrid-salesBrandQuantity").createGridColumnLoad({
					frozenCol : [[
						{field:'idok',checkbox:true,isShow:true}
					]],
					commonCol : [[
						  {field:'brandid',title:'品牌编号',width:60,align:'center'},
						  {field:'brandname',title:'商品品牌',width:130},
						  {field:'auxunitnum',title:'销售总数量',width:80,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
				        		return formatterBigNumNoLen(value);
				        	}
						  },
						  {field:'auxunitname',title:'销售单位',width:60,align:'center'},
						  {field:'totalweight',title:'总重量(kg)',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'totalvolume',title:'总体积(m³)',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
			         ]]
				});
				
				$("#report-datagrid-salesBrandQuantity").datagrid({ 
			 		authority:brandTableColumnListJson,
			 		frozenColumns: brandTableColumnListJson.frozen,
					columns:brandTableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		pageSize:100,
		  	 		showFooter: true,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
					toolbar:'#report-toolbar-salesBrandQuantity',
					url: 'report/sales/showSalesQuantityReportData.do?groupby=brand',
					queryParams:initBrandQueryJSON,
					onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SRBrand_footerobject = footerrows[0];
							brandCountTotalAmount();
						}
			 		},
					onCheckAll:function(){
						brandCountTotalAmount();
					},
					onUncheckAll:function(){
						brandCountTotalAmount();
					},
					onCheck:function(){
						brandCountTotalAmount();
					},
					onUncheck:function(){
						brandCountTotalAmount();
					}
				}).datagrid("columnMoving");
				
				
				//回车事件
				controlQueryAndResetByKey("report-query-salesBrandQuantity","report-reload-salesBrandQuantity");
				
				//查询
				$("#report-query-salesBrandQuantity").click(function(){
		      		var queryJSON = $("#report-query-form-salesBrandQuantity").serializeJSON();
		      		$("#report-datagrid-salesBrandQuantity").datagrid({
		      			url: 'report/sales/showSalesQuantityReportData.do?groupby=brand',
		      			pageNumber:1,
						queryParams:queryJSON
		      		}).datagrid("columnMoving");
				});
				//重置
				$("#report-reload-salesBrandQuantity").click(function(){
					$("#report-query-brandid").widget("clear");
					$("#report-query-form-salesBrandQuantity")[0].reset();
					var queryJSON = $("#report-query-form-salesBrandQuantity").serializeJSON();
		       		$("#report-datagrid-salesBrandQuantity").datagrid('loadData',{total:0,rows:[]});
				});
				
				$("#report-buttons-salesBrandQuantityPage").Excel('export',{
					queryForm: "#report-query-form-salesBrandQuantity", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:exporttitle,
			 		url:'report/sales/exportSalesQuantityReportData.do?groupby=brand'
				});

				$("#report-query-brandid").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
		    		width:130,
					onlyLeafCheck:false,
					singleSelect:true
				});
				
    		});
    		function brandCountTotalAmount(){
    			var rows =  $("#report-datagrid-salesBrandQuantity").datagrid('getChecked');
    			var auxunitnum = 0;
        		var totalweight = 0;
        		var totalvolume= 0;
        		for(var i=0;i<rows.length;i++){
        			auxunitnum = Number(auxunitnum)+Number(rows[i].auxunitnum == undefined ? 0 : rows[i].auxunitnum);
        			totalweight = Number(totalweight)+Number(rows[i].totalweight == undefined ? 0 : rows[i].totalweight);  
        			totalvolume = Number(totalvolume)+Number(rows[i].totalvolume == undefined ? 0 : rows[i].totalvolume);      			
        		}
        		var foot=[{brandname:'选中合计',auxunitnum:formatterMoney(auxunitnum),totalweight:formatterMoney(totalweight),totalvolume:formatterMoney(totalvolume)
            			}];
        		if(null!=SRBrand_footerobject){
            		foot.push(SRBrand_footerobject);
        		}
        		$("#report-datagrid-salesBrandQuantity").datagrid("reloadFooter",foot);
    		}
    	</script>
  </body>
</html>
