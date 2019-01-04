<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分司机销售退货情况统计报表</title>
	<%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
  </head>
  
  <body>
    <table id="report-datagrid-rejectEnter"></table>
    <div id="report-toolbar-rejectEnter" style="padding: 0px">
        <div class="buttonBG">
            <security:authorize url="/report/sales/SalesRejectEnterReportExport.do">
                <a href="javaScript:void(0);" id="report-buttons-rejectEnterPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
            </security:authorize>
        </div>
    	<form action="" id="report-query-form-rejectEnter" method="post">
    		<table>
    			<tr>
    				<td>业务日期:</td>
    				<td><input id="report-query-businessdate1" type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 
    					到 <input id="report-query-businessdate2" type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" />
    				</td>
    				<td>品牌:</td>
    				<td><input id="report-query-brandid" type="text" name="brandid" style="width: 130px"/>
    					<input name="brandids" type="hidden" value="${brandids }" style="width: 130px"/>
    				</td>
    				<td>司机:</td>
    				<td><input id="report-query-driverid" name="driverid" style="width: 130px"/></td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="report-queay-rejectEnter" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-rejectEnter" class="button-qr">重置</a>
    				</td>
    			</tr>
    		</table>
    	</form>
    </div>
    <script type="text/javascript">
    	var SR_footerobject  = null;
    	$(function(){
   			var rejectEntertableColumnListJson = $("#report-datagrid-rejectEnter").createGridColumnLoad({
				frozenCol : [[
					{field:'idok',checkbox:true,isShow:true}
				]],
				commonCol : [[
					  {field:'driverid',title:'司机',width:60,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.drivername;
			        	}
					  },
					  {field:'directreturnnum',title:'直退数量',width:60,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterBigNumNoLen(value);
			        	}
					  },
					  {field:'auxdirectnumdetail',title:'直退辅数量',width:70,sortable:true,align:'right'},
					  {field:'directreturnamount',title:'直退金额',resizable:true,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'checkreturnnum',title:'退货数量',width:80,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterBigNumNoLen(value);
			        	}
					  },
					  {field:'auxchecknumdetail',title:'退货辅数量',width:70,sortable:true,align:'right'},
					  {field:'checkreturnamount',title:'退货金额',resizable:true,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'checkreturnrate',title:'退货率',width:60,sortable:true,align:'right',isShow:true,
					  	formatter:function(value,rowData,rowIndex){
					  		if(value!=null && value!=0){
			        			return formatterMoney(value)+"%";
			        		}else{
			        			return "";
			        		}
			        	}
					  },
					  {field:'returnnum',title:'退货总数量',width:70,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterBigNumNoLen(value);
			        	}
					  },
					  {field:'auxreturnnumdetail',title:'退货总辅数量',width:70,sortable:true,align:'right'},
					  {field:'returnamount',title:'退货合计',resizable:true,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  }
		         ]]
			});
			
			$("#report-datagrid-rejectEnter").datagrid({ 
		 		authority:rejectEntertableColumnListJson,
		 		frozenColumns: rejectEntertableColumnListJson.frozen,
				columns:rejectEntertableColumnListJson.common,
		 		method:'post',
	  	 		title:'',
	  	 		fit:true,
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		showFooter: true,
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
	  	 		pageSize:100,
				toolbar:'#report-toolbar-rejectEnter',
	  	 		onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						SR_footerobject = footerrows[0];
						countTotalAmount();
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
			
			//品牌
   			$("#report-query-brandid").widget({
    			referwid:'RL_T_BASE_GOODS_BRAND',
    			singleSelect:false,
    			param:[{field:'id',op:'in',value:'${brandids}'}],
    			width:'150',
    			onlyLeafCheck:true
    		});
    		$("#report-query-driverid").widget({
    			referwid:'RL_T_BASE_PERSONNEL_LOGISTICS',
    			col:'brandid',
    			singleSelect:false,
    			width:'150',
    			onlyLeafCheck:true
    		});
			//回车事件
			controlQueryAndResetByKey("report-queay-rejectEnter","report-reload-rejectEnter");
			
			//查询
			$("#report-queay-rejectEnter").click(function(){
	      		var queryJSON = $("#report-query-form-rejectEnter").serializeJSON();
	      		$("#report-datagrid-rejectEnter").datagrid({
	      			url: 'report/sales/getSalesRejectEnterReportData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		});
			});
			//重置
			$("#report-reload-rejectEnter").click(function(){
				$("#report-query-brandid").widget('clear');
				$("#report-query-driverid").widget('clear');
				$("#report-query-form-rejectEnter")[0].reset();
				var queryJSON = $("#report-query-form-rejectEnter").serializeJSON();
	       		$("#report-datagrid-rejectEnter").datagrid('loadData',{total:0,rows:[]});
			});
			
			$("#report-buttons-rejectEnterPage").Excel('export',{
				queryForm: "#report-query-form-rejectEnter", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
		 		type:'exportUserdefined',
		 		name:'分司机销售退货情况统计报表',
		 		url:'report/sales/exportSalesRejectEnterReportData.do'
			});
    		function countTotalAmount(){
    			var rows =  $("#report-datagrid-rejectEnter").datagrid('getChecked');
        		var directreturnnum=0;
        		var directreturnamount =0;
        		var checkreturnnum=0;
        		var checkreturnamount=0;
        		var returnnum =0;
        		var returnamount = 0;
        		for(var i=0;i<rows.length;i++){
        			directreturnnum = Number(directreturnnum)+Number(rows[i].directreturnnum == undefined ? 0 : rows[i].directreturnnum);
        			directreturnamount = Number(directreturnamount)+Number(rows[i].directreturnamount == undefined ? 0 : rows[i].directreturnamount);
        			checkreturnnum = Number(checkreturnnum)+Number(rows[i].checkreturnnum == undefined ? 0 : rows[i].checkreturnnum);
        			checkreturnamount = Number(checkreturnamount)+Number(rows[i].checkreturnamount == undefined ? 0 : rows[i].checkreturnamount);
        			returnnum = Number(returnnum)+Number(rows[i].returnnum == undefined ? 0 : rows[i].returnnum);
        			returnamount = Number(returnamount)+Number(rows[i].returnamount == undefined ? 0 : rows[i].returnamount);
        		}
        		var foot=[{drivername:'选中合计',directreturnnum:directreturnnum,directreturnamount:directreturnamount,checkreturnnum:checkreturnnum,
            				checkreturnamount:checkreturnamount,returnnum:returnnum,returnamount:returnamount
            			}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-rejectEnter").datagrid("reloadFooter",foot);
    		}
   		});
    </script>
  </body>
</html>
