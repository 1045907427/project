<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
			    <form action="agprint/tplpapersize/editPrintPaperSize.do" method="post" id="tplpapersize-form-addPrintPaperSize">
					<table cellpadding="3" cellspacing="3" border="0">
	    				<tr>	    			
	    					<td width="90px" align="right">名称:</td>
	    					<td align="left">
	    						<input type="text" name="printPaperSize.name" value="${printPaperSize.name }" class="easyui-validatebox" required="true" validType="maxByteLength[120]" style="width:300px;" autocomplete="off" />
	    					</td>
	    				</tr>
						<tr>
							<td align="right">宽:</td>
							<td align="left">
								<div style="float:left;width:150px;">
									<input type="text" name="printPaperSize.width" value="${printPaperSize.width}" id="tplresource-PrintPaperSize-form-width" style="width:100px;"/>厘米
								</div>
								<div style="float:left;width:150px;">
									高:<input type="text" name="printPaperSize.height" value="${printPaperSize.height}" id="tplresource-PrintPaperSize-form-height" style="width:100px;"/>厘米
								</div>
							</td>
						</tr>
						<tr>
							<td align="right">排序:</td>
							<td align="left">
								<input type="text" name="printPaperSize.seq" value="${printPaperSize.seq }"  class="easyui-validatebox" validType="validSeqInt['int']" required="true" style="width:300px;"/>
							</td>
						</tr>
	    				<tr>
	    					<td width="90px" align="right">状态</td>
	    					<td align="left">
	    						<select style="width:300px;" disabled="disabled">
				    				<option value="1" <c:if test="${printPaperSize.state=='1' }">selected="selected"</c:if> >有效</option>
				    				<option value="0" <c:if test="${printPaperSize.state=='0' }">selected="selected"</c:if> >无效</option>
				    			</select>
	    					</td>
	    				</tr>
				    	<tr>
				    		<td align="right">备注:</td>
			    			<td align="left">
				    			<textarea rows="0" cols="0" name="printPaperSize.remark" style="width:300px;height:80px;" class="easyui-validatebox" validType="maxByteLength[240]">${printPaperSize.remark}</textarea>
			    			</td>
				    	</tr>
    				</table>
    				<input type="hidden" name="printPaperSize.id" value="${printPaperSize.id }" />
			    </form>
	    	</div>
      </div>
      <div data-options="region:'south',border:false">
          <div class="buttonDetailBG" style="height:30px;text-align:right;">
              <input type="button" name="savegoon" id="tplpapersize-save-addPrintPaperSize" value="确定"/>
          </div>
      </div>
  </div>
    <script type="text/javascript">
    	$(function(){
			$("#tplresource-PrintPaperSize-form-width").numberbox({
				precision:2,
				required:true
			});
			$("#tplresource-PrintPaperSize-form-height").numberbox({
				precision:2,
				required:true
			});
    		$("#tplpapersize-form-addPrintPaperSize").form({
    			onSubmit: function(){
    				var flag = $(this).form('validate');
    				if(flag==false){
    					return false;
    				}
					var width=$("#tplresource-PrintPaperSize-form-width").numberbox('getValue');
					if(width<=0){
						$.messager.alert("提醒","请认真填写长度!");
						return false;
					}
					var height=$("#tplresource-PrintPaperSize-form-height").numberbox('getValue');
					if(width<=0){
						$.messager.alert("提醒","请认真填写宽度!");
						return false;
					}
    			},
    			success:function(data){
    				//$.parseJSON()解析JSON字符串 
    				var json = $.parseJSON(data);
    				if(json.flag==true){
    					$.messager.alert("提醒","修改纸张大小成功!");
    					$("#tplpapersize-dialog-printPaperSizeOper-content").dialog('close',true);
    					$("#tplpapersize-table-printPaperSizeList").datagrid('reload');
    				}
    				else{
    					$.messager.alert("提醒","修改纸张大小!");
    				}
    			}
    		});
    		$("#tplpapersize-save-addPrintPaperSize").click(function(){
    			$.messager.confirm("提醒","是否修改纸张大小信息?",function(r){
    				if(r){
    					$("#tplpapersize-form-addPrintPaperSize").submit();
    				}
    			});
    		});

    	});
    </script>
  </body>
</html>
  