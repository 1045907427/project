<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
  <style type="text/css">
      .datagrid-group{height:100px;overflow:hidden;font-weight:lighter;border-bottom:1px solid #ccc;background-color: #38B1B9}
  </style>
    <table id="sales-table-fullFreePage"></table>
    <script type="text/javascript">
    	$(function(){
			$("#sales-table-fullFreePage").datagrid({
				columns:[[
                    {field:'groupid',title:'促销产品编号',width:100, align: 'right',hidden:true},
                    {field:'goodsid',title:'商品编码',width:80,align:'left'},
                    {field:'goodsname',title:'商品名称',width:200,align:'left'},
                    {field:'unitname',title:'单位',width:60,align:'left',isShow:true},
                    {field:'boxnum',title:'箱装量',width:60,align:'left'},
                    {field:'sendnumdetail',title:'每份赠送数量',width:100,align:'left'},
                    {field:'auxnumdetail',title:'赠送总数量',width:100,align:'right'},
                    {field:'remark',title:'备注',width:100}
                ]],
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		collapsible:true,
		 		singleSelect:true,
		 		data:JSON.parse('${listJson}'),
		 		view:groupview,
                groupField:'groupid',
                groupFormatter:function(value,rows){
                    return '<label style="height:60px;cursor: pointer;"><input type="radio" name="fullFreePage-group" class="fullFreePage-group checkbox1" value="'+value+'" />'+rows[0].groupname+'</label>';
                }
			});
    	});
    </script>
  </body>
</html>
