<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>行程新增页</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form id="sales-form-routeAdd" method="post" style="margin-top: 30px;margin-left: 30px">
            <table style="border-collapse: collapse;" border="0" cellpadding="5" cellspacing="5">
                <tr>
                    <td>起始日期:</td>
                    <td class="tdinput">
                        <input type="text" name="businessdatestart" required="true" style="width:150px;"
                               class="easyui-validatebox Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                    </td>
                    <td>终止日期:</td>
                    <td>
                        <input type="text" name="businessdateend" required="true" class="easyui-validatebox Wdate"
                               style="width:150px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                    </td>
                </tr>
                <tr>
                    <td>业务员：</td>
                    <td><input id="sales-saler-routeAddPage" name="userid"/></td>
                    <td>行程计算方式：</td>
                    <td>
                        <select id="sales-type-routeAddPage" name="type">
                            <option value="1">按定位坐标计算</option>
                            <option value="2">按鹰眼计算</option>
                            <option value="3">优先鹰眼,定位坐标补充</option>
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'south',border:false" style="margin-bottom: 25px;margin-right: 26px">
        <div class="buttonDetailBG" style="text-align: right;">
            <input type="button" value="生成" name="savenogo" id="sales-routeAdd-saveData"/>
            <input type="button" value="关闭" name="close" id="sales-routeAdd-closeDlg"/>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $("#sales-saler-routeAddPage").widget({
            name: 't_phone_route_distance',
            col: 'userid',
            width: 150,
            singleSelect: true
        });
        $("#sales-routeAdd-closeDlg").click(function () {
            parent.$.dialog.dialog('close')
        });
        //保存
        $("#sales-routeAdd-saveData").click(function () {
            if ($("#sales-form-routeAdd").form('validate')) {
                loading();
                $.ajax({
                    url: "sales/addRoute.do",
                    data: $("#sales-form-routeAdd").serialize(),
                    type: 'post',
                    dataType: 'json',
                    success: function (r) {
                        loaded();
                        if (r.flag) {
                            $.messager.alert("提醒", r.msg);
                            parent.$.dialog.dialog('close');
                            parent.$.dg.datagrid('reload');
                        } else {
                            $.messager.alert("提醒", "生成失败");
                        }
                    }
                });
            }
        });
    });
</script>
</body>
</html>
