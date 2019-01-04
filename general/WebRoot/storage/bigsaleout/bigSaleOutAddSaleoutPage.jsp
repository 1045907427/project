<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>大单发货单发货单新增</title>

	</head>

	<body>
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center'">
				<table id="bigSaleOut-datagrid-saleOutListPage"></table>
				<div id="bigSaleOut-toolbar-query-saleOutListPage" style="padding:2px;height:auto">
					<form id="bigSaleOut-form-query-saleOutListPage">
						<input type="hidden" name="status" value="2"/>
						<input type="hidden" name="isbigsaleout" value="0"/>
						<table class="querytable">
							<tr>
								<td>业务日期:</td>
						    	<td><input type="text" name="businessdate1" style="width:80px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdate2" class="Wdate" style="width:80px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
			    				<td>编号:</td>
			    				<td><input type="text" name="id" style="width: 150px;"/></td>
			    				<td>订单编号:</td>
			    				<td><input type="text" name="saleorderid" style="width: 120px;"/></td>
			    			</tr>
			    			<tr>
			    				<td>商品名称:</td>
			    				<td><input id="bigSaleOut-query-goodsid" type="text" name="goodsid"/></td>
								<td>出库仓库:</td>
								<td><input id="bigSaleOut-query-storageid" type="text" name="storageid" value="${storageid }"/></td>
								<td>线路：</td>
								<td><input type="text" id="bigSaleOut-query-lineid" name="lineid"/></td>
			    			</tr>
							<tr>
								<td>客户:</td>
								<td><input id="bigSaleOut-query-customerid" type="text" name="customerid"/></td>
								<td colspan="2"></td>
								<td colspan="2">
									<a href="javaScript:void(0);" id="bigSaleOut-queay-saleOutListPage" class="button-qr">查询</a>
									<a href="javaScript:void(0);" id="bigSaleOut-reload-saleOutListPage" class="button-qr">重置</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
			<div data-options="region:'south',border:false">
                <div class="buttonDetailBG" style="text-align:right;">
                    <input type="button" value="确 定" id="bigSaleOut-save-saleOutListPage" />
                </div>
			</div>
		</div>
		<script type="text/javascript">
		
		$(function(){
			var saleoutJson = $("#bigSaleOut-datagrid-saleOutListPage").createGridColumnLoad({
				frozenCol : [[{field:'ck', checkbox:true}]],
				commonCol : [[
							{field:'id',title:'编号',width:130,sortable:true},
							  {field:'saleorderid',title:'订单编号',width:130,sortable:true},
							  {field:'businessdate',title:'业务日期',width:80,sortable:true},
							  {field:'storageid',title:'出库仓库',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.storagename;
					        	}
							  },
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
							  {field:'sendamount',title:'发货出库金额',width:80,align:'right',
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'sendnotaxamount',title:'发货出库未税金额',width:80,align:'right',hidden:true,
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'sourcetype',title:'来源类型',width:90,sortable:true,hidden:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return getSysCodeName("saleout-sourcetype",value);
					        	}
							  },
							  {field:'sourceid',title:'来源编号',width:80,sortable:true},
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
							  {field:'remark',title:'备注',width:80,sortable:true},
							  {field:'addusername',title:'制单人',width:60,sortable:true},
							  {field:'addtime',title:'制单时间',width:120,sortable:true}
						]]
			});
			
			$("#bigSaleOut-datagrid-saleOutListPage").datagrid({
				authority:saleoutJson,
		 		frozenColumns: saleoutJson.frozen,
				columns:saleoutJson.common,
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		sortName:'id',
		 		sortOrder:'desc',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
		 		pageSize:1000,
		 		pageList:[100,200,500,1000],
				toolbar:'#bigSaleOut-toolbar-query-saleOutListPage',
				onDblClickRow:function(rowIndex, rowData){
					top.addOrUpdateTab('storage/showSaleOutEditPage.do?id='+ rowData.id, "发货单查看");
				}
			}).datagrid("columnMoving");
			//商品
    		$("#bigSaleOut-query-goodsid").widget({ 
    			referwid:'RL_T_BASE_GOODS_INFO',
    			singleSelect:true,
    			width:180,
    			onlyLeafCheck:true
    		});
    		//客户
    		$("#bigSaleOut-query-customerid").widget({ 
    			referwid:'RL_T_BASE_SALES_CUSTOMER',
    			singleSelect:false,
    			width:180,
    			onlyLeafCheck:true
    		});
			//线路
			$("#bigSaleOut-query-lineid").widget({
				referwid:'RL_T_BASE_LOGISTICS_LINE',
				singleSelect:true,
				width:120,
				onlyLeafCheck:true
			});
			//出库仓库
			$("#bigSaleOut-query-storageid").widget({
				width:150,
				referwid:'RL_T_BASE_STORAGE_INFO',
				singleSelect:true
			});
			
			//查询
			$("#bigSaleOut-queay-saleOutListPage").click(function(){
				var rows = $("#bigSaleOut-table-SourceBill").datagrid("getRows");
				var addsaleoutids = "";
				for(var i=0;i<rows.length;i++){
					if(addsaleoutids == ""){
						addsaleoutids = rows[i].id;
					}else{
						addsaleoutids += "," + rows[i].id;
					}
				}
				var queryJSON = $("#bigSaleOut-form-query-saleOutListPage").serializeJSON();
				if(addsaleoutids != ""){
					queryJSON['addsaleoutids'] = addsaleoutids;
				}
		       	$("#bigSaleOut-datagrid-saleOutListPage").datagrid({
		       		url: 'storage/getSaleOutListForBigSaleOut.do',
	      			pageNumber:1,
					queryParams:queryJSON
		       	}).datagrid("columnMoving");
			});
    		//重置
    		$("#bigSaleOut-reload-saleOutListPage").click(function(){
    			$("#bigSaleOut-query-customerid").widget("clear");
				$("#bigSaleOut-query-goodsid").widget("clear");
				$("#bigSaleOut-query-lineid").widget("clear");
				$("#bigSaleOut-query-storageid").widget("setValue","${storageid}");
				$("#bigSaleOut-form-query-saleOutListPage").form("reset");
				$("#bigSaleOut-datagrid-saleOutListPage").datagrid("loadData",[]);
    		});
    		
		});
	   	$("#bigSaleOut-save-saleOutListPage").click(function(){
    			var rows = $("#bigSaleOut-datagrid-saleOutListPage").datagrid('getChecked');
    			if(rows.length == 0){
    				$.messager.alert("提醒","请选中要新增的发货单据！");
    				return false;
    			}
    			$.messager.confirm("警告","确定添加?",function(r){
					if(r){
		    			//插入选中行
		    			for(var i=0;i<rows.length;i++){
		    				$dgSourceBillList.datagrid('appendRow',rows[i]);
		    			}
						$("#bigSaleOut-dialog-saleout").dialog('close',true);
					
					}
				});
    		});
  	</script>
	</body>
</html>
