<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>要货申请单列表页面</title>
    <%@include file="/include.jsp" %>  
  </head>
  <body>
	<div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center'">
    		<div id="sales-queryDiv-demandListPage" style="padding:0px;height:auto">
    			<div class="buttonBG" id="sales-buttons-demandListPage" style="height:26px;">
					<security:authorize url="/sales/demandToOrder.do">
    				<a href="javascript:;" class="easyui-linkbutton" id="sales-order-demandListPage" data-options="plain:true,iconCls:'button-audit'">生成订单</a>
    				</security:authorize>
    				<a href="javascript:;" class="easyui-linkbutton" id="sales-view-demandListPage" data-options="plain:true,iconCls:'button-view'">查看</a>
    				<security:authorize url="/sales/deleteDemand.do">
    				<a href="javascript:;" class="easyui-linkbutton" id="sales-delete-demandListPage" data-options="plain:true,iconCls:'button-delete'">删除</a>
    				</security:authorize>
					<security:authorize url="/sales/addDemandByOrderGoods.do">
						<a href="javascript:;" class="easyui-linkbutton" id="sales-addDemandByOrderGoods-demandListPage" data-options="plain:true,iconCls:'button-add'">关联订货单</a>
					</security:authorize>
                    <span id="sales-queryAdvanced-demandListPage"></span>
    			</div>
    			<form id="sales-queryForm-demandListPage">
    				<table class="querytable">
		    			<tr>
		    				<td>客&nbsp;&nbsp;户:</td>
		    				<td class="tdinput"><input type="text" id="sales-customer-demandListPage" name="customerid" style="width: 225px"/></td>
                            <td>编号:</td>
                            <td class="tdinput"><input type="text" name="id" class="len180" /> </td>
                            <td>销售部门：</td>
                            <td class="tdinput">
                                <input name="salesdept" id="sales-salesDept-demandListPage" style="width: 130px"/>
                            </td>
		    			</tr>
		    			<tr>
		    				<td>业务日期:</td>
		    				<td class="tdinput">
                                <input type="text" name="businessdate" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
                                到 <input type="text" name="businessdate1" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
		    				<td>状态:</td>
		    				<td class="tdinput">
		    					<select name="status" style="width:180px;"><option value="0" selected="selected">未生成订单</option><option value="1">已生成订单</option></select>
		    				</td>
                            <td  rowspan="3" colspan="2" class="tdbutton">
                                <a href="javascript:;" id="sales-queay-demandListPage" class="button-qr">查询</a>
                                <a href="javaScript:;" id="sales-resetQueay-demandListPage" class="button-qr">重置</a>
                            </td>
		    			</tr>
		    		</table>
    			</form>
    		</div>
    		<table id="sales-datagrid-demandListPage" data-options="border:false"></table>
    	</div>
    </div>
	<div id="sales-dialog-addDemandByOrderGoods"></div>
    <script type="text/javascript">
    	var initQueryJSON = $("#sales-queryForm-demandListPage").serializeJSON();
    	$(function(){
    		$("#sales-customer-demandListPage").customerWidget({ //客户参照窗口
    			name:'t_sales_demand',
				col:'customerid',
    			singleSelect:true,
    			isdatasql:false,
    			width:225,
    			onlyLeafCheck:false
    		});
			$("#sales-order-demandListPage").click(function(){
				var rows = $("#sales-datagrid-demandListPage").datagrid('getChecked');
				if(rows.length == 0){
					$.messager.alert("提醒", "请选择需生成订单的记录");
					return ;
				}
				$.messager.confirm("提醒", "确定生成订单信息？", function(r){
					if(r){
						var ids = "";
						for(var i=0; i<rows.length; i++){
                            if(ids==""){
                                ids = rows[i].id ;
                            }else{
                                ids += ',' + rows[i].id;
                            }
						}
						var customerid = rows[0].customerid;
						loading("订单生成中...");
						$.ajax({
							url:'sales/canAuditDemand.do',
							dataType:'json',
							type:'post',
							data:{customerId:customerid,ids:ids},
							success:function(data){
								if(data.flag == true){
									loading("订单生成中...");
									$.ajax({
										url:'sales/auditDemand.do',
										dataType:'json',
										type:'post',
										data:'id='+ ids,
										success:function(json){
											loaded();
                                            if(json.msg) {
                                                $.messager.alert("提醒", json.msg);
                                                return false;
                                            }
											if(json.result == "canot"){
												$.messager.alert("提醒", "生成订单失败：非同一客户不可合并生成订单");
											}
											else if(json.result != null && json.result != "null" && json.result != "canot"){
												$.messager.alert("提醒", "生成订单成功，订单号："+ json.result);
												$("#sales-datagrid-demandListPage").datagrid('reload');
											}
											else{
												$.messager.alert("提醒", "生成订单失败");
											}
										}
									});
								}
								else{
									loaded();
									$.messager.confirm("提醒", data.msg+ "是否生成订单？", function(r){
										if(r){
											loading("订单生成中...");
											$.ajax({
												url:'sales/auditDemand.do',
												dataType:'json',
												type:'post',
												data:'id='+ ids,
												success:function(json){
													loaded();
													if(json.result == "canot"){
														$.messager.alert("提醒", "生成订单失败：非同一客户不可合并生成订单");
													}
													else if(json.result != null && json.result != "null" && json.result != "canot"){
														$.messager.alert("提醒", "生成订单成功，订单号："+ json.result);
														$("#sales-datagrid-demandListPage").datagrid('reload');
													}
													else{
														$.messager.alert("提醒", "生成订单失败");
													}
												}
											});
										}
										else{
											loaded();
											return ;
										}
									});
								}
							}
						});
					}
				});
			});
			$("#sales-view-demandListPage").click(function(){
				var row = $("#sales-datagrid-demandListPage").datagrid('getSelected');
				if(row == null){
					$.messager.alert("提醒", "请选择查看的记录");
					return ;
				}
				top.addOrUpdateTab('sales/demandPage.do?type=view&id='+ row.id, "要货申请单查看");
			});
			$("#sales-delete-demandListPage").click(function(){
				var rows = $("#sales-datagrid-demandListPage").datagrid('getChecked');
				if(rows.length == 0){
					$.messager.alert("提醒", "请选择需删除的记录");
					return ;
				}
				var ids = "";
				for(var i=0;i<rows.length;i++){
					if(ids == ""){
						ids = rows[i].id;
					}else{
						ids += "," + rows[i].id;
					}
				}
				$.messager.confirm("提醒", "确定删除该记录？", function(r){
					if(r){
						$.ajax({
							url:'sales/deleteDemand.do',
							dataType:'json',
							type:'post',
							data:'id='+ ids,
							success:function(json){
								if(json.flag == true){
									$.messager.alert("提醒", "删除成功.<br/>"+json.msg);
									$("#sales-datagrid-demandListPage").datagrid('reload');
								}
								else{
									$.messager.alert("提醒", "删除失败.<br/>"+json.msg);
								}
							}
						});
					}
				});
			});

			$("#sales-addDemandByOrderGoods-demandListPage").click(function(){
				var rows = $("#sales-datagrid-demandListPage").datagrid('getChecked');
				if(rows.length == 0){
					$.messager.alert("提醒", "请选择需要关联的记录");
					return ;
				}
				if(rows.length>1){
					$.messager.alert("提醒", "请选择一条记录");
					return ;
				}
				if(rows[0].status=='1'){
					$.messager.alert("提醒", "该单据已生成订单");
					return ;
				}
				$('<div id="sales-dialog-addDemandByOrderGoods-content"></div>').appendTo('#sales-dialog-addDemandByOrderGoods');
				$("#sales-dialog-addDemandByOrderGoods-content").dialog({ //弹出新添加窗口
					title: '关联订货单',
					maximizable: true,
					width: 1000,
					height: 450,
					closed: false,
					modal: true,
					cache: false,
					resizable: true,
					queryParams:{id:rows[0].id},
					href: 'sales/showDemandAddOrderGoodsPage.do?orderid='+rows[0].id,
					buttons:[
						{
							iconCls: 'button-save',
							text:'生成订单',
							handler:function(){
								addOrder();
							}
						},
						{
							iconCls: 'button-close',
							text:'关闭',
							handler:function(){
								$('#sales-dialog-addDemandByOrderGoods-content').dialog("destroy");
							}
						}
					],
					onClose: function () {
						$('#sales-dialog-addDemandByOrderGoods-content').dialog("destroy");
					},
					onLoad: function () {

					}
				});
			})

    		$("#sales-queay-demandListPage").click(function(){
	       		var queryJSON = $("#sales-queryForm-demandListPage").serializeJSON();
	       		$("#sales-datagrid-demandListPage").datagrid('load', queryJSON);
			});
			$("#sales-resetQueay-demandListPage").click(function(){
				$("#sales-customer-demandListPage").customerWidget("clear");
				$("#sales-salesDept-demandListPage").salesWidget('clear');
				$("#sales-queryForm-demandListPage").form("reset");
				var queryJSON = $("#sales-queryForm-demandListPage").serializeJSON();
				$("#sales-datagrid-demandListPage").datagrid('load', queryJSON);
			});			
			
			//销售部门控件
    		$("#sales-salesDept-demandListPage").salesWidget({ 
    			name:'t_base_department',
				col:'name',
    			singleSelect:true,
    			isdatasql:false,
    			width:400,
    			onlyLeafCheck:false
    		});
			
    		$("#sales-queryAdvanced-demandListPage").advancedQuery({ //通用查询
		 		name:'t_sales_demand',
		 		plain:true,
		 		datagrid:'sales-datagrid-demandListPage'
			});
			var demandListJson = $("#sales-datagrid-demandListPage").createGridColumnLoad({
				name :'t_sales_order',
				frozenCol : [[
			    			]],
				commonCol : [[{field:'id',title:'编号',width:130, align: 'left',sortable:true},
							  {field:'businessdate',title:'业务日期',width:80,align:'left',sortable:true},
							  {field:'customerid',title:'客户编码',width:60,align:'left',sortable:true},
							  {field:'customername',title:'客户名称',width:220,align:'left',isShow:true},
							  {field:'handlerid',title:'对方经手人',width:80,align:'left'},
							  {field:'salesdept',title:'销售部门',width:80,align:'left',sortable:true},
							  {field:'salesuser',title:'客户业务员',width:80,align:'left',sortable:true},
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
							  {field:'status',title:'状态',width:80,align:'left',sortable:true,
							  		formatter:function(value,row,index){
						        		if(value == "0"){
						        			return "未生成订单";
						        		}
						        		else if(value == "1"){
						        			return "已生成订单";
						        		}
							        }
							  },
							  {field:'indooruserid',title:'销售内勤',width:60,sortable:true,
							  	formatter:function(value,rowData,index){
					        		return rowData.indoorusername;
						        }
							  },
							  {field:'addusername',title:'制单人',width:60,sortable:true},
							  {field:'addtime',title:'制单日期',width:120,sortable:true},
							  {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
							  {field:'audittime',title:'审核日期',width:80,sortable:true,hidden:true},
							  {field:'remark',title:'备注',width:100}
				              ]]
			});
    		$("#sales-datagrid-demandListPage").datagrid({ 
		 		authority:demandListJson,
		 		frozenColumns:[[{field:'ordercheck',checkbox:true}]],
				columns:demandListJson.common,
		 		fit:true,
		 		title:"",
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		checkOnSelect:true,
		 		selectOnCheck:true,
				sortName:'addtime',
				sortOrder:'desc',
				url: 'sales/getDemandList.do',
				queryParams:initQueryJSON,
				toolbar:'#sales-queryDiv-demandListPage',
			    onDblClickRow:function(index, data){
		    		top.addOrUpdateTab('sales/demandPage.do?type=view&id='+ data.id, "要货申请单查看");
		    	}
			}).datagrid("columnMoving");
    	});	
    </script>
  </body>
</html>
