<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>客户库存单据新增页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <form id="crm-form-customerStorageOrderAddPage" action="crm/customerStorageOrder/addCustomerStorageOrder.do" method="post">
        <div data-options="region:'north',border:false" style="height:140px;">
            <table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
                <tr>
                    <td class="len80 left">编&nbsp;&nbsp;号：</td>
                    <td class="len165"><input class="len130 easyui-validatebox" id="crm-id-customerStorageOrderAddPage" name="customerStorageOrder.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
                    <td class="len80 left">业务日期：</td>
                    <td class="len165"><input id="crm-businessdate-customerStorageOrderAddPage" type="text" class="len150 Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${date }" name="customerStorageOrder.businessdate" /></td>
                    <td class="len80 left">状&nbsp;&nbsp;态：</td>
                    <td class="len165"><select disabled="disabled" class="len150"><option>新增</option></select></td>
                </tr>
                <tr>
                    <td class="len80 left">客&nbsp;&nbsp;户：</td>
                    <td colspan="3">
                        <input type="text" id="crm-customer-customerStorageOrderAddPage" name="customerStorageOrder.customerid" style="width: 300px;" required="required" /><span id="crm-customer-showid-customerStorageOrderAddPage" style="margin-left:5px;line-height:25px;"></span>
                        <input type="hidden" id="crm-customer-hidden-customerStorageOrderAddPage"/>
                    </td>
                    <td>来源类型：</td>
                    <td class="len150">
                        <select disabled="disabled" style="width: 150px;">
                            <option value="0"selected="selected">普通</option>
                            <option value="1">手机</option>
                            <option value="2">导入</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="len80 left">销售部门：</td>
                    <td>
                        <input type="text" id="crm-salesDept-customerStorageOrderAddPage" name="customerStorageOrder.salesdept" />
                    </td>
                    <td class="len80 left">客户业务员：</td>
                    <td>
                        <input type="text" id="crm-salesMan-customerStorageOrderAddPage" name="customerStorageOrder.salesuser"/>
                    </td>
                    <td>客户单号：</td>
                    <td class="len165"><input class="len150 easyui-validatebox" id="crm-sourceid-customerStorageOrderAddPage" name="customerStorageOrder.sourceid"/></td>
                </tr>
                <tr>
                    <td class="len80 left">备&nbsp;&nbsp;注：</td>
                    <td colspan="5"><input type="text" name="customerStorageOrder.remark" value="<c:out value="${customerStorageOrder.remark }"></c:out>" style="width:680px;" /></td>
                </tr>
            </table>
        </div>
        <div data-options="region:'center',border:false">
            <input type="hidden" name="goodsjson" id="crm-goodsJson-customerStorageOrderAddPage" />
            <table id="crm-datagrid-customerStorageOrderAddPage"></table>
        </div>
    </form>
    <div class="easyui-menu" id="crm-contextMenu-customerStorageOrderAddPage" style="display: none;">
        <div id="crm-addRow-customerStorageOrderAddPage" data-options="iconCls:'button-add'">添加</div>
        <div id="crm-editRow-customerStorageOrderAddPage" data-options="iconCls:'button-edit'">修改</div>
        <div id="crm-deleteRow-customerStorageOrderAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
