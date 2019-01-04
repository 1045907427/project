<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售订货单列表页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
    
</head>
<body>
<div id="sales-queryDiv-orderGoodsListPage" style="height:auto;padding:0px">
    <div class="buttonBG" id="sales-buttons-orderGoodsListPage"></div>
    <form id="sales-queryForm-orderGoodsListPage" method="post">
        <input type="hidden" name="exportid" id="sales-exportid-orderGoodsListPage"/>
        <table class="querytable">
            <tr>
                <td>业务日期：</td>
                <td class="tdinput"><input type="text" name="businessdate" style="width:100px;" class="Wdate"
                                           onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value=""/>
                    到<input type="text" name="businessdate1" class="Wdate" style="width:100px;"
                            onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
                <td>编号：</td>
                <td class="tdinput"><input type="text" name="id" class="len180"/></td>
                <td>销售部门：</td>
                <td class="tdinput">
                    <input type="text" id="sales-salesDept-orderGoodsListPage" name="salesdept" style="width: 130px;"/>
                </td>
            </tr>
            <tr>
                <td>客户：</td>
                <td class="tdinput"><input type="text" id="sales-customer-orderGoodsListPage" style="width: 213px"
                                           name="customerid"/></td>
                <td>状态：</td>
                <td class="tdinput">
                    <select name="status" style="width:180px;">
                        <option></option>
                        <option value="2" selected="selected">保存</option>
                        <option value="3">审核通过</option>
                        <option value="4">已关闭</option>
                        <option value="5">作废</option>
                    </select>
                </td>
                <td>商品名称：</td>
                <td class="tdinput"><input type="text" id="sales-goodsid-orderGoodsListPage" style="width: 130px"
                                           name="goodsid"/></td>
                <%--<td>客户单号：</td>--%>
                <%--<td class="tdinput"><input type="text" name="sourceid" class="len130"/></td>--%>
            </tr>
            <tr>
                <td>打印状态:</td>
                <td>
                    <select id="sales-printsign-orderGoodsListPage" name="printsign" style="width:213px;">
                        <option></option>
                        <option value="1">未打印</option>
                        <%-- 特别
							<option value="2">小于</option>
							<option value="3">小于等于</option>
							 --%>
                        <option value="4">已打印</option>
                        <%-- 特别
							<option value="5">大于等于</option>
							 --%>
                    </select>
                    <input type="hidden" id="sales-printtimes-orderGoodsListPage" name="queryprinttimes" value="0"/>
                </td>
                <td>提货券编号</td>
                <td>
                    <input id="sales-ladingbill-orderGoodsListPage" style="width: 180px;" name="ladingbill"/>
                </td>
                <td colspan="2" class="tdbutton">
                    <a href="javascript:void(0);" id="sales-queay-orderGoodsListPage" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="sales-resetQueay-orderGoodsListPage" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<table id="sales-datagrid-orderGoodsListPage" data-options="border:false"></table>
<input id="createGridColumnLoad-commonCol" hidden="true"/>
<div id="dialog-autoexport"></div>
<div style="display:none">
    <%--通用 --%>
    <div id="salesorder-import-dialog"></div>
