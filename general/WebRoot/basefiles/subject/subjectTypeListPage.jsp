<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>科目列表页面</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north',border:false">
            <div class="buttonBG" id="basefiles-button-subjectTypeListPage"></div>
        </div>
        <div data-options="region:'center'">
            <table id="basefiles-table-subjectTypeList"></table>
    		<div id="basefiles-table-subjectTypeListBtn" style="padding:2px;height:auto">
    			<form action="" id="basefiles-form-ListQuery" method="post">
	    			<table class="querytable">
	    				<tr>
	    					<td>分类名称：<input type="text" name="name" class="len150"/></td>
	    					<td>分类代码：<input type="text" name="typecode" class="len150"/></td>
   							<td>状态：<select name="state" class="len150">
				   				<option value=""></option>
				   				<option value="1">启用</option>
				   				<option value="0">禁用</option>
				   			</select></td>
				   			<td>
	    						<a href="javaScript:void(0);" id="basefiles-subjectTypeList-query-List" class="button-qr">查询</a>
					    		<a href="javaScript:void(0);" id="basefiles-subjectTypeList-query-reloadList" class="button-qr">重置</a>
	    					</td>	
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    	</div>
    </div>
	<div style="display:none">
    	<div id="basefiles-subjectTypeListPage-dialog-operate"></div>
    </div>
    <script type="text/javascript">
		var footerobject=null;
    	var subjectType_AjaxConnet = function (Data, Action, Str) {
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
		    });
		    return MyAjax.responseText;
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
    	function validLengthAndUsed(len,  initValue, message){ //initValue：修改的时候有初始值，判断是否为初始值，是不进行重复验证，否则修改的时候会提醒初始值重复，这里是不需要验证的。
    		var url="basefiles/subject/isUsedSubjectID.do";
    		$.extend($.fn.validatebox.defaults.rules, {
    			validLengthAndUsed:{
			    	validator:function(value){
			    		var reg=eval("/^[A-Za-z0-9]{"+len+"}$/");//正则表达式使用变量
	  					if(reg.test(value) == true){
	  						if(value == initValue){
	  							return true;
	  						}
				    		var data=subjectType_AjaxConnet({id:value},url);
		  					var json = $.parseJSON(data);
	    					if(json.flag == true){
		    					$.fn.validatebox.defaults.rules.validLengthAndUsed.message = message;
		    					return false;
		    				}else{
	    						return true;
		    				}
	    				}else{
	    					$.fn.validatebox.defaults.rules.validLengthAndUsed.message ='请输入'+len+'位字符!';
	    					return false;
	    				}
			    	},
			    	message:''
			    }
			});
    	}
    	
		//验证名称是否重复
    	function validUsed(initValue, message){
    		var url='basefiles/subject/isUsedSubjectName.do';
    		$.extend($.fn.validatebox.defaults.rules, {
				validUsed:{
			    	validator:function(value,param){
			    		if(value == initValue){
  							return true;
  						}
  						var retName = value;
  						if(value.length <= param[0]){
  							var data=subjectType_AjaxConnet({name: retName,istypehead:'1'},url);
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
		//验证分类代码
    	function validUsedType( initValue, message){
    		var url='basefiles/subject/isUsedSubjectType.do';
    		$.extend($.fn.validatebox.defaults.rules, {
    			validUsedType:{
			    	validator:function(value,param){
			    		if(value == initValue){
  							return true;
  						}
			    		var reg=eval("/^[A-Za-z][A-Za-z0-9_]+$/");//正则表达式使用变量
	  					if(reg.test(value) == true){
	  						if(value.length <= param[0]){
	  							var data=subjectType_AjaxConnet({typecode: value},url);
			  					var json = $.parseJSON(data);
			    				if(json.flag == true){
				   					$.fn.validatebox.defaults.rules.validUsedType.message = message;
				    				return false;
				    			}else{
			    					return true;
				    			}
	  						}else{
	  							$.fn.validatebox.defaults.rules.validUsedType.message = '输入长度过长,请输入{0}个字符';
	  							return false;
	  						}
	  					}
  						else{
  							$.fn.validatebox.defaults.rules.validUsedType.message = '类别代码格式为由字母、数字或下划线组成2位或2位以上且字母开头';
  							return false;
  						}
			    	},
			    	message:''
			    }
			});
    	}
		
		//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var costsFeeListColJson=$("#basefiles-table-subjectTypeList").createGridColumnLoad({
	     	name:'t_base_subject',
	     	frozenCol:[[
				{field:'idok',checkbox:true,isShow:true}
	     	]],
	     	commonCol:[[
	    		{field:'thisid',title:'编码',width:80,sortable:true},
  				{field:'name',title:'科目名称',width:150},
	    		{field:'typecode',title:'类别代码',width:130},
  				{field:'state',title:'状态',width:100,
  					formatter:function(val){
  						if(val=='1'){
  							return '启用';
  						}
  						else
  						{
  							return '禁用';
  						}
  					}
  				},
				{field:'remark',title:'备注',width:150,sortable:true},
				{field:'adduserid',title:'添加者编码',width:80,sortable:true,hidden:true},
				{field:'addusername',title:'添加者姓名',width:80,sortable:true,hidden:true},
				{field:'addtime',title:'制单时间',width:130,sortable:true,hidden:true,
					formatter:function(val,rowData,rowIndex){
						if(val){
							return val.replace(/[tT]/," ");
						}
					}
				}
			]]
	     });
	     
	   
	    function refreshADMOperDialog(title, url){
	    	$('<div id="basefiles-subjectTypeListPage-dialog-operate-content"></div>').appendTo("#basefiles-subjectTypeListPage-dialog-operate");
	   		$('#basefiles-subjectTypeListPage-dialog-operate-content').dialog({  
			    title: title,  
			    width: 400,  
			    height: 350,  
			    closed: true,
			    cache: false,  
			    href: url,
				maximizable:true,
				resizable:true,  
			    modal: true, 
			    onLoad:function(){
	   			},  
			    onClose:function(){
			    	$('#basefiles-subjectTypeListPage-dialog-operate-content').window("destroy");
			    }
			});
			$('#basefiles-subjectTypeListPage-dialog-operate-content').dialog('open');
	   	}

		function doneKeydown(){
            $("#basefiles-code-subjectTypeListPage").bind('keydown',function(e){
                if(e.keyCode==13){
                    $("#basefiles-name-subjectTypeListPage").select();
                    $("#basefiles-name-subjectTypeListPage").focus();
                }
            });
            $("#basefiles-name-subjectTypeListPage").bind('keydown',function(e){
                if(e.keyCode==13){
                    $("#basefiles-seq-subjectTypeListPage").select();
                    $("#basefiles-seq-subjectTypeListPage").focus();
                }
            });
            $("#basefiles-seq-subjectTypeListPage").bind('keydown',function(e){
                if(e.keyCode==13){
                    $("#basefiles-remark-subjectTypeListPage").select();
                    $("#basefiles-remark-subjectTypeListPage").focus();
                }
            });
            $("#basefiles-remark-subjectTypeListPage").bind('keydown',function(e){
                if(e.keyCode==13 && $("#subject-form-add").form('validate')){
                    if($("#subject-form-saveAgain").size() != 0){
                        $("#subject-form-saveAgain").focus();
                    }else if($("#subject-form-save").size() != 0){
                        $("#subject-form-save").focus();
                    }
                }
            });
        }

		$(function(){
			var initQueryJSON=$("#basefiles-form-ListQuery").serializeJSON();
			//回车事件
			controlQueryAndResetByKey("basefiles-subjectTypeList-query-List","basefiles-subjectTypeList-query-reloadList");
			
			//查询
			$("#basefiles-subjectTypeList-query-List").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#basefiles-form-ListQuery").serializeJSON();

	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#basefiles-table-subjectTypeList").datagrid('load',queryJSON);	
			});
			
			//重置按钮
			$("#basefiles-subjectTypeList-query-reloadList").click(function(){
				$("#basefiles-form-ListQuery")[0].reset();
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#basefiles-form-ListQuery").serializeJSON();

	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#basefiles-table-subjectTypeList").datagrid('load',queryJSON);	
			});
			
			//按钮
			$("#basefiles-button-subjectTypeListPage").buttonWidget({
				initButton:[
					{}
				],
				buttons:[
					{},
					<security:authorize url="/basefiles/subject/subjectTypeAddBtn.do">
						{
							id:'button-id-add',
							name:'新增 ',
							iconCls:'button-add',
							handler: function(){
								refreshADMOperDialog("科目分类【新增】", 'basefiles/subject/showSubjectTypeAddPage.do');
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/subject/subjectTypeEditBtn.do">
						{
							id:'button-id-edit',
							name:'修改 ',
							iconCls:'button-edit',
							handler: function(){
								var dataRow=$("#basefiles-table-subjectTypeList").datagrid('getSelected');
								if(dataRow==null || dataRow.id==""){
									$.messager.alert("提醒","请选择相应的科目分类!");
									return false;
								}
								refreshADMOperDialog("科目分类【修改】", "basefiles/subject/showSubjectTypeEditPage.do?id="+ dataRow.id);
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/subject/subjectTypeDeleteBtn.do">
						{
							id:'button-id-delete',
							name:'删除',
							iconCls:'button-delete',
							handler: function(){
								var dataRow=$("#basefiles-table-subjectTypeList").datagrid('getSelected');
								if(dataRow==null || dataRow.id==null || dataRow.id==""){
									$.messager.alert("提醒","请选择相应的科目分类!");
									return false;
								}
								if(dataRow.state=='1'){
									$.messager.alert("提醒","抱歉，启用的科目分类不能删除!");
									return false;
								}
								$.messager.confirm("提醒","是否删除该科目分类信息？",function(r){
									if(r){
										loading("删除中..");
										$.ajax({   
								            url :'basefiles/subject/deleteSubjectType.do?id='+ dataRow.id,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
									            if(json.flag){
								            		$.messager.alert("提醒","删除成功");	 
						        					$("#basefiles-subjectTypeList-query-List").trigger("click");     		
									            }else{
						        	            	if(json.msg){
						        						$.messager.alert("提醒","删除失败,"+json.msg);
						        	            	}else{
						        						$.messager.alert("提醒","删除失败!");
						        	            	}
									            }
											}
								        });
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/subject/subjectTypeOpenBtn.do">
						{
							id:'button-id-enable',
							name:'启用',
							iconCls:'button-open',
							handler: function(){
								var dataRow =  $("#basefiles-table-subjectTypeList").datagrid('getSelected');
								if(dataRow==null){
										$.messager.alert("提醒","请选择启用的科目分类!");
										return false;
									}
									if(dataRow.id == ""){
										$.messager.alert("提醒","抱歉，未能找到要启用的科目分类!");
										return false;
									}
									if(dataRow.state == "1"){
										$.messager.alert("提醒","启用状态不能启用!");
										return false;
									}
									$.messager.confirm("提醒","是否启用科目分类信息?",function(r){
					    				if(r){
											$.ajax({
												url:'basefiles/subject/enableSubjectType.do?id='+dataRow.id,
												type:'post',
												dataType:'json',
												success:function(json){
													if(json.flag==true){
														$.messager.alert("提醒","科目分类启用成功!");
							        					$("#basefiles-subjectTypeList-query-List").trigger("click");
													}else{
														$.messager.alert("提醒","科目分类启用失败 !");
													}
												}
											});
					    				}
					    			});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/subject/subjectTypeCloseBtn.do">
						{
							id:'button-id-disable',
							name:'禁用',
							iconCls:'button-close',
							handler: function(){
								var dataRow =  $("#basefiles-table-subjectTypeList").datagrid('getSelected');
								if(dataRow==null){
										$.messager.alert("提醒","请选择要禁用的科目分类!");
										return false;
								}
								if(dataRow.id == ""){
									$.messager.alert("提醒","抱歉，未能找到要禁用的科目分类!");
									return false;
								}
								if(dataRow.state == "0"){
									$.messager.alert("提醒","禁用状态不能禁用!");
									return false;
								}
								$.messager.confirm("提醒","是否禁用科目分类信息?",function(r){
				    				if(r){
										$.ajax({
											url:'basefiles/subject/disableSubjectType.do?id='+dataRow.id,
											type:'post',
											dataType:'json',
											success:function(json){
												if(json.flag==true){
													$.messager.alert("提醒","科目分类禁用成功!");
						        					$("#basefiles-subjectTypeList-query-List").trigger("click");
												}else{
													$.messager.alert("提醒","科目分类禁用失败 !");
												}
											}
										});
				    				}
								});
							}
						},
					</security:authorize>
					{}
				],
				model: 'bill',
				type: 'list',
				datagrid:'basefiles-table-subjectTypeList',
				tname: 't_base_subject'
			});
     		
     		$("#basefiles-table-subjectTypeList").datagrid({ 
     			authority:costsFeeListColJson,
	  	 		frozenColumns:costsFeeListColJson.frozen,
				columns:costsFeeListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortName:'id',
	  	 		sortOrder:'asc',
	  	 		pagination:true,
		 		idField:'id',
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
				pageSize:100,
				toolbar:'#basefiles-table-subjectTypeListBtn',
				pageList:[30,50,100,200],
				url: 'basefiles/subject/showSubjectTypePageList.do',
		 		queryParams:initQueryJSON,
			    onSelect:function(rowIndex, rowData){
			    	if(rowData.state=="1"){
			    		$("#basefiles-button-subjectTypeListPage").buttonWidget("disableButton", 'button-id-enable');
			    		$("#basefiles-button-subjectTypeListPage").buttonWidget("enableButton", 'button-id-disable');
			    		$("#basefiles-button-subjectTypeListPage").buttonWidget("disableButton", 'button-id-delete');
			    	}else{
			    		$("#basefiles-button-subjectTypeListPage").buttonWidget("enableButton", 'button-id-enable');
			    		$("#basefiles-button-subjectTypeListPage").buttonWidget("disableButton", 'button-id-disable');
			    		$("#basefiles-button-subjectTypeListPage").buttonWidget("enableButton", 'button-id-delete');				    	
			    	}
			    },
		    	onDblClickRow:function(rowIndex, rowData){
			    	refreshADMOperDialog("科目分类【详情】", 'basefiles/subject/showSubjectTypeViewPage.do?id='+rowData.id);
		    	}
			}).datagrid("columnMoving");
		});
    </script>
  </body>
</html>
