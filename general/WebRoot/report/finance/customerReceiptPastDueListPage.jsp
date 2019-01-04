<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售回单列表页面</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  <body>
  	<form action="" id="sales-form-receiptListPage">
  		<input type="hidden" name="customerid" value="${customerid}"/>
  		<input type="hidden" name="salesuserid" value="${salesuserid}"/>
  		<input type="hidden" name="seq" value="${seq}"/>
  		<input type="hidden" name="iswrite" value="${iswrite}"/>
  		<input type="hidden" name="businessdate1" value="${businessdate1}"/>
  		<input type="hidden" name="businessdate2" value="${businessdate2}"/>
  	</form>
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
		<table id="sales-datagrid-receiptListPage"></table>
		</div>
	</div>
    <script type="text/javascript">
		var SR_footerobject  = null;
    	var initQueryJSON = $("#sales-form-receiptListPage").serializeJSON();
    	$(function(){
			var orderListJson = $("#sales-datagrid-receiptListPage").createGridColumnLoad({
				name :'t_sales_receipt',
				frozenCol : [[
								{field:'idok',checkbox:true,isShow:true}
			    			]],
				commonCol : [[{field:'id',title:'编号',width:120, align: 'left'},
							  {field:'businessdate',title:'业务日期',width:80,align:'left'},
							  {field:'customerid',title:'客户编码',width:60,sortable:true},
							  {field:'customername',title:'客户简称',width:100,sortable:true,isShow:true},
							  {field:'salesdept',title:'销售部门',width:80,align:'left'},
							  {field:'salesuser',title:'客户业务员',width:80,align:'left'},
							  {field:'field01',title:'含税金额',resizable:true,align:'right',
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'field02',title:'未税金额',resizable:true,align:'right',
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'field03',title:'税额',resizable:true,align:'right',
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'duefromdate',title:'应收日期',width:80,align:'left'},
							  <c:if test="${iswrite=='1'}">
							  {field:'canceldate',title:'核销日期',width:80,align:'left'},
							  </c:if>
							  {field:'status',title:'状态',width:60,align:'left',
							  		formatter:function(value,row,index){
						        		return getSysCodeName('status', value);
							        }
							  },
							  {field:'isinvoice',title:'发票状态',width:60,align:'left',
							  		formatter:function(value,row,index){
						        		if(value == "0"){
						        			return "未开票";
						        		}
						        		else if(value == "1"){
						        			return "已开票";
						        		}
						        		else if(value == "2"){
						        			return "已核销";
						        		}
						        		else if(value == "3"){
						        			return "未开票";
						        		}
							        }
							  },
							  {field:'addusername',title:'制单人',width:60,sortable:true},
							  {field:'addtime',title:'制单日期',width:80,sortable:true,hidden:true},
							  {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
							  {field:'audittime',title:'审核日期',width:80,sortable:true,hidden:true}
				              ]]
			});
			$("#sales-datagrid-receiptListPage").datagrid({ 
		 		authority:orderListJson,
		 		frozenColumns: orderListJson.frozen,
				columns:orderListJson.common,
		 		fit:true,
		 		title:"",
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		pagination:true,
	  	 		showFooter: true,
	  	 		pageSize:100,
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		<c:if test="${type=='salesuser'}">
		 		url:'report/finance/showSalesUserPastDueDataList.do',
		 		</c:if>
		 		<c:if test="${type=='customer'}">
		 		url:'report/finance/showCustomerPastDueDataList.do',
		 		</c:if>
		 		queryParams:initQueryJSON,
			    onDblClickRow:function(index, data){
					if (top.$('#tt').tabs('exists','销售发货回单查看')){
						top.closeTab('销售发货回单查看');
					}
					top.addTab('<%=basePath%>/sales/receiptPage.do?type=view&id='+data.id, '销售发货回单查看');
		    	},
				onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						SR_footerobject = footerrows[0];
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
    	});
    	function countTotalAmount(){
			var rows =  $("#sales-datagrid-receiptListPage").datagrid('getChecked');
    		if(null==rows || rows.length==0){
        		var foot=[];
    			if(null!=SR_footerobject){
	        		foot.push(SR_footerobject);
	    		}
    			$("#sales-datagrid-receiptListPage").datagrid("reloadFooter",foot);
        		return false;
    		}
			var field01 = 0;
    		var field02 = 0;
    		var field03=0;
    		
    		for(var i=0;i<rows.length;i++){
    			field01 = Number(field01)+Number(rows[i].field01 == undefined ? 0 : rows[i].field01);
    			field02 = Number(field02)+Number(rows[i].field02 == undefined ? 0 : rows[i].field02);
    			field03 = Number(field03)+Number(rows[i].field03 == undefined ? 0 : rows[i].field03);
    		}
    		var foot=[{customername:'选中合计',field01:field01,field02:field02,field03:field03
        			}];
    		if(null!=SR_footerobject){
        		foot.push(SR_footerobject);
    		}
    		$("#sales-datagrid-receiptListPage").datagrid("reloadFooter",foot);
		}
    </script>
  </body>
</html>
