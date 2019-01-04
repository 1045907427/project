<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客户库存清除</title>
</head>
<body>
<form action="crm/dataport/clearCustomerSummary.do" id="crm-form-customerSummaryClearPage" method="post">
<table>
    <tr>
        <td>客&nbsp;&nbsp;户：</td>
        <td>
            <input type="text" id="crm-customerid-customerSummaryClearPage" name="customerid" style="width: 200px;"/>
        </td>
    </tr>
</table>
</form>
<div class="buttonDetailBG" style="height:30px;text-align:center;">
    <input type="button" id="crm-save-customerSummaryReportPage" value="确定"/>
</div>
<script type="text/javascript">
    $(function(){
        //客户参照窗口
        $("#crm-customerid-customerSummaryClearPage").customerWidget({
            required:true
        });

        $("#crm-save-customerSummaryReportPage").click(function(){
            $.messager.confirm("确认","确定要清除选中客户所有的库存商品？",function(r){
                if(r){
                    $("#crm-form-customerSummaryClearPage").submit();   
                }                
            });
        });

        $("#crm-form-customerSummaryClearPage").form({
            onSubmit: function(){
                var flag = $(this).form('validate');
                if(flag==false){
                    return false;
                }
                loading("提交中..");
            },
            success:function(json){
                var data = $.parseJSON(json);
                loaded();
                $.messager.alert("提醒",data.msg);
                $("#crm-clearSummary-dialog-content").dialog("close");
                $("#crm-table-customerSummaryReportPage").datagrid('reload');

            }
        });

    });
</script>
</body>

</html>
