<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>代配送销售退单明细添加</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
 	<div data-options="region:'center',border:false">
   	<form action="" method="post" id="delivery-form-deliveryRejectbillDetailAddPage">
   		<input type="hidden" id="delivery-deliveryRejectbill-goodssort" name="goodssort"/>
   		<input type="hidden" id="delivery-deliveryRejectbill-brandid" name="brandid"/>
   		<input type="hidden" id="delivery-deliveryRejectbill-totalbox" name="totalbox"/>
   		 <input type="hidden" id="delivery-deliveryAogorder-usablenum" name="usablenum"/>
  

   		<table  border="0" class="box_table">
   			<tr>
   				<td width="120">选择商品:</td>
   				<td style="text-align: left;">
   					<input type="text" id="delivery-deliveryRejectbill-goodsid" name="goodsid" width="180"/>
   				</td>
   				<td colspan="2" style="text-align: left;">
   				    <span id="delivery-loading-deliveryRejectbillDetailAddPage" style="float: left;"></span>
   				</td>
   			</tr>
   			<tr>
   				<td>数量:</td>
   				<td style="text-align: left;">
   					<input type="text" id="delivery-deliveryRejectbill-unitnum" name="unitnum" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
   				</td>
   				<td>辅数量:</td>
   				<td style="text-align: left;">
   					<input type="text" id="delivery-deliveryRejectbill-unitnum-aux" name="auxnum" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'integer'"/>
   					<span id="delivery-deliveryRejectbill-auxunitname1" style="float: left;"></span>
   					<input type="text" id="delivery-deliveryRejectbill-unitnum-unit" name="overnum" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
   					<span id="delivery-deliveryRejectbill-goodsunitname1" style="float: left;"></span>
   					<input type="hidden" id="delivery-deliveryRejectbill-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   			    <td>单价:</td>
   				<td>
   					<input type="text" id="delivery-deliveryRejectbill-price" name="price"/>
   				</td>
   				<td>金额:</td>
   				<td>
   					<input type="text" id="delivery-deliveryRejectbill-taxamount" name="taxamount" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>商品品牌:</td>
   				<td>
   					<input type="text" id="delivery-deliveryRejectbill-goodsbrandName" class="no_input" readonly="readonly"/>
   				</td>
   				<td>箱装量:</td>
   				<td>
   					<input type="text" id="delivery-deliveryRejectbill-boxnum1"  class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>主单位:</td>
   				<td style="text-align: left;">
   					<input type="text" id="delivery-deliveryRejectbill-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="delivery-deliveryRejectbill-unitid" name="unitid"/>
   				</td>
   				<td>辅单位:</td>
   				<td>
   					<input type="text" id="delivery-deliveryRejectbill-auxunitname" name="auxunitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="delivery-deliveryRejectbill-auxunitid" name="auxunitid"/>
   				</td>
   			</tr>
   			<tr>
		   				<td style="text-align: right;" >生产日期：</td>
		   				<td>
		   					<input type="text" id="delivery-deliveryRejectbill-produceddate" style="height: 20px;" name="produceddate" class="WdateRead" readonly="readonly"/>
		   				</td>
		   				<td style="text-align: right;" >截止日期：</td>
		   				<td>
		   					<input type="text" id="delivery-deliveryRejectbill-deadline" style="height: 20px;" name="deadline" class="WdateRead" readonly="readonly"/>
		   				</td>
		   			</tr>
		   			
		   			<tr>
		   				<td style="text-align: right;">批次号：</td>
		   				<td>
		   					<input type="text" id="delivery-deliveryRejectbill-batchno" name="batchno" class="no_input" readonly="readonly"/>
		   				</td>
		   				<td style="text-align: right;" >所属库位：</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="delivery-deliveryRejectbill-storagelocationid" name="storagelocationid"/>
		   				</td>
		   	</tr>
   			
   			
   			
   			<tr>
   			    <td>备注:</td>
   			    <td>
   			       <input type="text" id="delivery-deliveryRejectbillDetail-remark" name="remark" />
   			    </td>
   			</tr>
   		</table>
    </form>
    </div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="height:30px;text-align: right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
			<input type="button" value="继续添加" name="savegoon" id="delivery-savegoon-deliveryRejectbillDetailAddPage" />
		</div>
	</div>
   <script type="text/javascript">
   		var aogorderid = "";
   		var detailrows = $("#delivery-datagrid-deliveryRejectbillAddPage").datagrid('getRows');
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
		$(function(){
			var storageid=$("#delivery-deliveryRejectbill-storageid").widget("getValue");
			var supplierid=$("#delivery-deliveryRejectbill-supplierid").widget("getValue");
			$("#delivery-deliveryRejectbill-goodsid").goodsWidget({
	    			col:'id',
	    			singleSelect:true,
	    			width:150,
	    			canBuySale:'1',//购销类型，可采购（购销、可购）
			        defaultsupplier:supplierid,
	        		param:[
	                  		
	                ],
	    			onSelect:function(data){
	    				if(data){
	    					$("#delivery-deliveryRejectbill-batchno").val("");
   							$("#delivery-deliveryRejectbill-produceddate").val("");
   							$("#delivery-deliveryRejectbill-deadline").val("");
	    				
	    					$("#delivery-loading-deliveryRejectbillDetailAddPage").html("<font color='green'>"+data.id+"</font>");
	    					$("#delivery-deliveryRejectbill-goodsid1").val(data.id);
	    		  			$("#delivery-deliveryRejectbill-goodsbrandName").val(data.brandName); 
	    		  			$("#delivery-deliveryRejectbill-goodsunitname").val(data.mainunitName); 
	    		  			$("#delivery-deliveryRejectbill-goodsunitname1").html(data.mainunitName);
	    		  			$("#delivery-deliveryRejectbill-auxunitid").val(data.auxunitid);
	    		  			
	    		  			$("#delivery-deliveryRejectbill-boxnum1").val(data.boxnum);
	        				$("#delivery-deliveryRejectbill-taxprice").val(data.newbuyprice); 
	        				
	        				$("#delivery-deliveryRejectbill-goodssort").val(data.defaultsort); 
	        				$("#delivery-deliveryRejectbill-brandid").val(data.brand); 
	        				$("#delivery-deliveryRejectbill-unitid").val(data.mainunit); 
	        				
	        				$.ajax({   
		       		               url :'storage/deliveryout/getDeliveryOutGoodsInfo.do?goodsid='+data.id+"&customerid=${customerid}",
		       		               type:'post',
		       		               dataType:'json',
		       		               async:false,
		       		               success:function(rs){
		       		               		$("#delivery-deliveryRejectbill-price").val(rs.basesaleprice); 
		       		               }
    		           		});
// 	        				$("#delivery-deliveryRejectbill-price").val(formatterMoney(data.basesaleprice)); 
	        				
	        				
	        				getAuxunitname();
	   						computNum();
	   						computNumByAux();
	   						getAmount();
	   						
	   						if(data.isbatch=="1"){
							$("#delivery-deliveryRejectbill-storagelocationid").widget("clear");
							$("#delivery-deliveryRejectbill-storagelocationid").widget("enable");
							$('#delivery-deliveryRejectbill-batchno').validatebox({required:true});
// 							$("#delivery-deliveryRejectbill-batchno").removeClass("no_input");
// 							$("#delivery-deliveryRejectbill-batchno").removeAttr("readonly");
							
							$('#delivery-deliveryRejectbill-produceddate').validatebox({required:true});
							$("#delivery-deliveryRejectbill-produceddate").removeClass("WdateRead");
							$("#delivery-deliveryRejectbill-produceddate").addClass("Wdate");
							$("#delivery-deliveryRejectbill-produceddate").removeAttr("readonly");
							
							$("#delivery-deliveryRejectbill-deadline").removeClass("WdateRead");
							$("#delivery-deliveryRejectbill-deadline").addClass("Wdate");
							$("#delivery-deliveryRejectbill-deadline").removeAttr("readonly");
							
						}else{
							
							$("#delivery-deliveryRejectbill-storagelocationid").widget("clear");
							$("#delivery-deliveryRejectbill-storagelocationid").widget("disable");
							
							$('#delivery-deliveryRejectbill-batchno').validatebox({required:false});
// 							$("#delivery-deliveryRejectbill-batchno").addClass("no_input");
// 							$("#delivery-deliveryRejectbill-batchno").attr("readonly","readonly");
							
							$('#delivery-deliveryRejectbill-produceddate').validatebox({required:false});
							$("#delivery-deliveryRejectbill-produceddate").removeClass("Wdate");
							$("#delivery-deliveryRejectbill-produceddate").addClass("WdateRead");
							$("#delivery-deliveryRejectbill-produceddate").attr("readonly","readonly");
							
							$("#delivery-deliveryRejectbill-deadline").removeClass("Wdate");
							$("#delivery-deliveryRejectbill-deadline").addClass("WdateRead");
							$("#delivery-deliveryRejectbill-deadline").attr("readonly","readonly");
						}
   						
	   						$("#delivery-loading-deliveryRejectbillDetailAddPage").html("商品编码:<font color='green'>" + data.id + "</font>");
	    					$("#delivery-deliveryRejectbill-unitnum").focus();
	    					$("#delivery-deliveryRejectbill-unitnum").select();
	    				}
	    			}
	    	});
	    	
	    	
	    	$("#delivery-deliveryRejectbill-produceddate").click(function(){
					WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',
						onpicked:function(dp){
							if(dp.el.id=="delivery-deliveryRejectbill-produceddate"){
								var pro = dp.cal.getDateStr();
								var goodsid = $("#delivery-deliveryRejectbill-goodsid").goodsWidget("getValue");
								$.ajax({   
						            url :'storage/getBatchno.do',
						            type:'post',
						            data:{produceddate:pro,goodsid:goodsid},
						            dataType:'json',
						            async:false,
						            success:function(obj){
	                					$("#delivery-deliveryRejectbill-deadline").val(obj.deadline);//截止日期
	                					$("#delivery-deliveryRejectbill-batchno").val(obj.batchno); //批次号
                						
						            }
						        });
							}
						}
					});
			});
			
			
			$("#delivery-deliveryRejectbill-deadline").click(function(){
                    WdatePicker({dateFmt:'yyyy-MM-dd',
                        onpicked:function(dp){
                            if(dp.el.id=="delivery-deliveryRejectbill-deadline"){
                                var dead = dp.cal.getDateStr();
                                var goodsid = $("#delivery-deliveryRejectbill-goodsid").goodsWidget("getValue");
                                $.ajax({
                                    url :'storage/getBatchnoByDeadline.do',
                                    type:'post',
                                    data:{deadline:dead,goodsid:goodsid},
                                    dataType:'json',
                                    async:false,
                                    success:function(obj){
	                					$("#delivery-deliveryRejectbill-produceddate").val( obj.produceddate);//生产日期
	                					$("#delivery-deliveryRejectbill-batchno").val(obj.batchno); //批次号
                                    }
                                });
                            }
                        }
                    });
            });
			
			
			
			//库位
    		$("#delivery-deliveryRejectbill-storagelocationid").widget({
				name:'t_deliveryRejectbill_detail',
	    		width:165,
				col:'storagelocationid',
				singleSelect:true,
                disabled:true
			});
	    	
			
		

			$("#delivery-deliveryRejectbill-enterdeliverylocationid").widget({
				name:'t_delivery_allocate_out_detail',
	    		width:165,
				col:'enterdeliverylocationid',
				singleSelect:true,
				disabled:true,
				onSelect:function(data){
					$("#delivery-deliveryRejectbill-enterdeliverylocationname").val(data.name);
				},
				onClear:function(){
					$("#delivery-deliveryRejectbill-enterdeliverylocationname").val("");
				}
			});
			$("#delivery-deliveryRejectbill-unitnum").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				computNum();
				getAmount();
			});
			$("#delivery-deliveryRejectbill-unitnum-aux").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				computNumByAux();
				getAmount();
			});
			$("#delivery-deliveryRejectbill-unitnum-unit").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				computNumByAux();
				getAmount();
			});
			$("#delivery-deliveryRejectbill-price").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				getAmount();
			});

			$("#delivery-deliveryRejectbill-deadline").click(function(){
				if(!$("#delivery-deliveryRejectbill-batchno").hasClass("no_input")){
					WdatePicker({dateFmt:'yyyy-MM-dd'});
				}
			});
			$("#delivery-deliveryRejectbill-produceddate").click(function(){
				if(!$("#delivery-deliveryRejectbill-batchno").hasClass("no_input")){
					WdatePicker({dateFmt:'yyyy-MM-dd'});
				}
			});
			$("#delivery-deliveryRejectbill-unitnum").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#delivery-deliveryRejectbill-unitnum").blur();
		   			$("#delivery-deliveryRejectbill-unitnum-aux").focus();
		   			$("#delivery-deliveryRejectbill-unitnum-aux").select();
				}
			});
			$("#delivery-deliveryRejectbill-unitnum-aux").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#delivery-deliveryRejectbill-unitnum-auxm").blur();
		   			$("#delivery-deliveryRejectbill-unitnum-unit").focus();
		   			$("#delivery-deliveryRejectbill-unitnum-unit").select();
				}
			});
			$("#delivery-deliveryRejectbill-unitnum-unit").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#delivery-deliveryRejectbill-unitnum-auxm").blur();
		   			$("#delivery-deliveryRejectbill-price").focus();
		   			$("#delivery-deliveryRejectbill-price").select();
				}
			});
			$("#delivery-deliveryRejectbill-price").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#delivery-deliveryRejectbill-price").blur();
		   			$("#delivery-deliveryRejectbillDetail-remark").focus();
		   			$("#delivery-deliveryRejectbillDetail-remark").select();
				}
			});
			$("#delivery-deliveryRejectbillDetail-remark").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
		   			$("#delivery-savegoon-deliveryRejectbillDetailAddPage").focus();
				}
			});
			$("#delivery-savegoon-deliveryRejectbillDetailAddPage").die("keydown").live("keydown",function(event){
				//enter
				getAmount();
				if(event.keyCode==13){
					addSaveDetail(true);
					
				}
			});
		    $("#delivery-savegoon-deliveryRejectbillDetailAddPage").click(function(){
		    	getAmount();
		    	addSaveDetail(true);
		    });
		});
		//获取辅单位
		function getAuxunitname(){
			var goodsInfo= $("#delivery-deliveryRejectbill-goodsid").goodsWidget("getObject");
			if(null==goodsInfo){
				return false;
			}
			var auxunitid = $("#delivery-deliveryRejectbill-auxunitid").val();
			$.ajax({   
	            url :'delivery/getAuxunitname.do',
	            type:'post',
	            data:{goodsId:goodsInfo.id},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#delivery-deliveryRejectbill-auxunitname").val(json.auxunitname);
	            	$("#delivery-deliveryRejectbill-auxunitname1").html(json.auxunitname);
	            
	            }
	        });
		}
		

		//通过总数量 计算数量 金额换算
		function computNum(){
			var unitnum = $("#delivery-deliveryRejectbill-unitnum").val();
			var boxnum = $("#delivery-deliveryRejectbill-boxnum1").val();
			if(unitnum==""){
				unitnum="0";
				$("#delivery-deliveryRejectbill-unitnum").val(unitnum);
			}
			$("#delivery-deliveryRejectbill-unitnum").addClass("inputload");
			$.ajax({   
	            url :'delivery/computNum.do',
	            type:'post',
	            data:{unitnum:unitnum,boxnum:boxnum},
	            dataType:'json',
	            async:false,
	            success:function(json){ 
	            	$("#delivery-deliveryRejectbill-unitnum-aux").val(json.aux);
	            	$("#delivery-deliveryRejectbill-unitnum-unit").val(json.unit);
	            	$("#delivery-deliveryRejectbill-unitnum").removeClass("inputload");
	            }
	        });
			var auxunitname = $("#delivery-deliveryRejectbill-auxunitname").val();
	        var unitname = $("#delivery-deliveryRejectbill-goodsunitname").val();
	        var aux = $("#delivery-deliveryRejectbill-unitnum-aux").val();
			var unit = $("#delivery-deliveryRejectbill-unitnum-unit").val();
	        var auxdetail = Number(aux)+auxunitname;
	        if(unit>0){
	       		auxdetail += Number(unit)+unitname;
	        }
	        $("#delivery-deliveryRejectbill-auxunitnumdetail").val(auxdetail);
		}
		//通过辅单位数量
		function computNumByAux(){
			var aux = $("#delivery-deliveryRejectbill-unitnum-aux").val();
			var unit = $("#delivery-deliveryRejectbill-unitnum-unit").val();
			var boxnum = $("#delivery-deliveryRejectbill-boxnum1").val();
			if(aux==""){
				aux="0";
				$("#delivery-deliveryRejectbill-unitnum-aux").val(aux);
			}
			if(unit==""){
				unit="0";
				$("#delivery-deliveryRejectbill-unitnum-unit").val(unit);
			}
			$("#delivery-deliveryRejectbill-unitnum").addClass("inputload");
			$.ajax({   
	            url :'delivery/computNumByAux.do',
	            type:'post',
	            data:{aux:aux,unit:unit,boxnum:boxnum},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#delivery-deliveryRejectbill-unitnum").val(json.unitnum);
	            	$("#delivery-deliveryRejectbill-unitnum-aux").val(json.aux);
	            	$("#delivery-deliveryRejectbill-unitnum-unit").val(json.unit);
	            	$("#delivery-deliveryRejectbill-unitnum").removeClass("inputload");
	            }
	        });
	        var auxunitname = $("#delivery-deliveryRejectbill-auxunitname").val();
	        var unitname = $("#delivery-deliveryRejectbill-goodsunitname").val();
	        var aux1 = $("#delivery-deliveryRejectbill-unitnum-aux").val();
			var unit1 = $("#delivery-deliveryRejectbill-unitnum-unit").val();
	        var auxdetail = Number(aux1)+auxunitname;
	        if(unit>0){
	       		auxdetail += Number(unit1)+unitname;
	        }
	        $("#delivery-deliveryRejectbill-auxunitnumdetail").val(auxdetail);
		}
		//获取金额和可用量
		function getAmount(){
			
			var price = $("#delivery-deliveryRejectbill-price").val();
			var unitnum = $("#delivery-deliveryRejectbill-unitnum").val();
			var goodsid= $("#delivery-deliveryRejectbill-goodsid").val();
			var boxnum= $("#delivery-deliveryRejectbill-boxnum1").val();
			$.ajax({   
	            url :'delivery/getAmount.do',
	            type:'post',
	            data:{price:price,unitnum:unitnum,goodsid:goodsid,boxnum:boxnum},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#delivery-deliveryRejectbill-taxamount").val(formatterMoney(json.taxamount));
	            	$("#delivery-deliveryRejectbill-totalbox").val(json.totalbox);
	            }
	        });
		}
		
   </script>
  </body>
</html>
