$.extend($.fn.datagrid.defaults, {
	groupHeight: 25,
	expanderWidth: 30
});

var groupview = $.extend({}, $.fn.datagrid.defaults.view, {
	render: function(target, container, frozen){
		var table = [];
		var groups = this.groups;
		for(var i=0; i<groups.length; i++){
			table.push(this.renderGroup.call(this, target, i, groups[i], frozen));
		}
		$(container).html(table.join(''));
	},

	renderGroup: function(target, groupIndex, group, frozen){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var fields = $(target).datagrid('getColumnFields', frozen);

		if (frozen){
			if (!(opts.rownumbers || (opts.frozenColumns && opts.frozenColumns.length))){
				return '';
			}
		}

		var table = [];
		table.push('<div class="datagrid-group" group-index=' + groupIndex + '>');
		if ((frozen && (opts.rownumbers || opts.frozenColumns.length)) ||
				(!frozen && !(opts.rownumbers || opts.frozenColumns.length))){
			table.push('<span class="datagrid-group-expander">');
			table.push('<span class="datagrid-row-expander datagrid-row-collapse">&nbsp;</span>');
			table.push('</span>');
		}
		if (!frozen){
			table.push('<span class="datagrid-group-title">');
			table.push(opts.groupFormatter.call(target, group.value, group.rows));
			table.push('</span>');
		}
		table.push('</div>');

		table.push('<table class="datagrid-btable" cellspacing="0" cellpadding="0" border="0"><tbody>');
		var index = group.startIndex;
		for(var j=0; j<group.rows.length; j++) {
			var css = opts.rowStyler ? opts.rowStyler.call(target, index, group.rows[j]) : '';
			var classValue = '';
			var styleValue = '';
			if (typeof css == 'string'){
				styleValue = css;
			} else if (css){
				classValue = css['class'] || '';
				styleValue = css['style'] || '';
			}

			var cls = 'class="datagrid-row ' + (index % 2 && opts.striped ? 'datagrid-row-alt ' : ' ') + classValue + '"';
			var style = styleValue ? 'style="' + styleValue + '"' : '';
			var rowId = state.rowIdPrefix + '-' + (frozen?1:2) + '-' + index;
			table.push('<tr id="' + rowId + '" datagrid-row-index="' + index + '" ' + cls + ' ' + style + '>');
			table.push(this.renderRow.call(this, target, fields, frozen, index, group.rows[j]));
			table.push('</tr>');
			index++;
		}
		table.push('</tbody></table>');
		return table.join('');
	},

	bindEvents: function(target){
		var state = $.data(target, 'datagrid');
		var dc = state.dc;
		var body = dc.body1.add(dc.body2);
		var clickHandler = ($.data(body[0],'events')||$._data(body[0],'events')).click[0].handler;
		body.unbind('click').bind('click', function(e){
			var tt = $(e.target);
			var expander = tt.closest('span.datagrid-row-expander');
			if (expander.length){
				var gindex = expander.closest('div.datagrid-group').attr('group-index');
				if (expander.hasClass('datagrid-row-collapse')){
					$(target).datagrid('collapseGroup', gindex);
				} else {
					$(target).datagrid('expandGroup', gindex);
				}
			} else {
				clickHandler(e);
			}
			e.stopPropagation();
		});
	},

	onBeforeRender: function(target, rows){
		var state = $.data(target, 'datagrid');
		var opts = state.options;

		initCss();

		var groups = [];
		for(var i=0; i<rows.length; i++){
			var row = rows[i];
			var group = getGroup(row[opts.groupField]);
			if (!group){
				group = {
					value: row[opts.groupField],
					rows: [row]
				};
				groups.push(group);
			} else {
				group.rows.push(row);
			}
		}

		var index = 0;
		var newRows = [];
		for(var i=0; i<groups.length; i++){
			var group = groups[i];
			group.startIndex = index;
			index += group.rows.length;
			newRows = newRows.concat(group.rows);
		}

		state.data.rows = newRows;
		this.groups = groups;

		var that = this;
		setTimeout(function(){
			that.bindEvents(target);
		},0);

		function getGroup(value){
			for(var i=0; i<groups.length; i++){
				var group = groups[i];
				if (group.value == value){
					return group;
				}
			}
			return null;
		}
		function initCss(){
			if (!$('#datagrid-group-style').length){
				$('head').append(
					'<style id="datagrid-group-style">' +
					'.datagrid-group{height:'+opts.groupHeight+'px;overflow:hidden;font-weight:bold;border-bottom:1px solid #ccc;}' +
					'.datagrid-group-title,.datagrid-group-expander{display:inline-block;vertical-align:bottom;height:100%;line-height:'+opts.groupHeight+'px;padding:0 4px;}' +
					'.datagrid-group-expander{width:'+opts.expanderWidth+'px;text-align:center;padding:0}' +
					'.datagrid-row-expander{margin:'+Math.floor((opts.groupHeight-16)/2)+'px 0;display:inline-block;width:16px;height:16px;cursor:pointer}' +
					'</style>'
				);
			}
		}
	}
});

