<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>批量特价审批单处理页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
    <div class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'north',border:false">
            <div id="oa-condition-oaOffPriceDetailsAddPage">
                <form id="oa-form-oaOffPriceDetailsAddPage">
                    <input type="hidden" name="customerid" value="${param.customerid }"/>
                    <table>
                        <tr>
                            <td>编码/条形码：</td>
                            <td><input name="id" id="oa-id-oaOffPriceDetailsAddPage" class="len130" autocomplete="off"/></td>
                            <td>品牌：</td>
                            <td><input name="brandids" id="oa-brandids-oaOffPriceDetailsAddPage"/></td>
                            <td>商品分类：</td>
                            <td><input name="defaultsorts" id="oa-defaultsorts-oaOffPriceDetailsAddPage"/></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td colspan="3" class="right">
                                <a href="javascript:void(0);" id="oa-query-oaOffPriceDetailsAddPage" class="button-qr">查询</a>
                                <a href="javaScript:void(0);" id="oa-reset-oaOffPriceDetailsAddPage" class="button-qr">重置</a>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
        <div data-options="region:'center',border:false">
            <table id="oa-datagrid-oaOffPriceDetailsAddPage"></table>
        </div>
    </div>

</div>
<script type="text/javascript">
    <!--

    $(function () {

        $('#oa-brandids-oaOffPriceDetailsAddPage').widget({
            referwid: 'RL_T_BASE_GOODS_BRAND',
            singleSelect: false,
            width: 150
        });

        $('#oa-defaultsorts-oaOffPriceDetailsAddPage').widget({
            referwid: 'RL_T_BASE_GOODS_WARESCLASS',
            singleSelect: false,
            onlyLeafCheck: false,
            width: 150
        });

        $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid({
            columns: [[
                {field:'id', checkbox: true},
                {field:'goodsid', title: '商品编号', width: 70},
                {field:'goodsname', title: '商品名称', width: 210},
                {field:'barcode', title: '条形码', width: 120},
                {field:'buyprice', title: '进价', width: 55, align: 'right', hidden: false, <c:if test="${param.buyprice ne '1'}">hidden: true,</c:if>
                    formatter: function (value) {
                        return formatterMoney(value);
                    }
                },
                {field:'oldprice', title: '原价', width: 55, align: 'right',
                    formatter: function (value) {
                        return formatterMoney(value);
                    }
                },
                {field:'offprice', title: '特价', width: 55, align: 'right',
                    editor: {
                        type: 'numberbox',
                        options: {
                            required: false,
                            precision: 2,
                            onChange: computeProfit2
                        }
                    }
                },
                {field:'profitrate', title: '毛利率%', width: 55, align: 'right', hidden: false, <c:if test="${param.buyprice ne '1'}">hidden: true,</c:if>
                    editor: {
                        type: 'span'
                    }
                },
                {field:'ordernum', title: '本次订单数量', width: 100, align: 'right',
                    editor: {
                        type: 'textbox',
                        options:{
                            required: false,
                            validType: 'maxByteLength[20]'
                        }
                    }
                },
                {field:'remark', title: '说明', width: 145,
                    editor: {
                        type: 'textbox',
                        options:{
                            required: false,
                            validType: 'maxByteLength[50]'
                        }
                    }
                }
            ]],
            border: true,
            fit: true,
            rownumbers: true,
            showFooter: true,
            singleSelect: false,
            pagination: true,
            pageList: [20],
            pageSize: 20,
            checkOnSelect: false,
            toolbar: '#oa-condition-oaOffPriceDetailsAddPage',
            onClickRow: function () {
                $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('endEdit', retDetailEditIndex());
            },
            onDblClickCell: function (index, field) {

                var targetField = field;
                if(field != 'offprice' && field != 'ordernum' && field != 'remark') {
                    targetField = 'offprice';
                }
                var currentIndex = retDetailEditIndex();
                if(currentIndex >= 0) {
                    $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('endEdit', currentIndex);
                }

                $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('beginEdit', index);
                bindKeydownEvent(index, targetField);

            },
            onLoadSuccess: function(data) {

            }
        }); // datagrid close

        $('#oa-query-oaOffPriceDetailsAddPage').off('click').on('click', function(){

            var param = $('#oa-form-oaOffPriceDetailsAddPage').serializeJSON();
            $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid({
                url: 'oa/offprice/getGoodsList.do',
                queryParams: param
            });
        });

        $('#oa-reset-oaOffPriceDetailsAddPage').off('click').on('click', function(){

            $('#oa-form-oaOffPriceDetailsAddPage')[0].reset();
            $('#oa-form-oaOffPriceDetailsAddPage').form('clear');
            $('#oa-brandids-oaOffPriceDetailsAddPage').widget('clear');
            $('#oa-defaultsorts-oaOffPriceDetailsAddPage').widget('clear');
            $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('loadData', []);
        });

    });

    /**
     * 当前编辑行index
     *
     * @returns {number}
     */
    function retDetailEditIndex() {

        var rows = $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('getRows');

        for(var i = 0; i < rows.length; i++) {
            var editors = $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('getEditors', i);
            if(editors.length > 0) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 绑定回车事件
     * @returns {boolean}
     */
    function bindKeydownEvent(index, colname) {

        var editor1 = $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('getEditor', {index: index, field: 'offprice'});
        var editor2 = $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('getEditor', {index: index, field: 'ordernum'});
        var editor3 = $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('getEditor', {index: index, field: 'remark'});
        colname == 'offprice' ? $(editor1.target).textbox('textbox').focus() : false;
        $(editor1.target).textbox('textbox').off('keydown').on('keydown', function(e){
            if(e.keyCode == 13) {
                $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('endEdit', index);
                var rows = $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('getRows');
                var row = rows[index];
                if((row.offprice || '') != '') {
                    $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('checkRow', index);
                    $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('selectRow', index);
                }
                if(index >= 19) {
                    return true;
                }
                $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('beginEdit', index + 1);
                bindKeydownEvent(index + 1, 'offprice');
            }
        });
        colname == 'ordernum' ? $(editor2.target).textbox('textbox').focus() : false;
        $(editor2.target).textbox('textbox').off('keydown').on('keydown', function(e){
            if(e.keyCode == 13) {
                $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('endEdit', index);
                var rows = $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('getRows');
                var row = rows[index];
                if((row.offprice || '') != '') {
                    $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('checkRow', index);
                    $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('selectRow', index);
                }
                if(index >= 19) {
                    return true;
                }
                $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('beginEdit', index + 1);
                bindKeydownEvent(index + 1, 'ordernum');
            }
        });
        colname == 'remark' ? $(editor3.target).textbox('textbox').focus() : false;
        $(editor3.target).textbox('textbox').off('keydown').on('keydown', function(e){
            if(e.keyCode == 13) {
                $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('endEdit', index);
                var rows = $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('getRows');
                var row = rows[index];
                if((row.offprice || '') != '') {
                    $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('checkRow', index);
                    $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('selectRow', index);
                }
                if(index >= 19) {
                    return true;
                }
                $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('beginEdit', index + 1);
                bindKeydownEvent(index + 1, 'remark');
            }
        });
    }

    /**
     * 计算毛利率
     *
     * @returns {boolean}
     */
    function computeProfit2(newVal, oldVal) {

        var rows = $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('getRows');
        var currentIndex = retDetailEditIndex();
        var row = rows[currentIndex];
        var buyprice = row.buyprice || 0;
        var editor1 = $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('getEditor', {index: currentIndex, field: 'offprice'});
        var offprice = $(editor1.target).numberbox('getValue');

        var editor2 = $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('getEditor', {index: currentIndex, field: 'profitrate'});
        if(buyprice == 0) {
            $(editor2.target).html('');
            return true;
        }

        var profit = formatterMoney((parseFloat(offprice) - parseFloat(buyprice)) / parseFloat(offprice) * 100);
        $(editor2.target).html(profit);
        return true;
    }
    -->
</script>
</body>
</html>