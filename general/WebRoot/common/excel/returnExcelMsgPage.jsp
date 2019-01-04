<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>返回导入信息页面</title>
  </head>
  
  <body>
    <div style="padding: 10px; width: 300px;" id="importExcel-div-returndata">
    	
    </div>
   <!-- 
   	<table cellpadding='0' cellspacing='0' border='0' style='width: 300px;'>
    		<tr>
    			<td style='width:300px;'>
    				<c:if test="${map.flag != null }">导入成功!</c:if>
    				<c:if test="${map.success != null}">导入成功${map.success }条;</c:if>
    			</td>
    		</tr>
    		<tr>
    			<td style='width:300px;'>
    				<c:if test="${map.error != null }">导入出错!</c:if>
    			</td>
    		</tr>
    		<tr>
    			<td style='width:300px;'>
    				<c:if test="${map.versionerror != null }">导入模版错误!</c:if>
    			</td>
    		</tr>
    		<tr>
    			<td style='TABLE-LAYOUT:fixed;word-break:break-all;'>
    				<c:if test="${map.failure != null }">
    					<c:choose>
    						<c:when test="${map.failStr != null && map.failStr != ''}">
    							编码${map.failStr }导入失败,导入失败${map.failure }条;
    						</c:when>
    						<c:otherwise>
    							导入失败${map.failure }条;
    						</c:otherwise>
    					</c:choose>
    				</c:if>
    			</td>
    		</tr>
    		<tr>
    			<td style='TABLE-LAYOUT:fixed;word-break:break-all;'>
    				<c:if test="${map.repeatNum != null }">
    					<c:choose>
    						<c:when test="${map.repeatVal != null && map.repeatVal != ''}">
    							编码${map.repeatVal }重复,${map.repeatNum }条不允许导入;
    						</c:when>
    						<c:otherwise>
    							重复${map.repeatNum }条不允许导入;
    						</c:otherwise>
    					</c:choose>
    				</c:if>
    			</td>
    		</tr>
    		<tr>
    			<td style='TABLE-LAYOUT:fixed;word-break:break-all;'>
    				<c:if test="${map.closeNum != null }">
    					<c:choose>
    						<c:when test="${map.closeVal != null && map.closeVal != ''}">
    							编码${map.closeVal }禁用,${map.closeNum }条不允许导入;
    						</c:when>
    						<c:otherwise>
    							禁用${map.closeNum }条不允许导入;
    						</c:otherwise>
    					</c:choose>
    				</c:if>
    			</td>
    		</tr>
    		<tr>
    			<td style='TABLE-LAYOUT:fixed;word-break:break-all;'>
    				<c:if test="${map.msg != null }">${map.msg}</c:if>
    			</td>
    		</tr>
    	</table>
    --> 
  </body>
</html>
