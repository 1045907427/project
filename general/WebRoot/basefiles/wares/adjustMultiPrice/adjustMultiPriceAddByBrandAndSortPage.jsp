<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>商品多价调整单商品批量添加</title>
</head>
<body>
<table id="goods-datagrid-adjustMultiPriceByBrandAndSort"></table>
<div id="goods-queryDiv-adjustMultiPriceByBrandAndSort">
	<form id="goods-queryform-adjustMultiPriceByBrandAndSort">
		<table cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td class="len80 right">编码/条形码：</td>
				<td><input type="text" name="id" style="width: 150px;" /></td>
				<td class="len80 right">品&nbsp;&nbsp;牌：</td>
				<td><input type="text" name="brandArr" id="goods-brand-adjustMultiPriceByBrandAndSort" style="width: 150px;" /></td>
			</tr>
			<tr>
				<td class="len80 right">商品名称：</td>
				<td><input type="text" name="goodsname" style="width: 150px;" /></td>
				<td class="len80 right">商品分类：</td>
				<td><input type="text" name="defaultsortArr" id="goods-defaultsort-adjustMultiPriceByBrandAndSort" style="width: 150px;" /></td>
				<td class="len120 right"><a href="javascript:;" class="button-qr" id="goods-query-adjustMultiPriceByBrandAndSort">查询</a></td>
				<td class="len120 right"><a href="javascript:;" class="button-qr" id="goods-save-adjustMultiPriceByBrandAndSort">保存</a></td>
			</tr>
		</table>
		<input type="hidden" id="goods-detailJson-adjustMultiPriceByBrandAndSort" name="detailJson" />
	</form>
