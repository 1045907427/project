<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>用户登录控制页面</title>
	<%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<div id="sysuser-accesscontrol-rule-div" style="padding: 0px">
  		<div class="buttonBG">
  			<security:authorize url="/accesscontrol/showSysLoginRuleAddPage.do">
  			<a href="javaScript:void(0);" id="sysUser-add-addSysLoginRule" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增登录规则">新增</a>
  			</security:authorize>
  			<security:authorize url="/accesscontrol/deleteSysLoginRule.do">
  			<a href="javaScript:void(0);" id="sysUser-remove-SysLoginRule" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'" title="删除登录规则">删除</a>
  			</security:authorize>
  		</div>
  		<form action="" id="sysUser-form-usercontrolList" method="post">
			<table>
				<tr>
					<td>姓名:</td>
					<td><input name="name" style="width:150px" /></td>
					<td>角色:</td>
					<td>
						<input id="sysUser-form-roleid" name="roleid" style="width:150px" />
					</td>
				</tr>
				<tr>
	  				<td>所属部门:</td>
	  				<td>
	  					<input id="sysUser-form-departmentid" name="departmentid" style="width:150px" />
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
						<a href="javaScript:void(0);" id="sysUser-queay-queryUserList" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="sysUser-queay-reloadUserList" class="button-qr">重置</a>
					</td>
	  			</tr>
			</table>
		</form>
  	</div>
  	<table id="sysuser-accesscontrol-rule-table"></table>
  	<div id="sysuser-accesscontrol-rule-adddiv"></div>
  	<div id="sysuser-accesscontrol-rule-detail"></div>
	<script type="text/javascript">
	//列冻结、拖拽、样式保存
	var userListColJson = 
		$("#sysuser-accesscontrol-rule-table").createGridColumnLoad({
				frozenCol : [[]],
				commonCol : [[ 
								{field:'ck',checkbox:true},
				                {field:'userid',title:'用户编码',width:100,hidden:true},
								{field:'username',title:'用户名',width:80,sortable:true},
						        {field:'name',title:'姓名',width:80,sortable:true},  
						        {field:'departmentid',title:'部门编号',width:80,hidden:true,sortable:true},
						        {field:'departmentname',title:'部门名称',width:100},
						        {field:'isphone',title:'是否手机用户',width:80,sortable:true,
						        	formatter:function(val,rowData,rowIndex){
										if(val == '1'){
						        			return '是';
						        		}else if(val == '0'){
						        			return '否';
						        		}
									}
						        },
						        {field:'logintype',title:'系统登录规则',width:80,sortable:true,
						        	formatter:function(val,rowData,rowIndex){
										if(val == '1'){
						        			return '内网登录';
						        		}else if(val == '2'){
						        			return '外网登录';
						        		}else if(val == '3'){
						        			return '指定IP地址';
						        		}
									}
						        },
						        {field:'ip',title:'ip地址',width:120,sortable:true},  
						        {field:'ptype',title:'手机登录规则',width:80,sortable:true,
						        	formatter:function(val,rowData,rowIndex){
										if(val == '1'){
						        			return '不限制手机';
						        		}else if(val == '2'){
						        			return '绑定手机sid';
						        		}
									}
						        },
						        {field:'psid',title:'手机SID',width:330,sortable:true}
						    ]]
	});
	$(function(){
		$("#sysuser-accesscontrol-rule-table").datagrid({ 
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
  	 		singleSelect:false,
  	 		checkOnSelect:true,
  	 		selectOnCheck:true,
  	 		pageSize:200,
			toolbar:'#sysuser-accesscontrol-rule-div',
		    url:'accesscontrol/showSysLoginRuleList.do',
		    onDblClickRow:function(rowIndex, rowData){
		    	$('#sysuser-accesscontrol-rule-detail').dialog({  
				    title: '登录规则修改', 
				    width: 650,  
				    height: 400,  
				    closed: false,  
				    cache: false,
				    modal:true,
				    href: 'accesscontrol/showSysLoginRuleEditPage.do?userid='+rowData.userid
				});
		    	$('#sysuser-accesscontrol-rule-detail').dialog("open");
		    }
		}).datagrid("columnMoving");
		$("#sysUser-add-addSysLoginRule").click(function(){
			$('<div id="sysuser-accesscontrol-rule-adddiv-content"></div>').appendTo('#sysuser-accesscontrol-rule-adddiv');
    		$('#sysuser-accesscontrol-rule-adddiv-content').window({  
			    title: '登录规则添加', 
			    fit:true,
			    collapsible:false,
			    minimizable:false,
			    maximizable:true,
			    resizable:true,
			    modal:true,
			    closed: true,  
			    cache: false,  
			    href: 'accesscontrol/showSysLoginRuleAddPage.do',
			    onClose:function(){
			    	$('#sysuser-accesscontrol-rule-adddiv-content').window("destroy");
			    }
			});
			$('#sysuser-accesscontrol-rule-adddiv-content').window("open");
		});
		$("#sysUser-remove-SysLoginRule").click(function(){
			var json = $("#sysuser-accesscontrol-rule-table").datagrid('getChecked');
			var ids = "";
			for(var i=0;i<json.length;i++){
				if(ids==""){
					ids = json[i].userid;
				}else{
					ids += "," +json[i].userid;
				}
			}
			$.messager.confirm('提醒','确定删除吗?',function(r){   
			    if (r){   
			    	loading("提交中..");
			        $.ajax({   
			            url :'accesscontrol/deleteSysLoginRule.do?ids='+ids,
			            type:'post',
			            dataType:'json',
			            success:function(json){
			            	loaded();
			            	if(json.flag){
			            		$.messager.alert("提醒","删除成功");
			            	}else{
			            		$.messager.alert("提醒","删除失败");
			            	}
			            	$("#sysuser-accesscontrol-rule-table").datagrid("reload");
			            }
			        });
			    }   
			});  
		});
		$("#sysUser-form-roleid").widget({
			referwid:'RL_T_AC_AUTHORITY',
			width:'150',
			singleSelect:true
		});
		$("#sysUser-form-departmentid").widget({
			referwid:'RL_T_BASE_DEPATMENT',
			width:'150',
			singleSelect:true,
			onlyLeafCheck:false
		});
		$("#sysUser-queay-queryUserList").click(function(){
			var queryJSON = $("#sysUser-form-usercontrolList").serializeJSON();
       		$("#sysuser-accesscontrol-rule-table").datagrid("load",queryJSON);
		});
		//重置
		$("#sysUser-queay-reloadUserList").click(function(){
			$("#sysUser-form-roleid").widget("clear");
			$("#sysUser-form-departmentid").widget("clear");
			$("#sysUser-form-usercontrolList")[0].reset();
			$("#sysuser-accesscontrol-rule-table").datagrid("load",{});
       	});
	});
	</script>
  </body>
</html>
