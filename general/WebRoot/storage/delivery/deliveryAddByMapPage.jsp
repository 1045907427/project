<%--
  Created by IntelliJ IDEA.
  User: limin
  Date: 2016/9/29
  Time: 9:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>配送单新增（按地图）</title>
    <%@include file="/include.jsp" %>
</head>
<body style="padding: 0px; margin: 0px;">
<iframe name="mapframe" src="storage/deliveryAddByMapPage2.do" style="border: 0px; margin: 0px;" width="100%" height="100%" frameborder="0"></iframe>
<div style="display: none;">
    <div id="storage-dialog-deliveryAddByMapPage">
        <form id="storage-form-deliveryAddByMapPage">
            <table id="storage-table-deliveryAddByMapPage">
                <tr>
                    <td>线路：</td>
                    <td><input type="text" name="lineid" id="storage-lineid-deliveryAddByMapPage"/></td>
                </tr>
                <tr>
                    <td>车辆：</td>
                    <td><input type="text" name="carid" id="storage-carid-deliveryAddByMapPage" readonly="readonly" style="width: 250px;"/></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div style="display: none;">
    <div id="storage-dialog2-deliveryAddByMapPage">
        <table id="storage-datagrid-deliveryAddByMapPage">
        </table>
    </div>
</div>
<div style="display: none;">
    <div id="storage-dialog3-deliveryAddByMapPage">
        <table id="storage-datagrid2-deliveryAddByMapPage">
        </table>
    </div>
</div>
<style type="text/css">

    #storage-table-deliveryAddByMapPage {
        margin: 15px;
    }

    #storage-table-deliveryAddByMapPage td {
        border: 0px;
        padding: 0px;
        text-indent: 0px;
    }
