<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
  </head>
  
  <body>
  <table id="notice-table-noticePublishReceiveuserList" ></table>
	<script type="text/javascript">
 		$(document).ready(function(){
            $("#notice-table-noticePublishReceiveuserList").datagrid({
                fit:true,
                method:'post',
                //title:'发布范围(角色)',
                rownumbers:true,
                pagination:false,
                singleSelect:true,
                url:'message/notice/showNoticePublishRangeRolePageList.do?noticeid=${param.noticeid}',
                columns:[[
                    {field:'rolename',title:'名称',width:200},
                    {field:'roledesc',title:'描述',width:100,hidden:true}
                ]]
            });
 		});
 	</script>
  </body>
</html>

