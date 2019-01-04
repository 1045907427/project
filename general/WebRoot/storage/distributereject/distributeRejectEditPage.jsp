<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>配送入库单新增</title>
<%@include file="/include.jsp"%>
</head>
<body>
	<div class="easyui-panel" title="" data-options="fit:true,border:false">


		<div class="easyui-layout" data-options="fit:true">
			<form action="storage/distrtibution/distributeRejectInfoEditSave.do" id="storage-form-distributeRejectAddPage" method="post">
					<input type="hidden" id="storage-distrtibution-datagridValues" name="detail" /> 
					<input type="hidden" id="storage-distrtibution-foots" name="foot" />
					<input type="hidden" id="storage-distrtibution-Entryid" name="storageDeliveryEnter.id" value="${id }" />
					<input type="hidden" id="storage-distrtibution-Entryifchecked"  value="${storageDeliveryEnter.ischeck}" />
					<input type="hidden" id="storage-distrtibution-printtime"  value="${storageDeliveryEnter.printtimes}" />
					<input type="hidden" id="storage-distrtibution-status"  value="${storageDeliveryEnter.status}" />
					<input type="hidden" id="saveAndAudit" name="saveAndAudit" value="" />												
					<input type="hidden" name="storageDeliveryEnter.sourcetype" value="${storageDeliveryEnter.sourcetype}"></input>
					<input type="hidden" name="storageDeliveryEnter.billtype" value="${storageDeliveryEnter.billtype}"></input>				
					
			   <div data-options="region:'north',border:false"style="height: 100px;">
				<table style="border-collapse: collapse;" border="0" cellpadding="4" cellspacing="4">
						<tr>
							<td style="width: 80px;">编号：</td>
							<td style="width: 165px;">
								<input type="text" class="len150" readonly="readonly" value="${storageDeliveryEnter.id}" /></td>
							<td style="width: 80px;">业务日期：</td>
							<td style="width: 165px;">
								<input type="text" class="len150" id="storage-distributeRejectAddPage-businessdate" name="storageDeliveryEnter.businessdate" value='${storageDeliveryEnter.businessdate }'readonly="readonly" class="easyui-validatebox" required="true"onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
							<td style="width: 80px;">状态：</td>
							<td style="width: 165px;">
								<select id="select" disabled="disabled" class="len150">
									<option value="0">新增</option>
									<option value="1">暂存</option>
									<option value="2">保存</option>
									<option value="3">审核通过</option>
									<option value="4">关闭</option>
									<option value="5">中止</option>
								</select>
							</td>
						</tr>
						
						
			 	<c:if test="${billtype==1 }">
				    <tr>
							<td style="">供应商：</td>
							<td colspan="3">
								<input type="text" id="storage-distributeRejectAddPage-supplier" style="width: 320px;" name="storageDeliveryEnter.supplierid" value="${storageDeliveryEnter.supplierid }" text="<c:out value="${supplierName }" ></c:out>"  readonly="readonly" /> 
								<span  id="storage-supplier-showid-distributeRejectAddPage" style="margin-left: 5px; line-height: 25px;">编号：${storageDeliveryEnter.supplierid }</span>
							</td>
						
                       
 							<td style="">入库仓库：</td>
							<td> 	<input type="text" id="storage-distributeRejectAddPage-storage"  name="storageDeliveryEnter.storageid" value="<c:out value="${storageDeliveryEnter.storageid }"></c:out>"  readonly="readonly" /></td>
					</tr>
					
					<tr>
							<td style="width: 60px;">备注：</td>
							<td colspan="5"><input type="text" style="width: 672px;" id="storage-distributeRejectAddPage-remark" name="storageDeliveryEnter.remark" value="${storageDeliveryEnter.remark}" /></td>
					</tr>
				  </c:if>  
				  
				  <c:if test="${billtype==2 }">  
				   <tr>
						<td style="">供应商：</td>
						<td colspan="3">
								<input type="text" id="storage-distributeRejectAddPage-supplier" style="width: 320px;" name="storageDeliveryEnter.supplierid" value="${storageDeliveryEnter.supplierid }" text="<c:out value="${supplierName }" ></c:out>"readonly="readonly" /> 
								<span  id="storage-supplier-showid-distributeRejectAddPage" style="margin-left: 5px; line-height: 25px;">编号：${storageDeliveryEnter.supplierid }</span>
							</td>					
						<td style="">入库仓库：</td>
						<td> 	
								<input type="text" id="storage-distributeRejectAddPage-storage"  name="storageDeliveryEnter.storageid" value="<c:out value="${storageDeliveryEnter.storageid }"></c:out>"  readonly="readonly" />
						</td>
						
					</tr>
					
					<tr>
						<!-- 无来源 -->
						<c:if test="${storageDeliveryEnter.sourcetype==0}">
							<td style="">客户：</td>
							<td colspan="3">																																											<!-- customerName -->
								<input type="text" id="storage-distributeRejectEditPage-customer" style="width: 320px;" name="storageDeliveryEnter.customerid" /> 
								<span id="span-customerid" style="margin-left:5px;line-height:25px;">编号：${storageDeliveryEnter.customerid }</span>	
								 <input id="pcustomerid" type="hidden" name="storageDeliveryEnter.pcustomerid" value=""/>
                             	 <input id="customersort" type="hidden" name="storageDeliveryEnter.customersort" value=""/>
                             	 <input id="deptid" type="hidden" name="storageDeliveryEnter.deptid" value=""/>
							</td>
						</c:if>
						<c:if test="${storageDeliveryEnter.sourcetype!=0}">
							<td style="">客户：</td>
							<td colspan="3">																																											<!-- customerName -->
								<input type="text" id="storage-distributeRejectEditPage-customer" style="width: 320px;" name="storageDeliveryEnter.customerid" value="${storageDeliveryEnter.customerid }" text="<c:out value="${customerName}" ></c:out>"readonly="readonly" /> 
								<span style="margin-left:5px;line-height:25px;">编号：${storageDeliveryEnter.customerid }</span>	
							</td>
							
						</c:if>
						<td style="width:60px;">客户单号：</td>
						<td colspan="5">
							<input type="text" style="width:150px;" id="storage-distributeRejectAddPage-customerbill" name="storageDeliveryEnter.customerbill" value="${storageDeliveryEnter.customerbill }"/>
						</td>
					</tr>
					  <tr>
						  <td style="width:60px;">备注：</td>
						  <td colspan="5">
							  <input type="text" style="width:670px;" id="storage-distributeRejectAddPage-remark" name="storageDeliveryEnter.remark" value="${storageDeliveryEnter.remark }"/>
						  </td>
					  </tr>
				   
				  </c:if> 
						
					</table>
				</div>
				<div data-options="region:'center',border:false">
					<table id="storage-table-distributeRejectAddPage"></table>
				</div>
			</form>
		</div>
	</div>

	<div id="storage-Button-tableMenu" class="easyui-menu" style="width: 120px; display: none;">
		<div id="storage-tableMenu-itemAdd" iconCls="button-add">添加</div>
		<div id="storage-tableMenu-itemEdit" iconCls="button-edit">编辑</div>
		<div id="storage-tableMenu-itemDelete" iconCls="button-delete">删除</div>
	</div>



	<script type="text/javascript">
		var editRowIndex = undefined;
		function getAddRowIndex() {
			var $potable = $("#storage-table-distributeRejectAddPage");
			var dataRows = $potable.datagrid('getRows');
			var rindex = 0;
			for (rindex = 0; rindex < dataRows.length; rindex++) {
				if (dataRows[rindex].goodsid == null || dataRows[rindex].goodsid == "") {
					break;
				}
			}
			if (rindex == dataRows.length) {
				$potable.datagrid('appendRow', {});
			}
			return rindex;
		}

		$(function() {
			
			$("#storage-table-distributeRejectAddPage").datagrid({
				fit : true,
				striped : true,
				method : 'post',
				rownumbers : true,
				//idField:'id',
				singleSelect : true,
				showFooter : true,
				data : [ {}, {}, {}, {}, {}, {}, {}, {}, {},{}, {}, {} ],
				authority : tableColJson,
				frozenColumns : tableColJson.frozen,
				columns : tableColJson.common,
				onLoadSuccess : function() {
					var dataRows = $("#storage-table-distributeRejectAddPage").datagrid('getRows');
					var rowlen = dataRows.length;
					if (rowlen < 12) {
						for (var i = rowlen; i < 12; i++) {
							$("#storage-table-distributeRejectAddPage").datagrid('appendRow', {});
						}
					} else {
						$("#storage-table-distributeRejectAddPage").datagrid('appendRow', {});
					}
					$("#storage-table-distributeRejectAddPage").datagrid('reloadFooter', [ {goodsid : '合计',unitnum : '0',taxamount : '0',volume : '0',weight : '0',allboxnum : '0'} ]);
					footerReCalc()
				},	
				data : JSON.parse('${StorageDeliveryEnterDetailList}'),
				onDblClickRow : function(rowIndex, rowData) { //选中行
					
					if (rowData.goodsid&& rowData.goodsid != "") {
							editRowIndex = rowIndex;
							distributeRejectDetailEditDialog(rowData);
					} else {
						var sourcetype="${storageDeliveryEnter.sourcetype}";
		        		if(sourcetype!="0"){
		        			//$.messager.alert("提示","有来源的单据不能添加删除商品")
		        			return false;
		        		}
						editRowIndex = rowIndex;
						distributeRejectDetailAddDialog("${billtype}");
					}
				},
				onRowContextMenu : function(e, rowIndex,rowData) {
					e.preventDefault();
					var $contextMenu = $('#storage-Button-tableMenu');
					$contextMenu.menu('show', {left : e.pageX,top : e.pageY});
					$("#storage-table-distributeRejectAddPage").datagrid('selectRow', rowIndex);
					editRowIndex = rowIndex;
					$contextMenu.menu('enableItem','#storage-tableMenu-itemAdd');
					$contextMenu.menu('enableItem','#storage-tableMenu-itemEdit');
				    $contextMenu.menu('enableItem','#storage-tableMenu-itemDelete');
				    var sourcetype="${storageDeliveryEnter.sourcetype}";
	        		if(sourcetype!="0"){
	        			 if(rowData.id==undefined){
	        			   $contextMenu.menu('disableItem', '#storage-tableMenu-itemEdit');
	        			 }
	        			 $contextMenu.menu('disableItem', '#storage-tableMenu-itemAdd');
	        			 $contextMenu.menu('disableItem', '#storage-tableMenu-itemDelete');
	        		}
				}
		}).datagrid("columnMoving");

			//添加按钮事件
			$("#storage-tableMenu-itemAdd").unbind("click").bind("click",function() 
					{
						if ($("#storage-Button-tableMenu").menu('getItem', this).disabled) {
							return false;
						}
						distributeRejectDetailAddDialog();
					});

			//编辑按钮事件
			$("#storage-tableMenu-itemEdit").unbind("click").bind("click",function() {
						if ($("#storage-Button-tableMenu").menu('getItem', this).disabled) {
							return false;
						}
						var data = $("#storage-table-distributeRejectAddPage").datagrid('getSelected');
						if (null != data && data.goodsid != null&& data.goodsid != "") {
							distributeRejectDetailEditDialog(data);
						} else {
							distributeRejectDetailAddDialog();
						}
		});

			//删除按钮事件
			$("#storage-tableMenu-itemDelete").unbind("click").bind("click",function() {
								if ($("#storage-Button-tableMenu").menu('getItem', this).disabled) {
									return false;
								}
								var dataRow = $(
										"#storage-table-distributeRejectAddPage")
										.datagrid('getSelected');
								if (dataRow != null) {

									$.messager
											.confirm(
													"提示",
													"是否要删除选中的行？",
													function(r) {
														if (r) {
															if (dataRow != null) {
																var rowIndex = $(
																		"#storage-table-distributeRejectAddPage")
																		.datagrid(
																				'getRowIndex',
																				dataRow);
																$(
																		"#storage-table-distributeRejectAddPage")
																		.datagrid(
																				'updateRow',
																				{
																					index : rowIndex,
																					row : {}
																				});
																$(
																		"#storage-table-distributeRejectAddPage")
																		.datagrid(
																				'deleteRow',
																				rowIndex);
																var rowlen = $(
																		"#storage-table-distributeRejectAddPage")
																		.datagrid(
																				'getRows').length;
																if (rowlen < 15) {
																	$(
																			"#storage-table-distributeRejectAddPage")
																			.datagrid(
																					'appendRow',
																					{});
																}
																editRowIndex = undefined;
																$(
																		"#storage-table-distributeRejectAddPage")
																		.datagrid(
																				'clearSelections');
																footerReCalc();
															}
														}
													});
								}
							});
			
			//供应商
			$("#storage-distributeRejectAddPage-supplier")
					.supplierWidget(
							{
								name : 't_storage_delivery_enter',
								col : 'supplierid',
								width : 320,
								required : true,
								singleSelect : true,
								onlyLeafCheck : true,
								onSelect : function(data) {
									if (data == null) {
										return false;
									}
									$("#storage-supplier-showid-distributeRejectAddPage").text("编号：" + data.id);
								},
								onClear : function() {
									$("#storage-supplier-showid-distributeRejectAddPage").text("");
								}
							});
			
			<c:if test="${billtype==2 }">
			//客户参照窗口
			$("#storage-distributeRejectEditPage-customer").customerWidget({
				name:'t_storage_delivery_enter',
				col:'customerid',
				singleSelect:true,
				isdatasql:false,
				required:true,
				onlyLeafCheck:false
			<c:if test="${storageDeliveryEnter.sourcetype==0}">
				,
				onSelect:function(data){
					if(data==null){
						return false;
					}
					$("#pcustomerid").val(data.pid);
					$("#customersort").val(data.customersort);
					$("#deptid").val(data.salesdeptid);
					$("#span-customerid").text("编号: "+data.id);
					$("#storage-distributeRejectAddPage-remark").focus();			
				},
				onClear:function(){
					$("#span-customerid").text("");
					$("#pcustomerid").val("");
					$("#customersort").val("");
					$("#deptid").val("");
				}	
			</c:if>
			});
				<c:if test="${storageDeliveryEnter.sourcetype==0}">
					$("#storage-distributeRejectEditPage-customer").customerWidget('setText','${customerName}')
	    			$("#storage-distributeRejectEditPage-customer").customerWidget('setValue','${storageDeliveryEnter.customerid}')
				</c:if>
			</c:if>
			
			
			//入库仓库
			$("#storage-distributeRejectAddPage-storage").widget({
				name : 't_purchase_buyorder',
				col : 'storageid',
				width : 150,
				required : true,
				singleSelect : true,
				onlyLeafCheck : true,
				onSelect : function() {
				}
			});
			
			$("#storage-distributeRejectAddPage-remark").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					distributeRejectDetailAddDialog("${billtype}")
				}
		    });
			
		})
		  		

		$("#select option").eq(parseInt("${storageDeliveryEnter.status}")).attr("selected", "selected");
		$("#storage-distributeRejectAddPage-billtype option").eq(parseInt("${storageDeliveryEnter.billtype}")-1).attr("selected","selected");
		$("#storage-distributeRejectAddPage-sourcetype option").eq(parseInt("${storageDeliveryEnter.sourcetype}")).attr("selected", "selected");

		
		$("#storage-buttons-distributeRejectPage").buttonWidget("setDataID",  {id:'${storageDeliveryEnter.id}',state:'${storageDeliveryEnter.status}',type:'edit'});
		
		$("#storage-buttons-distributeRejectPage").buttonWidget("enableButton", 'button-add');
    	$("#storage-buttons-distributeRejectPage").buttonWidget("enableButton", 'button-save');
    	$("#storage-buttons-distributeRejectPage").buttonWidget("enableButton", 'button-saveaudit');
    	$("#storage-buttons-distributeRejectPage").buttonWidget("enableButton", 'button-delete');
    	$("#storage-buttons-distributeRejectPage").buttonWidget("enableButton", 'button-audit');
		

		</script>
</body>
</html>