</style>
<script type="text/javascript">

    $(function() {

        var orderCols2 = [[
            {field:'saleoutid', title:'编号', width:130,sortable:true},
            {field:'businessdate', title:'业务日期', width:70,sortable:true},
            {field:'orderid', title:'订单编号', width:130,sortable:true},
            {field:'customerid',title:'客户编码',width:80,align:'left'},
            {field:'customername',title:'客户名称',width:150,align:'left'},
            {field:'salesamount',title:'销售额',width:100,align:'right',sortable:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'boxnum',title:'商品箱数',width:80,align:'right',sortable:true,
                formatter:function(value,row,index){
                    return formatterBigNumNoLen(value);
                }
            },
            {field:'weight',title:'商品重量',width:80,align:'right',sortable:true,
                formatter:function(value,row,index){
                    if(value != null && value != undefined) {

                        return formatterMoney(value)+" kg";
                    }
                }
            },
            {field:'volume',title:'商品体积',width:80,align:'right',sortable:true,
                formatter:function(value,row,index){
                    if(value != null && value != undefined) {

                        return formatterMoney(value,4)+" m³";
                    }
                }
            },
            {field:'status',title:'状态',width:60,
                formatter:function(value,rowData,rowIndex){
                    return getSysCodeName("status",value);
                }
            },
            {field:'deliverytype',title:'来源单据类型',width:80,
                formatter:function(value,rowData,rowIndex){
                    if(value=='0'){
                        return "销售发货单";
                    }else if(value=='1'){
                        return "代配送出库单";
                    }
                }
            },
            {field:'remark',title:'备注',width:80,align:'left'}
        ]];
        var orderCols3 = [[
            {field:'checkbox',checkbox: true},
            {field:'saleoutid', title:'编号', width:130,sortable:true},
            {field:'businessdate', title:'业务日期', width:70,sortable:true},
            {field:'orderid', title:'订单编号', width:130,sortable:true},
            {field:'customerid',title:'客户编码',width:80,align:'left'},
            {field:'customername',title:'客户名称',width:150,align:'left'},
            {field:'salesareaname',title:'销售区域',width:70,align:'left'},
            {field:'salesamount',title:'销售额',width:100,align:'right',sortable:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'boxnum',title:'商品箱数',width:80,align:'right',sortable:true,
                formatter:function(value,row,index){
                    return formatterBigNumNoLen(value);
                }
            },
            {field:'weight',title:'商品重量',width:80,align:'right',sortable:true,
                formatter:function(value,row,index){
                    if(value != null && value != undefined) {

                        return formatterMoney(value)+" kg";
                    }
                }
            },
            {field:'volume',title:'商品体积',width:80,align:'right',sortable:true,
                formatter:function(value,row,index){
                    if(value != null && value != undefined) {

                        return formatterMoney(value,4)+" m³";
                    }
                }
            },
            {field:'status',title:'状态',width:60,
                formatter:function(value,rowData,rowIndex){
                    return getSysCodeName("status",value);
                }
            },
            {field:'deliverytype',title:'来源单据类型',width:80,
                formatter:function(value,rowData,rowIndex){
                    if(value=='0'){
                        return "销售发货单";
                    }else if(value=='1'){
                        return "代配送出库单";
                    }
                }
            },
            {field:'remark',title:'备注',width:80,align:'left'}
        ]];

        // 已选择单据
        $('#storage-datagrid-deliveryAddByMapPage').datagrid({
            columns: orderCols2,
            fit: true,
            rownumbers: true,
            pagination: false,
            idField: 'saleoutid',
            singleSelect: true,
            selectOnCheck: false,
            showFooter:true,
            checkOnSelect: true
        }).datagrid("columnMoving");

        // 未显示单据列表
        $('#storage-datagrid2-deliveryAddByMapPage').datagrid({
            columns: orderCols3,
            fit: true,
            rownumbers: true,
            pagination: false,
            idField: 'saleoutid',
            singleSelect: false,
            selectOnCheck: true,
            showFooter:true,
            checkOnSelect: true,
            onSelect: function (index, row) {
                window.mapframe.rows.checkBill(row.saleoutid);
            },
            onUnselect: function (index, row) {
                window.mapframe.rows.uncheckBill(row.saleoutid);
            },
            onSelectAll: function (rows) {
                for(var i in rows) {
                    var row = rows[i];
                    window.mapframe.rows.checkBill(row.saleoutid);
                }
            },
            onUnselectAll: function (rows) {
                for(var i in rows) {
                    var row = rows[i];
                    window.mapframe.rows.uncheckBill(row.saleoutid);
                }
            }
        }).datagrid("columnMoving");
        
        // 线路
        $('#storage-lineid-deliveryAddByMapPage').widget({
            referwid:'RL_T_BASE_LOGISTICS_LINE',
            singleSelect:true,
            width:250,
            onlyLeafCheck:true,
            required: true,
            onSelect:function(data){

                window.mapframe.$('#storage-lineid-deliveryAddByMapPage2').val(data.id);
                window.mapframe.$('#storage-linename-deliveryAddByMapPage2').val(data.name);
                window.$('#storage-maxvolumn-deliveryAddByMapPage2').val('');
                window.$('#storage-maxweight-deliveryAddByMapPage2').val('');
                window.$('#storage-maxboxnum-deliveryAddByMapPage2').val('');

                $('#storage-carid-deliveryAddByMapPage').removeAttr('readonly');
                var oldval = $('#storage-carid-deliveryAddByMapPage').widget('getValue');
                $('#storage-carid-deliveryAddByMapPage').widget({
                    referwid:'RL_T_BASE_LOGISTICS_LINE_CAR',
                    param:[{field:'lineid',op:'equal',value:data.id}],
                    singleSelect:true,
                    width:250,
                    onlyLeafCheck:true,
                    setValueSelect:true,
                    initValue:data.carid,
                    required: true,
                    onSelect:function(data2){

                        window.mapframe.$('#storage-carid-deliveryAddByMapPage2').val(data2.id);
                        var car = $('#storage-carid-deliveryAddByMapPage').widget('getObject');

                        window.mapframe.$('#storage-maxvolumn-deliveryAddByMapPage2').val(car.volumn);
                        window.mapframe.$('#storage-maxweight-deliveryAddByMapPage2').val(car.weight);
                        window.mapframe.$('#storage-maxboxnum-deliveryAddByMapPage2').val(car.boxnum);
                    },
                    onClear:function(data){

                        window.mapframe.$('#storage-maxvolumn-deliveryAddByMapPage2').val('');
                        window.mapframe.$('#storage-maxweight-deliveryAddByMapPage2').val('');
                        window.mapframe.$('#storage-maxboxnum-deliveryAddByMapPage2').val('');
                    },
                    onLoadSuccess:function(){

                        window.mapframe.$('#storage-carid-deliveryAddByMapPage2').val(data.carid);
                        $(this).widget('setValue', data.carid);
                        var car = $('#storage-carid-deliveryAddByMapPage').widget('getObject');

                        window.mapframe.$('#storage-maxvolumn-deliveryAddByMapPage2').val(car.volumn);
                        window.mapframe.$('#storage-maxweight-deliveryAddByMapPage2').val(car.weight);
                        window.mapframe.$('#storage-maxboxnum-deliveryAddByMapPage2').val(car.boxnum);
                    }
                });
                $('#storage-carid-deliveryAddByMapPage').widget('setValue', oldval);
            },
            onClear:function(data){
                $("#storage-carid-deliveryAddByMapPage").widget('clear');
                window.mapframe.$('#storage-maxvolumn-deliveryAddByMapPage2').val('');
                window.mapframe.$('#storage-maxweight-deliveryAddByMapPage2').val('');
                window.mapframe.$('#storage-maxboxnum-deliveryAddByMapPage2').val('');
            }
        });
    });

    /**
     * 设定线路车辆
     *
     * @returns {boolean}
     */
    function setLine() {

        $('#storage-dialog-deliveryAddByMapPage').dialog({
            maximizable: false,
            resizable: false,
            title: '指定线路/车辆',
            width: 350,
            height: 170,
            closed: false,
            cache: false,
            modal: true,
            buttons: [{
                iconCls:'button-save',
                text: '确定',
                handler:function(){

                    var flag = $('#storage-form-deliveryAddByMapPage').form('validate');
                    if(!flag){
                        return false;
                    }

                    $('#storage-dialog-deliveryAddByMapPage').dialog('close');
                    window.mapframe.refreshStatisticBar();
                }
            }],
            onClose:function(){
            }
        });

        return true;
    }
</script>
</body>
</html>
