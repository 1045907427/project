<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>数据源配置列表</title>
    <%@include file="/include.jsp" %>
</head>

<body>
<table id="system-table-sysDataSourceList"></table>
<div id="system-query-sysDataSourceList" style="padding:0px;height:auto">
    <div class="buttonBG" id="agprint-button-sysDataSource"></div>
    <form action="" id="system-form-sysDataSourceList" method="post" style="padding-top: 2px;">
        <table>
            <tr>
                <td>类别代码:</td>
                <td><input type="text" name="code" style="width:120px"/></td>
                <td>名称:</td>
                <td><input type="text" name="name" style="width:120px"/></td>
                <td>状态:</td>
                <td>
                    <select name="state" style="width:100px">
                        <option></option>
                        <option value="1">启用</option>
                        <option value="0">禁用</option>
                    </select>
                </td>
                <td>
                    <a href="javaScript:void(0);" id="system-query-querySysDataSourceList" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="system-query-reloadSysDataSourceList" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div style="display:none">
    <div id="system-dialog-sysDataSourceOper"></div>
</div>
<script type="text/javascript">
    $(function () {
        $("#agprint-button-sysDataSource").buttonWidget({
            //初始默认按钮 根据type对应按钮事件
            initButton: [
                {}
            ],
            buttons: [
                <security:authorize url="/system/sysdatasource/showSysDataSourceAddPageBtn.do">
                {
                    id: 'button-id-add',
                    name: '新增 ',
                    iconCls: 'button-add',
                    handler: function () {
                        sysDataSourceOpenDialog('数据源配置【新增】', 'system/sysdatasource/showSysDataSourceAddPage.do');
                    }
                },
                </security:authorize>
                <security:authorize url="/system/sysdatasource/showSysDataSourceEditPageBtn.do">
                {
                    id: 'button-id-edit',
                    name: '修改 ',
                    iconCls: 'button-edit',
                    handler: function () {
                        var dataRow = $("#system-table-sysDataSourceList").datagrid('getSelected');
                        if (dataRow == null) {
                            $.messager.alert("提醒", "请选择相应的数据源配置信息!");
                            return false;
                        }
                        sysDataSourceOpenDialog("数据源配置【更新】", 'system/sysdatasource/showSysDataSourceEditPage.do?id=' + dataRow.id);
                    }
                },
                </security:authorize>
                <security:authorize url="/system/sysdatasource/deleteSysDataSourceBtn.do">
                {
                    id: 'button-id-delete',
                    name: '删除',
                    iconCls: 'button-delete',
                    handler: function () {
                        var dataRow = $("#system-table-sysDataSourceList").datagrid('getSelected');
                        if (dataRow == null || dataRow.id == null || dataRow.id == "") {
                            $.messager.alert("提醒", "请选择相应的数据源配置信息!");
                            return false;
                        }
                        if (dataRow.state == '1') {
                            $.messager.alert("提醒", "抱歉，启用的打印内容数据源配置不能删除!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否删除该数据源配置信息？", function (r) {
                            if (r) {
                                loading("删除中..");
                                $.ajax({
                                    url: 'system/sysdatasource/deleteSysDataSource.do?id=' + dataRow.id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "删除成功");
                                            $("#system-query-querySysDataSourceList").trigger("click");
                                        } else {
                                            if (json.msg) {
                                                $.messager.alert("提醒", "删除失败!" + json.msg);
                                            } else {
                                                $.messager.alert("提醒", "删除失败!");
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/system/sysdatasource/enableSysDataSourceBtn.do">
                {
                    id: 'button-id-enable',
                    name: '启用 ',
                    iconCls: 'button-open',
                    handler: function () {
                        var dataRow = $("#system-table-sysDataSourceList").datagrid('getSelected');
                        if (dataRow == null) {
                            $.messager.alert("提醒", "请选择相应的数据源配置文件!");
                            return false;
                        }
                        if (dataRow.state == '1') {
                            $.messager.alert("提醒", "抱歉，启用的数据源配置不能被启用!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否启用该数据源配置文件？", function (r) {
                            if (r) {
                                loading("启用中..");
                                $.ajax({
                                    url: 'system/sysdatasource/enableSysDataSource.do?id=' + dataRow.id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "启用成功");
                                            $("#system-query-querySysDataSourceList").trigger("click");
                                        } else {
                                            if (json.msg) {
                                                $.messager.alert("提醒", "启用失败!" + json.msg);
                                            } else {
                                                $.messager.alert("提醒", "启用失败!");
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/system/sysdatasource/disableSysDataSourceBtn.do">
                {
                    id: 'button-id-disable',
                    name: '禁用',
                    iconCls: 'button-close',
                    handler: function () {
                        var dataRow = $("#system-table-sysDataSourceList").datagrid('getSelected');
                        if (dataRow == null) {
                            $.messager.alert("提醒", "请选择相应的数据源配置文件!");
                            return false;
                        }
                        if (dataRow.state == '0') {
                            $.messager.alert("提醒", "抱歉，已经禁用的数据源配置不能被禁用!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否禁用该数据源配置文件？", function (r) {
                            if (r) {
                                loading("禁用中..");
                                $.ajax({
                                    url: 'system/sysdatasource/disableSysDataSource.do?id=' + dataRow.id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "禁用成功");
                                            $("#system-query-querySysDataSourceList").trigger("click");
                                        } else {
                                            if (json.msg) {
                                                $.messager.alert("提醒", "禁用失败!" + json.msg);
                                            } else {
                                                $.messager.alert("提醒", "禁用失败!");
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                {
                    id: 'button-id-test',
                    name: '测试链接',
                    iconCls: 'button-edit',
                    handler: function () {
                        var dataRow = $("#system-table-sysDataSourceList").datagrid('getSelected');
                        if (dataRow == null) {
                            $.messager.alert("提醒", "请选择相应的数据源配置信息!");
                            return false;
                        }
                        $.getJSON("system/sysdatasource/testSysDataSource.do?id=" + dataRow.id,function(data){
                            $.messager.alert("测试链接", data.msg);
                        });
                    }
                }
            ],
            model: 'base',
            type: 'list',
            datagrid: 'system-table-sysDataSourceList',
            tname: 't_sys_datasource',
            //id:''
        });


        $("#system-table-sysDataSourceList").datagrid({
            fit: true,
            method: 'post',
            title: '',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            sortName: 'addtime',
            sortOrder: 'desc',
            singleSelect: true,
            toolbar: '#system-query-sysDataSourceList',
            url: 'system/sysdatasource/showSysDataSourcePageListData.do',
            columns: [[
                {field: 'id', title: '编号', width: 200, hidden: true},
                {field: 'name', title: '名称', width: 180},
                {field: 'code', title: '代码', width: 130},
                {field: 'jdbcdriver', title: 'JDBC驱动', width: 180},
                {field: 'jdbcurl', title: 'JDBC数据库连接', width: 200},
                {field: 'dbname', title: '数据库名称', width: 150},
                {field: 'dbuser', title: '数据库用户', width: 150},
                {
                    field: 'dbpasswd', title: '数据库密码', width: 80,
                    formatter: function (val) {
                        return '加密，不可见';
                    }
                },
                {
                    field: 'state', title: '状态', width: 80,
                    formatter: function (val) {
                        if (val == '1') {
                            return '启用';
                        }
                        else {
                            return '禁用';
                        }
                    }
                },
                {field: 'remark', title: '备注', width: 150}
            ]],
            onDblClickRow: function (index, dataRow) {
                sysDataSourceOpenDialog("数据源配置【查看】", 'system/sysdatasource/showSysDataSourceViewPage.do?id=' + dataRow.id);
            }
        });

        //查询
        $("#system-query-querySysDataSourceList").click(function () {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#system-form-sysDataSourceList").serializeJSON();
            $("#system-table-sysDataSourceList").datagrid("load", queryJSON);
        });
        //重置
        $('#system-query-reloadSysDataSourceList').click(function () {
            $("#system-sysDataSourceList-query-code").widget('clear');
            $("#system-form-sysDataSourceList")[0].reset();
            $("#system-table-sysDataSourceList").datagrid('loadData', {total: 0, rows: []});
        });

    });
    //验证名称是否重复
    function validUsedName(initValue, message) {
        var url = 'system/sysdatasource/isValidDataUsed.do?validtype=name';
        $.extend($.fn.validatebox.defaults.rules, {
            validUsedName: {
                validator: function (value, param) {
                    if (value == initValue) {
                        return true;
                    }
                    if (value.length <= param[0]) {
                        var data = sysDataSource_AjaxConnet({validdata: value}, url);
                        var json = $.parseJSON(data);
                        if (json.flag == true) {
                            $.fn.validatebox.defaults.rules.validUsedName.message = message;
                            return false;
                        } else {
                            return true;
                        }
                    }
                    else {
                        $.fn.validatebox.defaults.rules.validUsedName.message = '输入长度过长,请输入{0}个字符!';
                        return false;
                    }
                },
                message: ''
            }
        });
    }
    //验证分类代码
    function validUsedCode(initValue, message) {
        var url = 'system/sysdatasource/isValidDataUsed.do?validtype=code';
        $.extend($.fn.validatebox.defaults.rules, {
            validUsedCode: {
                validator: function (value, param) {
                    if (value == initValue) {
                        return true;
                    }
                    var reg = eval("/^[A-Za-z][A-Za-z0-9_]+$/");//正则表达式使用变量
                    if (reg.test(value) == true) {
                        if (value.length <= param[0]) {
                            var data = sysDataSource_AjaxConnet({validdata: value}, url);
                            var json = $.parseJSON(data);
                            if (json.flag == true) {
                                $.fn.validatebox.defaults.rules.validUsedCode.message = message;
                                return false;
                            } else {
                                return true;
                            }
                        } else {
                            $.fn.validatebox.defaults.rules.validUsedCode.message = '输入长度过长,请输入{0}个字符';
                            return false;
                        }
                    }
                    else {
                        $.fn.validatebox.defaults.rules.validUsedCode.message = '类别代码格式为由字母、数字或下划线组成2位或2位以上且字母开头';
                        return false;
                    }
                },
                message: ''
            }
        });
    }

    var sysDataSource_AjaxConnet = function (Data, Action, Str) {
        if (null != Str && "" != Str) {
            loading(Str);
        }
        var MyAjax = $.ajax({
            type: 'post',
            cache: false,
            url: Action,
            data: Data,
            async: false,
            success: function (data) {
                loaded();
            }
        });
        return MyAjax.responseText;
    }
    function sysDataSourceOpenDialog(title, url) {
        $('<div id="system-dialog-sysDataSourceOper-content"></div>').appendTo("#system-dialog-sysDataSourceOper");
        $('#system-dialog-sysDataSourceOper-content').dialog({
            title: title,
            //fit:true,
            width: 460,
            height: 500,
            closed: true,
            cache: false,
            href: url,
            maximizable: true,
            resizable: true,
            modal: true,
            onLoad: function () {
            },
            onClose: function () {
                $('#system-dialog-sysDataSourceOper-content').dialog("destroy");
            }
        });
        $('#system-dialog-sysDataSourceOper-content').dialog('open');
    }
</script>
</body>
</html>
