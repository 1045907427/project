<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>科目详情页面</title>
  </head>
  
  <body>
  	<form action="" id="basefiles-form-subjectViewPage" method="post">
  		<input type="hidden" id="subject-oldid" value="<c:out value="${subject.id }"></c:out>" />
	    <div class="pageContent" style="width:500px;">
	    	<p>
	    		<label>本级编号：</label>
	    		<input type="text" value="<c:out value="${subject.thisid }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>编号：</label>
	    		<input type="text" value="<c:out value="${subject.id }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>本级名称：</label>
	    		<input type="text" value="<c:out value="${subject.thisname }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>名称：</label>
	    		<input type="text" value="<c:out value="${subject.name}"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>上级科目：</label>
	    		<input type="text" id="basefiles-parent-subjectAddPage" disabled="disabled" value="<c:out value="${subject.pid }"></c:out>" />
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label>备注：</label>
	    		<textarea rows="3" cols="50" style="width: 195px;" readonly="readonly" name="subject.remark"><c:out value="${subject.remark }"></c:out></textarea>
	    	</p>
	    	<p>
	    		<label>状态：</label>
	    		<input id="common-combobox-state" value="${subject.state }" disabled="disabled" class="easyui-combobox" style="width: 200px" />
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
    	//加载下拉框
    	loaddropdown();
    	
    	$(function(){
    		$("#basefiles-parent-subjectAddPage").widget({
    			name:'t_base_subject',
				col:'pid',
    			singleSelect:true,
    			width:200,
	   			param:[
	   			       {field:'typecode',op:'equal',value:'${subjectType.typecode}'}
	   			],
    			onlyLeafCheck:true,
    			view:true
    		});
			$("#basefiles-buttons-subject").buttonWidget("setDataID", {id:$("#subject-oldid").val(), state:'${subject.state}', type:'view'});
			if('${subject.state}' != '1'){
				$("#basefiles-buttons-subject").buttonWidget('disableButton','button-add');
			}else{
				$("#basefiles-buttons-subject").buttonWidget('enableButton','button-add');
			}
			if('${subject.state}' == '1'){
				$("#basefiles-buttons-subject").buttonWidget('disableButton','button-delete');				
			}
    	});
    </script>
  </body>
</html>
