<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>在线表单类型工作处理页面（formtype为formkey）</title>
  </head>
  <body>
  	<style type="text/css">
  		.formKeyTable{border:1px solid #aaaaaa;border-collapse:collapse;width:100%;margin-bottom:10px;}
  		.formKeyTable td{border:1px solid #aaaaaa;line-height:36px;text-indent:8px;}
  	</style>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding:8px;">
			<form action="" method="post" id="activiti-form-workFormKeyViewPage">
                <iframe id="activiti-formcontent-workFormKeyViewPage" name="formcontent" width="100%" height="200px;" frameborder="0" src="act/workFormContentPage.do?id=${process.id }&type=view">
                </iframe>
			</form>
			<input type="hidden" name="processid" id="processid" value="${process.id }" />
			<input type="hidden" name="definitionkey" id="definitionkey" value="${process.definitionkey }" />
			<div class="clear"></div>
            <div id="activiti-container1-workFormKeyViewPage" class="easyui-panel" data-options="border: false" title="附件">
                <div style="width: 100%; background: #fff;" id="activiti-attach-workFormKeyPage">附件加载中...</div>
            </div>
            <div id="activiti-container2-workFormKeyViewPage" class="easyui-panel" data-options="border: false" title="审批信息">
                <div style="width: 100%; background: #fff;" id="activiti-comment-workFormKeyViewPage">审批信息加载中...</div>
            </div>
		</div>
	</div>
	<div id="activiti-dialog-workFormKeyViewPage"></div>
	<script type="text/javascript">
		$(function(){
            $('#activiti-attach-workFormKeyPage').attach({attach: false, businessid: '${process.businessid }', processid: '${process.id }'});
            $('#activiti-comment-workFormKeyViewPage').comments({businesskey: '${process.businessid }', processid: '${process.id }', type: 'vertical', width: '120', agree: null});
			$("#activiti-pic-workFormKeyViewPage").click(function(){
				$("#activiti-dialog-workFormKeyViewPage").dialog({
					title:'审批流程图',
					width:600,
					height:450,
					maximizable:true,
					href:'act/commentImgPage.do?type=1&id=${process.instanceid}'
				});
				$("#activiti-dialog-workFormKeyViewPage").dialog('open');
			});
			$("#activiti-list-workFormKeyViewPage").click(function(){
				$("#activiti-dialog-workFormKeyViewPage").dialog({
					title:'审批历史',
					width:600,
					height:400,
					href:'act/commentListPage.do?id=${process.instanceid}'
				});
				$("#activiti-dialog-workFormKeyViewPage").dialog('open');
			});
			$("#activiti-recover-workFormKeyViewPage").click(function(){
				$.messager.confirm("提醒", "确定撤销该流程，撤销后可以在我的草稿中重新提交！", function(r){
					if(r){
						loading("工作流撤销中...");
						$.ajax({
							url:'act/backOutWork.do',
							dataType:'json',
							type:'post',
							data:'id=${process.id}',
							success:function(json){
								loaded();
								if(json.flag == true){
									$.messager.alert("提醒", "撤销成功，请刷新列表");
									top.$("#tt").tabs('close', '工作查看');
								}
								else{
									$.messager.alert("提醒", "撤销失败");
								}
							}
						});
					}
				});
			});
			$("#activiti-dialog-workFormKeyViewPage").dialog({
				modal:true,
				cache:false,
				closed:true
			});
		});
		function toPrint(id){
			var w = window.open('act/workPrintPage.do?id='+ id, '打印');
            w.focus();
		}

        function setFrameHeight(height) {

            $('iframe#activiti-formcontent-workFormKeyViewPage').attr('height', (height + 40) +'px');
        }
	</script>
  </body>
</html>
