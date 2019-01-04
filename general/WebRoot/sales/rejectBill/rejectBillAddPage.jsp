<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售退货通知单新增页面</title>
  </head>  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-rejectBillAddPage" action="sales/addRejectBill.do" method="post">
    		<input type="hidden" id="sales-addType-rejectBillAddPage" name="addType" />
    		<input type="hidden" id="sales-saveaudit-rejectBillAddPage" name="saveaudit" />
			<input type="hidden" id="sales-storager-rejectBillAddPage" name="storager" />
	    	<div data-options="region:'north',border:false" style="height:160px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len136 easyui-validatebox" name="rejectBill.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len130" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate }" name="rejectBill.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select disabled="disabled" class="len136"><option>新增</option></select>
	    					<input type="hidden" class="len130" value="9" name="rejectBill.source" />
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td colspan="3"><input type="text" id="sales-customer-rejectBillAddPage" name="rejectBill.customerid" style="width: 300px;" required="required" /><span id="sales-customer-showid-dispatchBillAddPage" style="margin-left:5px;line-height:25px;"></span></td>
	    				<td class="len80 left">司机：</td>
	    				<td>
	    					<input type="text" id="sales-driver-rejectBillAddPage" class="len136" name="rejectBill.driverid" />
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">销售部门：</td>
	    				<td>
	    					<input id="sales-salesDept-rejectBillAddPage" class="len136" name="rejectBill.salesdept"/>
	    				</td>
	    				<td class="len80 left">客户业务员：</td>
	    				<td>
	    					<input type="text" id="sales-salesMan-rejectBillAddPage" class="len136" name="rejectBill.salesuser"/>
	    				</td>
	    				<td class="len80 left">退货类型：</td>
	    				<td>
	    					<select class="len136" name="rejectBill.billtype">
	    						<option value="1">直退</option>
	    						<option value="2" selected="selected">售后退货</option>
	    					</select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">入库仓库：</td>
	    				<td>
	    					<input type="text" id="sales-storage-rejectBillAddPage" class="len136" name="rejectBill.storageid" value="${defaultStorageid }"/>
	    				</td>
	    				<td class="len80 left">客户单号：</td>
	    				<td><input id="sales-sourceid-rejectBillAddPage" type="text" name="rejectBill.sourceid" style="width: 130px;" /></td>
	    			</tr>
					<tr>
						<td class="len80 left">备注：</td>
						<td colspan="5"><input id="sales-billremark-rejectBillAddPage" type="text" name="rejectBill.remark" style="width: 665px;" /></td>
					</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="goodsjson" id="sales-goodsJson-rejectBillAddPage" />
	    		<table id="sales-datagrid-rejectBillAddPage"></table>
	    	</div>
	    </form>
    </div>
    <div class="easyui-menu" id="sales-contextMenu-rejectBillAddPage" style="display: none;">
    	<div id="sales-addRow-rejectBillAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="sales-editRow-rejectBillAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="sales-removeRow-rejectBillAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="sales-dialog-rejectBillAddPage" class="easyui-dialog" data-options="closed:true"></div>
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
    		}).datagrid("loadData", {'total':12,'rows':[{},{},{},{},{},{},{},{},{},{},{},{} ],'footer':[{goodsid:'合计'}]}).datagrid('columnMoving');
    	
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
    				$("input[name='rejectBill.sourceid']").focus();
    			},
    			onClear:function(){
    				$("#sales-customer-showid-dispatchBillAddPage").text("");
    				$("#sales-handler-rejectBillAddPage").val('');
    			}
    		});
    		$("#sales-storage-rejectBillAddPage").widget({
    			referwid:'RL_T_BASE_STORAGE_INFO',
    			width:130,
				singleSelect:true,
                onSelect:function(data){
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
    			width:130,
				singleSelect:true
    		});
    		$("#sales-salesMan-rejectBillAddPage").widget({
    			name:'t_sales_rejectbill',
				col:'salesuser',
				readonly:salesReadonly,
				width:130,
				singleSelect:true
    		});
    		$("#sales-driver-rejectBillAddPage").widget({
    			referwid:'RL_T_BASE_PERSONNEL_LOGISTICS',
    			width:136,
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
    		$("#sales-buttons-rejectBill").buttonWidget("initButtonType", 'add');
    		$("#sales-buttons-rejectBill").buttonWidget("disableButton","button-printview-sales");
			$("#sales-buttons-rejectBill").buttonWidget("disableButton","button-print-sales");
            $("#sales-sourceid-rejectBillAddPage").die("keydown").live("keydown",function(event){
                if(event.keyCode==13){
                    $("input[name='rejectBill.remark']").focus();
                }
            });
    		$("#sales-billremark-rejectBillAddPage").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					beginAddDetail();
				}
			});
			
    	});
    	var $wareList = $("#sales-datagrid-rejectBillAddPage"); //商品datagrid的div对象
    	
    </script>
  </body>
</html>
