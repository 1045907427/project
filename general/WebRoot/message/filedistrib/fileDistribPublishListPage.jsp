<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>传阅件</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="fileDistrib-buttons-addFileDistribListPage" style="height:26px;"></div>
    	</div>
    	<div data-options="region:'center'">
    		<table id="filedistrib-table-fileDistribListPage"></table>
    		<div id="filedistrib-table-query-fileDistribListPage" style="padding:5px;height:auto">
				<div>
					<form action="" id="filedistrib-form-fileDistribListPage" method="post">
						<table>
			    			<tr>
			    				<td>
			    					标题:<input name="title" style="width:120px" />
									发布开始时间:<input name="startaddtime" style="width:80px" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
									发布结束时间:<input name="endaddtime" style="width:80px" readonly="sreadonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			    					<a href="javaScript:void(0);" id="filedistrib-btn-queryFileDistribListPage" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="[Alt+Q]查询">查询</a>
									<a href="javaScript:void(0);" id="filedistrib-btn-reloadFileDistribListPage" class="easyui-linkbutton" iconCls="icon-reload" plain="true" title="[Alt+R]重置">重置</a>
									<span id="filedistrib-table-query-fileDistribListPage-advanced"></span>
			    				</td>
			    			</tr>
			    		</table>
					</form>
				</div>
			</div>
    	</div>
    </div>
    <div id="fileDistrib-dialog-fileDistribPulishListOper" class="easyui-dialog" closed="true"></div>
    <script type="text/javascript">
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
			//按钮
			$("#fileDistrib-buttons-addFileDistribListPage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/message/filedistrib/fileDistribAddBtn.do">
					{
						type:'button-add',
						handler: function(){
							top.addOrUpdateTab('message/filedistrib/fileDistribPage.do','传阅件管理');
						}
					},
					</security:authorize>
					<security:authorize url="/message/filedistrib/fileDistribEditPage.do">
					{
						type:'button-edit',
						handler: function(){
							var datarow = $("#filedistrib-table-fileDistribListPage").datagrid('getSelected');
							if(datarow==null ||  datarow.id ==null){
			  		        	$.messager.alert("提醒","请选择要修改的传阅件");
								return false;
							}
							top.addOrUpdateTab('message/filedistrib/fileDistribPage.do?type=edit&id='+datarow.id,'传阅件管理');
						}
					},
					</security:authorize>
					<security:authorize url="/message/filedistrib/fileDistribPublishDelete.do">
					{
						type:'button-delete',
						handler: function(){
							var dataRows = $("#filedistrib-table-fileDistribListPage").datagrid('getChecked');
					    	if(dataRows.length==0){
					    		$.messager.alert("提醒","注意：请勾选要删除传阅件！");
					    		return false;
					    	}
							$.messager.confirm("删除确认","注意：启用的传阅件不可删除且删除后不可。<br/>确定要删除吗?",function(r){
				   				if(r){
					   				var idarr=new Array();
					   				for(var i=0;i<dataRows.length;i++){
						   				if(dataRows[i].id && dataRows[i].id !=""){
							   				idarr.push(dataRows[i].id);
						   				}
					   				}
							    	if(idarr.length==0){
							    		$.messager.alert("提醒","请选择要删除的未启用的传阅件！");
							    		return false;					    		
							    	}
							    	$.ajax({   
							            url :'message/filedistrib/fileDistribPublishDelete.do',
							            data:{ids:idarr.join(",")},
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	if(json.flag==true){
							            		$("#filedistrib-table-fileDistribListPage").datagrid('reload');
									            if(json.ismuti && json.ismuti==true){
							            			$.messager.alert("提醒", "删除成功！删除成功数："+ json.isuccess +"<br />删除失败数："+ json.ifailure + "<br />不允许删除数："+ json.inohandle);
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
					<security:authorize url="/message/filedistrib/fileDistribViewBtn.do">
					{
						type:'button-view',
						handler: function(){
							var datarow = $("#filedistrib-table-fileDistribListPage").datagrid('getSelected');
							if(datarow==null ||  datarow.id ==null){
			  		        	$.messager.alert("提醒","请选择要查看的传阅件");
								return false;
							}
							top.addOrUpdateTab('message/filedistrib/fileDistribPage.do?type=view&id='+datarow.id,'传阅件管理');
						}
					},
					</security:authorize>
					<security:authorize url="/message/filedistrib/openFileDistrib.do">
					{
						type:'button-open',
						handler: function(){
							var dataRows = $("#filedistrib-table-fileDistribListPage").datagrid('getChecked');
					    	if(dataRows.length==0){
					    		$.messager.alert("提醒","注意：请勾选要启用的传阅件！");
					    		return false;
					    	}
							$.messager.confirm("启用确认","注意：保存或禁用状态下才能启用。<br/>确定要启用所勾选的传阅件吗?",function(r){
				   				if(r){
					   				var idarr=new Array();
					   				for(var i=0;i<dataRows.length;i++){
						   				if(dataRows[i].id && dataRows[i].id !=""){
							   				idarr.push(dataRows[i].id);
						   				}
					   				}
							    	if(idarr.length==0){
							    		$.messager.alert("提醒","请选择要启用的传阅件！");
							    		return false;					    		
							    	}
							    	$.ajax({   
							            url :'message/filedistrib/openFileDistrib.do',
							            data:{ids:idarr.join(",")},
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	if(json.flag==true){
							            		$("#filedistrib-table-fileDistribListPage").datagrid('reload');
							            		$.messager.alert("提醒", "启用成功！<br/>启用成功数："+ json.isuccess +"<br />启用失败数："+ json.ifailure + "<br />不允许启用数："+ json.inohandle);
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
					<security:authorize url="/message/filedistrib/closeFileDistrib.do">
					{
						type:'button-close',
						handler: function(){
							var dataRows = $("#filedistrib-table-fileDistribListPage").datagrid('getChecked');
					    	if(dataRows.length==0){
					    		$.messager.alert("提醒","注意：请勾选要禁用的传阅件！");
					    		return false;
					    	}
							$.messager.confirm("禁用确认","注意：启用状态下才能禁用。<br/>确定要禁用所勾选的传阅件吗?",function(r){
				   				if(r){
					   				var idarr=new Array();
					   				for(var i=0;i<dataRows.length;i++){
						   				if(dataRows[i].id && dataRows[i].id !=""){
							   				idarr.push(dataRows[i].id);
						   				}
					   				}
							    	if(idarr.length==0){
							    		$.messager.alert("提醒","请选择要禁用的传阅件！");
							    		return false;					    		
							    	}
							    	$.ajax({   
							            url :'message/filedistrib/closeFileDistrib.do',
							            data:{ids:idarr.join(",")},
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	if(json.flag==true){
							            		$("#filedistrib-table-fileDistribListPage").datagrid('reload');
							            		$.messager.alert("提醒", "禁用成功！<br/>禁用成功数："+ json.isuccess +"<br />禁用失败数："+ json.ifailure + "<br />不允许禁用数："+ json.inohandle);
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
					{}
				],
				//新增自定义按钮
				buttons:[
				         	{},
							<security:authorize url="/message/filedistrib/fileDistribReceiveReadBtn.do">
							{
								id:'fileDistrib-button-fileDistribReceiveRead',
								name:'查看传阅人',
								iconCls:'icon-search',
								handler:function(){
									var $list=$("#filedistrib-table-fileDistribListPage");
									var datarow=$list.datagrid('getSelected');
									if(datarow==null || datarow.length==0 ){
						    			$.messager.alert("提醒","请选择要查看的传阅记录！");
						    			return false;
				    				}
									var url="message/filedistrib/fileDistribReadListPage.do?fid="+datarow.id;
									$readOper=$("#fileDistrib-dialog-fileDistribPulishListOper");
									$readOper.dialog({
										title:'查看传阅记录',
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
				datagrid:'filedistrib-table-fileDistribListPage',
				tname:'t_msg_filedistrib'
			});

			var fileDistribPublish_ColListJson=$("#filedistrib-table-fileDistribListPage").createGridColumnLoad({
	 			name : 'msg_filedistrib',
	 			frozenCol : [[
	 							{field : 'id',checkbox : true}
	 						]],
	 			commonCol : [[
					{field:'addusername',title:'发布人',width:120,sortable:true,isShow:true},
			        {field:'type',title:'内容格式',width:80,
		        		formatter:function(value,row,index){
		        			if(value=="0"){
			        			return "内容显示";
		        			}else if(value=="1"){
			        			return "文档内容";
		        			}
		        		}
		        	},  
					{field:'title',title:'标题',width:300},  
					{field:'publishrang',title:'发布范围',width:250,isShow:true,
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
					{field:'publishtime',title:'发布时间',width:120,isShow:true,					        	
					  	formatter:function(value,row,index){
					  	if(row.state==1){
						  	return row.addtime;
					  	}
					}},
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
 			$("#filedistrib-table-fileDistribListPage").datagrid({	
	 				fit:true,
	            	striped: true,
		  	 		method:'post',
		  	 		title:'传阅件理列表',
		  	 		rownumbers:true,
		  	 		pagination:true,
					singleSelect : false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
  	 				idField:'id',
					toolbar:'#filedistrib-table-query-fileDistribListPage',
				    url:'message/filedistrib/showFileDistribPublishPageList.do',  
				    authority : fileDistribPublish_ColListJson,
				    frozenColumns : fileDistribPublish_ColListJson.frozen,
				    columns : fileDistribPublish_ColListJson.common,
				    sortName:'addtime',
				    sortOrder:'desc',
				    onDblClickRow:function(rowIndex, rowData){
					    if(rowData!=null){
							top.addOrUpdateTab('message/filedistrib/fileDistribPage.do?type=view&id='+rowData.id,'传阅件管理');
					    }
				    }
				}).datagrid("columnMoving");


			$("#filedistrib-btn-queryFileDistribListPage").click(function(){
				//查询参数直接添加在url中         
       			var queryJSON  = $("#filedistrib-table-fileDistribListPage").serializeJSON();					
 				$('#filedistrib-table-fileDistribListPage').datagrid('load',queryJSON );
			});
			$("#filedistrib-btn-reloadFileDistribListPage").click(function(){			
				$("#filedistrib-table-fileDistribListPage")[0].reset();
				$('#filedistrib-table-fileDistribListPage').datagrid('load', {});
			});
			$("#filedistrib-table-query-fileDistribListPage-advanced").advancedQuery({
		 		name:'msg_filedistrib',
		 		datagrid:'filedistrib-table-fileDistribListPage'
			});
		});
    </script>
  </body>
</html>
