<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>考核指标科目查看</title>
  </head> 
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center">
	    		<form action="" method="post" id="performanceKPISubject-form-add" style="padding: 10px;">
	    			<table cellpadding="2" cellspacing="2" border="0">
	    				<tr>
	    					<td width="70px" align="left">科目代码名:</td>
	    					<td align="left">
	    						<select id="performanceKPISubject-form-view-code" class="len136" disabled="disabled">
		    						<option value="">--请选择--</option>
		    						<c:forEach items="${kpiScoreIndexSubject }" var="list">
			    						<c:choose>
		    								<c:when test="${list.code == performanceKPISubject.code}">
		    									<option value="${list.code }" selected="selected">${list.codename }</option>
		    								</c:when>
		    								<c:otherwise>
		    									<option value="${list.code }">${list.codename }</option>
		    								</c:otherwise>
		    							</c:choose>
		    						</c:forEach>
		    					</select>
		    					<label id="performanceKPISubject-form-view-code-text">${performanceKPISubject.code }</label>
							</td>
	    				</tr>
	    				<tr>
	    					<td width="70px" align="left">所属部门:</td>
	    					<td align="left">	    					
				    			<input id="performanceKPISubject-form-add-widget-deptid" name="performanceKPISubject.deptid" value="${performanceKPISubject.deptid }" type="text" style="width: 200px;" readonly="readonly"/>
	    					</td>
	    				</tr>
	    				<tr>
	    					<td width="70px" align="left">总分:</td>
	    					<td align="left">
				    			<input id="performanceKPISubject-form-add-score" class="easyui-numberbox" data-options="precision:0" name="performanceKPISubject.score" value="${performanceKPISubject.score }" type="text" style="width: 200px" readonly="readonly"/>
	    					</td>
	    				</tr>
	    				<tr>
	    					<td width="70px" align="left">每分价值:</td>
	    					<td align="left">
	    						<input type="text" style="width:200px" id="performanceKPISubject-form-add-svalue" class="easyui-numberbox" data-options="precision:2" name="performanceKPISubject.svalue" value="${performanceKPISubject.svalue }" readonly="readonly"/>
	    					</td>
	    				</tr>
	    				<tr>
	    					<td width="70px" align="left">排序:</td>
	    					<td align="left"><input type="text" name="performanceKPISubject.seq" value="${performanceKPISubject.seq }" readonly="readonly"  style="width:200px;"/></td>
	    				</tr>
	    				<tr>
	    					<td width="70px" align="left">状态:</td>
	    					<td align="left">	    					
				    			<select name="performanceKPISubject.state" style="width:200px;" disabled="disabled">
				    				<option value="1" <c:if test="${performanceKPISubject.state=='1' }">selected="selected"</c:if> >启用</option>
    								<option value="0" <c:if test="${performanceKPISubject.state=='0' }">selected="selected"</c:if> >禁用</option>
				    			</select>
	    					</td>
	    				</tr>	    				
	    				<tr>
	    					<td width="70px" align="left">备注:</td>
	    					<td align="left">	    					
				    			<textarea name="performanceKPISubject.remark" class="easyui-validatebox" validType="maxByteLength[255]" cols="0" rows="0" style="width:200px;height:50px;" disabled="disabled">${ performanceKPISubject.remark }</textarea>
	    					</td>
	    				</tr>
	    			</table>
	    			<input type="hidden" name="performanceKPISubject.id" value="${performanceKPISubject.id }" />
	    		</form>
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
    $(document).ready(function(){
    	//所属部门
	  	$("#performanceKPISubject-form-add-widget-deptid").widget({
	  		width:200,
			name:'t_report_performance_kpisubject',
			col:'deptid',
			singleSelect:true,
			onlyLeafCheck:false
		});
    });
    </script>
  </body>
</html>
