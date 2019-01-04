<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>部门分供应商费用明细报表</title>
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
   	<div id="departmentCosts-query-deptSupplierCostsMonthReport" style="padding:2px;height:auto">
    	<form action="" id="departmentCosts-query-form-deptSupplierCostsMonthReport" method="post">
    		<table>
                <tr>
                    <security:authorize url="/journalsheet/costsFee/exportDeptSupplierCostsMonthReportData.do">
                        <a href="javaScript:void(0);" id="departmentCosts-export-buton" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                        <a href="javaScript:void(0);" id="departmentCosts-export-excel" style="display:none">导出</a>
                    </security:authorize>
                </tr>
    			<tr>
    				<td>年份:</td>
    				<td><input id="departmentCosts-query-year" type="text" name="year" style="width:150px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy',maxDate:'${currentyear }'})" value="${currentyear }"/> </td>
    				<td>供应商:</td>
    				<td><input id="departmentCosts-query-supplierid" type="text" name="supplierid" style="width: 200px;"/></td>

                    <td>小计列:</td>
                    <td>
                        <input type="checkbox" class="costsgroupcols checkbox1" value="deptid" id="deptid"/>
                        <label for="deptid" class="divtext">部门</label>
                        <input type="checkbox" class="costsgroupcols checkbox1" value="subjectid" id="subjectid"/>
                        <label for="subjectid" class="divtext">科目</label>
                        <input type="checkbox" class="costsgroupcols checkbox1" value="year" id="year"/>
                        <label for="year" class="divtext">年份</label>
                        <input id="departmentCosts-query-groupcols" type="hidden" name="groupcols"/>
                    </td>
    				<td colspan="2">    					
    					<a href="javaScript:void(0);" id="departmentCosts-query-buton" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="departmentCosts-reload-buton" class="button-qr">重置</a>

    				</td>	    						
    			</tr>
    		</table>
    	</form>
    </div>
    <table id="departmentCosts-table-deptSupplierCostsMonthReport"></table>
    <script type="text/javascript">
		var footerobject=null;
    	$(function(){
    		var columnJson = $("#departmentCosts-table-deptSupplierCostsMonthReport").createGridColumnLoad({
				frozenCol : [[
								//{field:'idok',checkbox:true,isShow:true}
			    			]],
				commonCol : [[
							  {field:'supplierid',title:'供应商',sortable:true,isShow:true,
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
			$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid({ 
		 		authority:columnJson,
		 		frozenColumns: columnJson.frozen,
				columns:columnJson.common,
		 		fit:true,
		 		title:"",
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'pageRowId',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
				toolbar:'#departmentCosts-query-deptSupplierCostsMonthReport',
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
			
			$("#departmentCosts-query-buton").click(function(){
				//把form表单的name序列化成JSON对象
				reloadColumn();
	      		var queryJSON = $("#departmentCosts-query-form-deptSupplierCostsMonthReport").serializeJSON();
	      		$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid({
	      			url: 'journalsheet/costsFee/showDeptSupplierCostsMonthReportData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			$("#departmentCosts-reload-buton").click(function(){
				$("#departmentCosts-query-form-deptSupplierCostsMonthReport")[0].reset();
				$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid('loadData',{total:0,rows:[]});
				$("#departmentCosts-query-supplierid").supplierWidget('clear');
				$("#departmentCosts-query-groupcols").val('');
			});
			$("#departmentCosts-export-buton").click(function(){
				var year=$("#departmentCosts-query-year").val()||"";
				var deptname=$("#departmentCosts-query-supplierid").supplierWidget("getText")|| "" ;
				var title="分供应商费用明细报表";
				if($.trim(year)!=""){
					year=year+"年 ";
				}
				title=year+$.trim(deptname)+title;
				$("#departmentCosts-export-excel").Excel('export',{
					queryForm: "#departmentCosts-query-form-deptSupplierCostsMonthReport", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:title,
			 		url:'journalsheet/costsFee/exportDeptSupplierCostsMonthReportData.do'
				});
				$("#departmentCosts-export-excel").trigger("click");
			});
    	});
    </script>
    <script type="text/javascript">
    function countTotalAmount(){
		var rows =  $("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid('getChecked');
		if(null==rows || rows.length==0){
       		var foot=[];
			if(null!=footerobject){
        		foot.push(emptyChooseObjectFoot());
        		foot.push(footerobject);
    		}
			$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid("reloadFooter",foot);
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
		$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid("reloadFooter",foot);
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
			//$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid('showColumn', "deptid");
			$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid('showColumn', "supplierid");
			$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid('showColumn', "year");
			$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid('showColumn', "subjectid");
			return;
    	}
		cols=cols.toLowerCase();
		//$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid('hideColumn', "deptid");
		$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid('hideColumn', "supplierid");
		$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid('hideColumn', "year");
		$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid('hideColumn', "subjectid");
		var isnotgroup=true;
		/*
		if(cols.indexOf("deptid")>=0){
			$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid('showColumn', "deptid");
			isnotgroup=false;
		}
		*/
		if(cols.indexOf("supplierid")>=0){
			$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid('showColumn', "supplierid");
			isnotgroup=false;
		}
		if(cols.indexOf("year")>=0){
			$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid('showColumn', "year");			
			isnotgroup=false;	
		}
		if(cols.indexOf("subjectid")>=0){
			$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid('showColumn', "subjectid");	
			isnotgroup=false;			
		}
		if(isnotgroup){
			//$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid('showColumn', "deptid");
			$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid('showColumn', "supplierid");
			$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid('showColumn', "year");
			$("#departmentCosts-table-deptSupplierCostsMonthReport").datagrid('showColumn', "subjectid");
		}
	}
    </script>
  </body>
</html>
