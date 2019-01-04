<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
  <head>
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0"/>
    <title>要货单查询</title>
	<script type="text/javascript" src="../zepto.js"></script>
	<script type="text/javascript" src="../zepto.animate.js"></script>
	<link type="text/css" href="query.css" rel="stylesheet" />
  </head>
  <body>
    <div class="qn_main">
    	<form id="queryForm">
    		<input type="hidden" name="rows" value="20" />
    		<input type="hidden" name="uid" value="${uid }" />
    		<div class="qn_border">
	    		<div class="qn_item qn_bt">
	    			<div class="qn_fl">业务日期</div>
	    			<div class="qn_extend" id="beginDate">
	    				<div class="qn_extend_l">${today}</div>
	    				<div class="qn_arrow_grey r"></div>
	    			</div>
	    			<div class="clear"></div>
	    			<input type="hidden" name="businessdate" value="${today }" />
	    		</div>
	    		<div class="qn_item">
	    			<div class="qn_fl">结束日期</div>
	    			<div class="qn_extend" id="endDate">
	    				<div class="qn_extend_l">${today }</div>
	    				<div class="qn_arrow_grey r"></div>
	    			</div>
	    			<div class="clear"></div>
	    			<input type="hidden" name="businessdate1" value="${today }" />
	    		</div>
	    	</div>
    		<div class="qn_border qn_mt">
    			<div class="qn_item">
	    			<div class="qn_fl">客户名称</div>
	    			<div class="qn_extend" id="customer">
	    				<div class="qn_extend_l" style="overflow:hidden;"></div>
	    				<div class="qn_arrow_grey r"></div>
	    			</div>
	    			<div class="clear"></div>
	    			<input type="hidden" name="customerid" />
	    		</div>
    		</div>
    		<div class="qn_border qn_mt">
    			<div class="qn_item">
	    			<div class="qn_fl">单据编号</div>
	    			<div class="qn_extend">
	    				<div class="qn_extend_l"><input type="text" class="qn_inp" name="id" /></div>
	    			</div>
	    			<div class="clear"></div>
	    		</div>
    		</div>
    		<div class="qn_border qn_mt">
    			<div class="qn_item">
	    			<div class="qn_fl">每页显示</div>
	    			<div class="qn_extend">
	    				<div class="qn_extend_l" id="rowsNum">
	    					<a class="num_item num_item_cur" rel="20">20</a>
	    					<a class="num_item" rel="30">30</a>
	    					<a class="num_item" rel="50">50</a>
	    					<div class="clear"></div>
	    				</div>
	    			</div>
	    			<div class="clear"></div>
	    		</div>
    		</div>
	    	<div class="qn_btn">
	    		<a id="submitButton">开始查询</a>
	    	</div>
    	</form>
    	<div id="customerDiv" class="pcd">
			<div class="pcd_header">客户列表<a class="pcd_cancel"></a></div>
			<div class="pcd_search">
		   		<div class="pcd_search_icon"></div>
		   		<div class="pcd_searc_input">
		   			<input type="text" class="pcd_search_input" id="cdSearchInput" placeholder="名称/编号/助记符/拼音" />
		   		</div>
		   	</div>
		   	<div class="pcd_loading"><img src="../image/loading.gif" style="width:16px;height:16px;" /></div>
		   	<div class="pcd_items" id="customerItems" flag="0"></div>
		</div>
    </div>
  </body>
	<script type="text/javascript">
		var cjson = [];
   		$(function(){
   			$("#beginDate").on('click', function(){
    			window.agent.datePicker('beginDate', $('input[name=businessdate]').val());
    		});	
    		$("#endDate").on('click', function(){
    			window.agent.datePicker('endDate', $('input[name=businessdate1]').val());
    		});
    		$('#submitButton').on('touchstart', function(e){
    			$(this).css("background", "-webkit-gradient(linear, 0% 0, 0% 100%, from(#ff801a), to(#ffa442))");
    		});
    		$('#submitButton').on('touchmove', function(e){
    			$(this).css("background", "-webkit-gradient(linear, 0% 0, 0% 100%, from(#ffa442), to(#ff801a))");
    		});
    		$('#submitButton').on('touchend', function(e){
    			$(this).css("background", "-webkit-gradient(linear, 0% 0, 0% 100%, from(#ffa442), to(#ff801a))");
    		});
    		$('#rowsNum a').on('click', function(){
    			$("#rowsNum a").removeClass("num_item_cur");
    			$(this).addClass("num_item_cur");
    			$("input[name=rows]").val($(this).attr("rel"));
    		});
    		$("#customer").on('click', function(){
    			$("#customerDiv").show().animate({
    				left:"0%",
    				opacity:1,
    				width:"100%"
    			}, 
    			250, 
    			'easy-in', 
    			function(){
    				var flag = $("#customerItems").attr("flag");
    				if(flag == "0"){
    					$(".pcd_loading").show();
    					$("#customerItems").attr("flag", "1")
    					$.ajax({
    						url:'getCustomerList.do',
    						type:'post',
    						dataType:'json',
    						data:{uid:'${uid}'},
    						success:function(json){
    							cjson = json;
    							$(".pcd_loading").hide();
    							for(var i in json){
    								var j = json[i];
    								$('<div class="pcd_item" cid="'+j.id+'">'+j.name+'</div>').appendTo("#customerItems");
    							}	
    						}
    					});
    				}
    			});
    		});
    		$(".pcd_cancel").on('click', function(){
    			$(this).parent().parent().animate({
    				left:"100%",
    				opacity:0,
    				width:"0%"
    			}, 250, 'easy-out', function(){$(this).hide()});
    		});
    		$(".pcd_item").live('touchstart', function(){
    			$(this).css({"background":"#555555", "color":"#ffffff"});
    		});
    		$(".pcd_item").live('touchmove', function(){
    			$(this).css({"background":"#ffffff", "color":""});
    		});
    		$(".pcd_item").live('touchend', function(){
    			$(this).css({"background":"#ffffff", "color":""});
    		});	
    		$(".pcd_item").live('click', function(){
    			var id = $(this).attr("cid");
    			var name = $(this).text();
    			$("#customer .qn_extend_l").html(name);
    			$("input[name=customerid]").val(id);
    			$(".pcd_cancel").click();
    		});
    		$("#cdSearchInput").on('keyup', function(){
    			var con = $(this).val();
    			if($("#customerItems").html() == "" && con == ""){
    				for(var i in cjson){
    					var j = json[i];
    					$('<div class="pcd_item" cid="'+j.id+'">'+j.name+'</div>').appendTo("#customerItems");
    				}
    				return ;
    			}
    			$("#customerItems").html("");
    			var tmpJson = [];
    			for(var i in cjson){
    				var j = cjson[i];
    				if(j.id.indexOf(con)>-1 || j.name.indexOf(con)>-1 || j.spell.indexOf(con)>-1 || j.pinyin.indexOf(con)>-1){
    					$('<div class="pcd_item" cid="'+j.id+'">'+j.name+'</div>').appendTo("#customerItems");
    				}
    			}
    		});
    		$("#submitButton").on('click', function(){
    			var querystring = $("#queryForm").serialize();
    			location.href = "orderTrackPage.do?"+ querystring;
    		});
   		});
   		function setDate(id, sdate){
    		if(id == "beginDate"){
    			$("#beginDate .qn_extend_l").html(sdate);
    			$('input[name=businessdate]').val(sdate);
    		}
    		else if(id == "endDate"){
    			$("#endDate .qn_extend_l").html(sdate);
    			$("input[name=businessdate1]").val(sdate);
    		}
    	}
	</script>
</html>
