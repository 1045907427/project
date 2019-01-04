<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>初始化供应商列表</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
   <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:false" style="border: 0px;">
	    	<form action="" method="post" id="approvalPrice-form-edit">
		    	<table cellpadding="2" cellspacing="2" border="0">
	    			<tr>
	    				<td>供应商名称：</td>
	    				<td><input type="text" disabled="disabled" value="<c:out value="${supplierName }"></c:out>" style="width: 320px;"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td>所属部门：</td>
	    				<td><input type="text" style="width: 120" disabled="disabled" value="<c:out value="${supplierdeptName }"></c:out>"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td>核准金额：</td>
	    				<td><input type="text" name="approvalPrice.price" style="width: 120" class="easyui-numberbox" <c:if test="${editFlag == false }">disabled="disabled"</c:if> value="${approvalPrice.price }" required="true" data-options="min:-9999999999,max:9999999999,precision:2,groupSeparator:','"/>
	    					<input name="approvalPrice.id" type="hidden" value="${id }"/>
	    				</td>
	    			</tr>
	    		</table>
	    	</form>
    	</div>
    	<div data-options="region:'south'" style="height: 30px;">
    		<div align="right">
    			<a href="javaScript:void(0);" id="finance-button-initsupplier" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="确定">确定</a>
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
    	var approvalPrice_AjaxConn = function (Data, Action, Str) {
    		if(null != Str && "" != Str){
    			loading(Str);
    		}
		   var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false,
		        success:function(data){
		        	loaded();
		        }
		    })
		    return MyAjax.responseText;
		}
		//确定按钮
		$("#finance-button-initsupplier").click(function(){
			if(!$("#approvalPrice-form-edit").form('validate')){
       			return false;
       		}
			var ret = approvalPrice_AjaxConn($("#approvalPrice-form-edit").serializeJSON(),'journalsheet/approvalPrice/editApprovalPrice.do','提交中..');
			var retJson = $.parseJSON(ret);
			if(retJson.flag){
				$('#approvalPrice-dialog-operate').dialog('close');
				$("#finance-table-approvalPrice").datagrid("reload");
			}
			else{
				$.messager.alert("提醒","修改失败!");
			}
		});
    </script>
  </body>
</html>
