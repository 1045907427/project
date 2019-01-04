<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>添加表结构信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">  
  </head>
  
  <body>
    <form action="sysDataDictionary/tableColumnAdd.do" method="post" id="sysDataDictionary-form-addTableColumn">
    	<div class="pageContent">
    		<p>
    			<label>表名:</label>${tablename}
    		</p>
    		<p>
    			<label for="tableColumn.columnname">列名:</label>
    			<input type="text" name="tableColumn.columnname"  class="easyui-validatebox" validType="dbtable" required="true" style="width:200px;" autocomplete="off"/>
    			<span style="color:#f00">*</span>
    		</p>
    		<p>
    			<label>列描述名:</label>    			
    			<input type="text" name="tableColumn.colchnname"  class="easyui-validatebox" required="true" style="width:200px;"/>
    			<span style="color:#f00">*</span>
    		</p>
    		<p>
    			<label>数据类型:</label>
    			<input class="easyui-combobox" name="tableColumn.coldatatype" style="width: 200px;"
    				data-options="required:true,
    							  url:'common/sysCodeList.do?type=mysqldatatype',
    							  valueField:'id',
    						      textField:'name'" />
    		</p>
    		<p>
    			<label>字段长度:</label>
    			<input type="text" name="tableColumn.colwidth"  class="easyui-validatebox" validType="integer" style="width:200px;"/>
    		</p>
    		<p>
    			<label>小数位数:</label>
    			<input type="text" name="tableColumn.coldecimal"  class="easyui-validatebox" validType="integer" style="width:200px;"/>
    		</p>
    		<p>
    			<label>默认值:</label>
    			<input type="text" name="tableColumn.coldefault" style="width:200px;"/>
    		</p>
    		<p>
    			<label>列说明:</label>
    			<input type="text" name="tableColumn.coldescription" style="width:200px;"/>
    		</p>
    		<p>
    			<label>顺序:</label>
    			<input type="text" name="tableColumn.colorder"  class="easyui-validatebox" validType="number" required="true" value="${icount }"/>
    			<span style="color:#f00">*</span>
    		</p>
    		<p>
    			<label>固定字段:</label>			
    			<select name="tableColumn.usefixed">
    				<option value="0">否</option>
    				<option value="1">是</option>
    			</select>
    		</p>	
    		<p>
    			<label>编码字段:</label>			
    			<select name="tableColumn.usecoded" id="sysDataDictionary-form-addTableColumn-usecoded">
    				<option value="0">否</option>
    				<option value="1">是</option>
    			</select>
    		</p>
    		<p id="sysDataDictionary-form-addTableColumn-p-codedcoltype" style="display: none">
    			<label>编码类型:</label>
    			<input class="easyui-combogrid" name="tableColumn.codedcoltype" style="width: 200px;"
    				id="sysDataDictionary-form-addTableColumn-codedcoltype" 
    				data-options="url:'common/sysCodeType.do',
    							  panelWidth:230,
    							  idField:'type',
    						      textField:'typename',
    						      editable:false,
    						      columns:[[ 
	    						      {field:'type',title:'类型',width:100},  
	    						      {field:'typename',title:'类型名称',width:100}
    						      ]]" />
    		</p>	
    		<p>
    			<label>是否主键:</label>			
    			<select name="tableColumn.userpk">
    				<option value="0">否</option>
    				<option value="1">是</option>
    			</select>
    		</p>
    		<p>
    			<label>NULL值:</label>			
    			<select name="tableColumn.usenull">
    				<option value="0">否</option>
    				<option value="1">是</option>
    			</select>
    		</p>
    		<p>
    			<label>应用类型:</label>			
    			<select name="tableColumn.colapplytype">
    				<option value="0">否</option>
    				<option value="1">是</option>
    			</select>
    		</p>
    		<p>
    			<label>支持数据权限:</label>			
    			<select name="tableColumn.usedataprivilege">
    				<option value="0">否</option>
    				<option value="1">是</option>
    			</select>
    		</p>
    		<p>
    			<label>支持字段权限:</label>			
    			<select name="tableColumn.usecolprivilege">
    				<option value="0">否</option>
    				<option value="1">是</option>
    			</select>
    		</p>
    		<p>
    			<label>可做查询条件:</label>			
    			<select name="tableColumn.usecolquery">
    				<option value="1">是</option>
    				<option value="0">否</option>
    			</select>
    		</p>
    		<p>
    			<label>支持数据导出:</label>			
    			<select name="tableColumn.usedataexport">
    				<option value="0">否</option>
    				<option value="1">是</option>
    			</select>
    		</p>
    		<p>
    			<label>是否支持参照窗口:</label>			
    			<select id="sysDataDictionary-select-usecolrefer" name="tableColumn.usecolrefer">
    				<option value="0">否</option>
    				<option value="1">是</option>
    			</select>
    		</p>
    		<p id="sysDataDictionary-p-referwid" style="display: none;">
    			<label>参照窗口:</label>
    			<input id="sysDataDictionary-input-referwid" name="tableColumn.referwid" style="width: 200px;"/>	
    			<input id="sysDataDictionary-hidden-wname" type="hidden" name="tableColumn.wname"/>		
    		</p>
    		<p id="sysDataDictionary-p-referwflag" style="display: none;">
    			<label>是否导出参照名称:</label>
    			<select id="sysDataDictionary-select-referwflag" name="tableColumn.referwflag">
    				<option value="0">否</option>
    				<option value="1">是</option>
    			</select>
    		</p>
    		<p>
    			<label>是否引用显示:</label>			
    			<select name="tableColumn.isshow">
    				<option value="0">否</option>
    				<option value="1" selected="selected">是</option>
    			</select>
    		</p>
    		<p>
    			<label>启用后是否可以修改:</label>			
    			<select name="tableColumn.isopen">
    				<option value="0">否</option>
    				<option value="1">是</option>
    			</select>
    		</p>
    		<p>
    			<label>是否必填:</label>
    			<select name="tableColumn.isrequired">
    				<option value="0">否</option>
    				<option value="1">是</option>
    			</select>
    		</p>
    	</div>
    	<input type="hidden" name="tableColumn.tablename" value="${tablename}" />
    </form> 
    <script type="text/javascript">
    	$(function(){
            $('#sysDataDictionary-input-referwid').widget({
                referwid:'RL_T_AC_DATARULE_REFER',
                singleSelect:true,
                width:200,
                onSelect:function(data){
                    $("#sysDataDictionary-hidden-wname").attr("value",data.name);
                }
            });
			$("#sysDataDictionary-select-usecolrefer").change(function(){
				var val = $(this).val();
				if(val=='1'){
					$("#sysDataDictionary-p-referwid").show();
					$("#sysDataDictionary-p-referwflag").show();
                    $('#sysDataDictionary-input-referwid').widget({
                        referwid:'RL_T_AC_DATARULE_REFER',
                        singleSelect:true,
                        width:200,
                        required:true,
                        onSelect:function(data){
                            $("#sysDataDictionary-hidden-wname").attr("value",data.name);
                        }
                    });
				}else{
					$("#sysDataDictionary-p-referwid").hide();
					$("#sysDataDictionary-p-referwflag").hide();
					$('#sysDataDictionary-input-referwid').widget("clear");
					$("#sysDataDictionary-hidden-wname").attr("value","");
                    $('#sysDataDictionary-input-referwid').widget({
                        referwid:'RL_T_AC_DATARULE_REFER',
                        singleSelect:true,
                        width:200,
                        required:false,
                        onSelect:function(data){
                            $("#sysDataDictionary-hidden-wname").attr("value",data.name);
                        }
                    });
				}
			});
			$("#sysDataDictionary-form-addTableColumn-usecoded").change(function(){
    			if($(this).val()=="1"){
    				$("#sysDataDictionary-form-addTableColumn-p-codedcoltype").show();
    				$("#sysDataDictionary-form-addTableColumn-codedcoltype").combogrid({required:true});
    			}else{
    				$("#sysDataDictionary-form-addTableColumn-p-codedcoltype").hide();
    				$("#sysDataDictionary-form-addTableColumn-codedcoltype").combogrid({required:false});    				
    			}
    		});
    		$("#sysDataDictionary-form-addTableColumn").form({
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
    					$("#sysDataDictionary-dialog-tableColumnOper").dialog('close',true);
    					$("#${divid}").datagrid('reload');
    				}
    				else{
    					$.messager.alert("提醒",( json.msg|| "添加失败！"));
    				}
    			}
    		});
    		$("#sysDataDictionary-save-addTableColumn").click(function(){
    			$.messager.confirm("提醒","是否添加结构信息?",function(r){
    				if(r){    				
    					try{
    						if($("#sysDataDictionary-form-editTableColumn-usecoded").val()!="1"){
    							$("#sysDataDictionary-form-editTableColumn :input[name='tableColumn.codedcoltype']").val("");
    						}
					    	var tablename=$("#sysDataDictionary-form-addTableColumn :input[name='tableColumn.tablename']").val() || "";
					    	var columnname=$("#sysDataDictionary-form-addTableColumn :input[name='tableColumn.columnname']").val() || "";
					    	if(tablename==""){
					    		$.messager.alert("提醒","请填写表名称");
					    		return false;
					    	}
					    	if(columnname==""){
					    		$.messager.alert("提醒","请填写列名");
					    		return false;
					    	}
				    		$.ajax({   
					            url :'sysDataDictionary/existsTableColumnBy.do',
					            type:'post',
					            dataType:'json',
					            data:{'tablename':tablename.toLowerCase(),'columnname':columnname.toLowerCase},
					            success:function(json){
					            	if(json.flag==true){
					            		$.messager.alert("提醒","抱歉，表："+tablename+"中的字段 "+columnname+"已经存在！");
					            		return false;
					            	}else{		            		
				    					$("#sysDataDictionary-form-addTableColumn").submit();
					            	}
					            }
					        });
				        }catch(e){
				        	$("#sysDataDictionary-form-addTableColumn").submit();
				        }
    				}
    			});
    		});
    	});
    </script>
  </body>
</html>
