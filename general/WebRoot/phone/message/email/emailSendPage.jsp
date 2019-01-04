<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>发送邮件</title>
    <%@include file="../../common/include.jsp"%>
    <meta charset="utf-8">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/phone/js/jqm/jquery.mobile-1.4.5.min.css">
</head>
<body>
<div data-role="page" id="message-email-emailSendPage">
	<div data-role="header" data-position="fixed">		
  		<a href="#" data-rel="back" data-role="button" data-icon="back" style="box-shadow:none;border: 0px; background: #E9E9E9;">返回</a>
		<h2>写邮件</h2>
  		<a href="#" id="message-email-sendemailbtn" data-role="button" data-icon="navigation" data-iconpos="right" style="box-shadow:none;border: 0px; background: #E9E9E9;">发送</a>		
	</div>
    <div data-role="content">
    	<form  action="" id="message-form-emailSendPage" method="post">    		
            <div class="ui-field-contain">
			    <label>收件人：
		    		<textarea id="message-email-receiveuser" class="required" readonly="readonly"></textarea>
			    </label>
		    </div>
		    <%--
		    <div class="ui-field-contain">   	
			    <label>抄送：
			    	<input type="text" id="message-email-copytouser" readonly="readonly" />
			    </label>
		   	</div>
		   	 --%>		   	
		    <div class="ui-field-contain">
			    <label>主题：
			    	<input type="text" id="message-email-title" name="emailContent.title" class="required" />
			    </label>
		    </div>		    
		    <div class="ui-field-contain">
			    <label>内容:
		    		<textarea name="emailContent.content" id="message-email-content" class="required"></textarea>
				</label>
			</div>
			<div id="message-eamil-attach-list-div" class="ui-field-contain" style="display:none">
				<ui id="message-eamil-attach-list" data-role="listview" data-inset="true" data-split-icon="delete" >
					<li data-role="list-divider">附件</li>
				</ui>
			</div>
			<input type="hidden" id="message-email-hidden-receiveuser" name="emailContent.receiveuser" />
			<input type="hidden" id="message-email-hidden-copytouser" name="emailContent.copytouser" />
			<input type="hidden" id="message-email-hidden-attachment-add" name="emailContent.attach" />
			<input type="hidden" id="message-email-hiiden-attachment-delete"  name="attachdelete" />
	    </form>
    </div>
	<div data-role="footer" data-position="fixed">
		<a href="#" id="message-email-addattach" data-role="button" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-tag">添加附件</a>
	</div>
	<div id="message-email-attach-delete-askdialog" data-role="popup"  class="ui-content" style="max-width:340px; padding-bottom:2em;">

		<h3>是否删除该文件？</h3>
		<p>删除文件后将无法恢复</p>

		<a href="#" data-rel="back" class="ui-shadow ui-btn ui-corner-all ui-btn-b ui-icon-delete ui-btn-icon-left ui-btn-inline ui-mini" onclick="javascript:deleteAttach();">删除</a>
		<a href="#" data-rel="back" class="ui-shadow ui-btn ui-corner-all ui-btn-inline ui-mini" onclick="javascript:return true;">取消</a>
	
	</div>
	 <script type="text/javascript">
		 var upAttachIdArr=new Array();
		 var upAttachDeleteArr=new Array();
		 var wantDeleteId="";
	 	$(document).ready(function(){
	 		$("#message-email-content").css("height","8em");
	 		$("#message-email-sendemailbtn").off().on("click",function(){
	 			var isok=$("#message-form-emailSendPage").valid({
	 		        debug:true
	 		    });
	 			if(!isok){
	 				return false;
	 			}
				try {
					androidLoading('邮件发送中...');
					$.ajax({
						cache: false,
						type: "POST",
						url: "phone/message/addEmailSend.do",
						data: $('#message-form-emailSendPage').serialize(),
						dataType: "json",
						success: function (data) {
							androidLoaded();
							if (data.flag == true) {
								location.href = "phone/message/showEmailSendListPage.do";
							} else {
								if (data.msg) {
									alertMsg("邮件发送失败，" + data.msg);
								} else {
									alertMsg("邮件发送失败");
								}
							}
						},
						error:function(XMLHttpRequest, textStatus, errorThrown ){
							androidLoaded();
							layer.open({content: '邮件发送失败，请重新再试', time: 1});
						}
					});
				}catch(ex){
					androidLoaded();
				}
	 		});
			$('#message-email-addattach').off().on('click', function(e) {
				androidUpload('addAttachHandle');
			});
	 	});
		 function addAttachHandle(json){

			 if(json==null || json.id==null ||json.id==""){
				 alertMsg("文件上传失败，请重新再试");
			 }
			 upAttachIdArr.push(json.id);
			 $("#message-email-hidden-attachment-add").val(upAttachIdArr.join(','));
			 var htmlsb=new Array();
			 htmlsb.push("<li class=\"myupfile\" myupattachfileid='"+json.id+"' >");
			 htmlsb.push("<a href=\"phone/message/showMessageAttachViewPage.do?id="+json.id+"\">"+json.oldFileName+"</a>");
			 htmlsb.push("<a href=\"#message-email-attach-delete-askdialog\" data-rel=\"popup\" data-position-to=\"window\" data-transition=\"pop\" onclick=\"javascript:wantToDeleteAttach('"+json.id+"')\">delete</a>");
			 htmlsb.push("</li>");
			 $("#message-eamil-attach-list").append(htmlsb.join(''));
			 $("#message-eamil-attach-list").listview('refresh');
			 $("#message-eamil-attach-list-div").show();
		 }

		 function deleteAttach(){
			if(wantDeleteId==null || wantDeleteId==""){
				$("li[myupattachfileid='']").remove();
				return false;
			}
			if(upAttachIdArr.length>0){
				var index=upAttachIdArr.indexOf(wantDeleteId);
				if(index>=0){
					upAttachIdArr.splice(index,1);
					upAttachDeleteArr.push(wantDeleteId);
				}
			}
			 $("li[myupattachfileid='"+wantDeleteId+"']").remove();

			 $("#message-eamil-attach-list").listview('refresh');
			 if(upAttachIdArr==0){
				 $("#message-eamil-attach-list-div").hide();
			 }

			 $("#message-email-hidden-attachment-add").val(upAttachIdArr.join(','));
			 $("#message-email-hidden-attachment-delete").val(upAttachDeleteArr.join(','));
			return true;
		 }
		 function wantToDeleteAttach(id){
			 if(id==null || id==""){
				 id="";
			 }
			 wantDeleteId=id;
		 }
	 	
	 	function selectReceiveUser(data){	 		
	 		var idArr=new Array();
	 		var nameArr=new Array();
	 		for(var i=0;i < data.length;i++){
	 			var item=data[i];
	 			if(item!=null && item.isParent==false){
	 				idArr.push(item.id);
	 				nameArr.push(item.name);
	 			}
	 		}
	 		$('#message-email-hidden-receiveuser').val("");
	 		$('#message-email-receiveuser').val("");
	 		if(idArr.length>0){
	 			$('#message-email-hidden-receiveuser').val(idArr.join(','));
	 			$('#message-email-receiveuser').val(nameArr.join(','));
	 		}
	 		$('#message-email-receiveuser').textinput("refresh");
	 	}
	 	function selectCopytoUser(data){	 		
	 		var idArr=new Array();
	 		var nameArr=new Array();
	 		for(var i=0;i<data.length;i++){
	 			var item=data[i];
	 			if(item!=null && item.isParent=='false'){
	 				idArr.push(item.id);
	 				nameArr.push(item.id+":"+item.name);
	 			}
	 		}
	 		$('#message-email-hidden-copytouser').val("");
	 		$('#message-email-copytouser').val("");
	 		if(idArr.length>0){
	 			$('#message-email-hidden-copytouser').val(idArr.join(','));
	 			$('#message-email-copytouser').val(nameArr.join(','));
	 		}
	 	}
	 	
	 	$(document).on('click', '#message-email-receiveuser', function() {

            androidWidget({
                type: 'widget',
                name:'t_msg_emailcontent',
				col:'receiveuser',
				onCheck: 'selectReceiveUser',
				checkType:'2',
				onlyLeafCheck:'1'
            });
        });
	 	<%--
	 	$(document).on('click', '#message-email-copytouser', function() {

            androidWidget({
                type: 'widget',
                name:'t_msg_emailcontent',
				col:'copytouser',
                onCheck: 'selectCopytoUser'
            });
        }); --%>
	 </script>	 

</div>
</body>
</html>