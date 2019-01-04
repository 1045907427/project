<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>收件箱管理预览界面</title>
  </head>
  
  <body> 
  		<div title="" class="easyui-layout" data-options="fit:true" border="false">
  			<div title="" data-options="region:'north'" border="false" style="height:70px;padding:2px 4px; background-color: height: 28px;background: #EFEFEF;border-bottom: 1px solid #CCC;">
  				<div id="messageEmail-emailReceivePreviewList-query-showQueryToolbar" style="height:auto">
					<div>
                        <div style="float:left;">
                            <a href="javaScript:void(0);" id="messageEmail-emailReceivePreviewList-btn-deleteReceiveEmail" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
                            <a href="javaScript:void(0);" id="messageEmail-emailReceivePreviewList-btn-destroyReceiveEmail" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">彻底删除</a>
                            <a href="javaScript:void(0);" id="messageEmail-emailReceivePreviewList-btn-forwardEmail" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-oppaudit'">转发</a>
                            <a href="javaScript:void(0);" id="messageEmail-emailReceivePreviewList-btn-replyEmail" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-readmess'">回复</a>
                            <%--<a href="javaScript:void(0);" id="messageEmail-emailReceivePreviewList-btn-replyEmailAll" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-extend-newmail'">全部回复</a>	--%>
                            <a href="javaScript:void(0);" id="messageEmail-emailReceivePreviewList-btn-prevmail" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-back'">上一条</a>
                            <a href="javaScript:void(0);" id="messageEmail-emailReceivePreviewList-btn-nextmail" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-next'">下一条</a>
                        </div>
                        <div style="float:left">
                            <a href="javaScript:void(0);" id="messageEmail-emailReceivePreviewList-btn-showMovingOper" data-options="plain:true,iconCls:'button-import'">移动到</a>
                            <div id="messageEmail-emailReceivePreviewList-div-showEmailBox" style="width:200px;">
                            </div>
                        </div>
                        <div style="float:left">
                            <a href="javaScript:void(0);" id="messageEmail-emailReceivePreviewList-btn-showMoreOper" data-options="plain:true,iconCls:'icon-extend-subord'">更多</a>
                            <div id="messageEmail-emailReceivePreviewList-div-moreOperDiv" style="width:200px; ">
                                <div id="messageEmail-emailReceivePreviewList-btn-readEmail" data-options="iconCls:'button-readmess'">标记所选邮件为已读
                                </div>
                                <%-- <div id="messageEmail-emailReceivePreviewList-btn-xls" data-options="iconCls:'icon-extend-xls'">导出Excel--%>
                                <%-- </div>--%>
                                <%-- <div id="messageEmail-emailReceivePreviewList-btn-deleteEmail" data-options="iconCls:'icon-extend-expeml'">导出Eml--%>
                                <%-- </div>--%>
                                <div id="messageEmail-emailReceivePreviewList-btn-deleteReadEmail" data-options="iconCls:'button-delete'">删除所有已读邮件
                                </div>
                                <div id="messageEmail-emailReceivePreviewList-btn-readAllEmail" data-options="iconCls:'button-readmess'">标记全部邮件为已读
                                </div>
                            </div>
                        </div>
                        <div style="clear:both"></div>
                    </div>
                    <div>
						<form action="" id="messageEmail-form-emailReceivePreviewList" method="post">
							标题内容:<input name="msgctlecont" style="width:200px">
							<a href="javaScript:void(0);" id="messageEmail-emailReceivePreviewList-btn-queryEmailList" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="messageEmail-emailReceivePreviewList-btn-reloadEmailList" class="button-qr">重置</a>
                        	<span href="javaScript:void(0);" id="messageEmail-queay-emailReceivePreviewList-advanced"></span>
						</form>
					</div>
				</div>
  			</div>			
			<div title="" data-options="region:'center'" border="false">
				<div id="messageEmail-emailReceivePreviewList-div-wrap" style="display:none ;"  data-options="fit:true" class="easyui-panel" data-options="fit:true" border="false">
					<div id="messageEmail-emailReceivePreviewList-layout-content" class="easyui-layout" data-options="fit:true" border="false">
						<div title="" data-options="region:'center'" style="height:auto;" >						 	 
	     					<table id="messageEmail-table-emailReceivePreviewList"></table>
						</div>
						<div title="邮件详情" data-options="region:'east',split:true" style="height:auto;width:400px;" border="false">
							<div class="easyui-panel" data-options="fit:true"  id="messageEmail-emailReceivePreviewList-panel" border="false"></div>
						</div>
					</div>
				</div>					
				<div id="messageEmail-emailReceivePreviewList-div-nodata"  class="easyui-panel" data-options="fit:true" border="false" style="display:none;">
					<div style="width:370px;height:98px;margin:100px auto 0; border:1px solid #9F9F9F;line-height:80px;">
						<div style="float:left; padding:10px;" class="img-extend-infow64">						
						</div>
						<div style="float:left;font-size: 16px;color:#6BAD42;">收件箱未找到邮件，请尝试重新查询</div>
					</div>
				</div>
				<div style="clear:both"></div>
			</div>
	</div>     
	<div style="display:none">
		<div id="messageEmail-dialog-emailBoxOper"></div>
	</div>
	<script type="text/javascript">

  	  
  	 	var emailReceivePreviewList_showMailBox=function(){
  	  	 	try{
  	  	  	 	var boxid='${boxid}';
  	  	  	 	if(isNaN(boxid)){
  	  	  	  	 	boxid=0;
  	  	  	 	}
        		var data=new Array();
  	  	  	 	if(boxid==0){
					$.ajax({
						url:'message/email/showEmailBoxList.do',
						data:'',
						type:'post',
						dataType:'json',
						cache:false,
						success:function(json){
							json=json||[];
			            	if(json.length>0){
			            		$.each(json,function(i,item){
				            		data.push("<div data-options=\"iconCls:'icon-extend-pointRight2'\" ");
				            		data.push(" onclick=\"javascript:emailReceivePreviewList_moveToMailBox('"+item.id+"');\" ")
				            		data.push(">");
				            		data.push(item.title);
				            		data.push("</div>");
								});
			            		$("#messageEmail-emailReceivePreviewList-div-showEmailBox").html(data.join(""));
			            	}
			            },
			            complete:function(){
		        			$("#messageEmail-emailReceivePreviewList-btn-showMovingOper").menubutton({
			        			menu:'#messageEmail-emailReceivePreviewList-div-showEmailBox'
				        	});
			            }
					}); 
  	  	  	 	}else{
            		data.push("<div data-options=\"iconCls:'icon-extend-pointRight2'\" ");
            		data.push(" onclick=\"javascript:emailReceivePreviewList_moveToMailBox('0');\" ")
            		data.push(">");
            		data.push("收件箱");
            		data.push("</div>");
            	
            		$("#messageEmail-emailReceivePreviewList-div-showEmailBox").html(data.join(""));
        			$("#messageEmail-emailReceivePreviewList-btn-showMovingOper").menubutton({
	        			menu:'#messageEmail-emailReceivePreviewList-div-showEmailBox'
		        	});
  	  	  	 	}
  	  	 	}catch(e){
            	$("#messageEmail-emailReceivePreviewList-div-showEmailBox").html("").hide();
    			$("#messageEmail-emailReceivePreviewList-btn-showMovingOper").menubutton();
  	  	 	} 	  	 	
  	 	};
  	 	var emailReceivePreviewList_readEmail=function(idarr,emlid,rowIndex){
			try{
	  	  	 	if(idarr!=null && idarr!="" ){
		  	 		$.ajax({   
			            url :'message/email/readEmailReceive.do',
			            type:'post',
			            dataType:'json',
			            data:{ids:idarr},
			            success:function(json){
				            if(rowIndex!=null && !isNaN(rowIndex)){
								$("#messageEmail-table-emailReceivePreviewList").datagrid('updateRow',{
									index:rowIndex,
									row:{viewflag:0}
								});
				            }else{
								$("#messageEmail-table-emailReceivePreviewList").datagrid('loadData', { total: 0, rows: [] });
								$("#messageEmail-table-emailReceivePreviewList").datagrid('reload');
				            }
				            if(rowIndex!=null && !isNaN(rowIndex)){
				            	emailReceivePreviewList_sendReceiptAfReadEmail(emlid);
				            }
			            }
		        	});
	  	  	 	}
			}catch(e){
			}
  	 	}
  	 	var emailReceivePreviewList_sendReceiptAfReadEmail=function(emlid){
			try{
	  	  	 	if(emlid!=null && emlid!="" ){
		  	 		$.ajax({   
			            url :'message/email/sendReceiptAfReadEmail.do',
			            type:'post',
			            dataType:'json',
			            data:{emailid:emlid},
			            success:function(json){
			            }
		        	});
	  	  	 	}
			}catch(e){
			}
		}
  	 	var emailReceivePreviewList_showEmailContent=function(emailid){
		    try{
			   	var emailid=emailid||"";
			    if(emailid!=""){
			    	$("#messageEmail-emailReceivePreviewList-panel").panel({
						fit:true,
						border:false,
						title:'',
						href:'message/email/emailDetailPage.do?showsendu=1&showoper=0&id='+emailid,
					    cache:false,
					    maximized:true
					});	
			    }
		    }catch(e){
		    }
  	 	}
  	 	var emailReceivePreviewList_moveToMailBox=function(boxid){
  	  	 	if(typeof(boxid) == "undefined" || boxid==null){
				return false;
  	  	 	}
	  	  	var $emailReceivePreviewList=$("#messageEmail-table-emailReceivePreviewList");
			if($emailReceivePreviewList !=null){
				var dataRows = $emailReceivePreviewList.datagrid('getChecked');
		    	if(dataRows==null){
		    		$.messager.alert("提醒","请选择邮件信息！");
		    		return false;
		    	}
		    	var idarr=Enumerable.from(dataRows).select("$.id").toArray() || [];
		    	if(idarr.legnth==0){
			    	return false;
		    	}
		    	$.ajax({   
		            url :'message/email/moveToEmailBox.do',
		            type:'post',
		            dataType:'json',
		            data:{ids:idarr.join(","),boxid:boxid},
		            success:function(json){
						$("#messageEmail-table-emailReceivePreviewList").datagrid('reload');
		            }
        		});
			}	
  	 	}
  	 	var emailReceivePreviewList_curRowIndex=0;
  	 	
		$(document).ready(function(){
			$("#messageEmail-queay-emailReceivePreviewList-advanced").advancedQuery({
		 		name:'msg_emailcontent',
		 		datagrid:'messageEmail-table-emailReceivePreviewList'
			});
			
	  	 	$('#messageEmail-table-emailReceivePreviewList').datagrid({ 
	  	 		fit:true,
	            striped: true,
	  	 		method:'post',
	  	 		title:'',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
                pageSize:100,
			    url:'message/email/showEmailReceivePageList.do?boxid=${boxid}',  
			    columns:[[ 
					{field : 'idck',checkbox : true}, 
			        {field:'addusername',title:'发件人',width:110,
				        formatter: function(value,row,index){
				        	if(row.senduserid=='system'){
					        	return "系统";
				        	}else{
				        		if(row.emailContent && row.emailContent.addusername){
					    			return row.emailContent.addusername;
				        		}else {
				        			return "";
				        		}
				        	}
		        		}
		        	},  
			        {field:'title',title:'标题',width:200,
				        formatter: function(value,row,index){
				        	try{
					        	var html=new Array();
					        	if(row.viewflag==1){
						        	html.push("<span class=\"img-extend-emailnew\" style=\"float:left;\">&nbsp;</span>");
					        	}
					        	if(row.emailContent){
						        	if(row.emailContent.importantflag==1){
							        	html.push("<span style='color:#f00;'>");
							        	html.push( row.emailContent.title);
							        	html.push("</span>");
						        	}else if( row.emailContent.importantflag==2){
							        	html.push("<span style='color:#f00;font-weight:bold;'>");
							        	html.push( row.emailContent.title);
							        	html.push("</span>");
						        	}else{
							        	html.push( row.emailContent.title);
						        	}
					        	}
					        	return html.join("");
				        	}catch(e){
					        	return "";
				        	}
		        		}
	        		},  
			        {field:'recvtime',title:'时间',width:75,
				        formatter: function(value,row,index){
				        	value=value||"";
				        	if(value!=""){
					        	return value.split(" ")[0];
				        	}
			        	}
			        },  
			        {field:'attach',title:'附件',width:75,
				        formatter: function(value,row,index){
				        	if(row.emailContent!=null 
				        			&& row.emailContent.attach!=null
				        			&& row.emailContent.attach.length>0){
				        		return "有";
				        	}
				        	return "";				        	
			        	}
			        }
			    ]],
			    sortName:'emlc_addtime',
			    sortOrder:'desc',
			    onClickRow:function(rowIndex, rowData){
				    emailReceivePreviewList_showEmailContent(rowData.emailid);
				    if(rowData.viewflag==1){
			  	  		emailReceivePreviewList_readEmail(rowData.id,rowData.emailid,rowIndex);
				    }
			    },
			    onLoadSuccess:function(data){
				    if(data.rows.length>0){
				    	$("#messageEmail-emailReceivePreviewList-div-wrap").show();
				    	$("#messageEmail-emailReceivePreviewList-div-nodata").hide();
				    	$("#messageEmail-emailReceivePreviewList-layout-content").layout("resize");
					    try{
						   	var id=data.rows[0].emailid||"";
						    if(id!=""){
						    	emailReceivePreviewList_showEmailContent(id);
								if(data.rows[0].viewflag==1){
					  	  			emailReceivePreviewList_readEmail(data.rows[0].id,data.rows[0].emailid,0);
								}	
						    }
					    }catch(e){
					    }
			    	}else{
				    	$("#messageEmail-emailReceivePreviewList-div-wrap").hide();
				    	$("#messageEmail-emailReceivePreviewList-div-nodata").show();
				    	
			    	}
			    }
			}).datagrid("columnMoving"); 
			
			$("#messageEmail-emailReceivePreviewList-btn-showMoreOper").menubutton({  
			    menu:'#messageEmail-emailReceivePreviewList-div-moreOperDiv'
			});

			emailReceivePreviewList_showMailBox();
			//查询
			$("#messageEmail-emailReceivePreviewList-btn-queryEmailList").click(function(){
				//查询参数直接添加在url中         
				var queryJSON=$("#messageEmail-form-emailReceivePreviewList").serializeJSON();
				//重新赋值url 属性
	        	$("#messageEmail-table-emailReceivePreviewList").datagrid('load',queryJSON);
			});
			//重置
			$("#messageEmail-emailReceivePreviewList-btn-reloadEmailList").click(function(){
				$("#messageEmail-form-emailReceivePreviewList")[0].reset();
    			//重新赋值url 属性
				$("#messageEmail-table-emailReceivePreviewList").datagrid('load', {});
			});	
			$("#messageEmail-emailReceivePreviewList-btn-deleteReceiveEmail").click(function(){
				//删除接收邮件
				var $emailReceivePreviewList=$("#messageEmail-table-emailReceivePreviewList");
				var dataRow = $emailReceivePreviewList.datagrid('getChecked');
		    	if(dataRow==null){
		    		$.messager.alert("提醒","请选择邮件信息！");
		    		return false;
		    	}
		    	var idarr=Enumerable.from(dataRow).select("$.id").toArray();
		    	if(idarr.length==0){
		    		$.messager.alert("提醒","请选择邮件信息！");
		    		return false;
		    	}
				$.messager.confirm("提醒", "是否要把选中的邮件放入到废纸篓?", function(r) {
				if (r) {
			    	$.ajax({   
			            url :'message/email/deleteEmailReceive.do',
			            type:'post',
			            dataType:'json',
			            data:{dstate:2,ids:idarr.join(',')},
			            success:function(json){
		            		$emailReceivePreviewList.datagrid('reload');
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
			$("#messageEmail-emailReceivePreviewList-btn-destroyReceiveEmail").click(function(){
				//销毁邮件
				var $emailReceivePreviewList=$("#messageEmail-table-emailReceivePreviewList");
				var dataRow = $emailReceivePreviewList.datagrid('getChecked');
		    	if(dataRow==null){
		    		$.messager.alert("提醒","请选择邮件信息！");
		    		return false;
		    	}
		    	var idarr=Enumerable.from(dataRow).select("$.id").toArray();
		    	if(idarr.length==0){
		    		$.messager.alert("提醒","请选择邮件信息！");
		    		return false;
		    	}
				$.messager.confirm("提醒", "是否销毁选中的邮件？注意，销毁后不可恢复！", function(r) {
				if (r) {
			    	$.ajax({   
			            url :'message/email/deleteEmailReceive.do',
			            type:'post',
			            dataType:'json',
			            data:{dstate:0,ids:idarr.join(',')},
			            success:function(json){
		            		$emailReceivePreviewList.datagrid('reload');
			            	$.messager.alert("提醒","删除已完成！");
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
			$("#messageEmail-emailReceivePreviewList-btn-deleteReadEmail").click(function(){
				//删除已读邮件
				var $emailReceivePreviewList=$("#messageEmail-table-emailReceivePreviewList");
				if($emailReceivePreviewList !=null){
					var dataRows = $emailReceivePreviewList.datagrid('getChecked');
			    	if(dataRows==null){
			    		$.messager.alert("提醒","请选择邮件信息！");
			    		return false;
			    	}
			    	var idarr=Enumerable.from(dataRows).select("$.id").toArray();
			    	if(idarr.length==0){
			    		$.messager.alert("提醒","请选择邮件信息！");
			    		return false;
			    	}
			    	$.messager.confirm("提醒", "确认要删除所有已读邮件?", function(r) {
						if (r) {
							$.ajax({   
					            url :'message/email/deleteReadEmailReceive.do',
					            type:'post',
					            dataType:'json',
					            data:{ids:idarr.join(","),boxid:'${boxid}'},
					            success:function(json){
									$("#messageEmail-table-emailReceivePreviewList").datagrid('reload');
					            	$.messager.alert("提醒","操作已完成！");
					            }
			        		});
				    	}
					});
				}
			});
			
			$("#messageEmail-emailReceivePreviewList-btn-readEmail").click(function(){
				//标记邮件已阅
				var $emailReceivePreviewList=$("#messageEmail-table-emailReceivePreviewList");
				if($emailReceivePreviewList !=null){
					var dataRows = $emailReceivePreviewList.datagrid('getChecked');
			    	if(dataRows==null){
			    		$.messager.alert("提醒","请选择邮件信息！");
			    		return false;
			    	}
			    	var idarr=Enumerable.from(dataRows).select("$.id").toArray();
			    	idarr=idarr||[];
			    	if(idarr.length==0){
			    		$.messager.alert("提醒","请选择邮件信息！");
			    		return false;
			    	}
			    	$.messager.confirm("提醒", "确认要将所选的邮件标志为已读?", function(r) {
						if (r) {
				  	  		emailReceivePreviewList_readEmail(idarr.join(","));
				    	}
					});
				}
			});	
			$("#messageEmail-emailReceivePreviewList-btn-readAllEmail").click(function(){
				//标志所有邮件已读
				var $emailReceivePreviewList=$("#messageEmail-table-emailReceivePreviewList");
				if($emailReceivePreviewList !=null){
					var dataRows = $emailReceivePreviewList.datagrid('getChecked');
			    	if(dataRows==null){
			    		$.messager.alert("提醒","请选择邮件信息！");
			    		return false;
			    	}
			    	var idarr=Enumerable.from(dataRows).select("$.id").toArray();
			    	$.messager.confirm("提醒", "确认要将收件箱中全部邮件标志为已读?", function(r) {
						if (r) {
							$.ajax({   
					            url :'message/email/readAllEmailReceive.do',
					            type:'post',
					            dataType:'json',
					            data:"",
					            success:function(json){
					            	$("#messageEmail-table-emailReceivePreviewList").datagrid('loadData', { total: 0, rows: [] });
									$("#messageEmail-table-emailReceivePreviewList").datagrid('reload');
					            	$.messager.alert("提醒","操作已完成！");
					            }
			        		});
				    	}
					});
				}			
			});

			$("#messageEmail-emailReceivePreviewList-btn-forwardEmail").click(function(){
				//转发邮件
				var $emailReceivePreviewList=$("#messageEmail-table-emailReceivePreviewList");
				if($emailReceivePreviewList !=null){
					var dataRows = $emailReceivePreviewList.datagrid('getSelected');
			    	if(dataRows==null || dataRows.emailid==""){
			    		$.messager.alert("提醒","请选择要转发的邮件信息！");
			    		return false;
			    	}
			    	emailPage_openPanel('message/email/emailReplyPage.do?oper=forward&id='+dataRows.emailid);
				}
			});
			$("#messageEmail-emailReceivePreviewList-btn-replyEmail").click(function(){
				//回复邮件
				var $emailReceivePreviewList=$("#messageEmail-table-emailReceivePreviewList");
				if($emailReceivePreviewList !=null){
					var dataRows = $emailReceivePreviewList.datagrid('getSelected');
			    	if(dataRows==null || dataRows.emailid==""){
			    		$.messager.alert("提醒","请选择要回复的邮件信息！");
			    		return false;
			    	}
			    	emailPage_openPanel('message/email/emailReplyPage.do?oper=reply&id='+dataRows.emailid);
				}
			});


			$("#messageEmail-emailReceivePreviewList-btn-replyEmailAll").click(function(){
				//回复邮件
				var $emailReceivePreviewList=$("#messageEmail-table-emailReceivePreviewList");
				if($emailReceivePreviewList !=null){
					var dataRows = $emailReceivePreviewList.datagrid('getSelected');
			    	if(dataRows==null || dataRows.emailid==""){
			    		$.messager.alert("提醒","请选择要回复的邮件信息！");
			    		return false;
			    	}
			    	emailPage_openPanel('message/email/emailReplyPage.do?oper=replyall&id='+dataRows.emailid);
				}
			});			
			
			$("#messageEmail-emailReceivePreviewList-btn-prevmail").click(function(){
				//上一封邮件
				var $emailReceivePreviewList=$("#messageEmail-table-emailReceivePreviewList");
				if($emailReceivePreviewList !=null){
					var dataRows=$emailReceivePreviewList.datagrid('getRows');
					if(dataRows==null ){
						return false;
					}
					var index=emailReceivePreviewList_curRowIndex || 0;
					index=index-1;
					if(index<0){
						index=0;
					}
					emailReceivePreviewList_curRowIndex=index;
					$emailReceivePreviewList.datagrid('clearSelections');
					$emailReceivePreviewList.datagrid('selectRow',index);
				    emailReceivePreviewList_showEmailContent(dataRows[index].emailid);
				}				
			});
			$("#messageEmail-emailReceivePreviewList-btn-nextmail").click(function(){
				//下一封邮件
				var $emailReceivePreviewList=$("#messageEmail-table-emailReceivePreviewList");
				if($emailReceivePreviewList !=null){
					var dataRows=$emailReceivePreviewList.datagrid('getRows');
					if(dataRows==null ){
						return false;
					}
					
					var index=emailReceivePreviewList_curRowIndex || 0;
					index=index+1
					if(index>=dataRows.length){
						index=dataRows.length-1;
					}
					emailReceivePreviewList_curRowIndex=index;
					$emailReceivePreviewList.datagrid('clearSelections');
					$emailReceivePreviewList.datagrid('selectRow',index);
				    emailReceivePreviewList_showEmailContent(dataRows[index].emailid);
				}
			});
		});
  	 </script>
  </body>
</html>



