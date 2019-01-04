<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>采购发票</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="account-form-purchaseInvoiceAdd" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height: 140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len130 easyui-validatebox" id="account-purchaseInvoice-id" name="purchaseInvoice.id" value="${purchaseInvoice.id }" readonly="readonly"/></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" id="account-purchaseInvoice-businessdate" class="len130" value="${purchaseInvoice.businessdate }" name="purchaseInvoice.businessdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len150">
	    					<select disabled="disabled" class="len130">
    						<c:forEach items="${statusList }" var="list">
    							<c:choose>
    								<c:when test="${list.code == purchaseInvoice.status}">
    									<option value="${list.code }" selected="selected">${list.codename }</option>
    								</c:when>
    								<c:otherwise>
    									<option value="${list.code }">${list.codename }</option>
    								</c:otherwise>
    							</c:choose>
    						</c:forEach>
    						</select>
    						<input type="hidden" id="account-purchaseInvoice-status" name="purchaseInvoice.status" value="${purchaseInvoice.status }" readonly="readonly"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">供应商:</td>
	    				<td colspan="3">
	    					<input type="text" id="account-purchaseInvoice-supplierid" name="purchaseInvoice.supplierid" style="width:395px" value="${purchaseInvoice.supplierid }" text="<c:out value="${purchaseInvoice.suppliername }"></c:out>" readonly="readonly"/>
	    				</td>
                        <td>应付金额:</td>
                        <td>
                            <input type="text" id="account-purchaseInvoice-invoiceamount" style="width: 130px;" value="${purchaseInvoice.invoiceamount }" readonly="readonly"/>
                        </td>
	    				<input type="hidden" id="account-purchaseInvoice-sourcetype" name="purchaseInvoice.sourcetype" value="${purchaseInvoice.sourcetype }"/>
	    				<input type="hidden" id="account-purchaseInvoice-sourceid" name="purchaseInvoice.sourceid" value="${purchaseInvoice.sourceid }"/>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">发票类型:</td>
	    				<td>
	    					<input type="text" id="account-purchaseInvoice-invoicetype" name="purchaseInvoice.invoicetype" class="len150" value="${purchaseInvoice.invoicetype }"/>
	    				</td>
	    				<td class="len80 left">发票号:</td>
	    				<td>
	    					<input type="text" id="account-purchaseInvoice-invoiceno" name="purchaseInvoice.invoiceno" class="len130" value="${purchaseInvoice.invoiceno }"/>
	    				</td>
	    				<td class="len80 left">发票代码:</td>
	    				<td>
	    					<input type="text" name="purchaseInvoice.invoicecode" class="len130" value="${purchaseInvoice.invoicecode }"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">采购部门:</td>
	    				<td>
	    					<input type="text" id="account-purchaseInvoice-buydept" name="purchaseInvoice.buydept" class="len150" value="${purchaseInvoice.buydept }" readonly="readonly"/>
	    				</td>
	    				<td class="len80 left">采购员:</td>
	    				<td>
	    					<input type="text" id="account-purchaseInvoice-buyuser" name="purchaseInvoice.buyuser" class="len130" value="${purchaseInvoice.buyuser }" readonly="readonly"/>
	    				</td>
	    				<td class="len80 left">备注：</td>
	    				<td style="text-align: left">
	    					<input type="text" name="purchaseInvoice.remark" style="width: 130px;" value="<c:out value="${purchaseInvoice.remark }"></c:out>" />
	    				</td>
	    			</tr>
	    			<tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="account-datagrid-group-purchaseInvoiceAddPage"></table>
	    	</div>
            <div class="easyui-menu" id="account-contextMenu-purchaseInvoiceEditPage">
                <div id="account-editRow-purchaseInvoiceEditPage" data-options="iconCls:'button-edit'">修改</div>
                <div id="account-removeRow-purchaseInvoiceEditPage" data-options="iconCls:'button-delete'">删除</div>
            </div>
            	<input type="hidden" id="account-printtimes-purchaseInvoice"  value="${purchaseInvoice.printtimes}"/>
	    	    <input type="hidden" id="account-purchaseInvoice-purchaseInvoiceDetail" name="detailJson" />
	    </form>
    </div>
    <div id="account-dialog-purchaseInvoiceAddPage"></div>
    <div id="account-dialog-goodsdetail-purchaseInvoiceAddPage"></div>
    <script type="text/javascript">
    	$(function(){
            purchaseInvoice_detail_editmap = {};
    		$("#account-form-purchaseInvoiceAdd").form({
			    onSubmit: function(){
                    var updateRows = getTotalUpdateDetailRows();

					$("#account-purchaseInvoice-purchaseInvoiceDetail").val(JSON.stringify(updateRows));
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	loading("提交中..");
			    },
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	var json = $.parseJSON(data);
			    	if(json.flag){
                        purchaseInvoice_detail_editmap = null;

			    		$.messager.alert("提醒","保存成功");

			    		$("#account-panel-purchaseInvoicePage").panel({
							href:"account/payable/showPurchaseInvoiceEditPage.do?id="+json.id,
							title:'',
						    cache:false,
						    maximized:true,
						    border:false
						});
			    	}else{
			    		$.messager.alert("提醒","保存失败!");
			    	}
			    }  
			}); 
			$("#account-datagrid-group-purchaseInvoiceAddPage").datagrid({ //采购进货单明细信息编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			fit:true,
    			singleSelect: true,
    			data:JSON.parse('${detailList}'),
                onRowContextMenu: function(e, rowIndex, rowData){
                    e.preventDefault();
                    if(rowData.isedit == ""){
                        $("#account-contextMenu-purchaseInvoiceEditPage").menu('show', {
                            left:e.pageX,
                            top:e.pageY
                        });
						if(rowData.sourcetype == "3") {
							$("#account-contextMenu-purchaseInvoiceEditPage").menu('disableItem',
									$("#account-editRow-purchaseInvoiceEditPage"));
						}else{
							$("#account-contextMenu-purchaseInvoiceEditPage").menu('enableItem',
									$("#account-editRow-purchaseInvoiceEditPage"));
						}
                    }
                },
                rowStyler: function(index,row){
                    if (row.isedit == "1"){
                        return 'background-color:#FFF7CE;'; // return inline style
                    }
                },
    			onDblClickRow:function(rowIndex, rowData){
                    showPurchaseInvoiceDetailListByGoodsid();
    			},
    			onLoadSuccess:function(data){
    				countTotal("account-datagrid-group-purchaseInvoiceAddPage");
    			}
    		}).datagrid('columnMoving'); 
    		$("#account-purchaseInvoice-supplierid").widget({
    			name:'t_account_purchase_invoice',
	    		width:395,
				col:'supplierid',
				singleSelect:true,
				required:true
    		});
    		$("#account-purchaseInvoice-buydept").widget({
    			name:'t_account_purchase_invoice',
	    		width:130,
				col:'buydept',
				singleSelect:true
    		});
    		$("#account-purchaseInvoice-buyuser").widget({
    			name:'t_account_purchase_invoice',
	    		width:130,
				col:'buyuser',
				singleSelect:true
    		}); 
    		$("#account-purchaseInvoice-invoicetype").widget({
    			name:'t_account_purchase_invoice',
	    		width:130,
				col:'invoicetype',
				singleSelect:true
    		}); 
    		$("#account-purchaseInvoice-settletype").widget({
    			name:'t_account_purchase_invoice',
	    		width:130,
				col:'settletype',
				singleSelect:true
    		});
    		$("#account-purchaseInvoice-paytype").widget({
    			name:'t_account_purchase_invoice',
	    		width:130,
				col:'paytype',
				singleSelect:true
    		});	
    		$("#account-purchaseInvoice-invoiceamount").numberbox({
    			precision:2
    		});
			$("#account-contextMenu-purchaseInvoiceEditPage").menu({
				onClick:function(item){
					if(item.id == "account-removeRow-purchaseInvoiceEditPage"){
						removePushRow();
					}else if(item.id == "account-editRow-purchaseInvoiceEditPage"){
						editPushRow();
					}
				}
			});
    	});

    	//控制按钮状态
    	$("#account-buttons-purchaseInvoicePage").buttonWidget("setDataID",{id:'${purchaseInvoice.id}',state:'${purchaseInvoice.status}',type:'edit'});
    	$("#account-hidden-billid").val("${purchaseInvoice.id}");
    	setTimeout(function () {$("#account-purchaseInvoice-invoiceno").focus(); }, 1);
    	<c:if test="${purchaseInvoice.status=='2' || purchaseInvoice.status=='4' || purchaseInvoice.status=='1'}">
    		$("#account-buttons-purchaseInvoicePage").buttonWidget("disableButton", 'button-cancel');
    	</c:if>
    	<c:if test="${purchaseInvoice.status=='3' }">
    		$("#account-buttons-purchaseInvoicePage").buttonWidget("enableButton", 'button-cancel');
            $("#account-buttons-purchaseInvoicePage").buttonWidget("disableButton", 'button-uncancel');
    	</c:if>
        <c:choose>
            <c:when test="${purchaseInvoice.status =='3' || purchaseInvoice.status =='4'}">
                $("#account-buttons-purchaseInvoicePage").buttonWidget("disableButton", 'purchasepush-button');
            </c:when>
            <c:otherwise>
                $("#account-buttons-purchaseInvoicePage").buttonWidget("enableButton", 'purchasepush-button');
            </c:otherwise>
        </c:choose>
    </script>
  </body>
</html>
