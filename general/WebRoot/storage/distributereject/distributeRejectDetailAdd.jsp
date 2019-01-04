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
				<table style="border-collapse: collapse;" border="0" cellpadding="5"cellspacing="5">
					<tr>
						<td style="text-align: right;">&nbsp;&nbsp;&nbsp;商品：</td>
						<td><input type="text"
							id="storage-distributerejectDetail-goodsid" name="goodsid"
							style="width: 150px;" tabindex="1" /> <input type="hidden"
							id="storage-distributerejectDetail-goodsname" name="name" /></td>
						<td colspan="2" id="storage-loading-distributerejectDetail"></td>
					</tr>
					<tr>
						<c:if test="${billtype==1 }">
						<td style="text-align: right;">辅数量：</td>
						<td style="text-align: left;"><input type="text" id="storage-distributerejectDetail-auxnum" name="auxnum" value="0" class="easyui-validatebox" validType="integer" data-options="required:true" style="width: 60px; float: left;" />
							<span id="storage-distributerejectDetail-span-auxunitname" style="float: left; line-height: 25px;">&nbsp;</span> 
							<input type="text" id="storage-distributerejectDetail-unitnum-auxremainder" name="auxremainder" value="0" class="easyui-validatebox" validType="intOrFloatNum[${decimallen}]" data-options="required:true" style="width: 60px; float: left;" />
							<span id="storage-distributerejectDetail-span-unitname" style="float: left; line-height: 25px;">&nbsp;</span></td>
						<td style="text-align: right;">&nbsp;&nbsp;&nbsp;&nbsp;数量：</td>
						<td><input type="text" id="storage-distributerejectDetail-unitnum" name="unitnum" value="0" class="easyui-validatebox" validType="intOrFloatNum[${decimallen}]" data-options="required:true" style="width: 150px;" /></td>
						</c:if>	
						<c:if test="${billtype==2 }">
							<td style="text-align: right;">&nbsp;&nbsp;&nbsp;&nbsp;数量：</td>
						<td>
							<input type="text" id="storage-distributerejectDetail-unitnum" name="unitnum" value="0" class="easyui-validatebox" validType="intOrFloatNum[${decimallen}]" data-options="required:true" style="width: 150px;" />
						</td>
						<td style="text-align: right;">&nbsp;&nbsp;&nbsp;辅数量：</td>
						<td style="text-align: left;"><input type="text" id="storage-distributerejectDetail-auxnum" name="auxnum"  value="0" class="easyui-validatebox" validType="integer" data-options="required:true" style="width: 60px; float: left;" />
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
					
