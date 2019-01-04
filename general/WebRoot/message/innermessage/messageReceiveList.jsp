<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>已接收短信管理</title>    
	<%@include file="/include.jsp" %> 	
  	<script type="text/javascript" src="js/kindeditor/kindeditor-min.js"></script> 
  	<script type="text/javascript" src="js/kindeditor/lang/zh_CN.js"></script> 
  	<script type="text/javascript" src="js/linq.min.js"></script> 	
	<link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>
  </head>
  
  <body>
    <table id="innerMessage-table-messageReceiveList"></table>
  	<div id="innerMessage-query-messageReceiveList" style="padding:5px;height:auto">
		<div>
			<form action="" id="innerMessage-form-messageReceiveList" method="post">

				<table class="querytable">
					<tr>
						<td>内容：</td>
						<td>
							<input name="msgc_tlecont" style="width:150px">
						</td>
						<td>时间：</td>
						<td>
							<input name="starttime" style="width:90px" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">至<input name="endtime" style="width:90px" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
						</td>
					</tr>
					<tr>
						<td>
							发送人：
						</td>
						<td>
							<input id="innerMessage-form-messageReceiveList-adduserid" name="msgc_adduserid" style="width:150px" />
						</td>
						<td colspan="2">
							<a href="javaScript:void(0);" id="innerMessage-queay-queryMessageReceiveList" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="innerMessage-queay-reloadMessageReceiveList" class="button-qr">重置</a>
						</td>
					</tr>
				</table>

			</form>
		</div>
		<div>				
			<security:authorize url="/message/innerMessage/messageSendPage.do">
				<a href="javaScript:void(0);" id="innerMessage-messageReceiveList-btn-and-sendMessage" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-innerMessage'">发送内部短信</a>
			</security:authorize>
			<security:authorize url="/message/innerMessage/messageDetailPage.do">			
				<a href="javaScript:void(0);" id="innerMessage-messageReceiveList-btn-view-messageDetail" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-view'">查看</a>
			</security:authorize>	
			<security:authorize url="/message/innerMessage/deleteMessageReceive.do">			
				<a href="javaScript:void(0);" id="innerMessage-messageReceiveList-btn-remove-removeMessageReceive" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
			</security:authorize>			
			<security:authorize url="/message/innerMessage/setMessageReceiveReadFlag.do">			
				<a href="javaScript:void(0);" id="innerMessage-messageReceiveList-btn-setflag-setMessageReceiveRead" class="easyui-linkbutton" title="标记已阅读" data-options="plain:true,iconCls:'button-readmess'">标记已阅读</a>
			</security:authorize>
            <span id="innerMessage-queay-queryMessageReceiveList-advanced"></span>
		</div>
		<div id="innerMessage-dialog-showMessageReceiveUserList"></div>
		<div id="innerMessage-window-sendMessageOper"></div>	
	</div>
	<script type="text/javascript">

		var innerMessageReceiveList_showMessageDetail=function(id){
			if(id==null || $.trim(id)=="" || isNaN(id)){
				return false;
			} 				
		   	$('<div id="innerMessage-dialog-showMessageReceiveUserList-content"></div>').appendTo('#innerMessage-dialog-showMessageReceiveUserList');
			var $messageSendOper=$('#innerMessage-dialog-showMessageReceiveUserList-content');
			var url="message/innerMessage/messageDetailPage.do?id="+$.trim(id);
			$messageSendOper.dialog({  
				title:'查看内部短信',
			    width: 660,  
			    height: 450,
			    closed: true,
			    cache: false, 
			    href:url,
				modal: true,
			    onClose:function(){
		            $messageSendOper.dialog("destroy");
		        }
			});
			$messageSendOper.dialog("open");
		};
 		$(document).ready(function(){
 	 				
 			$("#innerMessage-table-messageReceiveList").datagrid({	
 				fit:true,
	  	 		method:'post',
	  	 		title:'已接收',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		singleSelect:true,
				toolbar:'#innerMessage-query-messageReceiveList',
			    url:'message/innerMessage/showMessageReceivePageList.do', 
			    columns:[[  
					{field : 'idck',checkbox : true},
			        {field:'sendusername',title:'发送人',width:120,sortable:true,
						formatter: function(value,row,index){
		        		if(row.senduserid=="system"){
		        			return "系统";
		        		} else {
		        			return value;
		        		}
		        	}},  
			        {field:'title',title:'内容',width:300,
			        	formatter: function(value,row,index){
			        		return JCommonUtils.getObjectValue(row.msgContent,"title");
			        }},  
			        {field:'sendtime',title:'发送时间',width:120},
			        {field:'viewflag',title:'阅读状态',width:120,
			        	formatter: function(value,row,index){
			        		if(row.viewflag=="1"){
			        			return "未阅读";
			        		} else {
			        			return "已阅读";
			        		}
			        }},
			        {field:'viewtime',title:'阅读时间',width:120,
			        	formatter: function(value,row,index){
		        			if(row.viewflag=="0" && value!=null){
			        			return value;
		        			}
			        	}
			        }
			    ]],
			    sortName:'msgc.addtime',
			    sortOrder:'desc',
			    onDblClickRow:function(rowIndex, rowData){
				    if(rowData.msgContent!=null && rowData.msgContent.id!=null && $.trim(rowData.msgContent.id)!=""){
		    			innerMessageReceiveList_showMessageDetail(rowData.msgContent.id);
				    }
		    	}
 			}).datagrid("columnMoving"); 
 			
 			$("#innerMessage-queay-queryMessageReceiveList-advanced").advancedQuery({
		 		name:'msg_content',
		 		datagrid:'innerMessage-table-messageReceiveList'
			});

 			$("#innerMessage-queay-queryMessageReceiveList").click(function(){
 				//查询参数直接添加在url中         
	       		var queryJSON = $("#innerMessage-form-messageReceiveList").serializeJSON();
	       		
 				$('#innerMessage-table-messageReceiveList').datagrid('load',queryJSON);
 			});
 			$("#innerMessage-queay-reloadMessageReceiveList").click(function(){
 				$("#innerMessage-form-messageReceiveList-adduserid").widget("clear");
				$("#innerMessage-form-messageReceiveList")[0].reset();
 				$('#innerMessage-table-messageReceiveList').datagrid('load', {});
 			});
 			
 			
 			$("#innerMessage-messageReceiveList-btn-and-sendMessage").click(function(){
				var url="message/innerMessage/messageSendPage.do";
			   	$('<div id="innerMessage-window-sendMessageOper-content"></div>').appendTo('#innerMessage-window-sendMessageOper');
		  		var $messageSendOper=$("#innerMessage-window-sendMessageOper-content");
				$messageSendOper.window({
					title:'内部短信发送',
				    width: 700,  
				    height: 450,
		            top:($(window).height() - 450) * 0.5, 
		            left:($(window).width() - 700) * 0.5, 
				    closed: true,  
				    cache: false, 
				    href:url,
				    modal: true,
				    onClose:function(){
			            $messageSendOper.dialog("destroy");
			        }
				});
				$messageSendOper.window("open");
			});
			$("#innerMessage-messageReceiveList-btn-remove-removeMessageReceive").click(function(){
				$.messager.confirm("删除确认","是否删除内部短信?",function(r){
	   				if(r){    		
	   					var $messageReceiveList=$("#innerMessage-table-messageReceiveList");			
						var msgrows = $messageReceiveList.datagrid('getChecked');
				    	if(msgrows==null || msgrows.length==0){
				    		$.messager.alert("提醒","请选择要删除的短信！");
				    		return false;
				    	}
				    	var idarr=Enumerable.from(msgrows).select("$.id").toArray();
				    	if(idarr.length==0){
				    		$.messager.alert("提醒","请选择要删除的短信！");
				    		return false;					    		
				    	}
				    	$.ajax({   
				            url :'message/innerMessage/deleteMessageReceive.do',
				            data:{ids:idarr.join(",")},
				            type:'post',
				            dataType:'json',
				            success:function(json){
			            		$messageReceiveList.datagrid('reload');
				            	if(json.flag==true){
				            		var msginfo=new Array();
				            		msginfo.push("操作成功");
				            		if(json.ismuti){
					            		msginfo.push(" <br/>成功 ");
					            		msginfo.push(json.isuccess);
					            		msginfo.push("条<br/>");
					            		msginfo.push("失败 ");
					            		msginfo.push(json.ifailure+json.inohandle);
					            		msginfo.push("条<br/>");
					            		//msginfo.push("不需要处理 ");
					            		//msginfo.push(json.inohandle);
					            		//msginfo.push("条<br/>");
				            		}
				            		$.messager.alert("提醒",msginfo.join(""));
				            	}else{
				            		$.messager.alert("提醒",(json.msg || "操作失败！"));
				            	}
				            }
				        });
	   				}
	   			});
			});
 			
 			$("#innerMessage-messageReceiveList-btn-setflag-setMessageReceiveRead").click(function(){
				$.messager.confirm("操作确认","是否标记内部短信为已阅读?",function(r){
	   				if(r){    	
	   					var $messageReceiveList=$("#innerMessage-table-messageReceiveList");
						var msgrows = $messageReceiveList.datagrid('getChecked');
				    	if(msgrows==null || msgrows.length==0){
				    		$.messager.alert("提醒","请选择要标记已阅读的内部短信！");
				    		return false;
				    	}
				    	var idarr=Enumerable.from(msgrows).where("$.viewflag==1").select("$.id").toArray();
				    	if(idarr.length==0){
				    		$.messager.alert("提醒","已经标记成功！");
				    		$messageReceiveList.datagrid('uncheckAll');
				    		return false;					    		
				    	}
				    	$.ajax({   
				            url :'message/innerMessage/setMessageReceiveReadFlag.do',
				            data:{ids:idarr.join(",")},
				            type:'post',
				            dataType:'json',
				            success:function(json){
			            		$('#innerMessage-table-messageReceiveList').datagrid('reload');
				            	if(json.flag==true){
				            		var msginfo=new Array();
				            		msginfo.push("操作成功");
				            		if(json.ismuti){
					            		msginfo.push(" <br/>成功 ");
					            		msginfo.push(json.isuccess);
					            		msginfo.push("条<br/>");
					            		msginfo.push("失败 ");
					            		msginfo.push(json.ifailure+json.inohandle);
					            		msginfo.push("条<br/>");
					            		//msginfo.push("不需要处理 ");
					            		//msginfo.push(json.inohandle);
					            		//msginfo.push("条<br/>");
				            		}
				            		$.messager.alert("提醒",msginfo.join(""));
				            	}else{
				            		$.messager.alert("提醒",(json.msg || "标记失败！"));
				            	}
				            }
				        });
	   				}
	   			});
			});

			$("#innerMessage-messageReceiveList-btn-view-messageDetail").click(function(){
				var msgrow = $("#innerMessage-table-messageReceiveList").datagrid('getSelected');
		    	if(msgrow==null || msgrow.length==0 || msgrow.msgContent==null){
		    		$.messager.alert("提醒","请选择要查看的内部短信！");
		    		return false;
		    	}
		    	innerMessageReceiveList_showMessageDetail(msgrow.msgContent.id);
			});

            $("#innerMessage-form-messageReceiveList-adduserid").widget({
                name: 'msg_content',
                col: 'adduserid',
                singleSelect: true,
                width: 150,
                onlyLeafCheck: false
            });
 		});
 	</script>
  </body>
</html>

