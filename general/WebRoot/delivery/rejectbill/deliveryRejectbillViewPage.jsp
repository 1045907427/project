<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>查看代配送销售退单</title>
</head>
<body>
    
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="delivery-form-deliveryRejectbillAddPage" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height: 130px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input id="delivery-deliveryRejectbill-id" class="easyui-validatebox" name="deliveryRejectbill.id" value="${deliveryRejectbill.id }"  readonly="readonly" style="width:160px;"/></td>
	    				<td class="len80 right">业务日期：</td>
	    				<td class="len165"><input type="text" id="delivery-deliveryRejectbill-businessdate" style="width:160px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"  name="deliveryRejectbill.businessdate" value="${deliveryRejectbill.businessdate }" readonly="readonly" /></td>
	    				<td class="len80 right">状态：</td>
	    				<td class="len165"><select id="delivery-deliveryRejectbill-status-select" disabled="disabled" style="width:160px;">
	    				    <c:forEach items="${statusList }" var="list">
    							<c:choose>
    								<c:when test="${list.code == deliveryRejectbill.status}">
    									<option value="${list.code }" selected="selected">${list.codename }</option>
    								</c:when>
    								<c:otherwise>
    									<option value="${list.code }">${list.codename }</option>
    								</c:otherwise>
    							</c:choose>
    						</c:forEach>
    						</select></td>
	    			</tr>
	    			<tr>
	    			    <td class="len80 left">供应商：</td>
	    				<td   colspan="3" style="text-align: left">
	    					<input type="text" id="delivery-deliveryRejectbill-supplierid" name="deliveryRejectbill.supplierid" value="${deliveryRejectbill.supplierid }" style="width:320px;" />
	    				    <span id="delivery-deliveryRejectbill-supplier" style="margin-left:5px;line-height:25px;"></span>
	    				</td>
	    				<td class="len80 right">退货仓库：</td>
	    				<td>
	    					<input type="text" id="delivery-deliveryRejectbill-storageid" name="deliveryRejectbill.storageid" value="${deliveryRejectbill.storageid }" readonly="readonly" style="width:160px;"/>
	    				</td>
						</tr>
	    		     <tr>
	    		        <td class="len80 left">客户编号：</td>
	    				<td class="len165">
	    			    	<input type="text" id="delivery-deliveryRejectbill-customerid" name="deliveryRejectbill.customerid"  style="width:160px;"  value="${deliveryRejectbill.customerid }" readonly="readonly" />
	    				</td>
	    				<td class="len80 right">客户名称：</td>
	    				<td class="len165"><input type="text" id="delivery-deliveryRejectbill-customername" name="deliveryRejectbill.customername"   value="${deliveryRejectbill.customername }"  readonly="readonly"  style="width:160px;" /></td>
						<td class="len80 right">客户单号：</td>
						<td>
							<input type="text" id="delivery-deliveryRejectbill-sourceid" name="deliveryRejectbill.sourceid" value="${deliveryRejectbill.sourceid }" style="width:160px;" onfocus="frm_focus('deliveryRejectbill.sourceid');" onblur="frm_blur('deliveryRejectbill.sourceid');" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td class="len80 left">备注：</td>
						<td colspan="5">
							<input type="text" id="delivery-deliveryRejectbill-remark" name="deliveryRejectbill.remark" value="${deliveryRejectbill.sourceid }" style="width:690px;" onfocus="frm_focus('deliveryRejectbill.remark');" onblur="frm_blur('deliveryRejectbill.remark');" readonly="readonly"/>
						</td>
					</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="delivery-datagrid-deliveryRejectbillAddPage"></table>
	    	</div>
	    	<input type="hidden" id="delivery-deliveryRejectbill-deliveryRejectbillDetail" name="detailJson"/>
	    	<input type="hidden" id="delivery-deliveryRejectbill-saveaudit" name="saveaudit" value="save"/>
	    </form>
    </div>
   
    <div id="delivery-dialog-deliveryRejectbillAddPage"></div>
    <script type="text/javascript">
    	$(function(){
    		var supplier=$("#delivery-deliveryRejectbill-supplierid").val();
    		$("#delivery-deliveryRejectbill-supplier").html("  编号:" + supplier);
    		var customer=${deliveryRejectbill.customerid }
    		$("#delivery-deliveryRejectbill-customer").html("编号:" + customer);
    		$("#delivery-datagrid-deliveryRejectbillAddPage").datagrid({ //采购入库单明细信息编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			fit:true,
    			singleSelect: false,
    			checkOnSelect:true,
    	 		selectOnCheck:true,
    			data:JSON.parse('${detailList}'),
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$("#delivery-datagrid-deliveryRejectbillAddPage").datagrid('selectRow', rowIndex);
                    $("#delivery-contextMenu-deliveryRejectbillAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onSortColumn:function(sort, order){
    				var rows = $("#delivery-datagrid-deliveryRejectbillAddPage").datagrid("getRows");
    				var dataArr = [];
    				for(var i=0;i<rows.length;i++){
    					if(rows[i].goodsid!=null && rows[i].goodsid!=""){
    						dataArr.push(rows[i]);
    					}
    				}
    				dataArr.sort(function(a,b){
    					if(order=="asc"){
    						return a[sort]>b[sort]?1:-1
    					}else{
    						return a[sort]<b[sort]?1:-1
    					}
    				});
    				$("#delivery-datagrid-deliveryRejectbillAddPage").datagrid("loadData",dataArr);
    				return false;
    			},
    			onLoadSuccess: function(data){
    				if(data.rows.length<12){
    					var j = 12-data.rows.length;
	            		for(var i=0;i<j;i++){
	            			$(this).datagrid('appendRow',{});
	            		}
    				}else{
    					$(this).datagrid('appendRow',{});
    				}
    				countTotal();
    			},
    			onCheckAll:function(){
					countTotal();
				},
				onUncheckAll:function(){
					countTotal();
				},
				onCheck:function(){
					countTotal();
				},
				onUncheck:function(){
					countTotal();
				},
    			onDblClickRow: function(rowIndex, rowData){
    				$(this).datagrid('clearSelections').datagrid('selectRow',rowIndex);
    				if(rowData.goodsid == undefined){
    					beginAddDetail();
    				}
    				else{
    					beginEditDetail();
    				}
    			},
    			
    		}).datagrid('columnMoving');
    		
    		//仓库选择
    		$("#delivery-deliveryRejectbill-storageid").widget({
    			referwid:'RL_T_BASE_STORAGE_INFO',
	    		width:160,
				col:'name',
				singleSelect:true,
				required:true,
				initValue:'${deliveryRejectbill.storageid}'
    		});
    		//供应商选择
    		$("#delivery-deliveryRejectbill-supplierid").widget({ 
    			name:'t_purchase_buyorder',
    			col:'supplierid',
    			readonly:true,
    			width:320,
    			singleSelect:true,
    			onlyLeafCheck:true
    		});
    		//订单提交
    		$("#delivery-form-deliveryRejectbillAddPage").form({  
			    onSubmit: function(){  
			    	var json = $("#delivery-datagrid-deliveryRejectbillAddPage").datagrid('getRows');
		
					$("#delivery-deliveryRejectbill-deliveryRejectbillDetail").val(JSON.stringify(json));
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	var json = $.parseJSON(data);
			    	if(json.flag){
			    		if(json.auditflag){
			    			$.messager.alert("提醒","保存并审核成功");
			    			$("#delivery-deliveryRejectbill-status-select").val("3");
			    			$("#delivery-buttons-deliveryRejectbillPage").buttonWidget("setDataID",{id:'${deliveryRejectbill.id}',state:'3',type:'view'});
			    		}else{
			    			var msg = "";
			    			if(json.msg!=null){
			    				msg = json.msg;
			    			}
			    			$.messager.alert("提醒","保存成功."+msg);
			    		}
			    	}else{
			    		$.messager.alert("提醒","保存失败."+json.msg);
			    	}
			    }  
			}); 
    	});
    	
    	//控制按钮状态
    	$("#delivery-buttons-deliveryRejectbillPage").buttonWidget("setDataID",{id:'${deliveryRejectbill.id}',state:'${deliveryRejectbill.status}',type:'v'});
    	$("#delivery-hidden-billid").val("${deliveryRejectbill.id}");
/*
    	<c:if test="${deliveryRejectbill.status=='2'}">
	    	$("#delivery-buttons-allocateOutPage").buttonWidget("enableButton","button-print");
	    	$("#delivery-buttons-allocateOutPage").buttonWidget("enableButton","button-preview");
		</c:if>
		<c:if test="${allocateOut.status !='2'}">
	    	$("#delivery-buttons-allocateOutPage").buttonWidget("disableButton","button-print");
	    	$("#delivery-buttons-allocateOutPage").buttonWidget("disableButton","button-preview");
		</c:if>
	
    	$("#delivery-hidden-billid").val("${allocateOut.id}");
    	<c:if test="${listSize>0}">
	    	$("#delivery-allocateOut-outstorageid").widget('readonly');
	    </c:if>
	    <c:if test="${allocateOut.sourcetype=='1'}">
    		 $("#delivery-contextMenu-allocateOutAddPage").menu('disableItem','#delivery-addRow-allocateOutAddPage');
			 $("#delivery-contextMenu-allocateOutAddPage").menu('disableItem','#delivery-removeRow-allocateOutAddPage');
    	</c:if>
    */  
    </script>
</body>

</html>