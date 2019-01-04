<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>打印任务列表</title>
	<%@include file="/include.jsp" %>
	<script src="js/jsonformat/c.js" type="text/javascript"></script>
	<link href="js/jsonformat/s.css" type="text/css" rel="stylesheet"></link>
</head>

<body>
<table id="tplprintjob-table-printJobList"></table>
<div id="tplprintjob-query-printJobList" style="padding:0px;height:auto">
	<div class="buttonBG" id="agprint-button-printJob"></div>
	<form action="" id="tplprintjob-form-printJobList" method="post" style="padding-top: 2px;">
		<table>
			<tr>
				<td>名称:</td>
				<td><input type="text" name="jobname" style="width:180px" /></td>
				<td>状态:</td>
				<td>
					<select name="state" style="width:100px">
						<option></option>
						<option value="0">申请</option>
						<option value="1">打印</option>
						<option value="2">次数更新成功</option>
					</select>
				</td>
				<td>
					<a href="javaScript:void(0);" id="tplprintjob-query-queryPrintJobList" class="button-qr">查询</a>
					<a href="javaScript:void(0);" id="tplprintjob-query-reloadPrintJobList" class="button-qr">重置</a>
				</td>
			</tr>
		</table>
	</form>
</div>
<div style="display:none">
	<div id="tplprintjob-dialog-printJobOper" ></div>
	<div id="tplprintjob-dialog-printJobOper-showrequestparam">
		<table>
			<tr>
				<td>格式化前：</td>
				<td>
					<input type="text" id="tplprintjob-dialog-printJobOper-showrequestparam-before" style="width:350px" readonly="readonly" class="readonly" />
				</td>
			</tr>
			<tr>
				<td>格式化后：</td>
				<td>
					<textarea id="tplprintjob-dialog-printJobOper-showrequestparam-after" rows="0" cols="0" style="width:350px;height:180px;overflow: :auto;" disabled="disabled" readonly="readonly"></textarea>
				</td>
			</tr>
		</table>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$("#agprint-button-printJob").buttonWidget({
			//初始默认按钮 根据type对应按钮事件
			initButton:[
				{}
			],
			buttons:[
				<security:authorize url="/agprint/tplprintjob/showPrintJobDetailListPageBtn.do">
				{
					id:'jobbutton-id-viewcontent',
					name:'查看打印内容信息',
					iconCls:'button-view',
					handler:function(){
						var dataRow=$("#tplprintjob-table-printJobList").datagrid('getSelected');
						if(dataRow==null){
							$.messager.alert("提醒","请选择相关的打印任务信息!");
							return false;
						}
						var title="查看打印内容信息";
						title=title+(dataRow.jobname || "");
						title=title+(dataRow.orderidarr || "");
						var onLoadFunc=function(){
							try{
								showPrintJobDetailDatagrid();
							}catch(ex){
							}
						};
						printJobOpenDialog(title, 'agprint/tplprintjob/showPrintJobDetailListPage.do?jobid='+dataRow.id,onLoadFunc);
					}
				},
				</security:authorize>
				<security:authorize url="/agprint/tplprintjob/showPrintJobCallHandleListPageBtn.do">
				{
					id:'jobbutton-id-viewcallhandle',
					name:'查看回调信息',
					iconCls:'button-view',
					handler:function(){
						var dataRow=$("#tplprintjob-table-printJobList").datagrid('getSelected');
						if(dataRow==null){
							$.messager.alert("提醒","请选择相关的打印任务信息!");
							return false;
						}
						var title="查看回调信息";
						title=title+(dataRow.jobname || "");
						title=title+(dataRow.orderidarr || "");
						var onLoadFunc=function(){
							try{
								showPrintJobCallHandleDatagrid();
							}catch(ex){

							}
						};
						printJobOpenDialog(title, 'agprint/tplprintjob/showPrintJobCallHandleListPage.do?jobid='+dataRow.id,onLoadFunc);
					}
				},
				</security:authorize>
				{}
			],
			model:'base',
			type:'list',
			datagrid:'tplprintjob-table-printJobList',
			tname:'t_print_job'
			//id:''
		});



		$("#tplprintjob-table-printJobList").datagrid({
			fit:true,
			method:'post',
			title:'',
			rownumbers:true,
			pagination:true,
			idField:'id',
			sortName : 'addtime',
			sortOrder : 'desc',
			singleSelect:true,
			checkOnSelect:true,
			selectOnCheck:true,
			toolbar:'#tplprintjob-query-printJobList',
			url:'agprint/tplprintjob/showPrintJobPageListData.do',
			columns:[[
				{field:'idok',checkbox:true,isShow:true},
				{field:'id',title:'编号',width:150},
				{field:'jobname',title:'名称',width:180},
				{field:'orderidarr',title:'申请打印单据号',width:140},
				{field:'requestparam',title:'请求参数',width:100,
					formatter:function(value,rowData,rowIndex){
						var tmps="<input id=\"reqparam_"+rowData.id+"\" type=\"hidden\" value=\""+value+"\">";
						tmps+="<a href=\"javascript:void(0);\" onclick=\"javascript:showRequestParamDailog('"+rowData.id+"');\">点击查看</a>";
						return tmps;
					}
				},
				{field:'status',title:'状态',width:100,
					formatter:function(val){
						if(val=="0"){
							return "申请";
						}else if(val=="1"){
							return "打印";
						}else if(val=="2"){
							return "次数更新成功";
						}
					}
				},
				{field:'adduserid',title:'添加者编号',width:80,hidden:true},
				{field:'addusername',title:'添加者名称',width:100},
				{field:'addtime',title:'添加者时间',width:130},
				{field:'modifyuserid',title:'修改者编号',width:80,hidden:true},
				{field:'modifyusername',title:'修改者名称',width:100},
				{field:'modifytime',title:'修改者时间',width:130},
				{field:'remark',title:'备注',width:150}
			]]
		});
		//回车事件
		controlQueryAndResetByKey("tplprintjob-query-queryPrintJobList","tplprintjob-query-reloadPrintJobList");

		//查询
		$("#tplprintjob-query-queryPrintJobList").click(function(){
			//把form表单的name序列化成JSON对象
			var queryJSON = $("#tplprintjob-form-printJobList").serializeJSON();
			$("#tplprintjob-table-printJobList").datagrid("load",queryJSON);
		});
		//重置
		$('#tplprintjob-query-reloadPrintJobList').click(function(){
			$("#tplprintjob-form-printJobList")[0].reset();
			$("#tplprintjob-table-printJobList").datagrid('loadData',{total:0,rows:[]});
		});

	});
	function showRequestParamDailog(id){
		if(id==null || $.trim(id)==""){
			return false;
		}
		var value=$("#reqparam_"+id).val() || "";
		var $before=$("#tplprintjob-dialog-printJobOper-showrequestparam-before");
		var $after=$("#tplprintjob-dialog-printJobOper-showrequestparam-after");
		$before.val(value);
		var valarr=value.split('&');
		$after.val(valarr.join('\n'));

		$('#tplprintjob-dialog-printJobOper-showrequestparam').dialog({
			title: "查看请求参数",
			//fit:true,
			width:460,
			height:300,
			closed: true,
			cache: false,
			maximizable:true,
			resizable:true,
			modal: true,
			onLoad:function(){
			}
		});
		$('#tplprintjob-dialog-printJobOper-showrequestparam').dialog('open');
	}

	function printJobOpenDialog(title,url,onLoadFunc){
		$('<div id="tplprintjob-dialog-printJobOper-content"></div>').appendTo("#tplprintjob-dialog-printJobOper");
		$('#tplprintjob-dialog-printJobOper-content').dialog({
			title: title,
			fit:true,
			//width:460,
			//height:300,
			cache: false,
			href: url,
			closed: true,
			cache: false,
			maximizable:true,
			resizable:true,
			modal: true,
			onLoad:function(){
				if(onLoadFunc!=null && typeof(onLoadFunc) == "function" ){
					onLoadFunc();
				}
			},
			onClose:function(){
				$('#tplprintjob-dialog-printJobOper-content').dialog("destroy");
			}
		});
		$('#tplprintjob-dialog-printJobOper-content').dialog('open');
	}

</script>
</body>
</html>
