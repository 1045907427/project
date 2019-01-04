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
			    <form id="tplpapersize-form-addOrderSeq">
					<table cellpadding="3" cellspacing="3" border="0">
	    				<tr>	    			
	    					<td width="90px" align="right">名称:</td>
	    					<td align="left">
	    						<input type="text" value="${printPaperSize.name }" readonly="readonly" style="width:300px;"/>
	    					</td>
	    				</tr>
						<tr>
							<td align="right">宽:</td>
							<td align="left">
								<div style="float:left;width:150px;">
									<input type="text" name="printPaperSize.width" value="${printPaperSize.width}" readonly="readonly" style="width:100px;"/>厘米
								</div>
								<div style="float:left;width:150px;">
									高:<input type="text" name="printPaperSize.height" value="${printPaperSize.height}" readonly="readonly" style="width:100px;"/>厘米
								</div>
							</td>
						</tr>
						<tr>
							<td align="right">排序:</td>
							<td align="left">
								<input type="text" name="printPaperSize.seq" value="${printPaperSize.seq }"  class="easyui-validatebox" validType="validInt['int']" required="true" style="width:300px;" readonly="readonly"/>
							</td>
						</tr>
	    				<tr>
	    					<td width="90px" align="right">状态</td>
	    					<td align="left">
	    						<select  disabled="disabled" style="width:300px;">
				    				<option value="1" <c:if test="${printPaperSize.state=='1' }">selected="selected"</c:if> >有效</option>
				    				<option value="0" <c:if test="${printPaperSize.state=='0' }">selected="selected"</c:if> >无效</option>
				    			</select>
	    					</td>
	    				</tr>
				    	<tr>
				    		<td align="right">备注:</td>
			    			<td align="left">
				    			<textarea rows="0" cols="0" style="width:300px;height:80px;" disabled="disabled" >${printPaperSize.remark}</textarea>
			    			</td>
				    	</tr>
    				</table>
    				<input type="hidden" value="${printPaperSize.id }" />
			    </form>
	    	</div>
      </div>
  </div>
  <script type="text/javascript">
	  $(function() {

		  $("#tplresource-PrintPaperSize-form-width").numberbox({
			  precision: 2,
			  required: true,
			  readonly:true
		  });
		  $("#tplresource-PrintPaperSize-form-height").numberbox({
			  precision: 2,
			  required: true,
			  readonly:true
		  });
	  });
  </script>
  </body>
</html>
