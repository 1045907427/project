<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>废纸篓管理界面</title>
  </head>
  
  <body> 
  		<div class="easyui-layout" data-options="fit:true" border="false">
			<div title="" data-options="region:'north'" border="false" style="height:70px;padding:2px 4px; background-color: height: 28px;background: #EFEFEF;border-bottom: 1px solid #CCC;">
				<div id="messageEmail-emailDropList-query-showQueryToolbar" style="height:auto">
					<div>
						<div style="float:left;">
							<a href="javaScript:void(0);" id="messageEmail-emailDropList-btn-recoverEmail" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-oppaudit'">恢复</a>
							<a href="javaScript:void(0);" id="messageEmail-emailDropList-btn-destroyEmail" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
						</div>
						<div style="float:left">				
							<a href="javaScript:void(0);" id="messageEmail-emailDropList-btn-showMoreOper" data-options="plain:true,iconCls:'icon-extend-subord'">更多</a>
							<div id="messageEmail-emailDropList-div-moreOperDiv" style="width:200px; ">
								<%-- <div id="messageEmail-emailDropList-btn-expxls" data-options="iconCls:'icon-extend-xls'">导出Excel--%>
								<%-- </div>--%>
								<%-- <div id="messageEmail-emailDropList-btn-expEmail" data-options="iconCls:'icon-extend-expeml'">导出Eml--%>
								<%-- </div>--%>
								<div id="messageEmail-emailDropList-btn-deleteAllEmail" data-options="iconCls:'button-delete'">清空废纸篓
								</div>
							</div>	
						</div>
						<div style="clear:both"></div>
					</div>
					<div>
						<form action="" id="messageEmail-form-emailDropList" method="post">
							标题内容:<input name="tlecont" style="width:200px;">
							<a href="javaScript:void(0);" id="messageEmail-emailDropList-btn-queryEmailList" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="messageEmail-emailDropList-btn-reloadEmailList" class="button-qr">重置</a>
							<span href="javaScript:void(0);" id="messageEmail-queay-emailDropList-advanced"></span>
						</form>
					</div>
				</div>
			</div>			
			<div title="" data-options="region:'center'" border="false">
				<div  id="messageEmail-emailDropList-div-wrap"  class="easyui-panel" data-options="fit:true" border="false">
					<div id="messageEmail-emailDropList-layout-content" class="easyui-layout" data-options="fit:true" border="false">
						<div title="" data-options="region:'center'" style="height:auto;" >						 	 
		    					<table id="messageEmail-table-emailDropList"></table>
						</div>
						<div title="邮件详情" data-options="region:'east',split:true" style="height:auto;width:400px;" border="false">
							<div id="messageEmail-emailDropList-panel" border="false"></div>
						</div>
					</div>
				</div>
				<div id="messageEmail-emailDropList-div-nodata"  class="easyui-panel" data-options="fit:true" border="false">					
					<div style="width:370px;height:98px;margin:100px auto 0; border:1px solid #9F9F9F;line-height:80px;">
						<div style="float:left; padding:10px;" class="img-extend-infow64">						
						</div>
						<div style="float:left;font-size: 16px;color:#6BAD42;">废纸篓未找到邮件，请尝试重新查询</div>
					</div>
				</div>
			</div>
		</div>
		<div style="clear:both"></div>	
		
	<script type="text/javascript">
		$(document).ready(function(){  	 	
			$("#messageEmail-queay-emailDropList-advanced").advancedQuery({
		 		name:'msg_emailcontent',
		 		datagrid:'messageEmail-table-emailDropList'
			});
			$('#messageEmail-table-emailDropList').datagrid({ 
		 		fit:true,
	        	striped: true,
		 		method:'post',
		 		title:'',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		singleSelect:true,
                pageSize:100,
			    sortName:'emlc_addtime',
			    sortOrder:'desc',
			    url:'message/email/showEmailDropPageList.do',  
			    columns:[[ 
					{field : 'idck',checkbox : true}, 
					{field : 'id',title:"编号", width:50}, 
			        {field:'addusername',title:'发件人',width:110,
				        formatter: function(value,row,index){
				        	if(row.emailContent && row.emailContent.addusername){
					        	return row.emailContent.addusername;
				        	}else{
					        	return "";
				        	}
		        		}
		        	},  
			        {field:'title',title:'标题',width:200,
				        formatter: function(value,row,index){
				        	if(row.emailContent && row.emailContent.title){
					        	return row.emailContent.title;
				        	}else{
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
			    onClickRow:function(rowIndex, rowData){
				    try{
					   	var id=rowData.emailid||"";
					    if(id!=""){
					    	$("#messageEmail-emailDropList-panel").panel({
								fit:true,
								title:'',
								href:'message/email/emailDetailPage.do?showoper=0&id='+id,
							    cache:false,
							    maximized:true
							});	
					    }
				    }catch(e){
				    }
			    },
			    onLoadSuccess:function(data){
				    if(data.rows.length>0){
				    	$("#messageEmail-emailDropList-div-wrap").show();
				    	$("#messageEmail-emailDropList-div-nodata").hide();
				    	$("#messageEmail-emailDropList-layout-content").layout("resize");
					    try{
						   	var id=data.rows[0].emailid||"";
						    if(id!=""){
						    	$("#messageEmail-emailDropList-panel").panel({
									fit:true,
									title:'',
									href:'message/email/emailDetailPage.do?showoper=0&id='+id,
								    cache:false,
								    maximized:true
								});	
						    }
					    }catch(e){
					    }
			    	}else{
				    	$("#messageEmail-emailDropList-div-wrap").hide();
				    	$("#messageEmail-emailDropList-div-nodata").show();
			    	}
			    }
			});  
			
			$("#messageEmail-emailDropList-btn-showMoreOper").splitbutton({  
			    menu:'#messageEmail-emailDropList-div-moreOperDiv'
			});
			//查询
			$("#messageEmail-emailDropList-btn-queryEmailList").click(function(){
				//查询参数直接添加在url中         
				var queryJSON=$("#messageEmail-form-emailDropList").serializeJSON();
				//重新赋值url 属性
		    	$("#messageEmail-table-emailDropList").datagrid('load',queryJSON);
			});
			//重置
			$("#messageEmail-emailDropList-btn-reloadEmailList").click(function(){
				$("#messageEmail-form-emailDropList")[0].reset();
				//重新赋值url 属性
				$("#messageEmail-table-emailDropList").datagrid('load', {});
			});
			$("#messageEmail-emailDropList-btn-recoverEmail").click(function(){
				//邮件恢复
				var $emailDropList=$("#messageEmail-table-emailDropList");
				var dataRow = $emailDropList.datagrid('getChecked');
		    	if(dataRow==null){
		    		$.messager.alert("提醒","请选择要恢复的邮件，至少一封邮件！");
		    		return false;
		    	}
		    	var emlcidarr=Enumerable.from(dataRow).where("$.emailContent!=null && $.emailContent.id !=null && $.emailContent.delflag=='2' && $.recvuserid=='2'").distinct("$.emailid").select("$.emailid").toArray();
		    	var emlridarr=Enumerable.from(dataRow).where("$.delflag=='2' && $.id !=null && $.recvuserid=='1'").distinct("$.id").select("$.id").toArray();
		    	if(emlcidarr.length==0 && emlridarr.length==0){
		    		$.messager.alert("提醒","请选择要恢复的邮件，至少一封邮件！");
		    		return false;
		    	}
				$.messager.confirm("提醒", "确定要恢复邮件吗？", function(r) {
				if (r) {
			    	$.ajax({   
			            url :'message/email/recoverDropEmail.do',
			            type:'post',
			            dataType:'json',
			            data:{emlcids:emlcidarr.join(','),emlrids:emlridarr.join(',')},
			            beforeSend:function(XHR){
				            loading("操作中...");
			            },
			            success:function(json){
				  		    loaded();
		            		$emailDropList.datagrid('reload');
			            	$.messager.alert("提醒","操作已完成！");
			            }
			        });
		        }});
			});	
			//删除
			$("#messageEmail-emailDropList-btn-destroyEmail").click(function(){
				//邮件恢复
				var $emailDropList=$("#messageEmail-table-emailDropList");
				var dataRow = $emailDropList.datagrid('getChecked');
		    	if(dataRow==null){
		    		$.messager.alert("提醒","请选择要删除的邮件，至少一封邮件！");
		    		return false;
		    	}
		    	var emlcidarr=Enumerable.from(dataRow).where("$.emailContent!=null && $.emailContent.id !=null && $.emailContent.delflag=='2' && $.recvuserid=='2'").distinct("$.emailContent.id").select("$.emailContent.id").toArray()||[];
		    	var emlridarr=Enumerable.from(dataRow).where("$.delflag=='2' && $.id !=null && $.recvuserid=='1'").distinct("$.id").select("$.id").toArray()||[];
		    	if(emlcidarr.length==0 && emlridarr.length==0){
		    		$.messager.alert("提醒","请选择要删除的邮件，至少一封邮件！");
		    		return false;
		    	}
				$.messager.confirm("提醒", "邮件删除后不可恢复，确定要删除邮件吗？", function(r) {
					if (r) {
				    	$.ajax({   
				            url :'message/email/deleteDropEmail.do',
				            type:'post',
				            dataType:'json',
				            data:{emlcids:emlcidarr.join(','),emlrids:emlridarr.join(',')},
				            beforeSend:function(XHR){
					            loading("操作中...");
				            },
				            success:function(json){
					  		    loaded();
			            		$emailDropList.datagrid('reload');
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
			//删除
			$("#messageEmail-emailDropList-btn-deleteAllEmail").click(function(){
				$.messager.confirm("提醒", "邮件删除后不可恢复，您确定要清空废纸篓吗?", function(r) {
				if (r) {
			    	$.ajax({   
			            url :'message/email/deleteAllDropEmail.do',
			            type:'post',
			            dataType:'json',
			            data:'',
			            success:function(json){
		            		$.messager.alert("提醒","操作成功！");
		        			$("#messageEmail-table-emailDropList").datagrid('load', {});
			            }
			        });
		        }});
			});
		});
  	 </script>
  </body>
</html>