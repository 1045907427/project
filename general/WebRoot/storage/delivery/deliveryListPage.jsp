<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>配送单页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-deliveryPage"></div>
    </div>
    <div data-options="region:'center'">
        <table id="storage-datagrid-deliveryPage"></table>
        <div id="storage-datagrid-toolbar-deliveryPage" style="padding:2px;height:auto">
            <form action="" id="storage-form-query-deliveryPage" method="post">
                <table class="querytable">
                    <tr>
                        <td>单据编号:</td>
                        <td><input type="text" id="storage-id-deliverySourceQueryPage" name="id" style="width: 225px;"/>
                        </td>
                        <td>车辆:</td>
                        <td><input type="text" id="storage-carid-deliverySourceQueryPage" name="carid"/></td>
                        <td>司机/跟车:</td>
                        <td><input type="text" id="storage-driverid-deliverySourceQueryPage" name="driverid"/></td>
                    </tr>
                    <tr>
                        <td>业务日期:</td>
                        <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate"
                                   onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${firstday }"/> 到 <input
                                type="text" name="businessdate2" class="Wdate" style="width:100px;"
                                onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
                        <td>线路:</td>
                        <td><input type="text" id="storage-lineid-deliverySourceQueryPage" name="lineid"/></td>
                        <!--
                    </tr>
                    <tr>
                        <td>客户:</td>
                        <td><input id="storage-query-customerid" type="text" name="chlidcustomerid" style="width: 220px;"/></td>
                        <td>总店:</td>
                        <td><input id="storage-query-headcustomerid" type="text" name="pcustomerid" style="width: 160px;"/></td>
                        <td>销售部门:</td>
                        <td><input id="storage-query-salesdept" type="text" name="salesdept" style="width: 160px;"/></td>
                    </tr>
                    <tr>
                        <td colspan="6">
                            <table cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                    <td>来源单据编号:</td>
                                    <td>
                                        <input id="storage-query-sourceid" type="text" name="sourceid" style="width: 200px;"/>
                                    </td>
                                    <td style="padding-left: 10px;">申请状态:</td>
                                    <td>
                                        <select name="applytype" style="width:100px;">
                                            <option></option>
                                            <option value="1">开票</option>
                                            <option value="2">核销</option>
                                            <option value="3">开票后核销</option>
                                        </select>
                                    </td>
                                    <td style="padding-left: 10px;">状态:</td>
                                    <td><select id="storage-queay-delivery-status" name="status" style="width:100px;"><option></option><option value="2" selected="selected">保存</option><option value="3">审核通过</option><option value="4">关闭</option></select></td>
                                </tr>
                            </table>
                        </td>-->
                    </tr>
                    <tr>
                        <td>客户:</td>
                        <td><input type="text" id="storage-customerid-deliverySourceQueryPage" name="customerid"
                                   style="width: 225px;"/></td>
                        <td>状态:</td>
                        <td><select name="status" style="width:150px;">
                            <option></option>
                            <option value="2" selected="selected">保存</option>
                            <option value="3">审核通过</option>
                            <option value="4">已关闭</option><!-- <option value="5">作废</option> --></select>
                        </td>
                        <td colspan="2">
                            <a href="javaScript:void(0);" id="storage-queay-delivery" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="storage-reload-delivery" class="button-qr">重置</a>
                            <span id="storage-query-advanced-delivery" hidden="true"></span>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<div style="display:none">
    <div id="storage-invoice-dialog-printview" class="easyui-dialog" data-options="closed:true">
        <table>
            <tr>
                <td>是否负值转为折扣:</td>
                <td>
                    <select id="printview-discount">
                        <option value="0">否</option>
                        <option value="1">是</option>
                    </select>
                </td>
            </tr>
        </table>
    </div>
    <div id="storage-invoice-dialog-print" class="easyui-dialog" data-options="closed:true">
        <table>
            <tr>
                <td>是否负值转为折扣:</td>
                <td>
                    <select id="print-discount">
                        <option value="0">否</option>
                        <option value="1">是</option>
                    </select>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="storage-panel-relation-upper"></div>
