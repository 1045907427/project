<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>工作处理</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<style type="text/css">
    .panel-header {
        background: #dce5f4;
    }
    .panel-title {
        color: #000;
    }
</style>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div style="padding: 5px; font-weight: bold; background: #dce5f4;">
            OA号：${process.id }
        </div>
        <c:if test="${(not empty process.keyword1) or (not empty process.keyword2) or (not empty process.keyword3) or (not empty process.keyword4) or (not empty process.keyword5)}">
            <div id="activiti-keywords-workHandlePage" style="padding: 5px; font-weight: bold; background: #fff; border: 1px solid #dce5f4;">
                关键字：
                <c:if test="${not empty process.keyword1 }">
                    <span style="margin: 3px; padding: 3px; border: solid 1px #ddd;"><c:out value="${process.keyword1 }"></c:out></span>
                </c:if>
                <c:if test="${not empty process.keyword2 }">
                    <span style="margin: 3px; padding: 3px; border: solid 1px #ddd;"><c:out value="${process.keyword2 }"></c:out></span>
                </c:if>
                <c:if test="${not empty process.keyword3 }">
                    <span style="margin: 3px; padding: 3px; border: solid 1px #ddd;"><c:out value="${process.keyword3 }"></c:out></span>
                </c:if>
                <c:if test="${not empty process.keyword4 }">
                    <span style="margin: 3px; padding: 3px; border: solid 1px #ddd;"><c:out value="${process.keyword4 }"></c:out></span>
                </c:if>
                <c:if test="${not empty process.keyword5 }">
                    <span style="margin: 3px; padding: 3px; border: solid 1px #ddd;"><c:out value="${process.keyword5 }"></c:out></span>
                </c:if>
            </div>
        </c:if>
    </div>
    <div data-options="region:'center',border:false">
        <div id="activiti_div_workHandlePage" class="easyui-panel" style="padding: 10px; color: #FFF;"><img src="image/loading.gif" alt="工作加载中..."/>&nbsp;
            <div id="activiti_tip_workHandlePage">
                工作加载中...<br/>如果长时间未显示工作处理页面，可能由于以下原因导致：<br/>
                1. 该工作流程未配置表单。<br/>
                2. 该工作已被处理、结束、删除。
            </div>
        </div>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDivR" id="activiti-buttons-workHandlePage">
            <c:if test="${exist and valid}">
                <c:choose>
                    <c:when test="${sign }">
                        <a href="javascript:void(0);" id="activiti-sign-workHandlePage" class="easyui-linkbutton" data-options="iconCls:'button-edit'">会签</a>
                    </c:when>
                    <c:otherwise>
                        <a href="javascript:void(0);" id="activiti-addidea-workHandlePage" class="easyui-linkbutton" data-options="iconCls:'button-edit'">保存并转交下一步</a>
                        <a href="javascript:void(0);" id="activiti-save-workHandlePage" class="easyui-linkbutton" data-options="iconCls:'button-save'">保存</a>
                        <c:if test="${not first}">
                            <a href="javascript:void(0);" id="activiti-backwork-workHandlePage" class="easyui-linkbutton" data-options="iconCls:'button-back'">驳回</a>
                        </c:if>
                    </c:otherwise>
                </c:choose>
                <a href="javascript:void(0);" id="activiti-viewflow-workHandlePage" class="easyui-linkbutton" data-options="iconCls:'button-view'">查看流程</a>
                <%-- <a href="javascript:void(0);" id="activiti-viewflowpic-workHandlePage" class="easyui-linkbutton" data-options="iconCls:'button-view'">查看流程图</a> --%>
                <c:if test="${not first and not sign}">
                    <a href="javascript:void(0);" id="activiti-change-workHandlePage" class="easyui-linkbutton" data-options="iconCls:'button-transfer'">变更处理人</a>
                </c:if>
                <a href="javascript:void(0);" id="activiti-print-workHandlePage" class="easyui-linkbutton" data-options="iconCls:'button-print'" onclick="javascript:var w = window.open('act/workPrintPage.do?instanceid=${process.instanceid }', '打印');w.focus();">打印</a>
            </c:if>
            <a href="javascript:void(0);" id="activiti-close-workHandlePage" class="easyui-linkbutton" data-options="iconCls:'button-close'">关闭</a>
        </div>
    </div>
    <div id="activiti-dialog-workHandlePage"></div>
    <div id="activiti-dialog2-workHandlePage"></div>
    <div id="activiti-dialog3-workHandlePage"></div>
    <div id="activiti-dialog4-workHandlePage"></div>
