<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>费用科目详情页面</title>
  </head>
  
  <body>
  	<form action="" id="journalsheet-form-deptcostssubjectViewPage" method="post">
  		<input type="hidden" id="deptCostsSubject-oldid" value="<c:out value="${deptCostsSubject.id }"></c:out>" />
	    <div class="pageContent" style="width:500px;">
	    	<p>
	    		<label>本级编号：</label>
	    		<input type="text" value="<c:out value="${deptCostsSubject.thisid }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>编号：</label>
	    		<input type="text" value="<c:out value="${deptCostsSubject.id }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>本级名称：</label>
	    		<input type="text" value="<c:out value="${deptCostsSubject.thisname }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>名称：</label>
	    		<input type="text" value="<c:out value="${deptCostsSubject.name}"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>上级分类：</label>
	    		<input type="text" id="journalsheet-parent-deptcostssubjectAddPage" disabled="disabled" value="<c:out value="${deptCostsSubject.pid }"></c:out>" />
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label>备注：</label>
	    		<textarea rows="3" cols="50" style="width: 195px;" readonly="readonly" name="deptCostsSubject.remark"><c:out value="${deptCostsSubject.remark }"></c:out></textarea>
	    	</p>
	    	<p>
	    		<label>状态：</label>
	    		<input id="common-combobox-state" value="${deptCostsSubject.state }" disabled="disabled" class="easyui-combobox" style="width: 200px" />
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
    	//加载下拉框
    	loaddropdown();
    	
    	$(function(){
    		$("#journalsheet-parent-deptcostssubjectAddPage").widget({
    			name:'t_js_departmentcosts_subject',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true,
    			view:true
    		});
			$("#journalsheet-buttons-deptcostssubject").buttonWidget("setDataID", {id:$("#deptCostsSubject-oldid").val(), state:'${deptCostsSubject.state}', type:'view'});
			if('${deptCostsSubject.state}' != '1'){
				$("#journalsheet-buttons-deptcostssubject").buttonWidget('disableButton','button-add');
			}else{
				$("#journalsheet-buttons-deptcostssubject").buttonWidget('enableButton','button-add');
			}
			if('${deptCostsSubject.state}' == '1'){
				$("#journalsheet-buttons-deptcostssubject").buttonWidget('disableButton','button-delete');				
			}
    	});
    </script>
  </body>
</html>
