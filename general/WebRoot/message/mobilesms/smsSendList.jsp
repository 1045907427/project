<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>短信发送管理</title>    
	<%@include file="/include.jsp" %>
  	<script type="text/javascript" src="js/linq.min.js"></script> 	
	<link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>
  </head>
  
  <body>
    <table id="mobileSms-table-smsSendList"></table>
  	<div id="mobileSms-query-smsSendList" style="padding:5px;height:auto">
		<div>
            <security:authorize url="/message/mobileSms/smsSendPage.do">
                <a href="javaScript:void(0);" id="mobileSms-smsSendList-btn-and-sendSms" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-mobilemess'">发送手机短信</a>
            </security:authorize>
            <security:authorize url="/message/mobileSms/deleteMobileSms.do">
                <a href="javaScript:void(0);" id="mobileSms-smsSendList-btn-remove-removeSmsSend" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
            </security:authorize>
            <span id="mobileSms-queay-querySmsSendList-advanced"></span>
			<form action="" id="mobileSms-form-smsSendList" method="post">
				短信内容:<input name="content" style="width:120px">
				开始时间:<input name="starttime" style="width:100px"  readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
				结束时间:<input name="endtime" style="width:100px"  readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
				<a href="javaScript:void(0);" id="mobileSms-queay-querySmsSendList" class="button-qr">查询</a>
				<a href="javaScript:void(0);" id="mobileSms-queay-reloadSmsSendList" class="button-qr">重置</a>
			</form>
		</div>
	</div>
	<div id="mobileSms-dialog-showSmsReceiveUserList" class="easyui-dialog" closed="true"></div>
	<div id="mobileSms_window_sendSmsOper" class="easyui-window" closed="true"></div>	
	<script type="text/javascript">
 		$(document).ready(function(){ 			
 			$("#mobileSms-queay-querySmsSendList-advanced").advancedQuery({
		 		name:'msg_mobilesms',
		 		datagrid:'mobileSms-query-smsSendList'
			});
			var smsSendList_smsColListJson=$("#mobileSms-table-smsSendList").createGridColumnLoad({
				name:'msg_mobilesms',
				frozenCol:[[
							{field : 'id',checkbox : true}
							]],
				commonCol:[[
							{field:'addusername',title:'发送人',width:120,isShow:true},  
							{field:'mobile',title:'手机号',width:120,isShow:true},  
							{field:'recvusername',title:'接收人',width:120,isShow:true},
							{field:'content',title:'内容',width:400},  
							{field:'sendtime',title:'发送时间',width:150},
							{field:'sendflag',title:'状态',width:120,
								formatter: function(value,row,index){
									if(value==1){
							    		return "待发送";
									}else if(value==2){
							    		return "发送失败";
									}else if(value==0){
							    		return "发送成功";
									}
							}},
							{field:'delflag',title:'删除状态',width:80,sortable:true,
							    formatter: function(value,row,index){
									if(value==1){
										return "未删除";
									} 
									else if(value==0){
										return "已删除";
									}
							}},
							{field:'deltime',title:'删除时间',width:150,
							    formatter: function(value,row,index){
									if(row.delflag==0){
										return value;
									}
							}},
							{field:'sendnum',title:'发送次数',width:120},
							{field:'dealtime',title:'处理时间',width:120},
							{field:'serialno',title:'发送序列',width:120}
							]]			
			});
 			$("#mobileSms-table-smsSendList").datagrid({	
 				fit:true,
 				nowrap:false,
            	striped: true,
	  	 		method:'post',
	  	 		title:'短信发送管理',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		singleSelect:true,
				toolbar:'#mobileSms-query-smsSendList',
			    url:'message/mobileSms/showSendMobileSmsPageList.do',  
			    authority:smsSendList_smsColListJson,
			    frozenColumns:smsSendList_smsColListJson.frozen,
			    columns:smsSendList_smsColListJson.common,
			    sortName:'addtime',
			    sortOrder:'desc',
 			}).datagrid("columnMoving");
 			
 			$("#mobileSms-queay-querySmsSendList").click(function(){
 				//查询参数直接添加在url中         
	       		var queryJSON = $("#mobileSms-form-smsSendList").serializeJSON();
	       		
 				$('#mobileSms-table-smsSendList').datagrid('load',queryJSON);
 			});
 			$("#mobileSms-queay-reloadSmsSendList").click(function(){ 				
				$("#mobileSms-form-smsSendList")[0].reset();
				$('#mobileSms-table-smsSendList').datagrid('load', {});
 			});
 			
 			
			$("#mobileSms-smsSendList-btn-and-sendSms").click(function(){
				var url="message/mobileSms/smsSendPage.do?smsSendPageId=mobileSms_window_sendSmsOper";
				$smsSendOper=$("#mobileSms_window_sendSmsOper");
				$smsSendOper.window({
					title:'手机短信发送',
				    width: 700,  
				    height: 450,
		            top:($(window).height() - 450) * 0.5, 
		            left:($(window).width() - 700) * 0.5, 
				    closed: true,  
				    cache: false, 
				    href:url,
				    modal: true
				});
				$smsSendOper.window("open");
			});
			$("#mobileSms-smsSendList-btn-remove-removeSmsSend").click(function(){
				$.messager.confirm("删除确认","是否删除手机短信（已发送的短信不可删除）?",function(r){
	   				if(r){    					
						var msgrows = $("#mobileSms-table-smsSendList").datagrid('getChecked');
				    	if(msgrows==null || msgrows.length==0){
				    		$.messager.alert("提醒","请选择要删除的手机短信！");
				    		return false;
				    	}
				    	var idarr=Enumerable.from(msgrows).where("$.sendflag != 0").select("$.id").toArray();
				    	if(idarr.length==0){
				    		$.messager.alert("提醒","请选择要删除的手机短信！");
				    		return false;					    		
				    	}
				    	$.ajax({   
				            url :'message/mobileSms/deleteMobileSms.do',
				            data:{ids:idarr.join(",")},
				            type:'post',
				            dataType:'json',
				            success:function(json){
			            		$('#mobileSms-table-smsSendList').datagrid('reload');
				            	if(json.flag==true){
				            		var msginfo=new Array();
				            		msginfo.push("操作成功");
				            		if(json.ismuti){
					            		msginfo.push("<br/>")
					            		msginfo.push("成功 ");
					            		msginfo.push(json.isuccess);
					            		msginfo.push("条<br/>");
					            		msginfo.push("失败 ");
					            		msginfo.push(json.ifailure+json.inohandle);
					            		msginfo.push("条<br/>");
					            		//msginfo.push("不需要处理 ");
					            		//msginfo.push(json.inohandle);
					            		//msginfo.push("条");
				            		}
				            		$.messager.alert("提醒",msginfo.join(""));
			            		}else{			            		
			            			$.messager.alert("提醒","操作失败！");
			            		}
				            }
				        });
	   				}
	   			});
			});	
 		});
 	</script>
  </body>
</html>
