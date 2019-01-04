
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
	  	<form action="purchase/returnorder/editReturnOrder.do" id="purchase-form-returnOrderAddPage" method="post">
	  		<input type="hidden" id="purchase-returnOrderAddPage-addType" name="addType"/>
	  		<input type="hidden" id="purchase-returnOrderAddPage-saveaudit" name="saveaudit"/>
    		<input type="hidden" id="purchase-returnOrderAddPage-printtimes" value="${returnOrder.printtimes}" />
	  		<div data-options="region:'north',border:false" style="height:100px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="width:80px;text-align: left;">编号：</td>
						<td style="width:165px;"><input type="text" class="len150" readonly="readonly" name="returnOrder.id" value="${returnOrder.id }" /></td>
						<td style="width:80px;text-align: left;">业务日期：</td>
						<td style="width:165px;"><input type="text" class="len150" id="purchase-returnOrderAddPage-businessdate" name="returnOrder.businessdate" value='${returnOrder.businessdate }' readonly="readonly" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
						<td style="width:80px;text-align: left;">状态：</td>
				    	<td style="width:165px;">			    		
	    					<select id="purchase-returnOrderAddPage-status" disabled="disabled" class="len150">
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
						<td colspan="3"><input type="text" id="purchase-returnOrderAddPage-supplier" style="width:320px;" name="returnOrder.supplierid" value="${returnOrder.supplierid }" title="<c:out value="${returnOrder.suppliername}"></c:out>" text="<c:out value="${returnOrder.suppliername}"></c:out>" readonly="readonly" />
							<span id="purchase-supplier-showid-returnOrderAddPage" style="margin-left:5px;line-height:25px;">编号：${returnOrder.supplierid }</span>
						</td>
                        <td style="">退货仓库：</td>
                        <td><input type="text" id="purchase-returnOrderAddPage-storage" name="returnOrder.storageid" /></td>
					</tr>
					<tr>
						<td style="">采购部门：</td>
				    	<td>
				    		<select id="purchase-returnOrderAddPage-buydept" name="returnOrder.buydeptid" class="len150">
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
							<input type="text" id="purchase-returnOrderAddPage-buyuser" name="returnOrder.buyuserid" value="${ returnOrder.buyuserid }" />
						</td>
                        <td>备注：</td>
                        <td>
                            <input type="text" style="width:150px;" name="returnOrder.remark" value="<c:out value="${returnOrder.remark}"></c:out>"/>
                        </td>
					</tr>
				</table>
	  		</div>
	  		<div data-options="region:'center',border:false">
	  			<table id="purchase-returnOrderAddPage-returnOrdertable"></table>
				<input type="hidden" id="purchase-returnOrderAddPage-returnOrderDetails" name="returnOrderDetails"/>
	  		</div>
	  	</form> 
	  </div>
  </div>
  <div id="purchase-Button-tableMenu" class="easyui-menu" style="width:120px;display: none;">
        <div id="purchase-tableMenu-itemAdd" iconCls="button-add">添加</div>
        <div id="purchase-tableMenu-itemEdit" iconCls="button-edit">编辑</div>
        <div id="purchase-tableMenu-itemDelete" iconCls="button-delete">删除</div>
  </div>
  <script type="text/javascript">
  	var editRowIndex = undefined;
  	function getAddRowIndex(){
  		var $potable=$("#purchase-returnOrderAddPage-returnOrdertable");
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
  		$("#purchase-buttons-returnOrderPage").buttonWidget("setDataID",  {id:'${returnOrder.id}',state:'${returnOrder.status}',type:'edit'});
  		<c:if test="${returnOrder.ckstatus == '1'}">
			$("#purchase-buttons-returnOrderPage").buttonWidget("disableButton","button-delete"); 	
			$("#purchase-buttons-returnOrderPage").buttonWidget("disableButton","button-oppaudit");	
		</c:if>
  	  	var $returnOrdertable=$("#purchase-returnOrderAddPage-returnOrdertable");
  	  	$returnOrdertable.datagrid({
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
	  	  		var $potable=$("#purchase-returnOrderAddPage-returnOrdertable");
	  	  		var dataRows=$potable.datagrid('getRows');
	  	  		var rowlen=dataRows.length;
		  	  	if(rowlen<12){
			  	  	for(var i=rowlen;i<12;i++){
						$returnOrdertable.datagrid('appendRow', {});
			  	  	}
				}
  	  			footerReCalc();
  	 		},
        	onDblClickRow: function(rowIndex, rowData){ //选中行
  	 			editRowIndex=rowIndex;
  	 			if(rowData.goodsid && rowData.goodsid!=""){
  	 				orderDetailEditDialog(rowData);
  	 			}else{
  	 				orderDetailAddDialog();
  	 			}
        	},
  	 		onRowContextMenu:function(e, rowIndex, rowData){
  	 			e.preventDefault();
  	 			var $contextMenu=$('#purchase-Button-tableMenu');
  	 			$contextMenu.menu('show', {
					left : e.pageX,
					top : e.pageY
				});
  	 			$returnOrdertable.datagrid('selectRow', rowIndex);
				editRowIndex=rowIndex;
                $contextMenu.menu('enableItem', '#purchase-tableMenu-itemEdit');
                $contextMenu.menu('enableItem', '#purchase-tableMenu-itemInsert');
                $contextMenu.menu('enableItem', '#purchase-tableMenu-itemDelete');
  	 		},
            onSortColumn:function(sort, order){
                return detailOnSortColumn(sort,order);
            }
  	  	}).datagrid("columnMoving");

  		//添加按钮事件
		$("#purchase-tableMenu-itemAdd").unbind("click").bind("click",function(){
			if($("#purchase-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			orderDetailAddDialog();			
		});
		
  		//编辑按钮事件
		$("#purchase-tableMenu-itemEdit").unbind("click").bind("click",function(){
			if($("#purchase-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			var data=$("#purchase-returnOrderAddPage-returnOrdertable").datagrid('getSelected');
			if(null!=data && data.goodsid !=null && data.goodsid!=""){
				orderDetailEditDialog(data);		
			}else{
				orderDetailAddDialog();	
			}
		});
        //删除按钮事件
		$("#purchase-tableMenu-itemDelete").unbind("click").bind("click",function(){
			if($("#purchase-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			var data=$("#purchase-returnOrderAddPage-returnOrdertable").datagrid('getSelected');
            var $returnOrdertable = $("#purchase-returnOrderAddPage-returnOrdertable");
			if(data!=null){
				$.messager.confirm("提示","是否要删除选中的行？", function(r){
					if (r){
                        var rowIndex =$("#purchase-returnOrderAddPage-returnOrdertable").datagrid('getRowIndex',data);
                        console.log(rowIndex);
                        $returnOrdertable.datagrid('updateRow',{
                            index:rowIndex,
                            row:{}
                        });
                        $returnOrdertable.datagrid('deleteRow', rowIndex);
                        var rowlen=$returnOrdertable.datagrid('getRows').length;
                        if(rowlen<15){
                            $returnOrdertable.datagrid('appendRow', {});
                        }
                        editRowIndex=undefined;
                        $returnOrdertable.datagrid('clearSelections');
                        footerReCalc();

					}
				});	
			}		
		});
		
		$("#purchase-returnOrderAddPage-buyuser").widget({
			name:'t_base_buy_supplier',
			col:'buyuserid',
			width:150,
    		async:false,
			singleSelect:true,
			onlyLeafCheck:false
		});
		
		/*
		$("#purchase-returnOrderAddPage-buydept").change(function(){
			var v = $(this).val();
			if(v!=null && v !=""){
				$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: v}, function(json){
	    			if(json.length>0){
	    				$("#purchase-returnOrderAddPage-buyuser").html("");
	    				$("#purchase-returnOrderAddPage-buyuser").append("<option value=''></option>");
	    				for(var i=0;i<json.length;i++){
	    					$("#purchase-returnOrderAddPage-buyuser").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    				}
	    			}	
	    		});
			}else{
				$("#purchase-returnOrderAddPage-buyuser").html("");
			}
		});
		*/
		$("#purchase-returnOrderAddPage-supplier").supplierWidget({ 
		});

		$("#purchase-returnOrderAddPage-storage").widget({ 
			name:'t_purchase_returnorder',
			col:'storageid',
			width:150,
			required:true,
			readonly:true,
			initValue:'${returnOrder.storageid}',
			singleSelect:true,
			onlyLeafCheck:true
		});
  	});
  </script>
  </body>
</html>
