<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>采购</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="purchase-buttons-returnOrderListPage" style="float: left"></div>
        <div style="float: left;position: relative">
            <a href="javaScript:void(0);" id="purchase-exportDetail-returnOrderListPage" class="easyui-linkbutton"
               iconCls="button-export" plain="true">明细导出</a>
        </div>
    </div>
    <div data-options="region:'center'">
        <table id="purchase-table-returnOrderListPage"></table>
        <div id="purchase-table-query-returnOrderListPage" style="padding:2px;height:auto">
            <div>
                <form action="" id="purchase-form-returnOrderListPage" method="post">
                    <table class="querytable">
                        <tr>
                            <td>业务日期:</td>
                            <td><input type="text" name="businessdatestart" style="width:100px;" class="Wdate"
                                       onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                                到 <input type="text" name="businessdateend" class="Wdate" style="width:100px;"
                                         onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                            </td>
                            <td>退货仓库:</td>
                            <td><input type="text" name="storageid" id="purchase-returnOrderListPage-storageid"/></td>
                            <td>状 态:</td>
                            <td>
                                <select name="status" style="width:150px;">
                                    <option></option>
                                    <option value="2" selected="selected">保存</option>
                                    <option value="3">审核通过</option>
                                    <option value="4">关闭</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>供应商:</td>
                            <td><input id="purchase-returnOrderListPage-supplier" type="text" name="supplierid"
                                       style="width: 225px;"/></td>
                            <td>出库状态:</td>
                            <td>
                                <select name="ckstatus" style="width:150px;">
                                    <option></option>
                                    <option value="1">出库</option>
                                    <option value="0">未出库</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>编&nbsp;号:</td>
                            <td><input type="text" name="id" style="width: 225px;"/></td>
                            <td>打印状态:</td>
                            <td>
                                <select name="printsign" style="width:150px;">
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
                                <input type="hidden" name="queryprinttimes" value="0" style="width:80px;"/>
                            </td>
                            <td rowspan="3" colspan="2" class="tdbutton">
                                <a href="javaScript:void(0);" id="purchase-btn-queryReturnOrderListPage"
                                   class="button-qr">查询</a>
                                <a href="javaScript:void(0);" id="purchase-btn-reloadReturnOrderListPage"
                                   class="button-qr">重置</a>
                                <span id="purchase-table-query-returnOrderListPage-advanced"></span>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>
