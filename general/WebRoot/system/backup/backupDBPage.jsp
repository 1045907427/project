<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>数据库备份</title>
    <%@include file="/include.jsp" %>
</head>

<body>
<table id="dataparam-table"></table>

<div id="dataparam-toolbar" style="padding:0px;height:auto">
    <div class="buttonBG">
        <security:authorize url="/system/backup/saveBackup.do">
            <a href="javaScript:void(0);" id="dataparam-button-save" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">保存当前配置</a>
        </security:authorize>
        <%--<security:authorize url="/manufacturerdata/saveDataParam.do">--%>
            <%--<a href="javaScript:void(0);" id="dataparam-button-test" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">数据库连接测试</a>--%>
        <%--</security:authorize>--%>
        <security:authorize url="/system/backup/doBackup.do">
            <a href="javaScript:void(0);" id="dataparam-button-doBackup" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">手动备份数据库</a>
        </security:authorize>
        <security:authorize url="/system/backup/delete.do">
            <a href="javaScript:void(0);" id="dataparam-button-delete" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">删除过期文件</a>
        </security:authorize>
    </div>
    <span style="margin-left:10px;font-size: 13px;font-weight: bold;">数据库备份基本配置</span>
    <form action="" method="post" id="dataparam-form-param">
        <table cellpadding="2px" cellspacing="2px" border="0px" style="padding: 20px;">
            <%--<tr>--%>
                <%--<td align="left">mysqldump路径:</td>--%>
                <%--<td align="left"><input type="text" name="backupDB.dumpurl"  value="${backupDB.dumpurl}"  class="easyui-validatebox" required="true" style="width:200px;" /></td>--%>
                <%--<td align="left">mysqldump.exe,mysqldump.pdb,mysqldumpslow.pl三个文件的所在目录。例如windows下：D:/mysqldump,linux下:/usr/bin/mysqldump。请根据实际配置填写!!!</td>--%>
            <%--</tr>--%>
            <tr>
                <td align="left">备份保存路径:</td>
                <td align="left"><input type="text" name="backupDB.fileurl"  value="${backupDB.fileurl}"  class="easyui-validatebox" required="true" style="width:200px;" /></td>
                <td align="left">备份文件保存的路径，加上文件名。例如windows下：E:/备份文件。linux下注意路径。请勿随意修改!下面文件列表根据此参数获取!</td>
            </tr>
            <%--<tr>--%>
                <%--<td align="left">upload文件夹路径:</td>--%>
                <%--<td align="left"><input type="text" name="backupDB.updatepath"  value="${backupDB.updatepath}"  class="easyui-validatebox" required="true" style="width:200px;" /></td>--%>
                <%--<td align="left">upload文件夹路径，例如：E:/upload,就会备份upload文件夹下的所有文件。linux下注意路径</td>--%>
            <%--</tr>--%>
            <tr>
                <td align="left">备份保存天数:</td>
                <td align="left"><input type="text" name="backupDB.savedaynum"  value="${backupDB.savedaynum}"  class="easyui-validatebox" required="true" style="width:200px;" /></td>
                <td align="left"></td>
            </tr>
        </table>
    </form>
    <span style="margin-left:10px;font-size: 13px;font-weight: bold;">备份文件列表</span>
    <table id="dataparam-datagrid-param"></table>
</div>
</body>
<script type="text/javascript">

    $(function () {
        var tableColJson=$("#dataparam-datagrid-param").createGridColumnLoad({
            frozenCol:[[]],
            commonCol:[[
                {field:'filename',title:'备份文件',width:200,
                    formatter:function(value,rowData,rowIndex){
                            return value+" (<a href=\"system/backup/downloadDBFile.do?filename="+value+"\" target=\"_blank\">下载</a>)";
                    }
                },
            ]]
        });
        $("#dataparam-datagrid-param").datagrid({ //采购入库单明细信息编辑
            authority:tableColJson,
            columns: tableColJson.common,
            frozenColumns: tableColJson.frozen,
            border: true,
            rownumbers: true,
            showFooter: true,
            striped:true,
            fit:true,
            singleSelect: false,
            checkOnSelect:true,
            selectOnCheck:true,
            data:JSON.parse('${detailList}'),
        }).datagrid('columnMoving');

        $("#dataparam-button-save").click(function () {
            $.messager.confirm("提醒", "确定保存配置信息？", function (r) {
                if (r) {
                    $("#dataparam-form-param").attr("action", "system/backup/saveBackup.do");
                    backup_form_submit()
                    $("#dataparam-form-param").submit();
                }
            });
        });


        $("#dataparam-button-doBackup").click(function () {
            loading("备份中，可能需要较长时间，请等候..");
            $.getJSON("system/backup/doBackup.do",function(data){
                loaded();
                $.messager.alert("手动备份", data.msg);
                if(data.flag){
                    doGzip(data.filename);
                }
            });
        });
    })
    function doGzip(filename) {
        loading("备份成功，正在压缩备份文件，可能需要较长时间，请等候..");
        $.getJSON("system/backup/doBackupFileToGzip.do?filename="+filename,function(data){
            loaded();
            $.messager.alert("压缩备份文件", "压缩备份文件成功");
            top.updateTab("/system/backup/showBackupDBPage.do","数据库备份");
        });
    }

    $("#dataparam-button-delete").click(function () {
        $.getJSON("system/backup/delete.do",function(data){
            $.messager.alert("删除过期文件", "删除过期文件成功");
            top.updateTab("/system/backup/showBackupDBPage.do","数据库备份");
        });
    });
    function backup_form_submit() {
        $("#dataparam-form-param").form({
            onSubmit: function () {
                loading("保存中..");
            },
            success: function (data) {
                loaded();
                var json = $.parseJSON(data);
                if (json.flag ) {
                    $.messager.alert("提醒", "保存成功");
                    top.updateTab("/system/backup/showBackupDBPage.do","数据库备份");
                    return false;
                }
                else {
                    $.messager.alert("提醒", "保存失败");
                    return false;
                }
            }
        });
    }
</script>
</html>

