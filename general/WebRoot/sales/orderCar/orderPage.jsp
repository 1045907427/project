<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>零售订单页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  <body>
  	<input type="hidden" id="sales-backid-orderPage" value="${id }" />
    <div id="sales-layout-orderPage" class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="sales-buttons-orderPage" style="height:26px;"></div>
    	</div>
    	<div data-options="region:'center',border:false">
    		<div id="sales-panel-orderPage">
    		</div>
    	</div>
    </div>
  	<div class="easyui-dialog" id="sales-dialog-orderPage" closed="true"></div>
    <script type="text/javascript">
    var wareListJson = $("#sales-datagrid-orderAddPage").createGridColumnLoad({
			frozenCol : [[]],
			commonCol : [[
			    		{field:'id',hidden:true},
			    		{field:'goodsid',title:'商品编码',width:60,align:' left'},
			    		{field:'goodsname', title:'商品名称', width:220,align:'left',aliascol:'goodsid',
			    			formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.name;
					       		}else{
					       			return "";
					      			}
					        }
			    		},
			    		{field:'barcode', title:'条形码',width:90,align:'left',aliascol:'goodsid',
			    			formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.barcode;
					       		}else{
					       			return "";
					       		}
					        }
			    		},
			    		{field:'brandName', title:'商品品牌',width:80,align:'left',aliascol:'goodsid',hidden:true,
			    			formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.brandName;
					       		}else{
					       			return "";
					       		}
					        }
			    		},
			    		{field:'boxnum', title:'箱装量',aliascol:'goodsid',width:45,align:'right',
			 						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
					       		}else{
					       			return "";
					       		}
					        }
			 			},
			    		{field:'unitname', title:'单位',width:35,align:'left'},
			    		{field:'usablenum', title:'可用量',width:60,align:'right',isShow:true,sortable:true,
    						formatter:function(value,row,index){
				        		return formatterBigNumNoLen(value);
					        }
				    	},
			    		{field:'unitnum', title:'数量',width:60,align:'right',
			    			formatter:function(value,row,index){
					       		return formatterBigNumNoLen(value);
					        },
					        styler:function(value,row,index){
					        	var status = $("#sales-status-orderAddPage").val();
					        	if((status==null ||status=='1' || status=="2") && Number(row.usablenum)<Number(value)){
					        		return 'background-color:red;';
					        	}
					        }
					   	},
					   	{field:'taxprice', title:'单价',width:60,align:'right',
			    			formatter:function(value,row,index){
      							if(parseFloat(value)>parseFloat(row.sysprice)){
   				        			return "<font color='blue' title='系统价:"+formatterMoney(row.sysprice)+"'>"+ formatterMoney(value)+ "</font>";
   				        		}
   				        		else if(parseFloat(value)<parseFloat(row.sysprice)){
   				        			return "<font color='red' title='系统价:"+formatterMoney(row.sysprice)+"'>"+ formatterMoney(value)+ "</font>";
   				        		}
   				        		else{
   				        			return formatterMoney(value);
   				        		}
					        },
					        editor:{
					        	type:'numberbox',
					        	options:{
					        		precision:6
					        	}
					        },
					        styler:function(value,row,index){
					        	if(row.taxprice!=null && (row.taxprice-row.initprice!=0)){
					        		return 'background-color:yellow;';
					        	}
					        }
					   	},
                        <security:authorize url="/sales/orderCarbuyprice.do">
                        {field:'buyprice', title:'采购价',width:60,align:'right',sortable:true,hidden:true,
                            formatter:function(value,row,index){
                            return formatterMoney(value);
                            }
                        },
                        </security:authorize>
			    		{field:'taxamount', title:'金额',width:60,align:'right',
			    			formatter:function(value,row,index){
					       		return formatterMoney(value);
					        }
					    },
			    		{field:'taxtype', title:'税种',width:50,align:'left',hidden:true,
			    			formatter:function(value,row,index){
			    				return row.taxtypename;
			    			}
			    		},
			    		{field:'notaxprice', title:'未税单价',width:60,align:'right', hidden:true,
			    			formatter:function(value,row,index){
					       		return formatterMoney(value);
					        }
					    },
			    		{field:'notaxamount', title:'未税金额',width:60,align:'right', hidden:true,
			    			formatter:function(value,row,index){
					       		return formatterMoney(value);
					        }
					    },
			    		{field:'tax', title:'税额',width:60,align:'right',hidden:true,
			    			formatter:function(value,row,index){
					       		return formatterMoney(value);
					        }
					    },
					    {field:'auxnumdetail', title:'辅数量',width:60,align:'right'},
			    		{field:'remark', title:'备注',width:230,align:'left'}
			    	  ]]
		});
    	$(function(){
    		var type = "${type }";
    		var url = "sales/orderCarAddPage.do";
    		if(type == "edit"){
    			url = "sales/orderCarEditPage.do?id=${id}";
    		}else if(type == "view"){
				url = "sales/orderCarViewPage.do?id=${id}";
			}
    		$("#sales-panel-orderPage").panel({
				href:url,
			    cache:false,
			    maximized:true,
			    border:false
			});
    		//按钮
			$("#sales-buttons-orderPage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/sales/orderCarAddPage.do">
					{
						type: 'button-add',
						handler: function(){
							top.addOrUpdateTab('sales/orderCarPage.do', "零售订单新增");
						}
					},
					</security:authorize>
					<security:authorize url="/sales/orderCarSave.do">
					{
						type: 'button-save',
						handler: function(){
							$("#sales-addType-orderAddPage").val("real");
							var json = $("#sales-datagrid-orderAddPage").datagrid('getRows');
							$("#sales-goodsJson-orderAddPage").val(JSON.stringify(json));
							$("#sales-form-orderAddPage").submit();
						}
					},
					</security:authorize>
					<security:authorize url="/sales/orderCarAudit.do">
					{
						type: 'button-audit',
						handler: function(){
							var id = $("#sales-backid-orderPage").val();
							if(id == ''){
								return false;
							}
							$.messager.confirm("提醒","确定审核该订单信息？",function(r){
								if(r){
									loading("审核中..");
									$.getJSON("sales/canAuditOrderCar.do",{ids: id}, function(json){
										if(json.flag == true){
											$.ajax({
												url:'sales/auditOrderCar.do',
												dataType:'json',
												type:'post',
												data:'id='+ id +'&type=1',
												success:function(json){
													loaded();
													if(json.flag == true){
														$.messager.alert("提醒","审核成功.");
//														$("#sales-status-orderAddPage").val("4");
														$("#sales-panel-orderPage").panel('refresh');
													}
													else{
														$.messager.alert("提醒","审核失败。"+json.msg);
													}
												},
												error:function(){
													loaded();
													$.messager.alert("错误","审核出错。");
												}
											});
										}
										else{
											loaded();
											$.messager.confirm("提醒",json.msg+"是否继续审核？",function(r){
												if(r){
													loading("审核中...");
													$.ajax({
														url:'sales/auditOrderCar.do',
														dataType:'json',
														type:'post',
														data:'id='+ id +'&type=1',
														success:function(json){
															loaded();
															if(json.flag == true){
																$.messager.alert("提醒","审核成功.");
//																$("#sales-status-orderAddPage").val("4");
																$("#sales-panel-orderPage").panel('refresh');
															}
															else{
																$.messager.alert("提醒","审核失败。"+json.msg);
															}
														},
														error:function(){
															loaded();
															$.messager.alert("错误","审核出错。");
														}
													});
												}
											});
										}
									});
									
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/sales/oppauditOrderCar.do">
					{
						type: 'button-oppaudit',
						handler: function(){
							var id = $("#sales-backid-orderPage").val();
							if(id == ''){
								return false;
							}
                            var businessdate = $("#sales-businessdate-orderAddPage").val();
                            var flag = isDoneOppauditBillCaseAccounting(businessdate);
                            if(!flag){
                                $.messager.alert("提醒","业务日期不在会计区间内或未设置会计区间,不允许反审!");
                                return false;
                            }
							$.messager.confirm("提醒","确定反审该订单信息？",function(r){
								if(r){
									loading("审核中..");
									$.ajax({
										url:'sales/oppauditOrderCar.do',
										dataType:'json',
										type:'post',
										data:'id='+ id +'&type=1',
										success:function(json){
											loaded();
											if(json.flag == true){
												$.messager.alert("提醒","反审成功.");
												$("#sales-panel-orderPage").panel('refresh', 'sales/orderCarEditPage.do?id='+ id);
											}
											else{
												$.messager.alert("提醒","反审失败。"+json.msg);
											}
										},
										error:function(){
											loaded();
											$.messager.alert("提醒","反审失败。该单据可能已开票或者已核销");
										}
									});
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/sales/orderCarBack.do">
					{
						type: 'button-back',
						handler: function(data){
							$("#sales-backid-orderPage").val(data.id);
							refreshPanel('sales/orderCarEditPage.do?id='+ data.id);
						}
					},
					</security:authorize>
					<security:authorize url="/sales/orderCarNext.do">
					{
						type: 'button-next',
						handler: function(data){
							$("#sales-backid-orderPage").val(data.id);
							refreshPanel('sales/orderCarEditPage.do?id='+ data.id);
						}
					},
					</security:authorize>
					{}
				],
				buttons:[
					<security:authorize url="/sales/updateOrderCarToDemand.do">
					{
						id:'button-auditDemand',
						name:'生成要货单',
						iconCls:'button-audit',
						handler:function(){
							var id = $("#sales-billid-orderAddPage").val();
							$.messager.confirm("提醒","是否把该直营销售单转换成要货申请单？",function(r){
								if(r){
									loading("操作中..");
									$.ajax({
										url:'sales/updateOrderCarToDemand.do',
										dataType:'json',
										type:'post',
										data:{id:id},
										success:function(json){
											loaded();
											if(json.flag == true){
												$.messager.alert("提醒","操作成功。"+json.msg);
												//关闭当前标签页
			    	  							top.updateTab('sales/demandPage.do?type=view&id='+ id, "要货申请单查看");
											}
											else{
												$.messager.alert("提醒","操作失败");
											}
										},
										error:function(){
											loaded();
											$.messager.alert("错误","操作失败");
										}
									});
								}
							});
						}
					},
					</security:authorize>
					{}
				],
				layoutid:'sales-layout-orderPage',
				model: 'bill',
				type: 'view',
				tab:'零售订单',
				taburl:'/sales/orderCarListPage.do',
				id:'${id}',
				datagrid:'sales-datagrid-orderListPage'
			});
			$(document).keydown(function(event){//alert(event.keyCode);
    			switch(event.keyCode){
    				case 13: //Enter
    					if(chooseNo == "ordercar.remark"){
    						$("input[name='ordercar.remark']").blur();
    						beginAddDetail();
    					}
    					if(chooseNo == "unitnum"){
    						$("input[name=auxnum]").focus();
							$("input[name=auxnum]").select();
    						return false;
    					}
    					if(chooseNo == "auxnum"){
    						$("input[name=overnum]").focus();
							$("input[name=overnum]").select();
    						return false;
    					}
    					if(chooseNo == "overnum"){ 
    						if($("input[name=taxprice]").attr("readonly") == "readonly"){
    							$("input[name=remark]").focus();
								$("input[name=remark]").select();
    						}
    						else{
    							$("input[name=taxprice]").focus();
								$("input[name=taxprice]").select();
    						}
    						return false;
    					}
    					if(chooseNo == "taxprice"){
    						if($("input[name=taxamount]").attr("readonly") == "readonly"){
    							$("input[name=remark]").focus();
								$("input[name=remark]").select();
    						}
    						else{
								getNumberBox("sales-taxamount-orderCarDetailAddPage").focus();
								getNumberBox("sales-taxamount-orderCarDetailAddPage").select();
								chooseNo = "taxamount";
    						}
    						return false;
    					}
    					if(chooseNo == "taxamount"){
    						$("input[name=remark]").focus();
							$("input[name=remark]").select();
    						return false;
    					}
    					if(chooseNo == "remark"){
    						$("input[name=savegoon]").click();
    						return false;
    					}
    				break;
	    			case 27: //Esc
	    				$("#sales-remark-orderCarDetailAddPage").focus();
	    				$("#sales-dialog-orderCarAddPage-content").dialog('close');
	    			break;
    			}
    		});
    		$(document).bind('keydown', 'ctrl+enter',function (){
		    	$("#sales-remark-orderCarDetailAddPage").focus();
		    	$("#sales-savegoon-orderCarDetailAddPage").trigger("click");
		    });
		    $(document).bind('keydown', '+',function (){
		    	$("#sales-remark-orderCarDetailAddPage").focus();
		    	setTimeout(function(){
		    		$("#sales-savegoon-orderCarDetailAddPage").trigger("click");
		    	},100);
		    	return false;
		    });
		});
    	function refreshPanel(url){ //更新panel
    		$("#sales-panel-orderPage").panel('refresh', url);
    	}
    	function countTotal(){ //计算合计
    		var rows = $("#sales-datagrid-orderAddPage").datagrid('getRows');
    		var leng = rows.length;
    		var unitnum = 0;
    		var taxamount = 0;
    		var notaxamount = 0;
    		var tax = 0;
    		for(var i=0; i<leng; i++){
    			unitnum += Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
    			taxamount += Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
    			notaxamount += Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
    			tax += Number(rows[i].tax == undefined ? 0 : rows[i].tax);
    		}
    			$("#sales-datagrid-orderAddPage").datagrid('reloadFooter',[{goodsid:'合计', unitnum: unitnum, taxamount: taxamount, notaxamount: notaxamount, tax: tax}]);
    	}
    	var editIndex = undefined;  
        function endEditing(){  
            if (editIndex == undefined){return true}  
            if ($('#sales-datagrid-orderAddPage').datagrid('validateRow', editIndex)){  
            	var data = $('#sales-datagrid-orderAddPage').datagrid("getRows")[editIndex];
            	var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'taxprice'});
            	if(ed != null){
					var price = $(ed.target).numberbox('getValue');
					if(price != data.taxprice){
						$wareList.datagrid('getRows')[editIndex].taxprice = price;
						var detail = async_ajaxContent("type=1&id="+data.goodsid+"&price="+price+"&taxtype="+data.taxtype+"&unitnum="+data.unitnum, "sales/getAmountChanger.do");
						var json = $.parseJSON(detail);
						$wareList.datagrid('getRows')[editIndex].taxamount = json.taxAmount;
						$wareList.datagrid('getRows')[editIndex].notaxprice = json.noTaxPrice;
						$wareList.datagrid('getRows')[editIndex].notaxamount = json.noTaxAmount;
						$wareList.datagrid('getRows')[editIndex].tax = json.tax;
					}
				}
                return true;  
            } else {  
                return false;  
            }  
        }
        function onClickRow(index, data){
        	if (endEditing()){
        		$('#sales-datagrid-orderAddPage').datagrid('endEdit', editIndex);  
        		if(editIndex == index){
        			editIndex = undefined;
        		}
        		else{
	        		$('#sales-datagrid-orderAddPage').datagrid('selectRow', index).datagrid('beginEdit', index);
	        		editIndex = index;
        		}
        	}
        }
        //回车跳到下一个
    	var chooseNo;
    	function frm_focus(val){
    		chooseNo = val;
    	}
    	function frm_blur(val){
    		if(val == chooseNo){
    			chooseNo = "";
    		}
    	}
    	//删除明细
    	function removeDetail(){
    		var row = $wareList.datagrid('getSelected');
	    	if(row == null){
	    		$.messager.alert("提醒", "请选择一条记录");
	    		return false;
	    	}
	    	$.messager.confirm("提醒","确定删除该商品明细?",function(r){
		    	if(r){
			   		var rowIndex = $wareList.datagrid('getRowIndex', row);
			   		$wareList.datagrid('deleteRow', rowIndex);
			   		countTotal();
			   		var rows = $wareList.datagrid('getRows');
			   		var leng = rows.length;
    				if(leng < 12){
    					for(var i=leng; i<12; i++){
    						$("#sales-datagrid-orderAddPage").datagrid('appendRow',{});
    					}
    				}
		    	}
	    	});	
    	}
    	//添加明细
    	function beginAddDetail(){ //开始添加商品信息
			var customer = $("#sales-customer-orderAddPage").customerWidget("getValue");
			if(customer == ''){
				$("#sales-customer-orderAddPage").focus();
				$.messager.alert("提醒","请先选择客户再进行添加商品信息");
				return false;
			}
			var storageid = $("#sales-storageid-orderAddPage").widget('getValue');
			if(storageid == ''){
				$("#sales-storageid-orderAddPage").focus();
				$.messager.alert("提醒","请先选择仓库再进行添加商品信息");
				return false;
			}
			$('<div id="sales-dialog-orderCarAddPage-content"></div>').appendTo('#sales-dialog-orderCarAddPage');
	    	$("#sales-dialog-orderCarAddPage-content").dialog({ //弹出新添加窗口
	    		title:'商品信息添加(按ESC退出)',
	    		maximizable:true,
	    		width:600,
	    		height:450,
	    		closed:false,
	    		modal:true,
	    		cache:false,
	    		resizable:true,
	    		href:'sales/orderCarDetailAddPage.do?cid='+ customer,
	    		onClose:function(){
			    	$('#sales-dialog-orderCarAddPage-content').dialog("destroy");
			    },
	    		onLoad:function(){
	    			$("#sales-goodsId-orderCarDetailAddPage").focus();
	    		}
	    	});
		}
		function addSaveDetail(go){ //添加新数据确定后操作，
    		var flag = $("#sales-form-orderCarDetailAddPage").form('validate');
		  	if(flag==false){
		  		return false;
		  	}
    		var form = $("#sales-form-orderCarDetailAddPage").serializeJSON();
    		var widgetJson = $("#sales-goodsId-orderCarDetailAddPage").storageGoodsWidget('getObject');
    		var goodsJson = {id:widgetJson.goodsid,name:widgetJson.goodsname,brandName:widgetJson.brandname,
    						model:widgetJson.model,barcode:widgetJson.barcode,boxnum:widgetJson.boxnum};
    		form.goodsInfo = goodsJson;
    		var customer = $("#sales-customer-dispatchorderCarAddPage-hidden").val();
    		if(form.overnum!=0){
    			form.auxnumdetail = form.auxnum + form.auxunitname + form.overnum + form.unitname;
    		}else{
    			form.auxnumdetail = form.auxnum + form.auxunitname;
    		}
    		var rowIndex = 0;
    		var rows = $wareList.datagrid('getRows');
    		var updateFlag = false;
    		for(var i=0; i<rows.length; i++){
    			var rowJson = rows[i];
    			//if(rowJson.goodsid==goodsJson.id){
    			//	rowIndex = i;
    			//	updateFlag =  true;
    			//	break;
    			//}
    			if(rowJson.goodsid == undefined){
    				rowIndex = i;
    				break;
    			}
    		}
    		if(rowIndex == rows.length - 1){
    			$wareList.datagrid('appendRow',{}); //如果是最后一行则添加一新行
    		}
    		//if(updateFlag){
    		//	$.messager.alert("提醒", "此商品已经添加！");
    		//}else{
    			$wareList.datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
    		//}
    		if(go){ //go为true确定并继续添加一条
    			$("#sales-form-orderCarDetailAddPage").form("clear");
    		}
    		else{ //否则直接关闭
    			$("#sales-dialog-dispatchorderCarAddPage-content").dialog('close', true)
    		}
    		countTotal(); //第添加一条商品明细计算一次合计
    	}
    	function beginEditDetail(rowData){ //开始修改商品信息
    		var customer = $("#sales-customer-orderAddPage").customerWidget("getValue");
			if(customer == ''){
				$.messager.alert("提醒","请先选择客户再进行添加商品信息");
				return false;
			}
			var storageid = $("#sales-storageid-orderAddPage").widget('getValue');
			if(storageid == ''){
				$("#sales-storageid-orderAddPage").focus();
				$.messager.alert("提醒","请先选择仓库再进行添加商品信息");
				return false;
			}
    		var row = $wareList.datagrid('getSelected');
    		if(row == null){
    			$.messager.alert("提醒", "请选择一条记录");
    			return false;
    		}
    		row.goodsname = row.goodsInfo.name;
    		row.model = row.goodsInfo.model;
    		row.brandName = row.goodsInfo.brandName;
    		row.barcode = row.goodsInfo.barcode;
			row.boxnum = row.goodsInfo.boxnum;
    		var url = '';
    		if(row.goodsid == undefined){
    			beginAddDetail();
    		}
    		else{
    			url = 'sales/orderCarDetailEditPage.do?cid='+ customer; //如果是修改页面，数据直接来源于datagrid中的json数据。
    			$('<div id="sales-dialog-orderCarAddPage-content"></div>').appendTo('#sales-dialog-orderCarAddPage');
	    		$("#sales-dialog-orderCarAddPage-content").dialog({ //弹出修改窗口
		    		title:'商品信息修改(按ESC退出)',
		    		maximizable:true,
		    		width:600,
		    		height:450,
		    		closed:false,
		    		modal:true,
		    		cache:false,
		    		resizable:true,
		    		href:url,
		    		onClose:function(){
				    	$('#sales-dialog-orderCarAddPage-content').dialog("destroy");
				    },
		    		onLoad: function(){
		    			$("#sales-form-orderCarDetailEditPage").form('load', row);
						$("#sales-boxnum-orderDetailAddPage").val(formatterBigNumNoLen(row.boxnum));
		    			$("#sales-notaxprice-orderCarDetailAddPage").val(row.notaxprice);
		    			$("#sales-tax-orderCarDetailAddPage").numberbox("setValue",row.tax);
		    			$("#sales-auxunitname-orderCarDetailAddPage").html(row.auxunitname);
		    			$("#sales-unitname-orderCarDetailAddPage").html(row.unitname);
		    			$("#sales-storage-orderCarDetailAddPage").widget("setValue", row.storageid);
		    			$("#sales-storagename-orderCarDetailAddPage").text(row.storagename);
		    			if(row.total == undefined){
			    			$.getJSON("storage/getStorageSummaryByStorageidAndGoodsid.do",{goodsid: row.goodsid,storageid:storageid},function(json){
			    				if(json.storageSummary!=null){
			    					$("#sales-loading-orderCarDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>"+row.goodsid+"</font>&nbsp;可用量：<font color='green'>"+ json.storageSummary.usablenum + json.storageSummary.unitname +"</font>");
			    				}else{
			    					$("#sales-loading-orderCarDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>仓库中不存在该商品</font>");
			    				}
			    			});
		    			}
		    			else{
		    				$("#sales-loading-orderCarDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>"+row.goodsid+"</font>&nbsp;可用量：<font color='green'>"+ row.total + row.sunitname +"</font>");
		    			}
		    			$("input[name=unitnum]").focus();

						formaterNumSubZeroAndDot();

						$("#sales-form-orderCarDetailEditPage").form('validate');
		    		}
		    	});
    		}
    	}
    	function editSaveDetail(go){ //修改数据确定后操作，
    		var flag = $("#sales-form-orderCarDetailEditPage").form('validate');
		  	if(flag==false){
		  		return false;
		  	}
            var rows = $wareList.datagrid('getRows');
    		var row = $wareList.datagrid('getSelected');
    		var rowIndex = $wareList.datagrid('getRowIndex', row);
    		var form = $("#sales-form-orderCarDetailEditPage").serializeJSON();
    		if(form.overnum!=0){
    			form.auxnumdetail = form.auxnum + form.auxunitname + form.overnum + form.unitname;
    		}else{
    			form.auxnumdetail = form.auxnum + form.auxunitname;
    		}
            if(rowIndex == rows.length - 1){
                $wareList.datagrid('appendRow',{}); //如果是最后一行则添加一新行
            }
    		$wareList.datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
   			$("#sales-dialog-orderCarAddPage-content").dialog('close', true)
    		countTotal();
    	}

	//根据客户编号显示客户详情
	function showCustomer(customerId){
		$('<div id="sales-dialog-customer"></div>').appendTo('#sales-dialog-orderPage');
		$('#sales-dialog-customer').dialog({
			maximizable:true,
			resizable:true,
			title: "客户档案【查看】",
			width: 740,
			height: 450,
			closed: true,
			cache: false,
			href: 'basefiles/showCustomerSimplifyViewPage.do?id='+customerId,
			modal: true,
			onClose:function(){
				$('#sales-dialog-customer').dialog("destroy");
			}
		});
		$("#sales-dialog-customer").dialog("open");
	}
    </script>
  </body>
</html>
