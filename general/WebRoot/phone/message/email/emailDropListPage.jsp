<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>垃圾箱</title>
    <%@include file="../../common/include.jsp"%>
    <meta charset="utf-8">

</head>
<body>

<div data-role="page" id="message-email-emailDropListPage">
	<div data-role="header" data-position="fixed">		
  		<a href="javascript:void(0);" onclick="javascript:location.href='phone/message/showMessageIndexPage.do'" data-role="button" data-icon="back" style="box-shadow:none;border: 0px; background: #E9E9E9;">首页</a>
		<h2>垃圾箱&nbsp;<a href="javascript:void(0);" onclick="javascript:loadEmailDropBoxData(true);" class="ui-btn ui-shadow ui-corner-all ui-icon-refresh ui-btn-icon-notext ui-btn-a ui-btn-inline" style="margin:0;width:18px;height:18px;">refresh</a></h2>
  		<a href="javascript:void(0);" onclick="javascript:location.href='phone/message/showEmailSendPage.do'" data-role="button" data-icon="mail" data-iconpos="right" style="box-shadow:none;border: 0px; background: #E9E9E9;">写邮件</a>
	</div>
    <div data-role="content">    	
	    <ul data-role="listview" id="message-email-emailDropListView">
	    <c:forEach items="${dataList }" var="item">
			<li><a href="phone/message/showEmailSendDetailPage.do?id=<c:out value='${item.id }'/>">
			<h2 style="margin:0.2em 0"><c:out value="${item.recvusername }"/></h2>
			<p style="margin:0.2em 0"><c:out value="${item.title }"/></p>
			<p style="margin:0.3em 0;color:#666;"><c:out value="${item.keyword }"/></p>
			<p class="ui-li-aside"><strong><fmt:formatDate  value="${item.addtime }" pattern="yyyy-MM-dd HH:mm:ss" /></strong></p></a>
			</li>
		</c:forEach>
		</ul>
    </div>
    <div id="message-footer-mailDropListPage" data-role="footer" style="text-align: center; padding: 10px;">
         <span id="message-footer-mailDropListPage-more" onclick="javascript:loadEmailDropBoxData(false);" style="cursor:pointer">点击加载更多</span>
         <span id="message-footer-mailDropListPage-loading" style="display:none;">加载中...</span>
         <span id="message-footer-mailDropListPage-nomore" style="display:none;">全部加载完毕</span>
    </div>
    <script type="text/javascript">

    var page = 1;
    var rows = 20;

    function loadEmailDropBoxData(isRefresh){
    	if(isRefresh){
    		page=1;
    		isRefresh=true;
    	}else{
    		isRefresh=false;
    	}
    	loadingAnimationGlobalConfig();
		$("#message-footer-mailDropListPage-more").hide();
		$("#message-footer-mailDropListPage-loading").show();
    	$.ajax({
            type: 'post',
            url: 'phone/message/getEmailDropPageList.do',
            data: {page: page, rows: rows},
            dataType: 'json',
            //async: false,   /**/
            success: function(json) {

                //console.log(json)

                //$('#message-email-emailDropListView').listview('refresh');
                if(isRefresh){
                	$('#message-email-emailDropListView').html("");
                }
                if(json.rows.length>0){
                    hideLodingFlag();
	                var htmlsb=new Array();
	                for(var i in json.rows) {
	
	                    var row = json.rows[i];
	
	                    htmlsb.push("<li>");
	                    htmlsb.push("<a href=\"phone/message/showEmailSendDetailPage.do?id="+row.id+"\">");
	                    htmlsb.push("<h2 style=\"margin:0.2em 0\">"+row.recvusername+"<\/h2>");
	                    htmlsb.push("<p style=\"margin:0.2em 0\">"+row.title+"<\/p>");
	                    htmlsb.push("<p style=\"margin:0.3em 0;color:#666;\">"+row.keyword+"<\/p>");
	                    htmlsb.push("<p class=\"ui-li-aside\">");
	                    htmlsb.push("<strong>");
	                    htmlsb.push(row.addtime);
	                    htmlsb.push("<\/strong>");
	                    htmlsb.push("<\/p>");
	                    htmlsb.push("<\/a>");
	                    htmlsb.push("<\/li>");
	                }
	                if(isRefresh){
	                	$('#message-email-emailDropListView').html(htmlsb.join(''));
	                }else{
	                	$('#message-email-emailDropListView').append(htmlsb.join(''));                	
	                }
	                $('#message-email-emailDropListView').listview('refresh');

                	page=page+1;
                }else{
			    	$.mobile.loading('hide');
			        $("#message-footer-mailDropListPage-more").hide();
					$("#message-footer-mailDropListPage-loading").hide();
					$("#message-footer-mailDropListPage-nomore").show();
				}
            },
            error: function() {
            	hideLodingFlag();
            }
        });
    }
    
    function hideLodingFlag(){
    	$.mobile.loading('hide');
        $("#message-footer-mailDropListPage-more").show();
		$("#message-footer-mailDropListPage-loading").hide();
		$("#message-footer-mailDropListPage-nomore").hide();
    }
    $(document).ready(function(e) {
		//加载
    	loadEmailDropBoxData(true);

    });

</script>
</div>


</body>
</html>