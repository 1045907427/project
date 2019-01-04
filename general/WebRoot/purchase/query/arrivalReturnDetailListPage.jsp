<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>采购进货流水明细</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<style>
    .checkbox1 {
        float: left;
        height: 22px;
        line-height: 22px;
    }

    .divtext {
        height: 22px;
        line-height: 22px;
        float: left;
        display: block;
    }
</style>
<table id="purchase-table-arrivalReturnListPage"></table>
<div id="purchase-table-query-arrivalReturnListPage" style="padding:0px;height:auto">
    <div class="buttonBG">
        <security:authorize url="/purchase/query/arrivalReturnPrintView.do">
            <a href="javaScript:void(0);" id="purchase-buttons-arrivalReturnPreview" class="easyui-linkbutton"
               iconCls="button-preview" plain="true" title="打印预览">打印预览</a>
        </security:authorize>
        <security:authorize url="/purchase/query/arrivalReturnPrint.do">
            <a href="javaScript:void(0);" id="purchase-buttons-arrivalReturnPrint" class="easyui-linkbutton"
               iconCls="button-print" plain="true" title="打印">打印</a>
        </security:authorize>
        <security:authorize url="/purchase/query/arrivalReturnListExport.do">
            <a href="javaScript:void(0);" id="purchase-buttons-export-arrivalReturnListPage" class="easyui-linkbutton"
               iconCls="button-export" plain="true" title="导出">导出</a>
        </security:authorize>
        <span id="purchase-table-query-arrivalReturnListPage-advanced"></span>
        <security:authorize url="/purchase/query/arrivalReturnListModifyArrivalRemark.do">
            <a href="javaScript:void(0);" id="purchase-buttons-modifyArrivalRemark-arrivalReturnListPage"
               class="easyui-linkbutton" iconCls="button-edit" plain="true" title="修改">修改进货单备注</a>
        </security:authorize>
    </div>
    <form action="" id="purchase-form-arrivalReturnListPage" method="post">
        <table class="querytable">
            <tr>
                <td>业务日期:</td>
                <td><input type="text" name="businessdatestart" style="width:100px;" class="Wdate"
                           onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"/>
                    到 <input type="text" name="businessdateend" class="Wdate" style="width:100px;"
                             onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"/>
                </td>
                <td>单据编号:</td>
                <td><input type="text" name="id" style="width: 150px;"/></td>
            </tr>
            <tr>
                <td>供应商名称:</td>
                <td><input id="purchase-query-supplierid" type="text" name="supplierid" style="width:225px;"/></td>
                <td>商品分类:</td>
                <td><input type="text" id="purchase-query-goodssort" name="goodssort" style="width: 150px;"/></td>
                <td>品牌名称:</td>
                <td><input id="purchase-query-brandid" type="text" name="brandid"/></td>
            </tr>
            <tr>
                <td>商&nbsp;&nbsp;品:</td>
                <td><input id="purchase-query-goodsid" type="text" name="goodsid"/></td>
                <td colspan="2">
                    <input type="checkbox" class="checkbox1" id="purchase-query-invoice1" name="invoice1" value="1"
                           checked="checked"/>
                    <label class="divtext" for="purchase-query-invoice1">已核销</label>
                    <input type="checkbox" class="checkbox1" id="purchase-query-invoice0" name="invoice0" value="0"
                           checked="checked"/>
                    <label class="divtext" for="purchase-query-invoice0">未核销</label>
                    <input type="checkbox" class="checkbox1" id="purchase-query-billtype0" name="billtype0" value="0"
                           checked="checked"/>
                    <label class="divtext" for="purchase-query-billtype0">进货单</label>
                    <input type="checkbox" class="checkbox1" id="purchase-query-billtype1" name="billtype1" value="1"
                           checked="checked"/>
                    <label class="divtext" for="purchase-query-billtype1">退货单</label>
                </td>
                <td rowspan="3" colspan="2" class="tdbutton">
                    <a href="javaScript:void(0);" id="purchase-btn-queryArrivalReturnListPage" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="purchase-btn-reloadArrivalReturnListPage" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div style="display:none">
    <div id="purchase-arrivalReturnListPage-dialog">
        <form action="" method="post" id="purchase-detailform-arrivalReturnListPage">
            <table>
                <tr>
                    <td>编号:</td>
                    <td><input type="text" id="purchase-arrivalReturnListPage-dialog-id"
                               style="width:300px; border:1px solid #B3ADAB; background-color: #EBEBE4;"
                               readonly="readonly"/></td>
                </tr>
                <tr>
                    <td>商品编号:</td>
                    <td><input type="text" id="purchase-arrivalReturnListPage-dialog-goodsid"
                               style="width:300px; border:1px solid #B3ADAB; background-color: #EBEBE4;"
                               readonly="readonly"/></td>
                </tr>
                <tr>
                    <td>商品名称:</td>
                    <td><input type="text" id="purchase-arrivalReturnListPage-dialog-goodsname"
                               style="width:300px; border:1px solid #B3ADAB; background-color: #EBEBE4;"
                               readonly="readonly"/></td>
                </tr>
                <tr>
                    <td>备注:</td>
                    <td><input type="text" id="purchase-arrivalReturnListPage-dialog-remark" name="remark"
                               style="width:300px" class="easyui-validatebox" validType="maxByteLength[500]"/></td>
                </tr>
            </table>
            <input type="hidden" id="purchase-arrivalReturnListPage-dialog-detailid"/>
        </form>
        <div id="purchase-arrivalReturnListPage-dialog-toolbar" class="buttonDetailBG">
            快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span
                style="font-weight: bold;color: red;">+</span><input type="button" value="继续修改" name="savegoon"
                                                                     id="purchase-arrivalReturnListPage-dialog-addSaveGoOn"/>
            <input type="button" value="保 存" name="savenogo" id="purchase-arrivalReturnListPage-dialog-addSave"/>
        </div>
    </div>
