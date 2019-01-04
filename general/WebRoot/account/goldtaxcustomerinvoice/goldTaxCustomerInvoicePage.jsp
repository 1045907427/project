<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>客户金税开票</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<input type="hidden" id="account-backid-goldTaxCustomerInvoiceAddPage" value="${id }"/>
<div id="account-goldTaxCustomerInvoicePage-layout" class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="account-buttons-goldTaxCustomerInvoicePage"></div>
    </div>
    <div data-options="region:'center'">
        <div class="easyui-panel" data-options="fit:true" id="account-panel-goldTaxCustomerInvoicePage">
        </div>
    </div>
</div>
<div style="display:none">
    <div id="account-dialog-showJSKPSysParamConfigOper"></div>
    <div id="account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper"></div>
    <a href="javaScript:void(0);" id="account-buttons-exportCustomerGoldTaxInvoiceXmlForHTKP-click" style="display: none"title="导出金税航天开票xml文件">导出金税航天开票xml文件</a>
    <a href="javaScript:void(0);" id="account-buttons-importCustomerGoldTaxInvoiceData-click" style="display: none"title="导入客户开票数据">导入客户开票数据</a>
    <div id="account-dialog-goldTaxCustomerInvoiceDetail-goodsOper">
        <form id="account-dialog-goldTaxCustomerInvoiceDetail-goodsOper-form" method="post">
            <table style="border-collapse:collapse;" border="0"  cellpadding="3" cellspacing="3">
                <tr>
                    <td style="line-height: 35px;">商品档案：</td>
                    <td>
                       <input type="text" id="account-dialog-goldTaxCustomerInvoiceDetail-goodsOper-goodsid" style="width:200px" />
                    </td>
                </tr>
                <tr>
                    <td>填入选项：</td>
                    <td>
                        <label>
                            <input type="checkbox" class="goodsOperChecks" id="account-dialog-goldTaxCustomerInvoiceDetail-goodsOper-ck-name" checked="checked" />名称
                        </label>
                        <label>
                            <input type="checkbox" class="goodsOperChecks" id="account-dialog-goldTaxCustomerInvoiceDetail-goodsOper-ck-jstypeid" checked="checked" />金税分类编码
                        </label>
                        <label>
                            <input type="checkbox" class="goodsOperChecks" id="account-dialog-goldTaxCustomerInvoiceDetail-goodsOper-ck-model" checked="checked" />规格型号
                        </label>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <label>
                            <input type="checkbox" class="goodsOperChecks" id="account-dialog-goldTaxCustomerInvoiceDetail-goodsOper-ck-unitname" checked="checked" />计量单位
                        </label>
                        <label>
                            <input type="checkbox" class="goodsOperChecks" id="account-dialog-goldTaxCustomerInvoiceDetail-goodsOper-ck-taxrate" checked="checked" />税率
                        </label>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div id="account-dialog-goldTaxCustomerInvoiceDetail-unitnameOper">
        <form id="account-dialog-goldTaxCustomerInvoiceDetail-unitnameOper-form" method="post">
            <table style="border-collapse:collapse;" border="0"  cellpadding="3" cellspacing="3">
                <tr>
                    <td style="line-height: 35px;">计量单位档案：</td>
                    <td>
                        <input type="text" id="account-dialog-goldTaxCustomerInvoiceDetail-unitnameOper-unitid" style="width:250px" />
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <div id="account-goldTaxCustomerInvoicePage-htkpdialogdiv">
        <form action="" method="post" id="account-goldTaxCustomerInvoicePage-htkp-form">
            <table>
                <tr id="account-goldTaxCustomerInvoicePage-htkp-form-title-tr">
                    <td>导出文件名:</td>
                    <td>
                        <input type="text" id="account-goldTaxCustomerInvoicePage-htkp-form-title" name="title"
                               style="width: 180px"/>
                    </td>
                </tr>
                <tr>
                    <td>收款人:</td>
                    <td>
                        <input type="text" id="account-goldTaxCustomerInvoicePage-htkp-form-receipter" name="receipterid"
                               style="width: 180px" value="${jskpDefaultReceipter}"/>
                    </td>
                </tr>
                <tr>
                    <td>复核人:</td>
                    <td>
                        <input type="text" id="account-goldTaxCustomerInvoicePage-htkp-form-checker" name="checkerid"
                               style="width: 180px" value="${jskpDefaultChecker}"/>
                    </td>
                </tr>
                <tr id="account-goldTaxCustomerInvoicePage-htkp-form-jsgoodsversion-tr" >
                    <td>商品版本号:</td>
                    <td>
                        <input type="text" id="account-goldTaxCustomerInvoicePage-htkp-form-jsgoodsversion" name="jsgoodsversion"
                               class="easyui-validatebox" style="width: 180px" value="${jsGoodsVersion}"/>
                    </td>
                </tr>
                <tr id="account-goldTaxCustomerInvoicePage-htkp-form-options-amount">
                    <td>金额选项:</td>
                    <td>
                        <select name="amountoptions" style="width:180px">
                            <option value="">全部</option>
                            <option value="1">正数</option>
                        </select>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="account-goldTaxCustomerInvoicePage-htkp-form-exportid" name="exportid"/>
        </form>
    </div>
    <div id="account-goldTaxCustomerInvoicePage-batcheditjstype-dailogdiv">
        <table>
            <tr>
                <td>金税分类:</td>
                <td>
                    <input type="text" id="account-goldTaxCustomerInvoicePage-batcheditjstype-dailogdiv-jstypeid" name="jstypeid"
                           style="width: 180px"/>
                </td>
            </tr>
        </table>
    </div>
