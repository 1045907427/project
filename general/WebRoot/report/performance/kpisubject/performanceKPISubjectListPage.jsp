<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>考核指标科目录入页面</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  <%
  	boolean isEdit=false;
  %>
  <security:authorize url="/report/performance/performanceKPISubjectEditBtn.do">
  	<% isEdit=true; %>
  </security:authorize>
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north'" style="height: 30px;">
    		<div id="performance-button-performanceKPISubject"></div>
    	</div>
    	<div data-options="region:'center',border:false" style="border: 0px;">
    		<div id="performance-table-performanceKPISubjectBtn">
    			<form action="" id="performance-form-ListQuery" method="post" style="padding-left: 5px; padding-top: 2px;">
	    			<table cellpadding="0" cellspacing="1" border="0">
	    				<tr>
	    					<td style="padding-left: 10px;">代码:&nbsp;</td>
	    					<td><input type="text" name="code" style="width:120px;height: 20px;"/></td>
   							<td>状态:</td>
							<td><select name="state" style="width:80px;">
				   				<option value=""></option>
				   				<option value="1">启用</option>
				   				<option value="0">禁用</option>
				   			</select></td>
				   			<td>部门:</td>
							<td>
								<input id="performance-form-add-widget-deptid" name="deptid" type="text" style="width: 120px;" />
							</td>
				   			<td>
				   				<a href="javaScript:void(0);" id="performance-performanceKPISubject-query-List" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="[Alt+Q]查询">查询</a>
					    		<a href="javaScript:void(0);" id="performance-performanceKPISubject-query-reloadList" class="easyui-linkbutton" iconCls="icon-reload" plain="true" title="[Alt+R]重置">重置</a>
				   			</td>	
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    		<table id="performance-table-performanceKPISubject"></table>
    		<div style="display:none">
	    		<div id="performance-dialog-operate"></div>
	    	</div>
    	</div>
    	<a href="javaScript:void(0);" id="performance-buttons-exportclick" style="display: none"title="导出">导出</a>
    </div>
    <script type="text/javascript">
		var footerobject=null;
    	var performanceKPISubject_AjaxConn = function (Data, Action, Str) {
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
    	var costsFeeListColJson=$("#performance-table-performanceKPISubject").createGridColumnLoad({
	     	name:'t_report_performance_kpisubject',
	     	frozenCol:[[
				{field:'idok',checkbox:true,isShow:true}
	     	]],
	     	commonCol:[[
	    		{field:'id',title:'编码',width:80,sortable:true,hidden:true},
	    		{field:'code',title:'科目代码',width:130},
  				{field:'name',title:'科目名称',width:150,isShow:true,
					formatter:function(value,rowData,rowIndex){
						if(rowData.code){
		     				return getSysCodeName('kpiScoreIndexSubject',rowData.code);
						}
					} 
		     	},
  				{field:'deptid',title:'部门',width:100,
  	  				formatter:function(val,rowData,rowIndex){
  	  					return rowData.deptname;
  					}
  	  			},
  				{field:'score',title:'总分',width:100},
  				{field:'svalue',title:'每分价值',width:70},
  				{field:'seq',title:'排序',width:60,sortable:true},
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
	   		$('#performance-dialog-operate').dialog({  
			    title: title,  
			    width: 450,  
			    height: 450,  
			    closed: false,  
			    cache: false,  
			    href: url,
				maximizable:true,
				resizable:true,  
			    modal: true, 
			    onLoad:function(){
	   			}
			});
			$('#performance-dialog-operate').dialog('open');
	   	}
	   	
		
		$(function(){

    		//所属部门
    	  	$("#performance-form-add-widget-deptid").widget({
    	  		width:120,
    			name:'t_report_performance_kpisubject',
    			col:'deptid',
    			singleSelect:true,
    			onlyLeafCheck:false,
    			required:true
    		});
			var initQueryJSON=$("#performance-form-ListQuery").serializeJSON();
			//回车事件
			controlQueryAndResetByKey("performance-performanceKPISubject-query-List","performance-performanceKPISubject-query-reloadList");
			
			//查询
			$("#performance-performanceKPISubject-query-List").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#performance-form-ListQuery").serializeJSON();

	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#performance-table-performanceKPISubject").datagrid('load',queryJSON);	
			});
			
			//重置按钮
			$("#performance-performanceKPISubject-query-reloadList").click(function(){
				$("#performance-form-ListQuery")[0].reset();
				$("#performance-table-performanceKPISubject").datagrid('loadData',{total:0,rows:[]});
			});
			
			$("#performance-button-performanceKPISubject").buttonWidget({
     			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{}
	 			],
				buttons:[
					<security:authorize url="/report/performance/performanceKPISubjectAddBtn.do">
					{
						id:'button-id-add',
						name:'新增 ',
						iconCls:'button-add',
						handler:function(){
							refreshLayout('部门考核指标科目【新增】', 'report/performance/showPerformanceKPISubjectAddPage.do');
						}
					},
					</security:authorize>
					<security:authorize url="/report/performance/performanceKPISubjectEditBtn.do">
					{
						id:'button-id-edit',
						name:'修改 ',
						iconCls:'button-edit',
						handler:function(){
							var dataRow=$("#performance-table-performanceKPISubject").datagrid('getSelected');
							if(dataRow==null || dataRow.id==null || dataRow.id==""){
								$.messager.alert("提醒","请选择相应的考核指标科目!");
								return false;
							}
							refreshLayout("部门考核指标科目【修改】", 'report/performance/showPerformanceKPISubjectEditPage.do?id='+dataRow.id);
						}
					},
					</security:authorize>
					<security:authorize url="/report/performance/performanceKPISubjectDelBtn.do">
					{
						id:'button-id-delete',
						name:'删除',
						iconCls:'button-delete',
						handler:function(){
							var rows =  $("#performance-table-performanceKPISubject").datagrid('getChecked');
							if(rows==null || rows.length==0){
								$.messager.alert("提醒","请选择相应的考核指标科目!");
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
								$.messager.alert("提醒","启用的科目考核指标不能被删除，下列选中为已经启用："+errorIdarr.join(","));
								return false;
							}
							$.messager.confirm("提醒","是否确认删除科目考核指标 ?",function(r){
								if(r){
				  				loading();
				  				$.ajax({
							        type: 'post',
							        cache: false,
							        url: 'report/performance/deletePerformanceKPISubjectMore.do',
							        data: {idarrs:idarrs.join(",")},
									dataType:'json',
							        success:function(json){
							        	loaded();
							        	if(json.flag==true){
						  					$.messager.alert("提醒", "删除成功数："+ json.isuccess +"<br />删除失败数："+ json.ifailure );							  					
											$("#performance-table-performanceKPISubject").datagrid('reload');
											$("#performance-table-performanceKPISubject").datagrid('clearSelections');	
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
					<security:authorize url="/report/performance/performanceKPISubjectEnableBtn.do">
					{
						id:'button-id-enable',
						name:'启用',
						iconCls:'icon-remove',
						handler: function(){
							var dataRow =  $("#performance-table-performanceKPISubject").datagrid('getSelected');
							if(dataRow==null){
			  					$.messager.alert("提醒","请选择启用的考核指标科目!");
			  					return false;
			  				}
			  				if(dataRow.state == "1"){
			  					$.messager.alert("提醒","启用状态不能启用!");
			  					return false;
			  				}
			  				$.ajax({
			  					url:'report/performance/enablePerformanceKPISubject.do?id='+dataRow.id,
			  					type:'post',
			  					dataType:'json',
			  					success:function(json){
			  						if(json.flag==true){
			  							$.messager.alert("提醒","考核指标科目启用成功!");
			  							$("#performance-table-performanceKPISubject").datagrid('reload');
			  						}else{
			  							$.messager.alert("提醒","考核指标科目启用失败 !");
			  						}
			  					}
			  				});
						}
					},
					</security:authorize>				
					<security:authorize url="/report/performance/performanceKPISubjectDisableBtn.do">
					{
						id:'button-id-disable',
						name:'禁用',
						iconCls:'icon-remove',
						handler: function(){
							var dataRow =  $("#performance-table-performanceKPISubject").datagrid('getSelected');
							if(dataRow==null){
			  					$.messager.alert("提醒","请选择要禁用的考核指标科目!");
			  					return false;
			  				}
			  				if(dataRow.state == "0"){
			  					$.messager.alert("提醒","禁用状态不能禁用!");
			  					return false;
			  				}
			  				$.ajax({
			  					url:'report/performance/disablePerformanceKPISubject.do?id='+dataRow.id,
			  					type:'post',
			  					dataType:'json',
			  					success:function(json){
			  						if(json.flag==true){
			  							$.messager.alert("提醒","考核指标科目禁用成功!");
			  							$("#performance-table-performanceKPISubject").datagrid('reload');
			  						}else{
			  							$.messager.alert("提醒","考核指标科目禁用失败 !");
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
				datagrid:'performance-table-performanceKPISubject',
				tname:'t_js_departmentcosts_subject',
				id:''
     		});
     		
     		$("#performance-table-performanceKPISubject").datagrid({ 
     			authority:costsFeeListColJson,
	  	 		frozenColumns:costsFeeListColJson.frozen,
				columns:costsFeeListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'部门考核指标科目列表',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortName:'seq',
	  	 		sortOrder:'asc',
	  	 		pagination:true,
		 		idField:'id',
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
				pageSize:20,
				toolbar:'#performance-table-performanceKPISubjectBtn',
				pageList:[10,20,30,50,200],
				url: 'report/performance/showPerformanceKPISubjectPageList.do',
		 		queryParams:initQueryJSON,
			    onSelect:function(rowIndex, rowData){
			    	if(rowData.state=="1"){
			    		$("#performance-button-performanceKPISubject").buttonWidget("disableButton", 'button-id-enable');
			    		$("#performance-button-performanceKPISubject").buttonWidget("enableButton", 'button-id-disable');
			    		$("#performance-button-performanceKPISubject").buttonWidget("disableButton", 'button-id-delete');
			    	}else{
			    		$("#performance-button-performanceKPISubject").buttonWidget("enableButton", 'button-id-enable');
			    		$("#performance-button-performanceKPISubject").buttonWidget("disableButton", 'button-id-disable');
			    		$("#performance-button-performanceKPISubject").buttonWidget("enableButton", 'button-id-delete');				    	
			    	}
			    },
		    	onDblClickRow:function(rowIndex, rowData){
			    	refreshLayout("部门考核指标科目【详情】", 'report/performance/showPerformanceKPISubjectViewPage.do?id='+rowData.id);
		    	}
			}).datagrid("columnMoving");
		});
    </script>
  </body>
</html>