</div>
<script type="text/javascript">

    var title = top.getNowTabTitle();

    /**
     * 放弃工作
     * @returns {boolean}
     */
    function quitWork() {

        $.ajax({
            type: 'post',
            dataType: 'json',
            data: {id: '${process.id }'},
            url: 'act/quitProcess.do',
            success: function(json) {
            }
        });

        return true;
    }

    $(function() {

        top.addTabRemind(title, '是否<font color="red">关闭</font>该工作？', quitWork);

        $('#activiti-close-workHandlePage').click(function() {
            parent.closeNowTab();
        });
    });

</script>
<c:if test="${exist and valid}">
    <script type="text/javascript">

        var $activiti_dialog = $('#activiti-dialog-workHandlePage');
        var processid = '${process.id }';
        var base_href = $('base')[0].href;

        var handleWork_taskId = "${taskid }";
        var handleWork_instanceId = "${instanceid }";
        $(function(){

            /////
            // 刷新未接受、办理中页面
            var win = tabsWindowURL('/act/myWorkPage7.do');
            try{
                if(win != null){
                    win.$("#activiti-datagrid-myWorkPage").datagrid('reload');
                }
            }catch(e){}

            var win2 = tabsWindowURL('/act/myWorkPage6.do');
            try{
                if(win2 != null){
                    win2.$("#activiti-datagrid-myWorkPage").datagrid('reload');
                }
            }catch(e){}
            /////

            var handleWork_formType = '${definition.formtype}';
            var url = '';
            // 在线FORM
            if(handleWork_formType == 'formkey'){
                url= "act/workFormKeyPage.do?taskid=${process.taskid }&instanceid=${process.instanceid }&processid=${process.id }";
                <c:if test="${sign }">
                    url = 'act/workFormKeyViewPage.do?taskid=${process.taskid }&instanceid=${process.instanceid }&processid=${process.id }';
                </c:if>
                // URL表单
            } else if(handleWork_formType == 'business'){

                url = '${businessUrl }';
                if(url.indexOf('?') >= 0) {
                    url = url + '&id=${process.businessid }&definitionkey=${process.definitionkey }&processid=${process.id }';
                } else {
                    url = url + '?id=${process.businessid }&definitionkey=${process.definitionkey }&processid=${process.id }';
                }
                <c:choose>
                    <c:when test="${sign }">
                        url = url + '&type=view';
                    </c:when>
                    <c:otherwise>
                        url = url + '&type=handle';
                    </c:otherwise>
                </c:choose>
            }
            <c:if test="${handle eq '1' }">
            $("#activiti_div_workHandlePage").panel({
                href:url,
                fit:true,
                border:false,
                cache:false,
                maximized:true,
                onLoad: function() {

                    $('#activiti-viewflow-workHandlePage').click(viewWorkFlow);

                    // checkProcessStatus
                    $('#activiti-sign-workHandlePage').click(commentAddPage, checkProcessStatus);
                    $('#activiti-addidea-workHandlePage').click(commentAddPage, checkProcessStatus);
                    $('#activiti-save-workHandlePage').click(updateBusinessId, checkProcessStatus);
                    $('#activiti-backwork-workHandlePage').click(workBackPage, checkProcessStatus);

                    // 4022 通用版：工作流增加可以更换处理人的功能
                    $('#activiti-change-workHandlePage').click(changeAssigneePage, checkProcessStatus);
                }
            });
            </c:if>

            setTimeout(function () {
                $('#activiti_tip_workHandlePage').css({color: '#000'});
            }, 3e3)
        });

        // 4022 通用版：工作流增加可以更换处理人的功能
        /**
         * 更换处理人
         */
        function changeAssigneePage() {

            var did = 'd' + getRandomid();
            $('<div id="' + did + '"></div>').appendTo('body');

            $('#' + did).dialog({
                title: '更换处理人',
                width: 400,
                height: 320,
                closed: false,
                cache: false,
                modal: true,
                href: 'act/changeAssigneePage.do?id=${process.id}&taskid=${process.taskid }',
                onLoad: function () {

                    // 变更按钮
                    $('#activiti-change-changeAssigneePage').off('click').on('click', function() {

                        updateProcessAssignee();
                    });

                    // 关闭按钮
                    $('#activiti-close-changeAssigneePage').off('click').on('click', function() {

                        $('#' + did).dialog('close');
                    });
                },
                onClose:function(){

                    $('#' + did).dialog('destroy');
                    location.href = 'act/workHandlePage.do?taskid=${process.taskid }&taskkey=${process.taskkey }&instanceid=${process.instanceid }'
                }
            });
        }

        /**
         * 驳回工作页面
         */
        function workBackPage() {

            var id = '${process.id }';
            var definitionkey = '${process.definitionkey }';
            var taskkey = '${process.taskkey }';

            $('#activiti-dialog2-workHandlePage').dialog({
                title: '驳回工作',
                width: 400,
                height: 320,
                closed: false,
                cache: false,
                modal: true,
                href: 'act/workBackPage.do?title=' + title + '&definitionkey=' + definitionkey + '&taskkey=' + taskkey + '&id=' + id,
                onLoad: function() {

                    $('#activiti-form-workBackPage').form({
                        onSubmit: function() {

                            var taskkey= $('input:radio[name="task"]:checked').val();

                            if(taskkey == null) {
                                $.messager.alert("提醒","请选择驳回至哪一节点！");
                                return false;
                            }

                            loading('驳回中');
                        },
                        success: function(data) {

                            loaded();
                            var json = $.parseJSON(data);

                            if(json.flag == true) {

                                $.messager.alert("提醒","驳回成功。");
                                $("#activiti-dialog2-workHandlePage").dialog('close');

                                //刷新"办理中"
                                var win = tabsWindowURL("/act/myWorkPage7.do");
                                try{
                                    if(win != null){
                                        win.$("#activiti-datagrid-myWorkPage").datagrid('reload');
                                    }
                                }catch(e){}
                                try{
                                    top.$("#protal-mywork").panel("refresh");
                                }catch(e){}
                                top.removeTabRemind(title);
                                top.$("#tt").tabs('close', title);

                                return false;
                            } else {

                                $.messager.alert("提醒", "驳回失败！<br/>可能因为该工作已被转交、挂起、删除或已经结束！");
                            }
                        }
                    });

                },
                onClose: function() {
                    backflag = false;
                    location.href = 'act/workHandlePage.do?taskid=${process.taskid }&taskkey=${process.taskkey }&instanceid=${process.instanceid }'
                }
            });

        }

        /**
         * 转交工作页面
         */
        function commentAddPage(businessid) {

            $activiti_dialog.dialog({
                title: <c:if test="${sign }">'会签' || </c:if> '转交工作',// '填写处理意见',
                width: 450,
                height: 418 <c:if test="${sign }"> - 150</c:if>,
                closed:false,
                cache:false,
                modal: true,
                href: 'act/commentAddPage.do',
                queryParams: {businessid: businessid, processid: processid, title: title <c:if test="${sign }">, sign: 1</c:if> },
                onClose: function() {
                    location.href = 'act/workHandlePage.do?taskid=${process.taskid }&taskkey=${process.taskkey }&instanceid=${process.instanceid }'
                }
            });
        }

        /**
         *
         */
        function updateBusinessId(businessId) {

            $.ajax({
                dataType: 'json',
                type: 'post',
                data: 'id=${process.id }&businessid='+ businessId,
                url: 'act/updateProcessBussinessId.do',
                success: function(json) {

                    if(!json.flag) {
                        $.messager.alert('提醒', '保存出错！');
                        return false;
                    }

                    location.href = 'act/workHandlePage.do?taskid=${process.taskid }&taskkey=${process.taskkey }&instanceid=${process.instanceid }'
                },
                error: function(data) {
                    $.messager.alert('错误', '保存出错！');
                }
            });
        }

        function viewWorkFlow() {

            if('${process.id }' == ''){
                return false;
            }

            var id = getRandomid();

            $('body').append('<div id="activiti-dialog' + id + '-workHandlePage"></div>');
            $('#activiti-dialog' + id + '-workHandlePage').dialog({
                title:'查看流程',
                //width:600,
                //height:450,
                closed:false,
                cache:false,
                modal: true,
                maximizable:true,
                maximized: true,
                resizable:true,
                //href:'act/commentListPage.do?type=3&id=${process.id }'
                href:'act/commentViewPage.do?id=${process.id }',
                onClose: function() {
                    $('head').append('<base id="basePath" href="' + base_href + '"></base>');
                    $('#activiti-dialog' + id + '-workHandlePage').dialog('destroy');
                }
            });
        }

        /**
         * 变更流程处理人
         */
        function updateProcessAssignee() {

            // form submit
            $('#activiti-form-changeAssigneePage').form({
                onSubmit: function(){

                    var flag = $('#activiti-form-changeAssigneePage').form('validate');
                    if(!flag) {

                        return false;
                    }

                    loading("提交中..");
                },
                success: function(data) {

                    loaded();
                    var json = $.parseJSON(data);

                    // 变更成功
                    if(json.flag) {

                        // 刷新未接收
                        var win2 = tabsWindowURL('/act/myWorkPage6.do');
                        try{
                            if(win2 != null){
                                win2.$('#activiti-datagrid-myWorkPage').datagrid('reload');
                            }
                        }catch(e){}

                        // 刷新办理中
                        var win = tabsWindowURL('/act/myWorkPage7.do');
                        try{
                            if(win != null){
                                win.$('#activiti-datagrid-myWorkPage').datagrid('reload');
                            }
                        }catch(e){}

                        $.messager.alert('提醒', '变更成功。');

                        top.removeTabRemind(top.getNowTabTitle());

                        top.closeNowTab();
                        return true;

                    } else {

                        $.messager.alert('提醒', '变更失败！');
                        return true;
                    }
                }
            }).submit();
        }

        /**
         * 验证流程状态
         */
        function checkProcessStatus(call) {

            var inited = checkInited();
            if(!inited) {
                $.messager.alert('警告', '自定义表单正在初始化。');
                return false;
            }

            loading('验证中...');
            $.ajax({
                type: 'post',
                url: 'act/selectProcess.do',
                data: {id: '${process.id }'},
                dataType: 'json',
                async: false,
                success: function(json) {

                    loaded();

                    var flag = true;
                    var process = json.process;

                    if(process == null) {
                        flag = false;
                    } else {

                        var pstatus = json.process.status;
                        var pisend = json.process.isend;

                        if(process == null || pstatus == '2' || pstatus == '3' || pstatus == '9' || pisend == '1') {

                            flag = false;
                        }
                    }
                    if(!flag) {
                        $.messager.alert("提醒", "无法对该工作进行操作！<br/>可能由于该工作已经结束，或处于挂起、删除状态。");
                        return false;
                    }

                    <c:choose>
                        <c:when test="${sign }">
                            if(process.taskid) {
                                $.messager.alert("提醒", "无法对该工作进行会签操作！<br/>可能由于该工作已经被处理。");
                                return false;
                            }
                        </c:when>
                        <c:otherwise>
                            if(process.taskid != '${process.taskid }') {
                                $.messager.alert("提醒", "无法对该工作进行操作！<br/>可能由于该工作已经被处理。");
                                return false;
                            }
                        </c:otherwise>
                    </c:choose>

                    if(call.data.name == 'workBackPage' || call.data.name == 'changeAssigneePage') {
                        call.data();
                    } else {

                        // capture keywords
                        {
                            var keywordrule = new Array();
                            <c:if test="${not empty keywordrule }">
                                <c:if test="${not empty keywordrule.keyword1 }">
                                    keywordrule.push('${keywordrule.keyword1 }');
                                </c:if>
                                <c:if test="${not empty keywordrule.keyword2 }">
                                    keywordrule.push('${keywordrule.keyword2 }');
                                </c:if>
                                <c:if test="${not empty keywordrule.keyword3 }">
                                    keywordrule.push('${keywordrule.keyword3 }');
                                </c:if>
                                <c:if test="${not empty keywordrule.keyword4 }">
                                    keywordrule.push('${keywordrule.keyword4 }');
                                </c:if>
                                <c:if test="${not empty keywordrule.keyword5 }">
                                    keywordrule.push('${keywordrule.keyword5 }');
                                </c:if>
                            </c:if>
                            var words = new Array();
                            $.each(keywordrule, function(index, value) {

                                <c:choose>
                                    <c:when test="${not empty definition.businessurl }">
                                        var key = value;
                                        var id = $('[name="' + key + '"]').attr('id');

                                        if(/-hidden$/.test(id)) {

                                            id = id.replace(/-hidden$/, '');
                                            var code = '';
                                            var name = '';
                                            var error = false;

                                            try {
                                                code = $('#' + id).widget('getValue');
                                                name = $('#' + id).widget('getText');
                                            } catch(e) {
                                                error = true;
                                            }

                                            if(error) {
                                                try {
                                                    error = false;
                                                    code = $('#' + id).goodsWidget('getValue');
                                                    name = $('#' + id).goodsWidget('getText');
                                                } catch(e) {
                                                    error = true;
                                                }
                                            }

                                            if(error) {
                                                try {
                                                    error = false;
                                                    code = $('#' + id).customerWidget('getValue');
                                                    name = $('#' + id).customerWidget('getText');
                                                } catch(e) {
                                                    error = true;
                                                }
                                            }

                                            if(error) {
                                                try {
                                                    error = false;
                                                    code = $('#' + id).supplierWidget('getValue');
                                                    name = $('#' + id).supplierWidget('getText');
                                                } catch(e) {
                                                    error = true;
                                                }
                                            }

                                            if(error) {
                                                try {
                                                    error = false;
                                                    code = $('#' + id).storageGoodsWidget ('getValue');
                                                    name = $('#' + id).storageGoodsWidget ('getText');
                                                } catch(e) {
                                                    error = true;
                                                }
                                            }

                                            if(code != null && code != '') {
                                                var word = [code, name];
                                                words.push(word.join(':'));
                                            }
                                        } else {

                                            var text = $('[name="' + key + '"]').val();
                                            if(text != null) {
                                                words.push(text);
                                            }
                                        }
                                    </c:when>
                                    <c:when test="${not empty definition.formkey }">
                                        //$.each(keywordrule, function(index, value) {

                                            var itemid = value;
                                            if(window.formcontent.retDom('[agentitemid=' + itemid + ']').is('[plugins="widget"]')) {

                                                if(window.formcontent.retDom('[agentitemid=' + itemid + ']').attr('widgetselectval') != undefined
                                                        && window.formcontent.retDom('[agentitemid=' + itemid + ']').attr('widgetselectval') != null
                                                        && window.formcontent.retDom('[agentitemid=' + itemid + ']').attr('widgetselectval') != '') {

                                                    var word = window.formcontent.retDom('[agentitemid=' + itemid + ']').attr('widgetselectval') + ':' + window.formcontent.retDom('[agentitemid=' + itemid + ']').attr('widgetselecttext');
                                                    words.push(word);
                                                }

                                            } else if(window.formcontent.retDom('[agentitemid=' + itemid + ']').is('input')) {
                                                words.push(window.formcontent.retDom('[agentitemid=' + itemid + ']').val());
                                            } else if(window.formcontent.retDom('[agentitemid=' + itemid + ']').is('textarea')) {
                                                words.push(window.formcontent.retDom('[agentitemid=' + itemid + ']').val());
                                            } else if(window.formcontent.retDom('[agentitemid=' + itemid + ']').is('select')) {
                                                var word = window.formcontent.retDom('[agentitemid=' + itemid + ']').val() + ':' + window.formcontent.retDom('[agentitemid=' + itemid + ']').find('option[value=' + $('[agentitemid=' + itemid + ']').val() + ']').text();
                                                words.push(word);
                                            } else if(window.formcontent.retDom('[agentitemid=' + itemid + ']').is('span')) {

                                                var arr = new Array();
                                                window.formcontent.retDom('[agentitemid=' + itemid + ']').children().each(function() {

                                                    if($(this).is(':checked')) {

                                                        arr.push($(this).attr('value') + ':' + $(this).attr('text'));
                                                    }
                                                });

                                                words.push(arr.join(';'));
                                            }

                                        //});
                                    </c:when>
                                </c:choose>

                            });

                            loaded();
        //                    loading('提交中...');
                            $.ajax({
                                type: 'post',
                                dataType: 'json',
                                url: 'act/captureKeyword.do',
                                data: {
                                    id: '${process.id }',
                                    keyword1: words.length >= 1 ? words[0] : '',
                                    keyword2: words.length >= 2 ? words[1] : '',
                                    keyword3: words.length >= 3 ? words[2] : '',
                                    keyword4: words.length >= 4 ? words[3] : '',
                                    keyword5: words.length >= 5 ? words[4] : ''
                                },
                                success: function(json) {

                                    loaded();
                                    <c:choose>
                                        <c:when test="${sign }">
                                            call.data('${process.businessid }');
                                        </c:when>
                                        <c:otherwise>
                                            workFormSubmit(call);
                                        </c:otherwise>
                                    </c:choose>

                                },
                                error: function() {

                                    $.messager.alert('提醒', '提取关键字失败！');
                                }
                            })
                        }


                    }
                },
                error: function() {

                    $.messager.alert("警告", "验证失败！<br/>如果一直出现该提示，请联系管理员。");
                }
            });
        }
        var formWidgetOption = {};

        /**
         * 打开form页面参照窗口
         * @param option
         */
        function showFormWidgetWindow(option) {

            formWidgetOption = option;
            var divid = 'activiti-formWidget' + getRandomid() + '-workHandlePage';
            $('body').append('<div id="' + divid + '"></div>');

            var $div = $('#' + divid);
            $div.dialog({
                title: option.title,
                width: 450,
                height: 250,
                closed: false,
                cache: false,
                modal: true,
                maximizable: true,
                resizable: true,
                href: 'act/formWidgetPage.do',
                onClose: function() {
                    $div.dialog('destroy');
                },
                buttons:[
                    {
                        text: "确定",
                        handler: function () {

                            var flag = $('#activiti-form-formWidgetPage').form('validate');
                            if(!flag) {

                                return false;
                            }

                            // 参照窗口widget/商品参照窗口goodWidget/客户参照窗口customerWidget 选择项目时，进行回设
                            window.formcontent.widgetOption.formWidgetCallback(
                                    window.formcontent.widgetOption.selectData,
                                    window.formcontent.widgetOption.selectObject);
                            $div.dialog('close', true);
                        }
                    },
                    {
                        text:"取消",
                        handler:function(){
                            $div.dialog('close', true);
                        }
                    }
                ]
            });
        }

        /**
         * 验证自定义表单是否已经完成初始化
         * @returns {boolean}
         */
        function checkInited() {

            if(!window.formcontent) {
                return true;
            }

            return window.formcontent.inited == '1';
        }
    </script>
</c:if>
</body>
</html>
