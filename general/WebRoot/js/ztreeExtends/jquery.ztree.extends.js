//jquery.ztree.extends
//author: zhengziyong
(function(){
	$.fn.extend({
		ChooseBox: function(url, options){
			options = $.extend({}, $.ChooseBox.defaults, {
				url: url
			}, options);
			return this.each(function(){
				new $.ChooseBox(this, options);
			});
		},
		result: function(handler){
			return this.bind("result", handler);
		}
	});
})(jQuery);

$.ChooseBox = function(input, options){
	var $input = $(input);
	var $parent = $input.parent();
	$input.bind(options.func, function(){
		var htmlFlag = $.ChooseBox.Html($input, options);
		$parent.css("position", "relative").css("float", "left");
		var input_left = $input.offset().left;
		var input_top = $input.offset().top;
		var input_height = $input.height();
		var parent_left = $parent.offset().left;
		var parent_top = $parent.offset().top;
		var z_box = $parent.find(".z-box");
		if(htmlFlag){
			z_box.css({"position":"absolute","top":(input_top - parent_top + input_height + 6),"left":(input_left - parent_left)}).html("加载中...").show();
			$.fn.zTree.init(z_box, $.ChooseBox.treeSetting($input, options), null);
		}
		else{
			z_box.show();
		}
		$(document).click(function(e){
			e = e || window.event;
			var target = e.target || e.srcElement;
			if($(target).closest($parent).length < 1){
				z_box.hide();
			}
		});
	});
};
$.ChooseBox.Html = function($input, options){
	var html = new Array();
	if($input.parent().find(".z-box").length > 0){
		return false;
	}
	var id = Math.ceil(Math.random()*1000);
	html.push("<div class='z-box ztree' id='"+id+"' style='width:"+options.width+"px;height:"+options.height+"px'>");
	html.push("</div>");
	$input.parent().append(html.join(""));
	return true;
};
$.ChooseBox.defaults = {
	multi: false,
	func: 'focus',
	width: 180,
	height: 230,
	onSelect:function(data){}
};
$.ChooseBox.treeSetting = function($input, options){
	var treeSetting = {
		view: {
			dblClickExpand: true,
			showLine: true,
			selectedMulti: false,
			showIcon:true,
			expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
		},
		async: {
			enable: true,
			url: options.url,
			autoParam: options.autoParam
		},
		data: {
			key:{
				title: options.title
			},
			simpleData: {
				enable:true,
				idKey: options.idKey,
				pIdKey: options.pIdKey,
				rootPId: options.rootPId
			}
		},
		callback: {
			//点击树状菜单更新页面按钮列表
			beforeClick: function(treeId, treeNode) {
				if (treeNode.isParent) {
				} else {
					//$input.trigger("onSelect", treeNode);
					options.onSelect(treeNode);
					$input.parent().find(".z-box").hide();
				}
			}
		}
	};
	return treeSetting;
};