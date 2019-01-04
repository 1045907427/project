<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>草稿箱管理</title>
  </head>
  
  <body>
  		<div class="easyui-layout" data-options="fit:true" border="false">
		<div title="" data-options="region:'north'" border="false" style="height:90px;padding:2px 4px; background-color: height: 28px;background: #EFEFEF;border-bottom: 1px solid #CCC;">
			<div id="messageEmail-emailDraftList-query-showQueryToolbar" style="height:auto">
				<div>
					<form action="" id="messageEmail-form-emailDraftList" method="post">
						<table class="querytable">
							<tr>
								<td>标题内容：</td>
								<td><input name="tlecont" style="width:200px;"></td>
								<td>
									修改时间：
								</td>
								<td>
									<input name="addtimestart" style="width:90px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
									到 <input name="addtimeend" style="width:90px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
								</td>
							</tr>
							<tr>
								<td colspan="2">
								</td>
								<td colspan="2">
									<a href="javaScript:void(0);" id="messageEmail-emailDraftList-btn-queryEmailList" class="button-qr">查询</a>
									<a href="javaScript:void(0);" id="messageEmail-emailDraftList-btn-reloadEmailList" class="button-qr">重置</a>
									<span href="javaScript:void(0);" id="messageEmail-queay-emailDraftList-advanced"></span>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div>
					<div style="float:left;">
	                    <a href="javaScript:void(0);" id="messageEmail-emailDraftList-btn-editEmail" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'">编辑</a>
	                    <a href="javaScript:void(0);" id="messageEmail-emailDraftList-btn-deleteEmail" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
	                    <span href="javaScript:void(0);" id="messageEmail-queay-emailDraftList-advanced"></span>
	                    <%--<a href="javaScript:void(0);" id="messageEmail-emailDraftList-btn-sendEmail" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-extend-sendMail'">发送</a>--%>
	                    <%--<a href="javaScript:void(0);" id="messageEmail-emailDraftList-btn-exportEmailxls" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-extend-xls'">导出Excel</a>--%>
					</div>
					<div style="clear:both"></div>
				</div>
			</div>
		</div>			
		<div title="" data-options="region:'center'" border="false">
			<table id="messageEmail-table-emailDraftList"></table>
		</div>
	</div>
	<div style="display:none">		
		<div id="messageEmail-dialog-emailList-DetailOper" ></div>
	</div>	
	
	<script type="text/javascript">
		var emailPage_showMailDetail=function(id){
			if(id==null || $.trim(id)==""){
				return;
			}
			id=$.trim(id);
			var atitle='草稿箱-邮件详情';
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
                        $mailDetailOper.dialog("destroy");
    				}
				    
				});
				$mailDetailOper.dialog("open");
		}
		$(document).ready(function(){
			$("#messageEmail-queay-emailDraftList-advanced").advancedQuery({
		 		name:'msg_emailcontent',
		 		datagrid:'messageEmail-table-emailDraftList'
			});
			$('#messageEmail-table-emailDraftList').datagrid({ 
	  	 		fit:true,
	            striped: true,
	  	 		method:'post',
	  	 		title:'',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:false,
                pageSize:100,
			    url:'message/email/showEmailSendPageList.do?sendflag=0',  
			    checkOnSelect : true,
			    selectOnCheck : true,
			    sortName:'addtime',
			    sortOrder:'desc',
			    columns:[[
					{field : 'idck',checkbox : true}, 
			        {field:'title',title:'标题',width:250},  
			        {field:'addtime',title:'修改时间',width:120},
			        {field:'attach',title:'附件',width:70,
			        	formatter: function(value,row,index){
		        			if(value!=null && value!=""){
			        			return "有";
		        			}
			        	}
				    }
			    ]],
				onDblClickRow:function(index, data){
					try{
						emailPage_showMailDetail(data.id);
					}catch(e){
						
					}
			    }
			}).datagrid("columnMoving"); 
			
			//查询
			$("#messageEmail-emailDraftList-btn-queryEmailList").click(function(){
				//查询参数直接添加在url中         
	       		var queryJSON = $("#messageEmail-form-emailDraftList").serializeJSON();
				//重新赋值url 属性
	        	$("#messageEmail-table-emailDraftList").datagrid('load',queryJSON);
			});
			//重置
			$("#messageEmail-emailDraftList-btn-reloadEmailList").click(function(){
				$("#messageEmail-form-emailDraftList")[0].reset();
    			//重新赋值url 属性
				$("#messageEmail-table-emailDraftList").datagrid('load', {});
			});
			$("#messageEmail-emailDraftList-btn-editEmail").click(function(){
				//编辑
				var dataRow = $("#messageEmail-table-emailDraftList").datagrid('getSelected');
		    	if(dataRow==null || dataRow.id==""){
		    		$.messager.alert("提醒","请选择邮箱信息！");
		    		return false;
		    	}
		    	emailPage_openPanel('message/email/emailEditPage.do?oper=edit&id='+dataRow.id);
			});
			//删除
			$("#messageEmail-emailDraftList-btn-deleteEmail").click(function(){
				var $emailDraftList=$("#messageEmail-table-emailDraftList");
				var dataRows = $emailDraftList.datagrid('getChecked');
		    	if(dataRows==null){
		    		$.messager.alert("提醒","请选择邮箱信息！");
		    		return false;
		    	}
		    	var idarr=Enumerable.from(dataRows).select("$.id").toArray();
		    	if(idarr.length==0){
		    		$.messager.alert("提醒","请选择邮件信息！");
		    		return false;
		    	}
				$.messager.confirm("提醒", "是否要选中的邮件删除?", function(r) {
				if (r) {
			    	$.ajax({   
			            url :'message/email/deleteEmail.do',
			            type:'post',
			            dataType:'json',
			            data:{dstate:0,ids:idarr.join(',')},
			            success:function(json){
		            		$emailDraftList.datagrid('reload');
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
		        }});
			});
		});
  	 </script>
  </body>
</html>

