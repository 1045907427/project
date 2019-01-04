<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>流程节点用户设置(会签)</title>
</head>
<body>
<style type="text/css">
    .signSettingTable td {
        padding: 2px;
    }
    .mapping-from-box {
        border: solid 1px #aaa;
        margin: 2px;
        float: left;
        line-height: 20px;
        width: 95%;
    }
    .mapping-to-box {
        border: solid 1px #aaa;
        margin: 2px;
        float: left;
        line-height: 20px;
        width: 95%;
    }
    #acitivti-mappingtable1-signTaskUserSettingPage td{
        padding: 0px;
        padding-bottom: 4px;;
    }
    .height18 {
        height: 15px;
        margin-bottom: 3px;
    }
    .customerHeaderStyle {
        filter: none;
        padding: 0px;
    }
    .customerHeaderStyle > .panel-title {
    }
</style>
<div style="border-bottom:0px solid #7babcf;">
    <a class="easyui-linkbutton" id="activiti-save1-signTaskUserSettingPage" data-options="plain:true,iconCls:'button-save'">保存</a>
</div>
<%--<div style="border:1px solid #7babcf; margin:5px; background: #efefef;">--%>
    <%--<ol>--%>
        <%--<li>每个会签节点请务必配置人员规则，否则工作将无法转交至该节点。</li>--%>
    <%--</ol>--%>
