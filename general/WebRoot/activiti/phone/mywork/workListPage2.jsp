<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>我的工作</title>
    <%@include file="/phone/common/include.jsp" %>
    <script type="text/javascript">
        <%--
        var page=1;var rows=20;var total=0;var sort=true;$(function(){localStorage.setItem("url","act/phone/workListPage2.do");getList({page:1,rows:20,type:10,title:""})});function getList(g){page=g.page||page;rows=g.rows||rows;$.extend(g,{page:page,rows:rows,type:10,title:$("#activiti-title-workListPage2").val()});var e=$(this);var d=e.jqmData("theme")||$.mobile.loader.prototype.options.theme;var b=e.jqmData("msgtext")||$.mobile.loader.prototype.options.text;var c=e.jqmData("textvisible")||$.mobile.loader.prototype.options.textVisible;var f=!!e.jqmData("textonly");var a=e.jqmData("html")||"";$.mobile.loading("show",{text:b,textVisible:c,theme:d,textonly:f,html:a});$("#activiti-more-workListPage2").hide();$("#activiti-loading-workListPage2").show();$("#activiti-nomore-workListPage2").hide();$("#activiti-notexist-workListPage2").hide();$.ajax({type:"post",url:"act/getWorkList.do",data:{page:g.page,rows:g.rows,type:g.type,title:g.title,sort:sort?" asc ":" desc "},dataType:"json",success:function(k){if((g.more||"")==""){$("body")[0].scrollTop=0}$.mobile.loading("hide");if(k.rows.length==0){$("#activiti-loading-workListPage2").hide();if(total==0){$("#activiti-nomore-workListPage2").hide();$("#activiti-notexist-workListPage2").show();return true}$("#activiti-nomore-workListPage2").show();return true}var j=new Array();for(var h in k.rows){var l=k.rows[h];j.push("<li>");j.push('<a href="act/phone/workHandlePage.do?id='+l.id+"&taskid="+l.taskid+"&instanceid="+l.instanceid+'" style="padding: 2px 5px 2px 5px;" rel="external">');j.push('<span style="width: 95%; word-wrap:break-word; word-break:break-all;'+(l.preagree=="0"?"color: #f05b72":"")+'">'+l.title+"</span>");j.push("<p>流程名称："+l.definitionname+"</p><p>发起人："+l.applyusername+"</p><p>当前节点："+l.taskname+"</p>");j.push("</a>");j.push("</li>")}if(page==1){$("#activiti-worklist-workListPage2").html("")}$("#activiti-worklist-workListPage2").append(j.join(""));$("#activiti-worklist-workListPage2").listview("refresh");if(total>0){$("#activiti-worklist-workListPage2>li:gt("+(total-1)+")").hide();$("#activiti-worklist-workListPage2>li:gt("+(total-1)+")").show("fast")}$("#activiti-more-workListPage2").show("fast");$("#activiti-loading-workListPage2").hide();total=total+k.rows.length;if(total==0){$("#activiti-notexist-workListPage2").show();$("#activiti-more-workListPage2").hide();$("#activiti-loading-workListPage2").hide();$("#activiti-nomore-workListPage2").hide()}if(total==k.total){$("#activiti-more-workListPage2").hide();$("#activiti-nomore-workListPage2").show();$("#activiti-notexist-workListPage2").hide()}page++},error:function(){}})}$(document).on("pageshow","#activiti-main-workListPage2",function(){$("#activiti-worklist-workListPage2").on("filterablebeforefilter",function(f,d){var a=$(this),g=$(d.input),c=g.val(),b=new Array();if(c&&c.length>1){a.html("");if(true){a.html('<li><div class="ui-loader"><span class="ui-icon ui-icon-loading"></span></div></li>');a.listview("refresh");total=0;getList({page:1,rows:20,type:10,title:c})}}})});function converse(){var a=$("#activiti-worklist-workListPage2"),b=new Array();sort=!sort;sort?$("#activiti-sort-workListPage2").html("办理中▲"):$("#activiti-sort-workListPage2").html("办理中▼");a.html("");if(true){a.listview("refresh");total=0;getList({page:1,rows:20,type:10,title:$("#activiti-title-workListPage2").val()})}};
        --%>
        var page = 1;
        var rows = 20;
        var total = 0;
        var sort = true;

        $(function() {

            localStorage.setItem('url', 'act/phone/workListPage2.do');
            getList({
                page: 1,
                rows: 20,
                type: 10,
                title: ''
            });
        });

        /**
         * 请求list数据
         */
        function getList(o) {

            page = o.page || page;
            rows = o.rows || rows;
            $.extend(o, {page: page, rows: rows, type: 10, title: $('#activiti-title-workListPage2').val()});

            var $this = $( this );
            var theme = $this.jqmData('theme') || $.mobile.loader.prototype.options.theme;
            var msgText = $this.jqmData('msgtext') || $.mobile.loader.prototype.options.text;
            var textVisible = $this.jqmData('textvisible') || $.mobile.loader.prototype.options.textVisible;
            var textonly = !!$this.jqmData('textonly');
            var h = $this.jqmData('html') || '';

            $.mobile.loading('show', {
                text: msgText,
                textVisible: textVisible,
                theme: theme,
                textonly: textonly,
                html: h
            });

            $('#activiti-more-workListPage2').hide();
            $('#activiti-loading-workListPage2').show();
            $('#activiti-nomore-workListPage2').hide();
            $('#activiti-notexist-workListPage2').hide();
            $.ajax({
                type: 'post',
                url: 'act/getWorkList.do',
                data: {page: o.page, rows: o.rows, type: o.type, title: o.title, sort: sort ? ' asc ' : ' desc '},
                dataType: 'json',
                success: function(json) {

                    if((o.more || '') == '') {
                        $('body')[0].scrollTop = 0;
                    }

                    $.mobile.loading('hide');
                    if(json.rows.length == 0) {

                        $('#activiti-loading-workListPage2').hide();

                        if(total == 0) {

                            $('#activiti-nomore-workListPage2').hide();
                            $('#activiti-notexist-workListPage2').show();
                            return true;
                        }

                        $('#activiti-nomore-workListPage2').show();
                        return true;
                    }

                    var html = new Array();
                    for(var i in json.rows) {

                        var row = json.rows[i];
                        html.push('<li>');
                        html.push('<a href="act/phone/workHandlePage.do?id=' + row.id + '&taskid=' + row.taskid + '&instanceid=' + row.instanceid + '" style="padding: 2px 5px 2px 5px;" rel="external">');
                        html.push('<span style="width: 95%; word-wrap:break-word; word-break:break-all;' + (row.preagree == '0' ? 'color: #f05b72' : '') + '">' + row.title + '</span>');

                        html.push('<p>流程名称：' + row.definitionname + '</p><p>发起人：' + row.applyusername + '</p><p ' + (row.sign ? ' style="color: #f00;" ' : '') + '>当前节点：' + row.taskname + (row.sign ? '(会签)' : '') + '</p>');
                        html.push('</a>');
                        html.push('</li>');
                    }

                    if(page == 1) {

                        $('#activiti-worklist-workListPage2').html('');
                    }

                    $('#activiti-worklist-workListPage2').append(html.join(''));
                    $('#activiti-worklist-workListPage2').listview('refresh');

                    if(total > 0) {

                        $('#activiti-worklist-workListPage2>li:gt(' + (total - 1) + ')').hide();
                        $('#activiti-worklist-workListPage2>li:gt(' + (total - 1) + ')').show('fast');
                    }
                    $('#activiti-more-workListPage2').show('fast');
                    $('#activiti-loading-workListPage2').hide();
                    total = total + json.rows.length;

                    if(total == 0) {

                        $('#activiti-notexist-workListPage2').show();
                        $('#activiti-more-workListPage2').hide();
                        $('#activiti-loading-workListPage2').hide();
                        $('#activiti-nomore-workListPage2').hide();
                    }

                    if(total == json.total) {

                        $('#activiti-more-workListPage2').hide();
                        $('#activiti-nomore-workListPage2').show();
                        $('#activiti-notexist-workListPage2').hide();
                    }

                    page++;
                },
                error: function() { }
            });
        }

        $(document).on('pageshow', '#activiti-main-workListPage2', function() {

            $("#activiti-worklist-workListPage2").on('filterablebeforefilter', function (e, data ) {
                var $ul = $(this),
                        $input = $(data.input),
                        value = $input.val(),
                        html = new Array();

                if (value && value.length > 1 ) {

                    $ul.html('');
                    if (true) {

                        $ul.html('<li><div class="ui-loader"><span class="ui-icon ui-icon-loading"></span></div></li>');
                        $ul.listview('refresh');

                        total = 0;
                        getList({page: 1, rows: 20, type: 10, title: value});
                    }
                }
            });
        });

        /**
        * 排序反转
         */
        function converse() {

            var $ul = $('#activiti-worklist-workListPage2'),
            //        $input = $(data.input),
            //        value = $input.val(),
                    html = new Array();

            sort = !sort;

            sort ? $('#activiti-sort-workListPage2').html('办理中▲') : $('#activiti-sort-workListPage2').html('办理中▼');

            $ul.html('');
            if (true) {

                //$ul.html('<li><div class="ui-loader"><span class="ui-icon ui-icon-loading"></span></div></li>');
                $ul.listview('refresh');

                total = 0;
                getList({page: 1, rows: 20, type: 10, title: $('#activiti-title-workListPage2').val()});
            }
        }

    </script>
