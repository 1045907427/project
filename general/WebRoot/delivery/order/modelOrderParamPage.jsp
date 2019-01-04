<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>模板上传</title>
</head>
<body>
<script>
    $(function(){

        var url = "";
        $("#importSet").widget({
            referwid:"RL_T_ORDER_IMPORT",
            singleSelect:true,
            param:[{field:'modeltype',op:'equal',value:'2'}],
            width:180,
            onSelect:function(data){
                $("#importModel-order-file").trigger("click");
                var test = data.url;
                if(test.indexOf("?") == -1){
                    url = data.url+"?busid="+data.busid+"&gtype="+data.gtype+"&fileparam="+data.fileparam+"&pricetype="+data.pricetype+"&supplierid="+data.supplierid;
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
        $("#delivery-import-dialog").dialog('close');
        $("#delivery-table-showDeliveryOrderList").datagrid('reload', 'delivery/showDeliveryOrderList.do');
    }

</script>

<div class="nodisplay">
    <iframe name="dummy_frame" style="display: none;" src="common/upload/modelMsgPage.jsp">
    </iframe>
</div>
<div>
    选择需导入的模板文件:<br/>
    <input id="importSet"  class="len180"  /><br/>
</div>

<form action="" name="form" id="common_form_import" style="position:relative;overflow:hidden;height:50px;" method="post" enctype="multipart/form-data" target="dummy_frame">
    <input type="file" id="importModel-order-file" name="importFile" style="width:0px;right:0;position:absolute;opacity:0;z-index:2;" /><br/>
</form>

</body>
</html>
