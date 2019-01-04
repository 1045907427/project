<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>商品多价调整单查看页面</title>
</head>
<body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="goods-form-adjustMultiPriceAddPage" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height: 70px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input id="goods-adjustMultiPrice-id" class="easyui-validatebox" name="adjustMultiPrice.id" value="${adjustMultiPrice.id }"  readonly="readonly" style="width:160px;"/></td>
	    				<td class="len80 right">业务日期：</td>
	    				<td class="len165"><input type="text" id="goods-adjustMultiPrice-businessdate"  onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${adjustMultiPrice.businessdate }"  name="adjustMultiPrice.businessdate" style="width:160px;"/></td>
	    				<td class="len80 right">状态：</td>
	    				<td class="len165"><select id="goods-adjustMultiPrice-status-select" disabled="disabled" style="width:160px;">
    	                          <c:forEach items="${statusList }" var="list">
    							<c:choose>
    								<c:when test="${list.code == adjustMultiPrice.status}">
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
	    			    <td>调价单名称：</td>
	    				<td>
	    					<input type="text" id="goods-adjustMultiPrice-name" name="adjustMultiPrice.name" value="${adjustMultiPrice.name }"  style="width:160px;" />
	    				</td>
						<td class="len80 right">备注：</td>
						<td colspan="3" style="text-align: left">
							<input type="text"  id="goods-adjustMultiPrice-remark" name="adjustMultiPrice.remark" value="${adjustMultiPrice.remark}" style="width: 425px;" onfocus="frm_focus('adjustMultiPrice.remark');" onblur="frm_blur('adjustMultiPrice.remark');"/>
						</td>
	    			<tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="goods-datagrid-adjustMultiPriceAddPage"></table>
	    	</div>
	    	<input type="hidden" id="goods-adjustMultiPrice-adjustMultiPriceDetail" name="detailJson"/>
	    	<input type="hidden" id="goods-adjustMultiPrice-saveaudit" name="saveaudit" value="save"/>
	    </form>
    </div>
    <div class="easyui-menu" id="goods-contextMenu-adjustMultiPriceAddPage" style="display: none;">
    	<div id="goods-addRow-adjustMultiPriceAddPage" data-options="iconCls:'button-add'">添加</div>
		<div id="goods-addMoreRow-adjustMultiPriceAddPage" data-options="iconCls:'button-add'">批量添加商品</div>
    	<div id="goods-editRow-adjustMultiPriceAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="goods-removeRow-adjustMultiPriceAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="goods-dialog-adjustMultiPriceAddPage"></div>
    <script type="text/javascript">
   
    	$(function(){
    		
    		$("#goods-buttons-adjustMultiPricePage").buttonWidget("initButtonType", 'add');
    		$("#goods-datagrid-adjustMultiPriceAddPage").datagrid({
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
    				$("#goods-datagrid-adjustMultiPriceAddPage").datagrid('selectRow', rowIndex);
                    $("#goods-contextMenu-adjustMultiPriceAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
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
			//批量添加
			$("#goods-addMoreRow-adjustMultiPriceAddPage").click(function(){
				beginAddMultiDetailByBrandAndSort();
			});

    		//调价商品添加
    		$("#goods-addRow-adjustMultiPriceAddPage").click(function(){
				var flag = $("#goods-contextMenu-adjustMultiPriceAddPage").menu('getItem','goods-addRow-adjustMultiPriceAddPage').disabled;
				if(flag){
					return false;
				}
    			beginAddDetail();
    		});
    		
    		//调价商品修改
    		$("#goods-editRow-adjustMultiPriceAddPage").click(function(){
    			var flag = $("#goods-contextMenu-adjustMultiPriceAddPage").menu('getItem','#goods-editRow-adjustMultiPriceAddPage').disabled;
				if(flag){
					return false;
				}
    			beginEditDetail();
    		});
    		//调价商品删除
    		$("#goods-removeRow-adjustMultiPriceAddPage").click(function(){
    			var flag = $("#goods-contextMenu-adjustMultiPriceAddPage").menu('getItem','#goods-removeRow-adjustMultiPriceAddPage').disabled;
				if(flag){
					return false;
				}
    			removeDetail();
    		});
    		//调价单提交
    		$("#goods-form-adjustMultiPriceAddPage").form({  
			    onSubmit: function(){  
			    	var json = $("#goods-datagrid-adjustMultiPriceAddPage").datagrid('getRows');
		
					$("#goods-adjustMultiPrice-adjustMultiPriceDetail").val(JSON.stringify(json));
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
			    	if(true==json.lock){
		  		  		$.messager.alert("提醒","其他用户正在修改该数据，无法修改");
		  		  		return false;
		  		  	}
			    	if(json.flag){
			    		$("#goods-adjustMultiPrice-id").val(json.id);
			    		if(json.auditflag){
			    			$("#goods-adjustMultiPrice-status-select").val("3");
			    			var d = new Date();
							var str = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
							$("#goods-adjustMultiPrice-businessdate").val(str);
			    			$.messager.alert("提醒","保存并审核成功");
							$("#goods-panel-adjustMultiPricePage").panel('refresh', 'basefiles/adjustMultiPriceEditPage.do?id='+ json.id);
			    		}else{
			    			$("#goods-adjustMultiPrice-status-select").val("2");
			    			$.messager.alert("提醒","保存成功"+json.msg);
			    			$("#goods-buttons-adjustMultiPricePage").buttonWidget("setDataID",{id:'${adjustMultiPrice.id}',state:'2',type:'edit'});
			    		}
			    		$("#goods-buttons-adjustMultiPricePage").buttonWidget("addNewDataId", json.id);
			    		
			    	}else{
			    		$.messager.alert("提醒","保存失败."+json.msg);
			    	}
			    }  
			});

    	});
    	//控制按钮状态
    	$("#goods-buttons-adjustMultiPricePage").buttonWidget("setDataID",{id:'${adjustMultiPrice.id}',state:'${adjustMultiPrice.status}',type:'edit'});
    </script>
</body>

</html>