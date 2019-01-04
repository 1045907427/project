<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售订单页面</title>
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
<input type="hidden" id="sales-backid-orderPage" value="${id }"/>
<div id="sales-orderPage-layout" class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="sales-buttons-orderPage"></div>
    </div>
    <div data-options="region:'center'">
        <div class="easyui-panel" data-options="fit:true" id="sales-panel-orderPage"></div>
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
    <div id="sales-dialog-version-orderPage"></div>
    <div id="sales-changePriceDialog-orderPage"></div>
    <div id="sales-addOrderByOrderGoodsDialog-orderPage"></div>
    <script type="text/javascript">
        //商品历史销售价显示条数
        var priceFloat = '${priceFloat}';
        var order_taxpricechange = "0";
        var order_url = "sales/orderAddPage.do";
        var order_type = '${type}';
        if (order_type == "view" || order_type == "show" || order_type == "handle") {
            order_url = "sales/orderViewPage.do?id=${id}";
        }
        if (order_type == "edit") {
            order_url = "sales/orderEditPage.do?id=${id}";
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

        var tableColJson = $("#sales-datagrid-orderAddPage").createGridColumnLoad({
            frozenCol: [[
                {field: 'id', title: '明细编号', hidden: true}
            ]],
            commonCol: [[
                {field: 'ck', checkbox: true},
                {field: 'goodsid', title: '商品编码', width: 70, align: ' left', sortable: true},
                {
                    field: 'goodsname', title: '商品名称', width: 250, align: 'left', aliascol: 'goodsid',
                    formatter: function (value, rowData, index) {
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
                                    return <c:if test="${CustomerNewBuyRemind eq '1'}"> (rowData.isnew == '1' ? '<font style="color: #f00;">[新]</font>' : '') + </c:if> '<a id="sales-historyprice-orderpage"  gindex ="' + index + '"  gid="' + rowData.goodsid + '" >' + rowData.goodsInfo.name + '</a>';
                                }
                            }
                        }else if (rowData.goodsname) {
                            return rowData.goodsname;
                        }else if (rowData.financeinfo) {
                            return rowData.financeinfo;
                        } else {
                            return '<a id="sales-historyprice-orderpage"  gindex ="' + index + '"  gid="' + rowData.goodsid + '" ></a>';
                        }
                    }
                },
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
                {field:'model', title:'规格型号',width:80,aliascol:'goodsid',hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        if(rowData.goodsInfo != null){
                            return rowData.goodsInfo.model;
                        }else{
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
                    field: 'unitnum', title: '数量', width: 60, align: 'right', sortable: true,
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
                    field: 'referenceprice', title: '参考价', width: 60, align: 'right', isShow: true, sortable: true,
                    formatter: function (value, row, index) {
                        return '<a id="sales-historyprice-orderpage" gindex ="' + index + '" gid="' + row.goodsid + '" >' + formatterMoney(value) + '</a>';

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
                {
                    field: 'totalboxweight', title: '重量（千克）', width: 80, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'totalboxvolume', title: '体积（立方米）', width: 80, align: 'right',
                    formatter: function (value, row, index) {
                        if (value != undefined) {
                            return Number(value).toFixed(3);
                        }
                    }
                },
                {
                    field: 'storageid', title: '所属仓库', width: 80, align: 'left',
                    formatter: function (value, row, index) {
                        return row.storagename;
                    }
                },
                {
                    field: 'storagelocationid', title: '所属库位', width: 80, align: 'left', hidden: true,
                    formatter: function (value, row, index) {
                        return row.storagelocationname;
                    }
                },
                {field: 'batchno', title: '批次号', width: 80, align: 'left'},
                {field: 'produceddate', title: '生产日期', width: 80, align: 'left'},
                {field: 'deadline', title: '截止日期', width: 80, align: 'left', hidden: true},
                {field: 'remark', title: '备注', width: 200, align: 'left'}
            ]]
        });
        $(function () {
            $("#sales-panel-orderPage").panel({
                href: order_url,
                cache: false,
                maximized: true,
                border: false,
                onLoad: function () {
                    $("#sales-customer-orderAddPage").focus();
                }
            });

            $("#sales-historyprice-orderpage").live("mouseover", function (e) {
                var goodsid = $(this).attr("gid");
                var index = $(this).attr("gindex");
                if (priceFloat > 0) {
                    var row = $wareList.datagrid('getSelected');
                    selectindex = $wareList.datagrid('getRowIndex', row);
                    if(selectindex != index ){
                        $wareList.datagrid('unselectAll');
                        $wareList.datagrid('selectRow',index);
                        var customerid = $("#sales-customer-hidden-orderAddPage").val();
                        showPirceMenu(index,customerid, goodsid);
                    }
                }


            });
            //查看页面显示商品悬浮历史价格页面
            function showPirceMenu(index,customerid, goodsid) {
                $("#sales-goodsPriceMenu-table").empty();
                var html = '<tr><td width=\"90\" align=center>业务日期</td><td width=\"50\" align=center>数量</td><td width=\"50\" align=center>单位</td><td width=\"50\" align=center>单价</td>' +
                    '<td width=\50\" align=center>金额</td><td width=\"80\" align=center>辅数量</td><td align=center width=\"120\">备注</td></tr>';
                $.ajax({
                    url: 'sales/getAuditPrice.do',
                    dataType: 'json',
                    type: 'post',
                    data: {customerid: customerid, goodsid: goodsid},
                    success: function (json) {
                        loaded();
                        for (var i = 0; i < json.length; ++i) {
                            html += "<tr><td align=center width=\"70\">" + json[i].businessdate + "</td><td align=center width=\"30\">" + json[i].unitnum + "</td><td align=center width=\"30\">" + json[i].unitname + "</td><td align=center width=\"30\">" +
                                json[i].taxprice + "</td><td align=center width=\"30\">" + json[i].taxamount + "</td><td align=center width=\"30\">" + json[i].auxnumdetail + "</td><td align=center width=\"120\">" + json[i].remark + "</td></tr>";
                        }
                        $("#sales-goodsPriceMenu-table").html(html);
                        var pos = 25*index;
                        $("#sales-goodsPriceMenu-orderPage").menu('show', {
                            left:720,
                            top: 215+pos
                        });
                    }
                });

            }



            //按钮
            $("#sales-buttons-orderPage").buttonWidget({
                initButton: [
                    {},
                    <security:authorize url="/sales/orderAddPage.do">
                    {
                        type: 'button-add',
                        handler: function () {
                            refreshPanel('sales/orderAddPage.do');
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sales/orderHold.do">
                    {
                        type: 'button-hold',
                        handler: function () {
                            $.messager.confirm("提醒", "确定暂存该订单信息？", function (r) {
                                if (r) {
                                    $("#sales-addType-orderAddPage").val("temp");
                                    var json = $("#sales-datagrid-orderAddPage").datagrid('getRows');
                                    $("#sales-goodsJson-orderAddPage").val(JSON.stringify(json));
                                    $("#sales-form-orderAddPage").submit();
                                }
                            });
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sales/orderSave.do">
                    {
                        type: 'button-save',
                        handler: function () {
                            $("#sales-addType-orderAddPage").val("real");
                            var rows = $("#sales-datagrid-orderAddPage").datagrid('getRows');
                            $("#sales-goodsJson-orderAddPage").val(JSON.stringify(rows));
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
                                        $("#sales-saveaudit-orderDetailAddPage").val("save");
                                        $("#sales-form-orderAddPage").submit();
                                    }
                                });
                            } else {
                                $("#sales-saveaudit-orderDetailAddPage").val("save");
                                $("#sales-form-orderAddPage").submit();
                            }
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sales/orderSaveAudit.do">
                    {
                        type: 'button-saveaudit',
                        handler: function () {
                            var rows = $("#sales-datagrid-orderAddPage").datagrid('getRows');
                            $("#sales-goodsJson-orderAddPage").val(JSON.stringify(rows));
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
                                        $("#sales-addType-orderAddPage").val("real");
                                        $("#sales-saveaudit-orderDetailAddPage").val("saveaudit");
                                        $("#sales-form-orderAddPage").submit();
                                    }
                                });
                            } else {
                                $.messager.confirm("提醒", "确定保存并审核订单信息？", function (r) {
                                    if (r) {
                                        $("#sales-addType-orderAddPage").val("real");
                                        $("#sales-saveaudit-orderDetailAddPage").val("saveaudit");
                                        $("#sales-form-orderAddPage").submit();
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
                            var type = $("#sales-buttons-orderPage").buttonWidget("getOperType");
                            if (type == "add") {
                                var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
                                top.closeTab(currTitle);
                            }
                            else if (type == "edit") {
                                var id = $("#sales-backid-orderPage").val();
                                if (id == "") {
                                    return false;
                                }
                                $("#sales-panel-orderPage").panel('refresh', 'sales/orderViewPage.do?id=' + id);
                            }
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sales/orderDelete.do">
                    {
                        type: 'button-delete',
                        handler: function () {
                            var id = $("#sales-backid-orderPage").val();
                            if (id == '') {
                                return false;
                            }
                            $.messager.confirm("提醒", "确定删除该订单信息？", function (r) {
                                if (r) {
                                    loading("删除中..");
                                    $.ajax({
                                        url: 'sales/deleteOrder.do',
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
                                                var data = $("#sales-buttons-orderPage").buttonWidget("removeData", '');
                                                if (data != null) {
                                                    $("#sales-backid-orderPage").val(data.id);
                                                    refreshPanel('sales/orderEditPage.do?id=' + data.id);
                                                }
                                                else {
                                                    parent.closeNowTab();
                                                }
                                            }
                                            else {
                                                $.messager.alert("提醒", json.msg || "删除失败");
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
                    <security:authorize url="/sales/orderCopy.do">
                    {
                        type: 'button-copy',
                        handler: function () {
                            var id = $("#sales-backid-orderPage").val();
                            if (id == '') {
                                return false;
                            }
                            top.addOrUpdateTab('sales/orderPage.do?type=copy&id=' + id, "销售订单新增");
                            //$("#sales-panel-orderPage").panel('refresh', 'sales/orderCopyPage.do?id='+ id);
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sales/orderAudit.do">
                    {
                        type: 'button-audit',
                        handler: function () {
                            var id = $("#sales-backid-orderPage").val();
                            if (id == '') {
                                return false;
                            }
                            var salesuser = $("#sales-salesMan-orderAddPage").widget('getValue');
                            if (salesuser == "") {
                                $.messager.alert("提醒", "客户业务员为空，不允许超级审核!");
                                return false;
                            }
                            loading("核对验证中...");
                            $.getJSON("sales/canAuditOrder.do", {ids: id}, function (json) {
                                loaded();
                                if (json.flag == true) {
                                    $.messager.confirm("提醒", "确定审核该订单信息？", function (r) {
                                        if (r) {
                                            loading("审核中...");
                                            $.ajax({
                                                url: 'sales/auditOrder.do',
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
                                                        $("#sales-buttons-orderPage").buttonWidget("setDataID", {
                                                            id: id,
                                                            state: '3',
                                                            type: 'view'
                                                        });
                                                        $("#sales-buttons-orderPage").buttonWidget("enableMenuItem", "button-printview-orderblank");
                                                        $("#sales-buttons-orderPage").buttonWidget("enableMenuItem", "button-print-orderblank");
                                                        $("#sales-buttons-orderPage").buttonWidget("enableMenuItem", "button-printview-DispatchBill");
                                                        $("#sales-buttons-orderPage").buttonWidget("enableMenuItem", "button-print-DispatchBill");
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
                                                url: 'sales/auditOrder.do',
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
                                                        $("#sales-buttons-orderPage").buttonWidget("setDataID", {
                                                            id: id,
                                                            state: '3',
                                                            type: 'view'
                                                        });

                                                        $("#sales-buttons-orderPage").buttonWidget("enableMenuItem", "button-printview-orderblank");
                                                        $("#sales-buttons-orderPage").buttonWidget("enableMenuItem", "button-print-orderblank");
                                                        $("#sales-buttons-orderPage").buttonWidget("enableMenuItem", "button-printview-DispatchBill");
                                                        $("#sales-buttons-orderPage").buttonWidget("enableMenuItem", "button-print-DispatchBill");
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
                    <security:authorize url="/sales/orderSupperAudit.do">
                    {
                        type: 'button-supperaudit',
                        handler: function () {
                            var id = $("#sales-backid-orderPage").val();
                            if (id == '') {
                                return false;
                            }
                            loading("核对验证中..");
                            $.getJSON("sales/canAuditOrder.do", {ids: id}, function (json) {
                                loaded();
                                if (json.flag == true) {
                                    $.messager.confirm("提醒", "确定审核该订单信息？", function (r) {
                                        if (r) {
                                            loading("审核中..");
                                            $.ajax({
                                                url: 'sales/auditOrder.do',
                                                dataType: 'json',
                                                type: 'post',
                                                data: 'id=' + id + '&type=1&model=supper',
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
                                                        $("#sales-buttons-orderPage").buttonWidget("setDataID", {
                                                            id: id,
                                                            state: '4',
                                                            type: 'view'
                                                        });

                                                        $("#sales-buttons-orderPage").buttonWidget("enableMenuItem", "button-printview-orderblank");
                                                        $("#sales-buttons-orderPage").buttonWidget("enableMenuItem", "button-print-orderblank");
                                                        $("#sales-buttons-orderPage").buttonWidget("enableMenuItem", "button-printview-DispatchBill");
                                                        $("#sales-buttons-orderPage").buttonWidget("enableMenuItem", "button-print-DispatchBill");
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
                                            $("#sales-panel-orderPage").panel('refresh', 'sales/orderViewPage.do?id=' + id);
                                        }
                                    });

                                }
                                else {
                                    loaded();
                                    $.messager.confirm("提醒", json.msg + "是否继续审核？", function (r) {
                                        if (r) {
                                            loading("审核中...");
                                            $.ajax({
                                                url: 'sales/auditOrder.do',
                                                dataType: 'json',
                                                type: 'post',
                                                data: 'id=' + id + '&type=1&model=supper',
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
                                                        $("#sales-panel-orderPage").panel('refresh', 'sales/orderViewPage.do?id=' + id);
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
                    <security:authorize url="/sales/orderOppaudit.do">
                    {
                        type: 'button-oppaudit',
                        handler: function () {
                            var id = $("#sales-backid-orderPage").val();
                            if (id == '') {
                                return false;
                            }
                            var businessdate = $("#sales-businessdate-orderAddPage").val();
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
                                var businessdate = $("#sales-businessdate-orderAddPage").val();
                                if (businessdate != '${today}') {
                                    $.messager.alert("提醒", "销售订单不能反审，业务日期不是今天。需要有权限的人才能反审！");
                                    return false;
                                }
                            }
                            $.messager.confirm("提醒", "确定反审该订单信息？", function (r) {
                                if (r) {
                                    loading("反审中..");
                                    $.ajax({
                                        url: 'sales/auditOrder.do',
                                        dataType: 'json',
                                        type: 'post',
                                        data: 'id=' + id + '&type=2',
                                        success: function (json) {
                                            loaded();
                                            if (!json.bigflag) {
                                                $.messager.alert("提醒", "状态条件不允许反审或生成关联的发货单已生成大单发货，不允许反审");
                                            } else {
                                                if (json.billArg == false) {
                                                    $.messager.alert("提醒", "反审失败，下游单据已生成并审核，无法反审");
                                                }
                                                else {
                                                    if (json.flag == true) {
                                                        $.messager.alert("提醒", "反审成功");
                                                        //$("#sales-customer-status").val("2");
                                                        //$("#sales-buttons-orderPage").buttonWidget("setDataID", {id:id, state:'2', type:'edit'});
                                                        $("#sales-panel-orderPage").panel('refresh', 'sales/orderEditPage.do?id=' + id);
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
                    <security:authorize url="/sales/orderBack.do">
                    {
                        type: 'button-back',
                        handler: function (data) {
                            $("#sales-backid-orderPage").val(data.id);
                            refreshPanel('sales/orderEditPage.do?id=' + data.id);
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sales/orderNext.do">
                    {
                        type: 'button-next',
                        handler: function (data) {
                            $("#sales-backid-orderPage").val(data.id);
                            refreshPanel('sales/orderEditPage.do?id=' + data.id);
                        }
                    },
                    </security:authorize>
                    {}
                ],
                buttons: [
                    <security:authorize url="/sales/oweOrderOnOrder.do">
                    {
                        id: 'order-oweorder-button',
                        name: '生成销售欠单',
                        iconCls: 'button-audit',
                        handler: function () {
                            $.messager.confirm("提醒", "是否生成销售欠单？", function (r) {
                                if (r) {
                                    var id = $("#sales-id-orderAddPage").val();
                                    if (id != "") {
                                        $.ajax({
                                            url: 'sales/addOweOrderByOrder.do?id=' + id,
                                            type: 'post',
                                            dataType: 'json',
                                            success: function (json) {
                                                if (json.flag) {
                                                    $.messager.alert("提醒", "生成销售欠单成功!<br>销售欠单编号:" + json.msg);
                                                    var url = 'sales/orderPage.do?type=edit&id=' + id;
                                                    refreshPanel(url);
                                                }
                                                else {
                                                    $.messager.alert("提醒", "生成销售欠单失败!<br>" + json.msg);
                                                }
                                            },
                                            error: function () {
                                                loaded();
                                                $.messager.alert("错误", "生成销售欠单出错!");
                                            }

                                        });
                                    }
                                }
                            });
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sales/orderDeployInfo.do">
                    {
                        id: 'button-deploy',
                        name: '配置库存',
                        iconCls: 'button-deploy',
                        handler: function () {
                            $.messager.confirm("提醒", "是否对当前销售订单配置库存？", function (r) {
                                if (r) {
                                    var id = $("#sales-backid-orderPage").val();
                                    if (id != "") {
                                        loading("配置中..");
                                        $.ajax({
                                            url: 'sales/orderDeployInfo.do?id=' + id,
                                            type: 'post',
                                            dataType: 'json',
                                            success: function (json) {
                                                loaded();
                                                if (json.flag) {
                                                    $.messager.alert("提醒", "商品数量充足!");
                                                    $("#button-deploy").linkbutton('disable');
                                                } else {
                                                    if (json.barcodeFlag) {
                                                        $("#sales-deployDialog-order-content").html(json.msg);
                                                        $("#sales-deployDialog-order").dialog({
                                                            title: '配置库存提醒信息',
                                                            width: 600,
                                                            height: 300,
                                                            closed: true,
                                                            cache: false,
                                                            modal: true,
                                                            buttons: [{
                                                                text: '追加',
                                                                handler: function () {
                                                                    var gArr = [];
                                                                    $('.deployStorage:checked').each(function (i) {
                                                                        var goodsid = $(this).attr("name");
                                                                        var rgoodsid = $(this).val();
                                                                        var storageid = $(this).attr("storageid");
                                                                        var gjson = {
                                                                            goodsid: goodsid,
                                                                            rgoodsid: rgoodsid,
                                                                            storageid: storageid
                                                                        };
                                                                        gArr.push(gjson);
                                                                    });
                                                                    var deployStr = "";
                                                                    if (gArr.length > 0) {
                                                                        deployStr = JSON.stringify(gArr)
                                                                    }
                                                                    $("#sales-deployDialog-order").dialog("close");
                                                                    $("#button-deploy").linkbutton('disable');
                                                                    var url = 'sales/orderDeployInfoPage.do?id=' + id + '&barcodeflag=1';
                                                                    refreshPanelAddParam(url, {deploy: deployStr});
                                                                    $.messager.alert("提醒", "配置库存成功!请确认保存后，销售订单才生效！");
                                                                }
                                                            }, {
                                                                text: '替换',
                                                                handler: function () {
                                                                    var gArr = [];
                                                                    $('.deployStorage:checked').each(function (i) {
                                                                        var goodsid = $(this).attr("name");
                                                                        var rgoodsid = $(this).val();
                                                                        var storageid = $(this).attr("storageid");
                                                                        var gjson = {
                                                                            goodsid: goodsid,
                                                                            rgoodsid: rgoodsid,
                                                                            storageid: storageid
                                                                        };
                                                                        gArr.push(gjson);
                                                                    });
                                                                    var deployStr = "";
                                                                    if (gArr.length > 0) {
                                                                        deployStr = JSON.stringify(gArr)
                                                                    }
                                                                    $("#sales-deployDialog-order").dialog("close");
                                                                    $("#button-deploy").linkbutton('disable');
                                                                    var url = 'sales/orderDeployInfoPage.do?id=' + id + '&barcodeflag=2';
                                                                    refreshPanelAddParam(url, {deploy: deployStr});
                                                                    $.messager.alert("提醒", "配置库存成功!请确认保存后，销售订单才生效！");
                                                                }
                                                            }, {
                                                                text: '直接配置',
                                                                handler: function () {
                                                                    $("#sales-deployDialog-order").dialog("close");
                                                                    $("#storage-deploy-button").linkbutton('disable');
                                                                    var url = 'sales/orderDeployInfoPage.do?id=' + id;
                                                                    refreshPanel(url);
                                                                    $.messager.alert("提醒", "配置库存成功!请确认保存后，销售订单才生效！");
                                                                }
                                                            }]
                                                        });
                                                        $("#sales-deployDialog-order").dialog("open");
                                                    } else {
                                                        $("#button-deploy").linkbutton('disable');
                                                        var url = 'sales/orderDeployInfoPage.do?id=' + id;
                                                        refreshPanel(url);
                                                        if (json.batchFlag) {
                                                            $.messager.alert("提醒", json.msg + "配置库存成功!<br/>请保存确认后，销售订单才生效！");
                                                        } else {
                                                            $.messager.alert("提醒", "配置库存成功!<br/>请保存确认后，销售订单才生效！");
                                                        }
                                                    }
                                                }
                                            },
                                            error: function () {
                                                loaded();
                                                $.messager.alert("错误", "配置库存出错!");
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sales/invalidOrderClose.do">
                    {
                        id: 'button-invalid',
                        name: '作废',
                        iconCls: 'button-delete',
                        handler: function () {
                            var id = $("#sales-backid-orderPage").val();
                            if (id == '') {
                                return false;
                            }
                            $.messager.confirm("提醒", "确定作废该订单信息？", function (r) {
                                if (r) {
                                    loading("作废中..");
                                    $.ajax({
                                        url: 'sales/doInvalidOrder.do',
                                        dataType: 'json',
                                        type: 'post',
                                        data: 'id=' + id + '&type=1',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag == true) {
                                                $.messager.alert("提醒", "作废成功。");
                                                //$("#sales-customer-status").val("5");
                                                //$("#sales-buttons-orderPage").buttonWidget("setDataID", {id:id, state:'5', type:'edit'});
                                                $("#sales-panel-orderPage").panel('refresh', 'sales/orderViewPage.do?id=' + id);
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
                    <security:authorize url="/sales/invalidOrderOpen.do">
                    {
                        id: 'button-uninvalid',
                        name: '取消作废',
                        iconCls: 'button-oppaudit',
                        handler: function () {
                            var id = $("#sales-backid-orderPage").val();
                            if (id == '') {
                                return false;
                            }
                            $.messager.confirm("提醒", "确定作废该订单信息？", function (r) {
                                if (r) {
                                    loading("取消作废中..");
                                    $.ajax({
                                        url: 'sales/doInvalidOrder.do',
                                        dataType: 'json',
                                        type: 'post',
                                        data: 'id=' + id + '&type=2',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag == true) {
                                                $.messager.alert("提醒", "取消作废成功。");
                                                //$("#sales-customer-status").val("2");
                                                //$("#sales-buttons-orderPage").buttonWidget("setDataID", {id:id, state:'2', type:'edit'});
                                                $("#sales-panel-orderPage").panel('refresh', 'sales/orderEditPage.do?id=' + id);
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
                    <security:authorize url="/sales/showOrderVersionListPage.do">
                    {
                        id: 'button-viewversion',
                        name: '查看修改记录',
                        iconCls: 'button-view',
                        handler: function () {
                            var id = $("#sales-backid-orderPage").val();
                            if (id == '') {
                                return false;
                            }
                            $("#sales-dialog-version-orderPage").dialog({
                                title: '销售订单修改记录',
                                width: 600,
                                height: 300,
                                closed: false,
                                modal: true,
                                cache: false,
                                maximizable: true,
                                resizable: true,
                                href: 'sales/showOrderVersionListPage.do?id=' + id
                            });

                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sales/orderPrintBtn.do">
                    {
                        id: 'menuButton',
                        type: 'menu',
                        name: '打印',
                        iconCls: 'button-print',
                        button: [
                            <security:authorize url="/storage/salesOrderblankForXSDPrintViewBtn.do">
                            {
                                id: 'button-printview-orderblank',
                                name: '三歆预览',
                                iconCls: 'button-preview',
                                handler: function () {
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/storage/salesOrderblankForXSDPrintBtn.do">
                            {
                                id: 'button-print-orderblank',
                                name: '三歆打印',
                                iconCls: 'button-print',
                                handler: function () {
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/storage/salesDispatchBillForXSDPrintViewBtn.do">
                            {
                                id: 'button-printview-DispatchBill',
                                name: '鹰之鹰预览',
                                iconCls: 'button-preview',
                                handler: function () {
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/storage/salesDispatchBillForXSDPrintBtn.do">
                            {
                                id: 'button-print-DispatchBill',
                                name: '鹰之鹰打印',
                                iconCls: 'button-print',
                                handler: function () {
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/sales/salesOrderPrintViewBtn.do">
                            {
                                id: 'button-printview-salesOrder',
                                name: '销售订单预览',
                                iconCls: 'button-preview',
                                handler: function () {
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/sales/salesOrderPrintBtn.do">
                            {
                                id: 'button-print-salesOrder',
                                name: '销售订单',
                                iconCls: 'button-print',
                                handler: function () {
                                }
                            },
                            </security:authorize>
                            {}
                        ]
                    },
                    </security:authorize>
                    {}
                ],
                layoutid: 'sales-orderPage-layout',
                model: 'bill',
                type: 'view',
                tab: '销售订单列表',
                taburl: '/sales/orderListPage.do',
                id: '${id}',
                datagrid: 'sales-datagrid-orderListPage'
            });
            $(document).keydown(function (event) {//alert(event.keyCode);
                switch (event.keyCode) {
                    case 13: //Enter
                        if (chooseNo == "saleorder.sourceid") {
                            $("input[name='saleorder.remark']").focus();
                            return false;
                        }
                        if (chooseNo == "saleorder.remark") {
                            $("input[name='saleorder.remark']").blur();
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
                        $("#sales-remark-orderDetailAddPage").focus();
                        $("#sales-dialog-orderAddPage-content").dialog('close');
                        break;
                    //case 37: //←
                    //	$("#button-back").click();
                    //break;
                    //case 39: //→
                    //	$("#button-next").click();
                    //break;
                    case 38://↑
                        var detaildialog = $("#sales-dialog-orderAddPage-content").length;
                        if(detaildialog == 0 ){
                            var row = $wareList.datagrid('getSelected');//console.log(row);
                            var selectindex = $wareList.datagrid('getRowIndex', row);
                            if(selectindex == -1){
                                selectindex = 0;
                            }else{
                                $wareList.datagrid('unselectRow',selectindex);
                                -- selectindex;
                            }
                            $wareList.datagrid('selectRow',selectindex);
                            var selectRow =  $wareList.datagrid('getRows')[selectindex];
                            var customerid = $("#sales-customer-hidden-orderAddPage").val();
                            if (priceFloat > 0 && selectindex > -1) {
                                showPirceMenu(selectindex,customerid, selectRow.goodsid);
                            }else{
                                $("#sales-goodsPriceMenu-orderPage").menu('hide');
                            }

                        }

                        break;
                    case 40://↓
                        var detaildialog = $("#sales-dialog-orderAddPage-content").length;
                        if(detaildialog == 0 ){
                            console.info(detaildialog)
                            var row = $wareList.datagrid('getSelected');//console.log(row);
                            var selectindex = $wareList.datagrid('getRowIndex', row);
                            if(selectindex == -1){
                                selectindex = 0;
                            }else{
                                $wareList.datagrid('unselectRow',selectindex);
                                ++ selectindex;
                            }
                            $wareList.datagrid('selectRow',selectindex);
                            var selectRow =  $wareList.datagrid('getRows')[selectindex];
                            var customerid = $("#sales-customer-hidden-orderAddPage").val();
                            if (priceFloat > 0 && selectRow.goodsid != undefined) {
                                showPirceMenu(selectindex,customerid, selectRow.goodsid);
                            }else{
                                $("#sales-goodsPriceMenu-orderPage").menu('hide');
                            }
                        }
                        break;
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
                $("#sales-savegoon-orderDetailAddPage").focus();
                $("#sales-savegoon-orderDetailAddPage").trigger("click");
            });
            $(document).bind('keydown', '+', function () {
                var saveDetailFlag = $("#sales-saveDetailFlag-orderDetailAddPage").val();
                if (saveDetailFlag == "1") {
                    return;
                }
                $("#sales-savegoon-orderDetailAddPage").focus();
                setTimeout(function () {
                    $("#sales-savegoon-orderDetailAddPage").trigger("click");
                }, 300);
                return false;
            });

        });
        function refreshPanel(url) { //更新panel
            //客户信息归零
            leftAmount = 0;
            receivableAmount = 0;
            $("#sales-panel-orderPage").panel('refresh', url);
        }
        function refreshPanelAddParam(url, param) { //更新panel
            //客户信息归零
            leftAmount = 0;
            receivableAmount = 0;
            $("#sales-panel-orderPage").panel({queryParams: param, href: url});
        }

        function countTotal(leftAmount, receivableAmount) { //计算合计
            var checkrows = $("#sales-datagrid-orderAddPage").datagrid('getChecked');
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
            leftAmount = formatterMoney(leftAmount);
            var foot = [{
                financeinfo: '余额:' + leftAmount,
                goodsid: '选中合计',
                usablenum: usablenum,
                unitnum: unitnum,
                taxamount: taxamount,
                notaxamount: notaxamount,
                tax: tax,
                auxnumdetail: totalbox + "箱",
                totalboxvolume: totalboxvolume,
                totalboxweight: totalboxweight
            }];
            //合计
            var rows = $("#sales-datagrid-orderAddPage").datagrid('getRows');
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
            receivableAmount = formatterMoney(receivableAmount);
            var footSum = {
                goodsid: '合计',
                financeinfo: "应收款:" + receivableAmount,
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
            $("#sales-datagrid-orderAddPage").datagrid('reloadFooter', foot);
        }
        function customerCheck() { //添加商品明细前确定客户已选
            var customer = $("#sales-customer-orderAddPage-hidden").val();
            if (customer == '') {
                $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
                $("#sales-customer-orderAddPage").focus();
                return false;
            }
            else {
                return customer;
            }
        }
        var insertIndex = undefined;
        function beginAddDetail(flag) { //开始添加商品信息
            if (flag) {
                var row = $wareList.datagrid('getSelected');
                insertIndex = $wareList.datagrid('getRowIndex', row);
                insertIndex = insertIndex - 1;
            }
            var customer = $("#sales-customer-orderAddPage-hidden").val();
            if (customer == '') {
                $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
                $("#sales-customer-orderAddPage").focus();
                return false;
            }
            $('<div id="sales-dialog-orderAddPage-content"></div>').appendTo('#sales-dialog-orderAddPage');
            $("#sales-dialog-orderAddPage-content").dialog({ //弹出新添加窗口
                title: '商品信息添加(按ESC退出)',
                maximizable: true,
                width: 600,
                height: 450,
                closed: false,
                modal: true,
                cache: false,
                resizable: true,
                href: 'sales/orderDetailAddPage.do?cid=' + customer,
                onClose: function () {
                    $('#sales-dialog-orderAddPage-content').dialog("destroy");
                },
                onLoad: function () {
                    $("#sales-goodsId-orderDetailAddPage").focus();
                }
            });
        }
        function addSaveDetail(go) { //添加新数据确定后操作，
            var flag = $("#sales-form-orderDetailAddPage").form('validate');
            if (flag == false) {
                return false;
            }
            var form = $("#sales-form-orderDetailAddPage").serializeJSON();
            var goodsJson = $("#sales-goodsId-orderDetailAddPage").goodsWidget('getObject');
            form.goodsInfo = goodsJson;
            var customer = $("#sales-customer-orderAddPage-hidden").val();
            form.fixnum = form.unitnum;
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
                $("#sales-form-orderDetailAddPage").form("clear");
                $("input[name=deliverydate]").val(deliverydate);
                $("#sales-deliverytype-orderDetailAddPage").val("0");
            }
            else { //否则直接关闭
                $("#sales-dialog-orderAddPage-content").dialog('close', true)
            }

            countTotal(leftAmount, receivableAmount); //第添加一条商品明细计算一次合计
        }
        function beginEditDetail(rowData) { //开始修改商品信息
            var customer = $("#sales-customer-orderAddPage-hidden").val();
            if (customer == '') {
                $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
                $("#sales-customer-orderAddPage").focus();
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
                url = 'sales/orderDetailEditPage.do?cid=' + customer + '&goodsid=' + row.goodsid; //如果是修改页面，数据直接来源于datagrid中的json数据。
                $('<div id="sales-dialog-orderAddPage-content"></div>').appendTo('#sales-dialog-orderAddPage');
                $("#sales-dialog-orderAddPage-content").dialog({ //弹出修改窗口
                    title: '商品信息修改(按ESC退出)',
                    maximizable: true,
                    width: 600,
                    height: 450,
                    closed: false,
                    modal: true,
                    cache: false,
                    resizable: true,
                    href: url,
                    onClose: function () {
                        $('#sales-dialog-orderAddPage-content').dialog("destroy");
                    },
                    onLoad: function () {
                        $("#sales-form-orderDetailEditPage").form('load', row);
                        //重量和体积
                        if (null != row.goodsInfo.grossweight && null != row.goodsInfo.singlevolume) {
                            var totalboxweight = row.unitnum * row.goodsInfo.grossweight;
                            var totalboxvolume = row.unitnum * row.goodsInfo.singlevolume;
                            $("#sales-totalboxweight-orderDetailAddPage").val(totalboxweight.toFixed(6));
                            $("#sales-totalboxvolume-orderDetailAddPage").val(totalboxvolume.toFixed(6));
                        } else {
                            $("#sales-totalboxweight-orderDetailAddPage").val(row.totalboxweight);
                            $("#sales-totalboxvolume-orderDetailAddPage").val(row.totalboxvolume);
                        }

                        $("#sales-unitname-orderDetailAddPage").text(row.unitname);
                        $("#sales-auxunitname-orderDetailAddPage").text(row.auxunitname);
                        $("#back-taxprice").val(formatterNum(row.goodsInfo.taxprice));
                        $("#sales-boxnum-orderDetailAddPage").val(formatterBigNumNoLen(row.goodsInfo.boxnum));
                        $("#sales-goodsId-orderDetailAddPage").goodsWidget("setValue", row.goodsid);
                        $("#sales-goodsId-orderDetailAddPage").goodsWidget("setText", row.goodsInfo.name);
                        if (row.total == undefined) {
                            var storageid = $("#sales-storageid-orderAddPage").widget("getValue");
                            $.getJSON("storage/getStorageSummarySumByGoodsid.do", {
                                goodsid: row.goodsid,
                                storageid: storageid,
                                summarybatchid: row.summarybatchid
                            }, function (json) {
                                $("#sales-usablenum-orderDetailAddPage").val(json.storageSummary.usablenum);
                                $("#sales-total-orderDetailAddPage").val(json.storageSummary.usablenum);
                                $("#sales-loading-orderDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>" + row.goodsid + "</font>&nbsp;可用量：<font color='green'>" + json.storageSummary.usablenum + json.storageSummary.unitname + "</font>&nbsp;参考价：<font color='green'>"+row.referenceprice+"</font>");
                            });
                        }
                        else {
                            $("#sales-loading-orderDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>" + row.goodsid + "</font>&nbsp;可用量：<font color='green'>" + row.total + row.unitname + "</font>&nbsp;参考价：<font color='green'>"+row.referenceprice+"</font>");
                        }
                        var isbatch = $("#sales-isbatch-orderDetailAddPage").val();
                        $("#sales-storageid-orderDetailAddPage").widget("setValue", row.storageid);
                        if (isbatch == "1") {
                            var param = null;
                            if (storageid != null && storageid != "") {
                                param = [{field: 'goodsid', op: 'equal', value: row.goodsid},
                                    {field: 'storageid', op: 'equal', value: storageid}];
                            } else {
                                param = [{field: 'goodsid', op: 'equal', value: row.goodsid}];
                            }
                            $("#sales-storageid-orderDetailAddPage").widget("readonly", true);
                            $("#sales-batchno-orderDetailAddPage").widget({
                                referwid: 'RL_T_STORAGE_BATCH_LIST',
                                param: param,
                                width: 150,
                                singleSelect: true,
                                initValue: row.batchno,
                                onSelect: function (obj) {
                                    $("#sales-summarybatchid-orderDetailAddPage").val(obj.id);
                                    $("#sales-storageid-orderDetailAddPage").widget("setValue", obj.storageid);
                                    $("#sales-storagename-orderDetailAddPage").val(obj.storagename);
                                    $("#sales-storagelocationname-orderDetailAddPage").val(obj.storagelocationname);
                                    $("#sales-storagelocationid-orderDetailAddPage").val(obj.storagelocationid);
                                    $("#sales-produceddate-orderDetailAddPage").val(obj.produceddate);
                                    $("#sales-deadline-orderDetailAddPage").val(obj.deadline);
                                    $("#sales-loading-orderDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>" + obj.goodsid + "</font>&nbsp;可用量：<font color='green'>" + obj.usablenum + obj.unitname + "</font>");
                                    if (obj.usablenum != null && obj.usablenum != '') {
                                        $("input[name=usablenum]").val(obj.usablenum);
                                        $("input[name=total]").val(obj.usablenum);
                                    } else {
                                        $("input[name=usablenum]").val(0);
                                    }
                                },
                                onClear: function () {
                                    $("#sales-summarybatchid-orderDetailAddPage").val("");
                                    $("#sales-storageid-orderDetailAddPage").widget("clear");
                                    $("#sales-storagename-orderDetailAddPage").val("");
                                    $("#sales-storagelocationname-orderDetailAddPage").val("");
                                    $("#sales-storagelocationid-orderDetailAddPage").val("");
                                    $("#sales-produceddate-orderDetailAddPage").val("");
                                    $("#sales-deadline-orderDetailAddPage").val("");
                                }
                            });
                        } else {
                            $("#sales-batchno-orderDetailAddPage").widget({
                                referwid: 'RL_T_STORAGE_BATCH_LIST',
                                width: 150,
                                singleSelect: true,
                                disabled: true
                            });
                            $("#sales-storageid-orderDetailAddPage").widget("readonly", false);

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
                            orderid: $("#sales-id-orderAddPage").val()
                        }, 'sales/getAuxUnitNumAndPrice.do');
                        var retjson = $.parseJSON(ret);
                        if (formatterDefineMoney(row.taxprice, 6) != formatterDefineMoney(retjson.taxprice, 6)) {
                            order_taxpricechange = "1";
                        } else {
                            order_taxpricechange = "0";
                        }

                        $("#sales-form-orderDetailEditPage").form('validate');
                    }
                });
            }
        }
        function editSaveDetail(go) { //修改数据确定后操作，
            var flag = $("#sales-form-orderDetailEditPage").form('validate');
            if (flag == false) {
                return false;
            }
            var row = $wareList.datagrid('getSelected');

            var rowIndex = $wareList.datagrid('getRowIndex', row);
            var form = $("#sales-form-orderDetailEditPage").serializeJSON();
            var goodsJson = $("#sales-goodsId-orderDetailAddPage").goodsWidget('getObject');
            if (goodsJson == null || goodsJson == "") goodsJson = $wareList.datagrid('getSelected').goodsInfo;
            form.goodsInfo = goodsJson;

            if(row.goodsMaxNum!=0&&parseFloat(form.unitnum)>parseFloat(row.goodsMaxNum)){
                $.messager.alert("提醒", "商品数量不能大于订货单数量"+row.goodsMaxNum);
                return false;
            }
            form.fixnum = form.unitnum;
            if (form.overnum != 0) {
                form.auxnumdetail = form.auxnum + form.auxunitname + form.overnum + form.unitname;
            } else {
                form.auxnumdetail = form.auxnum + form.auxunitname;
            }
            $wareList.datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
            $("#sales-dialog-orderAddPage-content").dialog('close', true)
            groupGoods();
            countTotal(leftAmount, receivableAmount);
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
                    countTotal(leftAmount, receivableAmount);
                }
            });
        }

        //商品折扣添加页面
        function beginAddDiscountDetail() {
            var customer = $("#sales-customer-orderAddPage").customerWidget("getValue");
            if (customer == '') {
                $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
                $("#sales-customer-orderAddPage").focus();
                return false;
            }
            var detailrows = $("#sales-datagrid-orderAddPage").datagrid('getRows');
            var goodsid = "";
            for (var i = 0; i < detailrows.length; i++) {
                var rowJson = detailrows[i];
                if (rowJson.goodsid != undefined && (rowJson.isdiscount == null || rowJson.isdiscount == '0')) {
                    if (goodsid == "") {
                        if (rowJson.goodsid != null && rowJson.goodsid != "") {
                            goodsid = rowJson.goodsid;
                        }
                    } else {
                        if (rowJson.goodsid != null && rowJson.goodsid != "") {
                            goodsid += "," + rowJson.goodsid;
                        }
                    }
                }
            }
            if (goodsid == "") {
                $.messager.alert("提醒", "请先添加商品信息");
                return false;
            }
            $('<div id="sales-dialog-orderAddPage-content"></div>').appendTo('#sales-dialog-orderAddPage');
            $("#sales-dialog-orderAddPage-content").dialog({
                title: '商品折扣添加',
                width: 680,
                height: 400,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                resizable: true,
                closed: true,
                cache: false,
                href: 'sales/orderDiscountAddPage.do',
                onClose: function () {
                    $("#sales-dialog-orderAddPage-content").dialog("destroy");
                },
                onLoad: function () {
                    $("#sales-order-goodsid").focus();
                }
            });
            $("#sales-dialog-orderAddPage-content").dialog("open");
        }
        //添加商品折扣
        function addSaveDetailDiscount() {
            var flag = $("#sales-form-orderDetailAddPage").form('validate');
            if (flag == false) {
                return false;
            }
            var form = $("#sales-form-orderDetailAddPage").serializeJSON();
            var widgetJson = $("#sales-order-goodsid").goodsWidget('getObject');
            form.goodsInfo = widgetJson;
            var remark = $("#goodsDiscountRemark").val();
            form.remark = remark;
            var rowIndex = 0;
            var rows = $wareList.datagrid('getRows');
            for (var i = 0; i < rows.length; i++) {
                var rowJson = rows[i];
//        if(rowJson.goodsid==widgetJson.id && rowJson.isdiscount=='1'){
//            rowIndex = i;
//            break;
//        }
//        if(rowJson.goodsid == undefined){
//            rowIndex = i;
//            break;
//        }
                if (rowJson.goodsid == undefined && rowJson.isdiscount != "2") {
                    rowIndex = i;
                    break;
                }
                if (rowJson.goodsid == undefined && rowJson.isdiscount == '2' && rows[i + 1].goodsid == undefined && rows[i + 1].isdiscount != '2' && rows[i + 1].goodsid == undefined) {
                    rowIndex = i + 1;
                    break;
                }

            }
            if (rowIndex == rows.length - 1) {
                $wareList.datagrid('appendRow', {}); //如果是最后一行则添加一新行
            }
            $wareList.datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
            $("#sales-dialog-orderAddPage-content").dialog('close');
            countTotal(leftAmount, receivableAmount);
        }
        //修改商品折扣页面
        function beginEditDetailDiscount() {
            var customer = $("#sales-customer-orderAddPage").customerWidget("getValue");
            if (customer == '') {
                $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
                $("#sales-customer-orderAddPage").focus();
                return false;
            }
            var row = $wareList.datagrid('getSelected');
            if (row == null) {
                $.messager.alert("提醒", "请选择一条记录");
                return false;
            }
            $('<div id="sales-dialog-orderAddPage-content"></div>').appendTo('#sales-dialog-orderAddPage');
            $("#sales-dialog-orderAddPage-content").dialog({
                title: '商品折扣修改',
                width: 680,
                height: 400,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                resizable: true,
                closed: true,
                cache: false,
                href: 'sales/orderDiscountEditPage.do',
                modal: true,
                onClose: function () {
                    $("#sales-dialog-orderAddPage-content").dialog("destroy");
                },
                onLoad: function () {
                    getNumberBox("sales-order-discount").focus();
                    getNumberBox("sales-order-discount").select();
                }
            });
            $("#sales-dialog-orderAddPage-content").dialog("open");
        }
        //修改保存商品折扣
        function editSaveDetailDiscount() {
            var flag = $("#sales-form-orderDetailAddPage").form('validate');
            if (flag == false) {
                return false;
            }
            var form = $("#sales-form-orderDetailAddPage").serializeJSON();
            var row = $wareList.datagrid('getSelected');
            var rowIndex = $wareList.datagrid('getRowIndex', row);
            form.goodsInfo = row.goodsInfo;
            $wareList.datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
            $("#sales-dialog-orderAddPage-content").dialog('close');
            countTotal(leftAmount, receivableAmount);
        }

        //历史价格查看
        function showHistoryGoodsPrice() {
            var row = $("#sales-datagrid-orderAddPage").datagrid('getSelected');
            if (row == null) {
                $.messager.alert("提醒", "请选择一条记录");
                return false;
            }
            var businessdate = $("#sales-businessdate-orderAddPage").val();
            var customerid = $("#sales-customer-hidden-orderAddPage").val();
            var customername = $("#sales-customer-orderAddPage").customerWidget('getText');
            var goodsid = row.goodsid;
            var goodsname = row.goodsInfo.name;
            $("#orderPage-goods-history-price").dialog({
                title: '客户[' + customerid + '] 商品[' + goodsid + ']' + goodsname + ' 历史价格表',
                width: 600,
                height: 400,
                closed: false,
                modal: true,
                cache: false,
                maximizable: true,
                resizable: true,
                href: 'sales/showRejectBillHistoryGoodsPricePage.do',
                queryParams: {customerid: customerid, goodsid: goodsid, businessdate: businessdate}
            });
        }

        function modifyGoodsContractPrice() {
            if (beginAddAlert()) {
                var row = $("#sales-datagrid-orderAddPage").datagrid('getSelected');
                var id = $("#sales-backid-orderPage").val();
                if (row.id == undefined) {
                    return false;
                }
                loading("合同价修改中……");
                $.ajax({
                    dataType: 'json',
                    type: 'post',
                    url: 'sales/modifyGoodsContractPrice.do',
                    data: 'id=' + id + "&goodsid=" + row.goodsid + "&price=" + row.taxprice,
                    success: function (json) {
                        loaded();
                        if (json.flag) {
                            if (json.msg == undefined) {
                                $.messager.alert("提醒", "单据商品价格修改成功!");
                            } else {
                                $.messager.alert("提醒", json.msg);
                            }
                        } else {
                            $.messager.alert("提醒", "设置失败");
                        }
                    }

                });

            }

        }

        function beginAddAlert() {
            var customer = $("#sales-customer-orderAddPage").customerWidget("getValue");
            if (customer == '') {
                $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
                $("#sales-customer-orderAddPage").focus();
                return false;
            }
            var detailrows = $("#sales-datagrid-orderAddPage").datagrid('getRows');
            var goodsid = "";
            for (var i = 0; i < detailrows.length; i++) {
                var rowJson = detailrows[i];
                if (!isObjectEmpty(rowJson)) {
                    if (rowJson.goodsid != undefined && (rowJson.isdiscount == null || rowJson.isdiscount == '0')) {
                        if (goodsid == "") {
                            if (rowJson.goodsid != null && rowJson.goodsid != "") {
                                goodsid = rowJson.goodsid;
                            }
                        } else {
                            if (rowJson.goodsid != null && rowJson.goodsid != "") {
                                goodsid += "," + rowJson.goodsid;
                            }
                        }
                    }
                }
            }
            if (goodsid == "") {
                $.messager.alert("提醒", "请先添加商品信息");
                return false;
            } else {
                return true;
            }
        }

        //显示订单折扣添加页面
        function beginAddOrderDiscountDetail() {
            if (beginAddAlert()) {
                $('<div id="sales-dialog-orderAddPage-content"></div>').appendTo('#sales-dialog-orderAddPage');
                $("#sales-dialog-orderAddPage-content").dialog({
                    title: '订单折扣添加',
                    width: 300,
                    height: 250,
                    collapsible: false,
                    minimizable: false,
                    maximizable: true,
                    resizable: true,
                    closed: true,
                    cache: false,
                    href: 'sales/orderDetailDiscountAddPage.do',
                    onClose: function () {
                        $("#sales-dialog-orderAddPage-content").dialog("destroy");
                    },
                    onLoad: function () {
                        //$("#sales-order-discount").next('span').find('input').focus()
                        getNumberBox("sales-order-discount").focus();
                    }
                });
                $("#sales-dialog-orderAddPage-content").dialog("open");
            }


        }

        //显示品牌折扣添加页面
        function beginAddBrandDiscountDetail() {
            if (beginAddAlert()) {

                var customer = $("#sales-customer-orderAddPage").customerWidget("getValue");
                $('<div id="sales-dialog-orderAddPage-content"></div>').appendTo('#sales-dialog-orderAddPage');
                $("#sales-dialog-orderAddPage-content").dialog({
                    title: '品牌折扣添加',
                    width: 430,
                    height: 350,
                    collapsible: false,
                    minimizable: false,
                    maximizable: true,
                    resizable: true,
                    closed: true,
                    cache: false,
                    href: 'sales/orderBrandDiscountAddPage.do?cid=' + customer,
                    onClose: function () {
                        $("#sales-dialog-orderAddPage-content").dialog("destroy");
                    },
                    onLoad: function () {
                        $("#sales-order-brandid").focus();
                    }
                });
                $("#sales-dialog-orderAddPage-content").dialog("open");

            }
        }
        //添加品牌折扣
        function addSaveDetailBrandDiscount() {
            var flag = $("#sales-form-orderDetailBrandAddPage").form('validate');
            if (flag == false) {
                return false;
            }
            var form = $("#sales-form-orderDetailBrandAddPage").serializeJSON();
            var widgetJson = $("#sales-order-brandid").widget('getObject');
            var object = {id: widgetJson.id, name: widgetJson.name};
            form.goodsInfo = object;
            var rowIndex = 0;
            var rows = $wareList.datagrid('getRows');
            for (var i = 0; i < rows.length; i++) {
                var rowJson = rows[i];
                if (rowJson.goodsid == widgetJson.id && (rowJson.isdiscount == '1' || rowJson.isdiscount == '2')) {
                    rowIndex = i;
                    break;
                }
                if (rowJson.goodsid == undefined && rowJson.brandid == undefined) {
                    rowIndex = i;
                    break;
                }
            }
            if (rowIndex == rows.length - 1) {
                $wareList.datagrid('appendRow', {}); //如果是最后一行则添加一新行
            }
            $wareList.datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
            $("#sales-dialog-orderAddPage-content").dialog('close');
            countTotal(leftAmount, receivableAmount);
        }
        //品牌折扣修改页面
        function beginEditDetailBrandDiscount() {
            var customer = $("#sales-customer-orderAddPage").customerWidget("getValue");
            if (customer == '') {
                $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
                $("#sales-customer-orderAddPage").focus();
                return false;
            }
            var row = $wareList.datagrid('getSelected');
            if (row == null) {
                $.messager.alert("提醒", "请选择一条记录");
                return false;
            }
            $('<div id="sales-dialog-orderAddPage-content"></div>').appendTo('#sales-dialog-orderAddPage');
            $("#sales-dialog-orderAddPage-content").dialog({
                title: '品牌折扣修改',
                width: 430,
                height: 350,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                resizable: true,
                closed: true,
                cache: false,
                href: 'sales/orderBrandDiscountEditPage.do',
                modal: true,
                onClose: function () {
                    $("#sales-dialog-orderAddPage-content").dialog("destroy");
                },
                onLoad: function () {
                    getNumberBox("sales-order-discount").focus();
                    getNumberBox("sales-order-discount").select();
                }
            });
            $("#sales-dialog-orderAddPage-content").dialog("open");
        }
        //品牌折扣修改保存
        function editSaveDetailBrandDiscount() {
            var flag = $("#sales-form-orderDetailBrandAddPage").form('validate');
            if (flag == false) {
                return false;
            }
            var form = $("#sales-form-orderDetailBrandAddPage").serializeJSON();
            var row = $wareList.datagrid('getSelected');
            var rowIndex = $wareList.datagrid('getRowIndex', row);
            form.goodsInfo = row.goodsInfo;
            $wareList.datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
            $("#sales-dialog-orderAddPage-content").dialog('close');
            countTotal(leftAmount, receivableAmount);
        }
        //显示促销界面
        function showPromotionPage(id, ptype) {
            var title = "";
            if (ptype == "1") {
                title = "买赠添加";
            } else if (ptype == "2") {
                title = "捆绑添加";
            }
            var storageid = $("#sales-storageid-orderAddPage").widget("getValue");
            var orderid = $("#sales-id-orderAddPage").val();
            if (orderid == null) {
                orderid = "";
            }
            $('<div id="sales-dialog-orderGoodsPromotion-ptype-content"></div>').appendTo('#sales-dialog-orderGoodsPromotion-ptype');
            $("#sales-dialog-orderGoodsPromotion-ptype-content").dialog({
                title: title,
                width: 800,
                height: 450,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                resizable: true,
                closed: true,
                cache: false,
                href: 'sales/orderGoodsPromotionDetailAddPage.do?groupid=' + id + '&storageid=' + storageid + "&orderid=" + orderid,
                modal: true,
                onClose: function () {
                    beginAddDetail();
                    $("#sales-dialog-orderGoodsPromotion-ptype-content").dialog("destroy");
                },
                onLoad: function () {
                    $("#sales-unitnum-orderGoodsPromotionGiveAddPage").focus();
                    $("#sales-unitnum-orderGoodsPromotionGiveAddPage").select();

                    $("#sales-unitnum-orderGoodsPromotionBundAddPage").focus();
                    $("#sales-unitnum-orderGoodsPromotionBundAddPage").select();
                }
            });
            $("#sales-dialog-orderGoodsPromotion-ptype-content").dialog("open");
            $("#sales-dialog-orderAddPage-content").dialog('close');
        }
        function insertRow(data) {
            var rowIndex = 0;
            var rows = $wareList.datagrid('getRows');
            var updateFlag = false;
            for (var i = 0; i < rows.length; i++) {
                var rowJson = rows[i];
                if (rowJson.goodsid == undefined && rowJson.brandid == undefined) {
                    rowIndex = i;
                    break;
                }
            }
            if (rowIndex == rows.length - 1) {
                $wareList.datagrid('appendRow', {}); //如果是最后一行则添加一新行
            }
            $wareList.datagrid('updateRow', {index: rowIndex, row: data}); //将数据更新到列表中
            groupGoods();
        }
        function updateRow(data) {
            var rowIndex = 0;
            var rows = $wareList.datagrid('getRows');
            var updateFlag = false;
            var groupIndex = 0;
            for (var i = 0; i < rows.length; i++) {
                var rowJson = rows[i];
                if (rowJson.groupid == data.groupid && rowJson.deliverytype == '0') {
                    groupIndex = i;
                }
                if (rowJson.goodsid == data.goodsid && rowJson.groupid == data.groupid
                    && rowJson.deliverytype == data.deliverytype && (formatterMoney(rowJson.taxprice) - formatterMoney(data.taxprice)) == 0) {
                    updateFlag = true;
                    rowIndex = i;
                    break;
                }
            }
            if (updateFlag) {
                $wareList.datagrid('updateRow', {index: rowIndex, row: data}); //将数据更新到列表中
            } else {
                $wareList.datagrid('insertRow', {index: groupIndex + 1, row: data});
            }
        }
        //删除分组
        function deleteRows(groupid) {
            var flag = false;
            var addrows = $("#sales-datagrid-orderAddPage").datagrid("getRows");
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
        //买赠捆绑分组
        function groupGoods() {
            var rows = $("#sales-datagrid-orderAddPage").datagrid("getRows");
            var merges = [];
            var groupIDs = "";
            for (var i = 0; i < rows.length; i++) {
                var groupid = rows[i].groupid;
                //特价的商品不需要捆绑组合 编号TJTZD开头的
                if (groupid != null && groupid != "" && groupIDs.indexOf(groupid) == -1 && groupid.indexOf("TJTZD") == -1) {
                    groupIDs = groupid + ",";
                    var count = 0;
                    for (var j = 0; j < rows.length; j++) {
                        if (groupid == rows[j].groupid) {
                            count++;
                        }
                    }
                    if (count > 1) {
                        merges.push({index: i, rowspan: count});
                    }
                }
            }
            for (var i = 0; i < merges.length; i++) {
                $("#sales-datagrid-orderAddPage").datagrid('mergeCells', {
                    index: merges[i].index,
                    field: 'ck',
                    rowspan: merges[i].rowspan,
                    type: 'body'
                });
            }
        }
        //修改买赠捆绑页面
        function showPromotionEditPage(data) {
            var groupid = data.groupid;
            var orderid = $("#sales-id-orderAddPage").val();
            if (orderid == null) {
                orderid = "";
            }
            if (groupid != null && groupid != "") {
                var buyObject = null;
                var giveList = [];
                var addrows = $("#sales-datagrid-orderAddPage").datagrid("getRows");
                for (var i = 0; i < addrows.length; i++) {
                    if (groupid == addrows[i].groupid) {
                        if (addrows[i].deliverytype == '0') {
                            buyObject = addrows[i];
                        } else {
                            giveList.push({goodsid: addrows[i].goodsid, unitnum: addrows[i].unitnum});
                        }
                    }
                }
                var goodsStr = JSON.stringify(giveList);
                $.ajax({
                    url: 'sales/getSalesOrderPromotionInfo.do',
                    dataType: 'json',
                    type: 'post',
                    data: {groupid: groupid, goodslist: goodsStr},
                    success: function (json) {
                        if (json.flag) {
                            //买赠
                            if (json.ptype == '1') {
                                $('<div id="sales-dialog-orderGoodsPromotion-ptype-content"></div>').appendTo('#sales-dialog-orderGoodsPromotion-ptype');
                                $("#sales-dialog-orderGoodsPromotion-ptype-content").dialog({
                                    title: "买赠修改",
                                    width: 800,
                                    height: 450,
                                    collapsible: false,
                                    minimizable: false,
                                    maximizable: true,
                                    resizable: true,
                                    closed: true,
                                    cache: false,
                                    href: 'sales/orderGoodsPromotionDetailEditPage.do?groupid=' + groupid + "&orderid=" + orderid,
                                    modal: true,
                                    onClose: function () {
                                        $("#sales-dialog-orderGoodsPromotion-ptype-content").dialog("destroy");
                                    },
                                    onLoad: function () {
                                        $("#sales-form-orderGoodsPromotionGiveAddPage").form('load', buyObject);
                                        giveunitnumChange(1);
                                        $("#sales-unitnum-orderGoodsPromotionGiveAddPage").focus();
                                        $("#sales-unitnum-orderGoodsPromotionGiveAddPage").select();

                                        formaterNumSubZeroAndDot();

                                        $("#sales-form-orderGoodsPromotionGiveAddPage").form('validate');
                                    }
                                });
                                $("#sales-dialog-orderGoodsPromotion-ptype-content").dialog("open");
                            } else if (json.ptype == '2') {
                                $('<div id="sales-dialog-orderGoodsPromotion-ptype-content"></div>').appendTo('#sales-dialog-orderGoodsPromotion-ptype');
                                $("#sales-dialog-orderGoodsPromotion-ptype-content").dialog({
                                    title: "捆绑修改",
                                    width: 800,
                                    height: 450,
                                    collapsible: false,
                                    minimizable: false,
                                    maximizable: true,
                                    resizable: true,
                                    closed: true,
                                    cache: false,
                                    href: 'sales/orderGoodsPromotionDetailEditPage.do?groupid=' + groupid + "&orderid=" + orderid,
                                    modal: true,
                                    onClose: function () {
                                        $("#sales-dialog-orderGoodsPromotion-ptype-content").dialog("destroy");
                                    },
                                    onLoad: function () {
                                        $("#sales-unitnum-orderGoodsPromotionBundAddPage").val(json.bundnum);
                                        giveunitnumChange();
                                        $("#sales-unitnum-orderGoodsPromotionBundAddPage").focus();
                                        $("#sales-unitnum-orderGoodsPromotionBundAddPage").select();

                                        formaterNumSubZeroAndDot();

                                        $("#sales-form-orderGoodsPromotionGiveAddPage").form('validate');
                                    }
                                });
                                $("#sales-dialog-orderGoodsPromotion-ptype-content").dialog("open");
                            } else {
                                beginEditDetail(data);
                            }
                        } else {
                            beginEditDetail(data);
                        }
                    }
                });
            }
        }
        function addSaveDetail(go, groupid) { //添加新数据确定后操作，
            var flag = $("#sales-form-orderDetailAddPage").form('validate');
            if (flag == false) {
                return false;
            }
            var form = $("#sales-form-orderDetailAddPage").serializeJSON();
            var goodsJson = $("#sales-goodsId-orderDetailAddPage").goodsWidget('getObject');
            form.goodsInfo = goodsJson;
            form.groupid = groupid;
            var customer = $("#sales-customer-orderAddPage-hidden").val();
            form.fixnum = form.unitnum;
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
                if (go) {
                    ++insertIndex;
                } else {
                    insertIndex = undefined;
                }
                //insertIndex = undefined
            }
            if (go) { //go为true确定并继续添加一条
                $("#sales-form-orderDetailAddPage").form("clear");
                $("input[name=deliverydate]").val(deliverydate);
                $("#sales-deliverytype-orderDetailAddPage").val("0");
            }
            else { //否则直接关闭
                $("#sales-dialog-orderAddPage-content").dialog('close', true);
            }
            countTotal(leftAmount, receivableAmount); //第添加一条商品明细计算一次合计
        }
        //删除分组
        function deleteRowsWithoutGoods(groupid) {
            var flag = false;
            var addrows = $("#sales-datagrid-orderAddPage").datagrid("getRows");
            for (var i = 0; i < addrows.length; i++) {
                if (groupid == addrows[i].groupid && addrows[i].deliverytype == '1') {
                    $wareList.datagrid('deleteRow', i);
                    flag = true;
                    break;
                }
            }
            if (flag) {
                deleteRowsWithoutGoods(groupid);
            }
        }
        function showFullFreePage(orderid, customerid, goodsid, num, type) {
            $('<div id="sales-dialog-orderGoodsFullFree-ptype-content"></div>').appendTo('#sales-dialog-orderGoodsPromotion-ptype');
            $("#sales-dialog-orderGoodsFullFree-ptype-content").dialog({
                title: "满赠明细",
                width: 800,
                height: 450,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                resizable: true,
                closed: true,
                cache: false,
                href: 'sales/showFullFreeListPage.do?orderid=' + orderid + '&customerid=' + customerid + "&goodsid=" + goodsid + "&num=" + num,
                modal: true,
                buttons: [{
                    text: '保存',
                    handler: function () {
                        if (type == "add") {
                            var groupid = $(".fullFreePage-group:checked").val();
                            if (groupid == null) {
                                $.messager.alert("提醒", "请选择赠品！");
                            } else {
                                addSaveDetail(true, groupid);
                                var rows = $("#sales-table-fullFreePage").datagrid("getRows");
                                for (var i = 0; i < rows.length; i++) {
                                    if (rows[i].groupid == groupid) {
                                        var goodsid = rows[i].goodsid;
                                        var unitnum = rows[i].unitnum;
                                        var fixnum = rows[i].unitnum;
                                        var auxnum = rows[i].auxnum;
                                        var overnum = rows[i].overnum;
                                        var auxnumdetail = rows[i].auxnumdetail;
                                        var totalbox = rows[i].totalbox;
                                        var usablenum = rows[i].usablenum;
                                        var data = {
                                            goodsid: goodsid,
                                            unitid: rows[i].unitid,
                                            unitname: rows[i].unitname,
                                            fixnum: fixnum,
                                            unitnum: unitnum,
                                            auxunitid: rows[i].auxunitid,
                                            auxunitname: rows[i].auxunitname,
                                            auxnum: auxnum,
                                            overnum: overnum,
                                            auxnumdetail: auxnumdetail,
                                            totalbox: totalbox,
                                            goodsInfo: rows[i].goodsInfo,
                                            deliverytype: '1',
                                            groupid: groupid,
                                            taxprice: 0,
                                            taxamount: rows[i].taxamount,
                                            boxprice: 0,
                                            notaxprice: 0,
                                            notaxamount: 0,
                                            tax: 0,
                                            taxtype: '',
                                            taxtypename: '',
                                            oldprice: 0,
                                            usablenum: usablenum,
                                            remark: '满赠'
                                        };
                                        insertRow(data);
                                    }
                                }
                                $("#sales-dialog-orderGoodsFullFree-ptype-content").dialog("close");
                            }
                        } else if (type == "edit") {
                            var groupid = $(".fullFreePage-group:checked").val();
                            if (groupid == null) {
                                $.messager.alert("提醒", "请选择赠品！");
                            } else {
                                editSaveDetail(true);
                                deleteRowsWithoutGoods(groupid);
                                var rows = $("#sales-table-fullFreePage").datagrid("getRows");
                                var row = $wareList.datagrid('getSelected');
                                var rowIndex = $wareList.datagrid('getRowIndex', row);
                                for (var i = 0; i < rows.length; i++) {
                                    if (rows[i].groupid == groupid) {
                                        var goodsid = rows[i].goodsid;
                                        var unitnum = rows[i].unitnum;
                                        var fixnum = rows[i].unitnum;
                                        var auxnum = rows[i].auxnum;
                                        var overnum = rows[i].overnum;
                                        var auxnumdetail = rows[i].auxnumdetail;
                                        var totalbox = rows[i].totalbox;
                                        var usablenum = rows[i].usablenum;
                                        var data = {
                                            goodsid: goodsid,
                                            unitid: rows[i].unitid,
                                            unitname: rows[i].unitname,
                                            fixnum: fixnum,
                                            unitnum: unitnum,
                                            auxunitid: rows[i].auxunitid,
                                            auxunitname: rows[i].auxunitname,
                                            auxnum: auxnum,
                                            overnum: overnum,
                                            auxnumdetail: auxnumdetail,
                                            totalbox: totalbox,
                                            goodsInfo: rows[i].goodsInfo,
                                            deliverytype: '1',
                                            groupid: groupid,
                                            taxprice: 0,
                                            taxamount: rows[i].taxamount,
                                            boxprice: 0,
                                            notaxprice: 0,
                                            notaxamount: 0,
                                            tax: 0,
                                            taxtype: '',
                                            taxtypename: '',
                                            oldprice: 0,
                                            usablenum: usablenum,
                                            remark: '满赠'
                                        };
                                        rowIndex++;
                                        $wareList.datagrid('insertRow', {index: rowIndex, row: data});
                                    }
                                }
                                groupGoods();
                                $("#sales-dialog-orderGoodsFullFree-ptype-content").dialog("close");
                            }
                        }

                    }
                }],
                onClose: function () {
                    $("#sales-dialog-orderGoodsFullFree-ptype-content").dialog("destroy");
                }
            });
            $("#sales-dialog-orderGoodsFullFree-ptype-content").dialog("open");
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
        var leftAmount = 0;
        var receivableAmount = 0;
        //客户变更后 更新明细价格以及相关信息
        function changeGoodsPrice() {
            var oldcustomerid = $("#sales-customer-hidden-orderAddPage").val();
            var customerid = $("#sales-customer-orderAddPage").customerWidget("getValue");
            $("#sales-customer-hidden-orderAddPage").val(customerid);

            if (oldcustomerid != null && oldcustomerid != "" && oldcustomerid != customerid) {
                loading("客户变更，明细价格调整中");
                var rows = $wareList.datagrid('getRows');
                var date = $("input[name='saleorder.businessdate']").val();
                var detailArray = new Array();
                for (var i = 0; i < rows.length; i++) {
                    // if (rows[i].isdiscount == null || rows[i].isdiscount == '0') {
                    //
                    // }
                    var goodsid = rows[i].goodsid;
                    var num = rows[i].unitnum;
                    if (goodsid) {
                        detailArray.push({goodsid: goodsid, num: num, isdiscount:rows[i].isdiscount})
                    }
                }

                $.ajax({
                    url: 'sales/multiRefreshCustomerGoodsPrice.do',
                    dataType: 'json',
                    type: 'post',
                    data: {customerid: customerid, date: date, detailsStr: JSON.stringify(detailArray)},
                    async: false,
                    success: function (json) {

                        loaded();
                        var detailsArr = json.details;
                        for(var j = 0; j < detailsArr.length; j++) {
                            if (detailsArr[j].isdiscount == null || detailsArr[j].isdiscount == '0') {

                                var row = rows[j];
                                row.taxprice = detailsArr[j].taxprice;
                                row.notaxprice = detailsArr[j].notaxprice;
                                row.taxamount = detailsArr[j].taxamount;
                                row.notaxamount = detailsArr[j].notaxamount;
                                row.tax = detailsArr[j].tax;
                                row.oldprice = detailsArr[j].taxprice;
                                row.isnew = detailsArr[j].isnew;
                                $wareList.datagrid('updateRow', {index: j, row: row});
                            }
                        }

                        leftAmount = json.receivableInfo.leftAmount;
                        receivableAmount = json.receivableInfo.receivableAmount;
                    }
                });

                getCustomerFinanceInfo(customerid);
                // if (count > 0) {
                //     $("#sales-customer-hidden-orderAddPage").val(customerid);
                //     $.messager.alert("提醒", "客户变更，自动调整订单明细中的商品价格！");
                //
                // }
                loaded();
            } else {
                getCustomerFinanceInfo(customerid);
            }

        }

        //根据客户编号显示客户详情
        function showCustomer(customerId) {
            $('<div id="sales-dialog-customer"></div>').appendTo('#sales-dialog-orderPage');
            $('#sales-dialog-customer').dialog({
                maximizable: true,
                resizable: true,
                title: "客户档案【查看】",
                width: 740,
                height: 450,
                closed: true,
                cache: false,
                href: 'basefiles/showCustomerSimplifyViewPage.do?id=' + customerId,
                modal: true,
                onClose: function () {
                    $('#sales-dialog-customer').dialog("destroy");
                }
            });
            $("#sales-dialog-customer").dialog("open");
        }

        // //当前客户应收款及余额情况
        function getCustomerFinanceInfo(customerid) {
            $.ajax({
                url: 'sales/getCustomerFinanceInfo.do',
                dataType: 'json',
                type: 'post',
                data: {customerid: customerid},
                async: false,
                success: function (json) {
                    leftAmount = json.leftAmount;
                    receivableAmount = json.receivableAmount;
                }
            });
            countTotal(leftAmount, receivableAmount);
        }

        function showChangePriceDialog() {

            $('#sales-changePriceDialog-orderPage').dialog({
                title: '修改商品价格',
                width: 300,
                height: 170,
                closed: false,
                cache: false,
                modal: true,
                content: '<div style="padding: 20px;"><div style="line-height: 25px;">该操作将会批量修改选中商品单价。</div><div style="line-height: 25px; width:140px; float: left;">确定要修改商品单价为：</div><div style="line-height: 25px; float: left;"><select id="sales-pricetype-orderPage" style="width: 100px;"><option value="1">最新采购价</option><option value="2">最新批次价</option><option value="3">采购价</option></select></div></div>',
                buttons: [{
                    text: '关闭',
                    iconCls: 'button-close',
                    handler: function () {
                        $('#sales-changePriceDialog-orderPage').dialog('close');
                    }
                }, {
                    text: '确定',
                    iconCls: 'button-save',
                    handler: function () {

                        var pricetype = $('#sales-pricetype-orderPage').val();
                        var rows = $("#sales-datagrid-orderAddPage").datagrid('getChecked');
                        var goodsArray = new Array();
                        for (var i in rows) {
                            var row = rows[i];
                            if ((row.groupid || '') == '' && row.goodsid) {

                                row.index = i;
                                var goodsInfo = {
                                    goodsid: row.goodsid,
                                    unitnum: row.unitnum,
                                    batchno: row.batchno
                                };
                                goodsArray.push(goodsInfo);
                            }
                        }

                        if (goodsArray.length == 0) {
                            $.messager.alert('提醒', '请选择正常商品');
                            return true;
                        }

                        loading('设定中...');
                        var url = 'sales/setOrderNewbuyprice.do';
                        if (pricetype == '2') {
                            url = 'sales/setOrderBatchprice.do';
                        } else if (pricetype == '3') {
                            url = 'sales/setOrderHighestbuyprice.do';
                        }

                        $.ajax({
                            type: 'post',
                            url: url,
                            data: {goodsStr: JSON.stringify(goodsArray)},
                            dataType: 'json',
                            success: function (json) {

                                loaded();
                                var oldDetails = $("#sales-datagrid-orderAddPage").datagrid('getChecked');
                                var newDetails = json;
                                for (var i in newDetails) {

                                    var newDetail = newDetails[i];
                                    var oldDetail = oldDetails[i];
                                    if (!oldDetail.goodsid) {
                                        continue;
                                    }
                                    oldDetail.taxprice = newDetail.taxprice;
                                    oldDetail.boxprice = newDetail.boxprice;
                                    oldDetail.taxamount = newDetail.taxamount;
                                    oldDetail.notaxprice = newDetail.notaxprice;
                                    oldDetail.notaxamount = newDetail.notaxamount;
                                    oldDetail.tax = newDetail.tax;

                                    var index = $("#sales-datagrid-orderAddPage").datagrid('getRowIndex', rows[i]);
                                    $("#sales-datagrid-orderAddPage").datagrid('updateRow', {
                                        index: index,
                                        row: oldDetail
                                    });
                                }
                                $.messager.alert('提醒', '价格已重新设定，请确认。');
                                $('#sales-changePriceDialog-orderPage').dialog('close');
                            },
                            error: function (err) {
                                $.messager.alert('错误', '出错了！');
                            }
                        })
                    }
                }]
            });
        }

        //批量添加商品
        function beginAddDetailByBrandAndSort() { //开始批量添加商品信息
            var customer = $("#sales-customer-orderAddPage-hidden").val();
            if (customer == '') {
                $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
                $("#sales-customer-orderAddPage").focus();
                return false;
            }
            $('<div id="sales-dialog-orderAddPage-content"></div>').appendTo('#sales-dialog-orderAddPage');
            $("#sales-dialog-orderAddPage-content").dialog({ //弹出新添加窗口
                title: '批量添加商品信息(按ESC退出)',
                maximizable: true,
                width: 1000,
                height: 500,
                closed: false,
                modal: true,
                cache: false,
                resizable: true,
                href: 'sales/orderDetailAddByBrandAndSortPage.do?customerid=' + customer,
                onClose: function () {
                    $('#sales-dialog-orderAddPage-content').dialog("destroy");
                },
                onLoad: function () {
                    $("#sales-goodsId-orderDetailAddPage").focus();
                }
            });
        }
        //根据商品(编码或品牌)完成对应折扣
        function computeDiscount(brandid, goodsid, taxamount) {
            $.ajax({
                url: 'sales/computeDispatchBillDiscountTax.do',
                type: 'post',
                data: {brandid: brandid, goodsid: goodsid, taxamount: taxamount},
                dataType: 'json',
                async: false,
                success: function (json) {
                    $("#sales-order-notaxamount").numberbox("setValue", json.notaxamount);
                    $("#sales-order-tax").val(json.tax);
                    $("#sales-order-taxtype").val(json.taxtype);
                    $("#sales-order-taxtypename").val(json.taxtypename);
                }
            });
        }
    </script>
    <%--打印开始 --%>
    <script type="text/javascript">
        $(function () {
            var printLimit = $("#sales-printlimit-orderAddPage").val() || 0;

            function printAfterHandler(option, printParam) {
                var isblank = false;
                var isorder = false;
                var isbill = false;
                $.each(option.code, function (i, item) {
                    if (item.codename == "storage_orderblank")
                        isblank = true;
                    else if (item.codename == "storage_deliveryorder" || item.codename == "sales_order")
                        isorder = true;
                    else if (item.codename == "storage_dispatchbill")
                        isbill = true;
                });
                if (isblank) {
                    //配货单打印
                    var printtimes = $("#sales-phprinttimes-orderAddPage").val();
                    $("#sales-phprinttimes-orderAddPage").val(printtimes + 1);
                    if (0 != printLimit) {
                        $("#sales-buttons-orderPage").buttonWidget("disableMenuItem", "button-print-orderblank");
                    }
                } else if (isorder) {
                    //库位套打  销售订单打印
                    var printtimes = $("#sales-printtimes-orderAddPage").val();
                    $("#sales-printtimes-orderAddPage").val(printtimes + 1);
                    if (0 != printLimit) {
                        $("#sales-buttons-orderPage").buttonWidget("disableMenuItem", "button-print-orderblank");
                        $("#sales-buttons-orderPage").buttonWidget("disableMenuItem", "button-print-DispatchBill");
                        $("#sales-buttons-orderPage").buttonWidget("disableMenuItem", "button-print-DeliveryOrder");
                    }
                } else if (isbill) {
                    //销售单-按订单套打
                    var printtimes = $("#sales-printtimes-orderAddPage").val();
                    $("#sales-printtimes-orderAddPage").val(printtimes + 1);
                    if (0 != printLimit) {
                        $("#sales-buttons-orderPage").buttonWidget("disableMenuItem", "button-print-DispatchBill");
                        $("#sales-buttons-orderPage").buttonWidget("disableMenuItem", "button-print-DeliveryOrder");
                    }
                }
            }

            function getDataNoAudit(tableId, printParam,isprint,option) {
                var billno = $("#sales-id-orderAddPage").val();
                if (billno == "") {
                    $.messager.alert("警告", "找不到要打印的信息!");
                    return false;
                }
                var status = $("#sales-customer-status").val() || "";
                var printtimes = 0;
                if (printParam.codename == "storage_orderblank") {
                    printtimes = $("#sales-phprinttimes-orderAddPage").val() || 0;
                }else{
                    printtimes = $("#sales-printtimes-orderAddPage").val() || 0;
                }
                printParam.saleidarrs = billno;
                if (printtimes > 0)
                    printParam.printIds = [billno];
                return true;
            }

            function getDataWithAudit(tableId, printParam) {
                var billno = $("#sales-id-orderAddPage").val();
                if (billno == "") {
                    $.messager.alert("警告", "找不到要打印的信息!");
                    return false;
                }
                var status = $("#sales-customer-status").val() || "";
                var printtimes = $("#sales-printtimes-orderAddPage").val() || 0;
                printParam.saleidarrs = billno;
                if (printtimes > 0)
                    printParam.printIds = [billno];
                return true;
            }

            //配货单打印
            AgReportPrint.init({
                id: "orderblank-dialog-print",
                code: "storage_orderblank",
                //tableId: "sales-datagrid-orderListPage",
                url_preview: "print/sales/salesOrderblankForXSDPrintView.do",
                url_print: "print/sales/salesOrderblankForXSDPrint.do",
                btnPreview: "button-printview-orderblank",
                btnPrint: "button-print-orderblank",
                printlimit: "${printlimit}",
                getData: function(tableId, printParam,isprint,option){
                    printParam.codename = "storage_orderblank";
                    return getDataNoAudit(tableId, printParam,isprint,option);
                },
                printAfterHandler: printAfterHandler
            });
            //库位套打
            AgReportPrint.init({
                id: "deliveryorder-dialog-print",
                code: "storage_deliveryorder",
                //tableId: "sales-datagrid-orderListPage",
                url_preview: "print/sales/salesDeliveryOrderForXSDPrintView.do",
                url_print: "print/sales/salesDeliveryOrderForXSDPrint.do",
                btnPreview: "button-printview-DeliveryOrder",
                btnPrint: "button-print-DeliveryOrder",
                printlimit: "${printlimit}",
                exPrintParam: {
                    printOrder: 1
                },
                getData: getDataWithAudit,
                printAfterHandler: printAfterHandler
            });
            //销售单-按订单套打
            AgReportPrint.init({
                id: "dispatchbill-dialog-print",
                code: "storage_dispatchbill",
                //tableId: "sales-datagrid-orderListPage",
                url_preview: "print/sales/salesDispatchBillForXSDPrintView.do",
                url_print: "print/sales/salesDispatchBillForXSDPrint.do",
                btnPreview: "button-printview-DispatchBill",
                btnPrint: "button-print-DispatchBill",
                printlimit: "${printlimit}",
                getData: getDataWithAudit,
                printAfterHandler: printAfterHandler
            });
            //销售订单打印
            AgReportPrint.init({
                id: "order-dialog-print",
                code: "sales_order",
                //tableId: "sales-datagrid-orderListPage",
                url_preview: "print/sales/salesOrderPrintView.do",
                url_print: "print/sales/salesOrderPrint.do",
                btnPreview: "button-printview-salesOrder",
                btnPrint: "button-print-salesOrder",
                printlimit: "${printlimit}",
                getData: function (tableId, printParam) {
                    var billno = $("#sales-id-orderAddPage").val();
                    if (billno == "") {
                        $.messager.alert("警告", "找不到要打印的信息!");
                        return false;
                    }
                    var status = $("#sales-customer-status").val() || "";
                    var printtimes = $("#sales-printtimes-orderAddPage").val() || 0;
                    printParam.saleidarrs = billno;
                    if (printtimes > 0)
                        printParam.printIds = [billno];
                    return true;
                },
                printAfterHandler: printAfterHandler
            });
        });
    </script>
    <%--打印结束 --%>
</body>
</html>
