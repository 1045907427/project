<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代码添加</title>
  </head> 
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'center',border:false">
    <form action="sysCode/addSysCode.do" method="post" id="sysCode-form-addCode">
    	<div class="pageContent">
    		<p>
    			<label>代码:</label>
    			<input type="text" id="code" name="sysCode.code" class="easyui-validatebox" required="true" validType="compare[1]" style="width:200px;"/>
    		</p>
    		<p>
    			<label>代码名称:</label>
    			<input type="text" name="sysCode.codename" class="easyui-validatebox" required="true" style="width:200px;"/>
    		</p>
			<p>
				<label>代码值:</label>
				<input type="text" name="sysCode.codevalue" style="width:200px;"/>
			</p>
    		<p>
    			<label>代码类型:</label>
    			<input type="text" id="type" name="sysCode.type" class="easyui-validatebox" required="true" validType="compare[2]" style="width:200px;"/>
    		</p>
    		<p>
    			<label>代码类型名称:</label>
    			<input type="text" name="sysCode.typename" class="easyui-validatebox" required="true" style="width:200px;"/>
    		</p>
    		<p>
    			<label>排序:</label>
    			<input type="text" name="sysCode.seq" class="easyui-validatebox" validType="validInt['int']" required="true" style="width:200px;"/>
    		</p>
    		<p>
    			<label>状态:</label>
    			<select name="sysCode.state" style="width:200px;">
    				<option value="1">有效</option>
    				<option value="0">无效</option>
    			</select>
    		</p><br/>
    	</div>
    </form>
      </div>
      <div data-options="region:'south',border:false">
          <div class="buttonDetailBG" style="height:30px;text-align:right;">
              <input type="button" name="savegoon" id="sysCode-save-addCode" value="确定"/>
          </div>
      </div>
  </div>
    <script type="text/javascript">
    	$(function(){
    		$.extend($.fn.validatebox.defaults.rules, {
    			compare:{
    				validator:function(value,param){
    					var code = $("#code").val();
    					var type = $("#type").val();
    					if(param[0]==1){
    						return value != type;
    					}
    					else{
    						return value != code;
    					}
    				},
    				message:'存在相同数据!'
    			}
    		});
    		$.extend($.fn.validatebox.defaults.rules, {
    			validInt:{
    				validator:function(value,param){
    					var isInt='int';
    					if(param[0]==isInt){
    						var reg=/^[1-9][0-9]{0,4}$/;
    						return reg.test(value);
    					} 
    				},
    				message:'请输入1-99999的整数类型数据!'
    			}
    		});
    		$("#sysCode-form-addCode").form({
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
    					$("#sysCode-dialog-codeOper").dialog('close',true);
    					$("#sysCode-table-showCodeList").datagrid('reload');
    				}
    				else{
    					$.messager.alert("提醒","添加失败!");
    				}
    			}
    		});
    		$("#sysCode-save-addCode").click(function(){
    			$.messager.confirm("提醒","是否添加代码信息?",function(r){
    				if(r){
    					$("#sysCode-form-addCode").submit();
    				}
    			});
    		});
    	});
    </script>
  </body>
</html>
