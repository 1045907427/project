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
						<td class="len165"><input class="len150"  name="goldTaxCustomerInvoice.id" value="${goldTaxCustomerInvoice.id }" readonly="readonly"/></td>
						<td class="len80 left">业务日期：</td>
						<td class="len165"><input type="text" id="account-goldTaxCustomerInvoice-businessdate" class="len150" value="${goldTaxCustomerInvoice.businessdate }" name="goldTaxCustomerInvoice.businessdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" class="easyui-validatebox "  required="required" tabindex="1"  /></td>
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
							<input type="hidden" id="account-goldTaxCustomerInvoice-hid-customername"  name="goldTaxCustomerInvoice.customername" value="${goldTaxCustomerInvoice.customername}"/>
						</td>
						<td class="len80 left">开票客户:</td>
						<td colspan="3">
							<input type="text" id="account-goldTaxCustomerInvoice-invoicecustomername" name="goldTaxCustomerInvoice.invoicecustomername" style="width: 400px;" value="${goldTaxCustomerInvoice.invoicecustomername }" autocomplete="off" class="easyui-validatebox "  required="required"  onfocus="frm_focus('goldTaxCustomerInvoice.invoicecustomername');" onblur="frm_blur('goldTaxCustomerInvoice.invoicecustomername');"/>
						</td>
					</tr>
					<tr>
						<td class="len100 left">客户税号:</td>
						<td>
							<input type="text" id="account-goldTaxCustomerInvoice-customertaxno" name="goldTaxCustomerInvoice.customertaxno" style="width: 150px;" value="${goldTaxCustomerInvoice.customertaxno }" autocomplete="off" onfocus="frm_focus('goldTaxCustomerInvoice.customertaxno');" onblur="frm_blur('goldTaxCustomerInvoice.customertaxno');"/>
						</td>
						<td class="len80 left">客户地址:</td>
						<td>
							<input type="text" id="goldTaxCustomerInvoice-customeraddr" name="goldTaxCustomerInvoice.customeraddr" class="len150" value="${goldTaxCustomerInvoice.customeraddr }" autocomplete="off" onfocus="frm_focus('goldTaxCustomerInvoice.customeraddr');" onblur="frm_blur('goldTaxCustomerInvoice.customeraddr');"/>
						</td>
						<td class="len80 left">客户电话:</td>
						<td>
							<input type="text" id="goldTaxCustomerInvoice-customerphone" name="goldTaxCustomerInvoice.customerphone" class="len150" value="${goldTaxCustomerInvoice.customerphone }" autocomplete="off" onfocus="frm_focus('goldTaxCustomerInvoice.customerphone');" onblur="frm_blur('goldTaxCustomerInvoice.customerphone');"/>
						</td>
					</tr>
					<tr>
						<td class="len100 left">银行卡号:</td>
						<td>
							<input type="text" id="goldTaxCustomerInvoice-customercardno" name="goldTaxCustomerInvoice.customercardno" class="len150" value="${goldTaxCustomerInvoice.customercardno }" autocomplete="off" onfocus="frm_focus('goldTaxCustomerInvoice.customercardno');" onblur="frm_blur('goldTaxCustomerInvoice.customercardno');"/>
						</td>
						<td class="len80 left">发票号:</td>
						<td>
							<input type="text" id="goldTaxCustomerInvoice-invoiceno" name="goldTaxCustomerInvoice.invoiceno" class="len150" value="${goldTaxCustomerInvoice.invoiceno }" autocomplete="off" onfocus="frm_focus('goldTaxCustomerInvoice.invoiceno');" onblur="frm_blur('goldTaxCustomerInvoice.invoiceno');"/>
						</td>
						<td class="len80 left">发票代码:</td>
						<td>
							<input type="text" id="goldTaxCustomerInvoice-invoicecode" name="goldTaxCustomerInvoice.invoicecode" class="len150" value="${goldTaxCustomerInvoice.invoicecode }" autocomplete="off" onfocus="frm_focus('goldTaxCustomerInvoice.invoicecode');" onblur="frm_blur('goldTaxCustomerInvoice.invoicecode');"/>
						</td>
					</tr>
					<tr>
						<td class="len80 left">备注：</td>
						<td style="text-align: left">
							<input type="text" name="goldTaxCustomerInvoice.remark" style="width: 150px;" value="${goldTaxCustomerInvoice.remark }" autocomplete="off" onfocus="frm_focus('goldTaxCustomerInvoice.remark');" onblur="frm_blur('goldTaxCustomerInvoice.remark');"/>
						</td>
						<td colspan="4">
							行颜色说明如下：1）金税分类编码为空<span style="background-color:#FFD9EC;">&nbsp;&nbsp;</span>，2）零税率<span style="background-color:#B5BEFF;">&nbsp;&nbsp;</span>,3)免税<span style="background-color:#CEFFCE;">&nbsp;&nbsp;</span>
						</td>
					</tr>
				</table>
				<input type="hidden" id="account-goldTaxCustomerInvoice-hid-invoicetype" value="${goldTaxCustomerInvoice.invoicetype}"/>
				<input type="hidden" id="account-goldTaxCustomerInvoice-hid-jxexporttimes" value="${goldTaxCustomerInvoice.jxexporttimes}"/>
				<input type="hidden" id="account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetableDetails" name="goldTaxCustomerInvoiceDetails"/>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetable"></table>
		</div>
	</div>
