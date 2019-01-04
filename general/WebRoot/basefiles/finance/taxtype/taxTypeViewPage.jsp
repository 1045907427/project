<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>税种新增页面</title>
  </head>
  
  <body>
    <form action="" method="post" id="finance-form-addTax">
    	<input id="taxType-oldid" type="hidden" value="<c:out value="${taxType.id}"></c:out>"/>
    	<table cellpadding="2" cellspacing="2" border="0" style="width: 350px;">
    		<tr>
    			<td style="width: 60px;">编码:</td>
    			<td><input id="finance-id-taxtype" name="taxType.id" value="<c:out value="${taxType.id}"></c:out>" style="width: 200px;" readonly="readonly"/>
    			</td>
    		</tr>
    		<tr>
    			<td style="width: 60px;">名称:</td>
    			<td><input name="taxType.name" value="<c:out value="${taxType.name}"></c:out>" style="width: 200px;" readonly="readonly"/></td>
    		</tr>
    		<tr>
    			<td style="width: 60px;">类型:</td>
    			<td><input id="common-combobox-taxtype" id="finance-rate-taxtype" name="taxType.type" class="easyui-combobox" style="width: 200px;" value="${taxType.type}" disabled="disabled"/></td>
    		</tr>
    		<tr>
    			<td style="width: 60px;">税率%:</td>
    			<td><input name="taxType.rate" value="${taxType.rate}" class="easyui-numberbox" data-options="precision:2,max:99" style="width: 200px;" readonly="readonly"/></td>
    		</tr>
<c:if test="${useHTKPExport=='1'}">
			<tr>
				<td style="width: 60px;">金税编码:</td>
				<td><input id="finance-jsrateid-taxtype" name="taxType.jsrateid" value="${taxType.jsrateid}" class="easyui-validatebox" style="width: 200px;" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td style="width: 60px;">金税标识:</td>
				<td>
					<select id="finance-jsflag-taxtype" name="taxType.jsflag" value="${taxType.jsflag}" disabled="disabled" style="width:200px;">
						<option value="0" <c:if test="${taxType.jsflag != '1'}">selected="selected"</c:if>>默认</option>
						<option value="1" <c:if test="${taxType.jsflag =='1'}">selected="selected"</c:if>>免税</option>
					</select>
				</td>
			</tr>
</c:if>
    		<tr>
    			<td style="width: 60px;">备注:</td>
    			<td><textarea name="taxType.remark" readonly="readonly" style="height:80px;width: 195px;"><c:out value="${taxType.remark}"></c:out></textarea></td>
    		</tr>
    		<tr>
    			<td style="width: 60px;"> 状态:</td>
    			<td><input id="common-combobox-state" value="${taxType.state}" disabled="disabled" class="easyui-combobox" style="width: 203px" /></td>
    		</tr>
    	</table>
    </form>
    <script type="text/javascript">
    	$(function(){
    		//税种类型
			$('#common-combobox-taxtype').combobox({
			    url:'common/sysCodeList.do?type=taxtype',   
			    valueField:'id',   
			    textField:'name'
			});
    		//状态
			$('#common-combobox-state').combobox({
			    url:'common/sysCodeList.do?type=state',   
			    valueField:'id',   
			    textField:'name'  
			});
			$("#finance-button-taxType").buttonWidget("setDataID", {id:$("#taxType-oldid").val(), state:'${taxType.state}', type:'view'});
			if('${taxType.state}' != '1'){
				$("#finance-button-taxType").buttonWidget('disableButton','button-add');
			}
    	});
    </script>
  </body>
</html>
