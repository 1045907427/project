<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>短信查看</title>	
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<%@include file="/include.jsp" %> 

  </head>
  
  <body>
  	
  	<div style="height:100%">
  		<div style="padding:10px;">
	  		<div 
	  			<c:choose>
	  				<c:when test="${msgContent.msgtype==1 }">
	  					title="个人短信"
	  				</c:when>
	  				<c:when test="${msgContent.msgtype==2 }">
	  					title="公告通知"
	  				</c:when>
	  				<c:when test="${msgContent.msgtype==3 }">
	  					title="电子邮件"
	  				</c:when>
	  				<c:when test="${msgContent.msgtype==4 }">
	  					title="工作流"
	  				</c:when>
	  				<c:when test="${msgContent.msgtype==5 }">
	  					title="业务预警"
	  				</c:when>
	  				<c:when test="${msgContent.msgtype==6 }">
	  					title="单据提醒"
	  				</c:when>
	  				<c:otherwise>
	  					title="详情"
	  				</c:otherwise>
	  			</c:choose>
	  		 class="easyui-panel" style="border:1px #CCC solid">
<%--		  		<div style="height:30px;line-height:30px;text-align:center;font-weight:bold;border-bottom:1px #CCC solid">${msgContent.title }</div>--%>
		  		<div style="height:30px;line-height:30px;padding:2px;text-align:left;background: #F2F2F2;border-bottom:1px #CCC solid">			
		  			<span>
		  			<c:choose>
		  				<c:when test="${msgContent.adduserid=='system'}">系统</c:when>
		  				<c:otherwise>
		  					${msgContent.addusername }
		  				</c:otherwise>
		  			</c:choose></span>&nbsp;<fmt:formatDate  value="${msgContent.addtime}" pattern="yyyy-MM-dd HH:mm:ss" />
		  		</div>
		  		<div style="height:auto;word-break:break-all; word-wrap:break-word;padding:2px; min-height: 200px;">
		  			${msgContent.content }
		  		</div>
		  		<div style="height:30px;text-align:right;margin-right:20px;">
			  		<c:if test="${userviewtype=='recv'}">
			  			<c:if test="${msgContent.msgtype==1 or msgContent.msgtype==2 or msgContent.msgtype==3 }">
			  				<a href="javascript:void(0);" onclick="javascript:innerMessageView_messageReply('${msgContent.adduserid}')">回复</a>
			  			</c:if>		  		
			  		</c:if>
		  			<c:if test="${(msgContent.msgtype !=1) and ( not empty(msgContent.url))}">
		  				<a href="javascript:void(0);" target="_blank" onclick="javascript:innerMessageView_viewDetail('${msgContent.url }','${msgContent.tabtitle }');" >查看详情</a>
		  			</c:if>
		  		</div>
		  		<div style="clear:both"></div>
	  		</div>
  		</div>
  	</div>  				
  	<script type="text/javascript">
		var innerMessageView_viewDetail=function(url,title){

			if(url==null || $.trim(url)==""){
				return false;
			}
			if(title==null || $.trim(title)==""){
				title="短信详情查看";
			}
			try{
				top.addOrUpdateTab(url,title);
			}catch(e){
			}
		};
  	</script>
  	<c:if test="${userviewtype=='recv'}">
  	<script type="text/javascript">
		var innerMessageView_messageReply=function(uid){
			if(uid==null || $.trim(uid)==""){
				return false;
			}
			try{
				top.baseInnerMessageSend(uid,"reply");
			}catch(e){
			}
		};
		var innerMessageView_markMessageRead=function(ids){
			if(ids==null || $.trim(ids)==""){
				return false;
			}
			$.ajax({   
	            url :'message/innerMessage/messageReceiveReadByMsgid.do',
	            data:{msgids:ids},
	            type:'post',
	            dataType:'json',
	            success:function(data){
	            	if(data.flag==true){
		            	try{
			            	if($("#innerMessage-table-messageReceiveList").size()>0){
			            		$("#innerMessage-table-messageReceiveList").datagrid('reload');
			            	}
		            	}catch(e){
		            	}
	            	}
	            }
	        });
		};
		$(document).ready(function(){
			innerMessageView_markMessageRead('${msgContent.id}');
		});
		var Listurl="/sales/oweOrderListPage.do";
    	function DataGridRefresh(){
    		try{			
    			tabsWindowURL(Listurl).$("#sales-datagrid-oweOrderListPage").datagrid('reload');
    		}catch(e){
    			
    		}
    	}
		function ToOweOrderListPage(id) {
			$.ajax({   
	            url :'sales/checkOweOrderId.do',
	            type:'post',
	            data:{id:id},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	if(json.flag){
	            	    top.addTab('sales/oweOrderPage.do?type=view&id='+ id, "欠货单查看");
	            	}
	            	else{
	            		$.messager.alert("提醒","抱歉，该单据不存在！");
	            	}
	            }
	        });
			
		}
  	</script>
  	</c:if>
  </body>
</html>
