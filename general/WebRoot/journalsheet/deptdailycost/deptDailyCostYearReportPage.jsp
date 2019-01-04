<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>部门日常费用报表</title>
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
   	<div id="deptDailyCost-query-reportList" style="padding:2px;height:auto">
    	<form action="" id="deptDailyCost-query-form-reportList" method="post">
    		<table>
                <tr>
                    <security:authorize url="/journalsheet/deptdailycost/exportDeptDailyCostReportDataBtn.do">
                        <a href="javaScript:void(0);" id="deptDailyCost-export-buton" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                        <a href="javaScript:void(0);" id="deptDailyCost-export-excel" style="display:none">导出</a>
                    </security:authorize>
                </tr>
    			<tr>
    				<td>部门名称:</td>
    				<td><input id="deptDailyCost-query-deptid" type="text" name="deptid" style="width: 100px;"/></td>
                    <td>年份:</td>
                    <td><input id="deptDailyCost-query-year" type="text" name="year" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy',maxDate:'${currentyear }'})" value="${currentyear }"/> </td>

    				<td>小计列:</td>
    				<td>
    					<input type="checkbox" class="costsgroupcols checkbox1" value="deptid" id="deptid"/>
                        <label for="deptid" class="divtext">部门</label>
    					<input type="checkbox" class="costsgroupcols checkbox1" value="subjectid" id="subjectid"/>
                        <label for="subjectid" class="divtext">科目</label>
    					<input type="checkbox" class="costsgroupcols checkbox1" value="year" id="year"/>
                        <label for="year" class="divtext">年份</label>
    					<input id="deptDailyCost-query-groupcols" type="hidden" name="groupcols"/>
    				</td>
                    <td rowspan="3" colspan="2" class="tdbutton">
    					<a href="javaScript:void(0);" id="deptDailyCost-query-buton" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="deptDailyCost-reload-buton" class="button-qr">重置</a>
    				</td>	    						
    			</tr>
    		</table>
    	</form>
    </div>
    <table id="deptDailyCost-table-reportList"></table>
    <script type="text/javascript">
		var footerobject=null;
    	$(function(){
    		var columnJson = $("#deptDailyCost-table-reportList").createGridColumnLoad({
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
			$("#deptDailyCost-table-reportList").datagrid({ 
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
				toolbar:'#deptDailyCost-query-reportList',
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
			
			$("#deptDailyCost-query-supplierid").supplierWidget();
			$("#deptDailyCost-query-deptid").widget({
				width:200,
				name:'t_js_dept_dailycost',
				col:'deptid',
				singleSelect:true,
				onlyLeafCheck:false
			});
	    	$(".costsgroupcols").click(function(){
				var cols = "";
				$("#deptDailyCost-query-groupcols").val("");
				$(".costsgroupcols").each(function(){
					if($(this).attr("checked")){
						if(cols==""){
							cols = $(this).val();
						}else{
							cols += ","+$(this).val();
						}
						$("#deptDailyCost-query-groupcols").val(cols);
					}
				});
			});
			$("#deptDailyCost-query-buton").click(function(){
				//把form表单的name序列化成JSON对象
				reloadColumn();
	      		var queryJSON = $("#deptDailyCost-query-form-reportList").serializeJSON();
	      		$("#deptDailyCost-table-reportList").datagrid({
	      			url: 'journalsheet/deptdailycost/showDeptDailyCostReportData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			$("#deptDailyCost-reload-buton").click(function(){
				$("#deptDailyCost-query-form-reportList")[0].reset();
				$("#deptDailyCost-table-reportList").datagrid('loadData',{total:0,rows:[]});
				$("#deptDailyCost-query-deptid").widget('clear');
				$("#deptDailyCost-query-groupcols").val("");
			});
			$("#deptDailyCost-export-buton").click(function(){
				var year=$("#deptDailyCost-query-year").val()||"";
				var deptname=$("#deptDailyCost-query-deptid").widget("getText")|| "" ;
				var title="部门日常费用报表";
				if($.trim(year)!=""){
					year=year+"年 ";
				}
				title=year+$.trim(deptname)+title;
				$("#deptDailyCost-export-excel").Excel('export',{
					queryForm: "#deptDailyCost-query-form-reportList", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:title,
			 		url:'journalsheet/deptdailycost/exportDeptDailyCostReportData.do'
				});
				$("#deptDailyCost-export-excel").trigger("click");
			});
    	});
    </script>
    <script type="text/javascript">
    function countTotalAmount(){
		var rows =  $("#deptDailyCost-table-reportList").datagrid('getChecked');
		if(null==rows || rows.length==0){
       		var foot=[];
			if(null!=footerobject){
        		foot.push(emptyChooseObjectFoot());
        		foot.push(footerobject);
    		}
			$("#deptDailyCost-table-reportList").datagrid("reloadFooter",foot);
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
		$("#deptDailyCost-table-reportList").datagrid("reloadFooter",foot);
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
    	var cols=$("#deptDailyCost-query-groupcols").val()||"";
    	if(cols==null || cols.length==0){
			$("#deptDailyCost-table-reportList").datagrid('showColumn', "deptid");
			$("#deptDailyCost-table-reportList").datagrid('showColumn', "year");
			$("#deptDailyCost-table-reportList").datagrid('showColumn', "subjectid");
			return;
    	}
		cols=cols.toLowerCase();
		$("#deptDailyCost-table-reportList").datagrid('hideColumn', "deptid");
		$("#deptDailyCost-table-reportList").datagrid('hideColumn', "year");
		$("#deptDailyCost-table-reportList").datagrid('hideColumn', "subjectid");
		var isnotgroup=true;
		if(cols.indexOf("deptid")>=0){
			$("#deptDailyCost-table-reportList").datagrid('showColumn', "deptid");
			isnotgroup=false;
		}
		if(cols.indexOf("year")>=0){
			$("#deptDailyCost-table-reportList").datagrid('showColumn', "year");			
			isnotgroup=false;	
		}
		if(cols.indexOf("subjectid")>=0){
			$("#deptDailyCost-table-reportList").datagrid('showColumn', "subjectid");	
			isnotgroup=false;			
		}
		if(isnotgroup){
			$("#deptDailyCost-table-reportList").datagrid('showColumn', "deptid");
			$("#deptDailyCost-table-reportList").datagrid('showColumn', "year");
			$("#deptDailyCost-table-reportList").datagrid('showColumn', "subjectid");
		}
	}
    </script>
  </body>
</html>
