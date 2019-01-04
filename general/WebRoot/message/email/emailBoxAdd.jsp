<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>添加自定义邮箱</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true" style="height:100%">			
		<div title="" data-options="region:'center'">
			<div style="padding:20px 10px 10px;">
				<form action="message/email/addEmailBox.do" method="post" id="messageEmail-form-addEmailBox">					
						<div style="height:30px;">
							<div style="float:left;width:100px;line-height:22px;">序号：</div>
							<div style="float:left">
			    				<input type="text" id="messageEmail-form-addEmailBox-boxorderno" name="emailBox.boxorderno" style="width:200px;" class="easyui-validatebox" required="true"  value="0" />
			    			</div>
							<div style="clear:both"></div>
						</div>
						<div style="height:30px;">
							<div style="float:left;width:100px;line-height:22px;">名称：</div>
							<div style="float:left">
			    				<input type="text" id="messageEmail-form-addEmailBox-title" name="emailBox.title" style="width:200px;" class="easyui-validatebox" required="true" />
			    			</div>
							<div style="clear:both"></div>
						</div>	
				</form>
			</div>
		</div>		
		<div data-options="region:'south'" style="height: 40px;">
			<div class="buttonBG" style="text-align: right;">
	    		<a href="javaScript:void(0);" id="messageEmail-form-addEmailBox-btn-saveEmailBox" class="easyui-linkbutton" data-options="plain:false,iconCls:'button-save'">保存</a>
	   		</div>		
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#messageEmail-form-addEmailBox-btn-saveEmailBox").click(function(){
				$.messager.confirm("提醒","是否保存此邮箱?",function(r){
    				if(r){		    							
			    		$("#messageEmail-form-addEmailBox").submit();
    				}
	    		});
			});
			$("#messageEmail-form-addEmailBox").form({
	    			onSubmit: function(){
	    				var flag = $(this).form('validate');
	    				if(flag==false){
	    					return false;
	    				}
	    			},
	    			success:function(data){
	    				//$.parseJSON()解析JSON字符串 
	    				var json=$.parseJSON(data);
	    				if(json.flag==true){
	    					$.messager.alert("提醒","添加成功!");
	    					$("#messageEmail-table-emailBoxList").datagrid('reload');
	    					$("#messageEmail-dialog-emailBoxOper").dialog('close');
	    					try{
	    						emailPage_showMyEmailBoxList();
	    					}catch(e){
	    					}
	    				}
	    				else{
	    					$.messager.alert("提醒",( json.msg|| "添加失败！"));
	    				}
	    			}
	    	});
		});
	</script>
  </body>
</html>
