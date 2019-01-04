<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售订货单页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
    <script type="text/javascript" src="js/datagrid-detailview.js"></script>
    <%
        boolean showPrintOptions = false;
        boolean printOptionsOrderSeq = false;
    %>
    <security:authorize url="/sales/salesDeliveryOrderPrintOptions.do">
        <%
            showPrintOptions = true;
        %>
    </security:authorize>
    <security:authorize url="/sales/salesOrderPrintOptionsOrderSeq.do">
        <%
            printOptionsOrderSeq = true;
        %>
    </security:authorize>
</head>
<body>
<input type="hidden" id="sales-backid-orderGoodsPage" value="${id }"/>
<div id="sales-orderGoodsPage-layout" class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="sales-buttons-orderGoodsPage"></div>
    </div>
    <div data-options="region:'center'">
        <div class="easyui-panel" data-options="fit:true" id="sales-panel-orderGoodsPage"></div>
        <div id="orderPage-goods-history-price"></div>
    </div>
</div>
<div id="sales-deployDialog-order">
    <div id="sales-deployDialog-order-content" style="padding: 3px;"></div>
    <div class="easyui-menu" id="sales-goodsPriceMenu-orderPage"
         style="width: 400px;height:120px;display: none;background:#D8D7D7">
        <table id="sales-goodsPriceMenu-table"></table>
    </div>
    <div style="display:none">
        <div class="easyui-dialog" id="sales-dialog-orderPage" closed="true"></div>
    </div>
    <div id="sales-dialog-version-orderGoodsPage"></div>
    <div id="sales-changePriceDialog-orderGoodsPage"></div>
    <div id="sales-addorder-orderGoodsPage"></div>
