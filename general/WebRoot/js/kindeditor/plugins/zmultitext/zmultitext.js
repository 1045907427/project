KindEditor.plugin('zmultitext', function(K) {
	var self = this, name = 'zmultitext';
	self.plugin.textDialog = function(options) {
		var html = '';
		var html = [
			'<div style="padding:20px;" id="extends-div">',
			'<form id="extends-form">',
			'<div class="ke-dialog-row">',
			'<label for="keRows" style="width:90px;">字段描述</label>',
			'<input type="text" name="desc" />',
			'</div>',
			'<div class="ke-dialog-row">',
			'<label for="keRows" style="width:90px;">字段名称</label>',
			'<input name="name" class="easyui-validatebox" data-options="required:true" />',
			'</div>',
			'<div class="ke-dialog-row">',
			'<label for="keRows" style="width:90px;">控件宽度</label>',
			'<input name="width" class="easyui-numberbox" style="width:80px;" />',
			'<select name="w-unit"><option value="px">px</option><option value="%">%</option></select>',
			'</div>',
			'<div class="ke-dialog-row">',
			'<label for="keRows" style="width:90px;">控件高度</label>',
			'<input name="height" class="easyui-numberbox" style="width:80px;" />',
			'<select name="h-unit"><option value="px">px</option><option value="%">%</option></select>',
			'</div>',
			'<div class="ke-dialog-row">',
			'<label for="keRows" style="width:90px;">列数</label>',
			'<input name="cols" class="easyui-numberbox" />',
			'</div>',
			'<div class="ke-dialog-row">',
			'<label for="keRows" style="width:90px;">行数</label>',
			'<input name="rows" class="easyui-numberbox" />',
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
					var desc = descBox.val(),
						name = nameBox.val(),
						width = widthBox.val(),
						height = heightBox.val(),
						wunit = wBox.val(),
						hunit = hBox.val(),
						cols = colsBox.val(),
						rows = rowsBox.val();
					var style = '';
					if(width !== ''){
						style += 'width:'+ width+ wunit+ ';';
					}
					if(height !== ''){
						style += 'height:'+ height+ hunit+ ';';
					}
					var html = '<textarea ';
					if(desc !== ''){
						html += 'title='+ desc+ ' ';
					}
					if(name !== ''){
						html += 'name='+ name+ ' ';
					}
					if(cols !== ''){
						html += 'cols='+ cols+ ' ';
					}
					if(rows !== ''){
						html += 'rows='+ rows+ ' ';
					}
					if(style !== ''){
						html += 'style='+ style+ ' ';
					}
					html += '></textarea>';
					self.insertHtml(html);
					self.hideDialog().focus();
				}
			}
		}),
		div = dialog.div,
		descBox = K('[name="desc"]', div),
		nameBox = K('[name="name"]', div),
		widthBox = K('[name="width"]', div),
		heightBox = K('[name="height"]', div),
		wBox = K('[name="w-unit"]', div),
		hBox = K('[name="h-unit"]', div),
		colsBox = K('[name="cols"]', div),
		rowsBox = K('[name="rows"]', div);
		return dialog;
	};
	self.plugin.text = {
		edit : function(){
			self.plugin.textDialog({});
			$.parser.parse('#extends-div');
		}
	};
	self.clickToolbar(name, self.plugin.text.edit);
});