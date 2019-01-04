<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>商品分类页面</title>
   	<%@include file="/include.jsp" %>   
  </head>
  
  <body>
  	<input type="hidden" id="wares-thisId-waresClass" />
  	<input type="hidden" id="wares-pId-waresClass" />
  	<input type="hidden" id="wares-isParent-waresClass" />
  	<input type="hidden" id="wares-state-waresClass" />
  	<input type="hidden" id="wares-level-waresClass" />
  	<input type="hidden" id="waresClass-operatree"/>
    <div class="easyui-panel" data-options="fit:true,border:false" style="position:relative">
    	<div id="waresClass" class="easyui-tabs" data-options="fit:true,border:false" style="position:relative">
			<div title="商品分类树状">
				<div class="easyui-layout" data-options="fit:true">
					<div title="" data-options="region:'north',border:false" style="height: 30px;overflow: hidden">
						<div class="buttonBG" id="waresClass-button-layoutTree"></div>
					</div>
					<div title="商品分类" data-options="region:'west',split:true" style="width:200px;overflow: auto;" >
			            <div id="waresClass-tree-waresClass" class="ztree"></div>
			    	</div>
			    	<div title="" data-options="region:'center',border:true">
			    		<div id="waresClass-div-waresClassInfo">
			    		</div>
		    		</div>
				</div>
			</div>
			<div title="商品分类列表" style="padding:2px;height: auto;" >
				<div id="waresClass-iframe-list"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	var waresClass_AjaxConn = function (Data, Action, Str) {
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
	
	//输入最大的字符个数
	$.extend($.fn.validatebox.defaults.rules, {
		maxLen:{
			validator:function(value,param){
				return value.length <= param[0];
			},
			message:'请输入{0}个字符!'
		}
	});

    function zTreeBeforeClick(treeId, treeNode){
        $("#wares-thisId-waresClass").val(treeNode.id);
        $("#wares-pId-waresClass").val(treeNode.parentid);
        $("#wares-state-waresClass").val(treeNode.state);
        $("#wares-level-waresClass").val(treeNode.level);
        if (treeNode.isParent) {
            $("#wares-isParent-waresClass").val("1")
        } else {
            $("#wares-isParent-waresClass").val("0")
        }
    }

    function refreshLayout(title,url){
        $("#waresClass-div-waresClassInfo").panel({
            title:title,
            href:url,
            cache:false,
            closed:true,
            maximized:true
        });
        $("#waresClass-div-waresClassInfo").panel("open");
    }

	//树型
	var waresClassTreeSetting = {
		view: {
			dblClickExpand: false,
			showLine: true,
			showIcon:true,
			expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
		},
		async: {
			enable: true,
			url: "basefiles/getWaresClassTreeList.do",
			autoParam: ["id","parentid", "text","state"]
		},
		data: {
			key:{
				name:"text",
				title:"text"
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
                zTreeBeforeClick(treeId, treeNode);
				if(treeNode.id == ""){
                    refreshLayout('商品分类【新增】','basefiles/showWaresClassAddPage.do');
					$("#waresClass-operatree").attr("value","add");
				}
				else{
                    refreshLayout('商品分类【详情】','basefiles/showWaresClassInfoPage.do?id='+treeNode.id);
				}
				var zTree = $.fn.zTree.getZTreeObj("waresClass-tree-waresClass");
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
		//商品分类树状初始化
		$.fn.zTree.init($("#waresClass-tree-waresClass"), waresClassTreeSetting,null);
		var waresClassTree=$.fn.zTree.getZTreeObj("waresClass-tree-waresClass");
		$("#waresClass-button-layoutTree").buttonWidget({
			//初始默认按钮 根据type对应按钮事件
			initButton:[
				{},
				<security:authorize url="/basefiles/waresClassAddBtn.do">
				{
					type:'button-add',//新增
					handler:function(){
						var url = "";
						if(waresClassTree.getSelectedNodes(true).length == 0){
							url = "basefiles/showWaresClassAddPage.do";
						}
						else{
							var pid = waresClassTree.getSelectedNodes()[0].id;
							var ret=waresClass_AjaxConn({pId:pid},"basefiles/getNextWCLenght.do");
							var json = $.parseJSON(ret);
							if(json.nextLen == 0){
								$.messager.alert("提醒","已为最大级次,不允许新增!");
								return false;
							}
							url="basefiles/showWaresClassAddPage.do?id="+$("#wares-thisId-waresClass").val();//将选中的编码作为上级编码获取本级编码的长度 
						}
                        refreshLayout('商品分类【新增】',url);
						$("#waresClass-operatree").attr("value","add");
					}
				},
				</security:authorize>
				<security:authorize url="/basefiles/waresClassEditBtn.do">
				{
					type:'button-edit',//修改
					handler:function(){
						var flag = isDoLockData(waresClassTree.getSelectedNodes()[0].id,"t_base_goods_waresclass");
						if(!flag){
							$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
							return false;
						}
                        refreshLayout('商品分类【修改】','basefiles/showWaresClassEditPage.do?id='+waresClassTree.getSelectedNodes()[0].id);
						$("#waresClass-operatree").attr("value","edit");
					}
				},
				</security:authorize>
				<security:authorize url="/basefiles/waresClassHoldBtn.do">
				{
					type:'button-hold',//暂存
					handler:function(){
						//获取当前按钮操作的状态。add表示正处于新增页面风格edit表示正处于修改页面风格
			 			var type = $("#waresClass-button-layoutTree").buttonWidget("getOperType");
			 			if(type == "add"){
			 				$.messager.confirm("提醒","是否暂存新增商品分类?",function(r){
		 						if(r){
				 					addWaresClassHold();//暂存新增商品分类
		 						}
		 					});
			 			}
			 			else{
			 				$.messager.confirm("提醒","是否暂存修改商品分类?",function(r){
		 						if(r){
									editWaresClassHold();//暂存修改商品分类
								}
		 					});
			 			}
					}
				},
				</security:authorize>
				<security:authorize url="/basefiles/waresClassSaveBtn.do">
				{
					type:'button-save',//保存
					handler:function(){
						//获取当前按钮操作的状态。add表示正处于新增页面风格edit表示正处于修改页面风格
			 			var type = $("#waresClass-button-layoutTree").buttonWidget("getOperType");
			 			if(type == "add"){
			 				$.messager.confirm("提醒","是否保存新增商品分类?",function(r){
		 						if(r){
				 					addWaresClassSave();//保存新增商品分类
		 						}
		 					});
			 			}
			 			else{
			 				$.messager.confirm("提醒","是否保存修改商品分类?",function(r){
		 						if(r){
									editWaresClassSave();//保存修改商品分类
								}
		 					});
			 			}
					}
				},
				</security:authorize>
				<security:authorize url="/basefiles/waresClassDelBtn.do">
				{
					type:'button-delete',//删除
					handler:function(){
						if(waresClassTree.getSelectedNodes().length == 0){
							$.messager.alert("提醒","请选择要删除的数据!");
							return false;
						}
						var id = waresClassTree.getSelectedNodes(true)[0].id;
						var flag = isLockData(waresClassTree.getSelectedNodes()[0].id,"t_base_goods_waresclass");
						if(flag){
							$.messager.alert("警告","该数据正在被其他人操作，暂不能删除！");
							return false;
						}
						var ret = waresClass_AjaxConn({id:id},'basefiles/isExistChildWCList.do','删除中..');
						var retJson = $.parseJSON(ret);
						if(retJson.flag){//true 存在，false 不存在
							$.messager.alert("提醒","下级中存在商品分类,请先删除下级商品分类!");
							return false;
						}
						else{
							$.messager.confirm("提醒","是否确定删除商品分类?",function(r){
								if(r){
									var jsonStr = waresClass_AjaxConn({id:id},'basefiles/deleteWaresClass.do','删除中..');
									var JSON = $.parseJSON(jsonStr);
									if(JSON.flag){//删除节点 
										$.messager.alert("提醒","删除成功");
                                        var treeObj = $.fn.zTree.getZTreeObj("waresClass-tree-waresClass");
                                        var node = treeObj.getNodeByParam("id", id, null);
                                        treeObj.removeNode(node); //删除子节点
                                        var pid = $("#wares-pId-waresClass").val();
                                        var snode = treeObj.getNodeByParam("id", pid, null);
                                        treeObj.selectNode(snode, false); //选中节点
                                        zTreeBeforeClick("waresClass-tree-waresClass", snode); //执行点击事件
                                        if(snode.id != "" && null != snode.id){
                                            refreshLayout("商品分类【详情】","basefiles/showWaresClassInfoPage.do?id="+ snode.id);
                                        }else{
                                            refreshLayout("商品分类【新增】","basefiles/showWaresClassAddPage.do?id="+ pid);
                                        }
										document.getElementById("waresClass-iframe").contentWindow.$("#waresClass-table-list").datagrid('reload');
										document.getElementById("waresClass-iframe").contentWindow.$("#waresClass-table-list").datagrid('clearSelections');
									}
									else{
										$.messager.alert("提醒",JSON.Mes);
									}
								}
							});
						}
					}
				},
				</security:authorize>
				<security:authorize url="/basefiles/waresClassCopyBtn.do">
				{
					type:'button-copy',//复制
					handler:function(){
                        refreshLayout('商品分类【新增】','basefiles/showWaresClassCopyPage.do?id='+waresClassTree.getSelectedNodes()[0].id);
						$("#waresClass-operatree").attr("value","add");
					}
				},
				</security:authorize>
				<security:authorize url="/basefiles/waresClassOpenBtn.do">
				{
					type:'button-open',//启用
					handler:function(){
						var id = $("#wares-thisId-waresClass").val();
						var pid = $("#wares-pId-waresClass").val();
						var node = waresClassTree.getNodeByParam("id", pid, null);
						if(waresClassTree.getSelectedNodes()[0].length == 0){
							$.messager.alert("提醒","请选择要启用的数据!");
							return false;
						}
						if(node.state == "0"){
							$.messager.alert("提醒","上级节点为禁用状态，无法启用该节点！");
							return false;
						}
						$.messager.confirm("提醒","是否确定启用商品分类?",function(r){
							if(r){
								var id = waresClassTree.getSelectedNodes()[0].id;
								var ret = waresClass_AjaxConn({id:id},'basefiles/enableWaresClass.do','启用中..');
								var retJson = $.parseJSON(ret);
								if(retJson.flag){
									$.messager.alert("提醒","启用成功!");
									$("#waresClass-button-layoutTree").buttonWidget('enableButton','button-add');
									var node = waresClassTree.getNodeByParam("id", id, null);
									node.state = '1';
									waresClassTree.updateNode(node); //更新子节点
									document.getElementById("waresClass-iframe").contentWindow.$("#waresClass-table-list").datagrid('reload');
									$("#waresClass-div-waresClassInfo").panel('refresh','basefiles/showWaresClassInfoPage.do?id='+id);
								}
								else{
									$.messager.alert("提醒","启用失败!");
								}
							}
						});
					}
				},
				</security:authorize>
				<security:authorize url="/basefiles/waresClassCloseBtn.do">
				{
					type:'button-close',//禁用
					handler:function(){
						var str = "是否确定禁用商品分类?";
						if(waresClassTree.getSelectedNodes()[0].length == 0){
							$.messager.alert("提醒","请选择要禁用的数据!");
							return false;
						}
						if(waresClassTree.getSelectedNodes()[0].isParent){
							str = "是否确定禁用商品分类及其下级商品分类?";
						}
						$.messager.confirm("提醒",str,function(r){
							if(r){
								var id = waresClassTree.getSelectedNodes()[0].id;
								var ret = waresClass_AjaxConn({id:id},'basefiles/disableWaresClass.do','禁用中..');
								var retJson = $.parseJSON(ret);
								$.messager.alert("提醒","禁用成功数："+ retJson.successNum + "<br />禁用失败数："+ retJson.failureNum + "<br />不可禁用数："+ retJson.notAllowNum+"<br />被引用数："+ retJson.userNum);
				  		        var treeObj = $.fn.zTree.getZTreeObj("sales-areaTree-salesArea");
								for(var i=0;i<retJson.ids.length;i++){ //所有禁用成功的节点都需要更新状态
									var node = waresClassTree.getNodeByParam("id", retJson.ids[i], null);
									node.state = '0';
									waresClassTree.updateNode(node); //更新子节点
								}
								document.getElementById("waresClass-iframe").contentWindow.$("#waresClass-table-list").datagrid('reload');
								$("#waresClass-div-waresClassInfo").panel('refresh','basefiles/showWaresClassInfoPage.do?id='+id);
							}
						});
					}
				},
				</security:authorize>
				{}
			],
			model:'base',
			type:'list',
			taburl:'/basefiles/showWaresClassPage.do',
			datagrid:'waresClass-table-list',
			tname:'t_base_goods_waresclass',
			id:''
		});
		//加载商品分类列表页面
    	$("#waresClass-iframe-list").panel({  
		    content:'<iframe id="waresClass-iframe" frameborder="0" width="99%" height="99%" scrolling="auto"></iframe>',
		    cache:false,
		    closed:true,
		    title:'',
		    maximized:true
		});
		$("#waresClass-iframe-list").panel("open");
		$("#waresClass-iframe").attr("src","basefiles/showWaresClassListPage.do"); 
		
	});
	</script>
  </body>
</html>
