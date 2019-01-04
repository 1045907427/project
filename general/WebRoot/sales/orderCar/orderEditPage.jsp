<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>零售订单查看页面</title>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-orderAddPage" action="sales/updateOrderCar.do" method="post">
    		<input type="hidden" value="${order.id }" name="ordercar.id" />
	    	<div data-options="region:'north',border:false" style="height:135px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input id="sales-billid-orderAddPage" type="text" class="len150" readonly="readonly" value="${order.id }" /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" id="sales-businessdate-orderAddPage" class="len150" readonly="readonly" value="${order.businessdate }" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len50">
	    					<select id="sales-status-orderAddPage" disabled="disabled" class="len150">
	    						<c:forEach items="${statusList }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == order.status}">
	    									<option value="${list.code }" selected="selected">${list.codename }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
	    						<option value="9" <c:if test="${order.status=='9'}">selected="selected"</c:if>>作废</option>
	    					</select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td colspan="3"><input type="text" id="sales-customer-orderAddPage" readonly="readonly" name="ordercar.customerid" text="<c:out value="${order.customername }"></c:out>" value="${order.customerid }" style="width: 300px;"/>
							<span id="sales-customer-showid-orderAddPage" style="margin-left:5px;line-height:25px;">
                                编号：<a href="javascript:showCustomer('${order.customerid }')">${order.customerid }</a></span>
							<input type="hidden" id="sales-customer-hidden-orderAddPage" value="${saleorder.customerid }"/>
                        <td class="len80 left">仓库：</td>
                        <td><input name="ordercar.storageid" id="sales-storageid-orderAddPage" value="${order.storageid }" /></td>
                    </tr>
	    			<tr>
	    				<td class="len80 left">销售部门：</td>
	    				<td><select id="sales-salesDept-orderAddPage" class="len150" disabled="disabled">
	    						<option value=""></option>
	    						<c:forEach items="${salesDept}" var="list">
	    							<c:choose>
	    								<c:when test="${list.id == order.salesdept}">
	    									<option value="${list.id }" selected="selected">${list.name }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.id }">${list.name }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
	    					</select>
						</td>
	    				<td class="len80 left">客户业务员：</td>
	    				<td><select id="sales-salesMan-orderAddPage" class="len150" disabled="disabled"></select></td>
						<td class="len80 left">车销人员：</td>
						<td><input id="sales-caruser-orderAddPage" class="len150" name="ordercar.caruser" value="${order.caruser}"/></td>
	    			</tr>
					<tr>
						<td class="len80 left">备注：</td>
						<td colspan="5"><input type="text" name="ordercar.remark" value="<c:out value="${order.remark }"></c:out>" style="width:680px;" /></td>
					</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="goodsjson" id="sales-goodsJson-orderAddPage" value="<c:out value="${goodsJson }" />" />
	    		<table id="sales-datagrid-orderAddPage"></table>
	    	</div>
	    </form>
    </div>
    <div class="easyui-menu" id="sales-contextMenu-orderCarAddPage" style="display: none;">
    	<div id="sales-addRow-orderCarAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="sales-editRow-orderCarAddPage" data-options="iconCls:'button-edit'">修改</div>
    	<div id="sales-removeRow-orderCarAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
	<div id="sales-dialog-orderCarAddPage"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#sales-datagrid-orderAddPage").datagrid({ //销售商品行编辑
    			authority:wareListJson,
    			columns: wareListJson.common,
    			frozenColumns: wareListJson.frozen,
    			border: true,
    			fit:true,
    			fitColumns:false,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: true,
    			data: JSON.parse('${goodsJson}'),
                onLoadSuccess: function(data){
                    if(data.rows.length<12){
                        var j = 12-data.rows.length;
                        for(var i=0;i<j;i++){
                            $(this).datagrid('appendRow',{});
                        }
                    }else{
                        $(this).datagrid('appendRow',{});
                    }
                    countTotal();
                },
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$wareList.datagrid('selectRow', rowIndex);
                    $("#sales-contextMenu-orderCarAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onDblClickRow: function(rowIndex, rowData){
    				if(rowData.goodsid == undefined && rowData.isdiscount==null){
    					beginAddDetail();
    				}
    				else{
    					if(rowData.isdiscount=='1'){
    						beginEditDetailDiscount();
    					}else if(rowData.isdiscount=='2'){
    						beginEditDetailBrandDiscount();
    					}else{
    						beginEditDetail(rowData);
    					}
    				}
    				
    			}
    		}).datagrid('columnMoving');
    		//折扣添加页面
    		$("#sales-addRow-orderCarAddDiscountPage").click(function(){
    			beginAddDiscountDetail();
    		});
    		//添加品牌折扣
    		$("#sales-addRow-orderCarAddBrandDiscountPage").click(function(){
    			beginAddBrandDiscountDetail();
    		});
    		$("#sales-addRow-orderCarAddPage").click(function(){
    			beginAddDetail();
    		});
    		$("#sales-editRow-orderCarAddPage").click(function(){
    			beginEditDetail();
    		});
    		$("#sales-modifyRow-orderCarAddPage").click(function(){
	    		var row = $wareList.datagrid('getSelected');
	    		if(row.isdiscount=='1'){
					beginEditDetailDiscount();
				}else if(row.isdiscount=='2'){
					beginEditDetailBrandDiscount();
				}else{
					beginEditDetail(row);
				}
    		});
    		$("#sales-removeRow-orderCarAddPage").click(function(){
    			removeDetail();
    		});
    		$("#sales-form-orderAddPage").form({
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
		  		   	if(flag==false){
		  		   		return false;
		  		   	}  
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		  	if(json.lock == true){
		  		  		$.messager.alert("提醒","其他用户正在修改该数据，无法修改");
		  		  		return false;
		  		  	}
		  		    if(json.flag==true){
		  		      	$.messager.alert("提醒","保存成功");
		  		      	$("#sales-backid-orderPage").val(json.backid);
		  		      	$("#sales-panel-orderPage").panel('refresh', 'sales/orderCarEditPage.do?id='+json.backid);
		  		    }
		  		    else{
		  		       	$.messager.alert("提醒","保存失败");
		  		    }
		  		}
		  	});
    		$("#sales-customer-orderAddPage").customerWidget({ //客户参照窗口
    			name:'t_sales_order_car',
				col:'customerid',
    			singleSelect:true,
    			isopen:true,
    			onSelect:function(data){
    				$("#sales-customer-showid-orderAddPage").text("编号："+ data.id);
    			},
    			onClear:function(){
    				$("#sales-customer-showid-orderAddPage").text("");
    			}
    		});
    		$("#sales-storageid-orderAddPage").widget({ //仓库参照窗口
    			name: "t_sales_order_car",
    			col: "storageid",
    			singleSelect:true,
    			required:true,
    			width:150
    		});
			$("#sales-caruser-orderAddPage").widget({
				referwid:'RL_T_BASE_PERSONNEL_CARUSER',
				width:150,
				required:true,
				singleSelect:true
			});
            $.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: ''}, function(json){
                if(json.length>0){
                    $("#sales-salesMan-orderAddPage").html("");
                    $("#sales-salesMan-orderAddPage").append("<option value=''></option>");
                    for(var i=0;i<json.length;i++){
                        $("#sales-salesMan-orderAddPage").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
                    }
                    $("#sales-salesMan-orderAddPage").val("${order.salesuser }");
                }
            });
	    	$.getJSON('basefiles/getContacterBy.do', {type:"1", id:"${order.customerid}"}, function(json){
	    		if(json.length>0){
	    			$("#sales-handler-orderAddPage").html("");
	    			$("#sales-handler-orderAddPage").append("<option value=''></option>");
	    			for(var i=0;i<json.length;i++){
	    				$("#sales-handler-orderAddPage").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    			}
	    			$("#sales-handler-orderAddPage").val("${order.handlerid }");
	    		}
	    	});
    		$("#sales-buttons-orderPage").buttonWidget("setDataID", {id:'${order.id}', state:'${order.status}', type:'edit'});
    	});
    	var $wareList = $("#sales-datagrid-orderAddPage"); //商品datagrid的div对象
   		<c:if test="${order.status=='9'}">
   			$("#sales-buttons-orderPage").buttonWidget("disableButton", 'button-audit');
   		</c:if>
    	<c:if test="${order.status!='2'}">
   			$("#sales-buttons-orderPage").buttonWidget("disableButton", 'button-auditDemand');
   		</c:if>
    </script>
  </body>
</html>
