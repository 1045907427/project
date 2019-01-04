<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>通知通告查看范围</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

  </head>
  
  <body>
	<div style="height:100%">
		<div style="padding:20px 10px 10px;">
			<div>
				<div style="float:left;width:100px;line-height:66px;">部门发布：</div>
				<div style="float:left;height:80px;">
					<textarea style="height:70px;width:400px;" readonly="readonly">${recvdeptname }</textarea>
				</div>
				<div style="float:left;line-height:80px;">
					<c:if test="${not empty recvdeptname}">
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-view'" style="margin-top: 20px;" onclick="javascript:noticeSendList_showRangeList('${msgNotice.id}','1')">查看更多</a>
					</c:if>
				</div>
				<div style="clear:both"></div>
			</div>
			<div>
				<div style="float:left;width:100px;line-height:66px;">角色发布：</div>
				<div style="float:left;height:80px;">
					<textarea style="height:70px;width:400px;" readonly="readonly">${recvrolename }</textarea>
				</div>
				<div style="float:left;line-height:80px;">
					<c:if test="${not empty recvrolename}">
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-view'" style="margin-top: 20px;" onclick="javascript:noticeSendList_showRangeList('${msgNotice.id}','2')">查看更多</a>
					</c:if>
				</div>
				<div style="clear:both"></div>
			</div>
			<div>
				<div style="float:left;width:100px;line-height:66px;">人员发布：</div>
				<div style="float:left;height:80px;">
						<textarea style="height:70px;width:400px;" readonly="readonly">${recvusername }</textarea>
				</div>
				<div style="float:left;">
					<c:if test="${not empty recvusername}">
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-view'" style="margin-top: 20px;" onclick="javascript:noticeSendList_showRangeList('${msgNotice.id}','3')">查看更多</a>
					</c:if>
				</div>
				<div style="clear:both"></div>
			</div>
			<div style="clear:both"></div>
		</div>
	</div>
  </body>
</html>
