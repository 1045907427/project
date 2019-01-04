<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>厂家数据抓取配置参数</title>
    <%@include file="/include.jsp" %>
</head>

<body>
<table id="datapurchase-table"></table>
<div id="datapurchase-toolbar" style="padding:0px;height:auto">
    <div class="buttonBG">
        <security:authorize url="/manufacturerdata/showDataPurchaseAddPage.do">
            <a href="javaScript:void(0);" id="datapurchase-button-add" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">分配数据</a>
        </security:authorize>
        <security:authorize url="/manufacturerdata/deleteDataPurchase.do">
            <a href="javaScript:void(0);" id="datapurchase-button-delete" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-delete'">删除数据</a>
        </security:authorize>
    </div>
    <div>
        <form action="" method="post" id="datapurchase-form">
            <table class="querytable">
                <tr>
                    <td>业务日期:</td>
                    <td class="tdinput"><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',minDate:'${initdate }'})" value="${initdate}"/> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
                    <td>对接方:</td>
                    <td><input type="text" id="datapurchase-markid" name="markid" style="width:150px"/></td>
                    <td>单据类型:</td>
                    <td>
                        <select  name="billtype" style="width:150px;">
                            <option value="" selected="selected"></option>
                            <option value="1">采购入库</option>
                            <option value="2">采购退货</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>商品名称:</td>
                    <td><input type="text" id="datapurchase-goodsid" name="goodsid" style="width:220px"/></td>
                    <td>供应商:</td>
                    <td><input type="text" id="datapurchase-supplierid" name="supplierid" style="width:150px"/></td>
                    <td>品牌名称:</td>
                    <td><input type="text" id="datapurchase-brandid" name="brandid" style="width:150px"/></td>
                </tr>
                <tr>
                    <td>单据编号:</td>
                    <td><input type="text" id="datapurchase-billid" name="billid" style="width:220px"/></td>
                    <td colspan="4"></td>
                </tr>
                <tr>
                    <td>小计列:</td>
                    <td colspan="3">
                        <input id="datapurchase-billid-checkbox" type="checkbox" class="groupcols checkbox1" checked="checked" name="groupcols" value="billid" id="billid"/>
                        <label class="divtext" for="billid">单据编号</label>
                        <input type="checkbox" class="groupcols checkbox1" name="groupcols" value="markid" checked="checked" id="markid"/>
                        <label class="divtext" for="markid">对接方</label>
                        <input type="checkbox" class="groupcols checkbox1" name="groupcols" value="goodsid" checked="checked" id="goodsid"/>
                        <label class="divtext" for="goodsid">商品</label>
                        <input type="checkbox" class="groupcols checkbox1" name="groupcols" value="brandid" id="brandid"/>
                        <label class="divtext" for="brandid">品牌</label>
                        <input type="checkbox" class="groupcols checkbox1" name="groupcols" value="supplierid" id="supplierid"/>
                        <label class="divtext" for="supplierid">供应商</label>
                        <input id="datapurchase-storageid-checkbox" type="checkbox" class="groupcols checkbox1" name="groupcols" value="storageid" id="storageid"/>
                        <label class="divtext" for="storageid">虚拟仓库</label>
                    </td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="datapurchase-queay-queryList" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="datapurchase-queay-reloadList" class="button-qr">重置</a>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="datapurchase-dataDetail" name="detailJson" />
        </form>
    </div>
</div>
<div id="datapurchase-dialog"></div>
<div class="easyui-menu" id="datapurchase-contextMenu" style="display: none;">
    <div id="datapurchase-addRow" data-options="iconCls:'button-add'">添加</div>
    <div id="datapurchase-editRow" data-options="iconCls:'button-edit'">编辑</div>
    <div id="datapurchase-removeRow" data-options="iconCls:'button-delete'">删除</div>
