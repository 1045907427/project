<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>采购计划分析表</title>
	<%@include file="/include.jsp" %>
	<script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>

<body>
<div id="purchase-analysisReport-layout" class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north'">
		<div class="buttonBG">
			<security:authorize url="/report/buy/createPlannedOrderByAnalysisReport.do">
				<a href="javaScript:void(0);" id="purchase-addplanorder-analysisReport" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-audit'" title="生成采购计划单">生成采购计划单</a>
			</security:authorize>
			<security:authorize url="/report/buy/createBuyOrderByAnalysisReport.do">
				<a href="javaScript:void(0);" id="purchase-addbuyorder-analysisReport" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-audit'" title="生成采购订单">生成采购订单</a>
			</security:authorize>
			<security:authorize url="/report/buy/exportPlannedOrderAnalysisReportData.do">
				<a href="javaScript:void(0);" id="purchase-buttons-analysisReportExport" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
			</security:authorize>
		</div>
	</div>
	<div data-options="region:'center'" >
		<table id="purchase-datagrid-analysisReport"></table>
		<div id="purchase-toolbar-analysisReport" style="padding:2px;height:auto">
			<form action="" id="purchase-query-form-analysisReport" method="post">
				<table class="querytable">
					<tr>
						<td>同期日期:</td>
						<td>
							<input type="text" id="purchase-query-tqstartdate" name="tqstartdate" value="${prevyearfirstday }" style="width:100px;" class="Wdate easyui-validatebox" data-options="required:true" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input id="purchase-query-tqenddate" type="text" name="tqenddate" value="${prevyearcurday }" class="Wdate easyui-validatebox" data-options="required:true" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
						<td>品牌名称:</td>
						<td><input type="text" id="purchase-query-brandid" name="brandid"/></td>
						<td>供应商:</td>
						<td ><input type="text" id="purchase-query-supplier" name="supplierid" style="width: 212px;"/></td>
						<td>
							库存参考:
							<select id="purchase-query-referStorageDataNumBy" name="referStorageDataNumBy" style="width:100px;">
								<option value="1">库存现存量</option>
								<option value="2">库存可用量</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>前期日期:</td>
						<td ><input type="text" id="purchase-query-qqstartdate" name="qqstartdate" value="${firstDay }" style="width:100px;" class="Wdate easyui-validatebox" data-options="required:true" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input id="purchase-query-qqenddate" type="text" name="qqenddate" value="${yestoday }" class="Wdate easyui-validatebox" data-options="required:true" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
						<td>日期参考:</td>
						<td>
                                <span>
                                    <select id="purchase-query-referDateBy" name="referDateBy" style="width:70px;">
										<option value="1">同期日期</option>
										<option value="2" selected="selected">前期日期</option>
									</select>
                                    可购商品:
                                    <select name="canBuySale" style="width: 47px;">
										<option value="">全部</option>
										<option value="1" selected="selected">是</option>
										<option value="4">否</option>
									</select>
                                </span>
						</td>
						<td>可订量筛选:</td>
						<td>
                                <span>
                                    <select name="canordernumFilter">
										<option value="">全部</option>
										<option value="1">大于零</option>
										<option value="2">小于等于零</option>
									</select>
                                    只计算发货仓:
                                    <select id="purchase-query-isSendsStorage" name="isSendsStorage" style="width:55px;">
										<option value="1">是</option>
										<option value="0">否</option>
									</select>
                                </span>
						</td>
						<td>销售数据来源:
						<select name="salesdatatype" style="width:75px;">
							<option value="0">系统销售</option>
							<option value="1">数据上报</option>
							<option value="2">大宗销售</option>
						</select>
						</td>
					</tr>
					<tr>
						<td>商品名称:</td>
						<td><input type="text" id="purchase-query-goodsid" name="goodsid" style="width: 220px;"/></td>
						<td>商品分类:</td>
						<td><input type="text" id="purchase-query-goodssort" name="goodssort" style="width: 176px;"/></td>
						<td>仓库:</td>
						<td><input type="text" id="purchase-query-storageid" name="storageid" style="width: 212px;"/></td>
						<td class="tdbutton">
							&nbsp;&nbsp;&nbsp;<a href="javaScript:void(0);" id="purchase-queay-analysisReport" class="button-qr">查询</a>
							&nbsp;&nbsp;&nbsp;<a href="javaScript:void(0);" id="purchase-reload-analysisReport" class="button-qr">重置</a>

						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div data-options="region:'south',border:false,tools:'#brandStoragePanel-tools',headerCls:'awinpanel-header'" title="按品牌合计库存" style="height:180px;" >
		<table id="purchase-datagrid-storageSummaryReport"></table>
	</div>
</div>
<div style="display:none">
	<div id="brandStoragePanel-tools">
		<a href="javascript:void(0)" id="brandStoragePanel-tools-reload" class="icon-reload" title="刷新"></a>
	</div>
	<div id="purchase-query-showPurchaseFlowList">
		<div id="purchase-table-query-PurchaseFlow-toolbar" style="padding:5px;height:auto">
			<form action="" id="purchase-form-PurchaseFlowList" method="post">
				<table>
					<tr>
						<td>业务日期:</td>
						<td><input type="text" name="businessdatestart" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdateend" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"/></td>
						<td>单据编号:</td>
						<td><input type="text" name="id" style="width: 150px;"/></td>
						<td>
							<input type="checkbox" name="invoice1" value="1" checked="checked"/>已核销
							<input type="checkbox" name="invoice0" value="0" checked="checked"/>未核销
							<input type="checkbox" name="billtype0" value="0" checked="checked"/>进货单
							<input type="checkbox" name="billtype1" value="1" checked="checked"/>退货单
						</td>
						<td>
							<a href="javaScript:void(0);" id="purchase-btn-queryPurchaseFlowList" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="purchase-btn-reloadPurchaseFlowList" class="button-qr">重置</a>
						</td>
					</tr>
				</table>
				<input type="hidden" name="goodsid" id="purchase-goodsid-queryPurchaseFlowList"/>
			</form>
		</div>
		<table id="purchase-datagrid-showPurchaseFlowList" ></table>
	</div>
