<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>客户金税开票列表</title>
    <%@include file="/include.jsp" %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="account-buttons-goldTaxCustomerInvoiceListPage"></div>
        <a href="javaScript:void(0);" id="account-btn-exportGoldTaxCustomerInvoiceListPage-excel" style="display: none">导出数据</a>
    </div>
    <div data-options="region:'center'">
        <table id="account-table-goldTaxCustomerInvoiceListPage"></table>
        <div id="account-table-query-goldTaxCustomerInvoiceListPage" style="padding:2px;height:auto">
            <div>
                <form action="" id="account-form-goldTaxCustomerInvoiceListPage" method="post">
                    <table class="querytable">
                        <tr>
                            <td>业务日期:</td>
                            <td class="tdinput">
                                <input type="text" id="account-goldTaxCustomerInvoiceListPage-businessdatestart"
                                       name="businessdatestart" style="width:100px;" class="Wdate"
                                       onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/> 到 <input type="text"
                                                                                                  id="account-goldTaxCustomerInvoiceListPage-businessdateend"
                                                                                                  name="businessdateend"
                                                                                                  class="Wdate"
                                                                                                  style="width:100px;"
                                                                                                  onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                            </td>
                            <td>档案客户:</td>
                            <td class="tdinput">
                                <input id="account-query-customerid" type="text" name="customerid"
                                       style="width:180px;"/>
                            </td>
                            <td>编号:</td>
                            <td><input type="text" name="id" style="width:165px;"/></td>
                        </tr>
                        <tr>
                            <td>发票号:</td>
                            <td>
                                <input id="account-query-invoiceno" type="text" name="invoiceno" style="width:220px;"/>
                            </td>
                            <td>开票客户:</td>
                            <td class="tdinput">
                                <input id="account-query-invoicecustomername" type="text" name="invoicecustomername" style="width:180px;"/>
                            </td>
                            <td>状态:</td>
                            <td>
                                <select id="account-queay-salesInvoice-status" name="status" style="width:165px;">
                                    <option></option>
                                    <option value="1">暂存</option>
                                    <option value="2" selected="selected">保存</option>
                                    <option value="3">审核通过</option>
                                    <option value="4">关闭</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>发票代码:</td>
                            <td>
                                <input id="account-query-invoicecode" type="text" name="invoicecode" style="width:220px;"/>
                            </td>
                            <td>金税导出:</td>
                            <td>
                                <select name="jsexportsign" style="width:180px;">
                                    <option></option>
                                    <option value="1">未导出</option>
                                    <%-- 特别
                                    <option value="2">小于</option>
                                    <option value="3">小于等于</option>
                                     --%>
                                    <option value="4">已导出</option>
                                    <%-- 特别
                                    <option value="5">大于等于</option>
                                     --%>
                                </select>
                                <input type="hidden" name="queryjxexporttimes" value="0"/>
                            </td>
                            <td colspan="2" class="tdbutton">
                                <a href="javaScript:void(0);" id="account-btn-queryGoldTaxCustomerInvoiceListPage"
                                   class="button-qr">查询</a>
                                <a href="javaScript:void(0);" id="account-btn-reloadGoldTaxCustomerInvoiceListPage"
                                   class="button-qr">重置</a>
                                <span id="account-table-query-goldTaxCustomerInvoiceListPage-advanced"></span>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>
<div style="display: none;">
    <div id="account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper"></div>
    <div id="account-account-dialog"></div>
