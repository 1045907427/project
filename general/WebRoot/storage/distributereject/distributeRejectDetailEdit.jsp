<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>

<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false">
			<form id="storage-form-distributerejectDetail" method="post">
				<table style="border-collapse: collapse;" border="0" cellpadding="5"
					cellspacing="5">
					<tr>
						<td style="text-align: right;">商品：</td>
						<td><input type="text"
							id="storage-distributerejectDetail-goodsid" name="goodsid"
							style="width: 170px;" tabindex="1" /> <input type="hidden"
							id="storage-distributerejectDetail-goodsname" name="name" /></td>
						<td colspan="2" id="storage-loading-distributerejectDetail"></td>
					</tr>
					<tr>
						<c:if test="${billtype==1 }">
						<td style="text-align: right;">辅数量：</td>
						<td style="text-align: left;"><input type="text"
							id="storage-distributerejectDetail-auxnum" name="auxnum"
							value="0" class="easyui-validatebox" validType="integer"
							data-options="required:true" style="width: 60px; float: left;" />
							<span id="storage-distributerejectDetail-span-auxunitname"
							style="float: left; line-height: 25px;">&nbsp;</span> <input
							type="text"
							id="storage-distributerejectDetail-unitnum-auxremainder"
							name="auxremainder" value="0" class="easyui-validatebox"
							validType="intOrFloatNum[${decimallen}]" data-options="required:true"
							style="width: 60px; float: left;" /> <span
							id="storage-distributerejectDetail-span-unitname"
							style="float: left; line-height: 25px;">&nbsp;</span></td>
						<td style="text-align: right;">&nbsp;&nbsp;&nbsp;&nbsp;数量：</td>
						<td><input type="text"
							id="storage-distributerejectDetail-unitnum" name="unitnum"
							value="0" class="easyui-validatebox" validType="intOrFloatNum[${decimallen}]" data-options="required:true"
							style="width: 150px;" /></td>
						</c:if>	
						<c:if test="${billtype==2 }">
						<td style="text-align: right;">&nbsp;&nbsp;&nbsp;&nbsp;数量：</td>
						<td><input type="text"
							id="storage-distributerejectDetail-unitnum" name="unitnum"
							value="0" class="easyui-validatebox" validType="intOrFloatNum[${decimallen}]" data-options="required:true"
							style="width: 150px;" /></td>
						
						<td style="text-align: right;">&nbsp;&nbsp;&nbsp;辅数量：</td>
						<td style="text-align: left;"><input type="text"
							id="storage-distributerejectDetail-auxnum" name="auxnum"
							value="0" class="easyui-validatebox" validType="integer"
							data-options="required:true" style="width: 60px; float: left;" />
							<span id="storage-distributerejectDetail-span-auxunitname"
							style="float: left; line-height: 25px;">&nbsp;</span> <input
							type="text"
							id="storage-distributerejectDetail-unitnum-auxremainder"
							name="auxremainder" value="0" class="easyui-validatebox"
							validType="intOrFloatNum[${decimallen}]" data-options="required:true"
							style="width: 60px; float: left;" /> <span
							id="storage-distributerejectDetail-span-unitname"
							style="float: left; line-height: 25px;">&nbsp;</span></td>
						</c:if>	
					</tr>
					<tr>
						<td style="text-align: right;">单位：</td>
						<td style="float: left;">主：<input
							id="storage-distributerejectDetail-unitname" name="unitname"
							type="text" class="readonly2" style="width: 48px;"
							readonly="readonly" /> 辅：<input
							id="storage-distributerejectDetail-auxunitname"
							name="auxunitname" type="text" class="readonly2"
							style="width: 48px;" readonly="readonly" />
						</td>
						<td style="text-align: right;">箱装量：</td>
						<td><input id="storage-distributerejectDetail-boxnum"
							type="text" class="len150 readonly" readonly="readonly" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">商品品牌：</td>
						<td><input type="text"
							id="storage-distributerejectDetail-brand" readonly="readonly"
							style="width: 150px; border: 1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
						<td style="text-align: right;">商品规格：</td>
						<td><input type="text"
							id="storage-distributerejectDetail-model" readonly="readonly"
							style="width: 150px; border: 1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">
						
							<c:if test="${billtype=='1'}">采购价：</c:if>
							<c:if test="${billtype=='2'}">单价：</c:if>
						
						</td>
						<td><input type="text"
							id="storage-distributerejectDetail-basesaleprice"
							name="basesaleprice" class="easyui-numberbox" readonly="readonly"
							required="required" validType="intOrFloat"
							data-options="precision:6"
							style="width: 150px; background-color: #EBEBE4" ;/></td>
						<td style="text-align: right;">
						
							<c:if test="${billtype=='1'}">采购金额：</c:if>
							<c:if test="${billtype=='2'}">金额：</c:if>
						
						</td>
						<td><input type="text"
							id="storage-distributerejectDetail-taxamount" name="taxamount"
							class="easyui-numberbox" required="required"
							validType="intOrFloat" data-options="precision:6"
							readonly="readonly"
							style="width: 150px; border: 1px solid #B3ADAB; background-color: #EBEBE4;" />
						</td>
					</tr>
					
					<input id="storage-distributerejectDetail-storename" type="hidden" class="len150 readonly" readonly="readonly" name="storagename" /> 
					
			    	<tr>
			    		<td style="text-align: right;" >生产日期：</td>
			    		<td><input id="storage-distributerejectDetail-produceddate" type="text" style="height: 20px;" class="WdateRead" readonly="readonly" name="produceddate" /> </td>
			    		<td style="text-align: right;" >截止日期：</td>
			    		<td><input id="storage-distributerejectDetail-deadline" type="text"   style="height: 20px;" class="WdateRead" readonly="readonly" name="deadline"/></td>
			    	</tr>
					
					<tr>
			    		<td style="text-align: right;">批次号：</td>
			    		<td>
			    			<input id="storage-distributerejectDetail-batchno" type="text"  readonly="readonly" class="no_input"   name="batchno" />
			    		</td>
			    		<td style="text-align: right;" >所属库位：</td>
			    		<td>
			    			<input id="storage-EnterDetail-storagelocationid" type="text" class="len150 readonly" readonly="readonly" name="storagename" /> 
			    			
			    		</td>
			    	</tr>
					
					<tr>
						<td style="text-align: right;">条形码：</td>
						<td><input type="text"
							id="storage-distributerejectDetail-barcode" readonly="readonly"
							style="width: 150px; border: 1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
							<td style="text-align: right;">备注：</td>
						<td><input type="text"  id="storage-DeliveryOutDetail-remark" name="remark"  style="width: 150px; border: 1px solid #B3ADAB; " /></td>
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
		var boxnum="";//箱装量 
		var basesaleprice="";//基准价
		var goodsid="";
		var unitid="";
		var goodsname="";//goodsname
		var summarybatchid="";
		var goodstype="";
		var maxnum=0;//最大量限制
		var auxunitid="";
		
		var produceddate = "" //生产日期
        var deadline = "" //截止日期
        var batchno= ""; //批次号
		
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
			
			if($("#storage-distributerejectDetail-unitnum").validatebox('isValid')){
					if(boxnum==""||basesaleprice==""){
						return false;
					}
					var num=$("#storage-distributerejectDetail-unitnum").val();
					if(num==""){
						return false;
					}
					var allPrivce=Number(num)*Number(basesaleprice)
					$("#storage-distributerejectDetail-taxamount").numberbox('setValue',allPrivce);
			}
			
		}
		
		function calculateNum(){
			if($("#storage-distributerejectDetail-auxnum").validatebox('isValid')){
				if(boxnum==""||basesaleprice==""){
					return false;
				}
				var num=$("#storage-distributerejectDetail-auxnum").val();//箱数
				var left=$("#storage-distributerejectDetail-unitnum-auxremainder").val(); //剩余数量
				var allAmount=Number(boxnum)*Number(num)+Number(left);
				$("#storage-distributerejectDetail-unitnum").val(Number(allAmount.toFixed(${decimallen})));
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
			$("#storage-distributerejectDetail-goodsid").focus();
  			var flag=$("#storage-form-distributerejectDetail").form('validate');
  			if(!flag){
	  			return false;
  			}
			var unitnum=$("#storage-distributerejectDetail-unitnum").val();
			if(unitnum==null || unitnum=="" ){
				$.messager.alert("提醒","抱歉，请填写数量！");
				return false;
			}
			var allNum=$("#storage-distributerejectDetail-unitnum").val();
			var num=$("#storage-distributerejectDetail-auxnum").val();//箱数
			var left=$("#storage-distributerejectDetail-unitnum-auxremainder").val(); //剩余数量
			var volume="";
			var weight="";
			
			
			var allPrice=$("#storage-distributerejectDetail-taxamount").numberbox('getValue');
			
			var object=getGoodsInfo();//获取商品体积,重量信息
			if(object!=null){
				if(object.singlevolume!=null&&object.singlevolume!=""){
			   		volume=Number(object.singlevolume)*Number(unitnum);//总体积
				}
				if(object.grossweight!=null&&object.grossweight!="")
					weight=Number(object.grossweight)*Number(unitnum);//重量
			}
			var formdata=$("#storage-form-distributerejectDetail").serializeJSON();
			formdata.goodsInfo = EditInitData.goodsInfo;
			formdata.taxamount = allPrice
			formdata.boxprice=Number(boxnum)*Number(basesaleprice);//金额
			formdata.auxunitname=$("#storage-distributerejectDetail-span-auxunitname").text();//辅单位
			formdata.volumn=volume;
			formdata.weight=weight;
			formdata.totalbox=(allNum/boxnum).toFixed(3);
			formdata.overnum=left;
			formdata.unitid=unitid;
			formdata.auxunitid=auxunitid;
			
			
			formdata.produceddate=produceddate
			formdata.deadline=deadline
			formdata.batchno=batchno
			
			formdata.auxnumdetail=parseInt($("#storage-distributerejectDetail-auxnum").val())+formdata.auxunitname+Number($("#storage-distributerejectDetail-unitnum-auxremainder").val())+$("#storage-distributerejectDetail-span-unitname").text();
			if(formdata){
				$("#storage-table-distributeRejectAddPage").datagrid('updateRow',{
					index:editRowIndex,
					row:formdata
				});
				footerReCalc();
			}
			EditInitData="";
			$("#storage-distributeRejectPage-dialog-DetailOper").dialog("close");
		}
		
  		$(document).ready(function(){
  			$("#storage-distributerejectDetail-goodsid").goodsWidget({
    			name:'t_storage_delivery_enter_detail',
    			col:'goodsid',
    			singleSelect:true,
    			width:150,
    			onSelect : function(data){
    				
    			}
    		});
  			
  			
  			$("#storage-distributerejectDetail-goodsid").goodsWidget("setValue",EditInitData.goodsid)
  			$("#storage-distributerejectDetail-goodsid").goodsWidget("setText",EditInitData.goodsname)
  			$("#storage-distributerejectDetail-goodsid").goodsWidget("readonly",true)
  			goodsid=EditInitData.goodsid;
  			boxnum=  EditInitData.goodsInfo.boxnum;
  			basesaleprice= EditInitData.basesaleprice;
  			unitid=EditInitData.unitid;
  			auxunitid=EditInitData.auxunitid;
  			
  			var str = "商品编码：<font color='green'>"+EditInitData.goodsid+"</font>"
  			//批次管理
  			if(EditInitData.batchno){
  				$("#storage-distributerejectDetail-batchno").val(EditInitData.batchno)
  				$("#storage-distributerejectDetail-storename").val(EditInitData.storagename)
  				$("#storage-distributerejectDetail-produceddate").val(EditInitData.produceddate)
  				$("#storage-distributerejectDetail-deadline").val(EditInitData.deadline)
  				str= '(批次管理)'+str
  				
  				$('#storage-distributerejectDetail-produceddate').validatebox({required:true});
				$("#storage-distributerejectDetail-produceddate").removeClass("WdateRead");
				$("#storage-distributerejectDetail-produceddate").addClass("Wdate");
				$("#storage-distributerejectDetail-produceddate").removeAttr("readonly");
				
				$("#storage-distributerejectDetail-deadline").removeClass("WdateRead");
				$("#storage-distributerejectDetail-deadline").addClass("Wdate");
				$("#storage-distributerejectDetail-deadline").removeAttr("readonly");
  				
  				$("#storage-distributerejectDetail-produceddate").click(function(){
					WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',
						onpicked:function(dp){
							if(dp.el.id=="storage-distributerejectDetail-produceddate"){
								var pro = dp.cal.getDateStr();
								var goodsid = $("#storage-distributerejectDetail-goodsid").goodsWidget("getValue");
								$.ajax({   
						            url :'storage/getBatchno.do',
						            type:'post',
						            data:{produceddate:pro,goodsid:EditInitData.goodsid},
						            dataType:'json',
						            async:false,
						            success:function(obj){
						            	produceddate = pro //生产日期
                						deadline = obj.deadline //截止日期
                						batchno=obj.batchno; //批次号
	                					$("#storage-distributerejectDetail-deadline").val(obj.deadline);//截止日期
	                					$("#storage-distributerejectDetail-batchno").val(obj.batchno); //批次号
                						
						            }
						        });
							}
						}
					});
			});
			
			$("#storage-distributerejectDetail-deadline").click(function(){
                    WdatePicker({dateFmt:'yyyy-MM-dd',
                        onpicked:function(dp){
                            if(dp.el.id=="storage-distributerejectDetail-deadline"){
                                var dead = dp.cal.getDateStr();
                                var goodsid = $("#storage-distributerejectDetail-goodsid").goodsWidget("getValue");
                                $.ajax({
                                    url :'storage/getBatchnoByDeadline.do',
                                    type:'post',
                                    data:{deadline:dead,goodsid:EditInitData.goodsid},
                                    dataType:'json',
                                    async:false,
                                    success:function(obj){
                                        $("#storage-distributerejectDetail-batchno").val(obj.batchno);
                                        $("#storage-distributerejectDetail-produceddate").val(obj.produceddate);
                						deadline = dead //截止日期
                                        produceddate = obj.produceddate //生产日期
                						batchno=obj.batchno; //批次号
	                					$("#storage-distributerejectDetail-produceddate").val( obj.produceddate);//生产日期
	                					$("#storage-distributerejectDetail-batchno").val(obj.batchno); //批次号
                                    }
                                });
                            }
                        }
                    });
            });
  		}
  			$("#storage-loading-distributerejectDetail").html(str);
  			
  			$("#storage-distributerejectDetail-span-auxunitname").text(EditInitData.auxunitname);
  			$("#storage-distributerejectDetail-span-unitname").text(EditInitData.unitname);
  			$("#storage-distributerejectDetail-unitname").val(EditInitData.unitname);
  			$("#storage-distributerejectDetail-auxunitname").val(EditInitData.auxunitname);
  			$("#storage-distributerejectDetail-boxnum").val(formatterBigNumNoLen(EditInitData.goodsInfo.boxnum));
  			if(EditInitData.goodsInfo.brandName==undefined){
  				$("#storage-distributerejectDetail-brand").val(EditInitData.goodsInfo.brandname);
  			}else{
  				$("#storage-distributerejectDetail-brand").val(EditInitData.goodsInfo.brandName);
  			}
  			
  			$("#storage-distributerejectDetail-model").val(EditInitData.goodsInfo.model);
  			$("#storage-distributerejectDetail-basesaleprice").val(EditInitData.basesaleprice);
  			$("#storage-distributerejectDetail-taxamount").val(EditInitData.taxamount);
  			$("#storage-distributerejectDetail-barcode").val(EditInitData.goodsInfo.barcode);
  			$("#storage-distributerejectDetail-auxnum").val(parseInt(EditInitData.totalbox)); //箱数
  			$("#storage-distributerejectDetail-unitnum-auxremainder").val(formatterBigNumNoLen(EditInitData.overnum));//主数量剩余
  			$("#storage-distributerejectDetail-unitnum").val(formatterBigNumNoLen(EditInitData.unitnum)); //数量
  			$("#storage-DeliveryOutDetail-remark").val(EditInitData.remark); //数量
  			
  			
  			
			$("#storage-distributerejectDetail-unitnum").change(function(){
					if($(this).validatebox('isValid')){
	    				var value=$(this).val();
	    				if(Number(value)<0)  return false;
	    				if(boxnum=="") return false;
	    				var num=Math.floor(Number(value)/Number(boxnum));//箱数
	    				var left=Number(value)%Number(boxnum);//剩余
	    				$("#storage-distributerejectDetail-auxnum").val(num);
	    				$("#storage-distributerejectDetail-unitnum-auxremainder").val(Number(left.toFixed(${decimallen})));
	    				$("#storage-distributerejectDetail-unitnum").val(Number(value));
	    				calculatePrice();
	        		}
					
			});
			
			$("#storage-distributerejectDetail-auxnum").change(function(){
				if($(this).validatebox('isValid')){
					calculateNum()
				}
			});
			
			$("#storage-distributerejectDetail-unitnum-auxremainder").change(function(){
				if($(this).validatebox('isValid')){
					calculateNum()
					if(Number($(this).val())>=Number(boxnum)){
						var allNum=$("#storage-distributerejectDetail-unitnum").val();
						$("#storage-distributerejectDetail-auxnum").val(Math.floor(Number(allNum)/Number(boxnum)));
						$("#storage-distributerejectDetail-unitnum-auxremainder").val(Number((Number(allNum)%Number(boxnum)).toFixed(${decimallen})));
					}
				}
			});


  		<c:if test="${billtype==1}">
  		$("#storage-distributerejectDetail-auxnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			$("storage-distributerejectDetail-auxnum").blur();
	   			$("#storage-distributerejectDetail-unitnum-auxremainder").focus();
	   			$("#storage-distributerejectDetail-unitnum-auxremainder").select();
	   			
			}
	    });
  		
  		$("#storage-distributerejectDetail-unitnum-auxremainder").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			$("storage-distributerejectDetail-unitnum-auxremainder").blur();
	   			$("#storage-distributerejectDetail-unitnum").focus();
	   			$("#storage-distributerejectDetail-unitnum").select();
	   			
			}
	    });
	    
	    $("#storage-distributerejectDetail-unitnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			$("storage-distributerejectDetail-unitnum").blur();
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
	    
	    
	    
  		$("#storage-distributerejectDetail-auxnum").focus();
  		$("#storage-distributerejectDetail-auxnum").select();
  		$("#storage-distributerejectDetail-addSaveGoOn").click(function(){
  			saveOrderDetail(false)
  		})
		</c:if>
		
		
  		<c:if test="${billtype==2}">
  		 $("#storage-distributerejectDetail-unitnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			$("storage-distributerejectDetail-unitnum").blur();
  				$("#storage-distributerejectDetail-auxnum").focus();
  				$("#storage-distributerejectDetail-auxnum").select();
			}
	    });
  		
  		$("#storage-distributerejectDetail-auxnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			$("storage-distributerejectDetail-auxnum").blur();
	   			$("#storage-distributerejectDetail-unitnum-auxremainder").focus();
	   				$("#storage-distributerejectDetail-unitnum-auxremainder").select();
	   			
			}
	    });
  		
  		$("#storage-distributerejectDetail-unitnum-auxremainder").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
	   			$("storage-distributerejectDetail-unitnum-auxremainder").blur();
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
  		$("#storage-distributerejectDetail-unitnum").focus();
  		$("#storage-distributerejectDetail-unitnum").select();
  		$("#storage-distributerejectDetail-addSaveGoOn").click(function(){
  			saveOrderDetail(false)
  		})
  		</c:if>
  	});
  	</script>
</body>
</html>
