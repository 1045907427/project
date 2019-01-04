<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>联系人分类</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
    <input type="hidden" id="contacter-thisId-contacterSort" />
  	<input type="hidden" id="contacter-parentId-contacterSort" />
  	<input type="hidden" id="contacter-isParent-contacterSort" />
  	<input type="hidden" id="contacter-state-contacterSort" />
  	<input type="hidden" id="contacter-level-contacterSort" />
  	<input type="hidden" id="contacter-hasLevel-contacterSort" value="${len }" />
  	<input type="hidden" id="contacter-leaveLen-contacterSort" value="${lenStr }" />
    <div class="easyui-layout" title="联系人类" data-options="fit:true" id="contacter-layout-contacterSort">
    	<div data-options="region:'north',border:false" style="height:30px;overflow: hidden;">
    		<div class="buttonBG" id="contacter-buttons-contacterSort"></div>
    	</div>
    	<div data-options="region:'west',border:false,split:true" title="联系人分类" style="width:200px;">
    		<div id="contacter-sortTree-contacterSort" class="ztree"></div>
    	</div>
    	<div data-options="region:'center',border:false" title="联系人分类">
    	
    	</div>
    </div>
    <script type="text/javascript">
    	var contacterSort_lenArr = $("#contacter-leaveLen-contacterSort").val().split(',');
    	$(function(){
    		//树型
			var contacterSortTreeSetting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "basefiles/getContacterSortTree.do",
					autoParam: ["id","pid", "name","state"]
				},
				data: {
					key:{
						title:"name"
					},
					simpleData: {
						enable:true,
						idKey: "id",
						pIdKey: "pid",
						rootPId: null
					}
				},
				callback: {
					//点击树状菜单更新页面按钮列表
					beforeClick: function(treeId, treeNode) {
						if(treeNode.id == ""){
							refreshLayout("联系人分类【新增】", 'basefiles/contacterSortAddPage.do');
						}
						else{
							refreshLayout("联系人分类【详情】",'basefiles/contacterSortViewPage.do?id='+ treeNode.id);
						}
						zTreeBeforeClick(treeId, treeNode);
						var zTree = $.fn.zTree.getZTreeObj("contacter-sortTree-contacterSort");
						if (treeNode.isParent) {
							if (treeNode.level == 0) {
								zTree.expandAll(false);
								zTree.expandNode(treeNode);
							} else {
								zTree.expandNode(treeNode);
							}
						}
						return true;
					}
				}
			};
			$.fn.zTree.init($("#contacter-sortTree-contacterSort"), contacterSortTreeSetting,null);
    		//按钮
			$("#contacter-buttons-contacterSort").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/basefiles/contacterSortAdd.do">
						{
							type: 'button-add',
							handler: function(){
								var thisId = $("#contacter-thisId-contacterSort").val();
								var hasLevel = $("#contacter-hasLevel-contacterSort").val();
								var level = $("#contacter-level-contacterSort").val();
								if(level == hasLevel){
									$.messager.alert("警告","该节点已为最大级次,不能再新增子节点!");
									return false;
								}
								refreshLayout("联系人分类【新增】",'basefiles/contacterSortAddPage.do?id='+ thisId);
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/contacterSortEdit.do">
						{
							type: "button-edit",
							handler: function(){
								var id = $("#contacter-thisId-contacterSort").val();
								if(id == ""){
									$.messager.alert("提醒","请选择一条需要修改的数据！");
									return false;
								}
								refreshLayout("联系人分类【修改】", "basefiles/contacterSortEditPage.do?id="+ id);
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/contacterSortHold.do">
						{
							type: 'button-hold',
							handler: function(){
								$.messager.confirm("提醒","确定暂存该联系人分类？",function(r){
									if(r){
										$("#contacter-addType-contacterSortAddPage").val("temp");
										contacterSort_tempSave_form_submit();
										$("#contacter-form-contacterSortAddPage").submit();
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/contacterSortHold.do">
						{
							type: "button-save",
							handler: function(){
								var flag = $("#contacter-form-contacterSortAddPage").form('validate');
					  		   	if(flag==false){
					  		   		return false;
					  		   	}
								$.messager.confirm("提醒","确定保存该联系人分类？",function(r){
									if(r){
										$("#contacter-addType-contacterSortAddPage").val("real");
										contacterSort_realSave_form_submit();
										$("#contacter-form-contacterSortAddPage").submit();
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/contacterSortDelete.do">
						{
							type: 'button-delete',
							handler: function(){
								var id = $("#contacter-thisId-contacterSort").val();
								var isParent = $("#contacter-isParent-contacterSort").val();
								if(isParent == "1"){
									$.messager.alert("提醒","先删除所有子节点后再删除该节点！");
									return false;
								}
								$.messager.confirm("提醒","是否删除该联系人分类信息?",function(r){
						  			if(r){
						  				loading("删除中..");
							  			$.ajax({
								  			url:'basefiles/deleteContacterSort.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.delFlag==true){
								  					$.messager.alert("提醒","该信息已被其他信息引用，无法删除！");
								  					return false;
								  				}
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","删除成功");
								  		        	var treeObj = $.fn.zTree.getZTreeObj("contacter-sortTree-contacterSort");
													var node = treeObj.getNodeByParam("id", id, null);
													treeObj.removeNode(node); //删除子节点
								  		        	var pid = $("#contacter-parentId-contacterSort").val();
								  		        	var snode = treeObj.getNodeByParam("id", pid, null);
										  		    treeObj.selectNode(snode, false); //选中节点
										  		    zTreeBeforeClick("contacter-sortTree-contacterSort", snode); //执行点击事件
								  		        	refreshLayout("客户分类【新增】","basefiles/contacterSortAddPage.do?id="+ pid);
								  		        }
								  		        else{
								  		        	$.messager.alert("提醒","删除失败");
								  		        }
								  			}
							  			});
						  			}
						  		});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/contacterSortDelete.do">
						{
							type: "button-open",
							handler: function(){
								var id = $("#contacter-thisId-contacterSort").val();
								var pid = $("#contacter-parentId-contacterSort").val();
								var treeObj = $.fn.zTree.getZTreeObj("contacter-sortTree-contacterSort");
								var node = treeObj.getNodeByParam("id", pid, null);
								if(node.state == "0"){
									$.messager.alert("提醒","上级节点为禁用状态，无法启用该节点！");
									return false;
								}
								$.messager.confirm("提醒","确定启用该联系人分类?",function(r){
						  			if(r){
						  				loading("启用中..");
							  			$.ajax({
								  			url:'basefiles/openContacterSort.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","启用成功");
								  		        	var treeObj = $.fn.zTree.getZTreeObj("contacter-sortTree-contacterSort");
													var node = treeObj.getNodeByParam("id", id, null);
													node.state = '1';
													treeObj.updateNode(node); //更新子节点
								  		        	var pid = $("#contacter-parentId-contacterSort").val();
								  		        	refreshLayout("联系人分类【详情】","basefiles/contacterSortViewPage.do?id="+ id);
								  		        }
								  		        else{
								  		        	$.messager.alert("提醒","启用失败");
								  		        }
								  			}
							  			});
						  			}
						  		});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/contacterSortDelete.do">
						{
							type: "button-close",
							handler: function(){
								var id = $("#contacter-thisId-contacterSort").val();
								$.messager.confirm("提醒","禁用该客户分类，将禁用该节点下所有联系人分类，是否禁用？",function(r){
						  			if(r){
						  				loading("禁用中..");
							  			$.ajax({
								  			url:'basefiles/closeContacterSort.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  		        $.messager.alert("提醒","禁用成功数："+ json.successNum + "<br />禁用失败数："+ json.failureNum + "<br />不可禁用数："+ json.notAllowNum);
								  		        var treeObj = $.fn.zTree.getZTreeObj("contacter-sortTree-contacterSort");
												for(var i=0;i<json.ids.length;i++){ //所有禁用成功的节点都需要更新状态
													var node = treeObj.getNodeByParam("id", json.ids[i], null);
													node.state = '0';
													treeObj.updateNode(node); //更新子节点
												}
								  		        refreshLayout("客户分类【详情】","basefiles/contacterSortViewPage.do?id="+ id);
								  			}
							  			});
						  			}
						  		});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/contacterSortPreview.do">
						{
							type: "button-preview",
							handler: function(){
								
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/contacterSortPrint.do">
						{
							type: "button-print",
							handler: function(){
								
							}
						},
					</security:authorize>
					{}
				],
				model: 'base',
				type: 'list',
				tname: 't_base_linkman_sort'
			});
    	});
    	function contacterSort_tempSave_form_submit(){
    		$("#contacter-form-contacterSortAddPage").form({
			    onSubmit: function(){  
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		    if(json.flag==true){
		  		      	$.messager.alert("提醒","暂存成功");
		  		      	var id = $("#contacter-thisId-contacterSort").val();
		  		      	var pid = $("#contacter-parentId-contacterSort").val();
		  		      	if(json.type == "add"){ //新增增加子节点
			  		      	var treeObj = $.fn.zTree.getZTreeObj("contacter-sortTree-contacterSort");
			  		      	var node = treeObj.getNodeByParam("id", id, null);
			  		      	treeObj.addNodes(node, json.node); //增加子节点
			  		      	var snode = treeObj.getNodeByParam("id", json.node.id, null);
			  		      	treeObj.selectNode(snode, false); //选中节点
			  		      	zTreeBeforeClick("contacter-sortTree-contacterSort", snode); //执行点击事件
		  		      	}
		  		      	if(json.type == "edit"){ //修改更新子节点
		  		      		var editType = $("#sales-editType-contacterSortAddPage").val();
		  		      		var treeObj = $.fn.zTree.getZTreeObj("contacter-sortTree-contacterSort");
		  		      		if(editType == "true"){
				  		      	var node = treeObj.getNodeByParam("id", pid, null);
				  		      	var cnode = treeObj.getNodeByParam("id", json.oldid, null);
								treeObj.removeNode(cnode); //删除子节点
				  		      	treeObj.addNodes(node, json.node); //增加子节点
				  		      	var snode = treeObj.getNodeByParam("id", json.node.id, null);
			  		      		treeObj.selectNode(snode, false); //选中节点
			  		      		zTreeBeforeClick("contacter-sortTree-contacterSort", snode); //执行点击事件
			  		      	}else{
				  		      	var node = treeObj.getNodeByParam("id", json.oldid, null);
								node.name = json.node.name;
								node.pid = json.node.pid;
								node.state = json.node.state;
								treeObj.updateNode(node); //更新子节点
							}
		  		      	}
		  		      	refreshLayout("联系人分类【详情】", "basefiles/contacterSortViewPage.do?id="+ json.backid);
		  		    }
		  		    else{
		  		       	$.messager.alert("提醒","暂存失败");
		  		    }
		  		}
		  	});
    	}
    	function contacterSort_realSave_form_submit(){
    		$("#contacter-form-contacterSortAddPage").form({
			    onSubmit: function(){  
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		    if(json.flag==true){
		  		      	$.messager.alert("提醒","保存成功");
		  		      	var id = $("#contacter-thisId-contacterSort").val();
		  		      	var pid = $("#contacter-parentId-contacterSort").val();
		  		      	if(json.type == "add"){ //新增增加子节点
			  		      	var treeObj = $.fn.zTree.getZTreeObj("contacter-sortTree-contacterSort");
			  		      	var node = treeObj.getNodeByParam("id", id, null);
			  		      	treeObj.addNodes(node, json.node); //增加子节点
				  		    var snode = treeObj.getNodeByParam("id", json.node.id, null);
			  		      	treeObj.selectNode(snode, false); //选中节点
			  		      	zTreeBeforeClick("contacter-sortTree-contacterSort", snode); //执行点击事件
		  		      	}
		  		      	if(json.type == "edit"){ //修改更新子节点
			  		      	var map = json.nodes;
			       			var treeObj = $.fn.zTree.getZTreeObj("contacter-sortTree-contacterSort");
			       			for(var key in map){
			       				var object = map[key];
			       				var node = treeObj.getNodeByParam("id", key, null);
			       				node.id = object.id;
								node.name = object.text;
								node.parentid = object.parentid;
								node.state = object.state;
								treeObj.updateNode(node);
			       			}
			  		      	
			  		      	/*var editType = $("#sales-editType-contacterSortAddPage").val();
		  		      		var treeObj = $.fn.zTree.getZTreeObj("contacter-sortTree-contacterSort");
		  		      		if(editType == "true"){
				  		      	var node = treeObj.getNodeByParam("id", pid, null);
				  		      	var cnode = treeObj.getNodeByParam("id", json.oldid, null);
								treeObj.removeNode(cnode); //删除子节点
				  		      	treeObj.addNodes(node, json.node); //增加子节点
				  		      	var snode = treeObj.getNodeByParam("id", json.node.id, null);
			  		      		treeObj.selectNode(snode, false); //选中节点
			  		      		zTreeBeforeClick("contacter-sortTree-contacterSort", snode); //执行点击事件
			  		      	}else{
				  		      	var node = treeObj.getNodeByParam("id", json.oldid, null);
								node.name = json.node.name;
								node.pid = json.node.pid;
								node.state = json.node.state;
								treeObj.updateNode(node); //更新子节点
							}*/
		  		      	}
		  		      	refreshLayout("分类【详情】", "basefiles/contacterSortViewPage.do?id="+ json.backid);
		  		    }
		  		    else{
		  		       	$.messager.alert("提醒","保存失败");
		  		    }
		  		}
		  	});
    	}
    	function refreshLayout(title, url){
    		$("#contacter-layout-contacterSort").layout('remove','center').layout('add',{
				region: 'center',  
			    title: title,
			    href:url
			});
    	}
    	var customerSort_ajaxContent = function (param, url) { //同步ajax
		    var ajax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: url,
		        data: param,
		        async: false
		    });
		    return ajax.responseText;
		}
    	function validLength(len){ //只验证长度
    		$.extend($.fn.validatebox.defaults.rules, {
				validLength:{
			    	validator:function(value){
			    		var reg=eval("/^[A-Za-z0-9]{"+len+"}$/");//正则表达式使用变量
			    		return reg.test(value);
			    	},
			    	message:'请输入'+len+'位字符!'
			    }
			});
    	}
    	//验证长度且验证重复
    	function validLengthAndUsed(len, url, id, initValue, message){ //initValue：修改的时候有初始值，判断是否为初始值，是不进行重复验证，否则修改的时候会提醒初始值重复，这里是不需要验证的。
    		$.extend($.fn.validatebox.defaults.rules, {
				validLength:{
			    	validator:function(value){
			    		var reg=eval("/^[A-Za-z0-9]{"+len+"}$/");//正则表达式使用变量 
	  					if(reg.test(value) == true){
	  						if(value == initValue){
	  							return true;
	  						}
				    		var data=customerSort_ajaxContent({id:(id+value)},url);
		  					var json = $.parseJSON(data);
	    					if(json.flag == true){
		    					$.fn.validatebox.defaults.rules.validLength.message = message;
		    					return false;
		    				}else{
	    						return true;
		    				}
	    				}else{
	    					$.fn.validatebox.defaults.rules.validLength.message ='请输入'+len+'位字符!';
	    					return false;
	    				}
			    	},
			    	message:''
			    }
			});
    	}
    	function zTreeBeforeClick(treeId, treeNode){
    		$("#contacter-thisId-contacterSort").val(treeNode.id);
			$("#contacter-parentId-contacterSort").val(treeNode.pid);
			$("#contacter-state-contacterSort").val(treeNode.state);
			$("#contacter-level-contacterSort").val(treeNode.level);
			if (treeNode.isParent) {
				$("#contacter-isParent-contacterSort").val("1")
			} else {
				$("#contacter-isParent-contacterSort").val("0")
			}
    	}
    </script>
  </body>
</html>
