<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>按客户业务员分客户,分商品，分客户分商品统计页面</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>     
  </head>
  
  <body>
  	<form action="" id="report-query-form-salesuerWithdrawnDetail" method="post">
		<input type="hidden" name="salesuser" value="${salesuser}"/>
		<input type="hidden" name="businessdate1" value="${businessdate1}"/>
		<input type="hidden" name="businessdate2" value="${businessdate2}"/>
	</form>
  	<div id="report-tab-salesuerWithdrawnDetail" class="buttonBG">
		<security:authorize url="/report/finance/fundsReturnDataDetailExport.do">
			<a href="javaScript:void(0);" id="report-export-salesuerWithdrawnDetail" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
		</security:authorize>
		<security:authorize url="/report/finance/fundsReturnDataDetailPrint.do">
			<a href="javaScript:void(0);" id="report-print-salesuerWithdrawnDetail" class="easyui-linkbutton" iconCls="button-print" plain="true" title="打印">打印</a>
		</security:authorize>
	</div>
	<div id="tt" class="easyui-tabs" data-options="fit:true">
	    <div title="分客户统计" style="padding:2px;" onclick="getSelectTable(1)">
	       <table id="report-datagrid-salesuerWithdrawnDetail-customer" ></table>
	    </div>
	    <div title="分商品统计" style="padding:2px;">
	        <table id="report-datagrid-salesuerWithdrawnDetail-goods" onclick="getSelectTable(2)"></table>
	    </div>
	    <div title="分客户分商品统计" style="padding:2px;">
	      <table id="report-datagrid-salesuerWithdrawnDetail-customerandgoods" onclick="getSelectTable(3)"></table>
	    </div>
	    <div title="分品牌统计" style="padding:2px;">
	      <table id="report-datagrid-salesuerWithdrawnDetail-brand" onclick="getSelectTable(4)"></table>
	    </div>
        <div title="分公司统计" style="padding:2px;">
            <table id="report-datagrid-salesuerWithdrawnDetail-company" onclick="getSelectTable(5)"></table>
        </div>
	</div>
    	<script type="text/javascript">
			//记录选中页面的table和url，打印的时候用
			var printtable='report-datagrid-salesuerWithdrawnDetail-customer';
			var printurl='report/finance/printBaseFinanceDrawnData.do?groupcols=customerid';
    		$('#tt').tabs({
				tools:'#report-tab-salesuerWithdrawnDetail'
			});
    		var initQueryJSON = $("#report-query-form-salesuerWithdrawnDetail").serializeJSON();
    		$(function(){
                $('#tt').tabs({
                    border:false,
                    onSelect:function(title){
                        if(title=='分客户统计'){
                            printtable='report-datagrid-salesuerWithdrawnDetail-customer';
                            printurl='report/finance/printBaseFinanceDrawnData.do?groupcols=customerid';
                        }else if(title=='分商品统计'){
                            printtable='report-datagrid-salesuerWithdrawnDetail-goods';
                            printurl='report/finance/printBaseFinanceDrawnData.do?groupcols=goodsid';
                        }else if(title=='分客户分商品统计'){
                            printtable='report-datagrid-salesuerWithdrawnDetail-customerandgoods';
                            printurl='report/finance/printBaseFinanceDrawnData.do?groupcols=customerid,goodsid';
                        }else if(title=='分品牌统计'){
                            printtable='report-datagrid-salesuerWithdrawnDetail-brand';
                            printurl='report/finance/printBaseFinanceDrawnData.do?groupcols=brandid';
                        }else if(title=='分公司统计'){
                            printtable='report-datagrid-salesuerWithdrawnDetail-company';
                            printurl='report/finance/printCompanyWithdrawnList.do';
                        }
                    }
                });
                $("#report-print-salesuerWithdrawnDetail").click(function () {

                    var msg = "";
                    printByAnalyse("分客户业务员资金回笼表", printtable, printurl, msg);
                });
                var customerColumnListJson = $("#report-datagrid-salesuerWithdrawnDetail-customer").createGridColumnLoad({
                    frozenCol: [[]],
                    commonCol: [[
                        {field:'customerid',title:'客户编号',sortable:true,width:60},
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
                        {field:'salesdept',title:'所属部门',width:80,hidden:true,
                            formatter:function(value,rowData,rowIndex){
                                if(value!=null){
                                    return rowData.salesdeptname;
                                }
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

                var goodsColumnListJson = $("#report-datagrid-salesuerWithdrawnDetail-goods").createGridColumnLoad({
                    frozenCol: [[]],
                    commonCol: [[
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

                var customerandgoodsColumnListJson = $("#report-datagrid-salesuerWithdrawnDetail-customerandgoods").createGridColumnLoad({
                    frozenCol: [[]],
                    commonCol: [[
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
                        {field:'goodsid',title:'商品编码',width:60},
                        {field:'goodsname',title:'商品名称',sortable:true,width:250},
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
                

                var brandColumnListJson = $("#report-datagrid-salesuerWithdrawnDetail-brand").createGridColumnLoad({
                    frozenCol: [[]],
                    commonCol: [[
                        {field:'brandid',title:'品牌编码',width:60},
                        {field:'brandname',title:'品牌名称',width:210},
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

                var companyColumnListJson = $("#report-datagrid-salesuerWithdrawnDetail-company").createGridColumnLoad({
                    frozenCol: [[]],
                    commonCol: [[
                        {field:'branddept',title:'公司',width:80,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.branddeptname;
                            }
                        },
                        {field:'withdrawnamount',title:'回笼金额',align:'right',width:70,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        }
                        <c:if test="${map.costwriteoffamount == 'costwriteoffamount'}">
                        ,
                        {field:'costwriteoffamount',title:'回笼成本',align:'right',width:70,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        }
                        </c:if>
                        <c:if test="${map.writeoffmarginamount == 'writeoffmarginamount'}">
                        ,
                        {field:'writeoffmarginamount',title:'回笼毛利额',align:'right',width:70,isShow:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        }
                        </c:if>
                        <c:if test="${map.writeoffrate == 'writeoffrate'}">
                        ,
                        {field:'writeoffrate',title:'回笼毛利率',align:'right',width:70,isShow:true,
                            formatter:function(value,rowData,rowIndex){
                                if(null != value && "" != value){
                                    return formatterMoney(value)+"%";
                                }
                            }
                        }
                        </c:if>
                    ]]
                });

    			$("#report-datagrid-salesuerWithdrawnDetail-customer").datagrid({
                    authority:customerColumnListJson,
                    frozenColumns: customerColumnListJson.frozen,
                    columns:customerColumnListJson.common,
			 		method:'post',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
		  	 		pageSize:100,
					toolbar:'#report-tab-salesuerWithdrawnDetail',
					url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=customerid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
				$("#report-datagrid-salesuerWithdrawnDetail-goods").datagrid({
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
				$("#report-datagrid-salesuerWithdrawnDetail-customerandgoods").datagrid({
                    authority:customerandgoodsColumnListJson,
                    frozenColumns: customerandgoodsColumnListJson.frozen,
                    columns:customerandgoodsColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
		  	 		pageSize:100,
					url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=customerid,goodsid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
				$("#report-datagrid-salesuerWithdrawnDetail-brand").datagrid({
                    authority:brandColumnListJson,
                    frozenColumns: brandColumnListJson.frozen,
                    columns:brandColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
		  	 		pageSize:100,
					url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=brandid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
				$("#report-datagrid-salesuerWithdrawnDetail-company").datagrid({
                    authority:companyColumnListJson,
                    frozenColumns: companyColumnListJson.frozen,
                    columns:companyColumnListJson.common,
                    method:'post',
                    title:'',
                    fit:true,
                    rownumbers:true,
                    pagination:true,
                    showFooter: true,
                    singleSelect:true,
                    pageSize:100,
                    url: 'report/finance/getCompanyWithdrawnList.do',
                    queryParams:initQueryJSON
                }).datagrid("columnMoving");

				$("#report-export-salesuerWithdrawnDetail").Excel('export',{
					queryForm: "#report-query-form-salesuerWithdrawnDetail", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'按客户业务员：[${salesusername}]统计表',
			 		url:'report/finance/exportFinanceWithdrawnDetailReportData.do?groupcols=customerid;goodsid;customerid,goodsid;brandid;company'
				});
    		});
    	</script>
  </body>
</html>
