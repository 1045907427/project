<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分品牌业务员资金回笼表</title>
    <%@include file="/include.jsp" %>  
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>   
  </head>
  
  <body>
    	<table id="report-datagrid-branduserWithdrawn"></table>
    	<div id="report-toolbar-branduserWithdrawn" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/branduserWithdrawnExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-branduserWithdrawnPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                </security:authorize>
                <div id="dialog-autoexport"></div>
            </div>
    		<form action="" id="report-query-form-branduserWithdrawn" method="post">
    		<table class="querytable">
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>品牌业务员:</td>
    				<td><input type="text" id="report-query-banduserid" name="branduser"/></td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="report-queay-branduserWithdrawn" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-branduserWithdrawn" class="button-qr">重置</a>
    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-fundsBrand-detail-dialog"></div>
    	<div id="report-dialog-branduserWithdrawnDetail"></div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-branduserWithdrawn").serializeJSON();
    		$(function(){

                $("#report-buttons-branduserWithdrawnPage").click(function(){console.log(3);
                    var queryJSON = $("#report-query-form-branduserWithdrawn").serializeJSON();
                    //获取排序规则
                    var objecr  = $("#report-datagrid-branduserWithdrawn").datagrid("options");
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryJSON["sort"] = objecr.sortName;
                        queryJSON["order"] = objecr.sortOrder;
                    }
                    //存入导出时的参数
                    queryJSON["groupcols"] = "branduser";
                    var queryParam = JSON.stringify(queryJSON);
                    var url = "report/finance/exportBaseFinanceDrawnData.do";
                    exportByAnalyse(queryParam,"分品牌业务员资金回笼表","report-datagrid-branduserWithdrawn",url);
                });

                var tableColumnListJson = $("#report-datagrid-branduserWithdrawn").createGridColumnLoad({
                    frozenCol: [[
                        {field: 'idok', checkbox: true, isShow: true}
                    ]],
                    commonCol: [[
                        {field:'branduser',title:'品牌业务员',width:70,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.brandusername;
                            }
                        },
                        {field:'branddept',title:'品牌部门',width:80,hidden:true,
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
    			$("#report-datagrid-branduserWithdrawn").datagrid({
                    authority:tableColumnListJson,
                    frozenColumns: tableColumnListJson.frozen,
                    columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		pageSize:100,
		  	 		singleSelect:true,
					toolbar:'#report-toolbar-branduserWithdrawn',
					onDblClickRow:function(rowIndex, rowData){
						var branduser = rowData.branduser;
						var brandusername = rowData.brandusername;
						var businessdate1 = $("#report-query-businessdate1").val();
    					var businessdate2 = $("#report-query-businessdate2").val();
    					var url = 'report/finance/showBranduserWithdrawnDetailListPage.do?branduser='+branduser+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2+'&brandusername='+brandusername;
						$("#report-dialog-branduserWithdrawnDetail").dialog({
							title:'按品牌业务员:['+rowData.brandusername+']统计',
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
						$("#report-dialog-branduserWithdrawnDetail").dialog("open");
					}
				}).datagrid("columnMoving");
				$("#report-query-banduserid").widget({
					referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
		    		width:130,
					singleSelect:true
				});
				
				//回车事件
				controlQueryAndResetByKey("report-queay-branduserWithdrawn","report-reload-branduserWithdrawn");
				
				//查询
				$("#report-queay-branduserWithdrawn").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-branduserWithdrawn").serializeJSON();
		      		$("#report-datagrid-branduserWithdrawn").datagrid({
		      			url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=branduser',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-branduserWithdrawn").click(function(){
					$("#report-query-banduserid").widget("clear");
					$("#report-query-form-branduserWithdrawn").form("reset");
					var queryJSON = $("#report-query-form-branduserWithdrawn").serializeJSON();
		       		$("#report-datagrid-branduserWithdrawn").datagrid('loadData',{total:0,rows:[]});
				});
				
    		});
    		
    		function showDetail(branduser,type){
    			var businessdate1 = $("#report-query-businessdate1").val();
    			var businessdate2 = $("#report-query-businessdate2").val();
    			var url = 'report/finance/showFundsBrandListPage.do?branduser='+branduser+'&type='+type+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2;
    			$("#report-fundsBrand-detail-dialog").dialog({
				    title: '分品牌业务员销售回单列表',  
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
