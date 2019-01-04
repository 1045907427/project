<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>商品档案列表</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<input type="hidden" id="goodsShortcut-opera"/>
<div class="easyui-panel" data-options="fit:true,border:false">
    <div title="" class="easyui-layout" data-options="fit:true,border:true">
        <div title="" data-options="region:'north',split:false,border:false" style="height: 30px;overflow: hidden">
            <div class="buttonBG" id="goodsShortcut-button-div"></div>
        </div>
        <div title="商品列表【简化版】" data-options="region:'center',split:true">
            <div id="wares-query-showgoodsShortcutList" style="padding: 0px;">
                <form action="" id="wares-form-goodsShortcutListQuery" method="post"
                      style="padding-left: 5px; padding-top: 2px;">
                    <table class="querytable">
                        <tr>
                            <td>编码:</td>
                            <td><input type="text" name="id" style="width:120px"/></td>
                            <td>名称:</td>
                            <td><input type="text" name="name" style="width:200px"/></td>
                            <td>商品品牌:</td>
                            <td><input id="wares-widget-goodsShortcut-brand" type="text" name="brandid"
                                       style="width:120px"/></td>
                        </tr>
                        <tr>
                            <td>所属部门:</td>
                            <td><input id="wares-widget-goodsShortcut-deptid" type="text" name="deptid"
                                       style="width:120px"/></td>
                            <td>所属供应商:</td>
                            <td><input id="wares-widget-goodsShortcut-supplierid" type="text" name="defaultsupplier"
                                       style="width:206px"/></td>
                            <td>状态:</td>
                            <td><select id="wares-widget-goodsShortcut-state" name="state" style="width:130px;">
                                <option></option>
                                <option value="2">保存</option>
                                <option value="1" selected="selected">启用</option>
                                <option value="0">禁用</option>
                            </select>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4"></td>
                            <td colspan="2">
                                <a href="javaScript:void(0);" id="wares-query-querygoodsShortcutList" class="button-qr">查询</a>
                                <a href="javaScript:void(0);" id="wares-query-reloadgoodsShortcutList"
                                   class="button-qr">重置</a>
                                <span id="goodsShortcut-query-advanced"></span>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <table id="wares-table-goodsShortcutList"></table>
        </div>
    </div>
