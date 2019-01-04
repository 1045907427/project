<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售回单修改页面</title>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-receiptAddPage" action="sales/updateReceipt.do" method="post">
    		<input type="hidden" id="sales-addType-receiptAddPage" name="addType" />
	    	<div data-options="region:'north',border:false" style="height:110px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input id="sales-id-receiptAddPage" class="len150 easyui-validatebox" name="receipt.id" value="${receipt.id }" readonly="readonly" /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input id="sales-businessdate-receiptAddPage" type="text" class="len130 Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${receipt.businessdate }" name="receipt.businessdate"  /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
							<select id="sales-status-receiptAddPage" disabled="disabled" name="receipt.status" class="len136">
	    						<c:forEach items="${status }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == receipt.status}">
	    									<option value="${list.code }" selected="selected">${list.codename }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
	    					</select>
	    					<input id="sales-saleorderid-receiptAddPage" type="hidden" value="${receipt.saleorderid }"/>
							<input id="sales-status-hidden-receiptAddPage" type="hidden" value="${receipt.status}" />
						</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td colspan="3">
	    					<input type="text"  value="<c:out value="${receipt.customername }"></c:out>" style="width: 300px;" readonly="readonly"/><span id="sales-customer-showid-receiptAddPage" style="margin-left:5px;line-height:25px;">编号：${receipt.customerid }</span>
	    					<input type="hidden" id="sales-customer-receiptAddPage" name="receipt.customerid" value="${receipt.customerid }"/>
	    				</td>
                        <td>销售订单：</td>
                        <td><input class="len136" value="${receipt.saleorderid }" readonly="readonly" name="receipt.saleorderid"/></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">销售部门：</td>
	    				<td><input id="sales-salesDept-receiptAddPage" class="len150" value="<c:out value="${receipt.salesdeptname }"></c:out>" readonly="readonly" /></td>
                        <td  class="len80 left">客户单号：</td>
                        <td><input type="text" name="receipt.sourceid" value="<c:out value="${receipt.sourceid }"></c:out>" class="len130"  /></td>
                        <td class="len80 left">备注：</td>
                        <td><input type="text" name="receipt.remark" value="<c:out value="${receipt.remark }"></c:out>" class="len136" /></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="goodsjson" id="sales-goodsJson-receiptAddPage" />
	    		<input id="sales-saveaudit-receiptAddPage" type="hidden" name="saveaudit"/>
	    		<table id="sales-datagrid-receiptAddPage"></table>
	    	</div>
			<input type="hidden" id="sales-printtimes-receiptAddPage" value="${receipt.printtimes }"/>
			<input type="hidden" id="sales-printlimit-receiptAddPage" value="${printlimit }"/>
	    </form>
    </div>
    
    <div class="easyui-menu" id="sales-contextMenu-receiptAddPage" style="display: none;">
		<c:if test="${receipt.source != '1' }">
    	<div id="sales-addRow-receiptAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="sales-editRow-receiptAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="sales-removeRow-receiptAddPage" data-options="iconCls:'button-delete'">删除</div>
		</c:if>
		<security:authorize url="/sales/receiptBrandDiscountAddPage.do">
		<div id="sales-addRow-receiptAddBrandDiscountPage" data-options="iconCls:'button-add'">添加品牌折扣</div>
		</security:authorize>
		<security:authorize url="/sales/receiptDiscountAddPage.do">
			<div id="sales-addRow-receiptAddReceiptDiscountPage" data-options="iconCls:'button-add'">添加回单折扣</div>
		</security:authorize>
    </div>
    <div id="sales-dialog-receiptAddPage" class="easyui-dialog" data-options="closed:true"></div>

    <a href="javaScript:void(0);" id="sales-export-receiptPage" style="display: none"></a>
    <script type="text/javascript">
		var receipt_fieldupdateflag = false;
		<security:authorize url="/sales/receiptDetailTaxpriceNotaxpriceUpdate.do">
		receipt_fieldupdateflag = true;
		</security:authorize>
    	$(function(){
    		$("#sales-datagrid-receiptAddPage").datagrid({ //销售商品明细信息编辑
    			authority:wareListJson,
    			columns: wareListJson.common,
    			frozenColumns: wareListJson.frozen,
    			border: true,
				idField:"id",
    			fit: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: false,
    			checkOnSelect:true,
    			selectOnCheck:true,
    			data: JSON.parse('${goodsJson}'),
    			onLoadSuccess: function(){
    				var rows = $("#sales-datagrid-receiptAddPage").datagrid('getRows');
    				var leng = rows.length;
    				if(leng < 12){
    					for(var i=leng; i<12; i++){
    						$("#sales-datagrid-receiptAddPage").datagrid('appendRow',{});
    					}
    				}
    				else{
    					$("#sales-datagrid-receiptAddPage").datagrid('appendRow',{});
    				}
    				countTotal();
    			},
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
					if('${receipt.source}' == '1'){
    					if(rowData.goodsid == undefined){
	    				}
	    				else{
	    					if(rowData.isdiscount!='1' && rowData.isdiscount!='2'){
	    						//onClickCell(rowIndex, "receiptnum");
	    					}
	    				}
    				}else{
	    				if(rowData.goodsid == undefined){
	    					beginAddDetail();
	    				}
	    				else{
	    					beginEditDetail();
	    				}
    				}
    			},
    			onClickCell: function(index, field, value){
    				var row = $wareList.datagrid('getRows')[index];
    				if(row.isdiscount!='1' && row.isdiscount!='2'){
    					onClickCell(index, field);
    				}
    			}
    		}).datagrid('columnMoving');
    		$("#sales-addRow-receiptAddPage").click(function(){
    			beginAddDetail();
    		});
    		$("#sales-editRow-receiptAddPage").click(function(){
    			beginEditDetail();
    		});
    		$("#sales-removeRow-receiptAddPage").click(function(){
    			removeDetail();
    		});
			//添加品牌折扣
			$("#sales-addRow-receiptAddBrandDiscountPage").click(function(){
				beginAddBrandDiscountDetail();
			});
			//添加回单折扣
			$("#sales-addRow-receiptAddReceiptDiscountPage").click(function(){
				beginAddReceiptDiscountDetail();
			});
    		$("#sales-buttons-receiptPage").buttonWidget("setDataID", {id:'${receipt.id}', state:'${receipt.status}', type:'edit'});
    	});
    	var $wareList = $("#sales-datagrid-receiptAddPage"); //商品datagrid的div对象
    	var editIndex = undefined;  
    	var thisIndex = undefined;
    	var editfiled = null;
        function endEditing(field){
        	if('${receipt.source}' == '0'){
				return false;
		    }
            if (editIndex == undefined){return true}
            if(field == "taxprice" && receipt_fieldupdateflag){
            	if ($('#sales-datagrid-receiptAddPage').datagrid('validateRow', editIndex)){
	            	var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'taxprice'});
					var taxprice = $(ed.target).val();
					//$wareList.datagrid('getRows')[editIndex]['taxprice'] = taxprice;
	                $('#sales-datagrid-receiptAddPage').datagrid('endEdit', editIndex);
	                var row = $wareList.datagrid('getRows')[editIndex];
					row.receipttaxamount = Number(row.receiptnum)*Number(row.taxprice);
					row.boxprice = Number(row.goodsInfo.boxnum)*Number(row.taxprice);
					//应收未税单价 = 含税单价/(1+税率) 保留6位
					row.receiptnotaxprice = Number(row.taxprice)/Number(row.goodsInfo.taxrate);
					row.receiptnotaxamount = Number(row.receiptnum)*Number(row.receiptnotaxprice);

					$('#sales-datagrid-receiptAddPage').datagrid('updateRow',{index:editIndex, row:row});
					countTotal();
	                editIndex = undefined;  
	                return true;  
	            } else {  
	                return false;  
	            }  
            }else if(field == "boxprice" && receipt_fieldupdateflag){
            	if ($('#sales-datagrid-receiptAddPage').datagrid('validateRow', editIndex)){
	            	var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'boxprice'});
					var boxprice = $(ed.target).val();
					//$wareList.datagrid('getRows')[editIndex]['taxprice'] = taxprice;
	                $('#sales-datagrid-receiptAddPage').datagrid('endEdit', editIndex);
	                var row = $wareList.datagrid('getRows')[editIndex];
	                row.taxprice = Number(row.boxprice)/Number(row.goodsInfo.boxnum);
					row.receipttaxamount = Number(row.receiptnum)*Number(row.taxprice);
					//应收未税单价 = 含税单价/(1+税率)
					row.receiptnotaxprice = Number(row.taxprice)/Number(row.goodsInfo.taxrate);
					row.receiptnotaxamount = Number(row.receiptnum)*Number(row.receiptnotaxprice);

					$('#sales-datagrid-receiptAddPage').datagrid('updateRow',{index:editIndex, row:row});
					countTotal();
	                editIndex = undefined;  
	                return true;  
	            } else {  
	                return false;  
	            }  
            }else if(field == "receiptnum"){
            	<c:if test="${receiptAndRejectType=='2' }">
            	if ($('#sales-datagrid-receiptAddPage').datagrid('validateRow', editIndex)){
	            	var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'receiptnum'});
                    var row = $wareList.datagrid('getRows')[editIndex];
					var receiptnum = $(ed.target).val();
                    var unitnum = row.unitnum;
                    if(Number(receiptnum)>Number(unitnum)){
                        $(ed.target).val(unitnum);
                        receiptnum = unitnum;
                        row.receiptnum = unitnum;
                    }
					//$wareList.datagrid('getRows')[editIndex]['taxprice'] = taxprice;
	                $('#sales-datagrid-receiptAddPage').datagrid('endEdit', editIndex);
					row.receipttaxamount = Number(row.receiptnum)*Number(row.taxprice);
					row.receiptnotaxamount = Number(row.receiptnum)*Number(row.receiptnotaxprice);

					$('#sales-datagrid-receiptAddPage').datagrid('updateRow',{index:editIndex, row:row});
					countTotal();
	                editIndex = undefined;  
	                return true;  
	            } else {  
	                return false;  
	            } 
            	 </c:if>
            }else if(field == "receipttaxamount" && receipt_fieldupdateflag){
				if ($('#sales-datagrid-receiptAddPage').datagrid('validateRow', editIndex)){
					var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'receipttaxamount'});
					var receipttaxamount = $(ed.target).val();
					$('#sales-datagrid-receiptAddPage').datagrid('endEdit', editIndex);
					var row = $wareList.datagrid('getRows')[editIndex];
					row.taxprice = Number(row.receipttaxamount)/Number(row.receiptnum);
					row.boxprice = Number(row.goodsInfo.boxnum)*Number(row.taxprice);
					//应收未税单价 = 含税单价/(1+税率)
					row.receiptnotaxprice = Number(row.taxprice)/Number(row.goodsInfo.taxrate);
					row.receiptnotaxamount = Number(row.receiptnum)*Number(row.receiptnotaxprice);

					$('#sales-datagrid-receiptAddPage').datagrid('updateRow',{index:editIndex, row:row});
					countTotal();
					editIndex = undefined;
					return true;
				} else {
					return false;
				}
			}else if(field == "receiptnotaxprice" && receipt_fieldupdateflag){
				if ($('#sales-datagrid-receiptAddPage').datagrid('validateRow', editIndex)){
					var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'receiptnotaxprice'});
					var receiptnotaxprice = $(ed.target).val();
					$('#sales-datagrid-receiptAddPage').datagrid('endEdit', editIndex);
					var row = $wareList.datagrid('getRows')[editIndex];
					row.taxprice = Number(row.receiptnotaxprice)*Number(row.goodsInfo.taxrate);
					row.boxprice = Number(row.goodsInfo.boxnum)*Number(row.taxprice);
					row.receiptnotaxamount = Number(row.receiptnum)*Number(row.receiptnotaxprice);
					row.receipttaxamount = Number(row.receiptnum)*Number(row.taxprice);

					$('#sales-datagrid-receiptAddPage').datagrid('updateRow',{index:editIndex, row:row});
					countTotal();
					editIndex = undefined;
					return true;
				} else {
					return false;
				}
			}else if(field == "receiptnotaxamount" && receipt_fieldupdateflag){
				if ($('#sales-datagrid-receiptAddPage').datagrid('validateRow', editIndex)){
					var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'receiptnotaxamount'});
					var receiptnotaxamount = $(ed.target).val();
					$('#sales-datagrid-receiptAddPage').datagrid('endEdit', editIndex);
					var row = $wareList.datagrid('getRows')[editIndex];
					row.receiptnotaxprice = Number(row.receiptnotaxamount)/Number(row.receiptnum);
					row.taxprice = Number(row.receiptnotaxprice)*Number(row.goodsInfo.taxrate);
					row.boxprice = Number(row.goodsInfo.boxnum)*Number(row.taxprice);
					row.receipttaxamount = Number(row.receiptnum)*Number(row.taxprice);

					$('#sales-datagrid-receiptAddPage').datagrid('updateRow',{index:editIndex, row:row});
					countTotal();
					editIndex = undefined;
					return true;
				} else {
					return false;
				}
			}
        }
        function onClickCell(index, field){  
            if (endEditing(editfiled)){
    			var row = $wareList.datagrid('getRows')[index];
    			if(row.goodsid == undefined){
    				return false;
    			}
    			editfiled = field;
            	if(field == "taxprice" && receipt_fieldupdateflag){
	                $('#sales-datagrid-receiptAddPage').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
	                editIndex = index;  
	                thisIndex = index;
	                var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'taxprice'});
                    getNumberBoxObject(ed.target).focus();
                    getNumberBoxObject(ed.target).select();
                }else if(field == "boxprice" && receipt_fieldupdateflag){
	                $('#sales-datagrid-receiptAddPage').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
	                editIndex = index;  
	                thisIndex = index;
	                var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'boxprice'});
                    getNumberBoxObject(ed.target).focus();
                    getNumberBoxObject(ed.target).select();
                }else if(field == "receiptnum"){
                	<c:if test="${receiptAndRejectType=='2' }">
                	$('#sales-datagrid-receiptAddPage').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});  
 	                editIndex = index;  
 	                thisIndex = index;
 	                var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'receiptnum'});
                    getNumberBoxObject(ed.target).focus();
                    getNumberBoxObject(ed.target).select();
 	                </c:if>
                }else if(field == "receipttaxamount" && receipt_fieldupdateflag){
					$('#sales-datagrid-receiptAddPage').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
					editIndex = index;
					thisIndex = index;
					var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'receipttaxamount'});
					getNumberBoxObject(ed.target).focus();
					getNumberBoxObject(ed.target).select();
				}else if(field == "receiptnotaxprice" && receipt_fieldupdateflag){
					$('#sales-datagrid-receiptAddPage').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
					editIndex = index;
					thisIndex = index;
					var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'receiptnotaxprice'});
					getNumberBoxObject(ed.target).focus();
					getNumberBoxObject(ed.target).select();
				}else if(field == "receiptnotaxamount" && receipt_fieldupdateflag){
					$('#sales-datagrid-receiptAddPage').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
					editIndex = index;
					thisIndex = index;
					var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'receiptnotaxamount'});
					getNumberBoxObject(ed.target).focus();
					getNumberBoxObject(ed.target).select();
				}
            }  
        }
        <c:if test="${receipt.status!='2' }">
    		$("#sales-buttons-receiptPage").buttonWidget("disableButton", 'cancel-check');
    	</c:if>
    	<c:if test="${receipt.status=='2' }">
    		$("#sales-buttons-receiptPage").buttonWidget("enableButton", 'cancel-check');
    		$("#sales-buttons-receiptPage").buttonWidget("enableButton", 'relation-rejectbill');
    	</c:if>
        <c:choose>
            <c:when test="${ receipt.status=='3'&& receipt.isinvoice == '3'}">
                $("#sales-buttons-receiptPage").buttonWidget("enableButton", 'receipt-writeoff');
            </c:when>
            <c:otherwise>
                $("#sales-buttons-receiptPage").buttonWidget("disableButton", 'receipt-writeoff');
            </c:otherwise>
        </c:choose>
    	
    	$("#sales-parentid-receiptPage").val("${receipt.billno}");
    </script>
  </body>
</html>
