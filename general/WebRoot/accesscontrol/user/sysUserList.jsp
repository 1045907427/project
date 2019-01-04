<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>用户列表</title>
	<%@include file="/include.jsp" %>   
	<script type="text/javascript" src="js/jquery.excel.js"></script>
  </head>
  
  <body>
	<table id="sysUser-table-showSysUserList"></table>
     <div id="sysUser-query-showUserList" style="padding:0px;height:auto">
		<div class="buttonBG">
			<security:authorize url="/basefiles/showSysUserAddPage.do">
				<a href="javaScript:void(0);" id="sysUser-add-addUser" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'" title="新增系统用户">新增</a>
			</security:authorize>
			<security:authorize url="/basefiles/showSysUserEditPage.do">
				<a href="javaScript:void(0);" id="sysUser-edit-editUser" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-edit'" title="修改系统用户">修改</a>
			</security:authorize>
			<security:authorize url="/basefiles/showSysUserRemovePage.do">
				<a href="javaScript:void(0);" id="sysUser-remove-removeUser" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-delete'" title="删除系统用户">删除</a>
			</security:authorize>
			<security:authorize url="/basefiles/showSysUserCopyPage.do">
				<a href="javaScript:void(0);" id="sysUser-copy-copyUser" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-copy'" title="复制">复制</a>
			</security:authorize>
			<security:authorize url="/basefiles/showSysUserEnablePage.do">
				<a href="javaScript:void(0);" id="sysUser-enable-enableUser" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-open'" title="启用系统用户">启用</a>
			</security:authorize>
			<security:authorize url="/basefiles/showSysUserRemovePage.do">
				<a href="javaScript:void(0);" id="sysUser-disable-disableUser" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-close'" title="禁用系统用户">禁用</a>
			</security:authorize>
			<security:authorize url="/basefiles/showSysUserInPage.do">
				<a href="javaScript:void(0);" id="sysUser-in-inUserList" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-import'" title="导入系统用户列表">导入</a>
			</security:authorize>
			<security:authorize url="/basefiles/showSysUserOutPage.do">
				<a href="javaScript:void(0);" id="sysUser-out-outUserList" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-export'" title="导出系统用户列表">导出</a>
			</security:authorize>
			<security:authorize url="/basefiles/showSysUserResetPwdPage.do">
				<a href="javaScript:void(0);" id="sysUser-resetPwd-resetPwdUser" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-reset'" title="密码重置">密码重置</a>
			</security:authorize>
			<security:authorize url="/basefiles/kickOutSysUser.do">
				<a href="javaScript:void(0);" id="sysUser-kickout-sysuser" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-oppaudit'" title="踢出">踢出</a>
			</security:authorize>
            <security:authorize url="/basefiles/uploadLocation.do">
                <a href="javaScript:void(0);" id="sysUser-upload-location" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'" title="强制定位">强制定位</a>
            </security:authorize>
            <span id="sysUser-query-advanced"></span>
        </div>
         <div>
			<form action="" id="sysUser-form-userList" method="post">
				<table class="querytable">
					<tr>
						<td>用户名:</td>
						<td><input name="username" style="width:150px" /></td>
						<td>姓名:</td>
						<td><input name="name" style="width:150px" /></td>
						<td>所属部门:</td>
						<td>
							<input id="sysUser-form-deptid" name="departmentid"/>
						</td>
					</tr>
					<tr>
						<td>角色:</td>
						<td>
							<input id="sysUser-form-roleid" name="roleid" />
						</td>
						<td>所属岗位:</td>
						<td>
							<input id="sysUser-form-workjobid" name="workjobid" />
						</td>
						<td>状态:</td>
						<td>
							<select name="state" style="width: 150px;">
								<option></option>
								<option value="2">保存</option>
								<option value="1">启用</option>
								<option value="0">禁用</option>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="4"></td>
						<td colspan="2">
							<a href="javaScript:void(0);" id="sysUser-queay-queryUserList" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="sysUser-queay-reloadUserList" class="button-qr">重置</a>

						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div id="sysUser-dialog-userOper"></div>
	<script type="text/javascript">
	var sysUser_AjaxConn = function (Data, Action) {
	    var MyAjax = $.ajax({
	        type: 'post',
	        cache: false,
	        url: Action,
	        data: Data,
	        async: false
	    })
	    return MyAjax.responseText;
	}
	//列冻结、拖拽、样式保存
	var userListColJson = 
		$("#sysUser-table-showSysUserList").createGridColumnLoad({
				name :'sys_user',
				frozenCol : [[
							{field:'ck',checkbox:true,isShow:true}
							]],
				commonCol : [[ {field:'userid',title:'用户编码',width:100,hidden:true},
								{field:'username',title:'用户名',width:80,sortable:true},
								{field:'personnelid',title:'关联人员编号',width:100,hidden:true}, 
						        {field:'name',title:'姓名',width:80,sortable:true},  
						        {field:'departmentid',title:'部门编号',width:80,hidden:true},
						        {field:'departmentname',title:'部门名称',width:100,sortable:true},
						        {field:'workjobid',title:'所属岗位',width:120,sortable:true,
						        	formatter:function(val,rowData,rowIndex){
						        		return rowData.workjobName;
						        	}
						        },
						        {field:'ispwd',title:'是否允许修改密码',width:100,
						        	formatter:function(val,rowData,rowIndex){
						        		if(val == '1'){
						        			return '是';
						        		}else if(val == '0'){
						        			return '否';
						        		}
									}
						        },
						        {field:'isphone',title:'是否手机用户',width:80,
						        	formatter:function(val,rowData,rowIndex){
										if(val == '1'){
						        			return '是';
						        		}else if(val == '0'){
						        			return '否';
						        		}
									}
						        },
						        {field:'isuploadlocation',title:'是否上传定位',width:80,
						        	formatter:function(val,rowData,rowIndex){
										if(val == '1'){
						        			return '是';
						        		}else if(val == '0'){
						        			return '否';
						        		}
									}
						        },
						        {field:'sex',title:'性别',width:40,sortable:true,
						        	formatter:function(val,rowData,rowIndex){
										return rowData.sexName;
									}
						        },
						        {field:'birthday',title:'出生日期',width:130,hidden:true},
						        {field:'telphone',title:'电话',width:80,hidden:true},
						        {field:'mobilephone',title:'手机',width:80,hidden:true},
						        {field:'email',title:'邮件',width:100,hidden:true},
						        {field:'qq',title:'QQ',width:70,hidden:true},
						        {field:'msn',title:'MSN',width:70,hidden:true},
						        {field:'icq',title:'icq号码',width:80,hidden:true},
						        {field:'addr',title:'地址',width:200,hidden:true},
						        {field:'zip',title:'邮编',width:60,hidden:true},
						        {field:'hometelphone',title:'家庭电话',width:70,hidden:true},
						        {field:'image',title:'头像地址',width:200,hidden:true},
						        {field:'seq',title:'序号',width:50,sortable:true},
						        {field:'state',title:'状态',width:40,
						        	formatter:function(val,rowData,rowIndex){
										return rowData.stateName;
									}
						        },
						        {field:'addtime',title:'新增时间',width:130,hidden:true},
						        {field:'adduserid',title:'新增人用户编码',width:80,hidden:true},
						        {field:'modifytime',title:'修改时间',width:130,hidden:true},
						        {field:'modifyuserid',title:'修改人用户编码',width:80,hidden:true},
						        {field:'lastip',title:'最近登录IP',width:100},
						        {field:'lastsid',title:'手机最近登录SID',width:100},
						        {field:'logintype',title:'登录状态',width:80,isShow:true,
						        	formatter:function(val,rowData,rowIndex){
						        		var str = "";
						        		if(rowData.isPhoneLogin=="1"){
						        			str += "手机";
						        		}
						        		if(rowData.isSysLogin=="1"){
						        			str += " 系统";
						        		}
						        		if(str!=""){
						        			str +="在线";
						        		}else{
						        			str +="离线";
						        		}
										return str;
									}	
						        }
						    ]]
	});
	
	//通用查询组建调用
	$("#sysUser-query-advanced").advancedQuery({
		//查询针对的表
 		name:'sys_user',
 		//查询针对的表格id
 		datagrid:'sysUser-table-showSysUserList'
	});
	
	
	$(function(){
		$('#sysUser-table-showSysUserList').datagrid({ 
            //判断是否开启表头菜单
  	 		authority:userListColJson,
  	 		frozenColumns: userListColJson.frozen,
			columns:userListColJson.common,
  	 		fit:true,
  	 		method:'post',
  	 		title:'',
  	 		rownumbers:true,
  	 		pagination:true,
  	 		idField:'userid',
  	 		sortName:'seq',
		 	sortOrder:'asc',
  	 		singleSelect:false,
  	 		pageSize:100,
  	 		checkOnSelect:true,
  	 		selectOnCheck:true,
			toolbar:'#sysUser-query-showUserList',
		    url:'accesscontrol/showSysUserList.do',
		    onDblClickRow:function(rowIndex, rowData){
		    	var url = "accesscontrol/showSysUserInfoPage.do?type=show&userid="+rowData.userid;
	       		top.addOrUpdateTab(url,'系统用户详情');
		    }
		}).datagrid("columnMoving");
		
		$("#sysUser-form-roleid").widget({
			referwid:'RL_T_AC_AUTHORITY',
			width:'150',
			singleSelect:true
		});
		$("#sysUser-form-deptid").widget({
			referwid:'RT_T_SYS_DEPT',
			width:'150',
			singleSelect:true,
			onlyLeafCheck:false
		});
		$("#sysUser-form-workjobid").widget({
			referwid:'RL_T_BASE_WORKJOB',
			width:'150',
			singleSelect:true
		});
		//回车事件
		controlQueryAndResetByKey("sysUser-queay-queryUserList","sysUser-queay-reloadUserList");
		
		//查询
		$("#sysUser-queay-queryUserList").click(function(){
       		var queryJSON = $("#sysUser-form-userList").serializeJSON();
       		$("#sysUser-table-showSysUserList").datagrid("load",queryJSON);
		});
		//重置
		$("#sysUser-queay-reloadUserList").click(function(){
			$("#sysUser-form-roleid").widget("clear");
			$("#sysUser-form-deptid").widget("clear");
			$("#sysUser-form-workjobid").widget("clear");
			$("#sysUser-form-userList")[0].reset();
			$("#sysUser-table-showSysUserList").datagrid("load",{});
       	});
       	
       	//新增按钮
		$("#sysUser-add-addUser").click(function(){
			var url = "accesscontrol/showSysUserAddPage.do?type=add";
			top.addTab(url,'系统用户新增');
       	});
       	
       	//修改按钮 
       	$("#sysUser-edit-editUser").click(function(){
       		var sysUser = $("#sysUser-table-showSysUserList").datagrid('getSelected');
       		if(null == sysUser){
       			$.messager.alert("提醒","请选择系统用户!");
       			return false
       		}
       		var url = "accesscontrol/showSysUserEditPage.do?type=edit&userid="+sysUser.userid;
       		if (top.$('#tt').tabs('exists','系统用户修改')){
       			top.$('#tt').tabs('select','系统用户修改')
       			top.updateTab(url,'系统用户修改');
			}
			else{
				top.addTab(url,'系统用户修改');
			}
       	});
       	
       	//启用按钮
       	$("#sysUser-enable-enableUser").click(function(){
       		var rows = $("#sysUser-table-showSysUserList").datagrid('getChecked');
       		if(rows.length == 0){
       			$.messager.alert("提醒","请选择系统用户!");
       			return false
       		}
       		var ids = "";
       		for(var i=0;i<rows.length;i++){
       			if(ids == ""){
       				ids = rows[i].userid;
       			}else{
       				ids += "," + rows[i].userid;
       			}
       		}
       		$.messager.confirm('提醒','确定启用该系统用户吗?',function(r){
       			if(r){
	       			$.ajax({   
			            url :'accesscontrol/enableSysUser.do?ids='+ids,
			            type:'post',
			            dataType:'json',
			            async: false,
			            success:function(json){
			            	$.messager.alert("提醒",json.msg);
		            		$("#sysUser-table-showSysUserList").datagrid('reload');
		            		$("#sysUser-table-showSysUserList").datagrid('clearSelections');
			            }
			        });
       			}
       		});
       	});
       	
       	//禁用按钮
       	$("#sysUser-disable-disableUser").click(function(){
       		var rows = $("#sysUser-table-showSysUserList").datagrid('getChecked');
       		if(rows.length == 0){
       			$.messager.alert("提醒","请选择系统用户!");
       			return false
       		}
       		var ids = "";
       		for(var i=0;i<rows.length;i++){
       			if(ids == ""){
       				ids = rows[i].userid;
       			}else{
       				ids += "," + rows[i].userid;
       			}
       		}
       		$.messager.confirm('提醒','确定禁用该系统用户吗?',function(r){
       			if(r){
       				$.ajax({   
			            url :'accesscontrol/disableSysUser.do?ids='+ids,
			            type:'post',
			            dataType:'json',
			            async: false,
			            success:function(json){
			            	$.messager.alert("提醒",json.msg);
		            		$("#sysUser-table-showSysUserList").datagrid('reload');
		            		$("#sysUser-table-showSysUserList").datagrid('clearSelections');
			            }
			        });
       			}
       		});
       	});
       	
       	//删除按钮
       	$("#sysUser-remove-removeUser").click(function(){
       		var rows = $("#sysUser-table-showSysUserList").datagrid('getChecked');
       		if(rows.length == 0){
       			$.messager.alert("提醒","请选择系统用户!");
       			return false
       		}
       		var ids = "";
       		for(var i=0;i<rows.length;i++){
       			if(ids == ""){
       				ids = rows[i].userid;
       			}else{
       				ids += "," + rows[i].userid;
       			}
       		}
       		$.messager.confirm('提醒','确定删除吗?',function(r){   
			    if (r){   
			        $.ajax({   
			            url :'accesscontrol/deleteSysUser.do?ids='+ids,
			            type:'post',
			            dataType:'json',
			            async: false,
			            success:function(json){
			            	$.messager.alert("提醒",json.msg);
			            	var queryJSON = $("#sysUser-form-userList").serializeJSON();
       						$("#sysUser-table-showSysUserList").datagrid("load",queryJSON);
		            		//$("#sysUser-table-showSysUserList").datagrid("load",{});
		            		//$("#sysUser-table-showSysUserList").datagrid('clearSelections');
			            }
			        });
			    }   
			});  
       	});
       	
       	//复制按钮
       	$("#sysUser-copy-copyUser").click(function(){
       		var sysUser = $("#sysUser-table-showSysUserList").datagrid('getSelected');
       		if(null == sysUser){
       			$.messager.alert("提醒","请选择系统用户!");
       			return false
       		}
       		var url = "accesscontrol/showSysUserCopyPage.do?type=copy&userid="+sysUser.userid;
       		if (top.$('#tt').tabs('exists','系统用户复制')){
       			top.$('#tt').tabs('select','系统用户复制')
       			top.updateTab(url,'系统用户复制');
			}
			else{
				top.addTab(url,'系统用户复制');
			}
       	});
       	
       	//密码重置
       	$("#sysUser-resetPwd-resetPwdUser").click(function(){
       		var rows = $("#sysUser-table-showSysUserList").datagrid('getChecked');
       		if(rows.length == 0){
       			$.messager.alert("提醒","请选择系统用户!");
       			return false
       		}
       		var ids = "";
       		for(var i=0;i<rows.length;i++){
       			if(ids == ""){
       				ids = rows[i].userid;
       			}else{
       				ids += "," + rows[i].userid; 
       			}
       		}
       		$.messager.confirm('提醒','确定重置该系统用户密码吗?',function(r){
       			if(r){
	       			$.ajax({   
			            url :'accesscontrol/resetSysUserPwd.do?ids='+ids,
			            type:'post',
			            dataType:'json',
			            async: false,
			            success:function(json){
			            	if(json.flag){
			            		$.messager.alert("提醒","密码重置成功!");
			            	}
			            	else{
			            		$.messager.alert("提醒","密码重置失败!");
			            	}
			            }
			        });
       			}
       		});
       	});
       	
       	//导入
		$("#sysUser-in-inUserList").Excel('import',{
			clazz: "sysUserService", //spring中注入的类名
	 		method: "addDRSysUser", //插入数据库的方法
	 		tn: "t_sys_user", //表名
            module: 'accesscontrol', //模块名，
	 		pojo: "SysUser", //实体类名，将和模块名组合成com.hd.agent.basefiles.model.DepartMent
			onClose: function(){ //导入成功后窗口关闭时操作，
		         //$("#department-table-departmentList").datagrid('clearSelections');	  
		         $("#sysUser-table-showSysUserList").datagrid('reload');	//更新列表	                                                                                        
			}
		});
		
		//导出 
		$("#sysUser-out-outUserList").Excel('export',{
			queryForm: "#sysUser-form-userList", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
	 		tn: 't_sys_user' //表名
		});
		//踢出用户
		$("#sysUser-kickout-sysuser").click(function(){
			var rows = $("#sysUser-table-showSysUserList").datagrid('getChecked');
       		if(rows.length == 0){
       			$.messager.alert("提醒","请选择系统用户!");
       			return false
       		}
       		var ids = "";
       		for(var i=0;i<rows.length;i++){
       			if(ids == ""){
       				ids = rows[i].userid;
       			}else{
       				ids += "," + rows[i].userid; 
       			}
       		}
       		$.messager.confirm('提醒','是否踢出选中用户?',function(r){
       			if(r){
	       			$.ajax({   
			            url :'accesscontrol/kickOutSysUser.do?ids='+ids,
			            type:'post',
			            dataType:'json',
			            async: false,
			            success:function(json){
			            	if(json.flag){
			            		$("#sysUser-table-showSysUserList").datagrid('reload');
			            		$.messager.alert("提醒","踢出成功!");
			            	}
			            	else{
			            		$.messager.alert("提醒","踢出失败!");
			            	}
			            }
			        });
       			}
       		});
		});

        $("#sysUser-upload-location").click(function(){
            var rows = $("#sysUser-table-showSysUserList").datagrid('getChecked');
            if(rows.length == 0){
                $.messager.alert("提醒","请选择系统用户!");
                return false
            }
            var ids = "";
            for(var i=0;i<rows.length;i++){
                if(ids == ""){
                    ids = rows[i].userid;
                }else{
                    ids += "," + rows[i].userid;
                }
            }
            $.messager.confirm('提醒','是否强制上传用户坐标?</br>注：强制上传坐标有一定时间延迟，或者用户手机未打开APP也将上传失败',function(r){
                if(r){
                    $.ajax({
                        url :'accesscontrol/uploadlocation.do?ids='+ids,
                        type:'post',
                        dataType:'json',
                        async: false,
                        success:function(json){
                            if(json.flag){
                                $("#sysUser-table-showSysUserList").datagrid('reload');
                                $.messager.alert("提醒","请求成功!");
                            }
                            else{
                                $.messager.alert("提醒","请求失败!");
                            }
                        }
                    });
                }
            });
        });
	});
	</script>
  </body>
</html>
