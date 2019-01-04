<%--
  Created by IntelliJ IDEA.
  User: limin
  Date: 2018/2/12
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>代垫费用申请单处理页面</title>
</head>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
    <div class="easyui-layout" data-options="fit:true,border:false">
        <c:choose>
            <c:when test="${empty matcost or empty matcost.id}">
                <form action="oa/matcost/addOaMatcost.do" id="oa-form-oaMatcostHandlePage" method="post">
            </c:when>
            <c:otherwise>
                <form action="oa/matcost/editOaMatcost.do" id="oa-form-oaMatcostHandlePage" method="post">
            </c:otherwise>
        </c:choose>
            <input type="hidden" name="matcost.id" id="oa-id-oaMatcostHandlePage" value="${matcost.id }"/>
            <input type="hidden" name="matcost.oaid" id="oa-oaid-oaMatcostHandlePage" value="${param.processid }"/>
            <input type="hidden" name="matcost.feeamount" id="oa-feeamount-oaMatcostHandlePage" value="${matcost.feeamount }"/>
            <div data-options="region:'center',border:false">
                <div style="margin: 0px auto; width: 805px; border: 1px solid #AAA;">
                    <process:definitionHeader process="${process}"/>
                    <div class="easyui-panel" data-options="border:false">
                        <table>
                            <tr>
                                <td class="len80 left">供应商：</td>
                                <td class="len180"><input class="easyui-validatebox len150" name="matcost.supplierid" id="oa-supplierid-oaMatcostHandlePage" value="${matcost.supplierid }" text="${supplier.name }" autocomplete="off"/><font color="#F00">*</font></td>
                                <td class="len100 left">确认单号：</td>
                                <td class="len180"><input class="easyui-validatebox len150" name="matcost.supplierbillid" id="oa-supplierbillid-oaMatcostHandlePage" value="${matcost.supplierbillid }" autocomplete="off" maxlength="25"/></td>
                                <td class="len80 left">申请日期：</td>
                                <td class="">
                                    <c:choose>
                                        <c:when test="${empty matcost.businessdate}">
                                            <input class="easyui-validatebox len150 Wdate" name="matcost.businessdate" id="oa-businessdate-oaMatcostHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:false" value="<fmt:formatDate value='${today }' pattern='yyyy-MM-dd' type='date' dateStyle='long' />" autocomplete="off"/>
                                        </c:when>
                                        <c:otherwise>
                                            <input class="easyui-validatebox len150 Wdate" name="matcost.businessdate" id="oa-businessdate-oaMatcostHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:false" value="${matcost.businessdate }" autocomplete="off"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td>部　　门：</td>
                                <td><input class="easyui-validatebox len150" name="matcost.deptid" id="oa-deptid-oaMatcostHandlePage" value="${matcost.deptid }" autocomplete="off"/></td>
                                <td>银行名称：</td>
                                <td><input class="easyui-validatebox len150" name="matcost.paybank" id="oa-paybank-oaMatcostHandlePage" value="${matcost.paybank }" autocomplete="off"/><font color="#F00">*</font></td>
                                <td>归还日期：</td>
                                <td><input class="easyui-validatebox len150 Wdate" name="matcost.returndate" id="oa-returndate-oaMatcostHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${matcost.returndate }" autocomplete="off"/></td>
                            </tr>
                            <tr>
                                <td>归还方式：</td>
                                <td>
                                    <select class="easyui-validatebox len150" name="matcost.returnway" id="oa-returnway-oaMatcostHandlePage" data="${matcost.returnway }" data-options="required:false">
                                        <option></option>
                                        <c:forEach items="${reimburseTypeList }" var="type">
                                            <option value="${type.code }" <c:if test="${matcost.returnway eq type.code}">selected="selected"</c:if> ><c:out value="${type.codename }"></c:out></option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>备　　注：</td>
                                <td colspan="3"><input class="easyui-validatebox" name="matcost.remark" id="oa-remark-oaMatcostHandlePage" value="${matcost.remark }" autocomplete="off" style="width: 418px;" maxlength="100"/></td>
                            </tr>
                        </table>
                    </div>
                    <!-- 费用明细一览 -->
                    <div class="easyui-panel" data-options="border:true" style="height: 310px; border-top: solid 1px #aaa; border-bottom: solid 1px #aaa;">
                        <input type="hidden" id="oa-detaillistStr-oaMatcostHandlePage" name="detaillist"/>
                        <table id="oa-detail-oaMatcostHandlePage">
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

                    <div style="border-top: 1px solid #AAA;">&nbsp;</div>
                    <div>
                        <div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
                        <div id="oa-attach-oaMatcostHandlePage" style="width: 800px;">
                        </div>
                    </div>
                    <div>
                        <div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
                        <div id="oa-comments-oaMatcostHandlePage" style="width: 800px;">
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    <!--

    var $supplierid = $('#oa-supplierid-oaMatcostHandlePage');
    var $supplierbillid = $('#oa-supplierbillid-oaMatcostHandlePage');
    var $deptid = $('#oa-deptid-oaMatcostHandlePage');
    var $paybank = $('#oa-paybank-oaMatcostHandlePage');
    var $returndate = $('#oa-returndate-oaMatcostHandlePage');
    var $returnway = $('#oa-returnway-oaMatcostHandlePage');

    var $detail = $('#oa-detail-oaMatcostHandlePage');
    var $feeamount = $('#oa-feeamount-oaMatcostHandlePage');
    var $detaillistStr = $('#oa-detaillistStr-oaMatcostHandlePage');
    var $form = $('#oa-form-oaMatcostHandlePage');

    var $attach = $('#oa-attach-oaMatcostHandlePage');
    var $comments = $('#oa-comments-oaMatcostHandlePage');
    -->
</script>
</body>
</html>