<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>审批页面</title>
  </head>
  <body>
  	<div class="easyui-layout" data-options="fit:true">
  		<form action="act/updateCommentInfo.do" method="post" id="activiti-form-commentAddPage">
  			<div data-options="region:'center',border:false">
  				<table>
  					<tr>
  						<td style="width:80px;text-align:right;">审批意见：</td>
  						<td>
  							<textarea rows="5" cols="40" name="comment.comment">${comment.comment }</textarea>
  						</td>
  					</tr>
  				</table>
			    <input type="hidden" id="activiti-id-commentAddPage" value="${comment.id }" name="comment.id"/>
			    <input type="hidden" id="activiti-taskid-commentAddPage" value="${comment.taskid }" name="comment.taskid"/>
			    <input type="hidden" id="activiti-type-commentAddPage" name="type" />
		    </div>
		    <div data-options="region:'south',border:false" style="height:40px;">
		    	<div class="buttonDivR">
	    			<a href="javascript:;" id="activiti-button-commentAddPage" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'">同意</a>
	    		</div>
		    </div>
    	</form>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#activiti-button-commentAddPage").click(function(){
				$("#activiti-form-commentAddPage").form({
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
		  		        if(json.flag==true){
		  		        	$.messager.alert("提醒","审批成功");
		  		        	$("#activiti-datagrid-myWorkPage").datagrid('reload');
		  		        	$("#activiti-dialog-myWorkPage").dialog('close');
		  		        }
		  		        else{
		  		        	$.messager.alert("提醒","审批失败");
		  		        }
		  		    }
		  		});
				$.messager.confirm("提醒","确定同意通过该信息?",function(r){
		  			if(r){
			  			$("#activiti-type-commentAddPage").val("2");
		  				$("#activiti-form-commentAddPage").submit();
		  			}
		  		});
			});
		});
	</script>
  </body>
</html>
