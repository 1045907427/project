<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>调账单明细添加</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
 	<div data-options="region:'center',border:false">
   	<form action="" method="post" id="storage-form-adjustmentsDetailEditPage">
   		<table cellpadding="4" cellspacing="2">
   			<tr>
   				<td width="100px;">商品名称:</td>
   				<td>
   					<input type="text" id="storage-adjustments-goodsname" width="180"  class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-adjustments-hidden-summarybatchid" name="summarybatchid"/>
   					<input type="hidden" id="storage-adjustments-hidden-goodsid" name="goodsid"/>
   					<input type="hidden" id="storage-adjustments-hidden-isbatch" value="${isbatch }"/>
   				</td>
   				<td>批次号:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-adjustments-batchno" name="batchno" />
   				</td>
   			</tr>
   			<tr>
   				<td>数量:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-adjustments-adjustnum" name="adjustnum" class="formaterNum easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
   				</td>
   				<td>辅数量:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-adjustments-adjustnum-aux" name="auxadjustnum" style="width:60px;" class="formaterNum easyui-validatebox" data-options="required:true,validType:'integer'"/><span id="storage-adjustments-auxunitname1"></span>
   					<input type="text" id="storage-adjustments-adjustnum-unit" name="auxadjustremainder" style="width:60px;" class="formaterNum easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/><span id="storage-adjustments-goodsunitname1"></span>
   					<input type="hidden" id="storage-adjustments-auxunitnumdetail" name="auxadjustnumdetail" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>主单位:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-adjustments-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-adjustments-goodsunitid" name="unitid"/>
   				</td>
   				<td>辅单位:</td>
   				<td>
   					<input type="text" id="storage-adjustments-auxunitname" name="auxunitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-adjustments-auxunitid" name="auxunitid"/>
   				</td>
   			</tr>
   			<tr>
   				<td width="120">商品品牌:</td>
   				<td>
   					<input type="text" id="storage-adjustments-goodsbrandName" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-adjustments-goodsbrandid" name="brandid" />
   				</td>
   				<td width="120">箱装量:</td>
   				<td>
   					<input type="text" id="storage-adjustments-boxnum" class="formaterNum no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td width="120">条形码:</td>
   				<td>
   					<input type="text" id="storage-adjustments-goodsbarcode" name="barcode" class="no_input" readonly="readonly"/>
   				</td>
   				<td width="120">规格型号:</td>
   				<td>
   					<input type="text" id="storage-adjustments-goodsmodel" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>含税单价:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-adjustments-price" name="price" class="no_input" readonly="readonly"/>
   				</td>
   				<td>含税金额:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-adjustments-amount" name="amount" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>

			<tr>
				<td>未税单价:</td>
				<td style="text-align: left;">
					<input type="text" id="storage-adjustments-notaxprice" name="notaxprice" class="no_input" readonly="readonly"/>
				</td>
				<td>未税金额:</td>
				<td style="text-align: left;">
					<input type="text" id="storage-adjustments-notaxamount" name="notaxamount" class="no_input" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>税种:</td>
				<td style="text-align: left;">
					<input type="text" id="storage-adjustments-taxtypename" name="taxtypename" class="no_input" readonly="readonly"/>
					<input type="hidden" id="storage-adjustments-taxtype" name="taxtype" class="no_input" readonly="readonly"/>
				</td>
				<td>税额:</td>
				<td style="text-align: left;">
					<input type="text" id="storage-adjustments-tax" name="tax" class="no_input" readonly="readonly"/>
				</td>
			</tr>

   			<tr>
   				<td>所属库位:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-adjustments-storagelocationname" name="storagelocationname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-adjustments-storagelocationid"  name="storagelocationid"/>
   				</td>
   				
   			</tr>
   			<tr>
   				<td>生产日期:</td>
   				<td>
   					<input type="text" id="storage-adjustments-produceddate" name="produceddate" class="WdateRead" readonly="readonly"/>
   				</td>
   				<td>有效截止日期:</td>
   				<td>
   					<input type="text" id="storage-adjustments-deadline" name="deadline" class="WdateRead" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>备注:</td>
   				<td colspan="3" style="text-align: left;">
   					<input id="storage-adjustmentsdetial-remark" type="text" name="remark" style="width: 438px;" maxlength="200"/>
   				</td>
   			</tr>
   		</table>
    </form>
    </div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="text-align: right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
			<input type="button" value="确定" name="savegoon" id="storage-savegoon-adjustmentsDetailEditPage" />
		</div>
	</div>
  </div>
   <script type="text/javascript">
 		//加载数据
		var object = $("#storage-datagrid-adjustmentsAddPage").datagrid("getSelected");
		$("#storage-form-adjustmentsDetailEditPage").form("load",object);
		$("#storage-adjustments-goodsname").val(object.goodsInfo.name);
		$("#storage-adjustments-goodsbrandName").val(object.goodsInfo.brandName);
		$("#storage-adjustments-goodsmodel").val(object.goodsInfo.model);
		$("#storage-adjustments-goodsbarcode").val(object.goodsInfo.barcode);
		$("#storage-adjustments-auxunitname1").text(object.auxunitname);
		$("#storage-adjustments-goodsunitname1").text(object.unitname);
		$("#storage-adjustments-boxnum").val(object.goodsInfo.boxnum);
        $("#storage-adjustments-taxtypename").val(object.goodsInfo.defaulttaxtypeName);
		$(function(){
			$('#storage-adjustments-adjustnum').change(function(){
				computNum();
			});

			$('#storage-adjustments-adjustnum-aux').change(function(){
				computNumByAux();
			});

			$('#storage-adjustments-adjustnum-unit').change(function(){
				computNumByAux();
			});

            $("#storage-adjustments-produceddate").click(function(){
                if($("#storage-adjustments-produceddate").hasClass("Wdate")){
                    WdatePicker({dateFmt:'yyyy-MM-dd',
                        onpicked:function(dp){
                            if(dp.el.id=="storage-adjustments-produceddate"){
                                var produceddate = dp.cal.getDateStr();
                                var goodsid = $("#storage-adjustments-hidden-goodsid").val();
                                $.ajax({
                                    url :'storage/getBatchno.do',
                                    type:'post',
                                    data:{produceddate:produceddate,goodsid:goodsid},
                                    dataType:'json',
                                    async:false,
                                    success:function(json){
                                        $('#storage-adjustments-deadline').val(json.deadline);
                                    }
                                });
                            }
                        }
                    });
                }
            });
            $("#storage-adjustments-deadline").click(function(){
                if($("#storage-adjustments-deadline").hasClass("Wdate")){
                    WdatePicker({dateFmt:'yyyy-MM-dd',
                        onpicked:function(dp){
                            if(dp.el.id=="storage-adjustments-deadline"){
                                var deadline = dp.cal.getDateStr();
                                var goodsid = $("#storage-adjustments-hidden-goodsid").val();
                                $.ajax({
                                    url :'storage/getBatchnoByDeadline.do',
                                    type:'post',
                                    data:{deadline:deadline,goodsid:goodsid},
                                    dataType:'json',
                                    async:false,
                                    success:function(json){
                                        $('#storage-adjustments-produceddate').val(json.produceddate);
                                    }
                                });
                            }
                        }
                    });
                }
            });
            setTimeout(function() {
                //单价
                $("#storage-adjustments-price").numberbox({
                    precision:6,
                    groupSeparator:','
                });
                //金额
                $("#storage-adjustments-amount").numberbox({
                    precision:2,
                    groupSeparator:','
                });
                $("#storage-adjustments-notaxprice").numberbox({
                    precision:6,
                    groupSeparator:','
                });
                $("#storage-adjustments-notaxamount").numberbox({
                    precision:2,
                    groupSeparator:','
                });
                $("#storage-adjustments-tax").numberbox({
                    precision:2,
                    groupSeparator:','
                });
                $("#storage-adjustments-adjustnum").off("keydown").on("keydown",function(event){
                    //enter
                    if(event.keyCode==13){
                        $("#storage-adjustments-adjustnum").blur();
                        $("#storage-adjustments-adjustnum-aux").focus();
                        $("#storage-adjustments-adjustnum-aux").select();
                    }
                });
                $("#storage-adjustments-adjustnum-aux").off("keydown").on("keydown",function(event){
                    if(event.keyCode==13){
                        $("#storage-adjustments-adjustnum-aux").blur();
                        $("#storage-adjustments-adjustnum-unit").focus();
                        $("#storage-adjustments-adjustnum-unit").select();
                    }
                });
                $("#storage-adjustments-adjustnum-unit").off("keydown").on("keydown",function(event){
                    if(event.keyCode==13){
                        $("#storage-adjustments-adjustnum-aux").blur();
                        $("#storage-adjustmentsdetial-remark").focus();
                        $("#storage-adjustmentsdetial-remark").select();
                    }
                });
                $("#storage-adjustmentsdetial-remark").die("keydown").live("keydown",function(event){
                    if(event.keyCode==13){
                        $("#storage-allocateOut-taxprice").blur();
                        $("#storage-savegoon-adjustmentsDetailEditPage").focus();
                    }
                });
                $("#storage-savegoon-adjustmentsDetailEditPage").die("keydown").live("keydown",function(event){
                    //enter
                    if(event.keyCode==13){
                        editSaveDetail(true);
                    }
                });
                $("#storage-savegoon-adjustmentsDetailEditPage").click(function(){
                    editSaveDetail(true);
                });
            }, 150);

		});
		//通过总数量 计算数量 金额换算
		function computNum(){
			var goodsid= $("#storage-adjustments-hidden-goodsid").val();
			if(null==goodsid){
				return false;
			}
			var auxunitid = $("#storage-adjustments-auxunitid").val();
			var unitnum = $("#storage-adjustments-adjustnum").val();
			var taxprice = $("#storage-adjustments-price").val();
			var taxtype = '';
			$("#storage-adjustments-adjustnum").addClass("inputload");
			$.ajax({   
	            url :'basefiles/computeGoodsByUnitnum.do',
	            type:'post',
	            data:{unitnum:unitnum,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#storage-adjustments-amount").numberbox("setValue",json.taxamount);
	            	$("#storage-adjustments-auxunitnumdetail").val(json.auxnumdetail);
	            	$("#storage-adjustments-auxunitnum").val(json.auxnum);
	            	$("#storage-adjustments-auxunitname").val(json.auxunitname);
	            	$("#storage-adjustments-auxunitname1").html(json.auxunitname);
	            	$("#storage-adjustments-goodsunitname").val(json.unitname);
	            	$("#storage-adjustments-goodsunitname1").html(json.unitname);
	            	
	            	$("#storage-adjustments-price").val(json.taxprice);
                    $("#storage-adjustments-notaxprice").numberbox("setValue",json.notaxprice);
	            	$("#storage-adjustments-notaxamount").numberbox("setValue",json.notaxamount);
                    $("#storage-adjustments-tax").numberbox("setValue",json.tax);
                    $("#storage-adjustments-taxtypename").val(json.taxtypename);

	            	$("#storage-adjustments-adjustnum-aux").val(json.auxInteger);
	            	$("#storage-adjustments-adjustnum-unit").val(json.auxremainder);
	            	if(json.auxrate!=null){
	            		$("#storage-adjustments-adjustnum-unit").attr("max",json.auxrate-1);
	            	}
	            	$("#storage-adjustments-adjustnum").removeClass("inputload");
	            }
	        });
		}
		//通过辅单位数量
		function computNumByAux(){
			var goodsid= $("#storage-adjustments-hidden-goodsid").val();
			if(null==goodsid){
				return false;
			}
			var auxunitid = $("#storage-adjustments-auxunitid").val();
			var taxprice = $("#storage-adjustments-price").val();
			var taxtype = '';
			var auxInterger = $("#storage-adjustments-adjustnum-aux").val();
			var auxremainder = $("#storage-adjustments-adjustnum-unit").val();
			var auxmax = $("#storage-adjustments-adjustnum-unit").attr("max");
			if(Number(auxremainder)>Number(auxmax)){
				auxremainder = auxmax;
				$("#storage-adjustments-adjustnum-unit").val(auxremainder);
			}
			$("#storage-adjustments-adjustnum").addClass("inputload");
			$.ajax({   
	            url :'basefiles/computeGoodsByAuxnum.do',
	            type:'post',
	            data:{auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#storage-adjustments-amount").numberbox("setValue",json.taxamount);
	            	
	            	$("#storage-adjustments-price").val(json.taxprice);
	            	$("#storage-adjustments-notaxamount").numberbox("setValue",json.notaxamount);
	            	
	            	$("#storage-adjustments-adjustnum").val(json.mainnum);
                    $("#storage-adjustments-adjustnum-aux").val(json.auxInterger);
                    $("#storage-adjustments-adjustnum-unit").val(json.auxremainder);
                    $("#storage-adjustments-auxunitnumdetail").val(json.auxnumdetail);

	            	$("#storage-adjustments-adjustnum").removeClass("inputload");
	            }
	        });
		}
   </script>
  </body>
</html>
