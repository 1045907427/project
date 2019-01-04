<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>打印模板代码修改</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'center',border:false">
	      <div align="center" style="padding: 10px;">
		    <form action="agprint/tplsubject/editPrintTempletSubject.do" method="post" id="tplsubjuect-form-editPrintTempletSubject">
		    	<table cellpadding="3" cellspacing="3" border="0">
			    	<tr>
			    		<td width="100px" align="right">模板代码:</td>
			    		<td align="left"><input type="text" name="printTempletSubject.code" readonly="readonly" value="${printTempletSubject.code }" class="easyui-validatebox" data-options="required:true" style="width:200px;"/></td>
			    	</tr>
			    	<tr>
			    		<td align="right">分类名称:</td>
			    		<td align="left">
			    			<input type="text" name="printTempletSubject.name" value="${printTempletSubject.name }" class="easyui-validatebox" required="true" style="width:200px;"/>
			    		</td>		    		
			    	</tr>
			    	<tr>
			    		<td align="right">排序:</td>
			    		<td align="left">
			    			<input type="text" name="printTempletSubject.seq" value="${printTempletSubject.seq }" class="easyui-validatebox" required="true" validType="validSeqInt['int']" style="width:200px;"/>
			    		</td>		    		
			    	</tr>
			    	<tr>
			    		<td align="right">模板选择方式:</td>
		    			<td align="left">
							<select name="printTempletSubject.uselinktype" id="tplsubject-form-add-uselinktype" style="width: 200px;">
								<option value="0" <c:if test="${printTempletSubject.uselinktype=='0' }">selected="selected"</c:if>>系统默认</option>
								<option value="1" <c:if test="${printTempletSubject.uselinktype=='1' }">selected="selected"</c:if>>使用关联</option>
								<option value="2" <c:if test="${printTempletSubject.uselinktype=='2' }">selected="selected"</c:if>>手动</option>
							</select>
		    			</td>
			    	</tr>
			    	<tr id="tplsubject-form-linktypeseq-tr" style="display:none">
			    		<td align="right">关联优先级:</td>
		    			<td align="left">	    				
			    			<input type="text" name="printTempletSubject.linktypeseq" id="tplsubject-form-linktypeseq-widget"  style="width:200px;" value="${printTempletSubject.linktypeseq }"/>			    			
			    			<div style="clear:both">
			    				<div style="line-height:25px;">
			    				显示结果：
			    				</div>
			    				<div id="tplsubject-form-linktypeseqname" style="clear:both;height:90px;width:200px;border:1px solid #ccc;overflow-x:hidden;overflow-y:auto;">
			    							    							    					
			    				</div>
			    				<div id="tplsubject-form-linktypeseqname-hidden-init" style="display:none;line-height:25px">${printTempletSubject.linktypeseqname }</div>
			    			</div>			    			
			    			<input type="hidden" id="tplsubject-form-linktypeseqname-hidden" name="printTempletSubject.linktypeseqname" value="${printTempletSubject.linktypeseqname }" />
		    			</td>
			    	</tr>
			    	<tr>
			    		<td align="right">状态:</td>
			    		<td align="left">
			    			<select disabled="disabled" style="width:200px;">
			    				<option value="1" <c:if test="${printTempletSubject.state=='1' }">selected="selected"</c:if> >有效</option>
			    				<option value="0" <c:if test="${printTempletSubject.state=='0' }">selected="selected"</c:if> >无效</option>
			    			</select>
			    			<div style="line-height:25px">
			    				状态会影响新模板新增，不影响旧模板使用。
			    			</div>
			    		</td>		    		
			    	</tr>
		    		<tr>
			    		<td align="right">备注:</td>
		    			<td align="left">
			    			<textarea rows="0" cols="0" name="printTempletSubject.remark" value="${printTempletSubject.remark }" style="width:200px;height:80px;" class="easyui-validatebox" validType="maxByteLength[500]">${printTempletSubject.remark }</textarea>
		    			</td>
			    	</tr>
				</table>
	      	</form>
	      </div>
      </div>
      <div data-options="region:'south',border:false">
          <div class="buttonDetailBG" style="height:30px;text-align:right;">
              <input type="button" name="savegoon" id="tplsubjuect-save-editPrintTempletSubject" value="确定"/>
          </div>
      </div>
  </div>
    <script type="text/javascript">
    	$(function(){
    		$("#tplsubjuect-form-editPrintTempletSubject").form({
    			onSubmit: function(){
    				var flag = $(this).form('validate');
    				if(flag==false){
    					return false;
    				}
    			},
    			success: function(data){
    				var json=$.parseJSON(data);
    				if(json.flag == true){
    					$.messager.alert("提醒","修改成功！");
    					$("#tplsubject-dialog-printTempletSubjectOper-content").dialog('close',true);
    					$("#tplsubject-table-printTempletSubjectList").datagrid('reload');
    					try{
    						showListPagePrintTempletSubjectWidget();
    					}catch(e){
    						
    					}
    				}else{
    					if(json.msg!=null){
    						$.messager.alert("提醒","修改失败,"+json.msg);    						
    					}else{
    						$.messager.alert("提醒","修改失败！");
    					}
    				}
    			}
    		});
    		$("#tplsubjuect-save-editPrintTempletSubject").click(function(){
    			$.messager.confirm("提醒","是否修改打印模板代码信息?", function(r){
    				if(r){
    					$("#tplsubjuect-form-editPrintTempletSubject").submit();
    				}
    			});
    		});

			$("#tplsubject-form-add-uselinktype").bind("change",function(){
				var val=$(this).val() || 0;
				if(val==1){
					$("#tplsubject-form-linktypeseq-tr").show();
					$("#tplsubject-form-linktypeseq-widget").validatebox({required: true});
					$("#tplsubject-form-linktypeseq-widget").validatebox('validate');
				}else{
					$("#tplsubject-form-linktypeseq-tr").hide();
					$("#tplsubject-form-linktypeseq-widget").widget("clear");
					$("#tplsubject-form-linktypeseq-widget").validatebox({required: false});
					$("#tplsubject-form-linktypeseq-widget").validatebox('validate');
				}
			});
    		
    		$("#tplsubject-form-linktypeseq-widget").widget({
	   			referwid:'RL_T_SYS_CODE_ENABLE',
	   			singleSelect:false,
	   			onlyLeafCheck:false,
	   			width:'200',
	   			view:true,
	   			param:[
	   			       {field:'type',op:'equal',value:'printTempletLinkType'}
	   			],
	   			onSelect:function(data){
	   				var val=$("#tplsubject-form-linktypeseq-widget").widget("getText");
	   				
	   				var valarr=val.split(',');
	   				var htmlsb=new Array();
	   				for(var i=0;i<valarr.length;i++){
	   					if(htmlsb.length>0){
	   						htmlsb.push("<br/>");
	   					}
	   					htmlsb.push(i+1);
	   					htmlsb.push(")");
    					htmlsb.push(valarr[i]);
	   				}
					$("#tplsubject-form-linktypeseqname").html(htmlsb.join(''));
					$("#tplsubject-form-linktypeseqname-hidden").val(val);
	   			},
	   			onClear:function(){
					$("#tplsubject-form-linktypeseqname").html("");
					$("#tplsubject-form-linktypeseqname-hidden").val("");
	   			}
	   		});
    		
    		showInitPageData();
    	});
    	function showInitPageData(){
			$("#tplsubject-form-add-uselinktype").trigger("change");

    		var typseqname=$("#tplsubject-form-linktypeseqname-hidden-init").html() || "";
    		typseqname=$.trim(typseqname);
    		if(typseqname!=""){
	    		var typseqnamearr=typseqname.split(',');
				var htmlsb=new Array();
				for(var i=0;i<typseqnamearr.length;i++){
					if(htmlsb.length>0){
						htmlsb.push("<br/>");
					}
					htmlsb.push(i+1);
					htmlsb.push(")");
					htmlsb.push(typseqnamearr[i]);
				}
				$("#tplsubject-form-linktypeseqname").html(htmlsb.join(''));
    		}
    	}
    </script>
  </body>
</html>
