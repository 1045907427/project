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
            <form action="" method="post" id="importModel-form-editPage" style="padding: 10px;">
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
                        <td align="left"><input type="text" id="importModel-edit-name" name="importSet.name" value="${importSet.name}" class="easyui-validatebox" required="true" style="width:200px;"/>
                        <input type="hidden" value="${importSet.id}" name="importSet.id"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="80px" align="left">模版url:</td>
                        <td align="left"><input type="text" id="importModel-edit-url"  name="importSet.url" value="${importSet.url}" class="easyui-validatebox" required="true"  style="width:200px;"/></td>
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
                            <input type="hidden" name="importSet.ctype" id="importModel-form-ctype" value="${importSet.ctype}"/>
                            <select id="importModel-form-editPage-ctype" style="width: 200px;">
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
                    <c:choose>
                        <c:when test="${flag}">
                            <c:if test="${importSet.modeltype == '1'}">
                                <tr>
                                    <td width="80px" align="left">导入类型:</td>
                                    <td align="left">
                                        <select name="importSet.gtype" style="width: 200px;" <c:if test="${!flag}">disabled</c:if>>
                                            <option value="1" <c:if test="${importSet.gtype == '1'}">selected="selected"</c:if> >按条形码导入</option>
                                            <option value="2" <c:if test="${importSet.gtype == '2'}">selected="selected"</c:if>>按店内码导入</option>
                                            <option value="3" <c:if test="${importSet.gtype == '3'}">selected="selected"</c:if>>按助记符导入</option>
                                            <option value="4" <c:if test="${importSet.gtype == '4'}">selected="selected"</c:if>>按商品编号导入</option>
                                        </select>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${importSet.modeltype == '2'}">
                                <tr>
                                    <td width="80px" align="left">导入类型:</td>
                                    <td align="left">
                                        <select name="importSet.gtype" style="width: 200px;"  <c:if test="${flag}">disabled</c:if>>
                                            <option value="1" <c:if test="${importSet.gtype == '1'}">selected="selected"</c:if>>按条形码导入</option>
                                            <option value="2" <c:if test="${importSet.gtype == '2'}">selected="selected"</c:if>>按商品编号导入</option>
                                        </select>
                                    </td>
                                </tr>
                            </c:if>
                        </c:when>
                    </c:choose>
                        <tr>
                            <td>客户编号：</td>
                            <td id="importModel-importSet-busid">
                                <input id="importModel-busid-EditPage" name="importSet.busid" style="width: 200px"/>
                            </td>
                        </tr>
                    <tr>
                        <td>取价方式</td>
                        <td>
                            <select name="importSet.pricetype" style="width: 200px;"  <c:if test="${!flag}">disabled</c:if>>
                                <option value=""></option>
                                <option value="0" <c:if test="${importSet.pricetype == 0}">selected="selected"</c:if>>取系统价格</option>
                                <option value="1" <c:if test="${importSet.pricetype == 1}">selected="selected"</c:if>>取导入时模板价格</option>
                            </select>
                        </td>
                    </tr>
                <c:if test="${importSet.remark != '' and importSet.remark != null}">
                    <tr>
                        <td>自适应类型：</td>
                        <td align="left">
                            <font style="color: #ff0000" name="importSet.remark" id="importModel-add-remark">${importSet.remark}</font>
                        </td>
                    </tr>
                </c:if>
                    <tr>
                        <td width="80px" align="left">状&nbsp;&nbsp;态:</td>
                        <td align="left">
                            <select disabled="disabled" >
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
                            <textarea name="importSet.fileparam" id="importModel-fileparam-EditPage" style="width: 200px;height: 60px;">${importSet.fileparam}</textarea>
                            <%--<input type="text" id="importModel-add-param"  name="importSet.fileparam" value="${importSet.fileparam}" style="width:200px;"/>--%>
                        </td>
                    </tr>
                    </c:if>
                </table>

            </form>
        </div>
    </div>
    <div data-options="region:'south'" style="height: 30px;border: 0px;">
        <div align="right">
            <a href="javaScript:void(0);" id="importModel-form-edit-save" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'">保存</a>
        </div>
    </div>
