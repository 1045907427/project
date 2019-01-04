<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>库存成本调整</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div id="report-toolbar-storageCostChangePage" style="text-align: center;padding-top: 20px;">
    <form id="report-form-storageCostChangePage" method="post">
        <input type="hidden" name="begindate" value="${param.begindate}">
        <input type="hidden" name="enddate" value="${param.enddate}">
        <table cellpadding="5px" cellspacing="5px" style="padding-top: 5px;padding-left: 5px;">
            <tr>
                <td>仓库:</td>
                <td>
                    <input id="report-storageid-storageCostChangePage" value="${storageid}" name="storageid"
                           readonly="readonly" select="width:150px"/>
                </td>
            </tr>
            <tr>
                <td>商品:</td>
                <td>
                    <input id="report-goodsid-storageCostChangePage" value="${goodsid}" name="goodsid"
                           readonly="readonly" select="width:150px"/>
                </td>
            </tr>
            <tr>
                <td>业务日期:</td>
                <td>
                    <input style="width:150px;"  disabled="disabled" type="text" style="width:100px;" class="Wdate"
                           onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${param.enddate}"/>
                    <input type="hidden" id="report-businessdate-storageCostChangePage"  name="changedate" value="${param.enddate}">
                </td>
            </tr>
            <tr>
                <td>调整类型:</td>
                <td>
                    <select name="changetype" style="width: 150px">
                        <option value="1" selected>出入库成本调整</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>调整金额:</td>
                <td>
                    <input type="text" autocomplete="off" id="report-changeamount-storageCostChangePage" class="len150" class="easyui-numberbox" validType="vailChangeAmount"  data-options="required:true,precision:6"  name="changeamount"/>
                </td>
            </tr>
            <tr>
                <td>调整后期末单价:</td>
                <td>
                    <input type="text" id="report-afterendprice-storageCostChangePage"  class="easyui-validatebox len150" required="required"  name="afterendprice"/>
                </td>
            </tr>
            <tr>
                <td>调整后库存单价:</td>
                <td>
                    <input type="text" id="report-afterstorageprice-storageCostChangePage"  class="easyui-validatebox len150" required="required"  name="afterstorageprice"/>
                </td>
            </tr>
            <tr>
                <td>销售成本是否重新计算:</td>
                <td>
                    <select name="isAcountCostPrice" class="len150">
                        <option value="0" selected>否</option>
                        <option value="1">是</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>结算方式:</td>
                <td>
                    <select class="len150" name="accounttype">
                        <option value="2" selected>全月一次加权平均法</option>
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    var realendnum=${param.realendnum};
    var realendamount=${param.realendamount};
    $("input[name='afterprice']").val(Number(parseFloat(realendnum)==0?0:parseFloat(realendamount)/parseFloat(realendnum)).toFixed(6));
    $.extend($.fn.validatebox.defaults.rules, {
        vailChangeAmount:{
            validator:function(value, param){
                return value!=0;
            },
            message : '调整金额不能为0'
        }
    });
    $(function () {

        $("#report-changeamount-storageCostChangePage").numberbox({
            "onChange":function(newValue,oldValue){
                computPrice();
            }
        });
        $("#report-goodsid-storageCostChangePage").widget({
            referwid:'RL_T_BASE_GOODS_INFO',
            width:'150',
            singleSelect:false
        })

        $("#report-storageid-storageCostChangePage").widget({
            referwid:'RL_T_BASE_STORAGE_INFO',
            width:'150',
            singleSelect:false
        })
    })
    function computPrice(){
        // if(!$("#report-form-storageCostChangePage").form('validate')){
        //     return false;
        // }
        var form = $("#report-query-abnormalBillReportPage").serializeJSON();
        form.businessdate1=$("#report-businessdate-storageCostChangePage").val();
        form.storageid='${param.storageid}';
        form.goodsid=$("#report-goodsid-storageCostChangePage").widget("getValue");
        form.changeamount=$("#report-changeamount-storageCostChangePage").numberbox("getValue");
        loading('计算中');
        $.ajax({
            url :'storage/cost/getStorageAfterChangePrice.do',
            type:'post',
            data:form,
            dataType:'json',
            success:function(json){
                loaded();
                $("#report-afterendprice-storageCostChangePage").val(json.afterendprice);
                $("#report-afterstorageprice-storageCostChangePage").val(json.afterstorageprice);
            }
        });
    }

</script>
</body>
</html>
