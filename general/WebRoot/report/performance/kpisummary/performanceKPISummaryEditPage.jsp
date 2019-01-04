<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>部门考核指标数据汇总修改页面</title>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center">
    			<form action="" method="post" id="performanceKPISummary-form-edit-subject" style="padding: 10px;">
    			<table cellpadding="2" cellspacing="2" border="0">
    				<tr>
	    				<td width="120px" align="right">业务日期:</td>
	   					<td align="left"><input type="text" id="performanceKPISummary-form-edit-date-businessdate" name="businessdate" class="easyui-validatebox"  readonly="readonly" value="${performanceKPISummary.businessdate }" style="width:150px"/></td>
	   					<td width="120px" align="right">所属部门:</td>
		    			<td align="left"><input type="text" id="performanceKPISummary-form-edit-widget-deptid" name="deptid" readonly="readonly" value="${performanceKPISummary.deptid }" style="width: 150px;"/></td>
	    			</tr>	    			
	    			<tr>
	   					<td colspan="4">
	   						<hr style="border-color:#374e60"/>
	   					</td>
	   				</tr>
	    			<tr>
	    				<td width="120px" align="right">销售额:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.salesamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">含税毛利:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.hsmlamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	    			</tr>
	    			<tr>
	    				<td width="120px" align="right">签呈返还:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.jcfhamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right" colspan="3">&nbsp;</td>
	    			</tr>	
	    			<tr>
	    				<td width="120px" align="right">小计:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.xjamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">小计率:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.xjrate }" readonly="readonly" data-options="precision:2,suffix:'%'" style="width:150px;" /></td>
	    			</tr>    			
	    			<tr>
	   					<td colspan="4">
	   						<hr style="border:1px dashed #374e60;"/>
	   					</td>
	   				</tr>
	   				<tr>
	    				<td width="120px" align="right">费用额:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.fyamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">费用率:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.fyrate }" readonly="readonly" data-options="precision:2,suffix:'%'" style="width:150px;" /></td>
	    			</tr>
	   				<tr>
	    				<td width="120px" align="right">净利额:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.jlamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">净利率:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.jlrate }" readonly="readonly" data-options="precision:2,suffix:'%'" style="width:150px;" /></td>
	    			</tr>
	   				<tr>
	    				<td width="120px" align="right">平均期末库存额:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.pjqmkcamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">平均库存周转天数:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.pjkczzday }" readonly="readonly" data-options="precision:2,suffix:'%'" style="width:150px;" /></td>
	    			</tr>
	   				<tr>
	    				<td width="120px" align="right">平均资金占用额:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.pjzjzyamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">资金利润率:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.zjlrrate }" readonly="readonly" data-options="precision:2,suffix:'%'" style="width:150px;" /></td>
	    			</tr>
	   				<tr>
	    				<td width="120px" align="right">期末代垫费用余额:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.qmddfyyeamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">代垫费占用率:</td>
	   					<td align="left"><input type="text"  class="easyui-numberbox" value="${performanceKPISummary.ddfyzyrate }" readonly="readonly" data-options="precision:2,suffix:'%'" style="width:150px;" /></td>
	    			</tr>
	    		</table>
	    		<input type="hidden" name="id" id="performanceKPISummary-form-edit-id" value="${performanceKPISummary.id }" />
	    	</form>
    		</div>
    	</div>
    	<div data-options="region:'south'" style="height: 35px;border: 0px;">
    		<div align="right" style="line-height:35px;">
    			<a href="javaScript:void(0);" id="performanceKPISummary-form-edit-saveMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="更新考核指标数据">更新考核指标数据</a>&nbsp;&nbsp;
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
   
    	//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			maxLen:{
	  			validator : function(value,param) { 
		            return value.length <= param[0]; 
		        }, 
		        message : '最多可输入{0}个字符!' 
	  		}
		});

		

    	//修改考核指标数据汇总数据录入
    	$("#performanceKPISummary-form-edit-saveMenu").click(function(){
    		$.messager.confirm("提醒","是否确认更新部门考核指标数据?",function(r){
				if(r){
					var id=$("#performanceKPISummary-form-edit-id").val()||"";
		       		if(null==id || ""==id){
		        		$.messager.alert("提醒","未能找到相关部门考核指标数据!"); 
		        		return false;          		
		       		}
		       				       		
		       		loading('正在努力计算并修改中，请耐心等待..');
		        	try{
			        	$.ajax({
					        type: 'post',
					        cache: false,
					        url: 'report/performance/editPerformanceKPISummary.do',
					        data: {id:id},
					        dataType:'json',
					        success:function(data){
					        	loaded();
				        		if(data.flag){
					        		$.messager.alert("提醒","更新成功!");
					        		$("#performance-query-kpiSummaryList").trigger('click');
					        		$('#performance-dialog-edit-operate').dialog('close');
				            	}
				            	else{
					            	if(retJson.msg){
					        			$.messager.alert("提醒","更新失败!"+data.msg);
					            	}else{
					        			$.messager.alert("提醒","更新失败!");
					            	}
				            	}
					        }
					    });  
		        	}catch(ex){
			        	loaded();
		        	}      
				}
			});
    	});
    	$(function(){
    		
			//所属部门
		  	$("#performanceKPISummary-form-edit-widget-deptid").widget({
		  		width:150,
				name:'t_report_performance_kpisummary',
				col:'deptid',
				singleSelect:true,
				required:true
			});
			
    	});
    	
    </script>
  </body>
</html>
