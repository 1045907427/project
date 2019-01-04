<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>节点触发器</title>
  </head>
  <body>
  	<style type="text/css">
		.handler-tip {
			border: solid 1px #7babcf;
			/*margin: 5px;*/
			background: #efefef;
		}
		.handler-main {
			margin-top: 5px;
		}
		.content {
			border-collapse: collapse;
			border: 1px solid #7babcf;
			width: 100%;
			table-layout: fixed;
			word-break: break-all;
            margin: 0px;
		}
		.content td {
			border: solid 1px #7babcf;
			padding: 5px;
		}
		.loadingtip {
			margin: 2px;
			padding: 1px;
		}
  	</style>
  	<div class="handler-tip">
        <ol>
            <li>当前页面可以配置每个用户节点的触发器。触发器即每个节点审批之后所要进行的操作。</li>
            <li style="color: #F00; font-weight: bold;">如果需要修改节点触发器配置，请在新流程版本基础上修改。</li>
        </ol>
  	</div>
  	<div class="handler-main">
  		<table style="width: 100%;" class="content" cellpadding="0" cellspacing="0">
			<tr style="background: #dfdfdf;line-height:28px;">
				<td style="width:5%">序号</td>
				<td style="width:15%">节点标识</td>
				<td style="width:18%">节点名称</td>
				<td style="width:31%">触发器</td>
			</tr>
  			<c:forEach items="${list }" var="item" varStatus="idx">
				<tr <c:if test="${item.nodeType eq 'ParallelMultiInstanceBehavior'}">sign title="会签节点" </c:if>>
  					<td><c:out value="${idx.index + 1 }"></c:out></td>
  					<td><c:out value="${item.key }"></c:out></td>
  					<td><c:out value="${item.name }"></c:out></td>
  					<td style="padding: 0px;">
  						<div class="loadingtip" event="complete" taskkey="${item.key }">加载中...</div>
						<div style="background:#dff5fd;">
							<a class="easyui-linkbutton" id="activiti-complete-${idx.index }-set-definitionHandlerSettingPage" data-options="plain:true,iconCls:'button-setting',disabled:true" style="text-indent:0px;" onclick="javascript:eventHandlerSetPage({definitionkey: '${param.definitionkey }', taskkey: '${item.key }', event: 'complete', title: '节点 [<c:out value="${item.name }"></c:out>] 触发器设置'});">设置</a>
						</div>
  					</td>
  				</tr>
  			</c:forEach>
  		</table>
  	</div>
  	<div id="activiti-dialog-definitionHandlerSettingPage"></div>
	<script type="text/javascript">
	
	$(function(){
	
		$('div.loadingtip[event]').each(function() {
		
			var definitionkey = '${param.definitionkey }';
			var taskkey = $(this).attr('taskkey');
			var event = $(this).attr('event');
			
			var option = {definitionkey: definitionkey, taskkey: taskkey, event: event};
		
			initEventHandler(option);
		});
	
		$("#activiti-dialog-definitionHandlerSettingPage").dialog({
			width:520,
			height:420,
			cache:false,
			modal:true,
			closed:true,
			buttons:[
				{
					iconCls:'button-save',
					text:'确定',
					handler:function(){
						saveBean();
					}
				},
				{
					iconCls:'button-quit',
					text:'取消',
					handler:function(){
						$("#activiti-dialog-definitionHandlerSettingPage").dialog('close');
					}
				}
			]				
		});
	
	});
	
	function initEventHandler(option) {

		$.ajax({
			type: 'post',
			dataType: 'json',
			url: 'act/getEventHandlerSetting.do',
			data: option,
			success: function(json) {
				
				var index = $('div.loadingtip[event='+ option.event + ']').index($('div.loadingtip[event='+ option.event + '][taskkey=' + json.taskkey + ']'));

				$('div.loadingtip[event='+ option.event + '][taskkey=' + json.taskkey + ']').css('border', 'solid 0px #DDD');
				if(json.list.length == 0) {

                    $('div.loadingtip[event='+ option.event + '][taskkey=' + json.taskkey + ']').css('color', '#aaa');
					$('div.loadingtip[event='+ option.event + '][taskkey=' + json.taskkey + ']').html('无');

				} else {
				
					var html = new Array();
					for(var i = 0; i < json.list.length; i++) {
						
						var h = json.list[i];
						html.push('<div style="margin: 2px; padding: 2px; border: solid 1px #aaa;">' + '触发器： <span style="color: #F00;">' + h.handler + '</span><br/>描　述： <span style="color: #F00;">' + h.description + '</span></div>');
					}
					$('div.loadingtip[event='+ option.event + '][taskkey=' + json.taskkey + ']').html(html.join(''));
				}
				$('#activiti-'+ option.event + '-' + index + '-set-definitionHandlerSettingPage').linkbutton('enable');
			},
			error: function() {
			
			}
		});
	
	}
	
	function eventHandlerSetPage(option) {

        $.messager.confirm('提醒', '如果需要修改触发器配置，请在<span style="color: #F00;">新流程版本</span>基础上修改 ！<br/>确定继续修改 ？', function(e) {
            if(e) {
                $("#activiti-dialog-definitionHandlerSettingPage").dialog({
                    href: 'act/definitionHandlerSettingPage2.do?definitionkey=' + option.definitionkey + '&taskkey=' + option.taskkey + '&event=' + option.event,
                    title: option.title
                });
                $("#activiti-dialog-definitionHandlerSettingPage").dialog('open');
            }
        });
	}

	</script>
  </body>
</html>
