<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>出入库流水账</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-inOutFlowList"></table>
    	<div id="report-toolbar-inOutFlowList" style="padding: 0px">
            <div class="buttonBG" >
                <security:authorize url="/report/storage/inOutFlowListExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-inOutFlowListPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-inOutFlowList" method="post">
    		<table class="querytable">
    			<tr>
    				<td>日期:</td>
    				<td><input type="text" name="businessdate1" value="${today }" style="width:90px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:90px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>单据编号:</td>
    				<td><input type="text" name="id" style="width: 150px;"/></td>
    				<td>品　牌:</td>
    				<td><input type="text" id="report-query-brandid" name="brandid" /></td>
    			</tr>
    			<tr>
    				<td>商品:</td>
    				<td><input type="text" id="report-query-goodsid" name="goodsid"/></td>
    				<td>单据名称:</td>
    				<td>
    					<select  id="report-combobox-billtype" name="billtype" style="width: 150px;" class="easyui-combobox" data-options="multiple:true,onLoadSuccess:function(){$(this).combobox('clear');}">
                            <option></option>
    						<option value="0">采购入库单</option>
    						<option value="1">销售退货入库单</option>
    						<option value="2">其他入库单</option>
    						<option value="3">调拨单</option>
    						<option value="4">报溢调账单</option>
    						<option value="41">报损调账单</option>
    						<option value="5">发货单</option>
    						<option value="6">采购退货出库单</option>
    						<option value="7">其他出库单</option>
    					</select>
    				</td>
                    <td>供应商:</td>
                    <td><input type="text" id="report-query-supplierid" name="supplierid" style="width: 210px;"/></td>
                </tr>
                <tr>
    				<td>客户:</td>
    				<td><input type="text" id="report-query-customerid" name="customerid" style="width: 204px;"/></td>
    				<td>仓　　库:</td>
    				<td><input type="text" id="report-query-storageid" name="storageid" /></td>
    				<td>客户分类:</td>
					<td><input type="text" id="report-query-customersort" name="customersort" /></td>
    			</tr>
				<tr>
					<td colspan="4"></td>
					<td colspan="2">
						<div style="margin-left: 60px;">
							<a href="javaScript:void(0);" id="report-queay-inOutFlowList" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="report-reload-inOutFlowList" class="button-qr">重置</a>
						</div>
					</td>
				</tr>
    		</table>
    	</form>
    	</div>

        <script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-inOutFlowList").serializeJSON();
    		var checkListJson = $("#report-datagrid-inOutFlowList").createGridColumnLoad({
				frozenCol : [[
				]],
				commonCol : [[
					{field:'storageid',title:'仓库名称',width:60,sortable:true,
						formatter:function(value,rowData,rowIndex){
							return rowData.storagename;
						}
					},
					{field:'businessdate',title:'业务日期',width:75,sortable:true},
					{field:'billtype',title:'单据名称',width:80,sortable:true,
						formatter:function(value,rowData,rowIndex){
							if(value=='0'){
								return "采购入库单";
							}else if(value=='1'){
								return "销售退货入库单";
							}else if(value=='2'){
								return "其他入库单";
							}else if(value=='3'){
								return "调拨单";
							}else if(value=='4'){
								return "报溢调账单 ";
							}else if(value=='41'){
								return "报损调账单 ";
							}else if(value=='5'){
								return "发货单";
							}else if(value=='6'){
								return "采购退货出库单";
							}else if(value=='7'){
								return "其他出库单";
							}
						}
					},
					{field:'id',title:'单据编号',width:130,sortable:true},
					{field:'customerid',title:'客户编码',width:60,sortable:true},
					{field:'customername',title:'客户名称',width:210,aliascol:'goodsid'},
					{field:'customersortname',title:'客户分类',width:80,hidden:true},
					{field:'supplierid',title:'供应商编码',width:70,sortable:true,hidden:true},
					{field:'suppliername',title:'供应商名称',width:210,aliascol:'goodsid',hidden:true},
					{field:'goodsid',title:'商品编码',width:60,sortable:true},
					{field:'goodsname',title:'商品名称',width:150,aliascol:'goodsid'},
					{field:'barcode',title:'条形码',width:90},
					{field:'spell',title:'助记符',width:90,hidden:true},
					{field:'brandname',title:'品牌名称',width:60,aliascol:'goodsid'},
					{field:'boxnum',title:'箱装量',width:60,aliascol:'goodsid',
						formatter:function(value,rowData,rowIndex){
							return formatterBigNumNoLen(value);
						}
					},
					{field:'unitname',title:'主单位',width:40},
					{field:'enternum',title:'入库数量',width:60,align:'right',sortable:true,
						formatter:function(value,rowData,rowIndex){
							if(value!=0){
								return formatterBigNumNoLen(value);
							}else{
								return "";
							}
						}
					},
					{field:'auxenternumdetail',title:'入库辅数量',width:70,sortable:true,align:'right',aliascol:'enternum'},
					{field:'outnum',title:'出库数量',width:60,align:'right',sortable:true,
						formatter:function(value,rowData,rowIndex){
							if(value!=0){
								return formatterBigNumNoLen(value);
							}else{
								return "";
							}
						}
					},
					{field:'auxoutnumdetail',title:'出库辅数量',width:70,sortable:true,align:'right',aliascol:'outnum'}
					<c:if test="${map.price == 'price'}">
					,
					{field:'price',title:'单价',width:60,align:'right'}
					</c:if>
					<c:if test="${map.amount == 'amount'}">
					,
					{field:'amount',title:'金额',resizable:true,align:'right',aliascol:'amount',
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},

					<security:authorize url="/report/storage/inOutFlowListStoragePrice.do">
					{field:'storagePrice',title:'成本单价',width:60,align:'right',hidden:true},
					{field:'storagePriceAmount',title:'成本金额',resizable:true,align:'right',aliascol:'storagePriceAmount',hidden:true,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					</security:authorize>


					{field:'remark',title:'备注',width:60,align:'right'}
					</c:if>
				]]
			});
    		$(function(){
    			$("#report-datagrid-inOutFlowList").datagrid({ 
					authority:checkListJson,
		 			frozenColumns: checkListJson.frozen,
					columns:checkListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
                    idField:'id',
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
		  	 		pageSize:100,
					sortName:'businessdate',
					sortOrder:'desc',
                    queryParams:initQueryJSON,
					toolbar:'#report-toolbar-inOutFlowList'
				}).datagrid("columnMoving");
				$("#report-query-storageid").widget({
					referwid:'RL_T_BASE_STORAGE_INFO',
		    		width:150,
					singleSelect:true
				});
				$("#report-query-goodsid").widget({
					referwid:'RL_T_BASE_GOODS_INFO',
		    		width:204,
					singleSelect:false
				});
				$('#report-query-supplierid').supplierWidget({});

				$("#report-query-customerid").customerWidget({});
				
				$("#report-query-brandid").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
		    		width:210,
					singleSelect:false
				});
				//客户分类
				$("#report-query-customersort").widget({
					referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
					width:210,
					singleSelect:false,
					onlyLeafCheck:false
				});
				
				//查询
				$("#report-queay-inOutFlowList").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-inOutFlowList").serializeJSON();
		      		$("#report-datagrid-inOutFlowList").datagrid({
		      			url: 'report/storage/showInOutFlowListData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-inOutFlowList").click(function(){
                    $("#report-query-form-inOutFlowList").form("reset");
                    $("#report-combobox-billtype").val("");
					$("#report-query-storageid").widget("clear");
					$("#report-query-goodsid").widget("clear");
					$("#report-query-brandid").widget('clear');
					$("#report-query-customersort").widget('clear');
					$("#report-combobox-billtype").val();
                    var queryJSON = $("#report-query-form-inOutFlowList").serializeJSON();
                    $("#report-datagrid-inOutFlowList").datagrid('load', queryJSON);
				});
				
				$("#report-buttons-inOutFlowListPage").Excel('export',{
					queryForm: "#report-query-form-inOutFlowList", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'出入库流水账',
			 		url:'report/storage/exportInOutFlowListData.do'
				});
    		});
    	</script>
  </body>
</html>
