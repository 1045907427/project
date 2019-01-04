<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>调拨单操作页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div id="storage-layout-allocateOutPage" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-allocateOutPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="storage-panel-allocateOutPage"></div>
    </div>
</div>
<div id="storage-panel-relation-upper"></div>
<div id="storage-panel-sourceQueryPage"></div>
<div id="workflow-addidea-dialog-page"></div>
<input type="hidden" id="storage-hidden-billid"/>
<script type="text/javascript">
    //根据初始的列与用户保存的列生成以及字段权限生成新的列
    var tableColJson = $("#storage-datagrid-allocateOutAddPage").createGridColumnLoad({
        frozenCol: [[]],
        commonCol: [[
            {field: 'goodsid', title: '商品编码', width: 80},
            {
                field: 'goodsname', title: '商品名称', width: 220, aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.name;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'barcode', title: '条形码', width: 85, aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.barcode;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'itemno', title: '商品货位', width: 60, aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.itemno;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'brandName', title: '商品品牌', width: 80, aliascol: 'goodsid', hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.brandName;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'model', title: '规格型号', width: 80, aliascol: 'goodsid', hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.model;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'boxnum', title: '箱装量', aliascol: 'goodsid', width: 45, align: 'right',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
                    } else {
                        return "";
                    }
                }
            },
            {field: 'unitname', title: '单位', width: 35},
            {
                field: 'unitnum', title: '数量', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterBigNumNoLen(value);
                }
            },
            <security:authorize url="/storage/eidtAndViewAllocateOutPrice.do">
            <c:if test="${isAllocateShowBilltype=='1'}">
            {field:'costprice', title:'成本价',width:60,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            </c:if>
            {
                field: 'taxprice', title: '调拨单价', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'boxprice', title: '调拨箱价', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'taxamount', title: '调拨金额', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            </security:authorize>
            /*
             {field:'notaxprice', title:'未税单价',width:60,align:'right',hidden:true,
             formatter:function(value,row,index){
             return formatterMoney(value);
             }
             },
             {field:'notaxamount', title:'未税金额',width:60,align:'right',hidden:true,
             formatter:function(value,row,index){
             return formatterMoney(value);
             }
             },
             {field:'taxtypename', title:'税种',width:60,aliascol:'taxtype',align:'right',hidden:true},*/
            /*{field:'tax', title:'税额',width:60,align:'right',hidden:true,
             formatter:function(value,row,index){
             return formatterMoney(value);
             }
             },*/
            {field: 'auxnumdetail', title: '辅数量', width: 60, align: 'right'},
            {field: 'batchno', title: '出库批次号', width: 80},
            {field: 'produceddate', title: '出库生产日期', width: 80},
            {field: 'deadline', title: '出库截止日期', width: 80},
            {
                field: 'storagelocationid', title: '出库库位', width: 100, hidden: true,
                formatter: function (value, row, index) {
                    return row.storagelocationname;
                }
            },
            {field: 'enterbatchno', title: '入库批次号', width: 80},
            {field: 'enterproduceddate', title: '入库生产日期', width: 80},
            {field: 'enterdeadline', title: '入库截止日期', width: 80},
            {
                field: 'enterstoragelocationid', title: '入库库位', width: 100, hidden: true,
                formatter: function (value, row, index) {
                    return row.enterstoragelocationname;
                }
            },
            {field: 'remark', title: '备注', width: 100}
        ]]
    });
    var page_url = "storage/allocateOutAddPage.do";
    var page_type = '${type}';
    if (page_type == "view" || page_type == "handle") {
        page_url = "storage/allocateOutViewPage.do?id=${id}";
    } else if (page_type == "edit") {
        var flag = '${flag}';
        if (flag == "true") {
            page_url = "storage/allocateOutEditPage.do?id=${id}";
        }
        else {
            $.messager.alert("提醒", "该订单已不存在！");
            page_url = null;
        }
    }
    $(function () {
        $("#storage-panel-allocateOutPage").panel({
            href: page_url,
            cache: false,
            maximized: true,
            border: false
        });
        //按钮
        $("#storage-buttons-allocateOutPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/allocateOutAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        $("#storage-panel-allocateOutPage").panel({
                            href: 'storage/allocateOutAddPage.do',
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/addallocateOutHold.do">
                {
                    type: 'button-hold',
                    handler: function () {
                        $.messager.confirm("提醒", "确定暂存该调拨单信息？", function (r) {
                            if (r) {
                                var type = $("#storage-buttons-allocateOutPage").buttonWidget("getOperType");
                                if (type == "add") {
                                    //暂存
                                    $("#storage-form-allocateOutAdd").attr("action", "storage/addAllocateOutHold.do");
                                    $("#storage-form-allocateOutAdd").submit();
                                } else if (type == "edit") {
                                    //暂存
                                    $("#storage-form-allocateOutAdd").attr("action", "storage/editAllocateOutHold.do");
                                    $("#storage-form-allocateOutAdd").submit();
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/addallocateOutSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        $.messager.confirm("提醒", "确定保存该调拨单信息？", function (r) {
                            if (r) {
                                var billtype=$("#storage-allocateOut-billtype").val();
                                var type = $("#storage-buttons-allocateOutPage").buttonWidget("getOperType");
                                var outisaloneaccount=$("#storage-allocateOut-outisaloneaccount").val();
                                var enterisaloneaccount=$("#storage-allocateOut-enterisaloneaccount").val();
                                if(billtype=='2'&&outisaloneaccount=='0'&&enterisaloneaccount=='0'){
                                    $.messager.confirm("提醒", "异价调拨类型单据调出仓库和调入仓库中没有独立核算仓库，是否继续？", function (s) {
                                        if (s) {
                                            if (type == "add") {
                                                //暂存
                                                $("#storage-form-allocateOutAdd").attr("action", "storage/addAllocateOutSave.do");
                                                $("#storage-form-allocateOutAdd").submit();
                                            } else if (type == "edit") {
                                                $("#storage-form-allocateOutAdd").attr("action", "storage/editAllocateOutSave.do");
                                                $("#storage-form-allocateOutAdd").submit();
                                            }
                                        }
                                    })
                                }else{
                                    if (type == "add") {
                                        //暂存
                                        $("#storage-form-allocateOutAdd").attr("action", "storage/addAllocateOutSave.do");
                                        $("#storage-form-allocateOutAdd").submit();
                                    } else if (type == "edit") {
                                        $("#storage-form-allocateOutAdd").attr("action", "storage/editAllocateOutSave.do");
                                        $("#storage-form-allocateOutAdd").submit();
                                    }
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/addallocateOutSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        $.messager.confirm("提醒", "确定保存并审核该调拨单信息？", function (r) {
                            if (r) {
                                var billtype=$("#storage-allocateOut-billtype").val();
                                $("#storage-allocateOut-saveaudit").val("saveaudit");
                                var type = $("#storage-buttons-allocateOutPage").buttonWidget("getOperType");
                                var outisaloneaccount=$("#storage-allocateOut-outisaloneaccount").val();
                                var enterisaloneaccount=$("#storage-allocateOut-enterisaloneaccount").val();
                                if(billtype=='2'&&outisaloneaccount=='0'&&enterisaloneaccount=='0'){
                                    $.messager.confirm("提醒", "异价调拨类型单据调出仓库和调入仓库中没有独立核算仓库，是否继续？", function (s) {
                                        if (s) {
                                            if (type == "add") {
                                                //暂存
                                                $("#storage-form-allocateOutAdd").attr("action", "storage/addAllocateOutSave.do");
                                                $("#storage-form-allocateOutAdd").submit();
                                            } else if (type == "edit") {
                                                $("#storage-form-allocateOutAdd").attr("action", "storage/editAllocateOutSave.do");
                                                $("#storage-form-allocateOutAdd").submit();
                                            }
                                        }
                                    })
                                }else{
                                    if (type == "add") {
                                        //暂存
                                        $("#storage-form-allocateOutAdd").attr("action", "storage/addAllocateOutSave.do");
                                        $("#storage-form-allocateOutAdd").submit();
                                    } else if (type == "edit") {
                                        $("#storage-form-allocateOutAdd").attr("action", "storage/editAllocateOutSave.do");
                                        $("#storage-form-allocateOutAdd").submit();
                                    }
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/allocateOutGiveUp.do">
                {
                    type: 'button-giveup',
                    handler: function () {
                        var type = $("#storage-buttons-allocateOutPage").buttonWidget("getOperType");
                        if (type == "add") {
                            var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
                            top.$('#tt').tabs('close', currTitle);
                        }
                        else if (type == "edit") {
                            var id = $("#storage-hidden-billid").val();
                            if (id == "") {
                                return false;
                            }
                            $("#storage-panel-allocateOutPage").panel({
                                href: 'storage/allocateOutViewPage.do?id=' + id,
                                title: '',
                                cache: false,
                                maximized: true,
                                border: false
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/deleteAllocateOut.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        $.messager.confirm("提醒", "是否删除当前调拨单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("删除中..");
                                    $.ajax({
                                        url: 'storage/deleteAllocateOut.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                var object = $("#storage-buttons-allocateOutPage").buttonWidget("removeData", id);
                                                $.messager.alert("提醒", "删除成功");
                                                if (null != object) {
                                                    $("#storage-panel-allocateOutPage").panel({
                                                        href: 'storage/allocateOutEditPage.do?id=' + object.id,
                                                        title: '',
                                                        cache: false,
                                                        maximized: true,
                                                        border: false
                                                    });
                                                } else {
                                                    parent.closeNowTab();
                                                }
                                            } else {
                                                $.messager.alert("提醒", "删除失败");
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "删除出错")
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/auditAllocateOut.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        $.messager.confirm("提醒", "是否审核调拨单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("审核中..");
                                    $.ajax({
                                        url: 'storage/auditAllocateOut.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "审核成功");
                                                $("#storage-allocateOut-status-select").val("4");
//                                                $("#storage-buttons-allocateOutPage").buttonWidget("setDataID", {
//                                                    id: id,
//                                                    state: '4',
//                                                    type: 'view'
//                                                });
                                                $("#storage-panel-allocateOutPage").panel({
                                                    href:"storage/allocateOutEditPage.do?id="+id,
                                                    cache:false,
                                                    maximized:true,
                                                    border:false
                                                });
                                            } else {
                                                $.messager.alert("提醒", "审核失败" + json.msg);
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "审核出错");
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                /**
                 {
                      type:'button-oppaudit',
                      handler:function(){
                          $.messager.confirm("提醒","是否反审调拨单？",function(r){
                             if(r){
                                 var id = $("#storage-hidden-billid").val();
                                 if(id!=""){
                                     loading("反审中..");
                                     $.ajax({
                                         url :'storage/oppauditAllocateOut.do?id='+id,
                                         type:'post',
                                         dataType:'json',
                                         success:function(json){
                                             loaded();
                                             if(json.flag){
                                                 $.messager.alert("提醒","反审成功");
                                                 $("#storage-panel-allocateOutPage").panel({
                                                     href:'storage/allocateOutViewPage.do?id='+id,
                                                     title:'调拨单查看',
                                                     cache:false,
                                                     maximized:true,
                                                     border:false
                                                 });
                                             }else{
                                                 $.messager.alert("提醒","反审失败");
                                             }
                                         },
                                         error:function(){
                                             loaded();
                                             $.messager.alert("错误","反审失败");
                                         }
                                     });
                                 }
                             }
                         });
                      },
                      url:'/storage/oppauditAllocateOut.do'
                  },**/
                </security:authorize>
                <security:authorize url="/storage/showAllocateOutRelationPage.do">
                {
                    type: 'button-relation',
                    button: [
                        {},
                        <security:authorize url="/storage/showAllocateOutRelationUpperPage.do">
                        {
                            type: 'relation-upper',
                            handler: function () {
                                $("#storage-panel-relation-upper").dialog({
                                    href: "storage/showAllocateOutRelationUpperPage.do",
                                    title: "上游单据查询",
                                    closed: false,
                                    modal: true,
                                    cache: false,
                                    width: 500,
                                    height: 300,
                                    buttons: [{
                                        text: '查询',
                                        handler: function () {
                                            var queryJSON = $("#storage-form-query-dispatchBill").serializeJSON();
                                            $("#storage-panel-relation-upper").dialog('close', true);
                                            var type = $("#storage-buttons-purchaseRejectOutPage").buttonWidget("getOperType");
                                            $("#storage-panel-sourceQueryPage").dialog({
                                                title: '调拨单列表',
                                                fit: true,
                                                closed: false,
                                                modal: true,
                                                cache: false,
                                                maximizable: true,
                                                resizable: true,
                                                href: 'storage/showAllocateOutSourceListPage.do',
                                                buttons: [{
                                                    text: '确定',
                                                    handler: function () {
                                                        var dispatchbill = $("#storage-orderDatagrid-dispatchBillSourcePage").datagrid('getSelected');
                                                        if (dispatchbill == null) {
                                                            $.messager.alert("提醒", "请选择一条订单记录");
                                                            return false;
                                                        }
                                                        $("#storage-panel-sourceQueryPage").dialog('close', true);
                                                        //生成采购退货出库单
                                                        loading("提交中..");
                                                        $.ajax({
                                                            url: 'storage/addAllocateOutByRefer.do',
                                                            type: 'post',
                                                            dataType: 'json',
                                                            data: {id: dispatchbill.id},
                                                            success: function (json) {
                                                                loaded();
                                                                if (json.flag) {
                                                                    $.messager.alert("提醒", "生成成功");
                                                                    $("#storage-panel-allocateOutPage").panel({
                                                                        href: 'storage/allocateOutViewPage.do?id=' + json.id,
                                                                        title: '',
                                                                        cache: false,
                                                                        maximized: true,
                                                                        border: false
                                                                    });
                                                                } else {
                                                                    $.messager.alert("提醒", "生成失败");
                                                                }
                                                            }
                                                        });
                                                    }
                                                }],
                                                onLoad: function () {
                                                    $("#storage-orderDatagrid-dispatchBillSourcePage").datagrid({
                                                        columns: [[
                                                            {field: 'id', title: '编号', width: 125, sortable: true},
                                                            {
                                                                field: 'businessdate',
                                                                title: '业务日期',
                                                                width: 80,
                                                                sortable: true
                                                            },
                                                            {
                                                                field: 'outstorageid',
                                                                title: '调出仓库',
                                                                width: 80,
                                                                sortable: true,
                                                                formatter: function (value, rowData, rowIndex) {
                                                                    return rowData.outstoragename;
                                                                }
                                                            },
                                                            {
                                                                field: 'enterstorageid',
                                                                title: '调入仓库',
                                                                width: 80,
                                                                sortable: true,
                                                                formatter: function (value, rowData, rowIndex) {
                                                                    return rowData.enterstoragename;
                                                                }
                                                            },
                                                            {
                                                                field: 'sourcetype',
                                                                title: '来源类型',
                                                                width: 90,
                                                                sortable: true,
                                                                formatter: function (value, rowData, rowIndex) {
                                                                    return getSysCodeName("allocateOut-sourcetype", value);
                                                                }
                                                            },
                                                            {
                                                                field: 'addusername',
                                                                title: '制单人',
                                                                width: 80,
                                                                sortable: true
                                                            },
                                                            {
                                                                field: 'addtime',
                                                                title: '制单时间',
                                                                width: 80,
                                                                sortable: true
                                                            },
                                                            {
                                                                field: 'auditusername',
                                                                title: '审核人',
                                                                width: 80,
                                                                sortable: true
                                                            },
                                                            {
                                                                field: 'audittime',
                                                                title: '审核时间',
                                                                width: 80,
                                                                sortable: true
                                                            },
                                                            {
                                                                field: 'stopusername',
                                                                title: '中止人',
                                                                width: 80,
                                                                hidden: true,
                                                                sortable: true
                                                            },
                                                            {
                                                                field: 'stoptime',
                                                                title: '中止时间',
                                                                width: 80,
                                                                hidden: true,
                                                                sortable: true
                                                            },
                                                            {
                                                                field: 'status', title: '状态', width: 60, sortable: true,
                                                                formatter: function (value, rowData, rowIndex) {
                                                                    return getSysCodeName("status", value);
                                                                }
                                                            },
                                                            {field: 'remark', title: '备注', width: 80, sortable: true}
                                                        ]],
                                                        fit: true,
                                                        method: 'post',
                                                        rownumbers: true,
                                                        pagination: true,
                                                        idField: 'id',
                                                        singleSelect: true,
                                                        fitColumns: true,
                                                        url: 'storage/showallocateOutList.do',
                                                        queryParams: queryJSON,
                                                        onClickRow: function (index, data) {
                                                            $("#storage-detailDatagrid-dispatchBillSourcePage").datagrid({
                                                                url: 'storage/showallocateOutDetailList.do',
                                                                queryParams: {
                                                                    id: data.id
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }]
                                });
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/showPurchaseRejectOutSourcePage.do">
                        {
                            type: 'relation-upper-view',
                            handler: function () {
                                var sourceid = $("#storage-allocateOut-sourceid").val();
                                if (null != sourceid && sourceid != "") {
                                    top.addOrUpdateTab('storage/showAllocateNoticeEditPage.do?id=' + sourceid, "调拨通知单");
                                }
                            }
                        },
                        </security:authorize>
                        {}
                    ]
                },
                </security:authorize>
                <security:authorize url="/storage/allocateOutViewBackPage.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        $("#storage-panel-allocateOutPage").panel({
                            href: 'storage/allocateOutEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/allocateOutViewNextPage.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        $("#storage-panel-allocateOutPage").panel({
                            href: 'storage/allocateOutEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/allocateOutPrintView.do">
                {
                    type: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/allocateOutPrint.do">
                {
                    type: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {},
            ],
            buttons:[
<security:authorize url="/storage/auditAllocateStorageEnterAndOut.do">
                {
                    id: 'auditbutton',
                    type: 'menu',
                    name: '出入库审核',
                    iconCls: 'button-audit',
                    button: [
                        <security:authorize url="/storage/auditAllocateStorageOut.do">
                        {
                            id:'storage-out-audit',
                            name:'审核出库',
                            iconCls:'button-audit',
                            handler:function(){
                                $.messager.confirm("提醒", "是否审核调拨出库？", function (r) {
                                    if (r) {
                                        var id = $("#storage-hidden-billid").val();
                                        if (id != "") {
                                            loading("审核中..");
                                            $.ajax({
                                                url: 'storage/auditAllocateStorageOut.do?id=' + id,
                                                type: 'post',
                                                dataType: 'json',
                                                success: function (json) {
                                                    loaded();
                                                    if (json.flag) {
                                                        $.messager.alert("提醒", "审核出库成功");
//                                                        $("#storage-allocateOut-status-select").val("4");
                                                        $("#storage-panel-allocateOutPage").panel({
                                                            href:"storage/allocateOutEditPage.do?id="+id,
                                                            cache:false,
                                                            maximized:true,
                                                            border:false
                                                        });
                                                    } else {
                                                        $.messager.alert("提醒", "审核失败" + json.msg);
                                                    }
                                                },
                                                error: function () {
                                                    loaded();
                                                    $.messager.alert("错误", "审核出错");
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/auditAllocateStorageEnter.do">
                        {
                            id: 'storage-enter-audit',
                            name: '审核入库',
                            iconCls: 'button-audit',
                            handler: function () {
                                $.messager.confirm("提醒", "是否审核调拨入库？", function (r) {
                                    if (r) {
                                        var id = $("#storage-hidden-billid").val();
                                        if (id != "") {
                                            loading("审核中..");
                                            $.ajax({
                                                url: 'storage/auditAllocateStorageEnter.do?id=' + id,
                                                type: 'post',
                                                dataType: 'json',
                                                success: function (json) {
                                                    loaded();
                                                    if (json.flag) {
                                                        $.messager.alert("提醒", "审核入库成功");
//                                                        $("#storage-allocateOut-status-select").val("4");
                                                        $("#storage-panel-allocateOutPage").panel({
                                                            href:"storage/allocateOutEditPage.do?id="+id,
                                                            cache:false,
                                                            maximized:true,
                                                            border:false
                                                        });
                                                    } else {
                                                        $.messager.alert("提醒", "审核失败" + json.msg);
                                                    }
                                                },
                                                error: function () {
                                                    loaded();
                                                    $.messager.alert("错误", "审核出错");
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/oppauditStorageStorageOut.do">
                        {
                            id:'storage-out-oppaudit',
                            name:'反审出库',
                            iconCls:'button-oppaudit',
                            handler:function(){
                                $.messager.confirm("提醒", "是否反审调拨出库？", function (r) {
                                    if (r) {
                                        var id = $("#storage-hidden-billid").val();
                                        if (id != "") {
                                            loading("反审中..");
                                            $.ajax({
                                                url: 'storage/oppauditAllocateStorageOut.do?id=' + id,
                                                type: 'post',
                                                dataType: 'json',
                                                success: function (json) {
                                                    loaded();
                                                    if (json.flag) {
                                                        $.messager.alert("提醒", "反审调拨出库成功");
//                                                        $("#storage-allocateOut-status-select").val("4");
                                                        $("#storage-panel-allocateOutPage").panel({
                                                            href:"storage/allocateOutEditPage.do?id="+id,
                                                            cache:false,
                                                            maximized:true,
                                                            border:false
                                                        });
                                                    } else {
                                                        $.messager.alert("提醒", "反审失败" + json.msg);
                                                    }
                                                },
                                                error: function () {
                                                    loaded();
                                                    $.messager.alert("错误", "反审出错");
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        },
                        </security:authorize>
                        {}
                    ]
                },
    </security:authorize>
            ],
            layoutid: 'storage-layout-allocateOutPage',
            model: 'bill',
            type: 'view',
            tab: '调拨单列表',
            taburl: '/storage/showAllocateOutListPage.do',
            id: '${id}',
            datagrid: 'storage-datagrid-allocateOutPage'
        });
    });

    //显示调拨单明细添加页面
    function beginAddDetail() {
        //验证表单
        var flag = $("#storage-form-allocateOutAdd").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先完善调拨单基本信息');
            return false;
        }

        var outstorageid = $("#storage-allocateOut-outstorageid").widget("getValue");
        $('<div id="storage-dialog-allocateOutAddPage-content"></div>').appendTo('#storage-dialog-allocateOutAddPage');
        $('#storage-dialog-allocateOutAddPage-content').dialog({
            title: '调拨单明细添加',
            width: 680,
            height: 420,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            modal: true,
            href: 'storage/showAllocateOutDetailAddPage.do?outstorageid=' + outstorageid,
            onLoad: function () {
                $("#storage-allocateOut-goodsid").focus();
            },
            onClose: function () {
                $('#storage-dialog-allocateOutAddPage-content').dialog("destroy");
            }
        });
        $('#storage-dialog-allocateOutAddPage-content').dialog("open");
    }
    //显示盘点单明细修改页面
    function beginEditDetail() {
        //验证表单
        var flag = $("#storage-form-allocateOutAdd").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先选择出库仓库');
            $("#storage-allocateOut-storageid").focus();
            return false;
        }
        var row = $("#storage-datagrid-allocateOutAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if (row.goodsid == undefined) {
            beginAddDetail();
        } else {
            var storageid = $("#storage-allocateOut-outstorageid").widget("getValue");
            var outStorage = $("#storage-allocateOut-outstorageid").widget("getObject");
            var enterStorage = $("#storage-allocateOut-enterstorageid").widget("getObject");
            var url = 'storage/showAllocateOutDetailEditPage.do?goodsid=' + row.goodsid + '&storageid=' + storageid;
            $('<div id="storage-dialog-allocateOutAddPage-content"></div>').appendTo('#storage-dialog-allocateOutAddPage');
            $('#storage-dialog-allocateOutAddPage-content').dialog({
                title: '调拨单明细修改',
                width: 680,
                height: 420,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                resizable: true,
                closed: true,
                cache: false,
                href: url,
                modal: true,
                onLoad: function () {
                    $("#storage-allocateOut-unitnum").focus();
                    $("#storage-allocateOut-unitnum").select();
                    var isbatch = $("#storage-allocateOut-isbatch").val();
                    if (isbatch == "1") {
                        $("#storage-allocateOut-enterstoragelocationid").widget("enable");
                        $("#storage-allocateOut-batchno").attr("readonly", false);
                        $("#storage-allocateOut-batchno").removeClass("no_input");
                        $("#storage-allocateOut-enterproduceddate").removeClass("WdateRead");
                        $("#storage-allocateOut-enterproduceddate").addClass("Wdate");
                        $("#storage-allocateOut-enterproduceddate").removeAttr("readonly");
                        if (enterStorage.isbatch == "1") {
                            $("#storage-allocateOut-enterproduceddate").validatebox({required: true});
                        } else {
                            $("#storage-allocateOut-enterproduceddate").validatebox({required: false});
                        }

                        $("#storage-allocateOut-enterdeadline").removeClass("WdateRead");
                        $("#storage-allocateOut-enterdeadline").addClass("Wdate");
                        $("#storage-allocateOut-enterdeadline").removeAttr("readonly");

                        var param = null;
                        if (storageid != null && storageid != "") {
                            param = [{field: 'goodsid', op: 'equal', value: row.goodsid},
                                {field: 'storageid', op: 'equal', value: storageid}];
                        } else {
                            param = [{field: 'goodsid', op: 'equal', value: data.id}];
                        }
                        //批次是否必填
                        var reFlag = false;
                        if (outStorage.isbatch == "1") {
                            reFlag = true;
                        }
                        $("#storage-allocateOut-batchno").widget({
                            referwid: 'RL_T_STORAGE_BATCH_LIST',
                            param: param,
                            width: 165,
                            singleSelect: true,
                            required: reFlag,
                            onSelect: function (obj) {
                                $("#storage-allocateOut-detail-summarybatchid").val(obj.id);
                                $("#storage-allocateOut-storagelocationname").val(obj.storagelocationname);
                                $("#storage-allocateOut-storagelocationid").val(obj.storagelocationid);
                                $("#storage-allocateOut-produceddate").val(obj.produceddate);
                                $("#storage-allocateOut-deadline").val(obj.deadline);
                                $("#storage-allocateOut-enterproduceddate").val(obj.produceddate);
                                $("#storage-allocateOut-enterdeadline").val(obj.deadline);
                                $("#storage-allocateOut-enterbatchno").val(obj.batchno);

                                $("#storage-allocateOut-usablenum").val(obj.newinventory);
                                computNum();
                                $("#storage-allocateOut-unitnum").focus();
                                $("#storage-allocateOut-unitnum").select();
                            },
                            onClear: function () {
                                $("#storage-allocateOut-detail-summarybatchid").val("");
                                $("#storage-allocateOut-storagelocationname").val("");
                                $("#storage-allocateOut-storagelocationid").val("");
                                $("#storage-allocateOut-produceddate").val("");
                                $("#storage-allocateOut-deadline").val("");
                                $("#storage-allocateOut-enterproduceddate").val("");
                                $("#storage-allocateOut-enterdeadline").val("");
                            }
                        });
                    } else {
                        $("#storage-allocateOut-enterproduceddate").removeClass("Wdate");
                        $("#storage-allocateOut-enterproduceddate").addClass("WdateRead");
                        $("#storage-allocateOut-enterproduceddate").attr("readonly", "readonly");
                        $("#storage-allocateOut-enterproduceddate").validatebox({required: false});
                        $("#storage-allocateOut-enterdeadline").removeClass("Wdate");
                        $("#storage-allocateOut-enterdeadline").addClass("WdateRead");
                        $("#storage-allocateOut-enterdeadline").attr("readonly", "readonly");

                        $("#storage-allocateOut-batchno").attr("readonly", true);
                        $("#storage-allocateOut-batchno").addClass("no_input");
                    }

                    formaterNumSubZeroAndDot();

                    $("#storage-form-allocateOutDetailAddPage").form('validate');
                },
                onClose: function () {
                    $('#storage-dialog-allocateOutAddPage-content').dialog("destroy");
                }
            });
            $('#storage-dialog-allocateOutAddPage-content').dialog("open");
        }
    }
    //保存调拨单明细
    function addSaveDetail(goFlag) { //添加新数据确定后操作，
        var flag = $("#storage-form-allocateOutDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#storage-form-allocateOutDetailAddPage").serializeJSON();
        var widgetJson = $("#storage-allocateOut-goodsid").goodsWidget('getObject');
        var goodsInfo = {
            id: widgetJson.id, name: widgetJson.name, brandName: widgetJson.brandName,
            model: widgetJson.model, barcode: widgetJson.barcode, boxnum: widgetJson.boxnum
        };
        form.goodsInfo = goodsInfo;
        $.ajax({
            url: 'basefiles/getItemByGoodsAndStorage.do',
            data:{
                goodsid:widgetJson.id,
                storageid:$("#storage-allocateOut-outstorageid").widget('getValue')
            },
            async: false,
            type: 'post',
            dataType: 'json',
            success: function (json) {
                form.goodsInfo.itemno=json.itemno;
            },
        });
        var rowIndex = 0;
        var rows = $("#storage-datagrid-allocateOutAddPage").datagrid('getRows');
        var updateFlag = false;
        for (var i = 0; i < rows.length; i++) {
            var rowJson = rows[i];
            if (rowJson.summarybatchid != null && form.summarybatchid != "") {
                if (rowJson.goodsid == widgetJson.id && rowJson.summarybatchid == form.summarybatchid) {
                    rowIndex = i;
                    updateFlag = true;
                    break;
                }
            } else {
                if (rowJson.goodsid == widgetJson.id) {
                    rowIndex = i;
                    updateFlag = true;
                    break;
                }
            }
            if (rowJson.goodsid == undefined) {
                rowIndex = i;
                break;
            }
        }
        if (rowIndex == 0) {
            $("#storage-allocateOut-outstorageid").widget('readonly', true);
        }
        if (rowIndex == rows.length - 1) {
            $("#storage-datagrid-allocateOutAddPage").datagrid('appendRow', {}); //如果是最后一行则添加一新行
        }
        if (updateFlag) {
            $.messager.alert("提醒", "此商品已经添加！");
            return false;
        } else {
            $("#storage-datagrid-allocateOutAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
            reloadBilltypeSelect();
        }
        if (goFlag) { //go为true确定并继续添加一条
            var storageid = $("#storage-allocateOut-outstorageid").widget("getValue");
            var url = 'storage/showAllocateOutDetailAddPage.do?outstorageid=' + storageid;
            $("#storage-dialog-allocateOutAddPage-content").dialog('refresh', url);
            setTimeout(function () {

                $("#storage-allocateOut-goodsid").focus();
            }, 100);
        }
        else { //否则直接关闭
            $("#storage-dialog-allocateOutAddPage-content").dialog('destroy');
        }
        countTotal();

    }
    //修改保存
    function editSaveDetail(goFlag) {
        var flag = $("#storage-form-allocateOutDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#storage-form-allocateOutDetailAddPage").serializeJSON();
        var row = $("#storage-datagrid-allocateOutAddPage").datagrid('getSelected');
        var rowIndex = $("#storage-datagrid-allocateOutAddPage").datagrid('getRowIndex', row);
        form.goodsInfo = row.goodsInfo;
        $("#storage-datagrid-allocateOutAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        $("#storage-dialog-allocateOutAddPage-content").dialog('destroy');
        countTotal();
    }
    //删除明细
    function removeDetail() {
        var row = $("#storage-datagrid-allocateOutAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $.messager.confirm("提醒", "确定删除该商品明细?", function (r) {
            if (r) {
                var rowIndex = $("#storage-datagrid-allocateOutAddPage").datagrid('getRowIndex', row);
                $("#storage-datagrid-allocateOutAddPage").datagrid('deleteRow', rowIndex);
                countTotal();
                var rows = $("#storage-datagrid-allocateOutAddPage").datagrid('getRows');
                var index = -1;
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].goodsid != undefined) {
                        index = i;
                        break;
                    }
                }
                if (index == -1) {
                    $("#storage-allocateOut-outstorageid").widget('readonly', false);
                }
                reloadBilltypeSelect();
            }
        });
    }
    function reloadBilltypeSelect(){
        var sourcetype=$("#storage-allocateOut-sourcetype").val();
        if(sourcetype==1){
            return;
        }
        var rows = $("#storage-datagrid-allocateOutAddPage").datagrid('getRows');
        if(rows[0].goodsid==''||rows[0].goodsid==undefined){
            $("#storage-allocateOut-billtype").removeAttr('disabled');
        }else{
            $("#storage-allocateOut-billtype").attr('disabled','disabled');
        }
    }
    //计算合计
    function countTotal() {
        var rows = $("#storage-datagrid-allocateOutAddPage").datagrid('getRows');
        var countNum = 0;
        var taxamount = 0;
        var notaxamount = 0;
        var tax = 0;
        for (var i = 0; i < rows.length; i++) {
            countNum = Number(countNum) + Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
            taxamount = Number(taxamount) + Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
            notaxamount = Number(notaxamount) + Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
            tax = Number(tax) + Number(rows[i].tax == undefined ? 0 : rows[i].tax);
        }
        $("#storage-datagrid-allocateOutAddPage").datagrid("reloadFooter", [{
            goodsid: '合计',
            unitnum: countNum,
            taxamount: taxamount,
            notaxamount: notaxamount,
            tax: tax
        }]);
    }
    $(function () {
        //关闭明细添加页面
        $(document).bind('keydown', 'esc', function () {
            $("#storage-allocateOutDetail-remark").focus();
            $("#storage-dialog-allocateOutAddPage-content").dialog("close");
        });
        $(document).bind('keydown', 'ctrl+enter', function () {
            $("#storage-allocateOutDetail-remark").focus();
            $("#storage-savegoon-allocateOutDetailAddPage").trigger("click");
            $("#storage-savegoon-allocateOutDetailEditPage").trigger("click");
        });
        $(document).bind('keydown', '+', function () {
            $("#storage-allocateOutDetail-remark").focus();
            setTimeout(function () {
                $("#storage-savegoon-allocateOutDetailAddPage").trigger("click");
                $("#storage-savegoon-allocateOutDetailEditPage").trigger("click");
            }, 300);
            return false;
        });
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "allocateout-dialog-print",
            code: "storage_allocateout",
            url_preview: "print/storage/allocateOutPrintView.do",
            url_print: "print/storage/allocateOutPrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var id = $("#storage-hidden-billid").val();
                if (id == "") {
                    $.messager.alert("警告", "找不到要打印的信息!");
                    return false;
                }
                printParam.idarrs = id;
                var printtimes = $("#storage-allocateOut-printtimes").val() || 0;
                if (printtimes > 0)
                    printParam.printIds = [id];
                return true;
            },
            printAfterHandler: function (option, printParam) {
                var printtimes = $("#storage-allocateOut-printtimes").val();
                $("#storage-allocateOut-printtimes").val(printtimes + 1);
                var printlimit = $("#storage-allocateOut-printlimit").val() || -1;
                if (0 != printlimit) {
                    $("#storage-buttons-allocateOutPage").buttonWidget("disableMenuItem", "button-print");
                }
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
