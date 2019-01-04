<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>要货单列表</title>
    <%@include file="/include.jsp" %>

</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:true">
    <input type="hidden" id="personnelid" value="${personnelid}"/>
    <div data-options="region:'center',border:false">
        <table id="sales-datagrid-demandViewPage"></table>
    </div>
    <div data-options="region:'south'" >
        <div class="buttonDetailBG" style="text-align:right;">
            <input type="button" name="savegoon" id="sales-close-demandViewPage" value="关闭"/>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var wareListJson = [[{field:'goodsid',title:'商品编码',width:70,align:' left'},
        {field:'goodsname', title:'商品名称', width:220,align:'left',aliascol:'goodsid',
            formatter:function(value,rowData,rowIndex){
                if(rowData.goodsInfo != null){
                    if(rowData.deliverytype=='1'){
                        return "<font color='blue'>&nbsp;赠 </font>"+rowData.goodsInfo.name;
                    }else if(rowData.deliverytype=='2'){
                        return "<font color='blue'>&nbsp;捆绑 </font>"+rowData.goodsInfo.name;
                    }else{
                        return rowData.goodsInfo.name;
                    }
                }else{
                    return "";
                }
            }
        },
        {field:'barcode', title:'条形码',width:90,align:'left',aliascol:'goodsid',
            formatter:function(value,rowData,rowIndex){
                if(rowData.goodsInfo != null){
                    return rowData.goodsInfo.barcode;
                }else{
                    return "";
                }
            }
        },
        {field:'brandName', title:'商品品牌',width:80,align:'left',aliascol:'goodsid',hidden:true,
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
                if(rowData.goodsInfo != null){
                    return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
                }else{
                    return "";
                }
            }
        },
        {field:'unitname', title:'单位',width:35,align:'left'},
        {field:'unitnum', title:'数量',width:60,align:'right',
            formatter:function(value,row,index){
                return formatterBigNumNoLen(value);
            }
        },
        {field:'taxprice', title:'单价',width:60,align:'right',
            formatter:function(value,row,index){
                if(parseFloat(value)>parseFloat(row.oldprice)){
                    return "<font color='blue'>"+ formatterMoney(value)+ "</font>";
                }
                else if(parseFloat(value)<parseFloat(row.oldprice)){
                    return "<font color='red'>"+ formatterMoney(value)+ "</font>";
                }
                else{
                    return formatterMoney(value);
                }
            }
        },
        {field:'boxprice', title:'箱价',width:60,aliascol:'taxprice',align:'right',
            formatter:function(value,row,index){
                if(parseFloat(row.taxprice)>parseFloat(row.oldprice)){
                    return "<font color='blue'>"+ formatterMoney(value)+ "</font>";
                }
                else if(parseFloat(row.taxprice)<parseFloat(row.oldprice)){
                    return "<font color='red'>"+ formatterMoney(value)+ "</font>";
                }
                else{
                    return formatterMoney(value);
                }
            }
        },
        {field:'taxamount', title:'金额',width:60,align:'right',
            formatter:function(value,row,index){
                return formatterMoney(value);
            }
        },
        {field:'taxtype', title:'税种',width:70,align:'left',hidden:true,
            formatter:function(value,row,index){
                return row.taxtypename;
            }
        },
        {field:'notaxprice', title:'未税单价',width:80,align:'right', hidden:true,
            formatter:function(value,row,index){
                return formatterMoney(value);
            }
        },
        {field:'notaxamount', title:'未税金额',width:90,align:'right', hidden:true,
            formatter:function(value,row,index){
                return formatterMoney(value);
            }
        },
        {field:'tax', title:'税额',width:80,align:'right',hidden:true,
            formatter:function(value,row,index){
                return formatterMoney(value);
            }
        },
        {field:'auxnumdetail', title:'辅数量',width:60,align:'right'},
        {field:'deliverydate', title:'交货日期',width:80,align:'left'},
        {field:'remark', title:'备注',width:100,align:'left'}
    ]]
    $(function() {
        $("#sales-customer-demandViewPage").customerWidget({ //客户参照窗口
            name:'t_sales_demand',
            col:'customerid',
            singleSelect:true,
            width:300
        })
        //关闭
        $("#sales-close-demandViewPage").click(function(){
            $("#sales-dialog-demandPersonnelDataPage1").dialog('close');
//            $("#sales-dialog-demangImagePage1").dialog('open');
        });
        $("#sales-datagrid-demandViewPage").datagrid({ //销售商品行编辑
            columns: wareListJson,
            border: true,
            fit:true,
            rownumbers: true,
            showFooter: true,
            striped:true,
            singleSelect: true,
            data: JSON.parse('${goodsJson}'),
            onLoadSuccess: function(){
                var rows = $("#sales-datagrid-demandViewPage").datagrid('getRows');
                var leng = rows.length;
                if(leng < 12){
                    for(var i=leng; i<12; i++){
                        $("#sales-datagrid-demandViewPage").datagrid('appendRow',{});
                    }
                }
                groupGoods();
                countTotal();
            }
        }).datagrid('columnMoving');
    })
    //买赠捆绑分组
    function groupGoods(){
        var rows = $("#sales-datagrid-demandViewPage").datagrid("getRows");
        var merges = [];
        var groupIDs = "";
        for(var i=0;i<rows.length;i++){
            var groupid = rows[i].groupid;
            if(groupid!=null && groupid!="" && groupIDs.indexOf(groupid)==-1){
                groupIDs = groupid+",";
                var count = 0;
                for(var j=0;j<rows.length;j++){
                    if(groupid == rows[j].groupid){
                        count ++;
                    }
                }
                if(count>1){
                    merges.push({index:i,rowspan:count});
                }
            }
        }
        for(var i=0; i<merges.length; i++){
            $("#sales-datagrid-demandViewPage").datagrid('mergeCells',{
                index: merges[i].index,
                field: 'ck',
                rowspan: merges[i].rowspan
            });
        }
    }
    function countTotal(){ //计算合计
        var rows = $("#sales-datagrid-demandViewPage").datagrid('getRows');
        var leng = rows.length;
        var unitnum = 0;
        var taxamount = 0;
        var notaxamount = 0;
        var tax = 0;
        for(var i=0; i<leng; i++){
            unitnum += Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
            taxamount += Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
            notaxamount += Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
            tax += Number(rows[i].tax == undefined ? 0 : rows[i].tax);
        }
        $("#sales-datagrid-demandViewPage").datagrid('reloadFooter',[{goodsid:'合计', unitnum: unitnum, taxamount: taxamount, notaxamount: notaxamount, tax: tax}]);
    }
</script>
</body>
</html>
