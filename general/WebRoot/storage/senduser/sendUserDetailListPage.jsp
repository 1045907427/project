<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>发货单据明细</title>
    <%@include file="/include.jsp" %>
</head>

<body>
<table id="sendUserDetailListPage-table"></table>
<div id="sendUserDetailListPage-toolbar" style="padding:0px;height:auto">
    <div class="buttonBG">
        <security:authorize url="/storage/senduser/exportSendUserDetailList.do">
            <a href="javaScript:void(0);" id="sendUserDetailListPage-button-export" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-export'">导出</a>
        </security:authorize>

    </div>
    <div>
        <form action="" method="post" id="sendUserDetailListPage-form">
            <table class="querytable">
                <tr>
                    <td>业务日期:</td>
                    <td class="tdinput"><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${tomonth}"/> 到 <input type="text" name="businessdate2" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" value="${today}"/></td>
                    <td>发货人:</td>
                    <td><input type="text" id="sendUserDetailListPage-storager" name="senduserid" style="width:150px"/></td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="sendUserDetailListPage-queay-queryList" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="sendUserDetailListPage-queay-reloadList" class="button-qr">重置</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div class="easyui-menu" id="sendUserDetailListPage-contextMenu" style="display: none;">
    <div id="sendUserDetailListPage-addRow" data-options="iconCls:'button-add'">添加</div>
    <div id="sendUserDetailListPage-editRow" data-options="iconCls:'button-edit'">编辑</div>
    <div id="sendUserDetailListPage-removeRow" data-options="iconCls:'button-delete'">删除</div>
