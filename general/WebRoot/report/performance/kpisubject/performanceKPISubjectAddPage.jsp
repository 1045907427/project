<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>考核指标科目添加</title>
  </head> 
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center">
	    		<form action="" method="post" id="performanceKPISubject-form-add" style="padding: 10px;">
	    			<table cellpadding="2" cellspacing="2" border="0">
	    				<tr>
	    					<td width="70px" align="left">科目代码名:</td>
	    					<td align="left">
	    						<select id="performanceKPISubject-form-add-code" name="performanceKPISubject.code" style="width:100px;">
		    						<option value="">--请选择--</option>
		    						<c:forEach items="${kpiScoreIndexSubject }" var="list">
		    							<option value="${list.code }">${list.codename }</option>
		    						</c:forEach>
		    					</select>
		    					<label id="performanceKPISubject-form-add-code-text"></label>
		    					<input type="hidden" id="performanceKPISubject-form-add-name" name="performanceKPISubject.name"></input>
	    					</td>
	    				</tr>
	    				<tr>
	    					<td width="70px" align="left">所属部门:</td>
	    					<td align="left">	    					
				    			<input id="departmentCosts-form-add-widget-deptid" name="performanceKPISubject.deptid" type="text" style="width: 200px;"/>
	    					</td>
	    				</tr>
	    				<tr>
	    					<td width="70px" align="left">总分:</td>
	    					<td align="left">
				    			<input id="performanceKPISubject-form-add-score" class="easyui-numberbox" data-options="precision:0" name="performanceKPISubject.score" type="text" style="width: 200px"/>
	    					</td>
	    				</tr>
	    				<tr>
	    					<td width="70px" align="left">每分价值:</td>
	    					<td align="left">
	    						<input type="text" style="width:200px" name="performanceKPISubject.svalue"  class="easyui-numberbox" data-options="precision:2" id="performanceKPISubject-form-add-svalue"/>
	    					</td>
	    				</tr>
	    				<tr>
	    					<td width="70px" align="left">排序:</td>
	    					<td align="left"><input type="text" name="performanceKPISubject.seq" class="easyui-validatebox" validType="validInt['int']" required="true" style="width:200px;"/></td>
	    				</tr>
	    				<tr>
	    					<td width="70px" align="left">状态:</td>
	    					<td align="left">	    					
				    			<select name="performanceKPISubject.state" style="width:200px;">
				    				<option value="1">启用</option>
				    				<option value="0">禁用</option>
				    			</select>
	    					</td>
	    				</tr>
	    				<tr>
	    					<td width="70px" align="left">备注:</td>
	    					<td align="left">	    					
				    			<textarea name="performanceKPISubject.remark" class="easyui-validatebox" validType="maxByteLength[255]" cols="0" rows="0" style="width:200px;height:50px;"></textarea>
	    					</td>
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    	</div>
    	<div data-options="region:'south'" style="height: 30px;border: 0px;">
    		<div align="right">
    			<a href="javaScript:void(0);" id="performanceKPISubject-form-save" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="添加考核指标科目">确定</a>
    			<a href="javaScript:void(0);" id="performanceKPISubject-form-saveAgain" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="继续添加考核指标科目">继续添加</a>
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
	    function isPercent(s){
		    if(null==s || ""==s){
			    return false;
		    }
	        s = $.trim(s);
	        var p = /^\d+(\.\d+)?$/;
	        return p.test(s);
	    }
    	$(function(){
    		$.extend($.fn.validatebox.defaults.rules, {
    			validInt:{
    				validator:function(value,param){
    					var isInt='int';
    					if(param[0]==isInt){
    						var reg=/^[1-9][0-9]{0,4}$/;
    						return reg.test(value);
    					} 
    				},
    				message:'请输入1-99999的整数类型数据!'
    			}
    		});
    		
			//所属部门
		  	$("#departmentCosts-form-add-widget-deptid").widget({
		  		width:200,
				name:'t_report_performance_kpisubject',
				col:'deptid',
				singleSelect:true,
				onlyLeafCheck:false,
				required:true
			});
    		
    		$("#performanceKPISubject-form-save").click(function(){

        		var tmp=$("#performanceKPISubject-form-add-code").val()||"";
        		if($.trim(tmp)==""){
            		$.messager.alert("提醒","请选择代码名!");
            		return false;            		
        		}
    			var flag = $("#performanceKPISubject-form-add").form('validate');
				if(flag==false){
					return false;
				}
    			$.messager.confirm("提醒","是否添加部门考核指标科目信息?",function(r){
    				if(r){
    					var ret = performanceKPISubject_AjaxConn($("#performanceKPISubject-form-add").serializeJSON(),'report/performance/addPerformanceKPISubject.do','提交中..');
    	            	var retJson = $.parseJSON(ret);
    	            	if(retJson.flag){
    	            		$.messager.alert("提醒","添加成功!");
        					$("#performance-dialog-operate").dialog('close',true);
        					$("#performance-performanceKPISubject-query-List").trigger("click");
    	            	}else{
        	            	if(retJson.msg){
        						$.messager.alert("提醒","添加失败!"+retJson.msg);
        	            	}else{
        						$.messager.alert("提醒","添加失败!");
        	            	}
        				}
    				}
    			});
    		});

    		$("#performanceKPISubject-form-saveAgain").click(function(){
    			var flag = $("#performanceKPISubject-form-add").form('validate');
				if(flag==false){
					return false;
				}
    			$.messager.confirm("提醒","是否添加部门考核指标科目信息?",function(r){
    				if(r){
    					var ret = performanceKPISubject_AjaxConn($("#performanceKPISubject-form-add").serializeJSON(),'report/performance/addPerformanceKPISubject.do','提交中..');
    	            	var retJson = $.parseJSON(ret);
    	            	if(retJson.flag){
    	            		$.messager.alert("提醒","添加成功!");
        					$("#performance-performanceKPISubject-query-List").trigger("click");

        					$("#performance-dialog-operate").dialog('close',true);
                			
        					refreshLayout('部门考核指标科目【新增】', 'report/performance/showPerformanceKPISubjectAddPage.do');
    	            	}else{
        	            	if(retJson.msg){
        						$.messager.alert("提醒","添加失败!"+retJson.msg);
        	            	}else{
        						$.messager.alert("提醒","添加失败!");
        	            	}
        				}
    				}
    			});
    		});
    		
    		$("#performanceKPISubject-form-add-code").change(function(){
        		var val=$(this).val() ||"";
        		$("#performanceKPISubject-form-add-code-text").html(val);
        		$("#performanceKPISubject-form-add-name").val($(this).find('option:selected').text());
    		});
    	});
    </script>
  </body>
</html>
