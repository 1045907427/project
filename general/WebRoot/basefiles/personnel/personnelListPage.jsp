<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>人员档案</title>
    <%@include file="/include.jsp" %>   
  </head>
  <body>
  	<input type="hidden" id="department-hidden-hdId" />
	<input type="hidden" id="department-hidden-hdpId" />
	<input type="hidden" id="department-hidden-hdIsParent" />
	<input type="hidden" id="personel-list-hdsysUserid" value="${sysUserid}"/>
	<div title="人员档案列表" class="easyui-layout" data-options="fit:true,border:true">
		<div title="" data-options="region:'north',split:false,border:false" style="height: 30px;overflow: hidden">
			<div class="buttonBG" id="basefile-button-personnelList"></div>
		</div>
		<div title="部门" data-options="region:'west',split:true" style="width:160px;">
            <div id="personnelDept-tree-personnelDept" class="ztree"></div>
    	</div>
    	<div title="人员列表" data-options="region:'center',split:true">
    		<div id="personnel-query-showPersonnelList">
	    		<form action="" id="personnel-form-personnelListQuery" method="post" style="display: none;padding-left: 5px; padding-top: 2px;">
		    		<input type="hidden" id="personnel-hdbelongdeptid" name="belongdeptid"/>
		    		<!-- <input type="hidden" id="personnel-hdstate" name="state"/> -->
		    		<table cellpadding="1" cellspacing="0" border="0">
		    			<tr>
		    				<td>编&nbsp;&nbsp;号:</td>
		    				<td style="padding-right: 50px"><input type="text" name="id" style="width:120px" /></td>
		    				<td>姓&nbsp;&nbsp;名:</td>
		    				<td><input type="text" name="name" style="width:150px" /></td>
		    			</tr>
		    			<tr>
		    				<td>入职年月:</td>
		    				<td style="padding-right: 50px"><input type="text" class="Wdate" name="datesemployed" onclick="WdatePicker({'dateFmt':'yyyy-MM',maxDate:'%y%M'})" style="width:120px" />
		    				</td>
		    				<td colspan="2">
			    				<a href="javaScript:void(0);" id="personnel-query-queryPersonnelList" class="button-qr">查询</a>
					    		<a href="javaScript:void(0);" id="personnel-query-reloadPersonnelList" class="button-qr">重置</a>
				    			<span id="personnel-query-advanced"></span>
		    				</td>
		    			</tr>
		    		</table>
	    		</form>
    		</div>
    		<table id="personnel-table-personnelList"></table>
   		</div>
   	</div>
  
  <script type="text/javascript">
  	var personnel_AjaxConn = function (Data, Action) {
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
     var personnelListColJson=$("#personnel-table-personnelList").createGridColumnLoad({
     	name:'base_personnel',
     	frozenCol:[[]],
     	commonCol:[[{field:'id',title:'编码',width:50,sortable:true},
     				{field:'name',title:'姓名',width:80,sortable:true},
     				{field:'state',title:'状态',width:50,sortable:true,
     					formatter:function(val,rowData,rowIndex){
							return rowData.stateName;
						}
     				},
     				{field:'remark',title:'备注',width:100,sortable:true},
     				{field:'employetype',title:'业务属性',width:70,sortable:true,
						formatter:function(val,rowData,rowIndex){
							return rowData.employetypeName;
						}
					},
     				{field:'namepinyin',title:'姓名拼音',width:70,hidden:true,resizable:true,sortable:true},
     				{field:'personnelstyle',title:'员工类型',width:60,hidden:true,sortable:true,
     					formatter:function(val,rowData,rowIndex){
							return rowData.perStyleName;
						}
     				}, 
					{field:'highestdegree',title:'最高学历',width:60,hidden:true,sortable:true,
						formatter:function(val,rowData,rowIndex){
							return rowData.highestDegreeName;
						}
					},
					{field:'datesemployed',title:'入职时间',width:90,hidden:true,sortable:true},
					{field:'belongdeptid',title:'所属部门',width:70,sortable:true,
						formatter:function(val,rowData,rowIndex){
							return rowData.belongdept;
						}
					},
					{field:'belongpost',title:'所属岗位',width:70,sortable:true,
						formatter:function(val,rowData,rowIndex){
							return rowData.belongpostName;
						}
					},
					{field:'leadid',title:'上级领导',width:60,sortable:true,
						formatter:function(val,rowData,rowIndex){
							return rowData.leadname;
						}
					},
					{field:'sex',title:'性别',width:50,hidden:true,sortable:true,
						formatter:function(val,rowData,rowIndex){
							return rowData.sexName;
						}
					},
					{field:'maritalstatus',title:'婚姻状况',width:60,hidden:true,sortable:true,
						formatter:function(val,rowData,rowIndex){
							return rowData.maritalstatusName;
						}
					},
					{field:'birthday',title:'出生日期',width:80,hidden:true,sortable:true},
					{field:'age',title:'年龄',width:30,hidden:true,sortable:true},
					{field:'idcard',title:'身份证号码',width:120,hidden:true,sortable:true},
					{field:'nation',title:'民族',width:50,hidden:true,sortable:true,
						formatter:function(val,rowData,rowIndex){
							return rowData.nationName;
						}
					},
					{field:'nativeplace',title:'籍贯',width:50,hidden:true,sortable:true}, 
					{field:'polstatus',title:'政治面貌',width:60,hidden:true,sortable:true,
						formatter:function(val,rowData,rowIndex){
							return rowData.polstatusName;
						}
					}, 
					{field:'tel',title:'电话',width:80,sortable:true},
					{field:'fax',title:'传真',width:80,sortable:true},
					{field:'email',title:'邮箱',width:80,sortable:true},
					{field:'telphone',title:'手机号码',width:80,sortable:true},
					{field:'compcornet',title:'公司短号',width:70,sortable:true}, 
					{field:'salaryscheme',title:'薪酬方案',width:80,hidden:true,sortable:true},
					{field:'address',title:'居住地址',width:100,hidden:true,sortable:true},
					{field:'addrpostcode',title:'居住地邮编',width:50,hidden:true,sortable:true},
					{field:'householdaddr',title:'户籍地址',width:100,hidden:true,sortable:true},
					{field:'householdcode',title:'户籍地邮编',width:50,hidden:true,sortable:true},
					{field:'adduserid',title:'建档人',width:60,hidden:true,sortable:true,
						formatter:function(val,rowData,rowIndex){
							return rowData.adduser;
						}
					},
					{field:'adddeptid',title:'建档部门',width:80,hidden:true,sortable:true,
						formatter:function(val,rowData,rowIndex){
							return rowData.adddept;
						}
					},
					{field:'addtime',title:'建档时间',width:115,hidden:true,sortable:true},
					{field:'modifyuserid',title:'最后修改人',width:60,hidden:true,sortable:true,
						formatter:function(val,rowData,rowIndex){
							return rowData.modifyuser;
						}
					},
					{field:'modifytime',title:'最后修改时间',width:115,hidden:true,sortable:true},
					{field:'openuserid',title:'启用人',width:60,hidden:true,sortable:true,
						formatter:function(val,rowData,rowIndex){
							return rowData.openuser;
						}
					},
					{field:'opentime',title:'启用时间',width:115,hidden:true,sortable:true},
					{field:'closeuserid',title:'禁用人',width:60,hidden:true,sortable:true,
						formatter:function(val,rowData,rowIndex){
							return rowData.closeuser;
						}
					},
					{field:'closetime',title:'禁用时间',width:115,hidden:true,sortable:true},
					{field:'field01',title:'${fieldmap.field01}',width:115,hidden:true,sortable:true},
					{field:'field02',title:'${fieldmap.field02}',width:115,hidden:true,sortable:true},
					{field:'field03',title:'${fieldmap.field03}',width:115,hidden:true,sortable:true},
					{field:'field04',title:'${fieldmap.field04}',width:115,hidden:true,sortable:true},
					{field:'field05',title:'${fieldmap.field05}',width:115,hidden:true,sortable:true},
					{field:'field06',title:'${fieldmap.field06}',width:115,hidden:true,sortable:true},
					{field:'field07',title:'${fieldmap.field07}',width:115,hidden:true,sortable:true},
					{field:'field08',title:'${fieldmap.field08}',width:115,hidden:true,sortable:true},
					{field:'field09',title:'${fieldmap.field09}',width:115,hidden:true,sortable:true},
					{field:'field10',title:'${fieldmap.field10}',width:115,hidden:true,sortable:true},
					{field:'field11',title:'${fieldmap.field11}',width:115,hidden:true,sortable:true},
					{field:'field12',title:'${fieldmap.field12}',width:115,hidden:true,sortable:true}
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
	    
   	$(function(){
   		var personnelDeptTreeSetting = {
			view: {
				dblClickExpand: false,
				showLine: true,
				selectedMulti: false,
				showIcon:true,
				expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
			},
			async: {
				enable: true,
				url: "basefiles/getPersonelDeptTree.do",
				autoParam: ["id","pId", "name","state","deptmanaguserid"]
			},
			data: {
				key:{
					title:"name"
				},
				simpleData: {
					enable:true,
					idKey: "id",
					pIdKey: "pId",
					rootPId: ""
				}
			},
			callback: {
				//点击树状菜单更新页面按钮列表
				beforeClick: function(treeId, treeNode) {
					$("#personnel-hdbelongdeptid").val(treeNode.id);
					//$("#personnel-hdstate").val(treeNode.state);
					//$("#personnel-form-personnelListQuery").show();
					$("#department-hidden-hdId").val(treeNode.id);
					$("#department-hidden-hdpId").val(treeNode.pId);
					var queryJSON = $("#personnel-form-personnelListQuery").serializeJSON();
				    $('#personnel-table-personnelList').datagrid({
			  			authority:personnelListColJson,
			  	 		frozenColumns:[[{field:'personnelck',checkbox:true}]],
						columns:personnelListColJson.common,
					    fit:true,
						title:'',
						toolbar:'#personnel-query-showPersonnelList',
						method:'post',
						rownumbers:true,
						pagination:true,
						idField:'id',
						singleSelect:false,
						checkOnSelect:true,
						selectOnCheck:true,
                        pageSize:100,
						queryParams:queryJSON,
						url:'basefiles/showPersonnelList.do',
						onDblClickRow:function(rowIndex, rowData){
							top.addOrUpdateTab('basefiles/showPersonnelPage.do?type=view&id='+rowData.id+'&state='+rowData.state,'人员档案');
						}
					}).datagrid("columnMoving");
					
					var zTree = $.fn.zTree.getZTreeObj("personnelDept-tree-personnelDept");
					if (treeNode.isParent) {
						if (treeNode.level == 0) {
							zTree.expandAll(false);
							zTree.expandNode(treeNode);
						} else {
							zTree.expandNode(treeNode);
						}
					}
					return true;
				},
				onClick:function(){
					$("#personnel-table-personnelList").datagrid('clearSelections');
				},
				onAsyncSuccess: function(event, treeId, treeNode, msg){
					$("#personnel-form-personnelListQuery").show();
					var queryJSON = $("#personnel-form-personnelListQuery").serializeJSON();
					$('#personnel-table-personnelList').datagrid({
			  			authority:personnelListColJson,
			  	 		frozenColumns:[[{field:'personnelck',checkbox:true}]],
						columns:personnelListColJson.common,
					    fit:true,
						title:'',
						toolbar:'#personnel-query-showPersonnelList',
						method:'post',
						rownumbers:true,
						pagination:true,
						idField:'id',
						singleSelect:false,
						checkOnSelect:true,
						selectOnCheck:true,
                        pageSize:100,
						queryParams:queryJSON,
						url:'basefiles/showPersonnelList.do',
						onDblClickRow:function(rowIndex, rowData){
							top.addOrUpdateTab('basefiles/showPersonnelPage.do?type=view&id='+rowData.id+'&state='+rowData.state,'人员档案');
						}
					}).datagrid("columnMoving");
				}
			}
		};
		
		$.fn.zTree.init($("#personnelDept-tree-personnelDept"), personnelDeptTreeSetting,null);
		
   		var treeobj = $.fn.zTree.getZTreeObj("personnelDept-tree-personnelDept");
		
		//加载按钮
		$("#basefile-button-personnelList").buttonWidget({
			//初始默认按钮 根据type对应按钮事件
			initButton:[
				{},
				<security:authorize url="/basefiles/showPersonnelAddPage.do">
				{
					type:'button-add',//新增
					handler:function(){
						var belongdeptid="";
						var personnel_zDeptTree = treeobj.getSelectedNodes();
						if(personnel_zDeptTree.length != 0){
			 				if("".localeCompare(personnel_zDeptTree[0].id) != 0){
								belongdeptid = personnel_zDeptTree[0].id;
							}
						}
						top.addOrUpdateTab('basefiles/showPersonnelPage.do?type=add&belongdeptid='+belongdeptid,'人员档案');
					}
				},
				</security:authorize>
				<security:authorize url="/basefiles/showPersonnelEditPage.do">
	 			{
		 			type:'button-edit',//修改
		 			handler:function(){
		 				var personnelRow=$("#personnel-table-personnelList").datagrid('getSelected');
						if(personnelRow == null){
							$.messager.alert("提醒","请选择人员!");
							return false;
						}
						var flag = isDoLockData(personnelRow.id,"t_base_personnel");
		 				if(!flag){
		 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
		 					return false;
		 				}
						top.addOrUpdateTab('basefiles/showPersonnelPage.do?type=edit&id='+personnelRow.id,'人员档案');
		 			}
	 			},
	 			</security:authorize>
				<security:authorize url="/basefiles/showPersonnelDeletePage.do">
	 			{
		 			type:'button-delete',//删除
		 			handler:function(){
						var personnelRows = $("#personnel-table-personnelList").datagrid('getChecked');
						if(personnelRows.length == 0){
							$.messager.alert("提醒","请勾选人员!");
							return false;
						}
						$.messager.confirm("提醒","是否确定删除人员档案?",function(r){
							if(r){
								var idStr = "";
								for(var i=0;i<personnelRows.length;i++){
									idStr += personnelRows[i].id + ",";
								}
								loading("删除中..");
								$.ajax({   
						            url :'basefiles/deletePersonnelInfo.do',
						            type:'post',
						            dataType:'json',
						            data:{ids:idStr},
						            success:function(data){
						            	loaded();
										var msg = data.userNum+"条记录被引用,不允许删除;<br/>"+data.lockNum+"条记录网络互斥,不允许删除;";
										if(data.msg != ""){
											msg += "<br>" + data.msg;
										}
						            	$.messager.alert("提醒",msg);
										var queryJSON = $("#personnel-form-personnelListQuery").serializeJSON();
	       								$("#personnel-table-personnelList").datagrid('load', queryJSON);
										$("#personnel-table-personnelList").datagrid('clearSelections');
										$("#personnel-table-personnelList").datagrid('clearChecked');
						            }
						        });
							}
						});
		 			}
	 			},
	 			</security:authorize>
				<security:authorize url="/basefiles/showPersonnelCopyPage.do">
	 			{
		 			type:'button-copy',//复制
		 			handler:function(){
		 				var personnelRow=$("#personnel-table-personnelList").datagrid('getSelected');
						if(personnelRow == null){
							$.messager.alert("提醒","请选择人员!");
							return false;
						}
						top.addOrUpdateTab('basefiles/showPersonnelPage.do?type=copy&id='+personnelRow.id+'&state='+personnelRow.state,'人员档案');
		 			}
	 			},
	 			</security:authorize>
				<security:authorize url="/basefiles/goodsInfoViewBtn.do">
	 			{
	 				type:'button-view',//查看
	 				handler:function(){
	 					var personnelRow=$("#personnel-table-personnelList").datagrid('getSelected');
						if(personnelRow == null){
							$.messager.alert("提醒","请选择人员!");
							return false;
						}
						top.addOrUpdateTab('basefiles/showPersonnelPage.do?type=view&id='+personnelRow.id+'&state='+personnelRow.state,'人员档案');
	 				}
	 			},
	 			</security:authorize>
				<security:authorize url="/basefiles/showPersonnelEnablePage.do">
	 			{
		 			type:'button-open',//启用
		 			handler:function(){
						var personnelRows = $("#personnel-table-personnelList").datagrid('getChecked');
						if(personnelRows.length == 0){
							$.messager.alert("提醒","请勾选人员!");
							return false;
						}
						$.messager.confirm("提醒","是否确定启用人员档案?",function(r){
							if(r){
								var idStr = "";
								for(var i=0;i<personnelRows.length;i++){
									idStr += personnelRows[i].id + ",";
								}
								loading("启用中..");
								$.ajax({   
						            url :'basefiles/enablePersonnels.do',
						            type:'post',
						            dataType:'json',
						            data:{ids:idStr},
						            success:function(retJson){
						            	loaded();
						            	$.messager.alert("提醒",""+retJson.unSucMes+"<br />"+retJson.sucMes+"<br />"+retJson.failMes+"");
										var queryJSON = $("#personnel-form-personnelListQuery").serializeJSON();
	       								$("#personnel-table-personnelList").datagrid('load', queryJSON);
										$("#personnel-table-personnelList").datagrid('clearSelections');
										$("#personnel-table-personnelList").datagrid('clearChecked');
						            }
						        });
							}
						});
		 			}
	 			},
	 			</security:authorize>
				<security:authorize url="/basefiles/showPersonnelDisablePage.do">
	 			{
		 			type:'button-close',//禁用
		 			handler:function(){
						var personnelRows=$("#personnel-table-personnelList").datagrid('getChecked');
						if(personnelRows.length == 0){
							$.messager.alert("提醒","请勾选人员!");
							return false;
						}
						var str="";
		                for(var i=0;i<personnelRows.length;i++){
		                   	str=personnelRows[i].id+","+str;
						}
						$.messager.confirm("提醒","是否禁用勾选人员?",function(r){
							if(r){
								//人员禁用时，提示是否同时禁用系统用户
								$.messager.confirm("提醒","是否同时禁用系统用户?",function(r){
									if(r){
										loading("禁用中..");
										$.ajax({   
								            url :'basefiles/disablePersonnels.do?type=tonbu',
								            type:'post',
								            dataType:'json',
								            data:{ids:str},
								            success:function(retJson){
								            	loaded();
								            	if(retJson.retFlag){
							                    	$.messager.alert("提醒",""+retJson.sucMes+"<br/>"+retJson.failMes+"<br/>"+retJson.userMes+"");
							                    	var queryJSON = $("#personnel-form-personnelListQuery").serializeJSON();
			       									$("#personnel-table-personnelList").datagrid('load', queryJSON);
							                    	$("#personnel-table-personnelList").datagrid('clearSelections');
													$("#personnel-table-personnelList").datagrid('clearChecked');
							                    }
							                    else{
							                    	$.messager.alert("提醒",""+retJson.sucMes+"<br/>"+retJson.failMes+"<br/>"+retJson.userMes+"");
							                    }
								            }
								        });
									}
									else{
										loading("禁用中..");
										$.ajax({   
								            url :'basefiles/disablePersonnels.do?type=yibu',
								            type:'post',
								            dataType:'json',
								            data:{ids:str},
								            success:function(retJson){
								            	loaded();
								            	if(retJson.retFlag == true){
							                    	$.messager.alert("提醒",""+retJson.sucMes+"<br/>"+retJson.failMes+"<br/>"+retJson.userMes+"");
							                    	var queryJSON = $("#personnel-form-personnelListQuery").serializeJSON();
			       									$("#personnel-table-personnelList").datagrid('load', queryJSON);
							                    	$("#personnel-table-personnelList").datagrid('clearSelections');
							                    	$("#personnel-table-personnelList").datagrid('clearChecked');
							                    }
							                    else{
							                    	$.messager.alert("提醒",""+retJson.sucMes+"<br/>"+retJson.failMes+"<br/>"+retJson.userMes+"");
							                    }
								            }
								        });
									}
								});
							}
							else{
								$("#personnel-table-personnelList").datagrid('clearSelections');
								$("#personnel-table-personnelList").datagrid('clearChecked');
							}
						});
		 			}
	 			},
	 			</security:authorize>
				<security:authorize url="/basefiles/showPersonnelImportPage.do">
	 			{
		 			type:'button-import',//导入
		 			attr:{
		 				clazz: "personnelService", //spring中注入的类名
				 		methodjson: {t_base_personnel:'addDRPersonnelInfo',t_base_personnel_post:'addDRPersonnelPost',
				 					t_base_personnel_work:'addDRPersonnelWork',t_base_perssonnel_edu:'addDRPersonnelEdu',
				 					t_base_personnel_brand:'addDRPsnlBrandAndCustomer',t_base_personnel_customer:'addDRPsnlBrandAndCustomer'},
				 		tnjson: {人员列表:'t_base_personnel',岗位变动:'t_base_personnel_post',工作经历:'t_base_personnel_work',
				 				教育经历:'t_base_perssonnel_edu',对应客户:'t_base_personnel_customer',对应品牌:'t_base_personnel_brand'},//表名
			            module: 'basefiles', //模块名，
				 		pojojson: {t_base_personnel:'Personnel',t_base_personnel_post:'Personnelpost',t_base_personnel_work:'Personnelworks',
				 				t_base_perssonnel_edu:'Personneledu',t_base_personnel_customer:'PersonnelCustomer',t_base_personnel_brand:'PersonnelBrand'},
						type:'importmore',
						childsamemethod:'addDRPsnlBrandAndCustomer',//子表导入需调用同一方法的名称
						majorkey:'id',
						version:'1',
						childkey:'personid',
						maintn:'t_base_personnel',
						onClose: function(){ //导入成功后窗口关闭时操作，
					         $("#personnel-table-personnelList").datagrid('reload');	//更新列表	                                                                                        
						}
		 			}
	 			},
	 			</security:authorize>
				<security:authorize url="/basefiles/showPersonnelExportPage.do">
	 			{
		 			type:'button-export',//导出 
		 			attr:{
		 				datagrid: "#personnel-table-personnelList",
		 				queryForm: "#personnel-form-personnelListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
				 		tnstr:'t_base_personnel,t_base_personnel_post,t_base_personnel_work,t_base_perssonnel_edu,t_base_personnel_customer,t_base_personnel_brand',//表名
				 		tnjson: {t_base_personnel:'人员列表',t_base_personnel_post:'岗位变动',t_base_personnel_work:'工作经历',
				 				t_base_perssonnel_edu:'教育经历',t_base_personnel_customer:'对应客户',t_base_personnel_brand:'对应品牌'},//表名
				 		type:'exportmore',
				 		uniontnjson:{t_base_personnel_customer:'t_base_personnel_supplier_customer',t_base_personnel_brand:'t_base_personnel_supplier_brand'},//需两子表合并查询表名
				 		exportparam:'参数描述',//参数描述
				 		shortcutname:'personnel',
				 		version:'1',//1不显示单选框，2显示单选框
				 		queryparam:'id,name,belongdeptid',//查询条件参数
				 		childkey:'personid',
						maintn:'t_base_personnel',
				 		name:'人员档案列表'
		 			}
	 			},
	 			</security:authorize>
				<security:authorize url="/basefiles/showPersonnelPrintViewPage.do">
	 			{
		 			type:'button-preview',//打印预览
		 			handler:function(){
		 				alert("import");
		 			}
	 			},
	 			</security:authorize>
				<security:authorize url="/basefiles/showPersonnelPrintPage.do">
		 			{
			 			type:'button-print',//打印
			 			handler:function(){
			 				alert("print");
			 			}
		 			},
	 			</security:authorize>
                {
                    type: 'button-commonquery',
                    attr:{
                        //查询针对的表
                        name:'base_personnel',
                        //查询针对的表格id
                        datagrid:'personnel-table-personnelList'
                    }
                },
	 			{}
			],
			buttons:[
				{},
				<security:authorize url="/basefiles/showPersonnelDistributePage.do">
				{
					id:'allotSysUserListBtn',
					name:'系统用户分配',
					iconCls:'assign-user',
					handler:function(){
						var personnels=$("#personnel-table-personnelList").datagrid('getChecked');
						if(personnels.length == 0){
							$.messager.alert("提醒","请勾选需要分配系统用户的人员!");
							return false;
						}
						var idArrStr="";
						for(var i=0;i<personnels.length;i++){
							idArrStr = idArrStr + personnels[i].id + ",";
						}
				    	$.messager.confirm("提醒", "是否分配系统用户?", function(r){
							if (r){
								loading("分配中..");
								$.ajax({   
						            url :'accesscontrol/allotSystemUser.do',
						            type:'post',
						            dataType:'json',
						            data:{persIDsStr:idArrStr},
						            success:function(retJson){
						            	loaded();
						            	$.messager.alert("提醒",""+retJson.failNum+"个人员状态不允许,分配无效;<br/>"+retJson.existNum+"个人员已分配;<br/>"+retJson.sucNum+"个人员分配成功;<br/>"+retJson.unSucNum+"个人员分配失败;");
										$("#personnel-table-personnelList").datagrid('clearChecked');
						            }
						        });
							}
						});
					}
				},
				</security:authorize>
				{}
			],
			model:'base',
			type:'multipleList',
			taburl:'/basefiles/showPersonnelListPage.do',
			datagrid:'personnel-table-personnelList',
			tname:'t_base_personnel',
			id:''
		});
		
		//回车事件
		controlQueryAndResetByKey("personnel-query-queryPersonnelList","personnel-query-reloadPersonnelList");
		
		//查询
		$("#personnel-query-queryPersonnelList").click(function(){
			//把form表单的name序列化成JSON对象
      		var queryJSON = $("#personnel-form-personnelListQuery").serializeJSON();
      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
      		$("#personnel-table-personnelList").datagrid("load",queryJSON);
		});
		
		//重置按钮
		$("#personnel-query-reloadPersonnelList").click(function(){
			$("#personnel-form-personnelListQuery")[0].reset();
			$("#personnel-table-personnelList").datagrid("load",{});
			
		});
		
		//通用查询组建调用
//		$("#personnel-query-advanced").advancedQuery({
//			//查询针对的表
//	 		name:'base_personnel',
//	 		//查询针对的表格id
//	 		datagrid:'personnel-table-personnelList'
//		});
//
   	});
  </script>
  </body>
</html>
