<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>模板上传</title>
</head>
<body>
<script>
    $(function(){

        //入库仓库
        $("#rejectBill-storage-rejectModelPage").widget({
            width:150,
            referwid:'RL_T_BASE_STORAGE_INFO',
            singleSelect:true,
            onlyLeafCheck:false
        });
        //司机
        $("#rejectBill-driver-rejectModelPage").widget({
            width:150,
            referwid:'RL_T_BASE_PERSONNEL_LOGISTICS',
            singleSelect:true,
            onlyLeafCheck:false
        });

        var url = "";
        $("#importSet").widget({
            referwid:"RL_T_ORDER_IMPORT",
            singleSelect:true,
            param:[{field:'modeltype',op:'equal',value:'5'}],
            width:150,
            onSelect:function(data){
                console.log(data);
                var storageid = $("#rejectBill-storage-rejectModelPage").widget('getValue');
                var driverid = $("#rejectBill-driver-rejectModelPage").widget('getValue');
                var billtype = $("#rejectBill-billtype-rejectModelPage").val();
                $("#importModel-order-file").trigger("click");
                url = data.url+"?busid="+data.busid+"&gtype="+data.gtype+"&fileparam="+data.fileparam+"&ctype="+data.ctype
                        +"&pricetype="+data.pricetype+"&storageid="+storageid+"&driverid="+driverid+"&billtype="+billtype;
            }
        });

        $("#importModel-order-file").change(function(){
            $.messager.confirm("提醒","确定导入该文件？", function(r){
                if(r){
                    document.form.action = url;
                    $("#common_form_import").submit();
                }else{
                    $("#common_form_import").form("reset");
                }
            });
        });
    });

    function showMsg(msg) {
        var errorfileid = "";
        if(msg.indexOf("&") > 0){
            var index = msg.indexOf("&");
            errorfileid = msg.substr(index+1,msg.length);
            msg = msg.substr(0,index);
        }
        if(errorfileid != ""){
            msg = msg+"<br/><a href=\"common/download.do?id="+errorfileid+"\" target=\"_blank\">"+"点击下载出错记录"+"</a>";
        }
        $.messager.alert('导入提醒',msg);
        $("#rejectBill-import-dialog").dialog('close');
        $("#sales-datagrid-rejectBillListPage").datagrid('reload', 'sales/getRejectBillList.do');
    }

</script>

<div class="nodisplay">
    <iframe name="dummy_frame" style="display: none;" src="common/upload/modelMsgPage.jsp">
    </iframe>
</div>
<div align="center">
    <table>
        <tr>
            <td colspan="2"><font style="color: red;algin:center">必填项：商品编码,数量,退货类型,客户</font></td>
        </tr>
        <tr>
            <td colspan="2"><font style="color: red;algin:center">选填项：单价,金额,入库仓库,司机</font></td>
        </tr>
        <tr>
            <td>司&nbsp;&nbsp;&nbsp;&nbsp;机:</td>
            <td>
                <input id="rejectBill-driver-rejectModelPage" name="driverid" style="width: 150px"/>
            </td>
        </tr>
        <tr>
            <td>入库仓库:</td>
            <td>
                <input id="rejectBill-storage-rejectModelPage" name="storageid" style="width: 150px"/>
            </td>
        </tr>
        <tr>
            <td>退货类型:</td>
            <td>
                <select id="rejectBill-billtype-rejectModelPage" style="width: 150px;">
                    <option value="1">直退</option>
                    <option value="2">售后退货</option>
                </select>
            </td>
        </tr>
        <tr>
            <td >模板文件:</td>
            <td><input id="importSet"  style="width: 150px;" /></td>
        </tr>
    </table>
</div>

<form action="" name="form" id="common_form_import" style="position:relative;overflow:hidden;height:50px;" method="post" enctype="multipart/form-data" target="dummy_frame">
    <input type="file" id="importModel-order-file" name="importFile" style="width:0px;right:0;position:absolute;opacity:0;z-index:2;" /><br/>
</form>
</body>
</html>
