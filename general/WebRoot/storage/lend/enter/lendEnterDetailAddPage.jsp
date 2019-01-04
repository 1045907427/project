<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>还货单明细添加</title>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
	   	<form action="" method="post" id="storage-form-lendDetailAddPage">
	   		<table  border="0" class="box_table">
	   			<tr>
	   				<td width="120">选择商品:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-goodsid" name="goodsid" width="180"/>
	   				</td>
	   				<td width="120">条形码:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-goodsbarcode" class="no_input" readonly="readonly"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>数量:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-unitnum" name="unitnum" class="easyui-validatebox" data-options="required:true,validType:['intOrFloatNum[${decimallen}]','min']"/>
	   				</td>
	   				<td>辅数量:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-unitnum-aux" name="auxnum" style="width:70px;" class="easyui-validatebox" data-options="required:true,validType:'integer'"/>
	   					<span id="storage-lend-auxunitname1" style="float: left;"></span>
	   					<input type="text" id="storage-lend-unitnum-unit" name="auxremainder" style="width:70px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
	   					<span id="storage-lend-goodsunitname1" style="float: left;"></span>
	   					<input type="hidden" id="storage-lend-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>商品品牌:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-goodsbrandName" class="no_input" readonly="readonly"/>
	   				</td>
	   				<td>箱装量:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-goodsboxnum" class="no_input" readonly="readonly"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>主单位:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
	   					<input type="hidden" id="storage-lend-goodsunitid" name="unitid"/>
	   				</td>
	   				<td>辅单位:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-auxunitname" name="auxunitname" class="no_input" readonly="readonly"/>
	   					<input type="hidden" id="storage-lend-auxunitid" name="auxunitid"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>含税单价:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-taxprice" name="taxprice" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'">
	   				</td>
	   				<td>含税金额:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-taxamount" name="taxamount" class="no_input" readonly="readonly"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>未税单价:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-notaxprice" name="notaxprice" class="no_input" readonly="readonly"/>
	   				</td>
	   				<td>未税金额:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-notaxamount" name="notaxamount" class="no_input" readonly="readonly"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>税种:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-taxtypename" name="taxtypename" class="no_input" readonly="readonly"/>
	   					<input type="hidden" id="storage-lend-taxtype" name="taxtype" class="no_input" readonly="readonly"/>
	   				</td>
	   				<td>税额:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-tax" name="tax" class="no_input" readonly="readonly"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>生产日期:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-produceddate" style="height: 20px;" name="produceddate" class="WdateRead" readonly="readonly"/>
	   				</td>
	   				<td>截止日期:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-deadline" style="height: 20px;" name="deadline" class="WdateRead" readonly="readonly"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>批次号:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-batchno" name="batchno" class="no_input" readonly="readonly"/>
	   				</td>
	   				<td>所属库位:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-storagelocationid" name="storagelocationid" class="no_input" />
	   					<input type="hidden" id="storage-lend-storagelocationname"  name="storagelocationname"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>备注:</td>
	   				<td colspan="3" style="text-align: left;">
	   					<input id="storage-lend-remark" type="text" name="remark" style="width: 488px;" maxlength="200"/>
	   				</td>
	   			</tr>
	   		</table>
	    </form>
	    </div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="height:30px;text-align: right;">
  				快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
	  			<input type="button" value="继续添加" name="savegoon" id="storage-savegoon-lendDetailAddPage" />
  			</div>
  		</div>
    </div>
   <script type="text/javascript">
		$(function(){
			$.extend($.fn.validatebox.defaults.rules, {
				min:{
					validator: function (value) {
						if(value == 0){
							return false;
						}else{
							return true;
						}
					},
					message:'数量不可为0'
				}
			});
			$("#storage-lend-goodsid").goodsWidget({
				required:true,
				onSelect:function(data){
					$("#storage-lend-goodsbrandName").val(data.brandName);
					$("#storage-lend-goodsboxnum").val(data.boxnum);
					$("#storage-lend-goodsunitid").val(data.mainunit);
					$("#storage-lend-auxunitid").val(data.meteringunitid);
					$("#storage-lend-taxtype").val(data.defaulttaxtype);
					$("#storage-lend-goodsbarcode").val(data.barcode);
					$("#storage-lend-taxprice").val(data.newbuyprice);
					//商品进行批次管理时
					if(data.isbatch=="1"){
						$("#storage-lend-storagelocationid").widget("clear");
						$("#storage-lend-storagelocationid").widget("enable");
						$('#storage-lend-batchno').validatebox({required:true});
						$("#storage-lend-batchno").removeClass("no_input");
						
						$('#storage-lend-produceddate').validatebox({required:true});
						$("#storage-lend-produceddate").removeClass("WdateRead");
						$("#storage-lend-produceddate").addClass("Wdate");
						$("#storage-lend-produceddate").removeAttr("readonly");
						$("#storage-lend-deadline").removeClass("WdateRead");
						$("#storage-lend-deadline").addClass("Wdate");
						$("#storage-lend-deadline").removeAttr("readonly");
						
					}else{
						
						$("#storage-lend-storagelocationid").widget("clear");
						$("#storage-lend-storagelocationid").widget("disable");
						
						$('#storage-lend-batchno').validatebox({required:false});
						$("#storage-lend-batchno").addClass("no_input");
						
						$('#storage-lend-produceddate').validatebox({required:false});
						$("#storage-lend-produceddate").removeClass("Wdate");
						$("#storage-lend-produceddate").addClass("WdateRead");
						$("#storage-lend-produceddate").attr("readonly","readonly");
						
						$("#storage-lend-deadline").removeClass("Wdate");
						$("#storage-lend-deadline").addClass("WdateRead");
						$("#storage-lend-deadline").attr("readonly","readonly");
					}
					computNum();
			        $("#storage-lend-unitnum").focus();
			        $("#storage-lend-unitnum").select();
				},
				onClear:function(){
					$("#storage-lend-storagelocationid").widget("clear");
					$("#storage-lend-storagelocationid").widget("disable");
					
					$('#storage-lend-batchno').validatebox({required:false});
					$("#storage-lend-batchno").addClass("no_input");
					
					$('#storage-lend-produceddate').validatebox({required:false});
					$("#storage-lend-produceddate").removeClass("Wdate");
					$("#storage-lend-produceddate").addClass("WdateRead");
					$("#storage-lend-produceddate").attr("readonly","readonly");
					
					$("#storage-lend-deadline").removeClass("Wdate");
					$("#storage-lend-deadline").addClass("WdateRead");
					$("#storage-lend-deadline").attr("readonly","readonly");
					$('#storage-lend-produceddate').val("");
					$('#storage-lend-batchno').val("");
	            	$('#storage-lend-deadline').val("");
				}
			});
			$("#storage-lend-storagelocationid").widget({
				name:'t_storage_salereject_enter_detail',
	    		width:165,
				col:'storagelocationid',
				singleSelect:true,
				disabled:true,
				onSelect:function(data){
					$("#storage-lend-storagelocationname").val(data.name);
				},
				onClear:function(){
					$("#storage-lend-storagelocationname").val("");
				}
			});
			$("#storage-lend-taxprice").change(function(){
				computNum();
			});
			$("#storage-lend-taxamount").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#storage-lend-notaxprice").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#storage-lend-notaxamount").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#storage-lend-tax").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#storage-lend-produceddate").click(function(){
				if(!$("#storage-lend-batchno").hasClass("no_input")){
					WdatePicker({dateFmt:'yyyy-MM-dd',
						onpicked:function(dp){
							if(dp.el.id=="storage-lend-produceddate"){
								var produceddate = dp.cal.getDateStr();
								var goodsid = $("#storage-lend-goodsid").goodsWidget("getValue");
								$.ajax({   
						            url :'storage/getBatchno.do',
						            type:'post',
						            data:{produceddate:produceddate,goodsid:goodsid},
						            dataType:'json',
						            async:false,
						            success:function(json){
						            	$('#storage-lend-batchno').val(json.batchno);
						            	$('#storage-lend-deadline').val(json.deadline);
						            }
						        });
							}
						}
					});
				}
			});
            $("#storage-lend-deadline").click(function(){
                if(!$("#storage-lend-batchno").hasClass("no_input")){
                    WdatePicker({dateFmt:'yyyy-MM-dd',
                        onpicked:function(dp){
                            if(dp.el.id=="storage-lend-deadline"){
                                var deadline = dp.cal.getDateStr();
                                var goodsid = $("#storage-lend-goodsid").goodsWidget("getValue");
                                $.ajax({
                                    url :'storage/getBatchnoByDeadline.do',
                                    type:'post',
                                    data:{deadline:deadline,goodsid:goodsid},
                                    dataType:'json',
                                    async:false,
                                    success:function(json){
                                        $('#storage-lend-batchno').val(json.batchno);
                                        $('#storage-lend-produceddate').val(json.produceddate);
                                    }
                                });
                            }
                        }
                    });
                }
            });
			$("#storage-lend-unitnum").change(function(){
				computNum();
			});
			$("#storage-lend-unitnum-aux").change(function(){
				computNumByAux();
			});
			$("#storage-lend-unitnum-unit").change(function(){
				computNumByAux();
			});
			$("#storage-lend-unitnum").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#storage-lend-unitnum").blur();
		   			$("#storage-lend-unitnum-aux").focus();
		   			$("#storage-lend-unitnum-aux").select();
				}
			});
			$("#storage-lend-unitnum-aux").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-lend-unitnum-auxm").blur();
		   			$("#storage-lend-unitnum-unit").focus();
		   			$("#storage-lend-unitnum-unit").select();
				}
			});
			$("#storage-lend-unitnum-unit").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-lend-unitnum-auxm").blur();
		   			$("#storage-lend-taxprice").focus();
		   			$("#storage-lend-taxprice").select();
				}
			});
			$("#storage-lend-taxprice").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-lend-taxprice").blur();
		   			$("#storage-lend-remark").focus();
		   			$("#storage-lend-remark").select();
				}
			});
			$("#storage-lend-remark").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-lend-taxprice").blur();
		   			$("#storage-savegoon-lendDetailAddPage").focus();
				}
			});
			$("#storage-savegoon-lendDetailAddPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					addSaveDetail(true);
				}
			});
		    $("#storage-savegoon-lendDetailAddPage").click(function(){
		    	addSaveDetail(true);
		    });
		});
		//通过总数量 计算数量 金额换算
		function computNum(){
			var goodsInfo= $("#storage-lend-goodsid").goodsWidget("getObject");
			if(null==goodsInfo){
				return false;
			}
			var auxunitid = $("#storage-lend-auxunitid").val();
			var unitnum = $("#storage-lend-unitnum").val();
			var taxprice = $("#storage-lend-taxprice").val();
			var notaxprice = $("#storage-lend-notaxprice").val();
			var taxtype = $("#storage-lend-taxtype").val();
			$("#storage-lend-unitnum").addClass("inputload");
			$.ajax({   
	            url :'basefiles/computeGoodsByUnitnum.do',
	            type:'post',
	            data:{unitnum:unitnum,goodsid:goodsInfo.id,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#storage-lend-taxamount").numberbox("setValue",json.taxamount);
	            	$("#storage-lend-notaxamount").numberbox("setValue",json.notaxamount);
	            	$("#storage-lend-tax").numberbox("setValue",json.tax);
	            	$("#storage-lend-taxtypename").val(json.taxtypename);
	            	$("#storage-lend-auxunitnumdetail").val(json.auxnumdetail);
	            	$("#storage-lend-auxunitnum").val(json.auxnum);
	            	$("#storage-lend-auxunitname").val(json.auxunitname);
	            	$("#storage-lend-auxunitname1").html(json.auxunitname);
	            	$("#storage-lend-goodsunitname").val(json.unitname);
	            	$("#storage-lend-goodsunitname1").html(json.unitname);
	            	
	            	$("#storage-lend-taxprice").val(json.taxprice);
	            	$("#storage-lend-notaxprice").numberbox("setValue",json.notaxprice);
	            	$("#storage-lend-notaxamount").numberbox("setValue",json.notaxamount);
	            	
	            	$("#storage-lend-unitnum-aux").val(json.auxInteger);
	            	$("#storage-lend-unitnum-unit").val(json.auxremainder);
	            	if(json.auxrate!=null){
	            		$("#storage-lend-unitnum-unit").attr("max",json.auxrate-1);
	            	}
	            	$("#storage-lend-unitnum").removeClass("inputload");
	            }
	        });
		}
		//通过辅单位数量
		function computNumByAux(){
			var goodsInfo= $("#storage-lend-goodsid").goodsWidget("getObject");
			if(null==goodsInfo){
				return false;
			}
			var auxunitid = $("#storage-lend-auxunitid").val();
			var taxprice = $("#storage-lend-taxprice").val();
			var notaxprice = $("#storage-lend-notaxprice").numberbox("getValue");
			var taxtype = $("#storage-lend-taxtype").val();
			var auxInterger = $("#storage-lend-unitnum-aux").val();
			var auxremainder = $("#storage-lend-unitnum-unit").val();
//			var auxmax = $("#storage-lend-unitnum-unit").attr("max");
//			if(Number(auxremainder)>Number(auxmax)){
//				auxremainder = auxmax;
//				$("#storage-lend-unitnum-unit").val(auxremainder);
//			}
			$("#storage-lend-unitnum").addClass("inputload");
			$.ajax({   
	            url :'basefiles/computeGoodsByAuxnum.do',
	            type:'post',
	            data:{auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsInfo.id,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#storage-lend-taxamount").numberbox("setValue",json.taxamount);
	            	$("#storage-lend-notaxamount").numberbox("setValue",json.notaxamount);
	            	$("#storage-lend-tax").numberbox("setValue",json.tax);
	            	$("#storage-lend-taxtypename").val(json.taxtypename);
	            	
	            	$("#storage-lend-taxprice").val(json.taxprice);
	            	$("#storage-lend-notaxprice").numberbox("setValue",json.notaxprice);
	            	$("#storage-lend-notaxamount").numberbox("setValue",json.notaxamount);
	            	
	            	$("#storage-lend-unitnum").val(json.mainnum);
	            	$("#storage-lend-unitnum").removeClass("inputload");

                    $("#storage-lend-unitnum-unit").val(json.auxremainder);
                    $("#storage-lend-unitnum-aux").val(json.auxInterger);
					$("#storage-lend-auxunitnumdetail").val(json.auxnumdetail);

	            }
	        });
		}
		//页面重置
		function otherEnterformReset(){
			$("#storage-form-lendDetailAddPage").form("clear");
           	$("#storage-lend-auxunitname1").html("");
           	$("#storage-lend-goodsunitname1").html("");
    		$("#storage-lend-goodsid").focus();
		}
		//默认禁用所属库位
		$("#storage-lend-storagelocationid").widget("disable");
   </script>
  </body>
</html>
