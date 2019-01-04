<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代码修改</title>
  </head>
  
  <body>
 <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'center',border:false">
       		<div align="center" style="padding: 10px;">
			    <form action="agprint/tplorderseq/editPrintOrderSeq.do" method="post" id="tplorderseq-form-addPrintOrderSeq">
					<table cellpadding="3" cellspacing="3" border="0">
	    				<tr>	    			
	    					<td width="90px" align="right">模板代码:</td>
	    					<td align="left">
		   						<input type="text" id="tplorderseq-PrintTempletOrderseq-form-code" name="printOrderSeq.code" value="${printOrderSeq.code }" style="width:300px" readonly="readonly" class="easyui-validatebox" required="true"/>
		    					<div id="tplorderseq-PrintTempletOrderseq-form-code-text" style="line-height:25px;" >${printOrderSeq.code }</div>
	    					</td>
	    				</tr>
	    				<tr>	    			
	    					<td width="90px" align="right">策略名称:</td>
	    					<td align="left">
	    						<input type="text" name="printOrderSeq.name" value="${printOrderSeq.name }" class="easyui-validatebox" required="true" validType="maxByteLength[120]" style="width:300px;" autocomplete="off" />
	    					</td>
	    				</tr>
	    				<tr>	    			
	    					<td width="90px" align="right">排序策略:</td>
	    					<td align="left">
	    						<div style="float:left">
	    							<input type="text" name="printOrderSeq.orderseq" value="${printOrderSeq.orderseq }" id="tplresource-PrintTempletOrderseq-form-orderseq" class="easyui-validatebox" required="true" validType="maxByteLength[100]" style="width:300px;" autocomplete="off" />
	    						</div>
	    						<div style="float:left">
	    							<a href="javascript:void(0);" id="tplorderseq-form-showCreateDialog-btn" class="easyui-linkbutton"  iconCls="button-import" plain="true" title="导出" >生成策略</a>
	    						</div>
	    					</td>
	    				</tr>
			    		<tr>
	    					<td align="right">注意：</td>
	    					<td>多个排序使用,分隔;asc(从小到大);desc(从大到小)</td>
	    				</tr>
	    				<tr>
	    					<td width="90px" align="right">状态</td>
	    					<td align="left">
	    						<select style="width:300px;" disabled="disabled">
				    				<option value="1" <c:if test="${printOrderSeq.state=='1' }">selected="selected"</c:if> >有效</option>
				    				<option value="0" <c:if test="${printOrderSeq.state=='0' }">selected="selected"</c:if> >无效</option>
				    			</select>
	    					</td>
	    				</tr>
				    	<tr>
				    		<td align="right">备注:</td>
			    			<td align="left">
				    			<textarea rows="0" cols="0" name="printOrderSeq.remark" style="width:300px;height:80px;" class="easyui-validatebox" validType="maxByteLength[500]">${printOrderSeq.remark}</textarea>
			    			</td>
				    	</tr>
    				</table>
    				<input type="hidden" name="printOrderSeq.id" value="${printOrderSeq.id }" />
    				<input type="hidden" id="tplorderseq-form-add-linkdatatable" name="printOrderSeq.tablename" value="${printOrderSeq.tablename }"/>
			    </form>
	    	</div>
      </div>
      <div data-options="region:'south',border:false">
          <div class="buttonDetailBG" style="height:30px;text-align:right;">
              <input type="button" name="savegoon" id="tplorderseq-save-addPrintOrderSeq" value="确定"/>
          </div>
      </div>
  </div>
    <script type="text/javascript">
    	$(function(){
    		$("#tplorderseq-PrintTempletOrderseq-form-code").widget({
	   			referwid:'RL_PRINT_TEMPLET_SUBJECT',
	   			singleSelect:true,
	   			width:'300',
	   			onSelect:function(data){
	   				if(data!=null){
		   				$("#tplorderseq-PrintTempletOrderseq-form-code-text").html(data.code); 
	   				}else{
		   				$("#tplorderseq-PrintTempletOrderseq-form-code-text").html("");   					
	   				}
	   			},
	   			onClear:function(){   	
	   				$("#tplorderseq-PrintTempletOrderseq-form-code-text").html("");        			
	   			}
	   		});
    		$("#tplorderseq-form-addPrintOrderSeq").form({
    			onSubmit: function(){
    				var flag = $(this).form('validate');
    				if(flag==false){
    					return false;
    				}
    			},
    			success:function(data){
    				//$.parseJSON()解析JSON字符串 
    				var json = $.parseJSON(data);
    				if(json.flag==true){
    					$.messager.alert("提醒","修改排序策略成功!");
    					$("#tplorderseq-dialog-printOrderSeqOper-content").dialog('close',true);
    					$("#tplorderseq-table-printOrderSeqList").datagrid('reload');
    				}
    				else{
    					$.messager.alert("提醒","修改排序策略!");
    				}
    			}
    		});
    		$("#tplorderseq-save-addPrintOrderSeq").click(function(){
    			$.messager.confirm("提醒","是否修改排序策略信息?",function(r){
    				if(r){
    					$("#tplorderseq-form-addPrintOrderSeq").submit();
    				}
    			});
    		});
    		
	   		
	   		$("#tplorderseq-form-showCreateDialog-btn").click(function(){
	   			var options={};
	   			options.tablesavetoid='tplorderseq-form-add-linkdatatable';
	   			options.seqsavetoid='tplresource-PrintTempletOrderseq-form-orderseq';
	   			options.initdata=$('#tplresource-PrintTempletOrderseq-form-orderseq').val() || "";
	   			options.seqnamesavetoid='tplresource-PrintTempletOrderseq-form-name';
	    		showPrintOrderSeqCreateDialog(options);
	    	 });
    	});
    </script>
  </body>
</html>
  