<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售机会来源页面</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<input type="hidden" id="salesChanceSort-opera"/>
    <input type="hidden" id="crmrelations-thisId-salesChanceSort" />
  	<input type="hidden" id="crmrelations-parentId-salesChanceSort" />
  	<input type="hidden" id="crmrelations-isParent-salesChanceSort" />
  	<input type="hidden" id="crmrelations-state-salesChanceSort" />
  	<input type="hidden" id="crmrelations-level-salesChanceSort" />
  	<input type="hidden" id="crmrelations-hasLevel-salesChanceSort" value="${len }" />
  	<input type="hidden" id="crmrelations-leaveLen-salesChanceSort" value="${lenStr }" />
  	<div class="easyui-layout" title="销售机会来源分类" data-options="fit:true" id="crmrelations-layout-salesChanceSort">
  		<div data-options="region:'north'" style="height:30px;overflow: hidden">
  			<div class="buttonBG" id="crmrelations-buttons-salesChanceSort"></div>
  		</div>
  		<div data-options="region:'west',border:false,split:true" title="销售机会来源" style="width:200px">
  			<div id="crmrelations-salesChanceSortTree-salesChanceSort" class="ztree"></div>
  		</div>
  		<div data-options="region:'center',border:false"></div>
  	</div>
  	<script type="text/javascript">
  		var $saleChanceSortButton = $("#crmrelations-buttons-salesChanceSort");
  		var saleChance_lenArr = $("#crmrelations-leaveLen-salesChanceSort").val().split(',');
  		var saleChanceSort_AjaxConn = function (Data, Action, Str) {
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
  		$(function(){
  			//树型
  			var salesChanceSortTreeSetting = {
  				view:{
  					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
  				},
  				async:{
  					enable: true,
					url: "basefiles/crmrelations/getSaleChanceSortTree.do",
					autoParam: ["id","parentid", "text","state"]
  				},
  				data:{
  					key:{
						title:"text",
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
  							refreshLayout("销售机会来源【新增】",'basefiles/crmrelations/showSaleChance_SortAddPage.do');
  							$("#salesChanceSort-opera").attr("value","add");
  						}
  						else{
  							refreshLayout("销售机会来源【详情】",'basefiles/crmrelations/showSaleChance_SortViewPage.do?id='+treeNode.id);
  							$("#salesChanceSort-opera").attr("value","view");
  						}
  						zTreeBeforeClick(treeId, treeNode);
  						var zTree = $.fn.zTree.getZTreeObj("crmrelations-salesChanceSortTree-salesChanceSort");
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
  			$.fn.zTree.init($("#crmrelations-salesChanceSortTree-salesChanceSort"), salesChanceSortTreeSetting,null);
  			//按钮
  			$saleChanceSortButton.buttonWidget({
  				initButton:[
  					{},
					<security:authorize url="/basefiles/crmrelations/sortChanceAddBtn.do">
  						{
	  						type: 'button-add',//新增
							handler: function(){
								var thisId = $("#crmrelations-thisId-salesChanceSort").val();
								var hasLevel = $("#crmrelations-hasLevel-salesChanceSort").val();
								var level = $("#crmrelations-level-salesChanceSort").val();
								if(level == hasLevel){
									$.messager.alert("警告","该节点已为最大级次,不能再新增子节点!");
									return false;
								}
								refreshLayout("销售机会来源【新增】", 'basefiles/crmrelations/showSaleChance_SortAddPage.do?id='+ thisId);
								$("#salesChanceSort-opera").attr("value","add");
							}
	  					},
  					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/sortChanceEditBtn.do">
  						{
	  						type: "button-edit",//修改
							handler: function(){
								var id = $("#crmrelations-thisId-salesChanceSort").val();
								if(id == ""){
									$.messager.alert("提醒","请选择一条需要修改的数据！");
									return false;
								}
								var flag = isDoLockData(id,"t_base_crm_salechance_sort");
				 				if(!flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
				 					return false;
				 				}
								refreshLayout("销售机会来源【修改】", "basefiles/crmrelations/showSaleChance_SortEditPage.do?id="+ id);
								$("#salesChanceSort-opera").attr("value","edit");
							}
	  					},
  					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/sortChanceHoldBtn.do">
	  					{
	  						type: 'button-hold',//暂存
							handler: function(){
								var type = $("#salesChanceSort-opera").val();
								if($("#crmrelations-thisId-saleChance").val() == ""){
									$.messager.alert("提醒","本级编码不能为空!");
									return false;
								}
								$.messager.confirm("提醒","确定暂存该销售机会来源？",function(r){
									if(r){
										if(type == "add"){//新增
											addSaleChance("hold");
										}
										else{//修改
											editSaleChance("hold");
										}
									}
								});
							}
	  					},
  					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/sortChanceSaveBtn.do">
						{
							type: "button-save",//保存
							handler: function(){
								var type = $("#salesChanceSort-opera").val();
								$.messager.confirm("提醒","确定保存该销售机会来源？",function(r){
									if(r){
										if(type == "add"){
											addSaleChance("save");
										}
										else{
											editSaleChance("save");
										}
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/sortChanceCopyBtn.do">
						{
							type: "button-copy",//复制
							handler: function(){
								var id = $("#crmrelations-thisId-salesChanceSort").val();
								if(id == ""){
									$.messager.alert("提醒","请选择一条需要复制的数据！");
									return false;
								}
								refreshLayout("销售机会来源【复制】", "basefiles/crmrelations/showSaleChance_SortCopyPage.do?id="+ id);
								$("#salesChanceSort-opera").attr("value","add");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/sortChanceGiveupBtn.do">
						{
			 				type:'button-giveup',//放弃 
			 				handler:function(){
				 				var type = $("#salesChanceSort-opera").val();
				 				if(type=="add"){
				 					$saleChanceSortButton.buttonWidget("initButtonType","list");
				 					$("#crmrelations-layout-salesChanceSort").layout('remove','center');
				 				}else if(type=="edit"){
				 					var id = $("#crmrelations-thisId-salesChanceSort").val();
				 					var state = $("#crmrelations-state-salesChanceSort").val();
				 					$.ajax({   
							            url :'system/lock/unLockData.do',
							            type:'post',
							            data:{id:id,tname:'t_base_crm_salechance_sort'},
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
					 				refreshLayout("销售机会来源【详情】","basefiles/crmrelations/showSaleChance_SortViewPage.do?id="+ id);
									$("#salesChanceSort-opera").attr("value","view");
				 				}
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/crmrelations/sortChanceDeleteBtn.do">
						{
							type: 'button-delete',
							handler: function(){
								var id = $("#crmrelations-thisId-salesChanceSort").val();
								var isParent = $("#crmrelations-isParent-salesChanceSort").val();
								var flag = isLockData(id,"t_base_crm_salechance_sort");
				 				if(flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能删除！");
				 					return false;
				 				}
								if(isParent == "1"){
									$.messager.alert("提醒","先删除所有子节点后再删除该节点！");
									return false;
								}
								$.messager.confirm("提醒","是否删除该销售机会来源信息?",function(r){
						  			if(r){
						  				loading("删除中..");
							  			$.ajax({
								  			url:'basefiles/crmrelations/deleteSaleChance.do',
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
								  		        	var treeObj = $.fn.zTree.getZTreeObj("crmrelations-salesChanceSortTree-salesChanceSort");
													var node = treeObj.getNodeByParam("id", id, null);
													treeObj.removeNode(node); //删除子节点
								  		        	var pid = $("#crmrelations-parentId-salesChanceSort").val();
								  		        	var snode = treeObj.getNodeByParam("id", pid, null);
										  		    treeObj.selectNode(snode, false); //选中节点
										  		    zTreeBeforeClick("crmrelations-salesChanceSortTree-salesChanceSort", snode); //执行点击事件
								  		        	//refreshLayout("销售机会来源【新增】","basefiles/crmrelations/showSaleChance_SortAddPage.do?id="+ pid);
								  		        	$("#crmrelations-layout-salesChanceSort").layout('remove','center');
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
					<security:authorize url="/basefiles/crmrelations/sortChanceOpenBtn.do">
						{
							type: "button-open",
							handler: function(){
								var id = $("#crmrelations-thisId-salesChanceSort").val();
								var pid = $("#crmrelations-parentId-salesChanceSort").val();
								var treeObj = $.fn.zTree.getZTreeObj("crmrelations-salesChanceSortTree-salesChanceSort");
								var node = treeObj.getNodeByParam("id", pid, null);
								if(node.state == "0"){
									$.messager.alert("提醒","上级节点为禁用状态，无法启用该节点！");
									return false;
								}
								$.messager.confirm("提醒","确定启用该销售机会来源?",function(r){
						  			if(r){
						  				loading("启用中..");
							  			$.ajax({
								  			url:'basefiles/crmrelations/enableSaleChance_Sort.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","启用成功");
								  		        	var treeObj = $.fn.zTree.getZTreeObj("crmrelations-salesChanceSortTree-salesChanceSort");
													var node = treeObj.getNodeByParam("id", id, null);
													node.state = '1';
													treeObj.updateNode(node); //更新子节点
								  		        	var pid = $("#crmrelations-parentId-salesChanceSort").val();
								  		        	refreshLayout("销售机会来源【详情】","basefiles/crmrelations/showSaleChance_SortViewPage.do?id="+ id);
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
					<security:authorize url="/basefiles/crmrelations/sortChanceCloseBtn.do">
						{
							type: "button-close",
							handler: function(){
								var id = $("#crmrelations-thisId-salesChanceSort").val();
								$.messager.confirm("提醒","禁用该销售机会来源，将禁用该节点下所有销售机会来源，是否禁用？",function(r){
						  			if(r){
						  				loading("禁用中..");
							  			$.ajax({
								  			url:'basefiles/crmrelations/disableSaleChance_Sort.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  		        $.messager.alert("提醒","禁用成功数："+ json.successNum + "<br />禁用失败数："+ json.failureNum + "<br />不可禁用数："+ json.notAllowNum);
								  		        var treeObj = $.fn.zTree.getZTreeObj("crmrelations-salesChanceSortTree-salesChanceSort");
												for(var i=0;i<json.ids.length;i++){ //所有禁用成功的节点都需要更新状态
													var node = treeObj.getNodeByParam("id", json.ids[i], null);
													node.state = '0';
													treeObj.updateNode(node); //更新子节点
												}
								  		        refreshLayout("销售机会来源【详情】","basefiles/crmrelations/showSaleChance_SortViewPage.do?id="+ id);
								  			}
							  			});
						  			}
						  		});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/sortChancePreViewBtn.do">
						{
							type: "button-preview",
							handler: function(){
								
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/sortChancePrintBtn.do">
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
  				tname:'t_base_crm_salechance_sort'
  			});
  		});
  		
  		function refreshLayout(title, url){
    		$("#crmrelations-layout-salesChanceSort").layout('remove','center').layout('add',{
				region: 'center',  
			    title: title,
			    href:url
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
				    		var data=saleChanceSort_AjaxConn({id:(id+value)},url);
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
  							var data=saleChanceSort_AjaxConn({name: retName},url);
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
    		$("#crmrelations-thisId-salesChanceSort").val(treeNode.id);
			$("#crmrelations-parentId-salesChanceSort").val(treeNode.parentid);
			$("#crmrelations-state-salesChanceSort").val(treeNode.state);
			$("#crmrelations-level-salesChanceSort").val(treeNode.level);
			if (treeNode.isParent) {
				$("#crmrelations-isParent-salesChanceSort").val("1")
			} else {
				$("#crmrelations-isParent-salesChanceSort").val("0")
			}
    	}
  	</script>
  </body>
</html>
