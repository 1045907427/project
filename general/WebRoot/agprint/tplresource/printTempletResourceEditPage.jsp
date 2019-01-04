<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>打印模板资源修改</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'center',border:false">
		  <div align="center">
	    	<form action="agprint/tplresource/editPrintTempletResource.do" method="post" id="tplresource-form-editPrintTempletResource">
	    	<table cellpadding="3" cellspacing="3" border="0">
		    	<tr>
		    		<td width="100px" align="right">模板代码:</td>
		    		<td align="left">
						<div style="line-height:25px;" >${printTempletResource.code }</div>
						<div style="line-height:25px;" >${codename }</div>
						<input type="hidden" id="tplresource-printTempletResource-form-code" name="printTempletResource.code" value="${printTempletResource.code }" />
					</td>
		    	</tr>
		    	<tr>
		    		<td align="right">资源名称:</td>
		    		<td align="left">
		    			<input type="text" name="printTempletResource.name" value="${printTempletResource.name }" class="easyui-validatebox" required="true" style="width:200px;"/>
		    		</td>
		    	</tr>
   				<tr>
   					<td align="right">模板文件(jasper):</td>
   					<td align="left">
   						${printTempletResource.templetfile }
   					</td>
   				</tr>
   				<tr>
   					<td align="right">模板源文件(jrxml):</td>
   					<td align="left">
   						${printTempletResource.sourcefile }
   					</td>
   				</tr>
				<tr>
					<td align="right">纸张:</td>
					<td align="left">
						<div id="tplresource-form-papersizeid-widget-myuidiv" style="width:215px;float:left"></div>
						<div style="float:left">
							<a href="javaScript:void(0);" id="tplresource-form-add-papersizeid" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>
						</div>
						<div style="clear:both">
							<div style="line-height:25px;" id="tplresource-form-papersizeid-result">${papersizename }</div>
							<div style="line-height:25px;display: none">注意：系统默认纸张大小(21.5*14)</div>
						</div>
						<input type="hidden" id="tplresource-form-hidden-papersizeid"  name='printTempletResource.papersizeid' value="${printTempletResource.papersizeid}" />
					</td>
				</tr>
		    	<tr>
		    		<td align="right">状态:</td>
		    		<td align="left">
		    			<select disabled="disabled" style="width:200px;">
		    				<option value="1" <c:if test="${printTempletResource.state=='1' }">selected="selected"</c:if> >有效</option>
		    				<option value="0" <c:if test="${printTempletResource.state=='0' }">selected="selected"</c:if> >无效</option>
		    			</select>
		    		</td>
		    	</tr>
	    		<tr>
		    		<td align="right">备注:</td>
	    			<td align="left">
		    			<textarea rows="0" cols="0" name="printTempletResource.remark" value="${printTempletResource.remark }" style="width:200px;height:80px;" class="easyui-validatebox" validType="maxByteLength[500]">${printTempletResource.remark }</textarea>
	    			</td>
		    	</tr>
			</table>
			<input type="hidden" name="printTempletResource.id" value="${printTempletResource.id }" />
      	</form>
		  </div>
	  </div>
      <div data-options="region:'south',border:false">
          <div class="buttonDetailBG" style="height:30px;text-align:right;">
              <input type="button" name="savegoon" id="tplresource-save-editPrintTempletResource" value="确定"/>
          </div>
      </div>
  </div>
    <script type="text/javascript">
    	$(function(){
    		$("#tplresource-form-editPrintTempletResource").form({
    			onSubmit: function(){
    				var flag = $(this).form('validate');
    				if(flag==false){
    					return false;
    				}
    			},
    			success: function(data){
    				var json=$.parseJSON(data);
    				if(json.flag == true){
    					$.messager.alert("提醒","修改成功！");
    					$("#tplresource-dialog-printTempletResourceOper-content").dialog('close',true);
    					$("#tplresource-table-printTempletResourceList").datagrid('reload');
    				}else{
    					if(json.msg!=null){
    						$.messager.alert("提醒","修改失败,"+json.msg);    						
    					}else{
    						$.messager.alert("提醒","修改失败！");
    					}
    				}
    			}
    		});
    		$("#tplresource-save-editPrintTempletResource").click(function(){
    			$.messager.confirm("提醒","是否修改打印模板资源信息?", function(r){
    				if(r){
    					$("#tplresource-form-editPrintTempletResource").submit();
    				}
    			});
    		});
			showTempletResourcePaperSizeWidget('${printTempletResource.papersizeid}');
			addTempletResourceNewPaperSize();
    	});

		function addTempletResourceNewPaperSize(){
			$("#tplresource-form-add-papersizeid").click(function(){
				var onLoadFunc=function(){
					$("#tplpapersize-PrintPaperSize-form-afterSaveOperType").val("callBackFunc");
					$("#tplpapersize-PrintPaperSize-form-afterSaveOper").val("addTempletResourcePaperSizeCallBack");
				};
				printPaperSizeOpenDialog('打印纸张【新增】',
						'agprint/tplpapersize/showPrintPaperSizeAddPage.do',
						onLoadFunc);
			});
		}

		function addTempletResourcePaperSizeCallBack(){
			showTempletResourcePaperSizeWidget();
		}
		function showTempletResourcePaperSizeWidget(initValue){
			if(initValue==null){
				initValue='';
			}
			initValue= $.trim(initValue);
			try{
				$("#tplresource-form-papersizeid-widget").widget('clear');
			}catch(e){

			}
			$("#tplresource-form-papersizeid-widget-myuidiv").empty();

			var inputStr="<input type='text' id='tplresource-form-papersizeid-widget' class='easyui-validatebox' required='true' style='width:200px;'/>";
			$(inputStr).appendTo("#tplresource-form-papersizeid-widget-myuidiv");
			$("#tplresource-form-papersizeid-widget").widget({
				referwid:'RL_PRINT_TEMPLET_PAPERSIZE',
				singleSelect:true,
				width:'200',
				//required:true,
				onSelect:function(data){
					$("#tplresource-form-papersizeid-result").html("");
					if(data!=null){
						$("#tplresource-form-hidden-papersizeid").val(data.id);
						if(data.state!='1'){
							var htmlsb=new Array();
							htmlsb.push("注意:");
							if(data.name!=null){
								htmlsb.push(data.name);
							}
							htmlsb.push("(未启用)");
							$("#tplresource-form-papersizeid-result").html(htmlsb.join(''));
						}
					}else{
						$("#tplresource-form-hidden-papersizeid").val("");
					}
				},
				onClear:function(){
					$("#tplresource-form-hidden-papersizeid").val("");
				}
			});
			if(initValue!=''){
				$("#tplresource-form-papersizeid-widget").widget('setValue',initValue);
			}
		}
    </script>
  </body>
</html>
