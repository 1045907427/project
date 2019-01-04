<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>子菜单添加页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  
  <body>
      <form action="accesscontrol/editOperate.do" method="post" id="accesscontrol-form-editMenu">
	   	<div class="pageContent">
			<p>
				<label>按钮名称:</label>
				<input type="text" name="operate.operatename" class="easyui-validatebox" required="true" style="width:200px;" value="${operate.operatename }"/>
			</p>
			<p>
				<label>按钮描述:</label>
				<input type="text" name="operate.description" class="easyui-validatebox" required="true" style="width:200px;" value="${operate.description }"/>
			</p>
			<p>
				<label>链接地址:</label>
				<input type="text" name="operate.url" class="easyui-validatebox" required="true"  style="width:200px;" value="${operate.url }"/>
			</p>
			<p>
				<label>所属菜单:</label>
				<input type="hidden" name="oldPid" value="${operate.pid }"/>
				<input type="text" id="accesscontrol-menuEdit-pid" style="width:200px;" name="operate.pid" value="${operate.pid }"/>
			</p>
	    </div>
	    <input type="hidden" name="operate.operateid" value="${operate.operateid }"/>
    </form>
    <script type="text/javascript">
    	$(function(){
    		$("#accesscontrol-menuEdit-pid").widget({
    			referwid:'RT_T_AC_OPERATE',
    			singleSelect:true,
    			onlyLeafCheck:false
    		});
     		$("#accesscontrol-form-editMenu").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    },  
			    success:function(data){
			    	//转为json对象
			    	var json = $.parseJSON(data);
			        if(json.flag==true){
			        	$.messager.alert("提醒","修改成功");
			        	$('#accesscontrol-table-operateList').datagrid('reload');
			        	$("#accesscontrol-dialog-operate").dialog('close',true);
			        }else{
			        	$.messager.alert("提醒","修改失败");
			        }
			    }  
			}); 
			$("#accesscontrol-edit-editMenuSubmit").click(function(){
				$.messager.confirm("提醒", "是否确定修改?", function(r){
					if (r){
						$("#accesscontrol-form-editMenu").submit();
					}
				});
			});
     	});
    </script>
  </body>
</html>
