<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hd.agent.common.util.BillGoodsNumDecimalLenUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <title>新品登录单页面</title>
	<%@include file="/include.jsp" %>
  </head>
  
  <body>
  <style type="text/css">
	  .len30 {
		  width: 30px;
	  }
	  .len200 {
		  width: 200px;
	  }
  </style>
  	<input type="hidden" id="oa-backid-oagoodsPage" value="${param.id }" />
  	<input type="hidden" id="oa-id-oagoodsViewPage" value="${param.id }" />
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center',border:false">
    		<div id="oa-panel-oagoodsPage">
    		</div>
    	</div>
    </div>
	<div class="easyui-menu" id="oa-contextMenu-oagoodsPage">
		<div id="oa-addRow-oagoodsPage" data-options="iconCls:'button-add'">添加</div>
		<div id="oa-editRow-oagoodsPage" data-options="iconCls:'button-add'">编辑</div>
		<div id="oa-removeRow-oagoodsPage" data-options="iconCls:'button-delete'">删除</div>
	</div>
    <div id="oa-dialog-oagoodsPage"></div>
	<script type="text/javascript">

	var minLength = 10;	// 商品明细默认行数
	
	var goods_url = '';
    var goods_type = '${param.type }';
    var goods_id = '${param.id }';
    var goods_step = '${param.step }';
    var goods_from = '${param.from }';

	if(goods_type == 'add') {
		goods_url = 'oa/oagoodsAddPage.do';
	} else if(goods_type == 'edit') {
		goods_url = 'oa/oagoodsEditPage.do?id=' + goods_id;
	} else if(goods_type == 'view' || goods_step == '99') {
		goods_url = 'oa/oagoodsViewPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }&billtype=${param.billtype }';
	} else if(goods_type == 'handle') {
        goods_url = 'oa/oagoodsHandlePage.do?id=${param.id }&step=${param.step }&type=handle&processid=${param.processid }&billtype=${param.billtype }';
    } else if(goods_type == 'print') {
        goods_url = 'oa/oagoodsPrintPage.do?id=${param.id }&type=print&processid=${param.processid }&billtype=${param.billtype }';
        window.location.href = goods_url;
	}
		
	var goodsDetailData = {};
    
    var cid = '${param.id }';

    var $dialog;
	
	$(function(){
	
		$("#oa-panel-oagoodsPage").panel({
			href: goods_url,
			cache: false,
			maximized: true,
			border: false,
			onLoad: function() {

                // 附件
                $attach.attach({
                    attach: true<c:if test="${param.type eq 'view' }"> && false</c:if>,
                    businessid: '${param.id }',
                    processid: '${param.processid }'
                });

                $comments.comments({
                    businesskey: '${param.id }',
                    processid: '${param.processid }',
                    type: 'vertical',
                    width: '120',
                    agree: null
                });

				$oa_datagrid.datagrid({ 
					//判断是否开启表头菜单
					columns: [[{field: 'id', hidden: true, formatter: function(value,row,index){return index;}},
								  {field: 'goodsid', title: '商品编码'/*, width: 80*/},
								   {field: 'goodsname', title: '商品名称'/*, width: 200*/},
								   {field: 'brandid', hidden: true},
								   {field: 'brandname', title: '品牌'/*, width: 140*/},
								   {field: 'barcode', title: '条形码'/*, width: 80*/},
								   {field: 'boxbarcode', title: '箱装条形码'/*, width: 80*/},
								   {field: 'goodssort', hidden: true},
								   {field: 'goodssortname', title: '商品分类'/*, width: 80*/},
								   {field: 'unitid', hidden: true},
								   {field: 'unitname', title: '单位'/*, width: 80*/},
								   {field: 'auxunitid', hidden: true},
								   {field: 'auxunitname', title: '辅单位'/*, width: 80*/},
								   {field: 'model', title: '规格型号'/*, width: 80*/},
								   {field: 'boxnum', title: '箱装量'/*, width: 80*/, align: 'right',
										formatter: function(value,row,index){
						        			return formatterMoney(value, <%=BillGoodsNumDecimalLenUtils.decimalLen %>);
							        	}
							        },
								   {field: 'totalweight', title: '箱重'/*, width: 80*/, align: 'right',
										formatter: function(value,row,index){
						        			return formatterMoney(value, 2);
							        	}
							       },
								   {field: 'glength', hidden: true,
										formatter: function(value,row,index){
						        			return formatterMoney(value, 2);
							        	}
							       },
								   {field: 'gwidth', hidden: true,
										formatter: function(value,row,index){
						        			return formatterMoney(value, 2);
							        	}
							       },
								   {field: 'gheight', hidden: true,
										formatter: function(value,row,index){
						        			return formatterMoney(value, 2);
							        	}
							       },
								   {field: 'totalvolume', title: '箱体积'/*, width: 80*/, align: 'right',
										formatter: function(value,row,index){
						        			return formatterMoney(value, 6);
							        	}
							       },
								   {field: 'storageid', hidden: true},
								   {field: 'storagename', title: '仓库'/*, width: 80*/},
								   {field: 'buytaxprice', title: '最高采购价'/*, width: 80*/, align: 'right',
										formatter: function(value,row,index){
						        			return formatterMoney(value, 6);
							        	}
							       },
								   {field: 'basesaleprice', title: '基准销售价'/*, width: 80*/, align: 'right',
										formatter: function(value,row,index){
						        			return formatterMoney(value, 6);
							        	}
							       },
								   {field: 'taxtype', hidden: true},
								   {field: 'taxname', title: '税种'/*, width: 80*/},
								   {field: 'taxrate', hidden: true},
                                   <c:forEach items="${priceList }" var="item" varStatus="idx">
                                        {field: 'price${idx.index + 1 }', title: '<c:out value="${item.codename}" />'/*, width: 80*/, align: 'right',
                                            formatter: function(value,row,index){
                                                return formatterMoney(value, 6);
                                            }
                                        },
                                        {field: 'profit${idx.index + 1 }', title: '<c:out value="${item.codename}" />毛利率'/*, width: 80*/, align: 'right',
                                            formatter: function(value,row,index){

                                                var price = row.price${idx.index + 1 };
                                                var cost1 = row.costaccountprice;
                                                var cost2 = row.buytaxprice;

                                                if(price == undefined || price == null || price == '') {
                                                    return '';
                                                }

                                                var cost = cost2;

                                                var profit = formatterMoney(price - cost, 2);
                                                if(isNaN(profit)) {
                                                    return '';
                                                }

                                                var rate = formatterMoney(profit * 100 / price, 2);
                                                if(isNaN(rate)) {
                                                    return '';
                                                }

                                                return rate + '%';
                                            }
                                        },
                                   </c:forEach>
								   {field: 'costaccountprice', title: '核算成本价'/*, width: 80*/, align: 'right', hidden: true,
										formatter: function(value,row,index){
						        			return formatterMoney(value);
							        	}
							       },
                                   {field: 'productfield', title: '产地'},
                                   {field: 'shelflife', title: '保质期',
                                   	    formatter: function(value, row, index){

                                   	    	var shelflifeunit = '';
                                   	    	if(row.shelflifeunit == 1) {
                                   	    		shelflifeunit = '天';
                                   	    	} else if(row.shelflifeunit == 2) {
                                   	    		shelflifeunit = '周';
                                   	    	} else if(row.shelflifeunit == 3) {
                                   	    		shelflifeunit = '月';
                                   	    	} else if(row.shelflifeunit == 4) {
                                   	    		shelflifeunit = '年';
                                   	    	}

                                   	    	if(value != undefined && shelflifeunit) {
                                   	    		return value + shelflifeunit;
                                   	    	}
                                   	    }
									},
								   {field: 'remark', title: '备注'/*, width: 180*/}
								]],
                    title: '商品明细清单',
					border: false,
					fit: true,
					rownumbers: true,
					showFooter: true,
					striped:true,
					singleSelect: true,
					<c:if test="${param.type ne 'add' }">
                        data: $.parseJSON('${list }'),
						onLoadSuccess: function(data) {

							for(var i = 0; i < data.total; i++) {
								var row = data.rows[i];
								row.id = i;
								$oa_datagrid.datagrid('updateRow', {index: i, row: row})
							}
							
							for(var i = data.total; i < minLength; i++) {
								$oa_datagrid.datagrid('appendRow', {});
							}
							
							if(data.total >= minLength) {
								$oa_datagrid.datagrid('appendRow', {});
							}
							
						},
					</c:if>
					<c:if test="${param.type ne 'view' }">
						onDblClickRow: function() {
							opeGoodsDetail();
						},
					</c:if>
					onRowContextMenu: function(e, rowIndex, rowData){
						e.preventDefault();
						<c:if test="${param.type == 'view' }">
							return false;
						</c:if>
						$line.datagrid('selectRow', rowIndex);
						var selectedRow = $line.datagrid('getSelected');
						
						$("#oa-contextMenu-oagoodsPage").menu('show', {  
							left:e.pageX,  
							top:e.pageY  
						});
						
						// 该行内容为空时，不能编辑
						if(selectedRow.goodsid == undefined) {
							$("#oa-contextMenu-oagoodsPage").menu('disableItem', editItem);
						} else {
							$("#oa-contextMenu-oagoodsPage").menu('enableItem', editItem);
						}
					}
				});

				<c:if test="${param.type == 'add' }">
					$oa_datagrid.datagrid("loadData", {'total': minLength, 'rows': [{},{},{},{},{},{},{},{},{},{} ]});
				</c:if>

				$('#oa-addRow-oagoodsPage').click(addGoodsDetail);
				$('#oa-editRow-oagoodsPage').click(editGoodsDetail);
				$('#oa-removeRow-oagoodsPage').click(removeGoodsDetail);
			}
		});
		
		if(goods_from == 'work') {

			$('#oa-buttons-oagoodsPage').hide();
		}

	});

	// 
    function addGoodsDetail(){
    
    	var index = -1;
    	var rows = $line.datagrid('getRows');
    	for(var i = 0; i < rows.length; i++) {
    		if(rows[i].goodsid == undefined || rows[i].goodsid == null) {
    		
    			index = i;
    			break;
    		}
    	}

        var did = 'd' + getRandomid();

		$('<div id="oa-' + did + '-oagoodsDetailAddPage-content"></div>').appendTo('#oa-dialog-oagoodsPage');
        $dialog = $('#oa-' + did + '-oagoodsDetailAddPage-content');
        $dialog.dialog({ //弹出新添加窗口
			title:'添加商品明细数据',
			maximizable:true,
			width:640,
			height:450,
			closed:false,
			modal:true,
			cache:false,
			resizable:true,
			href:'oa/oagoodsDetailAddPage.do?index=' + index,
			onClose:function(){
                $dialog.dialog("destroy");
                setTimeout(function() {

                    $dialog.remove();
                }, 100);
			},
			onLoad:function(){
                $("#oa-form-oagoodsDetailAddPage").focus();
			}
		});
    }
    
    function editGoodsDetail() {
    
		var row = $line.datagrid('getSelected');

		if(row.goodsid == undefined) {
			return false;
		}
		
		goodsDetailData = row;
		var index = row.id;

		$('<div id="oa-dialog-oagoodsDetailEditPage-content"></div>').appendTo('#oa-dialog-oagoodsPage');
		$("#oa-dialog-oagoodsDetailEditPage-content").dialog({ //弹出新添加窗口
			title:'修改商品明细数据',
			maximizable:true,
			width:640,
			height:450,
			closed:false,
			modal:true,
			cache:false,
			resizable:true,
			href: 'oa/oagoodsDetailEditPage.do?index=' + index + '&goodsid=' + row.goodsid + '&goodsname=' + row.goodsname + '&barcode=' + row.barcode + '&boxbarcode=' + row.boxbarcode,
			onClose:function(){
				$('#oa-dialog-oagoodsDetailEditPage-content').dialog("destroy");
			},
			onLoad: function(){
				$('#goods-oldgoodsid').val(row.goodsid);
				$('#goods-oldgoodsname').val(row.goodsname);
				$('#goods-oldbarcode').val(row.barcode);
				$('#goods-oldboxbarcode').val(row.boxbarcode);
			}
		});
    	return false;
    }
    
    function removeGoodsDetail() {
    
		var row = $line.datagrid('getSelected');

		if(row.goodsid == undefined) {
			return false;
		}

		var rows = $line.datagrid('getRows');
		var rowIndex = rows.length - 1;
		for(var i=0; i<rows.length; i++){
			var rowJson = rows[i];
			if(rowJson.goodsid == row.goodsid) {

				$line.datagrid('deleteRow', i);
				break;
			}
		}
		
		var recordCnt = $line.datagrid('getRows').length;
		
		for(var i = recordCnt; i < minLength; i++) {
			$line.datagrid('appendRow', {});
		}

    }

	function addDetail(go){ //添加新数据确定后操作，
	
		var flag = $("#oa-form-oagoodsDetailAddPage").form('validate');
		if(!flag){
			return false;
		}
				
		var form = $("#oa-form-oagoodsDetailAddPage").serializeJSON();
		var id =  parseInt(form.id);
		// form.totalvolume = parseFloat(form.glength) * parseFloat(form.gwidth) * parseFloat(form.gheight);

		var rows = $line.datagrid('getRows');
		var rowIndex = -1;
		for(var i = 0; i < rows.length; i++){
			var rowJson = rows[i];
			if(rowJson.goodsid == undefined){
				rowIndex = i;
				break;
			} else if(rowJson.id == form.id) {
				rowIndex = i;
				break;
			}
		}

		if(rowIndex == -1){
			rowIndex = rows.length;
			$line.datagrid('appendRow',{}); //如果是最后一行则添加一新行
		} 
		$line.datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
		
		var d = $line.datagrid('getRows');
		$line.datagrid("loadData", {'total': d.length, 'rows': d});
		for(var i = d.length; i < minLength; i++) {
			$oa_datagrid.datagrid('appendRow', {id: rowIndex});
		}
		if(d[d.length - 1].id) {
			$line.datagrid('appendRow', {id: rowIndex});
		}
		
		if(go){ //go为true确定并继续添加一条
		
			$('#oa-goodssort-oagoodsDetailAddPage').widget('clear');
			$("#oa-form-oagoodsDetailAddPage").form("clear");
			$("#oa-taxtype-oagoodsDetailAddPage").widget('setValue', defaulttaxtype);
			$("#oa-auxunitid-oagoodsDetailAddPage").widget('setValue', defaultAuxunitid);
			$("#auxunitname").val(defaultAuxunitname);
			$("#taxname").val(defaulttaxname);
			id = id + 1;
			$('#id').val(id);

            setTimeout(function() {

                $('#oa-goodsid-oagoodsDetailAddPage').focus();
            }, 100);

		}
		else{ //否则直接关闭
            $dialog.dialog('close', true)
		}
	}
	
	function editDetail(go){ //编辑新数据确定后操作，
	
		var flag = $("#oa-form-oagoodsDetailEditPage").form('validate');
		if(!flag){
			return false;
		}

        loading('处理中...');

		var form = $("#oa-form-oagoodsDetailEditPage").serializeJSON();

		// form.totalvolume = parseFloat(parseFloat(form.glength) * parseFloat(form.gwidth) * parseFloat(form.gheight));

		var rows = $line.datagrid('getRows');
		var rowIndex = -1;
		for(var i = 0; i < rows.length; i++){
			var rowJson = rows[i];
			if(rowJson.id == undefined){
				rowIndex = i;
				break;
			} else if(rowJson.id == form.id) {
				rowIndex = i;
				break;
			}
		}
		if(rowIndex == -1){
			rowIndex = rows.length;
			$line.datagrid('appendRow',{id: rowIndex}); //如果是最后一行则添加一新行
		} 
		$line.datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
		
		var d = $line.datagrid('getRows');
		$line.datagrid("loadData", {'total': d.length, 'rows': d});

        loaded();
		if(go){ //go为true确定并继续添加一条
			$('#oa-goodssort-oagoodsDetailAddPage').widget('clear');
			$("#oa-form-oagoodsDetailEditPage").form("clear");
		}
		else{ //否则直接关闭
			$("#oa-dialog-oagoodsDetailEditPage-content").dialog('close', true)
		}
	}

	function oagoods_handle_save_form_submit(callBack, args) {

		// 保存当前客户信息
		$("#oa-form-oagoodsHandlePage").form({
			onSubmit: function(){
				var flag = $(this).form('validate');
				if(flag==false){
					return false;
				}  
		
				var json = $("#oa-datagrid-oagoodsHandlePage").datagrid('getRows');
				
				$("#oa-goodsBrandJSON-oagoodsHandlePage").val(JSON.stringify(json));
	
				loading("保存中...");
			},
			success:function(data){

    			loaded();
    			var json;
				try{
					json = $.parseJSON(data);
				}catch(e){
					$.messager.alert("提醒","保存失败");
    				return false;
				}
    			
    			// 保存失败
    			if(data.flag) {
    				$.messager.alert("提醒","保存失败");
    				return false;
    			}
    			
    			// 保存成功
				$.messager.alert("提醒","保存成功");
				$('#oa-id-oagoodsHandlePage').val(json.backid);
				if(callBack.data != undefined && callBack.data != null) {
				
					callBack.data(json.backid);
					return false;
				}

			}
		});
	
	}

	// 提交表单(工作页面提交表单接口方法)
	// call为newWorkAddPage.jsp中的save方法
	// args为save方法的参数，包含args.type
	function workFormSubmit(call, args) {

        var json = $("#oa-datagrid-oagoodsHandlePage").datagrid('getRows');

        $("#oa-goodsBrandJSON-oagoodsHandlePage").val(JSON.stringify(json));
        oagoods_handle_save_form_submit(call, args);
        $("#oa-form-oagoodsHandlePage").submit();
	}
	
	$(function(){

    	//检验商品数据（唯一性，最大长度等）
		$.extend($.fn.validatebox.defaults.rules, {
   			validId:{//编号唯一性,最大长度
   				validator:function(value,param){
   					if(value.length <= param[0]){

   						if($("#goods-oldgoodsid").val() == value){
   							return true;
   						}
   						var ret = goodsInfo_AjaxConn({goodsid:value},'oa/selectExistedGoodsid.do');//true 重复，false 不重复
   						var retJson = $.parseJSON(ret);
   						if(retJson.flag){
   							$.fn.validatebox.defaults.rules.validId.message = '编号重复, 请重新输入!';
   							return false;
   						}
   						
   						var rows = $line.datagrid('getRows');
   						for(var i = 0; i < rows.length; i++) {
   						
   							if(value == rows[i].goodsid) {
   								$.fn.validatebox.defaults.rules.validId.message = '编号在列表中已存在, 请重新输入!';
   								return false;
   							}
   						}
   					}
   					else{
   						$.fn.validatebox.defaults.rules.validId.message = '最多可输入{0}个字符!';
						return false;
   					}
   					return true;
   				},
   				message:''
   			},
   			validName:{//名称唯一性,最大长度
   				validator:function(value,param){
   					if(value.length <= param[0]){

   						if($("#goods-oldgoodsname").val() == value){
   							return true;
   						}
   						var ret = goodsInfo_AjaxConn({goodsname:value},'oa/selectExistedGoodsname.do');//true 重复，false 不重复
   						var retJson = $.parseJSON(ret);
   						if(retJson.flag){
   							$.fn.validatebox.defaults.rules.validName.message = '名称重复, 请重新输入!';
   							return false;
   						}
   						
   						var rows = $line.datagrid('getRows');
   						for(var i = 0; i < rows.length; i++) {
   						
   							if(value == rows[i].goodsname) {
   								$.fn.validatebox.defaults.rules.validName.message = '名称在列表中已存在, 请重新输入!';
   								return false;
   							}
   						}
   					}
   					else{
   						$.fn.validatebox.defaults.rules.validName.message = '最多可输入{0}个字符!';
						return false;
   					}
   					return true;
   				},
   				message:''
   			},
   			maxLen:{//最大长度
   				validator:function(value,param){
   					if(value.length <= param[0]){
   						return true;
   					}
   					else{
   						$.fn.validatebox.defaults.rules.maxLen.message = '最多可输入{0}个字符!';
   						return false;
   					}
   				},
   				message:''
   			},
   			validBarcode:{//唯一性,最大长度
   				validator:function(value,param){
   					if(value.length <= param[0]){
   						if($("#goods-oldbarcode").val() == value){
   							return true;
   						}
   						var ret = goodsInfo_AjaxConn({barcode:value},'oa/selectExistedBarcode.do');//true 重复，false 不重复
   						var retJson = $.parseJSON(ret);
   						if(retJson.flag){
   							$.fn.validatebox.defaults.rules.isRepeatBarcode.message = '条形码重复, 请重新输入!';
   							return false;
   						}
   					}
   					else{
   						$.fn.validatebox.defaults.rules.isRepeatBarcode.message = '最多可输入{0}个字符!';
						return false;
   					}
   					return true;
   				},
   				message:''
   			},
   			validBoxbarcode:{//唯一性,最大长度
   				validator:function(value,param){
   					if(value.length <= param[0]){
   						if($("#goods-oldboxbarcode").val() == value){
   							return true;
   						}
   						var ret = goodsInfo_AjaxConn({boxbarcode:value},'oa/selectExistedBoxbarcode.do');//true 重复，false 不重复
   						var retJson = $.parseJSON(ret);
   						if(retJson.flag){
   							$.fn.validatebox.defaults.rules.isRepeatBoxbarcode.message = '箱装条形码重复, 请重新输入!';
   							return false;
   						}
   					}
   					else{
   						$.fn.validatebox.defaults.rules.isRepeatBoxbarcode.message = '最多可输入{0}个字符!';
						return false;
   					}
   					return true;
   				},
   				message:''
   			}
		});

	});
	
	function opeGoodsDetail() {
	
		var row = $line.datagrid('getSelected');

		if(row.goodsid == undefined) {
			addGoodsDetail();
			return ;
		}
		
		editGoodsDetail();
	}
	
	var goodsInfo_AjaxConn = function (Data, Action) {
	    var MyAjax = $.ajax({
	        type: 'post',
	        cache: false,
	        url: Action,
	        data: Data,
	        async: false,
	        ajaxSend:function(){
	        	loading("提交中..");
	        },
	        success:function(data){
	        	loaded();
	        }
	    });
	    return MyAjax.responseText;
	};
	
	$(function(){

		// 进场明细添加页面
		$(document).keydown(function(event){
			switch(event.keyCode){
				case 27: //Esc
					$("#oa-dialog-oagoodsDetailAddPage-content").dialog('close');
				break;
			}
		});
		$(document).bind('keydown', 'ctrl+enter',function (){
			$("#oa-price3-oagoodsDetailAddPage").blur();
			setTimeout(function(){
				$("#oa-savegoon-oagoodsDetailAddPage").trigger("click");
				$("#oa-savegoon-oagoodsDetailAddPage").trigger("click");
			},100);
		});
		$(document).bind('keydown', '+',function (){
			$("#oa-price3-oagoodsDetailAddPage").blur();
			setTimeout(function(){
				$("#oa-savegoon-oagoodsDetailAddPage").trigger("click");
				$("#oa-savegoon-oagoodsDetailAddPage").trigger("click");
			},100);
			return false;
		});

		// 进场明细编辑页面
		$(document).keydown(function(event){
			switch(event.keyCode){
				case 27: //Esc
					$("#oa-dialog-oagoodsDetailEditPage-content").dialog('close');
				break;
			}
		});
		$(document).bind('keydown', 'ctrl+enter',function (){
			$("#oa-price3-oagoodsDetailEditPage").blur();
			setTimeout(function(){
				$("#oa-savegoon-oagoodsDetailEditPage").trigger("click");
				$("#oa-savegoon-oagoodsDetailEditPage").trigger("click");
			},100);
		});
		$(document).bind('keydown', '+',function (){
			$("#oa-price3-oagoodsDetailEditPage").blur();
			setTimeout(function(){
				$("#oa-savegoon-oagoodsDetailEditPage").trigger("click");
				$("#oa-savegoon-oagoodsDetailEditPage").trigger("click");
			},100);
			return false;
		});

	});
	
	</script>
  </body>
</html>