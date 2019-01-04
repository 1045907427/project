<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
    <title>系统用户复制</title>
    <%@include file="/include.jsp" %> 
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
  		<form id="sysUser-form-copySysUser" action="accesscontrol/sysUserCopyAdd.do" method="post">
	  		<div data-options="region:'north',border:false" style="height: 110px;">
	  			<table cellpadding="1px" cellspacing="0" border="0">
					<tr>
						<td style="text-align: right;">用户名:</td>
						<td><input type="text" name="user.username"  class="easyui-validatebox" required="true" validType="usernameCheck" style="width: 130px" />
							<input id="sysUser-hidden-hdDeptName" type="hidden" name="user.departmentname" value="<c:out value="${user.departmentname}"></c:out>"/>
							<input id="sysUser-hidden-hdUserAuthorityid" type="hidden" name="userAuthority.authorityid" value="${userAuthorityStr }" />
							<input id="sysUser-hidden-hdLockFlag" type="hidden" value="${lockFlag }"/>
							<input id="sysUser-hidden-hdState" type="hidden" name="user.state"/>
						</td>
						<td style="text-align: right;">姓名:</td>
						<td><input type="text" name="user.name" value="<c:out value="${user.name }"></c:out>" class="easyui-validatebox" validType="chineseChar[20]" style="width: 130px" /></td>
						<td style="text-align: right;">部门名称:</td>
						<td><input type="text" id="sysUser-widget-Dept" name="user.departmentid" value="${user.departmentid }" style="width: 130px" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">工作岗位:</td>
						<td><input type="text" id="sysUser-widget-workjob" name="user.workjobid" style="width: 130px" value="${user.workjobid }"/></td>
						<td style="text-align: right;">关联人员:</td>
						<td><input id="sysUser-widget-personnel" name="user.personnelid" value="${user.personnelid}" style="width: 130px" /></td>
						<td style="text-align: right;">状态:</td>
						<td><select id="sysUser-select-state" disabled="disabled" style="width: 130px">
								<option value="4">新增</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">允许修改密码:</td>
						<td>
							<select style="width: 130px;" name="user.ispwd">
								<option value="1" <c:if test="${user.ispwd=='1' }">selected="selected"</c:if>>是</option>
								<option value="0" <c:if test="${user.ispwd=='0' }">selected="selected"</c:if>>否</option>
							</select>
						</td>
						<td style="text-align: right;">是否手机用户:</td>
						<td>
							<select style="width: 130px;" name="user.isphone">
								<option value="0" <c:if test="${user.isphone=='0' }">selected="selected"</c:if>>否</option>
								<option value="1" <c:if test="${user.isphone=='1' }">selected="selected"</c:if>>是</option>
							</select>
						</td>
						<td style="text-align: right;">序号:</td>
						<td><input type="text" name="user.seq" style="width: 130px" class="easyui-numberbox" value="${user.seq }"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">性别:</td>
						<td><input type="text" id="sysUser-combobox-sex" name="user.sex" value="${user.sex }" class="easyui-combobox" style="width: 130px" /></td>
						<td style="text-align: right;">出生日期:</td>
						<td><input type="text" name="user.birthday" value="${user.birthday }" style="width:130px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1958-01-01',maxDate:'%y-%M-%ld'})"/></td>
						<td style="text-align: right;">电话:</td>
						<td><input type="text" name="user.telphone" value="${user.telphone }" class="easyui-validatebox" validType="phone" style="width: 130px" /></td>
					</tr>
				</table>
	  		</div>
	  		<div data-options="region:'center',split:false,border:false">
	  			<table id="sysUser-table-authorityList"></table>
	  		</div>
	  		<div data-options="region:'south'" style="text-align: right;height: 30px">
	  			<a href="javaScript:void(0);" id="sysUser-save-saveCopyMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="保存系统用户信息">保存</a>
	  		</div>
  		</form>
  	</div>
  	<script type="text/javascript">
  		//表单数据验证
  		$.extend($.fn.validatebox.defaults.rules, {
  			chineseChar:{
   				validator : function(value,param) { 
		            return value.length < param[0]; 
		        }, 
		        message : '最大允许输入20个字符' 
   			},
   			usernameCheck:{//用户名的唯一性检验
		    	validator: function(value){
		    		var flag = true;
		    		$.ajax({   
			            url :'accesscontrol/usernameCheck.do?username='+value,
			            type:'post',
			            dataType:'json',
			            async: false,
			            success:function(json){
			            	flag =  json.flag;
			            }
			        });
			    	return flag;
		    	},
		    	message:'用户名已存在，请重新输入!'
		    }
  		});
  		
  		//从代码表中获取性别类型 
