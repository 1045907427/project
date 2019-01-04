<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>拆分单新增/编辑页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
    <div class="easyui-layout" data-options="fit:true">
        <c:choose>
            <c:when test="${empty splitmerge or empty splitmerge.id }">
                <form id="storage-form-splitEditPage" action="storage/splitmerge/addSplit.do" method="post">
                    <input type="hidden" id="storage-status-splitEditPage" name="splitmerge.status" value="2"/>
                    <input type="hidden" id="storage-billtype-splitEditPage" name="splitmerge.billtype" value="1"/>
            </c:when>
            <c:otherwise>
                <form id="storage-form-splitEditPage" action="storage/splitmerge/editSplit.do" method="post">
                    <input type="hidden" id="storage-status-splitEditPage" name="splitmerge.status" value="${splitmerge.status }"/>
                    <input type="hidden" id="storage-billtype-splitEditPage" name="splitmerge.billtype" value="${splitmerge.billtype }"/>
            </c:otherwise>
        </c:choose>
            <input type="hidden" id="storage-detaillist-splitEditPage" name="detaillist"/>
            <input type="hidden" id="storage-summarybatchid-splitEditPage" name="splitmerge.summarybatchid" value="${splitmerge.summarybatchid }"/>
            <input type="hidden" id="storage-storagelocationid-splitEditPage" name="splitmerge.storagelocationid" value="${splitmerge.storagelocationid }"/>
            <input type="hidden" id="storage-produceddate-splitEditPage" name="splitmerge.produceddate" value="${splitmerge.produceddate }"/>
            <input type="hidden" id="storage-deadline-splitEditPage" name="splitmerge.deadline" value="${splitmerge.deadline }"/>
            <input type="hidden" id="storage-backid-splitEditPage" name="backid"/>
            <div data-options="region:'north'">
                <div class="easyui-panel" data-options="fit:false" style="padding-left: 5px;">
                    <table id="storage-table-splitEditPage" class="main-table">
                        <tr>
                            <td class="len90 left">拆分单号：</td>
                            <td class="len150 left"><input type="text" class="len130 easyui-validatebox" id="storage-id-splitEditPage" name="splitmerge.id" value="${splitmerge.id }" <c:if test="${autoCreate}" >readonly="readonly"</c:if><c:if test="${not autoCreate}" >required="required"</c:if> /></td>
                            <td class="len100 right">业务日期：</td>
                            <td class="len150 left">
                                <c:choose>
                                    <c:when test="${empty splitmerge.businessdate }">
                                        <input type="text" class="len130 easyui-validatebox Wdate" id="storage-businessdate-splitEditPage" name="splitmerge.businessdate" data-options="required:true" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" class="len130 easyui-validatebox Wdate" id="storage-businessdate-splitEditPage" name="splitmerge.businessdate" data-options="required:true" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${splitmerge.businessdate }"/>
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
                                        <input type="hidden" id="storage-status-splitEditPage" value="2"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" id="storage-status-splitEditPage" value="${splitmerge.status }"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td class="left">拆装规则：</td>
                            <td class="left" colspan="3"><input type="text" class="len309 easyui-validatebox" id="storage-bomid-splitEditPage" name="splitmerge.bomid" value="${splitmerge.bomid }"/></td>
                            <td class="right">出库仓库：</td>
                            <td class="left"><input type="text" class="len130 easyui-validatebox" id="storage-storageid-splitEditPage" name="splitmerge.storageid" value="${splitmerge.storageid }"/></td>
                        </tr>
                        <tr>
                            <td class="left">商　品：</td>
                            <td class="left" colspan="3"><input type="text" class="len388 easyui-validatebox" id="storage-goodsid-splitEditPage" name="splitmerge.goodsid" value="${splitmerge.goodsid }" text="<c:out value='${splitmerge.goodsname }'/>" /><div id="storage-goodsid2-splitEditPage" style="line-height: 22px;"><c:if test="${not empty splitmerge.goodsid }" >　商品编号：<font color="#080">${splitmerge.goodsid }</font></c:if></div></td>
                            <td class="right">批　　次：</td>
                            <td class="left"><input type="text" class="len130 easyui-validatebox" id="storage-batchid-splitEditPage" name="splitmerge.batchid" value="${splitmerge.batchid }"/></td>
                            <%--
                            <td class="right">商品编码：</td>
                            <td class="left"><input type="text" class="len130 easyui-validatebox" id="storage-goodsid2-splitEditPage" readonly="readonly" value="${splitmerge.goodsid }"/></td>
                            --%>
                        </tr>
                        <tr>
                            <td class="left">单　价：</td>
                            <td class="left"><input type="text" class="easyui-numberbox" style="width: 130px;" id="storage-price-splitEditPage" name="splitmerge.price" data-options="required:true,min:0,precision:6,onChange:computeTotalamount" value="${splitmerge.price }" <c:if test="${editPrice ne '1'}">readonly="readonly"</c:if> /></td>
                            <td class="right">数　　量：</td>
                            <td class="left"><input type="text" class="easyui-numberbox" style="width: 130px;" id="storage-unitnum-splitEditPage" name="splitmerge.unitnum" data-options="required:true,min:1,max:99999999,onChange:function(a,b){checkUsablenum(a,b);computeTotalamount();refreshDetailTotalnum();}" value="${splitmerge.unitnum }"/></td>
                            <td class="right">金　　额：</td>
                            <td class="left">
                                <input type="text" class="easyui-numberbox" style="width: 130px;" id="storage-totalamount-splitEditPage" name="" data-options="required:false,min:0,precision:2,disabled:true" value="${splitmerge.price * splitmerge.unitnum }"/>
                            </td>
                        </tr>
                        <tr>
                            <td>条形码：</td>
                            <td><input type="text" id="storage-barcode-splitEditPage" name="splitmerge.barcode" class="len130" maxlength="165" value="<c:out value='${splitmerge.barcode}'/>" autocomplete="off" readonly="readonly" style="background: rgb(235, 235, 228);"/></td>
                            <td class="right">备　　注：</td>
                            <td colspan="5"><input type="text" id="storage-remark-splitEditPage" name="splitmerge.remark" style="width: 388px;" maxlength="165" value="<c:out value='${splitmerge.remark}'/>" autocomplete="off"/></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div data-options="region:'center'">
                <div class="easyui-panel" data-options="fit:true,border:true">
                    <table id="storage-detail-splitEditPage">
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

    var $goodsid = $('#storage-goodsid-splitEditPage');
    var $goodsid2 = $('#storage-goodsid2-splitEditPage');
    var $storageid = $('#storage-storageid-splitEditPage');
    var $detail = $('#storage-detail-splitEditPage');
    var $bomid = $('#storage-bomid-splitEditPage');
    var $price = $('#storage-price-splitEditPage');
    var $unitnum = $('#storage-unitnum-splitEditPage');
    var $totalamount = $('#storage-totalamount-splitEditPage');
    var $batchid = $('#storage-batchid-splitEditPage');
    var $summarybatchid = $('#storage-summarybatchid-splitEditPage');
    var $storagelocationid = $('#storage-storagelocationid-splitEditPage');
    var $produceddate = $('#storage-produceddate-splitEditPage');
    var $deadline = $('#storage-deadline-splitEditPage');
    var $backid = $('#storage-backid-splitEditPage');
    var $barcode = $('#storage-barcode-splitEditPage');

    var $form = $('#storage-form-splitEditPage');
    var $detaillist = $('#storage-detaillist-splitEditPage');

    -->

</script>
</body>
</html>
