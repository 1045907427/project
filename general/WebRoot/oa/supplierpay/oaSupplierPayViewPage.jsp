<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<title>货款支付申请单</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<div class="easyui-layout" data-options="fit:true,border:false">
			<c:choose>
				<c:when test="${empty pay or empty pay.id}">
					<form action="oa/addOaSupplierPay.do" id="oa-form-oaSupplierPayViewPage" method="post">
				</c:when>
				<c:otherwise>
					<form action="oa/editOaSupplierPay.do" id="oa-form-oaSupplierPayViewPage" method="post">
				</c:otherwise>
			</c:choose>
				<input type="hidden" name="pay.id" id="oa-id-oaSupplierPayViewPage" value="${pay.id }"/>
				<input type="hidden" name="pay.oaid" id="oa-oaid-oaSupplierPayViewPage" value="${param.processid }"/>
				<input type="hidden" id="oa-suppliername-oaSupplierPayViewPage" value="${supplier.name }"/>
				<div data-options="region:'center',border:false">
					<div style="margin: 20px auto; width: 790px; border: 1px solid #AAA;">
                        <process:definitionHeader process="${process}"/>
                        <div class="easyui-panel" data-options="border:false">
                            <table>
                                <tr>
                                    <td class="len80 left">供应商：</td>
                                    <td class="len180"><input class="easyui-validatebox" style="width: 162px;" name="pay.supplierid" id="oa-supplierid-oaSupplierPayViewPage" data-options="required:false" value="${pay.supplierid }" autocomplete="off" maxlength="10"/></td>
                                    <td class="len80 left">收款银行：</td>
                                    <td class="len180"><input class="easyui-validatebox len140" name="pay.collectionbank" id="oa-collectionbank-oaSupplierPayViewPage" data-options="required:false" value="${pay.collectionbank }" autocomplete="off" maxlength="100" readonly="readonly"/></td>
                                    <td class="len80 left">银行账号：</td>
                                    <td class="len180"><input class="easyui-validatebox len140" name="pay.collectionbankno" id="oa-collectionbankno-oaSupplierPayViewPage" data-options="required:false" value="${pay.collectionbankno }" autocomplete="off" maxlength="50" readonly="readonly"/></td>
                                </tr>
                                <tr>
                                    <td class="left">付款金额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.payamount" id="oa-payamount-oaSupplierPayViewPage" data-options="required:false,precision:2" value="${pay.payamount }" autocomplete="off" maxlength="15" readonly="readonly"/></td>
                                    <td class="left">大写金额：</td>
                                    <td class=""><input class="easyui-validatebox len140" name="pay.upperpayamount" id="oa-upperpayamount-oaSupplierPayViewPage" data-options="required:false" value="${pay.upperpayamount }" autocomplete="off" maxlength="50" readonly="readonly"/></td>
                                    <td class="left">付款日期：</td>
                                    <td class=""><input class="easyui-validatebox len140 Wdate" name="pay.paydate" id="oa-paydate-oaSupplierPayViewPage" onclick="" value="${pay.paydate }" autocomplete="off" maxlength="10" readonly="readonly"/></td>
                                </tr>
                                <tr>
                                    <td class="left">付款银行：</td>
                                    <td class=""><input class="easyui-validatebox len140" name="pay.paybank" id="oa-paybank-oaSupplierPayViewPage" data-options="required:false" value="${pay.paybank }" autocomplete="off" maxlength="20" readonly="readonly"/></td>
                                    <td class="left">到货金额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.arrivalamount" id="oa-arrivalamount-oaSupplierPayViewPage" data-options="required:false,precision:2" value="${pay.arrivalamount }" autocomplete="off" maxlength="18" readonly="readonly"/></td>
                                    <td class="left">付款差额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.paymargin" id="oa-paymargin-oaSupplierPayViewPage" data-options="precision:2" value="${pay.paymargin }" autocomplete="off" maxlength="18" readonly="readonly"/></td>
                                </tr>
                                <tr>
                                    <td class="left">到货日期：</td>
                                    <td class=""><input class="easyui-validatebox len140 Wdate" name="pay.arrivaldate" id="oa-arrivaldate-oaSupplierPayViewPage" onclick="" data-options="required:false" value="${pay.arrivaldate }" autocomplete="off" maxlength="10" readonly="readonly"/></td>
                                    <td class="left">发票金额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.billamount" id="oa-billamount-oaSupplierPayViewPage" data-options="required:false,precision:2" value="${pay.billamount }" autocomplete="off" maxlength="18" readonly="readonly"/></td>
                                    <td class="left">收回费用：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.expenseamount" id="oa-expenseamount-oaSupplierPayViewPage" data-options="precision:2" value="${pay.expenseamount }" autocomplete="off" maxlength="18" readonly="readonly"/></td>
                                </tr>
                                <tr>
                                    <td class="left">抽单金额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.writeoffamount" id="oa-writeoffamount-oaSupplierPayViewPage" data-options="precision:2" value="${pay.writeoffamount }" autocomplete="off" maxlength="18" readonly="readonly"/></td>
                                    <td class="left">抽单日期：</td>
                                    <td class=""><input class="easyui-validatebox len140 Wdate" name="pay.writeoffdate" id="oa-writeoffdate-oaSupplierPayViewPage" onclick="" data-options="required:false" value="${pay.writeoffdate }" autocomplete="off" maxlength="10" readonly="readonly"/></td>
                                    <td class="left">订单金额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.orderamount" id="oa-orderamount-oaSupplierPayViewPage" data-options="required:false,precision:2" value="${pay.orderamount }" autocomplete="off" maxlength="18" readonly="readonly"/></td>
                                </tr>
                                <tr>
                                    <td class="left">预付金额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.advanceamount" id="oa-advanceamount-oaSupplierPayViewPage" data-options="precision:2,disabled:true" value="${pay.advanceamount }" autocomplete="off" maxlength="18" readonly="readonly"/></td>
                                    <td class="left">库存金额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.stockamount" id="oa-stockamount-oaSupplierPayViewPage" data-options="precision:2,disabled:true" value="${pay.stockamount }" autocomplete="off" maxlength="18" readonly="readonly"/></td>
                                    <td class="left">应收金额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.receivableamount" id="oa-receivableamount-oaSupplierPayViewPage" data-options="precision:2,disabled:true" value="${pay.receivableamount }" autocomplete="off" maxlength="18" readonly="readonly"/></td>
                                </tr>
                                <tr>
                                    <td class="left">代垫金额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.actingmatamount" id="oa-actingmatamount-oaSupplierPayViewPage" data-options="precision:2,disabled:true" value="${pay.actingmatamount }" autocomplete="off" maxlength="18" readonly="readonly"/></td>
                                    <td class="left">应付金额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.payableamount" id="oa-payableamount-oaSupplierPayViewPage" data-options="precision:2,disabled:true" value="${pay.payableamount }" autocomplete="off" maxlength="18" readonly="readonly"/></td>
                                    <td class="left">合计占用：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.totalamount" id="oa-totalamount-oaSupplierPayViewPage" data-options="precision:2,disabled:true" value="${pay.totalamount }" autocomplete="off" maxlength="18" readonly="readonly"/></td>
                                </tr>
                                <tr>
                                    <td class="left">备注：</td>
                                    <td class="" colspan="5">
                                        <textarea style="width: 668px; height: 50px; resize: none;"name="pay.remark" id="oa-remark-oaSupplierPayViewPage" maxlength="165" readonly="readonly"><c:out value="${pay.remark }"></c:out></textarea>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="easyui-panel" data-options="border:false">
                            <div id="oa-comments-oaSupplierPayViewPage">
                            </div>
                        </div>
                        <div style="border-top: 1px solid #AAA;">&nbsp;</div>
                        <div>
                            <div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
                            <div id="oa-attach-oaSupplierPayViewPage" style="width: 765px;">
                            </div>
                        </div>
                        <div>
                            <div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
                            <div id="oa-comments2-oaSupplierPayViewPage" style="width: 760px;">
                            </div>
                        </div>
					</div>
				</div>
			</form>
		</div>
	</div>
    <script type="text/javascript">
    <!--
    
    var $supplierid = $('#oa-supplierid-oaSupplierPayViewPage');
    var $suppliername = $('#oa-suppliername-oaSupplierPayViewPage');
    var $paybank = $('#oa-paybank-oaSupplierPayViewPage');
    var $payamount = $('#oa-payamount-oaSupplierPayViewPage');
    var $upperpayamount = $('#oa-upperpayamount-oaSupplierPayViewPage');
    var $comments = $('#oa-comments-oaSupplierPayViewPage');
    var $comments2 = $('#oa-comments2-oaSupplierPayViewPage');
    var $attach = $('#oa-attach-oaSupplierPayViewPage');
        
    // 金额
    var $advanceamount = $('#oa-advanceamount-oaSupplierPayViewPage');			// 预付金额
    var $stockamount = $('#oa-stockamount-oaSupplierPayViewPage');				// 库存金额
    var $receivableamount = $('#oa-receivableamount-oaSupplierPayViewPage');	// 应收金额
    var $actingmatamount = $('#oa-actingmatamount-oaSupplierPayViewPage');		// 代垫金额
    var $payableamount = $('#oa-payableamount-oaSupplierPayViewPage');			// 应付金额
    var $totalamount = $('#oa-totalamount-oaSupplierPayViewPage');				// 合计占用
    
    var $arrivalamount = $('#oa-arrivalamount-oaSupplierPayViewPage');			// 到货金额
    var $payamount = $('#oa-payamount-oaSupplierPayViewPage');					// 付款金额
    var $expenseamount = $('#oa-expenseamount-oaSupplierPayViewPage');			// 收回费用
    var $paymargin = $('#oa-paymargin-oaSupplierPayViewPage');					// 付款差额

    var $form = $('#oa-form-oaSupplierPayViewPage');
    
    $(function() {
    
    });

	-->
	</script>
  </body>
</html>