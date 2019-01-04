<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售区域报表</title>
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
   	<div id="salesArea-query-reportList" style="padding:2px;height:auto">
    	<form action="" id="salesArea-query-form-reportList" method="post">
    		<table>
                <tr>
                    <security:authorize url="/journalsheet/deptdailycost/exportDeptDailyCostReportDataBtn.do">
                        <a href="javaScript:void(0);" id="salesArea-export-buton" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                        <a href="javaScript:void(0);" id="salesArea-export-excel" style="display:none">导出</a>
                    </security:authorize>
                </tr>
    			<tr>
    				<td>业务日期:</td>
    				<td><input id="salesArea-query-businessdate1" type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${firstDay }"/> 到 <input id="salesArea-query-businessdate2" type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"/></td>
    				<td>销售区域:</td>
    				<td><input type="text" name="salesarea" id="salesArea-query-salesarea"/></td>
                    <td class="tdbutton">
    					<a href="javaScript:void(0);" id="salesArea-query-buton" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="salesArea-reload-buton" class="button-qr">重置</a>
    				</td>	    						
    			</tr>
    		</table>
    	</form>
    </div>
    <table id="salesArea-table-reportList"></table>
    <div class="easyui-menu" id="salesArea-contextMenu" style="display: none;">
    	<div id="salesArea-contextMenu-export">导出</div>
    	<div id="salesArea-contextMenu-expand">展开</div>
    	<div id="salesArea-contextMenu-collapse">折叠</div>
    </div>
    <div style="display:none">
    	<div id="salesArea-reportDetailList-div"></div>
    </div>
    <script type="text/javascript">
		var footerobject=null;
    	$(function(){
    		var queryInitJSON = $("#salesArea-query-form-reportList").serializeJSON();
			$("#salesArea-table-reportList").treegrid({ 
				columns:[[
				    {field:'salesarea',title:'销售编号',width:80,sortable:true,hidden:true},
				    {field:'salesareaname',title:'销售区域',sortable:true,width:120},
				    {field:'orderamount',title:'订单金额',resizable:true,sortable:true,align:'right',
					    formatter:function(value,rowData,rowIndex){
			                return formatterMoney(value);
			            }
				    },
				    {field:'initsendamount',title:'发货单金额',resizable:true,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					},
					{field:'sendamount',title:'发货出库金额',resizable:true,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					},
					{field:'pushbalanceamount',title:'冲差金额',resizable:true,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					},
					{field:'directreturnamount',title:'直退金额',resizable:true,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					},
					{field:'checkreturnamount',title:'退货金额',resizable:true,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					},
					{field:'returnamount',title:'退货合计',resizable:true,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					},
					{field:'salenum',title:'销售数量',width:60,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterBigNumNoLen(value);
			        	}
					},
					{field:'saletotalbox',title:'销售箱数',width:60,align:'right',isShow:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					},
					{field:'saleamount',title:'销售金额',resizable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					},
				  	{field:'costamount',title:'成本金额',align:'right',resizable:true,isShow:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					},
					{field:'salemarginamount',title:'销售毛利额',resizable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					},
					{field:'realrate',title:'实际毛利率',width:70,align:'right',isShow:true,
					  	formatter:function(value,rowData,rowIndex){
			        		if(value!=null && value!=0){
			        			return formatterMoney(value)+"%";
			        		}else{
			        			return "";
			        		}
			        	}
					}
			    ]],
		 		fit:true,
		 		title:"",
		 		method:'post',
		 		rownumbers:true,
		 		idField:'rid',
		 		treeField:'salesareaname',
		 		singleSelect:true,
		 		showFooter: true,
		 		url: 'journalsheet/salesarea/showDSalesAreaReportData.do',
		 		queryParams:queryInitJSON,
				toolbar:'#salesArea-query-reportList',
				onDblClickRow:function(row){
					$("#salesArea-table-reportList").treegrid("expand",row.rid);
				},
				onContextMenu: function(e, rowData){
    				e.preventDefault();
    				$(this).treegrid('select', rowData.rid);
                    $("#salesArea-contextMenu").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
			});
			//销售区域
			$("#salesArea-query-salesarea").widget({
	  			referwid:'RT_T_BASE_SALES_AREA',
	   			singleSelect:false,
	   			width:200,
	   			onlyLeafCheck:false
	  		});
			
			$("#salesArea-query-buton").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#salesArea-query-form-reportList").serializeJSON();
	      		$("#salesArea-table-reportList").treegrid('load', queryJSON);
			});
			$("#salesArea-reload-buton").click(function(){
				$("#salesArea-query-form-reportList")[0].reset();
				$("#salesArea-table-reportList").treegrid('loadData',[]);
				$("#salesArea-query-salesarea").widget('clear');
			});
			$("#salesArea-contextMenu-export").click(function(){
				var row = $("#salesArea-table-reportList").treegrid('getSelected');
					var salesarea = row.id;
					var date1=$("#salesArea-query-businessdate1").val()||"";
					var date2=$("#salesArea-query-businessdate2").val()||"";
					var salesareaname = row.salesareaname ;
					var title="";
					title=date1+(date1!=""&&date2!=""?"至":"")+date2;
					title=title+$.trim(salesareaname)+"销售区域报表";
					$("#salesArea-export-excel").Excel('export',{
						queryForm: "#salesArea-query-form-reportList", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
				 		type:'exportUserdefined',
				 		name:title,
				 		url:'journalsheet/salesarea/exportSalesAreaReportData.do?id='+salesarea
					});
					$("#salesArea-export-excel").trigger("click");
			});
			$("#salesArea-contextMenu-expand").click(function(){
				var row = $("#salesArea-table-reportList").treegrid('getSelected');
				$("#salesArea-table-reportList").treegrid("expandAll",row.rid);
			});
			$("#salesArea-contextMenu-collapse").click(function(){
				var row = $("#salesArea-table-reportList").treegrid('getSelected');
				$("#salesArea-table-reportList").treegrid("collapseAll",row.rid);
			});
			$("#salesArea-export-buton").click(function(){
				var date1=$("#salesArea-query-businessdate1").val()||"";
				var date2=$("#salesArea-query-businessdate2").val()||"";
				var salesareaname=$("#salesArea-query-deptid").widget("getText")|| "" ;
				var title="";
				title=date1+(date1!=""&&date2!=""?"至":"")+date2;
				title=title+$.trim(salesareaname)+"销售区域报表";
				$("#salesArea-export-excel").Excel('export',{
					queryForm: "#salesArea-query-form-reportList", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:title,
			 		url:'journalsheet/salesarea/exportSalesAreaReportData.do'
				});
				$("#salesArea-export-excel").trigger("click");
			});
    	});
    </script>
  </body>
</html>
