<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    Cookie cookies[] = request.getCookies();
    if (cookies != null && cookies.length > 0) {
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName()+","+cookie.getValue());
        }
    }
%>
<html>
<head>
    <title></title>
</head>
<body>
<br/>
<a >&nbsp;&nbsp;保存成功，是否继续添加销售订单？<br/>&nbsp;&nbsp;&nbsp;否则跳转至商品查看页面.</a>
<br/><br/>
<div style="text-align: left">
    <label class="divtext">
        <input type="checkbox" id="sales-check-messageRedictPage" class="checkbox1" checked="checked"/>勾选默认选择当前操作</label>
</div>
<br/><br/><br/>
<div class="buttonDetailBG" style="height:30px;text-align:center;">
    <input type="button"  value="是" id="sales-save-messageRedictPage" />
    <input type="button"  value="否" id="sales-cancel-messageRedictPage" />
</div>
<script type="text/javascript">

    $("#sales-save-messageRedictPage").click(function(){
        $("#sales-dialog-version-orderPage").dialog('close');
        $("#sales-buttons-orderPage").buttonWidget("addNewDataId", '${id}');
        $("#sales-panel-orderPage").panel('refresh', 'sales/orderAddPage.do');

        var checkbox = document.getElementById('sales-check-messageRedictPage');
        if(checkbox.checked){
            document.cookie="addCheck=add";
        }

    });

    $("#sales-cancel-messageRedictPage").click(function(){
        $("#sales-dialog-version-orderPage").dialog('close');
        $("#sales-setContractPrice-orderAddPage").show();
        <%--$("#sales-panel-orderPage").panel('refresh', 'sales/orderEditPage.do?id=${id}');--%>
        var checkbox = document.getElementById('sales-check-messageRedictPage');
        if(checkbox.checked){
            document.cookie="addCheck=edit";
        }
    });



</script>

</body>
</html>
