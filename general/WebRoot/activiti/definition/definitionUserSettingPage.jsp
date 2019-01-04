<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>流程节点用户设置</title>
  </head>
  <body>
  	<style type="text/css">
		.userSettingTable {
			border-collapse: collapse;
			border: 1px solid #7babcf;
			width: 100%;
			table-layout: fixed;
			word-break: break-all;
		}

		.userSettingTable td {
			border: 1px solid #7babcf;
			line-height: 25px;
			text-indent: 0px;
		}

		.userSettingTable td .userSettingCheckbox {
			position: relative;
			top: 3px;
		}

		.userSettingTable td .combo {
			text-indent: 0;
		}

		label > input[type=checkbox] {
			float: left;
			height: 18px;
			line-height: 18px;
		}
	</style>
  	<div id="dummy" style="display: none;">
  		<input type="text" id="activiti-dummy1-definitionUserSettingPage"/>
  		<input type="text" id="activiti-dummy2-definitionUserSettingPage"/>
  		<input type="text" id="activiti-dummy3-definitionUserSettingPage"/>
  		<input type="text" id="activiti-dummy4-definitionUserSettingPage"/>
  	</div>
  	<div style="border:1px solid #7babcf; margin:0px 0 5px 0px; background: #efefef;">
        <ol>
            <li>该部分可以配置每个节点的候选人员，如果没有配置人员，则默认的候选人员为空。</li>
            <li>指定全体人员：未选中时，则只能在“人员设置”指定规则中的候选人员中选择人员；反之则能在<span style="color: #f00; font-weight: bold;">全体人员</span>中选择该节点的审批人员。</li>
        </ol>
  	</div>
	<table cellpadding="0" cellspacing="0" class="userSettingTable">
		<tr style="background: #dfdfdf;line-height:28px;">
			<td style="padding: 5px; width:5%">序号</td>
			<td style="padding: 5px; width:15%">节点标识</td>
			<td style="padding: 5px; width:18%">节点名称</td>
			<td style="padding: 5px; width:50%">人员设置</td>
			<td style="padding: 5px; width:12%">指定全体人员</td>
		</tr>
		<c:forEach items="${list }" var="item" varStatus="status">
			<tr <c:if test="${item.nodeType eq 'ParallelMultiInstanceBehavior'}">sign title="会签节点"</c:if>>
				<td style="padding: 5px;">${status.index + 1 }</td>
				<td style="padding: 5px;">${item.key }</td>
				<td style="padding: 5px;"><c:out value="${item.name }"/><c:if test="${status.index eq 0}" ><span style="color: #F00;">（新建工作）</span></c:if></td>
				<td>
					<div style="padding:3px;" class="rule_aud<c:if test="${item.nodeType eq 'ParallelMultiInstanceBehavior'}">_sign </c:if> rule_aud<c:if test="${item.nodeType eq 'ParallelMultiInstanceBehavior'}">_sign </c:if>_${item.key }" data="${item.key }" <c:if test="${status.index eq 0}" >first="true"</c:if>>
						<c:choose>
							<c:when test="${item.nodeType eq 'ParallelMultiInstanceBehavior'}">
								<div class="rule_aud_tips_${item.key }"><a href="javascript:void(0);" onclick="javascript:$('#activiti-tabs-definitionSettingPage').tabs('select', '会签设置');">请在会签页进行人员设置。</a></div>
							</c:when>
							<c:otherwise>
								<div class="rule_aud_tips_${item.key }">加载中...</div>
							</c:otherwise>
						</c:choose>
					</div>
					<div style="background:#dff5fd;">
						<a class="easyui-linkbutton" id="activiti-button-aud-${item.key }-definitionUserSettingPage" onclick="setAuth('aud', '${param.definitionkey }', '${item.key }', false<c:if test="${status.index eq 0}" > || true</c:if>, '<c:out value="${item.name }"/>')" data-options="plain:true,iconCls:'button-setting',disabled:true" style="text-indent:0px;">设置</a>
						<a class="easyui-linkbutton" id="activiti-button-aud-${item.key }-clear-definitionUserSettingPage" onclick="clearAuth({definitionkey: '${param.definitionkey }', taskkey: '${item.key }', type: 'aud'})" data-options="plain:true,iconCls:'button-close',disabled:true" style="text-indent:0px;">清除规则</a>
					</div>
				</td>
				<td>
					<label><input type="checkbox" name="${item.key }" id="${item.key }" onclick="javascript:setCanassign('${item.key }')" <c:if test="${item.nodeType eq 'ParallelMultiInstanceBehavior'}">disabled="disabled"</c:if>/>允许</label>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div id="activiti-dialog1-definitionUserSettingPage" style="padding:5px;"></div>
	<div id="activiti-dialog2-definitionUserSettingPage" style="padding:5px;"></div>
	<script type="text/javascript">

    <!--

        var $dialog2;

		var currentSetTaskKey = '';
		var currentSetTaskName = '';
		var currentSetType = '';
		$(function(){

			$('.detaildescription').each(function() {

				var userid = $(this).next().val();
				var deptid = $(this).next().next().val();
				var roleid = $(this).next().next().next().val();
				var postid = $(this).next().next().next().next().val();

				$('#activiti-dummy1-definitionUserSettingPage').val(userid);
				$('#activiti-dummy1-definitionUserSettingPage').widget({
					referwid: 'RT_T_SYS_USER',
	  				singleSelect: false,
	  				treePName: false
				});
				$('#activiti-dummy2-definitionUserSettingPage').val(deptid);
				$('#activiti-dummy2-definitionUserSettingPage').widget({
					referwid: 'RT_T_SYS_DEPT',
	  				singleSelect: false,
	  				treePName: false
				});
				$('#activiti-dummy3-definitionUserSettingPage').val(roleid);
				$('#activiti-dummy3-definitionUserSettingPage').widget({
					referwid: 'RL_T_AC_AUTHROITY',
	  				singleSelect: false,
	  				treePName: false
				});
				$('#activiti-dummy4-definitionUserSettingPage').val(postid);
				$('#activiti-dummy4-definitionUserSettingPage').widget({
					referwid: 'RL_T_BASE_WORKJOB',
	  				singleSelect: false,
	  				treePName: false
				});

				var user = $('#activiti-dummy1-definitionUserSettingPage').widget('getText');
				var dept = $('#activiti-dummy2-definitionUserSettingPage').widget('getText');
				var role = $('#activiti-dummy3-definitionUserSettingPage').widget('getText');
				var post = $('#activiti-dummy4-definitionUserSettingPage').widget('getText');

				$('#activiti-dummy1-definitionUserSettingPage').val('');
				$('#activiti-dummy2-definitionUserSettingPage').val('');
				$('#activiti-dummy3-definitionUserSettingPage').val('');
				$('#activiti-dummy4-definitionUserSettingPage').val('');

				var detail = new Array();
				if(dept != '' && dept != null) {
					detail.push(dept);
				}
				if(role != '' && role != null) {
					detail.push(role);
				}
				if(post != '' && post != null) {
					detail.push(post);
				}
				if(user != '' && user != null) {
					detail.push(user);
				}

				$(this).text(detail.join('：'));

			});

			// 加载各节点审批人员设定
			$('.rule_aud').each(function() {

				var taskkey = $(this).attr('data');
				var definitionkey = '${param.definitionkey }';
				var type = 'aud';
                var first = $(this).is('[first]');

				var p = {definitionkey: definitionkey, taskkey: taskkey, type: type, first: first};
				initUserTask(p);
				initCanAssign(p);

			});

			initUserTask({definitionkey: '${param.definitionkey }', taskkey: '', type: 'add'});
		});

		function setAuth(type, definitionkey, taskkey, first, taskname){

			currentSetType = type;

            var did = 'd' + getRandomid();
            $("#activiti-dialog2-definitionUserSettingPage").after('<div id="activiti-' + did + '-definitionUserSettingPage"></div>');
            $dialog2 = $('#activiti-' + did + '-definitionUserSettingPage');

            $dialog2.dialog({
                width:620,
                height:450,
                title:'人员设置[' + taskname + ']',
                cache:false,
                modal:true,
                closed:true,
                maximized:true,
                href:'act/definitionUserSettingPage3.do?type=' + type + '&definitionkey=' + definitionkey + '&taskkey=' + taskkey + '&first=' + first,
                onClose: function() {

                    $dialog2.dialog('destroy');
                }
            }).dialog('open');
		}

		function setCanassign(taskkey) {

			var checked = '0';
			if($('#' + taskkey)[0].checked) {
				checked = '1'
			}

			loading('设置中...');
			$.ajax({
				dataType: 'json',
				type: 'post',
				url: 'act/setCanassign.do',
				data: {definitionkey: '${param.definitionkey }', canassign: checked, taskkey: taskkey},
				success: function(json) {

					loaded();
					if(json.flag == false) {

						$.messager.alert("提醒", "设置失败！");
						// $('#' + taskkey)[0].checked = !$('#' + taskkey)[0].checked;
						if(checked == '0') {
							$('#' + taskkey).attr('checked', 'checked');
						} else {
							$('#' + taskkey).removeAttr('checked');
						}
						return true;
					}

					$.messager.alert("提醒", "设置成功。");
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {

					loaded();
					$.messager.alert("提醒", "设置失败！");
				}

			});

			return true;
		}

		function setRuleUser(rule){
			var user = dept = role = post = "";
			var ruleDetail = detail = "";

			if(rule == "assignee"){
				user = $("#activiti-assignee-definitionUserSettingPage2").widget("getValue");
				if(user == "") return ;
				ruleDetail = "指定人员";
				detail = $("#activiti-assignee-definitionUserSettingPage2").widget("getText");
			}
			if(rule == "departmentRole"){
				role = $("#activiti-role-definitionUserSettingPage2").widget("getValue");
				if(role == "") return ;
				ruleDetail = "本部门指定角色";
				detail = $("#activiti-role-definitionUserSettingPage2").widget("getText");
			}
			if(rule == "department"){
				ruleDetail = "本部门所有人";
			}
			if(rule == "oneRole"){
				role = $("#activiti-role2-definitionUserSettingPage2").widget("getValue");
				if(role == "") return ;
				ruleDetail = "指定角色";
				detail = $("#activiti-role2-definitionUserSettingPage2").widget("getText");
			}
			if(rule == "oneDepartment"){
				dept = $("#activiti-department-definitionUserSettingPage2").widget("getValue");
				if(dept == "") return ;
				ruleDetail = "指定部门";
				detail = $("#activiti-department-definitionUserSettingPage2").widget("getText");
			}
			if(rule == "oneDepartmentOneRole"){
				dept = $("#activiti-department2-definitionUserSettingPage2").widget("getValue");
				role = $("#activiti-role3-definitionUserSettingPage2").widget("getValue");
				if(dept == "" || role == "") return ;
				ruleDetail = "指定部门与角色";
				detail = $("#activiti-department2-definitionUserSettingPage2").widget("getText")+ "："+ $("#activiti-role3-definitionUserSettingPage2").widget("getText");
			}
			if(rule == "onePost"){
				post = $("#activiti-post-definitionUserSettingPage2").widget("getValue");
				if(post == "") return ;
				ruleDetail = "指定岗位";
				detail = $("#activiti-post-definitionUserSettingPage2").widget("getText");
			}
			loading("人员设置中...");
			$.ajax({
				url:"act/addDefinitionUser.do",
				type:"post",
				dataType:"json",
				data:"definitionTask.definitionkey=${param.definitionkey }&definitionTask.taskkey="+ currentSetTaskKey+ "&definitionTask.taskname="+ currentSetTaskName+ "&definitionTask.rule="+ rule+ "&definitionTask.user="+ user+ "&definitionTask.dept="+ dept+ "&definitionTask.role="+ role+ "&definitionTask.post="+ post+ "&definitionTask.rulename="+ ruleDetail+ "&definitionTask.ruledetail="+ detail,
				success:function(json){
					loaded();
					if(json.flag == true){

						// 设定成功时，调用initUserTask刷新当前用户节点配置
						initUserTask({definitionkey: '${param.definitionkey}', taskkey: currentSetTaskKey, type: 'aud'});
						$.messager.alert("提醒", "设置成功");
						$('#activiti-userset-definitionSettingPage').click();
						return true;

						// 以下代码无效
						$(".rule_aud_"+ currentSetTaskKey).text(ruleDetail);
						$(".detail_aud_"+ currentSetTaskKey).text(detail);
						$.messager.alert("提醒", "设置成功");
						$('#activiti-userset-definitionSettingPage').click();
					}
					else{
						$.messager.alert("提醒", "设置失败");
					}
				}
			});
		}

		function setAuthRuleUser(type, rule){
			var user = dept = role = post = "";
			var ruleDetail = detail = "";
			if(rule == "01"){
				user = $("#activiti-assignee-definitionUserSettingPage3").widget("getValue");
				if(user == "") return ;
				ruleDetail = "指定人员";
				detail = $("#activiti-assignee-definitionUserSettingPage3").widget("getText");
			}
			if(rule == "02"){
				role = $("#activiti-role-definitionUserSettingPage3").widget("getValue");
				if(role == "") return ;
				ruleDetail = "本部门指定角色";
				detail = $("#activiti-role-definitionUserSettingPage3").widget("getText");
			}
			if(rule == "03"){
				ruleDetail = "本部门所有人";
			}
			if(rule == "04"){
				role = $("#activiti-role2-definitionUserSettingPage3").widget("getValue");
				if(role == "") return ;
				ruleDetail = "指定角色";
				detail = $("#activiti-role2-definitionUserSettingPage3").widget("getText");
			}
			if(rule == "05"){
				dept = $("#activiti-department-definitionUserSettingPage3").widget("getValue");
				if(dept == "") return ;
				ruleDetail = "指定部门";
				detail = $("#activiti-department-definitionUserSettingPage3").widget("getText");
			}
			if(rule == "06"){
				dept = $("#activiti-department2-definitionUserSettingPage3").widget("getValue");
				role = $("#activiti-role3-definitionUserSettingPage3").widget("getValue");
				if(dept == "" || role == "") return ;
				ruleDetail = "指定部门与角色";
				detail = $("#activiti-department2-definitionUserSettingPage3").widget("getText")+ "："+ $("#activiti-role3-definitionUserSettingPage3").widget("getText");
			}
			if(rule == "07"){
				post = $("#activiti-post-definitionUserSettingPage3").widget("getValue");
				if(post == "") return ;
				ruleDetail = "指定岗位";
				detail = $("#activiti-post-definitionUserSettingPage3").widget("getText");
			}

			loading("人员设置中...");
			var definitionkey = '${param.definitionkey }';
			$.ajax({
				url:'act/addAuthRule.do',
				type:'post',
				dataType:'json',
				data:{definitionkey: definitionkey, type: type, rule: rule, userid: user, deptid: dept, roleid: role, postid: post},
				success:function(json){
					loaded();
					if(json.flag == true){
						$('.rule_' + type + '_').text(ruleDetail);
						$('.detail_' + type + '_').text(detail);
						$.messager.alert("提醒", "设置成功");
					}
					else{
						$.messager.alert("提醒", "设置失败");
					}
					$("#activiti-dialog2-definitionUserSettingPage").dialog("close");
				}
			});
		}

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

							$('<input type="text" id="activiti-dummy1-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage"/>').appendTo($('div#dummy'));
							$('<input type="text" id="activiti-dummy2-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage"/>').appendTo($('div#dummy'));
							$('<input type="text" id="activiti-dummy3-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage"/>').appendTo($('div#dummy'));
							$('<input type="text" id="activiti-dummy4-' + definitionkey + '-' + taskkey + '-' + type + '-' + rule + '-definitionUserSettingPage"/>').appendTo($('div#dummy'));

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

                    if(type == 'aud' && !p.first) {

                        html.push('<div style="background: #dddddd; margin: 1px; padding-left: 5px;">按发起人规则</div>');

                        if (json.mappings == 0) {
                            html.push('<div class="activiti-rule-' + definitionkey + '-' + taskkey + '-' + type + '" style="border: 1px solid #ccc; margin: 1px;">');
                            html.push('无指定规则');
                            html.push('</div>');
                        } else {
                            html.push('<div class="activiti-rule-' + definitionkey + '-' + taskkey + '-' + type + '" style="border: 1px solid #ccc; margin: 1px;">');
                            html.push('<div style="color: #f00;">规则数：' + json.mappings + '</div>');
                            html.push('<div>点击设置查看详细规则</div>');
                            html.push('</div>');
                        }

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

		// 初始化该节点的“能否指定人员”设定
		function initCanAssign(p) {

			$.ajax({
				type: 'post',
				dataType: 'json',
				url: 'act/getCanassignSetting.do',
				data: p,
				success: function(json) {

					if(json.canassign == '1') {

						$('input[name=' + json.taskkey + ']').attr('checked', 'checked');
					} else {

						$('input[name=' + json.taskkey + ']').removeAttr('checked');
					}
				}
			});
		}

		function clearAuth(p) {

			$.ajax({
				type: 'post',
				dataType: 'json',
				url: 'act/clearAuthRule.do',
				data: p,
				success: function(json) {

					$.messager.alert("提醒", "设置成功。");
					initUserTask(p);
				},
				error: function() {
					$.messager.alert("警告", "设置失败！");
				}
			});
		}

    -->

	</script>
  </body>
</html>
