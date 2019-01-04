<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>代配送销售订单明细修改</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
   	<form action="" method="post" id="delivery-form-deliveryOrderDetailAddPage">
   		<input type="hidden" id="delivery-deliveryOrder-usablenum" name="usablenum"/>
   		<input type="hidden" id="delivery-deliveryOrder-totalbox" name="totalbox"/>
   		<table  border="0" class="box_table">
   			<tr>
   				<td width="120">商品:</td>
   				<td style="text-align: left;">
   					<input type="text" id="delivery-deliveryOrder-goodsname" width="180" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="delivery-deliveryOrder-goodsid1" name="goodsid" width="170"/>
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
   					<input type="text" id="delivery-deliveryOrder-taxamount" name="taxamount" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>商品品牌:</td>
   				<td>
   					<input type="text" id="delivery-deliveryOrder-goodsbrandName" class="no_input" readonly="readonly"/>
   				</td>
   				<td>箱装量:</td>
   				<td>
   					<input type="text" id="delivery-deliveryOrder-boxnum1" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>主单位:</td>
   				<td style="text-align: left;">
   					<input type="text" id="delivery-deliveryOrder-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="delivery-deliveryOrder-goodsunitid" name="unitid"/>
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
 	    			<input id="delivery-deliveryOrder-batchno" type="text" class="len150 readonly" readonly="readonly"  name="batchno" />
 	    		</td>
 	    		<td style="text-align: right;" >所属仓库：</td>
 	    		<td>
 	    			<input id="delivery-deliveryOrder-store" type="text" class="len150 readonly" readonly="readonly" name="storagename" /> 
 	    		</td>
 	    	</tr>
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
			<input type="button" value="确 定" name="savegoon" id="delivery-savegoon-deliveryOrderDetailEditPage" />
		</div>
	</div>
   <script type="text/javascript">
   		
        //加载数据
	    var object = $("#delivery-datagrid-deliveryOrderAddPage").datagrid("getSelected");
	    $("#delivery-loading-deliveryOrderDetailAddPage").html("商品编码:<font color='green'>"+object.goodsInfo.id+"</font>&nbsp;可用量:<font color='green'>"+ parseInt(object.usablenum)+"</font>");
	
		$("#delivery-deliveryOrder-batchno").val(object.batchno)
		$("#delivery-deliveryOrder-store").val(object.store)
		$("#delivery-deliveryOrder-produceddate").val(object.produceddate)
		$("#delivery-deliveryOrder-deadline").val(object.deadline)
	
	    $("#delivery-deliveryOrder-goodsid1").val(object.goodsInfo.id);
	    $("#delivery-form-deliveryOrderDetailAddPage").form("load",object);
	    $("#delivery-deliveryOrder-taxamount").val(formatterMoney(object.taxamount));
	    $("#delivery-deliveryOrder-price").val(object.price);
	    $("#delivery-deliveryOrder-goodsname").val(object.goodsInfo.name);
	    $("#delivery-deliveryOrder-goodsbrandName").val(object.goodsInfo.brandName);
    	$("#delivery-deliveryOrder-boxnum1").val(formatterBigNumNoLen(object.goodsInfo.boxnum));
    	$("#delivery-deliveryOrder-auxunitname1").html(object.auxunitname);
    	$("#delivery-deliveryOrder-goodsunitname1").html(object.unitname);
     	$("#delivery-deliveryOrder-auxunitname").val(object.auxunitname);
    	$("#delivery-deliveryOrder-goodsunitname").val(object.unitname);
    	$("#delivery-deliveryOrder-unitnum-unit").val(formatterBigNumNoLen(object.overnum));
    	$("#delivery-deliveryOrder-unitnum-aux").val(formatterBigNum(object.auxnum));
    	$("#delivery-deliveryOrder-unitnum").val(formatterBigNumNoLen(object.unitnum));
    	$("#delivery-deliveryOrder-goodsid").val(object.goodsid);
    	$("#delivery-deliveryOrder-usablenum").val(object.usablenum);
    	
    	
    	
    	var goodsInfoStr = "商品编码:<font color='green'>"+object.goodsInfo.id+"</font>&nbsp;可用量:<font color='green'>"+ parseInt(object.usablenum)+"</font>";
    	if(object.batchno){
    		$("#delivery-deliveryOrder-batchno").removeAttr("readonly");
    		var storageid = $("#delivery-deliveryOrder-storageid").widget('getValue');
    		console.log(storageid)
    		var param = [{field:'goodsid',op:'equal',value:object.goodsid},{field:'storageid',op:'equal',value:storageid}];
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
					$("delivery-loading-deliveryOrderDetailAddPage").html("(批次商品)商品编码：<font color='green'>"+obj.goodsid+"</font>&nbsp;可用量：<font color='green'>"+ obj.usablenum +"</font>");
				},
				onClear:function(){
					$("#delivery-deliveryOrder-batchno").val("")
                    $("#delivery-deliveryOrder-produceddate").val("");
                    $("#delivery-deliveryOrder-deadline").val("");
				}
        	});
    		goodsInfoStr = "(批次商品)"+goodsInfoStr
    	}
    	$("#delivery-loading-deliveryOrderDetailAddPage").html(goodsInfoStr)
    	
		$(function(){
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
		   			$("#delivery-savegoon-deliveryOrderDetailEditPage").focus();
				}
			});
			$("#delivery-savegoon-deliveryOrderDetailEditPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					getAmount()
					editSaveDetail(true);
				}
			});
		    $("#delivery-savegoon-deliveryOrderDetailEditPage").click(function(){
		    	getAmount()
		    	editSaveDetail(true);
		    });
		});
		//通过总数量 计算数量 金额换算
		function computNum(){
			var unitnum = $("#delivery-deliveryOrder-unitnum").val();
			var boxnum = $("#delivery-deliveryOrder-boxnum1").val();
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
				$("#delivery-deliveryAogorder-unitnum-aux").val(aux);
			}
			if(unit==""){
				unit="0";
				$("#delivery-deliveryAogorder-unitnum-unit").val(unit);
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
		//获取金额
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