</div>
<div id="account-Button-tableMenu" class="easyui-menu" style="width:120px;display: none;">
	<div id="account-tableMenu-itemAdd" iconCls="button-edit">添加</div>
	<div id="account-tableMenu-itemEdit" iconCls="button-edit">编辑</div>
	<div id="account-tableMenu-itemDelete" iconCls="button-delete">删除</div>
	<div id="account-tableMenu-editJsTaxtype" iconCls="button-edit">批量修改金税分类</div>
</div>
<script type="text/javascript">
    var oldcustomername="";
    $("#account-goldTaxCustomerInvoice-customerid").widget({
        width:150,
        referwid:'RL_CUSTOMER_GOLDTAX',
        singleSelect: true,
        onSelect:function(data){
            $("#account-goldTaxCustomerInvoice-hid-customername").val(data.name ||"" );
            var invoicecustomername=$("#account-goldTaxCustomerInvoice-invoicecustomername").val() ||"";
            invoicecustomername= $.trim(invoicecustomername);
            if(invoicecustomername=="" || oldcustomername==invoicecustomername){
                $("#account-goldTaxCustomerInvoice-invoicecustomername").val(data.name || "");
            }
            oldcustomername=data.name;
            $("#account-goldTaxCustomerInvoice-invoicecustomername").focus();
            $("#account-goldTaxCustomerInvoice-invoicecustomername").select();
            chooseNo="goldTaxCustomerInvoice.invoicecustomername";

            if(data.taxno!=null && $.trim(data.taxno)!=""){
                $("#account-goldTaxCustomerInvoice-customertaxno").val(data.taxno);
            }
            if(data.address!=null && $.trim(data.address)!=""){
                $("#account-goldTaxCustomerInvoice-customeraddr").val(data.address);
            }
            if(data.mobile!=null && $.trim(data.mobile)!=""){
                $("#account-goldTaxCustomerInvoice-customerphone").val(data.mobile);
            }

        },
        onClear:function () {
            var invoicecustomername=$("#account-goldTaxCustomerInvoice-invoicecustomername").val() ||"";
            invoicecustomername= $.trim(invoicecustomername);
            if(invoicecustomername!="" && oldcustomername==invoicecustomername){
                $("#account-goldTaxCustomerInvoice-invoicecustomername").val("");
            }
            oldcustomername="";
        }
    });
    var editRowIndex = undefined;

    $(document).ready(function(){

        $("#account-buttons-goldTaxCustomerInvoicePage").buttonWidget("setDataID",  {id:'${goldTaxCustomerInvoice.id}',state:'${goldTaxCustomerInvoice.status}',type:'edit'});
        $("#account-buttons-goldTaxCustomerInvoicePage").buttonWidget("disableMenuItem", 'button-more-invoiceno');
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
            idField:'upid',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
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
            onDblClickRow: function(rowIndex, rowData){ //选中行
                editRowIndex=rowIndex;
                if(rowData.goodsname && rowData.goodsname!=""){
                    orderDetailEditDialog(rowData);
                }else{
                    orderDetailAddDialog();
                }
            },
            onRowContextMenu:function(e, rowIndex, rowData){
                e.preventDefault();
                var $contextMenu=$('#account-Button-tableMenu');
                $contextMenu.menu('show', {
                    left : e.pageX,
                    top : e.pageY
                });
                $goldTaxCustomerInvoicetable.datagrid('selectRow', rowIndex);
                editRowIndex=rowIndex;
                $contextMenu.menu('enableItem', '#account-tableMenu-itemEdit');
                $contextMenu.menu('enableItem', '#account-tableMenu-itemInsert');
                $contextMenu.menu('enableItem', '#account-tableMenu-itemDelete');
                $contextMenu.menu('enableItem', '#account-tableMenu-editJsTaxtype');
            },
            rowStyler: function(index,row){
                return getGridRowStyle(row);
            }
        }).datagrid("columnMoving");

        //添加按钮事件
        $("#account-tableMenu-itemAdd").unbind("click").bind("click",function(){
            if($("#account-Button-tableMenu").menu('getItem',this).disabled){
                return false;
            }
            orderDetailAddDialog();
        });

        //编辑按钮事件
        $("#account-tableMenu-itemEdit").unbind("click").bind("click",function(){
            if($("#account-Button-tableMenu").menu('getItem',this).disabled){
                return false;
            }
            var data=$("#account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetable").datagrid('getSelected');
            orderDetailEditDialog(data);
        });

        $("#account-tableMenu-itemDelete").unbind("click").bind("click",function(){
            if($("#account-Button-tableMenu").menu('getItem',this).disabled){
                return false;
            }
            $goldTaxCustomerInvoicetable = $("#account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetable");
            var dataRow=$goldTaxCustomerInvoicetable.datagrid('getSelected');
            if(dataRow!=null){
                $.messager.confirm("提示","是否要删除选中的行？", function(r){
                    if (r){
                        if(dataRow!=null){
                            var rowIndex =$goldTaxCustomerInvoicetable.datagrid('getRowIndex',dataRow);
                            $goldTaxCustomerInvoicetable.datagrid('updateRow',{
                                index:rowIndex,
                                row:{}
                            });
                            $goldTaxCustomerInvoicetable.datagrid('deleteRow', rowIndex);
                            var rowlen=$goldTaxCustomerInvoicetable.datagrid('getRows').length;
                            if(rowlen<15){
                                $goldTaxCustomerInvoicetable.datagrid('appendRow', {});
                            }
                            editRowIndex=undefined;

                            $goldTaxCustomerInvoicetable.datagrid('clearSelections');
                            footerReCalc();
                        }
                    }
                });
            }
        });

        $("#account-tableMenu-editJsTaxtype").unbind("click").bind("click",function(){
            if($("#account-Button-tableMenu").menu('getItem',this).disabled){
                return false;
            }

            var datarow=$("#account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetable").datagrid("getChecked");
            if(datarow==null || datarow.length==0){
                $.messager.alert("错误","抱歉，请选择您要修改金税分类");
                return false;
            }
            $("#account-goldTaxCustomerInvoicePage-batcheditjstype-dailogdiv-jstypeid").widget("clear");

            $('#account-goldTaxCustomerInvoicePage-batcheditjstype-dailogdiv').dialog({
                title: '批量修改金税分类',
                //fit:true,
                width:300,
                height:150,
                closed: true,
                cache: false,
                maximizable:true,
                resizable:true,
                modal: true,
                buttons: [
                    {
                        text: '确定',
                        iconCls: 'button-ok',
                        handler: function () {
                            $.messager.confirm("提示", "是否要批量修改金税分类？", function (r) {
                                if (r) {
                                    var jstypeid = $("#account-goldTaxCustomerInvoicePage-batcheditjstype-dailogdiv-jstypeid").widget("getValue");
                                    if (jstypeid != "") {
                                        for (var i = 0; i < datarow.length; i++) {
                                            var rowIndex = $("#account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetable").datagrid('getRowIndex', datarow[i].upid);
                                            $("#account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetable").datagrid('updateRow', {
                                                index: rowIndex,
                                                row: {
                                                    jstypeid: jstypeid
                                                }
                                            });
                                        }
                                    }
                                    $('#account-goldTaxCustomerInvoicePage-batcheditjstype-dailogdiv').dialog("close");
                                }else{
                                    $('#account-goldTaxCustomerInvoicePage-batcheditjstype-dailogdiv').dialog("close");
                                }
                            });
                        }
                    },

                    {
                        text: '取消',
                        iconCls: 'button-cancel',
                        handler: function () {
                            $('#account-goldTaxCustomerInvoicePage-batcheditjstype-dailogdiv').dialog("close");
                            return false;
                        }
                    }
                ]
            });
            $('#account-goldTaxCustomerInvoicePage-batcheditjstype-dailogdiv').dialog('open');
        });


        $("#account-goldTaxCustomerInvoiceAddPage-remark").die("keydown").live("keydown",function(event){
            //enter
            if(event.keyCode==13){
                var flag = $("#account-form-goldTaxCustomerInvoiceAddPage").form('validate');
                if(flag==false){
                    $.messager.alert("提醒",'请先完善客户金税开票基本信息');
                    return false;
                }else{
                    $("#account-goldTaxCustomerInvoiceAddPage-remark").blur();
                    //orderDetailAddDialog();
                }
            }
        });
    });
</script>
</body>
</html>
