<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分制单人销售情况统计报表</title>
    <%@include file="/include.jsp" %>  
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
  </head>
  
  <body>
    <table id="report-datagrid-salesAdduser"></table>
    <div id="report-toolbar-salesAdduser" style="padding: 0px">
        <div class="buttonBG">
            <security:authorize url="/report/sales/salesAdduserReportExport.do">
                <a href="javaScript:void(0);" id="report-autoExport-salesAdduser" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
            </security:authorize>
        </div>
   		<form action="" id="report-query-form-salesAdduser" method="post">
			<input type="hidden" name="datasqltype" value="adduserid"/>
			<table class="querytable">
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>制单人:</td>
    				<td><input id="report-query-adduserid" type="text" name="adduserid" style="width: 130px;"/></td>
    				<td>销售区域:</td>
    				<td><input id="report-query-salesarea" type="text" name="salesarea" style="width: 130px;"/></td>
    			</tr>
    			<tr>
    				<td>客户名称</td>
    				<td><input type="text" id="report-query-customerid" name="customerid" style="width: 225px;"/></td>
    				<td>品牌:</td>
    				<td><input type="text" id="report-query-brandid" name="brandid" style="width: 130px;"/></td>
    				<td>销售部门:</td>
    				<td><input id="report-query-salesdept" type="text" name="salesdept" style="width: 130px;"/></td>
    			</tr>
    			<tr>
    				<td colspan="4"></td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="report-queay-salesAdduser" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-salesAdduser" class="button-qr">重置</a>    					
    				</td>
    			</tr>
    		</table>
    	</form>
   	</div>
    <div id="report-dialog-salesAdduserDetail"></div>
   	<script type="text/javascript">
   		var SR_footerobject  = null;
   		$(function(){
   			var tableColumnListJson = $("#report-datagrid-salesAdduser").createGridColumnLoad({
				frozenCol : [[
								{field:'idok',checkbox:true,isShow:true}
			    			]],
				commonCol : [[
							  {field:'adduserid',title:'制单人',sortable:true,width:60,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.addusername;
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
   			$("#report-datagrid-salesAdduser").datagrid({ 
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
				toolbar:'#report-toolbar-salesAdduser',
				onDblClickRow:function(rowIndex, rowData){
                    var adduserid = rowData.adduserid;
                    var addusername = rowData.addusername;
                    var businessdate1 = $("#report-query-businessdate1").val();
                    var businessdate2 = $("#report-query-businessdate2").val();
                    var queryJSON = $("#report-query-form-salesAdduser").serializeJSON();
                    queryJSON['adduserid'] = adduserid;
                    queryJSON['addusername'] = addusername;
                    queryJSON['businessdate1'] = businessdate1;
                    queryJSON['businessdate2'] = businessdate2;

                    $('<div id="report-dialog-salesAdduserDetail1"></div>').appendTo('#report-dialog-salesAdduserDetail');
                    $("#report-dialog-salesAdduserDetail1").dialog({
						title:'按制单人:['+rowData.addusername+']统计',
			    		width:800,
			    		height:400,
			    		closed:true,
			    		modal:true,
			    		maximizable:true,
			    		cache:false,
			    		resizable:true,
			    		maximized:true,
					    href: 'report/sales/showSalesAdduserDetailPage.do?jsonstr='+JSON.stringify(queryJSON),
                        onClose:function(){
                            $('#report-dialog-salesAdduserDetail1').dialog("destroy");
                        }
					});
					$("#report-dialog-salesAdduserDetail1").dialog("open");
				},
	  	 		onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						SR_footerobject = footerrows[0];
						baseSalesReportCountTotalAmount('addusername',this);
					}
		 		},
				onCheckAll:function(){
					baseSalesReportCountTotalAmount('addusername',this);
				},
				onUncheckAll:function(){
					baseSalesReportCountTotalAmount('addusername',this);
				},
				onCheck:function(){
					baseSalesReportCountTotalAmount('addusername',this);
				},
				onUncheck:function(){
					baseSalesReportCountTotalAmount('addusername',this);
				}
			}).datagrid("columnMoving");
			$("#report-query-customerid").customerWidget({});
			$("#report-query-brandid").widget({
				referwid:'RL_T_BASE_GOODS_BRAND',
				width:130,
				singleSelect:false
			});
			$("#report-query-adduserid").widget({
				referwid:'RL_T_SYS_USER',
				width:130,
				singleSelect:false
			});
			$("#report-query-salesarea").widget({
				referwid:'RT_T_BASE_SALES_AREA',
				width:130,
				onlyLeafCheck:false,
				singleSelect:true
			});
			$("#report-query-salesdept").widget({
				referwid:'RL_T_BASE_DEPARTMENT_SELLER',
				width:130,
				singleSelect:false
			});
			
			//回车事件
			controlQueryAndResetByKey("report-queay-salesAdduser","report-reload-salesAdduser");
			
			//查询
			$("#report-queay-salesAdduser").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#report-query-form-salesAdduser").serializeJSON();
	      		$("#report-datagrid-salesAdduser").datagrid({
	      			url: 'report/sales/showBaseSalesReportData.do?groupcols=adduserid',
	      			pageNumber:1,
					queryParams:queryJSON
	      		});
			});
			//重置
			$("#report-reload-salesAdduser").click(function(){
				$("#report-query-customerid").customerWidget('clear');
				$("#report-query-brandid").widget('clear');
				$("#report-query-salesarea").widget('clear');
				$("#report-query-salesdept").widget('clear');
				$("#report-query-adduserid").widget('clear');
				$("#report-query-form-salesAdduser").form("reset");
				var queryJSON = $("#report-query-form-salesAdduser").serializeJSON();
	       		$("#report-datagrid-salesAdduser").datagrid('loadData',{total:0,rows:[]});
			});

			$("#report-autoExport-salesAdduser").click(function(){
				var queryJSON = $("#report-query-form-salesAdduser").serializeJSON();
				//获取排序规则
				var objecr  = $("#report-datagrid-salesAdduser").datagrid("options");
				if(null != objecr.sortName && null != objecr.sortOrder ){
					queryJSON["sort"] = objecr.sortName;
					queryJSON["order"] = objecr.sortOrder;
				}
				var queryParam = JSON.stringify(queryJSON);
				var url = 'report/sales/exportBaseSalesReportData.do?groupcols=adduserid';
				exportByAnalyse(queryParam,"分制单人销售情况统计报表","report-datagrid-salesAdduser",url);

			});
   		});
   	</script>
  </body>
</html>