</div>
<script type="text/javascript">
	var SR_footerobject  = null;
	var pageDefaultSort='goodsid';
	var pageDefaultOrder='asc';
	var initQueryJSON = $("#purchase-query-form-analysisReport").serializeJSON();
	var checkListJson = $("#purchase-datagrid-analysisReport").createGridColumnLoad({
		frozenCol : [[
			{field:'ck',checkbox:true,width:50}

		]],
		commonCol : [[
            {field:'goodsid',title:'商品编码',width:60,sortable:true},
			{field:'goodsname',title:'商品名称',width:200,aliascol:'goodsid',
				formatter:function(value,rowData,rowIndex){
					if(null!=rowData.goodsid){
						return "<a href=\"javascript:void(0);\" onclick=\"javascript:showPurchaseFlowList('"+rowData.goodsid+"')\">"+value+"</a>";
					}
				}
			},
			{field:'barcode',title:'条形码',sortable:true,width:90},
			{field:'goodssort',title:'商品分类',sortable:true,width:90,
				formatter:function(value,rowData,rowIndex){
					return rowData.goodssortname;
				}
			},
			{field:'brandname',title:'品牌名称',width:70,aliascol:'goodsid'},
			{field:'boxnum',title:'箱装量',width:50,align:'right',
				formatter:function(value,rowData,rowIndex){
					return formatterBigNumNoLen(value);
				}
			},
			{field:'unitname',title:'主单位',width:50},
			{field:'auxunitname',title:'单位',width:50},
			{field:'buyprice',title:'采购单价',width:70,align:'right',hidden:true,
				formatter:function(value,rowData,rowIndex){
					return formatterMoney(value);
				}
			},
			{field:'boxamount',title:'箱价',width:50,sortable:true,align:'right',
				formatter:function(value,rowData,rowIndex){
					return formatterMoney(value);
				}
			},
			{field:'existingunitnum',title:'实际数量',width:80,align:'right',sortable:true},
			{field:'existingnum',title:'实际库存',width:80,align:'right',sortable:true,
				formatter:function(value,rowData,rowIndex){
					if(value !=null && $.trim(value)!=""){
						return value+rowData.auxunitname;
					}else{
						return "";
					}
				}
			},
			{field:'existingamount',title:'实际库存金额',width:80,align:'right',sortable:true,
				formatter:function(value,rowData,rowIndex){
					return formatterMoney(value);
				}
			},
			{field:'transitnum',title:'在途量',width:80,align:'right',sortable:true,
				formatter:function(value,rowData,rowIndex){
					if(value !=null && $.trim(value)!=""){
						return value+rowData.auxunitname;
					}else{
						return "";
					}
				}
			},
			{field:'transitamount',title:'在途金额',width:80,align:'right',sortable:true,
				formatter:function(value,rowData,rowIndex){
					return formatterMoney(value);
				}
			},
			{field:'curstoragenum',title:'本期存货',width:80,align:'right',sortable:true,
				formatter:function(value,rowData,rowIndex){
					if(value !=null && $.trim(value)!=""){
						return value+rowData.auxunitname;
					}else{
						return "";
					}
				}
			},
			{field:'curstorageamount',title:'本期库存金额',width:80,align:'right',sortable:true,
				formatter:function(value,rowData,rowIndex){
					return formatterMoney(value);
				}
			},
			{field:'tqsaleunitnum',title:'同期销售主单位数量',width:100,align:'right',sortable:true,hidden:true,
				formatter:function(value,rowData,rowIndex){
					if(value !=null && $.trim(value)!=""){
                        var a=formatterBigNumNoLen(value);
                        if(a!=0) {
                            return a + rowData.unitname;
                        }
					}else{
						return "";
					}
				}
			},
			{field:'tqsalenum',title:'同期销售数量',width:80,align:'right',sortable:true,
				formatter:function(value,rowData,rowIndex){
					if(value !=null && $.trim(value)!=""){
						return value+rowData.auxunitname;
					}else{
						return "";
					}
				}
			},
			{field:'tqsaleamount',title:'同期销售金额',width:80,align:'right',sortable:true,hidden:true,
				formatter:function(value,rowData,rowIndex){
					return formatterMoney(value);
				}
			},
			{field:'qqsaleunitnum',title:'前期销售主单位数量',width:100,align:'right',sortable:true,hidden:true,
				formatter:function(value,rowData,rowIndex){
					if(value !=null && $.trim(value)!=""){
                        var a=formatterBigNumNoLen(value);
                        if(a!=0) {
                            return a + rowData.unitname;
                        }
					}else{
						return "";
					}
				}
			},
			{field:'qqsalenum',title:'前期销售数量',width:80,align:'right',sortable:true,
				formatter:function(value,rowData,rowIndex){
					if(value !=null && $.trim(value)!=""){
						return value+rowData.auxunitname;
					}else{
						return "";
					}
				}
			},
			{field:'qqsaleamount',title:'前期销售金额',width:80,align:'right',sortable:true,hidden:true,
				formatter:function(value,rowData,rowIndex){
					return formatterMoney(value);
				}
			},
			{field:'canordernum',title:'可订量',width:70,align:'right',sortable:true,
				formatter:function(value,rowData,rowIndex){
					if(value>0){
						return value+rowData.auxunitname;
					}else{
						return "";
					}
				}
			},
			{field:'orderunitnum',title:'本次采购主单位数量',width:120,align:'right',hidden:true,
				formatter:function(value,rowData,rowIndex){
					if(value>0){
						return value+rowData.unitname;
					}else{
						return "";
					}
				}
			},
			{field:'ordernum',title:'本次采购箱数',width:80,align:'right',sortable:true,
				formatter:function(value,rowData,rowIndex){
					if(rowData.goodsInfo!=null &&
							rowData.goodsInfo.bstype=='3'){
						return "商品限购(只可销售)";
					}
					if(value>0){
						return formatterNum(value)+rowData.auxunitname;
					}else{
						return "";
					}
				},
				editor:{
					type:'numberbox',
					options:{
						min:0
					}
				}
			},
			{field:'orderauxremainder',title:'本次采购个数',width:80,align:'right',sortable:true,
				formatter:function(value,rowData,rowIndex){
					if(rowData.goodsInfo!=null &&
							rowData.goodsInfo.bstype=='3'){
						return "商品限购(只可销售)";
					}
					if(value>0){
						return formatterBigNumNoLen(value)+rowData.unitname;
					}else{
						return "";
					}
				},
				editor:{
					type:'numberbox',
					options:{
						min:0,
						precision:general_bill_decimallen
					}
				}
			},
			{field:'orderamount',title:'本次采购金额',resizable:true,align:'right',sortable:true,
				formatter:function(value,rowData,rowIndex){
					return formatterMoney(value);
				}
			},
			{field:'totalstoragenum',title:'合计库存数',width:80,align:'right',sortable:true,
				formatter:function(value,rowData,rowIndex){
					if(value !=null && $.trim(value)!=""){
					    var a=formatterBigNumNoLen(value);
					    if(a!=0) {
                            return a + rowData.auxunitname;
                        }
					}else{
						return "";
					}
				}
			},
			{field:'totalstorageamount',title:'合计库存金额',width:80,align:'right',sortable:true,
				formatter:function(value,rowData,rowIndex){
					return formatterMoney(value);
				}
			},
			{field:'cansaleday',title:'可销售时段',width:70,align:'right',sortable:true,
				formatter:function(value,rowData,rowIndex){
					return value;
				},
				styler:function(value,rowData,rowIndex){
					if(value>30){
						return "background-color:#FFC0CB;font-weight:bold;";
					}
				}
			},
			{field:'saleday',title:'查询间隔天数',width:80,align:'right',hidden:true}
		]]
	});

	var storageTableColJson = $("#purchase-datagrid-storageSummaryReport").createGridColumnLoad({
		//name :'storage_summary',
		frozenCol : [[]],
		commonCol : [[
			{field:'brandid',title:'品牌名称',width:80,isShow:true,isShow:true,
				formatter:function(value,rowData,rowIndex){
					return rowData.brandname;
				}
			},
			{field:'ordernum',title:'本次采购箱数',resizable:true,width:100,align:'right',
				formatter:function(value,rowData,rowIndex){
					var result="";
					if(null!=rowData.brandid && value!=null ){
						if(rowData.ordernum!=0){
							result= formatterNum(value);
						}else{
							result="0";
						}
						result=result+rowData.auxunitname;
					}
					return result;
				}
			},
			{field:'orderamount',title:'本次采购金额',resizable:true,width:100,align:'right',
				formatter:function(value,rowData,rowIndex){
					return formatterMoney(value);
				}
			},
			{field:'existingunitnum',title:'实际数量',width:80,align:'right',sortable:true},
			{field:'existingnum',title:'实际库存',width:80,align:'right',sortable:true,
				formatter:function(value,rowData,rowIndex){
					if(value !=null && $.trim(value)!=""){
						return value+rowData.auxunitname;
					}else{
						return "";
					}
				}
			},
			{field:'existingamount',title:'实际库存金额',width:80,align:'right',sortable:true,
				formatter:function(value,rowData,rowIndex){
					return formatterMoney(value);
				}
			},
			{field:'transitnum',title:'在途量',width:80,align:'right',sortable:true,
				formatter:function(value,rowData,rowIndex){
					if(value !=null && $.trim(value)!=""){
						return value+rowData.auxunitname;
					}else{
						return "";
					}
				}
			},
			{field:'transitamount',title:'在途金额',width:80,align:'right',sortable:true,
				formatter:function(value,rowData,rowIndex){
					return formatterMoney(value);
				}
			},
			{field:'curstoragenum',title:'当前库存数',resizable:true,width:100,align:'right',
				formatter:function(value,rowData,rowIndex){
					if(value !=null && $.trim(value)!=""){
						return value+rowData.auxunitname;
					}else{
						return "";
					}
				}
			},
			{field:'curstorageamount',title:'当前库存金额',resizable:true,width:100,align:'right',
				formatter:function(value,rowData,rowIndex){
					return formatterMoney(value);
				}
			},
			{field:'totalstoragenum',title:'合计库存数',resizable:true,width:100,align:'right',
				formatter:function(value,rowData,rowIndex){
					if(value !=null && $.trim(value)!=""){
						return formatterBigNumNoLen(value)+rowData.auxunitname;
					}else{
						return "";
					}
				}
			},
			{field:'totalstorageamount',title:'合计库存金额',resizable:true,width:100,align:'right',
				formatter:function(value,rowData,rowIndex){
					return formatterMoney(value);
				}
			}
		]]
	});
	$(function(){
		$("#purchase-datagrid-analysisReport").datagrid({
			authority:checkListJson,
			frozenColumns: checkListJson.frozen,
			columns:checkListJson.common,
			method:'post',
			title:'',
			fit:true,
			idField:'goodsid',
			rownumbers:true,
			pagination:true,
			showFooter: true,
			singleSelect:false,
			checkOnSelect:true,
			selectOnCheck:true,
			remoteSort:false,
			pageSize:500,
			//url: 'report/buy/showPlannedOrderAnalysisPageList.do',
			queryParams:initQueryJSON,
			toolbar:'#purchase-toolbar-analysisReport',
			onBeforeLoad:function(param){
				param.sort=pageDefaultSort;
				param.order=pageDefaultOrder;
			},
			onDblClickRow: function(rowIndex, rowData){
				onClickCell(rowIndex, "ordernum");
				thisIndex = rowIndex;
			},
			onClickRow: function(rowIndex, rowData){
				if(rowData.orderunitnum >0 && !(rowData.goodsInfo!=null && rowData.goodsInfo.bstype=='3') ){
					//只可销售的不能采购
					$("#purchase-datagrid-analysisReport").datagrid('checkRow',rowIndex);
				}else{
					$("#purchase-datagrid-analysisReport").datagrid('uncheckRow',rowIndex);
					$("#purchase-datagrid-analysisReport").datagrid('unselectRow',rowIndex);
				}
				thisIndex=rowIndex;
			},
			onClickCell: function(index, field, value){
				onClickCell(index, field);

				thisIndex = index;
			},
			onLoadSuccess:function(data){
				if(data!=null & data.rows != null && data.rows.length>0){
					var ordernum=0;
					var orderunitnum=0;
					var orderauxremainder=0;
					for(var i=0;i<data.rows.length;i++){
						if(data.rows[i].goodsid!=null && ""!=data.rows[i].goodsid && storeChangeData.length>0){
							ordernum=storeChangeData[data.rows[i].goodsid].ordernum ||0;
							orderauxremainder=storeChangeData[data.rows[i].goodsid].orderauxremainder ||0;
							orderunitnum=storeChangeData[data.rows[i].goodsid].orderunitnum ||0;
						}
						if(orderunitnum!=null && orderunitnum>0){
							$("#purchase-datagrid-analysisReport").datagrid('updateRow',{
								index: i,
								row: {
									ordernum: ordernum,
									orderauxremainder: orderauxremainder,
									orderunitnum: orderunitnum,
								}
							});
						}else{
							if(data.rows[i].orderunitnum !=null && data.rows[i].orderunitnum >0 && !(data.rows[i].goodsInfo!=null && data.rows[i].goodsInfo.bstype=='3')){
								//只可销售，不能采购

								$("#purchase-datagrid-analysisReport").datagrid('checkRow',i);
							}
						}
					}
				}
				editIndex = undefined;
				thisIndex = undefined;
				editfield = null;
				nextfiled = null;

				countTotalAmount();
			},
			rowStyler:function(index,row){
				if (row.ordernum!=null && row.ordernum>0){
					return 'background-color:#FBEC8;';
				}
			},
			onSortColumn:function(sort, order){
				var remoteSortArr=['goodsid','barcode'];
				var issort=false;
				if(sort==null || sort==""){
					return true;
				}
				for(var i=0;i<remoteSortArr.length;i++){
					if(sort==remoteSortArr[i].toLowerCase()){
						pageDefaultSort=sort;
						pageDefaultOrder=order;
						return true;
					}
				}
				pageDefaultSort='goodsid';
				pageDefaultOrder='asc';
				var data = $("#purchase-datagrid-analysisReport").datagrid("getData");
				var rows = data.rows;
				var dataArr = [];
				for(var i=0;i<rows.length;i++){
					if(rows[i].goodsid!=null && rows[i].goodsid!=""){
						dataArr.push(rows[i]);
					}
				}
				dataArr.sort(function(a,b){
					if($.isNumeric(a[sort])){
						if(order=="asc"){
							return Number(a[sort])>Number(b[sort])?1:-1
						}else{
							return Number(a[sort])<Number(b[sort])?1:-1
						}
					}else{
						if(order=="asc"){
							return a[sort]>b[sort]?1:-1
						}else{
							return a[sort]<b[sort]?1:-1
						}
					}
				});
				$("#purchase-datagrid-analysisReport").datagrid("loadData",{rows:dataArr,total:data.total});
				return false;
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

		$("#purchase-datagrid-storageSummaryReport").datagrid({
			frozenColumns: storageTableColJson.frozen,
			columns:storageTableColJson.common,
			fit:true,
			rownumbers:true,
			pagination: false,
			idField:'brandid',
			showFooter: true,
			singleSelect:true,
			pageSize:100,
			//url: 'report/buy/showStorageSummaryByBrandInBuyOrder.do',
			queryParams:initQueryJSON
		}).datagrid("columnMoving");

		$("#purchase-query-goodsid").goodsWidget();
		$("#purchase-query-brandid").widget({
			referwid:'RL_T_BASE_GOODS_BRAND',
			width:176,
			singleSelect:true
		});
		//商品分类
		$("#purchase-query-goodssort").widget({
			name:'t_base_goods_info',
			col:'defaultsort',
			singleSelect:false,
			isall:true,
			onlyLeafCheck:true,
			width:176
		});

		$("#purchase-query-supplier").supplierWidget({
			name:'t_purchase_buyorder',
			col:'supplierid',
			singleSelect:true,
			onlyLeafCheck:true,
			required:true
		});
		//仓库
		$("#purchase-query-storageid").widget({
			width:212,
			referwid:'RL_T_BASE_STORAGE_INFO',
			singleSelect:false,
			onlyLeafCheck:false,
			onSelect:function(){
				$("#purchase-query-isSendsStorage").val("0");
			},
			onClear:function(){
				$("#purchase-query-isSendsStorage").val("1");
			}
		});

		//回车事件
		controlQueryAndResetByKey("purchase-queay-analysisReport","purchase-reload-analysisReport");

		//查询
		$("#purchase-queay-analysisReport").click(function(){
			var flag = $("#purchase-query-form-analysisReport").form('validate');
			var supplierid = $("#purchase-query-supplier").supplierWidget("getValue")||"";
			if(supplierid==""){
				$.messager.alert("提醒",'抱歉，供应商不能为空');
				return false;
			}
			if(flag==false){
				return false;
			}
			storeChangeData={};
			editIndex = undefined;
			thisIndex = undefined;
			editfield = null;
			nextfiled = null;
			var queryJSON = $("#purchase-query-form-analysisReport").serializeJSON();
			$("#purchase-datagrid-analysisReport").datagrid({
				url:'report/buy/showPlannedOrderAnalysisPageList.do',
				pageNumber:1,
				queryParams:queryJSON,
				sortName:'goodsid',
				sortOrder:'asc'
			}).datagrid("columnMoving");

			$("#purchase-datagrid-analysisReport").datagrid("clearSelections");
			$("#purchase-datagrid-analysisReport").datagrid("clearChecked");

			$("#purchase-datagrid-storageSummaryReport").datagrid({
				url:'report/buy/showStorageSummaryByBrand.do',
				pageNumber:1,
				queryParams:queryJSON
			}).datagrid("columnMoving");
		});
		//重置
		$("#purchase-reload-analysisReport").click(function(){
			storeChangeData={};
			editIndex = undefined;
			thisIndex = undefined;
			editfield = null;
			nextfiled = null;
			$("#purchase-query-goodsid").goodsWidget("clear");
			$("#purchase-query-brandid").widget("clear");
			$("#purchase-query-form-analysisReport").form("reset");
			$("#purchase-query-supplier").supplierWidget("clear");
			//var queryJSON = $("#purchase-query-form-analysisReport").serializeJSON();
			$("#purchase-datagrid-analysisReport").datagrid('loadData',{total:0,rows:[]});

			$("#purchase-datagrid-analysisReport").datagrid("clearSelections");
			$("#purchase-datagrid-analysisReport").datagrid("clearChecked");

			$("#purchase-datagrid-storageSummaryReport").datagrid('loadData',{total:0,rows:[]});
		});

		//按品牌合计库存刷新
		$("#brandStoragePanel-tools-reload").click(function(){
			//把form表单的name序列化成JSON对象
			var flag=$("#purchase-query-form-analysisReport").form('validate');
			if(flag==false){
				return false;
			}
			var queryJSON = $("#purchase-query-form-analysisReport").serializeJSON();
			$("#purchase-datagrid-storageSummaryReport").datagrid({
				url:'report/buy/showStorageSummaryByBrand.do',
				pageNumber:1,
				queryParams:queryJSON
			}).datagrid("columnMoving");
		});

		<security:authorize url="/report/buy/exportPlannedOrderAnalysisReportData.do">
		$("#purchase-buttons-analysisReportExport").Excel('export',{
			queryForm: "#purchase-query-form-analysisReport", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			type:'exportUserdefined',
			name:'采购计划分析表',
			url:'report/buy/exportPlannedOrderAnalysisReportData.do'
		});
		</security:authorize>
		<security:authorize url="/report/buy/createPlannedOrderByAnalysisReport.do">
		//生成采购计划单
		$("#purchase-addplanorder-analysisReport").click(function(){

			$.messager.confirm("提示","是否要生成采购计划单？", function(r){
				if (r){
					var rows = $("#purchase-datagrid-analysisReport").datagrid("getChecked");
					var datarow=new Array();
					var data={};
					var wargarr=new Array();
					var limitarr=new Array();
					var queryForm = $("#purchase-query-form-analysisReport").serializeJSON();
					for(var i=0;i<rows.length;i++){
						if(rows[i].goodsInfo!=null && rows[i].goodsInfo.bstype=='3'){
							//只可销售的，不能采购
							limitarr.push(rows[i].goodsid);
							continue;
						}
						data={};
						if(null!=rows[i].orderunitnum && !isNaN(rows[i].orderunitnum) && rows[i].orderunitnum>0){
							data.auxunitid=rows[i].auxunitid;
							data.auxunitname=rows[i].auxunitname;
							data.unitid=rows[i].unitid;
							data.unitname=rows[i].unitname;
							data.brandid=rows[i].brandid;
							data.brandname=rows[i].brandname;
							data.goodsid=rows[i].goodsid;
							data.goodsname=rows[i].goodsname;
							data.buyinnum=rows[i].orderunitnum;
							if(rows[i].supplierid !=null ){
								data.supplierid=rows[i].supplierid;
							}
							if(rows[i].storageid !=null && queryForm.storageid!=null && $.trim(queryForm.storageid)!="" ){
								data.storageid=rows[i].storageid;
							}
							datarow.push(data);
						}else{
							wargarr.push(rows[i].goodsid);
						}
					}
					if(limitarr.length>0){
						$.messager.alert("提醒", "抱歉，限购的商品不能生成采购订单"+limitarr.join(",")+"");
						return false;
					}
					if(wargarr.length>0){
						$.messager.alert("提醒-请填写本次采购数量", "商品："+wargarr.join(",")+"，请填写本次采购数量");
						return false;
					}
					var remark="通过采购计划分析表生成！同期日期：";
					var detail = JSON.stringify(datarow);
					queryForm.oldFromData="";
					delete queryForm.oldFromData;
					var queryFormString=JSON.stringify(queryForm);
					var businessdate1 =  $("#purchase-query-tqstartdate").val();
					var businessdate2 =  $("#purchase-query-tqenddate").val();
					loading("生成中..");
					$.ajax({
						url :'purchase/planorder/addPlannedOrderByReport.do',
						type:'post',
						dataType:'json',
						data:{detailList:detail,businessdate1:businessdate1,businessdate2:businessdate2,remark:remark,queryForm:queryFormString},
						success:function(json){
							loaded();
							if(json.flag){
								$.messager.alert("提醒", "生成成功。"+json.msg+"<br/>"+json.errormsg);
							}else{
								$.messager.alert("提醒", "生成失败。"+json.errormsg);
							}
						},
						error:function(){
							loaded();
							$.messager.alert("错误", "系统出错。");
						}
					});
				}
			});
		});
		</security:authorize>
		<security:authorize url="/report/buy/createBuyOrderByAnalysisReport.do">
		//生成采购订单
		$("#purchase-addbuyorder-analysisReport").click(function(){

			$.messager.confirm("提示","是否要生成采购订单？", function(r){
				if (r){
					var rows = $("#purchase-datagrid-analysisReport").datagrid("getChecked");
					var datarow=new Array();
					var data={};
					var wargarr=new Array();
					var limitarr=new Array();
					var queryForm = $("#purchase-query-form-analysisReport").serializeJSON();
					for(var i=0;i<rows.length;i++){
						if(rows[i].goodsInfo!=null && rows[i].goodsInfo.bstype=='3'){
							//只可销售的，不能采购
							limitarr.push(rows[i].goodsid);
							continue;
						}
						data={};
						if(null!=rows[i].orderunitnum && !isNaN(rows[i].orderunitnum) && rows[i].orderunitnum>0){
							data.auxunitid=rows[i].auxunitid;
							data.auxunitname=rows[i].auxunitname;
							data.unitid=rows[i].unitid;
							data.unitname=rows[i].unitname;
							data.brandid=rows[i].brandid;
							data.brandname=rows[i].brandname;
							data.goodsid=rows[i].goodsid;
							data.goodsname=rows[i].goodsname;
							data.buyinnum=rows[i].orderunitnum;
							if(rows[i].supplierid !=null ){
								data.supplierid=rows[i].supplierid;
							}
							if(rows[i].storageid !=null && queryForm.storageid!=null && $.trim(queryForm.storageid)!=""){
								data.storageid=rows[i].storageid;
							}
							datarow.push(data);
						}else{
							wargarr.push(rows[i].goodsid);
						}
					}
					if(limitarr.length>0){
						$.messager.alert("提醒", "抱歉，限购的商品不能生成采购订单"+limitarr.join(",")+"");
						return false;
					}
					if(wargarr.length>0){
						$.messager.alert("提醒-请填写本次采购数量", "商品："+wargarr.join(",")+"，请填写本次采购数量");
						return false;
					}
					var remark="通过采购计划分析表生成！同期日期：";
					var detail = JSON.stringify(datarow);
					queryForm.oldFromData="";
					delete queryForm.oldFromData;
					var queryFormString=JSON.stringify(queryForm);
					var businessdate1 =  $("#purchase-query-tqstartdate").val();
					var businessdate2 =  $("#purchase-query-tqenddate").val();
					loading("生成中..");
					$.ajax({
						url :'purchase/buyorder/addBuyOrderByReport.do',
						type:'post',
						dataType:'json',
						data:{detailList:detail,businessdate1:businessdate1,businessdate2:businessdate2,remark:remark,queryForm:queryFormString},
						success:function(json){
							loaded();
							if(json.flag){
								$.messager.alert("提醒", "生成成功。"+json.msg+"<br/>"+json.errormsg);
							}else{
								$.messager.alert("提醒", "生成失败。"+json.errormsg);
							}
						},
						error:function(){
							loaded();
							$.messager.alert("错误", "系统出错。");
						}
					});
				}
			});
		});
		</security:authorize>
	});
	var $wareList = $("#purchase-datagrid-analysisReport"); //商品datagrid的div对象
	var editIndex = undefined;
	var thisIndex = undefined;
	var editfield = null;
	var nextfiled = null;
	var storeChangeData = {};
	function endEditing(field){
		if (editIndex == undefined){
			return true;
		}
		if(field == "ordernum"){
			if ($wareList.datagrid('validateRow', editIndex)){
				nextfiled = "ordernum";
				var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'ordernum'});
				var edObj=getNumberBoxObject(ed.target);
				if(null==edObj){
					return false;
				}
				var ordernum = edObj.val();
				if(ordernum<=0){
					ordernum=0;

					$wareList.datagrid('unselectRow',editIndex);
				}else{
					$wareList.datagrid('selectRow',editIndex);
				}
				$wareList.datagrid('getRows')[editIndex]['ordernum'] = ordernum;
				dataChangeByOrdernum($wareList.datagrid('getRows')[editIndex]);
				$wareList.datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}else if(field == "orderauxremainder"){
			if ($wareList.datagrid('validateRow', editIndex)){
				nextfiled = "orderauxremainder";
				var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'orderauxremainder'});
				var edObj=getNumberBoxObject(ed.target);
				if(null==edObj){
					return false;
				}
				var orderauxremainder = edObj.val() || 0;
				if(orderauxremainder<=0){
					orderauxremainder=0;

					$wareList.datagrid('unselectRow',editIndex);
				}else{
					$wareList.datagrid('selectRow',editIndex);
				}
				orderauxremainder=Number(orderauxremainder);
				$wareList.datagrid('getRows')[editIndex]['orderauxremainder'] = orderauxremainder;
				dataChangeByOrdernum($wareList.datagrid('getRows')[editIndex]);
				$wareList.datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
	}
	function onClickCell(index, field){
		if (endEditing(editfield)){
			var row = $wareList.datagrid('getRows')[index];
			if(row.goodsid == undefined){
				return false;
			}

			if(row.goodsInfo!=null && row.goodsInfo.bstype=='3'){
				//只可销售
				return false;
			}
			editfield = field;
			if(field == "ordernum"){
				$wareList.datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
				editIndex = index;
				thisIndex = index;
				var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'ordernum'});
				var obj=getNumberBoxObject(ed.target);
				if(null==obj){
					return false;
				}
				obj.focus();
				obj.select();
				obj.die("keyup").bind("keyup",function(e){
					var e = e || event,
							keycode = e.which || e.keyCode;
					if (keycode==13 || keycode==38 || keycode==40) {
						if(editfield!=null){
							endEditing(editfield);
						}
					}
				});
			}else if(field == "orderauxremainder"){
				$wareList.datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
				editIndex = index;
				thisIndex = index;
				var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'orderauxremainder'});
				var obj=getNumberBoxObject(ed.target);
				if(null==obj){
					return false;
				}
				obj.focus();
				obj.select();
				obj.die("keyup").bind("keyup",function(e){
					var e = e || event,
							keycode = e.which || e.keyCode;
					if (keycode==13 || keycode==38 || keycode==40) {
						if(editfield!=null){
							endEditing(editfield);
						}
					}
				});
			}
		}
	}
	function dataChangeByOrdernum(data){
		var referDateBy=$("#purchase-query-referDateBy").val();
		if(data!=null && typeof(data)=="object"){
			if(data.ordernum==null || isNaN(data.ordernum)){
				data.ordernum=0;
			}

			if(data.boxnum == null || isNaN(data.boxnum)){
				data.boxnum=0;
			}
			if(data.buyprice == null || isNaN(data.buyprice)){
				data.buyprice=0;
			}
			if(data.boxamount == null || isNaN(data.boxamount)){
				data.boxamount=Number(data.boxnum)*Number(data.buyprice);
			}
			if(data.orderauxremainder==null || isNaN(data.orderauxremainder)){
				data.orderauxremainder=0;
			}

			data.orderunitnum=Number(((Number(data.ordernum)* Number(data.boxnum)+Number(data.orderauxremainder)).toFixed(general_bill_decimallen)));

			if(data.goodsid!=null && ""!=data.goodsid){
				storeChangeData[data.goodsid]={
					ordernum:Number(data.ordernum),
					orderauxremainder:Number(data.orderauxremainder),
					orderunitnum:Number(data.orderunitnum)
				};
			}
			data.orderamount=Number(data.buyprice) * Number(data.orderunitnum);
			data.newordernum=data.ordernum;
			if(data.boxnum!=0){
				data.newordernum=Number(data.orderunitnum) / Number(data.boxnum);
			}

			if(data.curstoragenum !=null && !isNaN(data.curstoragenum)){
				data.totalstoragenum=Number((Number(data.curstoragenum)+Number(data.newordernum)).toFixed(general_bill_decimallen));
				data.totalstorageamount=Number(data.totalstoragenum) * Number(data.boxamount);

				if(referDateBy=="1"){
					if(data.tqsalenum!=null && !isNaN(data.tqsalenum) && data.tqsalenum>0){
						if(data.saleday !=null && !isNaN(data.saleday)){
							data.cansaleday=Math.ceil(data.totalstoragenum * data.saleday /data.tqsalenum);
						}else{
							var saleday=getSaleday();
							data.cansaleday=Math.ceil(data.totalstoragenum * saleday /data.tqsalenum);
						}
					}
				}else{
					if(data.qqsalenum!=null && !isNaN(data.qqsalenum) && data.qqsalenum>0){
						if(data.saleday !=null && !isNaN(data.saleday)){
							data.cansaleday=Math.ceil(data.totalstoragenum * data.saleday /data.qqsalenum);
						}else{
							var saleday=getQqsaleday();
							data.cansaleday=Math.ceil(data.totalstoragenum * saleday /data.qqsalenum);
						}
					}
				}
			}
			if(data.curstorageunitnum !=null && !isNaN(data.curstorageunitnum)){
				data.totalstorageunitnum=Number((Number(data.curstorageunitnum)+Number(data.orderunitnum)).toFixed(general_bill_decimallen));
			}
		}
	}

	function getSaleday() {
		var iDays=0;
		var referDateBy=$("#purchase-query-referDateBy").val();
		try{
			var sDate1="";
			var sDate2="";
			if(referDateBy=="1"){
				sDate1=$.trim($("#purchase-query-tqstartdate").val()||"");
				sDate2=$.trim($("#purchase-query-tqedate").val() || "");
			}else{
				sDate1=$.trim($("#purchase-query-qqstartdate").val()||"");
				sDate2=$.trim($("#purchase-query-qqedate").val() || "");
			}
			if(sDate1=="" || sDate2 == "" ){
				return 0;
			}
			var aDate = sDate1.split("-");
			var oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
			var aDate = sDate2.split("-");
			var oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
			iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24);
		}catch(e){
		}
		return iDays;
	}
	function moveToNextRow(){
		var datarow=$wareList.datagrid('getRows');
		if(null!=datarow && (thisIndex+1) < datarow.length){
			if(editIndex == null){
				onClickCell(thisIndex+1, nextfiled);
			}
		}
	}

	function moveToPrevRow(){
		if((thisIndex-1) >0){
			if(editIndex == null){
				onClickCell(thisIndex-1, nextfiled);
			}
		}
	}
	$(document).bind('keyup', 'up',function (event){
		moveToPrevRow();
		return false;
	});
	$(document).bind('keyup', 'down',function (event){
		moveToNextRow();
		return false;
	});
	$(document).bind('keyup', 'enter',function (event){
		moveToNextRow();
		return false;
	});

	var purchaseFlowGridList=null;
	function showPurchaseFlowList(goodsid){
		purchaseFlowGridList=null;
		$("#purchase-goodsid-queryPurchaseFlowList").val(goodsid);
		$('#purchase-query-showPurchaseFlowList').dialog({
			title: '商品'+goodsid+"采购进货流水明细",
			fit:true,
			closed: true,
			cache: false,
			maximizable:true,
			resizable:true,
			modal: true,
			onOpen:function(){
				var queryJSONdata = $("#purchase-form-PurchaseFlowList").serializeJSON();
				purchaseFlowGridList=$("#purchase-datagrid-showPurchaseFlowList").datagrid({
					fit:true,
					method:'post',
					rownumbers:true,
					pagination:true,
					idField:'id',
					pageSize:100,
					singleSelect:true,
					showFooter: true,
					toolbar:'#purchase-table-query-PurchaseFlow-toolbar',
					url:"purchase/query/showArrivalReturnDetailList.do",
					queryParams:queryJSONdata,
					columns:[[
						{field:'id',title:'编号',width:120},
						{field:'billtypename',title:'类型',width:60},
						{field:'businessdate',title:'业务日期',width:100},
						{field:'goodsid',title:'商品编码',width:60},
						{field:'goodsname',title:'商品名称',width:130},
						{field:'barcode',title:'条形码',width:90},
						{field:'brandname',title:'品牌名称',width:60},
						{field:'model',title:'规格参数',width:60,hidden:true},
						{field:'unitname',title:'单位',width:40},
						{field:'auxunitname',title:'辅单位',width:50,hidden:true},
						{field:'auxnumdetail',title:'辅数量',resizable:true,align:'right'},
						{field:'unitnum', title:'数量',width:80,align:'right',
							formatter:function(value,row,index){
								return formatterBigNumNoLen(value);
							}
						},
						{field:'taxprice',title:'单价',width:60,align:'right',
							formatter: function(value,row,index){
								if(row.businessdate==null || row.businessdate.indexOf("小计")<0){
									return formatterMoney(value);
								}
							}
						},
						{field:'taxamount',title:'金额',resizable:true,align:'right',
							formatter: function(value,row,index){
								return formatterMoney(value);
							}
						},
						{field:'notaxamount',title:'未税金额',resizable:true,align:'right',
							formatter: function(value,row,index){
								return formatterMoney(value);
							}
						},
						{field:'taxtypename',title:'税种',width:60},
						{field:'tax',title:'税额',resizable:true,align:'right',
							formatter: function(value,row,index){
								return formatterMoney(value);
							}
						},
						{field:'iswriteoffname',title:'状态',width:50},
						{field:'remark',title:'备注',width:120}
					]]
				});
			},
			onClose:function(){
				try{
					if(purchaseFlowGridList!=null){
						purchaseFlowGridList.datagrid('loadData', { total: 0, rows: [] });
						$("#purchase-form-PurchaseFlowList").form("reset");
					}
				}catch(ex){
				}
			}
		});
		$('#purchase-query-showPurchaseFlowList').dialog('open');
	}

	$("#purchase-btn-queryPurchaseFlowList").click(function(){
		var queryJSON = $("#purchase-form-PurchaseFlowList").serializeJSON();
		$("#purchase-datagrid-showPurchaseFlowList").datagrid({
			url:'purchase/query/showArrivalReturnDetailList.do',
			pageNumber:1,
			queryParams:queryJSON
		}).datagrid("columnMoving");
	});
	$("#purchase-btn-reloadPurchaseFlowList").click(function(){
		$("#purchase-form-PurchaseFlowList").form("reset");
		$("#purchase-datagrid-showPurchaseFlowList").datagrid('loadData',{total:0,rows:[]});
		$("#purchase-datagrid-showPurchaseFlowList").datagrid('clearSelections');
	});

	function countTotalAmount(){
		var rows =  $("#purchase-datagrid-analysisReport").datagrid('getChecked');
		var orderamount = 0;
		var ordernum = 0;
		for(var i=0;i<rows.length;i++){
			orderamount = Number(orderamount)+Number(rows[i].orderamount == undefined ? 0 : rows[i].orderamount);
			ordernum = Number(ordernum)+Number(rows[i].ordernum == undefined ? 0 : rows[i].ordernum);
		}
		var foot=[{goodsid:'选中金额',goodsname:'',auxunitname:'',ordernum:ordernum,orderamount:orderamount}];
		if(null!=SR_footerobject){
			foot.push(SR_footerobject);
		}
		$("#purchase-datagrid-analysisReport").datagrid("reloadFooter",foot);
	}

</script>
</body>
</html>
