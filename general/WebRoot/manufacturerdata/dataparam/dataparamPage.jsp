<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>厂家数据抓取配置参数</title>
    <%@include file="/include.jsp" %>
</head>

<body>
<table id="dataparam-table"></table>

<div id="dataparam-toolbar" style="padding:0px;height:auto">
    <div class="buttonBG">
        <security:authorize url="/manufacturerdata/saveDataParam.do">
            <a href="javaScript:void(0);" id="dataparam-button-save" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">保存</a>
        </security:authorize>
        <security:authorize url="/manufacturerdata/addDataStorageSummaryInit.do">
            <a href="javaScript:void(0);" id="dataparam-button-init" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">库存初始化</a>
        </security:authorize>
        <security:authorize url="/manufacturerdata/createUserAndView.do">
            <a href="javaScript:void(0);" id="dataparam-button-createUserForView" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">生成用户账号</a>
        </security:authorize>
        <security:authorize url="/manufacturerdata/uploadData.do">
            <a href="javaScript:void(0);" id="dataparam-button-uploadData" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">上报数据</a>
        </security:authorize>
    </div>
    <form action="" method="post" id="dataparam-form-param">
        <table  cellpadding="5px" cellspacing="5px" style="padding-top: 5px;padding-left: 5px;">
            <th colspan="4" align="left">基础参数设置</th>
            <tr>
                <td></td>
                <td align="right" style="width: 80px">开始日期：</td>
                <td><input type="text" id="dataparam-startdate" class="Wdate" name="InitStratDate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" style="width: 140px;" value="${dataparam.InitStratDate}"/></td>
            </tr>
            <th colspan="4" align="left">厂方数据对接配置</th>
            <input type="hidden" id="dataparam-dataDetail" name="detailJson" />
        </table>
    </form>
</div>
<div id="dataparam-dialog"></div>
<div class="easyui-menu" id="dataparam-contextMenu" style="display: none;">
    <div id="dataparam-addRow" data-options="iconCls:'button-add'">添加</div>
    <div id="dataparam-editRow" data-options="iconCls:'button-edit'">编辑</div>
    <div id="dataparam-removeRow" data-options="iconCls:'button-delete'">删除</div>
