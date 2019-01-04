<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售订单商品捆绑新增页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <div id="sales-div-orderGoodsPromotionBundAddPage">
            <form id="sales-form-orderGoodsPromotionBundAddPage">
                <table cellpadding="5" cellspacing="5">
                    <tr>
                        <td colspan="4">捆绑套餐编码：${promotionPackageGroup.groupid}；
                            套餐名称：${promotionPackageGroup.groupname}；
                            <c:if test="${promotionPackageGroup.limitnum>0}">
                                剩余份数：<fmt:formatNumber value="${promotionPackageGroup.remainnum}" type="number" pattern="#0"/>份
                            </c:if>
                            <input id="sales-groupid-orderGoodsPromotionBundAddPage" type="hidden" name="groupid" value="${promotionPackageGroup.groupid}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>套餐价格：</td>
                        <td>
                            <input type="text" class="easyui-validatebox len150 readonly" value="${promotionPackageGroup.price}" readonly="readonly"/>
                        </td>
                        <td>数量：</td>
                        <c:choose>
                            <c:when test="${promotionPackageGroup.remainnum>0 && promotionPackageGroup.limitnum>0}">
                                <td><input id="sales-unitnum-orderGoodsPromotionBundAddPage" class="easyui-validatebox len150 goodsNum" value="0" data-options="required:true,validType:['intOrFloatNum[${decimallen}]','max[${promotionPackageGroup.remainnum}]']" /></td>
                            </c:when>
                            <c:otherwise>
                                <td><input id="sales-unitnum-orderGoodsPromotionBundAddPage" class="easyui-validatebox len150 goodsNum" value="0" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'" /></td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr>
                        <td>含税金额：</td>
                        <td>
                            <input type="text" class="len150 readonly easyui-numberbox" id="sales-taxamount-orderGoodsPromotionBundAddPage" data-options="precision:6,groupSeparator:','" readonly="readonly" />
                        </td>
                        <td>未税金额：</td>
                        <td><input type="text" class="len150 readonly easyui-numberbox" id="sales-notaxamount-orderGoodsPromotionBundAddPage" readonly="readonly" data-options="precision:6,groupSeparator:','" /></td>
                    </tr>
                    <tr>
                        <td>备注：</td>
                        <td colspan="3"><input type="text" style="width: 400px;" value=""/></td>
                    </tr>
                </table>
            </form>
            <h1>捆绑商品列表</h1>
        </div>
        <table id="sales-table-orderGoodsPromotionBundAddPage"></table>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align:right;">
            <input type="button" value="继续添加" name="savegoon" id="sales-savegoon-orderGoodsPromotionBundAddPage" />
        </div>
    </div>
</div>

