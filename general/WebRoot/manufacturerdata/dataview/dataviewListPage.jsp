<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>数据视图</title>
    <%@include file="/include.jsp" %>
</head>

<body>
<table id="dataview-table"></table>

<div id="dataview-toolbar" style="padding:0px;height:auto">
    <div class="buttonBG">
        <%--<security:authorize url="/manufacturerdata/saveDataParam.do">--%>
            <%--<a href="javaScript:void(0);" id="dataview-button-add" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">新增</a>--%>
        <%--</security:authorize>--%>
        <security:authorize url="/manufacturerdata/saveDataParam.do">
            <a href="javaScript:void(0);" id="dataview-button-delete" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-delete'">删除</a>
        </security:authorize>
        <%--<security:authorize url="/manufacturerdata/addDataStorageSummaryInit.do">--%>
            <%--<a href="javaScript:void(0);" id="dataview-button-init" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">库存初始化</a>--%>
        <%--</security:authorize>--%>
        <%--<security:authorize url="/manufacturerdata/createUserAndView.do">--%>
            <%--<a href="javaScript:void(0);" id="dataview-button-createUserForView" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">生成用户账号</a>--%>
        <%--</security:authorize>--%>
        <%--<security:authorize url="/manufacturerdata/uploadData.do">--%>
            <%--<a href="javaScript:void(0);" id="dataview-button-uploadData" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">上报数据</a>--%>
        <%--</security:authorize>--%>
    </div>
    <form action="" method="post" id="dataview-form">
        <table class="querytable">
            <tr>
                <td>视图名称:</td>
                <td colspan="3">
                    <input type="text"  name="viewname" style="width:220px"/>
                </td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="dataview-queay-queryList" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="dataview-queay-reloadList" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="dataview-dialog"></div>
