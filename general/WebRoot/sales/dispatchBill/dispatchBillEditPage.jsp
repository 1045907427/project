<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售发货通知单修改</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-dispatchBillAddPage" action="sales/updateDispatchBill.do" method="post">
    		<input type="hidden" id="sales-addType-dispatchBillAddPage" name="addType" />
    		<input type="hidden" name="dispatchBill.oldid" value="${bill.id }" />
	    	<div data-options="region:'north',border:false" style="height:110px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len150" name="dispatchBill.id" value="${bill.id }" readonly="readonly" /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input id="sales-businessdate-dispatchBillAddPage" type="text" class="len150 Wdate" value="${bill.businessdate }" name="dispatchBill.businessdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select id="sales-status-dispatchBillAddPage" class="len150" name="dispatchBill.status" disabled="disabled">
	    						<c:forEach items="${statusList }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == bill.status}">
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
	    				<td colspan="3"><input type="text" id="sales-customer-dispatchBillAddPage" name="dispatchBill.customerid" style="width: 300px;" value="${bill.customerid }" text="<c:out value="${bill.customername }"></c:out>" readonly="readonly" /><span id="sales-customer-showid-dispatchBillAddPage" style="margin-left:5px;line-height:25px;">编号：${bill.customerid }</span></td>
                        <c:if test="${bill.source == '2' }">
                            <td class="len80 left">发货仓库：</td>
                            <td><input id="sales-storage-dispatchBillAddPage" class="len150" value="${bill.storageid }" readonly="readonly"/></td>
                        </c:if>
                        <c:if test="${bill.source != '2' }">
                            <td class="len80 left">发货仓库：</td>
                            <td><input id="sales-storage-dispatchBillAddPage" class="len150" name="dispatchBill.storageid" value="${bill.storageid }"/></td>
                        </c:if>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">销售部门：</td>
	    				<td>
	    					<input type="text" class="len150" value="${bill.salesdeptname }" readonly="readonly"/>
	    					<input id="sales-salesDept-dispatchBillAddPage-hidden" type="hidden" name="dispatchBill.salesdept" value="${bill.salesdept}"/>
	    				</td>
	    				<td class="len80 left">客户业务员：</td>
	    				<td>
	    					<input type="text" class="len150" value="${bill.salesusername }" readonly="readonly"/>
	    					<input id="sales-salesMan-dispatchBillAddPage-hidden" type="hidden" name="dispatchBill.salesuser" value="${bill.salesuser}"/>
	    				</td>
                        <td class="len80 left">备注：</td>
                        <td><input type="text" name="dispatchBill.remark" value="<c:out value="${bill.remark }"></c:out>" class="len150" autocomplete="off"/></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="goodsjson" id="sales-goodsJson-dispatchBillAddPage" />
	    		<input id="sales-saveaudit-dispatchBillAddPage" type="hidden" name="saveaudit"/>
	    		<table id="sales-datagrid-dispatchBillAddPage"></table>
	    		<input type="hidden" id="sales-billno-dispatchBillAddPage" value="${bill.billno }"/>
	    		<input type="hidden" id="sales-printtimes-dispatchBillAddPage" value="${bill.printtimes }"/>
	    		<input type="hidden" id="sales-phprinttimes-dispatchBillAddPage" value="${bill.phprinttimes }"/>
	    		<input type="hidden" id="sales-printlimit-dispatchBillAddPage" value="${printlimit }"/>
	    		<input type="hidden" id="sales-fHPrintAfterSaleOutAudit-dispatchBillAddPage" value="${fHPrintAfterSaleOutAudit }"/>
	    	</div>
    	</form>
    </div>
    <c:choose>
    	<c:when test="${bill.source == 0}">
		    <div class="easyui-menu" id="sales-contextMenu-dispatchBillAddPage" style="display: none;">
		    	<security:authorize url="/sales/dispatchBillAddDiscountPage.do">
		    	<div id="sales-addRow-dispatchBillAddDiscountPage" data-options="iconCls:'button-add'">添加商品折扣</div>
		    	</security:authorize>
		    	<security:authorize url="/sales/dispatchBillAddBrandDiscountPage">
		    	<div id="sales-addRow-dispatchBillAddBrandDiscountPage" data-options="iconCls:'button-add'">添加品牌折扣</div>
		    	</security:authorize>
		    	<div id="sales-addRow-dispatchBillAddPage" data-options="iconCls:'button-add'">添加</div>
		    	<div id="sales-editRow-dispatchBillAddPage" data-options="iconCls:'button-edit'">修改</div>
		    	<div id="sales-removeRow-dispatchBillAddPage" data-options="iconCls:'button-delete'">删除</div>
		    </div>
    	</c:when>
    	<c:otherwise>
		    <div class="easyui-menu" id="sales-contextMenu-dispatchBillAddPage" style="display: none;">
		    	<security:authorize url="/sales/dispatchBillAddDiscountPage.do">
		    	<div id="sales-addRow-dispatchBillAddDiscountPage" data-options="iconCls:'button-add'">添加商品折扣</div>
		    	</security:authorize>
		    	<security:authorize url="/sales/dispatchBillAddBrandDiscountPage">
		    	<div id="sales-addRow-dispatchBillAddBrandDiscountPage" data-options="iconCls:'button-add'">添加品牌折扣</div>
		    	</security:authorize>
    			<div id="sales-modifyRow-dispatchBillAddPage" data-options="iconCls:'button-edit'">修改</div>
    			<div id="sales-removeRow-dispatchBillAddPage" data-options="iconCls:'button-delete'">删除</div>
		    </div>
    	</c:otherwise>
    </c:choose>
	<div id="sales-dialog-dispatchBillAddPage" class="easyui-dialog" data-options="closed:true"></div>
    <script type="text/javascript">
        $("#sales-buttons-dispatchBill").buttonWidget("disableButton",'storage-oweorder-button');
    	var storageEdit = false;
    	<security:authorize url="/sales/dispatchBillEditStorage.do">
    	storageEdit=true;
    	</security:authorize>
    	$(function(){
    		$("#sales-datagrid-dispatchBillAddPage").datagrid({ //销售商品明细信息编辑
    			authority:wareListJson,
    			columns: wareListJson.common,
    			frozenColumns: wareListJson.frozen,
    			border: true,
    			fit:true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: true,
    			data: JSON.parse('${goodsJson}'),
    			onSortColumn:function(sort, order){
    				var rows = $("#sales-datagrid-dispatchBillAddPage").datagrid("getRows");
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
    				$("#sales-datagrid-dispatchBillAddPage").datagrid("loadData",dataArr);
    				return false;
    			},
    			onLoadSuccess: function(){
    				var rows = $("#sales-datagrid-dispatchBillAddPage").datagrid('getRows');
    				var leng = rows.length;
    				if(leng > 0){
    					$("#sales-parentid-dispatchBill").val(rows[0].billno);
    				}
    				if(leng < 12){
    					for(var i=leng; i<12; i++){
    						$("#sales-datagrid-dispatchBillAddPage").datagrid('appendRow',{});
    					}
    				}
    				else{
    					$("#sales-datagrid-dispatchBillAddPage").datagrid('appendRow',{});
    				}
    				countTotal();
    			},
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$wareList.datagrid('selectRow', rowIndex);
                    $("#sales-contextMenu-dispatchBillAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onDblClickRow: function(rowIndex, rowData){
    				if(rowData.goodsid == undefined && rowData.isdiscount==null){
    				<c:if test="${bill.source=='0' }">
    					beginAddDetail();
    				</c:if>
    				}
    				else{
    					if(rowData.isdiscount=='1'){
    						<security:authorize url="/sales/dispatchBillAddDiscountPage.do">
    						beginEditDetailDiscount();
    				    	</security:authorize>
    					}else if(rowData.isdiscount=='2'){
    						<security:authorize url="/sales/dispatchBillAddBrandDiscountPage">
    						beginEditDetailBrandDiscount();
    				    	</security:authorize>
    					}else{
    						beginEditDetail(rowData);
    					}
    				}
    				
    			}
    		}).datagrid('columnMoving');
    		$("#sales-customer-dispatchBillAddPage").customerWidget({ //客户参照窗口
    			name:'t_sales_dispatchbill',
				col:'customerid',
    			width:300,
    			required:true
    		});
            $("#sales-storage-dispatchBillAddPage").widget({
                referwid:'RL_T_BASE_STORAGE_INFO',
                width:150,
                <c:if test="${isOrderStorageSelect=='1'}">
                required:true,
                </c:if>
                singleSelect:true,
                readonly:!storageEdit,
                onSelect:function(data){
                    var rows = $("#sales-datagrid-dispatchBillAddPage").datagrid('getRows');
                    var count = 0;
                    var showBatchMsg = false;
                    for(var i=0;i<rows.length; i++){
                        var row = rows[i];
                        if((rows[i].goodsid!=null && rows[i].goodsid!='') || rows[i].isdiscount!=null){
                            var rowIndex = $wareList.datagrid("getRowIndex", row);
                            if(row.summarybatchid==null || row.summarybatchid==""){
                                row.storageid = data.id;
                                row.storagename = data.name;
                                $("#sales-datagrid-dispatchBillAddPage").datagrid('updateRow', {index: rowIndex, row: row});
                            }else{
                                if(row.storageid!=data.id){
                                    showBatchMsg = true;
                                    row.summarybatchid="";
                                    row.batchno="";
                                    row.produceddate="";
                                    row.deadline="";
                                    row.storageid = data.id;
                                    row.storagename = data.name;
                                    $("#sales-datagrid-dispatchBillAddPage").datagrid('updateRow', {index: rowIndex, row: row});
                                }
                            }

                        }
                    }
                    if(showBatchMsg){
                        $.messager.alert("提醒","商品指定批次的仓库与发货仓库不一致，自动清除批次信息。");
                    }
                },
                onClear:function(){
                    var rows = $("#sales-datagrid-dispatchBillAddPage").datagrid('getRows');
                    for(var i=0;i<rows.length; i++){
                        var row = rows[i];
                        if((rows[i].goodsid!=null && rows[i].goodsid!='') || rows[i].isdiscount!=null) {
                            var rowIndex = $wareList.datagrid("getRowIndex", row);
                            row.storageid = "";
                            row.storagename = "";
                            if(row.summarybatchid==null || row.summarybatchid=="") {
                                $("#sales-datagrid-dispatchBillAddPage").datagrid('updateRow', {index: rowIndex, row: row});
                            }
                        }
                    }
                }
            });

    		$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: "${bill.salesdept}"}, function(json){
	    		if(json.length>0){
	    			$("#sales-salesMan-dispatchBillAddPage").html("");
	    			$("#sales-salesMan-dispatchBillAddPage").append("<option value=''></option>");
	    			for(var i=0;i<json.length;i++){
	    				$("#sales-salesMan-dispatchBillAddPage").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    			}
			    	$("#sales-salesMan-dispatchBillAddPage").val("${bill.salesuser}");
	    		}	
	    	});
    		$("#sales-salesMan-dispatchBillAddPage").change(function(){
    			$("#sales-salesMan-dispatchBillAddPage-hidden").val($(this).val());
    		});
    		$("#sales-settletype-dispatchBillAddPage").change(function(){
    			$("#sales-settletype-dispatchBillAddPage-hidden").val($(this).val());
    		});
    		$("#sales-paytype-dispatchBillAddPage").change(function(){
    			$("#sales-paytype-dispatchBillAddPage-hidden").val($(this).val());
    		});
    		
    		//折扣添加页面
    		$("#sales-addRow-dispatchBillAddDiscountPage").click(function(){
    			beginAddDiscountDetail();
    		});
    		//添加品牌折扣
    		$("#sales-addRow-dispatchBillAddBrandDiscountPage").click(function(){
    			beginAddBrandDiscountDetail();
    		});
    		$("#sales-addRow-dispatchBillAddPage").click(function(){
    			beginAddDetail();
    		});
    		$("#sales-editRow-dispatchBillAddPage").click(function(){
    			beginEditDetail();
    		});
    		$("#sales-modifyRow-dispatchBillAddPage").click(function(){
	    		var row = $wareList.datagrid('getSelected');
	    		if(row.isdiscount=='1'){
	    			<security:authorize url="/sales/dispatchBillAddDiscountPage.do">
					beginEditDetailDiscount();
			    	</security:authorize>
				}else if(row.isdiscount=='2'){
					<security:authorize url="/sales/dispatchBillAddBrandDiscountPage">
					beginEditDetailBrandDiscount();
			    	</security:authorize>
				}else{
					beginEditDetail(row);
				}
    		});
    		$("#sales-removeRow-dispatchBillAddPage").click(function(){
    			removeDetail();
    		});
    		$("#sales-buttons-dispatchBill").buttonWidget("setDataID", {id:'${bill.id}', state:'${bill.status}', type:'edit'});
    		$("#sales-buttons-dispatchBill").buttonWidget("disableButton","button-print");
			$("#sales-buttons-dispatchBill").buttonWidget("disableButton","button-preview");
    		
    	});
    	var $wareList = $("#sales-datagrid-dispatchBillAddPage"); //商品datagrid的div对象
    	<c:if test="${bill.status!='2' }">
    		$("#sales-buttons-dispatchBill").buttonWidget("disableButton", 'storage-deploy-button');
    	</c:if>
    	<c:if test="${bill.status=='2' }">
    		$("#sales-buttons-dispatchBill").buttonWidget("enableButton", 'storage-deploy-button');
    	</c:if>
    	
		$("#sales-buttons-dispatchBill").buttonWidget("disableMenuItem","button-printview-orderblank");
    	$("#sales-buttons-dispatchBill").buttonWidget("disableMenuItem","button-print-orderblank");
		$("#sales-buttons-dispatchBill").buttonWidget("disableMenuItem","button-printview-DispatchBill");
		$("#sales-buttons-dispatchBill").buttonWidget("disableMenuItem","button-print-DispatchBill");
		$("#sales-buttons-dispatchBill").buttonWidget("disableMenuItem","button-printview-DeliveryOrder");
		$("#sales-buttons-dispatchBill").buttonWidget("disableMenuItem","button-print-DeliveryOrder");

		<c:if test="${(bill.status=='3' or bill.status=='4') and (bill.phprinttimes =='0'  or '0'==printlimit) }">
			$("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem","button-print-orderblank");
		</c:if>
		<c:choose>
			<c:when test="${fHPrintAfterSaleOutAudit=='1' }">
				<c:if test="${bill.status=='4' and (bill.printtimes == '0' or '0'==printlimit) }">
					$("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem","button-print-DeliveryOrder");
					$("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem","button-print-DispatchBill");
				</c:if>
			</c:when>
			<c:otherwise>
	    		<c:if test="${(bill.status=='3' or bill.status=='4') and (bill.printtimes == '0' or '0'==printlimit) }">
					$("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem","button-print-DeliveryOrder");
					$("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem","button-print-DispatchBill");
				</c:if>
			</c:otherwise>
		</c:choose>
		
		<c:if test="${(bill.status=='3' or bill.status=='4') }">
			$("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem","button-printview-DeliveryOrder");
			$("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem","button-printview-DispatchBill");
			$("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem","button-printview-orderblank");
		</c:if>
    </script>
   
  </body>
</html>
