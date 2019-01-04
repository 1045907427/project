<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
<div id="sendUserDetailListLog-table-query">
    <div class="buttonBG">
        <security:authorize url="/storage/senduser/exportSendUserDetailLogList">
            <a href="javaScript:void(0);" id="sendUserDetailListLog-button-export" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-export'">导出</a>
        </security:authorize>

    </div>
    <div>
        <form action="" id="sendUserDetailListLog-table-form">
            <td>业务日期:</td>
            <td class="tdinput"><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${tomonth}"/> 到 <input type="text" name="businessdate2" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" value="${today}"/></td>
            <td>
                <a href="javaScript:void(0);" id="sendUserDetailListLog-queay-queryList" class="button-qr">查询</a>
            </td>
        </form>
    </div>
</div>
<table id="sendUserDetailListLog-table"></table>
<script type="text/javascript">
    var senduserid='${senduserid}';
    $(function(){
        var sendUserDetailListLogFormJson = $("#sendUserDetailListLog-table-form").serializeJSON();
        //根据初始的列与用户保存的列生成以及字段权限生成新的列
        $('#sendUserDetailListLog-table').datagrid({
            columns: [[
                    {field:'sourceid',title:'单据编号',width:150,sortable:true,rowspan:2},
                    {field:'businessdate',title:'业务日期',width:125,sortable:true,rowspan:2},
                    {title:'核对',align:'center',colspan:5},
                    {title:'发货',align:'center',colspan:5},
                    {title:'卸货',align:'center',colspan:5},
                    {field:'remark',title:'备注',width:125,sortable:true,rowspan:2},
                ],
                [
                    {field:'checknum',title:'核对数量',width:125,sortable:true},
                    {field:'checkamount',title:'核对金额',width:125,sortable:true,
                        formatter:function(value,row,index){
                            return formatterMoney(value);
                        }
                    },
                    {field:'checkvolume',title:'核对体积',width:125,sortable:true,
                        formatter:function(value,row,index){
                            return formatterMoney(value);
                        }
                    },
                    {field:'checkweight',title:'核对重量',width:125,sortable:true,
                        formatter:function(value,row,index){
                            return formatterMoney(value);
                        }
                    },
                    {field:'checkbox',title:'核对箱数',width:125,sortable:true,
                        formatter:function(value,row,index){
                            return formatterMoney(value);
                        }
                    },

                    {field:'loadednum',title:'发货数量',width:125,sortable:true},
                    {field:'loadedamount',title:'发货金额',width:125,sortable:true,
                        formatter:function(value,row,index){
                            return formatterMoney(value);
                        }
                    },
                    {field:'loadedvolume',title:'发货体积',width:125,sortable:true,
                        formatter:function(value,row,index){
                            return formatterMoney(value);
                        }
                    },
                    {field:'loadedweight',title:'发货重量',width:125,sortable:true,
                        formatter:function(value,row,index){
                            return formatterMoney(value);
                        }
                    },
                    {field:'loadedbox',title:'发货箱数',width:125,sortable:true,
                        formatter:function(value,row,index){
                            return formatterMoney(value);
                        }
                    },

                    {field:'unloadednum',title:'卸货数量',width:125,sortable:true},
                    {field:'unloadedamount',title:'卸货金额',width:125,sortable:true,
                        formatter:function(value,row,index){
                            return formatterMoney(value);
                        }
                    },
                    {field:'unloadedvolume',title:'卸货体积',width:125,sortable:true,
                        formatter:function(value,row,index){
                            return formatterMoney(value);
                        }
                    },
                    {field:'unloadedweight',title:'卸货重量',width:125,sortable:true,
                        formatter:function(value,row,index){
                            return formatterMoney(value);
                        }
                    },
                    {field:'unloadedbox',title:'卸货箱数',width:125,sortable:true,
                        formatter:function(value,row,index){
                            return formatterMoney(value);
                        }
                    },
            ]],
            sortName: 'businessdate',
            sortOrder: 'asc',
            fit:true,
            rownumbers:true,
            pagination: true,
            pageSize:100,
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            showFooter:true,
            toolbar:'#sendUserDetailListLog-table-query',
            url:'storage/senduser/getSendUserDetailLogBySendUser.do?senduserid='+senduserid,
            queryParams:sendUserDetailListLogFormJson
        }).datagrid("columnMoving");
    });
    //查询
    function querySumarryLog(){
        //把form表单的name序列化成JSON对象
        var queryJSON = $("#sendUserDetailListLog-table-form").serializeJSON();
        $("#sendUserDetailListLog-table").datagrid("load",queryJSON);
    }
    //导出
    $("#sendUserDetailListLog-button-export").Excel('export',{
        queryForm: "#sendUserDetailListLog-form", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
        datagrid: "#sendUserDetailListLog-table",
        type:'exportUserdefined',
        name:'发货人报表明细导出',
        url:'storage/senduser/exportSendUserDetailLogList.do'
    });
</script>
</body>
</html>
