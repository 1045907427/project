<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>调拨入库单</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-allocateEnterAdd" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height: 100px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len130 easyui-validatebox" name="allocateEnter.id" value="${allocateEnter.id }" readonly="readonly"/></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" id="storage-allocateEnter-businessdate" class="len130" value="${allocateEnter.businessdate }" name="allocateEnter.businessdate" readonly="readonly"/></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select disabled="disabled" class="len136">
    						<c:forEach items="${statusList }" var="list">
    							<c:choose>
    								<c:when test="${list.code == allocateEnter.status}">
    									<option value="${list.code }" selected="selected">${list.codename }</option>
    								</c:when>
    								<c:otherwise>
    									<option value="${list.code }">${list.codename }</option>
    								</c:otherwise>
    							</c:choose>
    						</c:forEach>
    						</select>
    						<input type="hidden" id="storage-allocateEnter-status" name="allocateEnter.status" value="${allocateEnter.status }" readonly="readonly"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">调入仓库:</td>
	    				<td>
	    					<input type="text" id="storage-allocateEnter-enterstorageid" name="allocateEnter.enterstorageid" class="len130" value="${allocateEnter.enterstorageid }" readonly="readonly"/>
	    				</td>
	    				<td class="len80 left">调出仓库：</td>
	    				<td>
	    					<input type="text" id="storage-allocateEnter-outstorageid" name="allocateEnter.outstorageid" class="len130" value="${allocateEnter.outstorageid }" readonly="readonly"/>
	    					<input type="hidden" id="storage-allocateEnter-outstorageid-old" value="${allocateEnter.outstorageid} "/>
	    				</td>
	    				<td class="len80 left">来源类型：</td>
	    				<td>
	    					<select disabled="disabled" class="len136">
	    						<option value="0" <c:if test="${allocateEnter.sourcetype=='0'}">selected="selected"</c:if>>无</option>
	    						<option value="1" <c:if test="${allocateEnter.sourcetype=='1'}">selected="selected"</c:if>>调拨出库单</option>
	    					</select>
	    					<input type="hidden" name="allocateEnter.sourcetype" value="${allocateEnter.sourcetype }"/>
	    					<input type="hidden" id="storage-allocateEnter-sourceid" name="allocateEnter.sourceid" value="${allocateEnter.sourceid }"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="5" style="text-align: left">
	    					<input type="text" name="allocateEnter.remark" style="width: 500px;" value="<c:out value="${allocateEnter.remark }"></c:out>" readonly="readonly"/>
	    				</td>
	    			</tr>
	    			<tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="storage-datagrid-allocateEnterAddPage"></table>
	    	</div>
	    	<input type="hidden" id="storage-allocateEnter-allocateEnterDetail" name="detailJson"/>
	    </form>
    </div>
    <div id="storage-dialog-allocateEnterAddPage"></div>
    <script type="text/javascript">
    	$(function(){
			$("#storage-datagrid-allocateEnterAddPage").datagrid({ //采购入库单明细信息编辑
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
	            			$("#storage-datagrid-allocateEnterAddPage").datagrid('appendRow',{});
	            		}
   					}else{
   						$("#storage-datagrid-allocateEnterAddPage").datagrid('appendRow',{});
   					}
    				countTotal();
    			}
    		}).datagrid('columnMoving'); 
    		$("#storage-allocateEnter-outstorageid").widget({
    			name:'t_storage_allocate_notice',
	    		width:130,
				col:'outstorageid',
				singleSelect:true,
				required:true
    		});
    		$("#storage-allocateEnter-enterstorageid").widget({
    			name:'t_storage_allocate_notice',
	    		width:130,
				col:'enterstorageid',
				singleSelect:true,
				initValue:'${allocateEnter.enterstorageid}',
				param:[
					{field:'id',op:'notin',value:'${allocateEnter.outstorageid}'},
				]
    		});   		
    	});
    	
    	//控制按钮状态
    	$("#storage-buttons-allocateEnterPage").buttonWidget("setDataID",{id:'${allocateEnter.id}',state:'${allocateEnter.status}',type:'view'});
    	$("#storage-hidden-billid").val("${allocateEnter.id}");
    	<c:if test="${allocateEnter.sourcetype=='0'}">
	    	$("#storage-buttons-allocateEnterPage").buttonWidget("disableMenuItem","relation-upper-view");
	    </c:if>
	    <c:if test="${allocateEnter.sourcetype!='0'}">
	    	$("#storage-buttons-allocateEnterPage").buttonWidget("enableMenuItem","relation-upper-view");
	    </c:if>
    </script>
  </body>
</html>
