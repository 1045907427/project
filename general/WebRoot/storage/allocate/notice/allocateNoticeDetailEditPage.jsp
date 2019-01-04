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
   		<input type="hidden" id="storage-allocateNotice-notaxprice" name="notaxprice">
   		<input type="hidden" id="storage-allocateNotice-notaxamount" name="notaxamount">
   		<input type="hidden" id="storage-allocateNotice-tax" name="tax">
   		<input type="hidden" id="storage-allocateNotice-taxtypename" name="taxtypename"/>
   		<input type="hidden" id="storage-allocateNotice-taxtype" name="taxtype"/>
        <input type="hidden" id="storage-allocateNotice-usablenum" name="usablenum"/>
        <input type="hidden" id="storage-allocateNotice-isbatch" value="${isbatch}"/>
        
   		<table  border="0" class="box_table">
   			<tr>
   				<td width="120">商品:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-allocateNotice-goodsname" width="180" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-allocateNotice-goodsid" name="goodsid" width="170"/>
   				</td>
                <td colspan="2" id="storage-allocateNotice-loadInfo" style="text-align: left;">&nbsp;</td>
   			</tr>
   			<tr>
   				<td>数量:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-allocateNotice-unitnum" name="unitnum" class="formaterNum easyui-validatebox" data-options="validType:'intOrFloatNum[${decimallen}]'"/>
   				</td>
   				<td>辅数量:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-allocateNotice-unitnum-aux" name="auxnum" style="width:60px;" class="formaterNum easyui-validatebox" data-options="required:true,validType:'integer'"/>
   					<span id="storage-allocateNotice-auxunitname1" style="float: left;"></span>
   					<input type="text" id="storage-allocateNotice-unitnum-unit" name="auxremainder" style="width:60px;" class="formaterNum easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
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
                <td style="text-align: left;">
                    <input type="text" id="storage-allocateNotice-batchno" name="batchno"/>
                </td>
   				<td>出库库位:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-allocateNotice-storagelocationname" name="storagelocationname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-allocateNotice-storagelocationid"  name="storagelocationid"/>
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
			<input type="button" value="确 定" name="savegoon" id="storage-savegoon-allocateNoticeDetailEditPage" />
		</div>
	</div>
  </div>
   <script type="text/javascript">
   		//加载数据
		var object = $("#storage-datagrid-allocateNoticeAddPage").datagrid("getSelected");
		$("#storage-form-allocateNoticeDetailAddPage").form("load",object);
		$("#storage-allocateNotice-goodsname").val(object.goodsInfo.name);
		$("#storage-allocateNotice-goodsbrandName").val(object.goodsInfo.brandName);
		$("#storage-allocateNotice-goodsmodel").val(object.goodsInfo.model);
		$("#storage-allocateNotice-auxunitname1").html(object.auxunitname);
		$("#storage-allocateNotice-goodsunitname1").html(object.unitname);
		$("#storage-allocateNotice-barcode").val(object.goodsInfo.barcode);
		$("#storage-allocateNotice-boxnum").val(object.goodsInfo.boxnum);
		var billtype=$("#storage-allocateNotice-billtype").val();
		if(billtype=='1'){
			$("#storage-allocateNotice-taxprice").attr('readonly','readonly');
			$("#storage-allocateNotice-boxprice").attr('readonly','readonly');
			$("#storage-allocateNotice-taxamount").attr('readonly','readonly');
		}
		$(function(){
            $("#storage-allocateNotice-enterproduceddate").click(function(){
                if($("#storage-allocateNotice-enterproduceddate").hasClass("Wdate")){
                    WdatePicker({dateFmt:'yyyy-MM-dd',
                        onpicked:function(dp){
                            if(dp.el.id=="storage-allocateNotice-enterproduceddate"){
                                var produceddate = dp.cal.getDateStr();
                                var goodsid = $("#storage-allocateNotice-goodsid").val();
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
                                var goodsid = $("#storage-allocateNotice-goodsid").val();
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
		   			$("#storage-savegoon-allocateNoticeDetailEditPage").focus();
				}
			});
			$("#storage-savegoon-allocateNoticeDetailEditPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					editSaveDetail(true);
				}
			});
		    $("#storage-savegoon-allocateNoticeDetailEditPage").click(function(){
		    	editSaveDetail(true);
		    });
		});
		//通过总数量 计算数量 金额换算
		function computNum(){
			var goodsid= $("#storage-allocateNotice-goodsid").val();
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
			var goodsid= $("#storage-allocateNotice-goodsid").val();
			if(null==goodsid){
				return false;
			}
			var auxunitid = $("#storage-allocateNotice-auxunitid").val();
			var taxprice = $("#storage-allocateNotice-taxprice").val();
			var notaxprice = $("#storage-allocateNotice-notaxprice").val();
			var taxtype = $("#storage-allocateNotice-taxtype").val();
			var auxInterger = $("#storage-allocateNotice-unitnum-aux").val();
			var auxremainder = $("#storage-allocateNotice-unitnum-unit").val();
			var auxmax = $("#storage-allocateNotice-unitnum-unit").attr("max");
			if(Number(auxremainder)>Number(auxmax)){
				auxremainder = auxmax;
				$("#storage-allocateNotice-unitnum-unit").val(auxremainder);
			}
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
		
		function goodsNumControl(){
			var isbatch = $("#storage-allocateNotice-isbatch").val();
			if(isbatch=="1"){
				//控件其他出库的最大数量
				$.ajax({   
		            url :'storage/getStorageSummaryBatchInfo.do',
		            type:'post',
		            data:{summarybatchid:object.summarybatchid},
		            dataType:'json',
		            success:function(json){
		            	if(json.storageSummaryBatch!=null){
		            		var unitname = $("#storage-allocateNotice-goodsunitname").val();
		            		var status = $("#storage-allocateNotice-status").val();
		            		var usablenum = json.storageSummaryBatch.usablenum;
		            		var maxnum = Number(object.unitnum)+Number(json.storageSummaryBatch.usablenum);
		            		<c:if test="${fieldMap.existingnum!=null || fieldMap.usablenum!=null}">
		            			$("#storage-allocateNotice-loadInfo").html("现存量：<font color='green'>"+json.storageSummaryBatch.existingnum+unitname+"</font>&nbsp;可用量：<font color='green'>"+ json.storageSummaryBatch.usablenum +unitname+"</font>");
		                        $("#storage-allocateNotice-usablenum").val(json.storageSummaryBatch.usablenum);
		                    </c:if>
							if(json.storageSummaryBatch.storagelocationid!=null && json.storageSummaryBatch.storagelocationid!=""){
								$("#storage-allocateNotice-enterstoragelocationid").widget("enable");
							}
		            	}
		            }
		        });
			}else{
				var storageid = $("#storage-allocateNotice-outstorageid").widget("getValue");
				//控件其他出库的最大数量
				$.ajax({   
		            url :'storage/getStorageSummarySumByGoodsid.do',
		            type:'post',
		            data:{goodsid:object.goodsid,storageid:storageid},
		            dataType:'json',
		            success:function(json){
		            	if(json.storageSummary!=null){
		            		var unitname = $("#storage-allocateNotice-goodsunitname").val();
		            		var status = $("#storage-allocateNotice-status").val();
		            		var usablenum = json.storageSummary.usablenum;
		            		var maxnum = Number(object.unitnum)+Number(json.storageSummary.usablenum);
		            		<c:if test="${fieldMap.existingnum!=null || fieldMap.usablenum!=null}">
		            			$("#storage-allocateNotice-loadInfo").html("现存量：<font color='green'>"+json.storageSummary.existingnum+unitname+"</font>&nbsp;可用量：<font color='green'>"+ json.storageSummary.usablenum +unitname+"</font>");
		                        $("#storage-allocateNotice-usablenum").val(json.storageSummary.usablenum);
		                    </c:if>
		            	}
		            }
		        });
			}
		}
        function priceChange(){
            var goodsid= $("#storage-allocateNotice-goodsid").val();
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
            var goodsid= $("#storage-allocateNotice-goodsid").val();
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
            var goodsid= $("#storage-allocateNotice-goodsid").val();
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
		goodsNumControl();
		
   </script>
  </body>
</html>
