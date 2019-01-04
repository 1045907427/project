<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
    <title>系统用户新增</title>
    <%@include file="/include.jsp" %> 
  </head>
  
  <body >
  	<div class="easyui-layout" data-options="fit:true,border:false">
  		<form id="sysUser-form-addSysUser" action="accesscontrol/addSysUser.do" method="post">
	  		<div data-options="region:'north',border:false" style="height: 140px;">
	  			<table cellpadding="1px" cellspacing="0" border="0">
					<tr>
						<td style="text-align: right;">用户名:</td>
						<td><input type="text" name="user.username" class="easyui-validatebox" required="true" validType="usernameCheck" style="width: 130px" />
							<input id="sysUser-hidden-hdDeptName" type="hidden" name="user.departmentname" />
							<input id="sysUser-hidden-hdUserAuthorityid" type="hidden" name="userAuthority.authorityid" />
							<input id="sysUser-hidden-hdState" type="hidden" name="user.state" value="2"/>
						</td>
						<td style="text-align: right;">姓名:</td>
						<td><input type="text" id="user-name" name="user.name" class="easyui-validatebox" validType="chineseChar[20]" style="width: 130px" /></td>
						<td style="text-align: right;">部门名称:</td>
						<td><input id="sysUser-widget-Dept" type="text" name="user.departmentid" style="width: 130px" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">工作岗位:</td>
						<td><input type="text" id="sysUser-widget-workjob" name="user.workjobid" style="width: 130px" /></td>
						<td style="text-align: right;">关联人员:</td>
						<td><input type="text" id="sysUser-widget-personnel" name="user.personnelid" style="width: 130px" /></td>
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
								<option value="1" selected="selected">是</option>
								<option value="0">否</option>
							</select>
						</td>
						<td style="text-align: right;">是否手机用户:</td>
						<td>
							<select style="width: 130px;" name="user.isphone">
								<option value="0">否</option>
								<option value="1" selected="selected">是</option>
							</select>
						</td>
						<td style="text-align: right;">是否上传定位:</td>
						<td>
							<select style="width: 130px;" name="user.isuploadlocation">
								<option value="0">否</option>
								<option value="1" selected="selected">是</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">性别:</td>
						<td><input id="sysUser-combobox-sex" type="text" name="user.sex" value="2" style="width: 130px" /></td>
						<td style="text-align: right;">出生日期:</td>
						<td><input type="text" name="user.birthday" style="width:130px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1958-01-01',maxDate:'%y-%M-%ld'})"/></td>
						<td style="text-align: right;">序号:</td>
						<td><input type="text" name="user.seq" style="width: 130px" class="easyui-numberbox"/></td>					
					</tr>
					<tr>
						<td style="text-align: right;">联系电话:</td>
						<td colspan="5"><input type="text" name="user.telphone" value="" style="width: 130px" /></td>		
					</tr>
				</table>
	  		</div>
	  		<div data-options="region:'center',split:false,border:false">
	  			<table id="sysUser-table-authorityList"></table>
	  		</div>
	  		<div data-options="region:'south'" align="right" style="height: 30px;">
				<a href="javaScript:void(0);" id="sysUser-save-saveMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="保存系统用户信息">保存</a>
			</div>  
  		</form>
  	</div>
  	<script type="text/javascript">
        var associate = '${associate}';
        associate =  $.parseJSON(associate);
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
	  		width:'130',
			name:'t_sys_user',
			col:'departmentid',
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
	  		width:'130',
			name:'t_sys_user',
			col:'personnelid',
			singleSelect:true,
			onlyLeafCheck:true,
			onSelect:function(data){
                var key = data.id ;
                if(key in associate){
                    $.messager.alert("提醒","关联失败，该用户已关联系统用户:"+ associate[key]);
                }else{
                    $.messager.alert("提醒","关联成功");
                }
				//$("#sysUser-hidden-hdPersonnelid").val(data.id);
			},
	  		onLoadSuccess:function(){
	  			return true;
	  		}
		}); 
		
		//工作岗位
	  	$("#sysUser-widget-workjob").widget({
	  		width:'130',
			name:'t_sys_user',
			col:'workjobid',
			singleSelect:true,
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
		
		//初次载入操作
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
			    ]]
			});
			//保存按钮 
			$("#sysUser-save-saveMenu").click(function(){
                var personnelid = $("#sysUser-widget-personnel").widget('getValue');
                if(personnelid in associate){
                    $.messager.alert("提醒","关联失败，该用户已关联系统用户:"+ associate[personnelid]);
                    $("#sysUser-widget-personnel").focus();
                    return false ;

                }
				$.messager.confirm("提醒", "是否新增系统用户信息?", function(r){
					if (r){
						var s ="";
						var sysUserRows = $('#sysUser-table-authorityList').datagrid('getChecked');
						for(var i=0;i<sysUserRows.length;i++){
							s = s + sysUserRows[i].authorityid + ",";
						}
				    	$("#sysUser-hidden-hdUserAuthorityid").val(s);
						$("#sysUser-form-addSysUser").submit();
					}
				});
			});
			
			$("#sysUser-form-addSysUser").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	//表单提交前 弹出提交等待页面
			    	//默认方式
			    	//loading();
			    	//可传值
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	//转为json对象
			    	var json = $.parseJSON(data);
			    	$("#sysUser-form-addSysUser").validateFormSubmit({
			    		json:json,
			    		successMsg:'新增成功',
			    		failMsg:'新增失败',
			    		//添加成功后 当添加了isSuccess事件后，添加成功后successMsg将不会弹出提醒框
			    		isSuccess:function(){
			    			tabsWindowURL("/accesscontrol/showSysUserPage.do").$("#sysUser-table-showSysUserList").datagrid('reload');
			    			$.messager.alert("提醒",'添加成功');
			    			top.closeTab('系统用户新增');
			    		},
			    		//添加失败 当添加了isFail事件后，添加成功后failMsg将不会弹出提醒框
			    		isFail:function(){
			    			$.messager.alert("提醒",'添加失败');
			    		}
			    	});
			    }  
			});
		}); 
  	</script>
  </body>
</html>
