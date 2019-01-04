<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>部门收入报表</title>
    <%@include file="/include.jsp" %>
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
   	<div id="deptIncome-query-reportList" style="padding:2px;height:auto">
    	<form action="" id="deptIncome-query-form-reportList" method="post">
    		<table>
                <tr>
                    <security:authorize url="/journalsheet/deptincome/exportDeptIncomeReportDataBtn.do">
                        <a href="javaScript:void(0);" id="deptIncome-export-buton" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                        <a href="javaScript:void(0);" id="deptIncome-export-excel" style="display:none">导出</a>
                    </security:authorize>
                </tr>
    			<tr>
    				<td>业务日期:</td>
    				<td><input id="deptIncome-query-businessdate1" type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${firstday }"/> 到 <input id="deptIncome-query-businessdate2" type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"/></td>
    				<td>部门名称:</td>
    				<td><input id="deptIncome-query-deptid" type="text" name="deptid" style="width: 100px;"/></td>
    				<td></td>
    			</tr>
    			<tr>
    				<td>收入科目:</td>
    				<td><input id="deptIncome-query-subjectid" type="text" name="subjectid" style="width: 100px;"/></td>    				
    				<td colspan="2"></td>
                    <td class="tdbutton">
    					<a href="javaScript:void(0);" id="deptIncome-query-buton" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="deptIncome-reload-buton" class="button-qr">重置</a>
    				</td>	    						
    			</tr>
    		</table>
    	</form>
    </div>
    <table id="deptIncome-table-reportList"></table>
    <div class="easyui-menu" id="deptIncome-contextMenu" style="display: none;">
    	<div id="deptIncome-contextMenu-export">导出</div>
    	<div id="deptIncome-contextMenu-expand">展开</div>
    	<div id="deptIncome-contextMenu-collapse">折叠</div>
    </div>
    <div style="display:none">
    	<div id="deptIncome-reportDetailList-div"></div>
    </div>
    <script type="text/javascript">
		var footerobject=null;
    	$(function(){
    		var queryInitJSON = $("#deptIncome-query-form-reportList").serializeJSON();
			$("#deptIncome-table-reportList").treegrid({ 
				columns:[[
						  {field:'deptidno',title:'部门编号',width:80,sortable:true,hidden:true,
							  	formatter:function(value,rowData,rowIndex){
									return rowData.deptid;
									}
							  },
							  {field:'name',title:'部门/科目名称',width:200,sortable:true},
							  {field:'subamount',title:'科目金额',resizable:true,sortable:true,align:'right',isShow:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'totalamount',title:'金额',resizable:true,sortable:true,align:'right',isShow:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  }
				             ]],
		 		fit:true,
		 		title:"",
		 		method:'post',
		 		rownumbers:true,
		 		idField:'rid',
		 		treeField:'name',
		 		singleSelect:true,
		 		showFooter: true,
		 		url: 'journalsheet/deptincome/showDeptIncomeReportData.do',
		 		queryParams:queryInitJSON,
				toolbar:'#deptIncome-query-reportList',
				onDblClickRow:function(row){
					$("#deptIncome-table-reportList").treegrid("expand",row.rid);
				},
				onContextMenu: function(e, rowData){
    				e.preventDefault();
    				$(this).treegrid('select', rowData.rid);
                    $("#deptIncome-contextMenu").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
			});
			$("#deptIncome-query-deptid").widget({
				width:200,
				name:'t_js_dept_income',
				col:'deptid',
				singleSelect:true,
				onlyLeafCheck:false
			});
			$("#deptIncome-query-subjectid").widget({
    			referwid:'RL_T_BASE_SUBJECT',
    			width:225,
				singleSelect:true,
    			treePName:false,
    			treeNodeDataUseNocheck:true,
	   			param:[
	   			       {field:'typecode',op:'equal',value:'INCOME_SUBJECT'}
	   			],
				onlyLeafCheck:false
    		});
			$("#deptIncome-query-buton").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#deptIncome-query-form-reportList").serializeJSON();
	      		$("#deptIncome-table-reportList").treegrid('load', queryJSON);
			});
			$("#deptIncome-reload-buton").click(function(){
				$("#deptIncome-query-form-reportList")[0].reset();
				$("#deptIncome-table-reportList").treegrid('loadData',[]);
				$("#deptIncome-query-deptid").widget('clear');
				$("#deptIncome-query-subjectid").widget('clear');
			});
			$("#deptIncome-contextMenu-export").click(function(){
				var row = $("#deptIncome-table-reportList").treegrid('getSelected');
				if(row.type=='1'){
					var sdeptid = row.id;
					var date1=$("#deptIncome-query-businessdate1").val()||"";
					var date2=$("#deptIncome-query-businessdate2").val()||"";
					var deptname = row.name ;
					var title="";
					title=date1+(date1!=""&&date2!=""?"至":"")+date2;
					title=title+$.trim(deptname)+"部门收入报表";
					$("#deptIncome-export-excel").Excel('export',{
						queryForm: "#deptIncome-query-form-reportList", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
				 		type:'exportUserdefined',
				 		name:title,
				 		url:'journalsheet/deptincome/exportDeptIncomeReportData.do?sdeptid='+sdeptid
					});
					$("#deptIncome-export-excel").trigger("click");
				}else if(row.type=='2'){
					$.messager.alert("提醒","请选择部门导出。");
				}
			});
			$("#deptIncome-contextMenu-expand").click(function(){
				var row = $("#deptIncome-table-reportList").treegrid('getSelected');
				$("#deptIncome-table-reportList").treegrid("expandAll",row.rid);
			});
			$("#deptIncome-contextMenu-collapse").click(function(){
				var row = $("#deptIncome-table-reportList").treegrid('getSelected');
				$("#deptIncome-table-reportList").treegrid("collapseAll",row.rid);
			});
			$("#deptIncome-export-buton").click(function(){
				var date1=$("#deptIncome-query-businessdate1").val()||"";
				var date2=$("#deptIncome-query-businessdate2").val()||"";
				var deptname=$("#deptIncome-query-deptid").widget("getText")|| "" ;
				var title="";
				title=date1+(date1!=""&&date2!=""?"至":"")+date2;
				title=title+$.trim(deptname)+"部门收入报表";
				$("#deptIncome-export-excel").Excel('export',{
					queryForm: "#deptIncome-query-form-reportList", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:title,
			 		url:'journalsheet/deptincome/exportDeptIncomeReportData.do'
				});
				$("#deptIncome-export-excel").trigger("click");
			});
    	});
    </script>
  </body>
</html>
