<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags"
          prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>送入库单单操作页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<input type="hidden" id="storage-distributeRejectPage-id" name="storage-distributeRejectPage-id" value="${id }"></input>
<div id="storage-layout-distributeRejectPage" class="easyui-layout"
     data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-distributeRejectPage"
             style="height: 26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div class="easyui-panel" id="storage-panel-distributeRejectPage"
             data-options="fit:true"></div>
    </div>
</div>
<div id="storage-distributeRejectPage-dialog-DetailOper"></div>
<script type="text/javascript">
    var tableColJson = $("#storage-table-distributeRejectAddPage").createGridColumnLoad({
        name: 't_storage_delivery_enter_detail',
        frozenCol: [[]],
        commonCol: [[
            {field: 'id', title: '编码', width: 60, hidden: true, isShow: true},
            {field: 'goodsid', title: '商品编码', width: 80, isShow: true},
            {field: 'goodsname', title: '商品名称', width: 220, isShow: true},
            {
                field: 'barcode', title: '条形码', width: 90, isShow: true,
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
                field: 'boxnum', title: '箱装量', width: 45, align: 'right',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'unitname', title: '单位', width: 35,
                formatter: function (value, row, index) {
                    return row.unitname;
                }
            },
            {
                field: 'unitnum', title: '数量', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterBigNumNoLen(value);
                }
            },
            {
                field: 'basesaleprice', title: '单价', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'boxprice', title: '箱价', width: 60, align: 'right',
                formatter: function (value, row, row) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'taxamount', title: '金额', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {field: 'batchno', title: '批次号', width: 80, align: 'right'},
            {field: 'produceddate', title: '生产日期', width: 80, align: 'right'},
            {
                field: 'auxunitname', title: '辅单位', width: 60,
                formatter: function (value, row, index) {
                    return row.auxunitname;
                }, hidden: true
            },
            {field: 'auxnumdetail', title: '辅数量', width: 80, align: 'right'},
            {
                field: 'volumn', title: '总体积', width: 80, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'weight', title: '总重量', width: 80, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'totalbox', title: '总箱数', width: 80, align: 'right',
                formatter: function (value, row, index) {
                    return formatterBigNumNoLen(value);
                }
            },
            {field: 'overnum', title: '剩余数量', width: 80, align: 'right', hidden: true},
            {field: 'auxunitid', title: '辅计量单位', width: 80, align: 'right', hidden: true}
        ]]
    });
    //
    function footerReCalc() {
        var $potable = $("#storage-table-distributeRejectAddPage");
        var data = $potable.datagrid('getRows');
        var unitnum = 0;
        var taxamount = 0;
        var totalbox = 0;
        var volumn = 0;
        var weight = 0;
        for (var i = 0; i < data.length; i++) {
            if (data[i].unitnum) {
                unitnum = Number(unitnum) + Number(data[i].unitnum);
            }
            if (data[i].taxamount) {
                taxamount = Number(taxamount) + Number(data[i].taxamount);
            }
            if (data[i].totalbox) {
                totalbox = Number(totalbox) + Number(data[i].totalbox);
            }
            if (data[i].volumn) {
                volumn = Number(volumn) + Number(data[i].volumn);
            }
            if (data[i].weight) {
                weight = Number(weight) + Number(data[i].weight);
            }
        }
        $potable.datagrid('reloadFooter', [
            {goodsid: '合计', unitnum: unitnum, taxamount: taxamount, volumn: volumn, weight: weight, totalbox: totalbox}
        ]);
    }
    function checkGoodsDetailEmpty() {
        var flag = true;
        var $table = $("#storage-table-distributeRejectAddPage");
        var data = $table.datagrid('getRows');
        for (var i = 0; i < data.length; i++) {
            if (data[i].goodsid && data[i].goodsid != "") {
                flag = false;
                break;
            }
        }
        return flag;
    }
    function form_submit(isaudit) {
        if (isaudit) {
            $("#saveAndAudit").val("audit");
        }
        $("#storage-form-distributeRejectAddPage").form({
            onSubmit: function () {
                var flag = $(this).form('validate');
                if (flag == false) {
                    return false;
                }
                loading("提交中..");
            },
            success: function (data) {
                var json = $.parseJSON(data);
                if (json.flag == true) {
                    loaded();
                    DataGridRefresh();
                    if (isaudit) {
                        $.messager.alert("提醒", "保存并审核成功");
                    } else {
                        $.messager.alert("提醒", "保存成功");
                    }
                    $("#storage-distributeRejectPage-id").val(json.backid);
                    $("#storage-panel-distributeRejectPage").panel('refresh', 'storage/distrtibution/distributeRejectEdit.do?billtype=${billtype}&type=edit&id=' + json.backid);
                } else {
                    if (isaudit) {
                        $.messager.alert("提醒", "保存并审核失败");
                    } else {
                        $.messager.alert("提醒", "保存失败");
                    }
                }
            }
        });
        $("#storage-form-distributeRejectAddPage").submit();
    }
    //添加详情Dialog
    function distributeRejectDetailAddDialog(billtye) {
        var DetailHref = "";
        var supplierid = $("#storage-distributeRejectAddPage-supplier").supplierWidget('getValue');
        var customerid = $("#storage-distributeRejectListPage-customer").customerWidget('getValue');
        DetailHref = "storage/distrtibution/distributeRejectDetailAdd.do?supplierid=" + supplierid + "&billtype=${billtype}&customerid=" + customerid;
        var flag = $("#storage-form-distributeRejectAddPage").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先完善配送入库单基本信息');
            return false;
        }
        var businessdate = $("#storage-distributeRejectAddPage-businessdate").val();
        if (businessdate == null) {
            $.messager.alert("提醒", "请先选择业务日期");
            $("#storage-distributeRejectAddPage-businessdate").focus();
            return false;
        }
        var $DetailOper = $("#storage-distributeRejectPage-dialog-DetailOper");
        $DetailOper.dialog({
            title: '商品信息新增(按ESC退出)',
            width: 600,
            height: 320,
            closed: true,
            cache: false,
            modal: true,
            resizable: true,
            href: DetailHref,
            onLoad: function () {
                $("#storage-distributerejectDetail-goodsid").focus();
            }
        });
        $DetailOper.dialog("open");
    }
    var EditInitData;
    function distributeRejectDetailEditDialog(initdata) {
        EditInitData = initdata;
        var $DetailOper = $("#storage-distributeRejectPage-dialog-DetailOper");
        $DetailOper.dialog({
            title: '商品信息修改(按ESC退出)',
            width: 600,
            height: 320,
            closed: true,
            cache: false,
            modal: true,
            resizable: true,
            href: "storage/distrtibution/distributeRejectDetailEdit.do?billtype=${billtype}",
            onLoad: function () {
                if ("${billtype}" == "1") {
                    $("#storage-distributerejectDetail-auxnum").focus();
                }
                if ("${billtype==2}") {
                    $("#storage-distributerejectDetail-unitnum").focus();
                }
            }
        });
        $DetailOper.dialog("open");
    }
    //判断 添加商品后
    function checkAfterAddGoods(goodsid) {
        if (goodsid == null || goodsid == "") {
            return false;
        }
        var $distributeRejectrtable = $("#storage-table-distributeRejectAddPage");
        var flag = true;
        if ($distributeRejectrtable.size() > 0) {
            var data = $distributeRejectrtable.datagrid('getRows');
            if (data != null && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    if (data[i].goodsid == goodsid) {
                        flag = false;
                        break;
                    }
                }
            }
        }
        return flag;
    }
    var Listurl = "/storage/distrtibution/showDistributeRejectListPage.do?entertype=" + "${billtype}";
    function DataGridRefresh() {
        try {
            tabsWindowURL(Listurl).$("#storage-table-distributeRejectListPage").datagrid('reload');
        } catch (e) {
        }
    }
    $(function () {
        //判断添加还是修改 add,edit
        var type = '${type}';
        storage_delivery_url = "";
        if (type == "add") {
            storage_delivery_url = "storage/distrtibution/distributeRejectAddPage.do?billtype=${billtype}";
        }
        if (type == "edit") {
            storage_delivery_url = "storage/distrtibution/distributeRejectEdit.do?billtype=${billtype}&id=${id}&type=edit";
        }
        if (type == "view") {
            var flag = '${flag}';
            if (flag == "true") {
                storage_delivery_url = "storage/distrtibution/distributeRejectEdit.do?billtype=${billtype}&id=${id}&type=view";
            }
            else {
                $.messager.alert("提醒", "该订单已不存在！");
                page_url = null;
            }
        }
        $("#storage-panel-distributeRejectPage").panel({
            href: storage_delivery_url,
            cache: false,
            maximized: true,
            border: false,
            onLoad: function () {
                /* if("
                ${billtype}"==1){ */
                $("#storage-distributeRejectAddPage-supplier").focus();
                /* }
                 if("
                ${billtype}"==2){
                 $("#storage-distributeRejectListPage-customer").focus();
                 }	 */
            }
        });
        //按钮
        $("#storage-buttons-distributeRejectPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/distrtibution/showDistributeRejectPageAdd.do">
                {
                    type: 'button-add',
                    handler: function () {
                        $("#storage-panel-distributeRejectPage").panel({
                            href: 'storage/distrtibution/distributeRejectAddPage.do?billtype=${billtype}',
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    },
                    url: 'storage/distrtibution/distributeRejectAddPage.do?billtype=${billtype}'
                },
                </security:authorize>
                <security:authorize url="/storage/distrtibution/distributeRejectInfoSave.do">
                {
                    id: 'button-save-distributeReject',
                    type: 'button-save',
                    handler: function () {
                        if (checkGoodsDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，请填写配送入库单商品信息");
                            return false;
                        }
                        var datarows = $("#storage-table-distributeRejectAddPage").datagrid('getRows');
                        var foots = $("#storage-table-distributeRejectAddPage").datagrid('getFooterRows');
                        if (datarows != null && datarows.length > 0) {
                            $("#storage-distrtibution-datagridValues").val(JSON.stringify(datarows));
                            $("#storage-distrtibution-foots").val(JSON.stringify(foots));
                        }
                        form_submit();
                    },
                },
                </security:authorize>
                <security:authorize url="/storage/distrtibution/audit.do">
                {
                    id: 'button-saveaudit-distributeReject',
                    type: 'button-audit',
                    handler: function () {
                        if (checkGoodsDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，入库单商品信息为空");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否审核此入库单？", function (r) {
                            if (r) {
                                var id = $("#storage-distributeRejectPage-id").val();
                                $.ajax({
                                    url: 'storage/distrtibution/audit.do?id=' + id,
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "审核成功");
                                            DataGridRefresh();
                                            parent.closeNowTab();
                                        } else {
                                            if (json.msg) {
                                                $.messager.alert("提醒", "审核失败" + json.msg);
                                            } else {
                                                $.messager.alert("提醒", "审核失败" + json.msg);
                                            }
                                        }
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "审核出错")
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/distrtibution/distributeRejectSaveAndAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        if (checkGoodsDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，请填写配送入库单商品信息");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否保存并审核此出库单？", function (r) {
                            if (r) {
                                var datarows = $("#storage-table-distributeRejectAddPage").datagrid('getRows');
                                var foots = $("#storage-table-distributeRejectAddPage").datagrid('getFooterRows');
                                if (datarows != null && datarows.length > 0) {
                                    $("#storage-distrtibution-datagridValues").val(JSON.stringify(datarows));
                                    $("#storage-distrtibution-foots").val(JSON.stringify(foots));
                                }
                                form_submit(true);
                            }
                        });
                    },
                },
                </security:authorize>
                <security:authorize url="/storage/distrtibution/opaudit.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        $.messager.confirm("提醒", "是否反审此入库单？", function (r) {
                            if (r) {
                                var id = $("#storage-distributeRejectPage-id").val();
                                var ischeck = $("#storage-distrtibution-Entryifchecked").val();
                                if ("1" == ischeck) {
                                    $.messager.alert("提醒", "抱歉，已经验收的不能反审");
                                    return false;
                                }
                                if (id != "") {
                                    loading("反审中..");
                                    $.ajax({
                                        url: 'storage/distrtibution/opaudit.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "反审成功");
                                                DataGridRefresh();
                                                parent.closeNowTab();
                                            } else {
                                                $.messager.alert("提醒", "反审失败</br>" + (null != json.msg ? json.msg : ""));
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
                <security:authorize url="/storage/distrtibution/showDistributeRejectPageDelete.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        var status = $("#storage-distrtibution-status").val();
                        if (status != "2" && status != "1") {
                            $.messager.alert("提醒", "抱歉，此入库单已审核,无法删除");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否删除此出库单？", function (r) {
                            if (r) {
                                var id = $("#storage-distributeRejectPage-id").val();
                                if (id != "") {
                                    loading("删除中..");
                                    $.ajax({
                                        url: 'storage/distrtibution/deleteEnter.do?id=' + id,
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "删除成功");
                                                DataGridRefresh();
                                                parent.closeNowTab();
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
                {
                    type: 'button-back',
                    handler: function (data) {
                        if (data != null && data.id != null && data.id != "") {
                            $("#storage-distributeRejectPage-id").val(data.id);
                            $("#storage-panel-distributeRejectPage").panel('refresh', 'storage/distrtibution/distributeRejectEdit.do?billtype=${billtype}&type=edit&id=' + data.id);
                        }
                    }
                },
                {
                    type: 'button-next',
                    handler: function (data) {
                        if (data != null && data.id != null && data.id != "") {
                            $("#storage-distributeRejectPage-id").val(data.id);
                            $("#storage-panel-distributeRejectPage").panel('refresh', 'storage/distrtibution/distributeRejectEdit.do?billtype=${billtype}&type=edit&id=' + data.id);
                        }
                    }
                },
                <security:authorize url="/print/delivery/deliveryorderPrintView.do">
                {
                    type: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/print/delivery/deliveryorderPrint.do">
                {
                    type: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}
            ],
            <c:if test="${billtype==2}">
            buttons: [
                {},
                <security:authorize url="/storage/delistributeReject/check.do">
                {
                    id: 'button-returnorder-check',
                    name: '验收',
                    iconCls: 'button-audit',
                    handler: function () {
                        var id = $("#storage-distributeRejectPage-id").val();
                        var ischeck = $("#storage-distrtibution-Entryifchecked").val();
                        if ("1" == ischeck) {
                            $.messager.alert("提醒", "抱歉，此单据已验收");
                            return false;
                        }
                        var status = $("#storage-distrtibution-status").val();
                        if (status != '3') {
                            $.messager.alert("提醒", "此单据未审核,不能验收");
                            return false;
                        }
                        $.messager.confirm("提醒", "确定验收此配送入库单？", function (r) {
                            if (r) {
                                loading("验收操作中..");
                                $.ajax({
                                    url: 'storage/distrtibution/check.do?id=' + id,
                                    dataType: 'json',
                                    type: 'post',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag == true) {
                                            $.messager.alert("提醒", "验收操作成功");
                                            DataGridRefresh();
                                            parent.closeNowTab();
                                        } else {
                                            $.messager.alert("提醒", "验收操作失败");
                                        }
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "验收操作出错");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                {},
            ],
            </c:if>
            //TODO
            layoutid: 'storage-distributereject-distributeRejectPage',
            model: 'bill',
            type: "view",//add
            tab: '入库单列表',
            taburl: Listurl,
            id: '${id}',
            datagrid: 'storage-table-distributeRejectListPage'
        });
        $(document).bind('keydown', 'ctrl+enter', function () {
            $("#storage-distributerejectDetail-addSaveGoOn").focus();
            $("#storage-distributerejectDetail-addSaveGoOn").trigger("click");
        });
        $(document).bind('keydown', '+', function () {
            $("#storage-distributerejectDetail-addSaveGoOn").focus();
            setTimeout(function () {
                $("#storage-distributerejectDetail-addSaveGoOn").trigger("click");
            }, 300);
            return false;
        });
        $(document).keydown(function (event) {
            switch (event.keyCode) {
                case 27: //Esc
                    $("#storage-distributerejectDetail-addSaveGoOn").focus();
                    $("#storage-distributeRejectPage-dialog-DetailOper").dialog('close');
                    break;
            }
        })
    })
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "distributeRejectListPage-dialog-print",
            code: "storage_delivery_enter",
            url_preview: "print/delivery/deliveryEnterPrintView.do",
            url_print: "print/delivery/deliveryorderPrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            exPrintParam: {
                billtype: "${billtype}",
                isenter: true
            },
            getData: getData
        });
        function getData(tableId, printParam) {
            var id = $("#storage-distributeRejectPage-id").val();
            if (id == "") {
                $.messager.alert("警告", "找不到要打印的信息!");
                return false;
            }
            printParam.idarrs = id;
            var printtimes = $("#storage-distrtibution-printtime").val();
            if (printtimes > 0)
                printParam.printIds = [id];
            return true;
        }
    });
</script>
<%--打印结束 --%>
</body>
</html>
