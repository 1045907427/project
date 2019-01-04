<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>费用科目页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
  	<input type="hidden" id="deptCostsSubject-opera"/>
    <input type="hidden" id="journalsheet-thisId-deptcostssubject" />
  	<input type="hidden" id="journalsheet-parentId-deptcostssubject" />
  	<input type="hidden" id="journalsheet-isParent-deptcostssubject" />
  	<input type="hidden" id="journalsheet-state-deptcostssubject" />
  	<input type="hidden" id="journalsheet-level-deptcostssubject" />
  	<input type="hidden" id="journalsheet-hasLevel-deptcostssubject" value="${len }" />
  	<input type="hidden" id="journalsheet-leaveLen-deptcostssubject" value="${lenStr }" />
    <div class="easyui-layout" title="费用科目" data-options="fit:true" id="journalsheet-layout-deptcostssubject">
    	<div data-options="region:'north',border:false" style="height:30px;overflow: hidden">
    		<div class="buttonBG" id="journalsheet-buttons-deptcostssubject"></div>
    	</div>
    	<div data-options="region:'west',split:true" title="费用科目" style="width:160px;">
    		<div id="journalsheet-Tree-deptcostssubject" class="ztree"></div>
    	</div>
    	<div data-options="region:'center',border:true" title=""></div>
    </div>
    <script type="text/javascript">
    	var deptCostsSubject_lenArr = $("#journalsheet-leaveLen-deptcostssubject").val().split(',');

    	function refreshLayout(title, url){
    		$("#journalsheet-layout-deptcostssubject").layout('remove','center').layout('add',{
				region: 'center',
			    title: title,
			    href:url
			});
    	}
    	var deptCostsSubject_ajaxContent = function (param, url) { //同步ajax
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
				    		var data=deptCostsSubject_ajaxContent({id:(id+value)},url);
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
  							var data=deptCostsSubject_ajaxContent({name: retName},url);
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
    		$("#journalsheet-thisId-deptcostssubject").val(treeNode.id);
			$("#journalsheet-parentId-deptcostssubject").val(treeNode.parentid);
			$("#journalsheet-state-deptcostssubject").val(treeNode.state);
			$("#journalsheet-level-deptcostssubject").val(treeNode.level);
			if (treeNode.isParent) {
				$("#journalsheet-isParent-deptcostssubject").val("1")
			} else {
				$("#journalsheet-isParent-deptcostssubject").val("0")
			}
			return true;
    	}
    	$(function(){
    		//树型
			var deptCostsSubjectTreeSetting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "journalsheet/costsFee/getDeptCostsSubjectTree.do",
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
							refreshLayout("费用科目【新增】",'journalsheet/costsFee/showDeptCostsSubjectAddPage.do');
							$("#deptCostsSubject-opera").val("add");
						}
						else{
							refreshLayout("费用科目【详情】", 'journalsheet/costsFee/showDeptCostsSubjectViewPage.do?id='+ treeNode.id);
							$("#deptCostsSubject-opera").val("view");
						}
						zTreeBeforeClick(treeId, treeNode);
						var zTree = $.fn.zTree.getZTreeObj("journalsheet-Tree-deptcostssubject");
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
			$.fn.zTree.init($("#journalsheet-Tree-deptcostssubject"), deptCostsSubjectTreeSetting,null);
			//按钮
			$("#journalsheet-buttons-deptcostssubject").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/journalsheet/costsFee/deptCostsSubjectAddBtn.do">
						{
							type: 'button-add',
							handler: function(){
								var thisId = $("#journalsheet-thisId-deptcostssubject").val();
								var hasLevel = $("#journalsheet-hasLevel-deptcostssubject").val();
								var level = $("#journalsheet-level-deptcostssubject").val();
								if(level == hasLevel){
									$.messager.alert("警告","该节点已为最大级次,不能再新增子节点!");
									return false;
								}
								refreshLayout("费用科目【新增】", 'journalsheet/costsFee/showDeptCostsSubjectAddPage.do?id='+ thisId);
								$("#deptCostsSubject-opera").val("add");
							}
						},
					</security:authorize>
					<security:authorize url="/journalsheet/costsFee/deptCostsSubjectEditBtn.do">
						{
							type: "button-edit",
							handler: function(){
								var id = $("#journalsheet-thisId-deptcostssubject").val();
								if(id == ""){
									$.messager.alert("提醒","请选择一条需要修改的数据！");
									return false;
								}
								refreshLayout("费用科目【修改】", "journalsheet/costsFee/showDeptCostsSubjectEditPage.do?id="+ id);
								$("#deptCostsSubject-opera").val("edit");
							}
						},
					</security:authorize>
					<security:authorize url="/journalsheet/costsFee/deptCostsSubjectHoldBtn.do">
						{
							type: 'button-hold',
							handler: function(){
								var type = $("#deptCostsSubject-opera").val();
								$.messager.confirm("提醒","确定暂存该费用科目？",function(r){
									if(r){
										if(type == "add"){
											addDeptCostsSubject("hold");
										}
										else{
											editDeptCostsSubject("hold");
										}
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/journalsheet/costsFee/deptCostsSubjectSaveBtn.do">
						{
							type: "button-save",
							handler: function(){
								var type = $("#deptCostsSubject-opera").val();
								$.messager.confirm("提醒","确定保存该费用科目？",function(r){
									if(r){
										if(type == "add"){
											addDeptCostsSubject("save");
										}
										else{
											editDeptCostsSubject("save");
										}
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/journalsheet/costsFee/deptCostsSubjectGiveUpBtn.do">
						{
			 				type:'button-giveup',//放弃 
			 				handler:function(){
				 				var type = $("#deptCostsSubject-opera").val();
				 				if(type=="add"){
				 					$("#journalsheet-buttons-deptcostssubject").buttonWidget("initButtonType","list");
				 					$("#journalsheet-layout-deptcostssubject").layout('remove','center');
				 				}else if(type=="edit"){
				 					var id = $("#journalsheet-thisId-deptcostssubject").val();
				 					var state = $("#journalsheet-state-deptcostssubject").val();
				 					$.ajax({   
							            url :'system/lock/unLockData.do',
							            type:'post',
							            data:{id:id,tname:'t_js_departmentcosts_subject'},
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
					 				refreshLayout("费用科目【详情】","journalsheet/costsFee/showDeptCostsSubjectViewPage.do?id="+ id);
									$("#deptCostsSubject-opera").attr("value","view");
				 				}
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/journalsheet/costsFee/deptCostsSubjectDelBtn.do">
						{
							type: 'button-delete',
							handler: function(){
								var id = $("#journalsheet-thisId-deptcostssubject").val();
								var isParent = $("#journalsheet-isParent-deptcostssubject").val();
								if(isParent == "1"){
									$.messager.alert("提醒","先删除所有子节点后再删除该节点！");
									return false;
								}
								$.messager.confirm("提醒","是否删除该费用科目信息?",function(r){
						  			if(r){
						  				loading("删除中..");
							  			$.ajax({
								  			url:'journalsheet/costsFee/deleteDeptCostsSubject.do',
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
								  		        	var treeObj = $.fn.zTree.getZTreeObj("journalsheet-Tree-deptcostssubject");
													var node = treeObj.getNodeByParam("id", id, null);
													treeObj.removeNode(node); //删除子节点
								  		        	var pid = $("#journalsheet-parentId-deptcostssubject").val();
								  		        	var snode = treeObj.getNodeByParam("id", pid, null);
										  		    treeObj.selectNode(snode, false); //选中节点
										  		    zTreeBeforeClick("journalsheet-Tree-deptcostssubject", snode); //执行点击事件
                                                    if(snode.id != "" && null != snode.id){
                                                        refreshLayout("费用科目【详情】","journalsheet/costsFee/showDeptCostsSubjectViewPage.do?id="+ snode.id);
                                                    }else{
                                                        refreshLayout("费用科目【新增】",'journalsheet/costsFee/showDeptCostsSubjectAddPage.do?id='+ pid);
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
					<security:authorize url="/basefiles/waresClassCopyBtn.do">
						{
							type:'button-copy',//复制
							handler:function(){
								var thisId = $("#journalsheet-thisId-deptcostssubject").val();
								refreshLayout("费用科目【新增】", 'journalsheet/costsFee/showDeptCostsSubjectCopyPage.do?id='+ thisId);
								$("#deptCostsSubject-opera").val("add");
							}
						},
					</security:authorize>
					<security:authorize url="/journalsheet/costsFee/depCostsSubjectEnableBtn.do">
						{
							type: "button-open",
							handler: function(){
								var id = $("#journalsheet-thisId-deptcostssubject").val();
								var pid = $("#journalsheet-parentId-deptcostssubject").val();
								var treeObj = $.fn.zTree.getZTreeObj("journalsheet-Tree-deptcostssubject");
								var node = treeObj.getNodeByParam("id", pid, null);
								if(node.state == "0"){
									$.messager.alert("提醒","上级节点为禁用状态，无法启用该节点！");
									return false;
								}
								$.messager.confirm("提醒","确定启用该费用科目?",function(r){
						  			if(r){
						  				loading("启用中..");
							  			$.ajax({
								  			url:'journalsheet/costsFee/enableDeptCostsSubject.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","启用成功");
								  		        	var treeObj = $.fn.zTree.getZTreeObj("journalsheet-Tree-deptcostssubject");
													var node = treeObj.getNodeByParam("id", id, null);
													node.state = "1";
													treeObj.updateNode(node); //更新子节点
								  		        	var pid = $("#journalsheet-parentId-deptcostssubject").val();
								  		        	$("#journalsheet-state-deptcostssubject").val("1")
								  		        	refreshLayout("费用科目【详情】","journalsheet/costsFee/showDeptCostsSubjectViewPage.do?id="+ id);
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
					<security:authorize url="/journalsheet/costsFee/depCostsSubjectDisableBtn.do">
						{
							type: "button-close",
							handler: function(){
								var id = $("#journalsheet-thisId-deptcostssubject").val();
								$.messager.confirm("提醒","禁用该费用科目，将禁用该节点下所有费用科目，是否禁用？",function(r){
						  			if(r){
						  				loading("禁用中..");
							  			$.ajax({
								  			url:'journalsheet/costsFee/disableDeptCostsSubject.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  		        $.messager.alert("提醒","禁用成功数："+ json.successNum + "<br />禁用失败数："+ json.failureNum + "<br />不可禁用数："+ json.notAllowNum);
								  		        var treeObj = $.fn.zTree.getZTreeObj("journalsheet-Tree-deptcostssubject");
												for(var i=0;i<json.ids.length;i++){ //所有禁用成功的节点都需要更新状态
													var node = treeObj.getNodeByParam("id", json.ids[i], null);
													node.state = "0";
													treeObj.updateNode(node); //更新子节点
													$("#journalsheet-state-deptcostssubject").val("0")
												}
								  		        refreshLayout("费用科目【详情】","journalsheet/costsFee/showDeptCostsSubjectViewPage.do?id="+ id);
								  			}
							  			});
						  			}
						  		});
							}
						},
					</security:authorize>
					<security:authorize url="/journalsheet/costsFee/deptCostsSubjectImportBtn.do">
						{
                            type:'button-import',//导入
                            attr:{
                                clazz: "costsFeeService",
                                method: "addDRDeptCostsSubjectExcel",
                                tn: "t_js_departmentcosts_subject",
                                module: 'journalsheet',
                                pojo: "DeptCostsSubject",
                                onClose:function(){
                                	$("#journalsheet-Tree-deptcostssubject").html();
                        			$.fn.zTree.init($("#journalsheet-Tree-deptcostssubject"), deptCostsSubjectTreeSetting,null);
                                }
                            }
						},
					</security:authorize>
					<security:authorize url="/journalsheet/costsFee/deptCostsSubjectExportBtn.do">
						{
                            type:'button-export',//导出
                            attr:{
                                tn: 't_js_departmentcosts_subject',//表名
                                name:'费用科目列表'
                            }
						},
					</security:authorize>
					{}
				],
				model: 'base',
				type: 'list',
				tname: 't_js_departmentcosts_subject'
			});
    	});

    </script>
  </body>
</html>
