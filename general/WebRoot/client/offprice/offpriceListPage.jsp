<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>客户端特价</title>
	<%@include file="/include.jsp"%>
	<style type="text/css">
		.checkbox1{
			float:left;
			height: 22px;
			line-height: 22px;
		}
		.divtext{
			height:22px;
			line-height:22px;
			float:left;
			display: block;
		}
	</style>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',border:false">
			<div class="buttonBG" id="client-buttons-offpriceListPage"></div>
		</div>  
		<div data-options="region:'center'">
			<table id="client-datagrid-query-offpriceListPage"></table>
			<div id="storage-table-query-DeliveryOutListPage"style="padding: 2px; height: auto">
				<form id="client-form-offprceListPage" method="post">
					<table>
						<tr>
							<td class="len80">门店名称：</td>
							<td class="len200">
								<input type="text" id="storeid" name="storeid" style="width: 150px;"/>
							</td>
							<td class="len100">　所属供应商：</td>
							<td class="len150">
								<input type="text" id="supplierid" name="supplierid" style="width: 150px;"/>
							</td>
						</tr>
						<tr>
							<td>商品品牌：</td>
							<td >
								<input type="text" id="brand" name="brand" style="width: 150px;"/>
							</td>
							<td class="right">商品分类：</td>
							<td >
								<input type="text" id="goodstype" name="goodstype" style="width: 150px;"/>
							</td>
							<%--<td>　编码/条形码：</td>--%>
							<%--<td>--%>
								<%--<input type="text" id="barcode" name="barcode" style="width:150px;"/>--%>
							<%--</td>--%>
							<td>　商品名称：</td>
							<td >
								<input type="text" id="goodsid" name="goodsid" style="width: 150px;"/>
							</td>
						</tr>
						<tr>
							<td>特价状态：</td>
							<td >
								<label class="divtext"><input type="checkbox" class="checkbox1" name="expired" value="1"/>未生效</label>
								<label class="divtext"><input type="checkbox" class="checkbox1" name="expired" value="2"/>生效</label>
								<label class="divtext"><input type="checkbox" class="checkbox1" name="expired" value="3"/>已失效</label>
							</td>
							<td colspan="2">
							</td>
							<td colspan="2" align="right">
								<a href="javaScript:void(0);" id="beginquery" class="button-qr">查询</a>
								<a href="javaScript:void(0);" id="resetBtn" class="button-qr">重置</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<div id="client-dialog-offpriceDialog"></div>
	<script type="text/javascript">

		/**
		 * 验证扩展
		 */
		$.extend($.fn.validatebox.defaults.rules, {
			dateBiggerOrEqual:{
				validator : function(value, param){

					if(value) {
						if(value >= param[0]) {
							return true;
						}
					}
					return false;
				},
//				message: '日期时间段设定不正确'
				message: '日期设定不能小于{0}'
			},
			timeBiggerOrEqual:{
				validator : function(value, param){

					if(value) {
						if(value >= param[0]) {
							return true;
						}
					}
					return false;
				},
				message: '时间设定不能小于{0}'
			}
		});

		$(function(){

			var initQueryJSON = $("#client-form-offprceListPage").serializeJSON();

			var editRow=undefined;
			var dg = $("#client-datagrid-query-offpriceListPage");

			var OffPriceListJson=$("#client-datagrid-query-offpriceListPage").createGridColumnLoad({
				name :'t_client_offprice',
				frozenCol : [[
//					{field:'idok',checkbox:false,isShow:true}
				]],
				commonCol :[[
					{field:'id',title:'编号',width:120,hidden:true},
					{field:'deptid',title:'部门编号',width:70},
					{field:'deptname',title:'部门名称',width:120,aliascol: 'storeid'},
					{field:'goodsid',title:'商品编码',width:70},
					{field:'goodsname',title:'商品名称',width:120,aliascol: 'goodsid'},
					{field:'status',title:'状态',width:60,aliascol: 'goodsid'},
					{field:'barcode',title:'条形码',width:100,aliascol: 'goodsid'},
					{field:'mainunit',title:'单位',width:60,aliascol: 'goodsid'},
					{field:'basesaleprice',title:'基准销售价',width:80,aliascol: 'goodsid',
						formatter: function(value,row,index){
							return formatterMoney(value);
						}
					},
					{field:'retailprice',title:'零售价格',width:80,
						editor:{
							type:'numberbox',
							options:{
								min:0,
								precision:2,
								required:true
							}
						},
						formatter: function(value,row,index){
							return formatterMoney(value);
						}
					},
					{field:'begindate',title:'起始日期',width:95,editor:'dateText'},
					{field:'enddate',title:'终止日期',width:95,editor:'dateText'},
					{field:'begintime',title:'起始时间',width:73,editor:'timespinner'},
					{field:'endtime',title:'结束时间',width:73,editor:'timespinner'},
					{field:'operateuserid',title:'制单人',width:60,aliascol: 'goodsid',
						formatter: function(value, row, index){
							return row.operateusername;
						}
					},
					{field:'addtime',title:'添加时间',width:125}
				]]
			});

			$("#client-datagrid-query-offpriceListPage").datagrid({
				fit:true,
				method:'post',
				rownumbers:true,
				pagination:true,
				idField:'id',
				singleSelect:true,
				showFooter: true,
				toolbar:'#storage-table-query-DeliveryOutListPage',
				url:"client/offprice/getOffPriceList.do",
				queryParams:initQueryJSON,
				pageSize:100,
				authority:OffPriceListJson,
				frozenColumns: OffPriceListJson.frozen,
				columns:OffPriceListJson.common,
				onClickRow: function() {dg.datagrid('endEdit',editRow);},
				onDblClickRow:function(rowIndex,rowData){
					if(editRow!=undefined){
						$.messager.alert("提醒","请先完成当前操作","warning");
						return false;
					}
					dg.datagrid('unselectAll');
					dg.datagrid('beginEdit',rowIndex);
					editRow=rowIndex;
				},
				onLoadSuccess:function(data){
					editRow = undefined
				},
				onAfterEdit:function(index,rowData,changes){

					var inserted=dg.datagrid('getChanges','inserted');
					var updated=dg.datagrid('getChanges','updated');
					var url="";var updateRowData = {};
					if(inserted.length>0){

					}
					if(updated.length>0){
						url='client/offprice/updateOffPriceInfo.do';
						updateRowData['clientOffprice.id'] = rowData.id;
						updateRowData['clientOffprice.storeid'] = rowData.storeid;
						updateRowData['clientOffprice.goodsid'] = rowData.goodsid;
						updateRowData['clientOffprice.retailprice'] = rowData.retailprice;
						updateRowData['clientOffprice.begindate'] = rowData.begindate;
						updateRowData['clientOffprice.enddate'] = rowData.enddate;
						updateRowData['clientOffprice.begintime'] = rowData.begintime;
						updateRowData['clientOffprice.endtime'] = rowData.endtime
					}
					if(url==""){
						editRow=undefined;
						return false;
					}
					loading();
					$.ajax({
						url:url,
						type:'post',
						data:updateRowData,
						dataType:'json',
						success:function(r){
							loaded();
							if(r&&r.flag){
								$.messager.alert('提醒',r.msg);
								dg.datagrid('acceptChanges');
							}else{
								$.messager.alert('错误',r.msg);
								dg.datagrid('rejectChanges');
							}
							editRow=undefined;
							dg.datagrid('unselectAll');
						}
					});
				}
			});

			$("#client-buttons-offpriceListPage").buttonWidget({
				initButton:[
					{},
					{	id:'addBtn',
						type:'button-add',
						handler: function(){
							var $diaLog=$("#client-dialog-offpriceDialog");
							parent.$.dialog=$diaLog;
							parent.$.dg=$("#client-datagrid-query-offpriceListPage");
							$diaLog.dialog({
								title:'新增特价',
								width: 470,
								height: 200,
								closed: true,
								cache: false,
								modal: true,
								resizable:true,
								href:'client/offprice/offpriceDetailAddPage.do',
								onLoad:function(){
								}
							});
							$diaLog.dialog("open");
						}

					},
					{	id:'saveBtn',
						type:'button-save',
						handler: function(){
							if(editRow!=undefined){
								dg.datagrid('endEdit',editRow);
							}else{
								$.messager.alert('提醒','未找到要修改的数据')
							}
						}
					},
					{
						type: 'button-import',
						attr: {
							type:'importUserdefined',
							url:'client/offprice/importClientOffPrice.do',
							importparam:'必填：门店名称、商品编码、起始日期、终止日期、起始时间、结束时间、零售价格',
							onClose: function(){ //导入成功后窗口关闭时操作，
								$("#client-datagrid-query-offpriceListPage").datagrid('reload');	//更新列表
							}
						}
					},
					{
						type:'button-export',//导出
						attr:{
							queryForm: "#client-form-offprceListPage",
							type:'exportUserdefined',
							name:'门店特价',
							url:'client/offprice/exportClientOffPrice.do'
						}
					},
					{}
				],
				buttons:[
					{},
					{
						id: 'button-offprice-log',
						name:'日志查询',
						iconCls:'button-audit',
						handler: function(){
							top.addTab('client/offprice/offpriceLogPage.do',"日志查询");
						}
					},
					{}
				]
			});
			$("#client-buttons-offpriceListPage").buttonWidget("enableButton", 'button-save');

			//门店名称
			$("#storeid").widget({
				referwid :'RL_T_BASE_STORE_INFO',
				singleSelect:true,
				onlyLeafCheck:true,
				width: 150,
				onSelect:function(data){
				}
			});

			//门店分类
			$("#storetype").widget({
				referwid :'RL_T_BASE_STORE_TYPE',
				singleSelect:true,
				onlyLeafCheck:true,
				width: 150
			});

			//所属供应商
			$("#supplierid").widget({
				referwid:'RL_T_BASE_BUY_SUPPLIER',
				width:150
			});

			//商品品牌
			$("#brand").widget({
				width:150,
				referwid:'RL_T_BASE_GOODS_BRAND',
				col:'brand',
				singleSelect:true,
				onlyLeafCheck:false
			});

			//商品分类
			$("#goodstype").widget({
				width:150,
				referwid:'RL_T_BASE_GOODS_WARESCLASS'
			});

			//商品名称
			$("#goodsid").goodsWidget({
			});

			//查询
			$("#beginquery").click(function(){
				var queryJSON = $("#client-form-offprceListPage").serializeJSON();
				$('#client-datagrid-query-offpriceListPage').datagrid('load',queryJSON);
			});
			//重置
			$("#resetBtn").click(function(){
				$("#storeid").widget('clear');
				$("#brand").widget('clear');
				$("#goodstype").widget('clear');
				$("#supplierid").widget('clear');
				$("#storetype").widget('clear');
				$('#goodsid').goodsWidget('clear');
				$('#client-form-offprceListPage')[0].reset();
				var queryJSON = $("#client-form-offprceListPage").serializeJSON();
				$('#client-datagrid-query-offpriceListPage').datagrid('load',queryJSON);
			});

		})
	</script>
</body>
</html>