<div id="crm-dialog-customerStorageOrderAddPage" class="easyui-dialog" data-options="closed:true"></div>
<script type="text/javascript">

    $(function(){
        $("#crm-buttons-customerStorageOrderPage").buttonWidget("initButtonType", 'add');
        $("#crm-datagrid-customerStorageOrderAddPage").datagrid({ //销售商品明细信息编辑
            authority:tableColJson,
            columns: tableColJson.common,
            frozenColumns: tableColJson.frozen,
            border: true,
            fit: true,
            rownumbers: true,
            showFooter: true,
            striped:true,
            singleSelect: false,
            checkOnSelect:true,
            selectOnCheck:true,
            onLoadSuccess: function(data) {
                if (data.rows.length < 12) {
                    var j = 12 - data.rows.length;
                    for (var i = 0; i < j; i++) {
                        $(this).datagrid('appendRow', {});
                    }
                } else {
                    $(this).datagrid('appendRow', {});
                }
            },
            onRowContextMenu: function(e, rowIndex, rowData){
                e.preventDefault();
                $wareList.datagrid('selectRow', rowIndex);
                $("#crm-contextMenu-customerStorageOrderAddPage").menu('show', {
                    left:e.pageX,
                    top:e.pageY
                });
            },
            onDblClickRow: function(rowIndex, rowData) {
                $(this).datagrid('clearSelections').datagrid('selectRow', rowIndex);
                if (rowData.goodsid == undefined) {
                    beginAddCustomerStorageDetail();
                }else{
                    beginEditCustomerStorageDetail(rowData,false);
                }
            }
        }).datagrid("loadData", {'total':12,'rows':[{},{},{},{},{},{},{},{},{},{},{},{} ]/**,'footer':[{goodsid:'合计'}]**/}).datagrid('columnMoving');

        //添加
        $("#crm-addRow-customerStorageOrderAddPage").click(function(){
            beginAddCustomerStorageDetail();
        });
        //修改
        $("#crm-editRow-customerStorageOrderAddPage").click(function(){
            var row = $wareList.datagrid('getSelected');
            beginEditCustomerStorageDetail(row);
        });
        //删除
        $("#crm-deleteRow-customerStorageOrderAddPage").click(function(){
            deleteCustomerStorageDetail();
        });

        $("#crm-customer-customerStorageOrderAddPage").customerWidget({ //客户参照窗口
            name:'t_crm_customer_storage',
            col:'customerid',
            required:true,
            isopen:true,
            onSelect:function(data){
                var html = "编号："+'<a href="javascript:showCustomer(\''+data.id+'\')">'+data.id+'</a>';
                $("#crm-customer-showid-customerStorageOrderAddPage").html(html);
                $("#crm-customer-customerStorageOrderAddPage").customerWidget("setValue",data.id);
                $("#crm-customer-showid-customerStorageOrderAddPage").val(data.id);
                $("#crm-customer-hidden-customerStorageOrderAddPage").val(data.id);
                $("#crm-salesMan-customerStorageOrderAddPage").widget("setValue",data.salesuserid);
                if(data.salesdeptid!=null && data.salesdeptid!=""){
                    $("#crm-salesDept-customerStorageOrderAddPage").widget("setValue",data.salesdeptid);
                }else{
                    $("#crm-salesDept-customerStorageOrderAddPage").widget("clear");
                }
                //客户变更后 更新明细价格数据
                changeGoodsPrice();
            },
            onClear:function(){
                $("#crm-customer-showid-customerStorageOrderAddPage").text("");
            }
        });
        //销售部门
        $("#crm-salesDept-customerStorageOrderAddPage").widget({
            referwid:'RL_T_BASE_DEPARTMENT_SELLER',
            width:150,
            singleSelect:true
        });
        //客户业务员
        $("#crm-salesMan-customerStorageOrderAddPage").widget({
            referwid:'RL_T_BASE_PERSONNEL_SELLER',
            width:150,
            singleSelect:true
        });
        $("#crm-form-customerStorageOrderAddPage").form({
            onSubmit: function(){
                var flag = $(this).form('validate');
                if(flag==false){
                    return false;
                }
                loading("提交中..");
            },
            success:function(data){
                loaded();
                var json = $.parseJSON(data);
                if(json.flag==true){
                    $.messager.alert("提醒","保存成功！");
                    $("#crm-backid-customerStorageOrderPage").val(json.id);
                    $("#crm-table-customerStorageOrderListPage").datagrid('reload');
                    $("#crm-panel-customerStorageOrderPage").panel('refresh', 'crm/customerStorageOrder/showCustomerStorageEditPage.do?id='+json.id);
                }else{
                    $.messager.alert("提醒","保存失败");
                }
            }
        });

    });

    //根据客户编号显示客户详情
    function showCustomer(customerId){
        $('<div id="crm-dialog-customer"></div>').appendTo('#crm-dialog-customerStorageOrderAddPage');
        $('#crm-dialog-customer').dialog({
            maximizable:true,
            resizable:true,
            title: "客户档案【查看】",
            width: 740,
            height: 450,
            closed: true,
            cache: false,
            href: 'basefiles/showCustomerSimplifyViewPage.do?id='+customerId,
            modal: true,
            onClose:function(){
                $('#crm-dialog-customer').dialog("destroy");
            }
        });
        $("#crm-dialog-customer").dialog("open");
    }

    var $wareList = $("#crm-datagrid-customerStorageOrderAddPage");

</script>
</body>
</html>
