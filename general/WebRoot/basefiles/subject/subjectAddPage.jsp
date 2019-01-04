<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>科目新增页面</title>
  </head>
  
  <body>
  	<form action="" id="basefiles-form-subjectAddPage" method="post">
  		<input type="hidden" name="parentId" id="basefiles-parentId-subjectAddPage" value="${subject.id }" />
  		<input type="hidden" name="subject.typeid" id="basefiles-typeid-subjectAddPage" value="${subjectType.id }" />
  		<input type="hidden" name="parentName" id="basefiles-parentName-subjectAddPage" value="<c:if test="${subject.istypehead !='1' }">${subject.name}</c:if>" />  		
	    <div class="pageContent" style="width:500px;">
	    	<p>
	    		<label>本级编号：</label>
	    		<input class="easyui-validatebox" name="subject.thisid" id="basefiles-thisId-subjectAddPage" data-options="required:true,validType:['validLength[${len}]']"  />
	    	</p>
	    	<p>
	    		<label>编号：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${subject.id }"></c:out>" name="subject.id" id="basefiles-id-subjectAddPage" />
	    	</p>
	    	<p>
	    		<label>本级名称：</label>
	    		<input type="text" class="easyui-validatebox" name="subject.thisname" id="basefiles-thisName-subjectAddPage" required="required" validType="validUsed[50]"/>
	    	</p>
	    	<p>
	    		<label>名称：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:if test="${subject.istypehead !='1' }">${subject.name}</c:if>" name="subject.name" id="basefiles-name-subjectAddPage" />
	    	</p>
	    	<p>
	    		<label>上级科目：</label>
	    		<input type="text" id="basefiles-parent-subjectAddPage" value="<c:out value="${subject.id }"></c:out>" name="subject.pid" />
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label>备注：</label>
	    		<textarea rows="3" cols="50" class="easyui-validatebox" style="width: 195px;" name="subject.remark" validType="maxLen[200]"></textarea>
	    	</p>
	    	<p>
	    		<label>状态：</label>
	    		<select class="easyui-combobox" disabled="disabled" style="width:200px;"  name="subject.state">
	    			<option value="4">新增</option>
	    		</select>
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
    	//加载下拉框
    	loaddropdown();
    	
    	maxLen();
    	
    	validUsed('basefiles/subject/isUsedSubjectName.do',$("#basefiles-parentName-subjectAddPage").val(),'','该名称已被使用,请另输入!');
    	
    	$(function(){
    		validLengthAndUsed('${len}', "basefiles/subject/isUsedSubjectID.do", $("#basefiles-parentId-subjectAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    		$("#basefiles-parent-subjectAddPage").widget({
    			name:'t_base_subject',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			treePName:false,
    			treeNodeDataUseNocheck:true,
	   			param:[
	   			       {field:'typecode',op:'equal',value:'${subjectType.typecode}'}
	   			],
    			onlyLeafCheck:true,
    			required:true,
    			onChecked: function(data, check){
    				if(check){
    					
    					$("#basefiles-id-subjectAddPage").val(data.id + $("#basefiles-thisId-subjectAddPage").val());
    					$("#basefiles-parentId-subjectAddPage").val(data.id);
						var thelevel=data.level;
    					if(data.istypehead!='1'){
	    					if($("#basefiles-thisName-subjectAddPage").val() != ""){
	    						$("#basefiles-name-subjectAddPage").val(data.name + '/' + $("#basefiles-thisName-subjectAddPage").val());
	    					}
	    					else{
	    						$("#basefiles-name-subjectAddPage").val(data.name);
	    					}
    						$("#basefiles-parentName-subjectAddPage").val(data.name);
    					}else{
    						$("#basefiles-parentName-subjectAddPage").val("");
    						$("#basefiles-name-subjectAddPage").val($("#basefiles-thisName-subjectAddPage").val());
    						thelevel=data.level+1;
    					}
    					$("#basefiles-thisId-subject").val(data.id);
    					$("#basefiles-parentId-subject").val(data.pid);
    					var hasLevel = $("#basefiles-hasLevel-subject").val();
    					if(thelevel==hasLevel){
    						$.messager.alert("警告","该节点已为最大级次,不能再新增子节点!");
    						$("#basefiles-buttons-subject").buttonWidget("disableButton", "button-hold");
    						$("#basefiles-buttons-subject").buttonWidget("disableButton", "button-save");
    						return false;
    					}
    					else{
    						$("#basefiles-buttons-subject").buttonWidget("enableButton", "button-hold");
    						$("#basefiles-buttons-subject").buttonWidget("enableButton", "button-save");
    					}
    					validLengthAndUsed(subject_lenArr[parseInt(thelevel)], "basefiles/subject/isUsedSubjectID.do", $("#basefiles-parentId-subjectAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    				}
    				else{
    					$("#basefiles-id-subjectAddPage").val($("#basefiles-thisId-subjectAddPage").val());
    					$("#basefiles-name-subjectAddPage").val($("#basefiles-thisName-subjectAddPage").val());
    					$("#basefiles-parentId-subjectAddPage").val("");
    					$("#basefiles-parentName-subjectAddPage").val("");
    					$("#basefiles-thisId-subject").val("");
    					$("#basefiles-parentId-subject").val("");
    					$("#basefiles-buttons-subject").buttonWidget("enableButton", "button-hold");
    					$("#basefiles-buttons-subject").buttonWidget("enableButton", "button-save");
    					$("#basefiles-parent-subjectAddPage").widget('setValue','${param.typeid}');
    					//validLengthAndUsed(subject_lenArr[0], "basefiles/subject/isUsedSubjectID.do", $("#basefiles-parentId-subjectAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    				}
    			},
    			onClear: function(){
    				$("#basefiles-id-subjectAddPage").val($("#basefiles-thisId-subjectAddPage").val());
    				$("#basefiles-name-subjectAddPage").val($("#basefiles-thisName-subjectAddPage").val());
    				$("#basefiles-parentId-subjectAddPage").val("");
    				$("#basefiles-parentName-subjectAddPage").val("");
    				$("#basefiles-thisId-subject").val("");
    				$("#basefiles-parentId-subject").val("");
    				$("#basefiles-buttons-subject").buttonWidget("enableButton", "button-hold");
    				$("#basefiles-buttons-subject").buttonWidget("enableButton", "button-save");
    				//validLengthAndUsed(subject_lenArr[0], "basefiles/subject/isUsedSubjectID.do", $("#basefiles-parentId-subjectAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    				$("#basefiles-parent-subjectAddPage").widget('setValue','${param.typeid}');
    				
    			}
    		});
    		$("#basefiles-thisId-subjectAddPage").change(function(){
    			$("#basefiles-id-subjectAddPage").val($("#basefiles-parentId-subjectAddPage").val() + $(this).val());
    		});
    		$("#basefiles-thisName-subjectAddPage").change(function(){
    			var name = $("#basefiles-parentName-subjectAddPage").val();
    			if(name == ""){
    				$("#basefiles-name-subjectAddPage").val($(this).val());
    			}
    			else{
    				$("#basefiles-name-subjectAddPage").val(name + '/' + $(this).val());	
    			}
    			if($(this).val() == ""){
    				$("#basefiles-name-subjectAddPage").val(name);
    			}
    		});
			$("#basefiles-buttons-subject").buttonWidget("initButtonType", 'add');
    		
   			<c:if test="${subject.istypehead =='1' }">
				$("#basefiles-buttons-subject").buttonWidget("disableButton", "button-edit");
				$("#basefiles-buttons-subject").buttonWidget("disableButton", "button-delete");
				$("#basefiles-buttons-subject").buttonWidget("disableButton", "button-copy");
				$("#basefiles-buttons-subject").buttonWidget("disableButton", "button-open");
				$("#basefiles-buttons-subject").buttonWidget("disableButton", "button-close");
   			</c:if>
    	});
    </script>
  </body>
</html>
