<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>添加表信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  
  <body>
    <form action="sysDataDictionary/tableInfoAdd.do" method="post" id="sysDataDictionary-form-addTableInfo">
    	<div class="pageContent">
    		<p>
    			<label>表名:</label>
    			<input type="text" id="sysDataDictionary-form-addTableInfo-tablename" name="tableInfo.tablename" class="easyui-validatebox"  validType="dbtable" required="true" style="width:200px;" autocomplete="off"/>
    		</p>
    		<p>
    			<label>描述名:</label>
    			<input type="text" name="tableInfo.tabledescname" class="easyui-validatebox" required="true" style="width:200px;" autocomplete="off"/>
    		</p>
    		<p>
    			<label>表类型:</label>    			
    			<select name="tableInfo.tabletype">
    				<option value="1">系统</option>
    				<option value="2">业务</option>
    				<option value="3">虚拟</option>
    			</select>
    		</p>
    		<p>
    			<label>模块类型:</label>
    			<input type="text" name="tableInfo.moduletype" id="sysDataDictionary-form-editTableInfo-moduletype"/>
    		</p>
    		<p>
    			<label>创建方式:</label>    			
    			<select name="tableInfo.createmethod">
    				<option value="1">系统预置</option>
    				<option value="2">自建</option>
    			</select>
    		</p>
            <p>
                <label>是否支持数据权限:</label>
                <select name="tableInfo.isdatarule">
                    <option value="1">是</option>
                    <option value="0" selected="selected">否</option>
                </select>
            </p>
    		<p>
    			<label>使用状态:</label>    			
    			<select name="tableInfo.state">
    				<option value="1">启用</option>
    				<option value="0">停用</option>
    			</select>
    		</p>
    		<p>
    			<label>使用历史库:</label>    			
    			<select name="tableInfo.usehistory">
    				<option value="0">否</option>
    				<option value="1">是</option>
    			</select>
    		</p>
    		<p>
    			<label>使用版本库:</label>    			
    			<select name="tableInfo.useversion">
    				<option value="0">否</option>
    				<option value="1">是</option>
    			</select>
    		</p>
    		<p>
    			<label>使用自动编号:</label>    			
    			<select name="tableInfo.useautoencoded">
    				<option value="1">是</option>
    				<option value="0">否</option>
    			</select>
    		</p> 	
    		<p>
    			<label>使用树形显示:</label>    			
    			<select id="sysDataDictionary-form-addTableInfo-usetreelist" name="tableInfo.usetreelist">
    				<option value="0">否</option>
    				<option value="1">是</option>
    			</select>
    		</p>
    		<p id="sysDataDictionary-form-addTableInfo-refertreecol-panel" style="display:none">
    			<label>树形父节点列名:</label>    			
    			<input type="text" id="sysDataDictionary-form-addTableInfo-refertreecol" name="tableInfo.refertreecol" class="easyui-combogrid" style="width:200px;" autocomplete="off"/>
    		</p>	
    		<p style="height:70px;">
    			<label>描述:</label>
    			<textarea name="tableInfo.remark" class="easyui-validatebox" style="width:200px;height:60px;"></textarea>
    		</p>    		
    	</div>
    	<s:token></s:token>
    </form> 
    <script type="text/javascript">
    	$(function(){
            $("#sysDataDictionary-form-editTableInfo-moduletype").widget({
                referwid:'RL_T_SYS_CODE_MODULE',
                singleSelect:false
            });    		
    		$("#sysDataDictionary-form-addTableInfo-refertreecol").combogrid({
    			url:'sysDataDictionary/showDBTableColumnList.do?tablename=${tableInfo.tablename}',
			    panelWidth:320,  
			    idField:'columnname',  
			    textField:'colchnname', 
				columns : [[ 
					{field : 'columnname',title : '字段名',width : 100},
					{field : 'colchnname',title : '字段描述名',width : 100},
					{field : 'coldatatype',title : '数据类型',width : 80}
				]]
			});
    		$("#sysDataDictionary-form-addTableInfo-tablename").blur(function(){
    			var tablename=$.trim($(this).val()||"");
    			if(tablename!=""){
    				var url="sysDataDictionary/showDBTableColumnList.do?tablename="+tablename.toLowerCase();
    				$("#sysDataDictionary-form-addTableInfo-refertreecol").combogrid({'url':url});
    			}
    		});
    		$("#sysDataDictionary-form-addTableInfo-usetreelist").change(function(){
    			$("#sysDataDictionary-form-addTableInfo-refertreecol-panel").toggle();
    		});
    		$("#sysDataDictionary-form-addTableInfo").form({
    			onSubmit: function(){
    				var flag = $(this).form('validate');
    				if(flag==false){
    					return false;
    				}    
    			},
    			success:function(data){
    				//$.parseJSON()解析JSON字符串 
    				var json=$.parseJSON(data);
    				if(json.flag==true){
    					$.messager.alert("提醒","添加成功!");
    					$("#sysDataDictionary-dialog-tableInfoOper").dialog('close',true);
    					$("#sysDataDictionary-table-showTableInfoList").datagrid('reload');
    				}
    				else{
    					$.messager.alert("提醒",(json.msg||"添加失败！"));
    				}
    			}
    		});
    		$("#sysDataDictionary-save-addTableInfo").click(function(){
    			$.messager.confirm("提醒","是否添加表信息?",function(r){
    				if(r){
    				
	    				if($("#sysDataDictionary-form-addTableInfo-usetreelist").val()=="0"){
	    					$("#sysDataDictionary-form-addTableInfo-refertreecol").val("");
	    				}else{
	    					if($.trim($("#sysDataDictionary-form-addTableInfo  :input[name='tableInfo.refertreecol']").val())==""){
						    		$.messager.alert("错误","请填写树形父节点列名");
						    		return false;    						
	    					}
	    				}
    					try{
					    	var tablename=$("#sysDataDictionary-form-addTableInfo :input[name='tableInfo.tablename']").val() || "";
					    	if(tablename==""){
					    		$.messager.alert("错误","请填写表名称");
					    		return false;
					    	}
				    		$.ajax({   
					            url :'sysDataDictionary/existsTableInfoByTablename.do?tablename='+tablename.toLowerCase(),
					            type:'post',
					            dataType:'json',
					            success:function(json){
					            	if(json.flag==true){
					            		$.messager.alert("提醒","抱歉，"+tablename+"已经存在！");
					            		return false;
					            	}else{		            		
				    					$("#sysDataDictionary-form-addTableInfo").submit();
					            	}
					            }
					        });
				        }catch(e){
				        	$("#sysDataDictionary-form-addTableInfo").submit();
				        }
    				}
    			});
    		});
    	});
    </script>
  </body>
</html>
