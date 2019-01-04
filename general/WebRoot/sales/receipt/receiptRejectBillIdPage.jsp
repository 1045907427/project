<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',split:true,border:false" style="height:260px">
        <table id="sales-receiptRelationRejectBillPage"></table>
    </div>
</div>
<input type="hidden" id="sales-receiptid" value="${id}"/>
<script type="text/javascript">
    var receipt_AjaxConn = function (Data, Action) {
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

    $(function(){
        $("#sales-receiptRelationRejectBillPage").datagrid({
            fit:true,
            columns:[[
                {field:'ck',checkbox:true},
                {field:'id',title:'编号',width:150, align: 'left'},
                {field:'businessdate',title:'业务日期',width:80,align:'left'},
                {field:'customerid',title:'客户名称',width:100,align:'left',
                    formatter:function(value,row,index){
                        return row.customername;
                    }
                },
                {field:'handlerid',title:'对方经手人',width:80,align:'left'},
                {field:'billtype',title:'退货类型',width:60,align:'left',
                    formatter:function(value,row,index){
                        if(value=="1"){
                            return "直退";
                        }else if(value=="2"){
                            return "售后退货";
                        }
                    }
                },
                {field:'salesdept',title:'销售部门',width:80,align:'left'},
                {field:'salesuser',title:'客户业务员',width:80,align:'left'},
                {field:'status',title:'关联状态',width:60,align:'left',
                    formatter:function(value,row,index){
                        if(row.receiptid!=""){
                            return "已关联";
                        }else{
                            return "未关联";
                        }
                    }
                },
                {field:'receiptid',title:'关联回单编号',width:100,align:'left'},
                {field:'addusername',title:'制单人',width:80,sortable:true},
                {field:'addtime',title:'制单时间',width:80,sortable:true},
                {field:'remark',title:'备注',width:100,sortable:true}
            ]],
            title:"",
            method:'post',
            rownumbers:true,
            idField:'id',
            singleSelect:false,
            selectOnCheck:true,
            checkOnSelect:true,
            data:JSON.parse('${jsonStr}'),
        });

    });

</script>
</body>
</html>
