<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>特价调整单列表页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="sales-buttons-offpriceListPage"></div>
    </div>
    <div data-options="region:'center'">
        <table id="sales-datagrid-offpriceListPage"></table>
        <div id="sales-queryDiv-offpriceListPage" style="padding:2px;height:auto">
            <form id="sales-queryForm-offpriceListPage">
                <table class="querytable">
                    <tr>
                        <td>业务日期：</td>
                        <td><input type="text" name="businessdate" style="width:100px;" class="Wdate"
                                   onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                            到 <input type="text" name="businessdate1" style="width:100px;" class="Wdate"
                                     onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
                        <td>客&nbsp;户&nbsp;群：</td>
                        <td>
                            <input type="text" name="customertype" id="sales-customertype-offpriceListPage"
                                   style="width: 183px;"/>
                        </td>
                        <td>状态：</td>
                        <td>
                            <select name="status" style="width:160px;">
                                <option></option>
                                <option value="2" selected="selected">保存</option>
                                <option value="3">审核通过</option>
                                <option value="4">关闭</option>
                                <option value="5">中止</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>商&nbsp;&nbsp;品：</td>
                        <td><input type="text" id="sales-goodsid-offpriceListPage" name="goodsid" style="width:225px"/>
                        </td>
                        <td>档期:</td>
                        <td><input type="text" name="schedule" style="width: 150px;"></td>
                        <td>打印状态:</td>
                        <td>
                            <select name="printsign" style="width:160px;">
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
                            <input type="hidden" name="queryprinttimes" value="0"/>
                        </td>
                    </tr>
                    <tr>
                        <td>客户群名称：</td>
                        <td id="customertd">
                            <input type="text" id="sales-customer-offpriceListPage" name="customerid"/>
                        </td>
                        <td colspan="2"></td>
                        <td colspan="2" class="tdbutton">
                            &nbsp;
                            <a href="javascript:;" id="sales-queay-offpriceListPage" class="button-qr">查询</a>
                            <a href="javaScript:;" id="sales-resetQueay-offpriceListPage" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        var queryJSON = $("#sales-queryForm-offpriceListPage").serializeJSON();

        $("#sales-goodsid-offpriceListPage").goodsWidget();
        //客户群
        $("#sales-customertype-offpriceListPage").widget({
            name: 't_sales_offprice',
            col: 'customertype',
            singleSelect: false,
            width: 150,
            onLoadSuccess: function () {
                $("#sales-customertype-offpriceListPage").prepend("<option value='' selected></option>");
            },
            onSelect: function (data) {
                changeCustomerWidget(data.id, "", "0");
            }
        });
        //客户群名称
        $("#sales-customer-offpriceListPage").widget({
            referwid: "RL_T_BASE_SALES_CUSTOMER",
            singleSelect: true,
            width: 225,
            onlyLeafCheck: true
        });
        function changeCustomerWidget(customertype, customerid, disabled) {
            $("#customertd").empty();
            var tdstr = "", disabledstr = "";
            if (disabled == "1") {
                disabledstr = 'disabled="disabled"';
            }
            if ("1" == customertype) {
                tdstr = '<input type="text" id="sales-customer-offpriceListPage" name="customerid" value="' + customerid + '" ' + disabledstr + '/>';
            } else if ("2" == customertype) {
                tdstr = '<input type="text" id="sales-customer-offpriceListPage" name="customerid" value="' + customerid + '" ' + disabledstr + '/>';
            } else if ("3" == customertype) {
                tdstr = '<input type="text" id="sales-customer-offpriceListPage" name="customerid" value="' + customerid + '" ' + disabledstr + '/>';
            } else if ("4" == customertype) {
                tdstr = '<input type="text" id="sales-customer-offpriceListPage" name="customerid" value="' + customerid + '" ' + disabledstr + '/>';
            } else if ("5" == customertype) {
                tdstr = '<input type="text" id="sales-customer-offpriceListPage" name="customerid" value="' + customerid + '" ' + disabledstr + '/>';
            } else if ("6" == customertype) {
                tdstr = '<input type="text" id="sales-customer-offpriceListPage" name="customerid" value="' + customerid + '" ' + disabledstr + '/>';
            } else if ("7" == customertype) {
                tdstr = '<input type="text" id="sales-customer-offpriceListPage" name="customerid" value="' + customerid + '" ' + disabledstr + '/>';
            } else if ("8" == customertype) {
                tdstr = '<input type="text" id="sales-customer-offpriceListPage" name="customerid" value="' + customerid + '" ' + disabledstr + '/>';
            } else if ("0" == customertype) {
                tdstr = '<input type="text" id="sales-customer-offpriceListPage" name="customerid" readonly="readonly" style="width: 225px;" class="no_input"' +
                    ' value="' + customerid + '" ' + disabledstr + '/>';
            } else {
                tdstr = '<input type="text" id="sales-customer-offpriceListPage" name="customerid" value="' + customerid + '" ' + disabledstr + '/>';
            }
            $(tdstr).appendTo("#customertd");
            if ("1" == customertype) {
                $("#sales-customer-offpriceListPage").widget({
                    referwid: "RL_T_BASE_SALES_CUSTOMER",
                    singleSelect: true,
                    width: 225
                });
            } else if ("2" == customertype) {
                $("#sales-customer-offpriceListPage").widget({
                    referwid: "RL_T_SYS_CODE_PROMOTIONSORT",
                    width: 225
                });
            } else if ("3" == customertype) {
                $("#sales-customer-offpriceListPage").widget({
                    referwid: "RT_T_BASE_SALES_CUSTOMERSORT",
                    singleSelect: true,
                    width: 225,
                    onlyLeafCheck: false
                });
            } else if ("4" == customertype) {
                $("#sales-customer-offpriceListPage").widget({
                    referwid: "RL_T_SYS_CODE_PRICELIST",
                    width: 225
                });
            } else if ("5" == customertype) {
                $("#sales-customer-offpriceListPage").widget({
                    referwid: "RT_T_BASE_SALES_AREA",
                    singleSelect: true,
                    onlyLeafCheck: false,
                    width: 225
                });
            } else if ("6" == customertype) {
                $("#sales-customer-offpriceListPage").widget({
                    referwid: "RL_T_BASE_SALES_CUSTOMER_PARENT",
                    singleSelect: true,
                    width: 225
                });
            } else if ("7" == customertype) {
                $("#sales-customer-offpriceListPage").widget({
                    referwid: "RL_T_SYS_CODE_CREDITRATING",
                    width: 225
                });
            } else if ("8" == customertype) {
                $("#sales-customer-offpriceListPage").widget({
                    referwid: "RL_T_SYS_CODE_CANCELTYPE",
                    width: 225
                });
            } else {
                $("#sales-customer-offpriceListPage").widget({
                    referwid: "RL_T_BASE_SALES_CUSTOMER",
                    singleSelect: true,
                    width: 225,
                    onlyLeafCheck: true
                });
            }
        }


        //回车事件
        controlQueryAndResetByKey("sales-queay-offpriceListPage", "sales-resetQueay-offpriceListPage");

        $("#sales-queay-offpriceListPage").click(function () {
            var queryJSON = $("#sales-queryForm-offpriceListPage").serializeJSON();
            $("#sales-datagrid-offpriceListPage").datagrid('load', queryJSON).datagrid("columnMoving");
        });
        $("#sales-resetQueay-offpriceListPage").click(function () {
            //	$(':input','#myform').not(':button,:submit,:reset,:hidden').val('');
            $("#sales-queryForm-offpriceListPage").form("reset");
            $("#sales-customertype-offpriceListPage").val();
            //判断客户是否为全体客户，添加不同参照窗口
            if ("" == $("#sales-customer-offpriceListPage").val()) {
                $("#sales-customer-offpriceListPage").removeClass("no_input");
                $("#sales-customer-offpriceListPage").widget({
                    referwid: "RL_T_BASE_SALES_CUSTOMER",
                    singleSelect: true,
                    width: 225
                });
            } else {
                $("#sales-customer-offpriceListPage").widget("clear");
            }

            $("#sales-goodsid-offpriceListPage").goodsWidget("clear");
            var queryJSON = $("#sales-queryForm-offpriceListPage").serializeJSON();
            $("#sales-datagrid-offpriceListPage").datagrid('load', queryJSON).datagrid("columnMoving");
        });
        //按钮
        $("#sales-buttons-offpriceListPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/sales/offpriceAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addTab('sales/offpricePage.do', "特价调整单新增");
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/offpriceViewPage.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var con = $("#sales-datagrid-offpriceListPage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        top.addOrUpdateTab('sales/offpricePage.do?type=edit&id=' + con.id, "特价调整单查看");
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/offpriceImport.do">
                {
                    type: 'button-import',
                    attr: {
                        type: 'importUserdefined',
                        importparam: '必填项：客户群，客户群名称，生效日期，截止日期，商品编码。<br/>选填项：数量区间',
                        url: 'sales/importOffpriceListData.do',
                        onClose: function () { //导入成功后窗口关闭时操作，
                            $("#sales-datagrid-orderListPage").datagrid('reload');	//更新列表
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/offpriceExport.do">
                {
                    type: 'button-export',
                    attr: {
                        datagrid: "#sales-datagrid-offpriceListPage",
                        queryForm: "#sales-queryForm-offpriceListPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                        type: 'exportUserdefined',
                        name: '特价促销列表',
                        url: 'sales/exportOffpriceListData.do'
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/offpricePrintViewBtn.do">
                {
                    type: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/offpricePrintBtn.do">
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
                        name: 't_sales_offprice',
                        plain: true,
                        //查询针对的表格id
                        datagrid: 'sales-datagrid-offpriceListPage'
                    }
                },
                {}
            ],
            buttons: [
                {},
                <security:authorize url="/sales/offpriceCancelBtn.do">
                {
                    id: 'button-offpriceCancel',
                    name: '作废',
                    iconCls: 'button-delete',
                    handler: function () {
                        var con = $("#sales-datagrid-offpriceListPage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择至少一条非中止的记录");
                            return false;
                        }
                        $.messager.confirm("提醒", "确定作废选中的特价促销信息？", function (r) {
                            if (r) {
                                var rows = $("#sales-datagrid-offpriceListPage").datagrid('getChecked');
                                var ids = "";
                                var failnum = 0;
                                for (var i = 0; i < rows.length; i++) {
                                    if (ids == "" && rows[i].status != '5') {
                                        ids = rows[i].id;
                                    } else if (rows[i].status != '5') {
                                        ids += "," + rows[i].id;
                                    } else {
                                        failnum += 1;
                                    }
                                }
                                if (ids != '') {
                                    $.ajax({
                                        url: 'sales/offpriceCancel.do',
                                        dataType: 'json',
                                        type: 'post',
                                        data: 'ids=' + ids + "&operate=1",
                                        success: function (json) {
                                            loaded();
                                            if (json.flag == true) {
                                                $.messager.alert("提醒", "作废成功：" + json.success + "<br/>作废失败：" + (json.failure + failnum));
                                                $("#sales-datagrid-offpriceListPage").datagrid('reload');
                                            }
                                            else {
                                                $.messager.alert("提醒", "作废失败");
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "作废失败");
                                        }
                                    });
                                } else {
                                    $.messager.alert("错误", "请选择非中止状态的记录进行作废");
                                }
                            }
                        });
                    }
                },
                </security:authorize>
            ],
            model: 'bill',
            type: 'list',
            tname: 't_sales_offprice'
        });
        var offpriceListJson = $("#sales-datagrid-offpriceListPage").createGridColumnLoad({
            name: 't_sales_offprice',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编号', width: 130, align: 'left'},
                {field: 'businessdate', title: '业务日期', width: 80, align: 'left'},
                {
                    field: 'customertype', title: '客户群', width: 80, align: 'left',
                    formatter: function (value, row, index) {
                        return getSysCodeName('customertype', value);
                    }
                },
                {field: 'customerid', title: '客户群编码', width: 80, align: 'left'},
                {field: 'customername', title: '客户群名称', width: 200, align: 'left', isShow: true},
                {field: 'schedule', title: '档期', width: 60, align: 'left', isShow: true},
                {field: 'applydeptid', title: '申请部门', width: 80, align: 'left', hidden: true},
                {field: 'applyuserid', title: '申请人', width: 60, align: 'left', hidden: true},
                {field: 'begindate', title: '生效日期', width: 80, align: 'left'},
                {field: 'enddate', title: '截止日期', width: 80, align: 'left'},
                {
                    field: 'status', title: '状态', width: 60, align: 'left',
                    formatter: function (value, row, index) {
                        return getSysCodeName('status', value);
                    }
                },
                {field: 'addusername', title: '制单人', width: 80, sortable: true},
                {field: 'printtimes', title: '打印次数', width: 60, hidden: true},
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
                {field: 'addtime', title: '制单时间', width: 80, sortable: true, hidden: true},
                {field: 'auditusername', title: '审核人', width: 80, sortable: true, hidden: true},
                {field: 'audittime', title: '审核时间', width: 80, sortable: true, hidden: true}
            ]]
        });
        $("#sales-datagrid-offpriceListPage").datagrid({
            authority: offpriceListJson,
            frozenColumns: offpriceListJson.frozen,
            columns: offpriceListJson.common,
            fit: true,
            title: "",
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            pageSize: 100,
            url: 'sales/getOffpriceList.do',
            queryParams: queryJSON,
            toolbar: '#sales-queryDiv-offpriceListPage',
            onDblClickRow: function (index, data) {
                top.addOrUpdateTab('sales/offpricePage.do?type=edit&id=' + data.id, '特价调整单查看');
            }
        }).datagrid("columnMoving");
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-offprice-dialog-print",
            code: "sales_offprice",
            tableId: "sales-datagrid-offpriceListPage",
            url_preview: "print/sales/salesOffpricePrintView.do",
            url_print: "print/sales/salesOffpricePrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var data = $("#" + tableId).datagrid("getChecked");
                if (data == null || data.length == 0) {
                    $.messager.alert("提醒", "请选择至少一条记录");
                    return false;
                }
                for (var i = 0; i < data.length; i++) {
                    if (data[i].status != '3' && data[i].status != '4') {
                        $.messager.alert("提醒", "抱歉，保存状态下不能打印");
                        return false;
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
