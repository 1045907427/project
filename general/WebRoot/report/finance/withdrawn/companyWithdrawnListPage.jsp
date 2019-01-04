<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分公司资金回笼报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
  </head>
  
  <body>
    <table id="report-datagrid-companyWithdrawn"></table>
    <div id="report-toolbar-companyWithdrawn" style="padding: 0px">
        <div class="buttonBG">
            <security:authorize url="/report/finance/companyWithdrawnListExport.do">
                <a href="javaScript:void(0);" id="report-export-companyWithdrawn" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
            </security:authorize>
            <div id="dialog-autoexport"></div>
        </div>
    	<form action="" id="report-query-form-companyWithdrawn" method="post">
    		<table class="querytable">

    			<tr>
    				<td>业务日期:</td>
    				<td><input id="report-query-businessdate1" type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input id="report-query-businessdate2" type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<!-- <td>公司:</td>
    				<td><input type="text" id="report-query-company" name="branddept"/></td> -->
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="report-queay-companyWithdrawn" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-companyWithdrawn" class="button-qr">重置</a>

    				</td>
    			</tr>
    		</table>
    	</form>
    </div>
    <div id="report-dialog-companyWithdrawnDetail"></div>
    <script type="text/javascript">
    	var SR_footerobject  = null;
    	$(function(){
            $("#report-export-companyWithdrawn").click(function(){

                var queryJSON = $("#report-query-form-companyWithdrawn").serializeJSON();
                //获取排序规则
                var objecr  = $("#report-datagrid-companyWithdrawn").datagrid("options");
                if(null != objecr.sortName && null != objecr.sortOrder ){
                    queryJSON["sort"] = objecr.sortName;
                    queryJSON["order"] = objecr.sortOrder;
                }
                var queryParam = JSON.stringify(queryJSON);
                var url = "report/finance/exportCompanyWithdrawnData.do";
                exportByAnalyse(queryParam,"分公司资金回笼情况报表","report-datagrid-companyWithdrawn",url);
            });

            var tableColumnCompanyWithdrawnJson = $("#report-datagrid-companyWithdrawn").createGridColumnLoad({
					frozenCol : [[{field:'idok',checkbox:true,isShow:true}]],
					commonCol : [[
						  {field:'branddept',title:'公司',width:80,isShow:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
						  		return rowData.branddeptname;
				        	}
						  },
						  {field:'withdrawnamount',title:'回笼金额',align:'right',resizable:true,isShow:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
						  <c:if test="${map.costwriteoffamount == 'costwriteoffamount'}">
							  ,
							  {field:'costwriteoffamount',title:'回笼成本',align:'right',resizable:true,isShow:true,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  }
						  </c:if>
						  <c:if test="${map.writeoffmarginamount == 'writeoffmarginamount'}">
							  ,
							  {field:'writeoffmarginamount',title:'回笼毛利额',align:'right',resizable:true,isShow:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  }
						  </c:if>
						  <c:if test="${map.writeoffrate == 'writeoffrate'}">
							  ,
							  {field:'writeoffrate',title:'回笼毛利率',align:'right',width:80,isShow:true,
							  	formatter:function(value,rowData,rowIndex){
							  		if(null != value && "" != value){
							  			return formatterMoney(value)+"%";
							  		}
					        	}
							  }
						  </c:if>
					  ]]
				});
				
				$("#report-datagrid-companyWithdrawn").datagrid({ 
    				authority:tableColumnCompanyWithdrawnJson,
			 		frozenColumns: tableColumnCompanyWithdrawnJson.frozen,
					columns:tableColumnCompanyWithdrawnJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		showFooter: true,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
					toolbar:'#report-toolbar-companyWithdrawn',
					onDblClickRow:function(rowIndex, rowData){
						var businessdate1 = $("#report-query-businessdate1").val();
    					var businessdate2 = $("#report-query-businessdate2").val();
    					var url = 'report/finance/showCompanyWithdrawnDetailListPage.do?branddept='+rowData.branddept+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2+'&branddeptname='+rowData.branddeptname;
						$('<div id="report-dialog-companyWithdrawnDetail1"></div>').appendTo('#report-dialog-companyWithdrawnDetail');
						$("#report-dialog-companyWithdrawnDetail1").dialog({
						    title: '按公司：['+rowData.branddeptname+']分品牌部门统计',  
				    		width:800,
				    		height:400,
				    		closed:true,
				    		modal:true,
				    		maximizable:true,
				    		cache:false,
				    		resizable:true,
				    		maximized:true,
						    href: url,
						    onClose:function(){
						    	$('#report-dialog-companyWithdrawnDetail1').dialog("destroy");
						    }
						});
						$("#report-dialog-companyWithdrawnDetail1").dialog("open");
					}
				}).datagrid("columnMoving");
				
				//查询
				$("#report-queay-companyWithdrawn").click(function(){
		      		var queryJSON = $("#report-query-form-companyWithdrawn").serializeJSON();
		      		$("#report-datagrid-companyWithdrawn").datagrid({
		      			url: 'report/finance/getCompanyWithdrawnList.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-companyWithdrawn").click(function(){
					$("#report-query-form-companyWithdrawn")[0].reset();
		       		$("#report-datagrid-companyWithdrawn").datagrid('loadData',{total:0,rows:[]});
				});

    	});
    </script>
  </body>
</html>
