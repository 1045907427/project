<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>打印模板代码查看</title>
  </head>
  
  <body>
  		<div align="center" style="padding: 10px;">
	    	<table cellpadding="3" cellspacing="3" border="0">
		    	<tr>
		    		<td width="100px" align="right">模板代码:</td>
		    		<td align="left">
	    				<input type="text" name="printTempletSubject.code" readonly="readonly" value="${printTempletSubject.code }" class="easyui-validatebox" required="true" style="width:200px;"/>
	    			</td>
	    		</tr>
	    		<tr>
		    		<td width="100px" align="right">分类名称:</td>
		    		<td align="left">
	    				<input type="text" name="printTempletSubject.name" readonly="readonly" value="${printTempletSubject.name }" class="easyui-validatebox" required="true" style="width:200px;"/>
	    			</td>
	    		</tr>
	    		<tr>
		    		<td width="100px" align="right">排序:</td>
		    		<td align="left">
	    				<input type="text" name="printTempletSubject.seq" readonly="readonly" value="${printTempletSubject.seq }" class="easyui-validatebox" required="true" style="width:200px;"/>
	    			</td>
	    		</tr>
				<tr>
					<td align="right">模板选择方式:</td>
					<td align="left">
						<select name="printTempletSubject.uselinktype" id="tplsubject-form-add-uselinktype" style="width: 200px;" disabled="disabled">
							<option value="0" <c:if test="${printTempletSubject.uselinktype=='0' }">selected="selected"</c:if>>系统默认</option>
							<option value="1" <c:if test="${printTempletSubject.uselinktype=='1' }">selected="selected"</c:if>>使用关联</option>
							<option value="2" <c:if test="${printTempletSubject.uselinktype=='2' }">selected="selected"</c:if>>手动</option>
						</select>
					</td>
				</tr>
				<c:if test="${printTempletSubject.uselinktype=='1' }">
		    	<tr>
		    		<td align="right">关联优先级:</td>
	    			<td align="left">
	    				<div>
		    				<div id="tplsubject-form-linktypeseqname" style="clear:both;height:90px;width:200px;border:1px solid #ccc;overflow-x:hidden;overflow-y:auto;">
		    							    							    					
		    				</div>
		    				<div id="tplsubject-form-linktypeseqname-hidden-init" style="display:none;line-height:25px">${printTempletSubject.linktypeseqname }</div>
		    			</div>			    
	    			</td>
		    	</tr>
				</c:if>
	    		<tr>
		    		<td width="100px" align="right">状态:</td>
		    		<td align="left">    				
		    			<select name="printTempletSubject.state" style="width:200px;" disabled="disabled">
		    				<option value="1" <c:if test="${printTempletSubject.state=='1' }">selected="selected"</c:if> >有效</option>
		    				<option value="0" <c:if test="${printTempletSubject.state=='0' }">selected="selected"</c:if> >无效</option>
		    			</select>
	    			</td>
	    		</tr>
	    		<tr>
		    		<td width="100px" align="right">备注:</td>
		    		<td align="left">    				
		    			<textarea rows="0" cols="0" name="printTempletSubject.remark" style="width:200px;height:80px;" readonly="readonly">${printTempletSubject.remark }</textarea>
	    			</td>
	    		</tr>
	    	</table>
  		</div>
  		<script type="text/javascript">
  			$(document).ready(function(){
  				showInitPageData();
  			});
  			function showInitPageData(){
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
  	    		}else{
  	    			$("#tplsubject-form-linktypeseqname").html("无");
  	    		}
  			}
  		</script>
  </body>
</html>
