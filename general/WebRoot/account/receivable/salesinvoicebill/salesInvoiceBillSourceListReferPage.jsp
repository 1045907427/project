<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售开票列表页面</title>
  </head>
  
  <body>
	<table id="account-datagrid-salesInvoiceBillPage"></table>
     <script type="text/javascript">
     	var sourcedata =  JSON.parse('${list}');
    	$(function(){
			$("#account-datagrid-salesInvoiceBillPage").datagrid({
				frozenColumns:[[]],
				columns:[[{field:'id',title:'编号',width:120, align: 'left'},
					{field:'businessdate',title:'业务日期',width:80,align:'left'},
					{field:'saleorderid',title:'订单编号',width:150,align:'left'},
					{field:'customerid',title:'客户编码',width:60,align:'left'},
					{field:'customername',title:'客户名称',width:260,align:'left'},
                    {field:'sourceid',title:'客户单号',width:120,align:'left'},
					{field:'salesdeptname',title:'销售部门',width:60,align:'left',hidden:true},
					{field:'salesusername',title:'客户业务员',width:70,align:'left'},
					{field:'field01',title:'含税金额',resizable:true,align:'right',
						formatter:function(value,row,index){
							return formatterMoney(value);
						}
					},
					{field:'field02',title:'未税金额',resizable:true,align:'right',hidden:true,
						formatter:function(value,row,index){
							return formatterMoney(value);
						}
					},
					{field:'field03',title:'税额',resizable:true,align:'right',hidden:true,
						formatter:function(value,row,index){
							return formatterMoney(value);
						}
					},
					{field:'field04',title:'开票金额',resizable:true,align:'right',
						formatter:function(value,row,index){
							return formatterMoney(value);
						}
					},
					{field:'addusername',title:'制单人',width:60,align:'left'},
					{field:'addtime',title:'制单时间',width:100,align:'left',hidden:true},
					{field:'status',title:'状态',width:60,align:'left',
						formatter:function(value,row,index){
							return getSysCodeName('status', value);
						}
					},
					{field:'isinvoicebill',title:'开票状态',width:60,align:'left',
						formatter:function(value,row,index){
							if(value == "0"){
								return "未开票";
							}else if(value == "1"){
								return "已开票";
							}else if(value == "3"){
								return "未开票";
							}else if(value == "4"){
								return "开票中";
							}
						}
					},
					{field:'isinvoice',title:'抽单状态',width:60,align:'left',
						formatter:function(value,row,index){
							if(value == "0"){
								return "未申请";
							}else if(value == "1"){
								return "已申请";
							}else if(value == "2"){
								return "核销";
							}else if(value == "3"){
								return "未申请";
							}else if(value == "4"){
								return "申请中";
							}else if(value == "5"){
								return "部分核销";
							}
						}
					}
					<c:if test="${invoiceStatus == '2'}">
					<security:authorize url="/account/receivable/editSalesInvoiceBillSave.do">
					,
					{field:'isdel',title:'是否删除',resizable:true,align:'center',
						formatter:function(value,row,index){
							return '<a href="javascript:deleteSalesInvoiceBillSource(\''+row.id+'\',\''+row.sourcetype+'\',\''+index+'\');" style="text-decoration:underline" title="是否从销售开票中删除">删除</a>';
						}
					}
					</security:authorize>
					</c:if>
				]],
		 		fit:true,
		 		height:400,
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:true,
				data: sourcedata,
				onDblClickRow:function(rowIndex, rowData){
					if("${sourcetype}"=="1"){
						top.addOrUpdateTab('sales/receiptPage.do?type=view&id='+ rowData.id, "销售发货回单");
					}else if("${sourcetype}"=="2"){
						top.addOrUpdateTab('sales/rejectBill.do?type=view&id='+ rowData.id, "销售退货通知单");
					}
				}
			}).datagrid("columnMoving");
    	});
    	
    	//sourcetype:1销售回单2销售退货通知单3冲差单
    	function deleteSalesInvoiceBillSource(sourceid,sourcetype,index){
    		$.messager.confirm("提醒","确定删除?",function(r){
    			if(r){
    				var invoiceid = $("#account-hidden-billid").val();
    				if(invoiceid!=""){
						loading("删除中..");
						$.ajax({   
				            url :'account/receivable/deleteSalesInvoiceBillSource.do?invoiceid='+invoiceid+'&sourceid='+sourceid,
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	loaded();
				            	if(json.flag){
				            		$.messager.alert("提醒", "删除成功");
				            		$("#salesinvoice-detail-close-detele").val("1");
				            		$("#account-datagrid-salesInvoiceBillPage").datagrid('deleteRow',index);
				            		var rows = $("#account-datagrid-salesInvoiceBillPage").datagrid('getRows');
				            		//来源单据是否完全删除，完全删除则删除发票
				            		if(rows.length != 0){
				            			$("#account-datagrid-salesInvoiceBillPage").datagrid('loadData',rows);
				            		}else{
										var billtype = $("#account-salesInvoiceBill-hid-billtype").val();
										loading("开票删除中..");
										$.ajax({
											url :'account/receivable/deleteSalesInvoiceBill.do?id='+invoiceid+'&billtype='+billtype,
											type:'post',
											dataType:'json',
											success:function(json){
												loaded();
												if(json.flag){
													var object = $("#account-buttons-salesInvoiceBillPage").buttonWidget("removeData",invoiceid);
													$.messager.alert("提醒", "删除成功");
													parent.closeNowTab();
													$("#account-panel-relation-upper").dialog('close');
												}else{
													$.messager.alert("提醒", "删除失败");
												}
											},
											error:function(){
												loaded();
												$.messager.alert("错误", "删除出错");
											}
										});
				            		}
				            	}else{
				            		$.messager.alert("提醒", "删除失败</br>"+json.msg);
				            	}
				            },
				            error:function(){
				            	loaded();
				            	$.messager.alert("错误", "删除出错");
				            }
				        });
					}
    			}
    		});
    	}
    </script>
  </body>
</html>
