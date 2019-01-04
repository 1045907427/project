<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>科目页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
  	<input type="hidden" id="subject-opera"/>
    <input type="hidden" id="basefiles-thisId-subject" />
  	<input type="hidden" id="basefiles-parentId-subject" />
  	<input type="hidden" id="basefiles-isParent-subject" />
  	<input type="hidden" id="basefiles-state-subject" />
  	<input type="hidden" id="basefiles-level-subject" />
  	<input type="hidden" id="basefiles-hasLevel-subject" value="${len }" />
  	<input type="hidden" id="basefiles-leaveLen-subject" value="${lenStr }" />
  	<div class="easyui-layout" title="科目" data-options="fit:true" id="basefiles-layout-subject">
    	<div data-options="region:'north',border:false" style="height:30px;overflow: hidden">
    		<div class="buttonBG" id="basefiles-buttons-subject"></div>
    	</div>
    	<div data-options="region:'west',split:true" title="科目" style="width:160px;">
    		<div id="basefiles-Tree-subject" class="ztree"></div>
    	</div>
    	<div data-options="region:'center',border:true" title=""></div>
    </div>
    <div style="display:none">
    	
    	<a href="javaScript:void(0);" id="basefiles-subject-buttons-exportclick" style="display: none"title="导出">导出</a>
    	<a href="javaScript:void(0);" id="basefiles-subject-buttons-importclick" style="display: none"title="导入">导入</a>
    </div>
    <script type="text/javascript">
    	var subject_lenArr = $("#basefiles-leaveLen-subject").val().split(',');

        
    	function addSubject(type){
    		if(type == "save"){
        		if(!$("#basefiles-form-subjectAddPage").form('validate')){
        			return false;
        		}
        	}
        	var ret = subject_ajaxContent($("#basefiles-form-subjectAddPage").serializeJSON(),'basefiles/subject/addSubject.do?type='+type);
        	var retJson = $.parseJSON(ret);
        	if(retJson.flag){
        		if(type == "save"){
        			$.messager.alert("提醒","保存成功");
        		}
        		else{
        			$.messager.alert("提醒","暂存成功");
        		}
        		var id = $("#basefiles-thisId-subject").val();
	  		    var pid = $("#basefiles-parentId-subject").val();
	  		    var treeObj = $.fn.zTree.getZTreeObj("basefiles-Tree-subject");
	  		    var node = treeObj.getNodeByParam("id", id, null);
  		      	treeObj.addNodes(node, retJson.node); //增加子节点
	  		    var snode = treeObj.getNodeByParam("id", retJson.node.id, null);
	  		    treeObj.selectNode(snode, false); //选中节点
	  		    zTreeBeforeClick("basefiles-Tree-subject", snode); //执行点击事件
	  		    refreshLayout("任务【详情】",'basefiles/subject/showSubjectViewPage.do?id='+$("#basefiles-id-subjectAddPage").val());
        	}
        	else{
        		if(type == "save"){
        			$.messager.alert("提醒","保存失败");
        		}
        		else{
        			$.messager.alert("提醒","暂存失败");
        		}
        	}
    	}
		
		function  editSubject(type){
        	if(type == "save"){
        		if(!$("#basefiles-form-subjectEditPage").form('validate')){
        			return false;
        		}
        	}
        	loading("提交中..");
        	var ret = subject_ajaxContent($("#basefiles-form-subjectEditPage").serializeJSON(),'basefiles/subject/editSubject.do?type='+type);
        	var retJson = $.parseJSON(ret);
        	loaded();
        	if(retJson.flag){
        		if(type == "save"){
        			$.messager.alert("提醒","保存成功");
        		}
        		else{
        			$.messager.alert("提醒","暂存成功");
        		}
        		//更新所有子节点
       			var map = retJson.nodes;
       			var treeObj = $.fn.zTree.getZTreeObj("basefiles-Tree-subject");
       			for(var key in map){
       				var object = map[key];
       				var node = treeObj.getNodeByParam("id", key, null);
       				node.id = object.id;
					node.text = object.text;
					node.parentid = object.parentid;
					node.state = object.state;
					treeObj.updateNode(node);
       			}
	  		    refreshLayout("科目【详情】",'basefiles/subject/showSubjectViewPage.do?id='+$("#basefiles-id-subjectAddPage").val());
        	}
        	else{
        		if(type == "save"){
        			$.messager.alert("提醒","保存失败");
        		}
        		else{
        			$.messager.alert("提醒","暂存失败");
        		}
        	}
        }
		
    	function refreshLayout(title, url){
    		$("#basefiles-layout-subject").layout('remove','center').layout('add',{
				region: 'center',
			    title: title,
			    href:url
			});
    	}
    	var subject_ajaxContent = function (param, url) { //同步ajax
		    var ajax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: url,
		        data: param,
		        async: false
		    });
		    return ajax.responseText;
		}

		//加载下拉框
		function loaddropdown(){

    		//状态
			$('#common-combobox-state').combobox({
			    url:'common/sysCodeList.do?type=state',
			    valueField:'id',
			    textField:'name'
			});
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

    	//最大长度
    	function maxLen(){ //只验证长度
    		var ch = 0;
    		$.extend($.fn.validatebox.defaults.rules, {
				maxLen:{
			    	validator:function(value,param){
			    		ch = parseInt(param[0]/2);
			    		if(value.length <= param[0]){
			    			return true;
			    		}
			    		else{
			    			$.fn.validatebox.defaults.rules.maxLen.message = "最多只可输入{0}位字符或"+ch+"位汉字!";
			    			return false;
			    		}
			    		return ;
			    	},
			    	message:''
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
				    		var data=subject_ajaxContent({id:(id+value)},url);
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
  							var data=subject_ajaxContent({name: retName,typeid:'${subjectType.id}'},url);
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
    		$("#basefiles-thisId-subject").val(treeNode.id);
			$("#basefiles-parentId-subject").val(treeNode.parentid);
			$("#basefiles-state-subject").val(treeNode.state);
			$("#basefiles-level-subject").val(treeNode.level);
			if (treeNode.isParent) {
				$("#basefiles-isParent-subject").val("1")
			} else {
				$("#basefiles-isParent-subject").val("0")
			}
			return true;
    	}
    	$(function(){
    		//树型
			var subjectTreeSetting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "basefiles/subject/getSubjectTree.do?typeid=${subjectType.id}",
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
							refreshLayout("科目【新增】",'basefiles/subject/showSubjectAddPage.do?id=${subjectType.id}&typeid=${subjectType.id}');
							$("#subject-opera").val("add");
						}
						else if(treeNode.id=='${subjectType.id}'){
							refreshLayout("科目【新增】",'basefiles/subject/showSubjectAddPage.do?id=${subjectType.id}&typeid=${subjectType.id}');
							$("#subject-opera").val("add");
						}else{
							refreshLayout("科目【详情】", 'basefiles/subject/showSubjectViewPage.do?id='+ treeNode.id);
							$("#subject-opera").val("view");
						}
						zTreeBeforeClick(treeId, treeNode);
						var zTree = $.fn.zTree.getZTreeObj("basefiles-Tree-subject");
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
			$.fn.zTree.init($("#basefiles-Tree-subject"), subjectTreeSetting,null);
			//按钮
			$("#basefiles-buttons-subject").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/basefiles/subject/subjectAddBtn.do">
						{
							type: 'button-add',
							handler: function(){
								var thisId = $("#basefiles-thisId-subject").val();
								var hasLevel = $("#basefiles-hasLevel-subject").val();
								var level = $("#basefiles-level-subject").val();
								if(level == hasLevel){
									$.messager.alert("警告","该节点已为最大级次,不能再新增子节点!");
									return false;
								}
								var typeid='${subjectType.id}';
								if(thisId==null || thisId==""){
									thisId=typeid;
								}
								refreshLayout("科目【新增】", 'basefiles/subject/showSubjectAddPage.do?id='+ thisId+"&typeid=${subjectType.id}");
								$("#subject-opera").val("add");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/subject/subjectEditBtn.do">
						{
							type: "button-edit",
							handler: function(){
								var id = $("#basefiles-thisId-subject").val();
								if(id == ""){
									$.messager.alert("提醒","请选择一条需要修改的数据！");
									return false;
								}
								refreshLayout("科目【修改】", "basefiles/subject/showSubjectEditPage.do?id="+ id);
								$("#subject-opera").val("edit");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/subject/subjectHoldBtn.do">
						{
							type: 'button-hold',
							handler: function(){
								var type = $("#subject-opera").val();
								$.messager.confirm("提醒","确定暂存该科目？",function(r){
									if(r){
										if(type == "add"){
											addSubject("hold");
										}
										else{
											editSubject("hold");
										}
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/subject/subjectSaveBtn.do">
						{
							type: "button-save",
							handler: function(){
								var type = $("#subject-opera").val();
								$.messager.confirm("提醒","确定保存该科目？",function(r){
									if(r){
										if(type == "add"){
											addSubject("save");
										}
										else{
											editSubject("save");
										}
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/subject/subjectGiveUpBtn.do">
						{
			 				type:'button-giveup',//放弃 
			 				handler:function(){
				 				var type = $("#subject-opera").val();
				 				if(type=="add"){
				 					$("#basefiles-buttons-subject").buttonWidget("initButtonType","list");
				 					$("#basefiles-layout-subject").layout('remove','center');
				 				}else if(type=="edit"){
				 					var id = $("#basefiles-thisId-subject").val();
				 					var state = $("#basefiles-state-subject").val();
				 					$.ajax({   
							            url :'system/lock/unLockData.do',
							            type:'post',
							            data:{id:id,tname:'t_base_subject'},
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
					 				refreshLayout("科目【详情】","basefiles/subject/showSubjectViewPage.do?id="+ id);
									$("#subject-opera").attr("value","view");
				 				}
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/subject/subjectDeleteBtn.do">
						{
							type: 'button-delete',
							handler: function(){
								var id = $("#basefiles-thisId-subject").val();
								var isParent = $("#basefiles-isParent-subject").val();
								if(isParent == "1"){
									$.messager.alert("提醒","先删除所有子节点后再删除该节点！");
									return false;
								}
								$.messager.confirm("提醒","是否删除该科目信息?",function(r){
						  			if(r){
						  				loading("删除中..");
							  			$.ajax({
								  			url:'basefiles/subject/deleteSubject.do',
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
								  		        	var treeObj = $.fn.zTree.getZTreeObj("basefiles-Tree-subject");
													var node = treeObj.getNodeByParam("id", id, null);
													treeObj.removeNode(node); //删除子节点
								  		        	var pid = $("#basefiles-parentId-subject").val();
								  		        	var snode = treeObj.getNodeByParam("id", pid, null);
										  		    treeObj.selectNode(snode, false); //选中节点
										  		    zTreeBeforeClick("basefiles-Tree-subject", snode); //执行点击事件
                                                    if(snode.id != "" && null != snode.id){
                                                        refreshLayout("科目【详情】","basefiles/subject/showSubjectViewPage.do?id="+ snode.id);
                                                    }else{
                                                        refreshLayout("科目【新增】",'basefiles/subject/showSubjectAddPage.do?id='+ pid);
                                                    }
								  		        }
								  		        else{
								  		        	if(json.msg!=null){
									  		        	$.messager.alert("提醒","删除失败!"+json.msg);								  		        		
								  		        	}else{
								  		        		$.messager.alert("提醒","删除失败");
								  		        	}
								  		        }
								  			}
							  			});
						  			}
						  		});
							}
						},
					</security:authorize>
					<%--
					<security:authorize url="/basefiles/subject/subjectCopyBtn.do">
						{
							type:'button-copy',//复制
							handler:function(){
								var thisId = $("#basefiles-thisId-subject").val();
								refreshLayout("科目【新增】", 'basefiles/subject/showSubjectCopyPage.do?id='+ thisId);
								$("#subject-opera").val("add");
							}
						},
					</security:authorize>
					--%>
					<security:authorize url="/basefiles/subject/subjectOpenBtn.do">
						{
							type: "button-open",
							handler: function(){
								var id = $("#basefiles-thisId-subject").val();
								var pid = $("#basefiles-parentId-subject").val();
								var treeObj = $.fn.zTree.getZTreeObj("basefiles-Tree-subject");
								var node = treeObj.getNodeByParam("id", pid, null);
								if(node.state == "0"){
									$.messager.alert("提醒","上级节点为禁用状态，无法启用该节点！");
									return false;
								}
								$.messager.confirm("提醒","确定启用该科目?",function(r){
						  			if(r){
						  				loading("启用中..");
							  			$.ajax({
								  			url:'basefiles/subject/enableSubject.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","启用成功");
								  		        	var treeObj = $.fn.zTree.getZTreeObj("basefiles-Tree-subject");
													var node = treeObj.getNodeByParam("id", id, null);
													node.state = "1";
													treeObj.updateNode(node); //更新子节点
								  		        	var pid = $("#basefiles-parentId-subject").val();
								  		        	$("#basefiles-state-subject").val("1")
								  		        	refreshLayout("科目【详情】","basefiles/subject/showSubjectViewPage.do?id="+ id);
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
					<security:authorize url="/basefiles/subject/subjectCloseBtn.do">
						{
							type: "button-close",
							handler: function(){
								var id = $("#basefiles-thisId-subject").val();
								$.messager.confirm("提醒","禁用该科目，将禁用该节点下所有科目，是否禁用？",function(r){
						  			if(r){
						  				loading("禁用中..");
							  			$.ajax({
								  			url:'basefiles/subject/disableSubject.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  		        $.messager.alert("提醒","禁用成功数："+ json.successNum + "<br />禁用失败数："+ json.failureNum + "<br />不可禁用数："+ json.notAllowNum);
								  		        var treeObj = $.fn.zTree.getZTreeObj("basefiles-Tree-subject");
												for(var i=0;i<json.ids.length;i++){ //所有禁用成功的节点都需要更新状态
													var node = treeObj.getNodeByParam("id", json.ids[i], null);
													node.state = "0";
													treeObj.updateNode(node); //更新子节点
													$("#basefiles-state-subject").val("0")
												}
								  		        refreshLayout("科目【详情】","basefiles/subject/showSubjectViewPage.do?id="+ id);
								  			}
							  			});
						  			}
						  		});
							}
						},
					</security:authorize>
					{}
				],
				buttons:[
					<security:authorize url="/basefiles/subject/subjectImportBtn.do">
					{
						id:'button-import-excel',
						name:'导入',
						iconCls:'button-import',
						handler: function(){
							<c:if test="${empty(subjectType.id)}">
								$.messager.alert("提醒","抱歉，未找到相关科目分类");
								return true;								
							</c:if>
							var typeid="${subjectType.id}";
							if($.trim(typeid)==""){
								$.messager.alert("提醒","抱歉，未找到相关科目分类");
								return true;
							}

							var typename="${subjectType.name}";
							if(typename==""){
								typename=typeid+" ${subjectType.typecode}";
							}
							var importparam='导入的科目分类为：'+typename+"<br/>";
							importparam=importparam+"<a href=\"basefiles/exceltemplet/SubjectTempletSample.xls\">"+"点击下载导入模板样式</a><br/><br/>";
							$("#basefiles-subject-buttons-importclick").Excel('import',{
								type:'importUserdefined',
								version:'1',
								fieldParam:{typeid:typeid},
						 		importparam:importparam,
					 			url:'basefiles/subject/importSubjectListData.do',
								onClose: function(){ //导入成功后窗口关闭时操作，
                                	$("#basefiles-Tree-subject").html();
                        			$.fn.zTree.init($("#basefiles-Tree-subject"), subjectTreeSetting,null);
								}
							});
							$("#basefiles-subject-buttons-importclick").trigger("click");
						}				
					},
					</security:authorize>					
					<security:authorize url="/basefiles/subject/subjectExportBtn.do">
					{
						id:'button-export-excel',
						name:'导出',
						iconCls:'button-export',
						handler: function(){
							<c:if test="${empty(subjectType.typecode)}">
								$.messager.alert("提醒","抱歉，未找到相关科目分类");
								return true;								
							</c:if>
							var typecode="${subjectType.typecode}";
							if($.trim(typecode)==""){
								$.messager.alert("提醒","抱歉，未找到相关科目分类");
								return true;
							}
							var typename="${subjectType.name}";
							if(typename==""){
								typename=typeid+" ${subjectType.typecode}";
							}
							$("#basefiles-subject-buttons-exportclick").Excel('export',{
								queryForm: "#basefiles-subject-form-ListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
						 		type:'exportUserdefined',
						 		name:'科目分类：'+typename,
								fieldParam:{typecode:typecode,istypehead:0},
						 		url:'basefiles/subject/exportSubjectListData.do'
							});
							$("#basefiles-subject-buttons-exportclick").trigger("click");
						}
					},
					</security:authorize>
					{}
				],
				model: 'base',
				type: 'list',
				tname: 't_base_subject'
			});
    	});

    </script>
  </body>
</html>
