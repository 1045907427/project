<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售退货通知单修改页面</title>
  </head> 
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-check-rejectBillAddPage" action="sales/updateRejectBillCheck.do" method="post">
    		<input type="hidden" id="sales-addType-rejectBillAddPage" name="addType" />
	    	<div data-options="region:'north',border:false" style="height:140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len150" id="sales-id-rejectBillAddPage" name="rejectBill.id" value="${rejectBill.id }" readonly="readonly" /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len150 Wdate" value="${rejectBill.businessdate }" name="rejectBill.businessdate" readonly="readonly"/></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select id="sales-status-rejectBillAddPage" disabled="disabled" name="rejectBill.status" class="len150">
	    						<c:forEach items="${status }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == rejectBill.status}">
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
	    				<td class="len80 left">客户：</td>
	    				<td colspan="3">
	    					<input type="text" id="sales-customer-rejectBillAddPage" name="rejectBill.customerid" value="${rejectBill.customerid }" text="<c:out value="${rejectBill.customername }"></c:out>" style="width: 300px;" readonly="readonly"/>
	    					<span id="sales-customer-showid-dispatchBillAddPage" style="margin-left:5px;line-height:25px;">编号：${rejectBill.customerid }</span>
	    					<input type="hidden" id="sales-customer-showid-hidden-dispatchBillAddPage" value="${rejectBill.customerid }"/>
	    				</td>
	    				<td class="len80 left">司机：</td>
	    				<td>
	    					<input class="len150" value="<c:out value="${rejectBill.drivername }"></c:out>" readonly="readonly"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">销售部门：</td>
	    				<td>
	    					<input type="text" id="sales-salesDept-rejectBillAddPage" class="len150" name="rejectBill.salesdept" value="${rejectBill.salesdeptname }" readonly="readonly"/>
	    				</td>
	    				<td class="len80 left">客户业务员：</td>
	    				<td>
	    					<input type="text" class="len150" name="rejectBill.salesuser" id="sales-salesMan-rejectBillAddPage" value="${rejectBill.salesusername}" readonly="readonly"/>
	    				</td>
	    				<td class="len80 left">退货类型：</td>
	    				<td>
	    					<select class="len150" name="rejectBill.billtype" disabled="disabled">
	    						<option value="1" <c:if test="${rejectBill.billtype=='1'}">selected="selected"</c:if>>直退</option>
	    						<option value="2" <c:if test="${rejectBill.billtype=='2'}">selected="selected"</c:if>>售后退货</option>
	    					</select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">入库仓库：</td>
	    				<td><input id="sales-storage-rejectBillAddPage" class="len150" value="<c:out value="${rejectBill.storagename }"></c:out>" readonly="readonly" /></td>
						<td class="len80 left">销售类型：</td>
						<td>
							<select class="len150" name="rejectBill.salestype" disabled="disabled">
								<c:forEach items="${salestypeList }" var="salestype">
									<option value="${salestype.code }" <c:if test="${salestype.code eq rejectBill.salestype }">selected="selected"</c:if>><c:out value="${salestype.codename }"/></option>
								</c:forEach>
							</select>
						</td>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="1"><input type="text" name="rejectBill.remark" value="<c:out value="${rejectBill.remark }"></c:out>" style="width: 150px;" readonly="readonly"/></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="goodsjson" id="sales-goodsJson-rejectBillAddPage" />
	    		<table id="sales-datagrid-rejectBillAddPage"></table>
	    	</div>
			<input type="hidden" id="sales-isinvoice-rejectBillAddPage" value="${rejectBill.isinvoice}" />
			<input type="hidden" id="sales-ysprinttimes-rejectBillAddPage" value="${rejectBill.ysprinttimes}" />
	    </form>
    </div>
    <div class="easyui-menu" id="sales-contextMenu-rejectBillAddPage" style="display: none;">
        <div id="sales-history-price-rejectBillAddPage" data-options="iconCls:'button-view'">查看历史销售价</div>
    </div>
    <div id="sales-dialog-rejectBillAddPage" ></div>
    <script type="text/javascript">
    	$(function(){
    		$("#sales-datagrid-rejectBillAddPage").datagrid({ //销售商品明细信息编辑
    			authority:wareListJson,
    			columns: wareListJson.common,
    			frozenColumns: wareListJson.frozen,
    			border: true,
    			fit: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: true,
    			data: JSON.parse('${goodsJson}'),
    			onLoadSuccess: function(){
    				var rows = $("#sales-datagrid-rejectBillAddPage").datagrid('getRows');
    				var leng = rows.length;
    				if(leng > 0){
    					$("#sales-parentid-rejectBill").val(rows[0].billno);
    				}
    				if(leng < 12){
    					for(var i=leng; i<12; i++){
    						$("#sales-datagrid-rejectBillAddPage").datagrid('appendRow',{});
    					}
    				}
    				else{
    					$("#sales-datagrid-rejectBillAddPage").datagrid('appendRow',{});
    				}
    				countTotal();
    			},
                onRowContextMenu: function(e, rowIndex, rowData){
                    e.preventDefault();
                    $(this).datagrid('selectRow', rowIndex);
                    $("#sales-contextMenu-rejectBillAddPage").menu('show', {
                        left:e.pageX,
                        top:e.pageY
                    });
                }
    		}).datagrid('columnMoving');
    		$("#sales-form-check-rejectBillAddPage").form({
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
		  		      	$.messager.alert("提醒","操作成功");
		  		      	//刷新列表
		    	  		tabsWindowURL("/sales/rejectBillCheckListPage.do").$("#sales-datagrid-rejectBillCheckListPage").datagrid('reload');
		    	  		//关闭当前标签页
		    	  		top.closeNowTab();
		  		      	//$("#sales-panel-rejectBill").panel('refresh', 'sales/rejectBillCheckEditPage.do?id=${rejectBill.id }');
		  		    }
		  		    else{
		  		       	$.messager.alert("提醒","操作失败。"+json.msg);
		  		    }
		  		}
		  	});
    		$("#sales-customer-rejectBillAddPage").customerWidget({ //客户参照窗口
    			required:true,
    			onSelect:function(data){
    				$("#sales-customer-showid-dispatchBillAddPage").text("编号："+ data.id);
    				$("#sales-salesDept-rejectBillAddPage").val(data.salesdeptid);
    				$("#sales-salesDept-rejectBillAddPage-hidden").val(data.salesdeptid);
	    			$("#sales-settletype-rejectBillAddPage").val(data.settletype);
	    			$("#sales-paytype-rejectBillAddPage").val(data.paytype);
	    			$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: data.salesdeptid}, function(json){
	    				if(json.length>0){
	    					$("#sales-salesMan-rejectBillAddPage").html("");
	    					$("#sales-salesMan-rejectBillAddPage").append("<option value=''></option>");
	    					for(var i=0;i<json.length;i++){
	    						$("#sales-salesMan-rejectBillAddPage").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    					}
			    			$("#sales-salesMan-rejectBillAddPage").val(data.salesuserid);
			    			$("#sales-salesMan-rejectBillAddPage-hidden").val(data.salesuserid);
	    				}	
	    			});
    				$("input[name='rejectBill.remark']").focus();
    			},
    			onClear:function(){
    				$("#sales-customer-showid-dispatchBillAddPage").text("");
			    	$("#sales-salesDept-rejectBillAddPage").val('');
			    	$("#sales-salesMan-rejectBillAddPage").val('');
	    			$("#sales-settletype-rejectBillAddPage").val("");
	    			$("#sales-paytype-rejectBillAddPage").val("");
    			}
    		});
    		$("#sales-salesDept-rejectBillAddPage").change(function(){
   				var v = $(this).val();
    			$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: v}, function(json){
	    			if(json.length>0){
	    				$("#sales-salesMan-rejectBillAddPage").html("");
	    				$("#sales-salesMan-rejectBillAddPage").append("<option value=''></option>");
	    				for(var i=0;i<json.length;i++){
	    					$("#sales-salesMan-rejectBillAddPage").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    				}
	    			}	
	    		});
    		});
            $("#sales-history-price-rejectBillAddPage").click(function(){
                showHistoryGoodsPrice();
            });
    		$("#sales-buttons-rejectBill").buttonWidget("setDataID", {id:'${rejectBill.id}', state:'${rejectBill.status}', type:'edit'});
    	});
    	$("#sales-buttons-rejectBill").buttonWidget("setDataID", {id:'${rejectBill.id}', state:'${rejectBill.status}', type:'view'});
		$("#sales-buttons-rejectBill").buttonWidget("disableButton","button-split");
		$("#sales-buttons-rejectBill").buttonWidget("disableButton","button-savecheck");
		<c:if test="${rejectBill.isinvoice=='3' }">
			$("#sales-buttons-rejectBill").buttonWidget("enableButton","button-checkcancel");
		</c:if>

		$("#storage-buttons-saleOutPage").buttonWidget("disableButton","button-printview-rejectBillCheck");
		$("#storage-buttons-saleOutPage").buttonWidget("disableButton","button-print-rejectBillCheck");
		<c:if test="${rejectBill.isinvoice=='1' ||rejectBill.isinvoice=='2' || rejectBill.isinvoice=='3' ||rejectBill.isinvoice=='4' ||rejectBill.isinvoice=='5' }">
			$("#storage-buttons-saleOutPage").buttonWidget("enableButton","button-printview-rejectBillCheck");
			$("#storage-buttons-saleOutPage").buttonWidget("enableButton","button-print-rejectBillCheck");
		</c:if>
    </script>
  </body>
</html>