</div>
<script type="text/javascript">
    var $adjustMultiPriceGrid = $("#goods-datagrid-adjustMultiPriceByBrandAndSort");
	var editIndex = undefined;
	var thisIndex = undefined;
	var editfiled = null;
	var nextfiled = null;

    function endEditing(field){
        if (editIndex == undefined){
            return true;
        }
        if ($adjustMultiPriceGrid.datagrid('validateRow', editIndex)) {
            var ed = $adjustMultiPriceGrid.datagrid('getEditor', {
                index: editIndex,
                field: field
            });
            var edObj = getNumberBoxObject(ed.target);
            if (null == edObj) {
                return false;
            }else{
                $adjustMultiPriceGrid.datagrid('endEdit', editIndex);

                var row = $adjustMultiPriceGrid.datagrid('getRows')[editIndex];
                if(field == "newbuyprice"){
                    if(row.newbuyprice != "" && row.newbuyprice != null){
                        $adjustMultiPriceGrid.datagrid('selectRow', editIndex);
                    }else{
                        $adjustMultiPriceGrid.datagrid('uncheckRow',editIndex);
                        $adjustMultiPriceGrid.datagrid('unselectRow',editIndex);
                    }

                }else if(field == "newsalesprice"){
                    if(row.newsalesprice != "" && row.newsalesprice != null){
                        $adjustMultiPriceGrid.datagrid('selectRow', editIndex);
                    }else{
                        $adjustMultiPriceGrid.datagrid('uncheckRow',editIndex);
                        $adjustMultiPriceGrid.datagrid('unselectRow',editIndex);
                    }

                }else if(field == "newprice1"){
                    if(row.newprice1 != "" && row.newprice1 != null){
                        $adjustMultiPriceGrid.datagrid('selectRow', editIndex);
                    }else{
                        $adjustMultiPriceGrid.datagrid('uncheckRow',editIndex);
                        $adjustMultiPriceGrid.datagrid('unselectRow',editIndex);
                    }

                }if(field == "newprice2"){
                    if(row.newprice2 != "" && row.newprice2 != null){
                        $adjustMultiPriceGrid.datagrid('selectRow', editIndex);
                    }else{
                        $adjustMultiPriceGrid.datagrid('uncheckRow',editIndex);
                        $adjustMultiPriceGrid.datagrid('unselectRow',editIndex);
                    }

                }else if(field == "newprice3"){
                    if(row.newprice3 != "" && row.newprice3 != null){
                        $adjustMultiPriceGrid.datagrid('selectRow', editIndex);
                    }else{
                        $adjustMultiPriceGrid.datagrid('uncheckRow',editIndex);
                        $adjustMultiPriceGrid.datagrid('unselectRow',editIndex);
                    }

                }else if(field == "newprice4"){
                    if(row.newprice4 != "" && row.newprice4 != null){
                        $adjustMultiPriceGrid.datagrid('selectRow', editIndex);
                    }else{
                        $adjustMultiPriceGrid.datagrid('uncheckRow',editIndex);
                        $adjustMultiPriceGrid.datagrid('unselectRow',editIndex);
                    }

                }if(field == "newprice5"){
                    if(row.newprice5 != "" && row.newprice5 != null){
                        $adjustMultiPriceGrid.datagrid('selectRow', editIndex);
                    }else{
                        $adjustMultiPriceGrid.datagrid('uncheckRow',editIndex);
                        $adjustMultiPriceGrid.datagrid('unselectRow',editIndex);
                    }

                }else if(field == "newprice6"){
                    if(row.newprice6 != "" && row.newprice6 != null){
                        $adjustMultiPriceGrid.datagrid('selectRow', editIndex);
                    }else{
                        $adjustMultiPriceGrid.datagrid('uncheckRow',editIndex);
                        $adjustMultiPriceGrid.datagrid('unselectRow',editIndex);
                    }

                }else if(field == "newprice7"){
                    if(row.newprice7 != "" && row.newprice7 != null){
                        $adjustMultiPriceGrid.datagrid('selectRow', editIndex);
                    }else{
                        $adjustMultiPriceGrid.datagrid('uncheckRow',editIndex);
                        $adjustMultiPriceGrid.datagrid('unselectRow',editIndex);
                    }

                }if(field == "newprice8"){
                    if(row.newprice8 != "" && row.newprice8 != null){
                        $adjustMultiPriceGrid.datagrid('selectRow', editIndex);
                    }else{
                        $adjustMultiPriceGrid.datagrid('uncheckRow',editIndex);
                        $adjustMultiPriceGrid.datagrid('unselectRow',editIndex);
                    }

                }else if(field == "newprice9"){
                    if(row.newprice9 != "" && row.newprice9 != null){
                        $adjustMultiPriceGrid.datagrid('selectRow', editIndex);
                    }else{
                        $adjustMultiPriceGrid.datagrid('uncheckRow',editIndex);
                        $adjustMultiPriceGrid.datagrid('unselectRow',editIndex);
                    }

                }else if(field == "newprice10"){
                    if(row.newprice10 != "" && row.newprice10 != null){
                        $adjustMultiPriceGrid.datagrid('selectRow', editIndex);
                    }else{
                        $adjustMultiPriceGrid.datagrid('uncheckRow',editIndex);
                        $adjustMultiPriceGrid.datagrid('unselectRow',editIndex);
                    }

                }else if(field == "remark" ){
                    if(row.remark != "" && row.remark != null){
                        $adjustMultiPriceGrid.datagrid('selectRow', editIndex);
                    }else{
                        $adjustMultiPriceGrid.datagrid('uncheckRow',editIndex);
                        $adjustMultiPriceGrid.datagrid('unselectRow',editIndex);
                    }

                }
                editIndex = undefined;
                return true ;
            }
        }else{
            return false;
        }

    }

    function onClickCell(index, field){
        if (endEditing(editfiled)){
            var row = $adjustMultiPriceGrid.datagrid('getRows')[index];
            if(row.goodsid == undefined){
                return false;
            }
            editfiled = field;
            if(field == "newbuyprice"){
                nextLineCell(index,"newbuyprice");

            }else if(field == "newsalesprice"){
                nextLineCell(index,"newsalesprice")

            }else if(field == "newprice1"){
                nextLineCell(index,"newprice1");

            }if(field == "newprice2"){
                nextLineCell(index,"newprice2");

            }else if(field == "newprice3"){
                nextLineCell(index,"newprice3")

            }else if(field == "newprice4"){
                nextLineCell(index,"newprice4");

            }if(field == "newprice5"){
                nextLineCell(index,"newprice5");

            }else if(field == "newprice6"){
                nextLineCell(index,"newprice6")

            }else if(field == "newprice7"){
                nextLineCell(index,"newprice7");

            }if(field == "newprice8"){
                nextLineCell(index,"newprice8");

            }else if(field == "newprice9"){
                nextLineCell(index,"newprice9")

            }else if(field == "newprice10"){
                nextLineCell(index,"newprice10");

            }else if(field == "remark" ){
                nextLineCell(index,"remark");

            }
        }
    }

    function nextLineCell(index,field){
        nextfiled = field;
        $adjustMultiPriceGrid.datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
        editIndex = index;
        thisIndex = index;
        var ed = $adjustMultiPriceGrid.datagrid('getEditor', {index:editIndex,field:field});
        var obj=getNumberBoxObject(ed.target);
        if(null==obj){
            return false;
        }
        obj.focus();
        obj.select();
        obj.die("keyup").bind("keyup",function(e){
            var e = e || event,
                    keycode = e.which || e.keyCode;
            if (keycode==13 || keycode==38 || keycode==40) {
                if(editfiled!=null){
                    endEditing(editfiled);
                }
            }
        });
    }

    function moveToNextRow(){
        var datarow=$adjustMultiPriceGrid.datagrid('getRows');
        if(null!=datarow && (thisIndex+1) < datarow.length){
            if(editIndex == null){
                onClickCell(thisIndex+1, nextfiled);
            }
        }
    }

    function moveToPrevRow(){
        if((thisIndex-1) >0){
            if(editIndex == null){
                onClickCell(thisIndex-1,nextfiled);
            }
        }
    }
    $(document).bind('keyup', 'up',function (event){
        moveToPrevRow();
        return false;
    });
    $(document).bind('keyup', 'down',function (event){
        moveToNextRow();
        return false;
    });
    $(document).bind('keyup', 'enter',function (event){
        moveToNextRow();
        return false;
    });


    $(function(){
        //品牌
        $("#goods-brand-adjustMultiPriceByBrandAndSort").widget({
            width : 120,
            name : 't_base_goods_brand',
            col : 'id',
            singleSelect : false,
            onlyLeafCheck : false
        });
        //商品分类
        $("#goods-defaultsort-adjustMultiPriceByBrandAndSort").widget({
            width : 120,
            referwid : 'RL_T_BASE_GOODS_WARESCLASS',
            col : 'id',
            singleSelect : false,
            onlyLeafCheck : false
        });

        var tableColJson = $adjustMultiPriceGrid.createGridColumnLoad({
            frozenCol : [[
                {field : 'ck',checkbox : true} ,
            ]],
            commonCol : [[
                {field : 'goodsid',title : '商品编码',width : 70,align : 'left',sortable : true},
                {field : 'goodsname',title : '商品名称',width : 250,align : 'left'},
                {field : 'barcode',title : '条形码',width : 90,align : 'left'},
                {field : 'oldbuyprice',title : '原始采购价',width : 70,align : 'right',
                    styler:function(){
                        return 'background-color:#D8D7D7;';
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                {field : 'newbuyprice',title : '调整采购价',width : 70,align : 'right',
                    editor:{
                        type:'numberbox',
                        options:{
                            min:0,
                            precision:6
                        }
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                {field : 'oldsalesprice',title : '原始销售价',width : 70,align : 'right',
                    styler:function(){
                        return 'background-color:#D8D7D7;';
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                {field : 'newsalesprice',title : '调整销售价',width : 70,align : 'right',
                    editor:{
                        type:'numberbox',
                        options:{
                            min:0,
                            precision:6
                        }
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                <c:if test="${colMap.price1 != null}">
                {field : 'oldprice1',title : '1号价',width : 60,align : 'right',
                    styler:function(){
                        return 'background-color:#D8D7D7;';
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                {field : 'newprice1',title : '调整1号价',width : 60,align : 'right',
                    editor:{
                        type:'numberbox',
                        options:{
                            min:0,
                            precision:6
                        }
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                </c:if>
                <c:if test="${colMap.price2 != null}">
                {field : 'oldprice2',title : '2号价',width : 60,align : 'right',
                    styler:function(){
                        return 'background-color:#D8D7D7;';
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                {field : 'newprice2',title : '调整2号价',width : 60,align : 'right',
                    editor:{
                        type:'numberbox',
                        options:{
                            min:0,
                            precision:6
                        }
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                </c:if>
                <c:if test="${colMap.price3 != null}">
                {field : 'oldprice3',title : '3号价',width : 60,align : 'right',
                    styler:function(){
                        return 'background-color:#D8D7D7;';
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                {field : 'newprice3',title : '调整3号价',width : 60,align : 'right',
                    editor:{
                        type:'numberbox',
                        options:{
                            min:0,
                            precision:6
                        }
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                </c:if>
                <c:if test="${colMap.price4 != null}">
                {field : 'oldprice4',title : '4号价',width : 60,align : 'right',
                    styler:function(){
                        return 'background-color:#D8D7D7;';
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                {field : 'newprice4',title : '调整4号价',width : 60,align : 'right',
                    editor:{
                        type:'numberbox',
                        options:{
                            min:0,
                            precision:6
                        }
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                </c:if>
                <c:if test="${colMap.price5 != null}">
                {field : 'oldprice5',title : '5号价',width : 60,align : 'right',
                    styler:function(){
                        return 'background-color:#D8D7D7;';
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                {field : 'newprice5',title : '调整5号价',width : 60,align : 'right',
                    editor:{
                        type:'numberbox',
                        options:{
                            min:0,
                            precision:6
                        }
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                </c:if>
                <c:if test="${colMap.price6 != null}">
                {field : 'oldprice6',title : '6号价',width : 60,align : 'right',
                    styler:function(){
                        return 'background-color:#D8D7D7;';
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                {field : 'newprice6',title : '调整6号价',width : 60,align : 'right',
                    editor:{
                        type:'numberbox',
                        options:{
                            min:0,
                            precision:6
                        }
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                </c:if>
                <c:if test="${colMap.price7 != null}">
                {field : 'oldprice7',title : '7号价',width : 60,align : 'right',
                    styler:function(){
                        return 'background-color:#D8D7D7;';
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                {field : 'newprice7',title : '调整7号价',width : 60,align : 'right',
                    editor:{
                        type:'numberbox',
                        options:{
                            min:0,
                            precision:6
                        }
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                </c:if>
                <c:if test="${colMap.price8 != null}">
                {field : 'oldprice8',title : '8号价',width : 60,align : 'right',
                    styler:function(){
                        return 'background-color:#D8D7D7;';
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                {field : 'newprice8',title : '调整8号价',width : 60,align : 'right',
                    editor:{
                        type:'numberbox',
                        options:{
                            min:0,
                            precision:6
                        }
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                </c:if>
                <c:if test="${colMap.price9 != null}">
                {field : 'oldprice9',title : '9号价',width : 60,align : 'right',
                    styler:function(){
                        return 'background-color:#D8D7D7;';
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                {field : 'newprice9',title : '调整9号价',width : 60,align : 'right',
                    editor:{
                        type:'numberbox',
                        options:{
                            min:0,
                            precision:6
                        }
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                </c:if>
                <c:if test="${colMap.price10 != null}">
                {field : 'oldprice10',title : '10号价',width : 60,align : 'right',
                    styler:function(){
                        return 'background-color:#D8D7D7;';
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                {field : 'newprice10',title : '调整10号价',width : 60,align : 'right',
                    editor:{
                        type:'numberbox',
                        options:{
                            min:0,
                            precision:6
                        }
                    },
                    formatter : function(value, row,index) {
                        return formatterDefineMoney(value);
                    }
                },
                </c:if>
                {field : 'remark',title : '备注',width : 80,align : 'left',editor:'textbox'}

            ]]
        });

        $adjustMultiPriceGrid.datagrid({
            authority:tableColJson,
            columns: tableColJson.common,
            frozenColumns: tableColJson.frozen,
            border: true,
            rownumbers: true,
            showFooter: true,
            pagination:true,
            striped:true,
            fit:true,
            singleSelect: false,
            checkOnSelect:true,
            selectOnCheck:true,
            toolbar : "#goods-queryDiv-adjustMultiPriceByBrandAndSort",
            onClickCell: function(index, field, value){
                onClickCell(index, field);
                thisIndex = index;
            },
            onLoadSuccess:function(data){
                editIndex = undefined;
                thisIndex = undefined;
                editfiled = null;
                nextfiled = null;
            }

        }).datagrid('columnMoving');

        //查询
        $("#goods-query-adjustMultiPriceByBrandAndSort").click(function() {
            editIndex = undefined;
            thisIndex = undefined;
            editfiled = null;
            nextfiled = null;
            var queryJSON = $("#goods-queryform-adjustMultiPriceByBrandAndSort").serializeJSON();
            $('#goods-datagrid-adjustMultiPriceByBrandAndSort').datagrid('options').url = 'basefiles/getAdjustMultiPriceGoodsByBrandAndSort.do';
            $adjustMultiPriceGrid.datagrid('load', queryJSON);
        });
        //保存
        $("#goods-save-adjustMultiPriceByBrandAndSort").click(function() {

            var rows = $adjustMultiPriceGrid.datagrid('getChecked');
            if(rows.length > 0){
                if(editIndex == undefined){
                    editIndex = $adjustMultiPriceGrid.datagrid('getRowIndex',rows[0]);

                    if(rows[0].offprice == ""|| rows[0].offprice == undefined){
                        $adjustMultiPriceGrid.datagrid('unselectRow',editIndex);
                    }else{
                        $adjustMultiPriceGrid.datagrid('checkRow',editIndex);
                    }
                }else{
                    $adjustMultiPriceGrid.datagrid('endEdit',editIndex);
                    var row = $adjustMultiPriceGrid.datagrid('getRows')[editIndex];

                    if(row.offprice == ""|| row.offprice == undefined){
                        $adjustMultiPriceGrid.datagrid('unselectRow',editIndex);
                    }else{
                        $adjustMultiPriceGrid.datagrid('checkRow',editIndex);
                    }
                }
                rows = sortRow(rows);
                for(var i = 0 ;i<rows.length ; i++ ){
                    var row = rows[i];

                    var rowIndex = 0;
                    var updateFlag = false;
                    var offrows = $("#goods-datagrid-adjustMultiPriceAddPage").datagrid('getRows');
                    for(var j = 0 ; j < offrows.length ; j++){
                        if(row.goodsid == offrows[j].goodsid){
                            rowIndex = j ;
                            updateFlag = true;
                            break;
                        }
                        if(offrows[j].goodsid == undefined){
                            rowIndex = j;
                            break;
                        }
                    }

                    if(rowIndex == offrows.length - 1){
                        $("#goods-datagrid-adjustMultiPriceAddPage").datagrid('appendRow',{}); //如果是最后一行则添加一新行
                    }

                    if(updateFlag){
                        var r =confirm("商品编号："+row.goodsid+"已存在，是否替换？");
                        if(r){
                            $("#goods-datagrid-adjustMultiPriceAddPage").datagrid('updateRow',{index:rowIndex, row:row}); //将数据更新到列表中
                        }
                    }else{
                        $("#goods-datagrid-adjustMultiPriceAddPage").datagrid('updateRow',{index:rowIndex, row:row});
                    }
                }
                $("#goods-dialog-adjustMultiPriceAddPage-content").window('close',true);
            }else{
                alert("请选择一条记录");
            }
            

        });


        //对批量添加的商品进行排序
        function sortRow(rows){
            var arrRow = new Array();
            var arr = new Array();
            for(var i = 0 ;i<rows.length ; i++ ){
                arr[i] = rows[i].goodsid;
            }
            arr = arr.sort(function(a,b){
                //从小到大排序
                return a>b?1:-1;
            });
            for(var i=0;i<arr.length;i++){
                for(var j = 0 ; j<rows.length ;j ++){
                    if(arr[i] == rows[j].goodsid){
                        arrRow[i] = rows[j];
                    }
                }
            }
            return arrRow;
        }

    });




    </script>
</body>

</html>