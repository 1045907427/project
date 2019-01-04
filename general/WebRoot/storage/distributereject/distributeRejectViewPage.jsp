<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>配送入库单查看</title>
<%@include file="/include.jsp"%>
</head>
<body>
	<div class="easyui-panel" title="" data-options="fit:true,border:false">


	 <div class="easyui-layout" data-options="fit:true">
	  	<form action="storage/distrtibution/distributeRejectInfoAdd.do" id="storage-form-distributeRejectAddPage" method="post">
	  		<input  type="hidden" id="storage-distrtibution-status" name="storageDeliveryEnter.status" value="${storageDeliveryEnter.status }"/>
	  		<input  type="hidden" id="storage-distrtibution-Entryifchecked" value="${storageDeliveryEnter.ischeck }"/>
	  		<input type="hidden" id="storage-distrtibution-printtime"  value="${storageDeliveryEnter.printtimes}" />
	  		<div data-options="region:'north',border:false" style="height:100px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="4" cellspacing="4">
					<tr>
						<td style="width:80px;">编号：</td>
						<td style="width:165px;"><input type="text" class="len150" readonly="readonly" value="${storageDeliveryEnter.id }"/></td>
						<td style="width:80px;">业务日期：</td>
						<td style="width:165px;"><input type="text" class="len150" id="storage-distributeRejectAddPage-businessdate" name="storageDeliveryEnter.businessdate" value='${storageDeliveryEnter.businessdate }' readonly="readonly" class="easyui-validatebox" required="true" /></td>
						<td style="width:80px;">状态：</td>
				    	<td style="width:165px;">
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
								<input type="text" id="storage-distributeRejectAddPage-supplier" style="width: 320px;" name="storageDeliveryEnter.supplierid" value="${supplierName}" readonly="readonly" /> 
								<span  id="storage-supplier-showid-distributeRejectAddPage" style="margin-left: 5px; line-height: 25px;">编号：${storageDeliveryEnter.supplierid }</span>
							</td>
                        <td style="">入库仓库：</td>
                        <td><input type="text" id="storage-distributeRejectAddPage-storage" name="storageDeliveryEnter.storageid" value="<c:out value="${storageDeliveryEnter.storageid }"></c:out>"  readonly="readonly"/></td>
					</tr>
					
					<tr>
						<td style="width:60px;">备注：</td>
						<td colspan="5">
							<input type="text" style="width:672px;" id="storage-distributeRejectAddPage-remark" name="storageDeliveryEnter.remark" readonly="readonly" value="${ storageDeliveryEnter.remark}"/>
						</td>	
					</tr>
				</c:if>
				    
				    
				<c:if test="${billtype==2 }">
					 <tr>
							<td style="">供应商：</td>
							<td colspan="3">
								<input type="text" id="storage-distributeRejectAddPage-supplier" style="width: 320px;" name="storageDeliveryEnter.supplierid" value="${supplierName}" readonly="readonly" /> 
								<span  id="storage-supplier-showid-distributeRejectAddPage" style="margin-left: 5px; line-height: 25px;">编号：${storageDeliveryEnter.supplierid }</span></td>
                        <td style="">入库仓库：</td>
                        <td>
                        	<input type="text" id="storage-distributeRejectAddPage-storage" name="storageDeliveryEnter.storageid" value="<c:out value="${storageDeliveryEnter.storageid }"></c:out>"  readonly="readonly"/></td>
					</tr>
					
					<tr>
						<td style="">客户：</td>
							<td colspan="3">																																											<!-- customerName -->
								<input type="text" id="storage-distributeRejectEditPage-customer" style="width: 320px;" name="storageDeliveryEnter.customerid" value="${customerName}" readonly="readonly" /> 
								  <span style="margin-left:5px;line-height:25px;">编号：${storageDeliveryEnter.customerid }</span>	
							</td>

						<td style="width:60px;">客户单号：</td>
						<td colspan="5">
							<input type="text" style="width:150px;" id="storage-distributeRejectAddPage-customerbill" name="storageDeliveryEnter.customerbill" value="${storageDeliveryEnter.customerbill }" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td style="width:60px;">备注：</td>
						<td colspan="5">
							<input type="text" style="width:670px;" id="storage-distributeRejectAddPage-remark" name="storageDeliveryEnter.remark" value="${storageDeliveryEnter.remark }" readonly="readonly"/>
						</td>
					</tr>
				</c:if>
				    
				    
				   <%--  <tr>
						<c:if test="${billtype==1 }">
							<td style="">供应商：</td>
							<td colspan="3">
								<input type="text" id="storage-distributeRejectAddPage-supplier" style="width: 320px;" name="storageDeliveryEnter.supplierid" value="${supplierName}" readonly="readonly" /> 
								<span  id="storage-supplier-showid-distributeRejectAddPage" style="margin-left: 5px; line-height: 25px;"></span></td>
							</c:if>
							<c:if test="${billtype==2 }">
							<td style="">客户：</td>
							<td colspan="3">																																											<!-- customerName -->
								<input type="text" id="storage-distributeRejectEditPage-customer" style="width: 320px;" name="storageDeliveryEnter.customerid" value="${customerName}" readonly="readonly" /> 
								  <span style="margin-left:5px;line-height:25px;">编号：${storageDeliveryEnter.customerid }</span>	
							</td>
							</c:if>
                        <td style="">入库仓库：</td>
                        <td><input type="text" id="storage-distributeRejectAddPage-storage" name="storageDeliveryEnter.storageid" value="<c:out value="${storageDeliveryEnter.storageid }"></c:out>"  readonly="readonly"/></td>
					</tr>
					
					<tr>
						<td style="width:60px;">备注：</td>
						<td colspan="5">
							<input type="text" style="width:672px;" id="storage-distributeRejectAddPage-remark" name="storageDeliveryEnter.remark" readonly="readonly"/>
						</td>	
					</tr> --%>
					
				</table>
	  		</div>
	  		<div data-options="region:'center',border:false">
	  			<table id="storage-table-distributeRejectAddPage"></table>
	  		</div>
	  	</form> 
	  </div>





	</div>

  <div id="storage-Button-tableMenu" class="easyui-menu" style="width:120px;display: none;">
        <div id="storage-tableMenu-itemAdd" iconCls="button-add">添加</div>
        <div id="storage-tableMenu-itemEdit" iconCls="button-edit">编辑</div>
        <div id="storage-tableMenu-itemDelete" iconCls="button-delete">删除</div>
  </div>



