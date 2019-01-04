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
					<form action="oa/addOaInnerShare.do" id="oa-form-oaInnerShareViewPage" method="post">
				</c:when>
				<c:otherwise>
					<form action="oa/editOaInnerShare.do" id="oa-form-oaInnerShareViewPage" method="post">
				</c:otherwise>
			</c:choose>
				<input type="hidden" name="share.id" id="oa-id-oaInnerShareViewPage" value="${share.id }"/>
				<input type="hidden" name="share.oaid" id="oa-oaid-oaInnerShareViewPage" value="${param.processid }"/>
				<input type="hidden" name="share.billtype" id="oa-billtype-oaInnerShareViewPage" value="${param.billtype }"/>
				<div data-options="region:'center',border:false">
					<div style="margin: 20px auto; width: 790px; border: 1px solid #AAA;">
                        <process:definitionHeader process="${process}"/>
                        <div class="easyui-panel" data-options="border:false">
                        <table>
                            <tr>
                                <td class="len80 left">付款部门：</td>
                                <td class="len180"><input class="len140" name="share.paydeptid" id="oa-paydeptid-oaInnerShareViewPage" value="${share.paydeptid }" autocomplete="off" maxlength="10"/></td>
                                <td class="len80 left">付款日期：</td>
                                <td class="len180"><input class="easyui-validatebox Wdate len140" name="share.businessdate" id="oa-businessdate-oaPersonalLoanHandlePage" onclick="" data-options="required:false" value="${share.businessdate }" autocomplete="off" maxlength="10" readonly="readonly"/></td>
                                <td class="len80 left">费用科目：</td>
                                <td class="len180"><input class="len140" name="share.costsort" id="oa-costsort-oaInnerShareViewPage" value="${share.costsort }" autocomplete="off" maxlength="10"/></td>
                            </tr>
                            <tr>
                                <td class="left">金额：</td>
                                <td class=""><input class="easyui-numberbox len140" name="share.amount" id="oa-amount-oaInnerShareViewPage" data-options="required:false,min:0,precision:2" value="${share.amount }" autocomplete="off" maxlength="15" readonly="readonly"/></td>
                                <td class="left">大写金额：</td>
                                <td class=""><input class="easyui-validatebox len140" name="share.upamount" id="oa-upamount-oaInnerShareViewPage" data-options="required:false" value="${share.upamount }" autocomplete="off" maxlength="50" readonly="readonly"/></td>
                                <td class="left">收款部门：</td>
                                <td class=""><input class="len140" name="share.collectdeptid" id="oa-collectdeptid-oaInnerShareViewPage" value="${share.collectdeptid }" autocomplete="off" maxlength="10"/></td>
                            </tr>
                            <tr>
                                <td class="left">备注：</td>
                                <td class="" colspan="5">
                                    <textarea name="share.remark" id="oa-remark-oaInnerShareViewPage" style="width: 672px; height: 50px; resize: none;" maxlength="165" readonly="readonly"><c:out value="${share.remark }"></c:out></textarea>
                                </td>
                            </tr>
                        </table>
                    </div>
                        <div class="easyui-panel" data-options="border:false">
                            <div id="oa-comments2-oaInnerShareViewPage" style="width: 760px;">
                            </div>
                        </div>
                        <div style="border-top: 1px solid #AAA;">&nbsp;</div>
                        <div>
                            <div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
                            <div id="oa-attach-oaInnerShareViewPage" style="width: 765px;">
                            </div>
                        </div>
                        <div>
                            <div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
                            <div id="oa-comments-oaInnerShareViewPage" style="width: 760px;">
                            </div>
                        </div>
					</div>
				</div>
			</form>
		</div>
	</div>
    <script type="text/javascript">
    <!--
    
    var $paydeptid = $('#oa-paydeptid-oaInnerShareViewPage');
    var $amount = $('#oa-amount-oaInnerShareViewPage');
    var $upamount = $('#oa-upamount-oaInnerShareViewPage');
    var $costsort = $('#oa-costsort-oaInnerShareViewPage');
    var $collectdeptid = $('#oa-collectdeptid-oaInnerShareViewPage');
    // var $paybank = $('#oa-paybank-oaInnerShareViewPage');
    
    var $form = $('#oa-form-oaInnerShareViewPage');
    var $comments = $('#oa-comments-oaInnerShareViewPage');
    var $comments2 = $('#oa-comments2-oaInnerShareViewPage');
    var $attach = $('#oa-attach-oaInnerShareViewPage');
    
    $(function() {
    
    });

	-->
	</script>
  </body>
</html>