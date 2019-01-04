<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售订单模板信息添加</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:true">
        <div align="center">
            <form action="" method="post" id="importModel-form-addPage" style="padding: 10px;">
                <table cellpadding="3" cellspacing="3" border="0">
                    <tr>
                        <td width="80px" align="left">模板类型:</td>
                        <td align="left">
                            <select name="importSet.modeltype" style="width: 200px;" id="importModel-add-modeltype" >
                                <option value="1">销售订单模板</option>
                                <option value="2">代配送模板</option>
                                <option value="3">客户销量模板</option>
                                <option value="4">客户库存模板</option>
                                <option value="5">销售退货通知单</option>
                                <option value="6">采购退货通知单</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td width="80px" align="left">模板描述名:</td>
                        <td align="left"><input type="text" id="importModel-add-name" name="importSet.name" class="easyui-validatebox" required="true" style="width:200px;"/></td>
                    </tr>
                    <tr>
                        <td width="80px" align="left">模版url:</td>
                        <td align="left">
                            <input type="text" id="importModel-add-url" onchange="showDialog();"  name="importSet.url"  required="true"  style="width:200px;"/>
                        </td>
                    </tr>
                    <tr id="supplierid" hidden="true">
                        <td>供应商编号：</td>
                        <td><input id="importModel-supplierid-addPage" name="importSet.supplierid" style="width: 200px" /></td>
                    </tr>
                    <tr id="customertype">
                        <td width="80px" align="left">客户类型:</td>
                        <td align="left" id="importModel-tr-ctype">
                            <select id="importModel-form-add-ctype" name="importSet.ctype" style="width: 200px;">
                                <option value=""></option>
                                <option value="1">指定客户编号</option>
                                <option value="2">指定总店按店号分配</option>
                                <option value="3">按客户助记码分配</option>
                                <option value="4">按客户名称分配</option>
                                <option value="5">按客户简称分配</option>
                                <option value="6">按客户地址分配</option>
                            </select>
                        </td>
                    </tr>
                    <tr id="busid">
                        <td>客户编号：</td>
                        <td id="importModel-addPage-busid"><input id="importModel-busid-addPage" name="importSet.busid" style="width: 200px" disabled/></td>
                    </tr>
                    <tr id="goodsimport-type">
                        <td width="80px" align="left">导入类型:</td>
                        <td align="left">
                            <select name="importSet.gtype" style="width: 200px;">
                                <option value="1">按条形码导入</option>
                                <option value="2">按店内码导入</option>
                                <option value="3">按助记符导入</option>
                                <option value="4">按商品编号导入</option>
                            </select>
                        </td>
                    </tr>
                    <tr id="delivery-goodsimport-type" hidden="true">
                        <td width="80px" align="left">导入类型:</td>
                        <td align="left">
                            <select style="width: 200px;">
                                <option value=""></option>
                                <option value="1">按条形码导入</option>
                                <option value="4">按商品编号导入</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                    <tr id="catch-price">
                        <td>取价方式</td>
                        <td>
                            <select name="importSet.pricetype" style="width: 200px;" id="importModel-form-pricetype">
                                <option value="0" <c:if test="${importSet.pricetype == 0}">selected="selected"</c:if>>取系统价格</option>
                                <option value="1" <c:if test="${importSet.pricetype == 1}">selected="selected"</c:if>>取导入时模板价格</option>
                            </select>
                        </td>
                    </tr>
                    <tr  id="model-remark">
                        <td>自适应类型：</td>
                        <td align="left">
                            <input type="text" id="importModel-add-remark"  name="importSet.remark"  style="width:200px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="80px" align="left">状&nbsp;&nbsp;态:</td>
                        <td align="left">
                            <select name="importSet.state">
                                <option value="1" selected>启用</option>
                                <option value="0">禁用</option>
                            </select>
                        </td>
                    </tr>
                    <tr id="fileparam">
                        <td>参数信息：</td>
                        <td align="left">
                            <textarea name="importSet.fileparam" id="importModel-fileparam-addPage" style="width: 200px;height: 50px;"></textarea>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div data-options="region:'south'" style="height: 30px;border: 0px;">
        <div align="right">
            <a href="javaScript:void(0);" id="importModel-form-add-save" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'">保存</a>
        </div>
    </div>
