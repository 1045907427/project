<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>明细修改</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form action="" method="post" id="simSales-form-simSalesDetailEditPage">
            <table border="0" class="box_table">
                <tr>
                    <td width="120">商品:</td>
                    <td style="text-align: left;">
                        <input type="text" id="simSales-simSalesDetailEditPage-goodsname" width="180" class="no_input" readonly="readonly"/>
                        <input type="hidden" id="simSales-simSalesDetailEditPage-goodsid1" name="goodsid" width="170"/>

                    </td>
                    <td colspan="2" style="text-align: left;"><span id="simSales-loading-simSalesDetailEditPage" ></span></td>
                </tr>
                <tr>
                    <td>辅数量:</td>
                    <td style="text-align: left;">
                        <input type="text" id="simSales-simSalesDetailEditPage-unitnum-aux" name="auxnum" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'integer'"/>
                        <span id="simSales-simSalesDetailEditPage-auxunitname1" style="float: left;"></span>
                        <input type="text" id="simSales-simSalesDetailEditPage-unitnum-unit" name="overnum" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
                        <span id="simSales-simSalesDetailEditPage-goodsunitname1" style="float: left;"></span>
                        <input type="hidden" id="simSales-simSalesDetailEditPage-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
                    </td>
                    <td>数量:</td>
                    <td style="text-align: left;">
                        <input type="text" id="simSales-simSalesDetailEditPage-unitnum" name="unitnum" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
                    </td>

                </tr>
                <tr>
                    <td>销售价:</td>
                    <td>
                        <input type="text" id="simSales-simSalesDetailEditPage-price" name="price"/>
                    </td>
                    <td>销售金额:</td>
                    <td>
                        <input type="text" id="simSales-simSalesDetailEditPage-taxamount" name="taxamount" class="no_input" readonly="readonly" />
                    </td>
                </tr>
                <tr>
                    <td>商品品牌:</td>
                    <td>
                        <input type="text" id="simSales-simSalesDetailEditPage-goodsbrandName" class="no_input" readonly="readonly"/>
                    </td>
                    <td>箱装量:</td>
                    <td>
                        <input type="text" id="simSales-simSalesDetailEditPage-boxnum1" class="no_input" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td>主单位:</td>
                    <td style="text-align: left;">
                        <input type="text" id="simSales-simSalesDetailEditPage-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
                        <input type="hidden" id="simSales-simSalesDetailEditPage-goodsunitid" name="unitid"/>
                    </td>
                    <td>辅单位:</td>
                    <td>
                        <input type="text" id="simSales-simSalesDetailEditPage-auxunitname" name="auxunitname" class="no_input" readonly="readonly"/>
                        <input type="hidden" id="simSales-simSalesDetailEditPage-auxunitid" name="auxunitid"/>
                    </td>
                </tr>
                <tr>

                    <td>备注:</td>
                    <td>
                        <input type="text" id="simSales-simSalesDetailEditPage-remark" name="remark" />
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align: right;">
            快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
            <input type="button" value="确 定" name="savegoon" id="simSales-savegoon-simSalesDetailEditPage" />
        </div>
    </div>
