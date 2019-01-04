<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>传阅件</title>    
		<%@include file="/include.jsp" %>
	</head>
  
  <body>
  	  	<div style="height:100%;margin:10px 20px; ">
				<form action="#" method="post" id="fileDistrib-form-fileDistribAddPage">
	  				<input type="hidden" id="fileDistrib-fileDistribAddPage-addType" name="addType"/>				
					<div style="hiehgt:30px;margin:10px 0;">
						<div style="float:left;width:100px;line-height:22px;">当前状态：</div>						
						<select class="easyui-combobox" style="width:100px;"  name="fileDistrib.state" id="fileDistrib-form-noticePage-state" disabled="disabled">
				    		<c:forEach var="item" items="${statelist }">
    							<c:choose>
    								<c:when test="${item.code == fileDistrib.state}">
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
						<select name="fileDistrib.type" disabled="disabled">
							<option value="0" <c:if test="${fileDistrib.type=='0' }">selected="selected"</c:if> >内容显示</option>
							<option value="1" <c:if test="${fileDistrib.type=='1' }">selected="selected"</c:if> >文档显示</option>
							<%--<option value="2" <c:if test="${fileDistrib.type=='2' }">selected="selected"</c:if> >表单显示</option>--%>
						</select>
						<div style="clear:both"></div>
					</div>
					<div style="hiehgt:30px;padding-bottom:4px;">
						<div style="float:left;width:100px;line-height:22px;">标题：</div>
						<div style="float:left;height:16px;padding-top:3px;">
							<input type="text" id="fileDistrib-form-fileDistribAddPage-title" name="fileDistrib.title" value="${fileDistrib.title }" style="width:350px;" readonly="readonly"/>
						</div>
						<div style="clear:both"></div>
					</div>
					<div style="hiehgt:30px;padding-bottom:4px;">
						<div style="float:left;width:100px;line-height:22px;">主题词：</div>
						<div style="float:left;height:16px;padding-top:3px;">
							<input type="text" id="fileDistrib-form-fileDistribAddPage-titleword" name="fileDistrib.titleword" value="${fileDistrib.titleword }" style="width:350px;" readonly="readonly"/>
						</div>
						<div style="clear:both"></div>
					</div>
					<div style="hiehgt:30px;padding-bottom:4px;">
						<div style="float:left;width:100px;line-height:22px;">字号：</div>
						<div style="float:left;height:16px;padding-top:3px;">
							<input type="text" id="fileDistrib-form-fileDistribAddPage-wordsize" name="fileDistrib.wordsize" value="${fileDistrib.wordsize }" style="width:350px;" readonly="readonly"/>
						</div>
						<div style="clear:both"></div>
					</div>
					<div style="height:2px;"></div>
					<div style="height:auto;margin-bottom: 5px;">
						<div style="float:left;width:100px;line-height:22px;">按部门发布：</div>
						<div id="fileDistrib-form-fileDistribAddPage-recvdeptname-div" style="float:left;">
							<input id="fileDistrib-form-fileDistribAddPage-recvdeptname"  name="recvdeptname" value="${fileDistrib.receivedept }" />
		    			</div>	
			    		<input type="hidden" name="fileDistrib.receivedept" id="fileDistrib-form-fileDistribAddPage-receivedept" value="${fileDistrib.receivedept }" />
		    			<div style="clear:both"></div>
					</div>
					<div style="height:auto;margin-bottom: 5px;">
						<div style="float:left;width:100px;line-height:22px;">按角色发布：</div>							
						<div id="fileDistrib-form-fileDistribAddPage-recvrolename-div" style="float:left;">
							<input id="fileDistrib-form-fileDistribAddPage-recvrolename"  name="recvrolename" value="${fileDistrib.receiverole }"/>
		    			</div>
		    			<div id="fileDistrib-form-fileDistribAddPage-recvrolename-alldiv" style="float:left; display: none;">
		    				<input type="text"  id="messageNotice-form-addNotice-recvallrolename" readonly="readonly" style="display:none;width:380px;" value="所有角色" />
		    			</div> 
			    		<input type="hidden" name="fileDistrib.receiverole" id="fileDistrib-form-fileDistribAddPage-receiverole" value="${fileDistrib.receiverole }" />
		    			<div style="clear:both"></div>
					</div>
					<div style="height:auto;margin-bottom: 5px;">
						<div style="float:left;width:100px;line-height:22px;">按人员发布：</div>						
						<div style="float:left;">
							<input id="fileDistrib-form-fileDistribAddPage-recvusername"  name="recvusername" value="${fileDistrib.receiveuser }" />
		    			</div>
			    		<input type="hidden" name="fileDistrib.receiveuser" id="fileDistrib-form-fileDistribAddPage-receiveuser" value="${fileDistrib.receiveuser }"/>
		    			<div style="clear:both"></div>
					</div>
					<div style="height:35px;line-height:30px;">
						<div style="float:left;width:100px;line-height:22px;">有效期：</div>
						<div style="float:left">
							<input type="text" style="width:80px" name="fileDistrib.startdate" value="${fileDistrib.startdate }" readonly="readonly"/>至<input type="text" style="width:80px" name="fileDistrib.enddate" readonly="readonly"/>&nbsp;为空为手动终止
						</div>
						<div style="clear:both"></div>
					</div>					
					<input type="hidden" id="fileDistrib-form-fileDistribAddPage-attachment" name="fileDistrib.attach" value="${fileDistrib.attach }" />
					<div style="height:30px;">
						<div style="float:left;width:100px;line-height:22px;"> 短信提醒：</div>
						<div style="float:left">
		    				<input type="checkbox" id="fileDistrib-form-fileDistribAddPage-ismsg" name="fileDistrib.ismsg" value="1" <c:if test="${fileDistrib.ismsg==1 }" > checked="checked" </c:if> disabled="disabled"/>使用内部短信提醒 &nbsp;
		    			</div>
						<div style="clear:both"></div>
					</div>
					<div style="height:30px;">
						<div style="float:left;width:100px;line-height:22px;">置顶：</div>
						<div style="float:left">
		    				<input type="checkbox" id="fileDistrib-form-fileDistribAddPage-istop" name="fileDistrib.istop" value="1" <c:if test="${fileDistrib.istop==1 }" > checked="checked" </c:if> disabled="disabled"/>置顶，显示为重要&nbsp;
		    				<input type="text" style="width:50px;" name="fileDistrib.topday" value="${fileDistrib.topday }" readonly="readonly"/>天后结束置顶，0表示一直置顶
		    			</div>
						<div style="clear:both"></div>
					</div>
					<c:if test="${fileDistrib.type=='1' and !empty(fileDistrib.contid) }">
						<div id="fileDistrib-form-fileDistribAddPage-cfile-div" style="min-height:30px;margin-bottom:5px;">
							<div style="float:left;width:100px;line-height:30px;">文档：</div>
							<div id="fileDistrib-form-fileDistribAddPage-cfile-show-div" style="display:none;float:left">
								<div id="fileDistrib-form-fileDistribAddPage-cfile-uplist">
								</div>
			    			</div>
							<div style="clear:both"></div>
							<input type="hidden" id="fileDistrib-form-fileDistribAddPage-cfile"  name="cfile" value="${fileDistrib.contid}"  />
							<input type="hidden" id="fileDistrib-form-fileDistribAddPage-cfile-delete"  name="cfiledelete" />
						</div>
					</c:if>
					<%--
					<div id="fileDistrib-form-fileDistribAddPage-cfile-flash" style="width:780px;height:450px; display:none;">
						<c:if test="${fileDistrib.type=='1' }">
							<iframe height="100%" width="100%" src="common/viewFileFlash.do?id=${fileDistrib.contid }"></iframe>
						</c:if>
					</div>
					 --%>
					<c:if test="${!empty(fileDistrib.attach) }">					
						<div id="fileDistrib-form-fileDistribAddPage-attachment-div" style=" min-height:30px;margin-bottom:5px;">
							<div style="float:left;width:100px;line-height:30px;">附件：</div>
							<div id="fileDistrib-form-fileDistribAddPage-attachment-show-div" style="display:none;float:left">
								<div id="fileDistrib-form-fileDistribAddPage-attachment-uplist">
								</div>
			    			</div>
							<div style="clear:both"></div>
							<input type="hidden" id="fileDistrib-form-fileDistribAddPage-attachment"  name="fileDistrb.attach" value="${fileDistrb.attach }" />
							<input type="hidden" id="fileDistrib-form-fileDistribAddPage-attachment-delete"  name="attachdelete" />
						</div>	
										
					</c:if>
					<div id="fileDistrib-form-fileDistribAddPage-content-div" style='display:none; margin-bottom: 5px;'>
						<textarea id="fileDistrib-form-fileDistribAddPage-content" name="content" rows="0" cols="0" style="width:90%;height:400px;"><c:if test="${fileDistrib.type=='0' }" >${fileDistrib.content }</c:if></textarea>
					</div>
					
					<div style="clear:both"></div>
				</form>
				<br/>
				<div style="clear:both"></div>
		</div>			
		<div id="User-window-userChooser" class="easyui-dialog" closed="true"></div>
		<script type="text/javascript">
			var initLoadForm=function(){
				var type="${fileDistrib.type}";
				var ctype="${fileDistrib.cftype}";
				if(type=="1"){
					$("#fileDistrib-form-fileDistribAddPage-content-div").hide();
					$("#fileDistrib-form-fileDistribAddPage-cfile-div").show();
					$("#fileDistrib-form-fileDistribAddPage-cfile-flash").show();
				}else if(type=="2"){
				}else {
					$("#fileDistrib-form-fileDistribAddPage-content-div").show();
					$("#fileDistrib-form-fileDistribAddPage-cfile-flash").hide();
					$("#fileDistrib-form-fileDistribAddPage-cfile-div").hide();
				}
			}
			$(document).ready(function(){
		  		$("#fileDistrib-buttons-fileDistribPage").buttonWidget("setDataID",  {id:'${fileDistrib.id}',state:'${fileDistrib.state}',type:'view'});
		  		initLoadForm();
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
					items: [ 'source', '|', 'preview', 'print',  'copy' ],
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
	    			onlyLeafCheck:true,
	    			readonly:true,
	    			allSelect:false,
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
	    			},
	    			onLoadSuccess:function(){
	    				var temp=$.trim($("#fileDistrib-form-fileDistribAddPage-receivedept").val()||"");
	    				if(temp.toUpperCase()=="ALL"){
		    				$("#fileDistrib-form-fileDistribAddPage-recvdeptname").val("所有部门");
	    				}
	    			}
	    		}); 
	    		//角色 
	    	  	$("#fileDistrib-form-fileDistribAddPage-recvrolename").widget({
	    			name:'t_msg_filedistrib',
					col:'receiverole',
	    			width:350,
	    			singleSelect:false,
	    			onlyLeafCheck:true,
	    			readonly:true,
	    			onSelect:function(data){
	    				$("#fileDistrib-form-fileDistribAddPage-receiverole").val($(this).widget('getValue'));
	    			},
	    			onUnselect:function(data){
	    				$("#fileDistrib-form-fileDistribAddPage-receiverole").val($(this).widget('getValue'));
	    			},
	    			onClear:function(){
	    				$("#fileDistrib-form-fileDistribAddPage-receiverole").val("");
	    			},
	    			onLoadSuccess:function(){
	    				var temp=$.trim($("#fileDistrib-form-fileDistribAddPage-receiverole").val()||"");
	    				if(temp.toUpperCase()=="ALL"){
	    					$("#fileDistrib-form-fileDistribAddPage-recvrolename-div").hide();
	    					$("#fileDistrib-form-fileDistribAddPage-recvrolename-alldiv").show();	
	    				}
	    			}
	    		}); 
	    	  //按人员
	    		$("#fileDistrib-form-fileDistribAddPage-recvusername").widget({
	    			name:'t_msg_filedistrib',
					col:'receiveuser',
					width:350,
	    			singleSelect:false,
	    			onlyLeafCheck:true,
	    			readonly:true,
	    			allSelect:false,
	    			onChecked:function(data){
	    				$("#fileDistrib-form-fileDistribAddPage-receiveuser").val($(this).widget('getValue'));
	    			},
	    			onLoadSuccess:function(){
	    				var temp=$.trim($("#fileDistrib-form-fileDistribAddPage-receiveuser").val()||"");
	    				if(temp.toUpperCase()=="ALL"){
	    					$("#fileDistrib-form-fileDistribAddPage-recvusername").val("所有人员");
	    				}
	    			}
	    		});
	    	  
	    		<c:if test="${fileDistrib.type=='1' and !empty(fileDistrib.contid) }">
					renderDocAttachList($("#fileDistrib-form-fileDistribAddPage-cfile").val());
	    		</c:if>
	    		<c:if test="${!empty(fileDistrib.attach) }">
					renderAttachList($("#fileDistrib-form-fileDistribAddPage-attachment").val());	
				</c:if>
			});
		</script>
  </body>
  
</html>