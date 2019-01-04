<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>明细添加</title>
</head>

<body>
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'center',border:false">
            <form action="" method="post" id="simSales-form-simSalesDetailAddPage">
                <input type="hidden" id="simSales-simSalesDetailAddPage-goodssort" name="goodssort"/>
                <input type="hidden" id="simSales-simSalesDetailAddPage-brandid" name="brandid"/>
                <table  border="0" class="box_table">
                    <tr>
                        <td width="120">选择商品:</td>
                        <td style="text-align: left;">
                            <input type="text" id="simSales-simSalesDetailAddPage-goodsid" name="goodsid" />
                        </td>
                        <td colspan="2" style="text-align: left;"><span id="simSales-loading-simSalesDetailAddPage" ></span></td>
                    </tr>
                    <tr>
                        <td>辅数量:</td>
                        <td style="text-align: left;">
                            <input type="text" id="simSales-simSalesDetailAddPage-unitnum-aux" name="auxnum" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'integer'"/>
                            <span id="simSales-simSalesDetailAddPage-auxunitname1" style="float: left;"></span>
                            <input type="text" id="simSales-simSalesDetailAddPage-unitnum-unit" name="overnum" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
                            <span id="simSales-simSalesDetailAddPage-goodsunitname1" style="float: left;"></span>
                            <input type="hidden" id="simSales-simSalesDetailAddPage-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
                        </td>
                        <td>数量:</td>
                        <td >
                            <input type="text" id="simSales-simSalesDetailAddPage-unitnum" name="unitnum" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>销售价:</td>
                        <td>
                            <input type="text" id="simSales-simSalesDetailAddPage-price"  name="price"/>
                        </td>
                        <td>销售金额:</td>
                        <td>
                            <input type="text" id="simSales-simSalesDetailAddPage-taxamount" class="no_input" readonly="readonly" name="taxamount"/>
                        </td>
                    </tr>
                    <tr>
                        <td>商品品牌:</td>
                        <td>
                            <input type="text" id="simSales-simSalesDetailAddPage-goodsbrandName" class="no_input" readonly="readonly"/>
                        </td>
                        <td> 箱装量:</td>
                        <td>
                            <input type="text" id="simSales-simSalesDetailAddPage-boxnum1"  class="no_input" readonly="readonly"/>
                        </td>
                    </tr>
                    <tr>
                        <td>主单位:</td>
                        <td >
                            <input type="text" id="simSales-simSalesDetailAddPage-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
                            <input type="hidden" id="simSales-simSalesDetailAddPage-unitid" name="unitid"/>
                        </td>
                        <td>辅单位:</td>
                        <td>
                            <input type="text" id="simSales-simSalesDetailAddPage-auxunitname" name="auxunitname" class="no_input" readonly="readonly"/>
                            <input type="hidden" id="simSales-simSalesDetailAddPage-auxunitid" name="auxunitid"/>
                        </td>
                    </tr>
                    <tr>
                        <td>备注:</td>
                        <td>
                            <input type="text" id="simSales-simSalesDetailAddPage-remark" name="remark"  />
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div data-options="region:'south',border:false">
            <div class="buttonDetailBG" style="height:30px;text-align: right;">
                快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
                <input type="button" value="继续添加" name="savegoon" id="simSales-savegoon-simSalesDetailAddPage" />
            </div>
        </div>
    </div>
    <script type="text/javascript">
        var customerid= $("#simSales-outcustomerid-simSalesDataAddPage").customerWidget("getValue");
        console.log(customerid)
        var businessdate= $("#simSales-businessdate-simSalesDataPage").val();
        console.log(businessdate)
        $(function() {
            $("#simSales-simSalesDetailAddPage-goodsid").goodsWidget({
                col: 'id',
                singleSelect: true,
                width: 150,
                canBuySale: '1',
                onSelect: function(data) {
                    if (data) {
                        $("#simSales-simSalesDetailAddPage-goodsid1").val(data.id);
                        $("#simSales-simSalesDetailAddPage-goodsbrandName").val(data.brandName);
                        $("#simSales-simSalesDetailAddPage-goodsunitname").val(data.mainunitName);
                        $("#simSales-simSalesDetailAddPage-goodsunitname1").html(data.mainunitName);
                        $("#simSales-simSalesDetailAddPage-unitid").val(data.mainunit);
                        $("#simSales-simSalesDetailAddPage-auxunitname").val(data.auxunitname);
                        $("#simSales-simSalesDetailAddPage-auxunitname1").html(data.auxunitname);
                        $("#simSales-simSalesDetailAddPage-auxunitid").val(data.auxunitid);
                        $("#simSales-simSalesDetailAddPage-boxnum1").val(data.boxnum);
                        $("#simSales-simSalesDetailAddPage-goodssort").val(data.defaultsort);
                        $("#simSales-simSalesDetailAddPage-brandid").val(data.brand);
                        $("#simSales-simSalesDetailAddPage-price").val(data.highestbuyprice);
                        $.ajax({
                            url:'sales/getGoodsDetail.do',
                            dataType:'json',
                            type:'post',
                            async:false,
                            data:{id:data.id,cid:customerid,data:businessdate},
                            success:function(json){
                                $("#simSales-simSalesDetailAddPage-price").val(formatterMoney(json.detail.taxprice));
                            }
                        });
                        getAuxunitname();
                        computNum();
                        computNumByAux();
                        getAmount();
                        $("#simSales-loading-simSalesDetailAddPage").html("商品编码:<font color='green'>" + data.id + "</font>");
                        $("#simSales-simSalesDetailAddPage-unitnum-aux").focus();
                        $("#simSales-simSalesDetailAddPage-unitnum-aux").select();
                    }
                }
            });
            $("#simSales-simSalesDetailAddPage-unitnum").change(function(){
                if($(this).val()==""){
                    $(this).val("0");
                    $(this).validatebox("validate");
                }
                computNum();
                getAmount();
            });
            $("#simSales-simSalesDetailAddPage-unitnum-aux").change(function(){
                if($(this).val()==""){
                    $(this).val("0");
                    $(this).validatebox("validate");
                }
                computNumByAux();
                getAmount();

            });
            $("#simSales-simSalesDetailAddPage-unitnum-unit").change(function(){
                if($(this).val()==""){
                    $(this).val("0");
                    $(this).validatebox("validate");
                }
                computNumByAux();
                getAmount();
            });
            $("#simSales-simSalesDetailAddPage-price").change(function(){
                if($(this).val()==""){
                    $(this).val("0");
                    $(this).validatebox("validate");
                }
                getAmount();

            });
            $("#simSales-simSalesDetailAddPage-unitnum").die("keydown").live("keydown",function(event){
                //enter
                if(event.keyCode==13){
                    $("#simSales-simSalesDetailAddPage-unitnum").blur();

                    $("#simSales-simSalesDetailAddPage-price").focus();
                    $("#simSales-simSalesDetailAddPage-price").select();
                }
            });
            $("#simSales-simSalesDetailAddPage-price").die("keydown").live("keydown",function(event){
                //enter
                if(event.keyCode==13){
                    $("#simSales-simSalesDetailAddPage-price").blur();

                    $("#simSales-simSalesDetailAddPage-remark").focus();
                    $("#simSales-simSalesDetailAddPage-remark").select();
                }
            });

            $("#simSales-simSalesDetailAddPage-unitnum-aux").die("keydown").live("keydown",function(event){
                if(event.keyCode==13){
                    $("#simSales-simSalesDetailAddPage-unitnum-auxm").blur();
                    $("#simSales-simSalesDetailAddPage-unitnum-unit").focus();
                    $("#simSales-simSalesDetailAddPage-unitnum-unit").select();
                }
            });
            $("#simSales-simSalesDetailAddPage-unitnum-unit").die("keydown").live("keydown",function(event){
                if(event.keyCode==13){
                    $("#simSales-simSalesDetailAddPage-unitnum-auxm").blur();
                    $("#simSales-simSalesDetailAddPage-unitnum").focus();
                    $("#simSales-simSalesDetailAddPage-unitnum").select();
                }
            });
            $("#simSales-simSalesDetailAddPage-remark").die("keydown").live("keydown",function(event){
                if(event.keyCode==13){
                    $("#simSales-savegoon-simSalesDetailAddPage").focus();
                }
            });
            $("#simSales-savegoon-simSalesDetailAddPage").die("keydown").live("keydown",function(event){
                //enter
                getAmount();
                if(event.keyCode==13){
                    addSaveDetail(true);
                }
            });
            $("#simSales-savegoon-simSalesDetailAddPage").click(function(){
                getAmount();
                addSaveDetail(true);
            });
        });
        //获取辅单位和可用量
        function getAuxunitname(){
            var goodsInfo= $("#").goodsWidget("getObject");
            if(null==goodsInfo){
                return false;
            }
            var auxunitid = $("#simSales-simSalesDetailAddPage-auxunitid").val();
            $.ajax({
                url :'manufacturerdata/getAuxunitname.do',
                type:'post',
                data:{goodsId:goodsInfo.id},
                dataType:'json',
                async:false,
                success:function(json){
                    $("#simSales-simSalesDetailAddPage-usablenum").val(json.usablenum);
                }
            });
        }

        //通过总数量
        function computNum(){
            var unitnum = $("#simSales-simSalesDetailAddPage-unitnum").val();
            var boxnum = $("#simSales-simSalesDetailAddPage-boxnum1").val();
            if(unitnum==""){
                unitnum="0";
                $("#simSales-simSalesDetailAddPage-unitnum").val(unitnum);
            }
            var aux=parseInt(unitnum)/parseInt(boxnum);
            var unit=parseInt(unitnum)-parseInt(aux)*parseInt(boxnum);
            $("#simSales-simSalesDetailAddPage-unitnum-aux").val(parseInt(aux));
            $("#simSales-simSalesDetailAddPage-unitnum-unit").val(unit);
            var auxunitname = $("#simSales-simSalesDetailAddPage-auxunitname").val();
            var unitname = $("#simSales-simSalesDetailAddPage-goodsunitname").val();
            var aux = $("#simSales-simSalesDetailAddPage-unitnum-aux").val();
            var unit = $("#simSales-simSalesDetailAddPage-unitnum-unit").val();
            var auxdetail = Number(aux)+auxunitname;
            if(unit>0){
                auxdetail += Number(unit)+unitname;
            }
            $("#simSales-simSalesDetailAddPage-auxunitnumdetail").val(auxdetail);
        }
        //通过辅单位数量
        function computNumByAux(){
            var aux = $("#simSales-simSalesDetailAddPage-unitnum-aux").val();
            var unit = $("#simSales-simSalesDetailAddPage-unitnum-unit").val();
            var boxnum = $("#simSales-simSalesDetailAddPage-boxnum1").val();
            if(aux==""){
                aux="0";
                $("#simSales-simSalesDetailAddPage-unitnum-aux").val(aux);
            }
            if(unit==""){
                unit="0";
                $("#simSales-simSalesDetailAddPage-unitnum-unit").val(unit);
            }
            var unitnum=parseInt(aux)*parseInt(boxnum)+parseInt(unit);
            aux=parseInt(unitnum)/parseInt(boxnum);
            unit=parseInt(unitnum)-parseInt(aux)*parseInt(boxnum);
            $("#simSales-simSalesDetailAddPage-unitnum").val(parseInt(unitnum));
            $("#simSales-simSalesDetailAddPage-unitnum-aux").val(parseInt(aux));
            $("#simSales-simSalesDetailAddPage-unitnum-unit").val(parseInt(unit));
            var auxunitname = $("#simSales-simSalesDetailAddPage-auxunitname").val();
            var unitname = $("#simSales-simSalesDetailAddPage-goodsunitname").val();
            var aux1 = $("#simSales-simSalesDetailAddPage-unitnum-aux").val();
            var unit1 = $("#simSales-simSalesDetailAddPage-unitnum-unit").val();
            var auxdetail = Number(aux1)+auxunitname;
            if(unit>0){
                auxdetail += Number(unit1)+unitname;
            }
            $("#simSales-simSalesDetailAddPage-auxunitnumdetail").val(auxdetail);
        }
        //获取金额
        function getAmount(){
            var price = $("#simSales-simSalesDetailAddPage-price").val();
            var unitnum = $("#simSales-simSalesDetailAddPage-unitnum").val();
            var goodsid= $("#simSales-simSalesDetailAddPage-goodsid").val();
            var taxamount=parseFloat(price)*parseFloat(unitnum);
            $("#simSales-simSalesDetailAddPage-taxamount").val(formatterMoney(taxamount));
        }
    </script>
</body>
</html>
