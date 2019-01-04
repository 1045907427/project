<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>代垫录入详情页面</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:true">
        <div align="center">
            <form action="" method="post" id="matcostsInput-form-add" style="padding: 10px;">
                <input type="hidden" name="matcostsInput.id" value="${matcostsInput.id }" />
                <table cellpadding="2" cellspacing="2" border="0">
                    <tr>
                        <td class="len80 right">业务日期:</td>
                        <td class="len140 left"><input id="journalsheet-date-businesstime" type="text" name="matcostsInput.businessdate" class="len136 Wdate" value="${matcostsInput.businessdate }" class="easyui-validatebox" value="${businessdate }" readonly="readonly"/></td>
                        <td class="len80 right">经办人:</td>
                        <td class="len140 left"><input id="journalsheet-number-transactorid" class="len136" type="text" name="matcostsInput.transactorid" value="${matcostsInput.transactorid }" readonly="readonly"/></td>
                    </tr>
                    <c:if test="${ matcostsInput.hcflag=='1'}">
                        <tr>
                            <td class="len80 right">关联代垫:</td>
                            <td colspan="3" align="left">
                                <input type="text" class="readonly" readonly="readonly" name="matcostsInput.hcreferid" value="${matcostsInput.hcreferid }"  style="width:368px; font-family: sans-serif;"/>
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <td class="len80 right">支付日期:</td>
                        <td class="len140 left"><input id="journalsheet-date-paydate" type="text" name="matcostsInput.paydate" class="Wdate len136" value="${matcostsInput.paydate }" readonly="readonly" /></td>
                        <td class="len80 right">收回日期:</td>
                        <td class="len140 left"><input id="journalsheet-date-takebackdate" type="text" name="matcostsInput.takebackdate" class="Wdate len136" value="${matcostsInput.takebackdate }" readonly="readonly" /></td>
                    </tr>
                    <tr>
                        <td class="len80 right">OA编号:</td>
                        <td class="len140 left"><input id="journalsheet-text-oaid" type="text" name="matcostsInput.oaid" class="len136" value="${matcostsInput.oaid }" readonly="readonly"/></td>
                        <td class="len80 right">科目名称:</td>
                        <td class="len140 left"><input id="journalsheet-widget-subjectid" class="len136" type="text" name="matcostsInput.subjectid" value="${matcostsInput.subjectid }"  disabled="disabled" readonly="readonly"/></td>
                    </tr>
                    <tr>
                        <td class="right">供应商:</td>
                        <td align="left" colspan="3"><input id="journalsheet-widget-supplierid" type="text" value="${matcostsInput.suppliername }" readonly="readonly"  style="width:368px"/></td>
                    </tr>
                    <tr>
                        <td class="right">所属部门:</td>
                        <td class="left"><input id="journalsheet-widget-supplierdeptid" name="matcostsInput.supplierdeptid" readonly="readonly" type="text" style="width: 120px;"/></td>
                        <td class="right">商品品牌:</td>
                        <td class="left"><input type="text" id="report-query-brandid" class="len136" name="matcostsInput.brandid" value="${matcostsInput.brandid }" readonly="readonly"/></td>
                    </tr>
                    <tr>
                        <td class="right">客户名称:</td>
                        <td class="left" colspan="3"><input id="journalsheet-widget-customerid" type="text" style="width: 368px;" value="${matcostsInput.customername }" readonly="readonly"/></td>
                    </tr>
                    <tr>
                        <td class="right">工厂投入:</td>
                        <td class="left"><input id="journalsheet-number-factoryamount" class="len136" type="text" name="matcostsInput.factoryamount" value="${matcostsInput.factoryamount }" readonly="readonly" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/></td>
                        <td class="right">费用金额:</td>
                        <td class="left"><input id="journalsheet-number-expense" class="len136" type="text" name="matcostsInput.expense" value="${matcostsInput.expense }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','" readonly="readonly"/></td>
                    </tr>
                    <tr>
                        <td class="right">收回金额:</td>
                        <td class="left"><input id="journalsheet-number-reimburseamount" class="len136" type="text" value="${matcostsInput.writeoffamount }" readonly="readonly" class="easyui-numberbox" data-options="precision:2,groupSeparator:','" disabled="disabled"/></td>
                        <td class="right">代垫金额:</td>
                        <td class="left"><input id="journalsheet-number-actingmatamount" class="len136" disabled="disabled" type="text" name="matcostsInput.actingmatamount" value="${matcostsInput.actingmatamount }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','" disabled="disabled"/></td>
                    </tr>
                    <tr>
                        <td class="right">备注:</td>
                        <td align="left" colspan="5">
                            <textarea name="matcostsInput.remark" class="easyui-validatebox" validType="maxLen[200]" style="width: 365px; height: 50px;" disabled="disabled">${matcostsInput.remark }</textarea>
                        </td>
                    </tr>
                </table>
                <div style="display:none">
                    <input id="journalsheet-number-htcompdiscount" class="len136" type="hidden" name="matcostsInput.htcompdiscount" value="${matcostsInput.htcompdiscount }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','" readonly="readonly" />
                    <input id="journalsheet-number-htpayamount" class="len136" type="hidden" name="matcostsInput.htpayamount" value="${matcostsInput.htpayamount }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','" readonly="readonly" />
                    <input id="journalsheet-number-branchaccount" class="len136" type="hidden" disabled="disabled" name="matcostsInput.branchaccount" value="${matcostsInput.branchaccount }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/>
                    <input id="journalsheet-text-hcflag" type="hidden" name="matcostsInput.hcflag" value="${matcostsInput.hcflag }" />
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){

        $("#report-query-brandid").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            width:136,
            singleSelect:true
        });
        //科目编码
        $("#journalsheet-widget-subjectid").widget({
            width:136,
            name:'t_js_matcostsinput',
            col:'subjectid',
            singleSelect:true,
            initValue:'${matcostsInput.subjectid}'
        });
        //所属部门
        $("#journalsheet-widget-supplierdeptid").widget({
            width:136,
            name:'t_js_matcostsinput',
            col:'supplierdeptid',
            initValue:'${matcostsInput.supplierdeptid}',
            singleSelect:true
        });

        //经办人
        $("#journalsheet-widget-transactorid").widget({
            width:136,
            name:'t_js_matcostsinput',
            col:'transactorid',
            readonly:true,
            initValue:'${matcostsInput.transactorid}'
        });

    });
</script>
</body>
</html>
