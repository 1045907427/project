<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>新工作添加页面</title>
    <%@include file="/include.jsp" %>  
  </head>
  <body>
  	<div class="easyui-panel" data-options="fit:true,border:false" title="新建工作-${definitionName }">
	  	<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border: false" style="text-align: center;padding: 2px; height: 53px;">
				<div style="text-align:center;border-bottom:1px solid #dddddd;padding-bottom:8px;margin-bottom:8px;">
           			<input type="text" name="title" id="title" style="width:350px;line-height:20px;padding:5px;border:1px solid #999999;padding-left: 50px;" value="<c:out value='${title }' escapeXml='true'></c:out>" maxlength="50" autocomplete="off"/>
           			<input type="text" name="pretitle" style="width:40px;line-height:20px;padding:5px;border:1px solid #999999;BORDER-RIGHT-STYLE: none; position: relative; left: -413px; color: #999999;" value="{OA号}-" onfocus="javascript:$('#title').focus();"/>
				</div>
			</div>
			<div data-options="region:'center',border:false">
				<c:choose>
					<c:when test="${formType == 'formkey' }">
						<form action="" method="post" id="work-form-newWorkAddPage">
							${formData }
						</form>
					</c:when>
					<c:otherwise>
						<!-- 加载自定义URL表单 -->
						<div id="activiti-panel-newWorkAddPage" data-options="fit:true">加载自定义URL表单失败
						</div>
						<script type="text/javascript">
							$(function(){
								$("#activiti-panel-newWorkAddPage").panel({
									<c:choose>
										<c:when test="${definitionkey eq 'addOaAccess'}">
											href: '${businessUrl }?type=handle',
											//href: 'act/workHandlePage.do?instanceid'
										</c:when>
										<c:otherwise>
											href: '${businessUrl }?type=add',
										</c:otherwise>
									</c:choose>
									cache: false,
									maximized: true,
									border: false
								});
							});
						</script>
					</c:otherwise>
				</c:choose>
			</div>
			<div data-options="region:'south',border:false">
				<div class="buttonDivR">
					<a href="javascript:;" id="activiti-next-newWorkAddPage" class="easyui-linkbutton" data-options="iconCls:'button-next'">保存并转交下一步</a>
					<a href="javascript:;" id="activiti-save-newWorkAddPage" class="easyui-linkbutton" data-options="iconCls:'button-save'">保存</a>
					<a href="javascript:;" id="activiti-savePrev-newWorkAddPage" class="easyui-linkbutton" data-options="iconCls:'button-back'">保存返回</a>
					<a href="javascript:;" id="activiti-giveup-newWorkAddPage" class="easyui-linkbutton" data-options="iconCls:'button-giveup'">取消</a>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" name="definitionkey" id="definitionkey" value="${definitionkey }" />
	<input type="hidden" name="processid" id="processid"/>
	<div id="activiti-dialog-newWorkAddPage"></div>
	<script type="text/javascript">
	
		var $activiti_dialog = $('activiti-dialog-newWorkAddPage');
	
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

			$("#activiti-giveup-newWorkAddPage").click(function(){
				$.messager.confirm("提醒", "确定不保存该工作吗？", function(r){
					if(r){
						location.href = "act/newWorkPage.do";
					}
				});
			});
			$("#activiti-next-newWorkAddPage").click(function(){
				// 自定义URL表单时调用自定义保存方法
				<c:choose>
					<c:when test="${formType == 'formkey' }">save(1, null);</c:when>
					<c:otherwise>customSave(1)</c:otherwise>
				</c:choose>
			});
			$("#activiti-save-newWorkAddPage").click(function(){
				<c:choose>
					<c:when test="${formType == 'formkey' }">save(2, null);</c:when>
					<c:otherwise>customSave(2)</c:otherwise>
				</c:choose>
			});
			$("#activiti-savePrev-newWorkAddPage").click(function(){
				<c:choose>
					<c:when test="${formType == 'formkey' }">save(3, null);</c:when>
					<c:otherwise>customSave(3)</c:otherwise>
				</c:choose>
			});
			$("#activiti-dialog-newWorkAddPage").dialog({
				title:'启动工作流',
				width:500,
				height: 320,
				modal:true,
				cache:false,
				closed:true,
				buttons:[
					{
						text:'确定转交',
						iconCls:'button-save',
						handler:function(){
		
							var type = $("input:radio:checked").val();
							
							// 指定接收人时，必须选择人员
							if(type == "2"){
		
								var flag = $("#activiti-form-startNewWorkPage").form('validate');
					
								if(!flag) {
									return false;
								}
							}

							$("#activiti-form-startNewWorkPage").submit();
						}
					},
					{
						text:'取消转交',
						iconCls:'button-giveup',
						handler:function(){
							$("#activiti-dialog-newWorkAddPage").dialog('close');
						}
					}
				],
				onClose: function() {
					location.href = "act/newWorkUpdatePage.do?id=" + $('#processid').val();
				}
			});
		});
		
		// 自定义URL表单保存方法
		function customSave(type) {
			workFormSubmit(save, {type: type});
		}
		
		// businessId: 在线Form表单时传入null；
		// 自定义URL表单时，在自定义workFormSubmit中回调save方法时传入id
		function save(type, businessId){

			var flag = $("#work-form-newWorkAddPage").form("validate");
			if(flag == false) return ;
			loading("提交中...");
			var json = $("#work-form-newWorkAddPage").serializeJSON();
			var title = $("#title").val();
			var definitionkey = $("#definitionkey").val();
			var html = $("#work-form-newWorkAddPage").formhtml();
			var processid = $("#processid").val();
			$.ajax({
				url:'act/addNewWork.do',
				type:'post',
				dataType:'json',
				data:{'process.id':processid ,'process.json':JSON.stringify(json), 'process.definitionkey': definitionkey, 'type':type, 'process.title':title, 'process.html':html, 'process.businessid':businessId},
				success:function(json){
					loaded();
					if(json.flag == true){
						$.messager.alert("提醒","保存成功");
						
						// 保存并转交效一步
						if(type == 1){
							
							$("#processid").val(json.id);
							$("#activiti-dialog-newWorkAddPage").dialog({
								href:'act/startNewWorkPage.do?id='+ json.id
							});
							$("#activiti-dialog-newWorkAddPage").dialog('open');

						// 保存
						} else if(type == 2){
							location.href = "act/newWorkUpdatePage.do?id="+ json.id;

						// 保存返回
						} else if(type == 3){
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
