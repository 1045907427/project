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
	    				<td width="120px" align="right">业务日期:</td>
	   					<td align="right"><input type="text" id="performanceKPIScore-form-edit-date-businessdate" name="performanceKPIScore.businessdate" class="easyui-validatebox"  readonly="readonly" value="${performanceKPIScore.businessdate }" style="width:150px"/></td>
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
	   					<td align="right"><input type="text" id="performanceKPIScore-form-salesamountindex" class="easyui-numberbox" readonly="readonly" name="performanceKPIScore.salesamountindex" value="${performanceKPIScore.salesamountindex }" data-options="precision:2" style="width:150px;" tabindex="1" /></td>
	   					<td width="120px" align="right">销售额:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" value="${performanceKPIScore.salesamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	    			</tr>
	    			<tr>
	    				<td width="120px" align="right">销售额指标总分:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" value="${performanceKPIScore.salesamountindexscore }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">销售额指标分值:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" value="${performanceKPIScore.salesamountindexvalue }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	    			</tr>    			
	    			<tr>
	   					<td colspan="4">
	   						<hr style="border:1px dashed #374e60;"/>
	   					</td>
	   				</tr>	   				
	    			<tr>
	    				<td width="120px" align="right">毛利额指标:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" value="${performanceKPIScore.mlamountindex }" readonly="readonly" data-options="precision:2" style="width:150px;" tabindex="2"  /></td>
	   					<td width="120px" align="right">毛利额:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" value="${performanceKPIScore.mlamount }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	    			</tr>
	    			<tr>
	    				<td width="120px" align="right">毛利率指标:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" value="${performanceKPIScore.mlrateindex }" readonly="readonly" data-options="precision:2" style="width:145px;" tabindex="3"  />%</td>
	   					<td width="120px" align="right">毛利率:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" value="${performanceKPIScore.mlrate }" readonly="readonly" data-options="precision:2,suffix:'%'" style="width:150px;" /></td>
	    			</tr>   	
	    			<tr>
	    				<td width="120px" align="right">毛利指标总分:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" value="${performanceKPIScore.mlindexscore }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">毛利指标分值:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" value="${performanceKPIScore.mlindexvalue }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	    			</tr>    			
	    			<tr>
	   					<td colspan="4">
	   						<hr style="border:1px dashed #374e60;"/>
	   					</td>
	   				</tr>
	   				<tr>
	    				<td width="120px" align="right">库存周转日数指标:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" value="${performanceKPIScore.kczlrsindex }" readonly="readonly" data-options="precision:2" style="width:150px;" tabindex="4"  /></td>
	   					<td width="120px" align="right">库存周转实绩:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" value="${performanceKPIScore.kczlrs }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	    			</tr>
	   				<tr>
	    				<td width="120px" align="right">库存周转指标总分:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" value="${performanceKPIScore.kczlindexscore }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">库存周转指标分值:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" value="${performanceKPIScore.kczlindexvalue }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	    			</tr>			
	    			<tr>
	   					<td colspan="4">
	   						<hr style="border:1px dashed #374e60;"/>
	   					</td>
	   				</tr>
	   				<tr>
	    				<td width="120px" align="right">费用率指标:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" value="${performanceKPIScore.fyrateindex }" readonly="readonly" data-options="precision:2" style="width:145px;" tabindex="5"  />%</td>
	   					<td width="120px" align="right">费用率实绩:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" value="${performanceKPIScore.fyrate }" readonly="readonly" data-options="precision:2,suffix:'%'" style="width:150px;" /></td>
	    			</tr>
	   				<tr>
	    				<td width="120px" align="right">费用率指标总分:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" value="${performanceKPIScore.fyrateindexscore }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	   					<td width="120px" align="right">费用率指标分值:</td>
	   					<td align="right"><input type="text"  class="easyui-numberbox" value="${performanceKPIScore.fyrateindexvalue }" readonly="readonly" data-options="precision:2" style="width:150px;" /></td>
	    			</tr>
	    		</table>
	    		<input type="hidden" name="performanceKPIScore.id" id="performanceKPIScore-form-edit-id" value="${performanceKPIScore.id }" />
	    	</form>
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
    	$(function(){
    		
			//所属部门
		  	$("#performanceKPIScore-form-edit-widget-deptid").widget({
		  		width:150,
				name:'t_report_performance_kpiscore',
				col:'deptid',
				singleSelect:true,
				required:true,
				cls:'readonly'
			});
			
    	});
    	
    </script>
  </body>
</html>
