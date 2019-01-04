<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
    <div class="links">
   	<c:forEach var="list" items="${list}">
   		<security:authorize url="${list.url}">
    		<div class="link" id="link${list.id }">
    		<a href="javaScript:addTab('.${list.url}','${list.description }');"><img src="${list.image }" border="0" style="padding-top: 3px;"/>${list.aliasname }</a><br/>
    		<div class="close" linkid="${list.id}"></div>
    		</div>
   		</security:authorize>
   	</c:forEach>
    </div>
    <div id="protal-shortcut-dialog"></div>
    <script type="text/javascript">
    	JCommonUtils.namespace("protal");
    	protal.addShortcut = function(){
    		$('#protal-shortcut-dialog').dialog({  
			    title: '快捷操作新增',  
			    width: 450,  
			    height: 200,  
			    closed: true,  
			    cache: false,  
			    href: 'common/showShortcutAddPage.do',  
			    modal: true,
			    buttons:[
			    	{  
			    		id:'protal-shortcut-save',
                    	text:'保存',  
	                    iconCls:'button-save',
	                    plain:true
	                }
			    ]
			});
			$('#protal-shortcut-dialog').dialog("open");
    	}
    	$(function(){
    		$(".link").mouseover(function(){
    			$(".link").removeClass("linkover");
    			$(".close").removeClass("closeover");
    			$(".close").hide();
    			$(this).addClass("linkover");
    			$(this).find(".close").addClass("closeover");
    			$(this).find(".close").show();
    		});
    		$(".link").mouseleave(function(){
   				$(".link").removeClass("linkover");
   				$(".close").removeClass("closeover");
   				$(".close").hide();
    		});
    		$(".close").click(function(){
    			var id = $(this).attr("linkid");
    			$.ajax({   
		            url :'common/deleteShortcut.do?id='+id,
		            type:'post',
		            dataType:'json',
		            success:function(json){
		            	if(json.flag==true){
		            		$("#link"+id).remove();
		            		$.messager.alert("提醒","删除成功！");
		            	}else{
		            		$.messager.alert("提醒","删除失败！");
		            	}
		            }
		        });
    		});
    	});
    </script>
  </body>
</html>
