<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分供应商资金应收款情况表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-supplierReceipt"></table>
    	<div id="report-toolbar-supplierReceipt" style="padding: 0px">
    		<form action="" id="report-query-form-supplierReceipt" method="post">
                <div class="buttonBG" style="height: 26px">
                    <security:authorize url="/report/finance/supplierReceiptExport.do">
                        <a href="javaScript:void(0);" id="report-buttons-supplierReceiptPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                    </security:authorize>
                </div>
    		<table class="querytable">
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>品牌部门:</td>
    				<td><input type="text" id="report-query-branddept" name="branddept"/></td>
    			</tr>
    			<tr>
    				<td>供应商:</td>
    				<td><input type="text" id="report-query-supplierid" name="supplierid"/>
                        <input type="hidden" name="groupcols" value="supplierid"/>
                    </td>
                    <td></td>
    				<td>
    					<a href="javaScript:void(0);" id="report-queay-supplierReceipt" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-supplierReceipt" class="button-qr">重置</a>
    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-supplier-detail-dialog"></div>
    	<script type="text/javascript">
            var SRL_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-supplierReceipt").serializeJSON();

    		$(function(){
                //全局导出
                $("#report-buttons-supplierReceiptPage").click(function(){
                    var queryJSON = $("#report-query-form-supplierReceipt").serializeJSON();
                    //获取排序规则
                    var objecr  = $("#report-datagrid-supplierReceipt").datagrid("options");
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryJSON["sort"] = objecr.sortName;
                        queryJSON["order"] = objecr.sortOrder;
                    }
                    var queryParam = JSON.stringify(queryJSON);
                    var url = "report/finance/exportBaseFinanceReceiptData.do";
                    exportByAnalyse(queryParam,"分供应商资金应收款情况表","report-datagrid-supplierReceipt",url);
                });

                var suppliertableColumnListJson = $("#report-datagrid-supplierReceipt").createGridColumnLoad({
                    frozenCol : [[
                        {field:'idok',checkbox:true,isShow:true}
                    ]],
                    commonCol : [[
                        {field:'suppliername',title:'供应商编码',width:70,
                            formatter:function(value,rowData,rowIndex){
                                if("0" != rowData.supplierid){
                                    return rowData.supplierid;
                                }else{
                                    return "";
                                }
                            }
                        },
                        {field:'supplierid',title:'供应商名称',width:260,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.suppliername;
                            }
                        },
                        {field:'branddept',title:'品牌部门',width:60,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.branddeptname;
                            }
                        },
                        {field:'allunwithdrawnamount',title:'应收款总额',align:'right',resizable:true,isShow:true,
                            formatter:function(value,rowData,rowIndex){
                                if("QC" != rowData.supplierid && value!=0){
                                    return '<a href="javascript:showDetail(\''+rowData.supplierid+'\',0);" style="text-decoration:none">'+formatterMoney(value)+'</a>';
                                }else{
                                    return formatterMoney(value);
                                }
                            }
                        },
                        {field:'unauditamount',title:'未验收应收款',align:'right',resizable:true,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        {field:'auditamount',title:'已验收应收款',align:'right',resizable:true,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        {field:'rejectamount',title:'退货应收款',align:'right',resizable:true,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        {field:'allpushbalanceamount',title:'冲差应收款',align:'right',resizable:true,isShow:true,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        }
                    ]]
                });

    			$("#report-datagrid-supplierReceipt").datagrid({
                    authority:suppliertableColumnListJson,
                    frozenColumns: suppliertableColumnListJson.frozen,
                    columns:suppliertableColumnListJson.common,
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
					toolbar:'#report-toolbar-supplierReceipt',
                    onLoadSuccess:function(){
                        var footerrows = $(this).datagrid('getFooterRows');
                        if(null!=footerrows && footerrows.length>0){
                            SRL_footerobject = footerrows[0];
                            SRL_countTotalAmount();
                        }
                    },
                    onCheckAll:function(){
                        SRL_countTotalAmount();
                    },
                    onUncheckAll:function(){
                        SRL_countTotalAmount();
                    },
                    onCheck:function(){
                        SRL_countTotalAmount();
                    },
                    onUncheck:function(){
                        SRL_countTotalAmount();
                    }
				}).datagrid("columnMoving");
				$("#report-query-supplierid").widget({
					referwid:'RL_T_BASE_BUY_SUPPLIER',
		    		width:225,
					onlyLeafCheck:false,
					singleSelect:false
				});
				
				$("#report-query-branddept").widget({
					referwid:'RL_T_BASE_DEPARTMENT_BUYER',
		    		width:205,
					onlyLeafCheck:false,
					singleSelect:true
				});
				
				//回车事件
				controlQueryAndResetByKey("report-queay-supplierReceipt","report-reload-supplierReceipt");
				
				//查询
				$("#report-queay-supplierReceipt").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-supplierReceipt").serializeJSON();
		      		$("#report-datagrid-supplierReceipt").datagrid({
                        url:'report/finance/showBaseFinanceReceiptData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-supplierReceipt").click(function(){
					$("#report-query-supplierid").widget("clear");
					$("#report-query-branddept").widget('clear');
					$("#report-query-form-supplierReceipt")[0].reset();
		       		$("#report-datagrid-supplierReceipt").datagrid('loadData',{total:0,rows:[]});
				});
    		});

            function SRL_countTotalAmount(){
                var rows =  $("#report-datagrid-supplierReceipt").datagrid('getChecked');
                var allunwithdrawnamount = 0;
                var unauditamount = 0;
                var auditamount = 0;
                var rejectamount = 0;
                var allpushbalanceamount = 0;
                for(var i=0;i<rows.length;i++){
                    allunwithdrawnamount = Number(allunwithdrawnamount)+Number(rows[i].allunwithdrawnamount == undefined ? 0 : rows[i].allunwithdrawnamount);
                    unauditamount = Number(unauditamount)+Number(rows[i].unauditamount == undefined ? 0 : rows[i].unauditamount);
                    auditamount = Number(auditamount)+Number(rows[i].auditamount == undefined ? 0 : rows[i].auditamount);
                    rejectamount = Number(rejectamount)+Number(rows[i].rejectamount == undefined ? 0 : rows[i].rejectamount);
                    allpushbalanceamount = Number(allpushbalanceamount)+Number(rows[i].allpushbalanceamount == undefined ? 0 : rows[i].allpushbalanceamount);
                }
                var foot=[{suppliername:'选中合计',allunwithdrawnamount:allunwithdrawnamount,unauditamount:unauditamount,auditamount:auditamount,rejectamount:rejectamount,allpushbalanceamount:allpushbalanceamount}];
                if(null!=SRL_footerobject){
                    foot.push(SRL_footerobject);
                }
                $("#report-datagrid-supplierReceipt").datagrid("reloadFooter",foot);
            }

    		function showDetail(supplierid,type){
    			var businessdate1 = $("#report-query-businessdate1").val();
    			var businessdate2 = $("#report-query-businessdate2").val();
    			var url = 'report/finance/showFundsBrandListPage.do?supplierid='+supplierid+'&type='+type+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2;
    			$("#report-supplier-detail-dialog").dialog({
				    title: '分供应商销售回单列表',  
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
