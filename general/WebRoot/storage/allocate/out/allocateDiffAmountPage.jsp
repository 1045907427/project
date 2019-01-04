<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>调拨差额报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="report-toolbar-allocateDiffAmountPage" style="padding: 0px">
        <div class="buttonBG">
            <security:authorize url="/storage/exportAllocateDiffAmountData.do">
                <a href="javaScript:void(0);" id="report-export-allocateDiffAmountPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
            </security:authorize>
            <div id="dialog-autoexport"></div>
        </div>
        <form action="" id="report-query-form-allocateDiffAmountPage" method="post">
            <table class="querytable">

                <tr>
                    <td>业务日期:</td>
                    <td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                    <td>商&nbsp;&nbsp;品:</td>
                    <td><input type="text" name="goodsid" id="report-goodsid-advancedQuery" style="width: 210px;"/>
                    <td>仓库:</td>
                    <td><input type="text" name="storageid" id="report-storageid-advancedQuery"/></td>
                </tr>
                <tr>
                    <td>部门:</td>
                    <td><input type="text" name="deptid" id="report-deptid-advancedQuery" />
                    <td>小计列：</td>
                    <td>
                        <input type="checkbox" class="groupcols checkbox1" value="goodsid" id="report-goodsid-checkbox-advancedQuery"/>
                        <label for="report-goodsid-checkbox-advancedQuery" class="divtext">商品</label>
                        <input type="checkbox" class="groupcols checkbox1" value="storageid" id="report-storageid-checkbox-advancedQuery"/>
                        <label for="report-storageid-checkbox-advancedQuery" class="divtext">仓库</label>
                        <input type="checkbox" class="groupcols checkbox1" value="deptid" id="report-deptid-checkbox-advancedQuery"/>
                        <label for="report-deptid-checkbox-advancedQuery" class="divtext">部门</label>
                        <input id="report-query-groupcols" type="hidden" name="groupcols"/>
                    </td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="report-queay-allocateDiffAmountPage" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="report-reload-allocateDiffAmountPage" class="button-qr">重置</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <table id="report-datagrid-allocateDiffAmountPage"></table>
</div>






