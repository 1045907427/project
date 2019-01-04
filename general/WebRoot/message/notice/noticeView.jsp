<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>公告通知查看</title>    
		<%@include file="/include.jsp" %> 	
	  	<script type="text/javascript" src="js/kindeditor/kindeditor-min.js"></script> 
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
			<div>
				<form id="messageNotice-form-viewNotice">
					<div style="margin:3px 0px;">
						<div style="float:left;width:100px;line-height:22px;">当前状态：</div>
						<select  name="status" style="width: 45px" id="messageNotice-form-noticePage-state-select" disabled="disabled">
				    		<c:forEach var="item" items="${statelist }">
								<c:choose>
				    				<c:when test="${item.code == msgNotice.state }">
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
					<%--<div style="hiehgt:30px;margin:10px 0;">--%>
						<%--<div style="float:left;width:100px;line-height:22px;">选择格式：</div>--%>
						<%--<select class="easyui-combobox" id="messageNotice-form-viewNotice-form" name="msgNotice.form" style="width: 90px" disabled="disabled">--%>
							<%--<option value="1" <c:if test="${msgNotice.form==1}"> selected="selected" </c:if> >普通格式</option>--%>
							<%--<option value="2" <c:if test="${msgNotice.form==2}"> selected="selected" </c:if> >MHT格式</option>--%>
							<%--<option value="3" <c:if test="${msgNotice.form==3}"> selected="selected" </c:if> >超级链接</option>--%>
						<%--</select>--%>
						<%--<div style="clear:both"></div>--%>
					<%--</div>--%>
					<%--<div style="hiehgt:30px;margin:10px 0;">--%>
						<%--<div style="float:left;width:100px;line-height:20px;">公告分类：</div>--%>
						<%--<select name="msgNotice.type" class="easyui-combobox"  disabled="disabled" style="width: 90px">--%>
							<%--<option value="1">无类型</option>--%>
						<%--</select>--%>
						<%--<div style="clear:both"></div>--%>
					<%--</div>						--%>
					<div style="padding-bottom:4px;">
						<div style="float:left;width:100px;line-height:22px;">标题：</div>
						<div style="float:left">
							<input type="text" id="messageNotice-form-viewNotice-title" name="msgNotice.title" name="msgNotice.title" value="${msgNotice.title }" style="width:405px;" readonly="readonly"/>
							<label id="messageNotice-form-viewNotice-tcolorshow">设置标题颜色</label>
						</div>
						<div style="float:left;height:16px;padding-top:3px;">
							<input type="hidden" id="messageNotice-form-viewNotice-tcolor" name="msgNotice.tcolor" value="${msgNotice.tcolor }" readonly="readonly"/>
						</div>
						<div style="clear:both"></div>
					</div>
					<div style="height:auto;margin-bottom: 5px;">
						<div style="float:left;width:100px;line-height:46px;">按部门发布：</div>
						<div style="float:left;">
							<textarea id="messageNotice-form-viewNotice-recvdeptname"  name="recvdeptname" class="easyui-validatebox"  style="height:40px;width:400px;" readonly="readonly">${msgNotice.receivedeptname }</textarea>
		    			</div>
						<div style="float:left;">
							<c:if test="${not empty msgNotice.receivedeptname}">
								<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-view'" style="margin-top: 20px;" onclick="javascript:noticeSendList_showRangeList('${msgNotice.id}','1')">查看更多</a>
							</c:if>
						</div>
						<div style="clear:both"></div>
					</div>
					<div style="height:auto;margin-bottom: 5px;">
						<div style="float:left;width:100px;line-height:46px;">按角色发布：</div>
						<div style="float:left;">
							<textarea id="messageNotice-form-viewNotice-recvrolename"  name="recvrolename" class="easyui-validatebox" style="height:40px;width:400px;" readonly="readonly">${msgNotice.receiverolename }</textarea>
		    			</div>
						<div style="float:left;">
							<c:if test="${not empty msgNotice.receiverolename}">
								<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-view'" style="margin-top: 20px;" onclick="javascript:noticeSendList_showRangeList('${msgNotice.id}','2')">查看更多</a>
							</c:if>
						</div>
						<div style="clear:both"></div>
					</div>
					<div style="height:auto;margin-bottom: 5px;">
						<div style="float:left;width:100px;line-height:46px;">按人员发布：</div>
						<div style="float:left;">
							<textarea id="messageNotice-form-viewNotice-recvusername"  name="recvusername" class="easyui-validatebox" style="height:40px;width:400px;" readonly="readonly">${msgNotice.receiveusername }</textarea>
		    			</div>
						<div style="float:left;">
							<c:if test="${not empty msgNotice.receiveusername}">
								<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-view'" style="margin-top: 20px;" onclick="javascript:noticeSendList_showRangeList('${msgNotice.id}','3')">查看更多</a>
							</c:if>
						</div>
						<div style="clear:both"></div>
					</div>
					<%--<div id="messageNotice-form-viewNotice-url-div" style="height:35px;line-height:30px;">--%>
						<%--<div style="float:left;width:100px;line-height:22px;">超级链接地址：</div>--%>
						<%--<div style="float:left">--%>
							<%--<input type="text" style="width:400px;" name="msgNotice.url" value="${msgNotice.url }"  readonly="readonly"/>--%>
						<%--</div>--%>
						<%--<div style="clear:both"></div>--%>
					<%--</div>--%>
					 <c:if test="${msgNotice.state==1}">
						<div style="height:30px;">
							<div style="float:left;width:100px;line-height:22px;">发布时间：</div>
							<div style="float:left;">
								<input type="text" style="width:130px" class="easyui-validatebox" data-options="required:true"  name="msgNotice.addtime" value='<fmt:formatDate  value="${msgNotice.addtime }" pattern="yyyy-MM-dd HH:mm:ss" />' readonly="readonly"/>
							</div>
							<div style="clear:both"></div>
						</div>
					</c:if>
					<div style="height:30px;">
						<div style="float:left;width:100px;line-height:22px;">有效期：</div>
						<div style="float:left;">
							<input type="text" style="width:80px" name="msgNotice.startdate" value="${msgNotice.startdate }" readonly="readonly"/>至<input type="text" style="width:80px" name="msgNotice.enddate" value="${msgNotice.enddate }" readonly="readonly"/>&nbsp;为空为手动终止
						</div>
						<div style="clear:both"></div>
					</div>
					<div style="height:30px;">
						<div style="float:left;width:100px;line-height:22px;"> 短信提醒：</div>
						<div style="float:left;">
		    				<lable class="checkbox1"><input type="checkbox" class="divtext" id="messageNotice-form-viewNotice-ismsg" name="msgNotice.ismsg" value="${msgNotice.ismsg }" <c:if test="${ msgNotice.ismsg==1}" > checked="checked" </c:if> disabled="disabled" />使用内部短信提醒 &nbsp;</lable>
		    			</div>
						<div style="clear:both"></div>
					</div>
					<div style="height:30px;">
						<div style="float:left;width:100px;line-height:22px;">置顶：</div>
						<div style="float:left">
                            <lable class="checkbox1"><input type="checkbox" class="divtext" id="messageNotice-form-viewNotice-istop" name="msgNotice.istop" value="${msgNotice.istop }" <c:if test="${msgNotice.istop==1}"> checked="checked" </c:if> disabled="disabled"/>使公告通知置顶，显示为重要&nbsp;</lable>
		    				<input type="text" style="width:50px;" name="msgNotice.topday"  value="${ msgNotice.topday }" readonly="readonly"/>天后结束置顶，0表示一直置顶
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
						<input type="hidden" id="messageNotice-form-addNotice-attachment" name="msgNotice.attach" value="${msgNotice.attach }"/>
					</div>
					<%--<div style="height:30px;">--%>
						<%--<div style="float:left;width:100px;line-height:22px;">内容简介：</div>--%>
						<%--<div style="float:left">--%>
							<%--<input type="text" style="width:400px;" name="msgNotice.intro" value="${msgNotice.intro }" readonly="readonly"/>--%>
						<%--</div>--%>
						<%--<div style="clear:both"></div>--%>
					<%--</div>--%>
					<div id="messageNotice-form-viewNotice-content-div" <c:if test="msgNotice.form==1">style="display:''" </c:if>>
						<textarea id="messageNotice-form-viewNotice-content" name="msgNotice.content" rows="0" cols="0" style="width:90%;height:400px;" readonly="readonly" disabled="disabled">${msgNotice.content }</textarea>
					</div>						
					<div style="height:30px;margin-top:5px;">
						<%--<div style="float:left;width:100px;line-height:22px;" >关键词：</div>--%>
						<%--<div style="float:left;">--%>
							<%--<input type="text" style="width:400px;" readonly="readonly" name="msgNotice.keyword" value="${msgNotice.keyword }"/>  (您可以调整关键词内容，多个关键词请用,分隔)--%>
						<%--</div>--%>
						<div style="clear:both"></div> 
					</div>
					<input type="hidden" name="msgNotice.id" value="${msgNotice.id }" />
					<input type="hidden" id="messageNotice-form-noticePage-state" value="${msgNotice.state }"/>
					<div style="clear:both"></div>
				</form>
			</div>
		</div>
		<script type="text/javascript">
			$(document).ready(function(){
				$("#notice-buttons-noticePage").buttonWidget("setDataID",  {id:'${msgNotice.id}',state:'${msgNotice.state}',type:'view'});
				var noticeViewKEditor=KindEditor.create('#messageNotice-form-viewNotice-content',{
					allowPreviewEmoticons:false,
					allowImageUpload:false,
					allowFlashUpload:false,
					allowMediaUpload:false,
					allowFileUpload:false,
					allowFileManager:false,
					resizeType: 1,
					syncType : 'auto',
					readonlyMode : true,
					items:[ 'preview','print','copy','selectall','fullscreen','|','about'],
					afterChange: function (e) {
						this.sync();
					},
					afterCreate: function(e){
                        $("span.ke-disabled").each(function(i,el){
                            $(this).removeClass("ke-disabled");
                            $(this).css("opacity","");
                        });
					}
				});
                //noticeViewKEditor.readonly(true);

				
	    		$("#messageNotice-form-viewNotice-form").change(function(){
	    			var val=$(this).val()||"1";
	    			if(val==2){
	    				$("#messageNotice-form-viewNotice-attachment-div").css("display","");
	    				$("#messageNotice-form-viewNotice-content-div").css("display","none");
	    				$("#messageNotice-form-viewNotice-url-div").css("display","none");
	    			}else if(val==3){
	    				$("#messageNotice-form-viewNotice-url-div").css("display","");
	    				$("#messageNotice-form-viewNotice-content-div").css("display","none");
	    				$("#messageNotice-form-viewNotice-attachment-div").css("display","none");
	    			}else{
	    				$("#messageNotice-form-viewNotice-content-div").css("display","");
	    				$("#messageNotice-form-viewNotice-attachment-div").css("display","");
	    				$("#messageNotice-form-viewNotice-url-div").css("display","none");
	    			}
	    		});
	    		$("#messageNotice-form-viewNotice-form").val('${msgNotice.form}');
	    		$("#messageNotice-form-viewNotice-form").change();
				
	    		$("#messageNotice-form-viewNotice-tcolor").colorPicker({
	    			pickerDefault: "000000",
	    			onColorChange:function(e,color){
	    				$("#messageNotice-form-viewNotice-tcolorshow").css("color",color);
	    			}
	    		});
	    		<c:if test="${not empty(msgNotice.attach)}">	
					renderAttachList($("#messageNotice-form-addNotice-attachment").val(),true);
				</c:if>
			});
		</script>
	</body>
</html>
