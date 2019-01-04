<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>航天金税税收分类编码维护列表</title>
    <%@include file="/include.jsp" %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="htgoldtax-button-jsTaxTypeCodeList"></div>
    </div>
    <div data-options="region:'center'">
        <table id="htgoldtax-table-jsTaxTypeCodeList"></table>
        <div id="htgoldtax-query-jsTaxTypeCodeList" style="padding:0px;height:auto">
            <form action="" id="htgoldtax-form-jsTaxTypeCodeList" method="post" style="padding-top: 2px;">
                <table>
                    <tr>
                        <td>编码:</td>
                        <td><input type="text" id="htgoldtax-jsTaxTypeCodeList-query-id" name="idlike" style="width:180px" /></td>
                        <td>货物名称:</td>
                        <td><input type="text" name="goodsname" style="width:180px" /></td>
                    </tr>
                    <tr>
                        <td>说明:</td>
                        <td><input type="text" name="description" style="width:180px" /></td>
                        <td>关键词:</td>
                        <td><input type="text" name="keyword" style="width:180px" /></td>
                        <td>
                            <a href="javaScript:void(0);" id="htgoldtax-query-queryJsTaxTypeCodeList" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="htgoldtax-query-reloadJsTaxTypeCodeList" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<div style="display:none">
    <div id="htgoldtax-dialogOper-jsTaxTypeCodeList"></div>
    <div id="htgoldtax-dialog-import-jsTaxTypeCode">
        <form action="" id="htgoldtax-dialog-import-jsTaxTypeCode-form" method="post">
            <table>
                <tbody>
                <tr>
                    <td colspan="2">
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;width:80px;">说明：</td>
                    <td>
                        <b style="color:#f00">一、导入版本</b>：<br/>
                        &nbsp;版本一：导入时，EXCEL中的编码字段为“编码”<br/>
                        &nbsp;版本二：导入时，EXCEL中的编码字段由“篇、类、章、节、条、款、项、目、子目、细目”<br/>
                        <b style="color:#f00">二、导入出错提示</b>：<br/>
                        &nbsp;已有编码提示：如果已经存在相同编码时，系统会提示该列数据。
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;">导入版本：</td>
                    <td>
                        <select id="htgoldtax-dialog-import-jsTaxTypeCode-form-version" name="importversion">
                            <option value="1">版本一</option>
                            <option value="2">版本二</option>
                        </select>

                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;">出错提示：</td>
                    <td>
                        <input type="checkbox" id="htgoldtax-dialog-import-jsTaxTypeCode-form-existsidtip" name="existsidtip" value="1" checked="checked">已有编码提示
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
    <a href="javaScript:void(0);" id="htgoldtax-button-export-jsTaxTypeCode-click" style="display: none"title="导出">导出</a>
    <a href="javaScript:void(0);" id="htgoldtax-button-import-jsTaxTypeCode-click" style="display: none"title="导入">导入</a>
