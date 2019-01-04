<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>电商商品列表</title>
    <%@include file="/include.jsp" %>
</head>
<body>
    <div class="easyui-layout" data-options="fit:true,border:true">
        <div data-options="region:'north',split:false,border:false">
            <div class="buttonBG" id="ebshop-buttons-ebgoodsListPage"></div>
        </div>
        <div data-options="region:'center'">
            <div id="ebshop-toobar-ebgoodsListPage" style="padding:0px;">
                <form id="ebshop-queryForm-ebgoodsListPage">
                    <table class="querytable">
                        <tr>
                            <td>编码:</td>
                            <td><input type="text" name="id" style="width:150px"/></td>
                            <td>名称:</td>
                            <td><input type="text" name="name" style="width:150px" /></td>
                            <td>状态：</td>
                            <td>
                                <select name="state" style="width:150px;">
                                    <option></option>
                                    <option value="2">保存</option>
                                    <option value="1">启用</option>
                                    <option value="0">禁用</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>自动更新商品库存：</td>
                            <td>
                                <select name="isupdate" style="width: 150px;">
                                    <option></option>
                                    <option value="0">否</option>
                                    <option value="1">是</option>
                                </select>
                            </td>
                            <td>是否上架：</td>
                            <td>
                                <select name="islisting" style="width: 150px;">
                                    <option></option>
                                    <option value="0">否</option>
                                    <option value="1">是</option>
                                </select>
                            </td>
                            <td colspan="2">
                                <a href="javascript:;" id="ebshop-queay-ebgoodsListPage"  class="button-qr">查询</a>
                                <a href="javaScript:;" id="ebshop-resetQueay-ebgoodsListPage"  class="button-qr">重置</a>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <table id="ebshop-datagrid-ebgoodsListPage"></table>
            <div id="ebshop-dialog-ebgoodsListPage"></div>
            <div id="ebshop-dialog-addmuti-ebgoodsListPage"></div>
        </div>
    </div>
    <script type="text/javascript">
    var ebgoods_AjaxConn = function (Data, Action) {
        var MyAjax = $.ajax({
            type: 'post',
            cache: false,
            url: Action,
            data: Data,
            async: false
        })
        return MyAjax.responseText;
    }

    //加锁
    function isDoLockData(id,tablename){
        var flag = false;
        $.ajax({
            url :'system/lock/isDoLockData.do',
            type:'post',
            data:{id:id,tname:tablename},
            dataType:'json',
            async: false,
            success:function(json){
                flag = json.flag
            }
        });
        return flag;
    }

    function refreshLayout(title, url){
        $('<div id="ebshop-dialog-ebgoodsListPage1"></div>').appendTo('#ebshop-dialog-ebgoodsListPage');
        $('#ebshop-dialog-ebgoodsListPage1').dialog({
            maximizable:true,
            resizable:true,
            title: title,
            fit:true,
            closed: true,
            cache: false,
            href: url,
            modal: true,
            onClose:function(){
                $('#ebshop-dialog-ebgoodsListPage1').dialog("destroy");
            }
        });
        $("#ebshop-dialog-ebgoodsListPage1").dialog("open");
    }

    function beginAddDetail(){
        $('<div id="ebshop-dialog-detial-ebgoodsAddPage1"></div>').appendTo('#ebshop-dialog-detial-ebgoodsAddPage');
        $("#ebshop-dialog-detial-ebgoodsAddPage1").dialog({ //弹出新添加窗口
            title:'商品信息添加(按ESC退出)',
            maximizable:true,
            width:530,
            height:260,
            closed:true,
            modal:true,
            cache:false,
            resizable:true,
            href:'ebgoods/showEbgoodsDetailAddPage.do',
            onClose:function(){
                $('#ebshop-dialog-detial-ebgoodsAddPage1').dialog("destroy");
            },
            onLoad:function(){
                $("#ebshop-goodsid-ebgoodsDetailAddPage").focus();
            }
        });
        $('#ebshop-dialog-detial-ebgoodsAddPage1').dialog("open");
    }

    function beginEditDetail(rowData){
        if(null == rowData){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        var row = rowData;
        var url = '';
        if(row.goodsid == undefined){
            beginAddDetail();
        }else{
            url = 'ebgoods/showEbgoodsDetailEditPage.do';
            $('<div id="ebshop-dialog-detial-ebgoodsAddPage1"></div>').appendTo('#ebshop-dialog-detial-ebgoodsAddPage');
            $("#ebshop-dialog-detial-ebgoodsAddPage1").dialog({ //弹出新添加窗口
                title:'商品信息修改(按ESC退出)',
                maximizable:true,
                width:530,
                height:260,
                closed:true,
                modal:true,
                cache:false,
                resizable:true,
                href:url,
                onClose:function(){
                    $('#ebshop-dialog-detial-ebgoodsAddPage1').dialog("destroy");
                },
                onLoad:function(){
                    $("#ebshop-form-ebgoodsDetailAddPage").form('load', row);
                    $("#ebshop-goodsid-ebgoodsDetailAddPage").goodsWidget("setValue", row.goodsid);
                    $("#ebshop-goodsid-ebgoodsDetailAddPage").goodsWidget("setText", row.goodsname);
                    $("#ebshop-loading-ebgoodsDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>"+row.goodsid+"</font>");
                }
            });
            $('#ebshop-dialog-detial-ebgoodsAddPage1').dialog("open");
        }
    }

    var $ebgoodslist = null;
    var insertIndex = undefined;
    function addEbgoodsDetail(){ //添加新数据确定后操作，
        if($ebgoodslist == null){
            return false;
        }
        var flag = $("#ebshop-form-ebgoodsDetailAddPage").form('validate');
        if(flag==false){
            return false;
        }
        var form = $("#ebshop-form-ebgoodsDetailAddPage").serializeJSON();
        var goodsJson = $("#ebshop-goodsid-ebgoodsDetailAddPage").goodsWidget('getObject');
        var rowIndex = 0;
        var rows = $ebgoodslist.datagrid('getRows');
        var updateFlag = false;
        for(var i=0; i<rows.length; i++){
            var rowJson = rows[i];
            if(rowJson.goodsid==goodsJson.id){
                rowIndex = i;
                updateFlag =  true;
                break;
            }
        }
        var num = 0;
        for(var i=0; i<rows.length; i++){
            var rowJson = rows[i];
            if(!isObjectEmpty(rowJson)){
                num++;
            }
        }
        insertIndex = num-1;
        if(rowIndex == rows.length - 1){
            $ebgoodslist.datagrid('appendRow',{}); //如果是最后一行则添加一新行
        }
        if(updateFlag){
            $.messager.alert("提醒", "此商品已经添加！");
            return false;
        }
        if(insertIndex == undefined){
            $ebgoodslist.datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
            insertIndex = rowIndex;
        }
        else{
            insertIndex = insertIndex+1;
            $ebgoodslist.datagrid('updateRow',{index:insertIndex,row:form});
        }
        $("#ebshop-form-ebgoodsDetailAddPage").form("clear");
        $("#ebshop-ismian-ebgoodsDetailAddPage").val("0");
        $("#ebshop-goodsid-ebgoodsDetailAddPage").focus();
        countTotal();
    }

    function editEbgoodsDetail(){
        if($ebgoodslist == null){
            return false;
        }
        var flag = $("#ebshop-form-ebgoodsDetailAddPage").form('validate');
        if(flag==false){
            return false;
        }
        var row = $ebgoodslist.datagrid('getSelected');
        var rowIndex = $ebgoodslist.datagrid('getRowIndex', row);
        var form = $("#ebshop-form-ebgoodsDetailAddPage").serializeJSON();
        $ebgoodslist.datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
        $("#ebshop-dialog-detial-ebgoodsAddPage1").dialog('close');
        countTotal();
    }

    function removeEbgoodsDetail(){
        if($ebgoodslist == null){
            return false;
        }
        var row = $ebgoodslist.datagrid('getSelected');
        if(row == null){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $.messager.confirm("提醒","确定删除该商品明细?",function(r) {
            if (r) {
                var rowIndex = $ebgoodslist.datagrid('getRowIndex', row);
                $ebgoodslist.datagrid('deleteRow', rowIndex);
                countTotal();
                var rows = $ebgoodslist.datagrid('getRows');
                insertIndex = insertIndex - 1;
                if(insertIndex == -1){
                    insertIndex = undefined;
                }
            }
        });
    }

    function countTotal(){
        var amount = 0;
        var rows = $ebgoodslist.datagrid('getRows');
        for(var i=0;i<rows.length;i++){
            var obj = rows[i];
            if(!isObjectEmpty(obj)){
                amount = Number(amount) + Number(obj.amount);
            }
        }
        $("#ebshop-amount-ebgoodsAddPage").numberbox('setValue',amount);
    }

    function unitnumChange(){ //数量改变方法
        var goodsid = $("input[name=goodsid]").val();
        var unitnum = $("#ebshop-unitnum-ebgoodsDetailAddPage").val();
        var price = $("#ebshop-price-ebgoodsDetailAddPage").val();

        var ret = ebgoods_AjaxConn({goodsid:goodsid,unitnum:unitnum,price:price},'ebgoods/getEbgoodsRowUnitnum.do');
        var json = $.parseJSON(ret);
        if(!isObjectEmpty(json)){
            $("input[name=auxnumdetail]").val(json.auxnumdetail);
            $("#ebshop-amount-ebgoodsDetailAddPage").numberbox('setValue',json.amount);
        }
    }
    function priceChange(){ //1含税单价或2未税单价改变计算对应数据
        var price = $( '#ebshop-price-ebgoodsDetailAddPage').val();
        var unitnum = $("#ebshop-unitnum-ebgoodsDetailAddPage").val();
        var amount = price * unitnum;
        $("#ebshop-amount-ebgoodsDetailAddPage").numberbox('setValue',amount);
    }

    //加载控件项
    function loadwidgetdown(){
        //卖家昵称
        $("#ebshop-sellernick-ebgoodsAddPage").widget({
            width:150,
            referwid:'RL_T_EB_SELLER',
            singleSelect:true
        });
    }

    $.extend($.fn.validatebox.defaults.rules, {
        validId:{//编号唯一性,最大长度
            validator:function(value,param){
                var pattern=/[`~!@#$%^&*()+<>?:"{},.\/;'[\]]/im;
                if(!pattern.test(value)){
                    var ret = ebgoods_AjaxConn({id:value},'ebgoods/isRepeatEbGoodsId.do');//true 重复，false 不重复
                    var retJson = $.parseJSON(ret);
                    if(retJson.flag){
                        $.fn.validatebox.defaults.rules.validId.message = '编号重复, 请重新输入!';
                        return false;
                    }
                }
                else{
                    $.fn.validatebox.defaults.rules.validId.message = '不能输入特殊字符串!';
                    return false;
                }
                return true;
            },
            message:''
        }
    });

    var ebgoodsListJson = $("#ebshop-datagrid-ebgoodsAddPage").createGridColumnLoad({
        frozenCol : [[]],
        commonCol: [[
            {field:'goodsid',title:'商品编码',width:60,align:' left'},
            {field:'goodsname', title:'商品名称', width:250,align:'left'},
            {field:'unitid', title:'单位',width:60,align:'left',
                formatter:function(value,rowData,rowIndex){
                    return rowData.unitname;
                }
            },
            {field:'auxunitid', title:'辅单位',width:60,align:'left',hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.auxunitname;
                }
            },
            {field:'auxnumdetail', title:'辅数量',width:60,align:'left',hidden:true},
            {field:'unitnum', title:'数量',width:80,align:'right',
                formatter:function(value,row,index){
                    return formatterNum(value);
                }
            },
            {field:'price', title:'单价',width:80,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'amount', title:'金额',width:80,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'rate', title:'占库存比重',width:90,align:'right',
                formatter:function(value,row,index){
                    if("" != value && 0 != formatterNum(value)){
                        return formatterNum(value)+"%";
                    }
                }
            },
            {field:'ismian', title:'是否主商品',width:70,align:'left',
                formatter:function(value,row,index){
                    if("1" == value){
                        return "是";
                    }else if("0" == value){
                        return "否";
                    }
                }
            },
            {field:'remark', title:'备注',width:150,align:'left'}
        ]]
    });

    $(function(){
        var queryJSON = $("#ebshop-queryForm-ebgoodsListPage").serializeJSON();

        //加载按钮
        $("#ebshop-buttons-ebgoodsListPage").buttonWidget({
            initButton:[
                {},
                <security:authorize url="/ebgoods/ebgoodsAddBtn.do">
                {
                    type:'button-add',//新增
                    handler:function(){
                        var url = "ebgoods/showEbgoodsAddPage.do";
                        refreshLayout("电商商品【新增】", url);
                    }
                },
                </security:authorize>
                <security:authorize url="/ebgoods/ebgoodsEditBtn.do">
                {
                    type:'button-edit',//修改
                    handler:function(){
                        var row = $("#ebshop-datagrid-ebgoodsListPage").datagrid('getSelected');
                        if(row == null){
                            $.messager.alert("提醒","请选择商品!");
                            return false;
                        }
                        var flag = isDoLockData(row.id,"t_eb_goods");
                        if(!flag){
                            $.messager.alert("警告","该数据正在被其他人操作，暂不能修改!");
                            return false;
                        }
                        var url = "ebgoods/showEbgoodsEditPage.do?id="+row.id;
                        refreshLayout("电商商品【修改】", url);
                    }
                },
                </security:authorize>
                <security:authorize url="/ebgoods/ebgoodsDeleteBtn.do">
                {
                    type:'button-delete',//删除
                    handler:function(){
                        var rows = $("#ebshop-datagrid-ebgoodsListPage").datagrid('getChecked');
                        if(rows.length == 0){
                            $.messager.alert("提醒","请选择商品!");
                            return false;
                        }
                        $.messager.confirm("提醒","是否确定删除商品?",function(r){
                            if(r){
                                var idStr = "";
                                for(var i=0;i<rows.length;i++){
                                    idStr += rows[i].id + ",";
                                }
                                loading("删除中..");
                                $.ajax({
                                    url :'ebgoods/deleteEbgoods.do',
                                    type:'post',
                                    dataType:'json',
                                    data:{idStr:idStr},
                                    success:function(retJSON){
                                        loaded();
                                        if(retJSON.flag){
                                            $.messager.alert("提醒",""+retJSON.userNum+"条记录被引用,不允许删除;<br/>"+retJSON.lockNum+"条记录网络互斥,不允许删除;<br/>删除成功"+retJSON.num+"条记录;");
                                            var queryJSON = $("#ebshop-queryForm-ebgoodsListPage").serializeJSON();
                                            $("#ebshop-datagrid-ebgoodsListPage").datagrid("load",queryJSON);
                                            $("#ebshop-datagrid-ebgoodsListPage").datagrid('clearSelections');
                                            $("#ebshop-datagrid-ebgoodsListPage").datagrid('clearChecked');
                                        }
                                        else{
                                            $.messager.alert("提醒",""+retJSON.userNum+"条记录被引用,不允许删除;<br/>"+retJSON.lockNum+"条记录网络互斥,不允许删除;<br/>删除成功"+retJSON.num+"条记录;");
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/ebgoods/ebgoodsCopyBtn.do">
                {
                    type:'button-copy',//复制
                    handler:function(){
                        var row = $("#ebshop-datagrid-ebgoodsListPage").datagrid('getSelected');
                        if(row == null){
                            $.messager.alert("提醒","请选择一个商品!");
                            return false;
                        }
                        var url = "ebgoods/showEbgoodsCopyPage.do?id="+row.id;
                        refreshLayout("电商商品【复制】", url);
                    }
                },
                </security:authorize>
                <security:authorize url="/ebgoods/ebgoodsViewBtn.do">
                {
                    type:'button-view',//查看
                    handler:function(){
                        var row = $("#ebshop-datagrid-ebgoodsListPage").datagrid('getSelected');
                        if(row == null){
                            $.messager.alert("提醒","请选择一个商品!");
                            return false;
                        }
                        var url = "ebgoods/showEbgoodsViewPage.do?id="+row.id;
                        refreshLayout("电商商品【查看】", url);
                    }
                },
                </security:authorize>
                <security:authorize url="/ebgoods/ebgoodsEnableBtn.do">
                {
                    type:'button-open',//启用
                    handler:function(){
                        var rows = $("#ebshop-datagrid-ebgoodsListPage").datagrid('getChecked');
                        if(rows.length == 0){
                            $.messager.alert("提醒","请勾选商品!");
                            return false;
                        }
                        $.messager.confirm("提醒","是否确定启用商品?",function(r){
                            if(r){
                                var idStr = "";
                                for(var i=0;i<rows.length;i++){
                                    idStr += rows[i].id + ",";
                                }
                                loading("启用中..");
                                $.ajax({
                                    url :'ebgoods/enableEbgoods.do',
                                    type:'post',
                                    dataType:'json',
                                    data:{idStr:idStr},
                                    success:function(retJSON){
                                        loaded();
                                        if(retJSON.flag){
                                            $.messager.alert("提醒","启用无效"+retJSON.invalidNum+"条记录;<br/>启用成功"+retJSON.num+"条记录;");
                                            var queryJSON = $("#ebshop-queryForm-ebgoodsListPage").serializeJSON();
                                            $("#ebshop-datagrid-ebgoodsListPage").datagrid("load",queryJSON);
                                            $("#ebshop-datagrid-ebgoodsListPage").datagrid('clearSelections');
                                            $("#ebshop-datagrid-ebgoodsListPage").datagrid('clearChecked');
                                        }
                                        else{
                                            $.messager.alert("提醒","启用无效"+retJSON.invalidNum+"条记录;<br/>启用失败"+retJSON.num+"条记录;");
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/ebgoods/ebgoodsDisableBtn.do">
                {
                    type:'button-close',//禁用
                    handler:function(){
                        var rows = $("#ebshop-datagrid-ebgoodsListPage").datagrid('getChecked');
                        if(rows.length == 0){
                            $.messager.alert("提醒","请勾选商品!");
                            return false;
                        }
                        $.messager.confirm("提醒","是否确定禁用商品?",function(r){
                            if(r){
                                var idStr = "";
                                for(var i=0;i<rows.length;i++){
                                    idStr += rows[i].id + ",";
                                }
                                loading("禁用中..");
                                $.ajax({
                                    url :'ebgoods/disableEbgoods.do',
                                    type:'post',
                                    dataType:'json',
                                    data:{idStr:idStr},
                                    success:function(retJSON){
                                        loaded();
                                        if(retJSON.flag){
                                            $.messager.alert("提醒",""+retJSON.invalidNum+"条记录状态不允许禁用;<br/>禁用成功"+retJSON.num+"条记录;");
                                            var queryJSON = $("#ebshop-queryForm-ebgoodsListPage").serializeJSON();
                                            $("#ebshop-datagrid-ebgoodsListPage").datagrid("load",queryJSON);
                                            $("#ebshop-datagrid-ebgoodsListPage").datagrid('clearSelections');
                                            $("#ebshop-datagrid-ebgoodsListPage").datagrid('clearChecked');
                                        }
                                        else{
                                            $.messager.alert("提醒",""+retJSON.invalidNum+"条记录状态不允许禁用;<br/>禁用失败"+retJSON.num+"条记录;");
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/ebgoods/ebgoodsImportBtn.do">
                {
                    type:'button-import',//导入
                    attr:{
//                        type:'importUserdefined',
//                        importparam:'商品编码、商品名称、条件码、商品品牌、默认供应商、主单位、默认仓库、默认税种必填',//参数描述
//                        version:'1',//导入页面显示哪个版本1：不显示，2：简化版或合同版，3：Excel文件或瑞家txt导入，4：Excel文件或三和txt导入
//                        url:'ebgoods/importEbgoodsListData.do',
//                        onClose: function(){ //导入成功后窗口关闭时操作，
//                            $("#ebshop-datagrid-ebgoodsListPage").datagrid('reload');	//更新列表
//                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/ebgoods/ebgoodsExportBtn.do">
                {
                    type:'button-export',//导出
                    attr:{
//                        datagrid: "#ebshop-datagrid-ebgoodsListPage",
//                        queryForm: "#ebshop-queryForm-ebgoodsListPage",
//                        type:'exportUserdefined',
//                        name:'电商商品列表',
//                        url:'ebgoods/exportEbgoodsListData.do'
                    }
                },
                </security:authorize>
            ],
            buttons:[
                {},
                <security:authorize url="/ebgoods/ebgoodsListintBtn.do">
                {
                    id:'listint',
                    name:'上架',
                    iconCls:'button-add',
                    handler:function(){
                        var rows = $("#ebshop-datagrid-ebgoodsListPage").datagrid('getChecked');
                        if(rows.length == 0){
                            $.messager.alert("提醒","请勾选商品!");
                            return false;
                        }
                        $.messager.confirm("提醒","是否确定上架商品?",function(r){
                            if(r){
                                var idStr = "",unidStr = "";
                                for(var i=0;i<rows.length;i++){
                                    if("0" == rows[i].islisting){
                                        if(idStr == ""){
                                            idStr = rows[i].id;
                                        }else{
                                            idStr += "," + rows[i].id;
                                        }
                                    }else{
                                        if(unidStr == ""){
                                            unidStr = rows[i].id;
                                        }else{
                                            unidStr += "," + rows[i].id;;
                                        }
                                        var index = $("#ebshop-datagrid-ebgoodsListPage").datagrid('getRowIndex',rows[i]);
                                        $("#ebshop-datagrid-ebgoodsListPage").datagrid('uncheckRow',index);
                                    }
                                }
                                if(unidStr != ""){
                                    $.messager.alert("提醒","电商商品 编号：" + unidStr + " 已上架，无需重复操作；");
                                }
                                if(idStr != ""){
                                    loading("上架中..");
                                    $.ajax({
                                        url :'ebtrade/doListintEbgoods.do',
                                        type:'post',
                                        dataType:'json',
                                        data:{idStr:idStr},
                                        success:function(retJSON){
                                            loaded();
                                            if(retJSON.msg != ""){
                                                $.messager.alert("提醒",retJSON.msg);
                                                $("#ebshop-datagrid-ebgoodsListPage").datagrid('reload');
                                            }
                                        },
                                        error:function(XMLHttpRequest, textStatus, errorThrown){
                                            loaded();
                                            $.messager.alert("提醒",errorThrown);
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/ebgoods/ebgoodsDelistingBtn.do">
                {
                    id:'delisting',
                    name:'下架',
                    iconCls:'button-delete',
                    handler:function(){
                        var rows = $("#ebshop-datagrid-ebgoodsListPage").datagrid('getChecked');
                        if(rows.length == 0){
                            $.messager.alert("提醒","请勾选商品!");
                            return false;
                        }
                        $.messager.confirm("提醒","是否确定下架商品?",function(r){
                            if(r){
                                var idStr = "",unidStr = "";
                                for(var i=0;i<rows.length;i++){
                                    if("1" == rows[i].islisting){
                                        if(idStr == ""){
                                            idStr = rows[i].id;
                                        }else{
                                            idStr += "," + rows[i].id;
                                        }
                                    }else{
                                        if(unidStr == ""){
                                            unidStr = rows[i].id;
                                        }else{
                                            unidStr += "," + rows[i].id;;
                                        }
                                        var index = $("#ebshop-datagrid-ebgoodsListPage").datagrid('getRowIndex',rows[i]);
                                        $("#ebshop-datagrid-ebgoodsListPage").datagrid('uncheckRow',index);
                                    }
                                }
                                if(unidStr != ""){
                                    $.messager.alert("提醒","电商商品 编号：" + unidStr + " 已下架，无需重复操作；");
                                }
                                if(idStr != ""){
                                    loading("下架中..");
                                    $.ajax({
                                        url :'ebtrade/doDelistingEbgoods.do',
                                        type:'post',
                                        dataType:'json',
                                        data:{idStr:idStr},
                                        success:function(retJSON){
                                            loaded();
                                            if(retJSON.msg != ""){
                                                $.messager.alert("提醒",retJSON.msg);
                                                $("#ebshop-datagrid-ebgoodsListPage").datagrid('reload');
                                            }
                                        },
                                        error:function(XMLHttpRequest, textStatus, errorThrown){
                                            loaded();
                                            $.messager.alert("提醒",errorThrown);
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/goodesSimplifyEditMoreBtn.do">
                {
                    id:'addMuti',
                    name:'批量生成',
                    iconCls:'button-add',
                    handler:function(){
                        $('<div id="ebshop-dialog-addmuti-ebgoodsListPage1"></div>').appendTo('#ebshop-dialog-addmuti-ebgoodsListPage');
                        $('#ebshop-dialog-addmuti-ebgoodsListPage1').dialog({
                            maximizable:true,
                            resizable:true,
                            title: '批量生成电商商品',
                            fit:true,
                            closed: true,
                            cache: false,
                            href: 'ebgoods/showEbGoodsAddMutiPage.do',
                            modal: true,
                            onClose:function(){
                                $('#ebshop-dialog-addmuti-ebgoodsListPage1').dialog("destroy");
                            }
                        });
                        $("#ebshop-dialog-addmuti-ebgoodsListPage1").dialog("open");
                    }
                },
                </security:authorize>
                {}
            ],
            model:'base',
            type:'multipleList',
            taburl:'/ebgoods/showEdGoodsListPage.do',
            datagrid:'ebshop-datagrid-ebgoodsListPage',
            tname:'t_eb_goods',
            id:''
        });

        $(document).keydown(function(event){//alert(event.keyCode);
            switch(event.keyCode){
                case 27: //Esc
                    $("#ebshop-dialog-detial-ebgoodsAddPage1").dialog('close');
                    break;
            }
        });

        $(document).bind('keydown', 'ctrl+enter',function (){
            $("#ebshop-savegoon-ebgoodsDetailAddPage").focus();
            $("#ebshop-savegoon-ebgoodsDetailAddPage").trigger("click");
        });
        $(document).bind('keydown', '+',function (){
            $("#ebshop-savegoon-ebgoodsDetailAddPage").focus();
            setTimeout(function(){
                $("#ebshop-savegoon-ebgoodsDetailAddPage").trigger("click");
            },300);
            return false;
        });

        var ebgoodsJson = $("#ebshop-datagrid-ebgoodsListPage").createGridColumnLoad({
            name:'t_eb_goods',
            frozenCol : [[{field:'ck',checkbox:true}]],
            commonCol : [[
                {field:'id',title:'编码',resizable:true,sortable:true},
                {field:'name',title:'名称',width:120,sortable:true},
                {field:'etype',title:'电商类型',width:80,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.etypename;
                    }
                },
                {field:'seller_nick',title:'卖家昵称',width:80,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.seller_nickname;
                    }
                },
                {field:'sku_id',title:'sku编号',width:80,sortable:true},
                {field:'num_iid',title:'电商编码',width:80,sortable:true},
                {field:'properties',title:'sku属性',width:80,sortable:true},
                {field:'price',title:'金额',resizable:true,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'usablenum', title:'可用量',width:80,align:'left',
                    formatter:function(value,row,index){
                        if(value != 0){
                            return formatterNum(value);
                        }else{
                            return value;
                        }
                    }
                },
                {field:'islisting', title:'是否上架',width:60,align:'left',
                    formatter:function(value,row,index){
                        if("1" == value){
                            return "是";
                        }else if("0" == value){
                            return "否";
                        }
                    }
                },
                {field:'state',title:'状态',width:50,sortable:true,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.statename;
                    }
                },
                {field:'remark',title:'备注',width:100,sortable:true}
            ]]
        });

        $('#ebshop-datagrid-ebgoodsListPage').datagrid({
            authority:ebgoodsJson,
            frozenColumns:ebgoodsJson.frozen,
            columns:ebgoodsJson.common,
            fit:true,
            toolbar:'#ebshop-toobar-ebgoodsListPage',
            method:'post',
            rownumbers:true,
            pagination:true,
            idField:'id',
            pageSize:100,
            showFooter:true,
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            queryParams:queryJSON,
            url:'ebgoods/getEbgoodsList.do',
            onDblClickRow:function(rowIndex, rowData){
                <security:authorize url="/ebgoods/ebgoodsViewBtn.do">
                var url = "ebgoods/showEbgoodsViewPage.do?id="+rowData.id;
                refreshLayout("电商商品【查看】", url);
                </security:authorize>
            }
        }).datagrid("columnMoving");

        //查询
        $("#ebshop-queay-ebgoodsListPage").click(function(){
            var queryJSON = $("#ebshop-queryForm-ebgoodsListPage").serializeJSON();
            $("#ebshop-datagrid-ebgoodsListPage").datagrid("load",queryJSON);
        });

        //重置按钮
        $("#ebshop-resetQueay-ebgoodsListPage").click(function(){
            $("#ebshop-queryForm-ebgoodsListPage")[0].reset();
            $("#ebshop-datagrid-ebgoodsListPage").datagrid("load",{});
        });
    });
    </script>
</body>
</html>
