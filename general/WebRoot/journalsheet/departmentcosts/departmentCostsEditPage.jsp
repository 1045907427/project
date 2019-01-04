<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>费用修改页面</title>
  </head>
  <%
  	int deptsubCount=0;
  	boolean isCreateTr=false;
  	int tabIndex=0;
  %>
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center">
    			<form action="" method="post" id="departmentCosts-form-edit-subject" style="padding: 10px;">
    			<table cellpadding="2" cellspacing="2" border="0">
    				<tr>
	    				<td width="70px" align="left">业务日期:</td>
	   					<td align="left"><input type="text" id="departmentCosts-form-edit-date-businessdate" name="businessdate" class="easyui-validatebox"  readonly="readonly" value="${deparmentCosts.businessdate }" style="width:150px"/></td>
	   					<td width="70px" align="left">所属部门:</td>
		    			<td align="left"><input type="text" id="departmentCosts-form-edit-widget-deptid" name="deptid" readonly="readonly" value="${deparmentCosts.deptid }" style="width: 150px;"/></td>
	    			</tr>
	    			<tr>
	   					<td colspan="4">
	   						<hr style="border-color:#374e60"/>
	   					</td>
	   				</tr>
	   				<%
	    				tabIndex=1;
	    			%>
	    			<c:forEach items="${deptCostsSubjectList }" var="list">
	    				<c:if test="${list.assignsupplier == 1 or not empty(list.supplierid)}">	    			
		    			<tr>
	   						<td align="left">${list.name}:</td>   					
		    				<td>
		    					<input type="text" name="deptCostsSubject_${list.code}" class="easyui-numberbox" value="${list.detailamount }" <c:if test="${list.dataedittype=='0' }"> readonly="readonly"</c:if>  data-options="precision:2" style="width:150px;<c:if test="${list.dataedittype=='0' }">border:1px solid #B3ADAB; background-color: #EBEBE4;</c:if>" inputdata="deptCostsSubject" <c:if test="${list.dataedittype=='1' }">tabindex="<%=tabIndex++ %>"</c:if> />
		    				</td>
		    				<td align="left">${list.name}分摊到:</td>   					
		    				<td>
		    					<input type="text" id="departmentCosts-form-edit-widget-supplier_${list.code}" name="supplierid_${list.code}" value="${list.supplierid }" style="width:150px;" tabindex="<%=tabIndex++ %>" />
		    					<script type="text/javascript">
		    						$(document).ready(function(){
			    						$("#departmentCosts-form-edit-widget-supplier_${list.code}").widget({ 
			    							name:'t_js_departmentcosts_detail',
			    							col:'supplierid',
			    							width:150,
			    							param:[{field:'buydeptid',op:'startwith',value:'${deparmentCosts.deptid}'}]
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
		    			<c:if test="${list.assignsupplier != 1 and empty(list.supplierid)}">
		    			<% 
		    				isCreateTr=true;
		    				if(deptsubCount%2==0){
		    				tabIndex=tabIndex+1;
		    			%>
		    				<tr>
			    		<%}  %>
	   						<td align="left">${list.name}:</td>   					
		    				<td>
		    					<input type="text" name="deptCostsSubject_${list.code}" class="easyui-numberbox" value="${list.detailamount }" <c:if test="${list.dataedittype=='0' }"> readonly="readonly"</c:if>  data-options="precision:2" style="width:150px;<c:if test="${list.dataedittype=='0' }">border:1px solid #B3ADAB; background-color: #EBEBE4;</c:if>" inputdata="deptCostsSubject" <c:if test="${list.dataedittype=='1' }"><%tabIndex=tabIndex+1; %>tabindex="<%=tabIndex %>"</c:if> />
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
	    		<input type="hidden" name="id" id="departmentCosts-form-edit-id" value="${deparmentCosts.id }" />
	    	</form>
    		</div>
    	</div>
    	<div data-options="region:'south'" style="height: 35px;border: 0px;">
    		<div align="right" style="line-height:35px;">
    			<a href="javaScript:void(0);" id="departmentCosts-form-edit-saveMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="修改费用录入">确定费用录入</a>&nbsp;&nbsp;
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
   
    	//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			maxLen:{
	  			validator : function(value,param) { 
		            return value.length <= param[0]; 
		        }, 
		        message : '最多可输入{0}个字符!' 
	  		}
		});

		

    	//修改费用录入
    	$("#departmentCosts-form-edit-saveMenu").click(function(){
    		$.messager.confirm("提醒","是否确认编辑部门费用?",function(r){
				if(r){
					var flag= $("#departmentCosts-form-edit-subject").form('validate');
					if(flag==false){
						return false;
					}
		       		if($("input[inputdata='deptCostsSubject']").size()==0){
		        		$.messager.alert("提醒","未能找到费用科目，无法修改!"); 
		        		return false;          		
		       		}
		       		var formData=$.extend({},$("#departmentCosts-form-edit-subject").serializeJSON());
		       		
		       		loading('正在努力计算并修改中，请耐心等待..');
		        	try{
			        	$.ajax({
					        type: 'post',
					        cache: false,
					        url: 'journalsheet/costsFee/editDepartmentCosts.do',
					        data: formData,
					        dataType:'json',
					        success:function(data){
					        	loaded();
				        		if(data.flag){
					        		$.messager.alert("提醒","修改成功!");
					        		$("#departmentCosts-query-List").trigger('click');
					        		$('#departmentCosts-dialog-edit-operate').dialog('close');
				            	}
				            	else{
					            	if(retJson.msg){
					        			$.messager.alert("提醒","修改失败!"+data.msg);
					            	}else{
					        			$.messager.alert("提醒","修改失败!");
					            	}
				            	}
					        }
					    });  
		        	}catch(ex){
			        	loaded();
		        	}      
				}
			});
    	});
    	$(function(){
    		
			//所属部门
		  	$("#departmentCosts-form-edit-widget-deptid").widget({
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
