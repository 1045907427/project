<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分供应商日常费用分摊报表</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
   	<div id="deptDailySupplierCost-query-supplierCostReport">
    	<form action="" id="deptDailySupplierCost-query-form-supplierCostReport" method="post">
    		<table>
    			<tr>
    				<td>年份:</td>
    				<td><input id="deptDailySupplierCost-query-year" type="text" name="year" style="width:150px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy',maxDate:'${currentyear }'})" value="${currentyear }"/> </td>
    				<td>供应商名称:</td>
    				<td><input id="deptDailySupplierCost-query-supplierid" type="text" name="supplierid" style="width: 200px;"/></td>
    			</tr>
    			<tr>
    				<td>小计列:</td>
    				<td>		
    					<input type="checkbox" class="costsgroupcols" value="supplierid"/>供应商
    					<input type="checkbox" class="costsgroupcols" value="subjectid"/>科目
    					<input type="checkbox" class="costsgroupcols" value="year"/>年份
    					<input id="deptDailySupplierCost-query-groupcols" type="hidden" name="groupcols"/>
    				</td>
    				<td colspan="2">    					
    					<a href="javaScript:void(0);" id="deptDailySupplierCost-query-buton" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="[Alt+Q]查询">查询</a>
						<a href="javaScript:void(0);" id="deptDailySupplierCost-reload-buton" class="easyui-linkbutton" iconCls="icon-reload" plain="true" title="[Alt+R]重置">重置</a>
    					<security:authorize url="/journalsheet/deptdailycost/exportDeptDailySupplierCostReportDataBtn.do">
							<a href="javaScript:void(0);" id="deptDailySupplierCost-export-buton" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
							<a href="javaScript:void(0);" id="deptDailySupplierCost-export-excel" style="display:none">导出</a>
						</security:authorize>
    				</td>	    						
    			</tr>
    		</table>
    	</form>
    </div>
    <table id="deptDailySupplierCost-table-supplierCostReport"></table>
    <script type="text/javascript">
		var footerobject=null;
    	$(function(){
    		var columnJson = $("#deptDailySupplierCost-table-supplierCostReport").createGridColumnLoad({
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
			$("#deptDailySupplierCost-table-supplierCostReport").datagrid({ 
		 		authority:columnJson,
		 		frozenColumns: columnJson.frozen,
				columns:columnJson.common,
		 		fit:true,
		 		title:"分供应商日常费用分摊报表",
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'pageRowId',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
				toolbar:'#deptDailySupplierCost-query-supplierCostReport',
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
			
			$("#deptDailySupplierCost-query-supplierid").supplierWidget();			

	    	$(".costsgroupcols").click(function(){
				var cols = "";
				$("#deptDailySupplierCost-query-groupcols").val("");
				$(".costsgroupcols").each(function(){
					if($(this).attr("checked")){
						if(cols==""){
							cols = $(this).val();
						}else{
							cols += ","+$(this).val();
						}
						$("#deptDailySupplierCost-query-groupcols").val(cols);
					}
				});
			});
	    	
			$("#deptDailySupplierCost-query-buton").click(function(){
				//把form表单的name序列化成JSON对象
				reloadColumn();
	      		var queryJSON = $("#deptDailySupplierCost-query-form-supplierCostReport").serializeJSON();
	      		$("#deptDailySupplierCost-table-supplierCostReport").datagrid({
	      			url: 'journalsheet/deptdailycost/showDeptDailySupplierCostReportData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			$("#deptDailySupplierCost-reload-buton").click(function(){
				$("#deptDailySupplierCost-query-form-supplierCostReport")[0].reset();
				$("#deptDailySupplierCost-table-supplierCostReport").datagrid('loadData',{total:0,rows:[]});
				$("#deptDailySupplierCost-query-supplierid").supplierWidget('clear');
				$("#deptDailySupplierCost-query-groupcols").val("");
			});
			$("#deptDailySupplierCost-export-buton").click(function(){
				var year=$("#deptDailySupplierCost-query-year").val()||"";
				var deptname=$("#deptDailySupplierCost-query-supplierid").supplierWidget("getText")|| "" ;
				var title="分供应商费用明细报表";
				if($.trim(year)!=""){
					year=year+"年 ";
				}
				title=year+$.trim(deptname)+title;
				$("#deptDailySupplierCost-export-excel").Excel('export',{
					queryForm: "#deptDailySupplierCost-query-form-supplierCostReport", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:title,
			 		url:'journalsheet/deptdailycost/exportDeptDailySupplierCostReportData.do'
				});
				$("#deptDailySupplierCost-export-excel").trigger("click");
			});
    	});
    </script>
    <script type="text/javascript">
    function countTotalAmount(){
		var rows =  $("#deptDailySupplierCost-table-supplierCostReport").datagrid('getChecked');
		if(null==rows || rows.length==0){
       		var foot=[];
			if(null!=footerobject){
        		foot.push(emptyChooseObjectFoot());
        		foot.push(footerobject);
    		}
			$("#deptDailySupplierCost-table-supplierCostReport").datagrid("reloadFooter",foot);
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
		$("#deptDailySupplierCost-table-supplierCostReport").datagrid("reloadFooter",foot);
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
    	var cols=$("#deptDailySupplierCost-query-groupcols").val()||"";
    	if(cols==null || cols.length==0){
			//$("#deptDailySupplierCost-table-supplierCostReport").datagrid('showColumn', "deptid");
			$("#deptDailySupplierCost-table-supplierCostReport").datagrid('showColumn', "supplierid");
			$("#deptDailySupplierCost-table-supplierCostReport").datagrid('showColumn', "year");
			$("#deptDailySupplierCost-table-supplierCostReport").datagrid('showColumn', "subjectid");
			return;
    	}
		cols=cols.toLowerCase();
		//$("#deptDailySupplierCost-table-supplierCostReport").datagrid('hideColumn', "deptid");
		$("#deptDailySupplierCost-table-supplierCostReport").datagrid('hideColumn', "supplierid");
		$("#deptDailySupplierCost-table-supplierCostReport").datagrid('hideColumn', "year");
		$("#deptDailySupplierCost-table-supplierCostReport").datagrid('hideColumn', "subjectid");
		var isnotgroup=true;
		/*
		if(cols.indexOf("deptid")>=0){
			$("#deptDailySupplierCost-table-supplierCostReport").datagrid('showColumn', "deptid");
			isnotgroup=false;
		}
		*/
		if(cols.indexOf("supplierid")>=0){
			$("#deptDailySupplierCost-table-supplierCostReport").datagrid('showColumn', "supplierid");
			isnotgroup=false;
		}
		if(cols.indexOf("year")>=0){
			$("#deptDailySupplierCost-table-supplierCostReport").datagrid('showColumn', "year");			
			isnotgroup=false;	
		}
		if(cols.indexOf("subjectid")>=0){
			$("#deptDailySupplierCost-table-supplierCostReport").datagrid('showColumn', "subjectid");	
			isnotgroup=false;			
		}
		if(isnotgroup){
			//$("#deptDailySupplierCost-table-supplierCostReport").datagrid('showColumn', "deptid");
			$("#deptDailySupplierCost-table-supplierCostReport").datagrid('showColumn', "supplierid");
			$("#deptDailySupplierCost-table-supplierCostReport").datagrid('showColumn', "year");
			$("#deptDailySupplierCost-table-supplierCostReport").datagrid('showColumn', "subjectid");
		}
	}
    </script>
  </body>
</html>
