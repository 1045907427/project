<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>模板上传</title>
</head>
<body>
<script>
    var type = '${type}';
    $(function(){
        var url = "";
        $("#importSet").widget({
            referwid:"RL_T_ORDER_IMPORT",
            singleSelect:true,
            param:[{field:'modeltype',op:'equal',value:type}],
            width:180,
            onSelect:function(data){
                $("#importModel-order-file").trigger("click");
                var test = data.url;
                if(test.indexOf("?") == -1){
                    url = data.url+"?busid="+data.busid+"&gtype="+data.gtype+"&ctype="+data.ctype+"&fileparam="+data.fileparam+"&pricetype="+data.pricetype;
                }else{
                    url = data.url;
                    $("#salesorder-import-dialog").dialog('close')  ;
                }
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

        $.messager.alert('提醒', msg);
        $("#crm-importModel-dialog").dialog('close');
        if(type == "3"){
            $("#crm-table-terminalSalesOrderList").datagrid('reload');
        }else  if(type == "4"){
            $("#crm-table-customerStorageOrderListPage").datagrid('reload');
        }

    }

</script>

<div class="nodisplay">
    <iframe name="dummy_frame" style="display: none;" src="common/upload/modelMsgPage.jsp">
    </iframe>
</div>
<div>
    <br/>
    &nbsp; &nbsp;<font style="color: red;algin:center">必填项：商品编码(条形码)，数量</font><br/>
    &nbsp; &nbsp;<font style="color: red;algin:center">选填项：商品零售价，商品成本价</font><br/>
    选择需导入的Excel模板文件(.xls/.xlsx):<br/>
    <input id="importSet" /><br/>
</div>

<form action="" name="form" id="common_form_import" style="position:relative;overflow:hidden;height:50px;" method="post" enctype="multipart/form-data" target="dummy_frame">
    <input type="file" id="importModel-order-file" name="importFile" style="width:0px;right:0;position:absolute;opacity:0;z-index:2;" /><br/>
</form>

</body>
</html>
