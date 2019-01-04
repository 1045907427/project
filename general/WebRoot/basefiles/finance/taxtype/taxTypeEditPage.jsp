<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>税种新增页面</title>
  </head>
  
  <body>
    <form action="" method="post" id="finance-form-editTax">
    	<table cellpadding="2" cellspacing="2" border="0" style="width: 350px;">
    		<tr>
    			<td style="width: 60px;">编码:</td>
    			<td><input id="finance-id-taxtype" name="taxType.id" value="<c:out value="${taxType.id}"></c:out>" style="width: 200px;" class="easyui-validatebox" validType="validID[20]" required="true" <c:if test="${editFlag == false }">readonly="readonly"</c:if>/>
    				<input id="taxType-oldid" name="taxType.oldId" type="hidden" value="<c:out value="${taxType.id}"></c:out>"/>
    				<input id="taxType-name" type="hidden" value="<c:out value="${taxType.name}"></c:out>"/>
    				<input name="taxType.state" type="hidden" value="${taxType.state}"/>
    			</td>
    		</tr>
    		<tr>
    			<td style="width: 60px;">名称:</td>
    			<td><input name="taxType.name" value="<c:out value="${taxType.name}"></c:out>" style="width: 200px;" class="easyui-validatebox" validType="validName[50]" required="true"/></td>
    		</tr>
    		<tr>
    			<td style="width: 60px;">类型:</td>
    			<td><input id="common-combobox-taxtype" name="taxType.type" class="easyui-combobox" style="width: 200px;" value="${taxType.type}" required="true"/></td>
    		</tr>
    		<tr>
    			<td style="width: 60px;">税率%:</td>
    			<td><input name="taxType.rate" id="finance-rate-taxtype" value="${taxType.rate}" class="easyui-numberbox" data-options="precision:2,max:99" style="width: 200px;" validType="validRate" required="true" <c:if test="${editFlag == false }">readonly="readonly"</c:if>/></td>
    		</tr>
<c:if test="${useHTKPExport=='1'}">
			<tr>
				<td style="width: 60px;">金税编码:</td>
				<td><input id="finance-jsrateid-taxtype" name="taxType.jsrateid" value="${taxType.jsrateid}" class="easyui-validatebox" validType="maxLen[20]"  style="width: 200px;"/>
				</td>
			</tr>
			<tr>
				<td style="width: 60px;">金税标识:</td>
				<td>
					<select id="finance-jsflag-taxtype" name="taxType.jsflag" value="${taxType.jsflag}" style="width:200px;">
						<option value="0" <c:if test="${taxType.jsflag != '1'}">selected="selected"</c:if>>默认</option>
						<option value="1" <c:if test="${taxType.jsflag =='1'}">selected="selected"</c:if>>免税</option>
					</select>
				</td>
			</tr>
</c:if>
    		<tr>
    			<td style="width: 60px;">备注:</td>
    			<td><textarea name="taxType.remark" class="easyui-validatebox" validType="maxLen[200]" style="height:80px;width: 195px;"><c:out value="${taxType.remark}"></c:out></textarea></td>
    		</tr>
    		<tr>
    			<td style="width: 60px;"> 状态:</td>
    			<td><input id="common-combobox-state" value="${taxType.state}" disabled="disabled" class="easyui-combobox" style="width: 203px" /></td>
    		</tr>
    	</table>
    </form>
    <script type="text/javascript">
    	validLengthAndUsed($("#taxType-oldid").val(),$("#taxType-name").val(),'${taxType.rate}');
    	function editTaxType(type){
    		if(type == "save"){
        		if(!$("#finance-form-editTax").form('validate')){
        			return false;
        		}
        	}
            var useHTKPExport="${useHTKPExport}";
            var rate=$("#finance-rate-taxtype").numberbox("getValue");
            if(useHTKPExport=="1"){
                var jsflag=$("#finance-jsflag-taxtype").val()||"";
                if(jsflag=="1" && rate!=0){
                    $.messager.alert("提醒","当税率等于零时，金税标识才可以选择免税");
                    return false;
                }
            }
        	var ret = taxType_AjaxConn($("#finance-form-editTax").serializeJSON(),'basefiles/finance/editTaxType.do?type='+type);
        	var retJson = $.parseJSON(ret);
        	if(retJson.flag){
        		if(type == "save"){
        			$.messager.alert("提醒","保存成功");
        		}
        		else{
        			$.messager.alert("提醒","暂存成功");
        		}
	  		    $("#finance-table-taxtypeList").datagrid('reload');
	  		    refreshLayout("税种【详情】",'basefiles/finance/showTaxTypeViewPage.do?id='+$("#finance-id-taxtype").val());
        	}
        	else{
                var msg="";
                if(type == "save"){
                    msg="保存失败";
                    if(retJson.msg!=null){
                        msg=msg+"<br/>"+retJson.msg ||"";
                    }
                    $.messager.alert("提醒",msg);
                }
                else{
                    msg="暂存失败";
                    if(retJson.msg!=null){
                        msg=msg+"<br/>"+retJson.msg ||"";
                    }
                    $.messager.alert("提醒",msg);
                }
        	}
    	}
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
			$("#finance-button-taxType").buttonWidget("setDataID", {id:$("#taxType-oldid").val(), state:'${taxType.state}', type:'edit'});
			if('${taxType.state}' != '1'){
				$("#finance-button-taxType").buttonWidget('disableButton','button-add');
			}
    	});
    </script>
  </body>
</html>
