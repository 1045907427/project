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
  <table id="notice-table-noticePublishRangeUserList" ></table>
	<script type="text/javascript">
 		$(document).ready(function(){
            $("#notice-table-noticePublishRangeUserList").datagrid({
                fit:true,
                method:'post',
                //title:'发布范围(人员)',
                rownumbers:true,
                pagination:true,
                singleSelect:true,
                sortName:'departmentid',
                sortOrder:'asc',
                pageSize:100,
                url:'message/notice/showNoticePublishRangeUserPageList.do?noticeid=${param.noticeid}',
                columns:[[
                    {field:'departmentname',title:'部门名称',width:150,sortable:true},
                    {field:'name',title:'姓名',width:150,sortable:true}
                ]]
            });
 		});
 	</script>
  </body>
</html>

