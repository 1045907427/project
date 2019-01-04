<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>代配送采购单明细添加</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
 	<div data-options="region:'center',border:false">
   	<form action="" method="post" id="delivery-form-deliveryAogorderDetailAddPage">
   		<input type="hidden" id="delivery-deliveryAogorder-goodssort" name="goodssort"/>
   		<input type="hidden" id="delivery-deliveryAogorder-brandid" name="brandid"/>
        <input type="hidden" id="delivery-deliveryAogorder-totalbox" name="totalbox"/>
        
   		<table  border="0" class="box_table">
   			<tr>
   				<td width="120">选择商品:</td>
   				<td style="text-align: left;">
   					<input type="text" id="delivery-deliveryAogorder-goodsid" name="goodsid" />
   				</td>
   				<td colspan="2" style="text-align: left;"><span id="delivery-loading-deliveryAogorderDetailAddPage" ></span></td>
   				
   			</tr>
   			<tr>
   			    <td>辅数量:</td>
   				<td style="text-align: left;">
   					<input type="text" id="delivery-deliveryAogorder-unitnum-aux" name="auxnum" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'integer'"/>
   					<span id="delivery-deliveryAogorder-auxunitname1" style="float: left;"></span>
   					<input type="text" id="delivery-deliveryAogorder-unitnum-unit" name="overnum" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
   					<span id="delivery-deliveryAogorder-goodsunitname1" style="float: left;"></span>
   					<input type="hidden" id="delivery-deliveryAogorder-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
   				</td>
   				<td>数量:</td>
   				<td >
   					<input type="text" id="delivery-deliveryAogorder-unitnum" name="unitnum" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
   				</td>
   			</tr>
   			<tr>
   			    <td>采购价:</td>
   				<td>
   					<input type="text" id="delivery-deliveryAogorder-price"  name="price"/>
   				</td>
   				<td>采购金额:</td>
   				<td>
   					<input type="text" id="delivery-deliveryAogorder-taxamount" class="no_input" readonly="readonly" name="taxamount"/>
   				</td>
   			</tr>
   			<tr>
   				<td>商品品牌:</td>
   				<td>
   					<input type="text" id="delivery-deliveryAogorder-goodsbrandName" class="no_input" readonly="readonly"/>
   				</td>
   				<td> 箱装量:</td>
   				<td>
   					<input type="text" id="delivery-deliveryAogorder-boxnum1"  class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>主单位:</td>
   				<td >
   					<input type="text" id="delivery-deliveryAogorder-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="delivery-deliveryAogorder-unitid" name="unitid"/>
   				</td>
   				<td>辅单位:</td>
   				<td>
   					<input type="text" id="delivery-deliveryAogorder-auxunitname" name="auxunitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="delivery-deliveryAogorder-auxunitid" name="auxunitid"/>
   				</td>
   			</tr>
   			
   			<tr>
		   				<td style="text-align: right;" >生产日期：</td>
		   				<td>
		   					<input type="text" id="delivery-deliveryAogorder-produceddate" style="height: 20px;" name="produceddate" class="WdateRead" readonly="readonly"/>
		   				</td>
		   				<td style="text-align: right;" >截止日期：</td>
		   				<td>
		   					<input type="text" id="delivery-deliveryAogorder-deadline" style="height: 20px;" name="deadline" class="WdateRead" readonly="readonly"/>
		   				</td>
		   			</tr>
		   			
		   			<tr>
		   				<td style="text-align: right;">批次号：</td>
		   				<td>
		   					<input type="text" id="delivery-deliveryAogorder-batchno" name="batchno" class="no_input" readonly="readonly"/>
		   				</td>
		   				<td style="text-align: right;" >所属库位：</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="delivery-deliveryAogorder-storagelocationid" name="storagelocationid"/>
		   				</td>
		   	</tr>
   			
   			
   			<tr>
   			    
   			    <td>备注:</td>
   			    <td>
   			       <input type="text" id="delivery-deliveryAogorderDetail-remark" name="remark"  />
   			    </td>
   			</tr>
   		</table>
    </form>
    </div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="height:30px;text-align: right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
			<input type="button" value="继续添加" name="savegoon" id="delivery-savegoon-deliveryAogorderDetailAddPage" />
		</div>
	</div>
   <script type="text/javascript">
   		var aogorderid = "";
   		var detailrows = $("#delivery-datagrid-deliveryAogorderAddPage").datagrid('getRows');
   		for(var i=0; i<detailrows.length; i++){
   			var rowJson = detailrows[i];
   			if(rowJson.goodsid != undefined){
   				if(aogorderid==""){
   					if(rowJson.aogorderid!=null && rowJson.aogorderid!=""){
   						aogorderid = rowJson.aogorderid;
   					}
   				}else{
   					if(rowJson.aogorderid!=null && rowJson.aogorderid!=""){
   						aogorderid += ","+rowJson.aogorderid;
   					}
   				}
   			}
   		}
   		$(function() {
   			var storageid=$("#delivery-deliveryAogorder-storageid").widget("getValue");
   			$("#delivery-deliveryAogorder-goodsid").goodsWidget({
   				col: 'id',
   				singleSelect: true,
   				width: 150,
   				canBuySale: '1',
   				queryAllBySupplier: '${supplierid}',
//    				storageid:storageid,
   				param: [

   				],
   				onSelect: function(data) {
   					if (data) {
   						$("#delivery-deliveryAogorder-goodsid1").val(data.id);
   						$("#delivery-deliveryAogorder-goodsbrandName").val(data.brandName);
   						$("#delivery-deliveryAogorder-goodsunitname").val(data.mainunitName);
   						$("#delivery-deliveryAogorder-goodsunitname1").html(data.mainunitName);
						$("#delivery-deliveryAogorder-unitid").val(data.mainunit);
						$("#delivery-deliveryAogorder-auxunitname").val(data.auxunitname);
						$("#delivery-deliveryAogorder-auxunitname1").html(data.auxunitname);
   						$("#delivery-deliveryAogorder-auxunitid").val(data.auxunitid);
   						$("#delivery-deliveryAogorder-boxnum1").val(data.boxnum);
   						$("#delivery-deliveryAogorder-goodssort").val(data.defaultsort);
   						$("#delivery-deliveryAogorder-brandid").val(data.brand);
   						$("#delivery-deliveryAogorder-price").val(data.highestbuyprice);
   						getAuxunitname();
   						computNum();
   						computNumByAux();
   						getAmount();
   						
   						$("#delivery-deliveryAogorder-batchno").val("");
   						$("#delivery-deliveryAogorder-produceddate").val("");
   						$("#delivery-deliveryAogorder-deadline").val("");
						
   						if(data.isbatch=="1"){
							$("#delivery-deliveryAogorder-storagelocationid").widget("clear");
							$("#delivery-deliveryAogorder-storagelocationid").widget("enable");
							$('#delivery-deliveryAogorder-batchno').validatebox({required:true});
// 							$("#delivery-deliveryAogorder-batchno").removeClass("no_input");
// 							$("#delivery-deliveryAogorder-batchno").removeAttr("readonly");
							
							$('#delivery-deliveryAogorder-produceddate').validatebox({required:true});
							$("#delivery-deliveryAogorder-produceddate").removeClass("WdateRead");
							$("#delivery-deliveryAogorder-produceddate").addClass("Wdate");
							$("#delivery-deliveryAogorder-produceddate").removeAttr("readonly");
							
							$("#delivery-deliveryAogorder-deadline").removeClass("WdateRead");
							$("#delivery-deliveryAogorder-deadline").addClass("Wdate");
							$("#delivery-deliveryAogorder-deadline").removeAttr("readonly");
						}else{
							$("#delivery-deliveryAogorder-storagelocationid").widget("clear");
							$("#delivery-deliveryAogorder-storagelocationid").widget("disable");
							
							$('#delivery-deliveryAogorder-batchno').validatebox({required:false});
// 							$("#delivery-deliveryAogorder-batchno").addClass("no_input");
// 							$("#delivery-deliveryAogorder-batchno").attr("readonly","readonly");
							
							$('#delivery-deliveryAogorder-produceddate').validatebox({required:false});
							$("#delivery-deliveryAogorder-produceddate").removeClass("Wdate");
							$("#delivery-deliveryAogorder-produceddate").addClass("WdateRead");
							$("#delivery-deliveryAogorder-produceddate").attr("readonly","readonly");
							
							$("#delivery-deliveryAogorder-deadline").removeClass("Wdate");
							$("#delivery-deliveryAogorder-deadline").addClass("WdateRead");
							$("#delivery-deliveryAogorder-deadline").attr("readonly","readonly");
						}
   						
   						
   						$("#delivery-loading-deliveryAogorderDetailAddPage").html("商品编码:<font color='green'>" + data.id + "</font>");
   						$("#delivery-deliveryAogorder-unitnum-aux").focus();
   						$("#delivery-deliveryAogorder-unitnum-aux").select();
   					}
   				}
   			});
			$("#delivery-deliveryAogorder-produceddate").click(function(){
// 				if(!$("#delivery-deliveryAogorder-batchno").hasClass("no_input")){
					WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',
						onpicked:function(dp){
							if(dp.el.id=="delivery-deliveryAogorder-produceddate"){
								var pro = dp.cal.getDateStr();
								var goodsid = $("#delivery-deliveryAogorder-goodsid").goodsWidget("getValue");
								$.ajax({   
						            url :'storage/getBatchno.do',
						            type:'post',
						            data:{produceddate:pro,goodsid:goodsid},
						            dataType:'json',
						            async:false,
						            success:function(obj){
	                					$("#delivery-deliveryAogorder-deadline").val(obj.deadline);//截止日期
	                					$("#delivery-deliveryAogorder-batchno").val(obj.batchno); //批次号
                						
						            }
						        });
							}
						}
					});
// 				}
			});
			
			
			$("#delivery-deliveryAogorder-deadline").click(function(){
                    WdatePicker({dateFmt:'yyyy-MM-dd',
                        onpicked:function(dp){
                            if(dp.el.id=="delivery-deliveryAogorder-deadline"){
                                var dead = dp.cal.getDateStr();
                                var goodsid = $("#delivery-deliveryAogorder-goodsid").goodsWidget("getValue");
                                $.ajax({
                                    url :'storage/getBatchnoByDeadline.do',
                                    type:'post',
                                    data:{deadline:dead,goodsid:goodsid},
                                    dataType:'json',
                                    async:false,
                                    success:function(obj){
                                        
	                					$("#delivery-deliveryAogorder-produceddate").val( obj.produceddate);//生产日期
	                					$("#delivery-deliveryAogorder-batchno").val(obj.batchno); //批次号
                                        
                                        
                                    }
                                });
                            }
                        }
                    });
            });
			
			
			
			//库位
    		$("#delivery-deliveryAogorder-storagelocationid").widget({
				name:'t_delivery_aogorder_detail',
	    		width:165,
				col:'storagelocationid',
				singleSelect:true,
                disabled:true
			});

			$("#delivery-deliveryAogorder-enterdeliverylocationid").widget({
				name:'t_delivery_allocate_out_detail',
	    		width:165,
				col:'enterdeliverylocationid',
				singleSelect:true,
				disabled:true,
				onSelect:function(data){
					$("#delivery-deliveryAogorder-enterdeliverylocationname").val(data.name);
				},
				onClear:function(){
					$("#delivery-deliveryAogorder-enterdeliverylocationname").val("");
				}
			});
			$("#delivery-deliveryAogorder-unitnum").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				computNum();
				getAmount();
			});
			$("#delivery-deliveryAogorder-unitnum-aux").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				computNumByAux();
				getAmount();

			});
			$("#delivery-deliveryAogorder-unitnum-unit").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				computNumByAux();
				getAmount();
			});
			$("#delivery-deliveryAogorder-price").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				getAmount();
	
			});
			$("#delivery-deliveryAogorder-deadline").click(function(){
				if(!$("#delivery-deliveryAogorder-batchno").hasClass("no_input")){
					WdatePicker({dateFmt:'yyyy-MM-dd'});
				}
			});
			$("#delivery-deliveryAogorder-produceddate").click(function(){
				if(!$("#delivery-deliveryAogorder-batchno").hasClass("no_input")){
					WdatePicker({dateFmt:'yyyy-MM-dd'});
				}
			});
			$("#delivery-deliveryAogorder-unitnum").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#delivery-deliveryAogorder-unitnum").blur();
		   			
		   			$("#delivery-deliveryAogorder-price").focus();
		   			$("#delivery-deliveryAogorder-price").select();
				}
			});
			$("#delivery-deliveryAogorder-price").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#delivery-deliveryAogorder-price").blur();
		   			
		   			$("#delivery-deliveryAogorderDetail-remark").focus();
		   			$("#delivery-deliveryAogorderDetail-remark").select();
				}
			});
	
			$("#delivery-deliveryAogorder-unitnum-aux").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#delivery-deliveryAogorder-unitnum-auxm").blur();
		   			$("#delivery-deliveryAogorder-unitnum-unit").focus();
		   			$("#delivery-deliveryAogorder-unitnum-unit").select();
				}
			});
			$("#delivery-deliveryAogorder-unitnum-unit").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#delivery-deliveryAogorder-unitnum-auxm").blur();
					$("#delivery-deliveryAogorder-unitnum").focus();
		   			$("#delivery-deliveryAogorder-unitnum").select();
				}
			});
			$("#delivery-deliveryAogorderDetail-remark").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
		   			$("#delivery-savegoon-deliveryAogorderDetailAddPage").focus();
				}
			});
			$("#delivery-savegoon-deliveryAogorderDetailAddPage").die("keydown").live("keydown",function(event){
				//enter
				getAmount();
				if(event.keyCode==13){
					addSaveDetail(true);
				}
			});
		    $("#delivery-savegoon-deliveryAogorderDetailAddPage").click(function(){
		    	getAmount();
		    	addSaveDetail(true);
		    });
		});
		//获取辅单位和可用量
		function getAuxunitname(){
			var goodsInfo= $("#").goodsWidget("getObject");
			if(null==goodsInfo){
				return false;
			}
			var auxunitid = $("#delivery-deliveryAogorder-auxunitid").val();
			$.ajax({   
	            url :'delivery/getAuxunitname.do',
	            type:'post',
	            data:{goodsId:goodsInfo.id},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#delivery-deliveryAogorder-usablenum").val(json.usablenum);
	            }
	        });
		}
		
		//通过总数量 
		function computNum(){
			var unitnum = $("#delivery-deliveryAogorder-unitnum").val();
			var boxnum = $("#delivery-deliveryAogorder-boxnum1").val();
			if(unitnum==""){
				unitnum="0";
				$("#delivery-deliveryAogorder-unitnum").val(unitnum);
			}
			$("#delivery-deliveryAogorder-unitnum").addClass("inputload");
			$.ajax({   
	            url :'delivery/computNum.do',
	            type:'post',
	            data:{unitnum:unitnum,boxnum:boxnum},
	            dataType:'json',
	            async:false,
	            success:function(json){ 
	            	$("#delivery-deliveryAogorder-unitnum-aux").val(json.aux);
	            	$("#delivery-deliveryAogorder-unitnum-unit").val(json.unit);
	            	$("#delivery-deliveryAogorder-unitnum").removeClass("inputload");
	            }
	        });
			var auxunitname = $("#delivery-deliveryAogorder-auxunitname").val();
	        var unitname = $("#delivery-deliveryAogorder-goodsunitname").val();
	        var aux = $("#delivery-deliveryAogorder-unitnum-aux").val();
			var unit = $("#delivery-deliveryAogorder-unitnum-unit").val();
	        var auxdetail = Number(aux)+auxunitname;
	        if(unit>0){
	       		auxdetail += Number(unit)+unitname;
	        }
	        $("#delivery-deliveryAogorder-auxunitnumdetail").val(auxdetail);
		}
		//通过辅单位数量
		function computNumByAux(){
			var aux = $("#delivery-deliveryAogorder-unitnum-aux").val();
			var unit = $("#delivery-deliveryAogorder-unitnum-unit").val();
			var boxnum = $("#delivery-deliveryAogorder-boxnum1").val();
			if(aux==""){
				aux="0";
				$("#delivery-deliveryAogorder-unitnum-aux").val(aux);
			}
			if(unit==""){
				unit="0";
				$("#delivery-deliveryAogorder-unitnum-unit").val(unit);
			}
			$("#delivery-deliveryAogorder-unitnum").addClass("inputload");
			$.ajax({   
	            url :'delivery/computNumByAux.do',
	            type:'post',
	            data:{aux:aux,unit:unit,boxnum:boxnum},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#delivery-deliveryAogorder-unitnum").val(json.unitnum);
	            	$("#delivery-deliveryAogorder-unitnum-aux").val(json.aux);
	            	$("#delivery-deliveryAogorder-unitnum-unit").val(json.unit);
	            	$("#delivery-deliveryAogorder-unitnum").removeClass("inputload");
	            }
	        });
	        var auxunitname = $("#delivery-deliveryAogorder-auxunitname").val();
	        var unitname = $("#delivery-deliveryAogorder-goodsunitname").val();
	        var aux1 = $("#delivery-deliveryAogorder-unitnum-aux").val();
			var unit1 = $("#delivery-deliveryAogorder-unitnum-unit").val();
	        var auxdetail = Number(aux1)+auxunitname;
	        if(unit>0){
	       		auxdetail += Number(unit1)+unitname;
	        }
	        $("#delivery-deliveryAogorder-auxunitnumdetail").val(auxdetail);
		}
		//获取金额
		function getAmount(){
			
			var price = $("#delivery-deliveryAogorder-price").val();
			var unitnum = $("#delivery-deliveryAogorder-unitnum").val();
			var goodsid= $("#delivery-deliveryAogorder-goodsid").val();
			var boxnum= $("#delivery-deliveryAogorder-boxnum1").val();
			
			
			$.ajax({   
	            url :'delivery/getAmount.do',
	            type:'post',
	            data:{price:price,unitnum:unitnum,goodsid:goodsid,boxnum:boxnum},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#delivery-deliveryAogorder-taxamount").val(formatterMoney(json.taxamount));
	            	$("#delivery-deliveryAogorder-totalbox").val(json.totalbox);
	            	
	            }
	        });
		}
		
		
   </script>
  </body>
</html>
