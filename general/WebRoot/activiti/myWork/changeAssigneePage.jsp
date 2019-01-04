<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>更换处理人</title>
  </head>
  <body>
    <c:choose>
        <c:when test="${not valid }">
            <div>
                <font color="#f00">该工作已经失效，可能已经被处理。</font>
            </div>
        </c:when>
        <c:otherwise>
            <style type="text/css">
                .changetable {
                    width: 100%;
                    border-collapse: collapse;
                }
                .changetable td {
                    border: 1px solid #aaaaaa;
                    padding: 3px;
                    line-height: 24px;
                }
            </style>
            <div class="easyui-layout" data-options="fit:true">
                <div data-options="region:'center',border:false">
                    <form id="activiti-form-changeAssigneePage" action="act/updateCommentInfo.do">
                        <input type="hidden" name="id" value="${param.id }"/>
                        <input type="hidden" name="taskid" value="${process.taskid }"/>
                        <input type="hidden" name="assignee" value="${process.assignee }"/>
                        <input type="hidden" id="activiti-id-changeAssigneePage" name="comment.id" value="${comment.id }"/>
                        <input type="hidden" id="activiti-taskid-changeAssigneePage" name="comment.taskid" value="${comment.taskid }"/>
                        <input type="hidden" id="activiti-comment-changeAssigneePage" name="comment.comment" value="@变更处理人@"/>
                        <input type="hidden" id="activiti-instanceid-changeAssigneePage" name="instanceid" value="${process.instanceid }"/>
                        <input type="hidden" id="activiti-nexttaskkey-changeAssigneePage" name="nexttaskkey" value="${process.taskkey }"/>
                        <input type="hidden" id="activiti-nexttasktype-changeAssigneePage" name="nexttasktype" value="userTask"/>
                        <input type="hidden" id="activiti-nextAssignee-hidden-changeAssigneePage" name="nextAssignee" value=""/>
                        <input type="hidden" id="activiti-type-changeAssigneePage" name="type" value="1"/>

                        <table class="changetable">
                            <tr>
                                <td style="background: #efefef; font-weight: 700;">当前节点：</td>
                            </tr>
                            <tr>
                                <td><c:out value="${process.taskname}" /></td>
                            </tr>
                            <tr>
                                <td style="background: #efefef; font-weight: 700;">当前节点候选人：</td>
                            </tr>
                            <tr>
                                <td><input type="text" name="assignee" id="activiti-assignee-changeAssigneePage"/></td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div data-options="region:'south',border:false" style="height:40px;">
                    <div class="buttonDivR">
                        <span id="activiti-span1-changeAssigneePage"><a href="javascript:void(0);" id="activiti-change-changeAssigneePage" class="easyui-linkbutton" data-options="iconCls:'button-open'">变更</a></span>
                        <span id="activiti-span2-changeAssigneePage"><a href="javascript:void(0);" id="activiti-close-changeAssigneePage" class="easyui-linkbutton" data-options="iconCls:'button-close'">关闭</a></span>
                    </div>
                </div>
            </div>
            <script type="text/javascript">

                $(function(){

                    loading('正在获取人员信息...');

                    $.ajax({
                        type: 'post',
                        url: 'act/getNextTaskDefinition.do',
                        data: {taskkey: '${process.taskkey }', id: '${param.id }', type: '1'},
                        dataType: 'json',
                        success: function(json) {

                            loaded();
                            widgetstr = json.widgetstr;
                            count = json.count;
                            ids = json.ids;

                            $('#activiti-assignee-changeAssigneePage').widget({
                                referwid: 'RL_T_SYS_USER',
                                singleSelect: true,
                                width: 180,
                                required: true,
                                onlyLeafCheck: true,
                                param: [
                                    {field: 'userid', op: 'in', value: widgetstr},
                                    {field: 'userid', op: 'notequal', value: '${process.assignee }'}
                                ],
                                onSelect: function(data) {

                                    $('#activiti-nextAssignee-hidden-changeAssigneePage').val(data.userid);
                                    return true;
                                }
                            });
                        },
                        error: function() {

                            loaded();
                            $.messager.alert("提醒","获取人员失败！");
                        }
                    });
                });

            </script>
        </c:otherwise>
    </c:choose>

  </body>
</html>