</div>
<script type="text/javascript">
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
    var initQueryJSON = $("#sales-queryForm-orderGoodsListPage").serializeJSON();
    $(function () {
        $("#sales-customer-orderGoodsListPage").customerWidget({ //客户参照窗口
            name: 't_sales_order',
            col: 'customerid',
            singleSelect: true,
            isdatasql: false,
            onlyLeafCheck: false
        });
        $("#sales-goodsid-orderGoodsListPage").goodsWidget({ //客户参照窗口
            singleSelect: true,
            isdatasql: false,
            onlyLeafCheck: false,
            isHiddenUsenum:true
        });

        //销售部门控件
        $("#sales-salesDept-orderGoodsListPage").widget({
            name: 't_sales_order',
            col: 'salesdept',
            singleSelect: true,
            width: 130,
            onlyLeafCheck: false
        });
        $("#sales-queay-orderGoodsListPage").click(function () {
            var queryJSON = $("#sales-queryForm-orderGoodsListPage").serializeJSON();
            $("#sales-datagrid-orderGoodsListPage").datagrid('load', queryJSON);
        });
        $("#sales-resetQueay-orderGoodsListPage").click(function () {
            $("#sales-customer-orderGoodsListPage").customerWidget("clear");
            $("#sales-goodsid-orderGoodsListPage").goodsWidget("clear");
            $("#sales-salesDept-orderGoodsListPage").widget('clear');
            $("#sales-queryForm-orderGoodsListPage").form("reset");

            var queryJSON = $("#sales-queryForm-orderGoodsListPage").serializeJSON();
            $("#sales-datagrid-orderGoodsListPage").datagrid('load', queryJSON);
        });
        //按钮
        $("#sales-buttons-orderGoodsListPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/sales/orderGoodsAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addTab('sales/orderGoodsPage.do', "销售订货单新增");
                    }
                },
                </security:authorize>
                <%--<security:authorize url="/sales/orderCopy.do">--%>
                <%--{--%>
                    <%--type: 'button-copy',--%>
                    <%--handler: function () {--%>
                        <%--var con = $("#sales-datagrid-orderGoodsListPage").datagrid('getSelected');--%>
                        <%--if (con == null) {--%>
                            <%--$.messager.alert("提醒", "请选择一条记录");--%>
                            <%--return false;--%>
                        <%--}--%>
                        <%--top.addOrUpdateTab('sales/orderPage.do?type=copy&id=' + con.id, "销售订单新增");--%>
                    <%--}--%>
                <%--},--%>
                <%--</security:authorize>--%>
                <%--<security:authorize url="/sales/orderGoodsImport.do">--%>
                <%--{--%>
                    <%--type: 'button-import',--%>
                    <%--attr: {--%>
                        <%--type: 'importbill',--%>
                        <%--module: 'sales',--%>
                        <%--majorkey: 'id',--%>
                        <%--pojo: "ImportSalesOrder",--%>
                        <%--clazz: "salesOrderService", //spring中注入的类名--%>
                        <%--method: "addDRSalesOrder", //插入数据库的方法--%>
                        <%--importparam: '必填项：客户编码，商品编码，数量。<br/>选填项：单价，金额',--%>
                        <%--onClose: function () { //导入成功后窗口关闭时操作，--%>
                            <%--$("#sales-datagrid-orderGoodsListPage").datagrid('reload');	//更新列表--%>
                        <%--}--%>
                    <%--}--%>
                <%--},--%>
                <%--</security:authorize>--%>
                <security:authorize url="/sales/orderGoodsExport.do">
                {
                    type: 'button-export',
                    attr: {
                        datagrid: "#sales-datagrid-orderGoodsListPage",
                        queryForm: "#sales-queryForm-orderGoodsListPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                        type:'exportUserdefined',
                        name:'订货单列表',
                        url:'sales/exportOrderGoodsData.do'
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/orderGoodsMultiAudit.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        var rows = $("#sales-datagrid-orderGoodsListPage").datagrid('getChecked');
                        if (rows.length == 0) {
                            $.messager.alert("提醒", "请选中需要审核的记录。");
                            return false;
                        }
                        var ids = "", noids = "";
                        for (var i = 0; i < rows.length; i++) {
                            //判断是否存在客户业务员,若不存在不允许审核
                            if (null != rows[i].salesuser && "" != rows[i].salesuser) {
                                ids += rows[i].id + ',';
                            } else {
                                if (noids == "") {
                                    noids = rows[i].id;
                                } else {
                                    noids += "," + rows[i].id;
                                }
                            }
                        }
                        if (noids != "") {
                            $.messager.alert("提醒", "销售订单编号：" + noids + "不存在客户业务员，不允许审核！");
                        }
                        if (ids != "") {
                            loading("核对验证中..");
                            $.getJSON("sales/canAuditOrderGoods.do", {ids: ids}, function (json) {
                                loaded();
                                if (json.flag == true) {
                                    $.messager.confirm("提醒", "确定审核这些订单？", function (r) {
                                        if (r) {
                                            loading("审核中...");
                                            $.ajax({
                                                url: 'sales/auditMultiOrderGoods.do',
                                                dataType: 'json',
                                                type: 'post',
                                                data: {ids: ids},
                                                success: function (json) {
                                                    loaded();
                                                    if (json.flag == true) {
                                                        $.messager.alert("提醒", "审核成功：" + json.success + "&nbsp;审核失败：" + json.failure + "&nbsp;无需审核：" + json.noaudit + "<br/>" + json.msg);
                                                        $("#sales-datagrid-orderGoodsListPage").datagrid('reload');
                                                    }
                                                    else {
                                                        $.messager.alert("提醒", "审核出错");
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
                                    $.messager.confirm("提醒", json.msg + "是否继续审核？", function (r) {
                                        if (r) {
                                            loading("审核中...");
                                            $.ajax({
                                                url: 'sales/auditMultiOrderGoods.do',
                                                dataType: 'json',
                                                type: 'post',
                                                data: {ids: ids},
                                                success: function (json) {
                                                    loaded();
                                                    if (json.flag == true) {
                                                        $.messager.alert("提醒", "审核成功：" + json.success + "&nbsp;审核失败：" + json.failure + "&nbsp;无需审核：" + json.noaudit + "<br/>" + json.msg);
                                                        $("#sales-datagrid-orderGoodsListPage").datagrid('reload');
                                                    }
                                                    else {
                                                        $.messager.alert("提醒", "审核出错");
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
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/orderGoodsSupperAudit.do">
                {
                    type: 'button-supperaudit',
                    handler: function () {
                        var rows = $("#sales-datagrid-orderGoodsListPage").datagrid('getChecked');
                        if (rows.length == 0) {
                            $.messager.alert("提醒", "请选中需要超级审核的记录。");
                            return false;
                        }
                        var ids = "", noids = "";
                        for (var i = 0; i < rows.length; i++) {
                            //判断是否存在客户业务员,若不存在不允许审核
                            if (null != rows[i].salesuser && "" != rows[i].salesuser) {
                                ids += rows[i].id + ',';
                            } else {
                                if (noids == "") {
                                    noids = rows[i].id;
                                } else {
                                    noids += "," + rows[i].id;
                                }
                            }
                        }
                        if (noids != "") {
                            $.messager.alert("提醒", "销售订单编号：" + noids + "不存在客户业务员，不允许审核！");
                        }
                        if (ids != "") {
                            loading("核对验证中..");
                            $.ajax({
                                url: 'sales/canAuditOrderGoods.do',
                                dataType: 'json',
                                type: 'post',
                                data: 'ids=' + ids,
                                success: function (json) {
                                    loaded();
                                    if (json.flag == true) {
                                        $.messager.confirm("提醒", "确定超级审核该订单信息？", function (r) {
                                            if (r) {
                                                loading("超级审核中...");
                                                $.ajax({
                                                    url: 'sales/supplierAuditOrderGoodsMuti.do',
                                                    dataType: 'json',
                                                    type: 'post',
                                                    data: 'ids=' + ids + '&type=1&model=supper',
                                                    success: function (json) {
                                                        loaded();
                                                        if (json.sucids != "") {
                                                            var msg = "订单:" + json.sucids + "审核成功;" + json.msg;
                                                            $.messager.alert("提醒", msg);
                                                        } else if (json.unsucids != "") {
                                                            $.messager.alert("提醒", "订单:" + json.unsucids + "审核失败<br/>" + json.msg);
                                                        }
                                                        $("#sales-datagrid-orderGoodsListPage").datagrid('reload');
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
                                                loading("超级审核中...");
                                                $.ajax({
                                                    url: 'sales/supplierAuditOrderGoodsMuti.do',
                                                    dataType: 'json',
                                                    type: 'post',
                                                    data: 'ids=' + ids + '&type=1&model=supper',
                                                    success: function (json) {
                                                        loaded();
                                                        if (json.sucids != "") {
                                                            var msg = "订单:" + json.sucids + "审核成功;" + json.msg;
                                                            $.messager.alert("提醒", msg);
                                                        } else if (json.unsucids != "") {
                                                            $.messager.alert("提醒", "订单:" + json.unsucids + "审核失败<br/>" + json.msg);
                                                        }
                                                        $("#sales-datagrid-orderGoodsListPage").datagrid('reload');
                                                    },
                                                    error: function () {
                                                        loaded();
                                                        $.messager.alert("错误", "审核出错");
                                                    }
                                                });
                                            }
                                        });
                                    }
                                },
                                error: function () {
                                    loaded();
                                    $.messager.alert("错误", "核对验证出错");
                                }
                            });
                        }
                    }
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        //查询针对的表
                        name: 't_sales_goodsorder',
                        plain: true,
                        //查询针对的表格id
                        datagrid: 'sales-datagrid-orderGoodsListPage'
                    }
                },
                {}
            ],
            buttons: [
                {},
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
                {}
            ],
            model: 'bill',
            type: 'list',
            tname: 't_sales_goodsorder'
        });
        var orderListJson = $("#sales-datagrid-orderGoodsListPage").createGridColumnLoad({
//            name: 't_sales_goodsorder',
            commonCol: [[
                {field: 'ck', checkbox: true},
                {field: 'id', title: '编号', width: 120, align: 'left', sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, align: 'left', sortable: true},
                {field: 'customerid', title: '客户编码', width: 60, align: 'left', sortable: true},
                {field: 'customername', title: '客户名称', width: 150, align: 'left', isShow: true},
                // {field: 'handlerid', title: '对方经手人', width: 80, align: 'left'},
                {field: 'salesdept', title: '销售部门', width: 100, align: 'left', sortable: true},
                {field: 'salesuser', title: '客户业务员', width: 80, align: 'left', sortable: true},
                {field: 'brandid', title: '品牌名称', width: 80, align: 'left', sortable: true,
                    formatter: function (value, row, index) {
                        return row.brandname;
                    }
                },
                {
                    field: 'field01', title: '金额', width: 80, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'field02', title: '未税金额', width: 80, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'field03', title: '税额', width: 80, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'status', title: '状态', width: 60, align: 'left', sortable: true,
                    formatter: function (value, row, index) {
                        return row.statusname;
                    }
                },
                // {
                //     field: 'orderunitnum', title: '已生成数量', width: 90, align: 'left', sortable: true,
                //     formatter: function (value, row, index) {
                //         return formatterBigNumNoLen(value);
                //     }
                // },
                // {
                //     field: 'notorderunitnum', title: '未生成数量', width: 90, align: 'left', sortable: true,
                //     formatter: function (value, row, index) {
                //         return formatterBigNumNoLen(value);
                //     }
                // },
                {
                    field: 'notordertaxamount', title: '未生成金额', width: 90, align: 'right', sortable: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'indooruserid', title: '销售内勤', width: 80, sortable: true,
                    formatter: function (value, rowData, index) {
                        return rowData.indoorusername;
                    }
                },
                {field: 'ladingbill', title: '提货券', width: 80, align: 'left', sortable: true},
                {
                    field: 'totalboxweight', title: '总重量(千克)', width: 80, hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'totalboxvolume', title: '总体积(立方米)', width: 100, hidden: true,
                    formatter: function (value, row, index) {
                        return Number(value).toFixed(3);
                    }
                },
                {field: 'addusername', title: '制单人', width: 60, sortable: true},
                {field: 'addtime', title: '制单时间', width: 130, sortable: true},
                {field: 'auditusername', title: '审核人', width: 80, sortable: true, hidden: true},
                {field: 'audittime', title: '审核时间', width: 80, sortable: true, hidden: true},
                {field: 'modifyusername', title: '修改人', width: 60, sortable: true, hidden: true},
                {field: 'modifytime', title: '修改时间', width: 130, sortable: true, hidden: true},
                {field: 'remark', title: '备注', width: 100},
                {field: 'printtimes', title: '打印次数', width: 60, hidden: true},
                {
                    field: 'printstate', title: '打印状态', width: 80, isShow: true,
                    formatter: function (value, rowData, index) {
                        if (rowData.printtimes > 0) {
                            return "已打印";
                        } else if (rowData.printtimes == -99) {
                            return "";
                        } else {
                            return "未打印";
                        }
                    }
                },
                {field: 'printdatetime', title: '打印时间', width: 130}
            ]]
        });

        var ListJson = JSON.stringify(orderListJson);
        $("#createGridColumnLoad-commonCol").val(ListJson);

        $("#sales-datagrid-orderGoodsListPage").datagrid({
            authority: orderListJson,
            frozenColumns: orderListJson.frozen,
            columns: orderListJson.common,
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            sortName: 'id',
            sortOrder: 'desc',
            url: 'sales/getOrderGoodsList.do',
            queryParams: initQueryJSON,
            toolbar: '#sales-queryDiv-orderGoodsListPage',
            onDblClickRow: function (index, data) {
                var flag = isLockData(data.id, "t_sales_goodsorder");
                if (!flag) {
                    $.messager.alert("警告", "该数据正在被其他人操作，暂不能修改！");
                    return false;
                }
                top.addOrUpdateTab('sales/orderGoodsPage.do?type=edit&id=' + data.id, '销售订货单查看');
            },
            onCheckAll:function(){
                getSelectBill();
            },
            onUncheckAll:function(){
                getSelectBill();
            },
            onCheck:function(){
                getSelectBill();
            },
            onUncheck:function(){
                getSelectBill();
            }
        }).datagrid("columnMoving");
    });

    $("#sales-phprintsign-orderGoodsListPage").change(function () {
        if ($(this).val() == "") {
            $("#sales-phprinttimes-orderGoodsListPage").val("");
        }
    });
    $("#sales-printsign-orderGoodsListPage").change(function () {
        if ($(this).val() == "") {
            $("#sales-printtimes-orderGoodsListPage").val("");
        }
    });

    function uploadHtml() {
        $("#salesorder-import-dialog").dialog({
            href: 'sales/salesModelPage.do',
            width: 400,
            height: 300,
            title: '模板文件上传',
            colsed: false,
            cache: false,
            modal: true
        });
    }
    function getSelectBill(){
        var rows = $("#sales-datagrid-orderGoodsListPage").datagrid('getChecked');
        var exportid="";
        for(var i=0;i<rows.length;i++){
            exportid+=","+rows[i].id;
        }
        $("#sales-exportid-orderGoodsListPage").val(exportid);
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
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

        <%--//销售订单打印--%>
        <%--AgReportPrint.init({--%>
            <%--id: "order-dialog-print",--%>
            <%--code: "sales_ordergoods",--%>
            <%--//tableId: "sales-datagrid-orderGoodsListPage",--%>
            <%--url_preview: "print/sales/salesOrderPrintView.do",--%>
            <%--url_print: "print/sales/salesOrderPrint.do",--%>
            <%--btnPreview: "button-printview-saleorder",--%>
            <%--btnPrint: "button-print-saleorder",--%>
            <%--printlimit: "${printlimit}",--%>
            <%--getData: function (tableId, printParam) {--%>
                <%--var data = $grid.datagrid('getChecked');--%>
                <%--if (data == null || data.length == 0) {--%>
                    <%--$.messager.alert("提醒", "请选择至少一条记录");--%>
                    <%--return false;--%>
                <%--}--%>
                <%--var idarray = [];--%>
                <%--var printIds = [];--%>
                <%--for (var i = 0; i < data.length; i++) {--%>
                    <%--idarray.push(data[i].id);--%>
                    <%--if (data[i].printtimes > 0) {--%>
                        <%--printIds.push(data[i].id);--%>
                    <%--}--%>
                <%--}--%>
                <%--printParam.saleidarrs = idarray.join(",");--%>
                <%--printParam.printIds = printIds;--%>
                <%--return true;--%>
            <%--},--%>
            <%--onPrintSuccess: onPrintSuccess--%>
        <%--});--%>
        //销售订单打印
        AgReportPrint.init({
            id: "order-dialog-print",
            code: "sales_ordergoods",
            //tableId: "sales-datagrid-orderListPage",
            url_preview: "print/sales/salesOrderGoodsPrintView.do",
            url_print: "print/sales/salesOrderGoodsPrint.do",
            btnPreview: "button-printview-salesOrderGoods",
            btnPrint: "button-print-salesOrderGoods",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var data = $grid.datagrid('getChecked');
                if (data == null || data.length == 0) {
                    $.messager.alert("提醒", "请选择至少一条记录");
                    return false;
                }
                var idarray = [];
                var printIds = [];
                for (var i = 0; i < data.length; i++) {
                    var ids='';
                    if (!(data[i].status == '3' || data[i].status == '4')) {
                        if(ids==''){
                            ids=data[i].id;
                        }else{
                            ids+=','+data[i].id
                        }

                    }
                    if(ids!=''){
                        if (!(status == '3' || status == '4')) {
                            $.messager.alert("提醒", "销售单"+ids + "不可打印");
                            return false;
                        }
                    }
                    idarray.push(data[i].id);
                    if (data[i].printtimes > 0) {
                        printIds.push(data[i].id);
                    }
                }
                printParam.saleidarrs = idarray.join(",");
                printParam.printIds = printIds;
                return true;
            },
            onPrintSuccess: onPrintSuccess
        });
    });

</script>
<%--打印结束 --%>
</body>
</html>
