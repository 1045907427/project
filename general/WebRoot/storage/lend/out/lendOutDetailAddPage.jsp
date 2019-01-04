<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>借货单明细添加</title>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
	   	<form action="" method="post" id="storage-form-lendDetailAddPage">
	   		<table  border="0" class="box_table">
	   			<tr>
	   				<td></td>
	   				<td></td>
	   				<td colspan="2" id="storage-lend-loadInfo" style="text-align: left;">&nbsp;</td>
	   			</tr>
	   			<tr>
	   				<td width="120">选择商品:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-goodsid" name="goodsid" width="180"/>
	   					<input type="hidden" id="storage-lend-summarybatchid" name="summarybatchid">
	   					<input type="hidden" id="storage-lend-goodsname" name="goodsname">
	   				</td>
	   				<td>批次号:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-batchno" name="batchno"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>数量:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-unitnum" name="unitnum" class="easyui-validatebox" data-options="required:true,validType:['intOrFloatNum[${decimallen}]','max','min']"/>
	   				</td>
	   				<td width="120">辅数量:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-unitnum-aux" name="auxnum" style="width:70px;" class="easyui-validatebox" data-options="required:true,validType:'integer'"/>
	   					<span id="storage-lend-auxunitname1" style="float: left;"></span>
	   					<input type="text" id="storage-lend-unitnum-unit" name="auxremainder" style="width:70px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
	   					<span id="storage-lend-goodsunitname1" style="float: left;"></span>
	   					<input type="hidden" id="storage-lend-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>条形码:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-goodsbarcode" name="barcode" class="no_input" readonly="readonly"/>
	   				</td>
	   				<td>箱装量:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-boxnum" class="no_input" readonly="readonly"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>商品品牌:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-goodsbrandName" name="brandName" class="no_input" readonly="readonly"/>
	   				</td>
	   				<td>规格型号:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-goodsmodel" name="model" class="no_input" readonly="readonly"/>
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
	   				<td>所属库位:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-storagelocationname" name="storagelocationname" class="no_input" readonly="readonly"/>
	   					<input type="hidden" id="storage-lend-storagelocationid"  name="storagelocationid"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>生产日期:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-produceddate" style="height: 20px;" class="no_input" name="produceddate" readonly="readonly"/>
	   				</td>
	   				<td>截止日期:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-lend-deadline" style="height: 20px;" class="no_input" name="deadline" readonly="readonly"/>
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
   <script type="text/javascript">
   		var selectStorageid = $("#storage-lend-storageid").widget('getValue');
        var storageInfo = $("#storage-lend-storageid").widget('getObject');
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
			    },
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
                storageid:selectStorageid,
				onSelect:function(data){
					$("#storage-lend-goodsname").val(data.name);
					$("#storage-lend-goodsbrandName").val(data.brandName);
					$("#storage-lend-goodsmodel").val(data.model);
					
					$("#storage-lend-goodsunitid").val(data.mainunit);
					$("#storage-lend-auxunitid").val(data.auxunitid);
					$("#storage-lend-auxunitname").val(data.auxunitname);
	            	$("#storage-lend-auxunitname1").html(data.auxunitname);
	            	$("#storage-lend-goodsunitname").val(data.mainunitName);
	            	$("#storage-lend-goodsunitname1").html(data.mainunitName);
	            	
					$("#storage-lend-taxtype").val(data.taxtype);
					$("#storage-lend-goodsbarcode").val(data.barcode);
					$("#storage-lend-taxprice").val(data.newbuyprice);
					$("#storage-lend-boxnum").val(data.boxnum);
					
					$("#storage-lend-unitnum").val(0);
					
					if(data.isbatch=='1'){
						$("#storage-lend-batchno").widget("enable");
    					var storageid = $("#storage-lend-storageid").widget("getValue");
    					var param = null;
                    	if(storageid!=null && storageid!=""){
                    		param = [{field:'goodsid',op:'equal',value:data.id},
                    		       {field:'storageid',op:'equal',value:storageid}];
                    	}else{
                    		param = [{field:'goodsid',op:'equal',value:data.id}];
                    	}
                        var reqFlag = false;
                        if(storageInfo.isbatch=="1"){
                            reqFlag = true;
                        }else{
                            getGoodsUsenum();
                        }
                    	$("#storage-lend-batchno").widget({
                    		referwid:'RL_T_STORAGE_BATCH_LIST',
                    		param:param,
                			width:165,
                			required:reqFlag,
            				singleSelect:true,
            				onSelect: function(obj){
            					$("#storage-lend-summarybatchid").val(obj.id);
            					$("#storage-lend-storagelocationname").val(obj.storagelocationname);
            					$("#storage-lend-storagelocationid").val(obj.storagelocationid);
            					$("#storage-lend-produceddate").val(obj.produceddate);
            					$("#storage-lend-deadline").val(obj.deadline);
            					
            					maxnum = obj.usablenum;
            					$("#storage-lend-loadInfo").html("现存量：<font color='green'>"+obj.existingnum+obj.unitname+"</font>&nbsp;可用量：<font color='green'>"+ obj.usablenum +obj.unitname+"</font>");
            					computNum();
            					$("#storage-lend-unitnum").focus();
        				        $("#storage-lend-unitnum").select();
            				},
            				onClear:function(){
            					$("#storage-lend-loadInfo").html("&nbsp;");
            					$("#storage-lend-summarybatchid").val("");
            					$("#storage-lend-storagelocationname").val("");
            					$("#storage-lend-storagelocationid").val("");
            					$("#storage-lend-produceddate").val("");
            					$("#storage-lend-deadline").val("");
            				}
                    	});
                    	$("#storage-lend-batchno").focus();
                    	
					}else{
						getGoodsUsenum();
						computNum();
				        $("#storage-lend-unitnum").focus();
				        $("#storage-lend-unitnum").select();
					}
				},
				onClear:function(){
					$("#storage-lend-batchno").widget("clear");
					$("#storage-lend-batchno").widget({
		        		referwid:'RL_T_STORAGE_BATCH_LIST',
		    			width:165,
		    			disabled:true,
						singleSelect:true
					});
					$("#storage-lend-loadInfo").html("&nbsp;");
					$("#storage-lend-summarybatchid").val("");
					$("#storage-lend-storagelocationname").val("");
					$("#storage-lend-storagelocationid").val("");
					$("#storage-lend-produceddate").val("");
					$("#storage-lend-deadline").val("");
				}
			});
			$("#storage-lend-batchno").widget({
        		referwid:'RL_T_STORAGE_BATCH_LIST',
    			width:165,
    			disabled:true,
				singleSelect:true
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
			var goodsid= $("#storage-lend-goodsid").goodsWidget("getValue");
			if(null==goodsid && goodsid!=""){
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
	            data:{unitnum:unitnum,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
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
			var goodsid= $("#storage-lend-goodsid").goodsWidget("getValue");
			if(null==goodsid && goodsid!=""){
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
	            data:{auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
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
                    $("#storage-lend-unitnum-aux").val(json.auxInterger);
                    $("#storage-lend-unitnum-unit").val(json.auxremainder);
                    $("#storage-lend-auxunitnumdetail").val(json.auxnumdetail);
	            	
	            	$("#storage-lend-unitnum").removeClass("inputload");
	            }
	        });
		}
		//页面重置
		function otherEnterformReset(){
			$("#storage-form-lendDetailAddPage").form("clear");
			$("#storage-lend-batchno").widget("clear");
			$("#storage-lend-batchno").widget({
        		referwid:'RL_T_STORAGE_BATCH_LIST',
    			width:165,
    			disabled:true,
				singleSelect:true
			});
           	$("#storage-lend-auxunitname1").html("");
           	$("#storage-lend-goodsunitname1").html("");
    		$("#storage-lend-goodsid").focus();
		}

   </script>
  </body>
</html>
