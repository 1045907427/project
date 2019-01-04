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
            <div data-options="region:'center',border:false">
                <div style="margin: 20px auto; width: 790px; border: 1px solid #AAA;">
                    <process:definitionHeader process="${process}"/>
                    <div class="easyui-panel" data-options="border:false">
                        <table>
                            <tr>
                                <td class="len80 left">供应商：</td>
                                <td class="len440"><input class="easyui-validatebox len400" name="price.supplierid" id="oa-supplierid-oaGoodsPriceViewPage" readonly value="<c:out value='${price.supplierid }' escapeXml='true'></c:out>" text="${supplier.name}"/></td>
                                <td class="len80 left">供应商编号：</td>
                                <td class="len180"><input class="easyui-validatebox len140" id="oa-supplierid2-oaGoodsPriceViewPage" data-options="required:false" value="${price.supplierid }" autocomplete="off" maxlength="50" readonly="readonly"/></td>
                            </tr>
                            <tr>
                                <td class="left">备&nbsp;注：</td>
                                <td class="" colspan="5">
                                    <textarea style="width: 658px; height: 50px; resize: none;"name="price.remark" id="oa-remark-oaGoodsPriceViewPage" maxlength="165" readonly="readonly"><c:out value="${price.remark }"></c:out></textarea>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="easyui-panel" data-options="border:false" title="商品明细清单" style="height: 327px;">
                        <input type="hidden" name="goodpricedetail" id="oa-detaillist-oaGoodsPriceViewPage"/>
                        <table id="oa-detail-oaGoodsPriceViewPage">
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
                        <div id="oa-comments-oaGoodsPriceViewPage">
                        </div>
                    </div>
                    <div style="border-top: 1px solid #AAA;">&nbsp;</div>
                    <div>
                        <div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
                        <div id="oa-attach-oaGoodsPriceViewPage" style="width: 765px;">
                        </div>
                    </div>
                    <div>
                        <div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
                        <div id="oa-comments2-oaGoodsPriceViewPage" style="width: 760px;">
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">

    <!--
    var $supplierid = $('#oa-supplierid-oaGoodsPriceViewPage');
    var $supplierid2 = $('#oa-supplierid2-oaGoodsPriceViewPage');

    var $detail = $('#oa-detail-oaGoodsPriceViewPage');

    var $form = $('#oa-form-oaGoodsPriceViewPage');
    var $line = $('#oa-detail-oaGoodsPriceViewPage');

    var $comments = $('#oa-comments-oaGoodsPriceViewPage');
    var $comments2 = $('#oa-comments2-oaGoodsPriceViewPage');
    var $attach = $('#oa-attach-oaGoodsPriceViewPage');
    var $oa_pricedatagrid = $('#oa-detail-oaGoodsPriceViewPage');
    $(function() {

    });
    var editItem = $("#oa-form-oaGoodsPriceViewPage");

    -->
</script>
</body>
</html>