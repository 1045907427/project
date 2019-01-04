<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购区域</title>
    <%@include file="/include.jsp" %>   
  </head>
  <body>
  	<input type="hidden" id="buy-thisId-buyArea" />
  	<input type="hidden" id="buy-parentId-buyArea" />
  	<input type="hidden" id="buy-isParent-buyArea" />
  	<input type="hidden" id="buy-state-buyArea" />
  	<input type="hidden" id="buy-hasLevel-buyArea" value="${len }" />
  	<input type="hidden" id="buy-leaveLen-buyArea" value="${lenStr }" />
    <div class="easyui-layout" title="采购区域" data-options="fit:true" id="buy-layout-buyArea">
    	<div data-options="region:'north',border:false" style="height: 30px;overflow: hidden">
    		<div class="buttonBG" id="buy-buttons-buyArea"></div>
    	</div>
    	<div data-options="region:'west',split:true" title="采购区域" style="width:200px;">
    		<div id="buy-areaTree-buyArea" class="ztree"></div>
    	</div>
    	<div data-options="region:'center',border:true" title="">
    	
    	</div>
    </div>
    <script type="text/javascript">
    	var buyArea_lenArr = $("#buy-leaveLen-buyArea").val().split(',');
    	$(function(){
    		//树型
			var buyAreaTreeSetting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "basefiles/getBuyAreaTree.do",
					autoParam: ["id","pId", "name","state"]
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
						$("#buy-thisId-buyArea").val(treeNode.id);
						$("#buy-parentId-buyArea").val(treeNode.pid);
						$("#buy-state-buyArea").val(treeNode.state);
						if(treeNode.id == ""){
							$("#buy-layout-buyArea").layout('remove','center').layout('add',{
								region: 'center',  
							    title: "采购区域【新增】",
							    href:'basefiles/buyAreaAddPage.do'
							});
						}
						else{
							$("#buy-layout-buyArea").layout('remove','center').layout('add',{
								region: 'center',  
							    title: "采购区域【查看】",
							    href:'basefiles/buyAreaViewPage.do?id='+ treeNode.id
							});
						}
						if (treeNode.isParent) {
							$("#buy-isParent-buyArea").val("1")
						} else {
							$("#buy-isParent-buyArea").val("0")
						}
						$("#buy-buttons-buyArea").buttonWidget("setDataID", {id:treeNode.id, state:treeNode.state});
						var zTree = $.fn.zTree.getZTreeObj("buy-areaTree-buyArea");
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
			$.fn.zTree.init($("#buy-areaTree-buyArea"), buyAreaTreeSetting,null);
			//按钮
			$("#buy-buttons-buyArea").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/basefiles/buyAreaAddPage.do">
						{
							type: 'button-add',
							handler: function(){
								var thisId = $("#buy-thisId-buyArea").val();
								$("#buy-layout-buyArea").layout('remove','center').layout('add',{
									region: 'center',  
								    title: "采购区域【新增】",
								    href:'basefiles/buyAreaAddPage.do?id='+ thisId
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buyAreaEditPage.do">
						{
							type: "button-edit",
							handler: function(){
								var id = $("#buy-thisId-buyArea").val();
								if(id == ""){
									$.messager.alert("提醒","请选择一条需要修改的数据！");
									return false;
								}
								refreshLayout("采购区域【修改】", "basefiles/buyAreaEditPage.do?id="+ id);
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buyAreaHoldPage.do">
						{
							type: 'button-hold',
							handler: function(){
								$.messager.confirm("提醒","确定添加该采购区域？",function(r){
									if(r){
										$("#buy-addType-buyAreaAddPage").val("temp");
										buyArea_tempSave_form_submit();
										$("#buy-form-buyAreaAddPage").submit();
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buyAreaSavePage.do">
						{
							type: "button-save",
							handler: function(){
								var flag = $("#buy-form-buyAreaAddPage").form('validate');
					  		   	if(flag==false){
					  		   		return false;
					  		   	}
								$.messager.confirm("提醒","确定添加该采购区域？",function(r){
									if(r){
										$("#buy-addType-buyAreaAddPage").val("real");
										buyArea_realSave_form_submit();
										$("#buy-form-buyAreaAddPage").submit();
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buyAreaDeletePage.do">
						{
							type: 'button-delete',
							handler: function(){
								var id = $("#buy-thisId-buyArea").val();
                                var isParent = $("#buy-isParent-buyArea").val();
                                if(isParent == "1"){
                                    $.messager.alert("提醒","先删除所有子节点后再删除该节点！");
                                    return false;
                                }
								$.messager.confirm("提醒","是否删除该采购区域信息?",function(r){
						  			if(r){
							  			$.ajax({
								  			url:'basefiles/deleteBuyArea.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
							  				if(json.delFlag==true){
							  					$.messager.alert("提醒","该信息已被其他信息引用，无法删除！");
							  					return false;
							  				}
							  				if(json.flag==true){
							  		        	$.messager.alert("提醒","删除成功");
							  		        	var treeObj = $.fn.zTree.getZTreeObj("buy-areaTree-buyArea");
												var node = treeObj.getNodeByParam("id", id, null);
												treeObj.removeNode(node); //删除子节点
							  		        	var pid = $("#buy-parentId-buyArea").val();
							  		        	var snode = treeObj.getNodeByParam("id", pid, null);
							  		        	if(null != snode){
							  		        		treeObj.selectNode(snode, false); //选中节点
									  		    	zTreeBeforeClick("buy-areaTree-buyArea", snode); //执行点击事件
                                                    if(snode.id != "" && null != snode.id){
                                                        refreshLayout("采购区域【详情】","basefiles/buyAreaViewPage.do?id="+ snode.id);
                                                    }else{
                                                        refreshLayout("采购区域【新增】","basefiles/buyAreaAddPage.do?id="+ pid);
                                                    }
							  		        	}
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
					<security:authorize url="/basefiles/buyAreaCopyPage.do">
						{
							type: "button-copy",
							handler: function(){
								var id = $("#buy-thisId-buyArea").val();
								refreshLayout("采购区域【新增】", "basefiles/buyAreaCopyPage.do?id="+ id);
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buyAreaOpenPage.do">
						{
							type: "button-open",
							handler: function(){
								var id = $("#buy-thisId-buyArea").val();
								var pid = $("#buy-parentId-buyArea").val();
								var treeObj = $.fn.zTree.getZTreeObj("buy-areaTree-buyArea");
								var node = treeObj.getNodeByParam("id", pid, null);
								if(node.state == "0"){
									$.messager.alert("提醒","上级节点为禁用状态，无法启用该节点！");
									return false;
								}
								$.messager.confirm("提醒","是否启用该采购区域信息?",function(r){
						  			if(r){
							  			$.ajax({
								  			url:'basefiles/openBuyArea.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","启用成功");
								  		        	var treeObj = $.fn.zTree.getZTreeObj("buy-areaTree-buyArea");
													var node = treeObj.getNodeByParam("id", id, null);
													node.state = '1';
													treeObj.updateNode(node); //更新子节点
								  		        	var pid = $("#buy-parentId-buyArea").val();
								  		        	refreshLayout("采购区域【查看】","basefiles/buyAreaViewPage.do?id="+ id);
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
					<security:authorize url="/basefiles/buyAreaClosePage.do">
						{
							type: "button-close",
							handler: function(){
								var id = $("#buy-thisId-buyArea").val();
								$.messager.confirm("提醒","是否禁用该采购区域信息?",function(r){
						  			if(r){
							  			$.ajax({
								  			url:'basefiles/closeBuyArea.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				if(json.flag==true){
								  					$.messager.alert("提醒", "禁用成功数："+ json.isuccess +"<br />禁用失败数："+ json.ifailure + "<br />不允许禁用数："+ json.inohandle);	
								  		        	var treeObj = $.fn.zTree.getZTreeObj("buy-areaTree-buyArea");
													var node = treeObj.getNodeByParam("id", id, null);
													node.state = '0';
													treeObj.updateNode(node); //更新子节点
								  		        	var pid = $("#buy-parentId-buyArea").val();
								  		        	refreshLayout("采购区域【查看】","basefiles/buyAreaViewPage.do?id="+ id);
								  		        }
								  		        else{
								  		        	$.messager.alert("提醒","禁用失败");
								  		        }
								  			}
							  			});
						  			}
						  		});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buyAreaImport.do">
						{
                            type:'button-import',//导入
                            attr:{
                                clazz: "buyService",
                                method: "addDRBuyAreaExcel",
                                tn: "t_base_buy_area",
                                module: 'basefiles',
                                pojo: "BuyArea"
                            }
						},
					</security:authorize>
					<security:authorize url="/basefiles/buyAreaExport.do">
						{
                            type:'button-export',//导出
                            attr:{
                                tn: 't_base_buy_area',//表名
                                name:'采购区域列表'
                            }
						},
					</security:authorize>
					{}
				],
				model: 'base',
				type: 'list',
				tname: 't_base_buy_area'
			});
    	});
    	function buyArea_tempSave_form_submit(){
    		$("#buy-form-buyAreaAddPage").form({
			    onSubmit: function(){  
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		    if(json.flag==true){
		  		      	$.messager.alert("提醒","暂存成功");
		  		      	var id = $("#buy-thisId-buyArea").val();
		  		      	var pid = $("#buy-parentId-buyArea").val();
		  		      	if(json.type == "add"){ //新增增加子节点
			  		      	var treeObj = $.fn.zTree.getZTreeObj("buy-areaTree-buyArea");
			  		      	var node = treeObj.getNodeByParam("id", id, null);
			  		      	treeObj.addNodes(node, json.node); //增加子节点
			  		      	var snode = treeObj.getNodeByParam("id", json.node.id, null);
			  		      	treeObj.selectNode(snode, false); //选中节点
			  		      	zTreeBeforeClick("buy-areaTree-buyArea", snode); //执行点击事件
		  		      	}
		  		      	if(json.type == "edit"){ //修改更新子节点
		  		      		var editType = $("#buy-editType-buyAreaAddPage").val();
		  		      		var treeObj = $.fn.zTree.getZTreeObj("buy-areaTree-buyArea");
		  		      		if(editType == "true"){
				  		      	var node = treeObj.getNodeByParam("id", pid, null);
				  		      	var cnode = treeObj.getNodeByParam("id", json.oldid, null);
								treeObj.removeNode(cnode); //删除子节点
				  		      	treeObj.addNodes(node, json.node); //增加子节点
				  		      	var snode = treeObj.getNodeByParam("id", json.node.id, null);
				  		      	treeObj.selectNode(snode, false); //选中节点
				  		      	zTreeBeforeClick("buy-areaTree-buyArea", snode); //执行点击事件
			  		      	}else{
				  		      	var node = treeObj.getNodeByParam("id", json.oldid, null);
								node.name = json.node.name;
								node.pid = json.node.pid;
								node.state = json.node.state;
								treeObj.updateNode(node); //更新子节点
							}
		  		      	}
		  		      	refreshLayout("采购区域【查看】", "basefiles/buyAreaViewPage.do?id="+ json.backid);
		  		    }
		  		    else{
		  		       	$.messager.alert("提醒","暂存失败");
		  		    }
		  		}
		  	});
    	}
    	function buyArea_realSave_form_submit(){
    		$("#buy-form-buyAreaAddPage").form({
			    onSubmit: function(){  
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		    if(json.flag==true){
		  		      	$.messager.alert("提醒","保存成功");
		  		      	var id = $("#buy-thisId-buyArea").val();
		  		      	var pid = $("#buy-parentId-buyArea").val();
		  		      	if(json.type == "add"){ //新增增加子节点
			  		      	var treeObj = $.fn.zTree.getZTreeObj("buy-areaTree-buyArea");
			  		      	var node = treeObj.getNodeByParam("id", id, null);
			  		      	treeObj.addNodes(node, json.node); //增加子节点
			  		      	var snode = treeObj.getNodeByParam("id", json.node.id, null);
			  		      	treeObj.selectNode(snode, false); //选中节点
			  		      	zTreeBeforeClick("buy-areaTree-buyArea", snode); //执行点击事件
		  		      	}
		  		      	if(json.type == "edit"){ //修改更新子节点
		  		      		var map = json.nodes;
			       			var treeObj = $.fn.zTree.getZTreeObj("buy-areaTree-buyArea");
			       			for(var key in map){
			       				var object = map[key];
			       				var node = treeObj.getNodeByParam("id", key, null);
			       				node.id = object.id;
								node.name = object.text;
								node.parentid = object.parentid;
								node.state = object.state;
								treeObj.updateNode(node);
			       			}
		  		      	}
		  		      	refreshLayout("采购区域【查看】", "basefiles/buyAreaViewPage.do?id="+ json.backid);
		  		    }
		  		    else{
		  		       	$.messager.alert("提醒","保存失败");
		  		    }
		  		}
		  	});
    	}
    	function refreshLayout(title, url){
    		$("#buy-layout-buyArea").layout('remove','center').layout('add',{
				region: 'center',  
			    title: title,
			    href:url
			});
    	}
    	var buyArea_ajaxContent = function (param, url) { //同步ajax
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
				    		var data=buyArea_ajaxContent({id:(id+value)},url);
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
    	function validUsed(url, name, message){
    		$.extend($.fn.validatebox.defaults.rules, {
				validUsed:{
			    	validator:function(value){
			    		var data=salesArea_ajaxContent({name: (name + '/' + value)},url);
	  					var json = $.parseJSON(data);
	    				if(json.flag == true){
		   					$.fn.validatebox.defaults.rules.remote.message = message;
		    				return false;
		    			}else{
	    					return true;
		    			}
			    	},
			    	message:''
			    }
			});
    	}
    	function zTreeBeforeClick(treeId, treeNode){
    		$("#buy-thisId-buyArea").val(treeNode.id);
			$("#buy-parentId-buyArea").val(treeNode.pid);
			$("#buy-state-buyArea").val(treeNode.state);
			$("#buy-level-buyArea").val(treeNode.level);
			if (treeNode.isParent) {
				$("#buy-isParent-buyArea").val("1")
			} else {
				$("#buy-isParent-buyArea").val("0")
			}
			return true;
    	}
    </script>
  </body>
</html>
