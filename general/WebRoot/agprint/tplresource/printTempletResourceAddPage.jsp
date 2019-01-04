<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>模板资源添加</title>
  </head> 
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'center',border:false">
	    <form action="agprint/tplresource/addPrintTempletResource.do" method="post" id="tplresource-form-addPrintTempletResource">
	    	<table cellpadding="3" cellspacing="3" border="0">
		    	<tr>
   					<td width="90px" align="right">模板代码:</td>
   					<td align="left">
   						<input type="text" name="printTempletResource.code" value="${param.thecode }" id="tplresource-printTempletResource-form-code" style="width:200px"/>
    					<div id="tplresource-printTempletResource-form-code-text" style="line-height:25px;" ></div>
   					</td>
 				</tr>
   				<tr>
   					<td width="90px" align="right">资源名称:</td>
   					<td align="left"><input type="text" name="printTempletResource.name" class="easyui-validatebox" required="true" validType="maxByteLength[100]" style="width:200px;"/></td>
   				</tr>
   				<tr class="uploadTrPanel" style="display:none">
   					<td width="90px" align="right">模板文件(jasper):</td>
   					<td align="left">
		    			<div style="float:left">
							<div style="float:left" id="tplresource-templetfile-operdiv"><a href="javascript:void(0);" id="tplresource-templetfile-upload-addclick" class="easyui-linkbutton" data-options="plain:false,iconCls:'icon-extend-uploadfile'">添加文件</a></div>
							<div style="float:left;display:none;line-height:30px;" id="tplresource-templetfile-upload-result"></div>
			    		</div>
						<div style="clear:both"></div>
						<input type="hidden" id="tplresource-templetfile-hidden"  name="printTempletResource.templetfileid" />
   					</td>
   				</tr>
   				<tr class="uploadTrPanel" style="display:none">
   					<td width="90px" align="right">模板源文件(jrxml):</td>
   					<td align="left">
		    			<div style="float:left">
							<div style="float:left" id="tplresource-sourcefile-operdiv"><a href="javascript:void(0);" id="tplresource-sourcefile-upload-addclick" class="easyui-linkbutton" data-options="plain:false,iconCls:'icon-extend-uploadfile'">添加文件</a></div>
							<div style="float:left;display:none;line-height:30px;" id="tplresource-sourcefile-upload-result"></div>
			    		</div>
						<div style="clear:both"></div>
						<input type="hidden" id="tplresource-sourcefile-hidden"  name="printTempletResource.sourcefileid" />
   					</td>
   				</tr>
				<tr>
					<td align="right">纸张:</td>
					<td align="left">
						<div id="tplresource-form-papersizeid-widget-myuidiv" style="width:215px;float:left"></div>
						<div style="float:left">
							<a href="javaScript:void(0);" id="tplresource-form-add-papersizeid" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>
						</div>
						<div style="clear:both;display: none">
							注意：系统默认纸张大小(21.5*14)
						</div>
						<input type="hidden" id="tplresource-form-hidden-papersizeid"  name='printTempletResource.papersizeid' />
					</td>
				</tr>
		    	<tr>
		    		<td align="right">状态:</td>
	    			<td align="left">
		    			<select  style="width:200px;" disabled="disabled">
		    				<option value="1">有效</option>
		    			</select>
		    			<input type="hidden" name="printTempletResource.state" value="1" />
		    			<div style="line-height:25px">
		    				状态会影响新模板新增，不影响旧模板使用。
		    			</div>
	    			</td>
		    	</tr>
		    	<tr>
		    		<td align="right">备注:</td>
	    			<td align="left">
		    			<textarea rows="0" cols="0" name="printTempletResource.remark" style="width:200px;height:80px;" class="easyui-validatebox" validType="maxByteLength[500]"></textarea>
	    			</td>
		    	</tr>
		    </table>
	    </form>
	    <input type="hidden" id="tplresource-printTempletResource-form-afterSaveOperType"/>
	    <input type="hidden" id="tplresource-printTempletResource-form-afterSaveOper"/>
      </div>
      <div data-options="region:'south',border:false">
          <div class="buttonDetailBG" style="height:30px;text-align:right;">
              <input type="button" name="savegoon" id="tplresource-save-addPrintTempletResource" value="确定"/>
          </div>
      </div>
  </div>
    <script type="text/javascript">
    	$(function(){
    		$("#tplresource-printTempletResource-form-code").widget({
	   			referwid:'RL_PRINT_TEMPLET_SUBJECT',
	   			singleSelect:true,
	   			width:'200',
	   			required:true,
	   			<c:if test="${param.readonlythecode=='true' and !empty( param.thecode )}">
	   				readonly:true,
				</c:if>
	   			onSelect:function(data){
	   				if(data!=null){
		   				$("#tplresource-printTempletResource-form-code-text").html(data.code);
	        			$("tr.uploadTrPanel").show();
	   				}else{
	        			$("tr.uploadTrPanel").hide();
	   				}
	   			},
	   			onClear:function(){
        			$("tr.uploadTrPanel").hide();
	   				$("#tplresource-printTempletResource-form-code-text").html("");
	   			}
	   		});

    		<c:if test="${!empty( param.thecode )}">
				setTimeout(function(){
					$("#tplresource-printTempletResource-form-code").widget('setValue','${param.thecode}');
				},100);

				$("#tplresource-printTempletResource-form-code").blur(function(event){
				    $("#tplresource-printTempletResource-form-code").widget('setValue','${param.thecode}');
                    event.preventDefault();
				});
    		</c:if>
    		$("#tplresource-form-addPrintTempletResource").form({
    			onSubmit: function(){
    				var flag = $(this).form('validate');
    				if(flag==false){
    					return false;
    				}
    				var tmp=$("#tplresource-templetfile-hidden").val()|| "";
    				if(tmp==""){
						$.messager.alert("提醒","请上传模板文件(jasper)!");
						return;
    				}
    				tmp=$("#tplresource-sourcefile-hidden").val()|| "";
    				if(tmp==""){
						$.messager.alert("提醒","请上传模板源文件(jxml)!");
						return;
    				}
    			},
    			success:function(data){
    				//$.parseJSON()解析JSON字符串
    				var json = $.parseJSON(data);

    				if(json.flag==true){
    					$.messager.alert("提醒","添加成功!");
						var afterSaveOperType=$("#tplresource-printTempletResource-form-afterSaveOperType").val() || "";
						var afterSaveOper=$("#tplresource-printTempletResource-form-afterSaveOper").val() || "";
    					$("#tplresource-dialog-printTempletResourceOper-content").dialog('close',true);

    					try{
    						$("#tplresource-table-printTempletResourceList").datagrid('reload');
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
    				}
    				else{
    					if(json.msg!=null){
    						$.messager.alert("提醒","添加失败,"+json.msg);
    					}else{
    						$.messager.alert("提醒","添加失败!");
    					}
    				}
    			}
    		});
    		$("#tplresource-save-addPrintTempletResource").click(function(){
    			$.messager.confirm("提醒","是否添加打印模板资源信息?",function(r){
    				if(r){
    					$("#tplresource-form-addPrintTempletResource").submit();
    				}
    			});
    		});
    		

    		$("#tplresource-templetfile-upload-addclick").webuploader({
				title: '模板文件(jasper)',
                filetype:'Files',
                allowType:"jasper",
                mimeTypes:'*',
                disableGlobalDnd:true,
                width: 700,
                height: 400,
                url:'common/uploadPrintTemplet.do',
                description:'',
                close:true,
                fileNumLimit:1,
                description:'请上传后缀名为 .jasper 文件',
                onBeforeLoad:function(){
                	var code=$("#tplresource-form-addPrintTempletResource").val() || "";
			        if($.trim(code)!=""){
			        	$.webuploader.setFormData({'prefix':code});
			        }
                },
				onComplete: function(data){
				   	if(data &&data.length>0 && data[0].id){
					   	$("#tplresource-templetfile-hidden").val(data[0].id);
					   	$("#tplresource-templetfile-operdiv").hide();
					   	$("#tplresource-templetfile-upload-result").show();			
					   	var htmlsb=new Array();
					   	htmlsb.push(data[0].oldFileName);
					   	htmlsb.push(" ");
					   	htmlsb.push("(<a href=\"javascript:void(0);\" onclick=\"javascript:openTempletUpload();\">重新上传</a>)");			   	
					   	$("#tplresource-templetfile-upload-result").html(htmlsb.join(""));
				   	}
				}
			});
    		  
    		$("#tplresource-sourcefile-upload-addclick").webuploader({
				title: '模板源文件(jrxml)上传',
                filetype:'Files',
                allowType:"jrxml",
                //mimeTypes:'text/*',
                disableGlobalDnd:true,
                width: 700,
                height: 400,
                url:'common/uploadPrintTemplet.do',
                description:'',
                close:true,
                fileNumLimit:1,
                description:'请上传后缀名为 .jrxml 文件',
                onBeforeLoad:function(){
			        var code=$("#tplresource-form-addPrintTempletResource").val() || "";
			        if($.trim(code)!=""){
	                	$.webuploader.setFormData({'prefix':code});
			        }
                },
				onComplete: function(data){
				   	if(data &&data.length>0 && data[0].id){
					   	$("#tplresource-sourcefile-hidden").val(data[0].id);
					   	$("#tplresource-sourcefile-operdiv").hide();
					   	$("#tplresource-sourcefile-upload-result").show();			
					   	var htmlsb=new Array();
					   	htmlsb.push(data[0].oldFileName);
					   	htmlsb.push(" ");
					   	htmlsb.push("(<a href=\"javascript:void(0);\" onclick=\"javascript:openSourceUpload();\">重新上传</a>)");			   	
					   	$("#tplresource-sourcefile-upload-result").html(htmlsb.join(""));
				   	}
				}
			});
			showTempletResourcePaperSizeWidget();
			addTempletResourceNewPaperSize();
    	});

    	function openTempletUpload(){
    		$("#tplresource-templetfile-operdiv").show();
    		$("#tplresource-templetfile-upload-result").html("");
    		$("#tplresource-templetfile-upload-result").hide();
    	}
    	function openSourceUpload(){
    		$("#tplresource-sourcefile-operdiv").show();
    		$("#tplresource-sourcefile-upload-result").html("");
    		$("#tplresource-sourcefile-upload-result").hide();   	
    	}

		function addTempletResourceNewPaperSize(){
			$("#tplresource-form-add-papersizeid").click(function(){
				var onLoadFunc=function(){
					$("#tplpapersize-PrintPaperSize-form-afterSaveOperType").val("callBackFunc");
					$("#tplpapersize-PrintPaperSize-form-afterSaveOper").val("addPaperSizeCallBack");
				};
				printPaperSizeOpenDialog('打印纸张【新增】',
						'agprint/tplpapersize/showPrintPaperSizeAddPage.do',
						onLoadFunc);
			});
		}

		function addTempletResourcePaperSizeCallBack(){
			showTempletResourcePaperSizeWidget();
		}
		function showTempletResourcePaperSizeWidget(){
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
					if(data!=null){
						$("#tplresource-form-hidden-papersizeid").val(data.id);
					}else{
						$("#tplresource-form-hidden-papersizeid").val("");
					}
				},
				onClear:function(){
					$("#tplresource-form-hidden-papersizeid").val("");
				}
			});
		}
    </script>
  </body>
</html>
