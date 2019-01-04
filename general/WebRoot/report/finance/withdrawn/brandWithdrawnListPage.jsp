<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分品牌资金回笼表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>     
  </head>
  
  <body>
    	<table id="report-datagrid-brandWithdrawn"></table>
    	<div id="report-toolbar-brandWithdrawn" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/brandWithdrawnExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-brandWithdrawn" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                </security:authorize>
                <div id="dialog-autoexport"></div>
            </div>
    		<form action="" id="report-query-form-brandWithdrawn" method="post">
    		<table class="querytable">
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>商品品牌:</td>
    				<td><input type="text" id="report-query-brandid" name="brandid"/></td>
    				<td>
    					<a href="javaScript:void(0);" id="report-queay-brandWithdrawn" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-brandWithdrawn" class="button-qr">重置</a>

    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-fundsBrand-detail-dialog"></div>
    	<div id="report-fundsBrandCustomerGoods-detail-dialog"></div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-brandWithdrawn").serializeJSON();
    		$(function(){

                $("#report-buttons-brandWithdrawn").click(function(){
                    var queryJSON = $("#report-query-form-brandWithdrawn").serializeJSON();
                    //获取排序规则
                    var objecr  = $("#report-datagrid-brandWithdrawn").datagrid("options");
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryJSON["sort"] = objecr.sortName;
                        queryJSON["order"] = objecr.sortOrder;
                    }
                    //存入导出时的参数
                    queryJSON["groupcols"] = "brandid";
                    var queryParam = JSON.stringify(queryJSON);
                    var url = "report/finance/exportBaseFinanceDrawnData.do";
                    exportByAnalyse(queryParam,"分品牌资金回笼表","report-datagrid-brandWithdrawn",url);
                });

                var tableColumnListJson = $("#report-datagrid-brandWithdrawn").createGridColumnLoad({
                    frozenCol : [[]],
                    commonCol : [[
                        {field:'brandid',title:'商品品牌',width:80,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.brandname;
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

    			$("#report-datagrid-brandWithdrawn").datagrid({
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
					toolbar:'#report-toolbar-brandWithdrawn',
					onDblClickRow:function(rowIndex, rowData){
						var brandid = rowData.brandid;
						var brandname = rowData.brandname;
						var businessdate1 = $("#report-query-businessdate1").val();
    					var businessdate2 = $("#report-query-businessdate2").val();
    					var url = 'report/finance/showBrandWithdrawnDetailListPage.do?brandid='+brandid+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2+'&brandname='+brandname;
						$("#report-fundsBrandCustomerGoods-detail-dialog").dialog({
							title:'按品牌:['+rowData.brandname+']统计',
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
						$("#report-fundsBrandCustomerGoods-detail-dialog").dialog("open");
					}
				}).datagrid("columnMoving");
				$("#report-query-brandid").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
		    		width:210,
					singleSelect:false
				});
				
				//回车事件
				controlQueryAndResetByKey("report-queay-brandWithdrawn","report-reload-brandWithdrawn");
				
				//查询
				$("#report-queay-brandWithdrawn").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-brandWithdrawn").serializeJSON();
		      		$("#report-datagrid-brandWithdrawn").datagrid({
		      			url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=brandid',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-brandWithdrawn").click(function(){
					$("#report-query-brandid").widget("clear");
					$("#report-query-form-brandWithdrawn")[0].reset();
		       		$("#report-datagrid-brandWithdrawn").datagrid('loadData',{total:0,rows:[]});
				});
				
    		});
    		
    		function showDetail(brand,type){
    			var businessdate1 = $("#report-query-businessdate1").val();
    			var businessdate2 = $("#report-query-businessdate2").val();
    			var url = 'report/finance/showFundsBrandListPage.do?brand='+brand+'&type='+type+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2;
    			$("#report-fundsBrand-detail-dialog").dialog({
				    title: '分品牌销售回单列表',  
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
