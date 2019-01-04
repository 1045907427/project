<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
		  	<form id="purchase-form-arrivalOrderDetailAddPage" method="post">
		  		<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="text-align: right;">商品：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-goodsid" name="goodsid" style="width:150px;" tabindex="1"/>
							<input type="hidden" id="purchase-arrivalOrderDetail-goodsname" name="name"/>
						</td>
						<td colspan="2" id="purchase-loading-arrivalOrderDetail"></td>				
					</tr>
					<tr>
						<td>辅数量：</td>
			   			<td style="text-align: left;">
		   					<input type="text" id="purchase-arrivalOrderDetail-auxnum" name="auxnum" value="0" class="easyui-validatebox" validType="integer" data-options="required:true" style="width:60px; float:left;" />
		   					<span id="purchase-arrivalOrderDetail-span-auxunitname" style="float: left;line-height:25px;">&nbsp;</span>
		   					<input type="text" id="purchase-arrivalOrderDetail-unitnum-auxremainder" name="auxremainder" value="0" class="easyui-validatebox" validType="intOrFloatNum[${decimallen}]" data-options="required:true" style="width:60px;float:left;"/>
		   					<span id="purchase-arrivalOrderDetail-span-unitname" style="float: left;line-height:25px;">&nbsp;</span>
		   					<input type="hidden" id="purchase-arrivalOrderDetail-auxnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
			   			</td>
						<td style="text-align: right;">数量：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-unitnum" name="unitnum" value="0" class="easyui-validatebox" validType="intOrFloatNum[${decimallen}]" required="true"  style="width:150px;" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">辅计量：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-auxunitname" name="auxunitname" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/><input type="hidden" id="purchase-arrivalOrderDetail-auxunitid" name="auxunitid"  style="width:150px;"/></td>
						<td style="text-align: right;">主单位：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-unitname" name="unitname" readonly="readonly"  style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;"/><input type="hidden" id="purchase-arrivalOrderDetail-unitid" name="unitid"  style="width:150px;"/></td>				
					</tr>
					<tr>
						<td style="text-align: right;">商品品牌：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-brand" name="brandName" readonly="readonly" style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
						<td style="text-align: right;">商品规格：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-model" name="model" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">税种：</td>
						<td>
							<input type="text" id="purchase-arrivalOrderDetail-taxname" name="taxtypename" readonly="readonly"  style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/>
							<input type="hidden" id="purchase-arrivalOrderDetail-taxtype" name="taxtype" />
						</td>
						<td style="text-align: right;">税额：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-tax" name="tax" class="easyui-numberbox" data-options="precision:6" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">含税单价：</td>
						<td>
							<input type="text" id="purchase-arrivalOrderDetail-taxprice" name="taxprice" class="easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> style="width:150px;"/>
							<input type="hidden" id="purchase-arrivalOrderDetail-hidtaxprice"/>
						</td>
						<td style="text-align: right;">含税金额：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-taxamount" name="taxamount" class="easyui-numberbox" data-options="precision:6" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">未税单价：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-notaxprice" name="notaxprice"  class="easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.notaxprice == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
						<td style="text-align: right;">未税金额：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-notaxamount" name="notaxamount" class="easyui-numberbox" data-options="precision:6" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">条形码：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-barcode" name="barcode" readonly="readonly" style="width:150px;  border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
						<td style="text-align: right;">批次号：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-batchno" name="batchno"  maxlength="20" style="width:150px; " tabindex="3"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">生产日期：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-produceddate" name="produceddate" readonly="readonly" style="width:150px; " tabindex="4"/></td>
						<td style="text-align: right;">有效截止日期：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-deadline" name="deadline" readonly="readonly" style="width:150px; " tabindex="5"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">备注：</td>
						<td colspan="3">
							<input type="text" id="purchase-arrivalOrderDetail-remark" name="remark" style="width:420px;" tabindex="6"/>
						</td>
					</tr>
				</table>		
				<input type="hidden" id="purchase-arrivalOrderDetail-id" name="id" />
				<input type="hidden" name="goodsfield01" />
				<input type="hidden" name="goodsfield02" />
				<input type="hidden" name="goodsfield03" />
				<input type="hidden" name="goodsfield04" />
				<input type="hidden" name="goodsfield05" />
				<input type="hidden" name="goodsfield06" />
				<input type="hidden" name="goodsfield07" />
				<input type="hidden" name="goodsfield08" />
				<input type="hidden" name="goodsfield09" />
				<input type="hidden" id="purchase-arrivalOrderDetail-boxnum" name="boxnum" />
		  	</form>		  	
  		</div>
      <div data-options="region:'south',border:false">
          <div class="buttonDetailBG" style="height:30px;text-align:right;">
	  			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span><input type="button" value="继续添加" name="savegoon" id="purchase-arrivalOrderDetailAddPage-addSaveGoOn" />
	  			<input type="button" value="确 定" name="savenogo" id="purchase-arrivalOrderDetailAddPage-addSave" />
  			</div>
	  	</div>
  	</div>
  	<script type="text/javascript">

		var selectedGoodsid=getFilterGoodsid();

		function getFilterGoodsid(){
			var tmpgoodsid=[];
			var goodsids="";
			var selectDetailRow=[];
	  		if($("#purchase-arrivalOrderAddPage-arrivalOrdertable").size()>0){
	  			selectDetailRow=$("#purchase-arrivalOrderAddPage-arrivalOrdertable").datagrid('getRows');
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
  	  		var taxprice=$("#purchase-arrivalOrderDetail-hidtaxprice").val();
  	  		var unitnum=$("#purchase-arrivalOrderDetail-unitnum").val();
  	  		if(taxprice==null || taxprice=="" ){
  	  	  		taxprice="0";
  	  		}
  	  		if(unitnum==null || unitnum==""){
  	  	  		unitnum="0";
  	  		}
  	  		var goodsid = $("#purchase-arrivalOrderDetail-goodsid").goodsWidget("getValue");
  	  		if(goodsid==null || goodsid==""){
  	  	  		return false;
  	  		}
  	  		var taxtype=$("#purchase-arrivalOrderDetail-taxtype").val();
  	  		
  	  		$("#purchase-arrivalOrderDetail-auxnum").addClass("inputload");
  	  		$("#purchase-arrivalOrderDetail-auxremainder").addClass("inputload");
  	  		$("#purchase-arrivalOrderDetail-unitnum").addClass("inputload");
  	  		
  	  		try{
	  			$.ajax({   
		            url :'purchase/common/completeBuyDetailGoodsOrder.do',
		            type:'post',
		            dataType:'json',
			        async: false,
		            data:{goodsid:goodsid,taxprice:taxprice,unitnum:unitnum,taxtype:taxtype,businessdate:'${businessdate}'},
		            success:function(json){
		            	$("#purchase-arrivalOrderDetail-taxname").val(json.taxtypename);
			            $("#purchase-arrivalOrderDetail-unitname").val(json.unitname);
		            	$("#purchase-arrivalOrderDetail-span-unitname").html(json.unitname);
						$("#purchase-arrivalOrderDetail-span-auxunitname").html(json.auxunitname);
		      	  		$("#purchase-arrivalOrderDetail-auxunitid").val(json.auxunitid);
			            $("#purchase-arrivalOrderDetail-auxunitname").val(json.auxunitname);
			            $("#purchase-arrivalOrderDetail-auxnumdetail").val(json.auxnumdetail);
			            $("#purchase-arrivalOrderDetail-auxnum").val(json.auxnum);
			            $("#purchase-arrivalOrderDetail-notaxprice").val(json.notaxprice);
			            $("#purchase-arrivalOrderDetail-taxprice").val(json.taxprice);
			            $("#purchase-arrivalOrderDetail-tax").numberbox('setValue', json.tax);
			           	$("#purchase-arrivalOrderDetail-taxamount").numberbox('setValue', json.taxamount);
			            $("#purchase-arrivalOrderDetail-notaxamount").numberbox('setValue', json.notaxamount);
			            
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
			            	$("#purchase-arrivalOrderDetail-remark").val(htmlsb.join(''));
			            }
			            $("#purchase-arrivalOrderDetail-auxnum").val(json.auxInteger);
		            	$("#purchase-arrivalOrderDetail-unitnum-auxremainder").val(json.auxremainder);
		            	if(json.auxrate!=null){
		            		$("#purchase-arrivalOrderDetail-unitnum-auxremainder").attr("max",json.auxrate-1);
		            	}

		            	$("#purchase-arrivalOrderDetail-auxnum").removeClass("inputload");
		      	  		$("#purchase-arrivalOrderDetail-auxremainder").removeClass("inputload");
		      	  		$("#purchase-arrivalOrderDetail-unitnum").removeClass("inputload");
		            }
		        });
  	  		}catch(e){
  	  		}
  		}
  		function orderDetailAddSaveGoOnDialog(){
  			var $DetailOper=$("#purchase-returnCheckOrderAddPage-dialog-DetailOper-content");
  	  		if($DetailOper.size()>0){
  				$DetailOper.dialog('close');
  	  		}
  		   	$('<div id="purchase-returnCheckOrderAddPage-dialog-DetailOper-content"></div>').appendTo('#purchase-returnOrderAddPage-dialog-DetailOper');
  	  		$DetailOper=$("#purchase-returnCheckOrderAddPage-dialog-DetailOper-content");
  	  		
  			$DetailOper.dialog({
  				title:'商品信息新增(按ESC退出)',
  			    width: 600,  
  			    height: 440,
  			    closed: true,  
  			    cache: false, 
  			    modal: true,
  			    maximizable:true,
  			    href:"purchase/arrivalorder/arrivalOrderDetailAddPage.do?supplierid=${supplierid}&businessdate=${businessdate}",
			    onLoad:function(){
			    	$("#purchase-arrivalOrderDetail-goodsid").focus();
		    	},
			    onClose:function(){
		            $DetailOper.dialog("destroy");
		        }
  			});
  			$DetailOper.dialog("open");
  		}
  		//通过总数量 计算数量 金额换算
		function computeNum(){
			var goodsInfo= $("#purchase-arrivalOrderDetail-goodsid").goodsWidget("getObject");
			if(null==goodsInfo){
				return false;
			}
			var auxunitid = $("#purchase-arrivalOrderDetail-auxunitid").val();
			var unitnum = $("#purchase-arrivalOrderDetail-unitnum").val();
			var taxprice = $("#purchase-arrivalOrderDetail-taxprice").val();
			if(taxprice==null || taxprice==""){
				taxprice="0";
			}
			var notaxprice = $("#purchase-arrivalOrderDetail-notaxprice").val();
			var taxtype = $("#purchase-arrivalOrderDetail-taxtype").val();


			$("#purchase-arrivalOrderDetail-auxnum").addClass("inputload");
  	  		$("#purchase-arrivalOrderDetail-auxremainder").addClass("inputload");
			$.ajax({   
	            url :'basefiles/computeGoodsByUnitnum.do',
	            type:'post',
	            data:{unitnum:unitnum,goodsid:goodsInfo.id,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#purchase-arrivalOrderDetail-taxamount").numberbox("setValue",json.taxamount);
	            	$("#purchase-arrivalOrderDetail-notaxamount").numberbox("setValue",json.notaxamount);
	            	$("#purchase-arrivalOrderDetail-tax").numberbox("setValue",json.tax);
	            	$("#purchase-arrivalOrderDetail-taxtypename").val(json.taxtypename);
	            	$("#purchase-arrivalOrderDetail-auxnumdetail").val(json.auxnumdetail);
	            	$("#purchase-arrivalOrderDetail-auxunitnum").val(json.auxnum);
	            	$("#purchase-arrivalOrderDetail-auxunitname").val(json.auxunitname);
	            	$("#purchase-arrivalOrderDetail-span-auxunitname").html(json.auxunitname);
	            	$("#purchase-arrivalOrderDe-unitnamename").val(json.unitname);
	            	$("#purchase-arrivalOrderDetail-span-unitname").html(json.unitname);
	            	
	            	$("#purchase-arrivalOrderDetail-notaxamount").numberbox("setValue",json.notaxamount);
	            	
	            	$("#purchase-arrivalOrderDetail-auxnum").val(json.auxInteger);
	            	$("#purchase-arrivalOrderDetail-unitnum-auxremainder").val(json.auxremainder);
	            	if(json.auxrate!=null){
	            		$("#purchase-arrivalOrderAddPag-unitnum-auxremainder").attr("max",json.auxrate-1);
	            	}
	            	$("#purchase-arrivalOrderDetail-auxnum").removeClass("inputload");
	      	  		$("#purchase-arrivalOrderDetail-auxremainder").removeClass("inputload");
	            }
	        });
		}
  	//通过辅单位数量
		function computeNumByAux(){
			var goodsInfo= $("#purchase-arrivalOrderDetail-goodsid").goodsWidget("getObject");
			var auxunitid = $("#purchase-arrivalOrderDetail-auxunitid").val();
			var taxprice = $("#purchase-arrivalOrderDetail-taxprice").val();
			if(taxprice==null || taxprice==""){
				taxprice="0";
			}
			var notaxprice = $("#purchase-arrivalOrderDetail-notaxprice").val();
			var taxtype = $("#purchase-arrivalOrderDetail-taxtype").val();
			var auxInterger = $("#purchase-arrivalOrderDetail-auxnum").val();
			var auxremainder = $("#purchase-arrivalOrderDetail-unitnum-auxremainder").val();
			
			$("#purchase-arrivalOrderDetail-auxnum").addClass("inputload");
  	  		$("#purchase-arrivalOrderDetail-auxremainder").addClass("inputload");
  	  		$("#purchase-arrivalOrderDetail-unitnum").addClass("inputload");
			$.ajax({   
	            url :'basefiles/computeGoodsByAuxnum.do',
	            type:'post',
	            data:{auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsInfo.id,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#purchase-arrivalOrderDetail-taxamount").numberbox("setValue",json.taxamount);
	            	$("#purchase-arrivalOrderDetail-notaxamount").numberbox("setValue",json.notaxamount);
	            	$("#purchase-arrivalOrderDetail-tax").numberbox("setValue",json.tax);
	            	$("#purchase-arrivalOrderDetail-taxtypename").val(json.taxtypename);
	            	
	            	$("#purchase-arrivalOrderDetail-taxprice").val(json.taxprice);
	            	$("#purchase-arrivalOrderDetail-notaxprice").val(json.notaxprice);
	            	$("#purchase-arrivalOrderDetail-notaxamount").numberbox("setValue",json.notaxamount);
	            	
	            	$("#purchase-arrivalOrderDetail-unitnum").val(json.mainnum);
	            	
	            	$("#purchase-arrivalOrderDetail-auxnum").removeClass("inputload");
	      	  		$("#purchase-arrivalOrderDetail-auxremainder").removeClass("inputload");
	      	  		$("#purchase-arrivalOrderDetail-unitnum").removeClass("inputload");
	            }
	        });
	        var auxunitname = $("#purchase-arrivalOrderDetail-auxunitname").val();
	        var unitname = $("#purchase-arrivalOrderDetail-unitname").val();
	        var auxdetail = auxInterger+auxunitname;
	        if(auxremainder>0){
	       		auxdetail += auxremainder+unitname;
	        }
	        $("#purchase-arrivalOrderDetail-auxnumdetail").val(auxdetail);
		}
		function saveOrderDetail(isGoOn){
	    	$("#purchase-arrivalOrderDetail-remark").focus();
  			var flag=$("#purchase-form-arrivalOrderDetailAddPage").form('validate');
  			if(!flag){
	  			return false;
  			}

  			var goodsid=$("#purchase-arrivalOrderDetail-goodsid").goodsWidget('getValue');
  			if(goodsid==null || goodsid==""){
				$.messager.alert("提醒","抱歉，请选择商品！");
				return false;		  			
  			}

  			if(!checkAfterAddGoods(goodsid)){
				$.messager.alert("提醒","抱歉，采购进货单中已经存在该商品！");
				return false;
			}

			var unitnum=$("#purchase-arrivalOrderDetail-unitnum").val();
			if(unitnum==null || unitnum=="" || unitnum==0){
				$.messager.alert("提醒","抱歉，请填写数量！");
				return false;
			}

			var formdata=$("#purchase-form-arrivalOrderDetailAddPage").serializeJSON();
			//保存前判断单价*数量 是否跟金额一致
			var taxprice = formdata.taxamount/formdata.unitnum;
			if(Number(taxprice).toFixed(2) != Number(formdata.taxprice).toFixed(2)){
				$.messager.alert("提醒","单价*数量与金额不一致，无法保存！");
				return false;
			}
			if(formdata){
				var index=getAddRowIndex();
				$("#purchase-arrivalOrderAddPage-arrivalOrdertable").datagrid('updateRow',{
					index:index,
					row:formdata
				});
				footerReCalc();
			}
			if(isGoOn){
				orderDetailAddSaveGoOnDialog();
			}else{
	  			$("#purchase-arrivalOrderAddPage-dialog-DetailOper-content").dialog("close");	
			}		
		}
		function priceChange(type, id){ //1含税单价或2未税单价改变计算对应数据
    		var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
    		var price = $(id).val();
    		var goodsid = $("#purchase-arrivalOrderDetail-goodsid").goodsWidget("getValue");
  	  		if(goodsid==null || goodsid==""){
  	  	  		return false;
  	  		}
    		var taxtype = $("#purchase-arrivalOrderDetail-taxtype").val();
    		var unitnum = $("#purchase-arrivalOrderDetail-unitnum").val();
    		var auxnum = $("#purchase-arrivalOrderDetail-auxnum").val();
    		$.ajax({
    			url:'purchase/common/getAmountChanger.do',
    			dataType:'json',
    			async:false,
    			type:'post',
    			data:'type='+ type +'&price='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsid,
    			success:function(json){
    				$("#purchase-arrivalOrderDetail-taxprice").val(json.taxPrice);
    				$("#purchase-arrivalOrderDetail-taxamount").numberbox('setValue',json.taxAmount);
    				$("#purchase-arrivalOrderDetail-notaxprice").val(json.noTaxPrice);
    				$("#purchase-arrivalOrderDetail-notaxamount").numberbox('setValue',json.noTaxAmount);
    				$("#purchase-arrivalOrderDetail-tax").numberbox('setValue',json.tax);
    				$this.css({'background':''});
    			}
    		});
    	}
  		$(document).ready(function(){
  			$("#purchase-arrivalOrderDetail-goodsid").goodsWidget({
    			name:'t_purchase_arrivalorder_detail',
    			col:'goodsid',
    			singleSelect:true,
    			width:150,
    			queryAllBySupplier:'${supplierid}',<%--关联出此供应商所有商品--%>
    			canBuySale:'1',<%--购销类型，可采购（购销、可购）--%>
        		param:[
                  		<%--通用版需要通过默认供应商和第二供应商查询
                  		{field:'defaultsupplier',op:'equal',value:'${supplierid}'}, --%>
                  		{field:'id',op:'notin',value:selectedGoodsid}
                  		<%--,{field:'isinoutstorage',op:'equal',value:'1'} 通用版只用到购销类型--%>
                ],
    			onSelect : function(data){
    				if(data){
    		  			if(!checkAfterAddGoods(data.id)){
    						$.messager.alert("提醒","抱歉，采购进货单中已经存在该商品！");
    	    				return false;
    					}
    		  			$("#purchase-loading-arrivalOrderDetail").html("商品编码：<font color='green'>"+data.id+"</font>");
        				$("#purchase-arrivalOrderDetail-goodsname").val(data.name);
        				$("#purchase-arrivalOrderDetail-brand").val(data.brandName);
        				$("#purchase-arrivalOrderDetail-model").val(data.model);
        				$("#purchase-arrivalOrderDetail-unitid").val(data.mainunit);
        				$("#purchase-arrivalOrderDetail-barcode").val(data.barcode);
        				$("#purchase-arrivalOrderDetail-taxtype").val(data.defaulttaxtype);
        				$("#purchase-form-arrivalOrderAddPage input[name='goodsfield01']").val(data.field01);
        				$("#purchase-form-arrivalOrderAddPage input[name='goodsfield02']").val(data.field02);
        				$("#purchase-form-arrivalOrderAddPage input[name='goodsfield03']").val(data.field03);
        				$("#purchase-form-arrivalOrderAddPage input[name='goodsfield04']").val(data.field04);
        				$("#purchase-form-arrivalOrderAddPage input[name='goodsfield05']").val(data.field05);
        				$("#purchase-form-arrivalOrderAddPage input[name='goodsfield06']").val(data.field06);
        				$("#purchase-form-arrivalOrderAddPage input[name='goodsfield07']").val(data.field07);
        				$("#purchase-form-arrivalOrderAddPage input[name='goodsfield08']").val(data.field08);
        				$("#purchase-form-arrivalOrderAddPage input[name='goodsfield09']").val(data.field09);

        				$("#purchase-arrivalOrderDetail-boxnum").val(data.boxnum);

        			  	<c:choose>
        			  		<c:when test="${purchasePriceType == 1}">
    			            	$("#purchase-arrivalOrderDetail-hidtaxprice").val(data.highestbuyprice);        			  			
        			  		</c:when>
        			  		<c:when test="${purchasePriceType == 2}">
			            		$("#purchase-arrivalOrderDetail-hidtaxprice").val(data.newbuyprice);        			  			
    			  			</c:when>
        			  		<c:otherwise>
			            		$("#purchase-arrivalOrderDetail-hidtaxprice").val(data.highestbuyprice); 
			            	</c:otherwise>
        			  	</c:choose>

			          //商品进行批次管理时
						if(data.isbatch=="1"){
							$('#purchase-arrivalOrderDetail-batchno').validatebox({required:true});
							$("#purchase-arrivalOrderDetail-batchno").removeClass("no_input");
							$("#purchase-arrivalOrderDetail-batchno").removeAttr("readonly");
							
							$('#purchase-arrivalOrderDetail-produceddate').validatebox({required:true});
							$("#purchase-arrivalOrderDetail-produceddate").removeClass("no_input");
							$("#purchase-arrivalOrderDetail-produceddate").removeAttr("readonly");
							
							$('#purchase-arrivalOrderDetail-deadline').validatebox({required:true});
							$("#purchase-arrivalOrderDetail-deadline").removeClass("no_input");
							$("#purchase-arrivalOrderDetail-deadline").removeAttr("readonly");
							
						}else{
							$('#purchase-arrivalOrderDetail-batchno').validatebox({required:false});
							$("#purchase-arrivalOrderDetail-batchno").addClass("no_input");
							$("#purchase-arrivalOrderDetail-batchno").attr("readonly","readonly");
							
							$('#purchase-arrivalOrderDetail-produceddate').validatebox({required:false});
							$("#purchase-arrivalOrderDetail-produceddate").addClass("no_input");
							$("#purchase-arrivalOrderDetail-produceddate").attr("readonly","readonly");
							
							$('#purchase-arrivalOrderDetail-deadline').validatebox({required:false});
							$("#purchase-arrivalOrderDetail-deadline").addClass("no_input");
							$("#purchase-arrivalOrderDetail-deadline").attr("readonly","readonly");
						}
						
        				detailRemoteCompleteOrder();
        				$("#purchase-arrivalOrderDetail-auxnum").focus();
        				$("#purchase-arrivalOrderDetail-auxnum").select();
    				}
    			}
    		});
    		
  			$("#purchase-arrivalOrderDetail-unitnum").change(function(){
    			if($(this).validatebox('isValid')){
    				computeNum();
    			}
    		});
    		$("#purchase-arrivalOrderDetail-auxnum").change(function(){
        		if($(this).validatebox('isValid')){
    				computeNumByAux();
        		}
    		});
    		$("#purchase-arrivalOrderDetail-unitnum-auxremainder").change(function(){
        		if($(this).validatebox('isValid')){
    				computeNumByAux();
        		}
    		});
    		$("#purchase-arrivalOrderDetail-taxprice").focus(function(){
        		if(!$(this).hasClass("readonly")){
            		$(this).select();
        		}else{
        			$("#purchase-arrivalOrderDetail-notaxprice").focus();
        		}
    		});
    		
    		$("#purchase-arrivalOrderDetail-taxprice").change(function(){
        		if($(this).validatebox('isValid') && !$(this).hasClass("readonly")){
        			priceChange("1",this);
        		}
    		});

    		$("#purchase-arrivalOrderDetail-notaxprice").change(function(){
        		if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
        			priceChange("2",this);
        		}
    		});

    		$("#purchase-arrivalOrderDetail-notaxprice").focus(function(){
        		if(!$(this).hasClass("readonly")){
            		$(this).select();
        		}else{
		   			$("#purchase-arrivalOrderDetail-remark").focus();
		   			$("#purchase-arrivalOrderDetail-remark").select();
        		}
    		});
			
	  		$("#purchase-arrivalOrderDetailAddPage-addSave").click(function(){
				saveOrderDetail(false);		
	  		});

	  		$("#purchase-arrivalOrderDetailAddPage-addSaveGoOn").click(function(){
				saveOrderDetail(true);		
	  		});

	  		$("#purchase-arrivalOrderDetail-produceddate").click(function(){
	  			if(!$("#purchase-arrivalOrderDetail-batchno").hasClass("no_input")){
					WdatePicker({dateFmt:'yyyy-MM-dd'});
				}
	  		});
	  		$("#purchase-arrivalOrderDetail-deadline").click(function(){
	  			if(!$("#purchase-arrivalOrderDetail-batchno").hasClass("no_input")){
					WdatePicker({dateFmt:'yyyy-MM-dd'});
				}
	  		});
	  		$("#purchase-arrivalOrderDetail-auxnum").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#purchase-arrivalOrderDetail-auxnum").blur();
		   			$("#purchase-arrivalOrderDetail-unitnum-auxremainder").focus();
		   			$("#purchase-arrivalOrderDetail-unitnum-auxremainder").select();
				}
		    });
	  		$("#purchase-arrivalOrderDetail-unitnum-auxremainder").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#purchase-arrivalOrderDetail-unitnum-auxremainder").blur();

		   			$("#purchase-arrivalOrderDetail-unitnum").focus();
		   			$("#purchase-arrivalOrderDetail-unitnum").select();
				}
		    });

	  		$("#purchase-arrivalOrderDetail-unitnum").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#purchase-arrivalOrderDetail-unitnum").blur();
		   			$("#purchase-arrivalOrderDetail-taxprice").focus();
				}
		    });
	  		$("#purchase-arrivalOrderDetail-taxprice").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#purchase-arrivalOrderDetail-taxprice").blur();
		   			$("#purchase-arrivalOrderDetail-notaxprice").focus();
				}
		    });
	  		$("#purchase-arrivalOrderDetail-notaxprice").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#purchase-arrivalOrderDetail-notaxprice").blur()
					$("#purchase-arrivalOrderDetail-remark").focus();
		   			$("#purchase-arrivalOrderDetail-remark").select();
				}
		    });
	  		$("#purchase-arrivalOrderDetail-remark").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#purchase-arrivalOrderDetail-remark").blur();
		   			$("#purchase-arrivalOrderDetailAddPage-addSaveGoOn").focus();
				}
		    });
	  		$("#purchase-arrivalOrderDetailAddPage-addSaveGoOn").die("keydown").live("keydown",function(event){
	  			if(event.keyCode==13){
	  				saveOrderDetail(true);
	  			}
	  		});
  		});
  	</script>
  </body>
</html>
