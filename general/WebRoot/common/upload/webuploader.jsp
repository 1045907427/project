<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>人员附件上传</title>
<%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:true">
	<div data-options="region:'north',border:false" style="height: 50px;" align="left">
		<div align="left" style="padding: 5px; <c:if test="${opeatype == 'view'}">display: none;</c:if>">
			<div id="wrapper">
			    <div id="container">
			        <!--头部，相册选择和格式选择-->

			        <div id="uploader_psn">
			            <div class="queueList_psn" style="margin:0px;border:0px;">
			                <table cellpadding="0" cellspacing="0" border="0">
		                		<tr>
		                			<td><div id="filePicker_psn"></div></td>
		                			<td style="width: 20px;"></td>
		                			<td><div class="info"></div></td>
		                		</tr>
		                	</table>
			            </div>
<!-- 			            <div class="statusBar" style="display:none;"> -->
<!-- 			                <div class="progress"> -->
<!-- 			                    <span class="text">0%</span> -->
<!-- 			                    <span class="percentage"></span> -->
<!-- 			                </div> -->
<!-- 			            </div> -->
			        </div>
			    </div>
			</div>
		</div>
	</div>
	<div data-options="region:'center',split:true,border:false">
		<div>
			<c:if test="${filenum != 0}">
				<table cellpadding="1" cellspacing="1" border="0" class="filelist">
					<c:forEach items="${filelist }" var="file">
						<c:choose>
	    					<c:when test="${isimgtype == 1}">
	    						<tr class="imgtr${file.id }">
			                		<td valign="top" align="rigtht" width="30px"></td>
			                		<td valign="top" width="400px"><img width="200px" height="120px" src="<%=basePath %>${file.fullpath }" alt="${file.oldfilename }"/></td>
			                		<td valign="top" width="50px"></td>
                                    <c:if test="${opeatype != 'view'}">
			                		<td valign="top"><a href="javascript:void(0);" onclick="javascript:deleteFile('${file.id }');">删除</a></td>
                                    </c:if>
		                			<td valign="top" align="rigtht" width="30px"></td>
		                		</tr>
	    					</c:when>
	    					<c:otherwise>
	    						<tr class="imgtr${file.id }">
					                <td valign="top" align="rigtht" width="30px"></td>
					                <td valign="top" width="400px"><a href="common/download.do?id=${file.id }">${file.oldfilename }</a></td>
					                <td valign="top" width="50px"></td>
					                <td valign="top"><a href="common/download.do?id=${file.id }">下载</a></td>
					                <td valign="top" width="2px"></td>
                                    <c:if test="${opeatype != 'view'}">
					                <td valign="top"><a href="javascript:void(0);" onclick="javascript:deleteFile('${file.id }');">删除</a></td>
                                    </c:if>
				               	 	<td valign="top" align="rigtht" width="30px"></td>
				                </tr>
	    					</c:otherwise>
	    				</c:choose>
					</c:forEach>
				</table>
			</c:if>
		</div>
		<div id="uploader-view-content">
			<div class="queueListcontent"></div>
		</div>
	</div>
</div>
<link rel="stylesheet" type="text/css" href="js/webuploader/css/webuploader.css">
<script type="text/javascript" src="js/webuploader/webuploader.js" charset="UTF-8"></script>
<script type="text/javascript">
var rootpath = '<%=basePath %>';
//根据模块的附件删除操作删除档案中的附件标记
function deleteRecordSign(uploadmode,fileid){
    loading("删除中...");
	if("person" == uploadmode){
		$.ajax({
	        url :'basefiles/deletePersonFiles.do',
	        type:'post',
	        dataType:'json',
	        data:{personid:'${personid}',fileid:fileid},
	        success:function(json){
                loaded();
	        	if(!json.flag){
	        		$.messager.alert("提醒","人员档案删除失败!");
				}
	        }
	    });
	}
}

function addRecordSign(uploadmode,fileid){
	if("person" == uploadmode){
        loading("上传中...");
		$.ajax({
	        url :'basefiles/addPersonFiles.do',
	        type:'post',
	        dataType:'json',
	        data:{personid:'${personid}',fileid:fileid},
	        success:function(json){
                loaded();
	        	if(!json.flag){
	        		$.messager.alert("提醒","人员档案上传失败!");
	        		deleteFile(fileid);
				}
	        }
	    });
	}
}

