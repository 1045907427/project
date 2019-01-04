<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
    <form action="common/addShortcut.do" method="post" id="protal-shortcut-form">
	   	<div class="pageContent">
			<p>
				<label>页面:</label>
				<input type="text" id="protal-shortcut-menu" required="true" style="width:200px;" name="shortcut.operateid" />
			</p>
			<p>
				<label>收藏别名:</label>
				<input type="text" id="protal-shortcut-aliasname" class="easyui-validatebox" required="true" style="width:200px;" name="shortcut.aliasname" />
			</p>
	    </div>
    </form>
    <script type="text/javascript">
    	$(function(){
    		$("#protal-shortcut-save").click(function(){
    			$("#protal-shortcut-form").submit();
    		});
    		$("#protal-shortcut-menu").widget({
    			referwid:'RT_T_AC_OPERATE_1',
    			singleSelect:true,
    			treeDistint:true,
    			treePName:false,
    			onSelect:function(data){
    				$("#protal-shortcut-aliasname").attr("value",data.description);
    			},
    			onClear:function(){
    				$("#protal-shortcut-aliasname").attr("value","");
    			}
    		});
    		$("#protal-shortcut-form").form({  
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
			    	if(json.flag==true){
			    		$('#protal-shortcut-dialog').dialog("close");
			    		$("#tools-div-dialog").dialog("refresh");
			    		$.messager.alert("提醒","添加成功!");
			    	}else{
			    		$.messager.alert("警告","添加失败!");
			    	}
			    }  
			}); 
    	});
    </script>
  </body>
</html>