<%--</div>--%>
<div id="activiti-panel-definitionOtherSettingPage" class="easyui-panel" style="padding: 3px; margin: 5px;" title="人员规则">
    <form id="activiti-form1-signTaskUserSettingPage" action="act/addAuthRule.do" method="post">
        <input type="hidden" name="type" value="${param.type }" />
        <input type="hidden" name="definitionkey" value="${param.definitionkey }" />
        <input type="hidden" name="taskkey" value="${param.taskkey }" />
        <input type="hidden" name="rule" id="activiti-rule-signTaskUserSettingPage" value="${rule }" />
        <table cellpadding="0" cellspacing="0" class="signSettingTable" style="border-collapse:collapse;border:1px solid #7babcf;width:100%;table-layout: fixed; margin-top: 3px;">
            <tr data="01">
                <td style="width:120px;"><label><input type="checkbox" value="01" data="01" class="userSettingCheckbox2"/>指定人员</label></td>
                <td data="01" style="border-right: none;width: 210px;">
                    <span><input id="activiti-assignee-signTaskUserSettingPage" class="userid" name="userid01" value="" /></span>
                </td>
                <td style="border-left: none"></td>
            </tr>
            <tr data="04">
                <td><label><input type="checkbox" value="04" data="04" class="userSettingCheckbox2"/>指定角色</label></td>
                <td data="04" style="border-right: none">
                    <span><input id="activiti-role2-signTaskUserSettingPage" class="roleid" name="roleid04" value=""/></span>
                </td>
                <td style="border-left: none"></td>
            </tr>
            <c:if test="${param.type eq 'aud'}">
                <tr data="02" <c:if test="${param.first }">style="display: none;"</c:if>>
                    <td><label><input type="checkbox" value="02" data="02" class="userSettingCheckbox2"/>本部门指定角色</label></td>
                    <td data="02" style="border-right: none">
                        <span><input id="activiti-role-signTaskUserSettingPage" class="roleid" name="roleid02" value=""/></span>
                    </td>
                    <td style="border-left: none"></td>
                </tr>
                <tr data="03" <c:if test="${param.first }">style="display: none;"</c:if>>
                    <td><label><input type="checkbox" value="03" data="03" class="userSettingCheckbox2"/>本部门所有人</label></td>
                    <td data="03" style="border-right: none">
                    </td>
                    <td style="border-left: none"></td>
                </tr>
            </c:if>
            <tr data="05">
                <td><label><input type="checkbox" value="05" data="05" class="userSettingCheckbox2"/>指定部门</label></td>
                <td data="05" style="border-right: none">
                    <span><input id="activiti-department-signTaskUserSettingPage" class="deptid" name="deptid05" value="" /></span>
                </td>
                <td style="border-left: none"></td>
            </tr>
            <tr data="06">
                <td style=""><label><input type="checkbox" value="06" data="06" class="userSettingCheckbox2"/>指定部门与角色</label></td>
                <td data="06" style="border-right: none;">
                    <span><input id="activiti-department2-signTaskUserSettingPage" class="deptid" name="deptid06" value="" /></span>
                </td>
                <td style="border-left: none;">
                    <span><input id="activiti-role3-signTaskUserSettingPage" class="roleid" name="roleid06" value="" /></span>
                </td>
            </tr>
            <tr data="07">
                <td><label><input type="checkbox" value="07" data="07" class="userSettingCheckbox2"/>指定岗位</label></td>
                <td data="07" style="border-right: none">
                    <span><input id="activiti-post-signTaskUserSettingPage" class="postid" name="postid07" value="" /></span>
                </td>
                <td style="border-left: none"></td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    <!--

    $(function(){

        loading('加载中...');

        $('input.userSettingCheckbox2').each(function() {

            var v = $(this).attr('data');
            if(v == '${rule }') {

                $(this).attr("checked", true);
                // return false;
            }
        });

        $('#activiti-form1-signTaskUserSettingPage input[type=checkbox]').click(function() {

            var rule = new Array();
            $('#activiti-form1-signTaskUserSettingPage input[type=checkbox]').each(function() {

                if($(this)[0].checked) {
                    rule.push($(this).val());
                }
            });

            $('#activiti-rule-signTaskUserSettingPage').val(rule.join(','));

            var required = $(this).is(':checked');

            if($(this).val() == '01') {

                $("#activiti-assignee-signTaskUserSettingPage").validatebox({required: required});
                $("#activiti-assignee-signTaskUserSettingPage").validatebox('validate');
            } else if($(this).val() == '02') {

                $("#activiti-role-signTaskUserSettingPage").validatebox({required: required});
                $("#activiti-role-signTaskUserSettingPage").validatebox('validate');
            } else if($(this).val() == '03') {

            } else if($(this).val() == '04') {

                $("#activiti-role2-signTaskUserSettingPage").validatebox({required: required});
                $("#activiti-role2-signTaskUserSettingPage").validatebox('validate');
            } else if($(this).val() == '05') {

                $("#activiti-department-signTaskUserSettingPage").validatebox({required: required});
                $("#activiti-department-signTaskUserSettingPage").validatebox('validate');
            } else if($(this).val() == '06') {

                $("#activiti-role3-signTaskUserSettingPage").validatebox({required: required});
                $("#activiti-role3-signTaskUserSettingPage").validatebox('validate');

                $("#activiti-department2-signTaskUserSettingPage").validatebox({required: required});
                $("#activiti-department2-signTaskUserSettingPage").validatebox('validate');

            } else if($(this).val() == '07') {

                $("#activiti-post-signTaskUserSettingPage").validatebox({required: required});
                $("#activiti-post-signTaskUserSettingPage").validatebox('validate');

            }
        });

        $('#activiti-form2-signTaskUserSettingPage input[type=checkbox]').click(function() {

            var rule = new Array();
            $('#activiti-form2-signTaskUserSettingPage input[type=checkbox]').each(function() {

                if($(this)[0].checked) {
                    rule.push($(this).val());
                }
            });

            $('#activiti-rule-signTaskUserSettingPage').val(rule.join(','));

            var required = $(this).is(':checked');

            if($(this).val() == '01') {

                $("#activiti-assignee-signTaskUserSettingPage").validatebox({required: required});
                $("#activiti-assignee-signTaskUserSettingPage").validatebox('validate');
            } else if($(this).val() == '02') {

                $("#activiti-role-signTaskUserSettingPage").validatebox({required: required});
                $("#activiti-role-signTaskUserSettingPage").validatebox('validate');
            } else if($(this).val() == '03') {

            } else if($(this).val() == '04') {

                $("#activiti-role2-signTaskUserSettingPage").validatebox({required: required});
                $("#activiti-role2-signTaskUserSettingPage").validatebox('validate');
            } else if($(this).val() == '05') {

                $("#activiti-department-signTaskUserSettingPage").validatebox({required: required});
                $("#activiti-department-signTaskUserSettingPage").validatebox('validate');
            } else if($(this).val() == '06') {

                $("#activiti-role3-signTaskUserSettingPage").validatebox({required: required});
                $("#activiti-role3-signTaskUserSettingPage").validatebox('validate');

                $("#activiti-department2-signTaskUserSettingPage").validatebox({required: required});
                $("#activiti-department2-signTaskUserSettingPage").validatebox('validate');

            } else if($(this).val() == '07') {

                $("#activiti-post-signTaskUserSettingPage").validatebox({required: required});
                $("#activiti-post-signTaskUserSettingPage").validatebox('validate');
            }
        });

        $.ajax({
            type: 'post',
            dataType: 'json',
            url: 'act/getMultiAuthruleSetting.do',
            data: {definitionkey: '${param.definitionkey }', taskkey: '${param.taskkey }', type: '${param.type }'},
            success: function(json) {

                loaded();
                var ruleArr = new Array();

                for(var key in json){

                    if(json[key].rule != undefined && json[key].rule != null && json[key].rule != '') {

                        var rule = json[key].rule;

                        $('#activiti-form1-signTaskUserSettingPage td[data=' + rule + '] input[type!=hidden]').validatebox({required:true});

                        var userid = json[key].userid;
                        var deptid = json[key].deptid;
                        var roleid = json[key].roleid;
                        var postid = json[key].postid;

                        $('#activiti-form1-signTaskUserSettingPage input[data=' + rule + ']').attr('checked', 'checked');

                        ruleArr.push(rule);

                        $('#activiti-form1-signTaskUserSettingPage tr[data=' + rule + ']').find('.userid').val(userid);
                        $('#activiti-form1-signTaskUserSettingPage tr[data=' + rule + ']').find('.deptid').val(deptid);
                        $('#activiti-form1-signTaskUserSettingPage tr[data=' + rule + ']').find('.roleid').val(roleid);
                        $('#activiti-form1-signTaskUserSettingPage tr[data=' + rule + ']').find('.postid').val(postid);
                    }

                }
                $('#activiti-rule-signTaskUserSettingPage').val(ruleArr.join(','))

                var rules = ruleArr.join(',');

                //// 初始化通用控件
                $("#activiti-assignee-signTaskUserSettingPage").widget({
                    async: false,
                    referwid: 'RT_T_SYS_USER',
                    singleSelect:false,
                    width:154,
                    treePName:true,
                    onlyLeafCheck:false,
                    required: rules.indexOf('01') >= 0 ? true : false
                });
                $("#activiti-department-signTaskUserSettingPage").widget({
                    async: false,
                    referwid: 'RT_T_SYS_DEPT',
                    singleSelect:false,
                    width:154,
                    treePName:true,
                    onlyLeafCheck:false,
                    required: rules.indexOf('05') >= 0 ? true : false
                });
                $("#activiti-department2-signTaskUserSettingPage").widget({
                    async: false,
                    referwid: 'RT_T_SYS_DEPT',
                    singleSelect:false,
                    width:154,
                    treePName:true,
                    onlyLeafCheck:false,
                    required: rules.indexOf('06') >= 0 ? true : false
                });
                $("#activiti-role3-signTaskUserSettingPage").widget({
                    async: false,
                    referwid: 'RL_T_AC_AUTHORITY',
                    width:150,
                    singleSelect: true,
                    required: rules.indexOf('06') >= 0 ? true : false
                });
                $("#activiti-role-signTaskUserSettingPage").widget({
                    async: false,
                    referwid: 'RT_T_AC_AUTHORITY',
                    width:154,
                    singleSelect: false,
                    required: rules.indexOf('02') >= 0 ? true : false
                });
                $("#activiti-role2-signTaskUserSettingPage").widget({
                    async: false,
                    referwid: 'RT_T_AC_AUTHORITY',
                    width:154,
                    singleSelect: false,
                    required: rules.indexOf('04') >= 0 ? true : false
                });
                $("#activiti-post-signTaskUserSettingPage").widget({
                    async: false,
                    referwid: 'RL_T_BASE_WORKJOB',
                    width:154,
                    required: rules.indexOf('07') >= 0 ? true : false
                });
                ////
            },
            error: function() { }
        });

        $('#activiti-form1-signTaskUserSettingPage').form({
            onSubmit: function() {

                var flag = $('#activiti-form1-signTaskUserSettingPage').form('validate');
                if(!flag) {

                    return false;
                }

                if($('.userSettingCheckbox2:checked').length == 0) {
                    $.messager.alert('提醒', '未选择规则！');
                    return false;
                }

                loading('保存中...');
            },
            success: function(data) {

                loaded();
                var json = $.parseJSON(data);
                if(json.flag) {
                    $.messager.alert('提醒', '保存成功。');
                    initUserTask({definitionkey: '${param.definitionkey }', taskkey: '${param.taskkey }', type: '${param.type }'});
                    return false;
                }
                $.messager.alert('提醒', '保存失败！');
                initUserTask({definitionkey: '${param.definitionkey }', taskkey: '${param.taskkey }', type: '${param.type }'});
                //$("#activiti-dialog2-definitionUserSettingPage").dialog('close');
            }
        });

        // 保存1
        $('#activiti-save1-signTaskUserSettingPage').click(function() {
            $('#activiti-form1-signTaskUserSettingPage').submit();
        });

        // 按发起人widget
        // 发起人
        $("#activiti-assignee2-signTaskUserSettingPage").widget({
            async: false,
            referwid: 'RT_T_SYS_USER',
            singleSelect:false,
            width:150,
            treePName:true,
            onlyLeafCheck:false,
            required: false
        });
        // 指定角色
        $("#activiti-role4-signTaskUserSettingPage").widget({
            async: false,
            referwid: 'RL_T_AC_AUTHORITY',
            width:150,
            singleSelect: false,
            required: false
        });
        // 指定部门
        $("#activiti-department3-signTaskUserSettingPage").widget({
            async: false,
            referwid: 'RT_T_SYS_DEPT',
            singleSelect:false,
            width:150,
            treePName:true,
            onlyLeafCheck:false,
            required: false
        });
        // 指定角色与部门
        $("#activiti-department4-signTaskUserSettingPage").widget({
            async: false,
            referwid: 'RT_T_SYS_DEPT',
            singleSelect:false,
            width:150,
            treePName:true,
            onlyLeafCheck:false,
            required: false
        });
        $("#activiti-role5-signTaskUserSettingPage").widget({
            async: false,
            referwid: 'RL_T_AC_AUTHORITY',
            width:150,
            singleSelect: false,
            required: false
        });
        // 指定岗位
        $("#activiti-post2-signTaskUserSettingPage").widget({
            async: false,
            referwid: 'RL_T_BASE_WORKJOB',
            width:150,
            singleSelect: false,
            required: false
        });
        // 指定人员
        $("#activiti-assignee3-signTaskUserSettingPage").widget({
            async: false,
            referwid: 'RT_T_SYS_USER',
            singleSelect:true,
            width:150,
            treePName:true,
            onlyLeafCheck:true,
            required: false
        });
        // 指定角色
        $("#activiti-role6-signTaskUserSettingPage").widget({
            async: false,
            referwid: 'RL_T_AC_AUTHORITY',
            width:150,
            singleSelect: true,
            required: false
        });
        // 指定部门
        $("#activiti-department5-signTaskUserSettingPage").widget({
            async: false,
            referwid: 'RT_T_SYS_DEPT',
            singleSelect:true,
            width:150,
            treePName:true,
            onlyLeafCheck:false,
            required: false
        });
        // 指定角色与部门
        $("#activiti-department6-signTaskUserSettingPage").widget({
            async: false,
            referwid: 'RT_T_SYS_DEPT',
            singleSelect:true,
            width:150,
            treePName:true,
            onlyLeafCheck:false,
            required: false
        });
        $("#activiti-role7-signTaskUserSettingPage").widget({
            async: false,
            referwid: 'RL_T_AC_AUTHORITY',
            width:150,
            singleSelect: true,
            required: false
        });
        // 指定岗位
        $("#activiti-post3-signTaskUserSettingPage").widget({
            async: false,
            referwid: 'RL_T_BASE_WORKJOB',
            width:150,
            singleSelect: true,
            required: false
        });

        //
        $('#activiti-form2-signTaskUserSettingPage .userSettingCheckbox3').click(function() {

            var v = $(this).val();
            var required = $(this).is(':checked');

            if(v == '01') {

                $('#activiti-assignee2-signTaskUserSettingPage').validatebox({required: required});
                $('#activiti-assignee2-signTaskUserSettingPage').validatebox('validate');
            } else if(v == '04') {

                $('#activiti-role4-signTaskUserSettingPage').validatebox({required: required});
                $('#activiti-role4-signTaskUserSettingPage').validatebox('validate');
            } else if(v == '05') {

                $('#activiti-department3-signTaskUserSettingPage').validatebox({required: required});
                $('#activiti-department3-signTaskUserSettingPage').validatebox('validate');
            } else if(v == '06') {

                $('#activiti-department4-signTaskUserSettingPage').validatebox({required: required});
                $('#activiti-department4-signTaskUserSettingPage').validatebox('validate');

                $('#activiti-role5-signTaskUserSettingPage').validatebox({required: required});
                $('#activiti-role5-signTaskUserSettingPage').validatebox('validate');
            } else if(v == '07') {

                $('#activiti-post2-signTaskUserSettingPage').validatebox({required: required});
                $('#activiti-post2-signTaskUserSettingPage').validatebox('validate');
            }
        });
        //
        $('#activiti-form2-signTaskUserSettingPage .userSettingCheckbox4').click(function() {

            $('#activiti-form2-signTaskUserSettingPage .userSettingCheckbox4').not($(this)).each(function() {

                $(this)[0].checked = false;
            });

            var v = $(this).val();
            var required = $(this).is(':checked');

            $('#activiti-assignee3-signTaskUserSettingPage').validatebox({required: false});
            $('#activiti-assignee3-signTaskUserSettingPage').validatebox('validate');
            $('#activiti-role6-signTaskUserSettingPage').validatebox({required: false});
            $('#activiti-role6-signTaskUserSettingPage').validatebox('validate');
            $('#activiti-department5-signTaskUserSettingPage').validatebox({required: false});
            $('#activiti-department5-signTaskUserSettingPage').validatebox('validate');
            $('#activiti-department6-signTaskUserSettingPage').validatebox({required: false});
            $('#activiti-department6-signTaskUserSettingPage').validatebox('validate');
            $('#activiti-role7-signTaskUserSettingPage').validatebox({required: false});
            $('#activiti-role7-signTaskUserSettingPage').validatebox('validate');
            $('#activiti-post3-signTaskUserSettingPage').validatebox({required: false});
            $('#activiti-post3-signTaskUserSettingPage').validatebox('validate');

            if(v == '01') {

                $('#activiti-assignee3-signTaskUserSettingPage').validatebox({required: required});
                $('#activiti-assignee3-signTaskUserSettingPage').validatebox('validate');
            } else if(v == '04') {

                $('#activiti-role6-signTaskUserSettingPage').validatebox({required: required});
                $('#activiti-role6-signTaskUserSettingPage').validatebox('validate');
            } else if(v == '05') {

                $('#activiti-department5-signTaskUserSettingPage').validatebox({required: required});
                $('#activiti-department5-signTaskUserSettingPage').validatebox('validate');
            } else if(v == '06') {

                $('#activiti-department6-signTaskUserSettingPage').validatebox({required: required});
                $('#activiti-department6-signTaskUserSettingPage').validatebox('validate');

                $('#activiti-role7-signTaskUserSettingPage').validatebox({required: required});
                $('#activiti-role7-signTaskUserSettingPage').validatebox('validate');
            } else if(v == '07') {

                $('#activiti-post3-signTaskUserSettingPage').validatebox({required: required});
                $('#activiti-post3-signTaskUserSettingPage').validatebox('validate');
            }
        });

        // 初始化列表
        {

            $.ajax({
                type: 'post',
                url: 'act/selectAuthMapping.do',
                data: {definitionkey: '${param.definitionkey }', taskkey: '${param.taskkey }', mappingtype: '1'},
                dataType: 'json',
                success: function(json) {

                    for(var i in json.list) {

                        var mapping = json.list[i];
                        var fromrule = mapping.fromrule;
                        var fromuserid = mapping.fromuserid;
                        var fromroleid = mapping.fromroleid;
                        var fromdeptid = mapping.fromdeptid;
                        var frompostid = mapping.frompostid;
                        var torule = mapping.torule;
                        var touserid = mapping.touserid;
                        var toroleid = mapping.toroleid;
                        var todeptid = mapping.todeptid;
                        var topostid = mapping.topostid;

                        var iid = getRandomid();
                        var iid1 = 'activiti-' + (iid + 1) + '-signTaskUserSettingPage';
                        var iid2 = 'activiti-' + (iid + 2) + '-signTaskUserSettingPage';
                        var iid3 = 'activiti-' + (iid + 3) + '-signTaskUserSettingPage';
                        var iid4 = 'activiti-' + (iid + 4) + '-signTaskUserSettingPage';
                        var iid5 = 'activiti-' + (iid + 5) + '-signTaskUserSettingPage';
                        var iid6 = 'activiti-' + (iid + 6) + '-signTaskUserSettingPage';
                        var iid7 = 'activiti-' + (iid + 7) + '-signTaskUserSettingPage';
                        var iid8 = 'activiti-' + (iid + 8) + '-signTaskUserSettingPage';

                        $('<input type="text" id="' + iid1 + '" />').appendTo('#activiti-dummy1-signTaskUserSettingPage');
                        $('<input type="text" id="' + iid2 + '" />').appendTo('#activiti-dummy1-signTaskUserSettingPage');
                        $('<input type="text" id="' + iid3 + '" />').appendTo('#activiti-dummy1-signTaskUserSettingPage');
                        $('<input type="text" id="' + iid4 + '" />').appendTo('#activiti-dummy1-signTaskUserSettingPage');
                        $('<input type="text" id="' + iid5 + '" />').appendTo('#activiti-dummy1-signTaskUserSettingPage');
                        $('<input type="text" id="' + iid6 + '" />').appendTo('#activiti-dummy1-signTaskUserSettingPage');
                        $('<input type="text" id="' + iid7 + '" />').appendTo('#activiti-dummy1-signTaskUserSettingPage');
                        $('<input type="text" id="' + iid8 + '" />').appendTo('#activiti-dummy1-signTaskUserSettingPage');

                        if(fromrule == '01') {
                            $('#' + iid1).widget({
                                async: false,
                                referwid: 'RT_T_SYS_USER',
                                singleSelect: true,
                                width: 150,
                                treePName: true,
                                onlyLeafCheck: true,
                                required: false
                            });
                            $('#' + iid1).widget('setValue', fromuserid);
                        }
                        if(fromrule == '04' || fromrule == '06') {
                            $('#' + iid2).widget({
                                async: false,
                                referwid: 'RL_T_AC_AUTHORITY',
                                width: 150,
                                singleSelect: true,
                                required: false,
                                initValue: fromroleid
                            });
                            $('#' + iid2).widget('setValue', fromroleid);
                        }
                        if(fromrule == '05' || fromrule == '06') {
                            $('#' + iid3).widget({
                                async: false,
                                referwid: 'RT_T_SYS_DEPT',
                                singleSelect: true,
                                width: 150,
                                treePName: true,
                                onlyLeafCheck: true,
                                required: false
                            });
                            $('#' + iid3).widget('setValue', fromdeptid);
                        }
                        if(fromrule == '07') {
                            $('#' + iid4).widget({
                                async: false,
                                referwid: 'RL_T_BASE_WORKJOB',
                                width: 154,
                                singleSelect: true,
                                required: false,
                                initValue: frompostid
                            });
                        }
                        if(torule == '01') {
                            $('#' + iid5).widget({
                                async: false,
                                referwid: 'RT_T_SYS_USER',
                                singleSelect: true,
                                width: 150,
                                treePName: true,
                                onlyLeafCheck: true,
                                required: false
                            });
                            $('#' + iid5).widget('setValue', touserid);
                        }
                        if(torule == '04' || torule == '06') {
                            $('#' + iid6).widget({
                                async: false,
                                referwid: 'RL_T_AC_AUTHORITY',
                                width: 150,
                                singleSelect: true,
                                required: false,
                                initValue: toroleid
                            });
                        }
                        if(torule == '05' || torule == '06') {
                            $('#' + iid7).widget({
                                async: false,
                                referwid: 'RT_T_SYS_DEPT',
                                singleSelect: true,
                                width: 150,
                                treePName: true,
                                onlyLeafCheck: true,
                                required: false
                            });
                            $('#' + iid7).widget('setValue', todeptid);
                        }
                        if(torule == '07') {
                            $('#' + iid8).widget({
                                async: false,
                                referwid: 'RL_T_BASE_WORKJOB',
                                width: 154,
                                singleSelect: true,
                                required: false,
                                initValue: topostid
                            });
                        }

                        var temp = new Array();
                        var temp1 = new Array();
                        var temp2 = new Array();
                        var temp3 = new Array();
                        var temp4 = new Array();
                        var temp5 = new Array();

                        var temp6 = new Array();

                        if(fromrule == '01') {

                            temp1.push('<div class="mapping-from-box">');
                            temp1.push('<div>规则：指定人员</div>');
                            temp1.push('<div>明细：' + $('#' + iid1).widget('getText') + '</div>');
                            temp1.push('<div class="nodisplay" rule>01</div>');
                            temp1.push('<div class="nodisplay" detail>' + $('#' + iid1).widget('getValue') + '</div>');
                            temp1.push('</div>');
                        }
                        if(fromrule == '04') {

                            temp2.push('<div class="mapping-from-box">');
                            temp2.push('<div>规则：指定角色</div>');
                            temp2.push('<div>明细：' + $('#' + iid2).widget('getText') + '</div>');
                            temp2.push('<div class="nodisplay" rule>04</div>');
                            temp2.push('<div class="nodisplay" detail>' + $('#' + iid2).widget('getValue') + '</div>');
                            temp2.push('</div>');
                        }
                        if(fromrule == '05') {

                            temp3.push('<div class="mapping-from-box">');
                            temp3.push('<div>规则：指定部门</div>');
                            temp3.push('<div>明细：' + $('#' + iid3).widget('getText') + '</div>');
                            temp3.push('<div class="nodisplay" rule>05</div>');
                            temp3.push('<div class="nodisplay" detail>' + $('#' + iid3).widget('getValue') + '</div>');
                            temp3.push('</div>');
                        }
                        if(fromrule == '06') {

                            temp4.push('<div class="mapping-from-box">');
                            temp4.push('<div>规则：指定部门与角色</div>');
                            temp4.push('<div>明细：[' + $('#' + iid3).widget('getText') + ']： [' + $('#' + iid2).widget('getText') + ']</div>');
                            temp4.push('<div class="nodisplay" rule>06</div>');
                            temp4.push('<div class="nodisplay" detail>' + $('#' + iid3).widget('getValue') + ':' + $('#' + iid2).widget('getValue') + '</div>');
                            temp4.push('</div>');
                        }
                        if(fromrule == '07') {

                            temp5.push('<div class="mapping-from-box">');
                            temp5.push('<div>规则：指定岗位</div>');
                            temp5.push('<div>明细：' + $('#' + iid4).widget('getText') + '</div>');
                            temp5.push('<div class="nodisplay" rule>07</div>');
                            temp5.push('<div class="nodisplay" detail>' + $('#' + iid4).widget('getValue') + '</div>');
                            temp5.push('</div>');
                        }

                        temp.push(temp1);
                        temp.push(temp2);
                        temp.push(temp3);
                        temp.push(temp4);
                        temp.push(temp5);
                        if(torule == '01') {

                            temp6.push('<div class="mapping-to-box">');
                            temp6.push('<div>规则：指定人员</div>');
                            temp6.push('<div>明细：' + $('#' + iid5).widget('getText') + '</div>');
                            temp6.push('<div class="nodisplay" rule>01</div>');
                            temp6.push('<div class="nodisplay" detail>' + $('#' + iid5).widget('getValue') + '</div>');
                            temp6.push('</div>');
                        }
                        if(torule == '04') {

                            temp6.push('<div class="mapping-to-box">');
                            temp6.push('<div>规则：指定角色</div>');
                            temp6.push('<div>明细：' + $('#' + iid6).widget('getText') + '</div>');
                            temp6.push('<div class="nodisplay" rule>04</div>');
                            temp6.push('<div class="nodisplay" detail>' + $('#' + iid6).widget('getValue') + '</div>');
                            temp6.push('</div>');
                        }
                        if(torule == '05') {

                            temp6.push('<div class="mapping-to-box">');
                            temp6.push('<div>规则：指定部门</div>');
                            temp6.push('<div>明细：' + $('#' + iid7).widget('getText') + '</div>');
                            temp6.push('<div class="nodisplay" rule>05</div>');
                            temp6.push('<div class="nodisplay" detail>' + $('#' + iid7).widget('getValue') + '</div>');
                            temp6.push('</div>');
                        }
                        if(torule == '06') {

                            temp6.push('<div class="mapping-to-box">');
                            temp6.push('<div>规则：指定部门与角色</div>');
                            temp6.push('<div>明细：[' + $('#' + iid7).widget('getText') + ']： [' + $('#' + iid6).widget('getText') + ']</div>');
                            temp1.push('<div class="nodisplay" rule>06</div>');
                            temp1.push('<div class="nodisplay" detail>' + $('#' + iid7).widget('getValue') + ':' + $('#' + iid6).widget('getValue') + '</div>');
                            temp6.push('</div>');
                        }
                        if(torule == '07') {

                            temp6.push('<div class="mapping-to-box">');
                            temp6.push('<div>规则：指定岗位</div>');
                            temp6.push('<div>明细：' + $('#' + iid8).widget('getText') + '</div>');
                            temp6.push('<div class="nodisplay" rule>07</div>');
                            temp6.push('<div class="nodisplay" detail>' + $('#' + iid8).widget('getValue') + '</div>');
                            temp6.push('</div>');
                        }

                        $.each(temp, function(index, value){

                            var recno = $('#acitivti-mappingtable1-signTaskUserSettingPage tr').length - 1;
                            if(value.length != 0) {

                                $('tr.delete-row2').before('<tr><td>' + recno + '</td><td>' + value.join('') + '</td><td>-></td><td>' + temp6.join('') + '</td><td><div class="height18"><a href="javascript:void(0);" onclick="upline(this);">上移</a></div><div class="height18"><a href="javascript:void(0);" onclick="downline(this);">下移</a></div><div class="height18"><a href="javascript:void(0);" onclick="removeline(this)">删除</a></div></td></tr>');
                            }
                        });

                        $('#activiti-dummy1-signTaskUserSettingPage').html('');

                    }
                }
            });
        }

        $('#activiti-form2-signTaskUserSettingPage').form({
            onSubmit: function() {

                var mappings = new Array();

                var fromrules = new Array();
                var torules = new Array();

                $('#activiti-form2-signTaskUserSettingPage div[rule]').each(function(index) {

                    if(index % 2 == 0) {
                        fromrules.push($(this).text());
                    } else {
                        torules.push($(this).text());
                    }
                });

                var fromdetails = new Array();
                var todetails = new Array();
                $('#activiti-form2-signTaskUserSettingPage div[detail]').each(function(index) {

                    if(index % 2 == 0) {
                        fromdetails.push($(this).text());
                    } else {
                        todetails.push($(this).text());
                    }
                });

                $.each(fromrules, function(index, value) {

                    var fromrule = fromrules[index];
                    var torule = torules[index];
                    var fromdetail = fromdetails[index];
                    var todetail = todetails[index];

                    var mapping = {

                        category: index + 1,

                        fromrule: fromrule,
                        fromuserids: '',
                        fromdeptids: '',
                        fromroleids: '',
                        frompostids: '',

                        torule: torule,
                        touserids: '',
                        todeptids: '',
                        toroleids: '',
                        topostids: ''
                    };

                    if(fromrule == '01') {
                        mapping.fromuserids = fromdetail;
                    } else if(fromrule == '04') {
                        mapping.fromroleids = fromdetail;
                    } else if(fromrule == '05') {
                        mapping.fromdeptids = fromdetail;
                    } else if(fromrule == '06') {
                        mapping.fromdeptids = fromdetail.split(':')[0];
                        mapping.fromroleids = fromdetail.split(':')[1];
                    } else if(fromrule == '07') {
                        mapping.frompostids = fromdetail;
                    }

                    if(torule == '01') {
                        mapping.touserids = todetail;
                    } else if(torule == '04') {
                        mapping.toroleids = todetail;
                    } else if(torule == '05') {
                        mapping.todeptids = todetail;
                    } else if(torule == '06') {
                        mapping.todeptids = todetail.split(':')[0];
                        mapping.toroleids = todetail.split(':')[1];
                    } else if(torule == '07') {
                        mapping.topostids = todetail;
                    }

                    mappings.push(mapping);
                });

                $('#activiti-mapping-signTaskUserSettingPage').val(JSON.stringify(mappings));

                loading('保存中...');
            },
            success: function(data) {

                loaded();
                var json = $.parseJSON(data);
                if(json.flag) {
                    $.messager.alert('提醒', '保存成功。');
                    initUserTask({definitionkey: '${param.definitionkey }', taskkey: '${param.taskkey }', type: '${param.type }'});
                    return false;
                }
                $.messager.alert('提醒', '保存失败！');
                initUserTask({definitionkey: '${param.definitionkey }', taskkey: '${param.taskkey }', type: '${param.type }'});
                //initUserTask({definitionkey: '${param.definitionkey }', taskkey: '${param.taskkey }', type: '${param.type }'});
                //$("#activiti-dialog2-definitionUserSettingPage").dialog('close');
            }
        });

        // 保存1
        $('#activiti-save2-signTaskUserSettingPage').click(function() {
            $('#activiti-form2-signTaskUserSettingPage').submit();
        });

    });

    //
    function addRecordForApply(validate) {

        if($('#activiti-form2-signTaskUserSettingPage .userSettingCheckbox3:checked').length == 0
            || $('#activiti-form2-signTaskUserSettingPage .userSettingCheckbox4:checked').length == 0) {

            $.messager.alert('提醒', '请选择规则');
            return false;
        }

        if(validate != false) {
            var flag = $('#activiti-form2-signTaskUserSettingPage').form('validate');

            if (!flag) {

                return flag;
            }
        }

        var temp = new Array();
        var temp1 = new Array();
        var temp2 = new Array();
        var temp3 = new Array();
        var temp4 = new Array();
        var temp5 = new Array();

        var temp6 = new Array();

        if($('#activiti-form2-signTaskUserSettingPage .userSettingCheckbox3[value=01]').is(':checked')) {

            temp1.push('<div class="mapping-from-box">');
            temp1.push('<div>规则：指定人员</div>');
            temp1.push('<div>明细：' + $('#activiti-assignee2-signTaskUserSettingPage').widget('getText') + '</div>');
            temp1.push('<div class="nodisplay" rule>01</div>');
            temp1.push('<div class="nodisplay" detail>' + $('#activiti-assignee2-signTaskUserSettingPage').widget('getValue') + '</div>');
            temp1.push('</div>');
        }
        if($('#activiti-form2-signTaskUserSettingPage .userSettingCheckbox3[value=04]').is(':checked')) {

            temp2.push('<div class="mapping-from-box">');
            temp2.push('<div>规则：指定角色</div>');
            temp2.push('<div>明细：' + $('#activiti-role4-signTaskUserSettingPage').widget('getText') + '</div>');
            temp2.push('<div class="nodisplay" rule>04</div>');
            temp2.push('<div class="nodisplay" detail>' + $('#activiti-role4-signTaskUserSettingPage').widget('getValue') + '</div>');
            temp2.push('</div>');
        }
        if($('#activiti-form2-signTaskUserSettingPage .userSettingCheckbox3[value=05]').is(':checked')) {

            temp3.push('<div class="mapping-from-box">');
            temp3.push('<div>规则：指定部门</div>');
            temp3.push('<div>明细：' + $('#activiti-department3-signTaskUserSettingPage').widget('getText') + '</div>');
            temp3.push('<div class="nodisplay" rule>05</div>');
            temp3.push('<div class="nodisplay" detail>' + $('#activiti-department3-signTaskUserSettingPage').widget('getValue') + '</div>');
            temp3.push('</div>');
        }
        if($('#activiti-form2-signTaskUserSettingPage .userSettingCheckbox3[value=06]').is(':checked')) {

            temp4.push('<div class="mapping-from-box">');
            temp4.push('<div>规则：指定部门与角色</div>');
            temp4.push('<div>明细：[' + $('#activiti-department4-signTaskUserSettingPage').widget('getText') + ']： [' + $('#activiti-role5-signTaskUserSettingPage').widget('getText') + ']</div>');
            temp4.push('<div class="nodisplay" rule>06</div>');
            temp4.push('<div class="nodisplay" detail>' + $('#activiti-department4-signTaskUserSettingPage').widget('getValue') + ':' + $('#activiti-role5-signTaskUserSettingPage').widget('getValue') + '</div>');
            temp4.push('</div>');
        }
        if($('#activiti-form2-signTaskUserSettingPage .userSettingCheckbox3[value=07]').is(':checked')) {

            temp5.push('<div class="mapping-from-box">');
            temp5.push('<div>规则：指定岗位</div>');
            temp5.push('<div>明细：' + $('#activiti-post2-signTaskUserSettingPage').widget('getText') + '</div>');
            temp5.push('<div class="nodisplay" rule>07</div>');
            temp5.push('<div class="nodisplay" detail>' + $('#activiti-post2-signTaskUserSettingPage').widget('getValue') + '</div>');
            temp5.push('</div>');
        }

        temp.push(temp1);
        temp.push(temp2);
        temp.push(temp3);
        temp.push(temp4);
        temp.push(temp5);

        var v2 = $('#activiti-form2-signTaskUserSettingPage .userSettingCheckbox4:checked').attr('value');
        if(v2 == '01') {

            temp6.push('<div class="mapping-to-box">');
            temp6.push('<div>规则：指定人员</div>');
            temp6.push('<div>明细：' + $('#activiti-assignee3-signTaskUserSettingPage').widget('getText') + '</div>');
            temp6.push('<div class="nodisplay" rule>01</div>');
            temp6.push('<div class="nodisplay" detail>' + $('#activiti-assignee3-signTaskUserSettingPage').widget('getValue') + '</div>');
            temp6.push('</div>');
        }
        if(v2 == '04') {

            temp6.push('<div class="mapping-to-box">');
            temp6.push('<div>规则：指定角色</div>');
            temp6.push('<div>明细：' + $('#activiti-role6-signTaskUserSettingPage').widget('getText') + '</div>');
            temp6.push('<div class="nodisplay" rule>04</div>');
            temp6.push('<div class="nodisplay" detail>' + $('#activiti-role6-signTaskUserSettingPage').widget('getValue') + '</div>');
            temp6.push('</div>');
        }
        if(v2 == '05') {

            temp6.push('<div class="mapping-to-box">');
            temp6.push('<div>规则：指定部门</div>');
            temp6.push('<div>明细：' + $('#activiti-department5-signTaskUserSettingPage').widget('getText') + '</div>');
            temp6.push('<div class="nodisplay" rule>05</div>');
            temp6.push('<div class="nodisplay" detail>' + $('#activiti-department5-signTaskUserSettingPage').widget('getValue') + '</div>');
            temp6.push('</div>');
        }
        if(v2 == '06') {

            temp6.push('<div class="mapping-to-box">');
            temp6.push('<div>规则：指定部门与角色</div>');
            temp6.push('<div>明细：[' + $('#activiti-department6-signTaskUserSettingPage').widget('getText') + ']： [' + $('#activiti-role7-signTaskUserSettingPage').widget('getText') + ']</div>');
            temp1.push('<div class="nodisplay" rule>06</div>');
            temp1.push('<div class="nodisplay" detail>' + $('#activiti-department6-signTaskUserSettingPage').widget('getValue') + ':' + $('#activiti-role7-signTaskUserSettingPage').widget('getValue') + '</div>');
            temp6.push('</div>');
        }
        if(v2 == '07') {

            temp6.push('<div class="mapping-to-box">');
            temp6.push('<div>规则：指定岗位</div>');
            temp6.push('<div>明细：' + $('#activiti-post3-signTaskUserSettingPage').widget('getText') + '</div>');
            temp6.push('<div class="nodisplay" rule>07</div>');
            temp6.push('<div class="nodisplay" detail>' + $('#activiti-post3-signTaskUserSettingPage').widget('getValue') + '</div>');
            temp6.push('</div>');
        }

        $.each(temp, function(index, value){

            var recno = $('#acitivti-mappingtable1-signTaskUserSettingPage tr').length - 1;
            if(value.length != 0) {

                $('tr.delete-row2').before('<tr><td>' + recno + '</td><td>' + value.join('') + '</td><td>-></td><td>' + temp6.join('') + '</td><td><div class="height18"><a href="javascript:void(0);" onclick="upline(this);">上移</a></div><div class="height18"><a href="javascript:void(0);" onclick="downline(this);">下移</a></div><div class="height18"><a href="javascript:void(0);" onclick="removeline(this)">删除</a></div></td></tr>');
            }
        });

        $('#activiti-form2-signTaskUserSettingPage input[type=checkbox]').attr('checked', false);

        $('#activiti-assignee2-signTaskUserSettingPage').widget('clear');
        $('#activiti-role4-signTaskUserSettingPage').widget('clear');
        $('#activiti-department3-signTaskUserSettingPage').widget('clear');
        $('#activiti-department4-signTaskUserSettingPage').widget('clear');
        $('#activiti-role5-signTaskUserSettingPage').widget('clear');
        $('#activiti-post2-signTaskUserSettingPage').widget('clear');
        $('#activiti-assignee3-signTaskUserSettingPage').widget('clear');
        $('#activiti-role6-signTaskUserSettingPage').widget('clear');
        $('#activiti-department5-signTaskUserSettingPage').widget('clear');
        $('#activiti-department6-signTaskUserSettingPage').widget('clear');
        $('#activiti-role7-signTaskUserSettingPage').widget('clear');
        $('#activiti-post3-signTaskUserSettingPage').widget('clear');

        $('#activiti-assignee2-signTaskUserSettingPage').validatebox({required: false});
        $('#activiti-role4-signTaskUserSettingPage').validatebox({required: false});
        $('#activiti-department3-signTaskUserSettingPage').validatebox({required: false});
        $('#activiti-department4-signTaskUserSettingPage').validatebox({required: false});
        $('#activiti-role5-signTaskUserSettingPage').validatebox({required: false});
        $('#activiti-post2-signTaskUserSettingPage').validatebox({required: false});
        $('#activiti-assignee3-signTaskUserSettingPage').validatebox({required: false});
        $('#activiti-role6-signTaskUserSettingPage').validatebox({required: false});
        $('#activiti-department5-signTaskUserSettingPage').validatebox({required: false});
        $('#activiti-department6-signTaskUserSettingPage').validatebox({required: false});
        $('#activiti-role7-signTaskUserSettingPage').validatebox({required: false});
        $('#activiti-post3-signTaskUserSettingPage').validatebox({required: false});

        $('#activiti-form2-signTaskUserSettingPage').form('validate');
    }

    function upline(d) {

        var recno = $('#acitivti-mappingtable1-signTaskUserSettingPage tr').index($(d).parents('tr'));

        if(recno <= 1) {
            return false;
        }

        var dest = recno - 1;
        var src = recno;
        var old = recno + 1;

        $('#acitivti-mappingtable1-signTaskUserSettingPage tr:eq(' + dest + ')').before($('#acitivti-mappingtable1-signTaskUserSettingPage tr:eq(' + src + ')').clone());

        $('#acitivti-mappingtable1-signTaskUserSettingPage tr:eq(' + old + ')').remove();
        $('#acitivti-mappingtable1-signTaskUserSettingPage tr:eq(' + dest + ')').hide();
        $('#acitivti-mappingtable1-signTaskUserSettingPage tr:eq(' + dest + ')').show('slow');

        refreshRecno();
    }

    function downline(d) {

        var recno = $('#acitivti-mappingtable1-signTaskUserSettingPage tr').index($(d).parents('tr'));

        if(recno >= $('#acitivti-mappingtable1-signTaskUserSettingPage tr').length - 2) {
            return false;
        }

        var dest = recno + 1;
        var src = recno;
        var old = recno;

        $('#acitivti-mappingtable1-signTaskUserSettingPage tr:eq(' + dest + ')').after($('#acitivti-mappingtable1-signTaskUserSettingPage tr:eq(' + src + ')').clone());

        $('#acitivti-mappingtable1-signTaskUserSettingPage tr:eq(' + dest + ')').hide();
        $('#acitivti-mappingtable1-signTaskUserSettingPage tr:eq(' + dest + ')').show('slow');
        $('#acitivti-mappingtable1-signTaskUserSettingPage tr:eq(' + old + ')').remove();
        refreshRecno();
    }

    function removeline(d) {

        $(d).parents('tr').hide('slow');

        setTimeout(function() {

            $(d).parents('tr').remove();
            refreshRecno();

        }, 1000);
    }

    function refreshRecno() {

        $('#acitivti-mappingtable1-signTaskUserSettingPage tr').each(function(index) {

            if(index != 0 && index < $('#acitivti-mappingtable1-signTaskUserSettingPage tr').length - 1) {

                $(this).find('td:eq(0)').text(index);
            }

        });
    }

    -->
</script>
</body>
</html>
