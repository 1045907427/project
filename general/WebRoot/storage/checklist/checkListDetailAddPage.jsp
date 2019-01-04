<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>盘点单明细添加</title>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
		   	<form action="" method="post" id="storage-form-checkListDetailAddPage">
		   		<table  border="0" class="box_table">
		   			<tr>
		   				<td width="120">选择商品:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="storage-checkList-goodsid" name="goodsid" width="180"/>
		   					<input type="hidden" id="storage-checkList-hidden-summarybatchid" name="summarybatchid"/>
		   					<input type="hidden" id="storage-checkList-hidden-goodsname" name="goodsname"/>
		   					<input type="hidden" id="storage-checkList-hidden-boxnum" name="boxnum"/>
		   					<input type="hidden" id="storage-checkList-hidden-isCheckListUseBatch" value="${IsCheckListUseBatch }"/>
		   				</td>
		   				<td>批次号:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="storage-checkList-batchno" name="batchno" />
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>实际数量:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-realnum" name="realnum" class="easyui-validatebox" data-options="validType:'intOrFloatNum[${decimallen}]'"/>
		   				</td>
		   				<td>实际辅数量:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-unitnum-aux" name="auxrealnum" style="width:60px;" class="easyui-validatebox" data-options="validType:'integer'"/>
		   					<span id="storage-checkList-auxunitname1" style="float: left;"></span>
		   					<input type="text" id="storage-checkList-unitnum-unit" name="auxrealremainder" style="width:60px;" class="easyui-validatebox" data-options="validType:'intOrFloatNum[${decimallen}]'"/>
		   					<span id="storage-checkList-goodsunitname1" style="float: left;"></span>
		   					<input type="hidden" id="storage-checkList-auxrealnumdetail" name="auxrealnumdetail" class="no_input" readonly="readonly"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td width="120">商品品牌:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-goodsbrandName" name="brandName" class="no_input" readonly="readonly"/>
		   					<input type="hidden" id="storage-checkList-brandid" name="brandid"/>
		   				</td>
		   				<td width="120">规格型号:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-goodsmodel" name="model" class="no_input" readonly="readonly"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>主单位:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
		   					<input type="hidden" id="storage-checkList-goodsunitid" name="unitid"/>
		   				</td>
		   				<td>辅单位:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-auxunitname" name="auxunitname" class="no_input" readonly="readonly"/>
		   					<input type="hidden" id="storage-checkList-auxunitid" name="auxunitid"/>
		   				</td>
		   			</tr>
		   			<tr <c:if test="${colMap!=null &&colMap.booknum ==null}">style="display: none;"</c:if>>
		   				<td>账面数量:</td>
		   				<td  style="text-align: left">
		   					<input type="text" id="storage-checkList-booknum" name="booknum" class="no_input" readonly="readonly" data-options="precision:${decimallen},groupSeparator:','"/>
		   				</td>
		   				<td>账面辅数量:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-auxbooknumdetail" name="auxbooknumdetail" class="no_input" readonly="readonly"/>
		   					<input type="hidden" id="storage-checkList-auxbooknum" name="auxbooknum"/>
		   				</td>
		   			</tr>
		   			<tr <c:if test="${colMap!=null && colMap.booknum ==null}">style="display: none;"</c:if>>
		   				<td>盈亏数量:</td>
		   				<td  style="text-align: left">
		   					<input type="text" id="storage-checkList-profitlossnum" name="profitlossnum" class="no_input" readonly="readonly" data-options="precision:${decimallen},groupSeparator:','"/>
		   				</td>
		   				<td>盈亏辅数量:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-auxprofitlossnumdetail" name="auxprofitlossnumdetail" class="no_input" readonly="readonly"/>
		   					<input type="hidden" id="storage-checkList-auxprofitlossnum" name="auxprofitlossnum"/>
		   				</td>
		   			</tr>
		   			<tr <c:if test="${colMap!=null && colMap.price ==null}">style="display: none;"</c:if>>
		   				<td>单价:</td>
		   				<td  style="text-align: left">
		   					<input type="text" id="storage-checkList-price" name="price" class="no_input" readonly="readonly" data-options="precision:6,groupSeparator:','"/>
		   				</td>
		   				<td>账面金额:</td>
		   				<td  style="text-align: left">
		   					<input type="text" id="storage-checkList-amount" name="amount" class="no_input" readonly="readonly"  data-options="precision:6,groupSeparator:','"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>条形码</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="storage-checkList-barcode" name="barcode" class="no_input" readonly="readonly"/>
		   				</td>
		   				<td>所属库位:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="storage-checkList-storagelocationname" name="storagelocationname" class="no_input" readonly="readonly"/>
		   					<input type="hidden" id="storage-checkList-storagelocationid"  name="storagelocationid"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>生产日期:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-produceddate" name="produceddate" class="no_input" readonly="readonly"/>
		   				</td>
		   				<td>有效截止日期:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-deadline" name="deadline" class="no_input" readonly="readonly"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>备注:</td>
		   				<td colspan="3" style="text-align: left;">
		   					<input id="storage-checkList-remark" type="text" name="remark" style="width: 490px;" maxlength="200"/>
		   				</td>
		   			</tr>
		   		</table>
		    </form>
		    </div>
	  		<div data-options="region:'south',border:false">
	  			<div class="buttonDetailBG" style="height:30px;text-align: right;">
	  				快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
		  			<input type="button" value="继续添加" name="savegoon" id="storage-savegoon-checkListDetailAddPage" />
	  			</div>
	  		</div>
	  	</div>
   <script type="text/javascript">
   		var initstorageid = $("#storage-checkList-storageid").widget('getValue');
		$(function(){
			$("#storage-checkList-goodsid").goodsWidget({
				width:180,
                isHiddenUsenum:true,
				onSelect:function(data){
					$("#storage-checkList-hidden-goodsname").val(data.name);
					$("#storage-checkList-hidden-boxnum").val(data.boxnum);
					$("#storage-checkList-barcode").val(data.barcode);
					$("#storage-checkList-goodsbrandName").val(data.brandName);
					$("#storage-checkList-brandid").val(data.brand);
					$("#storage-checkList-goodsmodel").val(data.model);
					$("#storage-checkList-goodsunitname").val(data.mainunitName);
					$("#storage-checkList-goodsunitid").val(data.mainunit);
					$("#storage-checkList-auxunitname").val(data.auxunitname);
					$("#storage-checkList-auxunitid").val(data.auxunitid);
					
					$("#storage-checkList-price").val(data.newbuyprice);
					
					var isCheckListUseBatch = $("#storage-checkList-hidden-isCheckListUseBatch").val();
					if(data.isbatch=="1" && isCheckListUseBatch=="1"){
						$("#storage-checkList-batchno").widget("enable");
    					var storageid = $("#storage-checkList-storageid").widget("getValue");
    					var param = null;
                    	if(storageid!=null && storageid!=""){
                    		param = [{field:'goodsid',op:'equal',value:data.id},
                    		       {field:'storageid',op:'equal',value:storageid}];
                    	}else{
                    		param = [{field:'goodsid',op:'equal',value:data.id}];
                    	}
                    	$("#storage-checkList-batchno").widget({
                    		referwid:'RL_T_STORAGE_BATCH_LIST',
                    		param:param,
                			width:165,
                			required:true,
            				singleSelect:true,
            				onSelect: function(obj){
            					$("#storage-checkList-booknum").val(obj.existingnum);
            					$("#storage-checkList-amount").val(Number(data.newbuyprice)*Number(obj.existingnum));
            					$("#storage-checkList-hidden-summarybatchid").val(obj.id);
            					$("#storage-checkList-batchno").val(obj.batchno);
            					$("#storage-checkList-produceddate").val(obj.produceddate);
            					$("#storage-checkList-deadline").val(obj.deadline);
            					$("#storage-checkList-storagelocationid").val(obj.storagelocationid);
            					$("#storage-checkList-storagelocationname").val(obj.storagelocationname);
            					
            					computNum();
            					$("#storage-checkList-realnum").val("0");
            					$("#storage-checkList-realnum").focus();
            					$("#storage-checkList-realnum").select();
            				},
            				onClear:function(){
            					$("#storage-checkList-booknum").val(0);
            					$("#storage-checkList-amount").val(0);
            					$("#storage-checkList-hidden-summarybatchid").val("");
            					$("#storage-checkList-batchno").val("");
            					$("#storage-checkList-produceddate").val("");
            					$("#storage-checkList-deadline").val("");
            					$("#storage-checkList-storagelocationid").val("");
            					$("#storage-checkList-storagelocationname").val("");
            				}
                    	});
                    	$("#storage-checkList-batchno").focus();
					}else{
						$("#storage-checkList-batchno").widget("disable");
						$.ajax({   
				            url :'storage/getStorageSummarySumByGoodsid.do',
				            type:'post',
				            data:{goodsid:data.id,storageid:initstorageid},
				            dataType:'json',
				            success:function(json){
				            	if(json.storageSummary!=null){
				            		$("#storage-checkList-booknum").val(json.storageSummary.existingnum);
									$("#storage-checkList-amount").val(Number(data.newbuyprice)*Number(json.storageSummary.existingnum));
									computNum();
									$("#storage-checkList-realnum").val("0");
									$("#storage-checkList-realnum").focus();
									$("#storage-checkList-realnum").select();
				            	}else{
				            		$("#storage-checkList-booknum").val(0);
									$("#storage-checkList-amount").val(Number(data.newbuyprice)*Number(0));
									computNum();
									$("#storage-checkList-realnum").val("0");
									$("#storage-checkList-realnum").focus();
									$("#storage-checkList-realnum").select();
				            	}
				            }
				        });
					}
				}
				
			});
			$("#storage-checkList-batchno").widget({
        		referwid:'RL_T_STORAGE_BATCH_LIST',
    			width:165,
    			disabled:true,
				singleSelect:true
			});
			//实际数量
			$("#storage-checkList-realnum").change(function(){
				computNum();
			});
			$("#storage-checkList-unitnum-aux").change(function(){
				computNumByAux();
			});
			$("#storage-checkList-unitnum-unit").change(function(){
				computNumByAux();
			});
			$("#storage-checkList-realnum").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
		   			$("#storage-checkList-unitnum-aux").focus();
		   			$("#storage-checkList-unitnum-aux").select();
				}
			});
			$("#storage-checkList-unitnum-aux").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
		   			$("#storage-checkList-unitnum-unit").focus();
		   			$("#storage-checkList-unitnum-unit").select();
				}
			});
			$("#storage-checkList-unitnum-unit").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
		   			$("#storage-checkList-remark").focus();
		   			$("#storage-checkList-remark").select();
				}
			});
			$("#storage-checkList-remark").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
		   			$("#storage-savegoon-checkListDetailAddPage").focus();
				}
			});
			$("#storage-savegoon-checkListDetailAddPage").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
		   			addSaveDetail(true);
				}
			});
			$("#storage-savegoon-checkListDetailAddPage").click(function(){
	   			addSaveDetail(true);
			});
		});
		//通过总数量 计算数量 金额换算
		function computNum(){
			var booknum = $("#storage-checkList-booknum").val();
			var realnum =  $("#storage-checkList-realnum").val();
			var goodsid= $("#storage-checkList-goodsid").storageGoodsWidget("getValue");
			var auxunitid = $("#storage-checkList-auxunitid").val();
			$("#storage-checkList-realnum").addClass("inputload");
			$.ajax({   
	            url :'storage/computeCheckListNum.do',
	            type:'post',
	            data:{booknum:booknum,realnum:realnum,goodsid:goodsid,auxunitid:auxunitid},
	            dataType:'json',
	            success:function(json){
	            	$("#storage-checkList-profitlossnum").val(json.profitlossnum);
	            	$("#storage-checkList-auxbooknumdetail").val(json.auxbooknumdetail);
	            	$("#storage-checkList-auxbooknum").val(json.auxbooknum);
	            	$("#storage-checkList-auxrealnumdetail").val(json.auxrealnumdetail);
	            	$("#storage-checkList-auxrealnum").val(json.auxrealnum);
	            	$("#storage-checkList-auxprofitlossnumdetail").val(json.auxprofitlossnumdetail);
	            	$("#storage-checkList-auxprofitlossnum").val(json.auxprofitlossnum);
	            	
	            	$("#storage-checkList-auxunitname").val(json.auxunitname);
	            	$("#storage-checkList-auxunitname1").html(json.auxunitname);
	            	$("#storage-checkList-goodsunitname").val(json.unitname);
	            	$("#storage-checkList-goodsunitname1").html(json.unitname);
	            	
	            	$("#storage-checkList-unitnum-aux").val(json.auxInteger);
	            	$("#storage-checkList-unitnum-unit").val(json.auxremainder);
	            	if(json.auxrate!=null){
	            		$("#storage-checkList-unitnum-unit").attr("max",json.auxrate-1);
	            	}
	            	$("#storage-checkList-realnum").removeClass("inputload");
                    validateFrom();
	            }
	        });
			
		}
		//通过辅单位数量
		function computNumByAux(){
			var booknum = $("#storage-checkList-booknum").val();
			var goodsid= $("#storage-checkList-goodsid").storageGoodsWidget("getValue");
			var auxunitid = $("#storage-checkList-auxunitid").val();
			var taxprice = $("#storage-checkList-price").val();
			var auxInterger = $("#storage-checkList-unitnum-aux").val();
			var auxremainder = $("#storage-checkList-unitnum-unit").val();
			if(taxprice==null || taxprice==""){
				return false;
			}
			$("#storage-checkList-realnum").addClass("inputload");
			$.ajax({   
	            url :'storage/computeCheckListNumByAux.do',
	            type:'post',
	            data:{booknum:booknum,auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#storage-checkList-amount").val(json.taxamount);
	            	$("#storage-checkList-price").val(json.taxprice);
	            	$("#storage-checkList-realnum").val(json.mainnum);
                    $("#storage-checkList-unitnum-aux").val(json.auxInterger);
                    $("#storage-checkList-unitnum-unit").val(json.auxremainder);
                    $("#storage-checkList-auxrealnumdetail").val(json.auxnumdetail);
	            	$("#storage-checkList-auxprofitlossnumdetail").val(json.auxprofitlossnumdetail);
	            	$("#storage-checkList-auxprofitlossnum").val(json.auxprofitlossnum);
	            	$("#storage-checkList-profitlossnum").val(json.profitlossnum);
	            	if(json.auxrate!=null){
	            		$("#storage-checkList-unitnum-unit").attr("max",json.auxrate-1);
	            	}
	            	$("#storage-checkList-realnum").removeClass("inputload");
                    validateFrom();
	            }
	        });
	        var auxunitname = $("#storage-checkList-auxunitname").val();
	        var unitname = $("#storage-checkList-goodsunitname").val();
	        var auxdetail = auxInterger+auxunitname;
	        if(auxremainder>0){
	       		auxdetail += auxremainder+unitname;
	        }
	        $("#storage-checkList-auxunitnumdetail").val(auxdetail);
		}
		//页面重置
		function checkListformReset(){
			$("#storage-form-checkListDetailAddPage").form("clear");
           	$("#storage-checkList-auxunitname1").html("");
           	$("#storage-checkList-goodsunitname1").html("");
    		$("#storage-checkList-goodsid").focus();
		}
       function validateFrom(){
           $("#storage-checkList-realnum").validatebox("isValid");
           $("#storage-checkList-unitnum-aux").validatebox("isValid");
           $("#storage-checkList-unitnum-unit").validatebox("isValid");
       }
   </script>
  </body>
</html>
