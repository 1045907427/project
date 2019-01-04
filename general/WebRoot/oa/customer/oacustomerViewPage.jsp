<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<title>新客户联络票查看页面</title>
	<%@include file="/include.jsp" %>
  </head>
  
  <body>
	<div class="easyui-panel" data-options="fit:true,border:false">
        <c:choose>
            <c:when test="${empty customer or empty customer.id}">
                <form action="oa/addOaCustomer.do" id="oa-form-oacustomerViewPage" method="post">
            </c:when>
            <c:otherwise>
                <form action="oa/editOaCustomer.do" id="oa-form-oacustomerViewPage" method="post">
            </c:otherwise>
        </c:choose>
                <input type="hidden" name="customer.id" id="id" value="<c:out value='${customer.id }' escapeXml='true'></c:out>"/>
                <div style="margin: 20px auto; width: 840px; border: 1px solid #AAA;">
                    <process:definitionHeader process="${process}"/>
                    <div class="easyui-panel" data-options="border:false" title="客户基本信息">
                        <table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
                            <tr>
                                <td class="left len100">客户编码：</td>
                                <td class="len180"><input class="easyui-validatebox len130" style="margin-left: 0px;" name="customer.customerid" id="oa-id-oacustomerViewPage" readonly="readonly" value="<c:out value='${customer.customerid }' escapeXml='true'></c:out>"/></td>
                                <td class="right len70">客户名称：</td>
                                <td class="len180" colspan="3"><input class="easyui-validatebox len130" style="margin-left: 0px; width: 390px;" name="customer.customername" id="oa-name-oacustomerViewPage" value="<c:out value='${customer.customername }' escapeXml='true'></c:out>" autocomplete="off" data-options="required:false" maxlength="100" readonly="readonly"/></td>
                            </tr>
                            <tr>
                                <td class="left len70">上级客户：</td>
                                <td class="len180"><input class="easyui-validatebox len130" style="margin-left: 0px;" name="customer.pcustomerid" id="oa-pcustomerid-oacustomerViewPage" value="<c:out value='${customer.pcustomerid }' escapeXml='true'></c:out>" autocomplete="off" data-options="required:false" maxlength="20" readonly="readonly"/></td>
                                <td class="right len70">助记符：</td>
                                <td class="len180"><input class="easyui-validatebox len130" style="margin-left: 0px;" name="customer.shortcode" id="oa-shortcode-oacustomerViewPage" value="<c:out value='${customer.shortcode }' escapeXml='true'></c:out>" autocomplete="off" data-options="required:false" maxlength="20" readonly="readonly"/></td>
                                <td class="right len70">营业执照号：</td>
                                <td class="len180"><input class="easyui-validatebox len130" style="margin-left: 0px;" name="customer.licenseno" id="oa-licenseno-oacustomerViewPage" value="<c:out value='${customer.licenseno }' escapeXml='true'></c:out>" autocomplete="off" data-options="required:false" maxlength="15" readonly="readonly"/></td>
                            </tr>
                            <tr>
                                <td class="left">销售部门：</td>
                                <td class=""><input class="easyui-validatebox len130" style="margin-left: 0px;" name="customer.salesdeptid" id="oa-salesdeptid-oacustomerViewPage" value="<c:out value='${customer.salesdeptid }' escapeXml='true'></c:out>" readonly="readonly"/></td>
                                <%--
                                <td class="left">客户简称：</td>
                                <td class=""><input class="easyui-validatebox len130" style="margin-left: 0px;" name="customer.shortname" id="oa-shortname-oacustomerViewPage" value="<c:out value='${customer.shortname }' escapeXml='true'></c:out>" readonly="readonly"/></td>
                                --%>
                                <td class="right">联系人：</td>
                                <td class=""><input class="easyui-validatebox len130" style="margin-left: 0px;" name="customer.contact" id="oa-contact-oacustomerViewPage" value="<c:out value='${customer.contact }' escapeXml='true'></c:out>" readonly="readonly"/></td>
                                <td class="right">联系电话：</td>
                                <td class=""><input class="easyui-validatebox len130" style="margin-left: 0px;" name="customer.mobile" id="oa-mobile-oacustomerViewPage" value="<c:out value='${customer.mobile }' escapeXml='true'></c:out>" readonly="readonly"/></td>
                            </tr>
                            <tr>
                                <td class="left">详细地址：</td>
                                <td class=""><input class="easyui-validatebox len130" style="margin-left: 0px;" name="customer.address" id="oa-address-oacustomerViewPage"value="<c:out value='${customer.address }' escapeXml='true'></c:out>" readonly="readonly"/></td>
                                <td class="right">注册资金：</td>
                                <td class=""><input class="easyui-validatebox easyui-numberbox len130" style="margin-left: 0px; width: 130px;" data-options="max:999999999999,precision:0" name="customer.fund" id="oa-fund-oacustomerViewPage" value="<c:out value='${customer.fund }' escapeXml='true'></c:out>" readonly="readonly"/></td>
                                <td class="right">门店面积：</td>
                                <td class=""><input class="easyui-validatebox easyui-numberbox len130" style="margin-left: 0px; width: 130px;" data-options="max:999999999999,precision:0" name="customer.storearea" id="oa-storearea-oacustomerViewPage" value="<c:out value='${customer.storearea }' escapeXml='true'></c:out>" readonly="readonly"/></td>
                            </tr>
                            <tr>
                                <td class="left">所属区域：</td>
                                <td class=""><input class="easyui-validatebox len130" style="margin-left: 0px;" name="customer.salesarea" id="oa-salesarea-oacustomerViewPage" value="<c:out value='${customer.salesarea }' escapeXml='true'></c:out>" readonly="readonly"/></td>
                                <td class="right">所属分类：</td>
                                <td class=""><input class="easyui-validatebox len130" style="margin-left: 0px;" name="customer.customersort" id="oa-customersort-oacustomerViewPage" value="<c:out value='${customer.customersort }' escapeXml='true'></c:out>" readonly="readonly"/></td>
                                <td class="right">默认价格套：</td>
                                <td class="">
                                    <select name="customer.pricesort" class="easyui-validatebox len130" style="margin-left: 0px;">
                                        <option></option>
                                        <c:forEach items="${priceList}" var="list">
                                            <c:choose>
                                                <c:when test="${list.code == customer.pricesort }"><option value="<c:out value='${list.code }' escapeXml='true'></c:out>" selected="selected">${list.codename }</option></c:when>
                                                <c:otherwise><option value="<c:out value='${list.code }' escapeXml='true'></c:out>">${list.codename }</option></c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td class="left">客户业务员：</td>
                                <td class=""><input class="easyui-validatebox len130" style="margin-left: 0px;" name="customer.salesuserid" id="oa-salesuserid-oacustomerViewPage" value="<c:out value='${customer.salesuserid }' escapeXml='true'></c:out>" readonly="readonly"/></td>
                                <td class="right">收款人：</td>
                                <td class=""><input class="easyui-validatebox len130" style="margin-left: 0px;" name="customer.payeeid" id="oa-payeeid-oacustomerViewPage" value="<c:out value='${customer.payeeid }' escapeXml='true'></c:out>"/></td>
                                <td class="right">默认内勤：</td>
                                <td class=""><input class="easyui-validatebox len130" style="margin-left: 0px;" name="customer.indoorstaff" id="oa-indoorstaff-oacustomerViewPage" value="<c:out value='${customer.indoorstaff }' escapeXml='true'></c:out>"/></td>
                            </tr>
                            <tr>
                                <td class="left">促销分类：</td>
                                <td class="">
                                    <select name="customer.promotionsort" id="oa-promotionsort-oacustomerViewPage" class="easyui-validatebox len130" style="margin-left: 0px;">
                                        <option></option>
                                        <c:forEach items="${promotionsortList }" var="list">
                                            <c:choose>
                                                <c:when test="${list.code == customer.promotionsort}"><option value="<c:out value='${list.code }' escapeXml='true'></c:out>" selected="selected">${list.codename }</option></c:when>
                                                <c:otherwise><option value="<c:out value='${list.code }' escapeXml='true'></c:out>">${list.codename }</option></c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td class="right">信用等级：</td>
                                <td class="">
                                    <select name="customer.creditrating" id="oa-creditrating-oacustomerViewPage" class="easyui-validatebox len130" style="margin-left: 0px;">
                                        <option></option>
                                        <c:forEach items="${creditratingList }" var="list">
                                            <c:choose>
                                                <c:when test="${list.code == customer.creditrating}"><option value="<c:out value='${list.code }' escapeXml='true'></c:out>" selected="selected">${list.codename }</option></c:when>
                                                <c:otherwise><option value="<c:out value='${list.code }' escapeXml='true'></c:out>">${list.codename }</option></c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td class="right">信用额度：</td>
                                <td class=""><input class="easyui-numberbox len130" name="customer.credit" id="oa-credit-oacustomerViewPage" data-options="max:999999999999,precision:0" value="<c:out value='${customer.credit }' escapeXml='true'></c:out>" style="margin-left: 0px; width: 130px;"/></td>
                            </tr>
                            <tr>
                                <td class="left">结算方式：</td>
                                <td class=""><input class="easyui-validatebox len130" name="customer.settletype" id="oa-settletype-oacustomerViewPage" value="<c:out value='${customer.settletype }' escapeXml='true'></c:out>" style="margin-left: 0px;"/></td>
                                <td class="right">结算日：</td>
                                <td class="">
                                    <select id="oa-settleday-oacustomerViewPage" name="customer.settleday" class="easyui-validatebox len130" style="margin-left: 0px;">
                                        <option></option>
                                        <c:forEach items="${dayList}" var="day">
                                            <c:choose>
                                                <c:when test="${day == customer.settleday}"><option value="<c:out value='${day }' escapeXml='true'></c:out>" selected="selected">${day }</option></c:when>
                                                <c:otherwise><option value="<c:out value='${day }' escapeXml='true'></c:out>">${day }</option></c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td class="right">核销方式：</td>
                                <td class="">
                                    <select name="customer.canceltype" id="oa-canceltype-oacustomerViewPage" class="easyui-validatebox len130" style="margin-left: 0px;">
                                        <option></option>
                                        <c:forEach items="${canceltypeList }" var="list">
                                            <c:choose>
                                                <c:when test="${list.code == customer.canceltype}"><option value="<c:out value='${list.code }' escapeXml='true'></c:out>" selected="selected">${list.codename }</option></c:when>
                                                <c:otherwise><option value="<c:out value='${list.code }' escapeXml='true'></c:out>">${list.codename }</option></c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td class="left">是否现款：</td>
                                <td class="">
                                    <select name="customer.iscash" id="oa-iscash-oacustomerViewPage" class="easyui-validatebox len130" style="margin-left: 0px;">
                                        <option></option>
                                        <option value="0" <c:if test="${customer.iscash == '0' }">selected="selected"</c:if>>否</option>
                                        <option value="1" <c:if test="${customer.iscash == '1' }">selected="selected"</c:if>>是</option>
                                    </select>
                                </td>
                                <td class="right">是否账期：</td>
                                <td class="">
                                    <select name="customer.islongterm" id="oa-islongterm-oacustomerViewPage" class="easyui-validatebox len130" style="margin-left: 0px;">
                                        <option></option>
                                        <option value="0" <c:if test="${customer.islongterm == '0' }">selected="selected"</c:if>>否</option>
                                        <option value="1" <c:if test="${customer.islongterm == '1' }">selected="selected"</c:if>>是</option>
                                    </select>
                                </td>
                                <td colspan="2">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" id="work-allot-oacustomerViewPage" class="easyui-linkbutton" data-options="iconCls:'button-relation'">分配品牌业务员</a>
                                </td>
                            </tr>
                            <tr>
                                <td class="left">备&nbsp;&nbsp;注：</td>
                                <td colspan="5"><textarea style="width: 660px; height: 50px; resize: none;" maxlength="165" name="customer.remark" id="oa-remark-oacustomerViewPage"><c:out value='${customer.remark }' escapeXml='true'></c:out></textarea></td>
                            </tr>
                        </table>
                    </div>
                    <div class="easyui-panel" data-options="border:false" title="谈判内容" style="height: 302px;">
                        <input type="hidden" name="customerBrandJSON" id="oa-customerBrandJSON-oacustomerViewPage"/>
                        <table id="oa-datagrid-oacustomerViewPage">
                            <tr></tr>
                            <tr></tr>
                            <tr></tr>
                            <tr></tr>
                            <tr></tr>
                            <tr></tr>
                            <tr></tr>
                            <tr></tr>
                            <tr></tr>
                            <tr></tr>
                        </table>
                    </div>
                    <div class="easyui-panel" data-options="border:false" title="最终结果">
                        <table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
                            <tr>
                                <td class="len90 left">客户要求：</td>
                                <td class="left" style="width: 600px;"><textarea style="width:650px; height: 40px;" name="customer.demand" id="oa-demand-oacustomerViewPage" readonly="readonly"><c:out value='${customer.demand }' escapeXml='true'></c:out></textarea></td>
                            </tr>
                            <tr>
                                <td class="len90 left">谈判结果：</td>
                                <td class="left" style="width: 600px;"><textarea style="width:650px; height: 40px;" name="customer.talkresult" id="oa-talkresult-oacustomerViewPage" readonly="readonly"><c:out value='${customer.talkresult }' escapeXml='true'></c:out></textarea></td>
                            </tr>
                            <tr>
                                <td class="len90 left">反馈情况：</td>
                                <td class="left" style="width: 600px;"><textarea style="width:650px; height: 40px;" name="customer.feedback" id="oa-feedback-oacustomerViewPage" readonly="readonly"><c:out value='${customer.feedback }' escapeXml='true'></c:out></textarea></td>
                            </tr>
                            <tr>
                                <td class="len90 left">合同期限：</td>
                                <td class="left" style="width: 600px;"><input class="easyui-validatebox" name="customer.pactdeadline" id="oa-pactdeadline-oacustomerViewPage" value="<c:out value='${customer.pactdeadline }' escapeXml='true'></c:out>" style=" margin-left: 2px;" readonly="readonly"/></td>
                            </tr>
                        </table>
                    </div>
                    <div style="border-top: 1px solid #AAA;">&nbsp;</div>
                    <div>
                        <div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
                        <div id="oa-attach-oacustomerViewPage" style="width: 845px;">
                        </div>
                    </div>
                    <div>
                        <div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
                        <div id="oa-comments2-oacustomerViewPage" style="width: 840px;">
                        </div>
                    </div>
                </div>
            </form>
	</div>
	<div class="easyui-menu" id="oa-contextMenu-oacustomerViewPage">
		<div id="oa-addRow-oacustomerViewPage" data-options="iconCls:'button-add'">添加</div>
		<div id="oa-editRow-oacustomerViewPage" data-options="iconCls:'button-edit'">编辑</div>
		<div id="oa-removeRow-oacustomerViewPage" data-options="iconCls:'button-delete'">删除</div>
	</div>
	<script type="text/javascript">
