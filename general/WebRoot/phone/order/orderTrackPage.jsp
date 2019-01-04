<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
  <head>
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0"/>
    <title>要货单列表</title>
	<script type="text/javascript" src="../zepto.js"></script>
	<script type="text/javascript" src="../zepto.animate.js"></script>
	<style type="text/css">
		body{overflow:hidden}
		body,p{margin:0;padding;}
		a{text-decoration:none;}
		.clear{clear:both;}
		#queryForm{display:none;}
		.list_item{padding:5px 8px;line-height:20px;border-bottom:2px solid #dfdfdf;clear:both;}
		.list_item .td1{width:80px;float:left;height:100%;}
		.loading{line-height:40px;text-align:center;background:#f6f6f6;}
	</style>
  </head>
  <body>
    <form id="queryForm">
    	<input type="hidden" name="page" value="1" />
    	<input type="hidden" name="rows" value="${rows }" />
    	<input type="hidden" name="sort" value="addtime" />
    	<input type="hidden" name="order" value="desc" />
    	<input type="hidden" name="adduserid" value="${uid }" />
    	<input type="hidden" name="businessdate" value="${businessdate }" />
    	<input type="hidden" name="businessdate1" value="${businessdate1 }" />
    	<input type="hidden" name="customerid" value="${customerid }" />
    	<input type="hidden" name="id" value="${id }" />
    	<input type="hidden" name="status" value="0,1" />
    </form>
    <div class="main">
    	<div class="list"></div>
    	<div class="loading">
    		数据加载中...
    	</div>
    </div>
    <script type="text/javascript">
    	var total = 0;
    	var isLoading = true;
    	$(function(){
    		$(".list_item").live('touchstart', function(){
    			$(this).css("background", "#efefef");
    		});
    		$(".list_item").live('touchmove', function(){
    			$(this).css("background", "");
    		});
    		$(".list_item").live('touchend', function(){
    			$(this).css("background", "");
    		});
    		$(".list_item").live('click', function(){
    			var id = $(this).attr("id");
    			location.href = "orderTrack.do?id="+ id;
    		});
    		var querystring = $("#queryForm").serialize();
    		$.ajax({
    			url:'getOrderTrackList.do',
    			dataType:'json',
    			type:'post',
    			data:querystring,
    			success:function(json){
    				isLoading = false;
    				total = json.total;
    				if(total == 0){
    					$(".list").html("未查询到任何符合条件的数据。");
    					$(".loading").hide();
    					return ;
    				}
    				var page = $("input[name=page]").val();
    				var rows = $("input[name=rows]").val();
    				if(total>(Number(page)*Number(rows))){
    					$(".loading").html("更多");
    				}
    				else{
    					$(".loading").hide();
    				}
    				var rows = json.rows;
    				for(var i in rows){
    					var row = rows[i];
    					var html=[
    						'<div class="list_item" id="'+row.id+'">',
    						'<table>',
    						'<tr>',
    						'<td class="td1">客户</td>',
    						'<td>'+row.customerid+row.customername+'</td>',
    						'</tr>',
    						'<tr>',
    						'<td class="td1">编号</td>',
    						'<td>'+row.id+'</td>',
    						'</tr>',
    						'<tr>',
    						'<td class="td1">金额</td>',
    						'<td>'+row.field01+'</td>',
    						'</tr>',
    						'<tr>',
    						'<td class="td1">业务日期</td>',
    						'<td>'+row.businessdate+'</td>',
    						'</tr>',
    						'<tr>',
    						'<td class="td1">制单时间</td>',
    						'<td>'+row.addtime+'</td>',
    						'</tr>',
    						'</table>',
    						'</div>'
    					].join('');
    					$(html).appendTo(".list");
    				}
    			}
    		});
    		$(".loading").on('click', function(){
    			$(this).html("数据加载中...");
    			getMore();
    		});
    	});
    	function getMore(){
    		if(isLoading) return ;
    		var page = $("input[name=page]").val();
    		$("input[name=page]").val(Number(page)+1);
    		var querystring = $("#queryForm").serialize();
    		isLoading = true;
    		$.ajax({
    			url:'getOrderTrackList.do',
    			dataType:'json',
    			type:'post',
    			data:querystring,
    			success:function(json){
    				isLoading = false;
    				total = json.total;
    				var page = $("input[name=page]").val();
    				var rows = $("input[name=rows]").val();
    				if(total>(Number(page)*Number(rows))){
    					$(".loading").html("更多");
    				}
    				else{
    					$(".loading").hide();
    				}
    				var rows = json.rows;
    				for(var i in rows){
    					var row = rows[i];
    					var html=[
    						'<div class="list_item" id="'+row.id+'">',
    						'<table>',
    						'<tr>',
    						'<td class="td1">客户</td>',
    						'<td>'+row.customerid+row.customername+'</td>',
    						'</tr>',
    						'<tr>',
    						'<td class="td1">编号</td>',
    						'<td>'+row.id+'</td>',
    						'</tr>',
    						'<tr>',
    						'<td class="td1">金额</td>',
    						'<td>'+row.field01+'</td>',
    						'</tr>',
    						'<tr>',
    						'<td class="td1">业务日期</td>',
    						'<td>'+row.businessdate+'</td>',
    						'</tr>',
    						'<tr>',
    						'<td class="td1">制单时间</td>',
    						'<td>'+row.addtime+'</td>',
    						'</tr>',
    						'</table>',
    						'</div>'
    					].join('');
    					$(html).appendTo(".list");
    				}
    			}
    		});
    	}
    </script>
  </body>
</html>
