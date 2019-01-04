<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>税收分类关联商品条形码维护列表</title>
    <%@include file="/include.jsp" %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="htgoldtax-button-jsTaxTypeCodeBarcodeList"></div>
    </div>
    <div data-options="region:'center'">
        <table id="htgoldtax-table-jsTaxTypeCodeBarcodeList"></table>
        <div id="htgoldtax-query-jsTaxTypeCodeBarcodeList" style="padding:0px;height:auto">
            <form action="" id="htgoldtax-form-jsTaxTypeCodeBarcodeList" method="post" style="padding-top: 2px;">
                <table>
                    <tr>
                        <td>分类编码:</td>
                        <td><input type="text" id="htgoldtax-jsTaxTypeCodeBarcodeList-query-id" name="jstypeid" style="width:180px" /></td>
                        <td>货物名称:</td>
                        <td><input type="text" name="goodsname" style="width:180px" /></td>
                        <td>条形码:</td>
                        <td><input type="text" name="barcode" style="width:150px" /></td>
                    </tr>
                    <tr>
                        <td>说明:</td>
                        <td><input type="text" name="description" style="width:180px" /></td>
                        <td>关键词:</td>
                        <td><input type="text" name="keyword" style="width:180px" /></td>
                        <td colspan="2">
                            <a href="javaScript:void(0);" id="htgoldtax-query-queryJsTaxTypeCodeBarcodeList" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="htgoldtax-query-reloadJsTaxTypeCodeBarcodeList" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<div style="display:none">
    <div id="htgoldtax-dialogOper-jsTaxTypeCodeBarcodeList"></div>
    <div id="htgoldtax-dialog-import-jsTaxTypeCodeBarcode">
        <form action="" id="htgoldtax-dialog-import-jsTaxTypeCodeBarcode-form" method="post">
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
                        <b style="color:#f00">一、导入出错提示</b>：<br/>
                        &nbsp;已有存在(税收分类和条形码相同)提示：如果已经存在相同编码时，系统会提示该列数据。
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;">出错提示：</td>
                    <td>
                        <input type="checkbox" id="htgoldtax-dialog-import-jsTaxTypeCodeBarcode-form-existsidtip" name="existsidtip" value="1" checked="checked">已存在提示
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
    <div id="htgoldtax-dialog-export-jsTaxTypeCodeBarcode">
        <form action="" id="htgoldtax-dialog-export-jsTaxTypeCodeBarcode-form" method="post">
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
                        <b style="color:#f00">一、导出版本</b>：<br/>
                        &nbsp;版本一：Excel列字段“税收分类编码”、“商品条形码”<br/>
                        &nbsp;版本二：Excel列字段“税收分类编码”、“商品条形码”、“货物和劳务名称”、“说明”、“关键字”<br/>。
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;">导出版本：</td>
                    <td>
                        <select id="htgoldtax-dialog-export-jsTaxTypeCodeBarcode-form-version" name="exportversion">
                            <option value="1">版本一</option>
                            <option value="2">版本二</option>
                        </select>

                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
    <a href="javaScript:void(0);" id="htgoldtax-button-export-jsTaxTypeCodeBarcode-click" style="display: none"title="导出">导出</a>
    <a href="javaScript:void(0);" id="htgoldtax-button-import-jsTaxTypeCodeBarcode-click" style="display: none"title="导入">导入</a>