$.extend($.fn.datagrid.methods, {
	groups:function(jq){
		return jq.datagrid('options').view.groups;
	},
    expandGroup:function(jq, groupIndex){
        return jq.each(function(){
            var view = $.data(this, 'datagrid').dc.view;
            var group = view.find(groupIndex!=undefined ? 'div.datagrid-group[group-index="'+groupIndex+'"]' : 'div.datagrid-group');
            var expander = group.find('span.datagrid-row-expander');
            if (expander.hasClass('datagrid-row-expand')){
                expander.removeClass('datagrid-row-expand').addClass('datagrid-row-collapse');
                group.next('table').show();
            }
            $(this).datagrid('fixRowHeight');
        });
    },
    collapseGroup:function(jq, groupIndex){
        return jq.each(function(){
            var view = $.data(this, 'datagrid').dc.view;
            var group = view.find(groupIndex!=undefined ? 'div.datagrid-group[group-index="'+groupIndex+'"]' : 'div.datagrid-group');
            var expander = group.find('span.datagrid-row-expander');
            if (expander.hasClass('datagrid-row-collapse')){
                expander.removeClass('datagrid-row-collapse').addClass('datagrid-row-expand');
                group.next('table').hide();
            }
            $(this).datagrid('fixRowHeight');
        });
    }
});

