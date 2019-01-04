<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商品科目新增</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:true">
    <div data-options="region:'north',split:false,border:false" style="height: 70px;overflow: hidden;">
        <div id="ledger-queryDiv-thirdGoodsSubjectAddPage" style="padding-bottom: 15px;">
            <form id="ledger-form-thirdGoodsSubjectAddPage">
                <input type="hidden" name="dbid" value="${param.dbid}"/>
                <input type="hidden" name="pid" value="${param.pid}"/>
                <table>
                    <tr>
                        <td>商品：</td>
                        <td><input type="text" id="thirdGoodsSubjectAddPage-widget-goodsid" name="goodsid" autocomplete="off"/></td>
                        <td>品牌：</td>
                        <td><input type="text" id="thirdGoodsSubjectAddPage-widget-brandid" name="brandid" autocomplete="off"/></td>
                        <td>商品分类：</td>
                        <td><input type="text" id="thirdGoodsSubjectAddPage-widget-goodssort" name="goodssort" autocomplete="off"/></td>
                    </tr>
                    <tr>
                        <td>科目：</td>
                        <td><input type="text" id="thirdGoodsSubjectAddPage-widget-subjectid" name="subjectid" autocomplete="off"/></td>
                        <td colspan="2" class="tdbutton">
                            <a href="javascript:void(0);" id="ledger-query-thirdGoodsSubjectAddPage" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="ledger-reset-thirdGoodsSubjectAddPage" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div data-options="region:'center',border:false" >
        <table id="ledger-datagrid-thirdGoodsSubjectAddPage"></table>
    </div>
</div>
<script type="text/javascript">

    $(function () {

        $("#thirdGoodsSubjectAddPage-widget-subjectid").widget({
            referwid:'RT_T_ERP_ACCOUNTCODE',
            onlyLeafCheck:true,
            singleSelect:true,
            width:120,
            param : [{field: 'id', op: 'startwith', value: '${param.subjectid}'},{field:'isleaf',op:'equal',value:'1'},
                {field:'dbid',op:'equal',value:'${param.dbid}'}],
            required:true
        })

        $("#thirdGoodsSubjectAddPage-widget-goodsid").widget({
            referwid:'RL_T_BASE_GOODS_INFO',
            singleSelect:false,
            width:120
        })

        $("#thirdGoodsSubjectAddPage-widget-brandid").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            singleSelect:false,
            width:120
        })

        $("#thirdGoodsSubjectAddPage-widget-goodssort").widget({
            referwid:'RL_T_BASE_GOODS_WARESCLASS',
            singleSelect:false,
            width:120
        })

        $("#ledger-datagrid-thirdGoodsSubjectAddPage").datagrid({
            frozenColumns: [[{field: 'ck', checkbox: true}]],
            columns:  [[
                {field: 'id', title: '商品编码', sortable: false, width: 110},
                {field: 'name', title: '商品名称', sortable: false, width: 200},
                {field: 'brandid', title: '品牌', sortable: false, width: 200,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.brandname;
                    }
                },
            ]],
            fit: true,
            border: false,
            rownumbers: true,
            pagination: true,
            pageSize: 100,
            idField: 'id',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            toolbar: 'ledger-queryDiv-thirdGoodsSubjectAddPage',
            url: 'thirdDb/getThirdGoodsList.do',
            queryParams: $("#ledger-form-thirdGoodsSubjectAddPage").serializeJSON()
        }).datagrid("columnMoving");

        // 查询
        $('#ledger-query-thirdGoodsSubjectAddPage').click(function (e) {
            var param = $("#ledger-form-thirdGoodsSubjectAddPage").serializeJSON();
            $("#ledger-datagrid-thirdGoodsSubjectAddPage").datagrid('load', param);
        });

        // 重置
        $('#ledger-reset-thirdGoodsSubjectAddPage').off('click').on('click', function (e) {


            $("#ledger-form-thirdGoodsSubjectAddPage")[0].reset();
            $("#thirdGoodsSubjectAddPage-widget-subjectid").widget('clear');
            $("#thirdGoodsSubjectAddPage-widget-goodsid").widget('clear');
            $("#thirdGoodsSubjectAddPage-widget-brandid").widget('clear');
            $("#thirdGoodsSubjectAddPage-widget-goodssort").widget('clear');

            var param = $("#ledger-form-thirdGoodsSubjectAddPage").serializeJSON();
            $("#ledger-datagrid-thirdGoodsSubjectAddPage").datagrid('load', param);
        });

    });

</script>
</body>
</html>
