<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>零售订单列表页面</title>
    <%@include file="/include.jsp" %>   
  </head>  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center'">
    		<div id="sales-queryDiv-orderListPage" style="padding:0px;height:auto">
    			<div class="buttonBG" id="sales-buttons-orderListPage" style="height:26px;"></div>
    			<form id="sales-queryForm-orderListPage">
    				<table class="querytable">
		    			<tr>
		    				<td>业务日期:</td>
		    				<td  class="tdinput"><input type="text" name="businessdate" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdate1" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
		    				<td>编号:</td>
		    				<td  class="tdinput"><input type="text" name="id" class="len180" /> </td>
                            <td>状态:</td>
                            <td  class="tdinput">
                                <select name="isClose" style="width:180px;"><option></option><option value="0" selected="selected">未审核</option><option value="1">已审核</option><option value="2">已作废</option></select>
                            </td>
		    			</tr>
		    			<tr>
		    				<td>客户:</td>
		    				<td  class="tdinput"><input type="text" id="sales-customer-orderListPage" style="width: 223px" name="customerid" /></td>
                            <td>发货仓库：</td>
                            <td class="tdinput">
                                <input  name="storageid" id="sales-storageid-orderListPage" style="width:180px;" />
                            </td>
                            <td>单据类型：</td>
                            <td class="tdinput">
                                <input  name="billtype" id="sales-billtype-orderListPage" style="width:180px;" />
                            </td>
                            <td  rowspan="3" colspan="2" class="tdbutton">
                                <a href="javascript:;" id="sales-queay-orderListPage" class="button-qr">查询</a>
                                <a href="javaScript:;" id="sales-resetQueay-orderListPage" class="button-qr">重置</a>
                            </td>
		    			</tr>
		    		</table>
    			</form>
    		</div>
    		<table id="sales-datagrid-orderListPage" data-options="border:false"></table>
    	</div>
    </div>
    <script type="text/javascript">
    	var SOC_footerobject = null;
    	var initQueryJSON = $("#sales-queryForm-orderListPage").serializeJSON();
    	$(function(){    		
    		$("#sales-storageid-orderListPage").widget({
     			width:180,
				referwid:'RL_T_BASE_STORAGE_INFO',
				singleSelect:true,
				onlyLeafCheck:false
     		});
    		$("#sales-billtype-orderListPage").widget({
                name:'t_sales_order_car',
                col:'billtype',
                width:180,
                initSelectNull:true,
                singleSelect:true
            });
    		$("#sales-customer-orderListPage").customerWidget({ //客户参照窗口
    			name:'t_sales_order_car',
				col:'customerid',
    			singleSelect:true,
    			isdatasql:false,
    			onlyLeafCheck:false
    		});
    		
			$("#sales-queay-orderListPage").click(function(){
	       		var queryJSON = $("#sales-queryForm-orderListPage").serializeJSON();
	       		$("#sales-datagrid-orderListPage").datagrid('load', queryJSON);
			});
			$("#sales-resetQueay-orderListPage").click(function(){
				$("#sales-customer-orderListPage").customerWidget("clear");
				$("#sales-storageid-orderListPage").widget("clear");
                $("#sales-billtype-orderListPage").widget("clear");
				$("#sales-queryForm-orderListPage").form("reset");
				var queryJSON = $("#sales-queryForm-orderListPage").serializeJSON();
				$("#sales-datagrid-orderListPage").datagrid('load', queryJSON);
			});
    		//按钮
			$("#sales-buttons-orderListPage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/sales/orderCarAddPage.do">
					{
						type: 'button-add',
						handler: function(){
							top.addOrUpdateTab('sales/orderCarPage.do', "零售订单新增");
						}
					},
					</security:authorize>
					<security:authorize url="/sales/orderCarViewPage.do">
					{
						type: 'button-view',
						handler: function(){
							var con = $("#sales-datagrid-orderListPage").datagrid('getSelected');
							if(con == null){
								$.messager.alert("提醒","请选择一条记录");
								return false;
							}	
							top.addOrUpdateTab('sales/orderCarPage.do?&type=edit&id='+ con.id, "零售订单查看");
						}
					},
					</security:authorize>
                    {
                        type: 'button-commonquery',
                        attr:{
                            //查询针对的表
                            name:'t_sales_order_car',
                            plain:true,
                            //查询针对的表格id
                            datagrid:'sales-datagrid-orderListPage'
                        }
                    },
					{}
				],
				buttons:[
					{},
					<security:authorize url="/sales/orderCarMultiAudit.do">
					{
						id:'button-mulitAudit',
						name:'批量审核',
						iconCls:'button-audit',
						handler:function(){
							var rows = $("#sales-datagrid-orderListPage").datagrid('getChecked');
							if(rows.length == 0){
								$.messager.alert("提醒","请选中需要审核的记录。");
								return false;
							}
							$.messager.confirm("提醒","确定审核这些订单？",function(r){
								if(r){
									var ids = "";
									for(var i=0; i<rows.length; i++){
										ids += rows[i].id + ',';
									}
									loading("操作中...");
									$.ajax({
										url:'sales/canAuditOrderCar.do',
										dataType:'json',
										type:'post',
										data:'ids='+ ids,
										success:function(json){
											if(json.flag == true){
												$.ajax({
													url:'sales/auditMultiOrderCar.do',
													dataType:'json',
													type:'post',
													data:'ids='+ ids,
													success:function(json){
														loaded();
														if(json.flag == true){
															$.messager.alert("提醒","审核成功："+json.success+"<br/>审核失败："+json.failure+"<br/>无需审核："+json.noaudit+"<br/>"+json.msg);
															$("#sales-datagrid-orderListPage").datagrid('reload');
														}
														else{
															$.messager.alert("提醒","审核出错");
														}
													},
													error:function(){
														loaded();
														$.messager.alert("错误","操作出错");
													}
												});
											}else{
												loaded();
												$.messager.confirm("提醒",json.msg+"是否继续审核？",function(r){
													if(r){
														loading("操作中...");
														$.ajax({
															url:'sales/auditMultiOrderCar.do',
															dataType:'json',
															type:'post',
															data:'ids='+ ids,
															success:function(json){
																loaded();
																if(json.flag == true){
																	$.messager.alert("提醒","审核成功："+json.success+"<br/>审核失败："+json.failure+"<br/>无需审核："+json.noaudit+"<br/>"+json.msg);
																	$("#sales-datagrid-orderListPage").datagrid('reload');
																}
																else{
																	$.messager.alert("提醒","审核出错");
																}
															},
															error:function(){
																loaded();
																$.messager.alert("错误","操作出错");
															}
														});
													}
												});
											}
										},
										error:function(){
											loaded();
											$.messager.alert("错误","操作出错");
										}
									});
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/sales/deleteOrderCar.do">
					{
						id: 'button-deleteOrderCar',
						name:'作废',
						iconCls:'button-delete',
						handler: function(){
							$.messager.confirm("提醒","确定作废该订单信息？",function(r){
								if(r){
									loading("删除中..");
									var rows = $("#sales-datagrid-orderListPage").datagrid('getChecked');
									var ids = "";
									for(var i=0;i<rows.length;i++){
										if(ids==""){
											ids = rows[i].id;
										}else{
											ids += ","+rows[i].id;
										}
									}
									if(ids != ''){
										$.ajax({
											url:'sales/deleteOrderCar.do',
											dataType:'json',
											type:'post',
											data:'ids='+ ids,
											success:function(json){
												loaded();
												if(json.flag == true){
													$.messager.alert("提醒","删除成功："+json.success+"<br/>删除失败："+json.failure);
													$("#sales-datagrid-orderListPage").datagrid('reload');
												}
												else{
													$.messager.alert("提醒","删除失败");
												}
											},
											error:function(){
												loaded();
												$.messager.alert("错误","删除失败");
											}
										});
									}
								}
							});
						}
					},
					</security:authorize>
					{}
				],
				model: 'bill',
				type: 'list',
				tname: 't_sales_order_car'
			});
			var orderListJson = $("#sales-datagrid-orderListPage").createGridColumnLoad({
				frozenCol : [[
			    			]],
				commonCol : [[{field:'id',title:'编号',width:125, align: 'left',sortable:true},
							  {field:'businessdate',title:'业务日期',width:80,align:'left',sortable:true},
                                {field:'billtype',title:'单据类型',width:60,align:'left',
                                    formatter:function(value,rowData,index){
                                        return getSysCodeName('ordercar-billtype', value);
                                    }
                                },
							  {field:'customerid',title:'客户编码',width:60,align:'left',sortable:true},
							  {field:'customername',title:'客户名称',width:220,align:'left',isShow:true},
							  {field:'storageid',title:'发货仓库',width:80,align:'left',sortable:true},
							  {field:'salesdept',title:'销售部门',width:80,align:'left',sortable:true},
							  {field:'salesuser',title:'客户业务员',width:70,align:'left',sortable:true},
							  {field:'field01',title:'金额',width:80,align:'right',
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'field02',title:'未税金额',width:80,align:'right',hidden:true,
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'field03',title:'税额',width:80,align:'right',hidden:true,
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'status',title:'状态',width:60,align:'left',
							  	formatter:function(value,rowData,index){
							  		if(value=="2"){
							  			return "保存";
							  		}else if(value =="3"){
							  			return "审核通过";
							  		}else if(value == "4"){
							  			return "关闭";
							  		}else if(value=="9"){
							  			return "作废";
							  		}
						        }
							  },
							  {field:'indooruserid',title:'销售内勤',width:60,sortable:true,
							  	formatter:function(value,rowData,index){
					        		return rowData.indoorusername;
						        }
							  },
							  {field:'addusername',title:'制单人',width:60,sortable:true},
							  {field:'addtime',title:'制单时间',width:120,sortable:true},
							  {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
							  {field:'audittime',title:'审核时间',width:80,sortable:true,hidden:true},
							  {field:'remark',title:'备注',width:100}
				              ]]
			});
			$("#sales-datagrid-orderListPage").datagrid({ 
		 		authority:orderListJson,
		 		frozenColumns:[[{field:'ordercheck',checkbox:true}]],
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
				sortName:'addtime',
				sortOrder:'desc',
				url: 'sales/getOrderCarList.do',
				queryParams:initQueryJSON,
				toolbar:'#sales-queryDiv-orderListPage',
			    onDblClickRow:function(index, data){
					top.addOrUpdateTab('sales/orderCarPage.do?type=edit&id='+data.id, '零售订单查看');
		    	},
	  	 		onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if( null!=footerrows && footerrows.length>0){
						SOC_footerobject = footerrows[1];
						orderCarListTotalAmount();
					}
		 		},
				onCheckAll:function(){
					orderCarListTotalAmount();
				},
				onUncheckAll:function(){
					orderCarListTotalAmount();
				},
				onCheck:function(){
					orderCarListTotalAmount();
				},
				onUncheck:function(){
					orderCarListTotalAmount();
				}
			}).datagrid("columnMoving");
    	});
    	
    	function orderCarListTotalAmount(){
    		var rows =  $("#sales-datagrid-orderListPage").datagrid('getChecked');
			if(null==rows || rows.length==0){
           		return false;
       		}
   			var amount = 0;
   			var notaxamount = 0;
   			var tax = 0
       		
       		for(var i=0;i<rows.length;i++){
       			amount = Number(amount)+Number(rows[i].field01 == undefined ? 0 : rows[i].field01);
       			notaxamount = Number(notaxamount)+Number(rows[i].field02 == undefined ? 0 : rows[i].field02);
       			tax = Number(tax)+Number(rows[i].field03 == undefined ? 0 : rows[i].field03);
       		}
       		var foot=[{customername:'选中合计',field01:amount,field02:notaxamount,field03:tax}];
       		if(null!=SOC_footerobject){
           		foot.push(SOC_footerobject);
       		}
		  	$("#sales-datagrid-orderListPage").datagrid("reloadFooter",foot);
    	}
    </script>
  </body>
</html>
