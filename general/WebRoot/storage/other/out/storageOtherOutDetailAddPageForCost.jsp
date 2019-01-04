<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>其他出库单明细添加</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form action="" method="post" id="storage-form-storageOtherOutDetailAddPage">
            <table  border="0" class="box_table">
                <tr>
                    <td></td>
                    <td></td>
                    <td colspan="2" id="storage-storageOtherOut-loadInfo" style="text-align: left;">&nbsp;</td>
                </tr>
                <tr>
                    <td width="120">选择商品:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherOut-goodsid" name="goodsid" width="180"/>
                        <input type="hidden" id="storage-storageOtherOut-summarybatchid" name="summarybatchid">
                        <input type="hidden" id="storage-storageOtherOut-goodsname" name="goodsname">
                    </td>
                    <td>批次号:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherOut-batchno" name="batchno"/>
                    </td>
                </tr>
                <tr>
                    <td>数量:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherOut-unitnum" name="unitnum" readonly="readonly" class="easyui-validatebox" data-options="required:true,validType:['intOrFloatNum[${decimallen}]','max']"/>
                    </td>
                    <td width="120">辅数量:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherOut-unitnum-aux" name="auxnum" readonly="readonly" style="width:70px;" class="easyui-validatebox" data-options="required:true,validType:'integer'"/>
                        <span id="storage-storageOtherOut-auxunitname1" style="float: left;"></span>
                        <input type="text" id="storage-storageOtherOut-unitnum-unit" name="auxremainder" readonly="readonly" style="width:70px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
                        <span id="storage-storageOtherOut-goodsunitname1" style="float: left;"></span>
                        <input type="hidden" id="storage-storageOtherOut-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td>条形码:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherOut-goodsbarcode" name="barcode" class="no_input" readonly="readonly"/>
                    </td>
                    <td>箱装量:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherOut-boxnum" class="no_input" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td>商品品牌:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherOut-goodsbrandName" name="brandName" class="no_input" readonly="readonly"/>
                    </td>
                    <td>规格型号:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherOut-goodsmodel" name="model" class="no_input" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td>主单位:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherOut-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
                        <input type="hidden" id="storage-storageOtherOut-goodsunitid" name="unitid"/>
                    </td>
                    <td>辅单位:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherOut-auxunitname" name="auxunitname" class="no_input" readonly="readonly"/>
                        <input type="hidden" id="storage-storageOtherOut-auxunitid" name="auxunitid"/>
                    </td>
                </tr>
                <tr>
                    <td>含税单价:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherOut-taxprice" readonly="readonly" name="taxprice" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'">
                    </td>
                    <td>含税金额:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherOut-taxamount" name="taxamount" class="easyui-validatebox"   data-options="required:true,validType:'intOrFloat',validType:'intOrFloatNum[6]'"/>
                    </td>
                </tr>
                <tr>
                    <td>未税单价:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherOut-notaxprice" name="notaxprice" class="no_input" readonly="readonly"/>
                    </td>
                    <td>未税金额:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherOut-notaxamount" name="notaxamount" class="easyui-validatebox"   data-options="required:true,validType:'intOrFloat',validType:'intOrFloatNum[6]'"/>
                    </td>
                </tr>
                <tr>
                    <td>税种:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherOut-taxtypename" name="taxtypename" class="no_input" readonly="readonly"/>
                        <input type="hidden" id="storage-storageOtherOut-taxtype" name="taxtype" class="no_input" readonly="readonly"/>
                    </td>
                    <td>税额:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherOut-tax" name="tax" class="easyui-validatebox"   data-options="required:true,validType:'intOrFloat',validType:'intOrFloatNum[6]'" class="no_input" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td>所属库位:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherOut-storagelocationname" name="storagelocationname" class="no_input" readonly="readonly"/>
                        <input type="hidden" id="storage-storageOtherOut-storagelocationid"  name="storagelocationid"/>
                    </td>
                </tr>
                <tr>
                    <td>生产日期:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherOut-produceddate" style="height: 20px;" class="no_input" name="produceddate" readonly="readonly"/>
                    </td>
                    <td>截止日期:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherOut-deadline" style="height: 20px;" class="no_input" name="deadline" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td>备注:</td>
                    <td colspan="3" style="text-align: left;">
                        <input id="storage-storageOtherOut-remark" type="text" name="remark" style="width: 488px;" maxlength="200"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align: right;">
            快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
            <input type="button" value="继续添加" name="savegoon" id="storage-savegoon-storageOtherOutDetailAddPage" />
        </div>
    </div>
    <script type="text/javascript">
        var selectStorageid = $("#storage-storageOtherOut-storageid").widget('getValue');
        var storageInfo = $("#storage-storageOtherOut-storageid").widget('getObject');
        var maxnum = 0;
        $(function(){
            $.extend($.fn.validatebox.defaults.rules, {
                max: {
                    validator: function (value) {
                        if(value > maxnum){
                            return false;
                        }else{
                            return true;
                        }
                    },
                    message:'数量超过可用量'
                }
            });
            $("#storage-storageOtherOut-goodsid").goodsWidget({
                required:true,
                storageid:selectStorageid,
                onSelect:function(data){
                    $("#storage-storageOtherOut-goodsname").val(data.name);
                    $("#storage-storageOtherOut-goodsbrandName").val(data.brandName);
                    $("#storage-storageOtherOut-goodsmodel").val(data.model);

                    $("#storage-storageOtherOut-goodsunitid").val(data.mainunit);
                    $("#storage-storageOtherOut-auxunitid").val(data.auxunitid);
                    $("#storage-storageOtherOut-auxunitname").val(data.auxunitname);
                    $("#storage-storageOtherOut-auxunitname1").html(data.auxunitname);
                    $("#storage-storageOtherOut-goodsunitname").val(data.mainunitName);
                    $("#storage-storageOtherOut-goodsunitname1").html(data.mainunitName);

                    $("#storage-storageOtherOut-taxtype").val(data.taxtype);
                    $("#storage-storageOtherOut-goodsbarcode").val(data.barcode);
                    $("#storage-storageOtherOut-taxprice").val(data.newstorageprice);
                    $("#storage-storageOtherOut-boxnum").val(data.boxnum);

                    $("#storage-storageOtherOut-unitnum").val(0);

                    if(data.isbatch=='1'){
                        $("#storage-storageOtherOut-batchno").widget("enable");
                        var storageid = $("#storage-storageOtherOut-storageid").widget("getValue");
                        var param = null;
                        if(storageid!=null && storageid!=""){
                            param = [{field:'goodsid',op:'equal',value:data.id},
                                {field:'storageid',op:'equal',value:storageid}];
                        }else{
                            param = [{field:'goodsid',op:'equal',value:data.id}];
                        }
                        var reqFlag = false;
                        if(storageInfo.isbatch=="1"){
                            reqFlag = true;
                        }else{
                            getGoodsUsenum();
                        }
                        $("#storage-storageOtherOut-batchno").widget({
                            referwid:'RL_T_STORAGE_BATCH_LIST',
                            param:param,
                            width:165,
                            required:reqFlag,
                            singleSelect:true,
                            onSelect: function(obj){
                                $("#storage-storageOtherOut-summarybatchid").val(obj.id);
                                $("#storage-storageOtherOut-storagelocationname").val(obj.storagelocationname);
                                $("#storage-storageOtherOut-storagelocationid").val(obj.storagelocationid);
                                $("#storage-storageOtherOut-produceddate").val(obj.produceddate);
                                $("#storage-storageOtherOut-deadline").val(obj.deadline);

                                maxnum = obj.usablenum;
                                $("#storage-storageOtherOut-loadInfo").html("现存量：<font color='green'>"+obj.existingnum+obj.unitname+"</font>&nbsp;可用量：<font color='green'>"+ obj.usablenum +obj.unitname+"</font>");
                                computNum();
                                $("#storage-storageOtherOut-taxamount").focus();
                                $("#storage-storageOtherOut-taxamount").select();
                            },
                            onClear:function(){
                                $("#storage-storageOtherOut-loadInfo").html("&nbsp;");
                                $("#storage-storageOtherOut-summarybatchid").val("");
                                $("#storage-storageOtherOut-storagelocationname").val("");
                                $("#storage-storageOtherOut-storagelocationid").val("");
                                $("#storage-storageOtherOut-produceddate").val("");
                                $("#storage-storageOtherOut-deadline").val("");
                            }
                        });
                        $("#storage-storageOtherOut-batchno").focus();

                    }else{
                        getGoodsUsenum();
                        computNum();
                        $("#storage-storageOtherOut-taxamount").focus();
                        $("#storage-storageOtherOut-taxamount").select();
                    }
                },
                onClear:function(){
                    $("#storage-storageOtherOut-batchno").widget("clear");
                    $("#storage-storageOtherOut-batchno").widget({
                        referwid:'RL_T_STORAGE_BATCH_LIST',
                        width:165,
                        disabled:true,
                        singleSelect:true
                    });
                    $("#storage-storageOtherOut-loadInfo").html("&nbsp;");
                    $("#storage-storageOtherOut-summarybatchid").val("");
                    $("#storage-storageOtherOut-storagelocationname").val("");
                    $("#storage-storageOtherOut-storagelocationid").val("");
                    $("#storage-storageOtherOut-produceddate").val("");
                    $("#storage-storageOtherOut-deadline").val("");
                }
            });
            $("#storage-storageOtherOut-batchno").widget({
                referwid:'RL_T_STORAGE_BATCH_LIST',
                width:165,
                disabled:true,
                singleSelect:true
            });
            $("#storage-storageOtherOut-taxprice").change(function(){
                computNum();
            });
            $("#storage-storageOtherOut-taxamount").change(function(){
                computNoTaxAmount();
            });


            $("#storage-storageOtherOut-notaxamount").change(function(){
                computTaxAmount();
            });
            $("#storage-storageOtherOut-unitnum").change(function(){
                computNum();
            });

            $("#storage-storageOtherOut-notaxprice").numberbox({
                precision:6,
                groupSeparator:','
            });

            $("#storage-storageOtherOut-unitnum").die("keydown").live("keydown",function(event){
                //enter
                if(event.keyCode==13){
                    $("#storage-storageOtherOut-unitnum").blur();
                    $("#storage-storageOtherOut-unitnum-aux").focus();
                    $("#storage-storageOtherOut-unitnum-aux").select();
                }
            });


            $("#storage-storageOtherOut-remark").die("keydown").live("keydown",function(event){
                if(event.keyCode==13){
                    $("#storage-storageOtherOut-taxprice").blur();
                    $("#storage-savegoon-storageOtherOutDetailAddPage").focus();
                }
            });
            $("#storage-savegoon-storageOtherOutDetailAddPage").die("keydown").live("keydown",function(event){
                //enter
                if(event.keyCode==13){
                    addSaveDetail(true);
                }
            });
            $("#storage-savegoon-storageOtherOutDetailAddPage").click(function(){
                addSaveDetail(true);
            });
        });
        //通过含税金额 税种计算未税金额
        function computNoTaxAmount(){
            var taxtype = $("#storage-storageOtherOut-taxtype").val();
            var taxamount=$("#storage-storageOtherOut-taxamount").val();
            $.ajax({
                url :'basefiles/getAmountMapByTaxAndAmount.do',
                type:'post',
                data:{taxtype:taxtype,taxamount:taxamount},
                dataType:'json',
                async:false,
                success:function(json){
                    $("#storage-storageOtherOut-notaxamount").val(json.notaxamount);
                    $("#storage-storageOtherOut-tax").val(json.tax);
                }
            });
        }

        //通过未税金额 税种计算含税金额
        function computTaxAmount(){
            var taxtype = $("#storage-storageOtherOut-taxtype").val();
            var notaxamount= $("#storage-storageOtherOut-notaxamount").val();
            $.ajax({
                url :'basefiles/getAmountMapByTaxAndAmount.do',
                type:'post',
                data:{taxtype:taxtype,notaxamount:notaxamount},
                dataType:'json',
                async:false,
                success:function(json){
                    $("#storage-storageOtherOut-taxamount").val(json.taxamount);
                    $("#storage-storageOtherOut-tax").val(json.tax);
                }
            });
        }

        //通过总数量 计算数量 金额换算
        function computNum(){
            $("#storage-storageOtherOut-unitnum").val(0);
            var goodsid= $("#storage-storageOtherOut-goodsid").goodsWidget("getValue");
            if(null==goodsid && goodsid!=""){
                return false;
            }
            var auxunitid = $("#storage-storageOtherOut-auxunitid").val();
            var unitnum = $("#storage-storageOtherOut-unitnum").val();
            var taxprice = $("#storage-storageOtherOut-taxprice").val();
            var notaxprice = $("#storage-storageOtherOut-notaxprice").val();
            var taxtype = $("#storage-storageOtherOut-taxtype").val();
            $("#storage-storageOtherOut-unitnum").addClass("inputload");
            $.ajax({
                url :'basefiles/computeGoodsByUnitnum.do',
                type:'post',
                data:{unitnum:unitnum,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
                dataType:'json',
                async:false,
                success:function(json){
                    $("#storage-storageOtherOut-taxtypename").val(json.taxtypename);
                    $("#storage-storageOtherOut-auxunitnumdetail").val(json.auxnumdetail);
                    $("#storage-storageOtherOut-auxunitnum").val(json.auxnum);
                    $("#storage-storageOtherOut-auxunitname").val(json.auxunitname);
                    $("#storage-storageOtherOut-auxunitname1").html(json.auxunitname);
                    $("#storage-storageOtherOut-goodsunitname").val(json.unitname);
                    $("#storage-storageOtherOut-goodsunitname1").html(json.unitname);

                    $("#storage-storageOtherOut-taxprice").val(json.taxprice);
                    $("#storage-storageOtherOut-notaxprice").val("setValue",json.notaxprice);

                    $("#storage-storageOtherOut-unitnum-aux").val(json.auxInteger);
                    $("#storage-storageOtherOut-unitnum-unit").val(json.auxremainder);
                    if(json.auxrate!=null){
                        $("#storage-storageOtherOut-unitnum-unit").attr("max",json.auxrate-1);
                    }
                    $("#storage-storageOtherOut-unitnum").removeClass("inputload");
                }
            });
        }

        //页面重置
        function otherEnterformReset(){
            $("#storage-form-storageOtherOutDetailAddPage").form("clear");
            $("#storage-storageOtherOut-batchno").widget("clear");
            $("#storage-storageOtherOut-batchno").widget({
                referwid:'RL_T_STORAGE_BATCH_LIST',
                width:165,
                disabled:true,
                singleSelect:true
            });
            $("#storage-storageOtherOut-auxunitname1").html("");
            $("#storage-storageOtherOut-goodsunitname1").html("");
            $("#storage-storageOtherOut-goodsid").focus();
        }

    </script>
</body>
</html>
