<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>代配送销售订单明细添加</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
 	<div data-options="region:'center',border:false">
   	<form action="" method="post" id="delivery-form-deliveryOrderDetailAddPage">
   		<input type="hidden" id="delivery-deliveryOrder-goodssort" name="goodssort"/>
   		<input type="hidden" id="delivery-deliveryOrder-brandid" name="brandid"/>
   		<input type="hidden" id="delivery-deliveryOrder-usablenum" name="usablenum"/>
   		<input type="hidden" id="delivery-deliveryOrder-totalbox" name="totalbox"/>
  

   		<table  border="0" class="box_table">
   			<tr>
   				<td width="120">选择商品:</td>
   				<td style="text-align: left;">
   					<input type="text" id="delivery-deliveryOrder-goodsid" name="goodsid" width="180"/>
   				</td>
   				<td colspan="2" style="text-align: left;">
   				    <span id="delivery-loading-deliveryOrderDetailAddPage" style="float: left;"></span>
   				</td>
   			</tr>
   			<tr>
   				<td>数量:</td>
   				<td style="text-align: left;">
   					<input type="text" id="delivery-deliveryOrder-unitnum" name="unitnum" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
   				</td>
   				<td>辅数量:</td>
   				<td style="text-align: left;">
   					<input type="text" id="delivery-deliveryOrder-unitnum-aux" name="auxnum" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'integer'"/>
   					<span id="delivery-deliveryOrder-auxunitname1" style="float: left;"></span>
   					<input type="text" id="delivery-deliveryOrder-unitnum-unit" name="overnum" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
   					<span id="delivery-deliveryOrder-goodsunitname1" style="float: left;"></span>
   					<input type="hidden" id="delivery-deliveryOrder-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   			    <td>单价:</td>
   				<td>
   					<input type="text" id="delivery-deliveryOrder-price" name="price"/>
   				</td>
   				<td>金额:</td>
   				<td>
   					<input type="text" id="delivery-deliveryOrder-taxamount"  name="taxamount" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>商品品牌:</td>
   				<td>
   					<input type="text" id="delivery-deliveryOrder-goodsbrandName" class="no_input" readonly="readonly"/>
   				</td>
   				 <td>箱装量:</td>
   				<td>
   					<input type="text" id="delivery-deliveryOrder-boxnum1"  class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>主单位:</td>
   				<td style="text-align: left;">
   					<input type="text" id="delivery-deliveryOrder-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="delivery-deliveryOrder-unitid" name="unitid"/>
   				</td>
   				<td>辅单位:</td>
   				<td>
   					<input type="text" id="delivery-deliveryOrder-auxunitname" name="auxunitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="delivery-deliveryOrder-auxunitid" name="auxunitid"/>
   				</td>
   			</tr>
   			
   			
   			<tr>
	    		<td style="text-align: right;">批次号：</td>
	    		<td>
	    			<input id="delivery-deliveryOrder-batchno" type="text" class="len150 "  name="batchno" />
	    		</td>
	    		<td style="text-align: right;" >所属仓库：</td>
	    		<td>
	    			<input id="delivery-deliveryOrder-store" type="text" class="len150 readonly" readonly="readonly" name="storagename" /> 
	    		</td>
	    	</tr>
	    	<tr>
	    		<td style="text-align: right;" >生产日期：</td>
	    		<td><input id="delivery-deliveryOrder-produceddate" type="text" class="len150 readonly" readonly="readonly" name="produceddate" /> </td>
	    		<td style="text-align: right;" >截止日期：</td>
	    		<td><input id="delivery-deliveryOrder-deadline" type="text" class="len150 readonly no_input"  readonly="readonly" name="deadline"/></td>
			</tr>
   			
   			
   			<tr>
   			    <td>备注:</td>
   			    <td>
   			       <input type="text" id="delivery-deliveryOrderDetail-remark" name="remark"/>
   			    </td>
   			</tr>
   		</table>
    </form>
    </div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="height:30px;text-align: right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
			<input type="button" value="继续添加" name="savegoon" id="delivery-savegoon-deliveryOrderDetailAddPage" />
		</div>
	</div>
   <script type="text/javascript">
   		var aogorderid = "";
   		var detailrows = $("#delivery-datagrid-deliveryOrderAddPage").datagrid('getRows');
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
			var storageid=$("#delivery-deliveryOrder-storageid").widget("getValue");
			var supplierid=$("#delivery-deliveryOrder-supplierid").widget("getValue");
			$("#delivery-deliveryOrder-goodsid").goodsWidget({
	    			col:'id',
	    			singleSelect:true,
	    			width:150,
	    			canBuySale:'1',<%--购销类型，可采购（购销、可购）--%>
			        queryAllBySupplier:supplierid,<%--关联出此供应商所有商品--%>
			        storageid:storageid,
	        		param:[
	                  		
	                ],
	    			onSelect:function(data){
	    				if(data){
	    					
	    					$("#delivery-deliveryOrder-goodsid1").val(data.id);
	    		  			$("#delivery-deliveryOrder-goodsbrandName").val(data.brandName); 
	    		  			$("#delivery-deliveryOrder-goodsunitname").val(data.mainunitName); 
	    		  			$("#delivery-deliveryOrder-goodsunitname1").html(data.mainunitName);
							$("#delivery-deliveryOrder-unitid").val(data.mainunit);
							$("#delivery-deliveryOrder-auxunitname").val(data.auxunitname);
							$("#delivery-deliveryOrder-auxunitname1").html(data.auxunitname);
							$("#delivery-deliveryOrder-auxunitid").val(data.auxunitid);
	    		  			
	    		  			$("#delivery-deliveryOrder-boxnum1").val(data.boxnum);
	        				
	        				$("#delivery-deliveryOrder-goodssort").val(data.defaultsort); 
	        				$("#delivery-deliveryOrder-brandid").val(data.brand); 

	        				$.ajax({   
		       		               url :'storage/deliveryout/getDeliveryOutGoodsInfo.do?goodsid='+data.id+"&customerid=${customerid}",
		       		               type:'post',
		       		               dataType:'json',
		       		               async:false,
		       		               success:function(rs){
		       		               		$("#delivery-deliveryOrder-price").val(rs.basesaleprice); 
		       		               }
       		           		});
// 	        				$("#delivery-deliveryOrder-price").val(formatterMoney(data.basesaleprice)); 
	        				getAuxunitname();
	   						computNum();
	   						computNumByAux();
	   						getAmount();
	   						
	   						
	   						
	   						
	   						var usablenum=$("#delivery-deliveryOrder-usablenum").val();
	        			    $("#delivery-loading-deliveryOrderDetailAddPage").html("商品编码:<font color='green'>"+data.id+"</font>&nbsp;可用量:<font color='green'>"+usablenum+"</font>");
        		        			
			    			if(data.isbatch=='1'){
		                        	var param = [{field:'goodsid',op:'equal',value:data.id},{field:'storageid',op:'equal',value:storageid}];
		                        	$("#delivery-deliveryOrder-batchno").widget({
		                        		referwid:'RL_T_STORAGE_BATCH_LIST',
		                        		param:param,
		                    			width:150,
		                    			disabled:false,
		                				singleSelect:true,
		                				required:true,
		                				onSelect: function(obj){
		                					$("#delivery-deliveryOrder-store").val(obj.storagename); //所属仓库
		                					$("#delivery-deliveryOrder-produceddate").val(obj.produceddate); //生产日期
		                					$("#delivery-deliveryOrder-deadline").val(obj.deadline);//截止日期
		                					$("#delivery-loading-deliveryOrderDetailAddPage").html("(批次商品)商品编码：<font color='green'>"+obj.goodsid+"</font>&nbsp;可用量：<font color='green'>"+ obj.usablenum +"</font>");
		                				},
		                				onClear:function(){
		                					$("#delivery-deliveryOrder-batchno").val("")
		                					$("#delivery-deliveryOrder-store").val("");
		                                    $("#delivery-deliveryOrder-produceddate").val("");
		                                    $("#delivery-deliveryOrder-deadline").val("");
		                				}
		                        	});
		                        	$("#delivery-deliveryOrder-batchno").widget("enable");
		        		        }else{
		        		        	var usablenum=$("#delivery-deliveryOrder-usablenum").val();
			        				$("#delivery-loading-deliveryOrderDetailAddPage").html("商品编码:<font color='green'>"+data.id+"</font>&nbsp;可用量:<font color='green'>"+usablenum+"</font>");
		        		        	
		        		        	$("#delivery-deliveryOrder-batchno").widget({
		        		        		required: false
		        		        	})
		        		        	$("#delivery-deliveryOrder-batchno").widget("disable");
		        		        }	
	   						
	    					$("#delivery-deliveryOrder-unitnum").focus();
	    					$("#delivery-deliveryOrder-unitnum").select();
	    				}
	    			}
	    	});
			
		

			$("#delivery-deliveryOrder-enterdeliverylocationid").widget({
				name:'t_delivery_allocate_out_detail',
	    		width:165,
				col:'enterdeliverylocationid',
				singleSelect:true,
				disabled:true,
				onSelect:function(data){
					$("#delivery-deliveryOrder-enterdeliverylocationname").val(data.name);
				},
				onClear:function(){
					$("#delivery-deliveryOrder-enterdeliverylocationname").val("");
				}
			});
			$("#delivery-deliveryOrder-unitnum").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				computNum();
				getAmount();
			});
			$("#delivery-deliveryOrder-unitnum-aux").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				computNumByAux();
				getAmount();
			});
			$("#delivery-deliveryOrder-unitnum-unit").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				computNumByAux();
				getAmount();
			});
			$("#delivery-deliveryOrder-price").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				getAmount();
			});

			$("#delivery-deliveryOrder-deadline").click(function(){
				if(!$("#delivery-deliveryOrder-batchno").hasClass("no_input")){
					WdatePicker({dateFmt:'yyyy-MM-dd'});
				}
			});
			$("#delivery-deliveryOrder-produceddate").click(function(){
				if(!$("#delivery-deliveryOrder-batchno").hasClass("no_input")){
					WdatePicker({dateFmt:'yyyy-MM-dd'});
				}
			});
			$("#delivery-deliveryOrder-unitnum").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#delivery-deliveryOrder-unitnum").blur();
		   			$("#delivery-deliveryOrder-unitnum-aux").focus();
		   			$("#delivery-deliveryOrder-unitnum-aux").select();
				}
			});
			$("#delivery-deliveryOrder-unitnum-aux").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#delivery-deliveryOrder-unitnum-auxm").blur();
		   			$("#delivery-deliveryOrder-unitnum-unit").focus();
		   			$("#delivery-deliveryOrder-unitnum-unit").select();
				}
			});
			$("#delivery-deliveryOrder-unitnum-unit").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#delivery-deliveryOrder-unitnum-auxm").blur();
		   			$("#delivery-deliveryOrder-price").focus();
		   			$("#delivery-deliveryOrder-price").select();
				}
			});
			$("#delivery-deliveryOrder-price").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#delivery-deliveryOrder-price").blur();
		   			$("#delivery-deliveryOrderDetail-remark").focus();
		   			$("#delivery-deliveryOrderDetail-remark").select();
				}
			});
			$("#delivery-deliveryOrderDetail-remark").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
		   			$("#delivery-savegoon-deliveryOrderDetailAddPage").focus();
				}
			});
			$("#delivery-savegoon-deliveryOrderDetailAddPage").die("keydown").live("keydown",function(event){
				//enter
				getAmount();
				if(event.keyCode==13){
					addSaveDetail(true);
					
				}
			});
		    $("#delivery-savegoon-deliveryOrderDetailAddPage").click(function(){
		    	getAmount();
		    	addSaveDetail(true);
		    });
		});
		//获取辅单位
		function getAuxunitname(){
			var goodsInfo= $("#delivery-deliveryOrder-goodsid").goodsWidget("getObject");
			var storageid=$("#delivery-deliveryOrder-storageid").widget("getValue");
			if(null==goodsInfo){
				return false;
			}
			var auxunitid = $("#delivery-deliveryOrder-auxunitid").val();
			$.ajax({   
	            url :'delivery/getAuxunitname.do',
	            type:'post',
	            data:{goodsId:goodsInfo.id,storageid:storageid},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#delivery-deliveryOrder-usablenum").val(json.usablenum);
	            }
	        });
		}

		//通过总数量 计算数量 金额换算
		function computNum(){
			var unitnum = $("#delivery-deliveryOrder-unitnum").val();
			var boxnum = $("#delivery-deliveryOrder-boxnum1").val();
			if(unitnum==""){
				unitnum="0";
				$("#delivery-deliveryOrder-unitnum").val(unitnum);
			}
			$("#delivery-deliveryOrder-unitnum").addClass("inputload");
			$.ajax({   
	            url :'delivery/computNum.do',
	            type:'post',
	            data:{unitnum:unitnum,boxnum:boxnum},
	            dataType:'json',
	            async:false,
	            success:function(json){ 
	            	$("#delivery-deliveryOrder-unitnum-aux").val(json.aux);
	            	$("#delivery-deliveryOrder-unitnum-unit").val(json.unit);
	            	$("#delivery-deliveryOrder-unitnum").removeClass("inputload");
	            }
	        });
			var auxunitname = $("#delivery-deliveryOrder-auxunitname").val();
	        var unitname = $("#delivery-deliveryOrder-goodsunitname").val();
	        var aux = $("#delivery-deliveryOrder-unitnum-aux").val();
			var unit = $("#delivery-deliveryOrder-unitnum-unit").val();
	        var auxdetail = Number(aux)+auxunitname;
	        if(unit>0){
	       		auxdetail += Number(unit)+unitname;
	        }
	        $("#delivery-deliveryOrder-auxunitnumdetail").val(auxdetail);
		}
		//通过辅单位数量
		function computNumByAux(){
			var aux = $("#delivery-deliveryOrder-unitnum-aux").val();
			var unit = $("#delivery-deliveryOrder-unitnum-unit").val();
			var boxnum = $("#delivery-deliveryOrder-boxnum1").val();
			
			if(aux==""){
				aux="0";
				$("#delivery-deliveryOrder-unitnum-aux").val(aux);
			}
			if(unit==""){
				unit="0";
				$("#delivery-deliveryOrder-unitnum-unit").val(unit);
			}
			$("#delivery-deliveryOrder-unitnum").addClass("inputload");
			$.ajax({   
	            url :'delivery/computNumByAux.do',
	            type:'post',
	            data:{aux:aux,unit:unit,boxnum:boxnum},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#delivery-deliveryOrder-unitnum").val(json.unitnum);
	            	$("#delivery-deliveryOrder-unitnum-aux").val(json.aux);
	            	$("#delivery-deliveryOrder-unitnum-unit").val(json.unit);
	            	$("#delivery-deliveryOrder-unitnum").removeClass("inputload");
	            }
	        });
	        var auxunitname = $("#delivery-deliveryOrder-auxunitname").val();
	        var unitname = $("#delivery-deliveryOrder-goodsunitname").val();
	        var aux1 = $("#delivery-deliveryOrder-unitnum-aux").val();
			var unit1 = $("#delivery-deliveryOrder-unitnum-unit").val();
	        var auxdetail = Number(aux1)+auxunitname;
	        if(unit>0){
	       		auxdetail += Number(unit1)+unitname;
	        }
	        $("#delivery-deliveryOrder-auxunitnumdetail").val(auxdetail);
		}
		//获取金额和可用量
		function getAmount(){
			
			var price = $("#delivery-deliveryOrder-price").val();
			var unitnum = $("#delivery-deliveryOrder-unitnum").val();
			var goodsid= $("#delivery-deliveryOrder-goodsid").val();
			var boxnum= $("#delivery-deliveryOrder-boxnum1").val();
			$.ajax({   
	            url :'delivery/getAmount.do',
	            type:'post',
	            data:{price:price,unitnum:unitnum,goodsid:goodsid,boxnum:boxnum},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#delivery-deliveryOrder-taxamount").val(formatterMoney(json.taxamount));
	            	$("#delivery-deliveryOrder-totalbox").val(json.totalbox);
	            	
	            }
	        });
		}
		
   </script>
  </body>
</html>
