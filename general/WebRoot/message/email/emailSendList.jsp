<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>发件箱管理预览界面</title>
  </head>
  
  <body> 
 		<div class="easyui-layout" data-options="fit:true" border="false">
 			<div title="" data-options="region:'north'" border="false" style="height:90px;padding:2px 4px; background-color: height: 28px;background: #EFEFEF;border-bottom: 1px solid #CCC;">
 				<div id="messageEmail-emailSendPreviewList-query-showQueryToolbar" style="height:auto">
                    <div>
                        <div style="float:left;">
                            <a href="javaScript:void(0);" id="messageEmail-emailSendPreviewList-btn-deleteEmail" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
                            <%-- <a href="javaScript:void(0);" id="messageEmail-emailDraftList-btn-exportEmailxls" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-extend-xls'">导出Excel</a>			--%>
                            <%-- <a href="javaScript:void(0);" id="messageEmail-emailSendPreviewList-btn-modelistview"  onclick="javascript:emailPage_openPanel('message/email/emailSendListPage.do','已发邮件箱')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-extend-modelist'">切换到列表界面</a>--%>
                        </div>
                        <div style="float:left">
							<div onclick="javascript:emailSendPreviewList_deleteEmailReceive(1);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-extend-filedel'">收回收件人未读邮件</div>
							<%-- <div onclick="javascript:emailSendPreviewList_deleteEmailReceive(3);" data-options="iconCls:'icon-extend-filedel'">收回收件人已读邮件</div>--%>
							<span href="javaScript:void(0);" id="messageNotice-queay-emailSendPreviewList-advanced"></span>

						</div>
                        <div style="clear:both"></div>
                    </div>
					<div>
						<form action="" id="messageEmail-form-emailSendPreviewList" method="post">
							<table class="querytable">
								<tr>
									<td>收件人：</td>
									<td><input id="messageEmail-form-emailSendPreviewList-receiver" name="queryreceiver" style="width:200px"></td>
									<td>标题内容：</td>
									<td><input name="tlecont" style="width:200px"></td>
								</tr>
								<tr>
									<td>
										发送时间：
									</td>
									<td>
										<input name="addtimestart" style="width:90px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
										到 <input name="addtimeend" style="width:90px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
									</td>
									<td colspan="2">
										<a href="javaScript:void(0);" id="messageEmail-emailSendPreviewList-btn-queryEmailList" class="button-qr">查询</a>
										<a href="javaScript:void(0);" id="messageEmail-emailSendPreviewList-btn-reloadEmailList" class="button-qr">重置</a>
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
 			</div>			
		<div title="" data-options="region:'center'" border="false">
			<table id="messageEmail-table-emailSendPreviewList"></table>
		</div>
	</div>
	<div style="display:none">
		<div id="messageEmail-dialog-emailList-DetailOper" ></div>
		<div id="messageEmail-dialog-emailSendPreviewOper" ></div>
	</div>
	<script type="text/javascript">

		var emailPage_showMailDetail=function(id){
			if(id==null || $.trim(id)==""){
				return;
			}
			var atitle='发件箱-邮件详情';
			$('<div id="messageEmail-dialog-emailList-DetailOper-content"></div>').appendTo('#messageEmail-dialog-emailList-DetailOper');
			var $mailDetailOper=$('#messageEmail-dialog-emailList-DetailOper-content');
				$mailDetailOper.dialog({  
				    title: atitle,  
				    fit:true, 
				    closed: true,  
				    cache: false,  
					href:'message/email/emailSendDetailPage.do?showoper=0&id='+id,
				    modal: true,
					onClose:function(){
						$("#messageEmail-dialog-emailList-DetailOper-content").dialog("destroy");    					
					}
				    
				});
				$mailDetailOper.dialog("open");
		}
		var emailSendPreviewList_showReceiveUserList=function(emailid){
			try{
				if(!isNaN(emailid) && emailid){
                    $('<div id="messageEmail-dialog-emailSendPreviewOper-content"></div>').appendTo('#messageEmail-dialog-emailSendPreviewOper');
                    var $emailSendOper=$('#messageEmail-dialog-emailSendPreviewOper-content');
                    $emailSendOper.dialog({
						title: '查看邮件状态',  
					    width: 450,  
					    height: 450,  
					    closed: true,
					    cache: false,  
					    href: 'message/email/emailSendBoxUserListPage.do?emailid='+emailid,  
					    modal: true,
                        onClose:function(){
                            $emailSendOper.dialog("destroy");
                        }
					});
                    $emailSendOper.dialog("open");
				}
			}catch(e){
			}
		}
		var emailSendPreviewList_deleteEmailReceive=function(oper){
			if(oper==null || oper==""){
				oper=1;
			}
			var $emailSendPreviewList=$("#messageEmail-table-emailSendPreviewList");
			var dataRows = $emailSendPreviewList.datagrid('getChecked');
	    	if(dataRows==null){
	    		$.messager.alert("提醒","请选择邮箱信息！");
	    		return false;
	    	}
	    	var idarr=Enumerable.from(dataRows).select("$.id").toArray();
	    	if(idarr.length==0){
	    		$.messager.alert("提醒","请选择邮件信息！");
	    		return false;
	    	}
			$.messager.confirm("提醒", "确定收回收件人未阅读的邮件?", function(r) {
			if (r) {
		    	$.ajax({   
		            url :'message/email/deleteEmailReceiveBySend.do',
		            type:'post',
		            dataType:'json',
		            data:{opertype:1,emlids:idarr.join(',')},
		            success:function(json){
	            		$.messager.alert("提醒","操作成功！");
	            		$emailSendPreviewList.datagrid('reload');
		            }
		        });
	        }});
		};
		var emailSendPreviewList_showEmailDetail=function(id){
			if(id==null || $.trim(id)=="" || isNaN(id)){
				return false;
			}
			$("#messageEmail-emailSendPreviewList-panel").panel({
				fit:true,
				border:false,
				closed:false,
				title:'',
				href:'message/email/emailSendDetailPage.do?showoper=0&id='+$.trim(id),
			    cache:false,
			    maximized:true
			});	
		};
		$(document).ready(function(){
			$("#messageNotice-queay-emailSendPreviewList-advanced").advancedQuery({
		 		name:'msg_emailcontent',
		 		datagrid:'messageEmail-table-emailSendPreviewList'
			});
			var messageEmail_emailListColJson=$("#messageEmail-table-emailSendPreviewList").createGridColumnLoad({
				name :'msg_emailcontent',
				frozenCol : [[
								{field:'id',checkbox:true}
			    			]],
				commonCol : [[
								{field:'receiver',title:'收件人',width:110,isShow:true,
								    formatter: function(value,row,index){
                                        if(row.recvusername!=null && row.recvusername!=""){
                                            return row.recvusername;
                                        }else {
                                            var usrc=row.receivernum;
											return "<a href=\"javaScript:void(0);\" onclick=\"javascript:emailSendPreviewList_showReceiveUserList('"+row.id+"')\">点击查看("+usrc+")</a>";
										}
								}},    
								{field:'title',title:'标题',width:200,
								    formatter: function(value,row,index){
										var html=new Array();
								    	try{
								        	if(row.emailReceiveList && row.emailReceiveList.length==1){
									        	if(row.emailReceiveList[0].viewflag!=null && row.emailReceiveList[0].viewflag==1){
										        	html.push("<span class=\"img-extend-emailnew\" style=\"float:left;\"></span>&nbsp;");
									        	}else{
										        	html.push("<span class=\"img-extend-emailopen\" style=\"float:left;\"></span>&nbsp;");
									        	}
								        	}else{
									        	html.push("<a href=\"javascript:void(0);\" onclick=\"javascript:emailSendPreviewList_showReceiveUserList(");
									        	html.push(row.id);
									        	html.push(");\"><span class=\"img-extend-deptuser2\" style=\"float:left; cursor:pointer;\"></span></a>&nbsp;");
								        	}
								    	}catch(e){
								    	}
								    	if(row.importantflag==1){
								        	html.push("<span style='color:#f00;'>");
								        	html.push(row.title);
								        	html.push("</span>");
								    	}else if( row.importantflag==2){
								        	html.push("<span style='color:#f00;font-weight:bold;'>");
								        	html.push(row.title);
								        	html.push("</span>");
								    	}else{
								        	html.push(row.title);
								    	}
								    	return html.join("");
								}},  
								{field:'addtime',title:'时间',width:70,
								    formatter: function(value,row,index){
								    	if(value!=null && value!=""){
								        	return value.split(" ")[0];
								    	}
									}
								},  
						        {field:'attach',title:'附件',width:75,isShow:true,
							        formatter: function(value,row,index){
							        	if(value!=null 
							        			&& value.length>0){
							        		return "有";
							        	}
							        	return "";				        	
						        	}
						        }
							]]
			});
	  	 	$('#messageEmail-table-emailSendPreviewList').datagrid({ 
	  	 		fit:true,
	            striped: true,
	  	 		method:'post',
	  	 		title:'',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:true,
                pageSize:100,
			    url:'message/email/showEmailSendPageList.do', 
			    authority : messageEmail_emailListColJson,
			    frozenColumns : messageEmail_emailListColJson.frozen,
			    columns : messageEmail_emailListColJson.common,
			    sortName:'addtime',
			    sortOrder:'desc',
			    onDblClickRow:function(rowIndex, rowData){
				    try{
						emailPage_showMailDetail(rowData.id);
				    }catch(e){
				    }
			    }
			}).datagrid("columnMoving");  
			

			//查询
			$("#messageEmail-emailSendPreviewList-btn-queryEmailList").click(function(){
				//查询参数直接添加在url中         
				var queryJSON= $("#messageEmail-form-emailSendPreviewList").serializeJSON();
				//重新赋值url 属性
	        	$("#messageEmail-table-emailSendPreviewList").datagrid('load',queryJSON);
			});
			//重置
			$("#messageEmail-emailSendPreviewList-btn-reloadEmailList").click(function(){
                $("#messageEmail-form-emailSendPreviewList-receiver").widget("clear");
				$("#messageEmail-form-emailSendPreviewList")[0].reset();
    			//重新赋值url 属性
				$("#messageEmail-table-emailSendPreviewList").datagrid('load', {});
			});
			//删除
			$("#messageEmail-emailSendPreviewList-btn-deleteEmail").click(function(){
				var $emailSendPreviewList=$("#messageEmail-table-emailSendPreviewList");
				var dataRows = $emailSendPreviewList.datagrid('getChecked');
		    	if(dataRows==null){
		    		$.messager.alert("提醒","请选择邮箱信息！");
		    		return false;
		    	}
		    	var idarr=Enumerable.from(dataRows).select("$.id").toArray();
		    	if(idarr.length==0){
		    		$.messager.alert("提醒","请选择邮件信息！");
		    		return false;
		    	}
				$.messager.confirm("提醒", "是否要交将选中的邮件放入废弃篓?", function(r) {
				if (r) {
			    	$.ajax({   
			            url :'message/email/deleteEmail.do',
			            type:'post',
			            dataType:'json',
			            data:{dstate:2,ids:idarr.join(',')},
			            success:function(json){
		            		$emailSendPreviewList.datagrid('reload');
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
		            			$.messager.alert("提醒","操作失败！");
		            		}
			            }
			        });
		        }});
			});

            //收件人
            $("#messageEmail-form-emailSendPreviewList-receiver").widget({
                name: 'msg_emailcontent',
                col: 'receiveuser',
                singleSelect: false,
                width: 200,
                onlyLeafCheck: false
            });
		});
  	 </script>
  </body>
</html>