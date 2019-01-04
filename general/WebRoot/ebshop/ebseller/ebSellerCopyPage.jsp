<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>卖家信息新增页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true" id="ebshop-layout-ebSellerAddPage">
    <div data-options="region:'center',split:true,border:true" align="center">
        <form id="ebshop-form-ebSellerAddPage" action="ebseller/addEbSeller.do" method="post">
            <table cellpadding="3" cellspacing="3" border="0" >
                <tr>
                    <td>卖家昵称：</td>
                    <td><input type="text" name="ebSeller.sellernick" class="len180 easyui-validatebox" validType="sellernick" required="true"/></td>
                </tr>
                <tr>
                    <td>所属公司：</td>
                    <td><input type="text" name="ebSeller.company" value="${ebSeller.company}" class="len180 easyui-validatebox" validType="illegalChar"/></td>
                </tr>
                <tr>
                    <td>联系方式：</td>
                    <td><input type="text" name="ebSeller.phone" value="${ebSeller.phone}" class="len180"/></td>
                </tr>
                <tr>
                    <td>备注：</td>
                    <td><textarea rows="3" style="width: 173px;" name="ebSeller.remark"></textarea></td>
                </tr>
                <tr>
                    <td>状态：</td>
                    <td><select name="ebSeller.state" class="len180" disabled="disabled">
                        <option value="4">新增</option>
                    </select></td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'south'">
        <div class="buttonDetailBG" style="text-align:right;">
            <input type="button" id="ebshop-save-ebSellerAddPage" value="保存"/>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){

        //新增电商商品
        $("#ebshop-save-ebSellerAddPage").click(function(){
            $.messager.confirm("提醒","确定添加该卖家信息?",function(r) {
                if(r){
                    $("#ebshop-form-ebSellerAddPage").submit();
                }
            });
        });

        $("#ebshop-form-ebSellerAddPage").form({
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
                if(json.flag){
                    $.messager.alert("提醒","添加成功。");
                    $("#ebshop-datagrid-ebSellerListPage").datagrid('reload');
                    $("#ebshop-dialog-ebSellerListPage1").dialog("close");
                }
                else{
                    $.messager.alert("提醒","添加失败");
                }
            }
        });
    });
</script>
</body>
</html>
