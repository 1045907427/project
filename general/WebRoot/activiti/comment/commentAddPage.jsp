<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>审批页面</title>
</head>
<body>
<style type="text/css">
    .tableclass {width:100%;border-collapse:collapse;}
    .tdclass{border:1px solid #aaaaaa;padding:3px;line-height:24px;}
    .trhidden{display: none;}
    label > input[type=radio]{
        float:left;
        height: 18px;
        line-height: 18px;
    }
</style>
<div class="easyui-layout" data-options="fit:true">
    <form action="act/updateCommentInfo.do" method="post" id="activiti-form-commentAddPage">
        <input type="hidden" name="sign" value="${param.sign }"/>
        <c:if test="${param.sign eq 1}">
            <input type="hidden" name="comment.agree" id="activiti-agree-commentAddPage" value="1"/>
        </c:if>
        <div data-options="region:'center',border:false">
            <div class="pageContent">
                <table class="tableclass">
                    <tr>
                        <th style="border:1px solid #aaaaaa;padding:3px;line-height:24px;">当前节点：</th><td class="tdclass"><c:out value="${process.taskname }" /></td>
                    </tr>
                    <tr>
                        <th class="tdclass len100">意见：</th>
                        <td class="tdclass">
                            <textarea style="width: 194px; height: 50px; resize: none;" name="comment.comment"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <th class="tdclass">签名：</th>
                        <td class="tdclass">
                            <input type="text" readonly="readonly" name="comment.signature" value="${signature }"/>
                        </td>
                    </tr>
                    <tr>
                        <th class="tdclass">时间：</th>
                        <td class="tdclass">
                            <input type="text" readonly="readonly" value="${time }" />
                        </td>
                    </tr>
                    <c:if test="${param.sign ne 1}">
                        <tr>
                            <th style="border:1px solid #aaaaaa;background:#efefef;font-weight:700;padding:3px;line-height:24px;">下一节点：</th>
                            <td class="tdclass" colspan="2">
                                <c:forEach items="${tasklist }" var="item">
                                    <div style="float: left; width: 160px;"><label <c:if test="${item.sign }">sign style="color: #F00;" title="会签节点:<c:out value="${item.taskname }"/>"</c:if> title="<c:out value="${item.taskname }"/>" ><input type="radio" <c:if test="${item.sign }"> sign </c:if> name="nexttask" value="${item.taskkey }" data="${item.type }" style="width: 13px; height: 18px;" /><c:out value="${item.taskname }"/></label></div>
                                </c:forEach>
                            </td>
                        </tr>
                        <tr class="trhidden">
                            <td style="border:1px solid #aaaaaa;background:#efefef;font-weight:700;padding:3px;line-height:24px;" rowspan="2">下一节点接收人：</td>
                            <td class="tdclass">
                                <div id="nextAssignSelect">
                                    <div style="float: left; width: 160px;">
                                        <div style="float: left;"><label><input type="radio" name="acceptType" id="radio1" value="1" style="width:13px; height: 18px;" checked="checked"/>默认接收人</label></div>
                                    </div>
                                    <div style="float: left; width: 160px;">
                                        <div style="float: left;"><label><input type="radio" name="acceptType" id="radio2" value="2" style="width:13px; height: 18px;" disabled="disabled"/>其他接收人</label></div>
                                    </div>
                                    <div style="float: left; width: 160px;">
                                        <div style="float: left;"><label><input type="radio" name="acceptType" id="radio3" value="3" style="width:13px; height: 18px;" disabled="disabled"/>会签接收人</label></div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr class="trhidden">
                            <%--<td style="border:1px solid #aaaaaa;background:#efefef;font-weight:700;padding:3px;line-height:24px;">选择人员：</th>--%>
                            <td class="tdclass">
                                <div id="activiti-applyUser-commentAddPage" style="display: block;">
                                    <input id="activiti-nextAssignee-commentAddPage" name="" autocomplete="off"/>
                                </div>
                            </td>
                        </tr>
                        <tr class="sign_row nodisplay">
                            <th style="border:1px solid #aaaaaa;background:#efefef;padding:3px;line-height:24px;" rowspan="2">会签完成后执行：</th>
                            <td class="tdclass" id="activiti-signNextTask-commentAddPage">
                            </td>
                        </tr>
                        <tr class="sign_row nodisplay">
                            <td class="tdclass" id="activiti-signNextAssigneeTd-commentAddPage">
                            </td>
                        </tr>
                    </c:if>
                </table>
                <input type="hidden" id="activiti-id-commentAddPage" value="${comment.id }" name="comment.id"/>
                <input type="hidden" id="activiti-taskid-commentAddPage" value="${comment.taskid }" name="comment.taskid"/>
                <input type="hidden" id="activiti-type-commentAddPage" name="type" />
                <input type="hidden" id="activiti-instanceid-commentAddPage" name="instanceid" value="${process.instanceid }"/>
                <input type="hidden" id="activiti-nexttaskkey-commentAddPage" name="nexttaskkey"/>
                <input type="hidden" id="activiti-nexttasktype-commentAddPage" name="nexttasktype"/>
                <input type="hidden" id="activiti-nextAssignee-hidden-commentAddPage" name="nextAssignee"/>
            </div>
        </div>
        <div data-options="region:'south',border:false" style="height:40px;">
            <div class="buttonDivR">
                <span id="activiti-span1-commentAddPage">
                    <c:choose>
                        <c:when test="${param.sign eq 1}">
                            <a href="javascript:;" id="activiti-ok-commentAddPage" class="easyui-linkbutton" data-options="iconCls:'button-open'">同意</a>
                            <a href="javascript:;" id="activiti-ng-commentAddPage" class="easyui-linkbutton" data-options="iconCls:'button-quit'">反对</a>
                        </c:when>
                        <c:otherwise>
                            <a href="javascript:;" id="activiti-next-commentAddPage" class="easyui-linkbutton" data-options="iconCls:'button-save'">保存并转入下一步</a>
                        </c:otherwise>
                    </c:choose>
                </span>
                <span id="activiti-span2-commentAddPage"><a href="javascript:;" id="activiti-close-commentAddPage" class="easyui-linkbutton" data-options="iconCls:'button-close'">关闭</a></span>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">

    $(function(){

        var taskkey = '';
        var widgetstr = '';
        var tasktype = '';
        var count = 0;
        var ids = new Array();

        <c:if test="${param.sign ne 1}">
            $('#activiti-span1-commentAddPage').hide();
        </c:if>

        // 下一节点点击事件
        $('input[name=nexttask]').unbind().click(function(){

            $('#activiti-nextAssignee-hidden-commentAddPage').val('');

            $('#activiti-nexttaskkey-commentAddPage').val($(this).val());
            $('#activiti-nexttasktype-commentAddPage').val($(this).attr('data'));
            if(taskkey == $(this).val()) {

                return false;
            }

            try {
                $("#activiti-nextAssignee-commentAddPage").widget('clear');
            } catch(e) { }

            $("#activiti-nextAssignee-commentAddPage").removeAttr('readonly');
            $('#activiti-span1-commentAddPage').hide();

            taskkey = $(this).val();
            var sign = $(this).is('[sign]') || false;

            var id = '${process.id }';
            var type = '1';
            tasktype = $(this).attr('data');

            // 下一节点为结束节点
            if(tasktype == 'endEvent') {

                $('#activiti-span1-commentAddPage').show();

                $("#activiti-nextAssignee-commentAddPage").widget({
                    referwid:'RT_T_SYS_USER',
                    singleSelect: true,
                    width: 180,
                    required: false,
                    onlyLeafCheck: true
                });

                $('.trhidden').hide();

                return true;
            }

            loading('正在获取人员信息...');
            $.ajax({
                type: 'post',
                dataType: 'json',
                url: 'act/getNextTaskDefinition.do',
                data: {taskkey: taskkey, id: id, type: type, sign: sign ? 1 : 0},
                success: function(json) {

                    //$("#activiti-nextAssignee-commentAddPage").removeAttr('readonly');
                    $('#activiti-span1-commentAddPage').show();
                    $('.trhidden').show();

                    widgetstr = json.widgetstr || '　';
                    count = json.count;
                    ids = json.ids;
                    $('input[name=acceptType]').first().click();

                    //
                    $('input[name=acceptType]').removeAttr('disabled');
                    $('input[name=acceptType]:eq(0)').attr('checked', 'checked');
                    $('input[name=acceptType]:eq(2)').attr('disabled', 'disabled');
                    if(!sign) {
                        $.ajax({
                            type: 'post',
                            dataType: 'json',
                            url: 'act/getCanassignSetting.do',
                            data: {definitionkey: '${process.definitionkey }', taskkey: taskkey, type: 'aud'},
                            success: function(json2) {

                                loaded();

                                if(json2.canassign != '1') {
                                    $('input[name=acceptType]:eq(1)').attr('disabled', 'disabled');
                                } else {
                                    $('input[name=acceptType]:eq(1)').removeAttr('disabled');
                                }
                            }
                        });
                        $('#activiti-signNextAssigneeTd-commentAddPage').html('');
                        $('#activiti-signNextTask-commentAddPage').html('');
                        $('.sign_row').hide();
                    } else {
//                        loaded();
                        $('input[name=acceptType]:eq(0)').attr('disabled', 'disabled');
                        $('input[name=acceptType]:eq(1)').attr('disabled', 'disabled');
                        $('input[name=acceptType]:eq(2)').removeAttr('disabled');
                        $('input[name=acceptType]:eq(2)').click();
                        $.ajax({
                            type: 'post',
                            dataType: 'json',
                            url: 'act/getNextTasksBySignTask.do',
                            data: {instanceid: '${process.instanceid }', taskkey: taskkey},
                            success: function(jsonArray) {
                                try {

                                    $('#activiti-signNextTask-commentAddPage').html('');
                                    jsonArray.map(function (item) {

                                        var htm = '<div style="float: left; width: 160px;"><label title="{1}" ><input type="radio" name="signnexttask" value="{0}" nodetype="{2}" style="width: 13px; height: 18px;"/><c:out value="{1}"/></label></div>';

                                        htm = htm.replace(/\{0\}/g, item.taskkey || '');
                                        htm = htm.replace(/\{1\}/g, item.taskname || '');
                                        htm = htm.replace(/\{2\}/g, item.type || '');

                                        $('#activiti-signNextTask-commentAddPage').append(htm);
                                        $('#activiti-signNextTask-commentAddPage > div').last().find('input[type=radio]').data('users', item.users || {});

                                    });

                                    $('input[name=signnexttask]').off().on('click', function (e) {

                                        var type = $(this).attr('nodetype');
                                        var users = $(this).data('users');

                                        if(users && users.widgetstr) {
                                            $('#activiti-signNextAssigneeTd-commentAddPage').html('');
                                            var htm = '<input type="text" name="signNextAssignee" id="activiti-signNextAssignee-commentAddPage" />';
                                            $('#activiti-signNextAssigneeTd-commentAddPage').html(htm);
                                            $('#activiti-signNextAssignee-commentAddPage').widget({
                                                referwid: 'RL_T_SYS_USER',
                                                singleSelect: true,
                                                width: 200,
                                                required: true,
                                                onlyLeafCheck: true,
                                                param: [{field: 'userid', op: 'in', value: users.widgetstr}],
                                                onLoadSuccess: function () {
                                                    if (users.count == 1) {
                                                        $("#activiti-signNextAssignee-commentAddPage").widget('setValue', users.ids[0]);
                                                        $('#activiti-signNextAssignee-hidden-commentAddPage').val(users.ids[0]);
                                                    }
                                                },
                                                onSelect: function (data) {
                                                    $('#activiti-signNextAssignee-hidden-commentAddPage').val($("#activiti-signNextAssignee-commentAddPage").widget('getValue'));
                                                },
                                                onClear: function () {
                                                    $('#activiti-signNextAssignee-hidden-commentAddPage').val($("#activiti-signNextAssignee-commentAddPage").widget('getValue'));
                                                }
                                            });
                                        } else {
                                            $('#activiti-signNextAssigneeTd-commentAddPage').html('结束节点不需要审批人。');
                                        }

                                    }).first().click();

                                } catch (e) {
                                    $.messager.alert('提醒', '发生异常！');
                                    return false;
                                }
                                loaded();
                            }
                        });
                        $('.sign_row').show();
                    }

                },
                error: function() {

                    loaded();
                    $('.trhidden').hide();
                    $.messager.alert("提醒","获取人员失败！");
                }
            });
        });

        // 接受人类型
        $('input[name=acceptType]').unbind().click(function(){

            $("#activiti-nextAssignee-commentAddPage").removeAttr('readonly');
            try {
                $("#activiti-nextAssignee-commentAddPage").widget('clear');
            } catch(e) { }
            $('#activiti-nextAssignee-hidden-commentAddPage').val('');

            $('#activiti-applyUser-commentAddPage').html('').append('<input id="activiti-nextAssignee-commentAddPage" name="" autocomplete="off" style="width: 200px;"/>');
            var acceptType = $(this).val();
            if(acceptType == '1') {

                $("#activiti-nextAssignee-commentAddPage").widget({
                    referwid:'RL_T_SYS_USER',
                    singleSelect:true,
                    width:200,
                    required: true,
                    onlyLeafCheck:true,
                    param: [{field: 'userid', op: 'in', value: widgetstr} ],
                    onLoadSuccess: function() {
                        if(count == 1) {
                            $("#activiti-nextAssignee-commentAddPage").widget('setValue', ids[0]);
                            $('#activiti-nextAssignee-hidden-commentAddPage').val(ids[0]);
                        }
                    },
                    onSelect: function(data) {
                        $('#activiti-nextAssignee-hidden-commentAddPage').val($("#activiti-nextAssignee-commentAddPage").widget('getValue'));
                    },
                    onClear: function() {
                        $('#activiti-nextAssignee-hidden-commentAddPage').val($("#activiti-nextAssignee-commentAddPage").widget('getValue'));
                    }
                });

            } else if(acceptType == '2') {

                $("#activiti-nextAssignee-commentAddPage").widget({
                    referwid:'RT_T_SYS_USER',
                    singleSelect:true,
                    width:200,
                    required: true,
                    onlyLeafCheck:true,
                    onChecked: function(data) {
                        $('#activiti-nextAssignee-hidden-commentAddPage').val($("#activiti-nextAssignee-commentAddPage").widget('getValue'));
                    },
                    onClear: function() {
                        $('#activiti-nextAssignee-hidden-commentAddPage').val($("#activiti-nextAssignee-commentAddPage").widget('getValue'));
                    }
                });

                setTimeout(function() {
                    $("#activiti-nextAssignee-commentAddPage").attr('readonly', 'readonly');
                }, 0);
            } else if(acceptType == '3') {

                $("#activiti-nextAssignee-commentAddPage").widget({
                    referwid:'RT_T_SYS_USER',
                    singleSelect:false,
                    width:200,
                    required: true,
                    onlyLeafCheck:true,
                    readonly: true,
                    param: [{field: 'userid', op: 'in', value: widgetstr} ],
                    onLoadSuccess: function() {
                        $("#activiti-nextAssignee-commentAddPage").widget('setValue', ids.join(','));
                        $("#activiti-nextAssignee-commentAddPage").attr('title', $("#activiti-nextAssignee-commentAddPage").widget('getText'));
                        $('#activiti-nextAssignee-hidden-commentAddPage').val(ids.join(','));
                    },
                    onChecked: function(data) {
                        $('#activiti-nextAssignee-hidden-commentAddPage').val($("#activiti-nextAssignee-commentAddPage").widget('getValue'));
                    },
                    onClear: function() {
                        $('#activiti-nextAssignee-hidden-commentAddPage').val($("#activiti-nextAssignee-commentAddPage").widget('getValue'));
                    }
                });

                setTimeout(function() {
                    $("#activiti-nextAssignee-commentAddPage").attr('readonly', 'readonly');
                    $("#activiti-nextAssignee-commentAddPage").validatebox('validate');
                }, 0);
            }

        });

        // 默认点击第一个节点
        $('input[name=nexttask]').first().click();

        $("#activiti-form-commentAddPage").form({

            onSubmit: function(){

                if(tasktype != 'endEvent') {
                    var flag = $(this).form('validate');
                    if(flag==false){
                        return false;
                    }
                }

                loading("提交中...");
            },
            success: function(data) {

                loaded();
                var json = $.parseJSON(data);

                if(json.flag==true){

                    $.messager.alert("提醒", "转交成功");
                    $activiti_dialog.dialog('close');
                    //刷新待办事宜
                    var win = tabsWindowURL('/act/myWorkPage6.do');
                    try{
                        if(win != null){
                            win.$("#activiti-datagrid-myWorkPage").datagrid('reload');
                        }
                    }catch(e){}

                    var win2 = tabsWindowURL('/act/myWorkPage7.do');
                    try{
                        if(win2 != null){
                            win2.$("#activiti-datagrid-myWorkPage").datagrid('reload');
                        }
                    }catch(e){}

                    try{
                        top.$("#protal-mywork").panel("refresh");
                    }catch(e){}

                    top.removeTabRemind('${param.title }');
                    top.closeNowTab();  // 2943 所有版本：ie 浏览器转交下一步时出现问题

                } else {
                    $.messager.alert("提醒", "操作失败！<br/>可能因为该工作已被转交、挂起、删除或已经结束！");
                }

            }
        });

        $("#activiti-next-commentAddPage").click(function(){

            $.messager.confirm("提醒", "是否保存信息并转到下一步?",function(r){
                if(r){
                    $("#activiti-type-commentAddPage").val("1");
                    $("#activiti-form-commentAddPage").submit();
                }
            });
        });

        // 同意
        $("#activiti-ok-commentAddPage").click(function(){

            $('#activiti-agree-commentAddPage').val(1);

            $.messager.confirm("提醒", "同意?",function(r){
                if(r){
                    $("#activiti-type-commentAddPage").val("1");
                    $("#activiti-form-commentAddPage").submit();
                }
            });
        });

        // 反对
        $("#activiti-ng-commentAddPage").click(function(){

            $('#activiti-agree-commentAddPage').val(0);

            $.messager.confirm("提醒", "反对?",function(r){
                if(r){
                    $("#activiti-type-commentAddPage").val("1");
                    $("#activiti-form-commentAddPage").submit();
                }
            });
        });

        $('#activiti-close-commentAddPage').click(function() {

            $('#activiti-dialog-workHandlePage').dialog('close');
        });
    });

    $.parser.onComplete = function(context){};

</script>
</body>
</html>
