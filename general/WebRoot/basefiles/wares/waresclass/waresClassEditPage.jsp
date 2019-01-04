<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>商品分类修改页面</title>
  </head>
  
  <body>
   	<form action="" method="post" id="waresClass-form-edit">
    	<table cellpadding="2" cellspacing="2" border="0">
    		<tr>
    			<td>本级编码:</td>
    			<td><input id="waresClass-input-thisid" type="text" name="waresClass.thisid" value="<c:out value="${waresClass.thisid}"></c:out>" class="easyui-validatebox" <c:if test="${editFlag == false}">readonly="readonly"</c:if> onchange="getInputValue(this.value)" validType="validLen[${nextLen }]" required="true" style="width:200px;"/>
				    <input type="hidden" id="waresClass-pid" value="<c:out value="${waresClass.pid }"></c:out>"/>
				    <input type="hidden" id="waresClass-thisname" value="<c:out value="${waresClass.thisname }"></c:out>"/>
				    <input type="hidden" id="waresClass-hidden-hdpId" value="<c:out value="${waresClass.pid }"></c:out>"/>
				    <input type="hidden" id="waresClass-hidden-hdpName" value="<c:out value="${pname }"></c:out>"/>
				    <input type="hidden" id="waresClass-hidden-hdOldId" name="waresClass.oldId" value="<c:out value="${oldId }"></c:out>"/>
				    <input type="hidden" id="waresClass-hidden-hdLen" value="${nextLen }"/>
				    <input type="hidden" id="waresClass-hidden-hdType" value="edit"/>
				    <input type="hidden" id="waresClass-hidden-hdState" name="waresClass.state" value="${waresClass.state}"/>
    			</td>
    		</tr>
    		<tr>
    			<td>编码:</td>
    			<td><input type="text" id="waresClass-input-id" value="<c:out value="${waresClass.id}"></c:out>" readonly="readonly" name="waresClass.id" class="easyui-validatebox" required="true" validType="maxLen[20]" style="width:200px;"/></td>
    		</tr>
    		<tr>
    			<td>本级名称:</td>
    			<td><input type="text" id="waresClass-input-thisname" value="<c:out value="${waresClass.thisname}"></c:out>" name="waresClass.thisname" class="easyui-validatebox" onchange="getInputName(this.value)" required="true" validType="isRepeatName[20]" style="width:200px;"/></td>
    		</tr>
    		<tr>
    			<td>名称:</td>
    			<td><input type="text" id="waresClass-input-name" value="<c:out value="${waresClass.name}"></c:out>" name="waresClass.name" readonly="readonly" class="easyui-validatebox" required="true" validType="maxLen[50]" style="width:200px;"/></td>
    		</tr>
    		<tr>
    			<td>上级分类:</td>
    			<td>
    				<input id="waresClass-widget-pid" type="text" name="waresClass.pid"  <c:if test="${editFlag == false}">readonly="readonly"</c:if> value="<c:out value="${waresClass.pid}"></c:out>"/>
    			</td>
    		</tr>
    		<tr>
    			<td>备注:</td>
    			<td><textarea name="waresClass.remark" class="easyui-validatebox" validType="maxLen[200]" style="height:40px;width: 195px;overflow: hidden"><c:out value=""></c:out>${waresClass.remark}</textarea></td>
    		</tr>
    		<tr>
    			<td>状态:</td>
    			<td><input id="common-combobox-state" value="${waresClass.state }" disabled="disabled" class="easyui-combobox" style="width: 200px" /></td>
    		</tr>
    	</table>
    </form>
    <script type="text/javascript">
    	//状态
		$('#common-combobox-state').combobox({
		    url:'common/sysCodeList.do?type=state',   
		    valueField:'id',   
		    textField:'name'  
		});
		
		//根据输入本级编码的值获取显示编码值
		function getInputValue(val){
			if($("#waresClass-pid").val() == ""){
				$("#waresClass-input-id").val(val);
			}
			else{
				$("#waresClass-input-id").val($("#waresClass-hidden-hdpId").val()+val);
			}
		}
		
		//根据输入本级名称的值获取显示的名称值
		function getInputName(val){
			if(val == ""){
				$("#waresClass-input-name").val($("#waresClass-hidden-hdpName").val());
			}
			else{
				if($("#waresClass-hidden-hdpName").val() == ""){
					$("#waresClass-input-name").val(val);
				}
				else{
					$("#waresClass-input-name").val($("#waresClass-hidden-hdpName").val()+ "/" + val);
				}
			}
		}
		//暂存修改商品分类
		var waresClassTree=$.fn.zTree.getZTreeObj("waresClass-tree-waresClass");
		function editWaresClassHold(){
			$("#waresClass-hidden-hdState").val(3);
			var ret = waresClass_AjaxConn($("#waresClass-form-edit").serializeJSON(),'basefiles/editWaresClassHold.do','提交中..');
			var retJson = $.parseJSON(ret);
			if(retJson.flag){//修改更新子节点
				var id = $("#waresClass-input-thisid").val();
	  		    var pid = $("#waresClass-widget-pid").widget("getValue");
	  		    var treeObj = $.fn.zTree.getZTreeObj("waresClass-tree-waresClass");
	  		    //if("${editFlag}" == "true"){
	  		    //	var node = treeObj.getNodeByParam("id", pid, null);
		  		//    var cnode = treeObj.getNodeByParam("id", retJson.oldId, null);
		  		//    treeObj.removeNode(cnode); //删除子节点
	  		    //  	treeObj.addNodes(node, retJson.node); //增加子节点
		  		//    var snode = treeObj.getNodeByParam("id", retJson.node.id, null);
		  		//    treeObj.selectNode(snode, false); //选中节点
		  		//    //zTreeBeforeClick("finance-Tree-expensesSort", snode); //执行点击事件
	  		    //}
	  		   //else{
	  		    	var node = treeObj.getNodeByParam("id", retJson.oldId, null);
					node.id = retJson.node.id;
					node.name = retJson.node.name;
					node.pid = retJson.node.pid;
					node.state = retJson.node.state;
					treeObj.updateNode(node); //更新子节点
	  		   //}
				document.getElementById("waresClass-iframe").contentWindow.$("#waresClass-table-list").datagrid('reload');
				$("#waresClass-div-waresClassInfo").panel('refresh','basefiles/showWaresClassInfoPage.do?id='+$("#waresClass-input-id").val());
				$("#waresClass-operatree").attr("value","view");
				$.messager.alert("提醒","商品分类新增暂存成功!");
			}
			else{
				$.messager.alert("提醒",retJson.Mes);
			}
		}
		//保存商品分类
		function editWaresClassSave(){
			if(!$("#waresClass-form-edit").form('validate')){
				return false;
			}
			if("${waresClass.state}" == "1"){
				$("#waresClass-hidden-hdState").val(1);
			}
			else{
				$("#waresClass-hidden-hdState").val(2);
			}
			var ret = waresClass_AjaxConn($("#waresClass-form-edit").serializeJSON(),'basefiles/editWaresClassSave.do','提交中..');
			var retJson = $.parseJSON(ret);
			if(retJson.flag){
				//更新所有子节点
       			var map = retJson.nodes;
       			var treeObj = $.fn.zTree.getZTreeObj("waresClass-tree-waresClass");
       			for(var key in map){
       				var object = map[key];
       				var node = treeObj.getNodeByParam("id", key, null);
       				node.id = object.id;
					node.text = object.text;
					node.parentid = object.parentid;
					node.state = object.state;
					treeObj.updateNode(node);
       			}
				document.getElementById("waresClass-iframe").contentWindow.$("#waresClass-table-list").datagrid('reload');
                refreshLayout('商品分类【详情】','basefiles/showWaresClassInfoPage.do?id='+$("#waresClass-input-id").val());
				$("#waresClass-operatree").attr("value","view");
				$.messager.alert("提醒","商品分类修改保存成功!");
			}
			else{
				$.messager.alert("提醒",retJson.Mes);
			}
		}
		$(function(){
			//本级编号长度检验 
			$.extend($.fn.validatebox.defaults.rules, {
	   			validLen:{
	   				validator:function(value,param){
	   					var reg=eval("/^[A-Za-z0-9]{"+param[0]+"}$/");//正则表达式使用变量 
	   					if(reg.test(value)){
	   						if($("#waresClass-hidden-hdOldId").val() == $("#waresClass-input-id").val()){
	   							return true;
	   						}
	   						var ret=waresClass_AjaxConn({id:$("#waresClass-pid").val()+value},"basefiles/isRepeatWCID.do");//判断编码是否重复，true 不重复，false 重复
	   						var json = $.parseJSON(ret);
	   						if(json.flag){
	   							$("#waresClass-button-layoutTree").buttonWidget("enableMenuItem","button-hold");
	   							$("#waresClass-button-layoutTree").buttonWidget("enableMenuItem","button-save");
	   							return true;
	    					}else{
	    						$("#waresClass-button-layoutTree").buttonWidget("disableMenuItem","button-hold");
								$("#waresClass-button-layoutTree").buttonWidget("disableMenuItem","button-save");
	    						$.fn.validatebox.defaults.rules.validLen.message = '本级编码已存在,不能重复!';
	    						return false;
	    					}
	   					}else{
	   						$("#waresClass-button-layoutTree").buttonWidget("enableMenuItem","button-hold");
	   						$("#waresClass-button-layoutTree").buttonWidget("enableMenuItem","button-save");
	   						$.fn.validatebox.defaults.rules.validLen.message ='请输入${nextLen}位字符!';
	   						return false;
	   					}
	   				},
	   				message:''
	   			},
	   			isRepeatName:{
	   				validator:function(value,param){
	   					if(value.length <= param[0]){
	   						if($("#waresClass-thisname").val() == value){
	   							return true;
	   						}
	   						var ret = waresClass_AjaxConn({thisname:value},'basefiles/isRepeatThisName.do');
	   						var retJson = $.parseJSON(ret);
	   						if(!retJson.flag){
	   							$.fn.validatebox.defaults.rules.isRepeatName.message ='名称重复,请修改!';
	   						}
	   						else{return true;}
	   					}
	   					else{
	   						$.fn.validatebox.defaults.rules.isRepeatName.message ='请输入{0}个字符!';
	   					}
	   				},
	   				message:''
	   			}
	   		});
			//上级分类变动，编码检验
			$("#waresClass-widget-pid").widget({
				name:'t_base_goods_waresclass',
				col:'pid',
				singleSelect:true,
				onlyLeafCheck:false,
				onSelect:function(record){
					$("#waresClass-hidden-hdpId").val(record.id);
					$("#waresClass-hidden-hdpName").val(record.name);
					if($("#waresClass-input-thisname").val() == ""){
						$("#waresClass-input-name").val(record.name+"/"+record.thisname);
					}
					else{
						$("#waresClass-input-name").val(record.name + "/" + $("#waresClass-input-thisname").val());
					}
					$("#waresClass-input-id").val(record.id+$("#waresClass-input-thisid").val());
					var ret=waresClass_AjaxConn({pId:record.id},"basefiles/getNextWCLenght.do");
					var json = $.parseJSON(ret);
					if(json.nextLen == 0){
						$.messager.alert("提醒","已为最大级次,不允许新增!");
						$("#waresClass-button-layoutTree").buttonWidget("disableMenuItem","button-hold");
						$("#waresClass-button-layoutTree").buttonWidget("disableMenuItem","button-save");
						return false;
					}
					else{
						$("#waresClass-button-layoutTree").buttonWidget("enableMenuItem","button-hold");
						$("#waresClass-button-layoutTree").buttonWidget("enableMenuItem","button-save");
					}
		  			//本级编号长度检验 ,选择上级部门后 
					$.extend($.fn.validatebox.defaults.rules, {
		    			validLen:{
		    				validator:function(value){
		    					var reg=eval("/^[A-Za-z0-9]{"+json.nextLen+"}$/");//正则表达式使用变量 
		    					if(reg.test(value)){
		    						if($("#waresClass-hidden-hdOldId").val() == $("#waresClass-input-id").val()){
			   							return true;
			   						}
		    						var retStr=waresClass_AjaxConn({id:record.id+value},"basefiles/isRepeatWCID.do");//判断编码是否重复，true 不重复，false 重复
		  							var jsonRet = $.parseJSON(retStr);
		    						if(!jsonRet.flag){
		    							$.fn.validatebox.defaults.rules.validLen.message = '本级编码已存在,不能重复!';
			    					}
			    					else{return true;}
		    					}else{
		    						$.fn.validatebox.defaults.rules.validLen.message ='请输入'+json.nextLen+'位字符!';
		    					}
		    				},
		    				message:''
		    			}
		    		});
		  		},
		  		onLoadSuccess:function(){
		  			return true;
		  		}
			});
			
			$("#waresClass-button-layoutTree").buttonWidget("setDataID",{id:$("#waresClass-hidden-hdOldId").val(),state:"${waresClass.state}",type:"edit"});
		});
    </script>
  </body>
</html>
