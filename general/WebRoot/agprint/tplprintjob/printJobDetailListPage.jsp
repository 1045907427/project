<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>打印任务单据页面列表</title>
    <%@include file="/include.jsp" %> 
  </head>
  
  <body>
  	<table id="tplprintjob-table-printJobDetailList"></table>
   	<div id="tplprintjob-query-printJobDetailList" style="padding:0px;height:auto">
 		<div class="buttonBG" id="agprint-button-printJobDetailList"></div>
		<form action="" id="tplprintjob-form-printJobDetailList" method="post" style="padding-top: 2px;">
			<table>
			   <tr>
			   		<td>单据号:</td>
					<td><input type="text" name="printorderid" style="width:180px" /></td>
				   <td>备注:</td>
				   <td><input type="text" name="remark" style="width:180px" /></td>
		   			<td>
		   				<a href="javaScript:void(0);" id="tplprintjob-query-queryPrintJobDetailList" class="button-qr">查询</a>
    					<a href="javaScript:void(0);" id="tplprintjob-query-reloadPrintJobDetailList" class="button-qr">重置</a>
		   			</td>
				</tr>
			</table>
			<input type="hidden" id="tplprintjob-printJobDetailList-jobid" name="jobid" value="${param.jobid}"/>
  		</form>
   	</div>
   	<div style="display:none">
   		<div id="tplprintjob-dialog-printJobDetailOper" ></div>
   	</div>
   	<script type="text/javascript">
   		$(function(){
			$("#agprint-button-printJobDetailList").buttonWidget({
	 			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{}
	 			],
				buttons:[
					<security:authorize url="/agprint/tplprintjob/showPrintJobDetailContentBtn.do">
					{
						id:'jobdetail-id-view',
						name:'查看内容',
						iconCls:'button-view',
						handler:function(){

							var dataRow=$("#tplprintjob-table-printJobDetailList").datagrid('getSelected');
							if(dataRow==null){
								$.messager.alert("提醒","请选择相关的打印任务内容信息!");
								return false;
							}
							var title="查看打印任务内容信息";
							title=title+(dataRow.jobid || "");

							if(dataRow.contenttype=='html' || dataRow.contenttype=='text') {
								printJobDetailContentOpenDialog(title, dataRow.id);
							}
						}
					},
					</security:authorize>
					{}		
				],
	 			model:'base',
				type:'list',
				datagrid:'tplprintjob-table-printJobDetailList',
				tname:'t_print_job_detail'
				//id:''
	 		});
   			

   			//回车事件
			controlQueryAndResetByKey("tplprintjob-query-queryPrintJobDetailList","tplprintjob-query-reloadPrintJobDetailList");
   			
   			//查询
  			$("#tplprintjob-query-queryPrintJobDetailList").click(function(){
  				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#tplprintjob-form-printJobDetailList").serializeJSON();
	       		$("#tplprintjob-table-printJobDetailList").datagrid("load",queryJSON);
  			});
  			//重置
  			$('#tplprintjob-query-reloadPrintJobDetailList').click(function(){
  				$("#tplprintjob-form-printJobDetailList")[0].reset();
	       		$("#tplprintjob-table-printJobDetailList").datagrid('loadData',{total:0,rows:[]});
  			});
			setTimeout(function(){
				$(".menu-sep").hide();
			},10);
			//showPrintJobDetailDatagrid();
   		});

		function showPrintJobDetailDatagrid(){

			var queryFormJSON = $("#tplprintjob-form-printJobDetailList").serializeJSON();
			$("#tplprintjob-table-printJobDetailList").datagrid({
				fit:true,
				method:'post',
				title:'',
				rownumbers:true,
				pagination:true,
				idField:'id',
				sortName : 'addtime',
				sortOrder : 'desc',
				singleSelect:true,
				toolbar:'#tplprintjob-query-printJobDetailList',
				url:'agprint/tplprintjob/showPrintJobDetailPageListData.do',
				queryParams : queryFormJSON,
				columns:[[
					{field:'id',title:'编号',width:200,hidden:true},
					{field:'jobid',title:'任务编号',width:200},
					{field:'printorderid',title:'打印的单据编号',width:140},
					{field:'currentpage',title:'当前页数',width:60},
					{field:'totalpage',title:'总共页数',width:60},
					{field:'content',title:'内容',width:100,
						formatter:function(value,dataRow,rowIndex){
							if(dataRow.contenttype=='html' || dataRow.contenttype=='text'){
								var tmps="<a href=\"javascript:void(0);\" onclick=\"javascript:printJobDetailContentOpenDialog('查看打印任务内容','"+dataRow.id+"');\">点击查看</a>";
								return tmps;
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
		}

		function printJobDetailContentOpenDialog(title,id){
			if(title==null){
				title="查看打印任务内容";
			}
			if(id==null || ""==id){
				$.messager.alert("提醒","抱歉，未找到相关打印任务内容信息!");
				return false;
			}
			var url='agprint/tplprintjob/getPrintJobDetailContentData.do?viewtype=justview&jobdetailid='+id;
			var iframe= $('<iframe></iframe>').attr('height', '100%').attr('width', '100%').attr('marginheight', '0').attr('marginwidth', '0').attr('frameborder','0');
			setTimeout(function(){
				iframe.attr('src', url);
			}, 1);
			$('<div id="tplprintjob-dialog-printJobDetailOper-content"></div>').appendTo("#tplprintjob-dialog-printJobDetailOper");
			$('#tplprintjob-dialog-printJobDetailOper-content').dialog({
				title: title,
				fit:true,
				//width:460,
				//height:300,
				cache: false,
				content:iframe,
				closed: true,
				cache: false,
				maximizable:true,
				resizable:true,
				modal: true,
				onClose:function(){
					$('#tplprintjob-dialog-printJobDetailOper-content').dialog("destroy");
				}
			});
			$('#tplprintjob-dialog-printJobDetailOper-content').dialog('open');
		}
   	</script>
  </body>
</html>
