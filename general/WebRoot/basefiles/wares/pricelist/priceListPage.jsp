<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>价格套</title>
    <%@include file="/include.jsp" %> 
  </head>
  
  <body>
    <div class="easyui-panel" data-options="fit:true">
    	<div class="easyui-layout" data-options="fit:true">
      		<div title="价格套列表" data-options="region:'west',split:true" style="width:200px;">
		     	<table id="pricelist-table-list"></table>
		    </div>
		    <div title="价格套对应商品列表" data-options="region:'center',split:true,border:true">
		    	<div id="wares-query-showGoodsInfoToPriceList">
		    		<!--<div id="pricelist-button-layout"></div>  -->
					<input type="hidden" id="pricelist-opera"/>
					<input type="hidden" id="goodsInfo-index-goodspriceList"/>
					<input type="hidden" id="goodsInfo-oldTaxPrice-priceList"/>
					<input type="hidden" id="goodsInfo-oldPrice-priceList"/>
		    		<form action="" id="wares-form-goodsInfoToPriceListQuery" method="post" style="display:none; padding-left: 5px; padding-top: 2px;">
                        <span id="goodsInfoToPrice-query-advanced"></span>
                        商品编码:<input name="goodsid" style="width:100px" />
			   			商品名称:<input name="goodsname" style="width:200px" />
			    		<a href="javaScript:void(0);" id="wares-query-queryGoodsInfoToPriceList" class="button-qr">查询</a>
			    		<a href="javaScript:void(0);" id="wares-query-reloadGoodsInfoToPriceList" class="button-qr">重置</a>
		    		</form>
	    		</div>
	    		<table id="wares-table-goodsInfoToPriceList"></table>
			</div>
    	</div>
    </div>
    <div id="wares-dialog-priceAdd"></div>
    <script type="text/javascript">
    	var pricelist_AjaxConn = function (Data, Action, Str) {
    		if(null != Str && "" != Str){
    			loading(Str);
    		}
		    var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false,
		        success:function(data){
		        	loaded();
		        }
		    })
		    return MyAjax.responseText;
		}
		
		var priceList_editIndex = undefined;
   		function priceList_endEditing(){
   			if (priceList_editIndex == undefined){
   				return true
   			}else{return false;}
   		}
		//制保留num位小数，如：2，会在2后面补上00.即2.00
		function toDecimal(x,num) {
			var f = parseFloat(x);
			var f = Math.round(x*100)/100;
			var s = f.toString();
			var rs = s.indexOf('.');
			if (rs < 0) {
				rs = s.length;
				s += '.';
			}
			while (s.length <= rs + num) {
				s += '0';
			}
			return s;
		}  
		
		//1含税单价或2无税单价改变计算对应数据
   		function pricelist_taxpriceChange(row){
   			var rowIndex = $dgGoodsInfoToPriceList.datagrid("getRowIndex",row);
   			var et = $dgGoodsInfoToPriceList.datagrid('getEditor', {index : rowIndex,field : 'taxprice'});
   			var ep = $dgGoodsInfoToPriceList.datagrid('getEditor', {index : rowIndex,field : 'price'});
   			if(et != null){
				var taxprice = Number(et.target[0].value);
				var oldTaxPrice = Number(et.oldHtml);
				if(taxprice != oldTaxPrice){
					var ret = pricelist_AjaxConn({type:'1',taxtypeRate:row.taxtypeRate,taxprice:taxprice},'basefiles/getPriceChanger.do');
		   			var retJson = $.parseJSON(ret);
		   			$(ep.target).numberbox('setValue',formatterMoney(retJson.price));
				}
			}
   		}
		
		$(function(){
			//加载按钮
			$dgpricelist = $("#wares-table-goodsInfoToPriceList");
			$("#pricelist-button-layout").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/basefiles/priceListEditBtn.do">
						{
				 			type:'button-edit',//修改
				 			handler:function(){
				 				var row = $dgpricelist.datagrid('getSelected');
			                	if (row == null) {
	           						$.messager.alert("提醒","请选择行!");
	           						return false;
	           					}
					            var rowIndex = $dgpricelist.datagrid('getRowIndex', row);
				                if(priceList_endEditing()){
				                	$("#goodsInfo-oldTaxPrice-priceList").val(row.taxprice);
					                $("#goodsInfo-oldPrice-priceList").val(row.price);
				                	$("#goodsInfo-index-goodspriceList").val(rowIndex);
									$dgpricelist.datagrid('beginEdit', rowIndex);
									priceList_editIndex = rowIndex;
								}
								$("#pricelist-button-layout").buttonWidget('setDataID',{id:row.goodsid,type:'edit'});
				 			}
			 			},
					</security:authorize>
					<security:authorize url="/basefiles/priceListSaveBtn.do">
		 			{
			 			type:'button-save',//保存
			 			handler:function(){
			 				var row = $dgpricelist.datagrid('getSelected');
           					if (row == null) {
           						$.messager.alert("提醒","请选择行!");
           						return false;
           					}
           					var index = $dgpricelist.datagrid('getRowIndex', row);
		                	var et = $dgpricelist.datagrid('getEditor', {index:index,field:'taxtype'});
							if(et == null){
								return false;
							}
				            var taxtypeName = $(et.target).widget("getText");
				   			$dgpricelist.datagrid('getRows')[index]['taxtypeName'] = taxtypeName;
				   			$dgpricelist.datagrid('endEdit', index);
				   			priceList_editIndex = undefined;
				   			var effectRow = new Object();
				   			if ($dgpricelist.datagrid('getChanges').length){
				   				var updated = $dgpricelist.datagrid('getChanges',"updated");
				                effectRow["updated"] = JSON.stringify(updated);
				                var ret = pricelist_AjaxConn(effectRow,'basefiles/editGoodsPriceList.do','提交中..');
					   			var retJson = $.parseJSON(ret);
					   			if(retJson.flag){
					   				$.messager.alert("提醒","修改成功!");
					   			}
					   			else{
					   				$.messager.alert("提醒","修改失败!");
					   			}
				   			}
				   			else{
				   				$.messager.alert("提醒","数据无修改!");
				   			}
				   			$("#pricelist-button-layout").buttonWidget('setDataID',{id:row.goodsid,type:'view'});
				   			$dgpricelist.datagrid('clearSelections');
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/priceListViewBtn.do">
		 			{
			 			type:'button-view',//查看
			 			handler:function(){
			 				var row = $dgpricelist.datagrid('getSelected');
           					if (row == null) {
           						$.messager.alert("提醒","请选择行!");
           						return false;
           					}
           					top.addOrUpdateTab('basefiles/showGoodsInfoPage.do?type=view&id='+row.goodsid,'商品档案');
			 			}
		 			},
		 			</security:authorize>
					{}
				],
				model:'base',
				type:'view',
				taburl:'/basefiles/showPriceListPage.do',
				datagrid:'wares-table-goodsInfoToPriceList',
				tname:'t_base_goods_info_price',
				id:''
			});
			//加载价格套列表
			$("#pricelist-table-list").datagrid({ 
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		idField:'code',
	  	 		singleSelect:true,
			    url:'basefiles/priceList.do?type=price_list',
			    toolbar: [{
					iconCls: 'button-add',
					handler: function(){
						$('#wares-dialog-priceAdd').dialog({  
						    title: '价格套【新增】',
						    width: 300,  
						    height: 200,  
						    closed: false,  
						    cache: false,  
						    href: 'basefiles/showPriceAddPage.do',  
						    modal: true
						});
					}
				},'-'],
				columns:[[
					{field:'code',title:'编号'},
					{field:'codename',title:'名称'},
					{field:'state',title:'状态',
						formatter:function(val){
							if(val == "1"){
								return "启用";
							}
							else{
								return "禁用";
							}
						}
					}
				]],
		    	onClickRow:function(rowIndex, rowData){
		    		$("#wares-form-goodsInfoToPriceListQuery").show();
		    		$dgGoodsInfoToPriceList = $('#wares-table-goodsInfoToPriceList');
					var url = 'basefiles/showGoodsInfoByPriceList.do?code='+rowData.code;
				    $dgGoodsInfoToPriceList.datagrid({
				    	fit:true,
						title:'',
						columns:[[
							{field:'goodsid',title:'商品编码',width:50,sortable:true},
		     				{field:'goodsname',title:'商品名称',width:120,sortable:true},
							{field:'goodsbrand',title:'商品品牌',width:70,sortable:true,
								formatter:function(val,rowData,rowIndex){
									return rowData.goodsbrandname;
								}
							},
							{field:'taxprice',title:'含税单价',width:80,align:'right',
								editor:{
					  					type:'numberbox',
					  					options:{
					  						precision:2,
					  						max:999999999999,
					  						min:0,
					  						onChange:function(newValue,oldValue){
					  							var row = $dgGoodsInfoToPriceList.datagrid("getSelected");
					  							pricelist_taxpriceChange(row);
					  						}
					  					}
				  					},
				  					formatter:function(val,rowData,rowIndex){
				  						if(val != "" && val != null){
				  							return toDecimal(val,2);
				  						}
				  					}
				  				},
				  				{field:'taxtype',title:'税种',width:100,
				  					formatter:function(val,rowData,rowIndex){
				  						return rowData.taxtypeName;
				  					},
				  					editor:{
					  					type:'comborefer',
					        		  	options:{
							    			name:'t_base_goods_info_price',
							    			col:'taxtype',
							    			singleSelect:true
							    		}
					  				}
				  				},
				  				{field:'price',title:'无税单价',width:85,align:'right',
				  					editor:{
				  						type:'numberbox',
				  						options:{
				  							precision:2,
				  							max:999999999999,
				  							min:0,
				  							disabled:true
				  						}
				  					},
				  					formatter:function(val,rowData,rowIndex){
				  						if(val != "" && val != null){
				  							return toDecimal(val,2);
				  						}
				  					}
				  				},
				  				{field:'remark',title:'备注',width:150,editor:'text'}
				     	]],
						toolbar:'#wares-query-showGoodsInfoToPriceList',
						method:'post',
						rownumbers:true,
						pagination:true,
						idField:'goodsid',
						singleSelect:true,
						url:url,
						onDblClickRow:function(rowIndex, rowData){
							var row = $('#wares-table-goodsInfoToPriceList').datagrid('getSelected');
           					if (row == null) {
           						$.messager.alert("提醒","请选择行!");
           						return false;
           					}
           					top.addOrUpdateTab('basefiles/showGoodsInfoPage.do?type=view&id='+rowData.goodsid+'&listType=1','商品档案');
						},
						onClickRow:function(rowIndex, rowData){
		   					var index = $("#goodsInfo-index-goodspriceList").val();
		   					if(index != null && index != ""){
		   						if(!priceList_endEditing()){
			   						$('#wares-table-goodsInfoToPriceList').datagrid('selectRow',index);
			   					}
		   					}
		   				}
					});
					$("#wares-form-goodsInfoToPriceListQuery")[0].reset();
					$("#wares-table-goodsInfoToPriceList").datagrid("load",{});
		    	}
			});
			//查询
			$("#wares-query-queryGoodsInfoToPriceList").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#wares-form-goodsInfoToPriceListQuery").serializeJSON();
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#wares-table-goodsInfoToPriceList").datagrid("load",queryJSON);
			});
			
			//重置按钮
			$("#wares-query-reloadGoodsInfoToPriceList").click(function(){
				$("#wares-form-goodsInfoToPriceListQuery")[0].reset();
				$("#wares-table-goodsInfoToPriceList").datagrid("load",{});
				
			});
			//通用查询组建调用
			$("#goodsInfoToPrice-query-advanced").advancedQuery({
				//查询针对的表
		 		name:'base_goods_info_price',
		 		//查询针对的表格id
		 		datagrid:'wares-table-goodsInfoToPriceList'
			});
     });
    </script>
  </body>
</html>
