<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>客户费用合同</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div id="contract-layout-contractPage" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
        <div class="buttonBG" id="contract-buttons-contractPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="contract-panel-contractPage"></div>
    </div>
</div>
<div id="contract-panel-relation-upper"></div>
<div id="contract-panel-sourceQueryPage"></div>
<div id="workflow-addidea-dialog-page"></div>
<input type="hidden" id="contract-hidden-billid"/>
<script type="text/javascript">
    //明细列表
    var tableColJson = $("#contract-datagrid-contractPage").createGridColumnLoad({
        frozenCol: [[]],
        commonCol: [[
            {field: 'ck', checkbox: true},
            {field: 'caluseid', title: '条款编号', width: 150},
            {field: 'costtype', title: '费用类型', width: 80,
                formatter: function (val, rowData, rowIndex) {
                    if ('0'==val) {
                        return "可变费用";
                    }else if('1'==val){
                        return "固定费用";
                    }else{
                        return "";
                    }
                }
            },
            {field: 'sharetype', title: '分摊方式', width: 80,
                formatter: function (val, rowData, rowIndex) {
                    if ('0'==val) {
                        return "一次性分摊";
                    }else if('1'==val){
                        return "分月平摊";
                    }else{
                        return "";
                    }
                }
            },
            {field: 'returntype', title: '支付类型', width: 80,
                formatter: function (val, rowData, rowIndex) {
                    if ('0'==val) {
                        return "月返";
                    }else if('1'==val){
                        return "季返";
                    }else if('2'==val){
                        return "年返";
                    }else{
                        return "";
                    }
                }
            },
            {field: 'returnmonthnum', title: '支付月份', width: 80},
            {field: 'returnrequire', title: '支付要求', width: 80,
                formatter: function (val, rowData, rowIndex) {
                    if ('0'==val) {
                        return "无要求";
                    }else if('1'==val){
                        return "销售达成";
                    }else{
                        return "";
                    }
                }
            },
            {field: 'subjectexpensesname', title: '对应费用科目', width: 80},
            {field: 'subjectexpenses', title: '对应费用科目编号', width: 80,hidden:true},
            {field: 'calculatetype', title: '计算方式', width: 80,
                formatter: function (val, rowData, rowIndex) {
                    if ('0'==val) {
                        return "固定费用";
                    }else if('1'==val){
                        return "百分比计算";
                    }else if('2'==val){
                        return "按门店数计算";
                    }else if('3'==val){
                        return "按新门店数计算";
                    }else if('4'==val){
                        return "按新品数计算";
                    }else{
                        return "";
                    }
                }
            },
            {field: 'calculateamount', title: '计算参数', width: 80,
                formatter: function (val, rowData, rowIndex) {
                    if ('1'==rowData.calculatetype) {
                        return val+"%";
                    }else{
                        return val;
                    }
                }},
            {field: 'remark', title: '备注', width: 80},
        ]]
    });//明细列表至此
    var page_url = "journalsheet/contract/showContractAddPage.do";
    var page_type = '${type}';
    if (page_type == "edit") {
        page_url = "journalsheet/contract/showContractEditPage.do?id=${id}";
    }else if (page_type == "view") {
        page_url = "journalsheet/contract/showContractViewPage.do?id=${id}";
    }
    $(function () {
        $("#contract-panel-contractPage").panel({
            href: page_url,
            cache: false,
            maximized: true,
            border: false,
            onLoad: function () {
                if (page_type == "edit") {
//                    $("#contract-contract-storageid").focus();
                }
                else {
//                    $("#contract-contract-supplierid").focus();
                }
            }
        });
        $("#contract-buttons-contractPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/contract/addDeliveryAogorderPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        page_type = "add";
                        $("#contract-panel-contractPage").panel('refresh', 'journalsheet/contract/showContractAddPage.do');
                    }
                },
                </security:authorize>
                <security:authorize url="/contract/addDeliveryAogorderSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        var messageStr = "确定保存该客户费用合同信息?";
                        if (checkContractDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，请填写客户费用合同条款");
                            return false;
                        }
                        var customerid =  $("#contract-customerid-contractPage").widget("getValue");
                        if("" == customerid || null == customerid){
                            $.messager.alert("提醒", "请先选择客户！");
                        }
                        $.ajax({
                            url: 'journalsheet/contract/checkCorrelationCustomer.do?customerid=' + customerid,
                            type: 'post',
                            dataType: 'json',
                            success: function (json) {
                                if(json.havestoredate || json.haveheadstoredata){
                                    var customertype = $("#contract-customertype-select-contractPage").val();
                                    if("0" == customertype){
                                        messageStr = "已存在所属门店的数据!确定保存该客户费用合同信息?";
                                    }else{
                                        messageStr = "已存在总店的数据!确定保存该客户费用合同信息?";
                                    }
                                }
                                $.messager.confirm("提醒", messageStr, function (r) {
                                    if (r) {
                                        $("#contract-saveaudit-contractPage").val("save");
                                        if (page_type == "add") {
                                            //暂存
                                            $("#contract-form-contractPage").attr("action", "journalsheet/contract/addContractSave.do");
                                            $("#contract-form-contractPage").submit();
                                        } else{
                                            $("#contract-form-contractPage").attr("action", "journalsheet/contract/editContractSave.do");
                                            $("#contract-form-contractPage").submit();
                                        }
                                    }
                                });
                            },
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/delivery/addDeliveryAogorderSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        var messageStr = "确定保存并审核该客户费用合同信息?";
                        if (checkContractDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，请填写客户费用合同条款");
                            return false;
                        }
                        var customerid =  $("#contract-customerid-contractPage").widget("getValue");
                        if("" == customerid || null == customerid){
                            $.messager.alert("提醒", "请先选择客户！");
                        }
                        $.ajax({
                            url: 'journalsheet/contract/checkCorrelationCustomer.do?customerid=' + customerid,
                            type: 'post',
                            dataType: 'json',
                            success: function (json) {
                                if(json.havestoredate || json.haveheadstoredata){
                                    var customertype = $("#contract-customertype-select-contractPage").val();
                                    if("0" == customertype){
                                        messageStr = "已存在所属门店的数据!确定保存并审核该客户费用合同信息?";
                                    }else{
                                        messageStr = "已存在总店的数据!确定保存并审核该客户费用合同信息?";
                                    }
                                }
                                $.messager.confirm("提醒",messageStr, function (r) {
                                    if (r) {
                                        $("#contract-saveaudit-contractPage").val("saveaudit");
                                        if (page_type == "add") {
                                            //暂存
                                            $("#contract-form-contractPage").attr("action", "journalsheet/contract/addContractSave.do");
                                            $("#contract-form-contractPage").submit();
                                        } else{
                                            $("#contract-form-contractPage").attr("action", "journalsheet/contract/editContractSave.do");
                                            $("#contract-form-contractPage").submit();
                                        }
                                    }
                                });
                            },
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/delivery/deleteDeliveryAogorder.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        $.messager.confirm("提醒", "是否删除当前费用合同？", function (r) {
                            if (r) {
                                var id = $("#contract-id-contractPage").val();
                                if (id != "") {
                                    loading("删除中..");
                                    $.ajax({
                                        url: 'journalsheet/contract/deleteContract.do?ids=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if(json.flag){
                                                $.messager.alert("提醒", "删除成功!");
                                                var data = $("#contract-buttons-contractPage").buttonWidget("removeData", '');
                                                if (data != null)
                                                    $("#contract-panel-contractPage").panel('refresh', 'journalsheet/contract/showContractEditPage.do?id=' + data.id);
                                                else {
                                                    parent.closeNowTab();
                                                }
                                            }else{
                                                $.messager.alert("提醒", json.msg);
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "删除出错");
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/delivery/auditDeliveryAogorder.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        $.messager.confirm("提醒", "是否审核客户费用合同？", function (r) {
                            if (r) {
                                var id = $("#contract-id-contractPage").val();
                                if (id != "") {
                                    loading("审核中..");
                                    $.ajax({
                                        url: 'journalsheet/contract/auditContract.do',
                                        data: {ids: id},
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if(json.flag){
                                                $.messager.alert("提醒", "审核成功!");
                                                page_type="view";
                                                $("#contract-panel-contractPage").panel('refresh', 'journalsheet/contract/showContractViewPage.do?id=' + id);
                                            }else{
                                                $.messager.alert("提醒", json.msg);
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "审核失败");
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/delivery/oppauditDeliveryAogorder.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        $.messager.confirm("提醒", "是否反审客户费用合同？", function (r) {
                            if (r) {
                                var id = $("#contract-id-contractPage").val();
                                if (id != "") {
                                    loading("反审中..");
                                    $.ajax({
                                        url: 'journalsheet/contract/oppauditContract.do?ids=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if(json.flag){
                                                $.messager.alert("提醒", "反审成功!");
                                                page_type="edit";
                                                $("#contract-panel-contractPage").panel('refresh', 'journalsheet/contract/showContractEditPage.do?id=' + id);
                                            }else{
                                                $.messager.alert("提醒", json.msg);
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
                    }
                },
                </security:authorize>
                {
                    type: 'button-back',
                    handler: function (data) {
                        $("#contract-panel-contractPage").panel('refresh', 'journalsheet/contract/showContractEditPage.do?id=' + data.id);
                    }
                },
                {
                    type: 'button-next',
                    handler: function (data) {
                        $("#contract-panel-contractPage").panel('refresh', 'journalsheet/contract/showContractEditPage.do?id=' + data.id);
                    }
                },
            ],
            layoutid: 'contract-layout-contractPage',
            model: 'bill',
            type: 'view',
            tab: '客户费用合同详情',
            taburl: '/journalsheet/contract/showContractListPage.do',
            id: '${id}',
            datagrid: 'contract-table-contractListPage'
        });
    });

    function checkContractDetailEmpty() {
        var flag = true;
        var $table = $("#contract-datagrid-contractPage");
        var data = $table.datagrid('getRows');
        for (var i = 0; i < data.length; i++) {
            if (data[i].caluseid && data[i].caluseid != "") {
                flag = false;
                break;
            }
        }
        return flag;
    }
    //显示客户费用合同明细添加页面
    function beginAddDetail() {
//        //验证表单
//        var flag = $("#contract-form-contractPage").form('validate');
//        if (flag == false) {
//            $.messager.alert("提醒", '请先完善客户费用合同基本信息');
//            return false;
//        }
        var supplierid = $("#contract-contractAogorder-supplierid").widget("getValue");
        var storageid = $("#contract-contractAogorder-storageid").widget("getValue");
        $('<div id="contract-dialog-contractPage-content"></div>').appendTo('#contract-dialog-contractPage');
        $('#contract-dialog-contractPage-content').dialog({
            title: '客户费用合同明细添加',
            width: 680,
            height: 320,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            modal: true,
            href: 'journalsheet/contract/showContractDetailAddPage.do',
            onLoad: function () {
                $("#contract-caluseid-contractDetailPage").focus();
            },
            onClose: function () {
                $('#contract-dialog-contractPage-content').dialog("destroy");
            }
        });
        $('#contract-dialog-contractPage-content').dialog("open");
    }
    //显示客户费用合同明细修改页面
    function beginEditDetail() {
//        //验证表单
//        var flag = $("#contract-form-contractPage").form('validate');
//        if (flag == false) {
//            $.messager.alert("提醒", '请先完善费用合同');
//            return false;
//        }
        var row = $("#contract-datagrid-contractPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if (row.caluseid == undefined) {
            beginAddDetail();
        } else {
            $('<div id="contract-dialog-contractPage-content"></div>').appendTo('#contract-dialog-contractPage');
            $('#contract-dialog-contractPage-content').dialog({
                title: '客户费用合同明细修改',
                width: 680,
                height: 320,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                resizable: true,
                closed: true,
                cache: false,
                href: 'journalsheet/contract/showContractDetailEditPage.do',
                modal: true,
                onLoad: function () {
                    $("#contract-form-contractDetailPage").form('load', row);
                    $("#contract-caluseid-contractDetailPage").widget("setValue", row.caluseid);
                    $("#contract-subjectexpenses-contractDetailPage").widget("setValue", row.subjectexpenses);
                    $("#contract-costtype-contractDetailPage").focus();
                    $("#contract-costtype-contractDetailPage").select();
                },
                onClose: function () {
                    $('#contract-dialog-contractPage-content').dialog("destroy");
                }
            });
            $('#contract-dialog-contractPage-content').dialog("open");
        }
    }
    //保存客户费用合同明细
    function addSaveDetail(goFlag) { //添加新数据确定后操作，
        var flag = $("#contract-form-contractDetailPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#contract-form-contractDetailPage").serializeJSON();
        var rowIndex = 0;
        var rows = $("#contract-datagrid-contractPage").datagrid('getRows');
        var updateFlag = false;
        for (var i = 0; i < rows.length; i++) {
            var rowJson = rows[i];
 			if(rowJson.caluseid==form.caluseid){
 				rowIndex = i;
 				updateFlag = true;
 				break;
 			}
            if (rowJson.caluseid == undefined) {
                rowIndex = i;
                break;
            }
        }
        if (rowIndex == 0) {
            $("#contract-contractAogorder-storageid").widget('readonly1', true);
        }
        if (rowIndex == rows.length - 1) {
            $("#contract-datagrid-contractPage").datagrid('appendRow', {}); //如果是最后一行则添加一新行
        }
 		if(updateFlag){
 			$.messager.alert("提醒", "此条款已经添加！");
 			return false;
 		}else{
        $("#contract-datagrid-contractPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
 		}
        if (goFlag) { //go为true确定并继续添加一条
            var url = 'journalsheet/contract/showContractDetailAddPage.do';
            $("#contract-dialog-contractPage-content").dialog('refresh', url);
        }
        else { //否则直接关闭
            $("#contract-dialog-contractPage-content").dialog('destroy');
        }
    }
    //修改保存客户费用合同明细
    function editSaveDetail(goFlag) {
        var flag = $("#contract-form-contractDetailPage").form('validate');
        if (flag == false) {
            return false;
        }
        var  subjectexpenses = $("#contract-subjectexpenses-contractDetailPage").widget("getValue");
        if("" == subjectexpenses){
            $.messager.alert("提醒", "请选择费用科目!");
            return false;
        }
        var form = $("#contract-form-contractDetailPage").serializeJSON();
        var row = $("#contract-datagrid-contractPage").datagrid('getSelected');
        var rowIndex = $("#contract-datagrid-contractPage").datagrid('getRowIndex', row);
        $("#contract-datagrid-contractPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        $("#contract-dialog-contractPage-content").dialog('destroy');
    }
    //删除客户费用合同明细
    function removeDetail() {
        var row = $("#contract-datagrid-contractPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $.messager.confirm("提醒", "确定删除该合同明细?", function (r) {
            if (r) {
                var rowIndex = $("#contract-datagrid-contractPage").datagrid('getRowIndex', row);
                $("#contract-datagrid-contractPage").datagrid('deleteRow', rowIndex);
                var rows = $("#contract-datagrid-contractPage").datagrid('getRows');
                var index = -1;
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].goodsid != undefined) {
                        index = i;
                        break;
                    }
                }
            }
        });
    }
    function  checkCorrelationCustomer() {

    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "contract_aogorder-dialog-print",
            code: "contract_aogorder",
            url_preview: "print/contract/aogorderPrintView.do",
            url_print: "print/contract/aogorderPrint.do",
            btnPreview: "button-printview-aogorder",
            btnPrint: "button-print-aogorder",
            getData: function (tableId, printParam) {
                var id = $("#contract-contract-id").val();
                if (id == "") {
                    $.messager.alert("警告", "找不到要打印的信息!");
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