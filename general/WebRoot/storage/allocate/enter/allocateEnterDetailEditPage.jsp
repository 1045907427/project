<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>调拨入库单明细添加</title>
  </head>
  
  <body>
   	<form action="" method="post" id="storage-form-allocateEnterDetailAddPage">
   		<table  border="0" class="box_table">
   			<tr>
   				<td width="120">商品:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-allocateEnter-goodsname" width="180" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-allocateEnter-goodsid" name="goodsid" width="170"/>
   				</td>
   				<td id="storage-allocateEnter-loadInfo" colspan="2" style="text-align: left;"></td>
   			</tr>
   			<tr>
   				<td>数量:</td>
   				<td colspan="3" style="text-align: left;">
   					<input type="text" id="storage-allocateEnter-unitnum" name="unitnum" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td width="120">商品品牌:</td>
   				<td>
   					<input type="text" id="storage-allocateEnter-goodsbrandName" class="no_input" readonly="readonly"/>
   				</td>
   				<td width="120">规格型号:</td>
   				<td>
   					<input type="text" id="storage-allocateEnter-goodsmodel" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>计量单位:</td>
   				<td colspan="3" style="text-align: left;">
   					<input type="text" id="storage-allocateEnter-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-allocateEnter-goodsunitid" name="unitid"/>
   				</td>
   			</tr>
   			<tr>
   				<td>辅单位:</td>
   				<td>
   					<input type="text" id="storage-allocateEnter-auxunitname" name="auxunitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-allocateEnter-auxunitid" name="auxunitid"/>
   				</td>
   				<td>辅单位数量:</td>
   				<td>
   					<input type="text" id="storage-allocateEnter-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-allocateEnter-auxunitnum" name="auxnum"/>
   				</td>
   			</tr>
   			<tr>
   				<td>含税单价:</td>
   				<td>
   					<input type="text" id="storage-allocateEnter-taxprice" name="taxprice" class="no_input" readonly="readonly">
   				</td>
   				<td>含税金额:</td>
   				<td>
   					<input type="text" id="storage-allocateEnter-taxamount" name="taxamount" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>无税单价:</td>
   				<td>
   					<input type="text" id="storage-allocateEnter-notaxprice" name="notaxprice" class="no_input" readonly="readonly"/>
   				</td>
   				<td>无税金额:</td>
   				<td>
   					<input type="text" id="storage-allocateEnter-notaxamount" name="notaxamount" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>税种:</td>
   				<td>
   					<input type="text" id="storage-allocateEnter-taxtypename" name="taxtypename" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-allocateEnter-taxtype" name="taxtype" class="no_input" readonly="readonly"/>
   				</td>
   				<td>税额:</td>
   				<td>
   					<input type="text" id="storage-allocateEnter-tax" name="tax" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>所属库位:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-allocateEnter-storagelocationid" name="storagelocationid" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-allocateEnter-storagelocationname"  name="storagelocationname"/>
   				</td>
   				<td>批次号:</td>
   				<td>
   					<input type="text" id="storage-allocateEnter-batchno" name="batchno" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>生产日期:</td>
   				<td>
   					<input type="text" id="storage-allocateEnter-produceddate" style="height: 20px;" name="produceddate" class="no_input Wdate" readonly="readonly"/>
   				</td>
   				<td>有效截止日期:</td>
   				<td>
   					<input type="text" id="storage-allocateEnter-deadline" style="height: 20px;" name="deadline" class="no_input Wdate" readonly="readonly"/>
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
   		//加载数据
		var object = $("#storage-datagrid-allocateEnterAddPage").datagrid("getSelected");
		$("#storage-form-allocateEnterDetailAddPage").form("load",object);
		$("#storage-allocateEnter-goodsname").val(object.goodsInfo.name);
		$("#storage-allocateEnter-goodsbrandName").val(object.goodsInfo.brandName);
		$("#storage-allocateEnter-goodsmodel").val(object.goodsInfo.model);
		
		$(function(){
			$("#storage-allocateEnter-unitnum").numberbox({
				precision:2,
				groupSeparator:',',
				required:true,
				onChange:function(newValue,oldValue){
					var goodsid= $("#storage-allocateEnter-goodsid").val();
					var auxunitid = $("#storage-allocateEnter-auxunitid").val();
					var unitnum = $("#storage-allocateEnter-unitnum").numberbox("getValue");
					var taxprice = $("#storage-allocateEnter-taxprice").numberbox("getValue");
					var notaxprice = $("#storage-allocateEnter-notaxprice").numberbox("getValue");
					var taxtype = $("#storage-allocateEnter-taxtype").val();
					$("#storage-allocateEnter-unitnum").addClass("inputload");
					
					$.ajax({   
			            url :'storage/computeAllocateNoticeDetailNum.do',
			            type:'post',
			            data:{unitnum:unitnum,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
			            dataType:'json',
			            async:false,
			            success:function(json){
			            	$("#storage-allocateEnter-taxamount").numberbox("setValue",json.taxamount);
			            	$("#storage-allocateEnter-notaxamount").numberbox("setValue",json.notaxamount);
			            	$("#storage-allocateEnter-tax").numberbox("setValue",json.tax);
			            	$("#storage-allocateEnter-taxtypename").val(json.taxtypename);
			            	$("#storage-allocateEnter-auxunitnumdetail").val(json.auxnumdetail);
			            	$("#storage-allocateEnter-auxunitnum").val(json.auxnum);
			            	$("#storage-allocateEnter-auxunitname").val(json.auxunitname);
			            	
			            	$("#storage-allocateEnter-notaxprice").numberbox("setValue",json.notaxprice);
			            	$("#storage-allocateEnter-notaxamount").numberbox("setValue",json.notaxamount);
			            	
			            	$("#storage-allocateEnter-unitnum").removeClass("inputload");
			            }
			        });
				}
			});
			$("#storage-allocateEnter-taxprice").numberbox({
				precision:2,
				groupSeparator:',',
				required:true,
				onChange:function(newValue,oldValue){
					var goodsid= $("#storage-allocateEnter-goodsid").val();
					var auxunitid = $("#storage-allocateEnter-auxunitid").val();
					var unitnum = $("#storage-allocateEnter-unitnum").numberbox("getValue");
					var taxprice = $("#storage-allocateEnter-taxprice").numberbox("getValue");
					var notaxprice = $("#storage-allocateEnter-notaxprice").numberbox("getValue");
					var taxtype = $("#storage-allocateEnter-taxtype").val();
					$("#storage-allocateEnter-unitnum").addClass("inputload");
					
					$.ajax({   
			            url :'storage/computeAllocateNoticeDetailNum.do',
			            type:'post',
			            data:{unitnum:unitnum,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice,customerid:customerid},
			            dataType:'json',
			            async:false,
			            success:function(json){
			            	$("#storage-allocateEnter-taxamount").numberbox("setValue",json.taxamount);
			            	$("#storage-allocateEnter-notaxamount").numberbox("setValue",json.notaxamount);
			            	$("#storage-allocateEnter-tax").numberbox("setValue",json.tax);
			            	$("#storage-allocateEnter-taxtypename").val(json.taxtypename);
			            	$("#storage-allocateEnter-auxunitnumdetail").val(json.auxnumdetail);
			            	$("#storage-allocateEnter-auxunitnum").val(json.auxnum);
			            	$("#storage-allocateEnter-auxunitname").val(json.auxunitname);
			            	
			            	$("#storage-allocateEnter-notaxprice").numberbox("setValue",json.notaxprice);
			            	$("#storage-allocateEnter-notaxamount").numberbox("setValue",json.notaxamount);
			            	
			            	$("#storage-allocateEnter-unitnum").removeClass("inputload");
			            }
			        });
				}
			});
			$("#storage-allocateEnter-taxamount").numberbox({
				precision:2,
				groupSeparator:',',
				required:true
			});
			$("#storage-allocateEnter-notaxprice").numberbox({
				precision:2,
				groupSeparator:',',
				required:true
			});
			$("#storage-allocateEnter-notaxamount").numberbox({
				precision:2,
				groupSeparator:',',
				required:true
			});
			$("#storage-allocateEnter-tax").numberbox({
				precision:2,
				groupSeparator:',',
				required:true
			});
			$("#storage-allocateEnter-deadline").click(function(){
				if(!$("#storage-allocateEnter-batchno").hasClass("no_input")){
					WdatePicker({dateFmt:'yyyy-MM-dd'});
				}
			});
			$("#storage-allocateEnter-produceddate").click(function(){
				if(!$("#storage-allocateEnter-batchno").hasClass("no_input")){
					WdatePicker({dateFmt:'yyyy-MM-dd'});
				}
			});
		});
		//控件采购退货的最大数量
		$.ajax({   
            url :'storage/getAllocateOutDetailInfo.do',
            type:'post',
            data:{id:object.sourceid,detailid:object.sourcedetailid},
            dataType:'json',
            success:function(json){
            	if(json.allocateOutDetail!=null){
	            	$("#storage-allocateEnter-loadInfo").html("调拨出库单中调出数量：<font color='green'>"+json.allocateOutDetail.unitnum+json.allocateOutDetail.unitname+"</font>");
	            	$("#storage-allocateEnter-unitnum").numberbox({max:json.allocateOutDetail.unitnum});
            	}
            	//商品是库位管理时，需要指定入库库位
            	if(json.isstoragelocation){
            		$("#storage-allocateEnter-storagelocationid").removeClass("no_input");
            		$("#storage-allocateEnter-storagelocationid").widget({
            			name:'t_storage_allocate_enter_detail',
			    		width:160,
						col:'storagelocationid',
						singleSelect:true,
						required:true,
						onSelect:function(data){
							$("#storage-allocateEnter-storagelocationname").val(data.name);
						}
            		});
            		$("#storage-allocateEnter-storagelocationid").widget("readonly",false);
            	}
            }
        });
   </script>
  </body>
</html>
