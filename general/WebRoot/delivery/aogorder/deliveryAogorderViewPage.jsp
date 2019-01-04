<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>查看代配送采购单</title>
</head>
<body>
    
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="delivery-form-deliveryAogorderAddPage" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height: 100px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input id="delivery-deliveryAogorder-id" class="easyui-validatebox" name="deliveryAogorder.id" value="${deliveryAogorder.id }"  readonly="readonly" style="width:160px;"/></td>
	    				<td class="len80 right">业务日期：</td>
	    				<td class="len165"><input type="text" id="delivery-deliveryAogorder-businessdate" style="width:160px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"  name="deliveryAogorder.businessdate" value="${deliveryAogorder.businessdate }" readonly="readonly" /></td>
	    				<td class="len80 right">状态：</td>
	    				<td class="len165"><select id="delivery-deliveryAogorder-status-select" disabled="disabled" style="width:160px;">
	    				    <c:forEach items="${statusList }" var="list">
    							<c:choose>
    								<c:when test="${list.code == deliveryAogorder.status}">
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
	    			    <td>供应商：</td>
	    				<td colspan="3" style="text-align: left">
	    					<input type="text" id="delivery-deliveryAogorder-supplierid" name="deliveryAogorder.supplierid" value="${deliveryAogorder.supplierid }" style="width:320px;"  readonly="readonly"  />
	    					<span id="delivery-deliveryAogorder-supplier" style="margin-left:5px;line-height:25px;"></span>
	    				</td>
	    				<td class="len80 left">到货仓库：</td>
	    				<td>
	    					<input type="text" id="delivery-deliveryAogorder-storageid" name="deliveryAogorder.storageid" value="${deliveryAogorder.storageid }" readonly="readonly"  style="width:160px;"/>
	    				</td>
	    		    <tr>
	    				<td class="len80 left" >备注：</td>
	    				<td colspan="5" style="text-align: left">
	    					<input type="text" name="deliveryAogorder.remark"  value="${deliveryAogorder.remark }" style="width: 690px;" readonly="readonly" />
	    				</td>
	    			</tr>
	    			<tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="delivery-datagrid-deliveryAogorderAddPage"></table>
	    	</div>
	    	<input type="hidden" id="delivery-deliveryAogorder-deliveryAogorderDetail" name="detailJson"/>
	    	<input type="hidden" id="delivery-deliveryAogorder-saveaudit" name="saveaudit" value="save"/>
	    </form>
    </div>
   
    <div id="delivery-dialog-deliveryAogorderAddPage"></div>
    <script type="text/javascript">
    	$(function(){
    		var supplier=$("#delivery-deliveryAogorder-supplierid").val();
    		$("#delivery-deliveryAogorder-supplier").html("  编号:" + supplier);
    		$("#delivery-datagrid-deliveryAogorderAddPage").datagrid({ //采购入库单明细信息编辑
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
    				$("#delivery-datagrid-deliveryAogorderAddPage").datagrid('selectRow', rowIndex);
                    $("#delivery-contextMenu-deliveryAogorderAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onSortColumn:function(sort, order){
    				var rows = $("#delivery-datagrid-deliveryAogorderAddPage").datagrid("getRows");
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
    				$("#delivery-datagrid-deliveryAogorderAddPage").datagrid("loadData",dataArr);
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
    		$("#delivery-deliveryAogorder-supplierid").widget({ 
    			name:'t_purchase_buyorder',
    			col:'supplierid',
    			width:320,
    			readonly:true,
    			singleSelect:true,
    			onlyLeafCheck:true
    		});	
    		//仓库选择
    		$("#delivery-deliveryAogorder-storageid").widget({
    			referwid:'RL_T_BASE_STORAGE_INFO',
	    		width:160,
				col:'name',
				singleSelect:true,
				required:true,
				initValue:'${deliveryAogorder.storageid}'
    		});
    		
    		//订单提交
    		$("#delivery-form-deliveryAogorderAddPage").form({  
			    onSubmit: function(){  
			    	var json = $("#delivery-datagrid-deliveryAogorderAddPage").datagrid('getRows');
		
					$("#delivery-deliveryAogorder-deliveryAogorderDetail").val(JSON.stringify(json));
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
			    			$("#delivery-deliveryAogorder-status-select").val("3");
			    			$("#delivery-buttons-deliveryAogorderPage").buttonWidget("setDataID",{id:'${deliveryAogorder.id}',state:'3',type:'view'});
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
    	$("#delivery-buttons-deliveryAogorderPage").buttonWidget("setDataID",{id:'${deliveryAogorder.id}',state:'${deliveryAogorder.status}',type:'v'});
    	$("#delivery-hidden-billid").val("${deliveryAogorder.id}");
/*
    	<c:if test="${deliveryAogorder.status=='2'}">
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