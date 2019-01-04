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
	  	<form action="storage/deliveryout/deliveryOutSave.do" id="storage-form-deliveryOutAddPage" method="post">
	  		<input  type="hidden" id="storage-deliveryout-datagridValues" name="detail"/>
	  		<input  type="hidden" id="storage-deliveryout-foots" name="foot"/>
	  		<input  type="hidden"  name="storageDeliveryOut.sourcetype" value="0"/>
	  		<!-- 状态 -->
	  		<input  type="hidden"  name="storageDeliveryOut.status" value="2"/>
	  		<input  type="hidden"  name="storageDeliveryOut.billtype" value="${billtype}"/>
	  		<input type="hidden" id="saveAndAudit" name="saveAndAudit" value="" />
	  		<div data-options="region:'north',border:false" style="height:130px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="4" cellspacing="4">
					<tr>
						<td style="width:80px;">编号：</td>
						<td style="width:165px;"><input type="text" class="len150" readonly="readonly" /></td>
						<td style="width:80px;">业务日期：</td>
						<td style="width:165px;"><input type="text" class="len150" id="storage-DeliveryOutAddPage-businessdate" name="storageDeliveryOut.businessdate" value='${busdate }' readonly="readonly" class="easyui-validatebox" required="true" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
						<td style="width:80px;">状态：</td>
				    	<td style="width:165px;">
				    		<input id="statusForDetail" type="hidden" value="add"></input>
				    		<select disabled="disabled" class="len150"><option>新增</option></select>
				    	</td>
				    </tr>	
				    <c:if test="${billtype==1 }">
				    <tr>
				    	
						<td style="">供应商：</td>
						<td colspan="3"><input type="text" id="storage-DeliveryOutAddPage-supplier" style="width:320px;" name="storageDeliveryOut.supplierid" />
								<span id="storage-supplier-showid-DeliveryOutAddPage" style="margin-left:5px;line-height:25px;"></span>
						</td>
                        <td style="">出库仓库：</td>
                        <td><input type="text" id="storage-DeliveryOutAddPage-storage" name="storageDeliveryOut.storageid" /></td>
					</tr>
					
					<tr>
						<td style="width:60px;">备注：</td>
						<td colspan="5">
							<input type="text" style="width:672px;" id="storage-DeliveryOutAddPage-remark" name="storageDeliveryOut.remark"/>
						</td>	
					</tr>
					</c:if>
					
					<c:if test="${billtype==2 }">
					  <tr>
				    	
						<td style="">供应商：</td>
						<td colspan="3"><input type="text" id="storage-DeliveryOutAddPage-supplier" style="width:320px;" name="storageDeliveryOut.supplierid" />
								<span id="storage-supplier-showid-DeliveryOutAddPage" style="margin-left:5px;line-height:25px;"></span>
						</td>
                        <td style="">出库仓库：</td>
                        <td><input type="text" id="storage-DeliveryOutAddPage-storage" name="storageDeliveryOut.storageid" /></td>
					  </tr>
					
					  <tr>
					  	<td style="">客户:</td>  
                        <td colspan="3"><input id="storage-DeliveryOutAddPage-customer" type="text" name="storageDeliveryOut.customerid" style="width: 320px;"/>
                            	<span id="storage-span-customer" style="margin-left:5px;line-height:25px;"></span>
                            	<input id="pcustomerid" type="hidden" name="storageDeliveryOut.pcustomerid" value=""/>
                            	<input id="customersort" type="hidden" name="storageDeliveryOut.customersort" value=""/>
                            	<input id="deptid" type="hidden" name="storageDeliveryOut.deptid" value=""/>
                        </td>

						<td style="width:60px;">客户单号：</td>
						<td>
							<input type="text" style="width:150px;" id="storage-DeliveryOutAddPage-customerbill" name="storageDeliveryOut.customerbill"/>
						</td>
					  </tr>
						<tr>
							<td style="width:60px;">备注：</td>
							<td colspan="5">
								<input type="text" style="width:670px;" id="storage-DeliveryOutAddPage-remark" name="storageDeliveryOut.remark"/>
							</td>
						</tr>
					</c:if>
				</table>
	  		</div>
	  		<div data-options="region:'center',border:false">
	  			<table id="storage-table-deliveryOutPage"></table>
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
  		var $potable=$("#storage-table-deliveryOutPage");
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

    	
    	$("#storage-table-deliveryOutPage").datagrid({
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
  	 			$("#storage-table-deliveryOutPage").datagrid('reloadFooter',[
					{goodsid: '合计',unitnum: '0',taxamount : '0',volume : '0',weight :'0',allboxnum : '0'}
				]);
  	 		},
        	onDblClickRow: function(rowIndex, rowData){ //选中行
        		editRowIndex=rowIndex;
  	 			if(rowData.goodsid && rowData.goodsid!=""){
  	 				distributeRejectDetailEditDialog(rowData);
  	 			}else{
  	 				distributeRejectDetailAddDialog("${billtype}");
  	 			}
        	},
  	 		onRowContextMenu:function(e, rowIndex, rowData){
  	 			e.preventDefault();
  	 			var $contextMenu=$('#storage-Button-tableMenu');
  	 			$contextMenu.menu('show', {
					left : e.pageX,
					top : e.pageY
				});
  	 			$("#storage-table-DeliveryOutAddPage").datagrid('selectRow', rowIndex);
				editRowIndex=rowIndex;
                $contextMenu.menu('enableItem', '#storage-tableMenu-itemAdd');
                $contextMenu.menu('enableItem', '#storage-tableMenu-itemEdit');
                $contextMenu.menu('enableItem', '#storage-tableMenu-itemDelete');
  	 		}
  	  	}).datagrid("columnMoving");
    	
    	//添加按钮事件
		$("#storage-tableMenu-itemAdd").unbind("click").bind("click",function(){
			if($("#storage-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			distributeRejectDetailAddDialog("${billtype}");			
		});
    	
		//编辑按钮事件
		$("#storage-tableMenu-itemEdit").unbind("click").bind("click",function(){
			if($("#storage-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			var data=$("#storage-table-deliveryOutPage").datagrid('getSelected');
			
			if(null!=data && data.goodsid !=null && data.goodsid!=""){
				distributeRejectDetailEditDialog(data);		
			}else{
				distributeRejectDetailAddDialog("${billtype}");	
			}
		});
		
		//删除按钮事件
		$("#storage-tableMenu-itemDelete").unbind("click").bind("click",function(){
			if($("#storage-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			var dataRow=$("#storage-table-deliveryOutPage").datagrid('getSelected');
			if(dataRow!=null){
				
				$.messager.confirm("提示","是否要删除选中的行？", function(r){
					if (r){
	        			if(dataRow!=null){
							var rowIndex =$("#storage-table-deliveryOutPage").datagrid('getRowIndex',dataRow);
							$("#storage-table-DeliveryOutAddPage").datagrid('updateRow',{
								index:rowIndex,
								row:{}
							});
							$("#storage-table-deliveryOutPage").datagrid('deleteRow', rowIndex);
							var rowlen=$("#storage-table-deliveryOutPage").datagrid('getRows').length; 
							if(rowlen<15){
								$("#storage-table-deliveryOutPage").datagrid('appendRow', {});
							}
							editRowIndex=undefined;
							$("#storage-table-deliveryOutPage").datagrid('clearSelections');
							footerReCalc();
	        			}
					}
				});	
			}		
		});
    	
    	
    	//供应商
    	$("#storage-DeliveryOutAddPage-supplier").supplierWidget({ 
			name:'t_storage_delivery_enter',
			col:'supplierid',
			width:320,
			required:true,
			singleSelect:true,
			onlyLeafCheck:true,
			onSelect:function(data){
				if(data==null){
					return false;
				}
				$("#storage-supplier-showid-DeliveryOutAddPage").text("编号: "+ data.id);
				
				if(data.storageid!=null && data.storageid!=""){
    					$("#storage-DeliveryOutAddPage-storage").widget("setValue",data.storageid);
    			}else{
    					$("#storage-DeliveryOutAddPage-storage").widget("clear"); 					
    			}
				<c:if test="${billtype==1 }">
				$("#storage-DeliveryOutAddPage-remark").focus();
				</c:if>
				<c:if test="${billtype==2 }">
					$("#storage-DeliveryOutAddPage-customer").focus();
				</c:if>
				
			},
			onClear:function(){
				$("#storage-supplier-showid-DeliveryOutAddPage").text("");
			}
		});	
    	
    	<c:if test="${billtype==2 }">
		//客户参照窗口
		$("#storage-DeliveryOutAddPage-customer").customerWidget({
			name:'t_storage_delivery_enter',
			col:'customerid',
			singleSelect:true,
			isdatasql:false,
			required:true,
			onlyLeafCheck:false,
			onSelect:function(data){
				if(data==null){
					return false;
				}
				
				
				$("#pcustomerid").val(data.pid);
				$("#customersort").val(data.customersort);
				$("#deptid").val(data.salesdeptid);
				
				$("#storage-span-customer").text("编号: "+data.id);
				$("#storage-DeliveryOutAddPage-remark").focus();			
			},
			onClear:function(){
				$("#storage-span-customer").text("");
				$("#pcustomerid").val("");
				$("#customersort").val("");
				$("#deptid").val("");
			}
		});
		</c:if>
    	
    	//入库仓库
    	$("#storage-DeliveryOutAddPage-storage").widget({ 
			name:'t_purchase_buyorder',
			col:'storageid',
			width:150,
			required:true,
			singleSelect:true,
			onlyLeafCheck:true,
			onSelect:function(){
				<c:if test="${billtype==1 }">
				$("#storage-DeliveryOutAddPage-remark").focus();
				</c:if>
				<c:if test="${billtype==2 }">
					$("#storage-DeliveryOutAddPage-customer").focus();
				</c:if>
				
			}
		});
    	
    	
    	
    	
    	
    	
    	$("#storage-DeliveryOutAddPage-remark").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				distributeRejectDetailAddDialog("${billtype}")
			}
	    });
	    
		
		$("#storage-buttons-deliveryOutPage").buttonWidget("enableButton", 'button-add');
    	$("#storage-buttons-deliveryOutPage").buttonWidget("enableButton", 'button-save');
    	$("#storage-buttons-deliveryOutPage").buttonWidget("enableButton", 'button-saveaudit');
    	$("#storage-buttons-deliveryOutPage").buttonWidget("disableButton", 'button-delete');
    	$("#storage-buttons-deliveryOutPage").buttonWidget("disableButton", 'button-audit');
		$("#storage-buttons-deliveryOutPage").buttonWidget("disableButton", 'button-oppaudit');
    	$("#storage-buttons-deliveryOutPage").buttonWidget("disableButton", 'button-returnorder-check');
  	})
</script>
</body>
</html>
