<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>发件箱</title>
    <%@include file="../../common/include.jsp"%>
    <meta charset="utf-8">

</head>
<body>

<div data-role="page" id="message-email-emailSendListPage">
	<div data-role="header" data-position="fixed">		
  		<a href="javascript:void(0);" onclick="javascript:location.href='phone/message/showMessageIndexPage.do';" data-role="button" data-icon="back" style="box-shadow:none;border: 0px; background: #E9E9E9;">返回</a>
		<h2>发件箱&nbsp;<a href="javascript:void(0);" onclick="javascript:loadEmailSendBoxData(true);" class="ui-btn ui-shadow ui-corner-all ui-icon-refresh ui-btn-icon-notext ui-btn-a ui-btn-inline" style="margin:0;width:18px;height:18px;">refresh</a></h2>
  		<a href="javascript:void(0);" onclick="javascript:location.href='phone/message/showEmailSendPage.do';" data-role="button" data-icon="mail" data-iconpos="right" style="box-shadow:none;border: 0px; background: #E9E9E9;" rel="external">写邮件</a>
	</div>
    <div data-role="content">    	
	    <ul data-role="listview" id="message-email-emailSendListView">
	    <c:forEach items="${dataList }" var="item">
			<li><a href="phone/message/showEmailSendDetailPage.do?id=${item.id }">
			<h2 style="margin:0.2em 0"><c:if test="${not empty(item.addusername) }">${item.addusername }</c:if></h2>
			<p style="margin:0.2em 0"><c:if test="${not empty(item.title) }">${item.title }</c:if></p>
			<p style="margin:0.3em 0;color:#666;"><c:if test="${not empty(item.keyword) }">${item.keyword }</c:if></p>
			<p class="ui-li-aside"><strong><c:if test="${not empty(item.addtime) }"><fmt:formatDate  value="${item.addtime }" pattern="yyyy-MM-dd HH:mm:ss" /></c:if></strong></p></a>
			</li>
		</c:forEach>
		</ul>
    </div>
    <div id="message-footer-mailSendListPage" data-role="footer" style="text-align: center; padding: 10px;">
         <span id="message-footer-mailSendListPage-more" onclick="javascript:loadEmailSendBoxData(false);" style="cursor:pointer">点击加载更多</span>
         <span id="message-footer-mailSendListPage-loading" style="display:none;">加载中...</span>
         <span id="message-footer-mailSendListPage-nomore" style="display:none;">全部加载完毕</span>
    </div>
    <script type="text/javascript">

    var page = 1;
    var rows = 20;

    function loadEmailSendBoxData(isRefresh){
    	if(isRefresh){
    		page=1;
    		isRefresh=true;
    	}else{
    		isRefresh=false;
    	}
    	loadingAnimationGlobalConfig();
		$("#message-footer-mailSendListPage-more").hide();
		$("#message-footer-mailSendListPage-loading").show();
    	$.ajax({
            type: 'post',
            url: 'phone/message/getEmailSendPageList.do',
            data: {page: page, rows: rows},
            dataType: 'json',
            //async: false,   /**/
            success: function(json) {

                //console.log(json)

                //$('#message-email-emailSendListView').listview('refresh');

                
                if(isRefresh){
                	$('#message-email-emailSendListView').html("");
                }
                
                if(json.rows.length>0){
                    hideLodingFlag();                	
	                var htmlsb=new Array();
	                for(var i in json.rows) {
	
	                    var row = json.rows[i];
	
	                    htmlsb.push("<li>");
	                    htmlsb.push("<a href=\"phone/message/showEmailSendDetailPage.do?id="+row.id+"\">");
	                    htmlsb.push("<h2 style=\"margin:0.2em 0\">");
	                    if(row.recvusername!=null){
	                    	htmlsb.push(row.recvusername);
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
	                	$('#message-email-emailSendListView').html(htmlsb.join(''));
	                }else{
	                	$('#message-email-emailSendListView').append(htmlsb.join(''));                	
	                }
	                $('#message-email-emailSendListView').listview('refresh');
	                page=page+1;
				}else{
			    	$.mobile.loading('hide');
			        $("#message-footer-mailSendListPage-more").hide();
					$("#message-footer-mailSendListPage-loading").hide();
					$("#message-footer-mailSendListPage-nomore").show();
				}
            },
            error: function() { 
            	hideLodingFlag();
            }
        });
    }
    
    function hideLodingFlag(){
    	$.mobile.loading('hide');
        $("#message-footer-mailSendListPage-more").show();
		$("#message-footer-mailSendListPage-loading").hide();
		$("#message-footer-mailSendListPage-nomore").hide();
    }
    $(document).ready(function(e) {
		//加载
    	loadEmailSendBoxData(true);

    });

</script>
</div>


</body>
</html>