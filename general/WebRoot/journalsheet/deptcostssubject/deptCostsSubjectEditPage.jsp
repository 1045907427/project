<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>费用科目修改页面</title>
  </head>
  
  <body>
  	<form action="" id="journalsheet-form-deptcostssubjectEditPage" method="post">
  		<input type="hidden" name="parentId" id="journalsheet-parentId-deptcostssubjectAddPage" value="<c:out value="${deptCostsSubject.pid }"></c:out>" />
  		<input type="hidden" name="parentName" id="journalsheet-parentName-deptcostssubjectAddPage" value="<c:out value="${parentName }"></c:out>" />
  		<input type="hidden" id="deptCostsSubject-oldid" name="deptCostsSubject.oldId" value="<c:out value="${deptCostsSubject.id }"></c:out>" />
  		<input type="hidden" id="deptCostsSubject-thisid" value="<c:out value="${deptCostsSubject.thisid }"></c:out>" />
  		<input type="hidden" id="deptCostsSubject-thisname" value="<c:out value="${deptCostsSubject.thisname }"></c:out>" />
  		<input type="hidden" name="deptCostsSubject.state" value="<c:out value="${deptCostsSubject.state }"></c:out>" />
  		<!-- 修改标识，判断有没有引用 -->
  		<input type="hidden" id="journalsheet-editType-deptcostssubjectAddPage" value="${editFlag }" />
	    <div class="pageContent" style="width:500px;">
	    	<p>
	    		<label>本级编号：</label>
	    		<input type="text" class="easyui-validatebox" name="deptCostsSubject.thisid" value="<c:out value="${deptCostsSubject.thisid }"></c:out>" <c:if test="${editFlag == false }">readonly="readonly"</c:if> id="journalsheet-thisId-deptcostssubjectAddPage" data-options="required:true,validType:['validLength[${len}]']" />
	    	</p>
	    	<p>
	    		<label>编号：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${deptCostsSubject.id }"></c:out>" name="deptCostsSubject.id" id="journalsheet-id-deptcostssubjectAddPage" />
	    	</p>
	    	<p>
	    		<label>本级名称：</label>
	    		<input type="text" class="easyui-validatebox" name="deptCostsSubject.thisname" value="<c:out value="${deptCostsSubject.thisname }"></c:out>" id="journalsheet-thisName-deptcostssubjectAddPage" required="required" validType="validUsed[50]"/>
	    	</p>
	    	<p>
	    		<label>名称：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${deptCostsSubject.name}"></c:out>" name="deptCostsSubject.name" id="journalsheet-name-deptcostssubjectAddPage" />
	    	</p>
	    	<p>
	    		<label>上级分类：</label>
	    		<input type="text" id="journalsheet-parent-deptcostssubjectAddPage" value="<c:out value="${deptCostsSubject.pid }"></c:out>" name="deptCostsSubject.pid" <c:if test="${editFlag ==false}">readonly="readonly"</c:if> />
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label>备注：</label>
	    		<textarea rows="3" cols="50" class="easyui-validatebox" style="width: 195px;" name="deptCostsSubject.remark" validType="maxLen[200]"><c:out value="${deptCostsSubject.remark }"></c:out></textarea>
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
		
		maxLen();
		
		function  editDeptCostsSubject(type){
        	if(type == "save"){
        		if(!$("#journalsheet-form-deptcostssubjectEditPage").form('validate')){
        			return false;
        		}
        	}
        	loading("提交中..");
        	var ret = deptCostsSubject_ajaxContent($("#journalsheet-form-deptcostssubjectEditPage").serializeJSON(),'journalsheet/costsFee/editDeptCostsSubject.do?type='+type);
        	var retJson = $.parseJSON(ret);
        	loaded();
        	if(retJson.flag){
        		if(type == "save"){
        			$.messager.alert("提醒","保存成功");
        		}
        		else{
        			$.messager.alert("提醒","暂存成功");
        		}
        		//更新所有子节点
       			var map = retJson.nodes;
       			var treeObj = $.fn.zTree.getZTreeObj("journalsheet-Tree-deptcostssubject");
       			for(var key in map){
       				var object = map[key];
       				var node = treeObj.getNodeByParam("id", key, null);
       				node.id = object.id;
					node.text = object.text;
					node.parentid = object.parentid;
					node.state = object.state;
					treeObj.updateNode(node);
       			}
	  		    refreshLayout("费用科目【详情】",'journalsheet/costsFee/showDeptCostsSubjectViewPage.do?id='+$("#journalsheet-id-deptcostssubjectAddPage").val());
        	}
        	else{
        		if(type == "save"){
        			$.messager.alert("提醒","保存失败");
        		}
        		else{
        			$.messager.alert("提醒","暂存失败");
        		}
        	}
        }
		
    	$(function(){
    		validLengthAndUsed('${len}', "journalsheet/costsFee/isUsedDeptCostsSubjectID.do", $("#journalsheet-parentId-deptcostssubjectAddPage").val(), $("#deptCostsSubject-thisid").val(), "该编号已被使用，请另输编号！"); //验证输入长度
    		validUsed('journalsheet/costsFee/isUsedDeptCostsSubjectName.do',$("#journalsheet-parentName-deptcostssubjectAddPage").val(),$("#deptCostsSubject-thisname").val(),'该名称已被使用,请另输入!');
    		$("#journalsheet-parent-deptcostssubjectAddPage").widget({
    			name:'t_js_departmentcosts_subject',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:false,
    			onChecked: function(data, check){
    				if(check){
    					$("#journalsheet-id-deptcostssubjectAddPage").val(data.id + $("#journalsheet-thisId-deptcostssubjectAddPage").val());
    					if($("#journalsheet-thisName-deptcostssubjectAddPage").val() != ""){
    						$("#journalsheet-name-deptcostssubjectAddPage").val(data.name + '/' + $("#journalsheet-thisName-deptcostssubjectAddPage").val());
    					}
    					else{
    						$("#journalsheet-name-deptcostssubjectAddPage").val(data.name);
    					}
    					$("#journalsheet-parentId-deptcostssubjectAddPage").val(data.id);
    					$("#journalsheet-parentName-deptcostssubjectAddPage").val(data.name);
    					$("#journalsheet-thisId-deptcostssubject").val(data.id);
    					$("#journalsheet-parentId-deptcostssubject").val(data.pid);
    					var hasLevel = $("#journalsheet-hasLevel-deptcostssubject").val();
    					if((data.level+1)==hasLevel){
    						$.messager.alert("警告","该节点已为最大级次,不能再新增子节点!");
    						$("#journalsheet-buttons-deptcostssubject").buttonWidget("disableButton", "button-hold");
    						$("#journalsheet-buttons-deptcostssubject").buttonWidget("disableButton", "button-save");
    						return false;
    					}
    					else{
    						$("#journalsheet-buttons-deptcostssubject").buttonWidget("enableButton", "button-hold");
    						$("#journalsheet-buttons-deptcostssubject").buttonWidget("enableButton", "button-save");
    					}
    					validLengthAndUsed(deptCostsSubject_lenArr[parseInt(data.level)], "journalsheet/costsFee/isUsedDeptCostsSubjectID.do", $("#journalsheet-parentId-deptcostssubjectAddPage").val(), "该编号已被使用，请另输编号！"); //验证输入长度
    				}
    				else{
    					$("#journalsheet-id-deptcostssubjectAddPage").val($("#journalsheet-thisId-deptcostssubjectAddPage").val());
    					$("#journalsheet-name-deptcostssubjectAddPage").val($("#journalsheet-thisName-deptcostssubjectAddPage").val());
    					$("#journalsheet-parentId-deptcostssubjectAddPage").val("");
    					$("#journalsheet-parentName-deptcostssubjectAddPage").val("");
    					$("#journalsheet-thisId-deptcostssubject").val("");
    					$("#journalsheet-parentId-deptcostssubject").val("");
    					$("#journalsheet-buttons-deptcostssubject").buttonWidget("enableButton", "button-hold");
    					$("#journalsheet-buttons-deptcostssubject").buttonWidget("enableButton", "button-save");
    					validLengthAndUsed(deptCostsSubject_lenArr[0], "journalsheet/costsFee/isUsedDeptCostsSubjectID.do", $("#journalsheet-parentId-deptcostssubjectAddPage").val(), $("#deptCostsSubject-thisid").val(), "该编号已被使用，请另输编号！"); //验证输入长度
    				}
    			},
    			onClear: function(){
    				$("#journalsheet-id-deptcostssubjectAddPage").val($("#journalsheet-thisId-deptcostssubjectAddPage").val());
    				$("#journalsheet-name-deptcostssubjectAddPage").val($("#journalsheet-thisName-deptcostssubjectAddPage").val());
    				$("#journalsheet-parentId-deptcostssubjectAddPage").val("");
    				$("#journalsheet-parentName-deptcostssubjectAddPage").val("");
    				$("#journalsheet-thisId-deptcostssubject").val("");
    				$("#journalsheet-parentId-deptcostssubject").val("");
    				$("#journalsheet-buttons-deptcostssubject").buttonWidget("enableButton", "button-hold");
    				$("#journalsheet-buttons-deptcostssubject").buttonWidget("enableButton", "button-save");
    				validLengthAndUsed(deptCostsSubject_lenArr[0], "journalsheet/costsFee/isUsedDeptCostsSubjectID.do", $("#journalsheet-parentId-deptcostssubjectAddPage").val(), $("#deptCostsSubject-thisid").val(), "该编号已被使用，请另输编号！"); //验证输入长度
    			}
    		});
    		$("#journalsheet-buttons-deptcostssubject").buttonWidget("setDataID", {id:$("#deptCostsSubject-oldid").val(), state:'${deptCostsSubject.state}', type:'edit'});
    		$("#journalsheet-thisId-deptcostssubjectAddPage").change(function(){
    			$("#journalsheet-id-deptcostssubjectAddPage").val($("#journalsheet-parentId-deptcostssubjectAddPage").val() + $(this).val());
    		});
    		$("#journalsheet-thisName-deptcostssubjectAddPage").change(function(){
    			var name = $("#journalsheet-parentName-deptcostssubjectAddPage").val();
    			if(name == ""){
    				$("#journalsheet-name-deptcostssubjectAddPage").val($(this).val());
    			}
    			else{
    				$("#journalsheet-name-deptcostssubjectAddPage").val(name + '/' + $(this).val());	
    			}
    			if($(this).val() == ""){
    				$("#journalsheet-name-deptcostssubjectAddPage").val(name);
    			}
    		});
    	});
    </script>
  </body>
</html>
