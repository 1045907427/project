<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>分销规则批量添加商品</title>
</head>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
    <div class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'north',border:false">
            <div id="oa-condition-distributionRuleGoodsAddPage">
                <form id="oa-form-distributionRuleGoodsAddPage">
                    <input type="hidden" name="customerid" value="${param.customerid }"/>
                    <input type="hidden" name="exceptids" />
                    <table>
                        <tr>
                            <td>编码/条形码：</td>
                            <td><input name="id" id="oa-id-distributionRuleGoodsAddPage" class="len130" autocomplete="off"/></td>
                            <td>品牌：</td>
                            <td><input name="brandids" id="oa-brandids-distributionRuleGoodsAddPage"/></td>
                            <td>商品分类：</td>
                            <td><input name="defaultsorts" id="oa-defaultsorts-distributionRuleGoodsAddPage"/></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td colspan="3" class="right">
                                <a href="javascript:void(0);" id="oa-query-distributionRuleGoodsAddPage" class="button-qr">查询</a>
                                <a href="javaScript:void(0);" id="oa-reset-distributionRuleGoodsAddPage" class="button-qr">重置</a>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
        <div data-options="region:'center',border:false">
            <table id="oa-datagrid-distributionRuleGoodsAddPage"></table>
        </div>
    </div>

</div>
<script type="text/javascript">
    <!--

    $(function () {

        (function () {

            var currentRows = $datagrid.datagrid('getRows');
            var exceptids = new Array();
            for(var i in currentRows) {
                exceptids.push(currentRows[i].goodsid);
            }

            $('[name=exceptids]').val(exceptids.join(','));
        })();

        $('#oa-brandids-distributionRuleGoodsAddPage').widget({
            referwid: 'RL_T_BASE_GOODS_BRAND',
            singleSelect: false,
            width: 150
        });

        $('#oa-defaultsorts-distributionRuleGoodsAddPage').widget({
            referwid: 'RL_T_BASE_GOODS_WARESCLASS',
            singleSelect: false,
            onlyLeafCheck: false,
            width: 150
        });

        $('#oa-datagrid-distributionRuleGoodsAddPage').datagrid({
            columns: [[
                {field:'checkbox', checkbox: true},
                {field:'goodsid', title: '商品编码', width: 70},
                {field:'goodsname', title: '商品名称', width: 210},
                {field:'barcode', title: '条形码', width: 120},
                {field:'brandname', title: '品牌', width: 80},
                {field:'goodssortname', title: '商品分类', width: 70},
                {field:'boxnum', title: '箱装量', width: 70, align: 'right'}
            ]],
            fit: true,
            rownumbers: true,
            showFooter: true,
            singleSelect: false,
            pagination: true,
            checkOnSelect: true,
            selectOnCheck: true,
            toolbar: '#oa-condition-distributionRuleGoodsAddPage'
        }); // datagrid close

        $('#oa-query-distributionRuleGoodsAddPage').off('click').on('click', function(){

            var param = $('#oa-form-distributionRuleGoodsAddPage').serializeJSON();
            $('#oa-datagrid-distributionRuleGoodsAddPage').datagrid({
                url: 'basefiles/distribution/getGoodsList.do',
                queryParams: param
            });
        });

        $('#oa-reset-distributionRuleGoodsAddPage').off('click').on('click', function(){

            $('#oa-form-distributionRuleGoodsAddPage')[0].reset();
            $('#oa-form-distributionRuleGoodsAddPage').form('clear');
            $('#oa-brandids-distributionRuleGoodsAddPage').widget('clear');
            $('#oa-defaultsorts-distributionRuleGoodsAddPage').widget('clear');
            $('#oa-datagrid-distributionRuleGoodsAddPage').datagrid('loadData', []);
        });

    });

    -->
</script>
</body>
</html>