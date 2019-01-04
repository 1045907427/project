<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>处理工作</title>
</head>
<body>
<script type="text/javascript">

    <%--
    $(function(){$.ajax({type:"post",url:"act/selectProcess.do",data:{id:"${param.id }"},dataType:"json",success:function(c){var b=true;var d=c.process;var e=d.status;var a=d.isend;if(d==null){b=false}else{if(d==null||e=="2"||e=="3"||e=="9"||a=="1"){b=false}if("${param.taskid}"!=d.taskid){b=false}}if(!b){return false}},error:function(){}});$.ajax({type:"post",url:"act/canBackWork.do",data:{id:"${param.id }"},dataType:"json",success:function(a){if(!a.flag){$("#activiti-back-workHandleFooterPage").hide()}}})});function updateBusinessId(a){$.ajax({dataType:"json",type:"post",data:{id:"${param.id }",businessid:a},url:"act/updateProcessBussinessId.do",success:function(b){if(!b.flag){alert("保存出错！");return false}location.href="act/phone/commentAddPage.do?id=${param.id }&taskid=${param.taskid }"},error:function(b){alert("保存出错！")}})}function checkProcessStatus(a,b){$.ajax({type:"post",url:"act/selectProcess.do",data:{id:"${param.id }"},dataType:"json",async:false,success:function(e){var d=true;var f=e.process;var g=f.status;var c=f.isend;if(f==null){d=false}else{if(f==null||g=="2"||g=="3"||g=="9"||c=="1"){d=false}if("${param.taskid}"!=f.taskid){d=false}}if(!d){alertMsg("该工作已经失效，无法进行处理。");$("#activiti-next-workHandleFooterPage").hide();$("#activiti-back-workHandleFooterPage").hide();return false}b(a);return true},error:function(){alertMsg("验证失败，无法进行处理。");return false}})}function workBackPage(){location.href="act/phone/workBackPage.do?id=${param.id }&taskid=${param.taskid }"};
    --%>
    $(function() {

        $('#activiti-footer-workHandleFooterPage').parent().before('<br/><br/>');

        $.ajax({
            type: 'post',
            url: 'act/selectProcess.do',
            data: {id: '${param.id }'},
            dataType: 'json',
            success: function (json) {

                var flag = true;

                var process = json.process;
                var pstatus = process.status;
                var pisend = process.isend;

                if (process == null) {

                    flag = false;
                } else {

                    if(process == null || pstatus == '2' || pstatus == '3' || pstatus == '9' || pisend == '1') {

                        flag = false;
                    }

                    if('${param.taskid}' != process.taskid) {

                        flag = false;
                    }
                }

                if (!flag) {

                    return false;
                }

            },
            error: function () { }
        });

        $.ajax({
            type: 'post',
            url: 'act/canBackWork.do',
            data: {id: '${param.id }'},
            dataType: 'json',
            success: function(json) {

                if(!json.flag) {

                    $('#activiti-back-workHandleFooterPage').hide();
                }
            }
        });
    });

    /**
     * 保存表单数据
     */
    function updateBusinessId(businessId) {

        androidLoading();
        $.ajax({
            dataType: 'json',
            type: 'post',
            data: {id: '${param.id }', businessid: businessId},
            url: 'act/updateProcessBussinessId.do',
            success: function(json) {

                if(!json.flag) {
                    alert('保存出错！');
                    return false;
                }

                <c:choose>
                    <c:when test="${param.sign eq 1}">
                        location.href = 'act/phone/commentAddPage.do?id=${param.id }&taskid=${param.taskid }&sign=1';
                    </c:when>
                    <c:otherwise>
                        location.href = 'act/phone/commentAddPage.do?id=${param.id }&taskid=${param.taskid }&sign=0';
                    </c:otherwise>
                </c:choose>
            },
            error: function(data) {

                alert('保存出错！');
            }
        });
    }

    /**
    * check process status
    * @param args
    * @param call
     */
    function checkProcessStatus(args, call) {

        hideFooter();
        $.ajax({
            type: 'post',
            url: 'act/selectProcess.do',
            data: {id: '${param.id }'},
            dataType: 'json',
            async: false,
            success: function (json) {

                var flag = true;

                var process = json.process;
                var pstatus = process.status;
                var pisend = process.isend;

                if (process == null) {

                    flag = false;
                } else {

                    if(process == null || pstatus == '2' || pstatus == '3' || pstatus == '9' || pisend == '1') {

                        flag = false;
                    }

                    <c:if test="${param.sign ne '1'}">
                        if('${param.taskid}' && '${param.taskid}' != process.taskid) {

                            flag = false;
                        }
                    </c:if>
                }

                if (!flag) {

                    alertMsg('该工作已经失效，无法进行处理。');
                    $('#activiti-next-workHandleFooterPage').hide();
                    $('#activiti-back-workHandleFooterPage').hide();
                    return false;
                }
                var flag = call(args);
                if(flag == false) {

                    showFooter();

                    <%-- 修复BUG：表单验证失败时，失败项目无法获取焦点。 start --%>
                    setTimeout(function () {
                        $('form').submit();
                    }, 100);
                    <%-- 修复BUG：表单验证失败时，失败项目无法获取焦点。 end --%>

                }
                return true;
            },
            error: function () {

                alertMsg('验证失败，无法进行处理。');
                return false;
            }
        });
    }

    function workBackPage() {

        location.href = 'act/phone/workBackPage.do?id=${param.id }&taskid=${param.taskid }';
    }

    function hideFooter() {

        $('#activiti-next-workHandleFooterPage').attr('class', $('#activiti-next-workHandleFooterPage').attr('class') + ' ui-disabled');
        $('#activiti-back-workHandleFooterPage').attr('class', $('#activiti-back-workHandleFooterPage').attr('class') + ' ui-disabled');
        $('#activiti-attach-workHandleFooterPage').attr('class', $('#activiti-attach-workHandleFooterPage').attr('class') + ' ui-disabled');
        $('#activiti-comment-workHandleFooterPage').attr('class', $('#activiti-comment-workHandleFooterPage').attr('class') + ' ui-disabled');

        $('#activiti-popupMenu-workHandleFooterPage').popup('close');
    }

    function showFooter() {

        $('#activiti-next-workHandleFooterPage').attr('class', $('#activiti-next-workHandleFooterPage').attr('class').replace('ui-disabled', ''));
        $('#activiti-back-workHandleFooterPage').attr('class', $('#activiti-back-workHandleFooterPage').attr('class').replace('ui-disabled', ''));
        $('#activiti-attach-workHandleFooterPage').attr('class', $('#activiti-attach-workHandleFooterPage').attr('class').replace('ui-disabled', ''));
        $('#activiti-comment-workHandleFooterPage').attr('class', $('#activiti-comment-workHandleFooterPage').attr('class').replace('ui-disabled', ''));

    }