</div>
</body>
<script type="text/javascript">
    var actiontype="";
    var manufacturerdataJson = $("#dataparam-table").createGridColumnLoad({
        name :'',
        frozenCol : [[
            {field:'ck',checkbox:true,isShow:true}
        ]],
        commonCol : [[
            {field:'name',title:'对接名称',width:125,sortable:true},
            {field:'markid',title:'标识',width:80,sortable:true},
            {field:'viewtype',title:'视图类型',width:80,sortable:true,
                formatter: function(dateValue,row,index){
                    if(dateValue=='0'){
                        return "默认视图";
                    }else if(dateValue=='1'){
                        return "数据对接视图";
                    }
                }
            },
            {field:'state',title:'状态',width:50,sortable:true,
                formatter: function(dateValue,row,index){
                    if(dateValue=='0'){
                        return "禁用";
                    }else if(dateValue=='1'){
                        return "启用";
                    }else if(dateValue=='2'){
                        return "保存";
                    }
                }
            },
            {field:'supplierid',title:'供应商编号',width:160,sortable:true},
            {field:'suppliername',title:'供应商名称',width:200,sortable:true},
            {field:'goodstype',title:'对应商品类型',width:90,sortable:true,
                formatter: function(dateValue,row,index){
                    if(dateValue=='1'){
                        return "全部";
                    }else if(dateValue=='2'){
                        return "按供应商";
                    }else if(dateValue=='3'){
                        return "按品牌";
                    }
                }
            },
            {field:'gtypeids',title:'对应商品类型编号',width:120,sortable:true,hidden:true},
            {field:'gtypenames',title:'对应商品类型名称',width:120,sortable:true},
            {field:'customertype',title:'对应客户类型',width:90,sortable:true,
                formatter: function(dateValue,row,index){
                    if(dateValue=='1'){
                        return "全部";
                    }else if(dateValue=='2'){
                        return "按部门";
                    }else if(dateValue=='3'){
                        return "按销售区域";
                    }else if(dateValue=='4'){
                        return "按客户分类";
                    }
                }
            },
            {field:'ctypeids',title:'对应客户类型编号',width:120,sortable:true},
            {field:'ctypenames',title:'对应客户类型编号',width:120,sortable:true},
            {field:'storagetype',title:'对应仓库类型',width:120,sortable:true,
                formatter: function(dateValue,row,index){
                    if(dateValue=='1'){
                        return "全部";
                    }else if(dateValue=='2'){
                        return "部分仓库";
                    }
                }},
            {field:'storageid',title:'对应仓库编号',width:120,sortable:true},
            {field:'storagename',title:'对应仓库名称',width:200,sortable:true},
            {field:'vstorageid',title:'虚拟仓库编号',width:80,sortable:true},
            {field:'vstoragename',title:'虚拟仓库名称',width:90,sortable:true},
            {field:'level',title:'优先级',width:80,sortable:true},
            {field:'addtime',title:'添加时间',width:80,sortable:true},
            {field:'adduserid',title:'添加人编号',width:80,sortable:true},
            {field:'addusername',title:'添加人名称',width:80,sortable:true},
            {field:'uploaddate',title:'最近上报时间',width:80,sortable:true},
            {field:'username',title:'账号名称',width:80,sortable:true},
            {field:'password',title:'账号密码',width:80,sortable:true},
        ]]
    });


    $(function(){
        $("#dataparam-table").datagrid({
            authority:manufacturerdataJson,
            frozenColumns: manufacturerdataJson.frozen,
            columns:manufacturerdataJson.common,
            border: true,
            rownumbers: true,
            showFooter: true,
            striped:true,
            fit:true,
            singleSelect: false,
            checkOnSelect:true,
            selectOnCheck:true,
            data:JSON.parse('${detailList}'),
            toolbar:'#dataparam-toolbar',
            onRowContextMenu: function(e, rowIndex, rowData){
                e.preventDefault();
                $("#dataparam-table").datagrid('selectRow', rowIndex);
                $("#dataparam-contextMenu").menu('show', {
                    left:e.pageX,
                    top:e.pageY
                });
            },
            onDblClickRow: function(rowIndex, rowData){
                $(this).datagrid('clearSelections').datagrid('selectRow',rowIndex);
                if(rowData.name == undefined){
                    showDataConfigAddPage();
                }
                else{
                    if(rowData.state=="1" ){
                        showDataConfigViewPage();
                    }else{
                        showDataConfigEditPage();
                    }

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

        $("#dataparam-addRow").click(function(){
            var flag = $("#dataparam-contextMenu").menu('getItem','#dataparam-addRow').disabled;
            if(flag){
                return false;
            }
            showDataConfigAddPage();
        });
        $("#dataparam-editRow").click(function(){
            var flag = $("#dataparam-contextMenu").menu('getItem','#dataparam-editRow').disabled;
            if(flag){
                return false;
            }
            showDataConfigEditPage();
        });
        $("#dataparam-removeRow").click(function(){
            var flag = $("#dataparam-contextMenu").menu('getItem','#dataparam-removeRow').disabled;
            if(flag){
                return false;
            }
            removeDataConfig();
        });


        $("#dataparam-button-save").click(function(){
//            if(checkDataDetailEmpty()){
//                $.messager.alert("提醒","抱歉，请填写配置信息");
//                return false;
//            }
            $.messager.confirm("提醒","确定保存配置信息？",function(r){
                if(r){
                   	$("#dataparam-form-param").attr("action", "manufacturerdata/saveDataParam.do");
                    $("#dataparam-form-param").submit();
                }
            });

        });
        $("#dataparam-button-init").click(function(){
            var rows =  $("#dataparam-table").datagrid('getChecked');
            var flag = false;
            var markids = null;
            if(rows.length>0){
                for(var i=0;i<rows.length;i++){
                    var row = rows[i];
                    if(row.markid!=null && row.markid!=""){
                        flag  = true;
                        if(markids==null){
                            markids = row.markid;
                        }else{
                            markids += ","+row.markid;
                        }
                    }
                }
            }
            if(!flag){
                $.messager.alert("提醒","请选择厂方数据对接配置项.");
                return false;
            }
            $.messager.confirm("提醒","是否初始化库存数据？",function(r){
                if(r){
                    loading("初始化中...");
                    $.ajax({
                        url:'manufacturerdata/addDataStorageSummaryInit.do',
                        dataType:'json',
                        type:'post',
                        data:{ids:markids},
                        success:function(json){
                            loaded();
                            if(json.flag){
                                $.messager.alert("提醒","初始化库存成功。"+json.msg);
                                refresh();
                            }else{
                                $.messager.alert("提醒","初始化库存失败。"+json.msg);
                                refresh();
                            }
                        },
                        error:function(){
                            loaded();
                            $.messager.alert("错误","出错了");
                            refresh();
                        }
                    });
                }
            });
        });
        $("#dataparam-button-createUserForView").click(function(){
            var rows =  $("#dataparam-table").datagrid('getChecked');
            var flag = false;
            var markids = null;
            if(rows.length>0){
                for(var i=0;i<rows.length;i++){
                    var row = rows[i];
                    if(row.markid!=null && row.markid!=""){
                        flag  = true;
                        if(markids==null){
                            markids = row.markid;
                        }else{
                            markids += ","+row.markid;
                        }
                    }
                }
            }
            if(!flag){
                $.messager.alert("提醒","请选择厂方数据对接配置项.");
                return false;
            }
            $.messager.confirm("提醒","是否为选中的对接方生成用户账号？",function(r){
                if(r){
                    loading("生成中...");
                    $.ajax({
                        url:'manufacturerdata/createUserAndView.do',
                        dataType:'json',
                        type:'post',
                        data:{markids:markids},
                        success:function(json){
                            loaded();
                            if(null!=json.msg){
                                $.messager.alert("提醒","生成完成。"+json.msg);
                            }
                            else{
                                $.messager.alert("提醒","生成完成。");
                            }
                            refresh();
                        },
                        error:function(){
                            loaded();
                            $.messager.alert("错误","出错了");
                            refresh();
                        }
                    });
                }
            });
        });
        $("#dataparam-button-uploadData").click(function(){
            var rows =  $("#dataparam-table").datagrid('getChecked');
            var flag = false;
            var markids = null;
            if(rows.length>0){
                for(var i=0;i<rows.length;i++){
                    var row = rows[i];
                    if(row.markid!=null && row.markid!=""){
                        flag  = true;
                        if(markids==null){
                            markids = row.markid;
                        }else{
                            markids += ","+row.markid;
                        }
                    }
                }
            }
            if(!flag){
                $.messager.alert("提醒","请选择厂方数据对接配置项.");
                return false;
            }
            $.messager.confirm("提醒","是否上报选中对接方的数据？",function(r){
                if(r){
                    loading("上报中...");
                    $.ajax({
                        url:'manufacturerdata/uploadData.do',
                        dataType:'json',
                        type:'post',
                        data:{markids:markids},
                        success:function(json){
                            loaded();
                            if(null!=json.msg){
                                $.messager.alert("提醒","上报完成。"+json.msg);
                            }
                            else{
                                $.messager.alert("提醒","上报完成。");
                            }
                            refresh();
                        },
                        error:function(){
                            loaded();
                            $.messager.alert("错误","出错了");
                            refresh();
                        }
                    });
                }
            });
        });
        $("#dataparam-form-param").form({
            onSubmit: function(){
                var json = $("#dataparam-table").datagrid('getRows');

                $("#dataparam-dataDetail").val(JSON.stringify(json));
                var flag = $(this).form('validate');
                if(flag==false){
                    return false;
                }
                loading("提交中..");
            },
            success:function(data){
                //表单提交完成后 隐藏提交等待页面
                loaded();
                var json = $.parseJSON(data);
                console.log(json)
                if(json.flag){
                    if(json.type=='dataparam'){
                        $.messager.alert("提醒","基础参数保存成功.");
                    }else if(json.type=='dataconfig'){
                        if(actiontype=="add"){
                            $.messager.alert("提醒","厂家参数保存成功.");
                        }else if(actiontype=="edit"){
                            $.messager.alert("提醒","厂家参数修改成功.");
                        }else if(actiontype=="delete"){
                            $.messager.alert("提醒","厂家参数删除成功.");
                        }

                    }
                }else{
                    $.messager.alert("提醒","保存失败.");
                }
                refresh();
            }
        })
    });



    function checkDataDetailEmpty(){
        var flag=true;
        var $table=$("#dataparam-table");
        var data = $table.datagrid('getRows');
        for(var i=0;i<data.length;i++){
            if(data[i].name && data[i].name!=""){
                flag=false;
                break;
            }
        }
        return flag;
    }
    function showDataConfigAddPage(){
        $('<div id="dataparam-dialog-content"></div>').appendTo('#dataparam-dialog');
        $('#dataparam-dialog-content').dialog({
            title: '新增厂家信息',
            width: 680,
            height: 320,
            collapsible:false,
            minimizable:false,
            maximizable:true,
            resizable:true,
            closed: true,
            cache: false,
            modal: true,
            href: 'manufacturerdata/showDataConfigAddPage.do',
            onLoad:function(){
                $("#dataparam-name-addDataconfig").focus();
            },
            onClose:function(){
                $('#dataparam-dialog-content').dialog("destroy");
            }
        });
        $('#dataparam-dialog-content').dialog("open");
    }


    function showDataConfigEditPage(){
        var row = $("#dataparam-table").datagrid('getSelected');
        if(row == null){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if(row.name == undefined){
            showDataConfigAddPage();
        }else{
            $('<div id="dataparam-dialog-content"></div>').appendTo('#dataparam-dialog');
            $('#dataparam-dialog-content').dialog({
                title: '修改厂家信息',
                width: 680,
                height: 320,
                collapsible:false,
                minimizable:false,
                maximizable:true,
                resizable:true,
                closed: true,
                cache: false,
                href: 'manufacturerdata/showDataConfigEditPage.do',
                modal: true,
                onLoad:function(){

                },
                onClose:function(){
                    $('#dataparam-dialog-content').dialog("destroy");
                }
            });
            $('#dataparam-dialog-content').dialog("open");
        }
    }

    function showDataConfigViewPage(){
        var row = $("#dataparam-table").datagrid('getSelected');
        if(row == null){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if(row.name == undefined){
            showDataConfigAddPage();
        }else{
            $('<div id="dataparam-dialog-content"></div>').appendTo('#dataparam-dialog');
            $('#dataparam-dialog-content').dialog({
                title: '查看厂家信息',
                width: 680,
                height: 320,
                collapsible:false,
                minimizable:false,
                maximizable:true,
                resizable:true,
                closed: true,
                cache: false,
                href: 'manufacturerdata/showDataConfigViewPage.do',
                modal: true,
                onLoad:function(){

                },
                onClose:function(){
                    $('#dataparam-dialog-content').dialog("destroy");
                }
            });
            $('#dataparam-dialog-content').dialog("open");
        }
    }

    //保存厂家信息明细
    function addDataConfig(goFlag) { //添加新数据确定后操作，

        var flag = $("#dataparam-form-addDataconfig").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先完善厂商信息');
            return false;
        }
        var form = $("#dataparam-form-addDataconfig").serializeJSON();
        var rowIndex = 0;
        var name = $("#dataparam-name-addDataconfig").val();
        var markid = $("#dataparam-markid-addDataconfig").val();
        var vstorageid = $("#dataparam-vstorageid-addDataconfig").val();
        var vstoragename = $("#dataparam-vstoragename-addDataconfig").val();
        var sameflag="";
        var updateFlag = false;
        var rows = $("#dataparam-table").datagrid('getRows');
        for (var i = 0; i < rows.length; i++) {
            var rowJson = rows[i];
            if (rowJson.name == name) {
                sameflag='name';
                rowIndex = i;
                updateFlag = true;
                break;
            }
            if (rowJson.markid==markid) {
                sameflag='markid';
                rowIndex = i;
                updateFlag = true;
                break;
            }
            if (rowJson.vstorageid==vstorageid) {
                sameflag='vstorageid';
                rowIndex = i;
                updateFlag = true;
                break;
            }
            if (rowJson.vstoragename==vstoragename) {
                sameflag='vstoragename';
                rowIndex = i;
                updateFlag = true;
                break;
            }
            if (rowJson.name == undefined) {
                rowIndex = i;
                break;
            }
        }

        if (rowIndex == rows.length - 1) {
            $("#dataparam-table").datagrid('appendRow', {}); //如果是最后一行则添加一新行
        }
        if(updateFlag){
            if(sameflag=='markid'){
                $.messager.alert("提醒", "已有相同标识的对接方！");
            }else  if(sameflag=='name'){
                $.messager.alert("提醒", "已有相同名字的对接方！");
            }else if(sameflag=='vstorageid'){
                $.messager.alert("提醒", "已有相同虚拟仓库编号的对接方！");
            }else  if(sameflag=='vstoragename'){
                $.messager.alert("提醒", "已有相同虚拟仓库名字的对接方！");
            }
 			return false;
 		}else{
            $("#dataparam-table").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        }
        if(goFlag){ //go为true确定并继续添加一条
            var url ='manufacturerdata/showDataConfigAddPage.do';
            $("#dataparam-dialog-content").dialog('refresh', url);
        }
        else{ //否则直接关闭
            $("#dataparam-dialog-content").dialog('destroy');
        }
        actiontype="add";
        saveDataConfig();
    }

    function editDataConfig(){
        var flag = $("#dataparam-form-addDataconfig").form('validate');
        if(flag==false){
            return false;
        }
        var form = $("#dataparam-form-addDataconfig").serializeJSON();
        var row = $("#dataparam-table").datagrid('getSelected');
        var rowIndex = $("#dataparam-table").datagrid('getRowIndex', row);
        $("#dataparam-table").datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
        $("#dataparam-dialog-content").dialog('destroy');
        actiontype="edit";
        saveDataConfig();
    }

    //删除代配送采购单明细
    function removeDataConfig(){
        var row = $("#dataparam-table").datagrid('getSelected');
        if(row == null){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if(row.state=='1'){
            $.messager.alert("提醒", "该条数据已启用，不能删除");
            return false;
        }
        $.messager.confirm("提醒","确定删除该厂家信息?",function(r){
            if(r){
                var rowIndex = $("#dataparam-table").datagrid('getRowIndex', row);
                $("#dataparam-table").datagrid('deleteRow', rowIndex);
                var rows = $("#dataparam-table").datagrid('getRows');
            }
            actiontype="delete";
            saveDataConfig();
        });

    }
    function saveDataConfig(){
        $("#dataparam-form-param").attr("action", "manufacturerdata/saveDataConfig.do");
        $("#dataparam-form-param").submit();
    }

    function refresh(){
        window.location.reload();
    }
</script>
</html>

