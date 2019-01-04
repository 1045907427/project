<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>费用分摊</title>
</head>
<body>
<form action="purchase/arrivalorder/addPurchaseShareAmount.do" method="post" id="purchase-form-arrivalorder">
    <table  border="0" style="width: 380px;">
        <tr>
            <td style="text-align: right;width: 100px;">单据编号:</td>
            <td style="text-align: left;">
                <input type="text"  name="id" style="width: 200px;" value="${param.id}" readonly="readonly"/>
            </td>
        </tr>
        <tr>
            <td style="text-align: right;width: 100px;">分摊类型:</td>
            <td style="text-align: left;">
                <select id="purchase-arrivalorder-type" name="type"  style="width: 200px;">
                    <option value="1">按数量</option>
                    <option value="2">按金额</option>
                    <option value="3">按重量</option>
                    <option value="4">按体积</option>
                    <option value="5">按单品条数</option>
                </select>
            </td>
        </tr>
        <tr>
            <td style="text-align: right;width: 100px;">分摊金额:</td>
            <td style="text-align: left;">
                <input type="text" id="purchase-arrivalorder-amount" name="amount" style="width: 200px;" class="easyui-validatebox easyui-numberbox" data-options="precision:2,required:true,validType:'intOrFloat'" />
            </td>
        </tr>
        <tr>
            <td style="text-align: right;width: 100px;">备注:</td>
            <td style="text-align: left;">
                <input type="text" name="remark" style="width: 200px;" id="purchase-arrivalorder-remark"  />
            </td>
        </tr>
    </table>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align:right;">
            <input type="button" value="确 定" name="savenogo" id="purchase-button-arrivalorder" />
        </div>
    </div>
</form>
<script type="text/javascript">

    $("#purchase-arrivalorder-type").change(function () {
        var type = $(this).val();
        $("#param-td").empty();
        if(type ==0){
            $('<input id="purchase-arrivalorder-param" name="param" disabled="disabled" />').appendTo("#param-td");
        }else if(type == 1){
            $('<input id="purchase-arrivalorder-param" name="param"/>').appendTo("#param-td");
            $("#purchase-arrivalorder-param").widget({
                referwid:'RL_T_BASE_GOODS_BRAND',
                param:[{field:'id',op:'in',value:'${brandStr}'}],
                width:200,
                singleSelect:true,
                required:true
            });
        }else if(type == 2){
            $('<input id="purchase-arrivalorder-param" name="param"/>').appendTo("#param-td");
            $("#purchase-arrivalorder-param").widget({
                referwid:'RL_T_BASE_GOODS_INFO',
                width:200,
                param:[{field:'id',op:'in',value:'${goodsStr}'}],
                singleSelect:true,
                required:true
            });

        }

    });

    $("#purchase-button-arrivalorder").click(function(){
        $("#purchase-form-arrivalorder").submit();
    })

    $("#purchase-form-arrivalorder").form({
        onSubmit:function () {
            var flag = $(this).form('validate');
            if(flag==false){
                return false;
            }
            loading("提交中..");
        },
        success:function(data){
            loaded();
            var json = $.parseJSON(data);
            if(json.flag){
                $.messager.alert("提醒","分摊成功！");
                var id = $("#purchase-backid-arrivalOrderAddPage").val();
                $("#purchase-panel-arrivalOrderPage").panel('refresh', 'purchase/arrivalorder/arrivalOrderEditPage.do?id=' + id);
                $('#purchase-share-dialog').dialog('close',true);
            }else{
                $.messager.alert("提醒","分摊失败！");
            }
        }
    });


    $("#purchase-arrivalorder-amount").numberbox({
        precision:2,
        required:true,
        validType:'intOrFloat',
        onChange:function(newValue,oldValue){
            var pushamount = newValue;
            if(newValue == 0){
                $.messager.alert("提醒","分摊金额不能为0");
                $('#purchase-arrivalorder-amount').numberbox("getValue");
                return false;
            }
        },
        formatter: function(value){
            if(value == 0){
                return "";
            }else{
                return value;
            }
        }
    });

</script>
</body>
</html>
