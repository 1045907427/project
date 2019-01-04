<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>信息交易页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div id="ebshop-ebtradePage-layout" class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="ebshop-buttons-ebtradePage"></div>
    </div>
    <div data-options="region:'center'">
        <div class="easyui-panel" data-options="fit:true" id="ebshop-panel-ebtradePage"></div>
        <div id="ebshop-window-showImg"></div>
    </div>
</div>
<div id="ebshop-ebtrade-dialog-split"></div>
<script type="text/javascript">
    var ebtrade_url = "ebtrade/showEbtradeAddPage.do";
    var ebtrade_type = '${type}';
    if(ebtrade_type == "view"){
        ebtrade_url = "ebtrade/showEbtradeViewPage.do?id=${id}";
    }
    if(ebtrade_type == "edit"){
        ebtrade_url = "ebtrade/showEbtradeEditPage.do?id=${id}";
    }
    if(ebtrade_type == "reissue"){
        ebtrade_url = "ebtrade/showEbtradeAddPage.do?id=${id}";
    }

    var detailTableColJson = $("#sales-datagrid-orderAddPage").createGridColumnLoad({
        frozenCol : [[
            {field:'id',title:'明细编号',hidden:true}
        ]],
        commonCol : [[
            {field:'goodsid',title:'商品编码',width:70,align:' left',sortable:true},
            {field:'goodsname', title:'商品名称', width:250,align:'left'},
            {field:'brandid', title:'商品品牌',width:60,align:'left',
                formatter:function(value,rowData,rowIndex){
                    return rowData.brandname;
                }
            },
            {field:'unitid', title:'单位',width:35,align:'left',
                formatter:function(value,rowData,rowIndex){
                    return rowData.unitname;
                }
            },
            {field:'unitnum', title:'数量',width:60,align:'right',sortable:true,
                formatter:function(value,row,index){
                    return formatterBigNum(value);
                }
            },
            {field:'auxunitid', title:'辅单位',width:50,align:'left',hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.auxunitname;
                }
            },
            {field:'totalbox', title:'箱数',width:60,align:'right',sortable:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'taxprice', title:'单价',width:60,align:'right',sortable:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'taxamount', title:'金额',width:60,align:'right',sortable:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'notaxprice', title:'未税单价',width:60,align:'right', hidden:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'notaxamount', title:'未税金额',width:60,align:'right', hidden:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'taxtype', title:'税种',width:60,align:'left',hidden:true,
                formatter:function(value,row,index){
                    return row.taxtypename;
                }
            },
            {field:'tax', title:'税额',width:60,align:'right',hidden:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'costprice', title:'成本价',width:60,align:'right', hidden:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'grossweight', title:'毛重',width:60,align:'right', hidden:true,
                formatter:function(value,row,index){
                    return formatterDefineMoney(value,4);
                }
            },
            {field:'singlevolume', title:'单体积',width:60,align:'right', hidden:true,
                formatter:function(value,row,index){
                    return formatterDefineMoney(value,4);
                }
            },
            {field:'auxnumdetail', title:'辅数量',width:60,align:'left'},
            {field:'remark', title:'备注',width:200,align:'left'}
        ]]
    });

    var orderTableColJson = $("#sales-datagrid-orderAddPage").createGridColumnLoad({
        frozenCol : [[
            {field:'id',title:'明细编号',hidden:true}
        ]],
        commonCol : [[
            {field:'tid',title:'订单交易号',width:70,align:' left',hidden:true},
            {field:'oid', title:'子订单编号', width:70,align:'left'},
            {field:'numIid', title:'商品数字ID',width:70,align:'left'},
            {field:'skuId', title:'最小库存单位',width:80,align:'left',
                formatter:function(value,rowData,rowIndex){
                    return rowData.skuIdname;
                }
            },
            {field:'outerIid', title:'商家外部编码',width:80,align:'left',sortable:true},
            {field:'price', title:'商品价格',width:60,align:'right',sortable:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'num', title:'购买数量',width:60,align:'right',sortable:true,
                formatter:function(value,row,index){
                    return formatterNum(value);
                }
            },
            {field:'totalFee', title:'应付金额',width:60,align:'right',sortable:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'payment', title:'实付金额',width:60,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'discountFee', title:'优惠金额',width:60,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'adjustFee', title:'手工调整金额',width:80,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'title', title:'商品标题',width:80,align:'left'},
            {field:'status', title:'订单状态',width:120,align:'left',
                formatter:function(value,row,index){
                    return row.statusname;
                }
            },
            {field:'refundStatus', title:'退款状态',width:120,align:'left',
                formatter:function(value,row,index){
                    return row.refundStatusname;
                }
            },
            {field:'sellerRate', title:'卖家是否评价',width:80,align:'left',
                formatter:function(value,row,index){
                    if("false" == value){
                        return "否";
                    }else if("1" == value){
                        return "是";
                    }
                }
            },
            {field:'sellerType', title:'商家类型',width:60,align:'left',
                formatter:function(value,row,index){
                    if("B" == value){
                        return "天猫";
                    }else if("C" == value){
                        return "普通卖家";
                    }
                }
            },
            {field:'picPath', title:'图片路径',width:200,align:'left',
                formatter:function(value,row,index){
                    if(null!=value){
                        return '<a href="javascript:showImages(\''+value+'\')">'+value+'</a>';
                    }
                }
            }
        ]]
    });

    //获取明细列表的高度
    function getHeight(go){
        var height = 0;
        if(go){
            height = $(window).height()-$("#base").height()-$("#more").height();
        }else{
            height = $(window).height()-$("#more").height()+27;
        }
        return height;
    }

    //加载控件项
    function loadwidgetdown(){
        //卖家昵称
        $("#ebshop-sellerNick-ebtradeAddPage").widget({
            width:150,
            referwid:'RL_T_EB_SELLER',
            singleSelect:true
        });

        //物流方式-配送时间变动
        $("#ebshop-shippingType-ebtradeAddPage").combobox({
            onSelect:function(record){
                if(record.value == "25" || record.value == "26"){
                    $("#ebshop-appointdate-ebtradeAddPage").removeAttr("disabled");
                }else{
                    $("#ebshop-appointdate-ebtradeAddPage").val("");
                    $("#ebshop-appointdate-ebtradeAddPage").attr("disabled","disabled");
                }
            }
        });
    }

    function refreshPanel(url){ //更新panel
        $("#ebshop-panel-ebtradePage").panel('refresh', url);
    }

    function countTotal(){ //计算合计
        var rows =  $("#ebshop-datagrid-ebtradeAddPage").datagrid('getRows');
        var unitnum = 0,auxnum = 0,auxremainder = 0,totalbox = 0,taxamount = 0,notaxamount = 0,tax = 0;
        for(var i=0; i<rows.length; i++){
            unitnum += Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
            auxnum += Number(rows[i].auxnum == undefined ? 0 : rows[i].auxnum);
            auxremainder += Number(rows[i].auxremainder == undefined ? 0 : rows[i].auxremainder);
            totalbox += Number(rows[i].totalbox == undefined ? 0 : rows[i].totalbox);
            taxamount += Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
            notaxamount += Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
            tax += Number(rows[i].tax == undefined ? 0 : rows[i].tax);
        }
        totalbox = formatterMoney(totalbox);
        var foot = [{goodsid:'合计',unitnum:unitnum,auxnum:auxnum,auxremainder:auxremainder,totalbox:totalbox,taxamount:taxamount,notaxamount:notaxamount,tax:tax,auxnumdetail:totalbox+"箱"}];
        $("#ebshop-datagrid-ebtradeAddPage").datagrid('reloadFooter',foot);
    }

    function countTotalAdd(){ //计算合计
        var rows =  $("#ebshop-datagrid-ebtradeAddPage").datagrid('getRows');
        var unitnum = 0,auxnum = 0,auxremainder = 0,totalbox = 0,taxamount = 0,notaxamount = 0,tax = 0,weight = 0,volume = 0;
        for(var i=0; i<rows.length; i++){
            unitnum += Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
            auxnum += Number(rows[i].auxnum == undefined ? 0 : rows[i].auxnum);
            auxremainder += Number(rows[i].auxremainder == undefined ? 0 : rows[i].auxremainder);
            totalbox += Number(rows[i].totalbox == undefined ? 0 : rows[i].totalbox);
            taxamount += Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
            notaxamount += Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
            tax += Number(rows[i].tax == undefined ? 0 : rows[i].tax);
            weight += Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum)*Number(rows[i].grossweight == undefined ? 0 : rows[i].grossweight);
            volume += Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum)*Number(rows[i].singlevolume == undefined ? 0 : rows[i].singlevolume);
        }
        totalbox = formatterMoney(totalbox);
        var foot = [{goodsid:'合计',unitnum:unitnum,auxnum:auxnum,auxremainder:auxremainder,totalbox:totalbox,taxamount:taxamount,notaxamount:notaxamount,tax:tax,auxnumdetail:totalbox+"箱"}];
        $("#ebshop-datagrid-ebtradeAddPage").datagrid('reloadFooter',foot);
        $("#ebshop-totalFee-ebtradeAddPage").numberbox('setValue',taxamount);
        $("#ebshop-weight-ebtradeAddPage").numberbox('setValue',weight*Number(1000));
        $("#ebshop-volume-ebtradeAddPage").numberbox('setValue',volume*Number(1000));
    }

    function showImages(value){
        $('<div id="ebshop-window-showImg1"></div>').appendTo('#ebshop-window-showImg');
        $('#ebshop-window-showImg1').dialog({
            title: '图片查看',
            maximizable:true,
            fit:true,
            closed:true,
            modal:true,
            cache:false,
            resizable:true,
            href:'ebtrade/showEbTradeOrderImgPage.do?imgpath='+value,
            onClose:function(){
                $('#ebshop-window-showImg1').dialog("destroy");
            }
        });
        $('#ebshop-window-showImg1').dialog("open");
    }

    //显示添加商品明细页面
    function beginAddDetail(flag){
        if(flag){
            var row = $dgDetailList.datagrid('getSelected');
            insertIndex = $dgDetailList.datagrid('getRowIndex', row);
        }
        $('<div id="ebshop-dialog-ebtradeAddPage-content"></div>').appendTo('#ebshop-dialog-ebtradeAddPage');
        $("#ebshop-dialog-ebtradeAddPage-content").dialog({ //弹出新添加窗口
            title:'商品信息添加(按ESC退出)',
            maximizable:true,
            width:600,
            height:400,
            closed:false,
            modal:true,
            cache:false,
            resizable:true,
            href:'ebtrade/showEbtradeDetailAddPage.do?',
            onClose:function(){
                $('#ebshop-dialog-ebtradeAddPage-content').dialog("destroy");
            },
            onLoad:function(){
                $("#ebshop-goodsId-ebtradeDetailAddPage").focus();
            }
        });
    }

    //添加新数据确定后操作
    function addSaveDetail(go){
        var flag = $("#ebshop-form-ebtradeDetailAddPage").form('validate');
        if(flag==false){
            return false;
        }
        var form = $("#ebshop-form-ebtradeDetailAddPage").serializeJSON();
        var goodsJson = $("#ebshop-goodsId-ebtradeDetailAddPage").goodsWidget('getObject');
        form.goodsInfo = goodsJson;
        if(form.auxremainder!=0){
            if(form.auxnum==null || form.auxnum==""){
                form.auxnum = 0;
            }
            form.auxnumdetail = form.auxnum + form.auxunitname + form.auxremainder + form.unitname;
        }else{
            form.auxnumdetail = form.auxnum + form.auxunitname;
        }

        var rowIndex = 0;
        var rows = $dgDetailList.datagrid('getRows');
        var updateFlag = false;
        for(var i=0; i<rows.length; i++){
            var rowJson = rows[i];
            if(rowJson.goodsid==goodsJson.id){
                rowIndex = i;
                updateFlag =  true;
                break;
            }
            if(rowJson.goodsid == undefined && rowJson.brandid==undefined){
                rowIndex = i;
                break;
            }
        }
        if(rowIndex == rows.length - 1){
            $dgDetailList.datagrid('appendRow',{}); //如果是最后一行则添加一新行
        }
        if(updateFlag){
            $.messager.alert("提醒", "此商品已经添加！");
            return false;
        }
        if(insertIndex == undefined){
            $dgDetailList.datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
        }
        else{
            $dgDetailList.datagrid('insertRow',{index:insertIndex+1,row:form});
            insertIndex = undefined
        }
        if(go){ //go为true确定并继续添加一条
            $("#ebshop-form-ebtradeDetailAddPage").form("clear");
        }
        else{ //否则直接关闭
            $("#ebshop-dialog-ebtradeAddPage-content").dialog('close', true)
        }

        countTotalAdd(); //第添加一条商品明细计算一次合计
    }

    //显示修改商品明细页面
    var insertIndex = undefined;
    function beginEditDetail(rowData){
        if(rowData == null){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        var row = rowData;
        row.goodsname = row.goodsInfo.name;
        row.brandname = row.goodsInfo.brandName;
        var url = '';
        if(row.goodsid == undefined){
            beginAddDetail();
        }
        else{
            url = 'ebtrade/showEbtradeDetailEditPage.do'; //如果是修改页面，数据直接来源于datagrid中的json数据。
            $('<div id="ebshop-dialog-ebtradeAddPage-content"></div>').appendTo('#ebshop-dialog-ebtradeAddPage');
            $("#ebshop-dialog-ebtradeAddPage-content").dialog({ //弹出修改窗口
                title:'商品信息修改(按ESC退出)',
                maximizable:true,
                width:600,
                height:400,
                closed:false,
                modal:true,
                cache:false,
                resizable:true,
                href:url,
                onClose:function(){
                    $('#ebshop-dialog-ebtradeAddPage-content').dialog("destroy");
                },
                onLoad: function(){
                    $("#ebshop-form-ebtradeDetailAddPage").form('load', row);
                    $("#ebshop-unitname-ebtradeDetailAddPage").text(row.unitname);
                    $("#ebshop-auxunitname-ebtradeDetailAddPage").text(row.auxunitname);
                    $("#ebshop-boxnum-ebtradeDetailAddPage").val(formatterNum(row.goodsInfo.boxnum));
                    $("#ebshop-goodsId-ebtradeDetailAddPage").goodsWidget("setValue", row.goodsid);
                    $("#ebshop-goodsId-ebtradeDetailAddPage").goodsWidget("setText", row.goodsInfo.name);
                    $("#ebshop-grossweight-ebtradeDetailAddPage").val(row.grossweight);
                    $("#ebshop-singlevolume-ebtradeDetailAddPage").val(row.singlevolume);
                    $("#ebshop-loading-ebtradeDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>"+row.goodsid+"</font>");
                    $("#ebshop-form-ebtradeDetailAddPage").form('validate');
                    $("input[name=unitnum]").focus();
                    $("input[name=unitnum]").select();
                }
            });
        }
    }

    //修改数据确定后操作，
    function editSaveDetail(go){
        var flag = $("#ebshop-form-ebtradeDetailAddPage").form('validate');
        if(flag==false){
            return false;
        }
        var row = $dgDetailList.datagrid('getSelected');
        var rowIndex = $dgDetailList.datagrid('getRowIndex', row);
        var form = $("#ebshop-form-ebtradeDetailAddPage").serializeJSON();
        var goodsJson = $("#ebshop-goodsId-ebtradeDetailAddPage").goodsWidget('getObject');
        if(goodsJson == null || goodsJson == "") goodsJson = $dgDetailList.datagrid('getSelected').goodsInfo;
        form.goodsInfo = goodsJson;
        if(form.auxremainder!=0){
            form.auxnumdetail = form.auxnum + form.auxunitname + form.auxremainder + form.unitname;
        }else{
            form.auxnumdetail = form.auxnum + form.auxunitname;
        }
        $dgDetailList.datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
        $("#ebshop-dialog-ebtradeAddPage-content").dialog('close', true)
        countTotalAdd();
    }

    function removeDetail(){
        var row = $dgDetailList.datagrid('getSelected');
        if(row == null){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $.messager.confirm("提醒","确定删除该商品明细?",function(r){
            if(r){
                var rowIndex = $dgDetailList.datagrid('getRowIndex', row);
                $dgDetailList.datagrid('deleteRow', rowIndex);
                countTotalAdd();
                var rows = $dgDetailList.datagrid('getRows');
                var index = -1;
                for(var i=0; i<rows.length; i++){
                    if(rows[i].goodsid != undefined){
                        index = i;
                        break;
                    }
                }
            }
        });
    }

    $(function(){
        $("#ebshop-panel-ebtradePage").panel({
            href:ebtrade_url,
            cache:false,
            maximized:true,
            border:false,
            onLoad:function(){

            }
        });

        //按钮
        $("#ebshop-buttons-ebtradePage").buttonWidget({
            initButton:[
                {},
                <security:authorize url="/ebtrade/ebtradeSave.do">
                {
                    type: 'button-save',
                    handler: function(){
                        $.messager.confirm("提醒","确定保存？",function(r){
                            if(r){
                                var rows = $("#ebshop-datagrid-ebtradeAddPage").datagrid('getRows');
                                $("#ebshop-goodsJson-ebtradeAddPage").val(JSON.stringify(rows));
                                $("#ebshop-form-ebtradeAddPage").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/ebtrade/ebtradeAudit.do">
                {
                    type: 'button-audit',
                    handler: function(){
                        var idStr = $("#ebshop-id-ebtradeAddPage").val();
                        var state = $("#ebshop-state-ebtradeAddPage").val();
                        if(state != "0"){
                            $.messager.alert("提醒","交易信息 编号：" + idStr + "不为未审核状态，不允许审核；");
                            return false;
                        }
                        if(idStr != ""){
                            loading("审核中..");
                            $.ajax({
                                url :'ebtrade/auditEbtrade.do',
                                type:'post',
                                dataType:'json',
                                data:{idStr:idStr},
                                success:function(json){
                                    loaded();
                                    var msg = "";
                                    if(json.retmsg != ""){
                                        msg = json.retmsg;
                                    }
                                    if(json.sucids != ""){
                                        if(msg == ""){
                                            msg = "交易信息 编号：" + json.sucids + "审核交易信息成功；";
                                        }else{
                                            msg += "<br>" + "交易信息 编号：" + json.sucids + "审核交易信息成功；";
                                        }
                                    }
                                    if(json.unsucids != ""){
                                        if(msg == ""){
                                            msg = "交易信息 编号：" + json.unsucids + "审核交易信息失败；";
                                        }else{
                                            msg += "<br>" + "交易信息 编号：" + json.unsucids + "审核交易信息失败；";
                                        }
                                    }
                                    if(json.msg != ""){
                                        if(msg == ""){
                                            msg = json.msg;
                                        }else{
                                            msg += "<br>" + json.msg;
                                        }
                                    }
                                    if(msg != ""){
                                        $.messager.alert("提醒",msg);
                                        refreshPanel("ebtrade/showEbtradeViewPage.do?id="+idStr);
                                    }
                                },
                                error:function(){
                                    loaded();
                                    $.messager.alert("提醒","审核出错！");
                                }
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/ebtrade/ebtradeSupperAudit.do">
                {
                    type: 'button-supperaudit',
                    handler: function(){
                        var idStr = $("#ebshop-id-ebtradeAddPage").val();
                        var state = $("#ebshop-state-ebtradeAddPage").val();
                        if(state != "0"){
                            $.messager.alert("提醒","交易信息 编号：" + idStr + "不为未审核状态，不允许审核；");
                            return false;
                        }
                        if(idStr != ""){
                            loading("审核中..");
                            $.ajax({
                                url :'ebtrade/doSupperAuditEbtrade.do',
                                type:'post',
                                dataType:'json',
                                data:{idStr:idStr},
                                success:function(json){
                                    loaded();
                                    var msg = "";
                                    if(json.sucids != ""){
                                        if(msg == ""){
                                            msg = "交易信息 编号：" + json.sucids + "审核交易信息成功；";
                                        }else{
                                            msg += "<br>" + "交易信息 编号：" + json.sucids + "审核交易信息成功；";
                                        }
                                    }
                                    if(json.unsucids != ""){
                                        if(msg == ""){
                                            msg = "交易信息 编号：" + json.unsucids + "审核交易信息失败；";
                                        }else{
                                            msg += "<br>" + "交易信息 编号：" + json.unsucids + "审核交易信息失败；";
                                        }
                                    }
                                    if(msg != ""){
                                        $.messager.alert("提醒",msg);
                                        refreshPanel("ebtrade/showEbtradeViewPage.do?id="+idStr);
                                    }
                                },
                                error:function(){
                                    loaded();
                                    $.messager.alert("提醒","审核出错！");
                                }
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/ebtrade/ebtradeOppaudit.do">
                {
                    type: 'button-oppaudit',
                    handler: function(){
                        var idStr = $("#ebshop-id-ebtradeAddPage").val();
                        var state = $("#ebshop-state-ebtradeAddPage").val();
                        if(state != "1"){
                            $.messager.alert("提醒","交易信息 编号：" + idStr + "不为审核状态，不允许反审；");
                            return false;
                        }
                        if(idStr != ""){
                            loading("反审中..");
                            $.ajax({
                                url :'ebtrade/oppauditEbtrade.do',
                                type:'post',
                                dataType:'json',
                                data:{idStr:idStr},
                                success:function(json){
                                    loaded();
                                    var msg = "";
                                    if(json.retmsg != ""){
                                        msg = json.retmsg;
                                    }
                                    if(json.sucids != ""){
                                        if(msg == ""){
                                            msg = "交易信息 编号：" + json.sucids + "反审交易信息成功；";
                                        }else{
                                            msg += "<br>" + "交易信息 编号：" + json.sucids + "反审交易信息成功；";
                                        }
                                    }
                                    if(json.unsucids != ""){
                                        if(msg == ""){
                                            msg = "交易信息 编号：" + json.unsucids + "反审交易信息失败；";
                                        }else{
                                            msg += "<br>" + "交易信息 编号：" + json.unsucids + "反审交易信息失败；";
                                        }
                                    }
                                    if(msg != ""){
                                        $.messager.alert("提醒",msg);
                                        refreshPanel("ebtrade/showEbtradeEditPage.do?id="+idStr);
                                    }
                                },
                                error:function(){
                                    loaded();
                                    $.messager.alert("提醒","反审出错！");
                                }
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/ebtrade/ebtradeBack.do">
                {
                    type: 'button-back',
                    handler: function(data){
                        refreshPanel('ebtrade/showEbtradeViewPage.do?id='+ data.id);
                    }
                },
                </security:authorize>
                <security:authorize url="/ebtrade/ebtradeNext.do">
                {
                    type: 'button-next',
                    handler: function(data){
                        refreshPanel('ebtrade/showEbtradeViewPage.do?id='+ data.id);
                    }
                },
                </security:authorize>
                {}
            ],
            buttons:[
                <security:authorize url="/sales/invalidOrderClose.do">
                {
                    id:'button-invalid',
                    name:'作废',
                    iconCls:'button-delete',
                    handler:function(){
                        var id = $("#ebshop-id-ebtradeAddPage").val();
                        if(id == ''){
                            return false;
                        }
                        $.messager.confirm("提醒","确定作废该电商交易信息？",function(r){
                            if(r){
                                loading("作废中..");
                                $.ajax({
                                    url:'ebtrade/invalidEbtrade.do',
                                    dataType:'json',
                                    type:'post',
                                    data:'id='+ id,
                                    success:function(json){
                                        loaded();
                                        if(json.flag == true){
                                            $.messager.alert("提醒","作废成功。");
                                            $("#ebshop-panel-ebtradePage").panel('refresh', 'ebtrade/showEbtradeViewPage.do?id='+ id);
                                        }
                                        else{
                                            $.messager.alert("提醒","作废失败<br/>"+json.msg);
                                        }
                                    },
                                    error:function(){
                                        loaded();
                                        $.messager.alert("错误","作废出错");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/ebtrade/getWaybillInfoBtn.do">
                {
                    id:'getWaybillInfo',
                    name:'申请物流面单',
                    iconCls:'button-audit',
                    handler:function(){
                        var idStr = $("#ebshop-id-ebtradeAddPage").val();
                        var state = $("#ebshop-state-ebtradeAddPage").val();
                        if(state != "1"){
                            $.messager.alert("提醒","交易信息 编号：" + idStr + "不为审核状态，不允许申请物流面单；");
                            return false;
                        }
                        if(idStr != ""){
                            loading("申请中..");
                            $.ajax({
                                url :'ebtrade/getWaybillInfo.do',
                                type:'post',
                                dataType:'json',
                                data:{idStr:idStr},
                                success:function(json){
                                    loaded();
                                    var msg = "";
                                    if(undefined != json.sucids && json.sucids != ""){
                                        msg = "交易信息 编号：" + json.sucids + "申请物流面单成功；";
                                        $("#ebshop-panel-ebtradePage").panel('refresh', 'ebtrade/showEbtradeViewPage.do?id='+ idStr);
                                    }
                                    if(undefined != json.unsucmsg && json.unsucmsg != ""){
                                        if(msg == ""){
                                            msg = json.unsucmsg + "不允许申请；";
                                        }else{
                                            msg += "<br>" + json.unsucmsg +"不允许申请；";
                                        }
                                    }
                                    if(json.retmsg != ""){
                                        if(msg == ""){
                                            msg = json.retmsg;
                                        }else{
                                            msg += "<br>" + json.retmsg;
                                        }
                                    }
                                    if(msg != ""){
                                        $.messager.alert("提醒",msg);
                                    }
                                },
                                error:function(){
                                    loaded();
                                    $.messager.alert("提醒","申请物流面单出错！");
                                }
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/ebtrade/printWaybillInfoBtn.do">
                {
                    id:'printWaybillInfo',
                    name:'打印物流面单',
                    iconCls:'button-print',
                    handler:function(){
                        var idStr = $("#ebshop-id-ebtradeAddPage").val();
                        $.ajax({
                            url :'ebtrade/checkWayBillPrintFlag.do',
                            type:'post',
                            dataType:'json',
                            data:{idStr:idStr},
                            success:function(json){
                                var obj = json.retobj;
                                if(!obj.flag){
                                    $.messager.alert('提醒','该交易信息的打印状态未通过验证，不允许打印!');
                                    return false;
                                }else{
                                    var retmsg = "";
                                    var wayobj = obj.items;
                                    if(!wayobj[0].flag){
                                        retmsg = "交易信息 编码："+wayobj[0].package_id+" "+wayobj[0].msg+ "，不允许打印；";
                                    }
                                    if(retmsg != ""){
                                        $.messager.alert("提醒",retmsg);
                                        return false;
                                    }else{
                                        $.messager.alert("提醒","允许打印！");
                                    }
                                }
                            },
                            error:function(){
                                loaded();
                                $.messager.alert("提醒","打印物流面单出错！");
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/ebtrade/cancelWayBillBtn.do">
                {
                    id:'cancelWayBill',
                    name:'取消物流信息',
                    iconCls:'button-oppaudit',
                    handler:function(){
                        var idStr = $("#ebshop-id-ebtradeAddPage").val();
                        var state = $("#ebshop-state-ebtradeAddPage").val();
                        if(state != "2"){
                            $.messager.alert("提醒","交易信息 编号：" + idStr + "不为已获取物流单号状态下，不允许取消打印信息；");
                            return false;
                        }
                        loading("取消中..");
                        $.ajax({
                            url :'ebtrade/cancelWayBill.do',
                            type:'post',
                            dataType:'json',
                            data:{idStr:idStr},
                            success:function(json){
                                loaded();
                                var msg = "";
                                if(json.sucids != ""){
                                    msg = "交易信息 编号：" + json.sucids + "取消物流面单打印信息成功；";
                                    $("#ebshop-panel-ebtradePage").panel('refresh', 'ebtrade/showEbtradeEditPage.do?id='+ idStr);
                                }
                                if(json.retmsg != ""){
                                    if(msg == ""){
                                        msg = json.retmsg;
                                    }else{
                                        msg += "<br>" + json.retmsg;
                                    }
                                }
                                if(msg != ""){
                                    $.messager.alert("提醒",msg);
                                }
                            },
                            error:function(){
                                loaded();
                                $.messager.alert("提醒","取消物流面单出错！");
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/ebtrade/sendTradeConfirmBtn.do">
                {
                    id:'sendTradeConfirm',
                    name:'交易订单发货',
                    iconCls:'button-view',
                    handler:function(){
                        var idStr = $("#ebshop-id-ebtradeAddPage").val();
                        var state = $("#ebshop-state-ebtradeAddPage").val();
                        if(state == "0" || state == "1"){
                            $.messager.alert("提醒","交易信息 编号：" + idStr + "未获取物流单号，不允许交易订单发货；");
                            return false;
                        }
                        if(idStr != ""){
                            loading("发货中..");
                            $.ajax({
                                url :'ebtrade/sendTradeConfirm.do',
                                type:'post',
                                dataType:'json',
                                data:{idStr:idStr},
                                success:function(json){
                                    loaded();
                                    var msg = "";
                                    if(json.sucids != ""){
                                        msg = "交易信息 编号：" + json.sucids + "交易订单发货成功；";
                                    }
                                    if(json.retmsg != ""){
                                        if(msg == ""){
                                            msg = json.retmsg;
                                        }else{
                                            msg += "<br>" + json.retmsg;
                                        }
                                    }
                                    if(msg != ""){
                                        $.messager.alert("提醒",msg);
                                    }
                                },
                                error:function(){
                                    loaded();
                                    $.messager.alert("提醒","交易订单发货出错！");
                                }
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/ebtrade/sendTradeConfirmBtn.do">
                {
                    id:'addSaleOut',
                    name:'生成销售发货单',
                    iconCls:'button-add',
                    handler:function(){
                        var idStr = $("#ebshop-id-ebtradeAddPage").val();
                        var state = $("#ebshop-state-ebtradeAddPage").val();
                        if(state == "6"){
                            $.messager.alert("提醒","交易信息 编号：" + idStr + "已生成发货单；");
                            return false;
                        }
                        if(idStr != ""){
                            loading("生成中..");
                            $.ajax({
                                url :'ebtrade/addSaleOut.do',
                                type:'post',
                                dataType:'json',
                                data:{idStr:idStr},
                                success:function(json){
                                    loaded();
                                    $.messager.alert("提醒",json.msg);
                                    if(json.billid != ""){
                                        refreshPanel("ebtrade/showEbtradeViewPage.do?id="+idStr);
                                    }else{
                                        refreshPanel("ebtrade/showEbtradeEditPage.do?id="+idStr);
                                    }
                                }
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/ebtrade/ebtrageSplitPage.do">
                {
                    id:'button-split',
                    name:'拆单',
                    iconCls:'button-add',
                    handler:function(){
                        var id = $("#ebshop-id-ebtradeAddPage").val();
                        var state = $("#ebshop-state-ebtradeAddPage").val();
                        if(state != "0"){
                            $.messager.alert("提醒","不为未审核状态的单据不允许拆分!");
                            return false;
                        }
                        $("#ebshop-ebtrade-dialog-split").dialog({
                            title:'电商交易订单拆分',
                            fit:true,
                            closed:false,
                            cache:false,
                            modal: true,
                            href:'ebtrade/showEbtradeSplitPage.do?id='+ id,
                            buttons:[
                                {
                                    text:'确定',
                                    handler: function(){
                                        //判断单据数量与拆分数量是否相等，若相等则不能拆分
                                        var rows = $("#ebshop-datagrid-ebtradeSplitPage").datagrid('getChecked');
                                        if(rows.length == 0){
                                            $.messager.alert("提醒","请勾选要拆分的单据!");
                                            return false;
                                        }
                                        var outeriids = "";
                                        var loadrows = $("#ebshop-datagrid-ebtradeSplitPage").datagrid('getRows');
                                        if(rows.length == loadrows.length){
                                            for(var i=0;i<rows.length;i++){
                                                if(rows[i].num == rows[i].splitnum){
                                                    var rowindex = $("#ebshop-datagrid-ebtradeSplitPage").datagrid('getRowIndex',rows[i]);
                                                    $("#ebshop-datagrid-ebtradeSplitPage").datagrid('uncheckRow',rowindex);
                                                    if(outeriids == ""){
                                                        outeriids = rows[i].outerIid;
                                                    }else{
                                                        outeriids += "," + rows[i].outerIid;
                                                    }
                                                }
                                            }
                                        }
                                        if(outeriids != ""){
                                            $.messager.alert("提醒","商家外部编码:"+outeriids+"的购买数量与拆分数量相等的单据,不能拆分!");
                                            return false;
                                        }
                                        $.messager.confirm('确认','是否根据选中行拆分当前电商交易订单，生成新的单据?',function(r){
                                            if(r){
                                                $("#ebshop-form-ebtradeSplitPage").submit();
                                            }
                                        });
                                    }
                                }
                            ]
                        });
                    }
                },
                </security:authorize>
            ],
            layoutid:'ebshop-ebtradePage-layout',
            model: 'bill',
            type: 'view',
            tab:'交易信息列表',
            taburl:'/ebtrade/showEbTradeListPage.do',
            id:'${id}',
            datagrid:'ebshop-datagrid-ebtrageListPage'
        });
    });
</script>
</body>
</html>
