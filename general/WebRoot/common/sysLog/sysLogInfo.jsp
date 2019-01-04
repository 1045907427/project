<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>系统用户日志详情</title>
  </head>
  
  <body>
    <div class="pageContent">
    		<p>
    			<label>日志类型:</label>
    			<input type="text" name="sysLog.type" readonly="readonly" value="${sysLog.type }" class="easyui-validatebox"/>
    		</p>
    		<p>
    			<label>日志内容:</label>
    			<input type="text" name="sysLog.content" readonly="readonly" value="${sysLog.content }" class="easyui-validatebox"/>
    		</p>
    		<p>
    			<label>关键字:</label>
    			<input type="text" name="sysLog.keyname" readonly="readonly" value="${sysLog.keyname }" class="easyui-validatebox"/>
    		</p>
    		<p>
    			<label>添加时间:</label>
    			<input type="text" name="sysLog.addtime" readonly="readonly" value="<fmt:formatDate value="${sysLog.addtime }" pattern="yyyy-MM-dd HH:mm:ss" />"  class="easyui-validatebox"/>
    		</p>
    		<p>
    			<label>添加人编号:</label>
    			<input type="text" name="sysLog.userid" readonly="readonly" value="${sysLog.userid }" class="easyui-validatebox"/>
    		</p>
    		<p>
    			<label>添加人姓名:</label>
    			<input type="text" name="sysLog.name" readonly="readonly" value="${sysLog.name }" class="easyui-validatebox"/>
    		</p>
    		<p>
    			<label>ip地址:</label>
    			<input type="text" name="sysLog.ip" readonly="readonly" value="${sysLog.ip }" class="easyui-validatebox"/>
    		</p>
    	</div>
  </body>
</html>
