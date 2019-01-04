<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售回单新增页面</title>
  </head>
  <body>
  	<div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-receiptAddPage" action="sales/addReceipt.do" method="post">
    		<input type="hidden" id="sales-addType-receiptAddPage" name="addType" />
	    	<div data-options="region:'north',border:false" style="height:140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input id="sales-id-receiptAddPage" class="len130 easyui-validatebox" name="receipt.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len130" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${date }" name="receipt.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165"><select disabled="disabled" class="len136"><option>新增</option></select></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td>
	    					<input type="text" id="sales-customer-receiptAddPage" name="receipt.customerid" class="len136"/>
	    				</td>
	    				<td class="len80 left">销售部门：</td>
	    				<td>
	    					<select id="sales-salesDept-receiptAddPage" class="len136" name="receipt.salesdept">
	    						<c:forEach items="${deptList }" var="list">
    									<option value="${list.id }">${list.name }</option>
	    						</c:forEach>
	    					</select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户业务员：</td>
	    				<td>
	    					<select id="sales-salesMan-receiptAddPage" class="len136" name="receipt.salesuser">
	    					</select>
	    				</td>
	    				<td class="len80 left">结算方式：</td>
	    				<td>
	    					<select class="len136" name="receipt.settletype" id="sales-settletype-receiptAddPage">
	    						<c:forEach items="${settletype}" var="list">
								<option value="${list.id }">${list.name }</option>
	    						</c:forEach>
	    					</select>
	    				</td>
	    				<td class="len80 left">支付方式：</td>
	    				<td>
	    					<select class="len136" name="receipt.paytype" id="sales-paytype-receiptAddPage">
	    						<c:forEach items="${paytypeList}" var="list">
								<option value="${list.id }" >${list.name }</option>
	    						</c:forEach>
	    					</select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">来源类型：</td>
	    				<td>
	    					<select class="len130" name="receipt.source" disabled="disabled">
	    						<option value="0">无</option>
	    						<option value="1">发货单</option>
	    					</select>
	    				</td>
	    				<td class="len80 left">出库仓库：</td>
	    				<td>
	    					<select class="len136" name="receipt.storageid" id="sales-storage-receiptAddPage">
	    						<c:forEach items="${storageList}" var="list">
								<option value="${list.id }">${list.name }</option>
	    						</c:forEach>
	    					</select>
	    				</td>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="5"><input type="text" name="receipt.remark" class="len130" /></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="goodsjson" id="sales-goodsJson-receiptAddPage" />
	    		<table id="sales-datagrid-receiptAddPage"></table>
	    	</div>
	    </form>
    </div>
    <div class="easyui-menu" id="sales-contextMenu-receiptAddPage" style="display: none;">
    	<div id="sales-addRow-receiptAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="sales-editRow-receiptAddPage" data-options="iconCls:'button-edit'">编辑</div>
		<security:authorize url="/sales/receiptDiscountAddPage.do">
			<div id="sales-addRow-receiptAddDiscountPage" data-options="iconCls:'button-add'">添加商品折扣</div>
		</security:authorize>
		<security:authorize url="/sales/receiptBrandDiscountAddPage.do">
			<div id="sales-addRow-receiptAddBrandDiscountPage" data-options="iconCls:'button-add'">添加品牌折扣</div>
		</security:authorize>
    	<div id="sales-removeRow-receiptAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="sales-dialog-receiptAddPage" class="easyui-dialog" data-options="closed:true"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#sales-customer-receiptAddPage").customerWidget({ //客户参照窗口
    			name:'t_sales_receipt',
				col:'customerid',
    			singleSelect:true,
    			width:130,
    			required:true,
    			isopen:true,
    			onSelect:function(data){
    				$("#sales-salesDept-receiptAddPage").val(data.salesdeptid);
	    			$("#sales-settletype-receiptAddPage").val(data.settletype);
	    			$("#sales-paytype-receiptAddPage").val(data.paytype);
	    			$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: data.salesdeptid}, function(json){
	    				if(json.length>0){
	    					$("#sales-salesMan-receiptAddPage").html("");
	    					for(var i=0;i<json.length;i++){
	    						$("#sales-salesMan-receiptAddPage").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    					}
			    			$("#sales-salesMan-receiptAddPage").val(data.salesuserid);
	    				}	
	    			});
	    			$.getJSON('basefiles/getContacterBy.do', {type:"1", id:data.id}, function(json){
	    				if(json.length>0){
	    					$("#sales-handler-receiptAddPage").html("");
	    					for(var i=0;i<json.length;i++){
	    						$("#sales-handler-receiptAddPage").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    					}
	    					$("#sales-handler-receiptAddPage").val(data.contact);
	    				}
	    			});
    			},
    			onClear:function(){
    				
    			}
    		});
    		$("#sales-salesDept-receiptAddPage").change(function(){
    			var v = $(this).val();
    			$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: v}, function(json){
	    			if(json.length>0){
	    				$("#sales-salesMan-receiptAddPage").html("");
	    				$("#sales-salesMan-receiptAddPage").append("<option value=''></option>");
	    				for(var i=0;i<json.length;i++){
	    					$("#sales-salesMan-receiptAddPage").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    				}
	    			}	
	    		});
    		});
    		$("#sales-addRow-receiptAddPage").click(function(){
    			beginAddDetail();
    		});
    		$("#sales-editRow-receiptAddPage").click(function(){
    			beginEditDetail();
    		});
    		$("#sales-removeRow-receiptAddPage").click(function(){
    			removeDetail();
    		});
    		$("#sales-buttons-receiptPage").buttonWidget("initButtonType", 'add');
    		$("#sales-datagrid-receiptAddPage").datagrid({ //销售商品明细信息编辑
    			authority:wareListJson,
    			columns: wareListJson.common,
    			frozenColumns: wareListJson.frozen,
    			border: true,
    			fit: true,
    			fitColumns:true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: true,
                onSortColumn:function(sort, order){
                    var rows = $("#sales-datagrid-receiptAddPage").datagrid("getRows");
                    var dataArr = [];
                    for(var i=0;i<rows.length;i++){
                        if(rows[i].goodsid!=null && rows[i].goodsid!=""){
                            dataArr.push(rows[i]);
                        }
                    }
                    dataArr.sort(function(a,b){
                        if($.isNumeric(a[sort])){
                            if(order=="asc"){
                                return Number(a[sort])>Number(b[sort])?1:-1
                            }else{
                                return Number(a[sort])<Number(b[sort])?1:-1
                            }
                        }else{
                            if(order=="asc"){
                                return a[sort]>b[sort]?1:-1
                            }else{
                                return a[sort]<b[sort]?1:-1
                            }
                        }
                    });
                    $("#sales-datagrid-receiptAddPage").datagrid("loadData",dataArr);
                    return false;
                },
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$wareList.datagrid('selectRow', rowIndex);
                    $("#sales-contextMenu-receiptAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onDblClickRow: function(rowIndex, rowData){
    				if(rowData.goodsid == undefined){
    					beginAddDetail();
    				}
    				else{
    					beginEditDetail();
    				}
    			}
    		}).datagrid("loadData", {'total':12,'rows':[{},{},{},{},{},{},{},{},{},{},{},{} ],'footer':[{goodsid:'合计'}]}).datagrid('columnMoving');
    	});
    	var $wareList = $("#sales-datagrid-receiptAddPage"); //商品datagrid的div对象
    </script>
  </body>
</html>
