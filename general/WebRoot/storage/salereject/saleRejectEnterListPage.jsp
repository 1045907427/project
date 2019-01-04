<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售退货入库单列表页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-saleRejectEnterPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center'">
        <table id="storage-datagrid-saleRejectEnterPage" data-options="border:false"></table>
    </div>
</div>
<div id="storage-datagrid-toolbar-saleRejectEnterPage">
    <form action="" id="storage-form-query-saleRejectEnterPage" method="post">
        <table class="querytable">
            <tr>
                <%--<select class="len136" name="storageid" id="storage-query-storageid"style="width:150px;" >--%>
                <%--<option></option>--%>
                <%--<c:forEach items="${storageList}" var="list">--%>
                <%--<option value="${list.id }">${list.name }</option>--%>
                <%--</c:forEach>--%>
                <%--</select>--%>
                <td>客&nbsp;&nbsp;户:</td>
                <td><input id="storage-query-customerid" type="text" name="customerid" style="width: 225px"/></td>
                <td>入库仓库:</td>
                <td>
                    <input type="text" name="storageid" id="storage-query-storageid"/>
                </td>
                <td>编号:</td>
                <td><input type="text" name="id" style="width:150px;"/></td>
                <td colspan="2"></td>
            </tr>
            <tr>
                <td>业务日期:</td>
                <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate"
                           onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"/>
                    到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;"
                             onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
                <td>退货类型:</td>
                <td>
                    <select name="sourcetype" style="width:150px;">
                        <option></option>
                        <option value="2">直退退货</option>
                        <option value="1">售后退货</option>
                    </select>
                </td>
                <td>状态:</td>
                <td>
                    <select name="status" style="width:150px;">
                        <option></option>
                        <option value="2" selected="selected">保存</option>
                        <option value="3">审核通过</option>
                        <option value="4">关闭</option>
                    </select>
                </td>
                <td colspan="2">
                </td>
            </tr>
            <tr>
                <td>退货通知单:</td>
                <td>
                    <input type="text" name="sourceid" style="width: 225px;"/>
                </td>
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
                    <input type="hidden" name="queryprinttimes" value="0" style="width:60px;"/>
                </td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="storage-queay-saleRejectEnter" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="storage-reload-saleRejectEnter" class="button-qr">重置</a>
                    <span id="storage-query-advanced-saleRejectEnter"></span>
                </td>
            </tr>
        </table>
    </form>
