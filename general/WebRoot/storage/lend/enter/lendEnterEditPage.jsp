<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>还货单新增页面</title>
  </head>  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-lendAdd"  method="post">
	    	<div data-options="region:'north',border:false" style="height:105px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="easyui-validatebox" style="width: 135px" name="lend.id" value="${lend.id }" readonly="readonly"/></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" id="storage-lend-businessdate" class="len130" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${lend.businessdate }" name="lend.businessdate"/></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select id="storage-lend-status-select" disabled="disabled" class="len136">
    						<c:forEach items="${statusList }" var="list">
    							<c:choose>
    								<c:when test="${list.code == lend.status}">
    									<option value="${list.code }" selected="selected">${list.codename }</option>
    								</c:when>
    								<c:otherwise>
    									<option value="${list.code }">${list.codename }</option>
    								</c:otherwise>
    							</c:choose>
    						</c:forEach>
    						</select>
    						<input type="hidden" name="lend.status" value="${lend.status }"/>
	    				</td>
	    			</tr>
	    			<tr>
						<td class="len80 left">还货人类型：</td>
						<td>
							<input type="text" id="storage-lend-lendtype" name="lend.lendtype" value="${lend.lendtype}"/>
						</td>
						<td class="len80 left">还货人：</td>
						<td colspan="3" id="lendp">
							<input id="storage-lend-lendid" name="lend.lendid" value="${lend.lendid}" style="width: 400px"/>
						</td>
	    			</tr>
	    			<tr>
						<td class="len80 left">入货仓库：</td>
						<td>
							<input type="text" id="storage-lend-storageid" name="lend.storageid" value="${lend.storageid}" readonly="readonly"/>
						</td>
						<td class="len80 left">相关部门：</td>
						<td>
							<input id="storage-lend-deptid" name="lend.deptid" value="${lend.deptid}"/>
						</td>
						<td class="len80 left">备注：</td>
						<td style="text-align: left;"><input type="text" style="width: 136px" name="lend.remark" value="<c:out value="${lend.remark}"></c:out>"/></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
				<input type="hidden" value="${lend.printtimes}" id="storage-lend-printtimes"/>
	    		<input type="hidden" name="detailJson" id="storage-lendEnter-lendDetail" />
	    		<input type="hidden" name="saveaudit" id="storage-purchaseEnter-saveaudit" value="save"/>
	    		<table id="storage-datagrid-lendAddPage"></table>
	    	</div>
	    </form>
    </div>
    <div class="easyui-menu" id="sales-contextMenu-lendAddPage" style="display: none;">
    	<div id="sales-addRow-lendAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="sales-editRow-lendAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="sales-removeRow-lendAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="storage-dialog-lendAddPage"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#storage-datagrid-lendAddPage").datagrid({ //销售商品明细信息编辑
    			columns: tableColJson,
    			border: true,
    			fit: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: true,
    			data:JSON.parse('${detailList}'),
    			onLoadSuccess:function(data){
    				if(data.rows.length<10){
	            		var j = 10-data.rows.length;
	            		for(var i=0;i<j;i++){
	            			$("#storage-datagrid-lendAddPage").datagrid('appendRow',{});
	            		}
   					}else{
   						$("#storage-datagrid-lendAddPage").datagrid('appendRow',{});
   					}
    				countTotal();
    			},
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$("#storage-datagrid-lendAddPage").datagrid('selectRow', rowIndex);
                    $("#sales-contextMenu-lendAddPage").menu('show', {  
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
    			}
    		}).datagrid('columnMoving');
            //入库仓库
            $("#storage-lend-storageid").widget({
                width:136,
                referwid:'RL_T_BASE_STORAGE_INFO',
                singleSelect:true,
                onlyLeafCheck:false,
                required:true
            });
			//部门控件
			$("#storage-lend-deptid").widget({
				name: 't_storage_lend',
				col: 'deptid',
				singleSelect: true,
				isdatasql: false,
				width: 136,
				onlyLeafCheck: false
			});

			<c:if test="${lend.lendtype=='1'}">
			$("#storage-lend-lendid").widget({
				width: 400,
				referwid:'RL_T_BASE_BUY_SUPPLIER',
				singleSelect:true,
				onlyLeafCheck:false,
				required:true
			});
			</c:if>
			<c:if test="${lend.lendtype=='2'}">
			$("#storage-lend-lendid").widget({
				width: 400,
				referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
				singleSelect:true,
				onlyLeafCheck:false,
				required:true
			});
			</c:if>

			$('#storage-lend-lendtype').widget({
				width:136,
				name: 't_storage_lend',
				col: 'lendtype',
				singleSelect:true,
				onlyLeafCheck:false,
				onSelect : function(data){
					$("#lendp").empty();
					var tdstr = '<input type="text" id="storage-lend-lendid" name="lend.lendid" style="width: 400px"/>';
					$(tdstr).appendTo("#lendp");
					if (data.id == 1) {
						$("#storage-lend-lendid").widget({
							width: 400,
							referwid:'RL_T_BASE_BUY_SUPPLIER',
							singleSelect:true,
							onlyLeafCheck:false,
							required:true
						})
					} else if (data.id == 2) {
						$("#storage-lend-lendid").widget({
							width: 400,
							referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
							singleSelect:true,
							onlyLeafCheck:false,
							required:true
						})
					}
				}
			});
    		$("#sales-addRow-lendAddPage").click(function(){
    			beginAddDetail();
    		});
    		$("#sales-editRow-lendAddPage").click(function(){
    			beginEditDetail();
    		});
    		$("#sales-removeRow-lendAddPage").click(function(){
    			removeDetail();
    		});
    		$("#storage-form-lendAdd").form({  
			    onSubmit: function(){  
			    	var json = $("#storage-datagrid-lendAddPage").datagrid('getRows');
					$("#storage-lendEnter-lendDetail").val(JSON.stringify(json));
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
			    			$.messager.alert("提醒",json.msg);
			    			$("#storage-lend-status-select").val("4");
			    			$("#storage-buttons-lendPage").buttonWidget("setDataID",{id:'${lend.id}',state:'4',type:'view'});
			    			$("#storage-buttons-lendPage").buttonWidget("enableButton", 'button-oppaudit');
                            $("#storage-panel-lendPage").panel({
                                href:'storage/lendEditPage.do?billtype=2&id=${lend.id}',
                                title:'',
                                cache:false,
                                maximized:true,
                                border:false
                            });
                        }else{
			    			$.messager.alert("提醒","保存成功");
			    		}
			    	}else{
			    		$.messager.alert("提醒","保存失败</br>"+json.msg);
			    	}
			    }  
			});
    	});
    	$("#storage-buttons-lendPage").buttonWidget("setDataID",{id:'${lend.id}',state:'${lend.status}',type:'edit'});
    	$("#storage-hidden-billid").val("${lend.id}");
    </script>
  </body>
</html>
