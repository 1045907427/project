<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>发信箱</title>
    <%@include file="../../common/include.jsp"%>
    <meta charset="utf-8">

</head>
<body>

<div data-role="page" id="message-innermessage-innermessageSendListPage">
	<div data-role="header" data-position="fixed">		
  		<a href="javascript:void(0);" onclick="location.href='phone/message/showMessageIndexPage.do'" data-role="button" data-icon="back" style="box-shadow:none;border: 0px; background: #E9E9E9;">返回</a>
		<h2>发信箱&nbsp;<a href="javascript:void(0);" onclick="javascript:loadInnerMessageSendBoxData(true);" class="ui-btn ui-shadow ui-corner-all ui-icon-refresh ui-btn-icon-notext ui-btn-a ui-btn-inline" style="margin:0;width:18px;height:18px;">refresh</a></h2>
  		<a href="javascript:void(0);" onclick="location.href='phone/message/showInnerMessageSendPage.do'" data-role="button" data-icon="comment" data-iconpos="right" style="box-shadow:none;border: 0px; background: #E9E9E9;" rel="external">发消息</a>
	</div>
    <div data-role="content">    	
	    <ul data-role="listview" id="message-innermessage-innermessageSendListView">
	    <c:forEach items="${dataList }" var="item">
			<li><a href="phone/message/showInnerMessageDetailPage.do?id=${item.id }">
			<h2 style="margin:0.2em 0">${item.receiveusername }</h2>
			<p style="margin:0.3em 0;color:#666;">${item.content }</p>
			<p class="ui-li-aside"><strong><fmt:formatDate  value="${item.addtime }" pattern="yyyy-MM-dd HH:mm:ss" /></strong></p></a>
			</li>
		</c:forEach>
		</ul>
    </div>
    <div id="message-footer-msgSendListPage" data-role="footer" style="text-align: center; padding: 10px;">
         <span id="message-footer-msgSendListPage-more" onclick="javascript:loadInnerMessageSendBoxData(false);" style="cursor:pointer">点击加载更多</span>
         <span id="message-footer-msgSendListPage-loading" style="display:none;">加载中...</span>
         <span id="message-footer-msgSendListPage-nomore" style="display:none;">全部加载完毕</span>
    </div>    
</div>
<script type="text/javascript">

    var page = 1;
    var rows = 20;

    function loadInnerMessageSendBoxData(isRefresh){
    	if(isRefresh){
    		page=1;
    		isRefresh=true;
    	}else{
    		isRefresh=false;
    	}
    	loadingAnimationGlobalConfig();
		$("#message-footer-msgSendListPage-more").hide();
		$("#message-footer-msgSendListPage-loading").show();
    	$.ajax({
            type: 'post',
            url: 'phone/message/getInnerMessageSendPageData.do',
            data: {page: page, rows: rows},
            dataType: 'json',
            //async: false,   /**/
            success: function(json) {

                //console.log(json)

                //$('#message-innermessage-innermessageSendListView').listview('refresh');          	
		    	
                if(isRefresh){
                	$('#message-innermessage-innermessageSendListView').html("");
                }
                if(json.rows.length>0){
                	hideLodingFlag();
	                var htmlsb=new Array();
	                for(var i in json.rows) {
	
	                    var row = json.rows[i];
	
	                    htmlsb.push("<li>");
	                    htmlsb.push("<a href=\"phone/message/showInnerMessageDetailPage.do?id="+row.id+"\" rel=\"external\">");
	                    htmlsb.push("<h2 style=\"margin:0.2em 0\">");
	                    if(row.receiveusername!=null){
	                    	htmlsb.push(row.receiveusername);
	                    }else{
	                    	htmlsb.push("&nbsp;");
	                    }
	                    htmlsb.push("<\/h2>");
	                    htmlsb.push("<p style=\"margin:0.3em 0;color:#666;\">");
						if(row.content!=null){
							htmlsb.push(row.content);
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
	                	$('#message-innermessage-innermessageSendListView').html(htmlsb.join(''));
	                }else{
	                	$('#message-innermessage-innermessageSendListView').append(htmlsb.join(''));                	
	                }
	                $('#message-innermessage-innermessageSendListView').listview('refresh');

                	page=page+1;
				}else{
			    	$.mobile.loading('hide');
			        $("#message-footer-msgSendListPage-more").hide();
					$("#message-footer-msgSendListPage-loading").hide();
					$("#message-footer-msgSendListPage-nomore").show();					
				}
            },
            error: function() { 
            	hideLodingFlag();
            }
        });
    }
    
    function hideLodingFlag(){
    	$.mobile.loading('hide');
        $("#message-footer-msgSendListPage-more").show();
		$("#message-footer-msgSendListPage-loading").hide();
		$("#message-footer-msgSendListPage-nomore").hide();		
    }
    $(document).ready(function(e) {
		//加载
    	loadInnerMessageSendBoxData(true);

    });

</script>

</body>
</html>