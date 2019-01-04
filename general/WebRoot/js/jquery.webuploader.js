(function(){
	$.fn.extend({
		webuploader:function(options, suboptions){
			options = $.extend({}, $.webuploader.defaults,options);
			return this.each(function(){
				new $.webuploader(this, options);
			});
		}
	});
})(jQuery);

function refreshWebuploderDialog(){
    $("#z-webuploader-dialog").dialog('refresh');
}

var _webuploder_option;
$.webuploader = function(btn, options){
	var $btn = $(btn);
	$btn.unbind("click");
	var z_webuploader_reload = true;
	$btn.bind("click",function(){
		if($btn.hasClass("easyui-linkbutton") && $btn.linkbutton('options').disabled){
			return false;
		}
		if($("#z-webuploader-dialog").length < 1){
			$("body").append("<div id='z-webuploader-dialog' style='overflow-x: hidden;'></div>");
		}
		if(z_webuploader_reload){
			
			_webuploder_option = options;
			
			var _option_description = "";
			if(undefined != options.description && '' != options.description){
				_option_description = options.description;
			}

			$("#z-webuploader-dialog").dialog({
				href: "common/showCommonWebuploaderPage.do",
			    //fit:true,
				queryParams:{description:_option_description},
				width: options.width,
				height: options.height,
				title: options.title,
			    close: false,
				cache: false,
				modal: true,
                onBeforeLoad:function(){
                    options.onBeforeLoad();
                },
                onLoad:function(){
                    options.onLoad();
					setTimeout(function(){
						initLoadWebuploader();
					},50)
                }
			});
			z_webuploader_reload = false;
		}
		else{
			$("#z-webuploader-dialog").dialog('open');
		}
		$.webuploader.getOptions = function(){
			return options;
		};
	});
}

$.webuploader.defaults = {
		width: 600,
		height: 400,
		//fit:true,
		title: "文件上传",
		filetype: '',
		allowType: '',
		mimeTypes:'',
		attaInput:'',
		url:'',
		disableGlobalDnd:true,
		close:false,
		description:'',
        converthtml:false,
        convertpdf:false,
		formData:{},
		fileObjName:'',
		onComplete: function(data){},
        onBeforeLoad: function(){},
        onLoad:function(){}
	};
$.webuploader.options={};
$.webuploader.onComplete = function(data){};
$.webuploader.onBeforeLoad = function(){};
$.webuploader.onLoad = function(){};
$.webuploader.getOptions = function(){};
$.webuploader.setFormData=function(value){
	if(typeof _webuploder_option === 'object' && _webuploder_option && typeof value==='object'){
		var formData=$.extend({}, _webuploder_option.formData,value);
		_webuploder_option.formData=formData;
	}
};
$.webuploader.openShowFileViewer = function(id){
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
$.webuploader.showAttachListDialog=function(idarrs,dialogTitle){

	if(idarrs==null || $.trim(idarrs)==""){
		return false;
	}
	if(dialogTitle==null || $.trim(dialogTitle)==""){
		dialogTitle="查看附件列表";
	}
	if($("#z-webuploader-dialog-attachViewList").length > 0){
		$("#z-webuploader-dialog-attachViewList").remove();
	}
	$("body").append("<div id='z-webuploader-dialog-attachViewList' style='overflow-x: hidden;'></div>");
	var $attachViewListDailog=$("#z-webuploader-dialog-attachViewList");
	$attachViewListDailog.append("<ul style='margin:0;padding:5px;list-style:none;'></ul>");
	idarrs=$.trim(idarrs);
	$.ajax({
		url:"common/getAttachFileList.do",
		data:{idarrs: idarrs},
		async:true,
		type:'POST',
		dataType:"text",
		success:function(data){
			var lisb=new Array();
			if(data != null && data != "null"){				
				var json = $.parseJSON(data);
				for(var i=0;i<json.length;i++){
					lisb.push("<li style='line-height:25px;clear:both;padding:0 5px;cursor: pointer;' onmouseover='style.backgroundColor=\"#efefef\"' onmouseout='style.backgroundColor=\"\"' ondblclick='$.webuploader.openShowFileViewer("+json[i].id+");'>");
					lisb.push("<a onclick='$.webuploader.openShowFileViewer("+json[i].id+");' href='javaScript:void(0); class='span1' style='width:300px;float:left;'>"+ json[i].oldfilename +"</a");
					lisb.push("<span class='span2' style='display:none'>");
					lisb.push("<a onclick='$.webuploader.openShowFileViewer("+json[i].id+");' href='javaScript:void(0);'>查看</a> <a href='common/download.do?id="+json[i].id+"'>下载</a> ");
					lisb.push("</li>");	
				};
				$attachViewListDailog.find("ul").append(lisb.join(""));
			}	
			if(lisb.length==0){
				$attachViewListDailog.append("<div style=\"padding:10px;\">抱歉，未能找到相关资源（相关资源可能被删除）。</div>")
			}
			$("#z-webuploader-dialog-attachViewList").dialog({
			    //fit:true,
				width: 522,
				height: 435,
				title: dialogTitle,
			    close: true,
				cache: false,
				modal: true,  
			    onClose:function(){
			    	$("#z-webuploader-dialog-attachViewList").window("destroy");
			    }
			});
			$("#z-webuploader-dialog-attachViewList").dialog('open');
		}
	});	
	
	
}