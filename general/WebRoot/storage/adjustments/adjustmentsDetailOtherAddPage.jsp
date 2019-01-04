<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>调账单明细添加</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
 	<div data-options="region:'center',border:false">
   	<form action="" method="post" id="storage-form-adjustmentsDetailAddPage">
   		<table  border="0" class="box_table">
   			<tr>
   				<td width="120">选择商品:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-adjustments-goodsid" name="goodsid" width="180"/>
   					<input type="hidden" id="storage-adjustments-summarybatchid" name="summarybatchid"/>
   				</td>
   				<td>条形码:</td>
   				<td>
   					<input type="text" id="storage-adjustments-goodsbarcode" name="barcode" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>调账数量:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-adjustments-adjustnum" name="adjustnum" class="easyui-validatebox" data-options="required:true"/>
   				</td>
   				<td>调账辅数量:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-adjustments-adjustnum-aux" name="auxadjustnum" style="width:60px;" class="easyui-validatebox" data-options="required:true"/>
   					<span id="storage-adjustments-auxunitname1" style="float: left;"></span>
   					<input type="text" id="storage-adjustments-adjustnum-unit" name="auxadjustremainder" style="width:60px;" class="easyui-validatebox" data-options="required:true"/>
   					<span id="storage-adjustments-goodsunitname1" style="float: left;"></span>
   					<input type="hidden" id="storage-adjustments-auxunitnumdetail" name="auxadjustnumdetail" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td width="120">商品品牌:</td>
   				<td>
   					<input type="text" id="storage-adjustments-goodsbrandName" class="no_input" readonly="readonly"/>
   				</td>
   				<td width="120">规格型号:</td>
   				<td>
   					<input type="text" id="storage-adjustments-goodsmodel" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>单位:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-adjustments-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-adjustments-goodsunitid" name="unitid"/>
   				</td>
   				<td>辅单位:</td>
   				<td>
   					<input type="text" id="storage-adjustments-auxunitname" name="auxunitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-adjustments-auxunitid" name="auxunitid"/>
   				</td>
   			</tr>
   			<%--<tr>--%>
   				<%--<td>含税单价:</td>--%>
   				<%--<td>--%>
   					<%--<input type="text" id="storage-adjustments-price" name="price" class="no_input" readonly="readonly"/>--%>
   				<%--</td>--%>
   				<%--<td>含税金额:</td>--%>
   				<%--<td>--%>
   					<%--<input type="text" id="storage-adjustments-amount" name="amount" class="no_input" readonly="readonly"/>--%>
   				<%--</td>--%>
   			<%--</tr>--%>
			<tr>
				<td>含税单价:</td>
				<td style="text-align: left;">
					<input type="text" id="storage-adjustments-price" name="price" class="no_input" readonly="readonly"/>
				</td>
				<td>含税金额:</td>
				<td style="text-align: left;">
					<input type="text" id="storage-adjustments-amount" name="amount" class="no_input" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>未税单价:</td>
				<td style="text-align: left;">
					<input type="text" id="storage-adjustments-notaxprice" name="notaxprice" class="no_input" readonly="readonly"/>
				</td>
				<td>未税金额:</td>
				<td style="text-align: left;">
					<input type="text" id="storage-adjustments-notaxamount" name="notaxamount" class="no_input" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>税种:</td>
				<td style="text-align: left;">
					<input type="text" id="storage-adjustments-taxtypename" name="taxtypename" class="no_input" readonly="readonly"/>
					<input type="hidden" id="storage-adjustments-taxtype" name="taxtype" class="no_input" readonly="readonly"/>
				</td>
				<td>税额:</td>
				<td style="text-align: left;">
					<input type="text" id="storage-adjustments-tax" name="tax" class="no_input" readonly="readonly"/>
				</td>
			</tr>
   			<tr>
   				<td>所属库位:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-adjustments-storagelocationid" name="storagelocationid" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-adjustments-storagelocationname"  name="storagelocationname"/>
   				</td>
   				<td>批次号:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-adjustments-batchno" name="batchno" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>生产日期:</td>
   				<td>
   					<input type="text" id="storage-adjustments-produceddate" name="produceddate" class="no_input" readonly="readonly"/>
   				</td>
   				<td>有效截止日期:</td>
   				<td>
   					<input type="text" id="storage-adjustments-deadline" name="deadline" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>备注:</td>
   				<td colspan="3" style="text-align: left;">
   					<input id="storage-adjustmentsdetial-remark" type="text" name="remark" style="width: 400px;" maxlength="200"/>
   				</td>
   			</tr>
   		</table>
    </form>
    </div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="text-align: right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
			<input type="button" value="继续添加" name="savegoon" id="storage-savegoon-adjustmentsDetailAddPage" />
		</div>
	</div>
   <script type="text/javascript">
		$(function(){
			$("#storage-adjustments-goodsid").goodsWidget({
				required:true,
				onSelect:function(data){
					$("#storage-adjustments-goodsbrandName").val(data.brandname);
					$("#storage-adjustments-goodsmodel").val(data.model);
					$("#storage-adjustments-goodsbarcode").val(data.barcode);
					$("#storage-adjustments-goodsunitid").val(data.mainunit);
					$("#storage-adjustments-auxunitid").val(data.meteringunitid);
					$("#storage-adjustments-price").numberbox("setValue",data.newbuyprice);
                    $("#storage-adjustments-taxtype").val(data.defaulttaxtype);

					//商品进行批次管理时
					if(data.isbatch=="1"){
						$("#storage-adjustments-storagelocationid").widget("clear");
						$("#storage-adjustments-storagelocationid").widget("disable");
						$("#storage-adjustments-storagelocationid").addClass("no_input");
						$("#storage-adjustments-storagelocationid").attr("readonly","readonly");
						if(null!=data.storagelocation && ""!=data.storagelocation){
							$("#storage-adjustments-storagelocationid").widget("setValue",data.storagelocation);
						}else{
							$("#storage-adjustments-storagelocationid").widget("clear");
						}
						$('#storage-adjustments-batchno').validatebox({required:true});
						$("#storage-adjustments-batchno").removeClass("no_input");
						$("#storage-adjustments-batchno").removeAttr("readonly");
						
						$('#storage-adjustments-produceddate').validatebox({required:true});
						$("#storage-adjustments-produceddate").removeClass("no_input");
						$("#storage-adjustments-produceddate").removeAttr("readonly");
						if(data.isshelflife=='1'){
							$('#storage-adjustments-deadline').validatebox({required:true});
						}
						$("#storage-adjustments-deadline").removeClass("no_input");
						$("#storage-adjustments-deadline").removeAttr("readonly");
						
					}else if(data.isstoragelocation=='1'){
						//商品进行库位管理时
						$("#storage-adjustments-storagelocationid").removeClass("no_input");
						$("#storage-adjustments-storagelocationid").removeAttr("readonly");
						
						$("#storage-adjustments-storagelocationid").widget("enable");
						if(null!=data.storagelocation && ""!=data.storagelocation){
							$("#storage-adjustments-storagelocationid").widget("setValue",data.storagelocation);
						}else{
							$("#storage-adjustments-storagelocationid").widget("clear");
						}
						$('#storage-adjustments-storagelocationid').validatebox({required:true});
						
						$('#storage-adjustments-batchno').validatebox({required:false});
						$("#storage-adjustments-batchno").addClass("no_input");
						$("#storage-adjustments-batchno").attr("readonly","readonly");
						
						$('#storage-adjustments-produceddate').validatebox({required:false});
						$("#storage-adjustments-produceddate").addClass("no_input");
						$("#storage-adjustments-produceddate").attr("readonly","readonly");
						
						$('#storage-adjustments-deadline').validatebox({required:false});
						$("#storage-adjustments-deadline").addClass("no_input");
						$("#storage-adjustments-deadline").attr("readonly","readonly");
					}else{
						
						$("#storage-adjustments-storagelocationid").widget("clear");
						$("#storage-adjustments-storagelocationid").widget("disable");
						$("#storage-adjustments-storagelocationid").addClass("no_input");
						$("#storage-adjustments-storagelocationid").attr("readonly","readonly");
						
						$('#storage-adjustments-batchno').validatebox({required:false});
						$("#storage-adjustments-batchno").addClass("no_input");
						$("#storage-adjustments-batchno").attr("readonly","readonly");
						
						$('#storage-adjustments-produceddate').validatebox({required:false});
						$("#storage-adjustments-produceddate").addClass("no_input");
						$("#storage-adjustments-produceddate").attr("readonly","readonly");
						
						$('#storage-adjustments-deadline').validatebox({required:false});
						$("#storage-adjustments-deadline").addClass("no_input");
						$("#storage-adjustments-deadline").attr("readonly","readonly");
					}
					
					computNum();
					$("#storage-adjustments-adjustnum").focus();
					$("#storage-adjustments-adjustnum").select();
				}
				
			});
			$("#storage-adjustments-storagelocationid").widget({
				name:'t_storage_adjustments_detail',
	    		width:165,
				col:'storagelocationid',
				singleSelect:true,
				onSelect:function(data){
					$("#storage-adjustments-storagelocationname").val(data.name);
				},
				onClear:function(){
					$("#storage-adjustments-storagelocationname").val("");
				}
			});
			//调账数量
			$("#storage-adjustments-adjustnum").change(function(){
				computNum();
			});
			$("#storage-adjustments-adjustnum-aux").change(function(){
				computNumByAux();
			});
			$("#storage-adjustments-adjustnum-unit").change(function(){
				computNumByAux();
			});
			//单价
			$("#storage-adjustments-price").numberbox({
				precision:6,
				groupSeparator:','
			});
			//金额
			$("#storage-adjustments-amount").numberbox({
				precision:2,
				groupSeparator:','
			});
            $("#storage-adjustments-notaxprice").numberbox({
                precision:6,
                groupSeparator:','
            });
            $("#storage-adjustments-notaxamount").numberbox({
                precision:2,
                groupSeparator:','
            });
            $("#storage-adjustments-tax").numberbox({
                precision:2,
                groupSeparator:','
            });
			$("#storage-adjustments-adjustnum").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#storage-adjustments-adjustnum").blur();
		   			$("#storage-adjustments-adjustnum-aux").focus();
		   			$("#storage-adjustments-adjustnum-aux").select();
				}
			});
			$("#storage-adjustments-adjustnum-aux").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-adjustments-adjustnum-auxm").blur();
		   			$("#storage-adjustments-adjustnum-unit").focus();
		   			$("#storage-adjustments-adjustnum-unit").select();
				}
			});
			$("#storage-adjustments-adjustnum-unit").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-adjustments-adjustnum-auxm").blur();
		   			$("#storage-adjustmentsdetial-remark").focus();
		   			$("#storage-adjustmentsdetial-remark").select();
				}
			});
			$("#storage-adjustmentsdetial-remark").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-allocateOut-taxprice").blur();
		   			$("#storage-savegoon-adjustmentsDetailAddPage").focus();
				}
			});
			$("#storage-savegoon-adjustmentsDetailAddPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					addSaveDetail(true);
				}
			});
		    $("#storage-savegoon-adjustmentsDetailAddPage").click(function(){
		    	addSaveDetail(true);
		    });
		});
		//通过总数量 计算数量 金额换算
		function computNum(){
			var goodsid= $("#storage-adjustments-goodsid").goodsWidget("getValue");
			if(null==goodsid){
				return false;
			}
			var auxunitid = $("#storage-adjustments-auxunitid").val();
			var unitnum = $("#storage-adjustments-adjustnum").val();
			var taxprice = $("#storage-adjustments-price").val();
			var taxtype = '';
			$("#storage-adjustments-adjustnum").addClass("inputload");
			$.ajax({   
	            url :'basefiles/computeGoodsByUnitnum.do',
	            type:'post',
	            data:{unitnum:unitnum,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#storage-adjustments-amount").numberbox("setValue",json.taxamount);
	            	$("#storage-adjustments-auxunitnumdetail").val(json.auxnumdetail);
	            	$("#storage-adjustments-auxunitnum").val(json.auxnum);
	            	$("#storage-adjustments-auxunitname").val(json.auxunitname);
	            	$("#storage-adjustments-auxunitname1").html(json.auxunitname);
	            	$("#storage-adjustments-goodsunitname").val(json.unitname);
	            	$("#storage-adjustments-goodsunitname1").html(json.unitname);
	            	
	            	$("#storage-adjustments-price").val(json.taxprice);
                    $("#storage-adjustments-notaxprice").numberbox("setValue",json.notaxprice);
                    $("#storage-adjustments-notaxamount").numberbox("setValue",json.notaxamount);
                    $("#storage-adjustments-tax").numberbox("setValue",json.tax);
                    $("#storage-adjustments-taxtypename").val(json.taxtypename);
	            	
	            	$("#storage-adjustments-adjustnum-aux").val(json.auxInteger);
	            	$("#storage-adjustments-adjustnum-unit").val(json.auxremainder);
	            	if(json.auxrate!=null){
	            		$("#storage-adjustments-adjustnum-unit").attr("max",json.auxrate-1);
	            	}
	            	$("#storage-adjustments-adjustnum").removeClass("inputload");
	            }
	        });
		}
		//通过辅单位数量
		function computNumByAux(){
			var goodsid= $("#storage-adjustments-goodsid").goodsWidget("getValue");
			if(null==goodsid){
				return false;
			}
			var auxunitid = $("#storage-adjustments-auxunitid").val();
			var taxprice = $("#storage-adjustments-price").val();
			var taxtype = '';
			var auxInterger = $("#storage-adjustments-adjustnum-aux").val();
			var auxremainder = $("#storage-adjustments-adjustnum-unit").val();
			var auxmax = $("#storage-adjustments-adjustnum-unit").attr("max");
			if(Number(auxremainder)>Number(auxmax)){
				auxremainder = auxmax;
				$("#storage-adjustments-adjustnum-unit").val(auxremainder);
			}
			$("#storage-adjustments-adjustnum").addClass("inputload");
			$.ajax({   
	            url :'basefiles/computeGoodsByAuxnum.do',
	            type:'post',
	            data:{auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#storage-adjustments-amount").numberbox("setValue",json.taxamount);
	            	
	            	$("#storage-adjustments-price").val(json.taxprice);
	            	$("#storage-adjustments-notaxamount").numberbox("setValue",json.notaxamount);
	            	
	            	$("#storage-adjustments-adjustnum").val(json.mainnum);
	            	$("#storage-adjustments-auxunitnumdetail").val(json.auxnumdetail);
	            	$("#storage-adjustments-adjustnum").removeClass("inputload");
	            }
	        });
		}
   </script>
  </body>
</html>
