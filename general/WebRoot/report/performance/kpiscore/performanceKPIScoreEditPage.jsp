<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>考核指标数据修改页面</title>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center">
    			<form action="" method="post" id="performanceKPIScore-form-edit-score" style="padding: 10px;">
    			<table cellpadding="2" cellspacing="2" border="0">
    				<tr>
	    				<td width="120px" align="right">使用最新指标科目:</td>
	   					<td>
	   						<select id="performanceKPIScore-form-edit-useNewKPISubject" name="useNewKPISubject" style="width:100px">
	   							<option value="1">否</option>
	   							<option value="2">是</option>
	   						</select>
	   					</td>
	   					<td width="120px" align="right" colspan="2">&nbsp;</td>
	    			</tr>
    				<tr>
	    				<td width="120px" align="right">业务日期:</td>
	   					<td align="right"><input type="text" id="performanceKPIScore-form-edit-date-businessdate" name="performanceKPIScore.businessdate" class="easyui-validatebox readonly"  readonly="readonly" value="${performanceKPIScore.businessdate }" style="width:150px"/></td>
	   					<td width="120px" align="right">所属部门:</td>
		    			<td align="right"><input type="text" id="performanceKPIScore-form-edit-widget-deptid" name="performanceKPIScore.deptid" readonly="readonly" class="readonly" value="${performanceKPIScore.deptid }" style="width: 150px;"/></td>
	    			</tr>	    			
	    			<tr>
	   					<td colspan="4">
	   						<hr style="border-color:#374e60"/>
	   					</td>
	   				</tr>
	    			<tr>
	    				<td width="120px" align="right">销售额指标:</td>
	   					<td align="right"><input type="text" id="performanceKPIScore-form-salesamountindex" class="easyui-numberbox" name="performanceKPIScore.salesamountindex" value="${performanceKPIScore.salesamountindex }" data-options="precision:2" style="width:150px;" tabindex="1" /></td>
	   					<td width="120px" align="right">销售额:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox readonly" value="${performanceKPIScore.salesamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	    			</tr>
	    			<tr>
	    				<td width="120px" align="right">销售额指标总分:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox readonly" value="${performanceKPIScore.salesamountindexscore }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">销售额指标分值:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox readonly" value="${performanceKPIScore.salesamountindexvalue }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	    			</tr>    			
	    			<tr>
	   					<td colspan="4">
	   						<hr style="border:1px dashed #374e60;"/>
	   					</td>
	   				</tr>	   				
	    			<tr>
	    				<td width="120px" align="right">毛利额指标:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" name="performanceKPIScore.mlamountindex" value="${performanceKPIScore.mlamountindex }" data-options="precision:2" style="width:150px;" tabindex="2"  /></td>
	   					<td width="120px" align="right">毛利额:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox readonly" value="${performanceKPIScore.mlamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	    			</tr>
	    			<tr>
	    				<td width="120px" align="right">毛利率指标:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" name="performanceKPIScore.mlrateindex" value="${performanceKPIScore.mlrateindex }" data-options="precision:2" style="width:145px;" tabindex="3"  />%</td>
	   					<td width="120px" align="right">毛利率:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox readonly" value="${performanceKPIScore.mlrate }" readonly="readonly" data-options="precision:2,suffix:'%'" style="width:150px;" /></td>
	    			</tr>   	
	    			<tr>
	    				<td width="120px" align="right">毛利指标总分:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox readonly" value="${performanceKPIScore.mlindexscore }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">毛利指标分值:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox readonly" value="${performanceKPIScore.mlindexvalue }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	    			</tr>    			
	    			<tr>
	   					<td colspan="4">
	   						<hr style="border:1px dashed #374e60;"/>
	   					</td>
	   				</tr>
	   				<tr>
	    				<td width="120px" align="right">库存周转日数指标:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" name="performanceKPIScore.kczlrsindex" value="${performanceKPIScore.kczlrsindex }" data-options="precision:2" style="width:150px;" tabindex="4"  /></td>
	   					<td width="120px" align="right">库存周转实绩:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox readonly" value="${performanceKPIScore.kczlrs }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	    			</tr>
	   				<tr>
	    				<td width="120px" align="right">库存周转指标总分:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox readonly" value="${performanceKPIScore.kczlindexscore }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">库存周转指标分值:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox readonly" value="${performanceKPIScore.kczlindexvalue }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	    			</tr>			
	    			<tr>
	   					<td colspan="4">
	   						<hr style="border:1px dashed #374e60;"/>
	   					</td>
	   				</tr>
	   				<tr>
	    				<td width="120px" align="right">费用率指标:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" name="performanceKPIScore.fyrateindex" value="${performanceKPIScore.fyrateindex }" data-options="precision:2" style="width:145px;" tabindex="5"  />%</td>
	   					<td width="120px" align="right">费用率实绩:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox readonly" value="${performanceKPIScore.fyrate }" readonly="readonly" data-options="precision:2,suffix:'%'" style="width:150px;" /></td>
	    			</tr>
	   				<tr>
	    				<td width="120px" align="right">费用率指标总分:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox readonly" value="${performanceKPIScore.fyrateindexscore }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">费用率指标分值:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox readonly" value="${performanceKPIScore.fyrateindexvalue }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	    			</tr>
	    		</table>
	    		<input type="hidden" name="performanceKPIScore.id" id="performanceKPIScore-form-edit-id" value="${performanceKPIScore.id }" />
	    	</form>
    		</div>
    	</div>
    	<div data-options="region:'south'" style="height: 35px;border: 0px;">
    		<div align="right" style="line-height:35px;">
    			<a href="javaScript:void(0);" id="performanceKPIScore-form-edit-saveMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="保存">保存</a>&nbsp;&nbsp;
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

		

    	//修改考核指标数据录入
    	$("#performanceKPIScore-form-edit-saveMenu").click(function(){
    		$.messager.confirm("提醒","是否确认更新部门考核指标数据?",function(r){
				if(r){
					var id=$("#performanceKPIScore-form-edit-id").val()||"";
		       		if(null==id || ""==id){
		        		$.messager.alert("提醒","未能找到相关部门考核指标数据!"); 
		        		return false;          		
		       		}
		       		var formdata=$("#performanceKPIScore-form-edit-score").serializeJSON();
		       		loading('正在努力计算并修改中，请耐心等待..');
		        	try{
			        	$.ajax({
					        type: 'post',
					        cache: false,
					        url: 'report/performance/editPerformanceKPIScore.do',
					        data: formdata,
					        dataType:'json',
					        success:function(data){
					        	loaded();
				        		if(data.flag){
					        		$.messager.alert("提醒","更新成功!");
					        		$("#performance-query-kpiScoreList").trigger('click');
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
		  	$("#performanceKPIScore-form-edit-widget-deptid").widget({
		  		width:150,
				name:'t_report_performance_kpiscore',
				col:'deptid',
				singleSelect:true,
				onlyLeafCheck:false,
				required:true,
				cls:'readonly'
			});
			
    	});
    	
    </script>
  </body>
</html>
