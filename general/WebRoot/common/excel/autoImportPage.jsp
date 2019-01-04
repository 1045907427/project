<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>自选导入</title>
</head>
<body>
<form action="common/readImportFile.do" method="post" id="common-form-autoImportPage" enctype="multipart/form-data" >
    <div style="width:350px;height:150px;overflow:hidden;margin: 5px;">
        <input type="file" id="autoImport-order-file" name="importFile" style="width:0px;right:0;position:absolute;opacity:0;z-index:2;" /><br/>
        <table>
            <tr id="fileType-tr">
                <td>导入格式:</td>
                <td>
                    <input type="radio" name="fileType" value="excel" style="cursor: pointer" checked="checked"/>excel
                    <input type="radio" name="fileType" value="csv" style="cursor: pointer"/>csv/txt
                    <input type="radio" name="fileType" value="html" style="cursor: pointer"/>html
                </td>
            </tr>
            <tr>
                <td>模板导入方法:</td>
                <td>
                    <input type="text" class="easyui-validatebox" id="common-method-autoImportPage" required="required"  style="width: 200px;"/>
                    <input type="hidden" id="common-url-autoImportPage" name="url" />
                    <input type="hidden" id="common-param-autoImportPage" name="param" />
                </td>
            </tr>
            <tr>
                <td width="80px" align="left">客户类型:</td>
                <td align="left">
                    <input name="customerRefferid"id="customerRefferid" type="hidden" />
                    <select id="common-ctype-autoImportPage" style="width: 200px;">
                        <option></option>
                        <option value="1">指定客户编号</option>
                        <option value="2">指定总店按店号分配</option>
                        <option value="3">按客户助记码导入</option>
                        <option value="4">按客户名称导入</option>
                        <option value="5">按客户简称导入</option>
                        <option value="6">按客户地址导入</option>
                        <option value="7">按客户编码导入</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td width="80px" align="left">商品类型:</td>
                <td align="left">
                    <select id="common-gtype-autoImportPage" style="width: 200px;">
                        <option></option>
                        <option value="1">按条形码导入</option>
                        <option value="2">按店内码导入</option>
                        <option value="3">按助记符导入</option>
                        <option value="4">按商品编号导入</option>
                    </select>
                </td>
            </tr>
        </table>
    </div>
    <table id="common-datagrid-autoImportPage"></table>
    <input type="hidden" id="goodsValue"  />
    <input type="hidden" id="goodsNumValue" />
    <input type="hidden" id="goodsBoxnumValue" />
    <input type="hidden" id="goodsPriceValue" />

    <input type="hidden" id="otherInfo" />
    <input type="hidden" id="remark" />
    <input type="hidden" id="customerBillid" />
    <input type="hidden" id="customerInfo"  />
    <input type="hidden" id="customerRegular"  />
    <input type="hidden" id="divideValue"/>
    <input type="hidden" id="customerValue" />
    <input type="hidden" id="remarkValue" />

    <div class="buttonDetailBG" style="height:30px;text-align:right;">

        <input type="button" hidden="hidden" value="确定" name="savegoon" id="common-save-autoImportPage" />
    </div>

</form>

