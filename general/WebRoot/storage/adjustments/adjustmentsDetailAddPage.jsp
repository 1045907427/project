<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>调账单明细添加</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
 	<div data-options="region:'center',border:false">
   	<form action="" method="post" id="storage-form-adjustmentsDetailAddPage">
        <input type="hidden" id="storage-adjustments-existingnum" name="existingnum"/>
        <input type="hidden" id="storage-adjustments-usablenum" name="usablenum"/>
   		<table cellpadding="4" cellspacing="2">
   			<tr>
   				<td width="100px;">选择商品:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-adjustments-goodsid" name="goodsid" width="180"/>
   					<input type="hidden" id="storage-adjustments-hidden-summarybatchid" name="summarybatchid"/>
   				</td>
   				<td>批次号:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-adjustments-batchno" name="batchno" />
   				</td>
   			</tr>
   			<tr>
   				<td>数量:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-adjustments-adjustnum" name="adjustnum" class="easyui-validatebox len150 goodsNum" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
   				</td>
   				<td>辅数量:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-adjustments-adjustnum-aux" name="auxadjustnum" style="width:60px;" class="easyui-validatebox goodsNum" data-options="required:true,validType:'integer'"/><span id="storage-adjustments-auxunitname1"></span>
   					<input type="text" id="storage-adjustments-adjustnum-unit" name="auxadjustremainder" style="width:60px;" class="easyui-validatebox goodsNum" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/><span id="storage-adjustments-goodsunitname1" style=""></span>
   					<input type="hidden" id="storage-adjustments-auxunitnumdetail" name="auxadjustnumdetail" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>单位:</td>
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
   					<input type="text" id="storage-adjustments-boxnum" class="no_input" readonly="readonly"/>
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

			<%--<tr>--%>
				<%--<td>单价:</td>--%>
				<%--<td style="text-align: left;">--%>
					<%--<input type="text" id="storage-adjustments-price" name="price" class="no_input" readonly="readonly"/>--%>
				<%--</td>--%>
				<%--<td>金额:</td>--%>
				<%--<td style="text-align: left;">--%>
					<%--<input type="text" id="storage-adjustments-amount" name="amount" class="no_input" readonly="readonly"/>--%>
				<%--</td>--%>
			<%--</tr>--%>

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
   				<td>截止日期:</td>
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
			<input type="button" value="继续添加" name="savegoon" id="storage-savegoon-adjustmentsDetailAddPage" />
		</div>
	</div>