<div id="storage-panel-sourceQueryPage"></div>
<div id="storage-dialog-writeoff"></div>
<script type="text/javascript">
    var initQueryJSON = $("#storage-form-query-deliveryPage").serializeJSON();
    var SI_footerobject = null;
    //加锁
    function isDoLockData(id, tablename) {
        var flag = false;
        $.ajax({
            url: 'system/lock/isDoLockData.do',
            type: 'post',
            data: {id: id, tname: tablename},
            dataType: 'json',
            async: false,
            success: function (json) {
                flag = json.flag
            }
        });
        return flag;
    }
    $(function () {
        $("#storage-lineid-deliverySourceQueryPage").widget({
            referwid: 'RL_T_BASE_LOGISTICS_LINE',
            singleSelect: true,
            width: 150,
            onlyLeafCheck: true
        });
        $("#storage-driverid-deliverySourceQueryPage").widget({
            referwid: 'RL_T_BASE_PERSONNEL_LOGISTICS',
            singleSelect: true,
            width: 120,
            onlyLeafCheck: true
        });
        $("#storage-carid-deliverySourceQueryPage").widget({
            referwid: 'RL_T_BASE_LOGISTICS_CAR',
            singleSelect: true,
            width: 150,
            onlyLeafCheck: true
        });

        //按钮
        $("#storage-buttons-deliveryPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/delivery/deliveryAdd.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addOrUpdateTab('storage/delivery/showDeliveryAddPage.do', "配送单新增");
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/delivery/deliveryView.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var con = $("#storage-datagrid-deliveryPage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        var type = 'view';
                        var status = con.status;
                        if (status == '2')
                            type = 'edit';
                        if (status == '3')
                            type = 'audit';
                        if ('view' != type) {
                            var flag = isDoLockData(con.id, "t_storage_logistics_delivery");
                            if (!flag) {
                                $.messager.alert("警告", "该数据正在被其他人操作，暂不能修改！");
                                return false;
                            }
                        }
                        top.addOrUpdateTab('storage/showDeliveryPage.do?type=' + type + '&id=' + con.id, "配送单查看");
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/delivery/deliveryDelete.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        var rows = $("#storage-datagrid-deliveryPage").datagrid('getChecked');
                        if (rows.length == 0) {
                            $.messager.alert("提醒", "请至少勾选一条记录");
                            return false;
                        }

                        $.messager.confirm("提醒", "是否确定删除选中的配送单?", function (r) {
                            if (r) {
                                var ids = "";
                                for (var i = 0; i < rows.length; i++) {
                                    if (ids == "") {
                                        ids = rows[i].id;
                                    } else {
                                        ids += "," + rows[i].id;
                                    }
                                }
                                loading("删除中..");
                                $.ajax({
                                    url: 'storage/deleteDeliverys.do?ids=' + ids,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        $.messager.alert("提醒", "" + json.userNum + "条记录被引用,不允许删除;<br/>" + json.lockNum + "条记录网络互斥,不允许删除;<br/>" + json.invalidNum + "条记录无效,不允许删除;<br/>删除成功" + json.num + "条记录;");
                                        if (json.flag) {
                                            var title = tabsWindowTitle('/storage/showDeliveryListPage.do');
                                            if (top.$('#tt').tabs('exists', title)) {
                                                tabsWindow(title).$("#storage-datagrid-deliveryPage").datagrid('reload');
                                            }
                                        }
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "删除出错");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/delivery/deliveryAudit.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        var rows = $("#storage-datagrid-deliveryPage").datagrid('getChecked');
                        if (rows == null) {
                            $.messager.alert("提醒", "请至少勾选一条记录");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否审核选中的配送单？", function (r) {
                            if (r) {
                                var ids = "";
                                for (var i = 0; i < rows.length; i++) {
                                    if (ids == "") {
                                        ids = rows[i].id;
                                    } else {
                                        ids += "," + rows[i].id;
                                    }
                                }
                                loading("审核中..");
                                $.ajax({
                                    url: 'storage/auditDeliverys.do?ids=' + ids,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            //$.messager.alert("提醒","审核成功");
                                            var title = tabsWindowTitle('/storage/showDeliveryListPage.do');
                                            if (top.$('#tt').tabs('exists', title)) {
                                                tabsWindow(title).$("#storage-datagrid-deliveryPage").datagrid('reload');
                                            }
                                            $.messager.alert("提醒", "审核无效" + json.invalidNum + "条记录;<br/>审核成功" + json.num + "条记录;");
                                            //$.messager.alert("提醒","审核成功。"+json.msg+"</br>生成销售发货回单，编号为："+json.receiptid);
                                            //$("#storage-delivery-status-select").val(3);
                                            //$("#storage-buttons-deliveryPage").buttonWidget("setDataID",{id:'${id}',state:'3',type:'view'});

                                            //$("#storage-buttons-deliveryPage").buttonWidget("disableButton","button-print-orderblank");
                                            //$("#storage-buttons-deliveryPage").buttonWidget("disableButton","button-printview-orderblank");

                                            //$("#storage-buttons-deliveryPage").buttonWidget("enableButton","button-print-DeliveryOrder");
                                            //$("#storage-buttons-deliveryPage").buttonWidget("enableButton","button-printview-DeliveryOrder");

                                            //刷新列表
                                            //tabsWindowURL("/storage/showDeliveryListPage.do").$("#storage-datagrid-deliveryPage").datagrid('reload');
                                            //关闭当前标签页
                                            //top.closeNowTab();
                                        } else {
                                            $.messager.alert("提醒", "审核失败");
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
                },
                </security:authorize>
                {}
            ],
            buttons: [
                {},
                <security:authorize url="/storage/delivery/deliveryPrintView.do">
                {
                    id: 'printMenuButton',
                    type: 'menu',
                    name: '打印',
                    iconCls: 'button-preview',
                    button: [
                        {},
                        <security:authorize url="/storage/delivery/deliveryCustomerPrintView.do">
                        {
                            id: 'print-preview-button',
                            name: '交接单明细打印预览',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/delivery/deliveryCustomerPrint.do">
                        {
                            id: 'print-bill-button',
                            name: '交接单明细打印',
                            iconCls: 'button-print',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/delivery/deliveryBillPrintView.do">
                        {
                            id: 'print-preview-deliverybill-button',
                            name: '配送单明细打印预览',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/delivery/deliveryBillPrint.do">
                        {
                            id: 'print-deliverybill-button',
                            name: '配送单明细打印',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        {}
                    ]
                },
                </security:authorize>
                <security:authorize url="/storage/delivery/exportDeliveryListData.do">
                {
                    id: 'button-deliverybill-export',
                    name: '导出',
                    iconCls: 'button-export',
                    handler: function () {
                        var queryJSON = $("#storage-form-query-deliveryPage").serializeJSON();
                        var rows = $("#storage-datagrid-deliveryPage").datagrid('getChecked');
                        var id = "" ;
                        if(rows.length >0){
                            for(var i = 0 ; i < rows.length ; ++ i){
                                id += rows[i].id + ",";
                            }
                            queryJSON["exportid"] = id ;
                        }
                        //获取排序规则
                        var objecr = $("#storage-datagrid-deliveryPage").datagrid("options");
                        if (null != objecr.sortName && null != objecr.sortOrder) {
                            queryJSON["sort"] = objecr.sortName;
                            queryJSON["order"] = objecr.sortOrder;
                        }
                        var queryParam = JSON.stringify(queryJSON);
                        var url = "storage/exportDeliveryListData.do";
                        exportByAnalyse(queryParam, "配送单单列表", "storage-datagrid-deliveryPage", url);
                    }
                },
                </security:authorize>
                {}
            ],
            model: 'bill',
            type: 'list',
            tname: 't_storage_allocate_enter'
        });
        var deliveryJson = $("#storage-datagrid-deliveryPage").createGridColumnLoad({
//            name: 't_storage_logistics_delivery',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编号', width: 125, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
                {
                    field: 'lineid', title: '线路名称', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.linename;
                    }
                },
                {
                    field: 'carid', title: '车辆名称', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.carname;
                    }
                },
                {field: 'cartype', title: '车型', width: 60, sortable: true},
                {
                    field: 'driverid', title: '司机名称', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.drivername;
                    }
                },
                {
                    field: 'followid', title: '跟车员名称', width: 150, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.followname;
                    }
                },
                {
                    field: 'boxnum', title: '箱数', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {field: 'truck', title: '装车次数', width: 60, sortable: true},
                {field: 'customernums', title: '送货家数', width: 60, sortable: true},
                {
                    field: 'collectionamount', title: '收款总金额', resizable: true, sortable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'salesamount', title: '销售额', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'weight', title: '重量', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (value == null)
                            return null;
                        return formatterMoney(value, 4) + " kg";
                    }
                },
                {
                    field: 'volume', title: '体积', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (value == null)
                            return null;
                        return formatterMoney(value, 4) + " m³";
                    }
                },
                {field: 'checkdate', title: '验收日期', width: 80, sortable: true, isShow: true},
                {field: 'addusername', title: '制单人', width: 80, sortable: true, hidden: true},
                {field: 'addtime', title: '制单时间', width: 80, sortable: true, hidden: true},
                {field: 'auditusername', title: '审核人', width: 80, hidden: true, sortable: true},
                {field: 'audittime', title: '审核时间', width: 80, hidden: true, sortable: true},
                {field: 'stopusername', title: '中止人', width: 80, hidden: true, sortable: true},
                {field: 'stoptime', title: '中止时间', width: 80, hidden: true, sortable: true},
                {
                    field: 'status', title: '状态', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return getSysCodeName("status", value);
                    }
                },
                {field: 'billnums', title: '单据数', width: 60, isShow: true},
                {field: 'printtimes', title: '打印次数', width: 60},
                {field: 'remark', title: '备注', width: 100, sortable: true}
            ]]
        });
        $("#storage-datagrid-deliveryPage").datagrid({
            authority: deliveryJson,
            frozenColumns: deliveryJson.frozen,
            columns: deliveryJson.common,
            fit: true,
            title: "",
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            sortName: 'id',
            sortOrder: 'desc',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            showFooter: false,
            pageSize: 100,
            url: 'storage/showDeliveryList.do',
            queryParams: initQueryJSON,
            toolbar: '#storage-datagrid-toolbar-deliveryPage',
            onDblClickRow: function (rowIndex, rowData) {
                var type = 'view';
                if (rowData.status == '2')
                    type = 'edit';
                if (rowData.status == '3')
                    type = 'audit';
                if (rowData.ischecked == '1')
                    type = 'view';
                if ('view' != type) {
                    var flag = isDoLockData(rowData.id, "t_storage_logistics_delivery");
                    if (!flag) {
                        $.messager.alert("警告", "该数据正在被其他人操作，暂不能修改！");
                        return false;
                    }
                }
                top.addOrUpdateTab('storage/delivery/showDeliveryPage.do?id=' + rowData.id + '&type=' + type, "配送单查看");
            },
            onCheckAll: function () {
                //countTotalAmount();
            },
            onUncheckAll: function () {
                //countTotalAmount();
            },
            onCheck: function () {
                //countTotalAmount();
            },
            onUncheck: function () {
                //countTotalAmount();
            },
            onLoadSuccess: function () {
                //var footerrows = $(this).datagrid('getFooterRows');
                //if(null!=footerrows && footerrows.length>0){
                //	SI_footerobject = footerrows[0];
                //}
            }
        }).datagrid("columnMoving");
        $("#storage-customerid-deliverySourceQueryPage").customerWidget({
            isall: true,
            singleSelect: true
        });
        $("#storage-query-headcustomerid").widget({
            referwid: 'RL_T_BASE_SALES_CUSTOMER_PARENT',
            width: 165,
            view: true,
            singleSelect: true
        });
        $("#storage-query-salesdept").widget({
            name: 't_storage_logistics_delivery',
            width: 160,
            col: 'salesdept',
            view: true,
            singleSelect: true
        });
        //通用查询组建调用
        $("#storage-query-advanced-delivery").advancedQuery({
            //查询针对的表
            name: 't_storage_logistics_delivery',
            //查询针对的表格id
            datagrid: 'storage-datagrid-deliveryPage',
            plain: true
        });

        //回车事件
        controlQueryAndResetByKey("storage-queay-delivery", "storage-reload-delivery");

        //查询
        $("#storage-queay-delivery").click(function () {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#storage-form-query-deliveryPage").serializeJSON();
            $("#storage-datagrid-deliveryPage").datagrid("load", queryJSON);
        });
        //重置
        $("#storage-reload-delivery").click(function () {
            $("#storage-lineid-deliverySourceQueryPage").widget("clear");
            $("#storage-driverid-deliverySourceQueryPage").widget("clear");
            $("#storage-driverid-deliverySourceQueryPage").widget("clear");
            $("#storage-carid-deliverySourceQueryPage").widget("clear");
            $("#storage-customerid-deliverySourceQueryPage").customerWidget("clear");
            $("#storage-form-query-deliveryPage")[0].reset();
            var queryJSON = $("#storage-form-query-deliveryPage").serializeJSON();
            $("#storage-datagrid-deliveryPage").datagrid("load", queryJSON);
        });
    });

    //计算勾选配送单合计
    function countTotalAmount() {
        var rows = $("#storage-datagrid-deliveryPage").datagrid('getChecked');
        if (null == rows || rows.length == 0) {
            var foot = [];
            if (null != SI_footerobject) {
                foot.push(SI_footerobject);
            }
            $("#storage-datagrid-deliveryPage").datagrid("reloadFooter", foot);
            return false;
        }
        var customeramount = 0, taxamount = 0, notaxamount = 0, discountamount = 0, invoiceamount = 0, writeoffamount = 0, tailamount = 0;
        for (var i = 0; i < rows.length; i++) {
            customeramount = Number(customeramount) + Number(rows[i].customeramount == undefined ? 0 : rows[i].customeramount);
            taxamount = Number(taxamount) + Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
            notaxamount = Number(notaxamount) + Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
            discountamount = Number(discountamount) + Number(rows[i].discountamount == undefined ? 0 : rows[i].discountamount);
            invoiceamount = Number(invoiceamount) + Number(rows[i].invoiceamount == undefined ? 0 : rows[i].invoiceamount);
            writeoffamount = Number(writeoffamount) + Number(rows[i].writeoffamount == undefined ? 0 : rows[i].writeoffamount);
            tailamount = Number(tailamount) + Number(rows[i].tailamount == undefined ? 0 : rows[i].tailamount);
        }
        var foot = [
            {
                id: '选中金额',
                customeramount: customeramount,
                taxamount: taxamount,
                notaxamount: notaxamount,
                discountamount: discountamount,
                invoiceamount: invoiceamount,
                writeoffamount: writeoffamount,
                tailamount: tailamount
            }
        ];
        if (null != SI_footerobject) {
            foot.push(SI_footerobject);
        }
        $("#storage-datagrid-deliveryPage").datagrid("reloadFooter", foot);
    }

    function reloadDeliveryList() {
        //把form表单的name序列化成JSON对象
        var status = $("#storage-queay-delivery-status").val();
        $("#storage-queay-delivery-status").val(status);
        var queryJSON = $("#storage-form-query-deliveryPage").serializeJSON();
        $("#storage-datagrid-deliveryPage").datagrid("load", queryJSON);
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        function getData(tableId, printParam) {
            var data = $("#" + tableId).datagrid("getChecked");
            if (data == null || data.length == 0) {
                $.messager.alert("提醒", "请选择一条记录");
                return false;
            }

            var unids = "";
            for (var i = 0; i < data.length; i++) {
                if (data[i].status != '3') {
                    if (unids == "") {
                        unids = data[i].id;
                    } else {
                        unids += "," + data[i].id;
                    }
                }
            }
            if (unids != "") {
                $.messager.alert("提醒", "单据编码：" + unids + "不为审核通过状态，不可打印");
                return false;
            }
            return data;
        }

        //交接单明细打印
        AgReportPrint.init({
            id: "logistics-dialog-print",
            code: "storage_logistics",
            tableId: "storage-datagrid-deliveryPage",
            url_preview: "print/storage/deliveryCustomerPrintView.do",
            url_print: "print/storage/deliveryCustomerPrint.do",
            btnPreview: "print-preview-button",
            btnPrint: "print-bill-button",
            getData: getData
        });
        //配送单明细打印
        AgReportPrint.init({
            id: "deliverybill-dialog-print",
            code: "storage_logistics_bill",
            tableId: "storage-datagrid-deliveryPage",
            url_preview: "print/storage/deliveryBillPrintView.do",
            url_print: "print/storage/deliveryBillPrint.do",
            btnPreview: "print-preview-deliverybill-button",
            btnPrint: "print-deliverybill-button",
            getData: getData
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
