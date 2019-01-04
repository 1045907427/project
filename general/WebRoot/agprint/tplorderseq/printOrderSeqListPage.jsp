<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>打印内容排序策略列表</title>
    <%@include file="/include.jsp" %> 
  </head>
  
  <body>
  	<table id="tplorderseq-table-printOrderSeqList"></table>
   	<div id="tplorderseq-query-printOrderSeqList" style="padding:0px;height:auto">
 	<div class="buttonBG" id="agprint-button-printOrderSeq"></div>
		<form action="" id="tplorderseq-form-printOrderSeqList" method="post" style="padding-top: 2px;">
			<table>
				<tr>
					<td>模板代码:</td>
					<td><input type="text" id="tplorderseq-printOrderSeqList-query-code" name="code" style="width:120px" /></td>
			   		<td>策略编号:</td>
					<td><input type="text" name="viewid" style="width:100px" /></td>
		   			<td></td>					
			   </tr>
			   <tr>
			   		<td>策略名称:</td>
					<td><input type="text" name="name" style="width:180px" /></td>
			   		<td>状态:</td>
					<td>
						<select name="state" style="width:100px">
			   				<option></option>
			   				<option value="1">启用</option>
			   				<option value="0">禁用</option>
			   			</select>
		   			</td>
		   			<td>
		   				<a href="javaScript:void(0);" id="tplorderseq-query-queryPrintOrderSeqList" class="button-qr">查询</a>
    					<a href="javaScript:void(0);" id="tplorderseq-query-reloadPrintOrderSeqList" class="button-qr">重置</a>
		   			</td>
				</tr>
			</table>
  		</form>
   	</div>
   	<div style="display:none">
   		<div id="tplorderseq-dialog-printOrderSeqOper" ></div>
   		<div id="tplorderseq-dialog-printOrderSeqOper-orderCreate"></div>
   	</div>
   	<script type="text/javascript">
   		$(function(){
    		$("#tplorderseq-printOrderSeqList-query-code").widget({
	   			referwid:'RL_PRINT_TEMPLET_SUBJECT',
	   			singleSelect:true,
	   			width:'180'
	   		});
   				
   				
			$("#agprint-button-printOrderSeq").buttonWidget({
	 			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{}
	 			],
				buttons:[
					<security:authorize url="/agprint/tplorderseq/showPrintOrderSeqAddPageBtn.do">
					{
						id:'button-id-add',
						name:'新增 ',
						iconCls:'button-add',
						handler:function(){
							printOrderSeqOpenDialog('打印顺序策略【新增】', 'agprint/tplorderseq/showPrintOrderSeqAddPage.do');							
						}
					},
					</security:authorize>
					<security:authorize url="/agprint/tplorderseq/showPrintOrderSeqEditPageBtn.do">
					{
						id:'button-id-edit',
						name:'修改 ',
						iconCls:'button-edit',
						handler:function(){
							var dataRow=$("#tplorderseq-table-printOrderSeqList").datagrid('getSelected');
							if(dataRow==null){
								$.messager.alert("提醒","请选择相应的顺序策略信息!");
								return false;
							}
							printOrderSeqOpenDialog("打印顺序策略【更新】", 'agprint/tplorderseq/showPrintOrderSeqEditPage.do?id='+dataRow.id);
						}
					},
					</security:authorize>
					<security:authorize url="/agprint/tplorderseq/deletePrintOrderSeqBtn.do">
					{
						id:'button-id-delete',
						name:'删除',
						iconCls:'button-delete',
						handler:function(){
							var dataRow=$("#tplorderseq-table-printOrderSeqList").datagrid('getSelected');
							if(dataRow==null || dataRow.id==null || dataRow.id==""){
								$.messager.alert("提醒","请选择相应的顺序策略信息!");
								return false;
							}
							if(dataRow.state=='1'){
								$.messager.alert("提醒","抱歉，启用的打印内容顺序策略不能删除!");
								return false;
							}
							$.messager.confirm("提醒","是否删除该顺序策略信息？",function(r){
								if(r){
									loading("删除中..");
									$.ajax({   
							            url :'agprint/tplorderseq/deletePrintOrderSeq.do?id='+ dataRow.id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
								            if(json.flag){
							            		$.messager.alert("提醒","删除成功");	 
			    	        					$("#tplorderseq-query-queryPrintOrderSeqList").trigger("click");     		
								            }else{
			    	        	            	if(json.msg){
			    	        						$.messager.alert("提醒","删除失败!"+json.msg);
			    	        	            	}else{
			    	        						$.messager.alert("提醒","删除失败!");
			    	        	            	}
								            }
										}
							        });
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/agprint/tplorderseq/enablePrintOrderSeqBtn.do">
					{
						id:'button-id-enable',
						name:'启用 ',
						iconCls:'button-open',
						handler:function(){	
							var dataRow=$("#tplorderseq-table-printOrderSeqList").datagrid('getSelected');
							if(dataRow==null){
								$.messager.alert("提醒","请选择相应的顺序策略文件!");
								return false;
							}
							if(dataRow.state=='1'){
								$.messager.alert("提醒","抱歉，启用的顺序策略不能被启用!");
								return false;
							}
							$.messager.confirm("提醒","是否启用该顺序策略文件？",function(r){
								if(r){
									loading("启用中..");
									$.ajax({   
							            url :'agprint/tplorderseq/enablePrintOrderSeq.do?id='+ dataRow.id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
								            if(json.flag){
							            		$.messager.alert("提醒","启用成功");	
		        	        					$("#tplorderseq-query-queryPrintOrderSeqList").trigger("click");         		
								            }else{
			    	        	            	if(json.msg){
			    	        						$.messager.alert("提醒","启用失败!"+json.msg);
			    	        	            	}else{
			    	        						$.messager.alert("提醒","启用失败!");
			    	        	            	}
								            }
										}
							        });
								}
						  });
						}
					},
					</security:authorize>
					<security:authorize url="/agprint/tplorderseq/disablePrintOrderSeqBtn.do">
					{
						id:'button-id-disable',
						name:'禁用',
						iconCls:'button-close',
						handler:function(){	
							var dataRow=$("#tplorderseq-table-printOrderSeqList").datagrid('getSelected');
							if(dataRow==null){
								$.messager.alert("提醒","请选择相应的顺序策略文件!");
								return false;
							}
							if(dataRow.state=='0'){
								$.messager.alert("提醒","抱歉，已经禁用的顺序策略不能被禁用!");
								return false;
							}
							$.messager.confirm("提醒","是否禁用该顺序策略文件？",function(r){
								if(r){
									loading("禁用中..");
									$.ajax({   
							            url :'agprint/tplorderseq/disablePrintOrderSeq.do?id='+ dataRow.id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
								            if(json.flag){
							            		$.messager.alert("提醒","禁用成功");	
		        	        					$("#tplorderseq-query-queryPrintOrderSeqList").trigger("click");         		
								            }else{
			    	        	            	if(json.msg){
			    	        						$.messager.alert("提醒","禁用失败!"+json.msg);
			    	        	            	}else{
			    	        						$.messager.alert("提醒","禁用失败!");
			    	        	            	}
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
	 			model:'base',
				type:'list',
				datagrid:'tplorderseq-table-printOrderSeqList',
				tname:'t_print_orderseq',
				//id:''
	 		});
   			
   			
   			
   			$("#tplorderseq-table-printOrderSeqList").datagrid({
	   			fit:true,
	   			method:'post',
	   			title:'',
	   			rownumbers:true,
	  			pagination:true,
	  			idField:'id',
				sortName : 'viewid',
				sortOrder : 'desc',
	  			singleSelect:true,
	  			toolbar:'#tplorderseq-query-printOrderSeqList',
	  			url:'agprint/tplorderseq/showPrintOrderSeqPageListData.do',
	  			columns:[[
	  	  			{field:'viewid',title:'策略编号',width:60},
	  				{field:'code',title:'模板代码代码',width:150},
	  				{field:'codename',title:'模板代码名称',width:180},
	  				{field:'name',title:'策略名称',width:180},
	  				{field:'orderseq',title:'策略',width:150},
	  				{field:'state',title:'状态',width:100,
	  					formatter:function(val){
	  						if(val=='1'){
	  							return '启用';
	  						}
	  						else
	  						{
	  							return '禁用';
	  						}
	  					}
	  				},
	  				{field:'remark',title:'备注',width:150}
	  			]],
	  			onDblClickRow:function(index, dataRow){
	  				printOrderSeqOpenDialog("打印顺序策略【查看】", 'agprint/tplorderseq/showPrintOrderSeqViewPage.do?id='+dataRow.id);
	  			}
	   		});
   			//装载组合下拉框
//			$("#tplorderseq-combobox-typesList").combogrid({
//         		 width:200,
//         		 panelWidth:400,
//           		 idField:'type',
//           		 textField:'typename',
//           		 rownumbers:true,
//           		 filter:function(q,row){
//           		 	var id = row.type;
//           		 	var text = row.typename;
//           		 	if(id.indexOf(q)==0 || text.indexOf(q)==0){
//           		 		return true;
//           		 	}else{
//           		 		return false;
//           		 	}
//           		 },
//			     columns:[[
//			        {field:'type',title:'编码类型',width:150},
//			        {field:'typename',title:'编码类型名称',width:200}
//			     ]],
//   				url:'sysCode/showSysCodeTypes.do'
//			});

            $("#tplorderseq-combobox-typesList").widget({
                referwid:'RL_T_SYS_CODE',
                singleSelect:true,
                width:200
            });
   			
   			//回车事件
			controlQueryAndResetByKey("tplorderseq-query-queryPrintOrderSeqList","tplorderseq-query-reloadPrintOrderSeqList");
   			
   			//查询
  			$("#tplorderseq-query-queryPrintOrderSeqList").click(function(){
  				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#tplorderseq-form-printOrderSeqList").serializeJSON();
	       		$("#tplorderseq-table-printOrderSeqList").datagrid("load",queryJSON);
  			});
  			//重置
  			$('#tplorderseq-query-reloadPrintOrderSeqList').click(function(){
  				$("#tplorderseq-printOrderSeqList-query-code").widget('clear');
  				$("#tplorderseq-form-printOrderSeqList")[0].reset();
	       		$("#tplorderseq-table-printOrderSeqList").datagrid('loadData',{total:0,rows:[]});
  			});

   		});
	   	function printOrderSeqOpenDialog(title, url){
	   		$('<div id="tplorderseq-dialog-printOrderSeqOper-content"></div>').appendTo("#tplorderseq-dialog-printOrderSeqOper");
	   		$('#tplorderseq-dialog-printOrderSeqOper-content').dialog({  
			    title: title,  
			    fit:true,
			    closed: true,  
			    cache: false,  
			    href: url,
				maximizable:true,
				resizable:true,  
			    modal: true, 
			    onLoad:function(){
	   			},  
			    onClose:function(){
			    	$('#tplorderseq-dialog-printOrderSeqOper-content').window("destroy");
			    }
			});
			$('#tplorderseq-dialog-printOrderSeqOper-content').dialog('open');
	   	}
	   	
	   	function showPrintOrderSeqCreateDialog(options){
	   		if(options==null){
	   			options={};
	   		}
	   		var url='agprint/tplorderseq/showPrintOrderSeqOrderCreatePage.do';
   		 	$('<div id="tplorderseq-dialog-printOrderSeqOper-orderCreate-content"></div>').appendTo("#tplorderseq-dialog-printOrderSeqOper-orderCreate");
	   		 $('#tplorderseq-dialog-printOrderSeqOper-orderCreate-content').dialog({  
			    title: "生成排序策略",  
			    fit:true,
			    closed: true,  
			    cache: false,  
			    href: url,
				maximizable:true,
				resizable:true,  
			    modal: true, 
			    onLoad:function(){
			    	seqOrderCreatePageOnOpenInit(options);
	   			},
			    onClose:function(){
			    	$('#tplorderseq-dialog-printOrderSeqOper-orderCreate-content').window("destroy");
			    }
			});
			$('#tplorderseq-dialog-printOrderSeqOper-orderCreate-content').dialog('open'); 
	   	}
   	</script>
  </body>
</html>
