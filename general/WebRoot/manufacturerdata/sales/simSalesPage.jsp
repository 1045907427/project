<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>操作</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div id="simSales-layout-simSalesDataPage" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
        <div class="buttonBG" id="simSales-buttons-simSalesDataPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="simSales-panel-simSalesDataPage"></div>
    </div>
</div>
<script type="text/javascript">
    //明细列表
    var tableColJson = $("#simSales-datagrid-simSalesDataPage").createGridColumnLoad({
        frozenCol : [[]],
        commonCol : [[
            {field:'ck',checkbox:true},
            {field:'goodsid',title:'商品编号',width:80
            },
            {field:'goodsname', title:'商品名称', width:250,aliascol:'goodsid',
                formatter:function(value,rowData,rowIndex){
                    if(rowData.goodsInfo != null){
                        return rowData.goodsInfo.name;
                    }else{
                        return "";
                    }
                }
            },

            {field:'brandname', title:'商品品牌', width:70,aliascol:'goodsid',
                formatter:function(value,rowData,rowIndex){
                    if(rowData.goodsInfo != null){
                        return rowData.goodsInfo.brandName;
                    }else{
                        return "";
                    }
                }
            },
            {field:'barcode',title:'条形码',width:100,
                formatter:function(value,rowData,rowIndex){
                    if(rowData.goodsInfo != null){
                        return rowData.goodsInfo.barcode;
                    }else{
                        return "";
                    }
                }
            },
            {field:'boxnum',title:'箱装量',width:50,
                formatter:function(value,rowData,rowIndex){
                    if(rowData.goodsInfo != null){
                        return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
                    }else{
                        return "";
                    }
                }
            },
            {field:'unitid', title:'主计量单位',width:100,hidden:true
            },
            {field:'unitnum', title:'数量',width:50,align:'right',
                formatter:function(value,row,index){
                    return formatterBigNumNoLen(value);
                }
            },
            {field:'unitname', title:'单位',width:40,aliascol:'goodsid',
                formatter:function(value,rowData,rowIndex){
                    if(rowData.goodsInfo != null){
                        return rowData.goodsInfo.mainunitName;
                    }else{
                        return "";
                    }
                }
            },


            {field:'auxunitid', title:'辅计量单位编号',width:45,aliascol:'goodsid',hidden:true,
                formatter:function(value,rowData,rowIndex){
                    if(rowData.goodsInfo != null){
                        return rowData.goodsInfo.auxunitid;
                    }else{
                        return "";
                    }
                }
            },
            {field:'auxunitname',title:'辅单位名称',width:80,hidden:true,},
            {field:'auxnum',title:'辅单位数量',width:80,align:'right',hidden:true,
                formatter:function(value,row,index){
                    return formatterBigNum(value);
                }
            },
            {field:'auxnumdetail',title:'辅数量',width:60,align:'right'},
            {field:'overnum', title:'主单位余数',width:100,hidden:true,
                formatter:function(value,row,index){
                    return formatterBigNumNoLen(value);
                }
            },
            {field:'price', title:'单价',width:50,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'taxamount', title:'金额',width:80,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'remark', title:'备注',width:100},
            {field:'seq', title:'序号',width:100,hidden:true}

        ]]
    });//明细列表至此
    var page_url = "manufacturerdata/showSimSalesDataAddPage.do";
    var page_type = '${type}';
    if(page_type == "edit"){
        page_url = "manufacturerdata/showSimSalesDataEditPage.do?billid=${billid}";
    }
    $(function(){
        $("#simSales-panel-simSalesDataPage").panel({
            href:page_url,
            cache:false,
            maximized:true,
            border:false,
            onLoad:function(){
//                if(page_type == "edit"){
//                    $("#simSales-simSalesAogorder-storageid").focus();
//                }
//                else{
//                    $("#simSales-simSalesAogorder-supplierid").focus();
//                }
            }
        });
        $("#simSales-buttons-simSalesDataPage").buttonWidget({
            initButton:[
                {},
                <security:authorize url="/manufacturerdata/addSimDataSales.do">
                {
                    type: 'button-save',
                    handler: function(){
                        //验证表单
                        var flag = $("#simSales-form-simSalesDataPage").form('validate');
                        if(flag==false){
                            $.messager.alert("提醒",'请先完善基本信息');
                            return false;
                        }
                        var outmarkid=$("#simSales-outmarkid-simSalesDataAddPage").widget("getValue");
                        var inmarkid=$("#simSales-inmarkid-simSalesDataAddPage").widget("getValue");
                        if(outmarkid==inmarkid){
                            $.messager.alert("提醒",'出库对接方和入库对接方不允许相同');
                            return false;
                        }
                        $.messager.confirm("提醒","确定保存该信息？",function(r){
                            if(r){
                                var type = $("#simSales-simSalesData-type").val();
                                if(type=="add"){
                                    //暂存
                                    $("#simSales-form-simSalesDataPage").attr("action", "manufacturerdata/addSimDataSales.do");
                                    $("#simSales-form-simSalesDataPage").submit();
                                }else if(type=="edit"){
                                    $("#simSales-form-simSalesDataPage").attr("action", "manufacturerdata/editSimDataSales.do");
                                    $("#simSales-form-simSalesDataPage").submit();
                                }
                            }
                        });
                    }
                },
                </security:authorize>

            ],
            layoutid:'simSales-layout-simSalesDataPage',
            model: 'bill',
            type: 'view',
            tab:'模拟销售',
            id:'${id}',
            datagrid:'simSales-datagrid-simSalesDataPage'
        });
    });
    //显示明细添加页面
    function beginAddDetail(){
        //验证表单
        var flag = $("#simSales-form-simSalesDataPage").form('validate');
        if(flag==false){
            $.messager.alert("提醒",'请先完善基本信息');
            return false;
        }
        var supplierid = $("#simSales-simSalesAogorder-supplierid").widget("getValue");
        var storageid = $("#simSales-simSalesAogorder-storageid").widget("getValue");
        $('<div id="simSales-dialog-simSalesDataPage-content"></div>').appendTo('#simSales-dialog-simSalesDataPage');
        $('#simSales-dialog-simSalesDataPage-content').dialog({
            title: '明细添加',
            width: 680,
            height: 320,
            collapsible:false,
            minimizable:false,
            maximizable:true,
            resizable:true,
            closed: true,
            cache: false,
            modal: true,
            href: 'manufacturerdata/showSimSalesDetailAddPage.do',
            onLoad:function(){
                $("#simSales-simSalesDetailAddPage-goodsid").focus();
            },
            onClose:function(){
                $('#simSales-dialog-simSalesDataPage-content').dialog("destroy");
            }
        });
        $('#simSales-dialog-simSalesDataPage-content').dialog("open");
    }
    //显示明细修改页面
    function beginEditDetail(){
        //验证表单
        var flag = $("#simSales-form-simSalesDataPage").form('validate');
        if(flag==false){
            $.messager.alert("提醒",'请先选择供应商');
            $("#simSales-simSalesAogorder-supplierid").focus();
            return false;
        }
        var row = $("#simSales-datagrid-simSalesDataPage").datagrid('getSelected');
        if(row == null){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if(row.goodsid == undefined){
            beginAddDetail();
        }else{
            $('<div id="simSales-dialog-simSalesDataPage-content"></div>').appendTo('#simSales-dialog-simSalesDataPage');
            $('#simSales-dialog-simSalesDataPage-content').dialog({
                title: '明细修改',
                width: 680,
                height: 320,
                collapsible:false,
                minimizable:false,
                maximizable:true,
                resizable:true,
                closed: true,
                cache: false,
                href: 'manufacturerdata/showSimSalesDetailEditPage.do',
                modal: true,
                onLoad:function(){
                    $("#simSales-simSalesAogorder-unitnum-aux").focus();
                    $("#simSales-simSalesAogorder-unitnum-aux").select();
                },
                onClose:function(){
                    $('#simSales-dialog-simSalesDataPage-content').dialog("destroy");
                }
            });
            $('#simSales-dialog-simSalesDataPage-content').dialog("open");
        }
    }

    //保存明细
    function addSaveDetail(goFlag){ //添加新数据确定后操作，
        var flag = $("#simSales-form-simSalesDetailAddPage").form('validate');
        if(flag==false){
            return false;
        }
        var form = $("#simSales-form-simSalesDetailAddPage").serializeJSON();
        var widgetJson = $("#simSales-simSalesDetailAddPage-goodsid").storageGoodsWidget('getObject');
        //var id=$("#simSales-simSalesAogorder-id").widget("getValue");
        var goodsInfo = {id:widgetJson.id,
            name:widgetJson.name,
            brandName:widgetJson.brandName,
            mainunitName:widgetJson.mainunitName,
            auxunitid:widgetJson.auxunitid,
            boxnum:widgetJson.boxnum,
            barcode:widgetJson.barcode};
        form.goodsInfo = goodsInfo;
        var rowIndex = 0;
        var rows = $("#simSales-datagrid-simSalesDataPage").datagrid('getRows');
        var updateFlag = false;
        for(var i=0; i<rows.length; i++){
            var rowJson = rows[i];
 			if(rowJson.goodsid==widgetJson.id){
 				rowIndex = i;
 				updateFlag = true;
 				break;
 			}
            if(rowJson.goodsid == undefined){
                rowIndex = i;
                break;
            }
        }

        if(rowIndex == rows.length - 1){
            $("#simSales-datagrid-simSalesDataPage").datagrid('appendRow',{}); //如果是最后一行则添加一新行
        }
 		if(updateFlag){
 			$.messager.alert("提醒", "此商品已经添加！");
 			return false;
 		}else{
        $("#simSales-datagrid-simSalesDataPage").datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
 		}
        if(goFlag){ //go为true确定并继续添加一条
            var url ='manufacturerdata/showSimSalesDetailAddPage.do?';
            $("#simSales-dialog-simSalesDataPage-content").dialog('refresh', url);
        }
        else{ //否则直接关闭
            $("#simSales-dialog-simSalesDataPage-content").dialog('destroy');
        }
        countTotal();
    }
    //修改保存明细
    function editSaveDetail(goFlag){
        var flag = $("#simSales-form-simSalesDetailEditPage").form('validate');
        if(flag==false){
            return false;
        }
        var form = $("#simSales-form-simSalesDetailEditPage").serializeJSON();
        var row = $("#simSales-datagrid-simSalesDataPage").datagrid('getSelected');
        var rowIndex = $("#simSales-datagrid-simSalesDataPage").datagrid('getRowIndex', row);
        form.goodsInfo = row.goodsInfo;
        $("#simSales-datagrid-simSalesDataPage").datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
        $("#simSales-dialog-simSalesDataPage-content").dialog('destroy');
        countTotal();
    }
    //删除明细
    function removeDetail(){
        var row = $("#simSales-datagrid-simSalesDataPage").datagrid('getSelected');
        if(row == null){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $.messager.confirm("提醒","确定删除该商品明细?",function(r){
            if(r){
                var rowIndex = $("#simSales-datagrid-simSalesDataPage").datagrid('getRowIndex', row);
                $("#simSales-datagrid-simSalesDataPage").datagrid('deleteRow', rowIndex);
                var rows = $("#simSales-datagrid-simSalesDataPage").datagrid('getRows');
                var index = -1;
                for(var i=0; i<rows.length; i++){
                    if(rows[i].goodsid != undefined){
                        index = i;
                        break;
                    }
                }
                if(index == -1){
                    $("#simSales-simSalesAogorder-supplierid").widget('readonly',false);
                }
            }
        });
        countTotal();
    }
    //回车跳到下一个
    var chooseNo;
    function frm_focus(val){
        chooseNo = val;
    }
    function frm_blur(val){
        if(val == chooseNo){
            chooseNo = "";
        }
    }

    $(document).keydown(function(event){//alert(event.keyCode);
        switch(event.keyCode){
            case 13: //Enter
                if(chooseNo == "simSalesAogorder.remark"){

                    beginAddDetail();
                }
                break;
            case 27: //Esc
                $("#simSales-simSalesAogorder-remark").focus();
                $("#simSales-dialog-simSalesDataPage-content").dialog('close');
                break;
            case 65: //a
                $("#button-add").click();
                break;
            case 83: //s
                if(event.ctrlKey){
                    $("#button-save").click();
                    return false;
                }
                break;

        }
    });

    $(document).bind('keydown', 'ctrl+enter',function (){
        $("#simSales-savegoon-simSalesDetailAddPage").focus();
        $("#simSales-savegoon-simSalesDetailAddPage").trigger("click");
        $("#simSales-savegoon-simSalesDetailEditPage").focus();
        $("#simSales-savegoon-simSalesDetailEditPage").trigger("click");
    });
    $(document).bind('keydown', '+',function (){
        $("#simSales-savegoon-simSalesDetailAddPage").focus();
        $("#simSales-savegoon-simSalesDetailEditPage").focus();
        setTimeout(function(){
            $("#simSales-savegoon-simSalesDetailAddPage").trigger("click");
            $("#simSales-savegoon-simSalesDetailEditPage").trigger("click");
        },300);
        return false;
    });
    function checkGoodsDetailEmpty(){
        var flag=true;
        var $table=$("#simSales-datagrid-simSalesDataPage");
        var data = $table.datagrid('getRows');
        for(var i=0;i<data.length;i++){
            if(data[i].goodsid && data[i].goodsid!=""){
                flag=false;
                break;
            }
        }
        return flag;
    }
    function countTotal(){ //计算合计
        var checkrows =  $("#simSales-datagrid-simSalesDataPage").datagrid('getChecked');
        var unitnum = 0;//合计数量
        var totalbox = 0;//合计箱数
        var taxamount = 0;//合计金额
        for(var i=0; i<checkrows.length; i++){
            unitnum += Number(checkrows[i].unitnum == undefined ? 0 : checkrows[i].unitnum);
            totalbox += Number(checkrows[i].totalbox == undefined ? 0 : checkrows[i].totalbox);
            taxamount += Number(checkrows[i].taxamount == undefined ? 0 : checkrows[i].taxamount);
        }
        totalbox = formatterMoney(totalbox);
        var foot = [{goodsid:'选中合计',unitnum:unitnum,taxamount:taxamount,auxnumdetail:totalbox+"箱"}];
        //合计
        var rows =  $("#simSales-datagrid-simSalesDataPage").datagrid('getRows');
        var unitnumSum = 0;
        var taxamountSum = 0;
        var totalboxSum = 0;
        for(var i=0; i<rows.length; i++){
            unitnumSum += Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
            taxamountSum += Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
            totalboxSum += Number(rows[i].totalbox == undefined ? 0 : rows[i].totalbox);
        }
        totalboxSum = formatterMoney(totalboxSum);
        var footSum = {goodsid:'合计',unitnum:unitnumSum,taxamount:taxamountSum,auxnumdetail:totalboxSum+"箱"};
        foot.push(footSum);
        $("#simSales-datagrid-simSalesDataPage").datagrid('reloadFooter',foot);
    }
</script>
</body>
</html>