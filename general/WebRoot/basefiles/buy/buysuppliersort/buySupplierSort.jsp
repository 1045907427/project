<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>供应商分类</title>
    <%@include file="/include.jsp" %>   
  </head>
  <body>
  	<input type="hidden" id="buy-thisId-buySupplierSort" />
  	<input type="hidden" id="buy-parentId-buySupplierSort" />
  	<input type="hidden" id="buy-isParent-buySupplierSort" />
  	<input type="hidden" id="buy-state-buySupplierSort" />
  	<input type="hidden" id="buy-hasLevel-buySupplierSort" value="${len }" />
  	<input type="hidden" id="buy-leaveLen-buySupplierSort" value="${lenStr }" />
    <div class="easyui-layout" title="供应商分类" data-options="fit:true" id="buy-layout-buySupplierSort">
    	<div data-options="region:'north',border:false" style="height:30px;overflow: hidden">
    		<div class="buttonBG" id="buy-buttons-buySupplierSort"></div>
    	</div>
    	<div data-options="region:'west',split:true" title="供应商分类" style="width:200px;">
    		<div id="buy-sortTree-buySupplierSort" class="ztree"></div>
    	</div>
    	<div data-options="region:'center',border:true" title="">
    	
    	</div>
    </div>
    <script type="text/javascript">
		var buySupplierSort_lenArr = $("#buy-leaveLen-buySupplierSort").val().split(',');
    	$(function(){
    		//树型
			var buySupplierSortTreeSetting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "basefiles/getBuySupplierSortTree.do",
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
						$("#buy-thisId-buySupplierSort").val(treeNode.id);
						$("#buy-parentId-buySupplierSort").val(treeNode.pid);
						$("#buy-state-buySupplierSort").val(treeNode.state);
						if(treeNode.id == ""){
							$("#buy-layout-buySupplierSort").layout('remove','center').layout('add',{
								region: 'center',  
							    title: "供应商分类【新增】",
							    href:'basefiles/buySupplierSortAddPage.do'
							});
						}
						else{
							$("#buy-layout-buySupplierSort").layout('remove','center').layout('add',{
								region: 'center',  
							    title: "供应商分类【详情】",
							    href:'basefiles/buySupplierSortViewPage.do?id='+ treeNode.id
							});
						}
						if (treeNode.isParent) {
							$("#buy-isParent-buySupplierSort").val("1")
						} else {
							$("#buy-isParent-buySupplierSort").val("0")
						}
						$("#buy-buttons-buySupplierSort").buttonWidget("setDataID", {id:treeNode.id, state:treeNode.state});
						var zTree = $.fn.zTree.getZTreeObj("buy-sortTree-buySupplierSort");
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
			$.fn.zTree.init($("#buy-sortTree-buySupplierSort"), buySupplierSortTreeSetting,null);
			//按钮
			$("#buy-buttons-buySupplierSort").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/basefiles/buySupplierSortAddPage.do">
						{
							type: 'button-add',
							handler: function(){
								var thisId = $("#buy-thisId-buySupplierSort").val();
								$("#buy-layout-buySupplierSort").layout('remove','center').layout('add',{
									region: 'center',  
								    title: "供应商分类【新增】",
								    href:'basefiles/buySupplierSortAddPage.do?id='+ thisId
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buySupplierSortEditPage.do">
						{
							type: "button-edit",
							handler: function(){
								var id = $("#buy-thisId-buySupplierSort").val();
								if(id == ""){
									$.messager.alert("提醒","请选择一条需要修改的数据！");
									return false;
								}
								refreshLayout("供应商分类【修改】", "basefiles/buySupplierSortEditPage.do?id="+ id);
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buySupplierSortHoldPage.do">
						{
							type: 'button-hold',
							handler: function(){
								$.messager.confirm("提醒","确定添加该供应商分类？",function(r){
									if(r){
										$("#buy-addType-buySupplierSortAddPage").val("temp");
										buySupplierSort_tempSave_form_submit();
										$("#buy-form-buySupplierSortAddPage").submit();
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buySupplierSortSavePage.do">
						{
							type: "button-save",
							handler: function(){
								var flag = $("#buy-form-buySupplierSortAddPage").form('validate');
					  		   	if(flag==false){
					  		   		return false;
					  		   	}
								$.messager.confirm("提醒","确定添加该供应商分类？",function(r){
									if(r){
										$("#buy-addType-buySupplierSortAddPage").val("real");
										buySupplierSort_realSave_form_submit();
										$("#buy-form-buySupplierSortAddPage").submit();
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buySupplierSortDeletePage.do">
						{
							type: 'button-delete',
							handler: function(){
								var id = $("#buy-thisId-buySupplierSort").val();
                                var isParent = $("#buy-isParent-buySupplierSort").val();
                                if(isParent == "1"){
                                    $.messager.alert("提醒","先删除所有子节点后再删除该节点！");
                                    return false;
                                }
								$.messager.confirm("提醒","是否删除该供应商分类信息?",function(r){
						  			if(r){
						  				loading("删除中..");
							  			$.ajax({
								  			url:'basefiles/deleteBuySupplierSort.do',
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
								  		        	var treeObj = $.fn.zTree.getZTreeObj("buy-sortTree-buySupplierSort");
													var node = treeObj.getNodeByParam("id", id, null);
													treeObj.removeNode(node); //删除子节点
								  		        	var pid = $("#buy-parentId-buySupplierSort").val();
                                                    var snode = treeObj.getNodeByParam("id", pid, null);
                                                    treeObj.selectNode(snode, false); //选中节点
                                                    zTreeBeforeClick("buy-sortTree-buySupplierSort", snode); //执行点击事件
                                                    if(snode.id != "" && null != snode.id){
                                                        refreshLayout("供应商分类【详情】","basefiles/buySupplierSortViewPage.do?id="+ snode.id);
                                                    }else{
                                                        refreshLayout("供应商分类【新增】","basefiles/buySupplierSortAddPage.do?id="+ pid);
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
					<security:authorize url="/basefiles/buySupplierSortCopyPage.do">
						{
							type: "button-copy",
							handler: function(){
								var id = $("#buy-thisId-buySupplierSort").val();
								refreshLayout("供应商分类【新增】", "basefiles/buySupplierSortCopyPage.do?id="+ id);
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buySupplierSortOpenPage.do">
						{
							type: "button-open",
							handler: function(){
								var id = $("#buy-thisId-buySupplierSort").val();
								var pid = $("#buy-parentId-buySupplierSort").val();
								var treeObj = $.fn.zTree.getZTreeObj("buy-sortTree-buySupplierSort");
								var node = treeObj.getNodeByParam("id", pid, null);
								if(node.state == "0"){
									$.messager.alert("提醒","上级节点为禁用状态，无法启用该节点！");
									return false;
								}
								$.messager.confirm("提醒","是否启用该供应商分类信息?",function(r){
						  			if(r){
						  				loading("启用中..");
							  			$.ajax({
								  			url:'basefiles/openBuySupplierSort.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","启用成功");
								  		        	var treeObj = $.fn.zTree.getZTreeObj("buy-sortTree-buySupplierSort");
													var node = treeObj.getNodeByParam("id", id, null);
													node.state = '1';
													treeObj.updateNode(node); //更新子节点
								  		        	var pid = $("#buy-parentId-buySupplierSort").val();
								  		        	refreshLayout("供应商分类【详情】","basefiles/buySupplierSortViewPage.do?id="+ id);
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
					<security:authorize url="/basefiles/buySupplierSortClosePage.do">
						{
							type: "button-close",
							handler: function(){
								var id = $("#buy-thisId-buySupplierSort").val();
								$.messager.confirm("提醒","是否禁用该供应商分类信息?",function(r){
						  			if(r){
						  				loading("禁用中..");
							  			$.ajax({
								  			url:'basefiles/closeBuySupplierSort.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.flag==true){
								  					$.messager.alert("提醒", "禁用成功数："+ json.isuccess +"<br />禁用失败数："+ json.ifailure + "<br />不允许禁用数："+ json.inohandle);
								  		        	var treeObj = $.fn.zTree.getZTreeObj("buy-sortTree-buySupplierSort");
													var node = treeObj.getNodeByParam("id", id, null);
													node.state = '0';
													treeObj.updateNode(node); //更新子节点
								  		        	var pid = $("#buy-parentId-buySupplierSort").val();
								  		        	refreshLayout("供应商分类【详情】","basefiles/buySupplierSortViewPage.do?id="+ id);
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
					<security:authorize url="/basefiles/buySupplierSortImport.do">
						{
                            type:'button-import',//导入
                            attr:{
                                clazz: "buyService",
                                method: "addDRSupplierSortExcel",
                                tn: "t_base_buy_supplier_sort",
                                module: 'basefiles',
                                pojo: "BuySupplierSort"
                            }
						},
					</security:authorize>
					<security:authorize url="/basefiles/buySupplierSortExport.do">
						{
                            type:'button-export',//导出
                            attr:{
                                tn: 't_base_buy_supplier_sort',//表名
                                name:'供应商分类列表'
                            }
						},
					</security:authorize>
					{}
				],
				model: 'base',
				type: 'list',
				tname: 't_base_buy_supplier_sort'
			});
    	});
    	function buySupplierSort_tempSave_form_submit(){
    		$("#buy-form-buySupplierSortAddPage").form({
			    onSubmit: function(){
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		    if(json.flag==true){
		  		      	$.messager.alert("提醒","暂存成功");
		  		      	var id = $("#buy-thisId-buySupplierSort").val();
		  		      	var pid = $("#buy-parentId-buySupplierSort").val();
		  		      	if(json.type == "add"){ //新增增加子节点
			  		      	var treeObj = $.fn.zTree.getZTreeObj("buy-sortTree-buySupplierSort");
			  		      	var node = treeObj.getNodeByParam("id", id, null);
			  		      	treeObj.addNodes(node, json.node); //增加子节点
			  		      	var snode = treeObj.getNodeByParam("id", json.node.id, null);
			  		      	treeObj.selectNode(snode, false); //选中节点
			  		      	zTreeBeforeClick("buy-sortTree-buySupplierSort", snode); //执行点击事件
		  		      	}
		  		      	if(json.type == "edit"){ //修改更新子节点
		  		      		var editType = $("#buy-editType-buySupplierSortAddPage").val();
		  		      		var treeObj = $.fn.zTree.getZTreeObj("buy-sortTree-buySupplierSort");
		  		      		if(editType == "true"){
				  		      	var node = treeObj.getNodeByParam("id", pid, null);
				  		      	var cnode = treeObj.getNodeByParam("id", json.oldid, null);
								treeObj.removeNode(cnode); //删除子节点
				  		      	treeObj.addNodes(node, json.node); //增加子节点
				  		      	var snode = treeObj.getNodeByParam("id", json.node.id, null);
				  		      	treeObj.selectNode(snode, false); //选中节点
				  		      	zTreeBeforeClick("buy-sortTree-buySupplierSort", snode); //执行点击事件
			  		      	}else{
				  		      	var node = treeObj.getNodeByParam("id", json.oldid, null);
								node.name = json.node.name;
								node.pid = json.node.pid;
								node.state = json.node.state;
								treeObj.updateNode(node); //更新子节点
							}
		  		      	}
		  		      	refreshLayout("供应商分类【详情】", "basefiles/buySupplierSortViewPage.do?id="+ json.backid);
		  		    }
		  		    else{
		  		       	$.messager.alert("提醒","暂存失败");
		  		    }
		  		}
		  	});
    	}
    	function buySupplierSort_realSave_form_submit(){
    		$("#buy-form-buySupplierSortAddPage").form({
			    onSubmit: function(){
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		    if(json.flag==true){
		  		      	$.messager.alert("提醒","保存成功");
		  		      	var id = $("#buy-thisId-buySupplierSort").val();
		  		      	var pid = $("#buy-parentId-buySupplierSort").val();
		  		      	if(json.type == "add"){ //新增增加子节点
			  		      	var treeObj = $.fn.zTree.getZTreeObj("buy-sortTree-buySupplierSort");
			  		      	var node = treeObj.getNodeByParam("id", id, null);
			  		      	treeObj.addNodes(node, json.node); //增加子节点
			  		      	var snode = treeObj.getNodeByParam("id", json.node.id, null);
			  		      	treeObj.selectNode(snode, false); //选中节点
			  		      	zTreeBeforeClick("buy-sortTree-buySupplierSort", snode); //执行点击事件
		  		      	}
		  		      	if(json.type == "edit"){ //修改更新子节点
		  		      		var map = json.nodes;
			       			var treeObj = $.fn.zTree.getZTreeObj("buy-sortTree-buySupplierSort");
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
		  		      	refreshLayout("供应商分类【详情】", "basefiles/buySupplierSortViewPage.do?id="+ json.backid);
		  		    }
		  		    else{
		  		       	$.messager.alert("提醒","保存失败");
		  		    }
		  		}
		  	});
    	}
    	function refreshLayout(title, url){
    		$("#buy-layout-buySupplierSort").layout('remove','center').layout('add',{
				region: 'center',  
			    title: title,
			    href:url
			});
    	}
    	var buySupplierSort_ajaxContent = function (param, url) { //同步ajax
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
		    			if(typeof(value)=="undefined" || !value){
	    					$.fn.validatebox.defaults.rules.validLength.message ='验证失败';
	    					return false;
		    			}
			    		var reg=eval("/^[A-Za-z0-9]{"+len+"}$/");//正则表达式使用变量 
	  					if(reg.test(value) == true){
	  						if(value == initValue){
	  							return true;
	  						}
				    		var data=buySupplierSort_ajaxContent({id:(id+value)},url);
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
			    		var data=buySupplierSort_ajaxContent({name: (name + '/' + value)},url);
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
    		$("#buy-thisId-buySupplierSort").val(treeNode.id);
			$("#buy-parentId-buySupplierSort").val(treeNode.pid);
			$("#buy-state-buySupplierSort").val(treeNode.state);
			$("#buy-level-buySupplierSort").val(treeNode.level);
			if (treeNode.isParent) {
				$("#buy-isParent-buySupplierSort").val("1")
			} else {
				$("#buy-isParent-buySupplierSort").val("0")
			}
			return true;
    	}
    </script>
  </body>
</html>
