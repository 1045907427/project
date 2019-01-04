<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>大单发货新增页面</title>
    <%@include file="/include.jsp"%>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north',border:false">
            <div class="buttonBG">
                <a href="javaScript:void(0);" id="storage-add-bigSaleOutPage" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'"  title="生成大单发货">生成大单发货</a>
            </div>
        </div>
        <div data-options="region:'center'">
            <table id="storage-datagrid-bigSaleOutPage"></table>
            <div id="storage-toolbar-bigSaleOutPage" style="padding:2px;height:auto">
                <form action="" id="storage-form-bigSaleOutPage" method="post">
                    <table class="querytable">
                        <tr>
                            <td>业务日期:</td>
                            <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
                            <td>商品品牌:</td>
                            <td><input type="text" id="storage-brandid-bigSaleOutPage" name="brandid"/></td>
                            <td>出库仓库:</td>
                            <td><input name="storageid" id="storage-storageid-bigSaleOutPage" type="text"/></td>
                        </tr>
                        <tr>
                            <td>商 品:</td>
                            <td><input type="text" id="storage-goodsid-bigSaleOutPage" name="goodsid" style="width: 225px;"/></td>
                            <td>客户业务员:</td>
                            <td><input type="text" id="storage-salesuser-bigSaleOutPage" name="salesuser"/></td>
							<td>线路：</td>
							<td><input type="text" id="storage-lineid-bigSaleOutPage" name="lineid"/></td>
                        </tr>
                        <tr>
                        	<td>客户:</td>
                        	<td><input type="text" id="storage-customerid-bigSaleOutPage" name="customerid"/></td>
							<td colspan="2"></td>
							<td colspan="2">
								<a href="javaScript:void(0);" id="storage-query-bigSaleOutPage" class="button-qr">查询</a>
								<a href="javaScript:void(0);" id="storage-reload-bigSaleOutPage" class="button-qr">重置</a>
							</td>
						</tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
    <script type="text/javascript">
    	$(function(){
			
			var saleoutJson = $("#storage-datagrid-bigSaleOutPage").createGridColumnLoad({
				name :'storage_saleout',
				frozenCol : [[
								{field:'idok',checkbox:true,isShow:true}
			    			]],
				commonCol : [[
							  {field:'id',title:'编号',width:130,sortable:true},
							  {field:'saleorderid',title:'销售订单编号',width:130,sortable:true},
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
							  {field:'stopusername',title:'中止人',width:80,hidden:true,sortable:true},
							  {field:'stoptime',title:'中止时间',width:80,hidden:true,sortable:true},
							  {field:'remark',title:'备注',width:80,sortable:true},
							  {field:'addusername',title:'制单人',width:60,sortable:true},
							  {field:'addtime',title:'制单时间',width:120,sortable:true}
				           ]]
			});
			
			$("#storage-datagrid-bigSaleOutPage").datagrid({ 
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
		 		pageSize:500,
				pageList:[20,100,200,500,1000],
				toolbar:'#storage-toolbar-bigSaleOutPage',
				onDblClickRow:function(rowIndex, rowData){
					top.addOrUpdateTab('storage/showSaleOutEditPage.do?id='+ rowData.id, "发货单查看");
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

            //出库仓库
            $("#storage-storageid-bigSaleOutPage").widget({
                width:130,
                referwid:'RL_T_BASE_STORAGE_INFO',
                singleSelect:true,
                required:true
            });
			//商品品牌
			$("#storage-brandid-bigSaleOutPage").widget({
				referwid:'RL_T_BASE_GOODS_BRAND',
				singleSelect:false,
				width:150
			});
			//客户业务员
			$("#storage-salesuser-bigSaleOutPage").widget({
				referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
				singleSelect:false,
				width:150
			});
			//商品
			$("#storage-goodsid-bigSaleOutPage").goodsWidget({
				singleSelect:true,
				isHiddenUsenum:true
			});
			//客户
			$("#storage-customerid-bigSaleOutPage").widget({
				referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_1',
				singleSelect:true,
				width:225
			});
			//线路
			$("#storage-lineid-bigSaleOutPage").widget({
				referwid:'RL_T_BASE_LOGISTICS_LINE',
				singleSelect:true,
				width:130,
				onlyLeafCheck:true
			});
			
			//查询
			$("#storage-query-bigSaleOutPage").click(function(){
	    		var storageid = $("#storage-storageid-bigSaleOutPage").widget('getValue');
				if(!$("#storage-form-bigSaleOutPage").form('validate') || storageid == '')
	    		{
	    			$.messager.alert("提醒","请选择出库仓库！");
	    			return false;
	    		}
	    		
				totalFooter={};
				$("#storage-datagrid-bigSaleOutPage").datagrid('loadData',{total:0,rows:[],footer:[totalFooter]});
				$("#storage-datagrid-bigSaleOutPage").datagrid('clearChecked');
				$("#storage-datagrid-bigSaleOutPage").datagrid('clearSelections');
				var queryJSON = $("#storage-form-bigSaleOutPage").serializeJSON();
				queryJSON['status'] = '2';
		       	$("#storage-datagrid-bigSaleOutPage").datagrid({
		       		url: 'storage/getSaleOutListForBigSaleOut.do',
	      			pageNumber:1,
					queryParams:queryJSON
		       	}).datagrid("columnMoving");
			});
			
			//重置
			$("#storage-reload-bigSaleOutPage").click(function(){
				$("#storage-brandid-bigSaleOutPage").widget('clear');
				$("#storage-goodsid-bigSaleOutPage").goodsWidget('clear');
                $("#storage-storageid-bigSaleOutPage").widget('clear');
                $("#storage-salesuser-bigSaleOutPage").widget('clear');
                $("#storage-customerid-bigSaleOutPage").widget('clear');
				$("#storage-lineid-bigSaleOutPage").widget('clear');
				$("#storage-datagrid-bigSaleOutPage").datagrid('clearChecked');
				$("#storage-datagrid-bigSaleOutPage").datagrid('clearSelections');
				$('#storage-form-bigSaleOutPage')[0].reset();
				$("#storage-datagrid-bigSaleOutPage").datagrid('loadData',{total:0,rows:[],footer:[]});
			});
			
			//大单发货 生成
			$("#storage-add-bigSaleOutPage").click(function(){
				$.messager.confirm("提醒","是否生成新的大单发货？",function(r){
					if(r){
						var storageid = $("#storage-storageid-bigSaleOutPage").widget('getValue');
						if(!$("#storage-form-bigSaleOutPage").form('validate') || storageid == '')
			    		{
			    			$.messager.alert("提醒","请选择出库仓库！");
			    			return false;
			    		}
			    		
			    		var rowArr = $("#storage-datagrid-bigSaleOutPage").datagrid("getChecked");
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
						var storageid = $("#storage-storageid-bigSaleOutPage").widget('getValue');
						loading("提交中..");
						$.ajax({
							url:'storage/addBigSaleOut.do',
							dataType:'json',
							type:'post',
							data:{ids:ids,storageid:storageid},
							success:function(json){
								loaded();
								if(json.flag){
				            		$.messager.alert("提醒","生成成功");
				            		totalFooter={};
									$("#storage-datagrid-bigSaleOutPage").datagrid('loadData',{total:0,rows:[],footer:[totalFooter]});
									$("#storage-datagrid-bigSaleOutPage").datagrid('clearChecked');
									$("#storage-datagrid-bigSaleOutPage").datagrid('clearSelections');
									var queryJSON = $("#storage-form-bigSaleOutPage").serializeJSON();
									queryJSON['status'] = '2';
							       	$("#storage-datagrid-bigSaleOutPage").datagrid({
							       		url: 'storage/getSaleOutListForBigSaleOut.do',
						      			pageNumber:1,
										queryParams:queryJSON
							       	}).datagrid("columnMoving");
				            		var title = parent.getNowTabTitle();
				            		top.addOrUpdateTab('storage/showBigSaleOutPage.do?id='+ json.id+'&type=edit', "大单发货单查看");
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
    		var rows =  $("#storage-datagrid-bigSaleOutPage").datagrid('getChecked');
    		var sendamount = 0;
    		var sendnotaxamount = 0;
    		for(var i=0;i<rows.length;i++){
    			sendamount = Number(sendamount)+Number(rows[i].sendamount == undefined ? 0 : rows[i].sendamount);
    			sendnotaxamount = Number(sendnotaxamount)+Number(rows[i].sendnotaxamount == undefined ? 0 : rows[i].sendnotaxamount);
    		}
    		$("#storage-datagrid-bigSaleOutPage").datagrid("reloadFooter",[{id:'选中金额',sendamount:sendamount,sendnotaxamount:sendnotaxamount}]);
    	}
    </script>
  </body>
</html>
