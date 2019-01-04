<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="process" uri="/tag/process" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	  <title>打印[行政采购付款申请]</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.3.js" charset="UTF-8"></script>
      <link rel="stylesheet" href="<%=request.getContextPath()%>/oa/common/css/print.css" type="text/css"/>
      <style type="text/css">
          table {
              border-collapse: collapse;
              border: 1px solid #000;
          }
          table th,td{
              border: 1px solid #000;
          }
      </style>
      <style type="text/css" media="print">
          .noprint {
              display: none;
          }
      </style>
  </head>
  <body>
    <div class="limit-width align-center">
        <process:processHeader process="${process}"/>
        <table>
            <tr>
                <th class="len80 left">申请日期</th>
                <td class="len180"><span><c:out value="${pay.businessdate }"/></span></td>
                <th class="len80 left">付款日期</th>
                <td class="len180"><span><c:out value="${pay.paydate }"/></span></td>
                <th class="len80 left">申请OA</th>
                <td class="len180"><span><c:out value="${pay.relateoaid }"/></span></td>
            </tr>
            <tr>
                <th class="left">收款单位</th>
                <td class=""><span><c:out value="${pay.receivername }"/></span></td>
                <th class="left">开户银行</th>
                <td class=""><span><c:out value="${pay.receiverbank }"/></span></td>
                <th class="left">银行账号</th>
                <td class=""><span><c:out value="${pay.receiverbankno }"/></span></td>
            </tr>
            <tr>
                <th class="left">大写金额</th>
                <td class=""><span><c:out value="${pay.upperpayamount }"/></span></td>
                <th class="left">付款金额</th>
                <td class=""><span precision="2"><c:out value="${pay.payamount }"/></span></td>
                <th class="left">用途</th>
                <td class=""><span><c:out value="${pay.usage }"/></span></td>
            </tr>
            <tr>
                <th class="left">付款银行</th>
                <td class=""><span><c:out value="${bank.name }"/></span></td>
                <th class="left">发票金额</th>
                <td class=""><span precision="2"><c:out value="${pay.invoiceamount }"/></span></td>
                <td colspan="2"></td>
            </tr>
            <tr>
                <th class="left">备注</th>
                <td class="" colspan="5"><span><c:out value="${pay.remark }"/></span></td>
            </tr>
        </table>
        <div style="">&nbsp;</div>
        <div class="loading comment">正在获取审批信息...</div>
        <script type="text/javascript">
            $.ajax({
                type: 'get',
                url: '../../act/getCommentImgInfo.do?processid=${param.processid }',
                dataType: 'json',
                success: function (commentArr) {

                    var html = new Array();
                    var taskcount = 0;
                    $('.loading').html('');

                    commentArr.sort(function (o1, o2) {

                        if((o1.task && o1.task[0] && o1.task[0].info5)
                            && (o2.task && o2.task[0] && o2.task[0].info5)) {
                            return o1.task[0].info5.localeCompare(o2.task[0].info5);
                        } else if((o1.task && o1.task[0] && o1.task[0].info5)) {
                            return -1;
                        } else if((o2.task && o2.task[0] && o2.task[0].info5)) {
                            return 1;
                        } else {
                            return 0;
                        }

                    });

                    for(var i in commentArr) {

                        var comment = commentArr[i];
                        if(comment.task && comment.task[0] && comment.task[0].info5) {

                            var taskname = comment.taskName;
                            var assignee = comment.task[0].info1.substring(4);
                            var time = comment.task[0].info5.substring(5, 15);
                            if(taskcount > 0) {
                                html.push('<div style="width: 20px; float: left; font-size: 13px;"><br/>-></div>');
                            }
                            html.push('<div style="width: 80px; font-size: 13px; float: left;">');
                            html.push('<div style="width: 80px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">' + taskname + '</div>');
                            html.push('<div style="width: 80px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">' + assignee + '</div>');
                            html.push('<div style="width: 80px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; height: 30px;">' + time + '</div>');
                            html.push('</div>');

                            taskcount ++ ;
                        }
                    }

                    if(taskcount > 0) {
                        $('.loading').html(html.join(''));
                    }
                }
            })
        </script>
	</div>
    <script type="text/javascript">
    <!--
    
    $(function() {

        $('span[precision]').each(function(index) {

            var text = $(this).text();
            var precision = $(this).attr('precision');

            if(text != undefined && text != '' && text != null) {

                text = parseFloat(text).toFixed(parseInt(precision));
            }

            $(this).text(text);
            $(this).parent().css({'text-align': 'right'});
        });

    });

	-->
	</script>
  </body>
</html>