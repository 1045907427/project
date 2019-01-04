<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分客户回笼资金销售回单列表页面</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  <body>
  	<div id="sales-divbtn-receiptListPage">
	  	<form action="" id="sales-form-receiptListPage">
	  		<input type="hidden" name="customerid" value="${customerid}"/>
	  		<input type="hidden" name="salesdept" value="${deptid}"/>
	  		<input type="hidden" name="goodsid" value="${goodsid}"/>
	  		<input type="hidden" name="brandid" value="${brand}"/>
	  		<input type="hidden" name="salesuser" value="${salesuserid}"/>
	  		<input type="hidden" name="businessdate1" value="${businessdate1}"/>
	  		<input type="hidden" name="businessdate2" value="${businessdate2}"/>
	  		<table cellpadding="0" cellspacing="0" border="0">
	  			<tr>
	  				<td>单据类型:</td>
	  				<td><select style="width: 130px;" name="billtype">
						<option ></option>
						<option value="1">发货单</option>
						<option value="2">直退退货单</option>
						<option value="3">售后退货单</option>
						<option value="4">冲差单</option>
					</select></td>
	  				<td>商品:</td>
	  				<td><input name="goodsid" id="sales-query-goodsid-receiptListPage" style="width: 130px;"/></td>
	  				<td>
	  					&nbsp;<a href="javaScript:void(0);" id="sales-queay-receiptList" class="button-qr" style="padding-left: 10px;">查询</a>
						<a href="javaScript:void(0);" id="sales-reload-receiptList" class="button-qr">重置</a>
	  				</td>
	  			</tr>
	  		</table>
	  	</form>
  	</div>
    <div id="sales-import-tab" class="buttonBG">
        <a href="javaScript:void(0);" id="report-buttons-unauditamount" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
    </div>
  	<div id="tt" class="easyui-tabs" data-options="fit:true">
	    <div title="未验收应收款" <c:if test="${type=='0'}">data-options="selected:true"</c:if> style="padding:2px;">
	       <table id="sales-datagrid-unauditamount"></table>
	    </div>
	    <div title="已验收应收款" <c:if test="${type=='1'}">data-options="selected:true"</c:if> style="padding:2px;">
	        <table id="sales-datagrid-auditamount"></table>
	    </div>
	    <div title="退货应收款" <c:if test="${type=='2'}">data-options="selected:true"</c:if> style="padding:2px;">
	      <table id="sales-datagrid-rejectamount"></table>
	    </div>
	    <div title="商品流水明细" <c:if test="${type=='3'}">data-options="selected:true"</c:if> style="padding:2px;">
	      <table id="sales-datagrid-goodsFlowdetail"></table>
	    </div>
	</div>
    <script type="text/javascript">
    $('#tt').tabs({
        tools:'#sales-import-tab'
    });
    	$(function(){
    		var initQueryJSON = $("#sales-form-receiptListPage").serializeJSON();
			var unauditamountListJson = $("#sales-datagrid-unauditamount").createGridColumnLoad({
				name :'t_sales_receipt',
				frozenCol : [[
			    			]],
				commonCol : [[{field:'id',title:'编号',width:60, align: 'left'},
							  {field:'businessdate',title:'业务日期',width:70,align:'left'},
							  {field:'customerid',title:'客户编码',width:60,sortable:true},
							  {field:'customername',title:'客户名称',width:210,sortable:true,isShow:true},
							  {field:'salesdept',title:'销售部门',width:80,align:'left'},
							  {field:'salesuser',title:'客户业务员',width:60,align:'left'},
							  {field:'field01',title:'含税金额',width:60,align:'right',
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'field02',title:'未税金额',width:60,align:'right',hidden:true,
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'field03',title:'税额',width:60,align:'right',hidden:true,
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'duefromdate',title:'应收日期',width:70,align:'left'},
							  <c:if test="${iswrite=='1'}">
							  {field:'canceldate',title:'核销日期',width:70,align:'left'},
							  </c:if>
							  {field:'status',title:'状态',width:60,align:'left',
							  		formatter:function(value,row,index){
						        		return getSysCodeName('status', value);
							        }
							  },
							  {field:'isinvoice',title:'抽单状态',width:60,align:'left',
							  		formatter:function(value,row,index){
						        		if(value == "0"){
						        			return "未申请";
						        		}else if(value == "1"){
						        			return "已申请";
						        		}else if(value == "2"){
						        			return "已核销";
						        		}else if(value == "3"){
						        			return "未申请";
						        		}else if(value == "4"){
						        			return "申请中";
						        		}else if(value == "5"){
						        			return "部分核销";
						        		}
							        }
							  },
							  {field:'addusername',title:'制单人',width:80,sortable:true},
							  {field:'addtime',title:'制单日期',width:70,sortable:true,hidden:true},
							  {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
							  {field:'audittime',title:'审核日期',width:70,sortable:true,hidden:true}
				              ]]
			});

			var auditamountListJson = $("#sales-datagrid-auditamount").createGridColumnLoad({
				name :'t_sales_receipt',
				frozenCol : [[
			    			]],
				commonCol : [[{field:'id',title:'编号',width:60, align: 'left'},
							  {field:'businessdate',title:'业务日期',width:70,align:'left'},
							  {field:'customerid',title:'客户名称',width:210,align:'left',
							  		formatter:function(value,row,index){
						        		return row.customername;
							        }
							  },
							  {field:'salesdept',title:'销售部门',width:80,align:'left'},
							  {field:'salesuser',title:'客户业务员',width:60,align:'left'},
							  {field:'field01',title:'客户验收金额',width:80,align:'right',
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'field02',title:'客户验收未税金额',width:100,align:'right',hidden:true,
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'field03',title:'客户验收税额',width:80,align:'right',hidden:true,
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'field04',title:'回单金额',width:70,align:'right',
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'duefromdate',title:'应收日期',width:70,align:'left'},
							  <c:if test="${iswrite=='1'}">
							  {field:'canceldate',title:'核销日期',width:70,align:'left'},
							  </c:if>
							  {field:'status',title:'状态',width:60,align:'left',
							  		formatter:function(value,row,index){
						        		return getSysCodeName('status', value);
							        }
							  },
							  {field:'isinvoice',title:'抽单状态',width:60,align:'left',
							  		formatter:function(value,row,index){
						        		if(value == "0"){
						        			return "未申请";
						        		}else if(value == "1"){
						        			return "已申请";
						        		}else if(value == "2"){
						        			return "已核销";
						        		}else if(value == "3"){
						        			return "未申请";
						        		}else if(value == "4"){
						        			return "申请中";
						        		}else if(value == "5"){
						        			return "部分核销";
						        		}
							        }
							  },
							  {field:'addusername',title:'制单人',width:80,sortable:true},
							  {field:'addtime',title:'制单日期',width:70,sortable:true,hidden:true},
							  {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
							  {field:'audittime',title:'审核日期',width:70,sortable:true,hidden:true}
				              ]]
			});
			var rejectamountListJson = $("#sales-datagrid-rejectamount").createGridColumnLoad({
				name :'t_storage_salereject_enter',
				frozenCol : [[
			    			]],
				commonCol : [[{field:'id',title:'编号',width:60, align: 'left'},
							  {field:'businessdate',title:'业务日期',width:70,align:'left'},
							  {field:'sourceid',title:'来源单据编号',width:80, align: 'left'},
							  {field:'customerid',title:'客户名称',width:210,align:'left',
							  		formatter:function(value,row,index){
						        		return row.customername;
							        }
							  },
							  {field:'salesdept',title:'销售部门',width:80,align:'left'},
							  {field:'salesuser',title:'客户业务员',width:60,align:'left'},
							  {field:'field01',title:'含税金额',width:60,align:'right',
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'field02',title:'未税金额',width:60,align:'right',hidden:true,
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'field03',title:'税额',width:40,align:'right',hidden:true,
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  <c:if test="${iswrite=='1'}">
							  {field:'canceldate',title:'核销日期',width:70,align:'left'},
							  </c:if>
							  {field:'status',title:'状态',width:40,align:'left',
							  		formatter:function(value,row,index){
						        		return getSysCodeName('status', value);
							        }
							  },
							  {field:'iswrite',title:'是否核销',width:60,align:'left',
							  		formatter:function(value,row,index){
						        		if(null != value && "" != value){
						        			if("1" == value){
						        				return "是";
						        			}
						        			else if("0" == value){
						        				return "否";
						        			}
						        		}
							        }
							  },
							  {field:'addusername',title:'制单人',width:80,sortable:true},
							  {field:'addtime',title:'制单日期',width:70,sortable:true,hidden:true},
							  {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
							  {field:'audittime',title:'审核日期',width:70,sortable:true,hidden:true}
				              ]]
			});
			var salesGoodsFlowDetailListJson = $("#sales-datagrid-goodsFlowdetail").createGridColumnLoad({
				frozenCol : [[]],
				commonCol : [[
					  {field:'businessdate',title:'业务日期',width:70},
					  {field:'customername',title:'客户名称',width:210},
					  {field:'billtype',title:'单据类型',width:70,
					  	formatter:function(value,rowData,rowIndex){
			        		if(value=='1'){
			        			return "发货单";
			        		}else if(value=='2'){
			        			return "直退退货单";
			        		}else if(value=='3'){
			        			return "售后退货单";
			        		}else if(value=='4'){
			        			return "冲差单";
			        		}
			        	}
					  },
					  {field:'id',title:'单据编号',width:80},
					  {field:'goodsid',title:'商品编码',width:60},
					  {field:'goodsname',title:'商品名称',width:250},
					  {field:'unitname',title:'单位',width:40},
					  {field:'price',title:'单价',width:40,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'unitnum',title:'数量',width:60,align:'right',
					  	formatter:function(value,rowData,rowIndex){
					  		if(value!=null && value != 0){
					  			return formatterNum(value);
					  		}
			        	}
					  },
					  {field:'taxamount',title:'商品金额',width:60,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'isinvoice',title:'抽单状态',align:'right',width:60,
					  	formatter:function(value,rowData,rowIndex){
					  		if(value!=null){
					  			if(value == "0"){
				        			return "未申请";
				        		}else if(value == "1"){
				        			return "已申请";
				        		}else if(value == "2"){
				        			return "已核销";
				        		}else if(value == "3"){
				        			return "未申请";
				        		}else if(value == "4"){
				        			return "申请中";
				        		}else if(value == "5"){
				        			return "部分核销";
				        		}
					  		}else{
					  			return "";
					  		}
			        	}
					  },
					  {field:'iswriteoff',title:'核销状态',align:'right',width:60,isShow:true,
					  	formatter:function(value,rowData,rowIndex){
			        		if(value=='1'){
					  			return "已核销";
					  		}else if(value=='0'){
					  			return "未核销";
					  		}
			        	}
					  },
					  {field:'remark',title:'备注',width:60}
		         ]]
			});
			$("#sales-query-goodsid-receiptListPage").widget({
				referwid:'RL_T_BASE_GOODS_INFO',
    			col:'goodsid',
    			singleSelect:false,
    			rows:20,
    			onlyLeafCheck:false
			});
			$("#sales-datagrid-unauditamount").datagrid({
		 		authority:unauditamountListJson,
		 		frozenColumns: unauditamountListJson.frozen,
				columns:unauditamountListJson.common,
		 		fit:true,
		 		title:"",
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:true,
		 		pagination:true,
		 		pageSize:100,
	  	 		showFooter: true,
	  	 		singleSelect:true,
		 		url:'report/finance/showUnauditamountDataList.do',
		 		queryParams:initQueryJSON,
			    onDblClickRow:function(index, data){
					if (top.$('#tt').tabs('exists','销售发货回单查看')){
						top.closeTab('销售发货回单查看');
					}
					top.addTab('<%=basePath%>/sales/receiptPage.do?type=view&id='+data.id, '销售发货回单查看');
		    	}
			}).datagrid("columnMoving");
			$("#sales-datagrid-auditamount").datagrid({
		 		authority:auditamountListJson,
		 		frozenColumns: auditamountListJson.frozen,
				columns:auditamountListJson.common,
		 		fit:true,
		 		title:"",
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:true,
		 		pagination:true,
		 		pageSize:100,
	  	 		showFooter: true,
	  	 		singleSelect:true,
		 		url:'report/finance/showAuditamountDataList.do',
		 		queryParams:initQueryJSON,
			    onDblClickRow:function(index, data){
					if (top.$('#tt').tabs('exists','销售发货回单查看')){
						top.closeTab('销售发货回单查看');
					}
					top.addTab('<%=basePath%>/sales/receiptPage.do?type=view&id='+data.id, '销售发货回单查看');
		    	}
			}).datagrid("columnMoving");
			$("#sales-datagrid-rejectamount").datagrid({
		 		authority:rejectamountListJson,
		 		frozenColumns: rejectamountListJson.frozen,
				columns:rejectamountListJson.common,
		 		fit:true,
		 		title:"",
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:true,
		 		pagination:true,
		 		pageSize:100,
	  	 		showFooter: true,
	  	 		singleSelect:true,
		 		url:'report/finance/showRejectamountDataList.do',
		 		queryParams:initQueryJSON,
			    onDblClickRow:function(index, data){
					if (top.$('#tt').tabs('exists','销售退货入库单查看')){
						top.closeTab('销售退货入库单查看');
					}
					top.addTab('<%=basePath%>/storage/showSaleRejectEnterViewPage.do?type=view&id='+data.id, '销售退货入库单查看');
		    	}
			}).datagrid("columnMoving");
			$("#sales-datagrid-goodsFlowdetail").datagrid({
		 		authority:salesGoodsFlowDetailListJson,
		 		frozenColumns: salesGoodsFlowDetailListJson.frozen,
				columns:salesGoodsFlowDetailListJson.common,
		 		fit:true,
		 		title:"",
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:true,
		 		pagination:true,
		 		pageSize:100,
	  	 		showFooter: true,
	  	 		singleSelect:true,
		 		url:'report/finance/showSalesGoodsFlowDetailDataList.do',
		 		queryParams:initQueryJSON,
		 		toolbar:'#sales-divbtn-receiptListPage',
			    onDblClickRow:function(index, data){
					var str = "", url = "";
			    	if(data.billtype=='1'){
			    		str = "发货单查看";
			    		url = "/storage/showSaleOutViewPage.do?id="+data.id;
	        		}else if(data.billtype=='2' || data.billtype=='3'){
	        			str = "销售退货入库单查看";
	        			url = "/storage/showSaleRejectEnterViewPage.do?type=view&id="+data.id;
	        		}else if(data.billtype=='4'){
	        			str = "客户应收款冲差";
	        			url = "/account/receivable/showCustomerPushBanlaceListPage.do?id="+data.id;
	        		}
					if (top.$('#tt').tabs('exists',str)){
						top.closeTab(str);
					}
					top.addTab('<%=basePath%>'+url+'',str);
		    	}
			}).datagrid("columnMoving");
			
			//回车事件
			controlQueryAndResetByKey("sales-queay-receiptList","sales-reload-receiptList");
			
			//查询
			$("#sales-queay-receiptList").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#sales-form-receiptListPage").serializeJSON();
	      		$("#sales-datagrid-goodsFlowdetail").datagrid("load",queryJSON);
			});
			//重置
			$("#sales-reload-receiptList").click(function(){
				$("#sales-query-goodsid-receiptListPage").goodsWidget('clear');
				$("#sales-form-receiptListPage").form("reset");
				var queryJSON = $("#sales-form-receiptListPage").serializeJSON();
	       		$("#sales-datagrid-goodsFlowdetail").datagrid("load",queryJSON);
			});
            //导出
            $("#report-buttons-unauditamount").Excel('export',{
                type:'exportUserdefined',
                name:'分客户资金应收明细',
                url:'report/finance/exportFundsCustomerList.do?customerid=${customerid}&businessdate1=${businessdate1}&businessdate2=${businessdate2}'
            });
    	});
    </script>
  </body>
</html>
