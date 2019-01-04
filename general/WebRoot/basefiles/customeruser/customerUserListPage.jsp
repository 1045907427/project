<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>客户用户</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/jquery.excel.js"></script>
</head>

<body>
<table id="customerUser-table-showCustomerUserList"></table>
<div id="customerUser-query-showCustomerUserList" style="padding:0px;height:auto">
    <div class="buttonBG">
        <security:authorize url="/basefiles/showCustomerUserEditPage.do">
            <a href="javaScript:void(0);" id="customerUser-edit-editUser" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-edit'" title="绑定客户">绑定客户</a>
        </security:authorize>
        <security:authorize url="/basefiles/showCustomerUserRemovePage.do">
            <a href="javaScript:void(0);" id="customerUser-remove-removeUser" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-delete'" title="删除客户用户">删除</a>
        </security:authorize>
        <security:authorize url="/basefiles/showCustomerUserEnablePage.do">
            <a href="javaScript:void(0);" id="customerUser-enable-enableUser" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-open'" title="启用客户用户">启用</a>
        </security:authorize>
        <security:authorize url="/basefiles/showCustomerUserRemovePage.do">
            <a href="javaScript:void(0);" id="customerUser-disable-disableUser" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-close'" title="禁用客户用户">禁用</a>
        </security:authorize>
        <security:authorize url="/basefiles/showCustomerUserResetPwdPage.do">
            <a href="javaScript:void(0);" id="customerUser-resetPwd-resetPwdUser" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-reset'" title="密码重置">密码重置</a>
        </security:authorize>

        <span id="customerUser-query-advanced"></span>
    </div>
    <div>
        <form action="" id="customerUser-form-userList" method="post">
            <table class="querytable">
                <tr>
                    <td>手机号:</td>
                    <td><input name="mobilephone" style="width:150px" /></td>
                    <td>关联客户:</td>
                    <td><input name="customername" style="width:150px" /></td>
                    <td>状态:</td>
                    <td>
                        <select name="state" style="width: 150px;">
                            <option></option>
                            <option value="2">保存</option>
                            <option value="1">启用</option>
                            <option value="0">禁用</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="4"></td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="customerUser-queay-queryUserList" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="customerUser-queay-reloadUserList" class="button-qr">重置</a>

                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="customerUser-dialog-showCustomerUserList"></div>
