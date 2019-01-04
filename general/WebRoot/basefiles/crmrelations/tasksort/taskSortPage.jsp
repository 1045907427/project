<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售机会来源页面</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <input type="hidden" id="taskSort-opera"/>
    <input type="hidden" id="crmrelations-thisId-taskSort" />
  	<input type="hidden" id="crmrelations-parentId-taskSort" />
  	<input type="hidden" id="crmrelations-isParent-taskSort" />
  	<input type="hidden" id="crmrelations-state-taskSort" />
  	<input type="hidden" id="crmrelations-level-taskSort" />
  	<input type="hidden" id="crmrelations-hasLevel-taskSort" value="${len }" />
  	<input type="hidden" id="crmrelations-leaveLen-taskSort" value="${lenStr }" />
  	<div class="easyui-layout" title="任务分类" data-options="fit:true" id="crmrelations-layout-taskSort">
  		<div data-options="region:'north',border:false" style="height:30px;overflow: hidden">
  			<div class="buttonBG" id="crmrelations-buttons-taskSort"></div>
  		</div>
  		<div data-options="region:'west',split:true" title="任务分类" style="width:200px">
  			<div id="crmrelations-taskSortTree-taskSort" class="ztree"></div>
  		</div>
  		<div data-options="region:'center'"></div>
  	</div>
  	<script type="text/javascript">
  		var $taskSortButton = $("#crmrelations-buttons-taskSort");
  		var taskSort_lenArr = $("#crmrelations-leaveLen-taskSort").val().split(',');
  		var taskSort_AjaxConn = function (Data, Action, Str) {
  			if(null != Str && "" != Str){
  				loading(Str);
  			}
		    var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false,
		        success:function(data){
		        	loaded();
		        }
		    })
		    return MyAjax.responseText;
		}
		
		//判断是否加锁
		function isLockData(id,tablename){
			var flag = false;
			$.ajax({
	            url :'system/lock/isLockData.do',
	            type:'post',
	            data:{id:id,tname:tablename},
	            dataType:'json',
	            async: false,
	            success:function(json){
	            	flag = json.flag
	            }
	        });
	        return flag;
		}
		//加锁
	    function isDoLockData(id,tablename){
	    	var flag = false;
	    	$.ajax({   
	            url :'system/lock/isDoLockData.do',
	            type:'post',
	            data:{id:id,tname:tablename},
	            dataType:'json',
	            async: false,
	            success:function(json){
	            	flag = json.flag
	            }
	        });
	        return flag;
	    }
	    function refreshLayout(title, url){
    		$("#crmrelations-layout-taskSort").layout('remove','center').layout('add',{
				region: 'center',  
			    title: title,
			    href:url
			});
    	}
    	//加载下拉框 
    	var taskSort_defaultdeptid = "";
		function loadDropdown(){
			//默认责任部门
			$("#crmrelations-defaultDept-taskSort").widget({
				width:200,
				name:'t_base_crm_task_sort',
				col:'defaultdeptid',
				singleSelect:true,
				onlyLeafCheck:false,
		  		required:true,
		  		onSelect:function(data){
		  			taskSort_defaultdeptid = data.id;
		  			$("#crmrelations-defaultUser-taskSort").widget({
						width:200,
						param:[{field:'pId',op:'equal',value:taskSort_defaultdeptid}],
						name:'t_base_crm_task_sort',
						col:'defaultuserid',
						singleSelect:true,
						onlyLeafCheck:false,
						required:true
					});
		  		}
			});
			//默认责任人
			$("#crmrelations-defaultUser-taskSort").widget({
				width:200,
				name:'t_base_crm_task_sort',
				col:'defaultuserid',
				singleSelect:true,
				onlyLeafCheck:true,
				required:true
			});
			
			//默认费用分类
			$("#crmrelations-defaultexpenses-taskSort").widget({
				width:200,
				name:'t_base_crm_task_sort',
				col:'defaultexpensesid',
				singleSelect:true,
				onlyLeafCheck:true,
				required:true
			});
			
			//状态
			$('#common-combobox-state').combobox({
			    url:'common/sysCodeList.do?type=state',   
			    valueField:'id',   
			    textField:'name'
			});
		}
    	function zTreeBeforeClick(treeId, treeNode){
    		$("#crmrelations-thisId-taskSort").val(treeNode.id);
			$("#crmrelations-parentId-taskSort").val(treeNode.parentid);
			$("#crmrelations-state-taskSort").val(treeNode.state);
			$("#crmrelations-hdlevel-taskSort").val(treeNode.level);
			if (treeNode.isParent) {
				$("#crmrelations-isParent-taskSort").val("1")
			} else {
				$("#crmrelations-isParent-taskSort").val("0")
			}
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
				    		var data=taskSort_AjaxConn({id:(id+value)},url);
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
  							var data=taskSort_AjaxConn({name: retName},url);
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
	    $(function(){
	    	//树型数据
	    	var taskSortTreeSetting = {
  				view:{
  					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
  				},
  				async:{
  					enable: true,
					url: "basefiles/crmrelations/getTaskSortTree.do",
					autoParam: ["id","parentid", "text","state"]
  				},
  				data:{
  					key:{
						//title:"text"
						name:"text"
					},
					simpleData: {
						enable:true,
						idKey: "id",
						pIdKey: "parentid",
						rootPId: "" 
					}
  				},
  				callback:{
  					//点击树状菜单更新页面按钮列表
  					beforeClick:function(treeId,treeNode){
  						if(treeNode.id == ""){
  							refreshLayout("任务【新增】",'basefiles/crmrelations/showTaskSortAddPage.do');
  							$("#taskSort-opera").attr("value","add");
  						}
  						else{
  							refreshLayout("任务【详情】",'basefiles/crmrelations/showTaskSortViewPage.do?id='+treeNode.id);
  							$("#taskSort-opera").attr("value","view");
  						}
  						zTreeBeforeClick(treeId, treeNode);
  						var zTree = $.fn.zTree.getZTreeObj("crmrelations-taskSortTree-taskSort");
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
  			$.fn.zTree.init($("#crmrelations-taskSortTree-taskSort"), taskSortTreeSetting,null);
  			
  			//按钮
  			$taskSortButton.buttonWidget({
  				initButton:[
  					{},
					<security:authorize url="/basefiles/crmrelations/taskSortAddBtn.do">
	  					{
	  						type: 'button-add',//新增
							handler: function(){
								var thisId = $("#crmrelations-thisId-taskSort").val();
								var hasLevel = $("#crmrelations-hasLevel-taskSort").val();
								var level = $("#crmrelations-level-taskSort").val();
								if(level == hasLevel){
									$.messager.alert("警告","该节点已为最大级次,不能再新增子节点!");
									return false;
								}
								refreshLayout("任务【新增】", 'basefiles/crmrelations/showTaskSortAddPage.do?id='+ thisId);
								$("#taskSort-opera").attr("value","add");
							}
	  					},
  					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/taskSortEditBtn.do">
	  					{
	  						type: "button-edit",//修改
							handler: function(){
								var id = $("#crmrelations-thisId-taskSort").val();
								if(id == ""){
									$.messager.alert("提醒","请选择一条需要修改的数据！");
									return false;
								}
								var flag = isDoLockData(id,"t_base_crm_task_sort");
				 				if(!flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
				 					return false;
				 				}
								refreshLayout("任务【修改】", "basefiles/crmrelations/showTaskSortEditPage.do?id="+ id);
								$("#taskSort-opera").attr("value","edit");
							}
	  					},
  					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/taskSortHoldBtn.do">
	  					{
	  						type: 'button-hold',//暂存
							handler: function(){
								var type = $("#taskSort-opera").val();
								if($("#crmrelations-thisId-taskSortAdd").val() == ""){
									$.messager.alert("提醒","本级编码不能为空!");
									return false;
								}
								$.messager.confirm("提醒","确定暂存该任务？",function(r){
									if(r){
										if(type == "add"){//新增
											addTaskSort("hold");
										}
										else{//修改
											editTaskSort("hold");
										}
									}
								});
							}
	  					},
  					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/taskSortSaveBtn.do">
	  					{
							type: "button-save",//保存
							handler: function(){
								var type = $("#taskSort-opera").val();
								$.messager.confirm("提醒","确定保存该任务？",function(r){
									if(r){
										if(type == "add"){
											addTaskSort("save");
										}
										else{
											editTaskSort("save");
										}
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/taskSortCopyBtn.do">
						{
							type: "button-copy",//复制
							handler: function(){
								var id = $("#crmrelations-thisId-taskSort").val();
								if(id == ""){
									$.messager.alert("提醒","请选择一条需要复制的数据！");
									return false;
								}
								refreshLayout("任务【复制】", "basefiles/crmrelations/showTaskSortCopyPage.do?id="+ id);
								$("#taskSort-opera").attr("value","add");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/taskSortGiveupBtn.do">
						{
			 				type:'button-giveup',//放弃 
			 				handler:function(){
				 				var type = $("#taskSort-opera").val();
				 				if(type=="add"){
				 					$taskSortButton.buttonWidget("initButtonType","list");
				 					$("#crmrelations-layout-taskSort").layout('remove','center');
				 				}else if(type=="edit"){
				 					var id = $("#crmrelations-thisId-taskSort").val();
				 					var state = $("#crmrelations-state-taskSort").val();
				 					$.ajax({   
							            url :'system/lock/unLockData.do',
							            type:'post',
							            data:{id:id,tname:'t_base_crm_task_sort'},
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
					 				refreshLayout("任务【详情】","basefiles/crmrelations/showTaskSortViewPage.do?id="+ id);
									$("#taskSort-opera").attr("value","view");
				 				}
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/crmrelations/taskSortDeleteBtn.do">
						{
							type: 'button-delete',
							handler: function(){
								var id = $("#crmrelations-thisId-taskSort").val();
								var isParent = $("#crmrelations-isParent-taskSort").val();
								var flag = isLockData(id,"t_base_crm_task_sort");
				 				if(flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能删除！");
				 					return false;
				 				}
								if(isParent == "1"){
									$.messager.alert("提醒","先删除所有子节点后再删除该节点！");
									return false;
								}
								$.messager.confirm("提醒","是否删除该任务信息?",function(r){
						  			if(r){
						  				loading("删除中..");
							  			$.ajax({
								  			url:'basefiles/crmrelations/deleteTaskSort.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.delFlag==true){
								  					$.messager.alert("提醒","该信息已被其他信息引用，无法删除！");
								  					return false;
								  				}
								  				if(json.flag){
								  		        	$.messager.alert("提醒","删除成功");
								  		        	var treeObj = $.fn.zTree.getZTreeObj("crmrelations-taskSortTree-taskSort");
													var node = treeObj.getNodeByParam("id", id, null);
													treeObj.removeNode(node); //删除子节点
								  		        	var pid = $("#crmrelations-parentId-taskSort").val();
								  		        	var snode = treeObj.getNodeByParam("id", pid, null);
										  		    treeObj.selectNode(snode, false); //选中节点
										  		    zTreeBeforeClick("crmrelations-taskSortTree-taskSort", snode); //执行点击事件
								  		        	$("#crmrelations-layout-taskSort").layout('remove','center');
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
					<security:authorize url="/basefiles/crmrelations/taskSortOpenBtn.do">
					{
						type: "button-open",
						handler: function(){
							var id = $("#crmrelations-thisId-taskSort").val();
							var pid = $("#crmrelations-parentId-taskSort").val();
							var treeObj = $.fn.zTree.getZTreeObj("crmrelations-taskSortTree-taskSort");
							var node = treeObj.getNodeByParam("id", pid, null);
							if(node.state == "0"){
								$.messager.alert("提醒","上级节点为禁用状态，无法启用该节点！");
								return false;
							}
							$.messager.confirm("提醒","确定启用该任务?",function(r){
					  			if(r){
					  				loading("启用中..");
						  			$.ajax({
							  			url:'basefiles/crmrelations/enableTaskSort.do',
							  			data:'id='+id,
							  			dataType:'json',
							  			type:'post',
							  			success:function(json){
							  				loaded();
							  				if(json.flag==true){
							  		        	$.messager.alert("提醒","启用成功");
							  		        	var treeObj = $.fn.zTree.getZTreeObj("crmrelations-taskSortTree-taskSort");
												var node = treeObj.getNodeByParam("id", id, null);
												node.state = '1';
												treeObj.updateNode(node); //更新子节点
							  		        	var pid = $("#crmrelations-parentId-taskSort").val();
							  		        	refreshLayout("任务【详情】","basefiles/crmrelations/showTaskSortViewPage.do?id="+ id);
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
					<security:authorize url="/basefiles/crmrelations/taskSortCloseBtn.do">
						{
							type: "button-close",
							handler: function(){
								var id = $("#crmrelations-thisId-taskSort").val();
								$.messager.confirm("提醒","禁用该任务，将禁用该节点下所有任务，是否禁用？",function(r){
						  			if(r){
						  				loading("禁用中..");
							  			$.ajax({
								  			url:'basefiles/crmrelations/disableTaskSort.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  		        $.messager.alert("提醒","禁用成功数："+ json.successNum + "<br />禁用失败数："+ json.failureNum + "<br />不可禁用数："+ json.notAllowNum);
								  		        var treeObj = $.fn.zTree.getZTreeObj("crmrelations-taskSortTree-taskSort");
												for(var i=0;i<json.ids.length;i++){ //所有禁用成功的节点都需要更新状态
													var node = treeObj.getNodeByParam("id", json.ids[i], null);
													node.state = '0';
													treeObj.updateNode(node); //更新子节点
												}
								  		        refreshLayout("任务【详情】","basefiles/crmrelations/showTaskSortViewPage.do?id="+ id);
								  			}
							  			});
						  			}
						  		});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/taskSortPreviewBtn.do">
						{
							type: "button-preview",
							handler: function(){
								
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/taskSortPrintBtn.do">
						{
							type: "button-print",
							handler: function(){
								
							}
						},
					</security:authorize>
					{}
  				],
  				model:'base',
  				type:'list',
  				tname:'t_base_crm_task_sort'
  			});
	    });
  	</script>
  </body>
</html>
