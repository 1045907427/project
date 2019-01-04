function weuploderUploaderFilesAgain(){
    $(".uploadBtn").click();
}

function initLoadWebuploader(){
    var webuploaderdata = [];

    var $ = jQuery,    // just in case. Make sure it's not an other libaray.

        $wrap = $('#uploader'),

        // 图片容器
        $queue = $('<ul class="filelist"></ul>')
            .appendTo( $wrap.find('.queueList') ),

        // 状态栏，包括进度和控制按钮
        $statusBar = $wrap.find('.statusBar'),

        // 文件总体选择信息。
        $info = $statusBar.find('.info'),

        // 上传按钮
        $upload = $wrap.find('.uploadBtn'),

        // 没选择文件之前的内容。
        $placeHolder = $wrap.find('.placeholder'),

        // 总体进度条
        $progress = $statusBar.find('.progress').hide(),

        // 添加的文件数量
        fileCount = 0,

        // 添加的文件总大小
        fileSize = 0,

        // 优化retina, 在retina下这个值是2
        ratio = window.devicePixelRatio || 1,

        // 缩略图大小
        thumbnailWidth = 110 * ratio,
        thumbnailHeight = 110 * ratio,

        // 可能有pedding, ready, uploading, confirm, done.
        state = 'pedding',

        // 所有文件的进度信息，key为file id
        percentages = {},

        supportTransition = (function(){
            var s = document.createElement('p').style,
                r = 'transition' in s ||
                      'WebkitTransition' in s ||
                      'MozTransition' in s ||
                      'msTransition' in s ||
                      'OTransition' in s;
            s = null;
            return r;
        })(),

        // WebUploader实例
        uploader;

    if ( !WebUploader.Uploader.support() ) {
        alert( 'Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
        throw new Error( 'WebUploader does not support the browser you are using.' );
    }
    
    var labeltext = '点击选择文件',
    	text_msg = "个文件",
    	_option_filetype = "Files",
    	_option_extensions = "",
    	_option_mimeTypes = '',
    	fileNumLimit = 300,
    	fileSizeLimit = 200,
    	fileSingleSizeLimit = 50;
    	_option_url = 'common/upload.do',
    	disableGlobalDnd = true,
        _option_spstr = '';
        _option_converthtml = false,
        _option_convertpdf = false,
    	_option_formData = {};
        _option_fileVal='file'; //默认
    
    if('Images' == _webuploder_option.filetype){
    	labeltext = '点击选择图片';
    	text_msg = "张图片";
        _option_spstr = '<span class="rotateRight">向右旋转</span><span class="rotateLeft">向左旋转</span>';
    }

    if(undefined != _webuploder_option.filetype && '' != _webuploder_option.filetype){
    	_option_filetype = _webuploder_option.filetype;
    }
    if(undefined != _webuploder_option.allowType && '' != _webuploder_option.allowType){
    	_option_extensions = _webuploder_option.allowType;
    }
    if(undefined != _webuploder_option.mimeTypes && '' != _webuploder_option.mimeTypes){
    	_option_mimeTypes = _webuploder_option.mimeTypes;
    }
    if(undefined != _webuploder_option.fileNumLimit && '' != _webuploder_option.fileNumLimit){
    	fileNumLimit = _webuploder_option.fileNumLimit;
    }
    if(undefined != _webuploder_option.fileSizeLimit && '' != _webuploder_option.fileSizeLimit){
    	fileSizeLimit = _webuploder_option.fileSizeLimit;
    }
    if(undefined != _webuploder_option.fileSingleSizeLimit && '' != _webuploder_option.fileSingleSizeLimit){
    	fileSingleSizeLimit = _webuploder_option.fileSingleSizeLimit;
    }
    if(undefined != _webuploder_option.url && '' != _webuploder_option.url){
    	_option_url = _webuploder_option.url;
    }
    if(undefined != _webuploder_option.disableGlobalDnd && '' != _webuploder_option.disableGlobalDnd){
    	disableGlobalDnd = _webuploder_option.disableGlobalDnd;
    }
    if(undefined != _webuploder_option.converthtml && '' != _webuploder_option.converthtml){
        _option_converthtml = _webuploder_option.converthtml;
    }
    if(undefined != _webuploder_option.convertpdf && '' != _webuploder_option.convertpdf){
        _option_convertpdf = _webuploder_option.convertpdf;
    }
    if(undefined != _webuploder_option.formData && '' != _webuploder_option.formData){
    	_option_formData = _webuploder_option.formData;
    }
    if(undefined != _webuploder_option.fileObjName && '' != _webuploder_option.fileObjName){
    	_option_fileVal = _webuploder_option.fileObjName;
    }
    _option_formData.converthtml = _option_converthtml;
    _option_formData.convertpdf = _option_convertpdf;
    // 实例化
    uploader = WebUploader.create({
        pick: {
            id: '#filePicker',
            label: labeltext
        },
        dnd: '#uploader .queueList',
        paste: document.body,
        accept: {
            title: _option_filetype,
            extensions: _option_extensions,
            mimeTypes: _option_mimeTypes
        },
        threads:1,
        prepareNextFile:true,
        swf: '/js/webuploader/Uploader.swf',
        duplicate:true,
        disableGlobalDnd: disableGlobalDnd,

        chunked: false,
        server: _option_url,
        formData:_option_formData,
        fileVal:_option_fileVal,
        fileNumLimit: fileNumLimit,
        fileSizeLimit: fileSizeLimit * 1024 * 1024,    // 200 M
        fileSingleSizeLimit: fileSingleSizeLimit * 1024 * 1024    // 50 M
    });
    
    // 添加“添加文件”的按钮，
    uploader.addButton({
        id: '#filePicker2',
        label: '继续添加'
    });

    // 当有文件添加进来时执行，负责view的创建
    function addFile( file ) {
        var $li = $( '<li title="'+file.name+'" id="' + file.id + '">' +
//                '<p class="title">' + file.name + '</p>' +
                '<p class="imgWrap"></p>'+
                '<p class="progress"><span></span></p>' +
                '</li>' ),

            $btns = $('<div title="'+file.name+'" class="file-panel">' +
                '<span class="cancel">删除</span>' + _option_spstr +
                '</div>').appendTo( $li ),
            $prgress = $li.find('p.progress span'),
            $wrap = $li.find( 'p.imgWrap' ),
            $info = $('<p class="error"></p>'),

            showError = function( code ) {
                switch( code ) {
                    case 'exceed_size':
                        text = '文件大小超出';
                        break;

                    case 'interrupt':
                        text = '上传暂停';
                        break;

                    default:
                        text = '上传失败，请重试';
                        break;
                }

                $info.text( text ).appendTo( $li );
            };
        if ( file.getStatus() === 'invalid' ) {
            showError( file.statusText );
        } else {
            // @todo lazyload
        	$wrap.text( '预览中' );
            var file_ext = (file.ext).toLowerCase();
            if("gif,jpg,jpeg,bmp,png".indexOf(file_ext) != -1){
            	uploader.makeThumb( file, function( error, src ) {
                    if ( error ) {
                        $wrap.text( '不能预览' );
                        return;
                    }

                    var img = $('<img src="'+src+'">');
                    $wrap.empty().append( img );
                }, thumbnailWidth, thumbnailHeight );
            }else{
                var img = "";
                if("doc" == file_ext || "docx" == file_ext){
                    img = $('<img width="'+thumbnailWidth+'px" height="'+thumbnailHeight+'px" src="./common/upload/doc.png">');
                }else if("xls"== file_ext || "xlsx"== file_ext){
                    img = $('<img width="'+thumbnailWidth+'px" height="'+thumbnailHeight+'px" src="./common/upload/xls.png">');
                }else if("ppt" == file_ext || "pptx" == file_ext){
                    img = $('<img width="'+thumbnailWidth+'px" height="'+thumbnailHeight+'px" src="./common/upload/ppt.png">');
                }else if("txt" == file_ext){
                    img = $('<img width="'+thumbnailWidth+'px" height="'+thumbnailHeight+'px" src="./common/upload/txt.png">');
                }else if("htm" == file_ext || "html" == file_ext){
                    img = $('<img width="'+thumbnailWidth+'px" height="'+thumbnailHeight+'px" src="./common/upload/html.png">');
                }else{
                	img = $('<img width="'+thumbnailWidth+'px" height="'+thumbnailHeight+'px" src="./common/upload/defaultfile.png">');                	
                }
                $wrap.empty().append( img );
            }

            percentages[ file.id ] = [ file.size, 0 ];
            file.rotation = 0;
        }

        file.on('statuschange', function( cur, prev ) {
            if ( prev === 'progress' ) {
                $prgress.hide().width(0);
            } else if ( prev === 'queued' ) {
                $li.off( 'mouseenter mouseleave' );
                $btns.remove();
            }

            // 成功
            if ( cur === 'error' || cur === 'invalid' ) {
                showError( file.statusText );
                percentages[ file.id ][ 1 ] = 1;
            } else if ( cur === 'interrupt' ) {
                showError( 'interrupt' );
            } else if ( cur === 'queued' ) {
                percentages[ file.id ][ 1 ] = 0;
            } else if ( cur === 'progress' ) {
                $info.remove();
                $prgress.css('display', 'block');
            } else if ( cur === 'complete' ) {
                $li.append( '<span class="success"></span>' );
            }

            $li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
        });

        $li.on( 'mouseenter', function() {
            $btns.stop().animate({height: 30});
            
        });

        $li.on( 'mouseleave', function() {
            $btns.stop().animate({height: 0});
        });

        $btns.on( 'click', 'span', function() {
            var index = $(this).index(),
                deg;

            switch ( index ) {
                case 0:
                    uploader.removeFile( file );
                    return;

                case 1:
                    file.rotation += 90;
                    break;

                case 2:
                    file.rotation -= 90;
                    break;
            }

            if ( supportTransition ) {
                deg = 'rotate(' + file.rotation + 'deg)';
                $wrap.css({
                    '-webkit-transform': deg,
                    '-mos-transform': deg,
                    '-o-transform': deg,
                    'transform': deg
                });
            } else {
                $wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
                // use jquery animate to rotation
                // $({
                //     rotation: rotation
                // }).animate({
                //     rotation: file.rotation
                // }, {
                //     easing: 'linear',
                //     step: function( now ) {
                //         now = now * Math.PI / 180;

                //         var cos = Math.cos( now ),
                //             sin = Math.sin( now );

                //         $wrap.css( 'filter', "progid:DXImageTransform.Microsoft.Matrix(M11=" + cos + ",M12=" + (-sin) + ",M21=" + sin + ",M22=" + cos + ",SizingMethod='auto expand')");
                //     }
                // });
            }


        });

        $li.appendTo( $queue );
    }

    // 负责view的销毁
    function removeFile( file ) {
        var $li = $('#'+file.id);

        delete percentages[ file.id ];
        updateTotalProgress();
        $li.off().find('.file-panel').off().end().remove();
    }

    function updateTotalProgress() {
        var loaded = 0,
            total = 0,
            spans = $progress.children(),
            percent;

        $.each( percentages, function( k, v ) {
            total += v[ 0 ];
            loaded += v[ 0 ] * v[ 1 ];
        } );

        percent = total ? loaded / total : 0;

        spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
        spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );
        updateStatus();
    }

    function updateStatus() {
        var text = '', stats;
        
        if ( state === 'ready' ) {
            text = '选中' + fileCount + text_msg +'，共' +
                    WebUploader.formatSize( fileSize ) + '。';
        } else if ( state === 'confirm' ) {
            stats = uploader.getStats();
            if ( stats.uploadFailNum ) {
                text = '已成功上传' + stats.successNum+ text_msg +'，'+
                    stats.uploadFailNum + text_msg +'上传失败，<a class="retry" href=javascript:void(0); onclick=javascript:weuploderUploaderFilesAgain();>重新上传</a>或<a class="ignore" href=javascript:void(0); onclick=javascript:refreshWebuploderDialog();>忽略</a>'
            }

        } else {
            stats = uploader.getStats();
            text = '共' + fileCount + text_msg +'（' +
                    WebUploader.formatSize( fileSize )  +
                    '），已上传' + stats.successNum + text_msg;

            if ( stats.uploadFailNum ) {
                text += '，失败' + stats.uploadFailNum + text_msg;
            }
        }

        $info.html( text );
    }

    function setState( val ) {
        var file, stats;
        if ( val === state ) {
            return;
        }
        $upload.removeClass( 'state-' + state );
        $upload.addClass( 'state-' + val );
        state = val;

        switch ( state ) {
            case 'pedding':
                $placeHolder.removeClass( 'element-invisible' );
                $queue.parent().removeClass('filled');
                $queue.hide();
                $statusBar.addClass( 'element-invisible' );
                uploader.refresh();
                break;

            case 'ready':
                $placeHolder.addClass( 'element-invisible' );
                $( '#filePicker2' ).removeClass( 'element-invisible');
                $queue.parent().addClass('filled');
                $queue.show();
                $statusBar.removeClass('element-invisible');
                uploader.refresh();
                break;

            case 'uploading':
                $( '#filePicker2' ).addClass( 'element-invisible' );
                $progress.show();
                $upload.text( '暂停上传' );
                break;

            case 'paused':
                $progress.show();
                $upload.text( '继续上传' );
                break;

            case 'confirm':
                $progress.hide();
                $placeHolder.addClass( 'element-invisible' );
                $( '#filePicker2' ).removeClass( 'element-invisible');
                $queue.parent().addClass('filled');
                $queue.show();
                $upload.text( '开始上传' );
                //$upload.text( '开始上传' ).addClass( 'disabled' );

                stats = uploader.getStats();
                if ( stats.successNum && !stats.uploadFailNum ) {
                    setState( 'finish' );
                    return;
                }
                break;
            case 'finish':
                stats = uploader.getStats();
                if ( stats.successNum ) {
                	_webuploder_option.onComplete(webuploaderdata);
                	if(_webuploder_option.attaInput != ''){
        				var $input = $(_webuploder_option.attaInput);
        				for(var i=0;i<webuploaderdata.length;i++){
        					if($input.val() == ''){
            					$input.val(webuploaderdata[i].id);
            				}
            				else{
            					$input.val($input.val() + ',' + webuploaderdata[i].id);
            				}
        				}
        			}
                	if(_webuploder_option.close){
                		$("#z-webuploader-dialog").dialog('close');
                	}else{
                        webuploaderdata = [];
                    }
//                	$.messager.alert("提醒","上传完成!");
                } else {
                    // 没有成功的图片，重设
                    state = 'done';
                    location.reload();
                }
                break;
        }

        updateStatus();
    }

    uploader.onUploadProgress = function( file, percentage ) {
        var $li = $('#'+file.id),
            $percent = $li.find('.progress span');

        $percent.css( 'width', percentage * 100 + '%' );
        percentages[ file.id ][ 1 ] = percentage;
        updateTotalProgress();
    };

    uploader.onFileQueued = function( file ) {
        fileCount++;
        fileSize += file.size;

        if ( fileCount === 1 ) {
            $placeHolder.addClass( 'element-invisible' );
            $statusBar.show();
        }

        addFile( file );
        setState( 'ready' );
        updateTotalProgress();
    };

    uploader.onFileDequeued = function( file ) {
        fileCount--;
        fileSize -= file.size;

        if ( !fileCount ) {
            setState( 'pedding' );
        }

        removeFile( file );
        updateTotalProgress();

    };

    uploader.on( 'uploadSuccess', function( file,response ) {
    	webuploaderdata.push(response);
    });

    uploader.on( 'all', function( type ) {
        var stats;
        switch( type ) {
            case 'uploadFinished':
                setState( 'confirm' );
                break;

            case 'startUpload':
                setState( 'uploading' );
                break;

            case 'stopUpload':
                setState( 'paused' );
                break;

        }
    });

    uploader.onError = function( code ) {
    	var text = '';
    	switch( code ) {
	        case 'Q_EXCEED_SIZE_LIMIT':
	        	text = '文件大小超出，最大'+fileSizeLimit+'M!';
	        	break;
	        case 'Q_TYPE_DENIED':
		        text = '文件类型不符合!';
	        	break;
	        case 'F_DUPLICATE':
	        	text = '文件重复!';
	        	break;
	        case 'Q_EXCEED_NUM_LIMIT':
	        	text = '只允许上传'+fileNumLimit+text_msg+'!';
	        	break;
	        case 'F_EXCEED_SIZE':
	        	text = '文件大小超出，最大'+fileSingleSizeLimit+'M!';
	        	break;
	        default:
	            text = '上传失败，请重试!';
	            break;
	    }
        $.messager.alert('提醒',text );
    };

    $upload.on('click', function() {
        if ( $(this).hasClass( 'disabled' ) ) {
            return false;
        }

        if ( state === 'ready' ) {
            uploader.upload();
        } else if ( state === 'paused' ) {
            uploader.upload();
        } else if ( state === 'uploading' ) {
            uploader.stop();
        }
    });

    $info.on( 'click', '.retry', function() {
        uploader.retry();
    } );

    $info.on( 'click', '.ignore', function() {
        alert( 'todo' );
    } );

    $upload.addClass( 'state-' + state );
    updateTotalProgress();
}

$(function(){
    //setTimeout('initLoadWebuploader()',100)
});