<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>代配送采购单明细修改</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'center',border:false">
		   	<form action="" method="post" id="delivery-form-deliveryAogorderDetailAddPage">
		   		<input type="hidden" id="delivery-deliveryAogorder-totalbox" name="totalbox"/>
		   		<table border="0" class="box_table">
		   			<tr>
		   				<td width="120">商品:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="delivery-deliveryAogorder-goodsname" width="180" class="no_input" readonly="readonly"/>
		   					<input type="hidden" id="delivery-deliveryAogorder-goodsid1" name="goodsid" width="170"/>
		   					
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
		   				<td style="text-align: left;">
		   					<input type="text" id="delivery-deliveryAogorder-unitnum" name="unitnum" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
		   				</td>
		   				
		   			</tr>
		   			<tr>
		   			    <td>采购价:</td>
		   				<td>
		   					<input type="text" id="delivery-deliveryAogorder-price" name="price"/>
		   				</td>
		   				<td>采购金额:</td>
		   				<td>
		   					<input type="text" id="delivery-deliveryAogorder-taxamount" name="taxamount" class="no_input" readonly="readonly" />
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>商品品牌:</td>
		   				<td>
		   					<input type="text" id="delivery-deliveryAogorder-goodsbrandName" class="no_input" readonly="readonly"/>
		   				</td>
		   				<td>箱装量:</td>
		   				<td>
		   					<input type="text" id="delivery-deliveryAogorder-boxnum1" class="no_input" readonly="readonly"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>主单位:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="delivery-deliveryAogorder-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
		   					<input type="hidden" id="delivery-deliveryAogorder-goodsunitid" name="unitid"/>
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
		   					<input type="text" id="delivery-deliveryAogorder-storagelocationid"  class="no_input" name="storagelocationid"/>
		   				</td>
		   	</tr>
		   			
		   			
		   			<tr>
		   			    
		   			    <td>备注:</td>
		   			    <td>
		   			       <input type="text" id="delivery-deliveryAogorderDetail-remark" name="remark" />
		   			    </td>
		   			</tr>
		   		</table>
		    </form>
	    </div>
		<div data-options="region:'south',border:false">
			<div class="buttonDetailBG" style="height:30px;text-align: right;">
				快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
				<input type="button" value="确 定" name="savegoon" id="delivery-savegoon-deliveryAogorderDetailEditPage" />
			</div>
		</div>
	</div>
   <script type="text/javascript">
   		
        //加载数据
	    var object=$("#delivery-datagrid-deliveryAogorderAddPage").datagrid("getSelected");
	    $("#delivery-loading-deliveryAogorderDetailAddPage").html("商品编码:<font color='green'>"+object.goodsInfo.id+"</font>");
	   
	   
	   
	   $("#delivery-deliveryAogorder-produceddate").val(object.produceddate);
	   $("#delivery-deliveryAogorder-deadline").val(object.deadline);
	   $("#delivery-deliveryAogorder-batchno").val(object.batchno);
	   $("#delivery-deliveryAogorder-storagelocationid").val(object.storagelocationid);
	   //商品批次管理
	   if(object.batchno){
	   		//生产日期
			$('#delivery-deliveryAogorder-produceddate').validatebox({required:true});
			$("#delivery-deliveryAogorder-produceddate").removeClass("WdateRead");
			$("#delivery-deliveryAogorder-produceddate").addClass("Wdate");
			$("#delivery-deliveryAogorder-produceddate").removeAttr("readonly");
	   		
		   	$("#delivery-deliveryAogorder-produceddate").click(function(){
						WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',
							onpicked:function(dp){
								if(dp.el.id=="delivery-deliveryAogorder-produceddate"){
									var pro = dp.cal.getDateStr();
									var goodsid = $("#delivery-deliveryAogorder-goodsid").goodsWidget("getValue");
									$.ajax({   
							            url :'storage/getBatchno.do',
							            type:'post',
							            data:{produceddate:pro,goodsid:object.goodsid},
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
			});
			//截止日期
			$("#delivery-deliveryAogorder-deadline").removeClass("WdateRead");
			$("#delivery-deliveryAogorder-deadline").addClass("Wdate");
			$("#delivery-deliveryAogorder-deadline").removeAttr("readonly");
	   
	   		$("#delivery-deliveryAogorder-deadline").click(function(){
                    WdatePicker({dateFmt:'yyyy-MM-dd',
                        onpicked:function(dp){
                            if(dp.el.id=="delivery-deliveryAogorder-deadline"){
                                var dead = dp.cal.getDateStr();
                                $.ajax({
                                    url :'storage/getBatchnoByDeadline.do',
                                    type:'post',
                                    data:{deadline:dead,goodsid:object.goodsid},
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
	   
	   }
	   
	   
	   
	   
	   
	   
	    $("#delivery-deliveryAogorder-goodsid1").val(object.goodsInfo.id);
	    $("#delivery-form-deliveryAogorderDetailAddPage").form("load",object);
	    $("#delivery-deliveryAogorder-taxamount").val(formatterMoney(object.taxamount));
	    $("#delivery-deliveryAogorder-price").val(object.price);
	    $("#delivery-deliveryAogorder-goodsname").val(object.goodsInfo.name);
	    $("#delivery-deliveryAogorder-goodsbrandName").val(object.goodsInfo.brandName);
    	$("#delivery-deliveryAogorder-boxnum1").val(formatterBigNumNoLen(object.goodsInfo.boxnum));
    	$("#delivery-deliveryAogorder-auxunitname1").html(object.auxunitname);
    	$("#delivery-deliveryAogorder-goodsunitname1").html(object.unitname);
     	$("#delivery-deliveryAogorder-auxunitname").val(object.auxunitname);
    	$("#delivery-deliveryAogorder-goodsunitname").val(object.unitname);
    	$("#delivery-deliveryAogorder-unitnum-unit").val(formatterBigNumNoLen(object.overnum));
    	$("#delivery-deliveryAogorder-unitnum-aux").val(formatterBigNum(object.auxnum));
    	$("#delivery-deliveryAogorder-unitnum").val(formatterBigNumNoLen(object.unitnum));
    	$("#delivery-deliveryAogorder-goodsid").val(object.goodsid);
		$(function(){
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
		   			$("#delivery-savegoon-deliveryAogorderDetailEditPage").focus();
				}
			});
			$("#delivery-savegoon-deliveryAogorderDetailEditPage").die("keydown").live("keydown",function(event){
				//enter
				getAmount();
				if(event.keyCode==13){
					editSaveDetail(true);
				}
			});
		    $("#delivery-savegoon-deliveryAogorderDetailEditPage").click(function(){
		    	getAmount();
		    	editSaveDetail(true);
		    });
		});
		//通过总数量 计算数量 金额换算
		function computNum(){
			var unitnum = $("#delivery-deliveryAogorder-unitnum").val();
			var boxnum = $("#delivery-deliveryAogorder-boxnum1").val();
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
