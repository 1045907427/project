<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分供应商收入分摊报表</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
   	<div id="deptSupplierIncome-query-supplierIncomeReport">
    	<form action="" id="deptSupplierIncome-query-form-supplierIncomeReport" method="post">
    		<table>
    			<tr>
    				<td>年份:</td>
    				<td><input id="deptSupplierIncome-query-year" type="text" name="year" style="width:150px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy',maxDate:'${currentyear }'})" value="${currentyear }"/> </td>
    				<td>供应商名称:</td>
    				<td><input id="deptSupplierIncome-query-supplierid" type="text" name="supplierid" style="width: 200px;"/></td>
    			</tr>
    			<tr>
    				<td>小计列:</td>
    				<td>		
    					<input type="checkbox" class="costsgroupcols" value="supplierid"/>供应商
    					<input type="checkbox" class="costsgroupcols" value="subjectid"/>科目
    					<input type="checkbox" class="costsgroupcols" value="year"/>年份
    					<input id="deptSupplierIncome-query-groupcols" type="hidden" name="groupcols"/>
    				</td>
    				<td colspan="2">    					
    					<a href="javaScript:void(0);" id="deptSupplierIncome-query-buton" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="[Alt+Q]查询">查询</a>
						<a href="javaScript:void(0);" id="deptSupplierIncome-reload-buton" class="easyui-linkbutton" iconCls="icon-reload" plain="true" title="[Alt+R]重置">重置</a>
    					<security:authorize url="/journalsheet/deptincome/exportDeptSupplierIncomeReportDataBtn.do">
							<a href="javaScript:void(0);" id="deptSupplierIncome-export-buton" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
							<a href="javaScript:void(0);" id="deptSupplierIncome-export-excel" style="display:none">导出</a>
						</security:authorize>
    				</td>	    						
    			</tr>
    		</table>
    	</form>
    </div>
    <table id="deptSupplierIncome-table-supplierIncomeReport"></table>
    <script type="text/javascript">
		var footerobject=null;
    	$(function(){
    		var columnJson = $("#deptSupplierIncome-table-supplierIncomeReport").createGridColumnLoad({
				frozenCol : [[
								//{field:'idok',checkbox:true,isShow:true}
			    			]],
				commonCol : [[
							  {field:'supplierid',title:'所属供应商',sortable:true,isShow:true,
								  	formatter:function(value,rowData,rowIndex){
					        			return rowData.suppliername;
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
			$("#deptSupplierIncome-table-supplierIncomeReport").datagrid({ 
		 		authority:columnJson,
		 		frozenColumns: columnJson.frozen,
				columns:columnJson.common,
		 		fit:true,
		 		title:"分供应商收入分摊报表",
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'pageRowId',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
				toolbar:'#deptSupplierIncome-query-supplierIncomeReport',
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
			
			$("#deptSupplierIncome-query-supplierid").supplierWidget();			

	    	$(".costsgroupcols").click(function(){
				var cols = "";
				$("#deptSupplierIncome-query-groupcols").val("");
				$(".costsgroupcols").each(function(){
					if($(this).attr("checked")){
						if(cols==""){
							cols = $(this).val();
						}else{
							cols += ","+$(this).val();
						}
						$("#deptSupplierIncome-query-groupcols").val(cols);
					}
				});
			});
	    	
			$("#deptSupplierIncome-query-buton").click(function(){
				//把form表单的name序列化成JSON对象
				reloadColumn();
	      		var queryJSON = $("#deptSupplierIncome-query-form-supplierIncomeReport").serializeJSON();
	      		$("#deptSupplierIncome-table-supplierIncomeReport").datagrid({
	      			url: 'journalsheet/deptincome/showDeptSupplierIncomeReportData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			$("#deptSupplierIncome-reload-buton").click(function(){
				$("#deptSupplierIncome-query-form-supplierIncomeReport")[0].reset();
				$("#deptSupplierIncome-table-supplierIncomeReport").datagrid('loadData',{total:0,rows:[]});
				$("#deptSupplierIncome-query-supplierid").supplierWidget('clear');
				$("#deptSupplierIncome-query-groupcols").val("");
			});
			$("#deptSupplierIncome-export-buton").click(function(){
				var year=$("#deptSupplierIncome-query-year").val()||"";
				var deptname=$("#deptSupplierIncome-query-supplierid").supplierWidget("getText")|| "" ;
				var title="分供应商费用明细报表";
				if($.trim(year)!=""){
					year=year+"年 ";
				}
				title=year+$.trim(deptname)+title;
				$("#deptSupplierIncome-export-excel").Excel('export',{
					queryForm: "#deptSupplierIncome-query-form-supplierIncomeReport", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:title,
			 		url:'journalsheet/deptincome/exportDeptSupplierIncomeReportData.do'
				});
				$("#deptSupplierIncome-export-excel").trigger("click");
			});
    	});
    </script>
    <script type="text/javascript">
    function countTotalAmount(){
		var rows =  $("#deptSupplierIncome-table-supplierIncomeReport").datagrid('getChecked');
		if(null==rows || rows.length==0){
       		var foot=[];
			if(null!=footerobject){
        		foot.push(emptyChooseObjectFoot());
        		foot.push(footerobject);
    		}
			$("#deptSupplierIncome-table-supplierIncomeReport").datagrid("reloadFooter",foot);
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
		$("#deptSupplierIncome-table-supplierIncomeReport").datagrid("reloadFooter",foot);
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
    	var cols=$("#deptSupplierIncome-query-groupcols").val()||"";
    	if(cols==null || cols.length==0){
			//$("#deptSupplierIncome-table-supplierIncomeReport").datagrid('showColumn', "deptid");
			$("#deptSupplierIncome-table-supplierIncomeReport").datagrid('showColumn', "supplierid");
			$("#deptSupplierIncome-table-supplierIncomeReport").datagrid('showColumn', "year");
			$("#deptSupplierIncome-table-supplierIncomeReport").datagrid('showColumn', "subjectid");
			return;
    	}
		cols=cols.toLowerCase();
		//$("#deptSupplierIncome-table-supplierIncomeReport").datagrid('hideColumn', "deptid");
		$("#deptSupplierIncome-table-supplierIncomeReport").datagrid('hideColumn', "supplierid");
		$("#deptSupplierIncome-table-supplierIncomeReport").datagrid('hideColumn', "year");
		$("#deptSupplierIncome-table-supplierIncomeReport").datagrid('hideColumn', "subjectid");
		var isnotgroup=true;
		/*
		if(cols.indexOf("deptid")>=0){
			$("#deptSupplierIncome-table-supplierIncomeReport").datagrid('showColumn', "deptid");
			isnotgroup=false;
		}
		*/
		if(cols.indexOf("supplierid")>=0){
			$("#deptSupplierIncome-table-supplierIncomeReport").datagrid('showColumn', "supplierid");
			isnotgroup=false;
		}
		if(cols.indexOf("year")>=0){
			$("#deptSupplierIncome-table-supplierIncomeReport").datagrid('showColumn', "year");			
			isnotgroup=false;	
		}
		if(cols.indexOf("subjectid")>=0){
			$("#deptSupplierIncome-table-supplierIncomeReport").datagrid('showColumn', "subjectid");	
			isnotgroup=false;			
		}
		if(isnotgroup){
			//$("#deptSupplierIncome-table-supplierIncomeReport").datagrid('showColumn', "deptid");
			$("#deptSupplierIncome-table-supplierIncomeReport").datagrid('showColumn', "supplierid");
			$("#deptSupplierIncome-table-supplierIncomeReport").datagrid('showColumn', "year");
			$("#deptSupplierIncome-table-supplierIncomeReport").datagrid('showColumn', "subjectid");
		}
	}
    </script>
  </body>
</html>
