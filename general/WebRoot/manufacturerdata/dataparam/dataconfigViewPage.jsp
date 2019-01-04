<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>添加厂家信息</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form action="" method="post" id="dataparam-form-addDataconfig">
            <table  border="0" class="box_table">
                <tr>
                    <td width="120">对接方名称:</td>
                    <td style="text-align: left;">
                        <input type="text" id="dataparam-name-addDataconfig" name="name" style="width: 150px" class="easyui-validatebox"  required="required" readonly="readonly"/>
                    </td>
                    <td width="120">标识:</td>
                    <td style="text-align: left;">
                        <input type="text" id="dataparam-markid-addDataconfig" name="markid" style="width: 150px"  class="easyui-validatebox"  required="required"  readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td width="120">供应商:</td>
                    <td style="text-align: left;" colspan="3">
                        <input type="text" id="dataparam-supplierid-addDataconfig" name="supplierid" style="width: 320px"/>
                        <input type="hidden" id="dataparam-suppliername-addDataconfig" name="suppliername" style="width: 320px"/>
                    </td>
                </tr>
                <tr>
                    <td width="120">对应商品类型:</td>
                    <td style="text-align: left;">
                        <select id="dataparam-goodstype-addDataconfig" name="goodstype" style="width:150px;" >
                            <option value="1" selected="selected">全部</option>
                            <option value="2">按供应商</option>
                            <option value="3">按品牌</option>
                        </select>
                    </td>
                    <td width="120">对应商品类型编号:</td>
                    <td style="text-align: left;" id="gtypeids">
                        <input type="text" id="dataparam-gtypeids-addDataconfig" name="gtypeids" style="width: 150px" />
                        <input type="hidden" id="dataparam-gtypenames-addDataconfig" name="gtypenames" style="width: 150px"/>
                    </td>
                </tr>
                <tr>
                    <td width="120">对应客户类型:</td>
                    <td style="text-align: left;">
                        <select id="dataparam-customertype-addDataconfig" name="customertype" style="width:150px;" >
                            <option value="1" selected="selected">全部</option>
                            <option value="2">按部门</option>
                            <option value="3">按销售区域</option>
                            <option value="4">按客户分类</option>
                        </select>
                    </td>
                    <td width="120">对应客户类型编号:</td>
                    <td style="text-align: left;" id="ctypeids">
                        <input type="text" id="dataparam-ctypeids-addDataconfig" name="ctypeids" style="width: 150px" />
                        <input type="hidden" id="dataparam-ctypenames-addDataconfig" name="ctypenames" style="width: 150px"/>
                    </td>
                </tr>
                <tr>
                    <td width="120">对应仓库类型:</td>
                    <td style="text-align: left;">
                        <select id="dataparam-storagetype-addDataconfig" name="storagetype" style="width:150px;">
                            <option value="1" selected="selected">全部</option>
                            <option value="2">部分仓库</option>

                        </select>
                    </td>
                    <td width="120">对应仓库:</td>
                    <td style="text-align: left;" id="storageid">
                        <input type="text" id="dataparam-storageid-addDataconfig" name="storageid" style="width: 150px"/>
                        <input type="hidden" id="dataparam-storagename-addDataconfig" name="storagename" style="width: 150px"/>
                    </td>

                </tr>
                <tr>
                    <td width="120">虚拟仓库编号:</td>
                    <td style="text-align: left;">
                        <input type="text" id="dataparam-vstorageid-addDataconfig" name="vstorageid" style="width: 150px"  class="easyui-validatebox"  required="required" readonly="readonly"/>
                    </td>
                    <td width="120">虚拟仓库名称:</td>
                    <td style="text-align: left;">
                        <input type="text" id="dataparam-vstoragename-addDataconfig" name="vstoragename" style="width: 150px"  class="easyui-validatebox"  required="required" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td width="120">状态:</td>
                    <td style="text-align: left;">
                        <select id="dataparam-state-addDataconfig" name="state" style="width:150px;"  readonly="readonly" disabled="disabled">
                            <option value="0" >禁用</option>
                            <option value="1">启用</option>
                            <option value="2">保存</option>
                        </select>
                    </td>
                    <td width="120">优先级:</td>
                    <td style="text-align: left;">
                        <select id="dataparam-level-addDataconfig" name="level" style="width:150px;" readonly="readonly" disabled="disabled">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="120">视图类型:</td>
                    <td style="text-align: left;">
                        <select id="dataparam-viewtype-addDataconfig" name="viewtype" style="width:150px;" >
                            <option value="0">默认视图</option>
                            <option value="1">数据对接视图</option>
                        </select>
                    </td>
                    <td colspan="2"></td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align: right;">
            快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
            <input type="button" value="保存" name="savegoon" id="dataparam-savegoon-addDataconfig" />
        </div>
    </div>
