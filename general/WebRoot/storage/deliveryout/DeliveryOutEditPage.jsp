<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>配送入库单修改</title>
<%@include file="/include.jsp"%>
</head>
<body>
	<div class="easyui-panel" title="" data-options="fit:true,border:false">

	 <div class="easyui-layout" data-options="fit:true">
	  	<form action="storage/deliveryout/deliveryOutEditSave.do" id="storage-form-deliveryOutAddPage" method="post">
	  		<input  type="hidden" id="storage-deliveryout-datagridValues" name="detail"/>
	  		<input  type="hidden" id="storage-deliveryout-foots" name="foot"/>
	  		<input  type="hidden"  id="storage-deliveryout-check" value="${storageDeliveryOut.ischeck}"/>
	  		<input  type="hidden"  id="storage-deliveryout-printtimes" value="${storageDeliveryOut.printtimes}"/>
	  		<!-- 状态 -->
	  		<input  type="hidden"  name="storageDeliveryOut.status" value="2"/>
	  		<input  type="hidden"  name="storageDeliveryOut.billtype" value="${billtype}"/>
	  		<input  type="hidden"  name="storageDeliveryOut.sourcetype" value="${storageDeliveryOut.sourcetype}"/>
	  		
	  		<input type="hidden" id="saveAndAudit" name="saveAndAudit" value="" />
	  		<div data-options="region:'north',border:false" style="height:130px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="4" cellspacing="4">
					<tr>
						<td style="width:80px;">编号：</td>
						<td style="width:165px;"><input type="text" class="len150" readonly="readonly" name="storageDeliveryOut.id" value="${storageDeliveryOut.id}" /></td>
						<td style="width:80px;">业务日期：</td>
						<td style="width:165px;"><input type="text" class="len150" id="storage-DeliveryOutAddPage-businessdate" name="storageDeliveryOut.businessdate" value="${storageDeliveryOut.businessdate }" readonly="readonly" class="easyui-validatebox" required="true" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
						<td style="width:80px;">状态：</td>
				    	<td style="width:165px;">
				    		<input  type="hidden"  id="storage-storageDeliveryOut-status" value="${storageDeliveryOut.status}"/>
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
							<td colspan="3"><input type="text" id="storage-DeliveryOutAddPage-supplier" style="width:320px;" name="storageDeliveryOut.supplierid"  value="${storageDeliveryOut.supplierid }" text="<c:out value="${supplierName }" ></c:out>"readonly="readonly"/>
								<span id="storage-supplier-showid-DeliveryOutEditPage" style="margin-left:5px;line-height:25px;">编号：${storageDeliveryOut.supplierid }</span>
						</td>
                        <td style="">出库仓库：</td>
                        <td><input type="text" id="storage-DeliveryOutAddPage-storage" name="storageDeliveryOut.storageid"  value="<c:out value="${storageDeliveryOut.storageid }"></c:out>"  readonly="readonly"/></td>
					</tr>
					
					<tr>
						<td style="width:60px;">备注：</td>
						<td colspan="5">
							<input type="text" style="width:672px;" id="storage-DeliveryOutAddPage-remark" name="storageDeliveryOut.remark" value="${storageDeliveryOut.remark }"/>
						</td>	
					</tr>
					</c:if>
				    
				    
				   <c:if test="${billtype==2 }">
					  <tr>
				    	
						<td style="">供应商：</td>  
							<td colspan="3"><input type="text" id="storage-DeliveryOutAddPage-supplier" style="width:320px;" name="storageDeliveryOut.supplierid"  value="${storageDeliveryOut.supplierid }" text="<c:out value="${supplierName }" ></c:out>"readonly="readonly"/>
								<span id="storage-supplier-showid-DeliveryOutEditPage" style="margin-left:5px;line-height:25px;">编号：${storageDeliveryOut.supplierid }</span>
						</td>
                        <td style="">出库仓库：</td>
                        <td><input type="text" id="storage-DeliveryOutAddPage-storage" name="storageDeliveryOut.storageid"  value="<c:out value="${storageDeliveryOut.storageid }"></c:out>"  readonly="readonly"/></td>
					  </tr>
					
					  <tr>
					  
					 <c:if test="${storageDeliveryOut.sourcetype==0}">
						 <td style="">客户:</td>
                            <td colspan="3">
                             <input id="storage-DeliveryOutEditPage-customer" style="width:320px;" type="text" name="storageDeliveryOut.customerid""/> 
                          	 <span id="span-customerid"style="margin-left:5px;line-height:25px;">编号：${storageDeliveryOut.customerid }</span>	
                          	 <input id="pcustomerid" type="hidden" name="storageDeliveryOut.pcustomerid" value=""/>
                             <input id="customersort" type="hidden" name="storageDeliveryOut.customersort" value=""/>
                             <input id="deptid" type="hidden" name="storageDeliveryOut.deptid" value=""/>
                        </td>	
					 </c:if> 
					 <c:if test="${storageDeliveryOut.sourcetype!=0}">
					  	<td style="">客户:</td>
                            <td colspan="3">
                             <input id="storage-DeliveryOutEditPage-customer" style="width:320px;" type="text" name="storageDeliveryOut.customerid" value="${storageDeliveryOut.customerid }" text="<c:out value="${customerName}" ></c:out>"readonly="readonly"/> 
                          	 <span style="margin-left:5px;line-height:25px;">编号：${storageDeliveryOut.customerid }</span>	
                        </td>	    	
					 </c:if>
					<td style="width:60px;">客户单号：</td>
					<td>
						<input type="text" style="width:150px;" id="storage-DeliveryOutAddPage-customerbill" name="storageDeliveryOut.customerbill"  value="${storageDeliveryOut.customerbill }"/>
					</td>
					</tr>
					<tr>
						<td style="width:60px;">备注：</td>
						<td colspan="5">
							<input type="text" style="width:670px;" id="storage-DeliveryOutAddPage-remark" name="storageDeliveryOut.remark"  value="${storageDeliveryOut.remark }"/>
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
				var dataRows = $("#storage-table-deliveryOutPage").datagrid('getRows');
					var rowlen = dataRows.length;
					if (rowlen < 12) {
						for (var i = rowlen; i < 12; i++) {
							$("#storage-table-deliveryOutPage").datagrid('appendRow', {});
						}
					} else {
						$("#storage-table-deliveryOutPage").datagrid('appendRow', {});
					}
					$("#storage-table-deliveryOutPage").datagrid('reloadFooter', [ {goodsid : '合计',unitnum : '0',taxamount : '0',volume : '0',weight : '0',allboxnum : '0'} ]);
					footerReCalc()
				
  	 		},
        	onDblClickRow: function(rowIndex, rowData){ //选中行        		        	
        		
  	 			if(rowData.goodsid && rowData.goodsid!=""){
  	 				editRowIndex=rowIndex;
  	 				distributeRejectDetailEditDialog(rowData);
  	 			}else{
	  	 			var sourcetype="${storageDeliveryOut.sourcetype}";
	        		if(sourcetype!="0"){
	        			//$.messager.alert("提示","有来源的单据不能添加删除商品")
	        			return false;
	        		}
	        		editRowIndex=rowIndex;
  	 				distributeRejectDetailAddDialog("${billtype}");
  	 			}
        	},
        	data : JSON.parse('${StorageDeliveryOutDetailList}'),
  	 		onRowContextMenu:function(e, rowIndex, rowData){
  	 			e.preventDefault();
  	 			var $contextMenu=$('#storage-Button-tableMenu');
  	 			$contextMenu.menu('show', {
					left : e.pageX,
					top : e.pageY
				});
  	 			$("#storage-table-DeliveryOutEditPage").datagrid('selectRow', rowIndex);
				editRowIndex=rowIndex;
                $contextMenu.menu('enableItem', '#storage-tableMenu-itemAdd');
                $contextMenu.menu('enableItem', '#storage-tableMenu-itemEdit');
                $contextMenu.menu('enableItem', '#storage-tableMenu-itemDelete');
                var sourcetype="${storageDeliveryOut.sourcetype}";
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
		$("#storage-tableMenu-itemAdd").unbind("click").bind("click",function(){
			if($("#storage-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			var sourcetype="${storageDeliveryOut.sourcetype}";
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
							$("#storage-table-DeliveryOutEditPage").datagrid('updateRow',{
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
		
		 $("#storage-DeliveryOutEditPage-remark").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				distributeRejectDetailAddDialog("${billtype}")
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
				$("#storage-supplier-showid-DeliveryOutEditPage").text("编号："+ data.id);
				
			},
			onClear:function(){
				$("#storage-supplier-showid-DeliveryOutEditPage").text("");
			}
		});	
    	
    	<c:if test="${billtype==2 }">
		//客户参照窗口
		$("#storage-DeliveryOutEditPage-customer").customerWidget({
			name:'t_storage_delivery_enter',
			col:'customerid',
			singleSelect:true,
			width:320,
			isdatasql:false,
			required:true,
			onlyLeafCheck:false
			<c:if test="${storageDeliveryOut.sourcetype==0}">
			,
			onSelect:function(data){
				console.log(data)
				if(data==null){
					return false;
				}
				$("#pcustomerid").val(data.pid);
				$("#customersort").val(data.customersort);
				$("#deptid").val(data.salesdeptid);
				$("#span-customerid").text("编号: "+data.id);
				$("#storage-DeliveryOutAddPage-remark").focus();			
			},
			onClear:function(){
				$("#span-customerid").text("");
				$("#pcustomerid").val("");
				$("#customersort").val("");
				$("#deptid").val("");
			}	
			</c:if>
		});
		    	 <c:if test="${storageDeliveryOut.sourcetype==0}">
		    		$("#storage-DeliveryOutEditPage-customer").customerWidget('setText','${customerName}')
		    		$("#storage-DeliveryOutEditPage-customer").customerWidget('setValue','${storageDeliveryOut.customerid}')
		    	</c:if>
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
				$("#storage-DeliveryOutEditPage-remark").focus();
			}
		});
    	$("#select option").eq(parseInt("${storageDeliveryOut.status}")).attr("selected", "selected");
    	
    	
    	
		$("#storage-buttons-deliveryOutPage").buttonWidget("enableButton", 'button-add');
    	$("#storage-buttons-deliveryOutPage").buttonWidget("enableButton", 'button-save');
    	$("#storage-buttons-deliveryOutPage").buttonWidget("enableButton", 'button-saveaudit');
    	$("#storage-buttons-deliveryOutPage").buttonWidget("enableButton", 'button-delete');
    	$("#storage-buttons-deliveryOutPage").buttonWidget("enableButton", 'button-audit');
    	$("#storage-buttons-deliveryOutPage").buttonWidget("disableButton", 'button-oppaudit');
  		
  	})
</script>
</body>
</html>
