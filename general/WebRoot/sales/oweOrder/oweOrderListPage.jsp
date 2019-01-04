<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>欠货列表页面</title>
    <%@include file="/include.jsp" %>  
  </head>
  <body>
	<div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center'">
    		<div id="sales-queryDiv-oweOrderListPage" style="padding:0px;height:auto">
    			<div class="buttonBG" id="sales-buttons-oweOrderListPage" style="height:26px;">
					<security:authorize url="/sales/oweOrderToOrder.do">
    				<a href="javascript:;" class="easyui-linkbutton" id="sales-order-oweOrderListPage" data-options="plain:true,iconCls:'button-audit'">生成订单</a>
    				</security:authorize>
    				<security:authorize url="/sales/viewOweOrder.do">
    				<a href="javascript:;" class="easyui-linkbutton" id="sales-view-oweOrderListPage" data-options="plain:true,iconCls:'button-view'">查看</a>
    				</security:authorize>
    				<security:authorize url="/sales/deleteOweOrder.do">
    				<a href="javascript:;" class="easyui-linkbutton" id="sales-delete-oweOrderListPage" data-options="plain:true,iconCls:'button-delete'">删除</a>
    				</security:authorize>
    				<security:authorize url="/sales/closeOweOrder.do">
    				<a href="javascript:;" class="easyui-linkbutton" id="sales-close-oweOrderListPage" data-options="plain:true,iconCls:'button-close'">关闭</a>
                    </security:authorize>
                    <span id="sales-queryAdvanced-oweOrderListPage"></span>
    			</div>
    			<form id="sales-queryForm-oweOrderListPage">
    				<table class="querytable">
		    			<tr>
		    				<td>客&nbsp;&nbsp;户:</td>
		    				<td class="tdinput"><input type="text" id="sales-customer-oweOrderListPage" name="customerid" style="width: 225px"/></td>
                            <td>编号:</td>
                            <td class="tdinput"><input type="text" name="id" class="len180" /> </td>
                            <td>商品编号：</td>
                            <td class="tdinput">
                                <input name="goodsid" id="sales-goodsid-oweOrderListPage" style="width: 130px"/>
                            </td>
		    			</tr>
		    			<tr>
		    				<td>业务日期:</td>
		    				<td class="tdinput">
                                <input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
                                                                 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
		    				<td>状态:</td>
		    				<td class="tdinput">
		    					<select name="status" style="width:180px;">
		    					<option ></option>
		    					<option value="0" selected="selected">未生成订单</option>
		    					<option value="1">已生成订单</option>
		    					<option value="4">关闭</option>
		    					</select>
		    				</td>
                            <td  rowspan="3" colspan="2" class="tdbutton">
                                <a href="javascript:;" id="sales-queay-oweOrderListPage" class="button-qr">查询</a>
                                <a href="javaScript:;" id="sales-resetQueay-oweOrderListPage" class="button-qr">重置</a>
                            </td>
		    			</tr>
		    		</table>
    			</form>
    		</div>
    		<table id="sales-datagrid-oweOrderListPage" data-options="border:false"></table>
    	</div>
    </div>
    <script type="text/javascript">
        var selectids = "${ids}";
        var idarr=selectids.split(',');
    	var initQueryJSON = $("#sales-queryForm-oweOrderListPage").serializeJSON();
    	$(function(){
    		$("#sales-customer-oweOrderListPage").customerWidget({ //客户参照窗口
    			singleSelect:true,
    			isdatasql:false,
    			width:225,
    			onlyLeafCheck:false
    		});
    		//商品控件
    		$("#sales-goodsid-oweOrderListPage").goodsWidget({
       				col: 'id',
       				singleSelect: true,
       				width: 150,
       				canBuySale: '1'
       			});
			
    		//生成订单
    		$("#sales-order-oweOrderListPage").click(function(){
    			var rows = $("#sales-datagrid-oweOrderListPage").datagrid('getChecked');
				if(rows.length == 0){
					$.messager.alert("提醒", "请选择需所要生成订单的记录");
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
				$.messager.confirm("提醒", "确定将所选记录生成销售订单？", function(r){
					if(r){
						$.ajax({
							url:'sales/OweOrderAddOrders.do',
							dataType:'json',
							type:'post',
							data:'ids='+ ids,
							success:function(json){
								$.messager.alert("提醒", json.msg);
								$("#sales-datagrid-oweOrderListPage").datagrid('reload');
							}
						});
					}
				});
			});
    		//查看
			$("#sales-view-oweOrderListPage").click(function(){
				var row = $("#sales-datagrid-oweOrderListPage").datagrid('getSelected');
				if(row == null){
					$.messager.alert("提醒", "请选择查看的记录");
					return ;
				}
				top.addOrUpdateTab('sales/oweOrderPage.do?type=view&id='+ row.id, "要货申请单查看");
			});
    		//删除
			$("#sales-delete-oweOrderListPage").click(function(){
				var rows = $("#sales-datagrid-oweOrderListPage").datagrid('getChecked');
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
				$.messager.confirm("提醒", "确定删除所选记录？", function(r){
					if(r){
						$.ajax({
							url:'sales/deleteOweOrder.do',
							dataType:'json',
							type:'post',
							data:'ids='+ ids,
							success:function(json){
								if(json.flag == true){
									$.messager.alert("提醒", "删除成功");
									$("#sales-datagrid-oweOrderListPage").datagrid('reload');
								}
								else{
									$.messager.alert("提醒", json.msg);
									$("#sales-datagrid-oweOrderListPage").datagrid('reload');
								}
							}
						});
					}
				});
			});
    		//关闭
			$("#sales-close-oweOrderListPage").click(function(){
				var rows = $("#sales-datagrid-oweOrderListPage").datagrid('getChecked');
				if(rows.length == 0){
					$.messager.alert("提醒", "请选择需关闭的记录");
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
				$.messager.confirm("提醒", "确定关闭所选记录？", function(r){
					if(r){
						$.ajax({
							url:'sales/closeOweOrder.do',
							dataType:'json',
							type:'post',
							data:'ids='+ ids,
							success:function(json){
								if(json.flag == true){
									$.messager.alert("提醒", "关闭成功");
									$("#sales-datagrid-oweOrderListPage").datagrid('reload');
								}
								else{
									$.messager.alert("提醒", json.msg);
									$("#sales-datagrid-oweOrderListPage").datagrid('reload');
								}
							}
						});
					}
				});
			});
    		$("#sales-queay-oweOrderListPage").click(function(){
	       		var queryJSON = $("#sales-queryForm-oweOrderListPage").serializeJSON();
	       		$("#sales-datagrid-oweOrderListPage").datagrid('load', queryJSON);
			});
			$("#sales-resetQueay-oweOrderListPage").click(function(){
				$("#sales-goodsid-oweOrderListPage").goodsWidget("clear");
				$("#sales-customer-oweOrderListPage").customerWidget("clear");
				$("#sales-queryForm-oweOrderListPage").form("reset");
				var queryJSON = $("#sales-queryForm-oweOrderListPage").serializeJSON();
				$("#sales-datagrid-oweOrderListPage").datagrid('load', queryJSON);
			});			
			
			
    		$("#sales-queryAdvanced-oweOrderListPage").advancedQuery({ //通用查询
		 		name:'t_sales_owe_order',
		 		plain:true,
		 		datagrid:'sales-datagrid-oweOrderListPage'
			});
			var oweOrderListJson = $("#sales-datagrid-oweOrderListPage").createGridColumnLoad({
				name :'',
				frozenCol : [[
			    			]],
				commonCol : [[{field:'id',title:'编号',width:130, align: 'left',sortable:true},
							  {field:'businessdate',title:'业务日期',width:80,align:'left',sortable:true},
					          {field:'billno',title:'来源销售订单',width:130, align: 'left',sortable:true,hidden:true},
							  {field:'customerid',title:'客户编码',width:60,align:'left',sortable:true},
							  {field:'customername',title:'客户名称',width:220,align:'left',isShow:true},
							  {field:'salesdept',title:'销售部门',width:80,align:'left',sortable:true,
								  formatter:function(value,rowData,index){
									  return rowData.salesdeptname;
								  }
							  },
							  {field:'salesusername',title:'客户业务员',width:80,align:'left',sortable:true},
							  
							  {field:'status',title:'状态',width:80,align:'left',sortable:true,
							  		formatter:function(value,row,index){
						        		if(value == "0"){
						        			return "未生成订单";
						        		}
						        		else if(value == "1"){
						        			return "已生成订单";
						        		}
						        		else if(value == "4"){
						        			return "关闭";
						        		}
							        }
							  },
							  {field:'indooruserid',title:'销售内勤',width:60,sortable:true,
							  	formatter:function(value,rowData,index){
					        		return rowData.indoorusername;
						        }
							  },
							  {field:'addusername',title:'制单人',width:60,sortable:true},
							  {field:'addtime',title:'制单日期',width:130,sortable:true,
								  formatter: function(dateValue,row,index){
									  	 if(dateValue!=undefined){
									  		 var newstr=dateValue.replace("T"," ");
									  		 return newstr;
									     }
									     return " ";
									  }  
							  },
							  {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
							  {field:'audittime',title:'审核日期',width:80,sortable:true,hidden:true,
								  formatter: function(dateValue,row,index){
									  	 if(dateValue!=undefined){
									  		 var newstr=dateValue.replace("T"," ");
									  		 return newstr;
									     }
									     return " ";
									  }    
							  },
							  {field:'remark',title:'备注',width:100}
				              ]]
			});
    		$("#sales-datagrid-oweOrderListPage").datagrid({ 
		 		authority:oweOrderListJson,
		 		frozenColumns:[[{field:'ordercheck',checkbox:true}]],
				columns:oweOrderListJson.common,
		 		fit:true,
		 		title:"",
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		checkOnSelect:true,
		 		selectOnCheck:true,
				sortName:'id',
				sortOrder:'desc',
				url: 'sales/getOweOrderList.do',
				queryParams:initQueryJSON,
				toolbar:'#sales-queryDiv-oweOrderListPage',
			    onDblClickRow:function(index, data){
		    		top.addOrUpdateTab('sales/oweOrderPage.do?type=view&id='+ data.id, "欠货单查看");
		    	},
			}).datagrid("columnMoving");
    	});	
    </script>
  </body>
</html>
