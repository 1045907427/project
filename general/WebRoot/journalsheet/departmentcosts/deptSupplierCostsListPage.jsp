<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>部门费用分供应商摊派页面</title>
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
    	<div data-options="region:'north'" style="height: 30px;">
    		<div id="departmentCosts-button-deptSupplierCosts"></div>
    	</div>
    	<div data-options="region:'center',border:false" style="border: 0px;">
    		<div id="departmentCosts-table-deptSupplierCostsBtn">
    			<form action="" id="departmentCosts-form-ListQuery" method="post" style="padding-left: 5px; padding-top: 2px;">
	    			<table cellpadding="0" cellspacing="1" border="0">
	    				<tr>
	    					<td style="padding-left: 10px;">业务日期:&nbsp;</td>
	    					<td><input id="begintime" name="begintime" value="${yearfirstmonth }"  class="Wdate" style="width:100px;height: 20px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM',maxDate:'${yearcurmonth }'})"/>
   								到&nbsp<input id="endtime" name="endtime" value="${yearcurmonth }"  class="Wdate" style="width:100px;height: 20px;" onclick="WdatePicker({'dateFmt':'yyyy-MM',maxDate:'${yearcurmonth }'})"  />
	    					</td>
	    					<td style="padding-left: 10px;">所属部门:&nbsp;</td>
	    					<td><input id="departmentCosts-widget-deptidquery" name="deptid" type="text" style="width: 120px;"/></td>
	    				</tr>
	    				<tr>	    					
	    					<td>状态:</td>
		    				<td>
		    					<select id="departmentCosts-form-isAudit" name="isAudit" style="width:100px;">
		    						<option></option>
		    						<option value="1" selected="selected">未审核</option>
		    						<option value="2">已审核</option>
		    					</select>
		    				</td>	    					
	    					<td colspan="2">
	    						<a href="javaScript:void(0);" id="departmentCosts-query-List" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="[Alt+Q]查询">查询</a>
					    		<a href="javaScript:void(0);" id="departmentCosts-query-reloadList" class="easyui-linkbutton" iconCls="icon-reload" plain="true" title="[Alt+R]重置">重置</a>
				    			<span id="departmentCosts-query-advanced"></span>
	    					</td>
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    		<table id="departmentCosts-table-deptSupplierCosts"></table>
    		<div style="display:none">
	    		<div id="departmentCosts-dialog-operate"></div>
	    		<div id="deptSupplierCosts-dialog-operate"></div>
	    	</div>
    	</div>
    	<a href="javaScript:void(0);" id="departmentCosts-buttons-exportclick" style="display: none"title="导出">导出</a>
    	<a href="javaScript:void(0);" id="departmentCosts-buttons-importclick" style="display: none"title="导入">导入</a>
    </div>
    <script type="text/javascript">
		var footerobject=null;
    	var departmentCosts_AjaxConn = function (Data, Action, Str) {
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
    	var costsFeeListColJson=$("#departmentCosts-table-deptSupplierCosts").createGridColumnLoad({
	     	name:'t_js_departmentcosts',
	     	frozenCol:[[
				{field:'idok',checkbox:true,isShow:true}
	     	]],
	     	commonCol:[[
	    		{field:'id',title:'编码',sortable:true},
	     		{field:'businessdate',title:'业务日期',width:80,sortable:true},
				{field:'deptid',title:'所属部门',width:70,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.deptname;
					}
				},
				{field:'status',title:'状态',width:70,sortable:true,
					formatter:function(val,rowData,rowIndex){
						if(val=='2'){
							return "保存";
						}else if(val=='3'){
							return "已审核";
						}
					}
				},
				<c:forEach items="${deptCostsSubjectList }" var="list">
				  {field:'deptCostsSubject_${list.code}',title:'${list.name}<c:if test="${list.state !=1}">(<b style="color:#f00">禁用</b>)</c:if>',align:'right',resizable:true,isShow:true,
				  	formatter:function(value,rowData,rowIndex){
		  				return formatterMoney(value);	        		
		        	},
		        	styler:function(value,row,index){
		        		<c:if test="${list.state !=1}">
		        		return 'background-color:#ffee00;color:red;';
		        		</c:if>
		        	}
				  },
				</c:forEach>
				{field:'subjectid',title:'科目名称',width:60,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.subjectname;
					}
				},
				{field:'amount',title:'费用',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'remark',title:'备注',width:80,sortable:true},
				{field:'adduserid',title:'制单人编码',width:80,sortable:true,hidden:true},
				{field:'addusername',title:'制单人',width:80,sortable:true,hidden:true},
				{field:'adddeptid',title:'制单部门',width:70,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.adddeptname;
					}
				},
				{field:'addtime',title:'制单时间',width:130,sortable:true,hidden:true,
					formatter:function(val,rowData,rowIndex){
						if(val){
							return val.replace(/[tT]/," ");
						}
					}
				},
				{field:'sourcefrome',title:'数据来源',resizable:true,hidden:true,
					formatter:function(val,rowData,rowIndex){
						if(val=="0"){
							return "手动添加";
						}else if(val=="1"){
							return "导入";
						}
					}
				}
			]]
	     });
	    
	    function showDeptCostsInitAddDialog(title, url){
	   		$('#departmentCosts-dialog-operate').dialog({  
			    title: title,  
			    width: 450,  
			    height: 180,  
			    closed: true,
			    cache: false,  
			    href: url,
				maximizable:true,
				resizable:true,  
			    modal: true, 
			    onLoad:function(){
   					var inputs=$("input[inputdata='deptCostsSubject'][tabindex='1']");
   					if(inputs && inputs.size()>0){
   						inputs.focus();
   						inputs.select();
   					}
	   			}
			});
			$('#departmentCosts-dialog-operate').dialog('open');
	   	}
	    function showDeptCostsDialog(title, url){
	   		$('#departmentCosts-dialog-operate').dialog({  
			    title: title,  
			    width: 680,  
			    height: 450,  
			    closed: true,
			    cache: false,  
			    href: url,
				maximizable:true,
				resizable:true,  
			    modal: true, 
			    onLoad:function(){
   					var inputs=$("input[inputdata='deptCostsSubject'][tabindex='1']");
   					if(inputs && inputs.size()>0){
   						inputs.focus();
   						inputs.select();
   					}
	   			}
			});
			$('#departmentCosts-dialog-operate').dialog('open');
	   	}
	   	
		//通用查询组建调用
		$("#departmentCosts-query-advanced").advancedQuery({
			//查询针对的表
	 		name:'t_js_departmentcosts',
	 		//查询针对的表格id
	 		datagrid:'departmentCosts-table-deptSupplierCosts'
		});
		
		$(function(){

		  //所属部门查询
		  	$("#departmentCosts-widget-deptidquery").widget({
		  		width:200,
				name:'t_js_departmentcosts',
				col:'deptid',
				singleSelect:true,
				initSelectNull:true
			});
			
			//回车事件
			controlQueryAndResetByKey("departmentCosts-query-List","departmentCosts-query-reloadList");
			
			//查询
			$("#departmentCosts-query-List").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#departmentCosts-form-ListQuery").serializeJSON();
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#departmentCosts-table-deptSupplierCosts").datagrid({
	      			url: 'journalsheet/costsFee/getDepartmentCostsPageList.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			
			//重置按钮
			$("#departmentCosts-query-reloadList").click(function(){
				$("#departmentCosts-form-ListQuery")[0].reset();
				$("#departmentCosts-table-deptSupplierCosts").datagrid('loadData',{total:0,rows:[]});
				$("#departmentCosts-widget-supplierquery").widget('clear');
				$("#departmentCosts-widget-subjectidquery").widget('clear');
				$("#departmentCosts-widget-customerquery").customerWidget('clear');
			});
			
			$("#departmentCosts-button-deptSupplierCosts").buttonWidget({
     			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{}
	 			],
				buttons:[
					<security:authorize url="/journalsheet/costsFee/departmentCostsAddBtn.do">
					{
						id:'button-id-add',
						name:'新增 ',
						iconCls:'button-add',
						handler:function(){
							showDeptCostsInitAddDialog('部门费用【新增】', 'journalsheet/costsFee/showDepartmentCostsAddPage.do');
							
						}
					},
					</security:authorize>
					<security:authorize url="/journalsheet/costsFee/departmentCostsEditBtn.do">
					{
						id:'button-id-edit',
						name:'修改 ',
						iconCls:'button-edit',
						handler:function(){
							var dataRow=$("#departmentCosts-table-deptSupplierCosts").datagrid('getSelected');
							if(dataRow==null){
								$.messager.alert("提醒","请选择相应的部门费用!");
								return false;
							}
							showDeptCostsDialog("部门费用【修改】", 'journalsheet/costsFee/showDepartmentCostsEditPage.do?id='+dataRow.id);
						}
					},
					</security:authorize>
					<security:authorize url="/journalsheet/costsFee/auditDepartmentCostsMore.do">
					{
						id:'button-id-audit',
						name:'审核 ',
						iconCls:'button-audit',
						handler: function(){
							var rows =  $("#departmentCosts-table-deptSupplierCosts").datagrid('getChecked');
							if(rows==null || rows.length==0){
								$.messager.alert("提醒","请选择相应的部门费用!");
								return false;
							}
							var idarrs=new Array();
							var errorIdarr=new Array();
							if(null !=rows && rows.length>0){
					    		for(var i=0;i<rows.length;i++){
						    		if(rows[i].id && rows[i].id!=""){
							    		idarrs.push(rows[i].id);
						    		}
						    		if(rows[i].id){
							    		if(rows[i].status=='3'){
							    			errorIdarr.push(rows[i].id);
							    		}
									}
					    		}
							}
							if(errorIdarr.length>0){
								$.messager.alert("提醒","已审核的费用，不能再审核，以下为已审核："+errorIdarr.join(","));
								return false;
							}
							$.messager.confirm("提醒","是否确认审核部门费用?",function(r){
								if(r){
				  				loading();
				  				$.ajax({
							        type: 'post',
							        cache: false,
							        url: 'journalsheet/costsFee/auditDepartmentCostsMore.do',
							        data: {idarrs:idarrs.join(",")},
									dataType:'json',
							        success:function(json){
							        	loaded();
							        	if(json.flag==true){
						  					$.messager.alert("提醒", "审核成功数："+ json.isuccess +"<br />审核失败数："+ json.ifailure );							  					
											$("#departmentCosts-table-deptSupplierCosts").datagrid('reload');
											$("#departmentCosts-table-deptSupplierCosts").datagrid('clearSelections');	
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
					<security:authorize url="/journalsheet/costsFee/oppauditDepartmentCostsMore.do">
					{
						id:'button-id-oppaudit',
						name:'反审 ',
						iconCls:'button-oppaudit',
						handler: function(){
							var rows =  $("#departmentCosts-table-deptSupplierCosts").datagrid('getChecked');
							if(rows==null || rows.length==0){
								$.messager.alert("提醒","请选择相应的部门费用!");
								return false;
							}
							var idarrs=new Array();
							var errorIdarr=new Array();
							if(null !=rows && rows.length>0){
					    		for(var i=0;i<rows.length;i++){
						    		if(rows[i].id && rows[i].id!=""){
							    		idarrs.push(rows[i].id);
						    		}
						    		if(rows[i].id){
							    		if(rows[i].status!='3'){
							    			errorIdarr.push(rows[i].id);
							    		}
									}
					    		}
							}
							if(errorIdarr.length>0){
								$.messager.alert("提醒","已审核的费用，不能再审核，以下为已审核："+errorIdarr.join(","));
								return false;
							}
		
							$.messager.confirm("提醒","是否反审核部门费用？",function(r){
								if(r){
									loading("审核中..");
									$.ajax({   
							            url :'journalsheet/costsFee/oppauditDepartmentCostsMore.do',
								        data: {idarrs:idarrs.join(",")},
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
								            if(json.flag){
							  					$.messager.alert("提醒", "反审核成功数："+ json.isuccess +"<br />反审核失败数："+ json.ifailure );							  					
												$("#departmentCosts-table-deptSupplierCosts").datagrid('reload');
												$("#departmentCosts-table-deptSupplierCosts").datagrid('clearSelections');						            		
								            }else{
								            	$.messager.alert("提醒","反审失败");
								            }
							            }
							        });	
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/journalsheet/costsFee/costsFeeDelBtn.do">
					{
						id:'button-id-delete',
						name:'删除',
						iconCls:'button-delete',
						handler:function(){
							var rows =  $("#departmentCosts-table-deptSupplierCosts").datagrid('getChecked');
							if(rows==null || rows.length==0){
								$.messager.alert("提醒","请选择相应的部门费用!");
								return false;
							}
							var idarrs=new Array();
							var errorIdarr=new Array();
							if(null !=rows && rows.length>0){
					    		for(var i=0;i<rows.length;i++){
						    		if(rows[i].id && rows[i].id!=""){
							    		idarrs.push(rows[i].id);
						    		}
						    		if(rows[i].oaid){
							    		if(rows[i].iswriteoff=='1'){
							    			errorIdarr.push(rows[i].id);
							    		}
									}
					    		}
							}
							if(errorIdarr.length>0){
								$.messager.alert("提醒","已审核的部门费用不能被删除，下列已审核："+errorIdarr.join(","));
								return false;
							}
							$.messager.confirm("提醒","是否确认删除部门费用?",function(r){
								if(r){
				  				loading();
				  				$.ajax({
							        type: 'post',
							        cache: false,
							        url: 'journalsheet/costsFee/deleteDepartmentCostsMore.do',
							        data: {idarrs:idarrs.join(",")},
									dataType:'json',
							        success:function(json){
							        	loaded();
							        	if(json.flag==true){
						  					$.messager.alert("提醒", "删除成功数："+ json.isuccess +"<br />删除失败数："+ json.ifailure );							  					
											$("#departmentCosts-table-deptSupplierCosts").datagrid('reload');
											$("#departmentCosts-table-deptSupplierCosts").datagrid('clearSelections');	
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
					<%--					
					<security:authorize url="/journalsheet/costsFee/costsFeeImportBtn.do">
					{
						id:'button-import-excel',
						name:'导入',
						iconCls:'button-import',
						handler: function(){
							$("#departmentCosts-buttons-importclick").Excel('import',{
								type:'importUserdefined',
					 			url:'journalsheet/costsFee/importDepartmentCostsListData.do',
								onClose: function(){ //导入成功后窗口关闭时操作，
							        var queryJSON = $("#departmentCosts-form-ListQuery").serializeJSON();
							        $("#departmentCosts-table-deptSupplierCosts").datagrid("load",queryJSON);
								}
							});
							$("#departmentCosts-buttons-importclick").trigger("click");
						}				
					},
					</security:authorize>
					--%>					
					<security:authorize url="/journalsheet/costsFee/departmentCostsExportBtn.do">
					{
						id:'button-export-excel',
						name:'导出',
						iconCls:'button-export',
						handler: function(){
							var rows =  $("#departmentCosts-table-deptSupplierCosts").datagrid('getChecked');

							//查询参数直接添加在url中         
				    		var idarrs=new Array();
				    		if(null !=rows && rows.length>0){
					    		for(var i=0;i<rows.length;i++){
						    		if(rows[i].id && rows[i].id!=""){
							    		idarrs.push(rows[i].id);
						    		}
					    		}
				    		}
							$("#departmentCosts-buttons-exportclick").Excel('export',{
								queryForm: "#departmentCosts-form-ListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
						 		type:'exportUserdefined',
						 		name:'部门费用列表',
						 		fieldParam:{idarrs:idarrs.join(",")},
						 		url:'journalsheet/costsFee/exportDepartmentCostsData.do'
							});
							$("#departmentCosts-buttons-exportclick").trigger("click");
						}
					},
					</security:authorize>
					<security:authorize url="/journalsheet/costsFee/showDeptSupplierCostsBtn.do">
					{
						id:'button-search-suppliercost',
						name:'查看供应商摊派',
						iconCls:'icon-search',
						handler: function(){
							var row =  $("#departmentCosts-table-deptSupplierCosts").datagrid('getSelected');
							if(row==null || row.id==""){
			  		        	$.messager.alert("提醒","请选择相关部门费用");
			  		        	return false;								
							}
							$('#deptSupplierCosts-dialog-operate').dialog({  
							    title: '查看相应供应商摊派明细表',  
							  	//width: 680,  
							    //height: 300,
							    fit:true,  
							    closed: true,  
							    cache: false,  
							    href: 'journalsheet/costsFee/showDeptSupplierCostsPopViewPage.do?deptcostsid='+$.trim(row.id),
								maximizable:true,
								resizable:true,  
							    modal: true,
							    onLoad:function(){
							    }
							});
							$('#deptSupplierCosts-dialog-operate').dialog('open');
						}
					},
					</security:authorize>
					{}		
				],
	 			model:'bill',
				type:'list',
				datagrid:'departmentCosts-table-deptSupplierCosts',
				tname:'t_js_departmentcosts',
				id:''
     		});
     		
     		$("#departmentCosts-table-deptSupplierCosts").datagrid({ 
     			authority:costsFeeListColJson,
	  	 		frozenColumns:costsFeeListColJson.frozen,
				columns:costsFeeListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'部门费用列表',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortName:'businessdate',
	  	 		sortOrder:'desc',
	  	 		pagination:true,
		 		idField:'id',
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
				pageSize:20,
				toolbar:'#departmentCosts-table-deptSupplierCostsBtn',
				pageList:[10,20,30,50,200],
			    onSelect:function(rowIndex, rowData){
			    	if(rowData.status=="3"){
			    		$("#departmentCosts-button-deptSupplierCosts").buttonWidget("disableButton", 'button-id-edit');
			    		$("#departmentCosts-button-deptSupplierCosts").buttonWidget("disableButton", 'button-id-delete');
			    		$("#departmentCosts-button-deptSupplierCosts").buttonWidget("disableButton", 'button-id-audit');
			    		$("#departmentCosts-button-deptSupplierCosts").buttonWidget("enableButton", 'button-id-oppaudit');
			    	}else{
			    		$("#departmentCosts-button-deptSupplierCosts").buttonWidget("enableButton", 'button-id-edit');
			    		$("#departmentCosts-button-deptSupplierCosts").buttonWidget("enableButton", 'button-id-delete');
			    		$("#departmentCosts-button-deptSupplierCosts").buttonWidget("enableButton", 'button-id-audit');
			    		$("#departmentCosts-button-deptSupplierCosts").buttonWidget("disableButton", 'button-id-oppaudit');
				    	
			    	}
			    },
		    	onDblClickRow:function(rowIndex, rowData){
			    	<%if(isEdit){%>
			    		if(rowData.status!='3'){
			    			showDeptCostsDialog("部门费用【修改】", 'journalsheet/costsFee/showDepartmentCostsEditPage.do?id='+rowData.id);
			    		}else{
			    			showDeptCostsDialog("部门费用【详情】", 'journalsheet/costsFee/showDepartmentCostsViewPage.do?id='+rowData.id);
			    		}
	     			<% }else { %>
	     				showDeptCostsDialog("部门费用【详情】", 'journalsheet/costsFee/showDepartmentCostsViewPage.do?id='+rowData.id);
	     			<%}%>
		    	},
				onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						footerobject = footerrows[0];
					}
		 		},
				onCheckAll:function(){
		 			deptCostsCountTotalAmount();
				},
				onUncheckAll:function(){
					deptCostsCountTotalAmount();
				},
				onCheck:function(){
					deptCostsCountTotalAmount();
				},
				onUncheck:function(){
					deptCostsCountTotalAmount();
				}
			}).datagrid("columnMoving");
		});
		function deptCostsCountTotalAmount(){
    		var rows =  $("#departmentCosts-table-deptSupplierCosts").datagrid('getChecked');
    		if(null==rows || rows.length==0){
           		var foot=[];
    			if(null!=footerobject){
	        		foot.push(emptyChooseObjectFoot());
	        		foot.push(footerobject);
	    		}
    			$("#departmentCosts-table-deptSupplierCosts").datagrid("reloadFooter",foot);
           		return false;
       		}
    		
    		<c:forEach items="${deptCostsSubjectList }" var="list">
    			var deptCostsSubject_${list.code}=0;
    		</c:forEach>
    		
    		for(var i=0;i<rows.length;i++){
    			
        		<c:forEach items="${deptCostsSubjectList }" var="list">
        			deptCostsSubject_${list.code} = Number(deptCostsSubject_${list.code})+Number(rows[i].deptCostsSubject_${list.code} == undefined ? 0 : rows[i].deptCostsSubject_${list.code});
        		</c:forEach>
        		
    		}
    		var foot=[{deptname:'选中金额'
        				<c:forEach items="${deptCostsSubjectList }" var="list">
							,deptCostsSubject_${list.code} : deptCostsSubject_${list.code}
						</c:forEach>
        			}];
    		if(null!=footerobject){
        		foot.push(footerobject);
    		}
    		$("#departmentCosts-table-deptSupplierCosts").datagrid("reloadFooter",foot);
    	}
    	function emptyChooseObjectFoot(){
    		
    		<c:forEach items="${deptCostsSubjectList }" var="list">
    			var deptCostsSubject_${list.code}=0;
    		</c:forEach>
    		
    		var foot={deptname:'选中金额'
				<c:forEach items="${deptCostsSubjectList }" var="list">
					,deptCostsSubject_${list.code} : deptCostsSubject_${list.code}
				</c:forEach>
			};
			return foot;
    	}
    </script>
  </body>
</html>
