<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>卖家信息列表页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
    <div class="easyui-layout" data-options="fit:true,border:true">
        <div data-options="region:'north',split:false,border:false">
            <div class="buttonBG" id="ebshop-buttons-ebSellerListPage"></div>
        </div>
        <div data-options="region:'center'">
            <div id="ebshop-toobar-ebSellerListPage" style="padding:0px;">
                <form id="ebshop-queryForm-ebSellerListPage">
                    <table class="querytable">
                        <tr>
                            <td>卖家昵称：</td>
                            <td><input type="text" name="sellernick" style="width:150px"/></td>
                            <td>所属公司：</td>
                            <td><input type="text" name="company" style="width:150px"/></td>
                            <td>联系方式：</td>
                            <td><input type="text" name="phone" style="width:150px"/></td>
                        </tr>
                        <tr>
                            <td>状态：</td>
                            <td>
                                <select name="state" style="width:150px;">
                                    <option></option>
                                    <option value="1">启用</option>
                                    <option value="2">保存</option>
                                    <option value="0">禁用</option>
                                </select>
                            </td>
                            <td colspan="2"></td>
                            <td colspan="2">
                                <a href="javascript:;" id="ebshop-queay-ebSellerListPage"  class="button-qr">查询</a>
                                <a href="javaScript:;" id="ebshop-resetQueay-ebSellerListPage"  class="button-qr">重置</a>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <table id="ebshop-datagrid-ebSellerListPage"></table>
            <div id="ebshop-dialog-ebSellerListPage"></div>
        </div>
    </div>
    <script type="text/javascript">
        var ebSeller_AjaxConn = function (Data, Action) {
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
            $('<div id="ebshop-dialog-ebSellerListPage1"></div>').appendTo('#ebshop-dialog-ebSellerListPage');
            $('#ebshop-dialog-ebSellerListPage1').dialog({
                maximizable:true,
                resizable:true,
                title: title,
                width:300,
                height:250,
                closed: true,
                cache: false,
                href: url,
                modal: true,
                onClose:function(){
                    $('#ebshop-dialog-ebSellerListPage1').dialog("destroy");
                }
            });
            $("#ebshop-dialog-ebSellerListPage1").dialog("open");
        }

        $(function(){
            $.extend($.fn.validatebox.defaults.rules, {
                sellernick:{//编号唯一性,最大长度
                    validator:function(value,param){
                        if(value == $("#ebSeller-oldsellernick").val()){
                            return true;
                        }
                        var pattern=/[`~!@#$%^&*()+<>?:"{},.\/;'[\]]/im;
                        if(!pattern.test(value)){
                            var ret = ebSeller_AjaxConn({sellernick:value},'ebseller/isRepeatSellernick.do');//true 重复，false 不重复
                            var retJson = $.parseJSON(ret);
                            if(retJson.flag){
                                $.fn.validatebox.defaults.rules.sellernick.message = '卖家昵称重复, 请重新输入!';
                                return false;
                            }
                        }
                        else{
                            $.fn.validatebox.defaults.rules.sellernick.message = '不能输入特殊字符串!';
                            return false;
                        }
                        return true;
                    },
                    message:''
                }
            });

            var queryJSON = $("#ebshop-queryForm-ebSellerListPage").serializeJSON();

            //加载按钮
            $("#ebshop-buttons-ebSellerListPage").buttonWidget({
                initButton:[
                    {},
                    <security:authorize url="/ebseller/ebSellerAddBtn.do">
                    {
                        type:'button-add',//新增
                        handler:function(){
                            var url = "ebseller/showEbSellerAddPage.do";
                            refreshLayout("卖家信息【新增】", url);
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/ebseller/ebSellerEditBtn.do">
                    {
                        type:'button-edit',//修改
                        handler:function(){
                            var row = $("#ebshop-datagrid-ebSellerListPage").datagrid('getSelected');
                            if(row == null){
                                $.messager.alert("提醒","请选择卖家信息!");
                                return false;
                            }
                            var flag = isDoLockData(row.id,"t_eb_seller");
                            if(!flag){
                                $.messager.alert("警告","该数据正在被其他人操作，暂不能修改!");
                                return false;
                            }
                            var url = "ebseller/showEbSellerEditPage.do?id="+row.id;
                            refreshLayout("卖家信息【修改】", url);
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/ebseller/ebSellerDeleteBtn.do">
                    {
                        type:'button-delete',//删除
                        handler:function(){
                            var rows = $("#ebshop-datagrid-ebSellerListPage").datagrid('getChecked');
                            if(rows.length == 0){
                                $.messager.alert("提醒","请选择卖家信息!");
                                return false;
                            }
                            $.messager.confirm("提醒","是否确定删除卖家信息?",function(r){
                                if(r){
                                    var ids = "";
                                    for(var i=0;i<rows.length;i++){
                                        if("" == ids){
                                            ids = rows[i].id;
                                        }else{
                                            ids += "," + rows[i].id;
                                        }
                                    }
                                    loading("删除中..");
                                    $.ajax({
                                        url :'ebseller/deleteEbSeller.do',
                                        type:'post',
                                        dataType:'json',
                                        data:{ids:ids},
                                        success:function(retJSON){
                                            loaded();
                                            $.messager.alert("提醒",""+retJSON.userNum+"条记录被引用,不允许删除;<br/>"+retJSON.lockNum+"条记录网络互斥,不允许删除;<br/>删除成功"+retJSON.sucnum+"条记录;<br/>删除失败"+retJSON.unsucnum+"条记录;");
                                            $("#ebshop-datagrid-ebSellerListPage").datagrid("reload");
                                        }
                                    });
                                }
                            });
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/ebseller/ebSellerCopyBtn.do">
                    {
                        type:'button-copy',//复制
                        handler:function(){
                            var row = $("#ebshop-datagrid-ebSellerListPage").datagrid('getSelected');
                            if(row == null){
                                $.messager.alert("提醒","请选择一个卖家信息!");
                                return false;
                            }
                            var url = "ebseller/showEbSellerCopyPage.do?id="+row.id;
                            refreshLayout("卖家信息【复制】", url);
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/ebseller/ebSellerViewBtn.do">
                    {
                        type:'button-view',//查看
                        handler:function(){
                            var row = $("#ebshop-datagrid-ebSellerListPage").datagrid('getSelected');
                            if(row == null){
                                $.messager.alert("提醒","请选择一个卖家信息!");
                                return false;
                            }
                            var url = "ebseller/showEbSellerViewPage.do?id="+row.id;
                            refreshLayout("卖家信息【查看】", url);
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/ebseller/ebSellerEnableBtn.do">
                    {
                        type:'button-open',//启用
                        handler:function(){
                            var rows = $("#ebshop-datagrid-ebSellerListPage").datagrid('getChecked');
                            if(rows.length == 0){
                                $.messager.alert("提醒","请勾选卖家信息!");
                                return false;
                            }
                            $.messager.confirm("提醒","是否确定启用卖家信息?",function(r){
                                if(r){
                                    var idStr = "";
                                    for(var i=0;i<rows.length;i++){
                                        if("" == idStr){
                                            idStr = rows[i].id;
                                        }else{
                                            idStr += "," + rows[i].id;
                                        }
                                    }
                                    loading("启用中..");
                                    $.ajax({
                                        url :'ebseller/enableEbSeller.do',
                                        type:'post',
                                        dataType:'json',
                                        data:{idStr:idStr},
                                        success:function(retJSON){
                                            loaded();
                                            $.messager.alert("提醒","启用无效"+retJSON.invalidnum+"条记录;<br/>启用成功"+retJSON.sucnum+"条记录;<br/>启用失败"+retJSON.unsucnum+"条记录;");
                                            $("#ebshop-datagrid-ebSellerListPage").datagrid("reload");
                                        }
                                    });
                                }
                            });
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/ebseller/ebSellerDisableBtn.do">
                    {
                        type:'button-close',//禁用
                        handler:function(){
                            var rows = $("#ebshop-datagrid-ebSellerListPage").datagrid('getChecked');
                            if(rows.length == 0){
                                $.messager.alert("提醒","请勾选卖家信息!");
                                return false;
                            }
                            $.messager.confirm("提醒","是否确定禁用卖家信息?",function(r){
                                if(r){
                                    var idStr = "";
                                    for(var i=0;i<rows.length;i++){
                                        if("" == idStr){
                                            idStr = rows[i].id;
                                        }else{
                                            idStr += "," + rows[i].id;
                                        }
                                    }
                                    loading("禁用中..");
                                    $.ajax({
                                        url :'ebseller/disableEbSeller.do',
                                        type:'post',
                                        dataType:'json',
                                        data:{idStr:idStr},
                                        success:function(retJSON){
                                            loaded();
                                            $.messager.alert("提醒",""+retJSON.invalidnum+"条记录状态不允许禁用;<br/>禁用成功"+retJSON.sucnum+"条记录;<br/>禁用失败"+retJSON.unsucnum+"条记录;");
                                            $("#ebshop-datagrid-ebSellerListPage").datagrid("reload");
                                        }
                                    });
                                }
                            });
                        }
                    }
                    </security:authorize>
                ],
                model:'base',
                type:'multipleList',
                taburl:'/ebseller/showEdSellerListPage.do',
                datagrid:'ebshop-datagrid-ebSellerListPage',
                tname:'t_eb_seller',
                id:''
            });

            var ebsellerListJson = $("#ebshop-datagrid-ebSellerListPage").createGridColumnLoad({
                frozenCol : [[{field:'ck',checkbox:true}]],
                commonCol: [[
                    {field:'id',title:'编号',width:60,align:' left',hidden:true},
                    {field:'sellernick', title:'卖家昵称', width:180,align:'left'},
                    {field:'company', title:'所属公司',width:180,align:'left'},
                    {field:'phone', title:'联系方式',width:100,align:'left'},
                    {field:'state', title:'状态',width:60,align:'left',
                        formatter:function(value,row,index){
                            return getSysCodeName('state', value);
                        }
                    },
                    {field:'remark', title:'备注',width:150,align:'left'}
                ]]
            });

            $('#ebshop-datagrid-ebSellerListPage').datagrid({
                authority:ebsellerListJson,
                frozenColumns:ebsellerListJson.frozen,
                columns:ebsellerListJson.common,
                fit:true,
                toolbar:'#ebshop-toobar-ebSellerListPage',
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
                url:'ebseller/getEbSellerList.do',
                onDblClickRow:function(rowIndex, rowData){
                    <security:authorize url="/ebseller/ebSellerViewBtn.do">
                    var url = "ebseller/showEbSellerViewPage.do?id="+rowData.id;
                    refreshLayout("卖家信息【查看】", url);
                    </security:authorize>
                }
            }).datagrid("columnMoving");

            //查询
            $("#ebshop-queay-ebSellerListPage").click(function(){
                var queryJSON = $("#ebshop-queryForm-ebSellerListPage").serializeJSON();
                $("#ebshop-datagrid-ebSellerListPage").datagrid("load",queryJSON);
            });

            //重置按钮
            $("#ebshop-resetQueay-ebSellerListPage").click(function(){
                $("#ebshop-queryForm-ebSellerListPage")[0].reset();
                $("#ebshop-datagrid-ebSellerListPage").datagrid("load",{});
            });
        });
    </script>
</body>
</html>
