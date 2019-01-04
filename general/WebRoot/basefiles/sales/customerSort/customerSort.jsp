<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户分类</title>
    <%@include file="/include.jsp" %>   
  </head>
  <body>
  	<input type="hidden" id="sales-thisId-customerSort" />
  	<input type="hidden" id="sales-parentId-customerSort" />
  	<input type="hidden" id="sales-isParent-customerSort" />
  	<input type="hidden" id="sales-state-customerSort" />
  	<input type="hidden" id="sales-level-customerSort" />
  	<input type="hidden" id="sales-hasLevel-customerSort" value="${len }" />
  	<input type="hidden" id="sales-leaveLen-customerSort" value="${lenStr }" />
    <div class="easyui-layout" title="客户分类" data-options="fit:true" id="sales-layout-customerSort">
    	<div data-options="region:'north',border:false" style="height:30px;overflow: hidden">
    		<div class="buttonBG" id="sales-buttons-customerSort"></div>
    	</div>
    	<div data-options="region:'west',split:true" title="客户分类" style="width:200px;">
    		<div id="sales-sortTree-customerSort" class="ztree"></div>
    	</div>
    	<div data-options="region:'center',border:true" title="">
    	
    	</div>
    </div>
    <script type="text/javascript">
    	var customerSort_lenArr = $("#sales-leaveLen-customerSort").val().split(',');
    	$(function(){
    		//树型
			var customerSortTreeSetting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "basefiles/getCustomerSortTree.do",
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
							refreshLayout("客户分类【新增】", 'basefiles/customerSortAddPage.do');
						}
						else{
							refreshLayout("客户分类【详情】",'basefiles/customerSortViewPage.do?id='+ treeNode.id);
						}
						zTreeBeforeClick(treeId, treeNode);
						var zTree = $.fn.zTree.getZTreeObj("sales-sortTree-customerSort");
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
			$.fn.zTree.init($("#sales-sortTree-customerSort"), customerSortTreeSetting,null);
			//按钮
			$("#sales-buttons-customerSort").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/basefiles/customerSortAdd.do">
						{
							type: 'button-add',
							handler: function(){
								var thisId = $("#sales-thisId-customerSort").val();
								var hasLevel = $("#sales-hasLevel-customerSort").val();
								var level = $("#sales-level-customerSort").val();
								if(level == hasLevel){
									$.messager.alert("警告","该节点已为最大级次,不能再新增子节点!");
									return false;
								}
								refreshLayout("客户分类【新增】",'basefiles/customerSortAddPage.do?id='+ thisId);
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSortEdit.do">
						{
							type: "button-edit",
							handler: function(){
								var id = $("#sales-thisId-customerSort").val();
								if(id == ""){
									$.messager.alert("提醒","请选择一条需要修改的数据！");
									return false;
								}
								refreshLayout("客户分类【修改】", "basefiles/customerSortEditPage.do?id="+ id);
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSortHold.do">
						{
							type: 'button-hold',
							handler: function(){
								$.messager.confirm("提醒","确定暂存该客户分类？",function(r){
									if(r){
										$("#sales-addType-customerSortAddPage").val("temp");
										customerSort_tempSave_form_submit();
										$("#sales-form-customerSortAddPage").submit();
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSortSave.do">
						{
							type: "button-save",
							handler: function(){
								var flag = $("#sales-form-customerSortAddPage").form('validate');
					  		   	if(flag==false){
					  		   		return false;
					  		   	}
								$.messager.confirm("提醒","确定保存该客户分类？",function(r){
									if(r){
										$("#sales-addType-customerSortAddPage").val("real");
										customerSort_realSave_form_submit();
										$("#sales-form-customerSortAddPage").submit();
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSortDelete.do">
						{
							type: 'button-delete',
							handler: function(){
								var id = $("#sales-thisId-customerSort").val();
								var isParent = $("#sales-isParent-customerSort").val();
								if(isParent == "1"){
									$.messager.alert("提醒","先删除所有子节点后再删除该节点！");
									return false;
								}
								$.messager.confirm("提醒","是否删除该客户分类信息?",function(r){
						  			if(r){
						  				loading("删除中..");
							  			$.ajax({
								  			url:'basefiles/deleteCustomerSort.do',
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
								  		        	var treeObj = $.fn.zTree.getZTreeObj("sales-sortTree-customerSort");
													var node = treeObj.getNodeByParam("id", id, null);
													treeObj.removeNode(node); //删除子节点
								  		        	var pid = $("#sales-parentId-customerSort").val();
								  		        	var snode = treeObj.getNodeByParam("id", pid, null);
										  		    treeObj.selectNode(snode, false); //选中节点
										  		    zTreeBeforeClick("sales-sortTree-customerSort", snode); //执行点击事件
                                                    if(snode.id != "" && null != snode.id){
                                                        refreshLayout("客户分类【详情】","basefiles/customerSortViewPage.do?id="+ snode.id);
                                                    }else{
                                                        refreshLayout("客户分类【新增】","basefiles/customerSortAddPage.do?id="+ pid);
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
					<security:authorize url="/basefiles/customerSortOpen.do">
						{
							type: "button-open",
							handler: function(){
								var id = $("#sales-thisId-customerSort").val();
								var pid = $("#sales-parentId-customerSort").val();
								var treeObj = $.fn.zTree.getZTreeObj("sales-sortTree-customerSort");
								var node = treeObj.getNodeByParam("id", pid, null);
								if(node.state == "0"){
									$.messager.alert("提醒","上级节点为禁用状态，无法启用该节点！");
									return false;
								}
								$.messager.confirm("提醒","确定启用该客户分类?",function(r){
						  			if(r){
						  				loading("启用中..");
							  			$.ajax({
								  			url:'basefiles/openCustomerSort.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","启用成功");
								  		        	var treeObj = $.fn.zTree.getZTreeObj("sales-sortTree-customerSort");
													var node = treeObj.getNodeByParam("id", id, null);
													node.state = '1';
													treeObj.updateNode(node); //更新子节点
								  		        	var pid = $("#sales-parentId-customerSort").val();
								  		        	refreshLayout("客户分类【详情】","basefiles/customerSortViewPage.do?id="+ id);
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
					<security:authorize url="/basefiles/customerSortClose.do">
						{
							type: "button-close",
							handler: function(){
								var id = $("#sales-thisId-customerSort").val();
								$.messager.confirm("提醒","禁用该客户分类，将禁用该节点下所有客户分类，是否禁用？",function(r){
						  			if(r){
						  				loading("禁用中..");
							  			$.ajax({
								  			url:'basefiles/closeCustomerSort.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  		        $.messager.alert("提醒","禁用成功数："+ json.successNum + "<br />禁用失败数："+ json.failureNum + "<br />不可禁用数："+ json.notAllowNum);
								  		        var treeObj = $.fn.zTree.getZTreeObj("sales-sortTree-customerSort");
												for(var i=0;i<json.ids.length;i++){ //所有禁用成功的节点都需要更新状态
													var node = treeObj.getNodeByParam("id", json.ids[i], null);
													node.state = '0';
													treeObj.updateNode(node); //更新子节点
												}
								  		        refreshLayout("客户分类【详情】","basefiles/customerSortViewPage.do?id="+ id);
								  			}
							  			});
						  			}
						  		});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSortImport.do">
						{
                            type:'button-import',//导入
                            attr:{
                                clazz: "salesService",
                                method: "addDRCustomerSortExcel",
                                tn: "t_base_sales_customersort",
                                module: 'basefiles',
                                pojo: "CustomerSort",
                                onClose: function(){

                                }
                            }
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSortExport.do">
						{
                            type:'button-export',//导出
                            attr:{
                                tn: 't_base_sales_customersort',//表名
                                name:'客户分类列表'
                            }
						},
					</security:authorize>
					{}
				],
				model: 'base',
				type: 'list',
				tname: 't_base_sales_customersort'
			});
    	});
    	function customerSort_tempSave_form_submit(){
    		$("#sales-form-customerSortAddPage").form({
			    onSubmit: function(){  
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		    if(json.flag==true){
		  		      	$.messager.alert("提醒","暂存成功");
		  		      	var id = $("#sales-thisId-customerSort").val();
		  		      	var pid = $("#sales-parentId-customerSort").val();
		  		      	if(json.type == "add"){ //新增增加子节点
			  		      	var treeObj = $.fn.zTree.getZTreeObj("sales-sortTree-customerSort");
			  		      	var node = treeObj.getNodeByParam("id", id, null);
			  		      	treeObj.addNodes(node, json.node); //增加子节点
			  		      	var snode = treeObj.getNodeByParam("id", json.node.id, null);
			  		      	treeObj.selectNode(snode, false); //选中节点
			  		      	zTreeBeforeClick("sales-sortTree-customerSort", snode); //执行点击事件
		  		      	}
		  		      	if(json.type == "edit"){ //修改更新子节点
		  		      		var editType = $("#sales-editType-customerSortAddPage").val();
		  		      		var treeObj = $.fn.zTree.getZTreeObj("sales-sortTree-customerSort");
		  		      		if(editType == "true"){
				  		      	var node = treeObj.getNodeByParam("id", pid, null);
				  		      	var cnode = treeObj.getNodeByParam("id", json.oldid, null);
								treeObj.removeNode(cnode); //删除子节点
				  		      	treeObj.addNodes(node, json.node); //增加子节点
				  		      	var snode = treeObj.getNodeByParam("id", json.node.id, null);
				  		      	treeObj.selectNode(snode, false); //选中节点
				  		      	zTreeBeforeClick("sales-sortTree-customerSort", snode); //执行点击事件
			  		      	}else{
				  		      	var node = treeObj.getNodeByParam("id", json.oldid, null);
								node.name = json.node.name;
								node.pid = json.node.pid;
								node.state = json.node.state;
								treeObj.updateNode(node); //更新子节点
							}
		  		      	}
		  		      	refreshLayout("客户分类【详情】", "basefiles/customerSortViewPage.do?id="+ json.backid);
		  		    }
		  		    else{
		  		       	$.messager.alert("提醒","暂存失败");
		  		    }
		  		}
		  	});
    	}
    	function customerSort_realSave_form_submit(){
    		$("#sales-form-customerSortAddPage").form({
			    onSubmit: function(){  
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		    if(json.flag==true){
		  		      	$.messager.alert("提醒","保存成功");
		  		      	var id = $("#sales-thisId-customerSort").val();
		  		      	var pid = $("#sales-parentId-customerSort").val();
		  		      	if(json.type == "add"){ //新增增加子节点
			  		      	var treeObj = $.fn.zTree.getZTreeObj("sales-sortTree-customerSort");
			  		      	var node = treeObj.getNodeByParam("id", id, null);
			  		      	treeObj.addNodes(node, json.node); //增加子节点
			  		      	var snode = treeObj.getNodeByParam("id", json.node.id, null);
			  		      	treeObj.selectNode(snode, false); //选中节点
			  		      	zTreeBeforeClick("sales-sortTree-customerSort", snode); //执行点击事件
		  		      	}
		  		      	if(json.type == "edit"){ //修改更新子节点
		  		      		var map = json.nodes;
			       			var treeObj = $.fn.zTree.getZTreeObj("sales-sortTree-customerSort");
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
		  		      	refreshLayout("客户分类【详情】", "basefiles/customerSortViewPage.do?id="+ json.backid);
		  		    }
		  		    else{
		  		       	$.messager.alert("提醒","保存失败");
		  		    }
		  		}
		  	});
    	}
    	function refreshLayout(title, url){
    		$("#sales-layout-customerSort").layout('remove','center').layout('add',{
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
    	function validUsed(url, name, message){
    		$.extend($.fn.validatebox.defaults.rules, {
				validUsed:{
			    	validator:function(value){
			    		var data=customerSort_ajaxContent({name: (name + '/' + value)},url);
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
    		$("#sales-thisId-customerSort").val(treeNode.id);
			$("#sales-parentId-customerSort").val(treeNode.pid);
			$("#sales-state-customerSort").val(treeNode.state);
			$("#sales-level-customerSort").val(treeNode.level);
			if (treeNode.isParent) {
				$("#sales-isParent-customerSort").val("1")
			} else {
				$("#sales-isParent-customerSort").val("0")
			}
    	}
    </script>
  </body>
</html>
