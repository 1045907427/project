<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>工作委托规则查看页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
    <style type="text/css">
        label > input[type=checkbox]{
            float:left;
            height: 12px;
            line-height: 15px;
        }
    </style>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<div class="easyui-layout" data-options="fit:true,border:false">
			<c:choose>
				<c:when test="${empty delegate or empty delegate.id}">
					<form action="oa/delegate/addOaDelegate.do" id="oa-form-oaDelegateViewPage" method="post">
				</c:when>
				<c:otherwise>
					<form action="oa/delegate/editOaDelegate.do" id="oa-form-oaDelegateViewPage" method="post">
				</c:otherwise>
			</c:choose>
				<input type="hidden" name="delegate.id" id="oa-id-oaDelegateViewPage" value="${delegate.id }"/>
				<input type="hidden" name="delegate.oaid" id="oa-oaid-oaDelegateViewPage" value="${param.processid }"/>
                <input type="hidden" name="delegate.definitionkey" id="oa-definitionkey-oaDelegateViewPage" value="${delegate.definitionkey }"/>
				<div data-options="region:'center',border:false">
					<div style="margin: 20px auto; width: 700px; border: 1px solid #bbb;">
                        <process:definitionHeader process="${process}"/>
                        <div class="easyui-panel" data-options="border:false">
                            <div>
                                <table class="content-table" cellspacing="0" cellpadding="0" style="width: 652px; margin: 0px auto;">
                                    <tr>
                                        <td class="title">　任　　务：</td>
                                        <td style="border-right: 0px;"><input type="text" class="easyui-validatebox len200" id="oa-definitionkey2-oaDelegateViewPage" value="${delegate.definitionkey }" readonly="readonly"/></td>
                                        <td style="border-left: 0px;"><label><input type="checkbox" id="oa-all-oaDelegateViewPage" value="0" <c:if test="${delegate.definitionkey eq '0'}">checked="checked"</c:if> disabled="disabled"/>所有流程</label></td>
                                    </tr>
                                    <tr>
                                        <td class="title">　委 托 人：</td>
                                        <td colspan="2"><input class="easyui-validatebox len200" name="delegate.userid" id="oa-userid-oaDelegateViewPage" data-options="required:true" value="${delegate.userid }" autocomplete="off" maxlength="50" readonly="readonly"/></td>
                                    </tr>
                                    <tr>
                                        <td class="title">　被委托人：</td>
                                        <td colspan="2"><input class="easyui-validatebox len200" name="delegate.delegateuserid" id="oa-delegateuserid-oaDelegateViewPage" data-options="required:true" value="${delegate.delegateuserid }" autocomplete="off" maxlength="50" readonly="readonly"/></td>
                                    </tr>
                                    <tr>
                                        <td class="title len120" style="border-bottom: 0px;">　生效日期：</td>
                                        <td class="len230" style="border-right: 0px; border-bottom: 0px;"><input class="easyui-validatebox len200 Wdate" name="delegate.begindate" id="oa-begindate-oaCustomerPayHandlePage" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd HH:mm:ss'})" data-options="required:true" value="${delegate.begindate }" autocomplete="off" disabled="disabled" /></td>
                                        <td rowspan="2" style="border-left: 0px;"><label><input type="checkbox" name="delegate.remain" id="oa-remain-oaDelegateViewPage" value="1" disabled="disabled" <c:if test="${delegate.remain eq '1'}">checked="checked"</c:if>/>一直有效</label></td>
                                    </tr>
                                    <tr>
                                        <td class="title" style="border-top: 0px;">　截止日期：</td>
                                        <td style="border-right: 0px; border-top: 0px;"><input class="easyui-validatebox len200 Wdate" name="delegate.enddate" id="oa-enddate-oaCustomerPayHandlePage" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd HH:mm:ss'})" data-options="required:true" value="${delegate.enddate }" autocomplete="off" disabled="disabled" /></td>
                                    </tr>
                                </table><br/>
                            </div>
                        </div>
                        <div style="border-top: 1px solid #AAA;">
                            <div style="background: #EEE; font-weight: bold; padding: 3px;">附件</div>
                            <div id="oa-attach-oaDelegateViewPage" style="width: 680px;"></div>
                        </div>
                        <div style="border-top: 1px solid #AAA; border-top: 0px;">
                            <div style="background: #EEE; font-weight: bold; padding: 3px;">审批信息</div>
                            <div id="oa-comments-oaDelegateViewPage" style="width: 676px;"></div>
                        </div>
					</div>
				</div>
			</form>
		</div>
	</div>
    <script type="text/javascript">

        var $definitionkey = $('#oa-definitionkey-oaDelegateViewPage');
        var $definitionkey2 = $('#oa-definitionkey2-oaDelegateViewPage');
        var $all = $('#oa-all-oaDelegateViewPage');
        var $userid = $('#oa-userid-oaDelegateViewPage');
        var $delegateuserid = $('#oa-delegateuserid-oaDelegateViewPage');
        var $begindate = $('#oa-begindate-oaCustomerPayHandlePage');
        var $enddate = $('#oa-enddate-oaCustomerPayHandlePage');
        var $remain = $('#oa-remain-oaDelegateViewPage');
        var $form = $('#oa-form-oaDelegateViewPage');

        var $attach = $('#oa-attach-oaDelegateViewPage');
        var $comments = $('#oa-comments-oaDelegateViewPage');

        /**
        *
        * @param e
        * @returns {boolean}
         */
        function checkAllDefinition(e) {}

    </script>
</body>
</html>
