<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>费用修改页面</title>
  </head>
  <%
  	int deptsubCount=0;
  	boolean isCreateTr=false;
  %>
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center">
    			<form action="" method="post" id="departmentCosts-form-add-subject" style="padding: 10px;">
    			<table cellpadding="2" cellspacing="2" border="0">
    				<tr>
	   					<td width="70px" align="left">业务日期:</td>
	   					<td align="left"><input type="text" id="departmentCosts-form-view-date-businesstime" name="businessdate" class="easyui-validatebox"  readonly="readonly" value="${deparmentCosts.businessdate }" style="width:150px"/></td>
	   					<td width="70px" align="left">所属部门:</td>
		    			<td align="left"><input type="text" id="departmentCosts-form-view-widget-deptid" name="deptid" readonly="readonly" value="${deparmentCosts.deptid }" style="width: 150px;"/></td>
	   				</tr>
	   				<tr>
	   					<td colspan="4">
	   						<hr style="border-color:#374e60"/>
	   					</td>
	   				</tr>
	   				<c:forEach items="${deptCostsSubjectList }" var="list">
	    			<c:if test="${list.assignsupplier == 1  or not empty(list.supplierid)}">	    			
		    			<tr>
	   						<td align="left">${list.name}:</td>   					
		    				<td>
		    					<input type="text" name="deptCostsSubject_${list.code}" class="easyui-numberbox" value="${list.detailamount }" readonly="readonly" data-options="precision:2" style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;" inputdata="deptCostsSubject" />
		    				</td>
		    				<td align="left">${list.name}分摊到:</td>   					
		    				<td>
		    					<input type="text" id="departmentCosts-form-view-widget-supplier_${list.code}" name="supplierid_${list.code}" value="${list.supplierid }" style="width:150px;" readonly="readonly" />
		    					<script type="text/javascript">
		    						$(document).ready(function(){
			    						$("#departmentCosts-form-view-widget-supplier_${list.code}").widget({ 
			    							name:'t_js_departmentcosts_detail',
			    							col:'supplierid',
			    							readonly:true,
			    							width:150
				    					});
		    						});
		    					</script>
		    				</td>
		    			</tr>
	    				</c:if>
					</c:forEach>					
					<%
					  	deptsubCount=0;
					%>
	    			<c:forEach items="${deptCostsSubjectList }" var="list">
	    			<c:if test="${list.assignsupplier != 1  and empty(list.supplierid)}">
	    			<% 
	    				isCreateTr=true;
	    				if(deptsubCount%2==0){ %>
	    			<tr>
	    			<%}  %>
   						<td align="left">${list.name}:</td>   					
	    				<td>
	    					<input type="text" name="deptCostsSubject_${list.code}" class="easyui-numberbox" readonly="readonly" value="${list.detailamount }" data-options="precision:2" style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;" inputdata="deptCostsSubject" />
	    				</td>
	    			<% deptsubCount=deptsubCount+1; %>
	    			<% if(deptsubCount%2==0){ %>	
	    			</tr>
	    			<%} %>
	    			</c:if>
					</c:forEach>
					<%if(isCreateTr && deptsubCount%2!=0) {%>
						</tr>
					<%} %>
	    		</table>
	    		</form>
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
   
    	$(function(){
    		
			//所属部门
		  	$("#departmentCosts-form-view-widget-deptid").widget({
		  		width:150,
				name:'t_js_departmentcosts',
				col:'deptid',
				singleSelect:true,
				required:true
			});
			
    	});
    	
    </script>
  </body>
</html>
