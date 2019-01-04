<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分公司分品牌部门资金回笼情况表</title>
  </head>
  
  <body>
    <table id="report-datagrid-companyWithdrawndetail"></table>
    <div id="report-toolbar-companyWithdrawndetail" class="buttonBG">
    	<form action="" id="report-query-form-companyWithdrawndetail" method="post" class="buttonBG">
    		<input type="hidden" name="branddept" value="${branddept}"/>
			<input id="report-query-businessdate1" type="hidden" name="businessdate1" value="${businessdate1}"/>
			<input id="report-query-businessdate2" type="hidden" name="businessdate2" value="${businessdate2}"/>
    		<security:authorize url="/report/finance/companyWithdrawnListExport.do">
				<a href="javaScript:void(0);" id="report-export-companyWithdrawndetail" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
			</security:authorize>
    	</form>
    </div>
    <div id="report-dialog-companyWithdrawnBrandDetail"></div>
    <script type="text/javascript">
    	var initQueryJSON = $("#report-query-form-companyWithdrawndetail").serializeJSON();
   		$(function(){

            $("#report-export-companyWithdrawndetail").click(function(){

                var queryJSON = $("#report-query-form-companyWithdrawndetail").serializeJSON();
                //获取排序规则
                var objecr  = $("#report-datagrid-companyWithdrawndetail").datagrid("options");
                if(null != objecr.sortName && null != objecr.sortOrder ){
                    queryJSON["sort"] = objecr.sortName;
                    queryJSON["order"] = objecr.sortOrder;
                }
                var queryParam = JSON.stringify(queryJSON);
                var url = "report/finance/exportBaseFinanceDrawnData.do?groupcols=branddept";
                exportByAnalyse(queryParam,"按公司：[${branddeptname}]分品牌部门统计","report-datagrid-companyWithdrawndetail",url);
            });

   			var tableColumnDeptListJson = $("#report-datagrid-companyWithdrawndetail").createGridColumnLoad({
					frozenCol : [[
				    			]],
					commonCol : [[
						  {field:'branddept',title:'品牌部门',width:80,isShow:true,sortable:true,
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
    			$("#report-datagrid-companyWithdrawndetail").datagrid({ 
			 		authority:tableColumnDeptListJson,
			 		frozenColumns: tableColumnDeptListJson.frozen,
					columns:tableColumnDeptListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		showFooter: true,
		  	 		queryParams:initQueryJSON,
		  	 		singleSelect:true,
		  	 		url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=branddept',
					toolbar:'#report-toolbar-companyWithdrawndetail',
					onDblClickRow:function(rowIndex, rowData){
						var businessdate1 = $("#report-query-businessdate1").val();
    					var businessdate2 = $("#report-query-businessdate2").val();
    					var url = 'report/finance/showCompanyWithdrawnBrandDetailPage.do?branddept='+rowData.branddept+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2+'&branddeptname='+rowData.branddeptname+'&company=${branddeptname}';
						$('<div id="report-dialog-companyWithdrawnBrandDetail1"></div>').appendTo('#report-dialog-companyWithdrawnBrandDetail');
						$("#report-dialog-companyWithdrawnBrandDetail1").dialog({
						    title: '按公司：[${branddeptname}],品牌部门：['+rowData.branddeptname+']分品牌统计',  
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
						    	$('#report-dialog-companyWithdrawnBrandDetail1').dialog("destroy");
						    }
						});
						$("#report-dialog-companyWithdrawnBrandDetail1").dialog("open");
					}
				}).datagrid("columnMoving");

   		});
    </script>
  </body>
</html>