<!-- 			    	<td style="text-align: right;" >所属仓库：</td> -->
	    			<input id="storage-EnterDetail-store" type="hidden" class="len150 readonly" readonly="readonly" name="storagename" /> 
					
					
					<tr>
		   				<td style="text-align: right;" >生产日期：</td>
		   				<td>
		   					<input type="text" id="storage-EnterDetail-produceddate" style="height: 20px;" name="produceddate" class="WdateRead" readonly="readonly"/>
		   				</td>
		   				<td style="text-align: right;" >截止日期：</td>
		   				<td>
		   					<input type="text" id="storage-EnterDetail-deadline" style="height: 20px;" name="deadline" class="WdateRead" readonly="readonly"/>
		   				</td>
		   			</tr>
		   			
		   			<tr>
		   				<td style="text-align: right;">批次号：</td>
		   				<td>
		   					<input type="text" id="storage-EnterDetail-batchno" name="batchno" class="no_input" readonly="readonly"/>
		   				</td>
		   				<td style="text-align: right;" >所属库位：</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="storage-EnterDetail-storagelocationid" name="storagelocationid"/>
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
				快捷键<span style="font-weight: bold; color: red;">Ctrl+Enter</span>或者<span
					style="font-weight: bold; color: red;">+</span><input type="button"
					value="确定" name="savegoon"
					id="storage-distributerejectDetail-addSaveGoOn" /> 
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var boxnum="";//箱装量 
		var basesaleprice="";//基准价
		var goodsid="";
		var unitid="";//主计量单位
		var goodsname="";//goodsname
		var summarybatchid="";
		var goodstype="";
		var maxnum=0;//最大量限制
		var auxunitid="";
		var buyprice="";
		
		var batchno = "" //批次号
		var produceddate = ""  //生产日期
		var deadline = "" //截止日期
		var storagename = ""
		
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
  			var $DetailOper=$("#storage-distributeRejectPage-dialog-DetailOper");
  			$DetailOper.dialog({
  				title:'商品信息新增',
  			    width: 600,  
  			    height: 320,
  			    closed: true,  
  			    cache: false, 
  			    modal: true,
  			    maximizable:true,
  			    href:"storage/distrtibution/distributeRejectDetailAdd.do?supplierid=${supplierid}&billtype=${billtype}"
  			});
  			$DetailOper.dialog("open");
  		}
 
		function saveOrderDetail(isGoOn){
			$("#storage-distributerejectDetail-goodsid").focus();
  			var flag=$("#storage-form-distributerejectDetail").form('validate');
  			if(!flag){
	  			return false;
  			}

  			var goodsInfo=$("#storage-distributerejectDetail-goodsid").goodsWidget('getObject');
  			if(goodsInfo==null || goodsInfo==""){
				$.messager.alert("提醒","抱歉，请选择商品！");
				return false;		  			
  			}
			var unitnum=$("#storage-distributerejectDetail-unitnum").val();
			if(unitnum==null || unitnum=="" || unitnum==0){
				$.messager.alert("提醒","抱歉，请填写数量！");
				return false;
			}
			var object=getGoodsInfo();
			var allNum=$("#storage-distributerejectDetail-unitnum").val();//总数量
			var num=$("#storage-distributerejectDetail-auxnum").val();//箱数
			var left=$("#storage-distributerejectDetail-unitnum-auxremainder").val(); //剩余数量
			var volume="";
			var weight="";
			if(object!=null){
				if(object.totalvolume!=null&&object.totalvolume!=""&&object.singlevolume!=null&&object.singlevolume!=""){
			   		volume=Number(object.singlevolume)*Number(unitnum);//总体积
				}
				if(object.totalweight!=null&&object.totalweight!=""&&object.grossweight!=null&&object.grossweight!="")
					   weight=Number(object.grossweight)*Number(unitnum);//重量
			}
			var formdata=$("#storage-form-distributerejectDetail").serializeJSON();
			formdata.goodsInfo = goodsInfo;
			formdata.price=basesaleprice;
			formdata.boxprice=Number(boxnum)*Number(basesaleprice);//金额
			formdata.auxunitname=$("#storage-distributerejectDetail-span-auxunitname").text();//辅单位
			formdata.volumn=volume;
			formdata.price=basesaleprice;
			formdata.weight=weight;
			formdata.goodsname=goodsname;
			formdata.summarybatchid=summarybatchid;
			formdata.totalbox=(allNum/boxnum).toFixed(3);
			formdata.overnum=left;
			formdata.unitid=unitid;
			formdata.goodssort=goodstype;
		    formdata.buyprice=buyprice;
		    formdata.storagename=storagename;
			formdata.produceddate=produceddate;
			formdata.deadline=deadline;
			formdata.batchno=batchno;
			
			var remark=$("#storage-DeliveryOutDetail-remark").val();
			formdata.remark=remark;
			formdata.auxnumdetail=parseInt($("#storage-distributerejectDetail-auxnum").val())+formdata.auxunitname+Number($("#storage-distributerejectDetail-unitnum-auxremainder").val())+$("#storage-distributerejectDetail-span-unitname").text();
			if(formdata){
				var index=getAddRowIndex();
				$("#storage-table-distributeRejectAddPage").datagrid('updateRow',{
					index:index,
					row:formdata
				});
				footerReCalc();
				if(index>=14){
					var rows=$("#storage-table-distributeRejectAddPage").datagrid('getRows');
					if(index == rows.length - 1){
						$("#storage-table-distributeRejectAddPage").datagrid('appendRow',{});
					}
				}
			}
			if(isGoOn){
				orderDetailAddSaveGoOnDialog();
			}else{
	  			$("#storage-distributeRejectPage-dialog-DetailOper").dialog("close");
			}
		}
		
  		$(document).ready(function(){
  			var storageid=$("#storage-distributeRejectAddPage-storage").widget('getValue');
  			
  			$("#storage-EnterDetail-deadline").attr("disabled","disabled");
			$("#storage-EnterDetail-produceddate").attr("disabled","disabled");
  			
  			$("#storage-distributerejectDetail-goodsid").goodsWidget({
    			param:[
					{field:'storageid',op:'equal',value:storageid}
				],
				queryAllBySupplier:"${supplierid}",
				onClear:function(){
               		$("#storage-distributerejectDetail-auxunitname").val("") 
  					$("#storage-distributerejectDetail-span-auxunitname").text("");
  					$("#storage-distributerejectDetail-unitname").val("");
  		        	$("#storage-distributerejectDetail-unitnum").text(""); 
  					$("#storage-distributerejectDetail-model").val("");
 		            $("#storage-distributerejectDetail-brand").val("");
 		            $("#storage-distributerejectDetail-basesaleprice").numberbox('setValue','0.00');//基准单价
                    $("#storage-distributerejectDetail-barcode").val("");
                    $("#storage-distributerejectDetail-boxnum").val("");
 		            $("#storage-EnterDetail-batchno").val("")
   					$("#storage-EnterDetail-store").val("");
                    $("#storage-EnterDetail-produceddate").val("");
                    $("#storage-EnterDetail-deadline").val("");
                    
                    $("#storage-EnterDetail-storagelocationid").val("")
                    
                },
    			onSelect : function(data){
    				if(data){
        				$("#storage-distributerejectDetail-taxamount").numberbox('setValue',"0.00");
        				$("#storage-distributerejectDetail-auxnum").val(0);
        				$("#storage-distributerejectDetail-unitnum-auxremainder").val(0);
        				$("#storage-distributerejectDetail-unitnum").val(0);
    					//箱装量
        				$("#storage-distributerejectDetail-boxnum").val(data.boxnum);
    					//二维码
        				$("#storage-distributerejectDetail-barcode").val(data.barcode);
    					var isbatch = data.isbatch;

    					$.ajax({
       		               url :'storage/deliveryout/getDeliveryOutGoodsInfo.do?goodsid='+data.id+"&customerid=${customerid}",
       		               type:'post',
       		               dataType:'json',
       		               async:false,
       		               success:function(rs){

       		               		//辅单位
       		               		$("#storage-distributerejectDetail-auxunitname").val(rs.auxunitname)
    		  					$("#storage-distributerejectDetail-span-auxunitname").text(rs.auxunitname);
    		  					//主单位
        		        		$("#storage-distributerejectDetail-unitname").val(rs.mainunitName);
    		  					$("#storage-distributerejectDetail-span-unitname").text(rs.mainunitName);
    		  					//商品规格
    		  					$("#storage-distributerejectDetail-model").val(rs.model);
    		  					//品牌名称
       		               		$("#storage-distributerejectDetail-brand").val(rs.brandName);

	       		            	goodstype=rs.defaultsort; //商品类型
	       		            	buyprice=rs.highestbuyprice;
	       		            	unitid=rs.mainunit; //主计量单位id
	       		            	<c:if test="${billtype=='1'}">
	       		            	basesaleprice=rs.highestbuyprice;
	       		            	$("#storage-distributerejectDetail-basesaleprice").numberbox('setValue',rs.highestbuyprice);//最高采购价(采购出库)
	       		            	</c:if>

	       		            	<c:if test="${billtype=='2'}">
	       		            	basesaleprice=rs.basesaleprice;
	       		            	$("#storage-distributerejectDetail-basesaleprice").numberbox('setValue',rs.basesaleprice);//基准单价
	       		            	</c:if>
       		               }
       		           	 });
        				goodsname=data.name; //商品名称
        				goodsid=data.id; //商品编号
        				boxnum=data.boxnum; //箱装量
        				auxunitid=data.auxunitid; //辅计量单位


        				$("#storage-EnterDetail-batchno").val("")
    					$("#storage-EnterDetail-store").val("");
                        $("#storage-EnterDetail-produceddate").val("");
                        $("#storage-EnterDetail-deadline").val("");


        				//判断商品是否批次管理 1是
        				var str = "商品编码：<font color='green'>"+data.id+"</font>"
	        			if(data.isbatch=="1"){
	        				str= '(批次管理)'+str;
							$("#storage-EnterDetail-storagelocationid").widget("clear");
							$("#storage-EnterDetail-storagelocationid").widget("enable");
// 							$('#storage-EnterDetail-batchno').validatebox({required:true});
// 							$("#storage-EnterDetail-batchno").removeClass("no_input");
// 							$("#storage-EnterDetail-batchno").removeAttr("readonly");

							$('#storage-EnterDetail-produceddate').validatebox({required:true});
							$("#storage-EnterDetail-produceddate").removeClass("WdateRead");
							$("#storage-EnterDetail-produceddate").addClass("Wdate");
							$("#storage-EnterDetail-produceddate").removeAttr("readonly");
							$("#storage-EnterDetail-produceddate").removeAttr("disabled");
							$("#storage-EnterDetail-deadline").removeClass("WdateRead");
							$("#storage-EnterDetail-deadline").addClass("Wdate");
							$("#storage-EnterDetail-deadline").removeAttr("readonly");
							$("#storage-EnterDetail-deadline").removeAttr("disabled");

						}else{
							$("#storage-EnterDetail-storagelocationid").widget("clear");
							$("#storage-EnterDetail-storagelocationid").widget("disable");

							$('#storage-EnterDetail-batchno').validatebox({required:false});
							$("#storage-EnterDetail-batchno").addClass("no_input");
							$("#storage-EnterDetail-batchno").attr("readonly","readonly");

							$('#storage-EnterDetail-produceddate').validatebox({required:false});
							$("#storage-EnterDetail-produceddate").removeClass("Wdate");
							$("#storage-EnterDetail-produceddate").addClass("WdateRead");
							$("#storage-EnterDetail-produceddate").attr("readonly","readonly");
							$("#storage-EnterDetail-deadline").attr("disabled","disabled");

							$("#storage-EnterDetail-deadline").removeClass("Wdate");
							$("#storage-EnterDetail-deadline").addClass("WdateRead");
							$("#storage-EnterDetail-deadline").attr("readonly","readonly");
							$("#storage-EnterDetail-deadline").attr("disabled","disabled");
						}
						$("#storage-loading-distributerejectDetail").html(str);

	        			$("#storage-EnterDetail-storagelocationid").widget("disable"); //库位先不考虑


        		        <c:if test="${billtype=='1'}">
        				$("#storage-distributerejectDetail-auxnum").focus();
        				$("#storage-distributerejectDetail-auxnum").select();
        				</c:if>
        				<c:if test="${billtype=='2'}">
        				$("#storage-distributerejectDetail-unitnum").focus();
        				$("#storage-distributerejectDetail-unitnum").select();
        				</c:if>
        				
    				}
    			}
    		});
    		
    		$("#storage-EnterDetail-produceddate").click(function(){
					WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',
						onpicked:function(dp){
							if(dp.el.id=="storage-EnterDetail-produceddate"){
								var pro = dp.cal.getDateStr();
								var goodsid = $("#storage-distributerejectDetail-goodsid").goodsWidget("getValue");
								$.ajax({   
						            url :'storage/getBatchno.do',
						            type:'post',
						            data:{produceddate:pro,goodsid:goodsid},
						            dataType:'json',
						            async:false,
						            success:function(obj){
						            	produceddate = pro //生产日期
                						deadline = obj.deadline //截止日期
                						batchno=obj.batchno; //批次号
	                					$("#storage-EnterDetail-deadline").val(obj.deadline);//截止日期
	                					$("#storage-EnterDetail-batchno").val(obj.batchno); //批次号
                						
						            }
						        });
							}
						}
					});
			});
    		
    		$("#storage-EnterDetail-deadline").click(function(){
                    WdatePicker({dateFmt:'yyyy-MM-dd',
                        onpicked:function(dp){
                            if(dp.el.id=="storage-EnterDetail-deadline"){
                                var dead = dp.cal.getDateStr();
                                var goodsid = $("#storage-distributerejectDetail-goodsid").goodsWidget("getValue");
                                $.ajax({
                                    url :'storage/getBatchnoByDeadline.do',
                                    type:'post',
                                    data:{deadline:dead,goodsid:goodsid},
                                    dataType:'json',
                                    async:false,
                                    success:function(obj){
                                        $("#storage-purchaseEnter-batchno").val(obj.batchno);
                                        $("#storage-purchaseEnter-produceddate").val(obj.produceddate);
                                        
                						deadline = dead //截止日期
                                        produceddate = obj.produceddate //生产日期
                						batchno=obj.batchno; //批次号
	                					$("#storage-EnterDetail-produceddate").val( obj.produceddate);//生产日期
	                					$("#storage-EnterDetail-batchno").val(obj.batchno); //批次号
                                        
                                    }
                                });
                            }
                        }
                    });
            });
    		
    		
    		
    		
    		//库位
    		$("#storage-EnterDetail-storagelocationid").widget({
				name:'t_storage_delivery_enter_detail',
	    		width:165,
				col:'storagelocationid',
				singleSelect:true,
                disabled:true
			});
    		
    		
			$("#storage-distributerejectDetail-unitnum").change(function(){
					if($(this).validatebox('isValid')){
	    				var value=$(this).val();
	    				if(Number(value)<=0)  return false;
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
	   			saveOrderDetail(true)
			}
	    });
	    
  		
  		$("#storage-distributerejectDetail-addSaveGoOn").click(function(){
  			saveOrderDetail(true)
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
