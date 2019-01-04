<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>公告新增</title>    
		<%@include file="/include.jsp" %> 	
	  	<script type="text/javascript" src="js/kindeditor/kindeditor-min.js"></script>  
		<script type="text/javascript" src="js/kindeditor/keconfig.js"></script> 
	  	<script type="text/javascript" src="js/kindeditor/lang/zh_CN.js"></script> 
	  	<script type="text/javascript" src="js/linq.min.js"></script> 		
		<link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>
	  	<script type="text/javascript" src="js/colorPicker/jquery.colorPicker.min.js"></script>
	  	<link rel="stylesheet" href="js/colorPicker/colorPicker.css" type="text/css" />
	</head>
  
  <body>	  
	<input type="hidden" id="notice-backid-noticeAddPage" value="${id }" />
	<div class="easyui-layout" data-options="fit:true">
	 	<div data-options="region:'north',border:false">
	 		<div class="buttonBG" id="notice-buttons-noticePage" style="height:26px;"></div>
	 	</div>
	 	<div data-options="region:'center'">
	 		<div class="easyui-panel" data-options="fit:true" id="notice-panel-noticePage">
	 		</div>
	 	</div>
	</div>
	<div style="display: none">
		<div id="messageNotice-dialog-publishRangeList"></div>
	</div>
	  <script type="text/javascript">
		function deleteAttach(fileid){
			if(fileid==null || $.trim(fileid)==""){
				return false;
			}
			fileid=$.trim(fileid);
			var $attach=$("#messageNotice-form-addNotice-attachment");
			var $attachdel=$("#messageNotice-form-addNotice-attachment-delete");
			var files=$.trim($attach.val() || "");
			var fdels=$.trim($attachdel.val() || "");
			if(files==""){
				return false;
			}
			var filearr=files.split(',');
			var fdelarr=new Array();
			if(fdels!=""){
				fdelarr=fdels.split(',');
			}
			var index=0;
			var flag=false;
			for(index=0;index<filearr.length;index++){
				if(filearr[index]==fileid){
					flag=true;
					break;
				}
			}
			if(flag){
				filearr.splice(index,1);
				fdelarr.push(fileid);
				$attach.val(filearr.join(','));
				$attachdel.val(fdelarr.join(','));
				$("#attachment_extend_menu_upfile").hide();
				$("#attachment_extend_menu_upfile").html("");
				$("#messageNotice-form-addNotice-attachment-uplist").find("div[fileid='"+fileid+"']").remove();
			}
		}
		function showAttachMenu(obj,fileid){
			if(fileid==null || $.trim(fileid)==""){
				return false;
			}
			fileid=$.trim(fileid);
          	var $menu = $("#attachment_extend_menu_upfile_"+fileid);
      
          	$menu.mouseout(function(){
          		$menu.hide();
          	}); 
          	
          	$menu.bind("mouseover",function(event){
          		event.stopPropagation(); 
          		$menu.show();
          		//$menu.unbind("mouseover"); 
          	});
          	$(obj).bind("mouseover",function(event){
          		event.stopPropagation(); 
          		$(obj).unbind("mouseover"); 
          		var top=$(obj).outerHeight()-2;
              	$menu.animate({ opacity: "show",  top: top}, 300); 
          		$menu.show();               
          	}); 
          	$(obj).bind("mouseout",function(event){
          		event.stopPropagation(); 
          		$(obj).unbind("mouseout"); 
          		$menu.hide();               
          	}); 
		}
		function showAttachMenuContent(item,isview){
			if(null==item){
				return "";
			}
			if(isview==null || false!=isview){
				isview=true;
			}
			var htmlsb=new Array();
			
		 	htmlsb.push("<div style=\"float:left;display:inline;margin-right:10px;position:relative;\" ");
		   	htmlsb.push(" onmouseover=\"javascript:showAttachMenu(this,'"+item.id+"')\" ");
			htmlsb.push(" fileid=\"");
			htmlsb.push(item.id);
			htmlsb.push("\">");
		   	htmlsb.push("<a target=\"_blank\" href='common/download.do?id="+item.id+"' title='点击下载'>");
		   	if(null!=item.oldFileName){
		   		htmlsb.push(item.oldFileName);
		   	}else if(null!=item.oldfilename){
		   		htmlsb.push(item.oldfilename);		   		
		   	}
		   	htmlsb.push("</a>");
			
			htmlsb.push("<div");
		   	htmlsb.push(" id=\"attachment_extend_menu_upfile_"+item.id+"\" ");
		   	htmlsb.push(" style=\"z-index: 9999;display:none ;position: absolute;background: #FFF;border: 1px solid #373737;width:100px;\">");
			htmlsb.push("<a target=\"_blank\" href=\"common/download.do?id="+item.id+"\" title=\"点击下载\" style=\"display: block;line-height: 20px;padding: 2px 0 2px 10px;\" ");
	        htmlsb.push("onmouseover=\"javascript:this.style.backgroundColor='#E2E5E6'\" onmouseout=\"javascript:this.style.backgroundColor='#FFF'\">下载</a>");
	        htmlsb.push("<a onclick='$.webuploader.openShowFileViewer("+item.id+");' href='javaScript:void(0);' title='点击查看' ");
	        htmlsb.push(" style=\"display: block;line-height: 20px;padding: 2px 0 2px 10px;\" ");
	        htmlsb.push("onmouseover=\"javascript:this.style.backgroundColor='#E2E5E6'\" onmouseout=\"javascript:this.style.backgroundColor='#FFF'\" >查看</a>");
	        if(!isview){
	          	htmlsb.push("<a href=\"javascript:void(0);\" onclick=\"javascript:deleteAttach("+item.id+")\" style=\"display: block;line-height: 20px;padding: 2px 0 2px 10px;\"");
	          	htmlsb.push("onmouseover=\"javascript:this.style.backgroundColor='#E2E5E6'\" onmouseout=\"javascript:this.style.backgroundColor='#FFF'\" >删除</a>");
	        }
		   	htmlsb.push("</div>");
		   	
		   	htmlsb.push("</div>");

		   	return htmlsb.join("");
		}
		function renderAttachList(idarrs,isview){
			if(isview==null || false!=isview){
				isview=true;
			}
			if(idarrs!=null && $.trim(idarrs)!=""){
				idarrs=$.trim(idarrs);
				$.ajax({
					url:"common/getAttachFileList.do",
					data:{idarrs: idarrs},
					async:true,
					type:'POST',
					dataType:"text",
					success:function(data){
						if(data != null && data != "null"){
							$("#messageNotice-form-addNotice-attachment-show-div").show();
							var json = $.parseJSON(data);
							var htmlsb=new Array();
							$.each(json,function(i,item){
							   	htmlsb.push(showAttachMenuContent(item,isview));							  
							});
						   	$("#messageNotice-form-addNotice-attachment-uplist").html(htmlsb.join(""));		
						}				   	
					}
				});		    	
			}
		}
	  </script>
	  <script type="text/javascript">
				

		var notice_type = '${type}';
		notice_type=$.trim(notice_type.toLowerCase());
		if(notice_type==""){
			notice_type='add';
		}
		var notice_url = "message/notice/noticeAddPage.do";
		if(notice_type == "view" || notice_type == "show" || notice_type == "handle"){
			notice_url = "message/notice/noticeViewPage.do?id=${id}";
		}
		if(notice_type == "edit"){
			notice_url = "message/notice/noticeEditPage.do?id=${id}";
		}
		if(notice_type == "copy"){
			notice_url = "message/notice/noticeCopyPage.do?id=${id}";
		}

	  	var pageListUrl="/message/notice/noticePublishListPage.do";
		
		function notice_tempSave_form_submit(){
			$("#messageNotice-form-addNotice").form({
			    onSubmit: function(){ 
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		    if(json.flag==true){
		  		    	notice_RefreshDataGrid();
		  		      	$.messager.alert("提醒","暂存成功");
		  		      	$("#notice-backid-noticeAddPage").val(json.backid);
		  		      	if(json.type && json.type == "add"){
		  		      		$("#notice-buttons-noticePage").buttonWidget("addNewDataId", json.backid);
		  		      	}
		  		      	$("#notice-panel-noticePage").panel('refresh', 'message/notice/noticeViewPage.do?id='+ json.backid);
		  		    }
		  		    else{
		  		       	$.messager.alert("提醒","暂存失败");
		  		    }
		  		}
		  	});
		}
		function notice_realSave_form_submit(){
			$("#messageNotice-form-addNotice").form({
			    onSubmit: function(){
			    	var flag = $(this).form('validate');
		  		   	if(flag==false){
		  		   		return false;
		  		   	}
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		    if(json.flag==true){
		  		    	notice_RefreshDataGrid();
		  		      	$.messager.alert("提醒","保存成功");
		  		      	$("#notice-backid-noticeAddPage").val(json.backid);
		  		      	if(json.type && json.type == "add"){
		  		      		$("#notice-buttons-noticePage").buttonWidget("addNewDataId", json.backid);
		  		      	}
		  		      	notice_RefreshDataGrid();
		  		      	$("#notice-panel-noticePage").panel('refresh', 'message/notice/noticeViewPage.do?id='+ json.backid);
		  		      	
		  		    }
		  		    else{
			  		    if(json.msg){
		  		       		$.messager.alert("提醒","保存失败!"+json.msg);
			  		    }else{
		  		       		$.messager.alert("提醒","保存失败");
			  		    }
		  		    }
		  		}
		  	});
		}

		function  notice_RefreshDataGrid(){
			try{			
				tabsWindowURL(pageListUrl).$("#messageNotice-table-showNoticePublishList").datagrid('reload');
			}catch(e){
			}
		}

		function isLockData(id,tname){
			var flag = false;
			$.ajax({
	            url :'system/lock/unLockData.do',
	            type:'post',
	            data:{id:id,tname:tname},
	            dataType:'json',
	            async: false,
	            success:function(json){
	            	flag = json.flag
	            }
	        });
	        return flag;
		}

		
	  	$(document).ready(function(){
			$("#notice-panel-noticePage").panel({
				href:notice_url,
			    cache:false,
			    maximized:true,
			    border:false
			});

			//按钮
			$("#notice-buttons-noticePage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/message/notice/noticeAddBtn.do">
					{
						type:'button-add',
						handler: function(){
							$("#notice-panel-noticePage").panel('refresh', 'message/notice/noticeAddPage.do');
						}
					},
					</security:authorize>
					<security:authorize url="/message/notice/noticeEditPage.do">
					{
						type:'button-edit',
						handler: function(){
							var id = $("#notice-backid-noticeAddPage").val();
							if(id == ""){
								return false;
							}
							$("#notice-panel-noticePage").panel('refresh', 'message/notice/noticeEditPage.do?id='+id);
						}
					},
					</security:authorize>
					<security:authorize url="/message/notice/noticeTempSave.do">
					{
						type:'button-hold',
						handler: function(){
							$.messager.confirm("提醒","确定暂存该公告信息？",function(r){
								if(r){
									$("#notice-noticeAddPage-addType").val("temp");
									notice_tempSave_form_submit();
									$("#messageNotice-form-addNotice").submit();
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/message/notice/noticeRealSave.do">
					{
						type:'button-save',
						handler: function(){
							$.messager.confirm("提醒","确定保存该公告信息？",function(r){
								if(r){
									$("#notice-noticeAddPage-addType").val("real");
									notice_realSave_form_submit();
									$("#messageNotice-form-addNotice").submit();
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/message/notice/noticeGiveUpBtn.do">
		 			{
						type:'button-giveup',//放弃 
		 				handler:function(){
			 				var $polbuttons=$("#notice-buttons-noticePage");
			 				var type = $polbuttons.buttonWidget("getOperType");
			 				if(type=="add"){
								var id = $("#notice-backid-noticeAddPage").val();
								if(id == ""){
									tabsWindowTitle(pageListUrl);
								}else{
									$("#notice-panel-noticePage").panel('refresh', 'message/notice/noticeViewPage.do?id='+id);
								}
			 				}else if(type=="edit"){
	
								var id = $("#notice-backid-noticeAddPage").val();
								if(id == ""){
									return false;
								}
			 					var flag = isLockData(id,"t_msg_notice");
				 				if(!flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
				 					return false;
				 				}
								$("#notice-panel-noticePage").panel('refresh', 'message/notice/noticeViewPage.do?id='+id);
			 				}
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/message/notice/deleteNotice.do">
					{
						type:'button-delete',
						handler: function(){
							var id = $("#notice-backid-noticeAddPage").val();
							if(id == ""){
				            	$.messager.alert("提醒","抱歉，未能找到公告信息");
				            	return false;
							}
		
							$.messager.confirm("提醒","是否删除公告信息？",function(r){
								if(r){
									$.ajax({   
							            url :'message/notice/noticeDelete.do',
							            data:{ids:id},
							            type:'post',
							            dataType:'json',
							            success:function(json){
								            if(json.flag==true && json.isuccess && json.isuccess>0){
								            	var nextdata = $("#notice-buttons-noticePage").buttonWidget("removeData",id);
							            		if(null!=nextdata && nextdata.id && nextdata.id!=""){
							            			$("#notice-backid-noticeAddPage").val(nextdata.id);
							            			$("#notice-panel-noticePage").panel('refresh', 'message/notice/noticeViewPage.do?id='+nextdata.id);
							            		}else{
													$("#notice-panel-noticePage").panel('refresh', 'message/notice/noticeAddPage.do');				            		
							            		}
							            		notice_RefreshDataGrid();		
								            }else{
								            	$.messager.alert("提醒","删除失败，启用状态下不允许删除！");
								            }											
							            }
							        });	
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/message/notice/noticeEnable.do">
					{
						type:'button-open',
						handler: function(){
							var id = $("#notice-backid-noticeAddPage").val();
							if(id == ""){
				            	$.messager.alert("提醒","抱歉，请未能找到公告信息");
				            	return false;
							}

		   					var status=$("#messageNotice-form-noticePage-state").val()||"";
		   					if(status!=2 && status!=0){
					    		$.messager.alert("提醒","保存或禁用状态下的公告通知才能启用！");
					    		return false;		
		   					}
	
							$.messager.confirm("提醒","是否启用该公告？",function(r){
								if(r){
									$.ajax({   
							            url :'message/notice/noticeEnable.do',
							            data:{ids:id},
							            type:'post',
							            dataType:'json',
							            success:function(json){
								            if(json.flag==true){
												var isuccess = json.isuccess || 0;
												var ifailure = json.ifailure || 0;
												var inohandle = json.inohandle || 0;
												$.messager.alert("提醒", "启用成功数："+ isuccess +"<br />禁用失败数："+ ifailure + "<br />不允许禁用数："+ inohandle);
								            }else{
								            	$.messager.alert("提醒","启用失败");
								            }
								            notice_RefreshDataGrid();
											$("#notice-panel-noticePage").panel('refresh', 'message/notice/noticeViewPage.do?id='+id);
							            }
							        });	
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/message/notice/noticeDisable.do">
					{
						type:'button-close',
						handler: function(){
							var id = $("#notice-backid-noticeAddPage").val();
							if(id == ""){
				            	$.messager.alert("提醒","抱歉，请未能找到公告信息");
				            	return false;
							}
							$.messager.confirm("提醒","是禁用该公告？",function(r){
								if(r){
				   					var status=$("#messageNotice-form-noticePage-state").val()||"";
				   					if(status!=1){
							    		$.messager.alert("提醒","启用状态下的公告通知才能禁用！");
							    		return false;		
				   					}
									$.ajax({   
							            url :'message/notice/noticeDisable.do',
							            data:{ids:id},
							            type:'post',
							            dataType:'json',
							            success:function(json){
								            if(json.flag==true && json.isuccess && json.isuccess>0){
							            		$.messager.alert("提醒", "禁用成功");						            		
								            }else{
								            	$.messager.alert("提醒","禁用失败");
								            }
								            notice_RefreshDataGrid();
											$("#notice-panel-noticePage").panel('refresh', 'message/notice/noticeViewPage.do?id='+id);
							            }
							        });	
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/message/notice/noticePrevPage.do">
					{
						type:'button-back',
						handler: function(data){
						   	if(data!=null && data.id!=null && data.id!=""){
								$("#notice-backid-noticeAddPage").val(data.id);
								$("#notice-panel-noticePage").panel('refresh','message/notice/noticeViewPage.do?id='+ data.id);
						   	}
						}
					},
					</security:authorize>
					<security:authorize url="/message/notice/noticeNextPage.do">
					{
						type:'button-next',
						handler: function(data){
						   	if(data!=null && data.id!=null && data.id!=""){
								$("#notice-backid-noticeAddPage").val(data.id);
								$("#notice-panel-noticePage").panel('refresh','message/notice/noticeViewPage.do?id='+ data.id);
						   	}
						}
					},
					</security:authorize>
					<%--
					<security:authorize url="/message/notice/noticePrintView.do">
					{
						type:'button-preview',
						handler:function(){
						}
					},
					</security:authorize>
					<security:authorize url="/message/notice/noticePrint.do">
					{
						type:'button-print',
						handler:function(){
						}
					},
					</security:authorize>
					--%>
					{}
				],
				model: 'base',
				type:'view',
				taburl:pageListUrl,
				datagrid:'messageNotice-table-showNoticePublishList',
				id:'${id}',
				tname:'t_msg_notice'
			});			
	  	});

        function noticeSendList_showRangeList(noticeid,type){
            if(noticeid==null || $.trim(noticeid)==""){
                $.messager.alert('提醒 ','抱歉，无法查看发布范围');
                return;
            }
            type=type||"";
            var url='';
            var iwidth=450;
            var iheight=450;
            var title="发布范围";
            if(type=="1"){
                title=title+"（部门）";
                url='message/notice/showNoticePublishRangeDepartPage.do?noticeid='+noticeid;
            }else if(type=="2"){
                title=title+"（角色）";
                url='message/notice/showNoticePublishRangeRolePage.do?noticeid='+noticeid;
            }else if(type=="3"){
                title=title+"（人员）";
                url='message/notice/showNoticePublishRangeUserPage.do?noticeid='+noticeid;
            }else {
                $.messager.alert('提醒 ','抱歉，无法查看发布范围');
                return;
            }
            $('<div id="messageNotice-dialog-publishRangeList-content"></div>').appendTo('#messageNotice-dialog-publishRangeList');
            var $noticeRangeOper=$('#messageNotice-dialog-publishRangeList-content');
            $noticeRangeOper.dialog({
                title:title,
                width: iwidth,
                height: iheight,
                left:($(window).width() - iwidth) * 0.5,
                top:($(window).height() - iheight) * 0.5,
                closed: true,
                cache: false,
                href:url,
                modal: true,
                onClose:function(){
                    $noticeRangeOper.dialog("destroy");
                }
            });
            $noticeRangeOper.dialog("open");
        }
	  </script>
  </body>
</html>