</div>
<script type="text/javascript">

    //加载数据
    var object=$("#simSales-datagrid-simSalesDataPage").datagrid("getSelected");
    $("#simSales-loading-simSalesDetailEditPage").html("商品编码:<font color='green'>"+object.goodsInfo.id+"</font>");
    $("#simSales-simSalesDetailEditPage-goodsid1").val(object.goodsInfo.id);
    $("#simSales-form-simSalesDetailEditPage").form("load",object);
    $("#simSales-simSalesDetailEditPage-taxamount").val(formatterMoney(object.taxamount));
    $("#simSales-simSalesDetailEditPage-price").val(object.price);
    $("#simSales-simSalesDetailEditPage-goodsname").val(object.goodsInfo.name);
    $("#simSales-simSalesDetailEditPage-goodsbrandName").val(object.goodsInfo.brandName);
    $("#simSales-simSalesDetailEditPage-boxnum1").val(formatterBigNumNoLen(object.goodsInfo.boxnum));
    $("#simSales-simSalesDetailEditPage-auxunitname1").html(object.auxunitname);
    $("#simSales-simSalesDetailEditPage-goodsunitname1").html(object.unitname);
    $("#simSales-simSalesDetailEditPage-auxunitname").val(object.auxunitname);
    $("#simSales-simSalesDetailEditPage-goodsunitname").val(object.unitname);
    $("#simSales-simSalesDetailEditPage-unitnum-unit").val(formatterBigNumNoLen(object.overnum));
    $("#simSales-simSalesDetailEditPage-unitnum-aux").val(formatterBigNum(object.auxnum));
    $("#simSales-simSalesDetailEditPage-unitnum").val(formatterBigNumNoLen(object.unitnum));
    $("#simSales-simSalesDetailEditPage-goodsid").val(object.goodsid);
    $(function(){
        $("#simSales-simSalesDetailEditPage-unitnum").change(function(){
            if($(this).val()==""){
                $(this).val("0");
                $(this).validatebox("validate");
            }
            computNum();
            getAmount();
        });
        $("#simSales-simSalesDetailEditPage-unitnum-aux").change(function(){
            if($(this).val()==""){
                $(this).val("0");
                $(this).validatebox("validate");
            }
            computNumByAux();
            getAmount();
        });
        $("#simSales-simSalesDetailEditPage-unitnum-unit").change(function(){
            if($(this).val()==""){
                $(this).val("0");
                $(this).validatebox("validate");
            }
            computNumByAux();
            getAmount();
        });
        $("#simSales-simSalesDetailEditPage-price").change(function(){
            if($(this).val()==""){
                $(this).val("0");
                $(this).validatebox("validate");
            }
            getAmount();
        });
        $("#simSales-simSalesDetailEditPage-unitnum").die("keydown").live("keydown",function(event){
            //enter
            if(event.keyCode==13){
                $("#simSales-simSalesDetailEditPage-unitnum").blur();

                $("#simSales-simSalesDetailEditPage-price").focus();
                $("#simSales-simSalesDetailEditPage-price").select();
            }
        });
        $("#simSales-simSalesDetailEditPage-price").die("keydown").live("keydown",function(event){
            //enter
            if(event.keyCode==13){
                $("#simSales-simSalesDetailEditPage-price").blur();

                $("#simSales-simSalesDetailEditPage-remark").focus();
                $("#simSales-simSalesDetailEditPage-remark").select();
            }
        });
        $("#simSales-simSalesDetailEditPage-unitnum-aux").die("keydown").live("keydown",function(event){
            if(event.keyCode==13){
                $("#simSales-simSalesDetailEditPage-unitnum-auxm").blur();
                $("#simSales-simSalesDetailEditPage-unitnum-unit").focus();
                $("#simSales-simSalesDetailEditPage-unitnum-unit").select();
            }
        });
        $("#simSales-simSalesDetailEditPage-unitnum-unit").die("keydown").live("keydown",function(event){
            if(event.keyCode==13){
                $("#simSales-simSalesDetailEditPage-unitnum-auxm").blur();
                $("#simSales-simSalesDetailEditPage-unitnum").focus();
                $("#simSales-simSalesDetailEditPage-unitnum").select();
            }
        });
        $("#simSales-simSalesDetailEditPage-remark").die("keydown").live("keydown",function(event){
            if(event.keyCode==13){
                $("#simSales-savegoon-simSalesDetailEditPage").focus();
            }
        });
        $("#simSales-savegoon-simSalesDetailEditPage").die("keydown").live("keydown",function(event){
            //enter
            getAmount();
            if(event.keyCode==13){
                editSaveDetail(true);
            }
        });
        $("#simSales-savegoon-simSalesDetailEditPage").click(function(){
            getAmount();
            editSaveDetail(true);
        });
    });
    //通过总数量
    function computNum(){
        var unitnum = $("#simSales-simSalesDetailEditPage-unitnum").val();
        var boxnum = $("#simSales-simSalesDetailEditPage-boxnum1").val();
        if(unitnum==""){
            unitnum="0";
            $("#simSales-simSalesDetailEditPage-unitnum").val(unitnum);
        }
        var aux=parseInt(unitnum)/parseInt(boxnum);
        var unit=parseInt(unitnum)-parseInt(aux)*parseInt(boxnum);
        $("#simSales-simSalesDetailEditPage-unitnum-aux").val(parseInt(aux));
        $("#simSales-simSalesDetailEditPage-unitnum-unit").val(unit);
        var auxunitname = $("#simSales-simSalesDetailEditPage-auxunitname").val();
        var unitname = $("#simSales-simSalesDetailEditPage-goodsunitname").val();
        var aux = $("#simSales-simSalesDetailEditPage-unitnum-aux").val();
        var unit = $("#simSales-simSalesDetailEditPage-unitnum-unit").val();
        var auxdetail = Number(aux)+auxunitname;
        if(unit>0){
            auxdetail += Number(unit)+unitname;
        }
        $("#simSales-simSalesDetailEditPage-auxunitnumdetail").val(auxdetail);
    }
    //通过辅单位数量
    function computNumByAux(){
        var aux = $("#simSales-simSalesDetailEditPage-unitnum-aux").val();
        var unit = $("#simSales-simSalesDetailEditPage-unitnum-unit").val();
        var boxnum = $("#simSales-simSalesDetailEditPage-boxnum1").val();
        if(aux==""){
            aux="0";
            $("#simSales-simSalesDetailEditPage-unitnum-aux").val(aux);
        }
        if(unit==""){
            unit="0";
            $("#simSales-simSalesDetailEditPage-unitnum-unit").val(unit);
        }
        var unitnum=parseInt(aux)*parseInt(boxnum)+parseInt(unit);
        aux=parseInt(unitnum)/parseInt(boxnum);
        unit=parseInt(unitnum)-parseInt(aux)*parseInt(boxnum);
        $("#simSales-simSalesDetailEditPage-unitnum").val(parseInt(unitnum));
        $("#simSales-simSalesDetailEditPage-unitnum-aux").val(parseInt(aux));
        $("#simSales-simSalesDetailEditPage-unitnum-unit").val(parseInt(unit));
        var auxunitname = $("#simSales-simSalesDetailEditPage-auxunitname").val();
        var unitname = $("#simSales-simSalesDetailEditPage-goodsunitname").val();
        var aux1 = $("#simSales-simSalesDetailEditPage-unitnum-aux").val();
        var unit1 = $("#simSales-simSalesDetailEditPage-unitnum-unit").val();
        var auxdetail = Number(aux1)+auxunitname;
        if(unit>0){
            auxdetail += Number(unit1)+unitname;
        }
        $("#simSales-simSalesDetailEditPage-auxunitnumdetail").val(auxdetail);
    }
    //获取金额
    function getAmount(){
        var price = $("#simSales-simSalesDetailEditPage-price").val();
        var unitnum = $("#simSales-simSalesDetailEditPage-unitnum").val();
        var goodsid= $("#simSales-simSalesDetailEditPage-goodsid").val();
        var taxamount=parseFloat(price)*parseFloat(unitnum);
        $("#simSales-simSalesDetailEditPage-taxamount").val(formatterMoney(taxamount));
    }
</script>
</body>
</html>
