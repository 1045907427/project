<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>特价调整单明细新增页面</title>
  </head>
  <body>
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
		  	<form id="sales-form-offpriceDetailAddPage">
			    <table cellpadding="5" cellspacing="5">
			    	<tr>
			    		<td class="len80">商品名称：</td>
			    		<td colspan="3" class="textalignleft">
			    			<input type="text" id="sales-offprice-goodsname" style="width:300px;" readonly="readonly"/>
			    			<input type="hidden" name="goodsid" />
			    			<span id="sales-idtip-offpriceDetailAddPage" style="margin-left:8px;line-height:23px;color:green;"></span>
			    		</td>
			    	</tr>
			    	<tr>
			    		<td>数量区间：</td>
			    		<td class="textalignleft">
			    			<input id="sales-lownum-offpriceDetailAddPage" name="lownum" style="width:55px"/>
			    			<span>-</span>
			    			<input id="sales-upnum-offpriceDetailAddPage" name="upnum" style="width:55px"/>
			    			<span class="auxUnitName"></span>
			    		</td>
			    		<td class="len80">特价：</td>
			    		<td class="textalignleft"><input id="sales-offprice-offprice" class="len150 easyui-validatebox" name="offprice" data-options="required:true" /> </td>
			    	</tr>
			    	<tr>
			    		<td>商品品牌：</td>
			    		<td class="textalignleft"><input id="sales-offprice-brandname" readonly="readonly" class="len150 readonly" /></td>
			    		<td>原价：</td>
			    		<td class="textalignleft"><input class="len150 readonly" readonly="readonly" name="oldprice" /></td>
			    	</tr>
			    	<tr>
			    		<td>条形码：</td>
			    		<td class="textalignleft"><input id="sales-offprice-barcode" class="len150 readonly" readonly="readonly" /></td>
			    		<td>单位：</td>
			    		<td class="textalignleft"><input id="sales-offprice-unitname" readonly="readonly" class="len150 readonly" /></td>
			    	</tr>
			    	<tr>
			    		<td>备注：</td>
			    		<td colspan="3" class="textalignleft"><input id="sales-remark-offpriceDetailAddPage" type="text" style="width:410px;" name="remark" /></td>
			    	</tr>
			    </table>
		    </form>
  		</div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="height:30px;text-align:right;">
  				快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
	  			<input type="button" value="确 定" name="savegoon" id="sales-savegoon-offpriceDetailEditPage" />
  			</div>
  		</div>
  	</div>
    <script type="text/javascript">
    	$(function(){
            $("#sales-lownum-offpriceDetailAddPage").numberbox({
                min:0,
				precision:general_bill_decimallen
            });

            $("#sales-upnum-offpriceDetailAddPage").numberbox({
                min:1,
				precision:general_bill_decimallen
            });

    		$("#sales-savegoon-offpriceDetailEditPage").click(function(){
    			editSaveDetail(true);
    		});
            getNumberBox("sales-lownum-offpriceDetailAddPage").bind('keydown', function(e){
    			if(e.keyCode==13){
		   			getNumberBox("sales-upnum-offpriceDetailAddPage").focus();
                    getNumberBox("sales-upnum-offpriceDetailAddPage").select();
				}
    		});
            getNumberBox("sales-upnum-offpriceDetailAddPage").bind('keydown', function(e){
    			if(e.keyCode==13){
		   			$("#sales-offprice-offprice").focus();
		   			$("#sales-offprice-offprice").select();
				}
    		});
    		$("#sales-offprice-offprice").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#sales-remark-offpriceDetailAddPage").focus();
		   			$("#sales-remark-offpriceDetailAddPage").select();
				}
			});
			$("#sales-remark-offpriceDetailAddPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#sales-savegoon-offpriceDetailEditPage").focus();
				}
			});
			$("#sales-savegoon-offpriceDetailEditPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					editSaveDetail(true);
				}
			});
    	});
    </script>
  </body>
</html>
