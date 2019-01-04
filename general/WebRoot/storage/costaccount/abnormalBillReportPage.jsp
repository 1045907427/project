<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>异常单据报表</title>
    <%@include file="/include.jsp" %>
</head>
<style>
    fieldset {
        padding: .35em .625em .75em;
        margin: 0 2px;
        border: 0px solid silver;
    }

    legend {
        padding: .5em;
        border: 0;
        width: auto;
    }
</style>
<body>
<table id="report-datagrid-abnormalBillReportPage"></table>
<div id="report-toolbar-abnormalBillReportPage">
    <div class="buttonBG">
        <%--<security:authorize url="/storage/cost/accountCostReportData.do">--%>
            <%--<a href="javaScript:void(0);" id="report-account-salesAbnormalBillReportPage" class="easyui-linkbutton" iconCls="button-audit" plain="true" title="销售成本结算">销售成本结算</a>--%>
        <%--</security:authorize>--%>
        <security:authorize url="/storage/cost/accountStorageCostReportData.do">
            <a href="javaScript:void(0);" id="report-account-storageAbnormalBillReportPage" class="easyui-linkbutton" iconCls="button-audit" plain="true" title="库存成本调整">库存成本调整</a>
        </security:authorize>
        <security:authorize url="/storage/cost/accountCostReportData.do">
            <a href="javaScript:void(0);" id="report-account-abnormalBillReportPage" class="easyui-linkbutton" iconCls="button-audit" plain="true" title="销售成本结算">销售成本结算</a>
        </security:authorize>
        <security:authorize url="/storage/cost/exportAccountCostReportData.do">
            <a href="javaScript:void(0);" id="report-export-abnormalBillReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
        </security:authorize>
    </div>
    <form action="" id="report-query-abnormalBillReportPage" method="post">
        <table>
            <tr>
                <td>业务日期:</td>
                <td>
                    <input type="text" id="report-businessdate-abnormalBillReportPage" style="width:100px;" class="Wdate"
                           onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" name="businessdate" value="${beginAccountDate}"/>
                    到<input type="text" id="report-businessdate1-abnormalBillReportPage" name="businessdate1" class="Wdate" style="width:100px;"
                            onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${endAccountDate}"/>
                </td>
                <td>品牌:</td>
                <td>
                    <input id="report-brandid-abnormalBillReportPage" name="brandid"/>
                </td>
                <td>仓库:</td>
                <td>
                    <select name="storagetype" style="width: 160px;">
                        <option select=""></option>
                        <option value="0">汇总仓库</option>
                        <option value="1">独立核算仓库</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>商品:</td>
                <td>
                    <input id="report-goodsid-abnormalBillReportPage" name="goodsid"/>
                </td>
                <td colspan="2">
                    取业务日期与审核日期不一致:
                    <select name="isdiffdate">
                        <option value="1" selected>是</option>
                        <option value="0">否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="8">
                    <fieldset>
                        <legend>销售成本异常</legend>
                        参考价格:
                        <select id="report-pricetype-abnormalBillReportPage" name="pricetype">
                            <option value="1">采购价</option>
                            <option value="2">成本价</option>
                        </select>
                        异常范围:
                        <input id="report-range-abnormalBillReportPage" validType="intOrFloat"
                               class="easyui-validatebox formaterNum" style="text-align: right;width: 40px;"
                               name="waverange"/>%
                    </fieldset>
                </td>
            </tr>
            <tr>
                <td colspan="8">
                    <fieldset>
                        <legend>库存成本价异常</legend>
                        <input class="checkbox1" type="checkbox" name="state5" value="1" checked="checked" id="report-query-state5"/>
                        参考价格:
                        <select id="report-spricetype-abnormalBillReportPage" name="spricetype">
                            <option value="1">采购价</option>
                            <option value="2">最新采购价</option>
                        </select>
                        异常范围:
                        <input id="report-srange-abnormalBillReportPage" validType="intOrFloat" class="easyui-validatebox formaterNum" style="text-align: right;width: 40px;"  name="swaverange"/>%
                        <div id="state_checkbox"  style="display: inline;float: right;">
                            <input class="checkbox1" type="checkbox" name="sstate1" value="1" id="report-query-state1"/>
                            <label for="report-query-state1" class="divtext">数量金额均为负</label>
                            <input class="checkbox1"  type="checkbox" name="sstate2" value="1" id="report-query-state2"/>
                            <label for="report-query-state2" class="divtext">数量金额相反</label>
                            <input class="checkbox1"  type="checkbox" name="sstate3" value="1" id="report-query-state3" />
                            <label for="report-query-state3" class="divtext">有数量无金额</label>
                            <input class="checkbox1" type="checkbox" name="sstate4" value="1" id="report-query-state4"/>
                            <label for="report-query-state4" class="divtext">无数量有金额</label>
                        </div>
                    </fieldset>
                </td>
                <%--<td colspan="2">--%>
                    <%--<a href="javaScript:void(0);" id="report-queay-abnormalBillReportPage" class="button-qr">查询</a>--%>
                    <%--<a href="javaScript:void(0);" id="report-reload-abnormalBillReportPage" class="button-qr">重置</a>--%>
                <%--</td>--%>
            </tr>
            <tr>
                <td colspan="8" style="text-align: center">
                    <a href="javaScript:void(0);" id="report-queay-abnormalBillReportPage" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-abnormalBillReportPage" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="abnormalBillReportPage-accountlog-dialog"></div>
