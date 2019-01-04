<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>发货单明细添加</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
	   	<form action="" method="post" id="storage-form-saleOutDetailAddPage">
	   		<table  border="0" class="box_table">
	   			<tr>
	   				<td width="120">选择商品:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-saleOut-goodsid" name="goodsid" width="180"/>
	   					<input type="hidden" id="storage-saleOut-hidden-summarybatchid" name="summarybatchid"/>
	   					<input type="hidden" name="isdiscount" value="0"/>
	   					
	   				</td>
	   				<td>条形码:</td>
	   				<td>
	   					<input type="text" id="storage-saleOut-goodsbarcode" class="no_input" readonly="readonly"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>数量:</td>
	   				<td>
	   					<input type="text" id="storage-saleOut-unitnum" name="unitnum" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
	   				</td>
	   				<td>辅数量:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-saleOut-unitnum-aux" name="auxnum" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'integer'"/>
	   					<span id="storage-saleOut-auxunitname1" style="float: left;"></span>
	   					<input type="text" id="storage-saleOut-unitnum-unit" name="auxremainder" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
	   					<span id="storage-saleOut-goodsunitname1" style="float: left;"></span>
	   					<input type="hidden" id="storage-saleOut-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
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
	   				<td>主单位:</td>
	   				<td style="text-align: left;">
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
	   				<td>含税单价:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-saleOut-taxprice" name="taxprice" class="no_input" readonly="readonly"/>
	   				</td>
	   				<td>含税金额:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-saleOut-taxamount" name="taxamount" class="no_input" readonly="readonly"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>未税单价:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-saleOut-notaxprice" name="notaxprice" class="no_input" readonly="readonly"/>
	   				</td>
	   				<td>未税金额:</td>
	   				<td style="text-align: left;">
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
	   				<td style="text-align: left;">
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
	   					<input id="storage-saleOut-remark" type="text" name="remark" style="width: 488px;" maxlength="200"/>
	   				</td>
	   			</tr>
	   		</table>
	    </form>
	    </div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="height:30px;text-align: right;">
  				快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
	  			<input type="button" value="确 定" name="savegoon" id="storage-savegoon-saleOutDetailAddPage" />
  			</div>
  		</div>
  	</div>
   <script type="text/javascript">
		$(function(){
			$("#storage-saleOut-goodsid").storageGoodsWidget({
				required:true,
				param:[{field:'storageid',op:'equal',value:'${storageid}'},
					{field:'usablenum',op:'greater',value:0}
				],
				onSelect:function(data){
					$("#storage-saleOut-hidden-summarybatchid").val(data.summarybatchid);
					$("#storage-saleOut-goodsbrandName").val(data.brandname);
					$("#storage-saleOut-goodsmodel").val(data.model);
					$("#storage-saleOut-goodsbarcode").val(data.barcode);
					$("#storage-saleOut-goodsunitname").val(data.unitname);
					$("#storage-saleOut-goodsunitname1").html(data.unitname);
					$("#storage-saleOut-goodsunitid").val(data.unitid);
					$("#storage-saleOut-auxunitname").val(data.auxunitname);
					$("#storage-saleOut-auxunitname1").html(data.auxunitname);
					$("#storage-saleOut-auxunitid").val(data.auxunitid);
					$("#storage-saleOut-batchno").val(data.batchno);
					$("#storage-saleOut-produceddate").val(data.produceddate);
					$("#storage-saleOut-deadline").val(data.deadline);
					$("#storage-saleOut-storagelocationid").val(data.storagelocationid);
					$("#storage-saleOut-storagelocationname").val(data.storagelocationname);
					
					
					var auxunitid = $("#storage-saleOut-auxunitid").val();
					var unitnum = $("#storage-saleOut-unitnum").val();
					if(unitnum!=""){
						$("#storage-saleOut-unitnum").addClass("inputload");
						var customerid = $("#storage-saleOut-customerid").widget("getValue");
						$.ajax({   
				            url :'storage/computeSaleOutDetailNum.do',
				            type:'post',
				            data:{unitnum:unitnum,goodsid:data.goodsid,auxunitid:auxunitid,customerid:customerid},
				            dataType:'json',
				            async:false,
				            success:function(json){
				            	if(json.goodsprice!=null){
				            		$("#storage-saleOut-taxprice").numberbox("setValue",json.goodsprice.taxprice);
					            	$("#storage-saleOut-notaxprice").numberbox("setValue",json.goodsprice.notaxprice);
					            	$("#storage-saleOut-taxtypename").val(json.goodsprice.taxtypename);
					            	$("#storage-saleOut-taxtype").val(json.goodsprice.taxtype);
				            	}
				            	$("#storage-saleOut-taxamount").numberbox("setValue",json.taxamount);
				            	$("#storage-saleOut-notaxamount").numberbox("setValue",json.notaxamount);
				            	$("#storage-saleOut-tax").numberbox("setValue",json.tax);
				            	$("#storage-saleOut-auxunitnumdetail").val(json.auxunitnumdetail);
				            	$("#storage-saleOut-auxunitnum").val(json.auxunitnum);
				            	$("#storage-saleOut-unitnum").removeClass("inputload");
				            }
				        });
			        }else{
			        	var customerid = $("#storage-saleOut-customerid").customerWidget("getValue");
			        	$.ajax({   
				            url :'storage/getCustomerGoodsPrice.do',
				            type:'post',
				            data:{goodsid:data.goodsid,customerid:customerid},
				            dataType:'json',
				            success:function(json){
				            	$("#storage-saleOut-taxprice").numberbox("setValue",json.goodsprice.taxprice);
				            	$("#storage-saleOut-notaxprice").numberbox("setValue",json.goodsprice.notaxprice);
				            	$("#storage-saleOut-taxtypename").val(json.goodsprice.taxtypename);
				            	$("#storage-saleOut-taxtype").val(json.goodsprice.taxtype);
				            }
				        });
			        }
			        $("#storage-saleOut-unitnum").focus();
			        $("#storage-saleOut-unitnum").select();
				},
				onClear:function(){
					$("#storage-saleOut-hidden-storageid").val("");
				}
			});
			$("#storage-saleOut-unitnum").change(function(){
				computNum();
			});
			$("#storage-saleOut-unitnum-aux").change(function(){
				computNumByAux();
			});
			$("#storage-saleOut-unitnum-unit").change(function(){
				computNumByAux();
			});
			$("#storage-saleOut-taxprice").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#storage-saleOut-taxamount").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#storage-saleOut-notaxprice").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#storage-saleOut-notaxamount").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#storage-saleOut-tax").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#storage-saleOut-unitnum").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#storage-saleOut-unitnum-aux").focus();
		   			$("#storage-saleOut-unitnum-aux").select();
				}
			});
			$("#storage-saleOut-unitnum-aux").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
		   			$("#storage-saleOut-unitnum-unit").focus();
		   			$("#storage-saleOut-unitnum-unit").select();
				}
			});
			$("#storage-saleOut-unitnum-unit").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
		   			$("#storage-saleOut-remark").focus();
		   			$("#storage-saleOut-remark").select();
				}
			});
			$("#storage-saleOut-remark").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
		   			$("#storage-savegoon-saleOutDetailAddPage").focus();
				}
			});
			$("#storage-savegoon-saleOutDetailAddPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					addSaveDetail(true);
				}
			});
			$("#storage-savegoon-saleOutDetailAddPage").click(function(){
				addSaveDetail(true);
			});
		});
		//通过总数量 计算数量 金额换算
		function computNum(){
			var goodsid= $("#storage-saleOut-goodsid").storageGoodsWidget("getValue");
			var auxunitid = $("#storage-saleOut-auxunitid").val();
			var unitnum = $("#storage-saleOut-unitnum").val();
			var taxprice = $("#storage-saleOut-taxprice").val();
			var notaxprice = $("#storage-saleOut-notaxprice").val();
			var taxtype = $("#storage-saleOut-taxtype").val();
			$("#storage-saleOut-unitnum").addClass("inputload");
			$.ajax({   
	            url :'basefiles/computeGoodsByUnitnum.do',
	            type:'post',
	            data:{unitnum:unitnum,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#storage-saleOut-taxamount").numberbox("setValue",json.taxamount);
	            	$("#storage-saleOut-notaxamount").numberbox("setValue",json.notaxamount);
	            	$("#storage-saleOut-tax").numberbox("setValue",json.tax);
	            	$("#storage-saleOut-taxtypename").val(json.taxtypename);
	            	$("#storage-saleOut-auxunitnumdetail").val(json.auxnumdetail);
	            	$("#storage-saleOut-auxunitnum").val(json.auxnum);
	            	$("#storage-saleOut-auxunitname").val(json.auxunitname);
	            	$("#storage-saleOut-auxunitname1").html(json.auxunitname);
	            	$("#storage-saleOut-goodsunitname").val(json.unitname);
	            	$("#storage-saleOut-goodsunitname1").html(json.unitname);
	            	
	            	$("#storage-saleOut-taxprice").val(json.taxprice);
	            	$("#storage-saleOut-notaxprice").numberbox("setValue",json.notaxprice);
	            	$("#storage-saleOut-notaxamount").numberbox("setValue",json.notaxamount);
	            	
	            	$("#storage-saleOut-unitnum-aux").val(json.auxInteger);
	            	$("#storage-saleOut-unitnum-unit").val(json.auxremainder);
	            	if(json.auxrate!=null){
	            		$("#storage-saleOut-unitnum-unit").attr("max",json.auxrate-1);
	            	}
	            	$("#storage-saleOut-unitnum").removeClass("inputload");
	            }
	        });
		}
		//通过辅单位数量
		function computNumByAux(){
			var goodsid= $("#storage-saleOut-goodsid").storageGoodsWidget("getValue");
			var auxunitid = $("#storage-saleOut-auxunitid").val();
			var taxprice = $("#storage-saleOut-taxprice").val();
			var notaxprice = $("#storage-saleOut-notaxprice").numberbox("getValue");
			var taxtype = $("#storage-saleOut-taxtype").val();
			var auxInterger = $("#storage-saleOut-unitnum-aux").val();
			var auxremainder = $("#storage-saleOut-unitnum-unit").val();
			var auxmax = $("#storage-saleOut-unitnum-unit").attr("max");
			if(Number(auxremainder)>Number(auxmax)){
				auxremainder = auxmax;
				$("#storage-saleOut-unitnum-unit").val(auxremainder);
			}
			$("#storage-saleOut-unitnum").addClass("inputload");
			$.ajax({   
	            url :'basefiles/computeGoodsByAuxnum.do',
	            type:'post',
	            data:{auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#storage-saleOut-taxamount").numberbox("setValue",json.taxamount);
	            	$("#storage-saleOut-notaxamount").numberbox("setValue",json.notaxamount);
	            	$("#storage-saleOut-tax").numberbox("setValue",json.tax);
	            	$("#storage-saleOut-taxtypename").val(json.taxtypename);
	            	
	            	$("#storage-saleOut-taxprice").val(json.taxprice);
	            	$("#storage-saleOut-notaxprice").numberbox("setValue",json.notaxprice);
	            	$("#storage-saleOut-notaxamount").numberbox("setValue",json.notaxamount);
	            	
	            	$("#storage-saleOut-unitnum").val(json.mainnum);
	            	
	            	$("#storage-saleOut-unitnum").removeClass("inputload");
	            }
	        });
	        var auxunitname = $("#storage-saleOut-auxunitname").val();
	        var unitname = $("#storage-saleOut-goodsunitname").val();
	        var auxdetail = auxInterger+auxunitname;
	        if(auxremainder>0){
	       		auxdetail += auxremainder+unitname;
	        }
	        $("#storage-saleOut-auxunitnumdetail").val(auxdetail);
		}
		//页面重置
		function otherEnterformReset(){
			$("#storage-form-saleOutDetailAddPage").form("clear");
           	$("#storage-saleOut-auxunitname1").html("");
           	$("#storage-saleOut-goodsunitname1").html("");
    		$("#storage-saleOut-goodsid").focus();
		}
   </script>
  </body>
</html>
