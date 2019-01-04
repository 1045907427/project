<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>数据异常规则添加</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  
  <body>
      <form action="system/dataexception/editDataException.do" method="post" id="dataexception-form-edit">
	   	<div class="pageContent">
			<p>
				<label>主表名:</label>
				<input type="text" id="dataexception-editinput-mtable" name="dataException.mtable" style="width:200px;"  required="true"/>
			</p>
			<p>
				<label>主表字段:</label>
				<input type="text" id="dataexception-editinput-mcolumn" name="dataException.mcolumn" style="width:200px;"  required="true"/>
			</p>
			<p>
				<label>校验取值类型:</label>
				<select name="dataException.type" id="dataexception-editselect-type" style="width:200px;">
					<option value="1" <c:if test="${dataException.type=='1'}">selected="selected"</c:if>>固定值</option>
					<option value="2" <c:if test="${dataException.type=='2'}">selected="selected"</c:if>>其他表</option>
				</select>
			</p>
			<p>
				<label>固定值:</label>
				<input type="text" id="dataexception-editinput-val" class="easyui-numberbox"  data-options="precision:4,groupSeparator:',',max:9999999999999" name="dataException.val" style="width:200px;" value="${dataException.val }"/>
			</p>
			<p>
				<label>对应表名:</label>
				<input type="text" id="dataexception-editinput-rtable" name="dataException.rtable" style="width:200px;"/>
			</p>
			<p>
				<label>对应表字段:</label>
				<input type="text" id="dataexception-editinput-rcolumn" name="dataException.rcolumn" style="width:200px;" />
			</p>
			<p>
				<label>主表关联字段:</label>
				<input type="text" id="dataexception-editinput-mrelatecolumn" name="dataException.mrelatecolumn" style="width:200px;" />
			</p>
			<p>
				<label>对应表关联字段:</label>
				<input type="text" id="dataexception-editinput-rrelatecolumn" name="dataException.rrelatecolumn" style="width:200px;" />
			</p>
			<p>
				<label>正常范围上限(%):</label>
				<input type="text" id="dataexception-editinput-normalup" name="dataException.normalup" value="${dataException.normalup}" class="easyui-validatebox easyui-numberbox"  validType="normal" data-options="precision:4,groupSeparator:',',max:100,min:0" style="width:200px;"  required="true"/>
			</p>
			<p>
				<label>正常范围下限(%):</label>
				<input type="text" id="dataexception-editinput-normaldown" name="dataException.normaldown" value="${dataException.normaldown}" class="easyui-validatebox easyui-numberbox" validType="normal" data-options="precision:4,groupSeparator:',',max:100,min:0" style="width:200px;"  required="true"/>
			</p>
			<p>
				<label>异常常范围上限(%):</label>
				<input type="text" name="dataException.exceptionup" value="${dataException.exceptionup}" class="easyui-validatebox easyui-numberbox" validType="numberCompare['dataexception-editinput-normalup']" invalidMessage="异常常范围上限必须大于正常范围上限或者等于0" data-options="precision:4,groupSeparator:',',max:100,min:0" style="width:200px;" />
			</p>
			<p>
				<label>异常范围下限(%):</label>
				<input type="text" name="dataException.exceptiondown" value="${dataException.exceptiondown}" class="easyui-validatebox easyui-numberbox" validType="numberCompare['dataexception-editinput-normaldown']" invalidMessage="异常常范围下限必须大于正常范围下限或者等于0" data-options="precision:4,groupSeparator:',',max:100,min:0" style="width:200px;" />
			</p>
			<p>
				<label>异常提醒内容:</label>
				<input type="text" name="dataException.exremind" value="${dataException.exremind }" style="width:200px;" />
			</p>
			<p>
				<label>状态:</label>
				<select name="dataException.state" style="width:200px;">
					<option value="1" <c:if test="${dataException.state=='1'}">selected="selected"</c:if>>启用</option>
					<option value="0" <c:if test="${dataException.state=='0'}">selected="selected"</c:if>>停用</option>
				</select>
			</p>
			<input type="hidden" id="dataexception-edithidden-name" name="dataException.name" value="${dataException.name }"/>
			<input  type="hidden" name="dataException.id" value="${dataException.id}"/>
	    </div>
	    <div class="buttondiv">
			<a href="javaScript:void(0);" id="dataexception-editbutton-save" class="easyui-linkbutton" data-options="iconCls:'button-save'">确定</a>
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
   		$("#dataexception-editinput-mtable").combogrid({  
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
		    	$("#dataexception-edithidden-name").attr("value",rowData.name);
		    	var url = 'common/getTableColList.do?name='+rowData.id; 
	            $("#dataexception-editinput-mcolumn").combogrid('setValue','');
	            $("#dataexception-editinput-mcolumn").combogrid({'url':url});
	            $("#dataexception-editinput-mrelatecolumn").combogrid('setValue','');
	            $("#dataexception-editinput-mrelatecolumn").combogrid({'url':url});	   
	            var type = $("#dataexception-editselect-type").val();
	            if(type=="1"){
	             	$("#dataexception-editinput-mrelatecolumn").combogrid("disable");
	            }         
		    },
		    onLoadSuccess:function(){
		    	$("#dataexception-editinput-mtable").combogrid('setValue','${dataException.mtable}');
		    }
		});
		$("#dataexception-editinput-mcolumn").combogrid({  
		    panelWidth:320,  
		    idField:'id',  
		    textField:'id', 
		    editable:false, 
		    url:'common/getTableColList.do?name=${dataException.mtable}',
		    columns:[[  
		        {field:'id',title:'字段名',width:100},  
		        {field:'name',title:'字段描述名',width:100},  
		        {field:'datatype',title:'数据类型',width:80}
		    ]] ,
		    onLoadSuccess:function(){
		    	$("#dataexception-editinput-mcolumn").combogrid('setValue','${dataException.mcolumn}');
		    } 
		});
		$("#dataexception-editinput-mrelatecolumn").combogrid({  
		    panelWidth:320,  
		    idField:'id',  
		    textField:'id', 
		    editable:false, 
		    url:'common/getTableColList.do?name=${dataException.mtable}',
		    columns:[[  
		        {field:'id',title:'字段名',width:100},  
		        {field:'name',title:'字段描述名',width:100},  
		        {field:'datatype',title:'数据类型',width:80}
		    ]],
		    onLoadSuccess:function(){
		    	$("#dataexception-editinput-mrelatecolumn").combogrid('setValue','${dataException.mrelatecolumn}');
		    } 
		});
		$("#dataexception-editinput-rtable").combogrid({  
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
	            $("#dataexception-editinput-rcolumn").combogrid('setValue','');
	            $("#dataexception-editinput-rcolumn").combogrid({'url':url});
	            $("#dataexception-editinput-rrelatecolumn").combogrid('setValue','');
	            $("#dataexception-editinput-rrelatecolumn").combogrid({'url':url});	            
		    },
		    onLoadSuccess:function(){
		    	$("#dataexception-editinput-rtable").combogrid('setValue','${dataException.rtable}');
		    }
		});
		$("#dataexception-editinput-rcolumn").combogrid({  
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
		$("#dataexception-editinput-rrelatecolumn").combogrid({  
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
			$("#dataexception-editselect-type").change(function(){
				var val = $(this).val();
				if(val=="1"){
					$("#dataexception-editinput-val").numberbox({
						disabled:false
					});
					$("#dataexception-editinput-rtable").combogrid("setValue","");
					$("#dataexception-editinput-rcolumn").combogrid("setValue","");
					$("#dataexception-editinput-rrelatecolumn").combogrid("setValue","");
					$("#dataexception-editinput-mrelatecolumn").combogrid("setValue","");
					
					$("#dataexception-editinput-rtable").combogrid("disable");
					$("#dataexception-editinput-rcolumn").combogrid("disable");
					$("#dataexception-editinput-rrelatecolumn").combogrid("disable");
					$("#dataexception-editinput-mrelatecolumn").combogrid("disable");
					
				}else{
					$("#dataexception-editinput-val").numberbox("setValue",0);
					$("#dataexception-editinput-val").numberbox({
						disabled:true
					});
					
					$("#dataexception-editinput-rtable").combogrid("enable");
					$("#dataexception-editinput-rcolumn").combogrid("enable");
					$("#dataexception-editinput-rrelatecolumn").combogrid("enable");
					$("#dataexception-editinput-mrelatecolumn").combogrid("enable");
				}
			});
			//保存
			$("#dataexception-editbutton-save").click(function(){
				$.messager.confirm("提醒", "是否修改数据异常规则?", function(r){
					if (r){
						$("#dataexception-form-edit").submit();
					}
				});
			});
			$("#dataexception-form-edit").form({  
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
			        	$.messager.alert("提醒","修改成功");
			        	$("#dataexception-window-editDataException").dialog('close',true);
			        	$("#dataexception-table-list").datagrid('reload');
			        }else{
			        	$.messager.alert("提醒","修改失败");
			        }
			    }  
			});
			<c:if test="${dataException.type=='1'}">
			$("#dataexception-editinput-rtable").combogrid("disable");
			$("#dataexception-editinput-rcolumn").combogrid("disable");
			$("#dataexception-editinput-rrelatecolumn").combogrid("disable");
			$("#dataexception-editinput-mrelatecolumn").combogrid("disable");
			</c:if>
			<c:if test="${dataException.type=='2'}">
			var url1 = 'common/getTableColList.do?name=${dataException.rtable}'; 
            $("#dataexception-editinput-rcolumn").combogrid({'url':url1});
            $("#dataexception-editinput-rcolumn").combogrid('setValue','${dataException.rcolumn}');
            $("#dataexception-editinput-rrelatecolumn").combogrid({'url':url1});
            $("#dataexception-editinput-rrelatecolumn").combogrid('setValue','${dataException.rrelatecolumn}');
			</c:if>
		});
    </script>
  </body>
</html>
