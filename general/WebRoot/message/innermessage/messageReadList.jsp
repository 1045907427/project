<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>你有${total}条新短信消息，请注意查收</title>
	<%@include file="/include.jsp" %>
  	<script type="text/javascript" src="js/linq.min.js"></script> 	
	<link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>

  </head>
  
  <body>
  		<div class="easyui-panel" data-options="fit:true,border:false">
	  		<div class="easyui-layout" data-options="fit:true">
	  			<div title="" data-options="region:'center',border:true">
			  		<div style="margin:10px;" id="innerMessage-div-messageReadList">
			  			<c:if test="${msgList!=null}">
				  			<c:forEach var="item" items="${msgList}">
				  			<c:if test="${item!=null && item.msgContent!=null}">
					  		<div readid="${item.id }"
					  			<c:choose>
					  				<c:when test="${item.msgContent.msgtype==1 }">
					  					title="个人短信"
					  				</c:when>
					  				<c:when test="${item.msgContent.msgtype==2 }">
					  					title="公告通知"
					  				</c:when>
					  				<c:when test="${item.msgContent.msgtype==3 }">
					  					title="电子邮件"
					  				</c:when>
					  				<c:when test="${item.msgContent.msgtype==4 }">
					  					title="工作流"
					  				</c:when>
					  				<c:when test="${item.msgContent.msgtype==5 }">
					  					title="业务预警"
					  				</c:when>
					  				<c:when test="${item.msgContent.msgtype==6 }">
					  					title="传阅件"
					  				</c:when>
					  				<c:otherwise>
					  					title="新短消息"
					  				</c:otherwise>
					  			</c:choose>
					  		 class="easyui-panel" style="border:1px #CCC solid;margin-bottom: 10px;">
						  		<div style="height:30px;line-height:30px;padding:2px;text-align:left;background: #F2F2F2;border-bottom:1px #CCC solid">			
						  			<span>
						  			<c:choose>
						  				<c:when test="${item.senduserid=='system'}">系统</c:when>
						  				<c:otherwise>
						  					${item.sendusername }
						  				</c:otherwise>
						  			</c:choose>
						  			</span>&nbsp;<fmt:formatDate  value="${item.msgContent.addtime}" pattern="yyyy-MM-dd HH:mm:ss" />
						  		</div>
						  		<div style="height:auto;word-break:break-all; word-wrap:break-word;margin:2px; line-height:15px; height: 100px; overflow: auto;">
						  			${item.msgContent.content }
						  		</div>
						  		<div style="height:30px;text-align:right;margin-right:20px;">
						  			<a href="javascript:void(0);" onclick="javascript:innerMessageReadList_markMessageRead('${item.id}')">已阅</a>
						  			<c:if test="${item.msgContent.msgtype==1 or item.msgContent.msgtype==2 or item.msgContent.msgtype==3 }">
						  				<a href="javascript:void(0);" onclick="javascript:innerMessageReadList_messageReply('${item.senduserid}')">回复</a>
						  			</c:if>
						  			<c:if test="${(item.msgContent.msgtype !=1)and( not empty(item.msgContent.url))}">
						  				<a href="javascript:void(0);" onclick="javascript:innerMessageReadList_viewDetail('${item.msgContent.url }','${item.msgContent.tabtitle }');">查看详情</a>
						  			</c:if>
						  		</div>
						  		<div style="clear:both"></div>
						  		<c:if test="${item.recvflag == 1}">
						  			<input type="hidden" id="innerMessage-hid-messageReadList-remindid" value="${item.id}"/>
						  		</c:if>
					  		</div>
					  		</c:if>
					  		</c:forEach>
				  		</c:if>
			  		</div>
			  	</div>
			  	<div title="" data-options="region:'south'" style="height:35px; text-align: center;padding-top:5px; <c:if test='${total==1 }'>display:none;</c:if> ">
					<a href="javaScript:void(0);" id="innerMessage-btn-readlist-markread" class="easyui-linkbutton" data-options="plain:false">全部已阅</a>
					<a href="javaScript:void(0);" id="innerMessage-btn-readlist-more" class="easyui-linkbutton" data-options="plain:false">查看更多</a>
			  	</div>
			</div>
		</div>
		<input type="hidden" id="innerMessage-hid-messageReadList-mgwindowpageid" value="${mgwindowpageid }"/>
		<input type="hidden" id="innerMessage-hid-messageReadList-total" value="${total }"/>
		<div id="innerMessage-messageReadList-window-replyMessageOper"></div>
  	<script type="text/javascript">

		var innerMessageReadList_messageReply=function(uid){
			if(uid==null || $.trim(uid)==""){
				return false;
			}
			try{
				top.baseInnerMessageSend(uid);
			}catch(e){
			}
		};

		var innerMessageReadList_viewDetail=function(url,title){
			if(url==null || $.trim(url)==""){
				return false;
			}
			if(title==null || $.trim(title)==""){
				title="短信详情查看";
			}
			try{
				top.addTab(url,title);
			}catch(e){
			}
		};
		var innerMessageReadList_setMessageRemind=function(){
			$.ajax({
				url:'message/innerMessage/setMessageRemindFlag.do',
  	  			type:'POST',
  	  			data:'',
  	  			dataType:'json',
  	  			cache:false,
  	  			success:function(data, textStatus, jqXHR){
  	  	  			
  	  			}				
			});
		};
		var innerMessageReadList_markMessageRead=function(ids){
			if(ids==null || $.trim(ids)==""){
				return false;
			}
			$.ajax({   
	            url :'message/innerMessage/setMessageReceiveReadFlag.do',
	            data:{ids:ids},
	            type:'post',
	            dataType:'json',
	            success:function(data){
	            	if(data.flag==true){
	            		innerMessageReadList_destoryPanel(ids);
	            	}
	            }
	        });
		};
		var innerMessageReadList_destoryPanel=function(ids){
			var msgwindowpageid =$.trim($("#innerMessage-hid-messageReadList-msgwindowpageid").val()||"");
			if(msgwindowpageid==""){
				msgwindowpageid="message-innerMessage-window-messageRemindOper-content";
			}
			var $messageReadList=$("#innerMessage-div-messageReadList");
			var idarr=ids.split(",");
			$.each(idarr,function(index,item){
				try{
					if(item!=null && $.trim(item)!=""){
						var el=$messageReadList.find("div[readid='"+item+"']");
						if(el.size()>0){
							el.panel("destroy",true);
						}
					}
				}catch(e){
				}
			});
			var total=$("#innerMessage-hid-messageReadList-total").val()||0;
			if(total==idarr.length){
				var aWin=window;
				if($("#"+msgwindowpageid).size()>0){
					aWin=window;
				}else if(parent.$("#"+msgwindowpageid).size()>0){
					aWin=parent;
				}else if(top.$("#"+msgwindowpageid).size()>0){
					aWin=top;
				}
				try{	    						   							
					aWin.$("#"+msgwindowpageid).window('close');
				}catch(e){
				}
				try{
					aWin.$("#"+msgwindowpageid).dialog('close');
				}catch(e){
				}
			}
			
			var left=total-idarr.length;
			left=(left>=0)?left:-left;
			$("#innerMessage-hid-messageReadList-total").val(left);

		};
		$(document).ready(function(){
			innerMessageReadList_setMessageRemind();

			$("#innerMessage-btn-readlist-markread").click(function(){
				var idarr=new Array();
				var id="";
				var $messageReadList=$("#innerMessage-div-messageReadList");
				$messageReadList.find("div[readid]").each(function(i){
					id=$.trim($(this).attr("readid")||"");
					if(id!=""){
						idarr.push(id);
					}
				});
				if(idarr.length>0){
					innerMessageReadList_markMessageRead(idarr.join(','));
				}
			});
			
			$("#innerMessage-btn-readlist-more").click(function(){
				top.addOrUpdateTab("/message/innerMessage/messageListPage.do","内部短信");
				

				var msgwindowpageid =$.trim($("#innerMessage-hid-messageReadList-msgwindowpageid").val()||"");
				if(msgwindowpageid==""){
					msgwindowpageid="message-innerMessage-window-messageRemindOper-content";
				}
				if(top.$("#"+msgwindowpageid).size()==0){
					return false;
				}
				try{
					top.$("#"+msgwindowpageid).window('close');
				}catch(e){
				}
				try{
					top.$("#"+msgwindowpageid).dialog('close');
				}catch(e){
				}
			});
		});
  	</script>
  </body>
</html>
