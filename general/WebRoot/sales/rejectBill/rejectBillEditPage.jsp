<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售退货通知单修改页面</title>
  </head> 
  <body>
  <form action="" method="post" id="sales-form-id-rejectBillEditPage">
      <input type="hidden" name="id" value="${rejectBill.id }"/>
  </form>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-rejectBillAddPage" action="sales/updateRejectBill.do" method="post">
    		<input type="hidden" id="sales-addType-rejectBillAddPage" name="addType" />
    		<input type="hidden" id="sales-saveaudit-rejectBillAddPage" name="saveaudit" />
			<input type="hidden" id="sales-storager-rejectBillAddPage" name="storager" />
	    	<div data-options="region:'north',border:false" style="height:160px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len150" id="sales-id-rejectBillAddPage" name="rejectBill.id" value="${rejectBill.id }" readonly="readonly" /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" id="sales-businessdate-rejectBillAddPage" class="len150 Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${rejectBill.businessdate }" name="rejectBill.businessdate" /></td>
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
	    					<input type="text" id="sales-customer-rejectBillAddPage" name="rejectBill.customerid" value="${rejectBill.customerid }" text="<c:out value="${rejectBill.customername }"></c:out>" style="width: 300px;"/><span id="sales-customer-showid-dispatchBillAddPage" style="margin-left:5px;line-height:25px;">编号：${rejectBill.customerid }</span>
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
	    					<input type="text" id="sales-storage-rejectBillAddPage" class="len150" name="rejectBill.storageid" value="${rejectBill.storageid}"/>
	    				</td>
						<td class="len80 left">客户单号：</td>
						<td><input id="sales-sourceid-rejectBillAddPage" type="text" name="rejectBill.sourceid" value="${rejectBill.sourceid}" style="width: 150px;" /></td>
	    			</tr>
					<tr>
						<td class="len80 left">备注：</td>
						<td colspan="5"><input type="text" name="rejectBill.remark" value="<c:out value="${rejectBill.remark }"></c:out>" style="width: 680px;"/></td>
					</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="goodsjson" id="sales-goodsJson-rejectBillAddPage"/>
	    		<table id="sales-datagrid-rejectBillAddPage"></table>
	    	</div>
	    	<input type="hidden" id="sales-printtimes-rejectBillAddPage" value="${rejectBill.printtimes }"/>
	    	<input type="hidden" id="sales-printlimit-rejectBillAddPage" value="${printlimit }"/>
			<input type="hidden" name="rejectBill.version" value="${rejectBill.version }"/>
	    </form>
    </div>
    <div class="easyui-menu" id="sales-contextMenu-rejectBillAddPage" style="display: none;">
    	<div id="sales-addRow-rejectBillAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="sales-editRow-rejectBillAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="sales-removeRow-rejectBillAddPage" data-options="iconCls:'button-delete'">删除</div>
        <div id="sales-history-price-rejectBillAddPage" data-options="iconCls:'button-view'">查看历史销售价</div>
    </div>
    <div id="sales-dialog-rejectBillAddPage"></div>
    <div id="rejectBill-goods-history-price"></div>
    <script type="text/javascript">
	  //是否允许修改销售部门客户业务员
		var salesReadonly = true;
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
    			singleSelect: true,
    			data: JSON.parse('${goodsJson}'),
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
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$wareList.datagrid('selectRow', rowIndex);
                    $("#sales-contextMenu-rejectBillAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onDblClickRow: function(rowIndex, rowData){
    				if(rowData.goodsid == undefined){
    					beginAddDetail();
    				}
    				else{
    					beginEditDetail();
    				}
    			}
    		}).datagrid('columnMoving');
    		$("#sales-customer-rejectBillAddPage").customerWidget({ //客户参照窗口
    			required:true,
    			isopen:true,
    			onSelect:function(data){
    				$("#sales-customer-showid-dispatchBillAddPage").text("编号："+ data.id);
    				$("#sales-salesMan-rejectBillAddPage").widget("setValue",data.salesuserid);
    				if(data.salesdeptid!=null && data.salesdeptid!=""){
    					$("#sales-salesDept-rejectBillAddPage").widget("setValue",data.salesdeptid);
    				}else{
    					$("#sales-salesDept-rejectBillAddPage").widget("clear"); 					
    				}
    				$("input[name='rejectBill.remark']").focus();
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
				singleSelect:true,
                onSelect:function(data){
                    console.info(data);
                    var rows = $("#sales-datagrid-rejectBillAddPage").datagrid('getRows');
                    var msgFlg = false;
                    for(var i=0;i<rows.length; i++){
                        var storageid = rows[i].storageid;
                        if(storageid!=null && storageid!="" && data.id!=storageid){
                            msgFlg = true;
                            break;
                        }
                    }
                    if(msgFlg){
                        $.messager.confirm("提醒","入库仓库与商品明细中批次所属仓库不一致，保存后将以入库仓库为准",function(r){
                            if(r){

                            }
                        });
                    }
                }
    		});
    		$("#sales-salesDept-rejectBillAddPage").widget({
    			name:'t_sales_rejectbill',
				col:'salesdept',
				readonly:salesReadonly,
    			width:150,
				singleSelect:true
    		});
    		$("#sales-salesMan-rejectBillAddPage").widget({
    			name:'t_sales_rejectbill',
				col:'salesuser',
				readonly:salesReadonly,
				width:150,
				singleSelect:true
    		});
    		$("#sales-driver-rejectBillAddPage").widget({
    			referwid:'RL_T_BASE_PERSONNEL_LOGISTICS',
    			width:150,
				singleSelect:true,
				onSelect:function(data){
					$("#sales-salesDept-rejectBillAddPage").focus();
				}
    		});
    		$("#sales-addRow-rejectBillAddPage").click(function(){
    			beginAddDetail();
    		});
    		$("#sales-editRow-rejectBillAddPage").click(function(){
    			beginEditDetail();
    		});
    		$("#sales-removeRow-rejectBillAddPage").click(function(){
    			removeDetail();
    		});
            $("#sales-history-price-rejectBillAddPage").click(function(){
                showHistoryGoodsPrice();
            });
    		$("#sales-buttons-rejectBill").buttonWidget("setDataID", {id:'${rejectBill.id}', state:'${rejectBill.status}', type:'edit'});

			<c:choose>
			<c:when test="${rejectBill.source=='8' && rejectBill.status!='9' }">
			$("#sales-buttons-rejectBill").buttonWidget("disableButton","button-print-sales");
			$("#sales-buttons-rejectBill").buttonWidget("disableButton","button-printview-sales");
			</c:when>
			<c:otherwise>
			$("#sales-buttons-rejectBill").buttonWidget("enableButton","button-print-sales");
			$("#sales-buttons-rejectBill").buttonWidget("enableButton","button-printview-sales");
			</c:otherwise>
			</c:choose>
    	});
    	var $wareList = $("#sales-datagrid-rejectBillAddPage"); //商品datagrid的div对象
    	$("#sales-buttons-rejectBill").buttonWidget("disableButton","button-split");

	  //历史价格查看
	  function showHistoryGoodsPrice(){
		  var row = $("#sales-datagrid-rejectBillAddPage").datagrid('getSelected');
		  if(row == null){
			  $.messager.alert("提醒", "请选择一条记录");
			  return false;
		  }
		  var businessdate = $("#sales-businessdate-rejectBillAddPage").val();
		  var customerid = $("#sales-customer-showid-hidden-dispatchBillAddPage").val();
		  var customername = $("#sales-customer-rejectBillAddPage").customerWidget('getText');
		  var goodsid = row.goodsid;
		  var goodsname = row.goodsInfo.name;
		  $("#rejectBill-goods-history-price").dialog({
			  title:'客户['+customerid+'] 商品['+goodsid+']'+goodsname+' 历史价格表',
			  width:600,
			  height:400,
			  closed:false,
			  modal:true,
			  cache:false,
			  maximizable:true,
			  resizable:true,
			  href:'sales/showRejectBillHistoryGoodsPricePage.do',
			  queryParams:{customerid:customerid,goodsid:goodsid,businessdate:businessdate}
		  });
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
    	<%--

		    <c:choose>
		    	<c:when test="${rejectBill.source=='8' && (rejectBill.status!='9' || rejectBill.status=='9' && printlimit=='1' ) }">
					$("#sales-buttons-rejectBill").buttonWidget("disableButton","button-print-sales");
					$("#sales-buttons-rejectBill").buttonWidget("disableButton","button-printview-sales");
		    	</c:when>
		    	<c:otherwise>
					$("#sales-buttons-rejectBill").buttonWidget("enableButton","button-print-sales");
					$("#sales-buttons-rejectBill").buttonWidget("enableButton","button-printview-sales");
		    	</c:otherwise>
		    </c:choose>
	    --%>
    </script>
  </body>
</html>
