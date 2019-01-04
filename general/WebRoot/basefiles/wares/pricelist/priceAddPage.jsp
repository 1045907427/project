<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代码添加</title>
  </head> 
  
  <body>
   	<div class="easyui-layout" data-options="fit:true,border:false" style="border: 0px;">
   		<div region="center" style="border: 0px;">
   			<form action="sysCode/addSysCode.do" method="post" id="sysCode-form-addCode">
   				<div style="text-align: center">
   					<p>
		    			<label>编号:</label>
		    			<input type="text" id="code" name="sysCode.code" class="easyui-validatebox" required="true" validType="compare[1]" style="width:200px;"/>
		    		</p>
		    		<p>
		    			<label>名称:</label>
		    			<input type="text" name="sysCode.codename" class="easyui-validatebox" required="true" style="width:200px;"/>
		    			<input type="hidden" name="sysCode.type" value="price_list"/>
		    			<input type="hidden" name="sysCode.typename" value="价格套"/>
		    			<input type="hidden" name="sysCode.seq" id="wares-seq-priceList"/>
		    		</p>
		    		<p>
		    			<label>状态:</label>
		    			<select name="sysCode.state" style="width:200px;">
		    				<option value="1">启用</option>
		    				<option value="0">禁用</option>
		    			</select>
		    		</p>
   				</div>
    		</form>
   	    </div>
	    <div region="south" style="height:33px;text-align: right;">
	    	<a href="javaScript:void(0);" id="sysCode-save-addCode" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'">确定</a>
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
    				loading("提交中..");
    			},
    			success:function(data){
                    loaded();
    				var json = $.parseJSON(data);
    				if(json.flag==true){
    					$.messager.alert("提醒","新增成功!");
    					$("#wares-dialog-priceAdd").dialog('close',true);
    					$("#pricelist-table-list").datagrid('reload');
    				}
    				else{
    					$.messager.alert("提醒","新增失败!");
    				}
    			}
    		});
    		$("#sysCode-save-addCode").click(function(){
    			$.messager.confirm("提醒","是否新增价格套信息?",function(r){
    				if(r){
    					var seq = $("#pricelist-table-list").datagrid('getRows').length+parseInt("1");
    					$("#wares-seq-priceList").val(seq);
    					$("#sysCode-form-addCode").submit();
    				}
    			});
    		});
    	});
    </script>
  </body>
</html>
