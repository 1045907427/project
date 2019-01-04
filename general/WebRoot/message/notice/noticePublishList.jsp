<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>公告管理列表</title>    
		<%@include file="/include.jsp" %> 	
	  	<script type="text/javascript" src="js/kindeditor/kindeditor-min.js"></script> 
	  	<script type="text/javascript" src="js/kindeditor/lang/zh_CN.js"></script> 
	  	<script type="text/javascript" src="js/linq.min.js"></script>
		<link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>
	  	<script type="text/javascript" src="js/colorPicker/jquery.colorPicker.min.js"></script>
	  	<link rel="stylesheet" href="js/colorPicker/colorPicker.css" type="text/css" />

	</head>

	<body>
	
  	<div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="messageNotice-buttons-addNoticeListPage" style="height:26px;"></div>
    	</div>
    	<div data-options="region:'center'">    		
			<table id="messageNotice-table-showNoticePublishList"></table>
		     <div id="messageNotice-query-showNoticePublishList" style="padding:5px;height:auto">
				<div>
					<form action="" id="messageNotice-form-noticePublishList" method="post">
						<table class="querytable">
							<tr>
								<td>发布时间：</td>
								<td>
									<input name="startaddtime" style="width:100px" class="Wdate" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
									到<input name="endaddtime" style="width:100px" class="Wdate" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
								</td>
								<td>标题内容：</td>
								<td><input name="tlecont" style="width:150px" /></td>
								<td>
									状态：
								</td>
								<td>
									<input type="text" id="messageNotice-form-noticePublishList-state" name="state" />
								</td>
							</tr>
							<tr>
								<td>接收人员：</td>
								<td>
									<input type="text" name="queryreceiver" id="messageNotice-form-noticePublishList-receiver" style="width:200px;" />
								</td>
								<td>接收部门：</td>
								<td>
									<input type="text" name="querydept" id="messageNotice-form-noticePublishList-dept" />
								</td>
								<td>接收角色：</td>
								<td><input type="text" name="queryrole" id="messageNotice-form-noticePublishList-role" /></td>
							</tr>
							<tr>
								<td>发布人：</td>
								<td>
									<input type="text" name="adduserid" id="messageNotice-form-noticePublishList-publisher" style="width:200px;" />
								</td>
								<td colspan="2"></td>
								<td colspan="2">
									<a href="javaScript:void(0);" id="messageNotice-btn-queryNoticePublishList" class="button-qr">查询</a>
									<a href="javaScript:void(0);" id="messageNotice-btn-reloadNoticePublishList" class="button-qr">重置</a>
									<span href="javaScript:void(0);" id="messageNotice-queay-queryNoticePublishList-advanced"></span>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div style="display: none">
		<div id="messageNotice-dialog-noticePublishOper"></div>
		<div id="messageNotice-dialog-publishRangeList"></div>
	</div>
		<script type="text/javascript">
	 		$(document).ready(function(){ 
	 			//按钮
				$("#messageNotice-buttons-addNoticeListPage").buttonWidget({
					initButton:[
						{},
						<security:authorize url="/message/notice/noticeAddBtn.do">
						{
							type:'button-add',
							handler: function(){
								top.addOrUpdateTab('message/notice/noticePage.do','公告通知管理');
							}
						},
						</security:authorize>
						<security:authorize url="/message/notice/noticeEditBtn.do">
						{
							type:'button-edit',
							handler: function(){
								var datarow = $("#messageNotice-table-showNoticePublishList").datagrid('getSelected');
								if(datarow==null ||  datarow.id ==null){
				  		        	$.messager.alert("提醒","请选择要修改的公告通知");
									return false;
								}
								top.addOrUpdateTab('message/notice/noticePage.do?type=edit&id='+datarow.id,'公告通知管理');
							}
						},
						</security:authorize>
						<security:authorize url="/message/notice/noticePublishDeleteBtn.do">
						{
							type:'button-delete',
							handler: function(){
								var dataRows = $("#messageNotice-table-showNoticePublishList").datagrid('getChecked');
                                if(dataRows.length==0){
                                    $.messager.alert("提醒","注意：请勾选要删除公告通知！");
                                    return false;
                                }
                                //只有一条启用的记录时的处理方法
                                if(dataRows[0].state == "1" && dataRows.length == 1){
                                    $.messager.alert("提醒","删除失败，启用状态下不允许删除");
                                    return false;
                                }
								$.messager.confirm("删除确认","确定要删除吗?",function(r){
					   				if(r){
						   				var idarr=new Array();
						   				for(var i=0;i<dataRows.length;i++){
							   				if(dataRows[i].id && dataRows[i].id !=""){
								   				idarr.push(dataRows[i].id);
							   				}
						   				}
								    	$.ajax({   
								            url :'message/notice/noticeDelete.do',
								            data:{ids:idarr.join(",")},
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	if(json.flag==true){
								            		$("#messageNotice-table-showNoticePublishList").datagrid('reload');
										            if(json.ismuti && json.ismuti==true){
								            			$.messager.alert("提醒", "删除成功！删除成功数："+ json.isuccess +"<br />删除失败数："+ json.ifailure );
										            }
								            	}else{
								            		$.messager.alert("提醒",(json.msg || "删除失败！"));
								            	}
								            }
								        });
					   				}
					   			});
							}
						},
						</security:authorize>
						<security:authorize url="/message/notice/noticeViewBtn.do">
						{
							type:'button-view',
							handler: function(){
								var datarow = $("#messageNotice-table-showNoticePublishList").datagrid('getSelected');
								if(datarow==null ||  datarow.id ==null){
				  		        	$.messager.alert("提醒","请选择要查看的公告通知");
									return false;
								}
								top.addOrUpdateTab('message/notice/noticePage.do?type=view&id='+datarow.id,'公告通知管理');
							}
						},
						</security:authorize>
						<security:authorize url="/message/notice/noticeEnable.do">
						{
							type:'button-open',
							handler: function(){
								var dataRows = $("#messageNotice-table-showNoticePublishList").datagrid('getChecked');
						    	if(dataRows.length==0){
						    		$.messager.alert("提醒","注意：请勾选要启用的公告通知！");
						    		return false;
						    	}
								$.messager.confirm("启用确认","确定要启用所勾选的公告通知吗?",function(r){
					   				if(r){
						   				var idarr=new Array();
						   				for(var i=0;i<dataRows.length;i++){
							   				if(dataRows[i].id && dataRows[i].id !=""){
								   				idarr.push(dataRows[i].id);
							   				}
						   				}
								    	if(idarr.length==0){
								    		$.messager.alert("提醒","请选择要启用的公告通知！");
								    		return false;					    		
								    	}
								    	$.ajax({   
								            url :'message/notice/noticeEnable.do',
								            data:{ids:idarr.join(",")},
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	if(json.flag==true){
								            		$("#messageNotice-table-showNoticePublishList").datagrid('reload');
								            		$.messager.alert("提醒", "启用成功！<br/>启用成功数："+ json.isuccess +"<br />启用失败数："+ json.ifailure);
								            	}else{
								            		$.messager.alert("提醒",(json.msg || "启用失败！"));
								            	}
								            }
								        });
					   				}
					   			});
							}
						},
						</security:authorize>
						<security:authorize url="/message/notice/noticeDisable.do">
						{
							type:'button-close',
							handler: function(){
								var dataRows = $("#messageNotice-table-showNoticePublishList").datagrid('getChecked');
						    	if(dataRows.length==0){
						    		$.messager.alert("提醒","注意：请勾选要禁用的公告通知！");
						    		return false;
						    	}
								$.messager.confirm("禁用确认","确定要禁用所勾选的公告通知吗?",function(r){
					   				if(r){
						   				var idarr=new Array();
						   				for(var i=0;i<dataRows.length;i++){
							   				if(dataRows[i].id && dataRows[i].id !=""){
								   				idarr.push(dataRows[i].id);
							   				}
						   				}
								    	if(idarr.length==0){
								    		$.messager.alert("提醒","请选择要禁用的公告通知！");
								    		return false;					    		
								    	}
								    	$.ajax({   
								            url :'message/notice/noticeDisable.do',
								            data:{ids:idarr.join(",")},
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	if(json.flag==true){
								            		$("#messageNotice-table-showNoticePublishList").datagrid('reload');
								            		$.messager.alert("提醒", "禁用成功！<br/>禁用成功数："+ json.isuccess +"<br />禁用失败数："+ json.ifailure );
								            	}else{
								            		$.messager.alert("提醒",(json.msg || "禁用失败！"));
								            	}
								            }
								        });
					   				}
					   			});
							}
						},
						</security:authorize>
                        {
                            type: 'button-commonquery',
                            attr:{
                                name:'msg_notice',
                                datagrid:'messageNotice-table-showNoticePublishList'
                            }
                        },
						{}
					],
					//新增自定义按钮
					buttons:[
					         	{},
								<security:authorize url="/message/notice/noticeReceiveReadBtn.do">
								{
									id:'messageNotice-button-noticeReceiveRead',
									name:'查看阅读情况',
									iconCls:'button-view',
									handler:function(){
										var $list=$("#messageNotice-table-showNoticePublishList");
										var datarow=$list.datagrid('getSelected');
										if(datarow==null || datarow.length==0 ){
							    			$.messager.alert("提醒","请选择要查看的公告通知记录！");
							    			return false;
					    				}
										var url="message/notice/noticereadListPage.do?noticeid="+datarow.id;
										$readOper=$("#messageNotice-dialog-noticePublishOper");
										$readOper.dialog({
											title:'查看阅读情况',
										    width: 550,
										    height: 400,
								            top:($(window).height() - 400) * 0.5,
								            left:($(window).width() - 550) * 0.5,
										    closed: true,  
										    cache: false, 
										    href:url,
										    modal: true
										});
										$readOper.dialog("open");
									}
								},
								</security:authorize>
								<security:authorize url="/message/notice/noticePublishRangeBtn.do">
								{
									id:'messageNotice-button-noticePublishRange',
									name:'查看发布范围',
									iconCls:'button-view',
									handler:function(){
										var $list=$("#messageNotice-table-showNoticePublishList");
										var datarow=$list.datagrid('getSelected');
										if(datarow==null || datarow.length==0 ){
							    			$.messager.alert("提醒","请选择要查看的公告通知记录！");
							    			return false;
					    				}
										var url="message/notice/noticePublishRangePage.do?noticeid="+datarow.id;
										$readOper=$("#messageNotice-dialog-noticePublishOper");
										$readOper.dialog({
											title:'查看发布范围',
										    width: 700,  
										    height: 450,
								            top:($(window).height() - 450) * 0.5, 
								            left:($(window).width() - 700) * 0.5, 
										    closed: true,  
										    cache: false, 
										    href:url,
										    modal: true
										});
										$readOper.dialog("open");
									}
								},
								</security:authorize>
					         	{}
					],
					model:'Base',
					type:'list',
					datagrid:'messageNotice-table-showNoticePublishList',
					tname:'msg_notice'
				});	
	 			var noticePublishList_noticeColListJson=$("#messageNotice-table-showNoticePublishList").createGridColumnLoad({
		 			name : 'msg_notice',
		 			frozenCol : [[
		 							{field : 'id',checkbox : true}
		 						]],
		 			commonCol : [[
						{field:'publishername',title:'发布人',width:120,sortable:true,isShow:true},
						{field:'form',title:'展现格式',width:80,
							formatter: function(value,row,index){
								if(value==2){
									return "MHT格式";
								}else if(value==3){
									return "超级链接";
								}else{
									return "普通格式";
								}
						}},  
						{field:'typename',title:'分类',width:80,sortable:true,isShow:true},  
						{field:'title',title:'标题',width:300,
							formatter: function(value,row,index){
								if(row.tcolor!=null && row.tcolor!=""){
									return '<label style="color:'+row.tcolor+'">'+value+'</label>';
								}else{
									return value;
								}
						}},  
						{field:'publishrang',title:'发布范围',width:250,isShow:true,
							formatter: function(value,row,index){
								var msgarr=new Array();
								if(row.receivedeptname !=null && row.receivedeptname!=""){
								    msgarr.push("<div style=\"line-height:25px;\">");
									msgarr.push("<a style='color:#00f;text-decoration: underline;cursor:pointer'");
									msgarr.push(" href=\"javascript:void(0);\" ");
									msgarr.push(" onclick=\"javascript:noticeSendList_showRangeList('"+row.id+"',1);\" ");
									msgarr.push(" title='");
									msgarr.push(row.receivedeptname);
									msgarr.push("'>");
									msgarr.push("部门</a>：");
									msgarr.push("<span title='");
									msgarr.push(row.receivedeptname);
									msgarr.push("'>");
									var recvdata=row.receivedeptname.split(",");
									if(recvdata.length>4){
										var data=new Array();
										for(var i=0;i<4;i++){
						    				data.push(recvdata[i]);
										}
										data.push("...");
										msgarr.push(data.join(","));
									}else{
										msgarr.push(row.receivedeptname);
									}
									msgarr.push("</span>");
									msgarr.push("</div>");
								}
								if( row.receiverolename !=null && row.receiverolename !=""){
                                    msgarr.push("<div style=\"line-height:25px;\">");
									msgarr.push("<a style='color:#00f;text-decoration: underline;cursor:pointer'");
									msgarr.push(" href=\"javascript:void(0);\" ");
									msgarr.push(" onclick=\"javascript:noticeSendList_showRangeList('"+row.id+"',2);\" ");
									msgarr.push(" title='");
									msgarr.push(row.receiverolename);
									msgarr.push("'>");
									msgarr.push("角色</a>：");
									msgarr.push("<span title='");
									msgarr.push(row.receiverolename);
									msgarr.push("'>");
									var recvdata=row.receiverolename.split(",");
									if(recvdata.length>4){
										var data=new Array();
										for(var i=0;i<4;i++){
						    				data.push(recvdata[i]);
										}
										data.push("...");
										msgarr.push(data.join(","));
									}else{
										msgarr.push(row.receiverolename);
									}
									msgarr.push("</span>");
									msgarr.push("</div>");
								}
								if( row.receiveusername !=null && row.receiveusername !=""){
                                    msgarr.push("<div style=\"line-height:25px;\">");
									msgarr.push("<a style='color:#00f;text-decoration: underline;cursor:pointer'");
                                    msgarr.push(" href=\"javascript:void(0);\" ");
									msgarr.push(" onclick=\"javascript:noticeSendList_showRangeList('"+row.id+"',3);\" ");
									msgarr.push(">");
									msgarr.push("人员</a>：");
									msgarr.push("<span>");
									var recvdata=row.receiveusername.split(",");
									if(recvdata.length>4){
										var data=new Array();
										for(var i=0;i<4;i++){
						    				data.push(recvdata[i]);
										}
										data.push("...");
										msgarr.push(data.join(","));
									}else{
										msgarr.push(row.receiveusername);
									}
									msgarr.push("</span>");
									msgarr.push("</div>");
								}
								return msgarr.join("");
						}},
						{field:'publishtime',title:'发布时间',width:120,isShow:true,					        	
						  	formatter:function(value,row,index){
								if(row.state==1){
									return value;
								}
							}
						},
						{field:'startdate',title:'生效日期',width:80},
						{field:'enddate',title:'终止日期',width:80},
						{field:'state',title:'状态',width:120,					        	
						  	formatter:function(value,row,index){
						        switch (value) {
									case "0":
										return "禁用";
									case "1":
										return "启用";
									case "2":
										return "保存";
									case "3":
										return "暂存";
									case "4":
										return "新增";
									default:
										return "";	
										break;
								}
						}},
						{field:'addtime',title:'添加时间',width:120},
						{field:'delfalg',title:'删除标志',width:120,hidden:true,
							formatter: function(value,row,index){
								if(value=="1"){
									return "未删除";
								}else{
									return "已删除";
								}
						}},
						{field:'istop',title:'置顶',width:60,sortable:true,order:'desc',
							formatter: function(value,row,index){
								if(value=="1"){
									return "是";
								}else{
									return "否";
								}
						}},
						{field:'topday',title:'置顶天数',width:60,sortable:true},
						{field:'ismsg',title:'是否短信提醒',width:60,sortable:true,
							formatter: function(value,row,index){
								if(value=="1"){
									return "是";
								}else{
									return "否";
								}
						}},
						{field:'keyword',title:'关键字',width:120},
						{field:'intro',title:'内容简介',width:120},
						{field:'tcolor',title:'标题颜色',width:120},
						{field:'url',title:'URL',width:120},
						{field:'adddeptid',title:'发布人所属部门',width:120,
							formatter: function(value,row,index){
								if(row.adddeptname!=null){
									return row.adddeptname;
								}
						}},
						{field:'modifyuserid',title:'修改人',width:120,
							formatter: function(value,row,index){
							if(row.modifyusername!=null){
								return row.modifyusername;
							}
						}},
						{field:'modifytime',title:'修改时间',width:120},
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
						{field:'attachment',title:'附件',width:70,
						    formatter: function(value,row,index){
						    	if(value!=null && value!=""){
									return "有";
						    	}
							}
						}
		 			]]
	 			});		
	 			$("#messageNotice-table-showNoticePublishList").datagrid({	
		 				fit:true,
		            	striped: true,
			  	 		method:'post',
			  	 		title:'公告管理列表',
			  	 		rownumbers:true,
			  	 		pagination:true,
						singleSelect : false,
				 		checkOnSelect:true,
				 		selectOnCheck:true,
	  	 				idField:'id',
						toolbar:'#messageNotice-query-showNoticePublishList',
					    url:'message/notice/showNoticePublishPageList.do',  
					    authority : noticePublishList_noticeColListJson,
					    frozenColumns : noticePublishList_noticeColListJson.frozen,
					    columns : noticePublishList_noticeColListJson.common,
					    sortName:'addtime',
					    sortOrder:'desc',
					    onDblClickRow:function(rowIndex, rowData){
							if(rowData!=null){
								top.addOrUpdateTab('message/notice/noticePage.do?type=view&id='+rowData.id,'公告通知管理');
					    	}
					    }
					}).datagrid("columnMoving");
					
