<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>商品成本变更记录列表</title>
  </head>
  
  <body>
    <table id="wares-table-costpriceChangeList"></table>

	<script type="text/javascript">
		$(function(){
            $('#wares-table-costpriceChangeList').datagrid({
                columns:[[
                    {field:'goodsid',title:'商品编码',width:80},
                    {field:'goodsname',title:'商品名称',width:120},
                    {field:'costprice',title:'成本价',width:100},
                    {field:'remark',title:'备注',width:200},
                    {field:'billid',title:'单据编号',width:100},
                    {field:'addtime',title:'添加时间',width:130}
                ]],
                fit:true,
                showFooter:true,
                method:'post',
                rownumbers:true,
                idField:'billid',
                singleSelect:true,
                pagination:true,
                striped:true,
                url:'basefiles/getGoodesSimplifyViewCostpriceChageData.do?goodsid=${param.id}'
            }).datagrid("columnMoving");
        });
	</script>
  </body>
</html>
