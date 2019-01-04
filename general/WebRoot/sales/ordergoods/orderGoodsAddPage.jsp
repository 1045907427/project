<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>销售订货单新增页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <form id="sales-form-orderGoodsAddPage" action="sales/addOrderGoods.do" method="post">
        <input type="hidden" id="sales-addType-orderGoodsAddPage" name="addType" />
        <div data-options="region:'north',border:false" style="height:140px;">
            <table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
                <tr>
                    <td class="len80 left">编&nbsp;&nbsp;号：</td>
                    <td class="len165"><input class="len150 easyui-validatebox" id="sales-id-orderGoodsAddPage" name="orderGoods.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
                    <td class="len80 left">业务日期：</td>
                    <td class="len165"><input id="sales-businessdate-orderGoodsAddPage" type="text" class="len150 Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate }" name="orderGoods.businessdate" /></td>
                    <td class="len80 left">状&nbsp;&nbsp;态：</td>
                    <td class="len165"><select disabled="disabled" class="len150"><option>新增</option></select></td>
                </tr>
                <tr>
                    <td class="len80 left">客&nbsp;&nbsp;户：</td>
                    <td colspan="3">
                        <input type="text" id="sales-customer-orderGoodsAddPage" name="orderGoods.customerid" style="width: 300px;" required="required" /><span id="sales-customer-showid-orderGoodsAddPage" style="margin-left:5px;line-height:25px;"></span>
                        <input type="hidden" id="sales-customer-hidden-orderGoodsAddPage"/>
                    </td>
                    <%--<td>来源类型：</td>--%>
                    <%--<td class="len150">--%>
                        <%--<select disabled="disabled" style="width: 150px;">--%>
                            <%--<option value="0"selected="selected">普通订单</option>--%>
                            <%--<option value="1">手机订单</option>--%>
                            <%--<option value="2">零售车销</option>--%>
                        <%--</select>--%>
                    <%--</td>--%>
                </tr>
                <tr>
                    <td class="len80 left">销售部门：</td>
                    <td>
                        <input type="text" id="sales-salesDept-orderGoodsAddPage" name="orderGoods.salesdept" />
                    </td>
                    <td class="len80 left">客户业务员：</td>
                    <td>
                        <input type="text" id="sales-salesMan-orderGoodsAddPage" name="orderGoods.salesuser"/>
                    </td>
                    <td class="len80 left">提货券编号：</td>
                    <td>
                        <input type="text" id="sales-ladingbill-orderGoodsAddPage" name="orderGoods.ladingbill"/>
                    </td>
                </tr>
                <tr>
                    <td class="len80 left">发货仓库：</td>
                    <td>
                        <input id="sales-storageid-orderGoodsAddPage" name="orderGoods.storageid" value="${defaultStorageid }"/>
                    </td>
                    <td class="len80 left">备&nbsp;&nbsp;注：</td>
                    <td colspan="3"><input id="sales-remark-orderDetailAddPage" type="text" name="orderGoods.remark" style="width:415px;" onfocus="frm_focus('orderGoods.remark');" onblur="frm_blur('orderGoods.remark');" /></td>
                </tr>
            </table>
        </div>
        <div data-options="region:'center',border:false">
            <input type="hidden" name="goodsjson" id="sales-goodsJson-orderGoodsAddPage" />
            <input id="sales-saveaudit-orderGoodsDetailAddPage" type="hidden" name="saveaudit" value="save"/>
            <table id="sales-datagrid-orderGoodsAddPage"></table>
        </div>
    </form>
</div>
<div class="easyui-menu" id="sales-contextMenu-orderGoodsAddPage" style="display: none;">
    <div id="sales-addRow-orderGoodsAddPage" data-options="iconCls:'button-add'">添加</div>
    <div id="sales-insertRow-orderGoodsAddPage" data-options="iconCls:'button-add'">插入</div>
    <div id="sales-editRow-orderGoodsAddPage" data-options="iconCls:'button-edit'">修改</div>
    <div id="sales-removeRow-orderGoodsAddPage" data-options="iconCls:'button-delete'">删除</div>
