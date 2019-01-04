<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>调拨通知单明细添加</title>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
	   	<form action="" method="post" id="storage-form-allocateNoticeDetailAddPage">
	   		<%--<input type="hidden" id="storage-allocateNotice-taxprice" name="taxprice">--%>
	   		<%--<input type="hidden" id="storage-allocateNotice-taxamount" name="taxamount">--%>
	   		<input type="hidden" id="storage-allocateNotice-notaxprice" name="notaxprice">
	   		<input type="hidden" id="storage-allocateNotice-notaxamount" name="notaxamount">
	   		<input type="hidden" id="storage-allocateNotice-tax" name="tax">
	   		<input type="hidden" id="storage-allocateNotice-taxtypename" name="taxtypename"/>
	   		<input type="hidden" id="storage-allocateNotice-taxtype" name="taxtype"/>
            <input type="hidden" id="storage-allocateNotice-usablenum" name="usablenum"/>
	   		<table  border="0" class="box_table">
	   			<tr>
	   				<td width="120">选择商品:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-allocateNotice-goodsid" name="goodsid" width="180"/>
	   				</td>
                    <td colspan="2" id="storage-allocateNotice-loadInfo" style="text-align: left;">&nbsp;</td>
	   			</tr>
	   			<tr>
	   				<td>数量:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-allocateNotice-unitnum" name="unitnum" class="easyui-validatebox" data-options="validType:'intOrFloatNum[${decimallen}]'"/>
	   				</td>
	   				<td>辅数量:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-allocateNotice-unitnum-aux" name="auxnum" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'integer'"/>
	   					<span id="storage-allocateNotice-auxunitname1" style="float: left;"></span>
	   					<input type="text" id="storage-allocateNotice-unitnum-unit" name="auxremainder" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
	   					<span id="storage-allocateNotice-goodsunitname1" style="float: left;"></span>
	   					<input type="hidden" id="storage-allocateNotice-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td>单位:</td>
	   				<td style="text-align: left;">
                        主:
	   					<input type="text" style="width: 40px;float: none;" id="storage-allocateNotice-goodsunitname" name="unitname" class="readonly2" readonly="readonly"/>
	   					<input type="hidden" id="storage-allocateNotice-goodsunitid" name="unitid"/>
                        辅:
                        <input type="text" style="width: 40px;float: none;" id="storage-allocateNotice-auxunitname" name="auxunitname" class="readonly2" readonly="readonly"/>
                        <input type="hidden" id="storage-allocateNotice-auxunitid" name="auxunitid"/>
	   				</td>
                    <td width="120">规格型号:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-allocateNotice-goodsmodel" class="no_input" readonly="readonly"/>
                    </td>
	   			</tr>
                <tr>
                    <td width="120">调拨单价:</td>
                    <td>
                        <input type="text" id="storage-allocateNotice-taxprice" class="easyui-validatebox" required="required" validType="intOrFloat" name="taxprice" />
                    </td>

                    <td width="120">调拨金额:</td>
                    <td>
                        <input type="text" id="storage-allocateNotice-taxamount" class="easyui-validatebox" required="required" validType="intOrFloat"  name="taxamount" />
                    </td>
                </tr>
                <tr>
                    <td width="120">调拨箱价:</td>
                    <td>
                        <input type="text" id="storage-allocateNotice-boxprice" class="easyui-validatebox" required="required" validType="intOrFloat" name="boxprice"/>
                    </td>
                    <td width="120">箱装量:</td>
                    <td>
                        <input type="text" id="storage-allocateNotice-boxnum" class="no_input" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td width="120">商品品牌:</td>
                    <td>
                        <input type="text" id="storage-allocateNotice-goodsbrandName" class="no_input" readonly="readonly"/>
                    </td>

                    <td width="120">条形码:</td>
                    <td>
                        <input type="text" id="storage-allocateNotice-barcode" class="no_input" readonly="readonly"/>
                    </td>
                </tr>
	   			<tr>
                    <td>出库批次号:</td>
                    <td>
                        <input type="text" id="storage-allocateNotice-batchno" name="batchno"/>
                    </td>
	   				<td>出库库位:</td>
	   				<td style="text-align: left;">
	   					<input type="text" id="storage-allocateNotice-storagelocationname" name="storagelocationname" class="no_input" readonly="readonly"/>
	   					<input type="hidden" id="storage-allocateNotice-storagelocationid"  name="storagelocationid"/>
	   					<input type="hidden" id="storage-allocateNotice-detail-storageid"  name="storageid"/>
	   					<input type="hidden" id="storage-allocateNotice-detail-summarybatchid"  name="summarybatchid"/>
	   				</td>
	   			</tr>
                <tr>
                    <td>出库生产日期:</td>
                    <td>
                        <input type="text" id="storage-allocateNotice-produceddate" style="height: 20px;" name="produceddate" class="WdateRead" readonly="readonly"/>
                    </td>
                    <td>出库截止日期:</td>
                    <td>
                        <input type="text" id="storage-allocateNotice-deadline" style="height: 20px;" name="deadline" class="WdateRead" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td>入库生产日期:</td>
                    <td>
                        <input type="text" id="storage-allocateNotice-enterproduceddate" style="height: 20px;" name="enterproduceddate" class="WdateRead" readonly="readonly"/>
                    </td>
                    <td>入库截止日期:</td>
                    <td>
                        <input type="text" id="storage-allocateNotice-enterdeadline" style="height: 20px;" name="enterdeadline" class="WdateRead" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td>入库批次号:</td>
                    <td>
                        <input type="text" id="storage-allocateNotice-enterbatchno" name="enterbatchno" class="no_input"  readonly="readonly"/>
                    </td>
                    <td>入库库位:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-allocateNotice-enterstoragelocationid" name="enterstoragelocationid"/>
                        <input type="hidden" id="storage-allocateNotice-enterstoragelocationname" name="enterstoragelocationname"/>
                    </td>
                </tr>
	   			<tr>
	   				<td>备注:</td>
	   				<td colspan="3" style="text-align: left;">
	   					<input id="storage-allocateNoticeDetail-remark" type="text" name="remark" style="width: 490px;" maxlength="200"/>
	   				</td>
	   			</tr>
	   		</table>
				<input type="hidden" id="storage-allocateNotice-costprice" name="costprice"/>
	    </form>
	    </div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="text-align: right;">
  				快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
	  			<input type="button" value="继续添加" name="savegoon" id="storage-savegoon-allocateNoticeDetailAddPage" />
  			</div>
  		</div>
  </div>
   <script type="text/javascript">
	   var storageid = $("#storage-allocateNotice-outstorageid").widget("getValue");
       var outStorage = $("#storage-allocateNotice-outstorageid").widget("getObject");
       var enterStorage = $("#storage-allocateNotice-enterstorageid").widget("getObject");
		$(function(){
			$("#storage-allocateNotice-goodsid").goodsWidget({
	    		width:130,
				singleSelect:true,
				required:true,
                storageid:storageid,
				onSelect:function(data){
					$("#storage-allocateNotice-goodsbrandName").val(data.brandName);
					$("#storage-allocateNotice-goodsmodel").val(data.model);
					$("#storage-allocateNotice-goodsunitname").val(data.mainunitName);
					$("#storage-allocateNotice-goodsunitid").val(data.mainunit);
					$("#storage-allocateNotice-auxunitname1").html(data.auxunitname);
					$("#storage-allocateNotice-goodsunitname1").html(data.mainunitName);
					
					$("#storage-allocateNotice-auxunitid").val(data.auxunitid);
					$("#storage-allocateNotice-taxtype").val(data.taxtype);
					$("#storage-allocateNotice-taxprice").val(data.newstorageprice);
					$("#storage-allocateNotice-barcode").val(data.barcode);
					$("#storage-allocateNotice-boxnum").val(data.boxnum);
                    $.ajax({
                        url :'storage/getStorageSummaryCostprice.do',
                        type:'post',
                        data:{goodsid:data.id,storageid:storageid},
                        dataType:'json',
                        async:false,
                        success:function(json){
                            $("#storage-allocateNotice-costprice").val(json.costprice);
                        }
                    });
					var billtype=$("#storage-allocateNotice-billtype").val();
					if(billtype=='1'){
						$("#storage-allocateNotice-taxprice").val($("#storage-allocateNotice-costprice").val());
						$("#storage-allocateNotice-taxprice").attr('readonly','readonly');
						$("#storage-allocateNotice-boxprice").attr('readonly','readonly');
						$("#storage-allocateNotice-taxamount").attr('readonly','readonly');
					}
					$("#storage-allocateNotice-unitnum").val(0);
					if(data.isbatch=='1'){
						$("#storage-allocateNotice-batchno").widget("enable");
						$("#storage-allocateNotice-enterstoragelocationid").widget("enable");
    					var param = null;
                    	if(storageid!=null && storageid!=""){
                    		param = [{field:'goodsid',op:'equal',value:data.id},
                    		       {field:'storageid',op:'equal',value:storageid}];
                    	}else{
                    		param = [{field:'goodsid',op:'equal',value:data.id}];
                    	}
                        $("#storage-allocateNotice-enterproduceddate").removeClass("WdateRead");
                        $("#storage-allocateNotice-enterproduceddate").addClass("Wdate");
                        $("#storage-allocateNotice-enterproduceddate").removeAttr("readonly");
                        if(enterStorage.isbatch=="1"){
                            $("#storage-allocateNotice-enterproduceddate").validatebox({required:true});
                        }else{
                            $("#storage-allocateNotice-enterproduceddate").validatebox({required:false});
                        }
                        $("#storage-allocateNotice-enterdeadline").removeClass("WdateRead");
                        $("#storage-allocateNotice-enterdeadline").addClass("Wdate");
                        $("#storage-allocateNotice-enterdeadline").removeAttr("readonly");
                        //批次是否必填
                        var reFlag = false;
                        if(outStorage.isbatch=="1"){
                            reFlag = true;
                        }else{
                            <c:if test="${fieldMap.existingnum!=null || fieldMap.usablenum!=null}">
                            $("#storage-allocateNotice-loadInfo").html("现存量：<font color='green'>"+data.highestinventory+data.mainunitName+"</font>&nbsp;可用量：<font color='green'>"+ data.newinventory +data.mainunitName+"</font>");
                            $("#storage-allocateNotice-usablenum").val(data.newinventory);
                            </c:if>
                        }
                    	$("#storage-allocateNotice-batchno").widget({
                    		referwid:'RL_T_STORAGE_BATCH_LIST',
                    		param:param,
                			width:165,
                			required:reFlag,
            				singleSelect:true,
            				onSelect: function(obj){
            					$("#storage-allocateNotice-detail-summarybatchid").val(obj.id);
            					$("#storage-allocateNotice-storagelocationname").val(obj.storagelocationname);
            					$("#storage-allocateNotice-storagelocationid").val(obj.storagelocationid);
            					$("#storage-allocateNotice-produceddate").val(obj.produceddate);
            					$("#storage-allocateNotice-deadline").val(obj.deadline);
                                $("#storage-allocateNotice-enterproduceddate").val(obj.produceddate);
                                $("#storage-allocateNotice-enterdeadline").val(obj.deadline);
                                $("#storage-allocateNotice-enterbatchno").val(obj.batchno);

            					<c:if test="${fieldMap.existingnum!=null || fieldMap.usablenum!=null}">
        						$("#storage-allocateNotice-loadInfo").html("现存量：<font color='green'>"+obj.existingnum+obj.unitname+"</font>&nbsp;可用量：<font color='green'>"+ obj.usablenum +obj.unitname+"</font>");
        						$("#storage-allocateNotice-usablenum").val(obj.usablenum);
        	                    </c:if>

            				},
            				onClear:function(){
            					$("#storage-allocateNotice-detail-summarybatchid").val("");
            					$("#storage-allocateNotice-storagelocationname").val("");
            					$("#storage-allocateNotice-storagelocationid").val("");
            					$("#storage-allocateNotice-produceddate").val("");
            					$("#storage-allocateNotice-deadline").val("");
            					
        						$("#storage-allocateNotice-loadInfo").html("&nbsp;");
        						$("#storage-allocateNotice-usablenum").val(0);
            				}
                    	});
						computNum();
                    	$("#storage-allocateOut-batchno").focus();
					}else{
                        $("#storage-allocateNotice-batchno").widget("clear");
                        $("#storage-allocateNotice-batchno").widget("disable");
                        $("#storage-allocateNotice-batchno").widget("required",false);

						$("#storage-allocateNotice-produceddate").removeClass("Wdate");
						$("#storage-allocateNotice-produceddate").addClass("WdateRead");
						$("#storage-allocateNotice-produceddate").attr("readonly","readonly");

                        $("#storage-allocateNotice-enterproduceddate").removeClass("Wdate");
                        $("#storage-allocateNotice-enterproduceddate").addClass("WdateRead");
                        $("#storage-allocateNotice-enterproduceddate").attr("readonly","readonly");
                        $("#storage-allocateNotice-enterproduceddate").validatebox({required:false});
                        $("#storage-allocateNotice-enterdeadline").removeClass("Wdate");
                        $("#storage-allocateNotice-enterdeadline").addClass("WdateRead");
                        $("#storage-allocateNotice-enterdeadline").attr("readonly","readonly");

						<c:if test="${fieldMap.existingnum!=null || fieldMap.usablenum!=null}">
						$("#storage-allocateNotice-loadInfo").html("现存量：<font color='green'>"+data.highestinventory+data.mainunitName+"</font>&nbsp;可用量：<font color='green'>"+ data.newinventory +data.mainunitName+"</font>");
						$("#storage-allocateNotice-usablenum").val(data.newinventory);
	                    </c:if>
	                    computNum();
					}
                    $("#storage-allocateNotice-unitnum").focus();
                    $("#storage-allocateNotice-unitnum").select();
					
				},
				onClear :function(){
                    $("#storage-allocateNotice-batchno").widget("clear");
                    $("#storage-allocateNotice-batchno").widget("disable");
                    $("#storage-allocateNotice-batchno").widget("required",false);

					$("#storage-allocateNotice-loadInfo").html("&nbsp;");
					$("#storage-allocateNotice-enterstoragelocationid").widget("disable");
					$("#storage-allocateNotice-produceddate").removeClass("Wdate");
					$("#storage-allocateNotice-produceddate").addClass("WdateRead");
					$("#storage-allocateNotice-produceddate").attr("readonly","readonly");
                    $("#storage-allocateNotice-enterproduceddate").removeClass("Wdate");
                    $("#storage-allocateNotice-enterproduceddate").addClass("WdateRead");
                    $("#storage-allocateNotice-enterproduceddate").attr("readonly","readonly");
                    $("#storage-allocateNotice-enterproduceddate").validatebox({required:false});
                    $("#storage-allocateNotice-enterdeadline").removeClass("Wdate");
                    $("#storage-allocateNotice-enterdeadline").addClass("WdateRead");
                    $("#storage-allocateNotice-enterdeadline").attr("readonly","readonly");
				}
			});
            $("#storage-allocateNotice-enterproduceddate").click(function(){
                if($("#storage-allocateNotice-enterproduceddate").hasClass("Wdate")){
                    WdatePicker({dateFmt:'yyyy-MM-dd',
                        onpicked:function(dp){
                            if(dp.el.id=="storage-allocateNotice-enterproduceddate"){
                                var produceddate = dp.cal.getDateStr();
                                var goodsid = $("#storage-allocateNotice-goodsid").goodsWidget("getValue");
                                $.ajax({
                                    url :'storage/getBatchno.do',
                                    type:'post',
                                    data:{produceddate:produceddate,goodsid:goodsid},
                                    dataType:'json',
                                    async:false,
                                    success:function(json){
                                        $('#storage-allocateNotice-enterbatchno').val(json.batchno);
                                        $('#storage-allocateNotice-enterdeadline').val(json.deadline);
                                    }
                                });
                            }
                        }
                    });
                }
            });
            $("#storage-allocateNotice-enterdeadline").click(function(){
                if($("#storage-allocateNotice-enterdeadline").hasClass("Wdate")){
                    WdatePicker({dateFmt:'yyyy-MM-dd',
                        onpicked:function(dp){
                            if(dp.el.id=="storage-allocateNotice-enterdeadline"){
                                var deadline = dp.cal.getDateStr();
                                var goodsid = $("#storage-allocateNotice-goodsid").goodsWidget("getValue");
                                $.ajax({
                                    url :'storage/getBatchnoByDeadline.do',
                                    type:'post',
                                    data:{deadline:deadline,goodsid:goodsid},
                                    dataType:'json',
                                    async:false,
                                    success:function(json){
                                        $('#storage-allocateNotice-enterbatchno').val(json.batchno);
                                        $('#storage-allocateNotice-enterproduceddate').val(json.produceddate);
                                    }
                                });
                            }
                        }
                    });
                }
            });
			$("#storage-allocateNotice-batchno").widget({
        		referwid:'RL_T_STORAGE_BATCH_LIST',
    			width:165,
    			disabled:true,
				singleSelect:true
			});
			$("#storage-allocateNotice-enterstoragelocationid").widget({
				name:'t_storage_allocate_notice_detail',
	    		width:165,
				col:'enterstoragelocationid',
				singleSelect:true,
				disabled:true,
				onSelect:function(data){
					$("#storage-allocateNotice-enterstoragelocationname").val(data.name);
				},
				onClear:function(){
					$("#storage-allocateNotice-enterstoragelocationname").val("");
				}
			});
			$("#storage-allocateNotice-unitnum").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				computNum();
			});
			$("#storage-allocateNotice-unitnum-aux").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				computNumByAux();
			});
			$("#storage-allocateNotice-unitnum-unit").change(function(){
				if($(this).val()==""){
					$(this).val("0");
					$(this).validatebox("validate");
				}
				computNumByAux();
			});
            $("#storage-allocateNotice-taxprice").change(function(){
                if($(this).val()==""){
                    $(this).val("0");
                    $(this).validatebox("validate");
                }
                priceChange();
            });
            $("#storage-allocateNotice-boxprice").change(function(){
                if($(this).val()==""){
                    $(this).val("0");
                    $(this).validatebox("validate");
                }
                computBoxprice();
            });
            $("#storage-allocateNotice-taxamount").change(function(){
                if($(this).val()==""){
                    $(this).val("0");
                    $(this).validatebox("validate");
                }
                amountChange();
            });
			$("#storage-allocateNotice-produceddate").click(function(){
				if(!$("#storage-allocateNotice-batchno").hasClass("no_input")){
					WdatePicker({dateFmt:'yyyy-MM-dd'});
				}
			});
			$("#storage-allocateNotice-unitnum").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#storage-allocateNotice-unitnum").blur();
		   			$("#storage-allocateNotice-unitnum-aux").focus();
		   			$("#storage-allocateNotice-unitnum-aux").select();
				}
			});
			$("#storage-allocateNotice-unitnum-aux").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-allocateNotice-unitnum-auxm").blur();
		   			$("#storage-allocateNotice-unitnum-unit").focus();
		   			$("#storage-allocateNotice-unitnum-unit").select();
				}
			});
			$("#storage-allocateNotice-unitnum-unit").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-allocateNotice-unitnum-auxm").blur();
		   			$("#storage-allocateNoticeDetail-remark").focus();
		   			$("#storage-allocateNoticeDetail-remark").select();
				}
			});
			$("#storage-allocateNoticeDetail-remark").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-allocateNotice-taxprice").blur();
		   			$("#storage-savegoon-allocateNoticeDetailAddPage").focus();
				}
			});
			$("#storage-savegoon-allocateNoticeDetailAddPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					addSaveDetail(true);
				}
			});
		    $("#storage-savegoon-allocateNoticeDetailAddPage").click(function(){
		    	addSaveDetail(true);
		    });
		});
		//通过总数量 计算数量 金额换算
		function computNum(){
			var goodsid= $("#storage-allocateNotice-goodsid").goodsWidget("getValue");
			if(null==goodsid){
				return false;
			}
			var auxunitid = $("#storage-allocateNotice-auxunitid").val();
			var unitnum = $("#storage-allocateNotice-unitnum").val();
			var taxprice = $("#storage-allocateNotice-taxprice").val();
			var notaxprice = $("#storage-allocateNotice-notaxprice").val();
			var taxtype = $("#storage-allocateNotice-taxtype").val();
			$("#storage-allocateNotice-unitnum").addClass("inputload");
			$.ajax({   
	            url :'basefiles/computeGoodsByUnitnum.do',
	            type:'post',
	            data:{unitnum:unitnum,goodsid:goodsid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#storage-allocateNotice-taxamount").val(json.taxamount);
	            	$("#storage-allocateNotice-notaxamount").val(json.notaxamount);
	            	$("#storage-allocateNotice-tax").val(json.tax);
	            	$("#storage-allocateNotice-taxtypename").val(json.taxtypename);
	            	$("#storage-allocateNotice-auxunitnumdetail").val(json.auxnumdetail);
	            	$("#storage-allocateNotice-auxunitnum").val(json.auxnum);
	            	$("#storage-allocateNotice-auxunitname").val(json.auxunitname);
	            	$("#storage-allocateNotice-auxunitname1").html(json.auxunitname);
	            	$("#storage-allocateNotice-goodsunitname").val(json.unitname);
	            	$("#storage-allocateNotice-goodsunitname1").html(json.unitname);
	            	
	            	$("#storage-allocateNotice-taxprice").val(json.taxprice);
                    $("#storage-allocateNotice-boxprice").val(json.boxprice);
	            	$("#storage-allocateNotice-notaxprice").val(json.notaxprice);

	            	$("#storage-allocateNotice-unitnum-aux").val(json.auxInteger);
	            	$("#storage-allocateNotice-unitnum-unit").val(json.auxremainder);
	            	if(json.auxrate!=null){
	            		$("#storage-allocateNotice-unitnum-unit").attr("max",json.auxrate-1);
	            	}
	            	$("#storage-allocateNotice-unitnum").removeClass("inputload");
	            }
	        });
		}
		//通过辅单位数量
		function computNumByAux(){
			var goodsid= $("#storage-allocateNotice-goodsid").goodsWidget("getValue");
			if(null==goodsid){
				return false;
			}
			var auxunitid = $("#storage-allocateNotice-auxunitid").val();
			var taxprice = $("#storage-allocateNotice-taxprice").val();
			var notaxprice = $("#storage-allocateNotice-notaxprice").val();
			var taxtype = $("#storage-allocateNotice-taxtype").val();
			var auxInterger = $("#storage-allocateNotice-unitnum-aux").val();
			var auxremainder = $("#storage-allocateNotice-unitnum-unit").val();
			$("#storage-allocateNotice-unitnum").addClass("inputload");
			$.ajax({   
	            url :'basefiles/computeGoodsByAuxnum.do',
	            type:'post',
	            data:{auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#storage-allocateNotice-taxamount").val(json.taxamount);
	            	$("#storage-allocateNotice-notaxamount").val(json.notaxamount);
	            	$("#storage-allocateNotice-tax").val(json.tax);
	            	$("#storage-allocateNotice-taxtypename").val(json.taxtypename);
	            	
	            	$("#storage-allocateNotice-taxprice").val(json.taxprice);
	            	$("#storage-allocateNotice-notaxprice").val(json.notaxprice);

	            	$("#storage-allocateNotice-unitnum").val(json.mainnum);
                    $("#storage-allocateNotice-unitnum-aux").val(json.auxInterger);
                    $("#storage-allocateNotice-unitnum-unit").val(json.auxremainder);
                    $("#storage-allocateNotice-auxunitnumdetail").val(json.auxnumdetail);
	            	
	            	$("#storage-allocateNotice-unitnum").removeClass("inputload");
	            }
	        });
		}
       function priceChange(){

           var goodsid= $("#storage-allocateNotice-goodsid").goodsWidget("getValue");
           if(null==goodsid){
               return false;
           }
           var auxunitid = $("#storage-allocateNotice-auxunitid").val();
           var unitnum = $("#storage-allocateNotice-unitnum").val();
           var taxprice = $("#storage-allocateNotice-taxprice").val();
           var taxtype = $("#storage-allocateNotice-taxtype").val();
           $("#storage-allocateNotice-taxprice").addClass("inputload");
           $.ajax({
               url :'sales/getAmountChanger.do',
               type:'post',
               data:{type:'1',price:taxprice,taxtype:taxtype,unitnum:unitnum,id:goodsid},
               dataType:'json',
               async:false,
               success:function(json){
                   $("#storage-allocateNotice-taxamount").val(json.taxAmount);
                   $("#storage-allocateNotice-notaxamount").val(json.noTaxAmount);
                   $("#storage-allocateNotice-tax").val(json.tax);

                   $("#storage-allocateNotice-taxprice").val(json.taxPrice);
                   $("#storage-allocateNotice-boxprice").val(json.boxPrice);
                   $("#storage-allocateNotice-notaxprice").val(json.noTaxPrice);

                   $("#storage-allocateNotice-taxprice").removeClass("inputload");
               }
           });
       }
       //通过总数量 计算数量 金额换算
       function computBoxprice(){
           var goodsid= $("#storage-allocateNotice-goodsid").goodsWidget("getValue");
           if(null==goodsid){
               return false;
           }
           var auxunitid = $("#storage-allocateNotice-auxunitid").val();
           var unitnum = $("#storage-allocateNotice-unitnum").val();
           var boxprice = $("#storage-allocateNotice-boxprice").val();
           var notaxprice = $("#storage-allocateNotice-notaxprice").val();
           var taxtype = $("#storage-allocateNotice-taxtype").val();
           $("#storage-allocateNotice-boxprice").addClass("inputload");
           $.ajax({
               url :'sales/getAmountChangerByBoxprice.do',
               type:'post',
               data:{unitnum:unitnum,id:goodsid,boxprice:boxprice,taxtype:taxtype},
               dataType:'json',
               async:false,
               success:function(json){
                   $("#storage-allocateNotice-taxamount").val(json.taxAmount);
                   $("#storage-allocateNotice-notaxamount").val(json.noTaxAmount);
                   $("#storage-allocateNotice-tax").val(json.tax);

                   $("#storage-allocateNotice-taxprice").val(json.taxPrice);
                   $("#storage-allocateNotice-boxprice").val(json.boxPrice);
                   $("#storage-allocateNotice-notaxprice").val(json.noTaxPrice);

                   $("#storage-allocateNotice-boxprice").removeClass("inputload");
               }
           });
       }
       function amountChange(type, id){ //1含税金额或2未税金额改变计算对应数据
           var goodsid= $("#storage-allocateNotice-goodsid").goodsWidget("getValue");
           if(null==goodsid){
               return false;
           }
           var auxunitid = $("#storage-allocateNotice-auxunitid").val();
           var unitnum = $("#storage-allocateNotice-unitnum").val();
           var amount = $("#storage-allocateNotice-taxamount").val();
           var taxtype = $("#storage-allocateNotice-taxtype").val();
           $("#storage-allocateNotice-taxamount").addClass("inputload");
           $.ajax({
               url :'sales/getAmountChangeByType.do',
               type:'post',
               data:{type:'1',amount:amount,taxtype:taxtype,unitnum:unitnum,id:goodsid},
               dataType:'json',
               async:false,
               success:function(json){
                   $("#storage-allocateNotice-taxamount").val(json.taxAmount);
                   $("#storage-allocateNotice-notaxamount").val(json.noTaxAmount);
                   $("#storage-allocateNotice-tax").val(json.tax);

                   $("#storage-allocateNotice-taxprice").val(json.taxPrice);
                   $("#storage-allocateNotice-boxprice").val(json.boxPrice);
                   $("#storage-allocateNotice-notaxprice").val(json.noTaxPrice);

                   $("#storage-allocateNotice-taxamount").removeClass("inputload");
               }
           });

       }
		//默认禁用所属库位
		$("#storage-allocateNotice-arrivedate").val($("#storage-allocateNotice-businessdate").val());
		
		$("#storage-allocateNotice-enterstoragelocationid").widget("disable");
   </script>
  </body>
</html>
