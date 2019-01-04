<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>调账单</title>
  </head>
  
  <body>
  	<form action="" method="post" id="storage-form-id-adjustmentsPage">
  		<input type="hidden" name="id" value="${adjustments.id }"/>
  	</form>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-adjustmentsEdit" action="" method="post">
    	<div data-options="region:'north',border:false" style="height:100px;">
    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
    			<tr>
    				<td class="len80 left">编号：</td>
    				<td class="len165"><input class="len150 easyui-validatebox" name="adjustments.id" readonly='readonly' value="${adjustments.id}" /></td>
    				<td class="len80 left">业务日期：</td>
    				<td class="len165"><input type="text" class="len150"  value="${adjustments.businessdate}" name="adjustments.businessdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
    				<td class="len80 left">状态：</td>
    				<td class="len165">
    					<select id="storage-adjustments-status" disabled="disabled" class="len150">
    						<c:forEach items="${statusList }" var="list">
    							<c:choose>
    								<c:when test="${list.code == adjustments.status}">
    									<option value="${list.code }" selected="selected">${list.codename }</option>
    								</c:when>
    								<c:otherwise>
    									<option value="${list.code }">${list.codename }</option>
    								</c:otherwise>
    							</c:choose>
    						</c:forEach>
    					</select>
    					<input type="hidden" name="adjustments.status" value="${adjustments.status }" />
    				</td>
    			</tr>
    			<tr>
    				<td class="len80 left">调账仓库：</td>
    				<td><input type="text" id="storage-adjustments-storageid" name="adjustments.storageid" class="len150" required="required" value="${adjustments.storageid }" readonly="readonly"/></td>
    				<td>盘点单号:</td>
    				<td>
    					<input type="text" class="len150" value="${adjustments.sourceid }" readonly="readonly"/>
    				</td>
    				<td class="len80 left">单据类型：</td>
    				<td>
    					<select class="len150" disabled="disabled">
    						<option value="1" <c:if test="${adjustments.billtype=='1' }">selected="selected"</c:if>>报溢调账单</option>
    						<option value="2" <c:if test="${adjustments.billtype=='2' }">selected="selected"</c:if>>报损调账单</option>
    					</select>
    					<input type="hidden" id="storage-adjustments-billtype" name="adjustments.billtype" value="${adjustments.billtype}"/>
    				</td>
    			</tr>
    			<tr>
    				<td class="len80 left">备注：</td>
    				<td colspan="5">
    					<input type="text" name="adjustments.remark" style="width:680px;" value="<c:out value="${adjustments.remark }"></c:out>"/>
    				</td>
    			</tr>
    		</table>
    	</div>
    	<div data-options="region:'center',border:false">
    		<table id="storage-datagrid-adjustmentsAddPage"></table>
    	</div>
    	<input type="hidden" id="storage-adjustments-adjustmentsDetail" name="adjustmentsDetailJson" />
    	<input type="hidden" id="storage-adjustments-saveaudit" name="saveaudit" value="save"/>
    	
			<input type="hidden" id="storage-adjustments-printtimes" value="${adjustments.printtimes}" />
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
    		$("#storage-datagrid-adjustmentsAddPage").datagrid({ //销售商品明细信息编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			fit:true,
    			singleSelect: true,
    			data: JSON.parse('${adjustmentsDetailList}'),
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
    			onLoadSuccess:function(data){
    				if(data.rows.length<10){
	            		var j = 10-data.rows.length;
	            		for(var i=0;i<j;i++){
	            			$("#storage-datagrid-adjustmentsAddPage").datagrid('appendRow',{});
	            		}
   					}else{
   						$("#storage-datagrid-adjustmentsAddPage").datagrid('appendRow',{});
   					}
    				countTotal();
    			}
    		}).datagrid('columnMoving');    
    		$("#storage-adjustments-storageid").widget({
    			name:'t_storage_adjustments',
	    		width:150,
				col:'storageid',
				singleSelect:true
    		});
    		$("#storage-adjustments-sourcetype").widget({
    			name:'t_storage_adjustments',
	    		width:150,
				col:'sourcetype',
				singleSelect:true,
				disabled:true
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
    		$("#storage-form-adjustmentsEdit").form({  
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
			    		if(json.saveaudit == "saveaudit" && json.auditflag){
			    			if(json.auditflag){
				    			$.messager.alert("提醒","保存并审核成功");
                                $("#storage-panel-adjustmentsPage").panel({
                                    href:'storage/adjustmentsEditPage.do?id=${adjustments.id }',
                                    title:'',
                                    cache:false,
                                    maximized:true,
                                    border:false
                                });
				    		}else{
				    			$.messager.alert("提醒","保存成功，审核失败。"+json.msg);
				    		}
			    		}else{
			    			$.messager.alert("提醒","保存成功。"+json.msg);
			    		}
			    	}else{
			    		$.messager.alert("提醒","保存失败");
			    	}
			    }  
			}); 	
			//查看盘点单
    		$("#storage-adjustments-sourcetype-view").click(function(){
    			var checkListid = "${adjustments.sourceid}";
    			top.addOrUpdateTab('storage/showCheckListViewPage.do?id='+ checkListid, "盘点单");
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
    	$("#storage-buttons-adjustmentsPage").buttonWidget("setDataID",{id:'${adjustments.id}',state:'${adjustments.status}',type:'edit'});
    	$("#storage-hidden-billid").val("${adjustments.id}");
        setTimeout(function(){
            <c:if test="${adjustments.sourcetype=='1'}">
            $("#storage-contextMenu-adjustmentsAddPage").menu('enableItem','#storage-addRow-adjustmentsAddPage');
            $("#storage-contextMenu-adjustmentsAddPage").menu('enableItem','#storage-removeRow-adjustmentsAddPage');
            $("#storage-contextMenu-adjustmentsAddPage").menu('enableItem','#storage-addOtherRow-adjustmentsAddPage');
            $("#storage-buttons-purchaseEnterPage").buttonWidget("disableMenuItem","relation-upper-view");
            </c:if>
            <c:if test="${adjustments.sourcetype!='1'}">
            $("#storage-contextMenu-adjustmentsAddPage").menu('disableItem','#storage-addRow-adjustmentsAddPage');
            $("#storage-contextMenu-adjustmentsAddPage").menu('disableItem','#storage-removeRow-adjustmentsAddPage');
            $("#storage-contextMenu-adjustmentsAddPage").menu('disableItem','#storage-addOtherRow-adjustmentsAddPage');

            $("#storage-buttons-purchaseEnterPage").buttonWidget("enableMenuItem","relation-upper-view");
            </c:if>
        },100);

    </script>
  </body>
</html>