<script type="text/javascript">
    var SD_footerobject  = null;
    var initQueryJSON = $("#report-query-form-allocateDiffAmountPage").serializeJSON();
    $(function(){
        $("#report-export-allocateDiffAmountPage").click(function(){
            var queryJSON = $("#report-query-form-allocateDiffAmountPage").serializeJSON();
            //获取排序规则
            var objecr  = $("#report-datagrid-allocateDiffAmountPage").datagrid("options");
            if(null != objecr.sortName && null != objecr.sortOrder ){
                queryJSON["sort"] = objecr.sortName;
                queryJSON["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryJSON);
            var url = "storage/exportAllocateDiffAmountData.do";
            exportByAnalyse(queryParam,"调拨差额导出","report-datagrid-allocateDiffAmountPage",url);
        });


        $(".groupcols").click(function(){
            var cols = "";
            $(".groupcols").each(function(){
                if($(this).attr("checked")){
                    if(cols==""){
                        cols = $(this).val();
                    }else{
                        cols += ","+$(this).val();
                    }
                    $("#report-query-groupcols").val(cols);
                }else{
                    $("#report-query-groupcols").val(cols);
                }
            });
        });
        var tableColumnListJson = $("#report-datagrid-allocateDiffAmountPage").createGridColumnLoad({
            frozenCol : [[
                {field:'idok',checkbox:true,isShow:true}
            ]],
            commonCol : [[
                {field:'goodsid',title:'商品编码',sortable:true,width:60},
                {field:'goodsname',title:'商品名称',width:130},
                {field:'storageid',title:'仓库编码',sortable:true,width:60},
                {field:'storagename',title:'仓库名称',sortable:true,width:80},
                {field:'deptid',title:'部门编码',sortable:true,width:80},
                {field:'deptname',title:'部门名称',sortable:true,width:80},
                {field:'taxamount',title:'调拨金额',sortable:true,width:80,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'notaxamount',title:'调拨未税金额',sortable:true,width:80,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'costamount',title:'成本金额',sortable:true,width:80,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'diffamount',title:'调拨差额',sortable:true,width:80,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                }
            ]]
        });
        
     
        $("#report-goodsid-advancedQuery").widget({
            referwid:'RL_T_BASE_GOODS_INFO',
            singleSelect:false,
            width:150,
            onlyLeafCheck:true
        });
      

        //部门
        $("#report-deptid-advancedQuery").widget({
            referwid:'RT_T_SYS_DEPT',
            singleSelect:false,
            width:'220',
            onlyLeafCheck:true
        });
        
    
        //销售部门
        $("#report-storageid-advancedQuery").widget({
            referwid:'RL_T_BASE_STORAGE_INFO',
            width:120,
            singleSelect:false,
            onlyLeafCheck:true
        });
      
        
        $("#report-datagrid-allocateDiffAmountPage").datagrid({
            authority:tableColumnListJson,
            frozenColumns: tableColumnListJson.frozen,
            columns:tableColumnListJson.common,
            method:'post',
            title:'',
            fit:true,
            rownumbers:true,
            pagination:true,
            showFooter: true,
//            pageSize:100,
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            toolbar:'#report-toolbar-allocateDiffAmountPage',
            url:'storage/showAllocateDiffAmountData.do',
            queryParams:initQueryJSON,
            onLoadSuccess:function(){
                var footerrows = $(this).datagrid('getFooterRows');
                if(null!=footerrows && footerrows.length>0){
                    SD_footerobject = footerrows[0];
                }
                countTotalAmount();
            },
            onCheckAll:function(){
                countTotalAmount();
            },
            onUncheckAll:function(){
                countTotalAmount();
            },
            onCheck:function(){
                countTotalAmount();
            },
            onUncheck:function(){
                countTotalAmount();
            }
        }).datagrid("columnMoving");

        //查询
        $("#report-queay-allocateDiffAmountPage").click(function(){
            setColumn();
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#report-query-form-allocateDiffAmountPage").serializeJSON();
            $("#report-datagrid-allocateDiffAmountPage").datagrid("load",queryJSON);
        });
        //重置
        $("#report-reload-allocateDiffAmountPage").click(function(){
            $(".groupcols").each(function(){
                if($(this).attr("checked")){
                    $(this)[0].checked = false;
                }
            });
            $("#report-query-groupcols").val("");
            
            $("#report-goodsid-advancedQuery").widget("clear");
            $("#report-storageid-advancedQuery").widget("clear");
            $("#report-deptid-advancedQuery").widget("clear");
            
            $("#report-query-form-allocateDiffAmountPage").form("reset");
            $('#report-datagrid-allocateDiffAmountPage').datagrid('loadData',{total:0,rows:[]});
            $('#report-datagrid-allocateDiffAmountPage').datagrid('reloadFooter',[]);
        });

    });
    var $datagrid = $("#report-datagrid-allocateDiffAmountPage");
    function setColumn(){
        var cols = $("#report-query-groupcols").val();
        if(cols!=""){
            $datagrid.datagrid('hideColumn', "deptid");
            $datagrid.datagrid('hideColumn', "deptname");
            $datagrid.datagrid('hideColumn', "goodsid");
            $datagrid.datagrid('hideColumn', "goodsname");
            $datagrid.datagrid('hideColumn', "storageid");
            $datagrid.datagrid('hideColumn', "storagename");
        }else{
            $datagrid.datagrid('showColumn', "deptid");
            $datagrid.datagrid('showColumn', "deptname");
            $datagrid.datagrid('showColumn', "goodsid");
            $datagrid.datagrid('showColumn', "goodsname");
            $datagrid.datagrid('showColumn', "storageid");
            $datagrid.datagrid('showColumn', "storagename");
        }

        var colarr = cols.split(",");
        for(var i=0;i<colarr.length;i++){
            var col = colarr[i];
            if(col=='goodsid'){
                $datagrid.datagrid('showColumn', "goodsid");
                $datagrid.datagrid('showColumn', "goodsname");
            }else if(col=="storageid"){
                $datagrid.datagrid('showColumn', "storageid");
                $datagrid.datagrid('showColumn', "storagename");
            }else if(col=="deptid"){
                $datagrid.datagrid('showColumn', "deptid");
                $datagrid.datagrid('showColumn', "deptname");
            }
        }
    }
    function countTotalAmount(){
        var rows =  $("#report-datagrid-allocateDiffAmountPage").datagrid('getChecked');
//        if(null==rows || rows.length==0){
//            var foot=[];
//            if(null!=SD_footerobject){
//                foot.push(SD_footerobject);
//            }
//            $("#report-datagrid-allocateDiffAmountPage").datagrid("reloadFooter",foot);
//            return false;
//        }
        var taxamount = 0;
        var notaxamount=0;
        var costamount = 0;
        var diffamount = 0;

        for(var i=0;i<rows.length;i++){
            taxamount = Number(taxamount)+Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
            notaxamount = Number(notaxamount)+Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
            costamount = Number(costamount)+Number(rows[i].costamount == undefined ? 0 : rows[i].costamount);
            diffamount = Number(diffamount)+Number(rows[i].diffamount == undefined ? 0 : rows[i].diffamount);
        }
        var foot=[{goosid:'选中合计',storageid:'',deptid:'',taxamount:taxamount,notaxamount:notaxamount,costamount:costamount,diffamount:diffamount}];
        if(null!=SD_footerobject){
            foot.push(SD_footerobject);
        }
        var groupcols=$("#report-query-groupcols").val();
        if(groupcols.indexOf("goodsid")>-1||groupcols==''){
            foot[0]['goodsid']="选中合计";
        }else if(groupcols.indexOf("storageid")>-1){
            foot[0]['storageid']="选中合计";
        }else if(groupcols.indexOf("deptid")>-1){
            foot[0]['deptid']="选中合计";
        }
        $("#report-datagrid-allocateDiffAmountPage").datagrid("reloadFooter",foot);
    }

</script>
</body>
</html>
