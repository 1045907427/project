<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>单据编号详情</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
		<div title="" data-options="region:'north',border:false" style="height: 115px;">
			<form action="" method="post" id="sysNumber-form-billInfo">
				<input id="system-numberid-sysNumber" name="sysNumber.numberid" type="hidden" value="${numberid }"/>
				<div id="sysNumber-button-billDetail">
					<table cellpadding="2" cellspacing="2" border="0">
						<tr>
							<td width="80px">表名:</td>
							<td><input type="text" value="${sysNumber.tablename }" disabled="disabled" name="sysNumber.tablename"  style="width: 180px;" id="system-tablename-sysNumber"/></td>
							<td width="80px">单据编码:</td>
							<td><input id="syetem-billcode-sysNumber" name="sysNumber.billcode" value="${sysNumber.billcode }" readonly="readonly" style="width: 180px;"/></td>
                            <td width="80px">单据名称:</td>
                            <td><input type="text" id="system-billname-sysNumber" name="sysNumber.billname" value="${sysNumber.billname}" style="width: 180px;" readonly="readonly"/></td>
						</tr>
						<tr>
                            <td width="80px">状态:</td>
                            <td><input id="sysNumber-widget-state" value="${sysNumber.state}" disabled="disabled" style="width: 180px" /></td>
							<td>流水号长度:</td>
							<td><input type="text" id="system-seriallength-sysNumber" name="sysNumber.seriallength" value="${sysNumber.seriallength }" readonly="readonly" style="width: 180px;" class="easyui-numberbox" data-options="min:0,max:99"></td>
							<td>流水号步长:</td>
							<td><input type="text" id="system-serialstep-sysNumber" name="sysNumber.serialstep" value="${sysNumber.serialstep }" readonly="readonly" style="width: 180px;" class="easyui-numberbox" data-options="min:0,max:999"/></td>
						</tr>
						<tr>
                            <td>流水号起始值:</td>
                            <td><input type="text" id="system-serialstart-sysNumber" name="sysNumber.serialstart" value="${sysNumber.serialstart }" readonly="readonly" style="width: 180px;" class="easyui-numberbox" data-options="min:0,max:99999999999"/></td>
							<td>当前流水号:</td>
							<td><input type="text" name="sysNumber.serialnumber" value="${sysNumber.serialnumber }" style="width: 180px;" readonly="readonly" maxlength="11"/></td>
							<td width="105px">流水号依据字段值:</td>
							<td><input type="text" id="system-valName-sysNumber" value="${testValueName}" style="width: 180px;" maxlength="20" readonly="readonly"/></td>
						</tr>
                        <tr>
                            <td>预览效果:</td>
                            <td><input type="text" id="system-preView-sysNumber" name="sysNumber.preview" value="${sysNumber.preview }" style="width: 180px;" maxlength="50" readonly="readonly"/></td>
                        </tr>
					</table>
				</div>
			</form>
		</div> 
		<div data-options="region:'center',border:false">
			<table id="system-table-sysNumberRule"></table>
		</div>
	</div>
	<script type="text/javascript">
		//表名
		$("#system-tablename-sysNumber").widget({
			width:180,
			name:'t_sys_number',
			col:'tablename',
			singleSelect:true
		});
		//状态
		$('#sysNumber-widget-state').widget({
			width:180,
			name:'t_sys_number',
			col:'state',
			singleSelect:true
		});
		$(function(){
			$("#sysNumber-button-billtype").buttonWidget("setDataID",{id:"${sysNumber.numberid}",state:"${sysNumber.state}",type:"view"});
			
			sysNumberRuleDataGrid("view");
			
			$('#system-add-sysNumberRule').linkbutton('disable');
			$('#system-edit-sysNumberRule').linkbutton('disable');
			$('#system-delete-sysNumberRule').linkbutton('disable');
		});
	</script>
  </body>
</html>
