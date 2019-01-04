<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>公告通知查看</title>
	<%@include file="/include.jsp" %> 	
	<link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>

  </head>
  
  <body>
  	
  	<div style="height:100%">
  		<div style="padding:10px;">
	  		<div title="通知详情" class="easyui-panel" style="border:1px #CCC solid">
	  			<c:choose>
	  				<c:when test="${msgNoData == '0'}">
		  		<div style="height:30px;line-height:30px;text-align:center;font-weight:bold;border-bottom:1px #CCC solid">${msgNotice.title }</div>
		  		<div style="height:30px;line-height:30px;text-align:right;background: #F2F2F2;border-bottom:1px #CCC solid">
		  			<span>发布部门：</span><span>${msgNotice.publishdeptname } &nbsp;</span>
		  			<span>发布人：</span><span>${msgNotice.publishername } &nbsp;</span>
		  			<span>发布于：</span><c:if test="${not empty(item.publishtime)}"><fmt:formatDate  value="${msgNotice.publishtime}" pattern="yyyy-MM-dd HH:mm:ss" /></c:if> &nbsp;</span>
		  		</div>
		  		<div style="height:auto;word-break:break-all; word-wrap:break-word;padding:2px; min-height: 150px;">
		  		<c:choose>
		  			<c:when test="${msgNotice.form==3}">
		  				内容展示地址：${msgNotice.url }<br/>
		  				<a href="${msgNotice.url }" target="_blank" style="line-height:30px;">点击查看详细内容</a>
		  			</c:when>
		  			<c:otherwise>
		  				${msgNotice.content }		  				
		  			</c:otherwise>
		  		</c:choose>
		  		</div>
		  		<c:if test="${not empty(msgNotice.attach)}">
						  <div id="messageNotice-form-addNotice-attachment-show-div" style="height:auto;padding:2px; border-top:1px #CCC solid;">
							<p>附件文件：</p>
							<div>
								<div id="messageNotice-form-addNotice-attachment-uplist">
								</div>
			    			</div>
							<div style="clear:both"></div>
							<input type="hidden" value="${msgNotice.attach}" id="messageNotice-form-addNotice-attachment"/>
						  </div>
					  		<script type="text/javascript">
								function showAttachOnlyViewMenu(obj,fileid){
									if(fileid==null || $.trim(fileid)==""){
										return false;
									}
									fileid=$.trim(fileid);
									//var $menuwrap=$("#attachment_extend_menu_upfile_wrap");
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
						          		$menu.show();               
						          	}); 
						          	$(obj).bind("mouseout",function(event){
						          		event.stopPropagation(); 
						          		$(obj).unbind("mouseout"); 
						          		$menu.hide();               
						          	}); 
								}
								function renderAttachList(idarrs,isview){
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
													   	htmlsb.push("<div style=\"float:left;display:inline;margin-right:10px;\" ");
													   	htmlsb.push(" onmouseover=\"javascript:showAttachOnlyViewMenu(this,'"+item.id+"')\" ");
														htmlsb.push(" fileid=\"");
														htmlsb.push(item.id);
														htmlsb.push("\">");
													   	htmlsb.push("<a target=\"_blank\" href='common/download.do?id="+item.id+"' title='点击下载'>");
													   	htmlsb.push(item.oldfilename);
													   	htmlsb.push("</a>");
													   	htmlsb.push("<div");
													   	htmlsb.push(" id=\"attachment_extend_menu_upfile_"+item.id+"\" ");
													   	htmlsb.push(" style=\"z-index: 9999;display:none ;position: absolute;background: #FFF;border: 1px solid #373737;width:100px;\">");
													   	htmlsb.push("<a target=\"_blank\" href=\"common/download.do?id="+item.id+"\" title=\"点击下载\" style=\"display: block;line-height: 20px;padding: 2px 0 2px 10px;\" ");
												        htmlsb.push("onmouseover=\"javascript:this.style.backgroundColor='#E2E5E6'\" onmouseout=\"javascript:this.style.backgroundColor='#FFF'\">下载</a>");
												        htmlsb.push("<a onclick='$.upload.handleDBClick("+item.id+");' href='javaScript:void(0);' title='点击查看' ");
												        htmlsb.push(" style=\"display: block;line-height: 20px;padding: 2px 0 2px 10px;\" ");
												        htmlsb.push("onmouseover=\"javascript:this.style.backgroundColor='#E2E5E6'\" onmouseout=\"javascript:this.style.backgroundColor='#FFF'\" >查看</a>");
													   	htmlsb.push("</div>");
													   	htmlsb.push("</div>");
													});
												   	$("#messageNotice-form-addNotice-attachment-uplist").html(htmlsb.join(""));		
												}				   	
											}
										});		    	
									}
								}
								renderAttachList($("#messageNotice-form-addNotice-attachment").val());

					</script>	
		  		</c:if>
		  		<div style="height:30px;line-height:30px;padding:2px; border-top:1px #CCC solid;">
		  			本文关键词:${msgNotice.keyword }
		  		</div>
		  		<c:if test="${not empty msgNotice.modifyusername}">
			  		<div style="height:30px;line-height:30px;padding:2px; border-top:1px #CCC solid;text-align:left;background: #F2F2F2;">  			
			  			<span>${msgNotice.modifyusername }</span>&nbsp;
			  			<span>最后编辑于：<fmt:formatDate  value="${msgNotice.modifytime}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
			  		</div>
		  		</c:if>
		  		<div style="clear:both"></div>
		  		<script type="text/javascript">
				  	var msgNoticeDetail_readNotice=function(){
						var ids='${msgNotice.id}' || '';
						if(ids==null || $.trim(ids)==""){
							return false;
						}
						$.ajax({   
				            url :'message/notice/addNoticeread.do',
				            data:{ids:ids},
				            type:'post',
				            dataType:'json',
				            success:function(data){		            
					            try{
						            if($("#innerMessage-table-messageReceiveList").size()>0){
						            	$("#innerMessage-table-messageReceiveList").datagrid('reload');
						            }
					            }catch(e){
					            }
				            }
				        });
					  	
				  	}
					$(document).ready(function(){
						msgNoticeDetail_readNotice();
					});
				</script>
				</c:when>
				<c:when test="${msgNoData == '1'}">
					<div style="height:150px;line-height:150px;text-align:center;font-weight:bold;border-bottom:1px #CCC solid">抱歉，未能找到相关公告信息</div>
				</c:when>
				<c:when test="${msgNoData == '2'}">
					<div style="height:150px;line-height:150px;text-align:center;font-weight:bold;border-bottom:1px #CCC solid">抱歉，您无权限查看此公告信息</div>
				</c:when>
				<c:when test="${msgNoData == '3'}">
					<div style="height:150px;line-height:150px;text-align:center;font-weight:bold;border-bottom:1px #CCC solid">抱歉，该公告未被启用</div>
				</c:when>
				<c:when test="${msgNoData == '4'}">
					<div style="height:150px;line-height:150px;text-align:center;font-weight:bold;border-bottom:1px #CCC solid">抱歉，该公告已经被删除</div>
				</c:when>
				<c:when test="${msgNoData == '5'}">
					<div style="height:150px;line-height:150px;text-align:center;font-weight:bold;border-bottom:1px #CCC solid">抱歉，该公告已经关闭了</div>
				</c:when>
				<c:when test="${msgNoData == '99'}">
					<div style="height:150px;line-height:150px;text-align:center;font-weight:bold;border-bottom:1px #CCC solid">${msgNoDataMsg }</div>
				</c:when>
	  			<c:otherwise>
	  				<div style="height:150px;line-height:150px;text-align:center;font-weight:bold;border-bottom:1px #CCC solid">抱歉，未能找到相关公告信息</div>
	  			</c:otherwise>
	  			</c:choose>
	  		</div>
  		</div>
  	</div>  	
  </body>
</html>
