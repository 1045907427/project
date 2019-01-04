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
		   	<form action="" method="post" id="storage-form-checkListDetailEditPage">
		   		<table  border="0" class="box_table">
		   			<tr>
		   				<td width="120">商品名称:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-goodsname" name="goodsname"  value="${checkListDetail.goodsname}" width="180"  class="no_input" readonly="readonly"/>
		   					<input type="hidden" id="storage-checkList-hidden-summarybatchid" name="summarybatchid" value="${checkListDetail.summarybatchid}"/>
		   					<input type="hidden" id="storage-checkList-hidden-goodsid" name="goodsid" value="${checkListDetail.goodsid}"/>
		   					<input type="hidden" id="storage-checkList-hidden-boxnum" name="boxnum" value="${checkListDetail.boxnum}"/>
		   					<input type="hidden" id="storage-checkList-hidden-isCheckListUseBatch" value="${IsCheckListUseBatch }"/>
		   					<input type="hidden" id="storage-checkList-hidden-isbatch" value="${isbatch }"/>
		   				</td>
		   				<td>批次号:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="storage-checkList-batchno" name="batchno" value="${checkListDetail.batchno}" class="no_input" readonly="readonly"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>实际数量:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-realnum" name="realnum" value="${checkListDetail.realnum}" class="formaterNum easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
		   				</td>
		   				<td>实际辅数量:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-unitnum-aux" name="auxrealnum"  value="${checkListDetail.auxrealnum}" style="width:60px;" class="formaterNum easyui-validatebox" data-options="required:true,validType:'integer'"/>
		   					<span id="storage-checkList-auxunitname1" style="float: left;">${checkListDetail.auxunitname}</span>
		   					<input type="text" id="storage-checkList-unitnum-unit" name="auxrealremainder" value="${checkListDetail.auxrealremainder}" style="width:60px;" class="formaterNum easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
		   					<span id="storage-checkList-goodsunitname1" style="float: left;">${checkListDetail.unitname}</span>
		   					<input type="hidden" id="storage-checkList-auxrealnumdetail" name="auxrealnumdetail" value="${checkListDetail.auxrealnumdetail}" class="no_input" readonly="readonly"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td width="120">商品品牌:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-goodsbrandName" name="brandName" value="${checkListDetail.brandName}" class="no_input" readonly="readonly"/>
		   					<input type="hidden" id="storage-checkList-brandid" name="brandid" value="${checkListDetail.brandid}"/>
		   				</td>
		   				<td width="120">规格型号:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-goodsmodel" class="no_input" readonly="readonly"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>主单位:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-goodsunitname" name="unitname" value="${checkListDetail.unitname}" class="no_input" readonly="readonly"/>
		   					<input type="hidden" id="storage-checkList-goodsunitid" name="unitid" value="${checkListDetail.unitid}"/>
		   				</td>
		   				<td>辅单位:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-auxunitname" name="auxunitname" value="${checkListDetail.auxunitname}" class="no_input" readonly="readonly"/>
		   					<input type="hidden" id="storage-checkList-auxunitid" name="auxunitid" value="${checkListDetail.auxunitid}"/>
		   				</td>
		   			</tr>
		   			<tr <c:if test="${colMap!=null &&colMap.booknum ==null}">style="display: none;"</c:if>>
		   				<td>账面数量:</td>
		   				<td  style="text-align: left">
		   					<input type="text" id="storage-checkList-booknum" name="booknum" value="${checkListDetail.booknum}" class="no_input" data-options="precision:${decimallen},groupSeparator:','" readonly="readonly" />
		   				</td>
		   				<td>账面辅数量:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-auxbooknumdetail" name="auxbooknumdetail" value="${checkListDetail.auxbooknumdetail}" class="no_input" readonly="readonly"/>
		   					<input type="hidden" id="storage-checkList-auxbooknum" name="auxbooknum" value="${checkListDetail.auxbooknum}"/>
		   				</td>
		   			</tr>
		   			<tr <c:if test="${colMap!=null &&colMap.booknum ==null}">style="display: none;"</c:if>>
		   				<td>盈亏数量:</td>
		   				<td  style="text-align: left">
		   					<input type="text" id="storage-checkList-profitlossnum" name="profitlossnum" value="${checkListDetail.profitlossnum}" class="no_input" readonly="readonly" data-options="precision:${decimallen},groupSeparator:','"/>
		   				</td>
		   				<td>盈亏辅数量:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-auxprofitlossnumdetail" name="auxprofitlossnumdetail" value="${checkListDetail.auxprofitlossnumdetail}" class="no_input" readonly="readonly" data-options="precision:6,groupSeparator:','"/>
		   					<input type="hidden" id="storage-checkList-auxprofitlossnum" name="auxprofitlossnum" value="${checkListDetail.auxprofitlossnum}"/>
                            <input type="hidden" id="storage-checkList-profitlossamount" name="profitlossamount" value="${checkListDetail.profitlossamount}"/>
		   				</td>
		   			</tr>
		   			<tr <c:if test="${colMap!=null && colMap.price ==null}">style="display: none;"</c:if>>
		   				<td>单价:</td>
		   				<td  style="text-align: left">
		   					<input type="text" id="storage-checkList-price" name="price" class="no_input" value="${checkListDetail.price}" readonly="readonly" data-options="precision:6,groupSeparator:','"/>
		   				</td>
		   				<td>账面金额:</td>
		   				<td  style="text-align: left">
		   					<input type="text" id="storage-checkList-amount" name="amount" class="no_input" value="${checkListDetail.amount}" readonly="readonly" data-options="precision:6,groupSeparator:','"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>条形码</td>
		   				<td>
		   					<input type="text" id="storage-checkList-barcode" name="barcode" class="no_input" readonly="readonly" value="${checkListDetail.barcode}"/>
		   				</td>
		   				<td>所属库位:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="storage-checkList-storagelocationname" name="storagelocationname"  value="${checkListDetail.storagelocationname}" class="no_input" readonly="readonly" />
		   					<input type="hidden" id="storage-checkList-storagelocationid"  name="storagelocationid" value="${checkListDetail.storagelocationid}"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>生产日期:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-produceddate" name="produceddate" value="${checkListDetail.produceddate}" class="no_input" readonly="readonly"/>
		   				</td>
		   				<td>有效截止日期:</td>
		   				<td>
		   					<input type="text" id="storage-checkList-deadline" name="deadline"  value="${checkListDetail.deadline}" class="no_input" readonly="readonly"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>备注:</td>
		   				<td colspan="3" style="text-align: left;">
		   					<input id="storage-checkList-remark" type="text" name="remark" value="${checkListDetail.remark}" style="width: 490px;" maxlength="200"/>
		   				</td>
		   			</tr>
		   		</table>
		    </form>
		    </div>
	  		<div data-options="region:'south',border:false">
	  			<div class="buttonDetailBG" style="height:30px;text-align: right;">
   					<input type="hidden" id="storage-checkList-default-checkuserid" value="${thisUserid }"/>
	  				快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
		  			<input type="button" value="继续修改" name="savegoon" id="storage-savegoon-checkListDetailEditPage" />
	  			</div>
	  		</div>
	  	</div>
   <script type="text/javascript">
		$(function(){
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
		   			$("#storage-savegoon-checkListDetailEditPage").focus();
				}
			});
			$("#storage-savegoon-checkListDetailEditPage").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
		   			setTimeout(function(){
			    		editSaveDetail(true);
			    	},150);
				}
			});
			$("#storage-savegoon-checkListDetailEditPage").click(function(){
				setTimeout(function(){
		    		editSaveDetail(true);
		    	},150);
			});

			
		});
		//通过总数量 计算数量 金额换算
		function computNum(){
			var booknum = $("#storage-checkList-booknum").val();
			var realnum =  $("#storage-checkList-realnum").val();
			var goodsid= $("#storage-checkList-hidden-goodsid").val();
			var auxunitid = $("#storage-checkList-auxunitid").val();
            var taxprice = $("#storage-checkList-price").val();
			$("#storage-checkList-realnum").addClass("inputload");
			$.ajax({   
	            url :'storage/computeCheckListNum.do',
	            type:'post',
	            data:{booknum:booknum,realnum:realnum,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice},
	            dataType:'json',
	            success:function(json){
                    $("#storage-checkList-profitlossamount").val(json.profitlossamount);
	            	$("#storage-checkList-profitlossnum").val(json.profitlossnum);
	            	$("#storage-checkList-auxbooknumdetail").val(json.auxbooknumdetail);
	            	$("#storage-checkList-auxbooknum").val(json.auxbooknum);
	            	$("#storage-checkList-auxrealnumdetail").val(json.auxrealnumdetail);
	            	$("#storage-checkList-auxrealnum").val(json.auxrealnum);
	            	$("#storage-checkList-auxprofitlossnumdetail").val(json.auxprofitlossnumdetail);
	            	$("#storage-checkList-auxprofitlossnum").val(json.auxprofitlossnum);

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
			var goodsid= $("#storage-checkList-hidden-goodsid").val();
			var auxunitid = $("#storage-checkList-auxunitid").val();
			var taxprice = $("#storage-checkList-price").val();
			var auxInterger = $("#storage-checkList-unitnum-aux").val();
			var auxremainder = $("#storage-checkList-unitnum-unit").val();
			var auxmax = $("#storage-checkList-unitnum-unit").attr("max");
			if(Number(auxremainder)>Number(auxmax)){
				auxremainder = auxmax;
				$("#storage-checkList-unitnum-unit").val(auxremainder);
			}
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
                    $("#storage-checkList-profitlossamount").val(json.profitlossamount);
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
		}
        function validateFrom(){
            $("#storage-checkList-realnum").validatebox("isValid");
            $("#storage-checkList-unitnum-aux").validatebox("isValid");
            $("#storage-checkList-unitnum-unit").validatebox("isValid");
        }
   </script>
  </body>
</html>
