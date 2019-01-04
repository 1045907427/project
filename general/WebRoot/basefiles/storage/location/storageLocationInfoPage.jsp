<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>库位档案新增页面</title>
  </head>
  
  <body>
  	<input type="hidden" id="storageLocation-oldid" value="<c:out value="${storageLocation.id }"></c:out>"/>
    <div class="pageContent" style="width:500px;">
    	<p>
    		<label style="text-align: right;">本级编码：</label>
    		<input type="text" name="storageLocation.thisid" id="storageLocation-thisid" value="<c:out value="${storageLocation.thisid}"></c:out>" readonly="readonly"/>
    	</p>
    	<p>
    		<label style="text-align: right;">编码：</label>
    		<input type="text"readonly="readonly" value="<c:out value="${storageLocation.id }"></c:out>" name="storageLocation.id" id="storageLocation-id" />
    	</p>
    	<p>
    		<label style="text-align: right;">本级名称：</label>
    		<input type="text" name="storageLocation.thisname" id="storageLocation-thisname" value="<c:out value="${storageLocation.thisname }"></c:out>" readonly="readonly"/>
    	</p>
    	<p>
    		<label style="text-align: right;">名称：</label>
    		<input type="text" name="storageLocation.name" id="storageLocation-name" value="<c:out value="${storageLocation.name }"></c:out>" readonly="readonly" />
    	</p>
    	<p>
    		<label style="text-align: right;">上级库位：</label>
    		<input type="text" id="storageLocation-parent" value="<c:out value="${storageLocation.pid }"></c:out>" name="storageLocation.pid" disabled="disabled"/>
    	</p>
    	<p>
    		<label style="text-align: right;">所属仓库：</label>
    		<input type="text" id="storageLocation-storageid" name="storageLocation.storageid" value="<c:out value="${storageLocation.storageid }"></c:out>" disabled="disabled"/>
    	</p>
    	<p>
	   		<label style="text-align: right;">库位体积(m<sup>3</sup>)：</label>
	   		<input type="text" id="storageLocation-volume" name="storageLocation.volume" value="${storageLocation.volume }" readonly="readonly" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:','"/>
	   	</p>
	   	<p>
	   		<label style="text-align: right;">当前商品箱数：</label>
	   		<input type="text" id="storageLocation-boxnum" name="storageLocation.boxnum" value="${storageLocation.boxnum }" readonly="readonly" class="easyui-numberbox" data-options="min:0,precision:0,groupSeparator:','"/>
	   	</p>
	   	<p>
	   		<label style="text-align: right;">是否空置：</label>
	   		<select id="storageLocation-isempty" name="storageLocation.isempty" disabled="disabled">
	   			<option></option>
	   			<option value="0" <c:if test="${storageLocation.isempty=='0'}">selected="selected"</c:if>>否</option>
	   			<option value="1" <c:if test="${storageLocation.isempty=='1'}">selected="selected"</c:if>>是</option>
	   		</select>
	   	</p>
    	<p style="height:auto;width:auto;">
    		<label style="text-align: right;">备注：</label>
    		<textarea name="storageLocation.remark" style="height: 100px;width:195px;"><c:out value="${storageLocation.remark }"></c:out></textarea>
    	</p>
    	<p>
    		<label style="text-align: right;">状态：</label>
    		<select class="easyui-combobox" disabled="disabled" style="width:200px;"  name="storageLocation.state">
    			<c:forEach items="${stateList }" var="list">
    				<c:choose>
    					<c:when test="${list.code == storageLocation.state}">
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
    <script type="text/javascript">
    	$(function(){
    		$("#storageLocation-button").buttonWidget("setDataID", {id:$("#storageLocation-oldid").val(), state:${storageLocation.state},type:'view'});
    		//未启用的节点 不能新增子节点
			if('${storageLocation.state}'!='1'){
				$("#storageLocation-button").buttonWidget("disableButton", "button-add");
			}else{
				$("#storageLocation-button").buttonWidget("enableButton", "button-add");
			}
    		$("#storageLocation-parent").widget({
    			name:'t_base_storage_location',
				col:'pid',
    			width:200,
    			view:true
    		});
    		$("#storageLocation-storageid").widget({
    			name:'t_base_storage_location',
				col:'storageid',
    			singleSelect:true,
    			width:200,
    			view:true
    		});
    	});
    	
    </script>
  </body>
</html>
