<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>费用通路单申请单查看页面</title>
    <%@include file="/include.jsp" %>
</head>

<body>
    <div class="easyui-panel" data-options="fit:true,border:false">
        <div class="easyui-layout" data-options="fit:true,border:false">
            <form action="oa/hd/editOaAccess.do" id="oa-form-oaAccessViewPage" method="post">
                <input type="hidden" name="access.id" id="oa-id-oaAccessViewPage" value="${param.id }"/>
                <input type="hidden" name="access.oaid" id="oa-oaid-oaAccessViewPage" value="${param.oaid }"/>
                <input type="hidden" name="goodsprice" id="oa-goodsprice-oaAccessViewPage" value=""/>
                <input type="hidden" name="goodsamount" id="oa-goodsamount-oaAccessViewPage" value=""/>
                <input type="hidden" name="branddiscount" id="oa-branddiscount-oaAccessViewPage" value=""/>
                <div data-options="region:'center',border:false">
                    <div style="margin: 20px auto; width: 840px; border: 1px solid #AAA;">
                        <process:definitionHeader process="${process}"/>
                        <div class="easyui-panel" data-options="border:false" title="第一确认：工厂">
                            <div class="easyui-panel" data-options="border:false">
                                <table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
                                    <tr>
                                        <td class="left len80">供应商：</td>
                                        <td class="len200"><input class="easyui-validatebox" style="width: 193px;" name="access.supplierid" value="${access.supplierid }" id="oa-supplierid-oaAccessViewPage" autocomplete="off" data-options="required:false"/></td>
                                        <td class="left">计划时间段：</td>
                                        <td class="left len200">
                                            <input class="easyui-validatebox len90 Wdate" name="access.planbegindate" value="${access.planbegindate }" id="oa-planbegindate-oaAccessViewPage" style=" margin-left: 0px;" autocomplete="off" readonly="readonly"/>～<input class="easyui-validatebox len90 Wdate" value="${access.planenddate }" name="access.planenddate" id="oa-planenddate-oaAccessViewPage" style=" margin-left: 0px;" autocomplete="off" data-options="required:false" readonly="readonly"/>
                                        </td>
                                        <td class="left len80">确认单号：</td>
                                        <td class="len150"><input class="easyui-validatebox len130" name="access.confirmid" value="${access.confirmid }" id="oa-confirmid-oaAccessViewPage" autocomplete="off" maxlength="20" readonly="readonly"/></td>
                                    </tr>
                                    <tr>
                                        <td class="left">申请通路费：</td>
                                        <td class="">
                                            <input class="easyui-validatebox len170" name="access.expensesort" id="oa-expensesort-oaAccessViewPage" value="${access.expensesort }" autocomplete="off"/>
                                        </td>
                                        <td class="left">申请特价：</td>
                                        <td>
                                            <c:choose>
                                            <c:when test="${empty access.pricetype and empty access.expensesort}">
                                            <select name="access.pricetype" id="oa-pricetype-oaAccessViewPage" class="easyui-validatebox len192" data-options="required:false,missingMessage:'通路费和特价至少应选择一个！'" data="${access.pricetype }" disabled="disabled">
                                                </c:when>
                                                <c:otherwise>
                                                <select name="access.pricetype" id="oa-pricetype-oaAccessViewPage" class="easyui-validatebox len192" data-options="required:false,missingMessage:'通路费和特价至少应选择一个！'" data="${access.pricetype }" disabled="disabled">
                                                    </c:otherwise>
                                                    </c:choose>
                                                    <option></option>
                                                    <option value="1">补差特价</option>
                                                    <option value="2">降价特价</option>
                                                </select>
                                        </td>
                                        <td class="left">补库存：</td>
                                        <td class="">
                                            <c:choose>
                                            <c:when test="${empty access.isaddstorage }">
                                            <select name="access.isaddstorage" id="oa-isaddstorage-oaAccessViewPage" class="len130" data="0" disabled="disabled">
                                                </c:when>
                                                <c:otherwise>
                                                <select name="access.isaddstorage" id="oa-isaddstorage-oaAccessViewPage" class="len130" data="${access.isaddstorage }" disabled="disabled">
                                                    </c:otherwise>
                                                    </c:choose>
                                                    <option value="1">是</option>
                                                    <option value="0" selected="selected">否</option>
                                                </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="left">客户：</td>
                                        <td class=""><input class="easyui-validatebox" style="width: 193px;" name="access.customerid" id="oa-customerid-oaAccessViewPage" value="${access.customerid }" autocomplete="off" data-options="required:false"/></td>
                                        <td class="left">执行地点：</td>
                                        <td class="" colspan="3"><input class="easyui-validatebox" style="width: 419px;" name="access.executeaddr" id="oa-executeaddr-oaAccessViewPage" value="${access.executeaddr }" autocomplete="off" readonly="readonly"/></td>
                                    </tr>
                                </table>
                            </div>
                            <!-- 商品明细一览 -->
                            <div class="easyui-panel" data-options="border:true" style="height: 165px; border-top: solid 1px #aaa; border-bottom: solid 1px #aaa;"><!-- style="height: 200px; border-top: solid 1px #aaa; border-bottom: solid 1px #aaa;" -->
                                <table id="oa-datagrid-goodsprice-oaAccessViewPage">
                                    <tr><td></td></tr>
                                    <tr><td></td></tr>
                                    <tr><td></td></tr>
                                    <tr><td></td></tr>
                                    <tr><td></td></tr>
                                </table>
                            </div>
                            <div class="easyui-panel" data-options="border: false">
                                <table>
                                    <tr>
                                        <td class="left len80">自理金额：</td>
                                        <td class="len200"><input class="easyui-numberbox len173" style="width: 173px;" name="access.myamount" id="oa-myamount-oaAccessViewPage" value="${access.myamount }" autocomplete="off" data-options="precision:2" readonly="readonly"/></td>
                                        <td class="left len80">收回方式：</td>
                                        <td class="len200">
                                            <select class="easyui-validatebox len173" name="access.reimbursetype" id="oa-reimbursetype-oaAccessViewPage" data="${access.reimbursetype }" data-options="required:false" disabled="disabled">
                                                <option></option>
                                                <c:forEach items="${typelist }" var="type">
                                                    <option value="${type.code }"><c:out value="${type.codename }"></c:out></option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td class="left len80">收回日期：</td>
                                        <td class="len200"><input class="easyui-validatebox Wdate" name="access.reimbursedate" value="${access.reimbursedate }" id="oa-reimbursedate-oaAccessViewPage" style=" margin-left: 0px; width: 145px;" autocomplete="off" readonly="readonly"/></td>
                                    </tr>
                                    <tr>
                                        <td class="left">说明：</td>
                                        <td colspan="9">
                                            <textarea name="access.remark1" id="oa-remark1-oaAccessViewPage" style="width: 705px; height: 50px; resize: none" readonly="readonly"><c:out value="${access.remark1 }" escapeXml="true"></c:out></textarea>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div id="oa-container-comments1-oaAccessViewPage" class="easyui-panel" data-options="border: false">
                                <div id="oa-comments1-oaAccessViewPage">
                                </div>
                            </div>
                        </div>
                        <div class="easyui-panel" data-options="border:false" title="第二确认：客户（品牌经理根据客户促销协议核对无误后确认提交）">
                            <div class="easyui-panel" data-options="border: false">
                                <table>
                                    <tr>
                                        <td class="left len60 condate">确认时间：</td>
                                        <td class="len220 condate">
                                            <input class="easyui-validatebox Wdate len100" name="access.conbegindate" id="oa-conbegindate-oaAccessViewPage" onclick="" style=" margin-left: 0px;" autocomplete="off" value="${access.conbegindate }" readonly="readonly"/>～<input class="easyui-validatebox Wdate len100" name="access.conenddate" id="oa-conenddate-oaAccessViewPage" onclick="" style=" margin-left: 0px;" autocomplete="off" value="${access.conenddate }" readonly="readonly"/>
                                        </td>
                                        <td class="left len90 comdate">降价设定时间：</td>
                                        <td class="len220 comdate">
                                            <input class="easyui-validatebox Wdate len100" name="access.combegindate" id="oa-combegindate-oaAccessViewPage" onclick="" style=" margin-left: 0px;" autocomplete="off" value="${access.combegindate }" readonly="readonly"/>～<input class="easyui-validatebox Wdate len100" name="access.comenddate" id="oa-comenddate-oaAccessViewPage" onclick="" style=" margin-left: 0px;" autocomplete="off" value="${access.comenddate }" readonly="readonly"/>
                                        </td>
                                        <td class="right len70">支付方式：</td>
                                        <td class="left">
                                            <select class="easyui-validatebox len100" name="access.paytype" id="oa-paytype-oaAccessViewPage" data="${access.paytype }" data-options="" disabled="disabled">
                                                <option></option>
                                                <option value="1">折扣</option>
                                                <option value="2">支票</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="left">费用金额：</td>
                                        <td class="left"><input class="easyui-numberbox" style="width: 212px;" name="access.totalamount" id="oa-totalamount-oaAccessViewPage" value="${access.totalamount }" autocomplete="off" data-options="required:false, precision:2" readonly="readonly"/></td>
                                        <td class="left">支付日期：</td>
                                        <td class=""><input class="easyui-validatebox Wdate len100" name="access.paydate" value="${access.paydate }" id="oa-paydate-oaAccessViewPage" autocomplete="off" readonly="readonly"/></td>
                                    </tr>
                                </table>
                            </div>
                            <div class="easyui-panel" data-options="border:true" style="height: 165px; border-top: solid 1px #aaa; border-bottom: solid 1px #aaa;"><!-- style="height: 200px; border-top: solid 1px #aaa; border-bottom: solid 1px #aaa;" -->
                                <table id="oa-datagrid-goodsamount-oaAccessViewPage">
                                    <tr><td></td></tr>
                                    <tr><td></td></tr>
                                    <tr><td></td></tr>
                                    <tr><td></td></tr>
                                    <tr><td></td></tr>
                                </table>
                            </div>
                            <div class="easyui-panel" data-options="border: false">
                                <table>
                                    <tr>
                                        <td class="left len60">说明：</td>
                                        <td><textarea name="access.remark2" id="oa-remark2-oaAccessViewPage" style="width: 711px; height: 50px; resize: none" readonly="readonly"><c:out value="${access.remark2 }" escapeXml="true"></c:out></textarea></td>
                                    </tr>
                                </table>
                            </div>
                            <div id="oa-container-comments2-oaAccessViewPage" class="easyui-panel" data-options="border: false">
                                <div id="oa-comments2-oaAccessViewPage">
                                </div>
                            </div>
                        </div>
                        <div class="easyui-panel" data-options="border:false" title="第三确认：支付信息">
                        <div class="easyui-panel" data-options="border: false">
                            <table>
                                <tr>
                                    <td class="len90 left">电脑冲差金额：</td>
                                    <td class="len108"><input class="easyui-numberbox" style="width: 90px;" name="access.compdiscount" id="oa-compdiscount-oaAccessViewPage" value="${access.compdiscount }" autocomplete="off" data-options="required:false,precision:2" readonly="readonly"/></td>
                                    <td class="len90 right">电脑降价金额：</td>
                                    <td class="len108"><input class="easyui-numberbox" style="width: 90px;" name="access.comdownamount" id="oa-comdownamount-oaAccessViewPage" value="${access.comdownamount }" autocomplete="off" data-options="required:false,precision:2" readonly="readonly"/></td>
                                    <td class="len80 right">支票金额：</td>
                                    <td class="len108"><input class="easyui-numberbox" style="width: 90px;" name="access.payamount" id="oa-payamount-oaAccessViewPage" value="${access.payamount }" autocomplete="off" data-options="required:false,precision:2,onChange:computeBranchaccount" readonly="readonly"/></td>
                                    <td class="len80 right">结算金额：</td>
                                    <td class="len108"><input class="easyui-numberbox" style="width: 90px;" name="access.branchaccount" id="oa-branchaccount-oaAccessViewPage" value="${access.branchaccount }" autocomplete="off" data-options="required:false,precision:2" readonly="readonly"/></td>
                                </tr>
                            </table>
                        </div>
                        <div id="oa-container-comments5-oaAccessViewPage" class="easyui-panel" data-options="border: false">
                            <div id="oa-comments5-oaAccessViewPage">
                            </div>
                        </div>
                        <div id="oa-invoice-oaAccessViewPage" class="easyui-panel" data-options="border: false" title="支票支付">
                            <table>
                                <tr>
                                    <td class="left len85">收款单位编码：</td>
                                    <td class="len80"><input class="easyui-validatebox len80" name="invoice.companyid" id="oa-companyid-oaAccessViewPage" value="${invoice.companyid }" autocomplete="off" data-options="required:false" readonly="readonly"/></td>
                                    <td class="left len60">收款单位：</td>
                                    <td class="len140"><input class="easyui-validatebox len130" name="invoice.companyname" id="oa-companyname-oaAccessViewPage" value="${invoice.companyname }" autocomplete="off" data-options="required:false" readonly="readonly"/></td>
                                    <td class="left len60">开户银行：</td>
                                    <td class="len100"><input class="easyui-validatebox len90" name="invoice.bank" id="oa-bank-oaAccessViewPage" value="${invoice.bank }" autocomplete="off" data-options="required:false" readonly="readonly"/></td>
                                    <td class="left len60">银行账号：</td>
                                    <td class="len136"><input class="easyui-validatebox len165" name="invoice.bankno" id="oa-bankno-oaAccessViewPage" value="${invoice.bankno }" autocomplete="off" data-options="required:false" readonly="readonly"/></td>
                                </tr>
                                <tr>
                                    <td class="left">付款金额：</td>
                                    <td class=""><input class="easyui-numberbox len80" name="invoice.payamount" id="oa-payamount2-oaAccessViewPage" value="${invoice.payamount }" autocomplete="off" data-options="required:false,precision:2,onChange:upperPayamount" readonly="readonly"/></td>
                                    <td class="left">金额大写：</td>
                                    <td class=""><input class="easyui-validatebox len130" name="invoice.amountwords" id="oa-amountwords-oaAccessViewPage" value="${invoice.amountwords }" autocomplete="off" data-options="required:false" readonly="readonly"/></td>
                                    <td class="left">费用项目：</td>
                                    <td class=""><input class="easyui-validatebox len90" name="invoice.expensesort" id="oa-expensesort2-oaAccessViewPage" value="${invoice.expensesort }" autocomplete="off" data-options="required:false" readonly="readonly"/></td>
                                    <td class="left">发票种类：</td>
                                    <td class="">
                                        <select class="easyui-validatebox len165" name="invoice.invoicetype" id="oa-invoicetype-oaAccessViewPage" data="${invoice.invoicetype }" autocomplete="off" data-options="required:false" disabled="disabled">
                                            <c:forEach items="${invoicetype }" var="item">
                                                <option value="${item.code }"><c:out value="${item.codename }"></c:out></option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="left">到发票日期：</td>
                                    <td class=""><input class="easyui-validatebox Wdate len80" name="invoice.invoicedate" id="oa-invoicedate-oaAccessViewPage" value="${invoice.invoicedate }" onclick="" style=" margin-left: 0px;" autocomplete="off" readonly="readonly"/></td>
                                    <td class="left">付款日期：</td>
                                    <td class=""><input class="easyui-validatebox Wdate len130" name="invoice.paydate" id="oa-paydate2-oaAccessViewPage" value="${invoice.paydate }" onclick="" style=" margin-left: 0px;" autocomplete="off" readonly="readonly"/></td>
                                </tr>
                            </table>
                        </div>
                        <div id="oa-container-comments4-oaAccessViewPage" class="easyui-panel" data-options="border: false">
                            <div id="oa-comments4-oaAccessViewPage">
                            </div>
                        </div>
                        <div id="oa-discount-oaAccessViewPage" class="easyui-panel" data-options="border: false" title="票扣折让">
                            <table id="oa-datagrid-branddiscount-oaAccessViewPage">
                                <tr><td></td></tr>
                                <tr><td></td></tr>
                                <tr><td></td></tr>
                                <tr><td></td></tr>
                                <tr><td></td></tr>
                            </table>
                        </div>
                        <div id="oa-container-comments3-oaAccessViewPage" class="easyui-panel" data-options="border: false">
                            <div id="oa-comments3-oaAccessViewPage">
                            </div>
                        </div>
                        <div>
                            <div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
                            <div id="oa-attach-oaAccessViewPage" style="width: 786px;">
                            </div>
                        </div>
                        <div>
                            <div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
                            <div id="oa-comments-oaAccessViewPage" style="width: 790px;">
                            </div>
                        </div>
                    </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
