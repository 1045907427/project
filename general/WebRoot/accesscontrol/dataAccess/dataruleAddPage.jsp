<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>数据权限添加</title>
	<%@include file="/include.jsp" %> 
  </head>
  
  <body>
  	 <div class="easyui-layout" data-options="fit:true">  
  	 	<div data-options="region:'center'">
			<div style="padding:10px 5px;">
				<form action="accesscontrol/addDatarule.do" method="post" id="dataAccess-form-addDatarule">
				<p>
					<label style="float:left; width:100px; padding:0 5px; line-height:21px;">类型:</label>
					<select id="dataAccess-select-type" name="datarule.type" style="width: 200px">
						<option value="1">数据字典</option>
						<option value="2">参照窗口</option>
					</select>
				</p>
				<p>
					<label style="float:left; width:100px; padding:0 5px; line-height:21px;">资源:</label>
					<input id="dataAccess-select-dataList" style="width:400px" name="datarule.tablename" />
					<input type="hidden" id="dataAccess-hidden-dataname" name="datarule.dataname">
					<input type="hidden" id="dataAccess-hidden-rule" name="datarule.rule"/>
				</p>
                </br>
				<p>
					<label style="float:left; width:100px; padding:0 5px; line-height:21px;">备注:</label>
					<textarea  name="datarule.remark" style="width:400px;height: 80px;"></textarea>
				</p>
				<p>
					<label style="float:left; width:100px; padding:0 5px; line-height:21px;">数据权限:</label>
					<div id="dataAccess-ruleAdd">
					</div>
				</p>
				</form>
			</div>
		</div>
		<div data-options="region:'south'" style="height: 40px;">
			<div class="buttonDivR">
	    		<a href="javaScript:void(0);" id="dataAccess-button-saveDatarule" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'">保存</a>
	    	</div>
		</div>
	</div>
	<script type="text/javascript">
		//验证资源是否已经配置数据权限骨子
		$.extend($.fn.validatebox.defaults.rules, {
			tableSelectedVal:{  
		        validator: function (value, param) {  
		        	var flag = false;
		        	$.ajax({   
			            url :'accesscontrol/checkDataruleTable.do?name='+value+'&type='+$("#dataAccess-select-type").val(),
			            type:'post',
			            dataType:'json',
			            async: false,
			            success:function(json){
			            	flag =  json.flag;
			            }
			        });
			        return flag;
		        },  
		        message:'该资源已配置,请重新选择!'  
		    } 
		});
		$(function() {
			$("#dataAccess-ruleAdd").queryRule();
            $("#dataAccess-select-dataList").widget({
                width:400,
                referwid:'RL_T_AC_DATARULE_TABLENAME',
                singleSelect:true,
                onlyLeafCheck:false,
                onSelect:function(rowData){
                    $("#dataAccess-hidden-dataname").attr("value",rowData.name);
                    $("#dataAccess-ruleAdd").queryRule({
                        rules:'',
                        name:rowData.id
                    });
                }
            });
			$("#dataAccess-form-addDatarule").form({
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    },  
			    success:function(data){
			    	//转为json对象
			    	var json = $.parseJSON(data);
			        if(json.flag==true){
			        	parent.$.messager.alert("提醒","保存成功");
			        	tabsWindowURL("/accesscontrol/showDataPage.do").$("#datarule-table-dataruleList").datagrid('reload');
			        	top.closeTab("数据权限新增");
			        }else{
			        	$.messager.alert("提醒","保存失败。已存在该数据权限！");
			        }
			    },
			    error:function(){
			    	$.messager.alert("错误","系统出错！");
			    }
			});
			$("#dataAccess-button-saveDatarule").click(function() {
				var ss = $("#dataAccess-ruleAdd").queryRule('getRules');
				$("#dataAccess-hidden-rule").attr("value",ss);
				$.messager.confirm("提醒", "是否添加数据权限规则?", function(r){
					if (r){
						$("#dataAccess-form-addDatarule").submit();
					}
				});
			});
			//变更类型后
			$("#dataAccess-select-type").change(function(){
				var type = $(this).val();
				if(type=="1"){
                    $("#dataAccess-select-dataList").widget({
                        width:400,
                        referwid:'RL_T_AC_DATARULE_TABLENAME',
                        singleSelect:true,
                        onlyLeafCheck:false,
                        onSelect:function(rowData){
                            $("#dataAccess-hidden-dataname").attr("value",rowData.name);
                            $("#dataAccess-ruleAdd").queryRule({
                                rules:'',
                                name:rowData.id
                            });
                        }
                    });
				}else{
                    $("#dataAccess-select-dataList").widget({
                        width:400,
                        referwid:'RL_T_AC_DATARULE_REFER',
                        singleSelect:true,
                        onlyLeafCheck:false,
                        onSelect:function(rowData){
                            $("#dataAccess-hidden-dataname").attr("value",rowData.name);
                            $("#dataAccess-ruleAdd").queryRule({
                                rules:'',
                                name:rowData.id,
                                restype:'2'
                            });
                        }
                    });
				}
			});
		})
	</script>
  </body>
</html>
