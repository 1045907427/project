<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>根据代码类型添加单据</title>
  </head>
  
  <body>
    <form action="sysCode/addSysCode.do" method="post" id="sysCode-form-addCode">
    	<div class="pageContent">
    		<p>
    			<label>单据:</label>
    			<input type="text" id="code" name="sysCode.code" class="easyui-validatebox" required="true" validType="compare[1]" style="width:200px;"/>
    		</p>
    		<p>
    			<label>单据名称:</label>
    			<input type="text" name="sysCode.codename" class="easyui-validatebox" required="true" style="width:200px;"/>
    		</p>
    		<input type="hidden" name="sysCode.typename" value="${typename }" readonly="readonly" class="easyui-validatebox" required="true" style="width:200px;"/>
    		<input type="hidden" id="type" name="sysCode.type" value="${type }" readonly="readonly" class="easyui-validatebox" required="true" validType="compare[2]" style="width:200px;"/>
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
    		<div class="buttondiv">
    			<a href="javaScript:void(0);" id="sysCode-save-addCode" class="easyui-linkbutton" data-options="iconCls:'button-save'">确定</a>
    		</div>
    	</div>
    </form>
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
    					$("#sysNumber-table-billName").datagrid('reload');
    				}
    				else{
    					$.messager.alert("提醒","添加失败!");
    				}
    			}
    		});
    		$("#sysCode-save-addCode").click(function(){
    			$.messager.confirm("提醒","是否添加单据信息?",function(r){
    				if(r){
    					$("#sysCode-form-addCode").submit();
    				}
    			});
    		});
    	});
    </script>
  </body>
</html>
