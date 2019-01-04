<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <title>新增代配送采购单</title>
</head>
<body>

<div class="easyui-layout" data-options="fit:true,border:false">
    <form id="contract-form-contractPage" action="" method="post">
        <div data-options="region:'north',border:false" style="height: 200px;">
            <table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
                <tr>
                    <td class="len80 left">编号：</td>
                    <td class="len165"><input id="contract-id-contractPage" class="easyui-validatebox" name="contract.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> style="width:150px;"/></td>
                    <td class="len80 right">合同编号：</td>
                    <td class="len165"><input type="text" id="contract-contractid-contractPage" name="contract.contractid" style="width:150px;" class="easyui-validatebox" required="true"/></td>
                    <td class="len80 right">状态：</td>
                    <td class="len165"><select id="contract-status-select-contractPage" disabled="disabled" style="width:150px;">
                        <option ></option>
                        <option value="0" selected="selected">新增</option>
                        <option value="2">保存</option>
                        <option value="3">审核通过</option>
                        <option value="4">关闭</option>
                    </select></td>
                </tr>
                <tr>
                    <td  class="len80 left">合同名称：</td>
                    <td class="len165">
                        <input type="text" id="contract-contractname-contractPage" name="contract.contractname" style="width:150px;" class="easyui-validatebox" required="true"/>
                    </td>
                    <td class="len80 right">客户类型：</td>
                    <td class="len165">
                        <select id="contract-customertype-select-contractPage" name="contract.customertype" style="width:150px;" class="easyui-validatebox" required="true">
                            <option value="0" selected="selected">总店</option>
                            <option value="1">门店</option>
                        </select>
                    </td>
                    <td  class="len80 right">客户：</td>
                    <td class="len165">
                        <input type="text" id="contract-customerid-contractPage" name="contract.customerid" style="width:150px;" />
                    </td>
                </tr>
                <tr>
                    <td  class="len80 left">联系人：</td>
                    <td class="len165">
                        <input type="text" id="contract-contacts-contractPage" name="contract.contacts" style="width:150px;"  />
                    </td>
                    <td class="len80 right">联系方式：</td>
                    <td class="len165">
                        <input type="text" id="contract-contactstype-contractPage" name="contract.contactstype" style="width:150px;" />
                    </td>
                    <td  class="len80 right">部门：</td>
                    <td class="len165">
                        <input type="text" id="contract-deptid-contractPage" name="contract.deptid" style="width:150px;" />
                    </td>
                </tr>
                <tr>
                    <td  class="len80 left">开始日期：</td>
                    <td class="len165"><input class="len150 easyui-validatebox" name="contract.begindate" data-options="required:true" onfocus="WdatePicker({'dateFmt':'yyyy-MM'})" /></td>
                    <td  class="len80 right">结束日期：</td>
                    <td class="len165"><input class="len150 easyui-validatebox" name="contract.enddate" data-options="required:true" onfocus="WdatePicker({'dateFmt':'yyyy-MM'})" /></td>
                    <td  class="len80 right">合同负责人：</td>
                    <td class="len165">
                        <input type="text" id="contract-personnelid-contractPage" name="contract.personnelid" style="width:150px;" />
                    </td>
                </tr>
                <tr>
                    <td  class="len80 left">供应商：</td>
                    <td class="len165">
                        <input type="text" id="contract-supplierid-contractPage" name="contract.supplierid" style="width:150px;" />
                    </td>
                    <td class="len80 right">品牌：</td>
                    <td class="len165">
                        <input type="text" id="contract-brandid-contractPage" name="contract.brandid" style="width:150px;" />
                    </td>
                    <td class="len80 right">参考数据来源：</td>
                    <td class="len165">
                        <select id="contract-datasource-select-contractPage" name="contract.datasource"  style="width:150px;">
                            <option value="0" selected="selected">数据上报</option>
                            <option value="1">系统销售</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="len80 left">备注：</td>
                    <td colspan="5" style="text-align: left">
                        <input type="text"  id="contract-remark-contractPage" name="contract.remark" style="width: 680px;" onfocus="frm_focus('contract.remark');" onblur="frm_blur('contract.remark');"/>
                    </td>
                </tr>
                <tr>
            </table>
        </div>
        <div data-options="region:'center',border:false">
            <table id="contract-datagrid-contractPage"></table>
        </div>
        <input type="hidden" id="contract-contractDetail-contractPage" name="detailJson"/>
        <input type="hidden" id="contract-saveaudit-contractPage" name="saveaudit" value="save"/>
    </form>
</div>
<div class="easyui-menu" id="contract-contextMenu-contractPage" style="display: none;">
    <div id="contract-addRow-contractPage" data-options="iconCls:'button-add'">添加</div>
    <div id="contract-editRow-contractPage" data-options="iconCls:'button-edit'">编辑</div>
    <div id="contract-removeRow-contractPage" data-options="iconCls:'button-delete'">删除</div>
