<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>内部短信发送</title>    
	<%@include file="/include.jsp" %> 	
  	<script type="text/javascript" src="js/kindeditor/kindeditor-min.js"></script> 
  	<script type="text/javascript" src="js/kindeditor/lang/zh_CN.js"></script>
	<link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>
  	<script type="text/javascript" src="js/colorPicker/jquery.colorPicker.min.js"></script>
  	<link rel="stylesheet" href="js/colorPicker/colorPicker.css" type="text/css" />

  </head>
  
  <body>
  	<div>
	  <div title="发送内部短信" >
	    <form action="message/innerMessage/messageSend.do" method="post" id="innerMessage-form-messageSend">
	    	<div class="pageContent">
	    		<div style="height:auto;margin-top:10px;margin-bottom: 5px;">
					<div style="float:left;width:80px;line-height:22px;">收信人：</div>						
					<div style="float:left;">
						<input id="innerMessage-form-messageSend-receivers"  name="msgContent.receivers" value="${touserids }" />
	    			</div>
	    			<div style="clear:both"></div>
				</div>
	    		<div style="height:auto;margin-bottom: 5px;">
	    			<div style="float:left;width:80px;line-height:22px;">内容：</div>
	    			<div style="float:left;">
		    			<textarea id="innerMessage-form-messageSend-content" name="msgContent.content" rows="0" cols="0" style="width:500px;height:200px"></textarea>
		    			<input type="hidden" id="innerMessage-form-messageSend-title" name="msgContent.title" maxlength="100"/>
	    			</div>
	    			<div style="clear:both"></div>
	    		</div>
	    		<c:if test="${msgtype!='reply' }">
		    		<div style="height:auto;display:none;">
		    			<div style="float:left;width:80px;line-height:22px;">发送时间：</div>
		    			<div style="float:left;">
		    				<input type="text" name="msgContent.clocktime" style="width:130px;" readonly="readonly" onClick="WdatePicker({minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />为空则即时发送
		    			</div>
		    			<div style="clear:both"></div>
		    		</div>
	    		</c:if>
	    		<div style="width:auto;height:auto;margin-top: 5px;text-align:center;">	    			
	    			<div style="width:120px;margin:auto"><a href="javaScript:void(0);" id="innerMessage-messageSend-btn-saveMessage" class="easyui-linkbutton" data-options="plain:false,iconCls:'icon-extend-sendMail'">发送短信</a></div>
	    		</div>
	    	</div>
    	</form>
		</div>
		<div id="User-window-userChooser" class="easyui-dialog" closed="true"></div>
		<input type="hidden" id="innerMessage-form-messageSend-msgSendPageId" value="${msgSendPageId }"/>
 	</div>
 	<script type="text/javascript">
 		$(document).ready(function(){
 	 			var innerMessage_Form_MessageSend_Editor=KindEditor.create('#innerMessage-form-messageSend-content',{
				allowPreviewEmoticons: false,
				allowFileManager: false,
				resizeType: 0,
				items:['fontname','fontsize','forecolor','hilitecolor','bold','|','link','unlink','emoticons'],
				afterChange: function (e) {
					this.sync();
				}
			});
	 		$("#innerMessage-form-messageSend").form({
	    			onSubmit: function(){
	    				var flag = $(this).form('validate');
	    				if(flag==false){
	    					return false;
	    				}
						loading("短信消息提交中，请稍后...");
	    			},
	    			success:function(data){
						loaded();
	    				//$.parseJSON()解析JSON字符串 
	    				var json=$.parseJSON(data);
	    				if(json.flag==true){
	    					$.messager.alert("提醒","添加成功!");	    					
	    					try{
	    						var msgSendPageId=$.trim($("#innerMessage-form-messageSend-msgSendPageId").val());
	    						msgSendPageId=(msgSendPageId!="")?msgSendPageId:'innerMessage-window-sendMessageOper-content';	    						
	    						var aWin=window;
	    						if($("#"+msgSendPageId).size()>0){
	    							aWin=window;
	    						}else if(parent.$("#"+msgSendPageId).size()>0){
	    							aWin=parent;
	    						}else if(top.$("#"+msgSendPageId).size()>0){
	    							aWin=top;
	    						}
	    						try{	    						   							
	    							aWin.$("#"+msgSendPageId).window('close');
	    						}catch(e){
	    						}
	    						try{
	    							aWin.$("#"+msgSendPageId).dialog('close');
	    						}catch(e){
	    						}
	    					}catch(e){
	    					}
	    					try{
		    					if($("#innerMessage-table-messageSendList").size()>0){
		    						$("#innerMessage-table-messageSendList").datagrid('reload');
		    					}
	    					}catch(e){
	    					}
	    					try{
		    					if($("#innerMessage-table-messageReceiveList").size()>0){
		    						$("#innerMessage-table-messageReceiveList").datagrid('reload');
		    					}
	    					}catch(e){
	    					}
	    				}
	    				else{
	    					$.messager.alert("提醒",( json.msg|| "添加失败！"));
	    				}
	    			},
					complete:function(xhr,s){
						loaded();
					}
	    	});
	 		
	 		$("#innerMessage-messageSend-btn-saveMessage").click(function(){
	 			$.messager.confirm("提醒","您是否要发送短信?",function(r){
	   				if(r){   
		   				try{
		   					$("#innerMessage-form-messageSend-title").val(innerMessage_Form_MessageSend_Editor.text());
		   				}catch(e){
		   				}
	   					$("#innerMessage-form-messageSend").submit(); 				
	   				}
	    		});
	 		}); 
    		//按人员
    		$("#innerMessage-form-messageSend-receivers").widget({
    			name:'t_msg_content',
				col:'receivers',
    			singleSelect:false,
    			width:350,
    			initValue:'${touserids}',
    			//param:[{field:'userid', op:'notequal', value:'${user.userid}'}],
    			onlyLeafCheck:true,
    			onChecked:function(data){
    			},
    			onClear:function(){
    			}
    		});
 		});
 	</script>
  </body>
</html>
