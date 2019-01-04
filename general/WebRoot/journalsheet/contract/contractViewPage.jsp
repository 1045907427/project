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
                    <td class="len165"><input id="contract-id-contractPage" class="easyui-validatebox" name="contract.id"  value="${contract.id }" readonly="readonly" style="width:150px;"/></td>
                    <td class="len80 right">合同编号：</td>
                    <td class="len165"><input type="text" id="contract-contractid-contractPage" name="contract.contractid" value="${contract.contractid }" style="width:150px;"   readonly="readonly" class="easyui-validatebox" required="true"/></td>
                    <td class="len80 right">状态：</td>
                    <td class="len165"><select id="contract-status-contractPage" disabled="disabled" style="width:150px;">
                        <c:forEach items="${statusList }" var="list">
                            <c:choose>
                                <c:when test="${list.code == contract.status}">
                                    <option value="${list.code }" selected="selected">${list.codename }</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${list.code }">${list.codename }</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select></td>
                </tr>
                <tr>
                    <td  class="len80 left">合同名称：</td>
                    <td class="len165">
                        <input type="text" id="contract-contractname-contractPage" name="contract.contractname" value="${contract.contractname }" style="width:150px;"  readonly="readonly" class="easyui-validatebox" required="true"/>
                    </td>
                    <td class="len80 right">客户类型：</td>
                    <td class="len165">
                        <select id="contract-customertype-select-contractPage" name="contract.customertype" style="width:150px;"  disabled="disabled">
                            <option value="0" <c:if test="${contract.customertype == '0' }">selected="selected"</c:if>>总店</option>
                            <option value="1" <c:if test="${contract.customertype == '1' }">selected="selected"</c:if>>门店</option>
                        </select>
                    </td>
                    <td  class="len80 right">客户：</td>
                    <td class="len165">
                        <input type="text" id="contract-customerid-contractPage" name="contract.customerid" value="${contract.customerid }" style="width:150px;" />
                    </td>
                </tr>
                <tr>
                    <td  class="len80 left">联系人：</td>
                    <td class="len165">
                        <input type="text" id="contract-contacts-contractPage" name="contract.contacts" value="${contract.contacts }" style="width:150px;" readonly="readonly"/>
                    </td>
                    <td class="len80 right">联系方式：</td>
                    <td class="len165">
                        <input type="text" id="contract-contactstype-contractPage" name="contract.contactstype" value="${contract.contactstype }" style="width:150px;" readonly="readonly" />
                    </td>
                    <td  class="len80 right">部门：</td>
                    <td class="len165">
                        <input type="text" id="contract-deptid-contractPage" name="contract.deptid" value="${contract.deptid }" style="width:150px;" />
                    </td>
                </tr>
                <tr>
                    <td  class="len80 left">开始日期：</td>
                    <td class="len165"><input class="len150" name="contract.begindate" value="${contract.begindate }"  type="text"  readonly="readonly" /></td>
                    <td  class="len80 right">结束日期：</td>
                    <td class="len165"><input class="len150" name="contract.enddate" value="${contract.enddate }" type="text"  readonly="readonly"/></td>
                    <td  class="len80 right">合同负责人：</td>
                    <td class="len165">
                        <input type="text" id="contract-personnelid-contractPage" name="contract.personnelid" value="${contract.personnelid }" style="width:150px;" />
                    </td>
                </tr>
                <tr>
                    <td  class="len80 left">供应商：</td>
                    <td class="len165">
                        <input type="text" id="contract-supplierid-contractPage" name="contract.supplierid" value="${contract.supplierid }" style="width:150px;" />
                    </td>
                    <td class="len80 right">品牌：</td>
                    <td class="len165">
                        <input type="text" id="contract-brandid-contractPage" name="contract.brandid" value="${contract.brandid }" style="width:150px;" />
                    </td>
                    <td class="len80 right">参考数据来源：</td>
                    <td class="len165">
                        <select id="contract-datasource-select-contractPage" name="contract.datasource" style="width:150px;" disabled="disabled">
                            <option value="0" <c:if test="${contract.datasource == '0' }">selected="selected"</c:if>>数据上报</option>
                            <option value="1" <c:if test="${contract.datasource == '1' }">selected="selected"</c:if>>系统销售</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="len80 left">备注：</td>
                    <td colspan="5" style="text-align: left">
                        <input type="text"  id="contract-remark-contractPage" name="contract.remark" value="${contract.remark }" readonly="readonly"  style="width: 680px;" onfocus="frm_focus('contract.remark');" onblur="frm_blur('contract.remark');"/>
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
        var customertype  ='${contract.customertype }';
        if("0"==customertype){
            $("#contract-customerid-contractPage").widget({
                referwid:"RL_T_BASE_SALES_CUSTOMER_PARENT",
                width:150,
                singleSelect:true,
                onlyLeafCheck:false,
                view:true,
                isPageReLoad:false,
                required:true
            });
        }else if("1"==customertype){
            $("#contract-customerid-contractPage").widget({
                referwid:"RL_T_BASE_SALES_CUSTOMER",
                width:150,
                singleSelect:true,
                onlyLeafCheck:false,
                view:true,
                isPageReLoad:false,
                required:true
            });
        }
        $("#contract-buttons-contractPage").buttonWidget("initButtonType", 'view');

        $("#contract-deptid-contractPage").widget({
            referwid:'RL_T_BASE_DEPARTMENT_BUYER',
            width:150,
            singleSelect:true,
            onlyLeafCheck:false,
            view:true,
            required:true,
            readonly:true
        });
        $("#contract-supplierid-contractPage").widget({
            referwid:'RL_T_BASE_BUY_SUPPLIER',
            width:150,
            singleSelect:true,
            onlyLeafCheck:false,
            view:true,
            required:true,
            readonly:true
        });
        $("#contract-brandid-contractPage").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            width:150,
            singleSelect:true,
            onlyLeafCheck:false,
            view:true,
            required:true,
            readonly:true
        });
        $("#contract-personnelid-contractPage").widget({
            referwid:'RL_T_BASE_PERSONNEL',
            width:150,
            singleSelect:true,
            onlyLeafCheck:false,
            view:true,
            required:true,
            readonly:true
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
            data:JSON.parse('${detailList}'),

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
        }).datagrid('columnMoving');

        $("#contract-buttons-contractPage").buttonWidget("disableButton","button-audit");
        $("#contract-buttons-contractPage").buttonWidget("disableButton","button-delete");
    })
</script>
</body>

</html>