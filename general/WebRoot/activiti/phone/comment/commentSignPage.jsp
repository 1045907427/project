<%--
  Created by IntelliJ IDEA.
  User: limin
  Date: 2017/6/21
  Time: 9:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="struts"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>工作转交页面(手机)</title>
    <%@include file="/phone/common/include.jsp" %>
    <script type="text/javascript">

        // var next=$.parseJSON('${next }');var nexttaskkey='';$(document).on('pageshow','#activiti-main-commentSignPage',function(){$('.nextuser').hide();$('#activiti-form-commentSignPage').submit(function(){$(this).validate({rules:{'assignee2':{required:true}},focusInvalid:true});var d=$(this).valid();if(!d){return false}loading();$(this).ajaxSubmit({url:'act/updateCommentInfo.do',type:'post',success:function(a){loaded();var b=$.parseJSON(a);if(b.flag){alertMsg('转交成功。');var c=localStorage.getItem('url')||'';if(c==''){backMain();return true}location.href=c;return true}alertMsg('转交失败！可能因为该工作已被转交、挂起、删除或已经结束！')}});return false});$('input[type=radio]').off().on('click',function(e){var a=$(this).attr('nodetype');var b=$(this).attr('value')||'';if(b==nexttaskkey){return true}nexttaskkey=b;$('#activiti-nexttaskkey-commentSignPage').val(b);$('#activiti-nexttasktype-commentSignPage').val(a);$('#activiti-assignee-commentSignPage').val('');$('#activiti-assignee2-commentSignPage').val('');if(a=='userTask'){$('.nextuser').removeClass('nodisplay');$('.nextuser').show();var c=null;for(var i in next){var d=next[i];if(d.id==nexttaskkey){c=d;break}}if(c!=null&&c.next.count==1){$('#activiti-assignee2-commentSignPage').val(c.next.username);$('#activiti-assignee-commentSignPage').val(c.next.userid);$('#activiti-nextAssignee-hidden-commentSignPage').val(c.next.userid)}$('#activiti-assignee2-commentSignPage').off().on('click',function(e){if(c==null){return true}androidWidget({type:'widget',onSelect:'selectAssignee',referwid:'RT_T_SYS_USER',checkType:1,paramRule:[{field:'userid',op:'in',value:c.next.widgetstr+',　'}]})})}else{$('.nextuser').hide()}});$('input[type=radio]:first').prev().trigger('click')});function selectAssignee(a){$('#activiti-assignee-commentSignPage').val(a.id);$('#activiti-assignee2-commentSignPage').val(a.name);$('#activiti-nextAssignee-hidden-commentSignPage').val(a.id)}

        var next = $.parseJSON('${next }');
        var nexttaskkey = '';   // 下一节点taskkey

        $(document).on('pageshow', '#activiti-main-commentSignPage', function() {

            $('.nextuser').hide();
            $('#activiti-form-commentSignPage').submit(function(){

                $(this).validate({
                    rules: {
                        'assignee2': {
                            required: true
                        }
                    },
                    focusInvalid: true
                });

                // 验证
                var flag = $(this).valid();
                if(!flag) {

                    return false;
                }

                androidLoading();
                $(this).ajaxSubmit({
                    url: 'act/updateCommentInfo.do',
                    type: 'post',
                    success: function(data) {

                        androidLoaded();
                        var json = $.parseJSON(data);
                        if(json.flag) {

                            // 转交成功后，跳转
                            alertMsg('转交成功。');
                            var url = localStorage.getItem('url') || '';

                            if(url == '') {

                                backMain();
                                return true;
                            }

                            location.href = url;
                            return true;
                        }

                        alertMsg('转交失败！可能因为该工作已被转交、挂起、删除或已经结束！');
                    }
                });

                return false; //此处必须返回false，阻止常规的form提交

            });

            // radio绑定事件，点击时初始化人员控件
            $('input[type=radio][taskkey]').off().on('click', function(e) {

                var type = $(this).attr('nodetype');
                var sign = $(this).is('[sign]');
                var currenttaskkey = $(this).attr('value') || '';
                if(currenttaskkey == nexttaskkey) {

                    return true;
                }

                nexttaskkey = currenttaskkey;

                $('#activiti-nexttaskkey-commentSignPage').val(currenttaskkey);
                $('#activiti-nexttasktype-commentSignPage').val(type);

                $('#activiti-assignee-commentSignPage').val('');
                $('#activiti-assignee2-commentSignPage').val('');

                $('.signNext').addClass('nodisplay');
                $('.signNext').hide();

                if(sign) {

                    $('.nextuser').removeClass('nodisplay');
                    $('.nextuser').show();

                    $('.signNext').removeClass('nodisplay');
                    $('.signNext').show();

                    $('#activiti-signNextAssignee-commentSignPage').val('');
                    $('#activiti-signNextAssignee2-commentSignPage').val('');

                    var rule = null;
                    var signNext = null;
                    for(var i in next) {

                        var temp = next[i];
                        if(temp.id == nexttaskkey) {

                            rule = temp;
                            signNext = temp.next;
                            break;
                        }
                    }

                    if(rule) {

                        $('#activiti-assignee2-commentSignPage').off();
                        $('#activiti-assignee2-commentSignPage').val(rule.users.username);
                        $('#activiti-assignee-commentSignPage').val(rule.users.userid);
                        $('#activiti-nextAssignee-hidden-commentSignPage').val(rule.users.userid);
                    }

                    if(signNext) {
                        var htmlArr = new Array();
                        signNext.map(function (item) {
                            var html = '<fieldset data-role="controlgroup">' +
                                '<label>' +
                                '<input type="radio" name="signnexttask" value="' + item.taskkey + '" taskkey="' + item.taskkey + '" onclick="javascript:$(\'#activiti-signNextAssignee2-commentSignPage\').val(\'' + item.users.username + '\');$(\'#activiti-signNextAssignee-commentSignPage\').val(\'' + item.users.userid + '\');"/>' + item.taskname +
                                '</label>' +
                                '</fieldset>';
                            htmlArr.push(html);
                        });
                        $('#activiti-signNextTask-commentSignPage').html('').append(htmlArr.join(''));
                        $('[name=signnexttask]').first().click();
                        $('#activiti-signNextTask-commentSignPage').trigger("create");
                    }

                    return true;
                }

                if(type == 'userTask') {

                    $('.nextuser').removeClass('nodisplay');
                    $('.nextuser').show();

                    var rule = null;
                    for(var i in next) {

                        var temp = next[i];
                        if(temp.id == nexttaskkey) {

                            rule = temp;
                            break;
                        }
                    }

                    if(rule != null && rule.users.count == 1) {

                        $('#activiti-assignee2-commentSignPage').val(rule.users.username);
                        $('#activiti-assignee-commentSignPage').val(rule.users.userid);
                        $('#activiti-nextAssignee-hidden-commentSignPage').val(rule.users.userid);
                    }

                    $('#activiti-assignee2-commentSignPage').off().on('click', function(e) {

                        if(rule == null) {
                            return true;
                        }

                        androidWidget({
                            type: 'widget',
                            onSelect: 'selectAssignee',
                            referwid: 'RT_T_SYS_USER',
                            // onlyLeafCheck: 1,
                            checkType: 1,
                            paramRule: [{field: 'userid', op: 'in', value: rule.users.widgetstr + ',　'} ]
                        });
                    });

                } else {

                    $('.nextuser').hide();
                }
            });

            // 默认选中第一个radio
            $('input[type=radio]:first').prev().trigger('click');
        });

        // 选中候选人触发该方法
        function selectAssignee(data) {

            $('#activiti-assignee-commentSignPage').val(data.id);
            $('#activiti-assignee2-commentSignPage').val(/*data.id + ':' + */data.name);
            $('#activiti-nextAssignee-hidden-commentSignPage').val(data.id);
        }

    </script>
