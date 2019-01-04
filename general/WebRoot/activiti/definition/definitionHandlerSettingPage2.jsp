<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>用户节点Handler设置</title>
  </head>
  <body>
  	<style type="text/css"> 
  		.len200 { 
  			width: 200px; 
  		}
		.content2 {
			border-collapse: collapse;
			border: 1px solid #7babcf;
			width: 100%;
			table-layout: fixed;
			word-break: break-all;
		}
		.content2 td {
			border: solid 1px #7babcf;
			padding: 5px;
		}
		.content2 td div {
			-moz-user-select:none;
			-khtml-user-select:none;
			-webkit-user-select:none;
			-moz-user-select:none;
		    -webkit-user-select:none;
		    user-select:none;  
		}
		.noselect {
			-moz-user-select:none;
			-khtml-user-select:none;
			-webkit-user-select:none;
			-moz-user-select:none;
		    -webkit-user-select:none;
		    user-select:none;  
		}
		.handler-tip2 {
			border: solid 1px #7babcf;
			margin: 3px;
			background: #efefef;
		}
		.len350 {
			width: 350px;
		}
  	</style>
  	<div class="handler-tip2">
        <ol>
            <li>拖动列表中的触发器，可以调整执行顺序。</li>
        </ol>
  	</div>
  	<div class="fill">
  		<table class=".x-unselectable" onselectstart="return false">
  			<tr>
  				<td>触发器：</td>
				<td>
					<select class="len350 easyui-validatebox" id="activiti-bean-definitionHandlerSettingPage2" data-options="required:true">
						<option></option>
						<c:forEach items="${items }" var="item" varStatus="status">
							<option value="${item.handler }" description="<c:out value="${item.handlerdescription}"/>"><c:out value="${item.handler}"/>：<c:out value="${item.handlerdescription}"/></option>
						</c:forEach>
					</select><span style="color: #F00;">*</span>
				</td>
  				<td>
                    <a class="easyui-linkbutton" data-options="plain:false,iconCls:'button-add'" style="text-indent:0px;" onclick="javascript:addBean();">添加</a>
                </td>
  			</tr>
  		</table>
  	</div>
	<div style="border-top: solid 1px #1ea4cf; height: 5px;"></div>
  	<div class="noselect" style="padding: 2px;">
  		<table class="content2" style="CURSOR: default;">
			<tr style="background: #dfdfdf; line-height: 16px;">
				<td style="width:8%">顺序</td>
				<td style="width:32%">触发器</td>
				<td style="width:60%">描述</td>
			</tr>
			<c:forEach items="${list }" var="item" varStatus="idx">
				<tr>
					<td><div class="line_num"><c:out value="${idx.index + 1 }"></c:out></div></td>
					<td eventhandler><c:out value="${item.handler }"></c:out></td>
					<td description><c:out value="${item.description }"></c:out></td>
				</tr>
			</c:forEach>
			<tr delete="delete">
				<td colspan="3" style="text-align: center; color: red; padding: 10px;">
					拖到此处以删除触发器。
				</td>
			</tr>
  		</table>
  	</div>
	<script type="text/javascript">
	<!--
	
	var $current;
	var position = -1;
	
	$(function(){
	
		bindTr();
	});
	
	function addBean() {


		var flag = $('#activiti-bean-definitionHandlerSettingPage2').validatebox('validate');
		var bean = $('#activiti-bean-definitionHandlerSettingPage2').val();
//		var description = $('#activiti-description-definitionHandlerSettingPage2').val();
		var description = $('#activiti-bean-definitionHandlerSettingPage2').find(':selected').attr('description') || bean;
		
		if(bean == null || bean == '') {
		
			$.messager.alert('提醒', '触发器未选择！');
			return false;
		}

        // 重复check
        var unique = true;
        $('td[eventhandler]').each(function() {

            if($(this).text() != null && $(this).text() != '') {

                if(bean == $(this).text()) {

                    $.messager.alert('提醒', '触发器已添加！');
                    unique = false;
                    return false;
                }

            }
        });

        if(!unique) {
            return false;
        }

        $.ajax({
			type: 'post',
			dataType: 'json',
			url: 'act/checkBean.do',
			data: {bean: bean},
			success: function(json) {
			
				if(!json.flag) {
				
					$.messager.alert('提醒', '输入的Bean不存在！');
					return false;
				}
								
				$('#activiti-bean-definitionHandlerSettingPage2').val('');
				$('#activiti-description-definitionHandlerSettingPage2').val('');
				
				var index = $('.content2 tr').length - 1;
				
				var html = new Array();
				html.push('<tr>')
				html.push('<td>');
				html.push('<div class="line_num">');
				html.push(index);
				html.push('</div>');
				html.push('</td>');
				html.push('<td eventhandler>');
				//html.push('<div>');
				html.push(bean);
				//html.push('</div>');
				html.push('</td>');
				html.push('<td description>');
				//html.push('<div>');
				html.push(description);
				//html.push('</div>');
				html.push('</td>');
				//html.push('<td><a href="#" onclick="return delBean();">×</a></td>');
				//html.push('</tr>')
				
				// $('table.content2').append(html.join(''));
				
				$('tr[delete]').before(html.join(''));
				
				//$('table.content2 tr:last').hide();
				//$('table.content2 tr:last').fadeIn('fast');
				
				bindTr();
			},
			error: function() {
			}
		});
	}
	
	function bindTr() {
	
		$current = null;
		position = -1;
		$(this).css('border-top', 'solid 1px #7babcf');
	
		$('.content2 tr:gt(0)[delete!=delete]').unbind().mouseenter(function(e){
		
			if($current != null) {
				$(this).css('border-top', 'solid 2px #7babcf');
				position = $('.content2 tr').index($(this));
			}
			
		}).mouseleave(function(e){
			
			$(this).css('border-top', 'solid 1px #7babcf');
			
		}).mousedown(function(e) {
		
			$current = $(this);
			$(this).css('background', 'rgb(213, 234, 255)');
			$(this).attr('selected', 'true');
		
			if($current != null) {
				$(this).css('border-top', 'solid 2px #7babcf');
				position = $('.content2 tr').index($(this));
				$(this).css('color', '#000');
			}

		}).mouseup(function(e) {
		
			if($current == null) {
				return false;
			}
			
			$('tr[selected]').css('background', '');
			$('table.content2 tr:eq(' + position + ')').before($current.clone());
			$('table.content2 tr:eq(' + position + ')').hide();
			$('table.content2 tr:eq(' + position + ')').show('fast');
			
			$current.remove();
			
			var line = 1;
			$('div.line_num').each(function() {
				
				$(this).html(line++);
			});
			
			$current = null;
			position = -1;
			
			bindTr();
		});
		
		$('.content2 tr:gt(0)[delete=delete]').unbind().mouseenter(function(e){
		
			if($current != null) {
				$(this).css('border', 'solid 2px red');
				position = $('.content2 tr').index($(this));
			}
			
		}).mouseleave(function(e){
			
			$(this).css('border', 'solid 1px #7babcf');
			
		}).mouseup(function(e) {
					
			if($current == null) {
				return false;
			}
			
			$('tr[selected]').css('background', '');
			
			$('.content2 tr').css('border', 'solid 1px #7babcf');
			
			$current.remove();
			
			var line = 1;
			$('div.line_num').each(function() {
				
				$(this).html(line++);
			});
			
			$current = null;
			position = -1;
			
			bindTr();
		});
		
		$('table.content2').unbind().mouseleave(function() {
		
			$('table.content2 tr[selected]').css('background', '');
			$current = null;
			position = -1;
		});
		
		$('table.content2 tr:eq(0)').unbind().mouseenter(function() {
		
			$('table.content2 tr[selected]').css('background', '');
			$current = null;
			position = -1;
		});
		
	}
	
	function delBean() {
		
		$('table.content2 tr[selected]').css('background', '');
		$current = null;
		position = -1;
	}
	
	function saveBean() {
	
		var handlers = new Array();
		$('td[eventhandler]').each(function() {
		
			if($(this).text() != null && $(this).text() != '') {
			
				var handler = $(this).text();
				var description = $(this).next().text();
				handlers.push({
					handler: handler, 
					description: description, 
					definitionkey: '${param.definitionkey }', 
					taskkey: '${param.taskkey }', 
					event: '${param.event}'
				});
			}
		});
		
		$.ajax({
			type: 'post',
			dataType: 'json',
			url: 'act/saveBean.do',
			data: {definitionkey: '${param.definitionkey }', taskkey: '${param.taskkey }', event: '${param.event }', handlers: JSON.stringify(handlers)},
			success: function(json) {
			
				if(!json.flag) {
				
					$.messager.alert('警告', '保存失败！');
					return false;
				}
				
				$.messager.alert('提醒', '保存成功。');
				initEventHandler({definitionkey: '${param.definitionkey }', taskkey: '${param.taskkey }', event: '${param.event }'});
				$("#activiti-dialog-definitionHandlerSettingPage").dialog('close');
			
			},
			error: function() {}
		});
		
	}
	
	-->
	</script>
  </body>
</html>
