<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<title>批量特价审批单查看页面</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<div class="easyui-layout" data-options="fit:true,border:false">
			<c:choose>
				<c:when test="${empty price or empty price.id}">
					<form action="oa/offprice/addOaOffPrice.do" id="oa-form-oaOffPriceViewPage" method="post">
				</c:when>
				<c:otherwise>
					<form action="oa/offprice/editOaOffPrice.do" id="oa-form-oaOffPriceViewPage" method="post">
				</c:otherwise>
			</c:choose>
                <input type="hidden" id="oa-customerid2-oaOffPriceViewPage" value="${price.customerid }"/>
				<input type="hidden" name="price.id" id="oa-id-oaOffPriceViewPage" value="${price.id }"/>
				<input type="hidden" name="price.oaid" id="oa-oaid-oaOffPriceViewPage" value="${param.processid }"/>
				<div data-options="region:'center',border:false">
					<div style="margin: 20px auto; width: 790px; border: 1px solid #AAA;">
                        <process:definitionHeader process="${process}"/>
                        <div class="easyui-panel" data-options="border:false">
                            <table>
                                <tr>
                                    <td class="len60 left">客户名称：</td>
                                    <td class="" colspan="3"><input class="easyui-validatebox" style="width: 387px;" name="price.customerid" id="oa-customerid-oaOffPriceViewPage" data-options="required:true" value="${price.customerid }" autocomplete="off" maxlength="20" text="<c:out value="${price.customername }"/>"/></td>
                                    <td class="len100 left">一次性开单时间：</td>
                                    <td class="len200">
                                        <input class="easyui-validatebox Wdate len90" name="price.pricebegindate" id="oa-pricebegindate-oaOffPriceViewPage" data-options="required:true" value="${price.pricebegindate }" autocomplete="off" maxlength="10" readonly="readonly"/>~
                                        <input class="easyui-validatebox Wdate len90" name="price.priceenddate" id="oa-priceenddate-oaOffPriceViewPage" data-options="required:true" value="${price.priceenddate }" autocomplete="off" maxlength="10" readonly="readonly"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>销售内勤：</td>
                                    <td><input class="easyui-validatebox len130" name="price.indoorstaff" id="oa-indoorstaff-oaOffPriceViewPage" data-options="required:false" value="${price.indoorstaff }" autocomplete="off" maxlength="20"/></td>
                                    <td>业务员：</td>
                                    <td><input class="easyui-validatebox len130" name="price.salesuserid" id="oa-salesuserid-oaOffPriceViewPage" data-options="required:false" value="${price.salesuserid }" autocomplete="off" maxlength="20"/></td>
                                    <td>档期：</td>
                                    <td><input type="text" class="easyui-validatebox" style="width: 192px;" data-options="required:false" validType="maxByteLength[50]" name="price.schedule" id="oa-schedule-oaOffPriceViewPage" value="<c:out value='${price.schedule }'/>" readonly="readonly"/></td>
                                </tr>
                                <tr>
                                    <td>说　　明：</td>
                                    <td colspan="5">
                                        <input type="text" class="easyui-validatebox" style="width: 667px;" data-options="required:false" name="price.remark" id="oa-remark-oaOffPriceViewPage" value="<c:out value='${price.remark }'/>" readonly="readonly"/>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="easyui-panel" data-options="border:false" style="height: 277px; border-top: solid 1px #aaa; border-bottom: solid 1px #aaa;">
                            <input type="hidden" name="paydetail" id="oa-detaillist-oaOffPriceViewPage"/>
                            <table id="oa-datagrid-oaOffPriceViewPage">
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
                            <div id="oa-attach-oaOffPriceViewPage" style="width: 765px;">
                            </div>
                        </div>
                        <div>
                            <div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
                            <div id="oa-comments-oaOffPriceViewPage" style="width: 760px;">
                            </div>
                        </div>
					</div>
				</div>
			</form>
		</div>
	</div>
    <script type="text/javascript">
    <!--
    
    var $customerid = $('#oa-customerid-oaOffPriceViewPage');
    var $customerid2 = $('#oa-customerid2-oaOffPriceViewPage');
    var $indoorstaff = $('#oa-indoorstaff-oaOffPriceViewPage');
    var $salesuserid = $('#oa-salesuserid-oaOffPriceViewPage');
    var $pricebegindate = $('#oa-pricebegindate-oaOffPriceViewPage');
    var $priceenddate = $('#oa-priceenddate-oaOffPriceViewPage');
    var $comments = $('#oa-comments-oaOffPriceViewPage');
    //var $comments2 = $('#oa-comments2-oaOffPriceViewPage');
    var $attach = $('#oa-attach-oaOffPriceViewPage');

    var $datagrid = $('#oa-datagrid-oaOffPriceViewPage');
    var $detaillist = $('#oa-detaillist-oaOffPriceViewPage');
    
    var $form = $('#oa-form-oaOffPriceViewPage');
    
    $(function() {

    });

	-->
	</script>
  </body>
</html>