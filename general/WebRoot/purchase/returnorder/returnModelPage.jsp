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
            param:[{field:'modeltype',op:'equal',value:'6'}],
            width:150,
            onSelect:function(data){
                $("#importModel-order-file").trigger("click");
                url = data.url+"?gtype="+data.gtype+"&fileparam="+data.fileparam+"&pricetype="+data.pricetype;
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
        $("#returnOrder-import-dialog").dialog('close');
        $("#purchase-table-returnOrderListPage").datagrid('reload', 'purchase/returnorder/showReturnOrderPageList.do');
    }

</script>

<div class="nodisplay">
    <iframe name="dummy_frame" style="display: none;" src="common/upload/modelMsgPage.jsp">
    </iframe>
</div>
<div align="center">
    <table>
        <tr>
            <td colspan="2"><font style="color: red;algin:center">必填项：商品（编码或条形码）,数量,供应商编码,仓库名称</font></td>
        </tr>
        <tr>
            <td colspan="2"><font style="color: red;algin:center">选填项：单价</font></td>
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

