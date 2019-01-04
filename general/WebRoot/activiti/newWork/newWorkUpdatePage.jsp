<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>新工作修改页面</title>
    <%@include file="/include.jsp" %>  
  </head>
  <body>
  	<div class="easyui-panel" data-options="fit:true,border:false" title="修改工作 - 编号：${process.id }">
	  	<div class="easyui-layout" data-options="fit:true">
	  		<c:if test="${definition != null and process != null}">
				<div data-options="region:'north',border: false" style="text-align: center;padding: 2px; height: 53px;">
					<div style="text-align:center;border-bottom:1px solid #dddddd;padding-bottom:8px;margin-bottom:8px;">
	           			<input type="text" name="title"    id="title"    style="width:350px;line-height:20px;padding:5px;border:1px solid #999999; font-size: 10pt; padding-left: 70px;" value="${process.title }" maxlength="50" autocomplete="off"/>
	           			<input type="text" name="pretitle" id="pretitle" style="width:60px;line-height:20px;padding:5px;border:1px solid #999999; font-size: 10pt; BORDER-RIGHT-STYLE: none; position: relative; left: -433px; color: #999999;text-align: right;" value="${process.id }-" onfocus="javascript:$('#title').focus();"/>
					</div>
				</div>
			</c:if>
			<div data-options="region:'center',border:false">
				<c:choose>
					<c:when test="${definition.formtype == 'formkey' }">
						<form action="" method="post" id="work-form-newWorkUpdatePage">
							${process.html }
						</form>
					</c:when>
					<c:otherwise>
						<!-- 加载自定义URL表单 -->
						<div id="activiti-panel-newWorkUpdatePage" data-options="fit:true">加载自定义URL表单失败，可能由于该工作已被删除或已被提交。
						</div>
						<c:if test="${definition != null and process != null}">
							<script type="text/javascript">
								$(function(){
	
									$("#activiti-panel-newWorkUpdatePage").panel({
										href: '${definition.businessurl }?type=edit&from=work&id=${process.businessid }',
										cache: false,
										maximized: true,
										border: false
									});
								});
							</script>
						</c:if>
					</c:otherwise>
				</c:choose>
			</div>
			<div data-options="region:'south',border:false">
				<div class="buttonDivR">
					<a href="javascript:;" id="activiti-next-newWorkUpdatePage" class="easyui-linkbutton" data-options="iconCls:'button-next'">保存并转交下一步</a>
					<a href="javascript:;" id="activiti-save-newWorkUpdatePage" class="easyui-linkbutton" data-options="iconCls:'button-save'">保存</a>
					<a href="javascript:;" id="activiti-savePrev-newWorkUpdatePage" class="easyui-linkbutton" data-options="iconCls:'button-back'">保存返回</a>
					<a href="javascript:;" id="activiti-viewflow-newWorkUpdatePage" class="easyui-linkbutton" data-options="iconCls:'workflow-viewflow'">查看流程</a>
					<a href="javascript:;" id="activiti-giveup-newWorkUpdatePage" class="easyui-linkbutton" data-options="iconCls:'button-giveup'">取消</a>
				</div>
			</div>	
		</div>
	</div>
	<input type="hidden" name="processid" id="processid" value="${process.id }" />
	<input type="hidden" name="definitionkey" id="definitionkey" value="${process.definitionkey }" />
	<div id="activiti-dialog-newWorkUpdatePage"></div>
	<script type="text/javascript">
		(function($) {
		    var oldHTML = $.fn.html;
		    $.fn.formhtml = function() {
		        if (arguments.length) return oldHTML.apply(this,arguments);
		        $("input,textarea,button", this).each(function() {
		            this.setAttribute('value',this.value);
		        });
		        $(":radio,:checkbox", this).each(function() {
		            if (this.checked) this.setAttribute('checked', 'checked');
		            else this.removeAttribute('checked');
		        });
		        $("option", this).each(function() {
		            if (this.selected) this.setAttribute('selected', 'selected');
		            else this.removeAttribute('selected');
		        });
		        return oldHTML.apply(this);
		    };
		})(jQuery);
		$(function(){
			$("#activiti-giveup-newWorkUpdatePage").click(function(){
				$.messager.confirm("提醒", "确定不保存该工作吗？", function(r){
					if(r){
						location.href = "act/newWorkPage.do";
					}
				});
			});
			$("#activiti-next-newWorkUpdatePage").click(function(){
				// 自定义URL表单时调用自定义保存方法
				<c:choose>
					<c:when test="${definition.formtype == 'formkey' }">save(1, null);</c:when>
					<c:otherwise>customSave(1)</c:otherwise>
				</c:choose>
			});
			$("#activiti-save-newWorkUpdatePage").click(function(){
				<c:choose>
					<c:when test="${definition.formtype == 'formkey' }">save(2, null);</c:when>
					<c:otherwise>customSave(2)</c:otherwise>
				</c:choose>
			});
			$("#activiti-savePrev-newWorkUpdatePage").click(function(){
				<c:choose>
					<c:when test="${definition.formtype == 'formkey' }">save(3, null);</c:when>
					<c:otherwise>customSave(3)</c:otherwise>
				</c:choose>
			});
			$('#activiti-viewflow-newWorkUpdatePage').click(function(){
				var id = '${process.id }';
				if(id == ''){
					return false;
				}
		
				$('#activiti-dialog-newWorkUpdatePage').dialog({
					title:'查看流程',
					width:600,
					height:450,
					closed:false,
					cache:false,
					modal: true,
					maximizable:true,
					resizable:true,
					href:'act/commentListPage.do?type=2&id=${process.businessid }',
					buttons: [
						{
							text: '关闭',
							iconCls:'button-close',
							handler:function(){
								$("#activiti-dialog-newWorkUpdatePage").dialog('close');
							}
						}
					]
				});
			});
		});

		// 自定义URL表单保存方法
		function customSave(type) {
			workFormSubmit(save, {type: type});
		}
		
		// businessId: 在线Form表单时传入null；
		// 自定义URL表单时，在自定义workFormSubmit中回调save方法时传入id
		function save(type, businessId){

			var flag = $("#work-form-newWorkUpdatePage").form("validate");
			if(flag == false) return ;
			loading("提交中...");
			var json = $("#work-form-newWorkUpdatePage").serializeJSON();
			var title = $("#title").val();
			var definitionkey = $("#definitionkey").val();
			var id = $("#processid").val();
			var html = $("#work-form-newWorkUpdatePage").formhtml();
			$.ajax({
				url:'act/updateNewWork.do',
				type:'post',
				dataType:'json',
				data:{'process.json':JSON.stringify(json), 'process.definitionkey': definitionkey, 'type':type, 'process.title':title, 'process.html':html, 'process.id':id},
				success:function(json){
					loaded();
					if(json.flag == true){
						$.messager.alert("提醒","保存成功");
						if(type == 1){
							
							$("#activiti-dialog-newWorkUpdatePage").dialog({
								title:'启动工作流',
								width:500,
								height: 320,
								modal:true,
								cache:false,
								closed:true,
								href:'act/startNewWorkPage.do?id='+ json.id,
								buttons:[
									{
										text:'确定转交',
										iconCls:'button-save',
										handler:function(){
											$("#activiti-form-startNewWorkPage").submit();
										}
									},
									{
										text:'取消转交',
										iconCls:'button-giveup',
										handler:function(){
											$("#activiti-dialog-newWorkUpdatePage").dialog('close');
										}
									}
								],
								onClose: function() {
									location.href = location.href;
								}
							});
							
							$("#activiti-dialog-newWorkUpdatePage").dialog('open');
						}
						else if(type == 2){
							location.href = "act/newWorkUpdatePage.do?id="+ json.id;
						}
						else if(type == 3){
							top.addOrUpdateTab('act/myWorkPage5.do', "我的草稿");
							location.href = "act/newWorkPage.do";
						}
					}
					else{
						$.messager.alert("提醒","保存失败");
					}
				}
			});
		}
	</script>
  </body>
</html>
