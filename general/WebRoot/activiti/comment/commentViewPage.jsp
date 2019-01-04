<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>查看流程页面</title>
  </head>
  <body>
  	<style type="text/css">
  		.tableclass {width:100%;border-collapse:collapse;}
  		.tdclass{border:1px solid #aaaaaa;padding:3px;line-height:24px;}
  		.trhidden{display: none;}
  	</style>
  	<div class="easyui-layout" data-options="fit:true">
        <div id="activiti-tabs-commentViewPage" class="easyui-tabs" data-options="fit:true,border:false">
            <div title="审批信息" style="padding:8px;" data-options="cache:true,href:'act/commentListPage.do?type=3&id=${param.id }'">
            </div>
            <div title="流程图" style="padding:8px;" data-options="cache:true,href:'act/commentImgPage.do?type=3&id=${param.id }'">
            </div>
        </div>
	</div>
	<script type="text/javascript">


	</script>
  </body>
</html>
