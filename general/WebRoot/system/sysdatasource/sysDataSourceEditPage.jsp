<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>数据源配置修改</title>
  </head>
  
  <body>
 <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'center',border:false">
       		<div align="center" style="padding: 10px;">
			    <form action="system/sysdatasource/editSysDataSource.do" method="post" id="system-form-addSysDataSource">
					<table cellpadding="3" cellspacing="3" border="0">
						<tr>
							<td align="right">名称:</td>
							<td align="left">
								<input type="text" name="sysDataSource.name" value="${sysDataSource.name}" id="system-SysDataSource-form-name" class="easyui-validatebox" required="true" validType="maxByteLength[120]" style="width:300px;" autocomplete="off" />
							</td>
						</tr>
						<tr>
							<td align="right">代码:</td>
							<td align="left">
								<input type="text" name="sysDataSource.code" value="${sysDataSource.code}" id="system-SysDataSource-form-code" class="easyui-validatebox" data-options="required:true,validType:['maxByteLength[120]','validUsedName[241]']" style="width:300px;" autocomplete="off" readonly="readonly" />
							</td>
						</tr>
						<tr>
							<td align="right">JDBC类型:</td>
							<td align="left">
								<input type="text" name="sysDataSource.jdbctype" value="${sysDataSource.jdbctype}" id="system-SysDataSource-form-jdbctype" class="easyui-validatebox" data-options="required:true,validType:['maxByteLength[120]','validUsedName[241]']" style="width:300px;" autocomplete="off" />
							</td>
						</tr>
						<tr>
							<td align="right">JDBC驱动:</td>
							<td align="left">
								<input type="text" name="sysDataSource.jdbcdriver" value="${sysDataSource.jdbcdriver}" id="system-SysDataSource-form-jdbcdriver" class="easyui-validatebox" required="true" validType="maxByteLength[120]" style="width:300px;" autocomplete="off" />
							</td>
						</tr>
						<tr>
							<td align="right">JDBC链接:</td>
							<td align="left">
								<input type="text" name="sysDataSource.jdbcurl" value="${sysDataSource.jdbcurl}" id="system-SysDataSource-form-jdbcurl" class="easyui-validatebox" required="true" validType="maxByteLength[250]" style="width:300px;" autocomplete="off" />
							</td>
						</tr>
						<tr>
							<td align="right">数据库名称:</td>
							<td align="left">
								<input type="text" name="sysDataSource.dbname" value="${sysDataSource.dbname}" id="system-SysDataSource-form-dbname" class="easyui-validatebox" required="true" validType="maxByteLength[50]" style="width:300px;" autocomplete="off" />
							</td>
						</tr>
						<tr>
							<td align="right">用户名:</td>
							<td align="left">
								<input type="text" name="sysDataSource.dbuser" value="${sysDataSource.dbuser}" id="system-SysDataSource-form-dbuser" class="easyui-validatebox" required="true" validType="maxByteLength[50]" style="width:300px;" autocomplete="off" />
							</td>
						</tr>
						<tr>
							<td align="right">密码:</td>
							<td align="left">
								<div>
									<lable>
										<input type="checkbox" value="1" name="ismodifypasswd" id="system-sysDataSource-modifypasswd" />
										修改
									</lable>
								</div>
								<div id="system-sysDataSource-passwd-div" style="display: none">
									<input type="password" name="sysDataSource.dbpasswd" value="${sysDataSource.dbpasswd}" id="system-SysDataSource-form-dbpasswd" class="easyui-validatebox" style="width:300px;" autocomplete="off" />
								</div>
							</td>
						</tr>
						<tr>
							<td align="right">状态:</td>
							<td align="left">
								<select style="width:300px;" disabled="disabled">
									<option value="1" <c:if test="${sysDataSource.state=='1' }">selected="selected"</c:if> >有效</option>
									<option value="0" <c:if test="${sysDataSource.state=='0' }">selected="selected"</c:if> >无效</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right">备注:</td>
							<td align="left">
								<textarea rows="0" cols="0" name="sysDataSource.remark" style="width:300px;height:80px;" class="easyui-validatebox" validType="maxByteLength[240]">${sysDataSource.remark}</textarea>
							</td>
						</tr>
    				</table>
    				<input type="hidden" name="sysDataSource.id" value="${sysDataSource.id }" />
			    </form>
	    	</div>
      </div>
      <div data-options="region:'south',border:false">
          <div class="buttonDetailBG" style="height:30px;text-align:right;">
              <input type="button" name="savegoon" id="system-save-addSysDataSource" value="确定"/>
          </div>
      </div>
  </div>
    <script type="text/javascript">
    	$(function(){
            validUsedName('${sysDataSource.name }','该名称已被使用,请另输入!');
            validUsedCode('${sysDataSource.code }','该分类代码已被使用,请另输入!');
            $("#system-sysDataSource-modifypasswd").click(function(){
                $("#system-SysDataSource-form-dbpasswd").val("");
                if($(this).prop("checked")==true){
                    $("#system-sysDataSource-passwd-div").show();
                    //$("#system-SysDataSource-form-dbpasswd").validatebox({required:true});
				}else{
                    $("#system-sysDataSource-passwd-div").hide();
                    //$("#system-SysDataSource-form-dbpasswd").validatebox({required:false});
				}
			});
    		$("#system-form-addSysDataSource").form({
    			onSubmit: function(){
    				var flag = $(this).form('validate');
    				if(flag==false){
    					return false;
    				}
    			},
    			success:function(data){
    				//$.parseJSON()解析JSON字符串 
    				var json = $.parseJSON(data);
    				if(json.flag==true){
    					$.messager.alert("提醒","修改数据源配置成功!");
                        $("#system-dialog-sysDataSourceOper-content").dialog('close',true);

                        try{
                            $("#system-table-sysDataSourceList").datagrid('reload');
                        }catch(e){

                        }
    				}
    				else{
    				    var msg="修改数据源配置失败!";
    				    if(json.msg!=null){
    				        msg=msg+"<br/>"+json.msg;
						}
    					$.messager.alert("提醒",msg);
    				}
    			}
    		});
    		$("#system-save-addSysDataSource").click(function(){

                var passwd=$("#system-SysDataSource-form-dbpasswd").val()||"";
                var msg="";
                if($.trim(passwd)==""){
					msg="注意，您填写的数据库密码为空。<br/>";
                }
    			$.messager.confirm("提醒",msg+"是否修改数据源配置信息?",function(r){
    				if(r){
    					$("#system-form-addSysDataSource").submit();
    				}
    			});
    		});


            $("#system-SysDataSource-form-jdbctype").widget({
                referwid:'RL_T_SYS_CODE_ENABLE',
                singleSelect:true,
                onlyLeafCheck:false,
                width:'300',
                required:true,
                param:[{field:'type',op:'equal',value:'DBJDBCTYPE'}],
                onSelect:function(data){
                    var valObj=$("#system-SysDataSource-form-jdbctype").widget("getObject") || {};
                    var codevalue=$.trim(valObj.codevalue || "");
                    $("#system-SysDataSource-form-jdbcdriver").val("");
                    if(codevalue!=""){
                        $("#system-SysDataSource-form-jdbcdriver").val(codevalue);
                    }

                    setTimeout(function() {
                        $("#system-SysDataSource-form-jdbcdriver").validatebox('validate');
                    },100);
                },
                onClear:function(){
                    $("#system-SysDataSource-form-jdbcdriver").val("");
                }
            });

    	});
    </script>
  </body>
</html>
  