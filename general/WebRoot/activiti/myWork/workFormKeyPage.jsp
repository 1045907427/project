<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>在线表单类型工作处理页面（formtype为formkey）</title>
  </head>
  <body>
  	<style type="text/css">
  		/*.formKeyTable{border:1px solid #aaaaaa;border-collapse:collapse;width:100%;margin-bottom:10px;}
  		.formKeyTable td{border:1px solid #aaaaaa;line-height:36px;text-indent:8px;}
        table {
            border-collapse: collapse;
            border:1px solid #7babcf;
        }*/
  	</style>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding:8px;">
			<form action="" method="post" id="activiti-form-workFormKeyPage">
                <iframe id="activiti-formcontent-workFormKeyPage" name="formcontent" width="100%" height="200px;" frameborder="0" src="act/workFormContentPage.do?id=${process.id }&type=handle">
                </iframe>
			</form>
			<input type="hidden" name="processid" id="processid" value="${process.id }" />
			<input type="hidden" name="definitionkey" id="definitionkey" value="${process.definitionkey }" />
			<div class="clear"></div>
            <div id="activiti-container1-workFormKeyPage" class="easyui-panel" data-options="border: false" title="附件">
                <div style="width: 100%; background: #fff;" id="activiti-attach-workFormKeyPage">附件加载中...</div>
            </div>
            <div id="activiti-container2-workFormKeyPage" class="easyui-panel" data-options="border: false" title="审批信息">
                <div style="width: 100%; background: #fff;" id="activiti-comment-workFormKeyPage">审批信息加载中...</div>
            </div>
		</div>
	</div>
	<div id="activiti-dialog-workFormKeyPage"></div>
	
	<script type="text/javascript">
		var taskkey = "${process.taskkey}";
	
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

            $('#activiti-attach-workFormKeyPage').attach({attach: true, businessid: '${param.id }', processid: '${param.processid }'});
            $('#activiti-comment-workFormKeyPage').comments({businesskey: '${process.businessid }', processid: '${process.id }', type: 'vertical', width: '120', agree: null});
		})(jQuery);
		
		function workFormSubmit(callBack) {

			loaded();
			loading('保存中...');
			var flag = $("#activiti-form-workFormKeyPage").form("validate");
			if(flag == false) {
			
				return ;
			}
			//var json = $("#activiti-form-workFormKeyPage").serializeJSON();
			var definitionkey = $("#definitionkey").val();
			var id = $("#processid").val();
			//var html = $("#activiti-form-workFormKeyPage").formhtml();
            var f = window.formcontent.parseForm();

            if(f == false) {
                return false;
            }

            var json = f.data;
            var html = f.html.replace(/^\s*(.*?)\s*$/, '$1');
			var type = '';
			$.ajax({
				url:'act/updateNewWork.do',
				type:'post',
				dataType:'json',
				data:{'json':JSON.stringify(json),'process.definitionkey': definitionkey, 'type':type,'html':html, 'process.id':id, 'process.title': '${fn:replace(title, "\'", "\\\'")}'},
				success:function(json){

					loaded();
					if(json.flag == true){
						$.messager.alert("提醒","保存成功");

						if(callBack.data) {
						
							callBack.data('');
							return false;
						}
					} else{
						$.messager.alert("提醒","保存失败");
					}
				}
			});
		
		}

    function setFrameHeight(height) {

        $('iframe#activiti-formcontent-workFormKeyPage').attr('height', (height + 40) +'px');
    }
		
	</script>
  </body>
</html>
