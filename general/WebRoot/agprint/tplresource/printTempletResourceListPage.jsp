<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>打印模板资源列表</title>
    <%@include file="/include.jsp" %> 
  </head>
  
  <body>  
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north',border:false">
   			<div class="buttonBG" id="tplresource-button-printTempletResourceList"></div>
  	 	</div>
        <div data-options="region:'center'">
  			<table id="tplresource-table-printTempletResourceList"></table>
		   	<div id="tplresource-query-printTempletResourceList" style="padding:0px;height:auto">
				<form action="" id="tplresource-form-printTempletResourceList" method="post" style="padding-top: 2px;">
					<table>
						<tr>
							<td>模板代码:</td>
							<td><input type="text" id="tplresource-printTempletResourceList-query-code" name="code" style="width:180px" /></td>
							<td>资源编号:</td>
							<td><input type="text" name="viewid" style="width:120px" /></td>
							<td colspan="2">
								状态:
		   						<select name="state" style="width:60px;" id="tplmanage-state-printTempletListQuery">
		   							<option value=""></option>
		   							<option value="1">启用</option>
		   							<option value="0">禁用</option>
		   						</select>
		   						预置:
		   						<select name="issystem" style="width:60px;"id="tplmanage-issystem-printTempletListQuery">
		   							<option value=""></option>
		   							<option value="1">是</option>
		   							<option value="0">否</option>
		   						</select>
							</td>
						</tr>
						<tr>
							<td>资源名称:</td>
							<td><input type="text" name="name" style="width:180px" /></td>
							<td></td>
							<td></td>
				   			<td>
				   				<a href="javaScript:void(0);" id="tplresource-query-queryPrintTempletResourceList" class="button-qr">查询</a>
		    					<a href="javaScript:void(0);" id="tplresource-query-reloadPrintTempletResourceList" class="button-qr">重置</a>
				   			</td>
						</tr>
					</table>
		  		</form>
		   </div>
   		</div>
   </div>
   <div style="display:none">
   	<div id="tplresource-dialog-printTempletResourceOper" ></div>
	   <div id="tplpapersize-dialog-printPaperSizeOper" ></div>
   </div>
   	<script type="text/javascript">
   		$(function(){
    		$("#tplresource-printTempletResourceList-query-code").widget({
	   			referwid:'RL_PRINT_TEMPLET_SUBJECT',
	   			singleSelect:true,
	   			width:'180'
	   		});
  			$.extend($.fn.validatebox.defaults.rules, {
    			validInt:{
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
   			
   		//根据初始的列与用户保存的列生成以及字段权限生成新的列
   	    	var templetSubjectListColJson=$("#tplresource-table-printTempletResourceList").createGridColumnLoad({
   		     	name:'print_templet_resouce',
   		     	frozenCol:[[
   					{field:'idok',checkbox:true,isShow:true}
   		     	]],
   		     	commonCol:[[
   		   		    {field:'viewid',title:'资源编号',width:60},
   		    		{field:'code',title:'模板代码代码',width:150},
   		    		{field:'codename',title:'模板代码名称',width:180},
	  				{field:'name',title:'资源名称',width:150},
	  				{field:'templetfile',title:'模板文件(jasper)',width:150 ,
                        formatter:function(value,rowData,rowIndex){
                            if(rowData.issystem=='1'){
                                return value+" (<a href=\"agprint/tplresource/downloadTempletSourceFile.do?type=2&id="+rowData.id+"\" target=\"_blank\">下载</a>)";
                            }else if(rowData.templetfileid!=null && rowData.templetfileid!=""){
                                return value+" (<a href=\"common/download.do?id="+rowData.templetfileid+"\" target=\"_blank\">下载</a>)";
                            }else{
                                return value;
                            }
                        }
                    },
                    {field:'sourcefile',title:'模板源文件(jrxml)',width:150,
                        formatter:function(value,rowData,rowIndex){
                            if(rowData.issystem=='1'){
                                return "<a href=\"agprint/tplresource/downloadTempletSourceFile.do?id="+rowData.id+"\" target=\"_blank\">"+value+"</a>";
                            }else if(rowData.sourcefileid!=null && rowData.sourcefileid!=""){
                                return "<a href=\"common/download.do?id="+rowData.sourcefileid+"\" target=\"_blank\">"+value+"</a>";
                            }else{
                                return value;
                            }
                        }
                    },
                    {field:'issystem',title:'是否预置',
                        formatter:function(value,rowData,rowIndex){
                            if(value=="1"){
                                return "是";
                            }else{
                                return "否";
                            }
                        }
                    },
					{field:'papersizename',title:'纸张大小',width:120,
						formatter:function(value,rowData,rowIndex){
							if(null!=rowData.papersizeid && ""!=rowData.papersizeid){
								return "<a href=\"javascript:void(0);\" onclick=\"javascript:templetResourceOpenDialogForPapaerSizeInfo('"+rowData.papersizeid+"');\">"+value+"</a>";
							}else{
								return value;
							}
						}
					},
	  				{field:'state',title:'状态',width:80,
	  					formatter:function(val){
	  						if(val=='1'){
	  							return '启用';
	  						}else{
	  							return '禁用';
	  						}
	  					}
	  				},
                    {field:'remark',title:'备注',width:150 },
                    {field:'addusername',title:'创建人',width:100},
                    {field:'addtime',title:'创建时间',width:120},
                    {field:'modifyusername',title:'修改人',width:100,hidden:true},
                    {field:'modifytime',title:'修改时间',width:120}
   				]]
   		     });
   			
   			$("#tplresource-table-printTempletResourceList").datagrid({
     			authority:templetSubjectListColJson,
	  	 		frozenColumns:templetSubjectListColJson.frozen,
				columns:templetSubjectListColJson.common,
	   			fit:true,
	   			method:'post',
	   			title:'',
	   			rownumbers:true,
	  			pagination:true,
	  			idField:'id',
				sortName : 'viewid',
				sortOrder : 'desc',	  			
	  			singleSelect:true,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
	  			toolbar:'#tplresource-query-printTempletResourceList',
	  			url:'agprint/tplresource/showPrintTempletResourcePageListData.do',
	  			onDblClickRow:function(index, dataRow){
	  				resourceADMOperDialog("模板资源【查看】", 'agprint/tplresource/showPrintTempletResourceViewPage.do?id='+dataRow.id);
	  			}
	   		});
   			
   			//回车事件
			controlQueryAndResetByKey("tplresource-query-queryPrintTempletResourceList","tplresource-query-reloadPrintTempletResourceList");
   			
   			//查询
  			$("#tplresource-query-queryPrintTempletResourceList").click(function(){
  				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#tplresource-form-printTempletResourceList").serializeJSON();
	       		$("#tplresource-table-printTempletResourceList").datagrid("load",queryJSON);
  			});
  			//重置
  			$('#tplresource-query-reloadPrintTempletResourceList').click(function(){

  				$("#tplresource-printTempletResourceList-query-code").widget('clear');
  				$("#tplresource-form-printTempletResourceList")[0].reset();
	       		$("#tplresource-table-printTempletResourceList").datagrid('loadData',{total:0,rows:[]});
  			});
  			
  			
  			$("#tplresource-button-printTempletResourceList").buttonWidget({
     			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{}
	 			],
				buttons:[
					<security:authorize url="/agprint/tplresource/printTempletResourceAddBtn.do">
					{
						id:'button-id-add',
						name:'新增 ',
						iconCls:'button-add',
						handler:function(){
							resourceADMOperDialog('模板资源【新增】', 'agprint/tplresource/showPrintTempletResourceAddPage.do');
						}
					},
					</security:authorize>
					<security:authorize url="/agprint/tplresource/printTempletResourceEditBtn.do">
					{
						id:'button-id-edit',
						name:'修改 ',
						iconCls:'button-edit',
						handler:function(){
							var dataRow=$("#tplresource-table-printTempletResourceList").datagrid('getSelected');
							if(dataRow==null || dataRow.id==""){
								$.messager.alert("提醒","请选择相应的打印模板资源!");
								return false;
							}
							resourceADMOperDialog("模板资源【修改】", 'agprint/tplresource/showPrintTempletResourceEditPage.do?id='+dataRow.id);
						}
					},
					</security:authorize>
					<security:authorize url="/agprint/tplresource/printTempletResourceDelBtn.do">
					{
						id:'button-id-delete',
						name:'删除',
						iconCls:'button-delete',
						handler:function(){
							var dataRow=$("#tplresource-table-printTempletResourceList").datagrid('getSelected');
							if(dataRow==null || dataRow.id==null || dataRow.id==""){
								$.messager.alert("提醒","请选择相应的打印模板资源!");
								return false;
							}
							if(dataRow.state=='1'){
								$.messager.alert("提醒","抱歉，启用的打印模板资源不能删除!");
								return false;
							}
							if(dataRow.issystem=='1'){
								$.messager.alert("提醒","抱歉，预置的打印模板资源不能删除!");
								return false;
							}
							$.messager.confirm("提醒","是否删除该打印模板资源信息？",function(r){
								if(r){
									loading("删除中..");
									$.ajax({   
							            url :'agprint/tplresource/deletePrintTempletResource.do?id='+ dataRow.id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
								            if(json.flag){
							            		$.messager.alert("提醒","删除成功");	 
			    	        					$("#tplresource-query-queryPrintTempletResourceList").trigger("click");     		
								            }else{
			    	        	            	if(json.msg){
			    	        						$.messager.alert("提醒","删除失败,"+json.msg);
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
					<security:authorize url="/agprint/tplresource/printTempletResourceEnableBtn.do">
					{
						id:'button-id-enable',
						name:'启用',
						iconCls:'button-open',
						handler: function(){
							var dataRow =  $("#tplresource-table-printTempletResourceList").datagrid('getSelected');
							if(dataRow==null){
			  					$.messager.alert("提醒","请选择启用的打印模板资源!");
			  					return false;
			  				}
			  				if(dataRow.id == ""){
			  					$.messager.alert("提醒","抱歉，未能找到要启用的打印模板资源!");
			  					return false;
			  				}
			  				if(dataRow.state == "1"){
			  					$.messager.alert("提醒","启用状态不能启用!");
			  					return false;
			  				}
			  				$.ajax({
			  					url:'agprint/tplresource/enablePrintTempletResource.do?id='+dataRow.id,
			  					type:'post',
			  					dataType:'json',
			  					success:function(json){
			  						if(json.flag==true){
			  							$.messager.alert("提醒","打印模板资源启用成功!");
			  							$("#tplresource-table-printTempletResourceList").datagrid('reload');
			  						}else{
			  							$.messager.alert("提醒","打印模板资源启用失败 !");
			  						}
			  					}
			  				});
						}
					},
					</security:authorize>				
					<security:authorize url="/agprint/tplresource/printTempletResourceDisableBtn.do">
					{
						id:'button-id-disable',
						name:'禁用',
						iconCls:'button-close',
						handler: function(){
							var dataRow =  $("#tplresource-table-printTempletResourceList").datagrid('getSelected');
							if(dataRow==null){
			  					$.messager.alert("提醒","请选择要禁用的打印模板资源!");
			  					return false;
			  				}
			  				if(dataRow.id == ""){
			  					$.messager.alert("提醒","抱歉，未能找到要禁用的打印模板资源!");
			  					return false;
			  				}
			  				if(dataRow.state == "0"){
			  					$.messager.alert("提醒","禁用状态不能禁用!");
			  					return false;
			  				}
			  				$.ajax({
			  					url:'agprint/tplresource/disablePrintTempletResource.do?id='+dataRow.id,
			  					type:'post',
			  					dataType:'json',
			  					success:function(json){
			  						if(json.flag==true){
			  							$.messager.alert("提醒","打印模板资源禁用成功!");
			  							$("#tplresource-table-printTempletResourceList").datagrid('reload');
			  						}else{
			  							$.messager.alert("提醒","打印模板资源禁用失败 !");
			  						}
			  					}
			  				});
						}
					},
					</security:authorize>
					{}		
				],
	 			model:'bill',
				type:'list',
				datagrid:'tplresource-table-printTempletResourceList',
				tname:'print_templet_subject',
				id:''
     		});
  			
   		});
	    function resourceADMOperDialog(title, url){
	    	$('<div id="tplresource-dialog-printTempletResourceOper-content"></div>').appendTo("#tplresource-dialog-printTempletResourceOper");
	   		$('#tplresource-dialog-printTempletResourceOper-content').dialog({  
			    title: title,  
			    width: 450,  
			    height: 450,  
			    closed: true,  
			    cache: false,  
			    href: url,
				maximizable:true,
				resizable:true,  
			    modal: true, 
			    onLoad:function(){
	   			},  
			    onClose:function(){
			    	$('#tplresource-dialog-printTempletResourceOper-content').window("destroy");
			    }
			});
			$('#tplresource-dialog-printTempletResourceOper-content').dialog('open');
	   	}
	    var templetSubject_getAjaxContent = function (param, url) { //同步ajax
		    var ajax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: url,
		        data: param,
		        async: false
		    });
		    return ajax.responseText;
		}
		function printPaperSizeOpenDialog(title, url,onLoadFunc){
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
					if(onLoadFunc!=null && typeof(onLoadFunc) == "function" ){
						onLoadFunc();
					}
				},
				onClose:function(){
					$('#tplpapersize-dialog-printPaperSizeOper-content').dialog("destroy");
				}
			});
			$('#tplpapersize-dialog-printPaperSizeOper-content').dialog('open');
		}

		function templetResourceOpenDialogForPapaerSizeInfo(id,title){
			if(null==id || ""==id){
				return false;
			}
			if(null==title){
				title='打印内容数据排序策略查看';
			}
			var url='agprint/tplpapersize/showPrintPaperSizeViewPage.do?id='+id;
			templetResourceOpenDialogForOtherInfo(title,url,{width:450,height:300});
		}
		function templetResourceOpenDialogForOtherInfo(title,url,options){
			$('<div id="tplresource-dialog-printTempletResourceOper-content"></div>').appendTo("#tplresource-dialog-printTempletResourceOper");

			var dialogOpts={
				title: title,
				width: 450,
				height: 450,
				closed: false,
				cache: false,
				href: url,
				maximizable:true,
				resizable:true,
				modal: true,
				onLoad:function(){
				},
				onClose:function(){
					$('#tplresource-dialog-printTempletResourceOper-content').window("destroy");
				}
			};
			if(undefined != options){
				dialogOpts = jQuery.extend({}, dialogOpts, options);
			}
			$('#tplresource-dialog-printTempletResourceOper-content').dialog(dialogOpts);
			$('#tplresource-dialog-printTempletResourceOper-content').dialog('open');
		}
   	</script>
  </body>
</html>
