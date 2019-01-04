<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客户库存更新</title>
</head>
<body>
<form action="crm/dataport/updateCustomerSummary.do" id="crm-form-customerSummaryPage" method="post">
<table>
    <tr>
        <td>客&nbsp;&nbsp;户：</td>
        <td>
            <input type="text" id="crm-customerid-customerSummaryPage" name="customerid" style="width: 200px;"/>
        </td>
    </tr>
    <tr>
        <td>开始日期：</td>
        <td>
            <input type="text" id="customerSummaryReportPage-begindate" name="begindate" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }',isShowClear:false})" style="width: 200px;" />
            <input type="hidden" id="begindate" name="begindate1" />
        </td>
    </tr>
    <tr>
        <td>结束日期：</td>
        <td>
            <input type="text" id="customerSummaryReportPage-enddate" name="enddate" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }',isShowClear:false})" style="width: 200px;" value="${today}"/>
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
        $("#crm-customerid-customerSummaryPage").customerWidget({
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
                            $("#customerSummaryReportPage-begindate").val(json.date);
                            $("#begindate").val(json.date);
                            $("#customerSummaryReportPage-begindate").attr("disabled", true);
                        }
                    }
                });
            },
            onClear: function (data) {
                $("#customerSummaryReportPage-begindate").removeAttr("disabled");
                $("#customerSummaryReportPage-begindate").val("");
                $("#begindate").val("");
            }
        });

        $("#crm-save-customerSummaryReportPage").click(function(){
            $("#crm-form-customerSummaryPage").submit();
        });

        $("#crm-form-customerSummaryPage").form({
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
                $("#crm-updateSummary-dialog-content").dialog("close");
                $("#crm-table-customerSummaryReportPage").datagrid('reload');

            }
        });

    });
</script>
</body>

</html>
