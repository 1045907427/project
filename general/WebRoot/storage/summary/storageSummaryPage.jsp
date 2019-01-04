<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>库存明细表</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
    <script type="text/javascript" src="js/datagrid-detailview.js"></script>
</head>
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
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG">
            <security:authorize url="/storage/exportStorageSummaryList.do">
                <a href="javaScript:void(0);" id="storageSummary-export-queryList" class="easyui-linkbutton"
                   iconCls="button-export" plain="true" title="全局导出">全局导出</a>
            </security:authorize>
            <security:authorize url="/storage/exportStorageSummaryDetailList.do">
                <a href="javaScript:void(0);" id="storageSummary-exportdetail-queryList" class="easyui-linkbutton"
                   iconCls="button-export" plain="true">明细导出</a>
            </security:authorize>
            <security:authorize url="/storage/storageSummaryDetailPrintView.do">
                <a href="javaScript:void(0);" id="storage-buttons-storageSummary-PrintView" class="easyui-linkbutton"
                   iconCls="button-preview" plain="true" title="打印预览">打印预览</a>
            </security:authorize>
            <security:authorize url="/storage/storageSummaryDetailPrint.do">
                <a href="javaScript:void(0);" id="storage-buttons-storageSummary-Print" class="easyui-linkbutton"
                   iconCls="button-print" plain="true" title="打印">打印</a>
            </security:authorize>
            <span id="storageSummaryByStorage-query"></span>
        </div>
    </div>
    <div data-options="region:'center'">
        <table id="storageSummary-tree-table"></table>
        <div id="storageSummary-query-div" style="padding:2px;height:auto">
            <form action="" id="storageSummary-form-query">
                <table class="querytable">
                    <tr>
                        <td>商品名称:</td>
                        <td><input type="text" id="storageSummary-goodsid" name="goodsid" style="width:200px"/></td>
                        <td>商品分类:</td>
                        <td><input type="text" id="storageSummary-goodssort" name="goodssort" style="width:150px"/></td>
                        <td colspan="2">
                            <input type="checkbox" class="groupcols checkbox1" name="existingnum" value="existingnum"
                                   id="existingnum" checked="checked"/>
                            <label class="divtext" for="existingnum" style="font-size: 13px">剔除库存为0的商品显示</label>
                        </td>
                    </tr>
                    <tr>
                        <td>供应商:</td>
                        <td><input id="storageSummaryByStorage-supplierid" name="supplierid" style="width:200px"/></td>
                        <td>品牌名称:</td>
                        <td><input type="text" id="storageSummary-brandid" name="brandid" style="width:150px"/></td>
                        <td>所属仓库:</td>
                        <td><input id="storageSummaryByStorage-storageid" name="storageid" style="width:150px"/></td>

                    </tr>
                    <tr>
                        <td>小计列:</td>
                        <td>
                            <input type="checkbox" class="groupcols checkbox1" name="groupcols" value="goodsid"
                                   checked="checked" disabled="disabled" id="goodsid"/>
                            <label class="divtext" for="goodsid">商品</label>

                            <input id="storageSummaryByStorage-storageid-checkbox" type="checkbox"
                                   class="groupcols checkbox1" checked="checked" name="groupcols" value="storageid" id="storageid"/>
                            <label class="divtext" for="storageid">仓库</label>
                        </td>
                        <td>状态：</td>
                        <td>
                            <select name="state" style="width:150px">
                                <option></option>
                                <option value="0">禁用</option>
                                <option value="1">启用</option>
                            </select>
                        </td>
                        <td>购销类型:</td>
                        <td>
                            <select name="bstype" style="width: 130px;" >
                                <option selected></option>
                                <c:forEach items="${bstypeList }" var="list">
                                    <option value="${list.code }">${list.codename }</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td colspan="2">
                            <input type="hidden" name="groupcols" value="goodsid"/>
                            <a href="javaScript:void(0);" id="storageSummary-queay-queryList" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="storageSummary-queay-reloadList" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div id="storageSummaryLog-div"></div>
        <div id="storageSummary-waitnum-div"></div>
        <div id="sstorageSummary-allocateOutWaitnum-div"></div>
    </div>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#storageSummary-form-query").serializeJSON();
    var storageSummaryLogJson = null;
    var SMR_footerobject = null;
    var loadData = false;
    $(function () {
        //根据初始的列与用户保存的列生成以及字段权限生成新的列
        var tableColJson = $("#storageSummary-tree-table").createGridColumnLoad({
            frozenCol: [[]],
            commonCol: [[
                {field: 'idok', checkbox: true, isShow: true},
                {
                    field: 'storageid', title: '所属仓库', width: 80, aliascol: 'storageid', sortable: true, hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.storagename;
                    }
                },
                {field: 'goodsid', title: '商品编码', width: 60, sortable: true},
                {field: 'goodsname', title: '商品名称', width: 220, aliascol: 'goodsid'},
                {field: 'spell', title: '助记符', width: 60, aliascol: 'goodsid'},
                {field: 'barcode', title: '条形码', width: 85, sortable: true, aliascol: 'goodsid'},
                {field: 'waresclassname', title: '商品分类', width: 120, aliascol: 'goodsid', sortable: true},
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
                <security:authorize url="/storage/showStorageSummaryAmount.do">
                {
                    field: 'price', title: '单价', width: 50, align: 'right', sortable: true, aliascol: 'amount',
                    formatter: function (value, rowData, rowIndex) {
                        if (value != "" && null != value) {
                            return formatterMoney(value);
                        }
                    }
                },
                {
                    field: 'basesaleprice', title: '基准价', width: 50, align: 'right', hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (value != "" && null != value) {
                            return formatterMoney(value);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/showStorageSummaryCostAmountView.do">
                {
                    field: 'costprice', title: '成本价', width: 50, align: 'right', sortable: true, aliascol: 'amount',
                    formatter: function (value, rowData, rowIndex) {
                        if (value != "" && null != value) {
                            return formatterMoney(value);
                        }
                    }
                },
                {
                    field: 'storageamount',
                    title: '仓库未分摊金额',
                    width: 100,
                    align: 'right',
                    sortable: true,
                    aliascol: 'amount',
                    formatter: function (value, rowData, rowIndex) {
                        if (value != "" && null != value) {
                            return formatterMoney(value);
                        }
                    }
                },
                </security:authorize>
                {
                    field: 'existingnum', title: '现存量', width: 60, align: 'right', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {field: 'auxexistingdetail', title: '现存箱数', width: 80, align: 'right', aliascol: 'existingnum'},
                <security:authorize url="/storage/showStorageSummaryAmount.do">
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
                    field: 'basesaleamount',
                    title: '基准金额',
                    resizable: true,
                    align: 'right',
                    aliascol: 'amount',
                    hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/showStorageSummaryCostAmountView.do">
                {
                    field: 'costamount',
                    title: '成本金额',
                    resizable: true,
                    align: 'right',
                    aliascol: 'amount',
                    sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                </security:authorize>
                {
                    field: 'usablenum', title: '可用量', width: 60, align: 'right', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {field: 'auxusabledetail', title: '可用箱数', width: 80, align: 'right', aliascol: 'usablenum'},
                <security:authorize url="/storage/showStorageSummaryAmount.do">
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
                </security:authorize>
                {
                    field: 'waitnum', title: '待发量', width: 60, align: 'right', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (value != 0 && rowData.goodsid != null && rowData.goodsid != "") {
                            return '<a href="javascript:showGoodsWaitInfo(\'' + rowData.goodsid + '\',\'' + rowData.storageid + '\')">' + formatterBigNumNoLen(value) + '</a>';
                        } else {
                            return formatterBigNumNoLen(value);
                        }
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
                <security:authorize url="/storage/showStorageSummaryAmount.do">
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
                </security:authorize>
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
                        if (value != 0 && rowData.goodsid != null && rowData.goodsid != "") {
                            return '<a href="javascript:showGoodsAllocateOutWaitInfo(\'' + rowData.goodsid + '\',\'' + rowData.storageid + '\')">' + formatterBigNumNoLen(value) + '</a>';
                        } else {
                            return formatterBigNumNoLen(value);
                        }
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

        $("#storageSummary-tree-table").datagrid({
            authority: tableColJson,
            frozenColumns: tableColJson.frozen,
            columns: tableColJson.common,
            fit: true,
            rownumbers: true,
            pagination: true,
            pageSize: 100,
            idField: 'id',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            showFooter: true,
            toolbar: '#storageSummary-query-div',
            url: 'storage/showStorageSummaryList.do',
            queryParams: initQueryJSON,
            view: detailview,
            onBeforeLoad: function () {
                $(this).datagrid('clearChecked');
                $(this).datagrid('clearSelections');
                return loadData;
            },
            detailFormatter: function (index, row) {
                return '<div style="padding:2px"><table class="table-leaf"></table></div>';
            },
            onExpandRow: function (index, row) {
                var urlstr="";
                if($("#existingnum").attr("checked")){
                    urlstr="&existingnum=1";
                }
                var url = 'storage/showStorageSummaryBatchList.do?goodsid=' + row.goodsid+urlstr;
                var checkflag = $("#storageSummaryByStorage-storageid-checkbox").attr("checked");
                if (checkflag) {
                    url = 'storage/showStorageSummaryBatchList.do?goodsid=' + row.goodsid + "&storageid=" + row.storageid+urlstr;
                }
                var storageid = $("#storageSummaryByStorage-storageid").widget("getValue");
                if (storageid != null && storageid != "") {
                    url = 'storage/showStorageSummaryBatchList.do?goodsid=' + row.goodsid + "&storageid=" + storageid+urlstr;
                }
                var ddv = $(this).datagrid('getRowDetail', index).find('table.table-leaf');
                ddv.datagrid({
                    columns: [[
                        {field: 'barcode', title: '条形码', width: 100},
                        {
                            field: 'storageid', title: '所属仓库', width: 80, aliascol: 'storageid', sortable: true,
                            formatter: function (value, rowData, rowIndex) {
                                return rowData.storagename;
                            }
                        },
                        {
                            field: 'storagelocationid', title: '所属库位', width: 80, aliascol: 'storageid', sortable: true,
                            formatter: function (value, rowData, rowIndex) {
                                return rowData.storagelocationname;
                            }
                        },
                        {field: 'batchno', title: '批次号', width: 100},
                        {field: 'produceddate', title: '生产日期', width: 80},
                        {field: 'deadline', title: '截止日期', width: 80},
                        {
                            field: 'existingnum', title: '现存量', width: 60, align: 'right', sortable: true,
                            formatter: function (value, rowData, rowIndex) {
                                return formatterBigNumNoLen(value);
                            }
                        },
                        {field: 'auxexistingdetail', title: '现存箱数', width: 80, align: 'right', aliascol: 'existingnum'},
                        <security:authorize url="/storage/showStorageSummaryAmount.do">
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
                        </security:authorize>
                        {
                            field: 'usablenum', title: '可用量', width: 60, align: 'right', sortable: true,
                            formatter: function (value, rowData, rowIndex) {
                                return formatterBigNumNoLen(value);
                            }
                        },
                        {field: 'auxusabledetail', title: '可用箱数', width: 80, align: 'right', aliascol: 'usablenum'},
                        <security:authorize url="/storage/showStorageSummaryAmount.do">
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
                        </security:authorize>
                        {
                            field: 'waitnum', title: '待发量', width: 60, align: 'right', sortable: true,
                            formatter: function (value, rowData, rowIndex) {
                                if (value != 0 && rowData.goodsid != null && rowData.goodsid != "") {
                                    return '<a href="javascript:showGoodsWaitBatchInfo(\'' + rowData.goodsid + '\',\'' + rowData.storageid + '\',\'' + rowData.id + '\')">' + formatterBigNumNoLen(value) + '</a>';
                                } else {
                                    return formatterBigNumNoLen(value);
                                }
                            }
                        },
                        {
                            field: 'auxwaitnumdetail', title: '待发箱数', width: 80, align: 'right', aliascol: 'waitnum',
                            formatter: function (value, rowData, rowIndex) {
                                if (rowData.waitnum != 0) {
                                    return value;
                                }
                            }
                        },
                        <security:authorize url="/storage/showStorageSummaryAmount.do">
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
                        </security:authorize>
                        {
                            field: 'allotwaitnum', title: '调拨待发量', width: 80, align: 'right', sortable: true,
                            formatter: function (value, rowData, rowIndex) {
                                return formatterBigNumNoLen(value);
                            }
                        },
                        {
                            field: 'auxallotwaitnumdetail',
                            title: '调拨待发箱数',
                            width: 100,
                            align: 'right',
                            aliascol: 'allotwaitnum',
                            formatter: function (value, rowData, rowIndex) {
                                if (rowData.allotwaitnum != 0) {
                                    return value;
                                }
                            }
                        },
                        {
                            field: 'allotenternum', title: '调拨待入量', width: 80, align: 'right', sortable: true,
                            formatter: function (value, rowData, rowIndex) {
                                return formatterBigNumNoLen(value);
                            }
                        },
                        {
                            field: 'auxallotenternumdetail',
                            title: '调拨待入箱数',
                            width: 100,
                            align: 'right',
                            aliascol: 'allotenternum',
                            formatter: function (value, rowData, rowIndex) {
                                if (rowData.allotenternum != 0) {
                                    return value;
                                }
                            }
                        }
                    ]],
                    url: url,
                    idField: 'id',
                    singleSelect: true,
                    rownumbers: true,
                    onLoadSuccess: function () {
                        setTimeout(function () {
                            $("#storageSummary-tree-table").datagrid('fixDetailRowHeight', index);
                        }, 0);
                    }
                });
                $("#storageSummary-tree-table").datagrid('fixDetailRowHeight', index);
            },
            onDblClickRow: function (rowIndex, rowData) {
                var goodsid = "";
                if (rowData.goodsid != null) {
                    goodsid = rowData.goodsid;
                }
                var flag = false;
                $(".groupcols").each(function () {
                    if ($(this).attr("checked")) {
                        var val = $(this).val();
                        if (val == "storageid") {
                            flag = true;
                        }
                    }
                });
                var url = "storage/showStorageSummaryLogPage.do?goodsid=" + goodsid;
                if (flag) {
                    var storageid = rowData.storageid;
                    url = "storage/showStorageSummaryLogPageByStorage.do?goodsid=" + goodsid + "&storageid=" + storageid;
                }
                $('#storageSummaryLog-div').dialog({
                    title: '商品库存量追踪日志',
                    width: 800,
                    height: 400,
                    collapsible: false,
                    minimizable: false,
                    maximizable: true,
                    resizable: true,
                    closed: true,
                    cache: false,
                    maximized: true,
                    href: url,
                    modal: true
                });
                $('#storageSummaryLog-div').dialog("open");
            },
            onLoadSuccess: function () {
                var footerrows = $(this).datagrid('getFooterRows');
                if (null != footerrows && footerrows.length > 0) {
                    SMR_footerobject = footerrows[0];
                    checkTotalAmount();
                }
            },
            onCheckAll: function () {
                checkTotalAmount();
            },
            onUncheckAll: function () {
                checkTotalAmount();
            },
            onCheck: function () {
                checkTotalAmount();
            },
            onUncheck: function () {
                checkTotalAmount();
            }
        });
        //通用查询组建调用
        $("#storageSummaryByStorage-query").advancedQuery({
            //查询针对的表
            name: 't_base_goods_info',
            //查询针对的表格id
            datagrid: 'storageSummary-tree-table'
        });
        $("#storageSummaryByStorage-storageid").widget({
            name: 't_storage_summary',
            col: 'storageid',
            singleSelect: false,
            width: 130,
            view: true
        });
        $("#storageSummaryByStorage-supplierid").widget({
            referwid: 'RL_T_BASE_BUY_SUPPLIER',
            width: 200,
            singleSelect: false
        });
        //商品档案通用控件
        $("#storageSummary-goodsid").widget({
            referwid: 'RL_T_BASE_GOODS_INFO',
            singleSelect: false,
            width: 200
        });
        $("#storageSummary-brandid").widget({
            referwid: 'RL_T_BASE_GOODS_BRAND',
            singleSelect: false,
            width: 150,
            view: true
        });
        //商品分类
        $("#storageSummary-goodssort").widget({
            referwid: 'RL_T_BASE_GOODS_WARESCLASS',
            singleSelect: false,
            width: 150,
            view: true,
            onlyLeafCheck: false
        });

        $("#storageSummary-export-queryList").click(function () {
            //封装查询条件
            var objecr = $("#storageSummary-tree-table").datagrid("options");
            var queryParam = $("#storageSummary-form-query").serializeJSON();
            if (null != objecr.sortName && null != objecr.sortOrder) {
                queryParam["sort"] = objecr.sortName;
                queryParam["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryParam);
            var url = "storage/exportSummaryByStorageData.do";
            exportByAnalyse(queryParam, "库存明细表", "storageSummary-tree-table", url);
        });

        //明细导出
        $("#storageSummary-exportdetail-queryList").Excel('export', {
            queryForm: "#storageSummary-form-query",
            type: 'exportUserdefined',
            name: '库存明细表商品明细',
            url: 'storage/exportSummaryByStorageDetailData.do'
        });
        $("#storageSummary-queay-queryList").click(function () {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#storageSummary-form-query").serializeJSON();
            //把form表单的name序列化成JSON对象
            $("#storageSummary-tree-table").datagrid({
                url: 'storage/showStorageSummaryList.do',
                pageNumber: 1,
                queryParams: queryJSON
            });
            reloadColumn();
        });
        $("#storageSummary-queay-reloadList").click(function () {
            $("#storageSummary-goodsid").widget("clear");
            $("#storageSummary-brandid").widget("clear");
            $("#storageSummary-goodssort").widget("clear");
            $("#storageSummaryByStorage-storageid").widget("clear");
            $("#storageSummaryByStorage-supplierid").widget("clear");
            $("#storageSummary-form-query")[0].reset();
            $("#storageSummary-tree-table").datagrid('loadData', {total: 0, rows: [], footer: []});

            $("#storageSummary-tree-table").datagrid('hideColumn', "storageid");
        });
    });

    function checkTotalAmount() {
        var rows = $("#storageSummary-tree-table").datagrid('getChecked');
        var existingnum = 0, existingauxint = 0, existingauxnum = 0, auxexistingdetail = '', existingamount = 0, costamount = 0,
            usablenum = 0, usableauxint = 0, usableauxnum = 0, auxusabledetail = '', usableamount = 0,
            waitnum = 0, waitauxint = 0, waitauxnum = 0, auxwaitdetail = '', waitamount = 0,
            transitnum = 0, transitauxint = 0, transitauxnum = 0, auxtransitdetail = '',
            allotwaitnum = 0, allotwaitauxint = 0, allotwaitauxnum = 0, auxallotwaitdetail = '',
            allotenternum = 0, allotenterauxint = 0, allotenterauxnum = 0, auxallotenterdetail = '',
            projectedusablenum = 0, projectedusableauxint = 0, projectedusableauxnum = 0, auxprojectedusabledetail = '',
            safenum = 0, safeauxint = 0, safeauxnum = 0, auxsafedetail = '', basesaleamount = 0;
        for (var i = 0; i < rows.length; i++) {
            existingnum = Number(existingnum) + Number(rows[i].existingnum == undefined ? 0 : rows[i].existingnum);
            existingauxint = Number(existingauxint) + Number(rows[i].existingauxint == undefined ? 0 : rows[i].existingauxint);
            existingauxnum = Number(existingauxnum) + Number(rows[i].existingauxnum == undefined ? 0 : rows[i].existingauxnum);
            existingamount = Number(existingamount) + Number(rows[i].existingamount == undefined ? 0 : rows[i].existingamount);
            costamount = Number(costamount) + Number(rows[i].costamount == undefined ? 0 : rows[i].costamount);

            usablenum = Number(usablenum) + Number(rows[i].usablenum == undefined ? 0 : rows[i].usablenum);
            usableauxint = Number(usableauxint) + Number(rows[i].usableauxint == undefined ? 0 : rows[i].usableauxint);
            usableauxnum = Number(usableauxnum) + Number(rows[i].usableauxnum == undefined ? 0 : rows[i].usableauxnum);
            usableamount = Number(usableamount) + Number(rows[i].usableamount == undefined ? 0 : rows[i].usableamount);

            waitnum = Number(waitnum) + Number(rows[i].waitnum == undefined ? 0 : rows[i].waitnum);
            waitauxint = Number(waitauxint) + Number(rows[i].waitauxint == undefined ? 0 : rows[i].waitauxint);
            waitauxnum = Number(waitauxnum) + Number(rows[i].waitauxnum == undefined ? 0 : rows[i].waitauxnum);
            waitamount = Number(waitamount) + Number(rows[i].waitamount == undefined ? 0 : rows[i].waitamount);

            transitnum = Number(transitnum) + Number(rows[i].transitnum == undefined ? 0 : rows[i].transitnum);
            transitauxint = Number(transitauxint) + Number(rows[i].transitauxint == undefined ? 0 : rows[i].transitauxint);
            transitauxnum = Number(transitauxnum) + Number(rows[i].transitauxnum == undefined ? 0 : rows[i].transitauxnum);

            allotwaitnum = Number(allotwaitnum) + Number(rows[i].allotwaitnum == undefined ? 0 : rows[i].allotwaitnum);
            allotwaitauxint = Number(allotwaitauxint) + Number(rows[i].allotwaitauxint == undefined ? 0 : rows[i].allotwaitauxint);
            allotwaitauxnum = Number(allotwaitauxnum) + Number(rows[i].allotwaitauxnum == undefined ? 0 : rows[i].allotwaitauxnum);

            allotenternum = Number(allotenternum) + Number(rows[i].allotenternum == undefined ? 0 : rows[i].allotenternum);
            allotenterauxint = Number(allotenterauxint) + Number(rows[i].allotenterauxint == undefined ? 0 : rows[i].allotenterauxint);
            allotenterauxnum = Number(allotenterauxnum) + Number(rows[i].allotenterauxnum == undefined ? 0 : rows[i].allotenterauxnum);

            projectedusablenum = Number(projectedusablenum) + Number(rows[i].projectedusablenum == undefined ? 0 : rows[i].projectedusablenum);
            projectedusableauxint = Number(projectedusableauxint) + Number(rows[i].projectedusableauxint == undefined ? 0 : rows[i].projectedusableauxint);
            projectedusableauxnum = Number(projectedusableauxnum) + Number(rows[i].projectedusableauxnum == undefined ? 0 : rows[i].projectedusableauxnum);

            safenum = Number(safenum) + Number(rows[i].safenum == undefined ? 0 : rows[i].safenum);
            safeauxint = Number(safeauxint) + Number(rows[i].safeauxint == undefined ? 0 : rows[i].safeauxint);
            safeauxnum = Number(safeauxnum) + Number(rows[i].safeauxnum == undefined ? 0 : rows[i].safeauxnum);
            basesaleamount = Number(basesaleamount) + Number(rows[i].basesaleamount == undefined ? 0 : rows[i].basesaleamount);
        }
        auxexistingdetail = existingauxint + '箱' + existingauxnum;
        auxusabledetail = usableauxint + '箱' + usableauxnum;
        auxwaitdetail = waitauxint + '箱' + Math.abs(waitauxnum);
        auxtransitdetail = transitauxint + '箱' + Math.abs(transitauxnum);
        auxallotwaitdetail = allotwaitauxint + '箱' + Math.abs(allotwaitauxnum);
        auxallotenterdetail = allotenterauxint + '箱' + Math.abs(allotenterauxnum);
        auxprojectedusabledetail = projectedusableauxint + '箱' + Math.abs(projectedusableauxnum);
        auxsafedetail = safeauxint + '箱' + Math.abs(safeauxnum);

        var foot = [{
            goodsname: '选中合计',
            existingnum: existingnum,
            auxexistingdetail: auxexistingdetail,
            existingamount: existingamount,
            costamount: costamount,
            usablenum: usablenum,
            auxusabledetail: auxusabledetail,
            usableamount: usableamount,
            waitnum: waitnum,
            auxwaitdetail: auxwaitdetail,
            waitamount: waitamount,
            transitnum: transitnum,
            auxtransitdetail: auxtransitdetail,
            allotwaitnum: allotwaitnum,
            auxallotwaitdetail: auxallotwaitdetail,
            basesaleamount: basesaleamount,
            allotenternum: allotenternum,
            auxallotenterdetail: auxallotenterdetail,
            projectedusablenum: projectedusablenum,
            auxprojectedusabledetail: auxprojectedusabledetail,
            safenum: safenum,
            auxsafedetail: auxsafedetail
        }];
        if (null != SMR_footerobject) {
            foot.push(SMR_footerobject);
        }
        $("#storageSummary-tree-table").datagrid("reloadFooter", foot);
    }

    function showGoodsWaitInfo(goodsid, storageid) {
        var flag = false;
        $(".groupcols").each(function () {
            if ($(this).attr("checked")) {
                var val = $(this).val();
                if (val == "storageid") {
                    flag = true;
                }
            }
        });
        var url = 'storage/showGoodsWaitSaleListPage.do?goodsid=' + goodsid;
        if (flag) {
            url = 'storage/showGoodsWaitSaleListPage.do?goodsid=' + goodsid + "&storageid=" + storageid;
        }
        $("#storageSummary-waitnum-div").dialog({
            title: '商品:' + goodsid + '待发明细列表',
            width: 880,
            height: 400,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: false,
            cache: false,
            href: url,
            modal: true
        });
    }

    //显示调拨待发数据
    function showGoodsAllocateOutWaitInfo(goodsid, storageid) {
        var flag = false;
        $(".groupcols").each(function () {
            if ($(this).attr("checked")) {
                var val = $(this).val();
                if (val == "storageid") {
                    flag = true;
                }
            }
        });
        var url = 'storage/showGoodsAllocateOutWaitListPage.do?goodsid=' + goodsid;
        if (flag) {
            url = 'storage/showGoodsAllocateOutWaitListPage.do?goodsid=' + goodsid + "&storageid=" + storageid;
        }
        $("#sstorageSummary-allocateOutWaitnum-div").dialog({
            title: '商品:' + goodsid + '调拨待发明细列表',
            width: 880,
            height: 400,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: false,
            cache: false,
            href: url,
            modal: true
        });
    }

    function showGoodsWaitBatchInfo(goodsid, storageid, summarybatchid) {
        var flag = false;
        $(".groupcols").each(function () {
            if ($(this).attr("checked")) {
                var val = $(this).val();
                if (val == "storageid") {
                    flag = true;
                }
            }
        });
        var url = 'storage/showGoodsWaitSaleListPage.do?goodsid=' + goodsid + '&summarybatchid=' + summarybatchid;
        if (flag) {
            url = 'storage/showGoodsWaitSaleListPage.do?goodsid=' + goodsid + "&storageid=" + storageid + '&summarybatchid=' + summarybatchid;
        }
        $("#storageSummary-waitnum-div").dialog({
            title: '商品:' + goodsid + '待发明细列表',
            width: 680,
            height: 400,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: false,
            cache: false,
            href: url,
            modal: true
        });
    }
    //重置字段列
    function reloadColumn() {
        var flag = false;
        $(".groupcols").each(function () {
            if ($(this).attr("checked")) {
                var val = $(this).val();
                if (val == "storageid") {
                    flag = true;
                }
            }
        });
        if (flag) {
            $("#storageSummary-tree-table").datagrid('showColumn', "storageid");
        } else {
            $("#storageSummary-tree-table").datagrid('hideColumn', "storageid");
        }
    }


    setTimeout(function () {
        loadData = true;
    }, 100);
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "summarydetail-dialog-print",
            code: "storage_summarydetail",
            queryForm: "storageSummary-form-query",
            url_preview: "print/storage/storageSummaryDetailPrintView.do",
            url_print: "print/storage/storageSummaryDetailPrint.do",
            btnPreview: "storage-buttons-storageSummary-PrintView",
            btnPrint: "storage-buttons-storageSummary-Print"
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
