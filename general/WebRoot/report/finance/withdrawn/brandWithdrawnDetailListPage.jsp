<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>按品牌分客户,分商品统计页面</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>     
  </head>
  
  <body>
  	<form action="" id="report-query-form-brandWithdrawnDetail" method="post">
		<input type="hidden" name="brandid" value="${brandid}"/>
		<input type="hidden" name="businessdate1" value="${businessdate1}"/>
		<input type="hidden" name="businessdate2" value="${businessdate2}"/>
	</form>
  	<div id="report-tab-brandWithdrawnDetail" class="buttonBG">
		<security:authorize url="/report/finance/fundsReturnDataDetailExport.do">
			<a href="javaScript:void(0);" id="report-export-brandWithdrawnDetail" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
		</security:authorize>
	</div>
	<div id="tt" class="easyui-tabs" data-options="fit:true">
	    <div title="分客户统计" style="padding:2px;">
	       <table id="report-datagrid-brandWithdrawnDetail-customer"></table>
	    </div>
	    <div title="分商品统计" style="padding:2px;">
	        <table id="report-datagrid-brandWithdrawnDetail-goods"></table>
	    </div>
	</div>
	<div id="report-fundsCustomer1-detail-dialog"></div>
    	<script type="text/javascript">
    		$('#tt').tabs({
				tools:'#report-tab-brandWithdrawnDetail'
			});
    		var initQueryJSON = $("#report-query-form-brandWithdrawnDetail").serializeJSON();
    		$(function(){
                var customerColumnListJson = $("#report-datagrid-brandWithdrawnDetail-customer").createGridColumnLoad({
                    frozenCol : [[]],
                    commonCol : [[
                        {field:'customerid',title:'客户编码',width:60},
                        {field:'customername',title:'客户名称',width:210},
                        {field:'pcustomerid',title:'总店名称',sortable:true,width:60,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.pcustomername;
                            }
                        },
                        {field:'salesarea',title:'所属区域',sortable:true,width:60,hidden:true,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.salesareaname;
                            }
                        },
                        {field:'salesdept',title:'所属部门',sortable:true,width:60,hidden:true,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.salesdeptname;
                            }
                        },
                        {field:'withdrawnamount',title:'回笼金额',align:'right',width:60,isShow:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        }
                        <c:if test="${map.costwriteoffamount == 'costwriteoffamount'}">
                        ,
                        {field:'costwriteoffamount',title:'回笼成本',align:'right',width:60,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        }
                        </c:if>
                        <c:if test="${map.writeoffmarginamount == 'writeoffmarginamount'}">
                        ,
                        {field:'writeoffmarginamount',title:'回笼毛利额',align:'right',width:70,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        }
                        </c:if>
                        <c:if test="${map.writeoffrate == 'writeoffrate'}">
                        ,
                        {field:'writeoffrate',title:'回笼毛利率',align:'right',width:70,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                if(null != value && "" != value){
                                    return formatterMoney(value)+"%";
                                }
                            }
                        }
                        </c:if>
                    ]]
                });

                var goodsColumnListJson = $("#report-datagrid-brandWithdrawnDetail-goods").createGridColumnLoad({
                    frozenCol : [[]],
                    commonCol : [[
                        {field:'goodsid',title:'商品编号',sortable:true,width:60},
                        {field:'goodsname',title:'商品名称',width:250},
                        {field:'barcode',title:'条形码',sortable:true,width:90},
                        {field:'brandname',title:'品牌名称',width:60},
                        {field:'unitnum',title:'数量',width:60,
                            formatter:function(value,rowData,rowIndex){
                                return formatterBigNumNoLen(value);
                            }
                        },
                        {field:'auxnumdetail',title:'辅数量',width:80,hidden:true},
                        {field:'withdrawnamount',title:'回笼金额',align:'right',width:60,isShow:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        }
                        <c:if test="${map.costwriteoffamount == 'costwriteoffamount'}">
                        ,
                        {field:'costwriteoffamount',title:'回笼成本',align:'right',width:60,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        }
                        </c:if>
                        <c:if test="${map.writeoffmarginamount == 'writeoffmarginamount'}">
                        ,
                        {field:'writeoffmarginamount',title:'回笼毛利额',align:'right',width:70,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        }
                        </c:if>
                        <c:if test="${map.writeoffrate == 'writeoffrate'}">
                        ,
                        {field:'writeoffrate',title:'回笼毛利率',align:'right',width:70,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                if(null != value && "" != value){
                                    return formatterMoney(value)+"%";
                                }
                            }
                        }
                        </c:if>
                    ]]
                });

    			$("#report-datagrid-brandWithdrawnDetail-customer").datagrid({
                    authority:customerColumnListJson,
                    frozenColumns: customerColumnListJson.frozen,
                    columns:customerColumnListJson.common,
			 		method:'post',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		pageSize:100,
		  	 		singleSelect:true,
					url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=customerid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
				$("#report-datagrid-brandWithdrawnDetail-goods").datagrid({
                    authority:goodsColumnListJson,
                    frozenColumns: goodsColumnListJson.frozen,
                    columns:goodsColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
		  	 		pageSize:100,
					url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=goodsid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
				
				$("#report-export-brandWithdrawnDetail").Excel('export',{
					queryForm: "#report-query-form-brandWithdrawnDetail", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'按品牌部门：[${brandname}]统计表',
			 		url:'report/finance/exportFinanceWithdrawnDetailReportData.do?groupcols=customerid;goodsid'
				});
    		});
    	</script>
  </body>
</html>
