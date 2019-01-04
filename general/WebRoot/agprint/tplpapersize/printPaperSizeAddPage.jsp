<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>打印纸张大小</title>
  </head> 
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'center',border:false">
       		<div align="center" style="padding: 10px;">
			    <form action="agprint/tplpapersize/addPrintPaperSize.do" method="post" id="tplpapersize-form-addPrintPaperSize">
					<table cellpadding="3" cellspacing="3" border="0">
	    				<tr>	    			
	    					<td align="right">名称:</td>
	    					<td align="left">
	    						<input type="text" name="printPaperSize.name" id="tplresource-PrintPaperSize-form-name" class="easyui-validatebox" required="true" validType="maxByteLength[120]" style="width:300px;" autocomplete="off" />
	    					</td>
	    				</tr>
						<tr>
							<td align="right">宽:</td>
							<td align="left">
								<div style="float:left;width:150px;">
									<input type="text" name="printPaperSize.width" id="tplresource-PrintPaperSize-form-width" style="width:100px;" value="0.00"/>厘米
								</div>
								<div style="float:left;width:150px;">
									高:<input type="text" name="printPaperSize.height" id="tplresource-PrintPaperSize-form-height" style="width:100px;" value="0.00"/>厘米
								</div>
							</td>
						</tr>
						<tr>
							<td align="right">排序:</td>
							<td align="left">
								<input type="text" name="printPaperSize.seq" class="easyui-validatebox" validType="validSeqInt['int']" required="true" style="width:300px;"/>
							</td>
						</tr>
	    				<tr>	    			
	    					<td align="right">状态:</td>
	    					<td align="left">
				    			<select  style="width:300px;" disabled="disabled">
				    				<option value="1">有效</option>
				    			</select>
		    					<input type="hidden" name="printPaperSize.state" value="1" />
	    					</td>
	    				</tr>
				    	<tr>
				    		<td align="right">备注:</td>
			    			<td align="left">
				    			<textarea rows="0" cols="0" name="printPaperSize.remark" style="width:300px;height:80px;" class="easyui-validatebox" validType="maxByteLength[240]"></textarea>
			    			</td>
				    	</tr>
    				</table>
			    </form>
			    <input type="hidden" id="tplpapersize-PrintPaperSize-form-afterSaveOperType"/>
			    <input type="hidden" id="tplpapersize-PrintPaperSize-form-afterSaveOper"/>
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
    					$.messager.alert("提醒","添加成功!");
    					var afterSaveOperType=$("#tplpapersize-PrintPaperSize-form-afterSaveOperType").val() || "";
						var afterSaveOper=$("#tplpapersize-PrintPaperSize-form-afterSaveOper").val() || "";
    					$("#tplpapersize-dialog-printPaperSizeOper-content").dialog('close',true);

    					try{
    						$("#tplpapersize-table-printPaperSizeList").datagrid('reload');
    					}catch(e){
    						
    					}
    					try{

    						if(afterSaveOper == ""){
    							return false;
    						}
    						if(afterSaveOperType == "callBackFunc"){
    							window[afterSaveOper]();
    						}
    					}catch(e){
    					}
    				}else if(null!=json.msg){
    					$.messager.alert("提醒","添加失败,"+json.msg);    					
    				}else{
    					$.messager.alert("提醒","添加失败!");
    				}
    			}
    		});
    		$("#tplpapersize-save-addPrintPaperSize").click(function(){
    			$.messager.confirm("提醒","是否添加打印纸张大小?",function(r){
    				if(r){
    					$("#tplpapersize-form-addPrintPaperSize").submit();
    				}
    			});
    		});

    	});
    </script>
  </body>
</html>