</div>
<div style="display:none">
    <%--通用 --%>
    <div id="listPage-Order-dialog-print">
        <form action="" id="listPage-Order-dialog-print-form" method="post">
            <table>
                <tr>
                    <td>打印模板：</td>
                    <td>
                        <select id="listPage-Order-dialog-print-templet" name="templetid">
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#storage-form-query-saleRejectEnterPage").serializeJSON();
    var footerobject = null;
    function confirmStorageWidget() {
        $("#storage-hidden-storager").widget({
            referwid: 'RL_T_BASE_PERSONNEL_STORAGER',
            width: 160,
            col: 'name',
            singleSelect: true,
            initValue: '${storager}',
        });
    }

    $(function () {
        //按钮
        $("#storage-buttons-saleRejectEnterPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/saleRejectEnterAddPage.do">
                <!--					{-->
                <!--						type: 'button-add',-->
                <!--						handler: function(){-->
                <!--							top.addTab('storage/showSaleRejectEnterAddPage.do', "销售退货入库单新增");-->
                <!--						}-->
                <!--					},-->
                </security:authorize>
                <security:authorize url="/storage/saleRejectEnterEditPage.do">
                <!--					{-->
                <!--						type: 'button-edit',-->
                <!--						handler: function(){-->
                <!--							var con = $("#storage-datagrid-saleRejectEnterPage").datagrid('getSelected');-->
                <!--							if(con == null){-->
                <!--								$.messager.alert("提醒","请选择一条记录");-->
                <!--								return false;-->
                <!--							}	-->
                <!--							top.addTab('storage/showSaleRejectEnterEditPage.do?id='+ con.id, "销售退货入库单修改");-->
                <!--						}-->
                <!--					},-->
                </security:authorize>
                <security:authorize url="/storage/saleRejectEnterViewPage.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var con = $("#storage-datagrid-saleRejectEnterPage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        top.addOrUpdateTab('storage/showSaleRejectEnterEditPage.do?id=' + con.id, "销售退货入库单查看");
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/saleRejectEnterPrintView.do">
                {
                    type: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/saleRejectEnterPrint.do">
                {
                    type: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        //查询针对的表
                        name: 't_storage_salereject_enter',
                        //查询针对的表格id
                        datagrid: 'storage-datagrid-saleRejectEnterPage',
                        plain: true
                    }
                },
                {}
            ],
            buttons: [
                <security:authorize url="/storage/editSalesRejectEnterStorager.do">
                {
                    id: 'button-mulitAudit',
                    name: '批量修改收货人',
                    iconCls: 'button-audit',
                    handler: function () {
                        var fn = function (r) {
                            if (r) {
                                var rows = $("#storage-datagrid-saleRejectEnterPage").datagrid('getChecked');
                                if (rows.length == 0) {
                                    $.messager.alert("提醒", "请选中需要修改的记录。");
                                    return false;
                                }
                                var ids = "";
                                for (var i = 0; i < rows.length; i++) {
                                    ids += rows[i].id + ',';
                                }
                                var storager = $("#storage-hidden-storager").widget("getValue");
                                loading("修改中..");
                                $.ajax({
                                    url: 'storage/editSalesRejectEnterStorager.do',
                                    dataType: 'json',
                                    type: 'post',
                                    data: {ids: ids, storager: storager},
                                    success: function (json) {
                                        loaded();
                                        if (json.flag == true) {
                                            $.messager.alert("提醒", "修改成功：" + json.success + "&nbsp;修改失败：" + json.failure + "<br/>" + json.msg);
                                            $("#storage-datagrid-saleRejectEnterPage").datagrid('reload');
                                        }
                                        else {
                                            $.messager.alert("提醒", "修改出错");
                                        }
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "修改出错");
                                    }
                                });

                            }
                        };
                        $.messager.confirm({
                            title: '批量修改收货人',
                            onOpen: function () {
                                confirmStorageWidget()
                            },
                            msg: "是否修改所选中单据的收货人？</br><span style=\"float: left;\">选择收货人:</span><input  id=\"storage-hidden-storager\" name=\"storager\"/>",
                            fn: fn
                        });
                    }
                },
                </security:authorize>
                {}
            ],
            model: 'bill',
            type: 'list',
            tname: 't_storage_salereject_enter'
        });
        var tableColumnListJson = $("#storage-datagrid-saleRejectEnterPage").createGridColumnLoad({
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编号', width: 130, sortable: true},
                {field: 'sourceid', title: '退货通知单编号', width: 130, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
                {
                    field: 'storageid', title: '入库仓库', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.storagename;
                    }
                },
                {field: 'customerid', title: '客户编码', width: 60, sortable: true},
                {field: 'customername', title: '客户名称', width: 150, isShow: true},
                {
                    field: 'salesdept', title: '销售部门', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.salesdeptname;
                    }
                },
                {
                    field: 'salesuser', title: '客户业务员', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.saleusername;
                    }
                },
                {
                    field: 'driverid', title: '司机', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.drivername;
                    }
                },
                {
                    field: 'sourcetype', title: '退货类型', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (value == "2") {
                            return "直退退货";
                        } else if (value == "1") {
                            return "售后退货";
                        }
                    }
                },
                {
                    field: 'totalamount', title: '退货金额', width: 80, align: 'right', isShow: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {field: 'checkdate', title: '验收日期', width: 80, sortable: true},
                {
                    field: 'status', title: '状态', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return getSysCodeName("status", value);
                    }
                },
                {
                    field: 'indooruserid', title: '销售内勤', width: 60, sortable: true,
                    formatter: function (value, rowData, index) {
                        return rowData.indoorusername;
                    }
                },
                {field: 'vouchertimes', title: '生成凭证次数', align: 'center', width:80},
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
                {
                    field: 'printtimes', title: '打印次数', width: 60, align: 'center',
                    formatter: function (value, rowData, index) {
                        if (value == -99) {
                            return "";
                        } else {
                            return value;
                        }
                    }
                },
                {field: 'duefromdate', title: '应收日期', width: 80, align: 'left', sortable: true, hidden: true},
                {field: 'addusername', title: '制单人', width: 80, sortable: true},
                {field: 'addtime', title: '制单时间', width: 120, sortable: true},
                {field: 'auditusername', title: '审核人', width: 80, sortable: true, hidden: true},
                {field: 'audittime', title: '审核时间', width: 80, sortable: true, hidden: true},
                {field: 'stopusername', title: '中止人', width: 80, hidden: true},
                {field: 'stoptime', title: '中止时间', width: 80, hidden: true, sortable: true},
                {field: 'remark', title: '备注', width: 80, sortable: true},
                {field: 'storagername', title: '收货人', width: 80, sortable: true, hidden: true},
            ]]
        });
        $("#storage-datagrid-saleRejectEnterPage").datagrid({
            authority: tableColumnListJson,
            frozenColumns: tableColumnListJson.frozen,
            columns: tableColumnListJson.common,
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            sortName: 'id',
            sortOrder: 'desc',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            showFooter: true,
            url: 'storage/showSaleRejectEnterList.do',
            queryParams: initQueryJSON,
            toolbar: '#storage-datagrid-toolbar-saleRejectEnterPage',
            onDblClickRow: function (rowIndex, rowData) {
                top.addOrUpdateTab('storage/showSaleRejectEnterEditPage.do?id=' + rowData.id, "销售退货入库单查看");
            },
            onCheck: function (rowIndex, rowData) {
                countTotalAmount();
            },
            onUncheck: function (rowIndex, rowData) {
                countTotalAmount();
            },
            onCheckAll: function (rows) {
                countTotalAmount();
            },
            onUncheckAll: function (rows) {
                countTotalAmount();
            },
            onLoadSuccess: function () {
                var footerrows = $(this).datagrid('getFooterRows');
                if (null != footerrows && footerrows.length > 0) {
                    footerobject = footerrows[0];
                    if (footerobject != null) {
                        footerobject.printtimes = -99;
                    }
                    countTotalAmount();
                }
            }
        }).datagrid("columnMoving");
        //入库仓库
        $("#storage-query-storageid").widget({
            width: 150,
            referwid: 'RL_T_BASE_STORAGE_INFO',
            singleSelect: true,
            onlyLeafCheck: false
        });
        $("#storage-query-customerid").customerWidget({
            name: 't_storage_salereject_enter',
            col: 'customerid',
            singleSelect: true,
            isdatasql: false,
            onlyLeafCheck: false,
            view: true
        });
        //通用查询组建调用
