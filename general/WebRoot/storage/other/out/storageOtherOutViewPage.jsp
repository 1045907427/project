<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>其他出库单新增页面</title>
  </head>  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-storageOtherOutAdd"  method="post">
	    	<div data-options="region:'north',border:false" style="height:135px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="easyui-validatebox" style="width: 135px" name="storageOtherOut.id" value="${storageOtherOut.id }" readonly="readonly"/></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" id="storage-storageOtherOut-businessdate" class="len130"  value="${storageOtherOut.businessdate }" name="storageOtherOut.businessdate" readonly="readonly"/></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select disabled="disabled" class="len136">
    						<c:forEach items="${statusList }" var="list">
    							<c:choose>
    								<c:when test="${list.code == storageOtherOut.status}">
    									<option value="${list.code }" selected="selected">${list.codename }</option>
    								</c:when>
    								<c:otherwise>
    									<option value="${list.code }">${list.codename }</option>
    								</c:otherwise>
    							</c:choose>
    						</c:forEach>
    						</select>
    						<input type="hidden" name="storageOtherOut.status" value="${storageOtherOut.status }"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">出库仓库：</td>
	    				<td>
                            <input type="text" id="storage-storageOtherOut-storageid" name="storageOtherOut.storageid" value="${storageOtherOut.storageid }" readonly="readonly"/>
	    				</td>
						<td class="len80 left">出库类型：</td>
						<td><input id="storage-otherOut-outtype" name="storageOtherOut.outtype" class="len130" value="${storageOtherOut.outtype }" readonly="readonly">
						</td>
	    				<td class="len80 left">相关部门：</td>
	    				<td><input id="storage-otherOut-deptid" name="storageOtherOut.deptid" value="${storageOtherOut.deptid }" readonly="readonly"/>
	    				</td>

	    			</tr>
	    			<tr>
						<td class="len80 left">来源类型：</td>
						<td><input id="storage-otherOut-sourcetype" name="sourcetype" value="<c:out value="${storageOtherOut.sourcetype}"></c:out>"  readonly="readonly"></td>
						<td class="len100 left">来源单据编号：</td>
						<td>
							<input id="storage-otherOut-sourceid" class="len130" name="sourceid" value="${storageOtherOut.sourceid}" readonly="readonly">
						</td>
						<td class="len80 left">相关人员：</td>
						<td>
							<select id="storage-otherOut-userid" class="len130" name="storageOtherOut.userid" disabled="disabled">
								<option value="${storageOtherOut.userid }">${storageOtherOut.username}</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="len80 left">备注：</td>
						<td colspan="5" style="text-align: left;"><input type="text" name="storageOtherOut.remark" style="width: 686px;" value="<c:out value="${storageOtherOut.remark}"></c:out>" readonly="readonly"/></td>
					</tr>

	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="detailJson" id="storage-purchaseEnter-storageOtherOutDetail" />
	    		<table id="storage-datagrid-storageOtherOutAddPage"></table>
	    	</div>
	    </form>
    </div>
    <div id="storage-dialog-storageOtherOutAddPage"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#storage-datagrid-storageOtherOutAddPage").datagrid({ //销售商品明细信息编辑
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
	            			$("#storage-datagrid-storageOtherOutAddPage").datagrid('appendRow',{});
	            		}
   					}else{
   						$("#storage-datagrid-storageOtherOutAddPage").datagrid('appendRow',{});
   					}
    				countTotal();
    			}
    		}).datagrid('columnMoving');

    	});
    	$("#storage-buttons-storageOtherOutPage").buttonWidget("setDataID",{id:'${storageOtherOut.id}',state:'${storageOtherOut.status}',type:'view'});
    	$("#storage-hidden-billid").val("${storageOtherOut.id}");
    	<c:if test="${storageOtherOut.status=='4'}">
    		$("#storage-buttons-storageOtherOutPage").buttonWidget("enableButton", 'button-oppaudit');
    	</c:if>

		$("#storage-otherOut-sourcetype").widget({
            referwid:'RL_T_SYS_CODESOURCETYPE',
			// name: 't_storage_other_out',
			// col: 'sourcetype',
			singleSelect: true,
			isdatasql: false,
			width:135,
			onlyLeafCheck: false
		});

        <%-- 来源为拆装单时，禁止反审 --%>
        <c:if test="${storageOtherOut.outtype eq 4}">
            $('#storage-buttons-storageOtherOutPage').buttonWidget('disableButton', 'button-oppaudit');
        </c:if>
		<%-- 来源为借货还货单时，禁止反审 --%>
		<c:if test="${storageOtherOut.sourcetype eq 2}">
		$('#storage-buttons-storageOtherOutPage').buttonWidget('disableButton', 'button-oppaudit');
		</c:if>
        $("#storage-storageOtherOut-storageid").widget({
            width:136,
            referwid:'RL_T_BASE_STORAGE_INFO',
            singleSelect:true,
            onlyLeafCheck:false
        });

		$("#storage-otherOut-outtype").widget({
			referwid:'RL_T_SYS_CODE_OUT_TYPE',
			singleSelect: true,
			isdatasql: false,
			width: 130,
			onlyLeafCheck: false
		});

		$("#storage-otherOut-deptid").widget({
			referwid:'RL_T_BASE_DEPARTMENT_1',
			singleSelect: true,
			isdatasql: false,
			width: 136,
			onlyLeafCheck: false
		});
    </script>
  </body>
</html>
