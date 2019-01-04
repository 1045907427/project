<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售订单查看页面</title>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-orderAddPage" action="sales/updateOrder.do" method="post">
    		<input type="hidden" id="sales-addType-orderAddPage" name="addType" />
    		<input type="hidden" name="saleorder.oldid" value="${saleorder.id }" />
	    	<div data-options="region:'north',border:false" style="height:140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编&nbsp;&nbsp;号：</td>
	    				<td class="len165"><input type="text" id="sales-id-orderAddPage" class="len130" readonly="readonly" name="saleorder.id" value="${saleorder.id }" /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input id="sales-businessdate-orderAddPage" type="text" class="len130 Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" name="saleorder.businessdate" value="${saleorder.businessdate }" /></td>
	    				<td class="len80 left">状&nbsp;&nbsp;态：</td>
	    				<td class="len165">
	    					<select id="sales-customer-status" disabled="disabled" class="len136">
	    						<c:forEach items="${statusList }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == saleorder.status}">
	    									<option value="${list.code }" selected="selected">${list.codename }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
	    					</select>
	    					<input type="hidden" name="saleorder.status" value="${saleorder.status }" />
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客&nbsp;&nbsp;户：</td>
	    				<td colspan="3">
	    					<input type="text" id="sales-customer-orderAddPage" name="saleorder.customerid" text="<c:out value="${saleorder.customername }"></c:out>" value="${saleorder.customerid }" style="width: 300px;"/>
                            <span id="sales-customer-showid-orderAddPage" style="margin-left:5px;line-height:25px;">
                                编号：<a href="javascript:showCustomer('${saleorder.customerid }')">${saleorder.customerid }</a></span>
	    					<input type="hidden" id="sales-customer-hidden-orderAddPage" value="${saleorder.customerid }"/>
	    				</td>
						<td>销售部门：</td>
						<td class="len150">
							<input id="sales-salesDept-orderAddPage" type="text" class="len136" name="saleorder.salesdept" value="${saleorder.salesdept }" />
						</td>
                    </tr>
	    			<tr>
	    				<td class="len80 left">发货仓库：</td>
	    				<td>
	    					<input type="text" id="sales-storageid-orderAddPage" name="saleorder.storageid" value="${saleorder.storageid }"/>
	    					<input type="hidden" id="sales-storageid-orderAddPage-old" value="${saleorder.storageid }"/>
	    				</td>
	    				<td class="len80 left">备&nbsp;&nbsp;注：</td>
	    				<td colspan="3"><input id="sales-remark-orderDetailAddPage" type="text" name="saleorder.remark" value="<c:out value="${saleorder.remark }"></c:out>" style="width:402px;" onfocus="frm_focus('saleorder.remark');" onblur="frm_blur('saleorder.remark');" /></td>
	    			</tr>
	    		</table>
	    		<input type="hidden" id="sales-printtimes-orderAddPage" value="${saleorder.printtimes }"/>
	    		<input type="hidden" id="sales-phprinttimes-orderAddPage" value="${saleorder.phprinttimes }"/>
	    		<input type="hidden" id="sales-printlimit-orderAddPage" value="${printlimit }"/>
	    		<input type="hidden" id="sales-fHPrintAfterSaleOutAudit-orderAddPage" value="${fHPrintAfterSaleOutAudit }"/>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="goodsjson" id="sales-goodsJson-orderAddPage"/>
	    		<input type="hidden" name="lackGoodsjson" id="sales-lackGoodsjson-orderAddPage" value="<c:out value="${laskJson }"/>"/>
	    		<input id="sales-saveaudit-orderDetailAddPage" type="hidden" name="saveaudit" value="save"/>
	    		<table id="sales-datagrid-orderAddPage"></table>
	    	</div>
	    </form>
    </div>
    <div class="easyui-menu" id="sales-contextMenu-orderAddPage" style="display: none;">
    	<div id="sales-addRow-orderAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="sales-insertRow-orderAddPage" data-options="iconCls:'button-add'">插入</div>
    	<div id="sales-editRow-orderAddPage" data-options="iconCls:'button-edit'">修改</div>
    	<div id="sales-addDetailByBrandAndSort-orderAddPage" data-options="iconCls:'button-add'">批量添加商品</div>
    	<security:authorize url="/sales/orderDiscountAddPage.do">
    	<div id="sales-addRow-orderAddDiscountPage" data-options="iconCls:'button-add'">添加商品折扣</div>
    	</security:authorize>
    	<security:authorize url="/sales/orderBrandDiscountAddPage.do">
    	<div id="sales-addRow-orderAddBrandDiscountPage" data-options="iconCls:'button-add'">添加品牌折扣</div>
    	</security:authorize>
        <security:authorize url="/sales/discountOrderPage.do">
        <div id="sales-addRow-orderAddOrderDiscountPage" data-options="iconCls:'button-add'">添加订单折扣</div>
        </security:authorize>
        <security:authorize url="/sales/setContractPrice.do">
        <div id="sales-setContractPrice-orderAddPage" data-options="iconCls:'button-delete'">设为商品合同价</div>
        </security:authorize>
        <div id="sales-removeRow-orderAddPage" data-options="iconCls:'button-delete'">删除</div>
        <div id="sales-history-price-orderAddPage" data-options="iconCls:'button-view'">查看历史销售价</div>
        <security:authorize url='/sales/setOrderNewprice.do'>
            <div id="sales-changePrice-orderAddPage" onclick="javascript:showChangePriceDialog();" data-options="iconCls:'button-edit'">修改商品价格</div>
        </security:authorize>
    </div>
    <div id="sales-dialog-orderAddPage" class="easyui-dialog" data-options="closed:true"></div>
    <div id="sales-dialog-orderGoodsPromotion-ptype"></div>
    <script type="text/javascript">

    var leftAmount = '${leftAmount}';
    var receivableAmount = '${receivableAmount}';

    $("#sales-buttons-orderPage").buttonWidget("disableButton",'order-oweorder-button');

    	//是否允许修改销售部门客户业务员
    	var salesReadonly = true;
    	<security:authorize url="/sales/orderEditSalesuserAndSalesdept.do">
    	salesReadonly = false;
		</security:authorize>
    	$(function(){
    		$("#sales-datagrid-orderAddPage").datagrid({ //销售商品明细信息编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			fit: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: false,
    			checkOnSelect:true,
    			selectOnCheck:true,
    			toolbar:'#sales-toolbar-orderAddPage',
    			data: JSON.parse('${goodsJson}'),
    			onSortColumn:function(sort, order){
    				var rows = $("#sales-datagrid-orderAddPage").datagrid("getRows");
    				var dataArr = [];
    				for(var i=0;i<rows.length;i++){
    					if(rows[i].goodsid!=null && rows[i].goodsid!=""){
    						dataArr.push(rows[i]);
    					}
    				}
    				dataArr.sort(function(a,b){
    					if($.isNumeric(a[sort])){
	    					if(order=="asc"){
	    						return Number(a[sort])>Number(b[sort])?1:-1
	    					}else{
	    						return Number(a[sort])<Number(b[sort])?1:-1
	    					}
    					}else{
    						if(order=="asc"){
	    						return a[sort]>b[sort]?1:-1
	    					}else{
	    						return a[sort]<b[sort]?1:-1
	    					}
    					}
    				});
    				$("#sales-datagrid-orderAddPage").datagrid("loadData",dataArr);
    				return false;
    			},
    			onLoadSuccess: function(data){
    				if(data.rows.length<12){
    					var j = 12-data.rows.length;
	            		for(var i=0;i<j;i++){
	            			$(this).datagrid('appendRow',{});
	            		}
    				}else{
    					$(this).datagrid('appendRow',{});
    				}
    				groupGoods();
    				countTotal(leftAmount,receivableAmount);
    			},
    			onCheckAll:function(){
					countTotal(leftAmount,receivableAmount);
				},
				onUncheckAll:function(){
					countTotal(leftAmount,receivableAmount);
				},
				onCheck:function(){
					countTotal(leftAmount,receivableAmount);
				},
				onUncheck:function(){
					countTotal(leftAmount,receivableAmount);
				},
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$wareList.datagrid('selectRow', rowIndex);
                    $("#sales-contextMenu-orderAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onDblClickRow: function(rowIndex, rowData){
    				$(this).datagrid('clearSelections').datagrid('selectRow',rowIndex);
    				if(rowData.goodsid == undefined && rowData.isdiscount==null){
                        beginAddDetail();
    				}
    				else{
    					if(rowData.isdiscount=='1'){
    						<security:authorize url="/sales/orderDiscountAddPage.do">
    						beginEditDetailDiscount();
    				    	</security:authorize>
    					}else if(rowData.isdiscount=='2'){
    						<security:authorize url="/sales/orderBrandDiscountAddPage.do">
    						beginEditDetailBrandDiscount();
    				    	</security:authorize>
    					}else if(rowData.isdiscount=='3'){//订单折扣

                        }else{
                            if(rowData.groupid != null && rowData.groupid!=""){
                                showPromotionEditPage(rowData);
                            }else{
                                beginEditDetail(rowData);
                            }
    					}
    				}
    			}
    		}).datagrid('columnMoving');
    		$("#sales-form-orderAddPage").form({
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
		  		  	if(json.lock == true){
		  		  		$.messager.alert("提醒","其他用户正在修改该数据，无法修改");
		  		  		return false;
		  		  	}
		  		    if(json.flag==true){
		  		    	var saveaudit = $("#sales-saveaudit-orderDetailAddPage").val();
		  		    	if(saveaudit=="saveaudit"){
			  		      	if(json.auditflag == true){
			  		    		$.messager.alert("提醒","保存审核成功，"+json.msg);
			  		    		$("#sales-customer-status").val("3");
								$("#sales-buttons-orderPage").buttonWidget("setDataID", {id:json.backid, state:'3', type:'view'});

			  		    		$("#sales-buttons-orderPage").buttonWidget("enableMenuItem","button-printview-orderblank");
			  					$("#sales-buttons-orderPage").buttonWidget("enableMenuItem","button-print-orderblank");
			  					$("#sales-buttons-orderPage").buttonWidget("enableMenuItem","button-printview-DispatchBill");
			  					$("#sales-buttons-orderPage").buttonWidget("enableMenuItem","button-print-DispatchBill");
                                $("#sales-panel-orderPage").panel('refresh', 'sales/orderViewPage.do?id='+json.backid);
			  		    	}else{
			  		    		$.messager.alert("提醒","保存成功,审核失败。"+json.msg);
			  		    	}
		  		    	}else{
		  		    		$.messager.alert("提醒","保存成功。"+json.msg);
		  		    	}

		  		    	var oldstorageid = $("#sales-storageid-orderAddPage-old").val();
		  		    	var newstorageid = $("#sales-storageid-orderAddPage").widget("getValue");
		  		    	$("#sales-backid-orderPage").val(json.backid);
		  		    	if(oldstorageid!=newstorageid){
		  		    		$("#sales-panel-orderPage").panel('refresh', 'sales/orderEditPage.do?id='+json.backid);//'sales/orderViewPage.do?id='+ json.backid);
		  		    	}
		  		    }else{
		  		       	$.messager.alert("提醒","保存失败。"+json.msg);
		  		    }
		  		    chooseGoods="";
		  		}
		  	});
    		$("#sales-customer-orderAddPage").customerWidget({ //客户参照窗口
    			name:'t_sales_order',
				col:'customerid',
    			width:300,
    			isopen:true,
    			required:true,
    			onSelect:function(data){
                    var html = '编号：<a href="javascript:showCustomer(\''+data.id+'\')">'+data.id+'</a>';
    				$("#sales-customer-showid-orderAddPage").html(html);
    				$("#sales-salesMan-orderAddPage").widget("setValue",data.salesuserid);
    				if(data.salesdeptid!=null && data.salesdeptid!=""){
    					$("#sales-salesDept-orderAddPage").widget("setValue",data.salesdeptid);
    				}else{
    					$("#sales-salesDept-orderAddPage").widget("clear"); 					
    				}
    				//客户变更后 更新明细价格数据
    				changeGoodsPrice();
    			},
    			onClear:function(){
    				$("#sales-customer-showid-orderAddPage").text("");
    			}
    			
    		});
    		$("#sales-storageid-orderAddPage").widget({
    			referwid:'RL_T_BASE_STORAGE_INFO',
    			width:130,
    			<c:if test="${isOrderStorageSelect=='1'}">
    			required:true,
    			</c:if>
				singleSelect:true,
				onSelect:function(data){
					$("#sales-remark-orderDetailAddPage").focus();
                    var rows = $("#sales-datagrid-orderAddPage").datagrid('getRows');
                    var count = 0;
                    var showBatchMsg = false;
                    for(var i=0;i<rows.length; i++){
                        var row = rows[i];
                        if((rows[i].goodsid!=null && rows[i].goodsid!='') || rows[i].isdiscount!=null){
                            var rowIndex = $wareList.datagrid("getRowIndex", row);
                            if(row.summarybatchid==null || row.summarybatchid==""){
                                row.storageid = data.id;
                                row.storagename = data.name;
                                $("#sales-datagrid-orderAddPage").datagrid('updateRow', {index: rowIndex, row: row});
                            }else{
                                if(row.storageid!=data.id){
                                    showBatchMsg = true;
                                    row.summarybatchid="";
                                    row.batchno="";
                                    row.produceddate="";
                                    row.deadline="";
                                    row.storageid = data.id;
                                    row.storagename = data.name;
                                    $("#sales-datagrid-orderAddPage").datagrid('updateRow', {index: rowIndex, row: row});
                                }
                            }

                        }
                    }
                    if(showBatchMsg){
                        $.messager.alert("提醒","商品指定批次的仓库与发货仓库不一致，自动清除批次信息。");
                    }
				},
                onClear:function(){
                    var rows = $("#sales-datagrid-orderAddPage").datagrid('getRows');
                    var count = 0;
                    for(var i=0;i<rows.length; i++){
                        var row = rows[i];
                        if((rows[i].goodsid!=null && rows[i].goodsid!='') || rows[i].isdiscount!=null) {
                            var rowIndex = $wareList.datagrid("getRowIndex", row);
                            row.storageid = "";
                            row.storagename = "";
                            if(row.summarybatchid==null || row.summarybatchid=="") {
                                $("#sales-datagrid-orderAddPage").datagrid('updateRow', {index: rowIndex, row: row});
                            }
                        }
                    }
                }
    		});
    		$("#sales-salesDept-orderAddPage").widget({
    			name:'t_sales_order',
				col:'salesdept',
    			width:130,
    			readonly:salesReadonly,
				singleSelect:true
    		});
    		$("#sales-salesMan-orderAddPage").widget({
    			name:'t_sales_order',
				col:'salesuser',
				width:130,
				readonly:salesReadonly,
				singleSelect:true,
				required:true
    		});
    		//批量添加
    		$("#sales-addDetailByBrandAndSort-orderAddPage").click(function(){
    			beginAddDetailByBrandAndSort();
    		});
    		//折扣添加页面
    		$("#sales-addRow-orderAddDiscountPage").click(function(){
    			beginAddDiscountDetail();
    		});
    		//添加品牌折扣
    		$("#sales-addRow-orderAddBrandDiscountPage").click(function(){
    			beginAddBrandDiscountDetail();
    		});
            //添加订单折扣
            $("#sales-addRow-orderAddOrderDiscountPage").click(function(){
                beginAddOrderDiscountDetail();
            });
    		$("#sales-addRow-orderAddPage").click(function(){
    			beginAddDetail(false);
    		});
    		$("#sales-insertRow-orderAddPage").click(function(){
    			beginAddDetail(true);
    		});
            //查看商品历史销售价
            $("#sales-history-price-orderAddPage").click(function(){
                showHistoryGoodsPrice();
            });
            //商品合同价修改
            $("#sales-setContractPrice-orderAddPage").click(function(){
                modifyGoodsContractPrice();
            });
    		$("#sales-editRow-orderAddPage").click(function(){
    			var row = $wareList.datagrid('getSelected');
	    		if(row.isdiscount=='1'){
	    			<security:authorize url="/sales/orderDiscountAddPage.do">
					beginEditDetailDiscount();
			    	</security:authorize>
				}else if(row.isdiscount=='2'){
					<security:authorize url="/sales/orderBrandDiscountAddPage.do">
					beginEditDetailBrandDiscount();
			    	</security:authorize>
				}else if(row.isdiscount=='3'){//订单折扣

                }else{
					beginEditDetail(row);
				}
    		});
    		$("#sales-removeRow-orderAddPage").click(function(){
    			removeDetail();
    		});
    		$("#sales-buttons-orderPage").buttonWidget("setDataID", {id:'${saleorder.id}', state:'${saleorder.status}', type:'edit'});
    		if("${saleorder.status}" == "2"){
    			$("#button-invalid").linkbutton("enable");
    			$("#button-uninvalid").linkbutton("disable");
    		}
    		if("${saleorder.status}" == "5"){
    			$("#button-invalid").linkbutton("disable");
    			$("#button-uninvalid").linkbutton("enable");
    		}


    	});

    	var $wareList = $("#sales-datagrid-orderAddPage"); //商品datagrid的div对象
    	$("#sales-buttons-orderPage").buttonWidget("enableButton", 'button-deploy');
	   <c:if test="${(saleorder.sourcetype=='3') }">
	     $("#sales-buttons-orderPage").buttonWidget("disableButton",'button-deploy');
	   </c:if>

    	$("#sales-buttons-orderPage").buttonWidget("enableMenuItem","button-printview-orderblank");
    	$("#sales-buttons-orderPage").buttonWidget("enableMenuItem","button-print-orderblank");
		$("#sales-buttons-orderPage").buttonWidget("enableMenuItem","button-printview-DispatchBill");
		$("#sales-buttons-orderPage").buttonWidget("enableMenuItem","button-print-DispatchBill");

    </script>
  </body>
</html>
