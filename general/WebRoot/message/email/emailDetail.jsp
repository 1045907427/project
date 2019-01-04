<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>邮件详细</title>
	<%@include file="/include.jsp" %>
	<link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>
  </head>
  
  <body>  
	<c:choose>
 		<c:when test="${showdata=='1'}">
		  	<div class="easyui-layout" data-options="fit:true" style="height:100%;">
				<div <c:if test='${showtitle==1} '> title="邮件详细"</c:if> data-options="region:'center'" style="height:auto">
					<div style="padding:5px;">
						<div style="border-bottom: 1px #CCC solid; background: #F6F6F6;">
							<div style="line-height:26px;">
							<div style="float:left;width:80px;line-height:22px;padding-left:5px;">主　题：</div>
								<div style="">
				    				${emailContent.title }&nbsp;
				    				<c:if test="${emailContent.importantflag==1}">
				    					<span style="color:#f00;">重要</span>
				    				</c:if>
				    				<c:if test="${emailContent.importantflag==2}">
				    					<span style="color:#f00;font-weighgt:bold;">非常重要</span>
				    				</c:if>
				    			</div>
								<div style="clear:both"></div>
							</div>
							<c:if test="${userviewtype=='recv'}">
								<div style="clear:both;height:30px;line-height:26px;">
									<div style="float:left;width:80px;line-height:22px;padding-left:5px;">发件人：</div>
									<div style="float:left">
										<span style="text-decoration: underline; padding-right: 3px; color: #898CA4;">
					    					${emailContent.addusername }
					    				</span>
					    			</div>
					    			<div style="float:left;padding-left:5px;">
					    				<a href="javaScript:void(0);" onclick="javascript:emailDetail_replyInnerSms('${emailContent.adduserid}')">回短信</a>
					    			</div>
									<div style="clear:both"></div>
								</div>
							</c:if>
							<div style="height:30px;line-height:26px;">
								<div style="float:left;width:80px;line-height:22px;padding-left:5px;">收件人：</div>
								<div id="messageEmail-emailDetail-div-recvusername" style="float:left" myoperdata="${emailContent.recvusername }" >				    				
				    			</div>
				    			<div id="messageEmail-emailDetail-div-showAllReceiver" style="float:left;padding-left:5px; display:none;">
				    				<a href="javaScript:void(0);" onclick="javascript:emailDetail_showAllReceiver();">全部名单</a>
				    			</div>
				    			<div style="float:left;padding-left:5px;">
				    				<a href="javaScript:void(0);" id="messageEmail-emailDetail-Statelist">查看邮件状态</a>
				    			</div>
								<div style="clear:both"></div>
							</div>
							<div style="clear:both;height:30px;line-height:26px;">
								<div style="float:left;width:80px;line-height:22px;padding-left:5px;">时　间：</div>
								<div style="float:left">
				    				<fmt:formatDate value="${emailContent.addtime}" pattern="yyyy年MM月dd日 E" />
				    			</div>
								<div style="clear:both"></div>
							</div>
							<c:if test="${not empty(emailContent.attach) }">				
								<div style="height:30px;line-height:26px;">
									<div style="float:left;width:80px;line-height:22px;padding-left:5px;">附　件：</div>
									<div style="float:left">
					    				<a href="javaScript:void(0);" onclick="$.webuploader.showAttachListDialog('${emailContent.attach }')" id="messageEmail-emailDetail-btn-viewattach" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">点击查看</a>
					    			</div>
									<div style="clear:both"></div>
								</div>
							</c:if>	
						</div>
						<div style="padding:5px;">
							${emailContent.content }
						</div>
					</div>
				</div>
				<c:if test="${showoper==1 }">
					<div title="" data-options="region:'south'" border="false" style="text-align:center;height:35px;">
						<div style="padding: 5px; ">		
							<c:if test="${showreturn==1}">
								<a href="javaScript:void(0);" id="messageEmail-emailDetail-btn-returnToList" class="easyui-linkbutton" >返回</a>		
							</c:if>
						</div>
					</div>
				</c:if>	
				<div id="messageEmail-emailDetail-dialog" ></div>
				<div id="messageEmail-innerMessage-window-sendMessageOper" ></div>
		</div>	
	</c:when>
	<c:otherwise>	
		<div title="邮件详细" class="easyui-panel"  data-options="fit:true"  style="height:100%">
			<div style="width:300px;height:98px;margin:100px auto 0; border:1px solid #9F9F9F;line-height:80px;">
				<div style="float:left; padding:10px;" class="img-extend-infow64">						
				</div>
				<div style="float:left;font-size: 16px;color:#6BAD42;">抱歉，未能找到想关邮件信息</div>
			</div>
		</div> 
	</c:otherwise>
	</c:choose>	
			    	
	<script type="text/javascript">		
			
		<c:if test="${userviewtype == 'recv'}">
					var emailDetail_sendReceiptAfReadEmail=function(id){
						try{
				  	  	 	if(id!=null && id!="" ){
					  	 		$.ajax({   
						            url :'message/email/sendReceiptAfReadEmail.do',
						            type:'post',
						            dataType:'json',
						            data:{emailid:id},
						            success:function(json){
						            }
					        	});
				  	  	 	}
						}catch(e){
						}
					}
					var emailDetail_readEmail=function(id){
						try{
				  	  	 	if(id!=null && id!="" ){
					  	 		$.ajax({   
						            url :'message/email/readEmailReceiveByEmailid.do',
						            type:'post',
						            dataType:'json',
						            data:{emailids:id},
						            success:function(json){
						            	emailDetail_sendReceiptAfReadEmail(id);
						            }
					        	});
				  	  	 	}
						}catch(e){
						}
					}
					$(document).ready(function(){
						emailDetail_readEmail('${emailContent.id}');
					});
		</c:if>
			
		<c:if test="${showoper==1 }">
			$(document).ready(function(){
				$("#messageEmail-emailDetail-btn-returnToList").click(function(){
					try{
						messageEmail_showBackEmailList(1);
					}catch(e){
					}
				});
			});
		</c:if>	
		<c:if test="${showdata=='1'}">
			var emailDetail_listRecvuser=function(){
				var recvname=$("#messageEmail-emailDetail-div-recvusername").attr("myoperdata") ||"";
				if(recvname!=""){
					var namearr=recvname.split(",");
					var data=new Array();
					$.each(namearr,function(i,item){
						if(i<=10){
							data.push("<span style=\"text-decoration: underline; padding-right: 3px; color: #898CA4;\">");
							data.push(item);
							data.push("</span>");
						}else{
							$("#messageEmail-emailDetail-div-showAllReceiver").show();
							return false;
						}
					});
					$("#messageEmail-emailDetail-div-recvusername").html(data.join(''));
				}
			}
			var emailDetail_showAllReceiver=function(){
				var recvname=$("#messageEmail-emailDetail-div-recvusername").attr("myoperdata") ||"";
				if(recvname!=""){
					alert(recvname);
				}
			};
			var emailDetail_replyInnerSms=function(userids){
				if(userids==null || $.trim(userids)==""){
					return false;
				}
				try{
					$('<div id="messageEmail-innerMessage-window-sendMessageOper-content"></div>').appendTo("#messageEmail-innerMessage-window-sendMessageOper");
					var $messageSendOper=$("#messageEmail-innerMessage-window-sendMessageOper-content");
					var url="message/innerMessage/messageReplyPage.do?touserids="+userids+"&msgsendpageid=messageEmail-innerMessage-window-sendMessageOper-content";
					var iframe= $('<iframe></iframe>').attr('height', '100%').attr('width', '100%').attr('marginheight', '0').attr('marginwidth', '0').attr('frameborder','0');
					setTimeout(function(){  
		                iframe.attr('src', url);  
		            }, 1);
					$messageSendOper.window({
						title:'内部短信发送',
					    width: 700,  
					    height: 350,
			            top:($(window).height() - 350) * 0.5, 
			            left:($(window).width() - 700) * 0.5, 
					    closed: true,  
					    cache: false, 
						content : iframe,
						minimizable:false,
					    modal: true,  
					    onClose:function(){
					    	$('#messageEmail-innerMessage-window-sendMessageOper-content').window("destroy");
					    }
					});
					$messageSendOper.window("open");
				}catch(e){
				}
			};
			$(document).ready(function(){
				emailDetail_listRecvuser();
				$("#messageEmail-emailDetail-Statelist").click(function(){
					try{
						var id=$.trim('${emailContent.id}');
						if(!isNaN(id)){
	                        $('<div id="messageEmail-emailDetail-dialog-content"></div>').appendTo("#messageEmail-emailDetail-dialog");
	
	                        $("#messageEmail-emailDetail-dialog-content").dialog({
	                            title: '查看邮件状态',
	                            width: 500,
	                            height: 450,
	                            closed: true,
	                            cache: false,
	                            href: 'message/email/emailSendBoxUserListPage.do?emailid='+id,
	                            modal: true,
	                            onClose:function(){
	                                $('#messageEmail-emailDetail-dialog-content').dialog("destroy");
	                            }
	                        });
	                        $("#messageEmail-emailDetail-dialog-content").dialog("open");
						}
					}catch(e){
					}
	
				});
			});
		</c:if>
	</script>	
  </body>
</html>
