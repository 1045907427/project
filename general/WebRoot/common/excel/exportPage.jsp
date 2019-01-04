<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>Excel导出</title>
		<!--version-简单导出弹出窗和多选导出弹出窗限制  -->
	</head>
<body>
	<form action="common/exportExcel.do" method="post" id="common-form-exportPage">
		<div style="width:350px;height:150px;overflow:hidden;margin: 5px;">
			<label style="width:80px">导出文件名：</label>
			<input type="text" class="easyui-validatebox" id="common-name-exportPage" required="required" name="excelTitle" style="width: 200px;"/>
			<div class="container" style="height:40px;">
				<div id="common-div-exportparam" style="margin: 10px;color: red;"></div>
			</div>
			<div align="center">
				<table cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td>
							<c:choose>
								<c:when test="${version == '2'}">
									<input type="radio" name="versiontype" value="1" checked="checked"/>简化版
      								<input type="radio" name="versiontype" value="2" />合同版
								</c:when>
								<c:when test="${version == '3'}">
									<input type="radio" name="filestype" value="1" checked="checked"/>Excel文件
      								<input type="radio" name="filestype" value="2" />txt文件
								</c:when>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td>
							<input type="hidden" name="version" value="${version }"/>
						</td>
					</tr>
				</table>
			</div>
			<div align="center">
				<a href="javascript:;" id="common-export-exportPage" class="easyui-linkbutton" data-options="iconCls:'icon-sum'">导出</a>
			</div>
		</div>
	</form>
	<script type="text/javascript">
		var exportExcel_AjaxConn = function (Data, Action) {
		    var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false
		    })
		    return MyAjax.responseText;
		}
		$(function(){
			$("#common-export-exportPage").click(function(){
				$("#common-form-exportPage").submit();
				$("#excel-export-dialog").dialog('close', true);
			});
		});
	</script>
</body>
</html>