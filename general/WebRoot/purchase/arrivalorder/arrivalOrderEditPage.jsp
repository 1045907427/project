<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
  <div class="easyui-panel" title="" data-options="fit:true,border:false">
	  <div class="easyui-layout" data-options="fit:true">
	  	<form action="purchase/arrivalorder/editArrivalOrder.do" id="purchase-form-arrivalOrderAddPage" method="post">
	  		<input type="hidden" id="purchase-arrivalOrderAddPage-addType" name="addType"/>
	  		<input type="hidden" id="purchase-arrivalOrderAddPage-saveaudit" name="saveaudit"/>
	  		<input type="hidden" id="purchase-arrivalOrderAddPage-referbillno" value="${ arrivalOrder.billno }"/>
	  		<div data-options="region:'north',border:false" style="height:110px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="width:80px;">编号：</td>
						<td style="width:165px;"><input type="text" style="width:150px;" value="${arrivalOrder.id }" readonly="readonly" /></td>
						<td style="width:80px;">业务日期：</td>
						<td style="width:165px;"><input type="text" id="purchase-arrivalOrderAddPage-businessdate" name="arrivalOrder.businessdate" value="${arrivalOrder.businessdate }" style="width:130px;" readonly="readonly" class="easyui-validatebox" required="true" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
						<td style="width:80px;">状态：</td>
				    	<td style="width:165px;">			    		
	    					<select id="purchase-arrivalOrderAddPage-status" disabled="disabled" class="len136">
	    						<c:forEach items="${statusList }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == arrivalOrder.status}">
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
						<td style="">供应商：</td>
						<td colspan="3"><input type="text" id="purchase-arrivalOrderAddPage-supplier" style="width:300px;" value="${arrivalOrder.supplierid }" text="<c:out value="${arrivalOrder.suppliername}"></c:out>" readonly="readonly"/>
							<span id="purchase-supplier-showid-arrivalOrderAddPage" style="margin-left:5px;line-height:25px;">编号：${arrivalOrder.supplierid }</span>
						</td>
                        <td style="">入库仓库：</td>
                        <td><input type="text" id="purchase-arrivalOrderAddPage-storage" style="width:135px;" /></td>
					</tr>
					<tr>
						<td style="">采购部门：</td>
				    	<td>
				    		<select id="purchase-arrivalOrderAddPage-buydept" style="width:150px;" disabled="disabled">
					    		<option value=""></option>
					    		<c:forEach items="${buyDept}" var="list">
	    							<c:choose>
	    								<c:when test="${list.id == arrivalOrder.buydeptid}">
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
							<input type="text" id="purchase-arrivalOrderAddPage-buyuser" name="arrivalOrder.buyuserid" style="width:130px;" readonly="readonly" value="${arrivalOrder.buyuserid }" />
						</td>
                        <td>备注：</td>
                        <td>
                            <input type="text" style="width:135px;"  id="purchase-arrivalOrderAddPage-remark" name="arrivalOrder.remark" value="<c:out value="${arrivalOrder.remark}"></c:out>"/>
                        </td>
					</tr>
				</table>
	  		</div>
	  		<div data-options="region:'center',border:false">
	  			<table id="purchase-arrivalOrderAddPage-arrivalOrdertable"></table>
				<input type="hidden" id="purchase-arrivalOrderAddPage-arrivalOrderDetails" name="arrivalOrderDetails" />
	  		</div>
	  		<input type="hidden" name="arrivalOrder.id" value="${arrivalOrder.id }" />
	  		<input type="hidden" id="purchase-arrivalOrderAddPage-isrefer" value="${arrivalOrder.isrefer }" />	
	  	</form> 
	    <input type="hidden" id="purchase-arrivalOrderAddPage-printtimes" value="${arrivalOrder.printtimes}" />		
  		<input type="hidden" id="purchase-arrivalOrderAddPage-printlimit" value="${printlimit }"/>
	  </div>
  </div>
  <div id="purchase-Button-tableMenu" class="easyui-menu" style="width:120px;display: none;">
  		<c:if test="${arrivalOrder.source !='1'}">
        	<div id="purchase-tableMenu-itemAdd" iconCls="button-add">添加</div>
        	<div id="purchase-tableMenu-itemEdit" iconCls="button-edit">编辑</div>
        	<div id="purchase-tableMenu-itemDelete" iconCls="button-delete">删除</div>
        </c:if>
	  <security:authorize url="/purchase/arrivalorder/receiptDiscountAddPage.do">
		  <div id="purchase-arrivalOrderAddPage-addRow" data-options="iconCls:'button-add'">添加进货折扣</div>
	  </security:authorize>
  </div>
  <script type="text/javascript">
  	var editRowIndex = undefined;
  	function getAddRowIndex(){
  		var $potable=$("#purchase-arrivalOrderAddPage-arrivalOrdertable");
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
  	function orderSourceDetailEditDialog(initdata){
  		$('<div id="purchase-arrivalOrderAddPage-dialog-DetailOper-content"></div>').appendTo('#purchase-arrivalOrderAddPage-dialog-DetailOper');
  		var $DetailOper=$("#purchase-arrivalOrderAddPage-dialog-DetailOper-content");
		$DetailOper.dialog({
			title:'商品信息修改(按ESC退出)',
		    width: 600,  
		    height: 440,
		    closed: true,  
		    cache: false, 
		    modal: true,
		    resizable:true,
		    href:"purchase/arrivalorder/arrivalOrderSourceDetailEditPage.do",  
		    onLoad:function(){
			    if(initdata!=null && initdata.goodsid!=null && initdata.goodsid!=""){
				    if($("#purchase-form-arrivalOrderSourceDetailEditPage").size()>0){
					    if(initdata.goodsInfo){
					    	$("#purchase-form-arrivalOrderSourceDetailEditPage").form('load',initdata.goodsInfo);
					    	$("#purchase-arrivalOrderDetail-boxnum").val(formatterBigNumNoLen(initdata.goodsInfo.boxnum));
					    }

					    $("#purchase-form-arrivalOrderSourceDetailEditPage").form('load',initdata);

			  	  		$("#purchase-arrivalOrderDetail-span-auxunitname").html(initdata.auxunitname);
			  	  		$("#purchase-arrivalOrderDetail-span-unitname").html(initdata.unitname);

				    }
			    }

			    $("#purchase-arrivalOrderDetail-taxprice").focus();
			    $("#purchase-arrivalOrderDetail-taxprice").select();

				formaterNumSubZeroAndDot();

				$("#purchase-form-arrivalOrderSourceDetailEditPage").form('validate');
		    },
		    onClose:function(){
	            $DetailOper.dialog("destroy");
	        }
		});
		$DetailOper.dialog("open");
  	}
  	$(document).ready(function(){
		//updateflag为true表示分摊过，不能修改金额
		var updateflag=${updateflag};
		$("#button-relateMenu").menu('enableItem', '#purchase-change');
		$("#button-relateMenu").menu('disableItem', '#purchase-view');
		$("#button-relateMenu").menu('disableItem', '#purchase-cancaldispense');
		if(updateflag){
			$("#button-relateMenu").menu('enableItem', '#purchase-view');
			$("#button-relateMenu").menu('enableItem', '#purchase-cancaldispense');
		}

  		$("#purchase-buttons-arrivalOrderPage").buttonWidget("setDataID",  {id:'${arrivalOrder.id}',state:'${arrivalOrder.status}',type:'edit'});

  		$("#purchase-buttons-arrivalOrderPage").buttonWidget("disableButton","button-print");
  		$("#purchase-buttons-arrivalOrderPage").buttonWidget("disableButton","button-preview");

  		<c:if test="${arrivalOrder.isrefer == '1'}">
			$("#purchase-buttons-arrivalOrderPage").buttonWidget("disableButton","button-delete"); 	
			$("#purchase-buttons-arrivalOrderPage").buttonWidget("disableButton","button-oppaudit");	
  		</c:if>
  	  	var $arrivalOrdertable=$("#purchase-arrivalOrderAddPage-arrivalOrdertable");
  	  	$arrivalOrdertable.datagrid({
  	  		authority:tableColJson,
	 		frozenColumns: tableColJson.frozen,
			columns:tableColJson.common,
	  	  	fit:true,
	  	  	striped:true,
	 		method:'post',
	 		rownumbers:true,
	 		idField:'id',
	 		singleSelect:true,
	 		showFooter:true,
  	 		data: JSON.parse('${goodsDataList}'),
  	 		onLoadSuccess:function(){
		  		var dataRows=$arrivalOrdertable.datagrid('getRows');
		  		var rowlen=dataRows.length;
		  	  	if(rowlen<12){
			  	  	for(var i=rowlen;i<12;i++){
			  	  		$arrivalOrdertable.datagrid('appendRow', {});
			  	  	}
				}
  	 			$arrivalOrdertable.datagrid('reloadFooter',[
					{goodsid: '合计', amount: '0',taxprice:'0',notaxprice:'0',notaxamount:'0',taxamount:'0',tax:'0'}
				]);
  	 			footerReCalc();
  	 		},
        	onDblClickRow: function(rowIndex, rowData){ //选中行
				if(updateflag){
					return;
				}
  	 			editRowIndex=rowIndex;
  	 			if(rowData.goodsid && rowData.goodsid!=""){
  	 				<c:choose>
  	 					<c:when test="${arrivalOrder.source =='0'}">
  	  	 					orderDetailEditDialog(rowData);
  	  	 				</c:when>
  	 					<c:otherwise>
  	 						orderSourceDetailEditDialog(rowData);
  	 					</c:otherwise>
  	 				</c:choose>
  	 			}else{
  	 				<c:if test="${arrivalOrder.source =='0'}">
	 					orderDetailAddDialog();
  	 				</c:if>
  	 			}
        	},
  	 		onRowContextMenu:function(e, rowIndex, rowData){
				if(updateflag){
					return;
				}
          		<c:if test="${arrivalOrder.source =='0'}">
  	 			e.preventDefault();
  	 			var $contextMenu=$('#purchase-Button-tableMenu');
  	 			$contextMenu.menu('show', {
					left : e.pageX,
					top : e.pageY
				});
  	 			$arrivalOrdertable.datagrid('selectRow', rowIndex);
				editRowIndex=rowIndex;
                $contextMenu.menu('enableItem', '#purchase-tableMenu-itemDelete');
                </c:if>

				<c:if test="${arrivalOrder.source =='1'}">
				<security:authorize url="/purchase/arrivalorder/receiptDiscountAddPage.do">
					e.preventDefault();
					var $contextMenu=$('#purchase-Button-tableMenu');
					$contextMenu.menu('show', {
						left : e.pageX,
						top : e.pageY
					});
					$arrivalOrdertable.datagrid('selectRow', rowIndex);
					editRowIndex=rowIndex;
					$contextMenu.menu('enableItem', '#purchase-tableMenu-itemDelete');
				</security:authorize>
				</c:if>
  	 		},
            onSortColumn:function(sort, order){
                return detailOnSortColumn(sort,order);
            }
  	  	}).datagrid("columnMoving");

  	  	<c:if test="${arrivalOrder.source !='1'}">
  		//添加按钮事件
		$("#purchase-tableMenu-itemAdd").unbind("click").bind("click",function(){
			if($("#purchase-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			orderDetailAddDialog();			
		});
		</c:if>
  	  	//编辑按钮事件
		$("#purchase-tableMenu-itemEdit").unbind("click").bind("click",function(){
			if($("#purchase-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			var data=$("#purchase-arrivalOrderAddPage-arrivalOrdertable").datagrid('getSelected');
			orderDetailEditDialog(data);			
		});
		<c:if test="${arrivalOrder.source !='1'}">
		$("#purchase-tableMenu-itemDelete").unbind("click").bind("click",function(){
			if($("#purchase-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			var dataRow=$arrivalOrdertable.datagrid('getSelected');
			if(dataRow!=null){
				$.messager.confirm("提示","是否要删除选中的行？", function(r){
					if (r){
	        			if(dataRow!=null){
							var rowIndex =$arrivalOrdertable.datagrid('getRowIndex',dataRow);
							$arrivalOrdertable.datagrid('updateRow',{
								index:rowIndex,
								row:{}
							});
							$arrivalOrdertable.datagrid('deleteRow', rowIndex);
							var rowlen=$arrivalOrdertable.datagrid('getRows').length; 
							if(rowlen<15){
								$arrivalOrdertable.datagrid('appendRow', {});
							}
							editRowIndex=undefined;
							
							$arrivalOrdertable.datagrid('clearSelections');
							footerReCalc();
	        			}
					}
				});	
			}		
		});
		</c:if>
		$("#purchase-arrivalOrderAddPage-buyuser").widget({
			name:'t_base_buy_supplier',
			col:'buyuserid',
			width:130,
    		async:false,
			singleSelect:true,
			onlyLeafCheck:false
		});
		$("#purchase-arrivalOrderAddPage-buydept").change(function(){
			var v = $(this).val();
			if(v!=null && v !=""){
				$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: v}, function(json){
	    			if(json.length>0){
	    				$("#purchase-arrivalOrderAddPage-buyuser").html("");
	    				$("#purchase-arrivalOrderAddPage-buyuser").append("<option value=''></option>");
	    				for(var i=0;i<json.length;i++){
	    					$("#purchase-arrivalOrderAddPage-buyuser").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    				}
	    			}	
	    		});
			}else{
				$("#purchase-arrivalOrderAddPage-buyuser").html("");
			}
		});
		
		$("#purchase-arrivalOrderAddPage-supplier").supplierWidget({ });	


		$("#purchase-arrivalOrderAddPage-storage").widget({ 
			name:'t_purchase_arrivalorder',
			col:'storageid',
			width:135,
      		<c:if test="${arrivalOrder.source == '1' && !empty(arrivalOrder.storageid)}">readonly:true,</c:if>
			initValue:'${arrivalOrder.storageid}',
			singleSelect:true,
			onlyLeafCheck:true
		});

		//添加进货折扣
		$("#purchase-arrivalOrderAddPage-addRow").click(function(){
			beginAddArrivalOrderDiscountDetail();
		});
		
  	});
  </script>
  </body>
</html>
