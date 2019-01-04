<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>发票号修改</title>
    <%@include file="/include.jsp" %>
</head>

<body>

<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
    <form action="" id="account-form-goldTaxCustomerInvoiceNoEditPage" method="post" style="padding-top: 2px;">
        <table>
            <tr>
                <td>
                    单据号：
                </td>
                <td>
                    <input type="text" id="account-form-goldTaxCustomerInvoiceNoEditPage-id" name="goldTaxCustomerInvoice.id" value="${goldTaxCustomerInvoice.id}" class="readonly" readonly="readonly" style="width: 200px;"  />
                </td>
            </tr>
            <tr>
                <td>
                    开票客户：
                </td>
                <td>
                    <input type="text" id="account-form-goldTaxCustomerInvoiceNoEditPage-invoicecustomername"  value="${goldTaxCustomerInvoice.invoicecustomername}" class="readonly" readonly="readonly" style="width: 200px;"  />
                </td>
            </tr>
            <tr>
                <td>
                    发票号：
                </td>
                <td>
                    <input type="text" id="account-form-goldTaxCustomerInvoiceNoEditPage-invoiceno" name="goldTaxCustomerInvoice.invoiceno" value="${goldTaxCustomerInvoice.invoiceno}" style="width: 200px;"  />
                </td>
            </tr>
            <tr>
                <td>
                    发票代码：
                </td>
                <td>
                    <input type="text" id="account-form-goldTaxCustomerInvoiceNoEditPage-invoicecode" name="goldTaxCustomerInvoice.invoicecode" value="${goldTaxCustomerInvoice.invoicecode}" style="width: 200px;"  />
                </td>
            </tr>
        </table>
    </form>
    </div>
    <div data-options="region:'south',border:false">
        <div style="text-align: right;line-height:35px;">
        <a href="javaScript:void(0);" id="account-form-goldTaxCustomerInvoiceNoEditPage-buttons-add" class="easyui-linkbutton" data-options="plain:false,iconCls:'button-edit'">更新</a>&nbsp;
        </div>
    </div>

</div>
<script type="text/javascript">
    $(function(){


        $("#account-form-goldTaxCustomerInvoiceNoEditPage-buttons-add").click(function(){
            var fromflag = $("#account-form-goldTaxCustomerInvoiceNoEditPage").form('validate');
            if (!fromflag) {
                $.messager.alert("提醒", "抱歉，请认真填写表单中数据");
                return false;
            }
            var formdata = $("#account-form-goldTaxCustomerInvoiceNoEditPage").serializeJSON();
            formdata.oldFromData = "";
            delete formdata.oldFromData;
            var invoiceno=$("#account-form-goldTaxCustomerInvoiceNoEditPage-invoiceno").val() || "";
            var invoicecode = $("#account-form-goldTaxCustomerInvoiceNoEditPage-invoiceno").val() || "";
            var msg="";
            if(invoiceno=="" && invoicecode==""){
                msg="发票号、发票代码设置为空<br/>";
            }
            msg=msg+"确认要修改信息吗？";
            $.messager.confirm("提醒",msg,function(r){
                if(r){
                    loading("提交中..");
                    $.ajax({
                        url:'account/goldtaxcustomerinvoice/editGoldTaxCustomerInvoiceNo.do',
                        dataType:'json',
                        type:'post',
                        data:formdata,
                        success:function(json){
                            loaded();
                            if(json.flag==true){
                                $.messager.alert("提醒","修改成功!<br>");
                                <c:choose>
                                    <c:when  test="${param.from=='page'}">
                                invoiceNoEditPageRefreshPage();
                                    </c:when>
                                <c:when  test="${param.from=='list'}">
                                invoiceNoEditPageRefreshList();
                                </c:when>
                                </c:choose>
                                $('#account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper-content').dialog("close");
                            }else{
                                $.messager.alert("提醒","修改失败!");
                            }
                        },
                        error:function(){
                            loaded();
                            $.messager.alert("错误","更新出错!");
                        }
                    });
                }
            });
        });
    });
    function invoiceNoEditPageRefreshPage(){
        try{
            goldTaxCustomerInvoice_RefreshDataGrid();
            $("#account-panel-goldTaxCustomerInvoicePage").panel('refresh', 'account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceEditPage.do?id=${goldTaxCustomerInvoice.id}' );
        }catch(e){

        }
    }
    function invoiceNoEditPageRefreshList(){
        try{
            $("#account-table-goldTaxCustomerInvoiceListPage").datagrid('reload');
        }catch(e){

        }
    }
</script>
</body>
</html>
