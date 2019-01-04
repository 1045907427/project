<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>商品历史销售价格</title>
</head>
<body>
    <table id="purchase-goods-history-price-dialog"></table>
    <script type="text/javascript">
        var historyPriceListJson = $("#purchase-goods-history-price-dialog").createGridColumnLoad({ //以下为商品明细datagrid字段
            commonCol: [[
                {field:'businessdate', title:'业务日期',width:90,align:'left'},
                {field:'suppliername', title:'供应商',width:150,align:'left'},
                {field:'unitname', title:'单位',width:35,align:'left'},
                {field:'unitnum', title:'数量',width:60,align:'right',
                    formatter:function(value,row,index){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'taxprice', title:'单价',width:60,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'boxprice', title:'箱价',aliascol:'goodsid',width:60,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'taxamount', title:'金额',width:60,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'notaxprice', title:'未税单价',width:80,align:'right', hidden:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'notaxamount', title:'未税金额',width:80,align:'right', hidden:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'tax', title:'税额',width:80,align:'right', hidden:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'auxnumdetail', title:'辅数量',width:60,align:'right'},
                {field:'remark', title:'备注',width:100,align:'left'}
            ]]
        });

        $(function(){
            $("#purchase-goods-history-price-dialog").datagrid({ //销售商品明细信息编辑
                authority:historyPriceListJson,
                columns: historyPriceListJson.common,
                border: true,
                fit: true,
                rownumbers: true,
                striped:true,
                singleSelect: true,
                data: JSON.parse('${listjson}')
            }).datagrid('columnMoving');
        });
    </script>
</body>
</html>
