<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分客户业务员资金回笼表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-salesuerWithdrawn"></table>
    	<div id="report-toolbar-salesuerWithdrawn" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/salesuerWithdrawnExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-salesuerWithdrawnPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                </security:authorize>
				<security:authorize url="/report/finance/salesuerWithdrawnPrint.do">
					<a href="javaScript:void(0);" id="report-print-salesuerWithdrawnPage" class="easyui-linkbutton" iconCls="button-print" plain="true" title="打印">打印</a>
				</security:authorize>
            </div>
    		<form action="" id="report-query-form-salesuerWithdrawn" method="post">
    		<table class="querytable">

    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>客户业务员:</td>
    				<td><input type="text" id="report-query-saleuserid" name="salesuser"/></td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="report-queay-salesuerWithdrawn" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-salesuerWithdrawn" class="button-qr">重置</a>
    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-fundsCustomer-detail-dialog"></div>
    	<div id="report-dialog-fundsSalesuserReturnDetail"></div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-salesuerWithdrawn").serializeJSON();
    		$(function(){
                $("#report-print-salesuerWithdrawnPage").click(function () {
                    var msg = "";
                    printByAnalyse("分客户业务员资金回笼表", "report-datagrid-salesuerWithdrawn", "report/finance/printBaseFinanceDrawnData.do?groupcols=salesuser", msg);
                });
                $("#report-buttons-salesuerWithdrawnPage").click(function(){
                    var queryJSON = $("#report-query-form-salesuerWithdrawn").serializeJSON();
                    //获取排序规则
                    var objecr  = $("#report-datagrid-salesuerWithdrawn").datagrid("options");
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryJSON["sort"] = objecr.sortName;
                        queryJSON["order"] = objecr.sortOrder;
                    }
                    //存入导出时的参数
                    queryJSON["groupcols"] = "salesuser";
                    var queryParam = JSON.stringify(queryJSON);
                    var url = "report/finance/exportBaseFinanceDrawnData.do";
                    exportByAnalyse(queryParam,"分客户业务员资金回笼表","report-datagrid-salesuerWithdrawn",url);
                });

                var tableColumnListJson = $("#report-datagrid-salesuerWithdrawn").createGridColumnLoad({
                    frozenCol: [[
                        {field: 'idok', checkbox: true, isShow: true}
                    ]],
                    commonCol: [[
                        {field:'salesuser',title:'客户业务员',width:70,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.salesusername;
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
    			$("#report-datagrid-salesuerWithdrawn").datagrid({
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
					toolbar:'#report-toolbar-salesuerWithdrawn',
					onDblClickRow:function(rowIndex, rowData){
						var salesuser = rowData.salesuser;
						var salesusername = rowData.salesusername;
						var businessdate1 = $("#report-query-businessdate1").val();
    					var businessdate2 = $("#report-query-businessdate2").val();
    					var url = 'report/finance/showSalesuserWithdrawnDetailListPage.do?salesuser='+salesuser+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2+'&salesusername='+salesusername;
						$("#report-dialog-fundsSalesuserReturnDetail").dialog({
							title:'按客户业务员:['+rowData.salesusername+']统计',
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
						$("#report-dialog-fundsSalesuserReturnDetail").dialog("open");
					}
				}).datagrid("columnMoving");
				$("#report-query-saleuserid").widget({
					referwid:'RL_T_BASE_PERSONNEL_SELLER',
		    		width:130,
					singleSelect:true
				});
				
				//回车事件
				controlQueryAndResetByKey("report-queay-salesuerWithdrawn","report-reload-salesuerWithdrawn");
				
				//查询
				$("#report-queay-salesuerWithdrawn").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-salesuerWithdrawn").serializeJSON();
		      		$("#report-datagrid-salesuerWithdrawn").datagrid({
		      			url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=salesuser',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-salesuerWithdrawn").click(function(){
					$("#report-query-saleuserid").widget("clear");
					$("#report-query-form-salesuerWithdrawn").form("reset");
					var queryJSON = $("#report-query-form-salesuerWithdrawn").serializeJSON();
		       		$("#report-datagrid-salesuerWithdrawn").datagrid('loadData',{total:0,rows:[]});
				});

				
    		});
    		function showDetail(salesuserid,type){
    			var businessdate1 = $("#report-query-businessdate1").val();
    			var businessdate2 = $("#report-query-businessdate2").val();
    			var url = 'report/finance/showFundsCustomerListPage.do?salesuserid='+salesuserid+'&type='+type+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2;
    			$("#report-fundsCustomer-detail-dialog").dialog({
				    title: '分客户业务员销售回单列表',  
		    		width:800,
		    		height:400,
		    		closed:false,
		    		modal:true,
		    		maximizable:true,
		    		cache:false,
		    		resizable:true,
				    href: url
				});
    		}
    	</script>
  </body>
</html>
