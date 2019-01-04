<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>客户应收款页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="account-buttons-customerPushBanlance"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="account-panel-customerPushBanlance">
        </div>
    </div>
</div>
<%--<input type="hidden" id="sales-backid-prepayOrderPage" value="${param.id }" />--%>
<div class="easyui-menu" id="account-menu-customerPushBanlance" style="display:  none;">
    <div id="account-addRow-customerPushBanlance" data-options="iconCls:'button-add'" onclick="javascript:showCustomerPushBanlanceAddPage();">添加</div>
    <div id="account-removeRow-customerPushBanlance" data-options="iconCls:'button-delete'" onclick="javascript:deleteDetailRow();">删除</div>
</div>

<script type="text/javascript">

    var type = '${type }' || '${param.type }';
    var id = '${param.id }';
    var url = 'account/receivable/showCustomerPushBanlancePage.do';
    if(type == 'add') {
        url = 'account/receivable/showCustomerPushBanlanceMoreAddPage.do';
    }

    var tableColJson = $("#account-datagrid-customerPushBanlance").createGridColumnLoad({
        frozenCol: [[

        ]],
        commonCol: [[
            {field: 'pushtype', title: '冲差类型编码', width: 70, align: ' left',hidden:true},
            {field: 'pushtypename', title: '冲差类型', width: 70, align: ' left'},
            {field: 'subject', title: '费用科目编码', width: 100, align: 'left',hidden:true},
            {field: 'subjectname', title: '费用科目', width: 100, align: 'left'},
            {field: 'brandid', title: '商品品牌编码', width: 100, align: 'left',hidden:true},
            {field: 'brandname', title: '商品品牌', width: 100, align: 'left'},
            {field: 'defaulttaxtype', title: '默认税种', width: 100, align: 'left',hidden:true},
            {field: 'defaulttaxtypename', title: '默认税种', width: 100, align: 'left'},
            {field: 'amount', title: '冲差金额', width: 80, align: 'right'},
            {field: 'notaxamount', title: '冲差未税金额', width: 80, align: 'right'},
            {field: 'tax', title: '冲差税额', width: 80, align: 'right'},
            {field: 'remark', title: '备注', width: 120, align: 'left'}
        ]]
    });

    $(function () {
        $("#account-panel-customerPushBanlance").panel({
            href: url,
            cache: false,
            maximized: true,
            border: false,
            onLoad: function () {

            }
        });

        //按钮
        $("#account-buttons-customerPushBanlance").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/account/receivable/save.do">
                {
                    type: 'button-save',
                    handler: function () {
                        saveCustomerPushBanlace();
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/saveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        $saveaudit.val('1');
                        saveCustomerPushBanlace();
                    }
                },
                </security:authorize>
                {}
            ],
//            layoutid: 'sales-orderPage-layout',
            model: 'bill',
            type: 'add',
            tab: '客户应收款冲差',
//            taburl: '/sales/orderListPage.do',
            <%--id: '${id}',--%>
//            datagrid: 'sales-datagrid-orderListPage'
        });


    });
    function showCustomerPushBanlanceAddPage(){
        var customerid=$("#account-customerPushBanlance-customerid").val();
        if(customerid=='' || customerid==undefined){
            $.messager.alert("提醒", "请选择客户");
            return false;
        }
        $('<div id="account-dialog-customerPushBanlanceAddPage-content"></div>').appendTo('#account-dialog-customerPushBanlanceAddPage');
        $("#account-dialog-customerPushBanlanceAddPage-content").dialog({ //弹出新添加窗口
            title: '客户应收款冲差新增',
            width: 400,
            height: 300,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            href: 'account/receivable/showCustomerPushBanlanceDetailAddPage.do',
            onClose: function () {
                $('#account-dialog-customerPushBanlanceAddPage-content').dialog("destroy");
            },
            onLoad: function () {
//                $("#sales-goodsId-orderDetailAddPage").focus();
            }
        });
        $("#account-dialog-customerPushBanlanceAddPage-content").dialog("open");

        return null;
    }
    function showCustomerPushBanlanceEditPage(rowData){
        $('<div id="account-dialog-customerPushBanlanceAddPage-content"></div>').appendTo('#account-dialog-customerPushBanlanceAddPage');
        $("#account-dialog-customerPushBanlanceAddPage-content").dialog({ //弹出新添加窗口
            title: '客户应收款冲差修改',
            width: 400,
            height: 300,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            queryParams: {subject: rowData.subject, brandid: rowData.brandid, defaulttaxtype: rowData.defaulttaxtype,pushtype:rowData.pushtype},
            href: 'account/receivable/showCustomerPushBanlanceDetailEditPage.do',
            onClose: function () {
                $('#account-dialog-customerPushBanlanceAddPage-content').dialog("destroy");
            },
            onLoad: function () {
                $("#account-form-customerPushBanlance-add").form('load', rowData);
            }
        });
        $("#account-dialog-customerPushBanlanceAddPage-content").dialog("open");

        return null;
    }
    /**
     * 删除明细
     * @returns {boolean}
     * @author limin
     * @date Jan 6, 2016
     */
    function deleteDetailRow() {
        $.messager.confirm('提醒', '确定要删除该条记录？', function (c) {

            if(c) {

                var row = $datagrid.datagrid('getSelected');
                if(row == null) {
                    return true;
                }

                var index = $datagrid.datagrid('getRowIndex', row);
                $datagrid.datagrid('deleteRow', index);
                refreshWidgetReadonly();
            }
        });

        return true;
    }
    function addCustomerPushBanlace(go){
        var flag = $("#account-form-customerPushBanlance-add").form('validate');
        if(flag==false){
            return false;
        }
        $("#account-customerPushBanlance-pushtypename").val($("#account-customerPushBanlance-pushtype").find("option:selected").text())
        var form = $("#account-form-customerPushBanlance-add").serializeJSON();
        var rowIndex = 0;
        var rows = $datagrid.datagrid('getRows');
        for(var i=0; i<rows.length; i++){
            if(rows[i].brandid== undefined){
                rowIndex=i;
                break;
            }
        }
        if(rowIndex == rows.length - 1){
            $datagrid.datagrid('appendRow',{}); //如果是最后一行则添加一新行
        }

        $("#account-datagrid-customerPushBanlance").datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
        refreshWidgetReadonly();
        if(go){ //go为true确定并继续添加一条
            formOperate(go);
        }
        else{ //否则直接关闭
            $("#account-dialog-customerPushBanlanceAddPage-content").dialog('close', true)
        }

    }
    function editCustomerPushBanlace(){
        var flag = $("#account-form-customerPushBanlance-add").form('validate');
        if(flag==false){
            return false;
        }
        $("#account-customerPushBanlance-pushtypename").val($("#account-customerPushBanlance-pushtype").find("option:selected").text());
        var form = $("#account-form-customerPushBanlance-add").serializeJSON();
        var row = $datagrid.datagrid('getSelected');
        var rowIndex = $datagrid.datagrid('getRowIndex', row);
        $datagrid.datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
        $("#account-dialog-customerPushBanlanceAddPage-content").dialog('close', true);
        refreshWidgetReadonly();
    }
    /**
     *
     */
    function formOperate(go) {
        //go为true确定并继续添加一条
        if(go){
            $("#account-form-customerPushBanlance-add").form("clear");
            //否则直接关闭
        } else {
            $("#account-dialog-customerPushBanlanceAddPage-content").dialog('close', true)
        }
        return true;
    }
    /**
     * 明细中有商品时，客户、配送中心、二级代理设为readonly状态
     */
    function refreshWidgetReadonly() {

        var rows = $datagrid.datagrid('getRows');
        var exists = false;
        for(var i in rows) {
            if(rows[i].brandid) {
                exists = true;
                break;
            }
        }
        if(exists) {

            $("#account-customerPushBanlance-customerid").customerWidget('readonly', true);
            $("#account-customerPushBanlance-deptid").widget('readonly', true);
            $("#account-customerPushBanlance-salesuser").widget('readonly', true);

        } else {
            $("#account-customerPushBanlance-customerid").customerWidget('readonly', false);
            $("#account-customerPushBanlance-deptid").widget('readonly', false);
            $("#account-customerPushBanlance-salesuser").widget('readonly', false);
        }
        return true;
    }
    //保存应收款冲差
    function saveCustomerPushBanlace() {

        var $form=$("#account-form-customerPushBanlance");

        var saveaudit = $saveaudit.val() == '1';
        $form.form({
            onSubmit: function(param) {

                var flag = $form.form('validate');

                if(!flag) {
                    return false;
                }

                var rows = $datagrid.datagrid('getRows');
                var exists = false;
                for(var i in rows) {
                    if(rows[i].brandid) {
                        exists = true;
                        break;
                    }
                }

                if(!exists) {
                    $.messager.alert('提醒', '请至少添加一条记录');
                    return false;
                }

                $detailStr.val(JSON.stringify(rows));

                loading("提交中...");
            },
            success: function(data) {

                loaded();
                // 保存成功
                $.messager.alert('提醒', saveaudit ? '保存并审核成功。' : '保存成功。');
                top.$("#tt").tabs('close', '客户应收款冲差批量新增');
            }
        }).submit();

        return true;
    }

    var customerPushBanlance_AjaxConn = function (Data, Action) {
        var MyAjax = $.ajax({
            type: 'post',
            cache: false,
            url: Action,
            data: Data,
            async: false
        })
        return MyAjax.responseText;
    }
    //根据数据冲差金额获取其未税金额、税额
    function getPushBanlanceNoTaxAmount() {
        var amount = $("#account-customerPushBanlance-amount").numberbox('getValue');
        var defaulttaxtype = $("#account-customerPushBanlance-defaulttaxtype").widget('getValue');
        var ret = customerPushBanlance_AjaxConn({
            amount: amount,
            defaulttaxtype: defaulttaxtype
        }, 'account/receivable/getPushBanlanceNoTaxAmount.do');
        var retjson = $.parseJSON(ret);
        $("#account-customerPushBanlance-notaxamount").numberbox('setValue', retjson.notaxamount);
        $("#account-customerPushBanlance-tax").numberbox('setValue', retjson.tax);
    }

</script>
</body>
</html>
