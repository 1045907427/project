<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代码添加</title>
  </head> 
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'center',border:false">
	      <div align="center" style="padding: 10px;">
		    <form action="agprint/tplsubject/addPrintTempletSubject.do" method="post" id="tplsubjuect-form-addPrintTempletSubject">
		    	<table cellpadding="3" cellspacing="3" border="0">
			    	<tr>
			    		<td width="100px" align="right">模板代码:</td>
		    			<td align="left">
		    				<input type="text" id="code" name="printTempletSubject.code" class="easyui-validatebox" data-options="required:true,validType:['validPrintTemletSubjectCode','maxByteLength[255]']" style="width:200px;"/>
		    			</td>	  
			    	</tr>
			    	<tr>
			    		<td align="right">分类名称:</td>
		    			<td align="left">
		    				<input type="text" name="printTempletSubject.name" class="easyui-validatebox" required="true" style="width:200px;"/>
		    			</td>
			    	</tr>
			    	<tr>
			    		<td align="right">排序:</td>
		    			<td align="left">
		    				<input type="text" name="printTempletSubject.seq" class="easyui-validatebox" validType="validSeqInt['int']" required="true" style="width:200px;"/>
		    			</td>
			    	</tr>
			    	<tr>
			    		<td align="right">模板选择方式:</td>
		    			<td align="left">
							<select name="printTempletSubject.uselinktype" id="tplsubject-form-add-uselinktype" style="width: 200px;">
								<option value="0">系统默认</option>
								<option value="1">使用关联</option>
								<option value="2">手动</option>
							</select>
		    			</td>
			    	</tr>
			    	<tr id="tplsubject-form-linktypeseq-tr" style="display:none">
			    		<td align="right">关联优先级:</td>
		    			<td align="left">	    				
			    			<input type="text" name="printTempletSubject.linktypeseq" id="tplsubject-form-linktypeseq-widget"  style="width:200px;" />
			    			<div style="clear:both">
			    				<div style="line-height:25px;">
			    				显示结果：
			    				</div>
			    				<div id="tplsubject-form-linktypeseqname" style="clear:both;height:90px;width:200px;border:1px solid #ccc;overflow-x:hidden;overflow-y:auto;">
			    							    							    					
			    				</div>
			    			</div>
			    			<input id="tplsubject-form-linktypeseqname-hidden" type="hidden" name="printTempletSubject.linktypeseqname" />
		    			</td>
			    	</tr>
			    	<tr>
			    		<td align="right">状态:</td>
		    			<td align="left">	    				
			    			<select name="printTempletSubject.state" style="width:200px;">
			    				<option value="1">有效</option>
			    				<option value="0">无效</option>
			    			</select>
			    			<div style="line-height:25px">
			    				状态会影响新模板新增，不影响旧模板使用。
			    			</div>
		    			</td>
			    	</tr>
			    	<tr>
			    		<td align="right">备注:</td>
		    			<td align="left">
			    			<textarea rows="0" cols="0" name="printTempletSubject.remark" style="width:200px;height:80px;" class="easyui-validatebox" validType="maxByteLength[500]"></textarea>
		    			</td>
			    	</tr>
			    </table>
		    </form>
	      </div>
      </div>
      <div data-options="region:'south',border:false">
          <div class="buttonDetailBG" style="height:30px;text-align:right;">
              <input type="button" name="savegoon" id="tplsubjuect-save-addPrintTempletSubject" value="确定"/>
          </div>
      </div>
  </div>
    <script type="text/javascript">
    	$(function(){
    		$("#tplsubjuect-form-addPrintTempletSubject").form({
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
    					$.messager.alert("提醒","添加成功!");
    					$("#tplsubject-dialog-printTempletSubjectOper-content").dialog('close',true);
    					$("#tplsubject-table-printTempletSubjectList").datagrid('reload');
    					try{
    						showListPagePrintTempletSubjectWidget();
    					}catch(e){
    						
    					}
    				}
    				else{
    					if(json.msg!=null){
    						$.messager.alert("提醒","添加失败,"+json.msg);
    					}else{
    						$.messager.alert("提醒","添加失败!");
    					}
    				}
    			}
    		});
    		$("#tplsubjuect-save-addPrintTempletSubject").click(function(){
    			$.messager.confirm("提醒","是否添加打印模板代码信息?",function(r){
    				if(r){
    					$("#tplsubjuect-form-addPrintTempletSubject").submit();
    				}
    			});
    		});
    		
    		$("#tplsubject-form-add-uselinktype").change(function(){
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
	   			param:[
	   			       {field:'type',op:'equal',value:'printTempletLinkType'}
	   			],
	   			onSelect:function(){
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
    	});
    </script>
  </body>
</html>
