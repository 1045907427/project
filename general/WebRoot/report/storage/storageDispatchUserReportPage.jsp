<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>资金统计报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
   	<div id="report-button-DispatchUserReport" style="padding: 0px">
        <div class="buttonBG">
            <security:authorize url="/report/storage/fundStatisticsExport.do">
                <a href="javaScript:void(0);" id="report-buttons-export" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
            </security:authorize>
        </div>
   		<form action="" id="report-form-DispatchUserReport" method="post">
   			<table class="querytable">
   				<tr>
   					<td>业务日期：</td>
    				<td><input type="text" name="businessdate1" value="${firstDay }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
   					<td style="padding-left: 10px;">发货人:&nbsp;</td>
   					<td><input id="report-widget-storager" name="storager" type="text"/></td>
   					<td style="padding-left: 10px;" colspan="2">
   						<a href="javaScript:void(0);" id="report-query-DispatchUserReport" class="button-qr">查询</a>
			    		<a href="javaScript:void(0);" id="report-reload-DispatchUserReport" class="button-qr">重置</a>
   					</td>
   				</tr>
   			</table>
   		</form>
   	</div>
    <table id="report-table-DispatchUserReport"></table>
    <script type="text/javascript">
   	    var SR_footerobject  = null;
    	var DispatchUserListColJson=$("#report-table-DispatchUserReport").createGridColumnLoad({
    		frozenCol : [[
							{field:'idok',checkbox:true,isShow:true}
		    			]],
	     	commonCol:[[
	     		{field:'storagername',title:'发货人',width:80,sortable:true},
				{field:'storagename',title:'仓库名称',width:150},
				{field:'papernum',title:'纸张数',width:100,sortable:true
				},
				{field:'printnum',title:'条数',resizable:true,sortable:true,align:'right'},
				{field:'unitnum',title:'数量',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						return formatterBigNumNoLen(val);
					}
				},
				{field:'totalbox',title:'箱数',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
                        return formatterDefineMoney(val,3);
					}
				},
				{field:'totalamount',title:'金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						return formatterMoney(val);
					}
				},
				{field:'totalweight',title:'重量',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						return formatterDefineMoney(val);
					}
				},
				{field:'totalvolume',title:'体积',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						return formatterDefineMoney(val);
					}
				},
				
			]]
	     });
	     
		$(function(){
		  	$("#report-widget-storager").widget({
				referwid:'RL_T_BASE_PERSONNEL_STORAGER',
				width:160,
				col:'name',
				singleSelect:true,
			});

			//查询
			$("#report-query-DispatchUserReport").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#report-form-DispatchUserReport").serializeJSON();
	      		
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#report-table-DispatchUserReport").datagrid({
	      			url:'report/storage/showDispatchUserListData.do',
	      			pageNumber:1,
	      			queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			
			$("#report-reload-DispatchUserReport").click(function(){
				$("#report-widget-storager").widget("clear");
				$("#report-form-DispatchUserReport")[0].reset();
	       		$("#report-table-DispatchUserReport").datagrid('loadData',{total:0,rows:[]});
			});

			$("#report-buttons-export").click(function(){
				//封装查询条件
				var objecr  = $("#report-table-DispatchUserReport").datagrid("options");
				var queryParam = objecr.queryParams;
				if(null != objecr.sortName && null != objecr.sortOrder ){
					queryParam["sort"] = objecr.sortName;
					queryParam["order"] = objecr.sortOrder;
				}
				var queryParam = JSON.stringify(queryParam);
				var url = "report/storage/exportDispatchUserReportData.do";
				exportByAnalyse(queryParam,"发货人报表","report-table-DispatchUserReport",url);
			});

			$("#report-table-DispatchUserReport").datagrid({
				authority:DispatchUserListColJson,
	  	 		frozenColumns:DispatchUserListColJson.frozen,
				columns:DispatchUserListColJson.common,
				method:'post',
	  	 		title:'',
	  	 		fit:true,
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		showFooter: true,
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
	  	 		sortName:'storagerid',
	  	 		sortOrder:'asc',
	  	 		pageSize:100,
				toolbar:'#report-button-DispatchUserReport',
				onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						SR_footerobject = footerrows[0];
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
			
		});
		function countTotalAmount(){
			var rows =  $("#report-table-DispatchUserReport").datagrid('getChecked');
    		if(null==rows || rows.length==0){
        		var foot=[];
    			if(null!=SR_footerobject){
	        		foot.push(SR_footerobject);
	    		}
    			$("#report-table-DispatchUserReport").datagrid("reloadFooter",foot);
        		return false;
    		}
			var papernum = 0;
			var printnum = 0;
    		var unitnum = 0;
    		var totalbox = 0;
    		var totalamount = 0;
    		var totalweight = 0;
    		var totalvolume=0;
    		for(var i=0;i<rows.length;i++){
    			papernum = Number(papernum)+Number(rows[i].papernum == undefined ? 0 : rows[i].papernum);
    			printnum = Number(printnum)+Number(rows[i].printnum == undefined ? 0 : rows[i].printnum);
    			unitnum = Number(unitnum)+Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
    			totalbox = Number(totalbox)+Number(rows[i].totalbox == undefined ? 0 : rows[i].totalbox);
    			totalamount = Number(totalamount)+Number(rows[i].totalamount == undefined ? 0 : rows[i].totalamount);
    			totalweight = Number(totalweight)+Number(rows[i].totalweight == undefined ? 0 : rows[i].totalweight);
    			totalvolume = Number(totalvolume)+Number(rows[i].totalvolume == undefined ? 0 : rows[i].totalvolume);
    		}
    		var foot=[{storagername:'选中金额',papernum:papernum,printnum:printnum,unitnum:unitnum,
    					totalbox:totalbox,totalamount:totalamount,totalweight:totalweight,
    					totalvolume:totalvolume,
        			}];
    		if(null!=SR_footerobject){
        		foot.push(SR_footerobject);
    		}
    		$("#report-table-DispatchUserReport").datagrid("reloadFooter",foot);
		}
    </script>
  </body>
</html>
