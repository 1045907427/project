<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>修改表信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  <body>
    <form action="sysDataDictionary/tableInfoEdit.do" method="post" id="sysDataDictionary-form-editTableInfo">
    	<div class="pageContent">
    		<p>
    			<label>表名:</label>
    			<input type="text" id="sysDataDictionary-form-editTableInfo-tablename" name="tableInfo.tablename" value="${tableInfo.tablename}" class="easyui-validatebox" required="true" style="width:200px;" autocomplete="off"/>
    		</p>
    		<p>
    			<label>描述名:</label>
    			<input type="text" name="tableInfo.tabledescname" value="${tableInfo.tabledescname }" class="easyui-validatebox" required="true" style="width:200px;"/>
    		</p>
    		<p>
    			<label>表类型:</label>    			
    			<select name="tableInfo.tabletype">
    				<option value="1" <c:if test="${tableInfo.tabletype=='1'}">selected="selected"</c:if>>系统</option>
    				<option value="2" <c:if test="${tableInfo.tabletype=='2'}">selected="selected"</c:if>>业务</option>
    				<option value="3" <c:if test="${tableInfo.tabletype=='3'}">selected="selected"</c:if>>虚拟</option>
    			</select>
    		</p>
    		<p>
    			<label>模块类型:</label>     			
    			<input id="sysDataDictionary-form-editTableInfo-moduletype"  name="tableInfo.moduletype" value="${tableInfo.moduletype }"/>
    		</p>
    		<p>
    			<label>创建方式:</label>    			
    			<select name="tableInfo.createmethod">
    				<option value="1" <c:if test="${tableInfo.createmethod=='1'}">selected="selected"</c:if>>系统预置</option>
    				<option value="2" <c:if test="${tableInfo.createmethod=='2'}">selected="selected"</c:if>>自建</option>
    			</select>
    		</p>
            <p>
                <label>是否支持数据权限:</label>
                <select name="tableInfo.isdatarule">
                    <option value="1" <c:if test="${tableInfo.isdatarule=='1'}">selected="selected"</c:if>>是</option>
                    <option value="0" <c:if test="${tableInfo.isdatarule=='0'}">selected="selected"</c:if>>否</option>
                </select>
            </p>
    		<p>
    			<label>使用状态:</label>    			
    			<select name="tableInfo.state">
    				<option value="0" <c:if test="${tableInfo.state=='0'}">selected="selected"</c:if>>停用</option>
    				<option value="1" <c:if test="${tableInfo.state=='1'}">selected="selected"</c:if>>启用</option>
    			</select>
    		</p>
    		<p>
    			<label>使用历史库:</label>    			
    			<select name="tableInfo.usehistory">
    				<option value="0" <c:if test="${tableInfo.usehistory=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${tableInfo.usehistory=='1'}">selected="selected"</c:if>>是</option>
    			</select>
    		</p>
    		<p>
    			<label>使用版本库:</label>    			
    			<select name="tableInfo.useversion">
    				<option value="0" <c:if test="${tableInfo.useversion=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${tableInfo.useversion=='1'}">selected="selected"</c:if>>是</option>
    			</select>
    		</p>
    		<p>
    			<label>使用自动编号:</label>    			
    			<select name="tableInfo.useautoencoded">
    				<option value="0" <c:if test="${tableInfo.useautoencoded=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${tableInfo.useautoencoded=='1'}">selected="selected"</c:if>>是</option>
    			</select>
    		</p> 	
    		<p>
    			<label>使用树形显示:</label>    			
    			<select id="sysDataDictionary-form-editTableInfo-usetreelist" name="tableInfo.usetreelist">
    				<option value="0" <c:if test="${tableInfo.usetreelist=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${tableInfo.usetreelist=='1'}">selected="selected"</c:if>>是</option>
    			</select>
    		</p>
    		<p id="sysDataDictionary-form-editTableInfo-refertreecol-panel" style="<c:if test="${tableInfo.usetreelist!='1'}">display:none</c:if>">
    			<label>树形父节点列名:</label>    			
    			<input type="text" id="sysDataDictionary-form-editTableInfo-refertreecol" name="tableInfo.refertreecol" value="${tableInfo.refertreecol }"  style="width:200px;" autocomplete="off"/>
    		</p>  
    		<p style="height:70px;">
    			<label>描述:</label>
    			<textarea name="tableInfo.remark" style="width:200px;height:60px;">${tableInfo.remark }</textarea>
    		</p>    		
    	</div>
		<input type="hidden" name="tableInfo.oldtablename" value="${tableInfo.tablename }"/>
    </form> 
    <script type="text/javascript">
        $("#sysDataDictionary-form-editTableInfo-moduletype").widget({
            referwid:'RL_T_SYS_CODE_MODULE',
            singleSelect:false
        });
    	$(function(){

    		$("#sysDataDictionary-form-editTableInfo-refertreecol").combogrid({
    			url:'sysDataDictionary/showDBTableColumnList.do?tablename=${tableInfo.tablename}',
			    panelWidth:320,  
			    idField:'columnname',  
			    textField:'colchnname',
			    editable:false,
				columns : [[ 
					{field : 'columnname',title : '字段名',width : 100},
					{field : 'colchnname',title : '字段描述名',width : 100},
					{field : 'coldatatype',title : '数据类型',width : 80}
				]]
			});
    		$("#sysDataDictionary-form-editTableInfo-tablename").blur(function(){
    			var tablename=$.trim($(this).val()||"");
    			if(tablename!=""){
    				var url="sysDataDictionary/showDBTableColumnList.do?tablename="+tablename.toLowerCase();
    				$("#sysDataDictionary-form-editTableInfo-refertreecol").combogrid({'url':url});
    			}
    		});
    		$("#sysDataDictionary-form-editTableInfo-usetreelist").change(function(){
    			$("#sysDataDictionary-form-editTableInfo-refertreecol-panel").toggle();
    		});
    		$("#sysDataDictionary-form-editTableInfo").form({
    			onSubmit: function(){
    				var flag = $(this).form('validate');
    				if(flag==false){
    					return false;
    				}
    			},
    			success:function(data){
    				try{
	    				//$.parseJSON()解析JSON字符串 
	    				var json=$.parseJSON(data);
	    				if(json.flag==true){
	    					$.messager.alert("提醒","修改成功!");
	    					$("#sysDataDictionary-dialog-tableInfoOper").dialog('close',true);
	    					$("#sysDataDictionary-table-showTableInfoList").datagrid('reload');
	    					$("#sysDataDictionary-table-showTableColumnList").datagrid('reload');
	    				}
	    				else{
	    					$.messager.alert("提醒",(json.msg || "修改失败"));
	    				}
    				}catch(e){
    					$.messager.alert("提醒","抱歉，系统异常，修改失败");
    				}
    			}
    		});
    		$("#sysDataDictionary-save-editTableInfo").click(function(){
    			$.messager.confirm("提醒","是否修改表描述信息?",function(r){
    				if(r){
    					if($("#sysDataDictionary-form-editTableInfo-usetreelist").val()=="0"){
	    					$("#sysDataDictionary-form-editTableInfo-refertreecol").val("");
	    				}else{
	    					if($.trim($("#sysDataDictionary-form-editTableInfo  :input[name='tableInfo.refertreecol']").val())==""){
						    		$.messager.alert("错误","请填写树形父节点列名");
						    		return false;    						
	    					}
	    				}
    					$("#sysDataDictionary-form-editTableInfo").submit();
    				}
    			});
    		});
    	});
    </script>
  </body>
</html>
