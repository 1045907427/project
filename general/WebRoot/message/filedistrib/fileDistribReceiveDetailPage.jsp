<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>传阅件查看</title>
	<%@include file="/include.jsp" %> 	
	<link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>
    <script type="text/javascript" src="js/jquery.upload.js"></script>
    <script type="text/javascript" src="js/flexpager/flexpaper.js"></script>
    <script type="text/javascript" src="js/flexpager/flexpaper_handlers.js"></script>

  </head>
  <body>
  	<div class="easyui-panel" data-options="fit:true" title="传阅件查看">
  		<c:if test="${noData=='0'}">
  		<table style="border-collapse:collapse; background-color: #F6FBFF; margin:5px; width:780px;"  border="1" cellpadding="5" cellspacing="5">
			<tr>
				<td style="width:80px;text-align: center;">
				标题
				</td>				
				<td style="width:700px;" colspan="5">
					${fileDistrib.title }
				</td>
			</tr>
			<tr>
				<td style="width:80px;text-align: center;">
				主题词
				</td>				
				<td style="width:700px;" colspan="5">
					${fileDistrib.titleword }
				</td>
			</tr>
			<tr>
				<td style="width:80px;text-align: center;">
				字号
				</td>				
				<td style="width:700px;" colspan="5">					
					${fileDistrib.wordsize }
				</td>
			</tr>			
			<tr>
				<td style="width:80px;text-align: center;">
				起草人
				</td>				
				<td style="width:120px">
					${fileDistrib.addusername }
				</td>
				<td style="width:80px;text-align: center;">
				起草部门
				</td>				
				<td style="">
					${fileDistrib.adddeptname }
				</td>
				<td style="width:80px;text-align: center;">
				起草日期
				</td>				
				<td style="width:120px;">	
					<fmt:formatDate value="${fileDistrib.addtime }" pattern="yyyy-MM-dd"/>					
				</td>
			</tr>
			<tr>
				<td colspan="6">
					<div style="overflow: auto; max-height:500px;max-width:780px;">						
						<c:choose>
							<c:when test="${fileDistrib.type=='1' &&  fileDistrib.cftype =='2' }">
								<iframe height="100%" width="100%" src="${fileDistrib.content }#"></iframe>
							</c:when>							
							<c:when test="${fileDistrib.type=='1' &&  fileDistrib.cftype =='1' }">
								<div id="fileDistrib-form-fileDistribAddPage-cfile-flash" style="height:500px;width:780px;"></div>
								<script type="text/javascript">
									$(document).ready(function(){
										$('#fileDistrib-form-fileDistribAddPage-cfile-flash').FlexPaperViewer(
							    	            { config : {
							    	                SWFFile : '${fileDistrib.content}',
							    	                Scale : 0.6,
							    	                ZoomTransition : 'easeOut',
							    	                ZoomTime : 0.5,
							    	                ZoomInterval : 0.2,
							    	                FitPageOnLoad : true,
							    	                FitWidthOnLoad : false,
							    	                FullScreenAsMaxWindow : false,
							    	                ProgressiveLoading : false,
							    	                MinZoomSize : 0.2,
							    	                MaxZoomSize : 5,
							    	                SearchMatchAll : false,
							    	                InitViewMode : 'Portrait',
							    	                RenderingOrder : 'flash',
							    	                StartAtPage : '',

							    	                ViewModeToolsVisible : true,
							    	                ZoomToolsVisible : true,
							    	                NavToolsVisible : true,
							    	                CursorToolsVisible : true,
							    	                SearchToolsVisible : true,
							    	                WMode : 'window',
							    	                localeChain: 'zh_CN'
							    	            }}
							    	    );
									});
								</script>
							</c:when>
							<c:otherwise>
								<div style="min-height:480px;min-width:760px;">
									${fileDistrib.content}
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</td>
			</tr>
		</table>
  		<div style="clear:both"></div>
	  	<script type="text/javascript">
		  	addFileDistribRead=function(){
				var ids='${fileDistrib.id}' || '';
				if(ids==null || $.trim(ids)==""){
					return false;
				}
				$.ajax({   
		            url :'message/filedistrib/addFileDistribread.do',
		            data:{ids:ids},
		            type:'post',
		            dataType:'json',
		            success:function(data){		            
			            try{
				            if(tabsWindow('传阅件接收').$("#filedistrib-table-showFileDistribList").size()>0){
				            	$("#filedistrib-table-showFileDistribList").datagrid('reload');
				            }
			            }catch(e){
			            }
		            }
		        });
			  	
		  	}
			$(document).ready(function(){
				addFileDistribRead();
			});
		</script>
  		</c:if>
		<c:if test="${noData == '1'}">
			<div style="height:150px;line-height:150px;text-align:center;font-weight:bold;border-bottom:1px #CCC solid">抱歉，未能找到相关传阅件信息</div>
		</c:if>
		<c:if test="${noData == '2'}">
			<div style="height:150px;line-height:150px;text-align:center;font-weight:bold;border-bottom:1px #CCC solid">抱歉，您无权限查看此传阅件信息</div>
		</c:if>
  	</div>
  </body>
</html>