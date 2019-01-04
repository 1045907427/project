<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>费用录入页面</title>
    <%@include file="/include.jsp" %>
  </head>
  <%
  	boolean isEdit=false;
  %>
  <security:authorize url="/journalsheet/costsFee/costsFeeEditBtn.do">
  	<% isEdit=true; %>
  </security:authorize>
  <body>
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north',border:false">
            <div class="buttonBG" id="costsFee-button-deptCostsSubject"></div>
        </div>
        <div data-options="region:'center'">
            <table id="costsFee-table-deptCostsSubject"></table>
    		<div id="costsFee-table-deptCostsSubjectBtn" style="padding:2px;height:auto">
    			<form action="" id="costsFee-form-ListQuery" method="post">
	    			<table class="querytable">
	    				<tr>
	    					<td>代码：<input type="text" name="code" class="len150"/></td>
   							<td>状态：<select name="state" class="len150">
				   				<option value=""></option>
				   				<option value="1">启用</option>
				   				<option value="0">禁用</option>
				   			</select></td>
				   			<td>
	    						<a href="javaScript:void(0);" id="costsFee-deptCostsSubject-query-List" class="button-qr">查询</a>
					    		<a href="javaScript:void(0);" id="costsFee-deptCostsSubject-query-reloadList" class="button-qr">重置</a>
	    					</td>	
	    				</tr>
	    			</table>
	    		</form>
    		</div>
            <div id="costsFee-dialog-operate"></div>
    	</div>
    	<a href="javaScript:void(0);" id="costsFee-buttons-exportclick" style="display: none"title="导出">导出</a>
    </div>
    <script type="text/javascript">
		var footerobject=null;
    	var deptCostsSubject_AjaxConn = function (Data, Action, Str) {
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
		
		//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var costsFeeListColJson=$("#costsFee-table-deptCostsSubject").createGridColumnLoad({
	     	name:'t_js_departmentcosts_subject',
	     	frozenCol:[[
				{field:'idok',checkbox:true,isShow:true}
	     	]],
	     	commonCol:[[
	    		{field:'id',title:'编码',width:80,sortable:true,hidden:true},
	    		{field:'code',title:'代码',width:130},
  				{field:'name',title:'代码名称',width:150},
  				{field:'seq',title:'排序',width:60,sortable:true},
  				<%--通用版不用
  				{field:'methodtype',title:'计算方式',width:100,
  					formatter:function(val){
  						if(val=='1'){
  							return '录入';
  						}
  						else if(val=='2')
  						{
  							return '销售占比';
  						}
  						else if(val=='3')
  						{
  							return '开单数';
  						}
  						else if(val=='4')
  						{
  							return '资金占用额';
  						}
  					}
  				},
  				{field:'methodviewvalue',title:'计算值',width:100,
  					formatter:function(val,rowData,rowIndex){
						return val;
  					}
  				},
  				{field:'methodviewunit',title:'计算值单位',width:70},
  				{field:'dataedittype',title:'对应值可否编辑',width:100,
  					formatter:function(val){
  						if(val=='1'){
  							return '可编辑';
  						}else{
  	  						return '不可编辑';
  						}
  					}
  				},
  				{field:'assignsupplier',title:'分供应商摊派',width:100,
  					formatter:function(val){
  						if(val=='1'){
  							return '指定摊派';
  						}else{
  	  						return '系统摊派';
  						}
  					}
  				},
  				 --%>
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
	     
	   
	    function refreshLayout(title, url){
	   		$('#costsFee-dialog-operate').dialog({  
			    title: title,  
			    width: 400,  
			    height: 300,  
			    closed: true,
			    cache: false,  
			    href: url,
				maximizable:true,
				resizable:true,  
			    modal: true, 
			    onLoad:function(){
                    $("#journalsheet-code-deptCostsSubject").select();
                    $("#journalsheet-code-deptCostsSubject").focus();
	   			}
			});
			$('#costsFee-dialog-operate').dialog('open');
	   	}

		function doneKeydown(){
            $("#journalsheet-code-deptCostsSubject").bind('keydown',function(e){
                if(e.keyCode==13){
                    $("#journalsheet-name-deptCostsSubject").select();
                    $("#journalsheet-name-deptCostsSubject").focus();
                }
            });
            $("#journalsheet-name-deptCostsSubject").bind('keydown',function(e){
                if(e.keyCode==13){
                    $("#journalsheet-seq-deptCostsSubject").select();
                    $("#journalsheet-seq-deptCostsSubject").focus();
                }
            });
            $("#journalsheet-seq-deptCostsSubject").bind('keydown',function(e){
                if(e.keyCode==13){
                    $("#journalsheet-remark-deptCostsSubject").select();
                    $("#journalsheet-remark-deptCostsSubject").focus();
                }
            });
            $("#journalsheet-remark-deptCostsSubject").bind('keydown',function(e){
                if(e.keyCode==13 && $("#deptCostsSubject-form-add").form('validate')){
                    if($("#deptCostsSubject-form-saveAgain").size() != 0){
                        $("#deptCostsSubject-form-saveAgain").focus();
                    }else if($("#deptCostsSubject-form-save").size() != 0){
                        $("#deptCostsSubject-form-save").focus();
                    }
                }
            });
        }

		$(function(){
			var initQueryJSON=$("#costsFee-form-ListQuery").serializeJSON();
			//回车事件
			controlQueryAndResetByKey("costsFee-deptCostsSubject-query-List","costsFee-deptCostsSubject-query-reloadList");
			
			//查询
			$("#costsFee-deptCostsSubject-query-List").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#costsFee-form-ListQuery").serializeJSON();

	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#costsFee-table-deptCostsSubject").datagrid('load',queryJSON);	
			});
			
			//重置按钮
			$("#costsFee-deptCostsSubject-query-reloadList").click(function(){
				$("#costsFee-form-ListQuery")[0].reset();
				$("#costsFee-table-deptCostsSubject").datagrid('loadData',{total:0,rows:[]});
			});
			
			$("#costsFee-button-deptCostsSubject").buttonWidget({
     			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{}
	 			],
				buttons:[
					<security:authorize url="/journalsheet/costsFee/deptCostsSubjectAddBtn.do">
					{
						id:'button-id-add',
						name:'新增 ',
						iconCls:'button-add',
						handler:function(){
							refreshLayout('日常费用科目【新增】', 'journalsheet/costsFee/showDeptCostsSubjectAddPage.do');
						}
					},
					</security:authorize>
					<security:authorize url="/journalsheet/costsFee/deptCostsSubjectEditBtn.do">
					{
						id:'button-id-edit',
						name:'修改 ',
						iconCls:'button-edit',
						handler:function(){
							var dataRow=$("#costsFee-table-deptCostsSubject").datagrid('getSelected');
							if(dataRow==null || dataRow.id==null || dataRow.id==""){
								$.messager.alert("提醒","请选择相应的费用科目!");
								return false;
							}
							refreshLayout("日常费用科目【修改】", 'journalsheet/costsFee/showDeptCostsSubjectEditPage.do?id='+dataRow.id);
						}
					},
					</security:authorize>
					<security:authorize url="/journalsheet/costsFee/deptCostsSubjectDelBtn.do">
					{
						id:'button-id-delete',
						name:'删除',
						iconCls:'button-delete',
						handler:function(){
							var rows =  $("#costsFee-table-deptCostsSubject").datagrid('getChecked');
							if(rows==null || rows.length==0){
								$.messager.alert("提醒","请选择相应的费用科目!");
								return false;
							}
							var idarrs=new Array();
							var errorIdarr=new Array();
							if(null !=rows && rows.length>0){
					    		for(var i=0;i<rows.length;i++){
						    		if(rows[i].id && rows[i].id!=""){
							    		idarrs.push(rows[i].id);
						    		}
						    		if(rows[i].state!=null){
							    		if(rows[i].state =='1' ){
							    			errorIdarr.push(rows[i].oaid);
							    		}
									}
					    		}
							}
							if(errorIdarr.length>0){
								$.messager.alert("提醒","启用的科目费用不能被删除，下列选中为已经启用："+errorIdarr.join(","));
								return false;
							}
							$.messager.confirm("提醒","是否确认删除科目费用 ?",function(r){
								if(r){
				  				loading();
				  				$.ajax({
							        type: 'post',
							        cache: false,
							        url: 'journalsheet/costsFee/deleteDeptCostsSubjectMore.do',
							        data: {idarrs:idarrs.join(",")},
									dataType:'json',
							        success:function(json){
							        	loaded();
							        	if(json.flag==true){
						  					$.messager.alert("提醒", "删除成功数："+ json.isuccess +"<br />删除失败数："+ json.ifailure );							  					
											$("#costsFee-table-deptCostsSubject").datagrid('reload');
											$("#costsFee-table-deptCostsSubject").datagrid('clearSelections');	
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
					<security:authorize url="/journalsheet/costsFee/depCostsSubjectEnableBtn.do">
					{
						id:'button-id-enable',
						name:'启用',
						iconCls:'button-open',
						handler: function(){
							var dataRow =  $("#costsFee-table-deptCostsSubject").datagrid('getSelected');
							if(dataRow==null){
			  					$.messager.alert("提醒","请选择启用的费用科目!");
			  					return false;
			  				}
			  				if(dataRow.state == "1"){
			  					$.messager.alert("提醒","启用状态不能启用!");
			  					return false;
			  				}
			  				$.ajax({
			  					url:'journalsheet/costsFee/enableDeptCostsSubject.do?id='+dataRow.id,
			  					type:'post',
			  					dataType:'json',
			  					success:function(json){
			  						if(json.flag==true){
			  							$.messager.alert("提醒","费用科目启用成功!");
			  							$("#costsFee-table-deptCostsSubject").datagrid('reload');
			  						}else{
			  							$.messager.alert("提醒","费用科目启用失败 !");
			  						}
			  					}
			  				});
						}
					},
					</security:authorize>				
					<security:authorize url="/journalsheet/costsFee/depCostsSubjectDisableBtn.do">
					{
						id:'button-id-disable',
						name:'禁用',
						iconCls:'button-close',
						handler: function(){
							var dataRow =  $("#costsFee-table-deptCostsSubject").datagrid('getSelected');
							if(dataRow==null){
			  					$.messager.alert("提醒","请选择要禁用的费用科目!");
			  					return false;
			  				}
			  				if(dataRow.state == "0"){
			  					$.messager.alert("提醒","禁用状态不能禁用!");
			  					return false;
			  				}
			  				$.ajax({
			  					url:'journalsheet/costsFee/disableDeptCostsSubject.do?id='+dataRow.id,
			  					type:'post',
			  					dataType:'json',
			  					success:function(json){
			  						if(json.flag==true){
			  							$.messager.alert("提醒","费用科目禁用成功!");
			  							$("#costsFee-table-deptCostsSubject").datagrid('reload');
			  						}else{
			  							$.messager.alert("提醒","费用科目禁用失败 !");
			  						}
			  					}
			  				});
						}
					},
					</security:authorize>
					{}		
				],
	 			model:'bill',
				type:'list',
				datagrid:'costsFee-table-deptCostsSubject',
				tname:'t_js_departmentcosts_subject',
				id:''
     		});
     		
     		$("#costsFee-table-deptCostsSubject").datagrid({ 
     			authority:costsFeeListColJson,
	  	 		frozenColumns:costsFeeListColJson.frozen,
				columns:costsFeeListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortName:'seq',
	  	 		sortOrder:'asc',
	  	 		pagination:true,
		 		idField:'id',
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
				pageSize:100,
				toolbar:'#costsFee-table-deptCostsSubjectBtn',
				pageList:[30,50,100,200],
				url: 'journalsheet/costsFee/showDeptCostsSubjectPageList.do',
		 		queryParams:initQueryJSON,
			    onSelect:function(rowIndex, rowData){
			    	if(rowData.state=="1"){
			    		$("#costsFee-button-deptCostsSubject").buttonWidget("disableButton", 'button-id-enable');
			    		$("#costsFee-button-deptCostsSubject").buttonWidget("enableButton", 'button-id-disable');
			    		$("#costsFee-button-deptCostsSubject").buttonWidget("disableButton", 'button-id-delete');
			    	}else{
			    		$("#costsFee-button-deptCostsSubject").buttonWidget("enableButton", 'button-id-enable');
			    		$("#costsFee-button-deptCostsSubject").buttonWidget("disableButton", 'button-id-disable');
			    		$("#costsFee-button-deptCostsSubject").buttonWidget("enableButton", 'button-id-delete');				    	
			    	}
			    },
		    	onDblClickRow:function(rowIndex, rowData){
			    	refreshLayout("日常费用科目【详情】", 'journalsheet/costsFee/showDeptCostsSubjectViewPage.do?id='+rowData.id);
		    	}
			}).datagrid("columnMoving");
		});
    </script>
  </body>
</html>
