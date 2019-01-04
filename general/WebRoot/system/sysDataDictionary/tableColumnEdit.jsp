<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>修改表结构信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  <body>
    <form action="sysDataDictionary/tableColumnEdit.do" method="post" id="sysDataDictionary-form-editTableColumn">
    	<div class="pageContent">
    		<p>
    			<label>表名:</label>${tableColumn.tablename}
    		</p>
    		<p>
    			<label for="tableColumn.columnname">列名:</label>
    			<input type="text" name="tableColumn.columnname" value="${ tableColumn.columnname }"  class="easyui-validatebox" validType="dbtable" required="true" style="width:200px;"/>
    			<span style="color:#f00">*</span>
    		</p>
    		<p>
    			<label>列描述名:</label>    			
    			<input type="text" name="tableColumn.colchnname" value="${ tableColumn.colchnname }"  class="easyui-validatebox" required="true" style="width:200px;"/>
    			<span style="color:#f00">*</span>
    		</p>
    		<p>
    			<label>数据类型:</label>    			
    			<input class="easyui-combobox" name="tableColumn.coldatatype" value="${tableColumn.coldatatype}"  style="width: 200px;"
    				data-options="required:true,
    						 	  valueField:'id',
    						      textField:'name',  						        
    						      url:'common/sysCodeList.do?type=mysqldatatype'"  />
    		</p>
    		<p>
    			<label>字段长度:</label>
    			<input type="text" name="tableColumn.colwidth" value="${tableColumn.colwidth }"  class="easyui-validatebox" validType="integer" style="width:200px;"/>
    		</p>
    		<p>
    			<label>小数位数:</label>
    			<input type="text" name="tableColumn.coldecimal" value="${tableColumn.coldecimal }" class="easyui-validatebox" validType="integer" style="width:200px;"/>
    		</p>
    		<p>
    			<label>默认值:</label>
    			<input type="text" name="tableColumn.coldefault"  class="easyui-validatebox"  style="width:200px;" value="${tableColumn.coldefault}"/>
    		</p>
    		<p>
    			<label>列说明:</label>
    			<input type="text" name="tableColumn.coldescription"  class="easyui-validatebox" style="width:200px;" value="${tableColumn.coldescription}"/>
    		</p>
    		<p>
    			<label>顺序:</label>
    			<input type="text" name="tableColumn.colorder"  class="easyui-validatebox" validType="number" required="true" style="width:200px;" value="${tableColumn.colorder}"/>
    			<span style="color:#f00">*</span>
    		</p>
    		<p>
    			<label>固定字段:</label>			
    			<select name="tableColumn.usefixed">
    				<option value="0" <c:if test="${tableColumn.usefixed=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${tableColumn.usefixed=='1'}">selected="selected"</c:if>>是</option>
    			</select>
    		</p>	
    		<p>
    			<label>编码字段:</label>			
    			<select name="tableColumn.usecoded" id="sysDataDictionary-form-editTableColumn-usecoded">
    				<option value="0" <c:if test="${tableColumn.usecoded=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${tableColumn.usecoded=='1'}">selected="selected"</c:if>>是</option>
    			</select>
    		</p>
    		<p id="sysDataDictionary-form-editTableColumn-p-codedcoltype" style="<c:if test="${tableColumn.usecoded !='1'}">display:none</c:if>">
    			<label>编码类型:</label>
    			<input class="easyui-combogrid" name="tableColumn.codedcoltype" style="width: 200px;"
    				id="sysDataDictionary-form-editTableColumn-codedcoltype" 
    				value="${tableColumn.codedcoltype }"
    				data-options="url:'common/sysCodeType.do',
    							  panelWidth:230,
    							  idField:'type',
    						      textField:'typename',
    						      filter:function(q,row){
				           		 	var id = row.type;
				           		 	var text = row.typename;
				           		 	if(id.indexOf(q)==0 || text.indexOf(q)==0){
				           		 		return true;
				           		 	}else{
				           		 		return false;
				           		 	}
				           		 },
    						      columns:[[ 
	    						      {field:'type',title:'类型',width:100},  
	    						      {field:'typename',title:'类型名称',width:100}
    						      ]]" />
    		</p>	
    		<p>
    			<label>是否主键:</label>			
    			<select name="tableColumn.usepk">
    				<option value="0" <c:if test="${tableColumn.usepk=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${tableColumn.usepk=='1'}">selected="selected"</c:if>>是</option>
    			</select>
    		</p>
    		<p>
    			<label>NULL值:</label>			
    			<select name="tableColumn.usenull">
    				<option value="0" <c:if test="${tableColumn.usenull=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${tableColumn.usenull=='1'}">selected="selected"</c:if>>是</option>
    			</select>
    		</p>
    		<p>
    			<label>应用类型:</label>			
    			<select name="tableColumn.colapplytype">
    				<option value="0" <c:if test="${tableColumn.colapplytype=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${tableColumn.colapplytype=='1'}">selected="selected"</c:if>>是</option>
    			</select>
    		</p>
    		<p>
    			<label>支持数据权限:</label>			
    			<select name="tableColumn.usedataprivilege">
    				<option value="0" <c:if test="${tableColumn.usedataprivilege=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${tableColumn.usedataprivilege=='1'}">selected="selected"</c:if>>是</option>
    			</select>
    		</p>
    		<p>
    			<label>支持字段权限:</label>			
    			<select name="tableColumn.usecolprivilege">
    				<option value="0" <c:if test="${tableColumn.usecolprivilege=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${tableColumn.usecolprivilege=='1'}">selected="selected"</c:if>>是</option>
    			</select>
    		</p>
    		<p>
    			<label>可做查询条件:</label>			
    			<select name="tableColumn.usecolquery">
    				<option value="0" <c:if test="${tableColumn.usecolquery=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${tableColumn.usecolquery=='1'}">selected="selected"</c:if>>是</option>
    			</select>
    		</p>
    		<p>
    			<label>支持数据导出:</label>			
    			<select name="tableColumn.usedataexport">
    				<option value="0" <c:if test="${tableColumn.usedataexport=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${tableColumn.usedataexport=='1'}">selected="selected"</c:if>>是</option>
    			</select>
    		</p>
    		<p>
    			<label>支持参照窗口:</label>			
    			<select id="sysDataDictionary-selectedit-usecolrefer" name="tableColumn.usecolrefer">
    				<option value="0" <c:if test="${tableColumn.usecolrefer=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${tableColumn.usecolrefer=='1'}">selected="selected"</c:if>>是</option>
    			</select>
    		</p>
    		<p id="sysDataDictionary-pedit-referwid" <c:if test="${tableColumn.usecolrefer=='0'}">style="display: none;" </c:if>>
    			<label>参照窗口:</label>
    			<input id="sysDataDictionary-inputedit-referwid" name="tableColumn.referwid" style="width: 200px;" value="${tableColumn.referwid}"/>
    			<input id="sysDataDictionary-hiddenedit-wname" type="hidden" name="tableColumn.wname"/>					
    		</p>
    		<p id="sysDataDictionary-p-referwflag" <c:if test="${tableColumn.usecolrefer=='0'}">style="display: none;" </c:if>>
    			<label>是否导出参照名称:</label>
    			<select id="sysDataDictionary-select-referwflag" name="tableColumn.referwflag">
    				<option value="0" <c:if test="${tableColumn.referwflag=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${tableColumn.referwflag=='1'}">selected="selected"</c:if>>是</option>
    			</select>
    		</p>
    		<p>
    			<label>是否引用显示:</label>			
    			<select name="tableColumn.isshow">
    				<option value="0" <c:if test="${tableColumn.isshow=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${tableColumn.isshow=='1'}">selected="selected"</c:if>>是</option>
    			</select>
    		</p>
    		<p>
    			<label>启用后是否可以修改:</label>			
    			<select name="tableColumn.isopenedit">
    				<option value="0" <c:if test="${tableColumn.isopenedit=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${tableColumn.isopenedit=='1'}">selected="selected"</c:if>>是</option>
    			</select>
    		</p>
    		<p>
    			<label>是否必填:</label>
    			<select name="tableColumn.isrequired">
    				<option value="0" <c:if test="${tableColumn.isrequired=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${tableColumn.isrequired=='1'}">selected="selected"</c:if>>是</option>
    			</select>
    		</p>
    	</div>
    	<input type="hidden" name="tableColumn.tablename" value="${tableColumn.tablename}" />
    	<input type="hidden" name="tableColumn.oldcolumnname" value="${tableColumn.tablename}" />
    	<input type="hidden" name="tableColumn.id" value="${tableColumn.id}" />
    </form> 
    <script type="text/javascript">
    	$(function(){
            $('#sysDataDictionary-inputedit-referwid').widget({
                referwid:'RL_T_AC_DATARULE_REFER',
                singleSelect:true,
                width:200,
                initValue:'${tableColumn.referwid}',
                onSelect:function(data){
                    $("#sysDataDictionary-hiddenedit-wname").attr("value",data.name);
                }
            });
    		$("#sysDataDictionary-selectedit-usecolrefer").change(function(){
				var val = $(this).val();
				if(val=='1'){
					$("#sysDataDictionary-pedit-referwid").show();
					$("#sysDataDictionary-p-referwflag").show();
				}else{
					$("#sysDataDictionary-pedit-referwid").hide();
					$("#sysDataDictionary-p-referwflag").hide();
					$("#sysDataDictionary-hiddenedit-wname").attr("value","");
				}
			});
    		$("#sysDataDictionary-form-editTableColumn-usecoded").change(function(){
    			if($(this).val()=="1"){
    				$("#sysDataDictionary-form-editTableColumn-p-codedcoltype").css("display","");
    				$("#sysDataDictionary-form-editTableColumn-codedcoltype").combogrid({required:true});
    			}else{
    				$("#sysDataDictionary-form-editTableColumn-p-codedcoltype").css("display","none");
    				$("#sysDataDictionary-form-editTableColumn-codedcoltype").combogrid({required:false});    				
    			}
    		});
    		$("#sysDataDictionary-form-editTableColumn").form({
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
    					$.messager.alert("提醒","修改成功!");
    					$("#sysDataDictionary-dialog-tableColumnOper").dialog('close',true);
    					$("#${divid}").datagrid('reload');
    				}
    				else{
    					$.messager.alert("提醒",(json.msg || "修改失败！"));
    				}
    			}
    		});
    		$("#sysDataDictionary-save-editTableColumn").click(function(){
    			$.messager.confirm("提醒","是否修改结构信息?",function(r){
    				if(r){    				
    					try{
    						if($("#sysDataDictionary-form-editTableColumn-usecoded").val()!="1"){
    							$("#sysDataDictionary-form-editTableColumn :input[name='tableColumn.codedcoltype']").val("");
    						}
					    	var tablename=$("#sysDataDictionary-form-editTableColumn :input[name='tableColumn.tablename']").val() || "";
					    	var columnname=$("#sysDataDictionary-form-editTableColumn :input[name='tableColumn.columnname']").val() || "";
					    	var oldcolumnname=$("#sysDataDictionary-form-editTableColumn :input[name='tableColumn.oldcolumnname']").val() || "";
					    	if(tablename==""){
					    		$.messager.alert("提醒","抱歉，未能找到相关表名称");
					    		return false;
					    	}
					    	if(columnname==""){
					    		$.messager.alert("提醒","请填写列名");
					    		return false;
					    	}
					    	if(oldcolumnname==""){
					    		$.messager.alert("提醒","抱歉，未能找到原列名。");
					    		return false;
					    	}
					    	
					    	if(columnname.toLowerCase()==oldcolumnname.toLowerCase()){					    		
				        		$("#sysDataDictionary-form-editTableColumn").submit();
				        		return true;
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
				    					$("#sysDataDictionary-form-editTableColumn").submit();
					            	}
					            }
					        });
				        }catch(e){
				        	$("#sysDataDictionary-form-editTableColumn").submit();
				        }
    				}
    			});
    		});
    	});
    </script>
  </body>
</html>
