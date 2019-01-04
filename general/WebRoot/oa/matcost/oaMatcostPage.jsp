<%--
  Created by IntelliJ IDEA.
  User: limin
  Date: 2018/2/12
  Time: 13:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>代垫费用申请单页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<style type="text/css">
    .len300 {
        width: 300px;
    }
    .len308 {
        width: 308px;
    }
    .len350 {
        width: 350px;
    }
</style>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <div id="oa-panel-oaMatcostPage">
        </div>
    </div>
</div>

<div class="easyui-menu" id="oa-contextMenu-oaMatcostPage">
    <div id="oa-addRow-oaMatcostPage" data-options="iconCls:'button-add'">添加</div>
    <div id="oa-editRow-oaMatcostPage" data-options="iconCls:'button-edit'">修改</div>
    <div id="oa-removeRow-oaMatcostPage" data-options="iconCls:'button-delete'">删除</div>
</div>
<div id="oa-dialog-oaMatcostPage"></div>
<script type="text/javascript">
    <!--

    var customerMap = {};
    var brandMap = {};
    var expensesortMap = {};

    var url = '';
    var type = '${param.type }';
    var id = '${param.id }';
    var step = '${param.step }';
    var from = '${param.from }';

    if(type == 'handle') {

        if(step == '99') {
            url = 'oa/matcost/oaMatcostViewPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }';
        } else if(step == '02') {

            url = 'oa/matcost/oaMatcostHandlePage2.do?id=${param.id }&step=${param.step }&type=handle&processid=${param.processid }';
        } else {

            url = 'oa/matcost/oaMatcostHandlePage1.do?id=${param.id }&step=${param.step }&type=handle&processid=${param.processid }';
        }
    } else if(type == 'view') {

        url = 'oa/matcost/oaMatcostViewPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }';
    } else if(type == 'print') {

        url = 'oa/matcost/oaMatcostPrintPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }';
        window.location.href = url;
    }

    $(function() {

        $('#oa-panel-oaMatcostPage').panel({
            href: url,
            cache: false,
            maximized: true,
            border: false,
            onLoad: function() {

                // 附件
                $attach.attach({
                    attach: true<c:if test="${param.type eq 'view' }"> && false</c:if>,
                    businessid: '${param.id }',
                    processid: '${param.processid }'
                });

                // 审批信息
                $comments.comments({
                    businesskey: '${param.id }',
                    processid: '${param.processid }',
                    type: 'vertical',
                    width: '120',
                    agree: null
                });

                // 供应商
                $supplierid.supplierWidget({
                    required: true,
                    onSelect: function(data) {

                    }
                });

                // 部门
                $deptid.widget({
                    referwid: 'RL_T_BASE_DEPATMENT',
                    singleSelect: true,
                    required: false,
                    width: 150
                });

                // 银行
                $paybank.widget({
                    referwid: 'RL_T_BASE_FINANCE_BANK',
                    singleSelect: true,
                    <c:choose>
                        <c:when test="${param.type eq 'handle' and param.step eq '02' }">
                            required: true,
                        </c:when>
                        <c:when test="${param.step eq '99' }">
                            required: false,
                        </c:when>
                        <c:otherwise>
                            required: false,
                        </c:otherwise>
                    </c:choose>
                    width: 150
                });

                var matcostDetailCol = [
                    [
                        {field:'id', title: '编号', width: 80, hidden: true},
                        {field: 'customerid', title: '客户', width: 150,
                            editor: {
                                type: 'widget',
                                options: {
                                    referwid: 'RL_T_BASE_SALES_CUSTOMER',
                                    required: true,
                                    singleSelect: true,
                                    async: false,
                                    width: 145,
                                    setValueSelect: false,
                                    onSelect: function(data) {
                                        customerMap[data.id] = data.name;
                                    }
                                }
                            },
                            formatter: function(value, row, index){

                                if(row.customername && !customerMap[value]) {
                                    customerMap[value] = row.customername;
                                }

                                var name = value || '';
                                if((customerMap[value] || '') != '') {

                                    name = customerMap[value];
                                }

                                if(name == '') {
                                    return value;
                                }

                                return '<span title="' + name + '">' + name + '</span>';
                            }
                        },
                        {field: 'brandid', title: '品牌', width: 110,
                            editor: {
                                type: 'widget',
                                options: {
                                    referwid: 'RL_T_BASE_GOODS_BRAND',
                                    required: true,
                                    singleSelect: true,
                                    async: false,
                                    width: 105,
                                    onSelect: function(data) {
                                        brandMap[data.id] = data.name;
                                    }
                                }
                            },
                            formatter: function(value, row, index){

                                if(row.brandname && !brandMap[value]) {
                                    brandMap[value] = row.brandname;
                                }

                                var name = value || '';
                                if((brandMap[value] || '') != '') {

                                    name = brandMap[value];
                                }

                                if(name == '') {
                                    return value;
                                }

                                return '<span title="' + name + '">' + name + '</span>';
                            }
                        },
                        {field: 'expensesort', title: '费用科目', width: 100,
                            editor: {
                                type: 'widget',
                                options: {
                                    referwid: 'RT_T_BASE_FINANCE_EXPENSES_SORT',
                                    required: true,
                                    singleSelect: true,
                                    async: false,
                                    width: 85,
                                    onSelect: function(data) {
                                        expensesortMap[data.id] = data.name;
                                    }
                                }
                            },
                            formatter: function(value, row, index){

                                if(row.expensesortname && !expensesortMap[value]) {
                                    expensesortMap[value] = row.expensesortname;
                                }

                                var name = value || '';
                                if((expensesortMap[value] || '') != '') {

                                    name = expensesortMap[value];
                                }

                                if(name == '') {
                                    return value;
                                }

                                return '<span title="' + name + '">' + name + '</span>';
                            }
                        },
                        {
                            field: 'factoryamount', title: '工厂投入', width: 80, align: 'right',
                            formatter: function(value, row, index){

                                if(value) {
                                    return formatterMoney(value);
                                }

                                return value;
                            },
                            editor: {
                                type: 'numberbox',
                                options: {
                                    precision: 2,
                                    required: false
                                }
                            }
                        },
                        {
                            field: 'myamount', title: '自理', width: 80, align: 'right',
                            formatter: function(value, row, index){

                                if(value) {
                                    return formatterMoney(value);
                                }

                                return value;
                            },
                            editor: {
                                type: 'numberbox',
                                options: {
                                    precision: 2,
                                    required: false
                                }
                            }
                        },
                        {
                            field: 'feeamount', title: '费用金额', width: 80, align: 'right',
                            formatter: function(value, row, index){

                                if(value) {
                                    return formatterMoney(value);
                                }

                                return value;
                            },
                            editor: {
                                type: 'numberbox',
                                options: {
                                    precision: 2,
                                    required: false
                                }
                            }
                        },
                        {field: 'remark', title: '备注', width: 170,
                            editor: {
                                type: 'validatebox',
                                options: {
                                    validType: ['maxByteLength[50]']
                                }
                            }
                        }
                    ]
                ];    //matcostDetailCol define ends

                $detail.datagrid({
                    columns: matcostDetailCol,
                    border: true,
                    fit: true,
                    rownumbers: true,
                    showFooter: true,
                    singleSelect: true,
                    data: $.parseJSON('${detailListStr }'),
                    queryParams: {billid: '${param.id}'},
                    onRowContextMenu: function(e, rowIndex, rowData){

                        e.preventDefault();

                        <c:if test="${param.type eq 'view'}">
                            return false;
                        </c:if>

                        $detail.datagrid('selectRow', rowIndex);
                        var selectedRow = $detail.datagrid('getSelected');

                        $("#oa-contextMenu-oaMatcostPage").menu('show', {
                            left:e.pageX,
                            top:e.pageY
                        });

                        // 该行内容为空时，不能编辑
                        if((selectedRow.customerid || '') == '' && retFeeDetailEditIndex() != rowIndex) {
                            $("#oa-contextMenu-oaMatcostPage").menu('disableItem', '#oa-removeRow-oaMatcostPage');
                        } else {
                            $("#oa-contextMenu-oaMatcostPage").menu('enableItem', '#oa-removeRow-oaMatcostPage');
                        }
                    },
                    <c:if test="${param.type eq 'handle' and param.step ne 99}">
                    onDblClickRow: editMatcostDetail,
                    onClickRow: endEditMatcostDetail,
                    </c:if>
                    onLoadSuccess: function(data) {

                        var rows = data.rows;
                        for(var i = rows.length; i < 10; i++) {

                            $detail.datagrid('appendRow', {});
                        }
                        computeTotalAmount();
                    }
                })

            }
        });   // panel close

        $('#oa-addRow-oaMatcostPage').click(addMatcost);
        $('#oa-editRow-oaMatcostPage').click(editMatcost);
        $('#oa-removeRow-oaMatcostPage').click(removeMatcost);

    });   // function close

    /**
     * 添加费用明细
     */
    function addMatcost() {

        if(retFeeDetailEditIndex() >= 0) {

            $.messager.alert("提醒", "当前明细正处于修改状态，无法再进行添加！");
            return true;
        }

        var rows = $detail.datagrid('getRows');

        for(var i in rows) {

            var row = rows[i];
            if(row.customerid) {
            } else {
                $detail.datagrid('beginEdit', i);
                return true;
            }
        }

        $detail.datagrid('appendRow', {});
        $detail.datagrid('beginEdit', rows.length);
    }

    /**
     * 编辑费用明细
     */
    function editMatcost() {

        if(retFeeDetailEditIndex() >= 0) {

            $.messager.alert("提醒", "当前明细正处于修改状态，无法再进行修改！");
            return true;
        }

        var selectedRow = $detail.datagrid('getSelected');
        if(selectedRow == null) {
            return true;
        }

        var index = $detail.datagrid('getRowIndex', selectedRow);
        $detail.datagrid('beginEdit', index);

        return true;
    }

    /**
     * 删除费用明细
     */
    function removeMatcost() {

//          if(retFeeDetailEditIndex() >= 0) {
//
//              $.messager.alert("提醒", "当前明细正处于修改状态，无法再进行删除！");
//              return true;
//          }

        var selectedRow = $detail.datagrid('getSelected');
        if(selectedRow == null) {
            return true;
        }

        var index = $detail.datagrid('getRowIndex', selectedRow);
        $detail.datagrid('deleteRow', index);

        computeTotalAmount();
        return true;
    }

    // 修改客户费用明细
    function editMatcostDetail() {

        var editIndex = retFeeDetailEditIndex();
        if(editIndex >= 0) {
            $.messager.alert("提醒", "当前明细正处于修改状态，无法再进行修改。");
            return false;
        }

        var selectedRow = $detail.datagrid('getSelected');
        var index = $detail.datagrid('getRowIndex', selectedRow);

        beginEditFeeDetailRow(index);
        return true;
    }

    // 结束对客户费用支付明细的修改
    function endEditMatcostDetail() {

        var editIndex = retFeeDetailEditIndex();
        if(editIndex < 0) {
            return true;
        }

        $detail.datagrid('endEdit', editIndex);
        computeTotalAmount();
        return true;
    }

    // 删除客户费用支付明细
    function removeMatcostDetail() {

        var editIndex = retFeeDetailEditIndex();
        if(editIndex >= 0) {
            $.messager.alert("提醒", "当前明细正处于修改状态，无法再进行删除。");
            return false;
        }

        var selectedRow = $detail.datagrid('getSelected');
        var index = $detail.datagrid('getRowIndex', selectedRow);

        $detail.datagrid('deleteRow', index);
        return true;
    }

    // 获取目前Datagrid处于编辑的行号，返回-1时，表明当前Datagrid未处于编辑状态
    function retFeeDetailEditIndex() {

        var rows = $detail.datagrid('getRows');

        for(var i = 0; i < rows.length; i++) {
            var editors = $detail.datagrid('getEditors', i);
            if(editors.length > 0) {
                return i;
            }
        }
        return -1;
    }

    // 编辑明细
    function beginEditFeeDetailRow(index) {

        $detail.datagrid('beginEdit', index);
    }

    /**
     * 计算合计金额
     * @returns {boolean}
     */
    function computeTotalAmount() {

        if(retFeeDetailEditIndex() >= 0) {
            return true;
        }

        var rows = $detail.datagrid('getRows');
        var factoryamount = 0;
        var myamount = 0;
        var feeamount = 0;
        for(var i in rows) {

            var row = rows[i];
            factoryamount = parseFloat(factoryamount || 0) + parseFloat(row.factoryamount || 0);
            myamount = parseFloat(myamount || 0) + parseFloat(row.myamount || 0);
            feeamount = parseFloat(feeamount || 0) + parseFloat(row.feeamount || 0);
        }

        var sumRow = {customerid: '', customername: '合计', factoryamount: formatterMoney(factoryamount), myamount: formatterMoney(myamount), feeamount: formatterMoney(feeamount)};
        $detail.datagrid('reloadFooter', [sumRow]);
        return true;
    }

    // 提交客户费用申请单表单
    function oamatcost_handle_save_form_submit(callBack, args) {

        $form.form({
            onSubmit: function(param) {

                var flag = $form.form('validate');
                if(!flag) {

                    loaded();
                    return false;
                }

                $detaillistStr.val(JSON.stringify($detail.datagrid('getRows')));

                loading("提交中...");
            },
            success: function(data) {

                loaded();
                var json;
                try{
                    json = $.parseJSON(data);
                }catch(e){
                    $.messager.alert('提醒', '保存失败！');
                    return false;
                }

                // 保存失败
                if(data.flag) {
                    $.messager.alert('提醒', '保存失败！');
                    return false;
                }

                // 保存成功
                $.messager.alert('提醒', '保存成功。');
                if(callBack.data != undefined && callBack.data != null) {

                    callBack.data(json.backid);
                    return false;
                }

            }
        }).submit();
    }

    // 提交表单(工作页面提交表单接口方法)
    function workFormSubmit(call, args) {

        endEditMatcostDetail();
        if(retFeeDetailEditIndex() >= 0) {

            $.messager.alert("提醒", "明细正处于编辑中，无法提交！");
            return true;
        }

        oamatcost_handle_save_form_submit(call, args);
    }
    -->
</script>
</body>
</html>