<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售区域添加页面</title>
  </head>
  
  <body>
  	<form action="" id="saleChance-form-edit" method="post">
  		<input type="hidden" name="parentId" id="crmrelations-parentId-saleChance" value="<c:out value="${saleChance_Sort.pid }"></c:out>" />
  		<input type="hidden" name="parentName" id="crmrelations-parentName-saleChance" value="<c:out value="${pname }"></c:out>"/>
  		<input type="hidden" id="saleChance_Sort-oldid" name="saleChance_Sort.oldId" value="<c:out value="${saleChance_Sort.id }"></c:out>"/>
  		<input type="hidden" id="saleChance_Sort-thisid" value="<c:out value="${saleChance_Sort.thisid }"></c:out>"/>
  		<input type="hidden" id="saleChance_Sort-thisname" value="<c:out value="${saleChance_Sort.thisname }"></c:out>"/>
  		<input type="hidden" name="saleChance_Sort.state" value="${saleChance_Sort.state }" />
  		<!-- 修改标识，判断有没有引用 -->
  		<input type="hidden" id="crmrelations-editType-saleChance" value="${editFlag}" />
	    <div class="pageContent" style="width:500px;">
	    	<p>
	    		<label>本级编号：</label>
	    		<input class="easyui-validatebox" name="saleChance_Sort.thisid" value="<c:out value="${saleChance_Sort.thisid }"></c:out>" <c:if test="${editFlag == false }">readonly="readonly"</c:if> id="crmrelations-thisId-saleChance" data-options="required:true,validType:['validLength[${len}]']"  />
	    	</p>
	    	<p>
	    		<label>编号：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${saleChance_Sort.id }"></c:out>" name="saleChance_Sort.id" id="crmrelations-id-saleChance" />
	    	</p>
	    	<p>
	    		<label>本级名称：</label>
	    		<input type="text" class="easyui-validatebox" name="saleChance_Sort.thisname" value="<c:out value="${saleChance_Sort.thisname}"></c:out>" id="crmrelations-thisName-saleChance" data-options="required:true,validType:['validUsed[50]']"/>
	    	</p>
	    	<p>
	    		<label>名称：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${saleChance_Sort.name}"></c:out>" name="saleChance_Sort.name" id="crmrelations-name-saleChance" />
	    	</p>
	    	<p>
	    		<label>上级分类：</label>
	    		<input type="text" id="crmrelations-parent-saleChance" value="<c:out value="${saleChance_Sort.pid }"></c:out>" name="saleChance_Sort.pid" <c:if test="${editFlag == false }">disabled="disabled"</c:if>/>
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label>备注：</label>
	    		<textarea rows="3" cols="50" style="width: 200px;" name="saleChance_Sort.remark"><c:out value="${saleChance_Sort.remark}"></c:out></textarea>
	    	</p>
	    	<p>
	    		<label>状态：</label>
	    		<input id="common-combobox-state" value="${saleChance_Sort.state}" disabled="disabled" class="easyui-combobox" style="width: 206px" />
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
        function editSaleChance(type){
        	if(type == "save"){
        		if(!$("#saleChance-form-edit").form('validate')){
        			return false;
        		}
        	}
        	var ret = saleChanceSort_AjaxConn($("#saleChance-form-edit").serializeJSON(),'basefiles/crmrelations/editSaleChance.do?type='+type,'提交中..');
        	var retJson = $.parseJSON(ret);
        	if(retJson.flag){
        		if(type == "save"){
        			$.messager.alert("提醒","保存成功");
        		}
        		else{
        			$.messager.alert("提醒","暂存成功");
        		}
        		//更新所有子节点
       			var map = retJson.nodes;
       			var treeObj = $.fn.zTree.getZTreeObj("crmrelations-salesChanceSortTree-salesChanceSort");
       			for(var key in map){
       				var object = map[key];
       				var node = treeObj.getNodeByParam("id", key, null);
       				node.id = object.id;
					node.text = object.text;
					node.parentid = object.parentid;
					node.state = object.state;
					treeObj.updateNode(node);
       			}
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
    		validLengthAndUsed('${len}', "basefiles/crmrelations/saleChanceNOUsed.do", $("#crmrelations-parentId-saleChance").val(), $("#saleChance_Sort-thisid").val(), "该编号已被使用，请另输编号！"); //验证输入长度
    		validUsed('basefiles/crmrelations/saleChanceNameNOUsed.do',$("#crmrelations-parentName-saleChance").val(),$("#saleChance_Sort-thisname").val(),'该名称已被使用,请另输入!');
    		$("#crmrelations-parent-saleChance").widget({
    			name:'t_base_crm_salechance_sort',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:false,
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
    					validLengthAndUsed(saleChance_lenArr[parseInt(data.level)], "basefiles/crmrelations/saleChanceNOUsed.do", $("#crmrelations-parentId-saleChance").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
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
    		$("#crmrelations-buttons-salesChanceSort").buttonWidget("setDataID", {id:$("#saleChance_Sort-oldid").val(), state:'${saleChance_Sort.state}', type:'edit'});
    		
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
			
    	});
    </script>
  </body>
</html>
