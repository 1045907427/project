<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>结算方式新增页面</title>
  </head>
  
  <body>
    <form action="" method="post" id="settlement-form-add">
    	 <div style="padding: 10px;">
	    	<p>
	    		<label>编码:</label>
	    		<input id="finance-id-settlement" type="text" name="settlement.id" class="easyui-validatebox" validType="validID[20]" required="true" style="width:200px;" maxlength="20"/>
	    	</p>
	    	<p>
	    		<label>名称:</label>
	    		<input type="text" name="settlement.name" class="easyui-validatebox" required="true" style="width:200px;" validType="validName[50]" maxlength="50"/>
	    	</p>
	    	<p>
	    		<label>类型:</label>
	    		<select id="finance-type-settlement" name="settlement.type" style="width:200px;" >
					<option value="1" selected="selected">月结</option>
					<option value="2">日结</option>
					<option value="3">现结</option>
					<option value="4">年结</option>
				</select>
	    	</p>
	    	<p>
	    		<label>天数:</label>
	    		<input id="finance-days-settlement" name="settlement.days" value="0" class="easyui-numberbox" data-options="min:0,precision:0,max:9999" style="width: 200px;text-align: right">
	    	</p>
	    	<p style="height: auto;">
	    		<label>备注:</label>
	    		<textarea name="settlement.remark" style="height: 100px;width: 195px;" class="easyui-validatebox" validType="maxLen[200]"></textarea>
	    	</p>
	    	<p>
	    		<label>状态:</label>
	    		<select name="settlement.state" style="width:200px;" disabled="disabled">
					<option value="4" selected="selected">新增</option>
				</select>
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
    	
    	$("#finance-type-settlement").change(function(){
    		//类型与天数的关系
    		if($("#finance-type-settlement").val() == 3){
  				$("#finance-days-settlement").val(0);
    		}
    	});   
    	
    	//新增销售方式
    	function addSettlement(type){
    		if(type == "save"){
    			if(!$("#settlement-form-add").form('validate')){
    				return false;
    			}
    		}
            loading("提交中..");
    		var ret = settlement_ajax($("#settlement-form-add").serializeJSON(),'basefiles/finance/addSettlement.do?type='+type);
    		var retJson = $.parseJSON(ret);
            loaded();
    		if(retJson.flag){
    			$("#settlement-table-list").datagrid('reload');
    			refreshLayout("结算方式【详情】",'basefiles/finance/settlementViewPage.do?id='+$("#finance-id-settlement").val());
    			if("save" == type ){
    				$.messager.alert("提醒","保存成功!");
    			}
    			else{
    				$.messager.alert("提醒","暂存成功!");
    			}
    		}
    		else{
    			if("save" == type ){
    				$.messager.alert("提醒","保存失败!");
    			}
    			else{
    				$.messager.alert("提醒","暂存失败!");
    			}
    		}
    	}
    	
    	$(function(){
    		$("#settlement-button").buttonWidget("initButtonType","add");
    		
    		//类型与天数的关系
    		if($("#finance-type-settlement").val() == 3){
  				$("#finance-days-settlement").val(0);
    		}
    	});
    </script>
  </body>
</html>
