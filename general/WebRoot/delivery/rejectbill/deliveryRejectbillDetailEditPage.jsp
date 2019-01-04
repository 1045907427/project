<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>代配送销售退单明细修改</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
   	<form action="" method="post" id="delivery-form-deliveryRejectbillDetailAddPage">
   		<input type="hidden" id="delivery-deliveryRejectbill-totalbox" name="totalbox"/>
   		<table  border="0" class="box_table">
   			<tr>
   				<td width="120">商品:</td>
   				<td style="text-align: left;">
   					<input type="text" id="delivery-deliveryRejectbill-goodsname" width="180" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="delivery-deliveryRejectbill-goodsid1" name="goodsid" width="170"/>
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
   					<input type="text" id="delivery-deliveryRejectbill-boxnum1" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>主单位:</td>
   				<td style="text-align: left;">
   					<input type="text" id="delivery-deliveryRejectbill-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="delivery-deliveryRejectbill-goodsunitid" name="unitid"/>
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
		   					<input type="text" id="delivery-deliveryRejectbill-storagelocationid"   class="no_input" readonly="readonly" name="storagelocationid"/>
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
			<input type="button" value="确 定" name="savegoon" id="delivery-savegoon-deliveryRejectbillDetailEditPage" />
		</div>
	</div>
   <script type="text/javascript">
   		
        //加载数据
	    var object = $("#delivery-datagrid-deliveryRejectbillAddPage").datagrid("getSelected");
	    $("#delivery-loading-deliveryRejectbillDetailAddPage").html("商品编码:<font color='green'>"+object.goodsInfo.id+"</font>");
	    $("#delivery-deliveryRejectbill-goodsid1").val(object.goodsInfo.id);
	    $("#delivery-form-deliveryRejectbillDetailAddPage").form("load",object);
	    $("#delivery-deliveryRejectbill-taxamount").val(formatterMoney(object.taxamount));
	    $("#delivery-deliveryRejectbill-price").val(object.price);
	    $("#delivery-deliveryRejectbill-goodsname").val(object.goodsInfo.name);
	    $("#delivery-deliveryRejectbill-goodsbrandName").val(object.goodsInfo.brandName);
    	$("#delivery-deliveryRejectbill-boxnum1").val(formatterBigNumNoLen(object.goodsInfo.boxnum));
    	$("#delivery-deliveryRejectbill-auxunitname1").html(object.auxunitname);
    	$("#delivery-deliveryRejectbill-goodsunitname1").html(object.unitname);
     	$("#delivery-deliveryRejectbill-auxunitname").val(object.auxunitname);
    	$("#delivery-deliveryRejectbill-goodsunitname").val(object.unitname);
    	$("#delivery-deliveryRejectbill-unitnum-unit").val(formatterBigNumNoLen(object.overnum));
    	$("#delivery-deliveryRejectbill-unitnum-aux").val(formatterBigNum(object.auxnum));
    	$("#delivery-deliveryRejectbill-unitnum").val(formatterBigNumNoLen(object.unitnum));
    	$("#delivery-deliveryRejectbill-goodsid").val(object.goodsid);
    	
    	//商品批次管理
 	   if(object.batchno){
 		   	//生产日期
			$('#delivery-deliveryRejectbill-produceddate').validatebox({required:true});
			$("#delivery-deliveryRejectbill-produceddate").removeClass("WdateRead");
			$("#delivery-deliveryRejectbill-produceddate").addClass("Wdate");
			$("#delivery-deliveryRejectbill-produceddate").removeAttr("readonly");
 		   
			$("#delivery-deliveryRejectbill-produceddate").click(function(){
				WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',
					onpicked:function(dp){
						if(dp.el.id=="delivery-deliveryRejectbill-produceddate"){
							var pro = dp.cal.getDateStr();
							$.ajax({   
					            url :'storage/getBatchno.do',
					            type:'post',
					            data:{produceddate:pro,goodsid:object.goodsid},
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
 		   
 		   //截止日期
			$("#delivery-deliveryRejectbill-deadline").removeClass("WdateRead");
			$("#delivery-deliveryRejectbill-deadline").addClass("Wdate");
			$("#delivery-deliveryRejectbill-deadline").removeAttr("readonly");   
	 		   
			$("#delivery-deliveryRejectbill-deadline").click(function(){
				WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',
					onpicked:function(dp){
						if(dp.el.id=="delivery-deliveryRejectbill-deadline"){
							var pro = dp.cal.getDateStr();
							$.ajax({   
					            url :'storage/getBatchnoByDeadline.do',
					            type:'post',
					            data:{deadline:pro,goodsid:object.goodsid},
					            dataType:'json',
					            async:false,
					            success:function(obj){
	            					$("#delivery-deliveryRejectbill-produceddate").val(obj.produceddate);//截止日期
	            					$("#delivery-deliveryRejectbill-batchno").val(obj.batchno); //批次号
	        						
					            }
					        });
						}
					}
				});
	});   
 		   
 		   
 		   
 		   
 		   
 		   
 	   }
    	
    	
		$(function(){
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
		   			$("#delivery-savegoon-deliveryRejectbillDetailEditPage").focus();
				}
			});
			$("#delivery-savegoon-deliveryRejectbillDetailEditPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					getAmount();
					editSaveDetail(true);
				}
			});
		    $("#delivery-savegoon-deliveryRejectbillDetailEditPage").click(function(){
		    	getAmount();
		    	editSaveDetail(true);
		    });
		});
		//通过总数量 计算数量 金额换算
		function computNum(){
			var unitnum = $("#delivery-deliveryRejectbill-unitnum").val();
			var boxnum = $("#delivery-deliveryRejectbill-boxnum1").val();
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
				$("#delivery-deliveryAogorder-unitnum-aux").val(aux);
			}
			if(unit==""){
				unit="0";
				$("#delivery-deliveryAogorder-unitnum-unit").val(unit);
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
		//获取金额
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
