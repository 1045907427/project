<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>用友数据库连接信息添加</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form id="erp-form-connectionAddPage" method="post" action="erpconnect/addErpConnection.do">
        <table >
            <tr>
                <td>接入系统:</td>
                <td>
                    <select name="erpDb.relatesystem" style="width: 150px;" >
                        <option VALUE="T3">用友T3</option>
                        <option VALUE="T6">用友T6</option>
                        <option VALUE="U8">用友U8</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>数据库IP:</td>
                <td>
                    <input type="text" class="easyui-validatebox len150" data-options="required:true" name="erpDb.dbIP"/>
                </td>
            </tr>
            <tr>
                <td>数据库账套名:</td>
                <td>
                    <input type="text" class="easyui-validatebox len150" data-options="required:true"  class="len150"  name="erpDb.dbasename"/>
                </td>
            </tr>
            <tr>
                <td>数据库名:</td>
                <td>
                    <input type="text" class="len150" class="len150"  name="erpDb.dbname"/>
                </td>
            </tr>
            <tr>
                <td>用户名:</td>
                <td>
                    <input type="text" class="len150"  name="erpDb.dbusername"/>
                </td>
            </tr>
            <tr>
                <td>密码:</td>
                <td>
                    <input type="text" class="len150"  name="erpDb.dbpassword"/>
                </td>
            </tr>
            <tr>
                <td>默认数据库:</td>
                <td>
                    <select name="erpDb.isdefault" style="width: 150px;" >
                        <option VALUE="0">是</option>
                        <option VALUE="1">否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>对应部门:</td>
                <td>
                    <select id="erp-deptid-connectionAddPage" class="len150" name="erpDb.deptid">
                    <option value=""></option>
                    <c:forEach items="${deptList}" var="list">
                        <c:choose>
                            <c:when test="${list.id == plannedOrder.buydeptid}">
                                <option value="${list.id }" selected="selected">${list.name }</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${list.id }">${list.name }</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    </select>
                </td>
            </tr>

        </table>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align:right;">
            <input type="button" name="savegoon" id="erp-savegoon-connectionAddPage" value="保存"/>
        </div>
    </div>

</div>
<script type="text/javascript">

    $(function () {

        $("#erp-savegoon-connectionAddPage").click(function () {
            $("#erp-form-connectionAddPage").submit();
        })

        $("#erp-form-connectionAddPage").form({
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
                var json = $.parseJSON(data);
                if(json.flag){
                    $.messager.alert("提醒","新增成功");
                    var rowIndex = 0 ;
                    var rows =  $("#erp-datagrid-dbConnectList").datagrid('getRows');
                    for(var i = 0 ; i<rows.length ; ++ i){
                        ++ rowIndex;
                    }
                    $("#erp-datagrid-dbConnectList").datagrid('insertRow', {index: rowIndex, row: json.row}); //将数据更新到列表中
                }else{
                    $.messager.alert("提醒","新增失败");
                }
                $("#erp-datagrid-dbConnectList").datagrid("reload");
                $("#erp-dialog-addConnection-content").dialog("destroy");
            }
        });

    });




</script>

</body>
</html>
