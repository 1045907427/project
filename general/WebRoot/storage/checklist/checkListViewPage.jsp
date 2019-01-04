<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>盘点单</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false" style="height:105px;">
    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
    			<tr>
    				<td class="len80 left">编号：</td>
    				<td class="len165"><input type="text" id="storage-checkList-thisid" class="len140 easyui-validatebox" name="checkList.id" readonly='readonly' value="${checkList.id}" /></td>
    				<td class="len80 left">业务日期：</td>
    				<td class="len165"><input type="text" id="storage-checkList-businessdate" style="width: 135px"  value="${checkList.businessdate}" name="checkList.businessdate" readonly="readonly"/></td>
    				<td class="len80 left">状态：</td>
    				<td class="len165">
    					<select disabled="disabled" class="len136">
    						<c:forEach items="${statusList }" var="list">
    							<c:choose>
    								<c:when test="${list.code == checkList.status}">
    									<option value="${list.code }" selected="selected">${list.codename }</option>
    								</c:when>
    								<c:otherwise>
    									<option value="${list.code }">${list.codename }</option>
    								</c:otherwise>
    							</c:choose>
    						</c:forEach>
    					</select>
    					<input type="hidden" name="checkList.status" value="${checkList.status }" />
    				</td>
    			</tr>
    			<tr>
    				<td class="len80 left">所属仓库：</td>
    				<td>
                        <input type="text" id="storage-checkList-storageid" name="checkList.storageid" value="${checkList.storageid}" readonly="readonly"/>
    				</td>
    				<td class="len80 left">生成方式：</td>
    				<td style="text-align: left">
    					<select name="checkList.createtype" class="len136" disabled="disabled">
    						<option value="1" <c:if test="${checkList.createtype=='1'}">selected="selected"</c:if>>手工生成</option>
	    					<option value="2" <c:if test="${checkList.createtype=='2'}">selected="selected"</c:if>>系统生成</option>
    					</select>
    					<input type="hidden" id="storage-checkList-createtype" name="checkList.createtype" value="${checkList.createtype}"/>
						<input type="hidden" id="storage-checkList-printtimes" value="${checkList.printtimes}"/>
    				</td>
    				<td class="len80 left">盘点状态：</td>
	    				<td style="text-align: left">
	    					<select id="storage-checkList-isfinish" name="checkList.isfinish" class="len136" disabled="disabled">
	    						<option value="0" <c:if test="${checkList.isfinish=='0'}">selected="selected"</c:if>>未完成</option>
	    						<option value="1" <c:if test="${checkList.isfinish=='1'}">selected="selected"</c:if>>完成</option>
	    					</select>
	    				</td>
    				</tr>
    			<tr>
    				<td class="len80 left">第几次盘点：</td>
    				<td style="text-align: left">
    					<input type="text" value="${checkList.checkno }" class="len140"  readonly="readonly"/>
    				</td>
    				<td class="len80 left">盘点人：${thisUserid}</td>
    				<td style="text-align: left">
    					<select id="storage-checkList-checkuserid" name="checkList.checkuserid" class="len136" disabled="disabled">
	   						<c:forEach items="${userList }" var="list">
							<option value="${list.id }" <c:if test="${list.id == checkList.checkuserid}">selected="selected"</c:if>>${list.name }</option>
	   						</c:forEach>
	   					</select>
    				</td>
    				<td class="len80 left">备注：</td>
    				<td>
    					<input type="text" name="checkList.remark" style="width:135px;" value="<c:out value="${checkList.remark }"></c:out>" readonly="readonly"/>
    				</td>
    			</tr>
    		</table>
    	</div>
    	<div data-options="region:'center',border:false">
    		<table id="storage-datagrid-checkListAddPage"></table>
    	</div>
    	<input type="hidden" id="storage-checkList-checkListDetail" name="checkListDetailJson"/>
    </div>
    <div id="storage-dialog-checkListAddPage"></div>
    <script type="text/javascript">
		var CLD_footerobject = null;
    	$(function(){
			$("#storage-datagrid-checkListAddPage").datagrid({ //销售商品明细信息编辑
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
				url: 'storage/getCheckListDetailData.do?id=${checkList.id}',
				pagination:true,
				pageSize:500,
    			onSortColumn:function(sort, order){
    				var rows = $("#storage-datagrid-checkListAddPage").datagrid("getRows");
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
    				$("#storage-datagrid-checkListAddPage").datagrid("loadData",dataArr);
    				return false;
    			},
    			onLoadSuccess:function(data){
    				if(data.rows.length<10){
	            		var j = 10-data.rows.length;
	            		for(var i=0;i<j;i++){
	            			$("#storage-datagrid-checkListAddPage").datagrid('appendRow',{});
	            		}
   					}
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						CLD_footerobject = footerrows[0];
						countTotal();
					}
    			}
    		}).datagrid('columnMoving');    		
    	});
        //所属仓库
        $("#storage-checkList-storageid").widget({
            width:140,
            referwid:'RL_T_BASE_STORAGE_INFO',
            singleSelect:true,
            onlyLeafCheck:false
        });
    	//控制按钮状态
    	$("#storage-buttons-checkListPage").buttonWidget("setDataID",{id:'${checkList.id}',state:'${checkList.status}',type:'view'});
    	$("#storage-hidden-billid").val("${checkList.id}");
    	<c:if test="${checkList.status=='2' }">
    		$("#storage-buttons-checkListPage").buttonWidget("enableButton", 'check-finish-button');
    		<c:if test="${checkList.isfinish=='1' }">
	    		$("#storage-buttons-checkListPage").buttonWidget("enableButton", 'check-button');
	    		$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-finish-button');
	    		$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'button-import');
	    		import_disabled = true;
	    	</c:if>
	    	<c:if test="${checkList.isfinish!='1' }">
	    		$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-button');
	    		$("#storage-buttons-checkListPage").buttonWidget("enableButton", 'button-import');
	    		import_disabled = false;
	    	</c:if>
	    	$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-add-button');
	    	$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-addadjust-button');
    	</c:if>
    	<c:if test="${checkList.status!='2' }">
    		$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-finish-button');
    		$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-button');
    		$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-add-button');
    		<c:if test="${checkList.isfinish=='1' }">
	    		$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'button-import');
	    		import_disabled = true;
	    	</c:if>
	    	<c:if test="${checkList.isfinish!='1' }">
	    		$("#storage-buttons-checkListPage").buttonWidget("enableButton", 'button-import');
	    		import_disabled = false;
	    	</c:if>
    		<c:if test="${checkList.status=='3' }">
	    		<c:if test="${checkList.istrue=='1' }">
		    		$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-add-button');
		    	</c:if>
		    	<c:if test="${checkList.istrue=='0' }">
		    		$("#storage-buttons-checkListPage").buttonWidget("enableButton", 'check-add-button');
		    	</c:if>
	    	</c:if>
	    	<c:if test="${checkList.status=='4' }">
	    		$("#storage-buttons-checkListPage").buttonWidget("enableButton", 'check-addadjust-button');
	    	</c:if>
	    	$("#storage-buttons-checkListPage").buttonWidget("enableButton", 'check-addadjust-button');
    	</c:if>
    	<c:if test="${checkList.isaddnew=='1' }">
    		$("#storage-buttons-checkListPage").buttonWidget("disableButton", 'check-add-button');
    	</c:if>
    </script>
  </body>
</html>
