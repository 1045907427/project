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
   				<td colspan="3" style="text-align: left;">
   					<input type="text" id="storage-saleOut-saleoutgoodsid"  width="180"/>
   					<input type="hidden" id="storage-saleOut-hidden-goodsid" name="goodsid"/>
   					<input type="hidden" id="storage-saleOut-hidden-dispatchbilldetailid" name="dispatchbilldetailid">
   					<input type="hidden" id="storage-saleOut-hidden-dispatchbillid" name="dispatchbillid">
   					<input type="hidden" name="isdiscount" value="0"/>
   				</td>
   			</tr>
   			<tr>
   				<td width="120">配置发货:</td>
   				<td colspan="3" style="text-align: left;">
   					<input type="text" id="storage-saleOut-goodsid" name="summarybatchid" width="170"/>
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
   				<td>计量单位:</td>
   				<td>
   					<input type="text" id="storage-saleOut-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-saleOut-goodsunitid" name="unitid"/>
   				</td>
   				<td>辅单位:</td>
   				<td>
   					<input type="text" id="storage-saleOut-auxunitname" name="auxunitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-saleOut-auxunitid" name="auxunitid"/>
   				</td>
   			</tr>
   			<tr>
   				<td>数量:</td>
   				<td>
   					<input type="text" id="storage-saleOut-unitnum" name="unitnum"/>
   				</td>
   				<td>辅单位数量:</td>
   				<td>
   					<input type="text" id="storage-saleOut-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-saleOut-auxunitnum" name="auxnum"/>
   				</td>
   			</tr>
   			<tr>
   				<td>含税单价:</td>
   				<td>
   					<input type="text" id="storage-saleOut-taxprice" name="taxprice" class="no_input" readonly="readonly"/>
   				</td>
   				<td>含税金额:</td>
   				<td>
   					<input type="text" id="storage-saleOut-taxamount" name="taxamount" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>无税单价:</td>
   				<td>
   					<input type="text" id="storage-saleOut-notaxprice" name="notaxprice" class="no_input" readonly="readonly"/>
   				</td>
   				<td>无税金额:</td>
   				<td>
   					<input type="text" id="storage-saleOut-notaxamount" name="notaxamount" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>税种:</td>
   				<td>
   					<input type="text" id="storage-saleOut-taxtypename" name="taxtypename" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-saleOut-taxtype" name="taxtype" class="no_input" readonly="readonly"/>
   				</td>
   				<td>税额:</td>
   				<td>
   					<input type="text" id="storage-saleOut-tax" name="tax" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>所属库位:</td>
   				<td>
   					<input type="text" id="storage-saleOut-storagelocationname" name="storagelocationname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-saleOut-storagelocationid"  name="storagelocationid"/>
   				</td>
   				<td>批次号:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-saleOut-batchno" name="batchno" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>生产日期:</td>
   				<td>
   					<input type="text" id="storage-saleOut-produceddate" name="produceddate" class="no_input" readonly="readonly"/>
   				</td>
   				<td>有效截止日期:</td>
   				<td>
   					<input type="text" id="storage-saleOut-deadline" name="deadline" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>备注:</td>
   				<td colspan="3" style="text-align: left;">
   					<input type="text" name="remark" style="width: 400px;" maxlength="200"/>
   				</td>
   			</tr>
   		</table>
   		
    </form>
   <script type="text/javascript">
   		var summarybatchid = "";
   		var detailrows = $("#storage-datagrid-saleOutAddPage").datagrid('getRows');
   		for(var i=0; i<detailrows.length; i++){
   			var rowJson = detailrows[i];
   			if(rowJson.goodsid != undefined){
   				if(summarybatchid==""){
   					if(rowJson.summarybatchid!=null && rowJson.summarybatchid!=""){
   						summarybatchid = rowJson.summarybatchid;
   					}
   				}else{
   					if(rowJson.summarybatchid!=null && rowJson.summarybatchid!=""){
   						summarybatchid += ","+rowJson.summarybatchid;
   					}
   				}
   			}
   		}
   		var dispatchDetailList = JSON.parse('${jsonData}');
   		var lastDisptachDetailList = [];
   		for(var i =0;i<dispatchDetailList.length;i++){
   			var unitnum = dispatchDetailList[i].unitnum;
   			for(var j=0;j<detailrows.length;j++){
   				if(dispatchDetailList[i].id==detailrows[j].dispatchbilldetailid){
   					unitnum = Number(unitnum)-Number(detailrows[j].unitnum);
   				}
   			}
   			if(unitnum>0){
   				dispatchDetailList[i].unitnum = unitnum;
   				lastDisptachDetailList.push(dispatchDetailList[i]);
   			}
   		}
		$(function(){
			$("#storage-saleOut-saleoutgoodsid").combogrid({
				columns:[[	
						{field:'goodsid',title:'商品编码',width:80,align:'left'},
    					{field:'goodsname', title:'商品名称', width:120,align:'left',
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.name;
					       		}else{
					       			return "";
				       			}
					        }
    					},
    					{field:'brandName', title:'商品品牌',width:80,align:'left',
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.brandName;
					       		}else{
					       			return "";
					       		}
					        }
    					},
    					{field:'unitname', title:'计量单位',width:70,align:'left'},
    					{field:'unitnum', title:'数量',width:60,align:'right',
    						formatter:function(value,row,index){
				        		return formatterBigNumNoLen(value);
					        }
				    	},
    					{field:'deliverydate', title:'交货日期',width:80,align:'left'},
    					{field:'deliverytype', title:'发货类型',width:80,align:'left',
    						formatter:function(value,rowData,rowIndex){
    							if(rowData.goodsid != undefined){ 
    								if(value == undefined || value == '' || value == null){
    									return '普通';
    								}
	    							else{
	    								return getSysCodeName('deliverytype', value);
	    							}
    							}
    						}
    					},
    					{field:'remark', title:'备注',width:100,align:'left'}	
				]],
				width:500,
				data: lastDisptachDetailList,
				idField:"id",
				textField:'goodsname',
				required:true,
				onSelect:function(rowIndex,rowData){
					rowData.goodsname = rowData.goodsInfo.name;
					$("#storage-saleOut-hidden-goodsid").val(rowData.goodsid);
					$("#storage-saleOut-hidden-dispatchbilldetailid").val(rowData.id);
					$("#storage-saleOut-hidden-dispatchbillid").val(rowData.dispatchbillid);
					$("#storage-saleOut-goodsbrandName").val(rowData.goodsInfo.brandName);
					$("#storage-saleOut-goodsmodel").val(rowData.goodsInfo.model);
					$("#storage-saleOut-goodsunitname").val(rowData.unitname);
					$("#storage-saleOut-goodsunitid").val(rowData.unitid);
					$("#storage-saleOut-auxunitname").val(rowData.auxunitname);
					$("#storage-saleOut-auxunitid").val(rowData.auxunitid);
					$("#storage-saleOut-taxtypename").val(rowData.taxtypename);
					$("#storage-saleOut-taxprice").numberbox("setValue",rowData.taxprice);
					$("#storage-saleOut-notaxprice").numberbox("setValue",rowData.notaxprice);
					$("#storage-saleOut-unitnum").numberbox("setValue",rowData.unitnum);
					
					$("#storage-saleOut-goodsid").widget({
						referwid:'RL_T_STORAGE_SUMMAY_BATCH',
						singleSelect:true,
						required:true,
						param:[{field:'storageid',op:'equal',value:'${storageid}'},
							{field:'id',op:'notin',value:summarybatchid},
							{field:'goodsid',op:'equal',value:rowData.goodsid},
							{field:'usablenum',op:'greater',value:0}
						],
						onSelect:function(data){
							$("#storage-saleOut-batchno").val(data.batchno);
							$("#storage-saleOut-produceddate").val(data.produceddate);
							$("#storage-saleOut-deadline").val(data.deadline);
							$("#storage-saleOut-storagelocationid").val(data.storagelocationid);
							$("#storage-saleOut-storagelocationname").val(data.storagelocationname);
							
							$("#storage-saleOut-summarybatchid").val(data.id);
							var unitnum = $("#storage-saleOut-unitnum").numberbox("getValue");
							if(data.usablenum<unitnum){
								$("#storage-saleOut-unitnum").numberbox("setValue",data.usablenum);
							}
						}
					});
				}
			});
			
			$("#storage-saleOut-unitnum").numberbox({
				precision:2,
				groupSeparator:',',
				required:true,
				onChange:function(newValue,oldValue){
					var goodsid= $("#storage-saleOut-hidden-goodsid").val();
					var auxunitid = $("#storage-saleOut-auxunitid").val();
					var unitnum = $("#storage-saleOut-unitnum").numberbox("getValue");
					var taxprice = $("#storage-saleOut-taxprice").numberbox("getValue");
					var notaxprice = $("#storage-saleOut-notaxprice").numberbox("getValue");
					var customerid = $("#storage-saleOut-customerid").widget("getValue");
					$("#storage-saleOut-unitnum").addClass("inputload");
					$.ajax({   
			            url :'storage/computeSaleOutDetailNum.do',
			            type:'post',
			            data:{unitnum:unitnum,goodsid:goodsid,auxunitid:auxunitid,customerid:customerid,taxprice:taxprice,notaxprice:notaxprice},
			            dataType:'json',
			            async:false,
			            success:function(json){
			            	$("#storage-saleOut-taxamount").numberbox("setValue",json.taxamount);
			            	$("#storage-saleOut-notaxamount").numberbox("setValue",json.notaxamount);
			            	$("#storage-saleOut-tax").numberbox("setValue",json.tax);
			            	$("#storage-saleOut-auxunitnumdetail").val(json.auxunitnumdetail);
			            	$("#storage-saleOut-auxunitnum").val(json.auxunitnum);
			            	$("#storage-saleOut-unitnum").removeClass("inputload");
			            }
			        });
				}
			});
			$("#storage-saleOut-taxprice").numberbox({
				precision:2,
				groupSeparator:',',
				required:true
			});
			$("#storage-saleOut-taxamount").numberbox({
				precision:2,
				groupSeparator:',',
				required:true
			});
			$("#storage-saleOut-notaxprice").numberbox({
				precision:2,
				groupSeparator:',',
				required:true
			});
			$("#storage-saleOut-notaxamount").numberbox({
				precision:2,
				groupSeparator:',',
				required:true
			});
			$("#storage-saleOut-tax").numberbox({
				precision:2,
				groupSeparator:',',
				required:true
			});
		});
   </script>
  </body>
</html>
