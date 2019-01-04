<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>打印模板配置管理页面</title>
    <%@include file="/include.jsp" %>
  	<script type="text/javascript" src="js/uploadify/jquery.uploadify.js"></script>
	<link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>
	<link rel="stylesheet" href="js/uploadify/uploadify.css" type="text/css"></link>
  </head>
  <body>
  	<div class="easyui-layout" data-options="fit:true">
    	<%--<div data-options="region:'north'" style="padding: 0px">--%>
    		<%--<div class="buttonBG" id="tplmanage-button-printTemplet"></div>--%>
    	<%--</div>--%>
    	<div data-options="region:'center',border:false" style="border: 0px;">
	  		<div id="tplmanage-table-printTempletBtn" style="padding: 0px">
            <div class="buttonBG" id="tplmanage-button-printTemplet"></div>
		  		<form action="" id="tplmanage-form-printTempletListQuery" method="post">
		   			<table>
		   				<tr>
		   					<td style="padding-left: 10px;">模板代码:&nbsp;</td>
		   					<td>
		   						<input type="text" id="tplmanage-PrintTempletList-query-code" name="thecode" style="width:180px" />
		   					</td>
		   					<td colspan="2">
		   						状态:
		   						<select name="state" style="width:60px;" id="tplmanage-state-printTempletListQuery">
		   							<option value=""></option>
		   							<option value="1" selected="selected">启用</option>
		   							<option value="0">禁用</option>
		   						</select>
		   						预置:
		   						<select name="issystem" style="width:60px;"id="tplmanage-issystem-printTempletListQuery">
		   							<option value=""></option>
		   							<option value="1">是</option>
		   							<option value="0">否</option>
		   						</select>
							</td>
                            <td style="padding-left: 10px;">描述名:&nbsp;</td>
                            <td><input name="name"  type="text" id="tplmanage-name-printTempletListQuery" style="width: 158px;"/></td>
		   				</tr>
		   				<tr>
		   					<td style="padding-left: 10px;">是否关联:&nbsp;</td>
		   					<td>
		   						<input id="tplmanage-liketype-printTempletListQuery" name="linktype"  style="width:180px;" />
							</td>
		   					<td colspan="2">
		   						<div id="tplmanage-likedata-printTempletListQuery-uidiv" linktype="0"></div>
		   						<input type="hidden" name="linkdataarr" id="tplmanage-hidden-likedata-printTempletListQuery" />
		   					</td>
		   					<td colspan="2">		   						
		   						&nbsp;
		   						<a href="javaScript:void(0);" id="tplmanage-query-printTempletList" class="button-qr">查询</a>
					    		<a href="javaScript:void(0);" id="tplmanage-query-printTemplet-reloadList" class="button-qr">重置</a>
		   					</td>
		   				</tr>
		   			</table>
		   		</form>
            </div>
	  		<table id="tplmanage-table-printTemplet"></table>
	  		<div style="display:none">
	   			<div id="printTemplet-dialog-add-operate"></div>
	   			<div id="printTemplet-dialog-operate-otherinfo"></div>
   				<div id="tplresource-dialog-printTempletResourceOper" ></div>
		   		<div id="tplorderseq-dialog-printOrderSeqOper" ></div>
		   		<div id="tplorderseq-dialog-printOrderSeqOper-orderCreate"></div>
	   		</div>
   	 	</div>
   	 </div>
	<div id="printTemplet-help-companytitle" style="display: none">
		打印模板公司抬头参数<br/>
		一、参数“P_CompanyName”：<br/>
		1、如果系统参数“CompanyNameForPrint”有值，就取此值。<br/>
		2、如果系统参数“COMPANYNAME”有值，就取此值。<br/>
		二、参数“P_TPL_COMPANYNAME”：当前模板里设置的公司抬头。<br/>
		三、参数“P_SYSAUTO_CompanyName”，取值按以下顺序<br/>
		1、如果模板设置填写了“公司抬头”，就取此值。<br/>
		2、如果系统参数“CompanyNameForPrint”有值，就取此值。<br/>
		3、如果系统参数“COMPANYNAME”有值，就取此值。<br/>
	</div>
	<div id="printTemplet-help-countperpage" style="display: none">
		打印模板每页条数：<br/>
		一、“每页条数”大于0才会启作用，目前用于“填充空行”。<br/>
		二、“每页条数”小于实际条数时，最后一页可能会出现空行页面。<br/>
		三、勾选了“填充空行”后，如果最后一页小于“每页条数”，会用空行填充。<br/>
	</div>
	<div id="printTemplet-help-lodophtmlmodel" style="display: none">
		（1）CLODOP超文本打印模式有两种：<br/>
		&nbsp;&nbsp;a)ADD_PRINT_HTM：超文本普通模式，某些样式无法解析，但清晰度好<br/>
		&nbsp;&nbsp;b)ADD_PRINT_HTML：超文本图形模式,类似截图输出，可以解析某些样式，清晰度不如ADD_PRINT_HTM<br/>
	</div>
   	 <script type="text/javascript">
	   	$(function(){
    		$.extend($.fn.validatebox.defaults.rules, {
    			validSeqInt:{
    				validator:function(value,param){
    					if(value!=null){
    						var reg=/^[1-9][0-9]{0,4}$/;
    						return reg.test(value);
    					}
    					return false; 
    				},
    				message:'请输入1-99999的整数类型数据!'
    			}
    		});
    		$("#tplmanage-PrintTempletList-query-code").widget({
	   			referwid:'RL_PRINT_TEMPLET_SUBJECT',
	   			singleSelect:true,
	   			width:'180'
	   		});
	
			$("#tplmanage-button-printTemplet").buttonWidget({
	 			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{}
	 			],
				buttons:[
					<security:authorize url="/agprint/tplmanage/showPrintTempletAddPageBtn.do">
					{
						id:'button-id-add',
						name:'新增 ',
						iconCls:'button-add',
						handler:function(){
							printTempletOpenDialog('打印模板配置【新增】', 'agprint/tplmanage/showPrintTempletAddPage.do');							
						}
					},
					</security:authorize>
					<security:authorize url="/agprint/tplmanage/showPrintTempletEditPageBtn.do">
					{
						id:'button-id-edit',
						name:'修改 ',
						iconCls:'button-edit',
						handler:function(){
							var dataRow=$("#tplmanage-table-printTemplet").datagrid('getSelected');
							if(dataRow==null){
								$.messager.alert("提醒","请选择相应的模板信息!");
								return false;
							}
							printTempletOpenDialog("打印模板配置【更新】", 'agprint/tplmanage/showPrintTempletEditPage.do?id='+dataRow.id);
						}
					},
					</security:authorize>
					<security:authorize url="/agprint/tplmanage/deletePrintTempletBtn.do">
					{
						id:'button-id-delete',
						name:'删除',
						iconCls:'button-delete',
						handler:function(){
							var dataRow=$("#tplmanage-table-printTemplet").datagrid('getSelected');
							if(dataRow==null){
								$.messager.alert("提醒","请选择相应的模板信息!");
								return false;
							}
							if(!(dataRow.detailid==null ||dataRow.detailid=='')){
								$.messager.alert("提醒","抱歉，指定模板文件后不能删除!");
								return false;
							}
							$.messager.confirm("提醒","是否删除该模板信息？",function(r){
								if(r){
									loading("删除中..");
									$.ajax({   
							            url :'agprint/tplmanage/deletePrintTemplet.do?id='+ dataRow.id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
								            if(json.flag){
							            		$.messager.alert("提醒","删除成功");	 
			    	        					$("#tplmanage-query-printTempletList").trigger("click");     		
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
					<security:authorize url="/agprint/tplmanage/enablePrintTempletBtn.do">
					{
						id:'button-id-enable',
						name:'启用 ',
						iconCls:'button-open',
						handler:function(){	
							var dataRow=$("#tplmanage-table-printTemplet").datagrid('getSelected');
							if(dataRow==null){
								$.messager.alert("提醒","请选择相应的模板文件!");
								return false;
							}
							if(dataRow.state=='1'){
								$.messager.alert("提醒","抱歉，启用的模板不能被启用!");
								return false;
							}
							$.messager.confirm("提醒","是否启用该模板文件？",function(r){
								if(r){
									loading("启用中..");
									$.ajax({   
							            url :'agprint/tplmanage/enablePrintTemplet.do?id='+ dataRow.id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
								            if(json.flag){
							            		$.messager.alert("提醒","启用成功");	
		        	        					$("#tplmanage-query-printTempletList").trigger("click");         		
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
					<security:authorize url="/agprint/tplmanage/disablePrintTempletBtn.do">
					{
						id:'button-id-disable',
						name:'禁用',
						iconCls:'button-close',
						handler:function(){	
							var dataRow=$("#tplmanage-table-printTemplet").datagrid('getSelected');
							if(dataRow==null){
								$.messager.alert("提醒","请选择相应的模板文件!");
								return false;
							}
							if(dataRow.state=='0'){
								$.messager.alert("提醒","抱歉，已经禁用的模板不能被禁用!");
								return false;
							}
							$.messager.confirm("提醒","是否禁用该模板文件？",function(r){
								if(r){
									loading("禁用中..");
									$.ajax({   
							            url :'agprint/tplmanage/disablePrintTemplet.do?id='+ dataRow.id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
								            if(json.flag){
							            		$.messager.alert("提醒","禁用成功");	
		        	        					$("#tplmanage-query-printTempletList").trigger("click");         		
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
					<%--
					<security:authorize url="/agprint/tplmanage/defaultPrintTempletBtn.do">
					{
						id:'button-id-default',
						name:'设置为默认 ',
						iconCls:'button-open',
						handler:function(){	
							var dataRow=$("#tplmanage-table-printTemplet").datagrid('getSelected');
							if(dataRow==null){
								$.messager.alert("提醒","请选择相应的模板文件!");
								return false;
							}
							if(dataRow.isdefault=='1'){
								$.messager.alert("提醒","抱歉，设置模板文件不能被启用!");
								return false;
							}
							$.messager.confirm("提醒","是否启用该模板文件？",function(r){
								if(r){
									loading("启用中..");
									$.ajax({   
							            url :'agprint/tplmanage/updatePrintTempletDefault.do?id='+ dataRow.id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
								            if(json.flag){
							            		$.messager.alert("提醒","设置默认成功");	
		        	        					$("#tplmanage-query-printTempletList").trigger("click");         		
								            }else{
			    	        	            	if(json.msg){
			    	        						$.messager.alert("提醒","设置默认失败!"+json.msg);
			    	        	            	}else{
			    	        						$.messager.alert("提醒","设置默认失败!");
			    	        	            	}
								            }
										}
							        });
								}
						  });
						}
					},
					</security:authorize>
					--%>
					{}		
				],
	 			model:'base',
				type:'list',
				datagrid:'tplmanage-table-printTemplet',
				tname:'t_print_templet',
				//id:''
	 		});

            var printTempletJson = $("#tplmanage-table-printTemplet").createGridColumnLoad({
                frozenCol : [[ ]],
                commonCol :[
                    [
                        {field:'idok',checkbox:true,isShow:true},
                        {field:'id',title:'序号',width:50,sortable:true},
                        {field:'seq',title:'排序',width:35},
                        {field:'code',title:'模板代码',width:150,
                        	formatter:function(value,rowData,rowIndex){
                        		return "<a href=\"javascript:void(0);\" onclick=\"javascript:printTempletOpenDialogForSubjectInfo('"+value+"');\">"+value+"</a>";
                        	}
                        },
                        {field:'codename',title:'模板代码名',width:180,isShow:true},
                        {field:'name',title:'模板描述名称',width:120},
                        {field:'companytitle',title:'公司抬头',width:120},
                        {field:'mark',title:'模板标识',width:100 },
                        {field:'tplresourcename',title:'模板资源名称',width:120,
                        	formatter:function(value,rowData,rowIndex){
                        		if(null!=rowData.tplresourceid && ""!=rowData.tplresourceid){
                        			return "<a href=\"javascript:void(0);\" onclick=\"javascript:printTempletOpenDialogForResourceInfo('"+rowData.tplresourceid+"');\">"+value+"</a>";
                        		}else{
                        			return value;
                        		}
                        	}
                         },
                        {field:'templetfile',title:'模板文件(jasper)',width:150 ,
                            formatter:function(value,rowData,rowIndex){
                                if(rowData.issystem=='1'){
                                    return value+" (<a href=\"agprint/tplmanage/downloadTempletSourceFile.do?type=2&id="+rowData.id+"\" target=\"_blank\">下载</a>)";
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
                                    return "<a href=\"agprint/tplmanage/downloadTempletSourceFile.do?id="+rowData.id+"\" target=\"_blank\">"+value+"</a>";
                                }else if(rowData.sourcefileid!=null && rowData.sourcefileid!=""){
                                    return "<a href=\"common/download.do?id="+rowData.sourcefileid+"\" target=\"_blank\">"+value+"</a>";
                                }else{
                                    return value;
                                }
                            }
                        },
                        {field:'tplorderseqname',title:'内容排序策略',width:120,
                        	formatter:function(value,rowData,rowIndex){
                        		if(null!=rowData.tplorderseqid && ""!=rowData.tplorderseqid){
                        			return "<a href=\"javascript:void(0);\" onclick=\"javascript:printTempletOpenDialogForOrderseqInfo('"+rowData.tplorderseqid+"');\">"+value+"</a>";
                        		}else{
                        			return value;
                        		}
                        	}
                        },
                        {field:'countperpage',title:'每页条数',width:35},
                        {field:'isfillblank',title:'是否填充空行',width:50,
                            formatter:function(value,rowData,rowIndex){
                                if(value=="1"){
                                    return "是";
								}else {
                                    return "否";
								}
                            }
                        },
                        {field:'linktype',title:'关联',width:50,
                            formatter:function(value,rowData,rowIndex){
                                return getSysCodeName('printTempletLinkType',value);
                            }
                        },
                        {field:'linkdataname',title:'关联数据',
                            formatter:function(value,rowData,rowIndex){
                                if(value){
                                    return "<a href=\"javascript:void(0);\" title=\""+value+"\" onclick=\"javascript:showMoreDeptNameInfo(this);\">"+value+"</a>";
                                }else{
                                    return value;
                                }
                            }
                        },
                        <%--
                       {field:'isdefault',title:'是否默认',
                            formatter:function(value,rowData,rowIndex){
                               if(value=="1"){
                                   return "是";
                               }else{
                                   return "否";
                               }
                           }
                        },
                        --%>
                        {field:'issystem',title:'是否预置',
                            formatter:function(value,rowData,rowIndex){
                                if(value=="1"){
                                    return "是";
                                }else{
                                    return "否";
                                }
                            }
                        },
                        {field:'state',title:'状态',width:35,
                            formatter:function(value,rowData,rowIndex){
                                if(value=="1"){
                                    return "启用";
                                }else{
                                    return "禁用";
                                }
                            }
                        },
                        {field:'lodophtmlmodel',title:'lodop超文本模式',width:80,
                            formatter:function(value,rowData,rowIndex){
                                if(value=="2"){
                                    return "图形模式";
                                }else{
                                    return "普通模式";
                                }
                            }
                        },
                        {field:'remark',title:'备注',width:100 },
                        {field:'addusername',title:'创建人',width:100,hidden:true},
                        {field:'addtime',title:'创建时间',width:120},
                        {field:'modifyusername',title:'修改人',width:100,hidden:true},
                        {field:'modifytime',title:'修改时间',width:120}
                    ]
        ]
            });
			var initQueryJSON = $("#tplmanage-form-printTempletListQuery").serializeJSON();
     		$("#tplmanage-table-printTemplet").datagrid({
                authority:printTempletJson,
                frozenColumns:printTempletJson.frozen,
                columns:printTempletJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		//title:'模板管理',
	  	 		showFooter: true,
                rownumbers:true,
                pagination:true,
		 		idField:'id',
	  	 		singleSelect:true,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		queryParams:initQueryJSON,
				//pageSize:20,
				toolbar:'#tplmanage-table-printTempletBtn',
				//pageList:[20,50,200],
				url: 'agprint/tplmanage/showPrintTempetPageListData.do',
	  			onDblClickRow:function(index, dataRow){
	  				printTempletOpenDialog("打印模板配置【查看】", 'agprint/tplmanage/showPrintTempletViewPage.do?id='+dataRow.id);		 			
		 		}
			}).datagrid("columnMoving");

			//回车事件
			controlQueryAndResetByKey("tplmanage-query-printTempletList","tplmanage-query-printTemplet-reloadList");
			
			//查询
			$("#tplmanage-query-printTempletList").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#tplmanage-form-printTempletListQuery").serializeJSON();

	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#tplmanage-table-printTemplet").datagrid('load',queryJSON);	
			});
			
			//重置按钮
			$("#tplmanage-query-printTemplet-reloadList").click(function(){
				$("#tplmanage-PrintTempletList-query-code").widget('clear');
				$("#tplmanage-liketype-printTempletListQuery").widget('clear');
				$("#tplmanage-form-printTempletListQuery").form('reset');
                var initQueryJSON = $("#tplmanage-form-printTempletListQuery").serializeJSON();
				$("#tplmanage-table-printTemplet").datagrid('load',initQueryJSON);
			});
			
			//关联下拉控件选择时
    		$("#tplmanage-liketype-printTempletListQuery").widget({
	   			referwid:'RL_T_SYS_CODE_ENABLE',
	   			singleSelect:true,
	   			onlyLeafCheck:false,
	   			width:'180',
	   			view:true,
	   			param:[
	   			       {field:'type',op:'equal',value:'printTempletLinkType'}
	   			],
	   			onSelect:function(data){
	   				var linktype=$(this).widget("getValue")  || "";
	   				linktype=$.trim(linktype);
	   				linktype=linktype.toUpperCase();	
	   				var oldlinktype=$("#tplmanage-likedata-printTempletListQuery-uidiv").attr("linktype") || "0";
	   				if(linktype==oldlinktype){
	   					return true;
	   				}
	   				if(linktype!=""){
		    			$("#tplmanage-likedata-printTempletListQuery-uidiv").empty();
		    			$("#tplmanage-hidden-likedata-printTempletListQuery").val("");
		    			
		    			if(linktype=="DEPTID"){
		    				showListPageLinkDataWidgetForDept();
		    			}else if(linktype=="CUSTOMERID"){
		    				showListPageLinkDataWidgetForCustomer();
		    			}else if(linktype=="SALESAREA"){
		    				showListPageLinkDataWidgetForSalesArea();
		    			}else if(linktype=="EBSHOPWLGS"){
		    				showListPageLinkDataWidgetForEbshopWLGS();
		    			}
			    	}else{
		    			$("#tplmanage-likedata-printTempletListQuery-uidiv").empty();
		    			$("#tplmanage-hidden-likedata-printTempletListQuery").val("");    		
			    	}
	   			},
	   			onClear:function(){
	    			$("#tplmanage-likedata-printTempletListQuery-uidiv").empty();
	    			$("#tplmanage-hidden-likedata-printTempletListQuery").val("");   
	   			}
	   		});
			
	   	});
	   	function printTempletOpenDialog(title, url){
	   		$('<div id="printTemplet-dialog-add-operate-content"></div>').appendTo("#printTemplet-dialog-add-operate");
	   		$('#printTemplet-dialog-add-operate-content').dialog({  
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
			    	$('#printTemplet-dialog-add-operate-content').window("destroy");
			    }
			});
			$('#printTemplet-dialog-add-operate-content').dialog('open');
	   	}
	   	function printTempletOpenDialogForOtherInfo(title,url,options){
	   		$('<div id="printTemplet-dialog-operate-otherinfo-content"></div>').appendTo("#printTemplet-dialog-operate-otherinfo");
	   		
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
				    	$('#printTemplet-dialog-operate-otherinfo-content').window("destroy");
				    }
				};
	   		if(undefined != options){
	   			dialogOpts = jQuery.extend({}, dialogOpts, options);
	   		}
	   		$('#printTemplet-dialog-operate-otherinfo-content').dialog(dialogOpts);
			$('#printTemplet-dialog-operate-otherinfo-content').dialog('open');
	   	}
	   	function printTempletOpenDialogForSubjectInfo(code,title){
	   		if(null==code || ""==code){
	   			return false;
	   		}
	   		if(null==title){
	   			title='打印模板清单代码查看';
	   		}
	   		var url='agprint/tplsubject/showPrintTempletSubjectViewPage.do?code='+code;
	   		printTempletOpenDialogForOtherInfo(title,url);
	   	}
	   	function printTempletOpenDialogForResourceInfo(id,title){
	   		if(null==id || ""==id){
	   			return false;
	   		}
	   		if(null==title){
	   			title='打印模板资源查看';
	   		}
	   		var url='agprint/tplresource/showPrintTempletResourceViewPage.do?id='+id;
	   		printTempletOpenDialogForOtherInfo(title,url);
	   	}
	   	function printTempletOpenDialogForOrderseqInfo(id,title){
	   		if(null==id || ""==id){
	   			return false;
	   		}
	   		if(null==title){
	   			title='打印内容数据排序策略查看';
	   		}
	   		var url='agprint/tplorderseq/showPrintOrderSeqViewPage.do?id='+id;
	   		printTempletOpenDialogForOtherInfo(title,url,{fit:true});
	   	}
	   	function showMoreDeptNameInfo(obj){
		   	if(obj && $(obj).size()>0){
        		$.messager.alert("关联部门",$(obj).attr("title"));	
		   	}
	   	}
	   	function showMoreLinkDataNameInfo(obj){
		   	if(obj && $(obj).size()>0){
        		$.messager.alert("关联数据",$(obj).attr("title"));	
		   	}
	   	}
	   	
	   	//部门 控件
	   	function showListPageLinkDataWidgetForDept(){
	   		$("<input type='text' id='tplmanage-likedata-printTempletListQuery-widget'/>").appendTo("#tplmanage-likedata-printTempletListQuery-uidiv");
	   		$("#tplmanage-likedata-printTempletListQuery-widget").widget({
	   			referwid:'RL_T_BASE_DEPARTMENT_SELLER',
	   			singleSelect:false,
	   			width:'200',
	   			onSelect:function(data){
	   				var val=$("#tplmanage-likedata-printTempletListQuery-widget").widget("getValue");
	   				$("#tplmanage-hidden-likedata-printTempletListQuery").val(val);
	   			},
	   			onClear:function(){
	   				$("#tplmanage-hidden-likedata-printTempletListQuery").val("");
	   			}
	   		});
	   	}
	   	//客户控件
	   	function showListPageLinkDataWidgetForCustomer(){
	   		$("<input type='text' id='tplmanage-likedata-printTempletListQuery-widget'/>").appendTo("#tplmanage-likedata-printTempletListQuery-uidiv");
	   		$("#tplmanage-likedata-printTempletListQuery-widget").widget({
	   			referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
	   			singleSelect:false,
	   			width:'200',
	   			onSelect:function(data){
	   				var val=$("#tplmanage-likedata-printTempletListQuery-widget").widget("getValue");
	   				$("#tplmanage-hidden-likedata-printTempletListQuery").val(val);
	   			},
	   			onClear:function(){
	   				$("#tplmanage-hidden-likedata-printTempletListQuery").val("");
	   			}
	   		});
	   	}
	   	//销售区域
	   	function showListPageLinkDataWidgetForSalesArea(){
	   		$("<input type='text' id='tplmanage-likedata-printTempletListQuery-widget'/>").appendTo("#tplmanage-likedata-printTempletListQuery-uidiv");
	   		$("#tplmanage-likedata-printTempletListQuery-widget").widget({
	   			referwid:'RT_T_BASE_SALES_AREA',
	   			singleSelect:false,
	   			onlyLeafCheck:false,
	   			width:'200',
	   			onSelect:function(data){
	   				var val=$("#tplmanage-likedata-printTempletListQuery-widget").widget("getValue");
	   				$("#tplmanage-hidden-likedata-printTempletListQuery").val(val);
	   			},
	   			onClear:function(){
	   				$("#tplmanage-hidden-likedata-printTempletListQuery").val("");
	   			}
	   		});
	   	}
	   	//电商物流公司
	   	function showListPageLinkDataWidgetForEbshopWLGS(){
	   		$("<input type='text' id='tplmanage-likedata-printTempletListQuery-widget'/>").appendTo("#tplmanage-likedata-printTempletListQuery-uidiv");
	   		$("#tplmanage-likedata-printTempletListQuery-widget").widget({
	   			referwid:'RL_T_SYS_CODE_ENABLE',
	   			singleSelect:true,
	   			onlyLeafCheck:false,
	   			width:'200',
	   			param:[{field:'type',op:'equal',value:'logistics'}],
	   			onSelect:function(data){
	   				var val=$("#tplmanage-likedata-printTempletListQuery-widget").widget("getValue");
	   				$("#tplmanage-hidden-likedata-printTempletListQuery").val(val);
	   			},
	   			onClear:function(){
	   				$("#tplmanage-hidden-likedata-printTempletListQuery").val("");
	   			}
	   		});
	   	}
	   	//添加，修改下拉控件
	   	function showPageLinkTypeWidget(isview,initLinktData){
	   		if(isview==true){
	   			isview=true;
	   		}else{
	   			isview=false;
	   		}
	   		if(initLinktData==null){
	   			initLinkData='';
	   		}

		    //关联下拉控件选择时
    		$("#printTemplet-form-add-linktype-widget").widget({
	   			referwid:'RL_T_SYS_CODE_ENABLE',
	   			singleSelect:true,
	   			onlyLeafCheck:false,
	   			sortName:'seq',
	   			sortOrder:'asc',
	   			width:'200',
	   			view:isview,
	   			param:[
	   			       {field:'type',op:'equal',value:'printTempletLinkType'}
	   			],
	   			onSelect:function(data){
	   				showPageLinkDataWidget(initLinktData);
	   			},
	   			onClear:function(){
	   				//隐藏关联数据tr
		    		$("#printTemplet-form-add-linkdata-tr").hide();	
	   				//隐藏关联数据控件div
	    			$("#printTemplet-form-add-linkdata-widget-myuidiv").empty();
	   				//关联数据值
	   				$("#printTemplet-form-add-linkdata").val("");
	   				//关联数据名称
	   				$("#printTemplet-form-add-linkdataname").val("");
	   				//关联tr里属性
	   				$("#printTemplet-form-add-linkdata-tr").attr("linktype","0");
		    		$("#printTemplet-form-add-linkdata-tr-name").html("");
	   			}
	   		});
	   	};
	   	
	   	function showPageLinkDataWidget(initValue){

			if(initValue==null){
				initValue='';
			}
	   		var linktype=$("#printTemplet-form-add-linktype-widget").widget("getValue") || "";
			linktype=$.trim(linktype);
			if(linktype==""){
			    linktype="0";
			}
			linktype=linktype.toUpperCase();	
			var oldlinktype=$("#printTemplet-form-add-linkdata-tr").attr("linktype") || "";
			if(linktype==oldlinktype){
				return true;
			}
			var linktypename=$("#printTemplet-form-add-linktype-widget").widget("getText") || "";
			$("#printTemplet-form-add-linkdata-tr").attr("linktype",linktype);
			$("#printTemplet-form-add-linkdata-widget-myuidiv").empty();
			$("#printTemplet-form-add-linkdata").val("");
			$("#printTemplet-form-add-linkdataname").val("");
	    	if(linktype!="0" ){
	    		$("#printTemplet-form-add-linkdata-tr-name").html(linktypename);
	    		$("#printTemplet-form-add-linkdata-tr").show(); 
	    		if(linktype=="CUSTOMERID"){
    				showPageLinkDataWidgetForCustomer(initValue);
    			}else if(linktype=="DEPTID"){
    				showPageLinkDataWidgetForDept(initValue);
    			}else if(linktype=="SALESAREA"){
    				showPageLinkDataWidgetForSalesArea(initValue);
    			}else if(linktype=="EBSHOPWLGS"){
    				showPageLinkDataWidgetForEbshopWLGS(initValue);
    			}
	    	}else{
	    		$("#printTemplet-form-add-linkdata-tr").hide();
	    	}
	   	}
	   	function resourceADMOperDialog(title, url,onLoadFunc){
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
			    	if(onLoadFunc!=null && typeof(onLoadFunc) == "function" ){
			    		onLoadFunc();
			    	}
	   			},  
			    onClose:function(){
			    	$('#tplresource-dialog-printTempletResourceOper-content').window("destroy");
			    }
			});
			$('#tplresource-dialog-printTempletResourceOper-content').dialog('open');
	   	}

	   	function printOrderSeqOpenDialog(title, url,onLoadFunc){
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
			    	if(onLoadFunc!=null && typeof(onLoadFunc) == "function" ){
			    		onLoadFunc();
			    	}
	   			},  
			    onClose:function(){
			    	$('#tplorderseq-dialog-printOrderSeqOper-content').window("destroy");
			    }
			});
			$('#tplorderseq-dialog-printOrderSeqOper-content').dialog('open');
	   	}

	   	
	   	function showPrintOrderSeqCreateDialog(tablesavetoid,seqsavetoid,initdata){
	   		tablesavetoid=tablesavetoid || "";
	   		seqsavetoid=seqsavetoid || "";
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
			    	seqOrderCreatePageOnOpenInit(tablesavetoid,seqsavetoid,initdata);
	   			},
			    onClose:function(){
			    	$('#tplorderseq-dialog-printOrderSeqOper-orderCreate-content').window("destroy");
			    }
			});
			$('#tplorderseq-dialog-printOrderSeqOper-orderCreate-content').dialog('open'); 
	   	}

        function showPrintTempletHelpDialog(type) {
            if(type==null || type==""){
                return false;
            }
            if("countperpage"==type){
                $.messager.alert("提醒",$("#printTemplet-help-countperpage").html());
            }else if("companytitle"==type){
                $.messager.alert("提醒",$("#printTemplet-help-companytitle").html());
            }else if("lodophtmlmodel"==type){
                $.messager.alert("提醒",$("#printTemplet-help-lodophtmlmodel").html());
            }
        }
   	 </script>
  </body>
</html>