<!--
	$(function(){
	
		customerWidgetWidth = customerWidgetWidth - 2;

		readonlyDom($('body'));
		$('select').attr('disabled', 'disabled');
		$('input[type=hidden]').removeAttr('readonly');
		
	});
	
	var $line = $("#oa-datagrid-oacustomerViewPage");
	var editItem = $('#oa-editRow-oacustomerViewPage');
				
	var $oa_salesarea = $("#oa-salesarea-oacustomerViewPage");
	var $oa_payeeid = $('#oa-payeeid-oacustomerViewPage');
	var $oa_customersort = $('#oa-customersort-oacustomerViewPage');
	var $oa_salesuserid = $("#oa-salesuserid-oacustomerViewPage");
	var $oa_indoorstaff = $("#oa-indoorstaff-oacustomerViewPage");
	var $oa_settletype = $("#oa-settletype-oacustomerViewPage");
	var $oa_settleday = $("#oa-settleday-oacustomerViewPage");
	var $oa_workAllot = $('#work-allot-oacustomerViewPage');
	var $oa_datagrid = $('#oa-datagrid-oacustomerViewPage');
    var $pcustomerid = $('#oa-pcustomerid-oacustomerViewPage');
    var $salesdeptid = $('#oa-salesdeptid-oacustomerViewPage');

var $comments = $('#oa-comments-oacustomerViewPage');
var $comments2 = $('#oa-comments2-oacustomerViewPage');
var $attach = $('#oa-attach-oacustomerViewPage');

    var $oa_customerid  = $('#oa-id-oacustomerViewPage');
	var $iscash = $('#oa-iscash-oacustomerViewPage');
	var $islongterm = $('#oa-islongterm-oacustomerViewPage');
-->
	</script>
  </body>
</html>