//			$("#storage-query-advanced-saleRejectEnter").advancedQuery({
//				//查询针对的表
//		 		name:'t_storage_salereject_enter',
//		 		//查询针对的表格id
//		 		datagrid:'storage-datagrid-saleRejectEnterPage',
//		 		plain:true
//			});

        //回车事件
        controlQueryAndResetByKey("storage-queay-saleRejectEnter", "storage-reload-saleRejectEnter");

        //查询
        $("#storage-queay-saleRejectEnter").click(function () {
            var queryJSON = $("#storage-form-query-saleRejectEnterPage").serializeJSON();
            $("#storage-datagrid-saleRejectEnterPage").datagrid("load", queryJSON);
        });
        //重置
        $("#storage-reload-saleRejectEnter").click(function () {
            $("#storage-query-storageid").widget('clear');
            $("#storage-query-customerid").customerWidget("clear");
            $("#storage-form-query-saleRejectEnterPage")[0].reset();
            var queryJSON = $("#storage-form-query-saleRejectEnterPage").serializeJSON();
            $("#storage-datagrid-saleRejectEnterPage").datagrid("load", queryJSON);
        });
    });
    function countTotalAmount() {
        var rows = $("#storage-datagrid-saleRejectEnterPage").datagrid('getChecked');
        var totalamount = 0;
        for (var i = 0; i < rows.length; i++) {
            totalamount = Number(totalamount) + Number(rows[i].totalamount == undefined ? 0 : rows[i].totalamount);
        }
        var footerrows = [{id: '选中金额', totalamount: totalamount, printtimes: -99}];
        if (null != footerobject) {
            footerrows.push(footerobject);
        }
        $("#storage-datagrid-saleRejectEnterPage").datagrid("reloadFooter", footerrows);
    }

</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "salerejectenter-dialog-print",
            code: "storage_salerejectenter",
            tableId: "storage-datagrid-saleRejectEnterPage",
            url_preview: "print/storage/saleRejectEnterPrintView.do",
            url_print: "print/storage/saleRejectEnterPrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var data = $("#" + tableId).datagrid("getChecked");
                if (data == null || data.length == 0) {
                    $.messager.alert("提醒", "请选择至少一条记录");
                    return false;
                }
                var printIds = [];
                for (var i = 0; i < data.length; i++) {
                    if (data[i].status != '4') {
                        $.messager.alert("提醒", data[i].id + "，此销售退货入库单不可打印");
                        return false;
                    } else if (data[i].ischeck != "1") {
                        $.messager.alert("提醒", data[i].id + "，此销售退货入库单未验收不可打印");
                        return false;
                    }
                    if (data[i].printtimes > 0) {
                        printIds.push(data[i].id);
                    }
                }
                return data;
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
