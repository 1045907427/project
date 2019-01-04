<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>流程定义管理-设置（主）</title>
  </head>
  
  <body>
  	<style type="text/css">
		.detailTable {
			border-collapse: collapse;
			border: 1px solid #7babcf;
			width: 100%;
		}

		.detailTable td {
			border: 1px solid #7babcf;
			line-height: 28px;
			text-indent: 5px;
		}

		.detailTable_td1 {
			width: 150px;
			text-align: right;
			background: #ebf5ff;
		}
  	</style>
	<div id="activiti-tabs-definitionSettingPage" class="easyui-tabs" data-options="fit:true,border:false">
		<div title="流程明细" style="padding:5px;">
			<table cellpadding="0" class="detailTable">
				<tr>
					<td class="detailTable_td1">流程分类：</td>
					<td>
						<span id="activiti-span1-definitionSettingPage">${definition.definitionType.name }</span>
						<span id="activiti-span2-definitionSettingPage" style="display:none;">
							<select id="activiti-typeList-definitionSettingPage">
								<option value="">请选择</option>
								<c:forEach items="${typeList }" var="list">
									<c:choose>
										<c:when test="${list.unkey == definition.type}">
											<option value="${list.unkey }" selected="selected">${list.name }</option>
										</c:when>
										<c:otherwise>
											<option value="${list.unkey }">${list.name }</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</span>
						<a class="easyui-linkbutton" id="activiti-typeSet-definitionSettingPage" data-options="plain:true,iconCls:'button-setting'" style="text-indent:0;" >设置</a>
						<span id="activiti-span3-definitionSettingPage" style="display: none;"><img src="image/loading.gif" style="position:relative;top:3px;" /></span>
					</td>
				</tr>
				<tr>
					<td class="detailTable_td1">流程名称：</td>
					<td>${definition.name }</td>
				</tr>
				<tr>
					<td class="detailTable_td1">流程定义Key：</td>
					<td>${definition.unkey }</td>
				</tr>
				<tr>
					<td class="detailTable_td1">创建人：</td>
					<td>${definition.addusername }</td>
				</tr>
				<tr>
					<td class="detailTable_td1">创建时间：</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${definition.adddate }"/></td>
				</tr>
				<tr>
					<td class="detailTable_td1">更新人：</td>
					<td>${definition.modifyusername }</td>
				</tr>
				<tr>
					<td class="detailTable_td1">更新时间：</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${definition.modifytime }"/></td>
				</tr>
				<tr>
					<td class="detailTable_td1">布署状态：</td>
					<td>
						<c:if test="${definition.isdeploy == 1}">
							<font color="green"><b>已布署</b></font>
							<c:if test="${definition.ismodify == 1}">
								<img src="image/btn/info_w64.png" style='width:16px;height:16px;position:relative;top:4px;' title='有修改但未重新布署' />
							</c:if>
						</c:if>
						<c:if test="${definition.isdeploy == 0}">未布署</c:if>
					</td>
				</tr>
				<tr>
					<td class="detailTable_td1">流程状态：</td>
					<td>
						<c:if test="${definition.state == 1 }"><font color="green"><b>启用</b></font></c:if>
						<c:if test="${definition.state == 0}">禁用</c:if>
					</td>
				</tr>
				<tr>
					<td class="detailTable_td1">当前使用版本号：</td>
					<td>${definition.version }</td>
				</tr>
				<tr>
					<td class="detailTable_td1">当前使用版本定义ID：</td>
					<td>${definition.definitionid }</td>
				</tr>
			</table>
		</div>
		<div title="人员设置" style="padding:5px;" data-options="href:'act/definitionUserSettingPage.do?definitionkey=${definition.unkey }'"></div>
		<div title="表单管理" style="padding:5px;" data-options="href:'act/definitionFormSettingPage.do?definitionkey=${definition.unkey }'"></div>
		<div title="会签设置" style="padding:5px;" data-options="href:'act/signTaskSettingPage.do?definitionkey=${definition.unkey }'"></div>
        <div title="触发器" style="padding:5px;" data-options="href:'act/definitionHandlerSettingPage.do?definitionkey=${definition.unkey }'">
        </div>
        <div title="其他设置" style="padding:5px;" data-options="cache:false,href:'act/definitionOtherSettingPage.do?definitionkey=${definition.unkey }'">
        </div>
        <div title="历史版本" style="padding:5px;" data-options="href:'act/definitionHistoryListPage.do?definitionkey=${definition.unkey }'"></div>
        <div title="流程示图">
            <div id="activiti-diagram-definitionSettingPage" style="padding:5px;">
                <img src="image/loading.gif" style="vertical-align: top" /> 流程示图加载中...
            </div>
        </div>
	</div>
	<script type="text/javascript">
		$(function(){
			$.ajax({
				url:'act/getDefinitionDiagram.do',
				type:'post',
				dataType:'json',
				data:'definitionkey=${definition.unkey}',
				success:function(json){
					$("#activiti-diagram-definitionSettingPage").html("<img src='activiti/diagram/"+json.path+"' />");
				}
			});
			$("#activiti-typeSet-definitionSettingPage").click(function(){
				var text = $(this).linkbutton('options').text;
				if(text == "设置"){
					$("#activiti-span1-definitionSettingPage").hide();
					$("#activiti-span2-definitionSettingPage").show();
					$(this).linkbutton({text:"保存"});
				}
				else if(text == "保存"){
					$("#activiti-span3-definitionSettingPage").show();
					var key = $("#activiti-typeList-definitionSettingPage").val();
					var name = $("#activiti-typeList-definitionSettingPage option:selected").text();
					if(key == ""){
						name = "";
						$("#activiti-span1-definitionSettingPage").text(name).show();
						$("#activiti-span2-definitionSettingPage").hide();
						$("#activiti-span3-definitionSettingPage").hide();
						$(this).linkbutton({text:"设置"});
                        $.ajax({
                            url:'act/setDefinitionType.do',
                            dataType:'json',
                            type:'post',
                            data:'definitionkey=${definition.unkey}&type='+ key,
                            success:function(json){
                                if(json.flag == true){
                                    $("#activiti-span1-definitionSettingPage").text(name).show();
                                    $("#activiti-span2-definitionSettingPage").hide();
                                    $("#activiti-span3-definitionSettingPage").hide();
                                    $("#activiti-typeSet-definitionSettingPage").linkbutton({text:"设置"});
                                }
                            }
                        });
					}
					else{
						$.ajax({
							url:'act/setDefinitionType.do',
							dataType:'json',
							type:'post',
							data:'definitionkey=${definition.unkey}&type='+ key,
							success:function(json){
								if(json.flag == true){
									$("#activiti-span1-definitionSettingPage").text(name).show();
									$("#activiti-span2-definitionSettingPage").hide();
									$("#activiti-span3-definitionSettingPage").hide();
									$("#activiti-typeSet-definitionSettingPage").linkbutton({text:"设置"});
								}
							}
						});
					}
				}
			});
		});
	</script>
  </body>
</html>
