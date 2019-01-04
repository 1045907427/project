<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分客户资金回笼表</title>
    <%@include file="/include.jsp" %> 
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>    
  </head>
  
  <body>
    	<table id="report-datagrid-CustomerWithdrawn"></table>
    	<div id="report-toolbar-CustomerWithdrawn" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/CustomerWithdrawnExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-CustomerWithdrawnPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                </security:authorize>
                <div id="dialog-autoexport"></div>
            </div>
    		<form action="" id="report-query-form-CustomerWithdrawn" method="post">
	    		<table>

	    			<tr>
	    				<td>业务日期:</td>
	    				<td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
	    				<td>客户名称:</td>
	    				<td><input type="text" id="report-query-customerid" name="customerid"/></td>
	    			</tr>
	    			<tr>
	    				<td>总店名称:</td>
	    				<td><input type="text" id="report-query-pcustomerid" name="pcustomerid"/></td>
                        <td></td>
	    				<td>
	    					<a href="javaScript:void(0);" id="report-queay-CustomerWithdrawn" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="report-reload-CustomerWithdrawn" class="button-qr">重置</a>
	    				</td>
	    			</tr>
	    		</table>
	    	</form>
    	</div>
    	<div id="report-fundsCustomer-detail-dialog"></div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-CustomerWithdrawn").serializeJSON();
    		$(function(){

                $("#report-buttons-CustomerWithdrawnPage").click(function(){
                    var queryJSON = $("#report-query-form-CustomerWithdrawn").serializeJSON();
                    //获取排序规则
                    var objecr  = $("#report-datagrid-CustomerWithdrawn").datagrid("options");
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryJSON["sort"] = objecr.sortName;
                        queryJSON["order"] = objecr.sortOrder;
                    }
                    //存入导出时的参数
                    queryJSON["groupcols"] = "customerid";
                    var queryParam = JSON.stringify(queryJSON);
                    var url = "report/finance/exportBaseFinanceDrawnData.do";
                    exportByAnalyse(queryParam,"分客户资金回笼表","report-datagrid-CustomerWithdrawn",url);
                });

                var tableColumnListJson = $("#report-datagrid-baseFinanceDrawn").createGridColumnLoad({
                    frozenCol : [[]],
                    commonCol : [[
                        {field:'customerid',title:'客户编码',width:60},
                        {field:'customername',title:'客户名称',width:210},
                        {field:'pcustomerid',title:'总店名称',width:60,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                if(rowData.customerid!=rowData.pcustomerid){
                                    return rowData.pcustomername;
                                }else{
                                    return "";
                                }
                            }
                        },
                        {field:'salesarea',title:'销售区域',sortable:true,width:60,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.salesareaname;
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

    			$("#report-datagrid-CustomerWithdrawn").datagrid({
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
					toolbar:'#report-toolbar-CustomerWithdrawn',
					onDblClickRow:function(rowIndex, rowData){
						var customerid = rowData.customerid;
						var customername = rowData.customername;
						var businessdate1 = $("#report-query-businessdate1").val();
    					var businessdate2 = $("#report-query-businessdate2").val();
    					var url = 'report/finance/showCustomerWithdrawnDetailListPage.do?customerid='+customerid+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2+'&customername='+customername;
						$("#report-fundsCustomer-detail-dialog").dialog({
							title:'按客户:['+rowData.customername+']统计',
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
						$("#report-fundsCustomer-detail-dialog").dialog("open");
					}
				}).datagrid("tooltip");
				$("#report-query-customerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER',
		    		width:205,
					singleSelect:true
				});
				$("#report-query-pcustomerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
		    		width:225,
					singleSelect:true
				});
				
				//回车事件
				controlQueryAndResetByKey("report-queay-CustomerWithdrawn","report-reload-CustomerWithdrawn");
				
				//查询
				$("#report-queay-CustomerWithdrawn").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-CustomerWithdrawn").serializeJSON();
		      		$("#report-datagrid-CustomerWithdrawn").datagrid({
		      			url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=customerid',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-CustomerWithdrawn").click(function(){
					$("#report-query-customerid").widget("clear");
					$("#report-query-pcustomerid").widget("clear");
					$("#report-query-form-CustomerWithdrawn").form("reset");
					var queryJSON = $("#report-query-form-CustomerWithdrawn").serializeJSON();
		       		$("#report-datagrid-CustomerWithdrawn").datagrid('loadData',{total:0,rows:[]});
				});

    		});
    		
    		function showDetail(customerid,type){
    			var businessdate1 = $("#report-query-businessdate1").val();
    			var businessdate2 = $("#report-query-businessdate2").val();
    			var url = 'report/finance/showFundsCustomerListPage.do?customerid='+customerid+'&type='+type+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2;
    			$("#report-fundsCustomer-detail-dialog").dialog({
				    title: '分客户销售回单列表',  
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
