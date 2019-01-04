<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>模板清单列表</title>
    <%@include file="/include.jsp" %> 
    <script type="text/javascript" src="js/datagrid-detailview.js"></script>
  </head>
  
  <body>  
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north',border:false">
   			<div class="buttonBG" id="tplsubject-button-printTempletSubjectList"></div>
  	 	</div>
        <div data-options="region:'center'">
  			<table id="tplsubject-table-printTempletSubjectList"></table>
		   	<div id="tplsubject-query-printTempletSubjectList" style="padding:0px;height:auto">
				<form action="" id="tplsubject-form-printTempletSubjectList" method="post" style="padding-top: 2px;">
					<table class="querytable">
						<tr>
							<td>模板代码:</td>
							<td>
								<div id="tplsubject-form-tpls-codewidget-mydiv"></div>
								<input type="hidden" id="tplsubject-form-tpls-hidden-code" name="code"/>
							</td>
							<td>分类名称:</td>
							<td><input type="text" name="name" style="width:100px" /></td>
							<td>状态:</td>
							<td><select name="state" style="width:100px;">
				   				<option></option>
				   				<option value="1" selected="selected">启用</option>
				   				<option value="0">禁用</option>
				   			</select></td>
				   			<td>
				   				<a href="javaScript:void(0);" id="tplsubject-query-queryPrintTempletSubjectList" class="button-qr">查询</a>
		    					<a href="javaScript:void(0);" id="tplsubject-query-reloadPrintTempletSubjectList" class="button-qr">重置</a>
				   			</td>
						</tr>
					</table>
		  		</form>
		   </div>
   		</div>
   </div>
   <div style="display:none">
		<div id="tplsubject-dialog-printTempletSubjectOper" ></div>
	   	<div id="printTemplet-dialog-add-operate"></div>
	   	<div id="printTemplet-dialog-operate-otherinfo"></div>
   		<div id="tplorderseq-dialog-printOrderSeqOper" ></div>
   		<div id="tplorderseq-dialog-printOrderSeqOper-orderCreate"></div>
   		<div id="tplresource-dialog-printTempletResourceOper" ></div>
	   <div id="tplpapersize-dialog-printPaperSizeOper" ></div>
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
    		$.extend($.fn.validatebox.defaults.rules, {
				validPrintTemletSubjectCode:{
			    	validator:function(value){
			    		var reg=/^([a-zA-Z0-9_]|-)+$/;
			    		if(!reg.test(value)){
			    			$.fn.validatebox.defaults.rules.validPrintTemletSubjectCode.message='代码名规则：英文字母、数字、中划线  - 或下划线  _'; 
			    		}
			    		if(value.length>0){
			    			var data=templetSubject_getAjaxContent({code: value},'agprint/tplsubject/isUsedPrintTempletSubjectCode.do');
			    			var json = $.parseJSON(data);
		    				if(json.flag == true){
		    					$.fn.validatebox.defaults.rules.validPrintTemletSubjectCode.message = '代码名已经使用，请重新填写';
			    				return false;
			    			}else{
		    					return true;
			    			}
			    		}
			    		return false;
			    	},
			    	message:'代码名规则：英文字母、数字、中划线  - 或下划线  _ '
			    }
			});
   			
   		//根据初始的列与用户保存的列生成以及字段权限生成新的列
   	    	var templetSubjectListColJson=$("#tplsubject-table-printTempletSubjectList").createGridColumnLoad({
   		     	name:'print_templet_subject',
   		     	frozenCol:[[
   					{field:'idok',checkbox:true,isShow:true}
   		     	]],
   		     	commonCol:[[
   		    		{field:'code',title:'代码',width:150},
	  				{field:'name',title:'代码名称',width:180},
	  				{field:'seq',title:'排序',width:80,sortable:true},
					{field:'uselinktype',title:'使用模板方式',width:60,isShow:true,
						formatter:function(val){
							if(val=='0'){
								return '系统默认';
							}else if(val=="1"){
								return "使用关联";
							}else if(val=="2"){
								return "手动";
							}
						}
					},
	  				{field:'linktypeseqname',title:'关联优先级',width:120,isShow:true},
	  				{field:'state',title:'状态',width:100,
	  					formatter:function(val){
	  						if(val=='1'){
	  							return '启用';
	  						}else{
	  							return '禁用';
	  						}
	  					}
	  				},
	  				{field:'remark',title:'备注',width:100}
   				]]
   		     });
   			
   			$("#tplsubject-table-printTempletSubjectList").datagrid({
     			authority:templetSubjectListColJson,
	  	 		frozenColumns:templetSubjectListColJson.frozen,
				columns:templetSubjectListColJson.common,
				fit:true,
	            striped: true,
	            collapsible:true,
	   			method:'post',
	   			title:'',
	   			rownumbers:true,
	  			pagination:true,
	  			idField:'code',
	  			singleSelect:false,
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		fitColumns:true,
	  			toolbar:'#tplsubject-query-printTempletSubjectList',
	  			url:'agprint/tplsubject/showPrintTempletSubjectPageListData.do',
	  			view: detailview,
	            detailFormatter:function(index,row){ 
	            	return showPrintTempletDatagridMenu(index,row);
	            },  
	            onExpandRow: function(index,row){ 
	            	showPrintTempletDataGridList(index,row);
                    showTplsListPageLinkTypeWidget(index);
                    printTmpletDataGridQueryClick(index);
                    printTempletDataGridQueryReloadClick(index);
	            },
	  			onDblClickRow:function(index, dataRow){
	  				subjectADMOperDialog("模板清单【查看】", 'agprint/tplsubject/showPrintTempletSubjectViewPage.do?code='+dataRow.code);
	  			}
	   		});
   			
   			//回车事件
			controlQueryAndResetByKey("tplsubject-query-queryPrintTempletSubjectList","tplsubject-query-reloadPrintTempletSubjectList");
   			
   			//查询
  			$("#tplsubject-query-queryPrintTempletSubjectList").click(function(){
  				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#tplsubject-form-printTempletSubjectList").serializeJSON();
	       		$("#tplsubject-table-printTempletSubjectList").datagrid("load",queryJSON);
  			});
  			//重置
  			$('#tplsubject-query-reloadPrintTempletSubjectList').click(function(){
  				try{
  					$("#tplsubject-form-tpls-codewidget").widget('clear');
  				}catch(e){
  					
  				}
  				$("#tplsubject-form-printTempletSubjectList")[0].reset();
  				var queryJSON = $("#tplsubject-form-printTempletSubjectList").serializeJSON();
	       		$("#tplsubject-table-printTempletSubjectList").datagrid("load",queryJSON);
  			});
  			
  			
  			$("#tplsubject-button-printTempletSubjectList").buttonWidget({
     			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{}
	 			],
				buttons:[
					<security:authorize url="/agprint/tplsubject/printTempletSubjectFoldAddBtn.do">
					{
						id:'button-id-add',
						name:'新增 ',
						iconCls:'button-add',
						handler:function(){
							subjectADMOperDialog('模板清单【新增】', 'agprint/tplsubject/showPrintTempletSubjectAddPage.do');
						}
					},
					</security:authorize>
					<security:authorize url="/agprint/tplsubject/printTempletSubjectFoldEditBtn.do">
					{
						id:'button-id-edit',
						name:'修改 ',
						iconCls:'button-edit',
						handler:function(){
							var dataRow=$("#tplsubject-table-printTempletSubjectList").datagrid('getSelected');
							if(dataRow==null || dataRow.code==""){
								$.messager.alert("提醒","请选择相应的打印模板清单!");
								return false;
							}
							subjectADMOperDialog("模板清单【修改】", 'agprint/tplsubject/showPrintTempletSubjectEditPage.do?code='+dataRow.code);
						}
					},
					</security:authorize>
					<security:authorize url="/agprint/tplsubject/printTempletSubjectFoldDelBtn.do">
					{
						id:'button-id-delete',
						name:'删除',
						iconCls:'button-delete',
						handler:function(){
							var rows =  $("#tplsubject-table-printTempletSubjectList").datagrid('getChecked');
							if(rows==null || rows.length==0){
								$.messager.alert("提醒","请选择相应的打印模板清单!");
								return false;
							}
							var idarrs=new Array();
							var errorIdarr=new Array();
							if(null !=rows && rows.length>0){
					    		for(var i=0;i<rows.length;i++){
						    		if(rows[i].code && rows[i].code!=""){
							    		idarrs.push(rows[i].code);
						    		}
						    		if(rows[i].state!=null){
							    		if(rows[i].state =='1' ){
							    			errorIdarr.push(rows[i].code);
							    		}
									}
					    		}
							}
							if(errorIdarr.length>0){
								$.messager.alert("提醒","启用的打印模板清单不能被删除，下列选中为已经启用："+errorIdarr.join(","));
								return false;
							}
							$.messager.confirm("提醒","是否确认删除打印模板清单 ?",function(r){
								if(r){
				  				loading();
				  				$.ajax({
							        type: 'post',
							        cache: false,
							        url: 'agprint/tplsubject/deletePrintTempletSubjectMore.do',
							        data: {codearrs:idarrs.join(",")},
									dataType:'json',
							        success:function(json){
							        	loaded();
							        	if(json.flag==true){
						  					$.messager.alert("提醒", "删除成功数："+ json.isuccess +"<br />删除失败数："+ json.ifailure );							  					
											$("#tplsubject-table-printTempletSubjectList").datagrid('reload');
											$("#tplsubject-table-printTempletSubjectList").datagrid('clearSelections');
											showListPagePrintTempletSubjectWidget();
						  		        }
						  		        else{
						  		        	$.messager.alert("提醒","删除失败");
						  		        }
							        }
							    });
						    }
							});
						}
					},
					</security:authorize>				
					<security:authorize url="/agprint/tplsubject/printTempletSubjectFoldEnableBtn.do">
					{
						id:'button-id-enable',
						name:'启用',
						iconCls:'button-open',
						handler: function(){
							var dataRow =  $("#tplsubject-table-printTempletSubjectList").datagrid('getSelected');
							if(dataRow==null){
			  					$.messager.alert("提醒","请选择启用的打印模板清单!");
			  					return false;
			  				}
			  				if(dataRow.code == ""){
			  					$.messager.alert("提醒","抱歉，未能找到要启用的打印模板清单!");
			  					return false;
			  				}
			  				if(dataRow.state == "1"){
			  					$.messager.alert("提醒","启用状态不能启用!");
			  					return false;
			  				}
			  				$.messager.confirm("提醒","是否启用该模板清单？",function(r){
								if(r){
									loading("启用中..");
					  				$.ajax({
					  					url:'agprint/tplsubject/enablePrintTempletSubject.do?code='+dataRow.code,
					  					type:'post',
					  					dataType:'json',
					  					success:function(json){
							            	loaded();
					  						if(json.flag==true){
					  							$.messager.alert("提醒","打印模板清单启用成功!");
					  							$("#tplsubject-table-printTempletSubjectList").datagrid('reload');
					  						}else{
					  							$.messager.alert("提醒","打印模板清单启用失败 !");
					  						}
					  					},
					  					error:function(){
							            	loaded();					  						
					  					}
					  				});
								}
			  				});
						}
					},
					</security:authorize>				
					<security:authorize url="/agprint/tplsubject/printTempletSubjectFoldDisableBtn.do">
					{
						id:'button-id-disable',
						name:'禁用',
						iconCls:'button-close',
						handler: function(){
							var dataRow =  $("#tplsubject-table-printTempletSubjectList").datagrid('getSelected');
							if(dataRow==null){
			  					$.messager.alert("提醒","请选择要禁用的打印模板清单!");
			  					return false;
			  				}
			  				if(dataRow.code == ""){
			  					$.messager.alert("提醒","抱歉，未能找到要禁用的打印模板清单!");
			  					return false;
			  				}
			  				if(dataRow.state == "0"){
			  					$.messager.alert("提醒","禁用状态不能禁用!");
			  					return false;
			  				}
			  				$.messager.confirm("提醒","是否禁用该模板清单？",function(r){
								if(r){
									loading("启用中..");
					  				$.ajax({
					  					url:'agprint/tplsubject/disablePrintTempletSubject.do?code='+dataRow.code,
					  					type:'post',
					  					dataType:'json',
					  					success:function(json){
							            	loaded();	
					  						if(json.flag==true){
					  							$.messager.alert("提醒","打印模板清单禁用成功!");
					  							$("#tplsubject-table-printTempletSubjectList").datagrid('reload');
					  						}else{
					  							$.messager.alert("提醒","打印模板清单禁用失败 !");
					  						}
					  					},
					  					error:function(){
							            	loaded();					  						
					  					}
					  				});
								}
			  				});
						}
					},
					</security:authorize>
					{}		
				],
	 			model:'bill',
				type:'list',
				datagrid:'tplsubject-table-printTempletSubjectList',
				tname:'print_templet_subject',
				id:''
     		});
  			$(".printTempletDataGridMenuAddButton").live("click",function(){
  				var code=$(this).attr("pcode") || "";
  				if($.trim(code)==""){
					$.messager.alert("提醒","未能找到打印模板清单清单!");
					return;
  				}
  				code=$.trim(code);
  				var index=$(this).attr("tindex");
  				var queryid="";
  				if(index!=null && $.trim(index)!=""){
  					queryid="tplsubject-printTempletList-queryBtn"+$.trim(index);
  				}  				
  				var onLoadFunc=function(){
  					$("#printTemplet-form-clickbtn-ReloadtableAfterSave").val(queryid);
  				};
  				printTempletOpenDialog('打印模板配置【新增】',
  						'agprint/tplmanage/showPrintTempletAddPage.do?readonlythecode=true&&thecode='+code,
  						onLoadFunc);
  			});
  			$(".printTempletDataGridMenuEditButton").live("click",function(){
  				var tableid=$(this).attr("tableid")||"";
  				if(tableid=="" || $("#"+tableid).size()==0){
					$.messager.alert("提醒","请选择相应的模板信息!");
					return false;  					
  				}
  				var dataRow=$("#"+tableid).datagrid('getSelected');
				if(dataRow==null){
					$.messager.alert("提醒","请选择相应的模板信息!");
					return false;
				}
  				var index=$(this).attr("tindex");
  				var queryid="";
  				if(index!=null && $.trim(index)!=""){
  					queryid="tplsubject-printTempletList-queryBtn"+$.trim(index);
  				}  				
  				var onLoadFunc=function(){
  					$("#printTemplet-form-clickbtn-ReloadtableAfterSave").val(queryid);
  				};
				printTempletOpenDialog("打印模板配置【更新】", 
						'agprint/tplmanage/showPrintTempletEditPage.do?id='+dataRow.id,
						onLoadFunc);
  			});
  			$(".printTempletDataGridMenuDeleteButton").live("click",function(){
  				var tableid=$(this).attr("tableid")||"";
  				if(tableid=="" || $("#"+tableid).size()==0){
					$.messager.alert("提醒","请选择相应的模板配置信息!");
					return false;  					
  				}
  				var dataRow=$("#"+tableid).datagrid('getSelected');
				if(dataRow==null){
					$.messager.alert("提醒","请选择相应的模板配置信息!");
					return false;
				}
  				var index=$(this).attr("tindex") || "";
  				index=$.trim(index);
				$.messager.confirm("提醒","是否删除该模板配置信息？",function(r){
					if(r){
						loading("删除中..");
						$.ajax({   
				            url :'agprint/tplmanage/deletePrintTemplet.do?id='+ dataRow.id,
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	loaded();
					            if(json.flag){
				            		$.messager.alert("提醒","模板配置删除成功");				            		
    	        					$("#tplsubject-printTempletList-queryBtn"+index).trigger("click");     		
					            }else{
    	        	            	if(json.msg){
    	        						$.messager.alert("提醒","模板配置删除失败!"+json.msg);
    	        	            	}else{
    	        						$.messager.alert("提醒","模板配置删除失败!");
    	        	            	}
					            }
							}
				        });
					}
				});
  			});
  			$(".printTempletDataGridMenuEnableButton").live("click",function(){
  				var tableid=$(this).attr("tableid")||"";
  				if(tableid=="" || $("#"+tableid).size()==0){
					$.messager.alert("提醒","请选择相应的模板配置信息!");
					return false;  					
  				}
  				var dataRow=$("#"+tableid).datagrid('getSelected');
				if(dataRow==null){
					$.messager.alert("提醒","请选择相应的模板配置信息!");
					return false;
				}
				if(dataRow.state=='1'){
					$.messager.alert("提醒","抱歉，启用的模板配置信息不能被启用!");
					return false;
				}
				var index=$(this).attr("tindex") || "";
  				index=$.trim(index);
				$.messager.confirm("提醒","是否启用该模板配置信息？",function(r){
					if(r){
						loading("启用中..");
						$.ajax({   
				            url :'agprint/tplmanage/enablePrintTemplet.do?id='+ dataRow.id,
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	loaded();
					            if(json.flag){
				            		$.messager.alert("提醒","启用模板配置信息成功");
				            		$("#tplsubject-printTempletList-queryBtn"+index).trigger("click");            		
					            }else{
    	        	            	if(json.msg){
    	        						$.messager.alert("提醒","启用模板配置信息失败!"+json.msg);
    	        	            	}else{
    	        						$.messager.alert("提醒","启用模板配置信息失败!");
    	        	            	}
					            }
							}
				        });
					}
			  });
  			});
  			$(".printTempletDataGridMenuDisableButton").live("click",function(){
  				var tableid=$(this).attr("tableid")||"";
  				if(tableid=="" || $("#"+tableid).size()==0){
					$.messager.alert("提醒","请选择相应的模板配置信息!");
					return false;  					
  				}
  				var dataRow=$("#"+tableid).datagrid('getSelected');
				if(dataRow==null){
					$.messager.alert("提醒","请选择相应的模板配置信息!");
					return false;
				}
				if(dataRow.state=='0'){
					$.messager.alert("提醒","抱歉，已经禁用的模板配置信息不能被禁用!");
					return false;
				}
				var index=$(this).attr("tindex") || "";
  				index=$.trim(index);
				$.messager.confirm("提醒","是否禁用该模板配置信息？",function(r){
					if(r){
						loading("禁用中..");
						$.ajax({   
				            url :'agprint/tplmanage/disablePrintTemplet.do?id='+ dataRow.id,
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	loaded();
					            if(json.flag){
				            		$.messager.alert("提醒","禁用模板配置信息成功");
				            		$("#tplsubject-printTempletList-queryBtn"+index).trigger("click");           		
					            }else{
    	        	            	if(json.msg){
    	        						$.messager.alert("提醒","禁用模板配置信息失败!"+json.msg);
    	        	            	}else{
    	        						$.messager.alert("提醒","禁用模板配置信息失败!");
    	        	            	}
					            }
							}
				        });
					}
			  });
  			});



            $(".printTempletDataGridMenuCopyButton").live("click",function(){
                var tableid=$(this).attr("tableid")||"";
                if(tableid=="" || $("#"+tableid).size()==0){
                    $.messager.alert("提醒","请选择相应的模板配置信息!");
                    return false;
                }
                var dataRow=$("#"+tableid).datagrid('getSelected');
                if(dataRow==null){
                    $.messager.alert("提醒","请选择相应的模板配置信息!");
                    return false;
                }
                var index=$(this).attr("tindex") || "";
                index=$.trim(index);
                $.messager.confirm("提醒","是否复制该模板配置信息<br/>复制成功的模板状态为“禁用”？",function(r){
                    if(r){
                        loading("复制中..");
                        $.ajax({
                            url :'agprint/tplmanage/copyPrintTemplet.do',
                            type:'post',
                            dataType:'json',
                            data:{id:dataRow.id},
                            success:function(json){
                                loaded();
                                if(json.flag){
                                    $.messager.alert("提醒","复制模板配置信息成功");
                                    $("#tplsubject-printTempletList-queryBtn"+index).trigger("click");
                                }else{
                                    var msg="禁用模板配置信息失败!";
                                    if(json.msg){
                                        $.messager.alert("提醒",msg+json.msg);
                                    }else{
                                        $.messager.alert("提醒",msg);
                                    }
                                }
                            }
                        });
                    }
                });
            });

  			showListPagePrintTempletSubjectWidget();
   		});

   		function showListPagePrintTempletSubjectWidget(){

			try{
				$("#tplsubject-form-tpls-codewidget").widget('clear');
			}catch(e){
				
			}
			$("#tplsubject-form-tpls-codewidget-mydiv").empty();

    		$("<input type='text' id='tplsubject-form-tpls-codewidget' style='width:180px;'/>").appendTo("#tplsubject-form-tpls-codewidget-mydiv");
    		$("#tplsubject-form-tpls-codewidget").widget({
	   			referwid:'RL_PRINT_TEMPLET_SUBJECT',
	   			singleSelect:true,
	   			width:'180',
	   			view:true,
	   			onSelect:function(data){
	   				var val=$("#tplsubject-form-tpls-codewidget").widget('getValue') || "";
	   				$("#tplsubject-form-tpls-hidden-code").val(val)
	   			},
	   			onClear:function(){
        			$("#tplsubject-form-tpls-hidden-code").val("");     			
	   			}
	   		});
    	}
	    function subjectADMOperDialog(title, url){
	    	$('<div id="tplsubject-dialog-printTempletSubjectOper-content"></div>').appendTo("#tplsubject-dialog-printTempletSubjectOper");
	   		$('#tplsubject-dialog-printTempletSubjectOper-content').dialog({  
			    title: title,  
			    fit:true,
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
			    	$('#tplsubject-dialog-printTempletSubjectOper-content').window("destroy");
			    }
			});
			$('#tplsubject-dialog-printTempletSubjectOper-content').dialog('open');
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
	    function showPrintTempletDataGridList(index,dataRow){
	    	var initTplQueryJSON = $("#tplsubject-form-printTempletListQuery"+index).serializeJSON();
	    	if(initTplQueryJSON==null){
	    		initTplQueryJSON={};
	    	}
	    	if((initTplQueryJSON.thecode==null || initTplQueryJSON.thecode=="") && dataRow.code!=""){
	    		initTplQueryJSON.thecode=dataRow.code;
	    	} 
	    	$("#tplsubject-table-printTemplet-"+index).datagrid({
                columns:[[
                          {field:'idok',checkbox:true,isShow:true},
                          {field:'id',title:'序号',width:50,sortable:true},
                          {field:'seq',title:'排序',width:35,sortable:true},
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
                                      return " <a href=\"agprint/tplmanage/downloadTempletSourceFile.do?type=2&id="+rowData.id+"\" target=\"_blank\">"+value+"</a>";
                                  }else if(rowData.templetfileid!=null && rowData.templetfileid!=""){
                                      return " <a href=\"common/download.do?id="+rowData.templetfileid+"\" target=\"_blank\">"+value+"</a>";
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
						{field:'papersizename',title:'纸张大小',width:120,
							formatter:function(value,rowData,rowIndex){
								if(null!=rowData.papersizeid && ""!=rowData.papersizeid){
									return "<a href=\"javascript:void(0);\" onclick=\"javascript:printTempletOpenDialogForPapaerSizeInfo('"+rowData.papersizeid+"');\">"+value+"</a>";
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
                              	if(value!="0") {
                                    return getSysCodeName('printTempletLinkType', value);
                                }
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
          		],
	  	 		method:'post',
	  	 		//title:'模板管理',
	  	 		showFooter: false,
                rownumbers:true,
                pagination:true,
		 		idField:'id',
	  	 		singleSelect:true,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		queryParams:initTplQueryJSON,
				pageSize:50,
				toolbar:'#tplsubject-table-printTemplet-oper-'+index,
                pageList:[50,100,200,300,500],
				url: 'agprint/tplmanage/showPrintTempetPageListData.do',
	  			onDblClickRow:function(rowIndex, dataRow){
	  				//printTempletOpenDialog("打印模板【查看】", 'agprint/tplmanage/showPrintTempletViewPage.do?id='+dataRow.id);
	  				var queryid="";
	  				if(index!=null && $.trim(index)!=""){
	  					queryid="tplsubject-printTempletList-queryBtn"+$.trim(index);
	  				}  				
	  				var onLoadFunc=function(){
	  					$("#printTemplet-form-clickbtn-ReloadtableAfterSave").val(queryid);
	  				};
					printTempletOpenDialog("打印模板配置【查看】", 
							'agprint/tplmanage/showPrintTempletViewPage.do?id='+dataRow.id,
							onLoadFunc);
		 		},  
                onResize:function(){  
                    $('#tplsubject-table-printTempletSubjectList').datagrid('fixDetailRowHeight',index); 
                },  
                onLoadSuccess:function(){
                    setTimeout(function(){  
                        $('#tplsubject-table-printTempletSubjectList').datagrid('fixDetailRowHeight',index); 
                    },50);  
                }
			}).datagrid("columnMoving");
            $.parser.parse("#tplsubject-table-printTemplet-oper-"+index);
            $('#tplsubject-table-printTempletSubjectList').datagrid('fixDetailRowHeight',index);
	    }
	    function printTmpletDataGridQueryClick(index){
	    	$("#tplsubject-printTempletList-queryBtn"+index).click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#tplsubject-form-printTempletListQuery"+index).serializeJSON();
				
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#tplsubject-table-printTemplet-"+index).datagrid('load',queryJSON);	
  			});
	    }
	    function printTempletDataGridQueryReloadClick(index){	    	
			$("#tplsubject-printTempletList-reloadBtn"+index).live("click",function(){
				$("#tplsubject-liketype-printTempletListQuery"+index).widget('clear');
				$("#tplsubject-form-printTempletListQuery"+index).form('reset');
                var queryJSON = $("#tplsubject-form-printTempletListQuery"+index).serializeJSON();
				$("#tplsubject-table-printTemplet-"+index).datagrid('load',queryJSON);
  			});
	    }
	    function showPrintTempletDatagridMenu(index,dataRow){
	    	var htmlsb=new Array();
        	htmlsb.push('<div style="padding:2px">');
        	htmlsb.push('<table id="tplsubject-table-printTemplet-'+index+'" ></table>');
        	htmlsb.push('<div id="tplsubject-table-printTemplet-oper-'+index+'"> ');
        	htmlsb.push('<div>');
        	<security:authorize url="/agprint/tplsubject/printTempletAddBtn.do">
        	htmlsb.push('<a href="javaScript:void(0);" class="easyui-linkbutton printTempletDataGridMenuAddButton" iconCls="button-add" plain="true" ');
        	htmlsb.push(" tindex=\""+index+"\"");
        	htmlsb.push(' pcode="'+dataRow.code+'" ');
        	htmlsb.push(' pname="'+dataRow.name+'" ');
        	htmlsb.push(' tableid="tplsubject-table-printTemplet-'+index+'" ');
        	htmlsb.push('>新增</a>');
        	</security:authorize>
			<security:authorize url="/agprint/tplsubject/printTempletEditBtn.do">
        	htmlsb.push('<a href="javaScript:void(0);" class="easyui-linkbutton printTempletDataGridMenuEditButton" iconCls="button-edit" plain="true" ');
        	htmlsb.push(" tindex=\""+index+"\"");
        	htmlsb.push(' pcode="'+dataRow.code+'" ');
        	htmlsb.push(' pname="'+dataRow.name+'" ');
        	htmlsb.push(' tableid="tplsubject-table-printTemplet-'+index+'"');
        	htmlsb.push(' >修改</a>');
        	</security:authorize>
			<security:authorize url="/agprint/tplsubject/printTempletDeleteBtn.do">
        	htmlsb.push('<a href="javaScript:void(0);" class="easyui-linkbutton printTempletDataGridMenuDeleteButton" iconCls="button-delete" plain="true" ');
        	htmlsb.push(" tindex=\""+index+"\"");
        	htmlsb.push(' pcode="'+dataRow.code+'" ');
        	htmlsb.push(' pname="'+dataRow.name+'" ');
        	htmlsb.push(' tableid="tplsubject-table-printTemplet-'+index+'"');
        	htmlsb.push('>删除</a>');
			</security:authorize>
			<security:authorize url="/agprint/tplsubject/printTempletEnableBtn.do">
        	htmlsb.push('<a href="javaScript:void(0);" class="easyui-linkbutton printTempletDataGridMenuEnableButton" iconCls="button-open" plain="true" ');
        	htmlsb.push(" tindex=\""+index+"\"");
        	htmlsb.push(' pcode="'+dataRow.code+'" ');
        	htmlsb.push(' pname="'+dataRow.name+'" ');
        	htmlsb.push(' tableid="tplsubject-table-printTemplet-'+index+'"');
        	htmlsb.push('>启用</a>');
			</security:authorize>
			<security:authorize url="/agprint/tplsubject/printTempletDisableBtn.do">
        	htmlsb.push('<a href="javaScript:void(0);" class="easyui-linkbutton printTempletDataGridMenuDisableButton" iconCls="button-close" plain="true" ');
        	htmlsb.push(" tindex=\""+index+"\"");
        	htmlsb.push(' pcode="'+dataRow.code+'" ');
        	htmlsb.push(' pname="'+dataRow.name+'" ');
        	htmlsb.push(' tableid="tplsubject-table-printTemplet-'+index+'"');
        	htmlsb.push('>禁用</a>');
			</security:authorize>
            <security:authorize url="/agprint/tplsubject/printTempletCopyBtn.do">
            htmlsb.push('<a href="javaScript:void(0);" class="easyui-linkbutton printTempletDataGridMenuCopyButton" iconCls="button-copy" plain="true" ');
            htmlsb.push(" tindex=\""+index+"\"");
            htmlsb.push(' pcode="'+dataRow.code+'" ');
            htmlsb.push(' pname="'+dataRow.name+'" ');
            htmlsb.push(' tableid="tplsubject-table-printTemplet-'+index+'"');
            htmlsb.push('>复制</a>');
            </security:authorize>

            htmlsb.push('</div>');
            
            htmlsb.push("<div>");
            htmlsb.push("<form action=\"\" id=\"tplsubject-form-printTempletListQuery"+index+"\" method=\"post\">");
            htmlsb.push("<table class=\"querytable\">");
            htmlsb.push("<tr>");
            htmlsb.push("<td style=\"padding-left: 10px;width:80px;\">描述名:&nbsp;</td>");
            htmlsb.push("<td><input name=\"name\" type=\"text\" style=\"width: 180px;\"/></td>");
            htmlsb.push("<td colspan=\"2\">");
            htmlsb.push("状态:");
            htmlsb.push("<select name=\"state\" style=\"width:60px;\">");
            htmlsb.push("<option value=\"\"></option>");
            htmlsb.push("<option value=\"1\" selected=\"selected\">启用</option>");
            htmlsb.push("<option value=\"0\">禁用</option>");
            htmlsb.push("</select>");
            htmlsb.push("预置:");
            htmlsb.push("<select name=\"issystem\" style=\"width:60px;\">");
            htmlsb.push("<option value=\"\"></option>");
            htmlsb.push("<option value=\"1\">是</option>");
            htmlsb.push("<option value=\"0\">否</option>");
            htmlsb.push("</select>");
            htmlsb.push("</td>");
            htmlsb.push("</tr>");
            htmlsb.push("<tr>");
            htmlsb.push("<td style=\"padding-left: 10px;\">是否关联:&nbsp;</td>");
            htmlsb.push("<td>");
            htmlsb.push("<input id=\"tplsubject-liketype-printTempletListQuery"+index+"\"");
           	htmlsb.push("name=\"linktype\"  style=\"width:180px;\" />");
            htmlsb.push("</td>");
            htmlsb.push("<td colspan=\"2\">");
            htmlsb.push("<div id=\"tplsubject-likedata-printTempletListQuery-uidiv"+index+"\" linktype=\"0\"></div>");
            htmlsb.push("<input type=\"hidden\" name=\"linkdataarr\" ");
            htmlsb.push("id=\"tplsubject-hidden-likedata-printTempletListQuery"+index+"\" />");
            htmlsb.push("</td>");
            htmlsb.push("<td colspan=\"2\">");
            htmlsb.push("&nbsp;");
            htmlsb.push("<a href=\"javaScript:void(0);\" id=\"tplsubject-printTempletList-queryBtn"+index+"\" class=\"button-qr\"");
            htmlsb.push(" tindex=\""+index+"\"");
            htmlsb.push(" >查询</a>&nbsp;");
            htmlsb.push("<a href=\"javaScript:void(0);\" id=\"tplsubject-printTempletList-reloadBtn"+index+"\" class=\"button-qr\"");
            htmlsb.push(" tindex=\""+index+"\"");
            htmlsb.push(" >重置</a>");
            htmlsb.push("</td>");
            htmlsb.push("</tr>");
            htmlsb.push("</table>");
            htmlsb.push("<input type=\"hidden\" name=\"thecode\" ");
            htmlsb.push(" value=\""+dataRow.code+"\" />");
            htmlsb.push("</form>");
            htmlsb.push("</div>");
            
            htmlsb.push('</div>');
            htmlsb.push('</div>');
            return htmlsb.join('');
	    }
	    
	    function showTplsListPageLinkTypeWidget(index){
	    	//关联下拉控件选择时
    		$("#tplsubject-liketype-printTempletListQuery"+index).widget({
	   			referwid:'RL_T_SYS_CODE_ENABLE',
	   			singleSelect:true,
	   			onlyLeafCheck:false,
	   			width:'180',
	   			view:true,
	   			param:[
	   			       {field:'type',op:'equal',value:'printTempletLinkType'}
	   			],
	   			onSelect:function(data){
	   				var linktype=$(this).widget("getValue")  || "0";
	   				linktype=$.trim(linktype);
	   				linktype=linktype.toUpperCase();
	   				if(linktype==""){
	   				    linktype="0";
					}
	   				var oldlinktype=$("#tplsubject-likedata-printTempletListQuery-uidiv"+index).attr("linktype") || "0";
	   				if(linktype==oldlinktype){
	   					return true;
	   				}

	   				if(linktype!="0"){
		    			$("#tplsubject-likedata-printTempletListQuery-uidiv"+index).empty();
		    			$("#tplsubject-hidden-likedata-printTempletListQuery"+index).val("");
		    			
		    			if(linktype=="DEPTID"){
		    				showTplsListPageLinkDataWidgetForDept(index);
		    			}else if(linktype=="CUSTOMERID"){
		    				showTplsListPageLinkDataWidgetForCustomer(index);
		    			}else if(linktype=="SALESAREA"){
		    				showTplsListPageLinkDataWidgetForSalesArea(index);
		    			}else if(linktype=="EBSHOPWLGS"){
		    				showTplsListPageLinkDataWidgetForEbshopWLGS(index);
		    			}
			    	}else{
		    			$("#tplsubject-likedata-printTempletListQuery-uidiv"+index).empty();
		    			$("#tplsubject-hidden-likedata-printTempletListQuery"+index).val("");    		
			    	}
	   			},
	   			onClear:function(){
	    			$("#tplsubject-likedata-printTempletListQuery-uidiv"+index).empty();
	    			$("#tplsubject-hidden-likedata-printTempletListQuery"+index).val("");   
	   			}
	   		});
	    }
	    
		//部门 控件
	   	function showTplsListPageLinkDataWidgetForDept(index){
	   		var inputStr="<input type='text' id='tplsubject-likedata-printTempletListQuery-widget"+index+"'/>";
	   		$(inputStr).appendTo("#tplsubject-likedata-printTempletListQuery-uidiv"+index);
	   		$("#tplsubject-likedata-printTempletListQuery-widget"+index).widget({
	   			referwid:'RL_T_BASE_DEPARTMENT_SELLER',
	   			singleSelect:false,
	   			width:'200',
	   			onSelect:function(data){
	   				var val=$("#tplsubject-likedata-printTempletListQuery-widget"+index).widget("getValue");
	   				$("#tplsubject-hidden-likedata-printTempletListQuery"+index).val(val);
	   			},
	   			onClear:function(){
	   				$("#tplsubject-hidden-likedata-printTempletListQuery"+index).val("");
	   			}
	   		});
	   	}
	   	//客户控件
	   	function showTplsListPageLinkDataWidgetForCustomer(index){
	   		var inputStr="<input type='text' id='tplsubject-likedata-printTempletListQuery-widget"+index+"'/>";
	   		$(inputStr).appendTo("#tplsubject-likedata-printTempletListQuery-uidiv"+index);
	   		$("#tplsubject-likedata-printTempletListQuery-widget"+index).widget({
	   			referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
	   			singleSelect:false,
	   			width:'200',
	   			onSelect:function(data){
	   				var val=$("#tplsubject-likedata-printTempletListQuery-widget"+index).widget("getValue");
	   				$("#tplsubject-hidden-likedata-printTempletListQuery"+index).val(val);
	   			},
	   			onClear:function(){
	   				$("#tplsubject-hidden-likedata-printTempletListQuery"+index).val("");
	   			}
	   		});
	   	}
	   	//销售区域
	   	function showTplsListPageLinkDataWidgetForSalesArea(index){
	   		var inputStr="<input type='text' id='tplsubject-likedata-printTempletListQuery-widget"+index+"'/>";
	   		$(inputStr).appendTo("#tplsubject-likedata-printTempletListQuery-uidiv"+index);
	   		$("#tplsubject-likedata-printTempletListQuery-widget"+index).widget({
	   			referwid:'RT_T_BASE_SALES_AREA',
	   			singleSelect:false,
	   			onlyLeafCheck:false,
	   			width:'200',
	   			onSelect:function(data){
	   				var val=$("#tplsubject-likedata-printTempletListQuery-widget"+index).widget("getValue");
	   				$("#tplsubject-hidden-likedata-printTempletListQuery"+index).val(val);
	   			},
	   			onClear:function(){
	   				$("#tplsubject-hidden-likedata-printTempletListQuery"+index).val("");
	   			}
	   		});
	   	}
	   	//电商物流公司
	   	function showTplsListPageLinkDataWidgetForEbshopWLGS(index){
	   		var inputStr="<input type='text' id='tplsubject-likedata-printTempletListQuery-widget"+index+"'/>";
	   		$(inputStr).appendTo("#tplsubject-likedata-printTempletListQuery-uidiv"+index);
	   		$("#tplsubject-likedata-printTempletListQuery-widget"+index).widget({
	   			referwid:'RL_T_SYS_CODE_ENABLE',
	   			singleSelect:true,
	   			onlyLeafCheck:false,
	   			width:'200',
	   			param:[{field:'type',op:'equal',value:'logistics'}],
	   			onSelect:function(data){
	   				var val=$("#tplsubject-likedata-printTempletListQuery-widget"+index).widget("getValue");
	   				$("#tplsubject-hidden-likedata-printTempletListQuery"+index).val(val);
	   			},
	   			onClear:function(){
	   				$("#tplsubject-hidden-likedata-printTempletListQuery"+index).val("");
	   			}
	   		});
	   	}

	   	function printTempletOpenDialog(title, url,onLoadFunc){
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
			    	if(onLoadFunc!=null && typeof(onLoadFunc) == "function" ){
			    		onLoadFunc();
			    	}
	   			},  
			    onClose:function(){
			    	$('#printTemplet-dialog-add-operate-content').window("destroy");
			    }
			});
			$('#printTemplet-dialog-add-operate-content').dialog('open');
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
			linktype=linktype.toUpperCase();	
			var oldlinktype=$("#printTemplet-form-add-linkdata-tr").attr("linktype") || "";
			if(linktype==oldlinktype){
				return true;
			}
			var linktypename=$("#printTemplet-form-add-linktype-widget").widget("getText") || "";
			$("#printTemplet-form-add-linkdata-tr").attr("linktype",linktype);
			$("#printTemplet-form-add-linkdata-widget-myuidiv").empty();
			if(initValue!=''){
				$("#printTemplet-form-add-linkdata").val("");
				$("#printTemplet-form-add-linkdataname").val("");
			}
	    	if(linktype!="" ){
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
		function printTempletOpenDialogForPapaerSizeInfo(id,title){
			if(null==id || ""==id){
				return false;
			}
			if(null==title){
				title='打印模板纸张大小查看';
			}
			var url='agprint/tplpapersize/showPrintPaperSizeViewPage.do?id='+id;
			printTempletOpenDialogForOtherInfo(title,url,{width:450,height:300});
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
