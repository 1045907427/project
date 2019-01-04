<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售区域添加页面</title>
  </head>
  
  <body>
  	<form action="" id="saleChance-form-copy" method="post">
  		<input type="hidden" name="parentId" id="crmrelations-parentId-saleChance" value="<c:out value="${saleChance_Sort.pid }"></c:out>" />
  		<input type="hidden" name="parentName" id="crmrelations-parentName-saleChance" value="<c:out value="${pname }"></c:out>"/>
  		<input type="hidden" name="saleChance_Sort.oldId" value="<c:out value="${saleChance_Sort.id }"></c:out>"/>
  		<input type="hidden" id="saleChance_Sort-pid" value="<c:out value="${saleChance_Sort.pid }"></c:out>"/>
	    <div class="pageContent" style="width:500px;">
	    	<p>
	    		<label>本级编号：</label>
	    		<input class="easyui-validatebox" name="saleChance_Sort.thisid" id="crmrelations-thisId-saleChance" data-options="required:true,validType:['validLength[${len}]']"  />
	    	</p>
	    	<p>
	    		<label>编号：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${saleChance_Sort.pid }"></c:out>" name="saleChance_Sort.id" id="crmrelations-id-saleChance" />
	    	</p>
	    	<p>
	    		<label>本级名称：</label>
	    		<input type="text" class="easyui-validatebox" name="saleChance_Sort.thisname" id="crmrelations-thisName-saleChance" data-options="required:true,validType:['validUsed[50]']"/>
	    	</p>
	    	<p>
	    		<label>名称：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${pname }"></c:out>" name="saleChance_Sort.name" id="crmrelations-name-saleChance" />
	    	</p>
	    	<p>
	    		<label>上级分类：</label>
	    		<input type="text" id="crmrelations-parent-saleChance" value="<c:out value="${saleChance_Sort.pid }"></c:out>" name="saleChance_Sort.pid" />
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label>备注：</label>
	    		<textarea rows="3" cols="50" style="width: 200px;" name="saleChance_Sort.remark"><c:out value="${saleChance_Sort.remark}"></c:out></textarea>
	    	</p>
	    	<p>
	    		<label>状态：</label>
	    		<select class="easyui-combobox" disabled="disabled" style="width:206px;"  name="salesArea.state">
	    			<option value="4">新增</option>
	    		</select>
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
        function addSaleChance(type){
        	if(type == "save"){
        		if(!$("#saleChance-form-copy").form('validate')){
        			return false;
        		}
        	}
        	var ret = saleChanceSort_AjaxConn($("#saleChance-form-copy").serializeJSON(),'basefiles/crmrelations/addSaleChance.do?type='+type,'提交中..');
        	var retJson = $.parseJSON(ret);
        	if(retJson.flag){
        		if(type == "save"){
        			$.messager.alert("提醒","保存成功");
        		}
        		else{
        			$.messager.alert("提醒","暂存成功");
        		}
        		var id = $("#crmrelations-thisId-salesChanceSort").val();
	  		    var pid = $("#crmrelations-parentId-salesChanceSort").val();
	  		    var treeObj = $.fn.zTree.getZTreeObj("crmrelations-salesChanceSortTree-salesChanceSort");
	  		    var node = treeObj.getNodeByParam("id", pid, null);
  		      	treeObj.addNodes(node, retJson.node); //增加子节点
	  		    var snode = treeObj.getNodeByParam("id", retJson.node.id, null);
	  		    treeObj.selectNode(snode, false); //选中节点
	  		    zTreeBeforeClick("sales-areaTree-salesArea", snode); //执行点击事件
	  		    refreshLayout("销售机会来源【详情】",'basefiles/crmrelations/showSaleChance_SortViewPage.do?id='+$("#crmrelations-id-saleChance").val());
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
    		$("#crmrelations-thisId-salesChanceSort").val($("#saleChance_Sort-pid").val());
    		validLengthAndUsed('${len}', "basefiles/crmrelations/saleChanceNOUsed.do", $("#crmrelations-parentId-saleChance").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    		validUsed('basefiles/crmrelations/saleChanceNameNOUsed.do',$("#crmrelations-parentName-saleChance").val(),'','该名称已被使用,请另输入!');
    		$("#crmrelations-parent-saleChance").widget({
    			name:'t_base_crm_salechance_sort',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true,
    			onChecked: function(data, check){
    				if(check){
    					$("#crmrelations-id-saleChance").val(data.id + $("#crmrelations-thisId-saleChance").val());
    					if($("#crmrelations-thisName-saleChance").val() != ""){
    						$("#crmrelations-name-saleChance").val(data.name + '/' + $("#crmrelations-thisName-saleChance").val());
    					}
    					else{
    						$("#crmrelations-name-saleChance").val(data.name);
    					}
    					$("#crmrelations-parentId-saleChance").val(data.id);
    					$("#crmrelations-parentName-saleChance").val(data.name);
    					$("#crmrelations-thisId-salesChanceSort").val(data.id);
    					$("#crmrelations-parentId-salesChanceSort").val(data.pid);
    					var hasLevel = $("#crmrelations-hasLevel-salesChanceSort").val();
    					if((data.level+1)==hasLevel){
    						$.messager.alert("警告","该节点已为最大级次,不能再新增子节点!");
    						$("#crmrelations-buttons-saleChance").buttonWidget("disableButton", "button-hold");
    						$("#crmrelations-buttons-saleChance").buttonWidget("disableButton", "button-save");
    						return false;
    					}
    					else{
    						$("#crmrelations-buttons-saleChance").buttonWidget("enableButton", "button-hold");
    						$("#crmrelations-buttons-saleChance").buttonWidget("enableButton", "button-save");
    					}
    					validLengthAndUsed(saleChance_lenArr[(data.level + 1)], "basefiles/crmrelations/saleChanceNOUsed.do", $("#crmrelations-parentId-saleChance").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    				}
    				else{
    					$("#crmrelations-id-saleChance").val($("#crmrelations-thisId-saleChance").val());
    					$("#crmrelations-name-saleChance").val($("#crmrelations-thisName-saleChance").val());
    					$("#crmrelations-parentId-saleChance").val("");
    					$("#crmrelations-parentName-saleChance").val("");
    					$("#crmrelations-thisId-salesChanceSort").val("");
    					$("#crmrelations-parentId-salesChanceSort").val("");
    					$("#crmrelations-buttons-saleChance").buttonWidget("enableButton", "button-hold");
    					$("#crmrelations-buttons-saleChance").buttonWidget("enableButton", "button-save");
    					validLengthAndUsed(saleChance_lenArr[0], "basefiles/crmrelations/saleChanceNOUsed.do", $("#crmrelations-parentId-saleChance").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    				}
    			},
    			onClear: function(){
    				$("#crmrelations-id-saleChance").val($("#crmrelations-thisId-saleChance").val());
    				$("#crmrelations-name-saleChance").val($("#crmrelations-thisName-saleChance").val());
    				$("#crmrelations-parentId-saleChance").val("");
    				$("#crmrelations-parentName-saleChance").val("");
    				$("#crmrelations-thisId-salesChanceSort").val("");
    				$("#crmrelations-parentId-salesChanceSort").val("");
    				$("#crmrelations-buttons-saleChance").buttonWidget("enableButton", "button-hold");
    				$("#crmrelations-buttons-saleChance").buttonWidget("enableButton", "button-save");
    				validLengthAndUsed(saleChance_lenArr[0], "basefiles/crmrelations/saleChanceNOUsed.do", $("#crmrelations-parentId-saleChance").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    			}
    		});
    		$("#crmrelations-thisId-saleChance").change(function(){
    			$("#crmrelations-id-saleChance").val($("#crmrelations-parentId-saleChance").val() + $(this).val());
    		});
    		$("#crmrelations-thisName-saleChance").change(function(){
    			var name = $("#crmrelations-parentName-saleChance").val();
    			if(name == ""){
    				$("#crmrelations-name-saleChance").val($(this).val());
    			}
    			else{
    				$("#crmrelations-name-saleChance").val(name + '/' + $(this).val());	
    			}
    			if($(this).val() == ""){
    				$("#crmrelations-name-saleChance").val(name);
    			}
    		});
			$("#crmrelations-buttons-salesChanceSort").buttonWidget("initButtonType", 'add');
    	});
    </script>
  </body>
</html>