</div>
<div id="wares-dialog-goodsShortcut"></div>
<div id="wares-dialog-goodsShortcutEditMore"></div>
<script type="text/javascript">
    var goodsShortcut_AjaxConn = function (Data, Action) {
        var MyAjax = $.ajax({
            type: 'post',
            cache: false,
            url: Action,
            data: Data,
            async: false,
            success: function (data) {
                loaded();
            }
        });
        return MyAjax.responseText;
    };

    function refreshLayout(title, url, type) {
        $('#wares-dialog-goodsShortcut').dialog({
            maximizable: true,
            resizable: true,
            title: title,
            width: 740,
            height: 450,
            closed: false,
            cache: false,
            href: url,
            modal: true
        });
        $("#goodsShortcut-opera").attr("value", type);
    }

    //加载下拉框
    function loadDropdown() {

        //主计量单位 -商品档案档案
        $("#goodsShortcut-widget-mainunit").widget({
            width: 130,
            name: 't_base_goods_info',
            col: 'mainunit',
            singleSelect: true,
            required: true,
            onlyLeafCheck: false
        });

        //辅计量单位
        $("#goodsShortcut-widget-meteringunitid").widget({
            width: 130,
            name: 't_base_goods_info_meteringunit',
            col: 'meteringunitid',
            singleSelect: true,
            required: true,
            onlyLeafCheck: false,
            onSelect: function (data) {
                $("#goodsShortcut-widget-mainunit").widget({
                    width: 130,
                    name: 't_base_goods_info',
                    col: 'mainunit',
                    param: [{field: 'id', op: 'notequal', value: data.id}],
                    singleSelect: true,
                    required: true,
                    onlyLeafCheck: false
                });
            },
            onClear: function () {
                $("#goodsShortcut-widget-mainunit").widget({
                    width: 130,
                    name: 't_base_goods_info',
                    col: 'mainunit',
                    singleSelect: true,
                    required: true,
                    onlyLeafCheck: false
                });
            }
        });

        //箱装量
        $("#goodsInfo-numberbox-rate").numberbox({
            required: true,
            min: 0,
            max: 999999999999,
            precision: general_bill_decimallen,
            groupSeparator: ',',
            onChange: function (newValue, oldValue) {
                if (newValue == Number(0)) {
                    $(this).numberbox('setValue', "9999");
                }
                var highestbuyprice = $("#goodsInfo-numberbox-highestbuyprice").numberbox('getValue');
                var buyboxprice = highestbuyprice * newValue;
                $("#goodsInfo-numberbox-buyboxprice").numberbox('setValue', buyboxprice);
            }
        });

        //长度
        $("#goodsInfo-numberbox-glength").numberbox({
            min: 0,
            max: 999999999999,
            precision: 6,
            groupSeparator: ',',
            onChange: function (newValue, oldValue) {
                var ghight = $("#goodsInfo-numberbox-ghight").numberbox('getValue');
                var gwidth = $("#goodsInfo-numberbox-gwidth").numberbox('getValue');
                var totalvolume = newValue * ghight * gwidth;
                $("#goodsInfo-numberbox-totalvolume").numberbox('setValue', totalvolume);
            }
        });
        //高度
        $("#goodsInfo-numberbox-ghight").numberbox({
            min: 0,
            max: 999999999999,
            precision: 6,
            groupSeparator: ',',
            onChange: function (newValue, oldValue) {
                var glength = $("#goodsInfo-numberbox-glength").numberbox('getValue');
                var gwidth = $("#goodsInfo-numberbox-gwidth").numberbox('getValue');
                var totalvolume = newValue * glength * gwidth;
                $("#goodsInfo-numberbox-totalvolume").numberbox('setValue', totalvolume);
            }
        });
        //宽度
        $("#goodsInfo-numberbox-gwidth").numberbox({
            min: 0,
            max: 999999999999,
            precision: 6,
            groupSeparator: ',',
            onChange: function (newValue, oldValue) {
                var glength = $("#goodsInfo-numberbox-glength").numberbox('getValue');
                var ghight = $("#goodsInfo-numberbox-ghight").numberbox('getValue');
                var totalvolume = newValue * glength * ghight;
                $("#goodsInfo-numberbox-totalvolume").numberbox('setValue', totalvolume);
            }
        });

        //最高采购价
        $("#goodsInfo-numberbox-highestbuyprice").numberbox({
            required: true,
            min: 0,
            max: 999999999999,
            precision: 6,
            groupSeparator: ',',
            onChange: function (newValue, oldValue) {
                $("#goodsInfo-numberbox-newbuyprice").numberbox('setValue', newValue);
                var boxnum = $("#goodsInfo-numberbox-rate").numberbox('getValue');
                if ("" == boxnum || Number(boxnum) == 0) {
                    boxnum = Number(1);
                }
                var buyboxprice = boxnum * newValue;
                $("#goodsInfo-numberbox-buyboxprice").numberbox('setValue', buyboxprice);
            }
        });

        //采购箱价
        $("#goodsInfo-numberbox-buyboxprice").numberbox({
            min: 0,
            max: 999999999999,
            precision: 6,
            groupSeparator: ',',
            onChange: function (newValue, oldValue) {
                var boxnum = $("#goodsInfo-numberbox-rate").numberbox('getValue');
                if ("" == boxnum || Number(boxnum) == 0) {
                    boxnum = Number(1);
                }
                var highestbuyprice = newValue / boxnum;
                $("#goodsInfo-numberbox-highestbuyprice").numberbox('setValue', highestbuyprice);
            }
        });

        //商品品牌
        $("#goodsShortcut-widget-brand").widget({
            width: 130,
            name: 't_base_goods_info',
            col: 'brand',
            required: true,
            singleSelect: true,
            onlyLeafCheck: false,
            onSelect: function (data) {
                $("#goodsShortcut-widget-deptid").widget('setValue', data.deptid);
                $("#goodsShortcut-supplierWidget-defaultsupplier").supplierWidget('setValue', data.supplierid);
                $("#goodsShortcut-supplierWidget-defaultsupplier").supplierWidget('setText', data.suppliername);
            },
            onClear: function () {
                $("#goodsShortcut-widget-deptid").widget('clear');
                $("#goodsShortcut-supplierWidget-defaultsupplier").supplierWidget('clear');
            }
        });

        //所属部门
        $("#goodsShortcut-widget-deptid").widget({
            width: 130,
            name: 't_base_goods_info',
            col: 'deptid',
            singleSelect: true,
            required: true,
            onlyLeafCheck: true
        });

        //默认分类
        $("#goodsShortcut-widget-defaultsort").widget({
            width: 130,
            name: 't_base_goods_info',
            col: 'defaultsort',
            singleSelect: true,
            required: true,
            onlyLeafCheck: true
        });

        //默认仓库
        $("#goodsShortcut-widget-storage").widget({
            width: 130,
            name: 't_base_goods_info',
            col: 'storageid',
            singleSelect: true,
            required: true,
            onlyLeafCheck: false
        });

        //默认库位
        $("#goodsShortcut-widget-storagelocation").widget({
            width: 130,
            name: 't_base_goods_info',
            col: 'storagelocation',
            singleSelect: true,
            onlyLeafCheck: false,
            onChecked: function (data, checked) {
                if (checked) {
                    document.getElementById("goodsShortcut-div-boxnumtext").style.visibility = "visible";
                    document.getElementById("goodsShortcut-div-boxnum").style.visibility = "visible";
                    $("#goodsShortcut-numberbox-boxnum").numberbox({
                        required: true,
                        min: 0,
                        precision: general_bill_decimallen,
                        groupSeparator: ','
                    });
                    $("#goodsShortcut-numberbox-boxnum").focus();
                } else {
                    document.getElementById("goodsShortcut-div-boxnumtext").style.visibility = "hidden";
                    document.getElementById("goodsShortcut-div-boxnum").style.visibility = "hidden";
                    $("#goodsShortcut-numberbox-boxnum").removeClass();
                }
            },
            onLoadSuccess: function () {
                var val = $(this).widget('getValue');
                if (null != val && val != "") {
                    document.getElementById("goodsShortcut-div-boxnumtext").style.visibility = "visible";
                    document.getElementById("goodsShortcut-div-boxnum").style.visibility = "visible";
                    $("#goodsShortcut-numberbox-boxnum").numberbox({
                        required: true,
                        min: 0,
                        precision: general_bill_decimallen,
                        groupSeparator: ','
                    });
                    $("#goodsShortcut-numberbox-boxnum").focus();
                } else {
                    document.getElementById("goodsShortcut-div-boxnumtext").style.visibility = "hidden";
                    document.getElementById("goodsShortcut-div-boxnum").style.visibility = "hidden";
                    $("#goodsShortcut-numberbox-boxnum").removeClass();
                }
            },
            onClear: function () {
                document.getElementById("goodsShortcut-div-boxnumtext").style.visibility = "hidden";
                document.getElementById("goodsShortcut-div-boxnum").style.visibility = "hidden";
                $("#goodsShortcut-numberbox-boxnum").removeClass();
            }
        });

        //默认供应商
        $("#goodsShortcut-supplierWidget-defaultsupplier").supplierWidget({
            required: true
        });

        //默认税种
        $("#goodsShortcut-widget-defaulttaxtype").widget({
            width: 130,
            name: 't_base_goods_info',
            col: 'defaulttaxtype',
            singleSelect: true,
            required: true,
            onlyLeafCheck: false
        });
    }

    //检验商品档案数据（唯一性，最大长度等）
    $.extend($.fn.validatebox.defaults.rules, {
        validId: {//编号唯一性,最大长度
            validator: function (value, param) {
                var reg = eval("/^[A-Za-z0-9]{0," + param[0] + "}$/");
                if (reg.test(value)) {
                    var ret = goodsShortcut_AjaxConn({id: value}, 'basefiles/isRepeatGoodsInfoID.do');//true 重复，false 不重复
                    var retJson = $.parseJSON(ret);
                    if (retJson.flag) {
                        $.fn.validatebox.defaults.rules.validId.message = '编号重复, 请重新输入!';
                        return false;
                    }
                }
                else {
                    $.fn.validatebox.defaults.rules.validId.message = '最多可输入{0}个字符!';
                    return false;
                }
                return true;
            },
            message: ''
        },
        validName: {//名称唯一性,最大长度
            validator: function (value, param) {
                if (value.length <= param[0]) {
                    var ret = goodsShortcut_AjaxConn({name: value}, 'basefiles/isRepeatGoodsInfoName.do');//true 重复，false 不重复
                    var retJson = $.parseJSON(ret);
                    if (retJson.flag) {
                        $.fn.validatebox.defaults.rules.validName.message = '名称重复, 请重新输入!';
                        return false;
                    }
                }
                else {
                    $.fn.validatebox.defaults.rules.validName.message = '最多可输入{0}个字符!';
                    return false;
                }
                return true;
            },
            message: ''
        },
        isRepeatItemno: {//唯一性,最大长度
            validator: function (value, param) {
                if (value.length <= param[0]) {
                    var ret = goodsShortcut_AjaxConn({itemno: value}, 'basefiles/isRepeatGoodsInfoItemno.do');//true 重复，false 不重复
                    var retJson = $.parseJSON(ret);
                    if (retJson.flag) {
                        $.fn.validatebox.defaults.rules.isRepeatItemno.message = '商品货位重复, 请重新输入!';
                        return false;
                    }
                }
                else {
                    $.fn.validatebox.defaults.rules.isRepeatItemno.message = '最多可输入{0}个字符!';
                    return false;
                }
                return true;
            },
            message: ''
        }
    });

    //根据初始的列与用户保存的列生成以及字段权限生成新的列
    var goodsShortcutListColJson = $("#wares-table-goodsShortcutList").createGridColumnLoad({
        name: 'base_goods_info',
        frozenCol: [[]],
        commonCol: [[{field: 'id', title: '编码', resizable: true, sortable: true},
            {field: 'name', title: '名称', width: 250, sortable: true},
            {field: 'barcode', title: '条形码', width: 95, sortable: true},
            {
                field: 'mainunit', title: '单位', width: 45, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.mainunitName;
                }
            },
            {field: 'boxnum', title: '箱装量', width: 45, isShow: true},
            {
                field: 'field01', title: '采购价', resizable: true, sortable: true, isShow: true,
                formatter: function (value, rowData, rowIndex) {
                    return formatterMoney(value);
                }
            },
            <c:forEach var="list" items="${priceList}" varStatus="status">
            <c:if test="${status.index < pricenum}">
            {
                field: '${list.code}', title: '${list.codename}', resizable: true, isShow: true,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.priceList != null && undefined != rowData.priceList) {
                        for (var i = 0; i < rowData.priceList.length; i++) {
                            var priceInfo = rowData.priceList[i];
                            if (priceInfo.code == '${list.code}') {
                                return formatterMoney(priceInfo.taxprice);
                                break;
                            }
                        }
                    } else {
                        return "0.00";
                    }
                }
            },
            </c:if>
            </c:forEach>
            {
                field: 'brand', title: '商品品牌', width: 70, sortable: true, hidden: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.brandName;
                }
            },
            {
                field: 'defaultsort', title: '默认分类', width: 80, sortable: true, hidden: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.defaultsortName;
                }
            },
            {
                field: 'storageid', title: '默认仓库', width: 60, sortable: true, hidden: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.storageName;
                }
            },
            {
                field: 'storagelocation', title: '默认库位', width: 60, sortable: true, hidden: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.storagelocationName;
                }
            },
            {
                field: 'defaultbuyer', title: '默认采购员', width: 70, sortable: true, hidden: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.defaultbuyerName;
                }
            },
            {
                field: 'defaultsaler', title: '默认业务员', width: 70, sortable: true, hidden: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.defaultsalerName;
                }
            },
            {
                field: 'defaultsupplier', title: '所属供应商', width: 160, sortable: true, hidden: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.defaultsupplierName;
                }
            },
            {
                field: 'state', title: '状态', width: 50, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.stateName;
                }
            }
        ]]
    });

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
        //商品品牌
        $("#wares-widget-goodsShortcut-brand").widget({
            width: 130,
            name: 't_base_goods_info',
            col: 'brand',
            singleSelect: true,
            onlyLeafCheck: false
        });

        //默认供应商
        $("#wares-widget-goodsShortcut-supplierid").supplierWidget({});

        //所属部门
        $("#wares-widget-goodsShortcut-deptid").widget({
            width: 120,
            referwid: 'RL_T_BASE_DEPATMENT',
            singleSelect: true,
            onlyLeafCheck: false
        });

        var queryJSON = $("#wares-form-goodsShortcutListQuery").serializeJSON();

        $('#wares-table-goodsShortcutList').datagrid({
            authority: goodsShortcutListColJson,
            frozenColumns: [[{field: 'ck', checkbox: true}]],
            columns: goodsShortcutListColJson.common,
            fit: true,
            title: '',
            toolbar: '#wares-query-showgoodsShortcutList',
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            queryParams: queryJSON,
            url: 'basefiles/goodsInfoListPage.do',
            onDblClickRow: function (rowIndex, rowData) {
                refreshLayout('商品简化版【查看】', 'basefiles/showGoodsShortcutViewPage.do?id=' + encodeURIComponent(rowData.id), 'view');
            }
        }).datagrid("columnMoving");
        //加载按钮
        $("#goodsShortcut-button-div").buttonWidget({
            //初始默认按钮 根据type对应按钮事件
            initButton: [
                {},
                <security:authorize url="/basefiles/goodesInfoAddBtn.do">
                {
                    type: 'button-add',//新增
                    handler: function () {
                        refreshLayout('商品简化版【新增】', 'basefiles/showGoodsShortcutAddPage.do', 'add');
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesInfoEditBtn.do">
                {
                    type: 'button-edit',//修改
                    handler: function () {
                        var goodsShortcutRow = $("#wares-table-goodsShortcutList").datagrid('getSelected');
                        if (goodsShortcutRow == null) {
                            $.messager.alert("提醒", "请选择商品!");
                            return false;
                        }
                        var flag = isDoLockData(goodsShortcutRow.id, "t_base_goods_info");
                        if (!flag) {
                            $.messager.alert("警告", "该数据正在被其他人操作，暂不能修改！");
                            return false;
                        }
                        refreshLayout('商品简化版【修改】', 'basefiles/showGoodsShortcutEditPage.do?id=' + encodeURIComponent(goodsShortcutRow.id), 'edit');
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesInfoCopyBtn.do">
                {
                    type: 'button-copy',//复制
                    handler: function () {
                        var goodsShortcutRow = $("#wares-table-goodsShortcutList").datagrid('getSelected');
                        if (goodsShortcutRow == null) {
                            $.messager.alert("提醒", "请选择商品!");
                            return false;
                        }
                        refreshLayout('商品简化版【新增】', 'basefiles/showGoodsShortcutCopyPage.do?id=' + encodeURIComponent(goodsShortcutRow.id), 'copy');
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesInfoDeleteBtn.do">
                {
                    type: 'button-delete',//删除
                    handler: function () {
                        var goodsShortcutRows = $("#wares-table-goodsShortcutList").datagrid('getChecked');
                        if (goodsShortcutRows.length == 0) {
                            $.messager.alert("提醒", "请勾选商品!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否确定删除商品档案?", function (r) {
                            if (r) {
                                var idStr = "";
                                for (var i = 0; i < goodsShortcutRows.length; i++) {
                                    idStr += goodsShortcutRows[i].id + ",";
                                }
                                loading("删除中..");
                                $.ajax({
                                    url: 'basefiles/deleteGoodsInfos.do',
                                    type: 'post',
                                    dataType: 'json',
                                    data: {idStr: idStr},
                                    success: function (retJSON) {
                                        loaded();
                                        if (retJSON.flag) {
                                            $.messager.alert("提醒", "" + retJSON.userNum + "条记录被引用,不允许删除;<br/>" + retJSON.lockNum + "条记录网络互斥,不允许删除;<br/>删除成功" + retJSON.num + "条记录;");
                                            var queryJSON = $("#wares-form-goodsShortcutListQuery").serializeJSON();
                                            $("#wares-table-goodsShortcutList").datagrid("load", queryJSON);
                                            $("#wares-table-goodsShortcutList").datagrid('clearSelections');
                                            $("#wares-table-goodsShortcutList").datagrid('clearChecked');
                                        }
                                        else {
                                            $.messager.alert("提醒", "" + retJSON.userNum + "条记录被引用,不允许删除;<br/>" + retJSON.lockNum + "条记录网络互斥,不允许删除;<br/>删除成功" + retJSON.num + "条记录;");
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesInfoEnableBtn.do">
                {
                    type: 'button-open',//启用
                    handler: function () {
                        var goodsShortcutRows = $("#wares-table-goodsShortcutList").datagrid('getChecked');
                        if (goodsShortcutRows.length == 0) {
                            $.messager.alert("提醒", "请勾选商品!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否确定启用商品档案?", function (r) {
                            if (r) {
                                var idStr = "";
                                for (var i = 0; i < goodsShortcutRows.length; i++) {
                                    idStr += goodsShortcutRows[i].id + ",";
                                }
                                loading("启用中..");
                                $.ajax({
                                    url: 'basefiles/enableGoodsInfos.do',
                                    type: 'post',
                                    dataType: 'json',
                                    data: {idStr: idStr},
                                    success: function (retJSON) {
                                        loaded();
                                        if (retJSON.flag) {
                                            $.messager.alert("提醒", "启用无效" + retJSON.invalidNum + "条记录;<br/>启用成功" + retJSON.num + "条记录;");
                                            var queryJSON = $("#wares-form-goodsShortcutListQuery").serializeJSON();
                                            $("#wares-table-goodsShortcutList").datagrid("load", queryJSON);
                                            $("#wares-table-goodsShortcutList").datagrid('clearSelections');
                                            $("#wares-table-goodsShortcutList").datagrid('clearChecked');
                                        }
                                        else {
                                            $.messager.alert("提醒", "启用无效" + retJSON.invalidNum + "条记录;<br/>启用失败" + retJSON.num + "条记录;");
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesInfoDisableBtn.do">
                {
                    type: 'button-close',//禁用
                    handler: function () {
                        var goodsShortcutRows = $("#wares-table-goodsShortcutList").datagrid('getChecked');
                        if (goodsShortcutRows.length == 0) {
                            $.messager.alert("提醒", "请勾选商品!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否确定禁用商品档案?", function (r) {
                            if (r) {
                                var idStr = "";
                                for (var i = 0; i < goodsShortcutRows.length; i++) {
                                    idStr += goodsShortcutRows[i].id + ",";
                                }
                                loading("禁用中..");
                                $.ajax({
                                    url: 'basefiles/disableGoodsInfos.do',
                                    type: 'post',
                                    dataType: 'json',
                                    data: {idStr: idStr},
                                    success: function (retJSON) {
                                        loaded();
                                        if (retJSON.flag) {
                                            $.messager.alert("提醒", "" + retJSON.invalidNum + "条记录状态不允许禁用;<br/>禁用成功" + retJSON.num + "条记录;");
                                            var queryJSON = $("#wares-form-goodsShortcutListQuery").serializeJSON();
                                            $("#wares-table-goodsShortcutList").datagrid("load", queryJSON);
                                            $("#wares-table-goodsShortcutList").datagrid('clearSelections');
                                            $("#wares-table-goodsShortcutList").datagrid('clearChecked');
                                        }
                                        else {
                                            $.messager.alert("提醒", "" + retJSON.invalidNum + "条记录状态不允许禁用;<br/>禁用失败" + retJSON.num + "条记录;");
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesInfoImportBtn.do">
                {
                    type: 'button-import',//导入
                    attr: {
                        clazz: "goodsService", //spring中注入的类名
                        methodjson: {
                            t_base_goods_info: 'addDRGoodsInfo',
                            t_base_goods_info_meteringunit: 'addDRGoodsInfoMU',
                            t_base_goods_info_price: 'addDRGoodsInfoPrice',
                            t_base_goods_info_storage: 'addDRGoodsInfoStorage',
                            t_base_goods_info_storagelocation: 'addDRGoodsInfoSL',
                            t_base_goods_info_waresclass: 'addDRGoodsInfoWC'
                        }, //插入数据库的方法
                        tnjson: {
                            商品列表: 't_base_goods_info',
                            对应辅单位: 't_base_goods_info_meteringunit',
                            对应价格套: 't_base_goods_info_price',
                            对应仓库: 't_base_goods_info_storage',
                            对应库位: 't_base_goods_info_storagelocation',
                            对应商品分类: 't_base_goods_info_waresclass'
                        },//表名
                        module: 'basefiles', //模块名，
                        pojojson: {
                            t_base_goods_info: 'GoodsInfo',
                            t_base_goods_info_meteringunit: 'GoodsInfo_MteringUnitInfo',
                            t_base_goods_info_price: 'GoodsInfo_PriceInfo',
                            t_base_goods_info_storage: 'GoodsInfo_StorageInfo',
                            t_base_goods_info_storagelocation: 'GoodsStorageLocation',
                            t_base_goods_info_waresclass: 'GoodsInfo_WaresClassInfo'
                        }, //实体类名，将和模块名组合成com.hd.agent.basefiles.model.GoodsInfo。
                        type: 'importmore',
                        importparam: '简化版:价格套、默认税种必填',//参数描述
                        version: '2',//导入页面显示哪个版本1：不显示，2：简化版或合同版，3：Excel文件或瑞家txt导入，4：Excel文件或三和txt导入
                        shortcutname: 'goods',
                        majorkey: 'id',
                        childkey: 'goodsid',
                        maintn: 't_base_goods_info',
                        onClose: function () { //导入成功后窗口关闭时操作，
                            $("#wares-table-goodsShortcutList").datagrid('reload');	//更新列表
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesInfoExportBtn.do">
                {
                    type: 'button-export',//导出
                    attr: {
                        datagrid: "#wares-table-goodsShortcutList",
                        queryForm: "#wares-form-goodsShortcutListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                        tnstr: 't_base_goods_info,t_base_goods_info_meteringunit,t_base_goods_info_price,t_base_goods_info_storage,t_base_goods_info_storagelocation,t_base_goods_info_waresclass',//表名
                        tnjson: {
                            t_base_goods_info: '商品列表',
                            t_base_goods_info_meteringunit: '对应辅单位',
                            t_base_goods_info_price: '对应价格套',
                            t_base_goods_info_storage: '对应仓库',
                            t_base_goods_info_storagelocation: '对应库位',
                            t_base_goods_info_waresclass: '对应商品分类'
                        },
                        type: 'exportmore',
                        queryparam: 'id,name,brandid,deptid,defaultsupplier',
                        exportparam: '简化版:若价格套或默认税种为空,则导出数据为空',//参数描述
                        version: '2',
                        shortcutname: 'goods',
                        childkey: 'goodsid',
                        maintn: 't_base_goods_info',
                        name: '商品档案列表'
                    }
                },
                </security:authorize>
                {}
            ],
            buttons: [
                {},
                <security:authorize url="/basefiles/goodesInfoEditMoreBtn.do">
                {
                    id: 'editMore',
                    name: '批量修改',
                    iconCls: 'button-edit',
                    handler: function () {
                        var goodsShortcutRows = $("#wares-table-goodsShortcutList").datagrid('getChecked');
                        if (goodsShortcutRows.length == 0) {
                            $.messager.alert("提醒", "请勾选商品!");
                            return false;
                        }
                        var idStr = "", flagIdStr = "";
                        var unInvNum = 0;
                        for (var i = 0; i < goodsShortcutRows.length; i++) {
                            var id = goodsShortcutRows[i].id;
                            var flag = isDoLockData(id, "t_base_goods_info");
                            if (!flag) {
                                flagIdStr += id + ",";
                                unInvNum++;
                                var index = $("#wares-table-goodsShortcutList").datagrid('getRowIndex', goodsShortcutRows[i]);
                                $("#wares-table-goodsShortcutList").datagrid('uncheckRow', index);
                            }
                            else {
                                idStr += id + ",";
                            }
                        }
                        if (flagIdStr != "") {
                            var unIds = flagIdStr.substring(0, flagIdStr.lastIndexOf(","));
                            $.messager.alert("警告", "" + unIds + "数据正在被其他人操作，暂不能修改！");
                            return false;
                        }
                        $('#wares-dialog-goodsShortcutEditMore').dialog({
                            title: '批量修改商品信息',
                            width: 550,
                            height: 330,
                            closed: false,
                            cache: false,
                            href: 'basefiles/goodsInfoMoreEditPage.do?idStr=' + idStr + '&unInvNum=' + unInvNum,
                            modal: true
                        });
                    }
                },
                </security:authorize>
                //<security:authorize url="/basefiles/goodsInfoConvertmodeBtn.do">
                //{
                //	id:'convertmode',
                //	name:'模式转换',
                //	iconCls:'icon-reload',
                //	handler:function(){
                //		top.addOrUpdateTab('basefiles/showGoodsInfoListPage.do','商品档案列表');
                //	}
                //},
                //</security:authorize>
                <security:authorize url="/basefiles/goodesShortcutPrintBtn.do">
                {
                    id: 'printMenuButton',
                    type: 'menu',
                    name: '打印',
                    iconCls: 'button-print',
                    button: [
                        <security:authorize url="/basefiles/goodsShortcutLocationPrintViewBtn.do">
                        {
                            id: 'printview-id-goodslocation',
                            name: '货位信息打印预览',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/basefiles/goodsShortcutLocationPrintBtn.do">
                        {
                            id: 'print-id-goodslocation',
                            name: '货位信息打印',
                            iconCls: 'button-print',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                    ]
                },
                </security:authorize>
                {}
            ],
            model: 'base',
            type: 'multipleList',
            taburl: '/basefiles/showGoodsShortcutPage.do',
            datagrid: 'wares-table-goodsShortcutList',
            tname: 't_base_goods_info',
            id: ''
        });

        //回车事件
        controlQueryAndResetByKey("wares-query-querygoodsShortcutList", "wares-query-reloadgoodsShortcutList");

        //查询
        $("#wares-query-querygoodsShortcutList").click(function () {
            var queryJSON = $("#wares-form-goodsShortcutListQuery").serializeJSON();
            $("#wares-table-goodsShortcutList").datagrid("load", queryJSON);
        });

        //重置按钮
        $("#wares-query-reloadgoodsShortcutList").click(function () {
            $("#wares-form-goodsShortcutListQuery")[0].reset();
            $("#wares-widget-goodsShortcut-brand").widget('clear');
            $("#wares-widget-goodsShortcut-supplierid").supplierWidget('clear');
            $("#wares-widget-goodsShortcut-deptid").widget('clear');
            var queryJSON = $("#wares-form-goodsShortcutListQuery").serializeJSON();
            $("#wares-table-goodsShortcutList").datagrid("load", queryJSON);

        });
        //通用查询组建调用
        $("#goodsShortcut-query-advanced").advancedQuery({
            //查询针对的表
            name: 'base_goods_info',
            //查询针对的表格id
            datagrid: 'wares-table-goodsShortcutList'
        });
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //货位信息打印
        AgReportPrint.init({
            id: "goodsShortcutList-dialog-print",
            code: "goods_goodslocation",
            tableId: "wares-table-goodsShortcutList",
            url_preview: "print/basefiles/goodsLocationPrintView.do",
            url_print: "print/basefiles/goodsLocationPrint.do",
            libtype: 'withbarcode',
            btnPreview: "printview-id-goodslocation",
            btnPrint: "print-id-goodslocation"
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
