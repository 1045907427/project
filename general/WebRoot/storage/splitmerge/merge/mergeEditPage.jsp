<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>组装单新增/编辑页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
    <div class="easyui-layout" data-options="fit:true,border:false">
        <c:choose>
            <c:when test="${empty splitmerge or empty splitmerge.id }">
                <form id="storage-form-mergeEditPage" action="storage/splitmerge/addMerge.do" method="post">
                    <input type="hidden" id="storage-status-mergeEditPage" name="splitmerge.status" value="2"/>
                    <input type="hidden" id="storage-billtype-mergeEditPage" name="splitmerge.billtype" value="2"/>
            </c:when>
            <c:otherwise>
                <form id="storage-form-mergeEditPage" action="storage/splitmerge/editMerge.do" method="post">
                    <input type="hidden" id="storage-status-mergeEditPage" name="splitmerge.status" value="${splitmerge.status }"/>
                    <input type="hidden" id="storage-billtype-mergeEditPage" name="splitmerge.billtype" value="${splitmerge.billtype }"/>
            </c:otherwise>
        </c:choose>
            <input type="hidden" id="storage-detaillist-mergeEditPage" name="detaillist" />
            <input type="hidden" id="storage-summarybatchid-mergeEditPage" name="splitmerge.summarybatchid" value="${splitmerge.summarybatchid }"/>
            <input type="hidden" id="storage-storagelocationid-mergeEditPage" name="splitmerge.storagelocationid" value="${splitmerge.storagelocationid }"/>
            <input type="hidden" id="storage-produceddate-mergeEditPage" name="splitmerge.produceddate" value="${splitmerge.produceddate }"/>
            <input type="hidden" id="storage-deadline-mergeEditPage" name="splitmerge.deadline" value="${splitmerge.deadline }"/>
            <input type="hidden" id="storage-backid-mergeEditPage" name="backid"/>
            <%-- isbatch: 主商品是否批次管理 --%>
            <input type="hidden" id="storage-isbatch-mergeEditPage" name="isbatch"/>
            <div data-options="region:'north',border:false">
                <div class="easyui-panel" data-options="fit:false" style="padding-left: 5px;">
                    <table id="storage-table-mergeEditPage" class="main-table">
                        <tr>
                            <td class="len90 left">组装单号：</td>
                            <td class="len150 left"><input type="text" class="len130 easyui-validatebox" id="storage-id-mergeEditPage" name="splitmerge.id" value="${splitmerge.id }" <c:if test="${autoCreate}" >readonly="readonly"</c:if><c:if test="${not autoCreate}" >required="required"</c:if> /></td>
                            <td class="len100 right">业务日期：</td>
                            <td class="len150 left">
                                <c:choose>
                                    <c:when test="${empty splitmerge.businessdate }">
                                        <input type="text" class="len130 easyui-validatebox Wdate" id="storage-businessdate-mergeEditPage" name="splitmerge.businessdate" data-options="required:true" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" class="len130 easyui-validatebox Wdate" id="storage-businessdate-mergeEditPage" name="splitmerge.businessdate" data-options="required:true" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${splitmerge.businessdate }"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="len100 right">状　　态：</td>
                            <td class="len150 left">
                                <select class="len130 easyui-validatebox" disabled="disabled">
                                    <option value="1" <c:if test="${empty splitmerge.status}">selected="selected" </c:if> >新增</option>
                                    <option value="2" <c:if test="${splitmerge.status eq '2'}">selected="selected" </c:if> >保存</option>
                                    <option value="3" <c:if test="${splitmerge.status eq '3'}">selected="selected" </c:if> >审核通过</option>
                                </select>
                                <c:choose>
                                    <c:when test="${empty splitmerge.status }">
                                        <input type="hidden" id="storage-status-mergeEditPage" value="2"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" id="storage-status-mergeEditPage" value="${splitmerge.status }"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td class="left">拆装规则：</td>
                            <td class="left" colspan="3"><input type="text" class="len309 easyui-validatebox" id="storage-bomid-mergeEditPage" name="splitmerge.bomid" value="${splitmerge.bomid }"/></td>
                            <td class="right">入库仓库：</td>
                            <td class="left"><input type="text" class="len130 easyui-validatebox" id="storage-storageid-mergeEditPage" name="splitmerge.storageid" value="${splitmerge.storageid }"/></td>
                        </tr>
                        <tr>
                            <td class="left">商　品：</td>
                            <td class="left" colspan="3"><input type="text" class="len388 easyui-validatebox" id="storage-goodsid-mergeEditPage" name="splitmerge.goodsid" value="${splitmerge.goodsid }" text="<c:out value='${splitmerge.goodsname }'/>"/><div id="storage-goodsid2-mergeEditPage" style="line-height: 22px;"><c:if test="${not empty splitmerge.goodsid }" >　商品编号：<font color="#080">${splitmerge.goodsid }</font></c:if></div></td>
                            <td class="right">批　　次：</td>
                            <td class="left"><input type="text" class="len130 easyui-validatebox readonly-gray" id="storage-batchid-mergeEditPage" name="splitmerge.batchid" value="${splitmerge.batchid }" readonly="readonly"/></td>
                            <%--
                            <td class="right">商品编码：</td>
                            <td class="left"><input type="text" class="len130 easyui-validatebox" id="storage-goodsid2-mergeEditPage" readonly="readonly" value="${splitmerge.goodsid }"/></td>
                            --%>
                        </tr>
                        <tr>
                            <td class="left">单　价：</td>
                            <td class="left"><input type="text" class="easyui-numberbox" style="width: 130px;" id="storage-price-mergeEditPage" name="splitmerge.price" data-options="required:true,min:0,precision:6,onChange:computeTotalamount" value="${splitmerge.price }" <c:if test="${editPrice ne '1'}">readonly="readonly"</c:if> /></td>
                            <td class="right">数　　量：</td>
                            <td class="left"><input type="text" class="easyui-numberbox" style="width: 130px;" id="storage-unitnum-mergeEditPage" name="splitmerge.unitnum" data-options="required:true,min:1,max:99999999,onChange:function(a,b){/*checkUsablenum(a,b);*/computeTotalamount();refreshDetailTotalnum();}" value="${splitmerge.unitnum }"/></td>
                            <td class="right">金　　额：</td>
                            <td class="left">
                                <input type="text" class="easyui-numberbox" style="width: 130px;" id="storage-totalamount-mergeEditPage" name="" data-options="required:false,min:0,precision:2,disabled:true" value="${splitmerge.price * splitmerge.unitnum }"/>
                            </td>
                        </tr>
                        <tr>
                            <td>条形码：</td>
                            <td><input type="text" id="storage-barcode-mergeEditPage" name="splitmerge.barcode" class="len130" maxlength="165" value="<c:out value='${splitmerge.barcode}'/>" autocomplete="off" readonly="readonly" style="background: rgb(235, 235, 228);"/></td>
                            <td class="right">备　　注：</td>
                            <td colspan="5"><input type="text" id="storage-remark-mergeEditPage" name="splitmerge.remark" style="width: 388px;" maxlength="165" value="<c:out value='${splitmerge.remark}'/>" autocomplete="off"/></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div data-options="region:'center',border:false">
                <div class="easyui-panel" data-options="fit:true,border:true">
                    <table id="storage-detail-mergeEditPage">
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
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">

    <!--

    var $goodsid = $('#storage-goodsid-mergeEditPage');
    var $goodsid2 = $('#storage-goodsid2-mergeEditPage');
    var $storageid = $('#storage-storageid-mergeEditPage');
    var $detail = $('#storage-detail-mergeEditPage');
    var $bomid = $('#storage-bomid-mergeEditPage');
    var $price = $('#storage-price-mergeEditPage');
    var $unitnum = $('#storage-unitnum-mergeEditPage');
    var $totalamount = $('#storage-totalamount-mergeEditPage');
    var $batchid = $('#storage-batchid-mergeEditPage');
    var $summarybatchid = $('#storage-summarybatchid-mergeEditPage');
    var $storagelocationid = $('#storage-storagelocationid-mergeEditPage');
    var $produceddate = $('#storage-produceddate-mergeEditPage');
    var $deadline = $('#storage-deadline-mergeEditPage');
    var $backid = $('#storage-backid-mergeEditPage');
    var $isbatch = $('#storage-isbatch-mergeEditPage');
    var $barcode = $('#storage-barcode-mergeEditPage');

    var $form = $('#storage-form-mergeEditPage');
    var $detaillist = $('#storage-detaillist-mergeEditPage');

    -->

</script>
</body>
</html>
