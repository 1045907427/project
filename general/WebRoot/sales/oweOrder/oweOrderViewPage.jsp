<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>要货申请单查看页面</title>
  </head>
  <body>
	<div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-oweOrderViewPage" action="sales/updateOweOrder.do" method="post">
	    	<div data-options="region:'north',border:false" style="height:110px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input id="sales-id-oweOrderViewPage"  type="text" class="len150" readonly="readonly" value="${oweOrder.id }" name="id" /></td>
	    				<td class="len80 right">业务日期：</td>
	    				<td class="len165"><input type="text" class="len130" readonly="readonly" value="${oweOrder.businessdate }" /></td>
	    				<td class="len80 right">状态：</td>
	    				<td class="len165">
	    					<select id="sales-status-oweOrderViewPage" disabled="disabled" class="len136">
	    						<option value="0" <c:if test="${oweOrder.status == 0 }">selected="selected"</c:if>>未生成订单</option>
	    						<option value="1" <c:if test="${oweOrder.status == 1 }">selected="selected"</c:if>>已生成订单</option>
	    						<option value="4" <c:if test="${oweOrder.status == 4 }">selected="selected"</c:if>>关闭</option>
	    					</select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td colspan="3"><input type="text" id="sales-customer-oweOrderViewPage" readonly="readonly" text="${oweOrder.customername }" value="${oweOrder.customerid }" style="width: 300px;"/><span id="sales-customer-showid-orderAddPage" style="margin-left:5px;line-height:25px;">编号：${oweOrder.customerid }</span></td>
                        <td class="len80 right">销售部门：</td>
                        <td><select id="sales-salesDept-oweOrderViewPage" class="len136" disabled="disabled">
                            <option value=""></option>
                            <c:forEach items="${salesDept}" var="list">
                                <c:choose>
                                    <c:when test="${list.id == oweOrder.salesdept}">
                                        <option value="${list.id }" selected="selected">${list.name }</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${list.id }">${list.name }</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        </td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户业务员：</td>
	    				<td><input type="text" id="sales-salesMan-oweOrderViewPage" class="len150" readonly="readonly"  value="${oweOrder.salesusername }"/></td>
                        <td class="len80 right">备注：</td>
                        <td colspan="3"><input type="text" readonly="readonly" value="<c:out value="${oweOrder.remark }"></c:out>" style="width:400px;" /></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="sales-datagrid-oweOrderViewPage"></table>
				<input type="hidden" name="goodsjson" id="sales-goodsJson-oweOrderViewPage"/>
	    	</div>
			<input id="sales-saveaudit-oweOrderViewPage"  type="hidden"  value="save" name="saveaudit" />
	    </form>
    </div>
	<div class="easyui-menu" id="sales-contextMenu-oweOrderViewPage" style="display: none;">
		<div id="sales-removeRow-oweOrderViewPage" data-options="iconCls:'button-delete'">删除</div>
	</div>
    <script type="text/javascript">
		var id=$("#sales-id-oweOrderViewPage").val();
        var page_type = '${type}';
	    if(page_type == "edit"){
	    	$("#sales-buttons-oweOrderPage").buttonWidget("setDataID",{id:'${oweOrder.id}',state:'2',type:'edit'});
	    }
	    if(page_type == "view"){
	    	$("#sales-buttons-oweOrderPage").buttonWidget("setDataID",{id:'${oweOrder.id}',state:'3',type:'view'});
	    	$("#sales-buttons-oweOrderPage").buttonWidget("disableButton",'button-close-oweOrderPage');
            $("#sales-buttons-oweOrderPage").buttonWidget("disableButton",'button-audit-oweOrderPage');
	    }
	    if(page_type == "close"){
	    	$("#sales-buttons-oweOrderPage").buttonWidget("setDataID",{id:'${oweOrder.id}',state:'3',type:'view'});
	    	$("#sales-buttons-oweOrderPage").buttonWidget("disableButton",'button-close-oweOrderPage');
            $("#sales-buttons-oweOrderPage").buttonWidget("disableButton",'button-audit-oweOrderPage');
	    	$("#sales-buttons-oweOrderPage").buttonWidget("disableButton",'button-delete');



	    }
    	$(function(){
    		$("#sales-datagrid-oweOrderViewPage").datagrid({
    			columns: wareListJson,
    			border: true,
    			fit:true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: true,
    			data: JSON.parse('${goodsJson}'),
    			onLoadSuccess: function(){
    				var rows = $("#sales-datagrid-oweOrderViewPage").datagrid('getRows');
    				var leng = rows.length;
    				if(leng < 12){
    					for(var i=leng; i<12; i++){
    						$("#sales-datagrid-oweOrderViewPage").datagrid('appendRow',{});
    					}
    				}
    				countTotal();
    			},
				onRowContextMenu: function(e, rowIndex, rowData){
					e.preventDefault();
					$("#sales-datagrid-oweOrderViewPage").datagrid('selectRow', rowIndex);
					$("#sales-contextMenu-oweOrderViewPage").menu('show', {
						left:e.pageX,
						top:e.pageY
					});
				},
                onClickCell: function(index, field, value){
                    onClickCell(index, field);
                },
    		}).datagrid('columnMoving');
    		
    		$("#sales-customer-oweOrderViewPage").customerWidget({ //客户参照窗口
    			name:'t_sales_oweOrder',
				col:'customerid',
    			singleSelect:true,
    			width:300
    		});

			$("#sales-form-oweOrderViewPage").form({
				onSubmit: function(){
					loading("提交中..");
				},
				success:function(data){
					loaded();
					var json = $.parseJSON(data);
					if('save'==json.savetype){
                        if(json.flag==true){
                            if(json.closeflag==true){
                                $.messager.alert("提醒",json.msg);
                                $("#sales-panel-oweOrderPage").panel('refresh', 'sales/oweOrderViewPage.do?id='+ id);
                            }else{
                                $.messager.alert("提醒","保存成功。");
                            }

                        }else{
                            $.messager.alert("提醒","保存失败。"+json.msg);
                        }
					}else if('saveaudit'==json.savetype){
                        if(json.flag){
                            $.messager.alert("提醒",json.msg);
                            $("#sales-panel-oweOrderPage").panel('refresh', 'sales/oweOrderViewPage.do?id='+ id);
                        }
                        else{
                            $.messager.alert("提醒", "生成订单失败<br>"+json.msg);
                        }
					}





				}
			});
			$("#sales-removeRow-oweOrderViewPage").click(function(){
				var row = $("#sales-datagrid-oweOrderViewPage").datagrid('getSelected');
				if(row == null){
					$.messager.alert("提醒", "请选择一条记录");
					return false;
				}
				$.messager.confirm("提醒","确定删除该商品明细?",function(r){
					if(r){
						var rowIndex = $("#sales-datagrid-oweOrderViewPage").datagrid('getRowIndex', row);
						$("#sales-datagrid-oweOrderViewPage").datagrid('deleteRow', rowIndex);
						countTotal();
					}
				});
			});
    	});


        //结束行编辑
        var $datagrid1 = $('#sales-datagrid-oweOrderViewPage');
        var editIndex = undefined;
        var editfiled = null;
        function endEditing(field){
            console.log(111111111111)
            if (editIndex == undefined){
                return true;
            }
            var row = $datagrid1.datagrid('getRows')[editIndex];
            if(row != undefined){
                var ordernum = row.ordernum;
                if(field == "ordernum"){
                    var ed = $datagrid1.datagrid('getEditor', {index:editIndex,field:'ordernum'});
                    if(null != ed){
                        ordernum = getNumberBoxObject(ed.target).val();
                        $datagrid1.datagrid('endEdit', editIndex);
                        var unitnum = row.unitnum;
                        var useable = row.useable;
                        row.ordernum = ordernum;
                        if(parseInt(ordernum)>parseInt(unitnum)){
                            row.ordernum=unitnum;
						}
                        if(parseInt(ordernum)>parseInt(useable)){
                            row.ordernum=useable;
                        }

                        $datagrid1.datagrid('updateRow',{index:editIndex, row:row});
                    }
                }
                editIndex = undefined;
                return true;
            }else{
                editIndex = undefined;
                return false;
            }
        }
        function onClickCell(index, field){
            if (endEditing(editfiled)){
                editfiled = field;
                if(field == "ordernum"){
                    editIndex = index;
                    var row = $datagrid1.datagrid('getRows')[editIndex];
                    if(row != undefined && row.goodsid!= undefined) {
                        $datagrid1.datagrid('selectRow', index).datagrid('editCell', {index: index, field: field});

                        var ed = $datagrid1.datagrid('getEditor', {index: editIndex, field: 'ordernum'});

                        if (null != ed) {
                            getNumberBoxObject(ed.target).focus();
                            getNumberBoxObject(ed.target).select();
                        }
                    }
                }
            }
        }

    </script>
  </body>
</html>
