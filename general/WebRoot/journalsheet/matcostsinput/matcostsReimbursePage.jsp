<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>代垫收回页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<%
    boolean isEdit = false;
%>
<security:authorize url="/journalsheet/matcostsInput/matcostsReimburseEditBtn.do">
    <% isEdit = true; %>
</security:authorize>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div id="journalsheet-button-matcostsReimburse" class="buttonBG"></div>
    </div>
    <div data-options="region:'center'">
        <table id="journalsheet-table-matcostsReimburse"></table>
        <div id="journalsheet-table-matcostsReimburseBtn" style="padding:2px;height:auto">
            <form action="" id="matcostsReimburse-form-ListQuery" method="post">
                <table class="querytable">
                    <tr>
                        <td>业务日期:</td>
                        <td><input name="begintime" id="matcostsReimburse-date-begintime" value="${firstday }"
                                   class="Wdate" style="width:100px;"
                                   onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"/>
                            到<input name="endtime" id="matcostsReimburse-date-endtime" value="${today }" class="Wdate"
                                    style="width:100px;"
                                    onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"/>
                        </td>
                        <td>开单日期：</td>
                        <td>
                            <input type="text" id="matcostsReimburse-date-billtime" name="billtime" style="width:150px;"
                                   class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value=""/>
                        </td>
                        <td>编码:</td>
                        <td><input id="journalsheet-text-id" name="id" type="text" style="width: 150px;"/></td>
                    </tr>
                    <tr>
                        <td>供 应 商:</td>
                        <td>
                            <input id="journalsheet-widget-supplierquery" name="supplierid" type="text"
                                   style="width:190px"/>
                            <label style="display: inline-block; vertical-align: middle;  ">
                                <input type="checkbox" id="journalsheet-widget-supplier-empty" name="emptysupplier"
                                       value="1" style="margin:0 3px;vertical-align: middle; "/><span
                                    style="cursor:pointer;">选择空</span>
                            </label>
                        </td>
                        <td>所属部门:</td>
                        <td><input id="journalsheet-widget-matcostsReimburse-supplierdeptidquery" name="supplierdeptid"
                                   type="text" style="width: 150px;"/></td>
                        <td>核销状态:</td>
                        <td>
                            <select name="writeoffstatus" style="width:150px;">
                                <option value="">全部</option>
                                <option value="1">核销</option>
                                <option value="3">核销中</option>
                                <option value="2">未核销</option>
                            </select>
                        </td>

                    </tr>
                    <tr>
                        <td>收入科目：</td>
                        <td>
                            <input id="matcostsReimburseList-widget-shsubjectid" name="shsubjectidlike" type="text"
                                   style="width: 150px;"/>
                        </td>
                        <td>打印状态:</td>
                        <td>
                            <select name="printsign" style="width:150px;">
                                <option></option>
                                <option value="1">未打印</option>
                                <%-- 特别
                                <option value="2">小于</option>
                                <option value="3">小于等于</option>
                                 --%>
                                <option value="4">已打印</option>
                                <%-- 特别
                                <option value="5">大于等于</option>
                                 --%>
                            </select>
                            <input type="hidden" name="queryprinttimes" value="0"/>
                        </td>
                        <td rowspan="2" colspan="2" class="tdbutton">
                            <a href="javaScript:void(0);" id="matcostsReimburse-query-List" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="matcostsReimburse-query-reloadList"
                               class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div style="display:none">
            <div id="matcostsReimburse-dialog-operate"></div>
            <div id="matcostsReimburseWriteoffQuery-dialog-operate"></div>
            <div id="matcostsReimburseTypeChange-dialog-operate"></div>
        </div>
    </div>
    <a href="javaScript:void(0);" id="matcostsReimburse-buttons-exportclick" style="display: none" title="导出">导出</a>
    <a href="javaScript:void(0);" id="matcostsReimburse-buttons-importclick" style="display: none" title="导入">导入</a>
