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
    <form id="sales-form-orderGoodsAddPage" action="sales/updateOrderGoods.do" method="post">
        <input type="hidden" id="sales-addType-orderGoodsAddPage" name="addType" />
        <input type="hidden" name="orderGoods.oldid" value="${orderGoods.id }" />
        <div data-options="region:'north',border:false" style="height:140px;">
            <table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
                <tr>
                    <td class="len80 left">编&nbsp;&nbsp;号：</td>
                    <td class="len165"><input type="text" id="sales-id-orderGoodsAddPage" class="len150" readonly="readonly" name="orderGoods.id" value="${orderGoods.id }" /></td>
                    <td class="len80 left">业务日期：</td>
                    <td class="len165"><input id="sales-businessdate-orderGoodsAddPage" type="text" class="len130 Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" name="orderGoods.businessdate" value="${orderGoods.businessdate }" /></td>
                    <td class="len80 left">状&nbsp;&nbsp;态：</td>
                    <td class="len165">
                        <select id="sales-customer-status" disabled="disabled" class="len136">
                            <c:forEach items="${statusList }" var="list">
                                <c:choose>
                                    <c:when test="${list.code == orderGoods.status}">
                                        <option value="${list.code }" selected="selected">${list.codename }</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${list.code }">${list.codename }</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="orderGoods.status" value="${orderGoods.status }" />
                    </td>
                </tr>
                <tr>
                    <td class="len80 left">客&nbsp;&nbsp;户：</td>
                    <td colspan="3">
                        <input type="text" id="sales-customer-orderGoodsAddPage" name="orderGoods.customerid" text="<c:out value="${orderGoods.customername }"></c:out>" value="${orderGoods.customerid }" style="width: 300px;"/>
                            <span id="sales-customer-showid-orderGoodsAddPage" style="margin-left:5px;line-height:25px;">
                                编号：<a href="javascript:showCustomer('${orderGoods.customerid }')">${orderGoods.customerid }</a></span>
                        <input type="hidden" id="sales-customer-hidden-orderGoodsAddPage" value="${orderGoods.customerid }"/>
                    </td>
                    <%--<td>来源类型：</td>--%>
                    <%--<td class="len165">--%>
                        <%--<select name="sourcetype" disabled="disabled" style="width: 136px;">--%>
                            <%--<option value="0"<c:if test="${orderGoods.sourcetype=='0'}">selected="selected"</c:if>>普通订单</option>--%>
                            <%--<option value="1"<c:if test="${orderGoods.sourcetype=='1'}">selected="selected"</c:if>>手机订单</option>--%>
                            <%--<option value="2"<c:if test="${orderGoods.sourcetype=='02'}">selected="selected"</c:if>>零售车销</option>--%>
                        <%--</select>--%>
                    <%--</td>--%>
                </tr>
                <tr>
                    <td class="len80 left">销售部门：</td>
                    <td>
                        <input id="sales-salesDept-orderGoodsAddPage" type="text" class="len136" name="orderGoods.salesdept" value="${orderGoods.salesdept }" />
                    </td>
                    <td class="len80 left">客户业务员：</td>
                    <td>
                        <input id="sales-salesMan-orderGoodsAddPage" type="text" class="len136" name="orderGoods.salesuser" value="${orderGoods.salesuser }" />
                    </td>
                    <td class="len80 left">提货券编号：</td>
                    <td>
                        <input type="text" id="sales-ladingbill-orderGoodsAddPage" class="len136" value="${orderGoods.ladingbill}" name="orderGoods.ladingbill"/>
                    </td>
                </tr>
                <tr>
                    <td class="len80 left">发货仓库：</td>
                    <td>
                        <input type="text" id="sales-storageid-orderGoodsAddPage" name="orderGoods.storageid" value="${orderGoods.storageid }"/>
                        <input type="hidden" id="sales-storageid-orderGoodsAddPage-old" value="${orderGoods.storageid }"/>
                    </td>
                    <td class="len80 left">备&nbsp;&nbsp;注：</td>
                    <td colspan="3"><input id="sales-remark-orderDetailAddPage" type="text" name="orderGoods.remark" value="<c:out value="${orderGoods.remark }"></c:out>" style="width:402px;" onfocus="frm_focus('orderGoods.remark');" onblur="frm_blur('orderGoods.remark');" /></td>
                </tr>
            </table>
            <input type="hidden" id="sales-printtimes-orderGoodsAddPage" value="${orderGoods.printtimes }"/>
            <input type="hidden" id="sales-phprinttimes-orderGoodsAddPage" value="${orderGoods.phprinttimes }"/>
            <input type="hidden" id="sales-printlimit-orderGoodsAddPage" value="${printlimit }"/>
            <input type="hidden" id="sales-fHPrintAfterSaleOutAudit-orderGoodsAddPage" value="${fHPrintAfterSaleOutAudit }"/>
        </div>
        <div data-options="region:'center',border:false">
            <input type="hidden" name="goodsjson" id="sales-goodsJson-orderGoodsAddPage"/>
            <input type="hidden" name="lackGoodsjson" id="sales-lackGoodsjson-orderGoodsAddPage" value="<c:out value="${laskJson }"/>"/>
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

    <c:if test="${orderGoods.status=='5'}">
    $("#storage-buttons-allocateOutPage").buttonWidget("disableButton","button-addorder-order");
    </c:if>



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
            toolbar:'#sales-toolbar-orderGoodsAddPage',
            data: JSON.parse('${goodsJson}'),
            onSortColumn:function(sort, order){
                var rows = $("#sales-datagrid-orderGoodsAddPage").datagrid("getRows");
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
//                groupGoods();
                countTotal();
            },
            onCheckAll:function(){
                countTotal();
            },
            onCheck:function(){
                countTotal();
            },
            onUncheck:function(){
                countTotal();
            },
            onRowContextMenu: function(e, rowIndex, rowData){
                e.preventDefault();
                $wareList.datagrid('selectRow', rowIndex);
                $("#sales-contextMenu-orderGoodsAddPage").menu('show', {
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
                    if(saveaudit=="saveaudit"){
                        if(json.auditflag == true){
                            $.messager.alert("提醒","保存审核成功");
                            $("#sales-customer-status").val("3");
                            $("#sales-buttons-orderGoodsPage").buttonWidget("setDataID", {id:json.backid, state:'3', type:'view'});

                            $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem","button-printview-orderblank");
                            $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem","button-print-orderblank");
                            $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem","button-printview-DispatchBill");
                            $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem","button-print-DispatchBill");
                            $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem","button-printview-DeliveryOrder");
                            $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem","button-print-DeliveryOrder");
                            $("#sales-panel-orderGoodsPage").panel('refresh', 'sales/orderGoodsViewPage.do?id='+json.backid);
                        }else{
                            $.messager.alert("提醒","保存成功,审核失败。"+json.msg);
                        }
                    }else{
                        $.messager.alert("提醒","保存成功。"+json.msg);
                    }

                    var oldstorageid = $("#sales-storageid-orderGoodsAddPage-old").val();
                    var newstorageid = $("#sales-storageid-orderGoodsAddPage").widget("getValue");
                    $("#sales-backid-orderGoodsPage").val(json.backid);
                    if(oldstorageid!=newstorageid){
                        $("#sales-panel-orderGoodsPage").panel('refresh', 'sales/orderGoodsEditPage.do?id='+json.backid);//'sales/orderViewPage.do?id='+ json.backid);
                    }
                }else{
                    $.messager.alert("提醒","保存失败。"+json.msg);
                }
                chooseGoods="";
            }
        });
        $("#sales-customer-orderGoodsAddPage").customerWidget({ //客户参照窗口
            name:'t_sales_order',
            col:'customerid',
            width:300,
            isopen:true,
            required:true,
            onSelect:function(data){
                var html = '编号：<a href="javascript:showCustomer(\''+data.id+'\')">'+data.id+'</a>';
                $("#sales-customer-showid-orderGoodsAddPage").html(html);
                $("#sales-salesMan-orderGoodsAddPage").widget("setValue",data.salesuserid);
                if(data.salesdeptid!=null && data.salesdeptid!=""){
                    $("#sales-salesDept-orderGoodsAddPage").widget("setValue",data.salesdeptid);
                }else{
                    $("#sales-salesDept-orderGoodsAddPage").widget("clear");
                }
                //客户变更后 更新明细价格数据
                changeGoodsPrice();
            },
            onClear:function(){
                $("#sales-customer-showid-orderGoodsAddPage").text("");
            }

        });
        $("#sales-storageid-orderGoodsAddPage").widget({
            referwid:'RL_T_BASE_STORAGE_INFO',
            width:130,
            <c:if test="${isOrderStorageSelect=='1'}">
            required:true,
            </c:if>
            singleSelect:true,
            onSelect:function(data){
                $("#sales-remark-orderDetailAddPage").focus();
                var rows = $("#sales-datagrid-orderGoodsAddPage").datagrid('getRows');
                var count = 0;
                var showBatchMsg = false;
                for(var i=0;i<rows.length; i++){
                    var row = rows[i];
                    if((rows[i].goodsid!=null && rows[i].goodsid!='') || rows[i].isdiscount!=null){
                        var rowIndex = $wareList.datagrid("getRowIndex", row);
                        if(row.summarybatchid==null || row.summarybatchid==""){
                            row.storageid = data.id;
                            row.storagename = data.name;
                            $("#sales-datagrid-orderGoodsAddPage").datagrid('updateRow', {index: rowIndex, row: row});
                        }else{
                            if(row.storageid!=data.id){
                                showBatchMsg = true;
                                row.summarybatchid="";
                                row.batchno="";
                                row.produceddate="";
                                row.deadline="";
                                row.storageid = data.id;
                                row.storagename = data.name;
                                $("#sales-datagrid-orderGoodsAddPage").datagrid('updateRow', {index: rowIndex, row: row});
                            }
                        }

                    }
                }
                if(showBatchMsg){
                    $.messager.alert("提醒","商品指定批次的仓库与发货仓库不一致，自动清除批次信息。");
                }
            },
            onClear:function(){
                var rows = $("#sales-datagrid-orderGoodsAddPage").datagrid('getRows');
                var count = 0;
                for(var i=0;i<rows.length; i++){
                    var row = rows[i];
                    if((rows[i].goodsid!=null && rows[i].goodsid!='') || rows[i].isdiscount!=null) {
                        var rowIndex = $wareList.datagrid("getRowIndex", row);
                        row.storageid = "";
                        row.storagename = "";
                        if(row.summarybatchid==null || row.summarybatchid=="") {
                            $("#sales-datagrid-orderGoodsAddPage").datagrid('updateRow', {index: rowIndex, row: row});
                        }
                    }
                }
            }
        });
        $("#sales-salesDept-orderGoodsAddPage").widget({
            name:'t_sales_order',
            col:'salesdept',
            width:130,
            readonly:salesReadonly,
            singleSelect:true
        });
        $("#sales-salesMan-orderGoodsAddPage").widget({
            name:'t_sales_order',
            col:'salesuser',
            width:130,
            readonly:salesReadonly,
            singleSelect:true,
            required:true
        });
        //批量添加
        $("#sales-addDetailByBrandAndSort-orderGoodsAddPage").click(function(){
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
        $("#sales-addRow-orderGoodsAddPage").click(function(){
            beginAddDetail(false);
        });
        $("#sales-insertRow-orderGoodsAddPage").click(function(){
            beginAddDetail(true);
        });
        //查看商品历史销售价
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

            }else{
                beginEditDetail(row);
            }
        });
        $("#sales-removeRow-orderGoodsAddPage").click(function(){
            removeDetail();
        });
        $("#sales-buttons-orderGoodsPage").buttonWidget("setDataID", {id:'${orderGoods.id}', state:'${orderGoods.status}', type:'edit'});
        if("${orderGoods.status}" == "2"){
            $("#button-invalid").linkbutton("enable");
            $("#button-uninvalid").linkbutton("disable");
            $("#sales-buttons-orderGoodsPage").buttonWidget("disableButton", 'button-addorder-order');
        }
        if("${orderGoods.status}" == "5"){
            $("#button-invalid").linkbutton("disable");
            $("#button-uninvalid").linkbutton("enable");
        }


    });

    var $wareList = $("#sales-datagrid-orderGoodsAddPage"); //商品datagrid的div对象
    $("#sales-buttons-orderGoodsPage").buttonWidget("enableButton", 'button-deploy');

    $("#sales-buttons-orderGoodsPage").buttonWidget("disableMenuItem","button-printview-orderblank");
    $("#sales-buttons-orderGoodsPage").buttonWidget("disableMenuItem","button-print-orderblank");
    $("#sales-buttons-orderGoodsPage").buttonWidget("disableMenuItem","button-printview-DispatchBill");
    $("#sales-buttons-orderGoodsPage").buttonWidget("disableMenuItem","button-print-DispatchBill");
    $("#sales-buttons-orderGoodsPage").buttonWidget("disableMenuItem","button-printview-DeliveryOrder");
    $("#sales-buttons-orderGoodsPage").buttonWidget("disableMenuItem","button-print-DeliveryOrder");
    <c:if test="${(orderGoods.status=='3' or orderGoods.status=='4') and (orderGoods.phprinttimes =='0'  or '0'==printlimit) }">
    $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem","button-print-orderblank");
    </c:if>
    <c:choose>
    <c:when test="${fHPrintAfterSaleOutAudit=='1' }">
    <c:if test="${orderGoods.status=='4' and (orderGoods.printtimes == '0' or '0'==printlimit) }">
    $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem","button-print-DeliveryOrder");
    $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem","button-print-DispatchBill");
    </c:if>
    </c:when>
    <c:otherwise>
    <c:if test="${(orderGoods.status=='3' or orderGoods.status=='4') and (orderGoods.printtimes == '0' or '0'==printlimit) }">
    $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem","button-print-DeliveryOrder");
    $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem","button-print-DispatchBill");
    </c:if>
    </c:otherwise>
    </c:choose>
    <c:if test="${(orderGoods.status=='3' or orderGoods.status=='4') }">
    $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem","button-printview-DeliveryOrder");
    $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem","button-printview-DispatchBill");
    $("#sales-buttons-orderGoodsPage").buttonWidget("enableMenuItem","button-printview-orderblank");
    $("#sales-buttons-orderGoodsPage").buttonWidget("enableButton", 'button-addorder-order');
    </c:if>
    <c:if test="${orderGoods.status=='3' }">
    $("#sales-buttons-orderGoodsPage").buttonWidget("enableButton","button-invalid");
    $("#sales-buttons-orderGoodsPage").buttonWidget("disableButton","button-uninvalid");
    </c:if>
    <c:if test="${orderGoods.status=='4' }">
    $("#sales-buttons-orderGoodsPage").buttonWidget("disableButton","button-invalid");
    $("#sales-buttons-orderGoodsPage").buttonWidget("enableButton","button-uninvalid");
    </c:if>
</script>
</body>
</html>
