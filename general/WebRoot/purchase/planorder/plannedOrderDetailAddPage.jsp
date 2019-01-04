<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
  
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
		  	<form id="purchase-form-plannedOrderDetailAddPage" method="post">
		  		<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="text-align: right;">商品：</td>
						<td><input type="text" id="purchase-plannedOrderDetail-goodsid" name="goodsid" style="width:150px;" tabindex="1"/>
							<input type="hidden" id="purchase-plannedOrderDetail-goodsname" name="name"/>
						</td>
						<td colspan="2" id="purchase-loading-plannedOrderDetail"></td>	
					</tr>
					<tr>
						<td style="text-align: right;">辅数量：</td>
			   			<td style="text-align: left;">
		   					<input type="text" id="purchase-plannedOrderDetail-auxnum" name="auxnum" value="0" class="easyui-validatebox" validType="integer" data-options="required:true" style="width:60px; float:left;" />
		   					<span id="purchase-plannedOrderDetail-span-auxunitname" style="float: left;line-height:25px;">&nbsp;</span>
		   					<input type="text" id="purchase-plannedOrderDetail-unitnum-auxremainder" name="auxremainder" value="0" class="easyui-validatebox" validType="intOrFloatNum[${decimallen}]" required="true" style="width:60px;float:left;"/>
		   					<span id="purchase-plannedOrderDetail-span-unitname" style="float: left;line-height:25px;">&nbsp;</span>
		   					<input type="hidden" id="purchase-plannedOrderDetail-auxnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
			   			</td>
						<td style="text-align: right;">数量：</td>
						<td><input type="text" id="purchase-plannedOrderDetail-unitnum" name="unitnum" value="0" class="easyui-validatebox" validType="intOrFloatNum[${decimallen}]" required="true"  style="width:150px;" /></td>
					</tr>
					<tr>
		   				<td style="text-align: right;">单位：</td>
			    		<td style="float: left;">
			    			主：<input id="purchase-plannedOrderDetail-unitname" name="unitname" type="text" class="readonly2" style="width: 48px;" readonly="readonly" /><input id="purchase-plannedOrderDetail-unitid" type="hidden" name="unitid" />
			    			辅：<input id="purchase-plannedOrderDetail-auxunitname" name="auxunitname" type="text" class="readonly2" style="width: 48px;" readonly="readonly" /><input id="purchase-plannedOrderDetail-auxunitid" type="hidden" name="auxunitid" />
			    		</td>
			    		<td style="text-align: right;">箱装量：</td>
			    		<td>
			    			<input id="purchase-plannedOrderDetail-boxnum" type="text" class="len150 readonly" readonly="readonly" />
			    		</td>
		   			</tr>
					<tr>
						<td style="text-align: right;">商品品牌：</td>
						<td><input type="text" id="purchase-plannedOrderDetail-brand" readonly="readonly" style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
						<td style="text-align: right;">商品规格：</td>
						<td><input type="text" id="purchase-plannedOrderDetail-model" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">税种：</td>
						<td>
							<input type="text" id="purchase-plannedOrderDetail-taxname" name="taxtypename" readonly="readonly"  style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/>
							<input type="hidden" id="purchase-plannedOrderDetail-taxtype" name="taxtype" />
						</td>
						<td style="text-align: right;">税额：</td>
						<td><input type="text" id="purchase-plannedOrderDetail-tax" name="tax" class="easyui-numberbox" data-options="precision:6" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">含税单价：</td>
						<td>
							<input type="text" id="purchase-plannedOrderDetail-taxprice" name="taxprice" class="easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> style="width:150px;"/>
							<input type="hidden" id="purchase-plannedOrderDetail-hidtaxprice"/>
						</td>
						<td style="text-align: right;">含税金额：</td>
						<td><input type="text" id="purchase-plannedOrderDetail-taxamount" name="taxamount"  class="easyui-validatebox <c:if test="${colMap.taxamount == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxamount == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">未税单价：</td>
						<td>
							<input type="text" id="purchase-plannedOrderDetail-notaxprice" name="notaxprice" class="easyui-validatebox <c:if test="${colMap.notaxprice == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.notaxprice == null }">readonly="readonly"</c:if> style="width:150px;" />
						</td>
						<td style="text-align: right;">未税金额：</td>
						<td><input type="text" id="purchase-plannedOrderDetail-notaxamount" name="notaxamount"class="easyui-validatebox <c:if test="${colMap.taxamount == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxamount == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">含税箱价：</td>
						<td><input type="text" id="purchase-plannedOrderDetail-boxprice" name="boxprice" class="easyui-validatebox <c:if test="${colMap.notaxprice == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
						<td style="text-align: right;">未税箱价：</td>
						<td><input type="text" id="purchase-plannedOrderDetail-noboxprice" name="noboxprice" class="easyui-validatebox" <c:if test="${colMap.notaxprice == null }">readonly="readonly"</c:if> required="required" validType="intOrFloat"  <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if>  style="width:150px; border:1px solid #B3ADAB;"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">条形码：</td>
						<td><input type="text" id="purchase-plannedOrderDetail-barcode" readonly="readonly" style="width:150px;  border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
						<td style="text-align: right;">要求到货日期：</td>
						<td><input type="text" id="purchase-plannedOrderDetail-arrivedate" name="arrivedate" value="${arrivedate }" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"  class="Wdate" style="width:150px; " tabindex="3"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">备注：</td>
						<td colspan="3">
							<input type="text" id="purchase-plannedOrderDetail-remark" name="remark" style="width:405px;" tabindex="4"/>
						</td>
					</tr>
				</table>
				
				<input type="hidden" id="purchase-plannedOrderDetail-id" name="id" />
				<input type="hidden" name="goodsfield01" />
				<input type="hidden" name="goodsfield02" />
				<input type="hidden" name="goodsfield03" />
				<input type="hidden" name="goodsfield04" />
				<input type="hidden" name="goodsfield05" />
				<input type="hidden" name="goodsfield06" />
				<input type="hidden" name="goodsfield07" />
				<input type="hidden" name="goodsfield08" />
				<input type="hidden" name="goodsfield09" />
				<input type="hidden" name="boxnum" id="purchase-plannedOrderDetail-boxnum" />
		  	</form>
  		</div>
        <div data-options="region:'south',border:false">
            <div class="buttonDetailBG" style="text-align:right;">
	  			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span><input type="button" value="继续添加" name="savegoon" id="purchase-plannedOrderDetailAddPage-addSaveGoOn" />
	  			<input type="button" value="确 定" name="savenogo" id="purchase-plannedOrderDetailAddPage-addSave" />
  			</div>
	  	</div>
  	</div>
  	<script type="text/javascript">  		
  		var selectedGoodsid=getFilterGoodsid();

  		function getFilterGoodsid(){
  			var tmpgoodsid=[];
  			var goodsids="";
  			var selectDetailRow=[];
  	  		if($("#purchase-plannedOrderAddPage-plannedOrdertable").size()>0){
  	  			selectDetailRow=$("#purchase-plannedOrderAddPage-plannedOrdertable").datagrid('getRows');
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
  	  		var taxprice=$("#purchase-plannedOrderDetail-hidtaxprice").val();
  	  		var unitnum=$("#purchase-plannedOrderDetail-unitnum").val();
  	  		if(taxprice==null || taxprice=="" ){
  	  	  		taxprice="0";
  	  		}
  	  		if(unitnum==null || unitnum==""){
  	  	  		unitnum="0";
  	  		}
  	  		var goodsid = $("#purchase-plannedOrderDetail-goodsid").goodsWidget("getValue");
  	  		if(goodsid==null || goodsid==""){
  	  	  		return false;
  	  		}
  	  		var taxtype=$("#purchase-plannedOrderDetail-taxtype").val();

			$("#purchase-plannedOrderDetail-auxnum").addClass("inputload");
  	  		$("#purchase-plannedOrderDetail-auxremainder").addClass("inputload");
  	  		$("#purchase-plannedOrderDetail-unitnum").addClass("inputload");
  	  		try{
	  			$.ajax({   
		            url :'purchase/common/completeBuyDetailGoodsOrder.do',
		            type:'post',
		            dataType:'json',
			        async: false,
		            data:{goodsid:goodsid,taxprice:taxprice,unitnum:unitnum,taxtype:taxtype,businessdate:'${businessdate}'},
		            success:function(json){
		            	$("#purchase-plannedOrderDetail-taxname").val(json.taxtypename);
		            	$("#purchase-plannedOrderDetail-span-unitname").html(json.unitname);
			            $("#purchase-plannedOrderDetail-unitname").val(json.unitname);
		            	$("#purchase-plannedOrderDetail-auxunitid").val(json.auxunitid)
			            $("#purchase-plannedOrderDetail-auxunitname").val(json.auxunitname);
		            	$("#purchase-plannedOrderDetail-span-auxunitname").html(json.auxunitname);
			            $("#purchase-plannedOrderDetail-auxnumdetail").val(json.auxnumdetail);
			            
			            $("#purchase-plannedOrderDetail-notaxprice").val(json.notaxprice);
			            $("#purchase-plannedOrderDetail-taxprice").val(json.taxprice);
			            $("#purchase-plannedOrderDetail-boxprice").val(json.boxprice);
			            $("#purchase-plannedOrderDetail-noboxprice").val(json.noboxprice);
			            $("#purchase-plannedOrderDetail-tax").numberbox('setValue', json.tax);
			            $("#purchase-plannedOrderDetail-taxamount").val( json.taxamount);
			            $("#purchase-plannedOrderDetail-notaxamount").val( json.notaxamount);

		            	$("#purchase-plannedOrderDetail-auxnum").val(json.auxInteger);
		            	$("#purchase-plannedOrderDetail-unitnum-auxremainder").val(json.auxremainder);
			            
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
			            	$("#purchase-plannedOrderDetail-remark").val(htmlsb.join(''));
			            }
		            	if(json.auxrate!=null){
		            		$("#purchase-plannedOrderDetail-unitnum-auxremainder").attr("max",json.auxrate-1);
		            	}
		            	$("#purchase-plannedOrderDetail-auxnum").removeClass("inputload");
		      	  		$("#purchase-plannedOrderDetail-auxremainder").removeClass("inputload");
		      	  		$("#purchase-plannedOrderDetail-unitnum").removeClass("inputload");
		            	
		            }
		        });
  	  		}catch(e){
  	  		}
  		}
  		function orderDetailAddSaveGoOnDialog(){
  			var $DetailOper=$("#purchase-plannedOrderAddPage-dialog-DetailOper-content");
  	  		if($DetailOper.size()>0){
  				$DetailOper.dialog('close');
  	  		}
  		   	$('<div id="purchase-plannedOrderAddPage-dialog-DetailOper-content"></div>').appendTo('#purchase-plannedOrderAddPage-dialog-DetailOper');
  	  		$DetailOper=$("#purchase-plannedOrderAddPage-dialog-DetailOper-content");
  	  		
  			$DetailOper.dialog({
  				title:'商品信息新增(按ESC退出)',
  			    width: 600,  
  			    height: 400,
  			    closed: true,  
  			    cache: false, 
  			    modal: true,
  			    maximizable:true,
  			    href:"purchase/planorder/plannedOrderDetailAddPage.do?supplierid=${supplierid}&businessdate=${businessdate}",
			    onLoad:function(){
				    $("#purchase-plannedOrderDetail-goodsid").focus();
			    },
			    onClose:function(){
		            $DetailOper.dialog("destroy");
		        }
  			});
  			$DetailOper.dialog("open");
  		}
  		//通过总数量 计算数量 金额换算
		function computeNum(){
			var goodsInfo= $("#purchase-plannedOrderDetail-goodsid").goodsWidget("getObject");
			if(null==goodsInfo){
				return false;
			}
			var auxunitid = $("#purchase-plannedOrderDetail-auxunitid").val();
			var unitnum = $("#purchase-plannedOrderDetail-unitnum").val();
			var taxprice = $("#purchase-plannedOrderDetail-taxprice").val();
			if(taxprice==null || taxprice==""){
				taxprice="0";
			}
			var notaxprice = $("#purchase-plannedOrderDetail-notaxprice").val();
			var taxtype = $("#purchase-plannedOrderDetail-taxtype").val();
			
			$("#purchase-plannedOrderDetail-auxnum").addClass("inputload");
  	  		$("#purchase-plannedOrderDetail-auxremainder").addClass("inputload");
			$.ajax({   
	            url :'basefiles/computeGoodsByUnitnum.do',
	            type:'post',
	            data:{unitnum:unitnum,goodsid:goodsInfo.id,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#purchase-plannedOrderDetail-taxamount").val(json.taxamount);
	            	$("#purchase-plannedOrderDetail-notaxamount").val(json.notaxamount);
	            	$("#purchase-plannedOrderDetail-tax").numberbox("setValue",json.tax);
	            	$("#purchase-plannedOrderDetail-taxtypename").val(json.taxtypename);
	            	$("#purchase-plannedOrderDetail-auxnumdetail").val(json.auxnumdetail);
	            	$("#purchase-plannedOrderDetail-auxunitnum").val(json.auxnum);
	            	$("#purchase-plannedOrderDetail-auxunitname").val(json.auxunitname);
	            	$("#purchase-plannedOrderDetail-span-auxunitname").html(json.auxunitname);
	            	$("#purchase-plannedOrderDetail-unitname").val(json.unitname);
	            	$("#purchase-plannedOrderDetail-span-unitname").html(json.unitname);

	            	$("#purchase-plannedOrderDetail-notaxprice").val(json.notaxprice);
	            	$("#purchase-plannedOrderDetail-boxprice").val(json.boxprice);
	            	$("#purchase-plannedOrderDetail-noboxprice").val(json.noboxprice);
	            	$("#purchase-plannedOrderDetail-notaxamount").val(json.notaxamount);
	            	
	            	$("#purchase-plannedOrderDetail-auxnum").val(json.auxInteger);
	            	$("#purchase-plannedOrderDetail-unitnum-auxremainder").val(json.auxremainder);
	            	if(json.auxrate!=null){
	            		$("#purchase-plannedOrderDetail-unitnum-auxremainder").attr("max",json.auxrate-1);
	            	}
	            	$("#purchase-plannedOrderDetail-auxnum").removeClass("inputload");
	      	  		$("#purchase-plannedOrderDetail-auxremainder").removeClass("inputload");
	            	
	            }
	        });
		}
  	//通过辅单位数量
		function computeNumByAux(){
			var goodsInfo= $("#purchase-plannedOrderDetail-goodsid").goodsWidget("getObject");
			var auxunitid = $("#purchase-plannedOrderDetail-auxunitid").val();
			var taxprice = $("#purchase-plannedOrderDetail-taxprice").val();
			if(taxprice==null || taxprice==""){
				taxprice="0";
			}
			var notaxprice = $("#purchase-plannedOrderDetail-notaxprice").val();
			var taxtype = $("#purchase-plannedOrderDetail-taxtype").val();
			var auxInterger = $("#purchase-plannedOrderDetail-auxnum").val();
			var auxremainder = $("#purchase-plannedOrderDetail-unitnum-auxremainder").val();
			
			$("#purchase-plannedOrderDetail-auxnum").addClass("inputload");
  	  		$("#purchase-plannedOrderDetail-auxremainder").addClass("inputload");
  	  		$("#purchase-plannedOrderDetail-unitnum").addClass("inputload");
			$.ajax({   
	            url :'basefiles/computeGoodsByAuxnum.do',
	            type:'post',
	            data:{auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsInfo.id,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#purchase-plannedOrderDetail-taxamount").val(json.taxamount);
	            	$("#purchase-plannedOrderDetail-notaxamount").val(json.notaxamount);
	            	$("#purchase-plannedOrderDetail-tax").numberbox("setValue",json.tax);
	            	$("#purchase-plannedOrderDetail-taxtypename").val(json.taxtypename);
	            	
	            	//$("#purchase-plannedOrderDetail-taxprice").val(json.taxprice);
	            	$("#purchase-plannedOrderDetail-notaxprice").val(json.notaxprice);
	            	$("#purchase-plannedOrderDetail-notaxamount").val(json.notaxamount);
	            	
	            	$("#purchase-plannedOrderDetail-unitnum").val(json.mainnum);
                    $("#purchase-plannedOrderDetail-auxnum").val(json.auxInterger);
                    $("#purchase-plannedOrderDetail-unitnum-auxremainder").val(json.auxremainder);
                    $("#purchase-plannedOrderDetail-auxnumdetail").val(json.auxnumdetail);

	    			$("#purchase-plannedOrderDetail-auxnum").removeClass("inputload");
	      	  		$("#purchase-plannedOrderDetail-auxremainder").removeClass("inputload");
	      	  		$("#purchase-plannedOrderDetail-unitnum").removeClass("inputload");
	            }
	        });

		}

		function saveOrderDetail(isGoOn){
			if(isGoOn==null){
				isGoOn=false;
			}
	    	$("#purchase-plannedOrderDetail-remark").focus();
  			var flag=$("#purchase-form-plannedOrderDetailAddPage").form('validate');
  			if(!flag){
	  			return false;
  			}

  			var goodsInfo=$("#purchase-plannedOrderDetail-goodsid").goodsWidget('getObject');
			
			if(goodsInfo==null || goodsInfo==""){
				$.messager.alert("提醒","抱歉，请选择商品！");
				return false;		  			
  			}
			
			<c:if test="${isrepeat == '0'}">
			if(!checkAfterAddGoods(goodsInfo.id)){
				$.messager.alert("提醒","抱歉，采购计划单中已经存在该商品！");
				return false;
			}
			</c:if>
			
			var unitnum=$("#purchase-plannedOrderDetail-unitnum").val();
			if(unitnum==null || unitnum=="" || unitnum==0){
				$.messager.alert("提醒","抱歉，请填写数量！");
				return false;
			}

			var formdata=$("#purchase-form-plannedOrderDetailAddPage").serializeJSON();
			formdata.goodsInfo = goodsInfo;
			if(formdata){
				var index=getAddRowIndex();
				$("#purchase-plannedOrderAddPage-plannedOrdertable").datagrid('updateRow',{
					index:index,
					row:formdata
				});
				footerReCalc();
				if(index>=14){
					var rows=$("#purchase-plannedOrderAddPage-plannedOrdertable").datagrid('getRows');
					if(index == rows.length - 1){
						$("#purchase-plannedOrderAddPage-plannedOrdertable").datagrid('appendRow',{});
					}
				}
			}
			if(isGoOn){
				orderDetailAddSaveGoOnDialog();
			}else{
	  			$("#purchase-plannedOrderAddPage-dialog-DetailOper-content").dialog("close");	
			}
		}
		function priceChange(type, id){ //1含税单价或2未税单价改变计算对应数据
    		var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
    		var price = $(id).val();
    		var goodsid = $("#purchase-plannedOrderDetail-goodsid").goodsWidget("getValue");
  	  		if(goodsid==null || goodsid==""){
  	  	  		return false;
  	  		}
    		var taxtype = $("#purchase-plannedOrderDetail-taxtype").val();
    		var unitnum = $("#purchase-plannedOrderDetail-unitnum").val();
    		var auxnum = $("#purchase-plannedOrderDetail-auxnum").val();
    		$.ajax({
    			url:'purchase/common/getAmountChanger.do',
    			dataType:'json',
    			async:false,
    			type:'post',
    			data:'type='+ type +'&price='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsid,
    			success:function(json){
    				$("#purchase-plannedOrderDetail-taxprice").val(json.taxPrice);
    				$("#purchase-plannedOrderDetail-boxprice").val(json.boxprice);
	            	$("#purchase-plannedOrderDetail-noboxprice").val(json.noboxprice);
    				$("#purchase-plannedOrderDetail-taxamount").val(json.taxAmount);
    				$("#purchase-plannedOrderDetail-notaxprice").val(json.noTaxPrice);
    				$("#purchase-plannedOrderDetail-notaxamount").val(json.noTaxAmount);
    				$("#purchase-plannedOrderDetail-tax").numberbox('setValue',json.tax);
    				$this.css({'background':''});
    			}
    		});
    	}
		function boxpriceChange(id,type){ //type:1含税箱价 2未税箱价
            var $this = $(id);
            $this.css({'background':'url(image/loading.gif) right top no-repeat'});
    		var price = $(id).val();
    		var goodsid = $("#purchase-plannedOrderDetail-goodsid").goodsWidget("getValue");
  	  		if(goodsid==null || goodsid==""){
  	  	  		return false;
  	  		}
    		var taxtype = $("#purchase-plannedOrderDetail-taxtype").val();
    		var unitnum = $("#purchase-plannedOrderDetail-unitnum").val();
    		var auxnum = $("#purchase-plannedOrderDetail-auxnum").val();
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
					"type": type
				},
    			success:function(json){
    				$("#purchase-plannedOrderDetail-taxprice").val(json.taxprice);
	            	$("#purchase-plannedOrderDetail-noboxprice").val(json.noboxprice);
					$("#purchase-plannedOrderDetail-boxprice").val(json.boxprice);
    				$("#purchase-plannedOrderDetail-taxamount").val(json.taxamount);
    				$("#purchase-plannedOrderDetail-notaxprice").val(json.notaxprice);
    				$("#purchase-plannedOrderDetail-notaxamount").val(json.notaxamount);
    				$("#purchase-plannedOrderDetail-tax").numberbox('setValue',json.tax);
    				$this.css({'background':''});
    			}
    		});
		}
		function amountChange(type, id){ //1含税金额或2未税金额改变计算对应数据
			var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
			var price = $(id).val();

			var goodsInfo= $("#purchase-plannedOrderDetail-goodsid").goodsWidget("getObject");
			if(null==goodsInfo && goodsInfo.id!=null && goodsInfo.id!=""){
				return false;
			}
			var taxtype = $("#purchase-plannedOrderDetail-taxtype").val();
			var unitnum = $("#purchase-plannedOrderDetail-unitnum").val();
			var auxnum = $("#purchase-plannedOrderDetail-auxnum").val();
			$.ajax({
				url:'purchase/common/getPriceChanger.do',
				dataType:'json',
				async:false,
				type:'post',
				data:'type='+ type +'&amount='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsInfo.id,
				success:function(json){
					$("#purchase-plannedOrderDetail-taxprice").val(json.taxPrice);
					$("#purchase-plannedOrderDetail-boxprice").val(json.boxprice);
					$("#purchase-plannedOrderDetail-noboxprice").val(json.noboxprice);
					$("#purchase-plannedOrderDetail-taxamount").val(json.taxAmount);
					$("#purchase-plannedOrderDetail-notaxprice").val(json.noTaxPrice);
					$("#purchase-plannedOrderDetail-notaxamount").val(json.noTaxAmount);
					$("#purchase-plannedOrderDetail-tax").numberbox('setValue',json.tax);
					$this.css({'background':''});
				}
			});
		}
  		$(document).ready(function(){
            $("#purchase-plannedOrderDetail-arrivedate").click(function(){
                if(!$("#purchase-plannedOrderDetail-arrivedate").hasClass("WdateRead")){
                    WdatePicker({dateFmt:'yyyy-MM-dd'});
                }
            });

  			$("#purchase-plannedOrderDetail-goodsid").goodsWidget({
    			name:'purchase_plannedorder_detail',
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
    					<c:if test="${isrepeat == '0'}">//1允许重复0不允许重复
        				if(!checkAfterAddGoods(data.id)){
        					$.messager.alert("提醒","抱歉，采购计划单已经存在该商品！");
            				return false;
        				}
    					</c:if>
    		  			$("#purchase-loading-plannedOrderDetail").html("商品编码：<font color='green'>"+data.id+"</font>");	
        				
        				$("#purchase-plannedOrderDetail-goodsname").val(data.name);
        				$("#purchase-plannedOrderDetail-brand").val(data.brandName);
        				$("#purchase-plannedOrderDetail-model").val(data.model);
        				$("#purchase-plannedOrderDetail-unitid").val(data.mainunit);
        				$("#purchase-plannedOrderDetail-barcode").val(data.barcode);
        				$("#purchase-plannedOrderDetail-taxtype").val(data.defaulttaxtype);
        				$("#purchase-plannedOrderDetail-boxnum").val(formatterBigNum(data.boxnum));
        				$("#purchase-form-plannedOrderAddPage input[name='goodsfield01']").val(data.field01);
        				$("#purchase-form-plannedOrderAddPage input[name='goodsfield02']").val(data.field02);
        				$("#purchase-form-plannedOrderAddPage input[name='goodsfield03']").val(data.field03);
        				$("#purchase-form-plannedOrderAddPage input[name='goodsfield04']").val(data.field04);
        				$("#purchase-form-plannedOrderAddPage input[name='goodsfield05']").val(data.field05);
        				$("#purchase-form-plannedOrderAddPage input[name='goodsfield06']").val(data.field06);
        				$("#purchase-form-plannedOrderAddPage input[name='goodsfield07']").val(data.field07);
        				$("#purchase-form-plannedOrderAddPage input[name='goodsfield08']").val(data.field08);
        				$("#purchase-form-plannedOrderAddPage input[name='goodsfield09']").val(data.field09);

			            
			            <c:choose>
	    			  		<c:when test="${purchasePriceType == 1}">
	    			  			$("#purchase-plannedOrderDetail-hidtaxprice").val(data.highestbuyprice);       			  			
	    			  		</c:when>
	    			  		<c:when test="${purchasePriceType == 2}">
					            $("#purchase-plannedOrderDetail-hidtaxprice").val(data.newbuyprice);
				  			</c:when>
	    			  		<c:otherwise>
	    			  			$("#purchase-plannedOrderDetail-hidtaxprice").val(data.highestbuyprice);
			            	</c:otherwise>
	    			  	</c:choose>
    			  	
			            $("#purchase-plannedOrderDetail-boxnum").val(data.boxnum);
        				detailRemoteCompleteOrder();

    		   			$("#purchase-plannedOrderDetail-auxnum").focus();
    		   			$("#purchase-plannedOrderDetail-auxnum").select();
    				}
    			}
    		});

    		$("#purchase-plannedOrderDetail-unitnum").change(function(){
    			if($(this).validatebox('isValid')){
    				computeNum();
    			}
    		});
    		$("#purchase-plannedOrderDetail-auxnum").change(function(){
        		if($(this).validatebox('isValid')){
    				computeNumByAux();
        		}
    		});
    		$("#purchase-plannedOrderDetail-unitnum-auxremainder").change(function(){
        		if($(this).validatebox('isValid')){
    				computeNumByAux();
        		}
    		});

    		$("#purchase-plannedOrderDetail-taxprice").focus(function(){
        		if(!$(this).hasClass("readonly")){
            		$(this).select();
        		}else{
        			$("#purchase-plannedOrderDetail-taxamount").focus();
        		}
    		});
    		
    		$("#purchase-plannedOrderDetail-taxprice").change(function(){
        		if($(this).validatebox('isValid') && !$(this).hasClass("readonly")){
        			priceChange("1",this);
        		}
    		});

			$("#purchase-plannedOrderDetail-taxamount").focus(function(){
        		if(!$(this).hasClass("readonly")){
            		$(this).select();
        		}else{
        			$("#purchase-plannedOrderDetail-notaxprice").focus();
        		}
    		});

    		
    		$("#purchase-plannedOrderDetail-taxamount").change(function(){
        		if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
        			amountChange("1",this);
        		}
    		});
    		$("#purchase-plannedOrderDetail-notaxprice").focus(function(){
        		if(!$(this).hasClass("readonly")){
            		$(this).select();
        		}else{
        			$("#purchase-plannedOrderDetail-notaxamount").focus();
        		}
    		});

    		$("#purchase-plannedOrderDetail-notaxprice").change(function(){
        		if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
        			priceChange("2",this);
        		}
    		});
    		
    		$("#purchase-plannedOrderDetail-notaxamount").focus(function(){
        		if(!$(this).hasClass("readonly")){
            		$(this).select();
        		}else{
        			$("#purchase-plannedOrderDetail-boxprice").focus();
        		}
    		});

    		$("#purchase-plannedOrderDetail-notaxamount").change(function(){
        		if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
        			amountChange("2",this);
        		}
    		});
    		
    		$("#purchase-plannedOrderDetail-boxprice").focus(function(){
    			if(!$(this).hasClass("readonly")){
            		$(this).select();
        		}else{
					$("#purchase-plannedOrderDetail-noboxprice").focus();
		   			$("#purchase-plannedOrderDetail-noboxprice").select();
        		}
    		});
    		
			$("#purchase-plannedOrderDetail-boxprice").change(function(){
				if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
					boxpriceChange(this,"1");
        		}
			});

			$("#purchase-plannedOrderDetail-noboxprice").focus(function(){
				if(!$(this).hasClass("readonly")){
					$(this).select();
				}else{
					$("#purchase-plannedOrderDetail-remark").focus();
					$("#purchase-plannedOrderDetail-remark").select();
				}
			});

			$("#purchase-plannedOrderDetail-noboxprice").change(function(){
				if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
					boxpriceChange(this,"2");
				}
			});
			
	  		$("#purchase-plannedOrderDetailAddPage-addSave").click(function(){
	  			saveOrderDetail(false);		
	  		});

	  		$("#purchase-plannedOrderDetailAddPage-addSaveGoOn").click(function(){
	  			saveOrderDetail(true);
	  		});
	  		$("#purchase-plannedOrderDetail-auxnum").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#purchase-plannedOrderDetail-auxnum").blur();
		   			$("#purchase-plannedOrderDetail-unitnum-auxremainder").focus();
		   			$("#purchase-plannedOrderDetail-unitnum-auxremainder").select();
				}
		    });
	  		$("#purchase-plannedOrderDetail-unitnum-auxremainder").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#purchase-plannedOrderDetail-unitnum-auxremainder").blur();
		   			$("#purchase-plannedOrderDetail-unitnum").focus();
		   			$("#purchase-plannedOrderDetail-unitnum").select();
				}
		    });
	  		$("#purchase-plannedOrderDetail-unitnum").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#purchase-plannedOrderDetail-unitnum").blur();
		   			$("#purchase-plannedOrderDetail-taxprice").focus();
				}
		    });
	  		$("#purchase-plannedOrderDetail-taxprice").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#purchase-plannedOrderDetail-taxprice").blur();
		   			$("#purchase-plannedOrderDetail-taxamount").focus();
				}
		    });
	  		$("#purchase-plannedOrderDetail-taxamount").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#purchase-plannedOrderDetail-taxamount").blur()
					$("#purchase-plannedOrderDetail-notaxprice").focus();
				}
		    });
	  		$("#purchase-plannedOrderDetail-notaxprice").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#purchase-plannedOrderDetail-notaxprice").blur()
					$("#purchase-plannedOrderDetail-remark").focus();
		   			$("#purchase-plannedOrderDetail-remark").select();
				}
		    });
	  		$("#purchase-plannedOrderDetail-remark").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#purchase-plannedOrderDetail-remark").blur();
		   			$("#purchase-plannedOrderDetailAddPage-addSaveGoOn").focus();
				}
		    });
	  		$("#purchase-plannedOrderDetailAddPage-addSaveGoOn").die("keydown").live("keydown",function(event){
	  			if(event.keyCode==13){
	  				saveOrderDetail(true);
	  			}
	  		});
	  		
  		});
  	</script>
  </body>
</html>
