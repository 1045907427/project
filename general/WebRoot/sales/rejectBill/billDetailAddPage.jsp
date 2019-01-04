<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售退货货通知单商品详细信息新增页面</title>
  </head>
  <body>
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
	  	<form id="sales-form-billDetailAddPage">
		    <table cellpadding="5" cellspacing="2">
		    	<tr>
		    		<td class="len80">选择商品：</td>
		    		<td colspan="3"><input id="sales-goodsId-billDetailAddPage" name="goodsid" style="width: 400px;" /><input type="hidden" id="sales-goodsname-billDetailAddPage" /></td>
		    	</tr>
		    	<tr>
		    		<td>数量</td>
		    		<td><input id="sales-unitnum-billDetailAddPage" class="len150" value="0" name="unitnum"/></td>
		    		<td>辅数量：</td>
		    		<td><input id="sales-auxnum-billDetailAddPage" name="auxnum"  value="0" style="width:60px;"/><span id="sales-auxunitname-billDetailAddPage"></span>
		    			<input id="sales-auxremainder-billDetailAddPage" name="auxremainder" value="0" style="width:60px;"/><span id="sales-unitname-billDetailAddPage"></span>
		    			<input id="sales-auxnumdetail-billDetailAddPage" type="hidden" name="auxnumdetail" />
		    		</td>
		    	</tr>
		    	<tr>
		    		<td>单位：</td>
		    		<td>
		    		主：<input name="unitname" type="text" class="readonly2" style="width: 40px;" readonly="readonly" /><input type="hidden" name="unitid" />
		    		辅：<input name="auxunitname" type="text" class="readonly2" style="width: 40px;" readonly="readonly" /><input type="hidden" name="auxunitid" /></td>
		    		<td>商品品牌：</td>
		    		<td><input type="text" id="sales-brandname-billDetailAddPage" readonly="readonly" class="len150 readonly" /></td>
		    	</tr>
		    	<tr>
		    		<td>含税单价：</td>
		    		<td><input type="hidden" id="back-taxprice" />
                        <input class="len150 easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>" name="taxprice" id="sales-taxprice-billDetailAddPage" required="required" validType="intOrFloat"/> </td>
		    		<td>含税金额：</td>
		    		<td><input class="len150 readonly easyui-numberbox" id="sales-taxamount-billDetailAddPage" readonly data-options="precision:6"  name="taxamount" /></td>
		    	</tr>
		    	<tr>
		    		<td>含税箱价：</td>
		    		<td><input id="sales-boxprice-billDetailAddPage" class="len150 easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>" name="boxprice" required="required" validType="intOrFloat" /> </td>
		    		<td>箱装量：</td>
		    		<td>
                        <input name="boxnum" id="sales-boxnum-billDetailAddPage" type="text" class="len150 readonly" /></td>
		    	</tr>
		    	<tr>
		    		<td>未税单价：</td>
		    		<td><input class="len150 easyui-validatebox <c:if test="${colMap.notaxprice == null }">readonly</c:if>" name="notaxprice" id="sales-notaxprice-billDetailAddPage" required="required" validType="intOrFloat" /> </td>
		    		<td>未税金额：</td>
		    		<td><input class="len150 readonly easyui-numberbox" id="sales-notaxamount-billDetailAddPage" readonly="readonly" name="notaxamount" data-options="precision:6" /></td>
		    	</tr>
		    	<tr>
		    		<td>税种：</td>
		    		<td><input class="len150 readonly" name="taxtypename" /><input type="hidden" name="taxtype" /> </td>
		    		<td>税额：</td>
		    		<td><input class="len150 readonly easyui-numberbox" id="sales-tax-billDetailAddPage" readonly="readonly" name="tax" data-options="precision:6,groupSeparator:','" /></td>
		    	</tr>
		    	<tr>
		    		<td>规格型号：</td>
		    		<td><input id="sales-model-billDetailAddPage" readonly="readonly" class="len150 readonly" /></td>
		    		<td>条形码：</td>
		    		<td><input id="sales-barcode-billDetailAddPage" class="len150 readonly" readonly="readonly"/></td>
		    	</tr>
		    	<tr>
		    		<td>批次号：</td>
		    		<td>
		    			<input id="sales-batchno-billDetailAddPage" type="text" class="len150 "  name="batchno"/>
		    			<input id="sales-summarybatchid-billDetailAddPage" type="hidden" name="summarybatchid" />
		    		</td>
		    		<td>所属仓库：</td>
		    		<td>
		    			<input id="sales-storagelocationname-billDetailAddPage" type="hidden"/>
		    			<input id="sales-storageid-billDetailAddPage" type="hidden" name="storageid" />
		    			<input id="sales-storagename-billDetailAddPage" type="text" class="len150 readonly" readonly="readonly" name="storagename" />
		    			<input id="sales-storagelocationid-billDetailAddPage" type="hidden" name="storagelocationid" />
		    		</td>
		    	</tr>
		    	<tr>
		    		<td>生产日期：</td>
		    		<td><input id="sales-produceddate-billDetailAddPage" type="text" class="WdateRead" readonly="readonly" name="produceddate" /> </td>
		    		<td>截止日期：</td>
		    		<td><input id="sales-deadline-billDetailAddPage" type="text" class="WdateRead"  readonly="readonly" name="deadline"/></td>
		    	</tr>
		    	<tr>
		    		<td>商品类型：</td>
		    		<td>
		    			<select id="sales-deliverytype-billDetailAddPage" name="deliverytype" style="width: 150px;">
		    				<option value="0" selected="selected">正常商品</option>
		    				<option value="1">赠品</option>
                            <option value="2">捆绑</option>
		    			</select>
		    		</td>
					<td>退货属性：</td>
					<td>
						<select id="sales-rejectcategory-billDetailAddPage" name="rejectcategory" style="width: 150px;">
							<c:forEach items="${rejectCategory }" var="category" varStatus="status">
								<option value="<c:out value="${category.code }"/>"><c:out value="${category.codename }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
		    		<td>备注：</td>
		    		<td colspan="3"><input id="sales-remark-billDetailAddPage" type="text" name="remark" style="width: 392px;"/></td>
		    	</tr>
		    </table>
	    </form>
	    </div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="height:30px;text-align:right;">
  				快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
	  			<input type="button" value="继续添加" name="savegoon" id="sales-savegoon-billDetailAddPage" />
  			</div>
  		</div>
  	</div>
    <script type="text/javascript">

        <security:authorize url="/storage/rejectBillEditTaxamount.do">
        $("#sales-taxamount-billDetailAddPage").removeAttr("readonly");
        $("#sales-taxamount-billDetailAddPage").numberbox({
            onChange:function(newValue,oldValue){
                amountChange("1",newValue);
            }
        });

        $("#sales-notaxamount-billDetailAddPage").removeAttr("readonly");
        $("#sales-notaxamount-billDetailAddPage").numberbox({
            onChange:function(newValue,oldValue){
                amountChange("2",newValue);
            }
        });

        function amountChange(type, amount){ //1含税金额或2未税金额改变计算对应数据
            var goodsId = $("input[name=goodsid]").val();
            var taxtype = $("input[name=taxtype]").val();
            var unitnum = $("input[name=unitnum]").val();
            var auxnum = $("input[name=auxnum]").val();
            $.ajax({
                url:'sales/getAmountChangeByType.do',
                dataType:'json',
                async:false,
                type:'post',
                data:{type:type,amount:amount,taxtype:taxtype,unitnum:unitnum,id:goodsId},
                success:function(json){
                    $("#sales-taxprice-billDetailAddPage").val(json.taxPrice);
                    $("#sales-boxprice-billDetailAddPage").val(json.boxPrice);
                    $("#sales-taxamount-billDetailAddPage").numberbox('setValue',json.taxAmount);
                    $("#sales-notaxprice-billDetailAddPage").val(json.noTaxPrice);
                    $("#sales-notaxamount-billDetailAddPage").numberbox('setValue',json.noTaxAmount);
                    $("#sales-tax-billDetailAddPage").numberbox('setValue',json.tax);
                }
            });
        }

        </security:authorize>

    	var storageid = $("#sales-storage-rejectBillAddPage").widget("getValue");
        var presentByZero = '${presentByZero}';
    	$(function(){
    		$("#sales-goodsId-billDetailAddPage").goodsWidget({
				singleSelect:true,
				width:400,
    			required:true,
    			isHiddenUsenum:true,
    			onSelect: function(data){
    				$("#sales-goodsname-billDetailAddPage").val(data.name);
    				$("#sales-model-billDetailAddPage").val(data.model);
    				$("#sales-brandname-billDetailAddPage").val(data.brandName);
    				$("#sales-barcode-billDetailAddPage").val(data.barcode);
    				$("input[name=boxnum]").val(data.boxnum);
    				var date = $("input[name='rejectBill.businessdate']").val();
    				$.ajax({
    					url:'sales/getGoodsDetail.do',
    					dataType:'json',
    					type:'post',
    					async:false,
    					data:'id='+ data.id +'&cid=${customerId}&date='+ date+'&type=reject',
    					success:function(json){
    						$("input[name=unitid]").val(json.detail.goodsInfo.mainunit);
    						$("input[name=unitname]").val(json.detail.goodsInfo.mainunitName);
    						$("#sales-unitname-billDetailAddPage").html(json.detail.goodsInfo.mainunitName);
    						$("input[name=unitnum]").val(json.detail.unitnum);
    						$("input[name=auxunitid]").val(json.detail.auxunitid);
    						$("input[name=auxunitname]").val(json.detail.auxunitname);
    						$("#sales-auxunitname-billDetailAddPage").html(json.detail.auxunitname);
    						$("input[name=auxnumdetail]").val(json.detail.auxnumdetail);
    						$("input[name=auxnum]").val(json.detail.auxnum);
    						$("input[name=taxtype]").val(json.detail.taxtype);
    						$("input[name=taxtypename]").val(json.detail.taxtypename);
    						$("#sales-taxprice-billDetailAddPage").val(json.detail.taxprice);
                            $("#back-taxprice").val(json.detail.taxprice);
    						$("#sales-boxprice-billDetailAddPage").val(json.detail.boxprice);
    						$("#sales-taxamount-billDetailAddPage").numberbox('setValue',json.detail.taxamount);
    						$("#sales-notaxprice-billDetailAddPage").val(json.detail.notaxprice);
    						$("#sales-notaxamount-billDetailAddPage").numberbox('setValue',json.detail.notaxamount);
    						$("#sales-tax-billDetailAddPage").numberbox('setValue',json.detail.tax);
    						$("#sales-remark-billDetailAddPage").val(json.detail.remark);
    						$("#sales-unitnum-billDetailAddPage").focus();
    						$("#sales-unitnum-billDetailAddPage").select();
    					}
    				});
    				$("#sales-batchno-billDetailAddPage").widget("clear");
                    $("#sales-summarybatchid-billDetailAddPage").val("");
                    $("#sales-storageid-billDetailAddPage").val("");
                    $("#sales-storagename-billDetailAddPage").val("");
                    $("#sales-storagelocationname-billDetailAddPage").val("");
					$("#sales-storagelocationid-billDetailAddPage").val("");
					$("#sales-produceddate-billDetailAddPage").val("");
					$("#sales-deadline-billDetailAddPage").val("");
                    if(data.isbatch=='1'){
                        $("#sales-produceddate-billDetailAddPage").removeClass("WdateRead");
                        $("#sales-produceddate-billDetailAddPage").addClass("Wdate");
                        $("#sales-produceddate-billDetailAddPage").removeAttr("readonly");
                        $("#sales-deadline-billDetailAddPage").removeClass("WdateRead");
                        $("#sales-deadline-billDetailAddPage").addClass("Wdate");
                        $("#sales-deadline-billDetailAddPage").removeAttr("readonly");

                    	var param = null;
                    	if(storageid!=null && storageid!=""){
                    		param = [{field:'goodsid',op:'equal',value:data.id},
                    		       {field:'storageid',op:'equal',value:storageid}];
                    	}else{
                    		param = [{field:'goodsid',op:'equal',value:data.id}];
                    	}
						$("#sales-batchno-billDetailAddPage").widget("enable");
                    	$("#sales-batchno-billDetailAddPage").widget({
                    		referwid:'RL_T_STORAGE_BATCH_LIST_NONUM',
                    		param:param,
                			width:150,
            				singleSelect:true,
            				onSelect: function(obj){
            					$("#sales-summarybatchid-billDetailAddPage").val(obj.id);
            					$("#sales-storageid-billDetailAddPage").val(obj.storageid);
            					$("#sales-storagename-billDetailAddPage").val(obj.storagename);
            					$("#sales-storagelocationname-billDetailAddPage").val(obj.storagelocationname);
            					$("#sales-storagelocationid-billDetailAddPage").val(obj.storagelocationid);
            					$("#sales-produceddate-billDetailAddPage").val(obj.produceddate);
            					$("#sales-deadline-billDetailAddPage").val(obj.deadline);
            				},
							onClear:function(){
								$("#sales-summarybatchid-billDetailAddPage").val("");
								$("#sales-storageid-billDetailAddPage").val("");
								$("#sales-storagename-billDetailAddPage").val("");
								$("#sales-storagelocationname-billDetailAddPage").val("");
								$("#sales-storagelocationid-billDetailAddPage").val("");
								$("#sales-produceddate-billDetailAddPage").val("");
								$("#sales-deadline-billDetailAddPage").val("");
							}
                    	});
                    }else{
                        $("#sales-produceddate-billDetailAddPage").removeClass("Wdate");
                        $("#sales-produceddate-billDetailAddPage").addClass("WdateRead");
                        $("#sales-produceddate-billDetailAddPage").attr("readonly","readonly");
                        $("#sales-deadline-billDetailAddPage").removeClass("Wdate");
                        $("#sales-deadline-billDetailAddPage").addClass("WdateRead");
                        $("#sales-deadline-billDetailAddPage").attr("readonly");

                    	$("#sales-batchno-billDetailAddPage").widget("disable");
                    }
    			},
    			onClear:function(){
                    $("#sales-produceddate-billDetailAddPage").removeClass("Wdate");
                    $("#sales-produceddate-billDetailAddPage").addClass("WdateRead");
                    $("#sales-produceddate-billDetailAddPage").attr("readonly","readonly");
                    $("#sales-deadline-billDetailAddPage").removeClass("Wdate");
                    $("#sales-deadline-billDetailAddPage").addClass("WdateRead");
                    $("#sales-deadline-billDetailAddPage").attr("readonly");

					$("#sales-batchno-billDetailAddPage").widget("disable");
    				$("#sales-batchno-billDetailAddPage").widget("clear");
    				$("#sales-storageid-billDetailAddPage").val("");
    				$("#sales-storagename-billDetailAddPage").val("");
    				$("#sales-summarybatchid-billDetailAddPage").val("");
                    $("#sales-storagelocationname-billDetailAddPage").val("");
					$("#sales-storagelocationid-billDetailAddPage").val("");
					$("#sales-produceddate-billDetailAddPage").val("");
					$("#sales-deadline-billDetailAddPage").val("");
    			}
    		});
            $("#sales-produceddate-billDetailAddPage").click(function(){
                if($("#sales-produceddate-billDetailAddPage").hasClass("Wdate")){
                    WdatePicker({dateFmt:'yyyy-MM-dd',
                        onpicked:function(dp){
                            if(dp.el.id=="sales-produceddate-billDetailAddPage"){
                                var produceddate = dp.cal.getDateStr();
                                var goodsid = $("#sales-goodsId-billDetailAddPage").goodsWidget("getValue");
                                $.ajax({
                                    url :'storage/getBatchno.do',
                                    type:'post',
                                    data:{produceddate:produceddate,goodsid:goodsid},
                                    dataType:'json',
                                    async:false,
                                    success:function(json){
                                        $('#sales-deadline-billDetailAddPage').val(json.deadline);
                                    }
                                });
                            }
                        }
                    });
                }
            });
            $("#sales-deadline-billDetailAddPage").click(function(){
                if($("#sales-deadline-billDetailAddPage").hasClass("Wdate")){
                    WdatePicker({dateFmt:'yyyy-MM-dd',
                        onpicked:function(dp){
                            if(dp.el.id=="sales-deadline-billDetailAddPage"){
                                var deadline = dp.cal.getDateStr();
                                var goodsid = $("#sales-goodsId-billDetailAddPage").goodsWidget("getValue");
                                $.ajax({
                                    url :'storage/getBatchnoByDeadline.do',
                                    type:'post',
                                    data:{deadline:deadline,goodsid:goodsid},
                                    dataType:'json',
                                    async:false,
                                    success:function(json){
                                        $("#sales-produceddate-billDetailAddPage").val(json.produceddate);
                                    }
                                });
                            }
                        }
                    });
                }
            });


    		$("#sales-batchno-billDetailAddPage").widget({
        		referwid:'RL_T_STORAGE_BATCH_LIST_NONUM',
    			width:150,
				singleSelect:true,
				disabled:true
        	});
    		$("#sales-unitnum-billDetailAddPage").validatebox({
			    required: true,
			    validType: 'intOrFloatNum[${decimallen}]'
			});
			$("#sales-auxnum-billDetailAddPage").validatebox({
			    required: true,
			    validType: 'integer'
			});
			$("#sales-auxremainder-billDetailAddPage").validatebox({
			    required: true,
			    validType: 'intOrFloatNum[${decimallen}]'
			});
            //商品类型变化
            $("#sales-deliverytype-billDetailAddPage").change(function(){
                var a =  $("#sales-deliverytype-billDetailAddPage").val();
                if(a == 1){
                    $("#sales-remark-billDetailAddPage").val("赠品");
                    if(presentByZero == 0){
                        $("#sales-taxprice-billDetailAddPage").val(0);
                        priceChange("1", '#sales-taxprice-billDetailAddPage');
                    }
                }else{
                    $("#sales-remark-billDetailAddPage").val("");
                    var price =  $("#back-taxprice").val();
                    $("#sales-taxprice-billDetailAddPage").val(price);
                    priceChange("1", '#sales-taxprice-billDetailAddPage');
                }
            });

    		$("#sales-unitnum-billDetailAddPage").change(function(){
    			 unitnumChange(1);
    		});
    		$("#sales-auxnum-billDetailAddPage").change(function(){
    			unitnumChange(2);
    		});
    		$("#sales-auxremainder-billDetailAddPage").change(function(){
    			unitnumChange(2);
    		});
    		$("#sales-taxprice-billDetailAddPage").change(function(){
    			 priceChange("1", '#sales-taxprice-billDetailAddPage');
    		});
    		$("#sales-notaxprice-billDetailAddPage").change(function(){
    			priceChange("2", '#sales-notaxprice-billDetailAddPage');
    		});
    		$("#sales-boxprice-billDetailAddPage").change(function(){
	   			 boxpriceChange();
	   		});
    		$("#sales-savegoon-billDetailAddPage").click(function(){
    			addSaveDetail(true);
    		});
    		$("#sales-unitnum-billDetailAddPage").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
		   			$("#sales-auxnum-billDetailAddPage").focus();
		   			$("#sales-auxnum-billDetailAddPage").select();
				}
			});
			$("#sales-auxnum-billDetailAddPage").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
		   			$("#sales-auxremainder-billDetailAddPage").focus();
		   			$("#sales-auxremainder-billDetailAddPage").select();
				}
			});
			$("#sales-auxremainder-billDetailAddPage").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
		   			$("#sales-remark-billDetailAddPage").focus();
		   			$("#sales-remark-billDetailAddPage").select();
				}
			});
			$("#sales-remark-billDetailAddPage").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
		   			$("#sales-savegoon-billDetailAddPage").focus();
		   			$("#sales-savegoon-billDetailAddPage").select();
				}
			});
			$("#sales-savegoon-billDetailAddPage").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					addSaveDetail(true);
				}
			});
			$("#sales-boxprice-billDetailAddPage").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
		   			$("#sales-remark-billDetailAddPage").focus();
		   			$("#sales-remark-billDetailAddPage").select();
				}
			});
    	});
    	function unitnumChange(type){ //数量改变方法
    		var $this = $("#sales-unitnum-billDetailAddPage").css({'background':'url(image/loading.gif) right top no-repeat'});
    		var goodsId = $("input[name=goodsid]").val();
    		var unitnum = $("input[name=unitnum]").val();
    		var auxnum = $("input[name=auxnum]").val();
    		var auxremainder = $("input[name=auxremainder]").val();
    		var aid = $("input[name=auxunitid]").val();
    		var taxprice = $("#sales-taxprice-billDetailAddPage").val();
    		var notaxprice = $("#sales-notaxprice-billDetailAddPage").val();
    		var taxtype = $("input[name=taxtype]").val();
    		$.ajax({
    			url:'sales/getAuxUnitNum.do',
    			dataType:'json',
    			type:'post',
    			async:false,
    			data:'id='+ goodsId +'&unitnum='+ unitnum +'&aid='+ aid +'&tp='+ taxprice +'&auxnum='+ auxnum +'&taxtype='+ taxtype+ '&overnum='+ auxremainder+ '&type='+ type,
    			success:function(json){
    				$("input[name=auxnumdetail]").val(json.auxnumdetail);
   					$("#sales-taxamount-billDetailAddPage").numberbox('setValue',json.taxAmount);
    				$("#sales-notaxamount-billDetailAddPage").numberbox('setValue',json.noTaxAmount);
    				$("#sales-tax-billDetailAddPage").numberbox('setValue',json.tax);
    				if(type == 1){
    					$("input[name=auxnum]").val(json.auxnum);
    					$("input[name=auxremainder]").val(json.overnum);
    				}
    				if(type == 2){
    					$("input[name=unitnum]").val(json.unitnum);
                        $("input[name=auxnum]").val(json.auxnum);
                        $("input[name=auxremainder]").val(json.overnum);
    				}
    				$this.css({'background':''});
    			}
    		});
    	}
    	function priceChange(type, id){ //1含税单价或2未税单价改变计算对应数据
    		var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
    		var price = $(id).val();
    		var goodsId = $("input[name=goodsid]").val();
    		var taxtype = $("input[name=taxtype]").val();
    		var unitnum = $("input[name=unitnum]").val();
    		var auxnum = $("input[name=auxnum]").val();
    		$.ajax({
    			url:'sales/getAmountChanger.do',
    			dataType:'json',
    			async:false,
    			type:'post',
    			data:'type='+ type +'&price='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsId,
    			success:function(json){
    				$("#sales-taxprice-billDetailAddPage").val(json.taxPrice);
    				$("#sales-boxprice-billDetailAddPage").val(json.boxPrice);
    				$("#sales-taxamount-billDetailAddPage").numberbox('setValue',json.taxAmount);
    				$("#sales-notaxprice-billDetailAddPage").val(json.noTaxPrice);
    				$("#sales-notaxamount-billDetailAddPage").numberbox('setValue',json.noTaxAmount);
    				$("#sales-tax-billDetailAddPage").numberbox('setValue',json.tax);
    				$this.css({'background':''});
    			}
    		});
    	}
    	function boxpriceChange(){
    		var $this = $("#sales-boxprice-billDetailAddPage").css({'background':'url(image/loading.gif) right top no-repeat'});
    		var price = $("#sales-boxprice-billDetailAddPage").val();
    		var goodsId = $("input[name=goodsid]").val();
    		var taxtype = $("input[name=taxtype]").val();
    		var unitnum = $("input[name=unitnum]").val();
    		var auxnum = $("input[name=auxnum]").val();
    		$.ajax({
    			url:'sales/getAmountChangerByBoxprice.do',
    			dataType:'json',
    			async:false,
    			type:'post',
    			data:'&boxprice='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsId,
    			success:function(json){
    				$("#sales-taxprice-billDetailAddPage").val(json.taxPrice);
    				$("#sales-boxprice-billDetailAddPage").val(json.boxPrice);
    				$("#sales-taxamount-billDetailAddPage").numberbox('setValue',json.taxAmount);
    				$("#sales-notaxprice-billDetailAddPage").val(json.noTaxPrice);
    				$("#sales-notaxamount-billDetailAddPage").numberbox('setValue',json.noTaxAmount);
    				$("#sales-tax-billDetailAddPage").numberbox('setValue',json.tax);
    				$this.css({'background':''});
    			}
    		});
    	}
    </script>
  </body>
</html>
