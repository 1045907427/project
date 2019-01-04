<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>仓库出入库统计报表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-inOutReport"></table>
    	<div id="report-toolbar-inOutReport" style="padding: 0px">
    		<div class="buttonBG">
		        <security:authorize url="/report/storage/inOutReportExport.do">
		            <a href="javaScript:void(0);" id="report-buttons-inOutReportPage" class="easyui-linkbutton button-list" iconCls="button-export" plain="true" >全局导出</a>
		        </security:authorize>
		    </div>
    		<form action="" id="report-query-form-inOutReport" method="post">
    		<table>
    			<tr>
    				<td>日期:</td>
    				<td><input type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>仓库:</td>
    				<td><input type="text" id="report-query-storageid" name="storageid" /></td>
    				<td>是否出入库:</td>
    				<td>
    					<select name="isenterorout" style="width: 150px;">
    						<option></option>
    						<option value="1">是</option>
    						<option value="2">否</option>
    					</select>
    				</td>
    			</tr>
    			<tr>
    				<td>商品名称:</td>
    				<td><input type="text" id="report-query-goodsid" name="goodsid" /></td>
    				<td>品牌名称:</td>
    				<td><input type="text" id="report-query-brandid" name="brandid" style="width: 150px;"/></td>
    				<td>商品分类:</td>
    				<td><input type="text" id="report-query-goodssort" name="goodssort" style="width: 150px;"/></td>
    			</tr>
    			<tr>
    				<td>供应商:</td>
    				<td>
    					<input type="text" id="report-query-supplierid" name="supplierid" style="width: 225px;"/>
    				</td>
    				<td colspan="2"></td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="report-queay-inOutReport" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-inOutReport" class="button-qr">重置</a>
    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-inOutReport").serializeJSON();
    		var dataListJson = $("#report-datagrid-inOutReport").createGridColumnLoad({
    			name :'t_report_storage_inout',
				frozenCol : [[
							{field:'storageid',title:'仓库名称',width:80,
							 	formatter:function(value,rowData,rowIndex){
					        		return rowData.storagename;
					        	}
							  },
							  {field:'goodsid',title:'商品编码',width:70}
			    			]],
				commonCol : [[
							 {field:'goodsname',title:'商品名称',width:130,aliascol:'goodsid',sortable:true,},
							 {field:'barcode',title:'条形码',sortable:true,width:90,isShow:true},
							  {field:'brandid',title:'品牌名称',width:80,aliascol:'goodsid',sortable:true,
								 formatter:function(value,rowData,rowIndex){
						        		return rowData.brandname;
						        	}
							  },
							  {field:'goodssort',title:'商品分类',width:80,aliascol:'goodsid',sortable:true,
								  formatter:function(value,rowData,rowIndex){
						        		return rowData.goodssortname;
						        	}
							  },
							  {field:'boxnum',title:'箱装量',width:40,aliascol:'goodsid',align:'right',
								  formatter:function(value,rowData,rowIndex){
									  return formatterBigNumNoLen(value);
						          }
							  },
							  {field:'unitname',title:'单位',width:40},
							  {field:'initnum',title:'期初数量',width:80,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterBigNumNoLen(value);
					        	}
							  },
							  {field:'initauxnumdetail',title:'期初辅数量',width:90,sortable:true,align:'right',aliascol:'initnum',
								  formatter:function(value,rowData,rowIndex){
									  if(rowData.initnum!=0){
										  return value;
									  }
						          }
							  },
							  {field:'enternum',title:'入库数量',width:80,align:'right',sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterBigNumNoLen(value);
					        	}
							  },
							  {field:'enterauxnumdetail',title:'入库辅数量',width:90,sortable:true,align:'right',aliascol:'enternum',
								  formatter:function(value,rowData,rowIndex){
									  if(rowData.enternum!=0){
										  return value;
									  }
						          }  
							  },
							  {field:'outnum',title:'出库数量',width:80,align:'right',sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterBigNumNoLen(value);
					        	}
							  },
							  {field:'outauxnumdetail',title:'出库辅数量',width:90,sortable:true,align:'right',aliascol:'outnum',
								  formatter:function(value,rowData,rowIndex){
									  if(rowData.outnum!=0){
										  return value;
									  }
						          }  
							  },
							  {field:'endnum',title:'期末数量',width:90,align:'right',sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterBigNumNoLen(value);
					        	}
							  },
							  {field:'endauxnumdetail',title:'期末辅数量',width:95,sortable:true,align:'right',aliascol:'endnum',
								  formatter:function(value,rowData,rowIndex){
									  if(rowData.endnum!=0){
										  return value;
									  }
						          }   
							  }
				             ]]
			});
    		$(function(){
    			$("#report-datagrid-inOutReport").datagrid({ 
					authority:dataListJson,
		 			frozenColumns: dataListJson.frozen,
					columns:dataListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		pageSize:100,
		  	 		singleSelect:true,
					toolbar:'#report-toolbar-inOutReport'
				}).datagrid("columnMoving");
    			$("#report-query-supplierid").supplierWidget({
    				width:225,
    			});
				$("#report-query-goodsid").widget({
					referwid:'RL_T_BASE_GOODS_INFO',
		    		width:225,
					singleSelect:false
				});
				$("#report-query-brandid").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
		    		width:150,
					singleSelect:false
				});
				$("#report-query-storageid").widget({
					referwid:'RL_T_BASE_STORAGE_INFO',
		    		width:150,
					singleSelect:true
				});
				$("#report-query-goodssort").widget({
					referwid:'RL_T_BASE_GOODS_WARESCLASS',
		    		width:150,
					singleSelect:true,
					onlyLeafCheck:false
				});
				$("#report-query-deptid").widget({
					referwid:'RL_T_BASE_DEPARTMENT_SELLER',
		    		width:150,
					singleSelect:true,
					onlyLeafCheck:false
				});
				//回车事件
				controlQueryAndResetByKey("report-queay-inOutReport","report-reload-inOutReport");
				
				//查询
				$("#report-queay-inOutReport").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-inOutReport").serializeJSON();
		      		$("#report-datagrid-inOutReport").datagrid({
		      			url: 'report/storage/showInOutReportData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-inOutReport").click(function(){
					$("#report-query-goodsid").goodsWidget("clear");
					$("#report-query-storageid").widget("clear");
					$("#report-query-brandid").widget("clear");
					$("#report-query-deptid").widget("clear");
					$("#report-query-goodssort").widget("clear");
					
					$("#report-query-form-inOutReport").form("reset");
					var queryJSON = $("#report-query-form-inOutReport").serializeJSON();
		       		$("#report-datagrid-inOutReport").datagrid('loadData',{total:0,rows:[]});
				});
                //导出
                $("#report-buttons-inOutReportPage").click(function(){
                    //封装查询条件
                    var objecr  = $("#report-datagrid-inOutReport").datagrid("options");
                    var queryParam = objecr.queryParams;
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryParam["sort"] = objecr.sortName;
                        queryParam["order"] = objecr.sortOrder;
                    }
                    var queryParam = JSON.stringify(queryParam);
                    var url = "report/storage/exportStorageRecSendReportData.do";
                    exportByAnalyse(queryParam,"仓库出入库情况表","report-datagrid-inOutReport",url);
                });

    		});
    	</script>
  </body>
</html>
