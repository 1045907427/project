<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>发货单明细添加</title>
  </head>
  
  <body>
   	<form action="" method="post" id="storage-form-saleOutDetailAddPage">
   		<table  border="0" class="box_table">
   			<tr>
   				<td width="120">选择商品:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-saleOut-goodsid" name="goodsid" width="180"/>
   					<input type="hidden" id="storage-saleOut-hidden-summarybatchid" name="summarybatchid"/>
   				</td>
   				<td>折扣金额:</td>
   				<td>
   					<input type="text" id="storage-saleOut-taxamount" name="taxamount"/>
   					<input type="hidden" name="isdiscount" value="1"/>
   				</td>
   			</tr>
   			<tr>
   				<td width="120">商品品牌:</td>
   				<td>
   					<input type="text" id="storage-saleOut-goodsbrandName" class="no_input" readonly="readonly"/>
   				</td>
   				<td width="120">规格型号:</td>
   				<td>
   					<input type="text" id="storage-saleOut-goodsmodel" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td width="120">折扣未税额:</td>
   				<td colspan="3" style="text-align: left;">
   					<input type="text" id="storage-saleOut-notaxamount" name="notaxamount" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-saleOut-tax" name="tax" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>备注:</td>
   				<td colspan="3" style="text-align: left;">
   					<input type="text" name="remark" style="width: 400px;" maxlength="200" value="折扣"/>
   				</td>
   			</tr>
   		</table>
   		
    </form>
   <script type="text/javascript">
   		var detailrows = $("#storage-datagrid-saleOutAddPage").datagrid('getRows');
   		var goodsid = "";
   		for(var i=0; i<detailrows.length; i++){
   			var rowJson = detailrows[i];
   			if(rowJson.goodsid != undefined && rowJson.isdiscount=='0'){
   				if(goodsid==""){
   					if(rowJson.goodsid!=null && rowJson.goodsid!=""){
   						goodsid = rowJson.goodsid;
   					}
   				}else{
   					if(rowJson.goodsid!=null && rowJson.goodsid!=""){
   						goodsid += ","+rowJson.goodsid;
   					}
   				}
   			}
   		}
   		$(function(){
   			$("#storage-saleOut-goodsid").goodsWidget({
   				name:'t_storage_saleout_detail',
				col:'goodsid',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:true,
    			param:[{field:'id',op:'in',value:goodsid}],
    			onSelect:function(data){
    				$("#storage-saleOut-goodsbrandName").val(data.brandName);
    				$("#storage-saleOut-goodsmodel").val(data.model);
    			}
   			});
			$("#storage-saleOut-taxamount").numberbox({
				precision:6,
				groupSeparator:',',
				required:true,
				onChange:function(newValue,oldValue){
					if(Number(newValue)>0){
						$("#storage-saleOut-taxamount").numberbox("setValue",-Number(newValue));
					}else{
						var goodsid = $("#storage-saleOut-goodsid").goodsWidget("getValue");
						var taxamount = $("#storage-saleOut-taxamount").numberbox("getValue");
						$.ajax({   
				            url :'storage/computeSaleOutDiscountTax.do',
				            type:'post',
				            data:{goodsid:goodsid,taxamount:taxamount},
				            dataType:'json',
				            async:false,
				            success:function(json){
				            	$("#storage-saleOut-notaxamount").numberbox("setValue",json.notaxamount);
				            	$("#storage-saleOut-tax").val(json.tax);
				            }
				        });
					}
				}
			});
			$("#storage-saleOut-notaxamount").numberbox({
				precision:6,
				groupSeparator:','
			});
		});
   </script>
  </body>
</html>
