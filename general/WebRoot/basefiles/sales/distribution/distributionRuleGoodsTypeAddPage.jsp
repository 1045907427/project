<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>分销规则批量添加品牌</title>
</head>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
    <div class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'north',border:false">
        </div>
        <div data-options="region:'center',border:false">
            <table id="sales-datagrid-distributionRuleGoodsTypeAddPage"></table>
        </div>
    </div>

</div>
<script type="text/javascript">
    <!--

    $(function () {

        $('#sales-datagrid-distributionRuleGoodsTypeAddPage').datagrid({
            columns: [[
                {field:'checkbox', checkbox: true},
                {field:'code', title: '品牌编码', width: 70, hidden: true},
                {field:'codename', title: '商品分类', width: 110}
            ]],
            fit: true,
            rownumbers: true,
            showFooter: true,
            singleSelect: false,
            pagination: false,
            checkOnSelect: true,
            selectOnCheck: true,
            data: <c:out value="${goodstypes }" escapeXml="false"/>
        }); // datagrid close

    });

    -->
</script>
</body>
</html>