<div id="abnormalBillReportPage-detaildialog-dialog"></div>
<div id="abnormalBillReportPage-accounttype-dialog"></div>
<div id="abnormalBillReportPage-storageCost-dialog"></div>
<script type="text/javascript">
    // $('#state_checkbox :checkbox[type="checkbox"]').each(function(){
    //     $(this).click(function(){
    //         if($(this).attr('checked')){
    //             $('#state_checkbox :checkbox[type="checkbox"]').removeAttr('checked');
    //             $(this).attr('checked','checked');
    //         }
    //     });
    // });


    if($(this).is(':checked')){
        $(this).attr('checked',true).siblings().attr('checked',false);
        alert($(this).val());
    }else{
        $(this).attr('checked',false).siblings().attr('checked',false);
    }
    var initQueryJSON = $("#report-query-abnormalBillReportPage").serializeJSON();
    $(function () {
        $("#report-goodsid-abnormalBillReportPage").widget({
            referwid:'RL_T_BASE_GOODS_INFO',
            width:'220',
            singleSelect:false
        })
        $("#report-brandid-abnormalBillReportPage").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            width:'160',
            singleSelect:false
        })
        $("#report-goodssort-abnormalBillReportPage").widget({
            referwid:'RL_T_BASE_GOODS_WARESCLASS',
            width:'120',
            singleSelect:false
        })

        var ledgerReportJson = $("#report-datagrid-abnormalBillReportPage").createGridColumnLoad({
            commonCol : [[
                {field:'ck',checkbox:true,isShow:true},
                {field:'goodsid',title:'商品编码',width:80,align:'left'},
                {field:'goodsname',title:'商品名称',width:250,align:'left'},
                {field:'storageid',title:'仓库编码',width:80,align:'left',sortable:true},
                {field:'storagename' ,title:'仓库名称',width:80,align:'left',sortable:true},
                {field:'initnum',title:'期初数量',width:80,align:'right',hidden:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'inittaxamount',title:'期初金额',width:80,align:'right',hidden:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'innum',title:'入库数量',width:80,align:'right',hidden:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'intaxamount',title:'入库金额',width:80,align:'right',hidden:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'outnum',title:'出库数量',width:80,align:'right',hidden:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'outtaxamount',title:'出库金额',width:80,align:'right',hidden:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'realendnum',title:'期末数量',width:80,align:'right',hidden:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'realendamount',title:'期末金额',width:80,align:'right',hidden:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'costprice' ,title:'期末单价',width:80,align:'right',sortable:true,
                    formatter:function(value,row,index){
                        return Number(value).toFixed(6);
                    }
                },
                {field:'buyprice' ,title:'采购价',width:80,align:'right',sortable:true},
                {field:'highestrealcostprice' ,title:'最高采购价',width:80,align:'right',sortable:true,
                    formatter:function(value,row,index){
                        return Number(value).toFixed(6);
                    }
                },
                {field:'lowestrealcostprice' ,title:'最低采购价',width:80,align:'right',sortable:true,
                    formatter:function(value,row,index){
                        return Number(value).toFixed(6);
                    }
                },
                {field:'nowCostPrice' ,title:'当前成本价',width:80,align:'right',sortable:true,
                    formatter:function(value,row,index){
                        return Number(value).toFixed(6);
                    }
                },
                {field:'highestcostprice' ,title:'最高销售成本价',width:80,align:'right',sortable:true,hidden:true,
                    formatter:function(value,row,index){
                        return Number(value).toFixed(6);
                    }
                },
                {field:'lowestcostprice' ,title:'最低销售成本价',width:80,align:'right',sortable:true,hidden:true,
                    formatter:function(value,row,index){
                        return Number(value).toFixed(6);
                    }
                },
                {field:'waverange',title:'销售成本波动范围',width:120,align:'right',
                    formatter:function(value,row,index){
                    if(value==undefined || value==''|| value==0){
                        return 0+'%';
                    }
                     return Number(value*100).toFixed(2)+'%';
                    }
                },
                {field:'swaverange',title:'库存成本波动范围',width:120,align:'right',
                    formatter:function(value,row,index){
                        if(value==undefined || value==''|| value==0){
                            return 0+'%';
                        }
                        return Number(value*100).toFixed(2)+'%';
                    }
                }
            ]]
        });

        $("#report-datagrid-abnormalBillReportPage").datagrid({
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
            toolbar:'#report-toolbar-abnormalBillReportPage',
            onDblClickRow: function(rowIndex,rowData) {
                var queryJSON = $("#report-query-abnormalBillReportPage").serializeJSON();
                queryJSON['goodsid']=rowData.goodsid;
                queryJSON['storageid']=rowData.storageid;
                queryJSON['costprice']=rowData.costprice;
                $("#abnormalBillReportPage-detaildialog-dialog").dialog({
                    title: '存货明细记录',
                    width: 950,
                    height: 560,
                    closed: false,
                    modal: true,
                    cache: false,
                    queryParams:queryJSON,
                    href: 'storage/cost/showCostAccountDetailPage.do',
                    onLoad: function () {

                    }
                });
            }
        });

        //查询
        $("#report-queay-abnormalBillReportPage").click(function(){
            setColumn();
            if(!$("#report-query-abnormalBillReportPage").form('validate')){
                return false;
            }
            var month=$("#report-businessdate-abnormalBillReportPage").val();
            var month1=$("#report-businessdate1-abnormalBillReportPage").val();
            if(month==''||month1==''){
                $.messager.alert("提醒","请选择业务日期!");
                return false;
            }
            var queryJSON = $("#report-query-abnormalBillReportPage").serializeJSON();
            $("#report-datagrid-abnormalBillReportPage").datagrid({
                url:' storage/cost/getAbnormalBillReportData.do',
                queryParams:queryJSON,
                pageNumber:1
            }).datagrid("columnMoving");
        });

        //重置
        $("#report-reload-abnormalBillReportPage").click(function () {
            $("#report-goodsid-abnormalBillReportPage").widget('clear');
            $("#report-brandid-abnormalBillReportPage").widget('clear');
            $("#report-query-abnormalBillReportPage")[0].reset();
            $("#report-datagrid-abnormalBillReportPage").datagrid("loadData",[]);
        });

        $("#report-account-abnormalBillReportPage").click(function(){
            var rows = $("#report-datagrid-abnormalBillReportPage").datagrid('getChecked');
            if (rows == null || rows.length == 0) {
                $.messager.alert("提醒", "请选择一条记录");
                return false;
            }

            var dataList=new Array();
            for(var i=0;i<rows.length;i++){
                dataList[i]={
                    storageid:rows[i].storageid,
                    goodsid:rows[i].goodsid
                };
            }

            $("#abnormalBillReportPage-accounttype-dialog").dialog({
                title: '结算方式',
                width: 250,
                height: 130,
                closed: false,
                modal: true,
                cache: false,
                href: 'storage/cost/showAccountTypePage.do',
                onLoad: function () {

                },
                buttons:[
                    {
                        text:'确定',
                        handler:function(){
                            var queryJSON = $("#report-query-abnormalBillReportPage").serializeJSON();
                            queryJSON['goodsid']=rows[0].goodsid;
                            queryJSON['storageid']=rows[0].storageid;
                            var accounttype=$("#report-accounttype-costAccountDetailPage").val();
                            queryJSON['accounttype']=accounttype;
                            queryJSON['datalist']=JSON.stringify(dataList);

                            loading("结算中");
                            $.ajax({
                                type: 'post',
                                cache: false,
                                // async: false,
                                url: 'storage/cost/accountCostReportData.do',
                                data: queryJSON,
                                dataType: 'json',
                                success: function (json) {
                                    loaded();
                                    if (json.flag) {
                                        $.messager.alert("提醒", "结算成功!");
                                        // $("#report-queay-abnormalBillReportPage").click();
                                        $("#abnormalBillReportPage-accounttype-dialog").dialog('close');
                                    } else {
                                        $.messager.alert("提醒", "结算失败!" + json.msg);
                                    }
                                }
                            });
                        }
                    },
                    {
                        text:'取消',
                        handler:function(){
                            $("#abnormalBillReportPage-accounttype-dialog").dialog('close');
                        }
                    }
                ],
            });
        })


        $("#report-account-storageAbnormalBillReportPage").click(function(){
            var enddate=''+$("#report-businessdate1-abnormalBillReportPage").val();
            var rows = $("#report-datagrid-abnormalBillReportPage").datagrid('getChecked');
            if (rows == null || rows.length == 0) {
                $.messager.alert("提醒", "请选择一条记录");
                return false;
            }

            $("#abnormalBillReportPage-storageCost-dialog").dialog({
                title: '库存成本调整',
                width: 350,
                height: 480,
                closed: false,
                modal: true,
                cache: false,
                queryParams:{
                    goodsid:rows[0].goodsid,
                    storageid:rows[0].storageid,
                    realendnum:rows[0].realendnum,
                    realendamount:rows[0].realendamount,
                    begindate:$("#report-businessdate-abnormalBillReportPage").val(),
                    enddate:enddate
                },
                href: 'storage/cost/showStorageCostChangePage.do',
                onLoad: function () {

                },
                buttons:[
                    {
                        text:'确定',
                        handler:function(){
                            var queryJSON = $("#report-query-abnormalBillReportPage").serializeJSON();
                            var storageQueryJSON=$("#report-form-storageCostChangePage").serializeJSON();
                            queryJSON['goodsid']=rows[0].goodsid;
                            //实际的单据生成的仓库，汇总类型的仓库取商品默认仓库
                            queryJSON['realstorageid']=storageQueryJSON.storageid;
                            queryJSON['storageid']=rows[0].storageid;
                            queryJSON['accounttype']=storageQueryJSON.accounttype;
                            queryJSON['changeamount']=storageQueryJSON.changeamount;
                            queryJSON['changedate']=storageQueryJSON.changedate;
                            queryJSON['isAcountCostPrice']=storageQueryJSON.isAcountCostPrice;

                            loading("调整中");
                            $.ajax({
                                type: 'post',
                                cache: false,
                                // async: false,
                                url: 'storage/cost/addChangeBill.do',
                                data: queryJSON,
                                dataType: 'json',
                                success: function (json) {
                                    loaded();
                                    if (json.flag) {
                                        $.messager.alert("提醒", "调整成功!");
                                        // $("#report-queay-abnormalBillReportPage").click();
                                        $("#abnormalBillReportPage-storageCost-dialog").dialog('close');
                                    } else {
                                        $.messager.alert("提醒", "调整失败!" + json.msg);
                                    }
                                }
                            });
                        }
                    },
                    {
                        text:'取消',
                        handler:function(){
                            $("#abnormalBillReportPage-storageCost-dialog").dialog('close');
                        }
                    }
                ],
            });
        })



        $("#report-export-abnormalBillReportPage").click(function(){
            var queryJSON = $("#report-query-abnormalBillReportPage").serializeJSON();
            //获取排序规则
            var objecr  = $("#report-datagrid-abnormalBillReportPage").datagrid("options");
            if(null != objecr.sortName && null != objecr.sortOrder ){
                queryJSON["sort"] = objecr.sortName;
                queryJSON["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryJSON);
            var url = "storage/cost/exportAccountCostReportData.do";
            exportByAnalyse(queryParam,"成本结算合计","report-datagrid-abnormalBillReportPage",url);
        });

        $("#report-accountLog-abnormalBillReportPage").click(function(){
            $("#abnormalBillReportPage-accountlog-dialog").dialog({
                title: '结算记录',
                width: 800,
                height: 560,
                closed: false,
                modal: true,
                cache: false,
                href: 'storage/cost/showAccountHistoryPage.do',
                onLoad: function () {

                }
            });
        })
    });

    function setColumn(){
        var $datagrid=$("#report-datagrid-abnormalBillReportPage");
        var pricetype=$("#report-pricetype-abnormalBillReportPage").val();
        if("1"==pricetype){
            $datagrid.datagrid('showColumn', "highestrealcostprice");
            $datagrid.datagrid('showColumn', "lowestrealcostprice");
            $datagrid.datagrid('hideColumn', "highestcostprice");
            $datagrid.datagrid('hideColumn', "lowestcostprice");
        }else if("2"==pricetype){
            $datagrid.datagrid('hideColumn', "highestrealcostprice");
            $datagrid.datagrid('hideColumn', "lowestrealcostprice");
            $datagrid.datagrid('showColumn', "highestcostprice");
            $datagrid.datagrid('showColumn', "lowestcostprice");
        }
    }
</script>
</body>
</html>