</div>
<script type="text/javascript">

    <c:choose>
    <c:when test="${importSet.ctype == '1' }">
    $("#importModel-busid-EditPage").customerWidget({
        singleSelect:true,
        initValue:'${importSet.busid}',
        required:true
    });
    $("#importModel-busid-EditPage").customerWidget('setValue','${importSet.busid}');
    $("#importModel-busid-EditPage").customerWidget('setText','${importSet.busname}');
    </c:when>
    <c:when test="${importSet.ctype == '2'}">
    $("#importModel-busid-EditPage").widget({
        referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
        singleSelect:true,
        initValue:'${importSet.busid}',
        required:true
    });
    </c:when>
    <c:otherwise>
    $("#importModel-busid-EditPage").addClass("readonly");
    document.getElementById("importModel-busid-EditPage").readOnly="readonly";
    </c:otherwise>
    </c:choose>
    $(function(){
        //供应商
        $("#importModel-supplierid-editPage").supplierWidget({
            required:true
        });

    <c:choose>
        <c:when test="${importSet.url == 'sales/model/importHtmlBydigitalParam.do' }">
        $("#importModel-form-editPage-ctype").addClass("readonly");
        $("#importModel-form-editPage-ctype").attr("disabled",true);
        </c:when>
        <c:otherwise>
        $("#importModel-form-editPage-ctype").combobox({
            onChange: function (newValue, oldValue) {
                $("#importModel-importSet-busid").empty();
                $("#importModel-form-ctype").val(newValue);
                if(newValue == 1){
                    $('<input id="importModel-busid-1" name="importSet.busid" style="width: 200px" />').
                            appendTo("#importModel-importSet-busid");
                    $("#importModel-busid-1").customerWidget({
                        singleSelect:true,
                        required:true
                    });
                }else if(newValue == 2){
                    $('<input id="importModel-busid-2" name="importSet.busid" style="width: 200px" />').
                            appendTo("#importModel-importSet-busid");
                    $("#importModel-busid-2").widget({
                        referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
                        singleSelect:true,
                        required:true
                    });
                }else{
                    $('<input id="importModel-busid-3" name="importSet.busid" value="" style="width: 200px" disabled/>').
                            appendTo("#importModel-importSet-busid");
                }
            }
        });
        </c:otherwise>
    </c:choose>


        $("#importModel-form-edit-save").click(function(){

            var name=$("#importModel-edit-name").val();
            if(name == ""){
                $.messager.alert("提醒","请填写模板描述名!");
                $("#importModel-edit-name").focus();
                return false;
            }
            var url=$("#importModel-edit-url").val();
            if(url == ""){
                $.messager.alert("提醒","请填写解析该模板对应的url!");
                $("#importModel-edit-url").focus();
                return false;
            }
            var modeltype = $("#importModel-edit-modeltype").val();
            if(modeltype == ""){
                $.messager.alert("提醒","请填写模板类型!");
                $("#importModel-edit-modeltype").focus();
                return false;
            }
            //自适应方法和普通的模板方法保存时进行区分
            if(url != "sales/model/importAdaptiveExcelByColumn.do" && url != "sales/model/importHtmlBydigitalParam.do"
            && url != "sales/model/importByDigitalParam.do"  && url != "crm/customerStorageOrder/importCustStorageModel.do"
                    && url != "crm/terminal/importCrmOrderModel.do"){
                var ctype = $("#importModel-form-ctype").val();
                if(ctype == ""){
                    $.messager.alert("提醒","请根据自适应类型填写客户类型!");
                    $("#importModel-form-edit-ctype").focus();
                    return false;
                }
                var busid = $("#importModel-busid-EditPage").val();
                if(ctype == '1' && busid == ""){
                    $.messager.alert("提醒","请指定客户编码!");
                    $("#importModel-busid-EditPage").focus();
                    return false;
                }
                if(ctype == '2' && busid == ""){
                    $.messager.alert("提醒","请填写总店编码!");
                    $("#importModel-busid-EditPage").focus();
                    return false;
                }
                var param = $("#importModel-fileparam-EditPage").val();
                if( param == ""){
                    $.messager.alert("提醒","自适应模板，请根据模板参数填写对应参数!");
                    $("#importModel-fileparam-EditPage").focus();
                    return false;
                }
            }else{
                var busid = $("#importModel-busid-EditPage").customerWidget('getValue');
                if(busid == ""){
                    $.messager.alert("提醒","请指定客户编码!");
                    $("#importModel-busid-EditPage").focus();
                    return false ;
                }
                var ctype = $("#importModel-form-ctype").val();
                if(ctype == "" || ctype == null){
                    $.messager.alert("提醒","请选择客户类型!");
                    $("#importModel-form-edit-ctype").focus();
                    return false;
                }
                var fileparam = $("#importModel-fileparam-EditPage").val();
                if(fileparam == ""){
                    $.messager.alert("提醒","请填写模板参数!");
                    $("#importModel-busid-addPage").focus();
                    return false ;
                }
            }

            $.messager.confirm("提醒","是否修改模板信息?",function(r){
                if(r){
                    var formdata=$("#importModel-form-editPage").serializeJSON();
                    loading('提交中..');
                    $.ajax({
                        type: 'post',
                        cache: false,
                        url: 'sales/import/editImportModel.do',
                        data:formdata,
                        dataType:'json',
                        success:function(data){
                            loaded();
                            if(data.flag){
                                $.messager.alert("提醒","修改成功!");
                                $("#saleOrderModel-dialog-edit-operate").dialog('close',true);
                                $("#saleOrderModel-query-importModelList").trigger("click");
                            }else{
                                if(data.msg){
                                    $.messager.alert("提醒","修改失败!"+data.msg);
                                }else{
                                    $.messager.alert("提醒","修改失败!");
                                }
                            }
                        }
                    });
                }else{
                    $("#saleOrderModel-table-importModel").datagrid('reload', 'sales/import/showImportModelData.do');
                }

            });
        });

    });

</script>

</body>
</html>
