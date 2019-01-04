<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>其他入库单明细修改</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form action="" method="post" id="storage-form-storageOtherEnterDetailAddPage">
            <table  border="0" class="box_table">
                <tr>
                    <td width="120">选择商品:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherEnter-goodsname" width="180" class="no_input" readonly="readonly"/>
                        <input type="hidden" id="storage-storageOtherEnter-goodsid" name="goodsid"/>
                    </td>
                    <td width="120">条形码:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherEnter-goodsbarcode" class="no_input" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td>数量:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherEnter-unitnum" name="unitnum" class="formaterNum easyui-validatebox"  readonly="readonly" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
                    </td>
                    <td>辅数量:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherEnter-unitnum-aux" readonly="readonly"  name="auxnum" style="width:70px;" class="formaterNum easyui-validatebox" data-options="required:true,validType:'integer'"/>
                        <span id="storage-storageOtherEnter-auxunitname1" style="float: left;"></span>
                        <input type="text" id="storage-storageOtherEnter-unitnum-unit" readonly="readonly"  name="auxremainder" style="width:70px;" class="formaterNum easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
                        <span id="storage-storageOtherEnter-goodsunitname1" style="float: left;"></span>
                        <input type="hidden" id="storage-storageOtherEnter-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td >商品品牌:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherEnter-goodsbrandName" class="no_input" readonly="readonly"/>
                    </td>
                    <td>规格型号:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherEnter-goodsmodel" class="no_input" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td>主单位:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherEnter-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
                        <input type="hidden" id="storage-storageOtherEnter-goodsunitid" name="unitid"/>
                    </td>
                    <td>辅单位:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherEnter-auxunitname" name="auxunitname" class="no_input" readonly="readonly"/>
                        <input type="hidden" id="storage-storageOtherEnter-auxunitid" name="auxunitid"/>
                    </td>
                </tr>
                <tr>
                    <td>含税单价:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherEnter-taxprice" readonly="readonly" name="taxprice">
                    </td>
                    <td>含税金额:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherEnter-taxamount" class="easyui-validatebox"   data-options="required:true,validType:'intOrFloat',validType:'intOrFloatNum[6]'" name="taxamount"/>
                    </td>
                </tr>
                <tr>
                    <td>未税单价:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherEnter-notaxprice" name="notaxprice" class="no_input" readonly="readonly"/>
                    </td>
                    <td>未税金额:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherEnter-notaxamount" class="easyui-validatebox"   data-options="required:true,validType:'intOrFloat',validType:'intOrFloatNum[6]'"  name="notaxamount"/>
                    </td>
                </tr>
                <tr>
                    <td>税种:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherEnter-taxtypename" name="taxtypename" class="no_input" readonly="readonly"/>
                        <input type="hidden" id="storage-storageOtherEnter-taxtype" name="taxtype" class="no_input" readonly="readonly"/>
                    </td>
                    <td>税额:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherEnter-tax" name="tax" class="no_input" class="easyui-validatebox"   data-options="required:true,validType:'intOrFloat',validType:'intOrFloatNum[6]'" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td>生产日期:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherEnter-produceddate" style="height: 20px;" name="produceddate" class="WdateRead" readonly="readonly"/>
                    </td>
                    <td>有效截止日期:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherEnter-deadline" style="height: 20px;" name="deadline" class="WdateRead" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td>批次号:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherEnter-batchno" name="batchno" class="no_input" readonly="readonly"/>
                    </td>
                    <td>所属库位:</td>
                    <td style="text-align: left;">
                        <input type="text" id="storage-storageOtherEnter-storagelocationid" name="storagelocationid" disabled="disabled"/>
                        <input type="hidden" id="storage-storageOtherEnter-storagelocationname"  name="storagelocationname"/>
                    </td>
                </tr>
                <tr>
                    <td>备注:</td>
                    <td colspan="3" style="text-align: left;">
                        <input id="storage-storageOtherEnter-remark" type="text" name="remark" style="width: 488px;" maxlength="200"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align: right;">
            快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
            <input type="button" value="继续修改" name="savegoon" id="storage-savegoon-storageOtherEnterDetailEditPage" />
        </div>
    </div>
    <script type="text/javascript">
        //加载数据
        var object = $("#storage-datagrid-storageOtherEnterAddPage").datagrid("getSelected");
        $("#storage-form-storageOtherEnterDetailAddPage").form("load",object);
        $("#storage-storageOtherEnter-goodsname").val(object.goodsInfo.name);
        $("#storage-storageOtherEnter-goodsbrandName").val(object.goodsInfo.brandName);
        $("#storage-storageOtherEnter-goodsmodel").val(object.goodsInfo.model);
        $("#storage-storageOtherEnter-goodsbarcode").val(object.goodsInfo.barcode);
        $("#storage-storageOtherEnter-taxtypename").val(object.goodsInfo.defaulttaxtypeName);
        $("#storage-storageOtherEnter-goodsunitname1").html(object.unitname);
        $("#storage-storageOtherEnter-auxunitname1").html(object.auxunitname);
        $(function(){
            $("#storage-storageOtherEnter-storagelocationid").widget({
                name:'t_storage_salereject_enter_detail',
                width:165,
                col:'storagelocationid',
                singleSelect:true,
                disabled:true,
                onSelect:function(data){
                    $("#storage-storageOtherEnter-storagelocationname").val(data.name);
                },
                onClear:function(){
                    $("#storage-storageOtherEnter-storagelocationname").val("");
                }
            });
            $("#storage-storageOtherEnter-taxprice").change(function(){
                computNum();
            });
            $("#storage-storageOtherEnter-taxamount").change(function(){
                computNoTaxAmount();
            });
            $("#storage-storageOtherEnter-notaxamount").change(function(){
                computTaxAmount();
            });
            $("#storage-storageOtherEnter-notaxprice").numberbox({
                precision:6,
                groupSeparator:','
            });

            $("#storage-storageOtherEnter-produceddate").click(function(){
                if(!$("#storage-storageOtherEnter-batchno").hasClass("no_input")){
                    WdatePicker({dateFmt:'yyyy-MM-dd',
                        onpicked:function(dp){
                            if(dp.el.id=="storage-storageOtherEnter-produceddate"){
                                var produceddate = dp.cal.getDateStr();
                                var goodsid = $("#storage-storageOtherEnter-goodsid").val();
                                $.ajax({
                                    url :'storage/getBatchno.do',
                                    type:'post',
                                    data:{produceddate:produceddate,goodsid:goodsid},
                                    dataType:'json',
                                    async:false,
                                    success:function(json){
                                        $('#storage-storageOtherEnter-batchno').val(json.batchno);
                                        $('#storage-storageOtherEnter-deadline').val(json.deadline);
                                    }
                                });
                            }
                        }
                    });
                }
            });
            $("#storage-storageOtherEnter-deadline").click(function(){
                if(!$("#storage-storageOtherEnter-batchno").hasClass("no_input")){
                    WdatePicker({dateFmt:'yyyy-MM-dd',
                        onpicked:function(dp){
                            if(dp.el.id=="storage-storageOtherEnter-deadline"){
                                var deadline = dp.cal.getDateStr();
                                var goodsid = $("#storage-storageOtherEnter-goodsid").val();
                                $.ajax({
                                    url :'storage/getBatchnoByDeadline.do',
                                    type:'post',
                                    data:{deadline:deadline,goodsid:goodsid},
                                    dataType:'json',
                                    async:false,
                                    success:function(json){
                                        $('#storage-storageOtherEnter-batchno').val(json.batchno);
                                        $('#storage-storageOtherEnter-produceddate').val(json.produceddate);
                                    }
                                });
                            }
                        }
                    });
                }
            });
            $("#storage-storageOtherEnter-unitnum").change(function(){
                computNum();
            });

            $("#storage-storageOtherEnter-remark").die("keydown").live("keydown",function(event){
                if(event.keyCode==13){
                    $("#storage-storageOtherEnter-taxprice").blur();
                    $("#storage-savegoon-storageOtherEnterDetailEditPage").focus();
                }
            });
            $("#storage-savegoon-storageOtherEnterDetailEditPage").die("keydown").live("keydown",function(event){
                //enter
                if(event.keyCode==13){
                    editSaveDetail(true);
                }
            });
            $("#storage-savegoon-storageOtherEnterDetailEditPage").click(function(){
                editSaveDetail(true);
            });
        });
        //通过总数量 计算数量 金额换算
        function computNum(){
            var goodsid= $("#storage-storageOtherEnter-goodsid").val();
            var auxunitid = $("#storage-storageOtherEnter-auxunitid").val();
            var unitnum = $("#storage-storageOtherEnter-unitnum").val();
            var taxprice = $("#storage-storageOtherEnter-taxprice").val();
            var notaxprice = $("#storage-storageOtherEnter-notaxprice").numberbox("getValue");
            var taxtype = $("#storage-storageOtherEnter-taxtype").val();
            if(taxprice==null || taxprice=="" || notaxprice==null || notaxprice=="" || taxtype==null || taxtype==""){
                return false;
            }
            $("#storage-storageOtherEnter-unitnum").addClass("inputload");
            $.ajax({
                url :'basefiles/computeGoodsByUnitnum.do',
                type:'post',
                data:{unitnum:unitnum,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
                dataType:'json',
                async:false,
                success:function(json){
                    $("#storage-storageOtherEnter-taxtypename").val(json.taxtypename);
                    $("#storage-storageOtherEnter-auxunitnumdetail").val(json.auxnumdetail);
                    $("#storage-storageOtherEnter-auxunitnum").val(json.auxnum);
                    $("#storage-storageOtherEnter-auxunitname").val(json.auxunitname);
                    $("#storage-storageOtherEnter-auxunitname1").html(json.auxunitname);
                    $("#storage-storageOtherEnter-goodsunitname").val(json.unitname);
                    $("#storage-storageOtherEnter-goodsunitname1").html(json.unitname);

                    $("#storage-storageOtherEnter-taxprice").val(json.taxprice);
                    $("#storage-storageOtherEnter-notaxprice").numberbox("setValue",json.notaxprice);

                    $("#storage-storageOtherEnter-unitnum-aux").val(json.auxInteger);
                    $("#storage-storageOtherEnter-unitnum-unit").val(json.auxremainder);
                    if(json.auxrate!=null){
                        $("#storage-storageOtherEnter-unitnum-unit").attr("max",json.auxrate-1);
                    }
                    $("#storage-storageOtherEnter-unitnum").removeClass("inputload");
                }
            });
        }
        //通过含税金额 税种计算未税金额
        function computNoTaxAmount(){
            var taxtype = $("#storage-storageOtherEnter-taxtype").val();
            var taxamount=$("#storage-storageOtherEnter-taxamount").val();
            $.ajax({
                url :'basefiles/getAmountMapByTaxAndAmount.do',
                type:'post',
                data:{taxtype:taxtype,taxamount:taxamount},
                dataType:'json',
                async:false,
                success:function(json){
                    $("#storage-storageOtherEnter-notaxamount").val(json.notaxamount);
                    $("#storage-storageOtherEnter-tax").val(json.tax);
                }
            });
        }

        //通过未税金额 税种计算含税金额
        function computTaxAmount(){
            var taxtype = $("#storage-storageOtherEnter-taxtype").val();
            var notaxamount= $("#storage-storageOtherEnter-notaxamount").val();
            $.ajax({
                url :'basefiles/getAmountMapByTaxAndAmount.do',
                type:'post',
                data:{taxtype:taxtype,notaxamount:notaxamount},
                dataType:'json',
                async:false,
                success:function(json){
                    $("#storage-storageOtherEnter-taxamount").val(json.taxamount);
                    $("#storage-storageOtherEnter-tax").val(json.tax);
                }
            });
        }
        //页面重置
        function otherEnterformReset(){
            $("#storage-form-storageOtherEnterDetailAddPage").form("clear");
            $("#storage-storageOtherEnter-auxunitname1").html("");
            $("#storage-storageOtherEnter-goodsunitname1").html("");
            $("#storage-storageOtherEnter-goodsid").focus();
        }
        //根据商品属性 设置批次相关页面
        function setDetailIsbatch(){
            var oldgoodsid = $("#storage-storageOtherEnter-goodsid").val();
            $.ajax({
                url :'storage/getGoodsInfo.do',
                type:'post',
                data:{goodsid:oldgoodsid},
                dataType:'json',
                async:false,
                success:function(json){
                    if(json.goodsInfo!=null){
                        //商品进行批次管理时
                        if(json.goodsInfo.isbatch=="1"){
                            $("#storage-storageOtherEnter-storagelocationid").widget("enable");
                            $('#storage-storageOtherEnter-batchno').validatebox({required:true});
                            $("#storage-storageOtherEnter-batchno").removeClass("no_input");
                            $("#storage-storageOtherEnter-batchno").removeAttr("readonly");

                            $('#storage-storageOtherEnter-produceddate').validatebox({required:true});
                            $("#storage-storageOtherEnter-produceddate").removeClass("WdateRead");
                            $("#storage-storageOtherEnter-produceddate").addClass("Wdate");
                            $("#storage-storageOtherEnter-produceddate").removeAttr("readonly");
                            $("#storage-storageOtherEnter-deadline").removeClass("WdateRead");
                            $("#storage-storageOtherEnter-deadline").addClass("Wdate");
                            $("#storage-storageOtherEnter-deadline").removeAttr("readonly");

                        }else{
                            $("#storage-storageOtherEnter-storagelocationid").widget("clear");
                            $("#storage-storageOtherEnter-storagelocationid").widget("disable");

                            $('#storage-storageOtherEnter-batchno').validatebox({required:false});
                            $('#storage-storageOtherEnter-batchno').validatebox("validate");
                            $("#storage-storageOtherEnter-batchno").addClass("no_input");
                            $("#storage-storageOtherEnter-batchno").attr("readonly","readonly");

                            $('#storage-storageOtherEnter-produceddate').validatebox({required:false});
                            $('#storage-storageOtherEnter-produceddate').validatebox("validate");
                            $("#storage-storageOtherEnter-produceddate").addClass("WdateRead");
                            $("#storage-storageOtherEnter-produceddate").attr("readonly","readonly");

                            $("#storage-storageOtherEnter-deadline").addClass("WdateRead");
                            $("#storage-storageOtherEnter-deadline").attr("readonly","readonly");
                        }
                    }
                }
            });
        }
        setDetailIsbatch();
    </script>
</body>
</html>