</div>
    <script type="text/javascript">
        var order_taxpricechange = "0";
        var order_url = "sales/orderGoodsAddPage.do";
        var order_type = '${type}';
        if (order_type == "view" || order_type == "show" || order_type == "handle") {
            order_url = "sales/orderGoodsViewPage.do?id=${id}";
        }
        if (order_type == "edit") {
            order_url = "sales/orderGoodsEditPage.do?id=${id}";
        }
        if (order_type == "copy") {
            order_url = "sales/orderCopyPage.do?id=${id}";
        }

        var order_AjaxConn = function (Data, Action) {
            var MyAjax = $.ajax({
                type: 'post',
                cache: false,
                url: Action,
                data: Data,
                async: false,
                success: function (data) {
                    loaded();
                }
            });
            return MyAjax.responseText;
        };

        var tableColJson = $("#sales-datagrid-orderGoodsAddPage").createGridColumnLoad({
            frozenCol: [[
                {field: 'id', title: '明细编号', hidden: true}
            ]],
            commonCol: [[
                {field: 'ck', checkbox: true},
                {field: 'goodsid', title: '商品编码', width: 70, align: ' left', sortable: true},
                {
                    field: 'goodsname', title: '商品名称', width: 250, align: 'left', aliascol: 'goodsid',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            if (rowData.isdiscount == '1') {
                                return "（折扣）" + rowData.goodsInfo.name;
                            } else if (rowData.isdiscount == '2') {
                                return "（折扣）" + rowData.goodsInfo.name;
                            } else {
                                if (rowData.deliverytype == '1') {
                                    return "<font color='blue'>&nbsp;赠 </font>" + rowData.goodsInfo.name;
                                } else if (rowData.deliverytype == '2') {
                                    return "<font color='blue'>&nbsp;捆绑 </font>" + rowData.goodsInfo.name;
                                } else {
                                    return '<a id="sales-historyprice-orderpage" gid="' + rowData.goodsid + '" >' + rowData.goodsInfo.name + '</a>';
                                }
                            }
                        }else if (rowData.goodsname) {
                            return rowData.goodsname;
                        }else {
                            return '<a id="sales-historyprice-orderpage" gid="' + rowData.goodsid + '" ></a>';
                        }
                    }
                },
                {field: 'deliverytype', title: '商品类型', width: 70, align: ' left',hidden:true},
                {
                    field: 'spell', title: '助记符', width: 80, aliascol: 'goodsid', hidden: true, align: 'left',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != undefined) {
                            return rowData.goodsInfo.spell;
                        } else {
                            return "";
                        }
                    }
                },
                {field: 'shopid', title: '店内码', width: 80, aliascol: 'goodsid', hidden: true, align: 'left'},
                {
                    field: 'barcode', title: '条形码', width: 90, align: 'left', aliascol: 'goodsid',
                    formatter: function (value, row, rowIndex) {
                        if (row.isdiscount != '1' && row.goodsInfo != null) {
                            return row.goodsInfo.barcode;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'brandName', title: '商品品牌', width: 60, align: 'left', aliascol: 'goodsid', hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            return rowData.goodsInfo.brandName;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'boxnum', title: '箱装量', aliascol: 'goodsid', width: 45, align: 'right',
                    formatter: function (value, row, rowIndex) {
                        if (row.isdiscount != '1' && row.goodsInfo != null) {
                            return formatterBigNumNoLen(row.goodsInfo.boxnum);
                        } else {
                            return "";
                        }
                    }
                },
                {field: 'unitname', title: '单位', width: 35, align: 'left'},
                {
                    field: 'usablenum', title: '可用量', width: 60, align: 'right', isShow: true, sortable: true,
                    formatter: function (value, row, index) {
                        if (((row.isdiscount == '0' || row.isdiscount == '') && row.goodsInfo != null) || row.goodsid == "选中合计" || row.goodsid == "合计") {
                            return formatterBigNumNoLen(value);
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'unitnum', title: '数量', width: 80, align: 'right', sortable: true,
                    formatter: function (value, row, index) {
                        return formatterBigNumNoLen(value);
                    },
                    styler: function (value, row, index) {
                        var status = $("#sales-customer-status").val();
                        if (row.goodsid != null && row.goodsid != "合计" && row.goodsid != "选中合计" && (status == null || status == '1' || status == "2") && Number(row.usablenum) < Number(value)) {
                            return 'background-color:red;';
                        }
                    }
                },
                {
                    field: 'notorderunitnum', title: '未生成订单数量', width: 80, align: 'right', sortable: true,
                    formatter: function (value, row, index) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {
                    field: 'orderunitnum', title: '已生成订单数量', width: 80, align: 'right', sortable: true,
                    formatter: function (value, row, index) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {
                    field: 'referenceprice', title: '参考价', width: 60, align: 'right', isShow: true, sortable: true,
                    formatter: function (value, row, index) {
                        if (row.isdiscount == '0' ) {
                            return '<a id="sales-historyprice-orderpage" gid="' + row.goodsid + '" >' + formatterMoney(value) + '</a>';
                        }else{
                            return '';
                        }

                    }
                },
                {
                    field: 'taxprice', title: '单价', width: 60, align: 'right', sortable: true,
                    formatter: function (value, row, index) {
                        if (row.isdiscount != '1' && row.isdiscount != '2') {
                            if (parseFloat(value) > parseFloat(row.oldprice)) {
                                if (parseFloat(value) < parseFloat(row.lowestsaleprice)) {
                                    return "<font color='blue' style='cursor: pointer;' title='原价:" + formatterMoney(row.oldprice) + ",最低销售价:" + formatterMoney(row.lowestsaleprice) + "'>" + formatterMoney(value) + "</font></a>";
                                } else {
                                    return "<font color='blue' style='cursor: pointer;' title='原价:" + formatterMoney(row.oldprice) + "'>" + formatterMoney(value) + "</font>";
                                }
                            }
                            else if (parseFloat(value) < parseFloat(row.oldprice)) {
                                if (parseFloat(value) < parseFloat(row.lowestsaleprice)) {
                                    return "<font color='red' style='cursor: pointer;' title='原价:" + formatterMoney(row.oldprice) + ",最低销售价:" + formatterMoney(row.lowestsaleprice) + "'>" + formatterMoney(value) + "</font>";
                                } else {
                                    return "<font color='red' style='cursor: pointer;' title='原价:" + formatterMoney(row.oldprice) + "'>" + formatterMoney(value) + "</font>";
                                }
                            }
                            else {
                                if (parseFloat(value) < parseFloat(row.lowestsaleprice)) {
                                    return "<font style='cursor: pointer;' title='最低销售价:" + formatterMoney(row.lowestsaleprice) + "'>" + formatterMoney(value) + "</font>";
                                } else {
                                    if (parseFloat(value) != parseFloat(row.demandprice) && parseFloat(row.demandprice) > 0) {
                                        return "<font  style='cursor: pointer;' title='要货价:" + formatterMoney(row.demandprice) + "'>" + formatterMoney(value) + "</font>";
                                    } else {
                                        return formatterMoney(value);
                                    }
                                }
                            }
                        } else {
                            return "";
                        }
                    },
                    styler: function (value, row, index) {
                        if (row.isdiscount != '1' && row.isdiscount != '2') {
                            if (parseFloat(value) < parseFloat(row.lowestsaleprice)) {
                                return 'background-color:yellow;';
                            } else {
                                if (parseFloat(value) != parseFloat(row.demandprice) && parseFloat(row.demandprice) > 0) {
                                    return 'background-color:#F4AE8A;';
                                }
                            }
                        }
                    }
                },
                {
                    field: 'oldtaxprice', title: '原价', width: 60, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'basesaleprice', title: '基准价', width: 60, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                <security:authorize url="/sales/goodsbuyprice.do">
                {
                    field: 'buyprice', title: '采购价', width: 60, align: 'right', sortable: true, hidden: true,
                    formatter: function (value, row, index) {
                        if (row.isdiscount == '0' || row.isdiscount == '') {
                            return formatterMoney(value);
                        } else {
                            return "";
                        }
                    }
                },
                </security:authorize>
                {
                    field: 'boxprice', title: '箱价', width: 60, aliascol: 'taxprice', align: 'right',
                    formatter: function (value, row, index) {
                        if (row.isdiscount != '1' && row.isdiscount != '2') {
                            return formatterMoney(value);
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'taxamount', title: '金额', width: 60, align: 'right', sortable: true,
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
                {
                    field: 'taxtype', title: '税种', width: 60, align: 'left', hidden: true,
                    formatter: function (value, row, index) {
                        return row.taxtypename;
                    }
                },
                {
                    field: 'tax', title: '税额', width: 60, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {field: 'auxnumdetail', title: '辅数量', width: 60, align: 'right'},
//                {
//                    field: 'totalboxweight', title: '重量（千克）', width: 80, align: 'right',
//                    formatter: function (value, row, index) {
//                        return formatterMoney(value);
//                    }
//                },
//                {
//                    field: 'totalboxvolume', title: '体积（立方米）', width: 80, align: 'right',
//                    formatter: function (value, row, index) {
//                        if (value != undefined) {
//                            return Number(value).toFixed(3);
//                        }
//                    }
//                },
//                {
//                    field: 'storageid', title: '所属仓库', width: 80, align: 'left',
//                    formatter: function (value, row, index) {
//                        return row.storagename;
//                    }
//                },
//                {
//                    field: 'storagelocationid', title: '所属库位', width: 80, align: 'left', hidden: true,
//                    formatter: function (value, row, index) {
//                        return row.storagelocationname;
//                    }
//                },
//                {field: 'batchno', title: '批次号', width: 80, align: 'left'},
//                {field: 'produceddate', title: '生产日期', width: 80, align: 'left'},
                {field: 'remark', title: '备注', width: 200, align: 'left'}
            ]]
        });
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
        $(function () {
            $("#sales-panel-orderGoodsPage").panel({
                href: order_url,
                cache: false,
                maximized: true,
                border: false,
                onLoad: function () {
                    $("#sales-customer-orderGoodsAddPage").focus();
                }
            });



            //按钮
            $("#sales-buttons-orderGoodsPage").buttonWidget({
                initButton: [
                    {},
                    <security:authorize url="/sales/orderGoodsAddPage.do">
                    {
                        type: 'button-add',
                        handler: function () {
                            refreshPanel('sales/orderGoodsAddPage.do');
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sales/orderGoodsSave.do">
                    {
                        type: 'button-save',
                        handler: function () {
                            $("#sales-addType-orderGoodsAddPage").val("real");
                            var rows = $("#sales-datagrid-orderGoodsAddPage").datagrid('getRows');

                            if(rows[0].goodsid=='' || rows[0].goodsid==undefined){
                                $.messager.alert("提醒", "没有商品明细不允许保存");
                                return false;
                            }
                            $("#sales-goodsJson-orderGoodsAddPage").val(JSON.stringify(rows));
                            var auditflag = true;
                            var msg = "";
                            for (var i = 0; i < rows.length; i++) {
                                var obj = rows[i];
                                if (obj.isdiscount == "1") {
                                    for (var j = 0; j < rows.length && i != j; j++) {
                                        if (rows[j].goodsid == obj.goodsid && rows[j].isdiscount == "0" || rows[j].isdiscount == undefined) {
                                            if (rows[j].notaxprice == "0.000000" || rows[j].notaxprice == "0") {
                                                $.messager.alert("提醒", rows[j].goodsid + "商品单价为0,不允许添加商品折扣");
                                                return;
                                            }
                                        }
                                    }
                                } else if (obj.isdiscount == "2") {
                                    var brandid = obj.brandid;
                                    var count = 0;
                                    for (var j = 0; j < rows.length && i != j; j++) {
                                        //修改时判断
                                        if (rows[j].brandid != undefined) {
                                            if (rows[j].brandid == brandid && rows[j].notaxprice != "0.000000" && rows[j].notaxprice != "0") {
                                                ++count;
                                            }
                                        } else {//新增时判断
                                            if (rows[j].goodsInfo.brand == brandid && rows[j].notaxprice != "0.000000" && rows[j].notaxprice != "0") {
                                                ++count;
                                            }
                                        }
                                    }
                                    if (count == 0) {
                                        $.messager.alert("提醒", obj.goodsInfo.name + " 品牌商品单价为0,不允许添加品牌折扣");
                                        return;
                                    }
                                }
                                if (null != obj && obj.goodsid != null && obj.goodsid != "") {
                                    if (obj.lowestsaleprice > 0 && Number(obj.lowestsaleprice) - Number(obj.taxprice) > 0) {
                                        auditflag = false;
                                        if (msg == "") {
                                            msg = "商品：" + obj.goodsid + "，商品价格" + formatterMoney(obj.taxprice) + "低于最低销售价：" + formatterMoney(obj.lowestsaleprice) + "</br>";
                                        } else {
                                            msg += "商品：" + obj.goodsid + "，商品价格" + formatterMoney(obj.taxprice) + "低于最低销售价：" + formatterMoney(obj.lowestsaleprice) + "</br>";
                                        }
                                    }
                                }
                            }
                            if (!auditflag) {
                                $.messager.confirm("提醒", msg + "是否继续保存", function (r) {
                                    if (r) {
                                        $("#sales-saveaudit-orderGoodsDetailAddPage").val("save");
                                        $("#sales-form-orderGoodsAddPage").submit();
                                    }
                                });
                            } else {
                                $("#sales-saveaudit-orderGoodsDetailAddPage").val("save");
                                $("#sales-form-orderGoodsAddPage").submit();
                            }
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sales/orderGoodsSaveAudit.do">
                    {
                        type: 'button-saveaudit',
                        handler: function () {
                            var rows = $("#sales-datagrid-orderGoodsAddPage").datagrid('getRows');
                            if(rows[0].goodsid=='' || rows[0].goodsid==undefined){
                                $.messager.alert("提醒", "没有商品明细不允许保存");
                                return false;
                            }
                            $("#sales-goodsJson-orderGoodsAddPage").val(JSON.stringify(rows));
                            var auditflag = true;
                            var msg = "";
                            for (var i = 0; i < rows.length; i++) {
                                var obj = rows[i];
                                if (obj.isdiscount == "1") {
                                    for (var j = 0; j < rows.length && i != j; j++) {
                                        if (rows[j].goodsid == obj.goodsid && rows[j].isdiscount == "0" || rows[j].isdiscount == undefined) {
                                            if (rows[j].notaxprice == "0.000000" || rows[j].notaxprice == "0") {
                                                $.messager.alert("提醒", rows[j].goodsid + "商品单价为0,不允许添加商品折扣");
                                                return;
                                            }
                                        }
                                    }
                                } else if (obj.isdiscount == "2") {
                                    var brandid = obj.brandid;
                                    var count = 0;
                                    for (var j = 0; j < rows.length && i != j; j++) {
                                        //修改时判断
                                        if (rows[j].brandid != undefined) {
                                            if (rows[j].brandid == brandid && rows[j].notaxprice != "0.000000" && rows[j].notaxprice != "0") {
                                                ++count;
                                            }
                                        } else {//新增时判断
                                            if (rows[j].goodsInfo.brand == brandid && rows[j].notaxprice != "0.000000" && rows[j].notaxprice != "0") {
                                                ++count;
                                            }
                                        }
                                    }
                                    if (count == 0) {
                                        $.messager.alert("提醒", obj.goodsInfo.name + " 品牌商品单价为0,不允许添加品牌折扣");
                                        return;
                                    }
                                }
                                if (null != obj && obj.goodsid != null && obj.goodsid != "") {
                                    if (obj.lowestsaleprice > 0 && Number(obj.lowestsaleprice) - Number(obj.taxprice) > 0) {
                                        auditflag = false;
                                        if (msg == "") {
                                            msg = "商品：" + obj.goodsid + "，商品价格" + formatterMoney(obj.taxprice) + "低于最低销售价：" + formatterMoney(obj.lowestsaleprice) + "</br>";
                                        } else {
                                            msg += "商品：" + obj.goodsid + "，商品价格" + formatterMoney(obj.taxprice) + "低于最低销售价：" + formatterMoney(obj.lowestsaleprice) + "</br>";
                                        }
                                    }
                                }
                            }
                            if (!auditflag) {
                                $.messager.confirm("提醒", msg + "是否继续保存审核", function (r) {
                                    if (r) {
                                        $("#sales-addType-orderGoodsAddPage").val("real");
                                        $("#sales-saveaudit-orderGoodsDetailAddPage").val("saveaudit");
                                        $("#sales-form-orderGoodsAddPage").submit();
                                    }
                                });
                            } else {
                                $.messager.confirm("提醒", "确定保存并审核订单信息？", function (r) {
                                    if (r) {
                                        $("#sales-addType-orderGoodsAddPage").val("real");
                                        $("#sales-saveaudit-orderGoodsDetailAddPage").val("saveaudit");
                                        $("#sales-form-orderGoodsAddPage").submit();
                                    }
                                });
                            }
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sales/orderGiveup.do">
                    {
                        type: 'button-giveup',
                        handler: function () {
                            var type = $("#sales-buttons-orderGoodsPage").buttonWidget("getOperType");
                            if (type == "add") {
                                var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
                                top.closeTab(currTitle);
                            }
                            else if (type == "edit") {
                                var id = $("#sales-backid-orderPage").val();
                                if (id == "") {
                                    return false;
                                }
                                $("#sales-panel-orderGoodsPage").panel('refresh', 'sales/orderViewPage.do?id=' + id);
                            }
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sales/orderGoodsDelete.do">
                    {
                        type: 'button-delete',
                        handler: function () {
                            var id = $("#sales-backid-orderGoodsPage").val();
                            if (id == '') {
                                return false;
                            }
                            $.messager.confirm("提醒", "确定删除该订单信息？", function (r) {
                                if (r) {
                                    loading("删除中..");
                                    $.ajax({
                                        url: 'sales/deleteOrderGoods.do',
                                        dataType: 'json',
                                        type: 'post',
                                        data: 'id=' + id,
                                        success: function (json) {
                                            loaded();
                                            if (json.delFlag == true) {
                                                $.messager.alert("提醒", "该信息已被其他信息引用，无法删除");
                                                return false;
                                            }
                                            if (json.flag == true) {
                                                $.messager.alert("提醒", "删除成功");
                                                var data = $("#sales-buttons-orderGoodsPage").buttonWidget("removeData", '');
                                                if (data != null) {
                                                    $("#sales-backid-orderGoodsPage").val(data.id);
                                                    refreshPanel('sales/orderGoodsEditPage.do?id=' + data.id);
                                                }
                                                else {
                                                    parent.closeNowTab();
                                                }
                                            }
                                            else {
                                                $.messager.alert("提醒", "删除失败");
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "删除失败");
                                        }
                                    });
                                }
                            });
                        }
                    },
                    </security:authorize>
                    <%--<security:authorize url="/sales/orderCopy.do">--%>
                    <%--{--%>
                        <%--type: 'button-copy',--%>
                        <%--handler: function () {--%>
                            <%--var id = $("#sales-backid-orderPage").val();--%>
                            <%--if (id == '') {--%>
                                <%--return false;--%>
                            <%--}--%>
                            <%--top.addOrUpdateTab('sales/orderPage.do?type=copy&id=' + id, "销售订单新增");--%>
                            <%--//$("#sales-panel-orderGoodsPage").panel('refresh', 'sales/orderCopyPage.do?id='+ id);--%>
                        <%--}--%>
                    <%--},--%>
                    <%--</security:authorize>--%>
                    <security:authorize url="/sales/orderGoodsAudit.do">
                    {
                        type: 'button-audit',
                        handler: function () {
                            var id = $("#sales-backid-orderGoodsPage").val();
                            if (id == '') {
                                return false;
                            }
                            var salesuser = $("#sales-salesMan-orderGoodsAddPage").widget('getValue');
                            if (salesuser == "") {
                                $.messager.alert("提醒", "客户业务员为空，不允许超级审核!");
                                return false;
                            }
                            loading("核对验证中...");
                            $.getJSON("sales/canAuditOrderGoods.do", {ids: id}, function (json) {
                                loaded();
                                if (json.flag == true) {
                                    $.messager.confirm("提醒", "确定审核该订货单信息？", function (r) {
                                        if (r) {
                                            loading("审核中...");
                                            $.ajax({
                                                url: 'sales/auditOrderGoods.do',
                                                dataType: 'json',
                                                type: 'post',
                                                data: 'id=' + id + '&type=1',
                                                success: function (json) {
                                                    loaded();
                                                    if (json.flag == true) {
                                                        if (json.billId == "") {
                                                            $.messager.alert("提醒", "审核成功，自动生成发货通知单失败。");
                                                        }
                                                        else {
                                                            $.messager.alert("提醒", "审核成功");
                                                        }
                                                        $("#sales-customer-status").val("3");
                                                        $("#sales-buttons-orderGoodsPage").buttonWidget("setDataID", {
                                                            id: id,
                                                            state: '3',
                                                            type: 'view'
                                                        });
                                                        $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem", "button-printview-orderblank");
                                                        $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem", "button-print-orderblank");
                                                        $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem", "button-printview-DispatchBill");
                                                        $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem", "button-print-DispatchBill");
                                                        $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem", "button-printview-DeliveryOrder");
                                                        $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem", "button-print-DeliveryOrder");
                                                        $("#sales-panel-orderGoodsPage").panel('refresh', 'sales/orderGoodsEditPage.do?id=' + id);
                                                    }
                                                    else {
                                                        $.messager.alert("提醒", "审核失败<br/>" + json.msg);
                                                    }
                                                },
                                                error: function () {
                                                    loaded();
                                                    $.messager.alert("错误", "审核出错");
                                                }
                                            });
                                        }
                                    });
                                }
                                else {
                                    loaded();
                                    $.messager.confirm("提醒", json.msg + "是否继续审核？", function (r) {
                                        if (r) {
                                            loading("审核中...");
                                            $.ajax({
                                                url: 'sales/auditOrderGoods.do',
                                                dataType: 'json',
                                                type: 'post',
                                                data: 'id=' + id + '&type=1',
                                                success: function (json) {
                                                    loaded();
                                                    if (json.flag == true) {
                                                        if (json.billId == "") {
                                                            $.messager.alert("提醒", "审核成功，自动生成发货通知单失败。");
                                                        }
                                                        else {
                                                            $.messager.alert("提醒", "审核成功，" + json.msg);
                                                        }
                                                        $("#sales-customer-status").val("3");
                                                        $("#sales-buttons-orderGoodsPage").buttonWidget("setDataID", {
                                                            id: id,
                                                            state: '3',
                                                            type: 'view'
                                                        });

                                                        $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem", "button-printview-orderblank");
                                                        $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem", "button-print-orderblank");
                                                        $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem", "button-printview-DispatchBill");
                                                        $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem", "button-print-DispatchBill");
                                                        $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem", "button-printview-DeliveryOrder");
                                                        $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem", "button-print-DeliveryOrder");
                                                        $("#sales-panel-orderGoodsPage").panel('refresh', 'sales/orderGoodsEditPage.do?id=' + id);
                                                    }
                                                    else {
                                                        $.messager.alert("提醒", "审核失败<br/>" + json.msg);
                                                    }
                                                },
                                                error: function () {
                                                    loaded();
                                                    $.messager.alert("错误", "审核出错");
                                                }
                                            });
                                        }
                                    });
                                }
                            });

                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sales/orderGoodsOppaudit.do">
                    {
                        type: 'button-oppaudit',
                        handler: function () {
                            var id = $("#sales-backid-orderGoodsPage").val();
                            if (id == '') {
                                return false;
                            }
                            var businessdate = $("#sales-businessdate-orderGoodsAddPage").val();
                            var flag = isDoneOppauditBillCaseAccounting(businessdate);
                            if (!flag) {
                                $.messager.alert("提醒", "业务日期不在会计区间内或未设置会计区间,不允许反审!");
                                return false;
                            }
                            var auditflag = true;
                            <security:authorize url="/sales/orderOppauditSupper.do">
                            auditflag = false;
                            </security:authorize>
                            if (auditflag) {
                                var businessdate = $("#sales-businessdate-orderGoodsAddPage").val();
                                if (businessdate != '${today}') {
                                    $.messager.alert("提醒", "销售订单不能反审，业务日期不是今天。需要有权限的人才能反审！");
                                    return false;
                                }
                            }
                            $.messager.confirm("提醒", "确定反审该订单信息？", function (r) {
                                if (r) {
                                    loading("反审中..");
                                    $.ajax({
                                        url: 'sales/auditOrderGoods.do',
                                        dataType: 'json',
                                        type: 'post',
                                        data: 'id=' + id + '&type=2',
                                        success: function (json) {
                                            loaded();
                                            if (json.orderflag) {
                                                $.messager.alert("提醒", "该单据已经生成过销售订单，不允许反审");
                                                return false;
                                            }  if (json.demandflag) {
                                                $.messager.alert("提醒", "该单据已经关联过要货单，不允许反审");
                                                return false;
                                            }if (!json.bigflag) {
                                                $.messager.alert("提醒", "状态条件不允许反审或生成关联的发货单已生成大单发货，不允许反审");
                                            } else {
                                                if (json.billArg == false) {
                                                    $.messager.alert("提醒", "反审失败，下游单据已生成并审核，无法反审");
                                                }
                                                else {
                                                    if (json.flag == true) {
                                                        $.messager.alert("提醒", "反审成功");
                                                        //$("#sales-customer-status").val("2");
                                                        //$("#sales-buttons-orderGoodsPage").buttonWidget("setDataID", {id:id, state:'2', type:'edit'});
                                                        $("#sales-panel-orderGoodsPage").panel('refresh', 'sales/orderGoodsEditPage.do?id=' + id);
                                                    }
                                                    else {
                                                        $.messager.alert("提醒", "反审失败");
                                                    }
                                                }
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "反审出错");
                                        }
                                    });
                                }
                            });
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sales/orderGoodsBack.do">
                    {
                        type: 'button-back',
                        handler: function (data) {
                            $("#sales-backid-orderGoodsPage").val(data.id);
                            refreshPanel('sales/orderGoodsEditPage.do?id=' + data.id);
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sales/orderGoodsNext.do">
                    {
                        type: 'button-next',
                        handler: function (data) {
                            $("#sales-backid-orderGoodsPage").val(data.id);
                            refreshPanel('sales/orderGoodsEditPage.do?id=' + data.id);
                        }
                    },
                    </security:authorize>
                    {}
                ],
                buttons: [
                    <security:authorize url="/sales/invalidOrderGoodsClose.do">
                    {
                        id: 'button-invalid',
                        name: '作废',
                        iconCls: 'button-delete',
                        handler: function () {
                            var id = $("#sales-backid-orderGoodsPage").val();
                            if (id == '') {
                                return false;
                            }
                            var checkflag=false;
                            var chekcmsg="";
                            $.ajax({
                                url: 'sales/checkInvalidOrderGoods.do',
                                dataType: 'json',
                                type: 'post',
                                async:false,
                                data: 'id=' + id,
                                success: function (json) {
                                    checkflag=json.flag;
                                    chekcmsg=json.msg;
                                },
                                error: function () {
                                    loaded();
                                    $.messager.alert("错误", "作废出错");
                                }
                            });
                            var confirmmsg="确定作废该订货单信息？";
                            if(checkflag){
                                confirmmsg=chekcmsg+",确认作废？"
                            }
                            $.messager.confirm("提醒", confirmmsg, function (r) {
                                if (r) {
                                    loading("作废中..");
                                    $.ajax({
                                        url: 'sales/doInvalidOrderGoods.do',
                                        dataType: 'json',
                                        type: 'post',
                                        data: 'id=' + id + '&type=1',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag == true) {
                                                $.messager.alert("提醒", "作废成功。");
                                                $("#sales-panel-orderGoodsPage").panel('refresh', 'sales/orderGoodsEditPage.do?id=' + id);
                                            }
                                            else {
                                                $.messager.alert("提醒", "作废失败<br/>" + json.msg);
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "作废出错");
                                        }
                                    });
                                }
                            });
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sales/invalidOrderGoodsOpen.do">
                    {
                        id: 'button-uninvalid',
                        name: '取消作废',
                        iconCls: 'button-oppaudit',
                        handler: function () {
                            var id = $("#sales-backid-orderGoodsPage").val();
                            if (id == '') {
                                return false;
                            }
                            $.messager.confirm("提醒", "确定作废该订货单信息？", function (r) {
                                if (r) {
                                    loading("取消作废中..");
                                    $.ajax({
                                        url: 'sales/doInvalidOrderGoods.do',
                                        dataType: 'json',
                                        type: 'post',
                                        data: 'id=' + id + '&type=2',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag == true) {
                                                $.messager.alert("提醒", "取消作废成功。");
                                                $("#sales-panel-orderGoodsPage").panel('refresh', 'sales/orderGoodsEditPage.do?id=' + id);
                                            }
                                            else {
                                                $.messager.alert("提醒", "取消作废失败<br/>" + json.msg);
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "取消作废出错");
                                        }
                                    });
                                }
                            });
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sales/salesOrderGoodsPrintViewBtn.do">
                    {
                        id: 'button-printview-salesOrderGoods',
                        name: '打印预览',
                        iconCls: 'button-preview',
                        handler: function () {
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sales/salesOrderGoodsPrintBtn.do">
                    {
                        id: 'button-print-salesOrderGoods',
                        name: '打印',
                        iconCls: 'button-print',
                        handler: function () {
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sales/orderGoodsAddOrder.do">
                    {
                        id: 'button-addorder-order',
                        name: '生成订单',
                        iconCls: 'button-add',
                        handler: function () {
                            $('<div id="sales-addorder-orderGoodsPage-content"></div>').appendTo('#sales-addorder-orderGoodsPage');
                            $("#sales-addorder-orderGoodsPage-content").dialog({ //弹出新添加窗口
                                title: '生成销售订单',
                                maximizable: true,
                                width: 1000,
                                height: 450,
                                closed: false,
                                modal: true,
                                cache: false,
                                resizable: true,
                                queryParams:{
                                    customerid:$("#sales-customer-orderGoodsAddPage").customerWidget("getValue"),
                                    orderid:$("#sales-id-orderGoodsAddPage").val(),
                                    date:$("#sales-businessdate-orderGoodsAddPage").val()
                                },
                                href: 'sales/showGoodsToOrderPage.do?id='+$("#sales-id-orderGoodsAddPage").val(),
                                onClose: function () {
                                    $('#sales-addorder-orderGoodsPage-content').dialog("destroy");
                                },
                                onLoad: function () {

                                },
                                buttons:[
                                    {
                                        iconCls: 'button-save',
                                        text:'生成订单',
                                        handler:function(){
                                            addOrder();
                                        }
                                    },
                                    {
                                        iconCls: 'button-close',
                                        text:'关闭',
                                        handler:function(){
                                            $('#sales-addorder-orderGoodsPage-content').dialog("destroy");
                                        }
                                    }
                                ]
                            });
                        }
                    },
                    </security:authorize>
                    {}
                ],
                layoutid: 'sales-orderGoodsPage-layout',
                model: 'bill',
                type: 'view',
                tab: '销售订货表',
                taburl: '/sales/orderGoodsListPage.do',
                id: '${id}',
                datagrid: 'sales-datagrid-orderGoodsListPage'
            });
            $(document).keydown(function (event) {//alert(event.keyCode);
                switch (event.keyCode) {
                    case 13: //Enter
                        if (chooseNo == "saleorder.remark") {
                            $("input[name='order.remark']").blur();
                            beginAddDetail();
                        }
                        if (chooseNo == "unitnum") {
                            $("input[name=auxnum]").focus();
                            return false;
                        }
                        if (chooseNo == "auxnum") {
                            $("input[name=overnum]").focus();
                            return false;
                        }
                        if (chooseNo == "overnum") {
                            if ($("input[name=taxprice]").attr("readonly") == "readonly") {
                                $("input[name=remark]").focus();
                            }
                            else {
                                $("input[name=taxprice]").focus();
                            }
                            return false;
                        }
                        if (chooseNo == "taxprice") {
                            $("input[name=boxprice]").focus();
                            return false;
                        }
                        if (chooseNo == "boxprice") {
                            $("input[name=remark]").focus();
                            return false;
                        }
                        if (chooseNo == "remark") {
                            $("input[name=savegoon]").click();
                            return false;
                        }
                        break;
                    case 27: //Esc
                        $("#sales-remark-orderGoodsDetailAddPage").focus();
                        $("#sales-dialog-orderGoodsAddPage-content").dialog('close');
                        break;
                    //case 37: //left
                    //	$("#button-back").click();
                    //break;
                    //case 39: //right
                    //	$("#button-next").click();
                    //break;
                    case 65: //a
                        if (event.altKey) {
                            $("#button-add").click();
                        }
                        break;
                    case 83: //s
                        if (event.ctrlKey) {
                            $("#button-save").click();
                            return false;
                        }
                        break;
                }
            });
            $(document).bind('keydown', 'ctrl+enter', function () {
                $("#sales-savegoon-orderGoodsDetailAddPage").focus();
                $("#sales-savegoon-orderGoodsDetailAddPage").trigger("click");
            });
            $(document).bind('keydown', '+', function () {
                var saveDetailFlag = $("#sales-saveDetailFlag-orderGoodsDetailAddPage").val();
                if (saveDetailFlag == "1") {
                    return;
                }
                $("#sales-savegoon-orderGoodsDetailAddPage").focus();
                setTimeout(function () {
                    $("#sales-savegoon-orderGoodsDetailAddPage").trigger("click");
                }, 300);
                return false;
            });

        });
        function refreshPanel(url) { //更新panel
            //客户信息归零

            $("#sales-panel-orderGoodsPage").panel('refresh', url);
        }
        function refreshPanelAddParam(url, param) { //更新panel
            //客户信息归零
            $("#sales-panel-orderGoodsPage").panel({queryParams: param, href: url});
        }

        //客户变更后 更新明细价格以及相关信息
        function changeGoodsPrice() {
            var oldcustomerid = $("#sales-customer-hidden-orderGoodsAddPage").val();
            var customerid = $("#sales-customer-orderGoodsAddPage").customerWidget("getValue");
            $("#sales-customer-hidden-orderGoodsAddPage").val(customerid);

            if (oldcustomerid != null && oldcustomerid != "" && oldcustomerid != customerid) {
                loading("客户变更，明细价格调整中");
                var rows = $wareList.datagrid('getRows');
                var count = 0;
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].isdiscount == null || rows[i].isdiscount == '0') {
                        var goodsid = rows[i].goodsid;
                        var num = rows[i].unitnum;
                        var date = $("input[name='saleorder.businessdate']").val();
                        if (goodsid != null && goodsid != "") {
                            var row = rows[i];
                            $.ajax({
                                url: 'sales/countSalesGoodsByCustomer.do',
                                dataType: 'json',
                                type: 'post',
                                data: {customerid: customerid, goodsid: goodsid, num: num, date: date},
                                async: false,
                                success: function (json) {
                                    var rowIndex = $wareList.datagrid("getRowIndex", row);
                                    row.taxprice = json.taxprice;
                                    row.notaxprice = json.notaxprice;
                                    row.taxamount = json.taxamount;
                                    row.notaxamount = json.notaxamount;
                                    row.tax = json.tax;
                                    row.oldprice = json.taxprice;
                                    $wareList.datagrid('updateRow', {index: rowIndex, row: row});
                                }
                            });
                        }
                        count++;
                    }
                }
                if (count > 0) {
                    $("#sales-customer-hidden-orderGoodsAddPage").val(customerid);
                    $.messager.alert("提醒", "客户变更，自动调整订单明细中的商品价格！");

                }
                loaded();
            } else {

            }

        }

        var insertIndex = undefined;
        function beginAddDetail(flag) { //开始添加商品信息
            if (flag) {
                var row = $wareList.datagrid('getSelected');
                insertIndex = $wareList.datagrid('getRowIndex', row);
                insertIndex = insertIndex - 1;
            }
            var customer = $("#sales-customer-orderGoodsAddPage").customerWidget("getValue");
            if (customer == '') {
                $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
                $("#sales-customer-orderGoodsAddPage").focus();
                return false;
            }
            $('<div id="sales-dialog-orderGoodsAddPage-content"></div>').appendTo('#sales-dialog-orderGoodsAddPage');
            $("#sales-dialog-orderGoodsAddPage-content").dialog({ //弹出新添加窗口
                title: '商品信息添加(按ESC退出)',
                maximizable: true,
                width: 620,
                height: 450,
                closed: false,
                modal: true,
                cache: false,
                resizable: true,
                href: 'sales/showOrderGoodsDetailAddPage.do?cid=' + customer,
                onClose: function () {
                    $('#sales-dialog-orderGoodsAddPage-content').dialog("destroy");
                },
                onLoad: function () {
                    $("#sales-goodsId-orderGoodsDetailAddPage").focus();
                }
            });
        }
        function addSaveDetail(go) { //添加新数据确定后操作，
            var flag = $("#sales-form-orderGoodsDetailAddPage").form('validate');
            if (flag == false) {
                return false;
            }
            var form = $("#sales-form-orderGoodsDetailAddPage").serializeJSON();
            var goodsJson = $("#sales-goodsId-orderGoodsDetailAddPage").goodsWidget('getObject');
            form.goodsInfo = goodsJson;
            var customer = $("#sales-customer-orderGoodsAddPage").widget('getValue');
            form.fixnum = form.unitnum;
            form.orderunitnum=0;
            form.notorderunitnum=form.unitnum;
            if (form.overnum != 0) {
                if (form.auxnum == null || form.auxnum == "") {
                    form.auxnum = 0;
                }
                form.auxnumdetail = form.auxnum + form.auxunitname + form.overnum + form.unitname;
            } else {
                form.auxnumdetail = form.auxnum + form.auxunitname;
            }

            var rowIndex = 0;
            var rows = $wareList.datagrid('getRows');
            var updateFlag = false;
            for (var i = 0; i < rows.length; i++) {
                var rowJson = rows[i];
                <c:if test="${isrepeat == '0'}">
                if (rowJson.goodsid == goodsJson.id) {
                    rowIndex = i;
                    updateFlag = true;
                    break;
                }
                </c:if>
                if (rowJson.goodsid == undefined && rowJson.brandid == undefined) {
                    rowIndex = i;
                    break;
                }
            }
            if (rowIndex == rows.length - 1) {
                $wareList.datagrid('appendRow', {}); //如果是最后一行则添加一新行
            }
            <c:if test="${isrepeat == '0'}">
            if (updateFlag) {
                $.messager.alert("提醒", "此商品已经添加！");
                return false;
            }
            </c:if>
            if (insertIndex == undefined) {
                $wareList.datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
            }
            else {
                $wareList.datagrid('insertRow', {index: insertIndex + 1, row: form});
                insertIndex = undefined;
            }
            if (go) { //go为true确定并继续添加一条
                $("#sales-form-orderGoodsDetailAddPage").form("clear");
                $("input[name=deliverydate]").val(deliverydate);
                $("#sales-deliverytype-orderGoodsDetailAddPage").val("0");
            }
            else { //否则直接关闭
                $("#sales-dialog-orderGoodsAddPage-content").dialog('close', true)
            }

            countTotal(); //第添加一条商品明细计算一次合计
        }
        function countTotal() { //计算合计
            var checkrows = $("#sales-datagrid-orderGoodsAddPage").datagrid('getChecked');
            var usablenum = 0;
            var unitnum = 0;
            var taxamount = 0;
            var notaxamount = 0;
            var tax = 0;
            var totalbox = 0;
            var totalboxweight = 0;
            var totalboxvolume = 0;
            for (var i = 0; i < checkrows.length; i++) {
                usablenum += Number(checkrows[i].usablenum == undefined ? 0 : checkrows[i].usablenum);
                unitnum += Number(checkrows[i].unitnum == undefined ? 0 : checkrows[i].unitnum);
                taxamount += Number(checkrows[i].taxamount == undefined ? 0 : checkrows[i].taxamount);
                notaxamount += Number(checkrows[i].notaxamount == undefined ? 0 : checkrows[i].notaxamount);
                tax += Number(checkrows[i].tax == undefined ? 0 : checkrows[i].tax);
                totalbox += Number(checkrows[i].totalbox == undefined ? 0 : checkrows[i].totalbox);
                totalboxweight += Number(checkrows[i].totalboxweight == undefined ? 0 : checkrows[i].totalboxweight);
                totalboxvolume += Number(checkrows[i].totalboxvolume == undefined ? 0 : checkrows[i].totalboxvolume);
            }
            totalboxweight = formatterMoney(totalboxweight);
            totalboxweight = formatterMoney(totalboxweight);
            var foot = [{
                goodsid: '选中合计',
                usablenum: usablenum,
                unitnum: unitnum,
                taxamount: taxamount,
                notaxamount: notaxamount,
                tax: tax,
                auxnumdetail: formatterMoney(totalbox) + "箱",
                totalboxvolume: totalboxvolume,
                totalboxweight: totalboxweight
            }];
            //合计
            var rows = $("#sales-datagrid-orderGoodsAddPage").datagrid('getRows');
            var usablenumSum = 0;
            var unitnumSum = 0;
            var taxamountSum = 0;
            var notaxamountSum = 0;
            var taxSum = 0;
            var totalboxSum = 0;
            var totalboxweightSum = 0;
            var totalboxvolumeSum = 0;
            for (var i = 0; i < rows.length; i++) {
                usablenumSum += Number(rows[i].usablenum == undefined ? 0 : rows[i].usablenum);
                unitnumSum += Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
                taxamountSum += Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
                notaxamountSum += Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
                taxSum += Number(rows[i].tax == undefined ? 0 : rows[i].tax);
                totalboxSum += Number(rows[i].totalbox == undefined ? 0 : rows[i].totalbox);
                totalboxweightSum += Number(rows[i].totalboxweight == undefined ? 0 : rows[i].totalboxweight);
                totalboxvolumeSum += Number(rows[i].totalboxvolume == undefined ? 0 : rows[i].totalboxvolume);
            }
            totalboxSum = formatterMoney(totalboxSum);
            var footSum = {
                goodsid: '合计',
                usablenum: usablenumSum,
                unitnum: Number(unitnumSum.toFixed(${decimallen})),
                taxamount: taxamountSum,
                notaxamount: notaxamountSum,
                tax: taxSum,
                auxnumdetail: totalboxSum + "箱",
                totalboxvolume: totalboxvolumeSum,
                totalboxweight: totalboxweightSum
            };
            foot.push(footSum);
            $("#sales-datagrid-orderGoodsAddPage").datagrid('reloadFooter', foot);
        }

        //修改商品折扣页面
        function beginEditDetailDiscount() {
            var customer = $("#sales-customer-orderGoodsAddPage").customerWidget("getValue");
            if (customer == '') {
                $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
                $("#sales-customer-orderGoodsAddPage").focus();
                return false;
            }
            var row = $wareList.datagrid('getSelected');
            if (row == null) {
                $.messager.alert("提醒", "请选择一条记录");
                return false;
            }
            $('<div id="sales-dialog-orderGoodsAddPage-content"></div>').appendTo('#sales-dialog-orderGoodsAddPage');
            $("#sales-dialog-orderGoodsAddPage-content").dialog({
                title: '商品折扣修改',
                width: 680,
                height: 400,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                resizable: true,
                closed: true,
                cache: false,
                href: 'sales/orderGoodsDiscountEditPage.do',
                modal: true,
                onClose: function () {
                    $("#sales-dialog-orderGoodsAddPage-content").dialog("destroy");
                },
                onLoad: function () {
                    getNumberBox("sales-order-discount").focus();
                    getNumberBox("sales-order-discount").select();
                }
            });
            $("#sales-dialog-orderGoodsAddPage-content").dialog("open");
        }

        //品牌折扣修改页面
        function beginEditDetailBrandDiscount() {
            var customer = $("#sales-customer-orderGoodsAddPage").customerWidget("getValue");
            if (customer == '') {
                $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
                $("#sales-customer-orderGoodsAddPage").focus();
                return false;
            }
            var row = $wareList.datagrid('getSelected');
            if (row == null) {
                $.messager.alert("提醒", "请选择一条记录");
                return false;
            }
            $('<div id="sales-dialog-orderGoodsAddPage-content"></div>').appendTo('#sales-dialog-orderGoodsAddPage');
            $("#sales-dialog-orderGoodsAddPage-content").dialog({
                title: '品牌折扣修改',
                width: 430,
                height: 350,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                resizable: true,
                closed: true,
                cache: false,
                href: 'sales/orderGoodsBrandDiscountEditPage.do',
                modal: true,
                onClose: function () {
                    $("#sales-dialog-orderGoodsAddPage-content").dialog("destroy");
                },
                onLoad: function () {
                    getNumberBox("sales-order-discount").focus();
                    getNumberBox("sales-order-discount").select();
                }
            });
            $("#sales-dialog-orderGoodsAddPage-content").dialog("open");
        }
        function beginEditDetail(rowData) { //开始修改商品信息
            var customer = $("#sales-customer-orderGoodsAddPage-hidden").val();
            if (customer == '') {
                $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
                $("#sales-customer-orderGoodsAddPage").focus();
                return false;
            }
            if (rowData == null) {
                $.messager.alert("提醒", "请选择一条记录");
                return false;
            }
            var row = rowData;
            row.goodsname = row.goodsInfo.name;
            row.brandName = row.goodsInfo.brandName;
            row.barcode = row.goodsInfo.barcode;
            var url = '';
            if (row.goodsid == undefined) {
                beginAddDetail();
            }
            else {
                url = 'sales/orderGoodsDetailEditPage.do?cid=' + customer + '&goodsid=' + row.goodsid; //如果是修改页面，数据直接来源于datagrid中的json数据。
                $('<div id="sales-dialog-orderGoodsAddPage-content"></div>').appendTo('#sales-dialog-orderGoodsAddPage');
                $("#sales-dialog-orderGoodsAddPage-content").dialog({ //弹出修改窗口
                    title: '商品信息修改(按ESC退出)',
                    maximizable: true,
                    width: 620,
                    height: 450,
                    closed: false,
                    modal: true,
                    cache: false,
                    resizable: true,
                    href: url,
                    onClose: function () {
                        $('#sales-dialog-orderGoodsAddPage-content').dialog("destroy");
                    },
                    onLoad: function () {
                        $("#sales-form-orderGoodsDetailEditPage").form('load', row);
                        //重量和体积
                        if (null != row.goodsInfo.grossweight && null != row.goodsInfo.singlevolume) {
                            var totalboxweight = row.unitnum * row.goodsInfo.grossweight;
                            var totalboxvolume = row.unitnum * row.goodsInfo.singlevolume;
                            $("#sales-totalboxweight-orderGoodsDetailAddPage").val(totalboxweight.toFixed(6));
                            $("#sales-totalboxvolume-orderGoodsDetailAddPage").val(totalboxvolume.toFixed(6));
                        } else {
                            $("#sales-totalboxweight-orderGoodsDetailAddPage").val(row.totalboxweight);
                            $("#sales-totalboxvolume-orderGoodsDetailAddPage").val(row.totalboxvolume);
                        }

                        $("#sales-unitname-orderGoodsDetailAddPage").text(row.unitname);
                        $("#sales-auxunitname-orderGoodsDetailAddPage").text(row.auxunitname);
                        $("#back-taxprice").val(formatterNum(row.goodsInfo.taxprice));
                        $("#sales-boxnum-orderGoodsDetailAddPage").val(formatterBigNumNoLen(row.goodsInfo.boxnum));
                        $("#sales-goodsId-orderGoodsDetailAddPage").goodsWidget("setValue", row.goodsid);
                        $("#sales-goodsId-orderGoodsDetailAddPage").goodsWidget("setText", row.goodsInfo.name);

                        $("#sales-oldtaxprice-orderGoodsDetailAddPage").val(formatterNum(row.oldtaxprice));
                        if (row.total == undefined) {
                            var storageid = $("#sales-storageid-orderGoodsAddPage").widget("getValue");
                            $.getJSON("storage/getStorageSummarySumByGoodsid.do", {
                                goodsid: row.goodsid,
                                storageid: storageid,
                                summarybatchid: row.summarybatchid
                            }, function (json) {
                                $("#sales-usablenum-orderGoodsDetailAddPage").val(json.storageSummary.usablenum);
                                $("#sales-total-orderGoodsDetailAddPage").val(json.storageSummary.usablenum);
                                $("#sales-loading-orderGoodsDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>" + row.goodsid + "</font>&nbsp;可用量：<font color='green'>" + json.storageSummary.usablenum + json.storageSummary.unitname + "</font>");
                            });
                        }
                        else {
                            $("#sales-loading-orderGoodsDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>" + row.goodsid + "</font>&nbsp;可用量：<font color='green'>" + row.total + row.unitname + "</font>");
                        }
                        var isbatch = $("#sales-isbatch-orderGoodsDetailAddPage").val();
                        $("#sales-storageid-orderGoodsDetailAddPage").widget("setValue", row.storageid);
                        if (isbatch == "1") {
                            var param = null;
                            if (storageid != null && storageid != "") {
                                param = [{field: 'goodsid', op: 'equal', value: row.goodsid},
                                    {field: 'storageid', op: 'equal', value: storageid}];
                            } else {
                                param = [{field: 'goodsid', op: 'equal', value: row.goodsid}];
                            }
                            $("#sales-storageid-orderGoodsDetailAddPage").widget("readonly", true);
                            $("#sales-batchno-orderGoodsDetailAddPage").widget({
                                referwid: 'RL_T_STORAGE_BATCH_LIST',
                                param: param,
                                width: 150,
                                singleSelect: true,
                                initValue: row.batchno,
                                onSelect: function (obj) {
                                    $("#sales-summarybatchid-orderGoodsDetailAddPage").val(obj.id);
                                    $("#sales-storageid-orderGoodsDetailAddPage").widget("setValue", obj.storageid);
                                    $("#sales-storagename-orderGoodsDetailAddPage").val(obj.storagename);
                                    $("#sales-storagelocationname-orderGoodsDetailAddPage").val(obj.storagelocationname);
                                    $("#sales-storagelocationid-orderGoodsDetailAddPage").val(obj.storagelocationid);
                                    $("#sales-produceddate-orderGoodsDetailAddPage").val(obj.produceddate);
                                    $("#sales-deadline-orderGoodsDetailAddPage").val(obj.deadline);
                                    $("#sales-loading-orderGoodsDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>" + obj.goodsid + "</font>&nbsp;可用量：<font color='green'>" + obj.usablenum + obj.unitname + "</font>");
                                    if (obj.usablenum != null && obj.usablenum != '') {
                                        $("input[name=usablenum]").val(obj.usablenum);
                                        $("input[name=total]").val(obj.usablenum);
                                    } else {
                                        $("input[name=usablenum]").val(0);
                                    }
                                },
                                onClear: function () {
                                    $("#sales-summarybatchid-orderGoodsDetailAddPage").val("");
                                    $("#sales-storageid-orderGoodsDetailAddPage").widget("clear");
                                    $("#sales-storagename-orderGoodsDetailAddPage").val("");
                                    $("#sales-storagelocationname-orderGoodsDetailAddPage").val("");
                                    $("#sales-storagelocationid-orderGoodsDetailAddPage").val("");
                                    $("#sales-produceddate-orderGoodsDetailAddPage").val("");
                                    $("#sales-deadline-orderGoodsDetailAddPage").val("");
                                }
                            });
                        } else {
                            $("#sales-batchno-orderGoodsDetailAddPage").widget({
                                referwid: 'RL_T_STORAGE_BATCH_LIST',
                                width: 150,
                                singleSelect: true,
                                disabled: true
                            });
                            $("#sales-storageid-orderGoodsDetailAddPage").widget("readonly", false);

                        }
                        $("input[name=unitnum]").focus();

                        formaterNumSubZeroAndDot();

                        //判断是否手动改过含税单价
                        var ret = order_AjaxConn({
                            id: row.goodsid,
                            unitnum: row.unitnum,
                            cid: customer,
                            date: $("input[name='saleorder.businessdate']").val(),
                            free: "1",
                            orderid: $("#sales-id-orderGoodsAddPage").val()
                        }, 'sales/getAuxUnitNumAndPrice.do');
                        var retjson = $.parseJSON(ret);
                        if (formatterDefineMoney(row.taxprice, 6) != formatterDefineMoney(retjson.taxprice, 6)) {
                            order_taxpricechange = "1";
                        } else {
                            order_taxpricechange = "0";
                        }

                        $("#sales-form-orderGoodsDetailEditPage").form('validate');
                    }
                });
            }
        }
        function editSaveDetail(go) { //修改数据确定后操作，
            var flag = $("#sales-form-orderGoodsDetailEditPage").form('validate');
            if (flag == false) {
                return false;
            }
            var row = $wareList.datagrid('getSelected');
            var rowIndex = $wareList.datagrid('getRowIndex', row);
            var form = $("#sales-form-orderGoodsDetailEditPage").serializeJSON();
            var goodsJson = $("#sales-goodsId-orderGoodsDetailAddPage").goodsWidget('getObject');
            if (goodsJson == null || goodsJson == "") goodsJson = $wareList.datagrid('getSelected').goodsInfo;
            form.goodsInfo = goodsJson;
            form.fixnum = form.unitnum;
            form.orderunitnum=0;
            form.notorderunitnum=form.unitnum;
            if (form.overnum != 0) {
                form.auxnumdetail = form.auxnum + form.auxunitname + form.overnum + form.unitname;
            } else {
                form.auxnumdetail = form.auxnum + form.auxunitname;
            }
            $wareList.datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
            $("#sales-dialog-orderGoodsAddPage-content").dialog('close', true)
//            groupGoods();
            countTotal();
        }
        function removeDetail() {
            var checkRows = $wareList.datagrid('getChecked');
            if (checkRows.length == 0) {
                $.messager.alert("提醒", "请选择要删除的记录");
                return false;
            }
            $.messager.confirm("提醒", "确定删除该商品明细?", function (r) {
                if (r) {
                    for (var i = 0; i < checkRows.length; ++i) {
                        var row = checkRows[i];
                        var groupid = row.groupid;
                        if (null != groupid && groupid != "") {
                            $.ajax({
                                url: 'sales/getSalesOrderPromotionInfo.do',
                                dataType: 'json',
                                type: 'post',
                                async: false,
                                data: {groupid: groupid, goodslist: ""},
                                success: function (json) {
                                    if (json.flag) {
                                        deleteRows(groupid);
                                    } else {
                                        var rowIndex = $wareList.datagrid('getRowIndex', row);
                                        $wareList.datagrid('deleteRow', rowIndex);
                                    }
                                }
                            });
                        } else {
                            var rowIndex = $wareList.datagrid('getRowIndex', row);
                            $wareList.datagrid('deleteRow', rowIndex);
                        }
                    }
                    var rows = $wareList.datagrid('getRows');
                    var data = new Array();
                    for (var i = 0; i < rows.length; i++) {
                        if (rows[i].goodsid != undefined) {
                            data.push(rows[i]);
                        }
                    }
                    $wareList.datagrid("loadData", data);
                    countTotal();
                }
            });
        }
        //删除分组
        function deleteRows(groupid) {
            var flag = false;
            var addrows = $("#sales-datagrid-orderGoodsAddPage").datagrid("getRows");
            for (var i = 0; i < addrows.length; i++) {
                if (groupid == addrows[i].groupid) {
                    $wareList.datagrid('deleteRow', i);
                    flag = true;
                    break;
                }
            }
            if (flag) {
                deleteRows(groupid);
            }
        }

    </script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        var printLimit = $("#sales-printlimit-orderGoodsAddPage").val() || 0;
        var $grid = $("#sales-datagrid-orderGoodsListPage");
        function onPrintSuccess(option) {
            var dataList = $grid.datagrid("getChecked");
            var isphprinttimes = false;
            $.each(option.code,function(i,item){
                if(item.codename == "storage_orderblank")
                    isphprinttimes = true;
            });
            for (var i = 0; i < dataList.length; i++) {
                if(isphprinttimes){
                    if (dataList[i].phprinttimes && !isNaN(dataList[i].phprinttimes)) {
                        dataList[i].phprinttimes = dataList[i].phprinttimes + 1;
                    } else {
                        dataList[i].phprinttimes = 1;
                    }
                }else {
                    if (dataList[i].printtimes && !isNaN(dataList[i].printtimes)) {
                        dataList[i].printtimes = dataList[i].printtimes + 1;
                    } else {
                        dataList[i].printtimes = 1;
                    }
                }
                var rowIndex = $grid.datagrid("getRowIndex", dataList[i].id);
                $grid.datagrid('updateRow', {index: rowIndex, row: dataList[i]});
            }
        }

        function getDataNoAudit(tableId, printParam) {
            var billno = $("#sales-id-orderGoodsAddPage").val();
            if (billno == "") {
                $.messager.alert("警告", "找不到要打印的信息!");
                return false;
            }
            var status = $("#sales-customer-status").val() || "";
            var printtimes = $("#sales-printtimes-orderGoodsAddPage").val() || 0;
            if (!(status == '3' || status == '4')) {
                $.messager.alert("提醒", "销售单"+billno + "不可打印");
                return false;
            }
            printParam.saleidarrs = billno;
            if (printtimes > 0)
                printParam.printIds = [billno];
            return true;
        }

        function getDataWithAudit(tableId, printParam) {
            var billno = $("#sales-id-orderGoodsAddPage").val();
            if (billno == "") {
                $.messager.alert("警告", "找不到要打印的信息!");
                return false;
            }
            var status = $("#sales-customer-status").val() || "";
            var printtimes = $("#sales-printtimes-orderGoodsAddPage").val() || 0;
            var fHPrintAfterSaleOutAudit = $("#sales-fHPrintAfterSaleOutAudit-orderGoodsAddPage").val() || "0";
            if (fHPrintAfterSaleOutAudit == "1") {
                if (status != '4') {
                    $.messager.alert("提醒", "抱歉，关闭状态下才能。<br/>" + billno + "此销售单不可打印");
                    return false;
                }
            } else {
                if (!(status == '3' || status == '4')) {
                    $.messager.alert("提醒", billno + "此销售单不可打印");
                    return false;
                }
            }
            printParam.saleidarrs = billno;
            if (printtimes > 0)
                printParam.printIds = [billno];
            return true;
        }

        //销售订单打印
        AgReportPrint.init({
            id: "order-dialog-print",
            code: "sales_ordergoods",
            //tableId: "sales-datagrid-orderListPage",
            url_preview: "print/sales/salesOrderGoodsPrintView.do",
            url_print: "print/sales/salesOrderGoodsPrint.do",
            btnPreview: "button-printview-salesOrderGoods",
            btnPrint: "button-print-salesOrderGoods",
            printlimit: printLimit,
            getData: getDataNoAudit,
            onPrintSuccess: onPrintSuccess
//            printAfterHandler: printAfterHandler
        });
    });

</script>
<%--打印结束 --%>

</body>
</html>
