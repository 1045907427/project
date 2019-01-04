<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>帐套会计日期设置</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',split:false,border:false,fit:true" style="padding: 15px;">
        <form id="form-accountDatePage" action="accesscontrol/modifyAccountDate.do" method="post">
            <table>
                <tr>
                    <td>操作日期：</td>
                    <td>
                        <input name="cdate" disabled="disabled" id="select_ledger_cdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${date}"  style="width: 150px;"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'south'" style="text-align: right;height: 28px">
        <a href="javaScript:void(0);" id="save-accountDatePage" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="确定">确定</a>
    </div>
</div>
<script type="text/javascript">
//    document.getElementById(elemId).onfocus=function(){return new WdatePicker({skin:'whyGreen',dateFmt:fmtStr});};
    $(function () {
        <security:authorize url="/accesscontrol/changeAccountDate.do">
        var IsOpenBusDate='${IsOpenBusDate}';
        if('1'==IsOpenBusDate) {
            $("#select_ledger_cdate").removeAttr('disabled');
        }
        </security:authorize>
        $("#save-accountDatePage").click(function () {
            $("#form-accountDatePage").submit();
        });

        $("#form-accountDatePage").form({
            onSubmit:function () {
                loading("提交中..");
            },
            success:function(data){
                loaded();
                if(data){
                    window.location.reload();
                    $.messager.alert("提醒","切换操作日期成功!");
                }else{
                    $.messager.alert("提醒","切换操作日期失败!");
                }
                $('#sysUser-dialog-accountDate').dialog("close");
            }
        });
    })
</script>
</body>
</html>
