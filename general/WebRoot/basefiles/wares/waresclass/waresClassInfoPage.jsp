<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>商品分类信息详情页面</title>
    <%@include file="/include.jsp" %>  

  </head>
  
  <body>
   	<form action="" method="post" id="waresClass-form-add">
    	<input type="hidden" id="waresClass-hidden-hdOldId" value="<c:out value="${oldId }"></c:out>"/>
    	<table cellpadding="2" cellspacing="2" border="0">
    		<tr>
    			<td>本级编码:</td>
    			<td><input id="waresClass-input-thisid" type="text" name="waresClass.thisid" value="<c:out value="${waresClass.thisid }"></c:out>" readonly="readonly" style="width:200px;"/>
    			</td>
    		</tr>
    		<tr>
    			<td>编码:</td>
    			<td><input type="text" id="waresClass-input-id" value="<c:out value="${waresClass.id }"></c:out>" readonly="readonly" name="waresClass.id" style="width:200px;"/></td>
    		</tr>
    		<tr>
    			<td>本级名称:</td>
    			<td><input type="text" name="waresClass.thisname" value="<c:out value="${waresClass.thisname }"></c:out>" readonly="readonly" style="width:200px;"/></td>
    		</tr>
    		<tr>
    			<td>名称:</td>
    			<td><input type="text" name="waresClass.name" value="<c:out value="${waresClass.name}"></c:out>" readonly="readonly" style="width:200px;"/></td>
    		</tr>
    		<tr>
    			<td>上级分类:</td>
    			<td>
    				<input id="waresClass-widget-pid" disabled="disabled" type="text" name="waresClass.pid" value="<c:out value="${waresClass.pid }"></c:out>" />
    			</td>
    		</tr>
    		<tr>
    			<td>备注:</td>
    			<td><textarea name="waresClass.remark" readonly="readonly" style="height:40px;width: 195px;overflow: hidden"><c:out value="${waresClass.remark}"></c:out></textarea></td>
    		</tr>
    		<tr>
    			<td>状态:</td>
    			<td><input id="common-combobox-state" value="${waresClass.state }" disabled="disabled" class="easyui-combobox" style="width: 200px" /></td>
    		</tr>
    	</table>
    </form>
    <script type="text/javascript">
    	//上级部门
		$("#waresClass-widget-pid").widget({
			name:'t_base_goods_waresclass',
			col:'pid',
			singleSelect:true,
			onlyLeafCheck:false,
			onLoadSuccess:function(){
				return true;
			}
		});
    	//状态
		$('#common-combobox-state').combobox({
		    url:'common/sysCodeList.do?type=state',   
		    valueField:'id',   
		    textField:'name'  
		});
		
		$(function(){
			$("#waresClass-button-layoutTree").buttonWidget("initButtonType","view");
			$("#waresClass-button-layoutTree").buttonWidget("setDataID",{id:$("#waresClass-hidden-hdOldId").val(),state:"${waresClass.state}",type:"view"});
			if("${waresClass.state}" != "1"){
				$("#waresClass-button-layoutTree").buttonWidget('disableButton','button-add');
			}
		});
    </script>
  </body>
</html>
