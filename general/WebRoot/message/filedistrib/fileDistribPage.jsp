<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<title>传阅件</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/kindeditor/kindeditor-min.js"></script> 
  	<script type="text/javascript" src="js/kindeditor/keconfig.js"></script> 
  	<script type="text/javascript" src="js/kindeditor/lang/zh_CN.js"></script>
    <script type="text/javascript" src="js/flexpager/flexpaper.js"></script>
    <script type="text/javascript" src="js/flexpager/flexpaper_handlers.js"></script>
   	
	<link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>
  </head>
  
  <body>
   <input type="hidden" id="fileDistrib-backid-fileDistribAddPage" value="${id }" />
  	<div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="fileDistrib-buttons-fileDistribPage" style="height:26px;"></div>
    	</div>
    	<div data-options="region:'center'">
    		<div class="easyui-panel" data-options="fit:true" id="fileDistrib-panel-fileDistribPage">
    		</div>
    	</div>
    </div>
	  <script type="text/javascript">
		function deleteAttach(fileid){
			if(fileid==null || $.trim(fileid)==""){
				return false;
			}
			fileid=$.trim(fileid);
			var $attach=$("#fileDistrib-form-fileDistribAddPage-attachment");
			var $attachdel=$("#fileDistrib-form-fileDistribAddPage-attachment-delete");
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
				$("#fileDistrib-form-fileDistribAddPage-attachment-uplist").find("div[fileid='"+fileid+"']").remove();
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
			if(null==item || null==item.id){
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
		   	htmlsb.push(" style=\"z-index: 9999;display:none ;position: absolute;background: #FFF;border: 1px solid #373737;width:120px;\">");
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
							$("#fileDistrib-form-fileDistribAddPage-attachment-show-div").show();
							var json = $.parseJSON(data);
							var htmlsb=new Array();
							$.each(json,function(i,item){
								htmlsb.push(showAttachMenuContent(item,isview));
							});
						   	$("#fileDistrib-form-fileDistribAddPage-attachment-uplist").html(htmlsb.join(""));		
						}				   	
					}
				});		    	
			}
		}
		
		function deleteDocAttach(fileid){
			if(fileid==null || $.trim(fileid)==""){
				return false;
			}
			fileid=$.trim(fileid);
			var $attach=$("#fileDistrib-form-fileDistribAddPage-cfile");
			var $attachdel=$("#fileDistrib-form-fileDistribAddPage-cfile-delete");
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
				$("#fileDistrib-form-fileDistribAddPage-cfile-uplist").find("div[fileid='"+fileid+"']").remove();
			}
		}
		function showDocAttachMenuContent(item,isview){
			if(null==item || null==item.id){
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
	          	htmlsb.push("<a href=\"javascript:void(0);\" onclick=\"javascript:deleteDocAttach("+item.id+")\" style=\"display: block;line-height: 20px;padding: 2px 0 2px 10px;\"");
	          	htmlsb.push("onmouseover=\"javascript:this.style.backgroundColor='#E2E5E6'\" onmouseout=\"javascript:this.style.backgroundColor='#FFF'\" >删除</a>");
	        }
		   	htmlsb.push("</div>");
		   	
		   	htmlsb.push("</div>");
		   	return htmlsb.join("");
		}
		
		function renderDocAttachList(idarrs,isview){
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
							$("#fileDistrib-form-fileDistribAddPage-cfile-show-div").show();
							var json = $.parseJSON(data);
							var htmlsb=new Array();
							$.each(json,function(i,item){
							   	htmlsb.push(showDocAttachMenuContent(item,isview));
							});
						   	$("#fileDistrib-form-fileDistribAddPage-cfile-uplist").html(htmlsb.join(""));		
						}				   	
					}
				});		    	
			}
		}
	  </script>
    <script type="text/javascript">
		var pageListUrl="/message/filedistrib/fileDistribPublishListPage.do";
		var fileDistrib_type = '${type}';
		fileDistrib_type=$.trim(fileDistrib_type.toLowerCase());
		if(fileDistrib_type==""){
			fileDistrib_type='add';
		}
		var fileDistrib_url = "message/filedistrib/fileDistribAddPage.do";
		if(fileDistrib_type == "view" || fileDistrib_type == "show" || fileDistrib_type == "handle"){
			fileDistrib_url = "message/filedistrib/fileDistribViewPage.do?id=${id}";
		}
		if(fileDistrib_type == "edit"){
			fileDistrib_url = "message/filedistrib/fileDistribEditPage.do?id=${id}";
		}
		if(fileDistrib_type == "copy"){
			fileDistrib_url = "message/filedistrib/fileDistribCopyPage.do?id=${id}";
		}

		function fileDistrib_tempSave_form_submit(){
			$("#fileDistrib-form-fileDistribAddPage").form({
			    onSubmit: function(){ 
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		    if(json.flag==true){
		  		    	fileDistrib_RefreshDataGrid();
		  		      	$.messager.alert("提醒","暂存成功");
		  		      	$("#fileDistrib-backid-fileDistribAddPage").val(json.backid);
		  		      	if(json.opertype && json.opertype == "add"){
		  		      		$("#fileDistrib-buttons-fileDistribPage").buttonWidget("addNewDataId", json.backid);
		  		      	}
		  		      	$("#fileDistrib-panel-fileDistribPage").panel('refresh', 'message/filedistrib/fileDistribViewPage.do?id='+ json.backid);
		  		    }
		  		    else{
		  		       	$.messager.alert("提醒","暂存失败");
		  		    }
		  		}
		  	});
		}
		function fileDistrib_realSave_form_submit(){
			$("#fileDistrib-form-fileDistribAddPage").form({
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
		  		    	fileDistrib_RefreshDataGrid();
		  		      	$.messager.alert("提醒","保存成功");
		  		      	$("#fileDistrib-backid-fileDistribAddPage").val(json.backid);
		  		      	if(json.opertype && json.opertype == "add"){
		  		      		$("#fileDistrib-buttons-fileDistribPage").buttonWidget("addNewDataId", json.backid);
		  		      	}
		  		      	$("#fileDistrib-panel-fileDistribPage").panel('refresh', 'message/filedistrib/fileDistribViewPage.do?id='+ json.backid);
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

		function  fileDistrib_RefreshDataGrid(){
			try{			
				tabsWindowURL(pageListUrl).$("#filedistrib-table-fileDistribListPage").datagrid('reload');
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
	    	$("#fileDistrib-panel-fileDistribPage").panel({
				href : fileDistrib_url,
			    cache:false,
			    maximized:true,
			    border:false
			});
			//按钮
			$("#fileDistrib-buttons-fileDistribPage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/message/filedistrib/fileDistribAddBtn.do">
					{
						type:'button-add',
						handler: function(){
							$("#fileDistrib-panel-fileDistribPage").panel('refresh', 'message/filedistrib/fileDistribAddPage.do');
						}
					},
					</security:authorize>
					<security:authorize url="/message/filedistrib/fileDistribEditPage.do">
					{
						type:'button-edit',
						handler: function(){
							var id = $("#fileDistrib-backid-fileDistribAddPage").val();
							if(id == ""){
								return false;
							}
							$("#fileDistrib-panel-fileDistribPage").panel('refresh', 'message/filedistrib/fileDistribEditPage.do?id='+id);
						}
					},
					</security:authorize>
					<security:authorize url="/message/filedistrib/fileDistribTempSave.do">
					{
						type:'button-hold',
						handler: function(){
							$.messager.confirm("提醒","确定暂存该传阅件信息？",function(r){
								if(r){
									$("#fileDistrib-fileDistribAddPage-addType").val("temp");
									fileDistrib_tempSave_form_submit();
									$("#fileDistrib-form-fileDistribAddPage").submit();
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/message/filedistrib/fileDistribRealSave.do">
					{
						type:'button-save',
						handler: function(){
							$.messager.confirm("提醒","确定保存该传阅件信息？",function(r){
								if(r){
									$("#fileDistrib-fileDistribAddPage-addType").val("real");
									var type=$("#fileDistrib-form-fileDistribAddPage-type").val()||"";
									if(type=="1"){
										var attach=$("#fileDistrib-form-fileDistribAddPage-cfile").val()||"";
										attach=$.trim(attach);
										if(attach==""){
											$.messager.alert("提醒","请上传文档！");
											return false;
										}
									}
									fileDistrib_realSave_form_submit();
									$("#fileDistrib-form-fileDistribAddPage").submit();
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/message/filedistrib/fileDistribGiveUpBtn.do">
		 			{
						type:'button-giveup',//放弃 
		 				handler:function(){
			 				var $polbuttons=$("#fileDistrib-buttons-fileDistribPage");
			 				var type = $polbuttons.buttonWidget("getOperType");
			 				if(type=="add"){
								var id = $("#fileDistrib-backid-fileDistribAddPage").val();
								if(id == ""){
									tabsWindowTitle(pageListUrl);
								}else{
									$("#fileDistrib-panel-fileDistribPage").panel('refresh', 'message/filedistrib/fileDistribViewPage.do?id='+id);
								}
			 				}else if(type=="edit"){
	
								var id = $("#fileDistrib-backid-fileDistribAddPage").val();
								if(id == ""){
									return false;
								}
			 					var flag = isLockData(id,"t_msg_filedistrib");
				 				if(!flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
				 					return false;
				 				}
								$("#fileDistrib-panel-fileDistribPage").panel('refresh', 'message/filedistrib/fileDistribViewPage.do?id='+id);
			 				}
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/message/filedistrib/deleteFileDistrib.do">
					{
						type:'button-delete',
						handler: function(){
							var id = $("#fileDistrib-backid-fileDistribAddPage").val();
							if(id == ""){
				            	$.messager.alert("提醒","抱歉，请未能找到传阅件信息");
				            	return false;
							}
		
							$.messager.confirm("提醒","是否删除传阅件信息？",function(r){
								if(r){
									$.ajax({   
							            url :'message/filedistrib/fileDistribPublishDelete.do?ids='+ id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
								            if(json.flag==true && json.isuccess && json.isuccess>0){
								            	var nextdata = $("#fileDistrib-buttons-fileDistribPage").buttonWidget("removeData",id);
							            		if(null!=nextdata && nextdata.id && nextdata.id!=""){
							            			$("#fileDistrib-backid-fileDistribAddPage").val(nextdata.id);
							            			$("#fileDistrib-panel-fileDistribPage").panel('refresh', 'message/filedistrib/fileDistribViewPage.do?id='+nextdata.id);
							            		}else{
													$("#fileDistrib-panel-fileDistribPage").panel('refresh', 'message/filedistrib/fileDistribAddPage.do');				            		
							            		}
							            		fileDistrib_RefreshDataGrid();		
								            }else{
								            	$.messager.alert("提醒","删除失败");
								            }											
							            }
							        });	
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/message/filedistrib/openFileDistrib.do">
					{
						type:'button-open',
						handler: function(){
							var id = $("#fileDistrib-backid-fileDistribAddPage").val();
							if(id == ""){
				            	$.messager.alert("提醒","抱歉，请未能找到传阅件信息");
				            	return false;
							}
	
							$.messager.confirm("提醒","是否启用该传阅件？",function(r){
								if(r){
									$.ajax({   
							            url :'message/filedistrib/openFileDistrib.do?ids='+ id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
								            if(json.flag==true && json.isuccess && json.isuccess>0){
							            		$.messager.alert("提醒", "启用成功");						            		
								            }else{
								            	$.messager.alert("提醒","启用失败");
								            }
								            fileDistrib_RefreshDataGrid();
											$("#fileDistrib-panel-fileDistribPage").panel('refresh', 'message/filedistrib/fileDistribViewPage.do?id='+id);
							            }
							        });	
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/message/filedistrib/closeFileDistrib.do">
					{
						type:'button-close',
						handler: function(){
							var id = $("#fileDistrib-backid-fileDistribAddPage").val();
							if(id == ""){
				            	$.messager.alert("提醒","抱歉，请未能找到传阅件信息");
				            	return false;
							}
							$.messager.confirm("提醒","是禁用该传阅件？",function(r){
								if(r){
									$.ajax({   
							            url :'message/filedistrib/closeFileDistrib.do?ids='+ id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
								            if(json.flag==true && json.isuccess && json.isuccess>0){
							            		$.messager.alert("提醒", "禁用成功");						            		
								            }else{
								            	$.messager.alert("提醒","禁用失败");
								            }
								            fileDistrib_RefreshDataGrid();
											$("#fileDistrib-panel-fileDistribPage").panel('refresh', 'message/filedistrib/fileDistribViewPage.do?id='+id);
							            }
							        });	
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/message/filedistrib/fileDistribPrevPage.do">
					{
						type:'button-back',
						handler: function(data){
						   	if(data!=null && data.id!=null && data.id!=""){
								$("#fileDistrib-backid-fileDistribAddPage").val(data.id);
								$("#fileDistrib-panel-fileDistribPage").panel('refresh','message/filedistrib/fileDistribViewPage.do?id='+ data.id);
						   	}
						}
					},
					</security:authorize>
					<security:authorize url="/message/filedistrib/fileDistribNextPage.do">
					{
						type:'button-next',
						handler: function(data){
						   	if(data!=null && data.id!=null && data.id!=""){
								$("#fileDistrib-backid-fileDistribAddPage").val(data.id);
								$("#fileDistrib-panel-fileDistribPage").panel('refresh','message/filedistrib/fileDistribViewPage.do?id='+ data.id);
						   	}
						}
					},
					</security:authorize>
					<security:authorize url="/message/filedistrib/fileDistribPrintView.do">
					{
						type:'button-preview',
						handler:function(){
						}
					},
					</security:authorize>
					<security:authorize url="/message/filedistrib/fileDistribPrint.do">
					{
						type:'button-print',
						handler:function(){
						}
					},
					</security:authorize>
					{}
				],
				model: 'base',
				type:'view',
				taburl:pageListUrl,
				datagrid:'filedistrib-table-fileDistribListPage',
				id:'${id}',
				tname:'t_msg_filedistrib'
			});
		});
	</script>
  </body>
</html>
