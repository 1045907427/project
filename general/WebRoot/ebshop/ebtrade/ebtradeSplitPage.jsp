<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>电商交易订单拆分页面</title>
</head>
<body>
<table id="ebshop-datagrid-ebtradeSplitPage"></table>
<form id="ebshop-form-ebtradeSplitPage" action="ebtrade/updateEbtradeSplit.do" method="post">
    <input type="hidden" name="ebtradeid" value="${ebtradeid }"/>
    <input type="hidden" id="ebshop-detail-ebtradeSplitPage" name="orderJson"/>
</form>
<script type="text/javascript">
    $(function(){
        $("#ebshop-form-ebtradeSplitPage").form({
            onSubmit: function(){
                endEditing(editfiled);
                var json = $("#ebshop-datagrid-ebtradeSplitPage").datagrid('getChecked');
                $("#ebshop-detail-ebtradeSplitPage").val(JSON.stringify(json));
                loading("提交中..");
            },
            success:function(data){
                loaded();
                var json = $.parseJSON(data);
                if(json.flag){
                    $.messager.alert("提醒","拆分成功。生成新的电商交易订单："+json.id);
                    $("#ebshop-ebtrade-dialog-split").dialog("close");
                    if($("#ebshop-panel-ebtradePage").size() != 0){
                        $("#ebshop-panel-ebtradePage").panel("refresh");
                    }
                    if($("#ebshop-datagrid-ebtrageListPage").size() != 0){
                        $("#ebshop-datagrid-ebtrageListPage").datagrid('reload');
                    }
                }else{
                    $.messager.alert("提醒","拆分失败.");
                }
            }
        });
        $("#ebshop-datagrid-ebtradeSplitPage").datagrid({ //销售商品明细信息编辑
            columns: [[
                {field:'ck',checkbox:true},
                {field:'id',title:'编号',width:70,align:' left',hidden:true},
                {field:'tid',title:'订单交易号',width:70,align:' left',hidden:true},
                {field:'oid', title:'子订单编号', width:70,align:'left'},
                {field:'numIid', title:'商品数字ID',width:70,align:'left'},
                {field:'outerIid', title:'商家外部编码',width:80,align:'left',sortable:true},
                {field:'title', title:'商品标题',width:80,align:'left'},
                {field:'price', title:'商品价格',width:60,align:'right',sortable:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'num', title:'购买数量',width:60,align:'right',sortable:true,
                    formatter:function(value,row,index){
                        return formatterNum(value);
                    }
                },
                {field:'splitnum', title:'拆分数量',width:60,align:'right',
                    formatter:function(value,row,index){
                        return formatterBigNum(value);
                    },
                    editor:{
                        type:'numberbox',
                        options:{
                            validType:'ebtradeMax'
                        }
                    }
                },
                {field:'totalFee', title:'应付金额',width:60,align:'right',sortable:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'payment', title:'实付金额',width:60,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'discountFee', title:'优惠金额',width:60,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'adjustFee', title:'手工调整金额',width:80,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                }
            ]],
            border: true,
            fit: true,
            rownumbers: true,
            showFooter: true,
            striped:true,
            singleSelect: false,
            checkOnSelect:true,
            selectOnCheck:true,
            data: JSON.parse('${orderJson}'),
            onDblClickRow: function(rowIndex, rowData){
                onClickCell(rowIndex, "splitnum");
            },
            onClickCell: function(index, field, value){
                onClickCell(index, field);
            }
        });
    });
    var $wareSplitList = $("#ebshop-datagrid-ebtradeSplitPage"); //商品datagrid的div对象
    var editIndex = undefined;
    var thisIndex = undefined;
    var editfiled = null;
    function endEditing(field){
        if (editIndex == undefined){return true}
        if(field == "splitnum"){
            if ($wareSplitList.datagrid('validateRow', editIndex)){
                var ed = $wareSplitList.datagrid('getEditor', {index:editIndex,field:'splitnum'});
                var splitnum = $(ed.target).val();
                $wareSplitList.datagrid('endEdit', editIndex);
                var row = $wareSplitList.datagrid('getRows')[editIndex];
                row.splitnum = splitnum;
                $wareSplitList.datagrid('updateRow',{index:editIndex, row:row});
                editIndex = undefined;
                return true;
            } else {
                return false;
            }
        }
    }
    function onClickCell(index, field){
        if (endEditing(editfiled)){
            var row = $wareSplitList.datagrid('getRows')[index];
            if(row.outerIid == undefined){
                return false;
            }
            editfiled = field;
            if(field == "splitnum"){
                $wareSplitList.datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
                ebtradeMax(parseInt(row.num));
                editIndex = index;
                thisIndex = index;
                var ed = $wareSplitList.datagrid('getEditor', {index:editIndex,field:'splitnum'});
                getNumberBoxObject(ed.target).focus();
                getNumberBoxObject(ed.target).select();
            }
        }
    }
    function ebtradeMax(max){ //验证接收数最大值
        $.extend($.fn.validatebox.defaults.rules, {
            ebtradeMax:{
                validator:function(value){
                    return parseInt(value) <= max;
                },
                message:'请输入小于等于'+max+'的数字!'
            }
        });
    }
</script>
</body>
</html>
