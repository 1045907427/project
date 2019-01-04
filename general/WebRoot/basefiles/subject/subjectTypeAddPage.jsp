<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>科目分类添加</title>
  </head> 
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'center',border:false">
	    <form action="basefiles/subject/addSubjectType.do" method="post" id="basefiles-form-addSubjectType">
	    	<table cellpadding="3" cellspacing="3" border="0">
		    	<tr>
   					<td width="90px" align="right">编码:</td>
   					<td align="left">
   						<input type="text" class="easyui-validatebox" style="width:200px;" name="subject.id" id="basefiles-id-subjectTypeAddPage" data-options="required:true,validType:['validLengthAndUsed[${len}]']"/>
   					</td>
 				</tr>
   				<tr>
   					<td width="90px" align="right">名称:</td>
   					<td align="left"><input type="text" name="subject.name" class="easyui-validatebox" required="true" validType="validUsed[50]" style="width:200px;"/></td>
   				</tr>
   				<tr>
   					<td width="90px" align="right">代码:</td>
   					<td align="left"><input type="text" name="subject.typecode" class="easyui-validatebox" required="true" validType="validUsedType[50]" style="width:200px;"/></td>
   				</tr>
		    	<tr>
		    		<td align="right">状态:</td>
	    			<td align="left">	    				
		    			<select  style="width:200px;" disabled="disabled">
		    				<option value="1">启用</option>
		    			</select>
		    			<input type="hidden" name="subject.state" value="1" />
	    			</td>
		    	</tr>
		    	<tr>
		    		<td align="right">备注:</td>
	    			<td align="left">
		    			<textarea rows="0" cols="0" name="subject.remark" style="width:200px;height:80px;" class="easyui-validatebox" validType="maxByteLength[500]"></textarea>
	    			</td>
		    	</tr>
		    </table>
		    <input type="hidden" name="subject.oldid" value="${subject.id }" />
	    </form>
      </div>
      <div data-options="region:'south',border:false">
          <div class="buttonDetailBG" style="height:30px;text-align:right;">
              <input type="button" name="savegoon" id="basefiles-save-addSubjectType" value="确定"/>
          </div>
      </div>
  </div>
    <script type="text/javascript">
    	$(function(){
    		validUsed('','该名称已被使用,请另输入!');
    		validUsedType('','该分类代码已被使用,请另输入!');
    		validLengthAndUsed('${len}', '', "该编号已被使用，请另输编号！"); //验证输入长度
    		$("#basefiles-form-addSubjectType").form({
    			onSubmit: function(){
    				var flag = $(this).form('validate');
    				if(flag==false){
    					return false;
    				}
    			},
    			success:function(data){
    				//$.parseJSON()解析JSON字符串 
    				var json = $.parseJSON(data);
    				
    				if(json.flag==true){
    					$.messager.alert("提醒","添加成功!");
    					$("#basefiles-subjectTypeListPage-dialog-operate-content").dialog('close',true);
    					$("#basefiles-table-subjectTypeList").datagrid('reload');
    				}
    				else{
    					if(json.msg!=null){
    						$.messager.alert("提醒","添加失败,"+json.msg);
    					}else{
    						$.messager.alert("提醒","添加失败!");
    					}
    				}
    			}
    		});
    		$("#basefiles-save-addSubjectType").click(function(){
    			$.messager.confirm("提醒","是否添加科目分类信息?",function(r){
    				if(r){
    					$("#basefiles-form-addSubjectType").submit();
    				}
    			});
    		});
    		

    		  
    		
    	});
    </script>
  </body>
</html>