$.extend(groupview, {
	refreshGroupTitle: function(target, groupIndex){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var dc = state.dc;
		var group = this.groups[groupIndex];
		var span = dc.body2.children('div.datagrid-group[group-index=' + groupIndex + ']').find('span.datagrid-group-title');
		span.html(opts.groupFormatter.call(target, group.value, group.rows));
	},

	insertRow: function(target, index, row){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var dc = state.dc;
		var group = null;
		var groupIndex;

		if (!state.data.rows.length){
			$(target).datagrid('loadData', [row]);
			return;
		}

		for(var i=0; i<this.groups.length; i++){
			if (this.groups[i].value == row[opts.groupField]){
				group = this.groups[i];
				groupIndex = i;
				break;
			}
		}
		if (group){
			if (index == undefined || index == null){
				index = state.data.rows.length;
			}
			if (index < group.startIndex){
				index = group.startIndex;
			} else if (index > group.startIndex + group.rows.length){
				index = group.startIndex + group.rows.length;
			}
			$.fn.datagrid.defaults.view.insertRow.call(this, target, index, row);

			if (index >= group.startIndex + group.rows.length){
				_moveTr(index, true);
				_moveTr(index, false);
			}
			group.rows.splice(index - group.startIndex, 0, row);
		} else {
			group = {
				value: row[opts.groupField],
				rows: [row],
				startIndex: state.data.rows.length
			}
			groupIndex = this.groups.length;
			dc.body1.append(this.renderGroup.call(this, target, groupIndex, group, true));
			dc.body2.append(this.renderGroup.call(this, target, groupIndex, group, false));
			this.groups.push(group);
			state.data.rows.push(row);
		}

		this.refreshGroupTitle(target, groupIndex);

		function _moveTr(index,frozen){
			var serno = frozen?1:2;
			var prevTr = opts.finder.getTr(target, index-1, 'body', serno);
			var tr = opts.finder.getTr(target, index, 'body', serno);
			tr.insertAfter(prevTr);
		}
	},

	updateRow: function(target, index, row){
		var opts = $.data(target, 'datagrid').options;
		$.fn.datagrid.defaults.view.updateRow.call(this, target, index, row);
		var tb = opts.finder.getTr(target, index, 'body', 2).closest('table.datagrid-btable');
		var groupIndex = parseInt(tb.prev().attr('group-index'));
		this.refreshGroupTitle(target, groupIndex);
	},

	deleteRow: function(target, index){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var dc = state.dc;
		var body = dc.body1.add(dc.body2);

		var tb = opts.finder.getTr(target, index, 'body', 2).closest('table.datagrid-btable');
		var groupIndex = parseInt(tb.prev().attr('group-index'));

		$.fn.datagrid.defaults.view.deleteRow.call(this, target, index);

		var group = this.groups[groupIndex];
		if (group.rows.length > 1){
			group.rows.splice(index-group.startIndex, 1);
			this.refreshGroupTitle(target, groupIndex);
		} else {
			body.children('div.datagrid-group[group-index='+groupIndex+']').remove();
			for(var i=groupIndex+1; i<this.groups.length; i++){
				body.children('div.datagrid-group[group-index='+i+']').attr('group-index', i-1);
			}
			this.groups.splice(groupIndex, 1);
		}

		var index = 0;
		for(var i=0; i<this.groups.length; i++){
			var group = this.groups[i];
			group.startIndex = index;
			index += group.rows.length;
		}
	}
});
$.extend($.fn.validatebox.defaults.rules, {
   idcard: {
        validator: function (value, param) {
            return idCard(value);
        },
        message:'请输入正确的身份证号码'
    } ,
	maxByteLength:{
    	validator:function(value, param){
	    	value = value || "";
	        var length = value.length;
	        for (var i = 0; i < value.length; i++) {
	            if (value.charCodeAt(i) > 127) {
	                length++;
	            }
	        }
	        return length <= param;
    	},
    	message:'请输入最大长度为{0}个字节(一个中文字算2个字节)'
	},
	byteRangeLength:{
		validator:function(value,param){
			value = value || "";
	        var length = value.length;
	        for (var i = 0; i < value.length; i++) {
	            if (value.charCodeAt(i) > 127) {
	                length++;
	            }
	        }
	        return (length >= param[0] && length <= param[1]);
		},
		message:'请输入{0}-{1}个字节(一个中文字算2个字节)'
	},
    minLength: {
        validator: function(value, param){
            return value.length >= param[0];
        },
        message: '请输入至少（2）个字符.'
    },
    length:{validator:function(value,param){
        var len=$.trim(value).length;
            return len>=param[0]&&len<=param[1];
        },
            message:"输入内容长度必须介于{0}和{1}之间."
        },
    validLength:{
	    validator:function(value, len){
		    var reg=eval("/^[A-Za-z0-9]{"+len+"}$/");//正则表达式使用变量
		    return reg.test(value)
	    },
	    message:'请输入{0}位字符!'
    },
    phone : {// 验证电话号码
        validator : function(value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message : '格式不正确,请使用下面格式:020-88888888'
    },
    mobile : {// 验证手机号码
        validator : function(value) {
            return /^(13|14|15|17|18)\d{9}$/i.test(value);
        },
        message : '手机号码格式不正确'
    },
    phoneOrMobile: {// 验证电话号码 或者手机号码
        validator : function(value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value)||/^(13|15|18)\d{9}$/i.test(value);
        },
        message : '格式不正确,请使用下面格式:020-88888888或者手机号码'
    },
    intOrFloat : {// 验证整数或小数
        validator : function(value) {
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message : '请输入数字，并确保格式正确'
    },
    currency : {// 验证货币
        validator : function(value) {
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message : '货币格式不正确'
    },
    qq : {// 验证QQ,从10000开始
        validator : function(value) {
            return /^[1-9]\d{4,9}$/i.test(value);
        },
        message : 'QQ号码格式不正确'
    },
    integer : {// 验证正整数
        validator : function(value) {
            return /^[0-9]+\d*$/i.test(value);
        },
        message : '请输入整数'
    },
    signinter:{// 验证带符号整数
        validator : function(value) {
        	return /^[-+]?[1-9]+\d*$/i.test(value);
	    },
	    message : '请输入整数'
    },
    number: {//验证数字
	        validator: function (value, param) {
	        return /^\d+$/.test(value);
	    },
	    message: '请输入数字'
    },
    signfloat:{
    	validator: function(value){
    		return /^[-+]?([1-9]\d*|0)(\.\d*)?$/i.test(value);
    	},
    	message:'请输入数字'
    },
    age : {// 验证年龄
        validator : function(value) {
            return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
        },
        message : '年龄必须是0到120之间的整数'
    },
    chinese : {// 验证中文
        validator : function(value) {
            return /^[\u4e00-\u9fa5]+$/i.test(value);
        },
        message : '请输入中文'
    },
    english : {// 验证英语
        validator : function(value) {
            return /^[A-Za-z]+$/i.test(value);
        },
        message : '请输入英文'
    },
    unnormal : {// 验证是否包含空格和非法字符
        validator : function(value) {
            return /.+/i.test(value);
        },
        message : '输入值不能为空和包含其他非法字符'
    },
    illegalChar : {//验证是否包含特色字符
    	 validator : function(value) {
	    	var pattern=/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im;
	        if(pattern.test(value)){
	            return false;
	        }
	        return true;
	    },
	    message : '不能输入特殊字符串'
    },
    username : {// 验证用户名
        validator : function(value) {
            return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
        },
        message : '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
    },
    dbtable : {// 验证数据库表名，列名，索引等
        validator : function(value) {
            return /^([a-zA-Z]+)|([a-zA-Z][a-zA-Z0-9_$]+)$/i.test(value);
        },
        message : '请以字母开头，任意字母、数字、“_”和“ $”'
    },
    faxno : {// 验证传真
        validator : function(value) {
//            return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value);
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message : '传真号码不正确'
    },
    zip : {// 验证邮政编码
        validator : function(value) {
            return /^[1-9]\d{5}$/i.test(value);
        },
        message : '邮政编码格式不正确'
    },
    ip : {// 验证IP地址
        validator : function(value) {
            return /d+.d+.d+.d+/i.test(value);
        },
        message : 'IP地址格式不正确'
    },
    name : {// 验证姓名，可以是中文或英文
            validator : function(value) {
                return /^[\Α-\￥]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value);
            },
            message : '请输入姓名'
    },
    date : {// 验证姓名，可以是中文或英文
        validator : function(value) {
         //格式yyyy-MM-dd或yyyy-M-d
            return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value);
        },
        message : '请输入合适的日期格式'
    },
    msn:{
        validator : function(value){
        	return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
    	},
    	message : '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
    },
    safepass: {
        validator: function (value, param) {
            return safePassword(value);
        },
        message: '密码由字母和数字组成，至少6位'
    },
    same:{
        validator : function(value, param){
            if($("#"+param[0]).val() != "" && value != ""){
                return $("#"+param[0]).val() == value;
            }else{
                return true;
            }
        },
        message : '两次输入的密码不一致！'
    } ,
    email:{
    	validator : function(value){
	    	return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
		},
		message : '请输入有效的邮箱(例：abc@hotnail.com)'
    },
  //验证角色别名是否唯一
	authorityAliasCheck:{
    	validator: function (value, param) {
    		var flag = false;
    		if(param&&value==param[0]){
    			flag = true;
    		}else{
	        	$.ajax({
		            url :'accesscontrol/checkAuthorityAlias.do?alias='+value,
		            type:'post',
		            dataType:'json',
		            async: false,
		            success:function(json){
		            	flag =  json.flag;
		            }
		        });
    		}
	        return flag;
        },
        message:'角色别名已重复，请重新输入!'
    }

});
/* 密码由字母和数字组成，至少6位 */
var safePassword = function (value) {
    return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(value));
}
var idCard = function (value) {
    if (value.length == 18 && 18 != value.length) return false;
    var number = value.toLowerCase();
    var d, sum = 0, v = '10x98765432', w = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2], a = '11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91';
    var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/);
    if (re == null || a.indexOf(re[1]) < 0) return false;
    if (re[2].length == 9) {
        number = number.substr(0, 6) + '19' + number.substr(6);
        d = ['19' + re[4], re[5], re[6]].join('-');
    } else d = [re[9], re[10], re[11]].join('-');
    //if (!isDateTime.call(d, 'yyyy-MM-dd')) return false;
    for (var i = 0; i < 17; i++) sum += number.charAt(i) * w[i];
    return (re[2].length == 9 || number.charAt(17) == v.charAt(sum % 11));
}

