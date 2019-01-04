KindEditor.plugin('zinputtext', function(K) {
	var self = this, name = 'zinputtext';
	self.plugin.textDialog = function(options) {
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
			'<label for="keRows" style="width:90px;">数据类型</label>',
			'<select name="type"><option value="text">文字</option><option value="number">数字</option><option value="date">日期</option></select>&nbsp; ',
			'<span class="datatype text">长度：<input name="length" class="easyui-numberbox" style="width:80px;" /></span>',
			'<span class="datatype number" style="display:none">最大值：<input name="max" class="easyui-numberbox" style="width:50px;" />&nbsp;精度：<input name="precision" class="easyui-numberbox" style="width:50px;" /></span>',
			'<span class="datatype date" style="display:none"><select name="dateformat"><option value="yyyy-MM-dd">yyyy-MM-dd</option><option value="yyyy-MM-dd HH:mm:ss">yyyy-MM-dd HH:mm:ss</option><option value="yyyy-MM-dd HH:mm">yyyy-MM-dd HH:mm</option><option value="HH:mm:ss">HH:mm:ss</option></select>&nbsp; ',
			'</span>',
			'</div>',
			'<div class="ke-dialog-row">',
			'<label for="keRows" style="width:90px;">选项</label>',
			'<input type="checkbox" name="required" class="extends-checkbox" />必填',
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
						length = lengthBox.val(),
						type = typeBox.val(),
						max = maxBox.val(),
						precision = precisionBox.val(),
						dateformat = dateformatBox.val(),
						required = $("input[name=required]").attr("checked"),
						width = widthBox.val(),
						wunit = wBox.val(),
						height = heightBox.val(),
						hunit = hBox.val();
					var style = '';
					if(width !== ''){
						style += 'width:'+ width+ wunit+ ';';
					}
					if(height !== ''){
						style += 'height:'+ height+ hunit+ ';';
					}
					var cls = '';
					var html = '<input type="text" ';
					if(desc !== ''){
						html += 'title='+ desc+ ' ';
					}
					if(name !== ''){
						html += 'name='+ name+ ' ';
					}
					if(type == 'text'){
						if(length !== ''){
							html += 'maxlength='+ length+ ' ';
						}
						cls = 'easyui-validatebox '; 
					}
					else if(type == 'number'){
						cls = 'easyui-numberbox ';
						html += 'data-options="';
						if(max !== ''){
							html += 'max:'+ max+ ', ';
						}
						if(precision !== ''){
							html += 'precision:'+ precision;
						}
						html += '"';
					}
					else if(type == 'date'){
						cls = 'easyui-validatebox ';
						html += 'onfocus="WdatePicker({\'dateFmt\':\''+ dateformat+ '\'})" ';
					}
					if(required == "checked"){
						html += 'required="required" ';
					}
					if(style !== ''){
						html += 'style='+ style+ ' class='+ cls+ ' ';
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
		lengthBox = K('[name="length"]', div),
		maxBox = K('[name="max"]', div),
		precisionBox = K('[name="precision"]', div),
		dateformatBox = K('[name="dateformat"]', div),
		requiredBox = K('[name="required"]', div),
		widthBox = K('[name="width"]', div),
		wBox = K('[name="w-unit"]', div),
		heightBox = K('[name="height"]', div),
		hBox = K('[name="h-unit"]', div),
		typeBox = K('[name="type"]', div);
		typeBox.change(function(){
			var type = $(this).val();
			$(".datatype").hide().each(function(i){
				if($(this).hasClass(type)){
					$(this).show();
				}
			});
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