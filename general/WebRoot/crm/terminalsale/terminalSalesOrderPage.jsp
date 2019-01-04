<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>客户销量页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<input type="hidden" id="crm-backid-terminalSalesOrderPage" value="${id }" />
<div id="crm-orderPage-layout" class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="crm-buttons-terminalSalesOrderPage"></div>
    </div>
    <div data-options="region:'center'">
        <div class="easyui-panel" id="crm-panel-terminalSalesOrderPage" data-options="fit:true"></div>
    </div>
</div>
<script type="text/javascript">
    var order_url = "crm/terminal/showTerminalSalesOrderAddPage.do";
    var order_type = '${type}';
    <%--if(order_type == "view" ){--%>
        <%--order_url = "crm/terminal/orderViewPage.do?id=${id}";--%>
    <%--}--%>
    if(order_type == "edit"){
        order_url = "crm/terminal/terminalOrderEditPage.do?id=${id}";
    }
    var terminal_taxpricechange = "0";

    var terminal_AjaxConn = function (Data, Action) {
        var MyAjax = $.ajax({
            type: 'post',
            cache: false,
            url: Action,
            data: Data,
            async: false,
            success:function(data){
                loaded();
            }
        })
        return MyAjax.responseText;
    }

    var tableColJson = $("#crm-datagrid-terminalSalesOrderAddPage").createGridColumnLoad({
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
                    }else if(value != undefined){
                        return value;
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
                    }else if(value != undefined){
                        return value;
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
            {field:'taxprice', title:'单价',width:60,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
//            {field:'boxprice', title:'箱价',width:60,aliascol:'taxprice',align:'right',
//                formatter:function(value,row,index){
//                    return formatterMoney(value);
//                }
//            },
            {field:'oldprice', title:'原价',width:60,align:'right', hidden:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'taxamount', title:'金额',width:60,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'notaxamount', title:'未税金额',width:60,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
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
            {field:'taxtype', title:'税种',width:60,align:'left',hidden:true,
                formatter:function(value,row,index){
                    return row.taxtypename;
                }
            },
            {field:'remark', title:'备注',width:200,align:'left'}
        ]]
    });
    //按钮
    $("#crm-buttons-terminalSalesOrderPage").buttonWidget({
        initButton: [
            {},
            <security:authorize url="/crm/terminal/addTerminalSalesOrder.do">
            {
                type: 'button-add',
                handler: function () {
                    refreshPanel('crm/terminal/terminalSalesOrderPage.do');
                }
            },
            </security:authorize>
            <security:authorize url="/crm/terminal/saveTerminalSalesOrder.do">
            {
                type: 'button-save',
                handler: function () {
                    var rows = $("#crm-datagrid-terminalSalesOrderAddPage").datagrid('getRows');
                    $("#crm-goodsJson-terminalSalesOrderAddPage").val(JSON.stringify(rows));
                    $("#crm-form-terminalSalesOrderAddPage").submit();
                }
            },
            </security:authorize>
            <security:authorize url="/crm/terminal/deleteTerminalSalesOrder.do">
            {
                type: 'button-delete',
                handler: function(){
                    $.messager.confirm("提醒","是否删除当前客户销量数据？",function(r){
                        if(r){
                            var id = $("#crm-backid-terminalSalesOrderPage").val();
                            if(id!=""){
                                loading("删除中..");
                                $.ajax({
                                    url :'crm/terminal/deleteTerminalSalesOrder.do?id='+id,
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
                                            var data = $("#crm-buttons-terminalSalesOrderPage").buttonWidget("removeData", '');
                                            if(data != null){
                                                $("#crm-backid-terminalSalesOrderPage").val(data.id);
                                                refreshPanel('crm/terminal/terminalOrderEditPage.do?id='+ data.id);
                                            }else{
                                                parent.closeNowTab();
                                            }
                                            $("#crm-table-terminalSalesOrderList").datagrid("reload");
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
            <security:authorize url="/crm/terminal/backTerminalSalesOrder.do">
            {
                type: 'button-back',
                handler: function(data){
                    $("#crm-backid-terminalSalesOrderPage").val(data.id);
                    refreshPanel('crm/terminal/terminalSalesOrderPage.do?type=edit&id='+ data.id);
                }
            },
            </security:authorize>
            <security:authorize url="/crm/terminal/nextTerminalSalesOrder.do">
            {
                type: 'button-next',
                handler: function(data){
                    $("#crm-backid-terminalSalesOrderPage").val(data.id);
                    refreshPanel('crm/terminal/terminalSalesOrderPage.do?type=edit&id='+ data.id);
                }
            },
            </security:authorize>
            {}
        ],
        layoutid:'crm-orderPage-layout',
        taburl:'/crm/terminal/terminalSalesOrderListPage.do',
        model:'bill',
        type: 'view',
        id:'${id}',
        datagrid:'crm-table-terminalSalesOrderList'
    });

    var leftAmount = 0;
    var receivableAmount = 0;
    function refreshPanel(url){ //更新panel
        //客户信息归零
        leftAmount = 0;
        receivableAmount = 0;
        $("#crm-panel-terminalSalesOrderPage").panel('refresh', url);
    }

    function beginAddTerminalDetail(){

        var customer = $("#crm-customer-terminalSalesOrderAddPage").customerWidget("getValue");
        if(customer == ''){
            $("#crm-customer-terminalSalesOrderAddPage").focus();
            $.messager.alert("提醒","请先选择客户再进行添加商品信息");
            return false;
        }
        $('<div id="crm-dialog-terminalSalesOrderAddPage-content"></div>').appendTo('#crm-dialog-terminalSalesOrderAddPage');
        $("#crm-dialog-terminalSalesOrderAddPage-content").dialog({ //弹出新添加窗口
            title:'商品信息添加(按ESC退出)',
            maximizable:true,
            width:600,
            height:450,
            closed:false,
            modal:true,
            cache:false,
            resizable:true,
            href:'crm/terminal/terminalOrderDetailAddPage.do?customerid='+customer,
            onClose:function(){
                $('#crm-dialog-terminalSalesOrderAddPage-content').dialog("destroy");
            },
            onLoad:function(){
                $("#crm-goodsId-terminalOrderDetailAddPage").focus();
            }
        });
    }

    function beginEditTerminalDetail(rowIndex,rowData){
        var customer = $("#crm-customer-terminalSalesOrderAddPage-hidden").val();
        if(customer == ''){
            $.messager.alert("提醒","请先选择客户再进行添加商品信息");
            $("#crm-customer-terminalSalesOrderAddPage").focus();
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
            beginAddTerminalDetail();
        }else{
            url = 'crm/terminal/terminalOrderDetailEditPage.do?orderid='+ rowData.id+'&goodsid='+row.goodsid+'&customerid='+customer; //如果是修改页面，数据直接来源于datagrid中的json数据。
            $('<div id="crm-dialog-terminalSalesOrderAddPage-content"></div>').appendTo('#crm-dialog-terminalSalesOrderAddPage');
            $("#crm-dialog-terminalSalesOrderAddPage-content").dialog({ //弹出新添加窗口
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
                    $('#crm-dialog-terminalSalesOrderAddPage-content').dialog("destroy");
                },
                onLoad:function(){
                    $("#crm-form-terminalOrderDetailAddPage").form('load', row);
                    $("#crm-rowIndex-terminalOrderDetailAddPage").val(rowIndex);
                    $("#crm-unitname-terminalOrderDetailAddPage").text(row.unitname);
                    $("#crm-auxunitname-terminalOrderDetailAddPage").text(row.auxunitname);
                    $("#crm-goodsId-terminalOrderDetailAddPage").goodsWidget("setValue", row.goodsid);
                    $("#crm-goodsId-terminalOrderDetailAddPage").goodsWidget("setText", row.goodsInfo.name);
                    $("#crm-loading-terminalOrderDetailAddPage").removeClass("img-loading").html
                    ("商品编码：<font color='green'>"+row.goodsid);
                    var boxprice = Number(row.taxprice) * Number(row.goodsInfo.boxnum);
                    $("#crm-boxprice-terminalOrderDetailAddPage").val(boxprice);
                    $("#crm-boxnum-terminalOrderDetailAddPage").val(row.goodsInfo.boxnum);
                    $("input[name=unitnum]").focus();
                    formaterNumSubZeroAndDot();

                    //判断是否手动改过含税单价
                    var ret = terminal_AjaxConn({id:row.goodsid,unitnum:row.unitnum,cid:customer,date:$("input[name='crmSalesOrder.businessdate']").val()},'sales/getAuxUnitNumAndPrice.do');
                    var retjson = $.parseJSON(ret);
                    if(formatterDefineMoney(row.taxprice,6) != formatterDefineMoney(retjson.taxprice,6)){
                        terminal_taxpricechange = "1";
                    }else{
                        terminal_taxpricechange = "0";
                    }

                    $("#crm-form-terminalOrderDetailAddPage").form('validate');
                }
            });

        }
    }

    function deleteTerminalDetail(){
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
        $("#crm-panel-terminalSalesOrderPage").panel({
            href:order_url,
            cache:false,
            maximized:true,
            border:false,
            onLoad:function(){
                $("#crm-customer-terminalSalesOrderAddPage").focus();
            }
        });

    });

    function countTotal(leftAmount,receivableAmount){ //计算合计
        var checkrows =  $("#crm-datagrid-terminalSalesOrderAddPage").datagrid('getChecked');
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
        leftAmount = formatterMoney(leftAmount);
        var foot = [{/*goodsname:'余额:'+leftAmount,*/goodsid:'选中合计',unitnum:unitnum,auxnumdetail:totalbox+"箱",
            totalboxvolume:totalboxvolume,totalboxweight:totalboxweight}];
        //合计
        var rows =  $("#crm-datagrid-terminalSalesOrderAddPage").datagrid('getRows');
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
        receivableAmount = formatterMoney(receivableAmount);
        var footSum = {goodsid:'合计',/*goodsname:"应收款:"+receivableAmount,*/unitnum:Number(unitnumSum.toFixed(${decimallen})),auxnumdetail:totalboxSum+"箱",
            totalboxvolume:totalboxvolumeSum,totalboxweight:totalboxweightSum};
        foot.push(footSum);
        $("#crm-datagrid-terminalSalesOrderAddPage").datagrid('reloadFooter',foot);
    }

    function addSaveDetail(go){ //添加新数据确定后操作，
        var flag = $("#crm-form-terminalOrderDetailAddPage").form('validate');
        if(flag==false){
            return false;
        }
        var form = $("#crm-form-terminalOrderDetailAddPage").serializeJSON();
        var goodsJson = $("#crm-goodsId-terminalOrderDetailAddPage").goodsWidget('getObject');
        if(goodsJson == null || goodsJson == ""){
            goodsJson = $wareList.datagrid('getSelected').goodsInfo;
        }
        form.goodsInfo = goodsJson;
        if(form.overnum!=0){
            form.auxnumdetail = form.auxnum + form.auxunitname + form.overnum + form.unitname;
        }else{
            form.auxnumdetail = form.auxnum + form.auxunitname;
        }
        var rowIndex = 0;
        var rows = $wareList.datagrid('getRows');
        if(form.rowIndex != undefined){
            rowIndex = form.rowIndex ;
        }else{
            for(var i=0; i<rows.length; i++){
                var rowJson = rows[i];
                if(rowJson.goodsid == undefined){
                    rowIndex = i;
                    break;
                }
            }
        }
        if(rowIndex == rows.length - 1){
            $wareList.datagrid('appendRow',{}); //如果是最后一行则添加一新行
        }
        $wareList.datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
       
        if(go){
            $("#crm-form-terminalOrderDetailAddPage").form("clear");
        }else{
            $("#crm-dialog-terminalSalesOrderAddPage-content").dialog('close', true);
        }
        countTotal();
    }

    //客户变更后 更新明细价格以及相关信息
    function changeGoodsPrice(){
        var oldcustomerid = $("#crm-customer-hidden-terminalSalesOrderAddPage").val();
        var customerid = $("#crm-customer-terminalSalesOrderAddPage").customerWidget("getValue");
        $("#crm-customer-hidden-terminalSalesOrderAddPage").val(customerid);

        if(oldcustomerid!=null && oldcustomerid!="" && oldcustomerid!=customerid){
            loading("客户变更，明细价格调整中");
            var rows = $wareList.datagrid('getRows');
            var count = 0;
            for(var i=0;i<rows.length; i++){
                if(rows[i].isdiscount==null || rows[i].isdiscount=='0'){
                    var goodsid = rows[i].goodsid;
                    var num = rows[i].unitnum;
                    var date = $("input[name='terminalSalesOrder.businessdate']").val();
                    if(goodsid!=null && goodsid!=""){
                        var row = rows[i];
                        $.ajax({
                            url:'sales/countSalesGoodsByCustomer.do',
                            dataType:'json',
                            type:'post',
                            data:{customerid:customerid,goodsid:goodsid,num:num,date:date},
                            async:false,
                            success:function(json){
                                leftAmount = json.leftAmount ;
                                receivableAmount = json.receivableAmount;
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
           getCustomerFinanceInfo(customerid);
            if(count>0){
                $("#crm-customer-hidden-terminalSalesOrderAddPage").val(customerid);
                $.messager.alert("提醒", "客户变更，自动调整订单明细中的商品价格！");

            }
            loaded();
        }else{
            getCustomerFinanceInfo(customerid);
        }
    }

    // //当前客户应收款及余额情况
    function getCustomerFinanceInfo(customerid){
        $.ajax({
            url:'sales/getCustomerFinanceInfo.do',
            dataType:'json',
            type:'post',
            data:{customerid:customerid},
            async:false,
            success:function(json){
                leftAmount = json.leftAmount ;
                receivableAmount = json.receivableAmount;
            }
        });
        countTotal(leftAmount,receivableAmount);
    }

</script>
</body>
</html>
