<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hd.agent.activiti.model.Process" %>
<%@ page import="com.hd.agent.common.util.CommonUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>打印</title>
      <script type="text/javascript" src="../activiti/js/jquery.js"></script>
      <style type="text/css" media="print">
          .Noprint {
              display: none;
          }
      </style>
      <style type="text/css">
          table {
              border-collapse: collapse;
              /*border:1px solid #7babcf;*/
          }
      </style>
      <style>
          html,body{
              /*height:100%;*/
              /*width:100%;*/
              padding:0;
              margin:0;
          }
          #preview{
              /*width:100%;*/
              /*height:100%;*/
              padding:0;
              margin:0;
          }
          #preview *{font-family:sans-serif;/*font-size:16px;*/}
          .process-info{table-layout: fixed;font-size: 12px; border: none;}
          .process-info th,td {height: 20px;padding: 2px; border: none;}
      </style>
      <style id="table">
          #preview table.noBorderTable td,#preview table.noBorderTable th,#preview table.noBorderTable caption{border:1px dashed #ddd !important}#preview table.sortEnabled tr.firstRow th,#preview table.sortEnabled tr.firstRow td{padding-right:20px; background-repeat: no-repeat;background-position: center right; background-image:url(../../themes/default/images/sortable.png);}#preview table.sortEnabled tr.firstRow th:hover,#preview table.sortEnabled tr.firstRow td:hover{background-color: #EEE;}#preview table{margin-bottom:10px;border-collapse:collapse;display:table;}#preview td,#preview th{ background:white; padding: 5px 10px;border: 1px solid #DDD;}#preview caption{border:1px dashed #DDD;border-bottom:0;padding:3px;text-align:center;}#preview th{border-top:1px solid #BBB;background:#F7F7F7;}#preview table tr.firstRow th{border-top:2px solid #BBB;background:#F7F7F7;}#preview tr.ue-table-interlace-color-single td{ background: #fcfcfc; }#preview tr.ue-table-interlace-color-double td{ background: #f7faff; }#preview td p{margin:0;padding:0;}
      </style>
      <style id="chartsContainerHeight">
          .edui-chart-container { height:500px}
          .edui-chart-container { height:500px}
      </style>
      <script type="text/javascript">

          <c:if test="${not empty definition.businessurl }">

            var url = '${definition.businessurl }';
            if(url.indexOf('?') >= 0) {
                url = '../' + url + '&type=print&id=${process.businessid }&processid=${process.id }';
            } else {
                url = '../' + url + '?type=print&id=${process.businessid }&processid=${process.id }';
            }

            window.location.href = url;
          </c:if>

      </script>
      <script type="text/javascript">

          <%
          Process process = (Process) request.getAttribute("process");
          String json = CommonUtils.bytes2String(process.getJson(), "UTF-8");
          %>

          // 字符串编码
          String.prototype.encodeHtml = function() {

              var s = '';
              var str = this || '';

              if (str.length == 0) {

                  return s;
              }
              s = str.replace(/&/g, "&amp;");
              s = s.replace(/</g, "&lt;");
              s = s.replace(/>/g, "&gt;");
              s = s.replace(/ /g, "&nbsp;");
              s = s.replace(/\'/g, "&#39;");
              s = s.replace(/\"/g, "&quot;");
              s = s.replace(/\//g, "&#47;");
              s = s.replace(/\\/g, "&#92;");
              s = s.replace(/\n/g, "<br>");
              return s;
          };

          // 字符串解码
          String.prototype.decodeHtml = function() {

              var s = '';
              var str = this || '';

              if (str.length == 0) {

                  return s;
              }

              s = str;
              do {
                  s = s.replace(/<br>/g, "\n");
                  s = s.replace(/&#92;/g, "\\");
                  s = s.replace(/&#47;/g, "\/");
                  s = s.replace(/&quot;/g, "\"");
                  s = s.replace(/&#39;/g, "\'");
                  s = s.replace(/&nbsp;/g, " ");
                  s = s.replace(/&gt;/g, ">");
                  s = s.replace(/&lt;/g, "<");
                  s = s.replace(/&amp;/g, "&");
              } while (/<br>/.test(s) || /&#92;/.test(s) || /&#47;/.test(s) || /&quot;/.test(s) || /&#39;/.test(s) || /&nbsp;/.test(s) || /&gt;/.test(s) || /&lt;/.test(s) || /&amp;/.test(s));
              return s;
          };

          $(function() {

              (function (data) {

                  $.each(data, function(index, item) {

                      if(item.type == 'textarea') {

                          $('[agentitemid=' + item.agentitemid + ']').text(item.vals.decodeHtml());

                      } else if(item.type == 'select') {

                          $('[agentitemid=' + item.agentitemid + ']').find('[text="' + item.vals + '"]').attr('selected', 'selected');

                      } else if(item.type == 'input[type=text][plugins=widget]') {

                          if(($('[agentitemid=' + item.agentitemid + ']').val() || '') == '') {

                              $('[agentitemid=' + item.agentitemid + ']').val(item.vals.text.decodeHtml());
                              $('[agentitemid=' + item.agentitemid + ']').attr('value', item.vals.text.decodeHtml());
                          }

                      } else if(item.type == 'input[type=text][plugins=goods]') {

                          if(($('[agentitemid=' + item.agentitemid + ']').val() || '')== '') {

                              $('[agentitemid=' + item.agentitemid + ']').val(item.vals.text.decodeHtml());
                              $('[agentitemid=' + item.agentitemid + ']').attr('value', item.vals.text.decodeHtml());
                          }

                      } else if(item.type == 'input[type=text][plugins=customer]') {

                          if(($('[agentitemid=' + item.agentitemid + ']').val() || '') == '') {

                              $('[agentitemid=' + item.agentitemid + ']').val(item.vals.text.decodeHtml());
                              $('[agentitemid=' + item.agentitemid + ']').attr('value', item.vals.text.decodeHtml());
                          }

                      } else if(item.type == 'radios') {

                          var vals = item.vals;
//                          var val = item.vals[0].val;
//                          $('[type=radio][name=' + item.agentitemid + '][value="' + val +'"]').attr('checked', 'checked');
                          for(var i in vals) {
                              var val = vals[i];
                              $('[type=radio][name=' + item.agentitemid + '][value="' + val.val +'"]').attr('checked', 'checked');
                          }

                      } else if(item.type == 'checkboxs') {

                          var vals = item.vals;
                          for(var i in vals) {
                              var val = vals[i];
                              $('[type=checkbox][name=' + item.agentitemid + '][value="' + val.val +'"]').attr('checked', 'checked');
                          }

                      } else if(item.type == 'input[type=text][plugins=numeric]') {

                          $('[agentitemid=' + item.agentitemid + ']').val(item.vals.decodeHtml());
                          $('[agentitemid=' + item.agentitemid + ']').attr('value', item.vals.decodeHtml());

                      } else if(item.type == 'input[type=text]') {

                          $('[agentitemid=' + item.agentitemid + ']').val(item.vals.decodeHtml());
                          $('[agentitemid=' + item.agentitemid + ']').attr('value', item.vals.decodeHtml());

                      } else if(item.type == 'input[type=text][plugins=compute]') {

                          $('[agentitemid=' + item.agentitemid + ']').val(item.vals.decodeHtml());
                          $('[agentitemid=' + item.agentitemid + ']').attr('value', item.vals.decodeHtml());
                      }
                  });
              })($.parseJSON('${json }'));

              setTimeout(function () {
                  $('input[type=text]').each(function() {

                      var text = $(this).val() + '&nbsp;';
                      var width = $(this).css('width');
                      var style = $(this).attr('style');
                      var align = $(this).css('text-align');

                      var span = '<span style="display:inline-block; ' + style + '">' + text + '</span>';
                      if(align != 'left' || align != 'center') {

                          span = '<span style="display:inline-block; text-align: left; ' + style + '">' + text + '</span>';
                      }

                      $(this).after(span);
                      $(this).next().css({height: ''});
                      //$(this).after('&nbsp;' + text + '&nbsp;');
                      $(this).hide();
                  });

                  $('input[type=radio]').each(function() {

                      $(this).attr('onclick', 'javascript:return false;');
                  });
                  $('input[type=checkbox].not').not('.print').each(function() {

                      $(this).attr('onclick', 'javascript:return false;');
                  });

                  $('textarea').each(function() {

                      var text = $(this).attr('text') || $(this).val();
                      if(text != undefined) {
                          text = text + '&nbsp;'
                      } else {
                          text = '&nbsp;';
                      }
                      var width = $(this).css('width');
                      var style = $(this).attr('style');
                      var align = $(this).css('text-align');

                      var span = '<span style="display:inline-block;  ' + style + '">' + text + '</span>';
                      if(align != 'left' || align != 'center') {

                          span = '<span style="display:inline-block; text-align: left; ' + style + '">' + text + '</span>';
                      }

                      $(this).after(span);
                      $(this).next().css({height: ''});
                      //$(this).after('&nbsp;' + text + '&nbsp;');
                      $(this).hide();
                  });
                  $('select').each(function() {

                      var text = $(this).children('[selected]').text();
                      var width = $(this).css('width');
                      var style = $(this).attr('style');
                      var align = $(this).css('text-align');

                      var span = '<span style="display:inline-block;  ' + style + '">' + text + '</span>';
                      if(align != 'left' || align != 'center') {

                          span = '<span style="display:inline-block; text-align: left; ' + style + '">' + text + '</span>';
                      }

                      $(this).after(span);
                      $(this).next().css({height: ''});
                      //$(this).after('&nbsp;' + text + '&nbsp;');
                      $(this).hide();

                  });
              }, 0)

              });
      </script>
  </head>
  <body class="view">
  <table style="width: 100%; border: none;" class="Noprint">
      <tr>
          <td align="center" style="width: 100%; border-bottom: 1px solid #DDD; padding: 5px;">
              <input type="button" onclick="print()" value="　打印　"/>
              <label style="font-size: 14px; font-family: 微软雅黑;"><input type="checkbox" checked="checked" class="print" onclick="javascript:if(this.checked){$('.comment').show();}else{$('.comment').hide();}"/>是否打印审批信息</label>
          </td>
      </tr>
  </table>
  <div style="margin:2px; padding: 10px; width: 780px; margin: 0px auto;">
      <table style="width: 100%; min-width: 780px; border: none;" class="process-info">
          <tr>
              <td style="width: 1%;border: none;"></td>
              <td style="width: 5%;border: none;">OA号：</td>
              <td style="width: 23%;border: none;"><u>&nbsp;<c:out value="${process.id }" />&nbsp;</u></td>
              <td style="width: 7%;border: none;">申请人：</td>
              <td style="width: 23%;border: none;"><u>&nbsp;<c:out value="${process.applyusername }" />&nbsp;</u></td>
              <td style="width: 9%;border: none;">申请时间：</td>
              <td style="width: 23%;border: none;"><u>&nbsp;<fmt:formatDate value="${process.applydate }" pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="full" />&nbsp;</u></td>
          </tr>
      </table>
  </div>
  <div id="preview" style="margin:2px; min-width: 780px; margin: 0px auto;">
      <c:choose>
          <c:when test="${empty html}">
              ${detail }
          </c:when>
          <c:otherwise>
              ${html }
          </c:otherwise>
      </c:choose>
      <div style="text-align: center;">
      <ul class="loading comment" style="text-align: center; margin: 0 auto;">正在获取审批信息...</ul>
      </div>
  </div>
  <script type="text/javascript">
      $.ajax({
          type: 'get',
          url: '../act/getCommentImgInfo.do?processid=${process.id }',
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
                  if(comment.task && comment.task[0] && comment.task[0].endtime) {

                      var taskname = comment.taskName;
                      var assignee = comment.task[0].handlename;
                      var time = comment.task[0].endtime.substring(0, 10);
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
      });

      $(function () {

          $('#preview').children().each(function (index, item) {

              var parentWidth = $('#preview')[0].clientWidth;
              var clientWidth = $(item)[0].clientWidth;
              if(clientWidth > parentWidth) {
                  $('#preview').css({width: clientWidth + 'px'});
              }

          })

      });
  </script>
  </body>
</html>