</div>
</body>
<script type="text/javascript">
    var initQueryJSON = $("#datapurchase-form").serializeJSON();
    var loadData = false;
    var SMR_footerobject = null;
    var manufacturerdataJson = $("#datapurchase-table").createGridColumnLoad({
        name :'',
        frozenCol : [[
            {field:'ck',checkbox:true,isShow:true}
        ]],
        commonCol : [[
            {field:'businessdate',title:'业务日期',width:100,sortable:true},
            {field:'markid',title:'标识',width:80,sortable:true,hidden:true},
            {field:'markname',title:'对接方名称',width:80,sortable:true},
            {field:'billid',title:'单据编号',width:150,sortable:true},
            {field:'detailid',title:'单据明细编号',width:80,sortable:true},
            {field:'billtype',title:'单据类型',width:90,sortable:true,
                formatter: function(dateValue,row,index){
                    if(dateValue=='1'){
                        return "采购入库";
                    }else if(dateValue=='2'){
                        return "采购退货";
                    }
                }
            },
            {field:'goodsid',title:'商品编号',width:80,sortable:true},
            {field:'goodsname',title:'商品名称',width:200,sortable:true},
            {field:'supplierid',title:'供应商编号',width:160,sortable:true,hidden:true},
            {field:'suppliername',title:'供应商名称',width:200,sortable:true},
            {field:'deptid',title:'部门编号',width:120,sortable:true,hidden:true},
            {field:'deptname',title:'部门名称',width:80,sortable:true},
            {field:'storageid',title:'仓库编号',width:120,sortable:true,hidden:true},
            {field:'storagename',title:'仓库名称',width:60,sortable:true},
            {field:'brandid',title:'品牌编号',width:120,sortable:true,hidden:true},
            {field:'brandname',title:'品牌名称',width:80,sortable:true},
            {field:'taxprice',title:'单价',width:100,align:'right',sortable:true},
            {field:'unitnum',title:'数量',width:80,align:'right',sortable:true},
            {field:'taxamount',title:'金额',width:100,align:'right',sortable:true},
            {field:'batchno',title:'批次号',width:120,sortable:true,hidden:true},
            {field:'producedate',title:'生产日期',width:120,sortable:true,hidden:true},
            {field:'isupload',title:'是否已上报',width:90,sortable:true,
                formatter: function(dateValue,row,index){
                    if(dateValue=='0'){
                        return "否";
                    }else if(dateValue=='1'){
                        return "是";
                    }
                }
            },
            {field:'remark',title:'备注',width:120,sortable:true},
        ]]
    });


    $(function() {
        $("#datapurchase-table").datagrid({
            authority: manufacturerdataJson,
            frozenColumns: manufacturerdataJson.frozen,
            columns: manufacturerdataJson.common,
            sortName: 'businessdate',
            sortOrder: 'asc',
            fit:true,
            rownumbers:true,
            pagination: true,
            pageSize:100,
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            showFooter:true,
            url: 'manufacturerdata/showDataPurchaseList.do',
            queryParams: initQueryJSON,
            toolbar: '#datapurchase-toolbar',
            onBeforeLoad:function(){
                $(this).datagrid('clearChecked');
                $(this).datagrid('clearSelections');
                return loadData;
            },
            onLoadSuccess:function(){
                var footerrows = $(this).datagrid('getFooterRows');
                if(null!=footerrows && footerrows.length>0){
                    SMR_footerobject = footerrows[0];
                    checkTotalAmount();
                }
            },
            onCheckAll:function(){
                checkTotalAmount();
            },
            onUncheckAll:function(){
                checkTotalAmount();
            },
            onCheck:function(){
                checkTotalAmount();
            },
            onUncheck:function(){
                checkTotalAmount();
            }
        }).datagrid('columnMoving');

        $("#datapurchase-button-add").click(function () {
            var url = "manufacturerdata/showDataPurchaseAddPage.do";
            top.addTab(url, '分配采购明细数据');
        });

        $("#datapurchase-button-delete").click(function () {
            var flag = false;
            var groupCol = "";
            $(".groupcols").each(function(){
                if($(this).attr("checked")){
                    var val = $(this).val();
                    groupCol += val+",";
                }
            });
            if(groupCol.indexOf("goodsid")<0){
                $.messager.alert("提醒","小计列未选择单据编号，不能删除");
                return false;
            }
            var rowArr = $("#datapurchase-table").datagrid("getChecked");
            var list = new Array();
            var indexlist = new Array();
            var insertFlag=true;
            if(rowArr.length==0){
                $.messager.alert("提醒","请选择数据");
                return false;
            }
            for(var i=0;i<rowArr.length;i++){
                if(indexlist.length>0){
                    for(var j=0;j<indexlist.length;j++){
                        if(rowArr[i].billid==indexlist[j]){
                            insertFlag=false;
                        }
                    }
                }
                console.log(indexlist[1])
                if(insertFlag){
                    indexlist[indexlist.length]= rowArr[i].billid;
                    var order = new Object();
                    order.billid =rowArr[i].billid;
                    order.billtype = rowArr[i].billtype;
                    order.markid = rowArr[i].markid;
                    list[list.length] = order;
                }
                insertFlag=true;
            }
            var orders=JSON.stringify(list);
            $.messager.confirm("提醒","确定删除选中数据相关单据编号下的所有明细？",function(r){
                if(r){
                    loading("数据处理中..");
                    $.ajax({
                        url:'manufacturerdata/deleteDataPurchase.do',
                        dataType:'json',
                        type:'post',
                        data:{orders:orders},
                        success:function(json){
                            loaded();
                            if(null!=json.msg){
                                $.messager.alert("提醒","删除完成。"+json.msg);
                            }
                            else{
                                $.messager.alert("提醒","删除完成。");
                            }
                            refresh();
                        },
                        error:function(){
                            loaded();
                            $.messager.alert("提醒","删除失败。");
                        }
                    });
                }
            });
        });


        //查询
        $("#datapurchase-queay-queryList").click(function(){
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#datapurchase-form").serializeJSON();
            $("#datapurchase-table").datagrid("load",queryJSON);
            reloadColumn();
        });
        //重置
        $("#datapurchase-queay-reloadList").click(function(){
            $("#datapurchase-goodsid").widget("clear");
            $("#datapurchase-markid").widget("clear");
            $("#datapurchase-supplierid").widget("clear");
            $("#datapurchase-brandid").widget("clear");
            $("#datapurchase-form")[0].reset();
            var queryJSON = $("#datapurchase-form").serializeJSON();
            $("#datapurchase-table").datagrid("load",queryJSON);
        });

        $("#datapurchase-goodsid").widget({
            referwid:'RL_T_BASE_GOODS_INFO',
            width:220,
            singleSelect:true,
            onlyLeafCheck:true,
        });
        $("#datapurchase-brandid").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            width:150,
            singleSelect:true,
            onlyLeafCheck:true,
        });
        $("#datapurchase-supplierid").widget({
            referwid:'RL_T_BASE_BUY_SUPPLIER',
            width:150,
            singleSelect:true,
            onlyLeafCheck:true,
        });
        $("#datapurchase-storageid").widget({
            referwid:'RL_T_BASE_STORAGE_INFO',
            width:150,
            singleSelect:true,
            onlyLeafCheck:true,
        });
        $("#datapurchase-markid").widget({
            referwid:'RL_T_BASE_DATACONFIG_INFO',
            width:150,
            singleSelect:true,
            onlyLeafCheck:true,
        });
        //计算选中合计
        function checkTotalAmount(){
            var rows =  $("#datapurchase-table").datagrid('getChecked');
            var unitnum = 0,taxamount = 0;
            for(var i=0;i<rows.length;i++){
                unitnum = Number(unitnum)+Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
                taxamount = Number(taxamount)+Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);

            }

            var foot=[{businessdate:'选中合计',unitnum:unitnum,taxamount:formatterMoney(taxamount) }];
            if(null!=SMR_footerobject){
                foot.push(SMR_footerobject);
            }
            $("#datapurchase-table").datagrid("reloadFooter",foot);
        }
        //重置字段列
        function reloadColumn(){
            var flag = false;
            var groupCol = "";
            $(".groupcols").each(function(){
                if($(this).attr("checked")){
                    var val = $(this).val();
                    groupCol += val+",";
                }
            });
            if(groupCol.indexOf("markid")>=0){
                $("#datapurchase-table").datagrid('showColumn', "markname");
            }else{
                $("#datapurchase-table").datagrid('hideColumn', "markname");
            }
            if(groupCol.indexOf("brandid")>=0){
                $("#datapurchase-table").datagrid('showColumn', "brandname");
            }else{
                $("#datapurchase-table").datagrid('hideColumn', "brandname");
            }

            if(groupCol.indexOf("goodsid")>=0){
                $("#datapurchase-table").datagrid('showColumn', "businessdate");
                $("#datapurchase-table").datagrid('showColumn', "detailid");
                $("#datapurchase-table").datagrid('showColumn', "billtype");
                $("#datapurchase-table").datagrid('showColumn', "goodsid");
                $("#datapurchase-table").datagrid('showColumn', "goodsname");
                $("#datapurchase-table").datagrid('showColumn', "deptname");
                $("#datapurchase-table").datagrid('showColumn', "taxprice");
                $("#datapurchase-table").datagrid('showColumn', "remark");
            }else{
                $("#datapurchase-table").datagrid('hideColumn', "businessdate");
                $("#datapurchase-table").datagrid('hideColumn', "detailid");
                $("#datapurchase-table").datagrid('hideColumn', "billtype");
                $("#datapurchase-table").datagrid('hideColumn', "goodsid");
                $("#datapurchase-table").datagrid('hideColumn', "goodsname");
                $("#datapurchase-table").datagrid('hideColumn', "deptname");
                $("#datapurchase-table").datagrid('hideColumn', "taxprice");
                $("#datapurchase-table").datagrid('hideColumn', "remark");

                if(groupCol.indexOf("supplierid")>=0){
                    $("#datapurchase-table").datagrid('showColumn', "suppliername");
                }else{
                    $("#datapurchase-table").datagrid('hideColumn', "suppliername");
                }
            }
            if(groupCol.indexOf("storageid")>=0){
                $("#datapurchase-table").datagrid('showColumn', "storagename");
            }else{
                $("#datapurchase-table").datagrid('hideColumn', "storagename");
            }
            if(groupCol.indexOf("billid")>=0){
                $("#datapurchase-table").datagrid('showColumn', "billid");
            }else{
                $("#datapurchase-table").datagrid('hideColumn', "billid");
            }
        }

        setTimeout(function(){
            loadData = true;
        },100);
    })

    function refresh(){
        var queryJSON = $("#datapurchase-form").serializeJSON();
        $("#datapurchase-table").datagrid("load",queryJSON);
    }
</script>
</html>

