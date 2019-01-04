<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>任务分类复制页面</title>
  </head>
  
  <body>
    <form action="" id="taskSort-form-copy" method="post">
    	<input type="hidden" id="taskSort-parentid" value="<c:out value="${taskSort.pid }"></c:out>" />
    	<input type="hidden" name="parentId" id="crmrelations-parentId-taskSortAdd" value="<c:out value="${taskSort.pid }"></c:out>" />
  		<input type="hidden" name="parentName" id="crmrelations-parentName-taskSortAdd" value="<c:out value="${pname }"></c:out>"/>
  		<div class="pageContent" style="width:500px;">
	    	<p>
	    		<label>本级编号：</label>
	    		<input class="easyui-validatebox" name="taskSort.thisid" id="crmrelations-thisId-taskSortAdd" data-options="required:true,validType:['validLength[${len}]']" />
	    	</p>
	    	<p>
	    		<label>编号：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${taskSort.pid }"></c:out>" name="taskSort.id" id="crmrelations-id-taskSortAdd" />
	    	</p>
	    	<p>
	    		<label>本级名称：</label>
	    		<input type="text" class="easyui-validatebox" name="taskSort.thisname" id="crmrelations-thisName-taskSortAdd" data-options="required:true,validType:['validUsed[50]']"/>
	    	</p>
	    	<p>
	    		<label>名称：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${pname }"></c:out>" name="taskSort.name" id="crmrelations-name-taskSortAdd" />
	    	</p>
	    	<p>
	    		<label>上级分类：</label>
	    		<input type="text" id="crmrelations-parent-taskSort" value="<c:out value="${taskSort.pid }"></c:out>" name="taskSort.pid" />
	    	</p>
	    	<p>
	    		<label>默认责任部门：</label>
	    		<input type="text" id="crmrelations-defaultDept-taskSort" name="taskSort.defaultdeptid" value="<c:out value="${taskSort.defaultdeptid}"></c:out>" />
	    	</p>
	    	<p>
	    		<label>默认责任人：</label>
	    		<input type="text" id="crmrelations-defaultUser-taskSort" name="taskSort.defaultuserid" value="<c:out value="${taskSort.defaultuserid}"></c:out>" />
	    	</p>
	    	<p>
	    		<label>默认费用分类：</label>
	    		<input type="text" id="crmrelations-defaultexpenses-taskSort" name="taskSort.defaultexpensesid" value="<c:out value="${taskSort.defaultexpensesid}"></c:out>" />
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label>备注：</label>
	    		<textarea rows="3" cols="50" style="width: 200px;" name="taskSort.remark"><c:out value="${taskSort.remark}"></c:out></textarea>
	    	</p>
	    	<p>
	    		<label>状态：</label>
	    		<select class="easyui-combobox" disabled="disabled" style="width:206px;"  name="taskSort.state">
	    			<option value="4">新增</option>
	    		</select>
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
    	//加载下拉控件
    	loadDropdown();
    	function addTaskSort(type){
        	if(type == "save"){
        		if(!$("#taskSort-form-copy").form('validate')){
        			return false;
        		}
        	}
        	var ret = taskSort_AjaxConn($("#taskSort-form-copy").serializeJSON(),'basefiles/crmrelations/addTaskSort.do?type='+type,'提交中..');
        	var retJson = $.parseJSON(ret);
        	if(retJson.flag){
        		if(type == "save"){
        			$.messager.alert("提醒","保存成功");
        		}
        		else{
        			$.messager.alert("提醒","暂存成功");
        		}
        		var id = $("#crmrelations-thisId-taskSort").val();
	  		    var pid = $("#crmrelations-parentId-taskSort").val();
	  		    var treeObj = $.fn.zTree.getZTreeObj("crmrelations-taskSortTree-taskSort");
	  		    var node = treeObj.getNodeByParam("id", id, null);
  		      	treeObj.addNodes(node, retJson.node); //增加子节点
	  		    var snode = treeObj.getNodeByParam("id", retJson.node.id, null);
	  		    treeObj.selectNode(snode, false); //选中节点
	  		    zTreeBeforeClick("crmrelations-taskSortTree-taskSort", snode); //执行点击事件
	  		    refreshLayout("任务【详情】",'basefiles/crmrelations/showTaskSortViewPage.do?id='+$("#crmrelations-id-taskSortAdd").val());
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
        
        validUsed('basefiles/crmrelations/isUsedTaskSortName.do',$("#crmrelations-parentName-taskSortAdd").val(),'','该名称已被使用,请另输入!');
        
    	$(function(){
    		validLengthAndUsed('${len}', "basefiles/crmrelations/isUsedTaskSortID.do", $("#crmrelations-parentId-taskSortAdd").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    		$("#crmrelations-thisId-taskSort").val($("#taskSort-parentid").val());
    		$("#crmrelations-parent-taskSortAdd").widget({
    			name:'t_base_crm_task_sort',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:false,
    			onChecked: function(data, check){
    				if(check){
    					$("#crmrelations-id-taskSortAdd").val(data.id + $("#crmrelations-thisId-taskSortAdd").val());
    					$("#crmrelations-name-taskSortAdd").val(data.name + '/' + $("#crmrelations-thisName-taskSortAdd").val());
    					$("#crmrelations-parentId-taskSortAdd").val(data.id);
    					$("#crmrelations-parentName-taskSortAdd").val(data.name);
    					$("#crmrelations-thisId-taskSort").val(data.id);
    					$("#crmrelations-parentId-taskSort").val(data.pid);
    				}
    				else{
    					$("#crmrelations-id-taskSortAdd").val($("#crmrelations-thisId-taskSortAdd").val());
    					$("#crmrelations-name-taskSortAdd").val($("#crmrelations-thisName-taskSortAdd").val());
    					$("#crmrelations-parentId-taskSortAdd").val("");
    					$("#crmrelations-parentName-taskSortAdd").val("");
    					$("#crmrelations-thisId-taskSort").val("");
    					$("#crmrelations-parentId-taskSort").val("");
    				}
    			}
    		});
    		$("#crmrelations-thisId-taskSortAdd").change(function(){
    			$("#crmrelations-id-taskSortAdd").val($("#crmrelations-parentId-taskSortAdd").val() + $(this).val());
    		});
    		$("#crmrelations-thisName-taskSortAdd").change(function(){
    			var name = $("#crmrelations-parentName-taskSortAdd").val();
    			if(name == ""){
    				$("#crmrelations-name-taskSortAdd").val($(this).val());
    			}
    			else{
    				$("#crmrelations-name-taskSortAdd").val(name + '/' + $(this).val());	
    			}
    			if($(this).val() == ""){
    				$("#crmrelations-name-taskSortAdd").val(name);
    			}
    		});
			$("#crmrelations-buttons-taskSort").buttonWidget("initButtonType", 'add');
    	});
    </script>
  </body>
</html>
