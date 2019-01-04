<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售订货单列表页面</title>
    <%@include file="/include.jsp" %>

</head>
<body>
<div id="sales-demandAddOrderGoodsPage-layout" class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table id="sales-datagrid-demandAddOrderGoodsPage" data-options="border:false"></table>
    </div>
</div>


<script type="text/javascript">
    function isLockData(id, tname) {
        var flag = false;
        $.ajax({
            url: 'system/lock/unLockData.do',
            type: 'post',
            data: {id: id, tname: tname},
            dataType: 'json',
            async: false,
            success: function (json) {
                flag = json.flag
            }
        });
        return flag;
    }

    var initQueryJSON = $("#sales-queryForm-demandAddOrderGoodsPage").serializeJSON();
    $(function () {
        $("#sales-datagrid-demandAddOrderGoodsPage").datagrid({
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            sortOrder: 'desc',
            url: 'sales/getDemandAddOrderGoodsData.do?id=${param.id}',
            <%--data: JSON.parse('${goodsJson}'),--%>
            queryParams: initQueryJSON,
            frozenColumns:[[{field: 'id', title: '明细编号', hidden: true}]],
            columns:[[
                {field: 'ck', checkbox: true},
                {field: 'id', title: '编号', width: 145, align: 'left', sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, align: 'left', sortable: true},
                {field: 'customerid', title: '客户编码', width: 60, align: 'left', sortable: true},
                {field: 'customername', title: '客户名称', width: 150, align: 'left', isShow: true},
                {field: 'handlerid', title: '对方经手人', width: 80, align: 'left'},
                {field: 'salesdept', title: '销售部门', width: 100, align: 'left', sortable: true},
                {field: 'salesuser', title: '客户业务员', width: 80, align: 'left', sortable: true},
                {
                    field: 'field01', title: '金额', width: 80, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'field02', title: '未税金额', width: 80, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'field03', title: '税额', width: 80, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'status', title: '状态', width: 60, align: 'left', sortable: true,
                    formatter: function (value, row, index) {
                        return row.statusname;
                    }
                },
                {
                    field: 'indooruserid', title: '销售内勤', width: 80, sortable: true,
                    formatter: function (value, rowData, index) {
                        return rowData.indoorusername;
                    }
                },
                {field: 'addusername', title: '制单人', width: 60, sortable: true},
                {field: 'addtime', title: '制单时间', width: 130, sortable: true},
                {field: 'auditusername', title: '审核人', width: 80, sortable: true, hidden: true},
                {field: 'audittime', title: '审核时间', width: 80, sortable: true, hidden: true},
                {field: 'modifyusername', title: '修改人', width: 60, sortable: true, hidden: true},
                {field: 'modifytime', title: '修改时间', width: 130, sortable: true, hidden: true},
                {field: 'remark', title: '备注', width: 100}
            ]]
        });
    });

    function addOrder(){
        var row=$("#sales-datagrid-demandAddOrderGoodsPage").datagrid("getChecked");
        if(row.length==0){
            $.messager.alert("提醒", "请选择要关联的订货单");
            return ;
        }
        var ordergoodsids="";
        for(var i=0;i<row.length;i++){
            var flag = isLockData(row[i].id, "t_sales_goodsorder");
            if (!flag) {
                $.messager.alert("警告", "订货单"+row[i].id+"正在被其他人操作，暂不能修改！");
                $("#sales-datagrid-demandListPage").datagrid('reload');
                return false;
            }
            if(ordergoodsids==""){
                ordergoodsids=row[i].id;
            }else{
                ordergoodsids+=","+row[i].id;
            }
        }

        $.messager.confirm("提醒", "确定生成订单信息？", function(r){
            if(r){
                var ids = "${demand.id}";
                var customerid = "${demand.customerid}";
                loading("订单生成中...");
                $.ajax({
                    url:'sales/canAuditDemand.do',
                    dataType:'json',
                    type:'post',
                    data:{customerId:customerid,ids:ids},
                    success:function(data){
                        if(data.flag == true){
                            loading("订单生成中...");
                            $.ajax({
                                url:'sales/addOrderByDemandAndOrderGoods.do',
                                dataType:'json',
                                type:'post',
                                data:{
                                    id:ids,
                                    ordergoodsid:ordergoodsids,
                                    ordercustomerid:row[0].customerid
                                },
                                success:function(json){
                                    loaded();
                                    if(json.result == "no_customer"){
                                        $.messager.alert("提醒", "不能关联不同客户的订货单");
                                    }else if(json.result == "no_goodsnum"){
                                        $.messager.alert("提醒", "订货单商品数量不符合要货单商品数量");
                                    }else if(json.result == "canot"){
                                        $.messager.alert("提醒", "生成订单失败：非同一客户不可合并生成订单");
                                    }
                                    else if(json.result != null && json.result != "null" && json.result != "canot"){
                                        $.messager.alert("提醒", "生成订单成功，订单号："+ json.result);
                                        $("#sales-datagrid-demandListPage").datagrid('reload');
                                        $('#sales-dialog-addDemandByOrderGoods-content').dialog("destroy");
                                    }
                                    else{
                                        $.messager.alert("提醒", "生成订单失败");
                                    }
                                }
                            });
                        }
                        else{
                            loaded();
                            $.messager.confirm("提醒", data.msg+ "是否生成订单？", function(r){
                                if(r){
                                    loading("订单生成中...");
                                    $.ajax({
                                        url:'sales/addOrderByDemandAndOrderGoods.do',
                                        dataType:'json',
                                        type:'post',
                                        data:{
                                            id:ids,
                                            ordergoodsid:ordergoodsids,
                                            ordercustomerid:row[0].customerid
                                        },
                                        success:function(json){
                                            loaded();
                                            if(json.result == "no_customer"){
                                                $.messager.alert("提醒", "不能关联不同客户的订货单");

                                            }else if(json.result == "no_goodsnum"){
                                                $.messager.alert("提醒", "订货单商品数量不符合要货单商品数量");
                                            }else if(json.result == "canot"){
                                                $.messager.alert("提醒", "生成订单失败：非同一客户不可合并生成订单");
                                            }
                                            else if(json.result != null && json.result != "null" && json.result != "canot"){
                                                $.messager.alert("提醒", "生成订单成功，订单号："+ json.result);
                                                $("#sales-datagrid-demandListPage").datagrid('reload');
                                                $('#sales-dialog-addDemandByOrderGoods-content').dialog("destroy");
                                            }
                                            else{
                                                $.messager.alert("提醒", "生成订单失败");
                                            }
                                        }
                                    });
                                }
                                else{
                                    loaded();
                                    return ;
                                }
                            });
                        }
                    }
                });
            }
        });
    }
</script>
</body>
</html>
