<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>收信箱</title>
    <%@include file="../../common/include.jsp"%>
	<script type="text/javascript" src="phone/js/layer.js"></script>
    <meta charset="utf-8">

</head>
<body>

<div data-role="page" id="message-innermessage-innermessageReceiveListPage">
	<div data-role="header" data-position="fixed"  style="overflow:hidden;">  		
  		<a  href="javascript:void(0);" onclick="javascript:location.href='phone/message/showMessageIndexPage.do'" data-role="button" data-icon="back" style="box-shadow:none;border: 0px; background: #E9E9E9;">返回</a>
  		<h2>收信箱&nbsp;<a href="javascript:void(0);" onclick="javascript:loadInnerMessageReceiveBoxData(true);" class="ui-btn ui-shadow ui-corner-all ui-icon-refresh ui-btn-icon-notext ui-btn-a ui-btn-inline" style="margin:0;width:18px;height:18px;">refresh</a></h2>
		<a href="#message-msg-receive-unreadbox-menu" data-role="button" data-icon="bullets" data-iconpos="right" style="box-shadow:none;border: 0px; background: #E9E9E9;"  data-rel="popup" data-position-to="origin" data-transition="pop" >菜单</a>
		<div data-role="navbar">
			<ul>
				<li><a href="phone/message/showInnerMessageReceiveListPage.do?viewflag=1" rel="external" <c:if test="${viewflag == 1 }">class="ui-btn-active"</c:if>>未读</a></li>
				<li><a href="phone/message/showInnerMessageReceiveListPage.do?viewflag=0" rel="external" <c:if test="${viewflag != 1 }">class="ui-btn-active"</c:if>>已读</a></li>
			</ul>
		</div>			
	</div>
    <div data-role="content">    	
	    <ul data-role="listview" id="message-innermessage-innermessageReceiveListView">
	    <c:forEach items="${dataList }" var="item">
	    	<c:if test="${null!=item.msgContent }">
				<li><a href="phone/message/showInnerMessageDetailPage.do?id=${item.msgContent.id }<c:if test='${viewflag==1 }'>&markreceiveflag=true</c:if>">
				<h2 style="margin:0.2em 0">${item.sendusername }</h2>
				<p style="margin:0.3em 0;color:#666;">${item.msgContent.content }</p>
				<p class="ui-li-aside"><strong><fmt:formatDate  value="${item.msgContent.addtime }" pattern="yyyy-MM-dd HH:mm:ss" /></strong></p></a>
				</li>
			</c:if>
		</c:forEach>
		</ul>
    </div>
    <div id="message-footer-msgReceiveListPage" data-role="footer" style="text-align: center; padding: 10px;">
         <span id="message-footer-msgReceiveListPage-more" onclick="javascript:loadInnerMessageReceiveBoxData(false);" style="cursor:pointer">点击加载更多</span>
         <span id="message-footer-msgReceiveListPage-loading" style="display:none;">加载中...</span>
         <span id="message-footer-msgReceiveListPage-nomore" style="display:none;">全部加载完毕</span>
    </div>
	<div id="message-msg-receive-unreadbox-menu"  data-role="popup"  class="ui-content" style="width:85%; padding-bottom:2em;" >
		<a href="phone/message/showInnerMessageSendPage.do" data-role="button" data-icon="comment" data-iconpos="left" rel="external">&nbsp;&nbsp;发消息&nbsp;&nbsp;</a>
		<a href="javascript:void(0);" data-rel="back"  onclick="javascript:loadInnerMessageReceiveBoxData(true);" data-role="button" data-icon="refresh" data-iconpos="left">刷新</a>
		<c:if test="${viewflag==1}">
			<a href="#" data-rel="back" data-role="button" data-icon="eye" data-iconpos="left" onclick="javascript:readAllUnreadMessage()">一键全部已读</a>
		</c:if>
	</div>
