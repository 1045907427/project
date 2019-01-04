<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>数据异常规则添加</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  
  <body>
      <form action="system/dataexception/addDataException.do" method="post" id="dataexception-form-add">
	   	<div class="pageContent">
			<p>
				<label>主表名:</label>
				<input type="text" id="dataexception-input-mtable" name="dataException.mtable" style="width:200px;"  required="true"/>
			</p>
			<p>
				<label>主表字段:</label>
				<input type="text" id="dataexception-input-mcolumn" name="dataException.mcolumn" style="width:200px;"  required="true"/>
			</p>
			<p>
				<label>校验取值类型:</label>
				<select name="dataException.type" id="dataexception-select-type" style="width:200px;">
					<option value="1">固定值</option>
					<option value="2">其他表</option>
				</select>
			</p>
			<p>
				<label>固定值:</label>
				<input type="text" id="dataexception-input-val" class="easyui-numberbox"  data-options="precision:4,groupSeparator:',',max:9999999999999" name="dataException.val" style="width:200px;" />
			</p>
			<p>
				<label>对应表名:</label>
				<input type="text" id="dataexception-input-rtable" name="dataException.rtable" style="width:200px;"/>
			</p>
			<p>
				<label>对应表字段:</label>
				<input type="text" id="dataexception-input-rcolumn" name="dataException.rcolumn" style="width:200px;" />
			</p>
			<p>
				<label>主表关联字段:</label>
				<input type="text" id="dataexception-input-mrelatecolumn" name="dataException.mrelatecolumn" style="width:200px;" />
			</p>
			<p>
				<label>对应表关联字段:</label>
				<input type="text" id="dataexception-input-rrelatecolumn" name="dataException.rrelatecolumn" style="width:200px;" />
			</p>
			<p>
				<label>正常范围上限(%):</label>
				<input type="text" id="dataexception-input-normalup" name="dataException.normalup" class="easyui-validatebox easyui-numberbox"  validType="normal" data-options="precision:4,groupSeparator:',',max:100,min:0" style="width:200px;"  required="true"/>
			</p>
			<p>
				<label>正常范围下限(%):</label>
				<input type="text" id="dataexception-input-normaldown" name="dataException.normaldown" class="easyui-validatebox easyui-numberbox" validType="normal" data-options="precision:4,groupSeparator:',',max:100,min:0" style="width:200px;"  required="true"/>
			</p>
			<p>
				<label>异常常范围上限(%):</label>
				<input type="text" name="dataException.exceptionup" class="easyui-validatebox easyui-numberbox" validType="numberCompare['dataexception-input-normalup']" invalidMessage="异常常范围上限必须大于正常范围上限或者等于0" data-options="precision:4,groupSeparator:',',max:100,min:0" style="width:200px;" />
			</p>
			<p>
				<label>异常范围下限(%):</label>
				<input type="text" name="dataException.exceptiondown" class="easyui-validatebox easyui-numberbox" validType="numberCompare['dataexception-input-normaldown']" invalidMessage="异常常范围下限必须大于正常范围下限或者等于0" data-options="precision:4,groupSeparator:',',max:100,min:0" style="width:200px;" />
			</p>
			<p>
				<label>异常提醒内容:</label>
				<input type="text" name="dataException.exremind" style="width:200px;" />
			</p>
			<p>
				<label>状态:</label>
				<select name="dataException.state" style="width:200px;">
					<option value="1">启用</option>
					<option value="0">停用</option>
				</select>
			</p>
			<input type="hidden" id="dataexception-hidden-name" name="dataException.name"/>
	    </div>
	    <div class="buttondiv">
			<a href="javaScript:void(0);" id="dataexception-button-save" class="easyui-linkbutton" data-options="iconCls:'button-save'">确定</a>
		</div>
    </form>
    <script type="text/javascript">
    	$.extend($.fn.validatebox.defaults.rules, {
    		normal:{
		    	validator : function(value, param){ 
		    		if(value>0){
		    			return true;
		    		}else{
		    			return false;
		    		}
			    }, 
			    message : '正常范围限需大于0'
		    },
		    numberCompare:{
		    	validator : function(value, param){ 
		    		if(value==0){
		    			return true;
		    		}
			        if($("#"+param[0]).val() != "" && value != ""){ 
			            return value -$("#"+param[0]).val()>0 ; 
			        }else{ 
			            return true; 
			        } 
			    }, 
			    message : '输入的值太小'
		    }
    	});
   		$("#dataexception-input-mtable").combogrid({  
		    url:'common/getTableList.do', 
		    panelWidth:400,  
  	 		rownumbers:true,
		    idField:'id',  
		    textField:'name', 
		    editable:false,
		    columns:[[  
		        {field:'id',title:'表名',width:120},  
		        {field:'name',title:'描述名',width:120}
		    ]],
		    onClickRow:function(owIndex, rowData){
		    	$("#dataexception-hidden-name").attr("value",rowData.name);
		    	var url = 'common/getTableColList.do?name='+rowData.id; 
	            $("#dataexception-input-mcolumn").combogrid('setValue','');
	            $("#dataexception-input-mcolumn").combogrid({'url':url});
	            $("#dataexception-input-mrelatecolumn").combogrid('setValue','');
	            $("#dataexception-input-mrelatecolumn").combogrid({'url':url});	   
	            var type = $("#dataexception-select-type").val();
	            if(type=="1"){
	             	$("#dataexception-input-mrelatecolumn").combogrid("disable");
	            }         
		    }
		});
		$("#dataexception-input-mcolumn").combogrid({  
		    panelWidth:320,  
		    idField:'id',  
		    textField:'id', 
		    editable:false, 
		    columns:[[  
		        {field:'id',title:'字段名',width:100},  
		        {field:'name',title:'字段描述名',width:100},  
		        {field:'datatype',title:'数据类型',width:80}
		    ]]  
		});
		$("#dataexception-input-mrelatecolumn").combogrid({  
		    panelWidth:320,  
		    idField:'id',  
		    textField:'id', 
		    editable:false, 
		    columns:[[  
		        {field:'id',title:'字段名',width:100},  
		        {field:'name',title:'字段描述名',width:100},  
		        {field:'datatype',title:'数据类型',width:80}
		    ]]  
		});
		$("#dataexception-input-rtable").combogrid({  
		    url:'common/getTableList.do', 
		    panelWidth:400,  
  	 		rownumbers:true,
		    idField:'id',  
		    textField:'name', 
		    editable:false,
		    columns:[[  
		        {field:'id',title:'表名',width:120},  
		        {field:'name',title:'描述名',width:120}
		    ]],
		    onClickRow:function(owIndex, rowData){
		    	
		    	var url = 'common/getTableColList.do?name='+rowData.id; 
	            $("#dataexception-input-rcolumn").combogrid('setValue','');
	            $("#dataexception-input-rcolumn").combogrid({'url':url});
	            $("#dataexception-input-rrelatecolumn").combogrid('setValue','');
	            $("#dataexception-input-rrelatecolumn").combogrid({'url':url});	            
		    }
		});
		$("#dataexception-input-rcolumn").combogrid({  
		    panelWidth:320,  
		    idField:'id',  
		    textField:'id', 
		    editable:false, 
		    columns:[[  
		        {field:'id',title:'字段名',width:100},  
		        {field:'name',title:'字段描述名',width:100},  
		        {field:'datatype',title:'数据类型',width:80}
		    ]]  
		});
		$("#dataexception-input-rrelatecolumn").combogrid({  
		    panelWidth:320,  
		    idField:'id',  
		    textField:'id', 
		    editable:false, 
		    columns:[[  
		        {field:'id',title:'字段名',width:100},  
		        {field:'name',title:'字段描述名',width:100},  
		        {field:'datatype',title:'数据类型',width:80}
		    ]]  
		});
		$(function(){
			//校验取值类型发生变化时
			$("#dataexception-select-type").change(function(){
				var val = $(this).val();
				if(val=="1"){
					$("#dataexception-input-val").numberbox({
						disabled:false
					});
					$("#dataexception-input-rtable").combogrid("setValue","");
					$("#dataexception-input-rcolumn").combogrid("setValue","");
					$("#dataexception-input-rrelatecolumn").combogrid("setValue","");
					$("#dataexception-input-mrelatecolumn").combogrid("setValue","");
					
					$("#dataexception-input-rtable").combogrid("disable");
					$("#dataexception-input-rcolumn").combogrid("disable");
					$("#dataexception-input-rrelatecolumn").combogrid("disable");
					$("#dataexception-input-mrelatecolumn").combogrid("disable");
					
				}else{
					$("#dataexception-input-val").numberbox("setValue",0);
					$("#dataexception-input-val").numberbox({
						disabled:true
					});
					
					$("#dataexception-input-rtable").combogrid("enable");
					$("#dataexception-input-rcolumn").combogrid("enable");
					$("#dataexception-input-rrelatecolumn").combogrid("enable");
					$("#dataexception-input-mrelatecolumn").combogrid("enable");
				}
			});
			//保存
			$("#dataexception-button-save").click(function(){
				$.messager.confirm("提醒", "是否添加数据异常规则?", function(r){
					if (r){
						$("#dataexception-form-add").submit();
					}
				});
			});
			$("#dataexception-form-add").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	//转为json对象
			    	var json = $.parseJSON(data);
			        if(json.flag==true){
			        	$.messager.alert("提醒","添加成功");
			        	$("#dataexception-window-addDataException").dialog('close',true);
			        	$("#dataexception-table-list").datagrid('reload');
			        }else{
			        	$.messager.alert("提醒","添加失败");
			        }
			    }  
			});
			
			$("#dataexception-input-rtable").combogrid("disable");
			$("#dataexception-input-rcolumn").combogrid("disable");
			$("#dataexception-input-rrelatecolumn").combogrid("disable");
			$("#dataexception-input-mrelatecolumn").combogrid("disable");
		});
    </script>
  </body>
</html>
