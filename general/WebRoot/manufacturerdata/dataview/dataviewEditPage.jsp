<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/10/10
  Time: 13:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form action="" method="post" id="dataview-form-addDateview">
            <input type="hidden"  name="oldviewname"  value="${dataview.viewname}" />
            <table  border="0" class="box_table" id="dataview-sql-table">
                <input type="hidden" id="dataview-oldviewname-addDateview" name="oldviewname"  value="${dataview.viewname}"/>
                <tr>
                    <td width="60">视图名称:</td>
                    <td style="text-align: left;">
                        <input type="text" id="dataview-viewname-addDateview" name="viewname"  value="${dataview.viewname}" style="width: 150px"  class="easyui-validatebox"  required="required"/>
                    </td>
                    <td colspan="4"></td>
                </tr>
                <tr>
                    <td width="120">sql预览:</td>
                    <td style="text-align: left;" colspan="3">
                        <textarea rows="" cols="" id="dataview-sqlview-addDateview"  style="width: 452px;height:150px;"  name="sqlview"><c:out value="${dataview.viewsql }"></c:out></textarea>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align: right;">
            快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
            <input type="button" value="保存" name="savegoon" id="dataview-savegoon-addDateview" />
        </div>
    </div>
</div>

<script type="text/javascript">

    $(function() {
        $("#dataview-savegoon-addDateview").click(function(){
            editDataView(false);
        });

        $("#dataview-form-addDateview").form({
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
                    $.messager.alert("提醒",'修改成功');
                    $("#dataview-dialog-content").dialog('destroy');
                    refresh();
                }else{
                    $.messager.alert("警告",'修改失败');
                }
            }
        });
    })



</script>
</body>
</html>
