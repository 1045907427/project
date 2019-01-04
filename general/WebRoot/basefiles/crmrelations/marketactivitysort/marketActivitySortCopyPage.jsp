<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>市场活动分类新增页面</title>
  </head>
  
  <body>
  	<form action="" id="CRM-form-marketActivitySortCopyPage" method="post">
  		<input type="hidden" name="parentId" id="CRM-parentId-marketActivitySortAddPage" value="<c:out value="${marketActivitySort.pid }"></c:out>" />
  		<input type="hidden" name="parentName" id="CRM-parentName-marketActivitySortAddPage" value="<c:out value="${pname }"></c:out>"/>
	    <div class="pageContent" style="width:500px;">
	    	<p>
	    		<label>本级编号：</label>
	    		<input class="easyui-validatebox" name="marketActivitySort.thisid" id="CRM-thisId-marketActivitySortAddPage" data-options="required:true,validType:['validLength[${len}]']"  />
	    	</p>
	    	<p>
	    		<label>编号：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${marketActivitySort.pid }"></c:out>" name="marketActivitySort.id" id="CRM-id-marketActivitySortAddPage" />
	    	</p>
	    	<p>
	    		<label>本级名称：</label>
	    		<input type="text" class="easyui-validatebox" name="marketActivitySort.thisname" id="CRM-thisName-marketActivitySortAddPage" required="required" validType="validUsed[50]"/>
	    	</p>
	    	<p>
	    		<label>名称：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${pname }"></c:out>" name="marketActivitySort.name" id="CRM-name-marketActivitySortAddPage" />
	    	</p>
	    	<p>
	    		<label>上级分类：</label>
	    		<input type="text" id="CRM-parent-marketActivitySortAddPage" value="<c:out value="${marketActivitySort.pid }"></c:out>" name="marketActivitySort.pid" />
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label>备注：</label>
	    		<textarea rows="3" cols="50" style="width: 200px;" name="marketActivitySort.remark"><c:out value="${marketActivitySort.remark }"></c:out></textarea>
	    	</p>
	    	<p>
	    		<label>状态：</label>
	    		<select class="easyui-combobox" disabled="disabled" style="width:206px;"  name="marketActivitySort.state">
	    			<option value="4">新增</option>
	    		</select>
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
    	function addMarketActivitySort(type){
    		if(type == "save"){
        		if(!$("#CRM-form-marketActivitySortCopyPage").form('validate')){
        			return false;
        		}
        	}
        	var ret = marketActivitySort_ajaxContent($("#CRM-form-marketActivitySortCopyPage").serializeJSON(),'basefiles/crmrelations/addmarketActivitySort.do?type='+type,'提交中..');
        	var retJson = $.parseJSON(ret);
        	if(retJson.flag){
        		if(type == "save"){
        			$.messager.alert("提醒","保存成功");
        		}
        		else{
        			$.messager.alert("提醒","暂存成功");
        		}
        		var id = $("#CRM-thisId-marketActivitySort").val();
	  		    var pid = $("#CRM-parentId-marketActivitySort").val();
	  		    var treeObj = $.fn.zTree.getZTreeObj("CRM-Tree-marketActivitySort");
	  		    var node = treeObj.getNodeByParam("id", id, null);
  		      	treeObj.addNodes(node, retJson.node); //增加子节点
	  		    var snode = treeObj.getNodeByParam("id", retJson.node.id, null);
	  		    treeObj.selectNode(snode, false); //选中节点
	  		    zTreeBeforeClick("CRM-Tree-marketActivitySort", snode); //执行点击事件
	  		    refreshLayout("任务【详情】",'basefiles/crmrelations/marketActivitySortViewPage.do?id='+$("#CRM-id-marketActivitySortAddPage").val());
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
    	
    	validUsed('basefiles/crmrelations/isUsedMarketActivitySortName.do',$("#CRM-parentName-marketActivitySortAddPage").val(),'','该名称已被使用,请另输入!');
    	
    	$(function(){
    		validLengthAndUsed('${len}', "basefiles/crmrelations/isUsedMarketActivitySortID.do", $("#CRM-parentId-marketActivitySortAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    		$("#CRM-parent-marketActivitySortAddPage").widget({
    			name:'t_base_crm_marketactivity_sort',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:false,
    			onChecked: function(data, check){
    				if(check){
    					$("#CRM-id-marketActivitySortAddPage").val(data.id + $("#CRM-thisId-marketActivitySortAddPage").val());
    					if($("#CRM-thisName-marketActivitySortAddPage").val() != ""){
    						$("#CRM-name-marketActivitySortAddPage").val(data.name + '/' + $("#CRM-thisName-marketActivitySortAddPage").val());
    					}
    					else{
    						$("#CRM-name-marketActivitySortAddPage").val(data.name);
    					}
    					$("#CRM-parentId-marketActivitySortAddPage").val(data.id);
    					$("#CRM-parentName-marketActivitySortAddPage").val(data.name);
    					$("#CRM-thisId-marketActivitySort").val(data.id);
    					$("#CRM-parentId-marketActivitySort").val(data.pid);
    					var hasLevel = $("#CRM-hasLevel-marketActivitySort").val();
    					if((data.level+1)==hasLevel){
    						$.messager.alert("警告","该节点已为最大级次,不能再新增子节点!");
    						$("#CRM-buttons-marketActivitySort").buttonWidget("disableButton", "button-hold");
    						$("#CRM-buttons-marketActivitySort").buttonWidget("disableButton", "button-save");
    						return false;
    					}
    					else{
    						$("#CRM-buttons-marketActivitySort").buttonWidget("enableButton", "button-hold");
    						$("#CRM-buttons-marketActivitySort").buttonWidget("enableButton", "button-save");
    					}
    					validLengthAndUsed(marketActivitySort_lenArr[(data.level + 1)], "basefiles/crmrelations/isUsedMarketActivitySortID.do", $("#CRM-parentId-marketActivitySortAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    				}
    				else{
    					$("#CRM-id-marketActivitySortAddPage").val($("#CRM-thisId-marketActivitySortAddPage").val());
    					$("#CRM-name-marketActivitySortAddPage").val($("#CRM-thisName-marketActivitySortAddPage").val());
    					$("#CRM-parentId-marketActivitySortAddPage").val("");
    					$("#CRM-parentName-marketActivitySortAddPage").val("");
    					$("#CRM-thisId-marketActivitySort").val("");
    					$("#CRM-parentId-marketActivitySort").val("");
    					$("#CRM-buttons-marketActivitySort").buttonWidget("enableButton", "button-hold");
    					$("#CRM-buttons-marketActivitySort").buttonWidget("enableButton", "button-save");
    					validLengthAndUsed(marketActivitySort_lenArr[0], "basefiles/crmrelations/isUsedMarketActivitySortID.do", $("#CRM-parentId-marketActivitySortAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    				}
    			},
    			onClear: function(){
    				$("#CRM-id-marketActivitySortAddPage").val($("#CRM-thisId-marketActivitySortAddPage").val());
    				$("#CRM-name-marketActivitySortAddPage").val($("#CRM-thisName-marketActivitySortAddPage").val());
    				$("#CRM-parentId-marketActivitySortAddPage").val("");
    				$("#CRM-parentName-marketActivitySortAddPage").val("");
    				$("#CRM-thisId-marketActivitySort").val("");
    				$("#CRM-parentId-marketActivitySort").val("");
    				$("#CRM-buttons-marketActivitySort").buttonWidget("enableButton", "button-hold");
    				$("#CRM-buttons-marketActivitySort").buttonWidget("enableButton", "button-save");
    				validLengthAndUsed(marketActivitySort_lenArr[0], "basefiles/crmrelations/isUsedMarketActivitySortID.do", $("#CRM-parentId-marketActivitySortAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    			}
    		});
    		$("#CRM-thisId-marketActivitySortAddPage").change(function(){
    			$("#CRM-id-marketActivitySortAddPage").val($("#CRM-parentId-marketActivitySortAddPage").val() + $(this).val());
    		});
    		$("#CRM-thisName-marketActivitySortAddPage").change(function(){
    			var name = $("#CRM-parentName-marketActivitySortAddPage").val();
    			if(name == ""){
    				$("#CRM-name-marketActivitySortAddPage").val($(this).val());
    			}
    			else{
    				$("#CRM-name-marketActivitySortAddPage").val(name + '/' + $(this).val());	
    			}
    			if($(this).val() == ""){
    				$("#CRM-name-marketActivitySortAddPage").val(name);
    			}
    		});
			$("#CRM-buttons-marketActivitySort").buttonWidget("initButtonType", 'add');
    	});
    </script>
  </body>
</html>
