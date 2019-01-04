<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分部门资金回笼表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>     
  </head>
  
  <body>
    	<table id="report-datagrid-salesdeptWithdrawn"></table>
    	<div id="report-toolbar-salesdeptWithdrawn" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/salesdeptWithdrawnExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-fundsDeptReturnPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                </security:authorize>
                <div id="dialog-autoexport"></div>
            </div>
    		<form action="" id="report-query-form-salesdeptWithdrawn" method="post">
    		<table>
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input id="report-query-businessdate2" type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
	    			<td>销售部门</td>
    				<td><input type="text" id="report-query-deptid" name="salesdept"/></td>
    				<td>
    					<a href="javaScript:void(0);" id="report-queay-salesdeptWithdrawn" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-salesdeptWithdrawn" class="button-qr">重置</a>

    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-fundsCustomer-detail-dialog"></div>
    	<div id="report-fundsSalesDept-detail-dialog"></div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-salesdeptWithdrawn").serializeJSON();
    		$(function(){

                $("#report-buttons-fundsDeptReturnPage").click(function(){
                    var queryJSON = $("#report-query-form-salesdeptWithdrawn").serializeJSON();
                    //获取排序规则
                    var objecr  = $("#report-datagrid-salesdeptWithdrawn").datagrid("options");
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryJSON["sort"] = objecr.sortName;
                        queryJSON["order"] = objecr.sortOrder;
                    }
                    //存入导出时的参数
                    queryJSON["groupcols"] = "salesdept";
                    var queryParam = JSON.stringify(queryJSON);
                    var url = "report/finance/exportBaseFinanceDrawnData.do";
                    exportByAnalyse(queryParam,"分销售部门资金回笼表","report-datagrid-salesdeptWithdrawn",url);
                });

                var tableColumnListJson = $("#report-datagrid-salesdeptWithdrawn").createGridColumnLoad({
                    frozenCol: [[
                        {field: 'idok', checkbox: true, isShow: true}
                    ]],
                    commonCol: [[
                        {field:'salesdept',title:'销售部门',width:80,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.salesdeptname;
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
    			$("#report-datagrid-salesdeptWithdrawn").datagrid({
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
		  	 		singleSelect:true,
					toolbar:'#report-toolbar-salesdeptWithdrawn',
					onDblClickRow:function(rowIndex, rowData){
						var salesdept = rowData.salesdept;
						var salesdeptname = rowData.salesdeptname;
						var businessdate1 = $("#report-query-businessdate1").val();
    					var businessdate2 = $("#report-query-businessdate2").val();
    					var url = 'report/finance/showSalesdeptWithdrawnDetailListPage.do?salesdept='+salesdept+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2+'&salesdeptname='+salesdeptname;
						$("#report-fundsSalesDept-detail-dialog").dialog({
							title:'按部门:['+rowData.salesdeptname+']统计',
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
						$("#report-fundsSalesDept-detail-dialog").dialog("open");
					}
				}).datagrid("columnMoving");
				$("#report-query-deptid").widget({
					referwid:'RL_T_BASE_DEPARTMENT_SELLER',
		    		width:210,
					singleSelect:false
				});
				
				//回车事件
				controlQueryAndResetByKey("report-queay-salesdeptWithdrawn","report-reload-salesdeptWithdrawn");
				
				//查询
				$("#report-queay-salesdeptWithdrawn").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-salesdeptWithdrawn").serializeJSON();
		      		$("#report-datagrid-salesdeptWithdrawn").datagrid({
		      			url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=salesdept',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-salesdeptWithdrawn").click(function(){
					$("#report-query-deptid").widget("clear");
					$("#report-query-form-salesdeptWithdrawn")[0].reset();
					var queryJSON = $("#report-query-form-salesdeptWithdrawn").serializeJSON();
		       		$("#report-datagrid-salesdeptWithdrawn").datagrid('loadData',{total:0,rows:[]});
				});

    		});
    		
    		function showDetail(deptid,type){
    			var businessdate1 = $("#report-query-businessdate1").val();
    			var businessdate2 = $("#report-query-businessdate2").val();
    			var url = 'report/finance/showFundsCustomerListPage.do?deptid='+deptid+'&type='+type+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2;
    			$("#report-fundsCustomer-detail-dialog").dialog({
				    title: '分部门销售回单列表',  
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
