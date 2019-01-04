KindEditor.plugin('zdictionary', function(K) {
	var self = this, name = 'zdictionary';
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
			'<label for="dictionaryChoose" style="width:90px;">选择编码字典</label>',
			'<input name="dictionary" id="dictionaryChoose" />',
			'</div>',
			'</form>',
			'</div>'
		].join('');
		var dialogWidth = 500, dialogHeight = 300;
		var dialog = self.createDialog({
			name : name,
			width : dialogWidth,
			height : dialogHeight,
			zIndex : 8998,
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
						dictionary = dictionaryBox.val();
					var style = '';
					if(width !== ''){
						style += 'width:'+ width+ wunit+ ';';
					}
					if(height !== ''){
						style += 'height:'+ height+ hunit+ ';';
					}
					var html = '<select ';
					if(desc !== ''){
						html += 'title='+ desc+ ' ';
					}
					if(name !== ''){
						html += 'name='+ name+ ' ';
					}
					if(style != ''){
						html += 'style='+ style+ ' ';
					}
					html += '>';
					html += options;
					html += '</select>';
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
		dictionaryBox = K('[name="dictionary"]', div);
		var options = "";
		$("#dictionaryChoose").combogrid({
			width:200,
         	panelWidth:400,
           	idField:'type',
           	textField:'typename',
           	rownumbers:true,
           	filter:function(q,row){
           		var id = row.type;
           		var text = row.typename;
           		if(id.indexOf(q)==0 || text.indexOf(q)==0){
           			return true;
           		}else{
           			return false;
           		}
           	},
			   columns:[[  
			      {field:'type',title:'编码类型',width:150},  
			      {field:'typename',title:'编码类型名称',width:200}
			   ]],
   			url:'sysCode/showSysCodeTypes.do',
   			onChange:function(newValue, oldValue){
   				options = "";
   				$.getJSON("sysCode/showSysCodeList.do", {type:newValue} , function(json){
   					for(var i=0; i<json.rows.length; i++){
   						var j = json.rows[i];
   						options += '<option value="'+j.code+'">'+j.codename+'</option>'; 
   					}
   				});
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