<script type="text/javascript">
    var customerUser_AjaxConn = function (Data, Action) {
        var MyAjax = $.ajax({
            type: 'post',
            cache: false,
            url: Action,
            data: Data,
            async: false
        })
        return MyAjax.responseText;
    }
    //列冻结、拖拽、样式保存
    var userListColJson =
        $("#customerUser-table-showCustomerUserList").createGridColumnLoad({
//            name :'sys_user',
            frozenCol : [[
                {field:'ck',checkbox:true,isShow:true}
            ]],
            commonCol : [[
                {field: 'mobilephone', title: '手机号', width: 120, align: 'left', sortable: true},
                {field: 'customerid', title: '关联客户编号', width: 120, align: 'left', sortable: true},
                {field: 'customername', title: '关联客户名称', width: 120, align: 'left', sortable: true},
                {field: 'wechatserid', title: '微信服务号id', width: 150, align: 'left', isShow: true},
                {field:'state',title:'状态',width:40,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.stateName;
                    }
                },
                {field: 'remark', title: '注册备注', width: 150, align: 'left', isShow: true},
                {field: 'addtime', title: '制单时间', width: 130, sortable: true},
            ]]
        });

    $(function(){
        $('#customerUser-table-showCustomerUserList').datagrid({
            //判断是否开启表头菜单
            authority:userListColJson,
            frozenColumns: userListColJson.frozen,
            columns:userListColJson.common,
            fit:true,
            method:'post',
            title:'',
            rownumbers:true,
            pagination:true,
            idField:'mobilephone',
            sortName:'addtime',
            sortOrder:'asc',
            singleSelect:false,
            pageSize:100,
            checkOnSelect:true,
            selectOnCheck:true,
            toolbar:'#customerUser-query-showCustomerUserList',
            url: 'basefiles/getCustomerUserList.do',
            onDblClickRow:function(rowIndex, rowData){
                var url = "accesscontrol/showCustomerUserInfoPage.do?type=show&userid="+rowData.userid;
                top.addOrUpdateTab(url,'客户用户详情');
            }
        }).datagrid("columnMoving");

    
        //回车事件
        controlQueryAndResetByKey("customerUser-queay-queryUserList","customerUser-queay-reloadUserList");

        //查询
        $("#customerUser-queay-queryUserList").click(function(){
            var queryJSON = $("#customerUser-form-userList").serializeJSON();
            $("#customerUser-table-showCustomerUserList").datagrid("load",queryJSON);
        });
        //重置
        $("#customerUser-queay-reloadUserList").click(function(){
            $("#customerUser-form-userList")[0].reset();
            $("#customerUser-table-showCustomerUserList").datagrid("load",{});
        });

        //绑定客户
        $("#customerUser-edit-editUser").click(function(){
            var customerUserInfoRow = $("#customerUser-table-showCustomerUserList").datagrid('getSelected');
            if(customerUserInfoRow == null){
                $.messager.alert("提醒","请选择客户用户!");
                return false;
            }
            var flag = isDoLockData(customerUserInfoRow.mobilephone,"t_base_sales_customer_user");
            if(!flag){
                $.messager.alert("警告","该数据正在被其他人操作，暂不能修改!");
                return false;
            }
            if(customerUserInfoRow.customerid!=''&& customerUserInfoRow.customerid !=null){
                var url = "basefiles/showCustomerUserEditCustomerPage.do?mobilephone="+customerUserInfoRow.mobilephone;
                refreshLayout("绑定用户[修改]", url);
            }else{
                var url = "basefiles/showCustomerUserAddCustomerPage.do?mobilephone="+customerUserInfoRow.mobilephone;
                refreshLayout("绑定用户[新增]", url);
            }

        });

        //启用按钮
        $("#customerUser-enable-enableUser").click(function(){
            var rows = $("#customerUser-table-showCustomerUserList").datagrid('getChecked');
            if(rows.length == 0){
                $.messager.alert("提醒","请选择客户用户!");
                return false
            }
            var mobilephones = "";
            for(var i=0;i<rows.length;i++){
                if(mobilephones == ""){
                    mobilephones = rows[i].mobilephone;
                }else{
                    mobilephones += "," + rows[i].mobilephone;
                }
            }
            $.messager.confirm('提醒','确定启用该客户用户吗?',function(r){
                if(r){
                    $.ajax({
                        url :'basefiles/enableCustomerUser.do?mobilephones='+mobilephones,
                        type:'post',
                        dataType:'json',
                        async: false,
                        success:function(json){
                            $.messager.alert("提醒",json.msg);
                            $("#customerUser-table-showCustomerUserList").datagrid('reload');
                            $("#customerUser-table-showCustomerUserList").datagrid('clearSelections');
                        }
                    });
                }
            });
        });

        //禁用按钮
        $("#customerUser-disable-disableUser").click(function(){
            var rows = $("#customerUser-table-showCustomerUserList").datagrid('getChecked');
            if(rows.length == 0){
                $.messager.alert("提醒","请选择客户用户!");
                return false
            }
            var mobilephones = "";
            for(var i=0;i<rows.length;i++){
                if(mobilephones == ""){
                    mobilephones = rows[i].mobilephone;
                }else{
                    mobilephones += "," + rows[i].mobilephone;
                }
            }
            $.messager.confirm('提醒','确定禁用该客户用户吗?',function(r){
                if(r){
                    $.ajax({
                        url :'basefiles/disableCustomerUser.do?mobilephones='+mobilephones,
                        type:'post',
                        dataType:'json',
                        async: false,
                        success:function(json){
                            $.messager.alert("提醒",json.msg);
                            $("#customerUser-table-showCustomerUserList").datagrid('reload');
                            $("#customerUser-table-showCustomerUserList").datagrid('clearSelections');
                        }
                    });
                }
            });
        });

        //删除按钮
        $("#customerUser-remove-removeUser").click(function(){
            var rows = $("#customerUser-table-showCustomerUserList").datagrid('getChecked');
            if(rows.length == 0){
                $.messager.alert("提醒","请选择客户用户!");
                return false
            }
            var ids = "";
            for(var i=0;i<rows.length;i++){
                if(ids == ""){
                    ids = rows[i].userid;
                }else{
                    ids += "," + rows[i].userid;
                }
            }
            $.messager.confirm('提醒','确定删除吗?',function(r){
                if (r){
                    $.ajax({
                        url :'accesscontrol/deleteCustomerUser.do?ids='+ids,
                        type:'post',
                        dataType:'json',
                        async: false,
                        success:function(json){
                            $.messager.alert("提醒",json.msg);
                            var queryJSON = $("#customerUser-form-userList").serializeJSON();
                            $("#customerUser-table-showCustomerUserList").datagrid("load",queryJSON);
                            //$("#customerUser-table-showCustomerUserList").datagrid("load",{});
                            //$("#customerUser-table-showCustomerUserList").datagrid('clearSelections');
                        }
                    });
                }
            });
        });

        //密码重置
        $("#customerUser-resetPwd-resetPwdUser").click(function(){
            var rows = $("#customerUser-table-showCustomerUserList").datagrid('getChecked');
            if(rows.length == 0){
                $.messager.alert("提醒","请选择客户用户!");
                return false
            }
            var ids = "";
            for(var i=0;i<rows.length;i++){
                if(ids == ""){
                    ids = rows[i].userid;
                }else{
                    ids += "," + rows[i].userid;
                }
            }
            $.messager.confirm('提醒','确定重置该客户用户密码吗?',function(r){
                if(r){
                    $.ajax({
                        url :'accesscontrol/resetCustomerUserPwd.do?ids='+ids,
                        type:'post',
                        dataType:'json',
                        async: false,
                        success:function(json){
                            if(json.flag){
                                $.messager.alert("提醒","密码重置成功!");
                            }
                            else{
                                $.messager.alert("提醒","密码重置失败!");
                            }
                        }
                    });
                }
            });
        });
    });


    function refreshLayout(title, url) {
        $('<div id="customerUser-dialog-showCustomerUserList1"></div>').appendTo('#customerUser-dialog-showCustomerUserList');
        $('#customerUser-dialog-showCustomerUserList1').dialog({
            maximizable: true,
            resizable: true,
            title: title,
            width: 740,
            height: 450,
            closed: true,
            cache: false,
            href: url,
            modal: true,
            onClose:function(){
                $('#customerUser-dialog-showCustomerUserList1').dialog("destroy");
            }
        });
        $("#customerUser-dialog-showCustomerUserList1").dialog("open");
    }

    //加锁
    function isDoLockData(id, tablename) {
        var flag = false;
        $.ajax({
            url: 'system/lock/isDoLockData.do',
            type: 'post',
            data: {id: id, tname: tablename},
            dataType: 'json',
            async: false,
            success: function (json) {
                flag = json.flag
            }
        });
        return flag;
    }

    function customeruser_save_form_submit(){
        $("#customeruser-form-addCustomerAddPage").form({
            onSubmit: function(){
                loading("提交中..");
            },
            success:function(data){
                loaded();
                var json = $.parseJSON(data);
                if(json.flag){
                    $("#customerUser-dialog-showCustomerUserList1").dialog('close');
                    $.messager.alert("提醒","绑定成功!");
                }else{
                    $.messager.alert("提醒","绑定失败!");
                }
            }
        });
    }
</script>
</body>
</html>
