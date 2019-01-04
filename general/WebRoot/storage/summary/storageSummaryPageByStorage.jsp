<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>分仓库库存总量查询</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG">
            <security:authorize url="/storage/summaryByStorageExport.do">
                <a href="javaScript:void(0);" id="storage-buttons-summaryByStoragePage" class="easyui-linkbutton"
                   iconCls="button-export" plain="true" title="导出">导出</a>
            </security:authorize>
            <security:authorize url="/storage/storageSummaryByStoragePrintView.do">
                <a href="javaScript:void(0);" id="storage-buttons-summaryByStoragePage-PrintView"
                   class="easyui-linkbutton" iconCls="button-preview" plain="true" title="打印预览">打印预览</a>
            </security:authorize>
            <security:authorize url="/storage/storageSummaryByStoragePrint.do">
                <a href="javaScript:void(0);" id="storage-buttons-summaryByStoragePage-Print" class="easyui-linkbutton"
                   iconCls="button-print" plain="true" title="打印">打印</a>
            </security:authorize>
            <span id="storageSummaryByStorage-query"></span>
        </div>
    </div>
    <div data-options="region:'center'">
        <table id="storageSummaryByStorage-tree-table"></table>
        <div id="storageSummaryByStorage-query-div" style="padding:2px;height:auto">
            <form action="" id="storageSummaryByStorage-form-query">
                <table class="querytable">
                    <tr>
                        <td>商品名称:</td>
                        <td><input id="storageSummaryByStorage-goodsid" name="goodsid"/></td>
                        <td>品牌名称:</td>
                        <td><input id="storageSummaryByStorage-brandid" name="brandid"/></td>
                        <td>所属仓库:</td>
                        <td><input id="storageSummaryByStorage-storageid" name="storageid"/></td>
                    </tr>
                    <tr>
                        <td>供应商:</td>
                        <td><input id="storageSummaryByStorage-supplierid" name="supplierid" style="width:200px"/></td>
                        <td>商品分类:</td>
                        <td><input id="storageSummaryByStorage-goodssort" name="goodssort"/></td>
                        <td rowspan="3" colspan="2">
                            <input type="hidden" name="groupcols" value="storageid,goodsid"/>
                            <a href="javaScript:void(0);" id="storageSummaryByStorage-queay-queryList"
                               class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="storageSummaryByStorage-queay-reloadList"
                               class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div id="storageSummaryLog-div"></div>
    </div>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#storageSummaryByStorage-form-query").serializeJSON();
    var storageSummaryByStorageLogJson = null;
    var loadData = false;
    $(function () {
        //根据初始的列与用户保存的列生成以及字段权限生成新的列
        var tableColJson = $("#storageSummaryByStorage-tree-table").createGridColumnLoad({
            name: 'storage_summary',
            frozenCol: [[
                {
                    field: 'storageid', title: '所属仓库', width: 80, aliascol: 'storageid', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.storagename;
                    }
                },
                {field: 'goodsid', title: '商品编码', width: 60, sortable: true},
                {field: 'goodsname', title: '商品名称', width: 220, aliascol: 'goodsid'},
                {field: 'spell', title: '助记符', width: 60, aliascol: 'goodsid'},
                {field: 'barcode', title: '条形码', width: 85, sortable: true, aliascol: 'goodsid'}
            ]],
            commonCol: [[
                {
                    field: 'brandid', title: '商品品牌', width: 80, aliascol: 'goodsid', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.brandname;
                    }
                },
                {field: 'model', title: '规格型号', width: 100, aliascol: 'goodsid', hidden: true},
                {
                    field: 'boxnum', title: '箱装量', width: 45, align: 'right', aliascol: 'goodsid',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {
                    field: 'unitid', title: '单位', width: 35,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.unitname;
                    }
                },
                {
                    field: 'price', title: '单价', width: 50, align: 'right', sortable: true, aliascol: 'amount',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'existingnum', title: '现存量', width: 60, align: 'right', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {field: 'auxexistingdetail', title: '现存箱数', width: 80, align: 'right', aliascol: 'existingnum'},
                {
                    field: 'existingamount',
                    title: '现存金额',
                    resizable: true,
                    align: 'right',
                    aliascol: 'amount',
                    sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'usablenum', title: '可用量', width: 60, align: 'right', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {field: 'auxusabledetail', title: '可用箱数', width: 80, align: 'right', aliascol: 'usablenum'},
                {
                    field: 'usableamount',
                    title: '可用金额',
                    resizable: true,
                    align: 'right',
                    aliascol: 'amount',
                    sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'waitnum', title: '待发量', width: 60, align: 'right', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {
                    field: 'auxwaitdetail', title: '待发箱数', width: 80, align: 'right', aliascol: 'waitnum',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.waitnum != 0) {
                            return value;
                        }
                    }
                },
                {
                    field: 'waitamount',
                    title: '待发金额',
                    resizable: true,
                    align: 'right',
                    aliascol: 'amount',
                    sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'transitnum', title: '在途量', width: 60, align: 'right', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {
                    field: 'auxtransitdetail', title: '在途箱数', width: 80, align: 'right', aliascol: 'transitnum',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.transitnum != 0) {
                            return value;
                        }
                    }
                },
                {
                    field: 'allotwaitnum', title: '调拨待发量', width: 60, align: 'right', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {
                    field: 'auxallotwaitdetail', title: '调拨待发箱数', width: 80, align: 'right', aliascol: 'allotwaitnum',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.allotwaitnum != 0) {
                            return value;
                        }
                    }
                },
                {
                    field: 'allotenternum', title: '调拨待入量', width: 60, align: 'right', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {
                    field: 'auxallotenterdetail', title: '调拨待入箱数', width: 80, align: 'right', aliascol: 'allotenternum',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.allotenternum != 0) {
                            return value;
                        }
                    }
                },
                {
                    field: 'projectedusablenum', title: '预计可用量', width: 60, align: 'right', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {
                    field: 'auxprojectedusabledetail',
                    title: '预计可用量箱数',
                    width: 80,
                    align: 'right',
                    aliascol: 'projectedusablenum',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.projectedusablenum != 0) {
                            return value;
                        }
                    }
                },
                {
                    field: 'safenum', title: '安全库存', width: 60, align: 'right', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {
                    field: 'auxsafedetail', title: '安全箱数', width: 80, align: 'right', aliascol: 'safenum',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.safenum != 0) {
                            return value;
                        }
                    }
                }
            ]]
        });
        $("#storageSummaryByStorage-tree-table").datagrid({
            authority: tableColJson,
            frozenColumns: tableColJson.frozen,
            columns: tableColJson.common,
            fit: true,
            rownumbers: true,
            pagination: true,
            idField: 'id',
            pageSize: 100,
            sortName: 'storageid',
            sortOrder: 'asc',
            singleSelect: true,
            showFooter: true,
            toolbar: '#storageSummaryByStorage-query-div',
            url: 'storage/showStorageSummaryList.do',
            queryParams: initQueryJSON,
            onBeforeLoad: function () {
                $(this).datagrid('clearChecked');
                $(this).datagrid('clearSelections');
                return loadData;
            },
            onDblClickRow: function (rowIndex, rowData) {
                var goodsid = "";
                if (rowData.goodsid != null) {
                    goodsid = rowData.goodsid;
                }
                var storageid = "";
                if (rowData.storageid != null) {
                    storageid = rowData.storageid;
                }
                var url = "storage/showStorageSummaryLogPage.do?goodsid=" + goodsid + "&storageid=" + storageid;
                $('#storageSummaryLog-div').dialog({
                    title: '商品库存量追踪日志',
                    width: 680,
                    height: 400,
                    collapsible: false,
                    minimizable: false,
                    maximizable: true,
                    resizable: true,
                    closed: true,
                    cache: false,
                    href: url,
                    modal: true
                });
                $('#storageSummaryLog-div').dialog("open");
            }
        });
        //通用查询组建调用
        $("#storageSummaryByStorage-query").advancedQuery({
            //查询针对的表
            name: 't_base_goods_info',
            //查询针对的表格id
            datagrid: 'storageSummary-tree-table'
        });
        $("#storageSummaryByStorage-supplierid").widget({
            referwid: 'RL_T_BASE_BUY_SUPPLIER',
            width: 250,
            singleSelect: false
        });
        //商品档案通用控件
        $("#storageSummaryByStorage-goodsid").widget({
            referwid: 'RL_T_BASE_GOODS_INFO',
            width: 250,
            singleSelect: false
        });
        $("#storageSummaryByStorage-brandid").widget({
            referwid: 'RL_T_BASE_GOODS_BRAND',
            singleSelect: false,
            width: 130,
            view: true
        });
        $("#storageSummaryByStorage-storageid").widget({
            name: 't_storage_summary',
            col: 'storageid',
            singleSelect: false,
            width: 130,
            view: true
        });
        //商品分类
        $("#storageSummaryByStorage-goodssort").widget({
            referwid: 'RL_T_BASE_GOODS_WARESCLASS',
            width: 130,
            singleSelect: false,
            view: true
        });

        //回车事件
        controlQueryAndResetByKey("storageSummaryByStorage-queay-queryList", "storageSummaryByStorage-queay-reloadList");

        $("#storageSummaryByStorage-queay-queryList").click(function () {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#storageSummaryByStorage-form-query").serializeJSON();
            $("#storageSummaryByStorage-tree-table").datagrid({
                url: 'storage/showStorageSummaryList.do',
                pageNumber: 1,
                queryParams: queryJSON
            });
        });
        $("#storageSummaryByStorage-queay-reloadList").click(function () {
            $("#storageSummaryByStorage-goodsid").widget("clear");
            $("#storageSummaryByStorage-storageid").widget("clear");
            $("#storageSummaryByStorage-brandid").widget("clear");
            $("#storageSummaryByStorage-goodssort").widget('clear');
            $("#storageSummaryByStorage-form-query")[0].reset();
            $("#storageSummaryByStorage-tree-table").datagrid("loadData", {total: 0, rows: [], footer: []});
        });

        $("#storage-buttons-summaryByStoragePage").Excel('export', {
            queryForm: "#storageSummaryByStorage-form-query", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            type: 'exportUserdefined',
            name: '分仓库库存总量查询',
            url: 'storage/exportSummaryStorageData.do'//和库存明细表的导出分开
        });

        //根据初始的列与用户保存的列生成以及字段权限生成新的列
        storageSummaryLogJson = $("#storage-storageSummaryLog-table").createGridColumnLoad({
            name: 'storage_summary_log',
            frozenCol: [[]],
            commonCol: [[
                {
                    field: 'goodsid', title: '商品名称', width: 100,
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            return rowData.goodsInfo.name;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'goodsInfo.name', title: '商品编码', width: 60, aliascol: 'goodsid',
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.goodsid;
                    }
                },
                {
                    field: 'goodsInfo.brand', title: '商品品牌', width: 80, aliascol: 'goodsid', hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            return rowData.goodsInfo.brandName;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'goodsInfo.model', title: '规格型号', width: 100, aliascol: 'goodsid', hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            return rowData.goodsInfo.model;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'goodsInfo.barcode', title: '条形码', width: 85, aliascol: 'goodsid', hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            return rowData.goodsInfo.barcode;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'storageid', title: '所属仓库', width: 80, isShow: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.storagename;
                    }
                },
                {
                    field: 'storagelocationid', title: '所属库位', width: 80, isShow: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.storagelocationname;
                    }
                },
                {field: 'batchno', title: '批次号', width: 80, isShow: true},
                {
                    field: 'unitid', title: '主单位', width: 60,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.unitname;
                    }
                },
                {
                    field: 'receivenum', title: '收货数量', width: 80, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        if (value != null && value != 0) {
                            if (rowData.unitname != null) {
                                return formatterMoney(value) + rowData.unitname;
                            } else {
                                return formatterMoney(value);
                            }
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'sendnum', title: '发货数量', width: 80, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        if (value != null && value != 0) {
                            if (rowData.unitname != null) {
                                return formatterMoney(value) + rowData.unitname;
                            } else {
                                return formatterMoney(value);
                            }
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'auxunitid', title: '辅单位', width: 50,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.auxunitname;
                    }
                },
                {
                    field: 'auxreceivenum', title: '辅单位收货数量', width: 90, align: 'right', aliascol: 'auxunitid',
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.auxreceivenumdetail;
                    }
                },
                {
                    field: 'auxsendnum', title: '辅单位发货数量', width: 90, align: 'right', aliascol: 'auxunitid',
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.auxsendnumdetail;
                    }
                },
                {field: 'addtime', title: '操作时间', width: 120},
                {
                    field: 'goodsInfo.field01',
                    title: '${fieldmap.field01}',
                    width: 100,
                    hidden: true,
                    aliascol: 'goodsid',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            return rowData.goodsInfo.field01;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'goodsInfo.field02',
                    title: '${fieldmap.field02}',
                    width: 100,
                    hidden: true,
                    aliascol: 'goodsid',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            return rowData.goodsInfo.field02;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'goodsInfo.field03',
                    title: '${fieldmap.field03}',
                    width: 100,
                    hidden: true,
                    aliascol: 'goodsid',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            return rowData.goodsInfo.field03;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'goodsInfo.field04',
                    title: '${fieldmap.field04}',
                    width: 100,
                    hidden: true,
                    aliascol: 'goodsid',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            return rowData.goodsInfo.field04;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'goodsInfo.field05',
                    title: '${fieldmap.field05}',
                    width: 100,
                    hidden: true,
                    aliascol: 'goodsid',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            return rowData.goodsInfo.field05;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'goodsInfo.field06',
                    title: '${fieldmap.field06}',
                    width: 100,
                    hidden: true,
                    aliascol: 'goodsid',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            return rowData.goodsInfo.field06;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'goodsInfo.field07',
                    title: '${fieldmap.field07}',
                    width: 100,
                    hidden: true,
                    aliascol: 'goodsid',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            return rowData.goodsInfo.field07;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'goodsInfo.field08',
                    title: '${fieldmap.field08}',
                    width: 100,
                    hidden: true,
                    aliascol: 'goodsid',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            return rowData.goodsInfo.field08;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'goodsInfo.field09',
                    title: '${fieldmap.field09}',
                    width: 100,
                    hidden: true,
                    aliascol: 'goodsid',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            return rowData.goodsInfo.field09;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'goodsInfo.field10',
                    title: '${fieldmap.field10}',
                    width: 100,
                    hidden: true,
                    aliascol: 'goodsid',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            return rowData.goodsInfo.field10;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'goodsInfo.field11',
                    title: '${fieldmap.field11}',
                    width: 100,
                    hidden: true,
                    aliascol: 'goodsid',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            return rowData.goodsInfo.field11;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'goodsInfo.field12',
                    title: '${fieldmap.field12}',
                    width: 100,
                    hidden: true,
                    aliascol: 'goodsid',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            return rowData.goodsInfo.field12;
                        } else {
                            return "";
                        }
                    }
                }
            ]]
        });
    });
    setTimeout(function () {
        loadData = true;
    }, 100);
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "summarybystorage-dialog-print",
            code: "storage_summarybystorage",
            queryForm: "storageSummaryByStorage-form-query",
            url_preview: "print/storage/storageSummaryByStoragePrintView.do",
            url_print: "print/storage/storageSummaryByStoragePrint.do",
            btnPreview: "storage-buttons-summaryByStoragePage-PrintView",
            btnPrint: "storage-buttons-summaryByStoragePage-Print"
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
