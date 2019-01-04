<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售订单模板信息添加</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:true">
        <div align="center">
            <form action="" method="post" id="importModel-form-view" style="padding: 10px;">
                <table cellpadding="3" cellspacing="3" border="0">
                    <tr>
                        <td width="80px" align="left">模板类型:</td>
                        <td align="left">
                            <select name="importSet.modeltype" style="width: 200px;" id="importModel-edit-modeltype" disabled>
                                <option value="1" <c:if test="${importSet.modeltype == '1'}">selected="selected"</c:if>>销售订单模板</option>
                                <option value="2" <c:if test="${importSet.modeltype == '2'}">selected="selected"</c:if>>代配送模板</option>
                                <option value="3" <c:if test="${importSet.modeltype == '3'}">selected="selected"</c:if>>客户销量模板</option>
                                <option value="4" <c:if test="${importSet.modeltype == '4'}">selected="selected"</c:if>>客户库存模板</option>
                                <option value="5" <c:if test="${importSet.modeltype == '5'}">selected="selected"</c:if>>销售退货通知单</option>
                                <option value="6" <c:if test="${importSet.modeltype == '6'}">selected="selected"</c:if>>采购退货通知单</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td width="80px" align="left">模板描述名:</td>
                        <td align="left"><input type="text" id="importModel-view-name" name="importSet.name" value="${importSet.name}" readonly="readonly" style="width:200px;"/>
                            <input type="hidden" value="${importSet.id}" name="importSet.id" />
                        </td>
                    </tr>
                    <tr>
                        <td width="80px" align="left">模版url:</td>
                        <td align="left"><input type="text" id="importModel-view-url"  name="importSet.url" value="${importSet.url}" readonly="readonly"  style="width:200px;"/></td>
                    </tr>
                    <c:if test="${importSet.modeltype == '2'}">
                        <tr>
                            <td>供应商编号：</td>
                            <td><input id="importModel-supplierid-editPage" name="importSet.supplierid" value="${importSet.supplierid}"
                                       text="<c:out value="${suppliername }"></c:out>" style="width: 200px" /></td>
                        </tr>
                    </c:if>
                    <tr>
                        <td width="80px" align="left">客户类型:</td>
                        <td align="left">
                            <select id="importModel-form-view-ctype" name="importSet.ctype" style="width: 200px;" disabled>
                                <option value="1" <c:if test="${importSet.ctype == '1'}">selected="selected"</c:if>>指定客户编号</option>
                                <option value="2" <c:if test="${importSet.ctype == '2'}">selected="selected"</c:if>>指定总店按店号分配</option>
                                <option value="3" <c:if test="${importSet.ctype == '3'}">selected="selected"</c:if>>按客户助记码导入</option>
                                <option value="4" <c:if test="${importSet.ctype == '4'}">selected="selected"</c:if>>按客户名称导入</option>
                                <option value="5" <c:if test="${importSet.ctype == '5'}">selected="selected"</c:if>>按客户简称导入</option>
                                <option value="6" <c:if test="${importSet.ctype == '6'}">selected="selected"</c:if>>按客户地址导入</option>
                                <option value="7" <c:if test="${importSet.ctype == '7'}">selected="selected"</c:if>>按客户编码导入</option>
                            </select>
                        </td>
                    </tr>
                    <%--<c:choose>--%>
                        <%--<c:when test="${flag}">--%>
                            <%--<c:if test="${importSet.modeltype == '1'}">--%>
                                <tr>
                                    <td width="80px" align="left">导入类型:</td>
                                    <td align="left">
                                        <select name="importSet.gtype" style="width: 200px;" disabled>
                                            <option value="1" <c:if test="${importSet.gtype == '1'}">selected="selected"</c:if>>按条形码导入</option>
                                            <option value="2" <c:if test="${importSet.gtype == '2'}">selected="selected"</c:if>>按店内码导入</option>
                                            <option value="3" <c:if test="${importSet.gtype == '3'}">selected="selected"</c:if>>按助记符导入</option>
                                            <option value="4" <c:if test="${importSet.gtype == '4'}">selected="selected"</c:if>>按商品编号导入</option>
                                        </select>
                                    </td>
                                </tr>
                            <%--</c:if>--%>
                            <%--<c:if test="${importSet.modeltype == '2'}">--%>
                                <%--<tr>--%>
                                    <%--<td width="80px" align="left">导入类型:</td>--%>
                                    <%--<td align="left">--%>
                                        <%--<select name="importSet.gtype" style="width: 200px;">--%>
                                            <%--<option value="1" <c:if test="${importSet.gtype == '1'}">selected="selected"</c:if>>按条形码导入</option>--%>
                                            <%--<option value="2" <c:if test="${importSet.gtype == '2'}">selected="selected"</c:if>>按商品编号导入</option>--%>
                                        <%--</select>--%>
                                    <%--</td>--%>
                                <%--</tr>--%>
                            <%--</c:if>--%>
                        <%--</c:when>--%>
                    <%--</c:choose>--%>
                    <tr>
                        <td>客户编号：</td>
                        <td id="importSet-view-busid">
                            <input id="importModel-busid" name="importSet.busid" value="${importSet.busid}" style="width: 200px" class="no_input" readonly="readonly"/>
                        </td>
                    </tr>
                    <c:if test="${flag}">
                    <tr>
                        <td>取价方式</td>
                        <td>
                            <select name="importSet.pricetype" style="width: 200px;" disabled>
                                <option value=""></option>
                                <option value="0" <c:if test="${importSet.pricetype == 0}">selected="selected"</c:if>>取系统价格</option>
                                <option value="1" <c:if test="${importSet.pricetype == 1}">selected="selected"</c:if>>取导入时模板价格</option>
                            </select>
                        </td>
                    </tr>
                    </c:if>
                    <c:if test="${importSet.remark != '' and importSet.remark != null}">
                        <tr>
                            <td>自适应类型：</td>
                            <td align="left">
                                <font style="color: #ff0000">${importSet.remark}</font>
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <td width="80px" align="left">状&nbsp;&nbsp;态:</td>
                        <td align="left">
                            <select  disabled="disabled">
                                <option value=""></option>
                                <option value="0" name="importSet.state" <c:if test="${importSet.state == 0}">selected="selected"</c:if>>禁用</option>
                                <option value="1" name="importSet.state" <c:if test="${importSet.state == 1}">selected="selected"</c:if>>启用</option>
                            </select>
                        </td>
                    </tr>
                    <c:if test="${filename != '' and filename != null}">
                    <tr>
                        <td width="80px" align="left">模板文件:</td>
                        <td align="left">
                            ${filename}
                        </td>
                    </tr>
                    </c:if>
                    <c:if test="${importSet.fileparam != '' and importSet.fileparam != null}">
                    <tr>
                        <td>参数信息：</td>
                        <td align="left">
                            <textarea name="importSet.fileparam" style="width: 200px;height: 60px;" readonly="readonly" >${importSet.fileparam}</textarea>
                        </td>
                    </tr>
                    </c:if>
                </table>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        //供应商
        $("#importModel-supplierid-editPage").supplierWidget({
            initvalue:"${importSet.supplierid}",
            required:true
        });

    })
</script>

</body>
</html>
