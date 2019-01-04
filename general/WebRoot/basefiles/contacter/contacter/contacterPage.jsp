<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>联系人</title>
    <%@include file="/include.jsp" %>  
  </head>
  <body>
  	<input type="hidden" id="contacter-id" value="<c:out value="${id}"></c:out>"/>
  	<input type="hidden" id="contacter-backid-contacterAddPage" value="<c:out value="${id}"></c:out>" />
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north',border:false" style="height:30px;overflow: hidden;">
    		<div class="buttonBG" id="contacter-buttons-contacterPage"></div>
    	</div>
    	<div data-options="region:'center'">
    		<div class="easyui-panel" data-options="fit:true" id="contacter-panel-contacterPage"></div>
    		<div id="contacter-window-showOldImg"></div>
    	</div>
    </div>
    <script type="text/javascript">
    	var contacter_sort = '${sort}';
    	var contacter_type = '${type}';
    	var contacter_url = "basefiles/contacterAddPage.do?sort="+ contacter_sort;
    	if(contacter_type == "view"){
    		contacter_url = "basefiles/contacterViewPage.do?id="+$("#contacter-id").val();
    	}
    	if(contacter_type == "edit"){
    		contacter_url = "basefiles/contacterEditPage.do?id="+$("#contacter-id").val();
    	}
    	if(contacter_type == "copy"){
    		contacter_url = "basefiles/contacterCopyPage.do?id="+$("#contacter-id").val();
    	}
    	$(function(){
    		$("#contacter-panel-contacterPage").panel({
				href:contacter_url,
			    cache:false,
			    maximized:true,
			    border:false
			});
    		//按钮
    		$("#contacter-buttons-contacterPage").buttonWidget({
    			initButton:[
    				{},
					<security:authorize url="/basefiles/contacterAdd.do">
						{
	    					type:'button-add',
	    					handler: function(){
	    						$("#contacter-panel-contacterPage").panel('refresh', 'basefiles/contacterAddPage.do');
	    					}
	    				},
					</security:authorize>
    				<security:authorize url="/basefiles/contacterEdit.do">
						{
	    					type:'button-edit',
	    					handler: function(){
	    						var id = $("#contacter-backid-contacterAddPage").val();
	    						if(id == ""){
	    							return false;
	    						}
	    						$("#contacter-panel-contacterPage").panel('refresh', 'basefiles/contacterEditPage.do?id='+ id);
	    					}
	    				},
					</security:authorize>
    				<security:authorize url="/basefiles/contacterHold.do">
						{
	    					type:'button-hold',
	    					handler: function(){
								$.messager.confirm("提醒","确定暂存该联系人信息？",function(r){
									if(r){
										$("#contacter-addType-contacterAddPage").val("temp");
										if($("#contacter-sortList-contacterAddPage").hasClass("create-datagrid")){
											var rows = $("#contacter-sortList-contacterAddPage").datagrid('getChanges');
											if(rows.length > 0){
												$("#contacter-sortEdit-contacterAddPage").val("1");
											}
										}
										contacter_tempSave_form_submit();
										$("#contacter-form-contacterAddPage").submit();
									}
								});
							}
	    				},
					</security:authorize>
    				<security:authorize url="/basefiles/contacterSave.do">
						{
	    					type:'button-save',
	    					handler: function(){
	    						$.messager.confirm("提醒","确定保存该联系人信息？",function(r){
									if(r){
										$("#contacter-addType-contacterAddPage").val("real");
										if($("#contacter-sortList-contacterAddPage").hasClass("create-datagrid")){
											var rows = $("#contacter-sortList-contacterAddPage").datagrid('getChanges');
											if(rows.length > 0){
												$("#contacter-sortEdit-contacterAddPage").val("1");
											}
										}
										contacter_realSave_form_submit();
										$("#contacter-form-contacterAddPage").submit();
									}
								});
	    					}
	    				},
					</security:authorize>
    				<security:authorize url="/basefiles/contacterDelete.do">
						{
	    					type:'button-delete',
	    					handler: function(){
	    						var id = $("#contacter-backid-contacterAddPage").val();
	    						if(id == ""){
	    							return false;
	    						}
								$.messager.confirm("提醒","是否删除该联系人分类信息?",function(r){
						  			if(r){
						  				loading("删除中..");
							  			$.ajax({
								  			url:'basefiles/deleteContacter.do',
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
								  		        	$("#contacter-panel-contacterPage").panel('refresh', 'basefiles/contacterAddPage.do');
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
    				<security:authorize url="/basefiles/contacterOpen.do">
						{
	    					type:'button-open',
	    					handler: function(){
	    						var id = $("#contacter-backid-contacterAddPage").val();
	    						if(id == ""){
	    							return false;
	    						}
								$.messager.confirm("提醒","确定启用该联系人信息?",function(r){
						  			if(r){
						  				loading("启用中..");
							  			$.ajax({
								  			url:'basefiles/openContacter.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","启用成功");
			  		      							$("#contacter-panel-contacterPage").panel('refresh', 'basefiles/contacterViewPage.do?id='+ json.backid);
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
    				<security:authorize url="/basefiles/contacterClose.do">
						{
	    					type:'button-close',
	    					handler: function(){
	    						var id = $("#contacter-backid-contacterAddPage").val();
	    						if(id == ""){
	    							return false;
	    						}
								$.messager.confirm("提醒","确定禁用该联系人信息?",function(r){
						  			if(r){
						  				loading("禁用中..");
							  			$.ajax({
								  			url:'basefiles/closeContacter.do',
								  			data:'id='+id,
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","禁用成功");
			  		      							$("#contacter-panel-contacterPage").panel('refresh', 'basefiles/contacterViewPage.do?id='+ json.backid);
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
    				<security:authorize url="/basefiles/contacterBack.do">
						{
	    					type:'button-back',
	    					handler: function(data){
	    						$("#contacter-backid-contacterAddPage").val(data.id);
	    						$("#contacter-panel-contacterPage").panel('refresh', 'basefiles/contacterViewPage.do?id='+ data.id);
	    					}
	    				},
					</security:authorize>
    				<security:authorize url="/basefiles/contacterNext.do">
						{
	    					type:'button-next',
	    					handler: function(data){
	    						$("#contacter-backid-contacterAddPage").val(data.id);
	    						$("#contacter-panel-contacterPage").panel('refresh', 'basefiles/contacterViewPage.do?id='+ data.id);
	    					}
	    				},
					</security:authorize>
    				<security:authorize url="/basefiles/contacterGiveUp.do">
						{
	    					type:'button-preview',
	    					handler: function(data){
	    					}
	    				},
					</security:authorize>
    				<security:authorize url="/basefiles/contacterPrint.do">
						{
	    					type:'button-print',
	    					handler: function(data){
		    				}
	    				},
					</security:authorize>
    				<security:authorize url="/basefiles/contacterGiveUp.do">
						{
	    					type:'button-giveup',
	    					handler:function(){
	    						var type = $("#contacter-buttons-contacterPage").buttonWidget("getOperType");
	    						if(type == "add"){
	    							var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
	    							top.closeTab(currTitle);
	    						}
	    						else if(type == "edit"){
		    						var id = $("#contacter-backid-contacterAddPage").val();
		    						if(id == ""){
		    							return false;
		    						}
	    							$("#contacter-panel-contacterPage").panel('refresh', 'basefiles/contacterViewPage.do?id='+ id);
	    						}
	    					}
	    				},
					</security:authorize>
    				{}
    			],
    			model:'base',
    			type:'view',
    			tname:'t_base_linkman_info',
				taburl: '/basefiles/contacterListPage.do',
				datagrid: 'contacter-datagrid-contacterListPage',
				id: $("#contacter-id").val()
    		});
    	});
    	function contacter_tempSave_form_submit(){
    		$("#contacter-form-contacterAddPage").form({
			    onSubmit: function(){  
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		  	if(json.lock == true){
		  		  		$.messager.alert("提醒","其他用户正在修改该数据，无法修改");
		  		  		return false;
		  		  	}
		  		    if(json.flag==true){
		  		      	$.messager.alert("提醒","暂存成功");
		  		      	$("#contacter-backid-contacterAddPage").val(json.backid);
		  		      	if(json.type == "add"){
		  		      		$("#contacter-buttons-contacterPage").buttonWidget("addNewDataId", json.backid);
		  		      	}
		  		      	$("#contacter-panel-contacterPage").panel('refresh', 'basefiles/contacterViewPage.do?id='+ json.backid);
		  		    }
		  		    else{
		  		       	$.messager.alert("提醒","暂存失败");
		  		    }
		  		}
		  	});
    	}
    	function contacter_realSave_form_submit(){
    		$("#contacter-form-contacterAddPage").form({
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
		  		   	if(flag==false){
		  		   		return false;
		  		   	}
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		  	if(json.lock == true){
		  		  		$.messager.alert("提醒","其他用户正在修改该数据，无法修改");
		  		  		return false;
		  		  	}
		  		    if(json.flag==true){
		  		      	$.messager.alert("提醒","保存成功");
		  		      	$("#contacter-backid-contacterAddPage").val(json.backid);
		  		      	if(json.type == "add"){
		  		      		$("#contacter-buttons-contacterPage").buttonWidget("addNewDataId", json.backid);
		  		      	}
		  		      	$("#contacter-panel-contacterPage").panel('refresh', 'basefiles/contacterViewPage.do?id='+ json.backid);
		  		    }
		  		    else{
		  		       	$.messager.alert("提醒","保存失败");
		  		    }
		  		}
		  	});
    	}
    	var contacter_ajaxContent = function (param, url) { //同步ajax
		    var ajax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: url,
		        data: param,
		        async: false
		    });
		    return ajax.responseText;
		}
		
		$.extend($.fn.validatebox.defaults.rules, {
			validUsed:{
		    	validator:function(value){
  					var reg=eval("/^[A-Za-z0-9]{0,20}$/");
					if(reg.test(value)){
						var data=contacter_ajaxContent({id: value},'basefiles/contacterNoUsed.do');
		  				var json = $.parseJSON(data);
	    				if(json.flag == true){
		    				$.fn.validatebox.defaults.rules.validUsed.message = "该编号已被使用，请另输入编号!";
		    				return false;
		    			}else{
	    					return true;
		    			}
					}else{
						$.fn.validatebox.defaults.rules.validUsed.message = '请输入不大于20个字符!';
						return false;
					}
		    	},
		    	message:''
		    }
		});
		
    	//验证重复
    	function validUsed(url, id, initValue, message){ //initValue：修改的时候有初始值，判断是否为初始值，是不进行重复验证，否则修改的时候会提醒初始值重复，这里是不需要验证的。
    		$.extend($.fn.validatebox.defaults.rules, {
				validUsed:{
			    	validator:function(value){
	  					if(value == initValue){
	  						return true;
	  					}
	  					var reg=eval("/^[A-Za-z0-9]{0,20}$/");
						if(reg.test(value)){
							var data=contacter_ajaxContent({id: value},url);
			  				var json = $.parseJSON(data);
		    				if(json.flag == true){
			    				$.fn.validatebox.defaults.rules.validUsed.message = message;
			    				return false;
			    			}else{
		    					return true;
			    			}
						}else{
							$.fn.validatebox.defaults.rules.validUsed.message = '请输入不大于20个字符!';
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