//		 			$("#messageNotice-queay-queryNoticePublishList-advanced").advancedQuery({
//				 		name:'msg_notice',
//				 		datagrid:'messageNotice-table-showNoticePublishList'
//					});

				$("#messageNotice-btn-queryNoticePublishList").click(function(){
					//查询参数直接添加在url中         
	       			var queryJSON  = $("#messageNotice-form-noticePublishList").serializeJSON();					
	 				$('#messageNotice-table-showNoticePublishList').datagrid('load',queryJSON );
				});
				$("#messageNotice-btn-reloadNoticePublishList").click(function(){
				    $("#messageNotice-form-noticePublishList-publisher").widget("clear");
				    $("#messageNotice-form-noticePublishList-receiver").widget("clear");
                    $("#messageNotice-form-noticePublishList-dept").widget("clear");
                    $("#messageNotice-form-noticePublishList-role").widget("clear");
					$("#messageNotice-form-noticePublishList")[0].reset();
					$('#messageNotice-table-showNoticePublishList').datagrid('load', {});
				});
	 		});
            function noticeSendList_showRangeList(noticeid,type){
                if(noticeid==null || $.trim(noticeid)==""){
                    $.messager.alert('提醒 ','抱歉，无法查看发布范围');
                    return;
                }
				type=type||"";
                var url='';
                var iwidth=450;
                var iheight=450;
                var title="发布范围";
                if(type=="1"){
                    title=title+"（部门）";
                    url='message/notice/showNoticePublishRangeDepartPage.do?noticeid='+noticeid;
                }else if(type=="2"){
                    title=title+"（角色）";
                    url='message/notice/showNoticePublishRangeRolePage.do?noticeid='+noticeid;
                }else if(type=="3"){
                    title=title+"（人员）";
                    url='message/notice/showNoticePublishRangeUserPage.do?noticeid='+noticeid;
				}else {
                    $.messager.alert('提醒 ','抱歉，无法查看发布范围');
                    return;
				}
                $('<div id="messageNotice-dialog-publishRangeList-content"></div>').appendTo('#messageNotice-dialog-publishRangeList');
                var $noticeRangeOper=$('#messageNotice-dialog-publishRangeList-content');
                $noticeRangeOper.dialog({
                    title:title,
                    width: iwidth,
                    height: iheight,
                    left:($(window).width() - iwidth) * 0.5,
                    top:($(window).height() - iheight) * 0.5,
                    closed: true,
                    cache: false,
                    href:url,
                    modal: true,
                    onClose:function(){
                        $noticeRangeOper.dialog("destroy");
                    }
                });
                $noticeRangeOper.dialog("open");
            }
            $("#messageNotice-form-noticePublishList-state").widget({
                referwid:'RL_T_SYS_CODE_ENABLE',
                singleSelect:true,
                onlyLeafCheck:false,
                width:'150',
                param:[{field:'type',op:'equal',value:'state'}],
                onSelect:function(data){
                }
            });

            $("#messageNotice-form-noticePublishList-receiver").widget({
                name:'msg_notice',
                col:'receiveuser',
                singleSelect:false,
                width:220,
                onlyLeafCheck:false
            });
            $("#messageNotice-form-noticePublishList-dept").widget({
                name:'msg_notice',
                col:'receivedept',
                singleSelect:false,
                width:150,
                onlyLeafCheck:false
            });
            $("#messageNotice-form-noticePublishList-role").widget({
                name:'msg_notice',
                col:'receiverole',
                singleSelect:false,
                width:150,
                onlyLeafCheck:false
            });
            $("#messageNotice-form-noticePublishList-publisher").widget({
                name:'msg_notice',
                col:'adduserid',
                singleSelect:true,
                width:220,
                onlyLeafCheck:false
            });
            
		</script>
	</body>
</html>
