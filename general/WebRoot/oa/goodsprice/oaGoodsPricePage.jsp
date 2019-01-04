<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <title>商品调价申请单</title>
	<%@include file="/include.jsp" %>
  </head>
  
  <body>
  <style type="text/css">
      .len400{
          width: 403px;
      }
      .len440{
          width: 440px;
      }
  </style>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center',border:false">
    		<div id="oa-panel-oaGoodsPricePage">
    		</div>
    	</div>
    </div>
	<div class="easyui-menu" id="oa-contextMenu-oagoodsPricePage">
		<div id="oa-addRow-oagoodsPricePage" data-options="iconCls:'button-add'">添加</div>
		<div id="oa-editRow-oagoodsPricePage" data-options="iconCls:'button-edit'">编辑</div>
		<div id="oa-removeRow-oagoodsPricePage" data-options="iconCls:'button-delete'">删除</div>
	</div>
    <div id="oa-dialog-oagoodsPricePage"></div>
	<script type="text/javascript">

        <!--
        var minLength = 10;
        var rowinfo = '';
        var pricelist = '${list }';

        function addDetail(go){
            var flag = $("#oa-form-oaGoodsPriceDetailAddPage").form('validate');
            if(!flag){
                return false;
            }
            var form = $("#oa-form-oaGoodsPriceDetailAddPage").serializeJSON();
            var id =  parseInt(form.id);
            var rows = $oa_pricedatagrid.datagrid('getRows');
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
                $oa_pricedatagrid.datagrid('appendRow',{}); //如果是最后一行则添加一新行
            }
            $oa_pricedatagrid.datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中

            var d = $oa_pricedatagrid.datagrid('getRows');
            $oa_pricedatagrid.datagrid("loadData", {'total': d.length, 'rows': d});
            for(var i = d.length; i < minLength; i++) {
                $oa_datagrid.datagrid('appendRow', {id: rowIndex});
            }
            if(d[d.length - 1].id) {
                $oa_pricedatagrid.datagrid('appendRow', {id: rowIndex});
            }
            if(go){
                $("#oa-form-oaGoodsPriceDetailAddPage").form("clear");
                id = id + 1;
                $('#id').val(id);
            }else{
                $("#oa-dialog-oaGoodsPriceDetailAddPage-content").dialog('destroy');
            }

        }

        function editDetail(go){
            var flag = $("#oa-form-oaGoodsPriceDetailEditPage").form('validate');
            if(!flag){
                return false;
            }
            var form = $("#oa-form-oaGoodsPriceDetailEditPage").serializeJSON();
            var id = parseInt(form.id);
            var rows = $oa_pricedatagrid.datagrid('getRows');
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
                $oa_pricedatagrid.datagrid('appendRow',{}); //如果是最后一行则添加一新行
            }
            $oa_pricedatagrid.datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中

            var d = $oa_pricedatagrid.datagrid('getRows');
            $oa_pricedatagrid.datagrid("loadData", {'total': d.length, 'rows': d});
            for(var i = d.length; i < minLength; i++) {
                $oa_datagrid.datagrid('appendRow', {id: rowIndex});
            }
            if(d[d.length - 1].id) {
                $$oa_pricedatagrid.datagrid('appendRow', {id: rowIndex});
            }
			$("#oa-dialog-oaGoodsPriceDetailAddPage-content").dialog('destroy');
        }

        function workFormSubmit(call, args) {
            oagoodsprice_handle_save_form_submit(call, args);
        }

        function oagoodsprice_handle_save_form_submit(callBack, args) {
            $form.form({
                onSubmit: function(param) {

                    var flag = $form.form('validate');
                    if(!flag) {

                        return false;
                    }

                    $("#oa-detaillist-oaGoodsPriceHandlePage").val(JSON.stringify($oa_pricedatagrid.datagrid('getRows')));

                    $('select').removeAttr('disabled');

                    loading("提交中...");
                },
                success: function(data) {

                    loaded();
                    var json;
                    try{
                        json = $.parseJSON(data);
                    }catch(e){
                        $.messager.alert("提醒","保存失败！");
                        return false;
                    }

                    // 保存失败
                    if(data.flag) {
                        $.messager.alert("提醒","保存失败！");
                        return false;
                    }

                    // 保存成功
                    $.messager.alert("提醒","保存成功。");
                    if(callBack.data != undefined && callBack.data != null) {

                        callBack.data(json.backid);
                        return false;
                    }

                }
            });
            $form.submit();
        }

        $(function() {

            var url = 'oa/goodsprice/oaGoodsPriceViewPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }';
            var type = '${param.type }';
            var step = '${param.step }';
            if(type == 'handle') {

                url = 'oa/goodsprice/oaGoodsPriceHandlePage.do?id=${param.id }&step=${param.step }&type=handle&processid=${param.processid }';

            } else if(type == 'view'){

                url = 'oa/goodsprice/oaGoodsPriceViewPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }';
            }

            $('#oa-panel-oaGoodsPricePage').panel({
                href: url,
                cache: false,
                maximized: true,
                border: false,
                onLoad: function () {
                    $oa_pricedatagrid.datagrid({
                        columns: [[
                            {field:"id",title:"bh",hidden:true,
                                formatter:function(value,row,index){
                                    return index;
                                }
                            },
                            {field: 'goodsid', title: '商品编号'/*, width: 80*/},
                            {field: 'goodsname', title: '商品名称'/*, width: 200*/},
                            {field: 'barcode', title: '条形码'/*, width: 80*/},
                            {field: 'unitname', title: '单位'/*, width: 80*/},
                            {field: 'brandid', hidden: true},
                            {field: 'goodssort',hidden: true},
                            {field: 'unitid', hidden: true},
                            {field: 'auxunitid', hidden: true},
                            {field: 'auxunitname', hidden: true},
                            {field: 'boxnum', hidden: true},
                            {field: 'storageid', hidden: true},
                            {field: 'taxtype', hidden: true},
                            {field: 'totalvolume', hidden: true},
                            {field: 'totalweight', hidden: true},
                            {field: 'glength', hidden: true},
                            {field: 'gwidth', hidden: true},
                            {field: 'gheight', hidden: true},
                            {field: 'oldbuytaxprice', title: '采购价(原)'/*, width: 80*/, align: 'right',
                                formatter: function(value,row,index){
                                    if(value != undefined){
                                        return formatterMoney(value);
                                    }
                                }
                            },
                            {field: 'newbuytaxprice', title: '采购价(现)'/*, width: 80*/, align: 'right',
                                formatter: function(value,row,index){
                                    return formatterMoney(value);
                                }
                            },
                            {field: 'oldbasesaleprice', title: '基准销售价(原)'/*, width: 80*/, align: 'right',
                                formatter: function(value,row,index){
                                    return formatterMoney(value, 6);
                                }
                            },
                            {field: 'newbasesaleprice', title: '基准销售价(现)'/*, width: 80*/, align: 'right',
                                formatter: function(value,row,index){
                                    return formatterMoney(value, 6);
                                }
                            },
                            {field: 'oldcostaccountprice', title: '核算成本价(原)',hidden:true/*, width: 80*/, align: 'right',
                                formatter: function(value,row,index){
                                    return formatterMoney(value, 6);
                                }
                            },
                            {field: 'newcostaccountprice', title: '核算成本价(现)',hidden:true/*, width: 80*/, align: 'right',
                                formatter: function(value,row,index){
                                    return formatterMoney(value, 6);
                                }
                            },
                              //循环显示启用状态下的价格套
                            <c:forEach items="${list }" var="item" varStatus="idx">
                            {field: 'oldprice${idx.index + 1}', title: '${item.codename }(原)'/*, width: 80*/, align: 'right',
                                formatter: function(value,row,index){
                                    if(value == undefined || value == null || value == ''){
                                        return value;
                                    }
                                    return formatterMoney(value, 6);
                                }
                            },
                            {field: 'oldprofit${idx.index + 1}', title: '${item.codename }毛利率(原)'/*, width: 80*/, align: 'right',
                                formatter: function(value,row,index){
                                    var price = row.oldprice${idx.index + 1};
                                    var cost1 = row.oldcostaccountprice;
                                    var cost2 = row.oldbuytaxprice;

                                    if(price == undefined || price == null || price == '') {
                                        return '';
                                    }
                                    var cost = cost1;
                                    if(cost1 == null || cost1 == '' || cost1 == '0.00' || cost1 == '0' || parseFloat(cost1) == 0) {
                                        cost = cost2;
                                    }
                                    var profit = formatterMoney(price - cost, 2);
                                    if(isNaN(profit)) {
                                        return '';
                                    }
                                    if(price == 0.00000000){
                                        return  0 + '%' ;
                                    }else{
                                        var rate = formatterMoney(profit * 100 / price, 2);
                                        if(isNaN(rate)) {
                                            return '';
                                        }
                                    }
                                    row['oldprofit${idx.index + 1}'] = rate;

                                    return rate + '%';
                                }
                            },
                            {field: 'newprice${idx.index + 1}', title: '${item.codename }(现)'/*, width: 80*/, align: 'right',
                                formatter: function(value,row,index){
                                    return formatterMoney(value, 6);
                                }
                            },
                            {field: 'newprofit${idx.index + 1}', title: '${item.codename }毛利率(现)'/*, width: 80*/, align: 'right',
                                formatter: function(value,row,index){
                                    var price = row.newprice${idx.index + 1};
                                    var cost1 = row.newcostaccountprice;
                                    var cost2 = row.newbuytaxprice;

                                    if(price == undefined || price == null || price == '') {
                                        return '';
                                    }
                                    var cost = cost1;
                                    if(cost1 == null || cost1 == '' || cost1 == '0.00' || cost1 == '0' || parseFloat(cost1) == 0) {
                                        cost = cost2;
                                    }
                                    var profit = formatterMoney(price - cost, 2);
                                    if(isNaN(profit)) {
                                        return '';
                                    }
                                    if(price == 0.00000000){
                                        return  0 + '%' ;
                                    }else{
                                        var rate = formatterMoney(profit * 100 / price, 2);
                                        if(isNaN(rate)) {
                                           var  rate = '';
                                        }
                                    }
                                    row['newprofit${idx.index + 1}'] = rate;

                                    return rate + '%';
                                }
                            },

                            </c:forEach>

                            {field: 'remark', title: '备注'/*, width: 180*/}

                        ]],
                        border: true,
                        fit: true,
                        rownumbers: true,
                        showFooter: true,
                        striped:true,
                        singleSelect: true,
                       // url: 'oa/goodsprice/selectGoodsPriceDetailList.do?billid=${param.id }',
                        data:eval('${priceList}'),
                        onLoadSuccess:function(data){
                            for(var i = 0; i < data.total; i++) {
                                var row = data.rows[i];
                                row.id = i;

                                $oa_pricedatagrid.datagrid('updateRow', {index: i, row: row})
                            }

                            for(var i = data.total; i < minLength; i++) {
                                $oa_pricedatagrid.datagrid('appendRow', {});
                            }

                            if(data.total >= minLength) {
                                $oa_pricedatagrid.datagrid('appendRow', {});
                            }
                        },
                        onRowContextMenu: function(e, rowIndex, rowData){
                            e.preventDefault();
                            <c:if test="${param.type == 'view' }">
                            return false;
                            </c:if>
                            $oa_pricedatagrid.datagrid('selectRow', rowIndex);
                            var selectedRow = $oa_pricedatagrid.datagrid('getSelected');

                            $("#oa-contextMenu-oagoodsPricePage").menu('show', {
                                left:e.pageX,
                                top:e.pageY
                            });

                            // 该行内容为空时，不能编辑
                            if(selectedRow.goodsid == undefined) {
                                $("#oa-contextMenu-oagoodsPricePage").menu('disableItem', editItem);
                            } else {
                                $("#oa-contextMenu-oagoodsPricePage").menu('enableItem', editItem);
                            }
                        },
                        <c:if test="${param.type ne 'view' }">
                        onDblClickRow: openGoodsPriceDetail,
                        </c:if>
                        //onClickRow: endEditGoodsPriceDetail,

                    });

                    // 附件
                    $attach.attach({
                        <c:choose>
                        <c:when test="${param.type == 'view' }">
                        attach: false,
                        </c:when>
                        <c:otherwise>
                        attach: true,
                        </c:otherwise>
                        </c:choose>
                        businessid: '${param.id }',
                        processid: '${param.processid }'
                    });

                    $comments2.comments({
                        businesskey: '${param.id }',
                        processid: '${param.processid }',
                        type: 'vertical',
                        width: '120',
                        agree: null
                    });

                    $supplierid.supplierWidget({
                        <c:choose>
                            <c:when test="${(param.type ne 'view') and (param.step ne 99)}">
                                required: true,
                            </c:when>
                            <c:otherwise>
                                required: false,
                            </c:otherwise>
                        </c:choose>
                        onSelect: function(data) {

                            $supplierid2.val(data.id);
                        },
                        onClear: function() {

                            $supplierid2.val('');
                        }
                    });
                    $('#oa-addRow-oagoodsPricePage').click(addGoodsPriceDetail);
                    $('#oa-editRow-oagoodsPricePage').click(editGoodsPriceDetail);
                    $('#oa-removeRow-oagoodsPricePage').click(removeGoodsDetail);
                }
            });

            function removeGoodsDetail() {

                var row = $oa_pricedatagrid.datagrid('getSelected');

                if(row.goodsid == undefined) {
                    return false;
                }

                var rows = $oa_pricedatagrid.datagrid('getRows');
                var rowIndex = rows.length - 1;
                for(var i=0; i<rows.length; i++){
                    var rowJson = rows[i];
                    if(rowJson.goodsid == row.goodsid) {

                        $oa_pricedatagrid.datagrid('deleteRow', i);
                        break;
                    }
                }

                var recordCnt = $line.datagrid('getRows').length;

                for(var i = recordCnt; i < minLength; i++) {
                    $oa_pricedatagrid.datagrid('appendRow', {});
                }

            }

            function openGoodsPriceDetail(){
               var row = $oa_pricedatagrid.datagrid('getSelected');
               if(row.goodsid == undefined){
                   addGoodsPriceDetail();
                   return ;
               }
                editGoodsPriceDetail();
            }

            function  addGoodsPriceDetail(){
                var index = -1;
                var rows = $oa_pricedatagrid.datagrid('getRows');
                var supplierid = $supplierid2.val();
                for(var i = 0; i < rows.length; i++) {
                    if(rows[i].goodsid == undefined || rows[i].goodsid == null) {
                        index = i;
                        break;
                    }
                }
                rowinfo = null;
                $('<div id="oa-dialog-oaGoodsPriceDetailAddPage-content"></div>').appendTo('#oa-dialog-oagoodsPricePage');

                $("#oa-dialog-oaGoodsPriceDetailAddPage-content").dialog({ //弹出新添加窗口
                    title:'商品价格调整单明细新增(按ESC退出)',
                    maximizable:true,
                    width:600,
                    height:400,
                    closed:false,
                    modal:true,
                    cache:false,
                    resizable:true,
                    href:'oa/goodsprice/oagoodsPriceDetailAddPage.do?index=' + index +'&supplierid='+supplierid,

                    onClose:function(){
                        $('#oa-dialog-oaGoodsPriceDetailAddPage-content').dialog("destroy");
                    },
                    onLoad:function(){
                        $("#oa-newbuytaxprice-oaGoodsPriceDetailAddPage").focus();
                    }
                });
            }

            function editGoodsPriceDetail(){
                var row = $oa_pricedatagrid.datagrid('getSelected');
                var index = $oa_pricedatagrid.datagrid('getRowIndex',row);

                goodsname = row.goodsname;
                rowinfo = row;

                var supplierid = $supplierid2.val();
                $('<div id="oa-dialog-oaGoodsPriceDetailAddPage-content"></div>').appendTo('#oa-dialog-oagoodsPricePage');

                $("#oa-dialog-oaGoodsPriceDetailAddPage-content").dialog({

                    title:'商品价格调整单明细修改(按ESC退出)',
                    maximizable:true,
                    width:600,
                    height:400,
                    closed:false,
                    modal:true,
                    cache:false,
                    resizable:true,
                    href:'oa/goodsprice/oagoodsPriceDetailEditPage.do?index=' + index +'&supplierid='+supplierid  ,

                    onClose:function(){
                        $('#oa-dialog-oaGoodsPriceDetailAddPage-content').dialog("destroy");
                    },
                    onLoad: function(){
                        $("#oa-form-oaGoodsPriceDetailEditPage").form('load', row);
                    }
                });
            }
        });

        //快捷键设置
        $(function(){

            // 明细添加页面
            $(document).keydown(function(event){
                switch(event.keyCode){
                    case 27: //Esc
                        $("#oa-dialog-oaGoodsPriceDetailAddPage-content").dialog('close');
                        break;
                }
            });
            $(document).bind('keydown', 'ctrl+enter',function (){
               // $("#oa-price3-oagoodsDetailAddPage").blur();
                setTimeout(function(){
                    $("#oa-savegoon-oaGoodsPriceDetailAddPage").trigger("click");
                    $("#oa-savegoon-oaGoodsPriceDetailAddPage").trigger("click");
                },100);
            });
            $(document).bind('keydown', '+',function (){
                $("#oa-price3-oagoodsDetailAddPage").blur();
                setTimeout(function(){
                    $("#oa-savegoon-oaGoodsPriceDetailAddPage").trigger("click");
                    $("#oa-savegoon-oaGoodsPriceDetailAddPage").trigger("click");
                },100);
                return false;
            });

            // 明细编辑页面
            $(document).keydown(function(event){
                switch(event.keyCode){
                    case 27: //Esc
                        $("#oa-dialog-oagoodsDetailEditPage-content").dialog('close');
                        break;
                }
            });
            $(document).bind('keydown', 'ctrl+enter',function (){
                //$("#oa-price3-oagoodsDetailEditPage").blur();
                setTimeout(function(){
                    $("#oa-savegoon-oaGoodsPriceDetailEditPage").trigger("click");
                    $("#oa-savegoon-oaGoodsPriceDetailEditPage").trigger("click");
                },100);
            });
            $(document).bind('keydown', '+',function (){
               // $("#oa-price3-oagoodsDetailEditPage").blur();
                setTimeout(function(){
                    $("#oa-savegoon-oaGoodsPriceDetailEditPage").trigger("click");
                    $("#oa-savegoon-oaGoodsPriceDetailEditPage").trigger("click");
                },100);
                return false;
            });

        });

        -->
	
	</script>
  </body>
</html>