</head>
<body>
<div data-role="page" id="activiti-main-commentSignPage">
    <form action="act/updateCommentInfo.do" method="post" id="activiti-form-commentSignPage">
        <input type="hidden" readonly="readonly" name="comment.signature" value="${user.name }"/>
        <input type="hidden" readonly="readonly" value="${time }" />

        <input type="hidden" id="activiti-id-commentSignPage" value="${comment.id }" name="comment.id"/>
        <input type="hidden" id="activiti-taskid-commentSignPage" value="${comment.taskid }" name="comment.taskid"/>
        <input type="hidden" id="activiti-type-commentSignPage" name="type" />
        <input type="hidden" id="activiti-instanceid-commentSignPage" name="instanceid" value="${process.instanceid }"/>
        <input type="hidden" id="activiti-nexttaskkey-commentSignPage" name="nexttaskkey"/>
        <input type="hidden" id="activiti-nexttasktype-commentSignPage" name="nexttasktype"/>
        <input type="hidden" id="activiti-nextAssignee-hidden-commentSignPage" name="nextAssignee"/>
        <input type="hidden" id="activiti-agree-commentSignPage" value="1" name="comment.agree"/>
        <input type="hidden" value="1" name="sign"/>
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1>会签[${param.id }]</h1>
            <a href="act/phone/workHandlePage.do?id=${param.id }&taskid=${param.taskid }&instanceid=${process.instanceid }" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;" rel="external">返回</a>
        </div>
        <div class="ui-corner-all custom-corners">
            <div class="ui-bar ui-bar-b">
                <h1>当前节点：<c:out value="${process.taskname }"/></h1>
            </div>
            <div class="ui-body ui-body-a">
                <label>意见:
                    <textarea type="text" name="comment.comment" id="activiti-comment-commentSignPage" value=""></textarea>
                </label>
            </div>
        </div>
        <footer data-role="footer" data-position="fixed">
            <a href="javascript:void(0);" onclick="javascript:document.getElementById('activiti-agree-commentSignPage').value='1';$('#activiti-form-commentSignPage').submit()" id="oa-ok-commentSignPage" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-check">同意</a>
            <a href="javascript:void(0);" onclick="javascript:document.getElementById('activiti-agree-commentSignPage').value='0';$('#activiti-form-commentSignPage').submit()" id="oa-ng-commentSignPage" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-delete">反对</a>
        </footer>
    </form>
</div>
</body>
</html>