</head>
<body>
    <div data-role="page" id="activiti-main-workListPage2">
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1 id="activiti-sort-workListPage2" onclick="javascript:converse();">办理中▲</h1>
            <a href="javascript:location.href='act/phone/workListPage1.do';" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
            <a href="javascript:location.href=location.href;" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-right ui-icon-refresh" style="border: 0px; background: #E9E9E9;">刷新</a>
            <input id="activiti-title-workListPage2" data-type="search" placeholder="输入编号或工作标题进行查询">
        </div>
        <div role="main" class="ui-content">
            <ul data-role="listview" data-inset="false" id="activiti-worklist-workListPage2" data-filter="true" data-input="#activiti-title-workListPage2">
                <%-- 初次不加载数据，通过ajax加载数据 --%>
            </ul>
        </div>
        <div id="activiti-footer-workListPage2" data-role="footer" style="text-align: center; padding: 10px;">
            <span id="activiti-more-workListPage2" onclick="javascript:getList({more:1});">加载更多</span>
            <span id="activiti-loading-workListPage2" class="nodisplay">加载中...</span>
            <span id="activiti-nomore-workListPage2" class="nodisplay">没有更多</span>
            <span id="activiti-notexist-workListPage2" class="nodisplay">没有可以处理的工作</span>
        </div>
    </div>
</body>
</html>
