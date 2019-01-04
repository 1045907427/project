<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代码修改</title>
  </head>
  
  <body>
<div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'center',border:false">
       		<div align="center" style="padding: 10px;">
			    <form id="tplorderseq-form-addOrderSeq">
					<table cellpadding="3" cellspacing="3" border="0">
	    				<tr>	    			
		   					<td width="90px" align="right">策略编号:</td>
		   					<td align="left">
		   						<input type="text" value="${printOrderSeq.viewid }" readonly="readonly" style="width:300px"/>
		   					</td>
		 				</tr>
	    				<tr>	    			
	    					<td width="90px" align="right">模板代码:</td>
	    					<td align="left">
		   						<input type="text" id="tplorderseq-PrintTempletOrderseq-form-code" value="${printOrderSeq.code }" style="width:300px" readonly="readonly" class="easyui-validatebox" required="true"/>
		    					<div id="tplorderseq-PrintTempletOrderseq-form-code-text" style="line-height:25px;" >${printOrderSeq.code }</div>
	    					</td>
	    				</tr>
	    				<tr>	    			
	    					<td width="90px" align="right">策略名称:</td>
	    					<td align="left">
	    						<input type="text" value="${printOrderSeq.name }" readonly="readonly" style="width:300px;"/>
	    					</td>
	    				</tr>
	    				<tr>	    			
	    					<td width="90px" align="right">排序策略:</td>
	    					<td align="left">
	    						<input type="text" value="${printOrderSeq.orderseq }" readonly="readonly" style="width:300px;"/>
	    					</td>
	    				</tr>
	    				<tr>
	    					<td align="right">注意：</td>
	    					<td>多个排序使用,分隔;asc(从小到大);desc(从大到小)</td>
	    				</tr>
	    				<tr>
	    					<td width="90px" align="right">状态</td>
	    					<td align="left">
	    						<select  disabled="disabled" style="width:300px;">
				    				<option value="1" <c:if test="${printOrderSeq.state=='1' }">selected="selected"</c:if> >有效</option>
				    				<option value="0" <c:if test="${printOrderSeq.state=='0' }">selected="selected"</c:if> >无效</option>
				    			</select>
	    					</td>
	    				</tr>
				    	<tr>
				    		<td align="right">备注:</td>
			    			<td align="left">
				    			<textarea rows="0" cols="0" style="width:300px;height:80px;" disabled="disabled" >${printOrderSeq.remark}</textarea>
			    			</td>
				    	</tr>
    				</table>
    				<input type="hidden" value="${printOrderSeq.id }" />
			    </form>
	    	</div>
      </div>
  </div>
  <script type="text/javascript">
  	$(document).ready(function(){
		$("#tplorderseq-PrintTempletOrderseq-form-code").widget({
   			referwid:'RL_PRINT_TEMPLET_SUBJECT',
   			singleSelect:true,
   			width:'300'
   		});
   		$("#tplorderseq-form-add-linkdatatable").widget({
   			referwid:'RL_T_SYS_TABLEINFO',
   			singleSelect:true,
   			width:'300',
   			view:true
   		});
		
	});
  </script>
  </body>
</html>
