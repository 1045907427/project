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
    				<td>部门名称:</td>
    				<td><input id="deptIncome-query-deptid" type="text" name="deptid" style="width: 100px;"/></td>
                    <td>年份:</td>
                    <td><input id="deptIncome-query-year" type="text" name="year" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy',maxDate:'${currentyear }'})" value="${currentyear }"/> </td>

    				<td>小计列:</td>
    				<td>
    					<input type="checkbox" class="costsgroupcols checkbox1" value="deptid" id="deptid"/>
                        <label for="deptid" class="divtext">部门</label>
    					<input type="checkbox" class="costsgroupcols checkbox1" value="subjectid" id="subjectid"/>
                        <label for="subjectid" class="divtext">科目</label>
    					<input type="checkbox" class="costsgroupcols checkbox1" value="year" id="year"/>
                        <label for="year" class="divtext">年份</label>
    					<input id="deptIncome-query-groupcols" type="hidden" name="groupcols"/>
    				</td>
                    <td rowspan="3" colspan="2" class="tdbutton">
    					<a href="javaScript:void(0);" id="deptIncome-query-buton" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="deptIncome-reload-buton" class="button-qr">重置</a>
    				</td>	    						
    			</tr>
    		</table>
    	</form>
    </div>
    <table id="deptIncome-table-reportList"></table>
    <script type="text/javascript">
		var footerobject=null;
    	$(function(){
    		var columnJson = $("#deptIncome-table-reportList").createGridColumnLoad({
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
			$("#deptIncome-table-reportList").datagrid({ 
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
				toolbar:'#deptIncome-query-reportList',
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
			
			$("#deptIncome-query-supplierid").supplierWidget();
			$("#deptIncome-query-deptid").widget({
				width:200,
				name:'t_js_dept_income',
				col:'deptid',
				singleSelect:true,
				onlyLeafCheck:false
			});
	    	$(".costsgroupcols").click(function(){
				var cols = "";
				$("#deptIncome-query-groupcols").val("");
				$(".costsgroupcols").each(function(){
					if($(this).attr("checked")){
						if(cols==""){
							cols = $(this).val();
						}else{
							cols += ","+$(this).val();
						}
						$("#deptIncome-query-groupcols").val(cols);
					}
				});
			});
			$("#deptIncome-query-buton").click(function(){
				//把form表单的name序列化成JSON对象
				reloadColumn();
	      		var queryJSON = $("#deptIncome-query-form-reportList").serializeJSON();
	      		$("#deptIncome-table-reportList").datagrid({
	      			url: 'journalsheet/deptincome/showDeptIncomeReportData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			$("#deptIncome-reload-buton").click(function(){
				$("#deptIncome-query-form-reportList")[0].reset();
				$("#deptIncome-table-reportList").datagrid('loadData',{total:0,rows:[]});
				$("#deptIncome-query-deptid").widget('clear');
				$("#deptIncome-query-groupcols").val("");
			});
			$("#deptIncome-export-buton").click(function(){
				var year=$("#deptIncome-query-year").val()||"";
				var deptname=$("#deptIncome-query-deptid").widget("getText")|| "" ;
				var title="部门收入报表";
				if($.trim(year)!=""){
					year=year+"年 ";
				}
				title=year+$.trim(deptname)+title;
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
    <script type="text/javascript">
    function countTotalAmount(){
		var rows =  $("#deptIncome-table-reportList").datagrid('getChecked');
		if(null==rows || rows.length==0){
       		var foot=[];
			if(null!=footerobject){
        		foot.push(emptyChooseObjectFoot());
        		foot.push(footerobject);
    		}
			$("#deptIncome-table-reportList").datagrid("reloadFooter",foot);
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
		$("#deptIncome-table-reportList").datagrid("reloadFooter",foot);
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
    	var cols=$("#deptIncome-query-groupcols").val()||"";
    	if(cols==null || cols.length==0){
			$("#deptIncome-table-reportList").datagrid('showColumn', "deptid");
			$("#deptIncome-table-reportList").datagrid('showColumn', "year");
			$("#deptIncome-table-reportList").datagrid('showColumn', "subjectid");
			return;
    	}
		cols=cols.toLowerCase();
		$("#deptIncome-table-reportList").datagrid('hideColumn', "deptid");
		$("#deptIncome-table-reportList").datagrid('hideColumn', "year");
		$("#deptIncome-table-reportList").datagrid('hideColumn', "subjectid");
		var isnotgroup=true;
		if(cols.indexOf("deptid")>=0){
			$("#deptIncome-table-reportList").datagrid('showColumn', "deptid");
			isnotgroup=false;
		}
		if(cols.indexOf("year")>=0){
			$("#deptIncome-table-reportList").datagrid('showColumn', "year");			
			isnotgroup=false;	
		}
		if(cols.indexOf("subjectid")>=0){
			$("#deptIncome-table-reportList").datagrid('showColumn', "subjectid");	
			isnotgroup=false;			
		}
		if(isnotgroup){
			$("#deptIncome-table-reportList").datagrid('showColumn', "deptid");
			$("#deptIncome-table-reportList").datagrid('showColumn', "year");
			$("#deptIncome-table-reportList").datagrid('showColumn', "subjectid");
		}
	}
    </script>
  </body>
</html>
