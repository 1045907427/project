<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>登录规则添加</title>
  </head>
  
  <body>
  <div id="sysUser-div-add-usercontrolList">
		<form action="" method="post" id="sysUser-form-querysysuser">
	  		<table>
				<tr>
					<td>姓名:</td>
					<td><input name="name" style="width:150px" /></td>
					<td>角色:</td>
					<td>
						<input id="sysUser-form-querysysuser-roleid" name="roleid" style="width:150px" />
					</td>
				</tr>
				<tr>
	  				<td>所属部门:</td>
	  				<td>
	  					<input id="sysUser-form-querysysuser-departmentid" name="departmentid" style="width:150px" />
	  				</td>
	  				<td>是否手机用户:</td>
					<td>
						<select name="isphone" style="width: 150px;">
							<option></option>
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</td>
	  				<td>
						<a href="javaScript:void(0);" id="sysUser-querysysuser-queryUserList" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="sysUser-querysysuser-reloadUserList" class="button-qr">重置</a>
					</td>
	  			</tr>
			</table>
	   </form>
	</div>
  <div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'north',border:false">
  			<form action="accesscontrol/addSysLoginRule.do" method="post" id="sysUser-form-add-usercontrolList">
	  		<table  border="0">
	  			<tr>
	  				<td width="120" style="text-align: right;">系统登录规则:</td>
	  				<td style="text-align: left;">
	  					<select name="logintype" style="width: 100px;">
	  						<option value="1">内网登录</option>
	  						<option value="2">允许外网登录</option>
	  						<option value="3">指定IP地址登录</option>
	  					</select>
	  				</td>
	  				<td colspan="2" style="text-align: left;">注：1.内网登录需要填写IP地址段，不填写默认192.168.*.*IP地址都可以登录。2.选择指定IP地址登录将默认指定用户最近一次登录的IP地址</td>
	  			</tr>
	  			<tr>
	  				<td style="text-align: right;">IP地址段：</td>
	  				<td colspan="3" style="text-align: left;">
	  					<input type="text" class="easyui-validatebox" validType="ip" style="width: 150px;" name="ip1"/> 到 <input type="text" class="easyui-validatebox" validType="ip" style="width: 150px;" name="ip2"/>
	  				</td>
	  			</tr>
	  			<tr>
	  				<td style="text-align: right;">手机登录规则:</td>
	  				<td>
	  					<select name="ptype" style="width: 100px;">
	  						<option value="1">不限制手机</option>
	  						<option value="2">绑定手机SID</option>
	  					</select>
	  				</td>
	  				<td colspan="2" style="text-align: left;">
	  				注：绑定手机SID，将使用用户最近一次登录的手机SID
	  				<input type="hidden" id="sysUser-userid-add-usercontrolList" name="userid">
	  				</td>
	  			</tr>
	  		</table>
	   </form>
	    </div>
  		<div data-options="region:'center',border:false">
  			<table id="sysUser-table-add-usercontrolList"></table>
	    </div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="height:30px;text-align: right;">
	  			<input type="button" value="确 定" name="savegoon" id="sysUser-form-addbutton-usercontrolList" />
  			</div>
  		</div>
  	</div>
   <script type="text/javascript">
		$(function(){
			$("#sysUser-table-add-usercontrolList").datagrid({ 
				columns:[[
							{field:'ck',checkbox:true,width:50},
							{field:'userid',title:'用户编码',width:100,hidden:true},
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
							{field:'lastip',title:'最近登录IP',width:100},
					        {field:'lastsid',title:'手机最近登录SID',width:100}
				        ]],
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		idField:'userid',
	  	 		singleSelect:false,
	  	 		pageSize:200,
	  	 		checkOnSelect:true,
	  	 		selectOnCheck:true,
				toolbar:'#sysUser-div-add-usercontrolList',
			    url:'accesscontrol/showSysUserList.do'
			}).datagrid("columnMoving");
			
			$("#sysUser-form-querysysuser-roleid").widget({
				referwid:'RL_T_AC_AUTHORITY',
				width:'150',
				singleSelect:true
			});
			$("#sysUser-form-querysysuser-departmentid").widget({
				referwid:'RL_T_BASE_DEPATMENT',
				width:'150',
				singleSelect:true,
				onlyLeafCheck:false
			});
			$("#sysUser-querysysuser-queryUserList").click(function(){
				var queryJSON = $("#sysUser-form-querysysuser").serializeJSON();
	       		$("#sysUser-table-add-usercontrolList").datagrid("load",queryJSON);
			});
			//重置
			$("#sysUser-querysysuser-reloadUserList").click(function(){
				$("#sysUser-form-querysysuser-roleid").widget("clear");
				$("#sysUser-form-querysysuser-departmentid").widget("clear");
				$("#sysUser-form-querysysuser")[0].reset();
				$("#sysUser-table-add-usercontrolList").datagrid("load",{});
	       	});
			
			$("#sysUser-form-add-usercontrolList").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	//转为json对象
			    	var json = $.parseJSON(data);
			    	if(json.flag){
			    		$.messager.alert("提醒",'添加成功！');
			    		$("#sysuser-accesscontrol-rule-table").datagrid("reload");
			    		$('#sysuser-accesscontrol-rule-adddiv-content').window("close");
			    	}else{
			    		$.messager.alert("提醒",'添加失败！');
			    	}
			    }  
			});
			$("#sysUser-form-addbutton-usercontrolList").click(function(){
				var json = $("#sysUser-table-add-usercontrolList").datagrid('getChecked');
				var userid = "";
				for(var i=0;i<json.length;i++){
					if(userid==""){
						userid = json[i].userid;
					}else{
						userid += "," +json[i].userid;
					}
				}
				$("#sysUser-userid-add-usercontrolList").val(userid);
				$.messager.confirm("提醒", "是否添加用户登录规则?", function(r){
					if (r){
						$("#sysUser-form-add-usercontrolList").submit();
					}
				});
			});
			
		});
   </script>
  </body>
</html>
