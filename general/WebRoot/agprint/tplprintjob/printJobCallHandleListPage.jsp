<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>打印任务调用列表</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  <table id="tplprintjob-table-printJobCallHandleList"></table>
  <div id="tplprintjob-query-printJobCallHandleList" style="padding:0px;height:auto">
	  <div class="buttonBG" id="agprint-button-printJobCallHandleList"></div>
	  <form action="" id="tplprintjob-form-printJobCallHandleList" method="post" style="padding-top: 2px;">
		  <table>
			  <tr>
				  <td>打印单据号:</td>
				  <td><input type="text" name="printorderid" style="width:180px" /></td>
				  <td>申请单据号:</td>
				  <td><input type="text" name="sourceorderid" style="width:180px" /></td>
				  <td>
					  <a href="javaScript:void(0);" id="tplprintjob-query-queryPrintJobCallHandleOperList" class="button-qr">查询</a>
					  <a href="javaScript:void(0);" id="tplprintjob-query-reloadPrintJobCallHandleOperList" class="button-qr">重置</a>
				  </td>
			  </tr>
		  </table>
		  <input type="hidden" id="tplprintjob-printJobCallHandleList-jobid" name="jobid" value="${param.jobid}" />
	  </form>
  </div>
   	<div style="display:none">
   		<div id="tplprintjob-dialog-printJobCallHandleOper" ></div>
   		<div id="tplprintjob-dialog-printJobCallHandleOper-methodparam">
			<table>
				<tr>
					<td>格式化前：</td>
					<td>
						<div id="tplprintjob-dialog-printJobCallHandleOper-methodparam-before" class="Canvas" style="width:450px;min-height:300px;overflow: :auto;">

						</div>
					</td>
					<td>格式化后：</td>
					<td>
						<div id="tplprintjob-dialog-printJobCallHandleOper-methodparam-after" class="Canvas" style="width:450px;min-height: 300px; overflow: :auto;">

						</div>
					</td>
				</tr>
			</table>
		</div>
   	</div>
   	<script type="text/javascript">
		var methodParamArray=new Array();
   		$(function(){
			$("#agprint-button-printJobCallHandleList").buttonWidget({
	 			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{}
	 			],
				buttons:[
					<security:authorize url="/agprint/tplprintjob/showPrintJobCallHandleOperEditPageBtn.do">
					{
						id:'callhandle-id-viewparam',
						name:'查看方法参数',
						iconCls:'button-view',
						handler:function(){

							var dataRow=$("#tplprintjob-table-printJobCallHandleList").datagrid('getSelected');
							if(dataRow==null){
								$.messager.alert("提醒","请选择相关的打印任务回调信息!");
								return false;
							}
							var title="查看打印任务回调信息";
							title=title+(dataRow.jobid || "");

							showCallHandleMethodParamDailog( dataRow.id);
						}
					},
					</security:authorize>
					{}		
				],
	 			model:'base',
				type:'list',
				datagrid:'tplprintjob-table-printJobCallHandleList',
				tname:'t_print_job_callhandle'
				//id:''
	 		});
   			

   			//回车事件
			controlQueryAndResetByKey("tplprintjob-query-queryPrintJobCallHandleList","tplprintjob-query-reloadPrintJobCallHandleList");
   			
   			//查询
  			$("#tplprintjob-query-queryPrintJobCallHandleOperList").click(function(){
  				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#tplprintjob-form-printJobCallHandleList").serializeJSON();
	       		$("#tplprintjob-table-printJobCallHandleList").datagrid("load",queryJSON);
  			});
  			//重置
  			$('#tplprintjob-query-reloadPrintJobCallHandleList').click(function(){
  				$("#tplprintjob-form-printJobCallHandleList")[0].reset();
	       		$("#tplprintjob-table-printJobCallHandleList").datagrid('loadData',{total:0,rows:[]});
  			});
			setTimeout(function(){
				$(".menu-sep").hide();
			},10);
			//showPrintJobCallHandleDatagrid();
   		});
	   	function showCallHandleMethodParamDailog(id){
			var $before=$("#tplprintjob-dialog-printJobCallHandleOper-methodparam-before");
			var $after=$("#tplprintjob-dialog-printJobCallHandleOper-methodparam-after");
			$before.val("");
			$after.val("");
			if(id==null || $.trim(id)==""){
				return false;
			}
			if(methodParamArray.length==0){
				return false;
			}
			var index=$("#methodparam_"+id).val() || "";
			if(index=="" || isNaN(index)){
				return false;
			}
			index=index-1;
			if(methodParamArray.length<index){
				return false;
			}
			var value=methodParamArray[index] || "";
			$before.html(value);
			//var style=
			if(value!="") {
				Process(value, 'tplprintjob-dialog-printJobCallHandleOper-methodparam-after');
			}

	   		$('#tplprintjob-dialog-printJobCallHandleOper-methodparam').dialog({
			    title: "查看方法参数",
			    fit:true,
				//width:460,
				//height:300,
			    closed: true,  
			    cache: false,
				maximizable:true,
				resizable:true,  
			    modal: true, 
			    onLoad:function(){
	   			}
			});
			$('#tplprintjob-dialog-printJobCallHandleOper-methodparam').dialog('open');
	   	}

		function showPrintJobCallHandleDatagrid(){
			var queryFormJSON = $("#tplprintjob-form-printJobCallHandleList").serializeJSON();
			$("#tplprintjob-table-printJobCallHandleList").datagrid({
				fit:true,
				method:'post',
				title:'',
				rownumbers:true,
				pagination:true,
				idField:'id',
				sortName : 'addtime',
				sortOrder : 'desc',
				singleSelect:true,
				toolbar:'#tplprintjob-query-printJobCallHandleList',
				url:'agprint/tplprintjob/showPrintJobCallHandlePageListData.do',
				queryParams : queryFormJSON,
				columns:[[
					{field:'id',title:'编号',width:200,hidden:true},
					{field:'jobid',title:'任务编号',width:180},
					{field:'classname',title:'调用类名',width:150},
					{field:'methodname',title:'调用方法',width:150},
					{field:'methodparam',title:'方法参数',width:150,
						formatter:function(value,rowData,rowIndex){
							if(methodParamArray==null){
								methodParamArray=new Array();
							}
							methodParamArray.push(value);
							var tmps="<input id=\"methodparam_"+rowData.id+"\" type=\"hidden\" value=\""+methodParamArray.length+"\">";
							tmps+="<a href=\"javascript:void(0);\" onclick=\"javascript:showCallHandleMethodParamDailog('"+rowData.id+"');\">点击查看</a>";
							return tmps;
						}
					},
					{field:'type',title:'调用类型',width:80,
						formatter:function(value,rowData,rowIndex){
							if(value=="1"){
								return "次数更新";
							}
						}
					},
					{field:'printorderid',title:'打印单据号',width:140},
					{field:'printordername',title:'单据名称',width:100},
					{field:'sourceorderid',title:'申请单据号',width:140},
					{field:'sourceordername',title:'申请单据名称',width:100},
					{field:'status',title:'状态',width:100,
						formatter:function(val){
							if(val=="0"){
								return "未处理";
							}else if(val=="1"){
								return "处理";
							}
						}
					},
					{field:'pages',title:'打印页面数',width:60},
					{field:'adduserid',title:'添加者编号',width:80,hidden:true},
					{field:'addusername',title:'添加者名称',width:100},
					{field:'addtime',title:'添加者时间',width:130},
					{field:'modifyuserid',title:'修改者编号',width:80,hidden:true},
					{field:'modifyusername',title:'修改者名称',width:100},
					{field:'modifytime',title:'修改者时间',width:130}
				]]
			});
		}
   	</script>
  </body>
</html>
