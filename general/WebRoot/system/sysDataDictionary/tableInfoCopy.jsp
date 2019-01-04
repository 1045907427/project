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
    <form action="sysDataDictionary/tableInfoCopy.do" method="post" id="sysDataDictionary-form-copyTableInfo">
    	<div class="pageContent">
    		<p>
    			<label>表名:</label>
    			<input type="text" id="sysDataDictionary-form-copyTableInfo-tablename" name="tableInfo.tablename" value="${tableInfo.tablename }" class="easyui-validatebox" required="true" style="width:200px;" autocomplete="off"/>
    		</p>
    		<p>
    			<label>描述名:</label>
    			<input type="text" name="tableInfo.tabledescname" value="${tableInfo.tabledescname }" class="easyui-validatebox" required="true" style="width:200px;"/>
    		</p>
    		<p>
    			<label>表类型:</label>    			
    			<select name="tableInfo.tabletype">
    				<option value="1">系统</option>
    				<option value="2">业务</option>
    				<option value="3" selected="selected">虚拟</option>
    			</select>
    		</p>
    		<p>
    			<label>模块类型:</label>     			
    			<input id="sysDataDictionary-form-copyTableInfo-moduletype"  name="tableInfo.moduletype" value="${tableInfo.moduletype }"/>
    		</p>
    		<p>
    			<label>创建方式:</label>    			
    			<select name="tableInfo.createmethod">
    				<option value="1">系统预置</option>
    				<option value="2" selected="selected">自建</option>
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
    			<select id="sysDataDictionary-form-copyTableInfo-usetreelist" name="tableInfo.usetreelist">
    				<option value="0" <c:if test="${tableInfo.usetreelist=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${tableInfo.usetreelist=='1'}">selected="selected"</c:if>>是</option>
    			</select>
    		</p>
    		<p id="sysDataDictionary-form-copyTableInfo-refertreecol-panel" style="<c:if test="${tableInfo.usetreelist!='1'}">display:none</c:if>">
    			<label>树形父节点列名:</label>    			
    			<input type="text" id="sysDataDictionary-form-copyTableInfo-refertreecol" name="tableInfo.refertreecol" value="${tableInfo.refertreecol }"  style="width:200px;" autocomplete="off"/>
    		</p>  
    		<p style="height:70px;">
    			<label>描述:</label>
    			<textarea name="tableInfo.remark" style="width:200px;height:60px;">${tableInfo.remark }</textarea>
    		</p>    		
    	</div>
		<input type="hidden" name="tableInfo.oldtablename" value="${tableInfo.tablename }"/>
    </form> 
    <script type="text/javascript">
        $("#sysDataDictionary-form-copyTableInfo-moduletype").widget({
            referwid:'RL_T_SYS_CODE_MODULE',
            singleSelect:false
        });
    	$(function(){

    		$("#sysDataDictionary-form-copyTableInfo-refertreecol").combogrid({
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
    		$("#sysDataDictionary-form-copyTableInfo-tablename").blur(function(){
    			var tablename=$.trim($(this).val()||"");
    			if(tablename!=""){
    				var url="sysDataDictionary/showDBTableColumnList.do?tablename="+tablename.toLowerCase();
    				$("#sysDataDictionary-form-copyTableInfo-refertreecol").combogrid({'url':url});
    			}
    		});
    		$("#sysDataDictionary-form-copyTableInfo-usetreelist").change(function(){
    			$("#sysDataDictionary-form-copyTableInfo-refertreecol-panel").toggle();
    		});
    		$("#sysDataDictionary-form-copyTableInfo").form({
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
	    					$.messager.alert("提醒","复制成功!");
	    					$("#sysDataDictionary-dialog-tableInfoOper").dialog('close',true);
	    					$("#sysDataDictionary-table-showTableInfoList").datagrid('reload');
	    					$("#sysDataDictionary-table-showTableColumnList").datagrid('reload');
	    				}
	    				else{
	    					$.messager.alert("提醒",(json.msg || "复制失败"));
	    				}
    				}catch(e){
    					$.messager.alert("提醒","抱歉，系统异常，复制失败");
    				}
    			}
    		});
    		$("#sysDataDictionary-save-copyTableInfo").click(function(){
    			$.messager.confirm("提醒","是否复制表及表字段?",function(r){
    				if(r){
    					if($("#sysDataDictionary-form-copyTableInfo-usetreelist").val()=="0"){
	    					$("#sysDataDictionary-form-copyTableInfo-refertreecol").val("");
	    				}else{
	    					if($.trim($("#sysDataDictionary-form-copyTableInfo  :input[name='tableInfo.refertreecol']").val())==""){
						    		$.messager.alert("错误","请填写树形父节点列名");
						    		return false;    						
	    					}
	    				}
    					$("#sysDataDictionary-form-copyTableInfo").submit();
    				}
    			});
    		});
    	});
    </script>
  </body>
</html>
