<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>相关档案与会计科目关系</title>

</head>

<body>
<form action="erpconnect/syncCustomerByInterface.do" method="post" id="chooseCustomerClass-form-add">
    <input type="hidden" name="relstr" value="${param.relstr}"/>
    <input type="hidden" name="dbid" value="${param.dbid}"/>
    <div class="pageContent">
        <table>
            <tr>
                <td>客户分类:</td>
                <td>
                    <select name="customerclass">
                        <c:forEach var="list" items="${list}">
                            <option value="${list.code}">${list.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
    </div>
</form>
<script type="text/javascript">
    $(function(){
        $("#chooseCustomerClass-form-add").form({
            onSubmit: function(){
                var flag = $(this).form('validate');
                if(flag==false){
                    return false;
                }
                loading("同步中..");
            },
            success:function(data){
                //表单提交完成后 隐藏提交等待页面
                loaded();
                var json = $.parseJSON(data);
                loaded();
                if (json.flag) {
                    $.messager.alert("提醒", "同步成功。");
                    $("#erp-dialog-customerClass-content").dialog("destroy");
                } else {
                    $.messager.alert("提醒", "同步失败。" + json.msg);
                }
            }
        });

    });
</script>
</body>
</html>
