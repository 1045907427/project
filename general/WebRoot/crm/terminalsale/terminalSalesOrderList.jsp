<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客户销量单据列表</title>
    <%@include file="/include.jsp"%>
</head>
<body>
<table id="crm-table-terminalSalesOrderList"></table>
<div id="crm-query-terminalSalesOrderList" style="padding:0px;height:90px">
    <div class="buttonBG" id="crm-buttons-terminalSalesOrderList" ></div>
    <div>
        <form action="" id="crm-form-terminalSalesOrderList" method="post">
            <table class="querytable">
                <tr>
                    <td>业务日期：</td>
                    <td class="tdinput"><input type="text" name="businessdate" style="width:95px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }" />
                        到 <input type="text" name="businessdate1" style="width:95px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"  />
                    </td>
                    <td>单据编号：</td>
                    <td class="tdinput"><input type="text" name="id" class="len150" /> </td>
                    <td>来源类型：</td>
                    <td>
                        <select name="sourcetype" style="width: 160px;">
                            <option></option>
                            <option value="0">普通</option>
                            <option value="1">手机</option>
                            <option value="2">导入</option>
                            <option value="3">销量更新</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>客&nbsp;&nbsp;户：</td>
                    <td class="tdinput"><input type="text" id="crm-customer-terminalSalesOrderList" style="width: 210px" name="customerid" /></td>
                    <td>客户单号：</td>
                    <td class="tdinput"><input type="text" name="sourceid" class="len150" /> </td>
                    <td>销售部门：</td>
                    <td class="tdinput">
                        <input type="text" id="crm-salesDept-terminalSalesOrderList" name="salesdept" style="width: 160px;" />
                    </td>
                    <td colspan="2" class="tdbutton" style="padding-left: 5px">
                        <a href="javascript:;" id="crm-queay-terminalSalesOrderList" class="button-qr">查询</a>
                        <a href="javascript:;" id="crm-resetQueay-terminalSalesOrderList" class="button-qr">重置</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="crm-importModel-dialog" ></div>
