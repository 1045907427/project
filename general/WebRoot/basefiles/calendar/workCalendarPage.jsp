<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>工作日历</title>
	<%@include file="/include.jsp" %>     
  </head>
  
  <body>
  	<div class="easyui-panel" data-options="fit:true">
      	<div class="easyui-layout" data-options="fit:true">  
      		<div  data-options="region:'north'" style="height:30px;overflow: hidden">
      			<div class="buttonBG">
      				<security:authorize url="/basefiles/showWorkCanlendarAddPage.do">
	            	<a href="javaScript:void(0);" id="workCanlendar-buton-add" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">新增</a>
	            	</security:authorize>
	            	<security:authorize url="/basefiles/showWorkCanlendarEditPage.do">
	            	<a href="javaScript:void(0);" id="workCanlendar-buton-edit" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'">修改</a>
	           	 	</security:authorize>
	           	 	<!--<security:authorize url="/basefiles/showWorkCalendarHoldAdd.do">
	           	 	<a href="javaScript:void(0);" id="workCanlendar-buton-hold" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save',disabled:true">暂存</a>
	           	 	</security:authorize>-->
	           	 	<security:authorize url="/basefiles/showWorkCalendarSave.do">
	           	 	<a href="javaScript:void(0);" id="workCanlendar-buton-save" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save',disabled:true">保存</a>
	             	</security:authorize>
	             	<security:authorize url="/basefiles/deleteWorkCalendar.do">
	             	<a href="javaScript:void(0);" id="workCanlendar-buton-delete" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
	             	</security:authorize>
	             	<security:authorize url="/basefiles/showWorkCanlendarCopyPage.do">
	             	<a href="javaScript:void(0);" id="workCanlendar-buton-copy" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-copy'">复制</a>
	            	</security:authorize>
	            	<security:authorize url="/basefiles/openWorkCalendar.do">
	            	<a href="javaScript:void(0);" id="workCanlendar-buton-enable" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-open'">启用</a>
	             	</security:authorize>
	             	<security:authorize url="/basefiles/closeWorkCalendar.do">
	             	<a href="javaScript:void(0);" id="workCanlendar-buton-disable" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-close'">禁用</a>
					</security:authorize>
					<input type="hidden" id="workCalendar-opera"/>
      			</div>
			</div>
            <div data-options="region:'west',split:true" style="width:300px;">
		     	<table id="workCanlendar-table-list"></table>
		    </div>
		    <div data-options="region:'center',split:true,border:true">
		    	<div id="workCanlendar-div-canlendarInfo"></div>
			</div>
		  </div>
     </div>
     <div id="workCanlendar-dialog-quickset"></div>
     <script type="text/javascript">
	     	$(function(){
	     		$("#workCanlendar-table-list").datagrid({ 
				columns:[[
					{field:'id',title:'编码',width:100},
					{field:'name',title:'名称',width:100},  
					{field:'state',title:'状态',width:60,
						formatter:function(val){
			        		if(val=='0'){
			        			return '禁用';
			        		}else if(val=='1'){
			        			return '启用';
			        		}else if(val=='2'){
			        			return '保存';
			        		}else if(val=='3'){
			        			return '暂存';
			        		}else if(val=='4'){
			        			return '新增';
			        		}
			        	}
					}
				]],
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'工作日历列表',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:true,
			    url:'basefiles/getWorkCalendarList.do',
			    onLoadSuccess:function(){
			    	var p = $('#workCanlendar-table-list').datagrid('getPager');  
				    $(p).pagination({  
				        pageSize: 20,//每页显示的记录条数，默认为10  
				        beforePageText: '',//页数文本框前显示的汉字  
				        afterPageText: '',  
				        showPageList:false,
				        displayMsg: ''
				    });
		    	},
		    	onClickRow:function(rowIndex, rowData){
		    		var url = "basefiles/showWorkCanlendarInfo.do?id="+rowData.id;
	     			$("#workCanlendar-div-canlendarInfo").panel({  
					    content:'<iframe id="workCanlendar-iframe" frameborder="0" width="99%" height="99%" scrolling="auto"></iframe>',
					    cache:false,
					    closed:true,
					    title:'工作日历详情',
					    maximized:true
					});
					$("#workCanlendar-div-canlendarInfo").panel("open");
					$("#workCanlendar-iframe").attr("src",url);
					$("#workCalendar-opera").attr("value","view");
					
					$("#workCanlendar-buton-save").linkbutton("disable");
			   		$("#workCanlendar-buton-hold").linkbutton("disable");
		    	}
			});
     		//新增
     		$("#workCanlendar-buton-add").click(function(){
     			var url = "basefiles/showWorkCanlendarAddPage.do";
     			$("#workCanlendar-div-canlendarInfo").panel({  
				    content:'<iframe id="workCanlendar-iframe" frameborder="0" width="99%" height="99%" scrolling="auto"></iframe>',
				    cache:false,
				    closed:true,
				    title:'工作日历新增',
				    maximized:true
				});
				$("#workCanlendar-div-canlendarInfo").panel("open");
				$("#workCanlendar-iframe").attr("src",url);
				$("#workCalendar-opera").attr("value","add");
				
				$("#workCanlendar-buton-save").linkbutton("enable");
		   		$("#workCanlendar-buton-hold").linkbutton("enable");
     		});
     		//暂存
     		$("#workCanlendar-buton-hold").click(function(){
     			//判断按钮是否隐藏
 				if($(this).linkbutton('options').disabled){
					return false;
				}
     			var type = $("#workCanlendar-div-canlendarInfo").attr("class");
     			if(!type){
     				$.messager.alert("提醒","不是新增或者修改时，暂存无效。");
     				return false;
     			}else{
	     			var title = $("#workCanlendar-div-canlendarInfo").panel("options").title;
	     			if(title!='工作日历新增'&&title!='工作日历修改'){
	     				$.messager.alert("提醒","不是新增或者修改时，暂存无效。");
	     				return false;
	     			}
     			}
     			$.messager.confirm("提醒", "是否暂存工作日历?", function(r){
					if (r){
     					$("#workCanlendar-iframe")[0].contentWindow.workCalendarHoldSubmit();
     					$("#workCalendar-opera").attr("value","holdsave");
     					
     					$("#workCanlendar-div-canlendarInfo").panel("close");
     					$("#workCanlendar-buton-save").linkbutton("disable");
		   				$("#workCanlendar-buton-hold").linkbutton("disable");
     				}
     			});
     		});
     		//保存
     		$("#workCanlendar-buton-save").click(function(){
     			//判断按钮是否隐藏
 				if($(this).linkbutton('options').disabled){
					return false;
				}
     			var type = $("#workCanlendar-div-canlendarInfo").attr("class");
     			if(!type){
     				$.messager.alert("提醒","不是新增或者修改时，暂存无效。");
     				return false;
     			}else{
	     			var title = $("#workCanlendar-div-canlendarInfo").panel("options").title;
	     			if(title!='工作日历新增'&&title!='工作日历修改'){
	     				$.messager.alert("提醒","不是新增或者修改时，暂存无效。");
	     				return false;
	     			}
     			}
     			var title = $("#workCanlendar-div-canlendarInfo").panel("options").title;
     			$.messager.confirm("提醒", "是否保存工作日历?", function(r){
					if (r){
     					$("#workCanlendar-iframe")[0].contentWindow.workCalendarSubmit();
     					$("#workCalendar-opera").attr("value","save");
     					
     					$("#workCanlendar-div-canlendarInfo").panel("close");
     					$("#workCanlendar-buton-save").linkbutton("disable");
		   				$("#workCanlendar-buton-hold").linkbutton("disable");
     				}
     			});
     		});
     		//删除
     		$("#workCanlendar-buton-delete").click(function(){
     			var workCalendar = $("#workCanlendar-table-list").datagrid('getSelected');
		    	if(workCalendar==null){
		    		$.messager.alert("提醒","请选择工作日历！");
		    		return false;
		    	}else{
		    		if(workCalendar.state=='0'){
		    			$.messager.alert("提醒","禁用状态不能删除！");
		    			return false;
		    		}
		    		$.messager.confirm("提醒", "是否删除工作日历?", function(r){
						if (r){
							loading("删除中..");
					    	$.ajax({   
					            url :'basefiles/deleteWorkCalendar.do',
					            type:'post',
					            data:'id='+workCalendar.id,
					            dataType:'json',
					            success:function(json){
					            	loaded();
					            	if(json.flag==true){
					            		$.messager.alert("提醒","删除成功！");
					            		$('#workCanlendar-table-list').datagrid('reload');
					            		$("#workCanlendar-div-canlendarInfo").panel({  
										    content:'<iframe id="workCanlendar-iframe" frameborder="0" width="99%" height="99%" scrolling="auto"></iframe>',
										    cache:false,
										    closed:true,
										    title:'工作日历详情',
										    maximized:true
										});
										$("#workCanlendar-div-canlendarInfo").panel("open");
					            	}else{
					            		$.messager.alert("提醒","删除失败！");
					            	}
					            }
					        });
				        }
					});
				}
     		});
     		//修改
     		$("#workCanlendar-buton-edit").click(function(){
     			var workCalendar = $("#workCanlendar-table-list").datagrid('getSelected');
		    	if(workCalendar==null){
		    		$.messager.alert("提醒","请选择工作日历！");
		    		return false;
		    	}else{
		    		if(workCalendar.state=='0'){
		    			$.messager.alert("提醒","该工作日历处于禁用状态不能修改！");
		    			return false;
		    		}
		    		var url = "basefiles/showWorkCanlendarEditPage.do?id="+workCalendar.id;
	     			$("#workCanlendar-div-canlendarInfo").panel({  
					    content:'<iframe id="workCanlendar-iframe" frameborder="0" width="99%" height="99%" scrolling="auto"></iframe>',
					    cache:false,
					    closed:true,
					    title:'工作日历修改',
					    maximized:true
					});
					$("#workCanlendar-div-canlendarInfo").panel("open");
					$("#workCanlendar-iframe").attr("src",url);
					if(workCalendar.state=='1'){
						$("#workCalendar-opera").attr("value","openedit");
					}else{
						$("#workCalendar-opera").attr("value","edit");
					}
			    }
			    $("#workCanlendar-buton-save").linkbutton("enable");
			    if(workCalendar.state=='3'){
			   		$("#workCanlendar-buton-hold").linkbutton("enable");
			    }
     		});
     		//启用
     		$("#workCanlendar-buton-enable").click(function(){
     			var workCalendar = $("#workCanlendar-table-list").datagrid('getSelected');
		    	if(workCalendar==null){
		    		$.messager.alert("提醒","请选择工作日历！");
		    		return false;
		    	}else{
		    		if($("#workCalendar-opera").val()=='openedit'){
		    			$("#workCanlendar-iframe")[0].contentWindow.workCalendarOpenSubmit();
		    			return false;
		    		}else if($("#workCalendar-opera").val()=='edit'){
		    			$.messager.alert("提醒","修改时不能进行启用操作！");
		    			return false;
		    		}
		    		if(workCalendar.state!='2'&&workCalendar.state!='0'){
     					$.messager.alert("提醒","只有保存与禁用状态才能进行启用操作");
     					return false;
     				}
		    		$.messager.confirm("提醒", "是否启用工作日历?", function(r){
						if (r){
							loading("启用中..");
					    	$.ajax({   
					            url :'basefiles/openWorkCalendar.do?id='+workCalendar.id,
					            type:'post',
					            dataType:'json',
					            success:function(json){
					            	loaded();
					            	if(json.flag==true){
					            		$.messager.alert("提醒","启用成功！");
					            		$('#workCanlendar-table-list').datagrid('reload');
					            	}else{
					            		$.messager.alert("提醒","启用失败！");
					            	}
					            }
					        });
				        }
					});
				}
     		});
     		//禁用
     		$("#workCanlendar-buton-disable").click(function(){
     			var workCalendar = $("#workCanlendar-table-list").datagrid('getSelected');
		    	if(workCalendar==null){
		    		$.messager.alert("提醒","请选择工作日历！");
		    		return false;
		    	}else{
		    		if(workCalendar.state!='1'){
     					$.messager.alert("提醒","只有启用状态才能进行禁用操作");
     					return false;
     				}else if($("#workCalendar-opera").val()=='edit'){
		    			$.messager.alert("提醒","修改时不能进行禁用操作！");
		    			return false;
		    		}
		    		$.messager.confirm("提醒", "是否禁用工作日历?", function(r){
						if (r){
							loading("禁用中..");
					    	$.ajax({   
					            url :'basefiles/closeWorkCalendar.do?id='+workCalendar.id,
					            type:'post',
					            dataType:'json',
					            success:function(json){
					            	loaded();
					            	if(json.flag==true){
					            		$.messager.alert("提醒","禁用成功！");
					            		$('#workCanlendar-table-list').datagrid('reload');
					            	}else{
					            		$.messager.alert("提醒","禁用失败！");
					            	}
					            }
					        });
				        }
					});
				}
     		});
     		//复制
     		$("#workCanlendar-buton-copy").click(function(){
     			var workCalendar = $("#workCanlendar-table-list").datagrid('getSelected');
		    	if(workCalendar==null){
		    		$.messager.alert("提醒","请选择工作日历！");
		    		return false;
		    	}else{
		    		var url = "basefiles/showWorkCanlendarCopyPage.do?id="+workCalendar.id;
	     			$("#workCanlendar-div-canlendarInfo").panel({  
					    content:'<iframe id="workCanlendar-iframe" frameborder="0" width="99%" height="99%" scrolling="auto"></iframe>',
					    cache:false,
					    closed:true,
					    title:'工作日历新增',
					    maximized:true
					});
					$("#workCanlendar-div-canlendarInfo").panel("open");
					$("#workCanlendar-iframe").attr("src",url);
					
					$("#workCanlendar-buton-save").linkbutton("enable");
		   			$("#workCanlendar-buton-hold").linkbutton("enable");
		    	}
     		});
     	});
     	function workCanlendarQuickSet(year){
     		$('#workCanlendar-dialog-quickset').dialog({  
			    title: '工作日历快速设置',  
			    width: 400,  
			    height: 350,  
			    closed: true,  
			    cache: false,  
			    href: 'basefiles/showWorkCanlendarQuickSetPage.do?year='+year,  
			    modal: true
			});
			$('#workCanlendar-dialog-quickset').dialog("open");
     	}
     </script>
  </body>
</html>
