<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售箱数统计报表</title>
    <%@include file="/include.jsp" %> 
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>    
  </head>
  
  <body>
    	<table id="report-datagrid-salesQuantity"></table>
    	<div id="report-toolbar-salesQuantity" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/sales/salesQuantityReportExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-salesQuantityPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-salesQuantity" method="post">
	    		<table class="querytable">

	    			<tr>
	    				<td>业务日期:</td>
	    				<td><input id="report-query-businessdate1" type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 
	    					到 <input id="report-query-businessdate2" type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
	    				<td>仓库:</td>
	    				<td><input type="text" id="report-query-storageid" style="width: 220px;" name="supplierid"/></td>
	    				<td>
	    					<a href="javaScript:void(0);" id="report-query-salesQuantity" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="report-reload-salesQuantity" class="button-qr">重置</a>

	    				</td>
	    			</tr>
	    		</table>
	    	</form>
    	</div>
    	<div id="report-dialog-salesQuantityDetail"></div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-salesQuantity").serializeJSON();
    		$(function(){
    			var tableColumnListJson = $("#report-datagrid-salesQuantity").createGridColumnLoad({
					frozenCol : [[
						{field:'idok',checkbox:true,isShow:true}
					]],
					commonCol : [[
						  {field:'storageid',title:'仓库编号',width:60,align:'center'},
						  {field:'storagename',title:'仓库名称',width:85,align:'center'},
						  {field:'auxunitnum',title:'销售总数量',width:80,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
				        		return formatterBigNumNoLen(value);
				        	}
						  },
						  {field:'auxunitname',title:'销售单位',width:60,align:'center'},
						  {field:'totalweight',title:'总重量(kg)',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'totalvolume',title:'总体积(m³)',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
			         ]]
				});
				
				$("#report-datagrid-salesQuantity").datagrid({ 
			 		authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		pageSize:100,
		  	 		showFooter: true,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
					toolbar:'#report-toolbar-salesQuantity',
					onDblClickRow:function(rowIndex, rowData){
						var storageid = rowData.storageid;
						var storagename = rowData.storagename;
						var businessdate1 = $("#report-query-businessdate1").val();
    					var businessdate2 = $("#report-query-businessdate2").val();
    					var url = 'report/sales/showSalesBrandQuantityReportPage.do?storageid='+storageid+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2+'&storagename='+storagename;
						$("#report-dialog-salesQuantityDetail").dialog({
						    title: '按仓库：['+rowData.storagename+']，分品牌销售数量统计表',  
				    		width:800,
				    		height:400,
				    		closed:true,
				    		modal:true,
				    		maximizable:true,
				    		cache:false,
				    		resizable:true,
				    		maximized:true,
						    href: url
						});
						$("#report-dialog-salesQuantityDetail").dialog("open");
					},
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
				
				
				//回车事件
				controlQueryAndResetByKey("report-query-salesQuantity","report-reload-salesQuantity");
				
				//查询
				$("#report-query-salesQuantity").click(function(){
		      		var queryJSON = $("#report-query-form-salesQuantity").serializeJSON();
		      		$("#report-datagrid-salesQuantity").datagrid({
		      			url: 'report/sales/showSalesQuantityReportData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		}).datagrid("columnMoving");
				});
				//重置
				$("#report-reload-salesQuantity").click(function(){
					$("#report-query-brandid").widget("clear");
					$("#report-query-form-salesQuantity")[0].reset();
					var queryJSON = $("#report-query-form-salesQuantity").serializeJSON();
		       		$("#report-datagrid-salesQuantity").datagrid('loadData',{total:0,rows:[]});
				});
				
				$("#report-buttons-salesQuantityPage").Excel('export',{
					queryForm: "#report-query-form-salesQuantity", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'销售箱数统计报表',
			 		url:'report/sales/exportSalesQuantityReportData.do'
				});

				$("#report-query-storageid").widget({
	    			referwid:'RL_T_BASE_STORAGE_INFO',
	    			singleSelect:true,
	    			width:120,
	    			view:true
	    		});
    		});
    		function countTotalAmount(){
    			var rows =  $("#report-datagrid-salesQuantity").datagrid('getChecked');
    			if(null==rows || rows.length==0){
            		var foot=[];
        			if(null!=SR_footerobject){
    	        		foot.push(SR_footerobject);
    	    		}
            		$("#report-datagrid-salesQuantity").datagrid("reloadFooter",foot);
            		return false;
        		}
    			var auxunitnum = 0;
        		var totalweight = 0;
        		var totalvolume= 0;
        		for(var i=0;i<rows.length;i++){
        			auxunitnum = Number(auxunitnum)+Number(rows[i].auxunitnum == undefined ? 0 : rows[i].auxunitnum);
        			totalweight = Number(totalweight)+Number(rows[i].totalweight == undefined ? 0 : rows[i].totalweight);  
        			totalvolume = Number(totalvolume)+Number(rows[i].totalvolume == undefined ? 0 : rows[i].totalvolume);      			
        		}
        		var foot=[{brandname:'选中合计',auxunitnum:formatterMoney(auxunitnum),totalweight:formatterMoney(totalweight),totalvolume:formatterMoney(totalvolume)
            			}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-salesQuantity").datagrid("reloadFooter",foot);
    		}
    	</script>
  </body>
</html>
