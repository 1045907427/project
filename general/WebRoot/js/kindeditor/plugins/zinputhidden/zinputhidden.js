KindEditor.plugin('zinputhidden', function(K) {
	var self = this, name = 'zinputhidden';
	self.plugin.textDialog = function(options) {
		var html = [
			'<div style="padding:5px;" id="extends-div">',
			'<form id="extends-form">',
			'<table class="extends-table">',
			'<tr>',
			'<td class="td1">字段描述：</td>',
			'<td class="td2"><input type="text" name="desc" /></td>',
			'<td class="td1">字段名称：</td>',
			'<td class="td2"><input name="name" class="easyui-validatebox" data-options="required:true" /></td>',
			'</tr>',
			'<tr>',
			'<td class="td1">数据长度：</td>',
			'<td class="td2"><input name="length" class="easyui-numberbox" /></td>',
			'<td class="td1">默认值：</td>',
			'<td class="td2"><input type="text" name="default" /></td>',
			'</tr>',
			'<tr>',
			'<td class="td1">控件宽度：</td>',
			'<td class="td2"><input name="width" class="easyui-numberbox" /></td>',
			'<td class="td1">控件高度：</td>',
			'<td class="td2"><input name="height" class="easyui-numberbox" /></td>',
			'</tr>',
			'</table>',
			'</form>',
			'</div>'
		].join('');
		var dialogWidth = 500, dialogHeight = 300;
		var dialog = self.createDialog({
			name : name,
			width : dialogWidth,
			height : dialogHeight,
			title : '<b>'+ self.lang(name)+ '</b>',
			body : html,
			yesBtn : {
				name : self.lang('yes'),
				click : function(e){
					if (dialog.isLoading) return;
					var flag = $("#extends-form").form('validate');
					if(flag == false) return ;
					var desc = $("input[name=desc]").val();
					var name = $("input[name=name]").val();
					var width = $("input[name=width]").val();
					var height = $("input[name=height]").val();
					var length = $("input[name=length]").val();
					var value = $("input[name=default]").val();
					self.insertHtml("<input type='hidden' title='"+desc+"' name='"+name+"' maxlength='"+length+"' value='"+value+"' style='width:"+width+"px;height:"+height+"px;' />");
					self.hideDialog().focus();
				}	
			}
		});
		return dialog;
	}
	self.plugin.text = {
		edit : function(){
			self.plugin.textDialog({});
			$.parser.parse('#extends-div');
		}
	};
	self.clickToolbar(name, self.plugin.text.edit);
});