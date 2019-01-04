<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>新增客户费用合同条款</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form action="" method="post" id="contractCaluse-form-addContractCaluse">
            <div data-options="region:'north',border:false" style="height:140px;">
                <table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
                    <tr>
                        <td class="len70 left">条款编号:</td>
                        <td style="text-align: left;">
                            <input type="text" id="contractCaluse-id-addContractCaluse" name="contractCaluse.id" value="${contractCaluse.id}"  style="width: 150px"  class="easyui-validatebox"  readonly="readonly"/>
                        </td>
                        <td class="len70 left">名称:</td>
                        <td style="text-align: left;">
                            <input type="text" id="contractCaluse-name-addContractCaluse" name="contractCaluse.name" value="${contractCaluse.name}" style="width: 150px"  class="easyui-validatebox"  readonly="readonly"/>
                        </td>
                        <td class="len70 left">状态:</td>
                        <td style="text-align: left;">
                            <select id="contractCaluse-state-addContractCaluse"  style="width:150px;"  readonly="readonly">
                                <c:forEach items="${stateList }" var="list">
                                    <c:choose>
                                        <c:when test="${list.code == contractCaluse.state}">
                                            <option value="${list.code }" selected="selected">${list.codename }</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${list.code }">${list.codename }</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                            <input type="hidden" name="contractCaluse.state" value="${contractCaluse.state }" />
                        </td>
                    </tr>
                    <tr>
                        <td class="len70 left">条款类型:</td>
                        <td style="text-align: left;">
                            <select id="contractCaluse-type-addContractCaluse"   name="contractCaluse.type"   style="width:150px;"  >
                                <c:forEach items="${calusetypeList }" var="list">
                                    <c:choose>
                                        <c:when test="${list.code == contractCaluse.type}">
                                            <option value="${list.code }" selected="selected">${list.codename }</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${list.code }">${list.codename }</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </td>
                        <td class="len70 left">费用类型:</td>
                        <td style="text-align: left;">
                            <select id="contractCaluse-costtype-addContractCaluse" name="contractCaluse.costtype"  style="width:150px;" >
                                <option value="0" <c:if test="${contractCaluse.costtype == '0' }"> selected="selected"</c:if> >可变费用</option>
                                <option value="1" <c:if test="${contractCaluse.costtype == '1' }"> selected="selected"</c:if>>固定费用</option>
                            </select>
                        </td>
                        <td class="len70 left">分摊方式:</td>
                        <td style="text-align: left;">
                            <select id="contractCaluse-sharetype-addContractCaluse" name="contractCaluse.sharetype" style="width:150px;" >
                                <option value="0" <c:if test="${contractCaluse.sharetype == '0' }"> selected="selected"</c:if>>一次性分摊</option>
                                <option value="1" <c:if test="${contractCaluse.sharetype == '1' }"> selected="selected"</c:if>>分月平摊</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="len70 left">支付类型:</td>
                        <td style="text-align: left;">
                            <select id="contractCaluse-returntype-addContractCaluse" name="contractCaluse.returntype" style="width:150px;" >
                                <option value="0" <c:if test="${contractCaluse.returntype == '0' }"> selected="selected"</c:if>>月返</option>
                                <option value="1" <c:if test="${contractCaluse.returntype == '1' }"> selected="selected"</c:if>>季返</option>
                                <option value="2" <c:if test="${contractCaluse.returntype == '2' }"> selected="selected"</c:if>>年返</option>
                            </select>
                        </td>
                        <td class="len70 left">支付月份:</td>
                        <td style="text-align: left;">
                            <input type="text" id="contractCaluse-returnmonthnum-addContractCaluse" name="contractCaluse.returnmonthnum" value="${contractCaluse.returnmonthnum}" style="width: 150px"  />
                        </td> <td class="len70 left">费用科目:</td>
                        <td style="text-align: left;">
                            <input type="text" id="contractCaluse-subjectexpenses-addContractCaluse" name="contractCaluse.subjectexpenses" value="${contractCaluse.subjectexpenses}" style="width: 150px"  class="easyui-validatebox"  required="required"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="len70 left">支付要求:</td>
                        <td style="text-align: left;">
                            <select id="contractCaluse-returnrequire-addContractCaluse" name="contractCaluse.returnrequire" style="width:150px;" >
                                <option value="0" <c:if test="${contractCaluse.returnrequire == '0' }"> selected="selected"</c:if>>无要求</option>
                                <option value="1" <c:if test="${contractCaluse.returnrequire == '1' }"> selected="selected"</c:if>>销售达成</option>
                            </select>
                        </td>
                        <td class="len70 left">备注:</td>
                        <td style="text-align: left;" colspan="3">
                            <input type="text" id="contractCaluse-remark-addContractCaluse" name="contractCaluse.remark" value="${contractCaluse.remark}" style="width: 370px"  />
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align: right;">
            快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
            <input type="button" value="保存" name="savegoon" id="contractCaluse-savegoon-addContractCaluse" />
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function() {
        var returntype1 ='${contractCaluse.returntype }';
        if("0" == returntype1){
            $("#contractCaluse-returnmonthnum-addContractCaluse").val("0");
            $("#contractCaluse-returnmonthnum-addContractCaluse").attr("readonly","readonly");
        }else{
            $("#contractCaluse-returnmonthnum-addContractCaluse").val("1");
            $("#contractCaluse-returnmonthnum-addContractCaluse").removeAttr("readonly");
        }
        $("#contractCaluse-returntype-addContractCaluse").change(function () {
            if("0" == $(this).val()){
                $("#contractCaluse-returnmonthnum-addContractCaluse").val("0");
                $("#contractCaluse-returnmonthnum-addContractCaluse").attr("readonly","readonly");

            }else{
                $("#contractCaluse-returnmonthnum-addContractCaluse").removeAttr("readonly");
            }
        })
        $("#contractCaluse-returnmonthnum-addContractCaluse").change(function () {
            var returntype = $("#contractCaluse-returntype-addContractCaluse").val();
            if("1" == returntype){
                if($(this).val() > 3){
                    $(this).val("3");
                }else if($(this).val() < 1){
                    $(this).val("1");
                }else {
                    $(this).val(parseInt($(this).val()));
                }
            }else if("2" == returntype) {
                if($(this).val() > 12){
                    $(this).val("12");
                }else if($(this).val() < 1){
                    $(this).val("1");
                }else {
                    $(this).val(parseInt($(this).val()));
                }
            }
        })


        $("#contractCaluse-savegoon-addContractCaluse").click(function(){
            editContractCaluse(false);
        });
        $("#contractCaluse-form-addContractCaluse").form({
            onSubmit: function(){
                var flag = $(this).form('validate');
                if(flag==false){
                    return false;
                }
                loading("提交中..");
            },
            success:function(data){
                //表单提交完成后 隐藏提交等待页面
                loaded();
                //转为json对象
                var json = $.parseJSON(data);
                if(json.flag){
                    $.messager.alert("提醒",'修改成功！');
                    $('#contractCaluse-dialog-content').dialog("destroy");
                    refresh();
                }else{
                    $.messager.alert("提醒",'修改失败！'+json.msg);
                }
            }
        });
    })
    $("#contractCaluse-subjectexpenses-addContractCaluse").widget({
        referwid:'RT_T_BASE_FINANCE_EXPENSES_SORT',
        width:150,
        singleSelect:true,
        onlyLeafCheck:true,
        required:true,
    });
</script>
</body>
</html>
