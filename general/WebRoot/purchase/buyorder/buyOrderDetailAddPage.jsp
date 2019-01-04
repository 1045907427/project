<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
		  	<form id="purchase-form-buyOrderDetailAddPage" method="post">
		  		<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="text-align: right;">商品：</td>
						<td><input type="text" id="purchase-buyOrderDetail-goodsid" name="goodsid" style="width:150px;" tabindex="1"/>
							<input type="hidden" id="purchase-buyOrderDetail-goodsname" name="name"/>
						</td>
						<td colspan="2" id="purchase-loading-buyOrderDetail"></td>					
					</tr>
					<tr>
						<td style="text-align: right;">辅数量：</td>
			   			<td style="text-align: left;">
		   					<input type="text" id="purchase-buyOrderDetail-auxnum" name="auxnum" value="0" class="easyui-validatebox" validType="integer" data-options="required:true" style="width:60px; float:left;" />
		   					<span id="purchase-buyOrderDetail-span-auxunitname" style="float: left;line-height:25px;">&nbsp;</span>
		   					<input type="text" id="purchase-buyOrderDetail-unitnum-auxremainder" name="auxremainder" value="0" class="easyui-validatebox" validType="intOrFloatNum[${decimallen}]" data-options="required:true" style="width:60px;float:left;"/>
		   					<span id="purchase-buyOrderDetail-span-unitname" style="float: left;line-height:25px;">&nbsp;</span>
		   					<input type="hidden" id="purchase-buyOrderDetail-auxnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
			   			</td>
						<td style="text-align: right;">数量：</td>
						<td><input type="text" id="purchase-buyOrderDetail-unitnum" name="unitnum" value="0" class="easyui-validatebox" validType="intOrFloatNum[${decimallen}]" data-options="required:true" style="width:150px;" /></td>
					</tr>
					<tr>
		   				<td style="text-align: right;">单位：</td>
			    		<td style="float: left;">
			    			主：<input id="purchase-buyOrderDetail-unitname" name="unitname" type="text" class="readonly2" style="width: 48px;" readonly="readonly" /><input id="purchase-buyOrderDetail-unitid" type="hidden" name="unitid" />
			    			辅：<input id="purchase-buyOrderDetail-auxunitname" name="auxunitname" type="text" class="readonly2" style="width: 48px;" readonly="readonly" /><input id="purchase-buyOrderDetail-auxunitid" type="hidden" name="auxunitid" />
			    		</td>
			    		<td style="text-align: right;">箱装量：</td>
			    		<td>
			    			<input id="purchase-buyOrderDetail-boxnum" type="text" class="len150 readonly" readonly="readonly" />
			    		</td>
		   			</tr>
					<tr>
						<td style="text-align: right;">商品品牌：</td>
						<td><input type="text" id="purchase-buyOrderDetail-brand" readonly="readonly" style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
						<td style="text-align: right;">商品规格：</td>
						<td><input type="text" id="purchase-buyOrderDetail-model" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">税种：</td>
						<td>
							<input type="text" id="purchase-buyOrderDetail-taxname" name="taxtypename" readonly="readonly"  style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/>
							<input type="hidden" id="purchase-buyOrderDetail-taxtype" name="taxtype" />
						</td>
						<td style="text-align: right;">税额：</td>
						<td><input type="text" id="purchase-buyOrderDetail-tax" name="tax" class="easyui-numberbox" data-options="precision:6" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">含税单价：</td>
						<td>
							<input type="text" id="purchase-buyOrderDetail-taxprice" name="taxprice" class="easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> style="width:150px;"/>
							<input type="hidden" id="purchase-buyOrderDetail-hidtaxprice"/>
						</td>
						<td style="text-align: right;">含税金额：</td>
						<td><input type="text" id="purchase-buyOrderDetail-taxamount" name="taxamount" class="easyui-validatebox <c:if test="${colMap.taxamount == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxamount == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">未税单价：</td>
						<td><input type="text" id="purchase-buyOrderDetail-notaxprice" name="notaxprice" class="easyui-validatebox <c:if test="${colMap.notaxprice == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
						<td style="text-align: right;">未税金额：</td>
						<td><input type="text" id="purchase-buyOrderDetail-notaxamount" name="notaxamount"class="easyui-validatebox <c:if test="${colMap.taxamount == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxamount == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">含税箱价：</td>
						<td><input type="text" id="purchase-buyOrderDetail-boxprice" name="boxprice" class="easyui-validatebox <c:if test="${colMap.notaxprice == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
						<td style="text-align: right;">未税箱价：</td>
						<td><input type="text" id="purchase-buyOrderDetail-noboxprice" name="noboxprice" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">条形码：</td>
						<td><input type="text" id="purchase-buyOrderDetail-barcode"  readonly="readonly" style="width:150px;  border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
						<td style="text-align: right;">要求到货日期：</td>
						<td><input type="text" id="purchase-buyOrderDetail-arrivedate" name="arrivedate" value="${arrivedate }" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" class="Wdate" style="width:150px; " tabindex="3"/></td>
					</tr>
                    <tr style="display: none">
                        <td>重量：</td>
                        <td><input id="purchase-buyOrderDetail-totalboxweight" name="totalboxweight" type="text" class="len130 readonly" readonly="readonly" /><span>千克</span></td>
                        <td>体积：</td>
                        <td><input id="purchase-buyOrderDetail-totalboxvolume" name="totalboxvolume" type="text" class="len120 readonly no_input"  readonly="readonly"/><span>立方米</span></td>
                    </tr>
					<tr>
						<td style="text-align: right;">备注：</td>
						<td colspan="3">
							<input type="text" id="purchase-buyOrderDetail-remark" name="remark" style="width:405px;" tabindex="4"/>
						</td>
					</tr>
				</table>
				<input type="hidden" id="purchase-buyOrderDetail-billdetailno" name="billdetailno" />
				<input type="hidden" id="purchase-buyOrderDetail-id" name="id" />
				<input type="hidden" name="goodsfield01" />
				<input type="hidden" name="goodsfield02" />
				<input type="hidden" name="goodsfield03" />
				<input type="hidden" name="goodsfield04" />
				<input type="hidden" name="goodsfield05" />
				<input type="hidden" name="goodsfield06" />
				<input type="hidden" name="goodsfield07" />
				<input type="hidden" name="goodsfield08" />
				<input type="hidden" name="goodsfield09" />
		  	</form>
  		</div>
      <div data-options="region:'south',border:false">
          <div class="buttonDetailBG" style="text-align:right;">
	  			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span><input type="button" value="继续添加" name="savegoon" id="purchase-buyOrderDetailAddPage-addSaveGoOn" />
	  			<input type="button" value="确 定" name="savenogo" id="purchase-buyOrderDetailAddPage-addSave" />
  			</div>
	  	</div>
  	</div>
  	<script type="text/javascript">

		var selectedGoodsid=getFilterGoodsid();
	
		function getFilterGoodsid(){
			var tmpgoodsid=[];
			var goodsids="";
			var selectDetailRow=[];
	  		if($("#purchase-buyOrderAddPage-buyOrdertable").size()>0){
	  			selectDetailRow=$("#purchase-buyOrderAddPage-buyOrdertable").datagrid('getRows');
	  		}
	  		for(var i=0; i<selectDetailRow.length; i++){
	   			var rowJson = selectDetailRow[i];
	   			if(rowJson.goodsid != undefined && rowJson.goodsid!=""){
  	   				tmpgoodsid.push(rowJson.goodsid);
	   			}
	   		}
	   		if(tmpgoodsid.length>0){
	   			goodsids=tmpgoodsid.join(',');
	   		}
	   		return goodsids;
		}
  		function detailRemoteCompleteOrder(){
  	  		var taxprice=$("#purchase-buyOrderDetail-hidtaxprice").val();
  	  		var unitnum=$("#purchase-buyOrderDetail-unitnum").val();
  	  		if(taxprice==null || taxprice=="" ){
  	  	  		taxprice="0";
  	  		}
  	  		if(unitnum==null || unitnum==""){
  	  	  		unitnum="0";
  	  		}
  	  		var goodsid = $("#purchase-buyOrderDetail-goodsid").goodsWidget("getValue");
  	  		if(goodsid==null || goodsid==""){
  	  	  		return false;
  	  		}
  	  		var taxtype=$("#purchase-buyOrderDetail-taxtype").val();

  	  		$("#purchase-buyOrderDetail-auxnum").addClass("inputload");
  	  		$("#purchase-buyOrderDetail-auxremainder").addClass("inputload");
  	  		$("#purchase-buyOrderDetail-unitnum").addClass("inputload");
  	  		try{
	  			$.ajax({   
		            url :'purchase/common/completeBuyDetailGoodsOrder.do',
		            type:'post',
		            dataType:'json',
			        async: false,
		            data:{goodsid:goodsid,taxprice:taxprice,unitnum:unitnum,taxtype:taxtype,businessdate:'${businessdate}'},
		            success:function(json){
		            	$("#purchase-buyOrderDetail-taxname").val(json.taxtypename);
			            $("#purchase-buyOrderDetail-unitname").val(json.unitname);
		            	$("#purchase-buyOrderDetail-span-unitname").html(json.unitname);
		            	$("#purchase-buyOrderDetail-auxunitid").val(json.auxunitid);
			            $("#purchase-buyOrderDetail-auxunitname").val(json.auxunitname);
		            	$("#purchase-buyOrderDetail-span-auxunitname").html(json.auxunitname);
			            $("#purchase-buyOrderDetail-auxnumdetail").val(json.auxnumdetail);
			            $("#purchase-buyOrderDetail-auxnum").val(json.auxnum);
			            $("#purchase-buyOrderDetail-notaxprice").val(json.notaxprice);
			            $("#purchase-buyOrderDetail-taxprice").val(json.taxprice);
			            $("#purchase-buyOrderDetail-boxprice").val(json.boxprice);
			            $("#purchase-buyOrderDetail-noboxprice").val(json.noboxprice);
			            $("#purchase-buyOrderDetail-tax").numberbox('setValue', json.tax);
			            $("#purchase-buyOrderDetail-taxamount").val( json.taxamount);
			            $("#purchase-buyOrderDetail-notaxamount").val( json.notaxamount);

                        $("#purchase-buyOrderDetail-totalboxweight").val(json.totalboxweight);
                        $("#purchase-buyOrderDetail-totalboxvolume").val(json.totalboxvolume);

			            if(json.limitprice && json.limitprice=='1'){
			            	var htmlsb=new Array();
			            	htmlsb.push('使用调价单中价格,调价期');
			            	htmlsb.push(json.lpstartdate);
			            	htmlsb.push("至");
			            	if(json.lpenddate){
				            	htmlsb.push(json.lpenddate);
			            	}else{
				            	htmlsb.push("--");
			            	}
			            	htmlsb.push("。");
			            	$("#purchase-buyOrderDetail-remark").val(htmlsb.join(''));
			            }
			            $("#purchase-buyOrderDetail-auxnum").val(json.auxInteger);
		            	$("#purchase-buyOrderDetail-unitnum-auxremainder").val(json.auxremainder);
		            	if(json.auxrate!=null){
		            		$("#purchase-buyOrderDetail-unitnum-auxremainder").attr("max",json.auxrate-1);
		            	}
		            	$("#purchase-buyOrderDetail-auxnum").removeClass("inputload");
		      	  		$("#purchase-buyOrderDetail-auxremainder").removeClass("inputload");
		      	  		$("#purchase-buyOrderDetail-unitnum").removeClass("inputload");
		            }
		        });
  	  		}catch(e){
  	  		}
  		}
  		function orderDetailAddSaveGoOnDialog(){
  			var $DetailOper=$("#purchase-buyOrderAddPage-dialog-DetailOper-content");
  	  		if($DetailOper.size()>0){
  				$DetailOper.dialog('close');
  	  		}
  		   	$('<div id="purchase-buyOrderAddPage-dialog-DetailOper-content"></div>').appendTo('#purchase-buyOrderAddPage-dialog-DetailOper');
  	  		$DetailOper=$("#purchase-buyOrderAddPage-dialog-DetailOper-content");
  	  		
  			$DetailOper.dialog({
  				title:'新增',
  			    width: 600,  
  			    height: 400,
  			    closed: true,  
  			    cache: false, 
  			    modal: true,
  			    maximizable:true,
  			    href:"purchase/buyorder/buyOrderDetailAddPage.do?supplierid=${supplierid}&businessdate=${businessdate}",
			    onLoad:function(){
			    	$("#purchase-buyOrderDetail-goodsid").focus();
		    	},
			    onClose:function(){
		            $DetailOper.dialog("destroy");
		        }
  			});
  			$DetailOper.dialog("open");
  		}
  	//通过总数量 计算数量 金额换算
		function computeNum(){
			var goodsInfo= $("#purchase-buyOrderDetail-goodsid").goodsWidget("getObject");
			if(null==goodsInfo){
				return false;
			}
			var auxunitid = $("#purchase-buyOrderDetail-auxunitid").val();
			var unitnum = $("#purchase-buyOrderDetail-unitnum").val();
			var taxprice = $("#purchase-buyOrderDetail-taxprice").val();
			if(taxprice==null || taxprice==""){
				taxprice="0";
			}
			var notaxprice = $("#purchase-buyOrderDetail-notaxprice").val();
			var taxtype = $("#purchase-buyOrderDetail-taxtype").val();
			$("#purchase-buyOrderDetail-unitnum").addClass("inputload");
			$.ajax({   
	            url :'basefiles/computeGoodsByUnitnum.do',
	            type:'post',
	            data:{unitnum:unitnum,goodsid:goodsInfo.id,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#purchase-buyOrderDetail-taxamount").val(json.taxamount);
	            	$("#purchase-buyOrderDetail-notaxamount").val(json.notaxamount);
	            	$("#purchase-buyOrderDetail-tax").numberbox("setValue",json.tax);
	            	$("#purchase-buyOrderDetail-taxtypename").val(json.taxtypename);
	            	$("#purchase-buyOrderDetail-auxnumdetail").val(json.auxnumdetail);
	            	$("#purchase-buyOrderDetail-auxunitnum").val(json.auxnum);
	            	$("#purchase-buyOrderDetail-auxunitname").val(json.auxunitname);
	            	$("#purchase-buyOrderDetail-span-auxunitname").html(json.auxunitname);
	            	$("#purchase-buyOrderDe-unitnamename").val(json.unitname);
	            	$("#purchase-buyOrderDetail-span-unitname").html(json.unitname);

                    $("#purchase-buyOrderDetail-totalboxweight").val(json.totalboxweight);
                    $("#purchase-buyOrderDetail-totalboxvolume").val(json.totalboxvolume);
	            	
	            	$("#purchase-buyOrderDetail-notaxamount").val(json.notaxamount);
	            	
	            	$("#purchase-buyOrderDetail-auxnum").val(json.auxInteger);
	            	$("#purchase-buyOrderDetail-unitnum-auxremainder").val(json.auxremainder);
	            	if(json.auxrate!=null){
	            		$("#purchase-buyOrderAddPag-unitnum-auxremainder").attr("max",json.auxrate-1);
	            	}
	            	$("#purchase-buyOrderDetail-unitnum").removeClass("inputload");
	            }
	        });
		}
  	//通过辅单位数量
		function computeNumByAux(){
			var goodsInfo= $("#purchase-buyOrderDetail-goodsid").goodsWidget("getObject");
			var auxunitid = $("#purchase-buyOrderDetail-auxunitid").val();
			var taxprice = $("#purchase-buyOrderDetail-taxprice").val();
			if(taxprice==null || taxprice==""){
				taxprice="0";
			}
			var notaxprice = $("#purchase-buyOrderDetail-notaxprice").val();
			var taxtype = $("#purchase-buyOrderDetail-taxtype").val();
			var auxInterger = $("#purchase-buyOrderDetail-auxnum").val();
			var auxremainder = $("#purchase-buyOrderDetail-unitnum-auxremainder").val();
			$("#purchase-buyOrderDetail-unitnum").addClass("inputload");
			$.ajax({   
	            url :'basefiles/computeGoodsByAuxnum.do',
	            type:'post',
	            data:{auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsInfo.id,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#purchase-buyOrderDetail-taxamount").val(json.taxamount);
	            	$("#purchase-buyOrderDetail-notaxamount").val(json.notaxamount);
	            	$("#purchase-buyOrderDetail-tax").numberbox("setValue",json.tax);
	            	$("#purchase-buyOrderDetail-taxtypename").val(json.taxtypename);
	            	
	            	$("#purchase-buyOrderDetail-taxprice").val(json.taxprice);
	            	$("#purchase-buyOrderDetail-notaxprice").val(json.notaxprice);
	            	$("#purchase-buyOrderDetail-notaxamount").val(json.notaxamount);
	            	
	            	$("#purchase-buyOrderDetail-unitnum").val(json.mainnum);
                    $("#purchase-buyOrderDetail-auxnum").val(json.auxInterger);
                    $("#purchase-buyOrderDetail-unitnum-auxremainder").val(json.auxremainder);
                    $("#purchase-buyOrderDetail-auxnumdetail").val(json.auxnumdetail);

                    $("#purchase-buyOrderDetail-totalboxweight").val(json.totalboxweight);
                    $("#purchase-buyOrderDetail-totalboxvolume").val(json.totalboxvolume);
	            	
	            	$("#purchase-buyOrderDetail-unitnum").removeClass("inputload");
	            }
	        });

		}
		function saveOrderDetail(isGoOn){
	    	$("#purchase-buyOrderDetail-remark").focus();
  			var flag=$("#purchase-form-buyOrderDetailAddPage").form('validate');
  			if(!flag){
	  			return false;
  			}

  			var goodsInfo=$("#purchase-buyOrderDetail-goodsid").goodsWidget('getObject');
  			if(goodsInfo==null || goodsInfo==""){
				$.messager.alert("提醒","抱歉，请选择商品！");
				return false;		  			
  			}

			<c:if test="${isrepeat == '0'}">
	  			if(!checkAfterAddGoods(goodsInfo.id)){
					$.messager.alert("提醒","抱歉，采购订单中已经存在该商品！");
					return false;
				}
			</c:if>

			var unitnum=$("#purchase-buyOrderDetail-unitnum").val();
			if(unitnum==null || unitnum=="" || unitnum==0){
				$.messager.alert("提醒","抱歉，请填写数量！");
				return false;
			}

			var formdata=$("#purchase-form-buyOrderDetailAddPage").serializeJSON();

			formdata.goodsInfo = goodsInfo;
			if(formdata){
				var index=getAddRowIndex();
				$("#purchase-buyOrderAddPage-buyOrdertable").datagrid('updateRow',{
					index:index,
					row:formdata
				});
				footerReCalc();
				if(index>=14){
					var rows=$("#purchase-buyOrderAddPage-buyOrdertable").datagrid('getRows');
					if(index == rows.length - 1){
						$("#purchase-buyOrderAddPage-buyOrdertable").datagrid('appendRow',{});
					}
				}
			}
			if(isGoOn){
				orderDetailAddSaveGoOnDialog();
			}else{
	  			$("#purchase-buyOrderAddPage-dialog-DetailOper-content").dialog("close");
			}
		}
		function priceChange(type, id){ //1含税单价或2未税单价改变计算对应数据
    		var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
    		var price = $(id).val();
    		var goodsid = $("#purchase-buyOrderDetail-goodsid").goodsWidget("getValue");
  	  		if(goodsid==null || goodsid==""){
  	  	  		return false;
  	  		}
    		var taxtype = $("#purchase-buyOrderDetail-taxtype").val();
    		var unitnum = $("#purchase-buyOrderDetail-unitnum").val();
    		var auxnum = $("#purchase-buyOrderDetail-auxnum").val();
    		$.ajax({
    			url:'purchase/common/getAmountChanger.do',
    			dataType:'json',
    			async:false,
    			type:'post',
    			data:'type='+ type +'&price='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsid,
    			success:function(json){
    				$("#purchase-buyOrderDetail-taxprice").val(json.taxPrice);
    				$("#purchase-buyOrderDetail-boxprice").val(json.boxprice);
    				$("#purchase-buyOrderDetail-noboxprice").val(json.noboxprice);
    				$("#purchase-buyOrderDetail-taxamount").val(json.taxAmount);
    				$("#purchase-buyOrderDetail-notaxprice").val(json.noTaxPrice);
    				$("#purchase-buyOrderDetail-notaxamount").val(json.noTaxAmount);
    				$("#purchase-buyOrderDetail-tax").numberbox('setValue',json.tax);
    				$this.css({'background':''});
    			}
    		});
    	}
		function boxpriceChange(id){
			var $this = $(id);
			$this.css({'background':'url(image/loading.gif) right top no-repeat'});
    		var price = $(id).val();
    		var goodsid = $("#purchase-buyOrderDetail-goodsid").goodsWidget("getValue");
  	  		if(goodsid==null || goodsid==""){
  	  	  		return false;
  	  		}
    		var taxtype = $("#purchase-buyOrderDetail-taxtype").val();
    		var unitnum = $("#purchase-buyOrderDetail-unitnum").val();
    		var auxnum = $("#purchase-buyOrderDetail-auxnum").val();
    		$.ajax({
    			url:'purchase/common/getAmountChangerByBoxprice.do',
    			dataType:'json',
    			async:false,
    			type:'post',
                data:{
                    "price": price ,
                    "taxtype": taxtype ,
                    "unitnum": unitnum,
                    "id": goodsid,
                    "type": "1"
                },
    			success:function(json){
                    $("#purchase-buyOrderDetail-taxprice").val(json.taxprice);
                    $("#purchase-buyOrderDetail-noboxprice").val(json.noboxprice);
                    $("#purchase-buyOrderDetail-boxprice").val(json.boxprice);
                    $("#purchase-buyOrderDetail-taxamount").val(json.taxamount);
                    $("#purchase-buyOrderDetail-notaxprice").val(json.notaxprice);
                    $("#purchase-buyOrderDetail-notaxamount").val(json.notaxamount);
                    $("#purchase-buyOrderDetail-tax").numberbox('setValue',json.tax);
    				$this.css({'background':''});
    			}
    		});
		}
		function amountChange(type, id){ //1含税金额或2未税金额改变计算对应数据
            var $this = $(id);
            $this.css({'background':'url(image/loading.gif) right top no-repeat'});
			var price = $(id).val();

			var goodsInfo= $("#purchase-buyOrderDetail-goodsid").goodsWidget("getObject");
			if(null==goodsInfo && goodsInfo.id!=null && goodsInfo.id!=""){
				return false;
			}
			
			var taxtype = $("#purchase-buyOrderDetail-taxtype").val();
			var unitnum = $("#purchase-buyOrderDetail-unitnum").val();
			var auxnum = $("#purchase-buyOrderDetail-auxnum").val();
			$.ajax({
				url:'purchase/common/getPriceChanger.do',
				dataType:'json',
				async:false,
				type:'post',
				data:'type='+ type +'&amount='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsInfo.id,
				success:function(json){
					$("#purchase-buyOrderDetail-taxprice").val(json.taxPrice);
					$("#purchase-buyOrderDetail-boxprice").val(json.boxprice);
					$("#purchase-buyOrderDetail-noboxprice").val(json.noboxprice);
					$("#purchase-buyOrderDetail-taxamount").val(json.taxAmount);
					$("#purchase-buyOrderDetail-notaxprice").val(json.noTaxPrice);
					$("#purchase-buyOrderDetail-notaxamount").val(json.noTaxAmount);
					$("#purchase-buyOrderDetail-tax").numberbox('setValue',json.tax);
					$this.css({'background':''});
				}
			});
		}
  		$(document).ready(function(){
            $("#purchase-buyOrderDetail-arrivedate").click(function(){
                if(!$("#purchase-buyOrderDetail-arrivedate").hasClass("WdateRead")){
                    WdatePicker({dateFmt:'yyyy-MM-dd'});
                }
            });

  			$("#purchase-buyOrderDetail-goodsid").goodsWidget({
    			name:'t_purchase_buyorder_detail',
    			col:'goodsid',
    			singleSelect:true,
    			width:150,
    			canBuySale:'1',<%--购销类型，可采购（购销、可购）--%>
    			queryAllBySupplier:'${supplierid}',<%--关联出此供应商所有商品--%>
        		param:[
                  		<%--通用版需要通过默认供应商和第二供应商查询
                  		{field:'defaultsupplier',op:'equal',value:'${supplierid}'}, --%>
               		<c:if test="${isrepeat == '0'}">//1允许重复0不允许重复
	               		{field:'id',op:'notin',value:selectedGoodsid}
               		</c:if>
              		<%--,{field:'isinoutstorage',op:'equal',value:'1'} 通用版只用到购销类型--%>
                ],
    			onSelect : function(data){
    				if(data){
    					<c:if test="${isrepeat == '0'}">
	    		  			if(!checkAfterAddGoods(data.id)){
	    						$.messager.alert("提醒","抱歉，采购订单中已经存在该商品！");
	    	    				return false;
	    					}
    					</c:if>
    		  			$("#purchase-loading-buyOrderDetail").html("商品编码：<font color='green'>"+data.id+"</font>");	
        				$("#purchase-buyOrderDetail-goodsname").val(data.name);
        				$("#purchase-buyOrderDetail-brand").val(data.brandName);
        				$("#purchase-buyOrderDetail-model").val(data.model);
        				$("#purchase-buyOrderDetail-unitname").val(data.mainunitName);
        				$("#purchase-buyOrderDetail-unitid").val(data.mainunit);
        				$("#purchase-buyOrderDetail-barcode").val(data.barcode);
        				$("#purchase-buyOrderDetail-taxtype").val(data.defaulttaxtype);
        				$("#purchase-form-buyOrderAddPage input[name='goodsfield01']").val(data.field01);
        				$("#purchase-form-buyOrderAddPage input[name='goodsfield02']").val(data.field02);
        				$("#purchase-form-buyOrderAddPage input[name='goodsfield03']").val(data.field03);
        				$("#purchase-form-buyOrderAddPage input[name='goodsfield04']").val(data.field04);
        				$("#purchase-form-buyOrderAddPage input[name='goodsfield05']").val(data.field05);
        				$("#purchase-form-buyOrderAddPage input[name='goodsfield06']").val(data.field06);
        				$("#purchase-form-buyOrderAddPage input[name='goodsfield07']").val(data.field07);
        				$("#purchase-form-buyOrderAddPage input[name='goodsfield08']").val(data.field08);
        				$("#purchase-form-buyOrderAddPage input[name='goodsfield09']").val(data.field09);

        				$("#purchase-buyOrderDetail-boxnum").val(data.boxnum);
        				
        				<c:choose>
	    			  		<c:when test="${purchasePriceType == 1}">
				            	$("#purchase-buyOrderDetail-hidtaxprice").val(data.highestbuyprice);       			  			
	    			  		</c:when>
	    			  		<c:when test="${purchasePriceType == 2}">
					            $("#purchase-buyOrderDetail-hidtaxprice").val(data.newbuyprice);        			  			
				  			</c:when>
	    			  		<c:otherwise>
				            	$("#purchase-buyOrderDetail-hidtaxprice").val(data.highestbuyprice); 
			            	</c:otherwise>
	    			  	</c:choose>
        				
						
        				detailRemoteCompleteOrder();
        				$("#purchase-buyOrderDetail-auxnum").focus();
        				$("#purchase-buyOrderDetail-auxnum").select();
    				}
    			}
    		});

  			$("#purchase-buyOrderDetail-unitnum").change(function(){
    			if($(this).validatebox('isValid')){
    				computeNum();
    			}
    		});
    		$("#purchase-buyOrderDetail-auxnum").change(function(){
        		if($(this).validatebox('isValid')){
    				computeNumByAux();
        		}
    		});
    		$("#purchase-buyOrderDetail-unitnum-auxremainder").change(function(){
        		if($(this).validatebox('isValid')){
    				computeNumByAux();
        		}
    		});

    		$("#purchase-buyOrderDetail-taxprice").focus(function(){
        		if(!$(this).hasClass("readonly")){
            		$(this).select();
        		}else{
        			$("#purchase-buyOrderDetail-taxamount").focus();
        		}
    		});
    		
    		$("#purchase-buyOrderDetail-taxprice").change(function(){
        		if($(this).validatebox('isValid') && !$(this).hasClass("readonly")){
        			priceChange("1",this);
        		}
    		});

			$("#purchase-buyOrderDetail-taxamount").focus(function(){
        		if(!$(this).hasClass("readonly")){
            		$(this).select();
        		}else{
        			$("#purchase-buyOrderDetail-notaxprice").focus();
        		}
    		});

    		
    		$("#purchase-buyOrderDetail-taxamount").change(function(){
        		if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
        			amountChange("1",this);
        		}
    		});
    		$("#purchase-buyOrderDetail-notaxprice").focus(function(){
        		if(!$(this).hasClass("readonly")){
            		$(this).select();
        		}else{
		   			$("#purchase-buyOrderDetail-notaxamount").focus();
        		}
    		});

    		$("#purchase-buyOrderDetail-notaxprice").change(function(){
        		if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
        			priceChange("2",this);
        		}
    		});
    		
    		$("#purchase-buyOrderDetail-notaxamount").focus(function(){
        		if(!$(this).hasClass("readonly")){
            		$(this).select();
        		}else{
        			$("#purchase-buyOrderDetail-boxprice").focus();
        		}
    		});

    		$("#purchase-buyOrderDetail-notaxamount").change(function(){
        		if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
        			amountChange("2",this);
        		}
    		});
    		
    		$("#purchase-buyOrderDetail-boxprice").focus(function(){
    			if(!$(this).hasClass("readonly")){
            		$(this).select();
        		}else{
					$("#purchase-buyOrderDetail-remark").focus();
		   			$("#purchase-buyOrderDetail-remark").select();
        		}
    		});
    		
			$("#purchase-buyOrderDetail-boxprice").change(function(){
				if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
					boxpriceChange(this);
        		}
			});
	  		$("#purchase-buyOrderDetailAddPage-addSave").click(function(){
	  			saveOrderDetail(false);	
	  		});

	  		$("#purchase-buyOrderDetailAddPage-addSaveGoOn").click(function(){
	  			saveOrderDetail(true);
	  		});
	  		$("#purchase-buyOrderDetail-auxnum").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#purchase-buyOrderDetail-auxnum").blur();
		   			$("#purchase-buyOrderDetail-unitnum-auxremainder").focus();
		   			$("#purchase-buyOrderDetail-unitnum-auxremainder").select();
				}
		    });
	  		$("#purchase-buyOrderDetail-unitnum-auxremainder").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#purchase-buyOrderDetail-unitnum-auxremainder").blur();
		   			$("#purchase-buyOrderDetail-unitnum").focus();
		   			$("#purchase-buyOrderDetail-unitnum").select();
				}
		    });
	  		$("#purchase-buyOrderDetail-unitnum").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#purchase-buyOrderDetail-unitnum").blur();
		   			$("#purchase-buyOrderDetail-taxprice").focus();
				}
		    });
	  		$("#purchase-buyOrderDetail-taxprice").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#purchase-buyOrderDetail-taxprice").blur();
		   			$("#purchase-buyOrderDetail-taxamount").focus();
				}
		    });
	  		$("#purchase-buyOrderDetail-taxamount").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#purchase-buyOrderDetail-taxamount").blur()
					$("#purchase-buyOrderDetail-notaxprice").focus();
				}
		    });
	  		$("#purchase-buyOrderDetail-notaxprice").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#purchase-buyOrderDetail-notaxprice").blur();
					$("#purchase-buyOrderDetail-notaxamount").focus();
				}
		    });
	  		$("#purchase-buyOrderDetail-notaxamount").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#purchase-buyOrderDetail-notaxamount").blur()
					$("#purchase-buyOrderDetail-boxprice").focus();
				}
		    });
			$("#purchase-buyOrderDetail-boxprice").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#purchase-buyOrderDetail-boxprice").blur();
					$("#purchase-buyOrderDetail-remark").focus();
		   			$("#purchase-buyOrderDetail-remark").select();
				}
		    });
	  		$("#purchase-buyOrderDetail-remark").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#purchase-buyOrderDetail-remark").blur();
		   			$("#purchase-buyOrderDetailAddPage-addSaveGoOn").focus();
				}
		    });
	  		$("#purchase-buyOrderDetailAddPage-addSaveGoOn").die("keydown").live("keydown",function(event){
	  			if(event.keyCode==13){
	  				saveOrderDetail(true);
	  			}
	  		});
  		});
  	</script>
  </body>
</html>
