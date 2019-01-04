<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>盘点单操作页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div id="storage-layout-checkListPage" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-checkListPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="storage-panel-checkListPage"></div>
    </div>
</div>
<form id="storage-form-export-checkListPage">
    <input id="storage-hidden-billid" type="hidden" name="id"/>
</form>
<div id="workflow-addidea-dialog-page"></div>
<div id="storage-dialog-confirm-checkListPage"></div>
<div id="storage-dialog-confirm1-checkListPage"></div>
<script type="text/javascript">
    var checkList_AjaxConn = function (Data, Action) {
        var MyAjax = $.ajax({
            type: 'post',
            cache: false,
            url: Action,
            data: Data,
            async: false
        })
        return MyAjax.responseText;
    }

    //根据初始的列与用户保存的列生成以及字段权限生成新的列
    var tableColJson = $("#storage-datagrid-checkListAddPage").createGridColumnLoad({
        name: 'storage_checklist_detail',
        frozenCol: [[{field: 'idok', checkbox: true, isShow: true}]],
        commonCol: [[
            {field: 'id', title: '编号', sortable: true, width: 70, hidden: true},
            {field: 'goodsid', title: '商品编码', sortable: true, width: 70},
            {field: 'goodsname', title: '商品名称', width: 220, aliascol: 'goodsid'},
            {field: 'barcode', title: '条形码', width: 120, aliascol: 'goodsid',sortable: true},
            {field: 'spell', title: '助记符', width: 80, aliascol: 'goodsid',sortable: true},
            {field: 'itemno', title: '商品货位', width: 60, aliascol: 'goodsid'},
            {field: 'goodssortname', title: '商品分类', width: 80, aliascol: 'goodsid'},
            {field: 'brandName', title: '商品品牌', width: 80, aliascol: 'goodsid', hidden: true},
            {field: 'model', title: '规格型号', width: 80, aliascol: 'goodsid', hidden: true},
            {
                field: 'boxnum', title: '箱装量', aliascol: 'goodsid', width: 50, align: 'right',
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
                field: 'price', title: '单价', width: 60, align: 'right', sortable: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'existingnum', title: '仓库现存量', width: 60, aliascol: 'booknum', align: 'right', sortable: true,
                formatter: function (value, row, index) {
                    return formatterBigNumNoLen(value);
                }
            },
            {
                field: 'booknum', title: '账面数量', width: 60, align: 'right', sortable: true,
                formatter: function (value, row, index) {
                    return formatterBigNumNoLen(value);
                }
            },
            {
                field: 'realnum', title: '实际数量', width: 60, align: 'right', sortable: true,
                formatter: function (value, row, index) {
                    return formatterBigNumNoLen(value);
                }
            },
            {
                field: 'profitlossnum', title: '盈亏数量', width: 60, align: 'right', sortable: true,
                formatter: function (value, row, index) {
                    return formatterBigNumNoLen(value);
                }
            },
            {field: 'auxbooknumdetail', title: '账面辅数量', width: 80, align: 'right'},
            {field: 'auxrealnumdetail', title: '实际辅数量', width: 80, align: 'right'},
            {field: 'auxprofitlossnumdetail', title: '盈亏辅数量', width: 80, align: 'right'},
            {
                field: 'amount', title: '金额', width: 60, align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'profitlossamount', title: '盈亏金额', width: 80, align: 'right', aliascol: 'profitlossnum',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {field: 'batchno', title: '批次号', width: 80},
            {field: 'produceddate', title: '生产日期', width: 80},
            {field: 'deadline', title: '截止日期', width: 80},
            {field: 'storagelocationname', title: '所属库位', width: 100, aliascol: 'storagelocationid'},
            {field: 'remark', title: '备注', width: 100}
        ]]
    });
    var checkList_url = "storage/checkListAddPage.do";
    var checkList_type = '${type}';
    if (checkList_type == "view" || checkList_type == "handle") {
        checkList_url = "storage/checkListViewPage.do?id=${id}";
    } else if (checkList_type == "edit") {
        checkList_url = "storage/checkListEditPage.do?id=${id}";
    }
    $(function () {
        $("#storage-panel-checkListPage").panel({
            href: checkList_url,
            cache: false,
            maximized: true,
            border: false
        });
        //按钮
        $("#storage-buttons-checkListPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/checkListAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addOrUpdateTab('storage/showCheckListAddPage.do', "盘点单新增");
                    },
                    url: '/storage/checkListAddPage.do'
                },
                </security:authorize>
                <security:authorize url="/storage/addCheckListHold.do">
                {
                    type: 'button-hold',
                    handler: function () {
                        $("#storage-form-chckListAdd").attr("action", "storage/editCheckListHold.do");
                        $("#storage-form-chckListAdd").submit();
                    },
                    url: '/storage/addCheckListHold.do'
                },
                </security:authorize>
                <security:authorize url="/storage/addCheckListSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        $("#storage-form-chckListAdd").attr("action", "storage/editCheckListSave.do");
                        $("#storage-form-chckListAdd").submit();
                    },
                    url: '/storage/addCheckListSave.do'
                },
                </security:authorize>
                <security:authorize url="/storage/checkListGiveUp.do">
                {
                    type: 'button-giveup',
                    handler: function () {
                        var type = $("#storage-buttons-checkListPage").buttonWidget("getOperType");
                        if (type == "add") {
                            var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
                            top.closeTab(currTitle);
                        }
                        else if (type == "edit") {
                            var id = $("#storage-hidden-billid").val();
                            if (id == "") {
                                return false;
                            }
                            $("#storage-panel-checkListPage").panel('refresh', 'storage/checkListViewPage.do?id=' + id);
                        }
                    },
                    url: '/storage/checkListGiveUp.do'
                },
                </security:authorize>
                <security:authorize url="/storage/deleteCheckList.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        $.messager.confirm("提醒", "是否删除当前盘点单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("删除中..");
                                    $.ajax({
                                        url: 'storage/deleteCheckList.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                var object = $("#storage-buttons-checkListPage").buttonWidget("removeData", id);
                                                $.messager.alert("提醒", "删除成功");
                                                if (null != object) {
                                                    $("#storage-panel-checkListPage").panel({
                                                        href: 'storage/checkListEditPage.do?id=' + object.id,
                                                        title: '',
                                                        cache: false,
                                                        maximized: true,
                                                        border: false
                                                    });
                                                } else {
                                                    parent.closeNowTab();
                                                }
                                            } else {
                                                $.messager.alert("提醒", "单据已删除,删除失败");
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
                    },
                    url: '/storage/deleteCheckList.do'
                },
                </security:authorize>
                <security:authorize url="/storage/auditCheckList.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        var id = $("#storage-hidden-billid").val();
                        if (id == "") {
                            return false;
                        }
                        var isfinish = $("#storage-checkList-isfinish").val();
                        if (isfinish == '0') {
                            $.messager.confirm("提醒", "盘点未完成，是否继续审核？", function (r) {
                                if (r) {
                                    auditCheckList();
                                }
                            });
                        } else {
                            auditCheckList();
                        }

                    },
                    url: '/storage/auditCheckList.do'
                },
                </security:authorize>
                <security:authorize url="/storage/oppauditCheckList.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        var businessdate = $("#storage-checkList-businessdate").val();
                        var flag = isDoneOppauditBillCaseAccounting(businessdate);
                        if (!flag) {
                            $.messager.alert("提醒", "业务日期不在会计区间内或未设置会计区间,不允许反审!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否反审盘点单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("反审中..");
                                    $.ajax({
                                        url: 'storage/oppauditCheckList.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $("#storage-buttons-checkListPage").buttonWidget("setDataID", {
                                                    id: id,
                                                    state: '2',
                                                    type: 'view'
                                                });
                                                $.messager.alert("提醒", "反审成功");
                                                $("#storage-panel-checkListPage").panel({
                                                    href: 'storage/checkListEditPage.do?id=' + id,
                                                    title: '',
                                                    cache: false,
                                                    maximized: true,
                                                    border: false
                                                });
                                            } else {
                                                $.messager.alert("提醒", "反审失败,已生成新的盘点单。");
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "反审失败");
                                        }
                                    });
                                }
                            }
                        });
                    },
                    url: '/storage/oppauditCheckList.do'
                },
                </security:authorize>
                <security:authorize url="/storage/checkListViewBackPage.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        $("#storage-panel-checkListPage").panel({
                            href: 'storage/checkListEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    },
                    url: '/storage/checkListViewBackPage.do'
                },
                </security:authorize>
                <security:authorize url="/storage/checkListViewNextPage.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        $("#storage-panel-checkListPage").panel({
                            href: 'storage/checkListEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    },
                    url: '/storage/checkListViewNextPage.do'
                },
                </security:authorize>
                <security:authorize url="/storage/checkListImport.do">
                {
                    type: 'button-import',
                    attr: {
                        type: 'importUserdefined',
                        version: '1',//导入页面显示哪个版本1：不显示，2：简化版或合同版，3：Excel文件或瑞家txt导入，4：Excel文件或三和txt导入
                        queryForm: "#storage-form-export-checkListPage",//需要传入的条件 放在form表单内
                        url: 'storage/importCheckListDataOfTheStyle.do',
                        importparam: '盘点导入。对盘点单的数据导出后，填写实际数量和盘点人后，再导入进来，可以直接盘点。<br/>实际箱数（整箱数量），实际个数（单品数量），盘点人必填。实际数量根据实际箱数和实际个数计算获得',
                        onClose: function () { //导入成功后窗口关闭时操作，
                            var id = $("#storage-hidden-billid").val();
                            $("#storage-panel-checkListPage").panel('refresh', 'storage/checkListEditPage.do?id=' + id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/checkListExport.do">
                {
                    type: 'button-export',
                    attr: {
                        queryForm: "#storage-form-export-checkListPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                        type: 'exportUserdefined',
                        name: '盘点单',
                        url: 'storage/exportCheckListDataOfTheStyle.do',
                        onBeforeExport: function () {
                            var id = $("#storage-hidden-billid").val();
                            if (id == null || id == "") {
                                $.messager.alert("提醒", "请选择盘点单");
                                return false;
                            }
                        }
                    }
                },
                </security:authorize>
                {}
            ],
            buttons: [
                {},
                <security:authorize url="/storage/checkListFinish.do">
                {
                    id: 'check-finish-button',
                    name: '盘点完成',
                    iconCls: 'button-save',
                    handler: function () {
                        $.messager.confirm("提醒", "是否确定盘点完成？盘点完成后，盘点单将不能修改。", function (r) {
                            if (r) {
                                $("#storage-checkList-checksave-checkListDetail").val("checksave");
                                $("#storage-form-chckListAdd").attr("action", "storage/editCheckListSave.do");
                                $("#storage-form-chckListAdd").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/addCheckListByCheck.do">
                {
                    id: 'check-add-button',
                    name: '追加盘点',
                    iconCls: 'button-add',
                    handler: function () {
                        $.messager.confirm("提醒", "是否对盘点错误的盘点单，重新盘点？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("生成中..");
                                    $.ajax({
                                        url: 'storage/addCheckListByCheck.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "生成成功");
                                                $("#storage-hidden-billid").val(json.newid);
                                                $("#storage-panel-checkListPage").panel({
                                                    href: 'storage/checkListEditPage.do?id=' + json.newid,
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
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/addAdjustByCheckList.do">
                {
                    id: 'check-addadjust-button',
                    name: '盈亏调整',
                    iconCls: 'button-order',
                    handler: function () {
                        $.messager.confirm("提醒", "是否根据该盘点单生成盈亏调账单,生成调账单后,会自动关闭盘点单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("生成中..");
                                    $.ajax({
                                        url: 'storage/addAdjustmentsByRefer.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "生成成功," + json.billid);
                                            } else {
                                                $.messager.alert("提醒", "生成失败.该盘点单已经生成调账单。");
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/checkListPrintBtn.do">
                {
                    id: 'menuButton',
                    type: 'menu',
                    name: '打印',
                    iconCls: 'button-print',
                    button: [
                        <security:authorize url="/storage/checkListPrintViewBtn.do">
                        {
                            id: 'button-printview-checklist',
                            name: '盘点单打印预览',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/checkListPrintDoneBtn.do">
                        {
                            id: 'button-print-checklist',
                            name: '盘点单打印',
                            iconCls: 'button-print',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        {}
                    ]
                },
                </security:authorize>
                {}
            ],
            layoutid: 'storage-layout-checkListPage',
            model: 'bill',
            type: 'view',
            tab: '盘点单列表',
            taburl: '/storage/showCheckListPage.do',
            id: '${id}',
            datagrid: 'storage-datagrid-checkListPage'
        });
    });

    //显示盘点单明细添加页面
    function beginAddDetail() {
        //验证表单
        var flag = $("#storage-form-chckListAdd").form('validate');
        var storageid = $("#storage-checkList-storageid").val();
        if (storageid == null || storageid == "") {
            $.messager.alert("提醒", '请先选择所属仓库');
            return false;
        }
        if(!flag){
            return false;
        }
        //判断盘点单添加权限
        <security:authorize url="/storage/checkListDetailAdd.do">
        $('<div id="storage-dialog-checkListAddPage-content"></div>').appendTo('#storage-dialog-checkListAddPage');
        $('#storage-dialog-checkListAddPage-content').dialog({
            title: '盘点单明细添加',
            width: 680,
            height: 400,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            modal: true,
            href: 'storage/showCheckListDetailAddPage.do?storageid=' + storageid,
            onLoad: function () {
                $("#storage-checkList-goodsid").focus();
            },
            onClose: function () {
                $('#storage-dialog-checkListAddPage-content').dialog("destroy");
            }
        });
        $('#storage-dialog-checkListAddPage-content').dialog("open");
        </security:authorize>
    }
    //显示盘点单明细修改页面
    function beginEditDetail(rowData) {
        var checklistid = $("#storage-checkList-thisid").val();
        //验证表单
        var flag = $("#storage-form-chckListAdd").form('validate');
        if (flag == false) {
//            $.messager.alert("提醒", '请先选择所属仓库');
//            $("#storage-checkList-storageid").focus();
            return false;
        }
//			var rows = $("#storage-datagrid-checkListAddPage").datagrid('getChecked');
//    		if(rows.length != 1){
//    			$.messager.alert("提醒", "请选择一条记录");
//				$("#storage-datagrid-checkListAddPage").datagrid('clearChecked');
//    			return false;
//    		}
        var row;
        if (null == rowData) {
            row = $("#storage-datagrid-checkListAddPage").datagrid('getSelected');
            if (row == null) {
                $.messager.alert("提醒", "请选择一条记录");
                return false;
            }
        } else {
            row = rowData;
        }
        if (row.goodsid == undefined) {
            beginAddDetail();
        } else {
            var url = 'storage/showCheckListDetailEditPage.do?goodsid=' + row.goodsid + '&checklistid=' + checklistid + '&id=' + row.id;
            $('<div id="storage-dialog-checkListAddPage-content"></div>').appendTo('#storage-dialog-checkListAddPage');
            $('#storage-dialog-checkListAddPage-content').dialog({
                title: '盘点单明细修改',
                width: 680,
                height: 400,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                resizable: true,
                closed: true,
                cache: false,
                href: url,
                modal: true,
                onLoad: function () {
                    //加载数据
                    if (null == row.auxrealnum) {
                        row.auxrealnum = Number(0);
                    }
                    if (null == row.auxrealremainder) {
                        row.auxrealremainder = Number(0);
                    }
                    var isCheckListUseBatch = $("#storage-checkList-hidden-isCheckListUseBatch").val();
                    var isbatch = $("#storage-checkList-hidden-isbatch").val();
                    if (isbatch == "1" && isCheckListUseBatch == "1") {
                        var storageid = $("#storage-checkList-storageid").widget("getValue");
                        var param = null;
                        if (storageid != null && storageid != "") {
                            param = [{field: 'goodsid', op: 'equal', value: row.goodsid},
                                {field: 'storageid', op: 'equal', value: storageid}];
                        } else {
                            param = [{field: 'goodsid', op: 'equal', value: row.goodsid}];
                        }
                        console.log(param)
//	                    	$("#storage-checkList-batchno").widget({
//	                    		referwid:'RL_T_STORAGE_BATCH_LIST',
//	                    		param:param,
//	                			width:165,
//	                			required:true,
//	            				singleSelect:true,
//	            				onSelect: function(obj){
//	            					$("#storage-checkList-booknum").val(obj.existingnum);
//	            					$("#storage-checkList-amount").val(Number(data.newbuyprice)*Number(obj.existingnum));
//	            					$("#storage-checkList-hidden-summarybatchid").val(obj.id);
//	            					$("#storage-checkList-batchno").val(obj.batchno);
//	            					$("#storage-checkList-produceddate").val(obj.produceddate);
//	            					$("#storage-checkList-deadline").val(obj.deadline);
//	            					$("#storage-checkList-storagelocationid").val(obj.storagelocationid);
//	            					$("#storage-checkList-storagelocationname").val(obj.storagelocationname);
//
//	            					computNum();
//	            					$("#storage-checkList-realnum").val("0");
//	            					$("#storage-checkList-realnum").focus();
//	            					$("#storage-checkList-realnum").select();
//	            				},
//	            				onClear:function(){
//	            					$("#storage-checkList-booknum").val(0);
//	            					$("#storage-checkList-amount").val(0);
//	            					$("#storage-checkList-hidden-summarybatchid").val("");
//	            					$("#storage-checkList-batchno").val("");
//	            					$("#storage-checkList-produceddate").val("");
//	            					$("#storage-checkList-deadline").val("");
//	            					$("#storage-checkList-storagelocationid").val("");
//	            					$("#storage-checkList-storagelocationname").val("");
//	            				}
//	                    	});
                    } else {
                        $("#storage-checkList-batchno").addClass("no_input");
                        $("#storage-checkList-batchno").attr("readonly", "readonly");
                    }
                    $("#storage-checkList-realnum").focus();
                    $("#storage-checkList-realnum").select();

                    formaterNumSubZeroAndDot();

                    $("#storage-form-checkListDetailEditPage").form('validate');
                },
                onClose: function () {
                    $('#storage-dialog-checkListAddPage-content').dialog("destroy");
                }
            });
            $('#storage-dialog-checkListAddPage-content').dialog("open");
        }
    }
    //保存盘点单
    function addSaveDetail(goFlag) { //添加新数据确定后操作，
        var flag = $("#storage-form-checkListDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#storage-form-checkListDetailAddPage").serializeJSON();
        form.editFlag = true;

        var ret = checkList_AjaxConn({
            goodsid: form.goodsid,
            checklistid: $("#storage-checkList-thisid").val()
        }, "storage/doCheckListDetailIsHadGoods.do");
        var json = $.parseJSON(ret);
        var updateFlag = false;
        updateFlag = json.flag;
        if (updateFlag) {
            $.messager.alert("提醒", "此商品已经添加！");
            return false;
        }
        loading("添加中...");
        var form2 = $("#storage-form-chckListAdd").serializeJSON();

        form["checklistid"] = $("#storage-checkList-thisid").val();
        $.ajax({
            url: 'basefiles/getItemByGoodsAndStorage.do',
            data:{
                goodsid:form.goodsid,
                storageid:$("#storage-checkList-storageid").widget('getValue')
            },
            async: false,
            type: 'post',
            dataType: 'json',
            success: function (json) {
                form.itemno=json.itemno;
            },
        });
        var detaillist = [];
        detaillist.push(form);

        form2["detailList"] = JSON.stringify(detaillist);
        var ret = checkList_AjaxConn(form2, "storage/addSaveCheckListDetail.do");
        var retjson = $.parseJSON(ret);
        loaded();
        if (retjson.flag) {
            if ("add" == checkList_type || "copy" == checkList_type) {
                top.addOrUpdateTab('storage/showCheckListEditPage.do?id=' + retjson.id, "盘点单查看");
                top.closeTab("盘点单新增");
            } else if ("edit" == checkList_type) {
                $("#storage-datagrid-checkListAddPage").datagrid('load');
            }
        }

        if (goFlag) { //go为true确定并继续添加一条
            checkListformReset();
        }
        else { //否则直接关闭
            $("#storage-dialog-checkListAddPage-content").dialog('destroy');
        }
        $("#storage-checkList-storageid").widget('readonly', true);
        countTotal();
    }
    //修改保存
    function editSaveDetail(goFlag) {
        var flag = $("#storage-form-checkListDetailEditPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#storage-form-checkListDetailEditPage").serializeJSON();
        var rows = $("#storage-datagrid-checkListAddPage").datagrid("getChecked");
        var rowSelected = rows[0];
        form.id = rowSelected.id;
        form.checklistid = rowSelected.checklistid;
        form.booknum = rowSelected.booknum;
        form.auxbooknumdetail = rowSelected.auxbooknumdetail;
        form.auxbooknum = rowSelected.auxbooknum;
        var rowIndex = $("#storage-datagrid-checkListAddPage").datagrid("getRowIndex", rowSelected);
        form.editFlag = true;

        loading("修改中...");
        var form2 = $("#storage-form-chckListAdd").serializeJSON();

        var detaillist = [];
        detaillist.push(form);

        form2["detailList"] = JSON.stringify(detaillist);
        var ret = checkList_AjaxConn(form2, "storage/editSaveCheckListDetail.do");
        var retjson = $.parseJSON(ret);
        loaded();
        if (retjson.flag) {
            if ("add" == checkList_type || "copy" == checkList_type) {
                top.addOrUpdateTab('storage/showCheckListEditPage.do?id=' + retjson.id, "盘点单查看");
            } else if ("edit" == checkList_type) {
                $("#storage-datagrid-checkListAddPage").datagrid('updateRow', {index: rowIndex, row: form});
                $("#storage-datagrid-checkListAddPage").datagrid('clearSelections');
//							$("#storage-datagrid-checkListAddPage").datagrid('load');
            }
            if (goFlag) { //go为true确定并继续添加一条
                var rows = $("#storage-datagrid-checkListAddPage").datagrid("getRows");
                var rownums = 0;
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].goodsid != null) {
                        rownums++;
                    }
                }
                if (rowIndex < rownums - 1) {
                    rowIndex = rowIndex + 1;
                    $("#storage-datagrid-checkListAddPage").datagrid("selectRow", rowIndex);
                    $('#storage-dialog-checkListAddPage-content').dialog("destroy");
                    //加载数据
                    var object = $("#storage-datagrid-checkListAddPage").datagrid("getSelected");
                    beginEditDetail(object)
                } else {
                    $.messager.alert("提醒", "已经到最后一条了！");
                    $("#storage-dialog-checkListAddPage-content").dialog('destroy');
                }
            }
            else { //否则直接关闭
                $("#storage-dialog-checkListAddPage-content").dialog('destroy');
            }
            countTotal();
        }else{
            $.messager.alert("提醒", retjson.msg);
        }


    }
    //删除明细
    function removeDetail() {
        var rows = $("#storage-datagrid-checkListAddPage").datagrid('getChecked');
        if (rows.length == 0) {
            $.messager.alert("提醒", "请选择要删除的记录！");
            return false;
        }
        loading("删除中...");
        var rows = $("#storage-datagrid-checkListAddPage").datagrid('getChecked');
        var form2 = $("#storage-form-chckListAdd").serializeJSON();
        form2["delDetailList"] = JSON.stringify(rows);
        var ret = checkList_AjaxConn(form2, "storage/deleteCheckListDetails.do");
        var retjson = $.parseJSON(ret);
        loaded();
        if (retjson.flag) {
            var totalrows = $("#storage-datagrid-checkListAddPage").datagrid('getRows');
            var rows2 = [];
            var rows3 = [];
            for (var i = 0; i < totalrows.length; i++) {
                if (!isObjectEmpty(totalrows[i])) {
                    rows2.push(totalrows[i]);
                }
            }
            for (var i = 0; i < rows2.length; i++) {
                var dif = true;
                for (var j = 0; j < rows.length; j++) {
                    dif = dif && (!isObjectEmpty(rows[j]) && rows2[i].goodsid != rows[j].goodsid);
                }
                if (dif) {
                    rows3.push(rows2[i]);
                }
            }
            if (rows3.length < 10) {
                var j = 10 - rows3.length;
                for (var i = 0; i < j; i++) {
                    rows3.push({})
                }
            }
            $("#storage-datagrid-checkListAddPage").datagrid('loadData', rows3);
        }
        countTotal();
        var rows = $("#storage-datagrid-checkListAddPage").datagrid('getRows');
        var index = -1;
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].goodsid != undefined) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            $("#storage-checkList-storageid").widget('readonly', false);
        }
    }
    //计算合计
    function countTotal() {
        var rows = $("#storage-datagrid-checkListAddPage").datagrid('getRows');
        var leng = rows.length;
        var booknum = 0;
        var realnum = 0;
        var profitlossnum = 0;
        var amount = 0;
        var profitlossamount = 0;
        for (var i = 0; i < leng; i++) {
            booknum += Number(rows[i].booknum == undefined ? 0 : rows[i].booknum);
            realnum += Number(rows[i].realnum == undefined ? 0 : rows[i].realnum);
            profitlossnum += Number(rows[i].profitlossnum == undefined ? 0 : rows[i].profitlossnum);
            amount += Number(rows[i].amount == undefined ? 0 : rows[i].amount);
            profitlossamount += Number(rows[i].profitlossamount == undefined ? 0 : rows[i].profitlossamount);
        }
        var foot = [{
            goodsid: '当页合计',
            booknum: booknum,
            realnum: realnum,
            profitlossnum: profitlossnum,
            amount: amount,
            profitlossamount: profitlossamount
        }];
        if (null != CLD_footerobject) {
            foot.push(CLD_footerobject);
        }
        $("#storage-datagrid-checkListAddPage").datagrid('reloadFooter', foot);
    }
    //获取仓库下商品明细
    function getCheckListDetail() {
        var storageid = $("#storage-checkList-storageid").widget("getValue");
        var creattype = $("#storage-checkList-createtype").val();
        if (storageid != null && storageid != "") {
            //生成方式为系统生成时
            if (creattype == "2") {
                showCheckListDetailByBrand();
                $("#storage-contextMenu-checkListAddPage").menu('disableItem', '#storage-addRow-checkListAddPage');
                $("#storage-contextMenu-checkListAddPage").menu('disableItem', '#storage-removeRow-checkListAddPage');
            } else {
                $("#storage-contextMenu-checkListAddPage").menu('enableItem', '#storage-addRow-checkListAddPage');
                $("#storage-contextMenu-checkListAddPage").menu('enableItem', '#storage-removeRow-checkListAddPage');
            }
        }
    }
    function showCheckListDetailByBrand(goodssorts) {
        var storageid = $("#storage-checkList-storageid").widget("getValue");
        if (storageid == "") {
            $.messager.alert("提醒", "请填写所属仓库!");
            $("#storage-checkList-storageid").focus();
            return false;
        }
        //验证表单
        var flag = $("#storage-form-chckListAdd").form('validate');
        if(!flag){
            return false;
        }
        if ($("#storage-dialog-checkListBrandPage1").size() > 0) {
            $("#storage-dialog-checkListBrandPage1").dialog("close");
        }
        $('<div id="storage-dialog-checkListBrandPage1"></div>').appendTo('#storage-dialog-checkListBrandPage');
        $("#storage-dialog-checkListBrandPage1").dialog({
            title: '按品牌添加明细',
            width: 400,
            height: 450,
            closed: true,
            cache: false,
            href: 'storage/showStorageBrandListPage.do?storageid=' + storageid,
            modal: true,
            buttons: [
                {
                    text: '下一步',
                    iconCls: 'button-next',
                    plain: true,
                    handler: function () {
                        var rows = $("#checklist-storage-brandlist").datagrid("getChecked");
                        var brands = "";
                        for (var i = 0; i < rows.length; i++) {
                            if (i == 0) {
                                brands = rows[i].id;
                            } else {
                                brands += "," + rows[i].id;
                            }
                        }
                        showCheckListDetailByGoodsSort(brands);
                    }
                },
                {
                    text: '确定',
                    iconCls: 'button-save',
                    plain: true,
                    handler: function () {
                        var rows = $("#checklist-storage-brandlist").datagrid("getChecked");
                        var brands = "";
                        for (var i = 0; i < rows.length; i++) {
                            if (i == 0) {
                                brands = rows[i].id;
                            } else {
                                brands += "," + rows[i].id;
                            }
                        }
                        addCheckListDetail(storageid, brands, goodssorts);
                    }
                }
            ],
            onClose: function () {
                $('#storage-dialog-checkListBrandPage1').dialog("destroy");
            }
        });
        $("#storage-dialog-checkListBrandPage1").dialog("open");
    }
    function showCheckListDetailByGoodsSort(brands) {
        var storageid = $("#storage-checkList-storageid").widget("getValue");
        if (storageid == "") {
            $.messager.alert("提醒", "请填写所属仓库!");
            $("#storage-checkList-storageid").focus();
            return false;
        }
        //验证表单
        var flag = $("#storage-form-chckListAdd").form('validate');
        if(!flag){
            return false;
        }
        if ($("#storage-dialog-checkListBrandPage1").size() > 0) {
            $("#storage-dialog-checkListBrandPage1").dialog("close");
        }
        $('<div id="storage-dialog-checkListBrandPage1"></div>').appendTo('#storage-dialog-checkListBrandPage');
        $("#storage-dialog-checkListBrandPage1").dialog({
            title: '按商品分类添加明细',
            width: 400,
            height: 450,
            closed: true,
            cache: false,
            href: 'storage/showStorageGoodsSortListPage.do',
            modal: true,
            buttons: [
                {
                    text: '上一步',
                    iconCls: 'button-back',
                    plain: true,
                    handler: function () {
                        var waresClassTree = $.fn.zTree.getZTreeObj("checklist-storage-goodssort");
                        var rows = waresClassTree.getCheckedNodes(true);
                        var goodssorts = "";
                        for (var i = 0; i < rows.length; i++) {
                            if (i == 0) {
                                goodssorts = rows[i].id;
                            } else {
                                goodssorts += "," + rows[i].id;
                            }
                        }
                        showCheckListDetailByBrand(goodssorts);
                    }
                },
                {
                    text: '确定',
                    iconCls: 'button-save',
                    plain: true,
                    handler: function () {
                        var waresClassTree = $.fn.zTree.getZTreeObj("checklist-storage-goodssort");
                        var rows = waresClassTree.getCheckedNodes(true);
                        var goodssorts = "";
                        for (var i = 0; i < rows.length; i++) {
                            if (i == 0) {
                                goodssorts = rows[i].id;
                            } else {
                                goodssorts += "," + rows[i].id;
                            }
                        }
                        addCheckListDetail(storageid, brands, goodssorts);
                    }
                }
            ],
            onClose: function () {
                $('#storage-dialog-checkListBrandPage1').dialog("destroy");
            }
        });
        $("#storage-dialog-checkListBrandPage1").dialog("open");
    }
    function addCheckListDetail(storageid, brands, goodssorts) {
        $.messager.confirm("提醒", "将真实添加数据，是否确定添加数据?", function (r) {
            if (r) {
                loading("添加中...");
                var json = $("#storage-form-chckListAdd").serializeJSON();
                json["storageid"] = storageid;
                json["brands"] = brands;
                json["goodssorts"] = goodssorts;
                $.ajax({
                    url: 'storage/addCheckListDetail.do',
                    data: json,
                    type: 'post',
                    dataType: 'json',
                    success: function (json) {
                        if (json.flag) {
                            top.addOrUpdateTab('storage/showCheckListEditPage.do?id=' + json.id, "盘点单查看");
                        }
                        loaded();
                        $("#storage-dialog-checkListBrandPage1").dialog("close");
                    },
                    error: function () {
                        loaded();
                    }
                });
            }
        });
    }

    $(function () {
        //关闭明细添加页面
        $(document).bind('keydown', 'esc', function () {
            $("#storage-dialog-checkListAddPage-content").dialog("close");
        });
        $(document).bind('keydown', 'ctrl+enter', function () {
            $("#storage-checkList-goodsunitname").focus();
            $("#storage-savegoon-checkListDetailAddPage").trigger("click");
            $("#storage-savegoon-checkListDetailEditPage").trigger("click");
        });
        $(document).bind('keydown', '+', function () {
            $("#storage-checkList-goodsunitname").focus();
            setTimeout(function () {
                $("#storage-savegoon-checkListDetailAddPage").trigger("click");
                $("#storage-savegoon-checkListDetailEditPage").trigger("click");
            }, 300);
            return false;
        });
    });
    function auditCheckList() {
        var id = $("#storage-hidden-billid").val();
        loading("检查中..");
        $.ajax({
            url: 'storage/getCheckListNumIsTure.do?id=' + id,
            type: 'post',
            dataType: 'json',
            success: function (json) {
                loaded();
                var msg = "";
                var addflag = true;
                if (json.flag) {
                    msg = "盘点后的实际数量与账面数量一致";
                    addflag = false;
                } else {
                    msg = "有" + json.num + "条商品明细数据，盘点后的实际数量与账面数量不一致。";
                }
                $("#storage-dialog-confirm-checkListPage").dialog({
                    title: '审核提醒',
                    width: 300,
                    height: 150,
                    content: '<div class="messager-icon messager-info" style="margin: 10px;"></div><div style="margin: 10px;">' + msg + '</div>',
                    shadow: true,
                    modal: true,
                    buttons: [
                        {
                            text: '完成本次盘点',
                            iconCls: 'button-audit',
                            plain: true,
                            handler: function () {
                                loading("操作中..");
                                $.ajax({
                                    url: 'storage/closeCheckListByCheck.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            if (addflag) {
                                                $("#storage-dialog-confirm1-checkListPage").dialog({
                                                    title: '提醒',
                                                    width: 300,
                                                    height: 150,
                                                    content: '<div class="messager-icon messager-info" style="margin: 10px;"></div><div style="margin: 10px;">操作成功，完成本次盘点。是否追加生成新的盘点单？</div>',
                                                    shadow: true,
                                                    modal: true,
                                                    buttons: [
                                                        {
                                                            text: '追加',
                                                            plain: true,
                                                            iconCls: 'button-add',
                                                            handler: function () {
                                                                $.ajax({
                                                                    url: 'storage/addCheckListByCheck.do?id=' + id,
                                                                    type: 'post',
                                                                    dataType: 'json',
                                                                    success: function (json) {
                                                                        loaded();
                                                                        if (json.flag) {
                                                                            $.messager.alert("提醒", "生成成功");
                                                                            $("#storage-hidden-billid").val(json.newid);
                                                                            $("#storage-panel-checkListPage").panel({
                                                                                href: 'storage/checkListEditPage.do?id=' + json.newid,
                                                                                title: '',
                                                                                cache: false,
                                                                                maximized: true,
                                                                                border: false
                                                                            });
                                                                        } else {
                                                                            $.messager.alert("提醒", "生成失败");
                                                                        }
                                                                        $("#storage-dialog-confirm1-checkListPage").dialog("close");
                                                                    }
                                                                });
                                                            }
                                                        },
                                                        {
                                                            text: '稍后追加',
                                                            plain: true,
                                                            iconCls: 'button-stop',
                                                            handler: function () {
                                                                $("#storage-dialog-confirm1-checkListPage").dialog("close");
                                                            }
                                                        }
                                                    ],
                                                    onClose: function () {
                                                        $("#storage-panel-checkListPage").panel("refresh");
                                                    }
                                                });
                                            } else {
                                                $.messager.alert("提醒", "盘点结束");
                                                $("#storage-panel-checkListPage").panel("refresh");
                                            }
                                        } else {
                                            $.messager.alert("提醒", "操作失败");
                                        }
                                        $("#storage-dialog-confirm-checkListPage").dialog("close");
                                    }
                                });
                            }
                        },
                        {
                            text: '关闭盘点',
                            iconCls: 'button-close',
                            plain: true,
                            handler: function () {
                                loading("审核中..");
                                $.ajax({
                                    url: 'storage/auditCheckList.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "审核通过");
                                            $("#storage-buttons-checkListPage").buttonWidget("setDataID", {
                                                id: id,
                                                state: '3',
                                                type: 'view'
                                            });
                                            $("#storage-panel-checkListPage").panel({
                                                href: 'storage/checkListViewPage.do?id=' + id,
                                                title: '',
                                                cache: false,
                                                maximized: true,
                                                border: false
                                            });
                                        } else {
                                            $.messager.alert("警告", "审核失败," + json.msg);
                                        }
                                        $("#storage-dialog-confirm-checkListPage").dialog("close");
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "审核出错");
                                    }
                                });
                            }
                        }, {
                            text: '取消',
                            iconCls: 'button-stop',
                            plain: true,
                            handler: function () {
                                $("#storage-dialog-confirm-checkListPage").dialog("close");
                            }
                        }]
                });
            }
        });
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "checklist-dialog-print",
            code: "storage_checklist",
            url_preview: "print/storage/checkListPrintView.do",
            url_print: "print/storage/checkListPrint.do",
            btnPreview: "button-printview-checklist",
            btnPrint: "button-print-checklist",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var id = $("#storage-checkList-thisid").val();
                printParam.id = id;
                var printtimes = $("#storage-checkList-printtimes").val();
                if (printtimes > 0)
                    printParam.printIds = [id];
                return true;
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
