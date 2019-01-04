<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>品牌进场明细添加页面</title>
	<%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
	  		<form id="oa-form-oacustomerBrandAddPage">
		  		<input type="hidden" id="oa-brandid-oacustomerBrandAddPage" name="brandid"/>
		  		<input type="hidden" id="oa-barcodenum-oacustomerBrandAddPage" name="barcodenum"/>
		  		<input type="hidden" id="oa-brandname-oacustomerBrandAddPage" name="brandname"/>
		
		  		<table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
		  			<tr>
		  				<td class="right len130">选择品牌：</td>
		  				<td class="len160"><input class="easyui-validatebox len150" name="name" id="oa-name-oacustomerBrandAddPage" autocomplete="off"/></td>
						<td colspan="3">
							<span id="oa-idtip-oacustomerBrandAddPage" style="margin-left:8px;line-height:23px;color:green;"></span>
						</td>
		  			</tr>
		  			<tr>
		  				<td class="right len130">单品实际进场：</td>
		  				<td class="len160"><input class="easyui-validatebox easyui-numberbox len150" name="realnum" id="oa-realnum-oacustomerBrandAddPage" data-options="min:0" autocomplete="off"/></td>
		  				<td class="right len90">陈列组数：</td>
		  				<td class="len100"><input class="easyui-validatebox easyui-numberbox len80" name="displaynum" id="oa-displaynum-oacustomerBrandAddPage" data-options="min:0" autocomplete="off"/></td>
		  				<td class="right len90">分摊费用：</td>
		  				<td class="len100"><input class="easyui-validatebox easyui-numberbox len80" name="cost" id="oa-cost-oacustomerBrandAddPage" data-options="min:0, precision:2" autocomplete="off"/></td>
		  			</tr>
		  		</table>
	  		</form>
	  	</div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="text-align:right;">
  				快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
	  			<input type="button" value="继续添加" name="savegoon" id="oa-savegoon-oacustomerBrandAddPage" />
  			</div>
  		</div>
  	</div>
  	<script type="text/javascript">


        $.parser.onComplete = function(context){

            $('#oa-name-oacustomerBrandAddPage').widget({
                name: 't_oa_customer_brand',
                col: 'brandid',
                singleSelect: true,
                required:true,
                width: 150,
                onSelect: function(data){

                    $("#oa-idtip-oacustomerBrandAddPage").addClass("img-loading");
                    $.ajax({
                        url:'oa/getBarcodenum.do',
                        dataType:'json',
                        type:'post',
                        data:'brand='+ data.id,
                        success:function(json){

                            $('#oa-brandid-oacustomerBrandAddPage').val(data.id);
                            $('#oa-brandname-oacustomerBrandAddPage').val(data.name);
                            $('#oa-barcodenum-oacustomerBrandAddPage').val(json.barcodenum);
                            $("#oa-idtip-oacustomerBrandAddPage").removeClass("img-loading").html("商品编码："+ data.id + "&nbsp;&nbsp;&nbsp;品牌商品数：" + json.barcodenum);
                        }
                    });

                    $('#oa-realnum-oacustomerBrandAddPage').textbox('textbox').focus();
                }
            });

            $("#oa-realnum-oacustomerBrandAddPage").textbox('textbox').keydown(function(event){
                //enter
                if(event.keyCode==13){
                    $("#oa-realnum-oacustomerBrandAddPage").numberbox('textbox').blur();
                    $("#oa-displaynum-oacustomerBrandAddPage").numberbox('textbox').focus();
                }
            });

            $("#oa-displaynum-oacustomerBrandAddPage").textbox('textbox').keydown(function(event){
                //enter
                if(event.keyCode==13){
                    $("#oa-displaynum-oacustomerBrandAddPage").textbox('textbox').blur();
                    $("#oa-cost-oacustomerBrandAddPage").textbox('textbox').focus();
                }
            });

            $("#oa-cost-oacustomerBrandAddPage").textbox('textbox').keydown(function(event){
                //enter
                if(event.keyCode==13){
                    $("#oa-cost-oacustomerBrandAddPage").textbox('textbox').blur();
                    addBrand(true);
                }
            });

            $("#oa-savegoon-oacustomerBrandAddPage").click(function(){
                addBrand(true);
            });
        };

  	</script>
  </body>
</html>