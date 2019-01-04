<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
		  	<form id="purchase-form-limitPriceOrderDetailAddPage" method="post">
		  		<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="text-align: right;">商品：</td>
						<td><input type="text" id="purchase-limitPriceOrderDetail-goodsid" name="goodsid" style="width:150px;" tabindex="1"/>
							<input type="hidden" id="purchase-limitPriceOrderDetail-goodsname" name="name"/>
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
						<td style="text-align: right;">主单位：</td>
						<td><input type="text" id="purchase-limitPriceOrderDetail-unitname" name="unitname" readonly="readonly"  style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;"/><input type="hidden" id="purchase-limitPriceOrderDetail-unitid" name="unitid"  style="width:150px;"/></td>				
						<td style="text-align: right;">辅计量：</td>
						<td><input type="text" id="purchase-limitPriceOrderDetail-auxunitname" name="auxunitname" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/><input type="hidden" id="purchase-limitPriceOrderDetail-auxunitid" name="auxunitid"  style="width:150px;"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">备注：</td>
						<td colspan="3">
							<input type="text"id="purchase-limitPriceOrderDetail-remark" name="remark" style="width:420px;" tabindex="4"/>
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
	  			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span><input type="button" value="继续添加" name="savegoon" id="purchase-limitPriceOrderDetailAddPage-addSaveGoOn" />
	  			<input type="button" value="确定" name="savenogo" id="purchase-limitPriceOrderDetailAddPage-addSave" />
  			</div>
	  	</div>
  	</div>
  	<script type="text/javascript">
		var selectedGoodsid=getFilterGoodsid();
		
		function getFilterGoodsid(){
			var tmpgoodsid=[];
			var goodsids="";
			var selectDetailRow=[];
	  		if($("#purchase-limitPriceOrderAddPage-limitPriceOrdertable").size()>0){
	  			selectDetailRow=$("#purchase-limitPriceOrderAddPage-limitPriceOrdertable").datagrid('getRows');
	  		}
	  		for(var i=0; i<selectDetailRow.length; i++){
	   			var rowJson = selectDetailRow[i];
	   			if(rowJson.goodsid != undefined && rowJson.goodsid!=""){
		   				tmpgoodsid.push(rowJson.goodsid);
	   			}
	   		}
	   		if(tmpgoodsid.length>0){
	   			goodsids=tmpgoodsid.join(',');
	   		}
	   		return goodsids;
		}
  		function showAuxunitNameInfo(){
	  		var goodsid = $("#purchase-limitPriceOrderDetail-goodsid").goodsWidget('getValue');
	  		if(goodsid==null || goodsid==""){
	  	  		return false;
	  		}
	  		var unitid=$("#purchase-limitPriceOrderDetail-unitid").val();
	  		try{
  			$.ajax({   
	            url :'purchase/common/showAuxAndUnitNameInfo.do',
	            type:'post',
	            dataType:'json',
		        async: false,
	            data:{goodsid:goodsid,unitid:unitid},
	            success:function(json){
		            if(json){
			            $("#purchase-limitPriceOrderDetail-auxunitid").val(json.auxunitid);
		            	$("#purchase-limitPriceOrderDetail-auxunitname").val(json.auxunitname);
		            	$("#purchase-limitPriceOrderDetail-unitname").val(json.unitname);
		            }
	            }
	        });
	  		}catch(e){
	  		}
		}
  		function orderDetailAddSaveGoOnDialog(){
  			var $DetailOper=$("#purchase-limitPriceOrderAddPage-dialog-DetailOper-content");
  	  		if($DetailOper.size()>0){
  				$DetailOper.dialog('close');
  	  		}
  		   	$('<div id="purchase-limitPriceOrderAddPage-dialog-DetailOper-content"></div>').appendTo('#purchase-returnOrderAddPage-dialog-DetailOper');
  	  		$DetailOper=$("#purchase-limitPriceOrderAddPage-dialog-DetailOper-content");
  	  		
  			$DetailOper.dialog({
  				title:'商品信息新增(按ESC退出)',
  			    width: 600,  
  			    height: 350,
  			    closed: true,  
  			    cache: false, 
  			    modal: true,
  			    maximizable:true,
  			    href:"purchase/limitpriceorder/limitPriceOrderDetailAddPage.do",
			    onLoad:function(){
		    		$("#purchase-limitPriceOrderDetail-goodsid").focus();
	    		},
			    onClose:function(){
		            $DetailOper.dialog("destroy");
		        }
  			});
  			$DetailOper.dialog("open");
  		}
  		function saveOrderDetail(isGoOn){
	    	$("#purchase-limitPriceOrderDetail-remark").focus();
  			var flag=$("#purchase-form-limitPriceOrderDetailAddPage").form('validate');
  			if(!flag){
	  			return false;
  			}
  			var goodsid=$("#purchase-limitPriceOrderDetail-goodsid").goodsWidget('getValue');
  			if(goodsid==null || goodsid==""){
				$.messager.alert("提醒","抱歉，请选择商品！");
				return false;		  			
  			}
  			if(!checkAfterAddGoods(goodsid)){
				$.messager.alert("提醒","抱歉，采购调价单中已经存在该商品！");
				return false;
			}
  			if(checkExistsLimitPrice(goodsid,"")){
	  			return false;
  			}
			var formdata=$("#purchase-form-limitPriceOrderDetailAddPage").serializeJSON();
			if(formdata){
				var index=getAddRowIndex();
				$("#purchase-limitPriceOrderAddPage-limitPriceOrdertable").datagrid('updateRow',{
					index:index,
					row:formdata
				});
				if(index>=14){
					var rows=$("#purchase-limitPriceOrderAddPage-limitPriceOrdertable").datagrid('getRows');
					if(index == rows.length - 1){
						$("#purchase-limitPriceOrderAddPage-limitPriceOrdertable").datagrid('appendRow',{});
					}
				}
			}
			if(isGoOn){
				orderDetailAddSaveGoOnDialog();
			}else{
				$("#purchase-limitPriceOrderAddPage-dialog-DetailOper-content").dialog("close");
			}	
  		}
  		$(document).ready(function(){
  			$("#purchase-limitPriceOrderDetail-goodsid").goodsWidget({
    			name:'t_purchase_limitPriceorder_detail',
    			col:'goodsid',
    			singleSelect:true,
    			width:150,
    			canBuySale:'1',<%--购销类型，可采购（购销、可购）--%>
        		param:[
                		{field:'id',op:'notin',value:selectedGoodsid}
                ],
    			onSelect : function(data){
    				if(data){
    		  			if(!checkAfterAddGoods(data.id)){
    						$.messager.alert("提醒","抱歉，采购调价单中已经存在该商品！");
    	    				return false;
    					}
        				$("#purchase-limitPriceOrderDetail-goodsname").val(data.name);
        				$("#purchase-limitPriceOrderDetail-brand").val(data.brandName);
        				$("#purchase-limitPriceOrderDetail-model").val(data.model);
        				$("#purchase-limitPriceOrderDetail-unitid").val(data.mainunit);
        				$("#purchase-limitPriceOrderDetail-barcode").val(data.barcode);
        				$("#purchase-form-limitPriceOrderAddPage input[name='goodsfield01']").val(data.field01);
        				$("#purchase-form-limitPriceOrderAddPage input[name='goodsfield02']").val(data.field02);
        				$("#purchase-form-limitPriceOrderAddPage input[name='goodsfield03']").val(data.field03);
        				$("#purchase-form-limitPriceOrderAddPage input[name='goodsfield04']").val(data.field04);
        				$("#purchase-form-limitPriceOrderAddPage input[name='goodsfield05']").val(data.field05);
        				$("#purchase-form-limitPriceOrderAddPage input[name='goodsfield06']").val(data.field06);
        				$("#purchase-form-limitPriceOrderAddPage input[name='goodsfield07']").val(data.field07);
        				$("#purchase-form-limitPriceOrderAddPage input[name='goodsfield08']").val(data.field08);
        				$("#purchase-form-limitPriceOrderAddPage input[name='goodsfield09']").val(data.field09); 
			            $("#purchase-limitPriceOrderDetail-priceasfound").numberbox('setValue',data.highestbuyprice);
			            
        				showAuxunitNameInfo();
        				$("#purchase-limitPriceOrderDetail-priceasleft").focus();
        				$("#purchase-limitPriceOrderDetail-priceasleft").select();
    				}
    			}
    		});

    		$("#purchase-limitPriceOrderDetail-priceasleft").numberbox({
    			precision:6,
				required:true,				
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
			
	  		$("#purchase-limitPriceOrderDetailAddPage-addSave").click(function(){
	  			saveOrderDetail(false);	  						
	  		});

	  		$("#purchase-limitPriceOrderDetailAddPage-addSaveGoOn").click(function(){
	  			saveOrderDetail(true);
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
		   			$("#purchase-limitPriceOrderDetailAddPage-addSaveGoOn").focus();
				}
		    });
	  		$("#purchase-limitPriceOrderDetailAddPage-addSaveGoOn").die("keydown").live("keydown",function(event){
	  			if(event.keyCode==13){
	  				saveOrderDetail(true);
	  			}
	  		});
  		});
  	</script>
  </body>
</html>
