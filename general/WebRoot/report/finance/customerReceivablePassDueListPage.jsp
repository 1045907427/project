<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户应收款超账期统计报表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-receivablePastDue"></table>
    	<div id="report-toolbar-receivablePastDue" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/receivablePastDueExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-receivablePastDuePage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
                <a href="javaScript:void(0);" id="report-setdays-receivablePastDue" class="easyui-linkbutton" iconCls="button-intervalSet" plain="true">设置区间</a>

            </div>
    		<form action="" id="report-query-form-receivablePastDue" method="post">
    		<table class="querytable">

    			<tr>
    				<td>客户名称:</td>
    				<td><input type="text" id="report-query-customerid" name="customerid"/></td>

    				<td>销售区域:</td>
    				<td><input type="text" id="report-query-salesarea" name="salesarea" style="width: 180px;"/></td>
    			</tr>
    			<tr>
    				<td>总店名称:</td>
	    			<td><input type="text" id="report-query-pcustomerid" name="pcustomerid" style="width: 210px;"/></td>
                    <td>客户业务员:</td>
                    <td><input type="text" id="report-query-salesuser" name="salesuser"/></td>
                </tr>
    			<tr>
                    <td>是否只显示超账:</td>
                    <td>
                        <select id="report-select-ispastdue" name="ispastdue" style="width: 40px">
                            <option value="0" selected="selected">否</option>
                            <option value="1">是</option>
                        </select>
                    </td>
    				<td colspan="2"></td>
    				<td colspan="2">
    					<input id="report-query-groupcols" type="hidden" name="groupcols" value="customerid"/>
    					<a href="javaScript:void(0);" id="report-queay-receivablePastDue" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-receivablePastDue" class="button-qr">重置</a>
						</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-paymentdaysSet-dialog"></div>
    	<div id="report-receivablePastDue-detail-dialog"></div>
    	<div id="report-customerReceivablePastDue-detail-dialog"></div>
    	<script type="text/javascript">
		var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-receivablePastDue").serializeJSON();
    		$(function(){
    			var tableColumnListJson = $("#report-datagrid-receivablePastDue").createGridColumnLoad({
    				frozenCol : [[
									{field:'idok',checkbox:true,isShow:true}
				    			]],
					commonCol : [[
						{field:'customerid',title:'客户编号',width:60},
						  {field:'customername',title:'客户名称',width:210},
						  {field:'salesuser',title:'客户业务员',sortable:true,width:80,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.salesusername;
				        	}
						  },
						  {field:'salesarea',title:'销售区域',sortable:true,width:80,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.salesareaname;
				        	}
						  },
						  {field:'saleamount',title:'应收款',align:'right',resizable:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'unpassamount',title:'正常期金额',align:'right',resizable:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'totalpassamount',title:'超账期金额',align:'right',resizable:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
			        			return formatterMoney(value);
				        	},
				        	styler:function(value,rowData,rowIndex){
				        		return 'color:blue';
				        	}
						  },
						  <c:forEach items="${list }" var="list">
						  {field:'passamount${list.seq}',title:'${list.detail}',align:'right',resizable:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
			        			return formatterMoney(value);
				        	}
						  },
	  					  </c:forEach>
	  					  {field:'returnamount',title:'退货金额',align:'right',resizable:true,sortable:true,width:100,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'returnrate',title:'退货率',align:'right',width:60,sortable:true,width:100,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
						  		if(value!=null && value!=""){
				        			return formatterMoney(value)+"%";
				        		}
				        	}
						  },
						  {field:'pushamount',title:'冲差金额',align:'right',resizable:true,sortable:true,width:100,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
					]]
    			});
    			$("#report-datagrid-receivablePastDue").datagrid({ 
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
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
					toolbar:'#report-toolbar-receivablePastDue',
					onDblClickRow:function(rowIndex, rowData){
						var customerid = rowData.customerid;
						var customername = rowData.customername;
						var ispastdue = $("#report-select-ispastdue").val();
    					var url = 'report/finance/showCustomerReceivablePastDueDetailPage.do?customerid='+customerid+'&customername='+customername+'&ispastdue='+ispastdue;
						$("#report-customerReceivablePastDue-detail-dialog").dialog({
							title:'按客户:['+rowData.customername+']统计',
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
						$("#report-customerReceivablePastDue-detail-dialog").dialog("open");
					},
					onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SR_footerobject = footerrows[0];
							countTotalAmount();
						}
			 		},
					onCheckAll:function(){
						countTotalAmount();
					},
					onUncheckAll:function(){
						countTotalAmount();
					},
					onCheck:function(){
						countTotalAmount();
					},
					onUncheck:function(){
						countTotalAmount();
					}
				}).datagrid("columnMoving");
				$("#report-query-customerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER',
		    		width:210,
					singleSelect:false
					
				});
				//总店
				$("#report-query-pcustomerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
		    		width:210,
					singleSelect:false
				});
				//销售区域
				$("#report-query-salesarea").widget({
					referwid:'RT_T_BASE_SALES_AREA',
		    		width:180,
		    		onlyLeafCheck:false,
					singleSelect:false
				});
				//客户业务员
				$("#report-query-salesuser").widget({
					referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
		    		width:180,
					singleSelect:false
				});
				//回车事件
				controlQueryAndResetByKey("report-queay-receivablePastDue","report-reload-receivablePastDue");
				
				//查询
				$("#report-queay-receivablePastDue").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-receivablePastDue").serializeJSON();
		      		$("#report-datagrid-receivablePastDue").datagrid({
		      			url: 'report/finance/showCustomerReceivablePastDueListData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-receivablePastDue").click(function(){
					$("#report-query-customerid").widget("clear");
					$("#report-query-pcustomerid").widget("clear");
					$("#report-query-salesarea").widget("clear");
					$("#report-query-salesuser").widget("clear");
					$("#report-query-form-receivablePastDue")[0].reset();
		       		$("#report-datagrid-receivablePastDue").datagrid('loadData',{total:0,rows:[],footer:[]});
				});
				
				$("#report-buttons-receivablePastDuePage").Excel('export',{
					queryForm: "#report-query-form-receivablePastDue", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'客户应收款超账期统计报表',
			 		url:'report/finance/exportBaseReceivablePastDueData.do'
				});
				
				//设置超账期区间
				$("#report-setdays-receivablePastDue").click(function(){
					$("#report-paymentdaysSet-dialog").dialog({
					    title: '超账期区间设置',  
					    width: 400,  
					    height: 400,  
					    closed: false,  
					    cache: false,  
					    modal: true,
					    href: 'report/paymentdays/showPaymetdaysSetPage.do'
					});
				});
    		});
    		function showDetail(customerid,pastseq,iswrite){
    			if(pastseq==null){
    				pastseq = "";
    			}
    			var url = 'report/finance/showCustomerPastDueListPage.do?customerid='+customerid+'&seq='+pastseq+"&iswrite="+iswrite;
    			$("#report-receivablePastDue-detail-dialog").dialog({
				    title: '超账期销售发货回单列表',  
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
    		function countTotalAmount(){
    			var rows =  $("#report-datagrid-receivablePastDue").datagrid('getChecked');
    			var saleamount = 0;
        		var unpassamount = 0;
        		var totalpassamount=0;
        		var returnamount = 0;
        		var pushamount = 0;
        		<c:forEach items="${list }" var="list">
				  var passamount${list.seq} = 0;
				</c:forEach>
        		for(var i=0;i<rows.length;i++){
        			saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
        			unpassamount = Number(unpassamount)+Number(rows[i].unpassamount == undefined ? 0 : rows[i].unpassamount);
        			totalpassamount = Number(totalpassamount)+Number(rows[i].totalpassamount == undefined ? 0 : rows[i].totalpassamount);

            		<c:forEach items="${list }" var="list">
            			passamount${list.seq} = Number(passamount${list.seq})+Number(rows[i].passamount${list.seq} == undefined ? 0 : rows[i].passamount${list.seq});
    				</c:forEach>

    				returnamount = Number(returnamount)+Number(rows[i].returnamount == undefined ? 0 : rows[i].returnamount);
        			pushamount = Number(pushamount)+Number(rows[i].pushamount == undefined ? 0 : rows[i].pushamount);
        		}
        		var foot=[{customername:'选中合计',bankname:'',saleamount:saleamount,unpassamount:unpassamount,totalpassamount:totalpassamount
        			<c:forEach items="${list }" var="list">
    					,passamount${list.seq}:passamount${list.seq}
    				</c:forEach>
    				,returnamount:returnamount,pushamount:pushamount
            			}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-receivablePastDue").datagrid("reloadFooter",foot);
    		}
    	</script>
  </body>
</html>
