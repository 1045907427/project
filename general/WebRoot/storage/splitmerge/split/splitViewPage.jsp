<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>拆分单查看页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
    <div class="easyui-layout" data-options="fit:true,border:false">
        <c:choose>
            <c:when test="${empty splitmerge or empty splitmerge.id }">
                <form id="storage-form-splitViewPage" action="storage/splitmerge/addSplit.do">
                    <input type="hidden" id="storage-status-splitViewPage" name="splitmerge.status" value="2"/>
                    <input type="hidden" id="storage-billtype-splitViewPage" name="splitmerge.billtype" value="1"/>
            </c:when>
            <c:otherwise>
                <form id="storage-form-splitViewPage" action="storage/splitmerge/editSplit.do">
                    <input type="hidden" id="storage-status-splitViewPage" name="splitmerge.status" value="${splitmerge.status }"/>
                    <input type="hidden" id="storage-billtype-splitViewPage" name="splitmerge.billtype" value="${splitmerge.billtype }"/>
            </c:otherwise>
        </c:choose>
            <input type="hidden" id="storage-detaillist-splitViewPage" name="detaillist"/>
            <input type="hidden" id="storage-summarybatchid-splitViewPage" name="splitmerge.summarybatchid" value="${splitmerge.summarybatchid }"/>
            <input type="hidden" id="storage-storagelocationid-splitViewPage" name="splitmerge.storagelocationid" value="${splitmerge.storagelocationid }"/>
            <input type="hidden" id="storage-produceddate-splitViewPage" name="splitmerge.produceddate" value="${splitmerge.produceddate }"/>
            <input type="hidden" id="storage-deadline-splitViewPage" name="splitmerge.deadline" value="${splitmerge.deadline }"/>
            <input type="hidden" id="storage-backid-splitViewPage" name="backid"/>
            <div data-options="region:'north',border:false">
                <div class="easyui-panel" data-options="fit:false,border:false" style="padding-left: 5px;">
                    <table id="storage-table-splitViewPage" class="main-table">
                        <tr>
                            <td class="len90 left">拆分单号：</td>
                            <td class="len150 left"><input type="text" class="len130 easyui-validatebox" id="storage-id-splitViewPage" name="splitmerge.id" value="${splitmerge.id }" readonly="readonly" /></td>
                            <td class="len100 right">业务日期：</td>
                            <td class="len150 left"><input type="text" class="len130 easyui-validatebox Wdate" id="storage-businessdate-splitViewPage" name="splitmerge.businessdate" data-options="required:true" value="${splitmerge.businessdate }" readonly="readonly"/></td>
                            <td class="len100 right">状　　态：</td>
                            <td class="len150 left">
                                <select class="len130 easyui-validatebox" disabled="disabled">
                                    <option value="1" <c:if test="${empty splitmerge.status}">selected="selected" </c:if> >新增</option>
                                    <option value="2" <c:if test="${splitmerge.status eq '2'}">selected="selected" </c:if> >保存</option>
                                    <option value="3" <c:if test="${splitmerge.status eq '3'}">selected="selected" </c:if> >审核通过</option>
                                </select>
                                <c:choose>
                                    <c:when test="${empty splitmerge.status }">
                                        <input type="hidden" id="storage-status-splitViewPage" value="2"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" id="storage-status-splitViewPage" value="${splitmerge.status }"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td class="left">拆装规则：</td>
                            <td class="left" colspan="3"><input type="text" class="len309 easyui-validatebox" id="storage-bomid-splitViewPage" name="splitmerge.bomid" value="${splitmerge.bomid }" readonly="readonly"/></td>
                            <td class="right">出库仓库：</td>
                            <td class="left"><input type="text" class="len130 easyui-validatebox" id="storage-storageid-splitViewPage" name="splitmerge.storageid" value="${splitmerge.storageid }" readonly="readonly"/></td>
                        </tr>
                        <tr>
                            <td class="left">商　品：</td>
                            <td class="left" colspan="3"><input type="text" class="len388 easyui-validatebox" id="storage-goodsid-splitViewPage" name="splitmerge.goodsid" value="${splitmerge.goodsid }" text="<c:out value='${splitmerge.goodsname }'/>" readonly="readonly"/><div id="storage-goodsid2-splitViewPage" style="line-height: 22px;"><c:if test="${not empty splitmerge.goodsid }" >　商品编号：<font color="#080">${splitmerge.goodsid }</font></c:if></div></td>
                            <td class="right">批　　次：</td>
                            <td class="left"><input type="text" class="len130 easyui-validatebox" id="storage-batchid-splitViewPage" name="splitmerge.batchid" value="${splitmerge.batchid }" readonly="readonly"/></td>
                            <%--
                            <td class="right">商品编码：</td>
                            <td class="left"><input type="text" class="len130 easyui-validatebox" id="storage-goodsid2-splitViewPage" readonly="readonly" value="${splitmerge.goodsid }"/></td>
                            --%>
                        </tr>
                        <tr>
                            <td class="left">单　价：</td>
                            <td class="left"><input type="text" class="easyui-numberbox" style="width: 130px;" id="storage-price-splitViewPage" name="splitmerge.price" data-options="required:true,min:0,precision:6,onChange:computeTotalamount,disabled:true" value="${splitmerge.price }"/></td>
                            <td class="right">数　　量：</td>
                            <td class="left"><input type="text" class="easyui-numberbox" style="width: 130px;" id="storage-unitnum-splitViewPage" name="splitmerge.unitnum" data-options="required:true,min:0,onChange:function(a,b){checkUsablenum(a,b);computeTotalamount();refreshDetailTotalnum();},disabled:true" value="${splitmerge.unitnum }"/></td>
                            <td class="right">金　　额：</td>
                            <td class="left">
                                <input type="text" class="easyui-numberbox" style="width: 130px;" id="storage-totalamount-splitViewPage" name="" data-options="required:false,min:0,precision:6,disabled:true" value="${splitmerge.price * splitmerge.unitnum }"/>
                            </td>
                        </tr>
                        <tr>
                            <td>条形码：</td>
                            <td><input type="text" id="storage-barcode-splitViewPage" name="splitmerge.barcode" class="len130" maxlength="165" value="<c:out value='${splitmerge.barcode}'/>" autocomplete="off" readonly="readonly" style="background: rgb(235, 235, 228);"/></td>
                            <td class="right">备　　注：</td>
                            <td colspan="5"><input type="text" id="storage-remark-splitViewPage" name="splitmerge.remark" style="width: 388px;" maxlength="165" value="<c:out value='${splitmerge.remark}'/>" autocomplete="off" readonly="readonly"/></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div data-options="region:'center',border:false">
                <div class="easyui-panel" data-options="fit:true,border:true">
                    <table id="storage-detail-splitViewPage">
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

    var $goodsid = $('#storage-goodsid-splitViewPage');
    var $goodsid2 = $('#storage-goodsid2-splitViewPage');
    var $storageid = $('#storage-storageid-splitViewPage');
    var $detail = $('#storage-detail-splitViewPage');
    var $bomid = $('#storage-bomid-splitViewPage');
    var $price = $('#storage-price-splitViewPage');
    var $unitnum = $('#storage-unitnum-splitViewPage');
    var $totalamount = $('#storage-totalamount-splitViewPage');
    var $batchid = $('#storage-batchid-splitViewPage');
    var $summarybatchid = $('#storage-summarybatchid-splitViewPage');
    var $storagelocationid = $('#storage-storagelocationid-splitViewPage');
    var $produceddate = $('#storage-produceddate-splitViewPage');
    var $deadline = $('#storage-deadline-splitViewPage');
    var $backid = $('#storage-backid-splitViewPage');

    var $form = $('#storage-form-splitViewPage');
    var $detaillist = $('#storage-detaillist-splitViewPage');

    $(function() {

        <c:if test="${not empty splitmerge.goodsid and not empty splitmerge.storageid }">
            loading('获取库存中...');
        /*
            // 获取商品的库存
            $.ajax({
                type: 'post',
                url: 'storage/showStorageSummaryList.do',
                data: {goodsid: '${splitmerge.goodsid }', groupcols: 'storageid,goodsid', page: 1, rows: 9999},
                dataType: 'json',
                success: function (json) {

                    loaded();

                    var storages = json.rows;
                    var storageids = new Array();
                    for (var i in storages) {

                        var storage = storages[i];
                        storageids.push(storage.storageid);

                        usablenums['${splitmerge.goodsid }' + ',' + storage.storageid] = storage.usablenum;
                    }
                }
            });
        */
            // 获取商品可用量
            $.ajax({
                type: 'post',
                url: 'storage/showStorageSummaryBatchList.do',
                data: {goodsid: '${splitmerge.goodsid }'/*, storageid: '${splitmerge.storageid }'*/},
                dataType: 'json',
                success: function (json) {

                    loaded();

                    var storages = json;
                    var storageids = new Array();
                    for (var i in storages) {

                        var storage = storages[i];
                        storageids.push(storage.storageid);

                        if((storage.batchno || '') == '') {
                            usablenums[storage.goodsid + ',' + storage.storageid] = storage.usablenum;
                        } else {
                            usablenums[storage.goodsid + ',' + storage.storageid + ',' + storage.batchno] = storage.usablenum;
                        }
                    }
                }
            });
        </c:if>
    });

    -->

</script>
</body>
</html>
