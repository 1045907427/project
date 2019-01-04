<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售特价调整单添加页面</title>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-offpriceAddPage" action="sales/addOffprice.do" method="post">
    		<input type="hidden" id="sales-addType-offpriceAddPage" name="addType" />
    		<input type="hidden" id="sales-saveaudit-offpriceAddPage" name="saveaudit" />
	    	<div data-options="region:'north',border:false" style="height:100px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编&nbsp;&nbsp;号：</td>
	    				<td class="len165"><input class="len150 easyui-validatebox" name="offprice.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len150" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate }" name="offprice.businessdate" /></td>
	    				<td class="len80 left">状&nbsp;&nbsp;态：</td>
	    				<td class="len165"><select disabled="disabled" class="len150"><option>新增</option></select></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客 户 群：</td>
	    				<td>
                            <input type="text" id="sales-customertype-offpriceAddPage" name="offprice.customertype"/>
	    				</td>
	    				<td class="len80 left">客户群名称：</td>
	    				<td id="customertd">
                            <input type="text" id="sales-customer-offpriceAddPage" readonly="readonly" class="no_input" name="offprice.customerid"/>
                        </td>
                        <td class="len80 left">档期：</td>
                        <td><input type="text" id="sales-schedule-offpriceAddPage" class="len150 easyui-validatebox" validType="illegalChar" name="offprice.schedule"/>
                            <input type="hidden" name="offprice.adduserid" value="${offprice.adduserid}"/>
                            <input type="hidden" name="offprice.applyuserid" value="${offprice.applyuserid}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="len80 left">生效日期：</td>
                        <td><input class="len150 easyui-validatebox" name="offprice.begindate" value="${offprice.begindate }" data-options="required:true" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
	    				<td class="len80 left">截止日期：</td>
	    				<td><input class="len150 easyui-validatebox" name="offprice.enddate" data-options="required:true" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
                        <td class="len80 left">备&nbsp;&nbsp;注：</td>
                        <td><input type="text" name="offprice.remark" value="<c:out value="${offprice.remark }"></c:out>" class="len150" /></td>

                    </tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="goodsjson" id="sales-goodsJson-offpriceAddPage" />
	    		<table id="sales-datagrid-offpriceAddPage"></table>
	    	</div>
	    </form>
    </div>
    <div class="easyui-menu" id="sales-contextMenu-offpriceAddPage" style="display: none;">
    	<div id="sales-addRow-offpriceAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="sales-editRow-offpriceAddPage" data-options="iconCls:'button-edit'">编辑</div>
        <div id="sales-addDetailByBrandAndSort-offpriceAddPage" data-options="iconCls:'button-add'">批量添加商品</div>
    	<div id="sales-removeRow-offpriceAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="sales-dialog-offpriceAddPage" class="easyui-dialog" data-options="closed:true"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#sales-datagrid-offpriceAddPage").datagrid({ //特价商品明细信息编辑
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
                    $("#sales-contextMenu-offpriceAddPage").menu('show', {  
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
    		}).datagrid("loadData", {'total':12,'rows':[{},{},{},{},{},{},{},{},{},{},{},{} ]}).datagrid('columnMoving');
    		$("#sales-addRow-offpriceAddPage").click(function(){ //添加一行
		  		beginAddDetail();
		  	});
		  	$("#sales-removeRow-offpriceAddPage").click(function(){
		  		removeDetail();
		  	});
		  	$("#sales-editRow-offpriceAddPage").click(function(){
		  		beginEditDetail();
		  	});
//    		$("#sales-customer-offpriceAddPage").widget({//客户群名称
//			    referwid:"RL_T_BASE_SALES_CUSTOMER",
//                singleSelect:false,
//                isPageReLoad:false,
//			    required:true,
//				width:150
//			});

			$("#sales-customertype-offpriceAddPage").widget({
                name:'t_sales_offprice',
                col:'customertype',
                singleSelect:false,
                treePName:false,
                width:150,
                onSelect:function(data){
                    changeCustomerWidget(data.id,"","0");
				}

			});

            //批量添加
            $("#sales-addDetailByBrandAndSort-offpriceAddPage").click(function(){
                beginAddDetailByBrandAndSort();
            });


			$("#sales-buttons-offpricePage").buttonWidget("initButtonType", 'add');

    	});
    	var $wareList = $("#sales-datagrid-offpriceAddPage"); //商品datagrid的div对象
    </script>
  </body>
</html>
