<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>品牌业务员考核统计报表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-branduserAssess"></table>
    	<div id="report-toolbar-branduserAssess">
    		<form action="" id="report-query-form-branduserAssess" method="post">
    		<table class="querytable">
                <tr>
                    <security:authorize url="/report/finance/branduserAssessReportExport.do">
                        <a href="javaScript:void(0);" id="report-buttons-branduserAssessPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                    </security:authorize>
                </tr>
    			<tr>
    				<td>日期:</td>
    				<td><input name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" value="${today }"/></td>
    				<td>品牌业务员:</td>
    				<td><input type="text" id="report-query-branduser" name="branduser"/></td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="report-query-branduserAssess" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-branduserAssess" class="button-qr">重置</a>
    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-dialog-branduserAssessDetail"></div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-branduserAssess").serializeJSON();
    		$(function(){
    			var tableColumnListJson = $("#report-datagrid-branduserAssess").createGridColumnLoad({
					frozenCol : [[]],
					commonCol : [[
						  {field:'branduser',title:'人员名称',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.brandusername;
				        	}
						  },
						  {field:'wdtargetamount',title:'回笼目标金额',resizable:true,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'wdaccomplishamount',title:'回笼实绩金额',resizable:true,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'retaccomplishamount',title:'实绩退货',resizable:true,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'retstandardamount',title:'退货标准',resizable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'superretamount',title:'超退货部分',resizable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'checkbonusbase',title:'核算奖金基数',resizable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'totalscore',title:'合计得分',resizable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'wdbonusamount',title:'回笼奖金金额',resizable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'kpitargetamount',title:'kpi目标',resizable:true,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'realaccomplish',title:'实绩完成',resizable:true,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'kpibonusamount',title:'kpi奖金金额',resizable:true,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'monthtotalamount',title:'本月合计奖金',resizable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
			         ]]
				});
    			$("#report-datagrid-branduserAssess").datagrid({ 
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
		  	 		pageSize:100,		  
					toolbar:'#report-toolbar-branduserAssess'
				}).datagrid("columnMoving");
				$("#report-query-branduser").widget({
					referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
		    		width:130,
					singleSelect:true
				});
				//回车事件
				controlQueryAndResetByKey("report-query-branduserAssess","report-reload-branduserAssess");
				
				//查询
				$("#report-query-branduserAssess").click(function(){
		      		var queryJSON = $("#report-query-form-branduserAssess").serializeJSON();
		      		$("#report-datagrid-branduserAssess").datagrid({
		      			url: 'report/finance/getBranduserAssessReportData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-branduserAssess").click(function(){
					$("#report-query-branduser").widget("clear");
					$("#report-query-form-branduserAssess")[0].reset();
					var queryJSON = $("#report-query-form-branduserAssess").serializeJSON();
		       		$("#report-datagrid-branduserAssess").datagrid('loadData',{total:0,rows:[]});
				});
				
				$("#report-buttons-branduserAssessPage").Excel('export',{
					queryForm: "#report-query-form-branduserAssess", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'品牌业务员考核统计报表',
			 		url:'report/finance/exportBranduserAssessReportData.do'
				});
    		});
    		
    	</script>
  </body>
</html>
