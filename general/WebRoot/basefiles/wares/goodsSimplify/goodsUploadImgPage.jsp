<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>上传图片页面</title>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true,border:true" id="wares-img-layout-goodsAddPage">
  		<div data-options="region:'center',split:true,border:false">
  			<div id="wares-queue-imglistview" style="margin-bottom: 2px;"></div>
  			<div style="float:left;display:none;padding-left: 5px;" id="wares-img-upload-result"></div>
  		</div>
        <div data-options="region:'south',border:false">
            <div class="buttonDetailBG" style="text-align:right;">
                <div style="<c:if test="${type == 'view'}">display: none;</c:if>"><input type="button" value="上传图片" id="wares-img-upload-goodsAddPage" /></div>
            </div>
        </div>
  	</div>
	<script type="text/javascript">
		var initimgids = $("#goodsSimplify-hidden-attaids").val();
		var imgmap = {};
		$(function(){
			if(undefined != initimgids && initimgids != ""){
				showImageInfoList(initimgids);
			}

            $("#wares-img-upload-goodsAddPage").webuploader({
                title: '图片批量上传',
                filetype:'Images',
                allowType:"gif,jpg,jpeg,bmp,png",
                mimeTypes:'image/*',
                disableGlobalDnd:true,
                width: 700,
                height: 400,
                url:'common/uploadGoodsImage.do',
                formData:{goodsid:'${goodsid}'},
                description:'',
                close:true,
                onComplete: function(data){
                    var imgids = "";
                    for(var i=0;i<data.length;i++){
                        var obj = data[i];
                        if(imgids == ""){
                            imgids = obj.id;
                        }else{
                            imgids += "," + obj.id;
                        }
                    }
                    var attaids = $("#goodsSimplify-hidden-attaids").val();
                    if(attaids != ""){
                        imgids = attaids + "," + imgids;
                    }
                    $("#goodsSimplify-hidden-attaids").val(imgids);
                    showImageInfoList(imgids);
                }
            });
		});

		//预览
		function showImageView(imgid){
			var ret = goodsInfo_AjaxConn({id:imgid},'common/getAttachFile.do');
			var imgobj = $.parseJSON(ret);
			if(null != imgobj){
				var photograph = rootpath+imgobj.file.fullpath;
				$('#wares-window-showImgView').window({  
					    title: '预览 '+imgobj.file.filename+' 图片',  
					    width: $(window).width(),  
					    height: $(window).height(),  
					    closed: true,  
					    cache: false,  
					    modal: true 
					});
				$("#wares-window-showImgView").window("open");
				$("#wares-window-showImgView").window("refresh","basefiles/showGoodsInfoOldImgPage.do?photograph="+photograph);
			}
		}
		
		//删除
		function deleteImage(imgid){
            loading("删除中...");
			//删除文件数据库中的图片
            var goodsid = $("#goodsInfo-id-baseInfo").val();
			var ret = goodsInfo_AjaxConn({id:imgid,goodsid:goodsid},'common/deleteGoodsAttachFile.do');
			var json = $.parseJSON(ret);
            loaded();
			if(json.flag){
				showImageInfoList(json.newimgids);
			}else{
				$.messager.alert("提醒","删除失败!");
			}
		}
		
		//显示图片信息列
		function showImageInfoList(imgids){
            loading("加载中...")
			var mainimage = "${goodsid}"+".jpg";
			var ret = goodsInfo_AjaxConn({idarrs:imgids},'common/getAttachFileList.do');
			var imgarr = $.parseJSON(ret);
            if(imgarr.length != 0){
				var htmlsb=new Array();
				htmlsb.push('<table cellpadding="1" cellspacing="1" border="0">');
				//获取主图，若无，则选中系统默认图片
				var mainobj = null;
				for(var i=0;i<imgarr.length;i++){
					var obj = imgarr[i];
					if(mainimage == obj.filename){
						mainobj = obj;
						break;
					}
				}
				htmlsb.push('<tr>');
				htmlsb.push('<td valign="top" width="10px"></td>');
				if(null != mainobj && !isObjectEmpty(mainobj)){
					imgmap[mainobj.id] = mainobj.fullpath;
				   	var path=rootpath+mainobj.fullpath+''+"?r="+Math.random();
				   	if("${type}" != "view"){
				   		htmlsb.push('<td valign="top" align="rigtht">主图:</td>')
					   	htmlsb.push('<td valign="top"><img width="200px" height="120px" src="'+path+'" alt="'+mainobj.filename+'"/></td>');
				   	}else{
				   		htmlsb.push('<td valign="top" align="rigtht">主图:</td>')
					   	htmlsb.push('<td valign="top"><img width="400px" height="300px" src="'+path+'" alt="'+mainobj.filename+'"/></td>');
				   	}
				   	htmlsb.push('<td valign="top" width="180px"></td>');
				   	if("${type}" != "view"){
				   		htmlsb.push('<td valign="top"><a href=\"javascript:void(0);\" onclick=\"javascript:deleteImage(\''+mainobj.id+'\');\">删除</a></td>');
				   	}
				}else{
					if("${type}" != "view"){
						htmlsb.push('<td valign="top" align="rigtht">主图:</td>')
					   	htmlsb.push('<td valign="top"><img width="200px" height="120px" src="./image/photo_per_default.jpg" alt="主图"/></td>');
					}else{
						htmlsb.push('<td valign="top" align="rigtht">主图:</td>')
					   	htmlsb.push('<td valign="top"><img width="400px" height="300px" src="./image/photo_per_default.jpg" alt="主图"/></td>');
					}
					htmlsb.push('<td valign="top" width="180px"></td>');
				   	if("${type}" != "view"){
				   		htmlsb.push('<td valign="top"></td>');
				   	}
				}
	   			htmlsb.push('</tr>');
	   			htmlsb.push('<tr><td colspan="6"><hr></td></tr>');
	   			htmlsb.push(" ");
	   			//获取附图
				for(var i=0;i<imgarr.length;i++){
					var obj = imgarr[i];
					if(mainimage != obj.filename){
						imgmap[obj.id] = obj.fullpath;
						htmlsb.push('<tr>');
					   	htmlsb.push('<td valign="top" width="10px"></td>');
					   	var path=rootpath+obj.fullpath+''+"?r="+Math.random();
					   	if("${type}" != "view"){
					   		htmlsb.push('<td valign="top" align="rigtht"></td>')
					   		htmlsb.push('<td valign="top"><img width="200px" height="120px" src="'+path+'" alt="'+obj.filename+'"/></td>');
					   	}else{
					   		htmlsb.push('<td valign="top" align="rigtht"></td>')
					   		htmlsb.push('<td valign="top"><img width="400px" height="300px" src="'+path+'" alt="'+obj.filename+'"/></td>');
					   	}
					   	htmlsb.push('<td valign="top" width="180px"></td>');
					   	if("${type}" != "view"){
					   		htmlsb.push('<td valign="top"><a href=\"javascript:void(0);\" onclick=\"javascript:deleteImage(\''+obj.id+'\');\">删除</a></td>');
					   	}
		    			htmlsb.push('</tr>');
		    			htmlsb.push(" ");
					}
				}
				htmlsb.push('</table>');
			   	$("#wares-img-upload-result").html(htmlsb.join(""));
	    		$("#wares-img-upload-result").show();
			}else{
				$("#wares-img-upload-result").html("");
	    		$("#wares-img-upload-result").hide();
			}
            loaded();
		}
	</script>
  </body>
</html>
