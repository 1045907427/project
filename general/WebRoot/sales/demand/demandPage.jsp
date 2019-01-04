<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>要货申请单页面</title>
    <%@include file="/include.jsp" %>  
  </head>
  <body>
	<input type="hidden" id="sales-backid-demandPage" value="${id }" />
    <div class="easyui-layout" id="sales-demandPage-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="sales-buttons-demandPage" style="height:26px;">
    		</div>
    	</div>
    	<div data-options="region:'center',border:false">
    		<div id="sales-panel-demandPage">
    		</div>
    	</div>
    </div>
  	<div class="easyui-dialog" id="sales-dialog-demandPage" closed="true"></div>
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
    					{field:'unitnum', title:'数量',width:60,align:'right',
    						formatter:function(value,row,index){
				        		return formatterBigNumNoLen(value);
					        }
				    	},
				    	{field:'taxprice', title:'单价',width:60,align:'right',
    						formatter:function(value,row,index){
				        		if(parseFloat(value)>parseFloat(row.oldprice)){
				        			return "<font color='blue'>"+ formatterMoney(value)+ "</font>";
				        		}
				        		else if(parseFloat(value)<parseFloat(row.oldprice)){
				        			return "<font color='red'>"+ formatterMoney(value)+ "</font>";
				        		}
				        		else{
				        			return formatterMoney(value);
				        		}
					        }
				    	},
				    	{field:'boxprice', title:'箱价',width:60,aliascol:'taxprice',align:'right',
    						formatter:function(value,row,index){
				        		if(parseFloat(row.taxprice)>parseFloat(row.oldprice)){
				        			return "<font color='blue'>"+ formatterMoney(value)+ "</font>";
				        		}
				        		else if(parseFloat(row.taxprice)<parseFloat(row.oldprice)){
				        			return "<font color='red'>"+ formatterMoney(value)+ "</font>";
				        		}
				        		else{
				        			return formatterMoney(value);
				        		}
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
			$("#sales-buttons-demandPage").buttonWidget({
				initButton:[
					{},
    				<security:authorize url="/sales/demandToOrder.do">
					{
						type: 'button-audit',
						handler: function(){
							var id = $("#sales-backid-demandPage").val();
							if(id == ''){
								return false;
							}
							var status = $("#sales-status-demandViewPage").val();
							if(status=="1"){
								$.messager.alert("提醒","订单已生成");
								return false;
							}
							$.messager.confirm("提醒", "确定生成订单信息？", function(r){
								if(r){
									var customerid = $("#sales-customer-demandViewPage").widget('getValue');
									loading("订单生成中...");
									$.ajax({
										url:'sales/canAuditDemand.do',
										dataType:'json',
										type:'post',
										data:{customerId:customerid,ids:id},
										success:function(data){
											if(data.flag == true){
												loading("订单生成中...");
												$.ajax({
													url:'sales/auditDemand.do',
													dataType:'json',
													type:'post',
													data:'id='+ id,
													success:function(json){
														loaded();
														if(json.result == "canot"){
															$.messager.alert("提醒", "生成订单失败：非同一客户不可合并生成订单");
														}
														else if(json.result != null && json.result != "null" && json.result != "canot"){
															$.messager.alert("提醒", "生成订单成功，订单号："+ json.result);
															$("#sales-panel-demandPage").panel('refresh', 'sales/demandViewPage.do?id='+ id);
														}
														else{
															$.messager.alert("提醒", "生成订单失败");
														}
													},
													error:function(){
														loaded();
														$.messager.alert("错误", "生成订单失败");
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
															data:'id='+ id,
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
																	$("#sales-panel-demandPage").panel('refresh', 'sales/demandViewPage.do?id='+ id);
																}
																else{
																	$.messager.alert("提醒", "生成订单失败");
																}
															},
															error:function(){
																loaded();
																$.messager.alert("错误", "生成订单失败");
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
						}
					},
					</security:authorize>
					{
						type: 'button-back',
						handler: function(data){
							$("#sales-backid-demandPage").val(data.id);
							refreshPanel('sales/demandViewPage.do?id='+ data.id);
						}
					},
					{
						type: 'button-next',
						handler: function(data){
							$("#sales-backid-demandPage").val(data.id);
							refreshPanel('sales/demandViewPage.do?id='+ data.id);
						}
					},
					{}
				],
				buttons:[
					<security:authorize url="/sales/updateDemandToOrderCar.do">
					{
						id:'button-auditDemand',
						name:'生成直营销售单',
						iconCls:'button-audit',
						handler:function(){
							var id = $("#sales-backid-demandPage").val();
							$.messager.confirm("提醒","是否把该要货申请单转换成直营销售单？",function(r){
								if(r){
									loading("操作中..");
									$.ajax({
										url:'sales/updateDemandToOrderCar.do',
										dataType:'json',
										type:'post',
										data:{id:id},
										success:function(json){
											loaded();
											if(json.flag == true){
												$.messager.alert("提醒","操作成功。"+json.msg);
												//关闭当前标签页
			    	  							top.updateTab('sales/orderCarPage.do?type=edit&id='+id, '直营销售单查看');
											}
											else{
												$.messager.alert("提醒","操作失败");
											}
										},
										error:function(){
											loaded();
											$.messager.alert("错误","操作失败");
										}
									});
								}
							});
						}
					},
					</security:authorize>
					{}
				],
				layoutid:'sales-demandPage-layout',
				model: 'bill',
				type: 'view',
				tab:'要货申请单',
				taburl:'/sales/demandListPage.do',
				id:'${id}',
				datagrid:'sales-datagrid-demandListPage'
			});
  			$("#sales-panel-demandPage").panel({
				href:'sales/demandViewPage.do?id=${id}',
			    cache:false,
			    maximized:true,
			    border:false
			});
			$("#sales-order-demandPage").click(function(){
				var id = $("#sales-backid-demandPage").val();
				if(id == ''){
					return false;
				}
				var status = $("#sales-status-demandViewPage").val();
				if(status=="1"){
					$.messager.alert("提醒","订单已生成");
					return false;
				}
				$.messager.confirm("提醒", "确定生成订单信息？", function(r){
					if(r){
						var customerid = $("#sales-customer-demandViewPage").widget('getValue');
						loading("订单生成中...");
						$.ajax({
							url:'sales/canAuditDemand.do',
							dataType:'json',
							type:'post',
							data:'customerId='+ customerid,
							success:function(data){
								if(data.flag == true){
									loading("订单生成中...");
									$.ajax({
										url:'sales/auditDemand.do',
										dataType:'json',
										type:'post',
										data:'id='+ id,
										success:function(json){
											loaded();
											if(json.result == "canot"){
												$.messager.alert("提醒", "生成订单失败：非同一客户不可合并生成订单");
											}
											else if(json.result != null && json.result != "null" && json.result != "canot"){
												$.messager.alert("提醒", "生成订单成功，订单号："+ json.result);
												$("#sales-panel-demandPage").panel('refresh', 'sales/demandViewPage.do?id='+ id);
											}
											else{
												$.messager.alert("提醒", "生成订单失败");
											}
										},
										error:function(){
											loaded();
											$.messager.alert("错误", "生成订单失败");
										}
									});
								}
								else{
									loaded();
									$.messager.confirm("提醒", "该客户存在超账期问题，是否确定生成订单？", function(r){
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
														$("#sales-panel-demandPage").panel('refresh', 'sales/demandViewPage.do?id='+ id);
													}
													else{
														$.messager.alert("提醒", "生成订单失败");
													}
												},
												error:function(){
													loaded();
													$.messager.alert("错误", "生成订单失败");
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
  		});
  		function countTotal(){ //计算合计
    		var rows = $("#sales-datagrid-demandViewPage").datagrid('getRows');
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
    			$("#sales-datagrid-demandViewPage").datagrid('reloadFooter',[{goodsid:'合计', unitnum: unitnum, taxamount: taxamount, notaxamount: notaxamount, tax: tax}]);
    	}
    	function refreshPanel(url){ //更新panel
    		$("#sales-panel-demandPage").panel('refresh', url);
    	}
    	//买赠捆绑分组
    	function groupGoods(){
    	    var rows = $("#sales-datagrid-demandViewPage").datagrid("getRows");
    	    var merges = [];
    	    var groupIDs = "";
    	    for(var i=0;i<rows.length;i++){
    	        var groupid = rows[i].groupid;
    	        if(groupid!=null && groupid!="" && groupIDs.indexOf(groupid)==-1){
    	            groupIDs = groupid+",";
    	            var count = 0;
    	            for(var j=0;j<rows.length;j++){
    	                if(groupid == rows[j].groupid){
    	                    count ++;
    	                }
    	            }
    	            if(count>1){
    	                merges.push({index:i,rowspan:count});
    	            }
    	        }
    	    }
    	    for(var i=0; i<merges.length; i++){
    	    	$("#sales-datagrid-demandViewPage").datagrid('mergeCells',{
    	            index: merges[i].index,
    	            field: 'ck',
    	            rowspan: merges[i].rowspan
    	        });
    	    }
    	}
  	</script>
  </body>
</html>
