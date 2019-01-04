<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>已发短信箱管理</title>    
	<%@include file="/include.jsp" %> 	
  	<script type="text/javascript" src="js/kindeditor/kindeditor-min.js"></script> 
  	<script type="text/javascript" src="js/kindeditor/lang/zh_CN.js"></script> 
  	<script type="text/javascript" src="js/linq.min.js"></script> 	
	<link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>
  </head>
  
  <body>
    <table id="innerMessage-table-messageSendList"></table>
  	<div id="innerMessage-query-messageSendList" style="padding:5px;height:auto">
		<div>
			<form action="" id="innerMessage-form-messageSendList" method="post">

				<table class="querytable">
					<tr>
						<td>内容：</td>
						<td>
							<input name="tlecont" style="width:150px">
						</td>
						<td>时间：</td>
						<td>
							<input name="starttime" style="width:90px" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">至<input name="endtime" style="width:90px" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
						</td>
					</tr>
					<tr>
						<td>
							接收人：
						</td>
						<td>
							<input id="innerMessage-form-messageSendList-receivers" name="query_msgr_receivers" style="width:150px" />
						</td>
						<td colspan="2">
							<a href="javaScript:void(0);" id="innerMessage-queay-queryMessageSendList" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="innerMessage-queay-reloadMessageSendList" class="button-qr">重置</a>

						</td>
					</tr>
				</table>
			</form>
		</div>
		<div>
		<security:authorize url="/message/innerMessage/messageSendPage.do">
			<a href="javaScript:void(0);" id="innerMessage-messageSendList-btn-and-sendMessage" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-innerMessage'">发送内部短信</a>
		</security:authorize>
		<security:authorize url="/message/innerMessage/messageSendDetailPage.do">
			<a href="javaScript:void(0);" id="innerMessage-messageSendList-btn-view-messageDetail" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-view'">查看</a>
		</security:authorize>				
		<security:authorize url="/message/innerMessage/deleteMessageSend.do">			
			<a href="javaScript:void(0);" id="innerMessage-messageSendList-btn-remove-removeMessageSend" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
		</security:authorize>
            <span id="innerMessage-queay-queryMessageSendList-advanced"></span>
		</div>
	</div>
	<div id="innerMessage-dialog-showMessageReceiveUserList"></div>
	<div id="innerMessage-window-sendMessageOper"></div>
	
 	<script type="text/javascript">
		var innerMessageSendList_showReceiveUserList=function(url){
 				if(url==null || $.trim(url)==""){
 					$.messager.alert('提醒 ','抱歉，无法打开接收人页面');
 					return;
 				} 				
 			   	$('<div id="innerMessage-dialog-showMessageReceiveUserList-content"></div>').appendTo('#innerMessage-dialog-showMessageReceiveUserList');
 				var $messageSendOper=$('#innerMessage-dialog-showMessageReceiveUserList-content');
				$messageSendOper.dialog({  
					title:'查看内部短信接收人',
				    width: 660,  
				    height: 450,
		            top:($(window).height() - 450) * 0.5, 
		            left:($(window).width() - 660) * 0.5, 
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
 		var innerMessageSendList_showMessageDetail=function(id){
			if(id==null || $.trim(id)=="" || isNaN(id)){
				return false;
			} 				
		   	$('<div id="innerMessage-dialog-showMessageReceiveUserList-content"></div>').appendTo('#innerMessage-dialog-showMessageReceiveUserList');
			var $messageSendOper=$('#innerMessage-dialog-showMessageReceiveUserList-content');
			var url="message/innerMessage/messageSendDetailPage.do?id="+$.trim(id);
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
 			$("#innerMessage-queay-queryMessageSendList-advanced").advancedQuery({
		 		name:'msg_content',
		 		datagrid:'innerMessage-table-messageSendList'
			});
 			var messageSendList_messageListColJson=$("#innerMessage-table-messageSendList").createGridColumnLoad({
				name :'msg_content',
				frozenCol : [[
								{field:'id',checkbox:true}
			    			]],
				commonCol : [[  	
								{field:'addusername',title:'发送人',width:120,isShow:true,
									formatter: function(value,row,index){
					        		if(row.adduserid=="system"){
					        			return "系统";
					        		} else {
					        			return value;
					        		}
					        	}},
								{field:'recvusername',title:'接收人',width:120,isShow:true,
								    formatter: function(value,row,index){
								    	if(row.receiveusername!=null && row.receiveusername!=""){
								    	    return row.receiveusername;
								    	}else {
                                            var usrc=0;
                                            if(row.clocktype==2){
                                                usrc=row.receivers.split(',').length;
                                            }else{
                                                usrc=row.receivernum;
                                            }
                                            var url="message/innerMessage/messageReceiveUserListPage.do?msgid="+row.id;
                                            return '<a href="javaScript:void(0);" title="共有'+usrc+'人" onclick="innerMessageSendList_showReceiveUserList(\''+url+'\')">点击查看('+usrc+')</a>';
                                        }
								}},							  
								{field:'title',title:'内容',width:400,sortable:false},  
								{field:'addtime',title:'发送时间',width:150,sortable:true,
								    formatter: function(value,row,index){
										if(row.clocktype==2){
											return row.clocktime+"(定时)";
										} 
										else {
											return value;
										}
								}},
								//aliascol指定数据库对应字段名
								{field:'state',title:'状态',width:120,aliascol:'viewflag',sortable:true,
									formatter: function(value,row,index){
										if(row.clocktype==2){
											return "定时发送";
										}else{
											if(row.msgReceive==null){
												return "";
											}
											if (row.msgReceive.length==1){
												if( row.msgReceive[0].viewflag==1){
													return "未阅读";
												}else{
													return "已阅读"
												}
											} else {
												var msgrcount=row.msgReceive.length;
												var unreads=Enumerable.from(row.msgReceive).select("$.viewflag").where("$==1").count();
												var text='';
												if(msgrcount==unreads){
													text='未阅读('+unreads+')';
												}else if( msgrcount>unreads){
													text='已阅读('+(msgrcount-unreads)+')/未阅读('+unreads+')';
												}else if(unreads==0){
													text='全部阅读';
												}else{
													text='已经阅读';
												}
												return text;									
											}
										}
								}},
								{field:'clocktype',title:'发送状态',width:150,
								    formatter: function(value,row,index){
										if(value==1){
											return "即时发送";
										} 
										else if(value==2){
											return "定时发送";
										}else if(value==0){
											return "定时已发送";
										}
								}},
								{field:'clocktime',title:'定时发送时间',width:150,
								    formatter: function(value,row,index){
									if(row.clocktype!=1){
										return value;
									}
								}},
								{field:'msgtype',title:'消息类型',width:150,sortable:true,
								    formatter: function(value,row,index){
									if(value==1){
										return "个人短信";
									} 
									else if(value==2){
										return "公告通知";
									}else if(value==3){
										return "电子邮件";
									}else if(value==4){
										return "工作流";
									}else if(value==5){
										return "业务预警";
									}else{
										return "其他";
									}
								}},
								{field:'url',title:'详细页面地址',width:150},
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
								}}
						    ]]
			});
 			$("#innerMessage-table-messageSendList").datagrid({	
 				fit:true,
            	striped: true,
	  	 		method:'post',
	  	 		title:'已发送',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		singleSelect:true,
				toolbar:'#innerMessage-query-messageSendList',
			    url:'message/innerMessage/showMessageSendPageList.do',  
			    authority : messageSendList_messageListColJson,
			    frozenColumns : messageSendList_messageListColJson.frozen,
			    columns : messageSendList_messageListColJson.common,
			    sortName:'addtime',
			    sortOrder:'desc',
			    onDblClickRow:function(rowIndex, rowData){
			    	innerMessageSendList_showMessageDetail(rowData.id);
			    }
 			}).datagrid("columnMoving");
 			
 			$("#innerMessage-queay-queryMessageSendList").click(function(){
 				//查询参数直接添加在url中         
	       		var queryJSON = $("#innerMessage-form-messageSendList").serializeJSON();
	       		
 				$('#innerMessage-table-messageSendList').datagrid('load',queryJSON);
 			});
 			$("#innerMessage-queay-reloadMessageSendList").click(function(){
 			    $("#innerMessage-form-messageSendList-receivers").widget("clear");
				$("#innerMessage-form-messageSendList")[0].reset();
				$('#innerMessage-table-messageSendList').datagrid('load', {});
 			});
 			
 			
			$("#innerMessage-messageSendList-btn-and-sendMessage").click(function(){
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
			$("#innerMessage-messageSendList-btn-remove-removeMessageSend").click(function(){
				$.messager.confirm("删除确认","是否删除内部短信?",function(r){
	   				if(r){    					
						var msgrows = $("#innerMessage-table-messageSendList").datagrid('getChecked');
				    	if(msgrows==null || msgrows.length==0){
				    		$.messager.alert("提醒","请选择要删除的内部短信！");
				    		return false;
				    	}
				    	var idarr=Enumerable.from(msgrows).select("$.id").toArray();
				    	if(idarr.length==0){
				    		$.messager.alert("提醒","请选择要删除的内部短信！");
				    		return false;					    		
				    	}
				    	$.ajax({   
				            url :'message/innerMessage/deleteMessageSend.do',
				            data:{ids:idarr.join(",")},
				            type:'post',
				            dataType:'json',
				            success:function(json){
			            		$('#innerMessage-table-messageSendList').datagrid('reload');
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
			$("#innerMessage-messageSendList-btn-view-messageDetail").click(function(){
				var msgrows = $("#innerMessage-table-messageSendList").datagrid('getSelected');
		    	if(msgrows==null){
		    		$.messager.alert("提醒","请选择要查看的内部短信！");
		    		return false;
		    	}
		    	innerMessageSendList_showMessageDetail(msgrows.id);
			});
            $("#innerMessage-form-messageSendList-receivers").widget({
                name: 'msg_content',
                col: 'receivers',
                singleSelect: false,
                width: 150,
                onlyLeafCheck: false
            });
 		});
 	</script>
  </body>
</html>
