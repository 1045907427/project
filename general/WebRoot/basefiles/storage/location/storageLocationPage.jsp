<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>库位档案</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<input type="hidden" id="storageLocation-viewid"/>
  	<input type="hidden" id="storageLocation-viewpid"/>
  	<input type="hidden" id="storageLocation-isParent"/>
  	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north',border:false" style="height: 30px;overflow: hidden;">
   			<div id="storageLocation-button" class="buttonBG"></div>
    	</div>
    	<div title="库位档案列表" data-options="region:'west',split:true" style="width:200px;">
            <div id="storageLocation-tree" class="ztree"></div>
    	</div>
    	<div data-options="region:'center',border:true" title="">
    		<div id="storageLocation-panel"></div>
   		</div>
	</div>
	<script type="text/javascript">
        function refreshLayout(title, url){
            $("#storageLocation-panel").panel({
                fit:true,
                title: title,
                closed:true,
                cache: false,
                href:url
            });
            $("#storageLocation-panel").panel("open");
        }
        function zTreeBeforeClick(treeId, treeNode){
            $("#storageLocation-viewid").val(treeNode.id);
            $("#storageLocation-viewpid").val(treeNode.pid);
            if (treeNode.isParent) {
                $("#storageLocation-isParent").val("1")
            } else {
                $("#storageLocation-isParent").val("0")
            }
        }
		$(function(){
			//树型
			var storageLocationTreeSetting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "basefiles/showStorageLocationTree.do",
					autoParam: ["id","pid", "name","state","isParent"]
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
                            refreshLayout('库位档案【新增】', 'basefiles/showStorageLocationAddPage.do');
						}
						else{
                            refreshLayout('库位档案【详情】', 'basefiles/showStorageLocationInfoPage.do?id='+ treeNode.id);
						}
                        zTreeBeforeClick(treeId, treeNode);
                        var zTree = $.fn.zTree.getZTreeObj("storageLocation-tree");
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
			$.fn.zTree.init($("#storageLocation-tree"), storageLocationTreeSetting,null);
			$("#storageLocation-button").buttonWidget({
				//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/basefiles/showStorageLocationAddPage.do">
						{
							type:'button-add',
							handler:function(){
								var id = $("#storageLocation-viewid").val();
								//获取下级编码长度
	    						var ret = ajaxCall({id:id,tname:'base_storage_location'},'common/getBaseFilesLevelLength.do');
								var retJson = $.parseJSON(ret);
								if(retJson.len==0){
									$.messager.alert("提醒","已为最大级次,不允许新增!");
									return false;
								}
                                refreshLayout('库位档案【新增】', 'basefiles/showStorageLocationAddPage.do?id='+id);
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/showStorageLocationEditPage.do">
			 			{
				 			type:'button-edit',
				 			handler:function(){
				 				var id = $("#storageLocation-viewid").val();
				 				$.ajax({
						            url :'system/lock/isDoLockData.do',
						            type:'post',
						            data:{id:id,tname:'t_base_storage_location'},
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
                                refreshLayout('库位档案【修改】', 'basefiles/showStorageLocationEditPage.do?id='+id);
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/addStorageLocationHold.do">
			 			{
			 				type:'button-hold',
			 				handler:function(){
				 				var type = $("#storageLocation-button").buttonWidget("getOperType");
				 				if(type=="add"){
				 					//暂存
				 					$("#storageLocation-form-add").attr("action", "basefiles/addStorageLocationHold.do");
				 					$("#storageLocation-form-add").submit();
				 				}else if(type=="edit"){
				 					//暂存
				 					$("#storageLocation-form-edit").attr("action", "basefiles/editStorageLocationHold.do");
				 					$("#storageLocation-form-edit").submit();
				 				}
				 			}
				 		},
			 		</security:authorize>
					<security:authorize url="/basefiles/addStorageLocationSave.do">
			 			{
				 			type:'button-save',
				 			handler:function(){
				 				var type = $("#storageLocation-button").buttonWidget("getOperType");
				 				if(type=="add"){
				 					//保存
				 					$("#storageLocation-form-add").attr("action", "basefiles/addStorageLocationSave.do");
				 					$("#storageLocation-form-add").submit();
				 				}else if(type=="edit"){
				 					//暂存
				 					$("#storageLocation-form-edit").attr("action", "basefiles/editStorageLocationSave.do");
				 					$("#storageLocation-form-edit").submit();
				 				}
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/deleteStorageLocation.do">
			 			{
				 			type:'button-delete',
				 			handler:function(){
				 				var id = $("#storageLocation-viewid").val();
				 				var isParent = $("#storageLocation-isParent").val();
								if(isParent=='1'){
									$.messager.alert("提醒","请先删除所有子节点后再删除该节点！");
									return false;
								}
				 				var url = 'basefiles/deleteStorageLocation.do?id='+id;
				 				$.ajax({   
						            url :'system/lock/isLockData.do',
						            type:'post',
						            data:{id:id,tname:'t_base_storage_location'},
						            dataType:'json',
						            async: false,
						            success:function(json){
						            	flag = json.flag
						            }
						        });
				 				if(flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
				 					return false;
				 				}
				 				$.messager.confirm("提醒", "是否删除该库位档案?", function(r){
									if (r){
										loading("删除中..");
										$.ajax({   
								            url :url,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	if(json.flag==true){
								            		$.messager.alert("提醒","删除成功！");
								            		var treeObj = $.fn.zTree.getZTreeObj("storageLocation-tree");
													var node = treeObj.getNodeByParam("id", id, null);
													treeObj.removeNode(node); //删除子节点
                                                    var pid = $("#storageLocation-viewpid").val();
                                                    var snode = treeObj.getNodeByParam("id", pid, null);
                                                    treeObj.selectNode(snode, false); //选中节点
                                                    zTreeBeforeClick("storageLocation-tree", snode); //执行点击事件
                                                    if(snode.id != "" && null != snode.id){
                                                        refreshLayout('库位档案【详情】', 'basefiles/showStorageLocationInfoPage.do?id='+snode.id);
                                                    }else{
                                                        refreshLayout("库位档案【新增】","basefiles/showStorageLocationAddPage.do?id="+ pid);
                                                    }
								            	}else{
								            		if(json.delFlag==false){
								            			$.messager.alert("警告","该数据已被引用，不能删除！");
								            		}else{
								            			$.messager.alert("警告","删除失败！");
								            		}
								            	}
								            }
								        });
									}
								});
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/openStorageLocation.do">
			 			{
				 			type:'button-open',
				 			handler:function(){
				 				var id = $("#storageLocation-viewid").val();
				 				var pid = $("#storageLocation-viewpid").val();
				 				var treeObj = $.fn.zTree.getZTreeObj("storageLocation-tree");
								var pnode = treeObj.getNodeByParam("id", pid, null);
								if(pnode.id!='' && pnode.state != "1"){
									$.messager.alert("提醒","上级节点为禁用状态，无法启用该节点！");
									return false;
								}
                                $.messager.confirm("提醒","是否启用该库位",function(r){
                                    if(r){
                                        loading("启用中..");
                                        $.ajax({
                                            url :'basefiles/openStorageLocation.do?id='+id,
                                            type:'post',
                                            dataType:'json',
                                            success:function(json){
                                                loaded();
                                                if(json.flag==true){
                                                    $.messager.alert("提醒","启用成功！");
                                                    var node = treeObj.getNodeByParam("id", id, null);
                                                    node.state = '1';
                                                    treeObj.updateNode(node); //更新子节点
                                                    //按钮点击后 控制按钮状态显示
                                                    $("#sales-buttons-salesArea").buttonWidget("initButtonType", 'view');
                                                    $("#storageLocation-button").buttonWidget("setButtonType","1");
                                                    refreshLayout('库位档案【详情】', 'basefiles/showStorageLocationInfoPage.do?id='+id);
                                                }else{
                                                    $.messager.alert("警告","启用失败！");
                                                }
                                            }
                                        });
                                    }
                                });
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/closeStorageLocation.do">
			 			{
				 			type:'button-close',
				 			handler:function(){
				 				var id = $("#storageLocation-viewid").val();
                                var msg = "";
                                var isParent = $("#storageLocation-isParent").val();
                                if(isParent=='1'){
                                    msg = "禁用该库位，将禁用该节点下所有库位，是否禁用?";
                                }else{
                                    msg = "是否禁用该库位?";
                                }
								$.messager.confirm("提醒",msg,function(r){
						  			if(r){
						  				loading("禁用中..");
						 				$.ajax({   
								            url :'basefiles/closeStorageLocation.do?id='+id,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	if(json.flag==true){
								            		$.messager.alert("提醒","禁用成功！");
								            		//按钮点击后 控制按钮状态显示
				 									$("#storageLocation-button").buttonWidget("setButtonType","0");
				 									//更新树状节点状态
				 									var treeObj = $.fn.zTree.getZTreeObj("storageLocation-tree");
				 									var pnode = treeObj.getNodeByParam("id", id, null);
													pnode.state = '0';
													treeObj.updateNode(pnode); //更新节点
				 									var nodes = treeObj.getNodesByParam("pid", id, null);
				 									for(var i=0;i<nodes.length;i++){ //所有禁用成功的节点都需要更新状态
														var node = nodes[i];
														if(node.state=='1'){
															node.state = '0';
														}
														treeObj.updateNode(node); //更新子节点
													}
                                                    refreshLayout('库位档案【详情】', 'basefiles/showStorageLocationInfoPage.do?id='+id);
								            	}else{
							            			$.messager.alert("警告","禁用失败！");
								            	}
								            }
								        });
						        	}
						  		});
				 			}
			 			},
		 			</security:authorize>
                    <security:authorize url="/basefiles/storageLoctionImport.do">
                    {
                        type:'button-import',//导入
                        attr:{
                            clazz: "storageService",
                            method: "addDRStorageLocationExcel",
                            tn: "t_base_storage_location",
                            module: 'basefiles',
                            pojo: "StorageLocation"
                        }
                    },
                    </security:authorize>
					<security:authorize url="/basefiles/storageLoctionExport.do">
			 			{
				 			type:'button-export',
				 			attr:{
							 	tn: 't_base_storage_location', //表名
							 	name:'库位档案列表'
				 			}
			 			},
		 			</security:authorize>
		 			{}
				],
				model:'base',
				type:'list',
				tname:'t_base_storage_location'
			});
		});
		//ajax调用
		var ajaxCall = function (Data, Action) {
		    var ajax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false
		    })
		    return ajax.responseText;
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
				    		var data=ajaxCall({id:(id+value)},url);
		  					var json = $.parseJSON(data);
	    					if(json.flag == false){
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
	</script>
  </body>
</html>
