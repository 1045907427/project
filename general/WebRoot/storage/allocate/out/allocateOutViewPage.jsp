<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>调拨单</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-allocateOutAdd" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height: 140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
					<tr>
						<td>编号：</td>
						<td><input class="easyui-validatebox" style="width: 160px" name="allocateOut.id" value="${allocateOut.id }" readonly="readonly"/></td>
						<td>业务日期：</td>
						<td><input type="text" id="storage-allocateOut-businessdate" class="len130" value="${allocateOut.businessdate }" name="allocateOut.businessdate" readonly="readonly"/></td>
						<td>状态：</td>
						<td>
							<select id="storage-allocateOut-status-select" disabled="disabled" class="len136">
								<c:forEach items="${statusList }" var="list">
									<c:choose>
										<c:when test="${list.code == allocateOut.status}">
											<option value="${list.code }" selected="selected">${list.codename }</option>
										</c:when>
										<c:otherwise>
											<option value="${list.code }">${list.codename }</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
							<input type="hidden" id="storage-allocateOut-status" name="allocateOut.status" value="${allocateOut.status }" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>调出仓库：</td>
						<td>
							<input type="text" id="storage-allocateOut-outstorageid" name="allocateOut.outstorageid" class="len130" value="${allocateOut.outstorageid }" readonly="readonly"/>
							<input type="hidden" id="storage-allocateOut-outstorageid-old" value="${allocateOut.outstorageid} "/>
						</td>
						<td>调入仓库:</td>
						<td>
							<input type="text" id="storage-allocateOut-enterstorageid"  name="allocateOut.enterstorageid" class="len130" value="${allocateOut.enterstorageid }" readonly="readonly"/>
						</td>
						<td>来源类型：</td>
						<td>
							<select disabled="disabled" style="width: 136px">
								<option value="0" <c:if test="${allocateOut.sourcetype=='0'}">selected="selected"</c:if>>无</option>
								<option value="1" <c:if test="${allocateOut.sourcetype=='1'}">selected="selected"</c:if>>调拨通知单</option>
							</select>
							<input type="hidden" name="allocateOut.sourcetype" value="${allocateOut.sourcetype }"/>
							<input type="hidden" id="storage-allocateOut-sourceid" name="allocateOut.sourceid" value="${allocateOut.sourceid }"/>
						</td>
					</tr>
					<tr>
						<td>出库部门：</td>
						<td>
							<input type="text" id="storage-allocateOut-outdeptid" readonly="readonly" name="allocateOut.outdeptid" value="${allocateOut.outdeptid}" class="len150" />
						</td>
						<td>调入部门:</td>
						<td>
							<input type="text" id="storage-allocateOut-enterdeptid" readonly="readonly" value="${allocateOut.enterdeptid}" name="allocateOut.enterdeptid" class="len136"/>
						</td>
						<td <c:if test="${isAllocateShowBilltype=='0'}">style="display: none;" </c:if>>调拨类型：</td>
						<td <c:if test="${isAllocateShowBilltype=='0'}">style="display: none;" </c:if>>
							<select style="width: 136px" disabled="disabled" id="storage-allocateOut-billtype" >
								<option value="1" <c:if test="${allocateOut.billtype=='1'}">selected="selected"</c:if>>成本调拨</option>
								<option value="2" <c:if test="${allocateOut.billtype=='2'}">selected="selected"</c:if>>异价调拨</option>
							</select>
							<input type="hidden" id="storage-allocateOut-billtype-hidden" name="allocateOut.billtype" />
						</td>
					</tr>
					<tr>
						<td class="len80 left">备注：</td>
						<td colspan="5" style="text-align: left">
							<input type="text" name="allocateOut.remark" style="width: 668px;" value="<c:out value="${allocateOut.remark }"></c:out>" readonly="readonly"/>
						</td>
					</tr>
	    		</table>
	    		<input type="hidden" id="storage-allocateOut-printtimes" name="allocateOut.printtimes" value="${allocateOut.printtimes }" readonly="readonly"/>
	    	<input type="hidden" id="storage-allocateOut-printlimit" value="${printlimit }" readonly="readonly"/>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="storage-datagrid-allocateOutAddPage"></table>
	    	</div>
	    	<input type="hidden" id="storage-allocateOut-allocateOutDetail" name="detailJson"/>
	    </form>
    </div>
    <div id="storage-dialog-allocateOutAddPage"></div>
    <script type="text/javascript">
    	$(function(){
			$("#storage-datagrid-allocateOutAddPage").datagrid({ //采购入库单明细信息编辑
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
	            			$("#storage-datagrid-allocateOutAddPage").datagrid('appendRow',{});
	            		}
   					}else{
   						$("#storage-datagrid-allocateOutAddPage").datagrid('appendRow',{});
   					}
    				countTotal();
    			}
    		}).datagrid('columnMoving');
			$("#storage-allocateOut-outdeptid").widget({
				referwid:'RL_T_BASE_DEPATMENT',
				width:160,
				singleSelect:true
			})
			$("#storage-allocateOut-enterdeptid").widget({
				referwid:'RL_T_BASE_DEPATMENT',
				width:130,
				singleSelect:true
			})
    		$("#storage-allocateOut-outstorageid").widget({
    			name:'t_storage_allocate_notice',
	    		width:160,
				col:'outstorageid',
				singleSelect:true,
				required:true
    		});
    		$("#storage-allocateOut-enterstorageid").widget({
    			name:'t_storage_allocate_notice',
	    		width:130,
				col:'enterstorageid',
				singleSelect:true,
				initValue:'${allocateOut.enterstorageid}'
    		});   		
    	});
    	
    	//控制按钮状态
    	$("#storage-buttons-allocateOutPage").buttonWidget("setDataID",{id:'${allocateOut.id}',state:'${allocateOut.status}',type:'view'});
    	$("#storage-hidden-billid").val("${allocateOut.id}");

    	$("#storage-buttons-allocateOutPage").buttonWidget("enableButton","button-print");
    	$("#storage-buttons-allocateOutPage").buttonWidget("enableButton","button-preview");
    	<c:if test="${allocateOut.sourcetype=='0'}">
	    	$("#storage-buttons-allocateOutPage").buttonWidget("disableMenuItem","relation-upper-view");
	    </c:if>
	    <c:if test="${allocateOut.sourcetype!='0'}">
	    	$("#storage-buttons-allocateOutPage").buttonWidget("enableMenuItem","relation-upper-view");
	    </c:if>

		$("#storage-buttons-allocateOutPage").buttonWidget("disableMenuItem","storage-out-oppaudit");
		$("#storage-buttons-allocateOutPage").buttonWidget("disableMenuItem","storage-out-audit");
		$("#storage-buttons-allocateOutPage").buttonWidget("disableMenuItem","storage-enter-audit");
<c:if test="${allocateOut.status=='2'}">
	$("#storage-buttons-allocateOutPage").buttonWidget("enableMenuItem","storage-out-audit");
</c:if>
<c:if test="${allocateOut.status=='7'}">
	$("#storage-buttons-allocateOutPage").buttonWidget("enableMenuItem","storage-out-oppaudit");
		$("#storage-buttons-allocateOutPage").buttonWidget("enableMenuItem","storage-enter-audit");
		$("#storage-buttons-allocateOutPage").buttonWidget("disableButton","button-audit");
</c:if>
		<c:if test="${allocateOut.status=='4'}">
		$("#storage-buttons-allocateOutPage").buttonWidget("disableMenuItem","storage-out-audit");
		$("#storage-buttons-allocateOutPage").buttonWidget("disableMenuItem","storage-out-oppaudit");
		$("#storage-buttons-allocateOutPage").buttonWidget("disableMenuItem","storage-enter-audit");
		</c:if>
    </script>
  </body>
</html>
