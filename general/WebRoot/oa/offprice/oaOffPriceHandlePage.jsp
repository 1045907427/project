<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<title>批量特价审批单处理页面</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<div class="easyui-layout" data-options="fit:true,border:false">
			<c:choose>
				<c:when test="${empty price or empty price.id}">
					<form action="oa/offprice/addOaOffPrice.do" id="oa-form-oaOffPriceHandlePage" method="post">
				</c:when>
				<c:otherwise>
					<form action="oa/offprice/editOaOffPrice.do" id="oa-form-oaOffPriceHandlePage" method="post">
				</c:otherwise>
			</c:choose>
                <input type="hidden" id="oa-customerid2-oaOffPriceHandlePage" value="${price.customerid }"/>
				<input type="hidden" name="price.id" id="oa-id-oaOffPriceHandlePage" value="${price.id }"/>
				<input type="hidden" name="price.oaid" id="oa-oaid-oaOffPriceHandlePage" value="${param.processid }"/>
				<div data-options="region:'center',border:false">
					<div style="margin: 20px auto; width: 790px; border: 1px solid #AAA;">
                        <process:definitionHeader process="${process}"/>
                        <div class="easyui-panel" data-options="border:false">
                            <table>
                                <tr>
                                    <td class="len60 left">客户名称：</td>
                                    <td class="" colspan="3"><input class="easyui-validatebox" style="width: 367px;" name="price.customerid" id="oa-customerid-oaOffPriceHandlePage" data-options="required:true" value="${price.customerid }" autocomplete="off" maxlength="20" text="<c:out value="${price.customername }"/>"/><font color="#F00">*</font></td>
                                    <td class="len100 left">一次性开单时间：</td>
                                    <td class="len200">
                                        <input class="easyui-validatebox Wdate len90" name="price.pricebegindate" id="oa-pricebegindate-oaOffPriceHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:true" value="${price.pricebegindate }" autocomplete="off" maxlength="10"/>~
                                        <input class="easyui-validatebox Wdate len90" name="price.priceenddate" id="oa-priceenddate-oaOffPriceHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:true" value="${price.priceenddate }" autocomplete="off" maxlength="10"/><font color="#F00">*</font>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="len60 left">销售内勤：</td>
                                    <td class="len165"><input class="easyui-validatebox len130" name="price.indoorstaff" id="oa-indoorstaff-oaOffPriceHandlePage" data-options="required:false" value="${price.indoorstaff }" autocomplete="off" maxlength="20"/></td>
                                    <td class="len60 left">业务员：</td>
                                    <td class="len165"><input class="easyui-validatebox len130" name="price.salesuserid" id="oa-salesuserid-oaOffPriceHandlePage" data-options="required:false" value="${price.salesuserid }" autocomplete="off" maxlength="20"/></td>
                                    <td>档期：</td>
                                    <td><input type="text" class="easyui-validatebox" style="width: 192px;" data-options="required:false" validType="maxByteLength[50]" name="price.schedule" id="oa-schedule-oaOffPriceHandlePage" value="<c:out value='${price.schedule }'/>" maxlength="25" autocomplete="off"/></td>
                                </tr>
                                <tr>
                                    <td>说　　明：</td>
                                    <td colspan="5">
                                        <input type="text" class="easyui-validatebox" style="width: 698px;" data-options="required:false" validType="maxByteLength[150]" name="price.remark" id="oa-remark-oaOffPriceHandlePage" value="<c:out value='${price.remark }'/>" autocomplete="off"/>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="easyui-panel" data-options="border:false" style="height: 327px; border-top: solid 1px #aaa; border-bottom: solid 1px #aaa;">
                            <input type="hidden" name="paydetail" id="oa-detaillist-oaOffPriceHandlePage"/>
                            <table id="oa-datagrid-oaOffPriceHandlePage">
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
                            <div id="oa-attach-oaOffPriceHandlePage" style="width: 765px;">
                            </div>
                        </div>
                        <div>
                            <div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
                            <div id="oa-comments-oaOffPriceHandlePage" style="width: 760px;">
                            </div>
                        </div>
					</div>
				</div>
			</form>
		</div>
	</div>
    <script type="text/javascript">
    <!--
    
    var $customerid = $('#oa-customerid-oaOffPriceHandlePage');
    var $customerid2 = $('#oa-customerid2-oaOffPriceHandlePage');
    var $indoorstaff = $('#oa-indoorstaff-oaOffPriceHandlePage');
    var $salesuserid = $('#oa-salesuserid-oaOffPriceHandlePage');
    var $pricebegindate = $('#oa-pricebegindate-oaOffPriceHandlePage');
    var $priceenddate = $('#oa-priceenddate-oaOffPriceHandlePage');
    var $comments = $('#oa-comments-oaOffPriceHandlePage');
    //var $comments2 = $('#oa-comments2-oaOffPriceHandlePage');
    var $attach = $('#oa-attach-oaOffPriceHandlePage');

    var $datagrid = $('#oa-datagrid-oaOffPriceHandlePage');
    var $detaillist = $('#oa-detaillist-oaOffPriceHandlePage');
    
    var $form = $('#oa-form-oaOffPriceHandlePage');
    
    $(function() {

    });

	-->
	</script>
  </body>
</html>