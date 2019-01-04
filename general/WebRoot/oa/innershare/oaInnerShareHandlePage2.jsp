<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<title>内部分摊申请单</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
  	<style type="text/css">
  		td { 
  			padding-top: 8px; 
  			padding-bottom: 6px; 
  		}
  	</style>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<div class="easyui-layout" data-options="fit:true,border:false">
			<c:choose>
				<c:when test="${empty share or empty share.id}">
					<form action="oa/addOaInnerShare.do" id="oa-form-oaInnerShareHandlePage" method="post">
				</c:when>
				<c:otherwise>
					<form action="oa/editOaInnerShare.do" id="oa-form-oaInnerShareHandlePage" method="post">
				</c:otherwise>
			</c:choose>
				<input type="hidden" name="share.id" id="oa-id-oaInnerShareHandlePage" value="${share.id }"/>
				<input type="hidden" name="share.oaid" id="oa-oaid-oaInnerShareHandlePage" value="${param.processid }"/>
				<input type="hidden" name="share.billtype" id="oa-billtype-oaInnerShareHandlePage" value="${param.billtype }"/>
				<div data-options="region:'center',border:false">
					<div style="margin: 20px auto; width: 790px; border: 1px solid #AAA;">
                        <process:definitionHeader process="${process}"/>
                        <div class="easyui-panel" data-options="border:false">
                            <table>
                                <tr>
                                    <td class="len80 left">付款部门：</td>
                                    <td class="len180"><input class="len140" name="share.paydeptid" id="oa-paydeptid-oaInnerShareHandlePage" value="${share.paydeptid }" autocomplete="off" maxlength="10"/><font color="#F00">*</font></td>
                                    <td class="len80 left">付款日期：</td>
                                    <td class="len180">
                                        <c:choose>
                                            <c:when test="${empty share.businessdate}">
                                                <input class="easyui-validatebox Wdate len140" name="share.businessdate" id="oa-businessdate-oaPersonalLoanHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:true" value="<fmt:formatDate value="${today}" pattern="yyyy-MM-dd" type="date" dateStyle="long" />" autocomplete="off" maxlength="10"/><font color="#F00">*</font>
                                            </c:when>
                                            <c:otherwise>
                                                <input class="easyui-validatebox Wdate len140" name="share.businessdate" id="oa-businessdate-oaPersonalLoanHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:true" value="${share.businessdate }" autocomplete="off" maxlength="10"/><font color="#F00">*</font>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="len80 left">费用科目：</td>
                                    <td class="len180"><input class="len140" name="share.costsort" id="oa-costsort-oaInnerShareHandlePage" value="${share.costsort }" autocomplete="off" maxlength="10"/><font color="#F00">*</font></td>
                                </tr>
                                <tr>
                                    <td class="left">金额：</td>
                                    <td class=""><input class="easyui-numberbox len140" name="share.amount" id="oa-amount-oaInnerShareHandlePage" data-options="required:true,min:0,precision:2" value="${share.amount }" autocomplete="off" maxlength="15"/><font color="#F00">*</font></td>
                                    <td class="left">大写金额：</td>
                                    <td class=""><input class="easyui-validatebox len140" name="share.upamount" id="oa-upamount-oaInnerShareHandlePage" data-options="required:false" value="${share.upamount }" autocomplete="off" maxlength="50"/></td>
                                    <td class="left">收款部门：</td>
                                    <td class=""><input class="len140" name="share.collectdeptid" id="oa-collectdeptid-oaInnerShareHandlePage" value="${share.collectdeptid }" autocomplete="off" maxlength="10"/><font color="#F00">*</font></td>
                                </tr>
                                <tr>
                                    <td class="left">备注：</td>
                                    <td class="" colspan="5">
                                        <textarea name="share.remark" id="oa-remark-oaInnerShareHandlePage" style="width: 672px; height: 50px; resize: none;" maxlength="165"><c:out value="${share.remark }"></c:out></textarea>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="easyui-panel" data-options="border:false">
                            <div id="oa-comments2-oaInnerShareHandlePage" style="width: 760px;">
                            </div>
                        </div>
                        <div style="border-top: 1px solid #AAA;">&nbsp;</div>
                        <div>
                            <div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
                            <div id="oa-attach-oaInnerShareHandlePage" style="width: 765px;">
                            </div>
                        </div>
                        <div>
                            <div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
                            <div id="oa-comments-oaInnerShareHandlePage" style="width: 760px;">
                            </div>
                        </div>
					</div>
				</div>
			</form>
		</div>
	</div>
    <script type="text/javascript">
    <!--
    
    var $paydeptid = $('#oa-paydeptid-oaInnerShareHandlePage');
    var $amount = $('#oa-amount-oaInnerShareHandlePage');
    var $upamount = $('#oa-upamount-oaInnerShareHandlePage');
    var $costsort = $('#oa-costsort-oaInnerShareHandlePage');
    var $collectdeptid = $('#oa-collectdeptid-oaInnerShareHandlePage');
    // var $paybank = $('#oa-paybank-oaInnerShareHandlePage');
    
    var $form = $('#oa-form-oaInnerShareHandlePage');
    var $comments = $('#oa-comments-oaInnerShareHandlePage');
    var $comments2 = $('#oa-comments2-oaInnerShareHandlePage');
    var $attach = $('#oa-attach-oaInnerShareHandlePage');
    
    $(function() {
    
    });

	-->
	</script>
  </body>
</html>