//  		$('#sysUser-combobox-sex').combobox({
//		    url:'common/sysCodeList.do?type=sex',
//		    valueField:'id',
//		    textField:'name'
//		});
        $("#sysUser-combobox-sex").widget({
            referwid:'RL_T_SYS_CODESEX',
            singleSelect:true,
            width:130
        });
		//部门 
	  	$("#sysUser-widget-Dept").widget({
			name:'t_sys_user',
			col:'departmentid',
			width:'130',
			singleSelect:true,
			onlyLeafCheck:false,
			onSelect:function(data){
				$("#sysUser-hidden-hdDeptName").val(data.name);
			},
			onClear:function(){
				$("#sysUser-hidden-hdDeptName").val("");
			},
	  		onLoadSuccess:function(){
	  			return true;
	  		}
		}); 
		
		//关联人员列表 
	  	$("#sysUser-widget-personnel").widget({
			name:'t_sys_user',
			col:'personnelid',
			width:'130',
			singleSelect:true,
			onlyLeafCheck:true,
			onSelect:function(data){
				//$("#sysUser-hidden-hdPersonnelid").val(data.id);
			},
	  		onLoadSuccess:function(){
	  			return true;
	  		}
		}); 
		
  		//初次加载
  		$(function(){
  			
  			//角色列表
			$('#sysUser-table-authorityList').datagrid({ 
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'角色列表',
	  	 		rownumbers:false,
	  	 		checkOnSelect:true,
	  	 		selectOnCheck:true,
	  	 		idField:'authorityid',
	  	 		singleSelect:false,
				frozenColumns:[[{field:'ck',checkbox:true}]],
			    url:'accesscontrol/showAuthorityList.do',
			    columns:[[ 
			        {field:'authorityname',title:'角色名称',width:100},
			        {field:'description',title:'角色描述',width:240},
			        {field:'alias',title:'角色别名',width:100}
			    ]],
			    onLoadSuccess:function(data){
			    	var userAuthorityArr = "${userAuthorityStr}".split(',');
			    	for(var i=0;i<userAuthorityArr.length;i++){
			    		$('#sysUser-table-authorityList').datagrid("selectRecord",userAuthorityArr[i]);
			    	}
			    	var rows = $('#sysUser-table-authorityList').datagrid("getSelections");
			    	for(var j=0;j<rows.length;j++){
			    		var index = $('#sysUser-table-authorityList').datagrid("getRowIndex",rows[j]);
			    		$('#sysUser-table-authorityList').datagrid("selectRow",index);
			    	}
			    }
			});
			//工作岗位
		  	$("#sysUser-widget-workjob").widget({
				name:'t_sys_user',
				col:'workjobid',
				singleSelect:true,
				width:'130',
				onlyLeafCheck:false,
				onSelect:function(data){
					$('#sysUser-table-authorityList').datagrid("unselectAll");
					$.ajax({   
			            url :'basefiles/getRoleListByWorkjob.do?id='+data.id,
			            type:'post',
			            dataType:'json',
			            success:function(json){
			            	for(var i=0;i<json.length;i++){
			            		var id = json[i];
			            		var index = $('#sysUser-table-authorityList').datagrid("getRowIndex",id);
			            		$('#sysUser-table-authorityList').datagrid("selectRow",index);
			            	}
			            }
			        });
				},
		  		onLoadSuccess:function(){
		  			return true;
		  		}
			});
  			//保存按钮 
			$("#sysUser-save-saveCopyMenu").click(function(){
				$.messager.confirm("提醒", "是否复制新增系统用户信息?", function(r){
					if (r){
						var s ="";
						var sysUserRows = $('#sysUser-table-authorityList').datagrid('getChecked');
						for(var i=0;i<sysUserRows.length;i++){
							s = s + sysUserRows[i].authorityid + ",";
						}
				    	$("#sysUser-hidden-hdUserAuthorityid").val(s);
				    	$("#sysUser-hidden-hdState").val("2");
						$("#sysUser-form-copySysUser").submit();
					}
				});
			});
			$("#sysUser-form-copySysUser").form({  
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
			    	$("#sysUser-form-copySysUser").validateFormSubmit({
			    		json:json,
			    		isSuccess:function(){
			    			tabsWindowURL("/accesscontrol/showSysUserPage.do").$("#sysUser-table-showSysUserList").datagrid('reload');
			    			$.messager.alert("提醒",'复制新增成功');
			    			top.closeTab('系统用户复制');
			    		},
			    		isFail:function(){
			    			$.messager.alert("提醒",'复制新增失败');
			    		}
			    	});
			    }  
			});
  		});
  	</script>
  </body>
</html>
