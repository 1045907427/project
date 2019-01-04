<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>部门费用明细报表</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
   	<div id="departmentCosts-query-deptCostsMonthReport">
    	<form action="" id="departmentCosts-query-form-deptCostsMonthReport" method="post">
    		<table>
    			<tr>
    				<td>年份:</td>
    				<td><input id="departmentCosts-query-year" type="text" name="year" style="width:150px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy',maxDate:'${currentyear }'})" value="${currentyear }"/> </td>
    				<td>部门名称:</td>
    				<td><input id="departmentCosts-query-deptid" type="text" name="deptid" style="width: 200px;"/></td>
    			</tr>
    			<tr>
    				<td>小计列:</td>
    				<td>
    					<input type="checkbox" class="costsgroupcols" value="deptid"/>部门
    					<input type="checkbox" class="costsgroupcols" value="subjectid"/>科目
    					<input type="checkbox" class="costsgroupcols" value="year"/>年份
    					<input id="departmentCosts-query-groupcols" type="hidden" name="groupcols"/>
    				</td>
    				<td colspan="2">    					
    					<a href="javaScript:void(0);" id="departmentCosts-query-buton" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="[Alt+Q]查询">查询</a>
						<a href="javaScript:void(0);" id="departmentCosts-reload-buton" class="easyui-linkbutton" iconCls="icon-reload" plain="true" title="[Alt+R]重置">重置</a>
    					<security:authorize url="/journalsheet/costsFee/exportDepartmentCostsMonthReportData.do">
							<a href="javaScript:void(0);" id="departmentCosts-export-buton" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
							<a href="javaScript:void(0);" id="departmentCosts-export-excel" style="display:none">导出</a>
						</security:authorize>
    				</td>	    						
    			</tr>
    		</table>
    	</form>
    </div>
    <table id="departmentCosts-table-deptCostsMonthReport"></table>
    <script type="text/javascript">
		var footerobject=null;
    	$(function(){
    		var columnJson = $("#departmentCosts-table-deptCostsMonthReport").createGridColumnLoad({
				frozenCol : [[
								//{field:'idok',checkbox:true,isShow:true}
			    			]],
				commonCol : [[
							  {field:'deptid',title:'部门名称',width:80,sortable:true,isShow:true,
								  	formatter:function(value,rowData,rowIndex){
					        			return rowData.deptname;
							  		}
							  },
							  {field:'year',title:'年份',resizable:true,sortable:true,isShow:true,align:'center',width:45},
							  {field:'subjectid',title:'科目',width:80,sortable:true,isShow:true,
									formatter:function(value,rowData,rowIndex){
					        			return rowData.subjectname;
							  		}
							  },
							  <c:forEach var="month" begin="1" end="12">
								  {field:'month_${month < 10 ?"0":"" }${month }',title:'${month}月',align:'right',resizable:true,sortable:true,width:75,
								  	formatter:function(value,rowData,rowIndex){
					        			return formatterMoney(value);
						        	}
								  },
    						  </c:forEach>
							  {field:'totalamount',title:'合计',resizable:true,sortable:true,align:'right',isShow:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  }
				             ]]
			});
			$("#departmentCosts-table-deptCostsMonthReport").datagrid({ 
		 		authority:columnJson,
		 		frozenColumns: columnJson.frozen,
				columns:columnJson.common,
		 		fit:true,
		 		title:"部门费用明细报表",
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'pageRowId',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
				toolbar:'#departmentCosts-query-deptCostsMonthReport',
				pageSize:50,
				pageList:[30,50,100,200],
				onDblClickRow:function(rowIndex, rowData){
				},
				onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						footerobject = footerrows[0];
					}
		 		},
				onCheckAll:function(){
		 			countTotalAmount();
				},
				onUncheckAll:function(){
					countTotalAmount();
				},
				onCheck:function(){
					countTotalAmount();
				},
				onUncheck:function(){
					countTotalAmount();
				}
			}).datagrid("columnMoving");
			
			$("#departmentCosts-query-supplierid").supplierWidget();
			$("#departmentCosts-query-deptid").widget({
				width:200,
				name:'t_js_departmentcosts',
				col:'deptid',
				singleSelect:true,
				onlyLeafCheck:false
			});
			$("#departmentCosts-query-buton").click(function(){
				//把form表单的name序列化成JSON对象
				reloadColumn();
	      		var queryJSON = $("#departmentCosts-query-form-deptCostsMonthReport").serializeJSON();
	      		$("#departmentCosts-table-deptCostsMonthReport").datagrid({
	      			url: 'journalsheet/costsFee/showDepartmentCostsMonthReportData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			$("#departmentCosts-reload-buton").click(function(){
				$("#departmentCosts-query-form-deptCostsMonthReport")[0].reset();
				$("#departmentCosts-table-deptCostsMonthReport").datagrid('loadData',{total:0,rows:[]});
				$("#departmentCosts-query-deptid").widget('clear');
				$("#departmentCosts-query-groupcols").val("");
			});
			$("#departmentCosts-export-buton").click(function(){
				var year=$("#departmentCosts-query-year").val()||"";
				var deptname=$("#departmentCosts-query-deptid").widget("getText")|| "" ;
				var title="部门费用明细报表";
				if($.trim(year)!=""){
					year=year+"年 ";
				}
				title=year+$.trim(deptname)+title;
				$("#departmentCosts-export-excel").Excel('export',{
					queryForm: "#departmentCosts-query-form-deptCostsMonthReport", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:title,
			 		url:'journalsheet/costsFee/exportDepartmentCostsMonthReportData.do'
				});
				$("#departmentCosts-export-excel").trigger("click");
			});

	    	$(".costsgroupcols").click(function(){
				var cols = "";
				$("#departmentCosts-query-groupcols").val("");
				$(".costsgroupcols").each(function(){
					if($(this).attr("checked")){
						if(cols==""){
							cols = $(this).val();
						}else{
							cols += ","+$(this).val();
						}
						$("#departmentCosts-query-groupcols").val(cols);
					}
				});
			});
    	});
    </script>
    <script type="text/javascript">
    function countTotalAmount(){
		var rows =  $("#departmentCosts-table-deptCostsMonthReport").datagrid('getChecked');
		if(null==rows || rows.length==0){
       		var foot=[];
			if(null!=footerobject){
        		foot.push(emptyChooseObjectFoot());
        		foot.push(footerobject);
    		}
			$("#departmentCosts-table-deptCostsMonthReport").datagrid("reloadFooter",foot);
       		return false;
   		}
		var totalamount = 0;
		
		 <c:forEach var="month" begin="1" end="12">
		  	var month_${month }=0;
		 </c:forEach>
		for(var i=0;i<rows.length;i++){
			totalamount = Number(totalamount)+Number(rows[i].totalamount == undefined ? 0 : rows[i].totalamount);
			<c:forEach var="month" begin="1" end="12">
				month_${month } = Number(month_${month })+Number( rows[i].month_${month < 10 ?"0":"" }${month }== undefined ? 0 : rows[i].month_${month < 10 ?"0":"" }${month });
    		</c:forEach>
    		
		}
		var foot=[{subjectname:'选中金额',totalamount:totalamount    				
    				<c:forEach var="month" begin="1" end="12">
						,month_${month < 10 ?"0":"" }${month } : month_${month }
					</c:forEach>
					
    			}];
		if(null!=footerobject){
    		foot.push(footerobject);
		}
		$("#departmentCosts-table-deptCostsMonthReport").datagrid("reloadFooter",foot);
	}
	function emptyChooseObjectFoot(){
		var totalamount = 0;
		<c:forEach var="month" begin="1" end="12">
	  		var month_${month }=0;
		</c:forEach>
		var foot={subjectname:'选中金额',totalamount:totalamount			
			<c:forEach var="month" begin="1" end="12">
				,month_${month < 10 ?"0":"" }${month } : month_${month }
			</c:forEach>
			
		};
		return foot;
	}
	function reloadColumn(){
    	var cols=$("#departmentCosts-query-groupcols").val()||"";
    	if(cols==null || cols.length==0){
			$("#departmentCosts-table-deptCostsMonthReport").datagrid('showColumn', "deptid");
			$("#departmentCosts-table-deptCostsMonthReport").datagrid('showColumn', "year");
			$("#departmentCosts-table-deptCostsMonthReport").datagrid('showColumn', "subjectid");
			return;
    	}
		cols=cols.toLowerCase();
		$("#departmentCosts-table-deptCostsMonthReport").datagrid('hideColumn', "deptid");
		$("#departmentCosts-table-deptCostsMonthReport").datagrid('hideColumn', "year");
		$("#departmentCosts-table-deptCostsMonthReport").datagrid('hideColumn', "subjectid");
		var isnotgroup=true;
		if(cols.indexOf("deptid")>=0){
			$("#departmentCosts-table-deptCostsMonthReport").datagrid('showColumn', "deptid");
			isnotgroup=false;
		}
		if(cols.indexOf("year")>=0){
			$("#departmentCosts-table-deptCostsMonthReport").datagrid('showColumn', "year");			
			isnotgroup=false;	
		}
		if(cols.indexOf("subjectid")>=0){
			$("#departmentCosts-table-deptCostsMonthReport").datagrid('showColumn', "subjectid");	
			isnotgroup=false;			
		}
		if(isnotgroup){
			$("#departmentCosts-table-deptCostsMonthReport").datagrid('showColumn', "deptid");
			$("#departmentCosts-table-deptCostsMonthReport").datagrid('showColumn', "year");
			$("#departmentCosts-table-deptCostsMonthReport").datagrid('showColumn', "subjectid");
		}
	}
    </script>
  </body>
</html>
