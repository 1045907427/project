<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>手机短信发送</title>    
	<%@include file="/include.jsp" %>
	<link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>

  </head>
  
  <body>
  	<div>
	  <div title="发送手机短信" >
	    <form action="message/mobileSms/mobileSmsSend.do" method="post" id="mobilesms-form-smsSend">
	    	<div class="pageContent">
	    		<div style="height:auto;margin-top:5px;margin-bottom: 5px;">
					<div style="float:left;width:130px;line-height:22px;">收信人【内部用户】：</div>						
					<div style="float:left;">
						<input id="mobilesms-form-smsSendPage-msgrecvids"  name="smsContent.receivers"  />
	    			</div>
	    			<div style="clear:both;padding-left:130px;height:30px;line-height:30px;">
		    			<a href="javaScript:void(0);" id="mobilesms-form-smsSendPage-btn-showPersonDiv" class="easyui-linkbutton" data-options="plain:false" >从人员档案添加</a>&nbsp;-		    			
	  					<security:authorize url="/message/mobileSms/mobileSmsOutSend.do">			    			
		    			<a href="javaScript:void(0);" id="mobilesms-form-smsSendPage-btn-showOutSendDiv" class="easyui-linkbutton" data-options="plain:false">从外部号码添加</a>
		    			</security:authorize>
			    	</div>
	    			<div style="clear:both"></div>
				</div>
				<div id="mobilesms-form-smsSendPage-div-showPersonDiv" style="display:none;height:auto;margin-top:5px;margin-bottom: 5px;">
					<div style="float:left;width:130px;line-height:22px;">收信人【人员档案】：</div>						
					<div style="float:left;">
						<input id="mobilesms-form-smsSendPage-recvpersons"  name="smsContent.recvpersons"  />
	    			</div>
	    			<div style="clear:both"></div>
	    			<input type="hidden" id="mobilesms-form-smsSendPage-hidden-recvpersons" />
				</div>
	  			<security:authorize url="/message/mobileSms/mobileSmsOutSend.do">
			    	<div id="mobilesms-form-smsSendPage-div-showOutSendDiv" style="display:none;hiehgt:auto;">
		    			<div style="float:left;width:130px;line-height:22px;">收信人【外部号码】：</div>
		    			<div style="float:left;">
			    			<textarea id="mobilesms-form-smsSendPage-recvphone"  name="smsContent.recvphones" style="height:60px;width:350px;"></textarea>（多个用户请用,分隔）
		    			</div>
		    			<div style="clear:both"></div>	
	    				<input type="hidden" id="mobilesms-form-smsSendPage-hidden-recvphone" />    			
		    		</div>
	    		</security:authorize>
	    		<div style="hiehgt:auto;">
	    			<div style="float:left;width:130px;line-height:22px;">短信内容：</div>
	    			<div style="float:left;">
	    				<div style="height:30px;line-height:25px;">已输入<input id="mobilesms-form-smsSendPage-content-word-use" type="text" style="width:50px" readonly="readonly" value="0"/>字符，剩余<input id="mobilesms-form-smsSendPage-content-word-left" type="text" style="width:50px" readonly="readonly" value="200" />字符，每条70字，最多可拆分成3条发送</div>
	    				<div>
	    					<textarea id="mobilesms-form-smsSendPage-content" name="smsContent.content" rows="0" cols="0" style="width:500px;height:70px" max="200" onpropertychange="javascript:msgSmsSendPage_contentWordCount();" oninput="javascript:msgSmsSendPage_contentWordCount();"></textarea>
	    				</div>
	    				<div style="clear:both"></div>		    				
	    			</div>
	    			<div style="clear:both"></div>	
	    		</div>
	    		<div style="hiehgt:30px;display:none;">
	    			<div style="float:left;width:130px;line-height:22px;">发送时间：</div>
	    			<div style="float:left;">
	    				<input type="text" <c:if test="${now !=null }"> value="<fmt:formatDate  value='${now }' pattern='yyyy-MM-dd HH:mm:ss' />" </c:if> name="smsContent.sendtime" style="width:130px;" readonly="readonly" onClick="WdatePicker({minDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
	    			</div>
	    			<div style="clear:both"></div>	 
	    		</div>	    	
	    		<div style="padding-top:20px; width:auto;height:auto;">	    			
	    			<div style="margin-left:130px;">
	    			<a href="javaScript:void(0);" id="mobilesms-smsSendPage-btn-saveSms" class="easyui-linkbutton" data-options="plain:false,iconCls:'icon-mobilemess'">发送短信</a>
	    			<a href="javaScript:void(0);" id="mobilesms-smsSendPage-btn-signcontent" class="easyui-linkbutton" data-options="plain:false,iconCls:'button-edit'">签名</a>
	    			<a href="javaScript:void(0);" id="mobilesms-smsSendPage-btn-clearcontent" class="easyui-linkbutton" data-options="plain:false,iconCls:'button-delete'">清空内容</a>
	    			</div>
	    		</div>
	    	</div>
    	</form>
		</div>
		<input type="hidden" id="mobilesms-form-smsSendPage-smsSendPageId" value="${smsSendPageId }"/>
 	</div>
  	<script type="text/javascript">
		var msgSmsSendPage_contentWordCount=function(){
			var $content=$("#mobilesms-form-smsSendPage-content");
			var len=($content.val()||"").length;
			var _max=$content.attr("max") || 200;
			if(len>=_max){
				$content.val($content.val().substring(0,_max));
				$("#mobilesms-form-smsSendPage-content-word-use").val(_max);
				$("#mobilesms-form-smsSendPage-content-word-left").val(0);
			}else{
				$("#mobilesms-form-smsSendPage-content-word-use").val(len);
				$("#mobilesms-form-smsSendPage-content-word-left").val(_max-len);
			}
		}
		var msgSmsSendPage_signContent=function(){
	 		var text=new Array();
	 		text.push('${username}');
	 		text.push("(");
	 		text.push('${deptname}');
	 		text.push(")：");
	 		var content=$("#mobilesms-form-smsSendPage-content").val()||"";
	 		$("#mobilesms-form-smsSendPage-content").val(text.join("")+content);
		}
 		$(document).ready(function(){
 	 		//内容签名
 			msgSmsSendPage_signContent();
 			//内容统计
 			msgSmsSendPage_contentWordCount();

	 		$("#mobilesms-form-smsSend").form({
	    			onSubmit: function(){
	    				var flag = $(this).form('validate');
	    				if(flag==false){
	    					return false;
	    				}
	    			},
	    			success:function(data){
	    				//$.parseJSON()解析JSON字符串 
	    				var json=$.parseJSON(data);
	    				if(json.flag==true){
	    					$.messager.alert("提醒","短信发送申请成功，我们正在努力进行发送处理，<br/>您可以继续进行其他工作!");	
	    					try{
		    					if($("#mobileSms-table-smsSendList").size()>0){
		    						$("#mobileSms-table-smsSendList").datagrid('reload');
		    					}
	    					}catch(e){
	    					}
	    					try{
		    					if($("#mobileSms-table-smsReceiveList").size()>0){
		    						$("#mobileSms-table-smsReceiveList").datagrid('reload');
		    					}
	    					}catch(e){
	    					}
    						var smsSendPageId=$.trim($("#mobilesms-form-smsSendPage-smsSendPageId").val());
    						smsSendPageId=(smsSendPageId!="")?smsSendPageId:'mobileSms_window_sendSmsOper'; 
    						if($("#"+smsSendPageId).size()>0){  
								try{
    								$("#"+smsSendPageId).dialog('close');
								}catch(e){
								}     					
		    					try{							
	    							$("#"+smsSendPageId).window('close');
		    					}catch(e){
		    					}
    						}
	    				}
	    				else{
	    					$.messager.alert("提醒",( json.msg|| "短信发送申请提交失败！"));
	    				}
	    			}
	    	});
	 		
	 		$("#mobilesms-smsSendPage-btn-saveSms").click(function(){
	 			$.messager.confirm("提醒","您是否要发送短信?",function(r){
	   				if(r){
	   					$("#mobilesms-form-smsSend").submit(); 				
	   				}
	    		});
	 		});
	 		$("#mobilesms-smsSendPage-btn-signcontent").click(function(){
	 			msgSmsSendPage_signContent();
	 			msgSmsSendPage_contentWordCount();
	 		});
	 		$("#mobilesms-smsSendPage-btn-clearcontent").click(function(){
		 		$("#mobilesms-form-smsSendPage-content").val("");
		 		msgSmsSendPage_contentWordCount();
	 		});

    		//按内部用户
    		$("#mobilesms-form-smsSendPage-msgrecvids").widget({
    			name:'t_msg_mobilesms',
				col:'receivers',
    			singleSelect:false,
    			width:350,
    			//param:[{field:'mobilephone', op:'notequal', value:''},{field:'isdept', op:'equal', value:'0'}],
    			onlyLeafCheck:true,
    			onChecked:function(data){
    			},
    			onClear:function(){
    			}
    		});
    		$("#mobilesms-form-smsSendPage-recvpersons").widget({
        		//内部档案
    			name:'t_msg_mobilesms',
				col:'recvpersons',
    			singleSelect:false,
    			width:350,
    			//param:[{field:'mobilephone', op:'notequal', value:''},{field:'isdept', op:'equal', value:'0'}],
    			onlyLeafCheck:true,
    			onChecked:function(data){
    			},
    			onClear:function(){
    			}
    		});
    		$("#mobilesms-form-smsSendPage-btn-showPersonDiv").click(function(){
				//从人员档案添加
				var $showPersonDiv=$("#mobilesms-form-smsSendPage-div-showPersonDiv");
				if($showPersonDiv.css("display")=="none"){
					$(this).linkbutton({  
						text: '隐藏人员档案添加'  
					});
					$("#mobilesms-form-smsSendPage-hidden-recvpersons").val("1");
					$showPersonDiv.show();
				}else{
					$showPersonDiv.hide();
					$(this).linkbutton({  
						text: '从人员档案添加'  
					});
					$("#mobilesms-form-smsSendPage-hidden-recvpersons").val("0");
				}
			});
    		$("#mobilesms-form-smsSendPage-btn-showOutSendDiv").click(function(){
				//从外部号码添加
				var $showOutSendDiv=$("#mobilesms-form-smsSendPage-div-showOutSendDiv");
				if($showOutSendDiv.css("display")=="none"){
					$(this).linkbutton({  
						text: '隐藏外部号码添加'  
					});
					$("#mobilesms-form-smsSendPage-hidden-recvphone").val("1");
					$showOutSendDiv.show();
				}else{
					$showOutSendDiv.hide();
					$(this).linkbutton({  
						text: '从外部号码添加'  
					});
					$("#mobilesms-form-smsSendPage-hidden-recvphone").val("0");
				}
			});
 		});
 	</script>
  </body>
</html>