</div>
<script type="text/javascript">
    var SR_footerobject = null;
    $(document).ready(function () {
        var initQueryJSON = $("#account-form-goldTaxCustomerInvoiceListPage").serializeJSON();
        var goldTaxCustomerInvoiceListJson = $("#account-table-goldTaxCustomerInvoiceListPage").createGridColumnLoad({
            name: 'goldtax_third_invoice',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编号', width: 120, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 70, sortable: true},
                {field: 'customerid', title: '档案客户编码', width: 70, sortable: true},
                {field: 'customername', title: '档案客户名称', width: 100, isShow: true},
                {field: 'invoicecustomername', title: '开票客户名称', width: 100},
                {
                    field: 'invoicetype', title: '开票类型', width: 80,isShow:false,hidden:true,
                    formatter: function (value, row, index) {
                        if(value==1){
                            return "增值税";
                        }else if(value==2){
                            return "普通";
                        }else {
                            return "其他";
                        }
                    }
                },
                {field: 'invoiceno', title: '发票号', width: 80},
                {field: 'invoicecode', title: '发票代码', width: 80},
                {field: 'customertaxno', title: '购方税号', width: 80},
                {
                    field: 'taxamount', title: '含税总金额', resizable: true, sortable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'notaxamount', title: '未税总金额', resizable: true, sortable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'tax', title: '税总金额', resizable: true, sortable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {field: 'addusername', title: '制单人', width: 80},
                {field: 'adddeptname', title: '制单人部门', width: 100, hidden: true},
                {field: 'addtime', title: '制单时间', width: 120, sortable: true, hidden: true},
                {field: 'modifyusername', title: '修改人', width: 120, hidden: true},
                {field: 'auditusername', title: '审核人', width: 100, hidden: true},
                {field: 'audittime', title: '审核时间', width: 100, hidden: true},
                {field:'jxexportstate',title:'导出状态',width:80,isShow:true,
                    formatter:function(value,rowData,index){
                        if(rowData.jxexporttimes>0){
                            return "已导出";
                        }else if(rowData.jxexporttimes==null){
                            return "";
                        }else{
                            return "未导出";
                        }
                    }
                },
                {field:'jxexporttimes',title:'导出次数',align:'center',width:60,
                    formatter:function(value,rowData,index){
                        if(value == null){
                            return "";
                        }else{
                            return value;
                        }
                    }
                },
                {field: 'remark', title: '备注', width: 100}
            ]]
        });
        $("#account-table-goldTaxCustomerInvoiceListPage").datagrid({
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            showFooter: true,
            toolbar: '#account-table-query-goldTaxCustomerInvoiceListPage',
            url: "account/goldtaxcustomerinvoice/getGoldTaxCustomerInvoicePageData.do",
            queryParams: initQueryJSON,
            pageSize: 100,
            authority: goldTaxCustomerInvoiceListJson,
            frozenColumns: goldTaxCustomerInvoiceListJson.frozen,
            columns: goldTaxCustomerInvoiceListJson.common,
            onLoadSuccess: function () {
                var footerrows = $(this).datagrid('getFooterRows');
                if (null != footerrows && footerrows.length > 0) {
                    SR_footerobject = footerrows[0];
                    if (SR_footerobject != null) {
                        SR_footerobject.printtimes = -99;
                    }
                    countTotalAmount();
                }
            },
            onDblClickRow: function (index, data) {
                top.addOrUpdateTab('account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoicePage.do?type=edit&id=' + data.id, "客户金税开票查看");
            },
            onCheckAll: function (rows) {
                countTotalAmount();
            },
            onUncheckAll: function (rows) {
                countTotalAmount();
            },
            onCheck: function (index, row) {
                countTotalAmount();
            },
            onUncheck: function (index, row) {
                countTotalAmount();
            }
        }).datagrid("columnMoving");

        //按钮
        $("#account-buttons-goldTaxCustomerInvoiceListPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/account/goldtaxcustomerinvoice/goldTaxCustomerInvoiceAddBtn.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addOrUpdateTab('account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoicePage.do', '客户金税开票新增');
                    }
                },
                </security:authorize>
                <security:authorize url="/account/goldtaxcustomerinvoice/goldTaxCustomerInvoiceEditBtn.do">
                {
                    type: 'button-edit',
                    handler: function () {
                        var datarow = $("#account-table-goldTaxCustomerInvoiceListPage").datagrid('getSelected');
                        if (datarow == null || datarow.id == null) {
                            $.messager.alert("提醒", "请选择要修改的客户金税开票");
                            return false;
                        }
                        top.addOrUpdateTab('account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoicePage.do?type=edit&id=' + datarow.id, '客户金税开票修改');
                    }
                },
                </security:authorize>
                <security:authorize url="/account/goldtaxcustomerinvoice/goldTaxCustomerInvoiceViewBtn.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var datarow = $("#account-table-goldTaxCustomerInvoiceListPage").datagrid('getSelected');
                        if (datarow == null || datarow.id == null) {
                            $.messager.alert("提醒", "请选择要查看的客户金税开票");
                            return false;
                        }
                        top.addOrUpdateTab('account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoicePage.do?type=edit&id=' + datarow.id, '客户金税开票查看');
                    }
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        name: 'goldtax_third_invoice',
                        plain: true,
                        datagrid: 'account-table-goldTaxCustomerInvoiceListPage'
                    }
                },
                {}
            ],
            buttons:[

                <security:authorize url="/account/goldtaxcustomerinvoice/goldTaxCustomerInvoiceEditInvoiceInfoBtn.do">
                {
                    id:'button-more-invoiceno',
                    name:'编辑发票号',
                    iconCls:'button-edit',
                    handler:function(){
                        var datarow = $("#account-table-goldTaxCustomerInvoiceListPage").datagrid('getSelected');
                        if (datarow == null || datarow.id == null) {
                            $.messager.alert("提醒", "请选择要查看的客户金税开票");
                            return false;
                        }
                        $('<div id="account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper-content"></div>').appendTo("#account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper");
                        $('#account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper-content').dialog({
                            title: '编辑发票号发票代码',
                            //fit:true,
                            width:300,
                            height:200,
                            closed: true,
                            cache: false,
                            href: 'account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceNoEditPage.do',
                            queryParams:{
                                id:datarow.id,
                                from:"page"
                            },
                            maximizable:true,
                            resizable:true,
                            modal: true,
                            onLoad:function(){
                            },
                            onClose:function(){
                                $('#account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper-content').dialog("destroy");
                            }
                        });
                        $('#account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper-content').dialog('open');
                    }
                },
                </security:authorize>
                {}
            ],
            model: 'bill',
            type: 'list',
            datagrid: 'account-table-goldTaxCustomerInvoiceListPage',
            tname: 'goldtax_third_invoice'

        });

        $("#account-btn-queryGoldTaxCustomerInvoiceListPage").click(function () {
            //查询参数直接添加在url中
            var queryJSON = $("#account-form-goldTaxCustomerInvoiceListPage").serializeJSON();
            $('#account-table-goldTaxCustomerInvoiceListPage').datagrid('load', queryJSON);
        });
        $("#account-btn-reloadGoldTaxCustomerInvoiceListPage").click(function () {
            $("#account-form-goldTaxCustomerInvoiceListPage")[0].reset();
            $("#account-goldTaxCustomerInvoiceListPage-buydeptid").widget('clear');
            $("#account-goldTaxCustomerInvoiceListPage-buydept").widget('clear');
            $("#account-goldTaxCustomerInvoiceListPage-goodsid").goodsWidget('clear');
            $("#account-goldTaxCustomerInvoiceListPage-supplier").supplierWidget("clear");
            var queryJSON = $("#account-form-goldTaxCustomerInvoiceListPage").serializeJSON();
            $('#account-table-goldTaxCustomerInvoiceListPage').datagrid('load', queryJSON);
        });

        $("#account-goldTaxCustomerInvoiceListPage-goodsid").goodsWidget({
            singleSelect: true,
            isHiddenUsenum: true
        });
        $("#account-query-customerid").widget({
            width:180,
            referwid:'RL_CUSTOMER_GOLDTAX',
            singleSelect: true,
            onSelect:function(data){
            },
            onClear:function () {
            }
        });
    });
    function countTotalAmount() {
        var rows = $("#account-table-goldTaxCustomerInvoiceListPage").datagrid('getChecked');
        var taxamount = 0;
        var notaxamount = 0;
        var tax = 0;
        for (var i = 0; i < rows.length; i++) {
            taxamount = Number(taxamount) + Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
            notaxamount = Number(notaxamount) + Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
            tax = Number(tax) + Number(rows[i].tax == undefined ? 0 : rows[i].tax);
        }
        var foot = [{
            id: '选中金额',
            taxamount: taxamount,
            notaxamount: notaxamount,
            tax: tax
        }];
        if (null != SR_footerobject) {
            foot.push(SR_footerobject);
        }
        $("#account-table-goldTaxCustomerInvoiceListPage").datagrid("reloadFooter", foot);
    }
</script>

</body>
</html>
