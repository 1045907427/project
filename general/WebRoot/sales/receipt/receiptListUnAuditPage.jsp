<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售回单列表页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center'">
    		<div id="sales-queryDiv-receiptListPage" style="padding:0px;height:auto">
                <div class="buttonBG" style="height: 26px">
                <a href="javaScript:void(0);" id="sales-export-receiptListPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </div>
    			<form id="sales-queryForm-receiptListPage">
    				<table class="querytable">
		    			<tr>
		    				<td>编&nbsp;&nbsp;&nbsp;号:</td>
		    				<td><input type="text" name="id" class="len120" /> </td>
		    				<td>客户:</td>
		    				<td><input type="text" id="sales-customer-receiptListPage" name="customerid" style="width: 120px;"/></td>
                            <td>客户业务员：</td>
                            <td><input type="text" id="sales-salesuser-receiptListPage" name="salesuser" /></td>

		    			</tr>
		    			<tr>
		    				<td>销售订单编号:</td>
		    				<td><input type="text" name="saleorderid" class="len120" /> </td>
                            <td>总店:</td>
                            <td><input type="text" id="sales-pcustomer-receiptListPage" name="pcustomerid" /></td>
		    				<td>销售内勤：</td>
		    				<td><input type="text" id="sales-indooruserid-receiptListPage" name="indooruserid" /></td>
		    			</tr>
		    			<tr>
		    				<td>未验收天数>=</td>
		    				<td><input type="text" name="uncheckday" class="len120" value="3"/></td>
                            <td colspan="2"></td>
		    				<td colspan="2" style="padding-left: 50px">
		    					<input type="hidden" name="status" value="2"/>
		    					<a href="javascript:;" id="sales-queay-receiptListPage" class="button-qr">查询</a>
								<a href="javaScript:;" id="sales-resetQueay-receiptListPage" class="button-qr">重置</a>
                            </td>
		    			</tr>
		    		</table>
    			</form>
    		</div>
    		<table id="sales-datagrid-receiptListPage" data-options="border:false"></table>
    	</div>
    </div>
    <script type="text/javascript">
    	var initQueryJSON = $("#sales-queryForm-receiptListPage").serializeJSON();
    	$(function(){
//            var idsarr=new Array();
//            $("#sales-export-receiptListPage").click(function(){
//                var rows = $("#sales-datagrid-receiptListPage").datagrid('getChecked');
//                if(rows.length > 0) {
//                    for (var i = 0; i < rows.length; i++) {
//                        idsarr.push(rows[i].id);
//                    }
//                }
//            });
            $("#sales-export-receiptListPage").Excel('export',{
                queryForm: "#sales-queryForm-receiptListPage",//查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                datagrid:"#sales-datagrid-receiptListPage",//导出选中记录，没有选中的记录则导出所有
                type:'exportUserdefined',
                //fieldParam:{ids:idsarr},
                name:'多日未验收回单报表',
                url:'sales/exportReceiptList.do'
            });

    		$("#sales-customer-receiptListPage").customerWidget({ //客户参照窗口
    		});
    		$("#sales-salesuser-receiptListPage").widget({
    			referwid:'RL_T_BASE_PERSONNEL_SELLER',
	   			singleSelect:true,
	   			width:'120'
    		});
    		$("#sales-pcustomer-receiptListPage").widget({
    			referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
	   		singleSelect:true,
	   		width:'120'
    		});
    		$("#sales-indooruserid-receiptListPage").widget({
    			referwid:'RL_T_BASE_PERSONNEL_INDOORSTAFF',
    			width:120,
    			singleSelect:true,
    			initSelectNull:true
    		});
    		//回车事件
			controlQueryAndResetByKey("sales-queay-receiptListPage","sales-resetQueay-receiptListPage");
    		
			$("#sales-queay-receiptListPage").click(function(){
	       		var queryJSON = $("#sales-queryForm-receiptListPage").serializeJSON();
	       		$("#sales-datagrid-receiptListPage").datagrid('load', queryJSON);
			});
			$("#sales-resetQueay-receiptListPage").click(function(){
				$("#sales-customer-receiptListPage").customerWidget("clear");
				$("#sales-salesuser-receiptListPage").widget("clear");
				$("#sales-indooruserid-receiptListPage").widget("clear");
				$("#sales-pcustomer-receiptListPage").widget('clear');
				$("#sales-queryForm-receiptListPage").form("reset");
				var queryJSON = $("#sales-queryForm-receiptListPage").serializeJSON();
				$("#sales-datagrid-receiptListPage").datagrid('load', queryJSON);
			});
			var orderListJson = $("#sales-datagrid-receiptListPage").createGridColumnLoad({
				name :'t_sales_receipt',
				frozenCol : [[
							  
			    			]],
				commonCol : [[{field:'id',title:'编号',width:120, align: 'left',sortable:true},
							  {field:'saleorderid',title:'销售订单编号',width:120, align: 'left',sortable:true},
							  {field:'businessdate',title:'业务日期',width:80,align:'left',sortable:true},
							  {field:'customerid',title:'客户编码',width:60,align:'left',sortable:true},
							  {field:'customername',title:'客户名称',width:100,align:'left',isShow:true},
							  {field:'headcustomerid',title:'总店',width:60,align:'left',sortable:true,isShow:true,
							  		formatter:function(value,row,index){
						        		return row.headcustomername;
							        }
							  },
							  {field:'handlerid',title:'对方经手人',width:70,align:'left'},
							  {field:'salesdept',title:'销售部门',width:80,align:'left',sortable:true,
								formatter:function(value,row,index){
									return row.salesdeptname;
								}
							  },
					          {field:'salesuser',title:'客户业务员',width:80,align:'left',sortable:true,
								formatter:function(value,row,index){
									return row.salesusername;
								}
							  },
							  {field:'totaltaxamount',title:'发货金额',width:80,align:'right',isShow:true,
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'totalreceipttaxamount',title:'应收金额',width:80,align:'right',isShow:true,
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'duefromdate',title:'应收日期',width:80,align:'left',sortable:true},
							  {field:'canceldate',title:'核销日期',width:80,align:'left',sortable:true},
							  {field:'status',title:'状态',width:60,align:'left',sortable:true,
							  		formatter:function(value,row,index){
						        		return getSysCodeName('status', value);
							        }
							  },
							  {field:'isinvoice',title:'发票状态',width:70,align:'left',sortable:true,
							  		formatter:function(value,row,index){
						        		if(value == "0"){
						        			return "未开票";
						        		}
						        		else if(value == "1"){
						        			return "已开票";
						        		}
						        		else if(value == "2"){
						        			return "已核销";
						        		}
						        		else if(value == "3"){
						        			return "未开票";
						        		}else if(value == "4"){
						        			return "开票中";
						        		}else if(value == "5"){
						        			return "部分核销";
						        		}
							        }
							  },
							  {field:'source',title:'来源类型',width:80,align:'left',hidden:true,sortable:true,
							  	formatter:function(value,row,index){
							  		if(value == "1"){
							  			return "发货单";
							  		}
							  		else{
							  			return "无";
							  		}
							    }
							  },
							  {field:'billno',title:'来源编号',width:120,align:'left',sortable:true},
							  {field:'indooruserid',title:'销售内勤',width:60,sortable:true,
							  	formatter:function(value,rowData,index){
					        		return rowData.indoorusername;
						        }
							  },
							  {field:'addusername',title:'制单人',width:80,sortable:true},
							  {field:'addtime',title:'制单时间',width:80,sortable:true,hidden:true},
							  {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
							  {field:'audittime',title:'审核时间',width:80,sortable:true,hidden:true},
							  {field:'remark',title:'备注',width:100,align:'left'}
				              ]]
			});
			$("#sales-datagrid-receiptListPage").datagrid({ 
		 		authority:orderListJson,
		 		frozenColumns: [[{field:'receiptcheck',checkbox:true}]],
				columns:orderListJson.common,
		 		fit:true,
		 		title:"",
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		singleSelect:false,
		 		checkOnSelect:true,
				selectOnCheck:true,
    			showFooter: true,
				sortName:'id',
				sortOrder:'desc',
				//fitColumns:true,
				url: 'sales/getReceiptList.do',
				queryParams:initQueryJSON,
				toolbar:'#sales-queryDiv-receiptListPage',
			    onDblClickRow:function(index, data){
					if (top.$('#tt').tabs('exists','销售发货回单查看')){
						top.closeTab('销售发货回单查看');
					}
					top.addTab('sales/receiptUnCheckPage.do?id='+data.id, '销售发货回单查看');
		    	},
		    	onCheck: function(rowIndex,rowData){
		    		var rows = $("#sales-datagrid-receiptListPage").datagrid("getChecked");
		    		countTotal(rows);
		    	},
		    	onUncheck: function(rowIndex,rowData){
		    		var rows = $("#sales-datagrid-receiptListPage").datagrid("getChecked");
		    		countTotal(rows);
		    	},
		    	onCheckAll: function(rows){
		    		var rows = $("#sales-datagrid-receiptListPage").datagrid("getChecked");
		    		countTotal(rows);
		    	},
		    	onUncheckAll: function(rows){
		    		$("#sales-datagrid-receiptListPage").datagrid('reloadFooter',[{id:'合计', totaltaxamount: 0, totalreceipttaxamount: 0}]);
		    	}
			}).datagrid("columnMoving").datagrid('reloadFooter',[{id:'合计', totaltaxamount: 0, totalreceipttaxamount: 0}]);

    	});
    	function countTotal(rows){
    		var a1 = 0;
    		var a2 = 0;
    		if(rows != null){
    			for(var i=0;i<rows.length;i++){
    				a1 += parseFloat(rows[i].totaltaxamount == undefined ? 0 : rows[i].totaltaxamount);
    				a2 += parseFloat(rows[i].totalreceipttaxamount == undefined ? 0 : rows[i].totalreceipttaxamount);
    			}
    		}
    		$("#sales-datagrid-receiptListPage").datagrid('reloadFooter',[{id:'合计', totaltaxamount: a1, totalreceipttaxamount: a2}]);
    	}
    </script>
  </body>
</html>
