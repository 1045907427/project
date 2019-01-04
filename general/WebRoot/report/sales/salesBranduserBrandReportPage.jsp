<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分品牌业务员分品牌销售情况统计报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>     
  </head>
  
  <body>
    	<table id="report-datagrid-salesBrandUserBrand"></table>
    	<div id="report-toolbar-salesBrandUserBrand" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/sales/salesBrandUserBrandReportExport.do">
                    <a href="javaScript:void(0);" id="report-autoExport-salesBrandUserBrandPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-salesBrandUserBrand" method="post">
	    		<table class="querytable">
	    			<tr>
	    				<td>业务日期:</td>
	    				<td><input id="report-query-businessdate1" type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 
	    					到 <input id="report-query-businessdate2" type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                        <td>商品品牌:</td>
                        <td><input type="text" id="report-query-brand" name="brandid"/></td>
	    			</tr>
                    <tr>
                        <td>品牌业务员:</td>
                        <td><input type="text" id="report-query-branduser" name="branduser"/></td>
                        <td colspan="3">
                            <a href="javaScript:void(0);" id="report-queay-salesBrandUserBrand" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="report-reload-salesBrandUserBrand" class="button-qr">重置</a>
                        </td>
                    </tr>
	    		</table>
	    	</form>
    	</div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-salesBrandUserBrand").serializeJSON();
    		$(function(){

                $("#report-autoExport-salesBrandUserBrandPage").click(function(){
                    var queryJSON = $("#report-query-form-salesBrandUserBrand").serializeJSON();
                    //获取排序规则
                    var objecr  = $("#report-datagrid-salesBrandUserBrand").datagrid("options");
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryJSON["sort"] = objecr.sortName;
                        queryJSON["order"] = objecr.sortOrder;
                    }
                    var queryParam = JSON.stringify(queryJSON);
                    var url = "report/sales/exportBaseSalesReportData.do?groupcols=branduser,brandid";
                    exportByAnalyse(queryParam,"分品牌业务员分品牌销售情况统计报表","report-datagrid-salesBrandUserBrand",url);
                });
    			var tableColumnListBrandJson = $("#report-datagrid-salesBrandUserBrand").createGridColumnLoad({
					frozenCol : [[
									{field:'idok',checkbox:true,isShow:true}
					]],
					commonCol : [[
						  {field:'branduser',title:'品牌业务员',width:70,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.brandusername;
				        	}
						  },
						  {field:'brandid',title:'商品品牌',width:60,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.brandname;
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
    			$("#report-datagrid-salesBrandUserBrand").datagrid({ 
					authority:tableColumnListBrandJson,
			 		frozenColumns: tableColumnListBrandJson.frozen,
					columns:tableColumnListBrandJson.common,
			 		method:'post',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		pageSize:100,
		  	 		showFooter: true,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
					toolbar:'#report-toolbar-salesBrandUserBrand',
		  	 		onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SR_footerobject = footerrows[0];
							baseSalesReportCountTotalAmount('brandusername',this);
						}
			 		},
					onCheckAll:function(){
						baseSalesReportCountTotalAmount('brandusername',this);
					},
					onUncheckAll:function(){
						baseSalesReportCountTotalAmount('brandusername',this);
					},
					onCheck:function(){
						baseSalesReportCountTotalAmount('brandusername',this);
					},
					onUncheck:function(){
						baseSalesReportCountTotalAmount('brandusername',this);
					}
				}).datagrid("columnMoving");
				
				$("#report-query-branduser").widget({
					referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
		    		width:225,
					singleSelect:true
				});
				
				$("#report-query-brand").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
		    		width:180,
					singleSelect:true
				});
				//回车事件
				controlQueryAndResetByKey("report-queay-salesBrandUserBrand","report-reload-salesBrandUserBrand");
				
				//查询
				$("#report-queay-salesBrandUserBrand").click(function(){
		      		var queryJSON = $("#report-query-form-salesBrandUserBrand").serializeJSON();
		      		$("#report-datagrid-salesBrandUserBrand").datagrid({
		      			url: 'report/sales/showBaseSalesReportData.do?groupcols=branduser,brandid',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-salesBrandUserBrand").click(function(){
					$("#report-query-branduser").widget("clear");
					$("#report-query-brand").widget("clear");
					$("#report-query-form-salesBrandUserBrand")[0].reset();
					var queryJSON = $("#report-query-form-salesBrandUserBrand").serializeJSON();
					$("#report-datagrid-salesBrandUserBrand").datagrid('loadData',{total:0,rows:[]});
				});

    		});

        </script>
  </body>
</html>
