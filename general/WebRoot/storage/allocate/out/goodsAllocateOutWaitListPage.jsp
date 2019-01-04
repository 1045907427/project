<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>调拨待发量来源单据页面</title>
</head>

<body>
<table id="storage-allocateout-waitlist"></table>
<script type="text/javascript">
    $(function(){
        $("#storage-allocateout-waitlist").datagrid({
            columns:[[
                {field:'id',title:'单据编号',width:150,align:'left'},
                {field:'businessdate',title:'业务日期',width:75,align:'left'},
                {field:'outstorageid',title:'调出仓库',width:80,align:'left',
                    formatter:function(value,row,index){
                        return row.outstoragename;
                    }
                },
                {field:'enterstorageid',title:'调入仓库',width:80,align:'left',
                    formatter:function(value,row,index){
                        return row.enterstoragename;
                    }
                },
                {field:'sourcetype', title:'来源类型', width:150,align:'left',hidden:true},
                {field:'goodsid',title:'商品编码',width:60,align:'left',hidden:true},
                {field:'goodsname', title:'商品名称', width:150,align:'left',hidden:true},
                {field:'barcode', title:'条形码', width:100,align:'left',hidden:true},
                {field:'brandname', title:'商品品牌',width:80,align:'left',hidden:true},
                {field:'boxnum', title:'箱装量',width:40,align:'right',
                    formatter:function(value,row,index){
                        return formatterBigNumNoLen(value);
                    }
                },
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
                {field:'taxamount', title:'金额',width:60,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'auxnumdetail', title:'辅数量',width:60,align:'right'},
                {field:'remark', title:'备注',width:100,align:'left'}
            ]],
            fit:true,
            method:'post',
            rownumbers:true,
            idField:'id',
            singleSelect:true,
            url: 'storage/showAllocateOutGoodsWaitListData.do?goodsid=${goodsid}&storageid=${storageid}&summarybatchid=${summarybatchid}',
        });
    });
</script>
</body>
</html>
