<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>我的工作</title>
    <%@include file="/phone/common/include.jsp"%>
    <style type="text/css">
        .ui-filter-inset {
            margin-top: 0;
        }
    </style>
    <script type="text/javascript">

        localStorage.setItem("url","act/phone/workQueryPage.do");var page=1;var requestData=function(){var a=$("#activiti-listview-workQueryPage");var d=$("#activiti-title-workQueryPage");var c=d.val();var b=new Array();a.listview("refresh");$.ajax({url:"act/getWorkQueryList.do",dataType:"json",data:{title:$("#activiti-title-workQueryPage").val(),rows:20,page:page}}).then(function(e){$("#activiti-more-workQueryPage").parent().remove();$.each(e.rows,function(f,g){b.push("<li>");b.push('<a href="act/phone/workViewPage.do?id='+g.id+'" style="padding: 2px 5px 2px 5px;" rel="external">');b.push('<span style="width: 95%; word-wrap:break-word; word-break:break-all;">'+g.title+"</span>");b.push("<p>流程名称："+g.definitionname+"</p><p>发起人："+g.applyusername+"</p><p>当前节点："+(g.taskname+("("+(g.assigneename||"")+")")||'<span style="color: #009933">已结束</span>')+"</p>");b.push("</a>");b.push("</li>")});b.push('<li><div id="activiti-more-workQueryPage" style="width: 100%; text-align: center; color: #F00;">正在加载...</div></li>');a.append(b.join(""));a.listview("refresh");a.trigger("updatelayout");$("ul > li").last().removeClass("ui-screen-hidden");if($("ul > li").length<e.total+1){$(window).on("scroll",function(f){$("#activiti-more-workQueryPage").each(function(){var g=$(window).height()+$(window).scrollTop();if(g>=$(this).offset().top+10){$(this).trigger("appear");$("#activiti-more-workQueryPage").off()}})});$("#activiti-more-workQueryPage").off().on("appear",function(){$(window).off("scroll");setTimeout(function(){page++;requestData()},1000)})}else{$("#activiti-more-workQueryPage").parent().remove()}})};$(document).on("pageshow","#activiti-main-workQueryPage",function(){$("#activiti-listview-workQueryPage").on("filterablebeforefilter",function(c,b){page=1;var a=$("#activiti-listview-workQueryPage");a.html("");requestData()});~(function(){page=1;var a=$("#activiti-listview-workQueryPage");a.html("");requestData()})()});
        <%--
        localStorage.setItem('url', 'act/phone/workQueryPage.do');
        var page = 1;

        var requestData = function () {

            var $ul = $("#activiti-listview-workQueryPage");
            var $input = $('#activiti-title-workQueryPage');
            var value = $input.val();
            var html = new Array();

            $ul.listview('refresh');

            $.ajax({
                url: 'act/getWorkQueryList.do',
                dataType: 'json',
                data: {title: $('#activiti-title-workQueryPage').val(), rows: 20, page: page}
            })
                .then(function(response) {

                    $("#activiti-more-workQueryPage").parent().remove();
                    $.each(response.rows, function (i, row) {

                        html.push('<li>');
                        html.push('<a href="act/phone/workViewPage.do?id=' + row.id + '" style="padding: 2px 5px 2px 5px;" rel="external">');
                        html.push('<span style="width: 95%; word-wrap:break-word; word-break:break-all;">' + row.title + '</span>');
                        html.push('<p>流程名称：' + row.definitionname + '</p><p>发起人：' + row.applyusername + '</p><p>当前节点：' + (row.taskname + ('(' + (row.assigneename || '') + ')') || '<span style="color: #009933">已结束</span>') + '</p>');
                        html.push('</a>');
                        html.push('</li>');
                    });

                    html.push('<li><div id="activiti-more-workQueryPage" style="width: 100%; text-align: center; color: #F00;">正在加载...</div></li>');

                    $ul.append(html.join(''));
                    $ul.listview('refresh');
                    $ul.trigger('updatelayout');

                    $('ul > li').last().removeClass('ui-screen-hidden');

                    if($('ul > li').length < response.total + 1) {

                        $(window).on("scroll", function(event){
                            $("#activiti-more-workQueryPage").each(function(){
                                var fold = $(window).height() + $(window).scrollTop();
                                if( fold >= $(this).offset().top + 10){
                                    $(this).trigger('appear');
                                    $("#activiti-more-workQueryPage").off();
                                }
                            });
                        });

                        $("#activiti-more-workQueryPage").off().on('appear', function(){
                            $(window).off('scroll');
                            setTimeout(function () {
                                page ++;
                                requestData();
                            }, 1000)
                        });

                    } else {
                        $("#activiti-more-workQueryPage").parent().remove();
                    }

                });
        };

        $(document).on('pageshow', '#activiti-main-workQueryPage', function() {

            $("#activiti-listview-workQueryPage").on('filterablebeforefilter', function (e, data ) {

                page = 1;
                var $ul = $("#activiti-listview-workQueryPage");
                $ul.html('');
                requestData();
            });

            ~(function(){
                page = 1;
                var $ul = $("#activiti-listview-workQueryPage");
                $ul.html('');
                requestData();
            })();
        });
        --%>

    </script>
</head>
<body>
    <div data-role="page" id="activiti-main-workQueryPage">
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1>工作查询</h1>
            <a href="javascript:location.href='act/phone/workListPage1.do';" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
            <a href="javascript:location.href=location.href;" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-right ui-icon-refresh" style="border: 0px; background: #E9E9E9;">刷新</a>
            <input id="activiti-title-workQueryPage" data-type="search" placeholder="输入编号或工作标题进行查询">
        </div>
        <div role="main" class="ui-content">
            <ul id="activiti-listview-workQueryPage" data-role="listview" data-inset="false" data-filter="true" data-input="#activiti-title-workQueryPage">
            </ul>
        </div>
    </div>
</body>
</html>
