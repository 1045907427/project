<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分供应商资金回笼表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-supplierWithdrawn"></table>
    	<div id="report-toolbar-supplierWithdrawn" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/supplierWithdrawnExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-supplierWithdrawnPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                </security:authorize>
                <div id="dialog-autoexport"></div>
            </div>
    		<form action="" id="report-query-form-supplierWithdrawn" method="post">
    		<table>
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>品牌部门:</td>
    				<td><input type="text" id="report-query-branddept" name="branddept"/></td>
    			</tr>
    			<tr>
    				<td>供 应 商:</td>
    				<td><input type="text" id="report-query-supplierid" name="supplierid"/></td>
                    <td></td>
    				<td>
    					<a href="javaScript:void(0);" id="report-queay-supplierWithdrawn" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-supplierWithdrawn" class="button-qr">重置</a>

    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-fundsSupplierCustomerGoods-detail-dialog"></div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-supplierWithdrawn").serializeJSON();
    		$(function(){

                $("#report-buttons-supplierWithdrawnPage").click(function(){
                    var queryJSON = $("#report-query-form-supplierWithdrawn").serializeJSON();
                    //获取排序规则
                    var objecr  = $("#report-datagrid-supplierWithdrawn").datagrid("options");
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryJSON["sort"] = objecr.sortName;
                        queryJSON["order"] = objecr.sortOrder;
                    }
                    //存入导出时的参数
                    queryJSON["groupcols"] = "supplierid";
                    var queryParam = JSON.stringify(queryJSON);
                    var url = "report/finance/exportBaseFinanceDrawnData.do";
                    exportByAnalyse(queryParam,"分供应商资金回笼表","report-datagrid-supplierWithdrawn",url);
                });

                var tableColumnListJson = $("#report-datagrid-supplierWithdrawn").createGridColumnLoad({
                    frozenCol: [[
                        {field: 'idok', checkbox: true, isShow: true}
                    ]],
                    commonCol: [[
                        {field:'supplierid',title:'供应商编码',width:80},
                        {field:'suppliername',title:'供应商名称',width:250},
                        {field:'branddept',title:'品牌部门',width:60,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.branddeptname;
                            }
                        },
                        {field:'withdrawnamount',title:'回笼金额',align:'right',resizable:true,isShow:true,
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

    			$("#report-datagrid-supplierWithdrawn").datagrid({
                    authority:tableColumnListJson,
                    frozenColumns: tableColumnListJson.frozen,
                    columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
		  	 		pageSize:100,
					toolbar:'#report-toolbar-supplierWithdrawn',
					onDblClickRow:function(rowIndex, rowData){
						var supplierid = rowData.supplierid;
						var suppliername = rowData.suppliername;
						var businessdate1 = $("#report-query-businessdate1").val();
    					var businessdate2 = $("#report-query-businessdate2").val();
    					var url = 'report/finance/showSupplierWithdrawnDetailListPage.do?supplierid='+supplierid+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2+'&suppliername='+suppliername;
						$("#report-fundsSupplierCustomerGoods-detail-dialog").dialog({
							title:'按供应商:['+rowData.suppliername+']统计',
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
						$("#report-fundsSupplierCustomerGoods-detail-dialog").dialog("open");
					}
				}).datagrid("columnMoving");
				$("#report-query-supplierid").widget({
					referwid:'RL_T_BASE_BUY_SUPPLIER',
		    		width:225,
					singleSelect:false
				});
				
				$("#report-query-branddept").widget({
					referwid:'RL_T_BASE_DEPARTMENT_BUYER',
		    		width:210,
					onlyLeafCheck:false,
					singleSelect:true
				});
				
				//回车事件
				controlQueryAndResetByKey("report-queay-supplierWithdrawn","report-reload-supplierWithdrawn");
				
				//查询
				$("#report-queay-supplierWithdrawn").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-supplierWithdrawn").serializeJSON();
		      		$("#report-datagrid-supplierWithdrawn").datagrid({
		      			url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=supplierid',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-supplierWithdrawn").click(function(){
					$("#report-query-supplierid").widget("clear");
					$("#report-query-branddept").widget('clear');
					$("#report-query-form-supplierWithdrawn")[0].reset();
		       		$("#report-datagrid-supplierWithdrawn").datagrid('loadData',{total:0,rows:[]});
				});
				
    		});
    	</script>
  </body>
</html>