</div>
<script type="text/javascript">

    function showDialog(){
        var url=$("#importModel-add-url").val();
        if(url == "sales/model/importHtmlBydigitalParam.do"){
            $("#importModel-tr-ctype").empty();
            $('<input id="importModel-add-ctype" name="importSet.ctype" readonly style="width: 200px;" class="no_input" />').
                    appendTo("#importModel-tr-ctype");
            $("#importModel-add-ctype").val("指定客户编号");
            $("#importModel-busid-addPage").customerWidget({
                singleSelect:true,
                required:true
            });
            $("#fileparam").show();
            $("#goodsimport-type").hide();
            $("#catch-price").hide();
            $("#model-remark").hide();

            document.getElementById('importModel-add-url').readOnly=true;
        }
        if(url == "sales/model/importByDigitalParam.do" || url == "sales/model/importAdaptiveExcelByColumn.do"
                || url == "sales/model/importHtmlBydigitalParam.do" || url == "crm/customerStorageOrder/importCustStorageModel.do"
                || url == "crm/terminal/importCrmOrderModel.do" || url == "delivery/importDeliveryOrderModel.do"){
            $("#fileparam").show();
            $("#goodsimport-type").hide();
            $("#catch-price").hide();
            $("#model-remark").hide();
        }else{
            $("#fileparam").hide();
            $("#goodsimport-type").show();
            $("#catch-price").show();
            $("#model-remark").show();
        }

    }


    $(function(){

        $("#importModel-form-add-ctype").combobox({
            onChange: function (newValue, oldValue) {
                $("#importModel-addPage-busid").empty();
                if(newValue == '1'){
                    $('<input id="importModel-busid-addPage-1" name="importSet.busid" style="width: 200px" />').
                            appendTo("#importModel-addPage-busid");
                    $("#importModel-busid-addPage-1").customerWidget({
                        singleSelect:true,
                        required:true
                    });
                }else if(newValue == '2'){
                    $('<input id="importModel-busid-addPage-2" name="importSet.busid" style="width: 200px" />').
                            appendTo("#importModel-addPage-busid");
                    $("#importModel-busid-addPage-2").widget({
                        referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
                        singleSelect:true,
                        required:true
                    });
                    $("#fileparam").show();
                }else if(newValue == '7'){
                    $("#busid").hide();
                }else{
                    var url=$("#importModel-add-url").val();
                    $('<input id="importModel-busid-addPage-3" name="importSet.busid" style="width: 200px" disabled/>').
                            appendTo("#importModel-addPage-busid");
                    if(url == "sales/model/importByDigitalParam.do" || url == "sales/model/importAdaptiveExcelByColumn.do"
                            || url == "sales/model/importHtmlBydigitalParam.do" || url == "crm/customerStorageOrder/importCustStorageModel.do"
                            || url == "crm/terminal/importCrmOrderModel.do" || url == "delivery/importDeliveryOrderModel.do" ){
                        $("#fileparam").show();
                        $("#delivery-goodsimport-type").val("");
                    }else{
                        $("#fileparam").hide();
                    }

                }
            }
        });
        $("#importModel-add-modeltype").combobox({
            onChange: function (newValue, oldValue) {
                if ("1" == newValue){
                    $("#supplierid").hide();
                    $("#delivery-goodsimport-type").hide();
                    $("#goodsimport-type").show();
                }else if("2" == newValue){
                    $("#importModel-form-add-ctype").combobox("setValue","1");
                    $("#supplierid").show();
                    $("#delivery-goodsimport-type").show();
                    $("#goodsimport-type").hide();
                }
            }
        });
        //供应商
        $("#importModel-supplierid-addPage").supplierWidget({
            required:true
        });

        $("#importModel-add-file").webuploader({
            title: '模板上传',
            filetype:'Files',
            close:true,//完成上传后是否自动关闭上传窗口
            fileNumLimit:1,//验证文件总数量, 超出则不允许加入队列，默认300
            disableGlobalDnd:false,//禁用拖动文件功能
            description:'',//描述
            onComplete: function(data){
                $("#importModel-filename").val(data[0].oldFileName);
                $("#importModel-fullPath").val(data[0].fullPath);
                $("#importModel-filename").show();
            }
        });

        $("#importModel-form-add-save").click(function(){

            var name=$("#importModel-add-name").val();
            if(name == ""){
                $.messager.alert("提醒","请填写模板描述名!");
                $("#importModel-add-name").focus();
                return false;
            }
            var url=$("#importModel-add-url").val();
            if(url == ""){
                $.messager.alert("提醒","请填写解析该模板对应的url!");
                $("#importModel-add-url").focus();
                return false;
            }else{
                var index = url.indexOf(".do");
                if(index < 0){
                    $.messager.alert("提醒","请填写正确的url!");
                    $("#importModel-add-url").focus();
                    return false;
                }
            }
            var modeltype = $("#importModel-add-modeltype").combobox('getValue');
            if(modeltype == ""){
                $.messager.alert("提醒","请填写模板类型!");
                $("#importModel-add-modeltype").focus();
                return false;
            }
            //客户类型 自适应方法和普通的模板方法保存时进行区分
            if(url != "sales/model/importAdaptiveExcelByColumn.do" && url != "sales/model/importHtmlBydigitalParam.do"
                    && url != "sales/model/importByDigitalParam.do"  && url != "crm/customerStorageOrder/importCustStorageModel.do"
                    && url != "crm/terminal/importCrmOrderModel.do"){

                var ctype = $("#importModel-form-add-ctype").combobox('getValue');
                if(ctype == ""){
                    $.messager.alert("提醒","请填写客户类型!");
                    $("#importModel-form-add-ctype").combobox().next('span').find('input').focus();
                    return false;
                }

                if(ctype == '1'){
                    var busid = $("#importModel-busid-addPage-1").val();
                    if(busid == ""){
                        $.messager.alert("提醒","请指定客户编码!");
                        return false ;
                    }
                    $("#importModel-busid-addPage-1").focus();
                }else if(ctype == "2"){
                    var busid = $("#importModel-busid-addPage-2").val();
                    if(busid == ""){
                        $.messager.alert("提醒","请指定客户编码!");
                        return false ;
                    }
                    $("#importModel-busid-addPage-2").focus();
                }
            }else{
                var busid = $("#importModel-busid-addPage").customerWidget('getValue');
                if(busid == ""){
                    $.messager.alert("提醒","请指定客户编码!");
                    $("#importModel-busid-addPage").focus();
                    return false ;
                }
                var fileparam = $("#importModel-fileparam-addPage").val();
                if(fileparam == ""){
                    $.messager.alert("提醒","请填写模板参数!");
                    $("#importModel-busid-addPage").focus();
                    return false ;
                }
            }


            $.messager.confirm("提醒","是否添加模板信息?",function(r){
                if(r){
                    var formdata=$("#importModel-form-addPage").serializeJSON();

                    loading('提交中..');
                    $.ajax({
                        type: 'post',
                        cache: false,
                        url: 'sales/manager/addImportModel.do',
                        data: formdata,
                        dataType:'json',
                        success:function(data){
                            loaded();
                            if(data.flag){
                                $.messager.alert("提醒","添加成功!");
                                $("#saleOrderModel-dialog-add-operate").dialog('close',true);
                                $("#saleOrderModel-query-importModelList").trigger("click");
                            }else{
                                if(data.msg){
                                    $.messager.alert("提醒","添加失败!"+data.msg);
                                }else{
                                    $.messager.alert("提醒","添加失败!");
                                }
                            }
                        }
                    });
                }
            });


        });

    });

</script>

</body>
</html>
