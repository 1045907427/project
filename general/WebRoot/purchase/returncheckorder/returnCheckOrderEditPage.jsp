
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
  <div class="easyui-panel" data-options="fit:true,border:false">
	  <div class="easyui-layout" data-options="fit:true">
	  	<form action="purchase/returnorder/updateReturnOrderCheck.do" id="purchase-form-returnCheckOrderAddPage" method="post">
	  		<input type="hidden" id="purchase-returnCheckOrderAddPage-addType" name="addType"/>
	  		<input type="hidden" id="purchase-returnCheckOrderAddPage-saveaudit" name="saveaudit"/>
	  		<div data-options="region:'north',border:false" style="height:150px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="width:80px;text-align: left;">编号：</td>
						<td style="width:165px;"><input type="text" style="width:150px;" readonly="readonly" name="returnOrder.id" value="${returnOrder.id }" /></td>
						<td style="width:80px;text-align: left;">业务日期：</td>
						<td style="width:165px;"><input type="text" id="purchase-returnCheckOrderAddPage-businessdate" name="returnOrder.businessdate" value='${returnOrder.businessdate }' style="width:130px;" readonly="readonly" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
						<td style="width:80px;text-align: left;">状态：</td>
				    	<td style="width:165px;">			    		
	    					<select id="purchase-returnCheckOrderAddPage-status" disabled="disabled" class="len130">
	    						<c:forEach items="${statusList }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == returnOrder.status}">
	    									<option value="${list.code }" selected="selected">${list.codename }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
	    					</select>
						</td>
					</tr>
					<tr>
						<td>供应商：</td>
						<td colspan="3"><input type="text" id="purchase-returnCheckOrderAddPage-supplier" style="width:300px;" name="returnOrder.supplierid" value="${returnOrder.supplierid }" title="<c:out value="${returnOrder.suppliername}"></c:out>" text="<c:out value="${returnOrder.suppliername}"></c:out>" readonly="readonly" />
							<span id="purchase-supplier-showid-returnCheckOrderAddPage" style="margin-left:5px;line-height:25px;">编号：${returnOrder.supplierid }</span>
						</td>
                        <td>验收状态：</td>
                        <td>
                            <select style="width:130px" disabled="disabled">
                                <option value=""></option>
                                <option value="0" <c:if test="${returnOrder.ischeck=='0' }">selected="selected"</c:if>>未验收</option>
                                <option value="1" <c:if test="${returnOrder.ischeck=='1' }">selected="selected"</c:if>>已验收</option>
                            </select>
                        </td>
					</tr>
					<tr>
						<td style="">退货仓库：</td>
				    	<td><input type="text" id="purchase-returnCheckOrderAddPage-storage" name="returnOrder.storageid" style="width:150px;" /></td>
						<td style="">采购部门：</td>
				    	<td>
				    		<select id="purchase-returnCheckOrderAddPage-buydept" name="returnOrder.buydeptid" style="width:130px;" >
					    		<option value=""></option>
					    		<c:forEach items="${buyDept}" var="list">
	    							<c:choose>
	    								<c:when test="${list.id == returnOrder.buydeptid}">
	    									<option value="${list.id }" selected="selected">${list.name }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.id }">${list.name }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
    						</select>
						</td>
						<td style="">采购员：</td>
						<td>
							<input type="text" id="purchase-returnCheckOrderAddPage-buyuser" name="returnOrder.buyuserid" style="width:130px;" value="${ returnOrder.buyuserid }" />
						</td>					
					</tr>
					<tr>

						<td>备注：</td>
						<td colspan="5">
							<input type="text" style="width:660px;" name="returnOrder.remark" class="readonly" readonly="readonly" value="<c:out value="${returnOrder.remark}"></c:out>"/>
						</td>		
					</tr>
				</table>
	  		</div>
	  		<div data-options="region:'center',border:false">
	  			<table id="purchase-returnCheckOrderAddPage-returnCheckOrdertable"></table>
				<input type="hidden" id="purchase-returnCheckOrderAddPage-returnCheckOrderDetails" name="returnCheckOrderDetails" />
	  		</div>
	  	</form> 
	  </div>
  </div>
  <div id="purchase-Button-tableMenu" class="easyui-menu" style="width:120px;display: none;">
        <div id="purchase-tableMenu-itemEdit" iconCls="icon-edit">编辑</div>  
  </div>
  <div id="purchase-returnCheckOrderAddPage-dialog-DetailOper" ></div>
  <script type="text/javascript">
  	var editRowIndex = undefined;
  	function getAddRowIndex(){
  		var $potable=$("#purchase-returnCheckOrderAddPage-returnCheckOrdertable");
  		var dataRows=$potable.datagrid('getRows');
  		
  		var rindex=0;
  		for(rindex=0;rindex<dataRows.length;rindex++){
  	  		if(dataRows[rindex].goodsid==null || dataRows[rindex].goodsid==""){
  	  	  		break;
  	  		}
  		}
  		if(rindex==dataRows.length){
  	  		$potable.datagrid('appendRow',{});
  		}
  		return rindex;
  	}
  	$(document).ready(function(){
  		$("#purchase-buttons-returnCheckOrderPage").buttonWidget("setDataID",  {id:'${returnOrder.id}',state:'${returnOrder.status}',type:'edit'});
		$("#purchase-buttons-returnCheckOrderPage").buttonWidget("disableButton","button-returnorder-savecheck"); 	
		$("#purchase-buttons-returnCheckOrderPage").buttonWidget("disableButton","button-returnorder-checkcancel");
		<c:choose>
			<c:when test="${returnOrder.ischeck == 0}">
				$("#purchase-buttons-returnCheckOrderPage").buttonWidget("enableButton","button-returnorder-savecheck"); 
				$("#purchase-buttons-returnCheckOrderPage").buttonWidget("disableButton","button-returnorder-checkcancel");
			</c:when>
			<c:when test="${returnOrder.ischeck == 1}">
				$("#purchase-buttons-returnCheckOrderPage").buttonWidget("disableButton","button-returnorder-savecheck"); 
				$("#purchase-buttons-returnCheckOrderPage").buttonWidget("enableButton","button-returnorder-checkcancel");
			</c:when>
		</c:choose>
  	  	var $returnCheckOrdertable=$("#purchase-returnCheckOrderAddPage-returnCheckOrdertable");
  	  	$returnCheckOrdertable.datagrid({
	  	  	fit:true,
	  	  	striped:true,
	 		method:'post',
	 		rownumbers:true,
	 		//idField:'id',
	 		singleSelect:true,
	 		showFooter:true,
  	 		data: JSON.parse('${goodsDataList}'),
  	 		authority:tableColJson,
  	 		frozenColumns: tableColJson.frozen,
			columns:tableColJson.common,
  	 		onLoadSuccess:function(){
	  	  		var $potable=$("#purchase-returnCheckOrderAddPage-returnCheckOrdertable");
	  	  		var dataRows=$potable.datagrid('getRows');
	  	  		var rowlen=dataRows.length;
		  	  	if(rowlen<12){
			  	  	for(var i=rowlen;i<12;i++){
						$returnCheckOrdertable.datagrid('appendRow', {});
			  	  	}
				}
  	  			footerReCalc();
  	 		},
        	onDblClickRow: function(rowIndex, rowData){ //选中行
  	 			editRowIndex=rowIndex;
  	 			if(rowData.goodsid != undefined && rowData.goodsid!=""){
  	 				orderDetailEditDialog(rowData);
  	 			}
        	},
  	 		onRowContextMenu:function(e, rowIndex, rowData){
  	 			if(rowData.goodsid == undefined || rowData.goodsid==""){
  	 				return;
  	 			}
  	 			e.preventDefault();
  	 			var $contextMenu=$('#purchase-Button-tableMenu');
  	 			$contextMenu.menu('show', {
					left : e.pageX,
					top : e.pageY
				});
  	 			$returnCheckOrdertable.datagrid('selectRow', rowIndex);
				editRowIndex=rowIndex;
                $contextMenu.menu('enableItem', '#purchase-tableMenu-itemEdit');
  	 		},
            onSortColumn:function(sort, order){
                return detailOnSortColumn(sort,order);
            }
  	  	}).datagrid("columnMoving");
		
  		//编辑按钮事件
		$("#purchase-tableMenu-itemEdit").unbind("click").bind("click",function(){
			if($("#purchase-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			var data=$("#purchase-returnCheckOrderAddPage-returnCheckOrdertable").datagrid('getSelected');
			if(null!=data && data.goodsid !=null && data.goodsid!=""){
				orderDetailEditDialog(data);		
			}else{
				orderDetailAddDialog();	
			}
		});
		
		$("#purchase-returnCheckOrderAddPage-buyuser").widget({
			name:'t_base_buy_supplier',
			col:'buyuserid',
			width:130,
    		async:false,
			singleSelect:true,
			onlyLeafCheck:false
		});
		
		$.getJSON('basefiles/getContacterBy.do', {type:"2", id:"${returnOrder.supplierid}"}, function(json){
			if(json.length>0){
				$("#purchase-returnCheckOrderAddPage-handler").html("");
				$("#purchase-returnCheckOrderAddPage-handler").append("<option value=''></option>");
				for(var i=0;i<json.length;i++){
					$("#purchase-returnCheckOrderAddPage-handler").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
				}
				$("#purchase-returnCheckOrderAddPage-handler").val("${returnOrder.handlerid}");
			}
		});
		
		/*
		$("#purchase-returnCheckOrderAddPage-buydept").change(function(){
			var v = $(this).val();
			if(v!=null && v !=""){
				$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: v}, function(json){
	    			if(json.length>0){
	    				$("#purchase-returnCheckOrderAddPage-buyuser").html("");
	    				$("#purchase-returnCheckOrderAddPage-buyuser").append("<option value=''></option>");
	    				for(var i=0;i<json.length;i++){
	    					$("#purchase-returnCheckOrderAddPage-buyuser").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    				}
	    			}	
	    		});
			}else{
				$("#purchase-returnCheckOrderAddPage-buyuser").html("");
			}
		});
		*/
		$("#purchase-returnCheckOrderAddPage-supplier").supplierWidget({ 
		});
		
		$("#purchase-returnCheckOrderAddPage-storage").widget({ 
			name:'t_purchase_returnorder',
			col:'storageid',
			width:150,	
			<c:if test="${returnOrder.source!='2'}">   //来源代配送时 不需要仓库
			required:true,
			</c:if>
			readonly:true,
			initValue:'${returnOrder.storageid}',
			singleSelect:true,
			onlyLeafCheck:true
		});
  	});
  </script>
  </body>
</html>
