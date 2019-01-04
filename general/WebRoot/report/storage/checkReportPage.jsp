<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>进销存汇总统计报表</title>
    <%@include file="/include.jsp" %>  
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>   
  </head>
  
  <body>
    	<table id="report-datagrid-checkReport"></table>
    	<div id="report-toolbar-checkReport" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/storage/checkReportExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-checkReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true">全局导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-checkReport" method="post">
    		<table class="querytable">
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" value="${firstDay }" style="width:90px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:90px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>仓库名称:</td>
    				<td><input type="text" id="report-query-storageid" name="storageid"/></td>
	    			<td>理货员:</td>
    				<td><input type="text" id="report-query-checkuserid" name="checkuserid"/></td>
    				<td>
    					<a href="javaScript:void(0);" id="report-queay-checkReport" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-checkReport" class="button-qr">重置</a>
    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-checkReport").serializeJSON();
    		var checkListJson = $("#report-datagrid-checkReport").createGridColumnLoad({
				frozenCol : [[
			    			]],
				commonCol : [[
							{field:'storageid',title:'仓库名称',width:80,sortable:true,
							 	formatter:function(value,rowData,rowIndex){
					        		return rowData.storagename;
					        	}
							 },
							 {field:'checkuserid',title:'理货员',width:80,sortable:true,
							 	formatter:function(value,rowData,rowIndex){
					        		return rowData.checkusername;
					        	}
							 },
							  {field:'checknum',title:'盘点数量',width:80,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterBigNumNoLen(value);
					        	}
							  },
							  {field:'truenum1',title:'第一次盘点正确数量',width:100,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterBigNumNoLen(value);
					        	}
							  },
							  {field:'truerate1',title:'第一次正确率',width:80,align:'right'},
							  {field:'truenum2',title:'第二次盘点正确数量',width:100,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterBigNumNoLen(value);
					        	}
							  },
							  {field:'truerate2',title:'第二次正确率',width:80,align:'right'},
							  {field:'truenum3',title:'第三次盘点正确数量',width:100,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterBigNumNoLen(value);
					        	}
							  },
							  {field:'truerate3',title:'第三次正确率',width:80,align:'right'},
							  {field:'truerate',title:'正确率',width:80,align:'right'}
				             ]]
			});
    		$(function(){
    			$("#report-datagrid-checkReport").datagrid({ 
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
					toolbar:'#report-toolbar-checkReport'
				}).datagrid("columnMoving");
				$("#report-query-checkuserid").widget({
					referwid:'RL_T_BASE_PERSONNEL_STORAGER',
					width:120,
					singleSelect:true
				});
				$("#report-query-storageid").widget({
					referwid:'RL_T_BASE_STORAGE_INFO',
					width:120,
					singleSelect:true
				});
				
				
				//回车事件
				controlQueryAndResetByKey("report-queay-checkReport","report-reload-checkReport");
				
				//查询
				$("#report-queay-checkReport").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-checkReport").serializeJSON();
		      		$("#report-datagrid-checkReport").datagrid({
		      			url: 'report/storage/showCheckReportData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-checkReport").click(function(){
					$("#report-query-checkuserid").widget("clear");
					$("#report-query-storageid").widget("clear");
					$("#report-query-form-checkReport").form("reset");
					var queryJSON = $("#report-query-form-checkReport").serializeJSON();
		       		$("#report-datagrid-checkReport").datagrid('loadData',{total:0,rows:[]});
				});
                //导出
                $("#report-buttons-checkReportPage").click(function(){
                    //封装查询条件
                    var objecr  = $("#report-datagrid-checkReport").datagrid("options");
                    var queryParam = objecr.queryParams;
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryParam["sort"] = objecr.sortName;
                        queryParam["order"] = objecr.sortOrder;
                    }
                    var queryParam = JSON.stringify(queryParam);
                    var url = "report/storage/exportCheckReportData.do";
                    exportByAnalyse(queryParam,"盘点报表",'report-datagrid-checkReport',url);
                });

    		});
    	</script>
  </body>
</html>