<div id="returnOrder-import-dialog"></div>
<script type="text/javascript">
    var SR_footerobject = null;
    $(document).ready(function () {
        var initQueryJSON = $("#purchase-form-returnOrderListPage").serializeJSON();
        var returnOrderListJson = $("#purchase-table-returnOrderListPage").createGridColumnLoad({
            name: 'purchase_returnorder',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编号', width: 120, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 70, sortable: true},
                {field: 'supplierid', title: '供应商编码', width: 70},
                {field: 'suppliername', title: '供应商名称', width: 100, isShow: true},
                {
                    field: 'handlerid', title: '对方联系人', width: 100,
                    formatter: function (value, row, index) {
                        return row.handlername;
                    }
                },
                {
                    field: 'buydeptid', title: '采购部门', width: 80,
                    formatter: function (value, row, index) {
                        return row.buydeptname;
                    }
                },
                {
                    field: 'field01', title: '金额', width: 60,//align:'right',
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
                {field: 'addusername', title: '制单人', width: 60},
                {field: 'adddeptname', title: '制单人部门', width: 100, hidden: true},
                {field: 'modifyusername', title: '修改人', width: 120, hidden: true},
                {field: 'auditusername', title: '审核人', width: 60},
                {field: 'audittime', title: '审核时间', width: 100, hidden: true, sortable: true},
                {
                    field: 'status', title: '状态', width: 60,
                    formatter: function (value, row, index) {
                        return getSysCodeName('status', value);
                    }
                },
                {
                    field: 'isinvoice', title: '发票状态', width: 70,
                    formatter: function (value, row, index) {
                        if (value == null || value == "" || value == "0" || value == "4") {
                            return "未开票";
                        } else if (value == "1") {
                            return "已开票";
                        } else if (value == "2") {
                            return "核销";
                        } else if (value == "3") {
                            return "开票中";
                        } else if (value == "-999") {
                            return "";
                        }
                    }
                },
                {
                    field: 'ckstatus', title: '出库状态', width: 60,
                    formatter: function (value, row, index) {
                        if (value == "1") {
                            return "出库";
                        } else if (value == "0") {
                            return "未出库";
                        }
                    }
                },
                {
                    field: 'ischeck', title: '验收状态', width: 60,
                    formatter: function (value, row, index) {
                        if (value == "1") {
                            return "已验收";
                        } else if (value == "0") {
                            return "未验收";
                        }
                    }
                },
                {field: 'checkdate', title: '验收日期', width: 70},
                {field: 'remark', title: '备注', width: 180},
                {field: 'addtime', title: '制单时间', width: 120, sortable: true},
                {
                    field: 'printstate', title: '打印状态', width: 80, isShow: true,
                    formatter: function (value, rowData, index) {
                        if (rowData.printtimes > 0) {
                            return "已打印";
                        } else if (rowData.printtimes == null) {
                            return "";
                        } else {
                            return "未打印";
                        }
                    }
                },
                {
                    field: 'printtimes', title: '打印次数', width: 80,
                    formatter: function (value, rowData, index) {
                        if (value == null) {
                            return "";
                        } else {
                            return value;
                        }
                    }
                },
                {field: 'checkusername', title: '验收人', width: 60, hidden: true}

            ]]
        });
        $("#purchase-table-returnOrderListPage").datagrid({
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            showFooter: true,
            toolbar: '#purchase-table-query-returnOrderListPage',
            url: "purchase/returnorder/showReturnOrderPageList.do",
            queryParams: initQueryJSON,
            authority: returnOrderListJson,
            frozenColumns: returnOrderListJson.frozen,
            columns: returnOrderListJson.common,
            onLoadSuccess: function () {
                var footerrows = $(this).datagrid('getFooterRows');
                if (null != footerrows && footerrows.length > 0) {
                    SR_footerobject = footerrows[0];
                    countTotalAmount();
                }
            },
            onDblClickRow: function (index, data) {
                top.addOrUpdateTab('purchase/returnorder/returnOrderPage.do?type=edit&id=' + data.id, "采购退货通知单查看");
            },
            onCheckAll: function () {
                countTotalAmount();
            },
            onUncheckAll: function () {
                countTotalAmount();
            },
            onCheck: function () {
                countTotalAmount();
            },
            onUncheck: function () {
                countTotalAmount();
            }
        }).datagrid("columnMoving");

        //按钮
        $("#purchase-buttons-returnOrderListPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/purchase/returnorder/returnOrderAddBtn.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addOrUpdateTab('purchase/returnorder/returnOrderPage.do', '采购退货通知单新增');
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/returnorder/returnOrderEditBtn.do">
                {
                    type: 'button-edit',
                    handler: function () {
                        var datarow = $("#purchase-table-returnOrderListPage").datagrid('getSelected');
                        if (datarow == null || datarow.id == null) {
                            $.messager.alert("提醒", "请选择要修改的采购退货通知单");
                            return false;
                        }
                        top.addOrUpdateTab('purchase/returnorder/returnOrderPage.do?type=edit&id=' + datarow.id, '采购退货通知单修改');
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/returnorder/returnOrderViewBtn.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var datarow = $("#purchase-table-returnOrderListPage").datagrid('getSelected');
                        if (datarow == null || datarow.id == null) {
                            $.messager.alert("提醒", "请选择要查看的采购退货通知单");
                            return false;
                        }
                        top.addOrUpdateTab('purchase/returnorder/returnOrderPage.do?type=edit&id=' + datarow.id, '采购退货通知单查看');
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/returnorder/returnOrderImport.do">
                {
                    type: 'button-import',
                    attr: {
                        url: 'purchase/returnorder/importReturnOrder.do',
                        type: 'importUserdefined',
                        importparam: '采购退货通知单导入。供应商编码、退货仓库、商品编码、数量必填，业务日期选填，若数量为空，则默认为0。',
                        onClose: function () { //导入成功后窗口关闭时操作，
                            $("#purchase-table-returnOrderListPage").datagrid('reload');
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/returnorder/returnOrderExport.do">
                {
                    type: 'button-export',
                    attr: {
                        datagrid: '#purchase-table-returnOrderListPage',
                        queryForm: "#purchase-form-returnOrderListPage",
                        type: 'exportUserdefined',
                        name: '采购退货通知单表',
                        url: 'purchase/returnorder/exportReturnOrderListData.do'
                    }
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        name: 'purchase_returnorder',
                        plain: true,
                        datagrid: 'purchase-table-returnOrderListPage'
                    }
                },
                {}
            ],
            buttons: [
                <security:authorize url="/purchase/returnorder/returnOrderPrintViewBtn.do">
                {
                    id: 'button-printview-returnorder',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/returnorder/returnOrderPrintBtn.do">
                {
                    id: 'button-print-returnorder',
                    name: '打印',
                    iconCls: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/returnOrderModelImport.do">
                {
                    id: 'button-import-return',
                    name: '模板导入',
                    iconCls: 'button-import',
                    handler: function () {
                        $("#returnOrder-import-dialog").dialog({
                            href: 'purchase/returnorder/showReturnModelPage.do',
                            width: 400,
                            height: 250,
                            title: '模板文件上传',
                            colsed: false,
                            cache: false,
                            modal: true
                        });

                    }
                },
                </security:authorize>
                {}
            ],
            model: 'bill',
            type: 'list',
            datagrid: 'purchase-table-returnOrderListPage',
            tname: 't_purchase_returnorder'
        });

        //仓库控件
        $("#purchase-returnOrderListPage-storageid").widget({
            width: 150,
            referwid: 'RL_T_BASE_STORAGE_INFO',
            singleSelect: true,
            onlyLeafCheck: false
        });

// 			$("#purchase-table-query-returnOrderListPage-advanced").advancedQuery({
//		 		name:'purchase_returnorder',
//		 		plain:true,
//		 		datagrid:'purchase-table-returnOrderListPage'
//			});

        //回车事件
        controlQueryAndResetByKey("purchase-btn-queryReturnOrderListPage", "purchase-btn-reloadReturnOrderListPage");

        $("#purchase-returnOrderListPage-supplier").supplierWidget({
            name: 't_purchase_returnorder',
            col: 'supplierid',
            singleSelect: true,
            onlyLeafCheck: true,
            onSelect: function (data) {
            }
        });

        $("#purchase-btn-queryReturnOrderListPage").click(function () {
            //查询参数直接添加在url中
            var queryJSON = $("#purchase-form-returnOrderListPage").serializeJSON();
            $('#purchase-table-returnOrderListPage').datagrid('load', queryJSON);
        });
        $("#purchase-btn-reloadReturnOrderListPage").click(function () {
            $("#purchase-returnOrderListPage-storageid").widget('clear');
            $("#purchase-returnOrderListPage-supplier").supplierWidget('clear');
            $("#purchase-form-returnOrderListPage")[0].reset();
            var queryJSON = $("#purchase-form-returnOrderListPage").serializeJSON();
            $('#purchase-table-returnOrderListPage').datagrid('load', queryJSON);
        });

    });
    function countTotalAmount() {
        var rows = $("#purchase-table-returnOrderListPage").datagrid('getChecked');
        /*if(null==rows || rows.length==0){
         var foot=[];
         if(null!=SR_footerobject){
         foot.push(SR_footerobject);
         }
         $("#purchase-table-returnOrderListPage").datagrid("reloadFooter",foot);
         return false;
         }*/
        var taxamount = 0;
        var notaxamount = 0;
        var tax = 0;
        for (var i = 0; i < rows.length; i++) {
            taxamount = Number(taxamount) + Number(rows[i].field01 == undefined ? 0 : rows[i].field01);
            notaxamount = Number(notaxamount) + Number(rows[i].field02 == undefined ? 0 : rows[i].field02);
            tax = Number(tax) + Number(rows[i].field03 == undefined ? 0 : rows[i].field03);
        }
        var obj = {id: '选中金额', field01: taxamount, field02: notaxamount, field03: tax, source: " ", isinvoice: " "};
        var foot = [];
        foot.push(obj);
        if (null != SR_footerobject) {
            foot.push(SR_footerobject);
        }
        $("#purchase-table-returnOrderListPage").datagrid("reloadFooter", foot);
    }
    $("#purchase-exportDetail-returnOrderListPage").Excel('export', {
        datagrid: '#purchase-table-returnOrderListPage',
        queryForm: "#purchase-form-returnOrderListPage",
        type: 'exportUserdefined',
        name: '采购退货通知单',
        url: 'purchase/returnorder/exportReturnOrderListDetail.do'
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-returnOrder-dialog-print",
            code: "purchase_returnorder",
            tableId: "purchase-table-returnOrderListPage",
            url_preview: "print/purchase/returnOrderPrintView.do",
            url_print: "print/purchase/returnOrderPrint.do",
            btnPreview: "button-printview-returnorder",
            btnPrint: "button-print-returnorder",
            printlimit: "${printlimit}"
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