</script>
<style type="text/css">
    .ui-popup-container,.ui-popup {
        position: fixed !important;
        bottom: 0.15rem !important;
        left: 0.15rem !important;
    }
</style>

<div id="activiti-footer-workHandleFooterPage" data-role="footer" data-position="fixed">
    <a href="#activiti-popupMenu-workHandleFooterPage" data-rel="popup" data-transition="" class="ui-btn ui-corner-all ui-shadow ui-btn-inline ui-icon-gear ui-btn-icon-left ui-btn-a">操作</a>
    <div data-role="popup" id="activiti-popupMenu-workHandleFooterPage" data-theme="a">
        <ul data-role="listview" data-inset="false" style="min-width: 10rem;">
            <li data-role="list-divider">请选择操作</li>
            <c:choose>
                <c:when test="${param.sign eq '1'}">
                    <li><a id="activiti-sign-workHandleFooterPage" href="javascript:void(0);" onclick="javascript:checkProcessStatus(updateBusinessId, workFormSubmit);" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-check">会签</a></li>
                </c:when>
                <c:otherwise>
                    <li><a id="activiti-next-workHandleFooterPage" href="javascript:void(0);" onclick="javascript:checkProcessStatus(updateBusinessId, workFormSubmit);" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-check">转交</a></li>
                    <li><a id="activiti-back-workHandleFooterPage" href="javascript:void(0);" onclick="javascript:checkProcessStatus(null, workBackPage);" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" rel="external">驳回</a></li>
                </c:otherwise>
            </c:choose>
            <li><a id="activiti-attach-workHandleFooterPage" href="act/phone/workAttachPage.do?processid=${param.id }&commentid=${param.commentid }" onclick="javascript:;" data-transition="slideup" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-tag">附件</a></li>
            <li><a id="activiti-comment-workHandleFooterPage" href="act/phone/commentListPage.do?processid=${param.id }" onclick="javascript:;" data-transition="slideup" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-clock">审批记录</a></li>
        </ul>
    </div>
</div>
</body>
</html>
