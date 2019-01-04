<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>按供应商分品牌,分商品统计页面</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
  	<form action="" id="report-query-form-branddeptWithdrawnDetail" method="post">
		<input type="hidden" name="supplierid" value="${supplierid}"/>
		<input type="hidden" name="businessdate1" value="${businessdate1}"/>
		<input type="hidden" name="businessdate2" value="${businessdate2}"/>
	</form>
  	<div id="report-tab-branddeptWithdrawnDetail" class="buttonBG">
		<security:authorize url="/report/finance/supplierWithdrawnExport.do">
			<a href="javaScript:void(0);" id="report-export-branddeptWithdrawnDetail" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
		</security:authorize>
	</div>
	<div id="tt" class="easyui-tabs" data-options="fit:true">
	    <div title="分品牌统计" style="padding:2px;">
	       <table id="report-datagrid-fundsCustomerDetail-brand"></table>
	    </div>
	</div>
	<div id="report-fundsCustomer1-detail-dialog"></div>
    	<script type="text/javascript">
    		$('#tt').tabs({
				tools:'#report-tab-branddeptWithdrawnDetail'
			});
    		var initQueryJSON = $("#report-query-form-branddeptWithdrawnDetail").serializeJSON();
    		$(function(){
                var tableColumnListJson = $("#report-datagrid-fundsCustomerDetail-brand").createGridColumnLoad({
                    frozenCol: [[
                        {field: 'idok', checkbox: true, isShow: true}
                    ]],
                    commonCol: [[
                        {field:'brandname',title:'商品品牌',width:60},
                        {field:'withdrawnamount',title:'回笼金额',align:'right',width:60,isShow:true,
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
                        {field:'writeoffmarginamount',title:'回笼毛利额',align:'right',resizable:true,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        }
                        </c:if>
                        <c:if test="${map.writeoffrate == 'writeoffrate'}">
                        ,
                        {field:'writeoffrate',title:'回笼毛利率',align:'right',width:80,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                if(null != value && "" != value){
                                    return formatterMoney(value)+"%";
                                }
                            }
                        }
                        </c:if>
                    ]]
                });

    			$("#report-datagrid-fundsCustomerDetail-brand").datagrid({
                    authority:tableColumnListJson,
                    frozenColumns: tableColumnListJson.frozen,
                    columns:tableColumnListJson.common,
			 		method:'post',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		pageSize:100,
		  	 		singleSelect:true,
					url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=brandid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");

				$("#report-export-branddeptWithdrawnDetail").Excel('export',{
					queryForm: "#report-query-form-branddeptWithdrawnDetail", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'按供应商：[${suppliername}]统计表',
			 		url:'report/finance/exportFinanceWithdrawnDetailReportData.do?groupcols=brandid;'
				});
    		});
    	</script>
  </body>
</html>
