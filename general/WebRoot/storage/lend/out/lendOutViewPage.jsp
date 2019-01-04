<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>借货单新增页面</title>
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
	    				<td class="len165"><input type="text" id="storage-lend-businessdate" class="len130"  value="${lend.businessdate }" name="lend.businessdate" readonly="readonly"/></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select disabled="disabled" class="len136">
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
						<td class="len80 left">借货人类型：</td>
						<td>
							<input type="text" id="storage-lend-lendtype" name="lend.lendtype" value="${lend.lendtype}" readonly="readonly"/>
						</td>
						<td class="len80 left">借货人：</td>
						<td colspan="3" id="lendp">
							<input id="storage-lend-lendid" name="lend.lendname" value="${lend.lendname}" style="width: 400px" readonly="readonly"/>
						</td>
	    			</tr>
	    			<tr>
						<td class="len80 left">出货仓库：</td>
						<td>
							<input type="text" id="storage-lend-storageid" name="lend.storageid" value="${lend.storageid}" readonly="readonly"/>
						</td>
						<td class="len80 left">相关部门：</td>
						<td>
							<input id="storage-lend-deptid" name="lend.deptid" value="${lend.deptid}" readonly="readonly"/>
						</td>
						<td class="len80 left">备注：</td>
						<td style="text-align: left;"><input type="text" style="width: 136px" name="lend.remark" value="<c:out value="${lend.remark}"></c:out>" readonly="readonly"/></td>
					</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
				<input type="hidden" value="${lend.printtimes}" id="storage-lend-printtimes"/>
	    		<input type="hidden" name="detailJson" id="storage-lendOut-lendDetail" />
	    		<table id="storage-datagrid-lendAddPage"></table>
	    	</div>
	    </form>
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
    			}
    		}).datagrid('columnMoving');

    	});
    	$("#storage-buttons-lendPage").buttonWidget("setDataID",{id:'${lend.id}',state:'${lend.status}',type:'view'});
    	$("#storage-hidden-billid").val("${lend.id}");
    	<c:if test="${lend.status=='4'}">
    		$("#storage-buttons-lendPage").buttonWidget("enableButton", 'button-oppaudit');
    	</c:if>


        $("#storage-lend-storageid").widget({
            width:136,
            referwid:'RL_T_BASE_STORAGE_INFO',
            singleSelect:true,
            onlyLeafCheck:false
        });

		//部门控件
		$("#storage-lend-deptid").widget({
			name: 't_storage_lend',
			col: 'deptid',
			singleSelect: true,
			isdatasql: false,
			width: 225,
			onlyLeafCheck: false,
			onSelect : function(data){
				$("#storage-lend-lendid").widget({
					name: 't_storage_lend',
					col:'lendid',
					param:[{field:'belongdeptid',op:'startwith',value:data.id}],
					singleSelect: true,
					onlyLeafCheck: false
				})
			},
			onClear:function(){
				$("#storage-lend-lendid").val("");
			}
		});

		$('#storage-lend-lendtype').widget({
			width:136,
			referwid:'RL_T_SYS_CODELENDTYPE',
			singleSelect:true,
			onlyLeafCheck:false
		});

    </script>
  </body>
</html>
