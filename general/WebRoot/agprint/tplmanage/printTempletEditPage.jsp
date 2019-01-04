<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>模板信息修改</title>
    <%@include file="/include.jsp" %>
  </head> 
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center">
	    		<form action="" method="post" id="printTemplet-form-edit" style="padding: 10px;">
	    			<table cellpadding="3" cellspacing="3" border="0">
	    				<tr>	    			
		   					<td width="90px" align="right">模板代码:</td>
		   					<td align="left">
		   						<input type="text" id="printTemplet-form-add-code" name="code" value="${printTemplet.code }" readonly="readonly" style="width:200px"/>
		    					<div id="printTemplet-form-add-code-text" style="line-height:25px;" >${printTemplet.code }</div>
		   					</td>
		 				</tr>
	    				<tr>
	    					<td align="right">模板描述名:</td>
	    					<td align="left"><input type="text" name="printTemplet.name" value="${printTemplet.name }" class="easyui-validatebox" required="true" validType="maxByteLength[100]" style="width:200px;"/></td>
	    				</tr>
						<tr>
							<td align="right">公司抬头:</td>
							<td align="left">
								<input type="text" name="printTemplet.companytitle" value="${printTemplet.companytitle}" class="easyui-validatebox" validType="maxByteLength[100]" style="width:200px;"/>
								<a href="javaScript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-help'" title="帮助" onclick="javascript:showHelpDialog('companytitle');"> </a>
							</td>
						</tr>
	    				<tr>
	    					<td align="right">模板标识:</td>
	    					<td align="left"><input type="text" name="printTemplet.mark" value="${printTemplet.mark }" validType="maxByteLength[500]" style="width:200px;"/></td>
	    				</tr>
	    				<tr>
	    					<td align="right">模板资源:</td>
	    					<td align="left">
	    						<div id="printTemplet-form-tplresourceid-widget-myuidiv" style="width:215px;float:left"></div>
	    						<div style="float:left">
	    							<a href="javaScript:void(0);" id="printTemplet-form-add-tplresource" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>
	    						</div>
	    						<div style="clear:both">
	    							<div style="line-height:25px;" id="printTemplet-form-tplresourceid-result">${tplresourcename }</div>
		    						<div style="line-height:25px;" id="printTemplet-templetfile-upload-result">模板文件(jasper):${printTemplet.templetfile }</div>
		    						<div style="line-height:25px;" id="printTemplet-sourcefile-upload-result">模板源文件(jrxml):${printTemplet.sourcefile }</div>
	    						</div>
	    						<input type="hidden" id="printTemplet-form-hidden-tplresourceid"  name='printTemplet.tplresourceid' value="${printTemplet.tplresourceid }" />	    						
	    						<input type="hidden" id="printTemplet-form-hidden-oldtplresourceid"  name='oldtplresourceid' value="${printTemplet.tplresourceid }" />
	    					</td>
	    				</tr>
	    				<tr>
	    					<td align="right">内容排序策略:</td>
	    					<td align="left">
	    						<div id="printTemplet-form-tplorderseq-widget-myuidiv" style="width:215px;float:left"></div>
	    						<div style="float:left">
	    							<a href="javaScript:void(0);" id="printTemplet-form-add-tplorderseq" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>
	    						</div>
	    						<div style="clear:both">
	    							<div style="line-height:25px;" id="printTemplet-form-tplorderseq-result">${tplorderseqname }</div>
	    						</div>
	    						<input type="hidden" id="printTemplet-form-hidden-tplorderseq"  name='printTemplet.tplorderseqid' value="${printTemplet.tplorderseqid }"/>
	    					</td>
	    				</tr>
						<tr>
							<td align="right">纸张:</td>
							<td align="left">
								<div id="printTemplet-form-papersizeid-widget-myuidiv" style="width:215px;float:left"></div>
								<div style="float:left">
									<a href="javaScript:void(0);" id="printTemplet-form-add-papersizeid" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>
								</div>
								<div style="clear:both">
									<div style="line-height:25px;" id="printTemplet-form-papersizeid-result">${papersizename }</div>
									<div style="line-height:25px;display: none">注意：系统默认纸张大小(21.5*14)</div>
								</div>
								<input type="hidden" id="printTemplet-form-hidden-papersizeid"  name='printTemplet.papersizeid' value="${printTemplet.papersizeid}" />
							</td>
						</tr>
						<tr id="printTemplet-form-countperpage-tr" style="display: none;">
							<td align="right">每页条数</td>
							<td align="left">
								<input type="text" name="printTemplet.countperpage" class="easyui-validatebox" validType="validSeqInt" style="width:80px;" value="${printTemplet.countperpage}"/>
								填充空行：
								<label >
									<input type="checkbox" name="printTemplet.isfillblank" value="1" style="height: none" <c:if test="${printTemplet.isfillblank=='1'}">checked="checked"</c:if>/>是
								</label>
								<a href="javaScript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-help'" title="帮助" onclick="javascript:showPrintTempletHelpDialog('countperpage');"> </a>
							</td>
						</tr>
	    				<tr>
	    					<td align="right">顺序:</td>
	    					<td align="left"><input type="text" name="printTemplet.seq" value="${printTemplet.seq }" class="easyui-validatebox" required="true" validType="validSeqInt" style="width:200px;"/></td>
	    				</tr>
	    				<tr>
	    					<td align="right">是否关联:</td>
	    					<td align="left">
	    						<input id="printTemplet-form-add-linktype-widget" name="printTemplet.linktype"  style="width:200px;" />
	    					</td>
	    				</tr>	    				
	    				<tr id="printTemplet-form-add-linkdata-tr" linktype="0" style="display:none">
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
				    			<textarea name="printTemplet.remark" class="easyui-validatebox" validType="maxByteLength[255]" cols="0" rows="0" style="width:200px;height:50px;">${printTemplet.remark }</textarea>
	    					</td>
	    				</tr>
	    				<tr>
	    					<td align="right">启用:</td>
	    					<td align="left">
	    						<select disabled="disabled" style="width:200px">
	    							<option value="0" <c:if test="${printTemplet.state=='0' }">selected="selected"</c:if>>禁用</option>
	    							<option value="1" <c:if test="${printTemplet.state=='1' }">selected="selected"</c:if>>启用</option>
	    						</select>
	    					</td>
	    				</tr>
	    			</table>
	    			<input type="hidden" id="printTemplet-form-add-id" name="printTemplet.id" value="${printTemplet.id }"/>
	    			<input type="hidden" name="printTemplet.linkdata" id="printTemplet-form-add-linkdata" value="${printTemplet.linkdata }" />
	    			<input type="hidden" name="printTemplet.linkdataname" id="printTemplet-form-add-linkdataname" value="${printTemplet.linkdataname}" />
	    		</form>
	    		<input type="hidden" id="printTemplet-form-clickbtn-ReloadtableAfterSave" />
    		</div>
    	</div>
    	<div data-options="region:'south'" style="height: 30px;border: 0px;">
    		<div align="right">
    			<a href="javaScript:void(0);" id="printTemplet-form-edit-save" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="编辑">保存</a>
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
    	$(function(){
    		$("#printTemplet-form-add-code").widget({
	   			referwid:'RL_PRINT_TEMPLET_SUBJECT',
	   			singleSelect:true,
	   			width:'200',
	   			readonly:true,
	   			view:true
	   		});

            $("#printTemplet-form-add-code").blur(function(event){
                $("#printTemplet-form-add-code").widget("setValue","${printTemplet.code}");
                event.preventDefault();
            });
    		$("#printTemplet-form-edit-save").click(function(){
        		var tmp=$("#printTemplet-form-add-id").val()||"";
        		if($.trim(tmp)==""){
            		$.messager.alert("提醒","请未找到相关要修改的模板信息!");
            		return false;            		
        		}
        		var linktype=$("#printTemplet-form-add-linktype").widget("getValue") || '0';
        		var linktypename=$("#printTemplet-form-add-linktype").widget("getText") || ""; 
        		    
		  
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
    			var flag = $("#printTemplet-form-edit").form('validate');
				if(flag==false){
					return false;
				}
    			$.messager.confirm("提醒","是否修改模板信息?",function(r){
    				if(r){
        				var formdata=$("#printTemplet-form-edit").serializeJSON();
        				loading('提交中..');
    					$.ajax({
    				        type: 'post',
    				        cache: false,
    				        url: 'agprint/tplmanage/editPrintTemplet.do',
    				        data: formdata,
    				        dataType:'json',
    				        success:function(data){
    				        	loaded();
    	    	            	if(data.flag){
    	        					var reloadtableClick=$("#printTemplet-form-clickbtn-ReloadtableAfterSave").val()||"";
    	    	            		$.messager.alert("提醒","修改成功!");
    	        					$("#printTemplet-dialog-add-operate-content").dialog('close',true);
    	        					if(reloadtableClick!="" &&$("#"+reloadtableClick).size()>0){
    	        						$("#"+reloadtableClick).trigger("click");
    	        					}else{
    	        						$("#tplmanage-query-printTempletList").trigger("click");
    	        					}
    	    	            	}else{
    	        	            	if(data.msg){
    	        						$.messager.alert("提醒","修改失败!"+data.msg);
    	        	            	}else{
    	        						$.messager.alert("提醒","修改失败!");
    	        	            	}
    	        				}
    				        }
    				    });
    	            	
    				}
    			});
    		});

	   		
	   		showInitWidgetData();
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

    		var val=$("#printTemplet-form-tplresourceid-widget").widget('getValue') || "";

    		if(code!=""){
    			showPrintTempletResourceWidget(code,val);
    		}
    	}
		function showPrintTempletResourceWidget(subject,initValue){
			if(subject==null || subject==""){
				$("#printTemplet-form-tplresourceid-widget-myuidiv").html("请选择模板代码");
				return;
			}
			if(initValue==null){
				initValue='';
			}
			initValue= $.trim(initValue);
			try{
				$("#printTemplet-form-tplresourceid-widget").widget('clear');
			}catch(e){

			}
			$("#printTemplet-form-tplresourceid-widget-myuidiv").empty();

			var inputStr="<input type='text' id='printTemplet-form-tplresourceid-widget' style='width:200px;'/>";
			$(inputStr).appendTo("#printTemplet-form-tplresourceid-widget-myuidiv");
			$("#printTemplet-form-tplresourceid-widget").widget({
				referwid:'RL_PRINT_TEMPLET_RESOURCE',
				singleSelect:true,
				width:'200',
				required:true,
				view:true,
				initValue:initValue,
				param:[{field:'code', op:'equal', value:subject}],
				onSelect:function(data){
					$("#printTemplet-form-tplresourceid-result").html("");
					if(data!=null){
						$("#printTemplet-form-hidden-tplresourceid").val(data.id);

						if(data.state!='1'){
							var htmlsb=new Array();
							htmlsb.push("注意:");
							if(data.name!=null){
								htmlsb.push(data.name);
							}
							htmlsb.push("(未启用)");
							$("#printTemplet-form-tplresourceid-result").html(htmlsb.join(''));
						}
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
					$("#printTemplet-form-tplresourceid-result").html("");
				}
			});
			if(initValue!=''){
				$("#printTemplet-form-tplresourceid-widget").widget('setValue',initValue);
			}
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
		function showPrintTempletOrderSeqWidget(subject,initValue){
			if(subject==null || subject==""){
				$("#printTemplet-form-tplorderseq-widget-myuidiv").html("请选择模板代码");
				return;
			}
			if(initValue==null){
				initValue='';
			}
			initValue= $.trim(initValue);
			try{
				$("#printTemplet-form-orderseq-widget").widget('clear');
			}catch(e){

			}
			$("#printTemplet-form-tplorderseq-widget-myuidiv").empty();

			var inputStr="<input type='text' id='printTemplet-form-orderseq-widget' style='width:200px;'/>";
			$(inputStr).appendTo("#printTemplet-form-tplorderseq-widget-myuidiv");
			$("#printTemplet-form-orderseq-widget").widget({
				referwid:'RL_PRINT_TEMPLET_ORDERSEQ',
				singleSelect:true,
				width:'200',
				view:true,
				param:[{field:'code', op:'equal', value:subject}],
				onSelect:function(data){
					$("#printTemplet-form-tplorderseq-result").html("");
					if(data!=null){
						$("#printTemplet-form-hidden-tplorderseq").val(data.id);
						if(data.state!='1'){
							var htmlsb=new Array();
							htmlsb.push("注意:");
							if(data.name!=null){
								htmlsb.push(data.name);
							}
							htmlsb.push("(未启用)");
							$("#printTemplet-form-tplorderseq-result").html(htmlsb.join(''));
						}
					}else{
						$("#printTemplet-form-hidden-tplorderseq").val("");
					}
				},
				onClear:function(){
					$("#printTemplet-form-hidden-tplorderseq").val("");
					$("#printTemplet-form-tplorderseq-result").html("");
				}
			});
			if(initValue!=''){
				$("#printTemplet-form-orderseq-widget").widget('setValue',initValue);
			}
		}
    	
    	function addTplOrderseqCallBack(){
    		var code=$("#printTemplet-form-add-code").widget("getValue") || "";
    		var val=$("#printTemplet-form-orderseq-widget").widget('getValue') || "";
    		if(code!=""){
    			showPrintTempletOrderSeqWidget(code,val);
    		}
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
		function showPrintTempletPaperSizeWidget(initValue){
			if(initValue==null){
				initValue='';
			}
			initValue= $.trim(initValue);
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
					$("#printTemplet-form-papersizeid-result").html("");
					if(data!=null){
						$("#printTemplet-form-hidden-papersizeid").val(data.id);
						if(data.state!='1'){
							var htmlsb=new Array();
							htmlsb.push("注意:");
							if(data.name!=null){
								htmlsb.push(data.name);
							}
							htmlsb.push("(未启用)");
							$("#printTemplet-form-papersizeid-result").html(htmlsb.join(''));
						}
					}else{
						$("#printTemplet-form-hidden-papersizeid").val("");
					}
				},
				onClear:function(){
					$("#printTemplet-form-hidden-papersizeid").val("");
				}
			});
			if(initValue!=''){
			    setTimeout(function(){
				$("#printTemplet-form-papersizeid-widget").widget('setValue',initValue);
				},10);
			}
		}
    	
    	function showInitWidgetData(){
    		var subject=$("#printTemplet-form-add-code").widget("getValue");
    		showPrintTempletResourceWidget(subject,'${printTemplet.tplresourceid}');
    		showPrintTempletOrderSeqWidget(subject,'${printTemplet.tplorderseqid}');
			showPrintTempletPaperSizeWidget('${printTemplet.papersizeid}');
    		
    		var linktype=$.trim("${printTemplet.linktype}");
    		var linkdata=$.trim("${printTemplet.linkdata}");
    		showPageLinkTypeWidget(true,linkdata);
    		if(linktype!=""){
	    		$("#printTemplet-form-add-linktype-widget").widget('setValue',linktype);
	    	}else{
	    		$("#printTemplet-form-add-linktype-widget").widget('clear');
	    	}
            showCountPerPageTr(subject);
    	}

		//部门 控件
		function showPageLinkDataWidgetForDept(initValue){
			if(initValue==null){
				initValue='';
			}
			$("<input type='text' id='printTemplet-form-add-linkdata-widget'/>").appendTo("#printTemplet-form-add-linkdata-widget-myuidiv");
			$("#printTemplet-form-add-linkdata-widget").widget({
				referwid:'RL_T_BASE_DEPARTMENT_SELLER',
				singleSelect:false,
				width:'200',
				required:true,
				initValue:initValue,
				onSelect:function(data){
					var val=$("#printTemplet-form-add-linkdata-widget").widget("getValue");
					$("#printTemplet-form-add-linkdata").val(val);
					val=$("#printTemplet-form-add-linkdata-widget").widget("getText");
					$("#printTemplet-form-add-linkdataname").val(val);
				},
				onClear:function(){
					$("#printTemplet-form-add-linkdata").val("");
					$("#printTemplet-form-add-linkdataname").val("");
				},
                onLoadSuccess:function(){
                    if(initValue!=''){
                        //$("#printTemplet-form-add-linkdata-widget").widget('setValue',initValue);
                        var val=$("#printTemplet-form-add-linkdata-widget").widget("getText");
                        $("#printTemplet-form-add-linkdata").val(initValue);
                        $("#printTemplet-form-add-linkdataname").val(val);
                    }
                }
			});
			//if(initValue!=''){
			//	$("#printTemplet-form-add-linkdata-widget").widget('setValue',initValue);
			//}
		}
		//客户控件
		function showPageLinkDataWidgetForCustomer(initValue){
			if(initValue==null){
				initValue='';
			}
			$("<input type='text' id='printTemplet-form-add-linkdata-widget'/>").appendTo("#printTemplet-form-add-linkdata-widget-myuidiv");
			$("#printTemplet-form-add-linkdata-widget").widget({
				referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_1',
				singleSelect:false,
				width:'200',
				required:true,
				initValue:initValue,
				onSelect:function(data){
					var val=$("#printTemplet-form-add-linkdata-widget").widget("getValue");
					$("#printTemplet-form-add-linkdata").val(val);
					val=$("#printTemplet-form-add-linkdata-widget").widget("getText");
					$("#printTemplet-form-add-linkdataname").val(val);
				},
				onClear:function(){
					$("#printTemplet-form-add-linkdata").val("");
					$("#printTemplet-form-add-linkdataname").val("");
				},
                onLoadSuccess:function(){
                    if(initValue!=''){
                        //$("#printTemplet-form-add-linkdata-widget").widget('setValue',initValue);
                        var val=$("#printTemplet-form-add-linkdata-widget").widget("getText");
                        $("#printTemplet-form-add-linkdata").val(initValue);
                        $("#printTemplet-form-add-linkdataname").val(val);
                    }
                }
			});
		}
		//销售区域
		function showPageLinkDataWidgetForSalesArea(initValue){
			if(initValue==null){
				initValue='';
			}
			$("<input type='text' id='printTemplet-form-add-linkdata-widget'/>").appendTo("#printTemplet-form-add-linkdata-widget-myuidiv");
			$("#printTemplet-form-add-linkdata-widget").widget({
				referwid:'RT_T_BASE_SALES_AREA',
				singleSelect:false,
				onlyLeafCheck:false,
				width:'200',
				required:true,
				initValue:initValue,
				onSelect:function(data){
					var val=$("#printTemplet-form-add-linkdata-widget").widget("getValue");
					$("#printTemplet-form-add-linkdata").val(val);
					val=$("#printTemplet-form-add-linkdata-widget").widget("getText");
					$("#printTemplet-form-add-linkdataname").val(val);
				},
				onClear:function(){
					$("#printTemplet-form-add-linkdata").val("");
					$("#printTemplet-form-add-linkdataname").val("");
				},
                onLoadSuccess:function(){
                    if(initValue!=''){
                        //$("#printTemplet-form-add-linkdata-widget").widget('setValue',initValue);
                        var val=$("#printTemplet-form-add-linkdata-widget").widget("getText");
                        $("#printTemplet-form-add-linkdata").val(initValue);
                        $("#printTemplet-form-add-linkdataname").val(val);
                    }
                }
			});

			//if(initValue!=''){
			//	$("#printTemplet-form-add-linkdata-widget").widget('setValue',initValue);
			//}

		}
		//电商物流公司
		function showPageLinkDataWidgetForEbshopWLGS(initValue){
			if(initValue==null){
				initValue='';
			}
			$("<input type='text' id='printTemplet-form-add-linkdata-widget'/>").appendTo("#printTemplet-form-add-linkdata-widget-myuidiv");
			$("#printTemplet-form-add-linkdata-widget").widget({
				referwid:'RL_T_SYS_CODE_ENABLE',
				singleSelect:true,
				onlyLeafCheck:false,
				width:'200',
				required:true,
				initValue:initValue,
				param:[{field:'type',op:'equal',value:'logistics'}],
				onSelect:function(data){
					var val=$("#printTemplet-form-add-linkdata-widget").widget("getValue");
					$("#printTemplet-form-add-linkdata").val(val);
					val=$("#printTemplet-form-add-linkdata-widget").widget("getText");
					$("#printTemplet-form-add-linkdataname").val(val);
				},
				onClear:function(){
					$("#printTemplet-form-add-linkdata").val("");
					$("#printTemplet-form-add-linkdataname").val("");
				},
                onLoadSuccess:function(){
                    if(initValue!=''){
                        //$("#printTemplet-form-add-linkdata-widget").widget('setValue',initValue);
                        var val=$("#printTemplet-form-add-linkdata-widget").widget("getText");
                        $("#printTemplet-form-add-linkdata").val(initValue);
                        $("#printTemplet-form-add-linkdataname").val(val);
                    }
                }
			});
			//if(initValue!=''){
			//	$("#printTemplet-form-add-linkdata-widget").widget('setValue',initValue);
			//}
		}

        function showCountPerPageTr(code){
            $("#printTemplet-form-countperpage-tr").hide();
                $("#printTemplet-form-countperpage-tr").show();
        }
    </script>
  </body>
</html>
