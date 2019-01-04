<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>应收交接单新增页面</title>
	<%@include file="/include.jsp"%>
  </head>
  
  <body>
    <table id="account-datagrid-receipthand"></table>
    <div id="account-toolbar-receipthand">
    	<form action="" id="account-form-receipthand">
    		<table cellpadding="0" cellspacing="0" border="0">
    			<tr>
    				<td>回单日期：</td>
    				<td><input type="text" name="businessdate1" style="width:80px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/> 
    					到 <input type="text" name="businessdate2" style="width:80px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
    				<td>回单编号：</td>
    				<td><input type="text" name="id" style="width: 130px;"/>
    					<input type="hidden" name="ishand" value="0"/>
    				</td>
    				<td>订单编号：</td>
    				<td><input type="text" name="saleorderid" style="width: 130px;"/></td>
    			</tr>
    			<tr>
    				<td>客户名称：</td>
    				<td><input type="text" id="account-customerid-receipthand" name="customerid" style="width: 180px;"/></td>
    				<td>销售区域：</td>
    				<td><input type="text" id="account-salesarea-receipthand" name="salesarea"/></td>
    				<td>总店：</td>
    				<td><input type="text" id="account-pcustomerid-receipthand" name="pcustomerid"/></td>
    			</tr>
    			<tr>
    				<td>客户业务员：</td>
    				<td><input type="text" id="account-salesuser-receipthand" name="salesuser"/></td>
    				<td>收款人：</td>
    				<td><input type="text" id="account-payee-receipthand" name="payeeid"/></td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="account-query-receipthand" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="查询">查询</a>
						<a href="javaScript:void(0);" id="account-reload-receipthand" class="easyui-linkbutton" iconCls="icon-reload" plain="true" title="重置">重置</a>
						<a href="javaScript:void(0);" id="account-add-receipthand" class="easyui-linkbutton" iconCls="button-add" title="生成交接单">生成交接单</a>
    				</td>
    			</tr>
    		</table>
    	</form>
    </div>
    <script type="text/javascript">
    	var RH_footerobject = null;
    	$(function(){
			var receiptHandJson = $("#account-datagrid-receipthand").createGridColumnLoad({
				frozenCol : [[{field:'idok',checkbox:true,isShow:true}]],
				commonCol : [[
					  {field:'id',title:'编号',width:130,sortable:true},
					  {field:'saleorderid',title:'订单编号',width:130,sortable:true},
					  {field:'businessdate',title:'业务日期',width:80,sortable:true},
					  {field:'customerid',title:'客户编码',width:60,sortable:true},
					  {field:'customername',title:'客户名称',width:150,isShow:true},
					  {field:'salesdept',title:'销售部门',width:80,sortable:true,hidden:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.salesdeptname;
			        	}
					  },
					  {field:'salesuser',title:'客户业务员',width:70,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.salesusername;
			        	}
					  },
					  {field:'payeeid',title:'收款人',width:60,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.payeename;
			        	}
					  },
					  {field:'totalreceipttaxamount',title:'应收金额',width:80,align:'right',
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
					  },
					  {field:'billno',title:'来源编号',width:80,sortable:true},
					  {field:'status',title:'状态',width:60,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return getSysCodeName("status",value);
			        	}
					  },
					  {field:'indooruserid',title:'销售内勤',width:60,sortable:true,
					  	formatter:function(value,rowData,index){
			        		return rowData.indoorusername;
				        }
					  },
					  {field:'duefromdate',title:'应收日期',width:80,hidden:true,sortable:true},
					  {field:'stopusername',title:'中止人',width:80,hidden:true,sortable:true},
					  {field:'stoptime',title:'中止时间',width:80,hidden:true,sortable:true},
					  {field:'remark',title:'备注',width:80,sortable:true},
					  {field:'addusername',title:'制单人',width:60,sortable:true},
					  {field:'addtime',title:'制单时间',width:120,sortable:true}
		           ]]
			});
			//销售区域
			$("#account-salesarea-receipthand").widget({
				referwid:'RT_T_BASE_SALES_AREA',
				width:130,
    			singleSelect:false,
    			onlyLeafCheck:false
			});
			$("#account-datagrid-receipthand").datagrid({ 
		 		authority:receiptHandJson,
		 		frozenColumns: receiptHandJson.frozen,
				columns:receiptHandJson.common,
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		sortName:'businessdate,id',
		 		sortOrder:'asc',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
		 		pageSize:500,
				pageList:[20,100,200,500,1000],
				toolbar:'#account-toolbar-receipthand',
				onDblClickRow:function(rowIndex, rowData){
					top.addOrUpdateTab('sales/receiptPage.do?type=edit&id='+rowData.id, '销售发货回单查看');
				},
				onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						RH_footerobject = footerrows[0];
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
			$("#account-customerid-receipthand").customerWidget({
				isdatasql:false
			});
			//总店
			$("#account-pcustomerid-receipthand").widget({
				referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
				width:130,
    			singleSelect:true
			});
			//客户业务员
			$("#account-salesuser-receipthand").widget({
				referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
				width:180,
    			singleSelect:true
			});
			//收款人
			$("#account-payee-receipthand").widget({
				referwid:'RL_T_BASE_CUSTOMER_PAYEE',
				width:130,
    			singleSelect:true
			});
			
			//查询
			$("#account-query-receipthand").click(function(){
				var queryJSON = $("#account-form-receipthand").serializeJSON();
				$("#account-datagrid-receipthand").datagrid({
		       		url: 'sales/getReceiptListForReceiptHand.do',
	      			pageNumber:1,
					queryParams:queryJSON
		       	}).datagrid("columnMoving");
			});
			//重置
			$("#account-reload-receipthand").click(function(){
				$("#account-customerid-receipthand").customerWidget('clear');
				$("#account-salesarea-receipthand").widget('clear');
				$("#account-pcustomerid-receipthand").widget('clear');
				$("#account-salesuser-receipthand").widget('clear');
				$("#account-payee-receipthand").widget('clear');
				$("#account-datagrid-receipthand").datagrid('clearChecked');
				$("#account-datagrid-receipthand").datagrid('clearSelections');
				$('#account-form-receipthand')[0].reset();
				$("#account-datagrid-receipthand").datagrid('loadData',{total:0,rows:[],footer:[]});
			});
			//生成交接单
			$("#account-add-receipthand").click(function(){
				$.messager.confirm("提醒","是否生成新的交接单？",function(r){
					if(r){
			    		var rowArr = $("#account-datagrid-receipthand").datagrid("getChecked");
			    		var ids = null;
			    		if(rowArr.length==0){
			    			$.messager.alert("提醒","请选择数据");
			    			return false;
			    		}
						for(var i=0;i<rowArr.length;i++){
							if(ids==null){
								ids = rowArr[i].id;
							}else{
								ids +="," + rowArr[i].id;
							}
						}
						loading("生成中..");
						$.ajax({
							url:'account/receipthand/addReceiptHand.do',
							dataType:'json',
							type:'post',
							data:{ids:ids},
							success:function(json){
								loaded();
								if(json.flag){
				            		$.messager.alert("提醒","生成成功");
									var queryJSON = $("#account-form-receipthand").serializeJSON();
									$("#account-datagrid-receipthand").datagrid({
							       		url: 'sales/getReceiptListForReceiptHand.do',
						      			pageNumber:1,
										queryParams:queryJSON
							       	}).datagrid("columnMoving");
				            		var title = parent.getNowTabTitle();
				            		top.addOrUpdateTab('account/receipthand/showReceiptHandPage.do?id='+ json.id+'&type=edit', "交接单查看");
								}else{
									$.messager.alert("提醒","生成失败<br/>");
								}
							},
							error:function(){
								loaded();
							}
						});
					}
				});
			});
    	});
    	
    	function countTotalAmount(){
    		var rows =  $("#account-datagrid-receipthand").datagrid('getChecked');
    		var totalreceipttaxamount = 0;
    		for(var i=0;i<rows.length;i++){
    			totalreceipttaxamount = Number(totalreceipttaxamount)+Number(rows[i].totalreceipttaxamount == undefined ? 0 : rows[i].totalreceipttaxamount);
    		}
    		var foot=[{id:'选中金额',totalreceipttaxamount:totalreceipttaxamount}];
    		if(null!=RH_footerobject){
           		foot.push(RH_footerobject);
       		}
    		$("#account-datagrid-receipthand").datagrid("reloadFooter",foot);
    	}
    </script>
  </body>
</html>
