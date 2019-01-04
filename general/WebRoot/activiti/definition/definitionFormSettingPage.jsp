<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>流程节点表单设置</title>
  </head>
  <body>
  	<style type="text/css">
  		.formSettingTable{border-collapse:collapse;border:1px solid #7babcf;width:100%;}
  		.formSettingTable td{border:1px solid #7babcf;line-height:25px;padding-left:5px;}
  		.formSettingTable td input{padding:2px;}
  		.formSettingTable td .formSettingCheckbox{position:relative;top:3px;}
  		.formSettingTable td .combo{text-indent:0;}
  		.noborder td{border: 0px;}
		label > input[type=checkbox]{
			float:left;
			height: 18px;
			line-height: 18px;
		}
	</style>
  	<div style="border-bottom:1px solid #7babcf;">
  		<a class="easyui-linkbutton" id="activiti-save-definitionFormSettingPage" data-options="plain:true,iconCls:'button-save'">保存</a>
  	</div>
  	<div style="border:1px solid #7babcf; margin:5px 0 5px 0; background:#efefef;">
        <ol>
            <li>在线表单：为系统自定义表单，通过编辑器设计生成。</li>
            <li>URL表单：是外部表单，地址写法规则为：路径从根开始写，例如 ：xx/xxxxPage.do 。</li>
            <li class="nodisplay">结束监听：URL表单类流程执行结束时执行的外部方法。</li>
            <li>结束通知：工作结束后是否通知发起人。</li>
        </ol>
  	</div>
  	<form id="activiti-form-definitionFormSettingPage" action="act/addDefinitionForm.do" method="post">
  	<input type="hidden" value="${param.definitionkey }" name="definitionkey" />
  	<table cellpadding="0" cellspacing="0" class="formSettingTable">
  		<tr style="height:50px;">
  			<td style="width:17%;background:#ebf5ff;text-align:right;font-weight:400;">全局表单：&nbsp;</td>
  			<td style="width:12%">
  				<select class="formSettingFormTypeGlobal" name="formtype" style="width: 95%;">
  					<option value="">请选择</option>
					<option value="formkey">在线表单</option>
					<option value="business">URL表单</option>
  				</select>
  			</td>
  			<td>
  				<div style="display:none;" class="formtype formkey">
  					<%--<div class="gFormKey_div">${definition.formkey }</div>--%>
  					<%--<a class="easyui-linkbutton" onclick="chooseForm('gFormKey')" data-options="plain:true, iconCls:'button-commonquery'">选择</a>--%>
		  			<input type="text" name="gFormkey" class="gFormKey" value="${definition.formkey }" id="activiti-gFormkey-definitionFormSettingPage"/>
  				</div>
  				<div style="display:none;" class="formtype business">
  					<table style="width: 95%;" class="noborder">
  						<tr>
  							<td><div style="width: 70px;" class="right">表单URL：</div></td>
  							<td style="width: 100%;"><input class="easyui-validatebox" type="text" style="width: 98%;" name="gBusinessUrl" value="${definition.businessurl }" autocomplete="off" data-options="validType:'url'"/></td>
  						</tr>
  						<tr class="nodisplay">
  							<td><div style="width: 70px;" class="right">结束监听：</div></td>
  							<td><input type="text" style="width: 98%;" name="endListener" value="${definition.endlistener }" autocomplete="off"/></td>
  						</tr>
  					</table>
  				</div>
  			</td>
  		</tr>
  		<tr style="height:40px;">
  			<td style="width:17%;background:#ebf5ff;text-align:right;font-weight:400;">结束通知：&nbsp;</td>
  			<td colspan="2">
  				<label><input type="checkbox" class="formSettingCheckbox" name="endremindtype" value="1" <c:if test="${fn:contains(definition.endremindtype, '1') }">checked="checked"</c:if> />站内短信</label>
  			</td>
  		</tr>
  	</table>
  	<div style="height:5px"></div>
	<table cellpadding="0" cellspacing="0" class="formSettingTable">
		<tr style="background: #dfdfdf;line-height:28px;">
            <td style="width:5%">序号</td>
            <td style="width:12%">节点标识</td>
            <td style="width:17%">节点名称</td>
            <td style="width:11%"><label><input type="checkbox" class="formSettingCheckbox" name="remindtype" value="1"/>通知审批人</label></td>
            <td style="width:11%"><label><input type="checkbox" class="formSettingCheckbox" name="endremindapplier" value="1"/>结束通知发起人</label></td>
            <td style="width:12%">表单类型</td>
            <td style="width:32%" class="formTypeName">表单</td>
		</tr>
		<c:forEach items="${list }" var="item" varStatus="status">
			<tr <c:if test="${item.nodeType eq 'ParallelMultiInstanceBehavior'}">sign title="会签节点" </c:if>>
				<td>
                    ${status.index + 1 }
                    <input type="hidden" name="definitionid" value="${definition.definitionid }" />
                </td>
				<td>${item.key }<input type="hidden" name="taskkey" value="${item.key }" /></td>
				<td>${item.name }<input type="hidden" name="taskName" value="${item.name }" /></td>
				<td>
					<label><input type="checkbox" name="remindtype_${item.key }" class="formSettingCheckbox" value="1" <c:if test="${fn:contains(item.remindtype, '1') }">checked="checked"</c:if> />站内短信</label>
				</td>
                <td>
                    <label><input type="checkbox" name="endremindapplier_${item.key }" class="formSettingCheckbox" value="1" <c:if test="${fn:contains(item.endremindapplier, '1') }">checked="checked"</c:if> />站内短信</label>
                </td>
				<td>
					<select class="formSettingFormType" disabled="disabled" style="width: 95%;">
						<option value="">请选择</option>
						<option value="formkey">在线表单</option>
						<option value="business">URL表单</option>
					</select>
				</td>
				<td style="padding:0;">
					<div style="padding:3px;">
						<div style="display:none;" class="">
							<div class="${item.key}_div">${item.formkey }</div>
		  					<a class="easyui-linkbutton" onclick="chooseForm('${item.key}')" data-options="plain:true, iconCls:'icon-search'">选择</a>
		  					<input type="hidden" name="formkey" class="${item.key}" value="${item.formkey }" />
		  				</div>
                        <div style="display:none;" class="formtype formkey">
                            <a class="easyui-linkbutton" onclick="setFormRule('${item.key}', '${item.name }')" data-options="plain:true,iconCls:'button-commonquery'<c:if test="${item.nodeType eq 'ParallelMultiInstanceBehavior'}">,disabled:true</c:if>" <c:if test="${item.nodeType eq 'ParallelMultiInstanceBehavior'}">title="会签节点不支持该设置。"</c:if>>设定表单权限</a>
                        </div>
		  				<div style="display:none;" class="formtype business">
		  					<!-- 表单URL： --><input type="text" class="easyui-validatebox" style="width: 95%;" name="businessUrl" value="${item.businessUrl }" autocomplete="off" data-options="validType:'url'" <c:if test="${item.nodeType eq 'ParallelMultiInstanceBehavior'}">readonly="readonly" </c:if>/>
		  				</div>
					</div>
				</td>
			</tr>
		</c:forEach>
	</table>
	</form>
	<div id="activiti-dialog-definitionFormSettingPage"></div>
	<script type="text/javascript">

        <!--

        $.extend($.fn.validatebox.defaults.rules, {
                url: {
                    validator: function (value, param) {
                        if(value && value.match(/^[\w\d\/]+\.do(\?[\w\d]+=[\w\d]*(&[\w\d]+=[\w\d]*)*)?$/g) == null) {
                            return false;
						}
						return true;
                    },
                    message: '请输入正确的表单URL<br/>格式：xxx[/xxxx]/xxxPage.do[?p1=x1&p2=x2]，[]里的内容为可选。'
                }
            }
        );

		$(function(){

            $("#activiti-gFormkey-definitionFormSettingPage").widget({
                referwid:'RT_T_ACT_FORM',
                singleSelect:true,
                width:145,
                onlyLeafCheck:true
            });

			$("#activiti-save-definitionFormSettingPage").click(function(){
				$("#activiti-form-definitionFormSettingPage").submit();
			});
			$("#activiti-form-definitionFormSettingPage").form({
				onSubmit: function(){
				    var flag = $("#activiti-form-definitionFormSettingPage").form('validate');
				    if(!flag) {
				        return false;
					}
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		  	if(json.flag == true){
		  		  		$.messager.alert("提醒", "设置成功。");
		  		  	}
		  		  	else{
                        if(json.message != undefined) {
                            $.messager.alert("提醒", json.message);
                            return false;
                        }
		  		  		$.messager.alert("提醒", "设置失败！");
		  		  	}
		  		}
			});
			$(".formSettingFormTypeGlobal").val("${definition.formtype}");
			$(".formSettingFormTypeGlobal").change(function(){
				var type = $(this).val();
				$(".formSettingFormType").val(type);
				$(".formtype").hide();
				if(type != ""){
					$("."+ type).show();
				}
				
				if(type == 'formkey') {
					$('td.formTypeName').text('在线表单');
				} else if(type == 'business') {
					$('td.formTypeName').text('表单URL');
				} else {
					$('td.formTypeName').text('表单');
				}
				
			});
			$(".formSettingFormTypeGlobal").change();
			$("#activiti-dialog-definitionFormSettingPage").dialog({
				width:710,
				height:400,
				title:'表单选择',
				cache:false,
				modal:true,
				closed:true
			});

            // 通知方式全选
            $('input[type=checkbox][name=remindtype]').click(function() {

                if($(this).is(':checked')) {
                    $('input[type=checkbox][name^="remindtype_"]').attr('checked', 'checked');
                } else {
                    $('input[type=checkbox][name^="remindtype_"]').removeAttr('checked');
                }

            });

            // 结束通知发起人方式全选
            $('input[type=checkbox][name=endremindapplier]').click(function() {

                if($(this).is(':checked')) {
                    $('input[type=checkbox][name^="endremindapplier_"]').attr('checked', 'checked');
                } else {
                    $('input[type=checkbox][name^="endremindapplier_"]').removeAttr('checked');
                }

            });
		});
		function chooseForm(id){

            var did = 'd' + getRandomid();
            $('body').append('<div id="activiti-' + did + '-definitionFormSettingPage"></div>');

			$('#activiti-' + did + '-definitionFormSettingPage').dialog({
                width:700,
                height:400,
                title:'表单选择',
                cache:false,
                modal:true,
                closed:true,
				href:'act/formSelectPage.do',
				buttons:[
                    {
                        iconCls:'button-save',
                        text:'确定',
                        handler:function(){
                            var row = $("#activiti-datagrid-formSelectPage").datagrid("getSelected");
                            if(row == null) {

                                return ;
                            }

                            $("."+id).val(row.unkey);
                            $("."+id+"_div").text(row.unkey);
                            $('#activiti-' + did + '-definitionFormSettingPage').dialog('close');
                            $("#activiti-form-definitionFormSettingPage").submit();
                        }
                    },
                    {
                        iconCls:'button-close',
                        text:'关闭',
                        handler:function(){
                            $('#activiti-' + did + '-definitionFormSettingPage').dialog('close');
                        }
                    }
				],
                onClose: function() {

                    $('#activiti-' + did + '-definitionFormSettingPage').dialog('destroy');
                }
			});
            $('#activiti-' + did + '-definitionFormSettingPage').dialog('open');
		}

        function setFormRule(taskkey, taskname) {
            $("#activiti-dialog-definitionFormSettingPage").dialog({
                href:'act/formRuleSetPage.do?definitionkey=${param.definitionkey }&taskkey=' + taskkey,
                title: '表单权限设定[' + taskname + ']',
                buttons:[
                    {
                        iconCls:'button-save',
                        text:'确定',
                        handler:function(){
                            //var row = $("#activiti-datagrid-formSelectPage").datagrid("getSelected");
                            //if(row == null) return ;
                            //$("."+id).val(row.unkey);
                            //$("."+id+"_div").text(row.unkey);
                            $("#activiti-dialog-definitionFormSettingPage").dialog('close');
                        }
                    }
                ]
            });
            $("#activiti-dialog-definitionFormSettingPage").dialog('open');
        }

        -->

	</script>
  </body>
</html>
