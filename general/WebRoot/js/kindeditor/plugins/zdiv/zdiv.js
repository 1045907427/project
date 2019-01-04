KindEditor.plugin('zdiv', function(K) {
	var self = this, name = 'zdiv';
	self.plugin.textDialog = function(options) {
		var html = [
			'<div style="padding:20px;" id="extends-div">',
			'<form id="extends-form">',
			'<div class="ke-dialog-row">',
			'<label for="keStyle" style="width:90px;">自定义样式</label>',
			'<textarea id="keStyle" name="style" style="width:350px;height:150px;"></textarea>',
			'</div>',
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
					var style = styleBox.val();
					var html = '<div style="'+style+'"></div>';
					self.insertHtml(html);
					self.hideDialog().focus();
				}
			}
		}),
		div = dialog.div,
		styleBox = K('[name=style]', div);
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