</div>
   <script type="text/javascript">
       var storageid = $("#storage-adjustments-storageid").widget("getValue");
       var billtype = $("#storage-adjustments-billtype").val();
       var batchFlag = true;
       if(billtype=="1"){
           batchFlag = false;
           storageid = null;
       }

		$(function(){
			$("#storage-adjustments-goodsid").goodsWidget({
				required:true,
                storageid:storageid,
				onSelect:function(data){
					$("#storage-adjustments-goodsbrandName").val(data.brandName);
					$("#storage-adjustments-goodsbrandid").val(data.brand);
					$("#storage-adjustments-goodsmodel").val(data.model);
					$("#storage-adjustments-goodsbarcode").val(data.barcode);
					$("#storage-adjustments-boxnum").val(data.boxnum);
					$("#storage-adjustments-goodsunitname").val(data.mainunitName);
					$("#storage-adjustments-goodsunitname1").text(data.mainunitName);
					$("#storage-adjustments-goodsunitid").val(data.mainunit);
					$("#storage-adjustments-auxunitname").val(data.auxunitname);
					$("#storage-adjustments-auxunitname1").text(data.auxunitname);
					$("#storage-adjustments-auxunitid").val(data.auxunitid);
                    $("#storage-adjustments-taxtype").val(data.defaulttaxtype);
					$("#storage-adjustments-price").numberbox("setValue",data.newstorageprice);
					
					if(data.isbatch=='1'){
                        $("#storage-adjustments-produceddate").removeClass("WdateRead");
                        $("#storage-adjustments-produceddate").addClass("Wdate");
                        $("#storage-adjustments-produceddate").removeAttr("readonly");

                        $("#storage-adjustments-deadline").removeClass("WdateRead");
                        $("#storage-adjustments-deadline").addClass("Wdate");
                        $("#storage-adjustments-deadline").removeAttr("readonly");
						$("#storage-adjustments-batchno").widget("enable");
    					var storageid = $("#storage-adjustments-storageid").widget("getValue");
    					var param = null;
                    	if(storageid!=null && storageid!=""){
                    		param = [{field:'goodsid',op:'equal',value:data.id},
                    		       {field:'storageid',op:'equal',value:storageid}];
                    	}else{
                    		param = [{field:'goodsid',op:'equal',value:data.id}];
                    	}
                    	$("#storage-adjustments-batchno").widget({
                    		referwid:'RL_T_STORAGE_BATCH_LIST',
                    		param:param,
                			width:150,
            				singleSelect:true,
                            required:batchFlag,
            				onSelect: function(obj){
                                $("#storage-adjustments-produceddate").removeClass("Wdate");
                                $("#storage-adjustments-produceddate").addClass("WdateRead");
                                $("#storage-adjustments-produceddate").attr("readonly","readonly");
                                $("#storage-adjustments-deadline").removeClass("Wdate");
                                $("#storage-adjustments-deadline").addClass("WdateRead");
                                $("#storage-adjustments-deadline").attr("readonly","readonly");

            					$("#storage-adjustments-hidden-summarybatchid").val(obj.id);
            					$("#storage-adjustments-produceddate").val(obj.produceddate);
            					$("#storage-adjustments-deadline").val(obj.deadline);
            					$("#storage-adjustments-storagelocationid").val(obj.storagelocationid);
            					$("#storage-adjustments-storagelocationname").val(obj.storagelocationname);
                                $('#storage-adjustments-existingnum').val(obj.existingnum);
                                $('#storage-adjustments-usablenum').val(obj.usablenum);
                                $("#storage-adjustments-taxtype").val(data.defaulttaxtype);
                                
        	                    computNum();
        	                    $("#storage-adjustments-adjustnum").focus();
        						$("#storage-adjustments-adjustnum").select();
            				},
            				onClear:function(){
                                $("#storage-adjustments-produceddate").removeClass("WdateRead");
                                $("#storage-adjustments-produceddate").addClass("Wdate");
                                $("#storage-adjustments-produceddate").removeAttr("readonly");
                                $("#storage-adjustments-deadline").removeClass("WdateRead");
                                $("#storage-adjustments-deadline").addClass("Wdate");
                                $("#storage-adjustments-deadline").removeAttr("readonly");

            					$("#storage-adjustments-hidden-summarybatchid").val("");
            					$("#storage-adjustments-produceddate").val("");
            					$("#storage-adjustments-deadline").val("");
            					$("#storage-adjustments-storagelocationid").val("");
            					$("#storage-adjustments-storagelocationname").val("");
                                $('#storage-adjustments-existingnum').val(0);
                                $('#storage-adjustments-usablenum').val(0);
            				}
                    	});
                    	$("#storage-adjustments-batchno").focus();
					}else{
                        $("#storage-adjustments-batchno").widget("clear");
                        $("#storage-adjustments-batchno").widget({
                            referwid:'RL_T_STORAGE_BATCH_LIST',
							width:150,
                            disabled:true,
                            singleSelect:true
                        });

                        $("#storage-adjustments-produceddate").removeClass("Wdate");
                        $("#storage-adjustments-produceddate").addClass("WdateRead");
                        $("#storage-adjustments-produceddate").attr("readonly","readonly");

                        $("#storage-adjustments-deadline").removeClass("Wdate");
                        $("#storage-adjustments-deadline").addClass("WdateRead");
                        $("#storage-adjustments-deadline").attr("readonly","readonly");

                        $("#storage-adjustments-hidden-summarybatchid").val("");
    					$("#storage-adjustments-produceddate").val("");
    					$("#storage-adjustments-deadline").val("");
    					$("#storage-adjustments-storagelocationid").val("");
    					$("#storage-adjustments-storagelocationname").val("");
    					
                        $('#storage-adjustments-existingnum').val(obj.highestinventory);
                        $('#storage-adjustments-usablenum').val(obj.newinventory);
						computNum();
						getGoodsUsenum();
						$("#storage-adjustments-adjustnum").focus();
						$("#storage-adjustments-adjustnum").select();
					}
					
				},
                onClear:function(){
                    $("#storage-adjustments-batchno").widget("clear");
                    $("#storage-adjustments-batchno").widget({
                        referwid:'RL_T_STORAGE_BATCH_LIST',
						width:150,
                        disabled:true,
                        singleSelect:true
                    });
                    $("#storage-adjustments-produceddate").removeClass("Wdate");
                    $("#storage-adjustments-produceddate").addClass("WdateRead");
                    $("#storage-adjustments-produceddate").attr("readonly","readonly");

                    $("#storage-adjustments-deadline").removeClass("Wdate");
                    $("#storage-adjustments-deadline").addClass("WdateRead");
                    $("#storage-adjustments-deadline").attr("readonly","readonly");

                    $("#storage-adjustments-hidden-summarybatchid").val("");
                    $("#storage-adjustments-produceddate").val("");
                    $("#storage-adjustments-deadline").val("");
                    $("#storage-adjustments-storagelocationid").val("");
                    $("#storage-adjustments-storagelocationname").val("");

                    $('#storage-adjustments-existingnum').val(obj.highestinventory);
                    $('#storage-adjustments-usablenum').val(obj.newinventory);
                }
				
			});

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
                                var goodsid = $("#storage-adjustments-goodsid").goodsWidget("getValue");
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
                                var goodsid = $("#storage-adjustments-goodsid").goodsWidget("getValue");
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
			$("#storage-adjustments-batchno").widget({
        		referwid:'RL_T_STORAGE_BATCH_LIST',
				width:150,
    			disabled:true,
				singleSelect:true
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
                        $("#storage-savegoon-adjustmentsDetailAddPage").focus();
                    }
                });
                $("#storage-savegoon-adjustmentsDetailAddPage").die("keydown").live("keydown",function(event){
                    //enter
                    if(event.keyCode==13){
                        addSaveDetail(true);
                    }
                });
                $("#storage-savegoon-adjustmentsDetailAddPage").click(function(){
                    addSaveDetail(true);
                });
            }, 150);

		});
		//通过总数量 计算数量 金额换算
		function computNum(){
			var goodsid= $("#storage-adjustments-goodsid").goodsWidget("getValue");
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
			var goodsid= $("#storage-adjustments-goodsid").goodsWidget("getValue");
			if(null==goodsid){
				return false;
			}
			var auxunitid = $("#storage-adjustments-auxunitid").val();
			var taxprice = $("#storage-adjustments-price").val();
			var taxtype = '';
			var auxInterger = $("#storage-adjustments-adjustnum-aux").val();
			var auxremainder = $("#storage-adjustments-adjustnum-unit").val();
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
		function getGoodsUsenum(){
			var storageid = $("#storage-adjustments-storageid").widget("getValue");
			var goodsid= $("#storage-adjustments-goodsid").goodsWidget("getValue");
			$('#storage-adjustments-existingnum').val(0);
            $('#storage-adjustments-usablenum').val(0);
			//控件其他出库的最大数量
			$.ajax({   
	            url :'storage/getStorageSummarySumByGoodsid.do',
	            type:'post',
	            data:{goodsid:goodsid,storageid:storageid},
	            dataType:'json',
	            success:function(json){
	            	if(json.storageSummary!=null){
	                    $('#storage-adjustments-existingnum').val(json.storageSummary.existingnum);
                        $('#storage-adjustments-usablenum').val(json.storageSummary.usablenum);
	            	}
	            }
	        });
		}
   </script>
  </body>
</html>
