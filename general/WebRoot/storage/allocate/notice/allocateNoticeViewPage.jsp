<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>调拨通知单</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-allocateNoticeAdd" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height: 130px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len150 easyui-validatebox" name="allocateNotice.id" value="${allocateNotice.id }" readonly="readonly"/></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" id="storage-allocateNotice-businessdate" class="len150" value="${allocateNotice.businessdate }" name="allocateNotice.businessdate" readonly="readonly"/></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select id="storage-allocateNotice-status-select" disabled="disabled" class="len150">
    						<c:forEach items="${statusList }" var="list">
    							<c:choose>
    								<c:when test="${list.code == allocateNotice.status}">
    									<option value="${list.code }" selected="selected">${list.codename }</option>
    								</c:when>
    								<c:otherwise>
    									<option value="${list.code }">${list.codename }</option>
    								</c:otherwise>
    							</c:choose>
    						</c:forEach>
    						</select>
    						<input type="hidden" id="storage-allocateNotice-status" name="allocateNotice.status" value="${allocateNotice.status }" readonly="readonly"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">调出仓库：</td>
	    				<td>
	    					<input type="text" id="storage-allocateNotice-outstorageid" name="allocateNotice.outstorageid" value="${allocateNotice.outstorageid }" readonly="readonly"/>
	    					<input type="hidden" id="storage-allocateNotice-outstorageid-old" value="${allocateNotice.outstorageid} "/>
	    				</td>
						<td class="len80 left">调入仓库:</td>
						<td>
							<input type="text" id="storage-allocateNotice-enterstorageid" name="allocateNotice.enterstorageid" value="${allocateNotice.enterstorageid }" readonly="readonly"/>
						</td>
						<td class="len80 left" <c:if test="${isAllocateShowBilltype=='0'}">style="display: none;" </c:if>>调拨类型：</td>
						<td <c:if test="${isAllocateShowBilltype=='0'}">style="display: none;" </c:if>>
							<select class="len150" id="storage-allocateNotice-billtype" disabled="disabled" name="allocateNotice.billtype">
								<option value="1" <c:if test="${allocateNotice.billtype==1}">selected</c:if>>成本调拨</option>
								<option value="2" <c:if test="${allocateNotice.billtype==2}">selected</c:if>>异价调拨</option>
							</select>
						</td>
	    			</tr>
					<tr>
						<td class="len80 left">出库部门：</td>
						<td>
							<input type="text" id="storage-allocateNotice-outdeptid" readonly="readonly" name="allocateNotice.outdeptid" value="${allocateNotice.outdeptid }" class="len150" />
						</td>
						<td class="len80 left">调入部门:</td>
						<td>
							<input type="text" id="storage-allocateNotice-enterdeptid" readonly="readonly" name="allocateNotice.enterdeptid" value="${allocateNotice.enterdeptid }" class="len150"/>
						</td>
					</tr>
	    			<tr>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="5" style="text-align: left">
	    					<input type="text" name="allocateNotice.remark" style="width: 680px;" value="<c:out value="${allocateNotice.remark }"></c:out>" readonly="readonly"/>
	    				</td>
	    			</tr>
	    			<tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="storage-datagrid-allocateNoticeAddPage"></table>
	    	</div>
	    	<input type="hidden" id="storage-allocateNotice-allocateNoticeDetail" name="detailJson"/>
	    </form>
    </div>
    <div id="storage-dialog-allocateNoticeAddPage"></div>
    <script type="text/javascript">
    	$(function(){
			$("#storage-datagrid-allocateNoticeAddPage").datagrid({ //采购入库单明细信息编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			fit:true,
    			singleSelect: true,
    			data:JSON.parse('${detailList}'),
    			onLoadSuccess:function(data){
    				if(data.rows.length<10){
	            		var j = 10-data.rows.length;
	            		for(var i=0;i<j;i++){
	            			$("#storage-datagrid-allocateNoticeAddPage").datagrid('appendRow',{});
	            		}
   					}else{
   						$("#storage-datagrid-allocateNoticeAddPage").datagrid('appendRow',{});
   					}
    				countTotal();
    			}
    		}).datagrid('columnMoving'); 
    		$("#storage-allocateNotice-outstorageid").widget({
    			name:'t_storage_allocate_notice',
	    		width:150,
				col:'outstorageid',
				singleSelect:true,
				required:true
    		});
    		$("#storage-allocateNotice-enterstorageid").widget({
    			name:'t_storage_allocate_notice',
	    		width:150,
				col:'enterstorageid',
				singleSelect:true,
				initValue:'${allocateNotice.enterstorageid}',
				param:[
					{field:'id',op:'notin',value:'${allocateNotice.outstorageid}'}
				]
    		});   		
    	});
		$("#storage-allocateNotice-outdeptid").widget({
			referwid:'RL_T_BASE_DEPATMENT',
			width:150,
			singleSelect:true
		})
		$("#storage-allocateNotice-enterdeptid").widget({
			referwid:'RL_T_BASE_DEPATMENT',
			width:150,
			singleSelect:true
		})
    	
    	//控制按钮状态
    	$("#storage-buttons-allocateNoticePage").buttonWidget("setDataID",{id:'${allocateNotice.id}',state:'${allocateNotice.status}',type:'view'});
    	$("#storage-hidden-billid").val("${allocateNotice.id}");
    </script>
  </body>
</html>