if ($.fn.pagination){
	$.fn.pagination.defaults.beforePageText = '第';
	$.fn.pagination.defaults.afterPageText = '共{pages}页';
	$.fn.pagination.defaults.displayMsg = '显示{from}到{to},共{total}记录';
}
if ($.fn.datagrid){
	$.fn.datagrid.defaults.loadMsg = '正在处理，请稍待。。。';
}
if ($.fn.treegrid && $.fn.datagrid){
	$.fn.treegrid.defaults.loadMsg = $.fn.datagrid.defaults.loadMsg;
}
if ($.messager){
	$.messager.defaults.ok = '确定';
	$.messager.defaults.cancel = '取消';
}
$.map(['validatebox','textbox','filebox','searchbox',
		'combo','combobox','combogrid','combotree',
		'datebox','datetimebox','numberbox',
		'spinner','numberspinner','timespinner','datetimespinner'], function(plugin){
	if ($.fn[plugin]){
		$.fn[plugin].defaults.missingMessage = '该输入项为必输项';
	}
});
if ($.fn.validatebox){
	$.fn.validatebox.defaults.rules.email.message = '请输入有效的电子邮件地址';
	$.fn.validatebox.defaults.rules.url.message = '请输入有效的URL地址';
	$.fn.validatebox.defaults.rules.length.message = '输入内容长度必须介于{0}和{1}之间';
	$.fn.validatebox.defaults.rules.remote.message = '请修正该字段';
}
if ($.fn.calendar){
	$.fn.calendar.defaults.weeks = ['日','一','二','三','四','五','六'];
	$.fn.calendar.defaults.months = ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'];
}
if ($.fn.datebox){
	$.fn.datebox.defaults.currentText = '今天';
	$.fn.datebox.defaults.closeText = '关闭';
	$.fn.datebox.defaults.okText = '确定';
	$.fn.datebox.defaults.formatter = function(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	};
	$.fn.datebox.defaults.parser = function(s){
		if (!s) return new Date();
		var ss = s.split('-');
		var y = parseInt(ss[0],10);
		var m = parseInt(ss[1],10);
		var d = parseInt(ss[2],10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
			return new Date(y,m-1,d);
		} else {
			return new Date();
		}
	};
}
if ($.fn.datetimebox && $.fn.datebox){
	$.extend($.fn.datetimebox.defaults,{
		currentText: $.fn.datebox.defaults.currentText,
		closeText: $.fn.datebox.defaults.closeText,
		okText: $.fn.datebox.defaults.okText
	});
}
if ($.fn.datetimespinner){
	$.fn.datetimespinner.defaults.selections = [[0,4],[5,7],[8,10],[11,13],[14,16],[17,19]]
}
