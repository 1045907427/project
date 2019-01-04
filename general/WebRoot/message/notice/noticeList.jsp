<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>公告通知列表</title>    
		<%@include file="/include.jsp" %> 	
	  	<script type="text/javascript" src="js/kindeditor/kindeditor-min.js"></script> 
	  	<script type="text/javascript" src="js/kindeditor/lang/zh_CN.js"></script> 
	  	<script type="text/javascript" src="js/linq.min.js"></script>
    	<script type="text/javascript" src="js/jquery.upload.js"></script>
		<link rel="stylesheet" href="css/icon-extend.css" type="text/css"/>
	</head>

	<body>
		<table id="messageNotice-table-showNoticeList"></table>
	     <div id="messageNotice-query-showNoticeList" style="padding:2px;height:auto">
             <form action="" id="messageNotice-form-noticeList" method="post">
                 <table class="querytable">
                     <tr>
                         <security:authorize url="/message/notice/setNoticeReadFlag.do">
                             <a href="javaScript:void(0);" id="messageNotice-noticeList-btn-setNoticeReadFlag" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-readmess'">标记已读</a>
                         </security:authorize>
                         <security:authorize url="/message/notice/noticeDetailPage.do">
                             <a href="javaScript:void(0);" id="messageNotice-noticeList-btn-showpage-noticeDetail" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-view'">查看公告通知</a>
                         </security:authorize>
                         <span id="messageNotice-queay-queryNoticeList-advanced"></span>
                     </tr>
                     <tr>
                         <td>发布时间:</td>
                         <td>
                             <input name="startpublishtime" style="width:90px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                             到 <input name="endpublishtime" style="width:90px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                         </td>
                         <td>标题内容：</td>
                         <td><input type="text" name="tlecont" style="width:150px"></td>
                         <td>是否已读：</td>
                         <td><select name="readcount" style="width: 150px;"><option value="0" selected="selected">未读</option><option value="1">已读</option></select></td>
                     </tr>
                     <tr>
                         <td>接收人员：</td>
                         <td>
                            <input type="text" name="queryreceiver" id="messageNotice-form-noticeList-receiver" style="width:200px;" />
                         </td>
                         <td>接收部门：</td>
                         <td>
                             <input type="text" name="querydept" id="messageNotice-form-noticeList-dept" />
                         </td>
                         <td>接收角色：</td>
                         <td><input type="text" name="queryrole" id="messageNotice-form-noticeList-role" /></td>
                     </tr>
                     <tr>
                         <td>发布人：</td>
                         <td>
                             <input type="text" name="adduserid" id="messageNotice-form-noticeList-publisher" style="width:200px;" />
                         </td>
                         <td colspan="2"></td>
                         <td colspan="2">
                             <a href="javaScript:void(0);" id="messageNotice-btn-queryNoticeList" class="button-qr">查询</a>
                             <a href="javaScript:void(0);" id="messageNotice-btn-reloadNoticeList" class="button-qr">重置</a>
                         </td>
                     </tr>
                 </table>
             </form>
		</div>
        <div style="display: none">
            <div id="messageNotice-dialog-publishRangeList"></div>
            <div id="messageNotice-dialog-noticeListOper"></div>
        </div>
		<script type="text/javascript">
			var msgNoticeList_showNotice=function(id){
				if(id==null || $.trim(id)=="" || isNaN(id)){
					return false;
				}
				<security:authorize url="/message/notice/noticeDetailPage.do">
				var url="message/notice/noticeDetailPage.do?noticeid="+id;
				$('<div id="messageNotice-dialog-noticeListOper-content"></div>').appendTo("#messageNotice-dialog-noticeListOper");
				var $noticeListOper=$("#messageNotice-dialog-noticeListOper");
				$noticeListOper.dialog({
					title:'查看公告通知',
				    width: 800,  
				    height: 450,
		            top:($(window).height() - 450) * 0.5, 
		            left:($(window).width() - 800) * 0.5, 
				    closed: true,  
				    cache: false, 
				    href:url,
				    modal: true,  
				    onClose:function(){
                        $noticeListOper.window("destroy");
				    }
				});
				$noticeListOper.dialog("open");	
				</security:authorize>
			}
			
	 		$(document).ready(function(){
                var initQueryJSON = $("#messageNotice-form-noticeList").serializeJSON();
	 			$("#messageNotice-table-showNoticeList").datagrid({	
	 				fit:true,
	            	striped: true,
		  	 		method:'post',
		  	 		title:'',
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
					toolbar:'#messageNotice-query-showNoticeList',
				    url:'message/notice/showNoticePageList.do',
                    queryParams:initQueryJSON,
				    columns:[[  
						{field : 'idck',checkbox : true}, 
				        {field:'addusername',title:'发布人',width:120,sortable:true},
				        {field:'typename',title:'分类',width:120,
				        		formatter:function(value,row,index){
				        			return "未知";
				        		}
				        },  
				        {field:'title',title:'标题',width:300,
				        	formatter: function(value,row,index){
				        		if(row.istop!=0){
					        		return "<span style='color:#f00;font-weight:bold;'>"+value+"</span>"
				        		}else{
					        		return value;
				        		}
				        }},  
					    {field:'publishrang',title:'发布范围',width:250,
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
				        {field:'publishtime',title:'发布日期',width:120,
							formatter:function(value,row,index){
								if(row.state==1){
									return value;
								}
							}
						},
				        {field:'readcount',title:'是否已读',width:100,
				        		formatter:function(value,row,index){
				        			if(value=="1"){
				        				return "已读";
				        			}else{
				        				return "未读";
				        			}
				        }}
				    ]],
				    onDblClickRow:function(rowIndex, rowData){
			    		msgNoticeList_showNotice(rowData.id);
			    	}
	 			});
	 			$("#messageNotice-queay-queryNoticeList-advanced").advancedQuery({
			 		name:'msg_notice',
			 		datagrid:'messageNotice-table-showNoticeList'
				});
	 			$("#messageNotice-noticeList-btn-setNoticeReadFlag").click(function(){
	 				$.messager.confirm("提醒","是否要标志所选的公告通知为已读?",function(r){
		   				if(r){    					
		   					var $showNoticeList=$("#messageNotice-table-showNoticeList");
							var noticeRows = $showNoticeList.datagrid('getChecked');
					    	if(noticeRows==null || noticeRows.length==0){
					    		$.messager.alert("提醒","请选择要标志的公告通知！");
					    		return false;
					    	}
					    	var readrow=Enumerable.from(noticeRows).where("$.readcount==0");
					    	var readcount=readrow.count();
					    	if(readcount==0){
					    		$.messager.alert("提醒","已经标志成功！");
					    		return false;					    		
					    	}
					    	var idarr=readrow.select("$.id").toArray();
					    	if(idarr.length==0){
					    		$.messager.alert("提醒","请选择要标志的公告通知！");
					    		return false;					    		
					    	}
					    	$.ajax({   
					            url :'message/notice/addNoticeread.do',
					            data:{ids:idarr.join(",")},
					            type:'post',
					            dataType:'json',
					            success:function(json){
					            	if(json.flag==true){
					            		$.messager.alert("提醒","标志成功！");
					            		$showNoticeList.datagrid('reload');
					            	}else{
					            		$.messager.alert("提醒",(json.msg || "标志失败！"));
					            	}
					            }
					        });
		   				}
		   			});
	 			});
	 			
				$("#messageNotice-btn-queryNoticeList").click(function(){
					//查询参数直接添加在url中         
	       			var queryJSON = $("#messageNotice-form-noticeList").serializeJSON();					
	 				$('#messageNotice-table-showNoticeList').datagrid('load',queryJSON);
				});
				$("#messageNotice-btn-reloadNoticeList").click(function(){
                    $("#messageNotice-form-noticeList-publisher").widget("clear");
                    $("#messageNotice-form-noticeList-receiver").widget("clear");
                    $("#messageNotice-form-noticeList-dept").widget("clear");
                    $("#messageNotice-form-noticeList-role").widget("clear");
					$("#messageNotice-form-noticeList")[0].reset();
                    var queryJSON = $("#messageNotice-form-noticeList").serializeJSON();
                    $('#messageNotice-table-showNoticeList').datagrid('load',queryJSON);
				});
				$("#messageNotice-noticeList-btn-showpage-noticeDetail").click(function(){
					var $showNoticeList=$("#messageNotice-table-showNoticeList");
					var noticerows=$showNoticeList.datagrid('getSelections');
					if(noticerows.length==0 ){
		    			$.messager.alert("提醒","请选择要查看的公告通知！");
		    			return false;
    				}
    				if(noticerows.length>1){
		    			$.messager.alert("提醒","抱歉，一次只能查看一条公告通知！");
		    			return false;
    				}
    				msgNoticeList_showNotice(noticerows[0].id);				
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

            $("#messageNotice-form-noticeList-receiver").widget({
                name:'t_msg_notice',
                col:'receiveuser',
                singleSelect:false,
                width:205,
                onlyLeafCheck:false
            });
            $("#messageNotice-form-noticeList-dept").widget({
                name:'t_msg_notice',
                col:'receivedept',
                singleSelect:false,
                width:150,
                onlyLeafCheck:false
            });
            $("#messageNotice-form-noticeList-role").widget({
                name:'t_msg_notice',
                col:'receiverole',
                singleSelect:false,
                width:150,
                onlyLeafCheck:false
            });
            $("#messageNotice-form-noticeList-publisher").widget({
                name:'msg_notice',
                col:'adduserid',
                singleSelect:true,
                width:205,
                onlyLeafCheck:false
            });

        </script>
	</body>
</html>