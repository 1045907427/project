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
<div class="easyui-panel" data-options="fit:true,border:false">
    <div title="商品档案列表" class="easyui-layout" data-options="fit:true,border:true">
        <div title="" data-options="region:'north',split:false,border:false" style="height: 30px;overflow: hidden">
            <div class="buttonBG" id="goodsInfo-button-div"></div>
            <input id="wares-id-hdWaresClassId" type="hidden"/>
            <input id="wares-pid-hdWaresClassPid" type="hidden"/>
        </div>
        <div title="商品分类" data-options="region:'west',split:true" style="width:200px;">
            <div id="wares-tree-waresClass" class="ztree"></div>
        </div>
        <div title="商品列表" data-options="region:'center',split:true">
            <div id="wares-query-showGoodsInfoList" style="padding: 0px;">
                <form action="" id="wares-form-goodsInfoListQuery" method="post"
                      style="display: none;padding-left: 5px; padding-top: 2px;">
                    <table class="querytable">
                        <%--<tr>--%>
                        <%--<span id="goodsInfo-query-advanced"></span>--%>
                        <%--</tr>--%>
                        <tr>
                            <td>商品编码:</td>
                            <td><input type="text" name="id" style="width:100px"/>
                                <input id="goods-hddefaultsort" name="defaultsort" type="hidden"/>
                            </td>
                            <td>商品名称:</td>
                            <td><input type="text" name="name" style="width:120px"/>
                            </td>
                            <td>所属供应商:</td>
                            <td><input id="wares-widget-goodsShortcut-supplierid" type="text" name="defaultsupplier"
                                       style="width:148px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>商品品牌:</td>
                            <td><input id="wares-widget-goodsShortcut-brand" type="text" name="brandid"
                                       style="width:100px"/></td>
                            <td>所属部门:</td>
                            <td><input id="wares-widget-goodsShortcut-deptid" type="text" name="deptid"/></td>
                            <td>状态:</td>
                            <td><select id="wares-widget-goodsShortcut-state" name="state" style="width:148px;">
                                <option></option>
                                <option value="2">保存</option>
                                <option value="1" selected="selected">启用</option>
                                <option value="0">禁用</option>
                            </select>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4"></td>
                            <td colspan="2" style="padding-left: 10px">
                                <a href="javaScript:void(0);" id="wares-query-queryGoodsInfoList"
                                   class="button-qr">查询</a>
                                <a href="javaScript:void(0);" id="wares-query-reloadGoodsInfoList"
                                   class="button-qr">重置</a>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <table id="wares-table-goodsInfoList"></table>
        </div>
    </div>
