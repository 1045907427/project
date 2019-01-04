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
        <form action="" method="post" id="contract-form-contractDetailPage">
            <input type="hidden" id="contract-goodssort-contractDetailPage" name="goodssort"/>
            <input type="hidden" id="contract-brandid-contractDetailPage" name="brandid"/>
            <input type="hidden" id="contract-totalbox-contractDetailPage" name="totalbox"/>

            <table  border="0" class="box_table">
                <tr>
                    <td>选择条款:</td>
                    <td style="text-align: left;">
                        <input type="text" id="contract-caluseid-contractDetailPage" name="caluseid" style="width:160px;" readonly="readonly" />
                    </td>
                    <td>费用类型:</td>
                    <td style="text-align: left;">
                        <select id="contract-costtype-contractDetailPage" name="costtype" style="width:160px;" >
                            <option value="0" selected="selected">可变费用</option>
                            <option value="1">固定费用</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>分摊方式:</td>
                    <td style="text-align: left;">
                        <select id="contract-sharetype-contractDetailPage" name="sharetype" style="width:160px;" >
                            <option value="0" selected="selected">一次性分摊</option>
                            <option value="1">分月平摊</option>
                        </select>
                    </td>
                    <td>支付类型:</td>
                    <td >
                        <select id="contract-returntype-contractDetailPage" name="returntype" style="width:160px;" >
                            <option value="0" selected="selected">月返</option>
                            <option value="1">季返</option>
                            <option value="2">年返</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>支付月份:</td>
                    <td style="text-align: left;">
                        <input type="text" id="contract-returnmonthnum-contractDetailPage" name="returnmonthnum" style="width:160px;"  />
                    </td>
                    <td>支付要求:</td>
                    <td >
                        <select id="contract-returnrequire-contractDetailPage" name="returnrequire" style="width:160px;" >
                            <option value="0" selected="selected">无要求</option>
                            <option value="1">销售达成</option>
                        </select>
                    </td>
                </tr>
                <tr>

                    <td>计算方式:</td>
                    <td >
                        <select id="contract-calculatetype-contractDetailPage" name="calculatetype" style="width:160px;" >
                            <option value="0" selected="selected">固定费用</option>
                            <option value="1">百分比计算</option>
                            <option value="2">按门店数计算</option>
                            <option value="3">按新门店数计算</option>
                            <option value="4">按新品数计算</option>
                        </select>
                    </td>
                    <td>计算参数:</td>
                    <td style="text-align: left;">
                        <input type="text" id="contract-calculateamount-contractDetailPage" style="width:160px;" class="easyui-validatebox" required="true"/>
                        <input type="hidden" id="contract-hidden-calculateamount-contractDetailPage" name="calculateamount" style="width:160px;" />
                    </td>
                </tr>
                <tr>
                    <td>费用科目:</td>
                    <td style="text-align: left;">
                        <input type="text" id="contract-subjectexpenses-contractDetailPage" name="subjectexpenses" style="width:160px;" />
                        <input type="hidden" id="contract-subjectexpensesname-contractDetailPage" name="subjectexpensesname" style="width:160px;" />
                    </td>
                    <td>备注:</td>
                    <td>
                        <input type="text" id="contract-contractDetailPageDetail-remark" name="remark"  style="width:160px;" />
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align: right;">
            快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
            <input type="button" value="继续添加" name="savegoon" id="contract-savegoon-contractDetailPage" />
        </div>
    </div>
</div>
<script type="text/javascript">
    var object=$("#contract-datagrid-contractPage").datagrid("getSelected");
    var calculatetype1 = object.calculatetype;
    if("1" == calculatetype1){
        $("#contract-calculateamount-contractDetailPage").val(object.calculateamount+"%");
    }else {
        $("#contract-calculateamount-contractDetailPage").val(object.calculateamount);
    }

    var returntype1 = object.returntype;
    if("0" == returntype1){
        $("#contract-returnmonthnum-contractDetailPage").val("0");
        $("#contract-returnmonthnum-contractDetailPage").attr("readonly","readonly");
    }else{
        $("#contractCaluse-returnmonthnum-addContractCaluse").val("1");
        $("#contract-returnmonthnum-contractDetailPage").removeAttr("readonly");
    }

    $(function() {

        $("#contract-returntype-contractDetailPage").change(function () {
            if("0" == $(this).val()){
                $("#contract-returnmonthnum-contractDetailPage").val("0");
                $("#contract-returnmonthnum-contractDetailPage").attr("readonly","readonly");

            }else{
                $("#contract-returnmonthnum-contractDetailPage").removeAttr("readonly");
            }
        })
        $("#contract-returnmonthnum-contractDetailPage").change(function () {
            var returntype = $("#contract-returntype-contractDetailPage").val();
            if("1" == returntype){
                if($(this).val() > 3){
                    $(this).val("3");
                }else if($(this).val() < 1){
                    $(this).val("1");
                }else {
                    $(this).val(parseInt($(this).val()));
                }
            }else if("2" == returntype) {
                if($(this).val() > 12){
                    $(this).val("12");
                }else if($(this).val() < 1){
                    $(this).val("1");
                }else {
                    $(this).val(parseInt($(this).val()));
                }
            }
        })
        $("#contract-calculatetype-contractDetailPage").change(function () {
            $("#contract-calculateamount-contractDetailPage").val("");
            $("#contract-hidden-calculateamount-contractDetailPage").val("");
        })
        $("#contract-calculateamount-contractDetailPage").change(function () {
            var a=RegExp("[1-9][0-9]{0,9}").test(parseInt($(this).val()));
            if(!a){
                $(this).val("0");
                $("#contract-hidden-calculateamount-contractDetailPage").val("0");
            }
            $("#contract-hidden-calculateamount-contractDetailPage").val($(this).val());
            var calculatetype = $("#contract-calculatetype-contractDetailPage").val();
            if("1" == calculatetype){
                $(this).val($(this).val()+"%");
            }
        })
        $("#contract-savegoon-contractDetailPage").die("keydown").live("keydown",function(event){
            //enter
            if(event.keyCode==13){
                editSaveDetail(true);
            }
        });
        $("#contract-savegoon-contractDetailPage").click(function(){
            editSaveDetail(true);
        });
    });
    $("#contract-subjectexpenses-contractDetailPage").widget({
        referwid:'RT_T_BASE_FINANCE_EXPENSES_SORT',
        width:160,
        singleSelect:true,
        onlyLeafCheck:true,
        required:true,
        onSelect: function(data) {
            $("#contract-subjectexpensesname-contractDetailPage").val(data.name);
        }
    });
</script>
</body>
</html>
