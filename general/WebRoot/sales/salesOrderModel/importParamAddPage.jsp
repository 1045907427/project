<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>模板参数信息页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="border:true">
        <div>
            <form action="" method="post" id="importParam-form-addPage" style="padding: 10px;">
                <input type="hidden" name="importSet.url" id="importParam-add-url" value="${url}"/>
                <input type="hidden" name="importSet.modeltype" id="importParam-add-modeltype"  value="${urltype}" />
                <table cellpadding="3" cellspacing="3" border="0">
                    <tr>
                        <td width="80px" align="left">模板描述名:</td>
                        <td align="left">
                            <input type="text" id="importParam-add-name" name="importSet.name" class="easyui-validatebox" required="true" style="width:150px;"/>
                            <font color="red">*</font>
                        </td>
                    </tr>
                    <c:if test="${colMap.supplierid != null}">
                    <tr>
                        <td>供应商编号：</td>
                        <td><input id="importParam-supplierid-addPage" name="importSet.supplierid" style="width: 150px" /><font color="red">*</font></td>
                    </tr>
                    </c:if>
                    <c:if test="${colMap.ctype == '2'}">
                        <tr>
                            <td width="80px" align="left">客户类型:</td>
                            <td align="left" id="importParam-tr-ctype">
                                <select id="importParam-form-add-ctype" name="importSet.ctype" style="width: 150px;">
                                    <option value="1">指定客户编号</option>
                                    <option value="2">指定总店按店号分配</option>
                                    <option value="3">按客户助记码导入</option>
                                    <option value="4">按客户名称导入</option>
                                    <option value="5">按客户简称导入</option>
                                    <option value="6">按客户地址导入</option>
                                    <option value="7">按客户编码导入</option>
                                </select>
                            </td>
                        </tr>
                    <tr id="importParam-customerid-tr" >
                        <td>客户编号:</td>
                        <td id="importParam-customerid-addPage">
                            <input id="importParam-busid-addPage" name="importSet.busid" style="width: 150px"/>&nbsp;<font color="red">*</font>
                        </td>
                    </tr>
                    <tr id="importParam-customercol-tr" hidden="true">
                        <td>客户单元格:</td>
                        <td>
                            <input name="importSet.customercell" id="importParam-customercell-addPage" style="width: 150px;" />
                            <font color="red">*</font>
                        </td>
                        <td>客户正则(管理员填):</td>
                        <td>
                            <input name="importSet.regularcustomer" id="importParam-regularcustomer-addPage" style="width: 150px;" />
                        </td>
                    </tr>
                    </c:if>
                    <c:if test="${colMap.ctype == '1'}">
                        <tr>
                            <td width="80px" align="left">客户类型:</td>
                            <td><input name="importSet.ctype" value="指定客户编号" style="width: 150px;" class="no_input"  readonly/></td>
                        </tr>
                        <tr>
                            <td>客户编号:</td>
                            <td><input id="importParam-busid-addPage" name="importSet.busid" style="width: 150px"/>&nbsp;<font color="red">*</font></td>
                        </tr>
                    </c:if>
                    <tr>
                        <td width="80px" align="left">状&nbsp;&nbsp;态:</td>
                        <td align="left">
                            <select name="importSet.state" style="width: 150px;">
                                <option value="1" selected>启用</option>
                                <option value="0">禁用</option>
                            </select>
                            <font color="red">*</font>
                        </td>
                    </tr>
                    <c:if test="${colMap.adaptive != null}">
                        <tr>
                            <td width="80px" align="left">首行:</td>
                            <td><input name="importSet.firstcol" style="width: 150px;" /></td>
                        </tr>
                        <tr>
                            <td width="80px" align="left">商品行:</td>
                            <td><input name="importSet.goodscol" style="width: 150px;" class="easyui-validatebox" data-options="required:true" /><font color="red">*</font></td>
                        </tr>
                        <tr>
                            <td width="80px" align="left">开始列:</td>
                            <td><input name="importSet.beginrow" style="width: 150px;" class="easyui-validatebox" data-options="required:true"/><font color="red">*</font></td>
                        </tr>
                    </c:if>
                    <c:if test="${colMap.param != null}">
                        <tr>
                            <td width="80px" align="left">商品标识:</td>
                            <td align="left">
                                <select name="importSet.gtype" style="width: 150px;">
                                    <option value="1">商品条形码</option>
                                    <c:if test="${colMap.goodsid == null}">
                                        <option value="2">商品店内码</option>
                                        <option value="3">商品助记符</option>
                                    </c:if>
                                    <option value="4">商品编码</option>
                                </select>
                            </td>
                            <c:if test="${colMap.word != null}">
                                <td>首行列文字:</td>
                            </c:if>
                            <c:if test="${colMap.word == null}">
                                <td>所在列:</td>
                            </c:if>
                            <td>
                                <input name="importSet.productcol" class="easyui-validatebox" style="width: 150px;"  data-options="required:true"  />
                                <font color="red">*</font>
                            </td>
                        </tr>
                        <tr>
                            <td width="80px" align="left">数量标识:</td>
                            <td>
                                <select name="importSet.numid" style="width: 150px;">
                                    <option value="0">商品数量</option>
                                    <option value="1">商品箱数</option>
                                </select>
                            </td>
                            <c:if test="${colMap.word != null}">
                                <td>首行列文字:</td>
                            </c:if>
                            <c:if test="${colMap.word == null}">
                                <td>所在列:</td>
                            </c:if>
                            <td>
                                <input name="importSet.numcol" class="easyui-validatebox" style="width: 150px;"  data-options="required:true"  />
                                <font color="red">*</font>
                            </td>
                        </tr>
                        <tr>
                        <c:if test="${colMap.digitalHtml == null}">
                            <td width="80px" align="left">客户单号位置:</td>
                        </c:if>
                        <c:if test="${colMap.digitalHtml != null}">
                            <td width="80px" align="left">数据去空:</td>
                        </c:if>
                            <td><input name="importSet.customercol" style="width: 150px;"/></td>
                        </tr>
                    </c:if>
                    <c:if test="${colMap.price != null}">
                        <tr>
                            <td width="80px" align="left">商品单价列:</td>
                            <td><input name="importSet.pricecol" style="width: 150px;" /></td>
                        </tr>
                    </c:if>
                    <c:if test="${colMap.retailprice != null}">
                        <tr>
                            <td width="80px" align="left">商品零售价列:</td>
                            <td><input name="importSet.pricecol" style="width: 150px;" /></td>
                        </tr>
                        <tr>
                            <td width="80px" align="left">商品成本价列:</td>
                            <td><input name="importSet.costpricecol" style="width: 150px;" /></td>
                        </tr>
                    </c:if>
                    <c:if test="${colMap.suppliername != null}">
                        <tr>
                            <td width="80px" align="left">供应商编码:</td>
                            <td><input name="importSet.suppliercol" style="width: 150px;" /><font color="red">*</font></td>
                        </tr>
                    </c:if>
                    <c:if test="${colMap.storage != null}">
                        <tr>
                            <td width="80px" align="left">仓库名称:</td>
                            <td><input name="importSet.storagecol" style="width: 150px;" /><font color="red">*</font></td>
                        </tr>
                    </c:if>
                    <c:if test="${colMap.digital != null}">
                       <tr>
                           <td width="80px" align="left">开始单元格:</td>
                           <td><input name="importSet.begincell"  class="easyui-validatebox" style="width: 150px;"  data-options="required:true"/><font color="red">*</font></td>
                       </tr>
                    </c:if>
                    <c:if test="${colMap.digitalHtml != null}">
                       <tr>
                           <td width="80px" align="left">有效列:</td>
                           <td><input name="importSet.validcol" style="width: 150px;" /><font color="red">*</font></td>
                           <td width="80px" align="left">数据位置:</td>
                           <td><input name="importSet.dataposition" style="width: 150px;" /></td>
                       </tr>
                    </c:if>
                        <tr>
                            <td width="80px" align="left">备注单元格:</td>
                            <td><input name="importSet.remarkcell" style="width: 150px;" /></td>
                        <c:if test="${colMap.digitalHtml != null}">
                            <td width="80px" align="left">模板首行:</td>
                            <td><input name="importSet.htmlFirstRow" style="width: 150px;" /></td>
                        </c:if>
                        </tr>
                    <c:if test="${colMap.divide != null}">
                        <tr>
                            <td width="80px" align="left">拆分所在列:</td>
                            <td><input name="importSet.dividecell" style="width: 150px;" /></td>
                        <c:if test="${colMap.dateother != null}">
                            <td width="80px" align="left">日期/其它列:</td>
                            <td><input name="importSet.othercol" style="width: 150px;" /></td>
                        </c:if>
                        </tr>
                    </c:if>
                </table>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    //客户
    $("#importParam-busid-addPage").customerWidget({
        singleSelect:true,
        required:true
    });
    //供应商
    $("#importParam-supplierid-addPage").supplierWidget({
        required:true
    });
    //客户类型变化
    $("#importParam-form-add-ctype").combobox({
        onChange: function (newValue, oldValue) {
            $("#importParam-customerid-addPage").empty();
            if(newValue == '1'){
                $('<input id="importModel-busid-addPage-1" name="importSet.busid" style="width: 150px" />').
                appendTo("#importParam-customerid-addPage");
                $("#importModel-busid-addPage-1").customerWidget({
                    singleSelect:true,
                    required:true
                });
                $("#importParam-customercol-tr").hide();
            }else if(newValue == '2'){
                $('<input id="importModel-busid-addPage-2" name="importSet.busid" style="width: 150px" />').
                appendTo("#importParam-customerid-addPage");
                $("#importModel-busid-addPage-2").widget({
                    referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
                    width:150,
                    singleSelect:true,
                    required:true
                });
                $("#importParam-customercol-tr").show();
            }else{
                $('<input id="importModel-busid-addPage-3" name="importSet.busid" style="width: 150px" disabled/>').
                appendTo("#importParam-customerid-addPage");
                $("#importParam-customercol-tr").show();
                $("#importParam-busid-addPage").hide();
            }
        }
    });

</script>
</body>
</html>
