<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售订单复制页面</title>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-orderAddPage" action="sales/addOrder.do" method="post">
    		<input type="hidden" id="sales-addType-orderAddPage" name="addType" />
	    	<div data-options="region:'north',border:false" style="height:140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len136 easyui-validatebox" id="sales-id-orderAddPage" name="saleorder.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len130 Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${saleorder.businessdate }" name="saleorder.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165"><select disabled="disabled" class="len136"><option>新增</option></select></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td colspan="3">
	    					<input type="text" id="sales-customer-orderAddPage" name="saleorder.customerid" text="<c:out value="${saleorder.customername }"></c:out>" value="${saleorder.customerid }" style="width: 320px;"/><span id="sales-customer-showid-orderAddPage" style="margin-left:5px;line-height:25px;">
                            编号：<a href="javascript:showCustomer(${saleorder.customerid })">${saleorder.customerid }</a></span>
	    					<input type="hidden" id="sales-customer-hidden-orderAddPage" value="${saleorder.customerid }"/>
	    				</td>
                        <td>销售部门：</td>
                        <td class="len165">
							<input id="sales-salesDept-orderAddPage" type="text" name="saleorder.salesdept" value="${saleorder.salesdept }" />
                        </td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">发货仓库：</td>
	    				<td><input id="sales-storageid-orderAddPage" name="saleorder.storageid" value="${saleorder.storageid }"/>
	    				</td>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="3"><input id="sales-remark-orderDetailAddPage" type="text" name="saleorder.remark" style="width:400px;" value="<c:out value="${saleorder.remark }"></c:out>" onfocus="frm_focus('saleorder.remark');" onblur="frm_blur('saleorder.remark');" /></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="goodsjson" id="sales-goodsJson-orderAddPage" />
	    		<input id="sales-saveaudit-orderDetailAddPage" type="hidden" name="saveaudit" value="save"/>
	    		<table id="sales-datagrid-orderAddPage"></table>
	    	</div>
	    </form>
    </div>
    <div class="easyui-menu" id="sales-contextMenu-orderAddPage">
    	<div id="sales-addRow-orderAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="sales-editRow-orderAddPage" data-options="iconCls:'button-edit'">修改</div>
    	<security:authorize url="/sales/orderDiscountAddPage.do">
    	<div id="sales-addRow-orderAddDiscountPage" data-options="iconCls:'button-add'">添加商品折扣</div>
    	</security:authorize>
    	<security:authorize url="/sales/orderBrandDiscountAddPage.do">
    	<div id="sales-addRow-orderAddBrandDiscountPage" data-options="iconCls:'button-add'">添加品牌折扣</div>
    	</security:authorize>
    	<div id="sales-removeRow-orderAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="sales-dialog-orderAddPage" class="easyui-dialog" data-options="closed:true"></div>
    <div id="sales-dialog-orderGoodsPromotion-ptype"></div>
    <script type="text/javascript">
        var isadd = '${isadd}';
        var salesReadonly = true;
        <security:authorize url="/sales/orderEditSalesuserAndSalesdept.do">
        salesReadonly = false;
        </security:authorize>
        var leftAmount = '${leftAmount}';
        var receivableAmount = '${receivableAmount}';

    	$(function(){
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
                        $("#sales-backid-orderPage").val(json.backid);
                        if(saveaudit=="saveaudit"){
                            if(json.auditflag){
                                $.messager.alert("提醒","保存审核成功，"+json.msg);
                                if(json.type == "add"){
                                    $("#sales-buttons-orderPage").buttonWidget("addNewDataId", json.backid);
                                }
                                $("#sales-panel-orderPage").panel('refresh', 'sales/orderEditPage.do?id='+json.backid);
                                // $("#sales-panel-orderPage").panel('refresh', 'sales/orderAddPage.do');
                            }else{
                                $.messager.alert("提醒","保存成功,审核失败。"+json.msg);
                                $("#sales-panel-orderPage").panel('refresh', 'sales/orderEditPage.do?id='+json.backid);
                            }
                        }else{
                            if(isadd == ""){
                                $("#sales-dialog-version-orderPage").dialog({
                                    title:'提醒',
                                    width:300,
                                    height:160,
                                    closed:true,
                                    modal:true,
                                    cache:false,
                                    //closable:false,
                                    maximizable:false,
                                    resizable:true,
                                    href:'sales/messageRedictPage.do?id='+json.backid,
                                    onClose:function () {
                                        $("#sales-setContractPrice-orderAddPage").show();
                                        var id = $("#sales-backid-orderPage").val();
                                        $("#sales-panel-orderPage").panel('refresh', 'sales/orderEditPage.do?id='+id);
                                    }
                                });
                                $("#sales-dialog-version-orderPage").dialog('open');
                            }else if(isadd == "add"){
                                $.messager.alert("提醒","保存成功！");
                                if(json.type == "add"){
                                    $("#sales-buttons-orderPage").buttonWidget("addNewDataId", json.backid);
                                }
                                $("#sales-panel-orderPage").panel('refresh', 'sales/orderAddPage.do');
                            }else if(isadd == "edit"){
                                $("#sales-setContractPrice-orderAddPage").show();
                                $.messager.alert("提醒","保存成功！");
                                $("#sales-panel-orderPage").panel('refresh', 'sales/orderEditPage.do?id='+json.backid);
                            }
                        }
		  		    }
		  		    else{
		  		       	$.messager.alert("提醒","保存失败");
		  		    }
		  		    chooseGoods="";
		  		}
		  	});
    		$("#sales-customer-orderAddPage").customerWidget({ //客户参照窗口
    			name:'t_sales_order',
				col:'customerid',
    			width:130,
    			isopen:true,
    			required:true,
    			onSelect:function(data){
                    var html = "编号："+'<a href="javascript:showCustomer(\''+data.id+'\')">'+data.id+'</a>';
                    $("#sales-customer-showid-orderAddPage").html(html);
                    $("#sales-customer-orderAddPage").customerWidget("setValue",data.id);
                    $("#sales-salesMan-orderAddPage").widget("setValue",data.salesuserid);
                    if(data.salesdeptid!=null && data.salesdeptid!=""){
                        $("#sales-salesDept-orderAddPage").widget("setValue",data.salesdeptid);
                    }else{
                        $("#sales-salesDept-orderAddPage").widget("clear");
                    }
                    //客户变更后 更新明细价格数据
                    changeGoodsPrice();
                    $("#sales-remark-orderDetailAddPage").focus();
                    <c:if test="${isOrderStorageSelect=='1'}">
                    $("#sales-storageid-orderAddPage").focus();
                    </c:if>
    			},
    			onClear:function(){
                    $("#sales-customer-showid-orderAddPage").text("");
    			}
    		});
            $("#sales-salesDept-orderAddPage").widget({
                referwid:'RL_T_BASE_DEPARTMENT_SELLER',
                width:150,
                readonly:salesReadonly,
                singleSelect:true
            });
            $("#sales-salesMan-orderAddPage").widget({
                referwid:'RL_T_BASE_PERSONNEL_SELLER',
                width:150,
                readonly:salesReadonly,
                singleSelect:true,
				required:true
            });
			$("#sales-storageid-orderAddPage").widget({
				referwid:'RL_T_BASE_STORAGE_INFO',
				width:150,
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
	    	//折扣添加页面
    		$("#sales-addRow-orderAddDiscountPage").click(function(){
    			beginAddDiscountDetail();
    		});
    		//添加品牌折扣
    		$("#sales-addRow-orderAddBrandDiscountPage").click(function(){
    			beginAddBrandDiscountDetail();
    		});
    		$("#sales-addRow-orderAddPage").click(function(){
    			beginAddDetail();
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
				}else{
					beginEditDetail(row);
				}
    		});
    		$("#sales-removeRow-orderAddPage").click(function(){
    			removeDetail();
    		});
    		$("#sales-buttons-orderPage").buttonWidget("initButtonType", 'add');
    		$("#button-invalid").linkbutton("disable");
    		$("#button-uninvalid").linkbutton("disable");
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
    			data: JSON.parse('${goodsJson}'),
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
    					}else{
    						beginEditDetail(rowData);
    					}
    				}
    			}
    		}).datagrid('columnMoving');
    	});
    	var $wareList = $("#sales-datagrid-orderAddPage"); //商品datagrid的div对象
    </script>
  </body>
</html>
