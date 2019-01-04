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
<input type="hidden" id="goodsInfo-index-priceList"/>
<input type="hidden" id="goodsInfo-index-MUList"/>
<input type="hidden" id="goodsInfo-index-storageList"/>
<input type="hidden" id="goodsInfo-index-WCList"/>
<input type="hidden" id="goodsInfo-index-SLList"/>
<input type="hidden" id="goodsInfo-MUIds-MUSole"/>
<input type="hidden" id="goodsInfo-defaulttaxtype"/>
<input type="hidden" id="goodsInfo-meteringUnit"/>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',split:false,border:false" style="height: 30px;overflow: hidden">
        <div class="buttonBG" id="goodsInfo-div-button"></div>
    </div>
    <div data-options="region:'center'" style="border: 0px;">
        <div class="easyui-panel" data-options="fit:true" id="wares-div-goodsInfo"></div>
        <div id="goodsInfo-window-showOldImg"></div>
    </div>
</div>
<div id="goodsInfo-dialog-addOper" class="easyui-dialog" closed="true"></div>
<script type="text/javascript">
    var goods_title = tabsWindowTitle('/basefiles/showGoodsInfoListPage.do');

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
        })
        return MyAjax.responseText;
    }
    //加载下拉框
    function loadDropdown() {

        //主计量单位 -商品档案档案
        $("#goodsInfo-widget-meteringUnit").widget({
            width: 120,
            name: 't_base_goods_info',
            col: 'mainunit',
            singleSelect: true,
            required: true,
            onlyLeafCheck: false,
            onSelect: function (data) {
                $("#goodsInfo-meteringUnit").val(data.id);
            }
        });

        //商品品牌
        $("#goodsInfo-widget-brand").widget({
            width: 120,
            name: 't_base_goods_info',
            col: 'brand',
            singleSelect: true,
            required: true,
            onlyLeafCheck: false,
            onSelect: function (data) {
                $("#goodsInfo-widget-deptid").widget('setValue', data.deptid);
                $("#goodsInfo-supplierWidget-defaultsupplier").supplierWidget('setValue', data.supplierid);
                $("#goodsInfo-supplierWidget-defaultsupplier").supplierWidget('setText', data.suppliername);
            },
            onClear: function () {
                $("#goodsInfo-widget-deptid").widget('clear');
                $("#goodsInfo-supplierWidget-defaultsupplier").supplierWidget('clear');
            }
        });

        //所属部门
        $("#goodsInfo-widget-deptid").widget({
            width: 120,
            name: 't_base_goods_info',
            col: 'deptid',
            singleSelect: true,
            required: true,
            onlyLeafCheck: true
        });

        //默认分类
        $("#goodsInfo-widget-waresClass").widget({
            width: 120,
            name: 't_base_goods_info',
            col: 'defaultsort',
            singleSelect: true,
            required: true,
            onlyLeafCheck: true,
            onLoadSuccess: function () {
                return true;
            }
        });

        //默认仓库
        $("#goodsInfo-widget-storage").widget({
            width: 120,
            name: 't_base_goods_info',
            col: 'storageid',
            singleSelect: true,
            required: true,
            onlyLeafCheck: false,
            onLoadSuccess: function () {
                return true;
            }
        });

        //默认库位
        $("#goodsInfo-widget-storagelocation").widget({
            width: 120,
            name: 't_base_goods_info',
            col: 'storagelocation',
            singleSelect: true,
            onlyLeafCheck: false,
            onLoadSuccess: function () {
                return true;
            }
        });

        //状态
        $('#goodsInfo-widget-state').widget({
            width: 120,
            name: 't_base_goods_info',
            col: 'state',
            singleSelect: true
        });

        //商品类型
        $('#goodsInfo-widget-goodstype').widget({
            width: 120,
            name: 't_base_goods_info',
            required: true,
            col: 'goodstype',
            singleSelect: true
        });

        //购销类型
        $('#goodsInfo-widget-bstype').widget({
            width: 120,
            required: true,
            name: 't_base_goods_info',
            col: 'bstype',
            singleSelect: true
        });

        //盘点方式
        $('#goodsInfo-widget-checktype').widget({
            width: 120,
            name: 't_base_goods_info',
            col: 'checktype',
            singleSelect: true
        });

        //商品形状
        $('#goodsInfo-widget-gshape').widget({
            width: 120,
            name: 't_base_goods_info',
            col: 'gshape',
            singleSelect: true
        });

        //默认采购员
        $("#goodsInfo-widget-defaultbuyer").widget({
            width: 120,
            name: 't_base_goods_info',
            col: 'defaultbuyer',
            singleSelect: true,
            onlyLeafCheck: false,
            onLoadSuccess: function () {
                return true;
            }
        });

        //默认业务员
        $("#goodsInfo-widget-defaultsaler").widget({
            width: 120,
            name: 't_base_goods_info',
            col: 'defaultsaler',
            singleSelect: true,
            onlyLeafCheck: false,
            onLoadSuccess: function () {
                return true;
            }
        });

        //默认供应商
        $("#goodsInfo-supplierWidget-defaultsupplier").supplierWidget({
            required: true
        });

        //默认税种
        $("#goodsInfo-widget-defaulttaxtype").widget({
            width: 120,
            name: 't_base_goods_info',
            col: 'defaulttaxtype',
            singleSelect: true,
            required: true,
            onlyLeafCheck: false,
            onSelect: function (data) {
                $("#goodsInfo-defaulttaxtype").val(data.id);
                if ($dgPriceInfoList != null) {
                    $dgPriceInfoList.removeClass("create-datagrid");
                }
                //$(".tags").find("li").eq(2).trigger("click");
            },
            onLoadSuccess: function () {
                var taxtype = $("#goodsInfo-widget-defaulttaxtype").widget("getValue");
                $("#goodsInfo-defaulttaxtype").val(taxtype);
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
                var boxnum = $("#goodsInfo-hidden-boxnum").val();
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
                var boxnum = $("#goodsInfo-hidden-boxnum").val();
                if ("" == boxnum || Number(boxnum) == 0) {
                    boxnum = Number(1);
                }
                var highestbuyprice = newValue / boxnum;
                $("#goodsInfo-numberbox-highestbuyprice").numberbox('setValue', highestbuyprice);
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
        //毛重
        $("#goodsInfo-numberbox-grossweight").numberbox({
            min: 0,
            max: 999999999999,
            precision: 6,
            groupSeparator: ',',
            onChange: function (newValue, oldValue) {
                var boxnum = $("#goodsInfo-hidden-boxnum").val();
                if ("" == boxnum) {
                    boxnum = Number(1);
                }
                var totalweight = $("#goodsInfo-numberbox-totalweight").numberbox('getValue');
                if ("" == totalweight || Number(totalweight) == 0) {
                    totalweight = newValue * boxnum;
                    $("#goodsInfo-numberbox-totalweight").numberbox('setValue', totalweight);
                }
            }
        });
        //箱重
        $("#goodsInfo-numberbox-totalweight").numberbox({
            min: 0,
            max: 999999999999,
            precision: 6,
            groupSeparator: ',',
            onChange: function (newValue, oldValue) {
                var boxnum = $("#goodsInfo-hidden-boxnum").val();
                if ("" == boxnum) {
                    boxnum = Number(1);
                }
                if (0 != Number(boxnum)) {
                    var grossweight = newValue / boxnum;
                    $("#goodsInfo-numberbox-grossweight").numberbox('setValue', grossweight);
                }
            }
        });
        //箱体积
        $("#goodsInfo-numberbox-totalvolume").numberbox({
            min: 0,
            max: 999999999999,
            precision: 6,
            groupSeparator: ',',
            onChange: function (newValue, oldValue) {
                var boxnum = $("#goodsInfo-hidden-boxnum").val();
                if ("" == boxnum) {
                    boxnum = Number(1);
                }
                if (0 != Number(boxnum)) {
                    var singlevolume = newValue / boxnum;
                    $("#goodsInfo-numberbox-singlevolume").numberbox('setValue', singlevolume);
                }
            }
        });
        //单体积
        $("#goodsInfo-numberbox-singlevolume").numberbox({
            min: 0,
            max: 999999999999,
            precision: 6,
            groupSeparator: ',',
            onChange: function (newValue, oldValue) {
                var boxnum = $("#goodsInfo-hidden-boxnum").val();
                if ("" == boxnum) {
                    boxnum = Number(1);
                }
                var totalvolume = $("#goodsInfo-numberbox-totalvolume").numberbox('getValue');
                if ("" == totalvolume || Number(totalvolume) == 0) {
                    totalvolume = newValue * boxnum;
                    $("#goodsInfo-numberbox-totalvolume").numberbox('setValue', totalvolume);
                }
            }
        });

    }

    /**
     * 生成随机id
     * @return
     */
    goodsInfo_getRandomid = function () {
        return parseInt(100000000000 * Math.random());
    }

    //结束行编辑
    var goodsInfo_editIndexPrice = undefined;
    var goodsInfo_editIndexMU = undefined;
    var goodsInfo_editIndexStorage = undefined;
    var goodsInfo_editIndexWC = undefined;
    var goodsInfo_editIndexSL = undefined;
    function goodsInfo_endEditingPrice() {
        if (goodsInfo_editIndexPrice == undefined) {
            return true
        } else {
            return false;
        }
    }
    function goodsInfo_endEditingMU() {
        if (goodsInfo_editIndexMU == undefined) {
            return true
        } else {
            return false;
        }
    }
    function goodsInfo_endEditingStorage() {
        if (goodsInfo_editIndexStorage == undefined) {
            return true
        } else {
            return false;
        }
    }
    function goodsInfo_endEditingWC() {
        if (goodsInfo_editIndexWC == undefined) {
            return true
        } else {
            return false;
        }
    }
    function goodsInfo_endEditingSL() {
        if (goodsInfo_editIndexSL == undefined) {
            return true
        } else {
            return false;
        }
    }

    //1含税单价或2无税单价改变计算对应数据
    function goods_taxpriceChange(row) {
        var rowIndex = $dgPriceInfoList.datagrid("getRowIndex", row);
        var et = $dgPriceInfoList.datagrid('getEditor', {index: rowIndex, field: 'taxprice'});
        var ep = $dgPriceInfoList.datagrid('getEditor', {index: rowIndex, field: 'price'});
        if (et != null) {
            var taxprice = Number(et.target[0].value);
            var oldTaxPrice = Number(et.oldHtml);
            if (taxprice != oldTaxPrice) {
                var ret = goodsInfo_AjaxConn({
                    type: '1',
                    taxtypeRate: row.taxtypeRate,
                    taxprice: taxprice
                }, 'basefiles/getPriceChanger.do');
                var retJson = $.parseJSON(ret);
                $(ep.target).numberbox('setValue', retJson.price);
            }
        }
    }
    var $dgPriceInfoList = null, $dgMeteringUnitInfoList = null, $dgStorageInfoList = null, $dgWaresClassInfoList = null, $dgStorageLocationInfoList = null;
    //获取标签页
    function getTabs(type) {
        var priceUrl = "", MUUrl = "", storageUrl = "", WCUrl = "", SLUrl = "";
        if (type == "copy") {
            var id = encodeURIComponent($("#goodsInfo-hdid-baseInfo").val());
            MUUrl = "basefiles/showMeteringUnitInfoList.do?goodsid=" + id;
            storageUrl = "basefiles/showStorageInfoList.do?goodsid=" + id;
            WCUrl = "basefiles/showWaresClassInfoList.do?goodsid=" + id;
            SLUrl = "basefiles/showGoodsStorageLocationList.do?goodsid=" + id;
        }
        else if (type == "edit" || type == "view") {
            var id = encodeURIComponent($("#goodsInfo-id-baseInfo").val());
            MUUrl = "basefiles/showMeteringUnitInfoList.do?goodsid=" + id;
            storageUrl = "basefiles/showStorageInfoList.do?goodsid=" + id;
            WCUrl = "basefiles/showWaresClassInfoList.do?goodsid=" + id;
            SLUrl = "basefiles/showGoodsStorageLocationList.do?goodsid=" + id;
        }
        $(".tags").find("li").click(function () {
            var index = $(this).index();
            $(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
            $(".tagsDiv .tagsDiv_item").hide().eq(index).show();
            if (index == 2) {
                $dgPriceInfoList = $("#goodsInfo-table-priceInfoList");
                var taxtype = $("#goodsInfo-defaulttaxtype").val();
                var oldid = encodeURIComponent($("#goodsInfo-hidden-oldId").val());
                priceUrl = "basefiles/showPriceInfoList.do?type=" + type + "&goodsid=" + id + "&taxtype=" + taxtype + "&oldgoodsid=" + oldid;
                if (!$dgPriceInfoList.hasClass("create-datagrid")) {
                    $dgPriceInfoList.datagrid({
                        method: 'post',
                        title: '',
                        rownumbers: true,
                        singleSelect: true,
                        url: priceUrl,
                        border: false,
                        columns: [[
                            {field: 'id', title: '编号', width: 150, hidden: true},
                            {field: 'goodsid', title: '商品编码', width: 150, hidden: true},
                            {field: 'code', title: '价格套编码', width: 100, hidden: true},
                            {field: 'name', title: '价格套名称', width: 100, align: 'center'},
                            {
                                field: 'taxprice', title: '含税单价', width: 80, align: 'right',
                                editor: {
                                    type: 'numberbox',
                                    options: {
                                        precision: 2,
                                        max: 999999999999,
                                        min: 0,
                                        onChange: function (newValue, oldValue) {
                                            var row = $dgPriceInfoList.datagrid("getSelected");
                                            goods_taxpriceChange(row);
                                        }
                                    }
                                },
                                formatter: function (val, rowData, rowIndex) {
                                    if (null != val && "" != val) {
                                        return formatterMoney(val);
                                    }
                                }
                            },
                            {
                                field: 'taxtype', title: '税种', width: 100, align: 'center',
                                formatter: function (val, rowData, rowIndex) {
                                    return rowData.taxtypeName;
                                }
                            },
                            {
                                field: 'price', title: '无税单价', width: 85, align: 'right',
                                editor: {
                                    type: 'numberbox',
                                    options: {
                                        precision: 2,
                                        max: 999999999999,
                                        min: 0,
                                        disabled: true
                                    }
                                },
                                formatter: function (val, rowData, rowIndex) {
                                    return formatterMoney(val);
                                }
                            },
                            {field: 'remark', title: '备注', width: 150, align: 'center', editor: 'text'}
                        ]],
                        onDblClickRow: function (rowIndex, rowData) {
                            var taxType = $("#goodsInfo-widget-defaulttaxtype").widget("getValue");
                            if (taxType == "" || rowData.taxtypeRate == null) {
                                $("#goodsInfo-widget-defaulttaxtype").widget("clear");
                                $.messager.alert("提醒", "请输入默认税种!");
                                $(".tags").find("li").eq(1).trigger("click");
                                return false;
                            }
                            if (goodsInfo_endEditingPrice()) {
                                $("#goodsInfo-oldTaxPrice-price").val(rowData.taxprice);
                                $("#goodsInfo-oldPrice-price").val(rowData.price);
                                $("#goodsInfo-index-priceList").val(rowIndex);
                                $dgPriceInfoList.datagrid('beginEdit', rowIndex);
                                goodsInfo_editIndexPrice = rowIndex;
                                $dgPriceInfoList.datagrid('selectRow', rowIndex);
                            }
                        },
                        onClickRow: function (rowIndex, rowData) {
                            var rindex = parseInt($("#goodsInfo-index-priceList").val());
                            $dgPriceInfoList.datagrid('endEdit', rindex);
                            goodsInfo_editIndexPrice = undefined;
                        }
                    });
                    $dgPriceInfoList.addClass("create-datagrid");
                }
            }
            else if (index == 3) {
                $dgMeteringUnitInfoList = $("#goodsInfo-table-meteringUnitInfo");
                if (!$dgMeteringUnitInfoList.hasClass("create-datagrid")) {
                    $dgMeteringUnitInfoList.datagrid({
                        method: 'post',
                        title: '',
                        rownumbers: true,
                        singleSelect: true,
                        url: MUUrl,
                        border: false,
                        columns: [[
                            {field: 'id', title: '编号', width: 150, hidden: true},
                            {field: 'goodsid', title: '商品编码', width: 150, hidden: true},
                            {
                                field: 'meteringunitid', title: '辅计量单位', width: 100, align: 'center',
                                formatter: function (val, rowData, rowIndex) {
                                    return rowData.meteringunitName;
                                },
                                editor: {
                                    type: 'combobox',
                                    options: {
                                        url: 'basefiles/getMUListForCombobox.do',
                                        valueField: 'id',
                                        textField: 'name',
                                        required: true,
                                        onSelect: function (data) {
                                            var row = $dgMeteringUnitInfoList.datagrid('getSelected');
                                            var index = $dgMeteringUnitInfoList.datagrid('getRowIndex', row);
                                            var goodsInfo_MUIds = $("#goodsInfo-meteringUnit").val();
                                            if (goodsInfo_MUIds == data.id) {
                                                $.messager.alert("提醒", "已存在该辅单位!");
                                                var ed = $dgMeteringUnitInfoList.datagrid('getEditor', {
                                                    index: index,
                                                    field: 'meteringunitid'
                                                });
                                                $(ed.target).combobox("clear");
                                                return false;
                                            }
                                            var rows = $dgMeteringUnitInfoList.datagrid('getRows');
                                            for (var i = 0; i < rows.length - 1; i++) {
                                                if (rows[i].meteringunitid == data.id) {
                                                    $.messager.alert("提醒", "已存在该辅单位!");
                                                    var ed = $dgMeteringUnitInfoList.datagrid('getEditor', {
                                                        index: index,
                                                        field: 'meteringunitid'
                                                    });
                                                    $(ed.target).combobox("clear");
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            },
                            {
                                field: 'isdefault', title: '默认辅单位', width: 80, align: 'center', editor: {
                                type: 'defaultSelect',
                                options: {
                                    valueField: 'value',
                                    textField: 'text',
                                    classid: 'contacterSortDefault',
                                    data: [
                                        {value: '0', text: '否'},
                                        {value: '1', text: '是'}
                                    ],
                                    onChange: function (oldval, newval) {
                                        var boxnum = Number(1);
                                        if ("1" == newval) {
                                            var edrate = $dgMeteringUnitInfoList.datagrid('getEditor', {
                                                index: goodsInfo_editIndexMU,
                                                field: 'rate'
                                            });
                                            if (null != edrate) {
                                                boxnum = $(edrate.target).val();
                                                if ("" != boxnum && Number(boxnum) != 0) {
                                                    boxnum = Number(boxnum);
                                                }
                                            }
                                        }
                                        $("#goodsInfo-hidden-boxnum").val(boxnum);
                                        var highestbuyprice = $("#goodsInfo-numberbox-highestbuyprice").numberbox('getValue');
                                        var buyboxprice = highestbuyprice * boxnum;
                                        $("#goodsInfo-numberbox-buyboxprice").numberbox('setValue', buyboxprice);
                                        var grossweight = $("#goodsInfo-numberbox-grossweight").numberbox('getValue');
                                        var totalweight = $("#goodsInfo-numberbox-totalweight").numberbox('getValue');
                                        var totalvolume = $("#goodsInfo-numberbox-totalvolume").numberbox('getValue');
                                        var singlevolume = $("#goodsInfo-numberbox-singlevolume").numberbox('getValue');
                                        //箱重，毛重
                                        if (totalweight != "" && 0 != Number(totalweight)) {
                                            grossweight = totalweight / boxnum;
                                            $("#goodsInfo-numberbox-grossweight").numberbox('setValue', grossweight);
                                        } else {
                                            if (grossweight != "" && 0 != Number(grossweight)) {
                                                totalweight = grossweight * boxnum;
                                                $("#goodsInfo-numberbox-totalweight").numberbox('setValue', totalweight);
                                            }
                                        }
                                        //单体积、箱体积
                                        if (totalvolume != "" && 0 != Number(totalvolume)) {
                                            singlevolume = singlevolume / boxnum;
                                            $("#goodsInfo-numberbox-singlevolume").numberbox('setValue', singlevolume);
                                        } else {
                                            if (singlevolume != "" && 0 != Number(singlevolume)) {
                                                totalvolume = singlevolume * boxnum;
                                                $("#goodsInfo-numberbox-totalvolume").numberbox('setValue', totalvolume);
                                            }
                                        }
                                    }
                                }
                            },
                                formatter: function (val, rowData, rowIndex) {
                                    switch (val) {
                                        case "0":
                                            return "否";
                                        case "1":
                                            return "是";
                                    }
                                }
                            },
                            {
                                field: 'type', title: '换算类型', width: 80, align: 'center',
                                formatter: function (val, rowData, rowIndex) {
                                    return getSysCodeName("priceType", val);
                                },
                                editor: {
                                    type: 'comborefer',
                                    options: {
                                        name: 't_base_goods_info_meteringunit',
                                        col: 'type',
                                        singleSelect: true
                                    }
                                }
                            },
                            {
                                field: 'mode', title: '换算方式', width: 80, align: 'center',
                                formatter: function (val, rowData, rowIndex) {
                                    return getSysCodeName("priceMode", val);
                                },
                                editor: {
                                    type: 'comborefer',
                                    options: {
                                        name: 't_base_goods_info_meteringunit',
                                        col: 'mode',
                                        singleSelect: true
                                    }
                                }
                            },
                            {
                                field: 'rate', title: '换算比率', width: 80, align: 'center',
                                editor: {
                                    type: 'numberbox',
                                    options: {
                                        precision: general_bill_decimallen,
                                        max: 999999999999,
                                        validType: 'minVal',
                                        required: true,
                                        onChange: function (newValue, oldValue) {
                                            if (newValue == Number(0)) {
                                                $(this).numberbox('setValue', "9999");
                                            }
                                            var ed = $dgMeteringUnitInfoList.datagrid('getEditor', {
                                                index: goodsInfo_editIndexMU,
                                                field: 'isdefault'
                                            });
                                            if (null != ed) {
                                                var isdefault = $(ed.target).val();
                                                if ("1" == isdefault) {
                                                    $("#goodsInfo-hidden-boxnum").val(newValue);
                                                    var highestbuyprice = $("#goodsInfo-numberbox-highestbuyprice").numberbox('getValue');
                                                    var buyboxprice = highestbuyprice * newValue;
                                                    $("#goodsInfo-numberbox-buyboxprice").numberbox('setValue', buyboxprice);
                                                    var grossweight = $("#goodsInfo-numberbox-grossweight").numberbox('getValue');
                                                    var totalweight = $("#goodsInfo-numberbox-totalweight").numberbox('getValue');
                                                    var totalvolume = $("#goodsInfo-numberbox-totalvolume").numberbox('getValue');
                                                    var singlevolume = $("#goodsInfo-numberbox-singlevolume").numberbox('getValue');
                                                    //箱重，毛重
                                                    if (totalweight != "" && 0 != Number(totalweight)) {
                                                        grossweight = totalweight / newValue;
                                                        $("#goodsInfo-numberbox-grossweight").numberbox('setValue', grossweight);
                                                    } else {
                                                        if (grossweight != "" && 0 != Number(grossweight)) {
                                                            totalweight = grossweight * newValue;
                                                            $("#goodsInfo-numberbox-totalweight").numberbox('setValue', totalweight);
                                                        }
                                                    }
                                                    //单体积、箱体积
                                                    if (totalvolume != "" && 0 != Number(totalvolume)) {
                                                        singlevolume = singlevolume / newValue;
                                                        $("#goodsInfo-numberbox-singlevolume").numberbox('setValue', singlevolume);
                                                    } else {
                                                        if (singlevolume != "" && 0 != Number(singlevolume)) {
                                                            totalvolume = singlevolume * newValue;
                                                            $("#goodsInfo-numberbox-totalvolume").numberbox('setValue', totalvolume);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                },
                                formatter: function (val, rowData, rowIndex) {
                                    if (val != "" && val != null) {
                                        return formatterBigNumNoLen(val);
                                    }
                                }
                            },
                            {
                                field: 'barcode', title: '辅单位条形码', width: 100, align: 'center', editor: {
                                type: 'validatebox',
                                options: {
                                    validType: 'barcodeVal[20]'
                                }
                            }
                            },
                            {field: 'remark', title: '备注', width: 150, align: 'center', editor: 'text'}
                        ]],
                        toolbar: [{
                            text: "添加",
                            iconCls: "button-add",
                            handler: function () {
                                if ($("#goodsInfo-widget-meteringUnit").widget("getValue") == "") {
                                    $.messager.alert("提醒", "请先选择主计量单位!");
                                    return false;
                                }
                                if (goodsInfo_endEditingMU()) {
                                    var id = goodsInfo_getRandomid();
                                    if (defaultMUAdd()) {
                                        $dgMeteringUnitInfoList.datagrid('appendRow', {
                                            id: id,
                                            goodsid: $("#goodsInfo-id-baseInfo").val(),
                                            isdefault: 'disabled'
                                        });
                                    }
                                    else {
                                        $dgMeteringUnitInfoList.datagrid('appendRow', {
                                            id: id,
                                            goodsid: $("#goodsInfo-id-baseInfo").val(),
                                            isdefault: '0'
                                        });
                                    }
                                    goodsInfo_editIndexMU = parseInt($dgMeteringUnitInfoList.datagrid('getRows').length - 1);
                                    $("#goodsInfo-index-MUList").val(goodsInfo_editIndexMU);
                                    $dgMeteringUnitInfoList.datagrid('selectRow', goodsInfo_editIndexMU).datagrid('beginEdit', goodsInfo_editIndexMU);
                                }
                            }
                        }, {
                            text: "确定",
                            iconCls: "button-save",
                            handler: function () {
                                var row = $dgMeteringUnitInfoList.datagrid('getSelected');
                                if (row == null) {
                                    $.messager.alert("提醒", "请选择行!");
                                    return false;
                                }
                                var index = $dgMeteringUnitInfoList.datagrid('getRowIndex', row);
                                if ($dgMeteringUnitInfoList.datagrid('validateRow', index)) {
                                    var eMU = $dgMeteringUnitInfoList.datagrid('getEditor', {
                                        index: index,
                                        field: 'meteringunitid'
                                    });
                                    if (eMU != null) {
                                        var meteringunitName = $(eMU.target).combobox("getText");
                                        $dgMeteringUnitInfoList.datagrid('getRows')[index]['meteringunitName'] = meteringunitName;
                                    }
                                    goodsInfo_editIndexMU = undefined;
                                    $dgMeteringUnitInfoList.datagrid('clearSelections');
                                    $dgMeteringUnitInfoList.datagrid('endEdit', index);
                                }
                            }
                        }, {
                            text: "修改",
                            iconCls: "button-edit",
                            handler: function () {
                                var row = $dgMeteringUnitInfoList.datagrid('getSelected');
                                if (row == null) {
                                    $.messager.alert("提醒", "请选择行!");
                                    return false;
                                }
                                var rowIndex = $dgMeteringUnitInfoList.datagrid('getRowIndex', row);
                                if (goodsInfo_endEditingMU()) {
                                    $("#goodsInfo-index-MUList").val(rowIndex);
                                    goodsInfo_editIndexMU = rowIndex;
                                    $dgMeteringUnitInfoList.datagrid('beginEdit', rowIndex);
                                    disabledSelectEdit(rowIndex, "goodsInfo-table-meteringUnitInfo");
                                }
                            }
                        }, {
                            text: "删除",
                            iconCls: "button-delete",
                            handler: function () {
                                var row = $dgMeteringUnitInfoList.datagrid('getSelected');
                                if (row == null) {
                                    $.messager.alert("提醒", "请选择行!");
                                    return false;
                                }
                                var rowIndex = $dgMeteringUnitInfoList.datagrid('getRowIndex', row);
                                $dgMeteringUnitInfoList.datagrid('deleteRow', rowIndex);
                                goodsInfo_editIndexMU = undefined;
                                $dgMeteringUnitInfoList.datagrid('clearSelections');
                            }
                        }],
                        onClickRow: function (rowIndex, rowData) {
                            var index = parseInt($("#goodsInfo-index-MUList").val());
                            if ($dgMeteringUnitInfoList.datagrid('validateRow', index)) {
                                var eMU = $dgMeteringUnitInfoList.datagrid('getEditor', {
                                    index: index,
                                    field: 'meteringunitid'
                                });
                                if (eMU != null) {
                                    var meteringunitName = $(eMU.target).combobox("getText");
                                    $dgMeteringUnitInfoList.datagrid('getRows')[index]['meteringunitName'] = meteringunitName;
                                }
                            }
                            $dgMeteringUnitInfoList.datagrid('endEdit', index);
                            goodsInfo_editIndexMU = undefined;
                        }
                    });
                    $dgMeteringUnitInfoList.addClass("create-datagrid");
                }
            }
            else if (index == 4) {
                $dgStorageInfoList = $("#goodsInfo-table-StorageInfo");
                if (!$dgStorageInfoList.hasClass("create-datagrid")) {
                    $dgStorageInfoList.datagrid({
                        method: 'post',
                        title: '',
                        rownumbers: true,
                        singleSelect: true,
                        url: storageUrl,
                        border: false,
                        frozenColumns: [[
                            {field: 'id', title: '编号', width: 150, hidden: true},
                            {field: 'goodsid', title: '商品编码', width: 150, hidden: true},
                            {
                                field: 'storageid', title: '仓库名称', width: 130,
                                formatter: function (val, rowData, rowIndex) {
                                    return rowData.storageName;
                                },
                                editor: {
                                    type: 'comborefer',
                                    options: {
                                        referwid: 'RL_T_BASE_STORAGE_INFO',
                                        name: 't_base_goods_info_storage',
                                        col: 'storageid',
                                        singleSelect: true,
                                        width: 130,
                                        required: true
                                    }
                                }
                            },
                            {
                                field: 'isdefault', title: '默认仓库', width: 80, align: 'center', editor: {
                                type: 'defaultSelect',
                                options: {
                                    valueField: 'value',
                                    textField: 'text',
                                    classid: 'contacterSortDefault',
                                    data: [
                                        {value: '0', text: '否'},
                                        {value: '1', text: '是'}
                                    ]
                                }
                            },
                                formatter: function (val, rowData, rowIndex) {
                                    switch (val) {
                                        case "0":
                                            return "否";
                                        case "1":
                                            return "是";
                                    }
                                }
                            },
                            {
                                field: 'highestinventory', title: '最高库存', width: 100, align: 'center',
                                editor: {
                                    type: 'numberbox',
                                    options: {
                                        precision:${decimallen},
                                        max: 999999999999,
                                        min: 0
                                    }
                                },
                                formatter: function (val, rowData, rowIndex) {
                                    if (val != "" && val != null) {
                                        return formatterBigNumNoLen(val);
                                    }
                                }
                            },
                            {
                                field: 'lowestinventory', title: '最低库存', width: 100, align: 'center',
                                editor: {
                                    type: 'numberbox',
                                    options: {
                                        precision:${decimallen},
                                        max: 999999999999,
                                        min: 0
                                    }
                                },
                                formatter: function (val, rowData, rowIndex) {
                                    if (val != "" && val != null) {
                                        return formatterBigNumNoLen(val);
                                    }
                                }
                            },
                            {
                                field: 'safeinventory', title: '安全库存', width: 100, align: 'center',
                                editor: {
                                    type: 'numberbox',
                                    options: {
                                        precision:${decimallen},
                                        max: 999999999999,
                                        min: 0
                                    }
                                },
                                formatter: function (val, rowData, rowIndex) {
                                    if (val != "" && val != null) {
                                        return formatterBigNumNoLen(val);
                                    }
                                }
                            },
                            {
                                field: 'checktype', title: '盘点方式', width: 95, align: 'center',
                                formatter: function (val, rowData, rowIndex) {
                                    return getSysCodeName('checktype', val);
                                },
                                editor: {
                                    type: 'comborefer',
                                    options: {
                                        name: 't_base_goods_info_storage',
                                        col: 'checktype',
                                        singleSelect: true
                                    }
                                }
                            }
                        ]],
                        columns: [[
                            {title: '盘点周期', colspan: 2},
                            {
                                field: 'lastcheckdate',
                                title: '上次盘点日期',
                                rowspan: 2,
                                width: 110,
                                align: 'center',
                                editor: {
                                    type: 'dateText',
                                    options: {
                                        startDate: '1900-01',
                                        dateFmt: 'yyyy-MM-dd',
                                        maxDate: '%y-%M-%d'
                                    }
                                }
                            },
                            {field: 'remark', title: '备注', width: 150, rowspan: 2, align: 'center', editor: 'text'}],
                            [{
                                field: 'checkdate', title: '数量', width: 60, align: 'center',
                                editor: {
                                    type: 'numberbox',
                                    options: {
                                        precision: 0,
                                        max: 9999999999,
                                        min: 0
                                    }
                                }
                            },
                                {
                                    field: 'checkunit', title: '单位', width: 80, align: 'center', editor: {
                                    type: 'selectboxText',
                                    options: {
                                        vals: '1,2,3,4',
                                        texts: '天,周,月,年',
                                        defaultChecked: '1'
                                    }
                                },
                                    formatter: function (val, rowData, rowIndex) {
                                        switch (val) {
                                            case "1":
                                                return "天";
                                            case "2":
                                                return "周";
                                            case "3":
                                                return "月";
                                            case "4":
                                                return "年";
                                        }
                                    }
                                }
                            ]],
                        toolbar: [{
                            text: "添加",
                            iconCls: "button-add",
                            handler: function () {
                                if (goodsInfo_endEditingStorage()) {
                                    var id = goodsInfo_getRandomid();
                                    if (defaultStorageAdd()) {
                                        $dgStorageInfoList.datagrid('appendRow', {
                                            id: id,
                                            goodsid: $("#goodsInfo-id-baseInfo").val(),
                                            isdefault: 'disabled'
                                        });
                                    }
                                    else {
                                        $dgStorageInfoList.datagrid('appendRow', {
                                            id: id,
                                            goodsid: $("#goodsInfo-id-baseInfo").val(),
                                            isdefault: '0'
                                        });
                                    }
                                    goodsInfo_editIndexStorage = parseInt($dgStorageInfoList.datagrid('getRows').length - 1);
                                    $("#goodsInfo-index-storageList").val(goodsInfo_editIndexStorage);
                                    $dgStorageInfoList.datagrid('selectRow', goodsInfo_editIndexStorage).datagrid('beginEdit', goodsInfo_editIndexStorage);
                                }
                            }
                        }, {
                            text: "确定",
                            iconCls: "button-save",
                            handler: function () {
                                var row = $dgStorageInfoList.datagrid('getSelected');
                                if (row == null) {
                                    $.messager.alert("提醒", "请选择行!");
                                    return false;
                                }
                                var index = $dgStorageInfoList.datagrid('getRowIndex', row);
                                if ($dgStorageInfoList.datagrid('validateRow', index)) {
                                    var eS = $dgStorageInfoList.datagrid('getEditor', {
                                        index: index,
                                        field: 'storageid'
                                    });
                                    if (eS != null) {
                                        var storageName = $(eS.target).widget("getText");
                                        $dgStorageInfoList.datagrid('getRows')[index]['storageName'] = storageName;
                                    }
                                    $dgStorageInfoList.datagrid('endEdit', index);
                                    var rows = $dgStorageInfoList.datagrid('getRows');
                                    for (var i = 0; i < rows.length; i++) {
                                        if (rows[i].isdefault == "1") {//默认仓库为是
                                            $("#goodsInfo-dblclick-storage").val(rows[i].storageName);
                                            $("#goodsInfo-dblclick-hdstorageid").val(rows[i].storageid);
                                            break;
                                        }
                                    }
                                    goodsInfo_editIndexStorage = undefined;
                                    $dgStorageInfoList.datagrid('clearSelections');
                                }
                            }
                        }, {
                            text: "修改",
                            iconCls: "button-edit",
                            handler: function () {
                                var row = $dgStorageInfoList.datagrid('getSelected');
                                if (row == null) {
                                    $.messager.alert("提醒", "请选择行!");
                                    return false;
                                }
                                var rowIndex = $dgStorageInfoList.datagrid('getRowIndex', row);
                                if (goodsInfo_endEditingStorage()) {
                                    $("#goodsInfo-index-storageList").val(rowIndex);
                                    $dgStorageInfoList.datagrid('beginEdit', rowIndex);
                                    goodsInfo_editIndexStorage = rowIndex;
                                    disabledSelectEdit(rowIndex, "goodsInfo-table-StorageInfo");
                                }
                            }
                        }, {
                            text: "删除",
                            iconCls: "button-delete",
                            handler: function () {
                                var row = $dgStorageInfoList.datagrid('getSelected');
                                if (row == null) {
                                    $.messager.alert("提醒", "请选择行!");
                                    return false;
                                }
                                if (row.isdefault == "1") {//若为默认仓库，则同时清楚基本信息标签中的默认仓库数据
                                    $("#goodsInfo-dblclick-storage").val("请双击跳转对应仓库标签!");
                                    $("#goodsInfo-dblclick-hdstorageid").val("");
                                }
                                var rowIndex = $dgStorageInfoList.datagrid('getRowIndex', row);
                                $dgStorageInfoList.datagrid('deleteRow', rowIndex);
                                goodsInfo_editIndexStorage = undefined;
                                $dgStorageInfoList.datagrid('clearSelections');
                            }
                        }],
                        onClickRow: function (rowIndex, rowData) {
                            var index = parseInt($("#goodsInfo-index-storageList").val());
                            if ($dgStorageInfoList.datagrid('validateRow', index)) {
                                var eS = $dgStorageInfoList.datagrid('getEditor', {index: index, field: 'storageid'});
                                if (eS != null) {
                                    var storageName = $(eS.target).widget("getText");
                                    $dgStorageInfoList.datagrid('getRows')[index]['storageName'] = storageName;
                                }
                                $dgStorageInfoList.datagrid('endEdit', index);
                                var rows = $dgStorageInfoList.datagrid('getRows');
                                for (var i = 0; i < rows.length; i++) {
                                    if (rows[i].isdefault == "1") {//默认仓库为是
                                        $("#goodsInfo-dblclick-storage").val(rows[i].storageName);
                                        $("#goodsInfo-dblclick-hdstorageid").val(rows[i].storageid);
                                        break;
                                    }
                                }
                                goodsInfo_editIndexStorage = undefined;
                            }
                            else {
                                $dgStorageInfoList.datagrid('selectRow', index);
                            }
                        }
                    });
                    $dgStorageInfoList.addClass("create-datagrid");
                }
            }
            else if (index == 5) {
                $dgWaresClassInfoList = $("#goodsInfo-table-WaresClass");
                if (!$dgWaresClassInfoList.hasClass("create-datagrid")) {
                    $dgWaresClassInfoList.datagrid({
                        method: 'post',
                        title: '',
                        rownumbers: true,
                        singleSelect: true,
                        url: WCUrl,
                        border: false,
                        columns: [[
                            {field: 'id', title: '编号', width: 150, hidden: true},
                            {field: 'goodsid', title: '商品编码', width: 150, hidden: true},
                            {
                                field: 'waresclass', title: '商品分类', width: 120, align: 'center',
                                formatter: function (val, rowData, rowIndex) {
                                    return rowData.waresclassName;
                                },
                                editor: {
                                    type: 'comborefer',
                                    options: {
                                        name: 't_base_goods_info_waresclass',
                                        col: 'waresclass',
                                        singleSelect: true,
                                        required: true
                                    }
                                }
                            },
                            {
                                field: 'isdefault', title: '默认分类', width: 80, align: 'center', editor: {
                                type: 'defaultSelect',
                                options: {
                                    valueField: 'value',
                                    textField: 'text',
                                    classid: 'contacterSortDefault',
                                    data: [
                                        {value: '0', text: '否'},
                                        {value: '1', text: '是'}
                                    ]
                                }
                            },
                                formatter: function (val, rowData, rowIndex) {
                                    switch (val) {
                                        case "0":
                                            return "否";
                                        case "1":
                                            return "是";
                                    }
                                }
                            },
                            {field: 'remark', title: '备注', width: 150, align: 'center', editor: 'text'}
                        ]],
                        toolbar: [{
                            text: "添加",
                            iconCls: "button-add",
                            handler: function () {
                                if (goodsInfo_endEditingWC()) {
                                    var id = goodsInfo_getRandomid();
                                    if (defaultWCAdd()) {
                                        $dgWaresClassInfoList.datagrid('appendRow', {
                                            id: id,
                                            goodsid: $("#goodsInfo-id-baseInfo").val(),
                                            isdefault: 'disabled'
                                        });
                                    }
                                    else {
                                        $dgWaresClassInfoList.datagrid('appendRow', {
                                            id: id,
                                            goodsid: $("#goodsInfo-id-baseInfo").val(),
                                            isdefault: '0'
                                        });
                                    }
                                    goodsInfo_editIndexWC = parseInt($dgWaresClassInfoList.datagrid('getRows').length - 1);
                                    $("#goodsInfo-index-WCList").val(goodsInfo_editIndexWC);
                                    $dgWaresClassInfoList.datagrid('selectRow', goodsInfo_editIndexWC).datagrid('beginEdit', goodsInfo_editIndexWC);
                                }
                            }
                        }, {
                            text: "确定",
                            iconCls: "button-save",
                            handler: function () {
                                var row = $dgWaresClassInfoList.datagrid('getSelected');
                                if (row == null) {
                                    $.messager.alert("提醒", "请选择行!");
                                    return false;
                                }
                                var index = $dgWaresClassInfoList.datagrid('getRowIndex', row);
                                if ($dgWaresClassInfoList.datagrid('validateRow', index)) {
                                    var eWC = $dgWaresClassInfoList.datagrid('getEditor', {
                                        index: index,
                                        field: 'waresclass'
                                    });
                                    if (eWC != null) {
                                        var waresclassName = $(eWC.target).widget("getText");
                                        $dgWaresClassInfoList.datagrid('getRows')[index]['waresclassName'] = waresclassName;
                                    }
                                    $dgWaresClassInfoList.datagrid('endEdit', index);
                                    var rows = $dgWaresClassInfoList.datagrid('getRows');
                                    for (var i = 0; i < rows.length; i++) {
                                        if (rows[i].isdefault == "1") {//若为默认分类，则同时清楚基本信息标签中的默认分类数据
                                            $("#goodsInfo-dblclick-waresClass").val(rows[i].waresclassName);
                                            $("#goodsInfo-dblclick-hdwaresClassid").val(rows[i].waresclass);
                                            break;
                                        }
                                    }
                                    goodsInfo_editIndexWC = undefined;
                                    $dgWaresClassInfoList.datagrid('clearSelections');
                                }
                            }
                        }, {
                            text: "修改",
                            iconCls: "button-edit",
                            handler: function () {
                                var row = $dgWaresClassInfoList.datagrid('getSelected');
                                if (row == null) {
                                    $.messager.alert("提醒", "请选择行!");
                                    return false;
                                }
                                var rowIndex = $dgWaresClassInfoList.datagrid('getRowIndex', row);
                                if (goodsInfo_endEditingWC()) {
                                    $("#goodsInfo-index-WCList").val(rowIndex);
                                    $dgWaresClassInfoList.datagrid('beginEdit', rowIndex);
                                    goodsInfo_editIndexWC = rowIndex;
                                    disabledSelectEdit(rowIndex, "goodsInfo-table-WaresClass");
                                }
                            }
                        }, {
                            text: "删除",
                            iconCls: "button-delete",
                            handler: function () {
                                var row = $dgWaresClassInfoList.datagrid('getSelected');
                                if (row == null) {
                                    $.messager.alert("提醒", "请选择行!");
                                    return false;
                                }
                                if (row.isdefault == "1") {//若为默认仓库，则同时清楚基本信息标签中的默认仓库数据
                                    $("#goodsInfo-dblclick-waresClass").val("请双击跳转对应分类标签!");
                                    $("#goodsInfo-dblclick-hdwaresClassid").val("");
                                }
                                var rowIndex = $dgWaresClassInfoList.datagrid('getRowIndex', row);
                                $dgWaresClassInfoList.datagrid('deleteRow', rowIndex);
                                goodsInfo_editIndexWC = undefined;
                            }
                        }],
                        onClickRow: function (rowIndex, rowData) {
                            var index = parseInt($("#goodsInfo-index-WCList").val());
                            if ($dgWaresClassInfoList.datagrid('validateRow', index)) {
                                var eWC = $dgWaresClassInfoList.datagrid('getEditor', {
                                    index: index,
                                    field: 'waresclass'
                                });
                                if (eWC != null) {
                                    var waresclassName = $(eWC.target).widget("getText");
                                    $dgWaresClassInfoList.datagrid('getRows')[index]['waresclassName'] = waresclassName;
                                }
                                $dgWaresClassInfoList.datagrid('endEdit', index);
                                var rows = $dgWaresClassInfoList.datagrid('getRows');
                                for (var i = 0; i < rows.length; i++) {
                                    if (rows[i].isdefault == "1") {//若为默认分类，则同时清楚基本信息标签中的默认分类数据
                                        $("#goodsInfo-dblclick-waresClass").val(rows[i].waresclassName);
                                        $("#goodsInfo-dblclick-hdwaresClassid").val(rows[i].waresclass);
                                        break;
                                    }
                                }
                                goodsInfo_editIndexWC = undefined;
                            }
                            else {
                                $dgWaresClassInfoList.datagrid('selectRow', index);
                            }
                        }
                    });
                    $dgWaresClassInfoList.addClass("create-datagrid");
                }
            }
            else if (index == 6) {
                $dgStorageLocationInfoList = $("#goodsInfo-table-StorageLocation");
                if (!$dgStorageLocationInfoList.hasClass("create-datagrid")) {
                    $dgStorageLocationInfoList.datagrid({
                        method: 'post',
                        title: '',
                        rownumbers: true,
                        singleSelect: true,
                        url: SLUrl,
                        border: false,
                        columns: [[
                            {field: 'id', title: '编号', width: 150, hidden: true},
                            {field: 'goodsid', title: '商品编码', width: 150, hidden: true},
                            {
                                field: 'storagelocationid', title: '库位名称', width: 90, align: 'center',
                                formatter: function (val, rowData, rowIndex) {
                                    return rowData.storagelocationName;
                                },
                                editor: {
                                    type: 'comborefer',
                                    options: {
                                        name: 't_base_goods_info_storagelocation',
                                        col: 'storagelocationid',
                                        singleSelect: true,
                                        required: true
                                    }
                                }
                            },
                            {
                                field: 'isdefault', title: '默认库位', width: 80, align: 'center', editor: {
                                type: 'defaultSelect',
                                options: {
                                    valueField: 'value',
                                    textField: 'text',
                                    classid: 'contacterSortDefault',
                                    data: [
                                        {value: '0', text: '否'},
                                        {value: '1', text: '是'}
                                    ]
                                }
                            },
                                formatter: function (val, rowData, rowIndex) {
                                    switch (val) {
                                        case "0":
                                            return "否";
                                        case "1":
                                            return "是";
                                    }
                                }
                            }, {
                                field: 'boxnum', title: '库位容量', width: 80, align: 'right',
                                editor: {
                                    type: 'numberbox',
                                    options: {
                                        precision:${decimallen},
                                        required: true
                                    }
                                },
                                formatter: function (val, rowData, rowIndex) {
                                    if (val != "" && val != null) {
                                        return formatterBigNumNoLen(val);
                                    }
                                }
                            },
                            {field: 'remark', title: '备注', width: 150, align: 'center', editor: 'text'}
                        ]],
                        toolbar: [{
                            text: "添加",
                            iconCls: "button-add",
                            handler: function () {
                                if (goodsInfo_endEditingSL()) {
                                    var id = goodsInfo_getRandomid();
                                    if (defaultSLAdd()) {
                                        $dgStorageLocationInfoList.datagrid('appendRow', {
                                            id: id,
                                            goodsid: $("#goodsInfo-id-baseInfo").val(),
                                            isdefault: 'disabled'
                                        });
                                    }
                                    else {
                                        $dgStorageLocationInfoList.datagrid('appendRow', {
                                            id: id,
                                            goodsid: $("#goodsInfo-id-baseInfo").val(),
                                            isdefault: '0'
                                        });
                                    }
                                    goodsInfo_editIndexSL = parseInt($dgStorageLocationInfoList.datagrid('getRows').length - 1);
                                    $("#goodsInfo-index-SLList").val(goodsInfo_editIndexSL);
                                    $dgStorageLocationInfoList.datagrid('selectRow', goodsInfo_editIndexSL).datagrid('beginEdit', goodsInfo_editIndexSL);
                                }
                            }
                        }, {
                            text: "确定",
                            iconCls: "button-save",
                            handler: function () {
                                var row = $dgStorageLocationInfoList.datagrid('getSelected');
                                if (row == null) {
                                    $.messager.alert("提醒", "请选择行!");
                                    return false;
                                }
                                var index = $dgStorageLocationInfoList.datagrid('getRowIndex', row);
                                if ($dgStorageLocationInfoList.datagrid('validateRow', index)) {
                                    var eSL = $dgStorageLocationInfoList.datagrid('getEditor', {
                                        index: index,
                                        field: 'storagelocationid'
                                    });
                                    if (eSL != null) {
                                        var storagelocationName = $(eSL.target).widget("getText");
                                        $dgStorageLocationInfoList.datagrid('getRows')[index]['storagelocationName'] = storagelocationName;
                                    }
                                    $dgStorageLocationInfoList.datagrid('endEdit', index);
                                    var rows = $dgStorageLocationInfoList.datagrid('getRows');
                                    for (var i = 0; i < rows.length; i++) {
                                        if (rows[i].isdefault == "1") {//若为默认分类，则同时清楚基本信息标签中的默认分类数据
                                            $("#goodsInfo-dblclick-storagelocation").val(rows[i].storagelocationName);
                                            $("#goodsInfo-dblclick-hdstoragelocation").val(rows[i].storagelocationid);
                                            break;
                                        }
                                    }
                                    goodsInfo_editIndexSL = undefined;
                                    $dgStorageLocationInfoList.datagrid('selectRow', index);
                                }
                            }
                        }, {
                            text: "修改",
                            iconCls: "button-edit",
                            handler: function () {
                                var row = $dgStorageLocationInfoList.datagrid('getSelected');
                                if (row == null) {
                                    $.messager.alert("提醒", "请选择行!");
                                    return false;
                                }
                                var rowIndex = $dgStorageLocationInfoList.datagrid('getRowIndex', row);
                                if (goodsInfo_endEditingSL()) {
                                    $("#goodsInfo-index-SLList").val(rowIndex);
                                    $dgStorageLocationInfoList.datagrid('beginEdit', rowIndex);
                                    goodsInfo_editIndexSL = rowIndex;
                                    disabledSelectEdit(rowIndex, "goodsInfo-table-StorageLocation");
                                }
                            }
                        }, {
                            text: "删除",
                            iconCls: "button-delete",
                            handler: function () {
                                var row = $dgStorageLocationInfoList.datagrid('getSelected');
                                if (row == null) {
                                    $.messager.alert("提醒", "请选择行!");
                                    return false;
                                }
                                if (row.isdefault == "1") {//若为默认库位，则同时清除基本信息标签中的默认库位数据
                                    //$("#goodsInfo-widget-storagelocation").widget("setValue","");
                                    $("#goodsInfo-widget-storagelocation").val("");
                                }
                                var rowIndex = $dgStorageLocationInfoList.datagrid('getRowIndex', row);
                                $dgStorageLocationInfoList.datagrid('deleteRow', rowIndex);
                                goodsInfo_editIndexSL = undefined;
                                $dgStorageLocationInfoList.datagrid('clearSelections');
                            }
                        }],
                        onClickRow: function (rowIndex, rowData) {
                            var index = parseInt($("#goodsInfo-index-SLList").val());
                            if ($dgStorageLocationInfoList.datagrid('validateRow', index)) {
                                var eSL = $dgStorageLocationInfoList.datagrid('getEditor', {
                                    index: index,
                                    field: 'storagelocationid'
                                });
                                if (eSL != null) {
                                    var storagelocationName = $(eSL.target).widget("getText");
                                    $dgStorageLocationInfoList.datagrid('getRows')[index]['storagelocationName'] = storagelocationName;
                                }
                                $dgStorageLocationInfoList.datagrid('endEdit', index);
                                var rows = $dgStorageLocationInfoList.datagrid('getRows');
                                for (var i = 0; i < rows.length; i++) {
                                    if (rows[i].isdefault == "1") {//若为默认分类，则同时清楚基本信息标签中的默认分类数据
                                        $("#goodsInfo-dblclick-storagelocation").text(rows[i].storagelocationName);
                                        $("#goodsInfo-dblclick-hdstoragelocation").val(rows[i].storagelocationid);
                                        break;
                                    }
                                }
                                goodsInfo_editIndexSL = undefined;
                            }
                            else {
                                $dgWaresClassInfoList.datagrid('selectRow', index);
                            }
                        }
                    });
                    $dgStorageLocationInfoList.addClass("create-datagrid");
                }
            }
        });
    }
    //添加行时是否默认辅助单位
    function defaultMUAdd() {
        var data = $dgMeteringUnitInfoList.datagrid('getRows');
        if (data.length == 0) {
            return false
        }
        for (var i = 0; i < data.length; i++) {
            if (data[i]['isdefault'] == "1") {
                return true;
            }
        }
        return false;
    }
    //添加行时判断是否默认仓库
    function defaultStorageAdd() {
        var data = $dgStorageInfoList.datagrid('getRows');
        if (data.length == 0) {
            return false
        }
        for (var i = 0; i < data.length; i++) {
            if (data[i]['isdefault'] == "1") {
                return true;
            }
        }
        return false;
    }

    //添加行时判断是否默认分类
    function defaultWCAdd() {
        var data = $dgWaresClassInfoList.datagrid('getRows');
        if (data.length == 0) {
            return false
        }
        for (var i = 0; i < data.length; i++) {
            if (data[i]['isdefault'] == "1") {
                return true;
            }
        }
        return false;
    }

    //添加行时判断是否默认库位
    function defaultSLAdd() {
        var data = $dgStorageLocationInfoList.datagrid('getRows');
        if (data.length == 0) {
            return false
        }
        for (var i = 0; i < data.length; i++) {
            if (data[i]['isdefault'] == "1") {
                return true;
            }
        }
        return false;
    }
    //修改是判断默认仓库选项是否可用
    function disabledSelectEdit(editIndex, listname) {
        var data = $("#" + listname + "").datagrid('getRows');
        if (data[editIndex]['isdefault'] == "1") {
            $(".contacterSortDefault").removeAttr("disabled");
        }
        else {
            var bl = false;
            for (var i = 0; i < data.length; i++) {
                if (data[i]['isdefault'] == "1") {
                    bl = true;
                }
            }
            if (bl) {
                $(".contacterSortDefault").attr("disabled", "disabled");
            }
            else {
                $(".contacterSortDefault").removeAttr("disabled");
            }
        }
    }

    //价格套提交
    function priceJson() {
        if ($dgPriceInfoList != null) {
            var effectRow = new Object();
            var priceChange = $dgPriceInfoList.datagrid('getRows');
            //var priceChange = $dgPriceInfoList.datagrid('getChanges');
            effectRow["priceChange"] = JSON.stringify(priceChange);
            return effectRow;
        }
        return null;
    }
    //辅助计量单位提交
    function MUJson() {
        if ($dgMeteringUnitInfoList != null) {
            var opera = $("#goodsInfo-opera").val();
            var effectRow = new Object();
            if (opera != "copy") {
                if ($dgMeteringUnitInfoList.datagrid('getChanges').length) {
                    var MUChange = $dgMeteringUnitInfoList.datagrid('getChanges');
                    var deleted = $dgMeteringUnitInfoList.datagrid('getChanges', "deleted");
                    if (deleted.length) {
                        effectRow["deletedMU"] = JSON.stringify(deleted);
                    }
                    effectRow["MUChange"] = JSON.stringify(MUChange);
                }
            }
            else if (opera == "copy") {
                var rows = $dgMeteringUnitInfoList.datagrid('getRows');
                effectRow["MUChange"] = JSON.stringify(rows);
            }
            return effectRow;
        }
        return null;
    }
    //对应仓库提交
    function storageJson() {
        if ($dgStorageInfoList != null) {
            var opera = $("#goodsInfo-opera").val();
            var effectRow = new Object();
            if (opera != "copy") {
                if ($dgStorageInfoList.datagrid('getChanges').length) {
                    var storageChange = $dgStorageInfoList.datagrid('getChanges');
                    var deleted = $dgStorageInfoList.datagrid('getChanges', "deleted");
                    if (deleted.length) {
                        effectRow["deletedStorage"] = JSON.stringify(deleted);
                    }
                    effectRow["storageChange"] = JSON.stringify(storageChange);
                }
            }
            else if (opera == "copy") {
                var rows = $dgStorageInfoList.datagrid('getRows');
                effectRow["storageChange"] = JSON.stringify(rows);
            }
            return effectRow;
        }
        return null;
    }
    //对应分类提交
    function WCJson() {
        if ($dgWaresClassInfoList != null) {
            var opera = $("#goodsInfo-opera").val();
            var effectRow = new Object();
            if (opera != "copy") {
                if ($dgWaresClassInfoList.datagrid('getChanges').length) {
                    var WCChange = $dgWaresClassInfoList.datagrid('getChanges');
                    var deleted = $dgWaresClassInfoList.datagrid('getChanges', "deleted");
                    if (deleted.length) {
                        effectRow["deletedWC"] = JSON.stringify(deleted);
                    }
                    effectRow["WCChange"] = JSON.stringify(WCChange);
                }
            }
            else if (opera == "copy") {
                var rows = $dgWaresClassInfoList.datagrid('getRows');
                effectRow["WCChange"] = JSON.stringify(rows);
            }
            return effectRow;
        }
        return null;
    }

    //对应库位提交
    function SLJson() {
        if ($dgStorageLocationInfoList != null) {
            var opera = $("#goodsInfo-opera").val();
            var effectRow = new Object();
            if (opera != "copy") {
                if ($dgStorageLocationInfoList.datagrid('getChanges').length) {
                    var SLChange = $dgStorageLocationInfoList.datagrid('getChanges');
                    var deleted = $dgStorageLocationInfoList.datagrid('getChanges', "deleted");
                    if (deleted.length) {
                        effectRow["deletedSL"] = JSON.stringify(deleted);
                    }
                    effectRow["SLChange"] = JSON.stringify(SLChange);
                }
            }
            else if (opera == "copy") {
                var rows = $dgStorageLocationInfoList.datagrid('getRows');
                effectRow["SLChange"] = JSON.stringify(rows);
            }
            return effectRow;
        }
        return null;
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
        isRepeatBarcode: {//唯一性,最大长度
            validator: function (value, param) {
                if (value.length <= param[0]) {
                    var ret = goodsInfo_AjaxConn({barcode: value}, 'basefiles/isRepeatGoodsInfoBarcode.do');//true 重复，false 不重复
                    var retJson = $.parseJSON(ret);
                    if (retJson.flag) {
                        $.fn.validatebox.defaults.rules.isRepeatBarcode.message = '条形码重复, 请重新输入!';
                        return false;
                    }
                }
                else {
                    $.fn.validatebox.defaults.rules.isRepeatBarcode.message = '最多可输入{0}个字符!';
                    return false;
                }
                return true;
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

    function setDatagridIdNull() {
        $dgPriceInfoList = null;
        $dgMeteringUnitInfoList = null;
        $dgStorageInfoList = null;
        $dgWaresClassInfoList = null;
        $dgStorageLocationInfoList = null;
    }

    $(function () {
        //加载按钮
        $("#goodsInfo-div-button").buttonWidget({
            //初始默认按钮 根据type对应按钮事件
            initButton: [
                {},
                <security:authorize url="/basefiles/goodesInfoAddBtn.do">
                {
                    type: 'button-add',//新增
                    handler: function () {
                        setDatagridIdNull();
                        panelRefresh('basefiles/showGoodsInfoAddPage.do', ' 商品档案【新增】', 'add');
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesInfoEditBtn.do">
                {
                    type: 'button-edit',//修改
                    handler: function () {
                        var flag = isDoLockData($("#goodsInfo-id-hidden").val(), "t_base_goods_info");
                        if (!flag) {
                            $.messager.alert("警告", "该数据正在被其他人操作，暂不能修改！");
                            return false;
                        }
                        setDatagridIdNull();
                        var id = $("#goodsInfo-id-baseInfo").val();
                        panelRefresh('basefiles/showGoodsInfoEidtPage.do?id=' + encodeURIComponent(id), ' 商品档案【修改】', 'edit');
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesInfoHoldBtn.do">
                {
                    type: 'button-hold',//暂存
                    handler: function () {
                        if (goodsInfo_editIndexPrice != undefined) {
                            $.messager.alert("提醒", "请确定价格套信息!");
                            return false;
                        }
                        if (goodsInfo_editIndexMU != undefined) {
                            $.messager.alert("提醒", "请确定辅助计量单位信息!");
                            return false;
                        }
                        if (goodsInfo_editIndexStorage != undefined) {
                            $.messager.alert("提醒", "请确定对应仓库信息!");
                            return false;
                        }
                        if (goodsInfo_editIndexWC != undefined) {
                            $.messager.alert("提醒", "请确定对应分类信息!");
                            return false;
                        }
                        if (goodsInfo_editIndexSL != undefined) {
                            $.messager.alert("提醒", "请确定对应库位信息!");
                            return false;
                        }
                        var wc = $("#goodsInfo-dblclick-waresClass").val();
                        var stro = $("#goodsInfo-dblclick-storage").val();
                        var sl = $("#goodsInfo-dblclick-storagelocation").val();
                        if (wc == "请双击跳转对应分类标签!") {
                            $("#goodsInfo-dblclick-waresClass").val("");
                        }
                        if (stro == "请双击跳转对应仓库标签!") {
                            $("#goodsInfo-dblclick-storage").val("");
                        }
                        if (sl == "请双击跳转对应库位标签!") {
                            $("#goodsInfo-dblclick-storagelocation").val("");
                        }
                        if ($("#goodsInfo-id-baseInfo").val() == "") {
                            $.messager.alert("提醒", "请输入编码!");
                            return false;
                        }
                        var type = $("#goodsInfo-div-button").buttonWidget("getOperType");
                        if (type == "add") {
                            if (!$("#goodsInfo-id-baseInfo").validatebox('isValid')) {
                                return false;
                            }
                            $.messager.confirm("提醒", "是否暂存新增商品档案?", function (r) {
                                if (r) {
                                    loading("提交中..");
                                    addGoodsInfo("hold");//暂存新增商品档案
                                }
                            });
                        }
                        else {
                            $.messager.confirm("提醒", "是否暂存修改商品档案?", function (r) {
                                if (r) {
                                    loading("提交中..");
                                    editGoodsInfo("hold");//暂存修改商品档案
                                }
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesInfoSaveBtn.do">
                {
                    type: 'button-save',//保存
                    handler: function () {
                        if (goodsInfo_editIndexPrice != undefined) {
                            $.messager.alert("提醒", "请确定价格套信息!");
                            return false;
                        }
                        if (goodsInfo_editIndexMU != undefined) {
                            $.messager.alert("提醒", "请确定辅助计量单位信息!");
                            return false;
                        }
                        if (goodsInfo_editIndexStorage != undefined) {
                            $.messager.alert("提醒", "请确定对应仓库信息!");
                            return false;
                        }
                        if (goodsInfo_editIndexWC != undefined) {
                            $.messager.alert("提醒", "请确定对应分类信息!");
                            return false;
                        }
                        if (goodsInfo_editIndexSL != undefined) {
                            $.messager.alert("提醒", "请确定对应库位信息!");
                            return false;
                        }
                        var wc = $("#goodsInfo-dblclick-waresClass").val();
                        var stro = $("#goodsInfo-dblclick-storage").val();
                        var sl = $("#goodsInfo-dblclick-storagelocation").val();
                        if (wc == "请双击跳转对应分类标签!") {
                            $("#goodsInfo-dblclick-waresClass").val("");
                        }
                        if (stro == "请双击跳转对应仓库标签!") {
                            $("#goodsInfo-dblclick-storage").val("");
                        }
                        if (sl == "请双击跳转对应库位标签!") {
                            $("#goodsInfo-dblclick-storagelocation").val("");
                        }
                        var type = $("#goodsInfo-div-button").buttonWidget("getOperType");
                        if (type == "add") {
                            if (!goodsInfoFormValidate()) {
                                $.messager.alert('提醒', "有必填项未填写!");
                                return false;
                            }
                            $.messager.confirm("提醒", "是否保存新增商品档案?", function (r) {
                                if (r) {
                                    loading("提交中..");
                                    addGoodsInfo("save");//保存新增商品档案
                                }
                            });
                        }
                        else {
                            if (!goodsInfoFormValidate()) {
                                return false;
                            }
                            $.messager.confirm("提醒", "是否保存修改商品档案?", function (r) {
                                if (r) {
                                    loading("提交中..");
                                    editGoodsInfo("save");//保存修改商品档案
                                }
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesInfoDeleteBtn.do">
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
                                        tabsWindow(goods_title).$("#wares-table-goodsInfoList").datagrid('reload');
                                    }
                                    var object = $("#goodsInfo-div-button").buttonWidget("removeData", id);
                                    if (null != object) {
                                        panelRefresh('basefiles/showGoodsInfoViewPage.do?id=' + encodeURIComponent(object.id), ' 商品档案【查看】', 'view');
                                    }
                                    else {
                                        top.closeNowTab();
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
                <security:authorize url="/basefiles/goodesInfoCopyBtn.do">
                {
                    type: 'button-copy',//复制
                    handler: function () {
                        setDatagridIdNull();
                        var id = $("#goodsInfo-id-baseInfo").val();
                        panelRefresh('basefiles/showGoodsInfoCopyPage.do?id=' + encodeURIComponent(id), ' 商品档案【复制】', 'copy');
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesInfoEnableBtn.do">
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
                                        tabsWindow(goods_title).$("#wares-table-goodsInfoList").datagrid('reload');
                                    }
                                    $("#wares-div-goodsInfo").panel('refresh', 'basefiles/showGoodsInfoViewPage.do?id=' + encodeURIComponent($("#goodsInfo-id-baseInfo").val()));
                                }
                                else {
                                    $.messager.alert("提醒", "启用无效" + retJSON.invalidNum + "条记录;<br/>启用失败" + retJSON.num + "条记录;");
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesInfoDisableBtn.do">
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
                                        tabsWindow(goods_title).$("#wares-table-goodsInfoList").datagrid('reload');
                                    }
                                    $("#wares-div-goodsInfo").panel('refresh', 'basefiles/showGoodsInfoViewPage.do?id=' + encodeURIComponent($("#goodsInfo-id-baseInfo").val()));
                                }
                                else {
                                    $.messager.alert("提醒", "" + retJSON.invalidNum + "条记录状态不允许禁用;<br/>禁用失败" + retJSON.num + "条记录;");
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesInfoBackBtn.do">
                {
                    type: 'button-back',//上一条
                    handler: function (data) {
                        setDatagridIdNull();
                        if ("${listType}" == "1") {
                            panelRefresh('basefiles/showGoodsInfoViewPage.do?id=' + encodeURIComponent(data.goodsid), ' 商品档案【查看】', 'view');
                        }
                        else if ("${listType}" == "2") {
                            panelRefresh('basefiles/showGoodsInfoViewPage.do?id=' + encodeURIComponent(data.id), ' 商品档案【查看】', 'view');
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesInfoNextBtn.do">
                {
                    type: 'button-next',//下一条
                    handler: function (data) {
                        setDatagridIdNull();
                        if ("${listType}" == "1") {
                            panelRefresh('basefiles/showGoodsInfoViewPage.do?id=' + encodeURIComponent(data.goodsid), ' 商品档案【查看】', 'view');
                        }
                        else if ("${listType}" == "2") {
                            panelRefresh('basefiles/showGoodsInfoViewPage.do?id=' + encodeURIComponent(data.id), ' 商品档案【查看】', 'view');
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesInfoGiveUpBtn.do">
                {
                    type: 'button-giveup',//放弃
                    handler: function () {
                        var type = $("#goodsInfo-div-button").buttonWidget("getOperType");
                        if (type == "add") {
                            var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
                            top.$('#tt').tabs('close', currTitle);
                        } else if (type == "edit") {
                            $.ajax({
                                url: 'system/lock/unLockData.do',
                                type: 'post',
                                data: {id: $("#goodsInfo-id-hidden").val(), tname: 't_base_goods_info'},
                                dataType: 'json',
                                async: false,
                                success: function (json) {
                                    flag = json.flag
                                }
                            });
                            if (!flag) {
                                $.messager.alert("警告", "该数据正在被其他人操作，暂不能修改！");
                                return false;
                            }
                            panelRefresh('basefiles/showGoodsInfoViewPage.do?id=' + encodeURIComponent($("#goodsInfo-id-baseInfo").val()), ' 商品档案【查看】', 'view');
                        }
                    }
                },
                </security:authorize>
            ],
            buttons: [
                {},
                <security:authorize url="/basefiles/goodesInfoPrintBtn.do">
                {
                    id: 'printMenuButton',
                    type: 'menu',
                    name: '打印',
                    iconCls: 'button-print',
                    button: [
                        <security:authorize url="/basefiles/goodsLocationPrintViewBtn.do">
                        {
                            id: 'printview-id-goodslocation',
                            name: '货位信息打印预览',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/basefiles/goodsLocationPrintBtn.do">
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
            goodsInfo_url = "basefiles/showGoodsInfoViewPage.do?id=" + encodeURIComponent($("#goodsInfo-id-hidden").val());
            goodsInfo_opera = "view";
            goodsInfo_type = "view";
            goodsInfo_title = "商品档案【查看】";
        }
        else if ("${type}" == "edit") {
            goodsInfo_url = "basefiles/showGoodsInfoEidtPage.do?id=" + encodeURIComponent($("#goodsInfo-id-hidden").val());
            goodsInfo_opera = "edit";
            goodsInfo_type = "edit";
            goodsInfo_title = "商品档案【修改】";
        }
        else if ("${type}" == "copy") {
            goodsInfo_url = "basefiles/showGoodsInfoCopyPage.do?id=" + encodeURIComponent($("#goodsInfo-id-hidden").val());
            goodsInfo_type = "copy";
            goodsInfo_title = "商品档案【复制】";
        }
        else {
            goodsInfo_url = "basefiles/showGoodsInfoAddPage.do?WCid=" + encodeURIComponent($("#goodsInfo-defaultWCid").val());
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
        //打印
        AgReportPrint.init({
            id: "goodsInfoPage-dialog-print",
            code: "goods_goodslocation",
            url_preview: "print/basefiles/goodsLocationPrintView.do",
            url_print: "print/basefiles/goodsLocationPrint.do",
            btnPreview: "printview-id-goodslocation",
            btnPrint: "print-id-goodslocation",
            libtype: "withbarcode",
            getData: getData
        });
        function getData(tableId, printParam) {
            var id = $("#goodsInfo-id-baseInfo").val();
            if (id == "") {
                $.messager.alert("警告", "找不到要打印的信息!");
                return false;
            }
            printParam.idarrs = id;
            var businessdate1 = $("#account-form-businessdate1").val();
            var businessdate2 = $("#account-form-businessdate2").val();
            if (businessdate1 == "" || businessdate2 == "") {
                $.messager.alert("提醒", '请输入业务日期时间段以便打印预览');
                return false;
            }
            return true;
        }
    });
</script>
<%--打印结束 --%>
</body>
</html>
