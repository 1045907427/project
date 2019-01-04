<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购区域添加页面</title>
  </head>
  
  <body>
  	<form action="basefiles/addBuyArea.do" id="buy-form-buyAreaAddPage" method="post">
  		<input type="hidden" id="buyArea-oldid" value="<c:out value="${buyArea.id }"></c:out>" />
	    <div class="pageContent" style="width:500px;">
	    	<p>
	    		<label>本级编号：</label>
	    		<input type="text" class="easyui-validatebox" value="<c:out value="${buyArea.thisid }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>编号：</label>
	    		<input type="text" class="easyui-validatebox" value="<c:out value="${buyArea.id }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>本级名称：</label>
	    		<input type="text" class="easyui-validatebox" value="<c:out value="${buyArea.thisname }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>名称：</label>
	    		<input type="text" class="easyui-validatebox" value="<c:out value="${buyArea.name }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>上级分类：</label>
	    		<input type="text" id="buy-parent-buyAreaAddPage" disabled="disabled" value="<c:out value="${buyArea.pid }"></c:out>" name="buyArea.pid" />
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label>备注：</label>
	    		<textarea style="width:195px;height:50px;" name="buyArea.remark"><c:out value="${buyArea.remark }"></c:out></textarea>
	    	</p>
	    	<p>
	    		<label>状态：</label>
	    		<select class="easyui-combobox" disabled="disabled" style="width:200px;"  name="buyArea.state">
	    			<c:forEach items="${stateList }" var="list">
	    				<c:choose>
	    					<c:when test="${list.code == buyArea.state }">
	    						<option value="${list.code }" selected="selected">${list.codename }</option>
	    					</c:when>
	    					<c:otherwise>
	    						<option value="${list.code }">${list.codename }</option>
	    					</c:otherwise>
	    				</c:choose>
	    			</c:forEach>
	    		</select>
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
    	$(function(){
    		$("#buy-parent-buyAreaAddPage").widget({
    			name:'t_base_buy_area',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
			$("#buy-buttons-buyArea").buttonWidget("setDataID", {id:$("#buyArea-oldid").val(), state:'${buyArea.state}',type:'view'});
			if('${buyArea.state}' != '1'){
                $("#buy-buttons-buyArea").buttonWidget('disableButton','button-add');
			}
    	});
    </script>
  </body>
</html>
