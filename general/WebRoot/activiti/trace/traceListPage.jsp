<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>数据追踪</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
  <script src="activiti/js/c.js" type="text/javascript"></script>
  <link href="activiti/css/s.css" type="text/css" rel="stylesheet"></link>
    <style type="text/css">
        table.tracetable {
            width: 100%;
            border-collapse: collapse;
            border:1px solid #7babcf;
        }
        table.tracetable th{
            border: 1px solid #CCC;
            padding: 5px;
            background: #accae0;
        }
        table.tracetable td{
            border: 1px solid #CCC;
            padding: 5px;
        }
    </style>
    <script type="text/javascript">
        <!--
        var traces = new Array();
        traces.push('');
        var queue = new Array();
        -->
    </script>
  	<div class="easyui-panel" data-options="fit:true,border:false">
		<div id="activiti-main-traceListPage" style="padding: 2px;">
            <div style="padding: 5px;">
                点击任意两条记录进行比对。
            </div>
            <table class="tracetable">
                <tr>
                    <th style="width: 5%; text-align: center;">#</th>
                    <th style="width: 10%;">OA编号</th>
                    <th style="width: 30%;">节点</th>
                    <th style="width: 25%;">修改人</th>
                    <th style="width: 30%;">修改时间</th>
                </tr>
                <c:forEach items="${traces }" var="item" varStatus="idx">
                    <script type="text/javascript">
                       traces.push('${item.trace }');
                    </script>
                    <tr onclick="javascript: compare(${idx.index + 1})" style="cursor: pointer;">
                        <td style="text-align: center;">${idx.index + 1}</td>
                        <td>${item.processid }</td>
                        <td><c:out value="${item.taskname }"/></td>
                        <td>${item.addusername }</td>
                        <td><fmt:formatDate value="${item.adddate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                </c:forEach>
            </table>
            <div id="activiti-tips-traceListPage" style="padding: 5px;"></div>
            <div id="activiti-compare-traceListPage"></div>
            <br/>
	  	</div>
	</div>
	<script type="text/javascript">
        <!--

        var formtype = '${definition.formtype }';

		$(function(){

		});

        /**
         * 差分
         * @param index
         */
        function compare(index) {

            // set compare-queue
            if (queue[queue.length - 1] == index) {
                queue.pop();
            }else if (queue[0] == index) {
                queue.shift();
            } else {
                if (queue.length == 2) {
                    queue.pop();
                }
                queue.push(index);
            }

            $('.tracetable tr:gt(0)').css({'background': '#fff'});
            $.each(queue, function(index, value){
                $('.tracetable tr:eq(' + value + ')').css({'background': '#f8e58c'});
            });

            $('#activiti-compare-traceListPage').empty();
            $('#activiti-tips-traceListPage').empty();
            if(queue.length <= 2) {

                if(formtype == 'formkey') {

                    $.each(queue, function(i, value) {

                        var html = new Array();
                        if(queue.length == 2) {
                            if(i == 0) {
                                html.push('<div id="activiti-d' + value + '-traceListPage" class="Canvas" style="width: ' + ($('#activiti-main-traceListPage').width() / 2 - 12) + 'px; float: left; margin-left: 0px;"></div>');
                            } else {
                                html.push('<div id="activiti-d' + value + '-traceListPage" class="Canvas" style="width: ' + ($('#activiti-main-traceListPage').width() / 2 - 12) + 'px; float: left; margin-left: 10px;"></div>');
                            }
                        } else {
                            html.push('<div id="activiti-d' + value + '-traceListPage" class="Canvas" style="width: ' + ($('#activiti-main-traceListPage').width() - 20) + 'px; float: left; margin-left: 0px;"></div>');
                        }

                        $('#activiti-compare-traceListPage').append(html.join(''));

                        var current = $.parseJSON(traces[value]);
                        var o = {};
                        for(var i = 0; i < current.length; i++) {

                            var obj = current[i];
                            var type = obj.type;
                            var title = obj.title;
                            var vals = obj.vals;
                            if(type == 'checkboxs' || type == 'radios') {
                                var html = new Array();
                                for(var j = 0; j < vals.length; j++) {
                                    html.push('&#91;' + vals[j].val + '&#58;' + vals[j].text + '&#93;');
                                }
                                vals = html.join(',');
                            }
                            if(type == 'input[type=text][plugins=widget]') {
                                var html = new Array();
                                if(vals.value) {

                                    html.push('&#91;' + vals.value + '&#58;' + vals.text + '&#93;');
                                } else {

                                    html.push('&#91;&#93;');
                                }
                                vals = html.join(',');
                            }
                            delete obj.agentitemid;
                            delete obj.type;
                            delete obj.title;
                            delete obj.vals;
                            delete obj.taskkey;
                            delete obj.inited;

                            obj['项目'] = title;
                            obj['内容'] = vals;
                            o[title] = vals;
                        }

                        Process(JSON.stringify(o), 'activiti-d' + value + '-traceListPage');

                        $('#activiti-tips-traceListPage').empty();
                        $('#activiti-tips-traceListPage').append('当前显示内容：' + queue.join('&nbsp;&lt;--&gt;&nbsp;') + '&nbsp;&nbsp;');

                    });

                } else if(formtype == 'business') {

                    $.each(queue, function(i, value) {

                        var html = new Array();
                        if(queue.length == 2) {
                            if(i == 0) {
                                html.push('<div id="activiti-d' + value + '-traceListPage" class="Canvas" style="width: ' + ($('#activiti-main-traceListPage').width() / 2 - 12) + 'px; float: left; margin-left: 0px;"></div>');
                            } else {
                                html.push('<div id="activiti-d' + value + '-traceListPage" class="Canvas" style="width: ' + ($('#activiti-main-traceListPage').width() / 2 - 12) + 'px; float: left; margin-left: 10px;"></div>');
                            }
                        } else {
                            html.push('<div id="activiti-d' + value + '-traceListPage" class="Canvas" style="width: ' + ($('#activiti-main-traceListPage').width() - 20) + 'px; float: left; margin-left: 0px;"></div>');
                        }

                        $('#activiti-compare-traceListPage').append(html.join(''));

                        var current = $.parseJSON(traces[value]);
//                    var o = {};
//                    for(var i = 0; i < current.length; i++) {
//
//
//                        obj['项目'] = title;
//                        obj['内容'] = vals;
//                        o[title] = vals;
//                    }

                        Process(JSON.stringify(current), 'activiti-d' + value + '-traceListPage');

                        $('#activiti-tips-traceListPage').empty();
                        $('#activiti-tips-traceListPage').append('当前显示内容：' + queue.join('&nbsp;&lt;--&gt;&nbsp;') + '&nbsp;&nbsp;');

                    });

                }
            }

            // 显示差分
            var different = false;
            if(queue.length == 2) {
                var $c1 = $('.CodeContainer:eq(0) span');
                var $c2 = $('.CodeContainer:eq(1) span');

                if($c1.length == $c2.length) {

                    var length = $c1.length;
                    for(var i = 0; i < length; i++) {

                        var t1 = $('.CodeContainer:eq(0) span:eq(' + i +')').text();
                        var t2 = $('.CodeContainer:eq(1) span:eq(' + i +')').text();

                        if(t1 != t2) {
                            $('.CodeContainer:eq(0) span:eq(' + i +')').css({'background': '#ff0'});
                            $('.CodeContainer:eq(1) span:eq(' + i +')').css({'background': '#ff0'});
                            different = true;
                        }
                    }
                    $('#activiti-tips-traceListPage').append(different ? '其中标黄色的数据为变动的数据。': '两边内容一致。')
                }
            }
        }

        -->
	</script>
  </body>
</html>