</div>
<script type="text/javascript">
    var detailDlgOpen = false;
    var editIndex;
    $(document).ready(function () {
        var initQueryJSON = $("#purchase-form-arrivalReturnListPage").serializeJSON();
        var arrivalOrderListJson = $("#purchase-table-arrivalReturnListPage").createGridColumnLoad({
            frozenCol: [[]],
            commonCol: [[
                {field: 'id', title: '编号', width: 120},
                {field: 'billtypename', title: '类型', width: 60},
                {field: 'businessdate', title: '业务日期', width: 100},
                {field: 'supplierid', title: '供应商编码', width: 80},
                {field: 'suppliername', title: '供应商名称', width: 200},
                {field: 'goodsid', title: '商品编码', width: 60},
                {field: 'goodsname', title: '商品名称', width: 130},
                {field: 'barcode', title: '条形码', width: 90},
                {field: 'brandname', title: '品牌名称', width: 60},
                {field: 'model', title: '规格参数', width: 60, hidden: true},
                {field: 'unitname', title: '单位', width: 40},
                {field: 'auxunitname', title: '辅单位', width: 50, hidden: true},
                {field: 'auxnumdetail', title: '辅数量', resizable: true, align: 'right'},
                {
                    field: 'unitnum', title: '数量', width: 80, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {
                    field: 'taxprice', title: '单价', width: 60, align: 'right',
                    formatter: function (value, row, index) {
                        if (row.businessdate == null || row.businessdate.indexOf("小计") < 0) {
                            return formatterMoney(value);
                        }
                    }
                },
                {
                    field: 'taxamount', title: '金额', resizable: true, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'notaxamount', title: '未税金额', resizable: true, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {field: 'taxtypename', title: '税种', width: 60},
                {
                    field: 'tax', title: '税额', resizable: true, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {field: 'iswriteoffname', title: '状态', width: 50},
                {field: 'remark', title: '备注', width: 120}
            ]]
        });
        $("#purchase-table-arrivalReturnListPage").datagrid({
            authority: arrivalOrderListJson,
            frozenColumns: arrivalOrderListJson.frozen,
            columns: arrivalOrderListJson.common,
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            //pageSize:100,
            singleSelect: true,
            showFooter: true,
            toolbar: '#purchase-table-query-arrivalReturnListPage',
            url: "purchase/query/showArrivalReturnDetailList.do",
            queryParams: initQueryJSON,
            onDblClickRow: function (index, data) {
                if (data.billtype == '0') {
                    top.addOrUpdateTab('/purchase/arrivalorder/arrivalOrderPage.do?type=view&id=' + data.id, "采购进货单");
                } else if (data.billtype == '1') {
                    top.addOrUpdateTab('/purchase/returnorder/returnOrderPage.do?type=view&id=' + data.id, "采购退货通知单");
                }
            },
            onSelect: function (rowIndex, rowData) {
                editIndex = rowIndex;
            }
        }).datagrid("columnMoving");
        $("#purchase-query-supplierid").supplierWidget();
        $("#purchase-query-brandid").widget({
            referwid: 'RL_T_BASE_GOODS_BRAND',
            width: 135,
            singleSelect: true
        });
        $("#purchase-query-goodssort").widget({
            referwid: 'RL_T_BASE_GOODS_WARESCLASS',
            width: 150,
            onlyLeafCheck: false,
            singleSelect: false
        });
        $("#purchase-query-goodsid").widget({
            referwid: 'RL_T_BASE_GOODS_INFO',
            width: 225,
            onlyLeafCheck: false,
            singleSelect: false
        });
        $("#purchase-btn-queryArrivalReturnListPage").click(function () {
            //判断复选框是否有选中
            var flag = false;
            $(".checkbox1").each(function () {
                if ($(this)[0].checked) {
                    flag = true;
                }
            });
            if (!flag) {
                $.messager.alert('提醒', '请勾选要查询的状态！');
                return false;
            }
            var queryJSON = $("#purchase-form-arrivalReturnListPage").serializeJSON();
            $('#purchase-table-arrivalReturnListPage').datagrid('load', queryJSON);
        });
        $("#purchase-btn-reloadArrivalReturnListPage").click(function () {
            $("#purchase-query-supplierid").supplierWidget("clear");
            $("#purchase-query-brandid").widget("clear");
            $("#purchase-query-goodssort").widget('clear');
            $("#purchase-form-arrivalReturnListPage")[0].reset();
            var queryJSON = $("#purchase-form-arrivalReturnListPage").serializeJSON();
            $('#purchase-table-arrivalReturnListPage').datagrid('load', queryJSON);
        });

        $("#purchase-buttons-export-arrivalReturnListPage").Excel('export', {
            queryForm: "#purchase-form-arrivalReturnListPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            type: 'exportUserdefined',
            name: '采购进货流水明细表',
            url: 'purchase/query/exportArrivalReturnListData.do'
        });

        $(document).keydown(function (event) {
            switch (event.keyCode) {
                case 13:
                    event.preventDefault();
                    if (detailDlgOpen) {
                        if (event.ctrlKey) {
                            $("#purchase-arrivalReturnListPage-dialog-addSaveGoOn").trigger("click");
                        }
                    }
                    break;
                case 107: //+
                    if (detailDlgOpen) {
                        $("#purchase-arrivalReturnListPage-dialog-addSaveGoOn").trigger("click");
                    }
                    break;
            }
        });
        <security:authorize url="/purchase/query/arrivalReturnListModifyArrivalRemark.do">
        $("#purchase-buttons-modifyArrivalRemark-arrivalReturnListPage").click(function () {
            var datarow = $("#purchase-table-arrivalReturnListPage").datagrid('getSelected');
            showArrivalReturnDetailDialog(datarow);
        });
        </security:authorize>

        $("#purchase-arrivalReturnListPage-dialog-addSaveGoOn").bind("click", function () {
            if (detailDlgOpen) {
                saveArrivalReturnHandle(true);
            }
        });
        $("#purchase-arrivalReturnListPage-dialog-addSave").bind("click", function () {
            if (detailDlgOpen) {
                $.messager.confirm("提醒", "确定修改进货单备注信息？", function (r) {
                    if (r) {
                        saveArrivalReturnHandle(false);
                    }
                });
            }
        });
    });

    function showArrivalReturnDetailDialog(rowData) {
        if (rowData.billtype != '0') {
            $.messager.alert("提醒", '抱歉，目前只提供进货单修改');
            return false;
        }
        var $arrivalReturnDialogOper = $("#purchase-arrivalReturnListPage-dialog");
        $arrivalReturnDialogOper.dialog({
            title: '修改进货单备注',
            //fit:true,
            width: 500,
            height: 250,
            closed: true,
            cache: false,
            modal: true,
            maximizable: true,
            resizable: true,
            buttons: '#purchase-arrivalReturnListPage-dialog-toolbar',
            onBeforeOpen: function () {
                $("#purchase-detailform-arrivalReturnListPage").form("reset");
                $("#purchase-arrivalReturnListPage-dialog-id").val("");
                $("#purchase-arrivalReturnListPage-dialog-goodsid").val("");
                $("#purchase-arrivalReturnListPage-dialog-goodsname").val("");
                $("#purchase-arrivalReturnListPage-dialog-remark").val("");
                $("#purchase-arrivalReturnListPage-dialog-detailid").val("");

                $("#purchase-arrivalReturnListPage-dialog-id").val(rowData.id);
                $("#purchase-arrivalReturnListPage-dialog-goodsid").val(rowData.goodsid);
                $("#purchase-arrivalReturnListPage-dialog-goodsname").val(rowData.goodsname);
                $("#purchase-arrivalReturnListPage-dialog-remark").val(rowData.remark);
                $("#purchase-arrivalReturnListPage-dialog-detailid").val(rowData.detailid);
            },
            onOpen: function () {
                detailDlgOpen = true;
                $("#purchase-arrivalReturnListPage-dialog-remark").focus();
            },
            onClose: function () {
                detailDlgOpen = false;
            }
        });
        $arrivalReturnDialogOper.dialog("open");
    }

    function saveArrivalReturnHandle(addon) {
        if (addon) {
            addon = true;
        } else {
            addon = false;
        }
//	  	  	var flag = $(this).form('validate');
//            console.log(flag);
//	    	if(flag==false){
//	    		return false;
//	    	}
        var detailid = $("#purchase-arrivalReturnListPage-dialog-detailid").val();
        if (null == detailid || "" == detailid) {
            $.messager.alert("提醒", "抱歉，未能找到明细编号");
            return false;
        }

        var data = {};

        data.detailid = detailid;
        data.orderid = $("#purchase-arrivalReturnListPage-dialog-id").val() || "";
        data.remark = $("#purchase-arrivalReturnListPage-dialog-remark").val() || "";

        loading("保存中..");
        try {
            $.ajax({
                url: 'purchase/arrivalorder/updateArrivalOrderDetailRemark.do',
                data: data,
                type: 'POST',
                dataType: 'json',
                success: function (json) {
                    loaded();
                    if (json.flag) {
                        $.messager.alert("提醒", "修改备注信息成功！");
                        if (undefined != editIndex) {
                            $("#purchase-table-arrivalReturnListPage").datagrid('updateRow', {
                                index: editIndex,
                                row: {
                                    remark: data.remark
                                }
                            });
                        } else {
                            $("#purchase-table-arrivalReturnListPage").datagrid('reload');
                        }
                        $('#purchase-arrivalReturnListPage-dialog').dialog("close");
                        if (addon) {
                            if (undefined != editIndex) {
                                var index = editIndex + 1;
                                var dataRows = $("#purchase-table-arrivalReturnListPage").datagrid('getRows');

                                var rowData = undefined;
                                var rowlen = dataRows.length;
                                while (index < rowlen) {
                                    rowData = dataRows[index];
                                    if (rowData.billtype == '0') {
                                        break;
                                    } else {
                                        rowData = undefined;
                                    }
                                    index = index + 1;
                                }
                                if (rowData != undefined) {
                                    showArrivalReturnDetailDialog(rowData);
                                    editIndex = index;
                                    $("#purchase-table-arrivalReturnListPage").datagrid('selectRow', index);
                                }
                            }
                        }
                        return false;
                    } else {
                        if (json.msg) {
                            $.messager.alert("提醒", json.msg);
                        } else {
                            $.messager.alert("提醒", "修改备注信息失败！");
                        }
                        return false;
                    }
                }
            });
        } catch (e) {
            loaded();
        }
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "arrivalReturnListPage-dialog-print",
            code: "purchase_journalaccout",
            queryForm: "purchase-form-arrivalReturnListPage",
            url_preview: "print/purchase/purchaseJournalAccountPrintView.do",
            url_print: "print/purchase/purchaseJournalAccountPrint.do",
            btnPreview: "purchase-buttons-arrivalReturnPreview",
            btnPrint: "purchase-buttons-arrivalReturnPrint",
            getData: function (tableId, printParam) {
                var businessdate1 = $("#storage-query-businessdatestart").val();
                var businessdate2 = $("#storage-query-businessdateend").val();
                if (businessdate1 == "" || businessdate2 == "") {
                    $.messager.alert("提醒", '请输入业务日期时间段以便打印');
                    return false;
                }
                var $invoice = $("#purchase-query-invoice1");
                var $uninvoice = $("#purchase-query-invoice0");
                var $arrivalBill = $("#purchase-query-billtype0");
                var $returnBill = $("#purchase-query-billtype1");
                if (!$arrivalBill.prop("checked") && !$returnBill.prop("checked")) {
                    $.messager.alert("提醒", '请选择你要打印的单据');
                    return false;
                }
                if (!$invoice.prop("checked") || $uninvoice.prop("checked")) {
                    $.messager.alert("提醒", '已核销的才能打印预览，请不要勾选“未核销”查询条件');
                    return false;
                }
                return true;
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
