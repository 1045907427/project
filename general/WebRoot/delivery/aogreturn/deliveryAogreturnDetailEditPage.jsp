<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>代配送采购退单明细修改</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
   	<form action="" method="post" id="delivery-form-deliveryAogreturnDetailAddPage">
   		<input type="hidden" id="delivery-deliveryAogreturn-usablenum" name="usablenum"/>
   		<input type="hidden" id="delivery-deliveryAogreturn-totalbox" name="totalbox"/>
   		<table  border="0" class="box_table">
   			<tr>
   				<td width="120">商品:</td>
   				<td style="text-align: left;">
   					<input type="text" id="delivery-deliveryAogreturn-goodsname" width="180" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="delivery-deliveryAogreturn-goodsid1" name="goodsid" width="170"/>
   				</td>
   			
   				<td colspan="2" style="text-align: left;">
   				    <span id="delivery-loading-deliveryAogreturnDetailAddPage" style="float: left;" ></span>
   				</td>
   			</tr>
   			<tr>
   			    <td>辅数量:</td>
   				<td style="text-align: left;">
   					<input type="text" id="delivery-deliveryAogreturn-unitnum-aux" name="auxnum" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'integer'"/>
   					<span id="delivery-deliveryAogreturn-auxunitname1" style="float: left;"></span>
   					<input type="text" id="delivery-deliveryAogreturn-unitnum-unit" name="overnum" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
   					<span id="delivery-deliveryAogreturn-goodsunitname1" style="float: left;"></span>
   					<input type="hidden" id="delivery-deliveryAogreturn-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
   				</td>
   				<td>数量:</td>
   				<td style="text-align: left;">
   					<input type="text" id="delivery-deliveryAogreturn-unitnum" name="unitnum" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
   				</td>
   				
   			</tr>
   			<tr>
   			    <td>采购价:</td>
   				<td>
   					<input type="text" id="delivery-deliveryAogreturn-price"  name="price"/>
   				</td>
   				 <td>采购金额:</td>
   				<td>
   					<input type="text" id="delivery-deliveryAogreturn-taxamount" name="taxamount" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>商品品牌:</td>
   				<td>
   					<input type="text" id="delivery-deliveryAogreturn-goodsbrandName" class="no_input" readonly="readonly"/>
   				</td>
   				  <td>箱装量:</td>
   				<td>
   					<input type="text" id="delivery-deliveryAogreturn-boxnum1" class="no_input" readonly="readonly"/>
   				</td>
   				
   			</tr>
   			<tr>
   				<td>主单位:</td>
   				<td style="text-align: left;">
   					<input type="text" id="delivery-deliveryAogreturn-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="delivery-deliveryAogreturn-goodsunitid" name="unitid"/>
   				</td>
   				<td>辅单位:</td>
   				<td>
   					<input type="text" id="delivery-deliveryAogreturn-auxunitname" name="auxunitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="delivery-deliveryAogreturn-auxunitid" name="auxunitid"/>
   				</td>
   			</tr>
   			
   			<tr>
	    		<td style="text-align: right;">批次号：</td>
	    		<td>
	    			<input id="delivery-deliveryAogreturn-batchno" type="text"  class="len150 readonly" readonly="readonly"  name="batchno" />
	    		</td>
	    		<td style="text-align: right;" >所属仓库：</td>
	    		<td>
	    			<input id="delivery-deliveryOrder-store" type="text" class="len150 readonly" readonly="readonly" name="storagename" /> 
	    		</td>
	    	</tr>
	    	<tr>
	    		<td style="text-align: right;" >生产日期：</td>
	    		<td><input id="delivery-deliveryAogreturn-produceddate" type="text" class="len150 readonly" readonly="readonly" name="produceddate" /> </td>
	    		<td style="text-align: right;" >截止日期：</td>
	    		<td><input id="delivery-deliveryAogreturn-deadline" type="text" class="len150 readonly no_input"  readonly="readonly" name="deadline"/></td>
			</tr>
   			
   			<tr>
   			    <td>备注:</td>
   			    <td>
   			       <input type="text" id="delivery-deliveryAogreturnDetail-remark" name="remark" />
   			    </td>
   			</tr>
   		</table>
    </form>
    </div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="height:30px;text-align: right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
			<input type="button" value="确 定" name="savegoon" id="delivery-savegoon-deliveryAogreturnDetailEditPage" />
		</div>
	</div>
   <script type="text/javascript">
        //加载数据
	    var object = $("#delivery-datagrid-deliveryAogreturnAddPage").datagrid("getSelected");
		$("#delivery-deliveryAogreturn-unitnum-aux").focus();
	    $("#delivery-deliveryAogreturn-goodsid1").val(object.goodsInfo.id);
	    $("#delivery-form-deliveryAogreturnDetailAddPage").form("load",object);
	    $("#delivery-deliveryAogreturn-taxamount").val(formatterMoney(object.taxamount));
	    $("#delivery-deliveryAogreturn-price").val(object.price);
	    $("#delivery-deliveryAogreturn-goodsname").val(object.goodsInfo.name);
	    $("#delivery-deliveryAogreturn-goodsbrandName").val(object.goodsInfo.brandName);
    	$("#delivery-deliveryAogreturn-boxnum1").val(formatterBigNumNoLen(object.goodsInfo.boxnum));
    	$("#delivery-deliveryAogreturn-auxunitname1").html(object.auxunitname);
    	$("#delivery-deliveryAogreturn-goodsunitname1").html(object.unitname);
     	$("#delivery-deliveryAogreturn-auxunitname").val(object.auxunitname);
    	$("#delivery-deliveryAogreturn-goodsunitname").val(object.unitname);
    	$("#delivery-deliveryAogreturn-unitnum-unit").val(formatterBigNumNoLen(object.overnum));
    	$("#delivery-deliveryAogreturn-unitnum-aux").val(formatterBigNum(object.auxnum));
    	$("#delivery-deliveryAogreturn-unitnum").val(formatterBigNumNoLen(object.unitnum));
    	$("#delivery-deliveryAogreturn-goodsid").val(object.goodsid);
    	$("#delivery-deliveryAogreturn-usablenum").val(object.usablenum);
    	var goodsInfostr = "商品编码:<font color='green'>"+object.goodsInfo.id+"</font>&nbsp;可用量:<font color='green' >"+ parseInt(object.usablenum)+"</font>"
    	
    	//批次管理
    	if(object.batchno){
    		$("#delivery-deliveryAogreturn-batchno").removeAttr("readonly");
    		var storageid = $("#delivery-deliveryAogreturn-storageid").widget('getValue');
    		var param = [{field:'goodsid',op:'equal',value:object.goodsid},{field:'storageid',op:'equal',value:storageid}];
        	$("#delivery-deliveryAogreturn-batchno").widget({
        		referwid:'RL_T_STORAGE_BATCH_LIST',
        		param:param,
    			width:150,
    			disabled:false,
				singleSelect:true,
				required:true,
				onSelect: function(obj){
					$("#delivery-deliveryAogreturn-store").val(obj.storagename); //所属仓库
					$("#delivery-deliveryAogreturn-produceddate").val(obj.produceddate); //生产日期
					$("#delivery-deliveryAogreturn-deadline").val(obj.deadline);//截止日期
					$("#delivery-loading-deliveryAogreturnDetailAddPage").html("(批次商品)商品编码：<font color='green'>"+obj.goodsid+"</font>&nbsp;可用量：<font color='green'>"+ obj.usablenum +"</font>");
				},
				onClear:function(){
					$("#delivery-deliveryAogreturn-batchno").val("")
                    $("#delivery-deliveryAogreturn-produceddate").val("");
                    $("#delivery-deliveryAogreturn-deadline").val("");
				}
        	});
    		goodsInfostr = "(批次商品)"+goodsInfostr
    	}
    	
    	$("#delivery-loading-deliveryAogreturnDetailAddPage").html(goodsInfostr);
    	
		$(function(){
			$("#delivery-deliveryAogreturn-unitnum").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				computNum();
				 getAmount();
			});
			$("#delivery-deliveryAogreturn-unitnum-aux").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				computNumByAux();
				 getAmount();
			});
			$("#delivery-deliveryAogreturn-unitnum-unit").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				computNumByAux();
				 getAmount();
			});
			$("#delivery-deliveryAogreturn-price").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				 getAmount();
			});


			$("#delivery-deliveryAogreturn-unitnum").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#delivery-deliveryAogreturn-unitnum").blur();

		   			$("#delivery-deliveryAogreturn-price").focus();
		   			$("#delivery-deliveryAogreturn-price").select();
				}
			});
			$("#delivery-deliveryAogreturn-price").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#delivery-deliveryAogreturn-price").blur();

		   			$("#delivery-deliveryAogreturnDetail-remark").focus();
		   			$("#delivery-deliveryAogreturnDetail-remark").select();
				}
			});
			$("#delivery-deliveryAogreturn-unitnum-aux").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#delivery-deliveryAogreturn-unitnum-auxm").blur();
		   			$("#delivery-deliveryAogreturn-unitnum-unit").focus();
		   			$("#delivery-deliveryAogreturn-unitnum-unit").select();
				}
			});
			$("#delivery-deliveryAogreturn-unitnum-unit").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#delivery-deliveryAogreturn-unitnum-unit").blur();
		   			$("#delivery-deliveryAogreturn-unitnum").focus();
			    	$("#delivery-deliveryAogreturn-unitnum").select();
				}
			});
			$("#delivery-deliveryAogreturnDetail-remark").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
		   			$("#delivery-savegoon-deliveryAogreturnDetailEditPage").focus();
				}
			});
			$("#delivery-savegoon-deliveryAogreturnDetailEditPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					getAmount();
					editSaveDetail(true);
				}
			});
		    $("#delivery-savegoon-deliveryAogreturnDetailEditPage").click(function(){
		    	getAmount();
		    	editSaveDetail(true);
		    });
		});
		//通过总数量 计算数量 金额换算
		function computNum(){
			var unitnum = $("#delivery-deliveryAogreturn-unitnum").val();
			var boxnum = $("#delivery-deliveryAogreturn-boxnum1").val();
			$("#delivery-deliveryAogreturn-unitnum").addClass("inputload");
			$.ajax({   
	            url :'delivery/computNum.do',
	            type:'post',
	            data:{unitnum:unitnum,boxnum:boxnum},
	            dataType:'json',
	            async:false,
	            success:function(json){ 
	            	$("#delivery-deliveryAogreturn-unitnum-aux").val(json.aux);
	            	$("#delivery-deliveryAogreturn-unitnum-unit").val(json.unit);
	            	$("#delivery-deliveryAogreturn-unitnum").removeClass("inputload");
	            }
	        });
			var auxunitname = $("#delivery-deliveryAogreturn-auxunitname").val();
	        var unitname = $("#delivery-deliveryAogreturn-goodsunitname").val();
	        var aux = $("#delivery-deliveryAogreturn-unitnum-aux").val();
			var unit = $("#delivery-deliveryAogreturn-unitnum-unit").val();
	        var auxdetail = Number(aux)+auxunitname;
	        if(unit>0){
	       		auxdetail += Number(unit)+unitname;
	        }
	        $("#delivery-deliveryAogreturn-auxunitnumdetail").val(auxdetail);
		}
		//通过辅单位数量
		function computNumByAux(){
			var aux = $("#delivery-deliveryAogreturn-unitnum-aux").val();
			var unit = $("#delivery-deliveryAogreturn-unitnum-unit").val();
			var boxnum = $("#delivery-deliveryAogreturn-boxnum1").val();
			
			if(aux==""){
				aux="0";
				$("#delivery-deliveryAogorder-unitnum-aux").val(aux);
			}
			if(unit==""){
				unit="0";
				$("#delivery-deliveryAogorder-unitnum-unit").val(unit);
			}
			$("#delivery-deliveryAogreturn-unitnum").addClass("inputload");
			$.ajax({   
	            url :'delivery/computNumByAux.do',
	            type:'post',
	            data:{aux:aux,unit:unit,boxnum:boxnum},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#delivery-deliveryAogreturn-unitnum").val(json.unitnum);
	            	$("#delivery-deliveryAogreturn-unitnum-aux").val(json.aux);
	            	$("#delivery-deliveryAogreturn-unitnum-unit").val(json.unit);
	            	$("#delivery-deliveryAogreturn-unitnum").removeClass("inputload");
	            }
	        });
	        var auxunitname = $("#delivery-deliveryAogreturn-auxunitname").val();
	        var unitname = $("#delivery-deliveryAogreturn-goodsunitname").val();
	        var aux1 = $("#delivery-deliveryAogreturn-unitnum-aux").val();
			var unit1 = $("#delivery-deliveryAogreturn-unitnum-unit").val();
	        var auxdetail = Number(aux1)+auxunitname;
	        if(unit>0){
	       		auxdetail += Number(unit1)+unitname;
	        }
	        $("#delivery-deliveryAogreturn-auxunitnumdetail").val(auxdetail);
		}
		//获取金额
		function getAmount(){
			var price = $("#delivery-deliveryAogreturn-price").val();
			var unitnum = $("#delivery-deliveryAogreturn-unitnum").val();
			var goodsid= $("#delivery-deliveryAogreturn-goodsid").val();
			var boxnum= $("#delivery-deliveryAogreturn-boxnum1").val();
			$.ajax({   
	            url :'delivery/getAmount.do',
	            type:'post',
	            data:{price:price,unitnum:unitnum,goodsid:goodsid,boxnum:boxnum},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#delivery-deliveryAogreturn-taxamount").val(formatterMoney(json.taxamount));
	            	$("#delivery-deliveryAogreturn-totalbox").val(json.totalbox);
	            }
	        });
		}
   </script>
  </body>
</html>
