<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>拆装规则编辑页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
    <div class="easyui-layout" data-options="fit:true">
        <form id="basefiles-form-bomEditPage" action="basefiles/editBom.do" method="post">
            <input type="hidden" id="basefiles-detailjson-bomEditPage" name="detailjson"/>
            <div data-options="region:'north'">
                <div class="easyui-panel" data-options="fit:false" style="padding-left: 5px;">
                    <table id="basefiles-table-bomEditPage">
                        <tr>
                            <td class="len100 left">编　　号：</td>
                            <td class="len200 left"><input type="text" class="len150 easyui-validatebox" id="basefiles-id-bomEditPage" name="bom.id" value="${bom.id }" readonly="readonly" /></td>
                            <td class="len100 right">业务日期：</td>
                            <td class="len200 left">
                                <c:choose>
                                    <c:when test="${empty bom.businessdate}">
                                        <input type="text" class="len130 easyui-validatebox Wdate" id="basefiles-businessdate-bomEditPage" name="bom.businessdate" data-options="required:true" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="<fmt:formatDate value='${today}' pattern='yyyy-MM-dd' type='date' dateStyle='long' />"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" class="len130 easyui-validatebox Wdate" id="basefiles-businessdate-bomEditPage" name="bom.businessdate" data-options="required:true" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${bom.businessdate }"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="len100 left">　　状　　态：</td>
                            <td class="len200 left">
                                <select class="len150 easyui-validatebox" disabled="disabled">
                                    <c:choose>
                                        <c:when test="${empty bom.status }">
                                            <option value="2">新增</option>
                                        </c:when>
                                        <c:when test="${bom.status eq '0'}">
                                            <option value="0">禁用</option>
                                        </c:when>
                                        <c:when test="${bom.status eq '1'}">
                                            <option value="1">启用</option>
                                        </c:when>
                                        <c:when test="${bom.status eq '2'}">
                                            <option value="2">保存</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="2">保存</option>
                                        </c:otherwise>
                                    </c:choose>
                                </select>
                                <c:choose>
                                    <c:when test="${empty bom.status }">
                                        <input type="hidden" id="basefiles-status-bomEditPage" name="bom.status" value="2"/>
                                    </c:when>
                                    <c:when test="${bom.status eq '0'}">
                                        <input type="hidden" id="basefiles-status-bomEditPage" name="bom.status" value="0"/>
                                    </c:when>
                                    <c:when test="${bom.status eq '1'}">
                                        <input type="hidden" id="basefiles-status-bomEditPage" name="bom.status" value="1"/>
                                    </c:when>
                                    <c:when test="${bom.status eq '2'}">
                                        <input type="hidden" id="basefiles-status-bomEditPage" name="bom.status" value="2"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" id="basefiles-status-bomEditPage" name="bom.status" value="2"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td class="left">商　　品：</td>
                            <td class="left" colspan="3"><input type="text" class="len388 easyui-validatebox" id="basefiles-goodsid-bomEditPage" name="bom.goodsid" value="${bom.goodsid }" /></td>
                            <td class="left">　　商品编码：</td>
                            <td class="left"><span id="basefiles-goodsid2-bomEditPage">${bom.goodsid }</span></td>
                        </tr>
                        <tr>
                            <td class="left">拆装规则名称：</td>
                            <td class="left"><input type="text" class="len150 easyui-validatebox" id="basefiles-name-bomEditPage" name="bom.name" value="${bom.name }" data-options="required:true" maxlength="66" autocomplete="off"/></td>
                            <c:choose>
                                <c:when test="${empty bom.unitnum}">
                                    <td class="right">数　　量：</td>
                                    <td class="left"><input type="text" class="len130 easyui-numberbox" id="basefiles-unitnum-bomEditPage" name="bom.unitnum" data-options="required:true,min:1,max:1" value="1"/></td>
                                    <td class="left">　　箱装量：</td>
                                    <td class="left">
                                        <input type="text" class="len150 easyui-numberbox" id="basefiles-boxnum-bomEditPage" name="bom.boxnum" data-options="required:false,precision:${decimallen},min:0" value="0"/>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td class="right">数　　量：</td>
                                    <td class="left"><input type="text" class="len130 easyui-numberbox" id="basefiles-unitnum-bomEditPage" name="bom.unitnum" data-options="required:true,min:1,max:1" value="${bom.unitnum }"/></td>
                                    <td class="left">　　箱装量：</td>
                                    <td class="left">
                                        <input type="text" class="len150 easyui-numberbox" id="basefiles-boxnum-bomEditPage" name="bom.boxnum" data-options="required:false,precision:${decimallen},min:0" value="${bom.boxnum }"/>
                                    </td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                        <tr>
                            <td class="left">备　　注：</td>
                            <td class="left" colspan="5"><input type="text" class="len645 easyui-validatebox" id="basefiles-remark-bomEditPage" name="bom.remark" value="<c:out value='${bom.remark }'/>" autocomplete="off"/></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div data-options="region:'center'">
                <div class="easyui-panel" data-options="fit:true,border:true">
                    <table id="basefiles-detail-bomEditPage">
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

    var $goodsid = $('#basefiles-goodsid-bomEditPage');
    var $goodsid2 = $('#basefiles-goodsid2-bomEditPage');
    var $storageid = $('#basefiles-storageid-bomEditPage');
    var $name = $('#basefiles-name-bomEditPage');
    var $goodsdetail = $('#basefiles-detail-bomEditPage');

    var $unitnum = $('#basefiles-unitnum-bomEditPage');
    var $boxnum = $('#basefiles-boxnum-bomEditPage');
    //var $auxremainder = $('#basefiles-auxremainder-bomEditPage');

    var $detailjson = $('#basefiles-detailjson-bomEditPage');

    var $form = $('#basefiles-form-bomEditPage');

    -->

</script>
</body>
</html>
