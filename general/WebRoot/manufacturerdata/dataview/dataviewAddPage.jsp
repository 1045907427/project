<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form action="" method="post" id="dataview-form-addDateview">
            <table  border="0" class="box_table" id="dataview-sql-table">
                <tr>
                    <td width="120">视图类型:</td>
                    <td style="text-align: left;">
                        <select id="dataview-viewtype-addDateview" name="viewtype" style="width:150px;">
                            <option selected="selected"></option>
                            <option value="1">客户视图</option>
                            <option value="2">商品视图</option>
                            <option value="3">销售视图</option>
                            <option value="4">采购视图</option>
                            <option value="5">盘点视图</option>
                            <option value="6">库存视图</option>
                        </select>
                    </td>
                    <td width="60">视图名称:</td>
                    <td style="text-align: left;">
                        <input type="text" id="dataview-viewname-addDateview" name="viewname" style="width: 150px"  class="easyui-validatebox"  required="required"/>
                    </td>
                    <td colspan="2"></td>
                </tr>
                <tr>
                    <td width="120">sql预览:</td>
                    <td style="text-align: left;" colspan="3">
                        <textarea rows="" cols="" id="dataview-sqlview-addDateview"  validtype="sqlview[1000]" style="width: 452px;height:150px;" name="sqlview"></textarea>
                    </td>
                </tr>
                <tr  class="dataview-param-tr" indexTab="0">
                    <td width="120">参数:</td>
                    <td style="text-align: left;">
                        <select id="dataview-paramtype-addDateview_0" name="paramtype" style="width:150px;" class="dataview-paramtype" indexTab="0">
                            <option selected="selected" value="0">自定义</option>
                            <option value="1">客户编码</option>
                            <option value="2">商品编码</option>
                            <option value="3">仓库编码</option>
                            <option value="4">供应商</option>
                        </select>
                    </td>
                    <td style="text-align: left;" id="dataview-paramrule-td_0">
                        <input type="text" id="dataview-paramrule-addDateview_0" name="paramrule" class="dataview-paramrule" style="width: 150px"  disabled="disabled"/>
                    </td>
                    <td style="text-align: left;" id="dataview-paramvalue-td_0">
                        <input type="text" id="dataview-paramvalue-addDateview_0" name="paramvalue" style="width: 150px"  class="easyui-validatebox"  required="required"/>
                    </td>
                    <td>
                        <a class="dataview-param-del" href="javascript:void(0);" indexTab="0">删除</a>&nbsp;<a class="dataview-param-add" href="javascript:void(0);" indexTab="0">添加</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align: right;">
            快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
            <input type="button" value="保存" name="savegoon" id="dataview-savegoon-addDateview" />
        </div>
    </div>
</div>