</div>
<script type="text/javascript">
    var goldTaxCustomerInvoice_type = '${type}';
    goldTaxCustomerInvoice_type = $.trim(goldTaxCustomerInvoice_type.toLowerCase());
    if (goldTaxCustomerInvoice_type == "") {
        goldTaxCustomerInvoice_type = 'add';
    }
    var goldTaxCustomerInvoice_url = "account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceAddPage.do";
    if (goldTaxCustomerInvoice_type == "view" || goldTaxCustomerInvoice_type == "show" || goldTaxCustomerInvoice_type == "handle") {
        goldTaxCustomerInvoice_url = "account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceViewPage.do?id=${id}";
    }
    if (goldTaxCustomerInvoice_type == "edit") {
        goldTaxCustomerInvoice_url = "account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceEditPage.do?id=${id}";
    }
    if (goldTaxCustomerInvoice_type == "copy") {
        goldTaxCustomerInvoice_url = "account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceCopyPage.do?id=${id}";
    }
    var pageListUrl = "/account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceListPage.do";
    function getGoldTaxCustomerPanelUrl(opertype){
        var id=$("#account-backid-goldTaxCustomerInvoiceAddPage").val()||"";
        var url = "account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceAddPage.do";
        if (opertype == "view" || opertype == "show" || opertype == "handle") {
            url = "account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceViewPage.do?id="+id;
        }
        if (opertype == "edit") {
            url = "account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceEditPage.do?id="+id;
        }
        if (opertype == "copy") {
            url = "account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceCopyPage.do?id="+id;
        }
        return url;
    }
    function goldTaxCustomerInvoice_tempSave_form_submit() {
        $("#account-form-goldTaxCustomerInvoiceAddPage").form({
            onSubmit: function () {
                loading("提交中..");
            },
            success: function (data) {
                loaded();
                var json = $.parseJSON(data);
                if (json.flag == true) {
                    goldTaxCustomerInvoice_RefreshDataGrid();
                    $.messager.alert("提醒", "暂存成功");
                    $("#account-backid-goldTaxCustomerInvoiceAddPage").val(json.backid);
                    if (json.opertype && json.opertype == "add") {
                        $("#account-buttons-goldTaxCustomerInvoicePage").buttonWidget("addNewDataId", json.backid);
                    }
                }
                else {
                    $.messager.alert("提醒", "暂存失败");
                }
            }
        });
    }
    function goldTaxCustomerInvoice_realSave_form_submit() {
        $("#account-form-goldTaxCustomerInvoiceAddPage").form({
            onSubmit: function () {
                var flag = $(this).form('validate');
                if (flag == false) {
                    return false;
                }
                loading("提交中..");
            },
            success: function (data) {
                loaded();
                var json = $.parseJSON(data);
                if (json.flag == true) {
                    goldTaxCustomerInvoice_RefreshDataGrid();
                    var saveaudit = $("#account-goldTaxCustomerInvoiceAddPage-saveaudit").val();
                    if (saveaudit == "saveaudit") {
                        if (json.auditflag==true) {
                            $.messager.alert("提醒", "保存审核成功");
                        } else {
                            if (json.auditmsg) {
                                $.messager.alert("提醒", "保存成功,审核失败。" + json.auditmsg);
                            } else {
                                $.messager.alert("提醒", "保存成功,审核失败。");
                            }
                        }
                    }else{
                        $.messager.alert("提醒", "保存成功。");
                    }

                    if (json.opertype && json.opertype == "add") {
                        $("#account-buttons-goldTaxCustomerInvoicePage").buttonWidget("addNewDataId", json.backid);
                    }

                    $("#account-backid-goldTaxCustomerInvoiceAddPage").val(json.backid);
                    goldTaxCustomerInvoice_RefreshDataGrid();
                    $("#account-panel-goldTaxCustomerInvoicePage").panel('refresh', 'account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceEditPage.do?id=' + json.backid);

                }
                else {
                    if (json.msg) {
                        $.messager.alert("提醒", "保存失败!" + json.msg);
                    } else {
                        $.messager.alert("提醒", "保存失败");
                    }
                }
            }
        });
    }
    function goldTaxCustomerInvoice_RefreshDataGrid() {
        try {
            tabsWindowURL(pageListUrl).$("#account-table-goldTaxCustomerInvoiceListPage").datagrid('reload');
        } catch (e) {
        }
    }

    function isLockData(id, tname) {
        var flag = false;
        $.ajax({
            url: 'system/lock/unLockData.do',
            type: 'post',
            data: {id: id, tname: tname},
            dataType: 'json',
            async: false,
            success: function (json) {
                flag = json.flag
            }
        });
        return flag;
    }

    function getUniqueArray(arr) {
        var a = [];
        var l = arr.length;
        for (var i = 0; i < l; i++) {
            for (var j = i + 1; j < l; j++) {
                if (arr[i] === arr[j]) j = ++i;
            }
            a.push(arr[i]);
        }
        return a;
    }

    function orderDetailAddDialog() {

        var flag = $("#account-form-goldTaxCustomerInvoiceAddPage").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先完善客户金税开票基本信息');
            return false;
        }
        var businessdate = $("#account-goldTaxCustomerInvoice-businessdate").val() || "";
        if ($.trim(businessdate)=="" ) {
            $.messager.alert("提醒", "请先选择业务日期");
            $("#account-goldTaxCustomerInvoice-businessdate").focus();
            return false;
        }

        $('<div id="account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper-content"></div>').appendTo('#account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper');
        var $DetailOper = $("#account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper-content");

        $DetailOper.dialog({
            title: '开票信息新增(按ESC退出)',
            width: 600,
            height: 440,
            closed: true,
            cache: false,
            modal: true,
            resizable: true,
            href: "account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceDetailAddPage.do",
            onLoad: function () {
                $("#account-goldTaxCustomerInvoiceDetail-goodsname").focus();
            },
            onClose: function () {
                $DetailOper.dialog("destroy");
            }
        });
        $DetailOper.dialog("open");
    }
    function orderDetailEditDialog(initdata) {

        $('<div id="account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper-content"></div>').appendTo('#account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper');
        var $DetailOper = $("#account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper-content");
        $DetailOper.dialog({
            title: '开票信息修改(按ESC退出)',
            width: 600,
            height: 440,
            closed: true,
            cache: false,
            modal: true,
            resizable: true,
            href: "account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceDetailEditPage.do",
            onLoad: function () {
                try {
                    if (initdata != null) {
                        if ($("#account-form-goldTaxCustomerInvoiceDetailEditPage").size() > 0) {
                            $("#account-goldTaxCustomerInvoiceDetail-jstypeid").widget("setValue",initdata.jstypeid);
                            $("#account-form-goldTaxCustomerInvoiceDetailEditPage").form('load', initdata);
                            setTimeout(function(){
                                $("#account-goldTaxCustomerInvoiceDetail-jstypeid").validatebox("validate");
                            },300);

                            if(initdata.sourcetype =="1"){

                                $("#account-goldTaxCustomerInvoiceDetail-taxprice").attr("readonly","readonly");
                                $("#account-goldTaxCustomerInvoiceDetail-taxprice").addClass("readonly");
                                $("#account-goldTaxCustomerInvoiceDetail-notaxprice").attr("readonly","readonly");
                                $("#account-goldTaxCustomerInvoiceDetail-notaxprice").addClass("readonly");

                                $("#account-goldTaxCustomerInvoiceDetail-sourcegoods-tr").show();
                            }else{
                                $("#account-goldTaxCustomerInvoiceDetail-sourcegoods-tr").hide();
                            }
                        }
                    }
                } catch (e) {
                }
                $("#account-goldTaxCustomerInvoiceDetail-goodsname").focus();
                $("#account-goldTaxCustomerInvoiceDetail-goodsname").select();

                formaterNumSubZeroAndDot();

                $("#account-form-goldTaxCustomerInvoiceDetailEditPage").form('validate');
            },
            onClose: function () {
                $DetailOper.dialog("destroy");
            }
        });
        $DetailOper.dialog("open");
    }
    function orderDetailViewDialog(initdata) {
        $('<div id="account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper-content"></div>').appendTo('#account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper');
        var $DetailOper = $("#account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper-content");
        $DetailOper.dialog({
            title: '查看',
            width: 600,
            height: 440,
            closed: true,
            cache: false,
            modal: true,
            resizable: true,
            href: "account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceDetailViewPage.do",
            onLoad: function () {
                if (initdata != null) {
                    if ($("#account-form-goldTaxCustomerInvoiceDetailViewPage").size() > 0) {
                        $("#account-form-goldTaxCustomerInvoiceDetailViewPage").form('load', initdata);
                        setTimeout(function(){
                            $("#account-goldTaxCustomerInvoiceDetail-jstypeid").widget("setValue",initdata.jstypeid);
                            $("#account-goldTaxCustomerInvoiceDetail-jstypeid").validatebox("validate");
                        },300);
                        if(initdata.sourcetype =="1"){
                            $("#account-goldTaxCustomerInvoiceDetail-taxprice").attr("readonly","readonly");
                            $("#account-goldTaxCustomerInvoiceDetail-taxprice").addClass("readonly");
                            $("#account-goldTaxCustomerInvoiceDetail-notaxprice").attr("readonly","readonly");
                            $("#account-goldTaxCustomerInvoiceDetail-notaxprice").addClass("readonly");
                            $("#account-goldTaxCustomerInvoiceDetail-sourcegoods-tr").show();
                        }else{
                            $("#account-goldTaxCustomerInvoiceDetail-sourcegoods-tr").hide();
                        }
                    }
                }
            },
            onClose: function () {
                $DetailOper.dialog("destroy");
            }
        });
        $DetailOper.dialog("open");
    }
    function footerReCalc() {
        var $potable = $("#account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetable");
        var data = $potable.datagrid('getRows');
        if (data == null || data.length == 0) {
            $potable.datagrid('reloadFooter', [
                {goodsname: '合计',  taxprice: '', notaxprice: '', notaxamount: '0', taxamount: '0', tax: '0'}
            ]);
        }
        var unitnum = 0;
        var taxamount = 0;
        var notaxamount = 0;
        var tax = 0;
        for (var i = 0; i < data.length; i++) {
            if (data[i].unitnum) {
                unitnum = Number(unitnum) + Number(data[i].unitnum);
            }
            if (data[i].taxamount) {
                taxamount = Number(taxamount) + Number(data[i].taxamount);
            }
            if (data[i].notaxamount) {
                notaxamount = Number(notaxamount) + Number(data[i].notaxamount);
            }
            if (data[i].tax) {
                tax = Number(tax) + Number(data[i].tax);
            }
        }
        unitnum = String(unitnum);
        taxamount = String(taxamount);
        notaxamount = String(notaxamount);
        tax = String(tax);
        $potable.datagrid('reloadFooter', [
            {
                goodsname: '合计',
                taxprice:'',
                notaxprice:'',
                unitnum: unitnum,
                taxamount: taxamount,
                notaxamount: notaxamount,
                tax: tax
            }
        ]);
    }


    var tableColJson = $("#account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetable").createGridColumnLoad({
        name: 'account_goldtaxcustomerinvoice_detail',
        frozenCol: [[
            {field:'idck',checkbox:true,isShow:true}
        ]],
        commonCol: [[
            {field: 'upid', title: '页面维一编码', width: 10, hidden: true},
            {field: 'id', title: '编码', width: 70, hidden: true, isShow: true},
            {field: 'jstypeid', title: '金税分类编码', width: 100},
            {field: 'goodsid', title: '档案商品编码', width: 80, sortable:true,hidden:true},
            {field: 'goodsname', title: '开票商品名称', width: 220},
            {field: 'sourcegoodsid', title: '客户商品编码', width: 220,hidden:true},
            {field: 'sourcegoodsname', title: '客户商品名称', width: 220, hidden:true},
            {
                field: 'model', title: '规格型号', width: 80,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.model;
                    } else {
                        return value;
                    }
                }
            },
            { field: 'unitname', title: '单位', width: 35 },
            {
                field: 'unitnum', title: '数量', width: 60, align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    return formatterBigNumNoLen(value);
                }
            },
            {
                field: 'taxprice', title: '单价', width: 60, align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    //return formatterMoney(value);
                    if(value!=null && !isNaN(value)) {
                        return Number(value);
                    }else{
                        return value;
                    }
                }
            },
            {
                field: 'taxamount', title: '金额', width: 60, align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'notaxprice', title: '未税单价', width: 60, align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    //return formatterMoney(value);
                    if(value!=null && !isNaN(value)) {
                        return Number(value);
                    }else{
                        return value;
                    }
                }
            },
            {
                field: 'notaxamount', title: '未税金额', width: 60, align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            { field: 'taxrate', title: '税率', width: 80,align:'right',
                formatter: function (value, row, index) {
                    if(value!=null && !isNaN(value)) {
                        return Number(value);
                    }else{
                        return value;
                    }
                }
            },
            {
                field: 'tax', title: '税额', width: 60, align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'taxfreeflag', title: '免税标识', width: 80,
                formatter: function (value, row, index) {
                    if(value==0){
                        return "其他税率";
                    }else if(value==1){
                        return "零税率";
                    }else if(value==2){
                        return "免税";
                    }
                }
            },
            { field: 'remark', title: '备注', width: 150 }
        ]]
    });
    //菜单开始
    $(document).ready(function () {
        $("#account-panel-goldTaxCustomerInvoicePage").panel({
            href: goldTaxCustomerInvoice_url,
            cache: false,
            maximized: true,
            border: false
        });
        //按钮
        $("#account-buttons-goldTaxCustomerInvoicePage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/account/goldtaxcustomerinvoice/goldTaxCustomerInvoiceAddBtn.do">
                {
                    type: 'button-add',
                    handler: function () {
                        $("#account-panel-goldTaxCustomerInvoicePage").panel('refresh', 'account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceAddPage.do');
                    }
                },
                </security:authorize>
                <security:authorize url="/account/goldtaxcustomerinvoice/goldTaxCustomerInvoiceTempSaveBtn.do">
                {
                    type: 'button-hold',
                    handler: function () {
                        $.messager.confirm("提醒", "确定暂存该客户金税开票信息？", function (r) {
                            if (r) {
                                $("#account-goldTaxCustomerInvoiceAddPage-addType").val("temp");
                                var datarows = $("#account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetable").datagrid('getRows');
                                if (datarows != null && datarows.length > 0) {
                                    $("#account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetableDetails").val(JSON.stringify(datarows));
                                }
                                goldTaxCustomerInvoice_tempSave_form_submit();
                                $("#account-form-goldTaxCustomerInvoiceAddPage").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/goldtaxcustomerinvoice/goldTaxCustomerInvoiceRealSaveBtn.do">
                {
                    type: 'button-save',
                    handler: function () {

                        if (checkGoodsDetailEmpty()) {
                            return false;
                        }
                        $.messager.confirm("提醒", "确定保存该客户金税开票信息？", function (r) {
                            if (r) {
                                $("#account-goldTaxCustomerInvoiceAddPage-addType").val("real");
                                var datarows = $("#account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetable").datagrid('getRows');
                                if (datarows != null && datarows.length > 0) {
                                    $("#account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetableDetails").val(JSON.stringify(datarows));
                                }
                                goldTaxCustomerInvoice_realSave_form_submit();
                                $("#account-form-goldTaxCustomerInvoiceAddPage").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/goldtaxcustomerinvoice/goldTaxCustomerInvoiceSaveAuditBtn.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        var checkOptions={
                            checkJsTypeid:true,
                            operfrom:"audit"
                        };
                        if (checkGoodsDetailEmpty(checkOptions)) {
                            return false;
                        }
                        $.messager.confirm("提醒", "确定保存并审核进货单信息？", function (r) {
                            if (r) {
                                $("#account-goldTaxCustomerInvoiceAddPage-addType").val("real");
                                $("#account-goldTaxCustomerInvoiceAddPage-saveaudit").val("saveaudit");
                                var datarows = $("#account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetable").datagrid('getRows');
                                if (datarows != null && datarows.length > 0) {
                                    $("#account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetableDetails").val(JSON.stringify(datarows));
                                }
                                goldTaxCustomerInvoice_realSave_form_submit();
                                $("#account-form-goldTaxCustomerInvoiceAddPage").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/goldtaxcustomerinvoice/goldTaxCustomerInvoiceGiveUpBtn.do">
                {
                    type: 'button-giveup',//放弃
                    handler: function () {
                        var $polbuttons = $("#account-buttons-goldTaxCustomerInvoicePage");
                        var type = $polbuttons.buttonWidget("getOperType");
                        if (type == "add") {
                            var id = $("#account-backid-goldTaxCustomerInvoiceAddPage").val();
                            if (id == "") {
                                tabsWindowTitle(pageListUrl);
                            } else {
                                $("#account-panel-goldTaxCustomerInvoicePage").panel('refresh', 'account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceViewPage.do?id=' + id);
                            }
                        } else if (type == "edit") {
                            var id = $("#account-backid-goldTaxCustomerInvoiceAddPage").val();
                            if (id == "") {
                                return false;
                            }
                            var flag = isLockData(id, "t_account_goldtaxcustomerinvoice");
                            if (!flag) {
                                $.messager.alert("警告", "该数据正在被其他人操作，暂不能修改！");
                                return false;
                            }
                            $("#account-panel-goldTaxCustomerInvoicePage").panel('refresh', 'account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceViewPage.do?id=' + id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/account/goldtaxcustomerinvoice/goldTaxCustomerInvoiceDeleteBtn.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        var id = $("#account-backid-goldTaxCustomerInvoiceAddPage").val();
                        if (id == "") {
                            return false;
                        }
                        $.messager.confirm("提醒", "是否删除该客户金税开票信息？", function (r) {
                            if (r) {
                                loading("删除中..");
                                $.ajax({
                                    url: 'account/goldtaxcustomerinvoice/deleteGoldTaxCustomerInvoice.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "删除成功");
                                            var nextdata = $("#account-buttons-goldTaxCustomerInvoicePage").buttonWidget("removeData", "");
                                            if (null != nextdata && nextdata.id && nextdata.id != "") {
                                                $("#account-backid-goldTaxCustomerInvoiceAddPage").val(nextdata.id);
                                                $("#account-panel-goldTaxCustomerInvoicePage").panel('refresh', 'account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceEditPage.do?id=' + nextdata.id);
                                                goldTaxCustomerInvoice_RefreshDataGrid();
                                            } else {
                                                $("#account-backid-goldTaxCustomerInvoiceAddPage").val("");
                                                //$("#account-panel-goldTaxCustomerInvoicePage").panel('refresh', 'account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceAddPage.do');
                                                goldTaxCustomerInvoice_RefreshDataGrid();
                                                parent.closeNowTab();
                                            }
                                        } else {
                                            $.messager.alert("提醒", "删除失败");
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/goldtaxcustomerinvoice/goldTaxCustomerInvoiceAuditBtn.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        var id = $("#account-backid-goldTaxCustomerInvoiceAddPage").val();
                        if (id == "") {
                            $.messager.alert("提醒", "未找到相关单据编号");
                            return false;
                        }
                        var checkOptions={
                            checkJsTypeid:true,
                            operfrom:"audit"
                        };
                        if (checkGoodsDetailEmpty(checkOptions)) {
                            return false;
                        }
                        $.messager.confirm("提醒", "是否审核通过该客户金税开票信息？", function (r) {
                            if (r) {
                                loading("审核中..");
                                $.ajax({
                                    url: 'account/goldtaxcustomerinvoice/auditGoldTaxCustomerInvoice.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            //$("#account-goldTaxCustomerInvoiceAddPage-status").val("3");

                                            //$("#account-buttons-goldTaxCustomerInvoicePage").buttonWidget("setDataID", {id:id, state:'3', type:'view'});
                                            $.messager.alert("提醒", "审核成功");
                                            goldTaxCustomerInvoice_RefreshDataGrid();
                                            $("#account-panel-goldTaxCustomerInvoicePage").panel('refresh', 'account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceEditPage.do?id=' + id);
                                        } else {
                                            if (json.msg) {
                                                $.messager.alert("提醒", "审核失败!" + json.msg);
                                            } else {
                                                $.messager.alert("提醒", "审核失败");
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/goldtaxcustomerinvoice/goldTaxCustomerInvoiceOppauditBtn.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        var id = $("#account-backid-goldTaxCustomerInvoiceAddPage").val();
                        if (id == "") {
                            $.messager.alert("提醒", "未找到相关单据编号");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否反审该客户金税开票信息？", function (r) {
                            if (r) {
                                loading("反审核中..");
                                $.ajax({
                                    url: 'account/goldtaxcustomerinvoice/oppauditGoldTaxCustomerInvoice.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            //$("#account-goldTaxCustomerInvoiceAddPage-status").val("3");

                                            //$("#account-buttons-goldTaxCustomerInvoicePage").buttonWidget("setDataID", {id:id, state:'3', type:'view'});
                                            $.messager.alert("提醒", "反核成功");
                                            goldTaxCustomerInvoice_RefreshDataGrid();
                                            $("#account-panel-goldTaxCustomerInvoicePage").panel('refresh', 'account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceEditPage.do?id=' + id);
                                        } else {
                                            if (json.msg) {
                                                $.messager.alert("提醒", "反核失败!" + json.msg);
                                            } else {
                                                $.messager.alert("提醒", "反核失败");
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/goldtaxcustomerinvoice/goldTaxCustomerInvoiceBackBtn.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        if (data != null && data.id != null && data.id != "") {
                            $("#account-backid-goldTaxCustomerInvoiceAddPage").val(data.id);
                            $("#account-panel-goldTaxCustomerInvoicePage").panel('refresh', 'account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceEditPage.do?id=' + data.id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/account/goldtaxcustomerinvoice/goldTaxCustomerInvoiceNextBtn.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        if (data != null && data.id != null && data.id != "") {
                            $("#account-backid-goldTaxCustomerInvoiceAddPage").val(data.id);
                            $("#account-panel-goldTaxCustomerInvoicePage").panel('refresh', 'account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceEditPage.do?id=' + data.id);
                        }
                    }
                },
                </security:authorize>
                {}
            ],

            buttons: [
                <security:authorize url="/account/goldtaxcustomerinvoice/goldTaxCustomerInvoiceMoreMenuBtn.do">
                {
                    id: 'moreMenuButton',
                    type: 'menu',
                    name: '更多',
                    iconCls: 'button-export',
                    button: [
                        <security:authorize url="/account/goldtaxcustomerinvoice/goldTaxCustomerInvoiceEditInvoiceInfoBtn.do">
                        {
                            id:'button-more-invoiceno',
                            name:'编辑发票号',
                            iconCls:'button-edit',
                            handler:function(){
                                var id = $("#account-backid-goldTaxCustomerInvoiceAddPage").val();
                                if (id == "") {
                                    $.messager.alert("提醒", "未找到相关单据编号");
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
                                        id:id,
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
                    ]
                },
                </security:authorize>
                <security:authorize url="/account/goldtaxcustomerinvoice/goldTaxCustomerInvoiceImExportMenuBtn.do">
                {
                    id: 'imexportMenuButton',
                    type: 'menu',
                    name: '导入导出',
                    iconCls: 'button-export',
                    button: [
                        <security:authorize url="/account/goldtaxcustomerinvoice/goldTaxCustomerInvoiceImportCustomerDetailDataBtn.do">
                        {
                            id:'button-import-kpdetail',
                            name:'导入客户开票数据',
                            iconCls:'button-export',
                            handler: function(){
                                var billid=$("#account-backid-goldTaxCustomerInvoiceAddPage").val() || "";
                                billid= $.trim(billid);
                                if(billid==""){
                                    $.messager.alert("提醒", "抱歉，新建的单据请先保存后，才能进行导入客户开票数据。");
                                    return false;
                                }

                                $.messager.confirm("提醒", "导入成功会清除当前已经存在明细数据，<br/>是否进行导入操作？", function (r) {
                                    if (r) {
                                        var msg = "<span style=\"color:#000;\">注意事项如下：</span><br/><span style=\"color:#000;\">一、导入字段有“<b>金税分类编码</b>”,“客户商品代码”，“<b style=\"color:#FF0000;\">商品名称</b>”，“规格型号”，“<b style=\"color:#FF0000;\">计量单位</b>”，“<b style=\"color:#FF0000;\">数量</b>”，“<b>含税单价</b>”，“<b style=\"color:#FF0000;\">含税金额</b>”，“<b style=\"color:#FF0000;\">税率</b>”。</span>";
                                        msg = msg + "<span style=\"color:#000;\">其中，<span style=\"color:#FF0000;\">红字</span>为必填，且字段名称必需要一致。</span>";
                                        msg = msg + "<br/><span style=\"color:#000;\">二、系统会重新计算“含税金税”，“税额”，导入后请仔细核对金额。</span>";
                                        $("#account-buttons-importCustomerGoldTaxInvoiceData-click").Excel('import', {
                                            type: 'importUserdefined',
                                            importparam: msg,//参数描述
                                            version: '1',//导入页面显示哪个版本1
                                            fieldParam: {billid: billid},
                                            url: 'account/goldtaxcustomerinvoice/importCustomerGoldTaxInvoiceData.do',
                                            onClose: function () { //导入成功后窗口关闭时操作，;
                                                $("#account-panel-goldTaxCustomerInvoicePage").panel('refresh', getGoldTaxCustomerPanelUrl("edit"));
                                                goldTaxCustomerInvoice_RefreshDataGrid();
                                            }
                                        });
                                        $("#account-buttons-importCustomerGoldTaxInvoiceData-click").trigger("click");
                                    }
                                });
                            }
                        },
                        {
                            type: 'menu-sep'
                        },
                        </security:authorize>
                        <security:authorize url="/account/goldtaxcustomerinvoice/exportGoldTaxCustomerInvoiceXMLForHTKPBtn.do">
                        {
                            id: 'button-export-xml-htkp',
                            name: '航天XML格式导出',
                            iconCls: 'button-export',
                            handler: function () {
                                var id = $("#account-backid-goldTaxCustomerInvoiceAddPage").val();
                                if (id == "") {
                                    $.messager.alert("提醒", "抱歉，未能找到相当单据");
                                    return false;
                                }
                                var status = $("#account-goldTaxCustomerInvoice-status").val();
                                if (status != '3' && status != '4') {
                                    $.messager.alert("提醒", "编号" + id + "审核通过后的单据才能生成航天XML文件");
                                    return false;
                                }
                                $("#account-goldTaxCustomerInvoicePage-htkp-form-exportid").val(id);
                                $("#account-goldTaxCustomerInvoicePage-htkp-form-title-tr").show();
                                $("#account-goldTaxCustomerInvoicePage-htkp-form-jsgoodsversion-tr").show();
                                $("#account-goldTaxCustomerInvoicePage-htkp-form-jsgoodsversion").validatebox({required:true});
                                var title=$("#account-goldTaxCustomerInvoice-invoicecustomername").val()|| "";
                                if(title!=""){
                                    title=title+"_";
                                }
                                title=title+id+"(航天开票XML导入格式)";
                                $("#account-goldTaxCustomerInvoicePage-htkp-form-title").val(title);
                                var exporttimes = $("#account-goldTaxCustomerInvoice-hid-jxexporttimes").val() || 0;
                                var onClickHandleFunc = function () {
                                    if ($("#account-goldTaxCustomerInvoicePage-htkp-form").form("validate")) {
                                        var formdata = $("#account-goldTaxCustomerInvoicePage-htkp-form").serializeJSON();
                                        formdata.oldFromData = "";
                                        delete formdata.oldFromData;
                                        loading("文件导出中..");
                                        $.ajax({
                                            url: 'account/goldtaxcustomerinvoice/exportGoldTaxCustomerInvoiceXMLForHTKP.do',
                                            data:formdata,
                                            type: 'post',
                                            dataType: 'json',
                                            success: function (json) {
                                                loaded();
                                                if (json.flag==true) {
                                                    $("#account-goldTaxCustomerInvoice-hid-jxexporttimes").val(parseInt(exporttimes)+1);

                                                    var msg="》》客户金税开票金税XML文件生成成功《《";
                                                    if(json.datafileid != null && json.datafileid != ''){
                                                        msg =msg + "<br/><span style=\"text-align: center\"><a href=\"common/download.do?id="+json.datafileid+"\" target=\"_blank\" style='font-weight: bold'>》点击下载文件《</a></span>";
                                                    }
                                                    if(json.amountmsg && json.amountmsg != ""){
                                                        msg=msg+"<br/>"+json.amountmsg;
                                                    }
                                                    easyuiMessagerAlert({
                                                        width:500,
                                                        title:"提醒",
                                                        msg:msg,
                                                        icon:'info',
                                                        fn:function () {
                                                        	$.messager.alert("提醒", "别忘记下载文件！！");
                                                        },
                                                        onOpen:function () {
                                                            $(".messager-button").css("visibility","hidden");
                                                            setTimeout(function () {
                                                                $(".messager-button").css("visibility","hidden");
                                                            },400);
                                                        }
                                                    });
                                                } else {
                                                    var msg="金税XML文件生成失败";

                                                    if (json.msg) {
                                                        msg=msg+json.msg;
                                                    }
                                                    var msgkind="1";
                                                    if(json.datafileid != null && json.datafileid != ''){
                                                        msgkind="2";
                                                        msg =msg + "<br/><span style=\"text-align: center\"><a href=\"common/download.do?id="+json.datafileid+"\" target=\"_blank\" style='font-weight: bold'>》点击下载文件《</a></span>";
                                                    }
                                                    if("2"==msgkind){
                                                        easyuiMessagerAlert({
                                                            width:500,
                                                            title:"提醒",
                                                            msg:msg,
                                                            icon:'info'
                                                        });
                                                    }else {
                                                        $.messager.alert("提醒", msg);
                                                    }
                                                }
                                            },
                                            error:function(){
                                                loaded();
                                                $.messager.alert("错误","抱歉，处理异常");
                                            }
                                        });

                                        $("#account-goldTaxCustomerInvoicePage-htkpdialogdiv").dialog("close");
                                        return true;
                                    }
                                    return false;
                                }
                                if (exporttimes > 0) {
                                    $.messager.confirm("提醒", "该单据已经导出过" + exporttimes + "次<br/>是否再次导出？", function (r) {
                                        if (r) {
                                            showHTKPExportDailog("account-goldTaxCustomerInvoicePage-htkpdialogdiv", "航天XML格式导出", onClickHandleFunc);
                                        }
                                    });
                                } else {
                                    showHTKPExportDailog("account-goldTaxCustomerInvoicePage-htkpdialogdiv", "航天XML格式导出", onClickHandleFunc);
                                }

                            }
                        },
                        {
                            type: 'menu-sep'
                        },
                        </security:authorize>
                        <security:authorize url="/account/receivable/updateJSKPSysParamConfigBtn.do">
                        {
                            id:'button-id-config',
                            name:'金税人员参数配置',
                            iconCls:'button-edit',
                            handler:function(){
                                $('<div id="account-dialog-showJSKPSysParamConfigOper-content"></div>').appendTo("#account-dialog-showJSKPSysParamConfigOper");
                                $('#account-dialog-showJSKPSysParamConfigOper-content').dialog({
                                    title: '金税收款人和复核人系统参数配置',
                                    //fit:true,
                                    width:480,
                                    height:250,
                                    closed: true,
                                    cache: false,
                                    href: 'account/receivable/showJSKPSysParamConfigPage.do',
                                    maximizable:true,
                                    resizable:true,
                                    modal: true,
                                    onLoad:function(){
                                    },
                                    onClose:function(){
                                        $('#account-dialog-showJSKPSysParamConfigOper-content').dialog("destroy");
                                    }
                                });
                                $('#account-dialog-showJSKPSysParamConfigOper-content').dialog('open');
                            }
                        },
                        {
                            type: 'menu-sep'
                        },
                        </security:authorize>
                        {}
                    ]
                },
                </security:authorize>
                {}
            ],
            layoutid: 'account-goldTaxCustomerInvoicePage-layout',
            model: 'bill',
            type: 'view',
            taburl: pageListUrl,
            id: '${id}',
            datagrid: 'account-table-goldTaxCustomerInvoiceListPage',
            tname: 't_account_goldtaxcustomerinvoice'
        });

        $(document).bind('keydown', 'esc', function () {
            $("#account-goldTaxCustomerInvoiceDetail-remark").focus();
            if ($("#account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper-content").size() > 0) {
                $("#account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper-content").dialog("close");
            }
        });
        $(document).bind('keydown', 'ctrl+enter', function () {
            $("#account-goldTaxCustomerInvoiceDetail-remark").focus();
            $("#account-goldTaxCustomerInvoiceDetailAddPage-addSaveGoOn").focus();
            $("#account-goldTaxCustomerInvoiceDetailEditPage-editSaveGoOn").focus();
            setTimeout(function () {
                $("#account-goldTaxCustomerInvoiceDetailAddPage-addSaveGoOn").trigger("click");
                $("#account-goldTaxCustomerInvoiceDetailEditPage-editSaveGoOn").trigger("click");
            }, 200);
            return false;
        });
        $(document).bind('keydown', 'alt+n', function () {
            $("#account-goldTaxCustomerInvoiceDetail-remark").focus();
            $("#account-goldTaxCustomerInvoiceDetailEditPage-editSave").focus();
            setTimeout(function () {
                $("#account-goldTaxCustomerInvoiceDetailEditPage-editSave").trigger("click");
            }, 200);
            return false;
        });

        $("#account-dialog-goldTaxCustomerInvoiceDetail-goodsOper-goodsid").widget({
            width: 250,
            referwid: 'RL_GOODS_INFO_GOLDTAX',
            singleSelect: true,
            onSelect: function (data) {

            }
        });

        $("#account-goldTaxCustomerInvoicePage-batcheditjstype-dailogdiv-jstypeid").widget({
            referwid:'RL_T_BASE_JSTAXTYPECODE',
            singleSelect:true,
            width:'200',
            required:true,
            onSelect: function(data) {
            }
        });
    });
    function resetDetailGoldTaxGoodsInfoDialog(){
        $("#account-dialog-goldTaxCustomerInvoiceDetail-goodsOper-goodsid").widget("clear");
        $("#account-dialog-goldTaxCustomerInvoiceDetail-goodsOper-form").form("reset");
        $(".goodsOperChecks").each(function(){
            if($(this).attr("checked")){
                $(this)[0].checked = true;
            }
        });
    }
    function showDetailGoldTaxGoodsInfoDialog(){
        resetDetailGoldTaxGoodsInfoDialog();
        var $detailOper=$("#account-dialog-goldTaxCustomerInvoiceDetail-goodsOper");
        $detailOper.dialog({
            title: '商品档案信息填入',
            width: 400,
            height: 250,
            closed: true,
            cache: false,
            modal: true,
            resizable: true,
            onOpen: function () {
                $("#account-dialog-goldTaxCustomerInvoiceDetail-goodsOper-goodsid").focus();
            },
            buttons:[
                {
                    text:'确定',
                    handler:function(){
                        var data=$("#account-dialog-goldTaxCustomerInvoiceDetail-goodsOper-goodsid").widget("getObject") || {};
                        $("#account-goldTaxCustomerInvoiceDetail-goodsid").val(data.goodsid || "");
                        var $ckname = $("#account-dialog-goldTaxCustomerInvoiceDetail-goodsOper-ck-name");
                        if($ckname.prop("checked")==true){
                            $("#account-goldTaxCustomerInvoiceDetail-goodsname").val(data.goodsname||"");
                            setTimeout(function(){
                                $("#account-goldTaxCustomerInvoiceDetail-goodsname").validatebox("validate");
                            },100);
                        }
                        $ckname = $("#account-dialog-goldTaxCustomerInvoiceDetail-goodsOper-ck-jstypeid");
                        if($ckname.prop("checked")==true){
                            $("#account-goldTaxCustomerInvoiceDetail-jstypeid").widget("setValue",(data.jstypeid||""));
                            setTimeout(function(){
                                $("#account-goldTaxCustomerInvoiceDetail-jstypeid").validatebox("validate");
                            },100);
                        }
                        $ckname = $("#account-dialog-goldTaxCustomerInvoiceDetail-goodsOper-ck-model");
                        if($ckname.prop("checked")==true){
                            $("#account-goldTaxCustomerInvoiceDetail-model").val(data.model||"");
                        }
                        $ckname = $("#account-dialog-goldTaxCustomerInvoiceDetail-goodsOper-ck-unitname");
                        if($ckname.prop("checked")==true){
                            $("#account-goldTaxCustomerInvoiceDetail-unitname").val(data.unitname||"");
                            setTimeout(function(){
                                $("#account-goldTaxCustomerInvoiceDetail-unitname").validatebox("validate");
                            },100);
                        }
                        $ckname = $("#account-dialog-goldTaxCustomerInvoiceDetail-goodsOper-ck-taxrate");
                        if($ckname.prop("checked")==true){
                            $("#account-goldTaxCustomerInvoiceDetail-taxrate").val(data.taxrate||"");
                            setTimeout(function(){
                                $("#account-goldTaxCustomerInvoiceDetail-taxrate").validatebox("validate");
                            },100);
                            if(data.taxrate==0 ){
                                if(data.jsflag==0) {
                                    $("#account-goldTaxCustomerInvoiceDetail-taxfreeflag").val("1");
                                }else if(data.jsflag==1){
                                    $("#account-goldTaxCustomerInvoiceDetail-taxfreeflag").val("2");
                                }
                            }else{
                                $("#account-goldTaxCustomerInvoiceDetail-taxfreeflag").val("0");
                            }
                            try {
                                unitnumChange();
                            }catch (e){
                            }
                        }
                        $detailOper.dialog('close');
                    }
                },
                {
                    text:'重置',
                    handler:function(){
                        resetDetailGoldTaxGoodsInfoDialog();
                    }
                }
            ],
        });
        $detailOper.dialog("open");
    }
    //主单位
    $("#account-dialog-goldTaxCustomerInvoiceDetail-unitnameOper-unitid").widget({
        referwid:'RL_T_BASE_GOODS_METERINGUNIT',
        singleSelect:true,
        width:150,
        onlyLeafCheck:true
    });
    function resetDetailUnitnameInfoDialog(){
        $("#account-dialog-goldTaxCustomerInvoiceDetail-unitnameOper-unitid").widget("clear");
    }
    function showDetailUnitnameInfoDialog(){
        resetDetailUnitnameInfoDialog();
        var $detailOper=$("#account-dialog-goldTaxCustomerInvoiceDetail-unitnameOper");
        $detailOper.dialog({
            title: '计量单位档案信息填入',
            width: 400,
            height: 150,
            closed: true,
            cache: false,
            modal: true,
            resizable: true,
            onOpen: function () {
                $("#account-dialog-goldTaxCustomerInvoiceDetail-unitnameOper-unitid").focus();
            },
            buttons:[
                {
                    text:'确定',
                    handler:function(){
                        var data=$("#account-dialog-goldTaxCustomerInvoiceDetail-unitnameOper-unitid").widget("getObject") || {};
                        $("#account-goldTaxCustomerInvoiceDetail-unitname").val(data.name || "");
                        $detailOper.dialog('close');
                    }
                },
                {
                    text:'重置',
                    handler:function(){
                        resetDetailUnitnameInfoDialog();
                    }
                }
            ],
        });
        $detailOper.dialog("open");
    }

    //回车跳到下一个
    var chooseNo;
    function frm_focus(val) {
        chooseNo = val;
    }
    function frm_blur(val) {
        if (val == chooseNo) {
            chooseNo = "";
        }
    }
    $(document).keydown(function (event) {//alert(event.keyCode);
        switch (event.keyCode) {
            case 13: //Enter
                if (chooseNo == "goldTaxCustomerInvoice.invoicecustomername") {
                    $("input[name='goldTaxCustomerInvoice.customertaxno']").focus();
                    return false;
                }
                if (chooseNo == "goldTaxCustomerInvoice.invoicecustomername") {
                    $("input[name='goldTaxCustomerInvoice.customertaxno']").focus();
                    return false;
                }
                if (chooseNo == "goldTaxCustomerInvoice.customertaxno") {
                    $("input[name='goldTaxCustomerInvoice.customeraddr']").focus();
                    return false;
                }
                if (chooseNo == "goldTaxCustomerInvoice.customeraddr") {
                    $("input[name='goldTaxCustomerInvoice.customerphone']").focus();
                    return false;
                }
                if (chooseNo == "goldTaxCustomerInvoice.customerphone") {
                    $("input[name='goldTaxCustomerInvoice.customercardno']").focus();
                    return false;
                }
                if (chooseNo == "goldTaxCustomerInvoice.customercardno") {
                    $("input[name='goldTaxCustomerInvoice.invoiceno']").focus();
                    return false;
                }
                if (chooseNo == "goldTaxCustomerInvoice.invoiceno") {
                    $("input[name='goldTaxCustomerInvoice.invoicecode']").focus();
                    return false;
                }
                if (chooseNo == "goldTaxCustomerInvoice.invoicecode") {
                    $("input[name='goldTaxCustomerInvoice.remark']").focus();
                    return false;
                }
                if (chooseNo == "goldTaxCustomerInvoice.remark") {
                    $("input[name='goldTaxCustomerInvoice.remark']").blur();
                    orderDetailAddDialog();
                }
                break;
        }
    });
    function getAddRowIndex(){
        var $potable=$("#account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetable");
        var dataRows=$potable.datagrid('getRows');

        var rindex=0;
        for(rindex=0;rindex<dataRows.length;rindex++){
            if(dataRows[rindex].goodsname==null || dataRows[rindex].goodsname==""){
                break;
            }
        }
        if(rindex==dataRows.length){
            $potable.datagrid('appendRow',{});
        }
        return rindex;
    }

    //通过总数量 计算数量 金额换算
    function unitnumChange(){
        var unitnum = $("#account-goldTaxCustomerInvoiceDetail-unitnum").val();
        var taxprice = $("#account-goldTaxCustomerInvoiceDetail-taxprice").val();
        var taxrate = $("#account-goldTaxCustomerInvoiceDetail-taxrate").val();
        $("#account-goldTaxCustomerInvoiceDetail-unitnum").css({'background':'url(image/loading.gif) right top no-repeat'});

        $.ajax({
            url :'account/goldtaxcustomerinvoice/getJsUnitnumChanger.do',
            type:'post',
            data:{unitnum:unitnum,taxprice:taxprice,taxrate:taxrate},
            dataType:'json',
            async:false,
            success:function(json){
                $("#account-goldTaxCustomerInvoiceDetail-notaxprice").val(json.notaxprice);
                if($.trim(taxprice)=="") {
                    $("#account-goldTaxCustomerInvoiceDetail-taxprice").val(json.taxprice);
                }
                if($.trim(unitnum)=="") {
                    $("#account-goldTaxCustomerInvoiceDetail-unitnum").val(json.unitnum);
                }
                $("#account-goldTaxCustomerInvoiceDetail-taxamount").val(json.taxamount);
                $("#account-goldTaxCustomerInvoiceDetail-notaxamount").val(json.notaxamount);
                $("#account-goldTaxCustomerInvoiceDetail-tax").numberbox("setValue",json.tax);

                $("#account-goldTaxCustomerInvoiceDetail-unitnum").css({'background':''});
            }
        });
    }
    function priceChange(type, id){ //1含税单价或2未税单价改变计算对应数据
        var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
        var price = $(id).val();
        var taxrate = $("#account-goldTaxCustomerInvoiceDetail-taxrate").val();
        var unitnum = $("#account-goldTaxCustomerInvoiceDetail-unitnum").val();
        $.ajax({
            url:'account/goldtaxcustomerinvoice/getJsPriceChanger.do',
            dataType:'json',
            async:false,
            type:'post',
            data:'type='+ type +'&price='+ price +'&taxrate='+ taxrate +'&unitnum='+ unitnum,
            success:function(json){
                if($.trim(unitnum)==""){
                    $("#account-goldTaxCustomerInvoiceDetail-unitnum").val(json.unitnum);
                }
                $("#account-goldTaxCustomerInvoiceDetail-taxprice").val(json.taxprice);
                $("#account-goldTaxCustomerInvoiceDetail-taxamount").val(json.taxamount);
                $("#account-goldTaxCustomerInvoiceDetail-notaxprice").val(json.notaxprice);
                $("#account-goldTaxCustomerInvoiceDetail-notaxamount").val(json.notaxamount);
                $("#account-goldTaxCustomerInvoiceDetail-tax").numberbox('setValue',json.tax);
                $this.css({'background':''});
            }
        });
    }
    function amountChange(type, id){ //1含税金额或2未税金额改变计算对应数据
        var $this = $("#"+id);
        $this.css({'background':'url(image/loading.gif) right top no-repeat'});
        var price = $this.val();
        var unitnum = $("#account-goldTaxCustomerInvoiceDetail-unitnum").val();
        var taxrate = $("#account-goldTaxCustomerInvoiceDetail-taxrate").val();
        $.ajax({
            url:'account/goldtaxcustomerinvoice/getJsAmountChanger.do',
            dataType:'json',
            async:false,
            type:'post',
            data:'type='+ type +'&amount='+ price +'&taxrate='+ taxrate +'&unitnum='+ unitnum,
            success:function(json){
                if($.trim(unitnum)==""){
                    $("#account-goldTaxCustomerInvoiceDetail-unitnum").val(json.unitnum);
                }
                $("#account-goldTaxCustomerInvoiceDetail-taxprice").val(json.taxprice);
                $("#account-goldTaxCustomerInvoiceDetail-taxamount").val(json.taxamount);
                $("#account-goldTaxCustomerInvoiceDetail-notaxprice").val(json.notaxprice);
                $("#account-goldTaxCustomerInvoiceDetail-notaxamount").val(json.notaxamount);
                $("#account-goldTaxCustomerInvoiceDetail-tax").numberbox('setValue',json.tax);
                $this.css({'background':''});
            }
        });
    }
    function checkGoodsDetailEmpty(options){
        if(typeof(options)=="undefined" || !$.isPlainObject(options)){
            options={};
        }

        var rows =  $("#account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetable").datagrid('getRows');
        var count = 0;
        loading("检查明细数据中..");
        for(var i=0;i<rows.length;i++){
            if(rows[i].goodsname!=null && rows[i].goodsname!=""){
                count ++;
                if(options.checkJsTypeid!=null && options.checkJsTypeid==true) {
                    if (rows[i].jstypeid == null || rows[i].jstypeid == "") {
                        loaded();
                        $.messager.alert("提醒", rows[i].goodsname + "金税编码不能为空");
                        return true;
                    }
                }
            }
        }
        loaded();
        if(count>0){
            return false;
        }else if(options.operfrom=='audit'){
            $.messager.alert("提醒", "抱歉，请填写客户金税开票商品信息");
            return true;
        } else if(goldTaxCustomerInvoice_type=="" || goldTaxCustomerInvoice_type=="add"){
            return false;
        }else {
            $.messager.alert("提醒", "抱歉，请填写客户金税开票商品信息");
            return true;
        }
    }
    function getGridRowStyle(rowData){
        if(rowData==null){
            return "";
        }
        var atmp=$.trim(rowData.jstypeid  || "");
        var goodsname=$.trim(rowData.goodsname || "");
        if(goodsname==""){
            return "";
        }
        if(atmp==""){
            return "background-color:#FFD9EC;";
        }
        atmp=$.trim(rowData.taxrate  || "");
        if(atmp==0){
            if(rowData.taxfreeflag==2){
                return "background-color:#CEFFCE;";
            }
            return "background-color:#B5BEFF";
        }
        return "";
    }

</script>

<script type="text/javascript">

    //关联人员列表
    $("#account-goldTaxCustomerInvoicePage-htkp-form-receipter").widget({
        width: '180',
        required: true,
        referwid: 'RT_T_BASE_PERSONNEL',
        onlyLeafCheck: true,
        singleSelect: true,
        onChecked: function () {
            setTimeout(function () {
                $("#account-goldTaxCustomerInvoicePage-htkp-form-receipter").validatebox('validate');
            }, 100);
        }
    });
    //关联人员列表
    $("#account-goldTaxCustomerInvoicePage-htkp-form-checker").widget({
        width: '180',
        required: true,
        referwid: 'RT_T_BASE_PERSONNEL',
        onlyLeafCheck: true,
        singleSelect: true,
        onChecked: function () {
            setTimeout(function () {
                $("#account-goldTaxCustomerInvoicePage-htkp-form-checker").validatebox('validate');
            }, 50);
        }
    });
    var showHTKPExportDailog = function (dialogid, title, onClickHandleFunc) {
        title = title || "航天开票接口";
        $('#' + dialogid).dialog({
            title: title,
            width: 350,
            height: 250,
            closed: false,
            cache: false,
            modal: true,
            buttons: [
                {
                    text: '确定',
                    iconCls: 'button-ok',
                    handler: function () {
                        try {
                            var flag = true;
                            if (onClickHandleFunc != null && typeof(onClickHandleFunc) == "function") {
                                flag = onClickHandleFunc() || true;
                            }
                            return flag;
                        } catch (e) {
                            return true;
                        }
                        return false;
                    }
                },

                {
                    text: '取消',
                    iconCls: 'button-cancel',
                    handler: function () {
                        $('#' + dialogid).dialog("close");
                        return false;
                    }
                }
            ]
        });
        $('#' + dialogid).dialog("open");
    }
</script>

</body>
</html>
