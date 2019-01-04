KindEditor.plugin('zusers', function(K) {
	var self = this, name = 'zusers';
	self.plugin.textDialog = function(options) {
		var html = [
			'<div style="padding:20px;" id="extends-div">',
			'<form id="extends-form">',
			'<div class="ke-dialog-row">',
			'<label for="keDesc" style="width:90px;">字段描述</label>',
			'<input type="text" id="keDesc" name="desc" />',
			'</div>',
			'<div class="ke-dialog-row">',
			'<label for="keName" style="width:90px;">字段名称</label>',
			'<input name="name" id="keName" class="easyui-validatebox" data-options="required:true" />',
			'</div>',
			'<div class="ke-dialog-row">',
			'<label for="keWidth" style="width:90px;">控件宽度</label>',
			'<input name="width" id="keWidth" class="easyui-numberbox" style="width:80px;" />',
			'<select name="w-unit"><option value="px">px</option><option value="%">%</option></select>',
			'</div>',
			'<div class="ke-dialog-row">',
			'<label for="keHeight" style="width:90px;">控件高度</label>',
			'<input name="height" id="keHeight" class="easyui-numberbox" style="width:80px;" />',
			'<select name="h-unit"><option value="px">px</option><option value="%">%</option></select>',
			'</div>',
			'<div class="ke-dialog-row">',
			'<label for="keRows" style="width:90px;">选项</label>',
			'<input type="checkbox" name="required" class="extends-checkbox" />必填',
			'</div>',
			'</form>',
			'</div>'
		].join('');
		var dialogWidth = 500, dialogHeight = 300;
		var time = new Date().getTime();
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
						required = $("input[name=required]").attr("checked"),
						width = widthBox.val(),
						height = heightBox.val(),
						wunit = wBox.val(),
						hunit = hBox.val();
					var style = '';
					if(width !== ''){
						style += 'width:'+ width+ wunit+ ';';
					}
					if(height !== ''){
						style += 'height:'+ height+ hunit+ ';';
					}
					var html = '<input type="text" id="text_'+time+'" readonly="readonly" ';
					if(desc !== ''){
						html += 'title='+ desc+ ' ';
					}
					if(name !== ''){
						html += 'name='+ name+ ' ';
					}
					if(style != ''){
						html += 'style='+ style+ ' ';
					}
					if(required == "checked"){
						html += 'required="required" class="easyui-validatebox"';
					}
					html += ' />';
					html += '<input type="hidden" name="'+name+'_id" id="hidden_'+time+'" /> ';
					html += '<a href="javascript:;" onclick="chooseUsers(\''+time+'\');" id="btn_'+time+'">选择</a>';
					html += '<div id="dialog_'+time+'" style="display:none;"></div>';
					self.insertHtml(html);
					self.hideDialog().focus();
				}
			}
		}),
		div = dialog.div
		descBox = K('[name="desc"]', div),
		nameBox = K('[name="name"]', div),
		widthBox = K('[name="width"]', div),
		heightBox = K('[name="height"]', div),
		wBox = K('[name="w-unit"]', div),
		hBox = K('[name="h-unit"]', div);
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