<script type="text/javascript">

    $(":radio").click(function () {
        console.info($(this).val());
    });

    $("#autoImport-order-file").change(function(){
        $("#common-form-autoImportPage").submit();
    });


    $("#common-method-autoImportPage").widget({
        referwid:"RL_T_ORDER_IMPORT",
        singleSelect:true,
        param:[{field:'modeltype',op:'equal',value:'1'}],
        width:200,
        onSelect:function(data){
            $("#autoImport-order-file").trigger("click");
            $("#common-url-autoImportPage").val(data.url);
            $("#common-param-autoImportPage").val(data.fileparam);
            $("#common-ctype-autoImportPage").val(data.ctype);
            $("#common-gtype-autoImportPage").val(data.gtype);
            $("#customerRefferid").val(data.busid);
        }
    });


    $(function () {

        $('#common-form-autoImportPage').form({
            onSubmit: function(){
                var flag = $(this).form('validate');
                if(flag==false){
                    return false;
                }
                loading("提交中..");
            },
            success:function(data){
                loaded();
                var json = $.parseJSON(data);
                if(json.flag == undefined ){
                    $("#goodsValue").val(json.goodsValue);
                    $("#goodsNumValue").val(json.goodsNumValue);
                    $("#goodsBoxnumValue").val(json.goodsBoxnumValue);
                    $("#goodsPriceValue").val(json.goodsPriceValue);

                    $("#otherInfo").val(json.otherInfo);
                    $("#remark").val(json.remark);
                    $("#customerBillid").val(json.customerBillid);
                    $("#customerInfo").val(json.customerInfo);
                    $("#customerRegular").val(json.customerRegular);

                    $("#divideValue").val(json.divideValue);
                    $("#customerValue").val(json.customerValue);
                    $("#remarkValue").val(json.remarkValue);

                    $("#common-datagrid-autoImportPage").datagrid({
                        columns: [[
                            {field: 'paramname', title: '参数名称', width: 120,algin:'center', sortable: true},
                            {field: 'modelCol', title: '模板列', width: 120,algin:'center', sortable: true}
                        ]],
                        method: 'post',
                        data: JSON.parse(json.paramJson),
                        singleSelect: false,
                        checkOnSelect: true,
                        selectOnCheck: true

                    }).datagrid("columnMoving");

                    if(json.modelfile != undefined){
                        $("#fileType-tr").append(
                            "<a href=\"common/download.do?id="+json.modelfile+"\" target=\"_blank\">"+"非excel数据.xls"+"</a>");
                    }
                    $("#common-save-autoImportPage").show();
                    $("#import-button").hide();
                    $(":radio").attr("disabled",true);

                    if(json.goodsValue == ""){
                        $.messager.alert('导入提醒','读取商品信息失败,请检查模板和参数是否对应.');
                    }

                    if(json.goodsNumValue == "" && json.goodsBoxnumValue == "" ){
                        $.messager.alert('导入提醒','读取商品数量信息失败,请检查模板和参数是否对应.');
                    }

                    if(json.msg != undefined){
                        $.messager.alert('导入提醒',json.msg);
                    }
                }else{
                    $("#salesorder-importModel-dialog").dialog('close');
                    $.messager.alert('导入提醒',"导入失败!方法参数值只能为大写字母或数字，请检查模板方法参数值.");
                }

            }

        });

        $("#common-save-autoImportPage").click(function () {
            var goodsValue = $("#goodsValue").val();
            var goodsNumValue = $("#goodsNumValue").val();
            var goodsBoxnumValue = $("#goodsBoxnumValue").val();

            var goodsPriceValue = $("#goodsPriceValue").val();
            var otherInfo = $("#otherInfo").val();
            var remark = $("#remark").val();
            var customerBillid = $("#customerBillid").val();
            var customerInfo = $("#customerInfo").val();
            var customerRegular = $("#customerRegular").val();
            var customerRefferid = $("#customerRefferid").val();
            var ctype = $("#common-ctype-autoImportPage").val();
            var gtype = $("#common-gtype-autoImportPage").val();

            var divideValue = $("#divideValue").val();
            var customerValue = $("#customerValue").val();
            var remarkValue = $("#remarkValue").val();
            $.ajax({
                url: 'common/ImportMapToObject.do',
                type: 'post',
                data: {goodsValue:goodsValue,goodsNumValue:goodsNumValue,goodsBoxnumValue:goodsBoxnumValue,goodsPriceValue:goodsPriceValue,
                    ctype:ctype,gtype:gtype,otherInfo:otherInfo,customerBillid:customerBillid,customerInfo:customerInfo,remark:remark,
                    customerRegular:customerRegular,customerRefferid:customerRefferid,divideValue:divideValue,customerValue:customerValue,remarkValue:remarkValue},
                dataType: 'json',
                async: false,
                success: function (json) {
                    var errorfileid = "";
                    var msg = json.msg;
                    if(msg.indexOf("&") > 0){
                        var index = msg.indexOf("&");
                        errorfileid = msg.substr(index+1,msg.length);
                        msg = msg.substr(0,index);
                    }
                    if(errorfileid != ""){
                        msg = msg+"<br/><a href=\"common/download.do?id="+errorfileid+"\" target=\"_blank\">"+"点击下载出错记录"+"</a>";
                    }
                    $.messager.alert('导入提醒',msg);
                    $("#salesorder-importModel-dialog").dialog('close');

                    $("#sales-datagrid-orderListPage").datagrid('reload', 'sales/getOrderList.do');

                }
            });

        });


    });


</script>

</body>
</html>
