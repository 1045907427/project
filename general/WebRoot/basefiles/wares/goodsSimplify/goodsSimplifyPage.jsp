<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>商品档案页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<input type="hidden" id="goodsInfo-opera"/>
<input type="hidden" id="goodsInfo-id-hidden" value="<c:out value="${id}"></c:out>"/>
<input type="hidden" id="goodsInfo-defaultWCid" value="<c:out value="${WCid }"></c:out>"/>
<div class="easyui-layout" data-options="fit:true,border:true">
    <div data-options="region:'north',split:false,border:false" style="height: 30px;overflow: hidden">
        <div class="buttonBG" id="goodsInfo-div-button"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div class="easyui-panel" data-options="fit:true" id="wares-div-goodsInfo"></div>
    </div>
</div>
<script type="text/javascript">
    var goods_title = tabsWindowTitle('/basefiles/showGoodsSimplifyListPage.do');
    var goodsInfo_AjaxConn = function (Data, Action) {
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
    //加载下拉框
    function loadDropdown() {
        //商品分类
        $("#goodsSimplify-widget-defaultsort").widget({
            name: 't_base_goods_info',
            col: 'defaultsort',
            singleSelect: true,
            required: true,
            isall: true,
            onlyLeafCheck: true,
            width: 135
        });
        //商品品牌
        $("#goodsSimplify-widget-brand").widget({
            name: 't_base_goods_info',
            col: 'brand',
            singleSelect: true,
            required: true,
            onlyLeafCheck: false,
            onSelect: function (data) {
                $("#goodsSimplify-widget-deptid").widget('setValue', data.deptid);
                $("#goodsSimplify-supplierWidget").supplierWidget('setValue', data.supplierid);
                $("#goodsSimplify-supplierWidget").supplierWidget('setText', data.suppliername);
            },
            onClear: function () {
                $("#goodsSimplify-widget-deptid").widget('clear');
                $("#goodsSimplify-supplierWidget").supplierWidget('clear');
            }
        });

        //所属部门
        $("#goodsSimplify-widget-deptid").widget({
            width: 146,
            name: 't_base_goods_info',
            col: 'deptid',
            singleSelect: true,
            required: true,
            onlyLeafCheck: true
        });

        //默认供应商
        $("#goodsSimplify-supplierWidget").widget({
            referwid: 'RL_T_BASE_BUY_SUPPLIER',
            required: true
        });
        //主单位
        $("#goodsSimplify-widget-mainunit").widget({
            name: 't_base_goods_info',
            col: 'mainunit',
            singleSelect: true,
            required: true,
            onlyLeafCheck: false,
            onSelect: function (data) {
                $("#goodsSimplify-widget-meteringunitid").widget({
                    referwid: 'RL_T_BASE_GOODS_METERINGUNIT',
                    singleSelect: true,
                    required: true,
                    param: [{field: 'id', op: 'notequal', value: data.id}],
                    onlyLeafCheck: false
                });
            }
        });
        //辅单位
        $("#goodsSimplify-widget-meteringunitid").widget({
            width: 140,
            referwid: 'RL_T_BASE_GOODS_METERINGUNIT',
            singleSelect: true,
            required: true,
            param: [{field: 'id', op: 'equal', value: $("#goodsInfo-mainunit").val()}],
            onlyLeafCheck: false
        });

        //箱装量
        $("#goodsSimplify-numberbox-rate").numberbox({
            required: true,
            min: 0,
            max: 999999999999,
            precision:${decimallen},
            groupSeparator: ',',
            onChange: function (newValue, oldValue) {
                if (newValue == Number(0)) {
                    $(this).numberbox('setValue', "9999");
                }
                var highestbuyprice = $("#goodsSimplify-numberbox-highestbuyprice").numberbox('getValue');
                if ("" != highestbuyprice && Number(highestbuyprice) != 0) {
                    var buyboxprice = highestbuyprice * newValue;
                    $("#goodsSimplify-numberbox-buyboxprice").numberbox('setValue', buyboxprice);
                }
            }
        });

        //长度
        $("#goodsSimplify-numberbox-glength").numberbox({
            min: 0,
            max: 999999999999,
            precision: 6,
            groupSeparator: ',',
            onChange: function (newValue, oldValue) {
                var ghight = $("#goodsSimplify-numberbox-ghight").numberbox('getValue');
                var gwidth = $("#goodsSimplify-numberbox-gwidth").numberbox('getValue');
                var totalvolume = newValue * ghight * gwidth;
                $("#goodsSimplify-numberbox-totalvolume").numberbox('setValue', totalvolume);
            }
        });
        //高度
        $("#goodsSimplify-numberbox-ghight").numberbox({
            min: 0,
            max: 999999999999,
            precision: 6,
            groupSeparator: ',',
            onChange: function (newValue, oldValue) {
                var glength = $("#goodsSimplify-numberbox-glength").numberbox('getValue');
                var gwidth = $("#goodsSimplify-numberbox-gwidth").numberbox('getValue');
                var totalvolume = newValue * glength * gwidth;
                $("#goodsSimplify-numberbox-totalvolume").numberbox('setValue', totalvolume);
            }
        });
        //宽度
        $("#goodsSimplify-numberbox-gwidth").numberbox({
            width: 135,
            min: 0,
            max: 999999999999,
            precision: 6,
            groupSeparator: ',',
            onChange: function (newValue, oldValue) {
                var glength = $("#goodsSimplify-numberbox-glength").numberbox('getValue');
                var ghight = $("#goodsSimplify-numberbox-ghight").numberbox('getValue');
                var totalvolume = newValue * glength * ghight;
                $("#goodsSimplify-numberbox-totalvolume").numberbox('setValue', totalvolume);
            }
        });

        //最高采购价
        $("#goodsSimplify-numberbox-highestbuyprice").numberbox({
            required: true,
            min: 0,
            max: 999999999999,
            precision: 6,
            groupSeparator: ',',
            onChange: function (newValue, oldValue) {
                $("#goodsSimplify-numberbox-newbuyprice").numberbox('setValue', newValue);
                var boxnum = $("#goodsSimplify-numberbox-rate").numberbox('getValue');
                if ("" == boxnum || Number(boxnum) == 0) {
                    boxnum = Number(1);
                }
                var buyboxprice = boxnum * newValue;
                $("#goodsSimplify-numberbox-buyboxprice").numberbox('setValue', buyboxprice);
            }
        });

        //采购箱价
        $("#goodsSimplify-numberbox-buyboxprice").numberbox({
            min: 0,
            max: 999999999999,
            precision: 6,
            groupSeparator: ',',
            onChange: function (newValue, oldValue) {
                var boxnum = $("#goodsSimplify-numberbox-rate").numberbox('getValue');
                if ("" == boxnum || Number(boxnum) == 0) {
                    boxnum = Number(1);
                }
                var highestbuyprice = newValue / boxnum;
                $("#goodsSimplify-numberbox-highestbuyprice").numberbox('setValue', highestbuyprice);
            }
        });

        //默认仓库
        $("#goodsSimplify-widget-storage").widget({
            width: 140,
            name: 't_base_goods_info',
            col: 'storageid',
            singleSelect: true,
            required: true,
            onlyLeafCheck: false
        });
        //默认库位
        $("#goodsSimplify-widget-storagelocation").widget({
            width: 140,
            name: 't_base_goods_info',
            col: 'storagelocation',
            singleSelect: true,
            onlyLeafCheck: false,
            onChecked: function (data, checked) {
                if (checked) {
                    document.getElementById("goodsSimplify-div-boxnumtext").style.visibility = "visible";
                    document.getElementById("goodsSimplify-div-boxnum").style.visibility = "visible";
                    $("#goodsShortcut-numberbox-boxnum").numberbox({
                        required: true,
                        min: 0,
                        precision: 0,
                        groupSeparator: ','
                    });
                    $("#goodsShortcut-numberbox-boxnum").focus();
                } else {
                    document.getElementById("goodsSimplify-div-boxnumtext").style.visibility = "hidden";
                    document.getElementById("goodsSimplify-div-boxnum").style.visibility = "hidden";
                    $("#goodsShortcut-numberbox-boxnum").removeClass();
                }
            },
            onLoadSuccess: function () {
                var val = $(this).widget('getValue');
                if (null != val && val != "") {
                    document.getElementById("goodsSimplify-div-boxnumtext").style.visibility = "visible";
                    document.getElementById("goodsSimplify-div-boxnum").style.visibility = "visible";
                    $("#goodsShortcut-numberbox-boxnum").numberbox({
                        required: true,
                        min: 0,
                        precision: 0,
                        groupSeparator: ','
                    });
                    $("#goodsShortcut-numberbox-boxnum").focus();
                } else {
                    document.getElementById("goodsSimplify-div-boxnumtext").style.visibility = "hidden";
                    document.getElementById("goodsSimplify-div-boxnum").style.visibility = "hidden";
                    $("#goodsShortcut-numberbox-boxnum").removeClass();
                }
            },
            onClear: function () {
                document.getElementById("goodsSimplify-div-boxnumtext").style.visibility = "hidden";
                document.getElementById("goodsSimplify-div-boxnum").style.visibility = "hidden";
                $("#goodsShortcut-numberbox-boxnum").removeClass();
            }
        });
        //默认税种
        $("#goodsSimplify-widget-defaulttaxtype").widget({
            width: 140,
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
                    var ret = goodsInfo_AjaxConn({id: value}, 'basefiles/isRepeatGoodsInfoID.do');//true 重复，false 不重复
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
                    var ret = goodsInfo_AjaxConn({name: value}, 'basefiles/isRepeatGoodsInfoName.do');//true 重复，false 不重复
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
        maxLen: {//最大长度
            validator: function (value, param) {
                if (value.length <= param[0]) {
                    return true;
                }
                else {
                    $.fn.validatebox.defaults.rules.maxLen.message = '最多可输入{0}个字符!';
                    return false;
                }
            },
            message: ''
        },
        isRepeatBoxbarcode: {//唯一性,最大长度
            validator: function (value, param) {
                if (value.length <= param[0]) {
                    var ret = goodsInfo_AjaxConn({boxbarcode: value}, 'basefiles/isRepeatGoodsInfoBoxbarcode.do');//true 重复，false 不重复
                    var retJson = $.parseJSON(ret);
                    if (retJson.flag) {
                        $.fn.validatebox.defaults.rules.isRepeatBoxbarcode.message = '箱装条形码重复, 请重新输入!';
                        return false;
                    }
                }
                else {
                    $.fn.validatebox.defaults.rules.isRepeatBoxbarcode.message = '最多可输入{0}个字符!';
                    return false;
                }
                return true;
            },
            message: ''
        },
        isRepeatItemno: {//唯一性,最大长度
            validator: function (value, param) {
                if (value.length <= param[0]) {
                    var ret = goodsInfo_AjaxConn({itemno: value}, 'basefiles/isRepeatGoodsInfoItemno.do');//true 重复，false 不重复
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
        },
        minVal: {//最小值大于0
            validator: function (value, param) {
                return parseFloat(value) > 0 || parseInt(value);
            },
            message: '输入的值必须大于0'
        },
        barcodeVal: {//条形码规则
            validator: function (value, param) {
                var reg = eval("/^[A-Za-z0-9]{0," + param[0] + "}$/");
                return reg.test(value)
            },
            message: '条形码错误,请重新输入'
        }
    });

    //判断是否加锁
    function isLockData(id, tablename) {
        var flag = false;
        $.ajax({
            url: 'system/lock/isLockData.do',
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

    //页面刷新
    function panelRefresh(url, title, type) {
        $("#wares-div-goodsInfo").panel({
            href: url,
            title: title,
            cache: false,
            maximized: true,
            border: false,
            loadingMessage: '数据加载中...'
        });
        $("#goodsInfo-opera").attr("value", type);
    }

    $(function () {
        //加载按钮
        $("#goodsInfo-div-button").buttonWidget({
            //初始默认按钮 根据type对应按钮事件
            initButton: [
                {},
                <security:authorize url="/basefiles/goodesSimplifyAddBtn.do">
                {
                    type: 'button-add',//新增
                    handler: function () {
                        panelRefresh('basefiles/showGoodsSimplifyAddPage.do', ' 商品档案【新增】', 'add');
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesSimplifyEditBtn.do">
                {
                    type: 'button-edit',//修改
                    handler: function () {
                        var id = $("#goodsInfo-id-baseInfo").val();
                        var flag = isDoLockData(id, "t_base_goods_info");
                        if (!flag) {
                            $.messager.alert("警告", "该数据正在被其他人操作，暂不能修改！");
                            return false;
                        }
                        panelRefresh('basefiles/showGoodsSimplifyEditPage.do?id=' + id, ' 商品档案【修改】', 'edit');
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesSimplifySaveBtn.do">
                {
                    type: 'button-save',//保存
                    handler: function () {
                        var type = $("#goodsInfo-div-button").buttonWidget("getOperType");
                        if (!$("#goodssimplify-form-add").form('validate')) {
                            return false;
                        }
                        $.messager.confirm("提醒", "是否保存商品档案?", function (r) {
                            if (r) {
                                loading("提交中..");
                                if (type == "add") {
                                    var ret = goodsInfo_AjaxConn($("#goodssimplify-form-add").serializeJSON(), 'basefiles/addGoodsInfoShortcut.do');
                                    var retJson = $.parseJSON(ret);
                                    if (retJson.flag) {
                                        if (top.$('#tt').tabs('exists', goods_title)) {
                                            var queryJSON = tabsWindow(goods_title).$("#wares-form-goodsInfoListQuery").serializeJSON();
                                            tabsWindow(goods_title).$("#wares-table-goodsInfoList").datagrid("load", queryJSON);
                                        }
                                        $("#goodsInfo-div-button").buttonWidget("addNewDataId", $("#goodsInfo-id-baseInfo").val());
                                        panelRefresh('basefiles/showGoodsSimplifyViewPage.do?id=' + $("#goodsInfo-id-baseInfo").val(), ' 商品档案【详情】', 'view');
                                        $.messager.alert("提醒", "新增成功!");
                                    } else {
                                        $.messager.alert("提醒", "新增失败!");
                                    }
                                }
                                else {
                                    loading("提交中..");
                                    var ret = goodsInfo_AjaxConn($("#goodssimplify-form-add").serializeJSON(), 'basefiles/editGoodsInfoShortcut.do');
                                    var retJson = $.parseJSON(ret);
                                    if (retJson.flag) {
                                        if (top.$('#tt').tabs('exists', goods_title)) {
                                            var queryJSON = tabsWindow(goods_title).$("#wares-form-goodsInfoListQuery").serializeJSON();
                                            tabsWindow(goods_title).$("#wares-table-goodsInfoList").datagrid("load", queryJSON);
                                        }
                                        panelRefresh('basefiles/showGoodsSimplifyViewPage.do?id=' + $("#goodsInfo-id-baseInfo").val(), ' 商品档案【详情】', 'view');
                                        $.messager.alert("提醒", "修改成功!");
                                    } else {
                                        $.messager.alert("提醒", "修改失败!");
                                    }
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesSimplifyDeleteBtn.do">
                {
                    type: 'button-delete',//删除
                    handler: function () {
                        var flag = isLockData($("#goodsInfo-id-baseInfo").val(), "t_base_goods_info");
                        if (flag) {
                            $.messager.alert("警告", "该数据正在被其他人操作，暂不能删除！");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否确定删除商品档案?", function (r) {
                            if (r) {
                                var id = $("#goodsInfo-id-baseInfo").val();
                                var ret = goodsInfo_AjaxConn({idStr: id}, 'basefiles/deleteGoodsInfos.do');
                                var retJSON = $.parseJSON(ret);
                                if (retJSON.flag) {
                                    $.messager.alert("提醒", "" + retJSON.userNum + "条记录被引用,不允许删除;<br/>删除成功" + retJSON.num + "条记录;");
                                    if (top.$('#tt').tabs('exists', goods_title)) {
                                        var queryJSON = tabsWindow(goods_title).$("#wares-form-goodsInfoListQuery").serializeJSON();
                                        tabsWindow(goods_title).$("#wares-table-goodsInfoList").datagrid("load", queryJSON);
                                    }
                                    var object = $("#goodsInfo-div-button").buttonWidget("removeData", id);
                                    if (null != object) {
                                        panelRefresh('basefiles/showGoodsSimplifyViewPage.do?id=' + object.id, ' 商品档案【查看】', 'view');
                                    }
                                    else {
                                        top.$('#tt').tabs('close', '商品档案');
                                    }
                                }
                                else {
                                    $.messager.alert("提醒", "" + retJSON.userNum + "条记录被引用,不允许删除;<br/>删除失败" + retJSON.num + "条记录;");
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesSimplifyCopyBtn.do">
                {
                    type: 'button-copy',//复制
                    handler: function () {
                        var id = $("#goodsInfo-id-baseInfo").val();
                        panelRefresh('basefiles/showGoodsSimplifyCopyPage.do?id=' + id, ' 商品档案【复制】', 'copy');
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesSimplifyEnableBtn.do">
                {
                    type: 'button-open',//启用
                    handler: function () {
                        $.messager.confirm("提醒", "是否确定启用商品档案?", function (r) {
                            if (r) {
                                var ret = goodsInfo_AjaxConn({idStr: $("#goodsInfo-id-baseInfo").val()}, 'basefiles/enableGoodsInfos.do');
                                var retJSON = $.parseJSON(ret);
                                if (retJSON.flag) {
                                    $.messager.alert("提醒", "启用无效" + retJSON.invalidNum + "条记录;<br/>启用成功" + retJSON.num + "条记录;");
                                    if (top.$('#tt').tabs('exists', goods_title)) {
                                        var queryJSON = tabsWindow(goods_title).$("#wares-form-goodsInfoListQuery").serializeJSON();
                                        tabsWindow(goods_title).$("#wares-table-goodsInfoList").datagrid("load", queryJSON);
                                    }
                                    $("#wares-div-goodsInfo").panel('refresh', 'basefiles/showGoodsSimplifyViewPage.do?id=' + $("#goodsInfo-id-baseInfo").val());
                                }
                                else {
                                    $.messager.alert("提醒", "启用无效" + retJSON.invalidNum + "条记录;<br/>启用失败" + retJSON.num + "条记录;");
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesSimplifyDisableBtn.do">
                {
                    type: 'button-close',//禁用
                    handler: function () {
                        $.messager.confirm("提醒", "是否确定禁用商品档案?", function (r) {
                            if (r) {
                                var ret = goodsInfo_AjaxConn({idStr: $("#goodsInfo-id-baseInfo").val()}, 'basefiles/disableGoodsInfos.do');
                                var retJSON = $.parseJSON(ret);
                                if (retJSON.flag) {
                                    $.messager.alert("提醒", "" + retJSON.invalidNum + "条记录状态不允许禁用;<br/>禁用成功" + retJSON.num + "条记录;");
                                    if (top.$('#tt').tabs('exists', goods_title)) {
                                        var queryJSON = tabsWindow(goods_title).$("#wares-form-goodsInfoListQuery").serializeJSON();
                                        tabsWindow(goods_title).$("#wares-table-goodsInfoList").datagrid("load", queryJSON);
                                    }
                                    $("#wares-div-goodsInfo").panel('refresh', 'basefiles/showGoodsSimplifyViewPage.do?id=' + $("#goodsInfo-id-baseInfo").val());
                                }
                                else {
                                    $.messager.alert("提醒", "" + retJSON.invalidNum + "条记录状态不允许禁用;<br/>禁用失败" + retJSON.num + "条记录;");
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesSimplifyBackBtn.do">
                {
                    type: 'button-back',//上一条
                    handler: function (data) {
                        if ("${listType}" == "1") {
                            panelRefresh('basefiles/showGoodsSimplifyViewPage.do?id=' + data.goodsid, ' 商品档案【查看】', 'view');
                        }
                        else if ("${listType}" == "2") {
                            panelRefresh('basefiles/showGoodsSimplifyViewPage.do?id=' + data.id, ' 商品档案【查看】', 'view');
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesSimplifyNextBtn.do">
                {
                    type: 'button-next',//下一条
                    handler: function (data) {
                        if ("${listType}" == "1") {
                            panelRefresh('basefiles/showGoodsSimplifyViewPage.do?id=' + data.goodsid, ' 商品档案【查看】', 'view');
                        }
                        else if ("${listType}" == "2") {
                            panelRefresh('basefiles/showGoodsSimplifyViewPage.do?id=' + data.id, ' 商品档案【查看】', 'view');
                        }
                    }
                },
                </security:authorize>
                {}
            ],
            buttons: [
                {},
                <security:authorize url="/basefiles/goodesSimplifyPrintBtn.do">
                {
                    id: 'printMenuButton',
                    type: 'menu',
                    name: '打印',
                    iconCls: 'button-print',
                    button: [
                        <security:authorize url="/basefiles/goodsSimplifyLocationPrintViewBtn.do">
                        {
                            id: 'printview-id-goodslocation',
                            name: '货位信息打印预览',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/basefiles/goodsSimplifyLocationPrintBtn.do">
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
            type: 'view',
            tab: goods_title,
            datagrid: '${listid}',
            id: $("#goodsInfo-id-hidden").val(),
            tname: 't_base_goods_info'
        });

        //加载新增商品页面
        var goodsInfo_url = "";
        var goodsInfo_opera = "add";
        var goodsInfo_type = "add";
        var goodsInfo_title = "商品档案【新增】";
        if ("${type}" == "view") {
            goodsInfo_url = "basefiles/showGoodsSimplifyViewPage.do?id=${id}";
            goodsInfo_opera = "view";
            goodsInfo_type = "view";
            goodsInfo_title = "商品档案【查看】";
        }
        else if ("${type}" == "edit") {
            goodsInfo_url = "basefiles/showGoodsSimplifyEditPage.do?id=${id}";
            goodsInfo_opera = "edit";
            goodsInfo_type = "edit";
            goodsInfo_title = "商品档案【修改】";
        }
        else if ("${type}" == "copy") {
            goodsInfo_url = "basefiles/showGoodsSimplifyCopyPage.do?id=${id}";
            goodsInfo_type = "copy";
            goodsInfo_title = "商品档案【复制】";
        }
        else {
            goodsInfo_url = "basefiles/showGoodsSimplifyAddPage.do?WCid=${WCid}";
        }
        $("#wares-div-goodsInfo").panel({
            href: goodsInfo_url,
            title: goodsInfo_title,
            cache: false,
            maximized: true,
            border: false,
            close: true
        });

        $("#goodsInfo-opera").attr("value", goodsInfo_opera);
        $("#goodsInfo-div-button").buttonWidget("initButtonType", goodsInfo_opera);
        $("#goodsInfo-div-button").buttonWidget("setButtonType", "${state}");

    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //货位信息打印
        AgReportPrint.init({
            id: "listPage-goodslocation-dialog-print",
            code: "goods_goodslocation",
            url_preview: "print/basefiles/goodsLocationPrintView.do",
            url_print: "print/basefiles/goodsLocationPrint.do",
            libtype: 'withbarcode',
            btnPreview: "printview-id-goodslocation",
            btnPrint: "print-id-goodslocation",
            getData: function (tableId, printParam) {
                var id = $("#goodsInfo-id-baseInfo").val();
                if (id == "") {
                    $.messager.alert("警告", "找不到要打印预览的信息!");
                    return false;
                }
                printParam.idarrs = id;
                return true;
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
