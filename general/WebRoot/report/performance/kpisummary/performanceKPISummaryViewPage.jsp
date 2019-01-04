<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>部门考核指标数据汇总页面</title>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center">
    			<form action="" method="post" id="performanceKPISummary-form-edit-subject" style="padding: 10px;">
    			<table cellpadding="2" cellspacing="2" border="0">
    				<tr>
	    				<td width="120px" align="right">业务日期:</td>
	   					<td align="left"><input type="text" id="performanceKPISummary-form-view-date-businessdate" name="businessdate" class="easyui-validatebox"  readonly="readonly" value="${performanceKPISummary.businessdate }" style="width:150px"/></td>
	   					<td width="120px" align="right">所属部门:</td>
		    			<td align="left"><input type="text" id="performanceKPISummary-form-view-widget-deptid" name="deptid" readonly="readonly" value="${performanceKPISummary.deptid }" style="width: 150px;"/></td>
	    			</tr>
	    			<tr>
	   					<td colspan="4">
	   						<hr style="border-color:#374e60"/>
	   					</td>
	   				</tr>
	    			<tr>
	    				<td width="120px" align="right">销售额:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.salesamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">含税毛利:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.hsmlamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	    			</tr>
	    			<tr>
	    				<td width="120px" align="right">签呈返还:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.jcfhamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right" colspan="3">&nbsp;</td>
	    			</tr>	
	    			<tr>
	    				<td width="120px" align="right">小计:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.xjamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">小计率:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.xjrate }" readonly="readonly" data-options="precision:2,suffix:'%'" style="width:150px;" /></td>
	    			</tr>    			
	    			<tr>
	   					<td colspan="4">
	   						<hr style="border:1px dashed #374e60;"/>
	   					</td>
	   				</tr>
	   				<tr>
	    				<td width="120px" align="right">费用额:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.fyamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">费用率:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.fyrate }" readonly="readonly" data-options="precision:2,suffix:'%'" style="width:150px;" /></td>
	    			</tr>
	   				<tr>
	    				<td width="120px" align="right">净利额:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.jlamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">净利率:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.jlrate }" readonly="readonly" data-options="precision:2,suffix:'%'" style="width:150px;" /></td>
	    			</tr>
	   				<tr>
	    				<td width="120px" align="right">平均期末库存额:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.pjqmkcamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">平均库存周转天数:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.pjkczzday }" readonly="readonly" data-options="precision:2,suffix:'%'" style="width:150px;" /></td>
	    			</tr>
	   				<tr>
	    				<td width="120px" align="right">平均资金占用额:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.pjzjzyamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">资金利润率:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.zjlrrate }" readonly="readonly" data-options="precision:2,suffix:'%'" style="width:150px;" /></td>
	    			</tr>
	   				<tr>
	    				<td width="120px" align="right">期末代垫费用余额:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.qmddfyyeamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">代垫费占用率:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.ddfyzyrate }" readonly="readonly" data-options="precision:2,suffix:'%'" style="width:150px;" /></td>
	    			</tr>
	    		</table>
	    		
	    	</form>
    		</div>
    	</div>
    </div>
    <script type="text/javascript">		
    	$(function(){
    		
			//所属部门
		  	$("#performanceKPISummary-form-view-widget-deptid").widget({
		  		width:150,
				name:'t_js_departmentcosts',
				col:'deptid',
				singleSelect:true,
				required:true,
				readonly:true
			});
			
    	});
    	
    </script>
  </body>
</html>