</div>
<div id="contract-dialog-contractPage"></div>
<script type="text/javascript">
    $(function() {

        $("#contract-buttons-contractPage").buttonWidget("initButtonType", 'add');
        $("#contract-customertype-select-contractPage").change(function(){
            $("#contract-customerid-contractPage").widget("clear");
            if($(this).val()=="0"){
                $("#contract-customerid-contractPage").widget({
                    referwid:"RL_T_BASE_SALES_CUSTOMER_PARENT",
                    width:150,
                    singleSelect:true,
                    onlyLeafCheck:false,
                    view:true,
                    required:true
                });
            }
            else if($(this).val()=="1"){
                $("#contract-customerid-contractPage").widget({
                    referwid:"RL_T_BASE_SALES_CUSTOMER",
                    width:150,
                    singleSelect:true,
                    onlyLeafCheck:false,
                    view:true,
                    required:true
                });
            }

        });

        $('#contract-customerid-contractPage').widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
            width:150,
            singleSelect:true,
            onlyLeafCheck:false,
            view:true,
            required:true
        });
        $("#contract-deptid-contractPage").widget({
            referwid:'RL_T_BASE_DEPARTMENT_BUYER',
            width:150,
            singleSelect:true,
            onlyLeafCheck:false,
            view:true,
            required:true
        });
        $("#contract-supplierid-contractPage").widget({
            referwid:'RL_T_BASE_BUY_SUPPLIER',
            width:150,
            singleSelect:true,
            onlyLeafCheck:false,
            view:true,
            required:true
        });
        $("#contract-brandid-contractPage").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            width:150,
            singleSelect:true,
            onlyLeafCheck:false,
            view:true,
            required:true
        });
        $("#contract-personnelid-contractPage").widget({
            referwid:'RL_T_BASE_PERSONNEL',
            width:150,
            singleSelect:true,
            onlyLeafCheck:false,
            view:true,
            required:true
        });


        $("#contract-datagrid-contractPage").datagrid({
            authority:tableColJson,
            columns: tableColJson.common,
            frozenColumns: tableColJson.frozen,
            border: true,
            rownumbers: true,
            showFooter: true,
            striped:true,
            fit:true,
            singleSelect: false,
            checkOnSelect:true,
            selectOnCheck:true,
            onRowContextMenu: function(e, rowIndex, rowData){
                e.preventDefault();
                $("#contract-datagrid-contractPage").datagrid('selectRow', rowIndex);
                $("#contract-contextMenu-contractPage").menu('show', {
                    left:e.pageX,
                    top:e.pageY
                });
            },
            onSortColumn:function(sort, order){
                var rows = $("#contract-datagrid-contractPage").datagrid("getRows");
                var dataArr = [];
                for(var i=0;i<rows.length;i++){
                    if(rows[i].goodsid!=null && rows[i].goodsid!=""){
                        dataArr.push(rows[i]);
                    }
                }
                dataArr.sort(function(a,b){
                    if(order=="asc"){
                        return a[sort]>b[sort]?1:-1
                    }else{
                        return a[sort]<b[sort]?1:-1
                    }
                });
                $("#contract-datagrid-contractPage").datagrid("loadData",dataArr);
                return false;
            },
            onDblClickRow: function(rowIndex, rowData){
                $(this).datagrid('clearSelections').datagrid('selectRow',rowIndex);
                if(rowData.caluseid == undefined){
                    beginAddDetail();
                }
                else{
                    beginEditDetail();
                }
            },

        }).datagrid("loadData", {'total':12,'rows':[{},{},{},{},{},{},{},{},{},{},{},{} ]}).datagrid('columnMoving');


        //客户费用合同明细添加
        $("#contract-addRow-contractPage").click(function(){
            var flag = $("#contract-addRow-contractPage").menu('getItem','#contract-addRow-contractPage').disabled;
            if(flag){
                return false;
            }
            beginAddDetail();
        });
        //客户费用合同明细修改
        $("#contract-editRow-contractPage").click(function(){
            var flag = $("#contract-contextMenu-contractPage").menu('getItem','#contract-editRow-contractPage').disabled;
            if(flag){
                return false;
            }
            beginEditDetail();
        });
        //客户费用合同明细删除
        $("#contract-removeRow-contractPage").click(function(){
            var flag = $("#contract-contextMenu-contractPage").menu('getItem','#contract-removeRow-contractPage').disabled;
            if(flag){
                return false;
            }
            removeDetail();
        });
        //订单提交
        $("#contract-form-contractPage").form({
            onSubmit: function(){
                var json = $("#contract-datagrid-contractPage").datagrid('getRows');

                $("#contract-contractDetail-contractPage").val(JSON.stringify(json));
                var flag = $(this).form('validate');
                if(flag==false){
                    return false;
                }

                loading("提交中..");
            },
            success:function(data){

                //表单提交完成后 隐藏提交等待页面
                loaded();
                var json = $.parseJSON(data);
                if(json.flag){
                    $("#contract-id-contractPage").val(json.id);
                    if(json.auditflag){
                        $("#contract-status-select-contractPage").val("3");
                        $.messager.alert("提醒","保存并审核成功");
                        page_type="view";
                        $("#contract-panel-contractPage").panel('refresh', 'journalsheet/contract/showContractViewPage.do?id='+json.id);
                    }else{
                        $("#contract-contractAogorder-status-select").val("2");
                        $.messager.alert("提醒","保存成功");
                        page_type="edit";
                        $("#contract-panel-contractPage").panel('refresh', 'journalsheet/contract/showContractEditPage.do?id='+json.id);

                    }
                    $("#contract-buttons-contractPage").buttonWidget("addNewDataId", json.id);

                }else{
                    $.messager.alert("提醒","保存失败.");
                }
            }
        });
    })
</script>
</body>

</html>