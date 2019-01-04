<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
  </head>
  
  <body>
	  <div style="height:100%" title="">
        <table id="emailMessage-table-emailSendBoxUserList" ></table>
        <%--<div id="messageEmail-query-emailSendBoxUserList" style="padding:5px;height:auto">		--%>
			<%--<div>--%>
				<%--<a href="javaScript:void(0);" id="messageEmail-btn-sendMobileSms" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-extend-mobilesms'">发短信</a>--%>
			<%--</div>--%>
		<%--</div>--%>
	  </div>
	  <script type="text/javascript">
		  $(document).ready(function(){
			  $("#emailMessage-table-emailSendBoxUserList").datagrid({	
					fit:true,
			 		method:'post',
			 		//title:'邮件主题-${emltitle}',
			 		rownumbers:true,
			 		pagination:true,
			 		singleSelect:true,
					toolbar:'#messageEmail-query-emailSendBoxUserList',
			    	url:'message/email/showEmailUserReadPageListBySend.do?emailid=${emailid}', 
				    columns:[[
						{field : 'idck',checkbox : true}, 
					    {field:'viewflag',title:'状态',width:30,
				        	formatter: function(value,row,index){
			        		if(value=="1"){
			        			return "<span class=\"img-extend-emailnew\" style=\"display:block;\" title=\"未阅读\">&nbsp;</span>";
			        		}else{
			        			return "<span class=\"img-extend-emailopen\" style=\"display:block;\" title=\"已阅读\">&nbsp;</span>";
			        		}
					    }},
				        {field:'recvusername',title:'接收人',width:120},
				        {field:'deptname',title:'所属部门',width:120}	        
				    ]]
				});

			  $("#messageEmail-btn-sendMobileSms").click(function(){
				  var $emailSendBoxUserList=$("#emailMessage-table-emailSendBoxUserList");
					var dataRow = $emailSendBoxUserList.datagrid('getChecked');
			    	if(dataRow==null){
			    		$.messager.alert("提醒","请选择要发送的用户！");
			    		return false;
			    	}
			    	var idarr=Enumerable.from(dataRow).select("$.recvuserid").toArray();
			    	if(idarr.length==0){
			    		$.messager.alert("提醒","请选择要发送的用户！");
			    		return false;
			    	}
			    	try{
				    	top.baseInnerMessageSend(idarr.join(','));
			    	}catch(e){
			    	}
			  });
				
		  });
	  </script>
  </body>
</html>
