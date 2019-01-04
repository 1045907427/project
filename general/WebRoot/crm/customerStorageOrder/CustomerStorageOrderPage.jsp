<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>客户库存页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<input type="hidden" id="crm-backid-customerStorageOrderPage" value="${id }" />
<div id="crm-orderPage-layout" class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="crm-buttons-customerStorageOrderPage"></div>
    </div>
    <div data-options="region:'center'">
        <div class="easyui-panel" id="crm-panel-customerStorageOrderPage" data-options="fit:true"></div>
    </div>
</div>
<script type="text/javascript">
    var order_url = "crm/customerStorageOrder/showCustomerStorageOrderAddPage.do";
    var order_type = '${type}';
    <%--if(order_type == "view" ){--%>
    <%--order_url = "crm/CustomerStorage/orderViewPage.do?id=${id}";--%>
    <%--}--%>
    if(order_type == "edit"){
        order_url = "crm/customerStorageOrder/showCustomerStorageEditPage.do?id=${id}";
    }
    var tableColJson = $("#crm-datagrid-customerStorageOrderPage").createGridColumnLoad({
        frozenCol : [[
            {field:'id',title:'明细编号',hidden:true}
        ]],
        commonCol : [[
            {field:'ck',checkbox:true},
            {field:'goodsid',title:'商品编码',width:70,align:' left',sortable:true},
            {field:'goodsname', title:'商品名称', width:250,align:'left',
                formatter:function(value,rowData,index){
                    if(rowData.goodsInfo != null){
                        return rowData.goodsInfo.name;
                    }else{
                        return value ;
                    }
                }
            },
            {field:'spell', title:'助记符',width:80,aliascol:'goodsid',hidden:true,align:'left',
                formatter:function(value,rowData,rowIndex){
                    if(rowData.goodsInfo != undefined){
                        return rowData.goodsInfo.spell;
                    }else{
                        return "";
                    }
                }
            },
//            {field:'shopid', title:'店内码',width:80,aliascol:'goodsid',hidden:true,align:'left'},
            {field:'barcode', title:'条形码',width:120,align:'left',aliascol:'goodsid',
                formatter:function(value,rowData,rowIndex){
                    if(rowData.goodsInfo != null){
                        return rowData.goodsInfo.barcode;
                    }else{
                        return "";
                    }
                }
            },
            {field:'brandName', title:'商品品牌',width:60,align:'left',aliascol:'goodsid',hidden:true,
                formatter:function(value,rowData,rowIndex){
                    if(rowData.goodsInfo != null){
                        return rowData.goodsInfo.brandName;
                    }else{
                        return "";
                    }
                }
            },
            {field:'boxnum', title:'箱装量',aliascol:'goodsid',width:45,align:'right',
                formatter:function(value,rowData,rowIndex){
                    if(rowData.goodsInfo != null){
                        return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
                    }else{
                        return "";
                    }
                }
            },
            {field:'unitname', title:'单位',width:35,align:'left'},
            {field:'unitnum', title:'数量',width:60,align:'right',sortable:true,
                formatter:function(value,row,index){
                    return formatterBigNumNoLen(value);
                }
            },
            {field:'auxnumdetail', title:'辅数量',width:60,align:'right'},

            {field:'totalboxweight', title:'重量（千克）',width:80,align:'right',hidden:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'totalboxvolume', title:'体积（立方米）',width:90,align:'right',hidden:true,
                formatter:function(value,row,index){
                    if(value != undefined){
                        return Number(value).toFixed(3);
                    }
                }
            },
            {field:'taxprice', title:'零售价',width:60,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'taxamount', title:'零售金额',width:60,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'notaxamount', title:'零售未税金额',width:90,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'costtaxamount', title:'成本金额',width:60,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'costnotaxamount', title:'成本未税金额',width:90,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'costprice', title:'成本单价',width:60,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'taxtype', title:'税种',width:60,align:'left',hidden:true,
                formatter:function(value,row,index){
                    return row.taxtypename;
                }
            },
            {field:'remark', title:'备注',width:200,align:'left'}
        ]]
    });
    //按钮
    $("#crm-buttons-customerStorageOrderPage").buttonWidget({
        initButton: [
            {},
            <security:authorize url="/crm/customerStorageOrder/addCustomerStorageOrder.do">
            {
                type: 'button-add',
                handler: function () {
                    refreshPanel('crm/customerStorageOrder/showCustomerStorageOrderPage.do');
                }
            },
            </security:authorize>
            <security:authorize url="/crm/customerStorageOrder/saveCustomerStorageOrder.do">
            {
                type: 'button-save',
                handler: function () {
                    var rows = $("#crm-datagrid-customerStorageOrderAddPage").datagrid('getRows');
                    $("#crm-goodsJson-customerStorageOrderAddPage").val(JSON.stringify(rows));
                    $("#crm-saveaudit-customerStorageOrderAddPage").val("saveaudit");
                    $("#crm-form-customerStorageOrderAddPage").submit();
                }
            },
            </security:authorize>
            <security:authorize url="/crm/customerStorageOrder/deleteCustomerStorageOrder.do">
            {
                type: 'button-delete',
                handler: function(){
                    $.messager.confirm("提醒","是否删除当前客户库存单据数据？",function(r){
                        if(r){
                            var id = $("#crm-backid-customerStorageOrderPage").val();
                            if(id!=""){
                                loading("删除中..");
                                $.ajax({
                                    url :'crm/customerStorageOrder/deleteCustomerStorageSalesOrder.do?id='+id,
                                    type:'post',
                                    dataType:'json',
                                    success:function(json){
                                        loaded();
                                        if(json.delFlag){
                                            $.messager.alert("提醒","该信息已被其他信息引用，无法删除");
                                            return false;
                                        }
                                        if(json.flag){
                                            $.messager.alert("提醒", "删除成功");
                                            var data = $("#crm-buttons-customerStorageOrderPage").buttonWidget("removeData", '');
                                            if(data != null){
                                                $("#crm-backid-customerStorageOrderPage").val(data.id);
                                                refreshPanel('crm/customerStorageOrder/showCustomerStorageEditPage.do?id='+ data.id);
                                            }else{
                                                parent.closeNowTab();
                                            }
                                            $("#crm-table-customerStorageOrderListPage").datagrid("reload");
                                        }else{
                                            $.messager.alert("提醒", "删除失败");
                                        }
                                    },
                                    error:function(){
                                        $.messager.alert("错误", "删除出错");
                                        loaded();
                                    }
                                });
                            }
                        }
                    });
                }
            },
            </security:authorize>
            <security:authorize url="/crm/customerStorageOrder/auditCustomerStorageOrder.do">
            {
                type: 'button-audit',
                handler: function () {
                    $.messager.confirm("提醒","审核后最新的商品信息将会生成客户库存记录,确定审核当前客户库存数据？",function(r){
                        if(r){
                            var id = $("#crm-backid-customerStorageOrderPage").val();
                            if(id!=""){
                                loading("审核中..");
                                $.ajax({
                                    url :'crm/customerStorageOrder/auditCustomerStorageSalesOrder.do?id='+id,
                                    type:'post',
                                    dataType:'json',
                                    success:function(json){
                                        loaded();
                                        if(json.flag){
                                            $.messager.alert("提醒", json.msg);
                                            refreshPanel('crm/customerStorageOrder/showCustomerStorageEditPage.do?id='+ id);
                                            $("#crm-table-customerStorageOrderListPage").datagrid("reload");
                                        }else{
                                            $.messager.alert("提醒", json.msg);
                                        }
                                    },
                                    error:function(){
                                        $.messager.alert("错误", "审核出错");
                                        loaded();
                                    }
                                });
                            }
                        }
                    });


                },
            },
            </security:authorize>
            <%--<security:authorize url="/crm/customerStorageOrder/oppAuditCustomerStorageOrder.do">--%>
            <%--{--%>
                <%--type: 'button-oppaudit',--%>
                <%--handler: function () {--%>
                    <%--$.messager.confirm("提醒","反审将清除该客户当前单据中所有商品的库存量，确定反审当前单据？",function(r){--%>
                        <%--if(r){--%>
                            <%--var id = $("#crm-backid-customerStorageOrderPage").val();--%>
                            <%--if(id!=""){--%>
                                <%--loading("反审中..");--%>
                                <%--$.ajax({--%>
                                    <%--url :'crm/customerStorageOrder/oppAuditCustomerStorageSalesOrder.do?id='+id,--%>
                                    <%--type:'post',--%>
                                    <%--dataType:'json',--%>
                                    <%--success:function(json){--%>
                                        <%--loaded();--%>
                                        <%--if(json.flag){--%>
                                            <%--$.messager.alert("提醒", "反审成功");--%>
                                            <%--refreshPanel('crm/customerStorageOrder/showCustomerStorageEditPage.do?id='+ json.id);--%>
                                            <%--$("#crm-table-customerStorageOrderListPage").datagrid("reload");--%>
                                        <%--}else{--%>
                                            <%--$.messager.alert("提醒", json.msg);--%>
                                        <%--}--%>
                                    <%--},--%>
                                    <%--error:function(){--%>
                                        <%--$.messager.alert("错误", "反审出错");--%>
                                        <%--loaded();--%>
                                    <%--}--%>
                                <%--});--%>
                            <%--}--%>
                        <%--}--%>
                    <%--});--%>
                <%--},--%>
            <%--},--%>
            <%--</security:authorize>--%>
            <security:authorize url="/crm/customerStorageOrder/backCustomerStorageOrder.do">
            {
                type: 'button-back',
                handler: function(data){
                    $("#crm-backid-customerStorageOrderPage").val(data.id);
                    refreshPanel('crm/customerStorageOrder/showCustomerStorageOrderPage.do?type=edit&id='+ data.id);
                }
            },
            </security:authorize>
            <security:authorize url="/crm/customerStorageOrder/nextCustomerStorageOrder.do">
            {
                type: 'button-next',
                handler: function(data){
                    $("#crm-backid-customerStorageOrderPage").val(data.id);
                    refreshPanel('crm/customerStorageOrder/showCustomerStorageOrderPage.do?type=edit&id='+ data.id);
                }
            },
            </security:authorize>
            {}
        ],
        layoutid:'crm-orderPage-layout',
        taburl:'/crm/customerStorageOrder/showCustomerStorageOrderListPage.do',
        model:'bill',
        type: 'view',
        id:'${id}',
        datagrid:'crm-table-customerStorageOrderListPage'
    });

    //回车跳到下一个
    var chooseNo;
    function frm_focus(val) {
        chooseNo = val;
    }
    function frm_blur(val) {
        if (val == chooseNo) {
            chooseNo = "";
        }
    }

    var leftAmount = 0;
    var receivableAmount = 0;
    function refreshPanel(url){ //更新panel
        //客户信息归零
        leftAmount = 0;
        receivableAmount = 0;
        $("#crm-panel-customerStorageOrderPage").panel('refresh', url);
    }

    function beginAddCustomerStorageDetail(){

        var customer = $("#crm-customer-customerStorageOrderAddPage").customerWidget("getValue");
        if(customer == ''){
            $("#crm-customer-customerStorageOrderPage").focus();
            $.messager.alert("提醒","请先选择客户再进行添加商品信息");
            return false;
        }
        $('<div id="crm-dialog-customerStorageOrderAddPage-content"></div>').appendTo('#crm-dialog-customerStorageOrderAddPage');
        $("#crm-dialog-customerStorageOrderAddPage-content").dialog({ //弹出新添加窗口
            title:'商品信息添加(按ESC退出)',
            maximizable:true,
            width:600,
            height:450,
            closed:false,
            modal:true,
            cache:false,
            resizable:true,
            href:'crm/customerStorageOrder/showCustStorageDetailAddPage.do',
            onClose:function(){
                $('#crm-dialog-customerStorageOrderAddPage-content').dialog("destroy");
            },
            onLoad:function(){
                $("#crm-goodsId-customerStorageDetailAddPage").focus();
            }
        });
    }
    //flag为true时 跳转到查看界面
    function beginEditCustomerStorageDetail(rowData,flag){
        var customer = $("#crm-customer-customerStorageOrderPage-hidden").val();
        if(customer == ''){
            $.messager.alert("提醒","请先选择客户再进行添加商品信息");
            $("#crm-customer-customerStorageOrderPage").focus();
            return false;
        }
        if(rowData == null){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        var row = rowData;
        row.goodsname = row.goodsInfo.name;
        row.brandName = row.goodsInfo.brandName;
        row.barcode = row.goodsInfo.barcode;
        var url = '';
        if(row.goodsid == undefined){
            beginAddCustomerStorageDetail();
        }else{
            url = 'crm/customerStorageOrder/showCustStorageDetailEditPage.do?orderid='+ rowData.id+'&goodsid='+row.goodsid; //如果是修改页面，数据直接来源于datagrid中的json数据。
            $('<div id="crm-dialog-customerStorageOrderAddPage-content"></div>').appendTo('#crm-dialog-customerStorageOrderAddPage');
            $("#crm-dialog-customerStorageOrderAddPage-content").dialog({ //弹出新添加窗口
                title:'商品信息修改(按ESC退出)',
                maximizable:true,
                width:600,
                height:450,
                closed:false,
                modal:true,
                cache:false,
                resizable:true,
                href:url,
                onClose:function(){
                    $('#crm-dialog-customerStorageOrderAddPage-content').dialog("destroy");
                },
                onLoad:function(){
                    $("#crm-form-customerStorageDetailAddPage").form('load', row);
                    $("#crm-unitname-customerStorageDetailAddPage").text(row.unitname);
                    $("#crm-auxunitname-customerStorageDetailAddPage").text(row.auxunitname);
                    $("#crm-goodsId-customerStorageDetailAddPage").goodsWidget("setValue", row.goodsid);
                    $("#crm-goodsId-customerStorageDetailAddPage").goodsWidget("setText", row.goodsInfo.name);
                    $("#crm-loading-customerStorageDetailAddPage").removeClass("img-loading").html
                    ("商品编码：<font color='green'>"+row.goodsid);
                    var boxprice = Number(row.taxprice) * Number(row.goodsInfo.boxnum);
                    $("#crm-boxprice-customerStorageDetailAddPage").val(boxprice);
                    $("#crm-boxnum-customerStorageDetailAddPage").val(row.goodsInfo.boxnum);
                    $("#crm-form-customerStorageDetailAddPage").form('validate');
                    if(flag){
                        $("input[name=unitnum]").attr("readonly",true);
                        $("#crm-goodsId-customerStorageDetailAddPage").goodsWidget("readonly",true);
                        $("#crm-goodsId-customerStorageDetailAddPage").goodsWidget("disable");
                    }else{
                        $("input[name=unitnum]").focus();
                    }
                    formaterNumSubZeroAndDot();
                    $("#crm-form-customerStorageDetailAddPage").form('validate');
                }
            });
        }
    }
    function deleteCustomerStorageDetail(){
        var row = $wareList.datagrid('getSelected');
        if(row == null){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $.messager.confirm("提醒","确定删除该商品明细?",function(r) {
            if (r) {
                var checkRows = $wareList.datagrid("getChecked");
                for(var i=0;i<checkRows.length;++i){
                    var rowIndex = $wareList.datagrid('getRowIndex',checkRows[i]);
                    $wareList.datagrid('deleteRow', rowIndex);
                }
                var rows = $wareList.datagrid("getRows");
                var data = new Array();
                for(var i=0; i<rows.length; i++){
                    if(rows[i].goodsid != undefined){
                        data.push(rows[i]);
                    }
                }
                $wareList.datagrid("loadData",data);

            }
        });
    }

    $(function(){
        $("#crm-panel-customerStorageOrderPage").panel({
            href:order_url,
            cache:false,
            maximized:true,
            border:false,
            onLoad:function(){
                $("#crm-customer-customerStorageOrderAddPage").focus();
            }
        });

        $(document).keydown(function (event) {//alert(event.keyCode);
            switch (event.keyCode) {
                case 13: //Enter
//                    if (chooseNo == "remark") {
//                        $("input[name='remark']").blur();
//                        beginAddCustomerStorageDetail();
//                    }
                    if (chooseNo == "unitnum") {
                        $("input[name=auxnum]").focus();
                        return false;
                    }
                    if (chooseNo == "auxnum") {
                        $("input[name=overnum]").focus();
                        return false;
                    }
                    if (chooseNo == "overnum") {
                        $("input[name=taxprice]").focus();
                        return false;
                    }
                    if (chooseNo == "taxprice") {
                        $("input[name=taxamount]").focus();
                        return false;
                    }
                    if (chooseNo == "taxamount") {
                        $("input[name=notaxprice]").focus();
                        return false;
                    }
                    if (chooseNo == "notaxprice") {
                        $("input[name=notaxamount]").focus();
                        return false;
                    }
                    if (chooseNo == "notaxamount") {
                        $("input[name=costprice]").focus();
                        return false;
                    }
                    if (chooseNo == "costprice") {
                        $("input[name=remark]").focus();
                        return false;
                    }
                    if (chooseNo == "remark") {
                        $("#crm-save-customerStorageDetailAddPage").click();
                        $("#crm-savegoon-customerStorageDetailAddPage").click();
                        return false;
                    }
                    break;
                case 27: //Esc
                    $("#crm-dialog-customerStorageOrderAddPage-content").dialog('close', true);
                    break;
                case 65: //a
                    if (event.altKey) {
                        $("#button-add").click();
                    }
                    break;
                case 83: //s
                    if (event.ctrlKey) {
                        $("#button-save").click();
                        return false;
                    }
                    break;
            }
        });

    });

    function countTotal(){ //计算合计
        var checkrows =  $("#crm-datagrid-customerStorageOrderAddPage").datagrid('getChecked');
        var unitnum = 0;
        var totalbox = 0;
        var totalboxweight = 0 ;
        var totalboxvolume = 0 ;
        for(var i=0; i<checkrows.length; i++){
            unitnum += Number(checkrows[i].unitnum == undefined ? 0 : checkrows[i].unitnum);
            totalboxweight += Number(checkrows[i].totalboxweight == undefined ? 0 : checkrows[i].totalboxweight);
            totalboxvolume += Number(checkrows[i].totalboxvolume == undefined ? 0 : checkrows[i].totalboxvolume);
            totalbox += Number(checkrows[i].totalbox == undefined ? 0 : checkrows[i].totalbox);
        }
        totalbox = formatterMoney(totalbox);
        totalboxweight = formatterMoney(totalboxweight);
        totalboxweight = formatterMoney(totalboxweight);
        var foot = [{/*goodsname:'余额:'+leftAmount,*/goodsid:'选中合计',unitnum:unitnum,auxnumdetail:totalbox+"箱",
            totalboxvolume:totalboxvolume,totalboxweight:totalboxweight}];
        //合计
        var rows =  $("#crm-datagrid-customerStorageOrderAddPage").datagrid('getRows');
        var unitnumSum = 0;
        var totalboxSum = 0;
        var totalboxweightSum = 0 ;
        var totalboxvolumeSum = 0 ;
        for(var i=0; i<rows.length; i++){
            unitnumSum += Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
            totalboxSum += Number(rows[i].totalbox == undefined ? 0 : rows[i].totalbox);
            totalboxweightSum += Number(rows[i].totalboxweight == undefined ? 0 : rows[i].totalboxweight);
            totalboxvolumeSum += Number(rows[i].totalboxvolume == undefined ? 0 : rows[i].totalboxvolume);
        }
        totalboxSum = formatterMoney(totalboxSum);
        var footSum = {goodsid:'合计',/*goodsname:"应收款:"+receivableAmount,*/unitnum:Number(unitnumSum.toFixed(${decimallen})),auxnumdetail:totalboxSum+"箱",
            totalboxvolume:totalboxvolumeSum,totalboxweight:totalboxweightSum};
        foot.push(footSum);
        $("#crm-datagrid-customerStorageOrderAddPage").datagrid('reloadFooter',foot);
    }

    function addSaveDetail(go){ //添加新数据确定后操作
        var flag = $("#crm-form-customerStorageDetailAddPage").form('validate');
        if(flag==false){
            return false;
        }
        var form = $("#crm-form-customerStorageDetailAddPage").serializeJSON();
        var goodsJson = $("#crm-goodsId-customerStorageDetailAddPage").goodsWidget('getObject');
        form.goodsInfo = goodsJson;
        if(form.overnum!=0){
            form.auxnumdetail = form.auxnum + form.auxunitname + form.overnum + form.unitname;
        }else{
            form.auxnumdetail = form.auxnum + form.auxunitname;
        }
        var rowIndex = 0;
        var rows = $wareList.datagrid('getRows');
        for(var i=0; i<rows.length; i++){
            var rowJson = rows[i];
            if(rowJson.goodsid == undefined){
                rowIndex = i;
                break;
            }
        }
        if(rowIndex == rows.length - 1){
            $wareList.datagrid('appendRow',{}); //如果是最后一行则添加一新行
        }
        $wareList.datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中

        if(go){
            $("#crm-form-customerStorageDetailAddPage").form("clear");
        }else{
            $("#crm-dialog-customerStorageOrderAddPage-content").dialog('close', true);
        }
        countTotal();
    }

    function editSaveDetail(){
        var flag = $("#crm-form-customerStorageDetailAddPage").form('validate');
        if(flag==false){
            return false;
        }
        var row = $wareList.datagrid('getSelected');
        var rowIndex = $wareList.datagrid('getRowIndex', row);
        var form = $("#crm-form-customerStorageDetailAddPage").serializeJSON();
        var goodsJson = $("#crm-goodsId-customerStorageDetailAddPage").goodsWidget('getObject');
        if(goodsJson == null || goodsJson == "") goodsJson = $wareList.datagrid('getSelected').goodsInfo;
        form.goodsInfo = goodsJson;
        form.fixnum=form.unitnum;
        if(form.overnum!=0){
            form.auxnumdetail = form.auxnum + form.auxunitname + form.overnum + form.unitname;
        }else{
            form.auxnumdetail = form.auxnum + form.auxunitname;
        }
        $wareList.datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
        $("#crm-dialog-customerStorageOrderAddPage-content").dialog('close', true);
        countTotal();

    }

    //客户变更后 更新明细价格以及相关信息
    function changeGoodsPrice(){
        var oldcustomerid = $("#crm-customer-hidden-customerStorageOrderAddPage").val();
        var customerid = $("#crm-customer-customerStorageOrderAddPage").customerWidget("getValue");
        $("#crm-customer-hidden-customerStorageOrderAddPage").val(customerid);

        if(oldcustomerid!=null && oldcustomerid!="" && oldcustomerid!=customerid){
            loading("客户变更，明细价格调整中");
            var rows = $wareList.datagrid('getRows');
            var count = 0;
            for(var i=0;i<rows.length; i++){
                if(rows[i].isdiscount==null || rows[i].isdiscount=='0'){
                    var goodsid = rows[i].goodsid;
                    var num = rows[i].unitnum;
                    var date = $("input[name='customerStorageOrder.businessdate']").val();
                    if(goodsid!=null && goodsid!=""){
                        var row = rows[i];
                        $.ajax({
                            url:'sales/countSalesGoodsByCustomer.do',
                            dataType:'json',
                            type:'post',
                            data:{customerid:customerid,goodsid:goodsid,num:num,date:date},
                            async:false,
                            success:function(json){
//                                leftAmount = json.leftAmount ;
//                                receivableAmount = json.receivableAmount;
                                var rowIndex = $wareList.datagrid("getRowIndex",row);
                                row.taxprice = json.taxprice;
                                row.oldprice = json.taxprice;
                                $wareList.datagrid('updateRow',{index:rowIndex, row:row});
                            }
                        });
                    }
                    count ++;
                }
            }
            //getCustomerFinanceInfo(customerid);
            if(count>0){
                $("#crm-customer-hidden-customerStorageOrderAddPage").val(customerid);
                $.messager.alert("提醒", "客户变更，自动调整订单明细中的商品价格！");

            }
            loaded();
        }else{
            //getCustomerFinanceInfo(customerid);
        }
    }

    //当前客户应收款及余额情况
//    function getCustomerFinanceInfo(customerid){
//        $.ajax({
//            url:'sales/getCustomerFinanceInfo.do',
//            dataType:'json',
//            type:'post',
//            data:{customerid:customerid},
//            async:false,
//            success:function(json){
//                leftAmount = json.leftAmount ;
//                receivableAmount = json.receivableAmount;
//            }
//        });
//        countTotal(leftAmount,receivableAmount);
//    }


</script>
</body>
</html>
