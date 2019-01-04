<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>拆装规则页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
    <style type="text/css">
        .len70 {
            width: 70px;
        }
        .len90 {
            width: 90px;
        }
        .len220 {
            width: 220px;
        }
        .len388 {
            width: 388px;
        }
        .len408 {
            width: 408px;
        }
        .len625 {
            width: 625px;
        }
        .len645 {
            width: 646px;
        }
        #basefiles-table-bomAddPage td,#basefiles-table-bomEditPage td {
            height: 26px;
        }

    </style>
    <div class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'north',border:false">
            <div class="buttonBG" id="basefiles-buttons-bomPage"></div>
        </div>
        <div data-options="region:'center',border:false">
            <div id="basefiles-panel-bomPage">
            </div>
        </div>
    </div>

    <div class="easyui-menu" id="basefiles-contextMenu-bomPage">
        <div id="basefiles-removeRow-bomPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <script type="text/javascript">

    <!--

    var LISTCOUNT = 10;

    var type = '${param.type }' || 'view';
    var url = 'basefiles/bomAddPage.do';

    if(type == 'add') {

        url = 'basefiles/bomAddPage.do';

    } else if(type == 'edit') {

        url = 'basefiles/bomEditPage.do?id=${param.id }';

    } else if(type == 'view') {

        url = 'basefiles/bomViewPage.do?id=${param.id }';
    }

    var goodsEditIndex = -1;
    var goodsMap = {};

    $(function() {

        $('#basefiles-panel-bomPage').panel({
            href: url,
            cache: false,
            maximized: true,
            border: false,
            onLoad: function () {

                // 商品
                $goodsid.goodsWidget({
                    required: true,
                    onSelect: function(data) {

                        $goodsid2.text(data.id);
                        $name.val(data.name);
                        $name.attr('title', data.name);
                        $boxnum.numberbox('setValue', data.boxnum);
                    },
                    onClear: function() {

                        $goodsid2.text('');
                        $name.val('');
                        $name.attr('title', '');
                        $boxnum.numberbox('setValue', 0);
                    }
                });
                $goodsid.goodsWidget('setText', "<c:out value='${bom.goodsname }' />");

                // disable goodsWidget
                if('${param.type }' == 'view') {

                    $goodsid.goodsWidget('disable');
                }

                // 辅数量
                $unitnum.numberbox('disable');
                $boxnum.numberbox('disable');

                // 明细
                $goodsdetail.datagrid({
                    columns: [[
                        {field: 'id', title: 'id', hidden: true,
                            formatter: function(value, row, index){

                                if((value || '') == '') {

                                    row.id = getRandomid();
                                    return row.id;
                                }
                            }
                        },
                        {field: 'goodsid', title: '商品编码', width: 100,
                            editor: {
                                type: 'span'
                            }
                        },
                        {field: 'goodsname', title: '商品名称', width: 250,
                            editor: {
                                type: 'text'
                            },
                            formatter: function(value, row, index){

                                if((goodsMap[row.goodsid] || '') != '') {

                                    return goodsMap[row.goodsid];
                                }

                                if((row.goodsname || '') != '') {

                                    return row.goodsname;
                                }

                                return value;
                            }
                        },
                        {field: 'unitnum', title: '每份数量', width: 80, align: 'right',
                            formatter: function(value, row, index){

                                if((value || '') != '') {

                                    return formatterBigNumNoLen(value);
                                }
                            },
                            editor: {
                                type: 'numberbox',
                                options: {
                                    min: 1,
                                    required: true,
                                    precision:${decimallen},
                                    onChange: function(newValue, oldValue) {

                                        var gei = retGoodsEditIndex();
                                        var ed1 = $goodsdetail.datagrid('getEditor', {index: gei, field: 'goodsname'});

                                        var goodsid = '';
                                        try {
                                            goodsid = $(ed1.target).goodsWidget('getValue') || '';
                                        } catch(e) {}

                                        if(goodsid == '') {
                                            return true;
                                        }

                                        $.ajax({
                                            type: 'post',
                                            data: {goodsid: goodsid, /*auxunitid: data.auxunitid, */unitnum: newValue},
                                            url: 'basefiles/computeGoodsByUnitnum.do',
                                            success: function(data) {

                                                var json = $.parseJSON(data);
                                                var auxnum = json.auxInteger;
                                                var auxremainder = json.auxremainder;

                                                var editor1 = $goodsdetail.datagrid('getEditor', {index: gei, field: 'auxnum'});
                                                var editor2 = $goodsdetail.datagrid('getEditor', {index: gei, field: 'auxremainder'});

                                                $(editor1.target).html(auxnum);
                                                $(editor2.target).html(auxremainder);
                                            }
                                        });
                                    }
                                }
                            }
                        },
                        {field: 'auxnum', title: '辅数量(整)', width: 100, align: 'right',
                            formatter: function(value, row, index){
                                if((value || '') != '') {
                                    return formatterNum(value);
                                }
                            },
                            editor: {
                                type: 'span'
                            }
                        },
                        {field: 'auxremainder', title: '辅数量(余)', width: 100, align: 'right',
                            formatter: function(value, row, index){

                                if((value || '') != '') {

                                    return formatterBigNumNoLen(value);
                                }
                            },
                            editor: {
                                type: 'span'
                            }
                        },
                        {field: 'remark', title: '备注', width: 320,
                            editor: {
                                type: 'validatebox'
                            }
                        }
                    ]],
                    border: true,
                    fit: true,
                    rownumbers: true,
                    showFooter: true,
                    singleSelect: true,
                    data: $.parseJSON('${list }'),
                    <c:if test="${param.type ne 'view'}">
                        onDblClickRow: function(rowIndex, rowData) {

                            beginEditDetail(rowIndex);
                        },
                        onClickRow: endEditDetail,
                    </c:if>
                    onLoadSuccess: function(data) {

                        for(var i = data.total; i < LISTCOUNT; i++) {

                            $goodsdetail.datagrid('appendRow', {});
                        }
                    },
                    onRowContextMenu: function(e, index, row){

                        <c:if test="${param.type eq 'view' }">
                            return false;
                        </c:if>

                        e.preventDefault();

                        $("#basefiles-contextMenu-bomPage").menu('show', {
                            left: e.pageX,
                            top: e.pageY
                        });

                        $goodsdetail.datagrid('selectRow', index);
                    }
                });

                $('#basefiles-buttons-bomPage').buttonWidget({
                    type: '${param.type }',
                    tab: '<c:out value="${param.title}" />',
                    taburl: '/basefiles/bomListPage.do',
                    id: '${param.id}',
                    datagrid: 'basefiles-datagrid-bomListPage',
                    initButton: [
                        <security:authorize url="/basefiles/saveBom.do">
                        {
                            type: 'button-save',
                            handler: function(){

                                saveBom();
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/basefiles/saveBom.do">
                        {
                            type: 'button-add',
                            handler: function(){

                                top.addTab('basefiles/bomPage.do?type=add', '拆装规则新增');
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/basefiles/openBom.do">
                        {
                            type: 'button-open',
                            handler: function(){

                                if('${bom.status }' == '1') {

                                    $.messager.alert('提醒', '该拆装规则已经启用！');
                                    return false;
                                }

                                loading();
                                $.ajax({
                                    type: 'post',
                                    url: 'basefiles/openBom.do',
                                    data: {id: '${bom.id }', status: '1'},
                                    dataType: 'json',
                                    success: function(json) {

                                        loaded();

                                        if(json.flag) {

                                            $.messager.alert('提醒', '启用成功。');
                                            window.location.href = window.location.href;
                                            return true;
                                        }

                                        $.messager.alert('提醒', '启用失败！');
                                        return true;
                                    }
                                });
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/basefiles/closeBom.do">
                        {
                            type: 'button-close',
                            handler: function(){

                                if('${bom.status }' == '0') {

                                    $.messager.alert('提醒', '该拆装规则已经关闭！');
                                    return false;
                                }

                                loading();
                                $.ajax({
                                    type: 'post',
                                    url: 'basefiles/closeBom.do',
                                    data: {id: '${bom.id }', status: '0'},
                                    dataType: 'json',
                                    success: function(json) {

                                        loaded();

                                        if(json.flag) {

                                            $.messager.alert('提醒', '禁用成功。');
                                            window.location.href = window.location.href;
                                            return true;
                                        }

                                        $.messager.alert('提醒', '禁用失败！');
                                        return true;
                                    }
                                });
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/basefiles/deleteBom.do">
                        {
                            type: 'button-delete',
                            handler: function(){
                                $.messager.confirm("提醒","确定要删除该拆装规则吗？",function(r){
                                    if(r){

                                        loading();
                                        $.ajax({
                                            type: 'post',
                                            url: 'basefiles/deleteBom.do',
                                            data: {id: '${bom.id }'},
                                            dataType: 'json',
                                            success: function(json) {

                                                loaded();

                                                if(json.flag) {

                                                    $.messager.alert('提醒', '删除成功。');
                                                    top.closeNowTab();
                                                    return true;
                                                }

                                                $.messager.alert('警告', json.msg || '删除失败！');
                                                return true;
                                            }
                                        });
                                    }
                                });

                            }
                        },
                        </security:authorize>
                        <security:authorize url="/basefiles/backBom.do">
                        {
                            type: 'button-back',
                            handler: function(data){

                                window.location.href = 'basefiles/bomPage.do?type=edit&id=' + data.id;
                                return true;
                                // refreshPanel('sales/orderEditPage.do?id='+ data.id);
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/basefiles/nextBom.do">
                        {
                            type: 'button-next',
                            handler: function(data){

                                window.location.href = 'basefiles/bomPage.do?type=edit&id=' + data.id;
                                return true;
                            }
                        },
                        </security:authorize>
                        {}
                    ]
                });

                // button 状态
                $(function(){

                    $('#basefiles-buttons-bomPage').buttonWidget('enableButton', 'button-back');
                    $('#basefiles-buttons-bomPage').buttonWidget('enableButton', 'button-next');

                    $('#basefiles-buttons-bomPage').buttonWidget('disableButton', 'button-add');
                    $('#basefiles-buttons-bomPage').buttonWidget('disableButton', 'button-save');
                    $('#basefiles-buttons-bomPage').buttonWidget('disableButton', 'button-open');
                    $('#basefiles-buttons-bomPage').buttonWidget('disableButton', 'button-close');
                    $('#basefiles-buttons-bomPage').buttonWidget('disableButton', 'button-delete');

                    if('${param.type }' == 'add') {

                        $('#basefiles-buttons-bomPage').buttonWidget('enableButton', 'button-save');

                    }/* else if('${param.type }' == 'view') {

                        $('#basefiles-buttons-bomPage').buttonWidget('enableButton', 'button-add');
                        $('#basefiles-buttons-bomPage').buttonWidget('disableButton', 'button-save');
                        $('#basefiles-buttons-bomPage').buttonWidget('enableButton', 'button-delete');

                    }*/ else if('${bom.status }' == '0' || '${bom.status }' == '2') {

                        $('#basefiles-buttons-bomPage').buttonWidget('enableButton', 'button-add');
                        $('#basefiles-buttons-bomPage').buttonWidget('enableButton', 'button-save');
                        $('#basefiles-buttons-bomPage').buttonWidget('enableButton', 'button-open');
                        $('#basefiles-buttons-bomPage').buttonWidget('enableButton', 'button-delete');

                    } else if('${bom.status }' == '1') {

                        $('#basefiles-buttons-bomPage').buttonWidget('enableButton', 'button-add');
                        $('#basefiles-buttons-bomPage').buttonWidget('enableButton', 'button-save');
                        $('#basefiles-buttons-bomPage').buttonWidget('enableButton', 'button-close');
                        $('#basefiles-buttons-bomPage').buttonWidget('enableButton', 'button-delete');
                    }
                });

                // 删除明细
                $('#basefiles-removeRow-bomPage').on('click', function(e) {

                    var row = $goodsdetail.datagrid('getSelected');
                    var index = $goodsdetail.datagrid('getRowIndex', row);

                    $goodsdetail.datagrid('deleteRow', index);

                    var rows = $goodsdetail.datagrid('getRows');

                    if(rows.length < 2) {

                        $goodsdetail.datagrid('appendRow', {});
                        return true;
                    }

                    var empty = false;
                    for(var i in rows) {

                        var row = rows[i];
                        if((row.goodsid || '') == '') {

                            empty = true;
                        }
                    }

                    if(!empty) {

                        $goodsdetail.datagrid('appendRow', {});
                    }

                    return true;
                });

            }
        }); // panel end

    });

    /**
     * 开始编辑
     */
    function beginEditDetail(rowIndex) {

        if(rowIndex < 0){

            $.messager.alert("提醒", "请选择记录！");
            return false;
        }

        if(retGoodsEditIndex() >= 0) {

            return true;
        }

        goodsEditIndex = rowIndex;

        var rows = $goodsdetail.datagrid('getRows');
        var goodsid = rows[rowIndex].goodsid || '';
        var goodsname = rows[rowIndex].goodsname || '';

        $goodsdetail.datagrid('beginEdit', rowIndex);

        if(goodsid != '' && !goodsMap[goodsid]) {

            goodsMap[goodsid] = goodsname;
        }

        setTimeout(function() {

            var editors = $goodsdetail.datagrid('getEditors', rowIndex);

            editors[1].target.attr('id', 'd' + getRandomid());
            editors[1].target.attr('value', goodsid);
            editors[1].target.val(goodsid);
            editors[1].target.attr('title', goodsMap[goodsid] || goodsname);

            initGoodsWidget(editors[1].target, goodsMap[goodsid] || goodsname);

        }, 100);

        return true;
    }

    /**
     * 结束编辑
     */
    function endEditDetail() {

        var gei = retGoodsEditIndex();

        if(gei < 0) {

            return true;
        }

        $goodsdetail.datagrid('endEdit', gei);

        var rows = $goodsdetail.datagrid('getRows');
        if(rows.length == (gei + 1)) {

            $goodsdetail.datagrid('appendRow', {});
        }

        goodsEditIndex = -1;
    }

    /**
    * 初始化商品控件
     */
    function initGoodsWidget(d, goodsname) {

        $(d).goodsWidget({
            required: true,
            onSelect: function(data) {

                var gei = retGoodsEditIndex();
                var ed1 = $goodsdetail.datagrid('getEditor', {index: gei, field: 'goodsid'});
                var ed2 = $goodsdetail.datagrid('getEditor', {index: gei, field: 'unitnum'});
                var ed3 = $goodsdetail.datagrid('getEditor', {index: gei, field: 'auxnum'});
                var ed4 = $goodsdetail.datagrid('getEditor', {index: gei, field: 'auxremainder'});

                $(ed1.target).html(data.id);
                $(ed2.target).numberbox('clear');
                $(ed3.target).html('');
                $(ed4.target).html('');

                goodsMap[data.id] = data.name;
            },
            onClear: function() {

                var gei = retGoodsEditIndex();
                var ed1 = $goodsdetail.datagrid('getEditor', {index: gei, field: 'goodsid'});
                var ed2 = $goodsdetail.datagrid('getEditor', {index: gei, field: 'unitnum'});
                var ed3 = $goodsdetail.datagrid('getEditor', {index: gei, field: 'auxnum'});
                var ed4 = $goodsdetail.datagrid('getEditor', {index: gei, field: 'auxremainder'});

                $(ed1.target).html('');
                $(ed2.target).numberbox('clear');
                $(ed3.target).html('');
                $(ed4.target).html('');
            }
        });

        $(d).goodsWidget('setText', goodsname);
    }

    /**
    *
    * @returns {number}
     */
    function retGoodsEditIndex() {

        var rows = $goodsdetail.datagrid('getRows');

        for(var i = 0; i < rows.length; i++) {
            var editors = $goodsdetail.datagrid('getEditors', i);
            if(editors.length > 0) {

                return i;
            }
        }

        return -1;
    }

    /**
    * 保存BOM
     */
    function saveBom() {

        endEditDetail();
        var index = retGoodsEditIndex();
        if(index >= 0) {

            $.messager.alert('提醒', '明细正在编辑中，无法保存！');
            return false;
        }

        $form.form({
            onSubmit: function(param) {

                loading();
                var flag = $form.form('validate');
                if(!flag) {

                    loaded();
                    return false;
                }

                var rows1 = $goodsdetail.datagrid('getRows');   //
                var rows2 = new Array();                        //

                rows2.push({
                    goodsid: $goodsid.goodsWidget('getValue') || '',
                    type: '1',
                    unitnum: $unitnum.numberbox('getValue')/*,
                    auxnum: $auxnum.numberbox('getValue'),
                    auxremainder: $auxremainder.numberbox('getValue')*/
                });

                for(var i in rows1) {

                    var row = rows1[i];
                    row.type = '2';
                    if((row.goodsid || '') != '') {

                        rows2.push(row);
                    }
                }

                if(rows2.length <= 1) {

                    $.messager.alert('提醒', '<font color="#f00">请输入明细！</font>');
                    return false;
                }

                $detailjson.val(JSON.stringify(rows2));
            },
            success: function(data) {

                loaded();
                var error = false;
                var json = {};

                try {
                    json = $.parseJSON(data);
                } catch (e) {
                    error = true;
                }

                if(error || !json.flag) {

                    $.messager.alert('提醒', '保存失败！');
                    return false;
                }

                $.messager.alert('提醒', '保存成功。');

                var title = top.getNowTabTitle();

                top.updateTab('basefiles/bomPage.do?type=edit&id=' + json.backid, '拆装规则编辑');
                top.closeTab(title);
            }
        }).submit();
    }

    -->

    </script>
</body>
</html>