</div>
<script type="text/javascript">

    $(function (){
        //加载数据
        var object=$("#dataparam-table").datagrid("getSelected");
        $("#dataparam-name-addDataconfig").val(object.name);
        $("#dataparam-markid-addDataconfig").val(object.markid);
        $("#dataparam-state-addDataconfig").val(object.state);
        $("#dataparam-supplierid-addDataconfig").val(object.supplierid);
        $("#dataparam-goodstype-addDataconfig").val(object.goodstype);
        $("#dataparam-customertype-addDataconfig").val(object.customertype);
        $("#dataparam-storagetype-addDataconfig").val(object.storagetype);
        $("#dataparam-vstorageid-addDataconfig").val(object.vstorageid);
        $("#dataparam-vstoragename-addDataconfig").val(object.vstoragename);
        $("#dataparam-level-addDataconfig").val(object.level);
        $("#dataparam-viewtype-addDataconfig").val(object.viewtype);

        if(object.goodstype=='1'){
            changeGoodstypeWidget("1","","1");
        }else{
            changeGoodstypeWidget(object.goodstype,object.gtypeids,"0");
        }

        if(object.customertype=='1'){
            changeCustomertypeWidget("1","","1");
        }else{
            changeCustomertypeWidget(object.customertype,object.ctypeids,"0");
        }

        if(object.storagetype=='1'){
            changeStoragetypeWidget("1","","1");
        }else{
            changeStoragetypeWidget(object.storagetype,object.storageid,"0");
        }

        //供应商选择
        $("#dataparam-supplierid-addDataconfig").widget({
            referwid:'RL_T_BASE_BUY_SUPPLIER',
            col:'supplierid',
            width:320,
            singleSelect:false,
            onlyLeafCheck:true,
            required:true,
            onSelect: function(data) {
                $("#dataparam-suppliername-addDataconfig").val(data.name);
            }
        });
        $("#dataparam-savegoon-addDataconfig").click(function(){
            editDataConfig(true);
        });

        //对应商品类型改变
        $("#dataparam-goodstype-addDataconfig").change(function(){
            if($(this).val()=="1"){
                changeGoodstypeWidget("1","","1");
            }
            else if($(this).val()=="2"){
                changeGoodstypeWidget("2","","0");
            }else if($(this).val()=="3"){
                changeGoodstypeWidget("3","","0");
            }
        });
        function changeGoodstypeWidget(sourcetype,sourceid,disabled){
            $("#gtypeids").empty();
            var tdstr = "",disabledstr="";
            if(disabled == "1"){
                disabledstr = 'disabled="disabled"';
            }
            tdstr='<input type="text" id="dataparam-gtypeids-addDataconfig" name="gtypeids" style="width: 150px"  value="'+sourceid+'" '+disabledstr+'/>';
            $(tdstr).appendTo("#gtypeids");
            if("2" == sourcetype){
                $("#dataparam-gtypeids-addDataconfig").widget({
                    referwid:'RL_T_BASE_BUY_SUPPLIER',
                    singleSelect:false,
                    required:true,
                    setValueSelect:true,
                    width: 150,
                    onSelect: function(data) {
                        $("#dataparam-gtypenames-addDataconfig").val(data.name);
                    }
                });
            }else if("3" == sourcetype){
                $("#dataparam-gtypeids-addDataconfig").widget({
                    referwid:"RL_T_BASE_GOODS_BRAND",
                    singleSelect:false,
                    required:true,
                    setValueSelect:true,
                    width: 150,
                    onSelect: function(data) {
                        $("#dataparam-gtypenames-addDataconfig").val(data.name);
                    }
                });
            }
        }

        //对应客户类型改变
        $("#dataparam-customertype-addDataconfig").change(function(){
            if($(this).val()=="1"){
                changeCustomertypeWidget("1","","1");
            }
            else if($(this).val()=="2"){
                changeCustomertypeWidget("2","","0");
            }else if($(this).val()=="3"){
                changeCustomertypeWidget("3","","0");
            }else if($(this).val()=="4"){
                changeCustomertypeWidget("4","","0");
            }
        });
        function changeCustomertypeWidget(sourcetype,sourceid,disabled){
            $("#ctypeids").empty();
            var tdstr = "",disabledstr="";
            if(disabled == "1"){
                disabledstr = 'disabled="disabled"';
            }
            tdstr='<input type="text" id="dataparam-ctypeids-addDataconfig" name="ctypeids" style="width: 150px" value="'+sourceid+'"  '+disabledstr+'/>';
            $(tdstr).appendTo("#ctypeids");
            if("2" == sourcetype){
                $("#dataparam-ctypeids-addDataconfig").widget({
                    referwid:'RL_T_BASE_DEPARTMENT_SELLER',
                    singleSelect:false,
                    required:true,
                    setValueSelect:true,
                    width: 150,
                    onSelect: function(data) {
                        $("#dataparam-ctypenames-addDataconfig").val(data.name);
                    }

                });
            }else if("3" == sourcetype){
                $("#dataparam-ctypeids-addDataconfig").widget({
                    referwid:"RT_T_BASE_SALES_AREA",
                    singleSelect:false,
                    required:true,
                    setValueSelect:true,
                    width: 150,
                    onSelect: function(data) {
                        $("#dataparam-ctypenames-addDataconfig").val(data.name);
                    }
                });
            }else if("4" == sourcetype){
                $("#dataparam-ctypeids-addDataconfig").widget({
                    referwid:"RT_T_BASE_SALES_CUSTOMERSORT",
                    singleSelect:false,
                    required:true,
                    setValueSelect:true,
                    width: 150,
                    onSelect: function(data) {
                        $("#dataparam-ctypenames-addDataconfig").val(data.name);
                    }
                });
            }
        }
        //对应仓库类型改变
        $("#dataparam-storagetype-addDataconfig").change(function(){
            if($(this).val()=="1"){
                changeStoragetypeWidget("1","","1");
            }
            else if($(this).val()=="2"){
                changeStoragetypeWidget("2","","0");
            }
        });
        function changeStoragetypeWidget(sourcetype,sourceid,disabled){
            console.log(sourceid)
            $("#storageid").empty();
            var tdstr = "",disabledstr="";
            if(disabled == "1"){
                disabledstr = 'disabled="disabled"';
            }
            tdstr='<input type="text" id="dataparam-storageid-addDataconfig" name="storageid" style="width: 150px" value="'+sourceid+'" '+disabledstr+'/>';
            $(tdstr).appendTo("#storageid");
            if("2" == sourcetype){
                $("#dataparam-storageid-addDataconfig").widget({
                    referwid:'RL_T_BASE_STORAGE_INFO',
                    width:150,
                    singleSelect:false,
                    setValueSelect:true,
                    required:true,
                    onSelect: function(data) {
                        $("#dataparam-storagename-addDataconfig").val(data.name);
                    }
                });
            }
        }
    })

</script>
</body>
</html>

