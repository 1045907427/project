<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>代垫收回查看页面</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:true">
        <div>
            <form action="" method="post" id="matcostsReimburse-form-add" style="padding: 10px;">
                <input type="hidden" name="matcostsInput.id" value="${matcostsInput.id }"/>
                <input type="hidden" id="matcostsReimburse-hidden-supplierdeptid" name="matcostsInput.supplierdeptid"/>
                <table cellpadding="2" cellspacing="2" border="0">
                    <tr>
                        <td width="70px" align="right">业务日期:</td>
                        <td align="left"><input id="matcostsReimburse-date-businesstime" type="text"
                                                name="matcostsInput.businessdate" value="${matcostsInput.businessdate }"
                                                readonly="readonly" style="width:120px"/></td>
                    </tr>
                    <tr>
                        <td align="right">供应商:</td>
                        <td align="left" colspan="3"><input id="matcostsReimburse-widget-supplierid" type="text"
                                                            name="matcostsInput.supplierid"
                                                            value="${matcostsInput.supplierid }"
                                                            text="${matcostsInput.suppliername}" readonly="readonly"
                                                            style="width:325px;"/></td>
                    </tr>
                    <tr>
                        <td width="70px" align="right">所属部门:</td>
                        <td align="left"><input id="matcostsReimburse-widget-supplierdeptid"
                                                name="matcostsInput.supplierdeptid"
                                                value="${matcostsInput.supplierdeptid }" readonly="readonly" type="text"
                                                style="width: 120px;"/></td>
                        <td align="right">收回方式:</td>
                        <td align="left"><input id="matcostsReimburse-widget-reimbursetype"
                                                name="matcostsInput.reimbursetype"
                                                value="${matcostsInput.reimbursetype }" readonly="readonly" type="text"
                                                style="width:120px;"/></td>
                    </tr>
                    <tr>
                        <td align="right">银行名称:</td>
                        <td align="left"><input id="matcostsReimburse-widget-bankid" name="matcostsInput.bankid"
                                                type="text" style="width:120px;" value="${matcostsInput.bankid }"
                                                readonly="readonly"/></td>
                        <td align="right">收回科目:</td>
                        <td align="left"><input id="matcostsReimburse-widget-shsubjectid"
                                                name="matcostsInput.shsubjectid" value="${matcostsInput.shsubjectid }"
                                                type="text" style="width:120px;" readonly="readonly"/></td>
                    </tr>
                    <tr>
                        <td align="right">单位:</td>
                        <td align="left"><input id="matcostsReimburse-text-unitid" name="matcostsInput.unitid"
                                                value="${matcostsInput.unitid }" type="text" style="width:120px;"
                                                readonly="readonly"/></td>
                        <td align="right">数量:</td>
                        <td align="left"><input id="matcostsReimburse-text-unitnum" name="matcostsInput.unitnum"
                                                value="${matcostsInput.unitnum }" type="text" style="width:120px;"
                                                readonly="readonly"/></td>
                    </tr>
                    <tr>
                        <td align="right">单价:</td>
                        <td align="left"><input id="matcostsReimburse-text-taxprice" name="matcostsInput.taxprice"
                                                value="${matcostsInput.taxprice }" class="easyui-numberbox"
                                                data-options="precision:4" type="text" style="width:120px;"
                                                readonly="readonly"/></td>
                        <td align="right">金额:</td>
                        <td align="left"><input id="matcostsReimburse-text-reimburseamount"
                                                name="matcostsInput.reimburseamount" class="easyui-numberbox"
                                                data-options="precision:2" value="${matcostsInput.reimburseamount }"
                                                type="text" style="width:120px;" readonly="readonly"/></td>
                    </tr>
                    <tr>
                        <td align="right">核销日期:</td>
                        <td align="left"><input value="${matcostsInput.writeoffdate }" readonly="readonly" type="text"
                                                style="width:120px;"/></td>
                        <td align="right">核销人员:</td>
                        <td align="left"><input value="${matcostsInput.writeoffername }" readonly="readonly" type="text"
                                                style="width:120px;"/></td>
                    </tr>
                    <tr>
                        <td align="right">核销金额:</td>
                        <td align="left"><input value="${matcostsInput.writeoffamount }" class="easyui-numberbox"
                                                data-options="precision:2" readonly="readonly" type="text"
                                                style="width:120px;" value="0"/></td>
                        <td align="right">核销余额:</td>
                        <td align="left" colspan="3"><input value="${matcostsInput.remainderamount }"
                                                            class="easyui-numberbox" data-options="precision:2"
                                                            readonly="readonly" type="text" style="width:120px;"
                                                            value="0"/></td>
                    </tr>
                    <tr>
                        <td align="right">备注:</td>
                        <td align="left" colspan="3">
                            <textarea id="matcostsReimburse-text-remark" name="matcostsInput.remark" disabled="disabled"
                                      style="width: 325px;height:60px;">${matcostsInput.remark }</textarea>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>

    <div data-options="region:'south'">
        <div class="buttonDetailBG" style="height:30px;text-align:right;">
            <input type="button" name="savegoon" id="matcostsReimburse-views-save-saveMenu" value="保存"/>
        </div>
    </div>
</div>
<script type="text/javascript">

    $(function () {
        //供应商
        $("#matcostsReimburse-widget-supplierid").supplierWidget({
            name: 't_js_matcostsinput',
            col: 'supplierid',
            singleSelect: true,
            onlyLeafCheck: false,
            required: true,
            initValue: '${matcostsInput.supplierid}'
        });
        //收回方式
        $("#matcostsReimburse-widget-reimbursetype").widget({
            width: 120,
            name: 't_js_matcostsinput',
            col: 'reimbursetype',
            singleSelect: true,
            initSelectNull: true
        });
        //所属部门
        $("#matcostsReimburse-widget-supplierdeptid").widget({
            width: 120,
            name: 't_js_matcostsinput',
            col: 'supplierdeptid',
            singleSelect: true
        });

        $("#matcostsReimburse-widget-bankid").widget({
            width: 120,
            name: 't_js_matcostsinput',
            col: 'bankid',
            singleSelect: true
        });
        //收回科目
        $("#matcostsReimburse-widget-shsubjectid").widget({
            referwid: 'RL_T_BASE_SUBJECT',
            width: 120,
            singleSelect: true,
            onlyLeafCheck: false,
            treePName: false,
            treeNodeDataUseNocheck: true,
            param: [
                {field: 'typecode', op: 'equal', value: 'DDREIMBURSE_SUBJECT'}
            ]
        });

        //主单位
        $("#matcostsReimburse-text-unitid").widget({
            referwid: 'RL_T_BASE_GOODS_METERINGUNIT',
            singleSelect: true,
            width: 120,
            onlyLeafCheck: true
        });

        initReimburseAEVPage();
    });

</script>
</body>
</html>
