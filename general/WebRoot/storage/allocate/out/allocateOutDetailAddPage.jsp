<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>调拨单明细添加</title>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
	   	<form action="" method="post" id="storage-form-allocateOutDetailAddPage">
	   		<input type="hidden" id="storage-allocateOut-notaxprice" name="notaxprice">
	   		<input type="hidden" id="storage-allocateOut-notaxamount" name="notaxamount">
	   		<input type="hidden" id="storage-allocateOut-tax" name="tax">
	   		<input type="hidden" id="storage-allocateOut-taxtypename" name="taxtypename"/>
	   		<input type="hidden" id="storage-allocateOut-taxtype" name="taxtype"/>
            <input type="hidden" id="storage-allocateOut-usablenum" name="usablenum"/>
	   		<table  border="0" class="box_table">
	   			<tr>
	   				<td width="120">选择商品:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-allocateOut-goodsid" name="goodsid" width="180"/>
	   				</td>
                    <td id="storage-allocateOut-loadInfo" colspan="2" style="text-align: left;"></td>
	   			</tr>
	   			<tr>
	   				<td>数量:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-allocateOut-unitnum" name="unitnum" class="easyui-validatebox" data-options="validType:'intOrFloatNum[${decimallen}]'"/>
	   				</td>
	   				<td>辅数量:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-allocateOut-unitnum-aux" name="auxnum" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'integer'"/>
	   					<span id="storage-allocateOut-auxunitname1" style="float: left;"></span>
	   					<input type="text" id="storage-allocateOut-unitnum-unit" name="auxremainder" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
	   					<span id="storage-allocateOut-goodsunitname1" style="float: left;"></span>
	   					<input type="hidden" id="storage-allocateOut-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>单位:</td>
                    <td style="text-align: left;">
                        主:
                        <input type="text" style="width: 40px;float: none;" id="storage-allocateOut-goodsunitname" name="unitname" class="readonly2" readonly="readonly"/>
                        <input type="hidden" id="storage-allocateOut-goodsunitid" name="unitid"/>
                        辅:
                        <input type="text" style="width: 40px;float: none;" id="storage-allocateOut-auxunitname" name="auxunitname" class="readonly2" readonly="readonly"/>
                        <input type="hidden" id="storage-allocateOut-auxunitid" name="auxunitid"/>
                    </td>
                    <td width="120">规格型号:</td>
                    <td>
                        <input type="text" id="storage-allocateOut-goodsmodel" class="no_input" readonly="readonly"/>
                    </td>
	   			</tr>
                <tr class="allocateOutPrice" style="display: none">
                    <td width="120">调拨单价:</td>
                    <td>
                        <input type="text" id="storage-allocateOut-taxprice" class="easyui-validatebox" validType="intOrFloat" name="taxprice" />
                    </td>

                    <td width="120">调拨金额:</td>
                    <td>
                        <input type="text" id="storage-allocateOut-taxamount" class="easyui-validatebox" validType="intOrFloat"  name="taxamount" />
                    </td>
                </tr>
                <tr>
                    <td width="120" class="allocateOutPrice" style="display: none">调拨箱价:</td>
                    <td class="allocateOutPrice" style="display: none">
                        <input type="text" id="storage-allocateOut-boxprice" class="easyui-validatebox" validType="intOrFloat" name="boxprice"/>
                    </td>
                    <td width="120">箱装量:</td>
                    <td>
                        <input type="text" id="storage-allocateOut-boxnum" class="no_input" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td width="120">商品品牌:</td>
                    <td>
                        <input type="text" id="storage-allocateOut-goodsbrandName" class="no_input" readonly="readonly"/>
                    </td>
                    <td width="120">条形码:</td>
                    <td>
                        <input type="text" id="storage-allocateOut-barcode" class="no_input" readonly="readonly"/>
                    </td>
                </tr>
	   			<tr>
                    <td>出库批次号:</td>
                    <td>
                        <input type="text" id="storage-allocateOut-batchno" name="batchno" />
                    </td>
	   				<td>出库库位:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-allocateOut-storagelocationname" name="storagelocationname" class="no_input" readonly="readonly"/>
	   					<input type="hidden" id="storage-allocateOut-storagelocationid"  name="storagelocationid"/>
	   					<input type="hidden" id="storage-allocateOut-detail-storageid"  name="storageid"/>
	   					<input type="hidden" id="storage-allocateOut-detail-summarybatchid"  name="summarybatchid"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>出库生产日期:</td>
	   				<td>
	   					<input type="text" id="storage-allocateOut-produceddate" style="height: 20px;" name="produceddate" class="WdateRead" readonly="readonly"/>
	   				</td>
	   				<td>出库截止日期:</td>
	   				<td>
	   					<input type="text" id="storage-allocateOut-deadline" style="height: 20px;" name="deadline" class="WdateRead" readonly="readonly"/>
	   				</td>
	   			</tr>
                <tr>
                    <td>入库生产日期</td>
                    <td>
                        <input type="text" id="storage-allocateOut-enterproduceddate" style="height: 20px;" name="enterproduceddate" class="WdateRead" readonly="readonly"/>
                    </td>
                    <td>入库截止日期</td>
                    <td>
                        <input type="text" id="storage-allocateOut-enterdeadline" style="height: 20px;" name="enterdeadline" class="WdateRead" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td>入库批次号:</td>
                    <td>
                        <input type="text" id="storage-allocateOut-enterbatchno" name="enterbatchno" class="no_input" readonly="readonly"/>
                    </td>
                    <td>入库库位:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-allocateOut-enterstoragelocationid" name="enterstoragelocationid"/>
                        <input type="hidden" id="storage-allocateOut-enterstoragelocationname" name="enterstoragelocationname"/>
                    </td>
                </tr>
	   			<tr>
	   				<td>备注:</td>
	   				<td colspan="3" style="text-align: left;">
	   					<input id="storage-allocateOutDetail-remark" type="text" name="remark" style="width: 490px;" maxlength="200"/>
	   				</td>
	   			</tr>
	   		</table>
			<input type="hidden" id="storage-allocateOut-costprice" name="costprice"/>
	    </form>
	    </div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="text-align: right;">
  				快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
	  			<input type="button" value="继续添加" name="savegoon" id="storage-savegoon-allocateOutDetailAddPage" />
  			</div>
  		</div>
  </div>
   <script type="text/javascript">
   		var storageid = $("#storage-allocateOut-outstorageid").widget("getValue");
        var outStorage = $("#storage-allocateOut-outstorageid").widget("getObject");
        var enterStorage = $("#storage-allocateOut-enterstorageid").widget("getObject");
        var storageFlag = true;
        <security:authorize url="/storage/allocateOutStorageView.do">
        storageFlag = false;
        </security:authorize>
		$(function(){
            <security:authorize url="/storage/eidtAndViewAllocateOutPrice.do" >
            $(".allocateOutPrice").show();
            $('#storage-allocateOut-taxprice').validatebox({required: true});
            $('#storage-allocateOut-taxamount').validatebox({required: true});
            $('#storage-allocateOut-boxprice').validatebox({required: true});
            </security:authorize>
			$("#storage-allocateOut-goodsid").goodsWidget({
	    		width:130,
				singleSelect:true,
				required:true,
                storageid:storageid,
                isHiddenUsenum:storageFlag,
				onSelect:function(data){
                    if(!storageFlag){
                        $("#storage-allocateOut-loadInfo").html("现存量：<font color='green'>"+data.highestinventory+data.mainunitName+"</font>&nbsp;可用量：<font color='green'>"+ data.newinventory +data.mainunitName+"</font>");
                    }
                    $("#storage-allocateOut-goodsbrandName").val(data.brandName);
					$("#storage-allocateOut-goodsmodel").val(data.model);
					$("#storage-allocateOut-goodsunitname").val(data.mainunitName);
					$("#storage-allocateOut-goodsunitid").val(data.mainunit);
					$("#storage-allocateOut-auxunitname1").html(data.auxunitname);
					$("#storage-allocateOut-goodsunitname1").html(data.mainunitName);
					
					$("#storage-allocateOut-auxunitid").val(data.auxunitid);
					$("#storage-allocateOut-taxtype").val(data.taxtype);
					$("#storage-allocateOut-taxprice").val(data.newstorageprice);
					$("#storage-allocateOut-barcode").val(data.barcode);
					$("#storage-allocateOut-boxnum").val(data.boxnum);
					$("#storage-allocateOut-unitnum").val(0);
					$.ajax({
						url :'storage/getStorageSummaryCostprice.do',
						type:'post',
						data:{goodsid:data.id,storageid:$("#storage-allocateOut-outstorageid").widget("getValue")},
						dataType:'json',
						async:false,
						success:function(json){
							$("#storage-allocateOut-costprice").val(json.costprice);
						}
					});
					var billtype=$("#storage-allocateOut-billtype").val();
					if(billtype=='1'){
						$("#storage-allocateOut-taxprice").val($("#storage-allocateOut-costprice").val());
						$("#storage-allocateOut-taxprice").attr('readonly','readonly');
						$("#storage-allocateOut-boxprice").attr('readonly','readonly');
						$("#storage-allocateOut-taxamount").attr('readonly','readonly');
					}
					if(data.isbatch=='1'){
						$("#storage-allocateOut-batchno").widget("enable");
						$("#storage-allocateOut-enterstoragelocationid").widget("enable");

                        $("#storage-allocateOut-enterproduceddate").removeClass("WdateRead");
                        $("#storage-allocateOut-enterproduceddate").addClass("Wdate");
                        $("#storage-allocateOut-enterproduceddate").removeAttr("readonly");
                        if(enterStorage.isbatch=="1"){
                            $("#storage-allocateOut-enterproduceddate").validatebox({required:true});
                        }else{
                            $("#storage-allocateOut-enterproduceddate").validatebox({required:false});
                        }

                        $("#storage-allocateOut-enterdeadline").removeClass("WdateRead");
                        $("#storage-allocateOut-enterdeadline").addClass("Wdate");
                        $("#storage-allocateOut-enterdeadline").removeAttr("readonly");

    					var storageid = $("#storage-allocateOut-outstorageid").widget("getValue");
    					var param = null;
                    	if(storageid!=null && storageid!=""){
                    		param = [{field:'goodsid',op:'equal',value:data.id},
                    		       {field:'storageid',op:'equal',value:storageid}];
                    	}else{
                    		param = [{field:'goodsid',op:'equal',value:data.id}];
                    	}
                        //批次是否必填
                        var reFlag = false;
                        if(outStorage.isbatch=="1"){
                            reFlag = true;
                        }
                    	$("#storage-allocateOut-batchno").widget({
                    		referwid:'RL_T_STORAGE_BATCH_LIST',
                    		param:param,
                			width:165,
                			required:reFlag,
            				singleSelect:true,
            				onSelect: function(obj){
            					$("#storage-allocateOut-detail-summarybatchid").val(obj.id);
            					$("#storage-allocateOut-storagelocationname").val(obj.storagelocationname);
            					$("#storage-allocateOut-storagelocationid").val(obj.storagelocationid);
            					$("#storage-allocateOut-produceddate").val(obj.produceddate);
            					$("#storage-allocateOut-deadline").val(obj.deadline);
                                $("#storage-allocateOut-enterproduceddate").val(obj.produceddate);
                                $("#storage-allocateOut-enterdeadline").val(obj.deadline);

            				},
            				onClear:function(){
								$("#storage-allocateOut-detail-summarybatchid").val("");
								$("#storage-allocateOut-storagelocationname").val("");
								$("#storage-allocateOut-storagelocationid").val("");
								$("#storage-allocateOut-produceddate").val("");
								$("#storage-allocateOut-deadline").val("");
                                $("#storage-allocateOut-enterproduceddate").val("");
                                $("#storage-allocateOut-enterdeadline").val("");
            				}
                    	});
						computNum();
					}else{
                        $("#storage-allocateOut-batchno").widget("clear");
                        $("#storage-allocateOut-batchno").widget("disable");
                        $("#storage-allocateOut-batchno").widget("required",false);

                        $("#storage-allocateOut-enterproduceddate").removeClass("Wdate");
                        $("#storage-allocateOut-enterproduceddate").addClass("WdateRead");
                        $("#storage-allocateOut-enterproduceddate").attr("readonly","readonly");
                        $("#storage-allocateOut-enterproduceddate").validatebox({required:false});

                        $("#storage-allocateOut-enterdeadline").removeClass("Wdate");
                        $("#storage-allocateOut-enterdeadline").addClass("WdateRead");
                        $("#storage-allocateOut-enterdeadline").attr("readonly","readonly");

						$("#storage-allocateOut-detail-summarybatchid").val("");
    					$("#storage-allocateOut-storagelocationname").val("");
    					$("#storage-allocateOut-storagelocationid").val("");
    					$("#storage-allocateOut-produceddate").val("");
    					$("#storage-allocateOut-deadline").val("");
                        $("#storage-allocateOut-enterproduceddate").val("");
                        $("#storage-allocateOut-enterdeadline").val("");

	                    computNum();
					}
                    $("#storage-allocateOut-unitnum").focus();
                    $("#storage-allocateOut-unitnum").select();
				},
				onClear :function(){
                    $("#storage-allocateOut-batchno").widget("clear");
					$("#storage-allocateOut-batchno").widget("disable");
                    $("#storage-allocateOut-batchno").widget("required",false);
					$("#storage-allocateOut-enterstoragelocationid").widget("disable");

                    $("#storage-allocateOut-enterproduceddate").removeClass("Wdate");
                    $("#storage-allocateOut-enterproduceddate").addClass("WdateRead");
                    $("#storage-allocateOut-enterproduceddate").attr("readonly","readonly");
                    $("#storage-allocateOut-enterproduceddate").validatebox({required:false});
                    $("#storage-allocateOut-enterdeadline").removeClass("Wdate");
                    $("#storage-allocateOut-enterdeadline").addClass("WdateRead");
                    $("#storage-allocateOut-enterdeadline").attr("readonly","readonly");

					$("#storage-allocateOut-detail-summarybatchid").val("");
					$("#storage-allocateOut-storagelocationname").val("");
					$("#storage-allocateOut-storagelocationid").val("");
					$("#storage-allocateOut-produceddate").val("");
                    $("#storage-allocateOut-enterproduceddate").val("");
                    $("#storage-allocateOut-enterdeadline").val("");
					$("#storage-allocateOut-deadline").val("");

				}
			});

            $("#storage-allocateOut-enterproduceddate").click(function(){
                if($("#storage-allocateOut-enterproduceddate").hasClass("Wdate")){
                    WdatePicker({dateFmt:'yyyy-MM-dd',
                        onpicked:function(dp){
                            if(dp.el.id=="storage-allocateOut-enterproduceddate"){
                                var produceddate = dp.cal.getDateStr();
                                var goodsid = $("#storage-allocateOut-goodsid").goodsWidget("getValue");
                                $.ajax({
                                    url :'storage/getBatchno.do',
                                    type:'post',
                                    data:{produceddate:produceddate,goodsid:goodsid},
                                    dataType:'json',
                                    async:false,
                                    success:function(json){
                                        $('#storage-allocateOut-enterbatchno').val(json.batchno);
                                        $('#storage-allocateOut-enterdeadline').val(json.deadline);
                                    }
                                });
                            }
                        }
                    });
                }
            });
            $("#storage-allocateOut-enterdeadline").click(function(){
                if($("#storage-allocateOut-enterdeadline").hasClass("Wdate")){
                    WdatePicker({dateFmt:'yyyy-MM-dd',
                        onpicked:function(dp){
                            if(dp.el.id=="storage-allocateOut-enterdeadline"){
                                var deadline = dp.cal.getDateStr();
                                var goodsid = $("#storage-allocateOut-goodsid").goodsWidget("getValue");
                                $.ajax({
                                    url :'storage/getBatchnoByDeadline.do',
                                    type:'post',
                                    data:{deadline:deadline,goodsid:goodsid},
                                    dataType:'json',
                                    async:false,
                                    success:function(json){
                                        $('#storage-allocateOut-enterbatchno').val(json.batchno);
                                        $('#storage-allocateOut-enterproduceddate').val(json.produceddate);
                                    }
                                });
                            }
                        }
                    });
                }
            });

			$("#storage-allocateOut-batchno").widget({
        		referwid:'RL_T_STORAGE_BATCH_LIST',
                param:[{field:'storageid',op:'equal',value:storageid}],
    			width:165,
    			disabled:true,
				singleSelect:true
			});
			$("#storage-allocateOut-enterstoragelocationid").widget({
				name:'t_storage_allocate_notice_detail',
	    		width:165,
				col:'enterstoragelocationid',
				singleSelect:true,
				disabled:true,
				onSelect:function(data){
					$("#storage-allocateOut-enterstoragelocationname").val(data.name);
				},
				onClear:function(){
					$("#storage-allocateOut-enterstoragelocationname").val("");
				}
			});
			$("#storage-allocateOut-unitnum").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				computNum();
			});
			$("#storage-allocateOut-unitnum-aux").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				computNumByAux();
			});
			$("#storage-allocateOut-unitnum-unit").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				computNumByAux();
			});
            $("#storage-allocateOut-taxprice").change(function(){
                if($(this).val()==""){
                    $(this).val("0");
                    $(this).validatebox("validate");
                }
                priceChange();
            });
            $("#storage-allocateOut-boxprice").change(function(){
                if($(this).val()==""){
                    $(this).val("0");
                    $(this).validatebox("validate");
                }
                computBoxprice();
            });
            $("#storage-allocateOut-taxamount").change(function(){
                if($(this).val()==""){
                    $(this).val("0");
                    $(this).validatebox("validate");
                }
                amountChange();
            });
			$("#storage-allocateOut-unitnum").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#storage-allocateOut-unitnum").blur();
		   			$("#storage-allocateOut-unitnum-aux").focus();
		   			$("#storage-allocateOut-unitnum-aux").select();
				}
			});
			$("#storage-allocateOut-unitnum-aux").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-allocateOut-unitnum-auxm").blur();
		   			$("#storage-allocateOut-unitnum-unit").focus();
		   			$("#storage-allocateOut-unitnum-unit").select();
				}
			});
			$("#storage-allocateOut-unitnum-unit").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-allocateOut-unitnum-auxm").blur();
		   			$("#storage-allocateOutDetail-remark").focus();
		   			$("#storage-allocateOutDetail-remark").select();
				}
			});
			$("#storage-allocateOutDetail-remark").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-allocateOut-taxprice").blur();
		   			$("#storage-savegoon-allocateOutDetailAddPage").focus();
				}
			});
			$("#storage-savegoon-allocateOutDetailAddPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					addSaveDetail(true);
				}
			});
		    $("#storage-savegoon-allocateOutDetailAddPage").click(function(){
		    	addSaveDetail(true);
		    });
		});
		//通过总数量 计算数量 金额换算
		function computNum(){
			var goodsid= $("#storage-allocateOut-goodsid").goodsWidget("getValue");
			if(null==goodsid){
				return false;
			}
			var auxunitid = $("#storage-allocateOut-auxunitid").val();
			var unitnum = $("#storage-allocateOut-unitnum").val();
			var taxprice = $("#storage-allocateOut-taxprice").val();
			var notaxprice = $("#storage-allocateOut-notaxprice").val();
			var taxtype = $("#storage-allocateOut-taxtype").val();
			$("#storage-allocateOut-unitnum").addClass("inputload");
			$.ajax({   
	            url :'basefiles/computeGoodsByUnitnum.do',
	            type:'post',
	            data:{unitnum:unitnum,goodsid:goodsid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#storage-allocateOut-taxamount").val(json.taxamount);
	            	$("#storage-allocateOut-notaxamount").val(json.notaxamount);
	            	$("#storage-allocateOut-tax").val(json.tax);
	            	$("#storage-allocateOut-taxtypename").val(json.taxtypename);
	            	$("#storage-allocateOut-auxunitnumdetail").val(json.auxnumdetail);
	            	$("#storage-allocateOut-auxunitnum").val(json.auxnum);
	            	$("#storage-allocateOut-auxunitname").val(json.auxunitname);
	            	$("#storage-allocateOut-auxunitname1").html(json.auxunitname);
	            	$("#storage-allocateOut-goodsunitname").val(json.unitname);
	            	$("#storage-allocateOut-goodsunitname1").html(json.unitname);
	            	
	            	$("#storage-allocateOut-taxprice").val(json.taxprice);
                    $("#storage-allocateOut-boxprice").val(json.boxprice);
	            	$("#storage-allocateOut-notaxprice").val(json.notaxprice);

	            	$("#storage-allocateOut-unitnum-aux").val(json.auxInteger);
	            	$("#storage-allocateOut-unitnum-unit").val(json.auxremainder);
	            	if(json.auxrate!=null){
	            		$("#storage-allocateOut-unitnum-unit").attr("max",json.auxrate-1);
	            	}
	            	$("#storage-allocateOut-unitnum").removeClass("inputload");
	            }
	        });
		}
		//通过辅单位数量
		function computNumByAux(){
			var goodsid= $("#storage-allocateOut-goodsid").goodsWidget("getValue");
			if(null==goodsid){
				return false;
			}
			var auxunitid = $("#storage-allocateOut-auxunitid").val();
			var taxprice = $("#storage-allocateOut-taxprice").val();
			var notaxprice = $("#storage-allocateOut-notaxprice").val();
			var taxtype = $("#storage-allocateOut-taxtype").val();
			var auxInterger = $("#storage-allocateOut-unitnum-aux").val();
			var auxremainder = $("#storage-allocateOut-unitnum-unit").val();
			$("#storage-allocateOut-unitnum").addClass("inputload");
			$.ajax({   
	            url :'basefiles/computeGoodsByAuxnum.do',
	            type:'post',
	            data:{auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#storage-allocateOut-taxamount").val(json.taxamount);
	            	$("#storage-allocateOut-notaxamount").val(json.notaxamount);
	            	$("#storage-allocateOut-tax").val(json.tax);
	            	$("#storage-allocateOut-taxtypename").val(json.taxtypename);
	            	
	            	$("#storage-allocateOut-taxprice").val(json.taxprice);
//                    $("#storage-allocateOut-boxprice").val(json.boxprice);
	            	$("#storage-allocateOut-notaxprice").val(json.notaxprice);

	            	$("#storage-allocateOut-unitnum").val(json.mainnum);
                    $("#storage-allocateOut-unitnum-aux").val(json.auxInterger);
                    $("#storage-allocateOut-unitnum-unit").val(json.auxremainder);
                    $("#storage-allocateOut-auxunitnumdetail").val(json.auxnumdetail);
	            	
	            	$("#storage-allocateOut-unitnum").removeClass("inputload");
	            }
	        });
		}

        function priceChange(){
            var goodsid= $("#storage-allocateOut-goodsid").goodsWidget("getValue");
            if(null==goodsid){
                return false;
            }
            var auxunitid = $("#storage-allocateOut-auxunitid").val();
            var unitnum = $("#storage-allocateOut-unitnum").val();
            var taxprice = $("#storage-allocateOut-taxprice").val();
            var taxtype = $("#storage-allocateOut-taxtype").val();
            $("#storage-allocateOut-taxprice").addClass("inputload");
            $.ajax({
                url :'sales/getAmountChanger.do',
                type:'post',
                data:{type:'1',price:taxprice,taxtype:taxtype,unitnum:unitnum,id:goodsid},
                dataType:'json',
                async:false,
                success:function(json){
                    $("#storage-allocateOut-taxamount").val(json.taxAmount);
                    $("#storage-allocateOut-notaxamount").val(json.noTaxAmount);
                    $("#storage-allocateOut-tax").val(json.tax);

                    $("#storage-allocateOut-taxprice").val(json.taxPrice);
                    $("#storage-allocateOut-boxprice").val(json.boxPrice);
                    $("#storage-allocateOut-notaxprice").val(json.noTaxPrice);

                    $("#storage-allocateOut-taxprice").removeClass("inputload");
                }
            });
        }
        //通过总数量 计算数量 金额换算
        function computBoxprice(){
            var goodsid= $("#storage-allocateOut-goodsid").goodsWidget("getValue");
            if(null==goodsid){
                return false;
            }
            var auxunitid = $("#storage-allocateOut-auxunitid").val();
            var unitnum = $("#storage-allocateOut-unitnum").val();
            var boxprice = $("#storage-allocateOut-boxprice").val();
            var notaxprice = $("#storage-allocateOut-notaxprice").val();
            var taxtype = $("#storage-allocateOut-taxtype").val();
            $("#storage-allocateOut-boxprice").addClass("inputload");
            $.ajax({
                url :'sales/getAmountChangerByBoxprice.do',
                type:'post',
                data:{unitnum:unitnum,id:goodsid,boxprice:boxprice,taxtype:taxtype},
                dataType:'json',
                async:false,
                success:function(json){
                    $("#storage-allocateOut-taxamount").val(json.taxAmount);
                    $("#storage-allocateOut-notaxamount").val(json.noTaxAmount);
                    $("#storage-allocateOut-tax").val(json.tax);

                    $("#storage-allocateOut-taxprice").val(json.taxPrice);
                    $("#storage-allocateOut-boxprice").val(json.boxPrice);
                    $("#storage-allocateOut-notaxprice").val(json.noTaxPrice);

                    $("#storage-allocateOut-boxprice").removeClass("inputload");
                }
            });
        }
        function amountChange(type, id){ //1含税金额或2未税金额改变计算对应数据
            var goodsid= $("#storage-allocateOut-goodsid").goodsWidget("getValue");
            if(null==goodsid){
                return false;
            }
            var auxunitid = $("#storage-allocateOut-auxunitid").val();
            var unitnum = $("#storage-allocateOut-unitnum").val();
            var amount = $("#storage-allocateOut-taxamount").val();
            var taxtype = $("#storage-allocateOut-taxtype").val();
            $("#storage-allocateOut-taxamount").addClass("inputload");
            $.ajax({
                url :'sales/getAmountChangeByType.do',
                type:'post',
                data:{type:'1',amount:amount,taxtype:taxtype,unitnum:unitnum,id:goodsid},
                dataType:'json',
                async:false,
                success:function(json){
                    $("#storage-allocateOut-taxamount").val(json.taxAmount);
                    $("#storage-allocateOut-notaxamount").val(json.noTaxAmount);
                    $("#storage-allocateOut-tax").val(json.tax);

                    $("#storage-allocateOut-taxprice").val(json.taxPrice);
                    $("#storage-allocateOut-boxprice").val(json.boxPrice);
                    $("#storage-allocateOut-notaxprice").val(json.noTaxPrice);

                    $("#storage-allocateOut-taxamount").removeClass("inputload");
                }
            });

        }
		//默认禁用所属库位
		$("#storage-allocateOut-arrivedate").val($("#storage-allocateOut-businessdate").val());
		
		$("#storage-allocateOut-enterstoragelocationid").widget("disable");
   </script>
  </body>
</html>