</div>
<div id="sales-dialog-orderGoodsAddPage" class="easyui-dialog" data-options="closed:true"></div>
<div id="sales-dialog-orderGoodsPromotion-ptype"></div>
<script type="text/javascript">

    var isadd = '${isadd}';

    $("#sales-buttons-orderGoodsPage").buttonWidget("disableButton",'order-oweorder-button');
    //是否允许修改销售部门客户业务员
    var salesReadonly = true;
    <security:authorize url="/sales/orderEditSalesuserAndSalesdept.do">
    salesReadonly = false;
    </security:authorize>
    $(function(){

        $("#sales-datagrid-orderGoodsAddPage").datagrid({ //销售商品明细信息编辑
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
            onRowContextMenu: function(e, rowIndex, rowData){
                e.preventDefault();
                $wareList.datagrid('selectRow', rowIndex);
                $("#sales-contextMenu-orderGoodsAddPage").menu('show', {
                    left:e.pageX,
                    top:e.pageY
                });
            },
            onSortColumn:function(sort, order){
                var rows = $("#sales-datagrid-orderGoodsAddPage").datagrid("getRows");
                var dataArr = [];
                for(var i=0;i<rows.length;i++){
                    if(rows[i].goodsid!=null && rows[i].goodsid!=""){
                        dataArr.push(rows[i]);
                    }
                }
                dataArr.sort(function(a,b){
                    if(order=="asc"){
                        return a[sort]>b[sort]?1:-1
                    }else{
                        return a[sort]<b[sort]?1:-1
                    }
                });
                $("#sales-datagrid-orderGoodsAddPage").datagrid("loadData",dataArr);
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
                countTotal();
            },
            onCheckAll:function(){
                countTotal();
            },
            onUncheckAll:function(){
                countTotal();
            },
            onCheck:function(){
                countTotal();
            },
            onUncheck:function(){
                countTotal();
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

                    }
                    else{
                        if(rowData.groupid != null && rowData.groupid!=""){
                            showPromotionEditPage(rowData);
                        }else{
                            beginEditDetail(rowData);
                        }
                    }
                }
            }
        }).datagrid("loadData", {'total':12,'rows':[{},{},{},{},{},{},{},{},{},{},{},{} ],'footer':[{goodsid:'合计'}]}).datagrid('columnMoving');
        $("#sales-form-orderGoodsAddPage").form({
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
                    var saveaudit = $("#sales-saveaudit-orderGoodsDetailAddPage").val();
                    $("#sales-backid-orderGoodsPage").val(json.backid);
                    if(saveaudit=="saveaudit"){
                        if(json.auditflag){
                            $.messager.alert("提醒","保存审核成功");
                            if(json.type == "add"){
                                $("#sales-buttons-orderGoodsPage").buttonWidget("addNewDataId", json.backid);
                            }
                            $("#sales-panel-orderGoodsPage").panel('refresh', 'sales/orderGoodsAddPage.do');
                        }else{
                            $.messager.alert("提醒","保存成功,审核失败。"+json.msg);
                            $("#sales-panel-orderGoodsPage").panel('refresh', 'sales/orderGoodsEditPage.do?id='+json.backid);
                        }
                    }else{
                        $.messager.alert("提醒","保存成功！");
                        $("#sales-panel-orderGoodsPage").panel('refresh', 'sales/orderGoodsEditPage.do?id='+json.backid);

                    }
                }
                else{
                    $.messager.alert("提醒","保存失败");
                }
                chooseGoods="";
            }
        });


        $("#sales-customer-orderGoodsAddPage").customerWidget({ //客户参照窗口
            name:'t_sales_order',
            col:'customerid',
            required:true,
            isopen:true,
            onSelect:function(data){
                var html = "编号："+'<a href="javascript:showCustomer(\''+data.id+'\')">'+data.id+'</a>';
                $("#sales-customer-showid-orderGoodsAddPage").html(html);
                $("#sales-customer-orderGoodsAddPage").customerWidget("setValue",data.id);
                $("#sales-salesMan-orderGoodsAddPage").widget("setValue",data.salesuserid);
                if(data.salesdeptid!=null && data.salesdeptid!=""){
                    $("#sales-salesDept-orderGoodsAddPage").widget("setValue",data.salesdeptid);
                }else{
                    $("#sales-salesDept-orderGoodsAddPage").widget("clear");
                }
                //客户变更后 更新明细价格数据
                changeGoodsPrice();
                $("#sales-remark-orderDetailAddPage").focus();
                <c:if test="${isOrderStorageSelect=='1'}">
                $("#sales-storageid-orderGoodsAddPage").focus();
                </c:if>
            },
            onClear:function(){
                $("#sales-customer-showid-orderGoodsAddPage").text("");
            }
        });
        $("#sales-storageid-orderGoodsAddPage").widget({
            referwid:'RL_T_BASE_STORAGE_INFO',
            width:150,
            <c:if test="${isOrderStorageSelect=='1'}">
            required:true,
            </c:if>
            singleSelect:true,
            onSelect:function(){
                $("#sales-remark-orderDetailAddPage").focus();
            }
        });
        $("#sales-salesDept-orderGoodsAddPage").widget({
            referwid:'RL_T_BASE_DEPARTMENT_SELLER',
            width:150,
            readonly:salesReadonly,
            singleSelect:true
        });
        $("#sales-salesMan-orderGoodsAddPage").widget({
            referwid:'RL_T_BASE_PERSONNEL_SELLER',
            width:150,
            readonly:salesReadonly,
            singleSelect:true,
            required:true
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
        $("#sales-addRow-orderGoodsAddPage").click(function(){
            beginAddDetail(false);
        });
        $("#sales-insertRow-orderGoodsAddPage").click(function(){
            beginAddDetail(true);
        });
        //批量添加
        $("#sales-addDetailByBrandAndSort-orderGoodsAddPage").click(function(){
            beginAddDetailByBrandAndSort();
        });
        $("#sales-history-price-orderGoodsAddPage").click(function(){
            showHistoryGoodsPrice();
        });
        //商品合同价修改
        $("#sales-setContractPrice-orderGoodsAddPage").click(function(){
            modifyGoodsContractPrice();
        });

        $("#sales-editRow-orderGoodsAddPage").click(function(){
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

            }
            else{
                beginEditDetail(row);
            }
        });
        $("#sales-removeRow-orderGoodsAddPage").click(function(){
            removeDetail();
        });
        $("#sales-buttons-orderGoodsPage").buttonWidget("initButtonType", 'add');
        $("#button-invalid").linkbutton("disable");
        $("#button-uninvalid").linkbutton("disable");

    });

    //根据客户编号显示客户详情
    function showCustomer(customerId){
        $('<div id="sales-dialog-customer"></div>').appendTo('#sales-dialog-orderGoodsPage');
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

    var $wareList = $("#sales-datagrid-orderGoodsAddPage"); //商品datagrid的div对象
    $("#sales-buttons-orderGoodsPage").buttonWidget("disableButton", 'button-deploy');

    $("#sales-buttons-orderGoodsPage").buttonWidget("disableMenuItem","button-printview-orderblank");
    $("#sales-buttons-orderGoodsPage").buttonWidget("disableMenuItem","button-print-orderblank");
    $("#sales-buttons-orderGoodsPage").buttonWidget("disableMenuItem","button-printview-DispatchBill");
    $("#sales-buttons-orderGoodsPage").buttonWidget("disableMenuItem","button-print-DispatchBill");
    $("#sales-buttons-orderGoodsPage").buttonWidget("disableMenuItem","button-printview-DeliveryOrder");
    $("#sales-buttons-orderGoodsPage").buttonWidget("disableMenuItem","button-print-DeliveryOrder");
</script>
</body>
</html>