<div id="crm-dialog-terminalSalesOrderList"></div>
<script type="text/javascript">
    var initQueryJSON = $("#crm-form-terminalSalesOrderList").serializeJSON();
    $(function(){
        //表头标题
        var crmTerminalJson = $("#crm-table-terminalSalesOrderList").createGridColumnLoad({
            name :'t_crm_sales_order',
            frozenCol : [[{field:'ck',checkbox:true}]],
            commonCol : [[
                {field:'id',title:'编号',width:150, align: 'left',sortable:true},
                {field:'businessdate',title:'业务日期',width:80,align:'left',sortable:true},
                {field:'customerid',title:'客户编码',width:60,align:'left',sortable:true},
                {field:'customername',title:'客户名称',width:150,align:'left',isShow:true},
                {field:'handlerid',title:'对方经手人',width:80,align:'left',hidden:true},
                {field:'salesdept',title:'销售部门',width:100,align:'left',sortable:true},
                {field:'salesuser',title:'客户业务员',width:80,align:'left',sortable:true},
                {field:'field01',title:'金额',width:80,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'field02',title:'未税金额',width:80,align:'right',hidden:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'field03',title:'税额',width:80,align:'right',hidden:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
//                {field:'status',title:'状态',width:60,align:'left',sortable:true,
//                    formatter:function(value,row,index){
//                        return row.statusname;
//                    }
//                },
                {field:'indooruserid',title:'销售内勤',width:80,sortable:true,
                    formatter:function(value,rowData,index){
                        return rowData.indoorusername;
                    }
                },
                {field:'sourcetype',title:'来源类型',width:80,sortable:true,
                    formatter:function(value,rowData,index){
                        if(value=="0"){
                            return "普通";
                        }else if(value=="1"){
                            return "手机";
                        }else if(value=="2"){
                            return "导入";
                        }else if(value=="3"){
                            return "销量更新";
                        }
                    }
                },
                {field:'sourceid',title:'来源单号/客户单号',width:120,sortable:true},
                {field:'field04',title:'总重量(千克)',width:80,hidden:true},
                {field:'field05',title:'总体积(立方米)',width:100,hidden:true},
                {field:'addusername',title:'制单人',width:60,sortable:true},
                {field:'addtime',title:'制单时间',width:130,sortable:true},
                {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
                {field:'audittime',title:'审核时间',width:80,sortable:true,hidden:true},
                {field:'modifyusername',title:'修改人',width:60,sortable:true,hidden:true},
                {field:'modifytime',title:'修改时间',width:130,sortable:true,hidden:true},
                {field:'remark',title:'备注',width:100}
            ]]
        });

        $("#crm-table-terminalSalesOrderList").datagrid({
            authority: crmTerminalJson,
            frozenColumns: crmTerminalJson.frozen,
            columns: crmTerminalJson.common,
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            sortName: 'addtime',
            sortOrder: 'desc',
            pageSize: 20,
            pageList: [10, 20, 30, 50],
            checkOnSelect: true,
            selectOnCheck:true,
            queryParams:initQueryJSON,
            url: 'crm/terminal/terminalSalesOrderListData.do',
            toolbar:'#crm-query-terminalSalesOrderList',
            onDblClickRow:function(index, data){
                top.addOrUpdateTab('crm/terminal/terminalSalesOrderPage.do?type=edit&id='+data.id, '客户销量查看');
            }

        });
        //查询
        $("#crm-queay-terminalSalesOrderList").click(function(){
            var queryJSON = $("#crm-form-terminalSalesOrderList").serializeJSON();
            $("#crm-table-terminalSalesOrderList").datagrid('load', queryJSON);
        });
        //重置
        $("#crm-resetQueay-terminalSalesOrderList").click(function(){
            $("#crm-customer-terminalSalesOrderList").customerWidget("clear");
            $("#crm-salesDept-terminalSalesOrderList").widget("clear");
            $("#crm-form-terminalSalesOrderList").form("reset");
            var queryJSON = $("#crm-form-terminalSalesOrderList").serializeJSON();
            $("#crm-table-terminalSalesOrderList").datagrid('load', queryJSON);
        });
        //客户参照窗口
        $("#crm-customer-terminalSalesOrderList").customerWidget({
            singleSelect:true,
            isdatasql:false,
            onlyLeafCheck:false
        });
        //销售部门控件
        $("#crm-salesDept-terminalSalesOrderList").widget({
            referwid:'RL_T_BASE_DEPARTMENT_SELLER',
            singleSelect:true,
            width:160,
            onlyLeafCheck:false
        });

        //按钮
        $("#crm-buttons-terminalSalesOrderList").buttonWidget({
            initButton:[
                {},
                <security:authorize url="/crm/terminal/addTerminalSalesOrder.do">
                {
                    type: 'button-add',
                    handler: function(){
                        top.addTab('crm/terminal/terminalSalesOrderPage.do', "客户销量新增");
                    }
                },
                </security:authorize>
                <%--<security:authorize url="/crm/terminal/editTerminalSalesOrder.do">--%>
                <%--{--%>
                    <%--type: 'button-edit',--%>
                    <%--handler: function(){--%>
                        <%--var con = $("#crm-table-terminalSalesOrderList").datagrid('getSelected');--%>
                        <%--if(con == null){--%>
                            <%--$.messager.alert("提醒","请选择一条记录");--%>
                                <%--return false;--%>
                            <%--}--%>
                            <%--top.addTab('crm/terminal/terminalSalesOrderPage.do?type=edit&id='+ con.id, "客户销量修改");--%>
                    <%--}--%>
                 <%--},--%>
                <%--</security:authorize>--%>
            ],
            buttons:[
                {},
                <security:authorize url="/crm/terminal/orderModelImport.do">
                {
                    id: 'button-import-html',
                    name:'模板导入',
                    iconCls:'button-import',
                    handler: function(){
                        uploadHtml();
                    }
                },
                </security:authorize>
                <security:authorize url="/crm/terminal/queryCrmSales.do">
                {
                    id: 'button-query',
                    name:'客户状态查询',
                    iconCls:'button-commonquery',
                    handler: function(){
                        $('<div id="crm-dialog-terminalSalesOrderList-content"></div>').appendTo('#crm-dialog-terminalSalesOrderList');
                        $("#crm-dialog-terminalSalesOrderList-content").dialog({ //弹出新添加窗口
                            title: '客户状态查询',
                            maximizable: true,
                            width: 1000,
                            height: 500,
                            closed: false,
                            modal: true,
                            cache: false,
                            resizable: true,
                            href: 'crm/terminal/showCustomerQeuryTeminalPage.do',
                            onClose: function () {
                                $('#crm-dialog-terminalSalesOrderList-content').dialog("destroy");
                            },
                            onLoad: function () {

                            }
                        });

                    }
                },
                </security:authorize>
            ],
            model: 'bill',
            type: 'list',
            tname: 't_crm_sales_order'
        });
    });
    /**
    *模板导入
     */
    function uploadHtml(){
        $("#crm-importModel-dialog").dialog({
            href: 'crm/terminal/showCrmModelParamPage.do?type=3',
            width: 400,
            height: 300,
            title: '模板文件上传',
            colsed: false,
            cache: false,
            modal: true
        });
    }



</script>
</body>
</html>