</div>
<div id="sendUserDetailListPage-dialog"></div>
</body>
<script type="text/javascript">
    var initQueryJSON = $("#sendUserDetailListPage-form").serializeJSON();
    var loadData = false;
    var SMR_footerobject = null;

    $(function() {
        $("#sendUserDetailListPage-table").datagrid({
            columns:[[
                {field:'ck',checkbox:true,isShow:true,rowspan:2},
                {field:'senduserid',title:'发货人编号',width:125,sortable:true,rowspan:2},
                {field:'sendusername',title:'发货人名称',width:125,sortable:true,rowspan:2},
                {title:'核对',align:'center',colspan:5},
                {title:'发货',align:'center',colspan:5},
                {title:'卸货',align:'center',colspan:5},
                ],[
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

                {field:'remark',title:'备注',width:125,sortable:true},
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
            url: 'storage/senduser/showSendUserDetailList.do',
            queryParams: initQueryJSON,
            toolbar: '#sendUserDetailListPage-toolbar',
            onBeforeLoad:function(){
                $(this).datagrid('clearChecked');
                $(this).datagrid('clearSelections');
                return loadData;
            },
            onLoadSuccess:function(){
                var footerrows = $(this).datagrid('getFooterRows');
                if(null!=footerrows && footerrows.length>0){
                    SMR_footerobject = footerrows[0];
                    checkTotalAmount();
                }
            },
            onCheckAll:function(){
                checkTotalAmount();
            },
            onUncheckAll:function(){
                checkTotalAmount();
            },
            onCheck:function(){
                checkTotalAmount();
            },
            onUncheck:function(){
                checkTotalAmount();
            },
            onDblClickRow:function(rowIndex, rowData){

                var senduserid = "";
                if(rowData.senduserid!=null){
                    senduserid = rowData.senduserid;
                }
                var url = "storage/senduser/showSendUserDetailListLogPage.do?senduserid="+senduserid;
                console.log(1111)
                $('#sendUserDetailListPage-dialog').dialog({
                    title: '商品库存量追踪日志',
                    width:800,
                    height:400,
                    collapsible:false,
                    minimizable:false,
                    maximizable:true,
                    resizable:true,
                    closed: true,
                    cache: false,
                    maximized:true,
                    href: url,
                    modal: true
                });
                $('#sendUserDetailListPage-dialog').dialog("open");
            },
        }).datagrid('columnMoving');

        //导出
        $("#sendUserDetailListPage-button-export").Excel('export',{
            queryForm: "#sendUserDetailListPage-form", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            datagrid: "#sendUserDetailListPage-table",
            type:'exportUserdefined',
            name:'发货人报表导出',
            url:'storage/senduser/exportSendUserDetailList.do'
        });

        //查询
        $("#sendUserDetailListPage-queay-queryList").click(function(){
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#sendUserDetailListPage-form").serializeJSON();
            $("#sendUserDetailListPage-table").datagrid("load",queryJSON);
        });
        //重置
        $("#sendUserDetailListPage-queay-reloadList").click(function(){
            $("#sendUserDetailListPage-storager").widget("clear");
            $("#sendUserDetailListPage-form")[0].reset();
            var queryJSON = $("#sendUserDetailListPage-form").serializeJSON();
            $("#sendUserDetailListPage-table").datagrid("load",queryJSON);
        });

        $("#sendUserDetailListPage-storager").widget({
            referwid:'RL_T_BASE_PERSONNEL_STORAGER',
            width:150,
            col:'name',
            singleSelect:true,
        });

        //计算选中合计
        function checkTotalAmount(){
            var rows =  $("#sendUserDetailListPage-table").datagrid('getChecked');
            var checknum = 0,checkamount = 0,checkvolume = 0,checkweight = 0,checkbox = 0,
                loadednum = 0,loadedamount = 0,loadedvolume = 0,loadedweight = 0,loadedbox = 0,
                unloadednum = 0,unloadedamount = 0,unloadedvolume = 0,unloadedweight = 0,unloadedbox = 0
            for(var i=0;i<rows.length;i++){
                checknum = Number(checknum)+Number(rows[i].checknum == undefined ? 0 : rows[i].checknum);
                checkamount = Number(checkamount)+Number(rows[i].checkamount == undefined ? 0 : rows[i].checkamount);
                checkvolume = Number(checkvolume)+Number(rows[i].checkvolume == undefined ? 0 : rows[i].checkvolume);
                checkweight = Number(checkweight)+Number(rows[i].checkweight == undefined ? 0 : rows[i].checkweight);
                checkbox = Number(checkbox)+Number(rows[i].checkbox == undefined ? 0 : rows[i].checkbox);

                loadednum = Number(loadednum)+Number(rows[i].loadednum == undefined ? 0 : rows[i].loadednum);
                loadedamount = Number(loadedamount)+Number(rows[i].loadedamount == undefined ? 0 : rows[i].loadedamount);
                loadedvolume = Number(loadedvolume)+Number(rows[i].loadedvolume == undefined ? 0 : rows[i].loadedvolume);
                loadedweight = Number(loadedweight)+Number(rows[i].loadedweight == undefined ? 0 : rows[i].loadedweight);
                loadedbox = Number(loadedbox)+Number(rows[i].loadedbox == undefined ? 0 : rows[i].loadedbox);

                unloadednum = Number(unloadednum)+Number(rows[i].unloadednum == undefined ? 0 : rows[i].unloadednum);
                unloadedamount = Number(unloadedamount)+Number(rows[i].unloadedamount == undefined ? 0 : rows[i].unloadedamount);
                unloadedvolume = Number(unloadedvolume)+Number(rows[i].unloadedvolume == undefined ? 0 : rows[i].unloadedvolume);
                unloadedweight = Number(unloadedweight)+Number(rows[i].unloadedweight == undefined ? 0 : rows[i].unloadedweight);
                unloadedbox = Number(unloadedbox)+Number(rows[i].unloadedbox == undefined ? 0 : rows[i].unloadedbox);
            }

            var foot=[{senduserid:'选中合计',checknum:checknum,checkamount:formatterMoney(checkamount),checkvolume:formatterMoney(checkvolume),checkweight:formatterMoney(checkweight),checkbox:formatterMoney(checkbox),
                loadednum:loadednum,loadedamount:formatterMoney(loadedamount),loadedvolume:formatterMoney(loadedvolume),loadedweight:formatterMoney(loadedweight),loadedbox:formatterMoney(loadedbox),
                unloadednum:unloadednum,unloadedamount:formatterMoney(unloadedamount),unloadedvolume:formatterMoney(unloadedvolume),unloadedweight:formatterMoney(unloadedweight),unloadedbox:formatterMoney(unloadedbox)
            }];
            if(null!=SMR_footerobject){
                foot.push(SMR_footerobject);
            }
            $("#sendUserDetailListPage-table").datagrid("reloadFooter",foot);
        }
        setTimeout(function(){
            loadData = true;
        },100);
    })
    function refresh(){
        var queryJSON = $("#sendUserDetailListPage-form").serializeJSON();
        $("#sendUserDetailListPage-table").datagrid("load",queryJSON);
    }
</script>
</html>

