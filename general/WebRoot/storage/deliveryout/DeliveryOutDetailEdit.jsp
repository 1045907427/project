<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>

<body>
	<div class="easyui-layout" data-options="fit:true">
		<div>
			<form id="storage-form-diliveryoutdetaill" method="post">
				<table style="border-collapse: collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="text-align: right;">商品：</td>
						<td>
							<input type="text" id="storage-diliveryoutdetaill-goodsid" name="goodsid" style="width: 170px;" tabindex="1" /> 
							<input type="hidden" id="storage-diliveryoutdetaill-goodsname" name="name" /></td>
						<td colspan="2" id="storage-loading-diliveryoutdetaill"></td>
					</tr>
					<tr>
					
					<c:if test="${billtype==1}">
						<td style="text-align: right;">辅数量：</td>
						<td style="text-align: left;">
							<input type="text" id="storage-diliveryoutdetaill-auxnum" name="auxnum" value="0" class="easyui-validatebox" validType="integer" data-options="required:true" style="width: 60px; float: left;" />
							<span id="storage-diliveryoutdetaill-span-auxunitname" style="float: left; line-height: 25px;">&nbsp;</span> 
							<input type="text" id="storage-diliveryoutdetaill-unitnum-auxremainder" name="auxremainder" value="0" class="easyui-validatebox" validType="intOrFloatNum[${decimallen}]" data-options="required:true" style="width: 60px; float: left;" />
							<span id="storage-diliveryoutdetaill-span-unitname" style="float: left; line-height: 25px;">&nbsp;</span>
						</td>
						<td style="text-align: right;">&nbsp;&nbsp;&nbsp;&nbsp;数量：</td>
						<td>
							<input type="text" id="storage-diliveryoutdetaill-unitnum" name="unitnum" value="0" class="easyui-validatebox" data-options="required:true,validType:['intOrFloatNum[${decimallen}]','max']" style="width: 150px;" />
						</td>
					 </c:if>	
					 <c:if test="${billtype==2}">
					 	<td style="text-align: right;">数量：</td>
						<td>
							<input type="text" id="storage-diliveryoutdetaill-unitnum" name="unitnum" value="0" class="easyui-validatebox" data-options="required:true,validType:['intOrFloatNum[${decimallen}]','max']" style="width: 150px;" />
						</td>
						<td style="text-align: right;">&nbsp;&nbsp;&nbsp;辅数量：</td>
						<td style="text-align: left;">
							<input type="text" id="storage-diliveryoutdetaill-auxnum" name="auxnum" value="0" class="easyui-validatebox" validType="integer" data-options="required:true" style="width: 60px; float: left;" />
							<span id="storage-diliveryoutdetaill-span-auxunitname" style="float: left; line-height: 25px;">&nbsp;</span> 
							<input type="text" id="storage-diliveryoutdetaill-unitnum-auxremainder" name="auxremainder" value="0" class="easyui-validatebox" validType="intOrFloatNum[${decimallen}]" data-options="required:true" style="width: 60px; float: left;" />
							<span id="storage-diliveryoutdetaill-span-unitname" style="float: left; line-height: 25px;">&nbsp;</span>
						</td>
					 </c:if>
						
					</tr>
					<tr>
						<td style="text-align: right;">单位：</td>
						<td style="float: left;">
							主：<input id="storage-diliveryoutdetaill-unitname" name="unitname" type="text" class="readonly2" style="width: 48px;" readonly="readonly" /> 辅：<input id="storage-diliveryoutdetaill-auxunitname" name="auxunitname" type="text" class="readonly2" style="width: 48px;" readonly="readonly" />
						</td>
						<td style="text-align: right;">箱装量：</td>
						<td><input id="storage-diliveryoutdetaill-boxnum"
							type="text" class="len150 readonly" readonly="readonly" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">商品品牌：</td>
						<td><input type="text"
							id="storage-diliveryoutdetaill-brand" readonly="readonly"
							style="width: 150px; border: 1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
						<td style="text-align: right;">商品规格：</td>
						<td><input type="text"
							id="storage-diliveryoutdetaill-model" readonly="readonly"
							style="width: 150px; border: 1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">
							<c:if test="${billtype=='1'}">采购价：</c:if>
							<c:if test="${billtype=='2'}">单价：</c:if>
						
						</td>
						<td><input type="text"
							id="storage-diliveryoutdetaill-basesaleprice"
							name="basesaleprice" class="easyui-numberbox" readonly="readonly"
							required="required" validType="intOrFloat"
							data-options="precision:6"
							style="width: 150px; background-color: #EBEBE4" ;/></td>
						<td style="text-align: right;">
							<c:if test="${billtype=='1'}">采购金额：</c:if>
							<c:if test="${billtype=='2'}">金额：</c:if>
						</td>
						<td>
							<input type="text" id="storage-diliveryoutdetaill-taxamount" name="taxamount" class="easyui-numberbox" required="required" validType="intOrFloat" data-options="precision:6" readonly="readonly" style="width: 150px; border: 1px solid #B3ADAB; background-color: #EBEBE4;" />
						</td>
					</tr>
					
					
					<tr>
			    		<td style="text-align: right;">批次号：</td>
			    		<td>
			    			<input id="storage-DeliveryOutDetail-batchno" type="text"  readonly="readonly" class="len150 "  name="batchno" />
			    		</td>
			    		<td style="text-align: right;" >所属仓库：</td>
			    		<td>
			    			<input id="storage-DeliveryOutDetail-store" type="text" class="len150 readonly" readonly="readonly" name="storagename" /> 
			    		</td>
			    	</tr>
			    	<tr>
			    		<td style="text-align: right;" >生产日期：</td>
			    		<td><input id="storage-DeliveryOutDetail-produceddate" type="text" class="len150 readonly" readonly="readonly" name="produceddate" /> </td>
			    		<td style="text-align: right;" >截止日期：</td>
			    		<td><input id="storage-DeliveryOutDetail-deadline" type="text" class="len150 readonly no_input"  readonly="readonly" name="deadline"/></td>
			    	</tr>
			    	
			    	
					
					<tr>
						<td style="text-align: right;">条形码：</td>
						<td>
							<input type="text" id="storage-diliveryoutdetaill-barcode" readonly="readonly" style="width: 150px; border: 1px solid #B3ADAB; background-color: #EBEBE4;" />
						</td>
						<td style="text-align: right;">备注：</td>
						<td>
							<input type="text"  id="storage-DeliveryOutDetail-remark" name="remark"  style="width: 150px; border: 1px solid #B3ADAB; " />
						</td>
						
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'south',border:false">
			<div class="buttonDetailBG" style="text-align: right;">
			 快捷键<span style="font-weight: bold; color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold; color: red;">+</span>
				<input
					type="button" value="保存" name="savenogo"
					id="storage-distributerejectDetail-addSaveGoOn" />
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var goodsname="";
		var boxnum="";//箱装量 
		var basesaleprice="";//基准价
		var goodsid="";
		var unitid="";
		var goodstype="";
		var maxnum=0;//最大
		
		
		var batchno=""; //批次号
		var produceddate = "" //生产日期
		var deadline = ""//截止日期
		var storagename = "" // //所属仓库
		$.extend($.fn.validatebox.defaults.rules, {
			   max: {  
			        validator: function (value) {  
			        	if(Number(value) > Number(maxnum)){
			        		return false;
			        	}else{
			        		return true;
			        	}
			        },  
			        message:'数量超过可用量'
			    } 
		});
		var selectedGoodsid=getFilterGoodsid();
		//获取所有的DataGrid中所有选中的GoodsID
		function getFilterGoodsid(){
			var tmpgoodsid=[];
			var goodsids="";
			var selectDetailRow=[];
	  		if($("#storage-table-distributeRejectAddPage").size()>0){
	  			selectDetailRow=$("#storage-table-distributeRejectAddPage").datagrid('getRows');
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
		
		function calculatePrice(){
			
			if($("#storage-diliveryoutdetaill-unitnum").validatebox('isValid')){
					if(boxnum==""||basesaleprice==""){
						return false;
					}
					var num=$("#storage-diliveryoutdetaill-unitnum").val();
					if(num==""){
						return false;
					}
					var allPrivce=Number(num)*Number(basesaleprice)
					$("#storage-diliveryoutdetaill-taxamount").numberbox('setValue',allPrivce);
			}
			
		}
		
		function calculateNum(){
			if($("#storage-diliveryoutdetaill-auxnum").validatebox('isValid')){
				if(boxnum==""||basesaleprice==""){
					return false;
				}
				var num=$("#storage-diliveryoutdetaill-auxnum").val();//箱数
				var left=$("#storage-diliveryoutdetaill-unitnum-auxremainder").val(); //剩余数量
				var allAmount=Number(boxnum)*Number(num)+Number(left);
				$("#storage-diliveryoutdetaill-unitnum").val(Number(allAmount.toFixed(${decimallen})));
				calculatePrice();
			}
		}
		//获取商品体积,重量信息
		function getGoodsInfo(){
			var object;
			$.ajax({   
	            url :'storage/distrtibution/getGoodsdetail.do?goodsId='+goodsid,
	            type:'post',
	            dataType:'json',
	            async:false,
	            success:function(data){
	            	object=data;
	            }
	        });
			return object;
		}
		
		function saveOrderDetail(isGoOn){
			$("#storage-diliveryoutdetaill-goodsid").focus();
  			var flag=$("#storage-form-diliveryoutdetaill").form('validate');
  			if(!flag){
	  			return false;
  			}
			var unitnum=$("#storage-diliveryoutdetaill-unitnum").val();
			if(unitnum==null || unitnum=="" ){
				$.messager.alert("提醒","抱歉，请填写数量！");
				return false;
			}
			
			var allNum=$("#storage-diliveryoutdetaill-unitnum").val();
			var num=$("#storage-diliveryoutdetaill-auxnum").val();//箱数
			var left=$("#storage-diliveryoutdetaill-unitnum-auxremainder").val(); //剩余数量
			
			var allPrice=$("#storage-diliveryoutdetaill-taxamount").numberbox('getValue');
			var volume="";
			var weight="";
			var object=getGoodsInfo();//获取商品体积,重量信息
			if(object!=null){
				if(object.singlevolume!=null&&object.singlevolume!=""){
			   		volume=Number(object.singlevolume)*Number(unitnum);//总体积
				}
				if(object.grossweight!=null&&object.grossweight!="")
					weight=Number(object.grossweight)*Number(unitnum);//重量
			}
			var formdata={};
			formdata.goodsInfo = EditInitData.goodsInfo;
			formdata.taxamount = allPrice
			formdata.boxprice=Number(boxnum)*Number(basesaleprice);//箱价
			formdata.auxunitname=$("#storage-diliveryoutdetaill-span-auxunitname").text();//辅单位
			formdata.volumn=volume;
			formdata.weight=weight;
			formdata.totalbox=(allNum/boxnum).toFixed(3);
			formdata.overnum=left;
			var remark=$("#storage-DeliveryOutDetail-remark").val();
			formdata.remark=remark;
			formdata.unitnum=allNum;
			formdata.goodsname=goodsname;
			
			formdata.batchno = batchno;
			formdata.produceddate = produceddate;
			formdata.deadline = deadline;
			
			formdata.auxnumdetail=parseInt($("#storage-diliveryoutdetaill-auxnum").val())+formdata.auxunitname+Number($("#storage-diliveryoutdetaill-unitnum-auxremainder").val())+$("#storage-diliveryoutdetaill-span-unitname").text();
			if(formdata){
				$("#storage-table-deliveryOutPage").datagrid('updateRow',{
					index:editRowIndex,
					row:formdata
				});
				footerReCalc();
			}
			EditInitData="";
			$("#storage-deliveryOutPage-dialog-DetailOper").dialog("close");
		}
  		$(document).ready(function(){
  			
  			
  			$("#storage-diliveryoutdetaill-goodsid").goodsWidget({
    			name:'t_storage_delivery_out_detail',
    			col:'goodsid',
    			singleSelect:true,
    			width:150,
    			onSelect : function(data){
    				
    			}
    		});
  			$("#storage-diliveryoutdetaill-goodsid").goodsWidget("setValue",EditInitData.goodsid)
  			$("#storage-diliveryoutdetaill-goodsid").goodsWidget("setText",EditInitData.goodsname)
  			$("#storage-diliveryoutdetaill-goodsid").goodsWidget("readonly",true)
  			var storageid=$("#storage-DeliveryOutAddPage-storage").widget('getValue');
           	var status=$("#storage-storageDeliveryOut-status").val();
           	
           	
  			if(!EditInitData.batchno){
  				//非批次管理
  				$("#storage-DeliveryOutDetail-batchno").widget("disable");
	  			$.ajax({   
		            url :'storage/deliveryout/getStorageSummaryByStorageidAndGoodsid.do?goodsid='+EditInitData.goodsid+"&storageid="+storageid,
		            type:'post',
		            dataType:'json',
		            async:false,
		            success:function(rs){
		            	var obj=rs.storageSummary;
		      			if(EditInitData!=""&&status=="2"){
		      				//保存页面
		      				maxnum=Number(obj.usablenum)+Number(EditInitData.unitnum);
		      				if(EditInitData.id==undefined){
		      					$("#storage-loading-diliveryoutdetaill").html("商品编码：<font color='green'>"+EditInitData.goodsid+"</font>&nbsp;可用量：<font color='green'>"+ obj.usablenum+obj.unitname+"</font>");	      				
		      				}else{
		      					$.ajax({
						            url :'storage/deliveryout/getStorageDeliveryOutDetail.do',
						            type:'post',
						            data:{'id':EditInitData.id},
						            async:false,
						            dataType:'json',
						            success:function(data){
						            	maxnum = Number(data.unitnum)+Number(obj.usablenum);
						            	$("#storage-loading-diliveryoutdetaill").html("商品编码：<font color='green'>"+EditInitData.goodsid+"</font>&nbsp;可用量：<font color='green'>"+ maxnum +obj.unitname+"</font>");
					            	}
						    	});
						    }	
		      			}else{
		      				//新增页面
		      				maxnum=Number(obj.usablenum);
		      				$("#storage-loading-diliveryoutdetaill").html("商品编码：<font color='green'>"+EditInitData.goodsid+"</font>&nbsp;可用量：<font color='green'>"+ obj.usablenum+obj.unitname+"</font>");
		      			}
		            }
		        });
  			}else{
  				//商品批次管理
  				$("#storage-DeliveryOutDetail-batchno").removeAttr("readonly");
  				
  				var param = [{field:'goodsid',op:'equal',value:EditInitData.goodsid},{field:'storageid',op:'equal',value:storageid}];
            	$("#storage-DeliveryOutDetail-batchno").widget({
            		referwid:'RL_T_STORAGE_BATCH_LIST',
            		param:param,
        			width:150,
        			disabled:false,
    				singleSelect:true,
    				required:true,
    				onSelect: function(obj){
    					batchno=obj.batchno; //批次号
    					produceddate = obj.produceddate //生产日期
    					deadline = obj.deadline //截止日期
    					storagename = obj.storagename // //所属仓库
    					$("#storage-DeliveryOutDetail-store").val(obj.storagename); //所属仓库
    					$("#storage-DeliveryOutDetail-produceddate").val(obj.produceddate); //生产日期
    					$("#storage-DeliveryOutDetail-deadline").val(obj.deadline);//截止日期
    					if(EditInitData!=""&&status=="2"){
// 		      				//保存页面
// 		      				maxnum=Number(obj.usablenum)+Number(EditInitData.unitnum);
		      				if(EditInitData.id==undefined){
		      					$("#storage-loading-diliveryoutdetaill").html("(批次商品)商品编码：<font color='green'>"+EditInitData.goodsid+"</font>&nbsp;可用量：<font color='green'>"+ obj.usablenum+obj.unitname+"</font>");
		      				}else{
		      					$.ajax({
						            url :'storage/deliveryout/getStorageDeliveryOutDetail.do',
						            type:'post',
						            data:{'id':EditInitData.id},
						            async:false,
						            dataType:'json',
						            success:function(data){
						            	if(obj.batchno == EditInitData.batchno){
							            	maxnum = Number(data.unitnum)+Number(obj.usablenum);
						            	}else{
							            	maxnum = Number(obj.usablenum)
						            	}
						            	$("#storage-loading-diliveryoutdetaill").html("(批次商品)商品编码：<font color='green'>"+EditInitData.goodsid+"</font>&nbsp;可用量：<font color='green'>"+ maxnum.toFixed(${decimallen}) +obj.unitname+"</font>");
					            	}
						    	});
						    }	
		      			}else{
// 		      				//新增页面
		      				maxnum=Number(obj.usablenum);
							$("#storage-loading-diliveryoutdetaill").html("(批次商品)商品编码：<font color='green'>"+obj.goodsid+"</font>&nbsp;可用量：<font color='green'>"+ obj.usablenum + obj.unitname +"</font>");
		      			}
    					
    				},
    				onClear:function(){
    					$("#storage-DeliveryOutDetail-batchno").val("")
    					$("#storage-DeliveryOutDetail-store").val("");
                        $("#storage-DeliveryOutDetail-produceddate").val("");
                        $("#storage-DeliveryOutDetail-deadline").val("");
    				}
            	});
                 $("#storage-DeliveryOutDetail-batchno").widget("enable");
  				 $("#storage-DeliveryOutDetail-batchno").widget("setValue",EditInitData.batchno)
  			
  			}
	        goodsname=EditInitData.goodsname;
  			goodsid=EditInitData.goodsid;
  			boxnum=  EditInitData.goodsInfo.boxnum;
  			basesaleprice= EditInitData.basesaleprice;
  			
			$("#storage-DeliveryOutDetail-produceddate").val(EditInitData.produceddate)    	
			$("#storage-DeliveryOutDetail-deadline").val(EditInitData.deadline)   
			$("#storage-DeliveryOutDetail-store").val(EditInitData.storagename)
			$("#storage-DeliveryOutDetail-batchno").val(EditInitData.batchno)
			 	
			//EditInitData.goodsInfo.mainunitName
  			$("#storage-diliveryoutdetaill-span-auxunitname").text(EditInitData.auxunitname);
  			$("#storage-diliveryoutdetaill-span-unitname").text(EditInitData.goodsInfo.mainunitName);
  			$("#storage-diliveryoutdetaill-unitname").val(EditInitData.goodsInfo.mainunitName);
  			$("#storage-diliveryoutdetaill-auxunitname").val(EditInitData.auxunitname);
  			$("#storage-diliveryoutdetaill-boxnum").val(formatterBigNumNoLen(EditInitData.goodsInfo.boxnum));
  			if(EditInitData.goodsInfo.brandName==undefined){
  				$("#storage-diliveryoutdetaill-brand").val(EditInitData.goodsInfo.brandname);
  			}else{
  				$("#storage-diliveryoutdetaill-brand").val(EditInitData.goodsInfo.brandName);
  			}
  			$("#storage-diliveryoutdetaill-model").val(EditInitData.goodsInfo.model);
  			$("#storage-diliveryoutdetaill-basesaleprice").val(EditInitData.basesaleprice);
  			$("#storage-diliveryoutdetaill-taxamount").val(EditInitData.taxamount);
  			$("#storage-diliveryoutdetaill-barcode").val(EditInitData.goodsInfo.barcode);
  			$("#storage-diliveryoutdetaill-auxnum").val(parseInt(EditInitData.totalbox)); //箱数
  			$("#storage-diliveryoutdetaill-unitnum-auxremainder").val(formatterBigNumNoLen(EditInitData.overnum));//主数量剩余
  			$("#storage-diliveryoutdetaill-unitnum").val(formatterBigNumNoLen(EditInitData.unitnum)); //数量
  			$("#storage-DeliveryOutDetail-remark").val(EditInitData.remark); //备注
			$("#storage-diliveryoutdetaill-unitnum").change(function(){
					if($(this).validatebox('isValid')){
	    				var value=$(this).val();
	    				if(Number(value)<0)  return false;
	    				if(boxnum=="") return false;
	    				var num=Math.floor(Number(value)/Number(boxnum));//箱数
	    				var left=Number(value)%Number(boxnum);//剩余
	    				$("#storage-diliveryoutdetaill-auxnum").val(Number(num));
	    				$("#storage-diliveryoutdetaill-unitnum-auxremainder").val(Number(left.toFixed(${decimallen})));
	    				$("#storage-diliveryoutdetaill-unitnum").val(Number(value));
	    				calculatePrice();
	        		}
					
			});
			
			$("#storage-diliveryoutdetaill-auxnum").change(function(){
			
				if($(this).validatebox('isValid')){
					calculateNum()
				}
			});
			
			$("#storage-diliveryoutdetaill-unitnum-auxremainder").change(function(){
				if($(this).validatebox('isValid')){
					calculateNum()
					if(Number($(this).val())>=Number(boxnum)){
						var allNum=$("#storage-diliveryoutdetaill-unitnum").val();
						$("#storage-diliveryoutdetaill-auxnum").val(Math.floor(Number(allNum)/Number(boxnum)));
						$("#storage-diliveryoutdetaill-unitnum-auxremainder").val((Number(allNum)%Number(boxnum)).toFixed(${decimallen}));
					}
				}
			});

		<c:if test="${billtype==1}">
		
  		
  		$("#storage-diliveryoutdetaill-auxnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			$("storage-diliveryoutdetaill-auxnum").blur();
	   			$("#storage-diliveryoutdetaill-unitnum-auxremainder").focus();
	   			$("#storage-diliveryoutdetaill-unitnum-auxremainder").select();
			}
	    });
  		
  		$("#storage-diliveryoutdetaill-unitnum-auxremainder").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			$("storage-diliveryoutdetaill-unitnum-auxremainder").blur();
	   			$("#storage-diliveryoutdetaill-unitnum").focus();
	   			$("#storage-diliveryoutdetaill-unitnum").select();
			}
	    });
	    
	    
	    $("#storage-diliveryoutdetaill-unitnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			$("storage-diliveryoutdetaill-unitnum").blur();
	   			$("#storage-DeliveryOutDetail-remark").focus();
	   			$("#storage-DeliveryOutDetail-remark").select();
			}
	    });
	    
	    $("#storage-DeliveryOutDetail-remark").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			saveOrderDetail(false)
			}
	    });
	    
	    
	    
  		
  		$("#storage-distributerejectDetail-addSaveGoOn").click(function(){
  			saveOrderDetail(false)
  		})
		
		$("#storage-diliveryoutdetaill-auxnum").focus();
  		$("#storage-diliveryoutdetaill-auxnum").select();
  		
	    </c:if>
	    
	    
  		<c:if test="${billtype==2}">
  		
		$("#storage-diliveryoutdetaill-unitnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			$("storage-diliveryoutdetaill-unitnum").blur();
	   			$("#storage-diliveryoutdetaill-auxnum").focus();
	   			$("#storage-diliveryoutdetaill-auxnum").select();
			}
	    });
  		
  		$("#storage-diliveryoutdetaill-auxnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			$("storage-diliveryoutdetaill-auxnum").blur();
	   			$("#storage-diliveryoutdetaill-unitnum-auxremainder").focus();
	   			$("#storage-diliveryoutdetaill-unitnum-auxremainder").select();
			}
	    });
  		
  		$("#storage-diliveryoutdetaill-unitnum-auxremainder").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			$("storage-diliveryoutdetaill-unitnum-auxremainder").blur();
	   			$("#storage-DeliveryOutDetail-remark").focus();
	   			$("#storage-DeliveryOutDetail-remark").select();
			}
	    });
	    
	    $("#storage-DeliveryOutDetail-remark").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			saveOrderDetail(false)
			}
	    });
  		
  		$("#storage-distributerejectDetail-addSaveGoOn").click(function(){
  			saveOrderDetail(false)
  		})
		
		$("#storage-diliveryoutdetaill-unitnum").focus();
  		$("#storage-diliveryoutdetaill-unitnum").select();
  		
	    </c:if>
		
  	});
  	</script>
</body>
</html>