</div>
<script type="text/javascript">
    function validJsTaxTypeCodeBarcode(operType,curId){
        $.extend($.fn.validatebox.defaults.rules, {
            validJsTaxTypeCodeBarcode:{
                validator:function(value){
                    if(typeof(curId)=="undefined" || null==curId){
                        curId="";
                    }
                    if(value.length>0){
                        var jstypeid=$("#htgoldtax-form-addJsTaxTypeCodeBarcode-jstypeid").widget("getValue") || "";
                        var queryParam={
                            jstypeid:jstypeid,
                            barcode: value
                        };
                        if($.trim(curId)!=""){
                            queryParam.notCurId=curId;
                        }else{
                            if("edit"==operType){
                                return false;
                            }
                        }
                        var data=jsTaxTypeCodeBarcode_getAjaxContent(queryParam,'basefiles/htgoldtax/isUsedJsTaxTypeCodeBarcode.do');
                        var json = $.parseJSON(data);
                        if(json.flag == true){
                            $.fn.validatebox.defaults.rules.validJsTaxTypeCodeBarcode.message = '当前税收分类与条形码组合已经使用，请重新填写';
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

    }
    $(function(){

        //根据初始的列与用户保存的列生成以及字段权限生成新的列
        var jsTaxTypeCodeBarcodeListColJson=$("#htgoldtax-table-jsTaxTypeCodeBarcodeList").createGridColumnLoad({
            name:'print_templet_resouce',
            frozenCol:[[
                {field:'idok',checkbox:true,isShow:true}
            ]],
            commonCol:[[
                {field:'id',title:'编号',width:100,hidden:true},
                {field:'jstypeid',title:'税收分类编码',width:150,sortable:true},
                {field:'barcode',title:'商品条形码',width:150},
                {field:'goodsname',title:'货物和劳务名称',width:180},
                {field:'description',title:'说明',width:200},
                {field:'keyword',title:'关键字',width:200},
                {field:'addusername',title:'创建人',width:100},
                {field:'addtime',title:'创建时间',width:120,
                    formatter:function(val,rowData,rowIndex){
                        if(val!=undefined){
                            return val.replace(/[tT]/," ");
                        }
                        return " ";
                    }
                },
                {field:'modifyusername',title:'修改人',width:100},
                {field:'modifytime',title:'修改时间',width:120,
                    formatter:function(val,rowData,rowIndex){
                        if(val!=undefined){
                            return val.replace(/[tT]/," ");
                        }
                        return " ";
                    }
                }
            ]]
        });

        $("#htgoldtax-table-jsTaxTypeCodeBarcodeList").datagrid({
            authority:jsTaxTypeCodeBarcodeListColJson,
            frozenColumns:jsTaxTypeCodeBarcodeListColJson.frozen,
            columns:jsTaxTypeCodeBarcodeListColJson.common,
            fit:true,
            method:'post',
            title:'',
            rownumbers:true,
            pagination:true,
            idField:'id',
            sortName : 'jstypeid',
            sortOrder : 'asc',
            singleSelect:true,
            checkOnSelect:true,
            selectOnCheck:true,
            pageSize:30,
            pageList:[30,50,100,200],
            toolbar:'#htgoldtax-query-jsTaxTypeCodeBarcodeList',
            url:'basefiles/htgoldtax/getJsTaxTypeCodeBarcodePageListData.do',
            onDblClickRow:function(index, dataRow){
                resourceADMOperDialog("税收分类关联商品条形码【查看】", 'basefiles/htgoldtax/showJsTaxTypeCodeBarcodeViewPage.do?id='+dataRow.id);
            }
        });

        //查询
        $("#htgoldtax-query-queryJsTaxTypeCodeBarcodeList").click(function(){
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#htgoldtax-form-jsTaxTypeCodeBarcodeList").serializeJSON();
            $("#htgoldtax-table-jsTaxTypeCodeBarcodeList").datagrid("load",queryJSON);
        });
        //重置
        $('#htgoldtax-query-reloadJsTaxTypeCodeBarcodeList').click(function(){

            //$("#htgoldtax-jsTaxTypeCodeBarcodeList-query-code").widget('clear');
            $("#htgoldtax-form-jsTaxTypeCodeBarcodeList")[0].reset();
            $("#htgoldtax-table-jsTaxTypeCodeBarcodeList").datagrid('loadData',{total:0,rows:[]});
        });


        $("#htgoldtax-button-jsTaxTypeCodeBarcodeList").buttonWidget({
            //初始默认按钮 根据type对应按钮事件
            initButton:[
                {}
            ],
            buttons:[
                <security:authorize url="/basefiles/htgoldtax/jsTaxTypeCodeBarcodeAddBtn.do">
                {
                    id:'button-id-add',
                    name:'新增 ',
                    iconCls:'button-add',
                    handler:function(){
                        resourceADMOperDialog('税收分类关联商品条形码【新增】', 'basefiles/htgoldtax/showJsTaxTypeCodeBarcodeAddPage.do');
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/htgoldtax/jsTaxTypeCodeBarcodeEditBtn.do">
                {
                    id:'button-id-edit',
                    name:'修改 ',
                    iconCls:'button-edit',
                    handler:function(){
                        var dataRow=$("#htgoldtax-table-jsTaxTypeCodeBarcodeList").datagrid('getSelected');
                        if(dataRow==null || dataRow.id==""){
                            $.messager.alert("提醒","请选择税收分类对应的商品条形码关联信息!");
                            return false;
                        }
                        resourceADMOperDialog("税收分类关联商品条形码【修改】", 'basefiles/htgoldtax/showJsTaxTypeCodeBarcodeEditPage.do?id='+dataRow.id);
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/htgoldtax/jsTaxTypeCodeBarcodeDeleteBtn.do">
                {
                    id:'button-id-delete',
                    name:'删除',
                    iconCls:'button-delete',
                    handler:function(){
                        var dataRow=$("#htgoldtax-table-jsTaxTypeCodeBarcodeList").datagrid('getSelected');
                        if(dataRow==null || dataRow.id==null || dataRow.id==""){
                            $.messager.alert("提醒","请选择相应的信息!");
                            return false;
                        }
                        $.messager.confirm("提醒","是否删除该税收分类对应的商品条形码关联信息？",function(r){
                            if(r){
                                loading("删除中..");
                                $.ajax({
                                    url :'basefiles/htgoldtax/deleteJsTaxTypeCodeBarcode.do?id='+ dataRow.id,
                                    type:'post',
                                    dataType:'json',
                                    success:function(json){
                                        loaded();
                                        if(json.flag){
                                            $.messager.alert("提醒","删除成功");
                                            $("#htgoldtax-query-queryJsTaxTypeCodeBarcodeList").trigger("click");
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
                <security:authorize url="/basefiles/htgoldtax/jsTaxTypeCodeBarcodeExportBtn.do">
                {
                    id:'button-export-jstaxtypecode',
                    name:'导出',
                    iconCls:'button-export',
                    handler: function(){
                        $("#htgoldtax-dialog-export-jsTaxTypeCodeBarcode").dialog({
                            title: '导出选项',
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
                                        $("#htgoldtax-dialog-export-jsTaxTypeCodeBarcode").dialog("close");
                                        var param={};
                                        var exportformflag = $("#htgoldtax-dialog-export-jsTaxTypeCodeBarcode-form").form('validate');
                                        if (!exportformflag) {
                                            $.messager.alert("提醒", "抱歉，请填写相关参数");
                                            return false;
                                        }
                                        var exportformparm = $("#htgoldtax-dialog-export-jsTaxTypeCodeBarcode-form").serializeJSON();
                                        exportformparm.oldFromData = "";
                                        delete exportformparm.oldFromData;
                                        param = jQuery.extend({}, exportformparm, param);


                                        $("#htgoldtax-button-export-jsTaxTypeCodeBarcode-click").Excel('export',{
                                            queryForm: "#htgoldtax-form-jsTaxTypeCodeBarcodeList",
                                            type:'exportUserdefined',
                                            name:'税收分类对应的商品条形码关联信息导出',
                                            fieldParam:param,
                                            url:'basefiles/htgoldtax/exportJsTaxTypeCodeBarcodeData.do'
                                        });
                                        $("#htgoldtax-button-export-jsTaxTypeCodeBarcode-click").trigger("click");

                                        $("#htgoldtax-dialog-export-jsTaxTypeCodeBarcode").dialog("close");
                                    }
                                },
                                {
                                    text:'取消',
                                    iconCls:'button-cancel',
                                    handler:function(){
                                        $("#htgoldtax-dialog-export-jsTaxTypeCodeBarcode").dialog("close");
                                        return false;
                                    }
                                }
                            ],
                            onClose:function(){
                                //$("#htgoldtax-dialog-export-jsTaxTypeCodeBarcode").dialog("destroy");
                            }
                        });
                        $("#htgoldtax-dialog-export-jsTaxTypeCodeBarcode").dialog("open");

                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/htgoldtax/jsTaxTypeCodeBarcodeImportBtn.do">
                {
                    id:'button-import-jstaxtypecode',
                    name:'导入',
                    iconCls:'button-export',
                    handler: function(){
                        $("#htgoldtax-dialog-import-jsTaxTypeCodeBarcode").dialog({
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
                                        $("#htgoldtax-dialog-import-jsTaxTypeCodeBarcode").dialog("close");
                                        var param={};
                                        var exportformflag = $("#htgoldtax-dialog-import-jsTaxTypeCodeBarcode-form").form('validate');
                                        if (!exportformflag) {
                                            $.messager.alert("提醒", "抱歉，请填写相关参数");
                                            return false;
                                        }
                                        var exportformparm = $("#htgoldtax-dialog-import-jsTaxTypeCodeBarcode-form").serializeJSON();
                                        exportformparm.oldFromData = "";
                                        delete exportformparm.oldFromData;
                                        param = jQuery.extend({}, exportformparm, param);

                                        var importips=$("#htgoldtax-dialog-import-jsTaxTypeCodeBarcode-form-existsidtip").prop("checked");
                                        var msg="";
                                        if(importips==true){
                                            msg="已有存在(税收分类和条形码相同)提示：如果已经存在相同编码时，系统会提示该列数据。";
                                        }

                                        $("#htgoldtax-button-import-jsTaxTypeCodeBarcode-click").Excel('import',{
                                            type:'importUserdefined',
                                            importparam:msg,//参数描述
                                            version:'1',//导入页面显示哪个版本1
                                            url:'basefiles/htgoldtax/importJsTaxTypeCodeBarcodeData.do',
                                            fieldParam:param,
                                            onClose: function(){ //导入成功后窗口关闭时操作，
                                                $("#htgoldtax-dialog-import-jsTaxTypeCodeBarcode").dialog("close");
                                                $("htgoldtax-query-queryJsTaxTypeCodeBarcodeList").trigger("click");
                                            }
                                        });
                                        $("#htgoldtax-button-import-jsTaxTypeCodeBarcode-click").trigger("click");
                                    }
                                },
                                {
                                    text:'取消',
                                    iconCls:'button-cancel',
                                    handler:function(){
                                        $("#htgoldtax-dialog-import-jsTaxTypeCodeBarcode").dialog("close");
                                        return false;
                                    }
                                }
                            ],
                            onClose:function(){
                                //$("#htgoldtax-dialog-import-jsTaxTypeCodeBarcode").dialog("destroy");
                            }
                        });
                        $("#htgoldtax-dialog-import-jsTaxTypeCodeBarcode").dialog("open");
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/htgoldtax/updateAndLinkGoodsInfoJsCodeByBarcodeBtn.do">
                {
                    id:'button-id-barcode-update',
                    name:'匹配商品档案',
                    iconCls:'button-edit',
                    handler:function(){
                        $.messager.confirm("提醒","系统将根据当前表中商品条形码关联库去关联商品档案并配对出对应金税分类编码，<br/>确定重新匹配吗？",function(r){
                            if(r){
                                loading("匹配中..");
                                $.ajax({
                                    url :'basefiles/htgoldtax/updateAndLinkGoodsInfoJsCodeByBarcode.do',
                                    type:'post',
                                    dataType:'json',
                                    success:function(json){
                                        loaded();
                                        var itotal=json.itotal || 0;
                                        var isuccess = json.isuccess || 0;
                                        var existscount = json.existscount || 0;
                                        if(json.flag){
                                            $.messager.alert("提醒","匹配成功,"+"待匹配条数："+itotal+"；成功匹配条数："+isuccess);
                                            $("#htgoldtax-query-queryJsTaxTypeCodeBarcodeList").trigger("click");
                                        }else{
                                            var msg="生成失败,"+"待匹配条数："+itotal+"；已存在条数："+existscount;
                                            if(json.msg){
                                                $.messager.alert("提醒",msg+"；"+json.msg);
                                            }else{
                                                $.messager.alert("提醒",msg);
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/basefiles/htgoldtax/updateAndCreateJsBarcodeDataBtn.do">
                {
                    id:'button-id-barcode-create',
                    name:'生成关联数据',
                    iconCls:'button-edit',
                    handler:function(){
                        $.messager.confirm("提醒","系统将根据商品档案中金税分类编码和条形码生成关联数据。<br/>生成时，会更新当前关联数据，确定要重新生成吗？",function(r){
                            if(r){
                                loading("生成中..");
                                $.ajax({
                                    url :'basefiles/htgoldtax/updateAndCreateJsBarcodeData.do',
                                    type:'post',
                                    dataType:'json',
                                    success:function(json){
                                        loaded();
                                        var itotal=json.itotal || 0;
                                        var isuccess = json.isuccess || 0;
                                        var existscount = json.existscount || 0;
                                        if(json.flag){
                                            $.messager.alert("提醒","生成成功,"+"待匹配条数："+itotal+"；成功匹配条数："+isuccess);
                                            $("#htgoldtax-query-queryJsTaxTypeCodeBarcodeList").trigger("click");
                                        }else{
                                            var msg="生成失败,"+"待匹配条数："+itotal+"；已存在条数："+existscount;
                                            if(json.msg){
                                                $.messager.alert("提醒",msg+"；"+json.msg);
                                            }else{
                                                $.messager.alert("提醒",msg);
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                {}
            ],
            model:'bill',
            type:'list',
            datagrid:'htgoldtax-table-jsTaxTypeCodeBarcodeList',
            tname:'base_jstaxtypecode',
            id:''
        });

    });
    function resourceADMOperDialog(title, url){
        $('<div id="htgoldtax-dialogOper-jsTaxTypeCodeBarcodeList-content"></div>').appendTo("#htgoldtax-dialogOper-jsTaxTypeCodeBarcodeList");
        $('#htgoldtax-dialogOper-jsTaxTypeCodeBarcodeList-content').dialog({
            title: title,
            width: 350,
            height: 250,
            closed: true,
            cache: false,
            href: url,
            maximizable:true,
            resizable:true,
            modal: true,
            onLoad:function(){
            },
            onClose:function(){
                $('#htgoldtax-dialogOper-jsTaxTypeCodeBarcodeList-content').window("destroy");
            }
        });
        $('#htgoldtax-dialogOper-jsTaxTypeCodeBarcodeList-content').dialog('open');
    }
    var jsTaxTypeCodeBarcode_getAjaxContent = function (param, url) { //同步ajax
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
