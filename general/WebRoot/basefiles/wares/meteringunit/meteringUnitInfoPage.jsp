<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@include file="/include.jsp" %>  
    <title>计量单位详情页面</title>
  </head>
  
 <body>
    <form action="" method="post" id="meteringUnit-form-showMeteringUnit">
    	<input id="meteringUnit-hidden-oldId" type="hidden" value="<c:out value="${meteringUnit.id }"></c:out>"/>
    	<table cellpadding="2" cellspacing="2" border="0">
    		<tr>
    			<td>编码:</td>
    			<td><input id="meteringUnit-input-id" name="meteringUnit.id" style="width: 200px;"
    				<c:choose>
						<c:when test="${showMap.id==null}">disabled="disabled" value=""</c:when>
						<c:otherwise>readonly="readonly" value="<c:out value="${meteringUnit.id }"></c:out>" </c:otherwise>
					</c:choose>/>
    				<input id="meteringUnit-hidden-state" type="hidden" name="meteringUnit.state"/>
    			</td>
    		</tr>
    		<tr>
    			<td>名称:</td>
    			<td><input name="meteringUnit.name" style="width: 200px;"
    				<c:choose>
						<c:when test="${showMap.name==null}">disabled="disabled" value=""</c:when>
						<c:otherwise>readonly="readonly" value="<c:out value="${meteringUnit.name }"></c:out>" </c:otherwise>
					</c:choose>/>
    			</td>
    		</tr>
    		<tr>
    			<td>备注:</td>
    			<td><textarea name="meteringUnit.remark" style="height:80px;width: 195px;overflow: hidden"
    				<c:choose>
						<c:when test="${showMap.remark==null}">disabled="disabled"</c:when>
						<c:otherwise>readonly="readonly"</c:otherwise>
					</c:choose>><c:choose><c:when test="${showMap.remark==null}"></c:when><c:otherwise><c:out value="${meteringUnit.remark }"></c:out></c:otherwise></c:choose></textarea></td>
    		</tr>
    		<tr>
    			<td>状态:</td>
    			<td><input id="common-combobox-state" value="${meteringUnit.state }" disabled="disabled" class="easyui-combobox" style="width: 200px" /></td>
    		</tr>
    	</table>
    </form>
    <script type="text/javascript">
    	$(function(){
    		//状态
			$('#common-combobox-state').combobox({
			    url:'common/sysCodeList.do?type=state',   
			    valueField:'id',   
			    textField:'name'  
			});
			
			$("#meteringUnit-button-layout").buttonWidget("setDataID",{id:$("#meteringUnit-hidden-oldId").val(),state:"${meteringUnit.state}",type:'view'});
    	});
    </script>
  </body>
</html>
