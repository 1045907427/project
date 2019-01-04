<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>传阅件列表</title>    
		<%@include file="/include.jsp" %> 	
	  	<script type="text/javascript" src="js/kindeditor/kindeditor-min.js"></script> 
	  	<script type="text/javascript" src="js/kindeditor/lang/zh_CN.js"></script>
    	<script type="text/javascript" src="js/jquery.upload.js"></script>
		<link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>
	</head>

	<body>
		<table id="fileDistrib-table-showFileDistribList"></table>
	     <div id="fileDistrib-query-showFileDistribList" style="padding:5px;height:auto">
			<div>
				<form action="" id="fileDistrib-form-fileDistribList" method="post">
					标题:<input name="title" style="width:120px">
					发布开始时间:<input name="startaddtime" style="width:80px" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					发布结束时间:<input name="endaddtime" style="width:80px" readonly="sreadonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					<a href="javaScript:void(0);" id="fileDistrib-btn-queryFileDistribList" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
					<a href="javaScript:void(0);" id="fileDistrib-btn-reloadFileDistribList" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">重置</a>
					<span id="fileDistrib-queay-queryFileDistribList-advanced"></span>
				</form>
			</div>
			<div>
    		<security:authorize url="/message/filedistrib/setFileDistribReadFlag.do">
				<a href="javaScript:void(0);" id="fileDistrib-fileDistribList-btn-setFileDistribReadFlag" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-extend-info-open'">标记已读</a>
			</security:authorize>			
    		<security:authorize url="/message/filedistrib/setFileDistribReceiveEditPage.do">
				<a href="javaScript:void(0);" id="fileDistrib-fileDistribList-btn-setFileDistribReceiveEditPage" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit',disabled:true">编辑内容</a>
			</security:authorize>
            <security:authorize url="/message/filedistrib/fileDistribReceiveDetailPage.do">
				<a href="javaScript:void(0);" id="fileDistrib-fileDistribList-btn-showpage-fileDistribDetail" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-extend-readoc'">查看传阅件</a>
            </security:authorize>
			</div>
		</div>
		<div id="fileDistrib-dialog-fileDistribListOper" class="easyui-dialog" closed="true"></div>
		<script type="text/javascript">
			var showFileDistrib=function(id){
				if(id==null || $.trim(id)=="" || isNaN(id)){
					return false;
				}

	            <security:authorize url="/message/filedistrib/fileDistribReceiveDetailPage.do">
					top.addTab("message/filedistrib/fileDistribReceiveDetailPage.do?id="+id,'传阅件查看');
				</security:authorize>
			}
			var showPublishRange=function(eid,type){
				if(eid==null || eid==""){
					return false;
				}
				type=type||0;
				switch(type){
					case 1:type="人员查看范围";break;
					case 2:type="部门查看范围";break;
					case 3:type="角色查看范围";break;
					default:type="范围";break;
				}
				var content=$(eid).attr("title")||"";
				if(content!=""){
					$.messager.alert(type,content);
				}
			}
			
	 		$(document).ready(function(){ 			
	 			$("#fileDistrib-table-showFileDistribList").datagrid({	
	 				fit:true,
	            	striped: true,
		  	 		method:'post',
		  	 		title:'传阅件列表',
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
					toolbar:'#fileDistrib-query-showFileDistribList',
				    url:'message/filedistrib/showFileDistribReceivePageList.do',  
				    columns:[[  
						{field : 'idck',checkbox : true}, 
				        {field:'addusername',title:'发布人',width:120,sortable:true},
				        {field:'type',title:'内容格式',width:80,
				        		formatter:function(value,row,index){
				        			if(value=="0"){
					        			return "内容显示";
				        			}else if(value=="1"){
					        			return "文档内容";
				        			}
				        		}
				        },  
				        {field:'title',title:'标题',width:300,
				        	formatter: function(value,row,index){
				        		if(row.istop!=0){
					        		return "<span style='color:#f00;font-weight:bold;'>"+value+"</span>";
				        		}else{
					        		return value;
				        		}
				        }},  
					    {field:'publishrang',title:'发布范围',width:250,
				        		formatter: function(value,row,index){
				        	var msgarr=new Array();
		        			if(row.receivedeptname !=null && row.receivedeptname!=""){
		        				msgarr.push("<span style='color:#00f;text-decoration: underline;cursor:pointer'");
		        				msgarr.push(" onclick='javascript:showPublishRange(this,2);' ");
								msgarr.push(" title='");
		        				msgarr.push(row.receivedeptname);
		        				msgarr.push("'>");
		        				msgarr.push("部门</span>：");
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
		        				msgarr.push("<br/>");
		        			}
		        			if( row.receiverolename !=null && row.receiverolename !=""){
		        				msgarr.push("<span style='color:#00f;text-decoration: underline;cursor:pointer'");
		        				msgarr.push(" onclick='javascript:showPublishRange(this,3);' ");
								msgarr.push(" title='");
		        				msgarr.push(row.receiverolename);
		        				msgarr.push("'>");
		        				msgarr.push("角色</span>：");
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
		        				msgarr.push("<br/>");
		        			}
		        			if( row.receiveusername !=null && row.receiveusername !=""){
		        				msgarr.push("<span style='color:#00f;text-decoration: underline;cursor:pointer'");
		        				msgarr.push(" onclick='javascript:showPublishRange(this,1);' ");
								msgarr.push(" title='");
		        				msgarr.push(row.receiveusername);
		        				msgarr.push("'>");
		        				msgarr.push("人员</span>：");
		        				msgarr.push("<span title='");
		        				msgarr.push(row.receiveusername);
		        				msgarr.push("'>");
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
		        				msgarr.push("<br/>");
		        			}
		        			return msgarr.join("");
				        }},
				        {field:'addtime',title:'发布日期',width:120},
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
			    		showFileDistrib(rowData.id);
			    	}
	 			});
	 			$("#fileDistrib-queay-queryFileDistribList-advanced").advancedQuery({
			 		name:'msg_fileDistrib',
			 		datagrid:'fileDistrib-table-showFileDistribList'
				});
	 			$("#fileDistrib-fileDistribList-btn-setFileDistribReadFlag").click(function(){
	 				$.messager.confirm("提醒","是否要标志所选的传阅件为已读?",function(r){
		   				if(r){    					
		   					var $showFileDistribList=$("#fileDistrib-table-showFileDistribList");
							var datarows = $showFileDistribList.datagrid('getChecked');
					    	if(datarows==null || datarows.length==0){
					    		$.messager.alert("提醒","请选择要标志的传阅件！");
					    		return false;
					    	}
					    	var readrow=new Array();
					    	for(var i=0;i<datarows[i].length;i++){
						    	if(datarows[i] && datarows[i].id && datarows[i].readcount && datarows[i].readcount==0){
							    	readrow.push(datarows[i].id);
						    	}
					    	}
					    	var readcount=readrow.length;
					    	if(readcount==0){
					    		$.messager.alert("提醒","已经标志成功！");
					    		return false;					    		
					    	}
					    	var idarr=readrow;
					    	$.ajax({   
					            url :'message/fileDistrib/addFileDistribread.do',
					            data:{ids:idarr.join(",")},
					            type:'post',
					            dataType:'json',
					            success:function(json){
					            	if(json.flag==true){
					            		$.messager.alert("提醒","标志成功！");
					            		$showFileDistribList.datagrid('reload');
					            	}else{
					            		$.messager.alert("提醒",(json.msg || "标志失败！"));
					            	}
					            }
					        });
		   				}
		   			});
	 			});
	 			
				$("#fileDistrib-btn-queryFileDistribList").click(function(){
					//查询参数直接添加在url中         
	       			var queryJSON = $("#fileDistrib-form-fileDistribList").serializeJSON();					
	 				$('#fileDistrib-table-showFileDistribList').datagrid('load',queryJSON);
				});
				$("#fileDistrib-btn-reloadFileDistribList").click(function(){	
					$("#fileDistrib-form-fileDistribList")[0].reset();
					$('#fileDistrib-table-showFileDistribList').datagrid('load', {});
				});
				$("#fileDistrib-fileDistribList-btn-showpage-fileDistribDetail").click(function(){
					/*
					var $showFileDistribList=$("#fileDistrib-table-showFileDistribList");
					var datarows=$showFileDistribList.datagrid('getSelections');
					if(datarows.length==0 ){
		    			$.messager.alert("提醒","请选择要查看的传阅件！");
		    			return false;
    				}
    				if(datarows.length>1){
		    			$.messager.alert("提醒","抱歉，一次只能查看一条传阅件！");
		    			return false;
    				}
    				top.addTab("message/filedistrib/fileDistribReceiveDetailPage.do?id="+datarows[0].id,'传阅件查看');	

    				*/		

					if($(this).linkbutton("options").disabled){
						return false;
					}
					var $showFileDistribList=$("#fileDistrib-table-showFileDistribList");
					var datarow=$showFileDistribList.datagrid('getSelected');
					if(datarow ==null || datarow.id ==null && datarow.id=="" ){
		    			$.messager.alert("提醒","请选择要查看的传阅件！");
		    			return false;
    				}
					top.addTab("message/filedistrib/fileDistribReceiveDetailPage.do?id="+datarow.id,'传阅件查看');
				});

				$("#fileDistrib-fileDistribList-btn-setFileDistribReceiveEditPage").click(function(){
					if($(this).linkbutton("options").disabled){
						return false;
					}
					var $showFileDistribList=$("#fileDistrib-table-showFileDistribList");
					var datarows=$showFileDistribList.datagrid('getSelections');
					if(datarows.length==0 ){
		    			$.messager.alert("提醒","请选择要查看的传阅件！");
		    			return false;
    				}
    				if(datarows.length>1){
		    			$.messager.alert("提醒","抱歉，一次只能查看一条传阅件！");
		    			return false;
    				}
    				top.addTab("message/filedistrib/fileDistribReceiveEditPage.do?id="+datarows[0].id,'传阅件内容编辑');			
				});
	 		});	 		
	 		
	 	</script>
	</body>
</html>