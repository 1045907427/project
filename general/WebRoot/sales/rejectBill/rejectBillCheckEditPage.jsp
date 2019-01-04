<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售退货通知单修改页面</title>
  </head> 
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-check-rejectBillAddPage" action="sales/updateRejectBillCheck.do" method="post">
    		<input type="hidden" id="sales-addType-rejectBillAddPage" name="addType" />
	    	<div data-options="region:'north',border:false" style="height:140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len150" name="rejectBill.id" value="${rejectBill.id }" readonly="readonly" /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len150 Wdate" id="date" value="${rejectBill.businessdate }" name="rejectBill.businessdate" readonly="readonly"/></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select id="sales-status-rejectBillAddPage" disabled="disabled" name="rejectBill.status" class="len150">
	    						<c:forEach items="${status }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == rejectBill.status}">
	    									<option value="${list.code }" selected="selected">${list.codename }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
	    					</select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td colspan="3">
	    					<input type="text" id="sales-customer-rejectBillAddPage" name="rejectBill.customerid" value="${rejectBill.customerid }" text="<c:out value="${rejectBill.customername }"></c:out>" style="width: 300px;"/>
	    					<span id="sales-customer-showid-dispatchBillAddPage" style="margin-left:5px;line-height:25px;">编号：${rejectBill.customerid }</span>
	    					<input type="hidden" id="sales-customer-showid-hidden-dispatchBillAddPage" value="${rejectBill.customerid }"/>
	    				</td>
	    				<td class="len80 left">司机：</td>
	    				<td>
	    					<input type="text" id="sales-driver-rejectBillAddPage" class="len150" name="rejectBill.driverid" value="${rejectBill.driverid }"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">销售部门：</td>
	    				<td>
	    					<input type="text" id="sales-salesDept-rejectBillAddPage" class="len150" name="rejectBill.salesdept" value="${rejectBill.salesdept }"/>
	    				</td>
	    				<td class="len80 left">客户业务员：</td>
	    				<td>
	    					<input type="text" class="len150" name="rejectBill.salesuser" id="sales-salesMan-rejectBillAddPage" value="${rejectBill.salesuser}"/>
	    				</td>
	    				<td class="len80 left">退货类型：</td>
	    				<td>
	    					<select class="len150" name="rejectBill.billtype">
	    						<option value="1" <c:if test="${rejectBill.billtype=='1'}">selected="selected"</c:if>>直退</option>
	    						<option value="2" <c:if test="${rejectBill.billtype=='2'}">selected="selected"</c:if>>售后退货</option>
	    					</select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">入库仓库：</td>
	    				<td>
	    					<input type="text" id="sales-storage-rejectBillAddPage" class="len150" name="rejectBill.storageid" value="${rejectBill.storageid}" readonly="readonly"/>
	    				</td>
						<td class="len80 left">销售类型：</td>
						<td>
							<select class="len150" name="rejectBill.salestype">
								<c:forEach items="${salestypeList }" var="salestype">
									<option value="${salestype.code }" <c:if test="${salestype.code eq rejectBill.salestype }">selected="selected"</c:if>><c:out value="${salestype.codename }"/></option>
								</c:forEach>
							</select>
						</td>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="1"><input type="text" name="rejectBill.remark" value="<c:out value="${rejectBill.remark }"></c:out>" style="width: 150px;"/></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="goodsjson" id="sales-goodsJson-rejectBillAddPage"  value="<c:out value="${goodsJson }"/>" />
	    		<table id="sales-datagrid-rejectBillAddPage"></table>
	    	</div>
			<input type="hidden" id="sales-isinvoice-rejectBillAddPage" value="${rejectBill.isinvoice}" />
			<input type="hidden" id="sales-ysprinttimes-rejectBillAddPage" value="${rejectBill.ysprinttimes}" />
	    </form>
    </div>
    <div class="easyui-menu" id="sales-contextMenu-rejectBillCheckPage" style="display: none;">
    	<div id="sales-RowNormal-rejectBillCheckPage" data-options="iconCls:'button-add'">正常商品</div>
    	<div id="sales-RowGive-rejectBillCheckPage" data-options="iconCls:'button-edit'">赠品</div>
        <div id="sales-bind-rejectBillCheckPage" data-options="iconCls:'button-edit'">捆绑</div>
        <div id="sales-history-price-rejectBillAddPage" data-options="iconCls:'button-view'">查看历史销售价</div>
    </div>
    <div id="sales-dialog-rejectBillAddPage" ></div>
    <script type="text/javascript">
	  //是否允许修改销售部门客户业务员
		var salesReadonly = true;
      var presentByZero = '${presentByZero}';
		<security:authorize url="/sales/rejectBillEditSalesuserAndSalesdept.do">
		salesReadonly = false;
		</security:authorize>
    	$(function(){
    		$("#sales-datagrid-rejectBillAddPage").datagrid({ //销售商品明细信息编辑
    			authority:wareListJson,
    			columns: wareListJson.common,
    			frozenColumns: wareListJson.frozen,
    			border: true,
    			fit: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: false,
    			checkOnSelect:true,
				selectOnCheck:true,
    			data: JSON.parse('${goodsJson}'),
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
                    $("#sales-contextMenu-rejectBillCheckPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onLoadSuccess: function(){
    				var rows = $("#sales-datagrid-rejectBillAddPage").datagrid('getRows');
    				var leng = rows.length;
    				if(leng > 0){
    					$("#sales-parentid-rejectBill").val(rows[0].billno);
    				}
    				if(leng < 12){
    					for(var i=leng; i<12; i++){
    						$("#sales-datagrid-rejectBillAddPage").datagrid('appendRow',{});
    					}
    				}
    				else{
    					$("#sales-datagrid-rejectBillAddPage").datagrid('appendRow',{});
    				}
    				countTotal();
    			},
    			onDblClickRow: function(rowIndex, rowData){
    				onClickCell(rowIndex, "taxprice");
    			},
    			onClickCell: function(index, field, value){
    				onClickCell(index, field);
    			}
    		}).datagrid('columnMoving');
    		$("#sales-form-check-rejectBillAddPage").form({
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
		  		      	$.messager.alert("提醒","操作成功");
		  		      	//刷新销售退货验收列表
		    	  		tabsWindowURL("/sales/rejectBillCheckListPage.do").$("#sales-datagrid-rejectBillCheckListPage").datagrid('reload');
		    	  		//关闭当前标签页
		    	  		top.closeNowTab();
		  		      	//$("#sales-panel-rejectBill").panel('refresh', 'sales/rejectBillCheckEditPage.do?id=${rejectBill.id }');
		  		    }
		  		    else{
                        $("#sales-panel-rejectBill").panel('refresh', 'sales/rejectBillCheckEditPage.do?id=${rejectBill.id }');
		  		       	$.messager.alert("提醒","操作失败。"+json.msg);
		  		    }
		  		}
		  	});
    		$("#sales-customer-rejectBillAddPage").customerWidget({ //客户参照窗口
    			required:true,
    			isdatasql:false,
    			onSelect:function(data){
    				$("#sales-customer-showid-dispatchBillAddPage").text("编号："+ data.id);
    				$("#sales-salesMan-rejectBillAddPage").widget("setValue",data.salesuserid);
    				if(data.salesdeptid!=null && data.salesdeptid!=""){
    					$("#sales-salesDept-rejectBillAddPage").widget("setValue",data.salesdeptid);
    				}else{
    					$("#sales-salesDept-rejectBillAddPage").widget("clear"); 					
    				}
    				$("input[name='rejectBill.remark']").focus();
    				//客户变更后 更新明细价格数据
    				changeGoodsPrice();
    			},
    			onClear:function(){
    				$("#sales-customer-showid-dispatchBillAddPage").text("");
    				$("#sales-handler-rejectBillAddPage").val('');
    			}
    		});
    		$("#sales-storage-rejectBillAddPage").widget({
    			referwid:'RL_T_BASE_STORAGE_INFO',
    			width:150,
				singleSelect:true
    		});
    		$("#sales-salesDept-rejectBillAddPage").widget({
    			name:'t_sales_rejectbill',
				col:'salesdept',
    			width:150,
    			readonly:salesReadonly,
				singleSelect:true
    		});
    		$("#sales-driver-rejectBillAddPage").widget({
    			referwid:'RL_T_BASE_PERSONNEL_LOGISTICS',
    			width:150,
				singleSelect:true
    		});
    		$("#sales-salesMan-rejectBillAddPage").widget({
    			name:'t_sales_rejectbill',
				col:'salesuser',
				width:150,
				readonly:salesReadonly,
				singleSelect:true
    		});
		$("#sales-RowNormal-rejectBillCheckPage").click(function(){
    			var rows = $("#sales-datagrid-rejectBillAddPage").datagrid("getChecked");
                var customerid = $("#sales-customer-rejectBillAddPage").val();
                var date = $("#date").val();
    			if(rows!=null && rows.length>0){
    				for(var i=0;i<rows.length;i++){
    					var row = rows[i];
    					if(row.goodsid!=null){
    						row.deliverytype="0";
                            if(row.remark.indexOf("赠品") >-1){
                                row.remark = row.remark.replace("赠品","");
                            }else if(row.remark.indexOf("捆绑") >-1){
                                row.remark = row.remark.replace("捆绑","");
                            }
                            if(presentByZero == 0){
                                $.ajax({
                                    url: 'sales/getGoodsDetail.do',
                                    dataType: 'json',
                                    type: 'post',
                                    data:'id='+ row.goodsid +'&cid='+customerid+'&date='+ date+'&type=reject',
                                    success: function (json) {
                                        row.taxprice = json.detail.taxprice;
                                        row.boxprice = json.detail.boxprice;
                                        row.notaxprice = json.detail.notaxprice;
                                        row.notaxamount = Number(json.detail.notaxprice)*Number(row.unitnum);
                                        row.taxamount = Number(json.detail.taxprice) * Number(row.unitnum);
                                        row.tax = row.taxamount - row.notaxamount ;
                                        if(row.remark.indexOf("赠品") >-1){
                                            row.remark = row.remark.replace("赠品","");
                                        }
                                        var rowIndex = $wareList.datagrid('getRowIndex',row);
                                        $wareList.datagrid('updateRow',{index:rowIndex, row:row});
                                    }
                                });
                            }else{
                                var rowIndex = $wareList.datagrid('getRowIndex',row);
                                $wareList.datagrid('updateRow',{index:rowIndex, row:row});
                            }


    					}
    				}
    			}
    		});
			$("#sales-RowGive-rejectBillCheckPage").click(function(){
				var rows = $("#sales-datagrid-rejectBillAddPage").datagrid("getChecked");
    			if(rows!=null && rows.length>0){
    				for(var i=0;i<rows.length;i++){
    					var row = rows[i];
    					if(row.goodsid!=null){
    						row.deliverytype="1";
                            if(row.remark.indexOf("赠品") >-1){
                                row.remark = row.remark.replace("赠品","");
                            }else if(row.remark.indexOf("捆绑") >-1){
                                row.remark = row.remark.replace("捆绑","");
                            }
                            row.remark = row.remark +'赠品';
                            if(presentByZero == 0){
                                row.taxprice = 0;
                                row.boxprice = 0;
                                row.taxamount = 0;
                                row.notaxprice = 0;
                                row.notaxamount = 0;
                                row.tax = 0 ;
                            }
        					var rowIndex = $wareList.datagrid('getRowIndex',row);
        					$wareList.datagrid('updateRow',{index:rowIndex, row:row});
    					}
    				}
    			}
    		});
            $("#sales-bind-rejectBillCheckPage").click(function(){
                var rows = $("#sales-datagrid-rejectBillAddPage").datagrid("getChecked");
                if(rows!=null && rows.length>0){
                    for(var i=0;i<rows.length;i++){
                        var row = rows[i];
                        if(row.goodsid!=null){
                            row.deliverytype="2";
                            if(row.remark.indexOf("赠品") >-1){
                                row.remark = row.remark.replace("赠品","");
                            }else if(row.remark.indexOf("捆绑") >-1){
                                row.remark = row.remark.replace("捆绑","");
                            }
                            row.remark = row.remark +'捆绑';
                            var rowIndex = $wareList.datagrid('getRowIndex',row);
                            $wareList.datagrid('updateRow',{index:rowIndex, row:row});
                        }
                    }
                }
            });
            $("#sales-history-price-rejectBillAddPage").click(function(){
                showHistoryGoodsPrice();
            });
    		$("#sales-buttons-rejectBill").buttonWidget("setDataID", {id:'${rejectBill.id}', state:'${rejectBill.status}', type:'edit'});
    		
    	});
    	var $wareList = $("#sales-datagrid-rejectBillAddPage"); //商品datagrid的div对象
    	var editIndex = undefined;  
    	var thisIndex = undefined;
    	var editfiled = null;
      function endEditing(field){
          if (editIndex == undefined){return true}
          if ($wareList.datagrid('validateRow', editIndex)){
              if(field == "taxprice"){
                  var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'taxprice'});
                  var taxprice = $(ed.target).val();
                  $wareList.datagrid('endEdit', editIndex);
                  var row = $wareList.datagrid('getRows')[editIndex];
                  $.ajax({
                      url:'sales/getAmountByPrice.do',
                      dataType:'json',
                      type:'post',
                      data:{goodsid:row.goodsid,type:"1",price:taxprice,unitnum:row.unitnum},
                      success:function(json){
                          row.taxprice = json.taxprice;
                          row.boxprice = json.boxprice;
                          row.taxamount = json.taxamount;
                          row.notaxamount = json.notaxamount;
                          row.notaxprice = json.notaxprice;
                          row.tax = json.tax;
                          $wareList.datagrid('endEdit', editIndex);
                          $wareList.datagrid('updateRow',{index:editIndex, row:row});
                          countTotal();
                          editIndex = undefined;
                          return true;
                      }
                  });
              }else if(field == "boxprice"){
                  var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'boxprice'});
                  var boxprice = $(ed.target).val();
                  var row = $wareList.datagrid('getRows')[editIndex];
                  $.ajax({
                      url:'sales/getAmountByPrice.do',
                      dataType:'json',
                      type:'post',
                      data:{goodsid:row.goodsid,type:"2",price:boxprice,unitnum:row.unitnum},
                      success:function(json){
                          row.taxprice = json.taxprice;
                          row.boxprice = json.boxprice;
                          row.taxamount = json.taxamount;
                          row.notaxamount = json.notaxamount;
                          row.notaxprice = json.notaxprice;
                          row.tax = json.tax;
                          $wareList.datagrid('endEdit', editIndex);
                          $wareList.datagrid('updateRow',{index:editIndex, row:row});
                          countTotal();
                          editIndex = undefined;
                          return true;
                      }
                  });
              }else if(field == "taxamount"){
                  var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'taxamount'});
                  var taxamount = $(ed.target).val();
                  var row = $wareList.datagrid('getRows')[editIndex];
                  $.ajax({
                      url:'sales/getPriceByAmount.do',
                      dataType:'json',
                      type:'post',
                      data:{goodsid:row.goodsid,taxamount:taxamount,unitnum:row.unitnum},
                      success:function(json){
                          row.taxprice = json.taxprice;
                          row.boxprice = json.boxprice;
                          row.taxamount = json.taxamount;
                          row.notaxamount = json.notaxamount;
                          row.notaxprice = json.notaxprice;
                          row.tax = json.tax;
                          $wareList.datagrid('endEdit', editIndex);
                          $wareList.datagrid('updateRow',{index:editIndex, row:row});
                          countTotal();
                          editIndex = undefined;
                          return true;
                      }
                  });
              }
          } else {
              return false;
          }
      }
      function onClickCell(index, field){
          if (endEditing(editfiled)){
              var row = $wareList.datagrid('getRows')[index];
              if(row.goodsid == undefined){
                  return false;
              }
              editfiled = field;
              if(field == "taxprice" ||field == "boxprice" || field == "taxamount"){

                  $wareList.datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
//                    $wareList.datagrid('beginEdit', index);
                  editIndex = index;
                  thisIndex = index;
                  var ed = $wareList.datagrid('getEditor', {index:editIndex,field:field});
                  $(ed.target).next("span").children("input:text").focus();
                  $(ed.target).next("span").children("input:text").select();
              }
          }
      }
        function biginEditGoodsRecord() {
            var row = $wareList.datagrid('getRows')[index];
            if(row.goodsid == undefined){
                return false;
            }
            $wareList.datagrid('beginEdit', index);
        }
        //客户变更后 更新明细价格以及相关信息
    	function changeGoodsPrice(){
    		var oldcustomerid = $("#sales-customer-showid-hidden-dispatchBillAddPage").val();
    		var customerid = $("#sales-customer-rejectBillAddPage").customerWidget("getValue");
    		$("#sales-customer-showid-hidden-dispatchBillAddPage").val(customerid);
    		if(oldcustomerid!=null && oldcustomerid!="" && oldcustomerid!=customerid){
    			loading("客户变更，明细价格调整中");
    			var rows = $wareList.datagrid('getRows');
    			var count = 0;
	    		for(var i=0; i<rows.length; i++){
	    			var goodsid = rows[i].goodsid;
	    			var num = rows[i].unitnum;
	    			var date = $("input[name='saleorder.businessdate']").val();
	    			if(goodsid!=null && goodsid!=""){
	    				var row = rows[i];
	    				$.ajax({
							url:'sales/countSalesGoodsByCustomer.do',
							dataType:'json',
							type:'post',
							data:{customerid:customerid,goodsid:goodsid,num:num,date:date,type:"reject"},
							async:false,
							success:function(json){
								var rowIndex = $wareList.datagrid("getRowIndex",row);
								row.taxprice = json.taxprice;
								row.notaxprice = json.notaxprice;
								row.taxamount = json.taxamount;
								row.notaxamount = json.notaxamount;
								row.tax = json.tax;
								$wareList.datagrid('updateRow',{index:rowIndex, row:row});
                                $("#sales-salesDept-rejectBillAddPage").widget("setValue",json.salesdept);
							}
						});
	    			}
	    			count ++;
	    		}
	    		if(count>0){
	    			$("#sales-customer-hidden-orderAddPage").val(customerid);
	    			$.messager.alert("提醒", "客户变更，自动调整订单明细中的商品价格！");
	    		}
	    		loaded();
	    		countTotal();
    		}
    	}
    	$("#sales-buttons-rejectBill").buttonWidget("setDataID", {id:'${rejectBill.id}', state:'${rejectBill.status}', type:'view'});
    		$("#sales-buttons-rejectBill").buttonWidget("enableButton","button-savecheck");
   			$("#sales-buttons-rejectBill").buttonWidget("enableButton","button-split");
    		<c:if test="${rejectBill.status=='3' }">
				<c:if test="${rejectBill.billtype=='1'}">
				<c:if test="${rejectBill.receiptid==null || rejectBill.receiptid=='' }">
				$("#sales-buttons-rejectBill").buttonWidget("enableButton","button-split");
				</c:if>
				</c:if>
				<c:if test="${rejectBill.receiptid!=null && rejectBill.receiptid!='' }">
				$("#sales-buttons-rejectBill").buttonWidget("disableButton","button-split");
				$("#sales-buttons-rejectBill").buttonWidget("disableButton","button-savecheck");
				</c:if>
			</c:if>
			<c:if test="${rejectBill.status!='3' }">
				$("#sales-buttons-rejectBill").buttonWidget("disableButton","button-split");
			</c:if>
			<c:if test="${rejectBill.isinvoice=='3' }">
				$("#sales-buttons-rejectBill").buttonWidget("enableButton","button-checkcancel");
			</c:if>
			<c:if test="${rejectBill.isinvoice=='0' }">
				$("#sales-buttons-rejectBill").buttonWidget("disableButton","button-checkcancel");
			</c:if>
		  $("#storage-buttons-saleOutPage").buttonWidget("disableButton","button-printview-rejectBillCheck");
		  $("#storage-buttons-saleOutPage").buttonWidget("disableButton","button-print-rejectBillCheck");
		  <c:if test="${rejectBill.isinvoice=='1' ||rejectBill.isinvoice=='2' || rejectBill.isinvoice=='3' ||rejectBill.isinvoice=='4' ||rejectBill.isinvoice=='5' }">
		  $("#storage-buttons-saleOutPage").buttonWidget("enableButton","button-printview-rejectBillCheck");
		  $("#storage-buttons-saleOutPage").buttonWidget("enableButton","button-print-rejectBillCheck");
		  </c:if>
    </script>
  </body>
</html>
