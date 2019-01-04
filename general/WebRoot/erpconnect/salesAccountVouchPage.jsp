<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title></title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form id="salesAccount-form-addacountvourch" action="erpconnect/addSalesAccountThirdVouch.do" method="post">
            <table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
                <tr>
                    <td>账套名称：</td>
                    <td>
                        <select id="sales-customer-databaseid" onchange="loadSubject()"  style="width: 200px;" name="dbid">
                            <c:forEach items="${dbList }" var="list">
                                <option value="${list.id }">${list.dbasename }</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>凭证类别：</td>
                    <td>
                        <select id="sales-customer-sign"  style="width: 200px;" name="sign">
                            <%--<c:forEach items="${signList }" var="list">--%>
                                <%--<option value="${list.id }">${list.codesign }</option>--%>
                            <%--</c:forEach>--%>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>开始日期：</td>
                    <td>
                        <input type="text" id="salesAccountVouch-begindate" class="Wdate" name="begindate"
                               onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" style="width: 200px;" value="${today}" />
                    </td>
                </tr>
                <tr>
                    <td>结束日期：</td>
                    <td>
                        <input type="text" id="salesAccountVouch-enddate" class="Wdate" name="enddate"
                               onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" style="width: 200px;" value="${today}" />
                    </td>
                </tr>
                <tr>
                    <td>操作日期：</td>
                    <td>
                        <input type="text" id="salesAccountVouch-operdate" class="Wdate" name="operdate"
                               onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" style="width: 200px;" value="${today}" />
                    </td>
                </tr>
                <tr>
                    <td>销售部门：</td>
                    <td>
                        <input type="text" id="salesAccountVouch-salesdept" name="salesdept" /><br/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">注：生成的销售凭证数据是该日期的销售数据（同销售情况报表中的数据）</td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align:right;">
            <input type="button" value="确 定" name="savenogo" id="salesAccount-button-addSave" />
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $("#salesAccountVouch-salesdept").widget({
            name:'t_sales_order',
            col:'salesdept',
            width:200,
            singleSelect:false,
            onlyLeafCheck:true
        });
        $("#salesAccount-button-addSave").click(function(){
            var operatedate = $("#salesAccountVouch-operdate").val();
            var todayyear = '${today}'.substring(0,4);
            var operateyear = operatedate.substring(0,4);
            if(todayyear != operateyear){
                $.messager.confirm("提醒","操作日期与当前年份不符，是否仍要生成凭证？",function (r) {
                    if(r){
                        $("#salesAccount-form-addacountvourch").submit();
                    }
                });
            }else{
                $("#salesAccount-form-addacountvourch").submit();
            }
        });
        $("#salesAccount-form-addacountvourch").form({
            onSubmit: function(){
                var flag = $(this).form('validate');
                if(flag==false){
                    return false;
                }
                loading("提交中..");
            },
            success:function(data){
                //表单提交完成后 隐藏提交等待页面
                loaded();
                var json = $.parseJSON(data);
                if (json.flag) {
                    $.messager.alert("提醒", "生成成功!");
                    $("#report-account-dialog").dialog("close");
                } else {
                    $.messager.alert("提醒", "生成失败!" + json.msg);
                    $("#report-account-dialog").dialog("close");
                }
            }
        });

        loadSubject();
    });
    function loadSubject(){
        var dbid=$("#sales-customer-databaseid").val();
        $.ajax({
            url :"thirdDb/getThirdDbParam.do",
            data:{
                dbid:dbid
            },
            type:'post',
            dataType:'json',
            async: false,
            success:function(json){
                var signList=json.signList;
                $("#sales-customer-sign").empty();
                for(var i=0;i<signList.length;i++){
                    var tdstr = '<option value="'+signList[i].id+'">'+signList[i].codesign+'</option>';
                    $(tdstr).appendTo("#sales-customer-sign");
                }
            }
        });

    }


</script>
</body>
</html>
