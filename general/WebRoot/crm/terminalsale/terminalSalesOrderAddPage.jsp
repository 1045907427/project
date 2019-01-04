<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>客户销量新增页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <form id="crm-form-terminalSalesOrderAddPage" action="crm/terminal/addTerminalSalesOrder.do" method="post">
        <div data-options="region:'north',border:false" style="height:100px;">
            <table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
                <tr>
                    <td class="len80 left">编&nbsp;&nbsp;号：</td>
                    <td class="len165"><input class="len130 easyui-validatebox" id="crm-id-terminalSalesOrderAddPage" name="crmSalesOrder.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
                    <td class="len80 left">业务日期：</td>
                    <td class="len165"><input id="crm-businessdate-terminalSalesOrderAddPage" type="text" class="len150 Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${date }" name="crmSalesOrder.businessdate" /></td>
                    <td class="len80 left">状&nbsp;&nbsp;态：</td>
                    <td class="len165"><select disabled="disabled" class="len150"><option>新增</option></select></td>
                </tr>
                <tr>
                    <td class="len80 left">客&nbsp;&nbsp;户：</td>
                    <td colspan="3">
                        <input type="text" id="crm-customer-terminalSalesOrderAddPage" name="crmSalesOrder.customerid" style="width: 300px;" required="required" /><span id="crm-customer-showid-terminalSalesOrderAddPage" style="margin-left:5px;line-height:25px;"></span>
                        <input type="hidden" id="crm-customer-hidden-terminalSalesOrderAddPage"/>
                    </td>
                    <td>来源类型：</td>
                    <td class="len150">
                        <select disabled="disabled" style="width: 150px;">
                            <option value="0"selected="selected">普通</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="len80 left">销售部门：</td>
                    <td>
                        <input type="text" id="crm-salesDept-terminalSalesOrderAddPage" name="crmSalesOrder.salesdept" />
                    </td>
                    <td class="len80 left">客户业务员：</td>
                    <td>
                        <input type="text" id="crm-salesMan-terminalSalesOrderAddPage" name="crmSalesOrder.salesuser"/>
                    </td>
                    <td>客户单号：</td>
                    <td class="len165"><input class="len150 easyui-validatebox" id="crm-sourceid-terminalSalesOrderAddPage" name="crmSalesOrder.sourceid"/></td>
                </tr>
                <tr>
                    <td class="len80 left">备&nbsp;&nbsp;注：</td>
                    <td colspan="5"><input type="text" name="crmSalesOrder.remark" style="width:690px;" /></td>
                </tr>
            </table>
        </div>
        <div data-options="region:'center',border:false">
            <input type="hidden" name="goodsjson" id="crm-goodsJson-terminalSalesOrderAddPage" />
            <table id="crm-datagrid-terminalSalesOrderAddPage"></table>
        </div>
    </form>
    <div class="easyui-menu" id="crm-contextMenu-terminalSalesOrderAddPage" style="display: none;">
        <div id="crm-addRow-terminalSalesOrderAddPage" data-options="iconCls:'button-add'">添加</div>
        <div id="crm-editRow-terminalSalesOrderAddPage" data-options="iconCls:'button-edit'">修改</div>
        <div id="crm-deleteRow-terminalSalesOrderAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
</div>
<div id="crm-dialog-terminalSalesOrderAddPage" class="easyui-dialog" data-options="closed:true"></div>
<script type="text/javascript">

    $(function(){
        $("#crm-buttons-terminalSalesOrderPage").buttonWidget("initButtonType", 'add');
        $("#crm-datagrid-terminalSalesOrderAddPage").datagrid({ //销售商品明细信息编辑
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
                $("#crm-contextMenu-terminalSalesOrderAddPage").menu('show', {
                    left:e.pageX,
                    top:e.pageY
                });
            },
            onDblClickRow: function(rowIndex, rowData) {
                $(this).datagrid('clearSelections').datagrid('selectRow', rowIndex);
                if (rowData.goodsid == undefined) {
                    beginAddTerminalDetail();
                }else{
                    beginEditTerminalDetail(rowIndex,rowData);
                }
            }
        }).datagrid("loadData", {'total':12,'rows':[{},{},{},{},{},{},{},{},{},{},{},{} ]/**,'footer':[{goodsid:'合计'}]**/}).datagrid('columnMoving');

        //添加
        $("#crm-addRow-terminalSalesOrderAddPage").click(function(){
            beginAddTerminalDetail();
        });
        //修改
        $("#crm-editRow-terminalSalesOrderAddPage").click(function(){
            var row = $wareList.datagrid('getSelected');
            var rowIndex = $wareList.datagrid("getRowIndex",row);
            beginEditTerminalDetail(rowIndex,row);
        });
        //删除
        $("#crm-deleteRow-terminalSalesOrderAddPage").click(function(){
            deleteTerminalDetail();
        });

        $("#crm-customer-terminalSalesOrderAddPage").customerWidget({ //客户参照窗口
            name:'t_crm_sales_order',
            col:'customerid',
            required:true,
            isopen:true,
            onSelect:function(data){
                var html = "编号："+'<a href="javascript:showCustomer(\''+data.id+'\')">'+data.id+'</a>';
                $("#crm-customer-showid-terminalSalesOrderAddPage").html(html);
                $("#crm-customer-terminalSalesOrderAddPage").customerWidget("setValue",data.id);
                $("#crm-salesMan-terminalSalesOrderAddPage").widget("setValue",data.salesuserid);
                if(data.salesdeptid!=null && data.salesdeptid!=""){
                    $("#crm-salesDept-terminalSalesOrderAddPage").widget("setValue",data.salesdeptid);
                }else{
                    $("#crm-salesDept-terminalSalesOrderAddPage").widget("clear");
                }
                //客户变更后 更新明细价格数据
                changeGoodsPrice();
            },
            onClear:function(){
                $("#crm-customer-showid-terminalSalesterminalSalesOrderAddPage").text("");
            }
        });
        //销售部门
        $("#crm-salesDept-terminalSalesOrderAddPage").widget({
            referwid:'RL_T_BASE_DEPARTMENT_SELLER',
            width:150,
            singleSelect:true
        });
        //客户业务员
        $("#crm-salesMan-terminalSalesOrderAddPage").widget({
            referwid:'RL_T_BASE_PERSONNEL_SELLER',
            width:150,
            singleSelect:true
        });
        $("#crm-form-terminalSalesOrderAddPage").form({
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
                    $("#crm-table-terminalSalesOrderList").datagrid('reload');
                    $("#crm-backid-terminalSalesOrderPage").val(json.id);
                    $("#crm-panel-terminalSalesOrderPage").panel('refresh', 'crm/terminal/terminalOrderEditPage.do?id='+json.id);
                }else{
                    $.messager.alert("提醒","保存失败");
                }
            }
        });

    });

    //根据客户编号显示客户详情
    function showCustomer(customerId){
        $('<div id="crm-dialog-customer"></div>').appendTo('#crm-dialog-terminalSalesOrderAddPage');
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

    var $wareList = $("#crm-datagrid-terminalSalesOrderAddPage");

</script>
</body>
</html>
