<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>传阅件管理</title>    
		<%@include file="/include.jsp" %>
	</head>
  
  <body>
  	  	<div style="height:100%;margin:10px 20px; ">
				<form action="message/filedistrib/addFileDistrib.do" method="post" id="fileDistrib-form-fileDistribAddPage" opertype="add">
	  				<input type="hidden" id="fileDistrib-fileDistribAddPage-addType" name="addType"/>				
					<div style="hiehgt:30px;margin:10px 0;">
						<div style="float:left;width:100px;line-height:22px;">当前状态：</div>						
						<select class="easyui-combobox" style="width:100px;"  name="state" id="fileDistrib-form-noticePage-state" disabled="disabled">
				    		<c:forEach var="item" items="${statelist }">
    							<c:choose>
    								<c:when test="${item.code == 4}">
    									<option value="${item.code }" selected="selected">${item.codename }</option>
			    					</c:when>
			    					<c:otherwise>	    		
			    						<option value="${item.code }">${item.codename }</option>			    						
			    					</c:otherwise>
			    				</c:choose>
				    		</c:forEach>
		    			</select>
						<div style="clear:both"></div>
					</div>
					<div style="hiehgt:30px;">
						<div style="float:left;width:100px;line-height:22px;">内容格式：</div>
						<select id="fileDistrib-form-fileDistribAddPage-type" name="fileDistrib.type" style="width:100px;">
							<option value="0">内容显示</option>
							<option value="1">文档显示</option>
							<%--<option value="2">表单显示</option>--%>
						</select>
						<div style="clear:both"></div>
					</div>
					<div style="hiehgt:30px;margin-bottom:4px;">
						<div style="float:left;width:100px;line-height:22px;">标题：</div>
						<div style="float:left;height:16px;padding-top:3px;">
							<input type="text" id="fileDistrib-form-fileDistribAddPage-title" name="fileDistrib.title" class="easyui-validatebox" required="true" style="width:350px;"/>
						</div>
						<div style="clear:both"></div>
					</div>
					<div style="hiehgt:30px;margin-bottom:4px;">
						<div style="float:left;width:100px;line-height:22px;">主题词：</div>
						<div style="float:left;height:16px;padding-top:3px;">
							<input type="text" id="fileDistrib-form-fileDistribAddPage-titleword" name="fileDistrib.titleword" class="easyui-validatebox" required="true" style="width:350px;"/>
						</div>
						<div style="clear:both"></div>
					</div>
					<div style="hiehgt:30px;margin-bottom:5px;">
						<div style="float:left;width:100px;line-height:22px;">字号：</div>
						<div style="float:left;height:16px;padding-top:3px;">
							<input type="text" id="fileDistrib-form-fileDistribAddPage-wordsize" name="fileDistrib.wordsize" class="easyui-validatebox" required="true" style="width:350px;"/>
						</div>
						<div style="clear:both"></div>
					</div>
					<div style="height:2px;"></div>
					<div style="height:auto;margin-bottom: 5px;">
						<div style="float:left;width:100px;line-height:22px;">按部门发布：</div>
						<div id="fileDistrib-form-fileDistribAddPage-recvdeptname-div" style="float:left;">
							<input id="fileDistrib-form-fileDistribAddPage-recvdeptname"  name="recvdeptname" />
		    			</div>
			    		<input type="hidden" name="fileDistrib.receivedept" id="fileDistrib-form-fileDistribAddPage-receivedept" />
		    			<div style="clear:both"></div>
					</div>
					<div style="height:auto;margin-bottom: 5px;">
						<div style="float:left;width:100px;line-height:22px;">按角色发布：</div>							
						<div id="fileDistrib-form-fileDistribAddPage-recvrolename-div" style="float:left;">
							<input id="fileDistrib-form-fileDistribAddPage-recvrolename"  name="recvrolename" />
							<div style="clear:both"></div>
		    			</div>		    			
		    			<div style="float:left;">
		    				<input type="text"  id="fileDistrib-form-fileDistribAddPage-recvallrolename" readonly="readonly" style="display:none;width:380px;" value="所有角色" />	    			
			    			&nbsp;<a href="javaScript:void(0);" id="fileDistrib-form-fileDistribAddPage-btn-addRole" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-commonquery'">选择全部</a>
		    			</div>
			    		<input type="hidden" name="fileDistrib.receiverole" id="fileDistrib-form-fileDistribAddPage-receiverole" />
		    			<div style="clear:both"></div>
					</div>
					<div style="height:auto;margin-bottom: 5px;">
						<div style="float:left;width:100px;line-height:22px;">按人员发布：</div>						
						<div style="float:left;">
							<input id="fileDistrib-form-fileDistribAddPage-recvusername"  name="recvusername"  />
		    			</div>
			    		<input type="hidden" name="fileDistrib.receiveuser" id="fileDistrib-form-fileDistribAddPage-receiveuser"/>
		    			<div style="clear:both"></div>
					</div>
					<div style="height:35px;line-height:30px;">
						<div style="float:left;width:100px;line-height:22px;">有效期：</div>
						<div style="float:left">
							<input type="text" style="width:80px" name="fileDistrib.startdate" value="${today}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>至<input type="text" style="width:80px" name="fileDistrib.enddate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>&nbsp;为空为手动终止
						</div>
						<div style="clear:both"></div>
					</div>					
					<div style="height:30px;">
						<div style="float:left;width:100px;line-height:22px;"> 短信提醒：</div>
						<div style="float:left">
		    				<input type="checkbox" id="fileDistrib-form-fileDistribAddPage-ismsg" name="fileDistrib.ismsg" value="1" />使用内部短信提醒 &nbsp;
		    			</div>
						<div style="clear:both"></div>
					</div>
					<div style="height:30px;">
						<div style="float:left;width:100px;line-height:22px;">置顶：</div>
						<div style="float:left">
		    				<input type="checkbox" id="fileDistrib-form-fileDistribAddPage-istop" name="fileDistrib.istop" value="1" />置顶，显示为重要&nbsp;
		    				<input type="text" style="width:50px;" name="fileDistrib.topday" value="0"/>天后结束置顶，0表示一直置顶
		    			</div>
						<div style="clear:both"></div>
					</div>		
					<div id="fileDistrib-form-fileDistribAddPage-cfile-div" style="display:none;min-height:30px;margin-bottom:5px;">
						<div style="float:left;width:100px;line-height:22px;">文档：</div>						
						<div style="float:left">
							<div id="fileDistrib-form-fileDistribAddPage-cfile-show-div" style="display:none;float:left; margin-bottom:5px;">
								<div id="fileDistrib-form-fileDistribAddPage-cfile-uplist">
								</div>
			    			</div>
			    			<div>
								<a href="javascript:void(0);" id="fileDistrib-form-fileDistribAddPage-cfile-addclick" class="easyui-linkbutton" data-options="plain:false,iconCls:'icon-extend-uploadfile'">+添加文档</a>
							</div>
		    			</div>
						<div style="clear:both"></div>
						<input type="hidden" id="fileDistrib-form-fileDistribAddPage-cfile"  name="cfile"  />
						<input type="hidden" id="fileDistrib-form-fileDistribAddPage-cfile-delete"  name="cfiledelete" />
					</div>
					<div id="fileDistrib-form-fileDistribAddPage-attachment-div" style="min-height:30px;margin-bottom:5px;">
						<div style="float:left;width:100px;line-height:22px;">附件：</div>						
						<div style="float:left">
							<div id="fileDistrib-form-fileDistribAddPage-attachment-show-div" style="display:none;float:left; margin-bottom:5px;">
								<div id="fileDistrib-form-fileDistribAddPage-attachment-uplist">
								</div>
			    			</div>
			    			<div>
								<a href="javascript:void(0);" id="attachment-addNotice-upload-addclick" class="easyui-linkbutton" data-options="plain:false,iconCls:'icon-extend-uploadfile'">+添加附件</a>
							</div>
		    			</div>
						<div style="clear:both"></div>
						<input type="hidden" id="fileDistrib-form-fileDistribAddPage-attachment"  name="fileDistrib.attach" value="${fileDistrib.attach }" />
						<input type="hidden" id="fileDistrib-form-fileDistribAddPage-attachment-delete"  name="attachdelete" />
					</div>
					<div id="fileDistrib-form-fileDistribAddPage-content-div" style="margin-bottom: 5px;">
						<textarea id="fileDistrib-form-fileDistribAddPage-content" name="content" rows="0" cols="0" style="width:90%;height:400px;"></textarea>
					</div>	
					<input type="hidden" id="fileDistrib-form-fileDistribAddPage-hid-content" name="fileDistrib.content"/> 
					<div style="clear:both"></div>
				</form>
				<br/>
				<div style="clear:both"></div>
		</div>			
		<div id="User-window-userChooser" class="easyui-dialog" closed="true"></div>
		<script type="text/javascript">
			$(document).ready(function(){

		  		$("#fileDistrib-buttons-fileDistribPage").buttonWidget("initButtonType", 'add');
				var fdKEditor=KindEditor.create('#fileDistrib-form-fileDistribAddPage-content',{
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

				//部门 
	    	  	$("#fileDistrib-form-fileDistribAddPage-recvdeptname").widget({
	    			name:'t_msg_filedistrib',
					col:'receivedept',
	    			width:350,
	    			singleSelect:false,
	    			onlyLeafCheck:false,
	    			onChecked:function(data){
	    				$("#fileDistrib-form-fileDistribAddPage-receivedept").val($(this).widget('getValue'));
	    			},
	    			onSelectAll:function(){
		    			$("#fileDistrib-form-fileDistribAddPage-recvdeptname").val("所有部门");
		    			$("#fileDistrib-form-fileDistribAddPage-receivedept").val("ALL");
	    			},
	    			onClear:function(){
	    				$("#fileDistrib-form-fileDistribAddPage-receivedeptname").val("");
	    				$("#fileDistrib-form-fileDistribAddPage-receivedept").val("");
	    			}
	    		}); 
	    		//角色 
	    	  	$("#fileDistrib-form-fileDistribAddPage-recvrolename").widget({
	    			name:'t_msg_filedistrib',
					col:'receiverole',
	    			width:350,
	    			singleSelect:false,
	    			onlyLeafCheck:true,
	    			onSelect:function(data){
	    				$("#fileDistrib-form-fileDistribAddPage-receiverole").val($(this).widget('getValue'));
	    			},
	    			onUnselect:function(data){
	    				$("#fileDistrib-form-fileDistribAddPage-receiverole").val($(this).widget('getValue'));
	    			},
	    			onClear:function(){
	    				$("#fileDistrib-form-fileDistribAddPage-receiverole").val("");
	    			}
	    		}); 

				//角色全部
	    		$("#fileDistrib-form-fileDistribAddPage-btn-addRole").click(function(){
		    		var state=$(this).attr("state")||"";
		    		if(state==1){
						$(this).attr("state","");
						$(this).linkbutton({text:'选择全部'});
			    		$("#fileDistrib-form-fileDistribAddPage-recvrolename-div").show();
			    		$("#fileDistrib-form-fileDistribAddPage-recvallrolename").hide();
		    			$("#fileDistrib-form-fileDistribAddPage-receiverole").val($("#fileDistrib-form-fileDistribAddPage-recvrolename").widget('getValue')||"");
		    		}else{
		    			$(this).attr("state","1");
						$(this).linkbutton({text:'选择角色'});
			    		$("#fileDistrib-form-fileDistribAddPage-recvrolename-div").hide();
			    		$("#fileDistrib-form-fileDistribAddPage-recvallrolename").show();
		    			$("#fileDistrib-form-fileDistribAddPage-receiverole").val("ALL");
		    		}
	    		});
	    	  //按人员
	    		$("#fileDistrib-form-fileDistribAddPage-recvusername").widget({
	    			name:'t_msg_filedistrib',
					col:'receiveuser',
	    			singleSelect:false,
	    			width:350,
	    			//param:[{field:'userid', op:'notequal', value:'${user.userid}'}],
	    			onlyLeafCheck:false,
	    			onChecked:function(data){
	    				$("#fileDistrib-form-fileDistribAddPage-receiveuser").val($(this).widget('getValue'));
	    			},
	    			onSelectAll:function(){
		    			$("#fileDistrib-form-fileDistribAddPage-recvusername").val("所有人员");
		    			$("#fileDistrib-form-fileDistribAddPage-receiveuser").val("ALL");
	    			},
	    			onClear:function(){
	    				$("#fileDistrib-form-fileDistribAddPage-recvusername").val("");
	    				$("#fileDistrib-form-fileDistribAddPage-receiveuser").val("");
	    			}
	    		});
	    		$("#fileDistrib-form-fileDistribAddPage-type").change(function(){
		    		var data=$(this).val()||"";
		    		if(data=="0"){
			    		$("#fileDistrib-form-fileDistribAddPage-content-div").show();
			    		$("#fileDistrib-form-fileDistribAddPage-cfile-div").hide();
		    		}else if(data=="1"){
			    		$("#fileDistrib-form-fileDistribAddPage-content-div").hide();
			    		$("#fileDistrib-form-fileDistribAddPage-cfile-div").show();			    		
		    		}else {
			    		return false;
		    		}
	    		});
	    		$("#attachment-addNotice-upload-addclick").webuploader({
					title: '附件上传',
	                filetype:'Files',
	                allowType:"txt,doc,docx,xls,xlsx,ppt,pptx,zip,rar,7z",
	                mimeTypes:'*/*',
	                disableGlobalDnd:true,
	                width: 700,
	                height: 400,
	                url:'common/upload.do',
	                description:'支持附件上传格式：txt,doc,docx,xls,xlsx,ppt,pptx,zip,rar,7z',
	                close:true,
					onComplete: function(data){
				    	$("#fileDistrib-form-fileDistribAddPage-attachment-show-div").show();
				    	
				    	var files=$.trim($("#fileDistrib-form-fileDistribAddPage-attachment").val() || "");
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
						   	$("#fileDistrib-form-fileDistribAddPage-attachment-uplist").append(htmlsb);
				    	}
				    	$("#fileDistrib-form-fileDistribAddPage-attachment").val(filearr.join(","));	
					}
				});

	    		$("#fileDistrib-form-fileDistribAddPage-cfile-addclick").webuploader({
					title: '文档上传',
	                filetype:'Files',
	                allowType:"txt,pdf,doc,docx,xls,xlsx,ppt,pptx",
	                mimeTypes:'*/*',
	                disableGlobalDnd:true,
	                width: 700,
	                height: 400,
	                fileNumLimit:1,
	                url:'common/upload.do',
	                description:'支持文档上传格式：txt,pdf,doc,docx,xls,xlsx,ppt,pptx',
	                close:true,
					onComplete: function(data){
				    	$("#fileDistrib-form-fileDistribAddPage-cfile-show-div").show();
				    	
				    	for(var i=0;i<data.length;i++){
				    		if(data[i].id==null || data[i].id==""){
				    			continue;
				    		}
				    		$("#fileDistrib-form-fileDistribAddPage-cfile").val(data[i].id);
						   	var htmlsb=showDocAttachMenuContent(data[i],false);
						   	$("#fileDistrib-form-fileDistribAddPage-cfile-uplist").append(htmlsb);
				    	}
					}
				});
			});
		</script>
  </body>
  
</html>
