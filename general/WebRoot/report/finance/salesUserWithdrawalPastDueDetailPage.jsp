<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>按客户业务员分客户,分商品,分客户分商品统计页面</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>     
  </head>
  
  <body>
  	<form action="" id="report-query-form-salesuserWithdrawalPastDue" method="post">
		<input type="hidden" name="salesuser" value="${salesuser}"/>
		<input type="hidden" name="businessdate1" value="${businessdate1}"/>
		<input type="hidden" name="businessdate2" value="${businessdate2}"/>
	</form>
  	<div id="report-tab-salesuserWithdrawalPastDue" class="buttonBG">
		<security:authorize url="/report/finance/receivablePastDueExport.do">
			<a href="javaScript:void(0);" id="report-export-salesuserWithdrawalPastDue" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
		</security:authorize>
	</div>
	<div id="tt" class="easyui-tabs" data-options="fit:true">
	    <div title="分客户统计" style="padding:2px;">
	       <table id="report-datagrid-salesuserWithdrawalPastDue-customer"></table>
	    </div>
	    <div title="分商品统计" style="padding:2px;">
	        <table id="report-datagrid-salesuserWithdrawalPastDue-goods"></table>
	    </div>
	    <div title="分客户分商品统计" style="padding:2px;">
	        <table id="report-datagrid-salesuserWithdrawalPastDue-customerandgoods"></table>
	    </div>
	</div>
	<div id="report-fundsCustomer1-detail-dialog"></div>
    	<script type="text/javascript">
    		$('#tt').tabs({
				tools:'#report-tab-salesuserWithdrawalPastDue'
			});
    		var initQueryJSON = $("#report-query-form-salesuserWithdrawalPastDue").serializeJSON();
    		$(function(){
    			var customertableColumnListJson = $("#report-datagrid-salesuserWithdrawalPastDue-customer").createGridColumnLoad({
					frozenCol : [[
				    			]],
					commonCol : [[
						  {field:'customerid',title:'客户编码',width:80},
						  {field:'customername',title:'客户名称',width:210},
						  {field:'settletypename',title:'结算方式',width:60,hidden:true},
						  {field:'taxamount',title:'回笼金额',align:'right',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  <c:if test="${map.costamount == 'costamount'}">
							  {field:'costamount',title:'回笼成本',align:'right',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
						  </c:if>
						  <c:if test="${map.marginamount == 'marginamount'}">
							  {field:'marginamount',title:'回笼毛利额',align:'right',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
						  </c:if>
						  <c:if test="${map.marginrate == 'marginrate'}">
							  {field:'marginrate',title:'回笼毛利率',align:'right',width:80,
							  	formatter:function(value,rowData,rowIndex){
							  		if(value!=""){
					        			return formatterMoney(value)+"%";
					        		}
					        	}
							  },
						  </c:if>
						  {field:'unpassamount',title:'正常期金额',align:'right',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'totalpassamount',title:'超账期金额',align:'right',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
			        			return formatterMoney(value);
				        	}
						  },
						  <c:forEach items="${list }" var="list">
						  {field:'passamount${list.seq}',title:'${list.detail}',align:'right',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
			        			return formatterMoney(value);
				        	}
						  },
   						  </c:forEach>
   						  {field:'returnamount',title:'退货金额',align:'right',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'returnrate',title:'退货率',align:'right',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
						  		if(value!=null && value!=""){
				        			return formatterMoney(value)+"%";
				        		}
				        	}
						  },
						  {field:'pushamount',title:'冲差金额',align:'right',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
			         ]]
				});
    			
    			var goodstableColumnListJson = $("#report-datagrid-salesuserWithdrawalPastDue-goods").createGridColumnLoad({
					frozenCol : [[
				    			]],
					commonCol : [[
                        {field:'goodsid',title:'商品编号',sortable:true,width:60},
                        {field:'goodsname',title:'商品名称',width:210},
                        {field:'brandname',title:'品牌名称',width:80},
                        {field:'unitnum',title:'数量',width:60,
							formatter:function(value,rowData,rowIndex){
								return formatterBigNumNoLen(value);
							}
						},
                        {field:'auxnumdetail',title:'辅数量',width:80,hidden:true},
                        {field:'taxamount',title:'回笼金额',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        <c:if test="${map.costamount == 'costamount'}">
                        {field:'costamount',title:'回笼成本',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        </c:if>
                        <c:if test="${map.marginamount == 'marginamount'}">
                        {field:'marginamount',title:'回笼毛利额',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        </c:if>
                        <c:if test="${map.marginrate == 'marginrate'}">
                        {field:'marginrate',title:'回笼毛利率',align:'right',width:80,
                            formatter:function(value,rowData,rowIndex){
                                if(value!=""){
                                    return formatterMoney(value)+"%";
                                }
                            }
                        },
                        </c:if>
                        {field:'unpassamount',title:'正常期金额',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        {field:'totalpassamount',title:'超账期金额',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        <c:forEach items="${list }" var="list">
                        {field:'passamount${list.seq}',title:'${list.detail}',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        </c:forEach>
                        {field:'returnamount',title:'退货金额',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        {field:'returnrate',title:'退货率',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                if(value!=null && value!=""){
                                    return formatterMoney(value)+"%";
                                }
                            }
                        },
                        {field:'pushamount',title:'冲差金额',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        }
			         ]]
				});
    			
    			var customerandgoodstableColumnListJson = $("#report-datagrid-salesuserWithdrawalPastDue-customerandgoods").createGridColumnLoad({
					frozenCol : [[
				    			]],
					commonCol : [[
                        {field:'customerid',title:'客户编码',width:80},
                        {field:'customername',title:'客户名称',width:210},
                        {field:'settletypename',title:'结算方式',width:60,hidden:true},
                        {field:'goodsid',title:'商品编号',sortable:true,width:60},
                        {field:'goodsname',title:'商品名称',width:210},
                        {field:'brandname',title:'品牌名称',width:80},
                        {field:'unitnum',title:'数量',width:60,
							formatter:function(value,rowData,rowIndex){
								return formatterBigNumNoLen(value);
							}
						},
                        {field:'auxnumdetail',title:'辅数量',width:80,hidden:true},
                        {field:'taxamount',title:'回笼金额',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        <c:if test="${map.costamount == 'costamount'}">
                        {field:'costamount',title:'回笼成本',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        </c:if>
                        <c:if test="${map.marginamount == 'marginamount'}">
                        {field:'marginamount',title:'回笼毛利额',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        </c:if>
                        <c:if test="${map.marginrate == 'marginrate'}">
                        {field:'marginrate',title:'回笼毛利率',align:'right',width:80,
                            formatter:function(value,rowData,rowIndex){
                                if(value!=""){
                                    return formatterMoney(value)+"%";
                                }
                            }
                        },
                        </c:if>
                        {field:'unpassamount',title:'正常期金额',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        {field:'totalpassamount',title:'超账期金额',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        <c:forEach items="${list }" var="list">
                        {field:'passamount${list.seq}',title:'${list.detail}',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        </c:forEach>
                        {field:'returnamount',title:'退货金额',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        {field:'returnrate',title:'退货率',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                if(value!=null && value!=""){
                                    return formatterMoney(value)+"%";
                                }
                            }
                        },
                        {field:'pushamount',title:'冲差金额',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        }
			         ]]
				});
    			$("#report-datagrid-salesuserWithdrawalPastDue-customer").datagrid({
					authority:customertableColumnListJson,
			 		frozenColumns: customertableColumnListJson.frozen,
					columns:customertableColumnListJson.common,
			 		method:'post',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
					url: 'report/finance/showBaseWithdrawnPastdueListData.do?groupcols=customerid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
				$("#report-datagrid-salesuserWithdrawalPastDue-goods").datagrid({ 
					authority:goodstableColumnListJson,
			 		frozenColumns: goodstableColumnListJson.frozen,
					columns:goodstableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
					url: 'report/finance/showBaseWithdrawnPastdueListData.do?groupcols=goodsid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
				
				$("#report-datagrid-salesuserWithdrawalPastDue-customerandgoods").datagrid({ 
					authority:customerandgoodstableColumnListJson,
			 		frozenColumns: customerandgoodstableColumnListJson.frozen,
					columns:customerandgoodstableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
					url: 'report/finance/showBaseWithdrawnPastdueListData.do?groupcols=customerid,goodsid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
				
				$("#report-export-salesuserWithdrawalPastDue").Excel('export',{
					queryForm: "#report-query-form-salesuserWithdrawalPastDue", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'按客户业务员：[${salesusername}]统计表',
			 		url:'report/finance/exportWithdrawalPastDueDetailData.do?groupcols=customerid;goodsid;customerid,goodsid'
				});
    		});
    	</script>
  </body>
</html>
