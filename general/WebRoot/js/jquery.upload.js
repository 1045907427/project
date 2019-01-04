(function(){
	$.fn.extend({
		upload: function(options, suboptions){
			if(options == "delete"){
				var $d = $("#z-upload-virtualDelete-input");
				if($d.length < 1){
					
				}
				else{
					var dels = $d.val();
					$.get('common/deleteAttachFile.do', {"id": dels}, function(json){});
				}
			}
			else if(typeof options === 'object' || !options){
				var noImgShow = $.extend({}, $.upload.noImgShow, {}, options.noImgShow);
				options = $.extend({}, $.upload.defaults, options, {
					noImgShow: noImgShow
				});
				return this.each(function(){
					new $.upload(this, options);
				});
			}
		}
	});
})(jQuery);
$.upload = function(btn, options){
	var $btn = $(btn);
	$btn.unbind("click");
	var z_upload_reload = true;
	$btn.bind("click",function(){
		if($btn.hasClass("easyui-linkbutton") && $btn.linkbutton('options').disabled){
			return false;
		}
		if($("#z-upload-dialog").length < 1){
			$("body").append("<div id='z-upload-dialog'></div>");
		}
		if($("#z-upload-virtualDelete-input").length < 1){
			$("body").append("<input type='hidden' id='z-upload-virtualDelete-input' />");
		}
		if(z_upload_reload){
			$("#z-upload-virtualDelete-input").val("");
			$("#z-upload-dialog").dialog({
				href: "common/uploadPage.do?type="+ options.type,
				width: options.width,
				height: options.height,
				title: options.title,
			    colsed: false,
				cache: false,
				modal: true,
				onLoad: function(){
					var $fileList = $("#z-upload-dialog").find(".fileList");
					$fileList.html("");
					if($fileList.length > 0){
						$fileList.append("<ul style='margin:0;padding:5px;list-style:none;'></ul>");
						var ids = options.ids.split(',');
						for(var i = 0; i<ids.length; i++){
							var id = ids[i];
							if(id != null && id != ""){
								$.get(
									"common/getAttachFile.do",
									{id: id},
									function(data){
										if(data != null && data != "null"){
											var json = $.parseJSON(data);
											var li = new Array();
											li.push("<li style='line-height:25px;clear:both;padding:0 5px;cursor: pointer;' onmouseover='$.upload.handleMouseOver(this);' onmouseout='$.upload.handleMouseOut(this);' ondblclick='$.upload.handleDBClick("+json.file.id+");'>");
											li.push("<a onclick='$.upload.handleDBClick("+json.file.id+");' href='javaScript:void(0); class='span1' style='width:300px;float:left;'>"+ json.file.oldfilename +"</a");
											li.push("<span class='span2' style='display:none'>");
											li.push("<a onclick='$.upload.handleDBClick("+json.file.id+");' href='javaScript:void(0);'>查看</a> <a href='common/download.do?id="+json.file.id+"'>下载</a> ");
											if(options.virtualDel){
												li.push("<a href='javascript:;' onclick='$.upload.virtualDelete(this, "+json.file.id+");'>删除</a> ");
											}
											li.push("</li>");
											$fileList.find("ul").append(li.join(""));
										}
									}
								);
							}
						}
					}
					if(options.type == 'upload'){
						$(".fileList").hide();
						$(".fileUpload").show();
					}
					else if(options.type == 'list'){
						$(".fileList").show();
						$(".fileUpload").hide();
					}
					else{
						$(".fileList").show();
						$(".fileUpload").show();
					}
				}
			});
			z_upload_reload = false;
		}
		else{
			$("#z-upload-dialog").dialog('open');
		}
		$.upload.onComplete = function(data){
			if(options.attaInput == ''){
				options.onComplete(data);
			}
			else{
				var $input = $(options.attaInput);
				if($input.val() == ''){
					$input.val(data.id);
				}
				else{
					$input.val($input.val() + ',' + data.id);
				}
			}
		};
		$.upload.onDelete = function(json){
			if(options.attaInput == ''){
				options.onDelete(json);
			}
			else{
				if(json.flag == true){
					var deleteId = json.file.id;
					var $input = $(options.attaInput);
					var ids = $input.val();
					var idArr = ids.split(',');
					var idTemp = new Array();
					for(var i = 0; i<idArr.length; i++){
						if(deleteId == idArr[i]){
							
						}
						else{
							idTemp.push(idArr[i]);
						}
					}
					$input.val(idTemp.join(","));
				}
				else{
					$.messager.alert("提醒","删除失败");
				}
			}
		};
		$.upload.options = function(){
			return options;
		};
		$.upload.virtualDelete = function(obj, id){
			$.messager.confirm("提醒", "确定删除该文件？该删除操作在保存后生效。", function(r){
				if(r){
					if(options.attaInput == ''){
						options.onVirtualDelete(id);
					}
					else{
						var $dinput = $("#z-upload-virtualDelete-input"); //保存删除的文件编号
						var $input = $(options.attaInput); //保存文件编号
						var ids = $input.val();
						var idArr = ids.split(',');
						var idTemp = new Array();
						for(var i = 0; i<idArr.length; i++){
							if(id == idArr[i]){
								
							}
							else{
								idTemp.push(idArr[i]);
							}
						}
						$input.val(idTemp.join(","));
						if($dinput.val() == ''){
							$dinput.val(id);
						}
						else{
							$dinput.val($dinput.val() + ',' + id);
						}
					}
					$(obj).parent().parent().fadeOut(1000);
				}
			});
		};
	});
};
$.upload.defaults = {
	width: 522,
	height: 435,
	title: "附件",
	auto: false,
	del: true,
	virtualDel: true,
	type: '',
	allowType: '',
	ids: '',
	noImgShow: {},
	attaInput: '',
	onComplete: function(data){},
	onDelete: function(data){},
	onVirtualDelete: function(id){}
};
$.upload.noImgShow = {
	"default":"/common/upload/default.png",
	".doc":"/common/upload/doc.png"	
};
$.upload.onComplete = function(data){};
$.upload.onDelete = function(file){};
$.upload.options = function(){};
$.upload.handleMouseOver = function(obj){
	var $obj = $(obj);
	$obj.css({"background": "#efefef"});
	$obj.children(".span2").show();
};
$.upload.handleMouseOut = function(obj){
	var $obj = $(obj);
	$obj.css({"background":""});
	$obj.children(".span2").hide();
};
$.upload.handleDBClick = function(id){
//	$.ajax({   
//        url :'common/viewFile.do?id='+id,
//        type:'post',
//        dataType:'json',
//        success:function(json){
//        	if(null!=json.file){
//        		window.open(json.file.htmlpath)
//        	}else{
//        		$.messager.alert("提醒", "该文件不能在线查看！");
//        	}
//		}
//    });
	var path = $("#basePath").attr("href");
    window.open(path+"common/viewFileFlash.do?id="+id);
};