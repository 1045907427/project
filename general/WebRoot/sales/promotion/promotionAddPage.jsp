<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>促销活动添加页面</title>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-promotionAddPage" action="sales/addPromotionGroup.do" method="post">
    		<input type="hidden" id="sales-addType-promotionAddPage" name="addType" />
    		<input id="sales-saveaudit-promotionAddPage" name="saveaudit" />
	    	<div data-options="region:'north',border:false" style="height:100px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5x">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165">
	    				<input id="sales-promotionViewPage-id" class="len150 easyui-validatebox" name="promotionPackage.id" readonly = "readonly" /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len150" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate }" name="businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
                            <select id="sales-promotionAddPage-status" readonly = "readonly" class="len150"  disabled="disabled">
                                <option value="4">新增</option>
                            </select></td>
	    			</tr>
	    			<tr>
	    			<td class="len80 left">客户群：</td>
	    				<td>
                            <input type="text" id="sales-customertype-promotionAddPage" name="promotionPackage.customertype"/>
	    				</td>
	    				<td class="len80 left">客户群名称：</td>
	    				<td id="customertd"><input id="sales-customer-promotionAddPage" readonly class="len150 no_input" name="promotionPackage.customerid" /></td>
                        <td class="len80 left">制单人：</td>
                        <td><input id="sales-applyuser-promotionAddPage"  class="len150" name="promotionPackage.addusername" value="${user.name }" readonly/></td>
                    </tr>
	    			<tr>
	    				<td class="len80 left">生效日期：</td>
	    				<td><input id="sales-beginDate-promotionAddPage" class="len150 easyui-validatebox" name="promotionPackage.begindate" data-options="required:true" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"  value="${date }" /></td>
	    				<td class="len80 left">截止日期：</td>
	    				<td><input id="sales-endDate-promotionAddPage"  class="len150 easyui-validatebox" name="promotionPackage.enddate" data-options="required:true" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${dateAfter}"/></td>
                        <td class="len80 left">备注：</td>
                        <td colspan="3"><input type="text" name="promotionPackage.remark" class="len150"/></td>
                    </tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="sales-datagrid-promotionAddPage"></table>
                <input id="sales-promotion-activityType" name="promotionPackage.ptype" value="${act }"/>
                <input id="sales-promotion-detailId"  value="${detailId}"/>
	    		<input type="hidden" name="groupAndGoodsjson" id="sales-goodsJson-promotionAddPage" />
	    	</div>
	    </form>
    </div>

    <div class="easyui-menu" id="sales-contextMenu-promotionAddPage" style="display: none;">
    	<div id="sales-addRow-promotionAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="sales-editRow-promotionAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="sales-removeRow-promotionAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <script type="text/javascript">

        var type = $("#sales-promotion-activityType").val();

    	$(function(){
    		$("#sales-buttons-promotionPage").buttonWidget("initButtonType", 'add');

            $("#sales-customertype-promotionAddPage").widget({
                name:'t_sales_promotion_package',
                col:'customertype',
                singleSelect:false,
                width:150,
                treePName:false,
                onSelect:function(data){
                    changeCustomerWidget(data.id,"","0");
                }
            });

//            $("#sales-customer-promotionAddPage").widget({
//                referwid:"RL_T_BASE_SALES_CUSTOMER",
//                singleSelect:false,
//                isPageReLoad:false,
//                required:true,
//                width:150
//            });

            if(type == '1' || type == '3'){
                $("#sales-datagrid-promotionAddPage").datagrid({
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
                        $wareList.datagrid('selectRow',cursorIndex(rowIndex,rowData));
                        $("#sales-contextMenu-promotionAddPage").menu('show', {
                            left:e.pageX,
                            top:e.pageY
                        });
                    },
                    onDblClickRow: function(rowIndex, rowData){
                        var type = $("#sales-promotion-activityType").val();
                        rowIndex = cursorIndex(rowIndex,rowData);
                        $wareList.datagrid('selectRow',rowIndex );
                        var rows = $("#sales-datagrid-promotionAddPage").datagrid('getRows');
                        if(rowData.goodsid == undefined){
                                beginAddGroup1(type);
                        }else{
                            var count = 0;
                            var rowList = [];
                            for( var i = 0;i<20 ;i++){
                                if(rows[i].groupname == rowData.groupname){
                                    rowList.push(rows[i]);
                                    ++ count ;
                                }
                            }
                            beginEditGroup(rowIndex, rowList, type,count);
                        }
                    }
                }).datagrid("loadData", {'total':20,'rows':[{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{} ]}).datagrid('columnMoving');
            }else{
                $("#sales-datagrid-promotionAddPage").datagrid({
                    authority:bundleJson,
                    columns:bundleJson.common,
                    frozenColumns: bundleJson.frozen,
                    border: true,
                    fit: true,
                    rownumbers: true,
                    showFooter: true,
                    striped:true,
                    singleSelect: true,
                    onRowContextMenu: function(e, rowIndex, rowData){
                        e.preventDefault();
                        rowIndex = cursorIndex(rowIndex,rowData);
                        $wareList.datagrid('selectRow',rowIndex);
                        $("#sales-contextMenu-promotionAddPage").menu('show', {
                            left:e.pageX,
                            top:e.pageY
                        });
                    },
                    onDblClickRow: function(rowIndex, rowData){
                        rowIndex = cursorIndex(rowIndex,rowData);
                        $wareList.datagrid('selectRow',rowIndex);
                        if(rowData.groupname == undefined){
                                beginAddGroup2(type,rowIndex);
                        }else{
                            var rows = $("#sales-datagrid-promotionAddPage").datagrid('getRows');
                            var count = 0;
                            var rowList = [];
                            for( var i = 0;i<20 ;i++){
                                if(rows[i].groupname != undefined ){
                                    if(rows[i].groupname == rowData.groupname){
                                        rowList.push(rows[i]);
                                        ++ count ;
                                    }

                                }
                            }
                            beginEditGroup(rowIndex, rowList, type,count);
                        }
                    }
                }).datagrid("loadData", {'total':20,'rows':[{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{} ]}).datagrid('columnMoving');

            }

            $("#sales-removeRow-promotionAddPage").click(function(){
                removeDetail();
            });

            $("#sales-addRow-promotionAddPage").click(function(){
                beginAddRow();
            });

            $("#sales-editRow-promotionAddPage").click(function(){
                editRow();
            });

            //提交表单
            $("#sales-form-promotionAddPage").form({
                onSubmit:function(){
                    var flag =$(this).form('validate');
                    if(flag == false) return false;
					var customertype = $("#sales-customertype-promotionAddPage").widget('getValue');
					var customer = "";
                    if("1" == customertype){
                        customer = $("#sales-customer-promotionAddPage").widget('getValue');
                    }else if("2" == customertype){
                        customer = $("#sales-promotionsort-promotionAddPage").widget('getValue');
                    }else if("3" == customertype){
                       customer = $("#sales-customersort-promotionAddPage").widget('getValue');
                    }else if("4" == customertype){
                       customer = $("#sales-pricelist-promotionAddPage").widget('getValue');
                    }else if("5" == customertype){
                       customer = $("#sales-salesarea-promotionAddPage").widget('getValue');
                    }else if("6" == customertype){
                       customer = $("#sales-pcustomer-promotionAddPage").widget('getValue');
                    }else if("7" == customertype){
                       customer = $("#sales-crenditrating-promotionAddPage").widget('getValue');
                    }else if("8" == customertype){
                       customer = $("#sales-canceltype-promotionAddPage").widget('getValue');
                    }else if("0" == customertype){

                    }
					if( customer != undefined && customer!="" ){
                        var arr = customer.split(",");
                        if(arr.length > 30){
                            $.messager.alert("提醒","当前客户数"+arr.length+"个，一般不超过30个，请修改选中的客户数量");
                            return false ;
                        }
					}
                    loading("提交中……");
                },
                success:function(data){
                    loaded();
                    var json = $.parseJSON(data);
                    var act = $("#sales-promotion-activityType").val();
                    if(json.flag == true){
                        $.messager.alert("提醒","保存成功");
                        $("#sales-backid-promotionPage").val(json.id);
                        $("#sales-promotion-detailId").val();
                        $("#sales-panel-promotionPage").panel('refresh', 'sales/promotionEditPage.do?id='+ json.id+"&act="+act);
                        $("#sales-buttons-promotionPage").buttonWidget("addNewDataId", json.id);
                    }else{
                        $.messager.alert("提醒","保存失败");
                    }
                }
            });

        });

        var $wareList = $("#sales-datagrid-promotionAddPage");
    	
        $("#sales-applydept-promotionAddPage").widget({ //申请部门参照窗口
            name:'t_sales_offprice',
            col:'applydeptid',
            singleSelect:true,
            width:130,
            onlyLeafCheck:true,
            onSelect:function(data){
                $("#sales-applyuser-promotionAddPage").widget({ //申请人参照窗口
                    name:'t_sales_promotion_package',
                    col:'applyuserid',
                    singleSelect:true,
                    width:130,
                    onlyLeafCheck:true,
                    param:[{field:'pid',op:'equal',value:data.id}]
                });
            }
        });

    </script>
    </body>
    </html>
