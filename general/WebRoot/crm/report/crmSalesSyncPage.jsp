<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客户销量更新</title>
</head>
<body>
<form action="crm/dataport/crmSalesSync.do" id="crm-form-crmSalesSyncPage" method="post">
    <table>
        <tr>
            <td>客&nbsp;&nbsp;户：</td>
            <td>
                <input type="text" id="crm-customerid-crmSalesSyncPage" name="customerid" style="width: 200px;"/>
            </td>
        </tr>
        <tr>
            <td>开始日期：</td>
            <td>
                <input type="text" id="crmSalesSyncPage-begindate" name="begindate" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" style="width: 200px;" />
            </td>
        </tr>
        <tr>
            <td>结束日期：</td>
            <td>
                <input type="text" id="crmSalesSyncPage-enddate" name="enddate" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" style="width: 200px;"/>
                <input type="hidden" id="enddate" name="enddate1" />
            </td>
        </tr>
    </table>
</form>
<div class="buttonDetailBG" style="height:30px;text-align:center;">
    <input type="button" id="crm-save-crmSalesSyncPage" value="确定"/>
</div>
<script type="text/javascript">
    $(function(){
        //客户参照窗口
        $("#crm-customerid-crmSalesSyncPage").customerWidget({
            required:true,
            onSelect:function(data){
                $.ajax({
                    url:'crm/dataport/getLastSummaryDayForCustomer.do',
                    dataType:'json',
                    type:'post',
                    data:{customerid:data.id},
                    async:false,
                    success:function(json){
                        if(null != json.date){
                            $("#crmSalesSyncPage-enddate").val(json.date);
                            $("#enddate").val(json.date);
                            $("#crmSalesSyncPage-enddate").attr("disabled", true);
                        }
                    }
                });
            },
            onClear: function (data) {
                $("#crmSalesSyncPage-enddate").removeAttr("disabled");
                $("#crmSalesSyncPage-enddate").val("");
                $("#enddate").val("");
            }
        });

        $("#crm-save-crmSalesSyncPage").click(function(){
            var begindate = $("#crmSalesSyncPage-begindate").val();
            var enddate = $("#crmSalesSyncPage-enddate").val();
            if(begindate == ""){
                $.messager.alert("提醒","请填写开始日期");
                return false;
            }
            if(enddate == ""){
                $.messager.alert("提醒","请填写结束日期");
                return false;
            }
            $("#crm-form-crmSalesSyncPage").submit();
        });

        $("#crm-form-crmSalesSyncPage").form({
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
                $.messager.alert("提醒",json.msg);
                $("#crm-updateSales-dialog-content").dialog("close");
                $("#crm-table-terminalSalesOrderReportPage").datagrid('reload');

            }
        });

    });
</script>
</body>

</html>
