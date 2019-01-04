<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>调账单</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-adjustmentsAdd" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height:100px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len150 easyui-validatebox" name="adjustments.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len150" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate }" name="adjustments.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165"><select disabled="disabled" class="len150"><option>新增</option></select></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">调账仓库：</td>
	    				<td>
	    					<input type="text" id="storage-adjustments-storageid" name="adjustments.storageid" class="len150" />
	    				</td>
	    				<td class="len80 left">单据类型：</td>
	    				<td>
	    					<select class="len150" disabled="disabled">
	    						<option value="1" <c:if test="${billtype=='1' }">selected="selected"</c:if>>报溢调账单</option>
	    						<option value="2" <c:if test="${billtype=='2' }">selected="selected"</c:if>>报损调账单</option>
	    					</select>
	    					<input type="hidden" id="storage-adjustments-billtype" name="adjustments.billtype" value="${billtype}"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="5" style="text-align: left">
	    					<input type="text" name="adjustments.remark" style="width:680px;" />
	    				</td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="storage-datagrid-adjustmentsAddPage"></table>
	    	</div>
	    	<input type="hidden" id="storage-adjustments-adjustmentsDetail" name="adjustmentsDetailJson"/>
	    	<input type="hidden" id="storage-adjustments-saveaudit" name="saveaudit" value="save"/>	
	    </form>
    </div>
    <div class="easyui-menu" id="storage-contextMenu-adjustmentsAddPage" style="display: none;">
    	<div id="storage-addRow-adjustmentsAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="storage-addOtherRow-adjustmentsAddPage" data-options="iconCls:'button-add'">添加其他商品</div>
    	<div id="storage-editRow-adjustmentsAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="storage-removeRow-adjustmentsAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="storage-dialog-adjustmentsAddPage"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#storage-datagrid-adjustmentsAddPage").datagrid({ //调账单明细信息编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			fit:true,
    			singleSelect: true,
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$("#storage-datagrid-adjustmentsAddPage").datagrid('selectRow', rowIndex);
                    $("#storage-contextMenu-adjustmentsAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onDblClickRow: function(rowIndex, rowData){
    				if(rowData.goodsid == undefined){
    					beginAddDetail();
    				}
    				else{
    					beginEditDetail();
    				}
    			},
    			onLoadSuccess:function(){
    				countTotal();
    			}
    		}).datagrid("loadData", {'total':12,'rows':[{},{},{},{},{},{},{},{},{},{}],'footer':[{goodsid:'合计'}]}).datagrid('columnMoving');   
    		$("#storage-adjustments-storageid").widget({
    			name:'t_storage_adjustments',
	    		width:150,
				col:'storageid',
				singleSelect:true,
				required:true
    		});
    		//调账单明细添加
    		$("#storage-addRow-adjustmentsAddPage").click(function(){
				var flag = $("#storage-contextMenu-adjustmentsAddPage").menu('getItem','#storage-addRow-adjustmentsAddPage').disabled;
				if(flag){
					return false;
				}
    			beginAddDetail();
    		});
    		$("#storage-addOtherRow-adjustmentsAddPage").click(function(){
    			var flag = $("#storage-contextMenu-adjustmentsAddPage").menu('getItem','#storage-addOtherRow-adjustmentsAddPage').disabled;
				if(flag){
					return false;
				}
    			beginAddOtherDetail();
    		});
    		//调账单明细修改
    		$("#storage-editRow-adjustmentsAddPage").click(function(){
    			var flag = $("#storage-contextMenu-adjustmentsAddPage").menu('getItem','#storage-editRow-adjustmentsAddPage').disabled;
				if(flag){
					return false;
				}
    			beginEditDetail();
    		});
    		//调账单明细删除
    		$("#storage-removeRow-adjustmentsAddPage").click(function(){
    			var flag = $("#storage-contextMenu-adjustmentsAddPage").menu('getItem','#storage-removeRow-adjustmentsAddPage').disabled;
				if(flag){
					return false;
				}
    			removeDetail();
    		});
    		$("#storage-form-adjustmentsAdd").form({  
			    onSubmit: function(){  
			    	var json = $("#storage-datagrid-adjustmentsAddPage").datagrid('getRows');
					$("#storage-adjustments-adjustmentsDetail").val(JSON.stringify(json));
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
			    		if(json.saveaudit == "saveaudit"){
			    			if(json.auditflag){
				    			$.messager.alert("提醒","保存并审核成功");
				    		}else{
				    			$.messager.alert("提醒","保存成功，审核失败。"+json.msg);
				    		}
			    		}else{
			    			$.messager.alert("提醒","保存成功。");
			    		}
			    		$("#storage-buttons-adjustmentsPage").buttonWidget("addNewDataId", json.id);
			    		$("#storage-panel-adjustmentsPage").panel({
							href:"storage/adjustmentsEditPage.do?id="+json.id,
						    cache:false,
						    maximized:true,
						    border:false
						});
			    	}else{
			    		$.messager.alert("提醒","保存失败");
			    	}
			    }  
			}); 
    	});
    	
    	//获取仓库下商品明细
    	function getadjustmentsDetail(){
    		var storageid = $("#storage-adjustments-storageid").widget("getValue");
    		var creattype = $("#storage-adjustments-createtype").widget("getValue");
    		if(storageid!=null && storageid!=""){
    			//生成方式为系统生成时
	    		if(creattype=="2"){
		    		$.ajax({   
			            url :'storage/getAdjustmentsDetail.do?storageid='+storageid,
			            type:'post',
			            dataType:'json',
			            success:function(json){
			            	$("#storage-datagrid-adjustmentsAddPage").datagrid("loadData",json);
			            	if(json.length>0){
			            		$("#storage-adjustments-storageid").widget('readonly',true);
			            	}
			            	if(json.length<10){
			            		var j = 10-json.length;
			            		for(var i=0;i<j;i++){
			            			$("#storage-datagrid-adjustmentsAddPage").datagrid('appendRow',{});
			            		}
	    					}else{
	    						$("#storage-datagrid-adjustmentsAddPage").datagrid('appendRow',{});
	    					}
			            }
			        });
			        $("#storage-contextMenu-adjustmentsAddPage").menu('disableItem','#storage-addRow-adjustmentsAddPage');
			        $("#storage-contextMenu-adjustmentsAddPage").menu('disableItem','#storage-removeRow-adjustmentsAddPage');
		        }else{
			        $("#storage-contextMenu-adjustmentsAddPage").menu('enableItem','#storage-addRow-adjustmentsAddPage');
			        $("#storage-contextMenu-adjustmentsAddPage").menu('enableItem','#storage-removeRow-adjustmentsAddPage');
		        }
    		}
    		
    	}
    	//控制按钮状态
    	$("#storage-buttons-adjustmentsPage").buttonWidget("initButtonType","add");
    </script>
  </body>
</html>
