<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>模板信息查看</title>
    <%@include file="/include.jsp" %>
  </head> 
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center">
	    		<form action="" method="post" id="printTemplet-form-view" style="padding: 10px;">
	    			<table cellpadding="3" cellspacing="3" border="0">
	    				<tr>	    			
		   					<td width="90px" align="right">模板代码:</td>
		   					<td align="left">
		    					<div style="line-height:25px;" >${printTemplet.code }</div>
		    					<div style="line-height:25px;" >${codename }</div>
		   					</td>
		 				</tr>
	    				<tr>
	    					<td align="right">模板描述名:</td>
	    					<td align="left">
	    						<input type="text" name="printTemplet.name" value="${printTemplet.name }" style="width:200px;" readonly="readonly"/>
	    					</td>
	    				</tr>
						<tr>
							<td align="right">公司抬头:</td>
							<td align="left">
								<input type="text" name="printTemplet.companytitle" value="${printTemplet.companytitle}" readonly="readonly" validType="maxByteLength[100]" style="width:200px;"/>
								<a href="javaScript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-help'" title="帮助" onclick="javascript:showHelpDialog('companytitle');"> </a>
							</td>
						</tr>
	    				<tr>
	    					<td align="right">模板标识:</td>
	    					<td align="left"><input type="text" name="printTemplet.mark" value="${printTemplet.mark }" readonly="readonly" style="width:200px;"/></td>
	    				</tr>
	    				<tr>
	    					<td width="90px" align="right">模板资源:</td>
	    					<td align="left">
	    						<div id="printTemplet-form-tplresourceid-widget-uidiv">${tplresourcename }</div>
	    						<div style="line-height:25px;" id="printTemplet-templetfile-upload-result">模板文件(jasper):${printTemplet.templetfile }</div>
	    						<div style="line-height:25px;" id="printTemplet-sourcefile-upload-result">模板源文件(jrxml):${printTemplet.sourcefile }</div>
	    					</td>
	    				</tr>
	    				<tr>
	    					<td width="90px" align="right">内容排序策略:</td>
	    					<td align="left">
	    						<div id="printTemplet-form-tplorderseq-widget-uidiv">${tplorderseqname }</div>
	    					</td>
	    				</tr>
						<tr>
							<td align="right">纸张:</td>
							<td align="left">
								<div id="printTemplet-form-papaersizeid-widget-uidiv">${papersizename }</div>
							</td>
						</tr>
	    				<tr>
	    					<td align="right">顺序:</td>
	    					<td align="left"><input type="text" name="printTemplet.seq" value="${printTemplet.seq }" readonly="readonly" style="width:200px;"/></td>
	    				</tr>
	    				<tr>
	    					<td align="right">是否关联:</td>
	    					<td align="left">
	    						<input id="printTemplet-form-add-linktype-widget" name="printTemplet.linktype" readonly="readonly"  style="width:200px;" />
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
								<select disabled="disabled" style="width:200px">
									<option value="1" <c:if test="${printTemplet.lodophtmlmodel=='1' }">selected="selected"</c:if>>普通模式</option>
									<option value="2" <c:if test="${printTemplet.lodophtmlmodel=='2' }">selected="selected"</c:if>>图形模式</option>
								</select>
								<a href="javaScript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-help'" title="帮助" onclick="javascript:showPrintTempletHelpDialog('lodophtmlmodel');"> </a>
							</td>
						</tr>
	    				<tr>
	    					<td align="right">备注:</td>
	    					<td align="left">	    					
				    			<textarea name="printTemplet.remark" cols="0" rows="0" style="width:200px;height:50px;" disabled="disabled">${printTemplet.remark }</textarea>
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
	    		</form>
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
    	$(function(){
	   		showInitWidgetData();
    	});
    	function showInitWidgetData(){
   		
    		var linktype=$.trim("${printTemplet.linktype}");
    		var linkdata=$.trim("${printTemplet.linkdata}");
    		showPageLinkTypeWidget(true,linkdata);
    		if(linktype!=""){
	    		$("#printTemplet-form-add-linktype-widget").widget('setValue',linktype);
	    	}else{
	    		$("#printTemplet-form-add-linktype-widget").widget('clear');
	    	}
	    	var code=$.trim("${printTemplet.code}") || "";
    		showCountPerPageTr(code);
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
   			readonly:true,
   			initValue:initValue
   		});
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
   			readonly:true,
   			initValue:initValue
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
   			readonly:true,
   			initValue:initValue
   		});
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
   			readonly:true,
   			initValue:initValue
   		});
   	}
        function showCountPerPageTr(code){
            $("#printTemplet-form-papercount-tr").hide();
            if(code=="sales_invoice" || code == "sales_invoicebill"){
                $("#printTemplet-form-papercount-tr").show();
            }
        }
    </script>
  </body>
</html>
