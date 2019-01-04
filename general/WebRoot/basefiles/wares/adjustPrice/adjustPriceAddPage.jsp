<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>调价单新增页面</title>
</head>
<body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="goods-form-adjustPriceAddPage" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height: 100px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input id="goods-adjustPrice-id" class="easyui-validatebox" name="adjustPrice.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> style="width:160px;"/></td>
	    				<td class="len80 right">业务日期：</td>
	    				<td class="len165"><input type="text" id="goods-adjustPrice-businessdate"  onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${date }" name="adjustPrice.businessdate" style="width:160px;"/></td>
	    				<td class="len80 right">状态：</td>
	    				<td class="len165"><select id="goods-adjustPrice-status-select" disabled="disabled" style="width:160px;">
    	                        <option ></option>
    	                        <option value="0" selected="selected">新增</option>
    	                        <option value="2">保存</option>
    	                        <option value="3">审核通过</option>
    	                        <option value="4">关闭</option>
    	                    </select>
    	                </td>
	    			</tr>
	    			<tr>
	    			    <td>调价单名称：</td>
	    				<td>
	    					<input type="text" id="goods-adjustPrice-name" name="adjustPrice.name" style="width:160;" />
	    				</td>
	    				<td class="len80 right">调价类型：</td>
	    				<td class="len165"><select id="goods-adjustPrice-type-select" style="width:160px;">
    	                        <option value="1"  selected="selected">商品采购价</option>
    	                        <option value="2">商品基准销售价</option>
    	                        <option value="3">价格套</option>
    	                        <option value="4">合同价</option>
    	                    </select>
    	                    <input type="hidden" id="goods-adjustPrice-type" name="adjustPrice.type" value="1"  style="width:160;" />
    	                </td>
    	                <td class="len80 right">对应项目：</td>
	    				<td class="len165" id="customertd">
	    				    <input  class="easyui-validatebox" readonly='readonly' style="width:160px;"/>
    	                </td>
    	            </tr>
	    		    <tr>
	    				<td>生成方式：</td>
	    				<td class="len165">
	    				    <select id="goods-adjustPrice-createtype-select" style="width:160px;">
    	                        <option value="0" selected="selected">手动生成</option>
    	                        <option value="1">品牌生成</option>
    	                        <option value="2">商品分类生成</option>
    	                    </select>
    	                </td>
	    				<td class="len80 right">备注：</td>
	    				<td colspan="3" style="text-align: left">
	    					<input type="text"  id="goods-adjustPrice-remark" name="adjustPrice.remark" style="width: 425px;" onfocus="frm_focus('adjustPrice.remark');" onblur="frm_blur('adjustPrice.remark');"/>
	    				</td>
	    			</tr>
	    			
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="goods-datagrid-adjustPriceAddPage"></table>
	    	</div>
	    	<input type="hidden" id="goods-adjustPrice-adjustPriceDetail" name="detailJson"/>
	    	<input type="hidden" id="goods-adjustPrice-saveaudit" name="saveaudit" value="save"/>
	    </form>
    </div>
    <div class="easyui-menu" id="goods-contextMenu-adjustPriceAddPage" style="display: none;">
    	<div id="goods-addRow-adjustPriceAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="goods-editRow-adjustPriceAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="goods-removeRow-adjustPriceAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="goods-dialog-adjustPriceAddPage"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#goods-buttons-adjustPricePage").buttonWidget("initButtonType", 'add');
    		
    		$("#goods-datagrid-adjustPriceAddPage").datagrid({
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
    	 		onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$("#goods-datagrid-adjustPriceAddPage").datagrid('selectRow', rowIndex);
                    $("#goods-contextMenu-adjustPriceAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
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
    		}).datagrid("loadData", {'total':12,'rows':[{},{},{},{},{},{},{},{},{},{},{},{}],}).datagrid('columnMoving'); 
    		//调价类型改变
    		$("#goods-adjustPrice-type-select").change(function(){
    			$("#goods-adjustPrice-type").val($(this).val());
				if($(this).val()=="1"){
					changeCustomerWidget("1","","0");
				}
				else if($(this).val()=="2"){
					changeCustomerWidget("2","","0");
				}
                else if($(this).val()=="3"){
                	changeCustomerWidget("3","","0");
				}
                else if($(this).val()=="4"){
                	changeCustomerWidget("4","","0");
				}
			});
    		//生成类型改变
    		$("#goods-adjustPrice-createtype-select").change(function(){
				if($(this).val()=="1"){
					var type=$("#goods-adjustPrice-type-select").val();
					if(type!="4"){
					   autoCreateByBrand();
					}
					else{
						autoCreateCustomerPriceByBrand();
					}
				}
				if($(this).val()=="2"){
					
					   autoCreateByDefaultSort();
				}
			});
    		//调价商品添加
    		$("#goods-addRow-adjustPriceAddPage").click(function(){
				var flag = $("#goods-contextMenu-adjustPriceAddPage").menu('getItem','goods-addRow-adjustPriceAddPage').disabled;
				if(flag){
					return false;
				}
    			beginAddDetail();
    		});
    		
    		//调价商品修改
    		$("#goods-editRow-adjustPriceAddPage").click(function(){
    			var flag = $("#goods-contextMenu-adjustPriceAddPage").menu('getItem','#goods-editRow-adjustPriceAddPage').disabled;
				if(flag){
					return false;
				}
    			beginEditDetail();
    		});
    		//调价商品删除
    		$("#goods-removeRow-adjustPriceAddPage").click(function(){
    			var flag = $("#goods-contextMenu-adjustPriceAddPage").menu('getItem','#goods-removeRow-adjustPriceAddPage').disabled;
				if(flag){
					return false;
				}
    			removeDetail();
    		});
    		//调价单提交
    		$("#goods-form-adjustPriceAddPage").form({  
			    onSubmit: function(){  
			    	var json = $("#goods-datagrid-adjustPriceAddPage").datagrid('getRows');
		
					$("#goods-adjustPrice-adjustPriceDetail").val(JSON.stringify(json));
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
			    		$("#goods-adjustPrice-id").val(json.id);
			    		if(json.auditflag){
			    			$("#goods-adjustPrice-status-select").val("3");
			    			var d = new Date();
							var str = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
							$("#goods-adjustPrice-businessdate").val(str);
			    			$.messager.alert("提醒","保存并审核成功");
			    			$("#goods-buttons-adjustPricePage").buttonWidget("setDataID",{id:'${adjustPrice.id}',state:'3',type:'view'});
			    		}else{
			    			$("#goods-adjustPrice-status-select").val("2");
			    			$.messager.alert("提醒","保存成功"+json.msg);
			    			$("#goods-buttons-adjustPricePage").buttonWidget("setDataID",{id:'${adjustPrice.id}',state:'2',type:'edit'});
			    		}
			    		$("#goods-buttons-adjustPricePage").buttonWidget("addNewDataId", json.id);
			    		
			    	}else{
			    		$.messager.alert("提醒","保存失败."+json.msg);
			    	}
			    }  
			}); 
    	});
    </script>
</body>

</html>