</body>
<script type="text/javascript">
    var initQueryJSON = $("#dataview-form").serializeJSON();
    var actiontype="";
    var manufacturerdataJson = $("#dataview-table").createGridColumnLoad({
        name :'',
        frozenCol : [[
        ]],
        commonCol : [[
            {field:'viewname',title:'视图名称',width:125,sortable:true},
            {field:'viewsql',title:'视图查询语句',width:550,sortable:true},

        ]]
    });


    $(function(){
        $("#dataview-table").datagrid({
            authority:manufacturerdataJson,
            frozenColumns: manufacturerdataJson.frozen,
            columns:manufacturerdataJson.common,
            border: true,
            rownumbers: true,
            pagination: true,
            showFooter: true,
            striped:true,
            fit:true,
            singleSelect: true,
            checkOnSelect:false,
            selectOnCheck:false,
            url: 'dataview/showDataViewListData.do',
            queryParams: initQueryJSON,
            toolbar:'#dataview-toolbar',
            onDblClickRow: function(rowIndex, rowData){
                $(this).datagrid('clearSelections').datagrid('selectRow',rowIndex);
                if(rowData.viewname != undefined){
                    showDataViewEditPage();
                }
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
            },
        }).datagrid('columnMoving');

        $("#dataview-addRow").click(function(){
            var flag = $("#dataview-contextMenu").menu('getItem','#dataview-addRow').disabled;
            if(flag){
                return false;
            }
            showDataViewAddPage();
        });
        $("#dataview-editRow").click(function(){
            var flag = $("#dataview-contextMenu").menu('getItem','#dataview-editRow').disabled;
            if(flag){
                return false;
            }
            showDataViewEditPage();
        });
        $("#dataview-removeRow").click(function(){
            var flag = $("#dataview-contextMenu").menu('getItem','#dataview-removeRow').disabled;
            if(flag){
                return false;
            }
            removeDataView();
        });
    });

    //查询
    $("#dataview-queay-queryList").click(function(){
        //把form表单的name序列化成JSON对象
        var queryJSON = $("#dataview-form").serializeJSON();
        $("#dataview-table").datagrid("load",queryJSON);
    });
    //重置
    $("#dataview-queay-reloadList").click(function(){
        $("#dataview-form")[0].reset();
        var queryJSON = $("#dataview-form").serializeJSON();
        $("#dataview-table").datagrid("load",queryJSON);
    });

    //新增
    $("#dataview-button-add").click(function(){
        showDataViewAddPage();
    });

    //删除
    $("#dataview-button-delete").click(function(){
        removeDataView();
    });
    function showDataViewAddPage(){
        $('<div id="dataview-dialog-content"></div>').appendTo('#dataview-dialog');
        $('#dataview-dialog-content').dialog({
            title: '新增数据采集视图',
            width: 680,
            height: 520,
            collapsible:false,
            minimizable:false,
            maximizable:true,
            resizable:true,
            closed: true,
            cache: false,
            modal: true,
            href: 'dataview/showDataViewAddPage.do',
            onLoad:function(){
            },
            onClose:function(){
                $("#dataview-dialog-content").dialog("destroy");
            }
        });
        $('#dataview-dialog-content').dialog("open");
    }


    function showDataViewEditPage(){
        var row = $("#dataview-table").datagrid('getSelected');
        if(row == null){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if(row.viewname == undefined){
            showDataViewAddPage();
        }else{
            $('<div id="dataview-dialog-content"></div>').appendTo('#dataview-dialog');
            $('#dataview-dialog-content').dialog({
                title: '修改数据采集视图',
                width: 680,
                height: 320,
                collapsible:false,
                minimizable:false,
                maximizable:true,
                resizable:true,
                closed: true,
                cache: false,
                href: 'dataview/showDataViewEditPage.do?viewname='+row.viewname,
                modal: true,
                onLoad:function(){

                },
                onClose:function(){
                    $('#dataview-dialog-content').dialog("destroy");
                }
            });
            $('#dataview-dialog-content').dialog("open");
        }
    }

    function showDataViewViewPage(){
        var row = $("#dataview-table").datagrid('getSelected');
        if(row == null){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if(row.name == undefined){
            showDataViewAddPage();
        }else{
            $('<div id="dataview-dialog-content"></div>').appendTo('#dataview-dialog');
            $('#dataview-dialog-content').dialog({
                title: '查看数据采集视图',
                width: 680,
                height: 320,
                collapsible:false,
                minimizable:false,
                maximizable:true,
                resizable:true,
                closed: true,
                cache: false,
                href: 'manufacturerdata/showDataViewViewPage.do',
                modal: true,
                onLoad:function(){

                },
                onClose:function(){
                    $('#dataview-dialog-content').dialog("destroy");
                }
            });
            $('#dataview-dialog-content').dialog("open");
        }
    }

    //保存厂家信息明细
    function addDataView(goFlag) { //添加新数据确定后操作，

        var flag = $("#dataview-form-addDateview").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先完善视图信息');
            return false;
        }
        var form = $("#dataview-form-addDateview").serializeJSON();
        var rowIndex = 0;
        var viewname = $("#dataview-viewname-addDateview").val();
        var sameflag="";
        var updateFlag = false;
        var rows = $("#dataview-table").datagrid('getRows');
        for (var i = 0; i < rows.length; i++) {
            var rowJson = rows[i];
            if (rowJson.name == viewname) {
                sameflag='viewname';
                rowIndex = i;
                updateFlag = true;
                break;
            }
        }

        if(updateFlag){
            if(sameflag=='viewname'){
                $.messager.alert("提醒", "已有相同名称的视图！");
            }
            return false;
        }
//        if(goFlag){ //go为true确定并继续添加一条
//            var url ='manufacturerdata/showDataViewAddPage.do';
//            $("#dataview-dialog-content").dialog('refresh', url);
//        }
//        else{ //否则直接关闭

//        }
        actiontype="add";
        saveDataView();
    }

    function editDataView(){
        console.log(111)
        var flag = $("#dataview-form-addDateview").form('validate');
        if(flag==false){
            $.messager.alert("提醒", '请先完善视图信息');
            return false;
        }

        actiontype="edit";
        saveDataView();
    }

    //删除代配送采购单明细
    function removeDataView(){
        var row = $("#dataview-table").datagrid('getSelected');
        if(row == null){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
//        if(row.state=='1'){
//            $.messager.alert("提醒", "该条数据已启用，不能删除");
//            return false;
//        }
        $.messager.confirm("提醒","确定删除该视图?",function(r){
            loading("数据处理中..");
            $.ajax({
                url:'dataview/deleteDataView.do',
                dataType:'json',
                type:'post',
                data:{viewname:row.viewname},
                success:function(json){
                    loaded();
                    if(json.flag){
                        $.messager.alert("提醒","删除成功。");
                    }
                    else{
                        $.messager.alert("提醒","删除失败。");
                    }
                    refresh();
                },
                error:function(){
                    loaded();
                    $.messager.alert("提醒","删除失败。");
                }
            });
        });

    }
    function saveDataView(){
        $("#dataview-form-addDateview").attr("action", "dataview/saveDataView.do");
        $("#dataview-form-addDateview").submit();
    }

    function refresh(){
        var queryJSON = $("#dataview-form").serializeJSON();
        $("#dataview-table").datagrid("load",queryJSON);
    }
</script>
</html>

