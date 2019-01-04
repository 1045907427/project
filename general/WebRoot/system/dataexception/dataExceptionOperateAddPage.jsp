<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

  </head>
  
  <body>
    <form action="system/dataexception/addDataExceptionOperate.do" method="post" id="dataexception-form-addoperate">
	   	<div class="pageContent">
			<p>
				<label>对应菜单功能:</label>
				<input type="text" id="dataexception-input-operatemenu" style="width:200px;"  required="true"/>
			</p>
			<p>
				<label>对应操作:</label>
				<input type="text" id="dataexception-input-operatebutton" name="dataExceptionOperate.button" style="width:200px;"  required="true"/>
			</p>
			<p>
				<label>URL地址:</label>
				<input type="text" id="dataexception-input-operateurl"  name="dataExceptionOperate.url" style="width: 200px;"/>
			</p>
			<input type="hidden" id="dataexception-hidden-operatemenu" name="dataExceptionOperate.menu" />
			<input type="hidden" id="dataexception-hidden-pid" name="dataExceptionOperate.dataexceptionid" value="${pid }"/>
	    </div>
	    <div class="buttondiv">
			<a href="javaScript:void(0);" id="dataexception-button-saveOperate" class="easyui-linkbutton" data-options="iconCls:'button-save'">确定</a>
		</div>
    </form>
    <script type="text/javascript">
    	$("#dataexception-input-operatemenu").combotree({
    		url:'accesscontrol/getMenuTreeForEasyui.do',
    		onBeforeSelect:function(node){
    			if($(this).tree("isLeaf",node.target)){
    				$("#dataexception-hidden-operatemenu").attr("value",node.text);
    				var url = 'accesscontrol/showOperListByMenu.do?operateid='+node.id;
    				$("#dataexception-input-operatebutton").combogrid('setValue','');
	            	$("#dataexception-input-operatebutton").combogrid({'url':url});
    			}else{
    				$.messager.alert("提醒","请选择子节点");
    				return false;
    			}
    		}
    	});
    	$("#dataexception-input-operatebutton").combogrid({  
		    panelWidth:400,  
		    idField:'operatename',  
		    textField:'operatename', 
		    editable:false, 
		    columns:[[  
		        {field:'operatename',title:'操作名称',width:100},  
		        {field:'url',title:'URL地址',width:250}  
		    ]],
		    onClickRow:function(rowIndex, rowData){
		    	$("#dataexception-input-operateurl").attr("value",rowData.url);
		    }
		});
		$(function(){
			$("#dataexception-form-addoperate").form({  
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
			        	$("#dataexception-window-addDataExceptionOperate").dialog('close',true);
			        	$("#${divid}").datagrid('reload');
			        }else{
			        	$.messager.alert("提醒","添加失败");
			        }
			    }  
			});
			$("#dataexception-button-saveOperate").click(function(){
				$.messager.confirm("提醒", "是否给数据异常规则添加对应操作功能?", function(r){
					if (r){
						$("#dataexception-form-addoperate").submit();
					}
				});
			});
		});
    </script>
  </body>
</html>
