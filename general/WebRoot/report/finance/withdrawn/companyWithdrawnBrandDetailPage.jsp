<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>按品牌资金回笼情况表</title>
  </head>
  
  <body>
    <table id="report-datagrid-companyWithdrawnBrand"></table>
    <div id="report-toolbar-companyWithdrawnBrand" class="buttonBG">
   		<form action="" id="report-query-form-companyWithdrawnBrand" method="post">
    		<input type="hidden" name="branddept" value="${branddept}"/>
			<input type="hidden" name="companyBranddetail" value="companyBranddetail"/>
			<input id="report-query-businessdate1" type="hidden" name="businessdate1" value="${businessdate1}"/>
			<input id="report-query-businessdate2" type="hidden" name="businessdate2" value="${businessdate2}"/>
    		<security:authorize url="/report/finance/companyWithdrawnListExport.do">
				<a href="javaScript:void(0);" id="report-buttons-companyWithdrawnBrand" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
			</security:authorize>
    	</form>
   	</div>
   	<script type="text/javascript">
   		var initQueryJSON = $("#report-query-form-companyWithdrawnBrand").serializeJSON();
   		$(function(){

            $("#report-buttons-companyWithdrawnBrand").click(function(){

                var queryJSON = $("#report-query-form-companyWithdrawnBrand").serializeJSON();
                //获取排序规则
                var objecr  = $("#report-datagrid-companyWithdrawnBrand").datagrid("options");
                if(null != objecr.sortName && null != objecr.sortOrder ){
                    queryJSON["sort"] = objecr.sortName;
                    queryJSON["order"] = objecr.sortOrder;
                }
                var queryParam = JSON.stringify(queryJSON);
                var url = "report/finance/exportBaseFinanceDrawnData.do?groupcols=brandid";
                exportByAnalyse(queryParam,"按公司：[${company}]，品牌部门：[${branddeptname}]分品牌资金回笼报表","report-datagrid-companyWithdrawnBrand",url);
            });

   			var tableColumnListBrandJson = $("#report-datagrid-companyWithdrawnBrand").createGridColumnLoad({
					frozenCol : [[
									{field:'idok',checkbox:true,isShow:true}
				    			]],
					commonCol : [[
						  {field:'brandid',title:'商品品牌',width:60,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.brandname;
				        	}
						  },
						  {field:'branddept',title:'品牌部门',width:80,sortable:true,
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
    			$("#report-datagrid-companyWithdrawnBrand").datagrid({ 
			 		authority:tableColumnListBrandJson,
			 		frozenColumns: tableColumnListBrandJson.frozen,
					columns:tableColumnListBrandJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		queryParams:initQueryJSON,
		  	 		singleSelect:false,
			 		url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=brandid',
		  	 		pageSize:100,
					toolbar:'#report-toolbar-companyWithdrawnBrand'
				}).datagrid("columnMoving");

   		});
   	</script>
  </body>
</html>
