<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
  	<input type="hidden" id="purchase-backid-limitPriceOrderAddPage" value="${id }" />
  	<div id="purchase-limitPriceOrderPage-layout" class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="purchase-buttons-limitPriceOrderPage"></div>
    	</div>
    	<div data-options="region:'center'">
    		<div class="easyui-panel" data-options="fit:true" id="purchase-panel-limitPriceOrderPage">
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
	var limitPriceOrder_type = '${type}';
	limitPriceOrder_type=$.trim(limitPriceOrder_type.toLowerCase());
	if(limitPriceOrder_type==""){
		limitPriceOrder_type='add';
	}
	var limitPriceOrder_url = "purchase/limitpriceorder/limitPriceOrderAddPage.do";
	if(limitPriceOrder_type == "view"){
		limitPriceOrder_url = "purchase/limitpriceorder/limitPriceOrderViewPage.do?id=${id}";
	}
	if(limitPriceOrder_type == "edit"){
		limitPriceOrder_url = "purchase/limitpriceorder/limitPriceOrderEditPage.do?id=${id}";
	}
	if(limitPriceOrder_type == "copy"){
		limitPriceOrder_url = "purchase/limitpriceorder/limitPriceOrderCopyPage.do?id=${id}";
	}
	var pageListUrl="/purchase/limitpriceorder/limitPriceOrderListPage.do";
	function limitPriceOrder_tempSave_form_submit(){
		$("#purchase-form-limitPriceOrderAddPage").form({
		    onSubmit: function(){  
	  		  	loading("提交中..");
	  		},  
	  		success:function(data){
	  		  	loaded();
	  		  	var json = $.parseJSON(data);
	  		    if(json.flag==true){
	  		    	limitPriceOrder_RefreshDataGrid();
	  		      	$.messager.alert("提醒","暂存成功");
	  		      	$("#purchase-backid-limitPriceOrderAddPage").val(json.backid);
	  		      	if(json.opertype && json.opertype == "add"){
	  		      		$("#purchase-buttons-limitPriceOrderPage").buttonWidget("addNewDataId", json.backid);
	  		      	}
	  		      	$("#purchase-panel-limitPriceOrderPage").panel('refresh', 'purchase/limitpriceorder/limitPriceOrderEditPage.do?id='+ json.backid);
	  		    }
	  		    else{
	  		       	$.messager.alert("提醒","暂存失败");
	  		    }
	  		}
	  	});
	}
	function limitPriceOrder_realSave_form_submit(){
		$("#purchase-form-limitPriceOrderAddPage").form({
		    onSubmit: function(){  
		    	var flag = $(this).form('validate');
	  		   	if(flag==false){
	  		   		return false;
	  		   	}
	  		  	loading("提交中..");
	  		},  
	  		success:function(data){
	  		  	loaded();
	  		  	var json = $.parseJSON(data);
	  		    if(json.flag==true){
	  		    	limitPriceOrder_RefreshDataGrid();
	  		      	$.messager.alert("提醒","保存成功");
	  		      	$("#purchase-backid-limitPriceOrderAddPage").val(json.backid);
	  		      	if(json.opertype && json.opertype == "add"){
	  		      		$("#purchase-buttons-limitPriceOrderPage").buttonWidget("addNewDataId", json.backid);
	  		      	}
	  		      	$("#purchase-panel-limitPriceOrderPage").panel('refresh', 'purchase/limitpriceorder/limitPriceOrderEditPage.do?id='+ json.backid);
	  		    }
	  		    else{
		  		    if(json.msg){
	  		       		$.messager.alert("提醒","保存失败!"+json.msg);
		  		    }else{
	  		       		$.messager.alert("提醒","保存失败");
		  		    }
	  		    }
	  		}
	  	});
	}
	function limitPriceOrder_RefreshDataGrid(){
		try{			
			tabsWindowURL(pageListUrl).$("#purchase-table-limitPriceOrderListPage").datagrid('reload');
		}catch(e){
		}
	}

	function isLockData(id,tname){
		var flag = false;
		$.ajax({
            url :'system/lock/unLockData.do',
            type:'post',
            data:{id:id,tname:tname},
            dataType:'json',
            async: false,
            success:function(json){
            	flag = json.flag
            }
        });
        return flag;
	}
	function orderDetailAddDialog(){
	   	$('<div id="purchase-limitPriceOrderAddPage-dialog-DetailOper-content"></div>').appendTo('#purchase-limitPriceOrderAddPage-dialog-DetailOper');
  		var $DetailOper=$("#purchase-limitPriceOrderAddPage-dialog-DetailOper-content");
		$DetailOper.dialog({
			title:'商品信息新增(按ESC退出)',
		    width: 600,  
		    height: 350,
		    closed: true,  
		    cache: false, 
		    modal: true,
		    maximizable:true,
		    resizable:true,
		    href:"purchase/limitpriceorder/limitPriceOrderDetailAddPage.do",
		    onLoad:function(){
	    		$("#purchase-limitPriceOrderDetail-goodsid").focus();
    		},
		    onClose:function(){
	            $DetailOper.dialog("destroy");
	        }
		});
		$DetailOper.dialog("open");
  	}
  	function orderDetailEditDialog(initdata){
	   	$('<div id="purchase-limitPriceOrderAddPage-dialog-DetailOper-content"></div>').appendTo('#purchase-limitPriceOrderAddPage-dialog-DetailOper');
  		var $DetailOper=$("#purchase-limitPriceOrderAddPage-dialog-DetailOper-content");
		$DetailOper.dialog({
			title:'商品信息修改(按ESC退出)',
		    width: 600,  
		    height: 350,
		    closed: true,  
		    cache: false, 
		    modal: true,
		    maximizable:true,
		    resizable:true,
		    href:"purchase/limitpriceorder/limitPriceOrderDetailEditPage.do",
		    onLoad:function(){
		    	try{
				    if(initdata!=null && initdata.goodsid!=null && initdata.goodsid!=""){ 
				    	if($("#purchase-form-limitPriceOrderDetailEditPage").size()>0){
						    if(initdata.goodsInfo){
						    	$("#purchase-form-limitPriceOrderDetailEditPage").form('load',initdata.goodsInfo);
						    }
			  	  			$("#purchase-form-limitPriceOrderDetailEditPage").form('load',initdata);
					    }
			  		}	
			    }catch(e){
			    }

	    		$("#purchase-limitPriceOrderDetail-priceasleft").focus();
	    		$("#purchase-limitPriceOrderDetail-priceasleft").select();
		    },
		    onClose:function(){
	            $DetailOper.dialog("destroy");
	        }
		});
		$DetailOper.dialog("open");
  	}
  	function checkAfterAddGoods(goodsid){
  	  	if(goodsid==null || goodsid==""){
  	  	  	return false;
  	  	}
  	  	var $ordertable=$("#purchase-limitPriceOrderAddPage-limitPriceOrdertable");
  	  	var flag=true;
  	  	if($ordertable.size()>0){
  	  	  	var data=$ordertable.datagrid('getRows');
  	  	  	if(data!=null && data.length>0){
	  	  	  	for(var i=0;i<data.length;i++){
	  	  	  	  	if(data[i].goodsid==goodsid){
	  	  	  	  	  	flag=false;
	  	  	  	  	  	break;
	  	  	  	  	}
	  	  	  	}
  	  	  	}
  	  	}
  	  	return flag;
  	}
  	function checkExistsLimitPrice(goodsid,id){
		var efstartdate=$("#purchase-limitPriceOrderAddPage-effectstartdate").val();
		var efenddate=$("#purchase-limitPriceOrderAddPage-effectenddate").val();
		if(id==null){
			id="";
		}
  	  	var flag=false;
  		try{
  			$.ajax({   
	            url :'purchase/limitpriceorder/existLimitPriceOrder.do',
	            type:'post',
	            dataType:'json',
		        async: false,
	            data:{id:id,goodsid:goodsid,efstartdate:efstartdate,efenddate:efenddate},
	            success:function(json){
		            if(json==null){
			            return false;
		            }
		            if(json.flag){
			            flag=true;
			            var htmlsb=new Array();
			            htmlsb.push("抱歉，编码为");
			            htmlsb.push(goodsid);
			  			htmlsb.push("商品,已经处于调价状态。");
			  			htmlsb.push("<br/>调价单据编号：");
			  			htmlsb.push(json.orderid);
			  			htmlsb.push("，调价有效期");
			  			htmlsb.push(json.efstartdate);
			  			htmlsb.push("至");
			  			if(json.efenddate && json.efenddate!=""){
				  			htmlsb.push(json.efenddate);
			  			}else{
				  			htmlsb.push("--");
			  			}
			  			htmlsb.push("。");

		  		      	$.messager.alert("提醒",htmlsb.join(""));
		            }
	            }
	        });
  		}catch(e){
  		}
  		return flag;
  	}
	function checkGoodsDetailEmpty(){
		var flag=true;
  	  	var $ordertable=$("#purchase-limitPriceOrderAddPage-limitPriceOrdertable");
		var data = $ordertable.datagrid('getRows');
		for(var i=0;i<data.length;i++){
			if(data[i].goodsid && data[i].goodsid!=""){
				flag=false;
				break;
			}
		}
		return flag;
	}
    $(document).ready(function(){
    	$("#purchase-panel-limitPriceOrderPage").panel({
			href : limitPriceOrder_url,
		    cache:false,
		    maximized:true,
		    border:false
		});
		//按钮
		$("#purchase-buttons-limitPriceOrderPage").buttonWidget({
			initButton:[
				{},
				<security:authorize url="/purchase/limitpriceorder/limitPriceOrderAddBtn.do">
				{
					type:'button-add',
					handler: function(){
						$("#purchase-panel-limitPriceOrderPage").panel('refresh', 'purchase/limitpriceorder/limitPriceOrderAddPage.do');
					}
				},
				</security:authorize>
				<security:authorize url="/purchase/limitpriceorder/limitPriceOrderTempSave.do">
				{
					type:'button-hold',
					handler: function(){
						$("#purchase-limitPriceOrderAddPage-addType").val("temp");
						var datarows=$("#purchase-limitPriceOrderAddPage-limitPriceOrdertable").datagrid('getRows');
						if(datarows!=null && datarows.length>0){
							$("#purchase-limitPriceOrderAddPage-limitPriceOrderDetails").val(JSON.stringify(datarows));
						}
						limitPriceOrder_tempSave_form_submit();
						$("#purchase-form-limitPriceOrderAddPage").submit();
					}
				},
				</security:authorize>
				<security:authorize url="/purchase/limitpriceorder/limitPriceOrderRealSave.do">
				{
					type:'button-save',
					handler: function(){
						if(checkGoodsDetailEmpty()){
			            	$.messager.alert("提醒","抱歉，请填写采购调价单商品信息");
			            	return false;
						}else{
							$("#purchase-limitPriceOrderAddPage-addType").val("real");
							var datarows=$("#purchase-limitPriceOrderAddPage-limitPriceOrdertable").datagrid('getRows');
							if(datarows!=null && datarows.length>0){
								$("#purchase-limitPriceOrderAddPage-limitPriceOrderDetails").val(JSON.stringify(datarows));
							}
							limitPriceOrder_realSave_form_submit();
							$("#purchase-form-limitPriceOrderAddPage").submit();
						}
					}
				},
				</security:authorize>
				<security:authorize url="/purchase/limitpriceorder/limitPriceOrderGiveUpBtn.do">
	 			{
					type:'button-giveup',//放弃 
	 				handler:function(){
	 					var $polbuttons=$("#purchase-buttons-limitPriceOrderPage");
		 				var type = $polbuttons.buttonWidget("getOperType");
		 				if(type=="add"){
							var id = $("#purchase-backid-limitPriceOrderAddPage").val();
							if(id == ""){
								tabsWindowTitle(pageListUrl);
							}else{
				 				$("#purchase-panel-limitPriceOrderPage").panel('refresh', 'purchase/limitpriceorder/limitPriceOrderEditPage.do?id='+id);
							}
		 				}else if(type=="edit"){
							var id = $("#purchase-backid-limitPriceOrderAddPage").val();
							if(id == ""){
								return false;
							}
		 					var flag = isLockData(id,"t_purchase_limitPriceorder");
			 				if(!flag){
			 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
			 					return false;
			 				}
			 				$("#purchase-panel-limitPriceOrderPage").panel('refresh', 'purchase/limitpriceorder/limitPriceOrderEditPage.do?id='+id);
		 				}
		 			}
	 			},
	 			</security:authorize>
				<security:authorize url="/purchase/limitpriceorder/deleteLimitPriceOrder.do">
				{
					type:'button-delete',
					handler: function(){
						var id = $("#purchase-backid-limitPriceOrderAddPage").val();
						if(id == ""){
							return false;
						}

						$.messager.confirm("提醒","是否删除该采购调价单信息？",function(r){
							if(r){
								loading("删除中..");
								$.ajax({   
						            url :'purchase/limitpriceorder/deleteLimitPriceOrder.do?id='+ id,
						            type:'post',
						            dataType:'json',
						            success:function(json){
						            	loaded();
							            if(json.flag){
						            		$.messager.alert("提醒", "删除成功");
						            		var nextdata = $("#purchase-buttons-limitPriceOrderPage").buttonWidget("removeData",id);
						            		if(null!=nextdata && nextdata.id && nextdata.id!=""){
						            			$("#purchase-backid-limitPriceOrderAddPage").val(nextdata.id);
								 				$("#purchase-panel-limitPriceOrderPage").panel('refresh', 'purchase/limitpriceorder/limitPriceOrderEditPage.do?id='+nextdata.id);
									            limitPriceOrder_RefreshDataGrid();
						            		}else{
									            limitPriceOrder_RefreshDataGrid();
						            			parent.closeNowTab();
								 				//$("#purchase-panel-limitPriceOrderPage").panel('refresh', 'purchase/limitpriceorder/limitPriceOrderAddPage.do');						            		
						            		}						            		
							            }else{
							            	$.messager.alert("提醒","删除失败");
							            }
						            }
						        });	
							}
						});
					}
				},
				</security:authorize>
				<security:authorize url="/purchase/limitpriceorder/auditLimitPriceOrder.do">
				{
					type:'button-audit',
					handler: function(){
						var id = $("#purchase-backid-limitPriceOrderAddPage").val();
						if(id == ""){
							return false;
						}
						if(checkGoodsDetailEmpty()){
			            	$.messager.alert("提醒","抱歉，请填写采购调价单商品信息");
			            	return false;
						}
						$.messager.confirm("提醒","是否审核通过该采购调价单信息？",function(r){
							if(r){
								loading("审核中..");
								$.ajax({   
						            url :'purchase/limitpriceorder/auditLimitPriceOrder.do?id='+ id,
						            type:'post',
						            dataType:'json',
						            success:function(json){
						            	loaded();
							            if(json.flag){
						            		$.messager.alert("提醒", "审核成功");						            		
							            }else{
								            if(json.limitpriceflag && json.limitpriceflag=="1"){
								            	var htmlsb=new Array();
									            try{
										            htmlsb.push("抱歉,审核失败");
										            htmlsb.push("，编码为");
										            htmlsb.push(json.goodsid);
										  			htmlsb.push("商品,已经处于调价状态。");
										  			htmlsb.push("<br/>调价单据编号：");
										  			htmlsb.push(json.orderid);
										  			htmlsb.push("，调价有效期");
										  			htmlsb.push(json.efstartdate);
										  			htmlsb.push("至");
										  			if(json.efenddate && json.efenddate!=""){
											  			htmlsb.push(json.efenddate);
										  			}else{
											  			htmlsb.push("--");
										  			}
										  			htmlsb.push("。");
									            }
									            catch(e){
										            htmlsb=new Array();
										            htmlsb.push("抱歉,审核失败");
									            }
	
								  		      	$.messager.alert("提醒",htmlsb.join(""));
								            }else{
								            	$.messager.alert("提醒","审核失败");
								            }
							            }
							            limitPriceOrder_RefreshDataGrid();
						 				$("#purchase-panel-limitPriceOrderPage").panel('refresh', 'purchase/limitpriceorder/limitPriceOrderEditPage.do?id='+id);
						            }
						        });	
							}
						});
					}
				},
				</security:authorize>
				<security:authorize url="/purchase/limitpriceorder/oppauditLimitPriceOrder.do">
				{
					type:'button-oppaudit',
					handler: function(){
						var id = $("#purchase-backid-limitPriceOrderAddPage").val();
						if(id == ""){
							return false;
						}
	
						$.messager.confirm("提醒","是否反审该采购调价单信息？",function(r){
							if(r){
								loading("反审中..");
								$.ajax({   
						            url :'purchase/limitpriceorder/oppauditLimitPriceOrder.do?id='+ id,
						            type:'post',
						            dataType:'json',
						            success:function(json){
						            	loaded();
							            if(json.flag){
						            		$.messager.alert("提醒", "反审成功");						            		
							            }else{
							            	$.messager.alert("提醒","反审失败");
							            }
							            limitPriceOrder_RefreshDataGrid();
						 				$("#purchase-panel-limitPriceOrderPage").panel('refresh', 'purchase/limitpriceorder/limitPriceOrderEditPage.do?id='+id);
						            }
						        });	
							}
						});
					}
				},
				</security:authorize>
				<security:authorize url="/purchase/limitpriceorder/limitPriceOrderWorkflow.do">
				{
					type: 'button-workflow',
					button:[
						{
							type: 'workflow-submit',
							handler: function(){
							
							}
						},
						{
							type: 'workflow-addidea',
							handler: function(){
							
							}
						},
						{
							type: 'workflow-viewflow',
							handler: function(){
							
							}
						},
						{
							type: 'workflow-viewflow-pic',
							handler: function(){
							
							}
						},
						{
							type: 'workflow-recover',
							handler: function(){
							
							}
						}
					]
				},
				</security:authorize>
				<security:authorize url="/purchase/limitpriceorder/LimitPriceOrderBack.do">
				{
					type:'button-back',
					handler: function(data){
					   	if(data!=null && data.id!=null && data.id!=""){
							$("#purchase-backid-limitPriceOrderAddPage").val(data.id);
							$("#purchase-panel-limitPriceOrderPage").panel('refresh','purchase/limitpriceorder/limitPriceOrderEditPage.do?id='+ data.id);
					   	}
					}
				},
				</security:authorize>
				<security:authorize url="/purchase/limitpriceorder/LimitPriceOrderNext.do">
				{
					type:'button-next',
					handler: function(data){
					   	if(data!=null && data.id!=null && data.id!=""){
							$("#purchase-backid-limitPriceOrderAddPage").val(data.id);
							$("#purchase-panel-limitPriceOrderPage").panel('refresh','purchase/limitpriceorder/limitPriceOrderEditPage.do?id='+ data.id);
					   	}
					}
				},
				</security:authorize>
				<security:authorize url="/purchase/limitpriceorder/LimitPriceOrderPreview.do">
				{
					type:'button-preview'
				},
				</security:authorize>
				<security:authorize url="/purchase/limitpriceorder/LimitPriceOrderPrint.do">
				{
					type:'button-print'
				},
				</security:authorize>
				{}
			],
			layoutid:'purchase-limitPriceOrderPage-layout',
			model: 'bill',
			type:'view',
			taburl:pageListUrl,
			id:'${id}',
			datagrid:'purchase-table-limitPriceOrderListPage',
			tname:'t_purchase_limitPriceorder'
		});

		$(document).bind('keydown', 'esc',function (){
			if($("#purchase-limitPriceOrderAddPage-dialog-DetailOper-content").size()>0){
				$("#purchase-limitPriceOrderAddPage-dialog-DetailOper-content").dialog("close");
			}
	    });
		$(document).bind('keydown', 'ctrl+enter',function (){
	    	$("#purchase-limitPriceOrderDetail-remark").focus();
	    	setTimeout(function(){
		    	$("#purchase-limitPriceOrderDetailAddPage-addSaveGoOn").trigger("click");
		    	$("#purchase-limitPriceOrderDetailEditPage-editSave").trigger("click");
	    	},200);
	    	return false;
	    });
	    $(document).bind('keydown', '+',function (){
	    	$("#purchase-limitPriceOrderDetail-remark").focus();
	    	setTimeout(function(){
		    	$("#purchase-limitPriceOrderDetailAddPage-addSaveGoOn").trigger("click");
		    	$("#purchase-limitPriceOrderDetailEditPage-editSave").trigger("click");
	    	},200);
	    	return false;
	    });
	});
    </script>
  </body>
</html>
