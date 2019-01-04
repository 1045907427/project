<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>收件箱</title>
    <%@include file="../../common/include.jsp"%>
	<script type="text/javascript" src="phone/js/layer.js"></script>
    <meta charset="utf-8">

</head>
<body>

<div data-role="page" id="message-email-emailReceiveListPage">
	<div data-role="header" data-position="fixed">		
  		<a  href="javascript:void(0);" onclick="location.href='phone/message/showMessageIndexPage.do'" data-role="button" data-icon="back" style="box-shadow:none;border: 0px; background: #E9E9E9;">返回</a>
		<h2 style="">收件箱&nbsp;<a href="javascript:void(0);" onclick="javascript:loadEmailReceiveBoxData(true);" class="ui-btn ui-shadow ui-corner-all ui-icon-refresh ui-btn-icon-notext ui-btn-a ui-btn-inline" style="margin:0;width:18px;height:18px;">refresh</a></h2>
		<a href="#message-email-receive-unreadbox-menu" data-role="button" data-icon="bullets" data-iconpos="right" style="box-shadow:none;border: 0px; background: #E9E9E9;"  data-rel="popup" data-position-to="origin" data-transition="pop" >菜单</a>
  		<div data-role="navbar">
			<ul>
				<li><a href="phone/message/showEmailReceiveListPage.do?viewflag=1" rel="external" <c:if test="${viewflag == 1 }">class="ui-btn-active"</c:if>>未读</a></li>
				<li><a href="phone/message/showEmailReceiveListPage.do?viewflag=0" rel="external" <c:if test="${viewflag != 1 }">class="ui-btn-active"</c:if>>已读</a></li>
			</ul>
		</div>		
	</div>
    <div data-role="content">
	    <ul data-role="listview" id="message-email-emailReceiveListView">
	    <c:forEach items="${dataList }" var="item">
			<li><a href="phone/message/showEmailReceiveDetailPage.do?id=${item.id }/>">
			<h2 style="margin:0.2em 0"><c:if test="${not empty(item.addusername) }">${item.addusername }</c:if></h2>
			<p style="margin:0.2em 0"><c:if test="${not empty(item.title) }">${item.title }</c:if></p>
			<p style="margin:0.3em 0;color:#666;"><c:if test="${not empty(item.keyword) }">${item.keyword }</c:if></p>
			<p class="ui-li-aside"><strong><c:if test="${not empty(item.addtime) }"><fmt:formatDate  value="${item.addtime }" pattern="yyyy-MM-dd HH:mm:ss" /></c:if></strong></p></a>
			</li>
		</c:forEach>
		</ul>
    </div>
    <div id="message-footer-mailReceiveListPage" data-role="footer" style="text-align: center; padding: 10px;">
         <span id="message-footer-mailReceiveListPage-more" onclick="javascript:loadEmailReceiveBoxData(false);" style="cursor:pointer">点击加载更多</span>
         <span id="message-footer-mailReceiveListPage-loading" style="display:none;">加载中...</span>
         <span id="message-footer-mailReceiveListPage-nomore" style="display:none;">全部加载完毕</span>
    </div>
	<div id="message-email-receive-unreadbox-menu"  data-role="popup"  class="ui-content" style="width:85%; padding-bottom:2em;" >
		<a href="phone/message/showEmailSendPage.do" data-role="button" data-icon="mail" data-iconpos="left"  rel="external">&nbsp;&nbsp;写邮件&nbsp;&nbsp;</a>
		<a href="javascript:void(0);" data-rel="back"  onclick="javascript:loadInnerMessageReceiveBoxData(true);" data-role="button" data-icon="refresh" data-iconpos="left">刷新</a>
		<c:if test="${viewflag==1}">
			<a href="#" data-rel="back" data-role="button" data-icon="eye" data-iconpos="left" onclick="javascript:readAllUnreadEmail()">一键全部已读</a>
		</c:if>
	</div>
    <script type="text/javascript">

    var page = 1;
    var rows = 20;

    function loadEmailReceiveBoxData(isRefresh){
    	if(isRefresh){
    		page=1;
    		isRefresh=true;
    	}else{
    		isRefresh=false;
    	}
    	loadingAnimationGlobalConfig();
		$("#message-footer-mailReceiveListPage-more").hide();
		$("#message-footer-mailReceiveListPage-loading").show();
    	$.ajax({
            type: 'post',
            url: 'phone/message/getEmailReceivePageList.do',
            data: {page: page, rows: rows,viewflag:'${viewflag}'},
            dataType: 'json',
            //async: false,   /**/
            success: function(json) {

                //console.log(json)

                //$('#message-email-emailReceiveListView').listview('refresh');
            	if(isRefresh){
                	$('#message-email-emailReceiveListView').html("");
                }
				if(json.rows.length>0){
	                hideLodingFlag();
	                var htmlsb=new Array();
	                for(var i in json.rows) {
	
	                    var row = json.rows[i];
	
	                    htmlsb.push("<li>");
	                    htmlsb.push("<a href=\"phone/message/showEmailReceiveDetailPage.do?id="+row.id+"\">");
						htmlsb.push("<h2 style=\"margin:0.2em 0\">");
						if(row.addusername!=null){
							htmlsb.push(row.addusername);
						}else{
							htmlsb.push("&nbsp;");
						}
						htmlsb.push("<\/h2>");
						htmlsb.push("<p style=\"margin:0.2em 0\">");
						if(row.title!=null){
							htmlsb.push(row.title);
						}else{
							htmlsb.push("&nbsp;");
						}
						htmlsb.push("<\/p>");
						htmlsb.push("<p style=\"margin:0.3em 0;color:#666;\">");
						if(row.keyword!=null){
							htmlsb.push(row.keyword);
						}else{
							htmlsb.push("&nbsp;");
						}
						htmlsb.push("<\/p>");
						htmlsb.push("<p class=\"ui-li-aside\">");
						htmlsb.push("<strong>");
						if(row.addtime!=null){
							htmlsb.push(row.addtime);
						}else{
							htmlsb.push("&nbsp;");
						}
						htmlsb.push("<\/strong>");
	                    htmlsb.push("<\/p>");
	                    htmlsb.push("<\/a>");
	                    htmlsb.push("<\/li>");
	                }
	                if(isRefresh){
	                	$('#message-email-emailReceiveListView').html(htmlsb.join(''));
	                }else{
	                	$('#message-email-emailReceiveListView').append(htmlsb.join(''));                	
	                }
	                $('#message-email-emailReceiveListView').listview('refresh');
	                page=page+1;
				}else{
			    	$.mobile.loading('hide');
			        $("#message-footer-mailReceiveListPage-more").hide();
					$("#message-footer-mailReceiveListPage-loading").hide();
					$("#message-footer-mailReceiveListPage-nomore").show();
				}
            },
            error: function() {
            	hideLodingFlag();
            }
        });
    }
    
    function hideLodingFlag(){
    	$.mobile.loading('hide');
        $("#message-footer-mailReceiveListPage-more").show();
		$("#message-footer-mailReceiveListPage-loading").hide();
		$("#message-footer-mailReceiveListPage-nomore").hide();
    }
	function readAllUnreadEmail(){
		layer.open({
			content: '确认要将收件箱中未阅读的邮件标记为已读？',
			btn: ['确认', '取消'],
			shadeClose: false,
			yes: function(){
				try {
					androidLoading('标记中...');
					$.ajax({
						url: 'message/email/readAllEmailReceive.do',
						type: 'post',
						dataType: 'json',
						data: "",
						success: function (json) {
							androidLoaded();
							layer.open({content: '标记成功', time: 1});
							loadEmailReceiveBoxData(true);
						},
						error:function(XMLHttpRequest, textStatus, errorThrown ){
							androidLoaded();
							layer.open({content: '标记时失败，请重新再试', time: 1});
						}
					});
				}catch(ex){
					androidLoaded();
					layer.open({content: '操作时系统异常，请重新再试', time: 1});
				}
			}, no: function(){
			}
		});
	}
    $(document).ready(function(e) {
		//加载
    	loadEmailReceiveBoxData(true);
	});

</script>
</div>


</body>
</html>