<script type="text/javascript">
    var sqlList = new Array();
    var sqlparamList = new Array();
    var thisIndex = 0;
    $(function() {

        $("#dataview-savegoon-addDateview").click(function(){
            addDataView(false);
        });
        $(".dataview-param-add").bind("click", function () {
            console.log(111)
            var index = $(this).attr("indexTab");
            var len = $(".dataview-param-add").length;
            thisIndex = Number(thisIndex) + Number(1);
            var seq = Number(len) + Number(1);
            var html = '<tr  class="dataview-param-tr" indexTab="'+thisIndex+'"> '+
                '<td width="120">参数:</td> '+
                '<td style="text-align: left;"> '+
                '<select id="dataview-paramtype-addDateview_'+thisIndex+'" name="paramtype" style="width:150px;" class="dataview-paramtype" indexTab="'+thisIndex+'"> '+
                '<option selected="selected" value="0">自定义</option> '+
                '<option value="1">客户编码</option> '+
                '<option value="2">商品编码</option> '+
                '<option value="3">仓库编码</option> '+
                '<option value="4">供应商</option> '+
                '</select> '+
                '</td> '+
                '<td style="text-align: left;" id="dataview-paramrule-td_'+thisIndex+'"> '+
                '<input type="text" id="dataview-paramrule-addDateview_'+thisIndex+'" name="paramrule" class="dataview-paramrule" style="width: 150px"  disabled="disabled"/> '+
                '</td> '+
                '<td style="text-align: left;" id="dataview-paramvalue-td_'+thisIndex+'"> '+
                '<input type="text" id="dataview-paramvalue-addDateview_'+thisIndex+'" name="paramvalue" style="width: 150px"  class="easyui-validatebox"  required="required"/> '+
                '</td> '+
                '<td> '+
                '<a class="dataview-param-del" href="javascript:void(0);" indexTab="'+thisIndex+'">删除</a>&nbsp;<a class="dataview-param-add" href="javascript:void(0);" indexTab="'+thisIndex+'">添加</a> '+
                '</td> '+
                '</tr> ';
            $("#dataview-sql-table").append(html);
        });
        $(".dataview-param-del").bind("click", function () {
            var len = $(".dataview-param-add").length;
            if (len > 1) {
                var index = $(this).attr("indexTab");
                $(".dataview-param-tr").each(function () {
                    var trIndex = $(this).attr("indexTab");
                    if (trIndex == index) {
                        $(this).remove();
                    }
                });
            } else {
                $.messager.alert("提醒", "最后一条数据不能删除。");
            }
        });
        $("#dataview-form-addDateview").form({
            onSubmit: function(){
                var flag = $(this).form('validate');
                if(flag==false){
                    return false;
                }
                loading("提交中..");
            },
            success:function(data){
                //表单提交完成后 隐藏提交等待页面
                loaded();
                //转为json对象
                var json = $.parseJSON(data);
                if(json.flag){
                    $.messager.alert("提醒",'新增成功');
                    $("#dataview-dialog-content").dialog('destroy');
                    refresh();
                }else{
                    $.messager.alert("警告",'新增失败');
                }
            }
        });
    })

    //视图类型改变
    $("#dataview-viewtype-addDateview").change(function(){
        initView();
        changeViewType($(this).val());
    });
    
    function initView() {
        $("#dataview-viewname-addDateview").val("");
        $("#dataview-sqlview-addDateview").val("");
        $(".dataview-param-tr").each(function () {
            var trIndex = $(this).attr("indexTab");
            if (trIndex != "0") {
                $(this).remove();
            }
        });
        $("#dataview-paramtype-addDateview_0").val("0");
        changeParamValueWidget("0","","0","0");
        changeParamRuleWidget("0","","0","0");

    }
    
    function changeViewType(viewtype){
        var sqlIndexList = new Array();
        if(viewtype=="1"){
            var sql='SELECT t.id AS customerid,t.name AS customername,"" AS linkman,"" AS telphone,t.remark AS remark FROM t_base_sales_customer t WHERE t.state = "1" ';
            sqlIndexList[sqlIndexList.length] = sql;
            $("#dataview-viewname-addDateview").val("customer_");
        }else if(viewtype=='2'){
            var sql='SELECT t.id AS goodsid,t.barcode AS barcode,t.name AS goodsname,t.model AS model,m.name AS unitname,"" AS unitname1,"" AS unitname2,"" AS unitname3,NULL AS price,"" AS rate1,"" AS rate2,"" AS rate3,t.remark AS remark  FROM t_base_goods_info t  LEFT JOIN t_base_goods_meteringunit m ON t.mainunit = m.id WHERE 1 = 1 ';
            sqlIndexList[sqlIndexList.length] = sql;
            $("#dataview-viewname-addDateview").val("goods_");
        }else if(viewtype == '3'){
            var sql='SELECT t.id AS billid,t.businessdate AS businessdate,"销售" AS billtype,c.name AS customername,t.customerid AS customerid,d.name AS deptname,g.name AS goodsname,t1.goodsid AS goodsid,t1.unitname AS unitname,t1.unitnum AS unitnum,t1.taxprice AS taxprice,t1.taxamount AS taxamount,t1.storageid AS storageid,t1.batchno AS batchno,t.STATUS AS STATUS,t1.remark AS remark FROM t_storage_saleout t RIGHT JOIN t_storage_saleout_detail t1 ON t.id = t1.saleoutid LEFT JOIN t_base_department d ON t.salesdept = d.id INNER JOIN t_base_sales_customer c ON c.id = t.customerid INNER JOIN t_base_goods_info g ON g.id = t1.goodsid WHERE t.STATUS IN ("3", "4") and t.customersort NOT IN("99") ';
            sqlIndexList[sqlIndexList.length] = sql;
            var sql=' UNION ALL SELECT t.id AS billid,t.businessdate AS businessdate,"销售" AS billtype,c.name AS customername,t.customerid AS customerid,d.name AS deptname,g.name AS goodsname,t1.goodsid AS goodsid,t1.unitname AS unitname ,- (t1.unitnum) AS unitnum,t1.taxprice AS taxprice ,- (t1.taxamount) AS taxamount,t.storageid AS storageid,t1.batchno AS batchno,t.STATUS AS STATUS,t1.remark AS remark FROM t_storage_salereject_enter t RIGHT JOIN t_storage_salereject_enter_detail t1 ON t.id = t1.salerejectid LEFT JOIN t_base_department d ON t.salesdept = d.id INNER JOIN t_base_sales_customer c ON c.id = t.customerid INNER JOIN t_base_goods_info g ON g.id = t1.goodsid WHERE  t.STATUS IN ("3", "4") 	AND t.ischeck = "1" ';
            sqlIndexList[sqlIndexList.length] = sql;
            $("#dataview-viewname-addDateview").val("sales_");
        }else if(viewtype == '4'){
            var sql = 'SELECT t.id AS billid,t1.id AS detailid,t.businessdate AS businessdate,"进货" AS billtype,t.supplierid AS supplierid,s.name AS suppliername,t1.goodsid AS goodsid,g.name AS goodsname,t1.unitname AS unitname,t1.unitnum AS unitnum,t1.taxprice AS taxprice,t1.taxamount AS taxamount,t1.batchno AS batchno,t1.storageid AS storageid,t1.remark AS remark FROM t_storage_purchase_enter_detail t1 LEFT JOIN t_storage_purchase_enter t ON t.id = t1.purchaseenterid JOIN t_base_buy_supplier s ON s.id = t.supplierid JOIN t_base_goods_info g ON g.id = t1.goodsid WHERE t.status IN("3", "4") ';
            sqlIndexList[sqlIndexList.length] = sql;
            var sql = 'UNION ALL SELECT t.id AS billid,t1.id AS detailid,t.businessdate AS businessdate,"进货" AS billtype,t.supplierid AS supplierid,s.name AS suppliername,t1.goodsid AS goodsid,g.name AS goodsname,t1.unitname AS unitname ,- (t1.unitnum) AS unitnum,t1.taxprice AS taxprice ,- (t1.taxamount) AS taxamount,t1.batchno AS batchno,t1.storageid AS storageid,t1.remark AS remark FROM t_storage_purchasereject_out_detail t1 LEFT JOIN t_storage_purchasereject_out t ON t.id = t1.orderid JOIN t_base_buy_supplier s ON s.id = t.supplierid JOIN t_base_goods_info g ON g.id = t1.goodsid WHERE  t.status IN("3", "4") ';
            sqlIndexList[sqlIndexList.length] = sql;
            $("#dataview-viewname-addDateview").val("enter_");
        }else if(viewtype == '5'){
            var sql='SELECTt.id AS billid,t.businessdate AS businessdate,"盘点" AS billtype,t1.goodsid AS goodsid,g.name AS goodsname,t1.unitname AS unitname,t1.unitnum AS unitnum,t1.taxprice AS taxprice,t1.taxamount AS taxamount,t1.batchno AS batchno,t1.storageid AS storageid,t1.remark AS remark FROM t_storage_other_enter_detail t1 LEFT JOIN t_storage_other_enter t ON t.id = t1.billid JOIN t_base_goods_info g ON g.id = t1.goodsid WHERE  t.status IN("3", "4") ';
            sqlIndexList[sqlIndexList.length] = sql;
            var sql=' UNION ALL SELECT	t.id AS billid,	t.businessdate AS businessdate,	"盘点" AS billtype,	t1.goodsid AS goodsid,	g.name AS goodsname,	t1.unitname AS unitname,	t1.unitnum AS unitnum,	t1.taxprice AS taxprice,	t1.taxamount AS taxamount,	t1.batchno AS batchno,	t1.storageid AS storageid,	t1.remark AS remark FROM t_storage_other_out_detail t1 LEFT JOIN t_storage_other_out t ON t.id = t1.billid JOIN t_base_goods_info g ON g.id = t1.goodsid WHERE AND t.status IN("3", "4") ';
            sqlIndexList[sqlIndexList.length] = sql;
            $("#dataview-viewname-addDateview").val("other_");
        }else if(viewtype == '6'){
            var sql = 'SELECT date_format(now(), "%Y-%m-%d") AS businessdate,t.goodsid AS goodsid,g.name AS goodsname,sum(t.existingnum) AS nums,t.unitname AS unitname,"" AS batchno,t.storageid AS storageid,"" AS remark ROM t_storage_summary t JOIN t_base_goods_info g WHERE t.goodsid = g.id  ';
            sqlIndexList[sqlIndexList.length] = sql;
            var sql = 'GROUP BY t.goodsid,t.storageid ';
            sqlIndexList[sqlIndexList.length] = sql;
            $("#dataview-viewname-addDateview").val("inventory_");
        }
        sqlList=sqlIndexList;
        changeSqlView();
    }

    function changeViewParam(){
        var sqlparamIndexList = new Array();
        $(".dataview-param-tr").each(function () {
            var indexTab = $(this).attr("indexTab");
            var paramtype  = $("#dataview-paramtype-addDateview_"+indexTab).val();
            var paramrule  = $("#dataview-paramrule-addDateview_"+indexTab).val();
            var paramvalue = $("#dataview-paramvalue-addDateview_"+indexTab).widget("getValue");
            var sqlparam="";
            if(""!=paramvalue ){
                if("0" == paramtype){
                    sqlparam = paramvalue;
                }else{
                    if("1"==paramtype){
                        sqlparam=" t.customerid ";
                    }else if("2"==paramtype){
                        sqlparam=" t.goodsid ";
                    }else if("3"==paramtype){
                        sqlparam=" t.storageid ";
                    }else if("4"==paramtype){
                        sqlparam=" t.supplierid ";
                    }

                    if("1"==paramrule){
                        sqlparam += " = ";
                    }else if("2"==paramrule){
                        sqlparam += " != ";
                    }

                    sqlparam = sqlparam + '"' +paramvalue + '"' ;
                }
            }
            sqlparamIndexList[sqlparamIndexList.length] = sqlparam;
        });
        sqlparamList=sqlparamIndexList;
        changeSqlView();
    }


    function changeSqlView(){
        var sqlview="";
        for (var i = 0; i < sqlList.length; i++) {
            var sql=sqlList[i];
            sqlview += sql;
            for (var j = 0; j < sqlparamList.length; j++) {
                var sqlparam=sqlparamList[j];
                sqlview = sqlview + "and" + sqlparam;
            }
        }
        $("#dataview-sqlview-addDateview").val(sqlview);
    }


    //参数类型改变
    $(".dataview-paramtype").change(function(){
        var indexTab = $(this).attr("indexTab");
        changeParamValueWidget($(this).val(),"","0",indexTab);
        changeParamRuleWidget($(this).val(),"","0",indexTab);
    });

    function changeParamValueWidget(sourcetype,sourceid,disabled,indexTab){
        $("#dataview-paramvalue-td_"+indexTab).empty();
        var tdstr = "",disabledstr="";
        if(disabled == "1"){
            disabledstr = 'disabled="disabled"';
        }
        tdstr = '<input type="text" id="dataview-paramvalue-addDateview_'+indexTab+'" type="text" name="paramvalue" style="width: 150px;" autocomplete="off" value="'+sourceid+'" '+disabledstr+'/>';
        $(tdstr).appendTo("#dataview-paramvalue-td_"+indexTab);
        if("1" == sourcetype){//客户
            $("#dataview-paramvalue-addDateview_"+indexTab).widget({
                referwid:"RL_T_BASE_SALES_CUSTOMER_PARENT_2",
                singleSelect:true,
                width: 150,
                onSelect:function(data){
                    changeViewParam();
                }
            });
        }else if("2" == sourcetype){//商品
            $("#dataview-paramvalue-addDateview_"+indexTab).widget({
                referwid:"RL_T_BASE_GOODS_INFO",
                singleSelect:true,
                width: 150,
                onSelect:function(data){
                    changeViewParam();
                }
            });
        }else if("3" == sourcetype){//仓库
            $("#dataview-paramvalue-addDateview_"+indexTab).widget({
                referwid:"RL_T_BASE_STORAGE_INFO",
                singleSelect:true,
                width: 150,
                onSelect:function(data){
                    changeViewParam();
                }
            });
        }else if("4" == sourcetype){//供应商
            $("#dataview-paramvalue-addDateview_"+indexTab).widget({
                referwid:"RL_T_BASE_BUY_SUPPLIER",
                singleSelect:true,
                width: 150,
                onSelect:function(data){
                    changeViewParam();
                }
            });
        }
    }
    //参数类型改变
    $(".dataview-paramrule").change(function(){
        changeViewParam();
    });
    function changeParamRuleWidget(sourcetype,sourceid,disabled,indexTab){
        $("#dataview-paramrule-td_"+indexTab).empty();
        var tdstr = "",disabledstr="";
        if(disabled == "1"){
            disabledstr = 'disabled="disabled"';
        }
        if("0" == sourcetype){
            tdstr = '<input type="text" id="dataview-paramrule-addDateview_'+indexTab+'" name="paramrule" class="dataview-paramrule" style="width: 150px"  disabled="disabled"/>';
        }else{
            tdstr='<select id="dataview-paramrule-addDateview_'+indexTab+'" name="paramrule" class="dataview-paramrule" style="width:150px;">';
            tdstr +='<option value="0"></option>';
            tdstr +='<option value="1">等于</option>';
            tdstr +='<option value="2">不等于</option>';
            tdstr +='</select>';
        }
        $(tdstr).appendTo("#dataview-paramrule-td_"+indexTab);
    }
</script>
</body>
</html>
