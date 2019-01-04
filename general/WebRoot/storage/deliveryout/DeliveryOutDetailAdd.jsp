<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
</head>

<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false">
			<form id="storage-form-DeliveryOutDetail" method="post">
				<table style="border-collapse: collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="text-align: right;">商品：</td>
						<td>
							<input type="text" id="storage-DeliveryOutDetail-goodsid" name="goodsid" style="width: 150px;" tabindex="1" /> 
							<input type="hidden" id="storage-DeliveryOutDetail-goodsname" name="name" /></td>
						<td colspan="2" id="storage-loading-DeliveryOutDetail"></td>
					</tr>
					<tr>
					
					<c:if test="${billtype==1}">
						<td style="text-align: right;">辅数量：</td>
						<td style="text-align: left;">
							<input type="text" id="storage-DeliveryOutDetail-auxnum" name="auxnum" value="0" class="easyui-validatebox" validType="integer" data-options="required:true" style="width: 60px; float: left;" />
							<span id="storage-DeliveryOutDetail-span-auxunitname" style="float: left; line-height: 25px;">&nbsp;</span> 
							<input type="text" id="storage-DeliveryOutDetail-unitnum-auxremainder" name="auxremainder" value="0" class="easyui-validatebox" validType="intOrFloatNum[${decimallen}]" data-options="required:true" style="width: 60px; float: left;" />
							<span id="storage-DeliveryOutDetail-span-unitname" style="float: left; line-height: 25px;">&nbsp;</span>
						</td>
						<td style="text-align: right;">&nbsp;&nbsp;&nbsp;&nbsp;数量：</td>
						<td>
							<input type="text" id="storage-DeliveryOutDetail-unitnum" name="unitnum" value="0" class="easyui-validatebox" data-options="required:true,validType:['intOrFloatNum[${decimallen}]','max']" style="width: 150px;"  />
						</td>
					</c:if>	
					
					<c:if test="${billtype==2}">
						<td style="text-align: right;">&nbsp;&nbsp;&nbsp;&nbsp;数量：</td>
						<td>
							<input type="text" id="storage-DeliveryOutDetail-unitnum" name="unitnum" value="0" class="easyui-validatebox" data-options="required:true,validType:['intOrFloatNum[${decimallen}]','max']" style="width: 150px;"/>
						</td>
					
						<td style="text-align: right;">&nbsp;&nbsp;&nbsp;辅数量：</td>
						<td style="text-align: left;">
							<input type="text" id="storage-DeliveryOutDetail-auxnum" name="auxnum" value="0" class="easyui-validatebox" validType="integer" data-options="required:true" style="width: 60px; float: left;" />
							<span id="storage-DeliveryOutDetail-span-auxunitname" style="float: left; line-height: 25px;">&nbsp;</span> 
							<input type="text" id="storage-DeliveryOutDetail-unitnum-auxremainder" name="auxremainder" value="0" class="easyui-validatebox" validType="intOrFloatNum[${decimallen}]" data-options="required:true" style="width: 60px; float: left;" />
							<span id="storage-DeliveryOutDetail-span-unitname" style="float: left; line-height: 25px;">&nbsp;</span>
						</td>
					</c:if>	
					</tr>
					<tr>
						<td style="text-align: right;">单位：</td>
						<td style="float: left;">
							主：<input id="storage-DeliveryOutDetail-unitname" name="unitname"type="text" class="readonly2" style="width: 48px;"readonly="readonly" />
						        辅：<input id="storage-DeliveryOutDetail-auxunitname" name="auxunitname" type="text" class="readonly2" style="width: 48px;" readonly="readonly" />
						</td>
						<td style="text-align: right;">箱装量：</td>
						<td>
							<input id="storage-DeliveryOutDetail-boxnum" type="text" class="len150 readonly" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">商品品牌：</td>
						<td>
							<input type="text" id="storage-DeliveryOutDetail-brand" readonly="readonly" style="width: 150px; border: 1px solid #B3ADAB; background-color: #EBEBE4;" />
						</td>
						<td style="text-align: right;">商品规格：</td>
						<td>
							<input type="text" id="storage-DeliveryOutDetail-model" readonly="readonly" style="width: 150px; border: 1px solid #B3ADAB; background-color: #EBEBE4;" />
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">
						
								<c:if test="${billtype=='1'}">采购价：</c:if>
								<c:if test="${billtype=='2'}">单价：</c:if>
						</td>
						<td>
							<input type="text" id="storage-DeliveryOutDetail-basesaleprice" name="basesaleprice" class="easyui-numberbox" readonly="readonly" required="required" validType="intOrFloat" data-options="precision:6" style="width: 150px; background-color: #EBEBE4" ;/></td>
						<td style="text-align: right;">
							<c:if test="${billtype=='1'}">采购金额：</c:if>
							<c:if test="${billtype=='2'}">金额：</c:if>
						</td>
						<td>
							<input type="text" id="storage-DeliveryOutDetail-taxamount" name="taxamount" class="easyui-numberbox" required="required" validType="intOrFloat" data-options="precision:6" readonly="readonly" style="width: 150px; border: 1px solid #B3ADAB; background-color: #EBEBE4;" />
						</td>
					</tr>
					
					
					
					<tr>
			    		<td style="text-align: right;">批次号：</td>
			    		<td>
			    			<input id="storage-DeliveryOutDetail-batchno" type="text" class="len150 "  name="batchno" />
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
						<td><input type="text"  id="storage-DeliveryOutDetail-barcode" readonly="readonly" style="width: 150px; border: 1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
						
						<td style="text-align: right;">备注：</td>
						<td><input type="text"  id="storage-DeliveryOutDetail-remark" name="remark"  style="width: 150px; border: 1px solid #B3ADAB; " /></td>
					</tr>
					
				</table>
			</form>
		</div>
		<div data-options="region:'south',border:false">
			<div class="buttonDetailBG" style="text-align: right;"> 
			         快捷键<span style="font-weight: bold; color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold; color: red;">+</span>
			          <input type="button" value="确定" name="savegoon" id="storage-distributerejectDetail-addSaveGoOn" /> 
				    
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var goodsname="";//goodsname
		var boxnum="";//箱装量 
		var basesaleprice="";//基准价    //最高采购价(采购出库)
		var goodsid="";
		var unitid="";//主计量单位
		var goodstype="";
		var maxnum=0;//最大量限制
		var auxunitid="";
		var buyprice="";
		var batchno = "" //批次号
		var produceddate = ""  //生产日期
		var deadline = "" //截止日期
		var storagename = ""
		
		
		$.extend($.fn.validatebox.defaults.rules, {
			   max: {  
			        validator: function (value) {  
			        	if(value > maxnum){
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
	  		if($("#storage-table-deliveryOutPage").size()>0){
	  			selectDetailRow=$("#storage-table-deliveryOutPage").datagrid('getRows');
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
		
			if($("#storage-DeliveryOutDetail-unitnum").validatebox('isValid')){
					if(boxnum==""||basesaleprice==""){
						return false;
					}
					var num=$("#storage-DeliveryOutDetail-unitnum").val();
					if(num==""){
						return false;
					}
					var allPrivce=Number(num)*Number(basesaleprice)
					$("#storage-DeliveryOutDetail-taxamount").numberbox('setValue',allPrivce);
			}
			
		}
		
		function calculateNum(){
			if($("#storage-DeliveryOutDetail-auxnum").validatebox('isValid')){
				if(boxnum==""||basesaleprice==""){
					return false;
				}
				var num=$("#storage-DeliveryOutDetail-auxnum").val();//箱数
				var left=$("#storage-DeliveryOutDetail-unitnum-auxremainder").val(); //剩余数量
				var allAmount=Number(boxnum)*Number(num)+Number(left);
				$("#storage-DeliveryOutDetail-unitnum").val(Number(allAmount.toFixed(${decimallen})));
				calculatePrice();
			}
		}
		
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

  		function orderDetailAddSaveGoOnDialog(){
  			var $DetailOper=$("#storage-deliveryOutPage-dialog-DetailOper");
  			$DetailOper.dialog({
  				title:'商品信息新增',
  			    width: 600,  
  			    height: 320,
  			    closed: true,  
  			    cache: false, 
  			    modal: true,
  			    maximizable:true,
  			    href:"storage/deliveryout/deliveryOutDetailDiaLogAdd.do?supplierid=${supplierid}&billtype=${billtype}"
  			});
  			$DetailOper.dialog("open");
  		}
 
		function saveOrderDetail(isGoOn){
			$("#storage-DeliveryOutDetail-goodsid").focus();
  			var flag=$("#storage-form-DeliveryOutDetail").form('validate');
  			if(!flag){
	  			return false;
  			}

  			var goodsInfo=$("#storage-DeliveryOutDetail-goodsid").goodsWidget('getObject');
  			if(goodsInfo==null || goodsInfo==""){
				$.messager.alert("提醒","抱歉，请选择商品！");
				return false;		  			
  			}
			var unitnum=$("#storage-DeliveryOutDetail-unitnum").val();
			if(unitnum==null || unitnum=="" || unitnum==0){
				$.messager.alert("提醒","抱歉，请填写数量！");
				return false;
			}
			var object=getGoodsInfo();
			var allNum=$("#storage-DeliveryOutDetail-unitnum").val();
			var num=$("#storage-DeliveryOutDetail-auxnum").val();//箱数
			var left=$("#storage-DeliveryOutDetail-unitnum-auxremainder").val(); //剩余数量
			var volume="";
			var weight="";
			if(object!=null){
				if(object.totalvolume!=null&&object.totalvolume!=""&&object.singlevolume!=null&&object.singlevolume!=""){
			   		volume=Number(object.totalvolume)*Number(num)+Number(object.singlevolume)*Number(left);//总体积
				}
				if(object.totalweight!=null&&object.totalweight!=""&&object.grossweight!=null&&object.grossweight!="")
					   weight=Number(object.totalweight)*Number(num)+Number(object.grossweight)*Number(left);//重量
			}
			var formdata=$("#storage-form-DeliveryOutDetail").serializeJSON();
			formdata.price=basesaleprice;
			formdata.goodsInfo = goodsInfo;
			formdata.boxprice=Number(boxnum)*Number(basesaleprice);//金额
			formdata.auxunitname=$("#storage-DeliveryOutDetail-span-auxunitname").text();//辅单位
			formdata.volumn=volume;
			formdata.buyprice=buyprice;
			formdata.weight=weight;
			formdata.goodsname=goodsname;
			formdata.totalbox=(allNum/boxnum).toFixed(3);
			formdata.overnum=left;
			formdata.unitid=unitid;
			formdata.goodssort=goodstype;
			formdata.auxunitid=auxunitid;
			
			formdata.storagename=storagename;
			formdata.produceddate=produceddate;
			formdata.deadline=deadline;
			formdata.batchno=batchno;
			var remark=$("#storage-DeliveryOutDetail-remark").val();
			formdata.remark=remark;
			formdata.auxnumdetail=parseInt($("#storage-DeliveryOutDetail-auxnum").val())+formdata.auxunitname+Number($("#storage-DeliveryOutDetail-unitnum-auxremainder").val())+$("#storage-DeliveryOutDetail-span-unitname").text();
			if(formdata){
				var index=getAddRowIndex();
				$("#storage-table-deliveryOutPage").datagrid('updateRow',{
					index:index,
					row:formdata
				});
				footerReCalc();
				if(index>=14){
					var rows=$("#storage-table-deliveryOutPage").datagrid('getRows');
					if(index == rows.length - 1){
						$("#storage-table-deliveryOutPage").datagrid('appendRow',{});
					}
				}
			}
			if(isGoOn){
				orderDetailAddSaveGoOnDialog();
			}else{
	  			$("#storage-deliveryOutPage-dialog-DetailOper").dialog("close");
			}
		}

		function loadDeliveryOutDetailBatchnoWidget(required,disabled){
			$("#storage-DeliveryOutDetail-batchno").widget({
				referwid:'RL_T_STORAGE_BATCH_LIST',
				width:150,
				singleSelect:true,
				required: required,
				disabled:disabled
			});
		}

  		$(document).ready(function(){
  			var storageid=$("#storage-DeliveryOutAddPage-storage").widget('getValue');
  			$("#storage-DeliveryOutDetail-goodsid").goodsWidget({
    			param:[
					{field:'storageid',op:'equal',value:storageid}
// 					{field:'defaultsupplier',op:'equal',value:'${supplierid}'}
				],
				queryAllBySupplier:"${supplierid}",
				onClear:function(){
					$("#storage-DeliveryOutDetail-auxnum").val("");
					$("#storage-DeliveryOutDetail-unitnum-auxremainder").val("");
					$("#storage-DeliveryOutDetail-unitnum").val("");
               		$("#storage-DeliveryOutDetail-unitname").val("") 
  					$("#storage-DeliveryOutDetail-span-unitname").text("");
  					$("#storage-DeliveryOutDetail-auxunitname").val("");
  		        	$("#storage-DeliveryOutDetail-span-auxunitname").text(""); 
  					$("#storage-DeliveryOutDetail-model").val("");
 		            $("#storage-DeliveryOutDetail-brand").val("");
 		            $("#storage-DeliveryOutDetail-basesaleprice").numberbox('setValue','0.00');//基准单价
 		            $("#storage-DeliveryOutDetail-taxamount").numberbox('setValue','0.00');
   					$("#storage-DeliveryOutDetail-store").val("");
                    $("#storage-DeliveryOutDetail-produceddate").val("");
                    $("#storage-DeliveryOutDetail-deadline").val("");
                    $("#storage-DeliveryOutDetail-barcode").val("");
                    $("#storage-DeliveryOutDetail-boxnum").val("");
					$("#storage-loading-DeliveryOutDetail").html("");
					loadDeliveryOutDetailBatchnoWidget(false,true);
					$("#storage-DeliveryOutDetail-batchno").widget("clear");
                },
				
    			onSelect : function(data){
    				if(data){
        				$("#storage-DeliveryOutDetail-taxamount").numberbox('setValue',"0.00");
        				$("#storage-DeliveryOutDetail-auxnum").val(0);
        				$("#storage-DeliveryOutDetail-unitnum-auxremainder").val(0);
        				$("#storage-DeliveryOutDetail-unitnum").val(0);
						//主单位
						$("#storage-DeliveryOutDetail-unitname").val(data.mainunitName)
						$("#storage-DeliveryOutDetail-span-unitname").text(data.mainunitName);
						//辅助单位
						$("#storage-DeliveryOutDetail-auxunitname").val(data.auxunitname);
						$("#storage-DeliveryOutDetail-span-auxunitname").text(data.auxunitname);
						//商品规格
						$("#storage-DeliveryOutDetail-model").val(data.model);
						//品牌名称
						$("#storage-DeliveryOutDetail-brand").val(data.brandName);
						//箱装量
						$("#storage-DeliveryOutDetail-boxnum").val(data.boxnum);
						//二维码
        				$("#storage-DeliveryOutDetail-barcode").val(data.barcode);

						var isbatch = data.isbatch;
						$.ajax({
							url :'storage/deliveryout/getDeliveryOutGoodsInfo.do?goodsid='+data.id+"&customerid=${customerid}",
							type:'post',
							dataType:'json',
							async:false,
							success:function(rs){
								goodstype=rs.defaultsort; //商品类型
								buyprice=rs.highestbuyprice;
								unitid=rs.mainunit; //主计量单位id
								<c:if test="${billtype=='1'}">
								basesaleprice=rs.highestbuyprice;
								$("#storage-DeliveryOutDetail-basesaleprice").numberbox('setValue',rs.highestbuyprice);//最高采购价(采购出库)
								</c:if>

								<c:if test="${billtype=='2'}">
								basesaleprice=rs.basesaleprice;
								$("#storage-DeliveryOutDetail-basesaleprice").numberbox('setValue',rs.basesaleprice);//基准单价
								</c:if>
							}
						});

						//获取可用量
						$.ajax({
							url :'storage/deliveryout/getStorageSummaryByStorageidAndGoodsid.do?goodsid='+data.id,
							type:'post',
							dataType:'json',
							data:{goodsid:data.id,storageid:storageid},
							async:false,
							success:function(rs){
								var str = "商品编码：<font color='green'>"+data.id+"</font>&nbsp;可用量：<font color='green'>"+ rs.storageSummary.usablenum +rs.storageSummary.unitname+"</font>"
								if(isbatch=="1"){
									str = "(批次商品)"+str
								}
								$("#storage-loading-DeliveryOutDetail").html(str);
								maxnum = rs.storageSummary.usablenum  //可用量
							}

						})

						goodsname=data.name; //商品名称
						goodsid=data.id; //商品编号
						boxnum=data.boxnum;//箱装量
						auxunitid=data.auxunitid; //辅计量单位

						$("#storage-DeliveryOutDetail-batchno").widget("clear");
						$("#storage-DeliveryOutDetail-store").val("");
						$("#storage-DeliveryOutDetail-produceddate").val("");
						$("#storage-DeliveryOutDetail-deadline").val("");

						//判断商品是否批次管理 1是
						if(data.isbatch=='1'){
							$("#storage-DeliveryOutDetail-batchno").widget("enable");
							var param = [{field:'goodsid',op:'equal',value:data.id},{field:'storageid',op:'equal',value:storageid}];
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
									$("#storage-loading-DeliveryOutDetail").html("(批次商品)商品编码：<font color='green'>"+obj.goodsid+"</font>&nbsp;可用量：<font color='green'>"+ obj.usablenum + obj.unitname +"</font>");
									if(obj.usablenum!=null && obj.usablenum!=''){
										maxnum  = obj.usablenum ;  //最大可用量
									}else{
										maxnum = 0 ;
									}
								},
								onClear:function(){
									$("#storage-DeliveryOutDetail-store").val("");
									$("#storage-DeliveryOutDetail-produceddate").val("");
									$("#storage-DeliveryOutDetail-deadline").val("");
									$("#storage-DeliveryOutDetail-batchno").widget("clear");
								}
							});
						}

						<c:if test="${billtype=='1'}">
						$("#storage-DeliveryOutDetail-auxnum").focus();
						$("#storage-DeliveryOutDetail-auxnum").select();
						</c:if>

						<c:if test="${billtype=='2'}">
						$("#storage-DeliveryOutDetail-unitnum").focus();
						$("#storage-DeliveryOutDetail-unitnum").select();

						</c:if>
    				}
    			}
    		});

			//初始化批次号
			loadDeliveryOutDetailBatchnoWidget(false,false);

			$("#storage-DeliveryOutDetail-unitnum").change(function(){
					if($(this).validatebox('isValid')){
	    				var value=$(this).val();
	    				if(Number(value)<=0)  return false;
	    				if(boxnum=="") return false;
	    				var num=Math.floor(Number(value)/Number(boxnum));//箱数
	    				var left=Number(value)%Number(boxnum);//剩余
	    				$("#storage-DeliveryOutDetail-auxnum").val(num);
	    				$("#storage-DeliveryOutDetail-unitnum-auxremainder").val(Number(left.toFixed(${decimallen})));
	    				$("#storage-DeliveryOutDetail-unitnum").val(Number(value));
	    				calculatePrice();
	        		}
					
			});
			
			$("#storage-DeliveryOutDetail-auxnum").change(function(){
				if($(this).validatebox('isValid')){
					calculateNum()
				}
			});
			
			$("#storage-DeliveryOutDetail-unitnum-auxremainder").change(function(){
				if($(this).validatebox('isValid')){
					calculateNum()
					if(Number($(this).val())>=Number(boxnum)){
						var allNum=$("#storage-DeliveryOutDetail-unitnum").val();
						$("#storage-DeliveryOutDetail-auxnum").val(Math.floor(Number(allNum)/Number(boxnum)));
						$("#storage-DeliveryOutDetail-unitnum-auxremainder").val(Number((Number(allNum)%Number()).toFixed(${decimallen})))
					}
				}
			});

		 <c:if test="${billtype==1}">	
  		
  		$("#storage-DeliveryOutDetail-auxnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			$("storage-DeliveryOutDetail-auxnum").blur();
	   			$("#storage-DeliveryOutDetail-unitnum-auxremainder").focus();
	   			$("#storage-DeliveryOutDetail-unitnum-auxremainder").select();
			}
	    });
  		
  		$("#storage-DeliveryOutDetail-unitnum-auxremainder").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			$("storage-DeliveryOutDetail-unitnum-auxremainder").blur();
	   			$("#storage-DeliveryOutDetail-unitnum").focus();
	   			$("#storage-DeliveryOutDetail-unitnum").select();
			}
	    });
  		
  		$("#storage-DeliveryOutDetail-unitnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			$("storage-DeliveryOutDetail-unitnum").blur();
	   			$("#storage-DeliveryOutDetail-remark").focus();
	   			$("#storage-DeliveryOutDetail-remark").select();
			}
	    });
	    
	    
	    $("#storage-DeliveryOutDetail-remark").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			saveOrderDetail(true);
			}
	    });

	    
  		
  		$("#storage-distributerejectDetail-addSaveGoOn").click(function(){
  			saveOrderDetail(true)
  		})
  		
  		</c:if>
  		
  		
  		<c:if test="${billtype==2}">	
  		 $("#storage-DeliveryOutDetail-unitnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			$("storage-DeliveryOutDetail-unitnum").blur();
	   			$("#storage-DeliveryOutDetail-auxnum").focus();
	   			$("#storage-DeliveryOutDetail-auxnum").select();
			}
	    });
  		 
  		 	
  		$("#storage-DeliveryOutDetail-auxnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			$("storage-DeliveryOutDetail-auxnum").blur();
	   			$("#storage-DeliveryOutDetail-unitnum-auxremainder").focus();
	   			$("#storage-DeliveryOutDetail-unitnum-auxremainder").select();
			}
	    });
  		
  		$("#storage-DeliveryOutDetail-unitnum-auxremainder").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			$("storage-DeliveryOutDetail-unitnum-auxremainder").blur();
	   			$("#storage-DeliveryOutDetail-remark").focus();
	   			$("#storage-DeliveryOutDetail-remark").select();
			}
	    });
  		
  		
	    
	    
	    $("#storage-DeliveryOutDetail-remark").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			saveOrderDetail(true)
			}
	    });

	    
  		
  		$("#storage-distributerejectDetail-addSaveGoOn").click(function(){
  			saveOrderDetail(true)
  		})
  		
  		 
  		 
  		
  		</c:if>
  	});
  	</script>
</body>
</html>
