<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分供应商日常费用分摊报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/datagrid-detailview.js"></script>
  </head>
  
  <body>
   	<div id="deptDailySupplierCost-query-supplierCostReport">
    	<form action="" id="deptDailySupplierCost-query-form-supplierCostReport" method="post">
    		<table>
                <tr>
                    <security:authorize url="/journalsheet/deptdailycost/exportDeptDailySupplierCostReportDataBtn.do">
                    		<a href="javaScript:void(0);" id="deptDailySupplierCost-export-buton" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
							<a href="javaScript:void(0);" id="deptDailySupplierCost-export-excel" style="display:none">导出</a>
                    </security:authorize>
                </tr>
    			<tr>
    				<td>年份:</td>
    				<td>
    				<input id="deptDailySupplierCost-query-year" class="easyui-numberspinner" style="width:80px;"name="year" value="${currentyear}"/>
    				</td>
    				<td>供应商名称:</td>
    				<td><input id="deptDailySupplierCost-query-supplierid" type="text" name="supplierid" style="width: 200px;"/></td>
    				<td>费用科目:</td>
    				<td><input id="deptDailyCost-query-subjectid" type="text" name="subjectid" style="width: 100px;"/></td>    				
    			</tr>
    			<tr>
    				<td colspan="5"></td>
                    <td class="tdbutton">    					
    					<a href="javaScript:void(0);" id="deptDailySupplierCost-query-buton" class="button-qr" title="[Alt+Q]查询">查询</a>
						<a href="javaScript:void(0);" id="deptDailySupplierCost-reload-buton" class="button-qr" title="[Alt+R]重置">重置</a>
    				</td>	    						
    			</tr>
    		</table>
    	</form>
    </div>
    <table id="deptDailySupplierCost-table-supplierCostReport"></table>
    <div class="easyui-menu" id="deptDailyCost-contextMenu" style="display: none;">
    	<div id="deptDailyCost-contextMenu-export">导出</div>
    </div>
    <script type="text/javascript">
		var footerobject=null;
    	$(function(){
    		var columnJson = $("#deptDailySupplierCost-table-supplierCostReport").createGridColumnLoad({
				frozenCol : [[
			    			]],
				commonCol : [[
							  {field:'supplierid',title:'供应商',sortable:true,isShow:true,width:200,
								  	formatter:function(value,rowData,rowIndex){
					        			return rowData.suppliername;
							  		}
							  },
							  {field:'year',title:'年份',sortable:true,isShow:true,align:'center',width:45},
							  <c:forEach var="month" begin="1" end="12">
								  {field:'month_${month < 10 ?"0":"" }${month }',title:'${month}月',align:'right',sortable:true,width:60,
								  	formatter:function(value,rowData,rowIndex){
					        			return formatterMoney(value);
						        	}
								  },
    						  </c:forEach>
							  {field:'totalamount',title:'合计',sortable:true,align:'right',isShow:true,width:80,
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
		 		method:'post',
		 		rownumbers:true,
		 		idField:'supplierid',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
				toolbar:'#deptDailySupplierCost-query-supplierCostReport',
				view: detailview,
                detailFormatter:function(index,row){
                    return '<div style="padding:2px"><table class="table-leaf"></table></div>';
                },
                onExpandRow: function(index,row){
                	var queryJSON = $("#deptDailySupplierCost-query-form-supplierCostReport").serializeJSON();
                	var supplierid = row.supplierid;
                	var subjectid = $("#deptDailyCost-query-subjectid").widget("getValue");
                    var ddv = $(this).datagrid('getRowDetail',index).find('table.table-leaf');
                    ddv.treegrid({
                    	columns:[[
                                  {field:'name',title:'科目名称',width:200},
                                  <c:forEach var="month" begin="1" end="12">
									  {field:'month_${month < 10 ?"0":"" }${month }',title:'${month}月',align:'right',width:60,
									  	formatter:function(value,rowData,rowIndex){
						        			return formatterMoney(value);
							        	}
									  },
	    						  </c:forEach>
                                  {field:'totalamount',title:'合计',width:80,align:'right',
                                	  formatter:function(value,rowData,rowIndex){
						        			return formatterMoney(value);
							        	}  
                                  }
                              ]],
                        url:'journalsheet/deptdailycost/showSupplierSubjectReportData.do?selsupplierid='+supplierid,
                        queryParams:queryJSON,
                        idField:'id',
                        treeField:'name',
                        singleSelect:true,
                        rownumbers:true,
                        onExpand:function(){
                        	$("#deptDailySupplierCost-table-supplierCostReport").datagrid('fixDetailRowHeight',index);
                        },
                        onCollapse:function(){
                        	$("#deptDailySupplierCost-table-supplierCostReport").datagrid('fixDetailRowHeight',index);
                        },
	                    onResize:function(){
	                    	$("#deptDailySupplierCost-table-supplierCostReport").datagrid('fixDetailRowHeight',index);
	                    },
	                    onLoadSuccess:function(){
	                        setTimeout(function(){
	                        	$("#deptDailySupplierCost-table-supplierCostReport").datagrid('fixDetailRowHeight',index);
	                        },0);
	                    }
                    });
                    $("#deptDailySupplierCost-table-supplierCostReport").datagrid('fixDetailRowHeight',index);
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
				},
				onRowContextMenu: function(e,index, rowData){
    				e.preventDefault();
    				$(this).datagrid('selectRow', index);
                    $("#deptDailyCost-contextMenu").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
			});
			
			$("#deptDailySupplierCost-query-supplierid").supplierWidget();			
			$("#deptDailyCost-query-subjectid").widget({
    			referwid:'RL_T_JS_DEPARTMENTCOSTS_SUBJECT',
    			width:225,
				singleSelect:true,
				onlyLeafCheck:false,
				onSelect:function(){
				}
    		});
	    	
			$("#deptDailySupplierCost-query-buton").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#deptDailySupplierCost-query-form-supplierCostReport").serializeJSON();
	      		$("#deptDailySupplierCost-table-supplierCostReport").datagrid({
	      			url: 'journalsheet/deptdailycost/showDeptDailySupplierCostReportData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		});
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
			$("#deptDailyCost-contextMenu-export").click(function(){
				var year=$("#deptDailySupplierCost-query-year").val()||"";
				var row = $("#deptDailySupplierCost-table-supplierCostReport").datagrid('getSelected');
				var title=row.supplierid+row.suppliername+"-费用科目明细报表";
				if($.trim(year)!=""){
					year=year+"年";
				}
				title=year+title;
				$("#deptDailySupplierCost-export-excel").Excel('export',{
					queryForm: "#deptDailySupplierCost-query-form-supplierCostReport", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:title,
			 		url:'journalsheet/deptdailycost/exportSupplierSubjectDetailData.do?selsupplierid='+row.supplierid
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
    </script>
  </body>
</html>
