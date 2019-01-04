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
					<form action="oa/addOaSupplierPay.do" id="oa-form-oaSupplierPayHandlePage" method="post">
				</c:when>
				<c:otherwise>
					<form action="oa/editOaSupplierPay.do" id="oa-form-oaSupplierPayHandlePage" method="post">
				</c:otherwise>
			</c:choose>
				<input type="hidden" name="pay.id" id="oa-id-oaSupplierPayHandlePage" value="${pay.id }"/>
				<input type="hidden" name="pay.oaid" id="oa-oaid-oaSupplierPayHandlePage" value="${param.processid }"/>
				<input type="hidden" id="oa-suppliername-oaSupplierPayHandlePage" value="${supplier.name }"/>
				<div data-options="region:'center',border:false">
					<div style="margin: 20px auto; width: 790px; border: 1px solid #AAA;">
                        <process:definitionHeader process="${process}"/>
                        <div class="easyui-panel" data-options="border:false">
                            <table>
                                <tr>
                                    <td class="len80 left">供应商：</td>
                                    <td class="len180"><input class="easyui-validatebox len140" name="pay.supplierid" id="oa-supplierid-oaSupplierPayHandlePage" data-options="required:true" value="${pay.supplierid }" autocomplete="off" maxlength="10"/><font color="#F00">*</font></td>
                                    <td class="len80 left">收款银行：</td>
                                    <td class="len180"><input class="easyui-validatebox len140" name="pay.collectionbank" id="oa-collectionbank-oaSupplierPayHandlePage" data-options="required:false" value="${pay.collectionbank }" autocomplete="off" maxlength="100"/></td>
                                    <td class="len80 left">银行账号：</td>
                                    <td class="len180"><input class="easyui-validatebox len140" name="pay.collectionbankno" id="oa-collectionbankno-oaSupplierPayHandlePage" data-options="required:false" value="${pay.collectionbankno }" autocomplete="off" maxlength="50"/></td>
                                </tr>
                                <tr>
                                    <td class="left">付款金额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.payamount" id="oa-payamount-oaSupplierPayHandlePage" data-options="required:true,precision:2,onChange:computePayMargin" value="${pay.payamount }" autocomplete="off" maxlength="15"/><font color="#F00">*</font></td>
                                    <td class="left">大写金额：</td>
                                    <td class=""><input class="easyui-validatebox len140" name="pay.upperpayamount" id="oa-upperpayamount-oaSupplierPayHandlePage" data-options="required:false" value="${pay.upperpayamount }" autocomplete="off" maxlength="50"/></td>
                                    <td class="left">付款日期：</td>
                                    <td class=""><input class="easyui-validatebox len140 Wdate" name="pay.paydate" id="oa-paydate-oaSupplierPayHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${pay.paydate }" autocomplete="off" maxlength="10"/></td>
                                </tr>
                                <tr>
                                    <td class="left">付款银行：</td>
                                    <td class=""><input class="easyui-validatebox len140" name="pay.paybank" id="oa-paybank-oaSupplierPayHandlePage" data-options="required:true" value="${pay.paybank }" autocomplete="off" maxlength="20"/><font color="#F00">*</font></td>
                                    <td class="left">到货金额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.arrivalamount" id="oa-arrivalamount-oaSupplierPayHandlePage" data-options="required:false,precision:2,onChange:computePayMargin" value="${pay.arrivalamount }" autocomplete="off" maxlength="18"/></td>
                                    <td class="left">付款差额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.paymargin" id="oa-paymargin-oaSupplierPayHandlePage" data-options="precision:2" value="${pay.paymargin }" autocomplete="off" maxlength="18"/></td>
                                </tr>
                                <tr>
                                    <td class="left">到货日期：</td>
                                    <td class=""><input class="easyui-validatebox len140 Wdate" name="pay.arrivaldate" id="oa-arrivaldate-oaSupplierPayHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:false" value="${pay.arrivaldate }" autocomplete="off" maxlength="10"/></td>
                                    <td class="left">发票金额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.billamount" id="oa-billamount-oaSupplierPayHandlePage" data-options="required:false,precision:2" value="${pay.billamount }" autocomplete="off" maxlength="18"/></td>
                                    <td class="left">收回费用：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.expenseamount" id="oa-expenseamount-oaSupplierPayHandlePage" data-options="precision:2,onChange:computePayMargin" value="${pay.expenseamount }" autocomplete="off" maxlength="18"/></td>
                                </tr>
                                <tr>
                                    <td class="left">抽单金额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.writeoffamount" id="oa-writeoffamount-oaSupplierPayHandlePage" data-options="precision:2" value="${pay.writeoffamount }" autocomplete="off" maxlength="18"/></td>
                                    <td class="left">抽单日期：</td>
                                    <td class=""><input class="easyui-validatebox len140 Wdate" name="pay.writeoffdate" id="oa-writeoffdate-oaSupplierPayHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:false" value="${pay.writeoffdate }" autocomplete="off" maxlength="10"/></td>
                                    <td class="left">订单金额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.orderamount" id="oa-orderamount-oaSupplierPayHandlePage" data-options="required:true,precision:2" value="${pay.orderamount }" autocomplete="off" maxlength="18"/><font color="#F00">*</font></td>
                                </tr>
                                <tr>
                                    <td class="left">预付金额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" style="" name="pay.advanceamount" id="oa-advanceamount-oaSupplierPayHandlePage" data-options="precision:2,disabled:true" value="${pay.advanceamount }" autocomplete="off" maxlength="18" readonly="readonly"/></td>
                                    <td class="left">库存金额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" style="" name="pay.stockamount" id="oa-stockamount-oaSupplierPayHandlePage" data-options="precision:2,disabled:true" value="${pay.stockamount }" autocomplete="off" maxlength="18" readonly="readonly"/></td>
                                    <td class="left">应收金额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" style="" name="pay.receivableamount" id="oa-receivableamount-oaSupplierPayHandlePage" data-options="precision:2,disabled:true" value="${pay.receivableamount }" autocomplete="off" maxlength="18" readonly="readonly"/></td>
                                </tr>
                                <tr>
                                    <td class="left">代垫金额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" style="" name="pay.actingmatamount" id="oa-actingmatamount-oaSupplierPayHandlePage" data-options="precision:2,disabled:true" value="${pay.actingmatamount }" autocomplete="off" maxlength="18" readonly="readonly"/></td>
                                    <td class="left">应付金额：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" style="" name="pay.payableamount" id="oa-payableamount-oaSupplierPayHandlePage" data-options="precision:2,disabled:true" value="${pay.payableamount }" autocomplete="off" maxlength="18" readonly="readonly"/></td>
                                    <td class="left">合计占用：</td>
                                    <td class=""><input class="easyui-numberbox" style="width: 140px;" style="" name="pay.totalamount" id="oa-totalamount-oaSupplierPayHandlePage" data-options="precision:2,disabled:true" value="${pay.totalamount }" autocomplete="off" maxlength="18" readonly="readonly"/></td>
                                </tr>
                                <tr>
                                    <td class="left">备注：</td>
                                    <td class="" colspan="5">
                                        <textarea style="width: 668px; height: 50px; resize: none;"name="pay.remark" id="oa-remark-oaSupplierPayHandlePage" maxlength="165"><c:out value="${pay.remark }"></c:out></textarea>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="easyui-panel" data-options="border:false">
                            <div id="oa-comments-oaSupplierPayHandlePage">
                            </div>
                        </div>
                        <div style="border-top: 1px solid #AAA;">&nbsp;</div>
                        <div>
                            <div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
                            <div id="oa-attach-oaSupplierPayHandlePage" style="width: 765px;">
                            </div>
                        </div>
                        <div>
                            <div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
                            <div id="oa-comments2-oaSupplierPayHandlePage" style="width: 760px;">
                            </div>
                        </div>
					</div>
				</div>
			</form>
		</div>
	</div>
    <script type="text/javascript">
    <!--
    
    var $supplierid = $('#oa-supplierid-oaSupplierPayHandlePage');
    var $suppliername = $('#oa-suppliername-oaSupplierPayHandlePage');
    var $paybank = $('#oa-paybank-oaSupplierPayHandlePage');
    var $payamount = $('#oa-payamount-oaSupplierPayHandlePage');
    var $upperpayamount = $('#oa-upperpayamount-oaSupplierPayHandlePage');
    var $comments = $('#oa-comments-oaSupplierPayHandlePage');
    var $comments2 = $('#oa-comments2-oaSupplierPayHandlePage');
    var $attach = $('#oa-attach-oaSupplierPayHandlePage');
    
    // 金额
    var $advanceamount = $('#oa-advanceamount-oaSupplierPayHandlePage');		// 预付金额
    var $stockamount = $('#oa-stockamount-oaSupplierPayHandlePage');			// 库存金额
    var $receivableamount = $('#oa-receivableamount-oaSupplierPayHandlePage');	// 应收金额
    var $actingmatamount = $('#oa-actingmatamount-oaSupplierPayHandlePage');	// 代垫金额
    var $payableamount = $('#oa-payableamount-oaSupplierPayHandlePage');		// 应付金额
    var $totalamount = $('#oa-totalamount-oaSupplierPayHandlePage');			// 合计占用
    
    var $collectionbank = $('#oa-collectionbank-oaSupplierPayHandlePage');
    var $collectionbankno = $('#oa-collectionbankno-oaSupplierPayHandlePage');
    
    var $arrivalamount = $('#oa-arrivalamount-oaSupplierPayHandlePage');		// 到货金额
    var $payamount = $('#oa-payamount-oaSupplierPayHandlePage');				// 付款金额
    var $expenseamount = $('#oa-expenseamount-oaSupplierPayHandlePage');		// 收回费用
    var $paymargin = $('#oa-paymargin-oaSupplierPayHandlePage');				// 付款差额
    
    var $form = $('#oa-form-oaSupplierPayHandlePage');
    
    $(function() {
    
    });

	-->
	</script>
  </body>
</html>