<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>供应商档案添加</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<input type="hidden" id="buy-backid-buySupplierAddPage" value="<c:out value="${id }"></c:out>" />
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north',border:false" style="height:30px;overflow: hidden">
    		<div class="buttonBG" id="buy-buttons-buySupplierPage"></div>
    	</div>
    	<div data-options="region:'center'">
    		<div class="easyui-panel" data-options="fit:true" id="buy-panel-buySupplierPage">
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
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
    	
    	var buySupplier_sort = '${sortid}';
    	var buySupplier_area = '${areaid}';
    	var buySupplier_type = '${type}';
    	buySupplier_type=$.trim(buySupplier_type.toLowerCase());
    	if(buySupplier_type==""){
    		buySupplier_type='add';
    	}
    	var buySupplier_url = "basefiles/buySupplierAddPage.do?sortid="+ buySupplier_sort+"&areaid="+buySupplier_area;
    	if(buySupplier_type == "view"){
    		buySupplier_url = "basefiles/buySupplierViewPage.do?id="+encodeURIComponent($("#buy-backid-buySupplierAddPage").val());
    	}
    	if(buySupplier_type == "edit"){
    		buySupplier_url = "basefiles/buySupplierEditPage.do?id="+encodeURIComponent($("#buy-backid-buySupplierAddPage").val());
    	}
    	if(buySupplier_type == "copy"){
    		buySupplier_url = "basefiles/buySupplierCopyPage.do?id="+encodeURIComponent($("#buy-backid-buySupplierAddPage").val());
    	}
    	$(function(){
    		var title = tabsWindowTitle('/basefiles/buySupplierListPage.do');
    		
    		$("#buy-panel-buySupplierPage").panel({
				href:buySupplier_url,
			    cache:false,
			    maximized:true,
			    border:false
			});
    		//按钮
    		$("#buy-buttons-buySupplierPage").buttonWidget({
    			initButton:[
    				{},
					<security:authorize url="/basefiles/buySupplierAddBtn.do">
						{
	    					type:'button-add',
	    					handler: function(){
	    						$("#buy-panel-buySupplierPage").panel('refresh', 'basefiles/buySupplierAddPage.do');
	    					}
	    				},
					</security:authorize>
    				<security:authorize url="/basefiles/buySupplierEditBtn.do">
						{
	    					type:'button-edit',
	    					handler: function(){
		    					var id = $("#buy-backid-buySupplierAddPage").val();
								if(id == ""){
									return false;
								}
								var flag = isDoLockData(id,"t_base_buy_supplier");
				 				if(!flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
				 					return false;
				 				}
	    						$("#buy-panel-buySupplierPage").panel('refresh', 'basefiles/buySupplierEditPage.do?id='+encodeURIComponent(id));
	    					}
	    				},
					</security:authorize>
    				<security:authorize url="/basefiles/buySupplierHoldBtn.do">
						{
	    					type:'button-hold',
	    					handler: function(){
								$.messager.confirm("提醒","确定暂存该供应商信息？",function(r){
									if(r){
										$("#buy-addType-buySupplierAddPage").val("temp");
										buySupplier_tempSave_form_submit();
										$("#buy-form-buySupplierAddPage").submit();
									}
								});
							}
	    				},
					</security:authorize>
    				<security:authorize url="/basefiles/buySupplierSaveBtn.do">
						{
	    					type:'button-save',
	    					handler: function(){
	    						$.messager.confirm("提醒","确定保存该供应商信息？",function(r){
									if(r){
										$("#buy-addType-buySupplierAddPage").val("real");
										buySupplier_realSave_form_submit();
										$("#buy-form-buySupplierAddPage").submit();
									}
								});
	    					}
	    				},
					</security:authorize>
    				<security:authorize url="/basefiles/buySuppplierGiveUpBtn.do">
						{
	    					type:'button-giveup',//放弃 
			 				handler:function(){
				 				var type = $("#buy-buttons-buySupplierPage").buttonWidget("getOperType");
				 				if(type=="add"){
				 					$("#buy-buttons-buySupplierPage").buttonWidget("initButtonType","list");
				 					if(buySupplier_type == "add"){
				 						$("#buy-panel-buySupplierPage").panel('refresh', 'basefiles/buySupplierPage.do');
				 						top.closeTab('供应商档案新增');
				 					}
				 					else{
				 						$("#buy-panel-buySupplierPage").panel('refresh', 'basefiles/buySupplierPage.do');
				 						top.closeTab('供应商档案复制');
				 					}
				 				}else if(type=="edit"){
			    					var id = $("#buy-backid-buySupplierAddPage").val();
									if(id == ""){
										return false;
									}
				 					var flag = isLockData(id,"t_base_buy_supplier");
					 				if(!flag){
					 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
					 					return false;
					 				}
				 					$("#buy-buttons-buySupplierPage").buttonWidget("initButtonType","view");
				 					//$("#buy-buttons-buySupplierPage").buttonWidget("setButtonType","${state}");
				 					$("#buy-panel-buySupplierPage").panel('refresh', 'basefiles/buySupplierPage.do?type=view&id='+encodeURIComponent(id));
				 					//top.closeTab'供应商档案修改');
				 				}
				 			}
			 			},
					</security:authorize>
		 			<security:authorize url="/basefiles/buySupplierDeleteBtn.do">
						{
	    					type:'button-delete',
	    					handler: function(){
	    						var id = $("#buy-backid-buySupplierAddPage").val();
	    						if(id == ""){
	    							return false;
	    						}
								$.messager.confirm("提醒","是否删除该供应商信息?",function(r){
						  			if(r){
						  				loading("删除中..");
						  				var id = $("#buySupplier-id-buySupplierAddPage").val();
							  			$.ajax({
								  			url:'basefiles/deleteBuySupplier.do',
								  			data:{id:id},
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
								  		        	//buySupplier_RefreshDataGrid();
													if (top.$('#tt').tabs('exists',title)){
									    				tabsWindowURL('/basefiles/buySupplierListPage.do').$("#buy-buySupplierListPage-table").datagrid('reload');
									    			}
									    			var data = $("#buy-buttons-buySupplierPage").buttonWidget("removeData", id);
									    			if(null != data){
									    				$("#buy-panel-buySupplierPage").panel('refresh', 'basefiles/buySupplierViewPage.do?id='+encodeURIComponent(data.id));
									    			}
									    			else{
									    				top.closeNowTab();
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
    				<security:authorize url="/basefiles/buySupplierOpenBtn.do">
						{
	    					type:'button-open',
	    					handler: function(){
	    						var id = $("#buy-backid-buySupplierAddPage").val();
	    						if(id == ""){
	    							return false;
	    						}
								$.messager.confirm("提醒","确定启用该供应商信息?",function(r){
						  			if(r){
						  				loading("启用中..");
							  			$.ajax({
								  			url:'basefiles/openBuySupplier.do',
								  			data:{id:id},
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","启用成功");
			  		      							$("#buy-panel-buySupplierPage").panel('refresh', 'basefiles/buySupplierViewPage.do?id='+ encodeURIComponent(json.backid));
			  		      							buySupplier_RefreshDataGrid();
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
    				<security:authorize url="/basefiles/buySupplierCloseBtn.do">
						{
	    					type:'button-close',
	    					handler: function(){
	    						var id = $("#buy-backid-buySupplierAddPage").val();
	    						if(id == ""){
	    							return false;
	    						}
								$.messager.confirm("提醒","确定禁用该供应商信息?",function(r){
						  			if(r){
						  				loading("禁用中..");
							  			$.ajax({
								  			url:'basefiles/closeBuySupplier.do',
								  			data:{id:id},
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","禁用成功");
			  		      							$("#buy-panel-buySupplierPage").panel('refresh', 'basefiles/buySupplierViewPage.do?id='+ encodeURIComponent(json.backid));
			  		      							buySupplier_RefreshDataGrid();
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
    				<security:authorize url="/basefiles/buySupplierBackBtn.do">
						{
	    					type:'button-back',
	    					handler: function(data){
							   	if(data!=null && data.id!=null && data.id!=""){
		    						$("#buy-backid-buySupplierAddPage").val(data.id);
		    						$("#buy-panel-buySupplierPage").panel('refresh', 'basefiles/buySupplierViewPage.do?id='+ encodeURIComponent(data.id));
							   	}
	    					}
	    				},
					</security:authorize>
    				<security:authorize url="/basefiles/buySupplierNextBtn.do">
						{
	    					type:'button-next',
	    					handler: function(data){
							   	if(data!=null && data.id!=null && data.id!=""){
		    						$("#buy-backid-buySupplierAddPage").val(data.id);
		    						$("#buy-panel-buySupplierPage").panel('refresh', 'basefiles/buySupplierViewPage.do?id='+ encodeURIComponent(data.id));
							   	}
	    					}
	    				},
					</security:authorize>
    				<security:authorize url="/basefiles/buySupplierPreviewBtn.do">
						{
	    					type:'button-preview',
	    					handler: function(data){
							   
	    					}
	    				},
					</security:authorize>
    				<security:authorize url="/basefiles/buySupplierPrintBtn.do">
						{
	    					type:'button-print',
	    					handler: function(data){
							   
	    					}
	    				},
					</security:authorize>
    				{}
    			],
    			model:'base',
    			type:'view',
    			tname:'t_base_buy_supplier',
    			taburl:'/basefiles/buySupplierListPage.do',
   				datagrid: 'buy-buySupplierListPage-table',
   				id: $("#buy-backid-buySupplierAddPage").val()
    		});
    	});
    	function buySupplier_RefreshDataGrid(){
    		try{			
				tabsWindowURL('/basefiles/buySupplierListPage.do').$("#buy-buySupplierListPage-table").datagrid('reload');
			}catch(e){
			}
    	}
    	function buySupplier_tempSave_form_submit(){
    		$("#buy-form-buySupplierAddPage").form({
			    onSubmit: function(){  
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		    if(json.flag==true){
		  		    	buySupplier_RefreshDataGrid();
		  		      	$.messager.alert("提醒","暂存成功");
		  		      	$("#buy-backid-buySupplierAddPage").val(json.backid);
		  		      	$("#buy-panel-buySupplierPage").panel('refresh', 'basefiles/buySupplierViewPage.do?id='+ encodeURIComponent(json.backid));
		  		    }
		  		    else{
		  		       	$.messager.alert("提醒","暂存失败");
		  		    }
		  		}
		  	});
    	}
    	function buySupplier_realSave_form_submit(){
    		$("#buy-form-buySupplierAddPage").form({
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
		  		   	if(flag==false){
		  		   		$.messager.alert('提醒',"有必填项未填写!");
		  		   		return false;
		  		   	}
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		    if(json.flag==true){
		  		    	buySupplier_RefreshDataGrid();
		  		      	$.messager.alert("提醒","保存成功");
		  		      	if("${type}" == "add"){
		  		      		$("#buy-buttons-buySupplierPage").buttonWidget("addNewDataId",$("#buy-id-buySupplierAddPage").val());
		  		      	}
		  		      	$("#buy-backid-buySupplierAddPage").val(json.backid);
		  		      	$("#buy-panel-buySupplierPage").panel('refresh', 'basefiles/buySupplierViewPage.do?id='+ encodeURIComponent(json.backid));
		  		    }
		  		    else{
		  		       	$.messager.alert("提醒","保存失败");
		  		    }
		  		}
		  	});
    	}
    	var buySupplier_ajaxContent = function (param, url) { //同步ajax
		    var ajax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: url,
		        data: param,
		        async: false
		    });
		    return ajax.responseText;
		}
		
    	//验证重复
    	$.extend($.fn.validatebox.defaults.rules, {
			validUsed:{
		    	validator:function(value){
		    		var reg=eval("/^[A-Za-z0-9]{0,20}$/");
					if(reg.test(value)){
						var data=buySupplier_ajaxContent({id:value},'basefiles/isBuySupplierIdExist.do');
		  				var json = $.parseJSON(data);
	    				if(json.flag){
		    				$.fn.validatebox.defaults.rules.validUsed.message = "该编号已被使用，请另输入编号！";
		    				return false;
		    			}else{
		    				return true;
		    			}
					}else{
						$.fn.validatebox.defaults.rules.validUsed.message = '请输入不少于20个字符!';
						return false;
					}
		    	},
		    	message:''
		    }
		});
    	
    	function validUsed(url, id, initValue, message){ //initValue：修改的时候有初始值，判断是否为初始值，是不进行重复验证，否则修改的时候会提醒初始值重复，这里是不需要验证的。
    		$.extend($.fn.validatebox.defaults.rules, {
				validUsed:{
			    	validator:function(value){
	  					if(value == initValue){
	  						return true;
	  					}
	  					var reg=eval("/^[A-Za-z0-9]{0,20}$/");
						if(reg.test(value)){
							var data=buySupplier_ajaxContent({id:id},url);
			  				var json = $.parseJSON(data);
		    				if(json.flag == true){
			    				$.fn.validatebox.defaults.rules.validUsed.message = message;
			    				return false;
			    			}else{
			    				return true;
			    			}
						}else{
							$.fn.validatebox.defaults.rules.validUsed.message = '请输入不少于20个字符!';
							return false;
						}
			    	},
			    	message:''
			    }
			});
    	}
    	function isLockData(id,tname){
			var flag = false;
			$.ajax({
	            url :'system/lock/unLockData.do',
	            type:'post',
	            data:{id:id,tname:tname},
	            dataType:'json',
	            async: false,
	            success:function(json){
	            	flag = json.flag
	            }
	        });
	        return flag;
		}
    </script>
  </body>
</html>
