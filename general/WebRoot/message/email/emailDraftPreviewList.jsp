<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>草稿箱管理</title>
  </head>
  
  <body>
  		<div class="easyui-layout" data-options="fit:true" border="false">
		<div title="" data-options="region:'north'" border="false" style="height:70px;padding:2px 4px; background-color: height: 28px;background: #EFEFEF;border-bottom: 1px solid #CCC;">
			<div id="messageEmail-emailDraftList-query-showQueryToolbar" style="height:auto">
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
				<div>
					<form action="" id="messageEmail-form-emailDraftList" method="post">
						标题内容:<input name="tlecont" style="width:200px;">
						<a href="javaScript:void(0);" id="messageEmail-emailDraftList-btn-queryEmailList" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="messageEmail-emailDraftList-btn-reloadEmailList" class="button-qr">重置</a>
						<span href="javaScript:void(0);" id="messageEmail-queay-emailDraftList-advanced"></span>
					</form>
				</div>
			</div>
		</div>			
		<div title="" data-options="region:'center'" border="false">
			<div  id="messageEmail-emailDraftList-div-wrap"  class="easyui-panel" data-options="fit:true" border="false">
				<div id="messageEmail-emailDraftList-layout-content" class="easyui-layout" data-options="fit:true" border="false">
					<div title="" data-options="region:'center'" style="height:auto;" >						 	 
	    				<table id="messageEmail-table-emailDraftList"></table>
					</div>
					<div title="邮件详情" data-options="region:'east',split:true" style="height:auto;width:400px;" border="false">
						<div id="messageEmail-emailDraftList-panel" border="false"></div>
					</div>
				</div>
			</div>
			<div id="messageEmail-emailDraftList-div-nodata"  class="easyui-panel" data-options="fit:true" border="false">					
				<div style="width:370px;height:98px;margin:100px auto 0; border:1px solid #9F9F9F;line-height:80px;">
					<div style="float:left; padding:10px;" class="img-extend-infow64">						
					</div>
					<div style="float:left;font-size: 16px;color:#6BAD42;">草稿箱未找到邮件，请尝试重新查询</div>
				</div>
			</div>
		</div>
	</div>
	<div id="messageEmail-dialog-emailBoxOper" class="easyui-dialog" closed="true"></div>
	<div style="clear:both"></div>	
	
	<script type="text/javascript">

	var emailSendPreviewList_showEmailDetail=function(id){
		if(id==null || $.trim(id)=="" || isNaN(id)){
			return false;
		}
		$("#messageEmail-emailDraftList-panel").panel({
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
			    sortName:'addtime',
			    sortOrder:'desc',
			    onClickRow:function(rowIndex, rowData){
				    try{
				    	emailSendPreviewList_showEmailDetail(rowData.id);
				    }catch(e){
				    }
			    },
			    onLoadSuccess:function(data){
				    if(data.rows.length>0){
				    	$("#messageEmail-emailDraftList-div-wrap").show();
				    	$("#messageEmail-emailDraftList-div-nodata").hide();
				    	$("#messageEmail-emailDraftList-layout-content").layout("resize");				    	
				    	try{
					    	emailSendPreviewList_showEmailDetail(data.rows[0].id);
					    }catch(e){
					    }
			    	}else{
				    	$("#messageEmail-emailDraftList-div-wrap").hide();
				    	$("#messageEmail-emailDraftList-div-nodata").show();
				    	
			    	}
			    }
			}).datagrid("columnMoving"); 
			
			//查询
			$("#messageEmail-emailDraftList-btn-queryEmailDraftList").click(function(){
				//查询参数直接添加在url中         
	       		var queryJSON = $("#messageEmail-form-emailDraftList").serializeJSON();
				//重新赋值url 属性
	        	$("#messageEmail-table-emailDraftList").datagrid('load',queryJSON);
			});
			//重置
			$("#messageEmail-emailDraftList-btn-reloadEmailDraftList").click(function(){
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

