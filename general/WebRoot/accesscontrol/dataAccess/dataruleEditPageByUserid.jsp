<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>数据权限修改页面</title>
	<%@include file="/include.jsp" %> 
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">  
  	 	<div data-options="region:'center'">
			<div style="padding:10px 5px;">
				<form action="accesscontrol/editDatarule.do" method="post" id="dataAccess-form-editDatarule">
				<p>
					<label style="float:left; width:100px; padding:0 5px; line-height:21px;">类型:</label>
					<select id="dataAccess-select-type" style="width: 200px" disabled="disabled">
						<option value="1" <c:if test="${datarule.type=='1'}">selected="selected"</c:if>>数据字典</option>
						<option value="2" <c:if test="${datarule.type=='2'}">selected="selected"</c:if>>参照窗口</option>
					</select>
					<input type="hidden" name="datarule.type" value="${datarule.type}"/>
				</p>
				<p>
					<label style="float:left; width:100px; padding:0 5px; line-height:21px;">资源:</label>
					<input  style="width:400px" value="${datarule.dataname}" readonly="readonly"/>
					<input type="hidden" value="${datarule.tablename }">
					<input type="hidden" id="dataAccess-hidden-ruleedit" name="datarule.rule" value=""/>
					<input type="hidden" name="datarule.dataid" value="${datarule.dataid }"/>
					<input type="hidden" name="datarule.scope" value="2"/>
					<input type="hidden" name="datarule.userid" value="${datarule.userid}"/>
				</p>
				<p>
					<label style="float:left; width:100px; padding:0 5px; line-height:21px;">备注:</label>
					<textarea  name="datarule.remark" style="width:400px;height:80px;"><c:out value="${datarule.remark }"></c:out></textarea>
				</p>
				<p>
					<label style="float:left; width:100px; padding:0 5px; line-height:21px;">数据权限:</label>
					<div id="dataAccess-ruleEdit-info">
					</div>
				</p>
				</form>
			</div>
		</div>
		<div data-options="region:'south'" style="height: 40px;">
			<div class="buttonDivR">
	    		<a href="javaScript:void(0);" id="dataAccess-button-editDatarule" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'">保存</a>
	    	</div>
		</div>
		<script type="text/javascript">
			$(function() {
				$("#dataAccess-form-editDatarule").form({  
				    onSubmit: function(){  
				    	var flag = $(this).form('validate');
				    	if(flag==false){
				    		return false;
				    	}
				    },  
				    success:function(json){
				    	//转为json对象
				    	var data = $.parseJSON(json);
				        if(data.flag==true){
				        	$.messager.alert("提醒","保存成功");
				        	parent.$("#sysUser-table-dataruleList").datagrid("reload");
				        	parent.$("#datarule-div-ruleInfo").html("");
				        	parent.$("#datarule-div-ruleInfo-add").dialog("close");
				        }else{
				        	$.messager.alert("提醒","保存失败");
				        }
				    }  
				});
				$("#dataAccess-button-editDatarule").click(function() {
					var rules = $("#dataAccess-ruleEdit-info").queryRule('getRules');
					$("#dataAccess-hidden-ruleedit").attr("value",rules);
					$.messager.confirm("提醒", "是否修改数据权限规则?", function(r){
						if (r){
							$("#dataAccess-form-editDatarule").submit();
						}
					});
				});
				window.setTimeout(function(){
					$("#dataAccess-ruleEdit-info").queryRule({
						scopetype:'2',
						rules:'${datarule.rule}',
						restype:'${datarule.type}',
						name:'${datarule.tablename }'
					});
				}, 200);
				
			})
		</script>
	</div>
  </body>
</html>
