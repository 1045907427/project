<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>异常成本报表</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<table id="report-datagrid-costAccountDetailPage"></table>
<div id="report-toolbar-costAccountDetailPage">
    <form action="" id="report-query-costAccountDetailPage" method="post">
        <input type="hidden" name="businessdate" value="${param.businessdate}"/>
        <input type="hidden" name="businessdate1" value="${param.businessdate1}"/>
        <input type="hidden" name="goodsid" value="${param.goodsid}"/>
        <input type="hidden" name="storageid" value="${param.storageid}"/>
        <input type="hidden" name="isdiffdate" value="${param.isdiffdate}"/>
        <input type="hidden" name="waverange" value="${param.waverange}"/>
        <input type="hidden" name="costprice" value="${param.costprice}"/>
        <input type="hidden" name="accounttype" value="2"/>
        <input type="hidden" name="pricetype" value="${param.pricetype}"/>
    </form>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#report-query-costAccountDetailPage").serializeJSON();
    var detailfooterobject = {goodsid:"合计"};
    $(function () {
        var ledgerReportJson = $("#report-datagrid-costAccountDetailPage").createGridColumnLoad({
            commonCol : [[
                {field:'ck',checkbox:true,isShow:true},
                {field:'sourcetype',title:'单据类型',width:100,align:'left',sortable:true},
                {field:'sourceid',title:'来源单据',width:150,align:'left',sortable:true},
                {field:'storageid',title:'仓库编码',width:80,align:'left'},
                {field:'storagename',title:'仓库名称',width:100,align:'left'},
                {field:'goodsid',title:'商品编码',width:80,align:'left',hidden:true},
                {field:'goodsname',title:'商品名称',width:200,align:'left',hidden:true},
                {field:'innum' ,title:'入库数量',width:80,align:'right',sortable:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value)
                    }
                },
                {field:'intaxamount',title:'入库金额',width:100,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value)
                    }
                },
                {field:'innotaxamount',title:'入库未税金额',width:100,align:'right',hidden:true,
                    formatter:function(value,row,index){
                       return formatterMoney(value)
                    }
                },
                {field:'outnum' ,title:'出库数量',width:100,align:'right',sortable:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value)
                    }
                },
                {field:'outtaxamount' ,title:'出库金额',width:100,align:'right',sortable:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value)
                    }
                },
                {field:'outnotaxamount' ,title:'出库未税金额',width:100,align:'right',sortable:true,hidden:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value)
                    }
                }
            ]]
        });

        $("#report-datagrid-costAccountDetailPage").datagrid({
            authority:ledgerReportJson,
            frozenColumns: ledgerReportJson.frozen,
            columns:ledgerReportJson.common,
            method:'post',
            fit:true,
            rownumbers:true,
            pageSize: 100, //页容量，必须和pageList对应起来，否则会报错
            pageNumber: 1, //默认显示第几页
            pageList: [10, 20, 30,50,100,200,500,1000],//分页中下拉选项的数值
            showFooter: true,
            pagination:true,
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            url:' storage/cost/showCostAccountDetailData.do',
            queryParams:initQueryJSON,
            toolbar:'#report-toolbar-costAccountDetailPage',
            onLoadSuccess:function(){
                var footerrows = $(this).datagrid('getFooterRows');
                if(null!=footerrows && footerrows.length>0){
                    detailfooterobject = footerrows[0];
                    detailCountTotal();
                }
            },
            onCheckAll:function(){
                detailCountTotal();
            },
            onUncheckAll:function(){
                detailCountTotal();
            },
            onCheck:function(){
                detailCountTotal();
            },
            onUncheck:function(){
                detailCountTotal();
            }
        });




    });

    function detailCountTotal() { //计算合计
        var checkrows = $("#report-datagrid-costAccountDetailPage").datagrid('getChecked');
        var innum = 0;
        var intaxamount = 0;
        var innotaxamount = 0;
        var outnum = 0;
        var outtaxamount = 0;
        var outnotaxamount = 0;
        for (var i = 0; i < checkrows.length; i++) {
            innum += Number(checkrows[i].innum == undefined ? 0 : checkrows[i].innum);
            intaxamount += Number(checkrows[i].intaxamount == undefined ? 0 : checkrows[i].intaxamount);
            innotaxamount += Number(checkrows[i].innotaxamount == undefined ? 0 : checkrows[i].innotaxamount);
            outnum += Number(checkrows[i].outnum == undefined ? 0 : checkrows[i].outnum);
            outtaxamount += Number(checkrows[i].outtaxamount == undefined ? 0 : checkrows[i].outtaxamount);
            outnotaxamount += Number(checkrows[i].outnotaxamount == undefined ? 0 : checkrows[i].outnotaxamount);
        }
        var foot = [{
            sourceid: '选中合计',
            innum: innum,
            intaxamount: intaxamount,
            innotaxamount: innotaxamount,
            outnum: outnum,
            outtaxamount: outtaxamount,
            outnotaxamount: outnotaxamount,
        }];
        foot.push(detailfooterobject);


        $("#report-datagrid-costAccountDetailPage").datagrid('reloadFooter', foot);
    }
</script>
</body>
</html>
