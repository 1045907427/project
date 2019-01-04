KindEditor.plugin('zcheckbox', function(K) {
	var self = this, name = 'zcheckbox';
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
			'<label style="width:90px;float:left;">多选框选项</label>',
			'<div style="float:left;height:120px;width:320px;overflow:auto;">',
			'<dl class="select-item">',
			'<dd><a href="javascript:;" class="addRow">添加</a></dd>',
			'<dd style="clear:both"><div style="width:260px;float:left;">值：<input type="text" name="value" style="width:80px;" /> 选项：<input type="text" name="text" style="width:100px;" /> </div></dd>',
			'</dl>',
			'</div>',
			'</div>',
			'<div style="clear:both"></div>',
			'</form>',
			'</div>'
		].join('');
		var dialogWidth = 500, dialogHeight = 350;
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
						hunit = hBox.val();
					var style = '';
					if(width !== ''){
						style += 'width:'+ width+ wunit+ ';';
					}
					if(height !== ''){
						style += 'height:'+ height+ hunit+ ';';
					}
					var p = '';
					if(desc !== ''){
						p += 'title='+ desc+ ' ';
					}
					if(name !== ''){
						p += 'name='+ name+ ' ';
					}
					if(style != ''){
						p += 'style='+ style;
					}
					var values = new Array();
					var texts = new Array();
					var html = "";
					$("input[name=value]").each(function(){
						values.push($(this).val());
					});
					$("input[name=text]").each(function(){
						texts.push($(this).val());
					});
					for(var i=0;i<values.length;i++){
						html += '<input type="checkbox" id="a_'+i+'" '+p+' value="'+values[i]+'" /><label for="a_'+i+'">'+texts[i]+'</label>';
					}
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
		hBox = K('[name="h-unit"]', div);
		var addRow = K('.addRow', div);
		addRow.click(function(){
			$('<dd style="clear:both"><div style="width:260px;float:left;">值：<input type="text" name="value" style="width:80px;" /> 选项：<input type="text" name="text" style="width:100px;" /> </div><a href="javascript:;" class="deleteRow" >移除</a></dd>').appendTo(".select-item");
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