<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>采购</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<input type="hidden" id="purchase-backid-plannedOrderAddPage" value="${id }"/>
<div id="purchase-plannedOrderPage-layout" class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="purchase-buttons-plannedOrderPage"></div>
    </div>
    <div data-options="region:'center'">
        <div class="easyui-panel" data-options="fit:true" id="purchase-panel-plannedOrderPage">
        </div>
    </div>
</div>
<div style="display:none">
    <div id="purchase-wfdialog-plannedOrderPage"></div>
    <div id="purchase-plannedOrderAddPage-dialog-DetailOper"></div>
    <div id="purchase-plannedOrderPage-dialog-planorderAnalysis"></div>
    <div id="purchase-goods-history-price"></div>
</div>
<script type="text/javascript">
    var plannedOrder_type = '${type}';
    plannedOrder_type = $.trim(plannedOrder_type.toLowerCase());
    if (plannedOrder_type == "") {
        plannedOrder_type = 'add';
    }
    var plannedOrder_url = "purchase/planorder/plannedOrderAddPage.do";
    if (plannedOrder_type == "view" || plannedOrder_type == "show" || plannedOrder_type == "handle") {
        plannedOrder_url = "purchase/planorder/plannedOrderEditPage.do?id=${id}";
    }
    if (plannedOrder_type == "edit") {
        plannedOrder_url = "purchase/planorder/plannedOrderEditPage.do?id=${id}";
    }
    if (plannedOrder_type == "copy") {
        plannedOrder_url = "purchase/planorder/plannedOrderCopyPage.do?id=${id}";
    }
    var pageListUrl = "/purchase/planorder/plannedOrderListPage.do";
    function plannedOrder_tempSave_form_submit() {
        $("#purchase-form-plannedOrderAddPage").form({
            onSubmit: function () {
                loading("提交中..");
            },
            success: function (data) {
                loaded();
                var json = $.parseJSON(data);
                if (json.flag == true) {
                    plannedOrder_RefreshDataGrid();
                    $.messager.alert("提醒", "暂存成功");
                    $("#purchase-backid-plannedOrderAddPage").val(json.backid);
                    if (json.opertype && json.opertype == "add") {
                        $("#purchase-buttons-plannedOrderPage").buttonWidget("addNewDataId", json.backid);
                    }
                    //$("#purchase-panel-plannedOrderPage").panel('refresh', 'purchase/planorder/plannedOrderEditPage.do?id='+ json.backid);
                    $("#purchase-plannedOrderAddPage-status").val("1");

                }
                else {
                    $.messager.alert("提醒", "暂存失败");
                }
            }
        });
    }

    //变更所有商品要求到货日期
    function changeArriveDate() {
        var arriveDate = $("#purchase-plannedOrderAddPage-arrivedate").val();
        var dataRows = $("#purchase-plannedOrderAddPage-plannedOrdertable").datagrid('getRows');
        for (var i = 0; i < dataRows.length; i++) {
            var row = dataRows[i];
            if (row.goodsid != undefined && row.goodsid != "") {
                row.arrivedate = arriveDate;
                $("#purchase-plannedOrderAddPage-plannedOrdertable").datagrid('updateRow', {index: i, row: row});
            }
        }
    }

    function plannedOrder_realSave_form_submit() {
        $("#purchase-form-plannedOrderAddPage").form({
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
                    plannedOrder_RefreshDataGrid();
                    $.messager.alert("提醒", "保存成功");
                    $("#purchase-backid-plannedOrderAddPage").val(json.backid);
                    if (json.opertype && json.opertype == "add") {
                        $("#purchase-buttons-plannedOrderPage").buttonWidget("addNewDataId", json.backid);
                        $("#purchase-panel-plannedOrderPage").panel('refresh', 'purchase/planorder/plannedOrderEditPage.do?id=' + json.backid);
                    } else {
                        $("#purchase-plannedOrderAddPage-status").val("2");
                    }
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
    function plannedOrder_RefreshDataGrid() {
        try {
            tabsWindowURL(pageListUrl).$("#purchase-table-plannedOrderListPage").datagrid('reload');
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
    function orderDetailAddDialog() {
        var flag = $("#purchase-form-plannedOrderAddPage").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先完善采购计划单基本信息');
            return false;
        }
        var supplierid = $("#purchase-plannedOrderAddPage-supplier").supplierWidget("getValue");
        if (supplierid == null) {
            $.messager.alert("提醒", "请先选择供应商档案再进行添加商品信息");
            $("#purchase-plannedOrderAddPage-supplier").focus();
            return false;
        }
        var businessdate = $("#purchase-plannedOrderAddPage-businessdate").val();
        if (businessdate == null) {
            $.messager.alert("提醒", "请先选择业务日期");
            $("#purchase-plannedOrderAddPage-businessdate").focus();
            return false;
        }
        $('<div id="purchase-plannedOrderAddPage-dialog-DetailOper-content"></div>').appendTo('#purchase-plannedOrderAddPage-dialog-DetailOper');
        var $DetailOper = $("#purchase-plannedOrderAddPage-dialog-DetailOper-content");
        $DetailOper.dialog({
            title: '商品信息新增(按ESC退出)',
            width: 600,
            height: 420,
            closed: true,
            cache: false,
            modal: true,
            resizable: true,
            href: "purchase/planorder/plannedOrderDetailAddPage.do?supplierid=" + supplierid + "&businessdate=" + businessdate,
            onLoad: function () {
                $("#purchase-plannedOrderDetail-goodsid").focus();
            },
            onClose: function () {
                $DetailOper.dialog("destroy");
            }
        });
        $DetailOper.dialog("open");
    }
    function orderDetailEditDialog(initdata) {
        $('<div id="purchase-plannedOrderAddPage-dialog-DetailOper-content"></div>').appendTo('#purchase-plannedOrderAddPage-dialog-DetailOper');
        var $DetailOper = $("#purchase-plannedOrderAddPage-dialog-DetailOper-content");
        $DetailOper.dialog({
            title: '商品信息修改(按ESC退出)',
            width: 600,
            height: 420,
            closed: true,
            cache: false,
            modal: true,
            resizable: true,
            href: "purchase/planorder/plannedOrderDetailEditPage.do",
            onLoad: function () {
                try {
                    if (initdata != null && initdata.goodsid != null && initdata.goodsid != "") {
                        if ($("#purchase-form-plannedOrderDetailEditPage").size() > 0) {
                            if (initdata.goodsInfo) {
                                $("#purchase-plannedOrderDetail-boxnum").val(formatterBigNumNoLen(initdata.goodsInfo.boxnum));
                                $("#purchase-plannedOrderDetail-brand").val(initdata.goodsInfo.brandName);
                                $("#purchase-plannedOrderDetail-model").val(initdata.goodsInfo.model);
                                $("#purchase-plannedOrderDetail-barcode").val(initdata.goodsInfo.barcode);
                                $("#purchase-plannedOrderDetail-goodsname").val(initdata.goodsInfo.name);
                            }
                            $("#purchase-form-plannedOrderDetailEditPage").form('load', initdata);
                            $("#purchase-plannedOrderDetail-span-auxunitname").html(initdata.auxunitname);
                            $("#purchase-plannedOrderDetail-span-unitname").html(initdata.unitname);
                            $("#purchase-plannedOrderDetail-goodsid").val(initdata.goodsid);
                        }
                    }
                } catch (e) {
                }
                $("#purchase-plannedOrderDetail-auxnum").focus();
                $("#purchase-plannedOrderDetail-auxnum").select();

                formaterNumSubZeroAndDot();

                $("#purchase-form-plannedOrderDetailEditPage").form('validate');
            },
            onClose: function () {
                $DetailOper.dialog("destroy");
            }
        });
        $DetailOper.dialog("open");
    }
    function checkAfterAddGoods(goodsid) {
        if (goodsid == null || goodsid == "") {
            return false;
        }
        var $plannedOrdertable = $("#purchase-plannedOrderAddPage-plannedOrdertable");
        var flag = true;
        if ($plannedOrdertable.size() > 0) {
            var data = $plannedOrdertable.datagrid('getRows');
            if (data != null && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    if (data[i].goodsid && data[i].goodsid == goodsid) {
                        flag = false;
                        break;
                    }
                }
            }
        }
        return flag;
    }
    function footerReCalc() {
        disableChoiceWidget();
        var $potable = $("#purchase-plannedOrderAddPage-plannedOrdertable");
        var data = $potable.datagrid('getRows');
        var unitnum = 0;
        var taxamount = 0;
        var notaxamount = 0;
        var tax = 0;
        var auxnum = 0;
        var auxunitname = "";
        var auxremainder = 0;
        for (var i = 0; i < data.length; i++) {
            if (!isObjectEmpty(data[i])) {
                unitnum = Number(unitnum) + Number(data[i].unitnum == undefined ? 0 : data[i].unitnum);
                taxamount += Number(data[i].taxamount == undefined ? 0 : data[i].taxamount);
                notaxamount += Number(data[i].notaxamount == undefined ? 0 : data[i].notaxamount);
                tax += Number(data[i].tax == undefined ? 0 : data[i].tax);
                auxnum += Number(data[i].auxnum == undefined ? 0 : data[i].auxnum);
                auxremainder += Number(data[i].auxremainder == undefined ? 0 : data[i].auxremainder);
                if ((auxunitname == "" || auxunitname == null) && data[i].auxunitname != undefined) {
                    auxunitname = data[i].auxunitname;
                }
            }
        }
        auxnum = Number(auxnum) + auxunitname + (Number(auxremainder) > 0 ? Number(auxremainder) : "");
        $potable.datagrid('reloadFooter', [
            {
                goodsid: '合计',
                unitnum: Number(unitnum.toFixed(general_bill_decimallen)),
                taxamount: Number(taxamount),
                notaxamount: Number(notaxamount),
                auxnumdetail: auxnum,
                tax: tax
            }
        ]);
    }

    //禁用表单title
    function disableChoiceWidget() {
        var rows = $("#purchase-plannedOrderAddPage-plannedOrdertable").datagrid('getRows');
        var count = 0;
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].goodsid && rows[i].goodsid != "") {
                count++;
            }
        }
        if (count > 0) {
            $("#purchase-plannedOrderAddPage-supplier").supplierWidget("readonly", true);
        } else {
            $("#purchase-plannedOrderAddPage-supplier").supplierWidget("readonly", false);
        }
    }
    function checkGoodsDetailEmpty() {
        var flag = true;
        var $ordertable = $("#purchase-plannedOrderAddPage-plannedOrdertable");
        var data = $ordertable.datagrid('getRows');
        for (var i = 0; i < data.length; i++) {
            if (data[i].goodsid && data[i].goodsid != "") {
                flag = false;
                break;
            }
        }
        return flag;
    }
    var tableColJson = $("#purchase-plannedOrderAddPage-plannedOrdertable").createGridColumnLoad({
        name: 'purchase_plannedorder_detail',
        frozenCol: [[]],
        commonCol: [[
            {field: 'goodsid', title: '商品编码', width: 70, isShow: true,sortable:true},
            {
                field: 'name', title: '商品名称', width: 220, isShow: true,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.name;
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'barcode', title: '条形码', width: 90, isShow: true,sortable:true,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.barcode;
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'model', title: '规格型号', width: 80, isShow: true, hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.model;
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'brandName', title: '商品品牌', width: 60, isShow: true, hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.brandName;
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'boxnum', title: '箱装量', aliascol: 'goodsid', width: 45, align: 'right',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null && rowData.goodsInfo.boxnum != null) {
                        return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
                    } else if (value != null) {
                        return formatterBigNumNoLen(value);
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'unitid', title: '单位', width: 35,
                formatter: function (value, row, index) {
                    return row.unitname;
                }
            },
            {
                field: 'unitnum', title: '数量', width: 60, align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    return formatterBigNumNoLen(value);
                }
            },
            {
                field: 'taxprice', title: '单价', width: 60, align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'boxprice', title: '箱价', width: 60, aliascol: 'taxprice', align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'taxamount', title: '金额', width: 60, align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'notaxprice', title: '未税单价', width: 80, align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'noboxprice', title: '未税箱价', width: 60, aliascol: 'notaxprice', align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'notaxamount', title: '未税金额', width: 60, align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {field: 'taxtypename', title: '税种', width: 60},
            {
                field: 'tax', title: '税额', width: 80, align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'auxunitid', title: '辅单位', width: 60, hidden: true,
                formatter: function (value, row, index) {
                    return row.auxunitname;
                }
            },
            {field: 'auxnumdetail', title: '辅数量', width: 80, align: 'right'},
            {field: 'arrivedate', title: '要求到货日期', width: 80},
            {field: 'remark', title: '备注', width: 150}
        ]]
    });
    function detailOnSortColumn(sort, order){
        var goodsInfoArr=["barcode"];
        var issort=false;
        if(sort==null || sort==""){
            return true;
        }
        var data = $("#purchase-plannedOrderAddPage-plannedOrdertable").datagrid("getData");
        var rows = data.rows;
        var dataArr = [];
        for(var i=0;i<rows.length;i++){
            if(rows[i].goodsid!=null && rows[i].goodsid!=""){
                dataArr.push(rows[i]);
            }
        }
        dataArr.sort(function(a,b){
            var atmp=0;
            var btmp=0;
            if($.inArray(sort,goodsInfoArr)>=0){
                console.log(sort);
                console.log(goodsInfoArr);
                var aGInfo=a.goodsInfo || {};
                var bGInfo=b.goodsInfo || {};
                atmp=aGInfo[sort];
                btmp=bGInfo[sort];

            }else{
                atmp = a[sort];
                btmp = b[sort];
            }
            if(atmp==null || btmp==null){
                return -1;
            }

            if($.isNumeric(atmp)){
                if(order=="asc"){
                    return Number(atmp)>Number(btmp)?1:-1
                }else{
                    return Number(atmp)<Number(btmp)?1:-1
                }
            }else{
                if(order=="asc"){
                    return atmp>btmp?1:-1
                }else{
                    return atmp<btmp?1:-1
                }
            }
        });
        $("#purchase-plannedOrderAddPage-plannedOrdertable").datagrid("loadData",{rows:dataArr,total:data.total});
        return true;
    };
    $(document).ready(function () {
        $("#purchase-panel-plannedOrderPage").panel({
            href: plannedOrder_url,
            cache: false,
            maximized: true,
            border: false
        });
        //按钮
        $("#purchase-buttons-plannedOrderPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/purchase/planorder/plannedOrderAddBtn.do">
                {
                    type: 'button-add',
                    handler: function () {
                        $("#purchase-panel-plannedOrderPage").panel('refresh', 'purchase/planorder/plannedOrderAddPage.do');
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/planorder/plannedOrderTempSave.do">
                {
                    type: 'button-hold',
                    handler: function () {
                        $("#purchase-plannedOrderAddPage-addType").val("temp");
                        var datarows = $("#purchase-plannedOrderAddPage-plannedOrdertable").datagrid('getRows');
                        if (datarows != null && datarows.length > 0) {
                            $("#purchase-plannedOrderAddPage-plannedOrderDetails").val(JSON.stringify(datarows));
                        }
                        plannedOrder_tempSave_form_submit();
                        $("#purchase-form-plannedOrderAddPage").submit();
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/planorder/plannedOrderRealSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        if (checkGoodsDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，请填写采购计划单商品信息");
                            return false;
                        } else {
                            $("#purchase-plannedOrderAddPage-addType").val("real");
                            var datarows = $("#purchase-plannedOrderAddPage-plannedOrdertable").datagrid('getRows');
                            if (datarows != null && datarows.length > 0) {
                                $("#purchase-plannedOrderAddPage-plannedOrderDetails").val(JSON.stringify(datarows));
                            }
                            plannedOrder_realSave_form_submit();
                            $("#purchase-form-plannedOrderAddPage").submit();
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/planorder/plannedOrderGiveUpBtn.do">
                {
                    type: 'button-giveup',//放弃
                    handler: function () {
                        var $polbuttons = $("#purchase-buttons-plannedOrderPage");
                        var type = $polbuttons.buttonWidget("getOperType");
                        if (type == "add") {
                            var id = $("#purchase-backid-arrivalOrderAddPage").val();
                            if (id == "") {
                                tabsWindowTitle(pageListUrl);
                            } else {
                                $("#purchase-panel-plannedOrderPage").panel('refresh', 'purchase/planorder/plannedOrderEditPage.do?id=' + id);
                            }
                        } else if (type == "edit") {

                            var id = $("#purchase-backid-plannedOrderAddPage").val();
                            if (id == "") {
                                return false;
                            }
                            var flag = isLockData(id, "t_purchase_plannedorder");
                            if (!flag) {
                                $.messager.alert("警告", "该数据正在被其他人操作，暂不能修改！");
                                return false;
                            }
                            $("#purchase-panel-plannedOrderPage").panel('refresh', 'purchase/planorder/plannedOrderEditPage.do?id=' + id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/planorder/deletePlannedOrder.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        var id = $("#purchase-backid-plannedOrderAddPage").val();
                        if (id == "") {
                            return false;
                        }

                        $.messager.confirm("提醒", "是否删除该采购计划单信息？", function (r) {
                            if (r) {
                                loading("删除中..");
                                $.ajax({
                                    url: 'purchase/planorder/deletePlannedOrder.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "删除成功");
                                            var nextdata = $("#purchase-buttons-plannedOrderPage").buttonWidget("removeData", id);
                                            if (null != nextdata && nextdata.id && nextdata.id != "") {
                                                $("#purchase-backid-plannedOrderAddPage").val(nextdata.id);
                                                $("#purchase-panel-plannedOrderPage").panel('refresh', 'purchase/planorder/plannedOrderEditPage.do?id=' + nextdata.id);

                                                plannedOrder_RefreshDataGrid();
                                            } else {

                                                plannedOrder_RefreshDataGrid();
                                                parent.closeNowTab();
                                                //$("#purchase-panel-plannedOrderPage").panel('refresh', 'purchase/planorder/plannedOrderAddPage.do');
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
                <security:authorize url="/purchase/planorder/auditPlannedOrder.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        var id = $("#purchase-backid-plannedOrderAddPage").val();
                        if (id == "") {
                            return false;
                        }
                        var flag = $("#purchase-form-plannedOrderAddPage").form('validate');
                        if (flag == false) {
                            $.messager.alert("提醒", "抱歉，请完善表单信息。修改完成后，请注意保存。");
                            $("#purchase-plannedOrderAddPage-formModStatus").val("123");
                            return false;
                        } else {
                            $("#purchase-plannedOrderAddPage-formModStatus").val("0");
                        }
                        var modstatus = $("#purchase-plannedOrderAddPage-formModStatus").val();
                        if (modstatus == "123") {
                            $.messager.alert("提醒", "抱歉，当前表单被修改但未保存，请保存后再审核");
                            return false;
                        }
                        if (checkGoodsDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，请填写采购计划单商品信息");
                            return false;
                        }

                        $.messager.confirm("提醒", "是否审核通过该采购计划单信息？", function (r) {
                            if (r) {
                                loading("审核中..");
                                $.ajax({
                                    url: 'purchase/planorder/auditPlannedOrder.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            //$("#purchase-plannedOrderAddPage-status").val("3");
                                            //$("#purchase-buttons-plannedOrderPage").buttonWidget("setDataID", {id:id, state:'3', type:'view'});
                                            $.messager.alert("提醒", "审核成功,并自动生成采购订单，单据编号：" + json.billid);
                                            plannedOrder_RefreshDataGrid();
                                            $("#purchase-panel-plannedOrderPage").panel('refresh', 'purchase/planorder/plannedOrderEditPage.do?id=' + id);
                                        } else {
                                            $.messager.alert("提醒", "审核失败");
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/planorder/oppauditPlannedOrder.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        var id = $("#purchase-backid-plannedOrderAddPage").val();
                        if (id == "") {
                            return false;
                        }
                        var businessdate = $("#purchase-plannedOrderAddPage-businessdate").val();
                        var flag = isDoneOppauditBillCaseAccounting(businessdate);
                        if (!flag) {
                            $.messager.alert("提醒", "业务日期不在会计区间内或未设置会计区间,不允许反审!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否反审该采购计划单信息？", function (r) {
                            if (r) {
                                loading("反审中..");
                                $.ajax({
                                    url: 'purchase/planorder/oppauditPlannedOrder.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "反审成功");
                                            plannedOrder_RefreshDataGrid();
                                            $("#purchase-panel-plannedOrderPage").panel('refresh', 'purchase/planorder/plannedOrderEditPage.do?id=' + id);
                                        } else {
                                            $.messager.alert("提醒", "反审失败");
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/planorder/plannedOrderWorkflow.do">
                {
                    type: 'button-workflow',
                    button: [
                        {
                            type: 'workflow-submit',
                            handler: function () {
                                $.messager.confirm("提醒", "是否提交采购计划单信息到工作流?", function (r) {
                                    if (r) {
                                        var id = $("#purchase-backid-plannedOrderAddPage").val();
                                        if (id == "") {
                                            $.messager.alert("警告", "没有需要提交工作流的信息!");
                                            return false;
                                        }
                                        loading("提交中..");
                                        $.ajax({
                                            url: 'purchase/planorder/submitPlannedOrderProcess.do',
                                            dataType: 'json',
                                            type: 'post',
                                            data: 'id=' + id,
                                            success: function (json) {
                                                loaded();
                                                if (json.flag == true) {
                                                    $.messager.alert("提醒", "提交成功!");
                                                    plannedOrder_RefreshDataGrid();
                                                    $("#purchase-panel-plannedOrderPage").panel("refresh");
                                                }
                                                else {
                                                    if (json.msg != null || json.msg != "") {
                                                        $.messager.alert("提醒", "提交失败!" + json.msg);
                                                    }
                                                    else {
                                                        $.messager.alert("提醒", "提交失败!");
                                                    }
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        },
                        {
                            type: 'workflow-addidea',
                            handler: function () {
                                if (plannedOrder_type == "handle") {
                                    $('<div id="purchase-wfdialog-plannedOrderPage-content"></div>').appendTo('#purchase-wfdialog-plannedOrderPage');
                                    var $wfDialogOper = $("#purchase-wfdialog-plannedOrderPage-content");
                                    $wfDialogOper.dialog({
                                        title: '填写处理意见',
                                        width: 450,
                                        height: 300,
                                        closed: false,
                                        cache: false,
                                        modal: true,
                                        href: 'workflow/commentAddPage.do?id=' + handleWork_taskId,
                                        onClose: function () {
                                            $wfDialogOper.dialog("destroy");
                                        }
                                    });
                                }
                            }
                        },
                        {
                            type: 'workflow-viewflow',
                            handler: function () {
                                var id = $("#purchase-backid-plannedOrderAddPage").val();
                                if (id == "") {
                                    return false;
                                }
                                $('<div id="purchase-wfdialog-plannedOrderPage-content"></div>').appendTo('#purchase-wfdialog-plannedOrderPage');
                                var $wfDialogOper = $("#purchase-wfdialog-plannedOrderPage-content");
                                $wfDialogOper.dialog({
                                    title: '查看流程',
                                    width: 600,
                                    height: 450,
                                    closed: false,
                                    cache: false,
                                    modal: true,
                                    maximizable: true,
                                    resizable: true,
                                    href: 'workflow/commentListPage.do?id=' + id,
                                    onClose: function () {
                                        $wfDialogOper.dialog("destroy");
                                    }
                                });
                            }
                        },
                        {
                            type: 'workflow-viewflow-pic',
                            handler: function () {
                                var id = $("#purchase-backid-plannedOrderAddPage").val();
                                if (id == "") {
                                    return false;
                                }
                                $('<div id="purchase-wfdialog-plannedOrderPage-content"></div>').appendTo('#purchase-wfdialog-plannedOrderPage');
                                var $wfDialogOper = $("#purchase-wfdialog-plannedOrderPage-content");
                                $wfDialogOper.dialog({
                                    title: '查看流程',
                                    width: 600,
                                    height: 450,
                                    closed: false,
                                    cache: false,
                                    modal: true,
                                    maximizable: true,
                                    resizable: true,
                                    href: 'workflow/showDiagramPage.do?id=' + id,
                                    onClose: function () {
                                        $wfDialogOper.dialog("destroy");
                                    }
                                });
                            }
                        },
                        {
                            type: 'workflow-recover',
                            handler: function () {

                            }
                        }
                    ]
                },
                </security:authorize>
                <security:authorize url="/purchase/planorder/plannedOrderPrevPage.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        if (data != null && data.id != null && data.id != "") {
                            $("#purchase-backid-plannedOrderAddPage").val(data.id);
                            $("#purchase-panel-plannedOrderPage").panel('refresh', 'purchase/planorder/plannedOrderEditPage.do?id=' + data.id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/planorder/plannedOrderNextPage.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        if (data != null && data.id != null && data.id != "") {
                            $("#purchase-backid-plannedOrderAddPage").val(data.id);
                            $("#purchase-panel-plannedOrderPage").panel('refresh', 'purchase/planorder/plannedOrderEditPage.do?id=' + data.id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/planorder/plannedOrderPrintView.do">
                {
                    type: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/planorder/plannedOrderPrint.do">
                {
                    type: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}
            ],
            buttons: [
                <security:authorize url="/report/buy/plannedOrderAnalysisReportBtn.do">
                {
                    id: 'button-view-planorderAnalysis',
                    name: '查看采购计划分析表',
                    iconCls: 'button-view',
                    handler: function () {
                        var businessdate = $("#purchase-plannedOrderAddPage-businessdate").val() || "";
                        if (businessdate == null || $.trim(businessdate) == "") {
                            $.messager.alert("提醒", "抱歉，未能找到相关商品信息!");
                            return;
                        }
                        var supplierid = "";
                        try {
                            supplierid = $("#purchase-plannedOrderAddPage-supplier").widget("getValue");
                        } catch (e) {
                        }
                        if (supplierid == null || $.trim(supplierid) == "") {
                            $.messager.alert("提醒", "抱歉，未能找到供应商信息!");
                            return false;
                        }
                        $('<div id="purchase-plannedOrderPage-dialog-planorderAnalysis-content"></div>').appendTo('#purchase-plannedOrderPage-dialog-planorderAnalysis');
                        var $planorderAnalysisDialog = $("#purchase-plannedOrderPage-dialog-planorderAnalysis-content");
                        $planorderAnalysisDialog.dialog({
                            title: '查看相应采购计划分析表',
                            //width: 680,
                            //height: 300,
                            fit: true,
                            closed: true,
                            cache: false,
                            href: 'purchase/buyorder/showPlanOrderAnalysisViewReportPage.do?businessdate=' + $.trim(businessdate) + "&supplierid=" + supplierid,
                            maximizable: true,
                            resizable: true,
                            modal: true,
                            onLoad: function () {
                                var idarr = new Array();
                                var orderInfoArr = new Array();
                                var rowsObjectData = $("#purchase-plannedOrderAddPage-plannedOrdertable").datagrid("getData");
                                var rowsData = rowsObjectData.rows;

                                for (var i = 0; i < rowsData.length; i++) {
                                    if (rowsData[i].goodsid != null && $.trim(rowsData[i].goodsid) != "") {
                                        idarr.push(rowsData[i].goodsid);
                                        var orderInfo = {};
                                        orderInfo.goodsid = rowsData[i].goodsid;
                                        orderInfo.unitnum = rowsData[i].unitnum;
                                        orderInfo.taxamount = rowsData[i].taxamount;
                                        orderInfo.taxprice = rowsData[i].taxprice;
                                        orderInfo.unitid = rowsData[i].unitid;
                                        orderInfo.unitname = rowsData[i].unitname;
                                        orderInfo.auxunitid = rowsData[i].auxunitid;
                                        orderInfo.auxunitname = rowsData[i].auxunitname;
                                        orderInfo.auxnum = rowsData[i].auxnum;
                                        orderInfo.auxremainder = rowsData[i].auxremainder;

                                        orderInfoArr.push(orderInfo);
                                    }
                                }

                                try {
                                    var queryFormdata = $("#purchase-plannedOrderAddPage-field08").val() || "";

                                    if ($.trim(queryFormdata) != "") {
                                        queryFormdata = JSON.parse(queryFormdata);
                                        if (queryFormdata) {
                                            poaOldFormData = queryFormdata;
                                            queryFormdata.supplierid = supplierid;
                                            $("#purchase-query-form-analysisReportView").form('load', queryFormdata);
                                        }
                                    }
                                } catch (e) {
                                }

                                var orderstatus = $("#purchase-buyOrderAddPage-status").val() || "2";
                                $("#purchase-queay-analysisReportView-orderstatus").val(orderstatus);
                                $("#purchase-queay-analysisReportView-goodsidarr").val(idarr.join(','));
                                $("#purchase-queay-analysisReportView-orderInfoarr").val(JSON.stringify(orderInfoArr));


                                initAnalysisDatagrid();
                                initStorageSummaryDategrid();
                            },
                            onClose: function () {
                                $planorderAnalysisDialog.dialog("destroy");
                            }
                        });
                        $planorderAnalysisDialog.dialog('open');
                    }
                },
                </security:authorize>
                {}
            ],
            layoutid: 'purchase-plannedOrderPage-layout',
            model: 'bill',
            type: 'view',
            taburl: pageListUrl,
            datagrid: 'purchase-table-plannedOrderListPage',
            id: '${id}',
            tname: 't_purchase_plannedorder'
        });

        $(document).bind('keydown', 'esc', function () {
            if ($("#purchase-plannedOrderAddPage-dialog-DetailOper-content").size() > 0) {
                $("#purchase-plannedOrderAddPage-dialog-DetailOper-content").dialog("close");
            }
        });
        $(document).bind('keydown', 'ctrl+enter', function () {
            $("#purchase-plannedOrderDetail-remark").focus();
            setTimeout(function () {
                $("#purchase-plannedOrderDetailAddPage-addSaveGoOn").trigger("click");
                $("#purchase-plannedOrderDetailEditPage-editSave").trigger("click");
            }, 200);
            return false;
        });
        $(document).bind('keydown', '+', function () {
            $("#purchase-plannedOrderDetail-remark").focus();
            setTimeout(function () {
                $("#purchase-plannedOrderDetailAddPage-addSaveGoOn").trigger("click");
                $("#purchase-plannedOrderDetailEditPage-editSave").trigger("click");
            }, 200);
            return false;
        });
    });
    //历史价格查看
    function showHistoryGoodsPrice() {
        var row = $("#purchase-plannedOrderAddPage-plannedOrdertable").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        var goodsid = row.goodsid;
        var goodsname = row.goodsInfo.name;
        $("#purchase-goods-history-price").dialog({
            title: '商品[' + goodsid + ']' + goodsname + ' 历史价格表',
            width: 600,
            height: 400,
            closed: false,
            modal: true,
            cache: false,
            maximizable: true,
            resizable: true,
            href: 'purchase/buyorder/showPurchaseHistoryGoodsPricePage.do',
            queryParams: {goodsid: goodsid}
        });
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-plannedOrder-dialog-print",
            code: "purchase_plannedorder",
            url_preview: "print/purchase/plannedOrderPrintView.do",
            url_print: "print/purchase/plannedOrderPrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            getData: function (tableId, printParam) {
                var id = $("#purchase-backid-plannedOrderAddPage").val();
                if (id == "") {
                    $.messager.alert("参数丢失");
                    return false;
                }
                printParam.idarrs = id;
                return true;
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