</div>
<script type="text/javascript">
    var footerobject = null;
    var reimburseWriteoffIdarr = new Array();
    var reimburseWriteoffQueryPageJsonForm = null;
    var matcostsReimburse_AjaxConn = function (Data, Action, Str) {
        if (null != Str && "" != Str) {
            loading(Str);
        }
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
    }
    function refreshLayout(title, url) {
        $('#matcostsReimburse-dialog-operate').dialog({
            title: title,
            width: 500,
            height: 380,
            closed: true,
            cache: false,
            href: url,
            maximizable: true,
            resizable: true,
            modal: true,
            onLoad: function () {
                $("#journalsheet-text-oaid").focus();
            }
        });
        $('#matcostsReimburse-dialog-operate').dialog('open');
    }
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

    function computeUnitnumPriceAmountChange(type) {
        if (type == null) {
            return;
        }
        var unitnum = $("#matcostsReimburse-text-unitnum").val() || 0;
        var taxprice = $("#matcostsReimburse-text-taxprice").val() || 0;
        var amount = $("#matcostsReimburse-text-reimburseamount").val() || 0;


        $("#matcostsReimburse-text-unitnum").addClass("inputload");
        $("#matcostsReimburse-text-taxprice").addClass("inputload");
        $("#matcostsReimburse-text-reimburseamount").addClass("inputload");

        if (type == "1") {
            $("#matcostsReimburse-text-reimburseamount").removeClass("inputload");
        } else if (type == "2") {
            $("#matcostsReimburse-text-unitnum").removeClass("inputload");
        } else if (type == "3") {
            $("#matcostsReimburse-text-taxprice").removeClass("inputload");
        }

        $.ajax({
            url: 'basefiles/computeUnitumPriceAmountChange.do',
            type: 'post',
            data: {type: type, unitnum: unitnum, taxprice: taxprice, amount: amount},
            dataType: 'json',
            async: false,
            success: function (json) {
                $("#matcostsReimburse-text-unitnum").val(json.unitnum);
                $("#matcostsReimburse-text-taxprice").val(json.taxprice);
                $("#matcostsReimburse-text-reimburseamount").val(json.amount);

                $("#matcostsReimburse-text-unitnum").removeClass("inputload");
                $("#matcostsReimburse-text-taxprice").removeClass("inputload");
                $("#matcostsReimburse-text-reimburseamount").removeClass("inputload");
            },
            error: function () {
                $("#matcostsReimburse-text-unitnum").removeClass("inputload");
                $("#matcostsReimburse-text-taxprice").removeClass("inputload");
                $("#matcostsReimburse-text-reimburseamount").removeClass("inputload");
            }
        });
    }

    function showBankidWidget(required, initValue) {
        if (required == null) {
            required = false;
        }
        if (typeof(initValue) == 'undefined' || null == initValue || "" == $.trim(initValue)) {
            initValue = '';
        }
        $("#matcostsReimburse-widget-bankid-div").empty();
        var inputStr = "<input id=\"matcostsReimburse-widget-bankid\" name=\"matcostsInput.bankid\" type=\"text\"/>";
        $("#matcostsReimburse-widget-bankid-div").append(inputStr);
        //银行名称
        $("#matcostsReimburse-widget-bankid").widget({
            width: 120,
            name: 't_js_matcostsinput',
            col: 'bankid',
            required: required,
            initValue: initValue,
            singleSelect: true,
            onSelect: function () {
                $(this).blur();
                $("#matcostsReimburse-widget-shsubjectid").focus();
                $("#matcostsReimburse-widget-shsubjectid").select();
            }
        });
    }


    function initReimburseAEVPage() {
        var tmp = $("#matcostsReimburse-text-unitnum").val() || 0;
        if (!isNaN(tmp)) {
            $("#matcostsReimburse-text-unitnum").val(formatterBigNumNoLen(tmp));
        }
        tmp = $("#matcostsReimburse-text-taxprice").val() || 0;
        if (!isNaN(tmp)) {
            $("#matcostsReimburse-text-taxprice").val(formatterMoney(tmp, 4));
        }
        tmp = $("#matcostsReimburse-text-reimburseamount").val() || 0;
        if (!isNaN(tmp)) {
            $("#matcostsReimburse-text-reimburseamount").val(formatterMoney(tmp));
        }
    }

    //根据初始的列与用户保存的列生成以及字段权限生成新的列
    var matcostsReimburseListColJson = $("#journalsheet-table-matcostsReimburse").createGridColumnLoad({
        name: 't_js_matcostsinput',
        frozenCol: [[
            {field: 'idok', checkbox: true, isShow: true}
        ]],
        commonCol: [[
            {field: 'id', title: '编码', width: 125, sortable: true},
            {
                field: 'biltime', title: '开单日期', width: 80, isShow: true,
                formatter: function (val, rowData, rowIndex) {
                    if (rowData.addtime != null) {
                        var tmpArr = new Array();
                        if (rowData.addtime.indexOf('T')) {
                            tmpArr = rowData.addtime.split('T');
                        } else if (rowData.addtime.indexOf('t')) {
                            tmpArr = rowData.addtime.split('t');
                        } else {
                            tmpArr = rowData.addtime.split(' ');
                        }
                        if (tmpArr.length > 0) {
                            return tmpArr[0];
                        }
                        return "";
                    }
                }
            },
            {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
            {field: 'supplierid', title: '供应商编码', width: 70, sortable: true},
            {field: 'suppliername', title: '供应商名称', width: 210, isShow: true},
            {
                field: 'supplierdeptid', title: '所属部门', width: 70, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.supplierdeptname;
                }
            },
            {
                field: 'reimbursetype', title: '收回方式', width: 60, sortable: true, hidden: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.reimbursetypename;
                }
            }
            <c:forEach items="${reimbursetypeList }" var="list">
            , {
                field: 'reimburse_${list.code}',
                title: '${list.codename}',
                align: 'right',
                resizable: true,
                isShow: true,
                formatter: function (value, rowData, rowIndex) {
                    return formatterMoney(value);
                }
            }
            </c:forEach>,
            {
                field: 'bankid', title: '银行名称', width: 60, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.bankname;
                }
            },
            {
                field: 'shsubjectid', title: '收回科目', width: 60, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.shsubjectname;
                }
            },
            {
                field: 'unitid', title: '单位', width: 60,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.unitname;
                }
            },
            {
                field: 'unitnum', title: '数量', width: 60, sortable: true, align: 'right',
                formatter: function (val, rowData, rowIndex) {
                    if (val != "" && val != null) {
                        return formatterBigNumNoLen(val);
                    }
                    else {
                        return "0";
                    }
                }
            },
            {
                field: 'taxprice', title: '单价', width: 80, sortable: true, align: 'right',
                formatter: function (val, rowData, rowIndex) {
                    if (val != "" && val != null) {
                        return formatterMoney(val, 4);
                    }
                    else {
                        return "0.0000";
                    }
                }
            },
            {
                field: 'reimburseamount', title: '收回金额', resizable: true, sortable: true, align: 'right',
                formatter: function (val, rowData, rowIndex) {
                    if (val != "" && val != null) {
                        return formatterMoney(val);
                    }
                    else {
                        return "0.00";
                    }
                }
            },
            {
                field: 'iswriteoff', title: '核销状态', resizable: true, sortable: true, align: 'right',
                formatter: function (val, rowData, rowIndex) {
                    if (val == "1") {
                        return "核销";
                    } else if (val == '2') {
                        return "核销中";
                    } else {
                        return "未核销";
                    }
                }
            },
            {
                field: 'writeoffamount', title: '核销金额', resizable: true, sortable: true, align: 'right',
                formatter: function (val, rowData, rowIndex) {
                    return formatterMoney(val);
                }
            },
            {
                field: 'remainderamount', title: '未核销收回', resizable: true, sortable: true, align: 'right',
                formatter: function (val, rowData, rowIndex) {
                    return formatterMoney(val);
                }
            },
            {
                field: 'writeoffdate', title: '核销日期', resizable: true, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    if ((rowData.iswriteoff == '1' || rowData.iswriteoff == '2') && val) {
                        if (val.length >= 10) {
                            return val.substring(0, 10);
                        } else {
                            return val;
                        }
                    }
                }
            },
            {
                field: 'writeoffer', title: '核销人员', resizable: true, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    if ((rowData.iswriteoff == '1' || rowData.iswriteoff == '2') && rowData.writeoffername) {
                        return rowData.writeoffername;
                    }
                }
            },
            {field: 'remark', title: '备注', width: 80, sortable: true},
            {
                field: 'printstate', title: '打印状态', width: 80, isShow: true,
                formatter: function (value, rowData, index) {
                    if (rowData.shprinttimes > 0) {
                        return "已打印";
                    } else if (rowData.shprinttimes == null) {
                        return "";
                    } else if (rowData.shprinttimes == -99) {
                        return "";
                    } else {
                        return "未打印";
                    }
                }
            },
            {field: 'shprinttimes', title: '打印次数', width: 80, hidden: true},
            {field: 'adduserid', title: '制单人编码', width: 80, sortable: true, hidden: true},
            {field: 'addusername', title: '制单人', width: 80, sortable: true, hidden: true},
            {
                field: 'addtime', title: '制单时间', width: 130, sortable: true, hidden: true,
                formatter: function (val, rowData, rowIndex) {
                    if (val) {
                        return val.replace(/[tT]/, " ");
                    }
                }
            },
            {
                field: 'sourcefrome', title: '数据来源', resizable: true, hidden: true,
                formatter: function (val, rowData, rowIndex) {
                    if (val == "0") {
                        return "手动添加";
                    } else if (val == "1") {
                        return "导入";
                    }
                }
            }
        ]]
    });

    $(function () {
        //供应商查询
        $("#journalsheet-widget-supplierquery").widget({
            width: 190,
            name: 't_js_matcostsinput',
            col: 'supplierid',
            singleSelect: true,
            view: true,
            onSelect: function () {
                $("#journalsheet-widget-supplier-empty").prop({checked: false});
            }
        });
        $("#journalsheet-widget-supplier-empty").click(function () {
            if ($(this).prop("checked")) {
                $("#journalsheet-widget-supplierquery").widget("clear");
            } else {
                $("#journalsheet-widget-supplierquery").widget("clear");
            }
        });

        $("#journalsheet-widget-matcostsReimburse-supplierdeptidquery").widget({
            width: 150,
            name: 't_js_matcostsinput',
            col: 'supplierdeptid',
            singleSelect: true
        });
        //收回科目
        $("#matcostsReimburseList-widget-shsubjectid").widget({
            referwid: 'RL_T_BASE_SUBJECT',
            width: 220,
            singleSelect: true,
            onlyLeafCheck: false,
            treePName: false,
            treeNodeDataUseNocheck: true,
            param: [
                {field: 'typecode', op: 'equal', value: 'DDREIMBURSE_SUBJECT'}
            ]
        });

        //回车事件
        controlQueryAndResetByKey("matcostsReimburse-query-List", "matcostsReimburse-query-reloadList");

        //查询
        $("#matcostsReimburse-query-List").click(function () {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#matcostsReimburse-form-ListQuery").serializeJSON();
            //调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
            $("#journalsheet-table-matcostsReimburse").datagrid({
                url: 'journalsheet/matcostsInput/getMatcostsReimbursePageList.do',
                pageNumber: 1,
                queryParams: queryJSON
            }).datagrid("columnMoving");
        });

        //重置按钮
        $("#matcostsReimburse-query-reloadList").click(function () {
            $("#matcostsReimburse-form-ListQuery")[0].reset();
            $("#journalsheet-table-matcostsReimburse").datagrid('loadData', {total: 0, rows: []});
            $("#journalsheet-widget-supplierquery").widget('clear');
        });

        $("#journalsheet-button-matcostsReimburse").buttonWidget({
            //初始默认按钮 根据type对应按钮事件
            initButton: [
                {},
                {
                    type: 'button-commonquery',
                    attr: {
                        //查询针对的表
                        name: 't_js_matcostsinput',
                        //查询针对的表格id
                        datagrid: 'journalsheet-table-matcostsReimburse'
                    }
                }
            ],
            buttons: [
                <security:authorize url="/journalsheet/matcostsInput/matcostsReimburseAddBtn.do">
                {
                    id: 'button-id-add',
                    name: '新增 ',
                    iconCls: 'button-add',
                    handler: function () {
                        refreshLayout('代垫收回【新增】', 'journalsheet/matcostsInput/showMatcostsReimburseAddPage.do');
                    }
                },
                </security:authorize>
                <security:authorize url="/journalsheet/matcostsInput/matcostsReimburseEditBtn.do">
                {
                    id: 'button-id-edit',
                    name: '修改 ',
                    iconCls: 'button-edit',
                    handler: function () {
                        var matcostsReimburse = $("#journalsheet-table-matcostsReimburse").datagrid('getSelected');
                        if (matcostsReimburse == null) {
                            $.messager.alert("提醒", "请选择相应的代垫收回!");
                            return false;
                        }
                        /*
                         var flag = isDoLockData(matcostsReimburse.id,"t_js_matcostsinput");
                         if(!flag){
                         $.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
                         return false;
                         }
                         */
                        refreshLayout("代垫收回【修改】", 'journalsheet/matcostsInput/showMatcostsReimburseEditPage.do?id=' + matcostsReimburse.id);
                    }
                },
                </security:authorize>
                <security:authorize url="/journalsheet/matcostsInput/matcostsReimburseDelBtn.do">
                {
                    id: 'button-id-delete',
                    name: '删除',
                    iconCls: 'button-delete',
                    handler: function () {
                        var rows = $("#journalsheet-table-matcostsReimburse").datagrid('getChecked');
                        if (rows == null || rows.length == 0) {
                            $.messager.alert("提醒", "请选择相应的代垫收回!");
                            return false;
                        }
                        var idarrs = new Array();
                        var errorIdarr = new Array();
                        if (null != rows && rows.length > 0) {
                            for (var i = 0; i < rows.length; i++) {
                                if (rows[i].id && rows[i].id != "") {
                                    idarrs.push(rows[i].id);
                                }
                                if (rows[i].oaid) {
                                    if (rows[i].iswriteoff == '1') {
                                        errorIdarr.push(rows[i].oaid);
                                    }
                                }
                            }
                        }
                        if (errorIdarr.length > 0) {
                            $.messager.alert("提醒", "已核销的代垫不能删除，下列代垫已经核销：" + errorIdarr.join(","));
                            return false;
                        }
                        $.messager.confirm("提醒", "是否确认删除代垫收回?", function (r) {
                            if (r) {
                                loading();
                                $.ajax({
                                    type: 'post',
                                    cache: false,
                                    url: 'journalsheet/matcostsInput/deleteMatcostsReimburseMore.do',
                                    data: {idarrs: idarrs.join(",")},
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag == true) {
                                            $.messager.alert("提醒", "删除成功数：" + json.isuccess + "<br />删除失败数：" + json.ifailure);
                                            $("#journalsheet-table-matcostsReimburse").datagrid('reload');
                                            $("#journalsheet-table-matcostsReimburse").datagrid('clearSelections');
                                        }
                                        else {
                                            $.messager.alert("提醒", "删除失败");
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/journalsheet/matcostsInput/matcostsReimburseWriteoffBtn.do">
                {
                    id: 'button-id-writeoff',
                    name: '代垫核销 ',
                    iconCls: 'button-audit',
                    handler: function () {

                        var rows = $("#journalsheet-table-matcostsReimburse").datagrid('getChecked');
                        var supplierid = "";
                        var isgo = true;
                        reimburseWriteoffIdarr = new Array();
                        reimburseWriteoffQueryPageJsonForm = null;
                        if (rows != null && rows.length > 0) {
                            for (var i = 0; i < rows.length; i++) {
                                if (rows[i].iswriteoff == '1') {
                                    continue;
                                }
                                if (supplierid == "") {
                                    supplierid = rows[i].supplierid;
                                }
                                if (supplierid == "") {
                                    supplierid = rows[i].supplierid;
                                }
                                if (supplierid != rows[i].supplierid) {
                                    isgo = false;
                                    break;
                                }
                                reimburseWriteoffIdarr.push(rows[i].id);
                            }
                        }
                        if (!isgo) {
                            $.messager.alert("提醒", "代垫核销时，请选择相同的供应商的数据!");
                            return false;
                        }
                        var reqparams = {};
                        reqparams.supplierid = supplierid;

                        var tmpv = $("#matcostsReimburse-date-begintime").val();
                        reqparams.begintime = tmpv;
                        tmpv = $("#matcostsReimburse-date-endtime").val();
                        reqparams.endtime = tmpv;
                        $('<div id="matcostsReimburseWriteoffQuery-dialog-operate-content"></div>').appendTo('#matcostsReimburseWriteoffQuery-dialog-operate');
                        $('#matcostsReimburseWriteoffQuery-dialog-operate-content').dialog({
                            title: '代垫收回核销：第一步、选择收回',
                            //width: 680,
                            //height: 300,
                            fit: true,
                            closed: true,
                            cache: false,
                            method: 'post',
                            queryParams: reqparams,
                            href: 'journalsheet/matcostsInput/matcostsReimburseWriteoffQueryPage.do',
                            maximizable: true,
                            resizable: true,
                            modal: true,
                            onLoad: function () {

                                reimburseWriteoffQueryPageJsonForm = $("#journalsheet-form-matcostsReimburseWriteoffQuery").serializeJSON();

                                createReimburseWriteoffQueryDataGrid();
                            },
                            onClose: function () {
                                $('#matcostsReimburseWriteoffQuery-dialog-operate-content').dialog('destroy');
                            }
                        });
                        $('#matcostsReimburseWriteoffQuery-dialog-operate-content').dialog('open');
                    }
                },
                </security:authorize>
                <security:authorize url="/journalsheet/matcostsInput/matcostsReimburseTypeChangePageBtn.do">
                {
                    id: 'button-id-typechage',
                    name: '收回方式变更',
                    iconCls: 'button-oppaudit',
                    handler: function () {
                        var dataRow = $("#journalsheet-table-matcostsReimburse").datagrid('getSelected');
                        if (dataRow == null) {
                            $.messager.alert("提醒", "请选择相应的代垫收回!");
                            return false;
                        }
                        if (dataRow.iswriteoff == '0') {
                            $.messager.alert("提醒", "未核销的收回，请直接修改收回!");
                            return false;
                        }
                        $('#matcostsReimburseTypeChange-dialog-operate').dialog({
                            title: '收回' + dataRow.id + ' 收回方式变更',
                            //width: 680,
                            //height: 300,
                            fit: true,
                            closed: true,
                            cache: false,
                            href: 'journalsheet/matcostsInput/matcostsReimburseTypeChangePage.do?id=' + dataRow.id,
                            maximizable: true,
                            resizable: true,
                            modal: true,
                            onLoad: function () {
                            }
                        });
                        $('#matcostsReimburseTypeChange-dialog-operate').dialog('open');
                    }
                },
                </security:authorize>
                <security:authorize url="/journalsheet/matcostsInput/matcostsReimburseImportBtn.do">
                {
                    id: 'button-import-excel',
                    name: '导入',
                    iconCls: 'button-import',
                    handler: function () {
                        $("#matcostsReimburse-buttons-importclick").Excel('import', {
                            type: 'importUserdefined',
                            url: 'journalsheet/matcostsInput/importMatcostsReimburseListData.do',
                            onClose: function () { //导入成功后窗口关闭时操作，
                                var queryJSON = $("#matcostsReimburse-form-ListQuery").serializeJSON();
                                $("#journalsheet-table-matcostsReimburse").datagrid("load", queryJSON);
                            }
                        });
                        $("#matcostsReimburse-buttons-importclick").trigger("click");
                    }
                },
                </security:authorize>
                <security:authorize url="/journalsheet/matcostsInput/matcostsReimburseExportBtn.do">
                {
                    id: 'button-export-excel',
                    name: '导出',
                    iconCls: 'button-export',
                    handler: function () {
                        var rows = $("#journalsheet-table-matcostsReimburse").datagrid('getChecked');

                        //查询参数直接添加在url中
                        var idarrs = new Array();
                        if (null != rows && rows.length > 0) {
                            for (var i = 0; i < rows.length; i++) {
                                if (rows[i].id && rows[i].id != "") {
                                    idarrs.push(rows[i].id);
                                }
                            }
                        }
                        $("#matcostsReimburse-buttons-exportclick").Excel('export', {
                            queryForm: "#matcostsReimburse-form-ListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                            type: 'exportUserdefined',
                            name: '代垫收回列表',
                            fieldParam: {idarrs: idarrs.join(",")},
                            url: 'journalsheet/matcostsInput/exportMatcostsReimburseData.do'
                        });
                        $("#matcostsReimburse-buttons-exportclick").trigger("click");
                    }
                },
                </security:authorize>
                <security:authorize url="/journalsheet/matcostsInput/matcostsReimbursePrintViewBtn.do">
                {
                    id: 'button-printview-order',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                        //orderPrintViewFunc();
                    }
                },
                </security:authorize>
                <security:authorize url="/journalsheet/matcostsInput/matcostsReimbursePrintBtn.do">
                {
                    id: 'button-print-order',
                    name: '打印',
                    iconCls: 'button-print',
                    handler: function () {
                        //orderPrintFunc();
                    }
                },
                </security:authorize>
                {}
            ],
            model: 'bill',
            type: 'list',
            datagrid: 'journalsheet-table-matcostsReimburse',
            tname: 't_js_matcostsinput',
            id: ''
        });

        $("#journalsheet-table-matcostsReimburse").datagrid({
            authority: matcostsReimburseListColJson,
            frozenColumns: matcostsReimburseListColJson.frozen,
            columns: matcostsReimburseListColJson.common,
            fit: true,
            method: 'post',
            showFooter: true,
            rownumbers: true,
            sortName: 'businessdate',
            sortOrder: 'desc',
            pagination: true,
            idField: 'id',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            pageSize: 100,
            toolbar: '#journalsheet-table-matcostsReimburseBtn',
            onSelect: function (rowIndex, rowData) {
                if (rowData.iswriteoff == "1" || rowData.iswriteoff == "2") {
                    $("#journalsheet-button-matcostsReimburse").buttonWidget("disableButton", 'button-id-edit');
                    $("#journalsheet-button-matcostsReimburse").buttonWidget("disableButton", 'button-id-delete');
                } else {
                    $("#journalsheet-button-matcostsReimburse").buttonWidget("enableButton", 'button-id-edit');
                    $("#journalsheet-button-matcostsReimburse").buttonWidget("enableButton", 'button-id-delete');

                }
            },
            onDblClickRow: function (rowIndex, rowData) {
                <%if(isEdit){%>
                if (rowData.iswriteoff != '1' && rowData.iswriteoff != '2') {
                    refreshLayout("代垫收回【修改】", 'journalsheet/matcostsInput/showMatcostsReimburseEditPage.do?id=' + rowData.id);
                } else {
                    refreshLayout("代垫收回【详情】", 'journalsheet/matcostsInput/showMatcostsReimburseViewPage.do?id=' + rowData.id);
                }
                <% }else { %>
                refreshLayout("代垫收回【详情】", 'journalsheet/matcostsInput/showMatcostsReimburseViewPage.do?id=' + rowData.id);
                <%}%>
            },
            onLoadSuccess: function () {
                var footerrows = $(this).datagrid('getFooterRows');
                if (null != footerrows && footerrows.length > 0) {
                    footerobject = footerrows[0];
                }
            },
            onCheckAll: function () {
                settleCountTotalAmount();
            },
            onUncheckAll: function () {
                settleCountTotalAmount();
            },
            onCheck: function () {
                settleCountTotalAmount();
            },
            onUncheck: function () {
                settleCountTotalAmount();
            }
        }).datagrid("columnMoving");
    });
    function settleCountTotalAmount() {
        var rows = $("#journalsheet-table-matcostsReimburse").datagrid('getChecked');
        if (null == rows || rows.length == 0) {
            var foot = [];
            if (null != footerobject) {
                foot.push(emptyChooseObjectFoot());
                foot.push(footerobject);
            }
            $("#journalsheet-table-matcostsReimburse").datagrid("reloadFooter", foot);
            return false;
        }

        var reimburseamount = 0;
        var writeoffamount = 0;
        var remainderamount = 0;
        <c:forEach items="${reimbursetypeList }" var="list">
        var reimburse_${list.code} = 0;
        </c:forEach>
        for (var i = 0; i < rows.length; i++) {
            reimburseamount = Number(reimburseamount) + Number(rows[i].reimburseamount == undefined ? 0 : rows[i].reimburseamount);
            writeoffamount = Number(writeoffamount) + Number(rows[i].writeoffamount == undefined ? 0 : rows[i].writeoffamount);
            remainderamount = Number(remainderamount) + Number(rows[i].remainderamount == undefined ? 0 : rows[i].remainderamount);
            <c:forEach items="${reimbursetypeList }" var="list">
            reimburse_${list.code} = Number(reimburse_${list.code}) + Number(rows[i].reimburse_${list.code} == undefined ? 0 : rows[i].reimburse_${list.code});
            </c:forEach>
        }
        var foot = [{
            suppliername: '选中金额', reimburseamount: reimburseamount,
            writeoffamount: writeoffamount, remainderamount: remainderamount
            <c:forEach items="${reimbursetypeList }" var="list">
            , reimburse_${list.code}: reimburse_${list.code}
            </c:forEach>
        }];
        if (null != footerobject) {
            foot.push(footerobject);
        }
        $("#journalsheet-table-matcostsReimburse").datagrid("reloadFooter", foot);
    }
    function emptyChooseObjectFoot() {
        var reimburseamount = 0;
        var writeoffamount = 0;
        var remainderamount = 0;
        <c:forEach items="${reimbursetypeList }" var="list">
        var reimburse_${list.code} = 0;
        </c:forEach>
        var foot = {
            suppliername: '选中金额', reimburseamount: reimburseamount,
            writeoffamount: writeoffamount, remainderamount: remainderamount
            <c:forEach items="${reimbursetypeList }" var="list">
            , reimburse_${list.code}: reimburse_${list.code}
            </c:forEach>
        };
        return foot;
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function(){
        AgReportPrint.init({
            id: "listPage-matreimburse-dialog-print",
            code: "journalsheet_matreimburse",
            //tableId: "journalsheet-table-matcostsReimburse",
            url_preview: "print/journalsheet/matReimbursePrintView.do",
            url_print: "print/journalsheet/matReimbursePrint.do",
            btnPreview: "button-printview-order",
            btnPrint: "button-print-order",
            getData: function(tableId, printParam) {
                var data = $("#journalsheet-table-matcostsReimburse").datagrid("getChecked");
                if (data == null || data.length == 0) {
                    $.messager.alert("提醒", "请选择至少一条记录");
                    return false;
                }

                var idarray = [];
                var errBDArr = [];
                var errSupplierArr = [];
                var errorArr = [];
                var ydprintArr = [];
                var billtime = null;
                var supplier = null;
                for (var i = 0; i < data.length; i++) {
                    var curbtime = "";
                    var cursupplier = "";
                    if (data[i].addtime != null) {
                        var tmpArr = [];
                        if (data[i].addtime.indexOf('T')) {
                            tmpArr = data[i].addtime.split('T');
                        } else if (data[i].addtime.indexOf('t')) {
                            tmpArr = data[i].addtime.split('t');
                        } else {
                            tmpArr = data[i].addtime.split(' ');
                        }
                        if (tmpArr.length > 0) {
                            curbtime = tmpArr[0];
                        }
                    }
                    if (billtime == null) {
                        billtime = curbtime;
                    } else if (billtime != curbtime) {
                        errBDArr.push(data[i].id);
                        continue;
                    }
                    cursupplier = data[i].supplierid || "";
                    if (supplier == null) {
                        supplier = cursupplier;
                    } else if (supplier != cursupplier) {
                        errSupplierArr.push(data[i].id);
                        continue;
                    }
                    idarray.push(data[i].id);
                    if (data[i].shprinttimes > 0) {
                        ydprintArr.push(data[i].id);
                    }
                }
                var msgArr = [];

                if (errorArr.length > 0 || errBDArr.length > 0 || errSupplierArr.length > 0) {
                    msgArr.push("抱歉，系统不能打印。问题如下：<br/>");
                    if (errorArr.length > 0) {
                        msgArr.push("保存状态下不能打印，单据号：");
                        msgArr.push(errorArr.join(","));
                        msgArr.push("<br/>");
                    }
                    if (errBDArr.length > 0) {
                        msgArr.push("开单日期不一致，单据号：");
                        msgArr.push(errBDArr.join(","));
                        msgArr.push("<br/>");
                    }
                    if (errSupplierArr.length > 0) {
                        msgArr.push("供应商不一致，单据号：");
                        msgArr.push(errSupplierArr.join(","));
                        msgArr.push("<br/>");
                    }
                    $.messager.alert("提醒", msgArr.join(''));
                    return false;
                }
                printParam.idarrs = idarray.join(",");
                printParam.printIds = ydprintArr;
                return true;
            },
            onPrintSuccess:function(option) {
                var $grid = $("#journalsheet-table-matcostsReimburse");
                var dataList = $grid.datagrid("getChecked");
                for (var i = 0; i < dataList.length; i++) {
                    if (dataList[i].shprinttimes && !isNaN(dataList[i].shprinttimes)) {
                        dataList[i].shprinttimes = dataList[i].shprinttimes + 1;
                    } else {
                        dataList[i].shprinttimes = 1;
                    }
                    var rowIndex = $grid.datagrid('getRowIndex', dataList[i].id);
                    $grid.datagrid('updateRow', {index: rowIndex, row: dataList[i]});
                }
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
