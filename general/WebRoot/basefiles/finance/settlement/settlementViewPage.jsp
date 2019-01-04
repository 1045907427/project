<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>结算方式详情页面</title>
  </head>
  
  <body>
    <form action="" method="post">
    	<input type="hidden" id="settlement-oldId" value="<c:out value="${settlement.id }"></c:out>" />
    	 <div style="padding: 10px;">
	    	<p>
	    		<label>编码:</label>
	    		<input id="finance-id-settlement" type="text" name="settlement.id" readonly="readonly" value="<c:out value="${settlement.id}"></c:out>" style="width:200px;"/>
	    	</p>
	    	<p>
	    		<label>名称:</label>
	    		<input type="text" name="settlement.name" value="<c:out value="${settlement.name}"></c:out>" style="width:200px;" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>类型:</label>
	    		<select name="settlement.type" style="width:200px;" disabled="disabled">
					<option value="1" <c:if test="${settlement.type=='1'}">selected="selected"</c:if> >月结</option>
					<option value="2" <c:if test="${settlement.type=='2'}">selected="selected"</c:if> >日结</option>
					<option value="3" <c:if test="${settlement.type=='3'}">selected="selected"</c:if> >现结</option>
					<option value="4" <c:if test="${settlement.type=='4'}">selected="selected"</c:if> >年结</option>
				</select>
	    	</p>
	    	<p>
	    		<label>天数:</label>
	    		<input id="finance-days-settlement" name="settlement.days" value="${settlement.days }" readonly="readonly" class="easyui-numberbox" data-options="min:0,precision:0,max:9999" style="width: 200px;">
	    	</p>
	    	<p style="height: auto;">
	    		<label>备注:</label>
	    		<textarea name="settlement.remark" style="height: 100px;width: 195px;" readonly="readonly"><c:out value="${settlement.remark}"></c:out></textarea>
	    	</p>
	    	<p>
	    		<label>状态:</label>
	    		<input id="common-combobox-state" value="${settlement.state }" disabled="disabled" class="easyui-combobox" style="width: 200px" />
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
    	//状态
		$('#common-combobox-state').combobox({
		    url:'common/sysCodeList.do?type=state',   
		    valueField:'id',   
		    textField:'name'  
		});
		
    	$(function(){
    		$("#settlement-button").buttonWidget("setDataID", {id:$("#settlement-oldId").val(), state:'${settlement.state}', type:'view'});
    	});
    </script>
  </body>
</html>
