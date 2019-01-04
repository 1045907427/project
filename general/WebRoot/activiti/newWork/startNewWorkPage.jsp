<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>启动工作流程页面</title>
  </head>
  
  <body>
  	<style type="text/css">
  		.startNewWorkTable{width:100%;border-collapse:collapse;}
  		.startNewWorkTable td{border:1px solid #aaaaaa;padding:3px;line-height:24px;}
  		.trhidden{display: none;}
  	</style>
	<div class="easyui-panel" data-options="fit:true,border:false" style="padding:5px;">
		<form id="activiti-form-startNewWorkPage" action="act/startNewWork.do" method="post">
			<input type="hidden" name="id" value="${process.id }" />
			<input type="hidden" name="nexttaskkey" />
			<table class="startNewWorkTable">
				<tr>
					<td style="background:#efefef;font-weight:700;">流水号${process.id } ： ${process.title }</td>
				</tr>
				<tr>
					<td>当前提交流程：${process.definitionname }</td>
				</tr>
			    <tr>
			    	<td style="border:1px solid #aaaaaa;background:#efefef;font-weight:700;padding:3px;line-height:24px;">附件：</td>	
			    </tr>
			    <tr>
			    	<td>
				    	<c:forEach items="${attachlist }" var="item">
				    		<input type="hidden" value="${item.attachid }" />
				    		<div class="attach" style="width: 96%; max-width: 400px; margin: 1px 5px 1px 5px;overflow: hidden; text-overflow:ellipsis;white-space:nowrap;"><a href="javascript:void(0);" onclick="return false;"><c:out value="${item.oldfilename }"></c:out></a></div>
				    	</c:forEach>
				    	<div id="addAttach" title="支持以下类型：*.png;*.jpg;*.gif;*.txt;*.doc;*.docx;*.xls;*.xlsx;*.ppt;*.pptx;*.zip;*.rar;*.7z;"><a href="javascript:;" id="activiti-addattach-startNewWorkPage" class="easyui-linkbutton" data-options="iconCls:'button-add',plain:true">添加</a></div>
			    	</td>
			    </tr>
			    <tr>
			    	<td style="border:1px solid #aaaaaa;background:#efefef;font-weight:700;padding:3px;line-height:24px;">下一节点：</td>	
			    </tr>
				<tr>
				  	<td>
					    <c:forEach items="${nexttasklist }" var="item">
					    	<div style="float: left;" style="overflow: hidden; text-overflow:ellipsis; white-space:nowrap;"><label><input type="radio" name="nexttask" value="${item.id }" data="${item.properties.type }" style="width: 13px; height: 18px;"/><c:out value="${item.properties.name }" escapeXml="true"></c:out></label></div>
					    </c:forEach>
				   	</td>
				</tr>
				<tr class="trhidden">
					<td style="background:#efefef;font-weight:700;">下一节点接收人：<c:out value="${nexttask.nameExpression.expressionText }" escapeXml="true"></c:out></td>
				</tr>
				<tr class="trhidden">
					<td>
						<input type="radio" name="acceptType" id="radio1" checked="checked" value="1" style="position:relative;top:3px;"/><label for="radio1">使用默认接收人</label> &nbsp; 
						<input type="radio" name="acceptType" id="radio2" value="2" style="position:relative;top:3px;" <c:if test="${count != '0' and canassign != '1' }">disabled="disabled" </c:if> /><label for="radio2">指定其他接收人</label>
					</td>
				</tr>
				<tr class="trhidden">
					<td style="height:24px;">
						<div id="activiti-apply-startNewWorkPage" style="display:block">选择人员：<input id="activiti-nextAssignee-startNewWorkPage" name="nextAssignee" data-option="required: true;" autocomplete="off"/>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">

		var taskkey = '';
		var widgetstr = '';
		var ids = new Array();
		var count = 0;

		$(function(){
		
			bindAttach();
				
			$('#activiti-addattach-startNewWorkPage').upload({
				auto: false,
				del: false,
				type: 'upload',
				allowType: '*.png;*.jpg;*.gif;*.txt;*.doc;*.docx;*.xls;*.xlsx;*.ppt;*.pptx;*.zip;*.rar;*.7z;',
				onComplete: function(json){

					var oldFileName = json.oldFileName;
					var newFileName = json.newFileName;
					var fileid = json.id;
					var fullPath = json.fullPath;
					var did = getRandomid();
					
					var id = '${process.id }';
					var attachid = json.id;
					
					$.ajax({
						type: 'post',
						data: {processid: id, attachid: fileid},
						url: 'act/addAttach.do',
						success: function(data) {
						
							var j =$.parseJSON(data);
							if(json.flag) {

								$('#addAttach').before('<input type="hidden" value="' + fileid + '"/><div class="attach" id="div' + did + '" style="overflow: hidden; text-overflow:ellipsis;white-space:nowrap;width: 96%; max-width: 400px; margin: 1px 5px 1px 5px;"></div>');
					    		$('div#div' + did).append('<a href="javascript:void(0);" target="_blank" class="attach">' + oldFileName + '</a>');

								$('#div' + did).hide();
								$('#div' + did).show('slow');

								bindAttach();
							   	
							}
						
						},
						error: function() {}
					});
					
				},
				onDelete: function(file){
					//alert(file.name);
					//console.log('onDelete');
				},
				onVirtualDelete: function(id){
					//alert(id);
					//console.log('onVirtualDelete');
				}
			});
		
			$('input[name=nexttask]').unbind().click(function() {
			
				$('input[name=nexttaskkey]').val($(this).val());;
				if(taskkey == $(this).val()) {
				
					return false;
				}
				taskkey = $(this).val();
				
				var id = '${param.id }';
				var type = '1';
				var tasktype = $(this).attr('data');
				
				if(tasktype == 'endEvent') {

					$("#activiti-apply-startNewWorkPage").widget({
			    		referwid:'RT_T_SYS_USER',
			    		singleSelect: true,
			    		width:180,
			    		required: false,
			    		onlyLeafCheck:true
			    	});
			    	
			    	$('.trhidden').hide();
			    	
			    	return true;
				}

				loading('正在获取人员信息...');
				
				$.ajax({
					type: 'post',
					data: {taskkey: taskkey, id: id, type: type},
					url: 'act/getNextTaskDefinition.do',
					success: function(data) {
						loaded();
						var json = $.parseJSON(data);
						$('.trhidden').show();

						widgetstr = json.widgetstr;
						ids = json.ids;
						count = json.count;
						$('input[name=acceptType]').first().click();
						
						if(json.canassign != '1') {
							$('input[name=acceptType]').last().attr('disabled', 'disabled');
						} else {
							$('input[name=acceptType]').last().removeAttr('disabled', 'disabled');
						}
						
						$("#activiti-nextAssignee-startNewWorkPage").widget({
			    			referwid:'RT_T_SYS_USER',
			    			singleSelect: true,
			    			width:180,
			    			required: true,
			    			onlyLeafCheck:true,
			    			param: [{field: 'userid', op: 'in', value: json.widgetstr}],
			    			onLoadSuccess: function() {
			    				if(json.count == 1) {
			    					$("#activiti-nextAssignee-startNewWorkPage").widget('setValue', json.ids[0]);
			    				}
			    			}
			    		});
			    		
					},
					error: function() {
						$('.trhidden').hide();
						$.messager.alert("提醒","获取人员失败！");
					}
				});

			});
		
			$("input[name=acceptType]").unbind().click(function(){

				var type = $(this).val();
				if(type == "2"){

					$("#activiti-nextAssignee-startNewWorkPage").widget({
		    			referwid:'RT_T_SYS_USER',
		    			singleSelect: true,
		    			width:180,
		    			required: true,
		    			onlyLeafCheck:true
		    		});
				}
				else{

					$("#activiti-nextAssignee-startNewWorkPage").widget({
		    			referwid:'RT_T_SYS_USER',
		    			singleSelect: true,
		    			width:180,
		    			required: true,
		    			onlyLeafCheck:true,
		    			param: [{field: 'userid', op: 'in', value: '${widgetstr }'} ],
			    		onLoadSuccess: function() {
			    			
				    		if(count == 1) {
				    			$("#activiti-nextAssignee-startNewWorkPage").widget('setValue', ids[0]);
			    			}
			    		}
		    		});
				}
			});
		
			$('input[name=nexttask]').first().click();

			$("#activiti-form-startNewWorkPage").form({
				onSubmit: function(){
				
					var flag = $("#activiti-form-startNewWorkPage").form('validate');
					if(!flag) {
					
						return false;
					}
					
		  		  	loading("提交中...");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		  	if(json.flag == true){
		  		  		$.messager.alert("提醒", "启动成功");
		  		  		top.updateTab('act/myWorkPage.do', "我的请求");
		  		  		top.updateTab('act/myWorkPage5.do', "我的草稿");
		  		  		top.$("#tt").tabs('close',"新建工作");
		  		  	}
		  		  	else{
		  		  		$.messager.alert("提醒", "启动失败");
		  		  	}
		  		}
			});
		});
		
		function deleteAttach(attachid) {
		
			$.messager.confirm("提醒","确定要删除该附件?",function(r){
			
				if(r) {
				
					$.ajax({
						type: 'post',
						data: {attachid: attachid},
						url: 'act/deleteAttach.do',
						success: function(data) {
						
							var json = $.parseJSON(data);
							
							if(json.flag) {
							
								$('div.attach').each(function(){
								
									if($(this).prev().val() == attachid) {
									
										$(this).hide('slow');
									}
								});
								
								$.messager.alert("提醒","删除附件成功。");
								return true;
							
							}
							
							$.messager.alert("提醒","删除附件失败！");
						
						},
						error: function() {}
					})				
				}
			});
		
		}
		
		function bindAttach() {
					
			$('div.attach').each(function() {
			
				var $obj = $(this);
				var fileid = $obj.prev().val();
				
				if(fileid == null || fileid == '') {
					return true;
				}
				
				var hb = new Array();	// html builder
				hb.push('<div style="width: 70px; margin: 5px 5px 5px 5px;">');
				hb.push('<a href="common/download.do?id=' + fileid + '" target="_blank" title="点击下载">下载</a>');
				hb.push('</div>');
				hb.push('<div style="width: 70px;">');
				hb.push('&nbsp;&nbsp;<a onclick="$.upload.handleDBClick(' + fileid + ');" href="javascript:void(0);" target="_blank" title="点击查看">查看</a>');
				hb.push('</div>');
				hb.push('<div style="width: 70px; margin: 10px 5px 5px 5px;">');
				hb.push('<a onclick="deleteAttach(' + fileid + ')" href="javascript:void(0);" title="点击删除文件">删除</a>');
				hb.push('</div>');
			
				$obj.tooltip({
					position: 'left',
					content: hb.join(''),
					onShow: function(){
					
						$obj.tooltip('tip').mouseover(function() {
						
							$obj.tooltip('show');
						});
						$obj.tooltip('tip').mouseleave(function() {
						
							$obj.tooltip('hide');
						});
					
				        $obj.tooltip('tip').css({
				            backgroundColor: '#FFF',
				            borderColor: '#000'
				        });
				    }
				});
				
				$obj.mouseover(function(){
				
					$obj.css('background-color', '#EEE');
				})
				
				$obj.mouseleave(function(){
				
					$obj.css('background-color', '#FFF');
				})
			});
		
		}
	</script>
  </body>
</html>
