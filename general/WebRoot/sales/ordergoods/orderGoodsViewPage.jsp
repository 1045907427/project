<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售订单查看页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <form id="sales-form-orderGoodsAddPage" action="" method="post">
        <div data-options="region:'north',border:false" style="height:140px;">
            <table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
                <tr>
                    <td class="len80 left">编号：</td>
                    <td class="len165"><input id="sales-id-orderGoodsAddPage" type="text" class="len150" readonly="readonly" value="${orderGoods.id }" /></td>
                    <td class="len80 left">业务日期：</td>
                    <td class="len165"><input id="sales-businessdate-orderGoodsAddPage" type="text" class="len130" readonly="readonly" value="${orderGoods.businessdate }" /></td>
                    <td class="len80 left">状态：</td>
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
                    </td>
                </tr>
                <tr>
                    <td class="len80 left">客户：</td>
                    <td colspan="3"><input type="text" id="sales-customer-orderGoodsAddPage" readonly="readonly" text="<c:out value="${orderGoods.customername }"></c:out>" value="${orderGoods.customerid }" style="width:300px;" />
                            <span id="sales-customer-showid-orderGoodsAddPage" style="margin-left:5px;line-height:25px;">
                                  编号：<a href="javascript:showCustomer('${orderGoods.customerid }')">${orderGoods.customerid }</a></span>
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
                        <input id="sales-salesDept-orderGoodsAddPage" type="text" class="len136" name="orderGoods.salesdept" value="${orderGoods.salesdept }" readonly="readonly"/>
                    </td>
                    <td class="len80 left">客户业务员：</td>
                    <td>
                        <input id="sales-salesMan-orderGoodsAddPage" type="text" class="len136" name="orderGoods.salesuser" value="${orderGoods.salesuser }" readonly="readonly"/>
                    </td>
                    <td class="len80 left">提货券编号：</td>
                    <td>
                        <input type="text" id="sales-ladingbill-orderGoodsAddPage" class="len136" readonly="readonly" value="${orderGoods.ladingbill}" name="orderGoods.ladingbill"/>
                    </td>
                </tr>
                <tr>
                    <td class="len80 left">发货仓库：</td>
                    <td>
                        <input id="sales-storageid-orderGoodsAddPage" name="orderGoods.storageid" value="${orderGoods.storageid }"/>
                    </td>
                    <td class="len80 left">备注：</td>
                    <td colspan="3"><input type="text" readonly="readonly" value="<c:out value="${orderGoods.remark }"></c:out>" style="width:400px;" /></td>
                </tr>
            </table>
            <input type="hidden" id="sales-printtimes-orderGoodsAddPage" value="${orderGoods.printtimes }"/>
            <input type="hidden" id="sales-phprinttimes-orderGoodsAddPage" value="${orderGoods.phprinttimes }"/>
            <input type="hidden" id="sales-printlimit-orderGoodsAddPage" value="${printlimit }"/>
        </div>
        <div data-options="region:'center',border:false">
            <table id="sales-datagrid-orderGoodsAddPage"></table>
        </div>
    </form>
</div>
<script type="text/javascript">
    <c:if test="${orderGoods.status=='5'}">
    $("#storage-buttons-allocateOutPage").buttonWidget("disableButton","button-addorder-order");
    </c:if>
    $("#sales-buttons-orderGoodsPage").buttonWidget("enableButton",'order-oweorder-button');
    $(function(){
        $("#sales-datagrid-orderGoodsAddPage").datagrid({ //销售商品行编辑
            authority:tableColJson,
            columns: tableColJson.common,
            frozenColumns: tableColJson.frozen,
            border: true,
            fit:true,
            rownumbers: true,
            showFooter: true,
            striped:true,
            singleSelect: false,
            checkOnSelect:true,
            selectOnCheck:true,
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
            onUncheckAll:function(){
                countTotal();
            },
            onCheck:function(){
                countTotal();
            },
            onUncheck:function(){
                countTotal();
            }
        }).datagrid('columnMoving');
        $("#sales-customer-orderGoodsAddPage").customerWidget({ //客户参照窗口
            name:'t_sales_order',
            col:'customerid',
            singleSelect:true,
            width:300
        });
        $("#sales-storageid-orderGoodsAddPage").widget({
            referwid:'RL_T_BASE_STORAGE_INFO',
            width:130,
            singleSelect:true,
            readonly:true
        });
        $("#sales-salesDept-orderGoodsAddPage").widget({
            name:'t_sales_order',
            col:'salesdept',
            width:130,
            singleSelect:true
        });
        $("#sales-salesMan-orderGoodsAddPage").widget({
            name:'t_sales_order',
            col:'salesuser',
            width:130,
            singleSelect:true
        });
        $("#sales-buttons-orderGoodsPage").buttonWidget("setDataID", {id:'${orderGoods.id}', state:'${orderGoods.status}', type:'view'});
        if("${orderGoods.status}" == "2"){
            $("#button-invalid").linkbutton("enable");
            $("#button-uninvalid").linkbutton("disable");
            $("#sales-buttons-orderGoodsPage").buttonWidget("enableButton", 'button-deploy');
        }
        else if("${orderGoods.status}" == "5"){
            $("#button-invalid").linkbutton("disable");
            $("#button-uninvalid").linkbutton("enable");
            $("#sales-buttons-orderGoodsPage").buttonWidget("disableButton", 'button-deploy');
        }
        else{
            $("#button-invalid").linkbutton("disable");
            $("#button-uninvalid").linkbutton("disable");
            $("#sales-buttons-orderGoodsPage").buttonWidget("disableButton", 'button-deploy');
        }

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
        $("#sales-buttons-orderGoodsPage").buttonWidget("enableButton","button-addorder-order");
        </c:if>

        <c:if test="${orderGoods.status!='2' }">
        $("#sales-buttons-orderGoodsPage").buttonWidget("disableButton", 'storage-deploy-button');
        </c:if>
        <c:if test="${orderGoods.status=='2' }">
        $("#sales-buttons-orderGoodsPage").buttonWidget("enableButton", 'storage-deploy-button');
        $("#sales-buttons-orderGoodsPage").buttonWidget("disableButton", 'button-addorder-order');
        </c:if>
        <c:if test="${orderGoods.status=='3' }">
        $("#sales-buttons-orderGoodsPage").buttonWidget("enableButton","button-invalid");
        $("#sales-buttons-orderGoodsPage").buttonWidget("disableButton","button-uninvalid");
        </c:if>
        <c:if test="${orderGoods.status=='4' }">
        $("#sales-buttons-orderGoodsPage").buttonWidget("disableButton","button-invalid");
        $("#sales-buttons-orderGoodsPage").buttonWidget("enableButton","button-uninvalid");
        </c:if>
    });
</script>
</body>
</html>
