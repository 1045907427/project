<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>单据编号新增详情</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
		<div title="" data-options="region:'north',border:false" style="height: 115px;">
			<form action="" method="post" id="sysNumber-form-billInfo">
				<input id="system-testvalue-sysNumber" name="sysNumber.testvalue" type="hidden" />
				<div id="sysNumber-button-billDetail">
					<table cellpadding="2" cellspacing="2" border="0">
						<tr>
							<td width="80px">表名:</td>
							<td><input type="text" name="sysNumber.tablename"  style="width: 180px;" id="system-tablename-sysNumber"/></td>
							<td width="80px">单据编码:</td>
							<td><input id="syetem-billcode-sysNumber" class="easyui-validatebox" data-options="required:true,validType:'validbillcode'" name="sysNumber.billcode" style="width: 180px;" maxlength="100"/></td>
							<td width="80px">单据名称:</td>
                            <td><input type="text" class="easyui-validatebox" data-options="required:true,validType:'validbillname'" name="sysNumber.billname" id="system-billname-sysNumber" style="width: 180px;"/></td>
						</tr>
						<tr>
                            <td>状态:</td>
                            <td>
                                <select style="width: 180px;" disabled="disabled">
                                    <option value="4">新增</option>
                                </select>
                            </td>
							<td>流水号长度:</td>
							<td><input type="text" id="system-seriallength-sysNumber" name="sysNumber.seriallength" style="width: 180px;" required="true" class="easyui-numberbox" data-options="min:0,max:99"></td>
							<td>流水号步长:</td>
							<td><input type="text" id="system-serialstep-sysNumber" name="sysNumber.serialstep" style="width: 180px;" required="true" class="easyui-numberbox" data-options="min:0,max:999"/></td>
						</tr>
						<tr>
                            <td>流水号起始值:</td>
                            <td><input type="text" id="system-serialstart-sysNumber" name="sysNumber.serialstart" style="width: 180px;" required="true" class="easyui-numberbox" data-options="min:0,max:99999999999"/></td>
							<td>当前流水号:</td>
							<td><input type="text" name="sysNumber.serialnumber" style="width: 180px;" readonly="readonly" maxlength="11"/></td>
							<td width="105px">流水号依据字段值:</td>
							<td><input type="text" id="system-valName-sysNumber" style="width: 180px;" maxlength="20" readonly="readonly"/></td>
						</tr>
                        <tr>
                            <td>预览效果:</td>
                            <td><input type="text" id="system-preView-sysNumber" name="sysNumber.preview" style="width: 180px;" maxlength="50" readonly="readonly"/></td>
                        </tr>
					</table>
				</div>
			</form>
		</div> 
		<div data-options="region:'center',border:false">
			<div id="system-button-sysNumberRule" style="padding:2px;height:auto">
				<a href="javaScript:void(0);" id="system-add-sysNumberRule" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增单据编号规则">新增</a>
				<a href="javaScript:void(0);" id="system-edit-sysNumberRule" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'" title="修改单据编号规则">修改</a>
				<a href="javaScript:void(0);" id="system-delete-sysNumberRule" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'" title="删除单据编号规则">删除</a>
			</div>
			<table id="system-table-sysNumberRule"></table>
		</div>
	</div>
	<script type="text/javascript">
		//表名
		$("#system-tablename-sysNumber").widget({
			width:180,
			name:'t_sys_number',
			col:'tablename',
			singleSelect:true,
			required:true,
	  		onSelect:function(data){
	  			$("#syetem-billcode-sysNumber").val(data.tablename);
   				$("#system-billname-sysNumber").val(data.tabledescname);
	  		}
		});
		
		$(function(){
			$("#sysNumber-button-billtype").buttonWidget("initButtonType","add");

            //检验商品档案数据（唯一性，最大长度等）
            $.extend($.fn.validatebox.defaults.rules, {
                validbillcode:{//编号唯一性,最大长度
                    validator:function(value,param){
                        var reg=/^\w+$/i;
                        if(reg.test(value)){
                            var ret = sysNumber_AjaxConn({billcode:value},'sysNumber/isRepeatBillCode.do');//true 重复，false 不重复
                            var retJson = $.parseJSON(ret);
                            if(retJson.flag){
                                $.fn.validatebox.defaults.rules.validbillcode.message = '单据编码重复, 请重新输入!';
                                return false;
                            }
                        }
                        else{
                            $.fn.validatebox.defaults.rules.validbillcode.message = '只允许输入字母、数字或下划线字符!';
                            return false;
                        }
                        return true;
                    },
                    message:''
                },
                validbillname:{//编号唯一性,最大长度
                    validator:function(value,param){
                        var reg=/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im;
                        if(!reg.test(value)){
                            var ret = sysNumber_AjaxConn({billname:value},'sysNumber/isRepeatBillName.do');//true 重复，false 不重复
                            var retJson = $.parseJSON(ret);
                            if(retJson.flag){
                                $.fn.validatebox.defaults.rules.validbillname.message = '单据名称重复, 请重新输入!';
                                return false;
                            }
                        }
                        else{
                            $.fn.validatebox.defaults.rules.validbillname.message = '不能输入特殊字符串!';
                            return false;
                        }
                        return true;
                    },
                    message:''
                }
            });

			sysNumberRuleDataGrid("add");
		});
	</script>
  </body>
</html>
