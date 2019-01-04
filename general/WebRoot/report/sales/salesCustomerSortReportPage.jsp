<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>按客户分类销售情况统计报表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-salesCustomerSort"></table>
    	<div id="report-toolbar-salesCustomerSort" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/sales/salesCustomerSortReportExport.do">
                    <a href="javaScript:void(0);" id="report-autoExport-salesCustomerSortPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-salesCustomerSort" method="post">
	    		<table class="querytable">
	    			<tr>
	    				<td>业务日期:</td>
	    				<td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
	    				<td>客户分类:</td>
	    				<td>
	    					<input type="text" id="report-query-customersort" name="customersort" style="width: 150px;"/>
	    				</td>
	    				<td colspan="2">
	    					<a href="javaScript:void(0);" id="report-queay-salesCustomerSort" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="report-reload-salesCustomerSort" class="button-qr">重置</a>

	    				</td>
	    			</tr>
	    		</table>
	    	</form>
    	</div>
    	<div id="report-dialog-salesCustomerSortDetail"></div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-salesCustomerSort").serializeJSON();
    		$(function(){
                $("#report-autoExport-salesCustomerSortPage").click(function(){
                    var queryJSON = $("#report-query-form-salesCustomerSort").serializeJSON();
                    //获取排序规则
                    var objecr  = $("#report-datagrid-salesCustomerSort").datagrid("options");
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryJSON["sort"] = objecr.sortName;
                        queryJSON["order"] = objecr.sortOrder;
                    }
                    var queryParam = JSON.stringify(queryJSON);
                    var url = "report/sales/exportBaseSalesReportData.do?groupcols=customersort";
                    exportByAnalyse(queryParam,"分客户分类销售情况统计报表","report-datagrid-salesCustomerSort",url);

                });
                
                var tableColumnListJson = $("#report-datagrid-salesCustomerSort").createGridColumnLoad({
					frozenCol : [[
									{field:'idok',checkbox:true,isShow:true}
				    			]],
					commonCol : [[
								  {field:'customersort',title:'客户分类',width:80,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.customersortname;
						        	}
								  },
								  {field:'salesarea',title:'所属区域',sortable:true,width:80,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.salesareaname;
						        	}
								  },
								  {field:'salesdept',title:'所属部门',sortable:true,width:80,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.salesdeptname;
						        	}
								  },
								  {field:'ordernum',title:'订单数量',width:60,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'ordertotalbox',title:'订单箱数',width:60,align:'right',isShow:true,sortable:true,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'orderamount',title:'订单金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'ordernotaxamount',title:'订单未税金额',width:80,sortable:true,align:'right',isShow:true,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'initsendnum',title:'发货单数量',width:70,align:'right',hidden:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'initsendtotalbox',title:'发货单箱数',width:70,align:'right',isShow:true,sortable:true,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'initsendamount',title:'发货单金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'initsendnotaxamount',title:'发货单未税金额',width:90,sortable:true,align:'right',isShow:true,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'sendnum',title:'发货出库数量',width:80,align:'right',hidden:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'sendtotalbox',title:'发货出库箱数',width:80,align:'right',isShow:true,sortable:true,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'sendamount',title:'发货出库金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'sendnotaxamount',title:'发货出库未税金额',width:100,sortable:true,align:'right',isShow:true,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  <c:if test="${map.sendcostamount == 'sendcostamount'}">
									  {field:'sendcostamount',title:'发货出库成本',width:80,sortable:true,align:'right',hidden:true,
									  	formatter:function(value,rowData,rowIndex){
							        		return formatterMoney(value);
							        	}
									  },
								  </c:if>
								  {field:'pushbalanceamount',title:'冲差金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'directreturnnum',title:'直退数量',width:60,sortable:true,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'directreturntotalbox',title:'直退箱数',width:60,sortable:true,align:'right',isShow:true,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'directreturnamount',title:'直退金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'checkreturnnum',title:'退货数量',width:60,sortable:true,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'checkreturntotalbox',title:'退货箱数',width:60,sortable:true,align:'right',isShow:true,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'checkreturnamount',title:'退货金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'checkreturnrate',title:'退货率',width:60,align:'right',isShow:true,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
								  		if(value!=null && value!=0){
						        			return formatterMoney(value)+"%";
						        		}else{
						        			return "";
						        		}
						        	}
								  },
								  {field:'returnnum',title:'退货总数量',width:70,sortable:true,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'returntotalbox',title:'退货总箱数',width:70,sortable:true,align:'right',isShow:true,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'returnamount',title:'退货合计',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'salenum',title:'销售数量',width:60,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'saletotalbox',title:'销售箱数',width:60,align:'right',isShow:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'saleamount',title:'销售金额',resizable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'salenotaxamount',title:'销售无税金额',width:80,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'saletax',title:'销售税额',width:60,align:'right',isShow:true,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  }
								  <c:if test="${map.costamount == 'costamount'}">
								  	,
								  	{field:'costamount',title:'成本金额',align:'right',resizable:true,isShow:true,
									  	formatter:function(value,rowData,rowIndex){
							        		return formatterMoney(value);
							        	}
									 }
								  </c:if>
								  <c:if test="${map.salemarginamount == 'salemarginamount'}">
									  ,
									  {field:'salemarginamount',title:'销售毛利额',resizable:true,align:'right',
									  	formatter:function(value,rowData,rowIndex){
							        		return formatterMoney(value);
							        	}
									  }
								  </c:if>
								  <c:if test="${map.realrate == 'realrate'}">
									  ,
									  {field:'realrate',title:'实际毛利率',width:70,align:'right',isShow:true,
									  	formatter:function(value,rowData,rowIndex){
							        		if(value!=null && value!=0){
							        			return formatterMoney(value)+"%";
							        		}else{
							        			return "";
							        		}
							        	}
									  }
								  </c:if>
					             ]]
				});
    			$("#report-datagrid-salesCustomerSort").datagrid({ 
			 		authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
		  	 		pageSize:100,
					toolbar:'#report-toolbar-salesCustomerSort',
					onDblClickRow:function(rowIndex, rowData){
<!--						var customerid = rowData.customerid;-->
<!--						var customername = rowData.customername;-->
<!--						var businessdate1 = $("#report-query-businessdate1").val();-->
<!--    					var businessdate2 = $("#report-query-businessdate2").val();-->
<!--    					var url = 'report/sales/showsalesCustomerSortDetailPage.do?customerid='+customerid+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2+'&customername='+customername;-->
<!--						$("#report-dialog-salesCustomerSortDetail").dialog({-->
<!--							title:'按客户:['+rowData.customername+']统计',-->
<!--				    		width:800,-->
<!--				    		height:400,-->
<!--				    		closed:true,-->
<!--				    		modal:true,-->
<!--				    		maximizable:true,-->
<!--				    		cache:false,-->
<!--				    		resizable:true,-->
<!--				    		maximized:true,-->
<!--						    href: url-->
<!--						});-->
<!--						$("#report-dialog-salesCustomerSortDetail").dialog("open");-->
					},
		  	 		onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SR_footerobject = footerrows[0];
							baseSalesReportCountTotalAmount('customersortname',this);
						}
			 		},
					onCheckAll:function(){
						baseSalesReportCountTotalAmount('customersortname',this);
					},
					onUncheckAll:function(){
						baseSalesReportCountTotalAmount('customersortname',this);
					},
					onCheck:function(){
						baseSalesReportCountTotalAmount('customersortname',this);
					},
					onUncheck:function(){
						baseSalesReportCountTotalAmount('customersortname',this);
					}
				}).datagrid("columnMoving");
				//回车事件
				controlQueryAndResetByKey("report-queay-salesCustomerSort","report-reload-salesCustomerSort");
				$("#report-query-customersort").widget({
					referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
		   			singleSelect:true,
		   			width:'150',
		   			onlyLeafCheck:false
				});
				//查询
				$("#report-queay-salesCustomerSort").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-salesCustomerSort").serializeJSON();
		      		$("#report-datagrid-salesCustomerSort").datagrid({
		      			url: 'report/sales/showBaseSalesReportData.do?groupcols=customersort',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-salesCustomerSort").click(function(){
					$("#report-query-customersort").widget("clear");
					var queryJSON = $("#report-query-form-salesCustomerSort").serializeJSON();
		       		$("#report-datagrid-salesCustomerSort").datagrid('loadData',{total:0,rows:[],footer:[]});
				});

    		});

        </script>
  </body>
</html>
