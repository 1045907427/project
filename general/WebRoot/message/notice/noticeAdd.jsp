<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>公告新增</title>    
		<%@include file="/include.jsp" %> 	
	  	<script type="text/javascript" src="js/kindeditor/kindeditor-min.js"></script> 
	  	<script type="text/javascript" src="js/kindeditor/keconfig.js"></script> 
	  	<script type="text/javascript" src="js/kindeditor/lang/zh_CN.js"></script>
		<link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>
	  	<script type="text/javascript" src="js/colorPicker/jquery.colorPicker.min.js"></script>
	  	<link rel="stylesheet" href="js/colorPicker/colorPicker.css" type="text/css" />
        <style>
            .checkbox1{
                float:left;
                height: 22px;
                line-height: 22px;
            }
            .divtext{
                height:22px;
                line-height:22px;
                float:left;
                display: block;
            }
        </style>
	</head>

	<body>		
		<div style="height:100%;padding:0px 20px;">
				<form action="message/notice/noticeAdd.do" method="post" id="messageNotice-form-addNotice">	
					<input type="hidden" id="notice-noticeAddPage-addType" name="addType"/>			
					<div style="margin:3px 0px;">
						<div style="float:left;width:100px;line-height:22px;">当前状态：</div>						
						<select name="state" style="width: 45px" id="messageNotice-form-noticePage-state" disabled="disabled">
				    		<c:forEach var="item" items="${statelist }">
				    			<c:choose>
				    				<c:when test="${item.code == '4' }">
				    					<option value="${item.code }" selected="selected">${item.codename }</option>
				    				</c:when>
				    				<c:otherwise>
				    					<option value="${item.code }">${item.codename}</option>
				    				</c:otherwise>
			    				</c:choose>
				    		</c:forEach>
		    			</select>
						<div style="clear:both"></div>
					</div>
					<%--<div style="hiehgt:30px;">--%>
						<%--<div style="float:left;width:100px;line-height:22px;padding-bottom: 6px">选择格式：</div>--%>
						<%--<select id="messageNotice-form-addNotice-form" name="msgNotice.form">--%>
							<%--<option value="1">普通格式</option>--%>
							<%--<option value="2">MHT格式</option>--%>
							<%--<option value="3">超级链接</option>--%>
						<%--</select>--%>
						<%--<div style="clear:both"></div>--%>
					<%--</div>--%>
					<%--<div style="hiehgt:30px;">--%>
						<%--<div style="float:left;width:100px;line-height:22px;padding-bottom: 6px">公告分类：</div>--%>
						<%--<select name="msgNotice.type">--%>
							<%--<option value="1">无类型</option>--%>
						<%--</select>--%>
						<%--<div style="clear:both"></div>--%>
					<%--</div>--%>
					<div style="hiehgt:30px;padding-bottom:4px;">
						<div style="float:left;width:100px;line-height:22px;">标题：</div>
						<div style="float:left">
							<input type="text" id="messageNotice-form-addNotice-title" name="msgNotice.title" class="easyui-validatebox" required="true" style="width:400px;"/>
							<label id="messageNotice-form-addNotice-tcolorshow">&nbsp;设置标题颜色</label>
						</div>
						<div style="float:left;height:16px;padding-top:3px;">
							<input type="hidden" id="messageNotice-form-addNotice-tcolor" name="msgNotice.tcolor"/>
						</div>
						<div style="clear:both"></div>
					</div>
					<div style="height:auto;margin-bottom: 5px;">
						<div style="float:left;width:100px;line-height:22px;">按部门发布：</div>
						<div id="messageNotice-form-addNotice-recvdeptname-div" style="float:left;">
							<input id="messageNotice-form-addNotice-recvdeptname"  name="recvdeptname" />
		    			</div>
			    		<input type="hidden" name="msgNotice.receivedept" id="messageNotice-form-addNotice-receivedept" />
		    			<div style="clear:both"></div>
					</div>
					<div style="height:auto;margin-bottom: 5px;">
						<div style="float:left;width:100px;line-height:22px;">按角色发布：</div>							
						<div id="messageNotice-form-addNotice-recvrolename-div" style="float:left;">
							<input id="messageNotice-form-addNotice-recvrolename"  name="recvrolename" />
		    			</div>		    			
		    			<div style="float:left;">
		    				<input type="text"  id="messageNotice-form-addNotice-recvallrolename" readonly="readonly" style="display:none;width:400px;" value="所有角色" />
			    			&nbsp;<a href="javaScript:void(0);" id="messageNotice-form-addNotice-btn-addRole" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">选择全部</a>
		    			</div>
			    		<input type="hidden" name="msgNotice.receiverole" id="messageNotice-form-addNotice-receiverole" />
		    			<div style="clear:both"></div>
					</div>
					<div style="height:auto;margin-bottom: 5px;">
						<div style="float:left;width:100px;line-height:22px;">按人员发布：</div>						
						<div style="float:left;">
							<input id="messageNotice-form-addNotice-recvusername"  name="recvusername"  />
		    			</div>
			    		<input type="hidden" name="msgNotice.receiveuser" id="messageNotice-form-addNotice-receiveuser"/>
		    			<div style="clear:both"></div>
					</div>
					<%--<div id="messageNotice-form-addNotice-url-div" style="display:none; height:35px;line-height:30px;">--%>
						<%--<div style="float:left;width:100px;line-height:22px;">超级链接地址：</div>--%>
						<%--<div style="float:left">--%>
							<%--<input type="text" style="width:400px;" name="msgNotice.url" value="http://" />--%>
						<%--</div>--%>
						<%--<div style="clear:both"></div>--%>
					<%--</div>--%>
					<div style="height:35px;line-height:30px;">
						<div style="float:left;width:100px;line-height:22px;">有效期：</div>
						<div style="float:left">
							<input type="text" style="width:80px" name="msgNotice.startdate" value="${today}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>至<input type="text" style="width:80px" name="msgNotice.enddate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>&nbsp;为空为手动终止
						</div>
						<div style="clear:both"></div>
					</div>					
					<div style="height:30px;">
						<div style="float:left;width:100px;line-height:22px;"> 短信提醒：</div>
						<div style="float:left">
                            <lable class="checkbox1"><input type="checkbox" class="divtext" id="messageNotice-form-addNotice-ismsg" name="msgNotice.ismsg" value="1" />使用内部短信提醒 &nbsp;</lable>
		    			</div>
						<div style="clear:both"></div>
					</div>
					<div style="height:30px;">
						<div style="float:left;width:100px;line-height:22px;">置顶：</div>
						<div style="float:left">
                            <lable class="checkbox1"><input type="checkbox" class="divtext" id="messageNotice-form-addNotice-istop" name="msgNotice.istop" value="1" />使公告通知置顶，显示为重要&nbsp;</lable>
		    				<input type="text" style="width:50px;" name="msgNotice.topday" value="0"/>天后结束置顶，0表示一直置顶
		    			</div>
						<div style="clear:both"></div>
					</div>					
					<div id="messageNotice-form-addNotice-attachment-show-div" style="display:none;margin-top:10px;margin-bottom: 10px;">
						<div style="float:left;width:100px;line-height:22px;">附件：</div>
						<div style="float:left">
							<div id="messageNotice-form-addNotice-attachment-uplist">
							</div>
		    			</div>
						<div style="clear:both"></div>
					</div>
					<div id="messageNotice-form-addNotice-attachment-div" style="margin-top:5px;margin-bottom: 5px;">
						<div style="float:left;width:100px;line-height:22px;">附件选择：</div>
						<div style="float:left">
							<a href="javascript:void(0);" id="attachment-addNotice-upload-addclick" class="easyui-linkbutton" data-options="plain:false,iconCls:'icon-extend-uploadfile'">+添加附件</a>
		    			</div>
						<div style="clear:both"></div>
						<input type="hidden" id="messageNotice-form-addNotice-attachment"  name="msgNotice.attach" value="${msgNotice.attach }" />
						<input type="hidden" id="messageNotice-form-addNotice-attachment-delete"  name="attachdelete" />
					</div>
					<%--<div style="height:30px;">--%>
						<%--<div style="float:left;width:100px;line-height:22px;">内容简介：</div>--%>
						<%--<div style="float:left">--%>
							<%--<input type="text" style="width:400px;" name="msgNotice.intro" />--%>
						<%--</div>--%>
						<%--<div style="clear:both"></div>--%>
					<%--</div>--%>
					<div id="messageNotice-form-addNotice-content-div">
						<textarea id="messageNotice-form-addNotice-content" name="msgNotice.content" rows="0" cols="0" style="width:90%;height:400px;"></textarea>
					</div>						
					<div style="height:30px;margin-top:5px;">
						<%--<div style="float:left;width:100px;line-height:22px;">关键词：</div>--%>
						<%--<div style="float:left">--%>
							<%--<input type="text" style="width:400px;" name="msgNotice.keyword"/>  (您可以调整关键词内容，多个关键词请用,分隔)--%>
						<%--</div>--%>
						<div style="clear:both"></div>
					</div>
					<div style="clear:both"></div>
				</form>
		</div>			
		<div id="User-window-userChooser" class="easyui-dialog" closed="true"></div>
		<script type="text/javascript">

			function checkBeforeTempSave(){
				try{
					var fval=$("#messageNotice-form-addNotice-form").val()||"1";
					if(fval==2){	    							
						noticeKEditor.html('');
					}else if(fval==3){	    							
						noticeKEditor.html('');
						$("#messageNotice-form-addNotice-attachment").val("0");
					}else{
						$("#messageNotice-form-addNotice-url").val("");
					}	    						
				}catch(e){
				}
			}
			function checkBeforeSave(){
				try{
					if($.trim($("#messageNotice-form-addNotice-title").val())==""){	    							
						$.messager.alert("提醒","请填写标题!");  
						$("#messageNotice-form-addNotice-title").focus();
						return;
					}
					var fval=$("#messageNotice-form-addNotice-form").val()||"1";
					if(fval==2){	    							
						noticeKEditor.html('');
					}else if(fval==3){	    							
						noticeKEditor.html('');
						$("#messageNotice-form-addNotice-attachment").val("0");
					}else{
						$("#messageNotice-form-addNotice-url").val("");
					}	    						
				}catch(e){
				}
			}
			 
			$(document).ready(function(){

		  		$("#notice-buttons-noticePage").buttonWidget("initButtonType", 'add');
				
			    var noticeKEditor=KindEditor.create('#messageNotice-form-addNotice-content',{
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
	    		$("#messageNotice-form-addNotice-form").change(function(){
	    			var val=$(this).val()||"1";
	    			if(val==2){
	    				$("#messageNotice-form-addNotice-attachment-div").css("display","");
	    				$("#messageNotice-form-addNotice-content-div").css("display","none");
	    				$("#messageNotice-form-addNotice-url-div").css("display","none");
	    			}else if(val==3){
	    				$("#messageNotice-form-addNotice-url-div").css("display","");
	    				$("#messageNotice-form-addNotice-content-div").css("display","none");
	    				$("#messageNotice-form-addNotice-attachment-div").css("display","none");
	    			}else{
	    				$("#messageNotice-form-addNotice-content-div").css("display","");
	    				$("#messageNotice-form-addNotice-attachment-div").css("display","");
	    				$("#messageNotice-form-addNotice-url-div").css("display","none");
	    			}
	    		});
	    		
	    		$("#messageNotice-save-noticePage").unbind("click").bind("click",function(){
		    		var isBtnOk=false;
		    		try{
		    			isBtnOk=$(this).linkbutton('options').disabled;
		    		}catch(e){
		    		}
	    			if(isBtnOk){
						return false;
					}
	    			$.messager.confirm("提醒","是否保存此条通知通告信息?",function(r){
	    				if(r){	    					
	    					
    						messageNotice.AddNotice.saveForm();		    							
				    		$("#messageNotice-form-addNotice").submit();
	    				}
	    			});
	    		});
	    		$("#messageNotice-tempsave-noticePage").unbind("click").bind("click",function(){
		    		var isBtnOk=false;
		    		try{
		    			isBtnOk=$(this).linkbutton('options').disabled;
		    		}catch(e){
		    		}
	    			if(isBtnOk){
						return false;
					}	    			
	    			$.messager.confirm("提醒","是否暂存此条通知通告信息?",function(r){
	    				if(r){	    					
	    					
    						messageNotice.AddNotice.holdForm();	
					    	$("#messageNotice-form-addNotice").submit();
	    				}
	    			});	    			
	    		});
	    		$("#messageNotice-form-addNotice-tcolor").colorPicker({
	    			pickerDefault: "000000",
	    			onColorChange:function(e,color){
	    				$("#messageNotice-form-addNotice-tcolorshow").css("color",color);
	    			}
	    		});
	    		//部门
	    	  	$("#messageNotice-form-addNotice-recvdeptname").widget({
	    			name:'t_msg_notice',
					col:'receivedept',
	    			width:400,
	    			singleSelect:false,
	    			onlyLeafCheck:false,
	    			onSelect:function(data){
	    				$("#messageNotice-form-addNotice-receivedept").val($(this).widget('getValue'));
	    			},
	    			onUnselect:function(data){
	    				$("#messageNotice-form-addNotice-receivedept").val($(this).widget('getValue'));
	    			},
	    			onClear:function(){
	    				$("#messageNotice-form-addNotice-receivedept").val("");
	    			},
	    			onLoadSuccess:function(){
	    				var temp=$.trim($("#messageNotice-form-addNotice-receivedept").val()||"");
	    			}
	    		}); 
	    		//角色 
	    	  	$("#messageNotice-form-addNotice-recvrolename").widget({
	    			name:'t_msg_notice',
					col:'receiverole',
	    			width:400,
	    			singleSelect:false,
	    			onlyLeafCheck:true,
	    			onSelect:function(data){
	    				$("#messageNotice-form-addNotice-receiverole").val($(this).widget('getValue'));
	    			},
	    			onUnselect:function(data){
	    				$("#messageNotice-form-addNotice-receiverole").val($(this).widget('getValue'));
	    			},
	    			onClear:function(){
	    				$("#messageNotice-form-addNotice-receiverole").val("");
	    			}
	    		}); 
	    	  //按人员
	    		$("#messageNotice-form-addNotice-recvusername").widget({
	    			name:'t_msg_notice',
					col:'receiveuser',
	    			singleSelect:false,
	    			width:400,
	    			//param:[{field:'userid', op:'notequal', value:'${user.userid}'}],
	    			onlyLeafCheck:false,
	    			onChecked:function(data){
	    				$("#messageNotice-form-addNotice-receiveuser").val($(this).widget('getValue'));
	    			},
	    			onSelectAll:function(){
		    			$("#messageNotice-form-addNotice-recvusername").val("所有人员");
		    			$("#messageNotice-form-addNotice-receiveuser").val("ALL");
	    			},
	    			onClear:function(){
	    				$("#messageNotice-form-addNotice-recvusername").val("");
	    				$("#messageNotice-form-addNotice-receiveuser").val("");
	    			}
	    		});
				//角色全部
	    		$("#messageNotice-form-addNotice-btn-addRole").click(function(){
		    		var state=$(this).attr("state")||"";
		    		if(state==1){
						$(this).attr("state","");
						$(this).linkbutton({text:'选择全部'});
			    		$("#messageNotice-form-addNotice-recvrolename-div").show();
			    		$("#messageNotice-form-addNotice-recvallrolename").hide();
		    			$("#messageNotice-form-addNotice-receiverole").val($("#messageNotice-form-addNotice-recvrolename").widget('getValue')||"");
		    		}else{
		    			$(this).attr("state","1");
						$(this).linkbutton({text:'选择角色'});
			    		$("#messageNotice-form-addNotice-recvrolename-div").hide();
			    		$("#messageNotice-form-addNotice-recvallrolename").show();
		    			$("#messageNotice-form-addNotice-receiverole").val("ALL");
		    		}
	    		});
	    		
	    		$("#attachment-addNotice-upload-addclick").webuploader({
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
				    	$("#messageNotice-form-addNotice-attachment-show-div").show();
				    	
				    	var files=$.trim($("#messageNotice-form-addNotice-attachment").val() || "");
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
						   	$("#messageNotice-form-addNotice-attachment-uplist").append(htmlsb);
				    	}
				    	$("#messageNotice-form-addNotice-attachment").val(filearr.join(","));	
					}
				});
			});
		</script>
	</body>
</html>
