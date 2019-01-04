<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>资金录入页面</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north'" style="height: 30px;">
    		<div id="journalsheet-button-capitalInput"></div>
    	</div>
    	<div data-options="region:'center',border:false" style="border: 0px;">
    		<div id="journalsheet-table-capitalInputBtn" style="padding: 2px;">
    			<form action="" id="capitalInput-form-ListQuery" method="post">
    				<table cellpadding="0" cellspacing="0" border="0">
    					<tr>
    						<td style="padding-left: 10px;">供应商：</td>
    						<td><input id="journalsheet-widget-supplierquery" name="supplierid" type="text" style="width: 320px;"/></td>
    						<td style="padding-left: 10px;">科目名称：</td>
    						<td><input id="journalsheet-widget-subjectidquery" name="subjectid" type="text" style="width: 120px;"/></td>
    						<td style="padding-left: 10px;">所属部门：</td>
    						<td><input id="journalsheet-widget-deptidquery" name="deptid" type="text" style="width: 120px;"/></td>
    					</tr>
    					<tr>
    						<td style="padding-left: 10px;">业务日期：</td>
    						<td><input id="begintime" name="begintime" class="Wdate" style="width:100px;height: 20px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
    						<td style="padding-left: 10px;">到</td>
    						<td><input id="endtime" name="endtime" class="Wdate" style="width:100px;height: 20px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" /></td>
    						<td style="padding-left: 10px;">
    							<a href="javaScript:void(0);" id="capitalInput-query-List" class="easyui-linkbutton" iconCls="icon-search" title="[Alt+Q]查询">查询</a>
					    		<a href="javaScript:void(0);" id="capitalInput-query-reloadList" class="easyui-linkbutton" iconCls="icon-reload" title="[Alt+R]重置">重置</a>
				    			<span id="capitalInput-query-advanced"></span>
    						</td>
    					</tr>
    				</table>
	    		</form>
    		</div>
    		<table id="journalsheet-table-capitalInput"></table>
    		<div id="capitalInput-dialog-operate"></div>
    	</div>
    </div>
    <script type="text/javascript">
    	var capitalInput_AjaxConn = function (Data, Action, Str) {
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
		//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var capitalInputListColJson=$("#journalsheet-table-capitalInput").createGridColumnLoad({
	     	name:'t_js_capitalinput',
	     	frozenCol:[[]],
	     	commonCol:[[
	     		{field:'businessdate',title:'业务日期',width:80,sortable:true},
				{field:'id',title:'编码',width:80,sortable:true,hidden:true},
				{field:'supplierid',title:'供应商编码',width:70,sortable:true},
				{field:'suppliername',title:'供应商名称',width:310,sortable:true},
				{field:'supplierdeptid',title:'所属部门',width:100,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.supplierdeptName;
					}
				},
				{field:'subjectid',title:'科目编码',width:80,sortable:true,hidden:true},
				{field:'subjectname',title:'科目名称',width:80,sortable:true},
				{field:'subjectexpenses',title:'科目费用',width:100,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'remark',title:'备注',width:100,sortable:true},
				{field:'adduserid',title:'制单人编码',width:80,sortable:true,hidden:true},
				{field:'addusername',title:'制单人',width:80,sortable:true,hidden:true},
				{field:'addtime',title:'制单时间',width:130,sortable:true,hidden:true}
			]]
	     });
	     
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
	     function refreshLayout(title, url){
	   		$('#capitalInput-dialog-operate').dialog({  
			    title: title,  
			    width: 500,  
			    height: 280,  
			    closed: false,  
			    cache: false,  
			    href: url,  
			    modal: true
			});
			//$('#capitalInput-dialog-operate').dialog('open');
	   	}
	   	
	   	//通用查询组建调用
		$("#capitalInput-query-advanced").advancedQuery({
			//查询针对的表
	 		name:'journalsheet_capitalInput',
	 		//查询针对的表格id
	 		datagrid:'journalsheet-table-capitalInput'
		});
	   	
	   	$(function(){
	   		//供应商查询
		  	$("#journalsheet-widget-supplierquery").widget({
		  		width:300,
				name:'t_js_capitalinput',
				col:'supplierid',
				singleSelect:true
			});
	   		 //科目查询
		  	$("#journalsheet-widget-subjectidquery").widget({
		  		width:120,
				name:'t_js_capitalinput',
				col:'subjectid',
				singleSelect:true
			});
			
			//回车事件
			controlQueryAndResetByKey("capitalInput-query-List","capitalInput-query-reloadList");
			
	   		//查询
			$("#capitalInput-query-List").click(function(){
				//把form表单的name序列化成journalsheetON对象
	      		var queryJson = $("#capitalInput-form-ListQuery").serializeJSON();
	      		//调用datagrid本身的方法把journalsheetON对象赋给queryParams 即可进行查询
	      		$("#journalsheet-table-capitalInput").datagrid("load",queryJson);
			});
			
			//重置按钮
			$("#capitalInput-query-reloadList").click(function(){
				$("#capitalInput-form-ListQuery")[0].reset();
				$("#journalsheet-widget-supplierquery").widget('clear');
				$("#journalsheet-table-capitalInput").datagrid("load",{});
				
			});
	   		$("#journalsheet-button-capitalInput").buttonWidget({
     			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/journalsheet/capitalinput/capitalInputAddBtn.do">
					{
						type:'button-add',//新增 
						handler:function(){
							refreshLayout('资金录入【新增】', 'journalsheet/capitalinput/capitalInputAddPage.do');
						}
					},
					</security:authorize>
					<security:authorize url="/journalsheet/capitalinput/capitalInputEditBtn.do">
		 			{
			 			type:'button-edit',//修改 
			 			handler:function(){
							var capitalInput=$("#journalsheet-table-capitalInput").datagrid('getSelected');
				  			if(capitalInput==null){
				  				$.messager.alert("提醒","请选择相应的资金录入!");
				  				return false;
				  			}
				  			var flag = isDoLockData(capitalInput.id,"t_js_capitalinput");
							if(!flag){
								$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
								return false;
							}
			     			refreshLayout("资金录入【修改】", 'journalsheet/capitalinput/capitalInputEditPage.do?id='+capitalInput.id);
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/journalsheet/capitalinput/capitalInputDelBtn.do">
		 			{
			 			type:'button-delete',//删除
			 			handler:function(){
			 				var capitalInput=$("#journalsheet-table-capitalInput").datagrid('getSelected');
				  			if(capitalInput==null){
				  				$.messager.alert("提醒","请选择相应的资金录入!");
				  				return false;
				  			}
				  			var flag = isLockData(capitalInput.id,"t_js_capitalinput");
							if(flag){
								$.messager.alert("警告","该数据正在被其他人操作，暂不能删除！");
								return false;
							}
				  			$.messager.confirm("提醒","是否确认删除资金录入?",function(r){
				  				if(r){
				  					var ret = capitalInput_AjaxConn({id:capitalInput.id},'journalsheet/capitalinput/deleteCapitalInput.do','删除中..');
									var retJson = $.parseJSON(ret);
									if(retJson.delFlag){
					  					$.messager.alert("提醒","该信息已被其他信息引用，无法删除！");
					  					return false;
					  				}
									if(retJson.flag){
										$("#journalsheet-table-capitalInput").datagrid('reload');
										$("#journalsheet-table-capitalInput").datagrid('clearSelections');
										$.messager.alert("提醒","删除成功!");
									}
									else{
										$.messager.alert("提醒","删除失败!");
									}
				  				}
				  			});
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/journalsheet/capitalinput/capitalInputImportBtn.do">
		 			{
			 			type:'button-import',//导入
			 			attr:{
			 				clazz: "journalSheetService", //spring中注入的类名
					 		method: "addDRCapitalInput", //插入数据库的方法
					 		tn: "t_js_capitalinput", //表名
				            module: 'journalsheet', //模块名，
					 		pojo: "CapitalInput", //实体类名，将和模块名组合成com.hd.agent.basefiles.model.CapitalInput。
							onClose: function(){ //导入成功后窗口关闭时操作，
						        var queryJSON = $("#capitalInput-form-ListQuery").serializeJSON();
						        $("#journalsheet-table-capitalInput").datagrid("load",queryJSON);
							}
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/journalsheet/capitalinput/capitalInputExportBtn.do">
		 			{
			 			type:'button-export',//导出 
			 			attr:{
							queryForm: "#capitalInput-form-ListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
					 		tn: 't_js_capitalinput', //表名
					 		name:'资金录入列表'
						}
		 			},
		 			</security:authorize>
		 			{}
	 			],
	 			model:'base',
				type:'list',
				tname:'t_js_capitalinput',
				id:''
     		});
     		
     		$("#journalsheet-table-capitalInput").datagrid({ 
     			authority:capitalInputListColJson,
	  	 		frozenColumns:[[]],
				columns:capitalInputListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'资金录入列表',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortName:'businessdate',
	  	 		sortOrder:'asc',
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:true,
				pageSize:20,
				toolbar:'#journalsheet-table-capitalInputBtn',
				pageList:[10,20,30,50,200],
			    url:'journalsheet/capitalinput/getCapitalInputListPage.do',
			    onClickRow:function(rowIndex, rowData){
			    	$("#journalsheet-button-capitalInput").buttonWidget("setDataID", {id:rowData.id, type:'view'});
			    },
		    	onDblClickRow:function(rowIndex, rowData){
	     			refreshLayout("资金录入【详情】", 'journalsheet/capitalinput/capitalInputViewPage.do?id='+rowData.id);
		    	}
			}).datagrid("columnMoving");
	   	});
    </script>
  </body>
</html>
