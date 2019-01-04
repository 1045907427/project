<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>发送短消息</title>
    <%@include file="../../common/include.jsp"%>
    <meta charset="utf-8">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/phone/js/jqm/jquery.mobile-1.4.5.min.css">
</head>
<body>
<div data-role="page" id="message-innermessage-innerMessageSendPage">
	<div data-role="header" data-position="fixed">		
  		<a href="#" data-rel="back" data-role="button" data-icon="back" style="box-shadow:none;border: 0px; background: #E9E9E9;">返回</a>
		<h2>发短消息</h2>
  		<a href="#" id="message-innermessage-sendinnermessagebtn" data-role="button" data-icon="navigation" data-iconpos="right" style="box-shadow:none;border: 0px; background: #E9E9E9;">发送</a>		
	</div>
    <div data-role="content">
    	<form  action="#" id="message-form-innermessageSendPage" method="post">    		
            <div class="ui-field-contain">
			    <label>收信人：
			    	<textarea id="message-innermessage-receivers" class="required" readonly="readonly"></textarea>
			    </label>
		    </div>	    
		    <div class="ui-field-contain">
			    <label>内容:
		    		<textarea name="msgContent.content" id="message-innermessage-content" class="required"></textarea>
				</label>
			</div>
			<input type="hidden" id="message-innermessage-hidden-receivers" name="msgContent.receivers"/>
	    </form>
    </div>
	<div id="message-innermessage-innerMessageSendPage-dialog" class="ui-content" data-role="popup">
	
	       <p id="message-innermessage-innerMessageSendPage-dialog-content">Hello</p>
	
	       <a id="yes" class="ui-btn ui-corner-all ui-mini ui-btn-a" data-rel="back">Yes</a>
	
	</div>
	 <script type="text/javascript" defer="defer">
	 	$(document).ready(function(){
			$("#message-innermessage-content").css("height","8em");
	 		$("#message-innermessage-sendinnermessagebtn").off().on("click",function(){
	 			var isok=$("#message-form-innermessageSendPage").valid({
	 		        debug:true
	 		    });
	 			if(!isok){
	 				return false;
	 			}
				try {
					androidLoading('短消息发送中...');
					$.ajax({
						cache: false,
						type: "POST",
						url: "phone/message/sendInnerMessage.do",
						data: $('#message-form-innermessageSendPage').serialize(),
						dataType: "json",
						success: function (data) {
							androidLoaded();
							if (data.flag == true) {
								location.href = "phone/message/showInnerMessageSendListPage.do";
							} else {
								if (data.msg) {
									showAlertDialog("短消息发送失败，" + data.msg);
								} else {
									showAlertDialog("短消息发送失败");
								}
							}
						},
						error:function(XMLHttpRequest, textStatus, errorThrown ){
							androidLoaded();
							layer.open({content: '短消息发送失败，请重新再试', time: 1});
						}
					});
				}catch(ex){
					androidLoaded();
				}
	 		});
	 	});
	 	function showAlertDialog(msg){
	 		if(msg==null || msg==""){
	 			return false;
	 		}
	 		$("#message-innermessage-innerMessageSendPage-dialog-content").html(msg);
	 		$("#message-innermessage-innerMessageSendPage-dialog").popup( "open" );
	 	}
	 	
	 	function selectReceiveUser(data){

	 		var idArr=new Array();
	 		var nameArr=new Array();
	 		for(var i=0;i<data.length;i++){
	 			var item=data[i];
	 			if(item!=null && item.isParent== false){
	 				idArr.push(item.id);
	 				nameArr.push(item.name);
	 			}
	 		}
	 		$('#message-innermessage-hidden-receivers').val("");
	 		$('#message-innermessage-receivers').val("");
	 		if(idArr.length>0){
	 			$('#message-innermessage-hidden-receivers').val(idArr.join(','));
	 			$('#message-innermessage-receivers').val(nameArr.join(','));
	 		}
	 		$('#message-innermessage-receivers').textinput("refresh");
	 	}
	 	
	 	$(document).on('click', '#message-innermessage-receivers', function() {
            androidWidget({
                type: 'widget',
                //referwid:'RT_T_SYS_USER',
                name:'t_msg_content',
				col:'receivers',
				checkType:'2',
				onlyLeafCheck:'1',
                //onSelect: 'selectReceiveUser',
                onCheck: 'selectReceiveUser'
            });
        });
	 </script>	 

</div>
</body>
</html>