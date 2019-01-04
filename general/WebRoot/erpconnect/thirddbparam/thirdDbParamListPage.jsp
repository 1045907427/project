<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>第三方账套参数设置</title>
    <%@include file="/include.jsp" %>
    <style type="text/css">
        .div_tr{
            height:20px;padding: 10px;
        }
        .div_td {
            float: left;
            margin-left:10px;
            width: 30%;
            text-align: left;
        }
    </style>
</head>
<body>
<input type="hidden" id="ledger-type--ledgerCommonSetPage"/>
<div class="easyui-layout" data-options="fit:true,border:false" id="ledger-layout--ledgerCommonSetPage">
    <div data-options="region:'north',split:false" style="height: 30px;overflow: hidden">
        <div class="buttonBG" id="ledger-button-ledgerCommonSetPage">
            <security:authorize url="/thirdDb/thirdDbAddBtn.do">
                <a href="javaScript:void(0);" id="ledger-addAccountSet-ledgerCommonSetPage" class="easyui-linkbutton button-list"
                   data-options="plain:true,iconCls:'button-add'">新增帐套</a>
            </security:authorize>
            <security:authorize url="/thirdDb/thirdDbEditBtn.do">
                <a href="javaScript:void(0);" id="ledger-edit-ledgerCommonSetPage" class="easyui-linkbutton button-list"
                   data-options="plain:true,iconCls:'button-edit'">修改</a>
            </security:authorize>
            <security:authorize url="/thirdDb/thirdDbSaveBtn.do">
                <a href="javaScript:void(0);" id="ledger-save-ledgerCommonSetPage" class="easyui-linkbutton button-list"
                   data-options="plain:true,iconCls:'button-save'">保存</a>
            </security:authorize>
            <security:authorize url="/thirdDb/thirdDbOpenBtn.do">
                <a href="javaScript:void(0);" id="ledger-open-ledgerCommonSetPage" class="easyui-linkbutton button-list"
                   data-options="plain:true,iconCls:'button-edit'">禁用/启用</a>
            </security:authorize>
            <security:authorize url="/thirdDb/thirdDbDelBtn.do">
                <a href="javaScript:void(0);" id="ledger-delete-ledgerCommonSetPage" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'" title="删除">删除</a>
            </security:authorize>
            <security:authorize url="/thirdDb/addSalesAccountVouch.do">
                <a href="javaScript:void(0);" id="erpconnect-button-sales" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">生成销售凭证</a>
            </security:authorize>
            <security:authorize url="/thirdDb/syncAccountCodeFromERP.do">
                <a href="javaScript:void(0);" id="erpconnect-button-synccode" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">同步会计科目</a>
            </security:authorize>
            <security:authorize url="/thirdDb/syncDsignFromERP.do">
                <a href="javaScript:void(0);" id="erpconnect-button-syncsign" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">同步凭证类别</a>
            </security:authorize>
            <%--<security:authorize url="/ledger/accountSetChangeBtn.do">--%>
            <%--<a href="javaScript:void(0);" id="ledger-change-ledgerCommonSetPage" class="easyui-linkbutton button-list"--%>
            <%--data-options="plain:true,iconCls:'button-oppaudit'">切换帐套</a>--%>
            <%--</security:authorize>--%>
            <%--<a style="float: right"> 当前帐套：${accountname}&nbsp;&nbsp;&nbsp;</a>--%>

        </div>
    </div>
    <div title="账套列表" data-options="region:'west',split:true,collapsible:false" style="width:190px;">
        <table id="ledger-VoucherSet-ledgerCommonSetPage"></table>
    </div>
    <div data-options="region:'center',split:true">
        <div id="ledgerCommonSetPage-panel-detail"></div>
    </div>
</div>
<div id="salesAccountVouch-dialog" class="easyui-dialog" title="生成销售凭证" style="width:400px;height:260px;"
     data-options="closed:true,resizable:true,modal:true,buttons:[{
                        text:'确定',
                        handler:function(){
                            addSalesAccountVouch();
                        }
                    }]">
    <div style="padding: 20px;">
        账套名称：<select id="sales-customer-databaseid" onchange="loadSubject()"  style="width: 200px;" name="dbid">
        <c:forEach items="${dbList }" var="list">
            <option value="${list.id }">${list.dbasename }</option>
        </c:forEach>
    </select>&nbsp;<br/>
        凭证类别：<select id="sales-customer-sign"  style="width: 200px;" name="sign">
        <c:forEach items="${signList }" var="list">
            <option value="${list.id }">${list.codesign }</option>
        </c:forEach>
    </select><br/>
        开始日期：<input type="text" id="salesAccountVouch-begindate" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" style="width: 200px;" value="${today}" /><br/>
        结束日期：<input type="text" id="salesAccountVouch-enddate" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" style="width: 200px;" value="${today}"/><br/>
        操作日期：<input type="text" id="salesAccountVouch-operdate" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style="width: 200px;" value="${currentDate}"/><br/>
        注：生成的销售凭证数据是该日期的销售数据（同销售情况报表中的数据）
    </div>
