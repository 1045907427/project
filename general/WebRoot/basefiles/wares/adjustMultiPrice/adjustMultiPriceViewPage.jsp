<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>调价单新增页面</title>
</head>
<body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="goods-form-adjustPriceAddPage" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height: 70px;">
				<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
					<tr>
						<td class="len80 left">编号：</td>
						<td class="len165"><input id="goods-adjustMultiPrice-id" class="easyui-validatebox" name="adjustMultiPrice.id" value="${adjustMultiPrice.id }"  readonly="readonly" style="width:160px;"/></td>
						<td class="len80 right">业务日期：</td>
						<td class="len165"><input type="text" id="goods-adjustMultiPrice-businessdate" readonly="readonly" value="${adjustMultiPrice.businessdate }"  name="adjustMultiPrice.businessdate" style="width:160px;"/></td>
						<td class="len80 right">状态：</td>
						<td class="len165"><select id="goods-adjustMultiPrice-status-select" disabled="disabled" style="width:160px;">
							<c:forEach items="${statusList }" var="list">
								<c:choose>
									<c:when test="${list.code == adjustMultiPrice.status}">
										<option value="${list.code }" selected="selected">${list.codename }</option>
									</c:when>
									<c:otherwise>
										<option value="${list.code }">${list.codename }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td>调价单名称：</td>
						<td>
							<input type="text" id="goods-adjustMultiPrice-name" name="adjustMultiPrice.name" readonly="readonly" value="${adjustMultiPrice.name }"  style="width:160px;" />
						</td>
						<td class="len80 right">备注：</td>
						<td colspan="3" style="text-align: left">
							<input type="text"  id="goods-adjustMultiPrice-remark" name="adjustMultiPrice.remark" readonly="readonly" value="${adjustMultiPrice.remark}" style="width: 425px;" onfocus="frm_focus('adjustMultiPrice.remark');" onblur="frm_blur('adjustMultiPrice.remark');"/>
						</td>
					<tr>
				</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="goods-datagrid-adjustMultiPriceAddPage"></table>
	    	</div>
	    </form>
    </div>
    <script type="text/javascript">
    	$(function(){

			$("#goods-buttons-adjustMultiPricePage").buttonWidget("initButtonType", 'add');
			$("#goods-datagrid-adjustMultiPriceAddPage").datagrid({
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
				data:JSON.parse('${detailList}'),
				onLoadSuccess: function(data){
					if(data.rows.length<12){
						var j = 12-data.rows.length;
						for(var i=0;i<j;i++){
							$(this).datagrid('appendRow',{});
						}
					}else{
						$(this).datagrid('appendRow',{});
					}
				}
			}).datagrid('columnMoving');

		});
    	//控制按钮状态
    	$("#goods-buttons-adjustPricePage").buttonWidget("setDataID",{id:'${adjustMultiPrice.id}',state:'${adjustMultiPrice.status}',type:'view'});
    </script>
</body>

</html>