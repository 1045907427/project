<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>市场活动分类页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
  	<input type="hidden" id="marketActivitySort-opera"/>
    <input type="hidden" id="CRM-thisId-marketActivitySort" />
  	<input type="hidden" id="CRM-parentId-marketActivitySort" />
  	<input type="hidden" id="CRM-isParent-marketActivitySort" />
  	<input type="hidden" id="CRM-state-marketActivitySort" />
  	<input type="hidden" id="CRM-level-marketActivitySort" />
  	<input type="hidden" id="CRM-hasLevel-marketActivitySort" value="${len }" />
  	<input type="hidden" id="CRM-leaveLen-marketActivitySort" value="${lenStr }" />
    <div class="easyui-layout" title="市场活动分类" data-options="fit:true" id="CRM-layout-marketActivitySort">
    	<div data-options="region:'north',border:false" style="height:30px;overflow: hidden">
    		<div class="buttonBG" id="CRM-buttons-marketActivitySort"></div>
    	</div>
    	<div data-options="region:'west',border:false,split:true" title="市场活动分类" style="width:200px;">
    		<div id="CRM-Tree-marketActivitySort" class="ztree"></div>
    	</div>
    	<div data-options="region:'center',border:false" title="市场活动"></div>
    </div>
    <script type="text/javascript">
    	var marketActivitySort_lenArr = $("#CRM-leaveLen-marketActivitySort").val().split(',');
    	
    	function refreshLayout(title, url){
    		$("#CRM-layout-marketActivitySort").layout('remove','center').layout('add',{
				region: 'center',  
			    title: title,
			    href:url
			});
    	}
    	var marketActivitySort_ajaxContent = function (param, url, Str) { //同步ajax
		    if(null != Str && "" != Str){
		    	loading(Str);
		    }
		    var ajax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: url,
		        data: param,
		        async: false,
		        success:function(data){
		        	loaded();
		        }
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
				    		var data=marketActivitySort_ajaxContent({id:(id+value)},url);
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
    	function validUsed(url, name,initValue, message){
    		$.extend($.fn.validatebox.defaults.rules, {
				validUsed:{
			    	validator:function(value,param){
			    		if(value == initValue){
  							return true;
  						}
  						var retName = value;
  						if(name != ""){
  							retName = name + '/' + value;
  						}
  						if(value.length <= param[0]){
  							var data=marketActivitySort_ajaxContent({name: retName},url);
		  					var json = $.parseJSON(data);
		    				if(json.flag == true){
			   					$.fn.validatebox.defaults.rules.validUsed.message = message;
			    				return false;
			    			}else{
		    					return true;
			    			}
  						}
  						else{
  							$.fn.validatebox.defaults.rules.validUsed.message = '输入长度过长,请输入{0}个字符!';
  							return false;
  						}
			    	},
			    	message:''
			    }
			});
    	}
    	function zTreeBeforeClick(treeId, treeNode){
    		$("#CRM-thisId-marketActivitySort").val(treeNode.id);
			$("#CRM-parentId-marketActivitySort").val(treeNode.parentid);
			$("#CRM-state-marketActivitySort").val(treeNode.state);
			$("#CRM-level-marketActivitySort").val(treeNode.level);
			if (treeNode.isParent) {
				$("#CRM-isParent-marketActivitySort").val("1")
			} else {
				$("#CRM-isParent-marketActivitySort").val("0")
			}
    	}
    	$(function(){
    		//树型
			var marketActivitySortTreeSetting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "basefiles/crmrelations/getmarketActivityTree.do",
					autoParam: ["id","parentid", "text","state"]
				},
				data: {
					key:{
						title:"text",
						name:"text"
					},
					simpleData: {
						enable:true,
						idKey: "id",
						pIdKey: "parentid",
						rootPId: null
					}
				},
				callback: {
					//点击树状菜单更新页面按钮列表
					beforeClick: function(treeId, treeNode) {
						if(treeNode.id == ""){
							refreshLayout("市场活动【新增】",'basefiles/crmrelations/marketActivitySortAddPage.do');
							$("#marketActivitySort-opera").val("add");
						}
						else{
							refreshLayout("市场活动【详情】", 'basefiles/crmrelations/marketActivitySortViewPage.do?id='+ treeNode.id);
							$("#marketActivitySort-opera").val("view");
						}
						zTreeBeforeClick(treeId, treeNode);
						var zTree = $.fn.zTree.getZTreeObj("CRM-Tree-marketActivitySort");
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
			$.fn.zTree.init($("#CRM-Tree-marketActivitySort"), marketActivitySortTreeSetting,null);
			//按钮
			$("#CRM-buttons-marketActivitySort").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/basefiles/crmrelations/marketActivitySortAddBtn.do">
						{
							type: 'button-add',
							handler: function(){
								var thisId = $("#CRM-thisId-marketActivitySort").val();
								var hasLevel = $("#CRM-hasLevel-marketActivitySort").val();
								var level = $("#CRM-level-marketActivitySort").val();
								if(level == hasLevel){
									$.messager.alert("警告","该节点已为最大级次,不能再新增子节点!");
									return false;
								}
								refreshLayout("市场活动【新增】", 'basefiles/crmrelations/marketActivitySortAddPage.do?id='+ thisId);
								$("#marketActivitySort-opera").val("add");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/marketActivitySortEditBtn.do">
						{
							type: "button-edit",
							handler: function(){
								var id = $("#CRM-thisId-marketActivitySort").val();
								if(id == ""){
									$.messager.alert("提醒","请选择一条需要修改的数据！");
									return false;
								}
								refreshLayout("市场活动【修改】", "basefiles/crmrelations/marketActivitySortEditPage.do?id="+ id);
								$("#marketActivitySort-opera").val("edit");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/marketActivitySortHoldBtn.do">
						{
							type: 'button-hold',
							handler: function(){
								var type = $("#marketActivitySort-opera").val();
								$.messager.confirm("提醒","确定暂存该市场活动？",function(r){
									if(r){
										if(type == "add"){
											addMarketActivitySort("hold");
										}
										else{
											editMarketActivitySort("hold");
										}
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/marketActivitySortSaveBtn.do">
						{
							type: "button-save",
							handler: function(){
								var type = $("#marketActivitySort-opera").val();
								$.messager.confirm("提醒","确定保存该市场活动？",function(r){
									if(r){
										if(type == "add"){
											addMarketActivitySort("save");
										}
										else{
											editMarketActivitySort("save");
										}
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/marketActivitySortGiveUpBtn.do">
						{
			 				type:'button-giveup',//放弃 
			 				handler:function(){
				 				var type = $("#marketActivitySort-opera").val();
				 				if(type=="add"){
				 					$("#CRM-buttons-marketActivitySort").buttonWidget("initButtonType","list");
				 					$("#CRM-layout-marketActivitySort").layout('remove','center');
				 				}else if(type=="edit"){
				 					var id = $("#CRM-thisId-marketActivitySort").val();
				 					var state = $("#CRM-state-marketActivitySort").val();
				 					$.ajax({   
							            url :'system/lock/unLockData.do',
							            type:'post',
							            data:{id:id,tname:'t_base_crm_marketactivity_sort'},
							            dataType:'json',
							            async: false,
							            success:function(json){
							            	flag = json.flag
							            }
							        });
					 				if(!flag){
					 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
					 					return false;
					 				}
					 				refreshLayout("任务【详情】","basefiles/crmrelations/marketActivitySortViewPage.do?id="+ id);
									$("#marketActivitySort-opera").attr("value","view");
				 				}
				 			}
			 			},
					</security:authorize>
		 			<security:authorize url="/basefiles/crmrelations/marketActivitySortDeleteBtn.do">
						{
							type: 'button-delete',
							handler: function(){
								var id = $("#CRM-thisId-marketActivitySort").val();
								var isParent = $("#CRM-isParent-marketActivitySort").val();
								if(isParent == "1"){
									$.messager.alert("提醒","先删除所有子节点后再删除该节点！");
									return false;
								}
								$.messager.confirm("提醒","是否删除该市场活动信息?",function(r){
						  			if(r){
						  				loading("删除中..");
							  			$.ajax({
								  			url:'basefiles/crmrelations/deleteMarketActivitySort.do',
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
								  		        	var treeObj = $.fn.zTree.getZTreeObj("CRM-Tree-marketActivitySort");
													var node = treeObj.getNodeByParam("id", id, null);
													treeObj.removeNode(node); //删除子节点
								  		        	var pid = $("#CRM-parentId-marketActivitySort").val();
								  		        	var snode = treeObj.getNodeByParam("id", pid, null);
										  		    treeObj.selectNode(snode, false); //选中节点
										  		    zTreeBeforeClick("CRM-Tree-marketActivitySort", snode); //执行点击事件
								  		        	$("#CRM-layout-marketActivitySort").layout('remove','center');
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
					<security:authorize url="/basefiles/crmrelations/marketActivitySortOpenBtn.do">
						{
							type: "button-open",
							handler: function(){
								var id = $("#CRM-thisId-marketActivitySort").val();
								var pid = $("#CRM-parentId-marketActivitySort").val();
								var treeObj = $.fn.zTree.getZTreeObj("CRM-Tree-marketActivitySort");
								var node = treeObj.getNodeByParam("id", pid, null);
								if(node.state == "0"){
									$.messager.alert("提醒","上级节点为禁用状态，无法启用该节点！");
									return false;
								}
								$.messager.confirm("提醒","确定启用该市场活动?",function(r){
						  			if(r){
						  				loading("启用中..");
							  			$.ajax({
								  			url:'basefiles/crmrelations/enableMarketActivitySort.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","启用成功");
								  		        	var treeObj = $.fn.zTree.getZTreeObj("CRM-Tree-marketActivitySort");
													var node = treeObj.getNodeByParam("id", id, null);
													node.state = '1';
													treeObj.updateNode(node); //更新子节点
								  		        	var pid = $("#CRM-parentId-marketActivitySort").val();
								  		        	refreshLayout("市场活动【详情】","basefiles/crmrelations/marketActivitySortViewPage.do?id="+ id);
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
					<security:authorize url="/basefiles/crmrelations/marketActivitySortCloseBtn.do">
						{
							type: "button-close",
							handler: function(){
								var id = $("#CRM-thisId-marketActivitySort").val();
								$.messager.confirm("提醒","禁用该市场活动，将禁用该节点下所有市场活动，是否禁用？",function(r){
						  			if(r){
						  				loading("禁用中..");
							  			$.ajax({
								  			url:'basefiles/crmrelations/disableMarketActivitySort.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  		        $.messager.alert("提醒","禁用成功数："+ json.successNum + "<br />禁用失败数："+ json.failureNum + "<br />不可禁用数："+ json.notAllowNum);
								  		        var treeObj = $.fn.zTree.getZTreeObj("CRM-Tree-marketActivitySort");
												for(var i=0;i<json.ids.length;i++){ //所有禁用成功的节点都需要更新状态
													var node = treeObj.getNodeByParam("id", json.ids[i], null);
													node.state = '0';
													treeObj.updateNode(node); //更新子节点
												}
								  		        refreshLayout("市场活动【详情】","basefiles/crmrelations/marketActivitySortViewPage.do?id="+ id);
								  			}
							  			});
						  			}
						  		});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/marketActivitySortPreviewBtn.do">
						{
							type: "button-preview",
							handler: function(){
								
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/marketActivitySortPrintBtn.do">
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
				tname: 't_base_crm_marketactivity_sort',
				id:''
			});
    	});
    	
    </script>
  </body>
</html>