</div>
<div id="ledger-changeAccountSet-dialog"></div>
<div id="ledger-addAccountSet-dialog"></div>
<div id="erp-dialog-chooseAccount"></div>
</body>
<script type="text/javascript">
    function refreshLayout(title, url,type){
        $("#ledger-layout--ledgerCommonSetPage").layout('remove','center').layout('add',{
            region: 'center',
            title: title,
            href:url
        });
        $("#ledger-type--ledgerCommonSetPage").val(type);
    }

    $(function () {

        $('#ledger-VoucherSet-ledgerCommonSetPage').datagrid({
            columns:[[
                {field: 'id', title: '编号', width: 60, align: 'left', hidden: true},
                {field: 'dbname', title: '数据库名', width: 60, align: 'left', hidden: true},
                {field:'dbasename',title:'账套名称',width:80,sortable:true},
                {field:'state',title:'状态',width:60,sortable:true,
                    formatter: function(value, row, index) {
                        if('0'==value){
                            return '禁用';
                        }else if('1'==value){
                            return '启用';
                        }
                    }
                }
            ]],
            fit:true,
            method:'post',
            rownumbers:true,
            idField:'id',
            singleSelect:true,
            url:'thirdDb/getThirdDbList.do',
            onClickRow:function(rowIndex, rowData){
                refreshLayout("账套明细【详情】",'thirdDb/showThirdDbParamViewPage.do?id='+rowData.id,'view');
            }
        });

        $(".groupcols").click(function(){
            var cols = "";
            $("#ledger-query-groupcols").val(cols);
            $(".groupcols").each(function(){
                if($(this).attr("checked")){
                    if(cols==""){
                        cols = $(this).val();
                    }else{
                        cols += ","+$(this).val();
                    }
                    $("#ledger-query-groupcols").val(cols);
                }
            });
        });

        //保存
        $("#ledger-save-ledgerCommonSetPage").click(function () {
            var type = $("#ledger-type--ledgerCommonSetPage").val();
            if("view" == type){
                $.messager.alert("提醒","编辑状态下才允许修改，请先点击修改按钮!");
            }else if("edit" == type){
                editAccountSet();
            }else{
                openCopyPage();
            }
        });


        //切换帐套
        $("#ledger-change-ledgerCommonSetPage").click(function () {
            $("#ledger-changeAccountSet-dialog").dialog({
                title:'总账帐套切换',
                maximizable:false,
                width:300,
                height:180,
                closed:true,
                modal:true,
                cache:false,
                resizable:true,
                href: 'ledger/showAccountSetChangePage.do'
            });
            $("#ledger-changeAccountSet-dialog").dialog("open");
        });

        //修改
        $("#ledger-edit-ledgerCommonSetPage").click(function () {
            var row = $('#ledger-VoucherSet-ledgerCommonSetPage').datagrid('getSelected');
            if(row == null){
                $.messager.alert("提醒","请选择一个账套!");
                return false;
            }else{
                refreshLayout("账套明细【修改】",'thirdDb/showThirdDbParamEditPage.do?id='+row.id,'edit');
            }
        });

        //新增账套
        $("#ledger-addAccountSet-ledgerCommonSetPage").click(function () {
            $("#ledger-type--ledgerCommonSetPage").val('add');
            $('#ledger-addAccountSet-dialog').dialog({
                title: '新增账套',
                width: 300,
                height: 410,
                closed: false,
                cache: false,
                modal: true,
                method: 'post',
                href: 'thirdDb/showThirdDbAddPage.do',
                onClose: function () {

                },
                buttons:[
                    {
                        iconCls: 'button-save',
                        text:'保存',
                        handler:function(){
                            $("#ledger-form-thirdDbAddPage").submit();
                        }
                    },
                    {
                        iconCls: 'button-close',
                        text:'关闭',
                        handler:function(){
                            $('#ledger-addAccountSet-dialog').dialog("close")
                        }
                    }
                ]
            });

        });



        $("#ledger-open-ledgerCommonSetPage").click(function () {
            var row = $('#ledger-VoucherSet-ledgerCommonSetPage').datagrid('getSelected');
            if(row == null){
                $.messager.alert("提醒","请选择一个账套!");
                return false;
            }
            loading("操作中..");
            $.ajax({
                url: 'thirdDb/updateThirdDbState.do',
                type: 'post',
                dataType: 'json',
                data:{id:row.id},
                success: function (json) {
                    loaded();
                    if (json.flag) {
                        $.messager.alert("提醒", json.msg);
                        $('#ledger-VoucherSet-ledgerCommonSetPage').datagrid('reload');
                    } else {
                        $.messager.alert("提醒",json.msg);
                    }
                },
                error: function () {
                    loaded();
                    $.messager.alert("错误", "操作失败");
                }
            });
        });

        $("#ledger-delete-ledgerCommonSetPage").click(function () {
            var row = $('#ledger-VoucherSet-ledgerCommonSetPage').datagrid('getSelected');
            if(row == null){
                $.messager.alert("提醒","请选择一个账套!");
                return false;
            }
            loading("操作中..");
            $.ajax({
                url: 'thirdDb/deleteThirdDb.do',
                type: 'post',
                dataType: 'json',
                data:{id:row.id,dbname:row.dbname,dbasename:row.dbasename},
                success: function (json) {
                    loaded();
                    if (json.flag) {
                        $.messager.alert("提醒", "删除成功");
                        $('#ledger-VoucherSet-ledgerCommonSetPage').datagrid('reload','thirdDb/getThirdDbList.do');
                    } else {
                        $.messager.alert("提醒","删除失败");
                    }
                },
                error: function () {
                    loaded();
                    $.messager.alert("错误", "删除失败");
                }
            });
        })

        //生成销售凭证
        $("#erpconnect-button-sales").click(function(){
            <%--var singList = '${signList}';--%>
            <%--if(singList == "" || singList == undefined){--%>
                <%--$.messager.alert("提醒","请先选择接入的系统和参数再进行凭证添加！");--%>
            <%--}else{--%>
                <%--$("#salesAccountVouch-dialog").dialog("open");--%>
            <%--}--%>

            $("#salesAccountVouch-dialog").dialog("open");
        });
        loadSubject();
    });

    //选择账套同步当前系统数据到用友
    function chooseAccount(type) {
        $('<div id="erp-dialog-chooseAccount-content"></div>').appendTo('#erp-dialog-chooseAccount');
        $("#erp-dialog-chooseAccount-content").dialog({
            title: '选择账套',
            width: 350,
            height: 160,
            closed: false,
            modal: true,
            cache: false,
            maximizable: true,
            resizable: true,
            href: 'erpconnect/showChooseAccountPage.do?type='+type,
            onClose:function () {
                $("#erp-dialog-chooseAccount-content").dialog("destroy");
            }
        });
    }
    function addSalesAccountVouch(){
        loading("生成中..");
        var databaseid = $("#sales-customer-databaseid").val();
        var begindate = $("#salesAccountVouch-begindate").val();
        var enddate = $("#salesAccountVouch-enddate").val();
        var operdate = $("#salesAccountVouch-operdate").val();
        var sign = $("#sales-customer-sign").val();
        $.ajax({
            url: 'erpconnect/addSalesAccountThirdVouch.do',
            type: 'post',
            dataType: 'json',
            data:{begindate:begindate,enddate:enddate,operdate:operdate,sign:sign,dbid:databaseid},
            success: function (json) {
                loaded();
                if (json.flag) {
                    $.messager.alert("提醒", "生成成功。");
                    $("#salesAccountVouch-dialog").dialog("close");
                } else {
                    $.messager.alert("提醒", "生成失败。"+json.msg);
                    $("#salesAccountVouch-dialog").dialog("close");
                }
            },
            error: function () {
                loaded();
                $.messager.alert("错误", "生成失败");
            }
        });
    }
    //同步会计科目
    $("#erpconnect-button-synccode").click(function(){
        chooseAccount(1);
    });
    //同步会计类别
    $("#erpconnect-button-syncsign").click(function(){
        chooseAccount(2);
    });

    function loadSubject(){
        var dbid=$("#sales-customer-databaseid").val();
        $.ajax({
            url :"thirdDb/getThirdDbParam.do",
            data:{
                dbid:dbid
            },
            type:'post',
            dataType:'json',
            async: false,
            success:function(json){
                var signList=json.signList;
                $("#sales-customer-sign").empty();
                for(var i=0;i<signList.length;i++){
                    var tdstr = '<option value="'+signList[i].id+'">'+signList[i].codesign+'</option>';
                    $(tdstr).appendTo("#sales-customer-sign");
                }
            }
        });
    }


</script>
</html>
