<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>Handler日志查询</title>
    <%@include file="/include.jsp" %>
	<style type="text/css">
		.len300 {
			width: 300px;
		}
	</style>
</head>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
	<%--<div id="activiti-datagrid-handlerLogListPage"></div>--%>
	<div id="activiti-div-handlerLogListPage" style="padding: 2px;">
		<form id="activiti-form-handlerLogListPage">
			<table>
				<tr>
					<td class="len40">OA编号：</td>
					<td class="len120"><input type="text" name="processid" id="activiti-processid-handlerLogListPage" class="len100" autocomplete="off"/></td>
					<td class="len70 right">执行类：</td>
					<td class="len150">
						<select name="clazz" id="activiti-clazz-handlerLogListPage" class="len300">
							<option></option>
							<c:forEach items="${items }" var="item" varStatus="status">
								<option value="${item.clazz}"><c:out value="${item.handler}"/>： <c:out value="${item.handlerdescription }"/></option>
							</c:forEach>
						</select>
					</td>
					<td colspan="2" align="right">
						<div>
							<a href="javascript:;" id="activiti-query-handlerLogListPage" class="button-qr">查询</a>
							<a href="javaScript:;" id="activiti-resetQueay-handlerLogListPage" class="button-qr">重置</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<table id="activiti-datagrid-handlerLogListPage"></table>
</div>

<script type="text/javascript">
	<!--

	$(function() {

		var logCols = $("#activiti-datagrid-handlerLogListPage").createGridColumnLoad({
//			name: 't_act_process',
			frozenCol: [[]],
			commonCol: [[
				{field:'processid', title:'OA号', width: 50, hidden: true},
				{field:'processname', title:'OA标题', width: 380},
				{field:'taskname', title:'节点名称', width: 100},
				{field:'definitionid', title:'流程版本', hidden: true},
				{field:'handler', title:'Handler'},
<%--				{field:'clazz', title:'Class类', hidden: true}, --%>
				{field:'handlerdescription', title:'描述' },
				{field:'logusername', title:'执行人'},
				{field:'logtime', title:'执行时间'},
				{field:'operate', title:'', width: 50, aliasCol: 'id',
					formatter: function(value, row, index){
						return '<a href="javascript:void(0);" onclick="viewLogInfo(' + row.id + ')">查看</a>';
					}
				}
			]]
		});

		$("#activiti-datagrid-handlerLogListPage").datagrid({
			authority: logCols,
			frozenColumns: logCols.frozen,
			columns: logCols.common,
			fit: true,
			border: false,
			method: 'post',
			rownumbers: true,
			pagination: true,
			singleSelect: true,
			toolbar: '#activiti-div-handlerLogListPage',
			url: 'act/getHandlerLogList.do'
		});
		$("#activiti-query-handlerLogListPage").click(function(){
			var queryJSON = $('#activiti-form-handlerLogListPage').serializeJSON();
			$('#activiti-datagrid-handlerLogListPage').datagrid('load', queryJSON);
		});

		// 重置
		$("#activiti-resetQueay-handlerLogListPage").click(function(){
            $('#activiti-form-handlerLogListPage')[0].reset();
            $('#activiti-form-handlerLogListPage').form('clear');
            var queryJSON = $('#activiti-form-handlerLogListPage').serializeJSON();
			$('#activiti-datagrid-handlerLogListPage').datagrid('load', queryJSON);
		});

	});

	function viewLogInfo(id) {

		var did = 'd' + getRandomid();
		$('body').append('<div id="' + did + '"></div>');
		$('#' + did).dialog({
			title: 'Handler 日志详情',
			width: 400,
			height: 200,
			closed: false,
			cache: false,
			maximized: true,
			href: 'act/handlerLogInfoPage.do',
			queryParams: {id: id},
			modal: true,
			onClose: function() {
				$('#' + did).dialog('destroy');
			}
		});
	}

	-->
</script>

</body>
</html>