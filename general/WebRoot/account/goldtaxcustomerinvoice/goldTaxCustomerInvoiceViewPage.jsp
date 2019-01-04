<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>客户金税开票编辑页面</title>
	<%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-panel" title="" data-options="fit:true,border:false">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',border:false" style="height:135px;">
			<form action="account/goldtaxcustomerinvoice/editGoldTaxCustomerInvoice.do" id="account-form-goldTaxCustomerInvoiceAddPage" method="post">
				<input type="hidden" id="account-goldTaxCustomerInvoiceAddPage-addType" name="addType"/>
				<input type="hidden" id="account-goldTaxCustomerInvoiceAddPage-saveaudit" name="saveaudit"/>
				<table style="border-collapse:collapse;" border="0"  cellpadding="2px" cellspacing="2px">
					<tr>
						<td class="len100 left">编号：</td>
						<td class="len165"><input class="len150"  name="goldTaxCustomerInvoice.id" value="${goldTaxCustomerInvoice.id }"readonly="readonly"/></td>
						<td class="len80 left">业务日期：</td>
						<td class="len165"><input type="text" id="account-goldTaxCustomerInvoice-businessdate" class="len150" value="${goldTaxCustomerInvoice.businessdate }" name="goldTaxCustomerInvoice.businessdate" readonly="readonly" tabindex="1"  /></td>
						<td class="len80 left">状态：</td>
						<td class="len165">
							<select disabled="disabled" class="len150">
								<c:forEach items="${statusList }" var="list">
									<c:choose>
										<c:when test="${list.code == goldTaxCustomerInvoice.status}">
											<option value="${list.code }" selected="selected">${list.codename }</option>
										</c:when>
										<c:otherwise>
											<option value="${list.code }">${list.codename }</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
							<input type="hidden" id="account-goldTaxCustomerInvoice-status" name="goldTaxCustomerInvoice.status" value="${goldTaxCustomerInvoice.status }" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td class="len100 left">客户:</td>
						<td>
							<input id="account-goldTaxCustomerInvoice-customerid"  name="goldTaxCustomerInvoice.customerid" value="${goldTaxCustomerInvoice.customerid}" class="len150" />
						</td>
						<td class="len80 left">开票客户:</td>
						<td colspan="3">
							<input type="text" id="account-goldTaxCustomerInvoice-invoicecustomername" name="goldTaxCustomerInvoice.invoicecustomername" style="width: 400px;" value="${goldTaxCustomerInvoice.invoicecustomername }" autocomplete="off" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td class="len100 left">客户税号:</td>
						<td>
							<input type="text" id="account-goldTaxCustomerInvoice-customertaxno" name="goldTaxCustomerInvoice.customertaxno" style="width: 150px;" value="${goldTaxCustomerInvoice.customertaxno }" autocomplete="off" readonly="readonly"/>
						</td>
						<td class="len80 left">客户地址:</td>
						<td>
							<input type="text" id="goldTaxCustomerInvoice-customeraddr" name="goldTaxCustomerInvoice.customeraddr" class="len150" value="${goldTaxCustomerInvoice.customeraddr }" autocomplete="off" readonly="readonly"/>
						</td>
						<td class="len80 left">客户电话:</td>
						<td>
							<input type="text" id="goldTaxCustomerInvoice-customerphone" name="goldTaxCustomerInvoice.customerphone" class="len150" value="${goldTaxCustomerInvoice.customerphone }" autocomplete="off" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td class="len100 left">银行卡号:</td>
						<td>
							<input type="text" id="goldTaxCustomerInvoice-customercardno" name="goldTaxCustomerInvoice.customercardno" class="len150" value="${goldTaxCustomerInvoice.customercardno }" autocomplete="off" readonly="readonly"/>
						</td>
						<td class="len100 left">发票号:</td>
						<td>
							<input type="text" id="goldTaxCustomerInvoice-invoiceno" name="goldTaxCustomerInvoice.invoiceno" class="len150" value="${goldTaxCustomerInvoice.invoiceno }" autocomplete="off" readonly="readonly"/>
						</td>
						<td class="len80 left">发票代码:</td>
						<td>
							<input type="text" id="goldTaxCustomerInvoice-invoicecode" name="goldTaxCustomerInvoice.invoicecode" class="len150" value="${goldTaxCustomerInvoice.invoicecode }" autocomplete="off" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td class="len80 left">备注：</td>
						<td style="text-align: left">
							<input type="text" name="goldTaxCustomerInvoice.remark" style="width: 150px;" value="${goldTaxCustomerInvoice.remark }" autocomplete="off" readonly="readonly"/>
						</td>
						<td colspan="4">
							行颜色说明如下：1）金税分类编码为空<span style="background-color:#FFD9EC;">&nbsp;&nbsp;</span>，2）零税率<span style="background-color:#B5BEFF;">&nbsp;&nbsp;</span>,3)免税<span style="background-color:#CEFFCE;">&nbsp;&nbsp;</span>
						</td>
					</tr>
				</table>
				<input type="hidden" id="account-goldTaxCustomerInvoice-hid-invoicetype" value="${goldTaxCustomerInvoice.invoicetype}"/>
				<input type="hidden" id="account-goldTaxCustomerInvoice-hid-jxexporttimes" value="${goldTaxCustomerInvoice.jxexporttimes}"/>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetable"></table>
			<input type="hidden" id="account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoiceDetails" name="goldTaxCustomerInvoiceDetails"/>
		</div>
	</div>
