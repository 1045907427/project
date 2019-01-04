<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>卖家信息新增页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true" id="ebshop-layout-ebSellerAddPage">
    <div data-options="region:'center',split:true,border:true" align="center">
        <form id="ebshop-form-ebSellerAddPage" action="ebseller/editEbSeller.do" method="post">
            <input type="hidden" id="ebSeller-oldsellernick" value="<c:out value="${ebSeller.sellernick}"></c:out>" />
            <input type="hidden" name="ebSeller.id" value="<c:out value="${ebSeller.id }"></c:out>" />
            <table cellpadding="3" cellspacing="3" border="0" >
                <tr>
                    <td>卖家昵称：</td>
                    <td><input type="text" name="ebSeller.sellernick" value="${ebSeller.sellernick}" readonly="readonly" class="len180"/></td>
                </tr>
                <tr>
                    <td>所属公司：</td>
                    <td><input type="text" name="ebSeller.company" value="${ebSeller.company}" readonly="readonly" class="len180"/></td>
                </tr>
                <tr>
                    <td>联系方式：</td>
                    <td><input type="text" name="ebSeller.phone" value="${ebSeller.phone}" readonly="readonly" class="len180"/></td>
                </tr>
                <tr>
                    <td>备注：</td>
                    <td><textarea rows="3" style="width: 173px;" name="ebSeller.remark" readonly="readonly">${ebSeller.remark}</textarea></td>
                </tr>
                <tr>
                    <td>状态：</td>
                    <td><select name="ebSeller.state" class="len180" disabled="disabled">
                        <c:forEach var="list" items="${stateList}">
                            <option value="${list.code }" <c:if test="${list.code == ebSeller.state}">selected="selected"</c:if>>${list.codename }</option>
                        </c:forEach>
                    </select></td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>
