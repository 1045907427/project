<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>欠货单页面</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
	<input type="hidden" id="sales-backid-oweOrderPage" value="${id }" />
    <div class="easyui-layout" id="sales-oweOrderPage-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="sales-buttons-oweOrderPage" style="height:26px;">
    		</div>
    	</div>
    	<div data-options="region:'center',border:false">
    		<div id="sales-panel-oweOrderPage">
    		</div>
    	</div>
    </div>
  	<div class="easyui-dialog" id="sales-dialog-oweOrderPage" closed="true"></div>
  	<script type="text/javascript">
  		var wareListJson = [[{field:'goodsid',title:'商品编码',width:70,align:' left'},
			{field:'goodsname', title:'商品名称', width:220,align:'left',aliascol:'goodsid',
				formatter:function(value,rowData,rowIndex){
					if(rowData.goodsInfo != null){
						if(rowData.deliverytype=='1'){
							return "<font color='blue'>&nbsp;赠 </font>"+rowData.goodsInfo.name;
						}else if(rowData.deliverytype=='2'){
							return "<font color='blue'>&nbsp;捆绑 </font>"+rowData.goodsInfo.name;
						}else{
							return rowData.goodsInfo.name;
						}
					}else{
						return "";
					}
				}
			},
			{field:'barcode', title:'条形码',width:90,align:'left',aliascol:'goodsid',
				formatter:function(value,rowData,rowIndex){
					if(rowData.goodsInfo != null){
						return rowData.goodsInfo.barcode;
					}else{
						return "";
					}
				}
			},
			{field:'brandName', title:'商品品牌',width:80,align:'left',aliascol:'goodsid',hidden:true,
				formatter:function(value,rowData,rowIndex){
					if(rowData.goodsInfo != null){
						return rowData.goodsInfo.brandName;
					}else{
						return "";
					}
				}
			},
			{field:'boxnum', title:'箱装量',aliascol:'goodsid',width:45,align:'right',
				formatter:function(value,rowData,rowIndex){
					if(rowData.goodsInfo != null){
						return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
					}else{
						return "";
					}
				}
			},
			{field:'unitname', title:'单位',width:35,align:'left'},

			{field:'useable', title:'可用量',width:60,align:'right',
				formatter:function(value,row,index){
					return formatterBigNumNoLen(value);
				}
			},


			{field:'unitnum', title:'数量',width:60,align:'right',isShow:true,sortable:true,
				formatter:function(value,row,index){
					return formatterBigNumNoLen(value);
				},
				styler:function(value,row,index){
					var status = $("#sales-status-oweOrderViewPage").val();
					if(status=="0" &&Number(row.useable)<Number(row.unitnum)){
						return 'background-color:red;';
					}
				}
			},
			{field:'ordernum', title:'生成订单数量',width:80,align:'right',
				formatter:function(value,row,index){
					return formatterBigNumNoLen(value);
				},
				editor:{
					type:'numberbox',
					options:{
						precision:${decimallen},
						max:999999999999,
						min:0
					}
				},
			},

			{field:'taxprice', title:'单价',width:60,align:'right',
				formatter:function(value,row,index){
						return formatterMoney(value);
				}
			},
			{field:'taxamount', title:'金额',width:60,align:'right',
				formatter:function(value,row,index){
					return formatterMoney(value);
				}
			},
			{field:'taxtype', title:'税种',width:70,align:'left',hidden:true,
				formatter:function(value,row,index){
					return row.taxtypename;
				}
			},
			{field:'notaxprice', title:'未税单价',width:80,align:'right', hidden:true,
				formatter:function(value,row,index){
					return formatterMoney(value);
				}
			},
			{field:'notaxamount', title:'未税金额',width:90,align:'right', hidden:true,
				formatter:function(value,row,index){
					return formatterMoney(value);
				}
			},
			{field:'tax', title:'税额',width:80,align:'right',hidden:true,
				formatter:function(value,row,index){
					return formatterMoney(value);
				}
			},
			{field:'auxnumdetail', title:'辅数量',width:60,align:'right'},
			{field:'deliverydate', title:'交货日期',width:80,align:'left'},
			{field:'remark', title:'备注',width:300,align:'left'}
		  ]]
  		$(function(){
  			//按钮
			$("#sales-buttons-oweOrderPage").buttonWidget({

				initButton:[
					{},
					<security:authorize url="/sales/oweOrderSave.do">
					{
						type: 'button-save',
						handler: function(){
							var rows = $("#sales-datagrid-oweOrderViewPage").datagrid('getRows');
							$("#sales-goodsJson-oweOrderViewPage").val(JSON.stringify(rows));
                            $("#sales-saveaudit-oweOrderViewPage").val("save");
							$.messager.confirm("提醒","是否保存当前销售欠货单",function(r){
								if(r){
									$("#sales-form-oweOrderViewPage").submit();
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/sales/deleteOweOrder.do">
					{
						id: 'button-delete-oweOrderPage',
						type: 'button-delete',
						handler: function(){
							$.messager.confirm("提醒","是否删除当前销售欠单？",function(r){
								if(r){
									var id = $("#sales-id-oweOrderViewPage").val();
									if(id!=""){
										loading("删除中..");
										$.ajax({
											url:'sales/deleteOweOrder.do',
								            type:'post',
								            dataType:'json',
								            data:'ids='+ id,
								            success:function(json){
								            	loaded();
												if(json.flag == true){
													$.messager.alert("提醒", "删除成功");
													var data = $("#sales-buttons-oweOrderPage").buttonWidget("removeData", '');
													if(data!=null){
														   $("#sales-panel-oweOrderPage").panel('refresh', 'sales/oweOrderViewPage.do?id='+ data.id);
													}else{
			                                            parent.closeNowTab();
			                                        }
												}
												else{
													$.messager.alert("错误", "删除失败")
												}
											},
								            error:function(){
								            	loaded();
								            	$.messager.alert("错误", "删除出错")
								            }
								        });
									}
								}
							});
						}
					},
					</security:authorize>
					{
						type: 'button-back',
						handler: function(data){
							$("#sales-backid-oweOrderPage").val(data.id);
							refreshPanel('sales/oweOrderViewPage.do?id='+ data.id);
						}
					},
					{
						type: 'button-next',
						handler: function(data){
							$("#sales-backid-oweOrderPage").val(data.id);
							refreshPanel('sales/oweOrderViewPage.do?id='+ data.id);
						}
					},
					{},
				],
				 buttons:[
                     <security:authorize url="/sales/oweOrderToOrder.do">
                     {
                         id: 'button-audit-oweOrderPage',
                         name:'生成订单',
                         iconCls:'button-audit',
                         handler: function(){
                             var id = $("#sales-backid-oweOrderPage").val();
                             if(id == ''){
                                 return false;
                             }
                             var status = $("#sales-status-oweOrderViewPage").val();
                             if(status=="1"){
                                 $.messager.alert("提醒","订单已生成");
                                 return false;
                             }
                             $.messager.confirm("提醒", "确定生成订单信息？", function(r){
                                 if(r){
                                     var customerid = $("#sales-customer-oweOrderViewPage").widget('getValue');
                                     loading("订单生成中...");
                                     $.ajax({
                                         url:'sales/checkAddOrder.do',
                                         dataType:'json',
                                         type:'post',
                                         data:{customerIds:customerid,ids:id},
                                         success:function(data){
                                             if(data.flag == true){
                                                 loaded();
                                                 loading("订单生成中...");
                                                 var rows = $("#sales-datagrid-oweOrderViewPage").datagrid('getRows');
                                                 $("#sales-goodsJson-oweOrderViewPage").val(JSON.stringify(rows));
                                                 $("#sales-saveaudit-oweOrderViewPage").val("saveaudit");
												 $("#sales-form-oweOrderViewPage").submit();
                                             }
                                             else{
                                                 loaded();
                                                 $.messager.confirm("提醒", data.msg+ "，是否确定生成订单？", function(r){
                                                     if(r){
                                                         loading("订单生成中...");
                                                         var rows = $("#sales-datagrid-oweOrderViewPage").datagrid('getRows');
                                                         $("#sales-goodsJson-oweOrderViewPage").val(JSON.stringify(rows));
                                                         $("#sales-saveaudit-oweOrderViewPage").val("saveaudit");
                                                         $("#sales-form-oweOrderViewPage").submit();
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
                         }
                     },
                     </security:authorize>
					<security:authorize url="/sales/closeOweOrder.do">
					{
						id: 'button-close-oweOrderPage',
						name:'关闭',
						iconCls:'button-delete',
						handler: function(){
							$.messager.confirm("提醒","是否关闭当前销售欠单？",function(r){
								if(r){
									var id = $("#sales-id-oweOrderViewPage").val();
									if(id!=""){
										loading("关闭中..");
										$.ajax({
											url:'sales/closeOweOrder.do',
											type:'post',
											dataType:'json',
											data:'ids='+ id,
											success:function(json){
												loaded();
												if(json.flag == true){
													$.messager.alert("提醒", "关闭成功");
													DataGridRefresh();
													parent.closeNowTab();
												}
												else{
													$.messager.alert("错误", "关闭失败")
												}
											},
											error:function(){
												loaded();
												$.messager.alert("错误", "关闭出错")
											}
										});
									}
								}
							});
						}
					},
					 </security:authorize>

				],
				layoutid:'sales-oweOrderPage-layout',
				model: 'bill',
				type: 'view',
				tab:'要货申请单',
				taburl:'/sales/oweOrderListPage.do',
				id:'${id}',
				datagrid:'sales-datagrid-oweOrderListPage'
			});
  			$("#sales-panel-oweOrderPage").panel({
				href:'sales/oweOrderViewPage.do?id=${id}',
			    cache:false,
			    maximized:true,
			    border:false
			});

  		});
  		function countTotal(){ //计算合计
    		var rows = $("#sales-datagrid-oweOrderViewPage").datagrid('getRows');
    		var leng = rows.length;
    		var unitnum = 0;
    		var taxamount = 0;
    		var notaxamount = 0;
    		var tax = 0;
    		for(var i=0; i<leng; i++){
    			unitnum += Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
    			taxamount += Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
    			notaxamount += Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
    			tax += Number(rows[i].tax == undefined ? 0 : rows[i].tax);
    		}
    			$("#sales-datagrid-oweOrderViewPage").datagrid('reloadFooter',[{goodsid:'合计', unitnum: unitnum, taxamount: taxamount, notaxamount: notaxamount, tax: tax}]);
    	}
    	function refreshPanel(url){ //更新panel
    		$("#sales-panel-oweOrderPage").panel('refresh', url);
    	}
    	var Listurl="/sales/oweOrderListPage.do";
    	function DataGridRefresh(){
    		try{
    			tabsWindowURL(Listurl).$("#sales-datagrid-oweOrderListPage").datagrid('reload');
    		}catch(e){

    		}
    	}

  	</script>
  </body>
</html>