<script type="text/javascript">
    <!--

    // 项目
    // DataGrid定义
    var $goodsprice = $('#oa-datagrid-goodsprice-oaAccessViewPage');
    var $goodsamount = $('#oa-datagrid-goodsamount-oaAccessViewPage');
    var $branddiscount = $('#oa-datagrid-branddiscount-oaAccessViewPage');
    var $result = $('#oa-result-oaAccessViewPage');

    // 项目
    var $supplierid = $('#oa-supplierid-oaAccessViewPage');
    var $expensesort = $('#oa-expensesort-oaAccessViewPage');
    var $pricetype = $('#oa-pricetype-oaAccessViewPage');
    var $customerid = $('#oa-customerid-oaAccessViewPage');
    var $salesarea = $('#oa-salesarea-oaAccessViewPage');
    var $totalamount = $('#oa-totalamount-oaAccessViewPage');
    var $totalnum = $('#oa-totalnum-oaAccessViewPage');
    var $payamount = $('#oa-payamount-oaAccessViewPage');
    var $paytype = $('#oa-paytype-oaAccessViewPage');
    var $executeaddr = $('#oa-executeaddr-oaAccessViewPage');

    var $factoryamount = $('#oa-factoryamount-oaAccessViewPage');	// 工厂金额
    var $myamount = $('#oa-myamount-oaAccessViewPage');	// 自理金额
    // var $payamount = $('#oa-payamount-oaAccessViewPage');
    var $branchaccount = $('#oa-branchaccount-oaAccessViewPage');	// 结算金额

    var $compdiscount = $('#oa-compdiscount-oaAccessViewPage');	// 电脑冲差金额
    var $comdownamount = $('#oa-comdownamount-oaAccessViewPage');	// 电脑降价金额

    var $planbegindate = $('#oa-planbegindate-oaAccessViewPage');	// 计划开始时间
    var $planenddate = $('#oa-planenddate-oaAccessViewPage');	// 计划结束时间
    var $conbegindate = $('#oa-conbegindate-oaAccessViewPage');	// 确认开始时间
    var $conenddate = $('#oa-conenddate-oaAccessViewPage');	// 确认结束时间
    var $combegindate = $('#oa-combegindate-oaAccessViewPage');	// 电脑设定开始时间
    var $comenddate = $('#oa-comenddate-oaAccessViewPage');	// 电脑设定结束时间

    var $goodspricelist = $('#oa-goodsprice-oaAccessViewPage');
    var $goodsamountlist = $('#oa-goodsamount-oaAccessViewPage');
    var $branddiscountlist = $('#oa-branddiscount-oaAccessViewPage');

    var $accessform = $('#oa-form-oaAccessViewPage');

    var $attach = $('#oa-attach-oaAccessViewPage');
    var $comments = $('#oa-comments-oaAccessViewPage');

    // 支票、折扣切换
    var $discount = $('#oa-discount-oaAccessViewPage');
    var $invoice = $('#oa-invoice-oaAccessViewPage');

    // 审批信息
    var $comment1 =	$('#oa-comments1-oaAccessViewPage');
    var $comment2 =	$('#oa-comments2-oaAccessViewPage');
    var $comment3 =	$('#oa-comments3-oaAccessViewPage');
    var $comment4 =	$('#oa-comments4-oaAccessViewPage');
    var $comment5 =	$('#oa-comments5-oaAccessViewPage');
    var $comment1container = $('#oa-container-comments1-oaAccessViewPage');
    var $comment2container = $('#oa-container-comments2-oaAccessViewPage');
    var $comment3container = $('#oa-container-comments3-oaAccessViewPage');
    var $comment4container = $('#oa-container-comments4-oaAccessViewPage');
    var $comment5container = $('#oa-container-comments5-oaAccessViewPage');

    var $companyname = $('#oa-companyname-oaAccessViewPage');
    var $companyid = $('#oa-companyid-oaAccessViewPage');
    var $bank = $('#oa-bank-oaAccessViewPage');
    var $bankno = $('#oa-bankno-oaAccessViewPage');
    var $payamount_i = $('#oa-payamount-oaAccessViewPage[name="invoice.payamount"]');
    var $amountwords = $('#oa-amountwords-oaAccessViewPage');
    var $expensesort_i = $('#oa-expensesort-oaAccessViewPage[name="invoice.expensesort"]');
    var $invoicetype = $('#oa-invoicetype-oaAccessViewPage');

    $(function(){

    });

    -->
</script>
</body>
</html>