</div>
<script type="text/javascript">
    $(function(){

        $.extend($.fn.validatebox.defaults.rules, {
            validJsTaxTypeCodeId:{
                validator:function(value){
                    if(value.length>0){
                        var data=jsTaxTypeCode_getAjaxContent({id: value},'basefiles/htgoldtax/isUsedJsTaxTypeCodeById.do');
                        var json = $.parseJSON(data);
                        if(json.flag == true){
                            $.fn.validatebox.defaults.rules.validJsTaxTypeCodeId.message = '当前编码已经使用，请重新填写';
                            return false;
                        }else{
                            return true;
                        }
                    }
                    return false;
                },
                message:'请填写编码'
            }
        });

        //根据初始的列与用户保存的列生成以及字段权限生成新的列
        var jsTaxTypeCodeListColJson=$("#htgoldtax-table-jsTaxTypeCodeList").createGridColumnLoad({
            name:'print_templet_resouce',
            frozenCol:[[
                {field:'idok',checkbox:true,isShow:true}
            ]],
            commonCol:[[
                {field:'id',title:'编号',width:100,sortable:true},
                {field:'mergeid',title:'合并编码',width:150,sortable:true},
                {field:'goodsname',title:'货物和劳务名称',width:180},
                {field:'description',title:'说明',width:200},
                {field:'keyword',title:'关键字',width:200},
                {field:'addusername',title:'创建人',width:100},
                {field:'addtime',title:'创建时间',width:120},
                {field:'modifyusername',title:'修改人',width:100},
                {field:'modifytime',title:'修改时间',width:120}
            ]]
        });

        $("#htgoldtax-table-jsTaxTypeCodeList").datagrid({
            authority:jsTaxTypeCodeListColJson,
            frozenColumns:jsTaxTypeCodeListColJson.frozen,
            columns:jsTaxTypeCodeListColJson.common,
            fit:true,
            method:'post',
            title:'',
            rownumbers:true,
            pagination:true,
            idField:'id',
            sortName : 'id',
            sortOrder : 'asc',
            singleSelect:true,
            checkOnSelect:true,
            selectOnCheck:true,
            pageSize:30,
            pageList:[30,50,100,200],
            toolbar:'#htgoldtax-query-jsTaxTypeCodeList',
            url:'basefiles/htgoldtax/getJsTaxTypeCodePageListData.do',
            onDblClickRow:function(index, dataRow){
                resourceADMOperDialog("金税税收分类【查看】", 'basefiles/htgoldtax/showJsTaxTypeCodeViewPage.do?id='+dataRow.id);
            }
        });

        //查询
        $("#htgoldtax-query-queryJsTaxTypeCodeList").click(function(){
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#htgoldtax-form-jsTaxTypeCodeList").serializeJSON();
            $("#htgoldtax-table-jsTaxTypeCodeList").datagrid("load",queryJSON);
        });
        //重置
        $('#htgoldtax-query-reloadJsTaxTypeCodeList').click(function(){
            $("#htgoldtax-form-jsTaxTypeCodeList")[0].reset();
            var queryJSON = $("#htgoldtax-form-jsTaxTypeCodeList").serializeJSON();
            $("#htgoldtax-table-jsTaxTypeCodeList").datagrid("load",queryJSON);
        });


        $("#htgoldtax-button-jsTaxTypeCodeList").buttonWidget({
            //初始默认按钮 根据type对应按钮事件
            initButton:[
                {}
            ],
            buttons:[
                <security:authorize url="/basefiles/htgoldtax/jsTaxTypeCodeAddBtn.do">
                {
                    id:'button-id-add',
                    name:'新增 ',
                    iconCls:'button-add',
                    handler:function(){
                        resourceADMOperDialog('金税税收分类【新增】', 'basefiles/htgoldtax/showJsTaxTypeCodeAddPage.do');
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/htgoldtax/jsTaxTypeCodeEditBtn.do">
                {
                    id:'button-id-edit',
                    name:'修改 ',
                    iconCls:'button-edit',
                    handler:function(){
                        var dataRow=$("#htgoldtax-table-jsTaxTypeCodeList").datagrid('getSelected');
                        if(dataRow==null || dataRow.id==""){
                            $.messager.alert("提醒","请选择相应的金税税收分类!");
                            return false;
                        }
                        resourceADMOperDialog("金税税收分类【修改】", 'basefiles/htgoldtax/showJsTaxTypeCodeEditPage.do?id='+dataRow.id);
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/htgoldtax/jsTaxTypeCodeCopyBtn.do">
                {
                    id:'button-id-copy',
                    name:'复制 ',
                    iconCls:'button-copy',
                    handler:function(){
                        var dataRow=$("#htgoldtax-table-jsTaxTypeCodeList").datagrid('getSelected');
                        if(dataRow==null || dataRow.id==""){
                            $.messager.alert("提醒","请选择相应的金税税收分类!");
                            return false;
                        }
                        resourceADMOperDialog('金税税收分类【复制】', 'basefiles/htgoldtax/showJsTaxTypeCodeCopyPage.do?id='+dataRow.id);
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/htgoldtax/jsTaxTypeCodeDeleteBtn.do">
                {
                    id:'button-id-delete',
                    name:'删除',
                    iconCls:'button-delete',
                    handler:function(){
                        var dataRow=$("#htgoldtax-table-jsTaxTypeCodeList").datagrid('getSelected');
                        if(dataRow==null || dataRow.id==null || dataRow.id==""){
                            $.messager.alert("提醒","请选择相应的金税税收分类!");
                            return false;
                        }
                        $.messager.confirm("提醒","删除后，商品档案中对应金税编码字段将设置为空,<br/>是否继续删除该金税税收分类信息？",function(r){
                            if(r){
                                loading("删除中..");
                                $.ajax({
                                    url :'basefiles/htgoldtax/deleteJsTaxTypeCode.do?id='+ dataRow.id,
                                    type:'post',
                                    dataType:'json',
                                    success:function(json){
                                        loaded();
                                        if(json.flag){
                                            $.messager.alert("提醒","删除成功");
                                            $("#htgoldtax-query-queryJsTaxTypeCodeList").trigger("click");
                                        }else{
                                            if(json.msg){
                                                $.messager.alert("提醒","删除失败,"+json.msg);
                                            }else{
                                                $.messager.alert("提醒","删除失败!");
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/htgoldtax/jsTaxTypeCodeExportBtn.do">
                {
                    id:'button-export-jstaxtypecode',
                    name:'导出',
                    iconCls:'button-export',
                    handler: function(){
                        $("#htgoldtax-button-export-jsTaxTypeCode-click").Excel('export',{
                            queryForm: "#htgoldtax-form-jsTaxTypeCodeList",
                            type:'exportUserdefined',
                            name:'金税税收分类导出',
                            fieldParam:{},
                            url:'basefiles/htgoldtax/exportJsTaxTypeCodeData.do'
                        });
                        $("#htgoldtax-button-export-jsTaxTypeCode-click").trigger("click");
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/htgoldtax/jsTaxTypeCodeImportBtn.do">
                {
                    id:'button-import-jstaxtypecode',
                    name:'导入',
                    iconCls:'button-export',
                    handler: function(){
                        $("#htgoldtax-dialog-import-jsTaxTypeCode").dialog({
                            title: '导入选项',
                            width: 450,
                            height: 300,
                            closed: true,
                            cache: false,
                            modal: true,
                            buttons:[
                                {
                                    text:'确定',
                                    iconCls:'button-ok',
                                    handler:function(){
                                        $("#htgoldtax-dialog-import-jsTaxTypeCode").dialog("close");
                                        var param={};
                                        var exportformflag = $("#htgoldtax-dialog-import-jsTaxTypeCode-form").form('validate');
                                        if (!exportformflag) {
                                            $.messager.alert("提醒", "抱歉，请填写相关参数");
                                            return false;
                                        }
                                        var exportformparm = $("#htgoldtax-dialog-import-jsTaxTypeCode-form").serializeJSON();
                                        exportformparm.oldFromData = "";
                                        delete exportformparm.oldFromData;
                                        param = jQuery.extend({}, exportformparm, param);

                                        var version=$("#htgoldtax-dialog-import-jsTaxTypeCode-form-version").val();


                                        var msg="注意：Excel中字段编码、货物和劳务名称、说明、关键字。<br/>";
                                        if(version=="1"){
                                            msg = msg + "版本一：导入时，Excel中的编码字段为“编码”<br/>";
                                        }else if(version == "2"){
                                            msg = msg + "版本二：导入时，Excel中的编码字段由“篇、类、章、节、条、款、项、目、子目、细目”<br/>";
                                        }

                                        $("#htgoldtax-button-import-jsTaxTypeCode-click").Excel('import',{
                                            type:'importUserdefined',
                                            importparam:msg,//参数描述
                                            version:'1',//导入页面显示哪个版本1
                                            url:'basefiles/htgoldtax/importJsTaxTypeCodeData.do',
                                            fieldParam:param,
                                            onClose: function(){ //导入成功后窗口关闭时操作，
                                                $("#htgoldtax-query-queryJsTaxTypeCodeList").trigger("click");
                                            }
                                        });
                                        $("#htgoldtax-dialog-import-jsTaxTypeCode").dialog('close');
                                        $("#htgoldtax-button-import-jsTaxTypeCode-click").trigger("click");
                                    }
                                },
                                {
                                    text:'取消',
                                    iconCls:'button-cancel',
                                    handler:function(){
                                        $("#htgoldtax-dialog-import-jsTaxTypeCode").dialog("close");
                                        return false;
                                    }
                                }
                            ],
                            onClose:function(){
                                //$("#htgoldtax-dialog-import-jsTaxTypeCode").dialog("destroy");
                            }
                        });
                        $("#htgoldtax-dialog-import-jsTaxTypeCode").dialog("open");


                    }
                },
                </security:authorize>
                {}
            ],
            model:'bill',
            type:'list',
            datagrid:'htgoldtax-table-jsTaxTypeCodeList',
            tname:'base_jstaxtypecode',
            id:''
        });

    });
    function resourceADMOperDialog(title, url){
        $('<div id="htgoldtax-dialogOper-jsTaxTypeCodeList-content"></div>').appendTo("#htgoldtax-dialogOper-jsTaxTypeCodeList");
        $('#htgoldtax-dialogOper-jsTaxTypeCodeList-content').dialog({
            title: title,
            width: 350,
            height: 350,
            closed: true,
            cache: false,
            href: url,
            maximizable:true,
            resizable:true,
            modal: true,
            onLoad:function(){
            },
            onClose:function(){
                $('#htgoldtax-dialogOper-jsTaxTypeCodeList-content').window("destroy");
            }
        });
        $('#htgoldtax-dialogOper-jsTaxTypeCodeList-content').dialog('open');
    }
    var jsTaxTypeCode_getAjaxContent = function (param, url) { //同步ajax
        var ajax = $.ajax({
            type: 'post',
            cache: false,
            url: url,
            data: param,
            async: false
        });
        return ajax.responseText;
    }
</script>
</body>
</html>
