<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>拆分单页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<style type="text/css">
    .len70 {
        width: 70px;
    }
    .len90 {
        width: 90px;
    }
    .len200 {
        width: 200px;
    }
    .len388 {
        width: 260px;
    }
    .len646 {
        width: 646px;
    }
</style>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-splitPage"></div>
        <input type="hidden" id="storage-backid-splitPage" value="${param.id }"/>
    </div>
    <div data-options="region:'center',border:false">
        <div id="storage-panel-splitPage">
        </div>
    </div>
</div>
<script type="text/javascript">
    var usablenums = {};
    var storageMap = {};
    var type = '${param.type }' || 'view';
    var url = 'storage/splitmerge/splitEditPage.do';
    if (type == 'add') {
        url = 'storage/splitmerge/splitEditPage.do?type=${param.type }&id=${param.id }';
    } else if (type == 'edit') {
        url = 'storage/splitmerge/splitEditPage.do?type=${param.type }&id=${param.id }';
    } else if (type == 'view') {
        url = 'storage/splitmerge/splitViewPage.do?type=${param.type }&id=${param.id }';
    }
    $(function () {
        $('#storage-panel-splitPage').panel({
            href: url,
            cache: false,
            maximized: true,
            border: false,
            onLoad: function () {
                // 商品
                $goodsid.widget({
                    required: false,
                    referwid: 'RL_T_BASE_GOODS_INFO',
                    width: 260,
                    onSelect: function (data) {
                        $goodsid2.val(data.id);
                    },
                    onClear: function () {
                        $goodsid2.val('');
                    }
                });
                // BOM
                $bomid.widget({
                    referwid: 'RL_T_BASE_BOM',
                    width: 388,
                    required: true,
                    onSelect: function (data) {
                        // 商品
                        $goodsid.widget({
                            required: false,
                            referwid: 'RL_T_BASE_GOODS_INFO',
                            width: 260,
                            param: [
                                {field: 'id', op: 'equal', value: data.goodsid}
                            ],
                            onSelect: function (data) {
                                $goodsid2.val(data.id);
                            },
                            onClear: function () {
                                $goodsid2.val('');
                            }
                        });
                        $goodsid.widget('setValue', data.goodsid);
                        $goodsid.widget('readonly', true);
                        $goodsid.widget('setWidth', 260);
                        $goodsid2.html('　商品编号：<font color="#080">' + data.goodsid + '</font>');
                        $barcode.val(data.barcode);
                        switchBatch();
                        <%-- // 商品取价：先核算成本价，再最新采购价。通用版禁用核算成本价 --%>
                        $price.numberbox('setValue', /*data.costaccountprice || */data.newstorageprice);
                        loading('加载中...');
                        // 获取商品的库存
                        $.ajax({
                            type: 'post',
                            url: 'storage/showStorageSummaryList.do',
                            data: {goodsid: data.goodsid, groupcols: 'storageid,goodsid', page: 1, rows: 9999},
                            dataType: 'json',
                            success: function (json) {
                                loaded();
                                var storages = json.rows;
                                var storageids = new Array();
                                for (var i in storages) {
                                    var storage = storages[i];
                                    storageids.push(storage.storageid);
                                    usablenums[data.goodsid + ',' + storage.storageid] = storage.usablenum;
                                }
                                $storageid.widget({
                                    referwid: 'RL_T_BASE_STORAGE_INFO',
                                    width: 130,
                                    required: true,
                                    param: [{field: 'id', op: 'in', value: storageids.join(',')}],
                                    onSelect: function (data) {
                                        $summarybatchid.val('');
                                        $storagelocationid.val('');
                                        $produceddate.val('');
                                        switchBatch();
                                        // 替换明细中的仓库
                                        var rows = $detail.datagrid('getRows');
                                        for (var i in rows) {
                                            var row = rows[i];
                                            if ((row.storageid || '') == '' && (row.goodsid || '') != '') {
                                                row.storageid = data.id;
                                                row.storagename = data.name;
                                                row.storageisbatch = data.isbatch;
                                            }
                                        }
                                        $detail.datagrid('loadData', rows);
                                    }
                                });
                                $.ajax({
                                    type: 'post',
                                    url: 'basefiles/selectBomDetailList.do',
                                    data: {billid: data.id, type: 2, page: 1, rows: 9999},
                                    dataType: 'json',
                                    success: function (json2) {
                                        loaded();
                                        var rows = json2.rows || new Array();
                                        for (var i in rows) {
                                            var row = rows[i];
                                            // unitnum -> rate
                                            row.rate = row.unitnum;
                                            delete row.unitnum;
                                        }
                                        $detail.datagrid('loadData', rows);
                                        refreshDetailTotalnum();
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert('提醒', '<font color="#f00>出错了。</font>')
                                    }
                                })
                            },
                            error: function () {
                                loaded();
                                $.messager.alert('提醒', '<font color="#f00>出错了。</font>')
                            }
                        })
                    },
                    onClear: function () {
                        switchBatch();
                    }
                });
                // 仓库
                $storageid.widget({
                    referwid: 'RL_T_BASE_STORAGE_INFO',
                    width: 130,
                    required: true,
                    // initValue: '${splitmerge.storageid }',
                    onSelect: function (data) {
                        switchBatch();
                        // 替换明细中的仓库
                        var rows = $detail.datagrid('getRows');
                        for (var i in rows) {
                            var row = rows[i];
                            if ((row.storageid || '') == '' && (row.goodsid || '') != '') {
                                row.storageid = data.id;
                                row.storagename = data.name;
                                row.storageisbatch = data.isbatch;
                            }
                        }
                        $detail.datagrid('loadData', rows);
                    },
                    onClear: function () {
                        switchBatch();
                    }
                });
                // 明细
                $detail.datagrid({
                    columns: [[
                        {field: 'id', title: 'id', hidden: true},
                        {field: 'goodsid', title: '商品编码', width: 70},
                        {field: 'goodsname', title: '商品名称', width: 230},
                        {field: 'barcode', title: '条形码', width: 100},
                        {
                            field: 'storageid', title: '入库仓库', width: 150,
                            formatter: function (value, row, index) {
                                if ((storageMap[value] || '') == '') {
                                    return row.storagename;
                                }
                                return storageMap[value];
                            },
                            editor: {
                                type: 'widget',
                                options: {
                                    referwid: 'RL_T_BASE_STORAGE_INFO',
                                    singleSelect: true,
                                    onlyLeafCheck: false,
                                    required: true,
                                    width: 142,
                                    onSelect: function (data) {
                                        var storageid = data.id;
                                        if ((storageMap[storageid] || '') == '') {
                                            storageMap[storageid] = data.name;
                                        }
                                        var editor1 = $detail.datagrid('getEditor', {
                                            index: retDetailEditIndex,
                                            field: 'isbatch'
                                        });
                                        $(editor1.target).html(data.storageisbatch);
                                    }
                                }
                            }
                        },
                        {
                            field: 'price', title: '单价', width: 80, align: 'right',
                            formatter: function (value, row, index) {
                                return formatterMoney(value, 6);
                            },
                            editor: {
                                type: 'numberbox',
                                options: {
                                    precision: 6,
                                    required: true,
                                    <c:if test="${editPrice ne '1'}">readonly: true,</c:if>
                                    onChange: computeDetailTotalamount
                                }
                            }
                        },
                        {
                            field: 'rate', title: '每份数量', width: 80, align: 'right',
                            formatter: function (value, row, index) {
                                if ((value || '') == '') {
                                    return '';
                                }
                                return parseFloat(row.rate || 0).toFixed(0);
                            }
                        },
                        {
                            field: 'unitnum', title: '总数量', width: 90, align: 'right',
                            formatter: function (value, row, index) {
                                return formatterNum(value);
                            },
                            editor: {
                                type: 'span'
                            }
                        },
                        {
                            field: 'totalamount', title: '金额', width: 120, align: 'right',
                            formatter: function (value, row, index) {
                                if ((value || '') != '') {
                                    return formatterMoney(value);
                                }
                                if ((row.goodsid || '') == '') {
                                    return '';
                                }
                                if ((row.unitnum || '') == '') {
                                    return '';
                                }
                                var totalamount = formatterMoney((parseFloat(row.price || 0) * parseFloat(row.unitnum)), 2);
                                row.totalamount = totalamount;
                                return totalamount;
                            },
                            editor: {
                                type: 'numberbox',
                                options: {
                                    precision: 2,
                                    disabled: true
                                }
                            }
                        },
                        {field: 'remark', title: '备注', width: 160}
                    ]],
                    border: true,
                    fit: true,
                    rownumbers: true,
                    showFooter: true,
                    singleSelect: true,
                    <c:if test="${param.type ne 'view' }">
                    onDblClickRow: beginEditDetail,
                    onClickRow: endEditDetail,
                    </c:if>
                    data: $.parseJSON('${list }'),
                    onLoadSuccess: function (data) {
                        if (data.total == 0) {
                            $detail.datagrid('appendRow', {});
                            $detail.datagrid('appendRow', {});
                            $detail.datagrid('appendRow', {});
                            $detail.datagrid('appendRow', {});
                            $detail.datagrid('appendRow', {});
                            $detail.datagrid('appendRow', {});
                            $detail.datagrid('appendRow', {});
                            $detail.datagrid('appendRow', {});
                            $detail.datagrid('appendRow', {});
                            $detail.datagrid('appendRow', {});
                            computeSumData();
                            return true;
                        }
                        if ((data.rows[data.rows.length - 1].goodsid || '') == '') {
                            computeSumData();
                            return true;
                        }
                        //$detail.datagrid('appendRow', {});
                        computeSumData();
                        return true;
                    }
                });
                $goodsid.widget('setWidth', 260);
                $bomid.widget('setWidth', 388);
                $storageid.widget('setWidth', 130);
                switchBatch();
            }
        }); // panel end
        $('#storage-buttons-splitPage').buttonWidget({
            type: '${param.type }',
            model: 'bill',
            tab: '拆分单列表',
            taburl: '/storage/splitmerge/splitListPage.do',
            id: '${param.id}',
            datagrid: 'storage-datagrid-splitListPage',
            initButton: [
                <security:authorize url="/storage/splitmerge/editSplit.do">
                {
                    type: 'button-save',
                    handler: function () {
                        saveSplit();
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/splitmerge/addSplit.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addTab('storage/splitmerge/splitPage.do?type=add', '拆分单新增');
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/splitmerge/auditSplit.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        if ('${splitmerge.status }' == '3') {
                            $.messager.alert('提醒', '该单据已经审核！');
                            return false;
                        }
                        if (!checkUsablenum()) {
                            return false;
                        }
                        loading('审核中...');
                        $.ajax({
                            type: 'post',
                            url: 'storage/splitmerge/auditSplit.do',
                            data: {id: '${splitmerge.id }'},
                            dataType: 'json',
                            success: function (json) {
                                loaded();
                                if (json.flag) {
                                    $.messager.alert('提醒', '审核成功。');
                                    var win = tabsWindowURL('/storage/splitmerge/splitListPage.do');
                                    try {
                                        if (win != null) {
                                            win.$('#storage-datagrid-splitListPage').datagrid('reload');
                                        }
                                    } catch (e) {
                                    }
                                    window.location.href = 'storage/splitmerge/splitPage.do?type=view&id=${splitmerge.id }';
                                    return true;
                                }
                                $.messager.alert('提醒', json.msg || '审核失败！');
                                return true;
                            },
                            error: function () {
                                $.messager.alert('提醒', '操作失败！');
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/splitmerge/deleteSplit.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        if ('${splitmerge.status }' == '3') {
                            $.messager.alert('提醒', '该单据已经审核，无法删除！');
                            return false;
                        }
                        $.messager.confirm('提醒', '是否删除该记录？', function (r) {
                            if (r) {
                                loading('删除中...');
                                $.ajax({
                                    type: 'post',
                                    url: 'storage/splitmerge/deleteSplit.do',
                                    data: {id: '${splitmerge.id }'},
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert('提醒', '删除成功。');
                                            var win = tabsWindowURL('/storage/splitmerge/splitListPage.do');
                                            try {
                                                if (win != null) {
                                                    win.$('#storage-datagrid-splitMergeListPage').datagrid('reload');
                                                }
                                            } catch (e) {
                                            }
                                            top.closeNowTab();
                                            return true;
                                        }
                                        $.messager.alert('提醒', json.msg || '删除失败！');
                                        return true;
                                    },
                                    error: function () {
                                        $.messager.alert('提醒', '操作失败！');
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/splitmerge/backSplit.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        $backid.val(data.id);
                        if (data.status == '3') {
                            window.location.href = 'storage/splitmerge/splitPage.do?type=view&id=' + data.id;
                            return true;
                        }
                        window.location.href = 'storage/splitmerge/splitPage.do?type=edit&id=' + data.id;
                        return true;
                        // refreshPanel('sales/orderEditPage.do?id='+ data.id);
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/splitmerge/nextSplit.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        $backid.val(data.id);
                        if (data.status == '3') {
                            window.location.href = 'storage/splitmerge/splitPage.do?type=view&id=' + data.id;
                            return true;
                        }
                        window.location.href = 'storage/splitmerge/splitPage.do?type=edit&id=' + data.id;
                        return true;
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/splitmerge/previewSplit.do">
                {
                    type: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/splitmerge/printSplit.do">
                {
                    type: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}
            ]
        });
        $('#storage-buttons-splitPage').buttonWidget('enableButton', 'button-add');
        $('#storage-buttons-splitPage').buttonWidget('enableButton', 'button-back');
        $('#storage-buttons-splitPage').buttonWidget('enableButton', 'button-next');
        <c:choose>
        <c:when test="${splitmerge.status eq '2'}">
        $('#storage-buttons-splitPage').buttonWidget('enableButton', 'button-audit');
        $('#storage-buttons-splitPage').buttonWidget('enableButton', 'button-delete');
        </c:when>
        <c:otherwise>
        $('#storage-buttons-splitPage').buttonWidget('disableButton', 'button-audit');
        $('#storage-buttons-splitPage').buttonWidget('disableButton', 'button-delete');
        </c:otherwise>
        </c:choose>
        <c:choose>
        <c:when test="${splitmerge.status eq '3'}">
        $('#storage-buttons-splitPage').buttonWidget('disableButton', 'button-save');
        $('#storage-buttons-splitPage').buttonWidget('disableButton', 'button-delete');
        </c:when>
        <c:otherwise>
        $('#storage-buttons-splitPage').buttonWidget('enableButton', 'button-save');
        $('#storage-buttons-splitPage').buttonWidget('enableButton', 'button-delete');
        </c:otherwise>
        </c:choose>
    });
    /**
     * 判断可用量
     * @param newValue
     * @param oldValue
     */
    function checkUsablenum() {
        return true;
        var bomid = $bomid.widget('getValue') || '';
        var goodsid = $goodsid.widget('getValue') || '';
        var storageid = $storageid.widget('getValue') || '';
        var batchid = $batchid.widget('getValue') || '';
        var goodsBatch = ($goodsid.widget('getObject') || {}).isbatch == '1';
        if (bomid == '') {
            $.messager.alert('提醒', '请选择BOM！');
            $unitnum.numberbox('clear');
            return false;
        }
        if (storageid == '') {
            $.messager.alert('提醒', '请选择仓库！');
            $unitnum.numberbox('clear');
            return false;
        }
        if (batchid == '' && goodsBatch) {
            $.messager.alert('提醒', '请选择批次！');
            $unitnum.numberbox('clear');
            return false;
        }
        var newValue = $unitnum.numberbox('getValue');
        var usablenum = parseFloat(usablenums[goodsid + ',' + storageid] || '0');
        if (goodsBatch && goodsid != batchid) {
            usablenum = parseFloat(usablenums[goodsid + ',' + storageid + ',' + batchid] || '0');
        }
        if (usablenum < parseFloat(newValue)) {
            <%--
            $.messager.alert('提醒', '数量超过可用量，该' + (goodsBatch ? '批次' : '商品') + '在仓库[' + $storageid.widget('getText') + ']中的可用量不能超过' + parseInt(usablenum));
            --%>
            $.messager.alert('提醒', '数量超过可用量，该' + (goodsBatch ? '批次' : '商品') + '在仓库[' + $storageid.widget('getText') + ']中的可用量不足！');
            $unitnum.numberbox('clear');
            return false;
        }
        return true;
    }
    /**
     * 计算金额
     */
    function computeTotalamount() {
        var price = $price.numberbox('getValue') || '0';
        var unitnum = $unitnum.numberbox('getValue') || '0';
        var totalamount = parseFloat(price) * parseFloat(unitnum);
        $totalamount.numberbox('setValue', totalamount);
        computeSumData();
        return true;
    }
    /**
     * 刷新明细总数量
     */
    function refreshDetailTotalnum() {
        if (retDetailEditIndex() >= 0) {
            return false;
        }
        var base = $unitnum.numberbox('getValue') || 0;
        var rows = $detail.datagrid('getRows');
        for (var i in rows) {
            var row = rows[i];
            if ((row.goodsid || '') == '') {
                continue;
            }
            var rate = row.rate || 0;
            var unitnum = parseFloat(base) * parseFloat(rate);
            row.unitnum = unitnum.toFixed(0);
            row.totalamount = (parseFloat(unitnum) * parseFloat(row.price || 0)).toFixed(6);
        }
        $detail.datagrid('loadData', rows);
    }
    /**
     * 计算明细金额
     */
    function computeDetailTotalamount() {
        var index = retDetailEditIndex();
        if (index < 0) {
            return false;
        }
        var editor1 = $detail.datagrid('getEditor', {index: index, field: 'price'});
        var editor2 = $detail.datagrid('getEditor', {index: index, field: 'unitnum'});
        var editor3 = $detail.datagrid('getEditor', {index: index, field: 'totalamount'});
        var price = $(editor1.target).numberbox('getValue') || '0.0000';
        var totalnum = $(editor2.target).text() || '0';
        var totalamount = parseFloat(price) * parseFloat(totalnum);
        $(editor3.target).numberbox('setValue', totalamount);
    }
    /**
     * 开始编辑datagrid
     * @returns {boolean}
     */
    function beginEditDetail() {
        var row = $detail.datagrid('getSelected');
        var goodsid = row.goodsid || '';
        if (goodsid == '') {
            return false;
        }
        var index = retDetailEditIndex();
        if (index >= 0) {
            $.messager.alert('提醒', '当前明细正在编辑！');
            return false;
        }
        var index = $detail.datagrid('getRowIndex', row);
        $detail.datagrid('beginEdit', index);
        disableTitle();
    }
    /**
     * 结束编辑datagrid
     */
    function endEditDetail() {
        var editIndex = retDetailEditIndex();
        if (editIndex < 0) {
            return true;
        }
        var valid = $detail.datagrid('validateRow', retDetailEditIndex());
        if (valid) {
            $detail.datagrid('endEdit', retDetailEditIndex());
            computeSumData();
            enableTitle();
        }
    }
    // 获取目前Datagrid处于编辑的行号，返回-1时，表明当前Datagrid未处于编辑状态
    function retDetailEditIndex() {
        var rows = $detail.datagrid('getRows');
        for (var i = 0; i < rows.length; i++) {
            var editors = $detail.datagrid('getEditors', i);
            if (editors.length > 0) {
                return i;
            }
        }
        return -1;
    }
    /**
     *
     */
    function disableTitle() {
        $bomid.widget('disable');
        $bomid.widget('readonly', true);
        $bomid.widget('setWidth', 388);
        $storageid.widget('disable');
        $storageid.widget('readonly', true);
        $storageid.widget('setWidth', 130);
        $price.numberbox('disable');
        $unitnum.numberbox('disable');
        return true;
    }
    /**
     *
     * @param a
     * @returns {boolean}
     */
    function enableTitle(a) {
        $bomid.widget('enable');
        $bomid.widget('readonly', false);
        $storageid.widget('enable');
        $storageid.widget('readonly', false);
        $price.numberbox('enable');
        $unitnum.numberbox('enable');
        return true;
    }
    /**
     * 切换批次
     */
    function switchBatch() {
        <%-- 查看的场合，批次不显示参照窗口 --%>
        <c:if test="${param.type eq 'view' }">
        return true;
        </c:if>
        var goodsid = $goodsid.widget('getValue') || '';
        var storageid = $storageid.widget('getValue') || '';
        var oldBatchid = $batchid.widget('getValue') || $batchid.val();
        var goodsBatch = ($goodsid.widget('getObject') || {}).isbatch || '';            // 商品批次管理
//            var storageBatch = ($storageid.widget('getObject') || [{}])[0].isbatch || ''; // 仓库批次管理
        var storageBatch = '1';                                                         // 仓库批次管理 暂时默认为1
        $batchid.widget({
//                referwid: 'RL_T_STORAGE_BATCH_LIST',
            referwid: 'RL_T_STORAGE_BATCH_LIST2',
            width: 130,
            required: (goodsBatch == '1') && (storageBatch == '1'),
            param: [
                {field: 'goodsid', op: 'equal', value: goodsid},
                {field: 'storageid', op: 'equal', value: storageid}
            ],
            initValue: oldBatchid,
            onSelect: function (data) {
                usablenums[goodsid + ',' + storageid + ',' + data.batchno] = data.usablenum;
                $summarybatchid.val(data.id);
                $storagelocationid.val(data.storagelocationid);
                $produceddate.val(data.produceddate);
                return true;
                if ((data.produceddate || '') != '') {
                    loading('设定中...');
                    $.ajax({
                        type: 'post',
                        url: 'storage/getBatchno.do',
                        data: {
                            produceddate: data.produceddate || new Date().Format('yyyy-MM-dd'),
                            goodsid: $goodsid.widget('getValue')
                        },
                        dataType: 'json',
                        success: function (json) {
                            loaded();
                            $deadline.val(json.deadline);
                        }
                    });
                }
            }
        });
        if (goodsid == '' || storageid == '' || goodsBatch != '1' || storageBatch != '1') {
            $batchid.widget('readonly', true);
            $batchid.widget('setWidth', 130);
            $batchid.widget('clear');
            return true;
        }
        return true;
    }
    /**
     * 合计数量、金额
     */
    function computeSumData(e) {
        var rows = $detail.datagrid('getRows');
        var unitnum = 0;        // 总数量
        var totalamount = 0;    // 金额
        for (var i in rows) {
            var row = rows[i];
            unitnum = parseFloat(unitnum) + parseFloat(row.unitnum || '0');
            totalamount = parseFloat(totalamount) + parseFloat(row.unitnum || '0') * parseFloat(row.price || '0');
        }
        $detail.datagrid('reloadFooter', [{
            goodsid: ' ',
            goodsname: '合计：' + formatterMoney(totalamount),
            barcode: '损溢金额：' + formatterMoney(totalamount - parseFloat($totalamount.numberbox('getValue')))
        }]);
        return true;
    }
    /**
     * 保存拆分单
     */
    function saveSplit() {
        if (retDetailEditIndex() > 0) {
            $.messager.alert('提醒', '明细正在编辑中，无法保存！');
            return false;
        }
        if (!checkUsablenum()) {
            return false;
        }
        var flag = $form.form('validate');
        if (!flag) {
            return false;
        }
        var rows = $detail.datagrid('getRows');
        <%--
        var errors = new Array();
        for(var i in rows) {
            var row = rows[i];
            if((row.goodsid || '') != '' && (row.price == undefined || row.price == '')) {
                errors.push(parseInt(i) + 1);
            }
        }
        if(errors.length > 0) {
            $.messager.alert('提醒', '以下明细的单价为空：<br/>' + errors.join(','));
            return false;
        }
        --%>
        /*
         loading('获取批次号中...')
         for(var i in rows) {
         var row = rows[i];
         if((row.goodsid || '') == '') {
         continue;
         }
         if(row.isbatch == '1' && row.storageisbatch == '1') {
         $.ajax({
         type: 'post',
         url: 'storage/getBatchno.do',
         data: {goodsid: row.goodsid, produceddate: $produceddate.val()},
         dataType: 'json',
         async: false,
         success: function(json) {
         row.batchid = json.batchno || '';
         row.produceddate = $produceddate.val() || '';
         row.deadline = json.deadline || '';
         },
         error: function() {
         loaded();
         }
         });
         }
         }
         loaded();
         */
        $detaillist.val(JSON.stringify(rows));
        $form.form({
            onSubmit: function () {
                var flag = $(this).form('validate');
                if (!flag) {
                    return false;
                }
                loading("保存中..");
            },
            success: function (data) {
                loaded();
                var json = $.parseJSON(data);
                if (json.flag) {
                    $.messager.alert('提醒', '保存成功。');
                    var win = tabsWindowURL('/storage/splitmerge/splitListPage.do');
                    try {
                        if (win != null) {
                            win.$('#storage-datagrid-splitMergeListPage').datagrid('reload');
                        }
                    } catch (e) {
                    }
                    location.href = 'storage/splitmerge/splitPage.do?type=add&id=' + json.backid;
                } else {
                    $.messager.alert('提醒', json.msg || '保存失败！');
                }
            }
        }).submit();
    }
    /**
     * 打印单据
     */
    function printBill() {
        $.AgReportPrint(AgReportPrint.printToolType,
            {
                webpath: '<%=basePath %>',
                url: 'print/storage/splitmerge/splitMergePrint.do',
                urlparam: {idarrs: '${param.id }'},
                showprintdialog: true,
                afterHandle: function () {
                }
            }
        );
        try {
            var win = tabsWindowURL('/storage/splitmerge/splitListPage.do');
            if (win != null) {
                var rows = win.$('#storage-datagrid-splitListPage').datagrid('getRows');
                var index = win.$('#storage-datagrid-splitListPage').datagrid('getRowIndex', '${param.id }');
                var row = rows[index];
                row.printtimes = parseInt(row.printtimes) + 1;
                win.$('#storage-datagrid-splitListPage').datagrid('updateRow', {index: index, row: row});
            }
        } catch (e) {
        }
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "split-dialog-print",
            code: "storage_split",
            url_preview: "print/storage/splitmerge/splitMergePrintView.do",
            url_print: "print/storage/splitmerge/splitMergePrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            getData: function (tableId, printParam) {
                printParam.idarrs = '${param.id }';
                if ('${splitmerge.printtimes }' > 0)
                    printParam.printIds = [printParam.idarrs];
                return true;
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
