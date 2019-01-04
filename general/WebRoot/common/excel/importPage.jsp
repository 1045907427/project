<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Excel导入</title>
</head>
<body>
	<style type="text/css">
    	.container{margin-top:5px;}
    	#dropArea{width:300px;height:70px;line-height:70px;border:2px dashed #dfdfdf;text-align:center;color:#aaaaaa;}
    	
    </style>
	<div style="width:350px;height:250px;overflow:hidden;padding:5px;">
		<div align="center" >
		<input type="hidden" id="common-filestype" />
		选择需导入的Excel文件(.xls/.xlsx)<c:if test="${empty(param.hideExportTip) or param.hideExportTip!='true'}">(导入模板可通过导出功能获取)</c:if><!-- ,你可以拖动文件到下面区域，也可以选择一个文件上传(IE浏览器不支持拖拽上传) -->
		</div>
		<div class="container" style="height:auto;">
			<div id="common-div-importparam" style="margin-left: 20px;color: red;"></div>
		</div>
		<div align="center" >
			<div align="center" >
				<form action="common/importExcel.do" id="common_form_importExcel" style="position:relative;overflow:hidden;height:60px;" method="post" enctype="multipart/form-data">
					<table cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td>
								<c:choose>
									<c:when test="${version == '2'}">
										<input type="radio" name="versiontype" value="1" checked="checked"/>简化版
					    				<input type="radio" name="versiontype" value="2" />合同版
									</c:when>
									<c:when test="${version == '4'}">
										<input type="radio" name="filestype" value="1" checked="checked"/>Excel文件
					    				<input type="radio" name="filestype" value="2" />三和txt导入
									</c:when>
									<c:when test="${version == '3'}">
										<input type="radio" name="filestype" value="1" checked="checked"/>Excel文件
					    				<input type="radio" name="filestype" value="2" />瑞家txt导入
									</c:when>
									<c:when test="${version == '1'}">
										<input type="hidden" name="filestype" value="1"/>
									</c:when>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td>
                                <input type="hidden" name="version" value="${version }"/>
								<input type="file" name="excelFile" style="width:0px;right:0;position:absolute;opacity:0;z-index:2;filter:alpha(opacity=0);" />
							</td>
						</tr>
						<tr>
							<td>
								<a id="common-button-uploadfile" class="easyui-linkbutton" href="javascript:;">选择一个文件</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var xhr=false;
		if(window.ActiveXObject){
		    try{xhr = new ActiveXObject("Msxml2.XMLHTTP.4.0");}
		    catch(e1){
		      try{xhr = new ActiveXObject("Msxml2.XMLHTTP");}
		      catch(e2){{xhr = new ActiveXObject("Msxml2.XMLHTTP");}}
		    }
		}
		else if(window.XMLHttpRequest){
		/*XMLHttpRequest放最后来创建，这样在IE7，IE8中双击运行或者拖拽进入浏览器中浏览就
		不会出现access is denied 拒绝访问错误了，因为使用的是acx来创建ajax对象*/
		   xhr= new XMLHttpRequest();
		}
		$(function(){
			$("input[name=excelFile]").change(function(){
				$.messager.confirm("提醒","确定导入该文件？", function(r){
					if(r){
						$("#common_form_importExcel").submit();
					}else{
                        $("#common_form_importExcel").form("reset");
                    }
				});
			});
			$("#common-button-uploadfile").click(function(){
                $("input[name=excelFile]").trigger("click");
			});
			$("#common-filestype").val($('input[name=filestype][checked]').val());
			$('input[name=filestype]').click(function(){
				$("#common-filestype").val($(this).val());
			});
		});
	</script>
</body>
</html>