<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>打印纸张大小列表</title>
    <%@include file="/include.jsp" %> 
  </head>
  
  <body>
  	<table id="tplpapersize-table-printPaperSizeList"></table>
   	<div id="tplpapersize-query-printPaperSizeList" style="padding:0px;height:auto">
 	<div class="buttonBG" id="agprint-button-printPaperSize"></div>
		<form action="" id="tplpapersize-form-printPaperSizeList" method="post" style="padding-top: 2px;">
			<table>
			   <tr>
			   		<td>名称:</td>
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
		   				<a href="javaScript:void(0);" id="tplpapersize-query-queryPrintPaperSizeList" class="button-qr">查询</a>
    					<a href="javaScript:void(0);" id="tplpapersize-query-reloadPrintPaperSizeList" class="button-qr">重置</a>
		   			</td>
				</tr>
			</table>
  		</form>
   	</div>
   	<div style="display:none">
   		<div id="tplpapersize-dialog-printPaperSizeOper" ></div>
   		<div id="tplpapersize-dialog-printPaperSizeOper-orderCreate"></div>
   	</div>
   	<script type="text/javascript">
        $.extend($.fn.validatebox.defaults.rules, {
            validSeqInt:{
                validator:function(value,param){
                    var isInt='int';
                    if(param[0]==isInt){
                        var reg=/^[1-9][0-9]{0,4}$/;
                        return reg.test(value);
                    }
                },
                message:'请输入1-99999的整数类型数据!'
            }
        });
   		$(function(){
    		$("#tplpapersize-printPaperSizeList-query-code").widget({
	   			referwid:'RL_PRINT_TEMPLET_SUBJECT',
	   			singleSelect:true,
	   			width:'180'
	   		});
   				
   				
			$("#agprint-button-printPaperSize").buttonWidget({
	 			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{}
	 			],
				buttons:[
					<security:authorize url="/agprint/tplpapersize/showPrintPaperSizeAddPageBtn.do">
					{
						id:'button-id-add',
						name:'新增 ',
						iconCls:'button-add',
						handler:function(){
							printPaperSizeOpenDialog('打印纸张大小【新增】', 'agprint/tplpapersize/showPrintPaperSizeAddPage.do');							
						}
					},
					</security:authorize>
					<security:authorize url="/agprint/tplpapersize/showPrintPaperSizeEditPageBtn.do">
					{
						id:'button-id-edit',
						name:'修改 ',
						iconCls:'button-edit',
						handler:function(){
							var dataRow=$("#tplpapersize-table-printPaperSizeList").datagrid('getSelected');
							if(dataRow==null){
								$.messager.alert("提醒","请选择相应的纸张大小信息!");
								return false;
							}
							printPaperSizeOpenDialog("打印纸张大小【更新】", 'agprint/tplpapersize/showPrintPaperSizeEditPage.do?id='+dataRow.id);
						}
					},
					</security:authorize>
					<security:authorize url="/agprint/tplpapersize/deletePrintPaperSizeBtn.do">
					{
						id:'button-id-delete',
						name:'删除',
						iconCls:'button-delete',
						handler:function(){
							var dataRow=$("#tplpapersize-table-printPaperSizeList").datagrid('getSelected');
							if(dataRow==null || dataRow.id==null || dataRow.id==""){
								$.messager.alert("提醒","请选择相应的纸张大小信息!");
								return false;
							}
							if(dataRow.state=='1'){
								$.messager.alert("提醒","抱歉，启用的打印内容纸张大小不能删除!");
								return false;
							}
							$.messager.confirm("提醒","是否删除该纸张大小信息？",function(r){
								if(r){
									loading("删除中..");
									$.ajax({   
							            url :'agprint/tplpapersize/deletePrintPaperSize.do?id='+ dataRow.id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
								            if(json.flag){
							            		$.messager.alert("提醒","删除成功");	 
			    	        					$("#tplpapersize-query-queryPrintPaperSizeList").trigger("click");     		
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
					<security:authorize url="/agprint/tplpapersize/enablePrintPaperSizeBtn.do">
					{
						id:'button-id-enable',
						name:'启用 ',
						iconCls:'button-open',
						handler:function(){	
							var dataRow=$("#tplpapersize-table-printPaperSizeList").datagrid('getSelected');
							if(dataRow==null){
								$.messager.alert("提醒","请选择相应的纸张大小文件!");
								return false;
							}
							if(dataRow.state=='1'){
								$.messager.alert("提醒","抱歉，启用的纸张大小不能被启用!");
								return false;
							}
							$.messager.confirm("提醒","是否启用该纸张大小文件？",function(r){
								if(r){
									loading("启用中..");
									$.ajax({   
							            url :'agprint/tplpapersize/enablePrintPaperSize.do?id='+ dataRow.id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
								            if(json.flag){
							            		$.messager.alert("提醒","启用成功");	
		        	        					$("#tplpapersize-query-queryPrintPaperSizeList").trigger("click");         		
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
					<security:authorize url="/agprint/tplpapersize/disablePrintPaperSizeBtn.do">
					{
						id:'button-id-disable',
						name:'禁用',
						iconCls:'button-close',
						handler:function(){	
							var dataRow=$("#tplpapersize-table-printPaperSizeList").datagrid('getSelected');
							if(dataRow==null){
								$.messager.alert("提醒","请选择相应的纸张大小文件!");
								return false;
							}
							if(dataRow.state=='0'){
								$.messager.alert("提醒","抱歉，已经禁用的纸张大小不能被禁用!");
								return false;
							}
							$.messager.confirm("提醒","是否禁用该纸张大小文件？",function(r){
								if(r){
									loading("禁用中..");
									$.ajax({   
							            url :'agprint/tplpapersize/disablePrintPaperSize.do?id='+ dataRow.id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
								            if(json.flag){
							            		$.messager.alert("提醒","禁用成功");	
		        	        					$("#tplpapersize-query-queryPrintPaperSizeList").trigger("click");         		
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
				datagrid:'tplpapersize-table-printPaperSizeList',
				tname:'t_print_papersize',
				//id:''
	 		});
   			
   			
   			
   			$("#tplpapersize-table-printPaperSizeList").datagrid({
	   			fit:true,
	   			method:'post',
	   			title:'',
	   			rownumbers:true,
	  			pagination:true,
	  			idField:'id',
				sortName : 'seq',
				sortOrder : 'asc',
	  			singleSelect:true,
	  			toolbar:'#tplpapersize-query-printPaperSizeList',
	  			url:'agprint/tplpapersize/showPrintPaperSizePageListData.do',
	  			columns:[[
	  	  			{field:'id',title:'编号',width:200,hidden:true},
	  				{field:'name',title:'名称',width:180},
					{field:'width',title:'宽度(厘米)',width:100,
						formatter:function(val){
							return formatterMoney(val);
						}
					},
					{field:'height',title:'高度(厘米)',width:100,
						formatter:function(val){
							return formatterMoney(val);
						}
					},
	  				{field:'seq',title:'排序',width:80},
	  				{field:'state',title:'状态',width:80,
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
	  				printPaperSizeOpenDialog("打印纸张大小【查看】", 'agprint/tplpapersize/showPrintPaperSizeViewPage.do?id='+dataRow.id);
	  			}
	   		});
   			//装载组合下拉框
//			$("#tplpapersize-combobox-typesList").combogrid({
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

            $("#tplpapersize-combobox-typesList").widget({
                referwid:'RL_T_SYS_CODE',
                singleSelect:true,
                width:200
            });
   			
   			//回车事件
			controlQueryAndResetByKey("tplpapersize-query-queryPrintPaperSizeList","tplpapersize-query-reloadPrintPaperSizeList");
   			
   			//查询
  			$("#tplpapersize-query-queryPrintPaperSizeList").click(function(){
  				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#tplpapersize-form-printPaperSizeList").serializeJSON();
	       		$("#tplpapersize-table-printPaperSizeList").datagrid("load",queryJSON);
  			});
  			//重置
  			$('#tplpapersize-query-reloadPrintPaperSizeList').click(function(){
  				$("#tplpapersize-printPaperSizeList-query-code").widget('clear');
  				$("#tplpapersize-form-printPaperSizeList")[0].reset();
	       		$("#tplpapersize-table-printPaperSizeList").datagrid('loadData',{total:0,rows:[]});
  			});

   		});
	   	function printPaperSizeOpenDialog(title, url){
	   		$('<div id="tplpapersize-dialog-printPaperSizeOper-content"></div>').appendTo("#tplpapersize-dialog-printPaperSizeOper");
	   		$('#tplpapersize-dialog-printPaperSizeOper-content').dialog({  
			    title: title,  
			    //fit:true,
				width:460,
				height:320,
			    closed: true,  
			    cache: false,  
			    href: url,
				maximizable:true,
				resizable:true,  
			    modal: true, 
			    onLoad:function(){
	   			},  
			    onClose:function(){
			    	$('#tplpapersize-dialog-printPaperSizeOper-content').dialog("destroy");
			    }
			});
			$('#tplpapersize-dialog-printPaperSizeOper-content').dialog('open');
	   	}

   	</script>
  </body>
</html>
