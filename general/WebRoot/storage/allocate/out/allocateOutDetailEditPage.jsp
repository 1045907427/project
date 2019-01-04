<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>调拨单明细修改</title>
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
        <input type="hidden" id="storage-allocateOut-isbatch" value="${isbatch}"/>
        
   		<table  border="0" class="box_table">
   			<tr>
   				<td width="120">商品:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-allocateOut-goodsname" width="180" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-allocateOut-goodsid" name="goodsid" width="170"/>
   				</td>
                <td id="storage-allocateOut-loadInfo" colspan="2" style="text-align: left;"></td>
   			</tr>
   			<tr>
   				<td>数量:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-allocateOut-unitnum" name="unitnum" class="formaterNum easyui-validatebox" data-options="validType:'intOrFloatNum[${decimallen}]'"/>
   				</td>
   				<td>辅数量:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-allocateOut-unitnum-aux" name="auxnum" style="width:60px;" class="formaterNum easyui-validatebox" data-options="required:true,validType:'integer'"/>
   					<span id="storage-allocateOut-auxunitname1" style="float: left;"></span>
   					<input type="text" id="storage-allocateOut-unitnum-unit" name="auxremainder" style="width:60px;" class="formaterNum easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
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
                <td width="120" >调拨单价:</td>
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
                <td style="text-align: left;">
                    <input type="text" id="storage-allocateOut-batchno" name="batchno"/>
                </td>
   				<td>出库库位:</td>
   				<td style="text-align: left;">
   					<input type="text" id="storage-allocateOut-storagelocationname" name="storagelocationname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="storage-allocateOut-storagelocationid"  name="storagelocationid"/>
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
                <td>入库生产日期:</td>
                <td>
                    <input type="text" id="storage-allocateOut-enterproduceddate" style="height: 20px;" name="enterproduceddate" class="WdateRead" readonly="readonly"/>
                </td>
                <td>入库截止日期:</td>
                <td>
                    <input type="text" id="storage-allocateOut-enterdeadline" style="height: 20px;" name="enterdeadline" class="WdateRead" readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td>入库批次号:</td>
                <td style="text-align: left;">
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
			<input type="button" value="确 定" name="savegoon" id="storage-savegoon-allocateOutDetailEditPage" />
		</div>
	</div>
  </div>
   <script type="text/javascript">
   		//加载数据
		var object = $("#storage-datagrid-allocateOutAddPage").datagrid("getSelected");
		$("#storage-form-allocateOutDetailAddPage").form("load",object);
		$("#storage-allocateOut-goodsname").val(object.goodsInfo.name);
		$("#storage-allocateOut-goodsbrandName").val(object.goodsInfo.brandName);
		$("#storage-allocateOut-goodsmodel").val(object.goodsInfo.model);
		$("#storage-allocateOut-auxunitname1").html(object.auxunitname);
		$("#storage-allocateOut-goodsunitname1").html(object.unitname);
		$("#storage-allocateOut-barcode").val(object.goodsInfo.barcode);
		$("#storage-allocateOut-boxnum").val(formatterBigNumNoLen(object.goodsInfo.boxnum));
        var billtype=$("#storage-allocateOut-billtype").val();
        if(billtype=='1'){
            $("#storage-allocateOut-taxprice").attr('readonly','readonly');
            $("#storage-allocateOut-boxprice").attr('readonly','readonly');
            $("#storage-allocateOut-taxamount").attr('readonly','readonly');
        }
		$(function(){
            <security:authorize url="/storage/eidtAndViewAllocateOutPrice.do" >
            $(".allocateOutPrice").show();
            $('#storage-allocateOut-taxprice').validatebox({required: true});
            $('#storage-allocateOut-taxamount').validatebox({required: true});
            $('#storage-allocateOut-boxprice').validatebox({required: true});
            </security:authorize>
            $("#storage-allocateOut-enterproduceddate").click(function(){
                if($("#storage-allocateOut-enterproduceddate").hasClass("Wdate")){
                    WdatePicker({dateFmt:'yyyy-MM-dd',
                        onpicked:function(dp){
                            if(dp.el.id=="storage-allocateOut-enterproduceddate"){
                                var produceddate = dp.cal.getDateStr();
                                var goodsid = $("#storage-allocateOut-goodsid").val();
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
                                var goodsid = $("#storage-allocateOut-goodsid").val();
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
			$("#storage-allocateOut-produceddate").click(function(){
				if(!$("#storage-allocateOut-batchno").hasClass("no_input")){
					WdatePicker({dateFmt:'yyyy-MM-dd'});
				}
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
		   			$("#storage-savegoon-allocateOutDetailEditPage").focus();
				}
			});
			$("#storage-savegoon-allocateOutDetailEditPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					editSaveDetail(true);
				}
			});
		    $("#storage-savegoon-allocateOutDetailEditPage").click(function(){
		    	editSaveDetail(true);
		    });
		});
		//通过总数量 计算数量 金额换算
		function computNum(){
			var goodsid= $("#storage-allocateOut-goodsid").val();
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
			var goodsid= $("#storage-allocateOut-goodsid").val();
			if(null==goodsid){
				return false;
			}
			var auxunitid = $("#storage-allocateOut-auxunitid").val();
			var taxprice = $("#storage-allocateOut-taxprice").val();
			var notaxprice = $("#storage-allocateOut-notaxprice").val();
			var taxtype = $("#storage-allocateOut-taxtype").val();
			var auxInterger = $("#storage-allocateOut-unitnum-aux").val();
			var auxremainder = $("#storage-allocateOut-unitnum-unit").val();
			var auxmax = $("#storage-allocateOut-unitnum-unit").attr("max");
			if(Number(auxremainder)>Number(auxmax)){
				auxremainder = auxmax;
				$("#storage-allocateOut-unitnum-unit").val(auxremainder);
			}
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
            var goodsid= $("#storage-allocateOut-goodsid").val();
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
            var goodsid= $("#storage-allocateOut-goodsid").val();
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
            var goodsid= $("#storage-allocateOut-goodsid").val();
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

        //控件调拨出库的最大数量
        var sourceid =$("#storage-allocateOut-sourceid").val();
        //当调拨单来源调拨通知单时
        if(sourceid!=null && sourceid!=""){
            $.ajax({
                url :'storage/getAllocateNoticeDetailInfo.do',
                type:'post',
                data:{id:object.sourceid,detailid:object.sourcedetailid},
                dataType:'json',
                success:function(json){
                    if(json.allocateOutDetail!=null){
                        var unitname = $("#storage-allocateOut-goodsunitname").val();
                        var maxnum = json.allocateNoticeDetail.unitnum;
                        $("#storage-allocateOut-loadInfo").html("调拨通知单中调出数量：<font color='green'>"+maxnum+unitname+"</font>");
                        $("#storage-allocateOut-unitnum").numberbox({max:maxnum});

                        if(json.allocateNoticeDetail.storagelocationid!=null){
                            $("#storage-allocateOut-enterstoragelocationid").widget("enable");
                            $("#storage-allocateOut-enterstoragelocationid").validatebox({required:true});
                        }
                    }
                }
            });
        }else{
            <security:authorize url="/storage/allocateOutStorageView.do">
            $.ajax({
                url :'storage/getStorageSummaryBatchInfo.do',
                type:'post',
                data:{summarybatchid:object.summarybatchid},
                dataType:'json',
                success:function(json){
                    if(json.storageSummaryBatch!=null){
                        var unitname = $("#storage-allocateOut-goodsunitname").val();
                        var status = $("#storage-allocateOut-status").val();
                        var usablenum = json.storageSummaryBatch.usablenum;
                        var maxnum = Number(object.unitnum)+Number(json.storageSummaryBatch.usablenum);
                        <c:if test="${fieldMap.existingnum!=null || fieldMap.usablenum!=null}">
                        $("#storage-allocateOut-loadInfo").html("现存量：<font color='green'>"+json.storageSummaryBatch.existingnum+unitname+"</font>&nbsp;可用量：<font color='green'>"+ json.storageSummaryBatch.usablenum +unitname+"</font>");
                        </c:if>
                        if(json.storageSummaryBatch.storagelocationid!=null && json.storageSummaryBatch.storagelocationid!=""){
                            $("#storage-allocateOut-enterstoragelocationid").widget("enable");
                            $("#storage-allocateOut-enterstoragelocationid").validatebox({required:true});
                        }
                        //$("#storage-allocateOut-unitnum").validatebox({validType:'max['+json.storageSummaryBatch.usablenum+']' });
                    }
                }
            });
            </security:authorize>
        }
   </script>
  </body>
</html>