</div>
<div id="account-Button-tableMenu" class="easyui-menu" style="width:120px;display: none;">
	<div id="account-tableMenu-itemAdd" iconCls="button-edit">添加</div>
	<div id="account-tableMenu-itemEdit" iconCls="button-edit">编辑</div>
	<div id="account-tableMenu-itemDelete" iconCls="button-delete">删除</div>
</div>
<script type="text/javascript">
    var oldcustomername="";
    $("#account-goldTaxCustomerInvoice-customerid").widget({
        width:150,
        referwid:'RL_CUSTOMER_GOLDTAX',
        singleSelect: true,
		readonly:true,
        onSelect:function(data){
        },
        onClear:function () {
        }
    });
    var editRowIndex = undefined;

    $(document).ready(function(){

        $("#account-buttons-goldTaxCustomerInvoicePage").buttonWidget("setDataID",  {id:'${goldTaxCustomerInvoice.id}',state:'${goldTaxCustomerInvoice.status}',type:'view'});
        $("#account-buttons-goldTaxCustomerInvoicePage").buttonWidget("disableMenuItem", 'button-more-invoiceno');
        $("#account-buttons-goldTaxCustomerInvoicePage").buttonWidget("disableMenuItem", 'button-export-xml-htkp');
        $("#account-buttons-goldTaxCustomerInvoicePage").buttonWidget("enableMenuItem", 'button-import-kpdetail');
        <c:if test="${goldTaxCustomerInvoice.status=='3'  or goldTaxCustomerInvoice.status=='4'}">
        $("#account-buttons-goldTaxCustomerInvoicePage").buttonWidget("disableMenuItem", 'button-import-kpdetail');
        $("#account-buttons-goldTaxCustomerInvoicePage").buttonWidget("enableMenuItem", 'button-more-invoiceno');
        $("#account-buttons-goldTaxCustomerInvoicePage").buttonWidget("enableMenuItem", 'button-export-xml-htkp');
        </c:if>

        var $goldTaxCustomerInvoicetable=$("#account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetable");
        $goldTaxCustomerInvoicetable.datagrid({
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
                var dataRows=$goldTaxCustomerInvoicetable.datagrid('getRows');
                var rowlen=dataRows.length;
                if(rowlen<12){
                    for(var i=rowlen;i<12;i++){
                        $goldTaxCustomerInvoicetable.datagrid('appendRow', {});
                    }
                }

                $goldTaxCustomerInvoicetable.datagrid('reloadFooter',[
                    {goodsname: '合计', taxprice:'',notaxprice:'',notaxamount:'${goldTaxCustomerInvoiceDetailSum.notaxamount}',taxamount:'${goldTaxCustomerInvoiceDetailSum.taxamount}',tax:'${goldTaxCustomerInvoiceDetailSum.tax}'}
                ]);
            },
            onSortColumn:function(sort, order){
                var rows = $("#account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetable").datagrid("getRows");
                var dataArr = [];
                for(var i=0;i<rows.length;i++){
                    if(rows[i].upid!=null && rows[i].upid!=""){
                        dataArr.push(rows[i]);
                    }
                }
                dataArr.sort(function(a,b){
                    var asort=a[sort];
                    var bsort=b[sort];
                    if(order=="asc"){
                        if(asort!=null && bsort != null && !isNaN(asort) && !isNaN(bsort)){
                            return Number(asort) > Number(bsort) ? 1 : -1;
                        }
                        return a[sort]>b[sort]?1:-1
                    }else{
                        if(asort!=null && bsort != null && !isNaN(asort) && !isNaN(bsort)){
                            return Number(asort) > Number(bsort) ? -1 : 1;
                        }
                        return a[sort]<b[sort]?1:-1
                    }
                });
                $("#account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetable").datagrid("loadData",dataArr);
                return false;
            }
        }).datagrid("columnMoving");

    });
</script>
</body>
</html>
