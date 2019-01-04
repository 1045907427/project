<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>品牌进场明细编辑页面</title>
	<%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
	  		<form id="oa-form-oacustomerBrandEditPage">
		  		<input type="hidden" id="oa-brandid-oacustomerBrandEditPage" name="brandid"/>
		  		<input type="hidden" id="oa-barcodenum-oacustomerBrandEditPage" name="barcodenum"/>
		  		<input type="hidden" id="oa-brandname-oacustomerBrandEditPage" name="brandname"/>
		
		  		<table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
		  			<tr>
		  				<td class="right len130">选择品牌：</td>
		  				<td class="len160"><input class="easyui-validatebox len150" name="name" id="oa-name-oacustomerBrandEditPage" disabled="disabled" autocomplete="off"/></td>
						<td colspan="3">
							<span id="oa-idtip-oacustomerBrandEditPage" style="margin-left:8px;line-height:23px;color:green;"></span>
						</td>
		  			</tr>
		  			<tr>
		  				<td class="right len130">单品实际进场：</td>
		  				<td class="len160"><input class="easyui-validatebox easyui-numberbox len150" name="realnum" id="oa-realnum-oacustomerBrandEditPage" data-options="min:0" value="${param.realnum }" autocomplete="off"/></td>
		  				<td class="right len90">陈列组数：</td>
		  				<td class="len100"><input class="easyui-validatebox easyui-numberbox len80" name="displaynum" id="oa-displaynum-oacustomerBrandEditPage" data-options="min:0" value="${param.displaynum }" autocomplete="off"/></td>
		  				<td class="right len90">分摊费用：</td>
		  				<td class="len100"><input class="easyui-validatebox easyui-numberbox len80" name="cost" id="oa-cost-oacustomerBrandEditPage" data-options="min:0, precision:2" value="${param.cost }" autocomplete="off"/></td>
		  			</tr>
		  		</table>
	  		</form>
	  	</div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="text-align:right;">
  				快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
	  			<input type="button" value="确定" name="savegoon" id="oa-savegoon-oacustomerBrandEditPage" />
  			</div>
  		</div>
  	</div>
  	<script type="text/javascript">


    <!--
    $.parser.onComplete = function(context){

        $("#oa-realnum-oacustomerBrandEditPage").textbox('textbox').focus();

        $("#oa-realnum-oacustomerBrandEditPage").textbox('textbox').keydown(function(event){
            //enter
            if(event.keyCode==13){
                $("#oa-realnum-oacustomerBrandEditPage").textbox('textbox').blur();
                $("#oa-displaynum-oacustomerBrandEditPage").textbox('textbox').focus();
            }
        });

        $("#oa-displaynum-oacustomerBrandEditPage").textbox('textbox').keydown(function(event){
            //enter
            if(event.keyCode==13){
                $("#oa-displaynum-oacustomerBrandEditPage").textbox('textbox').blur();
                $("#oa-cost-oacustomerBrandEditPage").textbox('textbox').focus();
            }
        });

        $("#oa-cost-oacustomerBrandEditPage").textbox('textbox').keydown(function(event){
            //enter
            if(event.keyCode==13){
                $("#oa-cost-oacustomerBrandEditPage").textbox('textbox').blur();
                editBrand(false);
            }
        });

        $("#oa-savegoon-oacustomerBrandEditPage").click(function(){
            editBrand(false);
        });

    };
    -->

  	</script>
  </body>
</html>