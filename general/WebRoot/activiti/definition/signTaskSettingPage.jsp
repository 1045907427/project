<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>会签设置页面</title>
  </head>
  <body>
  	<style type="text/css">
  		.signSettingTable{border-collapse:collapse;border:1px solid #7babcf;width:100%;}
  		.signSettingTable td{border:1px solid #7babcf;line-height:25px;padding-left:2px;}
  		.signSettingTable td .signSettingRadio{position:relative;top:3px;}
  		.signSettingTable td .combo{text-indent:0;}
		label > input[type=radio]{
			float:left;
			height: 16px;
			line-height: 16px;
		}
		label > input[type=checkbox]{
			 float:left;
			 height: 18px;
			 line-height: 18px;
		 }
  	</style>
	<c:choose>
		<c:when test="${not empty list}">

			<div style="border-bottom:1px solid #7babcf;">
				<a class="easyui-linkbutton" id="activiti-save-signTaskSettingPage" data-options="plain:true,iconCls:'button-save'">保存</a>
			</div>
			<div style="border:1px solid #7babcf; margin:5px 0 5px 0; background:#efefef;">
				<ol>
					<li style="color: #F00;">每个会签节点请务必配置人员规则，否则工作将无法转交至该节点。</li>
					<li>投票类型为绝对票数时，票数不能大于会签人员数量，当投票类型为百分比时，在票数处输入1到100的数字。</li>
					<li>达条件统计方式表示只要条件达到设定的要求就结束任务流转下一节点，未会签人员不需签。</li>
					<li>全签收统计方式表示所有人签收后再统计投票结果流转下一节点。</li>
				</ol>
			</div>
			<form id="activiti-form-signTaskSettingPage" action="act/addSignRule.do" method="post">
				<input type="hidden" value="${definitionkey }" name="definitionkey" />
				<table cellpadding="0" cellspacing="0" class="signSettingTable">
					<tr style="background: #dfdfdf;line-height:28px;">
						<td style="width:4%">序号</td>
						<td style="width:10%">节点标识</td>
						<td style="width:16%">节点名称</td>
						<td style="width:12%">统计方式</td>
						<td style="width:8%">决策方式</td>
						<td style="width:10%">投票类型</td>
						<td style="width:10%">票数/百分数</td>
						<td  style="width:30%">人员设置</td>
					</tr>
					<c:forEach items="${list }" var="item" varStatus="status">
						<tr>
							<td>${status.index + 1}</td>
							<td>${item.taskkey }<input type="hidden" name="taskkey" value="${item.taskkey }" /></td>
							<td>${item.taskname }<input type="hidden" name="taskName" value="${item.taskname }" /></td>
							<td>
								<label><input class="signSettingRadio" name="counttype" type="radio" value="1" <c:if test="${item.counttype ne 0 }">checked="checked"</c:if> />达条件统计</label><br/>
								<label><input class="signSettingRadio" name="counttype" type="radio" value="0" <c:if test="${item.counttype eq 0 }">checked="checked"</c:if> />全签收统计</label>
							</td>
							<td>
								<label><input class="signSettingRadio" name="decisiontype" type="radio" value="1" <c:if test="${item.decisiontype ne 0 }">checked="checked"</c:if> />通过</label><br/>
								<label><input class="signSettingRadio" name="decisiontype" type="radio" value="0" <c:if test="${item.decisiontype eq 0 }">checked="checked"</c:if> />拒绝</label>
							</td>
							<td>
								<label><input class="signSettingRadio" name="votetype" type="radio" value="1" <c:if test="${item.votetype ne 0 }">checked="checked"</c:if> onclick="javascript:$('#activiti-votenum-signTaskSettingPage').numberbox({min:1,max:9999,precision:0})"/>绝对票数</label><br/>
								<label><input class="signSettingRadio" name="votetype" type="radio" value="0" <c:if test="${item.votetype eq 0 }">checked="checked"</c:if> onclick="javascript:$('#activiti-votenum-signTaskSettingPage').numberbox({min:1,max: 100,precision:0})"/>百分数</label>
							</td>
							<td>
								<c:choose>
									<c:when test="${item.votetype eq 1}">
										<input class="easyui-numberbox" name="votenum" id="activiti-votenum-signTaskSettingPage" value="${item.votenum }" required="required" validType="integer" style="width: 60px;" data-options="min:1,max:100,precision:0"/>
									</c:when>
									<c:otherwise>
										<input class="easyui-numberbox" name="votenum" id="activiti-votenum-signTaskSettingPage" value="${item.votenum }" required="required" validType="integer" style="width: 60px;" data-options="min:1,max:9999,precision:0"/>
									</c:otherwise>
								</c:choose>
							</td>
							<td>
									<%--<div>${item.username }</div>--%>
								<div style="padding:3px;" class="rule_sign rule_sign_${item.taskkey }" data="${item.taskkey }">
									<div class="rule_sign_tips_${item.taskkey }">加载中...</div>
								</div>
								<a class="easyui-linkbutton" data-options="plain:true,iconCls:'button-setting'" onclick="javascript:setSign('sign', '${definitionkey}', '${item.taskkey }')">设置</a>
								<a class="easyui-linkbutton" data-options="plain:true,iconCls:'button-close'">清除</a>
								<input type="hidden" name="user_${item.key }" value="${item.user }" />
								<input type="hidden" name="name_${item.key }" value="${item.username }" />
							</td>
						</tr>
					</c:forEach>
				</table>
			</form>
			<div id="activiti-dialog2-signTaskSettingPage"></div>
			<div id="dummy2" class="nodisplay"></div>
			<script type="text/javascript">
                $(function(){

                    $("#activiti-save-signTaskSettingPage").click(function(){
                        $("#activiti-form-signTaskSettingPage").submit();
                    });

                    $("#activiti-form-signTaskSettingPage").form({
                        onSubmit: function(){
                            var flag = $(this).form("validate");
                            if(flag == false) return ;
                            loading("提交中..");
                        },
                        success:function(data){
                            loaded();
                            var json = $.parseJSON(data);
                            if(json.flag == true){
                                $.messager.alert("提醒", "设置成功");
                            }
                            else{
                                $.messager.alert("提醒", "设置失败");
                            }
                        }
                    });

                    // 加载各节点审批人员设定
                    $('.rule_sign').each(function() {

                        var taskkey = $(this).attr('data');
                        var definitionkey = '${param.definitionkey }';
                        var type = 'sign';

                        var p = {definitionkey: definitionkey, taskkey: taskkey, type: type};
                        initUserTask(p);

                    });

                    // initUserTask({definitionkey: '${param.key }', taskkey: '', type: 'add'});
                });

                // 初始化该节点的人员规则设定
                function initUserTask(p) {

                    $.ajax({
                        type: 'post',
                        dataType: 'json',
                        url: 'act/getRuleDetailDescription.do',
                        data: p,
                        success: function(json) {

                            var definitionkey = json.definitionkey;
                            var taskkey = json.taskkey;
                            var type = json.type;

                            var html = new Array();
                            html.push('<div class="activiti-rule-' + definitionkey + '-' + taskkey + '-' + type + '">');
                            html.push('<div style="background: #dddddd; margin: 1px; padding-left: 5px; border: 1px solid #ccc;">固定规则</div>');
                            for(key in json) {

                                if(key == 'definitionkey' || key == 'taskkey' || key == 'type' || key == 'mappings') {
                                    continue;
                                }

                                if(json[key].rule != undefined && json[key].rule != null && json[key].rule != '') {

                                    var rule = json[key].rule;

                                    var userid = json[key].userid;
                                    var deptid = json[key].deptid;
                                    var roleid = json[key].roleid;
                                    var postid = json[key].postid;

                                    $('<input type="text" id="activiti-dummy1-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage"/>').appendTo($('div#dummy2'));
                                    $('<input type="text" id="activiti-dummy2-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage"/>').appendTo($('div#dummy2'));
                                    $('<input type="text" id="activiti-dummy3-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage"/>').appendTo($('div#dummy2'));
                                    $('<input type="text" id="activiti-dummy4-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage"/>').appendTo($('div#dummy2'));

                                    var rulename = '';
                                    var ruledescription = '';
                                    // 指定人员
                                    if(rule == '01') {

                                        $('#activiti-dummy1-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').val(userid);
                                        $('#activiti-dummy1-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').widget({referwid: 'RT_T_SYS_USER', singleSelect: false,});
                                        rulename = '指定人员';
                                        ruledescription = $('#activiti-dummy1-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').widget('getText');

                                        // 本部门指定角色
                                    } else if(rule == '02') {

                                        $('#activiti-dummy1-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').val(roleid);
                                        $('#activiti-dummy1-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').widget({referwid: 'RL_T_AC_AUTHROITY', singleSelect: false});
                                        rulename = '本部门指定角色';
                                        ruledescription = $('#activiti-dummy1-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').widget('getText');

                                        // 本部门
                                    } else if(rule == '03') {

                                        rulename = '本部门所有人';
                                        ruledescription = '本部门所有人';

                                        // 指定角色
                                    } else if(rule == '04') {

                                        $('#activiti-dummy1-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').val(roleid);
                                        $('#activiti-dummy1-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').widget({referwid: 'RL_T_AC_AUTHROITY', singleSelect: false});
                                        rulename = '指定角色';
                                        ruledescription = $('#activiti-dummy1-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').widget('getText');

                                        // 指定部门
                                    } else if(rule == '05') {

                                        $('#activiti-dummy1-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').val(deptid);
                                        $('#activiti-dummy1-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').widget({referwid: 'RT_T_SYS_DEPT', singleSelect: false});
                                        rulename = '指定部门';
                                        ruledescription = $('#activiti-dummy1-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').widget('getText');

                                        // 指定部门与角色
                                    } else if(rule == '06') {

                                        $('#activiti-dummy1-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').val(deptid);
                                        $('#activiti-dummy1-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').widget({referwid: 'RT_T_SYS_DEPT', singleSelect: false});
                                        $('#activiti-dummy2-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').val(roleid);
                                        $('#activiti-dummy2-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').widget({referwid: 'RL_T_AC_AUTHROITY', singleSelect: false});
                                        rulename = '指定部门与角色';
                                        ruledescription = $('#activiti-dummy1-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').widget('getText') + '：' + $('#activiti-dummy2-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').widget('getText');
                                    } else if(rule == '07') {

                                        $('#activiti-dummy1-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').val(postid);
                                        $('#activiti-dummy1-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').widget({referwid: 'RL_T_BASE_WORKJOB', singleSelect: false});
                                        rulename = '指定岗位';
                                        ruledescription = $('#activiti-dummy1-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage').widget('getText');

                                    }

                                    html.push('<div class="activiti-rule-' + definitionkey + '-' + taskkey + '-' + type + '" style="border: 1px solid #ccc; margin: 1px;">');
                                    html.push('<div>规则：' + rulename + '</div>');
                                    html.push('<div>明细：' + ruledescription + '</div>');
                                    html.push('</div>');
                                }

                            }

                            if(html.length == 2) {
                                html.push('<div class="activiti-rule-' + definitionkey + '-' + taskkey + '-' + type + '" style="border: 1px solid #ccc; margin: 1px;">');
                                html.push('无指定规则');
                                html.push('</div>');
                            }

                            $('.activiti-rule-' + definitionkey + '-' + taskkey + '-' + type).remove();
                            $('.rule_' + type + '_' + taskkey).html('');
                            $('.rule_' + type + '_tips_' + taskkey).remove();
                            $('#activiti-button-'+ type + '-' + taskkey + '-definitionUserSettingPage').linkbutton('enable');
                            $('#activiti-button-'+ type + '-' + taskkey + '-clear-definitionUserSettingPage').linkbutton('enable');
                            $('.rule_' + type + '_' + taskkey).append(html.join(''));

                            return ;
                        }
                    });

                }

                function setSign(type, definitionkey, taskkey){

                    var did = 'd' + getRandomid();
                    $("#activiti-dialog2-signTaskSettingPage").after('<div id="activiti-' + did + '-signTaskSettingPage"></div>');
                    $dialog2 = $('#activiti-' + did + '-signTaskSettingPage');

                    $dialog2.dialog({
                        width:620,
                        height:450,
                        title:'会签人员设置',
                        cache:false,
                        modal:true,
                        closed:true,
                        maximized:true,
                        href:'act/signTaskUserSettingPage.do?type=' + type + '&definitionkey=' + definitionkey + '&taskkey=' + taskkey,
                        onClose: function() {

                            $dialog2.dialog('destroy');
                        }
                    }).dialog('open');
                }

			</script>
		</c:when>
		<c:otherwise>
			该流程不存在会签节点。
		</c:otherwise>
	</c:choose>
  </body>
</html>