<script type="text/javascript">
	var editRowIndex = undefined;
	
  	function getAddRowIndex(){
  		var $potable=$("#storage-table-distributeRejectAddPage");
  		var dataRows=$potable.datagrid('getRows');
  		
  		var rindex=0;
  		for(rindex=0;rindex<dataRows.length;rindex++){
  	  		if(dataRows[rindex].goodsid==null || dataRows[rindex].goodsid==""){
  	  	  		break;
  	  		}
  		}
  		if(rindex==dataRows.length){
  	  		$potable.datagrid('appendRow',{});
  		}
  		return rindex;
   	}
	
	
	
	
	
	
    
    $(function(){
    	
    	
    	
    	
    	$("#storage-table-distributeRejectAddPage").datagrid({
	  	  	fit:true,
	  	  	striped:true,
	 		method:'post',
	 		rownumbers:true,
	 		//idField:'id',
	 		singleSelect:true,
	 		showFooter:true,
  	 		data:[{},{},{},{},{},{},{},{},{},{},{},{}],
  	 		authority:tableColJson,
  	 		frozenColumns: tableColJson.frozen,
			columns:tableColJson.common,
  	 		onLoadSuccess:function(){
  	 			$("#storage-table-distributeRejectAddPage").datagrid('reloadFooter',[
					{goodsid: '合计',unitnum: '0',taxamount : '0',volume : '0',weight :'0',allboxnum : '0'}
				]);
				footerReCalc()
  	 		},
  	 		data : JSON.parse('${StorageDeliveryEnterDetailList}'),
        	onDblClickRow: function(rowIndex, rowData){ //选中行
        	},
  	  	}).datagrid("columnMoving");
    	
    	//添加按钮事件
		$("#storage-tableMenu-itemAdd").unbind("click").bind("click",function(){
			if($("#storage-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			distributeRejectDetailAddDialog();			
		});
    	
		//编辑按钮事件
		$("#storage-tableMenu-itemEdit").unbind("click").bind("click",function(){
			if($("#storage-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			var data=$("#storage-table-distributeRejectAddPage").datagrid('getSelected');
			
			if(null!=data && data.goodsid !=null && data.goodsid!=""){
				distributeRejectDetailEditDialog(data);		
			}else{
				distributeRejectDetailAddDialog();	
			}
		});
		
		//删除按钮事件
		$("#storage-tableMenu-itemDelete").unbind("click").bind("click",function(){
			if($("#storage-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			var dataRow=$("#storage-table-distributeRejectAddPage").datagrid('getSelected');
			if(dataRow!=null){
				
				$.messager.confirm("提示","是否要删除选中的行？", function(r){
					if (r){
	        			if(dataRow!=null){
							var rowIndex =$("#storage-table-distributeRejectAddPage").datagrid('getRowIndex',dataRow);
							$("#storage-table-distributeRejectAddPage").datagrid('updateRow',{
								index:rowIndex,
								row:{}
							});
							$("#storage-table-distributeRejectAddPage").datagrid('deleteRow', rowIndex);
							var rowlen=$("#storage-table-distributeRejectAddPage").datagrid('getRows').length; 
							if(rowlen<15){
								$("#storage-table-distributeRejectAddPage").datagrid('appendRow', {});
							}
							editRowIndex=undefined;
							$("#storage-table-distributeRejectAddPage").datagrid('clearSelections');
							footerReCalc();
	        			}
					}
				});	
			}		
		});
    	
    	
    	
    	//入库仓库
    	$("#storage-distributeRejectAddPage-storage").widget({ 
			name:'t_purchase_buyorder',
			col:'storageid',
			width:150,
			required:true,
			singleSelect:true,
			onlyLeafCheck:true,
			onSelect:function(){
				$("#storage-distributeRejectAddPage-remark").focus();
			}
		});
    	
    	
    	
    	
    	
    	
    	
    	$("#select option").eq(parseInt("${storageDeliveryEnter.status}")).attr("selected", "selected");
		$("#storage-distributeRejectAddPage-sourcetype option").eq(parseInt("${storageDeliveryEnter.sourcetype}")).attr("selected", "selected");
    	
    	
    	$("#storage-buttons-distributeRejectPage").buttonWidget("enableButton", 'button-add');
    	$("#storage-buttons-distributeRejectPage").buttonWidget("disableButton", 'button-save');
    	$("#storage-buttons-distributeRejectPage").buttonWidget("disableButton", 'button-saveaudit');
    	$("#storage-buttons-distributeRejectPage").buttonWidget("disableButton", 'button-delete');
    	$("#storage-buttons-distributeRejectPage").buttonWidget("disableButton", 'button-audit');
    	//关闭状态,验收按钮失效
    	if("${storageDeliveryEnter.status }"=='4'){
    		$("#storage-buttons-distributeRejectPage").buttonWidget("disableButton", 'button-returnorder-check');
    	}
    	
    
  	})
</script>
</body>
</html>
