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
	  		<input type="hidden" name="brandid" value="${brand}"/>
	  		<input type="hidden" name="branddept" value="${deptid}"/>
	  		<input type="hidden" name="branduser" value="${branduserid}"/>
	  		<input type="hidden" name="supplierid" value="${supplierid}"/>
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
	  					<a href="javaScript:void(0);" id="sales-queay-receiptList" class="button-qr" style="padding-left: 10px;" title="[Alt+Q]查询">查询</a>
						<a href="javaScript:void(0);" id="sales-reload-receiptList" class="button-qr">重置</a>
	  				</td>
	  			</tr>
	  		</table>
	  	</form>
  	</div>
  	<div id="tt" class="easyui-tabs" data-options="fit:true">
	    <div title="商品流水明细" style="padding:2px;">
	      <table id="sales-datagrid-goodsFlowdetail"></table>
	    </div>
	</div>
    <script type="text/javascript">
    	var initQueryJSON = $("#sales-form-receiptListPage").serializeJSON();
    	var SR_footerobject = null;
    	$(function(){
			var salesGoodsFlowDetailListJson = $("#sales-datagrid-goodsFlowdetail").createGridColumnLoad({
				frozenCol : [[
						{field:'idok',checkbox:true,isShow:true}
				]],
				commonCol : [[
					  {field:'businessdate',title:'业务日期',width:70},
					  	<c:if test="${type == '0'}">
							{field:'brandname',title:'商品品牌',width:60},
						</c:if>
						<c:if test="${type == '1'}">
							{field:'branddeptname',title:'品牌部门',width:80},
						</c:if>
						<c:if test="${type == '2'}">
							{field:'brandusername',title:'品牌业务员',width:70},
						</c:if>
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
					  {field:'id',title:'单据编号',width:130},
					  {field:'goodsid',title:'商品编码',width:60},
					  {field:'goodsname',title:'商品名称',width:250},
					  {field:'unitname',title:'单位',width:40},
					  {field:'unitnum',title:'数量',width:60,align:'right',
					  	formatter:function(value,rowData,rowIndex){
					  		if(value!=null &&　value!=0){
					  			return formatterBigNumNoLen(value);
					  		}
			        	}
					  },
					  {field:'price',title:'单价',width:60,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'taxamount',title:'金额',resizable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'isinvoice',title:'抽单状态',align:'right',width:60,
					  	formatter:function(value,rowData,rowIndex){
					  		if(value!=null){
						  		if(value=='0'){
						  			return "未申请";
						  		}else if(value=='1'){
						  			return "已申请";
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
					  {field:'remark',title:'备注',width:80}
		         ]]
			});
			$("#sales-query-goodsid-receiptListPage").widget({
				referwid:'RL_T_BASE_GOODS_INFO',
    			col:'goodsid',
    			singleSelect:true,
    			rows:20,
    			onlyLeafCheck:false
			});
			$("#sales-datagrid-goodsFlowdetail").datagrid({
		 		authority:salesGoodsFlowDetailListJson,
		 		frozenColumns: salesGoodsFlowDetailListJson.frozen,
				columns:salesGoodsFlowDetailListJson.common,
		 		fit:true,
		 		title:"",
		 		method:'post',
		 		rownumbers:true,
		 		//idField:'goodsid',
		 		pagination:true,
	  	 		showFooter: true,
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
				<c:if test="${type == '0'}">
					url:'report/finance/showSalesGoodsFlowDetailDataListByBrand.do',
				</c:if>
				<c:if test="${type == '1'}">
					url:'report/finance/showSalesGoodsFlowDetailDataListByBrandDept.do',
				</c:if>
				<c:if test="${type == '2'}">
					url:'report/finance/showSalesGoodsFlowDetailDataListByBrandUser.do',
				</c:if>
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
		    	},
				onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						SR_footerobject = footerrows[0];
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
    	});
    	function countTotalAmount(){
			var rows =  $("#sales-datagrid-goodsFlowdetail").datagrid('getChecked');
    		var unitnum = 0;
    		var taxamount = 0;
    		for(var i=0;i<rows.length;i++){
    			unitnum = Number(unitnum)+Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
    			taxamount = Number(taxamount)+Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
    		}
    		var foot=[{id:'选中合计',unitnum:unitnum,taxamount:taxamount}];
    		if(null!=SR_footerobject){
        		foot.push(SR_footerobject);
    		}
    		$("#sales-datagrid-goodsFlowdetail").datagrid("reloadFooter",foot);
		}
    </script>
  </body>
</html>
