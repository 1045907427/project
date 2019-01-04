<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
     <div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
		  	<form id="purchase-form-limitPriceOrderDetailEditPage" method="post">
		  		<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="text-align: right;">商品：</td>
						<td>
							<input type="text" id="purchase-limitPriceOrderDetail-goodsname" name="name" readonly="readonly" style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;"/>
							<input type="hidden" id="purchase-limitPriceOrderDetail-goodsid" name="goodsid"/>					
						</td>				
						<td style="text-align: right;">条形码：</td>
						<td><input type="text" id="purchase-limitPriceOrderDetail-barcode" name="barcode" readonly="readonly" style="width:150px;  border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>				
					</tr>
					<tr>
						<td style="text-align: right;">调整前采购价：</td>
						<td><input type="text" id="purchase-limitPriceOrderDetail-priceasfound" name="priceasfound" class="easyui-numberbox" data-options="precision:6" readonly="readonly" style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
						<td style="text-align: right;">调整后采购价：</td>
						<td><input type="text" id="purchase-limitPriceOrderDetail-priceasleft" name="priceasleft" style="width:150px;"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">商品品牌：</td>
						<td><input type="text" id="purchase-limitPriceOrderDetail-brand" name="brandName" readonly="readonly" style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
						<td style="text-align: right;">商品规格：</td>
						<td><input type="text" id="purchase-limitPriceOrderDetail-model" name="model" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">备注：</td>
						<td colspan="3">
							<input type="text" id="purchase-limitPriceOrderDetail-remark" name="remark" style="width:420px;" tabindex="4"/>
						</td>
					</tr>
				</table>
				
				<input type="hidden" id="purchase-limitPriceOrderDetail-id" name="id" />
				<input type="hidden" name="goodsfield01" />
				<input type="hidden" name="goodsfield02" />
				<input type="hidden" name="goodsfield03" />
				<input type="hidden" name="goodsfield04" />
				<input type="hidden" name="goodsfield05" />
				<input type="hidden" name="goodsfield06" />
				<input type="hidden" name="goodsfield07" />
				<input type="hidden" name="goodsfield08" />
				<input type="hidden" name="goodsfield09" />
		  	</form>
  		</div>  		
	  	<div data-options="region:'south',border:false">
  			<div class="buttonBG" style="height:26px;text-align:right;">
	  			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span><input type="button" value="确定" name="savenogo" id="purchase-limitPriceOrderDetailEditPage-editSave" />
  			</div>
	  	</div>
  	</div>
  	<script type="text/javascript">
	  	function showAuxunitNameInfo(){
	  		var goodsid = $("#purchase-limitPriceOrderDetail-goodsid").val();
	  		if(goodsid==null || goodsid==""){
	  	  		return false;
	  		}
	  		var auxunitid=$("#purchase-limitPriceOrderDetail-auxunitid").val();

	  		try{
				$.ajax({   
	            url :'purchase/common/showAuxAndUnitNameInfo.do',
	            type:'post',
	            dataType:'json',
		        async: false,
	            data:{goodsid:goodsid,auxunitid:auxunitid},
	            success:function(json){
		            if(json && json.auxunitname){
		            	$("#purchase-limitPriceOrderDetail-auxunitname").val(json.auxunitname);
		            }
	            }
	        });
	  		}catch(e){
	  		}
		}
	  	function saveOrderDetail(isGoOn){
	    	$("#purchase-limitPriceOrderDetail-remark").focus();
  			var flag=$("#purchase-form-limitPriceOrderDetailEditPage").form('validate');
  			if(!flag){
	  			return false;
  			}
			var formdata=$("#purchase-form-limitPriceOrderDetailEditPage").serializeJSON();
			if(formdata){
				$("#purchase-limitPriceOrderAddPage-limitPriceOrdertable").datagrid('updateRow',{
					index:editRowIndex,
					row:formdata
				});
			}
			$("#purchase-limitPriceOrderAddPage-dialog-DetailOper-content").dialog("close");			
  		}
  		$(document).ready(function(){
  			$("#purchase-limitPriceOrderDetail-priceasleft").numberbox({
				required:true,
				precision:6,
				groupSeparator:',',
				onChange:function(newValue,oldValue){
	  				var priceasfound= $("#purchase-limitPriceOrderDetail-priceasfound").numberbox('getValue');
					if(null==priceasfound){
						priceasfound=0;
					}
					if(newValue < 0){
						$(this).numberbox('clear');
					}
					if(newValue>priceasfound && priceasfound>0){
						var rat=Math.floor(newValue/priceasfound);
						if(rat>=10){
		  		        	$.messager.alert("提醒","调整后采购价与调整前采购价已经相差"+rat+"倍！");
						}
					}
				}
			}); 
	  		$("#purchase-limitPriceOrderDetailEditPage-editSave").click(function(){
	  			saveOrderDetail();		  			
	  		});
	  		$("#purchase-limitPriceOrderDetail-priceasleft").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#purchase-limitPriceOrderDetail-priceasleft").blur();
		   			$("#purchase-limitPriceOrderDetail-remark").focus();
		   			$("#purchase-limitPriceOrderDetail-remark").select();
				}
		    });
	  		$("#purchase-limitPriceOrderDetail-remark").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#purchase-limitPriceOrderDetail-remark").blur();
		   			$("#purchase-limitPriceOrderDetailEditPage-editSave").focus();
				}
		    });
	  		$("#purchase-limitPriceOrderDetailEditPage-editSave").die("keydown").live("keydown",function(event){
	  			if(event.keyCode==13){
	  				saveOrderDetail();
	  			}
	  		});
  		});
  	</script>
  </body>
</html>