</div>
<script type="text/javascript" defer="defer">

    var page = 1;
    var rows = 20;

    function loadInnerMessageReceiveBoxData(isRefresh){
    	if(isRefresh){
    		page=1;
    		isRefresh=true;
    	}else{
    		isRefresh=false;
    	}
    	loadingAnimationGlobalConfig();
		$("#message-footer-msgReceiveListPage-more").hide();
		$("#message-footer-msgReceiveListPage-loading").show();
    	$.ajax({
            type: 'post',
            url: 'phone/message/getInnerMessageReceivePageData.do',
            data: {page: page, rows: rows,viewflag:'${viewflag}'},
            dataType: 'json',
            //async: false,   /**/
            success: function(json) {

                //console.log(json)

                //$('#message-innermessage-innermessageReceiveListView').listview('refresh');
            	
                if(isRefresh){
                	$('#message-innermessage-innermessageReceiveListView').html("");
                }
                if(json.rows.length>0){
                	hideLodingFlag();
	                var htmlsb=new Array();
	                for(var i in json.rows) {
	
	                    var row = json.rows[i];
						if(null==row.msgContent){
							continue;
						}
	                    htmlsb.push("<li>");
	                    htmlsb.push("<a href=\"phone/message/showInnerMessageDetailPage.do?id="+row.msgid+"<c:if test='${viewflag==1 }'>&markreceiveflag=true</c:if>\" rel=\"external\">");
	                    htmlsb.push("<h2 style=\"margin:0.2em 0\">");
	                    if(row.sendusername!=null){
	                    	htmlsb.push(row.sendusername);
	                    }else{
	                    	htmlsb.push("&nbsp;");
	                    }
	                    htmlsb.push("<\/h2>");
	                    htmlsb.push("<p style=\"margin:0.3em 0;color:#666;\">");
						if(row.msgContent!=null &&　row.msgContent.content!=null){
							htmlsb.push(row.msgContent.content);
						}else{
							htmlsb.push("&nbsp;");
						}
						htmlsb.push("<\/p>");
	                    htmlsb.push("<p class=\"ui-li-aside\">");
	                    htmlsb.push("<strong>");
						if(row.msgContent!=null &&　row.msgContent.addtime!=null){
							htmlsb.push(row.msgContent.addtime);
						}else{
							htmlsb.push("&nbsp;");
						}
	                    htmlsb.push("<\/strong>");
	                    htmlsb.push("<\/p>");
	                    htmlsb.push("<\/a>");
	                    htmlsb.push("<\/li>");
	                }
	                if(isRefresh){
	                	$('#message-innermessage-innermessageReceiveListView').html(htmlsb.join(''));
	                }else{
	                	$('#message-innermessage-innermessageReceiveListView').append(htmlsb.join(''));                	
	                }
	                $('#message-innermessage-innermessageReceiveListView').listview('refresh');
	                page=page+1;
				}else{
			    	$.mobile.loading('hide');
			        $("#message-footer-msgReceiveListPage-more").hide();
					$("#message-footer-msgReceiveListPage-loading").hide();
					$("#message-footer-msgReceiveListPage-nomore").show();					
				}
            },
            error: function() {
            	hideLodingFlag();
            }
        });
    }
    
    function hideLodingFlag(){
    	$.mobile.loading('hide');
        $("#message-footer-msgReceiveListPage-more").show();
		$("#message-footer-msgReceiveListPage-loading").hide();
		$("#message-footer-msgReceiveListPage-nomore").hide();	
    }
	function readAllUnreadMessage(){
		layer.open({
			content: '确认要将收信箱中未阅读的短消息标记为已读？',
			btn: ['确认', '取消'],
			shadeClose: false,
			yes: function(){
				try {
					androidLoading('标记中...');
					$.ajax({
						url: 'message/innerMessage/readAllMessageReceive.do',
						type: 'post',
						dataType: 'json',
						data: "",
						success: function (json) {
							androidLoaded();
							layer.open({content: '标记成功', time: 1});
							loadInnerMessageReceiveBoxData(true);
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
    	loadInnerMessageReceiveBoxData(true);

    });

</script>

</body>
</html>