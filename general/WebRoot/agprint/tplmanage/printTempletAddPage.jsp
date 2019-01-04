<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>模板信息添加</title>
    <%@include file="/include.jsp" %>
  </head> 
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center" style="padding: 10px;">
	    		<form action="" method="post" id="printTemplet-form-add" >
	    			<table cellpadding="3" cellspacing="3" border="0">
	    				<tr>	    			
		   					<td width="90px" align="right">模板代码:</td>
		   					<td align="left">
		   						<input type="text" id="printTemplet-form-add-code" name="printTemplet.code" value="${param.thecode}" style="width:200px"/>
		    					<div id="printTemplet-form-add-code-text" style="line-height:25px;" ></div>
		   					</td>
		 				</tr>
	    				<tr>
	    					<td align="right">模板描述名:</td>
	    					<td align="left"><input type="text" name="printTemplet.name" class="easyui-validatebox" required="true" validType="maxByteLength[100]" style="width:200px;"/></td>
	    				</tr>
						<tr>
							<td align="right">公司抬头:</td>
							<td align="left">
								<input type="text" name="printTemplet.companytitle" class="easyui-validatebox" validType="maxByteLength[100]" style="width:200px;"/>
								<a href="javaScript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-help'" title="帮助" onclick="javascript:showHelpDialog('companytitle');"> </a>
							</td>
						</tr>
	    				<tr>
	    					<td align="right">模板标识:</td>
	    					<td align="left"><input type="text" name="printTemplet.mark" validType="maxByteLength[500]" style="width:200px;"/></td>
	    				</tr>
	    				<tr>
	    					<td align="right">模板资源:</td>
	    					<td align="left">
	    						<div id="printTemplet-form-tplresourceid-widget-myuidiv" style="width:215px;float:left"></div>
	    						<div style="float:left">
	    							<a href="javaScript:void(0);" id="printTemplet-form-add-tplresource" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>
	    						</div>
	    						<div style="clear:both">
		    						<div style="line-height:25px;" id="printTemplet-templetfile-upload-result"></div>
		    						<div style="line-height:25px;" id="printTemplet-sourcefile-upload-result"></div>
	    						</div>
	    						<input type="hidden" id="printTemplet-form-hidden-tplresourceid"  name='printTemplet.tplresourceid' />
	    					</td>
	    				</tr>
	    				<tr>
	    					<td align="right">内容排序策略:</td>
	    					<td align="left">
	    						<div id="printTemplet-form-tplorderseq-widget-myuidiv" style="width:215px;float:left"></div>
	    						<div style="float:left">
	    							<a href="javaScript:void(0);" id="printTemplet-form-add-tplorderseq" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>
	    						</div>
	    						<input type="hidden" id="printTemplet-form-hidden-tplorderseq"  name='printTemplet.tplorderseqid' />
	    					</td>
	    				</tr>
						<tr>
							<td align="right">纸张:</td>
							<td align="left">
								<div id="printTemplet-form-papersizeid-widget-myuidiv" style="width:215px;float:left"></div>
								<div style="float:left">
									<a href="javaScript:void(0);" id="printTemplet-form-add-papersizeid" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>
								</div>
								<div style="clear:both;display: none">
									注意：系统默认纸张大小(21.5*14)
								</div>
								<input type="hidden" id="printTemplet-form-hidden-papersizeid"  name='printTemplet.papersizeid' />
							</td>
						</tr>
						<tr id="printTemplet-form-countperpage-tr" style="display: none;">
							<td align="right">每页条数</td>
							<td align="left">
								<input type="text" id="printTemplet-form-countperpage" name="printTemplet.countperpage" class="easyui-validatebox" validType="validSeqInt" style="width:80px;" value=""/>
								填充空行：
								<label >
									<input type="checkbox" name="printTemplet.isfillblank" value="1" style="height: none" />是
								</label>
								<a href="javaScript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-help'" title="帮助" onclick="javascript:showPrintTempletHelpDialog('countperpage');"> </a>
							</td>
						</tr>
	    				<tr>
	    					<td align="right">顺序:</td>
	    					<td align="left"><input type="text" name="printTemplet.seq" class="easyui-validatebox" required="true" validType="validSeqInt" style="width:200px;"/></td>
	    				</tr>
	    				<tr>
	    					<td align="right">是否关联:</td>
	    					<td align="left">
	    						<input id="printTemplet-form-add-linktype-widget" name="printTemplet.linktype"  style="width:200px;" value="0" />
	    					</td>
	    				</tr>
	    				<tr id="printTemplet-form-add-linkdata-tr" style="display:none" linktype="0">
	    					<td align="right"><span id="printTemplet-form-add-linkdata-tr-name"></span></td>
	    					<td align="left">
	    						<div id="printTemplet-form-add-linkdata-widget-myuidiv"></div>
	    					</td>
	    				</tr>
						<tr>
							<td align="right">Lodop超文本模式:</td>
							<td align="left">
								<select style="width:200px" name="printTemplet.lodophtmlmodel">
									<option value="1" <c:if test="${printTemplet.lodophtmlmodel=='1' }">selected="selected"</c:if>>普通模式</option>
									<option value="2" <c:if test="${printTemplet.lodophtmlmodel=='2' }">selected="selected"</c:if>>图形模式</option>
								</select>
								<a href="javaScript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-help'" title="帮助" onclick="javascript:showPrintTempletHelpDialog('lodophtmlmodel');"> </a>
							</td>
						</tr>
	    				<tr>
	    					<td align="right">备注:</td>
	    					<td align="left">	    					
				    			<textarea name="printTemplet.remark" class="easyui-validatebox" validType="maxByteLength[255]" cols="0" rows="0" style="width:200px;height:50px;"></textarea>
	    					</td>
	    				</tr>
	    				<tr>
	    					<td align="right">启用:</td>
	    					<td align="left">
	    						<select disabled="disabled" style="width:200px">
	    							<option value="0">禁用</option>
	    						</select>
	    					</td>
	    				</tr>
	    			</table>
	    			<input type="hidden" name="printTemplet.linkdata" id="printTemplet-form-add-linkdata" />
	    			<input type="hidden" name="printTemplet.linkdataname" id="printTemplet-form-add-linkdataname" />
	    		</form>
	    		<input type="hidden" id="printTemplet-form-clickbtn-ReloadtableAfterSave" />
    		</div>
    	</div>
    	<div data-options="region:'south'" style="height: 30px;border: 0px;">
    		<div align="right">
    			<a href="javaScript:void(0);" id="printTemplet-form-add-save" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="添加">保存</a>
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
    	$(function(){    		

    		$("#printTemplet-form-add-code").widget({
	   			referwid:'RL_PRINT_TEMPLET_SUBJECT',
	   			singleSelect:true,
	   			width:'200',
	   			required:true,
	   			<c:if test="${param.readonlythecode=='true' and !empty( param.thecode )}">
	   				readonly:true,
    			</c:if>
	   			onSelect:function(data){
	   				if(data!=null){
		   				$("#printTemplet-form-add-code-text").html(data.code); 

	        			showPrintTempletOrderSeqWidget(data.code);
	        			showPrintTempletResourceWidget(data.code);

                        showCountPerPageTr(data.code);

	        			
	   				}else{   
		   				$("#printTemplet-form-add-code-text").html("");  

	   	    			$("#printTemplet-form-tplorderseq-widget-myuidiv").empty();
	   					$("#printTemplet-form-tplresourceid-widget-myuidiv").empty();
	   				}
	   			},
	   			onClear:function(){
	   				$("#printTemplet-form-add-code-text").html("");      
   	    			$("#printTemplet-form-tplorderseq-widget-myuidiv").empty();
   					$("#printTemplet-form-tplresourceid-widget-myuidiv").empty();
	   			}
	   		});
    		<c:if test="${!empty( param.thecode )}">
				setTimeout(function(){
                    $("#printTemplet-form-add-code").widget('setValue','${param.thecode}');
				},100);

            $("#printTemplet-form-add-code").blur(function(event){
                $("#printTemplet-form-add-code").widget('setValue','${param.thecode}');
                event.preventDefault();
            });
    		</c:if>
    		
    		$("#printTemplet-form-add-save").click(function(){
        		var tmp=$("#printTemplet-form-add-code").widget("getValue")||"";
        		if($.trim(tmp)==""){
            		$.messager.alert("提醒","请选择模板代码!");
            		return false;            		
        		}
        		tmp=$.trim(tmp);
        		if(!/([a-zA-Z0-9_]|-)+/.test(tmp)){
            		$.messager.alert("提醒","模板代码规则：英文字母、数字、中划线(-)或下划线(_)<br/>请联系管理员修改模板代码表中的打印模板!");
            		return false; 
        		}
        		var linktype=$("#printTemplet-form-add-linktype").val() || '0';
        		var linktypename=$("#printTemplet-form-add-linktype").find("option:selected").text(); 
        		    
		  
		    	if(linktype!='' && linktype!='0'){
		    		tmp=$("#printTemplet-form-add-linkdata").val() || "";
		    		if(tmp==""){
		    			if(linktypename!=''){
	                		$.messager.alert("提醒","请选择"+linktypename);		    				
		    			}else{
	                		$.messager.alert("提醒","请选择关联的项目");		    				
		    			}
                		return false;                          		
            		}
		    	}

    			tmp=$("#printTemplet-form-hidden-tplresourceid").val() || "";
    			if(null == tmp || "" == tmp){
            		$.messager.alert("提醒","抱歉，请选择模板资源!");
            		return;        			
    			}
    			var flag = $("#printTemplet-form-add").form('validate');
				if(flag==false){
					return false;
				}
    			$.messager.confirm("提醒","是否添加模板信息?",function(r){
    				if(r){
        				var formdata=$("#printTemplet-form-add").serializeJSON();
        				loading('提交中..');
    					$.ajax({
    				        type: 'post',
    				        cache: false,
    				        url: 'agprint/tplmanage/addPrintTemplet.do',
    				        data: formdata,
    				        dataType:'json',
    				        success:function(data){
    				        	loaded();
    	    	            	if(data.flag){
    	        					var reloadtableClick=$("#printTemplet-form-clickbtn-ReloadtableAfterSave").val()||"";
    	    	            		$.messager.alert("提醒","添加成功!");
    	        					$("#printTemplet-dialog-add-operate-content").dialog('close',true);
    	        					if(reloadtableClick!="" &&$("#"+reloadtableClick).size()>0){
    	        						$("#"+reloadtableClick).trigger("click");
    	        					}else{
    	        						$("#tplmanage-query-printTempletList").trigger("click");
    	        					}
    	    	            	}else{
    	        	            	if(data.msg){
    	        						$.messager.alert("提醒","添加失败!"+data.msg);
    	        	            	}else{
    	        						$.messager.alert("提醒","添加失败!");
    	        	            	}
    	        				}
    				        }
    				    });
    	            	
    				}
    			});
    		});

    		showPageLinkTypeWidget(false);

            showPrintTempletPaperSizeWidget();
    		addNewTplResource();
    		addNewTplOrderseq();
			addNewPaperSize();
		  
    	});
    	function addNewTplResource(){
    		$("#printTemplet-form-add-tplresource").click(function(){
    			var code=$("#printTemplet-form-add-code").widget("getValue") || "";
  				
  				var onLoadFunc=function(){
  					$("#tplresource-printTempletResource-form-afterSaveOperType").val("callBackFunc");
  					$("#tplresource-printTempletResource-form-afterSaveOper").val("addTplResourceCallBack");
  				};
  				resourceADMOperDialog('打印模板资源【新增】',
  						'agprint/tplresource/showPrintTempletResourceAddPage.do?readonlythecode=true&&thecode='+code,
  						onLoadFunc);
    		});
    	}
    	function addTplResourceCallBack(){
    		var code=$("#printTemplet-form-add-code").widget("getValue") || "";
    		if(code!=""){
    			showPrintTempletResourceWidget(code);
    		}
    	}

		function showPrintTempletResourceWidget(subject){
			if(subject==null || subject==""){
				$("#printTemplet-form-tplresourceid-widget-myuidiv").html("请选择模板代码");
				return;
			}
			try{
				$("#printTemplet-form-tplresourceid-widget").widget('clear');
			}catch(e){

			}
			$("#printTemplet-form-tplresourceid-widget-myuidiv").empty();

			var inputStr="<input type='text' id='printTemplet-form-tplresourceid-widget' class='easyui-validatebox' required='true' style='width:200px;'/>";
			$(inputStr).appendTo("#printTemplet-form-tplresourceid-widget-myuidiv");
			$("#printTemplet-form-tplresourceid-widget").widget({
				referwid:'RL_PRINT_TEMPLET_RESOURCE',
				singleSelect:true,
				width:'200',
				required:true,
				param:[{field:'code', op:'equal', value:subject}],
				onSelect:function(data){
					if(data!=null){
						$("#printTemplet-form-hidden-tplresourceid").val(data.id);


                        if(data.papersizeid!=null && data.papersizeid!="") {
                            var curpapersize = $("#printTemplet-form-papersizeid-widget").widget("getValue") || "";
                            curpapersize=$.trim(curpapersize);
                            if(curpapersize==""){
                                $("#printTemplet-form-papersizeid-widget").widget("setValue",data.papersizeid);
                            }
                        }

						$("#printTemplet-templetfile-upload-result").html("模板文件(jasper):"+data.templetfile);
						$("#printTemplet-sourcefile-upload-result").html("模板源文件(jrxml):"+data.sourcefile);
					}else{
						$("#printTemplet-form-hidden-tplresourceid").val("");
						$("#printTemplet-templetfile-upload-result").html("");
						$("#printTemplet-sourcefile-upload-result").html("");
					}
				},
				onClear:function(){
					$("#printTemplet-form-hidden-tplresourceid").val("");
					$("#printTemplet-templetfile-upload-result").html("");
					$("#printTemplet-sourcefile-upload-result").html("");
				}
			});
		}

    	function addNewTplOrderseq(){
    		$("#printTemplet-form-add-tplorderseq").click(function(){
    			var code=$("#printTemplet-form-add-code").widget("getValue") || "";
  				
  				var onLoadFunc=function(){
  					$("#tplorderseq-PrintTempletOrderseq-form-afterSaveOperType").val("callBackFunc");
  					$("#tplorderseq-PrintTempletOrderseq-form-afterSaveOper").val("addTplOrderseqCallBack");
  				};
  				printOrderSeqOpenDialog('打印内容排序策略【新增】',
  						'agprint/tplorderseq/showPrintOrderSeqAddPage.do?readonlythecode=true&&thecode='+code,
  						onLoadFunc);
    		});
    	}
    	
    	function addTplOrderseqCallBack(){
    		var code=$("#printTemplet-form-add-code").widget("getValue") || "";
    		if(code!=""){
    			showPrintTempletOrderSeqWidget(code);
    		}
    	}
		function showPrintTempletOrderSeqWidget(subject){
			if(subject==null || subject==""){
				$("#printTemplet-form-tplorderseq-widget-myuidiv").html("请选择模板代码");
				return;
			}

			try{
				$("#printTemplet-form-orderseq-widget").widget('clear');
			}catch(e){

			}
			$("#printTemplet-form-tplorderseq-widget-myuidiv").empty();

			$("<input type='text' id='printTemplet-form-orderseq-widget' class='easyui-validatebox' required='true' style='width:200px;'/>").appendTo("#printTemplet-form-tplorderseq-widget-myuidiv");
			$("#printTemplet-form-orderseq-widget").widget({
				referwid:'RL_PRINT_TEMPLET_ORDERSEQ',
				singleSelect:true,
				width:'200',
				param:[{field:'code', op:'equal', value:subject}],
				onSelect:function(data){
					if(data!=null){
						$("#printTemplet-form-hidden-tplorderseq").val(data.id);
					}else{
						$("#printTemplet-form-hidden-tplorderseq").val("");
					}
				},
				onClear:function(){
					$("#printTemplet-form-hidden-tplorderseq").val("");
				}
			});
		}

		function addNewPaperSize(){
			$("#printTemplet-form-add-papersizeid").click(function(){
				var onLoadFunc=function(){
					$("#tplpapersize-PrintPaperSize-form-afterSaveOperType").val("callBackFunc");
					$("#tplpapersize-PrintPaperSize-form-afterSaveOper").val("addPaperSizeCallBack");
				};
				printPaperSizeOpenDialog('打印纸张【新增】',
						'agprint/tplpapersize/showPrintPaperSizeAddPage.do',
						onLoadFunc);
			});
		}

		function addPaperSizeCallBack(){
			showPrintTempletPaperSizeWidget();
		}
		function showPrintTempletPaperSizeWidget(){
			try{
				$("#printTemplet-form-papersizeid-widget").widget('clear');
			}catch(e){

			}
			$("#printTemplet-form-papersizeid-widget-myuidiv").empty();

			var inputStr="<input type='text' id='printTemplet-form-papersizeid-widget' class='easyui-validatebox' required='true' style='width:200px;'/>";
			$(inputStr).appendTo("#printTemplet-form-papersizeid-widget-myuidiv");
			$("#printTemplet-form-papersizeid-widget").widget({
				referwid:'RL_PRINT_TEMPLET_PAPERSIZE',
				singleSelect:true,
				width:'200',
				//required:true,
				onSelect:function(data){
					if(data!=null){
						$("#printTemplet-form-hidden-papersizeid").val(data.id);
					}else{
						$("#printTemplet-form-hidden-papersizeid").val("");
					}
				},
				onClear:function(){
					$("#printTemplet-form-hidden-papersizeid").val("");
				}
			});
		}
    	

    	
	   	
	   	//部门 控件
	   	function showPageLinkDataWidgetForDept(){
	   		$("<input type='text' id='printTemplet-form-add-linkdata-widget'/>").appendTo("#printTemplet-form-add-linkdata-widget-myuidiv");
	   		$("#printTemplet-form-add-linkdata-widget").widget({
	   			referwid:'RL_T_BASE_DEPARTMENT_SELLER',
	   			singleSelect:false,
	   			width:'200',
	   			required:true,
	   			onSelect:function(data){
	   				var val=$(this).widget("getValue");
	   				$("#printTemplet-form-add-linkdata").val(val);
	   				val=$(this).widget("getText");
	   				$("#printTemplet-form-add-linkdataname").val(val);
	   			},
	   			onClear:function(){
	   				$("#printTemplet-form-add-linkdata").val("");
	   				$("#printTemplet-form-add-linkdataname").val("");
	   			}
	   		});
	   	}
	   	//客户控件
	   	function showPageLinkDataWidgetForCustomer(){
	   		$("<input type='text' id='printTemplet-form-add-linkdata-widget'/>").appendTo("#printTemplet-form-add-linkdata-widget-myuidiv");
	   		$("#printTemplet-form-add-linkdata-widget").widget({
	   			referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_1',
	   			singleSelect:false,
                onlyLeafCheck:false,
	   			width:'200',
	   			required:true,
	   			onSelect:function(data){
	   				var val=$(this).widget("getValue");
	   				$("#printTemplet-form-add-linkdata").val(val);
	   				val=$(this).widget("getText");
	   				$("#printTemplet-form-add-linkdataname").val(val);
	   			},
	   			onClear:function(){
	   				$("#printTemplet-form-add-linkdata").val("");
	   				$("#printTemplet-form-add-linkdataname").val("");
	   			}
	   		});
	   	}
	   	//销售区域
	   	function showPageLinkDataWidgetForSalesArea(){
	   		$("<input type='text' id='printTemplet-form-add-linkdata-widget'/>").appendTo("#printTemplet-form-add-linkdata-widget-myuidiv");
	   		$("#printTemplet-form-add-linkdata-widget").widget({
	   			referwid:'RT_T_BASE_SALES_AREA',
	   			singleSelect:false,
	   			onlyLeafCheck:false,
	   			width:'200',
	   			required:true,
	   			onSelect:function(data){
	   				var val=$(this).widget("getValue");
	   				$("#printTemplet-form-add-linkdata").val(val);
	   				val=$(this).widget("getText");
	   				$("#printTemplet-form-add-linkdataname").val(val);
	   			},
	   			onClear:function(){
	   				$("#printTemplet-form-add-linkdata").val("");
	   				$("#printTemplet-form-add-linkdataname").val("");
	   			}
	   		});
	   	}
	   	//电商物流公司
	   	function showPageLinkDataWidgetForEbshopWLGS(){
	   		$("<input type='text' id='printTemplet-form-add-linkdata-widget'/>").appendTo("#printTemplet-form-add-linkdata-widget-myuidiv");
	   		$("#printTemplet-form-add-linkdata-widget").widget({
	   			referwid:'RL_T_SYS_CODE_ENABLE',
	   			singleSelect:true,
	   			onlyLeafCheck:false,
	   			width:'200',
	   			required:true,
	   			param:[{field:'type',op:'equal',value:'logistics'}],
	   			onSelect:function(data){
	   				var val=$(this).widget("getValue");
	   				$("#printTemplet-form-add-linkdata").val(val);
	   				val=$(this).widget("getText");
	   				$("#printTemplet-form-add-linkdataname").val(val);
	   			},
	   			onClear:function(){
	   				$("#printTemplet-form-add-linkdata").val("");
	   				$("#printTemplet-form-add-linkdataname").val("");
	   			}
	   		});
	   	}
        function showCountPerPageTr(code){
	   	    $("#printTemplet-form-countperpage-tr").hide();
            $("#printTemplet-form-countperpage-tr").show();
		}

    </script>
  </body>
</html>
