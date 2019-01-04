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

<div data-role="page" id="message-notice-noticeListPage">
	<div data-role="header" data-position="fixed">		
  		<a href="javascript:void(0);" onclick="location.href='phone/message/showMessageIndexPage.do'" data-role="button" data-icon="back" style="box-shadow:none;border: 0px; background: #E9E9E9;" >返回</a>
		<h2>公告</h2>
  		<a href="javascript:void(0);" id="message-notice-refreshNoticeList" data-role="button" data-icon="refresh" data-iconpos="right" style="box-shadow:none;border: 0px; background: #E9E9E9;">刷新</a>
  		<div data-role="navbar">
			<ul>
				<li><a href="phone/message/showNoticeListPage.do?viewflag=1" rel="external" <c:if test="${viewflag == 1 }">class="ui-btn-active"</c:if>>未读</a></li>
				<li><a href="phone/message/showNoticeListPage.do?viewflag=0" rel="external" <c:if test="${viewflag != 1 }">class="ui-btn-active"</c:if>>已读</a></li>
			</ul>
		</div>	
	</div>
    <div data-role="content">    	
	    <ul data-role="listview" id="message-notice-noticeListView">
	    <c:forEach items="${dataList }" var="item">
			<li><a href="phone/message/showNoticeDetailPage.do?id=${item.id }<c:if test='${viewflag==1 }'>&markreceiveflag=true</c:if>">
			<h2 style="margin:0.2em 0">${item.title }</h2>
			<p style="margin:0.2em 0">${item.publishdeptname } &nbsp; ${item.publishername }</p>
			<p style="margin:0.3em 0;color:#666;">${item.content }</p>
			<p class="ui-li-aside"><c:if test="${not empty(item.publishtime)}"> <fmt:formatDate  value="${item.publishtime }" pattern="yyyy-MM-dd HH:mm:ss" /> </c:if></strong></p></a>
			</li>
		</c:forEach>
		</ul>
    </div>
    <div id="message-footer-noticeListPage" data-role="footer" style="text-align: center; padding: 10px;">
         <span id="message-footer-noticeListPage-more" onclick="javascript:loadNoticeBoxData(false);" style="cursor:pointer">点击加载更多</span>
         <span id="message-footer-noticeListPage-loading" style="display:none;">加载中...</span>
         <span id="message-footer-noticeListPage-nomore" style="display:none;">全部加载完毕</span>
    </div>
</div>
<script type="text/javascript">

    var page = 1;
    var rows = 20;

    function loadNoticeBoxData(isRefresh){
    	if(isRefresh){
    		page=1;
    		isRefresh=true;
    	}else{
    		isRefresh=false;
    	}
    	loadingAnimationGlobalConfig();
		$("#message-footer-noticeListListPage-more").hide();
		$("#message-footer-noticeListListPage-loading").show();
    	$.ajax({
            type: 'post',
            url: 'phone/message/getNoticePageData.do',
            data: {page: page, rows: rows,viewflag:'${viewflag}'},
            dataType: 'json',
            //async: false,   /**/
            success: function(json) {

                //console.log(json)

                //$('#message-notice-noticeListView').listview('refresh');
                if(isRefresh){
                	$('#message-notice-noticeListView').html("");
                }

                if(json.rows.length>0){
                	hideLodingFlag();
	                var htmlsb=new Array();
	                for(var i in json.rows) {
	
	                    var row = json.rows[i];
	
	                    htmlsb.push("<li>");
	                    htmlsb.push("<a href=\"phone/message/showNoticeDetailPage.do?id="+row.id+"<c:if test='${viewflag==1 }'>&markreceiveflag=true</c:if>\">");
	                    htmlsb.push("<h2 style=\"margin:0.2em 0\">"+row.title+"<\/h2>");
	                    htmlsb.push("<p style=\"margin:0.2em 0;\">");
	                    htmlsb.push(row.adddeptname!=null?row.adddeptname+"&nbsp":"");
	                    htmlsb.push(row.addusername!=null?row.addusername+"&nbsp":"" );
	                    htmlsb.push("<\/p>");
	                    htmlsb.push("<p style=\"margin:0.3em 0;color:#666;\">");
						htmlsb.push(row.content!=null?row.content+"&nbsp":"" );
						htmlsb.push("<\/p>");
	                    htmlsb.push("<p class=\"ui-li-aside\">");
	                    htmlsb.push("<strong>");
						htmlsb.push(row.addtime!=null?row.addtime+"&nbsp":"" );
	                    htmlsb.push("<\/strong>");
	                    htmlsb.push("<\/p>");
	                    htmlsb.push("<\/a>");
	                    htmlsb.push("<\/li>");
	                }
	                if(isRefresh){
	                	$('#message-notice-noticeListView').html(htmlsb.join(''));
	                }else{
	                	$('#message-notice-noticeListView').append(htmlsb.join(''));                	
	                }
	                $('#message-notice-noticeListView').listview('refresh');

                	page=page+1;
				}else{
			    	$.mobile.loading('hide');
			        $("#message-footer-noticeListPage-more").hide();
					$("#message-footer-noticeListPage-loading").hide();
					$("#message-footer-noticeListPage-nomore").show();
				}
            },
            error: function() {
            	hideLodingFlag();
            }
        });
    }
    
    function hideLodingFlag(){
    	$.mobile.loading('hide');
        $("#message-footer-noticeListPage-more").show();
		$("#message-footer-noticeListPage-loading").hide();
		$("#message-footer-noticeListPage-nomore").hide();
    }
    $(document).ready(function(e) {
		//加载
		setTimeout(function(){
    		loadNoticeBoxData(false);
		},10);

		$("#message-notice-refreshNoticeList").on("tap",function(){
			loadNoticeBoxData(true);
		});
    });

</script>

</body>
</html>