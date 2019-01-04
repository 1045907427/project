<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>回单交接列表（回收）</title>
	<%@include file="/include.jsp"%>
  </head>
  
  <body>
    <table id="account-datagrid-receiptbyhand"></table>
    <div id="account-toolbar-receiptbyhand" style="padding: 0px">
        <div class="buttonBG" style="height: 28px">
            <security:authorize url="/account/receipthand/doBackReceiptHandsBtn.do">
                <a href="javaScript:void(0);" id="account-back-receiptbyhand" class="easyui-linkbutton" iconCls="button-oppaudit" plain="true" title="回收">回收</a>
            </security:authorize>
			<security:authorize url="/account/receipthand /receiptHandsExport.do">
				<a href="javaScript:void(0);" id="account-export-receiptbyhand" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
			</security:authorize>
        </div>
    	<form action="" id="account-form-receiptbyhand">
    		<table class="querytable" style="padding-top: 0px">
    			<tr>
    				<td>单据日期：</td>
    				<td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"/> 
    					到 <input type="text" name="businessdate2" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"/></td>
    				<td>领 单 人：</td>
    				<td><input type="text" id="account-handuserid-receiptbyhand" name="handuserid"/></td>
                    <td>编&nbsp;&nbsp;号：</td>
                    <td><input type="text" name="billid" style="width: 120px;"/></td>

                </tr>
    			<tr>
    				<td>客户名称：</td>
    				<td><input type="text" id="account-customerid-receiptbyhand" name="customerid" style="width: 225px;"/></td>
    				<td>销售区域：</td>
    				<td><input type="text" id="account-salesarea-receiptbyhand" name="salesarea"/></td>
                    <td>是否回收：</td>
                    <td><select name="isrecycle" style="width: 120px;">
                        <option></option>
                        <option value="0" selected="selected">未回收</option>
                        <option value="1">已回收</option>
                    </select>
                    </td>

    			</tr>
    			<tr>
                    <td>领单日期：</td>
                    <td><input type="text" name="handdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"/>
                        到 <input type="text" name="handdate2" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"/></td>

    				<td>是否收款：</td>
    				<td><select name="status" style="width: 150px;">
    						<option></option>
    						<option value="0" selected="selected">未收款</option>
    						<option value="1">已收款</option>
    					</select>
    				</td>
                    <td>客户业务员：</td>
                    <td><input type="text" id="account-salesuser-receiptbyhand" name="salesuser"/></td>
    			</tr>
    			<tr>
    				<td>总&nbsp;&nbsp;店：</td>
    				<td><input type="text" id="account-pcustomerid-receiptbyhand" name="pcustomerid"/></td>
    				<td>单据类型：</td>
    				<td><select name="billtype" style="width: 150px;">
    						<option></option>
    						<option value="1">发货回单</option>
    						<option value="2">销售退货通知单</option>
    						<option value="3">冲差单</option>
    					</select>
    				</td>
					<td>交接审核人：</td>
					<td><input type="text" id="account-audituserid-receiptbyhand" name="audituserid" style="width: 120px;" /></td>
    				<td colspan="2" style="padding-left:18px">
    					<a href="javaScript:void(0);" id="account-query-receiptbyhand" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="account-reload-receiptbyhand" class="button-qr">重置</a>

    				</td>
    			</tr>
    		</table>
    	</form>
    </div>
    <script type="text/javascript">
    	var RH_footerobject = null;
    	var initQueryJSON = $("#account-form-receiptbyhand").serializeJSON();
    	$(function(){
			var receiptbyhandJson = $("#account-datagrid-receiptbyhand").createGridColumnLoad({
				frozenCol : [[{field:'idok',checkbox:true,isShow:true}]],
				commonCol : [[
					  {field:'id',title:'编号',width:130,hidden:true},
					  {field:'billid',title:'交接单编号',width:130,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
							if(null!=value && "选中合计" != value && "合计" != value){
								return "<a href=\"javascript:void(0);\" onclick=\"javascript:showReceiptHandPage(\'"+value+"\')\">"+value+"</a>";
							}else {
								return value;
							}
	        			}
					  },
					  {field:'relatebillid',title:'单据编号',width:130,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
							if(null!=value){
								return "<a href=\"javascript:void(0);\" onclick=\"javascript:showReceiptPage(\'"+value+"\',\'"+rowData.billtype+"\')\">"+value+"</a>";
							}
	        			}
					  },
					  {field:'saleorderid',title:'订单编号',width:130,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
							if(null!=value){
								return "<a href=\"javascript:void(0);\" onclick=\"javascript:showSaleOrderPage(\'"+value+"\')\">"+value+"</a>";
							}
	        			}
					  },
					  {field:'billtype',title:'单据类型',width:90,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
							return rowData.billtypename;
	        			}
					  },
					  {field:'businessdate',title:'单据日期',width:80,sortable:true},
					  {field:'customerid',title:'客户编码',width:60,sortable:true},
					  {field:'customername',title:'客户名称',width:150,isShow:true},
					  {field:'pcustomerid',title:'总店',width:80,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.pcustomername;
			        	}
					  },
					  {field:'customersort',title:'客户分类',width:80,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.customersortname;
			        	}
					  },
					  {field:'salesarea',title:'销售区域',width:80,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.salesareaname;
			        	}
					  },
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
					  {field:'handuserid',title:'领单人',width:60,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.handusername;
			        	}
					  },
					  {field:'handdate',title:'领单日期',width:70,sortable:true},
					  {field:'amount',title:'应收金额',width:80,align:'right',
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
					  },
					  {field:'isrecycle',title:'是否回收',width:60,
					  	formatter:function(value,rowData,rowIndex){
							return rowData.isrecyclename;
			        	}
					  },
					  {field:'recycledate',title:'回收日期',width:80},
					  {field:'status',title:'回单状态',width:60,
					  	formatter:function(value,rowData,rowIndex){
							return rowData.statusname;
			        	}
					  }
		           ]]
			});
			
			$("#account-datagrid-receiptbyhand").datagrid({ 
		 		authority:receiptbyhandJson,
		 		frozenColumns: receiptbyhandJson.frozen,
				columns:receiptbyhandJson.common,
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		sortName:'billid',
		 		sortOrder:'desc',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
		 		pageSize:500,
				pageList:[20,100,200,500,1000],
				toolbar:'#account-toolbar-receiptbyhand',
				queryParams:initQueryJSON,
				url:'account/receipthand/getReceiptListByHand.do',
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
			$("#account-customerid-receiptbyhand").customerWidget({
				isdatasql:false
			});
			//销售区域
			$("#account-salesarea-receiptbyhand").widget({
				referwid:'RT_T_BASE_SALES_AREA',
				width:150,
    			singleSelect:false,
    			onlyLeafCheck:false
			});
			//总店
			$("#account-pcustomerid-receiptbyhand").widget({
				referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
				width:225,
    			singleSelect:true
			});
			//客户业务员
			$("#account-salesuser-receiptbyhand").widget({
				referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
				width:120,
    			singleSelect:true
			});
			//领单人
			$("#account-handuserid-receiptbyhand").widget({
				name:'t_account_receipt',
                col:'handuserid',
				width:150,
    			singleSelect:true
			});
			//回单交接单审核人
			$('#account-audituserid-receiptbyhand').widget({
                name:'t_account_receipt',
                col:'audituserid',
                width:120,
                singleSelect:true
            });
			
			//查询
			$("#account-query-receiptbyhand").click(function(){
				var queryJSON = $("#account-form-receiptbyhand").serializeJSON();
				$("#account-datagrid-receiptbyhand").datagrid('load',queryJSON);
			});
			//重置
			$("#account-reload-receiptbyhand").click(function(){
				$("#account-customerid-receiptbyhand").customerWidget('clear');
				$("#account-salesarea-receiptbyhand").widget('clear');
				$("#account-pcustomerid-receiptbyhand").widget('clear');
				$("#account-salesuser-receiptbyhand").widget('clear');
				$("#account-handuserid-receiptbyhand").widget('clear');
                $('#account-audituserid-receiptbyhand').widget('clear');
				$("#account-datagrid-receiptbyhand").datagrid('clearChecked');
				$("#account-datagrid-receiptbyhand").datagrid('clearSelections');
				$('#account-form-receiptbyhand')[0].reset();
				var queryJSON = $("#account-form-receiptbyhand").serializeJSON();
				$("#account-datagrid-receiptbyhand").datagrid('load',queryJSON);
			});
			//回收
			$("#account-back-receiptbyhand").click(function(){
				var rows =  $("#account-datagrid-receiptbyhand").datagrid('getChecked');
				if(null == rows || rows.length == 0){
					$.messager.alert("提醒","请勾选要回收的数据!");
					return false;
				}
				$.messager.confirm("提醒","是否确定回收的交接单?",function(r){
					if(r){
						loading("回收中..");
						$.ajax({
				            url :'account/receipthand/doBackReceiptHands.do',
				            type:'post',
				            dataType:'json',
				            data:{billrows:JSON.stringify(rows)},
				            success:function(json){
				            	loaded();
				            	$.messager.alert("提醒","回收成功"+json.sucnum+"条记录;<br>"+json.msg);
				            	if(json.sucnum > 0){
				            		$("#account-datagrid-receiptbyhand").datagrid("reload");
				            	}
				            },
				            error:function(){
				            	loaded();
				            	$.messager.alert("错误","删除出错");
				            }
				        });
					}
				});
			});

			//全局导出
			$("#account-export-receiptbyhand").click(function(){
				var objecr  = $("#account-datagrid-receiptbyhand").datagrid("options");
				var queryParam = JSON.stringify(objecr.queryParams);
				var url = "account/receipthand/exportReceiptbyhandData.do";
				exportByAnalyse(queryParam,"回单交接单据列表","account-datagrid-receiptbyhand",url);
			});
    	});
    	
    	function countTotalAmount(){
    		var rows =  $("#account-datagrid-receiptbyhand").datagrid('getChecked');
    		var amount = 0;
    		for(var i=0;i<rows.length;i++){
    			amount = Number(amount)+Number(rows[i].amount == undefined ? 0 : rows[i].amount);
    		}
    		var foot=[{billid:'选中合计',amount:amount}];
    		if(null!=RH_footerobject){
           		foot.push(RH_footerobject);
       		}
    		$("#account-datagrid-receiptbyhand").datagrid("reloadFooter",foot);
    	}
    	
    	function showReceiptHandPage(receipthandid){
			top.addOrUpdateTab('account/receipthand/showReceiptHandPage.do?type=view&id='+ receipthandid, "交接单查看");
    	}
    	
    	function showReceiptPage(billid,billtype){
    		if("1" == billtype){
    			top.addOrUpdateTab('sales/receiptPage.do?type=edit&id='+billid, '销售发货回单查看');
    		}else if("2" == billtype){
    			top.addOrUpdateTab('sales/rejectBill.do?type=edit&id='+billid, '退货通知单查看');
    		}else if("3" == billtype){
    			top.addOrUpdateTab('account/receivable/showCustomerPushBanlaceListPage.do?id='+billid, '客户应收款冲差');
    		}
    	}
    	
    	function showSaleOrderPage(saleorderid){
    		top.addOrUpdateTab('sales/orderPage.do?type=edit&id='+ saleorderid, "销售订单查看");
    	}
    </script>
  </body>
</html>