<script type="text/javascript">
$(function(){
    $("#sales-table-orderGoodsPromotionBundAddPage").datagrid({
        fit : true,
        striped : true,
        rownumbers : true,
        singleSelect : true,
        columns: [[
            {field:'goodsid',title:'商品编号',width:80},
            {field:'goodsname',title:'商品名称',width:150 },
            {field:'brand',title:'品牌',width:50,
                formatter:function(value,rowData,rowIndex){
                    if(rowData.goodsInfo != null){
                        return rowData.goodsInfo.brandName;
                    }else{
                        return "";
                    }
                }
            },
            {field:'boxnum', title:'箱装量',aliascol:'goodsid',width:45,align:'right',
                formatter:function(value,rowData,rowIndex){
                    if(rowData.isdiscount!='1' && rowData.goodsInfo != null){
                        return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
                    }else{
                        return "";
                    }
                }
            },
            {field:'unitname',title:'单位',width:40},
            {field:'oldprice',title:'原价',width:50,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'price',title:'现价',width:50,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'unitnum',title:'每份捆绑数量',width:80,align:'right',
                formatter:function(value,row,index){
                    if(value!=null){
                        return formatterBigNumNoLen(value);
                    }else{
                        return "";
                    }
                }
            },
            {field:'bundunitnum',title:'合计数量',width:80,align:'right',
                formatter:function(value,row,index){
                    if(value!=null){
                        return formatterBigNumNoLen(value);
                    }else{
                        return "";
                    }
                }
            },
            {field:'taxamount',title:'金额',width:80,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            }
        ]],
        showFooter: true,
        striped:true,
        idField:'id',
        singleSelect: false,
        checkOnSelect:true,
        selectOnCheck:true,
        toolbar:'#sales-div-orderGoodsPromotionBundAddPage',
        data: JSON.parse('${listJson}')
    });
    $("#sales-unitnum-orderGoodsPromotionBundAddPage").change(function(){
        giveunitnumChange();
    });
    $("#sales-savegoon-orderGoodsPromotionBundAddPage").click(function(){
        setTimeout(addSaveBundNumDetail(),200);
    });
});
function giveunitnumChange(){ //数量改变方法
    var groupid = $("#sales-groupid-orderGoodsPromotionBundAddPage").val();
    var bundnum  = $("#sales-unitnum-orderGoodsPromotionBundAddPage").val();
    var customerid = $("#sales-customer-orderAddPage").customerWidget("getValue");
    var url = "sales/getGoodsDetailListByBund.do"
    var data = {groupid:groupid,bundnum:bundnum,customerid:customerid};

    $.ajax({
        url:url,
        dataType:'json',
        type:'post',
        async:false,
        data:data,
        success:function(json){

            if(json!=null){
                $("#sales-taxamount-orderGoodsPromotionBundAddPage").numberbox("setValue",json.totaltaxamount);
                $("#sales-notaxamount-orderGoodsPromotionBundAddPage").numberbox("setValue",json.totalnotaxamount);
                jsonArray = json.bundList;
                for(var i=0;i<jsonArray.length;i++){
                    var fieldid = jsonArray[i].id;
                    var rowIndex = $("#sales-table-orderGoodsPromotionBundAddPage").datagrid("getRowIndex",fieldid);
                    var row = $("#sales-table-orderGoodsPromotionBundAddPage").datagrid('getRows')[rowIndex];
                    row.bundunitnum = jsonArray[i].unitnum;
                    row.bundauxnum = jsonArray[i].auxnum;
                    row.bundovernum = jsonArray[i].overnum;
                    row.bundauxnumdetail = jsonArray[i].auxnumdetail;
                    row.totalbox = jsonArray[i].totalbox;
                    row.taxprice = jsonArray[i].taxprice;
                    row.boxprice = jsonArray[i].boxprice;
                    row.taxamount = jsonArray[i].taxamount;
                    row.notaxprice = jsonArray[i].notaxprice;
                    row.notaxamount = jsonArray[i].notaxamount;
                    row.tax = jsonArray[i].tax;
                    row.oldprice = jsonArray[i].oldprice;

                    $("#sales-table-orderGoodsPromotionBundAddPage").datagrid('updateRow',{index:rowIndex, row:row});
                }
            }
        }
    });
}
    function addSaveBundNumDetail(){
        var groupid =$("#sales-groupid-orderGoodsPromotionBundAddPage").val();
        var flag = $("#sales-form-orderGoodsPromotionBundAddPage").form('validate');
        if(flag==false){
            return false;
        }
        var addrows = $("#sales-datagrid-orderAddPage").datagrid("getRows");
        var addGiveFlag = true;
        for(var i=0;i<addrows.length;i++){
            if(groupid==addrows[i].groupid){
                addGiveFlag = false;
                break;
            }
        }
        if(addGiveFlag==false){
            $.messager.alert("提醒","已添加过此捆绑商品，不能重复添加");
            return false;
        }
        var rows = $("#sales-table-orderGoodsPromotionBundAddPage").datagrid("getRows");
        if(rows.length>0){
            for(var i=0;i<rows.length;i++){
                var goodsid = rows[i].goodsid;
                var brandid = rows[i].brand;
                var unitnum = rows[i].bundunitnum;
                var fixnum = rows[i].bundunitnum;
                var auxnum = rows[i].bundauxnum;
                var overnum = rows[i].bundovernum;
                var auxnumdetail = rows[i].bundauxnumdetail;
                var totalbox = rows[i].totalbox;
                var usablenum = rows[i].usablenum;
                var taxtype = rows[i].goodsInfo.defaulttaxtype;
                var taxtypename =rows[i].goodsInfo.defaulttaxtypeName;
                var data = {goodsid:goodsid,brandid:brandid,unitid:rows[i].unitid,unitname:rows[i].unitname,fixnum:fixnum,
                    unitnum:unitnum,auxunitid:rows[i].auxunitid,auxunitname:rows[i].auxunitname,
                    auxnum:auxnum,overnum:overnum,auxnumdetail:auxnumdetail,totalbox:totalbox,
                    taxtype:taxtype,taxtypename:taxtypename,
                    goodsInfo:rows[i].goodsInfo,deliverytype:'2',groupid:groupid,taxprice:rows[i].taxprice,taxamount:rows[i].taxamount,boxprice:rows[i].boxprice,
                    notaxprice:rows[i].notaxprice,notaxamount:rows[i].notaxamount,tax:rows[i].tax,oldprice:rows[i].oldprice,usablenum:usablenum}
                insertRow(data);
            }
        }
        $("#sales-form-orderDetailAddPage").form("clear");
        $("#sales-dialog-orderGoodsPromotion-ptype-content").dialog('close', true)
    }
</script>
</body>
</html>
