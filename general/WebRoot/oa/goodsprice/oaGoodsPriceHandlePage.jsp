<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>商品调价申请单处理页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
    <div class="easyui-layout" data-options="fit:true,border:false">
        <c:choose>
            <c:when test="${empty price or empty price.id}">
                <form action="oa/goodsprice/addOaGoodsPrice.do" id="oa-form-oaGoodsPriceHandlePage" method="post">
            </c:when>
            <c:otherwise>
                <form action="oa/goodsprice/editOaGoodsPrice.do" id="oa-form-oaGoodsPriceHandlePage" method="post">
            </c:otherwise>
        </c:choose>
            <input type="hidden" name="price.id" id="oa-id-oaGoodsPriceHandlePage" value="${price.id }"/>
            <input type="hidden" name="price.oaid" id="oa-oaid-oaGoodsPriceHandlePage" value="${param.processid }"/>
            <div data-options="region:'center',border:false">
                <div style="margin: 20px auto; width: 790px; border: 1px solid #AAA;">
                    <process:definitionHeader process="${process}"/>
                    <div class="easyui-panel" data-options="border:false">
                        <table>
                            <tr>
                                <td class="len80 left">供应商：</td>
                                <td class="len440"><input class="easyui-validatebox len400"  id="oa-supplierid-oaGoodsPriceHandlePage" value="<c:out value='${price.supplierid }' escapeXml='true'></c:out>" text="${supplier.name}"/><font color="#F00">*</font></td>
                                <td class="len80 left">供应商编号：</td>
                                <td class="len180"><input class="easyui-validatebox len140" name="price.supplierid" id="oa-supplierid2-oaGoodsPriceHandlePage" data-options="required:false" value="${price.supplierid }" autocomplete="off" maxlength="50" readonly="readonly"/></td>
                            </tr>
                            <tr>
                                <td class="left">备&nbsp;注：</td>
                                <td class="" colspan="5">
                                    <textarea style="width: 658px; height: 50px; resize: none;"name="price.remark" id="oa-remark-oaGoodsPriceHandlePage" maxlength="165"><c:out value="${price.remark }"></c:out></textarea>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="easyui-panel" data-options="border:false" title="商品明细清单" style="height: 327px;">
                        <input type="hidden" name="goodpricedetail" id="oa-detaillist-oaGoodsPriceHandlePage"/>
                        <table id="oa-detail-oaGoodsPriceHandlePage">
                            <tr><td></td></tr>
                            <tr><td></td></tr>
                            <tr><td></td></tr>
                            <tr><td></td></tr>
                            <tr><td></td></tr>
                            <tr><td></td></tr>
                            <tr><td></td></tr>
                            <tr><td></td></tr>
                            <tr><td></td></tr>
                            <tr><td></td></tr>
                        </table>
                    </div>
                    <div class="easyui-panel" data-options="border:false">
                        <div id="oa-comments-oaGoodsPriceHandlePage">
                        </div>
                    </div>
                    <div style="border-top: 1px solid #AAA;">&nbsp;</div>
                    <div>
                        <div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
                        <div id="oa-attach-oaGoodsPriceHandlePage" style="width: 765px;">
                        </div>
                    </div>
                    <div>
                        <div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
                        <div id="oa-comments2-oaGoodsPriceHandlePage" style="width: 760px;">
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">

    <!--
    var $supplierid = $('#oa-supplierid-oaGoodsPriceHandlePage');
    var $supplierid2 = $('#oa-supplierid2-oaGoodsPriceHandlePage');

//    var $detail = $('#oa-detail-oaGoodsPriceHandlePage');

    var $form = $('#oa-form-oaGoodsPriceHandlePage');
    var $line = $('#oa-detail-oaGoodsPriceViewPage');

    var $comments = $('#oa-comments-oaGoodsPriceHandlePage');
    var $comments2 = $('#oa-comments2-oaGoodsPriceHandlePage');
    var $attach = $('#oa-attach-oaGoodsPriceHandlePage');
    var $oa_pricedatagrid = $('#oa-detail-oaGoodsPriceHandlePage');
    $(function() {



    });
    var editItem = $("#oa-editRow-oagoodsPricePage");

    -->
</script>
</body>
</html>