//删除文件数据库中的文件
function deleteFile(fileid){
    loading("删除中...");
	$.ajax({
        url :'common/deleteAttachFile.do',
        type:'post',
        dataType:'json',
        data:{id:fileid},
        success:function(retJSON){
            loaded();
        	if(retJSON.flag){
        		$(".imgtr"+fileid).remove();
        		deleteRecordSign("${uploadmode}",fileid);
			}
			else{
				$.messager.alert("提醒","删除失败!");
			}
        }
    });
}
// 当domReady的时候开始初始化
$(function() {
    var $wrap = $('#uploader_psn'),

        // 图片容器
        $queue = $( '<table cellpadding="1" cellspacing="1" border="0" class="filelist"></table>' )
            .appendTo( $("#uploader-view-content").find( '.queueListcontent' ) ),

        // 状态栏，包括进度和控制按钮
        $statusBar = $wrap.find( '.statusBar' ),

        // 文件总体选择信息。
        $info = $("#dndArea").find( '.info' ),

        // 上传按钮
        $upload = $wrap.find( '.uploadBtn' ),

        // 没选择文件之前的内容。
        $placeHolder = $wrap.find( '.placeholder' ),

        $progress = $statusBar.find( '.progress' ).hide(),

        // 添加的文件数量
        fileCount = 0,

        // 添加的文件总大小
        fileSize = 0,

        // 优化retina, 在retina下这个值是2
        ratio = window.devicePixelRatio || 1,

        // 缩略图大小
        thumbnailWidth = 200 * ratio,
        thumbnailHeight = 120 * ratio,

        // 可能有pedding, ready, uploading, confirm, done.
        state = 'pedding',

        // 所有文件的进度信息，key为file id
        percentages = {},
        // 判断浏览器是否支持图片的base64
        isSupportBase64 = ( function() {
            var data = new Image();
            var support = true;
            data.onload = data.onerror = function() {
                if( this.width != 1 || this.height != 1 ) {
                    support = false;
                }
            }
            data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
            return support;
        } )(),

        // 检测是否已经安装flash，检测flash的版本
        flashVersion = ( function() {
            var version;

            try {
                version = navigator.plugins[ 'Shockwave Flash' ];
                version = version.description;
            } catch ( ex ) {
                try {
                    version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash')
                            .GetVariable('$version');
                } catch ( ex2 ) {
                    version = '0.0';
                }
            }
            version = version.match( /\d+/g );
            return parseFloat( version[ 0 ] + '.' + version[ 1 ], 10 );
        } )(),

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

    if ( !WebUploader.Uploader.support('flash') && WebUploader.browser.ie ) {

        // flash 安装了但是版本过低。
        if (flashVersion) {
            (function(container) {
                window['expressinstallcallback'] = function( state ) {
                    switch(state) {
                        case 'Download.Cancelled':
                            $.messager.alert('提醒','您取消了更新！')
                            break;

                        case 'Download.Failed':
                            $.messager.alert('提醒','安装失败')
                            break;

                        default:
                            $.messager.alert('提醒','安装已成功，请刷新！');
                            break;
                    }
                    delete window['expressinstallcallback'];
                };

                var swf = rootpath+'/js/webuploader/expressInstall.swf';
                // insert flash object
                var html = '<object type="application/' +
                        'x-shockwave-flash" data="' +  swf + '" ';

                if (WebUploader.browser.ie) {
                    html += 'classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" ';
                }

                html += 'width="100%" height="100%" style="outline:0">'  +
                    '<param name="movie" value="' + swf + '" />' +
                    '<param name="wmode" value="transparent" />' +
                    '<param name="allowscriptaccess" value="always" />' +
                '</object>';

                container.html(html);

            })($wrap);

        // 压根就没有安转。
        } else {
            $wrap.html('<a href="http://www.adobe.com/go/getflashplayer" target="_blank" border="0"><img alt="get flash player" src="http://www.adobe.com/macromedia/style_guide/images/160x41_Get_Flash_Player.jpg" /></a>');
        }

        return;
    } else if (!WebUploader.Uploader.support()) {
        $.messager.alert('提醒', 'Web Uploader 不支持您的浏览器！');
        return;
    }

    // 实例化
    uploader = WebUploader.create({
    	// 不压缩image
        resize: false,
    	auto:true,
        pick: {
            id: '#filePicker_psn',
            label: '<c:choose><c:when test="${isimgtype == 1}">点击选择图片</c:when><c:otherwise>点击选择文件</c:otherwise></c:choose>'
        },
        formData: {},
        dnd: '#uploader_psn .queueList_psn',
        paste: document.body,
        swf: rootpath+'/js/webuploader/Uploadify.swf',
        chunked: false,
        chunkSize: 512 * 1024,
        server: 'common/upload.do',
        // runtimeOrder: 'flash',
		<c:choose>
        	<c:when test="${isimgtype == 1}">
        	accept: {
                title: 'Images',
               	extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            },
        	</c:when>
        </c:choose>

        // 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。
        disableGlobalDnd: true,
        fileNumLimit: 300,
        fileSizeLimit: 200 * 1024 * 1024,    // 200 M
        fileSingleSizeLimit: 50 * 1024 * 1024    // 50 M
    });

    // 拖拽时不接受 js, txt 文件。
    uploader.on( 'dndAccept', function( items ) {
        var denied = false,
            len = items.length,
            i = 0,
            // 修改js类型
            unAllowed = 'text/plain;application/javascript ';

        for ( ; i < len; i++ ) {
            // 如果在列表里面
            if ( ~unAllowed.indexOf( items[ i ].type ) ) {
                denied = true;
                break;
            }
        }

        return !denied;
    });

    // uploader.on('filesQueued', function() {
    //     uploader.sort(function( a, b ) {
    //         if ( a.name < b.name )
    //           return -1;
    //         if ( a.name > b.name )
    //           return 1;
    //         return 0;
    //     });
    // });

    // 添加“添加文件”的按钮，
    uploader.addButton({
        id: '#filePicker2',
        label: '继续添加'
    });

    uploader.on('ready', function() {
        window.uploader = uploader;
    });
    
    function showImageListView( file, response){
    	var path=rootpath+response.fullPath+''+"?r="+Math.random();
    	<c:choose>
	    	<c:when test="${isimgtype == 1}">
		    	var $li = $( '<tr class="imgtr'+response.id+'">' +
		                '<td valign="top" align="rigtht" width="30px"></td>' +
		                '<td valign="top" width="400px"><img width="200px" height="120px" src="'+path+'" alt="'+response.oldFileName+'"/></td>'+
		                '<td valign="top" width="50px"></td>'+
		                '<td valign="top"><a href=\"javascript:void(0);\" onclick=\"javascript:deleteFile(\''+response.id+'\');\">删除</a></td>'+
		                '<td valign="top" align="rigtht" width="30px"></td>' +
		                '</tr>' ),
	    	</c:when>
		    <c:otherwise>
			    var $li = $( '<tr class="imgtr'+response.id+'">' +
		                '<td valign="top" align="rigtht" width="30px"></td>' +
		                '<td valign="top" width="400px"><a href="common/download.do?id='+response.id+'">'+response.oldFileName+'</a></td>'+
		                '<td valign="top" width="50px"></td>'+
		                '<td valign="top"><a href="common/download.do?id='+response.id+'">下载</a></td>'+
		                '<td valign="top" width="2px"></td>'+
		                '<td valign="top"><a href=\"javascript:void(0);\" onclick=\"javascript:deleteFile(\''+response.id+'\');\">删除</a></td>'+
		                '<td valign="top" align="rigtht" width="30px"></td>' +
		                '</tr>' ),
		    </c:otherwise>
	    </c:choose>
                $prgress = $li.find('td.progress span'),
                $info = $('<td class="error"></td>');
    	file.on('statuschange', function( cur, prev ) {
            if ( prev === 'progress' ) {
                $prgress.hide().width(0);
            } else if ( prev === 'queued' ) {
                $li.off( 'mouseenter mouseleave' );
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
        $li.appendTo( $queue );
            
    }
    
    // 当有文件添加进来时执行，负责view的创建
    function addFile( file ) {
           var showError = function( file,code ) {
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

                $.messager.alert("提醒",file.name+text);
            };

        if ( file.getStatus() === 'invalid' ) {
            showError( file.statusText );
        } else {
            percentages[ file.id ] = [ file.size, 0 ];
            file.rotation = 0;
        }
    }

    // 负责view的销毁
    function removeFile( file ) {
        var $li = $('#'+file.id);

        delete percentages[ file.id ];
        updateTotalProgress();
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
            text = '选中' + fileCount + '张图片，共' +
                    WebUploader.formatSize( fileSize ) + '。';
        } else if ( state === 'confirm' ) {
            stats = uploader.getStats();
            if ( stats.uploadFailNum ) {
                text = '已成功上传' + stats.successNum+ '张照片，'+
                    stats.uploadFailNum + '张照片上传失败，<a class="retry" href="#">重新上传</a>失败图片或<a class="ignore" href="#">忽略</a>'
            }

        } else {
            stats = uploader.getStats();
            text = '共' + fileCount + '张（' +
                    WebUploader.formatSize( fileSize )  +
                    '），已上传' + stats.successNum + '张';

            if ( stats.uploadFailNum ) {
                text += '，失败' + stats.uploadFailNum + '张';
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
                $queue.hide();
                $statusBar.addClass( 'element-invisible' );
                uploader.refresh();
                break;

            case 'ready':
                $placeHolder.addClass( 'element-invisible' );
                $( '#filePicker2' ).removeClass( 'element-invisible');
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
                $( '#filePicker2' ).removeClass( 'element-invisible' );
                $upload.text( '开始上传' );

                stats = uploader.getStats();
                if ( stats.successNum && !stats.uploadFailNum ) {
                    setState( 'finish' );
                    return;
                }
                break;
            case 'finish':
                stats = uploader.getStats();
                if ( stats.successNum ) {
                    //$.messager.alert('提醒', '上传成功' );
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

 // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on( 'uploadSuccess', function(file,response ) {
    	showImageListView(file,response);
    	addRecordSign('${uploadmode}',response.id);
    });
    
    uploader.onError = function( code ) {
    	var text = '';
    	switch( code ) {
	        case 'Q_EXCEED_SIZE_LIMIT':
	        	text = '文件大小超出!';
	        	break;
	        case 'Q_TYPE_DENIED':
		        text = '文件类型不符合!';
	        	break;
	        case 'F_DUPLICATE':
	        	text = '文件重复!';
	        	break;
	        case 'Q_EXCEED_NUM_LIMIT':
	        	text = '最大允许上传300个文件!';
	        	break;
	        case 'F_EXCEED_SIZE':
	        	text = '文件大小超出!';
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
        $.messager.alert('提醒', 'todo' );
    } );

    $upload.addClass( 'state-' + state );
    updateTotalProgress();
});
</script>
</body>
</html>