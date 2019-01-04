<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>邮件页面</title>    
	<%@include file="/include.jsp" %> 	
  	<script type="text/javascript" src="js/kindeditor/kindeditor-min.js"></script>  
	<script type="text/javascript" src="js/kindeditor/keconfig.js"></script> 
  	<script type="text/javascript" src="js/kindeditor/lang/zh_CN.js"></script> 
  	<script type="text/javascript" src="js/linq.min.js"></script> 		
	<link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>
  </head>
  
  <body>
     	<div class="easyui-layout" data-options="fit:true">  
           <div title="我的邮箱目录" data-options="region:'west',split:true" style="width:220px;">
           		<div class="easyui-layout" data-options="fit:true">
           			<div title="" data-options="region:'north'" class="msgEmailPage_LayoutNorth">
           				<a href="javaScript:void(0);" id="messageEmail-emailPage-btn-receiveEmail"  onclick="javascript:emailPage_openPanel('message/email/emailReceivePreviewListPage.do','收件箱')" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-getdown'">收邮件</a>
           				<a href="javaScript:void(0);" id="messageEmail-emailPage-btn-sendEmail" onclick="javascript:emailPage_openPanel('message/email/emailAddPage.do')" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'">发邮件</a>
           			</div>
           			<div title="" data-options="region:'center'">
           				<div id="messageEmail-emailPage-emailMenu" class="easyui-accordion" style="width:210px;height:300px;" border="false">
           					<div title="邮件管理箱" data-options="iconCls:'button-readmess'" style="overflow:auto;">
           						<div class="msgEmailPage_mailMenuItem">
           							<a href="javaScript:void(0);" id="messageEmail-emailPage-btn-receiveBoxList" onclick="javascript:emailPage_openPanel('message/email/emailReceivePreviewListPage.do','收件箱')" class="msgEmailPage_mailMenuItema">收件箱</a>
           						</div>
           						<div style="background-color:#EEF; " class="msgEmailPage_mailMenuItem">
           							<a href="javaScript:void(0);" id="messageEmail-emailPage-btn-draftBoxList" onclick="javascript:emailPage_openPanel('message/email/emailDraftListPage.do','草稿箱')" class="msgEmailPage_mailMenuItema">草稿箱</a>
           						</div>
           						<div  class="msgEmailPage_mailMenuItem">
           							<a href="javaScript:void(0);" id="messageEmail-emailPage-btn-sendBoxList" onclick="javascript:emailPage_openPanel('message/email/emailSendPreviewListPage.do','已发邮件箱')" class="msgEmailPage_mailMenuItema">已发送</a>
           						</div>
           						<div style="background-color:#EEF;" class="msgEmailPage_mailMenuItem">
           							<a href="javaScript:void(0);" id="messageEmail-emailPage-btn-trashBoxList" onclick="javascript:emailPage_openPanel('message/email/emailDropListPage.do','废纸篓')" class="msgEmailPage_mailMenuItema">废纸篓</a>
           						</div>
           					</div>
           					<div title="其他邮件箱" data-options="iconCls:'button-readmess'" style="overflow:auto;">
           						<div id="messageEmail-emailPage-div-otherMailBoxList" style="max-height: 182px;overflow:auto;">
            						<div style="clear:both"></div>
           						</div>
           						<div style="padding:10px; cursor:pointer">
           							<a href="javaScript:void(0);" onclick="javascript:emailPage_openPanel('message/email/emailBoxListPage.do')"  style="display:block; text-decoration: none; ">自定义邮件箱管理</a>
           						</div>  
           					</div>
           					<%--<div title="Internet邮件箱管理" data-options="iconCls:'icon-extend-newmail'" style="overflow:auto;">
           						<div style="border-bottom: 1px solid #ccc;padding:10px; cursor:pointer">
           							<a href="javaScript:void(0);" onclick="javascript:emailPage_addWebMailConfig()" style="display:block; text-decoration: none; ">新增Internet邮箱</a>
           						</div>
           						<div style="border-bottom: 1px solid #ccc;padding:10px; cursor:pointer;background-color:#EEF;">
           							<a href="javaScript:void(0);" onclick="javascript:emailPage_openPanel('message/email/webMailConfigListPage.do')" style="display:block; text-decoration: none; ">管理邮件箱</a>
           						</div> 
           					</div> --%>  
           				</div>
           			</div>
           		</div>   			
	    </div>
	    <div title="" data-options="region:'center',split:true,border:true" >
	    	<div title="收件箱" id="messageEmail-emailPage-panel-showContentPanel"></div>
		</div>			
		<div id="messageEmail-dialog-webMailConfigOper" class="easyui-dialog" closed="true"></div>
	</div>
	<div id="attachment_extend_menu_upfile" style="z-index: 9999; display: none;position: absolute;background: #FFF;border: 1px solid #373737;width:100px;">
	</div>
	<script type="text/javascript">		
		var emailPage_openPanel=function(surl,stitle,scache){
			scache=scache||false;
			surl=surl ||"";
			stitle=stitle ||"";
			if(surl!=""){
				try{
					$("#messageEmail-emailPage-panel-showContentPanel").panel('clear');
				}catch(e){
					
				}

				$("#messageEmail-emailPage-panel-showContentPanel").panel({
					fit:true,
					cache:scache,
					href:surl,
					title:stitle
				});
			}
		}
		var emailPage_addWebMailConfig=function(){
			var $webMailConfigOper=$('#messageEmail-dialog-webMailConfigOper');
				$webMailConfigOper.dialog({  
				    title: '添加',  
				    width: 600,  
				    height: 450,
				    closed: true,  
				    cache: false,  
				    href: 'message/email/showWebMailConfigAddPage.do',  
				    modal: true
				});
				$webMailConfigOper.dialog("open");
		}
		var emailPage_showMyEmailBoxList=function(){
			$.ajax({
				url:'message/email/showEmailBoxList.do',
				data:'',
				type:'post',
				dataType:'json',
				cache:false,
				success:function(json){
					$("#messageEmail-emailPage-div-otherMailBoxList").html("");
					json=json||[];
	            	if(json.length>0){
	            		var data=new Array();
	            		$.each(json,function(i,item){
		            		data.push("<div style=\" ");
		            		if(i%2 !=0){
		            			data.push("background-color:#EEF;");
		            		}
		            		data.push(" \" class=\"msgEmailPage_mailMenuItem\">");
							data.push("<a href=\"javascript:void(0);\"");
							data.push(" onclick=\"javascript:emailPage_openPanel('message/email/emailReceivePreviewListPage.do?boxid="+item.id+"','"+item.title+"')\" ")
							data.push(" class=\"msgEmailPage_mailMenuItema\">");
							data.push(item.title);
							data.push("</a>");
							data.push("</div>");
						});
						data.push("<div style=\"clear:both\"></div>");
	            		$("#messageEmail-emailPage-div-otherMailBoxList").html(data.join(""));
	            	}
	            }
			});
		}
		$(document).ready(function() {
			emailPage_showMyEmailBoxList();
			emailPage_openPanel('message/email/emailReceivePreviewListPage.do','收件箱');
		});
	</script>
  </body>
</html>


