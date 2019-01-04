<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="struts"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>工作驳回页面(手机)</title>
    <%@include file="/phone/common/include.jsp" %>
    <script type="text/javascript">

        $(function(){$("#activiti-main-workBackPage input[type=radio]").not('[disabled]').last().prev().click();$("#activiti-ok-workBackPage").off().on("click",function(){$("#activiti-form-workBackPage").submit(function(){var a=$(this).valid();if(!a){return false}$(this).ajaxSubmit({type:"post",url:"act/backWork.do",success:function(d){var c=$.parseJSON(d);if(c.flag){alertMsg("驳回成功。");var b=localStorage.getItem("url")||"";if(b==""){backMain();return true}location.href=b;return true}var e=c.msg||"";if(e!=""){alertMsg("驳回失败！"+e);return false}alertMsg("驳回失败！")}});return false}).submit()})});
        <%--
        $(function() {

            $('#activiti-main-workBackPage input[type=radio]').not('[disabled]').last().prev().click();

            $('#activiti-ok-workBackPage').off().on('click', function() {

                $('#activiti-form-workBackPage').submit(function(){

                    // 验证
                    var flag = $(this).valid();
                    if(!flag) {

                        return false;
                    }

                    $(this).ajaxSubmit({
                        type: 'post',
                        url: 'act/backWork.do',
                        success: function(data) {

                            var json = $.parseJSON(data);
                            if(json.flag) {

                                // 驳回成功后，跳转
                                alertMsg('驳回成功。');
                                var url = localStorage.getItem('url') || '';

                                if(url == '') {

                                    backMain();
                                    return true;
                                }

                                location.href = url;
                                return true;
                            }

                            var msg = json.msg || '';
                            if(msg != '') {

                                alertMsg('驳回失败！' + msg);
                                return false;
                            }

                            alertMsg('驳回失败！');
                        }
                    });

                    return false; //此处必须返回false，阻止常规的form提交

                }).submit();

            });
        });
        --%>
    </script>
</head>
<body>
<div data-role="page" id="activiti-main-workBackPage">
    <form id="activiti-form-workBackPage" action="act/backWork.do" method="post">
        <input type="hidden" name="id" value="${param.id }"/>
        <input type="hidden" name="comment.agree" value="0"/>
        <input type="hidden" name="comment.taskkey" value="<c:out value="${task.key }"></c:out>"/>
        <input type="hidden" name="comment.taskname" value="<c:out value="${task.nameExpression.expressionText }"></c:out>"/>
        <input type="hidden" name="comment.instanceid" value="${process.instanceid }"/>
        <input type="hidden" name="comment.taskid" value="${process.taskid }"/>
        <input type="hidden" name="comment.id" value="${comment.id }"/>
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1>驳回[${param.id }]</h1>
            <a href="act/phone/workHandlePage.do?id=${process.id }&taskid=${process.taskid }&instanceid=${process.instanceid }" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;" rel="external">返回</a>
        </div>
        <div class="ui-corner-all custom-corners">
            <div class="ui-bar ui-bar-b">
                <h1>当前节点：<c:out value="${process.taskname }"/></h1>
            </div>
        </div>
        <div class="ui-body ui-body-a">
            <label>意见:
                <textarea type="text" name="comment.comment" id="activiti-comment-workBackPage" value=""></textarea>
            </label>
        </div>
        <div class="ui-bar ui-bar-b">
            <h1>驳回至哪一节点</h1>
        </div>
        <div class="ui-body ui-body-a">
            <fieldset data-role="controlgroup">
                <c:forEach items="${list }" var="item">
                    <label>
                        <input type="radio" name="taskkey" value="${item.taskkey }" <c:if test="${item.sign }">disabled="disabled"</c:if> />
                        <c:out value="${item.taskname }"></c:out><c:if test="${item.sign }">(会签节点不允许驳回)</c:if>
                    </label>
                </c:forEach>
            </fieldset>
        </div>
        <footer data-role="footer" data-position="fixed">
            <a href="javascript:void(0);" id="activiti-ok-workBackPage" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-check">驳回</a>
        </footer>
    </form>
</div>
</body>
</html>