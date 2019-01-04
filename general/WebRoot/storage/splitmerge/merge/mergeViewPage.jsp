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
                <form id="storage-form-mergeViewPage" action="storage/splitmerge/addMerge.do">
                    <input type="hidden" id="storage-status-mergeViewPage" name="splitmerge.status" value="2"/>
                    <input type="hidden" id="storage-billtype-mergeViewPage" name="splitmerge.billtype" value="2"/>
            </c:when>
            <c:otherwise>
                <form id="storage-form-mergeViewPage" action="storage/splitmerge/editMerge.do">
                    <input type="hidden" id="storage-status-mergeViewPage" name="splitmerge.status" value="${splitmerge.status }"/>
                    <input type="hidden" id="storage-billtype-mergeViewPage" name="splitmerge.billtype" value="${splitmerge.billtype }"/>
            </c:otherwise>
        </c:choose>
            <input type="hidden" id="storage-detaillist-mergeViewPage" name="detaillist"/>
            <input type="hidden" id="storage-summarybatchid-mergeViewPage" name="splitmerge.summarybatchid" value="${splitmerge.summarybatchid }"/>
            <input type="hidden" id="storage-storagelocationid-mergeViewPage" name="splitmerge.storagelocationid" value="${splitmerge.storagelocationid }"/>
            <input type="hidden" id="storage-produceddate-mergeViewPage" name="splitmerge.produceddate" value="${splitmerge.produceddate }"/>
            <input type="hidden" id="storage-deadline-mergeViewPage" name="splitmerge.deadline" value="${splitmerge.deadline }"/>
            <input type="hidden" id="storage-backid-mergeViewPage" name="backid"/>
            <%-- isbatch: 主商品是否批次管理 --%>
            <input type="hidden" id="storage-isbatch-mergeViewPage" name="isbatch"/>
            <div data-options="region:'north',border:false">
                <div class="easyui-panel" data-options="fit:false" style="padding-left: 5px;">
                    <table id="storage-table-mergeViewPage" class="main-table">
                        <tr>
                            <td class="len90 left">组装单号：</td>
                            <td class="len150 left"><input type="text" class="len130 easyui-validatebox" id="storage-id-mergeViewPage" name="splitmerge.id" value="${splitmerge.id }" readonly="readonly" /></td>
                            <td class="len100 right">业务日期：</td>
                            <td class="len150 left">
                                <c:choose>
                                    <c:when test="${empty splitmerge.businessdate }">
                                        <input type="text" class="len130 easyui-validatebox Wdate" id="storage-businessdate-mergeViewPage" name="splitmerge.businessdate" data-options="required:true" value="<fmt:formatDate value='${today}' pattern='yyyy-MM-dd' type='date' dateStyle='long' />" readonly="readonly"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" class="len130 easyui-validatebox Wdate" id="storage-businessdate-mergeViewPage" name="splitmerge.businessdate" data-options="required:true" value="${splitmerge.businessdate }" readonly="readonly"/>
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
                                        <input type="hidden" id="storage-status-mergeViewPage" value="2"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" id="storage-status-mergeViewPage" value="${splitmerge.status }"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td class="left">拆装规则：</td>
                            <td class="left" colspan="3"><input type="text" class="len309 easyui-validatebox" id="storage-bomid-mergeViewPage" name="splitmerge.bomid" value="${splitmerge.bomid }" readonly="readonly"/></td>
                            <td class="right">入库仓库：</td>
                            <td class="left"><input type="text" class="len130 easyui-validatebox" id="storage-storageid-mergeViewPage" name="splitmerge.storageid" value="${splitmerge.storageid }" readonly="readonly"/></td>
                        </tr>
                        <tr>
                            <td class="left">商　品：</td>
                            <td class="left" colspan="3"><input type="text" class="len388 easyui-validatebox" id="storage-goodsid-mergeViewPage" name="splitmerge.goodsid" value="${splitmerge.goodsid }" text="<c:out value='${splitmerge.goodsname }'/>" readonly="readonly"/><div id="storage-goodsid2-mergeViewPage" style="line-height: 22px;"><c:if test="${not empty splitmerge.goodsid }" >　商品编号：<font color="#080">${splitmerge.goodsid }</font></c:if></div></td>
                            <td class="right">批　　次：</td>
                            <td class="left"><input type="text" class="len130 easyui-validatebox readonly-gray" id="storage-batchid-mergeViewPage" name="splitmerge.batchid" value="${splitmerge.batchid }" readonly="readonly"/></td>
                            <%--
                            <td class="right">商品编码：</td>
                            <td class="left"><input type="text" class="len130 easyui-validatebox" id="storage-goodsid2-mergeViewPage" readonly="readonly" value="${splitmerge.goodsid }"/></td>
                            --%>
                        </tr>
                        <tr>
                            <td class="left">单　价：</td>
                            <td class="left"><input type="text" class="easyui-numberbox" style="width: 130px;" id="storage-price-mergeViewPage" name="splitmerge.price" data-options="required:true,min:0,precision:6,disabled:true,onChange:computeTotalamount" value="${splitmerge.price }"/></td>
                            <td class="right">数　　量：</td>
                            <td class="left"><input type="text" class="easyui-numberbox" style="width: 130px;" id="storage-unitnum-mergeViewPage" name="splitmerge.unitnum" data-options="required:true,min:0,max:99999999,disabled:true,onChange:function(a,b){/*checkUsablenum(a,b);*/computeTotalamount();refreshDetailTotalnum();}" value="${splitmerge.unitnum }"/></td>
                            <td class="right">金　　额：</td>
                            <td class="left">
                                <input type="text" class="easyui-numberbox" style="width: 130px;" id="storage-totalamount-mergeViewPage" name="" data-options="required:false,min:0,precision:2,disabled:true" value="${splitmerge.price * splitmerge.unitnum }"/>
                            </td>
                        </tr>
                        <tr>
                            <td>条形码：</td>
                            <td><input type="text" id="storage-barcode-mergeViewPage" name="splitmerge.barcode" class="len130" maxlength="165" value="<c:out value='${splitmerge.barcode}'/>" autocomplete="off" readonly="readonly" style="background: rgb(235, 235, 228);"/></td>
                            <td class="right">备　　注：</td>
                            <td colspan="5"><input type="text" id="storage-remark-mergeViewPage" name="splitmerge.remark" style="width: 388px;" maxlength="165" value="<c:out value='${splitmerge.remark}'/>" autocomplete="off" readonly="readonly"/></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div data-options="region:'center',border:false">
                <div class="easyui-panel" data-options="fit:true,border:true">
                    <table id="storage-detail-mergeViewPage">
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

    var $goodsid = $('#storage-goodsid-mergeViewPage');
    var $goodsid2 = $('#storage-goodsid2-mergeViewPage');
    var $storageid = $('#storage-storageid-mergeViewPage');
    var $detail = $('#storage-detail-mergeViewPage');
    var $bomid = $('#storage-bomid-mergeViewPage');
    var $price = $('#storage-price-mergeViewPage');
    var $unitnum = $('#storage-unitnum-mergeViewPage');
    var $totalamount = $('#storage-totalamount-mergeViewPage');
    var $batchid = $('#storage-batchid-mergeViewPage');
    var $summarybatchid = $('#storage-summarybatchid-mergeViewPage');
    var $storagelocationid = $('#storage-storagelocationid-mergeViewPage');
    var $produceddate = $('#storage-produceddate-mergeViewPage');
    var $deadline = $('#storage-deadline-mergeViewPage');
    var $backid = $('#storage-backid-mergeViewPage');
    var $isbatch = $('#storage-isbatch-mergeViewPage');

    var $form = $('#storage-form-mergeViewPage');
    var $detaillist = $('#storage-detaillist-mergeViewPage');

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
