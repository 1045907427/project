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
            param:[{field:'modeltype',op:'equal',value:'1'}],
            width:180,
            onSelect:function(data){
                $("#importModel-order-file").trigger("click");
                var test = data.url;
                if(test.indexOf("?") == -1 && data.fileparam != undefined){
                    if(data.fileparam.indexOf("客户正则") > 0 ){
                        url = data.url+"?busid="+data.busid+"&ctype="+data.ctype+"&gtype="+data.gtype+"&id="+data.id+"&pricetype="+data.pricetype;
                    }else{
                        url = data.url+"?busid="+data.busid+"&ctype="+data.ctype+"&gtype="+data.gtype+"&fileparam="+data.fileparam+"&pricetype="+data.pricetype;
                    }
                }else{
                    url = data.url;
                    $("#salesorder-import-dialog").dialog('close');
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
        $("#salesorder-import-dialog").dialog('close');
        $("#sales-datagrid-orderListPage").datagrid('reload', 'sales/getOrderList.do');
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
