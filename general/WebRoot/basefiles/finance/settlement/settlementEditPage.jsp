<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>结算方式修改页面</title>
  </head>
  
  <body>
    <form action="" method="post" id="settlement-form-edit">
    	<input type="hidden" id="settlement-oldId" name="settlement.oldId" value="<c:out value="${settlement.id }"></c:out>" />
    	<input type="hidden" id="settlement-name" value="<c:out value="${settlement.name }"></c:out>" />
  		<input type="hidden" name="settlement.state" value="${settlement.state }" />
  		<!-- 修改标识，判断有没有引用 -->
  		<input type="hidden" id="finance-editType-settlement" value="${editFlag }" />
    	 <div style="padding: 10px;">
	    	<p>
	    		<label>编码:</label>
	    		<input id="finance-id-settlement" type="text" name="settlement.id" value="<c:out value="${settlement.id}"></c:out>" style="width:200px;" <c:if test="${editFlag == false }">readonly="readonly"</c:if> class="easyui-validatebox" validType="validID[20]" required="true" />
	    	</p>
	    	<p>
	    		<label>名称:</label>
	    		<input type="text" name="settlement.name" value="<c:out value="${settlement.name}"></c:out>" style="width:200px;" class="easyui-validatebox" required="true" validType="validName[50]" />
	    	</p>
	    	<p>
	    		<label>类型:</label>
	    		<select id="finance-type-settlement" name="settlement.type" style="width:200px;" >
					<option value="1" <c:if test="${settlement.type=='1'}">selected="selected"</c:if> >月结</option>
					<option value="2" <c:if test="${settlement.type=='2'}">selected="selected"</c:if> >日结</option>
					<option value="3" <c:if test="${settlement.type=='3'}">selected="selected"</c:if> >现结</option>
					<option value="4" <c:if test="${settlement.type=='4'}">selected="selected"</c:if> >年结</option>
				</select>
	    	</p>
	    	<p>
	    		<label>天数:</label>
	    		<input id="finance-days-settlement" name="settlement.days" value="${settlement.days }" class="easyui-numberbox" data-options="min:0,precision:0,max:9999" style="width: 200px;">
	    	</p>
	    	<p style="height: auto;">
	    		<label>备注:</label>
	    		<textarea name="settlement.remark" style="height: 100px;width:195px;" class="easyui-validatebox" validType="maxLen[200]"><c:out value="${settlement.remark}"></c:out></textarea>
	    	</p>
	    	<p>
	    		<label>状态:</label>
	    		<input id="common-combobox-state" value="${settlement.state }" disabled="disabled" class="easyui-combobox" style="width: 200px" />
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
    	//状态
		$('#common-combobox-state').combobox({
		    url:'common/sysCodeList.do?type=state',   
		    valueField:'id',   
		    textField:'name'  
		});
		//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			validID:{
				validator : function(value,param) {
					if(value == $("#settlement-oldId").val()){
						return true;
					}
					var reg=eval("/^[A-Za-z0-9]{0,"+param[0]+"}$/");
					if(reg.test(value)){
						var ret = settlement_ajax({id:value},'basefiles/finance/isUsedSettlementID.do');
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
							$.fn.validatebox.defaults.rules.validID.message = '编码已存在, 请重新输入!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validID.message = '最多可输入{0}个字符!';
						return false;
					}
		            return true;
		        }, 
		        message : '' 
			},
			validName:{
				validator : function(value,param) {
					if(value == $("#settlement-name").val()){
						return true;
					}
					if(value.length <= param[0]){
						var ret = settlement_ajax({name:value},'basefiles/finance/isUsedSettlementName.do');
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
							$.fn.validatebox.defaults.rules.validName.message = '名称重复, 请重新输入!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validName.message = '最多可输入{0}个字符!';
						return false;
					}
					return true;
		        }, 
		        message : '' 
			},
			maxLen:{
	  			validator : function(value,param) { 
		            return value.length <= param[0]; 
		        }, 
		        message : '最多可输入{0}个字符!' 
	  		}
		});
		
		$("#finance-type-settlement").change(function(){
    		//类型与天数的关系
    		if($("#finance-type-settlement").val() == 3){
  				$("#finance-days-settlement").val(0);
    		}
    	});  
		
		function  editSettlement(type){
        	if(type == "save"){
        		if(!$("#settlement-form-edit").form('validate')){
        			return false;
        		}
        	}
            loading("提交中..");
        	var ret = settlement_ajax($("#settlement-form-edit").serializeJSON(),'basefiles/finance/editSettlement.do?type='+type);
        	var retJson = $.parseJSON(ret);
            loaded();
        	if(retJson.flag){
        		if(type == "save"){
        			$.messager.alert("提醒","保存成功");
        		}
        		else{
        			$.messager.alert("提醒","暂存成功");
        		}
        		$("#settlement-table-list").datagrid('reload');
	  		    refreshLayout("结算方式【详情】",'basefiles/finance/settlementViewPage.do?id='+$("#finance-id-settlement").val());
        	}
        	else{
        		if(type == "save"){
        			$.messager.alert("提醒","保存失败");
        		}
        		else{
        			$.messager.alert("提醒","暂存失败");
        		}
        	}
        }
		
    	$(function(){
    		$("#settlement-button").buttonWidget("setDataID", {id:$("#settlement-oldId").val(), state:'${settlement.state}', type:'edit'});
    	
    		//类型与天数的关系
    		if($("#finance-type-settlement").val() == 3){
  				$("#finance-days-settlement").val(0);
    		}
    	});
    </script>
  </body>
</html>
