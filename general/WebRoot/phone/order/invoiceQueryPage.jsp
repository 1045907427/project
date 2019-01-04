<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
  <head>
  	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0"/>
    <title>申请抽单</title>
	<script type="text/javascript" src="../zepto.js"></script>
	<script type="text/javascript" src="../zepto.animate.js"></script>
	<link type="text/css" href="query.css" rel="stylesheet" />
	</style>
    <script type="text/javascript">
    	;(function($){
		  	$.extend($.fn, {
		    	select: function(options){
		    		var id = "select"+ new Date().getTime();
		    		$('<div id="'+(id+"_bg")+'"></div>').css({"position":"absolute", "width":"100%", "top":0, "left":0, "height":"100%", "background":"#333333", "opacity":"0.5"}).appendTo('body');
		    		$('<div id="'+id+'"></div>').css({"position":"absolute","width":"180px","background":"#ffffff","border":"2px solid #aaaaaa"}).appendTo('body');
		      		for(var i in options){
		      			var item = options[i];
		      			var itemId = "titem_"+item.id;
		      			$('<div id="'+itemId+'" rel="'+item.id+'">'+item.name+'</div>').css({"padding":"8px 10px"}).appendTo('#'+id);
		      			$('#'+itemId).on('touchstart', function(e){
		      				$(this).css("background", "#efefef");
		      			});
		      			$('#'+itemId).on('touchend', function(e){
		      				$(this).css("background", "");
		      			});
		      			$('#'+itemId).on('click', function(e){
		      				var sid = $(this).attr('rel');
		      				var sname = $(this).text();
		      				$('input[name=sourcetype]').val(sid);
		      				$('#billType .qn_extend_l').html(sname);
		      				$('#'+id+'_bg').remove();
		      				$('#'+id).remove();
		      			});
		      		}
		      		$('#'+ id).css({"padding":"6px 5px", "left":($(window).width() - 180)/2, "top":($(window).height() - $('#'+ id).height())/2});
		      		$('#'+id+'_bg').on('click', function(e){
		      			$(this).remove();
		      			$('#'+id).remove();
		      		});
		   		}
		  	});
		})(Zepto);
    	$(function(){ 
    		setTimeout(function(){
    			$(".qn_main").height($(window).height()-16);
    		}, 500);
    		$('#submitButton').on('touchstart', function(e){
    			$(this).css("background", "-webkit-gradient(linear, 0% 0, 0% 100%, from(#ff801a), to(#ffa442))");
    		});
    		$('#submitButton').on('touchmove', function(e){
    			$(this).css("background", "-webkit-gradient(linear, 0% 0, 0% 100%, from(#ffa442), to(#ff801a))");
    		});
    		$('#submitButton').on('touchend', function(e){
    			$(this).css("background", "-webkit-gradient(linear, 0% 0, 0% 100%, from(#ffa442), to(#ff801a))");
    		});
    		$('#billType').on('click', function(e){
    			$(this).select([{"id":1, "name": "销售发货回单"},{"id":2, "name": "销售退货通知单"},{"id":3, "name": "冲差单"}]);
    		});
    		$("#beginDate").on('click', function(){
    			window.agent.datePicker('beginDate', $('input[name=businessdate1]').val());
    		});	
    		$("#endDate").on('click', function(){
    			window.agent.datePicker('endDate', $('input[name=businessdate2]').val());
    		});
    		$("#rBeginDate").on('click', function(){
    			window.agent.datePicker('rBeginDate', $('input[name=duefromdate1]').val());
    		});
    		$("#rEndDate").on('click', function(){
    			window.agent.datePicker('rEndDate', $('input[name=duefromdate2]').val());
    		});
    	});
    	function setDate(id, sdate){
    		if(id == "beginDate"){
    			$("#beginDate .qn_extend_l").html(sdate);
    			$('input[name=businessdate1]').val(sdate);
    		}
    		else if(id == "endDate"){
    			$("#endDate .qn_extend_l").html(sdate);
    			$("input[name=businessdate2]").val(sdate);
    		}
    		else if(id == "rBeginDate"){
    			$("#rBeginDate .qn_extend_l").html(sdate);
    			$("input[name=duefromdate1]").val(sdate);
    		}
    		else if(id == "rEndDate"){
    			$("#rEndDate .qn_extend_l").html(sdate);
    			$("input[name=duefromdate2]").val(sdate);
    		}
    	}
    </script>
  </head>
  <body>
    <div class="qn_main">
    	<form id="queryForm" action="">
    		<input type="hidden" name="page" value="1" />
    		<input type="hidden" name="rows" value="20" />
    		<input type="hidden" name="sort" value="businessdate" />
    		<input type="hidden" name="order" value="desc" />
    		<input type="hidden" name="uid" value="${uid }" />
	    	<div class="qn_border">
	    		<div class="qn_item qn_bt">
	    			<div class="qn_fl">业务日期</div>
	    			<div class="qn_extend" id="beginDate">
	    				<div class="qn_extend_l"></div>
	    				<div class="qn_arrow_grey r"></div>
	    			</div>
	    			<div class="clear"></div>
	    			<input type="hidden" name="businessdate1" value="" />
	    		</div>
	    		<div class="qn_item">
	    			<div class="qn_fl">结束日期</div>
	    			<div class="qn_extend" id="endDate">
	    				<div class="qn_extend_l"></div>
	    				<div class="qn_arrow_grey r"></div>
	    			</div>
	    			<div class="clear"></div>
	    			<input type="hidden" name="businessdate2" value="" />
	    		</div>
	    	</div>
	    	<div class="qn_border qn_mt">
	    		<div class="qn_item">
	    			<div class="qn_fl">客户名称</div>
	    			<div class="qn_extend" id="customer">
	    				<div class="qn_extend_l" style="overflow:hidden;">请选择客户</div>
	    				<div class="qn_arrow_grey r"></div>
	    			</div>
	    			<div class="clear"></div>
	    			<input type="hidden" name="customerid" value="" />
	    		</div>
	    	</div>
	    	<div class="qn_border qn_mt">
	    		<div class="qn_item qn_bt">
	    			<div class="qn_fl">应收日期</div>
	    			<div class="qn_extend" id="rBeginDate">
	    				<div class="qn_extend_l"></div>
	    				<div class="qn_arrow_grey r"></div>
	    			</div>
	    			<div class="clear"></div>
	    			<input type="hidden" name="duefromdate1" value="" />
	    		</div>
	    		<div class="qn_item">
	    			<div class="qn_fl">结束日期</div>
	    			<div class="qn_extend" id="rEndDate">
	    				<div class="qn_extend_l"></div>
	    				<div class="qn_arrow_grey r"></div>
	    			</div>
	    			<div class="clear"></div>
	    			<input type="hidden" name="duefromdate2" />
	    		</div>
	    	</div>
	    	<div class="qn_border qn_mt">
	    		<div class="qn_item qn_bt">
	    			<div class="qn_fl">单据类型</div>
	    			<div class="qn_extend" id="billType">
	    				<div class="qn_extend_l"></div>
	    				<div class="qn_arrow_grey"></div>
	    			</div>
	    			<div class="clear"></div>
	    			<input type="hidden" name="sourcetype" />
	    		</div>
	    		<div class="qn_item">
	    			<div class="qn_fl">单据编号</div>
	    			<div class="qn_extend">
	    				<div class="qn_extend_l"><input type="text" class="qn_inp" name="id" /></div>
	    				<div class="qn_arrow_grey r"></div>
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
		   	<div class="pcd_items" id="customerItems" flag="0">
		   		
		   	</div>
		</div>
    </div>
    <script type="text/javascript">
    	var cjson = [];
    	var conLoad = true;
    	var isLoading = false;
    	;(function(){
    		$("#customer").on('click', function(){
    			$("#customerDiv").show().animate({
    				left:"5%",
    				opacity:1,
    				width:"95%"
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
    								$('<div class="pcd_item" cid="'+j.id+'">['+j.id+']'+j.name+'</div>').appendTo("#customerItems");
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
    					$('<div class="pcd_item" cid="'+j.id+'">['+j.id+']'+j.name+'</div>').appendTo("#customerItems");
    				}
    				return ;
    			}
    			$("#customerItems").html("");
    			var tmpJson = [];
    			for(var i in cjson){
    				var j = cjson[i];
    				if(j.id.indexOf(con)>-1 || j.name.indexOf(con)>-1 || j.spell.indexOf(con)>-1 || j.pinyin.indexOf(con)>-1){
    					$('<div class="pcd_item" cid="'+j.id+'">['+j.id+']'+j.name+'</div>').appendTo("#customerItems");
    				}
    			}
    		});
    		$("#submitButton").on('click', function(){
    			$(".re_main").height($(window).height()).show();
    			$(".qn_main").animate({
    				"margin-top":0-$(window).height()
    			}, 500, 'easy-in', function(){$(".qn_main").hide()});
    			$("input[name=page]").val("1");
    			var qstring = $("#queryForm").serialize();
    			isLoading = true;
    			$.ajax({
    				url:'getInvoiceBillList.do',
    				dataType:'json',
    				type:'post',
    				data:qstring,
    				success:function(json){
    					isLoading = false;
    					var rows = json.rows;
    					var total = json.total;
    					if(total == 0){
    						$(".re_items").html("未查询到任何符合条件的数据。");
    						$(".re_loading").hide();
    						return ;
    					}
    					var page = $("input[name=page]").val();
    					var t = Number(page)*20;
    					if(t >= total){
    						$(".re_loading").hide();
    						conLoad = false;
    					}
    					else{
    						$(".re_loading").html("更多");
    					}
    					for(var i in rows){
    						var row = rows[i];
    						var itemHtml = [
    							'<div class="re_item" id="'+row.id+'" type="'+row.billtype+'" cid="'+row.customerid+'" cname="'+row.customername+'">',
    							'<div>客户名称:'+row.customerid + row.customername+'</div>',
    							'<div>单据类型:'+getBillTypeName(row.billtype)+'</div>',
    							'<div>单据编号:'+row.id+'</div>',
    							'<div>制&nbsp;&nbsp;单&nbsp;&nbsp;人:'+ row.addusername + '</div>',
    							'<div>业务日期:'+row.businessdate+'</div>',
    							'</div>'
    						].join('');
    						$(itemHtml).appendTo(".re_items");
    					}
    				}
    			});
    		});
    		$(".re_item").live('touchstart', function(){
    			$(this).css("background","#efefef");
    		});
    		$(".re_item").live('touchmove', function(){
    			$(this).css("background","");
    		});
    		$(".re_item").live('touchend', function(){
    			$(this).css("background","");
    		});
    		$(".re_loading").live('click', function(){
    			$(this).html("数据加载中...");
    			getMorePage();
    		});
    	})();
    </script>
    <div class="re_main">
    	<div class="re_up"><img src="../image/arrow_up.png" /></div>
    	<div class="re_items_head">
    		<div>客户编号/客户名称/单据类型/单据编号/客户业务员/制单人/业务日期</div>
    	</div>
    	<div class="re_items">
    		
    	</div>
    	<div class="re_loading">
    		数据加载中...
    	</div>
    </div>
    <style type="text/css">
    	.re_main{position:relative;display:none;padding:0;height:100%;overflow-y:auto;}
    	.re_up{position:fixed;right:8px;bottom:8px;}
    	.re_items_head{padding:5px 8px;line-height:22px;border-bottom:2px solid #cfcfcf;}
    	.re_item{padding:5px 8px;line-height:22px;border-bottom:2px solid #cfcfcf;}
    	.re_loading{text-align:center;line-height:40px;background:#f5f5f5;}
    	.de_main{position:absolute;z-index:999;width:0;height:100%;top:0;left:100%;background:#ffffff;display:none;overflow-x:hidden;}
    	.de_head{position:fixed;top:0;left:0;width:100%;background:#ffffff;}
    	.de_header{text-align:center;height:40px;width:100%;line-height:40px;color:white;position:relative;background:-webkit-gradient(linear, 0% 0, 0% 100%, from(#ffa442), to(#ff801a));}
    	.de_cancel{position:absolute;width:30px;height:30px;background:url(../image/cancel.png) 0 0 no-repeat; top:11px;left:6px;background-size:60% 60%;}
    	.de_btn{position:absolute;top:6px;right:6px;height:22px;line-height:24px;color:#333333;font-size:12px;padding:2px 5px;border:1px solid #afafaf;border-radius:3px;background:#efefef;}
    	.de_loading{text-align:center;line-height:40px;}
    	.de_total{border-bottom:2px solid #afafaf;line-height:22px;}
    	.de_items{margin-top:0px;}
    	.de_item{padding:5px 8px;line-height:22px;border-bottom:2px solid #cfcfcf;}
    	.de_item div{width:100%;clear:both;}
    	.de_item span{width:80px;float:left;}
    	.de_customer_s{width:300px;overflow:hidden;}
    </style>
    <script type="text/javascript">
    	$(function(){
    		$(".re_up").on('click', function(){
    			$(".qn_main").show();
    			$(".re_items").html("");
    			$(".qn_main").animate({
    				"margin-top":0
    			}, 500, 'easy-in', function(){$(".re_main").hide();});
    		});
    	});
    	function getBillTypeName(billtype){
    		if(billtype == "1"){
    			return "销售发货回单";
    		}
    		else if(billtype == "2"){
    			return "销售退货通知单";
    		}else if(billtype == "3"){
    			return "冲差单";
    		}
    		return billtype;
    	}
    	function getMorePage(){
    		if(isLoading) return ;
    		if(!conLoad) return ;
    		$(".re_loading").show();
    		var page = $("input[name=page]").val();
    		$("input[name=page]").val(Number(page)+1);
    		var qstring = $("#queryForm").serialize();
    		isLoading = true;
    		$.ajax({
    			url:'getInvoiceBillList.do',
    			dataType:'json',
    			type:'post',
    			data:qstring,
    			success:function(json){
    				isLoading = false;
    				var rows = json.rows;
    				var total = json.total;
    				var page = $("input[name=page]").val();
    				var t = Number(page)*20;
    				if(t >= total){
    					$(".re_loading").hide();
    					conLoad = false;
    				}
    				else{
    					$(".re_loading").html("更多");
    				}
    				for(var i in rows){
    					var row = rows[i];
    					var itemHtml = [
    						'<div class="re_item" id="'+row.id+'" type="'+row.billtype+'" cid="'+row.customerid+'" cname="'+row.customername+'">',
    						'<div>客户名称:'+row.customerid + row.customername+'</div>',
    						'<div>单据类型:'+getBillTypeName(row.billtype)+'</div>',
    						'<div>单据编号:'+row.id+'</div>',
    						'<div>制&nbsp;&nbsp;单&nbsp;&nbsp;人:' + row.addusername + '</div>',
    						'<div>业务日期:'+row.businessdate+'</div>',
    						'</div>'
    					].join('');
    					$(itemHtml).appendTo(".re_items");
    				}
    			}
    		});
    	}
    </script>
    <div class="de_main">
    	<div class="de_head">
    		<div class="de_header">
	    		<div class="de_cancel"></div>
	    		<a class="de_btn" id="invoiceBtn">申请抽单</a>
	    		单据明细
    		</div>
    	</div>
    	<div style="margin-top:40px;">
    		<div class="de_loading">数据加载中...</div>
		    <div class="de_total">
		    	<p>单据编号：<span class="de_id_span"></span></p>
		    	<p>单据类型：<span class="de_type_span"></span></p>
		    	<p>合计条数：<span class="de_num_span"></span> &nbsp;合计金额：<span class="de_amount_span"></span></p>
		    	<p>客户名称：<select class="de_customer_s"></select></p>
		    </div>
		</div>
	    <div class="de_items">
	    	
	    </div>
    </div>
    <script type="text/javascript">
    	$(function(){
    		$(".re_item").live('click', function(){
    			var id = $(this).attr("id");
    			var type = $(this).attr("type");
    			var cid = $(this).attr("cid");
    			var cname = $(this).attr("cname");
    			$(".de_main").show();
    			$(".de_loading").show();
    			$(".de_customer_s").html("").css("width", ($(window).width() - 100));
    			$(".de_main").animate({
    				left:0,
    				width:"100%"
    			}, 300, 'easy-in',function(){
    				$.ajax({
    					url:'getInvoiceBillDetail.do',
    					type:'post',
    					dataType:'json',
    					data:{id:id, type:type, cid: cid},
    					success:function(json){
	    					$(".de_loading").hide();
	    					if(json != null){
	    						$("#invoiceBtn").attr("oid", json.id);
	    						$("#invoiceBtn").attr("type", json.type);
	    						$(".de_id_span").html(json.id);
	    						$(".de_type_span").html(getBillTypeName(json.type));
	    						$(".de_num_span").html(json.size);
	    						$(".de_amount_span").html(json.total);
	    						var cus = json.customer;
	    						if(cus.length > 0){
	    							for(var i in cus){
	    								var j = cus[i];
	    								$('<option value="'+j.id+'">'+j.name+'</option>').appendTo('.de_customer_s');
	    							}
	    						}
	    						else{
	    							$('<option value="'+cid+'">'+cname+'</option>').appendTo('.de_customer_s');
	    						}
	    						var rows = json.rows;
	    						for(var i in rows){
	    							var row = rows[i];
	    							var itemHtml = [
	    								'<div class="de_item">',
	    								'<div>'+row.goodsid + row.goodsname+'</div>',
	    								'<div><span>箱装量</span>'+row.boxnum+'</div>',
	    								'<div><span>单价</span>'+row.taxprice+'</div>',
	    								'<div><span>数量</span>'+row.unitnum + " " + row.unitname+'</div>',
	    								'<div><span>金额</span>'+row.taxamount+'</div>',
	    								'<div>'+getInvoice(row.isinvoice)+'</div>',
	    								'</div>'
	    							].join('');
	    							$(itemHtml).appendTo(".de_items");
	    						}
	    					}
    					}
    				});
    			});
    		});
    		$(".de_btn").on('touchstart', function(){
    			$(this).css({"background":"#dfdfdf"});
    		});
    		$(".de_btn").on('touchmove', function(){
    			$(this).css({"background":""});
    		});
    		$(".de_btn").on('touchend', function(){
    			$(this).css({"background":""});
    		});
    		$(".de_cancel").on('click', function(){
    			$(".de_main").animate({
    				left:"100%",
    				width:"0%"
    			}, 300, 'easy-out', function(){$(".de_items").html("");$(".de_main").hide();});
    		});
    		$("#invoiceBtn").on('click', function(){
    			var id = $(this).attr("oid");
    			var cid = $(".de_customer_s").val();
    			var cname = $(".de_customer_s option").not(
    				function(){
    					if(!this.selected){
    						return this;
    					}
    				});
    			var type = $(this).attr("type");
    			if(confirm("确定生成销售发票？\n编号："+id+"\n开票客户："+cname.text())){
    				$(this).html("生成中...");
    				$.ajax({
    					url:'makeInvoice.do',
    					dataType:'json',
    					type:'post',
    					data:{id:id, type:type, cid:cid, uid:'${uid}'},
    					success:function(json){
    						$("#invoiceBtn").html("申请抽单");
    						if(json.flag){
    							$(".de_cancel").click();
    							$("#"+id).hide();
    							window.agent.toast("生成成功，发票单据号："+ json.id);
    						}
    						else{
    							window.agent.toast("生成失败，"+ json.msg);
    						}
    					}
    				});
    			}
    		});
    	});
    	function getInvoice(str){
    		if(str == "0"){
    			return "未开票";
    		}
    		else if(str == "1"){
    			return "已开票";
    		}
    		return str;
    	}
    </script>
  </body>
</html>
