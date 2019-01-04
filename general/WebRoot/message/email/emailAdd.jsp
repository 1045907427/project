<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
	<title>写邮件</title>
	<%@include file="/include.jsp" %> 	
  	<script type="text/javascript" src="js/kindeditor/kindeditor-min.js"></script> 
	<script type="text/javascript" src="js/kindeditor/keconfig.js"></script> 
  	<script type="text/javascript" src="js/kindeditor/lang/zh_CN.js"></script> 
  	<script type="text/javascript" src="js/linq.min.js"></script> 	
	<link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>
  </head>
 
  <body>
  	<div class="easyui-layout" data-options="fit:true">			
			<div title="<c:if test='${ empty(noshowtitle) or noshowtitle==1 }'>发邮件</c:if>" data-options="region:'center'">
				<div style="padding:20px 10px 10px;">
					<form action="message/email/sendEmail.do" method="post" id="messageEmail-form-addEmail">						
						<div style="height:auto">
							<div style="float:left;width:100px;line-height:30px;">收件人：</div>						
							<div style="float:left;">
								<input id="messageEmail-form-addEmail-receiveuser" name="emailContent.receiveuser" value="${emailContent.receiveuser}"  />
			    			</div>
			    			<div style="clear:both;padding-left:100px;height:30px;line-height:30px;">			    				
								<%-- <a href="javaScript:void(0);" id="messageEmail-form-addEmail-btn-showTowebmailDiv" class="easyui-linkbutton" data-options="plain:false">添加外部接收人</a>&nbsp;- --%>
				    			<a href="javaScript:void(0);" id="messageEmail-form-addEmail-btn-showCopytouserDiv" class="easyui-linkbutton" data-options="plain:false" >添加抄送</a>&nbsp;-			    			
				    			<a href="javaScript:void(0);" id="messageEmail-form-addEmail-btn-showSecretTouserDiv" class="easyui-linkbutton" data-options="plain:false">添加密送</a>				    			
				    			<input type="hidden" name="emailContent.webmailflag" id="messageEmail-form-addEmail-webmailflag" value="${emailContent.webmailflag }"/>
			    			</div>
			    			<div style="clear:both"></div>
						</div>					
						<%-- <div style="height:auto;display:none" id="messageEmail-form-addEmail-div-towebmail">
							<div style="float:left;width:100px;line-height:36px;">外部接收人：</div>						
							<div style="float:left;">
								<textarea id="messageEmail-form-addEmail-view-towebmailname"  name="towebmailname" style="height:30px;width:350px;">${emailContent.towebmail }</textarea>
								<input id="messageEmail-form-addEmail-towebmailname" name="towebmailname"/>
				    			<input type="hidden" name="emailContent.towebmail" id="messageEmail-form-addEmail-towebmail" value="${emailContent.towebmail }" />
				    			<a href="javaScript:void(0);" id="messageEmail-form-addEmail-btn-addTowebmail" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
				    			<a href="javaScript:void(0);" id="messageEmail-form-addEmail-btn-delTowebmail" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">清空</a>
				    			(如有多个接收人请用逗号,分隔)
			    			</div>
			    			<div style="clear:both"></div>
						</div>
						<div style="hiehgt:30px;padding-top:5px;padding-bottom:5px;;display:none" id="messageEmail-form-addEmail-div-fromwebmail">
							<div style="float:left;width:100px;line-height:22px;">Internet邮箱：</div>
							<div style="float:left">
								<input type="text" class="easyui-combobox" id="messageEmail-form-addEmail-fromwebmailid" name="emailContent.fromwebmailid" style="width:200px;"  data-options="valueField:'id',textField:'name',editable:false" value="${emailContent.fromwebmailid }" />
								请到“Internet邮件”模块建立邮箱并设置密码
								<input type="hidden" name="emailContent.fromwebmail" id="messageEmail-form-addEmail-fromwebmail" value="${emailContent.fromwebmail }" />
							</div>
							<div style="clear:both"></div>
						</div> --%>
						<div style="height:auto;display:none" id="messageEmail-form-addEmail-div-copytouser">
							<div style="float:left;width:100px;line-height:36px;">抄送：</div>			
							<div style="float:left;">								
								<input type="text" id="messageEmail-form-addEmail-copytouser" name="emailContent.copytouser" value="${emailContent.copytouser }" />
				    			<input type="hidden" name="iscopytouser" id="messageEmail-form-addEmail-iscopytouser" value="0" />
			    			</div>
			    			<div style="clear:both"></div>
						</div>
						<div style="height:auto;display:none" id="messageEmail-form-addEmail-div-secrettouser" >
							<div style="float:left;width:100px;line-height:36px;">密送：</div>						
							<div style="float:left;">
								<input type="text" id="messageEmail-form-addEmail-secrettouser" name="emailContent.secrettouser" value="${emailContent.secrettouser }" />
				    			<input type="hidden" name="issecrettouser" id="messageEmail-form-addEmail-issecrettouser" value="0" />
			    			</div>
			    			<div style="clear:both"></div>
						</div>
						<c:if test="${oper=='forward'}">
							<div style="hiehgt:30px;padding-top:5px;padding-bottom:5px;">
								<div style="float:left;width:100px;line-height:22px;">邮件格式：</div>
								<div style="float:left">
									<input type="radio" name="emlcformat" id="messageEmail-form-addEmail-emlcformat-cformat" checked="checked"/><lable for="messageEmail-form-addEmail-emlcformat-cformat">转发格式  </lable>
									<input type="radio" name="emlcformat" id="messageEmail-form-addEmail-emlcformat-corg"/><lable for="messageEmail-form-addEmail-emlcformat-corg">原文格式  </lable> 
								</div>								
								<div style="clear:both"></div>
							</div>
						</c:if>
						<div style="hiehgt:30px;padding-top:5px;padding-bottom:5px;">
							<div style="float:left;width:100px;line-height:22px;">邮件主题：</div>
							<div style="float:left">
								<input type="text" id="messageEmail-form-addEmail-title" name="emailContent.title" class="easyui-validatebox" required="true" style="width:600px;" value="${emailContent.title }"/>
							</div>
							<div style="float:left">
								<select id="messageEmail-form-addEmail-mailtype" name="emailContent.importantflag">
									<option value="0" <c:if test="${emailContent.importantflag == 0}">selected="selected"</c:if>>一般邮件</option>
									<option value="1" <c:if test="${emailContent.importantflag == 1}">selected="selected"</c:if>>重要邮件</option>
									<option value="2" <c:if test="${emailContent.importantflag == 2}">selected="selected"</c:if>>非常重要</option>
								</select>
							</div>
							<div style="clear:both"></div>
						</div>
						<div id="messageEmail-form-addEmail-content-div">
							<div style="float:left;width:100px;line-height:22px;">邮件内容：</div>
							<div style="float:left">
								<textarea id="messageEmail-form-addEmail-content" name="emailContent.content" rows="0" cols="0" style="width:670px;height:400px;">${emailContent.content }</textarea>								
								<textarea col="0" row="0" style="display:none;width:0;height:0;" id="messageEmail-form-addEmail-hidcontent" >${emailContent.content}</textarea>							
							</div>
							<div style="clear:both"></div>
						</div>
						<div id="messageEmail-form-addEmail-attachment-show-div" style="display:none;margin-top:10px;margin-bottom: 5px;">
							<div style="float:left;width:100px;line-height:22px;">附件：</div>
							<div style="float:left">
								<div id="messageEmail-form-addEmail-attachment-uplist">
								</div>
			    			</div>
							<div style="clear:both"></div>
						</div>
						<div id="messageEmail-form-addEmail-attachment-div" style="margin-top:5px;margin-bottom: 5px;">
							<div style="float:left;width:100px;line-height:22px;">附件选择：</div>
							<div style="float:left">
								<a href="javascript:void(0);" id="attachment-upload-addclick" class="easyui-linkbutton" data-options="plain:false,iconCls:'icon-extend-uploadfile'">+添加附件</a>
			    			</div>
							<div style="clear:both"></div>
							<input type="hidden" id="messageEmail-form-addEmail-attachment"  name="emailContent.attach" value="${emailContent.attach }" />
							<input type="hidden" id="messageEmail-form-addEmail-attachment-delete"  name="attachdelete" />
						</div>
						<div style="height:30px;">
							<div style="float:left;width:100px;line-height:22px;">提醒：</div>
							<div style="float:left">
			    				<input type="checkbox" id="messageEmail-form-editEmail-ismsg" name="emailContent.ismsg" value="1" <c:choose><c:when test="${emailContent.ismsg == 1}"> checked="checked" </c:when><c:when test="${!empty(emailContent.id)}"> </c:when><c:otherwise> checked="checked" </c:otherwise></c:choose> style="float:left;" />
			    				<span style="display:block;float:left;line-height:28px;" for="messageEmail-form-editEmail-ismsg">使用内部短信提醒</span>
			    			</div>
							<div style="clear:both"></div>
						</div>
						<div style="height:30px;">
							<div style="float:left;width:100px;line-height:22px;">收条：</div>
							<div style="float:left">
			    				<input type="checkbox" id="messageEmail-form-editEmail-isreceipt" name="emailContent.isreceipt" value="1" <c:if test="${emailContent.isreceipt == 1}"> checked="checked" </c:if>  style="float:left;"/>
			    				<span style="display:block;float:left;line-height:28px;" for="messageEmail-form-editEmail-ismsg">请求阅读收条 &nbsp;(收件人第一次阅读邮件时，短信提醒发件人)</span>
			    			</div>
							<div style="clear:both"></div>
						</div>
						<div style="clear:both"></div>
						<input type="hidden" name="emailContent.sendflag" id="messageEmail-form-editEmail-sendflag" value="1" />
						<c:if test="${oper=='edit'}">
							<input type="hidden" name="emailContent.id" id="messageEmail-form-editEmail-id" value="${emailContent.id }" />
						</c:if>
					</form>
				</div>
			</div>
			<div data-options="region:'south'" style="height: 40px;">
				<div class="buttonBG" style="text-align: right;">
		    		<a href="javaScript:void(0);" id="messageEmail-form-addEmail-btn-saveEmail" class="easyui-linkbutton" data-options="plain:false,iconCls:'button-save'">立即发送</a>
		    		<a href="javaScript:void(0);" id="messageEmail-form-addEmail-btn-saveEmailDraft" class="easyui-linkbutton" data-options="plain:false,iconCls:'button-save'">保存到草稿箱</a>
		   		</div>		
				<div id="User-window-userChooser" class="easyui-dialog" closed="true"></div>
			</div>
			<input type="hidden" id="messageEmail-form-addEmail-hid-emlSendPageId" value="${emlSendPageId }"/>
		</div>
		<script type="text/javascript">
		function deleteAttach(fileid){
			if(fileid==null || $.trim(fileid)==""){
				return false;
			}
			fileid=$.trim(fileid);
			var $attach=$("#messageEmail-form-addEmail-attachment");
			var $attachdel=$("#messageEmail-form-addEmail-attachment-delete");
			var files=$.trim($attach.val() || "");
			var fdels=$.trim($attachdel.val() || "");
			if(files==""){
				return false;
			}
			var filearr=files.split(',');
			var fdelarr=new Array();
			if(fdels!=""){
				fdelarr=fdels.split(',');
			}
			var index=0;
			var flag=false;
			for(index=0;index<filearr.length;index++){
				if(filearr[index]==fileid){
					flag=true;
					break;
				}
			}
			if(flag){
				filearr.splice(index,1);
				fdelarr.push(fileid);
				$attach.val(filearr.join(','));
				$attachdel.val(fdelarr.join(','));
				$("#messageEmail-form-addEmail-attachment-uplist").find("div[fileid='"+fileid+"']").remove();
			}
		}
		function showAttachMenu(obj,fileid){
			if(fileid==null || $.trim(fileid)==""){
				return false;
			}
			fileid=$.trim(fileid);
			//var $menuwrap=$("#attachment_extend_menu_upfile_wrap");
            var $menu = $("#attachment_extend_menu_upfile_"+fileid);
        	$menu.mouseout(function(){
          		$menu.hide();
          	}); 
          	
          	$menu.bind("mouseover",function(event){
          		event.stopPropagation(); 
          		$menu.show();
          		//$menu.unbind("mouseover"); 
          	});
          	$(obj).bind("mouseover",function(event){
          		event.stopPropagation(); 
          		$(obj).unbind("mouseover"); 
          		var top=$(obj).outerHeight()-2;
              	$menu.animate({ opacity: "show",  top: top}, 300); 
          		$menu.show();               
          	}); 
          	$(obj).bind("mouseout",function(event){
          		event.stopPropagation(); 
          		$(obj).unbind("mouseout"); 
          		$menu.hide();               
          	});
		}
		function showAttachMenuContent(item,isview){
			if(null==item){
				return "";
			}
			if(isview==null || false!=isview){
				isview=true;
			}
			var htmlsb=new Array();
			
		   	htmlsb.push("<div style=\"float:left;display:inline;margin-right:10px;position:relative;\" ");
		   	htmlsb.push(" onmouseover=\"javascript:showAttachMenu(this,'"+item.id+"')\" ");
			htmlsb.push(" fileid=\"");
			htmlsb.push(item.id);
			htmlsb.push("\">");
		   	htmlsb.push("<a onclick='$.webuploader.openShowFileViewer("+item.id+");' href='javascript:void(0);' title='点击查看'>");
		   	if(null!=item.oldFileName){
		   		htmlsb.push(item.oldFileName);
		   	}else if(null!=item.oldfilename){
		   		htmlsb.push(item.oldfilename);		   		
		   	}
		   	htmlsb.push("</a>");

			htmlsb.push("<div");
		   	htmlsb.push(" id=\"attachment_extend_menu_upfile_"+item.id+"\" ");
		   	htmlsb.push(" style=\"z-index: 9999;display:none ;position: absolute;background: #FFF;border: 1px solid #373737;width:100px;\">");
			htmlsb.push("<a target=\"_blank\" href=\"common/download.do?id="+item.id+"\" title=\"点击下载\" style=\"display: block;line-height: 20px;padding: 2px 0 2px 10px;\" ");
	        htmlsb.push("onmouseover=\"javascript:this.style.backgroundColor='#E2E5E6'\" onmouseout=\"javascript:this.style.backgroundColor='#FFF'\">下载</a>");
	        htmlsb.push("<a onclick='$.webuploader.openShowFileViewer("+item.id+");' href='javaScript:void(0);' title='点击查看' ");
	        htmlsb.push(" style=\"display: block;line-height: 20px;padding: 2px 0 2px 10px;\" ");
	        htmlsb.push("onmouseover=\"javascript:this.style.backgroundColor='#E2E5E6'\" onmouseout=\"javascript:this.style.backgroundColor='#FFF'\" >查看</a>");
	        if(!isview){
	          	htmlsb.push("<a href=\"javascript:void(0);\" onclick=\"javascript:deleteAttach("+item.id+")\" style=\"display: block;line-height: 20px;padding: 2px 0 2px 10px;\"");
	          	htmlsb.push("onmouseover=\"javascript:this.style.backgroundColor='#E2E5E6'\" onmouseout=\"javascript:this.style.backgroundColor='#FFF'\" >删除</a>");
	        }
		   	htmlsb.push("</div>");
		   	
		   	htmlsb.push("</div>");
			
		   	return htmlsb.join("");
		}
		function renderAttachList(idarrs,isview){
			if(isview==null || false!=isview){
				isview=true;
			}
			if(idarrs!=null && $.trim(idarrs)!=""){
				idarrs=$.trim(idarrs);
				$.ajax({
					url:"common/getAttachFileList.do",
					data:{idarrs: idarrs},
					async:true,
					type:'POST',
					dataType:"text",
					success:function(data){
						if(data != null && data != "null"){
							$("#messageEmail-form-addEmail-attachment-show-div").show();
							var json = $.parseJSON(data);
							var htmlsb=new Array();
							$.each(json,function(i,item){
							   	htmlsb.push(showAttachMenuContent(item,isview));
							});
						   	$("#messageEmail-form-addEmail-attachment-uplist").html(htmlsb.join(""));		
						}				   	
					}
				});		    	
			}
		}
		
		
			var addEmail_KEditor=KindEditor.create('#messageEmail-form-addEmail-content',{
				allowPreviewEmoticons:false,
				allowImageUpload:true,
				allowFlashUpload:false,
				allowMediaUpload:false,
				allowFileUpload:false,
				allowFileManager:false,
				uploadJson : KEditor.kuploadjson,
				resizeType: 1,
				syncType : 'auto',
				items: KEditor.kditem,
				afterChange: function (e) {
					this.sync();
				}
		 	});
			var addEmail_initLoading=function(){
				var tempd="";
				tempd=$("#messageEmail-form-addEmail-webmailflag").val()||"";
				if($.trim(tempd)!=""){
					$("#messageEmail-form-addEmail-webmailflag").val("0")
				}
				tempd=$("#messageEmail-form-addEmail-copytouser").val()||"";
				if($.trim(tempd)!=""){
					$("#messageEmail-form-addEmail-btn-showCopytouserDiv").click();
				}
				tempd=$("#messageEmail-form-addEmail-secrettouser").val()||"";
				if($.trim(tempd)!=""){
					$("#messageEmail-form-addEmail-btn-showSecretTouserDiv").click();
				}
				if($("#messageEmail-form-editEmail-ismsg").attr("checked")==false){
					$("#messageEmail-form-editEmail-ismsg").val("0");
				}
				if($("#messageEmail-form-editEmail-isreceipt").attr("checked")==false){
					$("#messageEmail-form-editEmail-isreceipt").val("0");
				}
			}
			var addEmail_showReplyContent=function(corg){
				<c:if test="${oper=='forward' || oper=='reply' || oper=='replyall'}">
					corg=corg||"0";
					var html=new Array();
					<c:choose>
						<c:when test="${oper=='forward'}">
							if(corg=="1"){
								$("#messageEmail-form-addEmail-title").val("${emailContent.title}");
								addEmail_KEditor.html($("#messageEmail-form-addEmail-hidcontent").val()||"");
							}else{
								$("#messageEmail-form-addEmail-title").val("Fw：${emailContent.title}");
							}
						</c:when>
						<c:when test="${oper=='reply' || oper=='replyall'}">
							corg="0";
							$("#messageEmail-form-addEmail-title").val("Re：${emailContent.title}");
						</c:when>
						<c:otherwise>
							corg="0";
						</c:otherwise>
					</c:choose>
					if(corg!="1"){
						<c:if test="${(not empty emailContent.recvusername) && oper !='forward'}">
							html.push("${emailContent.recvusername},");
						</c:if>
						html.push("您好！<br />");
						html.push("<br />");
						html.push("<br />");
						html.push("========");
						<c:if test="${not empty emailContent.addusername}">
							html.push("${emailContent.addusername}");
						</c:if>
						<c:if test="${emailContent.addtime!=null}">
							html.push('在 <fmt:formatDate  value="${emailContent.addtime }" pattern="yyyy-MM-dd HH:mm:ss" /> 的');
						</c:if>
						html.push("来信中写道：==========<br /><br />");
						html.push("<div style=\"border-left: #000000 2px solid; padding-bottom: 5px; padding-left: 5px; padding-right: 5px; padding-top: 5px\">");
						html.push($("#messageEmail-form-addEmail-hidcontent").val()||"");
						html.push("</div>");
						html.push("<br />");
						html.push("=================================================<br />");
						html.push("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;致<br />");
						html.push("礼！");
						addEmail_KEditor.html(html.join(""));
					}
				</c:if>
			}
			var addEmail_saveForm=function(){
				//发送
				$("#messageEmail-form-editEmail-sendflag").val("1");					
				$("#messageEmail-form-addEmail").form({
					url:'message/email/sendEmail.do',
	    			onSubmit: function(){		    				
	    				var flag = $(this).form('validate');
	    				if(flag==false){
	    					return false;
	    				}
						loading("邮件提交中，请稍后...");
	    			},
	    			success:function(data){
						loaded();
	    				//$.parseJSON()解析JSON字符串 
	    				var json=$.parseJSON(data);
	    				if(json.flag==true){
	    					$.messager.alert("提醒","发送成功!");   					
				        	$("#messageEmail-table-showEmailSendList").datagrid('reload');
				        	try{
				        		emailPage_openPanel('message/email/emailSendPreviewListPage.do','已发邮件箱');
				        	}catch(e){
				        	}
				        	var emlSendPageId=$.trim($("#messageEmail-form-addEmail-hid-emlSendPageId").val()||"");
				        	if(emlSendPageId==""){
								emlSendPageId="message-email-window-sendEmailOper";
							}
				        	var aWin=window;
    						if($("#"+emlSendPageId).size()>0){
    							aWin=window;
    						}else if(parent.$("#"+emlSendPageId).size()>0){
    							aWin=parent;
    						}else if(top.$("#"+emlSendPageId).size()>0){
    							aWin=top;
    						}
    						try{	    						   							
    							aWin.$("#"+emlSendPageId).window('close');
    						}catch(e){
    						}
    						try{
    							aWin.$("#"+emlSendPageId).dialog('close');
    						}catch(e){
    						}
	    				}
	    				else{
	    					$.messager.alert("提醒",( json.msg|| "发送失败！"));
	    				}
	    			},
					complete:function(xhr,s){
						loaded();
					}
	    		});	
			};
			
			var addEmail_saveDraftForm=function(){	
				//保存草稿
				$("#messageEmail-form-editEmail-sendflag").val("0");				
				$("#messageEmail-form-addEmail").form({
					url:'message/email/sendEmail.do',
	    			onSubmit: function(){
						loading("邮件保存中，请稍后...");
	    			},
	    			success:function(data){
						loaded();
	    				//$.parseJSON()解析JSON字符串 
	    				var json=$.parseJSON(data);
	    				if(json.flag==true){
	    					$.messager.alert("提醒","保存成功!");   					
				        	$("#messageEmail-table-showEmailSendList").datagrid('reload');
				        	try{
				        		emailPage_openPanel('message/email/emailDraftListPage.do','草稿箱');
				        	}catch(e){
				        	}
							var emlSendPageId=$.trim($("#messageEmail-form-addEmail-hid-emlSendPageId").val()||"");
							if(emlSendPageId==""){
								emlSendPageId="message-email-window-sendEmailOper";
							}				        	

				        	var aWin=window;
    						if($("#"+emlSendPageId).size()>0){
    							aWin=window;
    						}else if(parent.$("#"+emlSendPageId).size()>0){
    							aWin=parent;
    						}else if(top.$("#"+emlSendPageId).size()>0){
    							aWin=top;
    						}
    						try{	    						   							
    							aWin.$("#"+emlSendPageId).window('close');
    						}catch(e){
    						}
    						try{
    							aWin.$("#"+emlSendPageId).dialog('close');
    						}catch(e){
    						}
	    				}
	    				else{
	    					$.messager.alert("提醒",( json.msg|| "保存失败！"));
	    				}
	    			},
					complete:function(xhr,s){
						loaded();
					}
	    		});	
			};
			$(document).ready(function(){
				$("#messageEmail-form-addEmail-btn-showCopytouserDiv").click(function(){
					//添加抄送
					var $divcopytouser=$("#messageEmail-form-addEmail-div-copytouser");
					if($divcopytouser.css("display")=="none"){
						$(this).linkbutton({  
    						text: '隐藏抄送'  
						});
						$("#messageEmail-form-addEmail-iscopytouser").val("1");
						$divcopytouser.show();
					}else{
						$divcopytouser.hide();
						$(this).linkbutton({  
    						text: '添加抄送'  
						});
						$("#messageEmail-form-addEmail-iscopytouser").val("0");
					}
				});
				$("#messageEmail-form-addEmail-btn-showTowebmailDiv").click(function(){
					//外部邮件
					var $divtowebmail=$("#messageEmail-form-addEmail-div-towebmail");
					if($divtowebmail.css("display")=="none"){
						$divtowebmail.show();
						$("#messageEmail-form-addEmail-div-fromwebmail").css("display",'');
						$("#messageEmail-form-addEmail-webmailflag").val("1");
					}else{
						$divtowebmail.hide();
						$("#messageEmail-form-addEmail-div-fromwebmail").css("display",'none');
						$("#messageEmail-form-addEmail-webmailflag").val("0");
					}
				});
				$("#messageEmail-form-addEmail-btn-showSecretTouserDiv").click(function(){
					//添加密送
					var $divsecrettouser=$("#messageEmail-form-addEmail-div-secrettouser");
					if($divsecrettouser.css("display")=="none"){
						$(this).linkbutton({  
    						text: '隐藏密送'  
						});
						$divsecrettouser.show();
						$("#messageEmail-form-addEmail-issecrettouser").val("1");
					}else{
						$divsecrettouser.hide();
						$(this).linkbutton({  
    						text: '添加密送'  
						});
						$("#messageEmail-form-addEmail-issecrettouser").val("0");
					}
				});	
			});
			
			$(document).ready(function(){
				addEmail_showReplyContent();
				//初始化
				addEmail_initLoading();
				setTimeout(function(){
					addEmail_KEditor.fullscreen(true);
					addEmail_KEditor.fullscreen(false);
					addEmail_KEditor.focus();
				},250);
				
							
				
				$("#messageEmail-form-addEmail-btn-saveEmail").unbind("click").bind("click",function(){
					$.messager.confirm("提醒","是否发送此邮件?",function(r){
						if(r){  
							var curquesize=$("#attachment-upload-addclick").attr("curquesize");
	    					if(curquesize!=null && curquesize>0){	
	    						$.messager.alert("提醒","您有"+curquesize+"个附件未上传");

								$("#attachemt-upload-upfile").focus();
								$("#attachemt-upload-upfile").linkbutton('select');
								return false;
	    					} 					
	    					try{
		    					var tempd=$("#messageEmail-form-addEmail-webmailflag").val() || "";
		    					if(tempd==""){
		    						$("#messageEmail-form-addEmail-webmailflag").val("0");
		    					}
	    						if(tempd=="0" || tempd==""){
	    							
		    						if($.trim($("#messageEmail-form-addEmail-receiveuser").widget("getValue"))==""){
		    							$.messager.alert("提醒","请选择收件人");
		    							return false;
		    						}
	    						}
	    					  <%--	else{
	    							if($.trim($("#messageEmail-form-addEmail-receiveuser").val())=="" || $.trim($("#messageEmail-form-addEmail-towebmail").val())=="" ){	    								
		    							$.messager.alert("提醒","请选择收件人或者外部收件人");
		    							return false;
	    							}
	    							if($.trim($("#messageEmail-form-addEmail-fromwebmail").val())==""){
	    								$.messager.alert("提醒","请添加Internet邮箱");
		    							return false;
	    							}
	    						}
	    					   --%>
	    						tempd=$("#messageEmail-form-addEmail-iscopytouser").val()||"";
	    						if(tempd=="0" || tempd==""){
	    							$("#messageEmail-form-addEmail-copytouser").widget("clear");
	    						}
	    						tempd=$("#messageEmail-form-addEmail-issecrettouser").val() ||"";
	    						if(tempd=="0" || tempd==""){
	    							$("#messageEmail-form-addEmail-secrettouser").widget("clear");
	    						}	    						
	    					}catch(e){
	    					}
    						addEmail_saveForm();		    							
				    		$("#messageEmail-form-addEmail").submit();
	    				}
	    			});
				});
				$("#messageEmail-form-addEmail-btn-saveEmailDraft").unbind("click").bind("click",function(){						
						$.messager.confirm("提醒","是否保存此邮件到草稿箱?",function(r){
		    				if(r){
		    					var curquesize=$("#attachment-upload-addclick").attr("curquesize");
								if(curquesize!=null && curquesize>0){	
									$.messager.alert("提醒","您有"+curquesize+"个附件未上传，需要上传后保存吗?");									
									$("#attachemt-upload-upfile").focus();
									$("#attachemt-upload-upfile").linkbutton('select');
									return false;
								}
	    						addEmail_saveDraftForm();		    							
					    		$("#messageEmail-form-addEmail").submit();
		    				}
		    			});
				});
				$("#messageEmail-form-addEmail-fromwebmailid").combobox({
					url:'message/email/showCurUserWebMailConfigList.do',  
    				valueField:'id',  
    				textField:'email',
    				onSelect:function(record){
    					$("#messageEmail-form-addEmail-fromwebmail").val(record.email);
    				}  
				});
	    		$("#messageEmail-form-addEmail-emlcformat-cformat").change(function(){
		    		if($(this).attr("checked")){
						addEmail_showReplyContent();
		    		}
	    		});
	    		//收件人
	    		$("#messageEmail-form-addEmail-receiveuser").widget({
	    			name:'t_msg_emailcontent',
					col:'receiveuser',
	    			singleSelect:false,
	    			width:670,
	    			initValue:'${emailContent.receiveuser}',
	    			//param:[{field:'mobilephone', op:'notequal', value:''},{field:'isdept', op:'equal', value:'0'}],
	    			onlyLeafCheck:false,
	    			onChecked:function(data){
	    			},
	    			onClear:function(){
	    			}
	    		});
	    		//抄送人
	    		$("#messageEmail-form-addEmail-copytouser").widget({
	    			name:'t_msg_emailcontent',
					col:'copytouser',
	    			singleSelect:false,
	    			width:670,
	    			//param:[{field:'mobilephone', op:'notequal', value:''},{field:'isdept', op:'equal', value:'0'}],
	    			onlyLeafCheck:true
	    		});
	    		//密送人
	    		$("#messageEmail-form-addEmail-secrettouser").widget({
	    			name:'t_msg_emailcontent',
					col:'secrettouser',
	    			singleSelect:false,
	    			width:670,
	    			//param:[{field:'mobilephone', op:'notequal', value:''},{field:'isdept', op:'equal', value:'0'}],
	    			onlyLeafCheck:true
	    		});	
				setTimeout(function(){			    
				    
		    		$("#attachment-upload-addclick").webuploader({
						title: '附件上传',
		                filetype:'Files',
		                //allowType:"jrxml",
		                //mimeTypes:'text/*',
		                disableGlobalDnd:true,
		                width: 700,
		                height: 400,
		                url:'common/upload.do',
		                description:'',
		                close:true,
		                converthtml:true,
						onComplete: function(data){
					    	$("#messageEmail-form-addEmail-attachment-show-div").show();
					    	
					    	var files=$.trim($("#messageEmail-form-addEmail-attachment").val() || "");
					    	var filearr=new Array();
					    	if(files!=""){
						    	filearr=files.split(',');
					    	}
					    	for(var i=0;i<data.length;i++){
					    		if(data[i].id==null || data[i].id==""){
					    			continue;
					    		}
					    		filearr.push(data[i].id)

				    			var htmlsb=showAttachMenuContent(data[i],false);
							   	$("#messageEmail-form-addEmail-attachment-uplist").append(htmlsb);
					    	}
					    	$("#messageEmail-form-addEmail-attachment").val(filearr.join(","));	
						}
					});
				    
				}, 50);

				<c:if test="${emailContent.attach !=null}">
					renderAttachList('${emailContent.attach}');
    			</c:if>
    			<c:if test="${emailContent.attach ==null}">
    				renderAttachList('');
    			</c:if>
				$("#messageEmail-form-addEmail-emlcformat-cformat").click(function(){
					addEmail_showReplyContent();
				});
				$("#messageEmail-form-addEmail-emlcformat-corg").click(function(){
					addEmail_showReplyContent(1);
				});
			});
		</script>
  </body>
</html>
