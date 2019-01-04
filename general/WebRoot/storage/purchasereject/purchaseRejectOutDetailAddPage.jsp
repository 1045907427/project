<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>采购退货出库单明细添加</title>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
	   	<form action="" method="post" id="storage-form-purchaseRejectOutDetailAddPage">
	   		<table  border="0" class="box_table">
	   			<tr>
	   				<td width="120">选择商品:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-purchaseRejectOut-goodsid" name="goodsid" width="180"/>
	   					<input type="hidden" id="storage-purchaseRejectOut-summarybatchid" name="summarybatchid">
	   					<input type="hidden" id="storage-purchaseRejectOut-goodsname" name="goodsname">
	   				</td>
	   				<td>条形码:</td>
	   				<td>
	   					<input type="text" id="storage-purchaseRejectOut-goodsbarcode" name="barcode" class="no_input" readonly="readonly"/>
	   				</td>
<!--	   				<td id="storage-purchaseRejectOut-loadInfo" colspan="2" style="text-align: left;"></td>-->
	   			</tr>
	   			<tr>
	   				<td>数量:</td>
	   				<td>
	   					<input type="text" id="storage-purchaseRejectOut-unitnum" name="unitnum" class="easyui-validatebox" data-options="required:true,validType:['intOrFloatNum[${decimallen}]','max']"/>
	   				</td>
	   				<td>辅数量:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-purchaseRejectOut-unitnum-aux" name="auxnum" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'integer'"/>
	   					<span id="storage-purchaseRejectOut-auxunitname1" style="float: left;"></span>
	   					<input type="text" id="storage-purchaseRejectOut-unitnum-unit" name="auxremainder" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
	   					<span id="storage-purchaseRejectOut-goodsunitname1" style="float: left;"></span>
	   					<input type="hidden" id="storage-purchaseRejectOut-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td width="120">商品品牌:</td>
	   				<td>
	   					<input type="text" id="storage-purchaseRejectOut-goodsbrandName" name="brandName" class="no_input" readonly="readonly"/>
	   				</td>
	   				<td width="120">规格型号:</td>
	   				<td>
	   					<input type="text" id="storage-purchaseRejectOut-goodsmodel" name="model" class="no_input" readonly="readonly"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>主单位:</td>
	   				<td>
	   					<input type="text" id="storage-purchaseRejectOut-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
	   					<input type="hidden" id="storage-purchaseRejectOut-goodsunitid" name="unitid"/>
	   				</td>
	   				<td>辅单位:</td>
	   				<td>
	   					<input type="text" id="storage-purchaseRejectOut-auxunitname" name="auxunitname" class="no_input" readonly="readonly"/>
	   					<input type="hidden" id="storage-purchaseRejectOut-auxunitid" name="auxunitid"/>
	   				</td>
	   			</tr>
	   			<tr>
                    <td <c:if test="${map.taxprice != 'taxprice'}"> style="display: none"</c:if>>含税单价:</td>
                    <td style="float: left;<c:if test="${map.taxprice != 'taxprice'}"> display: none;</c:if>">
                        <input type="text" id="storage-purchaseRejectOut-taxprice" name="taxprice" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'">
                    <td <c:if test="${map.taxamount != 'taxamount'}"> style="display: none"</c:if>>含税金额:</td>
                    <td  style="float: left;<c:if test="${map.taxamount != 'taxamount'}"> display: none;</c:if>">
                        <input type="text" id="storage-purchaseRejectOut-taxamount" name="taxamount" class="no_input" readonly="readonly"/>
                    </td>

	   			</tr>
	   			<tr>
                    <td <c:if test="${map.notaxprice != 'notaxprice'}"> style="display: none"</c:if>>未税单价:</td>
                    <td style="float: left;<c:if test="${map.notaxprice != 'notaxprice'}"> display: none;</c:if>">
                        <input type="text" id="storage-purchaseRejectOut-notaxprice" name="notaxprice" class="no_input" readonly="readonly"/>
                    </td>
                    <td <c:if test="${map.notaxamount != 'notaxamount'}"> style="display: none"</c:if>>未税金额:</td>
                    <td  style="float: left;<c:if test="${map.notaxamount != 'notaxamount'}"> display: none;</c:if>">
                        <input type="text" id="storage-purchaseRejectOut-notaxamount" name="notaxamount" class="no_input" readonly="readonly"/>
                    </td>
	   			</tr>
	   			<tr>
	   				<td>税种:</td>
	   				<td>
	   					<input type="text" id="storage-purchaseRejectOut-taxtypename" name="taxtypename" class="no_input" readonly="readonly"/>
	   					<input type="hidden" id="storage-purchaseRejectOut-taxtype" name="taxtype" class="no_input" readonly="readonly"/>
	   				</td>
                    <td <c:if test="${map.tax != 'tax'}"> style="display: none"</c:if>>税额:</td>
                    <td style="float: left;<c:if test="${map.tax != 'tax'}"> display: none;</c:if>">
                        <input type="text" id="storage-purchaseRejectOut-tax" name="tax" class="no_input" readonly="readonly"/>
                    </td>
	   			</tr>
	   			<tr>
	   				<td>所属库位:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-purchaseRejectOut-storagelocationname" name="storagelocationname" class="no_input" readonly="readonly"/>
	   					<input type="hidden" id="storage-purchaseRejectOut-storagelocationid"  name="storagelocationid"/>
	   				</td>
	   				<td>批次号:</td>
	   				<td>
	   					<input type="text" id="storage-purchaseRejectOut-batchno" name="batchno" class="no_input" readonly="readonly"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>生产日期:</td>
	   				<td>
	   					<input type="text" id="storage-purchaseRejectOut-produceddate" style="height: 20px;" class="no_input" name="produceddate" readonly="readonly"/>
	   				</td>
	   				<td>有效截止日期:</td>
	   				<td>
	   					<input type="text" id="storage-purchaseRejectOut-deadline" style="height: 20px;" class="no_input" name="deadline" readonly="readonly"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>备注:</td>
	   				<td colspan="3" style="text-align: left;">
	   					<input id="storage-purchaseRejectOut-remark" type="text" name="remark" style="width: 400px;" maxlength="200"/>
	   				</td>
	   			</tr>
	   		</table>
	    </form>
	    </div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="height:30px;text-align: right;">
  				快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
	  			<input type="button" value="继续添加" name="savegoon" id="storage-savegoon-purchaseRejectOutDetailAddPage" />
  			</div>
  		</div>
   <script type="text/javascript">
   		var selectStorageid = $("#storage-purchaseRejectOut-storageid").val();
   		var maxnum = 0;
		$(function(){
			$.extend($.fn.validatebox.defaults.rules, {
			   max: {  
			        validator: function (value) {  
			        	if(value > maxnum){
			        		return false;
			        	}else{
			        		return true;
			        	}
			        },  
			        message:'数量超过可用量'
			    } 
			});
			$("#storage-purchaseRejectOut-goodsid").storageGoodsWidget({
				param:[
					{field:'storageid',op:'equal',value:selectStorageid},
				],
				required:true,
				onSelect:function(data){
					$("#storage-purchaseRejectOut-goodsname").val(data.goodsname);
					$("#storage-purchaseRejectOut-summarybatchid").val(data.summarybatchid);
					$("#storage-purchaseRejectOut-goodsbrandName").val(data.brandname);
					$("#storage-purchaseRejectOut-goodsmodel").val(data.model);
					$("#storage-purchaseRejectOut-goodsunitid").val(data.unitid);
					$("#storage-purchaseRejectOut-auxunitid").val(data.auxunitid);
					$("#storage-purchaseRejectOut-taxtype").val(data.taxtype);
					$("#storage-purchaseRejectOut-goodsbarcode").val(data.barcode);
					$("#storage-purchaseRejectOut-taxprice").val(data.newstorageprice);
					
					$("#storage-purchaseRejectOut-storagelocationname").val(data.storagelocationname);
					$("#storage-purchaseRejectOut-storagelocationid").val(data.storagelocationid);
					$("#storage-purchaseRejectOut-batchno").val(data.batchno);
					$("#storage-purchaseRejectOut-produceddate").val(data.produceddate);
					$("#storage-purchaseRejectOut-deadline").val(data.deadline);
					//$("#storage-purchaseRejectOut-loadInfo").html("现存量：<font color='green'>"+data.existingnum+data.unitname+"</font>&nbsp;可用量：<font color='green'>"+ data.usablenum +data.unitname+"</font>");
					//最大量限制
					maxnum = data.usablenum;
					computNum();
			        $("#storage-purchaseRejectOut-unitnum").focus();
			        $("#storage-purchaseRejectOut-unitnum").select();
				}
			});
			$("#storage-purchaseRejectOut-taxprice").change(function(){
				computNum();
			});
			$("#storage-purchaseRejectOut-taxamount").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#storage-purchaseRejectOut-notaxprice").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#storage-purchaseRejectOut-notaxamount").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#storage-purchaseRejectOut-tax").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#storage-purchaseRejectOut-unitnum").change(function(){
				computNum();
			});
			$("#storage-purchaseRejectOut-unitnum-aux").change(function(){
				computNumByAux();
			});
			$("#storage-purchaseRejectOut-unitnum-unit").change(function(){
				computNumByAux();
			});
			$("#storage-purchaseRejectOut-unitnum").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#storage-purchaseRejectOut-unitnum").blur();
		   			$("#storage-purchaseRejectOut-unitnum-aux").focus();
		   			$("#storage-purchaseRejectOut-unitnum-aux").select();
				}
			});
			$("#storage-purchaseRejectOut-unitnum-aux").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-purchaseRejectOut-unitnum-auxm").blur();
		   			$("#storage-purchaseRejectOut-unitnum-unit").focus();
		   			$("#storage-purchaseRejectOut-unitnum-unit").select();
				}
			});
			$("#storage-purchaseRejectOut-unitnum-unit").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-purchaseRejectOut-unitnum-unit").blur();
		   			//$("#storage-purchaseRejectOut-taxprice").focus();
		   			//$("#storage-purchaseRejectOut-taxprice").select();
		   			$("#storage-purchaseRejectOut-remark").focus();
		   			$("#storage-purchaseRejectOut-remark").select();
				}
			});
			$("#storage-purchaseRejectOut-taxprice").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-purchaseRejectOut-taxprice").blur();
		   			$("#storage-purchaseRejectOut-remark").focus();
		   			$("#storage-purchaseRejectOut-remark").select();
				}
			});
			$("#storage-purchaseRejectOut-remark").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-purchaseRejectOut-unitnum-remark").blur();
					//$("#storage-purchaseRejectOut-taxprice").blur();
		   			$("#storage-savegoon-purchaseRejectOutDetailAddPage").focus();
				}
			});
			$("#storage-savegoon-purchaseRejectOutDetailAddPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					addSaveDetail(true);
				}
			});
		    $("#storage-savegoon-purchaseRejectOutDetailAddPage").click(function(){
		    	addSaveDetail(true);
		    });
		});
		//通过总数量 计算数量 金额换算
		function computNum(){
			var goodsInfo= $("#storage-purchaseRejectOut-goodsid").storageGoodsWidget("getObject");
			if(null==goodsInfo){
				return false;
			}
			var auxunitid = $("#storage-purchaseRejectOut-auxunitid").val();
			var unitnum = $("#storage-purchaseRejectOut-unitnum").val();
			var taxprice = $("#storage-purchaseRejectOut-taxprice").val();
			var notaxprice = $("#storage-purchaseRejectOut-notaxprice").val();
			var taxtype = $("#storage-purchaseRejectOut-taxtype").val();
			$("#storage-purchaseRejectOut-unitnum").addClass("inputload");
			$.ajax({   
	            url :'basefiles/computeGoodsByUnitnum.do',
	            type:'post',
	            data:{unitnum:unitnum,goodsid:goodsInfo.goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#storage-purchaseRejectOut-taxamount").numberbox("setValue",json.taxamount);
	            	$("#storage-purchaseRejectOut-notaxamount").numberbox("setValue",json.notaxamount);
	            	$("#storage-purchaseRejectOut-tax").numberbox("setValue",json.tax);
	            	$("#storage-purchaseRejectOut-taxtypename").val(json.taxtypename);
	            	$("#storage-purchaseRejectOut-auxunitnumdetail").val(json.auxnumdetail);
	            	$("#storage-purchaseRejectOut-auxunitnum").val(json.auxnum);
	            	$("#storage-purchaseRejectOut-auxunitname").val(json.auxunitname);
	            	$("#storage-purchaseRejectOut-auxunitname1").html(json.auxunitname);
	            	$("#storage-purchaseRejectOut-goodsunitname").val(json.unitname);
	            	$("#storage-purchaseRejectOut-goodsunitname1").html(json.unitname);
	            	
	            	$("#storage-purchaseRejectOut-taxprice").val(json.taxprice);
	            	$("#storage-purchaseRejectOut-notaxprice").numberbox("setValue",json.notaxprice);
	            	$("#storage-purchaseRejectOut-notaxamount").numberbox("setValue",json.notaxamount);
	            	
	            	$("#storage-purchaseRejectOut-unitnum-aux").val(json.auxInteger);
	            	$("#storage-purchaseRejectOut-unitnum-unit").val(json.auxremainder);
	            	if(json.auxrate!=null){
	            		$("#storage-purchaseRejectOut-unitnum-unit").attr("max",json.auxrate-1);
	            	}
	            	$("#storage-purchaseRejectOut-unitnum").removeClass("inputload");
	            }
	        });
		}
		//通过辅单位数量
		function computNumByAux(){
			var goodsInfo= $("#storage-purchaseRejectOut-goodsid").storageGoodsWidget("getObject");
			if(null==goodsInfo){
				return false;
			}
			var auxunitid = $("#storage-purchaseRejectOut-auxunitid").val();
			var taxprice = $("#storage-purchaseRejectOut-taxprice").val();
			var notaxprice = $("#storage-purchaseRejectOut-notaxprice").numberbox("getValue");
			var taxtype = $("#storage-purchaseRejectOut-taxtype").val();
			var auxInterger = $("#storage-purchaseRejectOut-unitnum-aux").val();
			var auxremainder = $("#storage-purchaseRejectOut-unitnum-unit").val();
			var auxmax = $("#storage-purchaseRejectOut-unitnum-unit").attr("max");
			if(Number(auxremainder)>Number(auxmax)){
				auxremainder = auxmax;
				$("#storage-purchaseRejectOut-unitnum-unit").val(auxremainder);
			}
			$("#storage-purchaseRejectOut-unitnum").addClass("inputload");
			$.ajax({   
	            url :'basefiles/computeGoodsByAuxnum.do',
	            type:'post',
	            data:{auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsInfo.goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#storage-purchaseRejectOut-taxamount").numberbox("setValue",json.taxamount);
	            	$("#storage-purchaseRejectOut-notaxamount").numberbox("setValue",json.notaxamount);
	            	$("#storage-purchaseRejectOut-tax").numberbox("setValue",json.tax);
	            	$("#storage-purchaseRejectOut-taxtypename").val(json.taxtypename);
	            	
	            	$("#storage-purchaseRejectOut-taxprice").val(json.taxprice);
	            	$("#storage-purchaseRejectOut-notaxprice").numberbox("setValue",json.notaxprice);
	            	$("#storage-purchaseRejectOut-notaxamount").numberbox("setValue",json.notaxamount);
	            	
	            	$("#storage-purchaseRejectOut-unitnum").val(json.mainnum);
                    $("#storage-purchaseRejectOut-unitnum-aux").val(json.auxInterger);
                    $("#storage-purchaseRejectOut-unitnum-unit").val(json.auxremainder);
                    $("#storage-purchaseRejectOut-auxunitnumdetail").val(json.auxnumdetail);
	            	
	            	$("#storage-purchaseRejectOut-unitnum").removeClass("inputload");
	            }
	        });
		}
		//页面重置
		function otherEnterformReset(){
			$("#storage-form-purchaseRejectOutDetailAddPage").form("clear");
           	$("#storage-purchaseRejectOut-auxunitname1").html("");
           	$("#storage-purchaseRejectOut-goodsunitname1").html("");
    		$("#storage-purchaseRejectOut-goodsid").focus();
		}
   </script>
  </body>
</html>