</div>
<div id="wares-dialog-goodsInfoEditMore"></div>
<script type="text/javascript">
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
    //根据初始的列与用户保存的列生成以及字段权限生成新的列
    var goodsInfoListColJson = $("#wares-table-goodsInfoList").createGridColumnLoad({
        name: 'base_goods_info',
        frozenCol: [[]],
        commonCol: [[{field: 'id', title: '编码', sortable: true, resizable: true},
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
                field: 'storageid', title: '默认仓库', width: 80, sortable: true, hidden: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.storageName;
                }
            },
            {
                field: 'defaultbuyer', title: '默认采购员', width: 80, sortable: true, hidden: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.defaultbuyerName;
                }
            },
            {
                field: 'state', title: '状态', width: 50, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.stateName;
                }
            },
            {field: 'remark', title: '备注', width: 100, sortable: true}
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
            width: 100,
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

        var queryJSON = $("#wares-form-goodsInfoListQuery").serializeJSON();

        var goodsInfoWaresClassTreeSetting = {
            view: {
                dblClickExpand: false,
                showLine: true,
                showIcon: true,
                expandSpeed: ($.browser.msie && parseInt($.browser.version) <= 6) ? "" : "fast"
            },
            async: {
                enable: true,
                url: "basefiles/goodsInfoWaresClassTree.do",
                autoParam: ["id", "parentid", "text", "state"]
            },
            data: {
                key: {
                    name: "text"
                    //title:"text"
                },
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentid",
                    rootPId: ""
                }
            },
            callback: {
                //点击树状菜单更新页面按钮列表
                beforeClick: function (treeId, treeNode) {
                    $("#goods-hddefaultsort").val(treeNode.id);
                    $("#wares-id-hdWaresClassId").val(treeNode.id);
                    $("#wares-pid-hdWaresClassPid").val(treeNode.pId);
                    var queryJSON = $("#wares-form-goodsInfoListQuery").serializeJSON();
                    $("#wares-table-goodsInfoList").datagrid("load", queryJSON);
                    var zTree = $.fn.zTree.getZTreeObj("wares-tree-waresClass");
                    if (treeNode.isParent) {
                        if (treeNode.level == 0) {
                            zTree.expandAll(false);
                            zTree.expandNode(treeNode);
                        } else {
                            zTree.expandNode(treeNode);
                        }
                    }
                    return true;
                },
                onClick: function () {
                    $("#wares-table-goodsInfoList").datagrid('clearSelections');
                },
                onAsyncSuccess: function (event, treeId, treeNode, msg) {
                    $("#wares-form-goodsInfoListQuery").show();
                    $('#wares-table-goodsInfoList').datagrid({
                        authority: goodsInfoListColJson,
                        frozenColumns: [[{field: 'goodsInfock', checkbox: true}]],
                        columns: goodsInfoListColJson.common,
                        fit: true,
                        title: '',
                        toolbar: '#wares-query-showGoodsInfoList',
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
                            top.addOrUpdateTab('basefiles/showGoodsInfoPage.do?type=view&listType=2&id=' + encodeURIComponent(rowData.id) + '&state=' + rowData.state, '商品档案');
                        }
                    }).datagrid("columnMoving");
                }
            }
        };
        $.fn.zTree.init($("#wares-tree-waresClass"), goodsInfoWaresClassTreeSetting, null);

        var goodsInfo_WCTreeObj = $.fn.zTree.getZTreeObj("wares-tree-waresClass");
        //加载按钮
        $("#goodsInfo-button-div").buttonWidget({
            //初始默认按钮 根据type对应按钮事件
            initButton: [
                {},
                <security:authorize url="/basefiles/goodesInfoAddBtn.do">
                {
                    type: 'button-add',//新增
                    handler: function () {
                        var WCid = "";
                        var WCTree = goodsInfo_WCTreeObj.getSelectedNodes();
                        if (WCTree.length != 0) {
                            if ("".localeCompare(WCTree[0].id) != 0) {
                                WCid = WCTree[0].id;
                            }
                        }
                        top.addOrUpdateTab('basefiles/showGoodsInfoPage.do?type=add&listType=2&WCid=' + WCid, '商品档案');
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesInfoEditBtn.do">
                {
                    type: 'button-edit',//修改
                    handler: function () {
                        var goodsInfoRow = $("#wares-table-goodsInfoList").datagrid('getSelected');
                        if (goodsInfoRow == null) {
                            $.messager.alert("提醒", "请选择商品!");
                            return false;
                        }
                        var flag = isDoLockData(goodsInfoRow.id, "t_base_goods_info");
                        if (!flag) {
                            $.messager.alert("警告", "该数据正在被其他人操作，暂不能修改！");
                            return false;
                        }
                        top.addOrUpdateTab('basefiles/showGoodsInfoPage.do?type=edit&listType=2&id=' + encodeURIComponent(goodsInfoRow.id), '商品档案');
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesInfoDeleteBtn.do">
                {
                    type: 'button-delete',//删除
                    handler: function () {
                        var goodsInfoRows = $("#wares-table-goodsInfoList").datagrid('getChecked');
                        if (goodsInfoRows.length == 0) {
                            $.messager.alert("提醒", "请选择商品!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否确定删除商品档案?", function (r) {
                            if (r) {
                                var idStr = "";
                                for (var i = 0; i < goodsInfoRows.length; i++) {
                                    idStr += goodsInfoRows[i].id + ",";
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
                                            var queryJSON = $("#wares-form-goodsInfoListQuery").serializeJSON();
                                            $("#wares-table-goodsInfoList").datagrid("load", queryJSON);
                                            $("#wares-table-goodsInfoList").datagrid('clearSelections');
                                            $("#wares-table-goodsInfoList").datagrid('clearChecked');
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
                <security:authorize url="/basefiles/goodesInfoCopyBtn.do">
                {
                    type: 'button-copy',//复制
                    handler: function () {
                        var goodsInfoRow = $("#wares-table-goodsInfoList").datagrid('getSelected');
                        if (goodsInfoRow == null) {
                            $.messager.alert("提醒", "请选择一个商品!");
                            return false;
                        }
                        top.addOrUpdateTab('basefiles/showGoodsInfoPage.do?type=copy&listType=2&id=' + encodeURIComponent(goodsInfoRow.id) + '&state=' + goodsInfoRow.state, '商品档案');
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodsInfoViewBtn.do">
                {
                    type: 'button-view',//查看
                    handler: function () {
                        var goodsInfoRow = $("#wares-table-goodsInfoList").datagrid('getSelected');
                        if (goodsInfoRow == null) {
                            $.messager.alert("提醒", "请选择一个商品!");
                            return false;
                        }
                        top.addOrUpdateTab('basefiles/showGoodsInfoPage.do?type=view&listType=2&id=' + encodeURIComponent(goodsInfoRow.id) + '&state=' + goodsInfoRow.state, '商品档案');
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesInfoEnableBtn.do">
                {
                    type: 'button-open',//启用
                    handler: function () {
                        var goodsInfoRows = $("#wares-table-goodsInfoList").datagrid('getChecked');
                        if (goodsInfoRows.length == 0) {
                            $.messager.alert("提醒", "请勾选商品!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否确定启用商品档案?", function (r) {
                            if (r) {
                                var idStr = "";
                                for (var i = 0; i < goodsInfoRows.length; i++) {
                                    idStr += goodsInfoRows[i].id + ",";
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
                                            var queryJSON = $("#wares-form-goodsInfoListQuery").serializeJSON();
                                            $("#wares-table-goodsInfoList").datagrid("load", queryJSON);
                                            $("#wares-table-goodsInfoList").datagrid('clearSelections');
                                            $("#wares-table-goodsInfoList").datagrid('clearChecked');
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
                        var goodsInfoRows = $("#wares-table-goodsInfoList").datagrid('getChecked');
                        if (goodsInfoRows.length == 0) {
                            $.messager.alert("提醒", "请勾选商品!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否确定禁用商品档案?", function (r) {
                            if (r) {
                                var idStr = "";
                                for (var i = 0; i < goodsInfoRows.length; i++) {
                                    idStr += goodsInfoRows[i].id + ",";
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
                                            var queryJSON = $("#wares-form-goodsInfoListQuery").serializeJSON();
                                            $("#wares-table-goodsInfoList").datagrid("load", queryJSON);
                                            $("#wares-table-goodsInfoList").datagrid('clearSelections');
                                            $("#wares-table-goodsInfoList").datagrid('clearChecked');
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
                        majorkey: 'id',
                        shortcutname: 'goods',//调用简单版名称判断
                        childkey: 'goodsid',
                        version: '2',//导入页面显示哪个版本1：不显示，2：简化版或合同版，3：Excel文件或瑞家txt导入，4：Excel文件或三和txt导入
                        maintn: 't_base_goods_info',
                        onClose: function () { //导入成功后窗口关闭时操作，
                            $("#wares-table-goodsInfoList").datagrid('reload');	//更新列表
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesInfoExportBtn.do">
                {
                    type: 'button-export',//导出
                    attr: {
                        datagrid: "#wares-table-goodsInfoList",
                        queryForm: "#wares-form-goodsInfoListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
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
                        exportparam: '简化版:若价格套或默认税种为空,则导出数据为空',//参数描述
                        shortcutname: 'goods',
                        version: '2',//1不显示单选框，2显示单选框
                        sort: 'defaultsort',
                        queryparam: 'id,name,brandid,deptid,defaultsupplier',
                        childkey: 'goodsid',
                        maintn: 't_base_goods_info',
                        name: '商品档案列表'
                    }
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        //查询针对的表
                        name: 'base_goods_info',
                        //查询针对的表格id
                        datagrid: 'wares-table-goodsInfoList'
                    }
                },
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
                        var goodsInfoRows = $("#wares-table-goodsInfoList").datagrid('getChecked');
                        if (goodsInfoRows.length == 0) {
                            $.messager.alert("提醒", "请勾选商品!");
                            return false;
                        }
                        var idStr = "", flagIdStr = "";
                        var unInvNum = 0;
                        for (var i = 0; i < goodsInfoRows.length; i++) {
                            var id = goodsInfoRows[i].id;
                            var flag = isDoLockData(id, "t_base_goods_info");
                            if (!flag) {
                                flagIdStr += id + ",";
                                unInvNum++;
                                var index = $("#wares-table-goodsInfoList").datagrid('getRowIndex', goodsInfoRows[i]);
                                $("#wares-table-goodsInfoList").datagrid('uncheckRow', index);
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
                        $('#wares-dialog-goodsInfoEditMore').dialog({
                            title: '批量修改商品信息',
                            width: 550,
                            height: 330,
                            closed: false,
                            cache: false,
                            href: 'basefiles/goodsInfoMoreEditPage.do?idStr=' + encodeURIComponent(idStr) + '&unInvNum=' + unInvNum,
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
                //		top.addOrUpdateTab('basefiles/showGoodsShortcutPage.do','商品档案列表');
                //	}
                //},
                //</security:authorize>

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
            type: 'multipleList',
            taburl: '/basefiles/showGoodsInfoListPage.do',
            datagrid: 'wares-table-goodsInfoList',
            tname: 't_base_goods_info',
            id: ''
        });

        //回车事件
        controlQueryAndResetByKey("wares-query-queryGoodsInfoList", "wares-query-reloadGoodsInfoList");

        //查询
        $("#wares-query-queryGoodsInfoList").click(function () {
            var queryJSON = $("#wares-form-goodsInfoListQuery").serializeJSON();
            $("#wares-table-goodsInfoList").datagrid("load", queryJSON);
        });

        //重置按钮
        $("#wares-query-reloadGoodsInfoList").click(function () {
            $("#wares-form-goodsInfoListQuery")[0].reset();
            $("#wares-widget-goodsShortcut-brand").widget('clear');
            $("#wares-widget-goodsShortcut-supplierid").supplierWidget('clear');
            $("#wares-widget-goodsShortcut-deptid").widget('clear');
            var queryJSON = $("#wares-form-goodsInfoListQuery").serializeJSON();
            $("#wares-table-goodsInfoList").datagrid("load", queryJSON);

        });
        //通用查询组建调用
//			$("#goodsInfo-query-advanced").advancedQuery({
//				//查询针对的表
//		 		name:'base_goods_info',
//		 		//查询针对的表格id
//		 		datagrid:'wares-table-goodsInfoList'
//			});

    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-goodslocation-dialog-print",
            code: "goods_goodslocation",
            tableId: "wares-table-goodsInfoList",
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
