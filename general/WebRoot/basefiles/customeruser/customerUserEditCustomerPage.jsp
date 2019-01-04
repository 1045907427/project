<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>代配送采购单明细添加</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form  action="basefiles/addCustomerUserAddCustomer.do" method="post" id="customeruser-form-addCustomerAddPage">
            <input type="hidden" id="customerUserAddPage-mobilephone" name="customerUser.mobilephone" />
            <table  border="0" class="box_table">
                <tr>
                    <td width="120">选择客户:</td>
                    <td style="text-align: left;" colspan="3">
                        <input type="text" id="customerUserAddPage-customerid" name="customerUser.customerid" />
                    </td>
                </tr>


            </table>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align: right;">
            <input type="button" value="保存" name="savegoon" id="customeruser-savegoon-addCustomerAddPage" />
        </div>
    </div>
</div>
<script type="text/javascript">
    var mobilephone = "${mobilephone}";
    $("#customerUserAddPage-mobilephone").val(mobilephone);
    $(function() {
        $("#customerUserAddPage-customerid").customerWidget({
            col: 'id',
            singleSelect: true,
            width: 250,
            canBuySale: '1',
            param: [

            ],
            onSelect: function(data) {
                $("#customerUserAddPage-contact").val(data.contact);
            }
        });

        //保存
        $("#customeruser-savegoon-addCustomerAddPage").click(function(){
            $.messager.confirm("提醒","是否绑定选择的客户?",function(r){
                if(r){
                    customeruser_save_form_submit();
                    $("#customeruser-form-addCustomerAddPage").submit();
                }
            });
        });
    })


</script>
</body>
</html>
