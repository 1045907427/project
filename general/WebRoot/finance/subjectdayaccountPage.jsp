<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分科目开单流水账</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north'" style="height: 70px;">
    		<form action="" id="finance-form-QuerySubjectDayAccount" method="post" style="padding: 5px;">
    			科目名称:<input id="finance-widget-subjectidquery" name="subjectid" type="text" style="width: 120px;"/>
    			所属部门:<input id="finance-widget-supplierdeptquery" name="supplierdeptid" type="text"/></br>
    			业务日期：<input id="begintime" name="begintime" class="Wdate" style="width:100px;height: 20px;" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-M-d',minDate:'1980-01-01',maxDate:'%y-%M-%ld'})"/>
   				到&nbsp<input id="endtime" name="endtime" class="Wdate" style="width:100px;height: 20px;" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-M-d',minDate:'1980-01-01',maxDate:'%y-%M-%ld'})" />
	    		<a href="javaScript:void(0);" id="finance-query-subjectList" class="easyui-linkbutton" iconCls="icon-search" title="查询">查询</a>
    		</form>
    	</div>
    	<div data-options="region:'center',border:false" style="border: 0px;">
    		<table id="finance-table-subjectlist"></table>
    	</div>
    </div>
    <script type="text/javascript">
    	//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var expensesEnteringListColJson=$("#finance-table-subjectlist").createGridColumnLoad({
	     	name:'t_finance_expensesentering',
	     	frozenCol:[[]],
	     	commonCol:[[
				{field:'id',title:'编码',width:80,sortable:true,hidden:true},
				{field:'supplierid',title:'供应商编码',width:80,sortable:true,hidden:true},
				{field:'suppliername',title:'供应商名称',width:220,sortable:true},
				{field:'supplierdeptid',title:'所属部门',width:100,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.supplierdeptName;
					}
				},
				{field:'businessdate',title:'业务日期',width:80,sortable:true},
				{field:'subjectid',title:'科目编码',width:80,sortable:true,hidden:true},
				{field:'subjectname',title:'科目名称',width:80,sortable:true},
				{field:'subjectexpenses',title:'科目费用',width:100,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
					}
				},
				{field:'remark',title:'备注',width:100,sortable:true},
				{field:'adduserid',title:'制单人编码',width:80,sortable:true,hidden:true},
				{field:'addusername',title:'制单人',width:80,sortable:true,hidden:true},
				{field:'addtime',title:'制单日期',width:130,sortable:true,hidden:true}
			]]
	     });
	     
	     //科目查询
	  	$("#finance-widget-subjectidquery").widget({
	  		width:120,
			name:'t_finance_expensesentering',
			col:'subjectid',
			singleSelect:true,
			required:true
		});
		$("#finance-widget-supplierdeptquery").widget({
	  		width:150,
	  		name:'t_finance_expensesentering',
			col:'supplierdeptid',
			singleSelect:true
		});
		$(function(){
			//查询
			$("#finance-query-subjectList").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#finance-form-QuerySubjectDayAccount").serializeJSON();
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#finance-table-subjectlist").datagrid("load",queryJSON);
			});
			$("#finance-table-subjectlist").datagrid({ 
     			authority:expensesEnteringListColJson,
	  	 		frozenColumns:[[]],
				columns:expensesEnteringListColJson.common,
	  	 		method:'post',
	  	 		title:'',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		singleSelect:true,
				pageSize:10,
				fit:true,
				pageList:[10,20,30,50,200],
	  	 		sortName:'businessdate',
	  	 		sortOrder:'asc',
	  	 		showFooter: true,
	  	 		singleSelect:true,
			    url:'basefiles/finance/getSubjectDayAccountListPage.do',
			    onLoadSuccess:function(data){
			    	var p = $('#finance-table-subjectlist').datagrid('getPager');  
				    $(p).pagination({  
				        beforePageText: '',//页数文本框前显示的汉字  
				        afterPageText: '',  
				        displayMsg: '显示第{from}家到{to}家供应商 总共{total}家供应商'
				    });
			    	if(data.rows.length>0){
			    	}else{
			    		$("#finance-table-subjectlist").datagrid("reloadFooter",{});
			    	}
    			},
    			rowStyler:function(index,row){
			    	if(row.subjectname=='小计'){
						return 'background-color:#E0ECFF;font-weight:bold;'; 
					}
				},
				onBeforeLoad:function(row,param){
			    	var flag = $("#finance-form-QuerySubjectDayAccount").form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    }
			}).datagrid("columnMoving");
			
		});
    </script>
  </body>
</html>
