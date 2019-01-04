<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>组装单页面</title>
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
    .readonly-gray {
        background: #EBEBE4;
        color: #545454;
    }
</style>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-mergePage"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="storage-panel-mergePage">
        </div>
    </div>
</div>
<script type="text/javascript">
    var usablenums = {};
    var goodsStorageMap = {};   // goodsid,storageid映射关系
    var goodsBatchMap = {};     // goodsid,batchid映射关系
    var storageMap = {};        // storageid,storage映射{1001: {id: 1001, name: '日化仓', isbatch: '1'}}
    var batchMap = {};          // batchid,batch映射关系{P20151105D365: {}}
    var type = '${param.type }' || 'view';
    var url = 'storage/splitmerge/mergeEditPage.do';
    if (type == 'add') {
        url = 'storage/splitmerge/mergeEditPage.do?type=${param.type }&id=${param.id }';
    } else if (type == 'edit') {
        url = 'storage/splitmerge/mergeEditPage.do?type=${param.type }&id=${param.id }';
    } else if (type == 'view') {
        url = 'storage/splitmerge/mergeViewPage.do?type=${param.type }&id=${param.id }';
    }
    $(function () {
        // 获取明细中的库存
        refreshUsablenum($.parseJSON('${list }'));

        $('#storage-panel-mergePage').panel({
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
                    onLoadSuccess: function () {
                        $isbatch.val(($goodsid.widget('getObject') || {}).isbatch);         // 当前商品是否批次管理
                    },
                    onClear: function () {
                        $goodsid2.val('');
                    }
                });
                $goodsid.widget('readonly', true);
                $goodsid.widget('setWidth', 260);
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
                                $isbatch.val(data.isbatch);
                            },
                            onLoadSuccess: function () {
                                $isbatch.val(($goodsid.widget('getObject') || {}).isbatch);         // 当前商品是否批次管理
                            },
                            onClear: function () {
                                $goodsid2.val('');
                            }
                        });
                        $goodsid.widget('setValue', data.goodsid);
                        $goodsid.widget('readonly', true);
                        $goodsid.widget('setWidth', 260);
                        $isbatch.val(($goodsid.widget('getObject') || {}).isbatch);         // 当前商品是否批次管理
                        $goodsid2.html('　商品编号：<font color="#080">' + data.goodsid + '</font>');
                        $barcode.val(data.barcode);
                        <%-- // 商品取价：先核算成本价，再最新采购价。通用版禁用核算成本价 --%>
                        $price.numberbox('setValue', /*data.costaccountprice || */data.newstorageprice);
                        $storageid.widget({
                            referwid: 'RL_T_BASE_STORAGE_INFO',
                            width: 130,
                            required: true,
                            // param: [{field: 'id', op: 'in', value: storageids.join(',')}],   // 入库不做控制
                            onSelect: function (data) {
                                storageMap[data.id] = data;
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
                                refreshUsablenum(rows);
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
//                            })
//                        }
                });
                // 仓库
                $storageid.widget({
                    referwid: 'RL_T_BASE_STORAGE_INFO',
                    width: 130,
                    required: true,
                    // initValue: '${splitmerge.storageid }',
                    onSelect: function (data) {
                        storageMap[data.id] = data;
                        // 替换明细中的仓库
                        var rows = $detail.datagrid('getRows');
                        for (var i in rows) {
                            var row = rows[i];
                            if ((row.storageid || '') == '' && (row.goodsid || '') != '') {
                                row.storageid = data.id;
                                row.storagename = data.name;
                            }
                        }
                        $detail.datagrid('loadData', rows);
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
                            field: 'storageid', title: '出库仓库', width: 150,
                            formatter: function (value, row, index) {
                                var storageid = goodsStorageMap[row.goodsid] || '';
                                if (storageid != '') {
                                    row.storageid = storageid;
                                }
                                if (storageid == '' || (storageMap[storageid] || '') == '' || (storageMap[storageid].name || '') == '') {
                                    storageMap[value] = {id: value, name: row.storagename, isbatch: row.storageisbatch};
                                    return row.storagename;
                                }
                                row.storageisbatch = storageMap[storageid].isbatch;
                                return storageMap[storageid].name;
                            },
                            editor: {
                                type: 'text'
                            }
                        },
                        {
                            field: 'batchid', title: '批次', width: 130,
                            formatter: function (value, row, index) {
                                var batchid = goodsBatchMap[row.goodsid] || '';
                                if (batchid != '') {
                                    row.batchid = batchid;
                                }
                                if (batchid == '' || (batchMap[batchid] || '') == '' || (batchMap[batchid].batchno || '') == '') {
                                    return row.batchid;
                                }
                                row.summarybatchid = batchMap[batchid].id;
                                row.storagelocationid = batchMap[batchid].storagelocationid;
                                return batchMap[batchid].batchno;
                            },
                            editor: {
                                type: 'text'
                            }
                        },
                        {
                            field: 'produceddate', title: '生产日期', width: 90,
                            editor: {
                                type: 'span'
                            }
                        },
                        {
                            field: 'deadline', title: '截止日期', width: 90,
                            editor: {
                                type: 'span'
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
                            field: 'rate', title: '每份数量', width: 60, align: 'right',
                            formatter: function (value, row, index) {
                                if ((value || '') == '') {
                                    return '';
                                }
                                return parseFloat(row.rate || 0).toFixed(0);
                            }
                        },
                        {
                            field: 'unitnum', title: '总数量', width: 60, align: 'right',
                            formatter: function (value, row, index) {
                                return formatterNum(value);
                            },
                            editor: {
                                type: 'span'
                            }
                        },
                        {
                            field: 'totalamount', title: '金额', width: 100, align: 'right',
                            formatter: function (value, row, index) {
                                if ((row.goodsid || '') == '') {
                                    return '';
                                }
                                if ((row.unitnum || '') == '') {
                                    return '';
                                }
                                if ((value || '') != '') {
                                    return formatterMoney(value);
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
                        computeSumData();
                        return true;
                    }
                });
                $goodsid.widget('setWidth', 260);
                $bomid.widget('setWidth', 388);
                $storageid.widget('setWidth', 130);
            }
        }); // panel end
        $('#storage-buttons-mergePage').buttonWidget({
            type: '${param.type }',
            model: 'bill',
            tab: '组装单列表',
            taburl: '/storage/splitmerge/mergeListPage.do',
            id: '${param.id}',
            datagrid: 'storage-datagrid-mergeListPage',
            initButton: [
                <security:authorize url="/storage/splitmerge/editMerge.do">
                {
                    type: 'button-save',
                    handler: function () {
                        saveMerge();
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/splitmerge/addMerge.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addTab('storage/splitmerge/mergePage.do?type=add', '组装单新增');
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/splitmerge/auditMerge.do">
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
                        loading();
                        $.ajax({
                            type: 'post',
                            url: 'storage/splitmerge/auditMerge.do',
                            data: {id: '${splitmerge.id }'},
                            dataType: 'json',
                            success: function (json) {
                                loaded();
                                if (json.flag) {
                                    $.messager.alert('提醒', '审核成功。');
                                    var win = tabsWindowURL('/storage/splitmerge/mergeListPage.do');
                                    try {
                                        if (win != null) {
                                            win.$('#storage-datagrid-splitListPage').datagrid('reload');
                                        }
                                    } catch (e) {
                                    }
                                    window.location.href = 'storage/splitmerge/mergePage.do?type=view&id=${splitmerge.id }';
                                    return true;
                                }
                                $.messager.alert('提醒', json.msg || '审核失败！');
                                return true;
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/splitmerge/deleteMerge.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        if ('${splitmerge.status }' == '3') {
                            $.messager.alert('提醒', '该单据已经审核，无法删除！');
                            return false;
                        }
                        $.messager.confirm('提醒', '是否删除该组装单', function (r) {
                            if (r) {
                                loading('删除中...');
                                $.ajax({
                                    type: 'post',
                                    url: 'storage/splitmerge/deleteMerge.do',
                                    data: {id: '${splitmerge.id }'},
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert('提醒', '删除成功。');
                                            var win = tabsWindowURL('/storage/splitmerge/mergeListPage.do');
                                            try {
                                                if (win != null) {
                                                    win.$('#storage-datagrid-mergeListPage').datagrid('reload');
                                                }
                                            } catch (e) {
                                            }
                                            top.closeNowTab();
                                            return true;
                                        }
                                        $.messager.alert('提醒', json.msg || '删除失败！');
                                        return true;
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/splitmerge/backMerge.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        $backid.val(data.id);
                        if (data.status == '3') {
                            window.location.href = 'storage/splitmerge/mergePage.do?type=view&id=' + data.id;
                            return true;
                        }
                        window.location.href = 'storage/splitmerge/mergePage.do?type=edit&id=' + data.id;
                        return true;
                        // refreshPanel('sales/orderEditPage.do?id='+ data.id);
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/splitmerge/nextMerge.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        $backid.val(data.id);
                        if (data.status == '3') {
                            window.location.href = 'storage/splitmerge/mergePage.do?type=view&id=' + data.id;
                            return true;
                        }
                        window.location.href = 'storage/splitmerge/mergePage.do?type=edit&id=' + data.id;
                        return true;
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/splitmerge/previewMerge.do">
                {
                    type: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/splitmerge/printMerge.do">
                {
                    type: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}
            ]
        });
        $('#storage-buttons-mergePage').buttonWidget('enableButton', 'button-add');
        $('#storage-buttons-mergePage').buttonWidget('enableButton', 'button-back');
        $('#storage-buttons-mergePage').buttonWidget('enableButton', 'button-next');
        <c:choose>
        <c:when test="${splitmerge.status eq '2'}">
        $('#storage-buttons-mergePage').buttonWidget('enableButton', 'button-audit');
        $('#storage-buttons-mergePage').buttonWidget('enableButton', 'button-delete');
        </c:when>
        <c:otherwise>
        $('#storage-buttons-mergePage').buttonWidget('disableButton', 'button-audit');
        $('#storage-buttons-mergePage').buttonWidget('disableButton', 'button-delete');
        </c:otherwise>
        </c:choose>
        <c:choose>
        <c:when test="${splitmerge.status eq '3'}">
        $('#storage-buttons-mergePage').buttonWidget('disableButton', 'button-save');
        $('#storage-buttons-mergePage').buttonWidget('disableButton', 'button-delete');
        </c:when>
        <c:otherwise>
        $('#storage-buttons-mergePage').buttonWidget('enableButton', 'button-save');
        $('#storage-buttons-mergePage').buttonWidget('enableButton', 'button-delete');
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
        var index = retDetailEditIndex();
        if (index >= 0) {
            $.messager.alert('提醒', '明细正在编辑中！');
            return false;
        }
        // 判断明细中的数量是否超过可用量
        var rows = $detail.datagrid('getRows');
        for (var i in rows) {
            var row = rows[i];
            var key = row.goodsid + ',' + row.storageid;
            if ((row.storageid || '') == '') {
                $.messager.alert('提醒', '商品[' + row.goodsid + ']未选择仓库！');
                return false;
            }
            if (row.isbatch == '1' && (row.storageisbatch == '1') && (row.batchid || '') == '') {
                $.messager.alert('提醒', '商品[' + row.goodsid + ']未选择批次！');
                return false;
            }
            if (row.isbatch == '1') {
                key = key + ',' + row.batchid;
            }
            if (parseInt(usablenums[key] || 0) < parseInt(row.unitnum)) {
                <%--
                $.messager.alert('提醒', '商品[' + row.goodsid + ']的数量超过可用量！该商品可用量为' + parseInt(usablenums[key] || 0));
                --%>
                $.messager.alert('提醒', '商品[' + row.goodsid + ']的数量超过可用量！');
                return false;
            }
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
        setTimeout(initStorageWidget, 100);
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
     * @returns {boolean}
     */
    function enableTitle() {
        $bomid.widget('enable');
        $bomid.widget('readonly', false);
        $storageid.widget('enable');
        $storageid.widget('readonly', false);
        $price.numberbox('enable');
        $unitnum.numberbox('enable');
        return true;
    }
    /**
     * 获取批次号
     */
    function generateBatchno() {
        var storageisbatch = ($storageid.widget('getObject') || {}).isbatch;
        if ($isbatch.val() != '1' || storageisbatch != '1') {
            $batchid.val('');
            $deadline.val('');
            return false;
        }
        var produceddate = new Date().Format('yyyy-MM-dd');
        var goodsid = $goodsid.widget('getValue');
        // get producedate
        var rows = $detail.datagrid('getRows');
        for (var i in rows) {
            var row = rows[i];
            if ((row.produceddate || '') == '') {
                continue;
            }
            if (row.produceddate < produceddate) {
                produceddate = row.produceddate;
            }
        }
        $produceddate.val(produceddate);
        //loading('数据请求中...');
        return true;
        $.ajax({
            type: 'post',
            url: 'storage/getBatchno.do',
            data: {goodsid: goodsid, produceddate: produceddate},
            dataType: 'json',
            async: false,
            success: function (json) {
                //loaded();
                $batchid.val(json.batchno || '');
                $deadline.val(json.deadline || '');
                return true;
            },
            error: function () {
                //loaded();
                return true;
            }
        });
        return true;
    }
    /**
     * 初始化仓库widget
     */
    function initStorageWidget() {
        var index = retDetailEditIndex();
        if (index < 0) {
            return false;
        }
        var rows = $detail.datagrid('getRows');
        var editor2 = $detail.datagrid('getEditor', {index: index, field: 'storageid'});
//            loading('数据请求中...');
//            $.ajax({
//                type: 'post',
//                url: 'storage/showStorageSummaryList.do',
//                data: {goodsid: rows[index].goodsid, groupcols: 'storageid,goodsid', page: 1, rows: 9999},
//                dataType: 'json',
//                success: function (json) {
//
//                    loaded();
//
//                    var storages = json.rows;
//                    var storageids = new Array();
//                    for (var i in storages) {
//
//                        var storage = storages[i];
//                        storageids.push(storage.storageid);
//
//                        usablenums[storage.goodsid + ',' + storage.storageid] = storage.usablenum;
//                    }
        var storageid = $(editor2.target).val();
        $(editor2.target).attr('id', 'storageid' + getRandomid());
        $(editor2.target).widget({
            referwid: 'RL_T_BASE_STORAGE_INFO',
            width: 130,
            required: true,
//                        param: [{field: 'id', op: 'in', value: storageids.join(',')}],
            initValue: storageid,
            onSelect: function (data2) {
                initBatchidWidget(data2 || {});
                storageMap[data2.id] = data2;
                // 设定goodsid.storageid映射关系
                var currentGoodsid = $detail.datagrid('getRows')[retDetailEditIndex()].goodsid;
                goodsStorageMap[currentGoodsid] = data2.id;
            },
            onLoadSuccess: function () {
                var storage = $(editor2.target).widget('getObject') || {};
                var currentGoodsid = $detail.datagrid('getRows')[retDetailEditIndex()].goodsid;
                goodsStorageMap[currentGoodsid] = $(editor2.target).widget('getValue');
                initBatchidWidget(storage)
            },
            onClear: function () {
                var editor3 = $detail.datagrid('getEditor', {index: index, field: 'batchid'});
                try {
                    $(editor3.target).widget('clear');
                    $(editor3.target).widget('readonly', true);
                } catch (e) {
                }
            }
        });
//                }
//            });
        return true;
    }
    /**
     * 初始化批次widget
     */
    function initBatchidWidget(storage) {
        var index = retDetailEditIndex();
        if (index < 0) {
            return false;
        }
        var rows = $detail.datagrid('getRows');
        var isbatch = (rows[index].isbatch == '1') && (storage.isbatch == '1');
        var editor2 = $detail.datagrid('getEditor', {index: index, field: 'storageid'});
        var editor3 = $detail.datagrid('getEditor', {index: index, field: 'batchid'});
        if (!isbatch) {
            $(editor3.target).attr('disabled', 'disabled');
            return true;
        }
        var storageid = storage.id || $(editor2.target).widget('getValue') || '';
        if ($(editor3.target).attr('id')) {
        } else {
            $(editor3.target).attr('id', 'batchid' + getRandomid());
        }
        $(editor3.target).widget({
//                referwid: 'RL_T_STORAGE_BATCH_LIST',
            referwid: 'RL_T_STORAGE_BATCH_LIST2',
            width: 130,
            required: isbatch,       // 商品和仓库批次管理都为“是”时，批次必填
            setValueSelect: true,
            param: [
                {field: 'goodsid', op: 'equal', value: rows[index].goodsid},
                {field: 'storageid', op: 'equal', value: storageid}
            ],
            onSelect: function (data) {
                usablenums[data.goodsid + ',' + data.storageid + ',' + data.batchno] = data.usablenum;
                // 设定goodsid.storageid映射关系
                var currentGoodsid = $detail.datagrid('getRows')[retDetailEditIndex()].goodsid;
                goodsBatchMap[currentGoodsid] = data.batchno;
//                    batchMap[data.batchno] = data.batchno;
                batchMap[data.batchno] = data;
                // 设定生产日期、截止日期
                var editor4 = $detail.datagrid('getEditor', {index: index, field: 'produceddate'});
                var editor5 = $detail.datagrid('getEditor', {index: index, field: 'deadline'});
                $(editor4.target).html(data.produceddate);
                $(editor5.target).html(data.deadline);
            },
            onLoadSuccess: function () {
                var k = $(editor3.target).widget('getValue');
                var o = $(editor3.target).widget('getObject');
                batchMap[k] = o;
            }
        });
        $(editor3.target).widget('setValue', $(editor3.target).widget('getValue'))
        if (isbatch) {
            try {
                $(editor3.target).removeAttr('disabled');
            } catch (e) {
            }
            try {
                $(editor3.target).removeAttr('readonly');
            } catch (e) {
            }
//                try {$(editor3.target).widget('clear');} catch (e) {}
            try {
                $(editor3.target).widget('readonly', false);
            } catch (e) {
            }
            try {
                $(editor3.target).widget('enable');
            } catch (e) {
            }
        } else {
            try {
                $(editor3.target).attr('disabled', 'disabled');
            } catch (e) {
            }
            try {
                $(editor3.target).attr('readonly', 'readonly');
            } catch (e) {
            }
            try {
                $(editor3.target).widget('clear');
            } catch (e) {
            }
            try {
                $(editor3.target).widget('readonly', true);
            } catch (e) {
            }
            try {
                $(editor3.target).widget('disabled');
            } catch (e) {
            }
        }
    }
    function refreshUsablenum(list) {
        $.each(list, function (index, item) {
            loading('获取库存中...');
            $.ajax({
                type: 'post',
                url: 'storage/showStorageSummaryBatchList.do',
                data: {goodsid: item.goodsid, storageid: item.storageid},
                dataType: 'json',
                async: true,
                success: function (json) {
                    loaded();
                    var storages = json;
                    var storageids = new Array();
                    for (var i in storages) {
                        var storage = storages[i];
                        storageids.push(storage.storageid);
                        if (storage.batchno == '') {
                            usablenums[storage.goodsid + ',' + storage.storageid] = storage.usablenum;
                        } else {
                            usablenums[storage.goodsid + ',' + storage.storageid + ',' + storage.batchno] = storage.usablenum;
                        }
                    }
                }
            });
        });
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
//            $detail.datagrid('reloadFooter', [{goodsid: ' ', goodsname: '合计', unitnum: unitnum, totalamount: formatterMoney(totalamount)}]);
        $detail.datagrid('reloadFooter', [{
            goodsid: ' ',
            goodsname: '合计：' + formatterMoney(totalamount),
            barcode: '损溢金额：' + formatterMoney(parseFloat($totalamount.numberbox('getValue')) - totalamount)
        }]);
        return true;
    }
    /**
     * 保存组装单
     */
    function saveMerge() {
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
        generateBatchno();
        $detaillist.val(JSON.stringify(rows));
        $form.form({
            onSubmit: function () {
                loading("保存中..");
            },
            success: function (data) {
                loaded();
                var json = $.parseJSON(data);
                if (json.flag) {
                    $.messager.alert('提醒', '保存成功。');
                    var win = tabsWindowURL('/storage/splitmerge/mergeListPage.do');
                    try {
                        if (win != null) {
                            win.$('#storage-datagrid-mergeListPage').datagrid('reload');
                        }
                    } catch (e) {
                    }
                    location.href = 'storage/splitmerge/mergePage.do?type=add&id=' + json.backid;
                } else {
                    $.messager.alert('提醒', json.msg || '保存失败！');
                }
            }
        }).submit();
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "merge-dialog-print",
            code: "storage_merge",
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
