<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>流程查看</title>
  </head>
  <body>
    <table id="activiti-table-commentListPage">
    	<tr>
    		<td colspan="2"><font color="#2E2E5A"><b>流程审批</b></font></td>
    	</tr>
    	<c:forEach var="comment" items="${list }" >
	    	<tr taskkey="${comment.taskkey}">
	    		<td taskkey="${comment.taskkey}">步骤：${comment.taskname }</td>
	    		<td>
	    			<input type="hidden" name="current_taskkey" value="${comment.taskkey }"/>
	    			<input type="hidden" name="current_taskid" value="${comment.taskid }"/>
	    			<font color="red"><b>办理人：${comment.assigneename }</b></font> [${comment.assignee }] <br />
	    			<c:if test="${comment.begintime != null }">开始于：${comment.begintime }</c:if> <c:if test="${comment.endtime != null }">结束于：${comment.endtime}</c:if><br />
	    			<c:if test="${not empty comment.endtime }">
		    			<c:choose>
		    				<c:when test="${comment.agree eq '1'}">同意。</c:when>
		    				<c:otherwise><font color="red">不同意。</font></c:otherwise>
		    			</c:choose>
	    			</c:if>
	    			<c:if test="${!empty comment.comment }">意见：${comment.comment }</c:if>
	    		</td>
	    	</tr>
    	</c:forEach>
    	<tr style="display: none;">
    		<td>
    			<c:choose>
    				<c:when test="${!empty process.taskid }">步骤：<c:out value="${process.taskname }"></c:out></c:when>
    				<c:otherwise>步骤：开始</c:otherwise>
    			</c:choose>
    		</td>
    		<td>
    			<font color="red">
    				<b>
    					办理人：${assignee.name }
    				</b>
    			</font>
    			[
    			<c:choose>
    				<c:when test="${!empty process.taskid }">
    					<font color="red">未处理</font>
    				</c:when>
    				<c:otherwise>
    					<font color="green">处理中</font>
    				</c:otherwise>
    			</c:choose>

    			]
    		</td>
    	</tr>
    </table>
    <style type="text/css">
    	#activiti-table-commentListPage{border-collapse:collapse;border:1px solid #B8D1E2;width:100%;bckground:#F0F0F0;}
    	#activiti-table-commentListPage td{border:1px solid #B8D1E2;line-height:22px;padding-left:10px;}
    </style>
    <script type="text/javascript">
    
    $(function(){

		var taskkey = $('input[name=current_taskkey]').last().val();
		var taskid = $('input[name=current_taskid]').last().val();
		
		if('${process.taskkey }' && taskkey != '${process.taskkey }' && taskkey != 'process_end') {
		
			$('tr').last().show();
		}

		var signTaskList = JSON.parse('${signTaskList }');
		signTaskList.map(function (item) {
			$('#activiti-table-commentListPage tr[taskkey]').each(function (index, item) {

			    var $current = $(this);
			    for(var i = 1, max = $('#activiti-table-commentListPage tr[taskkey]').length - index; i < max; i++) {
			        var travelIndex = (index + i);
			        var $travelRow = $('#activiti-table-commentListPage tr[taskkey]:eq(' + travelIndex + ')');

			        if($current.attr('taskkey') == $travelRow.attr('taskkey')) {
                        $travelRow.find('td').first().hide();
                        $current.find('td').first().attr('rowspan', parseInt(travelIndex) - parseInt(index) + 1);
                        $current.css({background: '#fff9be'});
                        $travelRow.css({background: '#fff9be'});
                        $current.attr('title', '会签节点');
                        $travelRow.attr('title', '会签节点');
					} else {
			            break;
					}
				}

			    // 该行不是最后一行
                if(index < $('#activiti-table-commentListPage tr[taskkey]').length - 1) {

				}

            })
        });
    });

    function mergeCells(signTaskList) {

    }
    
    </script>
  </body>
</html>
