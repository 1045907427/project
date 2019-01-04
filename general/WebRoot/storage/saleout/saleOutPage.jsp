<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>发货单操作页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div id="storage-layout-saleOutPage" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-saleOutPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="storage-panel-saleOutPage">
        </div>
    </div>
</div>
<div id="storage-panel-relation-upper"></div>
<div id="storage-panel-sourceQueryPage"></div>
<div id="workflow-addidea-dialog-page"></div>
<input type="hidden" id="storage-hidden-billid"/>

<div style="display:none">
    <%--通用 --%>
    <div id="listPage-Orderblank-dialog-print">
        <form action="" id="listPage-Orderblank-dialog-print-form" method="post">
            <table>
                <tr>
                    <td>打印模板：</td>
                    <td>
                        <select id="listPage-Orderblank-dialog-print-templet" name="templetid">
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div id="listPage-DeliveryOrder-dialog-print">
        <form action="" id="listPage-DeliveryOrder-dialog-print-form" method="post">
            <table>
                <tr>
                    <td>打印模板：</td>
                    <td>
                        <select id="listPage-DeliveryOrder-dialog-print-templet" name="templetid">
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    //根据初始的列与用户保存的列生成以及字段权限生成新的列
    function confirmStorageWidget() {
        $("#storage-hidden-storager").widget({
            referwid: 'RL_T_BASE_PERSONNEL_STORAGER',
            width: 160,
            col: 'name',
            singleSelect: true,
            initValue: '${storager}',
        });
    }
    var tableColJson = $("#storage-datagrid-saleOutAddPage").createGridColumnLoad({
        name: 'storage_saleout_detail',
        frozenCol: [[]],
        commonCol: [[
            {field: 'ck', checkbox: true},
            {field: 'goodsid', title: '商品编码', width: 70},
            {
                field: 'goodsname', title: '商品名称', width: 220, aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        if (rowData.isdiscount == '1' || rowData.isdiscount == '2') {
                            return "（折扣）" + rowData.goodsInfo.name;
                        } else {
                            return rowData.goodsInfo.name;
                        }
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'barcode', title: '条形码', width: 95, aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.barcode;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'itemno', title: '商品货位', width: 60, aliascol: 'goodsid', align: 'center',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.itemno;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'brandName', title: '商品品牌', width: 80, aliascol: 'goodsid', hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.brandName;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'boxnum', title: '箱装量', aliascol: 'goodsid', width: 50, align: 'right',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
                    } else {
                        return "";
                    }
                }
            },
            {field: 'unitname', title: '单位', width: 35},
            {
                field: 'unitnum', title: '数量', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterBigNumNoLen(value);
                },
                styler: function (value, row, index) {
                    var status = $("#storage-saleOut-status-select").val();
                    if (row.goodsid != null && row.goodsid != "合计" && status == "2" && row.isenough != '1') {
                        return 'background-color:red;';
                    }
                }
            },
            {
                field: 'taxprice', title: '单价', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    if (row.isdiscount != '1' && row.isdiscount != '2') {
                        return formatterMoney(value);
                    }
                }
            },
            {
                field: 'taxamount', title: '金额', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'notaxprice', title: '未税单价', width: 60, align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'notaxamount', title: '未税金额', width: 60, align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {field: 'taxtypename', title: '税种', width: 60, align: 'right', hidden: true},
            {
                field: 'tax', title: '税额', width: 60, align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {field: 'auxnumdetail', title: '辅数量', width: 60, align: 'right'},
            {field: 'storagelocationname', title: '所属库位', width: 100, aliascol: 'storagelocationid', hidden: true},
            {field: 'batchno', title: '批次号', width: 80},
            {field: 'produceddate', title: '生产日期', width: 80},
            {field: 'deadline', title: '有效截止日期', width: 80, hidden: true},
            {field: 'seq', title: '排序', width: 80, hidden: true},
            {field: 'remark', title: '备注', width: 100}
        ]]
    });

    var page_url = "storage/saleOutAddPage.do";
    var page_type = '${type}';
    if (page_type == "view" || page_type == "handler") {
        var flag = '${flag}';
        if (flag == "true") {
            page_url = "storage/saleOutViewPage.do?id=${id}";
        }
        else {
            $.messager.alert("提醒", "该订单已不存在！");
            page_url = null;
        }
    } else if (page_type == "edit") {
        page_url = "storage/saleOutEditPage.do?id=${id}";
    }
    $(function () {
        $("#storage-panel-saleOutPage").panel({
            href: page_url,
            cache: false,
            maximized: true,
            border: false
        });
        //按钮
        $("#storage-buttons-saleOutPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/addSaleOutSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        submitSaleOut();
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/addSaleOutSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        var fn = function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                var storager = $("#storage-hidden-storager").widget("getValue");
                                $("#storage-saveaudit-saleOutDetail").val("saveaudit");
                                $("#storage-form-saleOutAdd").attr("action", "storage/editSaleOutSave.do?storager=" + storager);
                                $("#storage-form-saleOutAdd").submit();
                            }
                        };
                        $.messager.confirm({
                            title: '提醒',
                            onOpen: function () {
                                confirmStorageWidget()
                            },
                            msg: "是否审核发货单？</br>" + "<span style=\"float: left;\">选择发货人:</span><input  id=\"storage-hidden-storager\" name=\"storager\"/>",
                            fn: fn
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/saleOutGiveUp.do">
                {
                    type: 'button-giveup',
                    handler: function () {
                        var type = $("#storage-buttons-saleOutPage").buttonWidget("getOperType");
                        if (type == "add") {
                            var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
                            top.closeTab(currTitle);
                        }
                        else if (type == "edit") {
                            var id = $("#storage-hidden-billid").val();
                            if (id == "") {
                                return false;
                            }
                            $("#storage-panel-saleOutPage").panel({
                                href: 'storage/saleOutViewPage.do?id=' + id,
                                title: '',
                                cache: false,
                                maximized: true,
                                border: false
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/deleteSaleOut.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        $.messager.confirm("提醒", "是否删除当前发货单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("删除中..");
                                    $.ajax({
                                        url: 'storage/deleteSaleOut.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                var object = $("#storage-buttons-saleOutPage").buttonWidget("removeData", id);
                                                $.messager.alert("提醒", "删除成功");
                                                if (null != object) {
                                                    $("#storage-panel-saleOutPage").panel({
                                                        href: 'storage/saleOutEditPage.do?id=' + object.id,
                                                        title: '',
                                                        cache: false,
                                                        maximized: true,
                                                        border: false
                                                    });
                                                } else {
                                                    parent.closeNowTab();
                                                }
                                            } else {
                                                $.messager.alert("提醒", "删除失败");
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "删除出错")
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/auditSaleOut.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        var fn = function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                var storager = $("#storage-hidden-storager").widget("getValue");
                                loading("审核中..");
                                $.ajax({
                                    url: 'storage/auditSaleOut.do?id=' + id + '&storager=' + storager,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "审核成功。" + json.msg + "</br>生成销售发货回单，编号为：" + json.receiptid);
                                            $("#storage-saleOut-status-select").val(3);
                                            $("#storage-buttons-saleOutPage").buttonWidget("setDataID", {
                                                id: id,
                                                state: '3',
                                                type: 'view'
                                            });
                                            //刷新列表
                                            tabsWindowURL("/storage/showSaleOutListPage.do").$("#storage-datagrid-saleoutPage").datagrid('reload');
                                            //关闭当前标签页
                                            top.closeNowTab();
                                        } else {
                                            $.messager.alert("提醒", "审核失败</br>" + json.msg);
                                        }
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "审核出错");
                                    }
                                });
                            }
                        };
                        $.messager.confirm({
                            title: '提醒',
                            onOpen: function () {
                                confirmStorageWidget()
                            },
                            msg: "是否审核发货单？</br>" + "<span style=\"float: left;\">选择发货人:</span><input  id=\"storage-hidden-storager\" name=\"storager\"/>",
                            fn: fn
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/oppauditSaleOut.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        var businessdate = $("#storage-saleOut-businessdate").val();
                        var flag = isDoneOppauditBillCaseAccounting(businessdate);
                        if (!flag) {
                            $.messager.alert("提醒", "业务日期不在会计区间内或未设置会计区间,不允许反审!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否反审发货单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                var auditflag = true;
                                <security:authorize url="/sales/oppauditSaleOutSupper.do">
                                auditflag = false;
                                </security:authorize>
                                if (auditflag) {
                                    var businessdate = $("#storage-saleOut-businessdate").val();
                                    if (businessdate != '${today}') {
                                        $.messager.alert("提醒", "发货单不能反审，业务日期不是今天。需要有权限的人才能反审！");
                                        return false;
                                    }
                                }
                                if (id != "") {
                                    loading("反审中..");
                                    $.ajax({
                                        url: 'storage/oppauditSaleOut.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "反审成功");
                                                $("#storage-panel-saleOutPage").panel({
                                                    href: 'storage/saleOutEditPage.do?id=' + id,
                                                    title: '',
                                                    cache: false,
                                                    maximized: true,
                                                    border: false
                                                });
                                            } else {
                                                if (json.oppflag == false) {
                                                    $.messager.alert("提醒", "反审失败,该发货单的审核时间不是今天，不能反审。只有当天审核的单据才能反审");
                                                } else {
                                                    $.messager.alert("提醒", "反审失败");
                                                }
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "反审出错");
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/showSaleOutRelationPage.do">
                {
                    type: 'button-relation',
                    button: [
                        {},
                        <security:authorize url="/storage/showRelationUpperPage.do">
                        {
                            type: 'relation-upper',
                            handler: function () {
                                $("#storage-panel-relation-upper").dialog({
                                    href: "storage/showRelationUpperPage.do",
                                    title: "销售发货通知单查询",
                                    closed: false,
                                    modal: true,
                                    cache: false,
                                    width: 500,
                                    height: 300,
                                    buttons: [{
                                        text: '查询',
                                        handler: function () {
                                            var queryJSON = $("#storage-form-query-dispatchBill").serializeJSON();
                                            $("#storage-panel-relation-upper").dialog('close', true);
                                            var type = $("#storage-buttons-saleOutPage").buttonWidget("getOperType");
                                            $("#storage-panel-sourceQueryPage").dialog({
                                                title: '销售发货通知单列表',
                                                fit: true,
                                                closed: false,
                                                modal: true,
                                                cache: false,
                                                maximizable: true,
                                                resizable: true,
                                                href: 'storage/showSaleOutSourceListPage.do',
                                                buttons: [{
                                                    text: '确定',
                                                    handler: function () {
                                                        var dispatchbill = $("#storage-orderDatagrid-dispatchBillSourcePage").datagrid('getSelected');
                                                        if (dispatchbill == null) {
                                                            $.messager.alert("提醒", "请选择一条订单记录");
                                                            return false;
                                                        }
                                                        $("#storage-panel-sourceQueryPage").dialog('close', true);
                                                        //生成发货单
                                                        loading("生成中..");
                                                        $.ajax({
                                                            url: 'storage/addSaleOutByRefer.do',
                                                            type: 'post',
                                                            dataType: 'json',
                                                            data: {dispatchbillid: dispatchbill.id},
                                                            success: function (json) {
                                                                loaded();
                                                                if (json.flag) {
                                                                    $.messager.alert("提醒", "生成成功,发货单编号为：" + json.saleoutid);
                                                                    $("#storage-panel-saleOutPage").panel({
                                                                        href: 'storage/saleOutViewPage.do?id=' + json.id,
                                                                        title: '',
                                                                        cache: false,
                                                                        maximized: true,
                                                                        border: false
                                                                    });
                                                                } else {
                                                                    $.messager.alert("提醒", json.msg);
                                                                }
                                                            }
                                                        });
                                                    }
                                                }],
                                                onLoad: function () {
                                                    $("#storage-orderDatagrid-dispatchBillSourcePage").datagrid({
                                                        columns: [[
                                                            {field: 'id', title: '编号', width: 100},
                                                            {field: 'businessdate', title: '业务日期', width: 100},
                                                            {
                                                                field: 'customerid',
                                                                title: '客户简称',
                                                                width: 100,
                                                                align: 'left'
                                                            },
                                                            {
                                                                field: 'handlerid',
                                                                title: '对方经手人',
                                                                width: 100,
                                                                align: 'left'
                                                            },
                                                            {
                                                                field: 'salesdept',
                                                                title: '销售部门',
                                                                width: 120,
                                                                align: 'left'
                                                            },
                                                            {
                                                                field: 'salesuser',
                                                                title: '客户业务员',
                                                                width: 100,
                                                                align: 'left'
                                                            },
                                                            {
                                                                field: 'addusername',
                                                                title: '制单人',
                                                                width: 100,
                                                                align: 'left'
                                                            },
                                                            {field: 'remark', title: '备注', width: 100, align: 'left'}
                                                        ]],
                                                        fit: true,
                                                        method: 'post',
                                                        rownumbers: true,
                                                        pagination: true,
                                                        idField: 'id',
                                                        singleSelect: true,
                                                        fitColumns: true,
                                                        url: 'sales/getDispatchBillList.do',
                                                        queryParams: queryJSON,
                                                        onClickRow: function (index, data) {
                                                            var saleoutid = $("#storage-hidden-billid").val();
                                                            $("#storage-detailDatagrid-dispatchBillSourcePage").datagrid({
                                                                url: 'storage/showDispatchBillDetailList.do',
                                                                queryParams: {
                                                                    dispatchBillid: data.id,
                                                                    saleoutid: saleoutid
                                                                },
                                                                onLoadSuccess: function () {
                                                                    $("#storage-detailDatagrid-dispatchBillSourcePage").datagrid("clearChecked");
                                                                    $("#storage-detailDatagrid-dispatchBillSourcePage").datagrid("checkAll");
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }]
                                });
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/showSaleOutSourcePage.do">
                        {
                            type: 'relation-upper-view',
                            handler: function () {
                                <c:if test="${IsDispatchProcessUse=='1'}">
                                var sourceid = $("#storage-saleOut-sourceid").val();
                                if (null != sourceid && sourceid != "") {
                                    top.addOrUpdateTab('sales/dispatchBill.do?type=view&id=' + sourceid, "销售发货通知单");
                                }
                                </c:if>
                                <c:if test="${IsDispatchProcessUse=='0'}">
                                var orderid = $("#storage-saleOut-saleorderid").val();
                                if (null != orderid && orderid != "") {
                                    top.addOrUpdateTab('sales/orderPage.do?type=view&id=' + orderid, '销售订单查看');
                                }
                                </c:if>
                            }
                        },
                        </security:authorize>
                        {}
                    ]
                },
                </security:authorize>
                <security:authorize url="/storage/saleOutViewBackPage.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        $("#storage-panel-saleOutPage").panel({
                            href: 'storage/saleOutEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/saleOutViewNextPage.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        $("#storage-panel-saleOutPage").panel({
                            href: 'storage/saleOutEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    }
                },
                </security:authorize>
                {}
            ],
            buttons: [
                <security:authorize url="/storage/storageOrderblankPrintView.do">
                {
                    id: 'button-printview-orderblank',
                    name: '配货单打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/storageOrderblankPrint.do">
                {
                    id: 'button-print-orderblank',
                    name: '配货单打印',
                    iconCls: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/storageDeliveryOrderPrintView.do">
                {
                    id: 'button-printview-DeliveryOrder',
                    name: '发货单打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/storageDeliveryOrderPrint.do">
                {
                    id: 'button-print-DeliveryOrder',
                    name: '发货单打印',
                    iconCls: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}
            ],
            layoutid: 'storage-layout-saleOutPage',
            model: 'bill',
            type: 'view',
            id: '${id}',
            taburl: '/storage/showSaleOutListPage.do',		//tab标签的url地址。
            datagrid: 'storage-datagrid-saleoutPage'
        });
    });
    $(function () {
        //关闭明细添加页面
        $(document).bind('keydown', 'esc', function () {
            $("#storage-saleOut-remark").focus();
            $("#storage-dialog-saleOutAddPage-content").dialog("close");
        });
        $(document).bind('keydown', 'ctrl+enter', function () {
            $("#storage-saleOut-remark").focus();
            $("#storage-savegoon-saleOutDetailEditPage").trigger("click");
            $("#storage-savegoon-saleOutDetailAddPage").trigger("click");
            return false;
        });
        $(document).bind('keydown', '+', function () {
            $("#storage-saleOut-remark").focus();
            setTimeout(function () {
                $("#storage-savegoon-saleOutDetailEditPage").trigger("click");
                $("#storage-savegoon-saleOutDetailAddPage").trigger("click");
            }, 100);
            return false;
        });
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        var $grid = $("#storage-datagrid-saleoutPage");
        var printLimit = "${printlimit}" || -1;
        var fHPrintAfterSaleOutAudit = $("#storage-saleOut-fHPrintAfterSaleOutAudit").val() || "0";

        function onPrintSuccess(option) {
            var thepage = tabsWindowURL("/storage/showSaleOutListPage.do");
            if (thepage == null) {
                return false;
            }
            var thegrid = thepage.$("#storage-datagrid-saleoutPage");
            if (thegrid == null || thegrid.size() == 0) {
                return false;
            }
            var dataList = thegrid.datagrid("getChecked");
            if (dataList == null || dataList.length == 0) {
                return false;
            }
            var isblank = false;
            $.each(option.code, function (i, item) {
                //配置打印次数更新
                if (item.codename == "storage_orderblank")
                    isblank = true;
            });
            for (var i = 0; i < dataList.length; i++) {
                if (isblank) {
                    if (dataList[i].phprinttimes && !isNaN(dataList[i].phprinttimes)) {
                        dataList[i].phprinttimes = dataList[i].phprinttimes + 1;
                    } else {
                        dataList[i].phprinttimes = 1;
                    }
                } else {
                    if (dataList[i].printtimes && !isNaN(dataList[i].printtimes)) {
                        dataList[i].printtimes = dataList[i].printtimes + 1;
                    } else {
                        dataList[i].printtimes = 1;
                    }
                }
                var rowIndex = thegrid.datagrid("getRowIndex", dataList[i].id);
                thegrid.datagrid('updateRow', {index: rowIndex, row: dataList[i]});
            }
        }

        function printAfterHandler(option, printParam) {
            var isblank = false;
            $.each(option.code, function (i, item) {
                //配置打印次数更新
                if (item.codename == "storage_orderblank")
                    isblank = true;
            });
            if(isblank){
                var printtimes = $("#storage-saleOut-phprinttimes").val() || 0;
                $("#storage-saleOut-phprinttimes").val(printtimes + 1);
                if (0 != printLimit) {
                    $("#storage-buttons-saleOutPage").buttonWidget("disableMenuItem", "button-print-DeliveryOrder");
                }
            }else {
                var printtimes = $("#storage-saleOut-printtimes").val() || 0;
                $("#storage-saleOut-printtimes").val(printtimes + 1);
                if (0 != printLimit) {
                    $("#storage-buttons-saleOutPage").buttonWidget("disableMenuItem", "button-print-orderblank");
                }
            }
        }

        //配货单打印
        AgReportPrint.init({
            id: "orderblank-dialog-print",
            code: "storage_orderblank",
            url_preview: "print/storage/storageOrderblankPrintView.do",
            url_print: "print/storage/storageOrderblankPrint.do",
            btnPreview: "button-printview-orderblank",
            btnPrint: "button-print-orderblank",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var id = $("#storage-hidden-billid").val();
                if (id == "") {
                    $.messager.alert("提醒", "请根据配货单不可打印");
                    return false;
                }
                printParam.idarrs = id;
                var printtimes = $("#storage-saleOut-phprinttimes").val() || 0;
                if (printtimes > 0)
                    printParam.printIds = [id];
                return true;
            },
            onPrintSuccess: onPrintSuccess,
            printAfterHandler: printAfterHandler
        });
        //库位套打
        AgReportPrint.init({
            id: "deliveryorder-dialog-print",
            code: "storage_deliveryorder",
            url_preview: "print/storage/storageDeliveryOrderPrintView.do",
            url_print: "print/storage/storageDeliveryOrderPrint.do",
            btnPreview: "button-printview-DeliveryOrder",
            btnPrint: "button-print-DeliveryOrder",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var id = $("#storage-hidden-billid").val();
                if (id == "") {
                    $.messager.alert("提醒", "请根据发货单不可打印");
                    return false;
                }
                if (fHPrintAfterSaleOutAudit == "1") {
                    if (status != '3' && status != '4') {
                        $.messager.alert("提醒", "抱歉，审核通过或关闭状态下才能。<br/>" + id + "此发货单不可打印");
                        return false;
                    }
                }
                printParam.idarrs = id;
                var printtimes = $("#storage-saleOut-printtimes").val() || 0;
                if (printtimes > 0)
                    printParam.printIds = [id];
                return true;
            },
            onPrintSuccess: onPrintSuccess
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
