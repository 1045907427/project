KindEditor.plugin('zmacro', function(K) {
	var self = this, name = 'zmacro';
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
			'<label for="keRows" style="width:90px;">控件类型</label>',
			'<select name="type"><option value="text">签名</option><option value="date">日期</option></select>&nbsp; ',
			'</div>',
			'<div class="ke-dialog-row">',
			'<label for="keRows" style="width:90px;">节点标识</label>',
			'<input name="task" class="easyui-validatebox" data-options="required:true" />&nbsp; ',
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
						type = typeBox.val(),
						task = taskBox.val();
					var style = '';
					if(width !== ''){
						style += 'width:'+ width+ wunit+ ';';
					}
					if(height !== ''){
						style += 'height:'+ height+ hunit+ ';';
					}
					var html = '<input readonly="readonly"';
					if(desc !== ''){
						html += 'title='+ desc+ ' ';
					}
					if(name !== ''){
						html += 'name='+ name+ ' ';
					}
					if(type == 'text'){
						html += 'class="'+task+'_name"';
					}
					else if(type == 'date'){
						html += 'class="'+task+'_date" ';
					}
					if(style !== ''){
						html += 'style='+ style+ ' ';
					}
					html += '/>';
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
		typeBox = K('[name="type"]', div),
		taskBox = K('[name="task"]', div);
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